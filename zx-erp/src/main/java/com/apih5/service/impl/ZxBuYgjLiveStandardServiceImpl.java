package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONArray;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.ConvertUtil;
import com.apih5.mybatis.pojo.ZxBuProjectTypeCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxBuYgjLiveStandardMapper;
import com.apih5.mybatis.pojo.ZxBuYgjLiveStandard;
import com.apih5.service.ZxBuYgjLiveStandardService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxBuYgjLiveStandardService")
public class ZxBuYgjLiveStandardServiceImpl implements ZxBuYgjLiveStandardService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxBuYgjLiveStandardMapper zxBuYgjLiveStandardMapper;

    @Override
    public ResponseEntity getZxBuYgjLiveStandardListByCondition(ZxBuYgjLiveStandard zxBuYgjLiveStandard) {
        if (zxBuYgjLiveStandard == null) {
            zxBuYgjLiveStandard = new ZxBuYgjLiveStandard();
        }
        // 分页查询
        PageHelper.startPage(zxBuYgjLiveStandard.getPage(),zxBuYgjLiveStandard.getLimit());
        // 获取数据
        List<ZxBuYgjLiveStandard> zxBuYgjLiveStandardList = zxBuYgjLiveStandardMapper.selectByZxBuYgjLiveStandardList(zxBuYgjLiveStandard);
        // 得到分页信息
        PageInfo<ZxBuYgjLiveStandard> p = new PageInfo<>(zxBuYgjLiveStandardList);

        return repEntity.okList(zxBuYgjLiveStandardList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxBuYgjLiveStandardDetail(ZxBuYgjLiveStandard zxBuYgjLiveStandard) {
        if (zxBuYgjLiveStandard == null) {
            zxBuYgjLiveStandard = new ZxBuYgjLiveStandard();
        }
        // 获取数据
        ZxBuYgjLiveStandard dbZxBuYgjLiveStandard = zxBuYgjLiveStandardMapper.selectByPrimaryKey(zxBuYgjLiveStandard.getZxBuYgjLiveStandardId());
        // 数据存在
        if (dbZxBuYgjLiveStandard != null) {
            return repEntity.ok(dbZxBuYgjLiveStandard);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxBuYgjLiveStandard(ZxBuYgjLiveStandard zxBuYgjLiveStandard) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxBuYgjLiveStandard.setCreateUserInfo(userKey, realName);
        if (StrUtil.isEmpty(zxBuYgjLiveStandard.getZxBuYgjLiveStandardId())) {
//            return repEntity.ok(new ArrayList<>());
            return repEntity.layerMessage("no", "id不能为空！");
        }
        // treeNode处理
        //有父id
        // 同级新增
        if (StrUtil.isNotEmpty(zxBuYgjLiveStandard.getZxBuYgjLiveStandardId())) {
            //取出父id下 最后一个treeCode
            ZxBuYgjLiveStandard zxBuYgjLiveStandard1 = new ZxBuYgjLiveStandard();
            zxBuYgjLiveStandard1.setParentID(zxBuYgjLiveStandard.getZxBuYgjLiveStandardId());
            List<ZxBuYgjLiveStandard> zxBuYgjLiveStandards = zxBuYgjLiveStandardMapper.selectByZxBuYgjLiveStandardList(zxBuYgjLiveStandard1);
            if (zxBuYgjLiveStandards == null || zxBuYgjLiveStandards.size() == 0) {
                //如果数据库没有他就是第一个值
                zxBuYgjLiveStandard.setTreeCode(zxBuYgjLiveStandard.getTreeCode() +"-"+ "1000");
            }else {
                //如果数据库有,就取最后一个值
                String treeCode = zxBuYgjLiveStandards.get(zxBuYgjLiveStandards.size() - 1).getTreeCode();
                BigDecimal intTreeCode = new BigDecimal(treeCode.substring(treeCode.length()-4));
                String s = CalcUtils.calcAdd(intTreeCode, (new BigDecimal(1))).toString();
                String strs = "";
                if(s.length() == 1){
                    strs = treeCode.substring(0,treeCode.length()-4) + "000"+ s;
                }else if(s.length() == 2){
                    strs = treeCode.substring(0,treeCode.length()-4) + "00"+ s;
                }else if(s.length() == 3){
                    strs = treeCode.substring(0,treeCode.length()-4) + "0"+ s;
                }else if(s.length() == 4){
                    strs = treeCode.substring(0,treeCode.length()-4) + s;
                }
                zxBuYgjLiveStandard.setTreeCode(strs);
            }
        }
        zxBuYgjLiveStandard.setParentID(zxBuYgjLiveStandard.getZxBuYgjLiveStandardId());
        zxBuYgjLiveStandard.setZxBuYgjLiveStandardId(UuidUtil.generate());
        //在这里添加是叶子节点
        zxBuYgjLiveStandard.setLeaf(1);
        zxBuYgjLiveStandard.setProjType(zxBuYgjLiveStandard.getProjType());
        int flag = zxBuYgjLiveStandardMapper.insert(zxBuYgjLiveStandard);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            // 修改父节点IsLeaf为0
            ZxBuYgjLiveStandard updateZxBuYgjLiveStandard = new ZxBuYgjLiveStandard();
            updateZxBuYgjLiveStandard.setZxBuYgjLiveStandardId(zxBuYgjLiveStandard.getParentID());
            //先查
            ZxBuYgjLiveStandard zxBuYgjLiveStandard1 = zxBuYgjLiveStandardMapper.selectByPrimaryKey(updateZxBuYgjLiveStandard.getZxBuYgjLiveStandardId());
            zxBuYgjLiveStandard1.setLeaf(0);
            zxBuYgjLiveStandardMapper.updateByPrimaryKey(zxBuYgjLiveStandard1);
            return repEntity.ok("sys.data.sava", updateZxBuYgjLiveStandard);
        }
    }

    @Override
    public ResponseEntity updateZxBuYgjLiveStandard(ZxBuYgjLiveStandard zxBuYgjLiveStandard) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxBuYgjLiveStandard dbZxBuYgjLiveStandard = zxBuYgjLiveStandardMapper.selectByPrimaryKey(zxBuYgjLiveStandard.getZxBuYgjLiveStandardId());
        if (dbZxBuYgjLiveStandard != null && StrUtil.isNotEmpty(dbZxBuYgjLiveStandard.getZxBuYgjLiveStandardId())) {
           // 父节点id
           dbZxBuYgjLiveStandard.setParentID(zxBuYgjLiveStandard.getParentID());
           // 机构ID
           dbZxBuYgjLiveStandard.setOrgID(zxBuYgjLiveStandard.getOrgID());
           // 项目类型
           dbZxBuYgjLiveStandard.setProjType(zxBuYgjLiveStandard.getProjType());
           // 树形编码
           dbZxBuYgjLiveStandard.setTreeCode(zxBuYgjLiveStandard.getTreeCode());
           // 是否叶子
           dbZxBuYgjLiveStandard.setLeaf(zxBuYgjLiveStandard.getLeaf());
           // 名称
           dbZxBuYgjLiveStandard.setName(zxBuYgjLiveStandard.getName());
           // 节点类型
           dbZxBuYgjLiveStandard.setNodeType(zxBuYgjLiveStandard.getNodeType());
           // 单位
           dbZxBuYgjLiveStandard.setUnit(zxBuYgjLiveStandard.getUnit());
           // 数量
           dbZxBuYgjLiveStandard.setCount(zxBuYgjLiveStandard.getCount());
           // 不含税单价/工期(月)
           dbZxBuYgjLiveStandard.setPrice(zxBuYgjLiveStandard.getPrice());
           // 不含税金额(元)/金额(元)
           dbZxBuYgjLiveStandard.setMoney(zxBuYgjLiveStandard.getMoney());
           // 是否生效
           dbZxBuYgjLiveStandard.setActivity(zxBuYgjLiveStandard.getActivity());
           // 生效日期
           dbZxBuYgjLiveStandard.setActivityDate(zxBuYgjLiveStandard.getActivityDate());
           // 编号
           dbZxBuYgjLiveStandard.setPp1(zxBuYgjLiveStandard.getPp1());
           // 
           dbZxBuYgjLiveStandard.setPp2(zxBuYgjLiveStandard.getPp2());
           // 
           dbZxBuYgjLiveStandard.setPp3(zxBuYgjLiveStandard.getPp3());
           // 
           dbZxBuYgjLiveStandard.setPp4(zxBuYgjLiveStandard.getPp4());
           // 
           dbZxBuYgjLiveStandard.setPp5(zxBuYgjLiveStandard.getPp5());
           // 
           dbZxBuYgjLiveStandard.setPp6(zxBuYgjLiveStandard.getPp6());
           // 
           dbZxBuYgjLiveStandard.setPp7(zxBuYgjLiveStandard.getPp7());
           // 
           dbZxBuYgjLiveStandard.setPp8(zxBuYgjLiveStandard.getPp8());
           // 
           dbZxBuYgjLiveStandard.setPp9(zxBuYgjLiveStandard.getPp9());
           // 
           dbZxBuYgjLiveStandard.setPp10(zxBuYgjLiveStandard.getPp10());
           // 税金单价/标准
           dbZxBuYgjLiveStandard.setPriceTax(zxBuYgjLiveStandard.getPriceTax());
           // 进项税金(元)
           dbZxBuYgjLiveStandard.setMoneyTax(zxBuYgjLiveStandard.getMoneyTax());
           // 备注
           dbZxBuYgjLiveStandard.setRemarks(zxBuYgjLiveStandard.getRemarks());
           // 排序
           dbZxBuYgjLiveStandard.setSort(zxBuYgjLiveStandard.getSort());
           // 共通
           dbZxBuYgjLiveStandard.setModifyUserInfo(userKey, realName);
           flag = zxBuYgjLiveStandardMapper.updateByPrimaryKey(dbZxBuYgjLiveStandard);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxBuYgjLiveStandard);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxBuYgjLiveStandard(List<ZxBuYgjLiveStandard> zxBuYgjLiveStandardList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxBuYgjLiveStandardList != null && zxBuYgjLiveStandardList.size() > 0) {
            for (ZxBuYgjLiveStandard zxBuYgjLiveStandard : zxBuYgjLiveStandardList) {
                ZxBuYgjLiveStandard zxBuYgjLiveStandard1 = new ZxBuYgjLiveStandard();
                zxBuYgjLiveStandard1.setParentID(zxBuYgjLiveStandard.getZxBuYgjLiveStandardId());
                List<ZxBuYgjLiveStandard> zxBuYgjLiveStandards = zxBuYgjLiveStandardMapper.selectByZxBuYgjLiveStandardList(zxBuYgjLiveStandard1);
                //子节点
                if(zxBuYgjLiveStandards != null && zxBuYgjLiveStandards.size() >0) {
                    return repEntity.layerMessage("no", "请先删除子节点");
                }
            }
           ZxBuYgjLiveStandard zxBuYgjLiveStandard = new ZxBuYgjLiveStandard();
           zxBuYgjLiveStandard.setModifyUserInfo(userKey, realName);
           flag = zxBuYgjLiveStandardMapper.batchDeleteUpdateZxBuYgjLiveStandard(zxBuYgjLiveStandardList, zxBuYgjLiveStandard);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            for (ZxBuYgjLiveStandard zxBuYgjLiveStandard : zxBuYgjLiveStandardList) {
                // 查询是否有子节点
                ZxBuYgjLiveStandard selectZxBuYgjLiveStandard = new ZxBuYgjLiveStandard();
                selectZxBuYgjLiveStandard.setParentID(zxBuYgjLiveStandard.getParentID());
                List<ZxBuYgjLiveStandard> zxBuYgjLiveStandardsList = zxBuYgjLiveStandardMapper.selectByZxBuYgjLiveStandardList(selectZxBuYgjLiveStandard);
                if (zxBuYgjLiveStandardsList == null || zxBuYgjLiveStandardsList.size() == 0) {
                    // 无根节点修改 IsLeaf为1
                    ZxBuYgjLiveStandard updateZxBuYgjLiveStandard = new ZxBuYgjLiveStandard();
                    updateZxBuYgjLiveStandard.setLeaf(1);
                    updateZxBuYgjLiveStandard.setZxBuYgjLiveStandardId(zxBuYgjLiveStandard.getParentID());
                    updateZxBuYgjLiveStandard.setModifyUserInfo(userKey, realName);
                    flag = zxBuYgjLiveStandardMapper.updateZxBuYgjLiveStandard(updateZxBuYgjLiveStandard);
                    if (flag == 0) {
                        return repEntity.errorSave();
                    }
                }
            }
            return repEntity.ok("sys.data.delete",zxBuYgjLiveStandardList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public ResponseEntity getZxBuYgjLiveStandardTree(ZxBuYgjLiveStandard zxBuYgjLiveStandard) {
        if (zxBuYgjLiveStandard == null) {
            zxBuYgjLiveStandard = new ZxBuYgjLiveStandard();
        }
        if (StrUtil.isEmpty(zxBuYgjLiveStandard.getProjType())) {
            return repEntity.ok(new ArrayList<>());
//            return repEntity.layerMessage("no", "项目ID不能为空！");
        }
        // 获取数据
        List<ZxBuYgjLiveStandard> zxBuYgjLiveStandardsList = zxBuYgjLiveStandardMapper.selectByZxBuYgjLiveStandardList(zxBuYgjLiveStandard);
        JSONArray jsonArray = ConvertUtil.listToTree(new JSONArray(zxBuYgjLiveStandardsList), "zxBuYgjLiveStandardId", "parentID", "name", "pp1");
        return repEntity.ok(jsonArray);
    }

    @Override
    public ResponseEntity getZxBuYgjLiveStandardTreeList(ZxBuYgjLiveStandard zxBuYgjLiveStandard) {
        if (StrUtil.isEmpty(zxBuYgjLiveStandard.getZxBuYgjLiveStandardId())) {
            return repEntity.ok(new ArrayList<>());
        }
        if (StrUtil.isEmpty(zxBuYgjLiveStandard.getProjType())) {
            return repEntity.ok(new ArrayList<>());
        }
        // 分页查询
        PageHelper.startPage(zxBuYgjLiveStandard.getPage(),zxBuYgjLiveStandard.getLimit());
        List<ZxBuYgjLiveStandard> zxBuYgjLiveStandardList = new ArrayList<>();
        if (StrUtil.equals(zxBuYgjLiveStandard.getParentID(),"0")) {
            zxBuYgjLiveStandard.setZxBuYgjLiveStandardId(null);
            zxBuYgjLiveStandardList = zxBuYgjLiveStandardMapper.selectByZxBuYgjLiveStandardListData(zxBuYgjLiveStandard);
        }else {
            zxBuYgjLiveStandardList = zxBuYgjLiveStandardMapper.selectByZxBuYgjLiveStandardListData(zxBuYgjLiveStandard);
        }
        // 得到分页信息
        PageInfo<ZxBuYgjLiveStandard> p = new PageInfo<>(zxBuYgjLiveStandardList);
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (ZxBuYgjLiveStandard zxBuYgjLiveStandard1 : zxBuYgjLiveStandardList) {
            Map<String, Object> map = BeanUtil.beanToMap(zxBuYgjLiveStandard1);
            map.remove("children");
            mapList.add(map);
        }
        return repEntity.okList(zxBuYgjLiveStandardList, p.getTotal());
    }


}
