package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.List;

import com.apih5.framework.entity.BasePojo;

public class ZxCtContrApplyWorks extends BasePojo {
    // 主键
    private String id;

    // parentId
    private String parentId;

    // 层次
    private String treeNode;

    // orgId
    private String orgId;

    // contrApplyId
    private String contrApplyId;

    // 设备ID(暂用workType)
    private int workType=0;

    // 清单编号
    private String workNo;

    // 清单名称
    private String workName;

    // 计量单位
    private String unit;

    // contractPrice
    private BigDecimal contractPrice;

    // contractQty
    private BigDecimal contractQty;

    // contractAmt
    private BigDecimal contractAmt;

    // quantity
    private BigDecimal quantity;

    // 含税单价
    private BigDecimal price;

    // deleted
    private int deleted=0;

    // isLeaf
    private int isLeaf=0;

    // exsitStatus
    private int exsitStatus=0;

    // isAssignable
    private int isAssignable=0;

    // updateFlag
    private String updateFlag;

    // updateFlag
    private String combProp;

    // pp1
    private String pp1;

    // pp2
    private String pp2;

    // pp3
    private String pp3;

    // pp4
    private String pp4;

    // 合同评审ID
    private String contractReview;

    // pp6
    private String pp6;

    // pp7
    private String pp7;

    // pp8
    private String pp8;

    // pp9
    private String pp9;

    // 租期(台班)
    private String leaseTerm;

    // checkQty
    private BigDecimal checkQty;

    // expectChangeQty
    private BigDecimal expectChangeQty;

    // expectChangePrice
    private BigDecimal expectChangePrice;

    // inputWorkType
    private String inputWorkType;

    // isReCountAmt
    private String isReCountAmt;

    // 合同数量
    private BigDecimal qty;

    // isGroup
    private String isGroup;

    // 要求修改
    private String requestEdit;

    // contractPriceNoTax
    private BigDecimal contractPriceNoTax;

    // 不含税单价
    private BigDecimal priceNoTax;

    // 税率(%)
    private String taxRate;

    // amtNoTax
    private BigDecimal amtNoTax;

    // ruleId
    private String ruleId;

    // ruleName
    private String ruleName;

    private List<ZxErpFile> importFileList;

    // 排序
    private int sort=0;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getParentId() {
        return parentId == null ? "" : parentId.trim();
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getTreeNode() {
        return treeNode == null ? "" : treeNode.trim();
    }

    public void setTreeNode(String treeNode) {
        this.treeNode = treeNode == null ? null : treeNode.trim();
    }

    public String getOrgId() {
        return orgId == null ? "" : orgId.trim();
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getContrApplyId() {
        return contrApplyId == null ? "" : contrApplyId.trim();
    }

    public void setContrApplyId(String contrApplyId) {
        this.contrApplyId = contrApplyId == null ? null : contrApplyId.trim();
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

    public String getContractReview() {
        return contractReview == null ? "" : contractReview.trim();
    }

    public void setContractReview(String contractReview) {
        this.contractReview = contractReview == null ? null : contractReview.trim();
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

    public String getLeaseTerm() {
        return leaseTerm == null ? "" : leaseTerm.trim();
    }

    public void setLeaseTerm(String leaseTerm) {
        this.leaseTerm = leaseTerm == null ? null : leaseTerm.trim();
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

    public List<ZxErpFile> getImportFileList() {
        return importFileList;
    }

    public void setImportFileList(List<ZxErpFile> importFileList) {
        this.importFileList = importFileList;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "ZxCtContrApplyWorks [id=" + id + ", workNo=" + workNo + ", parentId=" + parentId + "]";
    }

}
