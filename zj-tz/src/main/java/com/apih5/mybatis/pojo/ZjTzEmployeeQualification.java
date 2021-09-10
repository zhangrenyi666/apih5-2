package com.apih5.mybatis.pojo;

import java.util.Date;
import com.apih5.framework.entity.BasePojo;

public class ZjTzEmployeeQualification extends BasePojo {
    private String qualificationId;

    private String employeeInfoId;

    private String qualificationName;

    private String major;

    private Date getTime;

    private String certificateNumber;

    private String registeredUnit;

    private String issuedUnit;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public String getQualificationId() {
        return qualificationId == null ? "" : qualificationId.trim();
    }

    public void setQualificationId(String qualificationId) {
        this.qualificationId = qualificationId == null ? null : qualificationId.trim();
    }

    public String getEmployeeInfoId() {
        return employeeInfoId == null ? "" : employeeInfoId.trim();
    }

    public void setEmployeeInfoId(String employeeInfoId) {
        this.employeeInfoId = employeeInfoId == null ? null : employeeInfoId.trim();
    }

    public String getQualificationName() {
        return qualificationName == null ? "" : qualificationName.trim();
    }

    public void setQualificationName(String qualificationName) {
        this.qualificationName = qualificationName == null ? null : qualificationName.trim();
    }

    public String getMajor() {
        return major == null ? "" : major.trim();
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }

    public String getCertificateNumber() {
        return certificateNumber == null ? "" : certificateNumber.trim();
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber == null ? null : certificateNumber.trim();
    }

    public String getRegisteredUnit() {
        return registeredUnit == null ? "" : registeredUnit.trim();
    }

    public void setRegisteredUnit(String registeredUnit) {
        this.registeredUnit = registeredUnit == null ? null : registeredUnit.trim();
    }

    public String getIssuedUnit() {
        return issuedUnit == null ? "" : issuedUnit.trim();
    }

    public void setIssuedUnit(String issuedUnit) {
        this.issuedUnit = issuedUnit == null ? null : issuedUnit.trim();
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

