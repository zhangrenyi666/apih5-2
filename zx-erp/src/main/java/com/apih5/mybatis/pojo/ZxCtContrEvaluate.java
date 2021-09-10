package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;

public class ZxCtContrEvaluate extends BasePojo {
	// 附件
	private List<ZxErpFile> attachment;

	// 主键ID
	private String id;

	// 合同ID
	private String contractID;

	// 评价结果
	private String evalLevel;

	// 履行开始日期
	private Date startDate;

	// 履行结束日期
	private Date endDate;

	// 期次
	private String period;

	// 评价内容
	private String content;

	// 检查方
	private String checkunit;

	// 检查方代表
	private String checker;

	// 被检方
	private String byCheckunit;

	// 被检方代表
	private String byChecker;

	// 质量
	private String quanlityInfo;

	// 职业健康安全维护
	private String safeInfo;

	// 进度
	private String processInfo;

	// 计量支付
	private String measureInfo;

	// 机械业主合同管理-业主合同台账-信用评价(原表iect_ContrEvaluate
	private String equipInfo;

	// 人员配备
	private String labourInfo;

	// 物资配备
	private String materialInfo;

	// 当地纠纷
	private String dissensionInfo;

	// 民工工资
	private String wageInfo;

	// 反馈信息
	private String feebackInfo;

	// 评价日期
	private Date recordDate;

	// 记录人
	private String recordMan;

	// 审核人
	private String auditor;

	// 评价结果
	private String auditOption;

	// combProp
	private String combProp;

	// pp1
	private String pp1;

	// pp2
	private String pp2;

	// pp3
	private String pp3;

	// pp4
	private String pp4;

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

	// 最后编辑时间
	private Date editTime;

	// 评价类型
	private String type;

	// 备注
	private String remarks;

	// 排序
	private int sort = 0;

	public List<ZxErpFile> getAttachment() {
		return attachment == null ? Lists.newArrayList() : attachment;
	}

	public void setAttachment(List<ZxErpFile> attachment) {
		this.attachment = attachment == null ? null : attachment;
	}

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

	public String getEvalLevel() {
		return evalLevel == null ? "" : evalLevel.trim();
	}

	public void setEvalLevel(String evalLevel) {
		this.evalLevel = evalLevel == null ? null : evalLevel.trim();
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

	public String getPeriod() {
		return period == null ? "" : period.trim();
	}

	public void setPeriod(String period) {
		this.period = period == null ? null : period.trim();
	}

	public String getContent() {
		return content == null ? "" : content.trim();
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public String getCheckunit() {
		return checkunit == null ? "" : checkunit.trim();
	}

	public void setCheckunit(String checkunit) {
		this.checkunit = checkunit == null ? null : checkunit.trim();
	}

	public String getChecker() {
		return checker == null ? "" : checker.trim();
	}

	public void setChecker(String checker) {
		this.checker = checker == null ? null : checker.trim();
	}

	public String getByCheckunit() {
		return byCheckunit == null ? "" : byCheckunit.trim();
	}

	public void setByCheckunit(String byCheckunit) {
		this.byCheckunit = byCheckunit == null ? null : byCheckunit.trim();
	}

	public String getByChecker() {
		return byChecker == null ? "" : byChecker.trim();
	}

	public void setByChecker(String byChecker) {
		this.byChecker = byChecker == null ? null : byChecker.trim();
	}

	public String getQuanlityInfo() {
		return quanlityInfo == null ? "" : quanlityInfo.trim();
	}

	public void setQuanlityInfo(String quanlityInfo) {
		this.quanlityInfo = quanlityInfo == null ? null : quanlityInfo.trim();
	}

	public String getSafeInfo() {
		return safeInfo == null ? "" : safeInfo.trim();
	}

	public void setSafeInfo(String safeInfo) {
		this.safeInfo = safeInfo == null ? null : safeInfo.trim();
	}

	public String getProcessInfo() {
		return processInfo == null ? "" : processInfo.trim();
	}

	public void setProcessInfo(String processInfo) {
		this.processInfo = processInfo == null ? null : processInfo.trim();
	}

	public String getMeasureInfo() {
		return measureInfo == null ? "" : measureInfo.trim();
	}

	public void setMeasureInfo(String measureInfo) {
		this.measureInfo = measureInfo == null ? null : measureInfo.trim();
	}

	public String getEquipInfo() {
		return equipInfo == null ? "" : equipInfo.trim();
	}

	public void setEquipInfo(String equipInfo) {
		this.equipInfo = equipInfo == null ? null : equipInfo.trim();
	}

	public String getLabourInfo() {
		return labourInfo == null ? "" : labourInfo.trim();
	}

	public void setLabourInfo(String labourInfo) {
		this.labourInfo = labourInfo == null ? null : labourInfo.trim();
	}

	public String getMaterialInfo() {
		return materialInfo == null ? "" : materialInfo.trim();
	}

	public void setMaterialInfo(String materialInfo) {
		this.materialInfo = materialInfo == null ? null : materialInfo.trim();
	}

	public String getDissensionInfo() {
		return dissensionInfo == null ? "" : dissensionInfo.trim();
	}

	public void setDissensionInfo(String dissensionInfo) {
		this.dissensionInfo = dissensionInfo == null ? null : dissensionInfo.trim();
	}

	public String getWageInfo() {
		return wageInfo == null ? "" : wageInfo.trim();
	}

	public void setWageInfo(String wageInfo) {
		this.wageInfo = wageInfo == null ? null : wageInfo.trim();
	}

	public String getFeebackInfo() {
		return feebackInfo == null ? "" : feebackInfo.trim();
	}

	public void setFeebackInfo(String feebackInfo) {
		this.feebackInfo = feebackInfo == null ? null : feebackInfo.trim();
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public String getRecordMan() {
		return recordMan == null ? "" : recordMan.trim();
	}

	public void setRecordMan(String recordMan) {
		this.recordMan = recordMan == null ? null : recordMan.trim();
	}

	public String getAuditor() {
		return auditor == null ? "" : auditor.trim();
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor == null ? null : auditor.trim();
	}

	public String getAuditOption() {
		return auditOption == null ? "" : auditOption.trim();
	}

	public void setAuditOption(String auditOption) {
		this.auditOption = auditOption == null ? null : auditOption.trim();
	}

	public String getCombProp() {
		return combProp == null ? "" : combProp.trim();
	}

	public void setCombProp(String combProp) {
		this.combProp = combProp == null ? null : combProp.trim();
	}

	public String getPp1() {
		return pp1 == null ? "" : pp1.trim();
	}

	public void setPp1(String pp1) {
		this.pp1 = pp1 == null ? null : pp1.trim();
	}

	public String getPp2() {
		return pp2 == null ? "" : pp2.trim();
	}

	public void setPp2(String pp2) {
		this.pp2 = pp2 == null ? null : pp2.trim();
	}

	public String getPp3() {
		return pp3 == null ? "" : pp3.trim();
	}

	public void setPp3(String pp3) {
		this.pp3 = pp3 == null ? null : pp3.trim();
	}

	public String getPp4() {
		return pp4 == null ? "" : pp4.trim();
	}

	public void setPp4(String pp4) {
		this.pp4 = pp4 == null ? null : pp4.trim();
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

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public String getType() {
		return type == null ? "" : type.trim();
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
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
