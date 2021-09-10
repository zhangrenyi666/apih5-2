package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class SysFlowUser extends BasePojo {
    // 主键
    private String sysFlowUserId;

    // 流程角色ID
    private String flowRoleId;

    // 项目或公司
    private String topId;

    // 部门id不要
    private String departmentId;

    // 用户key不要
    private String userKey;

    // 姓名不要
    private String realName;

    // 部门路径不要
    private String departmentPath;

    // value
    private String value;

    // valuePid
    private String valuePid;

    // type
    private String type;

    // label
    private String label;

    // 部门父ID
    private String departmentParentId;

    // 流程workId
    private String workId;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getSysFlowUserId() {
        return sysFlowUserId == null ? "" : sysFlowUserId.trim();
    }

    public void setSysFlowUserId(String sysFlowUserId) {
        this.sysFlowUserId = sysFlowUserId == null ? null : sysFlowUserId.trim();
    }

    public String getFlowRoleId() {
        return flowRoleId == null ? "" : flowRoleId.trim();
    }

    public void setFlowRoleId(String flowRoleId) {
        this.flowRoleId = flowRoleId == null ? null : flowRoleId.trim();
    }

    public String getTopId() {
        return topId == null ? "" : topId.trim();
    }

    public void setTopId(String topId) {
        this.topId = topId == null ? null : topId.trim();
    }

    public String getDepartmentId() {
        return departmentId == null ? "" : departmentId.trim();
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }

    public String getUserKey() {
        return userKey == null ? "" : userKey.trim();
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey == null ? null : userKey.trim();
    }

    public String getRealName() {
        return realName == null ? "" : realName.trim();
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getDepartmentPath() {
        return departmentPath == null ? "" : departmentPath.trim();
    }

    public void setDepartmentPath(String departmentPath) {
        this.departmentPath = departmentPath == null ? null : departmentPath.trim();
    }

    public String getValue() {
        return value == null ? "" : value.trim();
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getValuePid() {
        return valuePid == null ? "" : valuePid.trim();
    }

    public void setValuePid(String valuePid) {
        this.valuePid = valuePid == null ? null : valuePid.trim();
    }

    public String getType() {
        return type == null ? "" : type.trim();
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getLabel() {
        return label == null ? "" : label.trim();
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public String getDepartmentParentId() {
        return departmentParentId == null ? "" : departmentParentId.trim();
    }

    public void setDepartmentParentId(String departmentParentId) {
        this.departmentParentId = departmentParentId == null ? null : departmentParentId.trim();
    }

    public String getWorkId() {
        return workId == null ? "" : workId.trim();
    }

    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

}
