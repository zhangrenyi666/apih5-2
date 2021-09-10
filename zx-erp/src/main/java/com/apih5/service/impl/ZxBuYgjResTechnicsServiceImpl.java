package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.collection.CollectionUtil;
import com.apih5.mybatis.dao.ZxBuWorksMapper;
import com.apih5.mybatis.pojo.ZxBuWorks;
import com.apih5.service.ZxBuWorksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxBuYgjResTechnicsMapper;
import com.apih5.mybatis.pojo.ZxBuYgjResTechnics;
import com.apih5.service.ZxBuYgjResTechnicsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxBuYgjResTechnicsService")
public class ZxBuYgjResTechnicsServiceImpl implements ZxBuYgjResTechnicsService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxBuYgjResTechnicsMapper zxBuYgjResTechnicsMapper;

    @Autowired(required = true)
    private ZxBuWorksMapper zxBuWorksMapper;

    @Autowired(required = true)
    private ZxBuWorksService zxBuWorksService;

    @Override
    public ResponseEntity getZxBuYgjResTechnicsListByCondition(ZxBuYgjResTechnics zxBuYgjResTechnics) {
        if (zxBuYgjResTechnics == null) {
            zxBuYgjResTechnics = new ZxBuYgjResTechnics();
        }
        // 分页查询
        PageHelper.startPage(zxBuYgjResTechnics.getPage(),zxBuYgjResTechnics.getLimit());
        // 获取数据
        List<ZxBuYgjResTechnics> zxBuYgjResTechnicsList = zxBuYgjResTechnicsMapper.selectByZxBuYgjResTechnicsList(zxBuYgjResTechnics);
        // 得到分页信息
        PageInfo<ZxBuYgjResTechnics> p = new PageInfo<>(zxBuYgjResTechnicsList);

        return repEntity.okList(zxBuYgjResTechnicsList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxBuYgjResTechnicsDetail(ZxBuYgjResTechnics zxBuYgjResTechnics) {
        if (zxBuYgjResTechnics == null) {
            zxBuYgjResTechnics = new ZxBuYgjResTechnics();
        }
        // 获取数据
        ZxBuYgjResTechnics dbZxBuYgjResTechnics = zxBuYgjResTechnicsMapper.selectByPrimaryKey(zxBuYgjResTechnics.getZxBuYgjResTechnicsId());
        // 数据存在
        if (dbZxBuYgjResTechnics != null) {
            return repEntity.ok(dbZxBuYgjResTechnics);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxBuYgjResTechnics(ZxBuYgjResTechnics zxBuYgjResTechnics) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxBuYgjResTechnics.setZxBuYgjResTechnicsId(UuidUtil.generate());
        zxBuYgjResTechnics.setCreateUserInfo(userKey, realName);
        int flag = zxBuYgjResTechnicsMapper.insert(zxBuYgjResTechnics);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxBuYgjResTechnics);
        }
    }

    @Override
    public ResponseEntity updateZxBuYgjResTechnics(ZxBuYgjResTechnics zxBuYgjResTechnics) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxBuYgjResTechnics dbZxBuYgjResTechnics = zxBuYgjResTechnicsMapper.selectByPrimaryKey(zxBuYgjResTechnics.getZxBuYgjResTechnicsId());
        if (dbZxBuYgjResTechnics != null && StrUtil.isNotEmpty(dbZxBuYgjResTechnics.getZxBuYgjResTechnicsId())) {
           // 工序名称
           dbZxBuYgjResTechnics.setName(zxBuYgjResTechnics.getName());
           // billID
           dbZxBuYgjResTechnics.setBillID(zxBuYgjResTechnics.getBillID());
           // orgID
           dbZxBuYgjResTechnics.setOrgID(zxBuYgjResTechnics.getOrgID());
           // orderFlag
           dbZxBuYgjResTechnics.setOrderFlag(zxBuYgjResTechnics.getOrderFlag());
           // money
           dbZxBuYgjResTechnics.setMoney(zxBuYgjResTechnics.getMoney());
           // 标准工序ID
           dbZxBuYgjResTechnics.setStandardTechID(zxBuYgjResTechnics.getStandardTechID());
           // techType
           dbZxBuYgjResTechnics.setTechType(zxBuYgjResTechnics.getTechType());
           // comPrice
           dbZxBuYgjResTechnics.setComPrice(zxBuYgjResTechnics.getComPrice());
           // 序号
           dbZxBuYgjResTechnics.setTechOrder(zxBuYgjResTechnics.getTechOrder());
           // 不含税单价
           dbZxBuYgjResTechnics.setPrice(zxBuYgjResTechnics.getPrice());
           // 数量
           dbZxBuYgjResTechnics.setQty(zxBuYgjResTechnics.getQty());
           // 工序单位
           dbZxBuYgjResTechnics.setUnit(zxBuYgjResTechnics.getUnit());
           // budgetBookID
           dbZxBuYgjResTechnics.setBudgetBookID(zxBuYgjResTechnics.getBudgetBookID());
           // isGroup
           dbZxBuYgjResTechnics.setIsGroup(zxBuYgjResTechnics.getIsGroup());
           // isGroupDoRes
           dbZxBuYgjResTechnics.setIsGroupDoRes(zxBuYgjResTechnics.getIsGroupDoRes());
           // 参考区域
           dbZxBuYgjResTechnics.setAreaName(zxBuYgjResTechnics.getAreaName());
           // 标准值
           dbZxBuYgjResTechnics.setTechnicAmt(zxBuYgjResTechnics.getTechnicAmt());
           // 下限值
           dbZxBuYgjResTechnics.setDownAmt(zxBuYgjResTechnics.getDownAmt());
           // 上限值
           dbZxBuYgjResTechnics.setUpAmt(zxBuYgjResTechnics.getUpAmt());
           // 税率(%)
           dbZxBuYgjResTechnics.setTaxRate(zxBuYgjResTechnics.getTaxRate());
           // 税金单价
           dbZxBuYgjResTechnics.setTaxPrice(zxBuYgjResTechnics.getTaxPrice());
           // 已完工程量不含税单价
           dbZxBuYgjResTechnics.setFinPrice(zxBuYgjResTechnics.getFinPrice());
           // 已完工程税率(%)
           dbZxBuYgjResTechnics.setFinTaxRate(zxBuYgjResTechnics.getFinTaxRate());
           // 已完工程税金单价
           dbZxBuYgjResTechnics.setFinTaxPrice(zxBuYgjResTechnics.getFinTaxPrice());
           // 剩余工程不含税单价
           dbZxBuYgjResTechnics.setRemPrice(zxBuYgjResTechnics.getRemPrice());
           // 剩余工程税率(%)
           dbZxBuYgjResTechnics.setRemTaxRate(zxBuYgjResTechnics.getRemTaxRate());
           // 剩余工程税金单价
           dbZxBuYgjResTechnics.setRemTaxPrice(zxBuYgjResTechnics.getRemTaxPrice());
           // 已完工程量数量
           dbZxBuYgjResTechnics.setFinQty(zxBuYgjResTechnics.getFinQty());
           // 剩余工程数量
           dbZxBuYgjResTechnics.setRemQty(zxBuYgjResTechnics.getRemQty());
           // 工序编号
           dbZxBuYgjResTechnics.setTechNon(zxBuYgjResTechnics.getTechNon());
           // 计算公式
           dbZxBuYgjResTechnics.setFormulaStr(zxBuYgjResTechnics.getFormulaStr());
           // 计算公式
           dbZxBuYgjResTechnics.setFormulaStr2(zxBuYgjResTechnics.getFormulaStr2());
           // 关联清单名称
           dbZxBuYgjResTechnics.setWorkName(zxBuYgjResTechnics.getWorkName());
           // 参考值查询
           dbZxBuYgjResTechnics.setPp1(zxBuYgjResTechnics.getPp1());
           // technicType
           dbZxBuYgjResTechnics.setTechnicType(zxBuYgjResTechnics.getTechnicType());
           // 引用标准清单ID
           dbZxBuYgjResTechnics.setStandardWorkID(zxBuYgjResTechnics.getStandardWorkID());
           // 备注
           dbZxBuYgjResTechnics.setRemarks(zxBuYgjResTechnics.getRemarks());
           // 排序
           dbZxBuYgjResTechnics.setSort(zxBuYgjResTechnics.getSort());
           // 共通
           dbZxBuYgjResTechnics.setModifyUserInfo(userKey, realName);
           flag = zxBuYgjResTechnicsMapper.updateByPrimaryKey(dbZxBuYgjResTechnics);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxBuYgjResTechnics);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxBuYgjResTechnics(List<ZxBuYgjResTechnics> zxBuYgjResTechnicsList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxBuYgjResTechnicsList != null && zxBuYgjResTechnicsList.size() > 0) {
           ZxBuYgjResTechnics zxBuYgjResTechnics = new ZxBuYgjResTechnics();
           zxBuYgjResTechnics.setModifyUserInfo(userKey, realName);
           flag = zxBuYgjResTechnicsMapper.batchDeleteUpdateZxBuYgjResTechnics(zxBuYgjResTechnicsList, zxBuYgjResTechnics);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxBuYgjResTechnicsList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓


    @Override
    public ResponseEntity relevanceZxBuYgjResTechnics(ZxBuYgjResTechnics zxBuYgjResTechnics) {
        if(zxBuYgjResTechnics == null){
            return repEntity.ok(new ArrayList<>());
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        //传进来的是清单id,项目id
        //查询工序所有数据
        //MyBillID:固定的清单
        int flag = 0;
        ZxBuYgjResTechnics selectZxBuYgjResTechnics = new ZxBuYgjResTechnics();
        selectZxBuYgjResTechnics.setBillID(zxBuYgjResTechnics.getMyBillID());
        List<ZxBuYgjResTechnics> zxBuYgjResTechnics1 = zxBuYgjResTechnicsMapper.selectByZxBuYgjResTechnicsList(selectZxBuYgjResTechnics);
        for (ZxBuYgjResTechnics buYgjResTechnics : zxBuYgjResTechnics1) {
            //工序id
            buYgjResTechnics.setStandardTechID(buYgjResTechnics.getZxBuYgjResTechnicsId());
            buYgjResTechnics.setZxBuYgjResTechnicsId(UuidUtil.generate());
            buYgjResTechnics.setCreateUserInfo(userKey, realName);
            buYgjResTechnics.setOrgID(zxBuYgjResTechnics.getOrgID());
            //项目的清单
            buYgjResTechnics.setBillID(zxBuYgjResTechnics.getBillID());
            flag = zxBuYgjResTechnicsMapper.insert(buYgjResTechnics);
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            //获取表格数据.
            ZxBuWorks zxBuWorks = new ZxBuWorks();
            zxBuWorks.setOrgID(zxBuYgjResTechnics.getOrgID());
            zxBuWorks.setParentID("-1");
            ResponseEntity zxBuWorksNoName = zxBuWorksService.getZxBuWorksNoName(zxBuWorks);
            if(zxBuWorksNoName.isSuccess()){
                return repEntity.ok("sys.data.sava", zxBuWorksNoName.getData());
            }else {
                return repEntity.errorSave();
            }
        }
    }

    @Override
    public ResponseEntity removeRelevanceZxBuYgjResTechnics(ZxBuYgjResTechnics zxBuYgjResTechnics) {
        if(zxBuYgjResTechnics == null){
            return repEntity.ok(new ArrayList<>());
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        //传进来的是 项目清单id,项目id,清单标准库id
        //              billID  ,orgID, standardTechID
        //清除对应的工序所有数据
        int flag = 0;
        ZxBuYgjResTechnics selectZxBuYgjResTechnics = new ZxBuYgjResTechnics();
        selectZxBuYgjResTechnics.setBillID(zxBuYgjResTechnics.getMyBillID());
        List<ZxBuYgjResTechnics> zxBuYgjResTechnicsList = zxBuYgjResTechnicsMapper.selectByZxBuYgjResTechnicsList(selectZxBuYgjResTechnics);
        if (zxBuYgjResTechnicsList != null && zxBuYgjResTechnicsList.size() > 0) {
            List<String> technicsIds =  zxBuYgjResTechnicsList.stream().map(ZxBuYgjResTechnics :: getZxBuYgjResTechnicsId).collect(Collectors.toList());
            ZxBuYgjResTechnics zxBuYgjResTechnics1 = new ZxBuYgjResTechnics();
            zxBuYgjResTechnics1.setModifyUserInfo(userKey, realName);
            zxBuYgjResTechnics1.setBillID(zxBuYgjResTechnics.getBillID());
            zxBuYgjResTechnics1.setOrgID(zxBuYgjResTechnics.getOrgID());
            flag = zxBuYgjResTechnicsMapper.batchDeleteUpdateRemoveRelevanceZxBuYgjResTechnics(technicsIds, zxBuYgjResTechnics1);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            //获取表格数据.
            ZxBuWorks zxBuWorks = new ZxBuWorks();
            zxBuWorks.setOrgID(zxBuYgjResTechnics.getOrgID());
            zxBuWorks.setParentID("-1");
            ResponseEntity zxBuWorksNoName = zxBuWorksService.getZxBuWorksNoName(zxBuWorks);
            if (zxBuWorksNoName.isSuccess()) {
                return repEntity.ok("sys.data.sava", zxBuWorksNoName.getData());
            } else {
                return repEntity.errorSave();
            }
        }
    }

    @Override
    public ResponseEntity updateZxBuYgjResTechnicsList(List<ZxBuYgjResTechnics> zxBuYgjResTechnicsList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        //根据清单id去查询清单数据
        int flag = 0;
        if(CollectionUtil.isNotEmpty(zxBuYgjResTechnicsList)){
            ZxBuYgjResTechnics zxBuYgjResTechnics = new ZxBuYgjResTechnics();
            String billID = zxBuYgjResTechnicsList.get(0).getBillID();
            zxBuYgjResTechnics.setBillID(billID);
            List<ZxBuYgjResTechnics> zxBuYgjResTechnicsList1 = zxBuYgjResTechnicsMapper.selectByZxBuYgjResTechnicsList(zxBuYgjResTechnics);
            //先删
            if(CollectionUtil.isNotEmpty(zxBuYgjResTechnicsList1)){
                zxBuYgjResTechnics.setModifyUserInfo(userKey,realName);
                zxBuYgjResTechnicsMapper.batchDeleteUpdateZxBuYgjResTechnicsByBillID(zxBuYgjResTechnicsList1,zxBuYgjResTechnics);
            }
            //后增
            for (ZxBuYgjResTechnics buYgjResTechnics : zxBuYgjResTechnicsList) {
                buYgjResTechnics.setZxBuYgjResTechnicsId(UuidUtil.generate());
                buYgjResTechnics.setBillID(billID);
                buYgjResTechnics.setCreateUserInfo(userKey, realName);
                flag = zxBuYgjResTechnicsMapper.insert(buYgjResTechnics);
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxBuYgjResTechnicsList);
        }
    }

    @Override
    public ResponseEntity getZxBuYgjResTechnicsAndQDList(ZxBuYgjResTechnics zxBuYgjResTechnics) {
        if (zxBuYgjResTechnics == null) {
            zxBuYgjResTechnics = new ZxBuYgjResTechnics();
        }
        if(StrUtil.isEmpty(zxBuYgjResTechnics.getBillID())){
            return repEntity.ok(new ArrayList<>());
        }
        // 获取数据
        List<ZxBuYgjResTechnics> zxBuYgjResTechnicsList = zxBuYgjResTechnicsMapper.selectByZxBuYgjResTechnicsList(zxBuYgjResTechnics);
        if(CollectionUtil.isNotEmpty(zxBuYgjResTechnicsList)){
            List<ZxBuYgjResTechnics> dbzxBuYgjResTechnicsListBase = zxBuYgjResTechnicsMapper.selectByBaseZxBuYgjResTechnics(zxBuYgjResTechnicsList);
            //关联工序
            List<ZxBuYgjResTechnics> zxBuYgjResTechnicsListNew = new ArrayList<>();
            dbzxBuYgjResTechnicsListBase.parallelStream().collect(Collectors.groupingBy(o->(o.getBillID()),Collectors.toList())).forEach(
                    (id,transfer) -> {
                        transfer.stream().reduce((a,b)-> new ZxBuYgjResTechnics(
                                a.getBillID()
                        )).ifPresent(zxBuYgjResTechnicsListNew::add);
                    }
            );
            if(CollectionUtil.isNotEmpty(zxBuYgjResTechnicsListNew)){
                //查询清单数据
                List<ZxBuYgjResTechnics> dbzxBuYgjResTechnicsListNew = zxBuWorksMapper.selectByZxBuWorksAndzxBuYgjResTechnicsList(zxBuYgjResTechnicsListNew);
                return repEntity.ok(dbzxBuYgjResTechnicsListNew);
            }
        }

        return repEntity.ok(new ArrayList<>());
    }
}
