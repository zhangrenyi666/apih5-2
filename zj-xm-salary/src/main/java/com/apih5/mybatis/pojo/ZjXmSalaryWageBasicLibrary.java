package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjXmSalaryWageBasicLibrary extends BasePojo {
    private String libraryId;

    private String userKey;

    private String extensionId;

    private String realName;

    private String idType;

    private String idNumber;

    private String userType;

    private BigDecimal salaryBase;

    private BigDecimal positionSalary;

    private String levelId;

    private String salaryId;

    private String levelSalaryId;

    private BigDecimal educationAllowance;

    private BigDecimal yearAllowance;

    private BigDecimal titleAllowance;

    private BigDecimal childAllowance;

    private BigDecimal plateauAllowance;

    private BigDecimal highTempAllowance;

    private BigDecimal certificateAllowance;

    private BigDecimal communicationAllowance;

    private BigDecimal personalThreshold;

    private String remarks;

    private Integer sort;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public String getLibraryId() {
        return libraryId == null ? "" : libraryId.trim();
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId == null ? null : libraryId.trim();
    }

    public String getUserKey() {
        return userKey == null ? "" : userKey.trim();
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey == null ? null : userKey.trim();
    }

    public String getExtensionId() {
        return extensionId == null ? "" : extensionId.trim();
    }

    public void setExtensionId(String extensionId) {
        this.extensionId = extensionId == null ? null : extensionId.trim();
    }

    public String getRealName() {
        return realName == null ? "" : realName.trim();
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getIdType() {
        return idType == null ? "" : idType.trim();
    }

    public void setIdType(String idType) {
        this.idType = idType == null ? null : idType.trim();
    }

    public String getIdNumber() {
        return idNumber == null ? "" : idNumber.trim();
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    public String getUserType() {
        return userType == null ? "" : userType.trim();
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public BigDecimal getSalaryBase() {
        return salaryBase;
    }

    public void setSalaryBase(BigDecimal salaryBase) {
        this.salaryBase = salaryBase;
    }

    public BigDecimal getPositionSalary() {
        return positionSalary;
    }

    public void setPositionSalary(BigDecimal positionSalary) {
        this.positionSalary = positionSalary;
    }

    public String getLevelId() {
        return levelId == null ? "" : levelId.trim();
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId == null ? null : levelId.trim();
    }

    public String getSalaryId() {
        return salaryId == null ? "" : salaryId.trim();
    }

    public void setSalaryId(String salaryId) {
        this.salaryId = salaryId == null ? null : salaryId.trim();
    }

    public String getLevelSalaryId() {
        return levelSalaryId == null ? "" : levelSalaryId.trim();
    }

    public void setLevelSalaryId(String levelSalaryId) {
        this.levelSalaryId = levelSalaryId == null ? null : levelSalaryId.trim();
    }

    public BigDecimal getEducationAllowance() {
        return educationAllowance;
    }

    public void setEducationAllowance(BigDecimal educationAllowance) {
        this.educationAllowance = educationAllowance;
    }

    public BigDecimal getYearAllowance() {
        return yearAllowance;
    }

    public void setYearAllowance(BigDecimal yearAllowance) {
        this.yearAllowance = yearAllowance;
    }

    public BigDecimal getTitleAllowance() {
        return titleAllowance;
    }

    public void setTitleAllowance(BigDecimal titleAllowance) {
        this.titleAllowance = titleAllowance;
    }

    public BigDecimal getChildAllowance() {
        return childAllowance;
    }

    public void setChildAllowance(BigDecimal childAllowance) {
        this.childAllowance = childAllowance;
    }

    public BigDecimal getPlateauAllowance() {
        return plateauAllowance;
    }

    public void setPlateauAllowance(BigDecimal plateauAllowance) {
        this.plateauAllowance = plateauAllowance;
    }

    public BigDecimal getHighTempAllowance() {
        return highTempAllowance;
    }

    public void setHighTempAllowance(BigDecimal highTempAllowance) {
        this.highTempAllowance = highTempAllowance;
    }

    public BigDecimal getCertificateAllowance() {
        return certificateAllowance;
    }

    public void setCertificateAllowance(BigDecimal certificateAllowance) {
        this.certificateAllowance = certificateAllowance;
    }

    public BigDecimal getCommunicationAllowance() {
        return communicationAllowance;
    }

    public void setCommunicationAllowance(BigDecimal communicationAllowance) {
        this.communicationAllowance = communicationAllowance;
    }

    public BigDecimal getPersonalThreshold() {
        return personalThreshold;
    }

    public void setPersonalThreshold(BigDecimal personalThreshold) {
        this.personalThreshold = personalThreshold;
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

