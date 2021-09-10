package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;

public class SysWoaLargeModule extends BasePojo {
    private String largeModuleId;

    private String roleId;

    private String roleName;

    private String largeModuleType;

    private String largeModuleTitle;

    private Integer largeModuleSort;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List sysWoaLargeModuleList;

    private List sysWoaSmallModuleList;

    private String projectId;

    public String getLargeModuleId() {
        return largeModuleId == null ? "" : largeModuleId.trim();
    }

    public void setLargeModuleId(String largeModuleId) {
        this.largeModuleId = largeModuleId == null ? null : largeModuleId.trim();
    }

    public String getRoleId() {
        return roleId == null ? "" : roleId.trim();
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getRoleName() {
        return roleName == null ? "" : roleName.trim();
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getLargeModuleType() {
        return largeModuleType == null ? "" : largeModuleType.trim();
    }

    public void setLargeModuleType(String largeModuleType) {
        this.largeModuleType = largeModuleType == null ? null : largeModuleType.trim();
    }

    public String getLargeModuleTitle() {
        return largeModuleTitle == null ? "" : largeModuleTitle.trim();
    }

    public void setLargeModuleTitle(String largeModuleTitle) {
        this.largeModuleTitle = largeModuleTitle == null ? null : largeModuleTitle.trim();
    }

    public Integer getLargeModuleSort() {
        return largeModuleSort;
    }

    public void setLargeModuleSort(Integer largeModuleSort) {
        this.largeModuleSort = largeModuleSort;
    }

    public String getDelFlag() {
        return delFlag == null ? "" : delFlag.trim();
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser == null ? "" : createUser.trim();
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getCreateUserName() {
        return createUserName == null ? "" : createUserName.trim();
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser == null ? "" : modifyUser.trim();
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    public String getModifyUserName() {
        return modifyUserName == null ? "" : modifyUserName.trim();
    }

    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName == null ? null : modifyUserName.trim();
    }

    public List getSysWoaLargeModuleList() {
        return sysWoaLargeModuleList;
    }

    public void setSysWoaLargeModuleList(List sysWoaLargeModuleList) {
        this.sysWoaLargeModuleList = sysWoaLargeModuleList;
    }

    public List getSysWoaSmallModuleList() {
        return sysWoaSmallModuleList;
    }

    public void setSysWoaSmallModuleList(List sysWoaSmallModuleList) {
        this.sysWoaSmallModuleList = sysWoaSmallModuleList;
    }

    public String getProjectId() {
        return projectId == null ? "" : projectId.trim();
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

}

