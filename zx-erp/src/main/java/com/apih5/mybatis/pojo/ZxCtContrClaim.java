package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;

public class ZxCtContrClaim extends BasePojo {
	// 主键
	private String id;

	// 合同ID
	private String contractID;

	// 项目ID
	private String orgID;

	// 索赔等级
	private String alterLevel;

	// 要求索赔方
	private String proposer;

	// 主要内容
	private String alterContent;

	// 主要原因
	private String alterReason;

	// 发生时间
	private Date happenDate;

	// 申报金额
	private BigDecimal applyAmount;

	// 申报日期
	private Date applyDate;

	// 申报文号
	private String applyNo;

	// 申报延期天数
	private String applyDelay;

	// 批复金额
	private BigDecimal replyAmount;

	// 批复日期
	private Date replyDate;

	// 批复文号
	private String replyNo;

	// 批复延期天数
	private String replyDelay;

	// 批复状态
	private String replyStatus;

	// 需要公司协助
	private String companyHelp;

	// 生效操作日期
	private Date takeEffectDate;

	// 生效操作人
	private String takeEffectMan;

	// 审核状态
	private String auditStatus;

	// 索赔工期
	private String claimPeriod;

	// 记录人
	private String recorder;

	// 记录日期
	private Date recordDate;

	// 驻地监理确认金额
	private BigDecimal confirmatAmountOfResidentSupervision;

	// 驻地监理确认日期
	private Date confirmatDateOfResidentSupervisor;

	// 总监办确认金额
	private BigDecimal amountConfirmedByDirectorOffice;

	// 总监办确认日期
	private Date dateConfirmedByDirectorOffice;

	// pp5
	private String pp5;

	// pp6
	private String pp6;

	// pp7
	private String pp7;

	// pp8
	private String pp8;

	// pp9
	private String pp9;

	// pp10
	private String pp10;

	// 附件
	private List<ZxErpFile> attachment;

	// 索赔明细列表
	private List<ZxCtContrClaimItem> contrClaimItemList;

	// 备注
	private String remarks;

	// 排序
	private int sort = 0;

	public String getId() {
		return id == null ? "" : id.trim();
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getContractID() {
		return contractID == null ? "" : contractID.trim();
	}

	public void setContractID(String contractID) {
		this.contractID = contractID == null ? null : contractID.trim();
	}

	public String getOrgID() {
		return orgID == null ? "" : orgID.trim();
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID == null ? null : orgID.trim();
	}

	public String getAlterLevel() {
		return alterLevel == null ? "" : alterLevel.trim();
	}

	public void setAlterLevel(String alterLevel) {
		this.alterLevel = alterLevel == null ? null : alterLevel.trim();
	}

	public String getProposer() {
		return proposer == null ? "" : proposer.trim();
	}

	public void setProposer(String proposer) {
		this.proposer = proposer == null ? null : proposer.trim();
	}

	public String getAlterContent() {
		return alterContent == null ? "" : alterContent.trim();
	}

	public void setAlterContent(String alterContent) {
		this.alterContent = alterContent == null ? null : alterContent.trim();
	}

	public String getAlterReason() {
		return alterReason == null ? "" : alterReason.trim();
	}

	public void setAlterReason(String alterReason) {
		this.alterReason = alterReason == null ? null : alterReason.trim();
	}

	public Date getHappenDate() {
		return happenDate;
	}

	public void setHappenDate(Date happenDate) {
		this.happenDate = happenDate;
	}

	public BigDecimal getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(BigDecimal applyAmount) {
		this.applyAmount = applyAmount;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getApplyNo() {
		return applyNo == null ? "" : applyNo.trim();
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo == null ? null : applyNo.trim();
	}

	public String getApplyDelay() {
		return applyDelay == null ? "" : applyDelay.trim();
	}

	public void setApplyDelay(String applyDelay) {
		this.applyDelay = applyDelay == null ? null : applyDelay.trim();
	}

	public BigDecimal getReplyAmount() {
		return replyAmount;
	}

	public void setReplyAmount(BigDecimal replyAmount) {
		this.replyAmount = replyAmount;
	}

	public Date getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}

	public String getReplyNo() {
		return replyNo == null ? "" : replyNo.trim();
	}

	public void setReplyNo(String replyNo) {
		this.replyNo = replyNo == null ? null : replyNo.trim();
	}

	public String getReplyDelay() {
		return replyDelay == null ? "" : replyDelay.trim();
	}

	public void setReplyDelay(String replyDelay) {
		this.replyDelay = replyDelay == null ? null : replyDelay.trim();
	}

	public String getReplyStatus() {
		return replyStatus == null ? "" : replyStatus.trim();
	}

	public void setReplyStatus(String replyStatus) {
		this.replyStatus = replyStatus == null ? null : replyStatus.trim();
	}

	public String getCompanyHelp() {
		return companyHelp == null ? "" : companyHelp.trim();
	}

	public void setCompanyHelp(String companyHelp) {
		this.companyHelp = companyHelp == null ? null : companyHelp.trim();
	}

	public Date getTakeEffectDate() {
		return takeEffectDate;
	}

	public void setTakeEffectDate(Date takeEffectDate) {
		this.takeEffectDate = takeEffectDate;
	}

	public String getTakeEffectMan() {
		return takeEffectMan == null ? "" : takeEffectMan.trim();
	}

	public void setTakeEffectMan(String takeEffectMan) {
		this.takeEffectMan = takeEffectMan == null ? null : takeEffectMan.trim();
	}

	public String getAuditStatus() {
		return auditStatus == null ? "" : auditStatus.trim();
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus == null ? null : auditStatus.trim();
	}

	public String getClaimPeriod() {
		return claimPeriod == null ? "" : claimPeriod.trim();
	}

	public void setClaimPeriod(String claimPeriod) {
		this.claimPeriod = claimPeriod == null ? null : claimPeriod.trim();
	}

	public String getRecorder() {
		return recorder == null ? "" : recorder.trim();
	}

	public void setRecorder(String recorder) {
		this.recorder = recorder == null ? null : recorder.trim();
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public BigDecimal getConfirmatAmountOfResidentSupervision() {
		return confirmatAmountOfResidentSupervision;
	}

	public void setConfirmatAmountOfResidentSupervision(BigDecimal confirmatAmountOfResidentSupervision) {
		this.confirmatAmountOfResidentSupervision = confirmatAmountOfResidentSupervision;
	}

	public Date getConfirmatDateOfResidentSupervisor() {
		return confirmatDateOfResidentSupervisor;
	}

	public void setConfirmatDateOfResidentSupervisor(Date confirmatDateOfResidentSupervisor) {
		this.confirmatDateOfResidentSupervisor = confirmatDateOfResidentSupervisor;
	}

	public BigDecimal getAmountConfirmedByDirectorOffice() {
		return amountConfirmedByDirectorOffice;
	}

	public void setAmountConfirmedByDirectorOffice(BigDecimal amountConfirmedByDirectorOffice) {
		this.amountConfirmedByDirectorOffice = amountConfirmedByDirectorOffice;
	}

	public Date getDateConfirmedByDirectorOffice() {
		return dateConfirmedByDirectorOffice;
	}

	public void setDateConfirmedByDirectorOffice(Date dateConfirmedByDirectorOffice) {
		this.dateConfirmedByDirectorOffice = dateConfirmedByDirectorOffice;
	}

	public String getPp5() {
		return pp5 == null ? "" : pp5.trim();
	}

	public void setPp5(String pp5) {
		this.pp5 = pp5 == null ? null : pp5.trim();
	}

	public String getPp6() {
		return pp6 == null ? "" : pp6.trim();
	}

	public void setPp6(String pp6) {
		this.pp6 = pp6 == null ? null : pp6.trim();
	}

	public String getPp7() {
		return pp7 == null ? "" : pp7.trim();
	}

	public void setPp7(String pp7) {
		this.pp7 = pp7 == null ? null : pp7.trim();
	}

	public String getPp8() {
		return pp8 == null ? "" : pp8.trim();
	}

	public void setPp8(String pp8) {
		this.pp8 = pp8 == null ? null : pp8.trim();
	}

	public String getPp9() {
		return pp9 == null ? "" : pp9.trim();
	}

	public void setPp9(String pp9) {
		this.pp9 = pp9 == null ? null : pp9.trim();
	}

	public String getPp10() {
		return pp10 == null ? "" : pp10.trim();
	}

	public void setPp10(String pp10) {
		this.pp10 = pp10 == null ? null : pp10.trim();
	}

	public List<ZxErpFile> getAttachment() {
		return attachment == null ? Lists.newArrayList() : attachment;
	}

	public void setAttachment(List<ZxErpFile> attachment) {
		this.attachment = attachment == null ? null : attachment;
	}

	public List<ZxCtContrClaimItem> getContrClaimItemList() {
		return contrClaimItemList == null ? Lists.newArrayList() : contrClaimItemList;
	}

	public void setContrClaimItemList(List<ZxCtContrClaimItem> contrClaimItemList) {
		this.contrClaimItemList = contrClaimItemList == null ? null : contrClaimItemList;
	}

	public String getRemarks() {
		return remarks == null ? "" : remarks.trim();
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks == null ? null : remarks.trim();
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
