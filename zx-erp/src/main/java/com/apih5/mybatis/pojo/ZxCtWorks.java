package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;

public class ZxCtWorks extends BasePojo {
	// 主键ID
	private String id;

	// 父节点ID
	private String parentID;

	// 树节点编号
	private String treeNode;

	// 项目机构ID
	private String orgID;

	// 清单书ID
	private String workBookID;

	// 清单类型
	private String workType;

	// 清单编号
	private String workNo;

	// 清单名称
	private String workName;

	// 计量单位
	private String unit;

	// 合同单价
	private BigDecimal contractPrice;

	// 合同数量
	private BigDecimal contractQty;

	// 合同金额
	private BigDecimal contractAmt;

	// 变更后数量
	private BigDecimal quantity;

	// 变更后单价
	private BigDecimal price;

	// 是否禁用
	private int deleted = 0;

	// 是否叶子节点
	private int isLeaf = 0;

	// 现有状态
	private int exsitStatus = 0;

	// 可分配的
	private int isAssignable = 0;

	// 修改状态
	private String updateFlag;

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

	// 最后修改时间
	private Date editTime;

	// 核定数量
	private BigDecimal checkQty;

	// 预计变更后数量
	private BigDecimal expectChangeQty;

	// 预计变更后单价
	private BigDecimal expectChangePrice;

	// 用于判断是否要重算预算单价
	private String isReCountAmt;

	// qty
	private BigDecimal qty;

	// 是否局下达，用于标准清单
	private String isGroup;

	// 不含税合同单价
	private BigDecimal contractPriceNoTax;

	// 不含税单价
	private BigDecimal priceNoTax;

	// 不含税合同金额
	private BigDecimal contractAmtNoTax;

	// 变更后不含税金额
	private BigDecimal amtNoTax;

	// 税率
	private String taxRate;

	// 变更记录
	private List<ZxCtWorkAlterSingle> changeRecord;

	// 计量表主键ID
	private String balID;

	// 本期合同内计量数量
	private BigDecimal balQty;

	// 本期合同外计量数量
	private BigDecimal balAltQty;

	// 本期变更增补金额
	private BigDecimal changeAltAmt;

	// 子级列表
	private List<ZxCtWorks> children;

	// 计量清单主键ID
	private String balItemId;

	// 计量金额
	private BigDecimal balAmt;

	// 上期末计量数量
	private BigDecimal lastTotalQty;

	// 上期末计量金额
	private BigDecimal lastTotalAmt;

	// 变更后金额
	private BigDecimal changeAmt;

	// 核查（定）金额
	private BigDecimal checkAmt;

	// 本期末计量数量
	private BigDecimal thisTotalQty;

	// 本期末计量金额
	private BigDecimal thisTotalAmt;

	// 挂接数量
	private BigDecimal linkQuantity;

	// 期次
	private String period;

	// 自定义查询语句
	private String sql;

	// workID
	private String workID;

	// RemainQty
	private BigDecimal remainQty;

	// TotalQty
	private BigDecimal totalQty;

	// TotalAmt
	private BigDecimal totalAmt;

	// 附件
	private List<ZxErpFile> attachment;

	// 清单导入时清单类型
	private String inputWorkType;

	// 覆盖类型
	private String delType;

	// 编号长度
	private int workNoLength = 0;

	// 备注
	private String remarks;

	// 新增
	private String addFlag; // 1：新增  2：删除

	// 排序
	private int sort = 0;

	//数据库清单编号
	private String workNoJoin;

	//数据库清单名称
	private String workNameJoin;

	//清单idjoin
	private String workIDJoin;

	//voie
	private String voidJoin;

	//编号
	private String resCodeJoin;

	//名称
	private String resNameJoin;

	//损耗系数join
	private String 	resGjLossCoefficientJoin;

	//折算系数join
	private String resGjConCoefficientJoin;

	//单价join
	private String priceJoin;

	public String getVoidJoin() {
		return voidJoin;
	}

	public void setVoidJoin(String voidJoin) {
		this.voidJoin = voidJoin;
	}

	public String getResCodeJoin() {
		return resCodeJoin;
	}

	public void setResCodeJoin(String resCodeJoin) {
		this.resCodeJoin = resCodeJoin;
	}

	public String getResNameJoin() {
		return resNameJoin;
	}

	public void setResNameJoin(String resNameJoin) {
		this.resNameJoin = resNameJoin;
	}

	public String getResGjLossCoefficientJoin() {
		return resGjLossCoefficientJoin;
	}

	public void setResGjLossCoefficientJoin(String resGjLossCoefficientJoin) {
		this.resGjLossCoefficientJoin = resGjLossCoefficientJoin;
	}

	public String getResGjConCoefficientJoin() {
		return resGjConCoefficientJoin;
	}

	public void setResGjConCoefficientJoin(String resGjConCoefficientJoin) {
		this.resGjConCoefficientJoin = resGjConCoefficientJoin;
	}

	public String getPriceJoin() {
		return priceJoin;
	}

	public void setPriceJoin(String priceJoin) {
		this.priceJoin = priceJoin;
	}

	public String getWorkIDJoin() {
		return workIDJoin;
	}

	public void setWorkIDJoin(String workIDJoin) {
		this.workIDJoin = workIDJoin;
	}

	public String getWorkNoJoin() {
		return workNoJoin;
	}

	public void setWorkNoJoin(String workNoJoin) {
		this.workNoJoin = workNoJoin;
	}

	public String getWorkNameJoin() {
		return workNameJoin;
	}

	public void setWorkNameJoin(String workNameJoin) {
		this.workNameJoin = workNameJoin;
	}

	public String getId() {
		return id == null ? "" : id.trim();
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
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

	public String getOrgID() {
		return orgID == null ? "" : orgID.trim();
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID == null ? null : orgID.trim();
	}

	public String getWorkBookID() {
		return workBookID == null ? "" : workBookID.trim();
	}

	public void setWorkBookID(String workBookID) {
		this.workBookID = workBookID == null ? null : workBookID.trim();
	}

	public String getWorkType() {
		return workType == null ? "" : workType.trim();
	}

	public void setWorkType(String workType) {
		this.workType = workType == null ? null : workType.trim();
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

	public String getUnit() {
		return unit == null ? "" : unit.trim();
	}

	public void setUnit(String unit) {
		this.unit = unit == null ? null : unit.trim();
	}

	public BigDecimal getContractPrice() {
		return contractPrice;
	}

	public void setContractPrice(BigDecimal contractPrice) {
		this.contractPrice = contractPrice;
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

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public int getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(int isLeaf) {
		this.isLeaf = isLeaf;
	}

	public int getExsitStatus() {
		return exsitStatus;
	}

	public void setExsitStatus(int exsitStatus) {
		this.exsitStatus = exsitStatus;
	}

	public int getIsAssignable() {
		return isAssignable;
	}

	public void setIsAssignable(int isAssignable) {
		this.isAssignable = isAssignable;
	}

	public String getUpdateFlag() {
		return updateFlag == null ? "" : updateFlag.trim();
	}

	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag == null ? null : updateFlag.trim();
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

	public BigDecimal getCheckQty() {
		return checkQty;
	}

	public void setCheckQty(BigDecimal checkQty) {
		this.checkQty = checkQty;
	}

	public BigDecimal getExpectChangeQty() {
		return expectChangeQty;
	}

	public void setExpectChangeQty(BigDecimal expectChangeQty) {
		this.expectChangeQty = expectChangeQty;
	}

	public BigDecimal getExpectChangePrice() {
		return expectChangePrice;
	}

	public void setExpectChangePrice(BigDecimal expectChangePrice) {
		this.expectChangePrice = expectChangePrice;
	}

	public String getIsReCountAmt() {
		return isReCountAmt == null ? "" : isReCountAmt.trim();
	}

	public void setIsReCountAmt(String isReCountAmt) {
		this.isReCountAmt = isReCountAmt == null ? null : isReCountAmt.trim();
	}

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public String getIsGroup() {
		return isGroup == null ? "" : isGroup.trim();
	}

	public void setIsGroup(String isGroup) {
		this.isGroup = isGroup == null ? null : isGroup.trim();
	}

	public BigDecimal getContractPriceNoTax() {
		return contractPriceNoTax;
	}

	public void setContractPriceNoTax(BigDecimal contractPriceNoTax) {
		this.contractPriceNoTax = contractPriceNoTax;
	}

	public BigDecimal getPriceNoTax() {
		return priceNoTax;
	}

	public void setPriceNoTax(BigDecimal priceNoTax) {
		this.priceNoTax = priceNoTax;
	}

	public BigDecimal getContractAmtNoTax() {
		return contractAmtNoTax;
	}

	public void setContractAmtNoTax(BigDecimal contractAmtNoTax) {
		this.contractAmtNoTax = contractAmtNoTax;
	}

	public BigDecimal getAmtNoTax() {
		return amtNoTax;
	}

	public void setAmtNoTax(BigDecimal amtNoTax) {
		this.amtNoTax = amtNoTax;
	}

	public String getTaxRate() {
		return taxRate == null ? "" : taxRate.trim();
	}

	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate == null ? null : taxRate.trim();
	}

	public List<ZxCtWorkAlterSingle> getChangeRecord() {
		return changeRecord == null ? Lists.newArrayList() : changeRecord;
	}

	public void setChangeRecord(List<ZxCtWorkAlterSingle> changeRecord) {
		this.changeRecord = changeRecord == null ? null : changeRecord;
	}

	public String getBalID() {
		return balID == null ? "" : balID.trim();
	}

	public void setBalID(String balID) {
		this.balID = balID == null ? null : balID.trim();
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

	public BigDecimal getChangeAltAmt() {
		return changeAltAmt;
	}

	public void setChangeAltAmt(BigDecimal changeAltAmt) {
		this.changeAltAmt = changeAltAmt;
	}

	public List<ZxCtWorks> getChildren() {
		return children == null ? Lists.newArrayList() : children;
	}

	public void setChildren(List<ZxCtWorks> children) {
		this.children = children == null ? null : children;
	}

	public String getBalItemId() {
		return balItemId == null ? "" : balItemId.trim();
	}

	public void setBalItemId(String balItemId) {
		this.balItemId = balItemId == null ? null : balItemId.trim();
	}

	public BigDecimal getBalAmt() {
		return balAmt;
	}

	public void setBalAmt(BigDecimal balAmt) {
		this.balAmt = balAmt;
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

	public BigDecimal getChangeAmt() {
		return changeAmt;
	}

	public void setChangeAmt(BigDecimal changeAmt) {
		this.changeAmt = changeAmt;
	}

	public BigDecimal getCheckAmt() {
		return checkAmt;
	}

	public void setCheckAmt(BigDecimal checkAmt) {
		this.checkAmt = checkAmt;
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

	public BigDecimal getLinkQuantity() {
		return linkQuantity;
	}

	public void setLinkQuantity(BigDecimal linkQuantity) {
		this.linkQuantity = linkQuantity;
	}

	public String getPeriod() {
		return period == null ? "" : period.trim();
	}

	public void setPeriod(String period) {
		this.period = period == null ? null : period.trim();
	}

	public String getSql() {
		return sql == null ? "" : sql.trim();
	}

	public void setSql(String sql) {
		this.sql = sql == null ? null : sql.trim();
	}

	public String getWorkID() {
		return workID == null ? "" : workID.trim();
	}

	public void setWorkID(String workID) {
		this.workID = workID == null ? null : workID.trim();
	}

	public BigDecimal getRemainQty() {
		return remainQty;
	}

	public void setRemainQty(BigDecimal remainQty) {
		this.remainQty = remainQty;
	}

	public BigDecimal getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(BigDecimal totalQty) {
		this.totalQty = totalQty;
	}

	public BigDecimal getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

	public List<ZxErpFile> getAttachment() {
		return attachment == null ? Lists.newArrayList() : attachment;
	}

	public void setAttachment(List<ZxErpFile> attachment) {
		this.attachment = attachment == null ? null : attachment;
	}

	public String getInputWorkType() {
		return inputWorkType == null ? "" : inputWorkType.trim();
	}

	public void setInputWorkType(String inputWorkType) {
		this.inputWorkType = inputWorkType == null ? null : inputWorkType.trim();
	}

	public String getDelType() {
		return delType == null ? "" : delType.trim();
	}

	public void setDelType(String delType) {
		this.delType = delType == null ? null : delType.trim();
	}

	public int getWorkNoLength() {
		return workNoLength;
	}

	public void setWorkNoLength(int workNoLength) {
		this.workNoLength = workNoLength;
	}

	public String getRemarks() {
		return remarks == null ? "" : remarks.trim();
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks == null ? null : remarks.trim();
	}

	public String getAddFlag() {
		return addFlag == null ? "" : addFlag.trim();
	}

	public void setAddFlag(String addFlag) {
		this.addFlag = addFlag == null ? null : addFlag.trim();
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
