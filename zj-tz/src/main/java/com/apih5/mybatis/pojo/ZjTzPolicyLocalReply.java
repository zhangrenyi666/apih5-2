package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzPolicyLocalReply extends BasePojo {
    private String policyReplyId;

    private String policyId;

    private String title;

    private String symbolNo;

    private String provinceId;

    private String releaseRankId;

    private String releaseRankName;

    private Date sysDate;

    private String departmentName;

    private Date releaseDate;

    private Date effectDate;

    private String registerUser;

    private String effectiveId;

    private String effectiveName;

    private String report;

    private String companyId;

    private String companyName;

    private String projectId;

    private String projectName;

    private String projectShortName;

    private String value;

    private String label;

    private String type;

    private String replyInfo;

    private Date replyTime;

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

    private List<ZjTzFile> zjTzPolicyLocalReplyFileList;
    
    private String replyFlag;

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

    public String getProvinceId() {
        return provinceId == null ? "" : provinceId.trim();
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId == null ? null : provinceId.trim();
    }

    public String getReleaseRankId() {
        return releaseRankId == null ? "" : releaseRankId.trim();
    }

    public void setReleaseRankId(String releaseRankId) {
        this.releaseRankId = releaseRankId == null ? null : releaseRankId.trim();
    }

    public String getReleaseRankName() {
        return releaseRankName == null ? "" : releaseRankName.trim();
    }

    public void setReleaseRankName(String releaseRankName) {
        this.releaseRankName = releaseRankName == null ? null : releaseRankName.trim();
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

    public Date getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate;
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

    public String getProjectShortName() {
        return projectShortName == null ? "" : projectShortName.trim();
    }

    public void setProjectShortName(String projectShortName) {
        this.projectShortName = projectShortName == null ? null : projectShortName.trim();
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

    public List<ZjTzFile> getZjTzPolicyLocalReplyFileList() {
        return zjTzPolicyLocalReplyFileList;
    }

    public void setZjTzPolicyLocalReplyFileList(List<ZjTzFile> zjTzPolicyLocalReplyFileList) {
        this.zjTzPolicyLocalReplyFileList = zjTzPolicyLocalReplyFileList;
    }
    
    public String getReplyFlag() {
		return replyFlag == null ? "" : replyFlag.trim();
	}

	public void setReplyFlag(String replyFlag) {
		this.replyFlag = replyFlag == null ? null : replyFlag.trim();
	}

}

