package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjXmSalaryContractManagement extends BasePojo {
	private String contractId;

	private String extensionId;

	private String contractNo;

	private Date signingDate;

	private String contractType;

	private Date startDate;

	private Date endDate;

	private Integer contractPeriod;

	private String probation;

	private String signingType;

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
	// 原单位离职证明
	private List<ZjXmSalaryUserAttachment> quitAttachmentList;
	// 合同附件
	private List<ZjXmSalaryUserAttachment> contractAttachmentList;

	public List<ZjXmSalaryUserAttachment> getQuitAttachmentList() {
		return quitAttachmentList;
	}

	public void setQuitAttachmentList(List<ZjXmSalaryUserAttachment> quitAttachmentList) {
		this.quitAttachmentList = quitAttachmentList;
	}

	public List<ZjXmSalaryUserAttachment> getContractAttachmentList() {
		return contractAttachmentList;
	}

	public void setContractAttachmentList(List<ZjXmSalaryUserAttachment> contractAttachmentList) {
		this.contractAttachmentList = contractAttachmentList;
	}

	public String getContractId() {
		return contractId == null ? "" : contractId.trim();
	}

	public void setContractId(String contractId) {
		this.contractId = contractId == null ? null : contractId.trim();
	}

	public String getExtensionId() {
		return extensionId == null ? "" : extensionId.trim();
	}

	public void setExtensionId(String extensionId) {
		this.extensionId = extensionId == null ? null : extensionId.trim();
	}

	public String getContractNo() {
		return contractNo == null ? "" : contractNo.trim();
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo == null ? null : contractNo.trim();
	}

	public Date getSigningDate() {
		return signingDate;
	}

	public void setSigningDate(Date signingDate) {
		this.signingDate = signingDate;
	}

	public String getContractType() {
		return contractType == null ? "" : contractType.trim();
	}

	public void setContractType(String contractType) {
		this.contractType = contractType == null ? null : contractType.trim();
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

	public Integer getContractPeriod() {
		return contractPeriod;
	}

	public void setContractPeriod(Integer contractPeriod) {
		this.contractPeriod = contractPeriod;
	}

	public String getProbation() {
		return probation == null ? "" : probation.trim();
	}

	public void setProbation(String probation) {
		this.probation = probation == null ? null : probation.trim();
	}

	public String getSigningType() {
		return signingType == null ? "" : signingType.trim();
	}

	public void setSigningType(String signingType) {
		this.signingType = signingType == null ? null : signingType.trim();
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
