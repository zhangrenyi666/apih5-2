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
        // 分页查询
        PageHelper.startPage(zjTzProjectEmployee.getPage(),zjTzProjectEmployee.getLimit());
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzProjectEmployee.getProjectId(), true)) {
                zjTzProjectEmployee.setProjectId("");
                zjTzProjectEmployee.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
            if(StrUtil.equals("all", zjTzProjectEmployee.getProjectId(), true)) {
                zjTzProjectEmployee.setProjectId("");
            }
        }
        if(CollectionUtil.isNotEmpty(zjTzProjectEmployee.getTimeList())){
            zjTzProjectEmployee.setTime(null);
        }
        // 获取数据(带list人)
        List<ZjTzProjectEmployee> zjTzProjectEmployeeList = zjTzProjectEmployeeMapper.selectByZjTzProjectEmployeeList(zjTzProjectEmployee);
        // 得到分页信息
        PageInfo<ZjTzProjectEmployee> p = new PageInfo<>(zjTzProjectEmployeeList);
        return repEntity.okList(zjTzProjectEmployeeList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzProjectEmployeeDetails(ZjTzProjectEmployee zjTzProjectEmployee) {

        if (zjTzProjectEmployee == null) {
            zjTzProjectEmployee = new ZjTzProjectEmployee();
        }
        // 获取数据
        ZjTzProjectEmployee dbZjTzProjectEmployee = zjTzProjectEmployeeMapper.selectByPrimaryKey(zjTzProjectEmployee.getProjectEmployeeId());
        // 数据存在
        if (dbZjTzProjectEmployee != null) {
            return repEntity.ok(dbZjTzProjectEmployee);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
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

        // 附件list
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
           // 项目id
           dbzjTzProjectEmployee.setProjectId(zjTzProjectEmployee.getProjectId());
           // 年月
           dbzjTzProjectEmployee.setYearAndMonth(zjTzProjectEmployee.getYearAndMonth());
           // 人数
           dbzjTzProjectEmployee.setEmployeeNumber(zjTzProjectEmployee.getEmployeeNumber());
           // 填报人员
           dbzjTzProjectEmployee.setRegisterUser(zjTzProjectEmployee.getRegisterUser());
           // 填报时间
           dbzjTzProjectEmployee.setRegisterTime(zjTzProjectEmployee.getRegisterTime());
           // 填报单位
           dbzjTzProjectEmployee.setRegisterUnits(zjTzProjectEmployee.getRegisterUnits());
           // 审核状态
           dbzjTzProjectEmployee.setAuditStatus(zjTzProjectEmployee.getAuditStatus());
           // 备注
           dbzjTzProjectEmployee.setRemarks(zjTzProjectEmployee.getRemarks());
           // 共通
           dbzjTzProjectEmployee.setModifyUserInfo(userKey, realName);
           flag = zjTzProjectEmployeeMapper.updateByPrimaryKey(dbzjTzProjectEmployee);
        }
        // 失败
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
                // 删除明细
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
        // 失败
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
                if(StrUtil.equals(zjTzProjectEmployee.getAuditStatus(), "已审核")) {
                    return repEntity.layerMessage("no", "包含已经审核的，请重新选择！");
                }
            }
            ZjTzProjectEmployee zjTzProjectEmployee = new ZjTzProjectEmployee();
            zjTzProjectEmployee.setAuditStatus("已审核");
            zjTzProjectEmployee.setModifyUserInfo(userKey, realName);
            flag = zjTzProjectEmployeeMapper.checkZjTzProjectEmployee(zjTzProjectEmployeeList, zjTzProjectEmployee);
        }
        // 失败
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
                if(StrUtil.equals(zjTzProjectEmployee.getAuditStatus(), "未审核")) {
                    return repEntity.layerMessage("no", "包含未审核的，请重新选择！");
                }
            }
            ZjTzProjectEmployee zjTzProjectEmployee = new ZjTzProjectEmployee();
            zjTzProjectEmployee.setAuditStatus("未审核");
            zjTzProjectEmployee.setModifyUserInfo(userKey, realName);
            flag = zjTzProjectEmployeeMapper.recallZjTzProjectEmployee(zjTzProjectEmployeeList, zjTzProjectEmployee);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzProjectEmployeeList);
        }
    }

    @Override
    public ResponseEntity recallAddZjTzProjectEmployee(ZjTzProjectEmployee zjTzProjectEmployee) {
        //(check判断新增人员,是否存在该项目中其他的项目人员中)
        List<ZjTzProjectAndEmployee> projectAndEmployeeList = zjTzProjectEmployee.getProjectAndEmployeeList();
        //先判断在不在自己的项目中
        ZjTzProjectAndEmployee zjTzProjectAndEmployee2 = new ZjTzProjectAndEmployee();
        zjTzProjectAndEmployee2.setProjectEmployeeId(zjTzProjectEmployee.getProjectEmployeeId());
        List<ZjTzProjectAndEmployee> zjTzProjectAndEmployeeList = zjTzProjectAndEmployeeMapper.selectByZjTzProjectAndEmployeeList(zjTzProjectAndEmployee2);
        zj:for (ZjTzProjectAndEmployee zjTzProjectAndEmployee : projectAndEmployeeList) {
            zjTzProjectAndEmployee.setProjectId(zjTzProjectEmployee.getProjectId());
            //先判断在不在自己的项目中
            //查不到自己未建的项目()
            for (ZjTzProjectAndEmployee tzProjectAndEmployee : zjTzProjectAndEmployeeList) {
                if(StrUtil.equals(zjTzProjectAndEmployee.getProjectAndEmployeeId(),tzProjectAndEmployee.getProjectAndEmployeeId())){
                    continue zj;
                }
            }
            ZjTzProjectAndEmployee zjTzProjectAndEmployee1 = zjTzProjectAndEmployeeMapper.selectByProjectIdAndEmployeeInfoId(zjTzProjectAndEmployee);
            if(zjTzProjectAndEmployee1!=null){
                return repEntity.layerMessage("no","人员中有人员已存在该项目中");
            }
        }

        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzProjectEmployee dbzjTzProjectEmployee = zjTzProjectEmployeeMapper.selectByPrimaryKey(zjTzProjectEmployee.getProjectEmployeeId());
        if (dbzjTzProjectEmployee != null && StrUtil.isNotEmpty(dbzjTzProjectEmployee.getProjectEmployeeId())) {
            // 人数
            dbzjTzProjectEmployee.setEmployeeNumber(projectAndEmployeeList.size());
            flag = zjTzProjectEmployeeMapper.updateByPrimaryKey(dbzjTzProjectEmployee);
        }
        //修改中间表
        ZjTzProjectAndEmployee zjTzProjectAndEmployee = new ZjTzProjectAndEmployee();
        zjTzProjectAndEmployee.setModifyUserInfo(userKey, realName);
        //先删
        zjTzProjectAndEmployeeMapper.DeleteUpdateZjTzProjectAndEmployee(zjTzProjectEmployee,zjTzProjectAndEmployee);
        //后增
        if(projectAndEmployeeList!=null){
            for (ZjTzProjectAndEmployee tzProjectAndEmployee : projectAndEmployeeList) {
                tzProjectAndEmployee.setProjectEmployeeId(zjTzProjectEmployee.getProjectEmployeeId());
                tzProjectAndEmployee.setProjectId(zjTzProjectEmployee.getProjectId());
                tzProjectAndEmployee.setProjectAndEmployeeId(UuidUtil.generate());
                tzProjectAndEmployee.setCreateUserInfo(userKey, realName);
                zjTzProjectAndEmployeeMapper.insert(tzProjectAndEmployee);
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzProjectEmployee);
        }
    }








}
