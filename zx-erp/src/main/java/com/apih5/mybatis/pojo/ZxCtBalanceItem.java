package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.apih5.framework.entity.BasePojo;

public class ZxCtBalanceItem extends BasePojo {
	// 主键ID
	private String id;

	// 计量单ID
	private String balID;

	// 管理单元ID
	private String muID;

	// 清单ID
	private String workID;

	// 单价
	private BigDecimal price;

	// 本期合同内计量数量
	private BigDecimal balQty;

	// 本期合同外计量数量
	private BigDecimal balAltQty;

	// 计量金额
	private BigDecimal balAmt;

	// 清单上报量
	private BigDecimal reqQty;

	// 变更上报量
	private BigDecimal reqAltQty;

	// 上报金额
	private BigDecimal reqAmt;

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

	// editTime
	private Date editTime;

	// 本期变更增补金额
	private BigDecimal changeAltAmt;

	// 期次
	private String period;

	// 本期末计量数量
	private BigDecimal thisTotalQty;

	// 本期末计量金额
	private BigDecimal thisTotalAmt;

	// 上期末计量数量
	private BigDecimal lastTotalQty;

	// 上期末计量金额
	private BigDecimal lastTotalAmt;

	// 项目ID
	private String orgID;

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

	public String getBalID() {
		return balID == null ? "" : balID.trim();
	}

	public void setBalID(String balID) {
		this.balID = balID == null ? null : balID.trim();
	}

	public String getMuID() {
		return muID == null ? "" : muID.trim();
	}

	public void setMuID(String muID) {
		this.muID = muID == null ? null : muID.trim();
	}

	public String getWorkID() {
		return workID == null ? "" : workID.trim();
	}

	public void setWorkID(String workID) {
		this.workID = workID == null ? null : workID.trim();
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getBalQty() {
		return balQty;
	}

	public void setBalQty(BigDecimal balQty) {
		this.balQty = balQty;
	}

	public BigDecimal getBalAltQty() {
		return balAltQty;
	}

	public void setBalAltQty(BigDecimal balAltQty) {
		this.balAltQty = balAltQty;
	}

	public BigDecimal getBalAmt() {
		return balAmt;
	}

	public void setBalAmt(BigDecimal balAmt) {
		this.balAmt = balAmt;
	}

	public BigDecimal getReqQty() {
		return reqQty;
	}

	public void setReqQty(BigDecimal reqQty) {
		this.reqQty = reqQty;
	}

	public BigDecimal getReqAltQty() {
		return reqAltQty;
	}

	public void setReqAltQty(BigDecimal reqAltQty) {
		this.reqAltQty = reqAltQty;
	}

	public BigDecimal getReqAmt() {
		return reqAmt;
	}

	public void setReqAmt(BigDecimal reqAmt) {
		this.reqAmt = reqAmt;
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

	public BigDecimal getChangeAltAmt() {
		return changeAltAmt;
	}

	public void setChangeAltAmt(BigDecimal changeAltAmt) {
		this.changeAltAmt = changeAltAmt;
	}

	public String getPeriod() {
		return period == null ? "" : period.trim();
	}

	public void setPeriod(String period) {
		this.period = period == null ? null : period.trim();
	}

	public BigDecimal getThisTotalQty() {
		return thisTotalQty;
	}

	public void setThisTotalQty(BigDecimal thisTotalQty) {
		this.thisTotalQty = thisTotalQty;
	}

	public BigDecimal getThisTotalAmt() {
		return thisTotalAmt;
	}

	public void setThisTotalAmt(BigDecimal thisTotalAmt) {
		this.thisTotalAmt = thisTotalAmt;
	}

	public BigDecimal getLastTotalQty() {
		return lastTotalQty;
	}

	public void setLastTotalQty(BigDecimal lastTotalQty) {
		this.lastTotalQty = lastTotalQty;
	}

	public BigDecimal getLastTotalAmt() {
		return lastTotalAmt;
	}

	public void setLastTotalAmt(BigDecimal lastTotalAmt) {
		this.lastTotalAmt = lastTotalAmt;
	}

	public String getOrgID() {
		return orgID == null ? "" : orgID.trim();
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID == null ? null : orgID.trim();
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
