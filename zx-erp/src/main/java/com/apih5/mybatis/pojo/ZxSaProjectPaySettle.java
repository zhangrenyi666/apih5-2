package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;

public class ZxSaProjectPaySettle extends BasePojo {
	// 主键
	private String projectPaySettleId;

	// 项目ID
	private String orgID;

	// 项目名称
	private String orgName;

	// 合同ID
	private String contractID;

	// 工程结算表ID
	private String billID;

	// 本期支付项结算金额(元)
	private BigDecimal thisAmt;

	// 累计支付项结算金额(元)
	private BigDecimal totalAmt;

	// 上期末累计支付项结算金额(元)
	private BigDecimal upAmt;

	// 物资调拨费本期结算小计(元)
	private BigDecimal materialAmt;

	// 上期末物资调拨费结算小计(元)
	private BigDecimal upMaterialAmt;

	// 机械使用费本期结算小计(元)
	private BigDecimal machineAmt;

	// 上期末机械使用费结算小计(元)
	private BigDecimal upMachineAmt;

	// 临时用工费本期结算小计(元)
	private BigDecimal tempAmt;

	// 上期末临时用工费结算小计(元)
	private BigDecimal upTempAmt;

	// 奖罚金额本期结算小计(元)
	private BigDecimal fineAmt;

	// 上期末奖罚金额结算小计(元)
	private BigDecimal upFineAmt;

	// 补偿金额本期结算小计(元)
	private BigDecimal recoupAmt;

	// 上期末补偿金额结算小计(元)
	private BigDecimal upRecoupAmt;

	// 其他款项本期结算小计(元)
	private BigDecimal otherAmt;

	// 上期末其他款项结算小计(元)
	private BigDecimal upOtherAmt;

	// 最后编辑时间
	private Date editTime;

	// 结算期次
	private String period;

	// 所属公司ID
	private String comID;

	// 所属公司
	private String comName;

	// 所属公司排序
	private String comOrders;

	// 本期支付项结算不含税金额(元)
	private BigDecimal thisAmtNoTax;

	// 本期结算税额
	private BigDecimal thisAmtTax;

	// 税率
	private String taxRate;

	// 附件
	private List<ZxErpFile> zxErpFileList;

	// 明细
	private List<ZxSaProjectPaySettleItem> ProjectPaySettleItemList;

	// 备注
	private String remark;

	// 排序
	private int sort = 0;

	public String getProjectPaySettleId() {
		return projectPaySettleId == null ? "" : projectPaySettleId.trim();
	}

	public void setProjectPaySettleId(String projectPaySettleId) {
		this.projectPaySettleId = projectPaySettleId == null ? null : projectPaySettleId.trim();
	}

	public String getOrgID() {
		return orgID == null ? "" : orgID.trim();
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID == null ? null : orgID.trim();
	}

	public String getOrgName() {
		return orgName == null ? "" : orgName.trim();
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName == null ? null : orgName.trim();
	}

	public String getContractID() {
		return contractID == null ? "" : contractID.trim();
	}

	public void setContractID(String contractID) {
		this.contractID = contractID == null ? null : contractID.trim();
	}

	public String getBillID() {
		return billID == null ? "" : billID.trim();
	}

	public void setBillID(String billID) {
		this.billID = billID == null ? null : billID.trim();
	}

	public BigDecimal getThisAmt() {
		return thisAmt;
	}

	public void setThisAmt(BigDecimal thisAmt) {
		this.thisAmt = thisAmt;
	}

	public BigDecimal getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

	public BigDecimal getUpAmt() {
		return upAmt;
	}

	public void setUpAmt(BigDecimal upAmt) {
		this.upAmt = upAmt;
	}

	public BigDecimal getMaterialAmt() {
		return materialAmt;
	}

	public void setMaterialAmt(BigDecimal materialAmt) {
		this.materialAmt = materialAmt;
	}

	public BigDecimal getUpMaterialAmt() {
		return upMaterialAmt;
	}

	public void setUpMaterialAmt(BigDecimal upMaterialAmt) {
		this.upMaterialAmt = upMaterialAmt;
	}

	public BigDecimal getMachineAmt() {
		return machineAmt;
	}

	public void setMachineAmt(BigDecimal machineAmt) {
		this.machineAmt = machineAmt;
	}

	public BigDecimal getUpMachineAmt() {
		return upMachineAmt;
	}

	public void setUpMachineAmt(BigDecimal upMachineAmt) {
		this.upMachineAmt = upMachineAmt;
	}

	public BigDecimal getTempAmt() {
		return tempAmt;
	}

	public void setTempAmt(BigDecimal tempAmt) {
		this.tempAmt = tempAmt;
	}

	public BigDecimal getUpTempAmt() {
		return upTempAmt;
	}

	public void setUpTempAmt(BigDecimal upTempAmt) {
		this.upTempAmt = upTempAmt;
	}

	public BigDecimal getFineAmt() {
		return fineAmt;
	}

	public void setFineAmt(BigDecimal fineAmt) {
		this.fineAmt = fineAmt;
	}

	public BigDecimal getUpFineAmt() {
		return upFineAmt;
	}

	public void setUpFineAmt(BigDecimal upFineAmt) {
		this.upFineAmt = upFineAmt;
	}

	public BigDecimal getRecoupAmt() {
		return recoupAmt;
	}

	public void setRecoupAmt(BigDecimal recoupAmt) {
		this.recoupAmt = recoupAmt;
	}

	public BigDecimal getUpRecoupAmt() {
		return upRecoupAmt;
	}

	public void setUpRecoupAmt(BigDecimal upRecoupAmt) {
		this.upRecoupAmt = upRecoupAmt;
	}

	public BigDecimal getOtherAmt() {
		return otherAmt;
	}

	public void setOtherAmt(BigDecimal otherAmt) {
		this.otherAmt = otherAmt;
	}

	public BigDecimal getUpOtherAmt() {
		return upOtherAmt;
	}

	public void setUpOtherAmt(BigDecimal upOtherAmt) {
		this.upOtherAmt = upOtherAmt;
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

	public BigDecimal getThisAmtNoTax() {
		return thisAmtNoTax;
	}

	public void setThisAmtNoTax(BigDecimal thisAmtNoTax) {
		this.thisAmtNoTax = thisAmtNoTax;
	}

	public BigDecimal getThisAmtTax() {
		return thisAmtTax;
	}

	public void setThisAmtTax(BigDecimal thisAmtTax) {
		this.thisAmtTax = thisAmtTax;
	}

	public String getTaxRate() {
		return taxRate == null ? "" : taxRate.trim();
	}

	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate == null ? null : taxRate.trim();
	}

	public List<ZxErpFile> getZxErpFileList() {
		return zxErpFileList == null ? Lists.newArrayList() : zxErpFileList;
	}

	public void setZxErpFileList(List<ZxErpFile> zxErpFileList) {
		this.zxErpFileList = zxErpFileList == null ? null : zxErpFileList;
	}

	public List<ZxSaProjectPaySettleItem> getProjectPaySettleItemList() {
		return ProjectPaySettleItemList == null ? Lists.newArrayList() : ProjectPaySettleItemList;
	}

	public void setProjectPaySettleItemList(List<ZxSaProjectPaySettleItem> ProjectPaySettleItemList) {
		this.ProjectPaySettleItemList = ProjectPaySettleItemList == null ? null : ProjectPaySettleItemList;
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
