package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;
public class ZjTzFinanceMonth extends BasePojo {
    private String financeMonthId;

    private Date yearMonthDate;

    private String yearMonthStr;

    private String projectId;

    private String projectName;

    private Date registerDate;

    private String registerPerson;

    private String process;

    private String releaseId;

    private String releaseName;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZjTzFile> zjTzFileList;

    private String projectIdSql;
    
    public String getFinanceMonthId() {
        return financeMonthId == null ? "" : financeMonthId.trim();
    }

    public void setFinanceMonthId(String financeMonthId) {
        this.financeMonthId = financeMonthId == null ? null : financeMonthId.trim();
    }

    public Date getYearMonthDate() {
        return yearMonthDate;
    }

    public void setYearMonthDate(Date yearMonthDate) {
        this.yearMonthDate = yearMonthDate;
    }

    public String getYearMonthStr() {
        return yearMonthStr == null ? "" : yearMonthStr.trim();
    }

    public void setYearMonthStr(String yearMonthStr) {
        this.yearMonthStr = yearMonthStr == null ? null : yearMonthStr.trim();
    }

    public String getProjectId() {
        return projectId == null ? "" : projectId.trim();
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName.trim();
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getRegisterPerson() {
        return registerPerson == null ? "" : registerPerson.trim();
    }

    public void setRegisterPerson(String registerPerson) {
        this.registerPerson = registerPerson == null ? null : registerPerson.trim();
    }

    public String getProcess() {
        return process == null ? "" : process.trim();
    }

    public void setProcess(String process) {
        this.process = process == null ? null : process.trim();
    }

    public String getReleaseId() {
        return releaseId == null ? "" : releaseId.trim();
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId == null ? null : releaseId.trim();
    }

    public String getReleaseName() {
        return releaseName == null ? "" : releaseName.trim();
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName == null ? null : releaseName.trim();
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

    public List<ZjTzFile> getZjTzFileList() {
        return zjTzFileList;
    }

    public void setZjTzFileList(List<ZjTzFile> zjTzFileList) {
        this.zjTzFileList = zjTzFileList;
    }
    
    public String getProjectIdSql() {
        return projectIdSql == null ? "" : projectIdSql.trim();
    }

    public void setProjectIdSql(String projectIdSql) {
        this.projectIdSql = projectIdSql == null ? null : projectIdSql.trim();
    }

}

