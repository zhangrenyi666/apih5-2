package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONObject;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.dao.ZjTzProjectAndEmployeeMapper;
import com.apih5.mybatis.pojo.*;
import com.apih5.service.ZjTzPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzProjectEmployeeMapper;
import com.apih5.service.ZjTzProjectEmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzProjectEmployeeService")
public class ZjTzProjectEmployeeServiceImpl implements ZjTzProjectEmployeeService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzProjectEmployeeMapper zjTzProjectEmployeeMapper;

    @Autowired
    private ZjTzProManageMapper zjTzProManageMapper;

    @Autowired
    private ZjTzFileMapper zjTzFileMapper;

    @Autowired
    private ZjTzProjectAndEmployeeMapper zjTzProjectAndEmployeeMapper;

    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    @Override
    public ResponseEntity getZjTzProjectEmployeeListByCondition(ZjTzProjectEmployee zjTzProjectEmployee) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        if (zjTzProjectEmployee == null) {
            zjTzProjectEmployee = new ZjTzProjectEmployee();
        }
        // ????????????
        PageHelper.startPage(zjTzProjectEmployee.getPage(),zjTzProjectEmployee.getLimit());
        // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // ???????????????????????????????????????sql?????????
            if(StrUtil.equals("all", zjTzProjectEmployee.getProjectId(), true)) {
                zjTzProjectEmployee.setProjectId("");
                zjTzProjectEmployee.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // ???????????????
            if(StrUtil.equals("all", zjTzProjectEmployee.getProjectId(), true)) {
                zjTzProjectEmployee.setProjectId("");
            }
        }
        if(CollectionUtil.isNotEmpty(zjTzProjectEmployee.getTimeList())){
            zjTzProjectEmployee.setTime(null);
        }
        // ????????????(???list???)
        List<ZjTzProjectEmployee> zjTzProjectEmployeeList = zjTzProjectEmployeeMapper.selectByZjTzProjectEmployeeList(zjTzProjectEmployee);
        // ??????????????????
        PageInfo<ZjTzProjectEmployee> p = new PageInfo<>(zjTzProjectEmployeeList);
        return repEntity.okList(zjTzProjectEmployeeList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzProjectEmployeeDetails(ZjTzProjectEmployee zjTzProjectEmployee) {

        if (zjTzProjectEmployee == null) {
            zjTzProjectEmployee = new ZjTzProjectEmployee();
        }
        // ????????????
        ZjTzProjectEmployee dbZjTzProjectEmployee = zjTzProjectEmployeeMapper.selectByPrimaryKey(zjTzProjectEmployee.getProjectEmployeeId());
        // ????????????
        if (dbZjTzProjectEmployee != null) {
            return repEntity.ok(dbZjTzProjectEmployee);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZjTzProjectEmployee(ZjTzProjectEmployee zjTzProjectEmployee) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzProjectEmployee.setProjectEmployeeId(UuidUtil.generate());
        zjTzProjectEmployee.setCreateUserInfo(userKey, realName);
        int flag = zjTzProjectEmployeeMapper.insert(zjTzProjectEmployee);

        // ??????list
        List<ZjTzFile> zjTzFileList = zjTzProjectEmployee.getZjTzFileListProjectEmployee();
        if(zjTzFileList != null && zjTzFileList.size()>0) {
            for(ZjTzFile zjTzFile:zjTzFileList) {
                zjTzFile.setUid(UuidUtil.generate());
                zjTzFile.setOtherId(zjTzProjectEmployee.getProjectEmployeeId());
                zjTzFile.setCreateUserInfo(userKey, realName);
                zjTzFileMapper.insert(zjTzFile);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzProjectEmployee);
        }
    }

    @Override
    public ResponseEntity updateZjTzProjectEmployee(ZjTzProjectEmployee zjTzProjectEmployee) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);

        int flag = 0;
        ZjTzProjectEmployee dbzjTzProjectEmployee = zjTzProjectEmployeeMapper.selectByPrimaryKey(zjTzProjectEmployee.getProjectEmployeeId());
        if (dbzjTzProjectEmployee != null && StrUtil.isNotEmpty(dbzjTzProjectEmployee.getProjectEmployeeId())) {
           // ??????id
           dbzjTzProjectEmployee.setProjectId(zjTzProjectEmployee.getProjectId());
           // ??????
           dbzjTzProjectEmployee.setYearAndMonth(zjTzProjectEmployee.getYearAndMonth());
           // ??????
           dbzjTzProjectEmployee.setEmployeeNumber(zjTzProjectEmployee.getEmployeeNumber());
           // ????????????
           dbzjTzProjectEmployee.setRegisterUser(zjTzProjectEmployee.getRegisterUser());
           // ????????????
           dbzjTzProjectEmployee.setRegisterTime(zjTzProjectEmployee.getRegisterTime());
           // ????????????
           dbzjTzProjectEmployee.setRegisterUnits(zjTzProjectEmployee.getRegisterUnits());
           // ????????????
           dbzjTzProjectEmployee.setAuditStatus(zjTzProjectEmployee.getAuditStatus());
           // ??????
           dbzjTzProjectEmployee.setRemarks(zjTzProjectEmployee.getRemarks());
           // ??????
           dbzjTzProjectEmployee.setModifyUserInfo(userKey, realName);
           flag = zjTzProjectEmployeeMapper.updateByPrimaryKey(dbzjTzProjectEmployee);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzProjectEmployee);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzProjectEmployee(List<ZjTzProjectEmployee> zjTzProjectEmployeeList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzProjectEmployeeList != null && zjTzProjectEmployeeList.size() > 0) {
            for (ZjTzProjectEmployee zjTzProjectEmployee : zjTzProjectEmployeeList) {
                // ????????????
                ZjTzProjectAndEmployee zjTzProjectAndEmployee = new ZjTzProjectAndEmployee();
                zjTzProjectAndEmployee.setProjectEmployeeId(zjTzProjectEmployee.getProjectEmployeeId());
                List<ZjTzProjectAndEmployee> zjTzProjectAndEmployees = zjTzProjectAndEmployeeMapper.selectByZjTzProjectAndEmployeeList(zjTzProjectAndEmployee);
                if(zjTzProjectAndEmployees != null && zjTzProjectAndEmployees.size()>0) {
                    zjTzProjectAndEmployee.setModifyUserInfo(userKey, realName);
                    zjTzProjectAndEmployeeMapper.batchDeleteUpdateZjTzProjectAndEmployee(zjTzProjectAndEmployees, zjTzProjectAndEmployee);
                }
            }

           ZjTzProjectEmployee zjTzProjectEmployee = new ZjTzProjectEmployee();
           zjTzProjectEmployee.setModifyUserInfo(userKey, realName);
           flag = zjTzProjectEmployeeMapper.batchDeleteUpdateZjTzProjectEmployee(zjTzProjectEmployeeList, zjTzProjectEmployee);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzProjectEmployeeList);
        }
    }

    @Override
    public ResponseEntity checkZjTzProjectEmployee(List<ZjTzProjectEmployee> zjTzProjectEmployeeList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzProjectEmployeeList != null && zjTzProjectEmployeeList.size() > 0) {
            for (ZjTzProjectEmployee zjTzProjectEmployee : zjTzProjectEmployeeList) {
                if(StrUtil.equals(zjTzProjectEmployee.getAuditStatus(), "?????????")) {
                    return repEntity.layerMessage("no", "??????????????????????????????????????????");
                }
            }
            ZjTzProjectEmployee zjTzProjectEmployee = new ZjTzProjectEmployee();
            zjTzProjectEmployee.setAuditStatus("?????????");
            zjTzProjectEmployee.setModifyUserInfo(userKey, realName);
            flag = zjTzProjectEmployeeMapper.checkZjTzProjectEmployee(zjTzProjectEmployeeList, zjTzProjectEmployee);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzProjectEmployeeList);
        }
    }

    @Override
    public ResponseEntity recallZjTzProjectEmployee(List<ZjTzProjectEmployee> zjTzProjectEmployeeList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzProjectEmployeeList != null && zjTzProjectEmployeeList.size() > 0) {
            for (ZjTzProjectEmployee zjTzProjectEmployee : zjTzProjectEmployeeList) {
                if(StrUtil.equals(zjTzProjectEmployee.getAuditStatus(), "?????????")) {
                    return repEntity.layerMessage("no", "???????????????????????????????????????");
                }
            }
            ZjTzProjectEmployee zjTzProjectEmployee = new ZjTzProjectEmployee();
            zjTzProjectEmployee.setAuditStatus("?????????");
            zjTzProjectEmployee.setModifyUserInfo(userKey, realName);
            flag = zjTzProjectEmployeeMapper.recallZjTzProjectEmployee(zjTzProjectEmployeeList, zjTzProjectEmployee);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzProjectEmployeeList);
        }
    }

    @Override
    public ResponseEntity recallAddZjTzProjectEmployee(ZjTzProjectEmployee zjTzProjectEmployee) {
        //(check??????????????????,????????????????????????????????????????????????)
        List<ZjTzProjectAndEmployee> projectAndEmployeeList = zjTzProjectEmployee.getProjectAndEmployeeList();
        //????????????????????????????????????
        ZjTzProjectAndEmployee zjTzProjectAndEmployee2 = new ZjTzProjectAndEmployee();
        zjTzProjectAndEmployee2.setProjectEmployeeId(zjTzProjectEmployee.getProjectEmployeeId());
        List<ZjTzProjectAndEmployee> zjTzProjectAndEmployeeList = zjTzProjectAndEmployeeMapper.selectByZjTzProjectAndEmployeeList(zjTzProjectAndEmployee2);
        zj:for (ZjTzProjectAndEmployee zjTzProjectAndEmployee : projectAndEmployeeList) {
            zjTzProjectAndEmployee.setProjectId(zjTzProjectEmployee.getProjectId());
            //????????????????????????????????????
            //??????????????????????????????()
            for (ZjTzProjectAndEmployee tzProjectAndEmployee : zjTzProjectAndEmployeeList) {
                if(StrUtil.equals(zjTzProjectAndEmployee.getProjectAndEmployeeId(),tzProjectAndEmployee.getProjectAndEmployeeId())){
                    continue zj;
                }
            }
            ZjTzProjectAndEmployee zjTzProjectAndEmployee1 = zjTzProjectAndEmployeeMapper.selectByProjectIdAndEmployeeInfoId(zjTzProjectAndEmployee);
            if(zjTzProjectAndEmployee1!=null){
                return repEntity.layerMessage("no","???????????????????????????????????????");
            }
        }

        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzProjectEmployee dbzjTzProjectEmployee = zjTzProjectEmployeeMapper.selectByPrimaryKey(zjTzProjectEmployee.getProjectEmployeeId());
        if (dbzjTzProjectEmployee != null && StrUtil.isNotEmpty(dbzjTzProjectEmployee.getProjectEmployeeId())) {
            // ??????
            dbzjTzProjectEmployee.setEmployeeNumber(projectAndEmployeeList.size());
            flag = zjTzProjectEmployeeMapper.updateByPrimaryKey(dbzjTzProjectEmployee);
        }
        //???????????????
        ZjTzProjectAndEmployee zjTzProjectAndEmployee = new ZjTzProjectAndEmployee();
        zjTzProjectAndEmployee.setModifyUserInfo(userKey, realName);
        //??????
        zjTzProjectAndEmployeeMapper.DeleteUpdateZjTzProjectAndEmployee(zjTzProjectEmployee,zjTzProjectAndEmployee);
        //??????
        if(projectAndEmployeeList!=null){
            for (ZjTzProjectAndEmployee tzProjectAndEmployee : projectAndEmployeeList) {
                tzProjectAndEmployee.setProjectEmployeeId(zjTzProjectEmployee.getProjectEmployeeId());
                tzProjectAndEmployee.setProjectId(zjTzProjectEmployee.getProjectId());
                tzProjectAndEmployee.setProjectAndEmployeeId(UuidUtil.generate());
                tzProjectAndEmployee.setCreateUserInfo(userKey, realName);
                zjTzProjectAndEmployeeMapper.insert(tzProjectAndEmployee);
            }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzProjectEmployee);
        }
    }








}
