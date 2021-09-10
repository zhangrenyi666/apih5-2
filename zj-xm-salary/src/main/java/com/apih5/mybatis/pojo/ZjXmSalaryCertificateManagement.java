package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjXmSalaryCertificateManagement extends BasePojo {
	private String certificateId;

	private String extensionId;

	private String certificateType;

	private String certificateName;

	private String certificateMajor;

	private String certificateNo;

	private Date issueDate;

	private String projectId;

	private BigDecimal rewardStandard;

	private String apportionYear;

	private String paidYear;

	private BigDecimal subsidyStandard;

	private Date startDate;

	private Date endDate;

	private String remarks;

	private Integer sort;

	private String delFlag;

	private Date createTime;

	private String createUser;

	private String createUserName;

	private Date modifyTime;

	private String modifyUser;

	private String modifyUserName;

	// 业务扩展字段
	// 证书附件
	private List<ZjXmSalaryUserAttachment> certificateAttachmentList;

	public List<ZjXmSalaryUserAttachment> getCertificateAttachmentList() {
		return certificateAttachmentList;
	}

	public void setCertificateAttachmentList(List<ZjXmSalaryUserAttachment> certificateAttachmentList) {
		this.certificateAttachmentList = certificateAttachmentList;
	}

	public String getCertificateId() {
		return certificateId == null ? "" : certificateId.trim();
	}

	public void setCertificateId(String certificateId) {
		this.certificateId = certificateId == null ? null : certificateId.trim();
	}

	public String getExtensionId() {
		return extensionId == null ? "" : extensionId.trim();
	}

	public void setExtensionId(String extensionId) {
		this.extensionId = extensionId == null ? null : extensionId.trim();
	}

	public String getCertificateType() {
		return certificateType == null ? "" : certificateType.trim();
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType == null ? null : certificateType.trim();
	}

	public String getCertificateName() {
		return certificateName == null ? "" : certificateName.trim();
	}

	public void setCertificateName(String certificateName) {
		this.certificateName = certificateName == null ? null : certificateName.trim();
	}

	public String getCertificateMajor() {
		return certificateMajor == null ? "" : certificateMajor.trim();
	}

	public void setCertificateMajor(String certificateMajor) {
		this.certificateMajor = certificateMajor == null ? null : certificateMajor.trim();
	}

	public String getCertificateNo() {
		return certificateNo == null ? "" : certificateNo.trim();
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo == null ? null : certificateNo.trim();
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getProjectId() {
		return projectId == null ? "" : projectId.trim();
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId == null ? null : projectId.trim();
	}

	public BigDecimal getRewardStandard() {
		return rewardStandard;
	}

	public void setRewardStandard(BigDecimal rewardStandard) {
		this.rewardStandard = rewardStandard;
	}

	public String getApportionYear() {
		return apportionYear == null ? "" : apportionYear.trim();
	}

	public void setApportionYear(String apportionYear) {
		this.apportionYear = apportionYear == null ? null : apportionYear.trim();
	}

	public String getPaidYear() {
		return paidYear == null ? "" : paidYear.trim();
	}

	public void setPaidYear(String paidYear) {
		this.paidYear = paidYear == null ? null : paidYear.trim();
	}

	public BigDecimal getSubsidyStandard() {
		return subsidyStandard;
	}

	public void setSubsidyStandard(BigDecimal subsidyStandard) {
		this.subsidyStandard = subsidyStandard;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
