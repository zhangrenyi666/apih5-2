package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzPolicyLocalReplyRecord extends BasePojo {
    private String recordId;

    private String policyReplyId;

    private String policyId;

    private String title;

    private String symbolNo;

    private Date sysDate;

    private String departmentName;

    private Date releaseDate;

    private String registerUser;

    private String effectiveId;

    private String effectiveName;

    private String report;

    private String companyId;

    private String companyName;

    private String projectId;

    private String projectName;

    private String value;

    private String label;

    private String type;

    private String replyInfo;

    private Date replyTime;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZjTzFile> zjTzFileList;

    public String getRecordId() {
        return recordId == null ? "" : recordId.trim();
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public String getPolicyReplyId() {
        return policyReplyId == null ? "" : policyReplyId.trim();
    }

    public void setPolicyReplyId(String policyReplyId) {
        this.policyReplyId = policyReplyId == null ? null : policyReplyId.trim();
    }

    public String getPolicyId() {
        return policyId == null ? "" : policyId.trim();
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId == null ? null : policyId.trim();
    }

    public String getTitle() {
        return title == null ? "" : title.trim();
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSymbolNo() {
        return symbolNo == null ? "" : symbolNo.trim();
    }

    public void setSymbolNo(String symbolNo) {
        this.symbolNo = symbolNo == null ? null : symbolNo.trim();
    }

    public Date getSysDate() {
        return sysDate;
    }

    public void setSysDate(Date sysDate) {
        this.sysDate = sysDate;
    }

    public String getDepartmentName() {
        return departmentName == null ? "" : departmentName.trim();
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRegisterUser() {
        return registerUser == null ? "" : registerUser.trim();
    }

    public void setRegisterUser(String registerUser) {
        this.registerUser = registerUser == null ? null : registerUser.trim();
    }

    public String getEffectiveId() {
        return effectiveId == null ? "" : effectiveId.trim();
    }

    public void setEffectiveId(String effectiveId) {
        this.effectiveId = effectiveId == null ? null : effectiveId.trim();
    }

    public String getEffectiveName() {
        return effectiveName == null ? "" : effectiveName.trim();
    }

    public void setEffectiveName(String effectiveName) {
        this.effectiveName = effectiveName == null ? null : effectiveName.trim();
    }

    public String getReport() {
        return report == null ? "" : report.trim();
    }

    public void setReport(String report) {
        this.report = report == null ? null : report.trim();
    }

    public String getCompanyId() {
        return companyId == null ? "" : companyId.trim();
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCompanyName() {
        return companyName == null ? "" : companyName.trim();
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
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

    public String getValue() {
        return value == null ? "" : value.trim();
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getLabel() {
        return label == null ? "" : label.trim();
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public String getType() {
        return type == null ? "" : type.trim();
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getReplyInfo() {
        return replyInfo == null ? "" : replyInfo.trim();
    }

    public void setReplyInfo(String replyInfo) {
        this.replyInfo = replyInfo == null ? null : replyInfo.trim();
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
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

}

