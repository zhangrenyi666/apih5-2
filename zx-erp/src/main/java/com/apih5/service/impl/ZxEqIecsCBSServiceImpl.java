package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.apih5.framework.constant.SysConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxEqIecsCBSMapper;
import com.apih5.mybatis.pojo.ZxEqIecsCBS;
import com.apih5.service.ZxEqIecsCBSService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqIecsCBSService")
public class ZxEqIecsCBSServiceImpl implements ZxEqIecsCBSService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqIecsCBSMapper zxEqIecsCBSMapper;

    @Override
    public ResponseEntity getZxEqIecsCBSListByCondition(ZxEqIecsCBS zxEqIecsCBS) {
        if (zxEqIecsCBS == null) {
            return repEntity.ok(new ArrayList<>());
        }
        if(StrUtil.isEmpty(zxEqIecsCBS.getOrgID()) && StrUtil.isEmpty(zxEqIecsCBS.getParentID())){
            return repEntity.ok(new ArrayList<>());
        }
        List<ZxEqIecsCBS> zxEqIecsCBSList;
        // 分页查询
        PageHelper.startPage(zxEqIecsCBS.getPage(),zxEqIecsCBS.getLimit());
        // 获取数据
        if(StrUtil.equals(zxEqIecsCBS.getCbsType(),"1")){
            zxEqIecsCBSList = zxEqIecsCBSMapper.selectByZxEqIecsCBSList(zxEqIecsCBS);
            for (ZxEqIecsCBS zxEqIecsCBS1 : zxEqIecsCBSList) {
                ZxEqIecsCBS zxEqIecsCBS2 = new ZxEqIecsCBS();
                zxEqIecsCBS2.setParentID(zxEqIecsCBS1.getIecsCBSID());
                List<ZxEqIecsCBS> zxEqIecsCBS3 = zxEqIecsCBSMapper.selectByZxEqIecsCBSList(zxEqIecsCBS2);
                zxEqIecsCBS1.setChildrenList(zxEqIecsCBS3);
            }
        }else {
            zxEqIecsCBSList = zxEqIecsCBSMapper.selectByZxEqIecsCBSList(zxEqIecsCBS);
        }
        // 得到分页信息
        PageInfo<ZxEqIecsCBS> p = new PageInfo<>(zxEqIecsCBSList);
        return repEntity.okList(zxEqIecsCBSList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqIecsCBSDetails(ZxEqIecsCBS zxEqIecsCBS) {
        if (zxEqIecsCBS == null) {
            zxEqIecsCBS = new ZxEqIecsCBS();
        }
        ZxEqIecsCBS dbZxEqIecsCBS;
        // 获取数据
        if(StrUtil.equals(zxEqIecsCBS.getCbsType(),"1")){
            dbZxEqIecsCBS = zxEqIecsCBSMapper.selectByPrimaryKey(zxEqIecsCBS.getIecsCBSID());
            ZxEqIecsCBS zxEqIecsCBS2 = new ZxEqIecsCBS();
            zxEqIecsCBS2.setParentID(dbZxEqIecsCBS.getIecsCBSID());
            List<ZxEqIecsCBS> zxEqIecsCBS3 = zxEqIecsCBSMapper.selectByZxEqIecsCBSList(zxEqIecsCBS2);
            dbZxEqIecsCBS.setChildrenList(zxEqIecsCBS3);
        }else{
            dbZxEqIecsCBS = zxEqIecsCBSMapper.selectByPrimaryKey(zxEqIecsCBS.getIecsCBSID());
        }
        // 数据存在
        if (dbZxEqIecsCBS != null) {
            return repEntity.ok(dbZxEqIecsCBS);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxEqIecsCBS(ZxEqIecsCBS zxEqIecsCBS) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqIecsCBS.setIecsCBSID(UuidUtil.generate());
        if(!StrUtil.equals(zxEqIecsCBS.getCbsType(),"4")){
            //先查一遍OrgID系统中有没有这条数据,如果没有则增加
            ZxEqIecsCBS zxEqIecsCBSOrg = new ZxEqIecsCBS();
            zxEqIecsCBSOrg.setOrgID(zxEqIecsCBS.getOrgID());
            zxEqIecsCBSOrg.setCbsType("1");
            List<ZxEqIecsCBS> zxEqIecsCBS1 = zxEqIecsCBSMapper.selectByZxEqIecsCBSList(zxEqIecsCBSOrg);
            ZxEqIecsCBS zxEqIecsCBSNO1 = new ZxEqIecsCBS();
            if(zxEqIecsCBS1 == null || zxEqIecsCBS1.size() == 0){
                //新增 项目数据
                zxEqIecsCBSNO1.setOrgID(zxEqIecsCBS.getOrgID());
                zxEqIecsCBSNO1.setName(zxEqIecsCBS.getOrgName());
                zxEqIecsCBSNO1.setIecsCBSID(zxEqIecsCBS.getParentID());
                zxEqIecsCBSNO1.setBsid("0000");
                zxEqIecsCBSNO1.setParentID("-1");
                zxEqIecsCBSNO1.setCbsType("1");
                zxEqIecsCBSNO1.setCreateUserInfo(userKey, realName);
                zxEqIecsCBSMapper.insert(zxEqIecsCBSNO1);
            }
            if(StrUtil.isNotEmpty(zxEqIecsCBSNO1.getIecsCBSID())){
                zxEqIecsCBS.setParentID(zxEqIecsCBSNO1.getIecsCBSID());
            }
            if(StrUtil.equals(zxEqIecsCBS.getCbsType(),"1")){
                // 上一节点的pid_all(包含上一节点id)
                if (StrUtil.isNotEmpty(zxEqIecsCBS.getBsid())) {
                    String pidAll = zxEqIecsCBS.getBsid();
                    zxEqIecsCBS.setBsid(pidAll + "," + zxEqIecsCBS.getIecsCBSID());
                } else {
                    // 新增根节点时情况
                }
            }
        }
        zxEqIecsCBS.setCreateUserInfo(userKey, realName);
        int flag = zxEqIecsCBSMapper.insert(zxEqIecsCBS);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqIecsCBS);
        }
    }

    @Override
    public ResponseEntity updateZxEqIecsCBS(ZxEqIecsCBS zxEqIecsCBS) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqIecsCBS dbzxEqIecsCBS = zxEqIecsCBSMapper.selectByPrimaryKey(zxEqIecsCBS.getIecsCBSID());
        if (dbzxEqIecsCBS != null && StrUtil.isNotEmpty(dbzxEqIecsCBS.getIecsCBSID())) {
           // 所属机构
           dbzxEqIecsCBS.setOrgID(zxEqIecsCBS.getOrgID());
           // 编号
           dbzxEqIecsCBS.setCode(zxEqIecsCBS.getCode());
           // 名称
           dbzxEqIecsCBS.setName(zxEqIecsCBS.getName());
           // 单位
           dbzxEqIecsCBS.setUnit(zxEqIecsCBS.getUnit());
           // 最后修改时间
           dbzxEqIecsCBS.setUpdateTime(zxEqIecsCBS.getUpdateTime());
           // ecbsID
           dbzxEqIecsCBS.setEcbsID(zxEqIecsCBS.getEcbsID());
           // bsid
           dbzxEqIecsCBS.setBsid(zxEqIecsCBS.getBsid());
           // parentID
           dbzxEqIecsCBS.setParentID(zxEqIecsCBS.getParentID());
           // 合同数量
           dbzxEqIecsCBS.setContrQty(zxEqIecsCBS.getContrQty());
           // 变更后数量
           dbzxEqIecsCBS.setAlterQty(zxEqIecsCBS.getAlterQty());
           // 合同合价
           dbzxEqIecsCBS.setContrTotalPrice(zxEqIecsCBS.getContrTotalPrice());
           // 变更后合价
           dbzxEqIecsCBS.setAlterTotalPrice(zxEqIecsCBS.getAlterTotalPrice());
           // cbs类型
           dbzxEqIecsCBS.setCbsType(zxEqIecsCBS.getCbsType());
           // 是否删除
           dbzxEqIecsCBS.setDeleted(zxEqIecsCBS.getDeleted());
           // 备注
           dbzxEqIecsCBS.setRemark(zxEqIecsCBS.getRemark());
           // 明细
           dbzxEqIecsCBS.setCombProp(zxEqIecsCBS.getCombProp());
           // 
           dbzxEqIecsCBS.setPp1(zxEqIecsCBS.getPp1());
           // 
           dbzxEqIecsCBS.setPp2(zxEqIecsCBS.getPp2());
           // 
           dbzxEqIecsCBS.setPp3(zxEqIecsCBS.getPp3());
           // 
           dbzxEqIecsCBS.setPp4(zxEqIecsCBS.getPp4());
           // 
           dbzxEqIecsCBS.setPp5(zxEqIecsCBS.getPp5());
           // 
           dbzxEqIecsCBS.setPp6(zxEqIecsCBS.getPp6());
           // 
           dbzxEqIecsCBS.setPp7(zxEqIecsCBS.getPp7());
           // 
           dbzxEqIecsCBS.setPp8(zxEqIecsCBS.getPp8());
           // 
           dbzxEqIecsCBS.setPp9(zxEqIecsCBS.getPp9());
           // 
           dbzxEqIecsCBS.setPp10(zxEqIecsCBS.getPp10());
           // 
           dbzxEqIecsCBS.setEditTime(zxEqIecsCBS.getEditTime());
           // 共通
           dbzxEqIecsCBS.setModifyUserInfo(userKey, realName);
           flag = zxEqIecsCBSMapper.updateByPrimaryKey(dbzxEqIecsCBS);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqIecsCBS);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqIecsCBS(List<ZxEqIecsCBS> zxEqIecsCBSList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqIecsCBSList != null && zxEqIecsCBSList.size() > 0) {
           ZxEqIecsCBS zxEqIecsCBS = new ZxEqIecsCBS();
           zxEqIecsCBS.setModifyUserInfo(userKey, realName);
           flag = zxEqIecsCBSMapper.batchDeleteUpdateZxEqIecsCBS(zxEqIecsCBSList, zxEqIecsCBS);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqIecsCBSList);
        }
    }

    @Override
    public ResponseEntity getZxEqIecsCBSOrgId(ZxEqIecsCBS zxEqIecsCBS) {
        if (zxEqIecsCBS == null) {
            zxEqIecsCBS = new ZxEqIecsCBS();
        }
        if(StrUtil.isNotEmpty(zxEqIecsCBS.getIecsCBSID())){
            // 分页查询
            PageHelper.startPage(zxEqIecsCBS.getPage(),zxEqIecsCBS.getLimit());
            // 获取数据
            List<ZxEqIecsCBS> zxEqIecsCBSList = zxEqIecsCBSMapper.selectByZxEqIecsCBSListOrgId(zxEqIecsCBS);
            // 得到分页信息
            PageInfo<ZxEqIecsCBS> p = new PageInfo<>(zxEqIecsCBSList);
            return repEntity.okList(zxEqIecsCBSList, p.getTotal());
        }else if(StrUtil.isEmpty(zxEqIecsCBS.getOrgID())){
            return repEntity.ok(new ArrayList<>());
        }else {
            // 分页查询
            PageHelper.startPage(zxEqIecsCBS.getPage(),zxEqIecsCBS.getLimit());
            // 获取数据
            List<ZxEqIecsCBS> zxEqIecsCBSList = zxEqIecsCBSMapper.selectByZxEqIecsCBSListOrgId(zxEqIecsCBS);
            // 得到分页信息
            PageInfo<ZxEqIecsCBS> p = new PageInfo<>(zxEqIecsCBSList);

            return repEntity.okList(zxEqIecsCBSList, p.getTotal());
        }
    }

    @Override
    public ResponseEntity getZxEqIecsCBSTree(ZxEqIecsCBS zxEqIecsCBS) {
        if(zxEqIecsCBS == null) {
            zxEqIecsCBS = new ZxEqIecsCBS();
        }
        zxEqIecsCBS.setCbsType("1");

        //获取项目信息
        String orgID = zxEqIecsCBS.getOrgID();
        String orgName = zxEqIecsCBS.getOrgName();
//        getSysProjectDetail
        List<ZxEqIecsCBS> zxEqIecsCBS1 = zxEqIecsCBSMapper.selectByZxEqIecsCBSList(zxEqIecsCBS);
        if(zxEqIecsCBS1 == null || zxEqIecsCBS1.size() ==0){
            ZxEqIecsCBS zxEqIecsCBSNO1 = new ZxEqIecsCBS();
            zxEqIecsCBSNO1.setOrgID(orgID);
            zxEqIecsCBSNO1.setName(orgName);
            zxEqIecsCBSNO1.setIecsCBSID(UuidUtil.createUUID());
            zxEqIecsCBSNO1.setBsid("0000");
            zxEqIecsCBSNO1.setParentID("-1");
            zxEqIecsCBSNO1.setCbsType("1");
            zxEqIecsCBS1.add(0,zxEqIecsCBSNO1);
        }
        JSONArray jsonArray = listToTree(new JSONArray(zxEqIecsCBS1), "iecsCBSID", "parentID", "name");
        return repEntity.ok(jsonArray);
    }

    private static JSONArray listToTree(JSONArray arr, String id, String parentID, String name) {
        String child = "childrenList";
        JSONArray r = new JSONArray();
        JSONArray newArr = new JSONArray();
        JSONObject hash = new JSONObject();
        for (int i = 0; i < arr.size(); i++) {
            JSONObject json = (JSONObject) arr.get(i);
            JSONObject newJSONObject = new JSONObject();
            newJSONObject.set(id, json.get(id));
            newJSONObject.set(parentID, json.get(parentID));
            newJSONObject.set(name, json.get(name));
            newJSONObject.set("orgID", json.get("orgID"));
            newJSONObject.set("code", json.get("code"));
            newJSONObject.set("bsid", json.get("bsid"));
            newJSONObject.set("unit", json.get("unit"));
            newJSONObject.set("updateTime", json.get("updateTime"));
            newJSONObject.set("ecbsID", json.get("ecbsID"));
            newJSONObject.set("contrQty", json.get("contrQty"));
            newJSONObject.set("alterQty", json.get("alterQty"));
            newJSONObject.set("contrTotalPrice", json.get("contrTotalPrice"));
            newJSONObject.set("alterTotalPrice", json.get("alterTotalPrice"));
            newJSONObject.set("cbsType", json.get("cbsType"));
            newJSONObject.set("deleted", json.get("deleted"));
            newJSONObject.set("remark", json.get("remark"));
            newJSONObject.set("combProp", json.get("combProp"));
            newJSONObject.set("pp1", json.get("pp1"));
            newJSONObject.set("pp2", json.get("pp2"));
            newJSONObject.set("pp3", json.get("pp3"));
            newJSONObject.set("pp4", json.get("pp4"));
            newJSONObject.set("pp5", json.get("pp5"));
            newJSONObject.set("pp6", json.get("pp6"));
            newJSONObject.set("pp7", json.get("pp7"));
            newJSONObject.set("pp8", json.get("pp8"));
            newJSONObject.set("pp9", json.get("pp9"));
            newJSONObject.set("pp10", json.get("pp10"));
            newJSONObject.set("editTime", json.get("editTime"));
            newJSONObject.set("delFlag", json.get("delFlag"));
            newJSONObject.set("createTime", json.get("createTime"));
            newJSONObject.set("createUser", json.get("createUser"));
            newJSONObject.set("createUserName", json.get("createUserName"));
            newJSONObject.set("modifyTime", json.get("modifyTime"));
            newJSONObject.set("modifyUser", json.get("modifyUser"));
            newJSONObject.set("modifyUserName", json.get("modifyUserName"));
            hash.set(json.getStr(id), newJSONObject);
            newArr.add(newJSONObject);
        }

        for (int j = 0; j < newArr.size(); j++) {
            JSONObject aVal = newArr.getJSONObject(Integer.valueOf(j));
            JSONObject hashVP = hash.getJSONObject(aVal.getStr(parentID));
            if (hashVP != null) {
                if (hashVP.get(child) != null) {
                    JSONArray ch = hashVP.getJSONArray(child);
                    ch.add(aVal);
                    hashVP.put(child, ch);
                } else {
                    JSONArray ch = new JSONArray();
                    ch.add(aVal);
                    hashVP.put(child, ch);
                }
            } else
                r.add(aVal);
        }
        return r;
    }

    @Override
    public ResponseEntity getZxEqIecsCBSPickingList(ZxEqIecsCBS zxEqIecsCBS) {
        if (zxEqIecsCBS == null) {
            return repEntity.ok(new ArrayList<>());
        }
        //todo: 忘记了这块为啥要这样写 2-22
//        if(StrUtil.isEmpty(zxEqIecsCBS.getOrgID()) && StrUtil.isEmpty(zxEqIecsCBS.getParentID())){
        if(StrUtil.isEmpty(zxEqIecsCBS.getOrgID())){
            return repEntity.ok(new ArrayList<>());
        }
        // 分页查询
        PageHelper.startPage(zxEqIecsCBS.getPage(),zxEqIecsCBS.getLimit());
        // 获取数据
        List<ZxEqIecsCBS> zxEqIecsCBSList = zxEqIecsCBSMapper.selectByZxEqIecsCBSPickingList(zxEqIecsCBS);
        // 得到分页信息
        PageInfo<ZxEqIecsCBS> p = new PageInfo<>(zxEqIecsCBSList);
        return repEntity.okList(zxEqIecsCBSList, p.getTotal());
    }


}
