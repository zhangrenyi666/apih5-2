package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzPppTerm extends BasePojo {
    private String pppTermId;

    private String projectId;

    private String subprojectInfoId;

    private String projectName;

    private String issueId;

    private String issueName;

    private String numberContent;

    private String term;

    private String riskFlag;

    private String measure;

    private String negotiateFlag;

    private String deptAndLeader;

    private String implement;

    private Date registerDate;

    private String registerPerson;

    private String statusId;

    private String statusName;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZjTzFile> zjTzFileList;

    private List<ZjTzPppTermReply> zjTzPppTermReplyList;

    private String projectIdSql;

    private String subprojectInfoName;

    public String getPppTermId() {
        return pppTermId == null ? "" : pppTermId.trim();
    }

    public void setPppTermId(String pppTermId) {
        this.pppTermId = pppTermId == null ? null : pppTermId.trim();
    }

    public String getProjectId() {
        return projectId == null ? "" : projectId.trim();
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getSubprojectInfoId() {
        return subprojectInfoId == null ? "" : subprojectInfoId.trim();
    }

    public void setSubprojectInfoId(String subprojectInfoId) {
        this.subprojectInfoId = subprojectInfoId == null ? null : subprojectInfoId.trim();
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName.trim();
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getIssueId() {
        return issueId == null ? "" : issueId.trim();
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId == null ? null : issueId.trim();
    }

    public String getIssueName() {
        return issueName == null ? "" : issueName.trim();
    }

    public void setIssueName(String issueName) {
        this.issueName = issueName == null ? null : issueName.trim();
    }

    public String getNumberContent() {
        return numberContent == null ? "" : numberContent.trim();
    }

    public void setNumberContent(String numberContent) {
        this.numberContent = numberContent == null ? null : numberContent.trim();
    }

    public String getTerm() {
        return term == null ? "" : term.trim();
    }

    public void setTerm(String term) {
        this.term = term == null ? null : term.trim();
    }

    public String getRiskFlag() {
        return riskFlag == null ? "" : riskFlag.trim();
    }

    public void setRiskFlag(String riskFlag) {
        this.riskFlag = riskFlag == null ? null : riskFlag.trim();
    }

    public String getMeasure() {
        return measure == null ? "" : measure.trim();
    }

    public void setMeasure(String measure) {
        this.measure = measure == null ? null : measure.trim();
    }

    public String getNegotiateFlag() {
        return negotiateFlag == null ? "" : negotiateFlag.trim();
    }

    public void setNegotiateFlag(String negotiateFlag) {
        this.negotiateFlag = negotiateFlag == null ? null : negotiateFlag.trim();
    }

    public String getDeptAndLeader() {
        return deptAndLeader == null ? "" : deptAndLeader.trim();
    }

    public void setDeptAndLeader(String deptAndLeader) {
        this.deptAndLeader = deptAndLeader == null ? null : deptAndLeader.trim();
    }

    public String getImplement() {
        return implement == null ? "" : implement.trim();
    }

    public void setImplement(String implement) {
        this.implement = implement == null ? null : implement.trim();
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

    public String getStatusId() {
        return statusId == null ? "" : statusId.trim();
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId == null ? null : statusId.trim();
    }

    public String getStatusName() {
        return statusName == null ? "" : statusName.trim();
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName == null ? null : statusName.trim();
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

    public List<ZjTzPppTermReply> getZjTzPppTermReplyList() {
        return zjTzPppTermReplyList;
    }

    public void setZjTzPppTermReplyList(List<ZjTzPppTermReply> zjTzPppTermReplyList) {
        this.zjTzPppTermReplyList = zjTzPppTermReplyList;
    }

    public String getProjectIdSql() {
        return projectIdSql == null ? "" : projectIdSql.trim();
    }

    public void setProjectIdSql(String projectIdSql) {
        this.projectIdSql = projectIdSql == null ? null : projectIdSql.trim();
    }

    public String getSubprojectInfoName() {
        return subprojectInfoName == null ? "" : subprojectInfoName.trim();
    }

    public void setSubprojectInfoName(String subprojectInfoName) {
        this.subprojectInfoName = subprojectInfoName == null ? null : subprojectInfoName.trim();
    }

}

