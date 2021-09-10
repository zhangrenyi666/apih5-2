package com.apih5.mybatis.pojo;

import java.util.Date;
import com.apih5.framework.entity.BasePojo;

public class ZjTzEmployeeLanguage extends BasePojo {
    private String languageId;

    private String employeeInfoId;

    private String language;

    private String languageProficiency;

    private String certificateName;

    private Date getTime;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public String getLanguageId() {
        return languageId == null ? "" : languageId.trim();
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId == null ? null : languageId.trim();
    }

    public String getEmployeeInfoId() {
        return employeeInfoId == null ? "" : employeeInfoId.trim();
    }

    public void setEmployeeInfoId(String employeeInfoId) {
        this.employeeInfoId = employeeInfoId == null ? null : employeeInfoId.trim();
    }

    public String getLanguage() {
        return language == null ? "" : language.trim();
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    public String getLanguageProficiency() {
        return languageProficiency == null ? "" : languageProficiency.trim();
    }

    public void setLanguageProficiency(String languageProficiency) {
        this.languageProficiency = languageProficiency == null ? null : languageProficiency.trim();
    }

    public String getCertificateName() {
        return certificateName == null ? "" : certificateName.trim();
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName == null ? null : certificateName.trim();
    }

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
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

