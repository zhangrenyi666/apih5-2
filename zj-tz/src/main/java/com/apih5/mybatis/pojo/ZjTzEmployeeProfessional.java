package com.apih5.mybatis.pojo;

import java.util.Date;
import com.apih5.framework.entity.BasePojo;

public class ZjTzEmployeeProfessional extends BasePojo {
    private String professionalId;

    private String employeeInfoId;

    private String professionalNameId;

    private String professionalLevelId;

    private Date getTime;

    private String getWayId;

    private String appUnit;

    private String engageProfessionalName;

    private Date engageStartTime;

    private Date engageEndTime;

    private String professionalFlag;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public String getProfessionalId() {
        return professionalId == null ? "" : professionalId.trim();
    }

    public void setProfessionalId(String professionalId) {
        this.professionalId = professionalId == null ? null : professionalId.trim();
    }

    public String getEmployeeInfoId() {
        return employeeInfoId == null ? "" : employeeInfoId.trim();
    }

    public void setEmployeeInfoId(String employeeInfoId) {
        this.employeeInfoId = employeeInfoId == null ? null : employeeInfoId.trim();
    }

    public String getProfessionalNameId() {
        return professionalNameId == null ? "" : professionalNameId.trim();
    }

    public void setProfessionalNameId(String professionalNameId) {
        this.professionalNameId = professionalNameId == null ? null : professionalNameId.trim();
    }

    public String getProfessionalLevelId() {
        return professionalLevelId == null ? "" : professionalLevelId.trim();
    }

    public void setProfessionalLevelId(String professionalLevelId) {
        this.professionalLevelId = professionalLevelId == null ? null : professionalLevelId.trim();
    }

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }

    public String getGetWayId() {
        return getWayId == null ? "" : getWayId.trim();
    }

    public void setGetWayId(String getWayId) {
        this.getWayId = getWayId == null ? null : getWayId.trim();
    }

    public String getAppUnit() {
        return appUnit == null ? "" : appUnit.trim();
    }

    public void setAppUnit(String appUnit) {
        this.appUnit = appUnit == null ? null : appUnit.trim();
    }

    public String getEngageProfessionalName() {
        return engageProfessionalName == null ? "" : engageProfessionalName.trim();
    }

    public void setEngageProfessionalName(String engageProfessionalName) {
        this.engageProfessionalName = engageProfessionalName == null ? null : engageProfessionalName.trim();
    }

    public Date getEngageStartTime() {
        return engageStartTime;
    }

    public void setEngageStartTime(Date engageStartTime) {
        this.engageStartTime = engageStartTime;
    }

    public Date getEngageEndTime() {
        return engageEndTime;
    }

    public void setEngageEndTime(Date engageEndTime) {
        this.engageEndTime = engageEndTime;
    }

    public String getProfessionalFlag() {
        return professionalFlag == null ? "" : professionalFlag.trim();
    }

    public void setProfessionalFlag(String professionalFlag) {
        this.professionalFlag = professionalFlag == null ? null : professionalFlag.trim();
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

