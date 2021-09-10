package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;

public class SysSelectDepartmentUser extends BasePojo {
    private String selectId;

    private String otherId;

    private String otherType;

    private String departmentUserFlag;

    private String projectId;

    private String objectUserKey;

    private String objectDepartmentId;

    private String objectName;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public String getSelectId() {
        return selectId == null ? "" : selectId.trim();
    }

    public void setSelectId(String selectId) {
        this.selectId = selectId == null ? null : selectId.trim();
    }

    public String getOtherId() {
        return otherId == null ? "" : otherId.trim();
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId == null ? null : otherId.trim();
    }

    public String getOtherType() {
        return otherType == null ? "" : otherType.trim();
    }

    public void setOtherType(String otherType) {
        this.otherType = otherType == null ? null : otherType.trim();
    }

    public String getDepartmentUserFlag() {
        return departmentUserFlag == null ? "" : departmentUserFlag.trim();
    }

    public void setDepartmentUserFlag(String departmentUserFlag) {
        this.departmentUserFlag = departmentUserFlag == null ? null : departmentUserFlag.trim();
    }

    public String getProjectId() {
        return projectId == null ? "" : projectId.trim();
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getObjectUserKey() {
        return objectUserKey == null ? "" : objectUserKey.trim();
    }

    public void setObjectUserKey(String objectUserKey) {
        this.objectUserKey = objectUserKey == null ? null : objectUserKey.trim();
    }

    public String getObjectDepartmentId() {
        return objectDepartmentId == null ? "" : objectDepartmentId.trim();
    }

    public void setObjectDepartmentId(String objectDepartmentId) {
        this.objectDepartmentId = objectDepartmentId == null ? null : objectDepartmentId.trim();
    }

    public String getObjectName() {
        return objectName == null ? "" : objectName.trim();
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName == null ? null : objectName.trim();
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

}

