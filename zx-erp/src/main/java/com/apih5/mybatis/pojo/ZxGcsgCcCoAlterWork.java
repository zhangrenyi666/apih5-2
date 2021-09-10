package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxGcsgCcCoAlterWork extends BasePojo {
	// 主键
	private String ccCoAlterWorkId;

	// 变更ID(alterID)
	private String ccCoAlterId;

	// 变更类型
	private String alterType;

	// 管理单元ID
	private String muID;

	// 清单ID(workID)
	private String ccWorksId;

	// 原合同数量
	private BigDecimal originQty;

	// 原含税合同单价
	private BigDecimal originPrice;

	// 申报数量
	private BigDecimal applyQty;

	// 申报单价
	private BigDecimal applyPrice;

	// 申报新增数量
	private BigDecimal applyAddQty;

	// 批复数量
	private BigDecimal replyQty;

	// 批复单价
	private BigDecimal replyPrice;

	// 批复新增数量
	private BigDecimal replyAddQty;

	// 废弃字段
	private String combProp;

	// 归属主合同清单编号
	private String pp1;

	// 清单名称
	private String pp2;

	// 单位
	private String pp3;

	// 清单编号
	private String pp4;

	// pp5
	private String pp5;

	// 清单父节点ID
	private String pp6;

	// pp7
	private String pp7;

	// pp8
	private String pp8;

	// pp9
	private String pp9;

	// 挂接
	private String pp10;

	// 归属主合同清单编号(pp1)
	private String ccWorksParentNo;

	// 清单名称(pp2)
	private String ccWorksName;

	// 单位(pp3)
	private String ccWorksUnit;

	// 清单编号(pp4)
	private String ccWorksNo;

	// 归属主合同清单ID(pp6)
	private String ccWorksParentId;

	// 挂接(pp10)
	private String mount;

	// 最后编辑时间
	private Date editTime;

	// 变更增减数量
	private BigDecimal changeQty;

	// 增减单价
	private BigDecimal changePrice;

	// 原含税合同金额
	private BigDecimal contractPrice;

	// 变更后数量
	private BigDecimal afterChangeQty;

	// 变更后单价
	private BigDecimal afterChangePrice;

	// 是否叶子节点
	private Integer isLeaf;

	// 要求修改
	private String requestEdit;

	// 修改人
	private String editUserID;

	// 修改人
	private String editUserName;

	// 修改日期
	private String editDate;

	// 原不含税合同单价
	private BigDecimal originPriceNoTax;

	// 原不含税合同金额
	private BigDecimal contractCostNoTax;

	// 税率(%)
	private String taxRate;

	// 变更后含税金额
	private BigDecimal afterAmt;

	// 变更后不含税金额
	private BigDecimal afterAmtNoTax;

	// 变更后税额
	private BigDecimal afterAmtTax;

	// 计价规则ID
	private String ruleID;

	// 计价规则名称
	private String ruleName;

	// 已挂接工序数
	private int gjNum = 0;

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

	// 工序审核状态
	private String apih5FlowStatus;

	// 流程状态
	private String apih5FlowNodeStatus;

	// work_id
	private String workId;

	// 补充协议ID
	private String ctContrApplyId;

	// 标准工序数组
	private JSONArray processArray;

	// 工序库类型
	private String inputWorkType;

	// 树节点编号
	private String treeNode;

	// 原合同变更后合同数量
	private BigDecimal quantity;

	// 备注
	private String remarks;

	// 排序
	private int sort = 0;

	public String getCcCoAlterWorkId() {
		return ccCoAlterWorkId == null ? "" : ccCoAlterWorkId.trim();
	}

	public void setCcCoAlterWorkId(String ccCoAlterWorkId) {
		this.ccCoAlterWorkId = ccCoAlterWorkId == null ? null : ccCoAlterWorkId.trim();
	}

	public String getCcCoAlterId() {
		return ccCoAlterId == null ? "" : ccCoAlterId.trim();
	}

	public void setCcCoAlterId(String ccCoAlterId) {
		this.ccCoAlterId = ccCoAlterId == null ? null : ccCoAlterId.trim();
	}

	public String getAlterType() {
		return alterType == null ? "" : alterType.trim();
	}

	public void setAlterType(String alterType) {
		this.alterType = alterType == null ? null : alterType.trim();
	}

	public String getMuID() {
		return muID == null ? "" : muID.trim();
	}

	public void setMuID(String muID) {
		this.muID = muID == null ? null : muID.trim();
	}

	public String getCcWorksId() {
		return ccWorksId == null ? "" : ccWorksId.trim();
	}

	public void setCcWorksId(String ccWorksId) {
		this.ccWorksId = ccWorksId == null ? null : ccWorksId.trim();
	}

	public BigDecimal getOriginQty() {
		return originQty;
	}

	public void setOriginQty(BigDecimal originQty) {
		this.originQty = originQty;
	}

	public BigDecimal getOriginPrice() {
		return originPrice;
	}

	public void setOriginPrice(BigDecimal originPrice) {
		this.originPrice = originPrice;
	}

	public BigDecimal getApplyQty() {
		return applyQty;
	}

	public void setApplyQty(BigDecimal applyQty) {
		this.applyQty = applyQty;
	}

	public BigDecimal getApplyPrice() {
		return applyPrice;
	}

	public void setApplyPrice(BigDecimal applyPrice) {
		this.applyPrice = applyPrice;
	}

	public BigDecimal getApplyAddQty() {
		return applyAddQty;
	}

	public void setApplyAddQty(BigDecimal applyAddQty) {
		this.applyAddQty = applyAddQty;
	}

	public BigDecimal getReplyQty() {
		return replyQty;
	}

	public void setReplyQty(BigDecimal replyQty) {
		this.replyQty = replyQty;
	}

	public BigDecimal getReplyPrice() {
		return replyPrice;
	}

	public void setReplyPrice(BigDecimal replyPrice) {
		this.replyPrice = replyPrice;
	}

	public BigDecimal getReplyAddQty() {
		return replyAddQty;
	}

	public void setReplyAddQty(BigDecimal replyAddQty) {
		this.replyAddQty = replyAddQty;
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

	public String getCcWorksParentNo() {
		return ccWorksParentNo == null ? "" : ccWorksParentNo.trim();
	}

	public void setCcWorksParentNo(String ccWorksParentNo) {
		this.ccWorksParentNo = ccWorksParentNo == null ? null : ccWorksParentNo.trim();
	}

	public String getCcWorksName() {
		return ccWorksName == null ? "" : ccWorksName.trim();
	}

	public void setCcWorksName(String ccWorksName) {
		this.ccWorksName = ccWorksName == null ? null : ccWorksName.trim();
	}

	public String getCcWorksUnit() {
		return ccWorksUnit == null ? "" : ccWorksUnit.trim();
	}

	public void setCcWorksUnit(String ccWorksUnit) {
		this.ccWorksUnit = ccWorksUnit == null ? null : ccWorksUnit.trim();
	}

	public String getCcWorksNo() {
		return ccWorksNo == null ? "" : ccWorksNo.trim();
	}

	public void setCcWorksNo(String ccWorksNo) {
		this.ccWorksNo = ccWorksNo == null ? null : ccWorksNo.trim();
	}

	public String getCcWorksParentId() {
		return ccWorksParentId == null ? "" : ccWorksParentId.trim();
	}

	public void setCcWorksParentId(String ccWorksParentId) {
		this.ccWorksParentId = ccWorksParentId == null ? null : ccWorksParentId.trim();
	}

	public String getMount() {
		return mount == null ? "" : mount.trim();
	}

	public void setMount(String mount) {
		this.mount = mount == null ? null : mount.trim();
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public BigDecimal getChangeQty() {
		return changeQty;
	}

	public void setChangeQty(BigDecimal changeQty) {
		this.changeQty = changeQty;
	}

	public BigDecimal getChangePrice() {
		return changePrice;
	}

	public void setChangePrice(BigDecimal changePrice) {
		this.changePrice = changePrice;
	}

	public BigDecimal getContractPrice() {
		return contractPrice;
	}

	public void setContractPrice(BigDecimal contractPrice) {
		this.contractPrice = contractPrice;
	}

	public BigDecimal getAfterChangeQty() {
		return afterChangeQty;
	}

	public void setAfterChangeQty(BigDecimal afterChangeQty) {
		this.afterChangeQty = afterChangeQty;
	}

	public BigDecimal getAfterChangePrice() {
		return afterChangePrice;
	}

	public void setAfterChangePrice(BigDecimal afterChangePrice) {
		this.afterChangePrice = afterChangePrice;
	}

	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
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

	public BigDecimal getOriginPriceNoTax() {
		return originPriceNoTax;
	}

	public void setOriginPriceNoTax(BigDecimal originPriceNoTax) {
		this.originPriceNoTax = originPriceNoTax;
	}

	public BigDecimal getContractCostNoTax() {
		return contractCostNoTax;
	}

	public void setContractCostNoTax(BigDecimal contractCostNoTax) {
		this.contractCostNoTax = contractCostNoTax;
	}

	public String getTaxRate() {
		return taxRate == null ? "" : taxRate.trim();
	}

	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate == null ? null : taxRate.trim();
	}

	public BigDecimal getAfterAmt() {
		return afterAmt;
	}

	public void setAfterAmt(BigDecimal afterAmt) {
		this.afterAmt = afterAmt;
	}

	public BigDecimal getAfterAmtNoTax() {
		return afterAmtNoTax;
	}

	public void setAfterAmtNoTax(BigDecimal afterAmtNoTax) {
		this.afterAmtNoTax = afterAmtNoTax;
	}

	public BigDecimal getAfterAmtTax() {
		return afterAmtTax;
	}

	public void setAfterAmtTax(BigDecimal afterAmtTax) {
		this.afterAmtTax = afterAmtTax;
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

	public int getGjNum() {
		return gjNum;
	}

	public void setGjNum(int gjNum) {
		this.gjNum = gjNum;
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

	public String getWorkId() {
		return workId == null ? "" : workId.trim();
	}

	public void setWorkId(String workId) {
		this.workId = workId == null ? null : workId.trim();
	}

	public String getCtContrApplyId() {
		return ctContrApplyId == null ? "" : ctContrApplyId.trim();
	}

	public void setCtContrApplyId(String ctContrApplyId) {
		this.ctContrApplyId = ctContrApplyId == null ? null : ctContrApplyId.trim();
	}

	public JSONArray getProcessArray() {
		return processArray == null ? new JSONArray() : processArray;
	}

	public void setProcessArray(JSONArray processArray) {
		this.processArray = processArray == null ? null : processArray;
	}

	public String getInputWorkType() {
		return inputWorkType == null ? "" : inputWorkType.trim();
	}

	public void setInputWorkType(String inputWorkType) {
		this.inputWorkType = inputWorkType == null ? null : inputWorkType.trim();
	}

	public String getTreeNode() {
		return treeNode == null ? "" : treeNode.trim();
	}

	public void setTreeNode(String treeNode) {
		this.treeNode = treeNode == null ? null : treeNode.trim();
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
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
