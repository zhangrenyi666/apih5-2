package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.apih5.framework.entity.BasePojo;

public class ZxSaProjectWorkSettleItem extends BasePojo {
	// 主键ID
	private String projectWorkSettleItemId;

	// 清单表主键
	private String projectWorkSettleId;

	// 合同ID
	private String contractID;

	// 序号
	private int orderNum = 0;

	// 签认单ID
	private String signedOrderID;

	// 签认单编号
	private String signedNo;

	// 签认单明细ID
	private String signedOrderItemID;

	// 细目ID
	private String workID;

	// 细目编号
	private String workNo;

	// 细目名称
	private String workName;

	// 父节点
	private String parentID;

	// 树节点
	private String treeNode;

	// 是否叶子节点
	private String isLeaf;

	// 单位
	private String unit;

	// 含税单价(元)
	private BigDecimal price;

	// 合同数量
	private BigDecimal contractQty;

	// 含税合同金额(元)
	private BigDecimal contractAmt;

	// 变更后数量
	private BigDecimal changeQty;

	// 变更后含税金额
	private BigDecimal changeAmt;

	// 本期结算合同数量
	private BigDecimal thisQty;

	// 本期结算变更数量
	private BigDecimal thisChangeQty;

	// 本期结算数量小计
	private BigDecimal thisTotalQty;

	// 本期结算含税金额(元)
	private BigDecimal thisTotalAmt;

	// 累计结算合同数量
	private BigDecimal allQty;

	// 累计结算变更数量
	private BigDecimal allChangeQty;

	// 累计结算数量小计
	private BigDecimal allTotalQty;

	// 累计结算含税金额(元)
	private BigDecimal allTotalAmt;

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

	// 变更后含税单价
	private BigDecimal changePrice;

	// 税率
	private BigDecimal taxRate;

	// 计算式
	private String planning;

	// 图号
	private String mapNum;

	// 上期末累计结算数量
	private BigDecimal upAllQty;

	// 上期末累计结算含税金额
	private BigDecimal upAllTotalAmt;

	// 本期清单结算含税金额(元)
	private BigDecimal thisAmt;

	// 本期清单结算不含税金额(元)
	private BigDecimal thisAmtNoTax;

	// 本期清单结算税额(元)
	private BigDecimal thisAmtTax;

	// 累计清单结算含税金额(元)
	private BigDecimal totalAmt;

	// 结算单主表ID
	private String settleauditId;

	// 不含税合同单价（元）
	private BigDecimal priceNoTax;

	// 合同税金（元）
	private BigDecimal contractTax;

	// 原合同不含税合同金额（元）
	private BigDecimal contractAmtNoTax;

	// 原合同合同税额（元）
	private BigDecimal contractAmtTax;

	// 变更后不含税合同金额（元）
	private BigDecimal changeAmtNoTax;

	// 变更后合同税额
	private BigDecimal changeAmtTax;

	// 本期结算不含税金额(元)
	private BigDecimal thisTotalAmtNoTax;

	// 本期结算税额(元)
	private BigDecimal thisTotalAmtTax;

	// 至上期末累计结算工程数量
	private BigDecimal upQty;

	// 至上期末累计结算含税金额(元)
	private BigDecimal upTotalAmt;

	// 至上期末累计结算不含税金额(元)
	private BigDecimal upTotalAmtNoTax;

	// 至上期末累计结算税额(元)
	private BigDecimal upTotalAmtTax;

	// 至本期末累计结算不含税金额(元)
	private BigDecimal allTotalAmtNoTax;

	// 至本期末累计结算税额(元)
	private BigDecimal allTotalAmtTax;

	// 是否抵扣
	private String isDeduct;

	// 本期结算小计
	private BigDecimal thisAmtSubTotal;

	// 至上期末结算小计
	private BigDecimal upAmtSubTotal;

	// 至本期末结算小计
	private BigDecimal allAmtSubTotal;

	// 备注
	private String remark;

	// 排序
	private int sort = 0;

	public String getProjectWorkSettleItemId() {
		return projectWorkSettleItemId == null ? "" : projectWorkSettleItemId.trim();
	}

	public void setProjectWorkSettleItemId(String projectWorkSettleItemId) {
		this.projectWorkSettleItemId = projectWorkSettleItemId == null ? null : projectWorkSettleItemId.trim();
	}

	public String getProjectWorkSettleId() {
		return projectWorkSettleId == null ? "" : projectWorkSettleId.trim();
	}

	public void setProjectWorkSettleId(String projectWorkSettleId) {
		this.projectWorkSettleId = projectWorkSettleId == null ? null : projectWorkSettleId.trim();
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

	public String getSignedOrderID() {
		return signedOrderID == null ? "" : signedOrderID.trim();
	}

	public void setSignedOrderID(String signedOrderID) {
		this.signedOrderID = signedOrderID == null ? null : signedOrderID.trim();
	}

	public String getSignedNo() {
		return signedNo == null ? "" : signedNo.trim();
	}

	public void setSignedNo(String signedNo) {
		this.signedNo = signedNo == null ? null : signedNo.trim();
	}

	public String getSignedOrderItemID() {
		return signedOrderItemID == null ? "" : signedOrderItemID.trim();
	}

	public void setSignedOrderItemID(String signedOrderItemID) {
		this.signedOrderItemID = signedOrderItemID == null ? null : signedOrderItemID.trim();
	}

	public String getWorkID() {
		return workID == null ? "" : workID.trim();
	}

	public void setWorkID(String workID) {
		this.workID = workID == null ? null : workID.trim();
	}

	public String getWorkNo() {
		return workNo == null ? "" : workNo.trim();
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo == null ? null : workNo.trim();
	}

	public String getWorkName() {
		return workName == null ? "" : workName.trim();
	}

	public void setWorkName(String workName) {
		this.workName = workName == null ? null : workName.trim();
	}

	public String getParentID() {
		return parentID == null ? "" : parentID.trim();
	}

	public void setParentID(String parentID) {
		this.parentID = parentID == null ? null : parentID.trim();
	}

	public String getTreeNode() {
		return treeNode == null ? "" : treeNode.trim();
	}

	public void setTreeNode(String treeNode) {
		this.treeNode = treeNode == null ? null : treeNode.trim();
	}

	public String getIsLeaf() {
		return isLeaf == null ? "" : isLeaf.trim();
	}

	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf == null ? null : isLeaf.trim();
	}

	public String getUnit() {
		return unit == null ? "" : unit.trim();
	}

	public void setUnit(String unit) {
		this.unit = unit == null ? null : unit.trim();
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getContractQty() {
		return contractQty;
	}

	public void setContractQty(BigDecimal contractQty) {
		this.contractQty = contractQty;
	}

	public BigDecimal getContractAmt() {
		return contractAmt;
	}

	public void setContractAmt(BigDecimal contractAmt) {
		this.contractAmt = contractAmt;
	}

	public BigDecimal getChangeQty() {
		return changeQty;
	}

	public void setChangeQty(BigDecimal changeQty) {
		this.changeQty = changeQty;
	}

	public BigDecimal getChangeAmt() {
		return changeAmt;
	}

	public void setChangeAmt(BigDecimal changeAmt) {
		this.changeAmt = changeAmt;
	}

	public BigDecimal getThisQty() {
		return thisQty;
	}

	public void setThisQty(BigDecimal thisQty) {
		this.thisQty = thisQty;
	}

	public BigDecimal getThisChangeQty() {
		return thisChangeQty;
	}

	public void setThisChangeQty(BigDecimal thisChangeQty) {
		this.thisChangeQty = thisChangeQty;
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

	public BigDecimal getAllQty() {
		return allQty;
	}

	public void setAllQty(BigDecimal allQty) {
		this.allQty = allQty;
	}

	public BigDecimal getAllChangeQty() {
		return allChangeQty;
	}

	public void setAllChangeQty(BigDecimal allChangeQty) {
		this.allChangeQty = allChangeQty;
	}

	public BigDecimal getAllTotalQty() {
		return allTotalQty;
	}

	public void setAllTotalQty(BigDecimal allTotalQty) {
		this.allTotalQty = allTotalQty;
	}

	public BigDecimal getAllTotalAmt() {
		return allTotalAmt;
	}

	public void setAllTotalAmt(BigDecimal allTotalAmt) {
		this.allTotalAmt = allTotalAmt;
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

	public BigDecimal getChangePrice() {
		return changePrice;
	}

	public void setChangePrice(BigDecimal changePrice) {
		this.changePrice = changePrice;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public String getPlanning() {
		return planning == null ? "" : planning.trim();
	}

	public void setPlanning(String planning) {
		this.planning = planning == null ? null : planning.trim();
	}

	public String getMapNum() {
		return mapNum == null ? "" : mapNum.trim();
	}

	public void setMapNum(String mapNum) {
		this.mapNum = mapNum == null ? null : mapNum.trim();
	}

	public BigDecimal getUpAllQty() {
		return upAllQty;
	}

	public void setUpAllQty(BigDecimal upAllQty) {
		this.upAllQty = upAllQty;
	}

	public BigDecimal getUpAllTotalAmt() {
		return upAllTotalAmt;
	}

	public void setUpAllTotalAmt(BigDecimal upAllTotalAmt) {
		this.upAllTotalAmt = upAllTotalAmt;
	}

	public BigDecimal getThisAmt() {
		return thisAmt;
	}

	public void setThisAmt(BigDecimal thisAmt) {
		this.thisAmt = thisAmt;
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

	public BigDecimal getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

	public String getSettleauditId() {
		return settleauditId == null ? "" : settleauditId.trim();
	}

	public void setSettleauditId(String settleauditId) {
		this.settleauditId = settleauditId == null ? null : settleauditId.trim();
	}

	public BigDecimal getPriceNoTax() {
		return priceNoTax;
	}

	public void setPriceNoTax(BigDecimal priceNoTax) {
		this.priceNoTax = priceNoTax;
	}

	public BigDecimal getContractTax() {
		return contractTax;
	}

	public void setContractTax(BigDecimal contractTax) {
		this.contractTax = contractTax;
	}

	public BigDecimal getContractAmtNoTax() {
		return contractAmtNoTax;
	}

	public void setContractAmtNoTax(BigDecimal contractAmtNoTax) {
		this.contractAmtNoTax = contractAmtNoTax;
	}

	public BigDecimal getContractAmtTax() {
		return contractAmtTax;
	}

	public void setContractAmtTax(BigDecimal contractAmtTax) {
		this.contractAmtTax = contractAmtTax;
	}

	public BigDecimal getChangeAmtNoTax() {
		return changeAmtNoTax;
	}

	public void setChangeAmtNoTax(BigDecimal changeAmtNoTax) {
		this.changeAmtNoTax = changeAmtNoTax;
	}

	public BigDecimal getChangeAmtTax() {
		return changeAmtTax;
	}

	public void setChangeAmtTax(BigDecimal changeAmtTax) {
		this.changeAmtTax = changeAmtTax;
	}

	public BigDecimal getThisTotalAmtNoTax() {
		return thisTotalAmtNoTax;
	}

	public void setThisTotalAmtNoTax(BigDecimal thisTotalAmtNoTax) {
		this.thisTotalAmtNoTax = thisTotalAmtNoTax;
	}

	public BigDecimal getThisTotalAmtTax() {
		return thisTotalAmtTax;
	}

	public void setThisTotalAmtTax(BigDecimal thisTotalAmtTax) {
		this.thisTotalAmtTax = thisTotalAmtTax;
	}

	public BigDecimal getUpQty() {
		return upQty;
	}

	public void setUpQty(BigDecimal upQty) {
		this.upQty = upQty;
	}

	public BigDecimal getUpTotalAmt() {
		return upTotalAmt;
	}

	public void setUpTotalAmt(BigDecimal upTotalAmt) {
		this.upTotalAmt = upTotalAmt;
	}

	public BigDecimal getUpTotalAmtNoTax() {
		return upTotalAmtNoTax;
	}

	public void setUpTotalAmtNoTax(BigDecimal upTotalAmtNoTax) {
		this.upTotalAmtNoTax = upTotalAmtNoTax;
	}

	public BigDecimal getUpTotalAmtTax() {
		return upTotalAmtTax;
	}

	public void setUpTotalAmtTax(BigDecimal upTotalAmtTax) {
		this.upTotalAmtTax = upTotalAmtTax;
	}

	public BigDecimal getAllTotalAmtNoTax() {
		return allTotalAmtNoTax;
	}

	public void setAllTotalAmtNoTax(BigDecimal allTotalAmtNoTax) {
		this.allTotalAmtNoTax = allTotalAmtNoTax;
	}

	public BigDecimal getAllTotalAmtTax() {
		return allTotalAmtTax;
	}

	public void setAllTotalAmtTax(BigDecimal allTotalAmtTax) {
		this.allTotalAmtTax = allTotalAmtTax;
	}

	public String getIsDeduct() {
		return isDeduct == null ? "" : isDeduct.trim();
	}

	public void setIsDeduct(String isDeduct) {
		this.isDeduct = isDeduct == null ? null : isDeduct.trim();
	}

	public BigDecimal getThisAmtSubTotal() {
		return thisAmtSubTotal;
	}

	public void setThisAmtSubTotal(BigDecimal thisAmtSubTotal) {
		this.thisAmtSubTotal = thisAmtSubTotal;
	}

	public BigDecimal getUpAmtSubTotal() {
		return upAmtSubTotal;
	}

	public void setUpAmtSubTotal(BigDecimal upAmtSubTotal) {
		this.upAmtSubTotal = upAmtSubTotal;
	}

	public BigDecimal getAllAmtSubTotal() {
		return allAmtSubTotal;
	}

	public void setAllAmtSubTotal(BigDecimal allAmtSubTotal) {
		this.allAmtSubTotal = allAmtSubTotal;
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
