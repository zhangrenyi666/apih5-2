package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjXmSalaryHealthCondition extends BasePojo {
	private String healthId;

	private String extensionId;

	private String physicalType;

	private String physicalInstitution;

	private String healthCondition;

	private String occupationalDisease;

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
	// 健康情况附件
	private List<ZjXmSalaryUserAttachment> healthAttachmentList;

	public List<ZjXmSalaryUserAttachment> getHealthAttachmentList() {
		return healthAttachmentList;
	}

	public void setHealthAttachmentList(List<ZjXmSalaryUserAttachment> healthAttachmentList) {
		this.healthAttachmentList = healthAttachmentList;
	}

	public String getHealthId() {
		return healthId == null ? "" : healthId.trim();
	}

	public void setHealthId(String healthId) {
		this.healthId = healthId == null ? null : healthId.trim();
	}

	public String getExtensionId() {
		return extensionId == null ? "" : extensionId.trim();
	}

	public void setExtensionId(String extensionId) {
		this.extensionId = extensionId == null ? null : extensionId.trim();
	}

	public String getPhysicalType() {
		return physicalType == null ? "" : physicalType.trim();
	}

	public void setPhysicalType(String physicalType) {
		this.physicalType = physicalType == null ? null : physicalType.trim();
	}

	public String getPhysicalInstitution() {
		return physicalInstitution == null ? "" : physicalInstitution.trim();
	}

	public void setPhysicalInstitution(String physicalInstitution) {
		this.physicalInstitution = physicalInstitution == null ? null : physicalInstitution.trim();
	}

	public String getHealthCondition() {
		return healthCondition == null ? "" : healthCondition.trim();
	}

	public void setHealthCondition(String healthCondition) {
		this.healthCondition = healthCondition == null ? null : healthCondition.trim();
	}

	public String getOccupationalDisease() {
		return occupationalDisease == null ? "" : occupationalDisease.trim();
	}

	public void setOccupationalDisease(String occupationalDisease) {
		this.occupationalDisease = occupationalDisease == null ? null : occupationalDisease.trim();
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
