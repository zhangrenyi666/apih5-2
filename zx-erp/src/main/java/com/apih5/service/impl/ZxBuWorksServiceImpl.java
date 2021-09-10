package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONArray;
import com.apih5.framework.utils.ConvertUtil;
import com.apih5.mybatis.dao.ZxBuWorksToStockPriceMapper;
import com.apih5.mybatis.dao.ZxBuYgjResTechnicsMapper;
import com.apih5.mybatis.dao.ZxCtWorksMapper;
import com.apih5.mybatis.pojo.ZxBuProjectTypeCheck;
import com.apih5.mybatis.pojo.ZxCtWorks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxBuWorksMapper;
import com.apih5.mybatis.pojo.ZxBuWorks;
import com.apih5.service.ZxBuWorksService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxBuWorksService")
public class ZxBuWorksServiceImpl implements ZxBuWorksService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxBuWorksMapper zxBuWorksMapper;

    @Autowired(required = true)
    private ZxCtWorksMapper zxCtWorksMapper;

    @Autowired(required = true)
    private ZxBuYgjResTechnicsMapper zxBuYgjResTechnicsMapper;

    @Autowired(required = true)
    private ZxBuWorksToStockPriceMapper zxBuWorksToStockPriceMapper;

    @Override
    public ResponseEntity getZxBuWorksListByCondition(ZxBuWorks zxBuWorks) {
        if (zxBuWorks == null) {
            zxBuWorks = new ZxBuWorks();
        }
        // 分页查询
        PageHelper.startPage(zxBuWorks.getPage(),zxBuWorks.getLimit());
        // 获取数据
        List<ZxBuWorks> zxBuWorksList = zxBuWorksMapper.selectByZxBuWorksList(zxBuWorks);
        // 得到分页信息
        PageInfo<ZxBuWorks> p = new PageInfo<>(zxBuWorksList);

        return repEntity.okList(zxBuWorksList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxBuWorksDetail(ZxBuWorks zxBuWorks) {
        if (zxBuWorks == null) {
            zxBuWorks = new ZxBuWorks();
        }
        // 获取数据
        ZxBuWorks dbZxBuWorks = zxBuWorksMapper.selectByPrimaryKey(zxBuWorks.getZxBuWorksId());
        // 数据存在
        if (dbZxBuWorks != null) {
            return repEntity.ok(dbZxBuWorks);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxBuWorks(ZxBuWorks zxBuWorks) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxBuWorks.setZxBuWorksId(UuidUtil.generate());
        zxBuWorks.setCreateUserInfo(userKey, realName);
        int flag = zxBuWorksMapper.insert(zxBuWorks);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxBuWorks);
        }
    }

    @Override
    public ResponseEntity updateZxBuWorks(ZxBuWorks zxBuWorks) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxBuWorks dbZxBuWorks = zxBuWorksMapper.selectByPrimaryKey(zxBuWorks.getZxBuWorksId());
        if (dbZxBuWorks != null && StrUtil.isNotEmpty(dbZxBuWorks.getZxBuWorksId())) {
           // 父节点ID
           dbZxBuWorks.setParentID(zxBuWorks.getParentID());
           // 树节点编号
           dbZxBuWorks.setTreeNode(zxBuWorks.getTreeNode());
           // 项目机构ID
           dbZxBuWorks.setOrgID(zxBuWorks.getOrgID());
           // 清单书ID
           dbZxBuWorks.setWorkBookID(zxBuWorks.getWorkBookID());
           // 清单类型
           dbZxBuWorks.setWorkType(zxBuWorks.getWorkType());
           // 清单编号
           dbZxBuWorks.setWorkNo(zxBuWorks.getWorkNo());
           // 清单名称
           dbZxBuWorks.setWorkName(zxBuWorks.getWorkName());
           // 计量单位
           dbZxBuWorks.setUnit(zxBuWorks.getUnit());
           // 合同单价
           dbZxBuWorks.setContractPrice(zxBuWorks.getContractPrice());
           // 合同数量
           dbZxBuWorks.setContractQty(zxBuWorks.getContractQty());
           // 合同金额
           dbZxBuWorks.setContractAmt(zxBuWorks.getContractAmt());
           // 变更后数量
           dbZxBuWorks.setQuantity(zxBuWorks.getQuantity());
           // 变更后单价
           dbZxBuWorks.setPrice(zxBuWorks.getPrice());
           // 是否禁用
           dbZxBuWorks.setDeleted(zxBuWorks.getDeleted());
           // 是否叶子节点
           dbZxBuWorks.setIsLeaf(zxBuWorks.getIsLeaf());
           // 现有状态
           dbZxBuWorks.setExsitStatus(zxBuWorks.getExsitStatus());
           // 可分配的
           dbZxBuWorks.setIsAssignable(zxBuWorks.getIsAssignable());
           // 修改状态
           dbZxBuWorks.setUpdateFlag(zxBuWorks.getUpdateFlag());
           // combProp
           dbZxBuWorks.setCombProp(zxBuWorks.getCombProp());
           // pp1
           dbZxBuWorks.setPp1(zxBuWorks.getPp1());
           // pp2
           dbZxBuWorks.setPp2(zxBuWorks.getPp2());
           // pp3
           dbZxBuWorks.setPp3(zxBuWorks.getPp3());
           // pp4
           dbZxBuWorks.setPp4(zxBuWorks.getPp4());
           // pp5
           dbZxBuWorks.setPp5(zxBuWorks.getPp5());
           // pp6
           dbZxBuWorks.setPp6(zxBuWorks.getPp6());
           // pp7
           dbZxBuWorks.setPp7(zxBuWorks.getPp7());
           // pp8
           dbZxBuWorks.setPp8(zxBuWorks.getPp8());
           // pp9
           dbZxBuWorks.setPp9(zxBuWorks.getPp9());
           // pp10
           dbZxBuWorks.setPp10(zxBuWorks.getPp10());
           // 核查数量
           dbZxBuWorks.setCheckQty(zxBuWorks.getCheckQty());
           // expectChangeQty
           dbZxBuWorks.setExpectChangeQty(zxBuWorks.getExpectChangeQty());
           // expectChangePrice
           dbZxBuWorks.setExpectChangePrice(zxBuWorks.getExpectChangePrice());
           // inputWorkType
           dbZxBuWorks.setInputWorkType(zxBuWorks.getInputWorkType());
           // isReCountAmt
           dbZxBuWorks.setIsReCountAmt(zxBuWorks.getIsReCountAmt());
           // qty
           dbZxBuWorks.setQty(zxBuWorks.getQty());
           // isGroup
           dbZxBuWorks.setIsGroup(zxBuWorks.getIsGroup());
           // contractPriceNoTax
           dbZxBuWorks.setContractPriceNoTax(zxBuWorks.getContractPriceNoTax());
           // priceNoTax
           dbZxBuWorks.setPriceNoTax(zxBuWorks.getPriceNoTax());
           // contractAmtNoTax
           dbZxBuWorks.setContractAmtNoTax(zxBuWorks.getContractAmtNoTax());
           // amtNoTax
           dbZxBuWorks.setAmtNoTax(zxBuWorks.getAmtNoTax());
           // taxRate
           dbZxBuWorks.setTaxRate(zxBuWorks.getTaxRate());
           // 备注
           dbZxBuWorks.setRemarks(zxBuWorks.getRemarks());
           // 排序
           dbZxBuWorks.setSort(zxBuWorks.getSort());
           // 共通
           dbZxBuWorks.setModifyUserInfo(userKey, realName);
           flag = zxBuWorksMapper.updateByPrimaryKey(dbZxBuWorks);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxBuWorks);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxBuWorks(List<ZxBuWorks> zxBuWorksList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxBuWorksList != null && zxBuWorksList.size() > 0) {
           ZxBuWorks zxBuWorks = new ZxBuWorks();
           zxBuWorks.setModifyUserInfo(userKey, realName);
           flag = zxBuWorksMapper.batchDeleteUpdateZxBuWorks(zxBuWorksList, zxBuWorks);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxBuWorksList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public ResponseEntity getZxBuWorksTree(ZxBuWorks zxBuWorks) {
        if (zxBuWorks == null) {
            zxBuWorks = new ZxBuWorks();
        }
        if (StrUtil.isEmpty(zxBuWorks.getOrgID())) {
            return repEntity.ok(new ArrayList<>());
//            return repEntity.layerMessage("no", "项目ID不能为空！");
        }
        // 获取数据
        List<ZxBuWorks> zxBuWorksList = zxBuWorksMapper.selectByZxBuWorksList(zxBuWorks);
        JSONArray jsonArray = ConvertUtil.listToTree(new JSONArray(zxBuWorksList), "zxBuWorksId", "parentID", "workName", "workNo");
        return repEntity.ok(jsonArray);
    }

    @Override
    public ResponseEntity getZxBuWorksTreeList(ZxBuWorks zxBuWorks) {
        if (StrUtil.isEmpty(zxBuWorks.getZxBuWorksId())) {
            return repEntity.ok(new ArrayList<>());
//            return repEntity.layerMessage("no", "父ID不能为空！");
        }
        if (StrUtil.isEmpty(zxBuWorks.getOrgID())) {
            return repEntity.ok(new ArrayList<>());
//            return repEntity.layerMessage("no", "项目不能为空！");
        }
        // 分页查询
        PageHelper.startPage(zxBuWorks.getPage(),zxBuWorks.getLimit());
        // 获取数据
        List<ZxBuWorks> zxBuWorksList = zxBuWorksMapper.getZxBuWorksTreeList(zxBuWorks);
        PageInfo<ZxBuWorks> p = new PageInfo<>(zxBuWorksList);
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (ZxBuWorks zxBuWorks1 : zxBuWorksList) {
            Map<String, Object> map = BeanUtil.beanToMap(zxBuWorks1);
            map.remove("children");
            mapList.add(map);
        }
        return repEntity.okList(mapList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxBuWorksTreeListAll(ZxBuWorks zxBuWorks) {
        if (zxBuWorks == null) {
            zxBuWorks = new ZxBuWorks();
        }
        // 分页查询
//        PageHelper.startPage(zxBuWorks.getPage(),zxBuWorks.getLimit());
        // 获取数据
        List<ZxBuWorks> zxBuWorksList = zxBuWorksMapper.selectByZxBuWorksList(zxBuWorks);
        // 得到分页信息
//        PageInfo<ZxBuWorks> p = new PageInfo<>(zxBuWorksList);
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (ZxBuWorks zxBuWorks1 : zxBuWorksList) {
            Map<String, Object> map = BeanUtil.beanToMap(zxBuWorks1);
            map.remove("children");
            mapList.add(map);
        }
//        return repEntity.okList(mapList,p.getTotal());
        return repEntity.ok(mapList);
    }

    @Override
    public ResponseEntity getZxBuWorksNoName(ZxBuWorks zxBuWorks) {
        if (zxBuWorks == null) {
            zxBuWorks = new ZxBuWorks();
        }
        if (StrUtil.isEmpty(zxBuWorks.getParentID())) {
            return repEntity.ok(new ArrayList<>());
//            return repEntity.layerMessage("no", "父ID不能为空！");
        }
        if (StrUtil.isEmpty(zxBuWorks.getOrgID())) {
            return repEntity.ok(new ArrayList<>());
//            return repEntity.layerMessage("no", "项目不能为空！");
        }
        if(StrUtil.equals("-1", zxBuWorks.getParentID())) {
            zxBuWorks.setParentID("");
        }
        ZxCtWorks zxCtWorks = new ZxCtWorks();
        zxCtWorks.setParentID(zxBuWorks.getParentID());
        zxCtWorks.setOrgID(zxBuWorks.getOrgID());
        // 获取数据
        List<ZxCtWorks> zxCtWorksList = zxCtWorksMapper.getZxCtWorksTreeList(zxCtWorks);
        zxCtWorksList.remove(0);
        //在这里挂接数据清单编号,  数据库清单名称
        for (ZxCtWorks ctWorks : zxCtWorksList) {
            if(ctWorks.getIsLeaf()==1){
                //是叶子节点
                ZxBuWorks workNoArrWorkNameArr = zxBuYgjResTechnicsMapper.getWorkNoArrWorkNameArr(ctWorks.getId());
                if(workNoArrWorkNameArr != null){
                    ctWorks.setWorkIDJoin(workNoArrWorkNameArr.getZxBuWorksId());
                    ctWorks.setWorkNameJoin(workNoArrWorkNameArr.getWorkName());
                    ctWorks.setWorkNoJoin(workNoArrWorkNameArr.getWorkNo());
                }
            }
        }
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (ZxCtWorks zxCtWorks1 : zxCtWorksList) {
            Map<String, Object> map = BeanUtil.beanToMap(zxCtWorks1);
            map.remove("children");
            mapList.add(map);
        }
        return repEntity.ok(mapList);
    }

    @Override
    public ResponseEntity getZxBuWorksResNoName(ZxBuWorks zxBuWorks) {
        if (zxBuWorks == null) {
            zxBuWorks = new ZxBuWorks();
        }
        if (StrUtil.isEmpty(zxBuWorks.getParentID())) {
            return repEntity.ok(new ArrayList<>());
//            return repEntity.layerMessage("no", "父ID不能为空！");
        }
        if (StrUtil.isEmpty(zxBuWorks.getOrgID())) {
            return repEntity.ok(new ArrayList<>());
//            return repEntity.layerMessage("no", "项目不能为空！");
        }
        if(StrUtil.equals("-1", zxBuWorks.getParentID())) {
            zxBuWorks.setParentID("");
        }
        ZxCtWorks zxCtWorks = new ZxCtWorks();
        zxCtWorks.setParentID(zxBuWorks.getParentID());
        zxCtWorks.setOrgID(zxBuWorks.getOrgID());
        // 获取数据
        List<ZxCtWorks> zxCtWorksList = zxCtWorksMapper.getZxCtWorksTreeList(zxCtWorks);
        zxCtWorksList.remove(0);
        //在这里挂接材料编号,  材料名称
        for (ZxCtWorks ctWorks : zxCtWorksList) {
            if(ctWorks.getIsLeaf()==1){
                //是叶子节点
                ZxCtWorks workNoArrWorkNameArr = zxBuWorksToStockPriceMapper.getResNoArrResNameArr(ctWorks.getId());
                if(workNoArrWorkNameArr != null){
                    ctWorks.setVoidJoin(workNoArrWorkNameArr.getVoidJoin());
                    ctWorks.setResCodeJoin(workNoArrWorkNameArr.getResCodeJoin());
                    ctWorks.setResNameJoin(workNoArrWorkNameArr.getResNameJoin());
                    ctWorks.setResGjLossCoefficientJoin(workNoArrWorkNameArr.getResGjLossCoefficientJoin());
                    ctWorks.setResGjConCoefficientJoin(workNoArrWorkNameArr.getResGjConCoefficientJoin());
                    ctWorks.setPriceJoin(workNoArrWorkNameArr.getPriceJoin());
                }
            }
        }
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (ZxCtWorks zxCtWorks1 : zxCtWorksList) {
            Map<String, Object> map = BeanUtil.beanToMap(zxCtWorks1);
            map.remove("children");
            mapList.add(map);
        }
        return repEntity.ok(mapList);
    }

}
