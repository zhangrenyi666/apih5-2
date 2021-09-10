package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;

public class ZxSaProjectWorkSettle extends BasePojo {
	// 主键ID
	private String projectWorkSettleId;

	// 项目ID
	private String orgID;

	// 项目名称
	private String orgName;

	// 合同ID
	private String contractID;

	// 工程结算表ID
	private String billID;

	// 签认单ID
	private String signedOrders;

	// 签认单编号
	private String signedNos;

	// 含税合同金额(万元)
	private BigDecimal contractAmt;

	// 变更后含税合同金额(万元)
	private BigDecimal changeAmt;

	// 本期清单结算含税金额(元)
	private BigDecimal thisAmt;

	// 累计清单结算含税金额(元)
	private BigDecimal totalAmt;

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

	// 本期清单结算不含税金额(元)
	private BigDecimal thisAmtNoTax;

	// 本期清单结算税额(元)
	private BigDecimal thisAmtTax;

	// 附件
	private List<ZxErpFile> zxErpFileList;

	// 明细列表
	private List<ZxSaProjectWorkSettleItem> zxSaProjectWorkSettleItemList;

	// 上期末累计清单结算含税金额(元)
	private BigDecimal upTotalAmt;

	// 备注
	private String remark;

	// 排序
	private int sort = 0;

	public String getProjectWorkSettleId() {
		return projectWorkSettleId == null ? "" : projectWorkSettleId.trim();
	}

	public void setProjectWorkSettleId(String projectWorkSettleId) {
		this.projectWorkSettleId = projectWorkSettleId == null ? null : projectWorkSettleId.trim();
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

	public String getSignedOrders() {
		return signedOrders == null ? "" : signedOrders.trim();
	}

	public void setSignedOrders(String signedOrders) {
		this.signedOrders = signedOrders == null ? null : signedOrders.trim();
	}

	public String getSignedNos() {
		return signedNos == null ? "" : signedNos.trim();
	}

	public void setSignedNos(String signedNos) {
		this.signedNos = signedNos == null ? null : signedNos.trim();
	}

	public BigDecimal getContractAmt() {
		return contractAmt;
	}

	public void setContractAmt(BigDecimal contractAmt) {
		this.contractAmt = contractAmt;
	}

	public BigDecimal getChangeAmt() {
		return changeAmt;
	}

	public void setChangeAmt(BigDecimal changeAmt) {
		this.changeAmt = changeAmt;
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

	public List<ZxErpFile> getZxErpFileList() {
		return zxErpFileList == null ? Lists.newArrayList() : zxErpFileList;
	}

	public void setZxErpFileList(List<ZxErpFile> zxErpFileList) {
		this.zxErpFileList = zxErpFileList == null ? null : zxErpFileList;
	}

	public List<ZxSaProjectWorkSettleItem> getZxSaProjectWorkSettleItemList() {
		return zxSaProjectWorkSettleItemList == null ? Lists.newArrayList() : zxSaProjectWorkSettleItemList;
	}

	public void setZxSaProjectWorkSettleItemList(List<ZxSaProjectWorkSettleItem> zxSaProjectWorkSettleItemList) {
		this.zxSaProjectWorkSettleItemList = zxSaProjectWorkSettleItemList == null ? null
				: zxSaProjectWorkSettleItemList;
	}

	public BigDecimal getUpTotalAmt() {
		return upTotalAmt;
	}

	public void setUpTotalAmt(BigDecimal upTotalAmt) {
		this.upTotalAmt = upTotalAmt;
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
