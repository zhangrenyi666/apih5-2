package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCcConstCoAlterWork extends BasePojo {
    // 主键
    private String id;

    // 变更ID
    private String alterId;

    // 变更类型
    private String alterType;

    // 管理单元ID
    private String muId;

    // 清单ID
    private String workId;

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

    // combProp
    private String combProp;

    // 归属主合同清单编号
    private String mainContractListNo;

    // 清单名称
    private String listName;

    // 单位
    private String unit;

    // 清单编号
    private String listNo;

    // pp5
    private String pp5;

    // 归属主合同清单ID
    private String mainContractListId;

    // pp7
    private String pp7;

    // pp8
    private String pp8;

    // pp9
    private String pp9;

    // 挂接
    private String hook;

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
    private int isLeaf=0;

    // 要求修改
    private String requestEdit;

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

    // ruleId
    private String ruleId;

    // ruleName
    private String ruleName;

    // 已挂接工序数
    private int gjNum=0;

    // 排序
    private int sort=0;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAlterId() {
        return alterId == null ? "" : alterId.trim();
    }

    public void setAlterId(String alterId) {
        this.alterId = alterId == null ? null : alterId.trim();
    }

    public String getAlterType() {
        return alterType == null ? "" : alterType.trim();
    }

    public void setAlterType(String alterType) {
        this.alterType = alterType == null ? null : alterType.trim();
    }

    public String getMuId() {
        return muId == null ? "" : muId.trim();
    }

    public void setMuId(String muId) {
        this.muId = muId == null ? null : muId.trim();
    }

    public String getWorkId() {
        return workId == null ? "" : workId.trim();
    }

    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
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

    public String getMainContractListNo() {
        return mainContractListNo == null ? "" : mainContractListNo.trim();
    }

    public void setMainContractListNo(String mainContractListNo) {
        this.mainContractListNo = mainContractListNo == null ? null : mainContractListNo.trim();
    }

    public String getListName() {
        return listName == null ? "" : listName.trim();
    }

    public void setListName(String listName) {
        this.listName = listName == null ? null : listName.trim();
    }

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getListNo() {
        return listNo == null ? "" : listNo.trim();
    }

    public void setListNo(String listNo) {
        this.listNo = listNo == null ? null : listNo.trim();
    }

    public String getPp5() {
        return pp5 == null ? "" : pp5.trim();
    }

    public void setPp5(String pp5) {
        this.pp5 = pp5 == null ? null : pp5.trim();
    }

    public String getMainContractListId() {
        return mainContractListId == null ? "" : mainContractListId.trim();
    }

    public void setMainContractListId(String mainContractListId) {
        this.mainContractListId = mainContractListId == null ? null : mainContractListId.trim();
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

    public String getHook() {
        return hook == null ? "" : hook.trim();
    }

    public void setHook(String hook) {
        this.hook = hook == null ? null : hook.trim();
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

    public int getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(int isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getRequestEdit() {
        return requestEdit == null ? "" : requestEdit.trim();
    }

    public void setRequestEdit(String requestEdit) {
        this.requestEdit = requestEdit == null ? null : requestEdit.trim();
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

    public String getRuleId() {
        return ruleId == null ? "" : ruleId.trim();
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId == null ? null : ruleId.trim();
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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

}
