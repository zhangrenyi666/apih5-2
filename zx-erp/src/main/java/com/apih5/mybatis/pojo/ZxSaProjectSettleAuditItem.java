package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.apih5.framework.entity.BasePojo;

public class ZxSaProjectSettleAuditItem extends BasePojo {
	// 主键ID
	private String projectSettleAuditItemId;

	// 结算单主键ID
	private String projectSettleAuditId;

	// 项目ID
	private String orgID;

	// 合同ID
	private String contractID;

	// 期次
	private String period;

	// 统计项编号
	private String statisticsNo;

	// 统计项
	private String statisticsName;

	// 本期(元)
	private String thisAmt;

	// 开累(元)
	private String totalAmt;

	// 最后编辑时间
	private Date editTime;

	// 比例
	private BigDecimal rate;

	// 统计项ID
	private String statisticsID;

	// 统计项类型
	private String statisticsType;

	// 上期金额
	private BigDecimal upAmt;

	// 所属公司ID
	private String comID;

	// 所属公司
	private String comName;

	// 所属公司排序
	private String comOrders;

	// 财务系统ID
	private String fiID;

	// 备注
	private String remark;

	// 质量保证金税率
	private BigDecimal qualityMarginTax;

	// 安全生产保证金税率
	private BigDecimal safetyDepositTax;

	// 农民工工资偿付保证金税率
	private BigDecimal wagePaymentBondTax;

	// 排序
	private int sort = 0;

	public String getProjectSettleAuditItemId() {
		return projectSettleAuditItemId == null ? "" : projectSettleAuditItemId.trim();
	}

	public void setProjectSettleAuditItemId(String projectSettleAuditItemId) {
		this.projectSettleAuditItemId = projectSettleAuditItemId == null ? null : projectSettleAuditItemId.trim();
	}

	public String getProjectSettleAuditId() {
		return projectSettleAuditId == null ? "" : projectSettleAuditId.trim();
	}

	public void setProjectSettleAuditId(String projectSettleAuditId) {
		this.projectSettleAuditId = projectSettleAuditId == null ? null : projectSettleAuditId.trim();
	}

	public String getOrgID() {
		return orgID == null ? "" : orgID.trim();
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID == null ? null : orgID.trim();
	}

	public String getContractID() {
		return contractID == null ? "" : contractID.trim();
	}

	public void setContractID(String contractID) {
		this.contractID = contractID == null ? null : contractID.trim();
	}

	public String getPeriod() {
		return period == null ? "" : period.trim();
	}

	public void setPeriod(String period) {
		this.period = period == null ? null : period.trim();
	}

	public String getStatisticsNo() {
		return statisticsNo == null ? "" : statisticsNo.trim();
	}

	public void setStatisticsNo(String statisticsNo) {
		this.statisticsNo = statisticsNo == null ? null : statisticsNo.trim();
	}

	public String getStatisticsName() {
		return statisticsName == null ? "" : statisticsName.trim();
	}

	public void setStatisticsName(String statisticsName) {
		this.statisticsName = statisticsName == null ? null : statisticsName.trim();
	}

	public String getThisAmt() {
		return thisAmt == null ? "" : thisAmt.trim();
	}

	public void setThisAmt(String thisAmt) {
		this.thisAmt = thisAmt == null ? null : thisAmt.trim();
	}

	public String getTotalAmt() {
		return totalAmt == null ? "" : totalAmt.trim();
	}

	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt == null ? null : totalAmt.trim();
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public String getStatisticsID() {
		return statisticsID == null ? "" : statisticsID.trim();
	}

	public void setStatisticsID(String statisticsID) {
		this.statisticsID = statisticsID == null ? null : statisticsID.trim();
	}

	public String getStatisticsType() {
		return statisticsType == null ? "" : statisticsType.trim();
	}

	public void setStatisticsType(String statisticsType) {
		this.statisticsType = statisticsType == null ? null : statisticsType.trim();
	}

	public BigDecimal getUpAmt() {
		return upAmt;
	}

	public void setUpAmt(BigDecimal upAmt) {
		this.upAmt = upAmt;
	}

	public String getComID() {
		return comID == null ? "" : comID.trim();
	}

	public void setComID(String comID) {
		this.comID = comID == null ? null : comID.trim();
	}

	public String getComName() {
		return comName == null ? "" : comName.trim();
	}

	public void setComName(String comName) {
		this.comName = comName == null ? null : comName.trim();
	}

	public String getComOrders() {
		return comOrders == null ? "" : comOrders.trim();
	}

	public void setComOrders(String comOrders) {
		this.comOrders = comOrders == null ? null : comOrders.trim();
	}

	public String getFiID() {
		return fiID == null ? "" : fiID.trim();
	}

	public void setFiID(String fiID) {
		this.fiID = fiID == null ? null : fiID.trim();
	}

	public String getRemark() {
		return remark == null ? "" : remark.trim();
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public BigDecimal getQualityMarginTax() {
		return qualityMarginTax;
	}

	public void setQualityMarginTax(BigDecimal qualityMarginTax) {
		this.qualityMarginTax = qualityMarginTax;
	}

	public BigDecimal getSafetyDepositTax() {
		return safetyDepositTax;
	}

	public void setSafetyDepositTax(BigDecimal safetyDepositTax) {
		this.safetyDepositTax = safetyDepositTax;
	}

	public BigDecimal getWagePaymentBondTax() {
		return wagePaymentBondTax;
	}

	public void setWagePaymentBondTax(BigDecimal wagePaymentBondTax) {
		this.wagePaymentBondTax = wagePaymentBondTax;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
