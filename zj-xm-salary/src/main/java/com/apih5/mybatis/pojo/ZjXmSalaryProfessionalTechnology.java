package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjXmSalaryProfessionalTechnology extends BasePojo {
	private String technologyId;

	private String extensionId;

	private String title;

	private String specialty;

	private String level;

	private String access;

	private String qualificationNo;

	private Date acquisitionDate;

	private String certificateNo;

	private String issueUnit;

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
	// 专业技术附件
	private List<ZjXmSalaryUserAttachment> technologyAttachmentList;

	public List<ZjXmSalaryUserAttachment> getTechnologyAttachmentList() {
		return technologyAttachmentList;
	}

	public void setTechnologyAttachmentList(List<ZjXmSalaryUserAttachment> technologyAttachmentList) {
		this.technologyAttachmentList = technologyAttachmentList;
	}

	public String getTechnologyId() {
		return technologyId == null ? "" : technologyId.trim();
	}

	public void setTechnologyId(String technologyId) {
		this.technologyId = technologyId == null ? null : technologyId.trim();
	}

	public String getExtensionId() {
		return extensionId == null ? "" : extensionId.trim();
	}

	public void setExtensionId(String extensionId) {
		this.extensionId = extensionId == null ? null : extensionId.trim();
	}

	public String getTitle() {
		return title == null ? "" : title.trim();
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getSpecialty() {
		return specialty == null ? "" : specialty.trim();
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty == null ? null : specialty.trim();
	}

	public String getLevel() {
		return level == null ? "" : level.trim();
	}

	public void setLevel(String level) {
		this.level = level == null ? null : level.trim();
	}

	public String getAccess() {
		return access == null ? "" : access.trim();
	}

	public void setAccess(String access) {
		this.access = access == null ? null : access.trim();
	}

	public String getQualificationNo() {
		return qualificationNo == null ? "" : qualificationNo.trim();
	}

	public void setQualificationNo(String qualificationNo) {
		this.qualificationNo = qualificationNo == null ? null : qualificationNo.trim();
	}

	public Date getAcquisitionDate() {
		return acquisitionDate;
	}

	public void setAcquisitionDate(Date acquisitionDate) {
		this.acquisitionDate = acquisitionDate;
	}

	public String getCertificateNo() {
		return certificateNo == null ? "" : certificateNo.trim();
	}

	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo == null ? null : certificateNo.trim();
	}

	public String getIssueUnit() {
		return issueUnit == null ? "" : issueUnit.trim();
	}

	public void setIssueUnit(String issueUnit) {
		this.issueUnit = issueUnit == null ? null : issueUnit.trim();
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
