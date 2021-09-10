package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;

public class ZxCtDaywork extends BasePojo {
	// 主键ID
	private String id;

	// 计量单ID
	private String balID;

	// 业务日期
	private Date busDate;

	// 管理单元ID
	private String muID;

	// 计量类型
	private String balanceType;

	// 单号
	private String billNo;

	// 金额
	private BigDecimal totalAmt;

	// 记录人
	private String reporter;

	// 记录日期
	private Date createDate;

	// 审核人
	private String auditor;

	// 审核日期
	private Date auditDate;

	// 审核意见
	private String auditMsg;

	// 审核状态
	private String auditStatus;

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

	// 最后编辑日期
	private Date editTime;

	// 计日工
	private BigDecimal dayPrice;

	// 暂定金
	private BigDecimal tempPrice;

	// 备注
	private String remark;

	// 附件
	private List<ZxErpFile> attachment;

	// 资源列表
	private List<ZxCtDayworkItem> dayworkItemList;

	// 排序
	private int sort = 0;

	public String getId() {
		return id == null ? "" : id.trim();
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getBalID() {
		return balID == null ? "" : balID.trim();
	}

	public void setBalID(String balID) {
		this.balID = balID == null ? null : balID.trim();
	}

	public Date getBusDate() {
		return busDate;
	}

	public void setBusDate(Date busDate) {
		this.busDate = busDate;
	}

	public String getMuID() {
		return muID == null ? "" : muID.trim();
	}

	public void setMuID(String muID) {
		this.muID = muID == null ? null : muID.trim();
	}

	public String getBalanceType() {
		return balanceType == null ? "" : balanceType.trim();
	}

	public void setBalanceType(String balanceType) {
		this.balanceType = balanceType == null ? null : balanceType.trim();
	}

	public String getBillNo() {
		return billNo == null ? "" : billNo.trim();
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo == null ? null : billNo.trim();
	}

	public BigDecimal getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

	public String getReporter() {
		return reporter == null ? "" : reporter.trim();
	}

	public void setReporter(String reporter) {
		this.reporter = reporter == null ? null : reporter.trim();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getAuditor() {
		return auditor == null ? "" : auditor.trim();
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor == null ? null : auditor.trim();
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getAuditMsg() {
		return auditMsg == null ? "" : auditMsg.trim();
	}

	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg == null ? null : auditMsg.trim();
	}

	public String getAuditStatus() {
		return auditStatus == null ? "" : auditStatus.trim();
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus == null ? null : auditStatus.trim();
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

	public BigDecimal getDayPrice() {
		return dayPrice;
	}

	public void setDayPrice(BigDecimal dayPrice) {
		this.dayPrice = dayPrice;
	}

	public BigDecimal getTempPrice() {
		return tempPrice;
	}

	public void setTempPrice(BigDecimal tempPrice) {
		this.tempPrice = tempPrice;
	}

	public String getRemark() {
		return remark == null ? "" : remark.trim();
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public List<ZxErpFile> getAttachment() {
		return attachment == null ? Lists.newArrayList() : attachment;
	}

	public void setAttachment(List<ZxErpFile> attachment) {
		this.attachment = attachment == null ? null : attachment;
	}

	public List<ZxCtDayworkItem> getDayworkItemList() {
		return dayworkItemList == null ? Lists.newArrayList() : dayworkItemList;
	}

	public void setDayworkItemList(List<ZxCtDayworkItem> dayworkItemList) {
		this.dayworkItemList = dayworkItemList == null ? null : dayworkItemList;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
