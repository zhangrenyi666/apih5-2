package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.apih5.framework.entity.BasePojo;

public class ZxSaProjectPaySettleItem extends BasePojo {
	// 主键
	private String projectPaySettleItemId;

	// 主表ID
	private String projectPaySettleId;

	// 合同ID
	private String contractID;

	// 序号
	private int orderNum = 0;

	// 支付项ID
	private String payID;

	// 编号
	private String payNo;

	// 支付项类型
	private String payType;

	// 名称
	private String payName;

	// 单位
	private String unit;

	// 数量
	private BigDecimal qty;

	// 单价
	private BigDecimal price;

	// 本期结算金额(元)
	private BigDecimal thisAmt;

	// 上期末结算金额(元)
	private BigDecimal upAmt;

	// 最后编辑时间
	private Date editTime;

	// 结算期次
	private String period;

	// 是否已修改
	private String isFixed;

	// 所属公司ID
	private String comID;

	// 所属公司
	private String comName;

	// 所属公司排序
	private String comOrders;

	// 税率(%)
	private String taxRate;

	// 不含税金额
	private BigDecimal thisAmtNoTax;

	// 税额
	private BigDecimal thisAmtTax;

	// 机构ID
	private String orgID;

	// 细目编号
	private String workNo;

	// 旧单位
	private String oldUnit;

	// 细目名称
	private String workName;

	// 细目类型
	private String workType;

	// 备注
	private String remark;

	// 排序
	private int sort = 0;

	public String getProjectPaySettleItemId() {
		return projectPaySettleItemId == null ? "" : projectPaySettleItemId.trim();
	}

	public void setProjectPaySettleItemId(String projectPaySettleItemId) {
		this.projectPaySettleItemId = projectPaySettleItemId == null ? null : projectPaySettleItemId.trim();
	}

	public String getProjectPaySettleId() {
		return projectPaySettleId == null ? "" : projectPaySettleId.trim();
	}

	public void setProjectPaySettleId(String projectPaySettleId) {
		this.projectPaySettleId = projectPaySettleId == null ? null : projectPaySettleId.trim();
	}

	public String getContractID() {
		return contractID == null ? "" : contractID.trim();
	}

	public void setContractID(String contractID) {
		this.contractID = contractID == null ? null : contractID.trim();
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getPayID() {
		return payID == null ? "" : payID.trim();
	}

	public void setPayID(String payID) {
		this.payID = payID == null ? null : payID.trim();
	}

	public String getPayNo() {
		return payNo == null ? "" : payNo.trim();
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo == null ? null : payNo.trim();
	}

	public String getPayType() {
		return payType == null ? "" : payType.trim();
	}

	public void setPayType(String payType) {
		this.payType = payType == null ? null : payType.trim();
	}

	public String getPayName() {
		return payName == null ? "" : payName.trim();
	}

	public void setPayName(String payName) {
		this.payName = payName == null ? null : payName.trim();
	}

	public String getUnit() {
		return unit == null ? "" : unit.trim();
	}

	public void setUnit(String unit) {
		this.unit = unit == null ? null : unit.trim();
	}

	public BigDecimal getQty() {
		return qty == null ? new BigDecimal(0) : qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public BigDecimal getPrice() {
		return price == null ? new BigDecimal(0) : price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getThisAmt() {
		return thisAmt == null ? new BigDecimal(0) : thisAmt;
	}

	public void setThisAmt(BigDecimal thisAmt) {
		this.thisAmt = thisAmt;
	}

	public BigDecimal getUpAmt() {
		return upAmt == null ? new BigDecimal(0) : upAmt;
	}

	public void setUpAmt(BigDecimal upAmt) {
		this.upAmt = upAmt;
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public String getPeriod() {
		return period == null ? "" : period.trim();
	}

	public void setPeriod(String period) {
		this.period = period == null ? null : period.trim();
	}

	public String getIsFixed() {
		return isFixed == null ? "" : isFixed.trim();
	}

	public void setIsFixed(String isFixed) {
		this.isFixed = isFixed == null ? null : isFixed.trim();
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

	public String getTaxRate() {
		return taxRate == null ? "" : taxRate.trim();
	}

	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate == null ? null : taxRate.trim();
	}

	public BigDecimal getThisAmtNoTax() {
		return thisAmtNoTax == null ? new BigDecimal(0) : thisAmtNoTax;
	}

	public void setThisAmtNoTax(BigDecimal thisAmtNoTax) {
		this.thisAmtNoTax = thisAmtNoTax;
	}

	public BigDecimal getThisAmtTax() {
		return thisAmtTax == null ? new BigDecimal(0) : thisAmtTax;
	}

	public void setThisAmtTax(BigDecimal thisAmtTax) {
		this.thisAmtTax = thisAmtTax;
	}

	public String getOrgID() {
		return orgID == null ? "" : orgID.trim();
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID == null ? null : orgID.trim();
	}

	public String getWorkNo() {
		return workNo == null ? "" : workNo.trim();
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo == null ? null : workNo.trim();
	}

	public String getOldUnit() {
		return oldUnit == null ? "" : oldUnit.trim();
	}

	public void setOldUnit(String oldUnit) {
		this.oldUnit = oldUnit == null ? null : oldUnit.trim();
	}

	public String getWorkName() {
		return workName == null ? "" : workName.trim();
	}

	public void setWorkName(String workName) {
		this.workName = workName == null ? null : workName.trim();
	}

	public String getWorkType() {
		return workType == null ? "" : workType.trim();
	}

	public void setWorkType(String workType) {
		this.workType = workType == null ? null : workType.trim();
	}

	public String getRemark() {
		return remark == null ? "" : remark.trim();
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
