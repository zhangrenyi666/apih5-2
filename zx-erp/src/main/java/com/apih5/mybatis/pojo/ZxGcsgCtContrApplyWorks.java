package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxGcsgCtContrApplyWorks extends BasePojo {
	// 主键
	private String ctContrApplyWorksId;

	// 父节点ID
	private String parentID;

	// 树节点编号
	private String treeNode;

	// 责任中心
	private String orgID;

	// 合同评审ID(contrApplyID)
	private String ctContrApplyId;

	// 清单类型
	private int workType = 0;

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

	// 工程量
	private BigDecimal quantity;

	// 计量单价(含税合同单价)
	private BigDecimal price;

	// 是否删除
	private int deleted = 0;

	// 是否叶子节点
	private Integer isLeaf;

	// 变更状态
	private int exsitStatus = 0;

	// 是否可分配计量
	private int isAssignable = 0;

	// updateFlag
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

	// 最后编辑时间
	private Date editTime;

	// 核定数量
	private BigDecimal checkQty;

	// 预计变更后数量
	private BigDecimal expectChangeQty;

	// 预计变更后单价
	private BigDecimal expectChangePrice;

	// 清单类型
	private String inputWorkType;

	// 用于判断是否要重算预算单价
	private String isReCountAmt;

	// old contract qty
	private BigDecimal qty;

	// 是否局下达
	private String isGroup;

	// 要求修改
	private String requestEdit;

	// 修改人
	private String editUserID;

	// 修改人
	private String editUserName;

	// 修改日期
	private String editDate;

	// 原合同不含税合同单价
	private BigDecimal contractPriceNoTax;

	// 变更后不含税合同单价
	private BigDecimal priceNoTax;

	// 税率
	private String taxRate;

	// 不含税合同金额
	private BigDecimal amtNoTax;

	// 计价规则ID
	private String ruleID;

	// 计价规则名称
	private String ruleName;

	// 备注
	private String opinionField1;

	// 备注
	private String opinionField2;

	// 备注
	private String opinionField3;

	// 备注
	private String opinionField4;

	// 备注
	private String opinionField5;

	// 备注
	private String opinionField6;

	// 备注
	private String opinionField7;

	// 备注
	private String opinionField8;

	// 备注
	private String opinionField9;

	// 备注
	private String opinionField10;

	// 流程id
	private String apih5FlowId;

	// work_id
	private String workId;

	// 工序审核状态
	private String apih5FlowStatus;

	// 流程状态
	private String apih5FlowNodeStatus;

	// 附件集合
	private List<ZxGcsgCommonAttachment> attachmentList;

	// 覆盖类型
	private String delType;

	// 编号长度
	private int workNoLength = 0;

	// 评审清单集合
	private List<ZxGcsgCtContrApplyWorks> ctContrApplyWorksList;

	// 标准工序数组
	private JSONArray processArray;

	// 是否抵扣
	private String isDeduct;

	// 工序挂接数量
	private Integer processNum;

	// 备注
	private String remarks;

	// 排序
	private int sort = 0;

	public String getCtContrApplyWorksId() {
		return ctContrApplyWorksId == null ? "" : ctContrApplyWorksId.trim();
	}

	public void setCtContrApplyWorksId(String ctContrApplyWorksId) {
		this.ctContrApplyWorksId = ctContrApplyWorksId == null ? null : ctContrApplyWorksId.trim();
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

	public String getCtContrApplyId() {
		return ctContrApplyId == null ? "" : ctContrApplyId.trim();
	}

	public void setCtContrApplyId(String ctContrApplyId) {
		this.ctContrApplyId = ctContrApplyId == null ? null : ctContrApplyId.trim();
	}

	public int getWorkType() {
		return workType;
	}

	public void setWorkType(int workType) {
		this.workType = workType;
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

	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
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

	public String getInputWorkType() {
		return inputWorkType == null ? "" : inputWorkType.trim();
	}

	public void setInputWorkType(String inputWorkType) {
		this.inputWorkType = inputWorkType == null ? null : inputWorkType.trim();
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

	public String getRequestEdit() {
		return requestEdit == null ? "" : requestEdit.trim();
	}

	public void setRequestEdit(String requestEdit) {
		this.requestEdit = requestEdit == null ? null : requestEdit.trim();
	}

	public String getEditUserID() {
		return editUserID == null ? "" : editUserID.trim();
	}

	public void setEditUserID(String editUserID) {
		this.editUserID = editUserID == null ? null : editUserID.trim();
	}

	public String getEditUserName() {
		return editUserName == null ? "" : editUserName.trim();
	}

	public void setEditUserName(String editUserName) {
		this.editUserName = editUserName == null ? null : editUserName.trim();
	}

	public String getEditDate() {
		return editDate == null ? "" : editDate.trim();
	}

	public void setEditDate(String editDate) {
		this.editDate = editDate == null ? null : editDate.trim();
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

	public String getTaxRate() {
		return taxRate == null ? "" : taxRate.trim();
	}

	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate == null ? null : taxRate.trim();
	}

	public BigDecimal getAmtNoTax() {
		return amtNoTax;
	}

	public void setAmtNoTax(BigDecimal amtNoTax) {
		this.amtNoTax = amtNoTax;
	}

	public String getRuleID() {
		return ruleID == null ? "" : ruleID.trim();
	}

	public void setRuleID(String ruleID) {
		this.ruleID = ruleID == null ? null : ruleID.trim();
	}

	public String getRuleName() {
		return ruleName == null ? "" : ruleName.trim();
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName == null ? null : ruleName.trim();
	}

	public String getOpinionField1() {
		return opinionField1 == null ? "" : opinionField1.trim();
	}

	public void setOpinionField1(String opinionField1) {
		this.opinionField1 = opinionField1 == null ? null : opinionField1.trim();
	}

	public String getOpinionField2() {
		return opinionField2 == null ? "" : opinionField2.trim();
	}

	public void setOpinionField2(String opinionField2) {
		this.opinionField2 = opinionField2 == null ? null : opinionField2.trim();
	}

	public String getOpinionField3() {
		return opinionField3 == null ? "" : opinionField3.trim();
	}

	public void setOpinionField3(String opinionField3) {
		this.opinionField3 = opinionField3 == null ? null : opinionField3.trim();
	}

	public String getOpinionField4() {
		return opinionField4 == null ? "" : opinionField4.trim();
	}

	public void setOpinionField4(String opinionField4) {
		this.opinionField4 = opinionField4 == null ? null : opinionField4.trim();
	}

	public String getOpinionField5() {
		return opinionField5 == null ? "" : opinionField5.trim();
	}

	public void setOpinionField5(String opinionField5) {
		this.opinionField5 = opinionField5 == null ? null : opinionField5.trim();
	}

	public String getOpinionField6() {
		return opinionField6 == null ? "" : opinionField6.trim();
	}

	public void setOpinionField6(String opinionField6) {
		this.opinionField6 = opinionField6 == null ? null : opinionField6.trim();
	}

	public String getOpinionField7() {
		return opinionField7 == null ? "" : opinionField7.trim();
	}

	public void setOpinionField7(String opinionField7) {
		this.opinionField7 = opinionField7 == null ? null : opinionField7.trim();
	}

	public String getOpinionField8() {
		return opinionField8 == null ? "" : opinionField8.trim();
	}

	public void setOpinionField8(String opinionField8) {
		this.opinionField8 = opinionField8 == null ? null : opinionField8.trim();
	}

	public String getOpinionField9() {
		return opinionField9 == null ? "" : opinionField9.trim();
	}

	public void setOpinionField9(String opinionField9) {
		this.opinionField9 = opinionField9 == null ? null : opinionField9.trim();
	}

	public String getOpinionField10() {
		return opinionField10 == null ? "" : opinionField10.trim();
	}

	public void setOpinionField10(String opinionField10) {
		this.opinionField10 = opinionField10 == null ? null : opinionField10.trim();
	}

	public String getApih5FlowId() {
		return apih5FlowId == null ? "" : apih5FlowId.trim();
	}

	public void setApih5FlowId(String apih5FlowId) {
		this.apih5FlowId = apih5FlowId == null ? null : apih5FlowId.trim();
	}

	public String getWorkId() {
		return workId == null ? "" : workId.trim();
	}

	public void setWorkId(String workId) {
		this.workId = workId == null ? null : workId.trim();
	}

	public String getApih5FlowStatus() {
		return apih5FlowStatus == null ? "" : apih5FlowStatus.trim();
	}

	public void setApih5FlowStatus(String apih5FlowStatus) {
		this.apih5FlowStatus = apih5FlowStatus == null ? null : apih5FlowStatus.trim();
	}

	public String getApih5FlowNodeStatus() {
		return apih5FlowNodeStatus == null ? "" : apih5FlowNodeStatus.trim();
	}

	public void setApih5FlowNodeStatus(String apih5FlowNodeStatus) {
		this.apih5FlowNodeStatus = apih5FlowNodeStatus == null ? null : apih5FlowNodeStatus.trim();
	}

	public List<ZxGcsgCommonAttachment> getAttachmentList() {
		return attachmentList == null ? Lists.newArrayList() : attachmentList;
	}

	public void setAttachmentList(List<ZxGcsgCommonAttachment> attachmentList) {
		this.attachmentList = attachmentList == null ? null : attachmentList;
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

	public List<ZxGcsgCtContrApplyWorks> getCtContrApplyWorksList() {
		return ctContrApplyWorksList == null ? Lists.newArrayList() : ctContrApplyWorksList;
	}

	public void setCtContrApplyWorksList(List<ZxGcsgCtContrApplyWorks> ctContrApplyWorksList) {
		this.ctContrApplyWorksList = ctContrApplyWorksList == null ? null : ctContrApplyWorksList;
	}

	public JSONArray getProcessArray() {
		return processArray == null ? new JSONArray() : processArray;
	}

	public void setProcessArray(JSONArray processArray) {
		this.processArray = processArray == null ? null : processArray;
	}

	public String getIsDeduct() {
		return isDeduct == null ? "" : isDeduct.trim();
	}

	public void setIsDeduct(String isDeduct) {
		this.isDeduct = isDeduct == null ? null : isDeduct.trim();
	}

	public Integer getProcessNum() {
		return processNum;
	}

	public void setProcessNum(Integer processNum) {
		this.processNum = processNum;
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
