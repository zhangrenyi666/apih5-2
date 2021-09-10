package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkStockFeeItem extends BasePojo {
    // 主键
    @JsonProperty("conRevDetailId")
    private String zxSkStockFeeItemId;

    // 
    private String mainID;

    // 清单编号
    private String workNo;

    // 清单名称
    private String workName;

    // 型号
    private String spec;

    // 计量单位
    private String unit;

    // 
    private String workType;

    // 层次
    private String treenode;

    // 合同数量
    private BigDecimal qty;

    // 含税合同单价
    private BigDecimal price;

    // 含税合同金额
    private BigDecimal contractSum;

    // 
    private String systemType;

    // 界面展现类型
    private String viewType;

    // 计划开始时间
    private Date planStartDate;

    // 计划结束时间
    private Date planEndDate;

    // 租赁开始时间
    private Date actualStartDate;

    // 租赁结束时间
    private Date actualEndDate;

    // 
    private String combProp;

    // 
    private String pp1;

    // 
    private String pp2;

    // 
    private String pp3;

    // 
    private String pp4;

    // 评审ID
    private String pp5;

    // 
    private String pp6;

    // 
    private String pp7;

    // 
    private String pp8;

    // 
    private String pp9;

    // 租期(台班)
    private String pp10;

    // 
    private Date editTime;

    // 设备ID(暂用workType)
    private String workTypeID;

    // 
    private String workID;

    // 数量
    private BigDecimal changeQty;

    // 变更后含税单价
    private BigDecimal changePrice;

    // 金额
    private BigDecimal changeContractSum;

    // null
    private Date changeDate;

    // 租赁单位
    private String rentUnit;

    // 变更后租赁开始时间
    private Date alterRentStartDate;

    // 变更后租赁结束时间
    private Date alterRentEndDate;

    // 1
    private BigDecimal alterTrrm;

    // 1
    private BigDecimal alterPrice;

    // 1
    private BigDecimal alterAmt;

    // 1
    private BigDecimal contrTrrm;

    // null
    private String isNetPrice;

    // 要求修改
    private String requestEdit;

    // 修改人
    private String editUserID;

    // 修改人
    private String editUserName;

    // 修改日期
    private String editDate;

    // 不含税合同单价
    private BigDecimal priceNoTax;

    // 不含税合同金额
    private BigDecimal contractSumNoTax;

    // 变更后不含税单价
    private BigDecimal changePriceNoTax;

    // 入账金额
    private BigDecimal changeContractSumNoTax;

    // null
    private BigDecimal alterPriceNoPrice;

    // null
    private BigDecimal alterAmtNoTax;

    // null
    private BigDecimal alterAmtTax;

    // null
    private BigDecimal alterPriceNoTax;

    // 合同ID
    private String contractID;

    // 税率(%)
    private String taxRate;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getZxSkStockFeeItemId() {
        return zxSkStockFeeItemId == null ? "" : zxSkStockFeeItemId.trim();
    }

    public void setZxSkStockFeeItemId(String zxSkStockFeeItemId) {
        this.zxSkStockFeeItemId = zxSkStockFeeItemId == null ? null : zxSkStockFeeItemId.trim();
    }

    public String getMainID() {
        return mainID == null ? "" : mainID.trim();
    }

    public void setMainID(String mainID) {
        this.mainID = mainID == null ? null : mainID.trim();
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

    public String getSpec() {
        return spec == null ? "" : spec.trim();
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getWorkType() {
        return workType == null ? "" : workType.trim();
    }

    public void setWorkType(String workType) {
        this.workType = workType == null ? null : workType.trim();
    }

    public String getTreenode() {
        return treenode == null ? "" : treenode.trim();
    }

    public void setTreenode(String treenode) {
        this.treenode = treenode == null ? null : treenode.trim();
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getContractSum() {
        return contractSum;
    }

    public void setContractSum(BigDecimal contractSum) {
        this.contractSum = contractSum;
    }

    public String getSystemType() {
        return systemType == null ? "" : systemType.trim();
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType == null ? null : systemType.trim();
    }

    public String getViewType() {
        return viewType == null ? "" : viewType.trim();
    }

    public void setViewType(String viewType) {
        this.viewType = viewType == null ? null : viewType.trim();
    }

    public Date getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(Date planStartDate) {
        this.planStartDate = planStartDate;
    }

    public Date getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(Date planEndDate) {
        this.planEndDate = planEndDate;
    }

    public Date getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(Date actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public Date getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(Date actualEndDate) {
        this.actualEndDate = actualEndDate;
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

    public String getWorkTypeID() {
        return workTypeID == null ? "" : workTypeID.trim();
    }

    public void setWorkTypeID(String workTypeID) {
        this.workTypeID = workTypeID == null ? null : workTypeID.trim();
    }

    public String getWorkID() {
        return workID == null ? "" : workID.trim();
    }

    public void setWorkID(String workID) {
        this.workID = workID == null ? null : workID.trim();
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

    public BigDecimal getChangeContractSum() {
        return changeContractSum;
    }

    public void setChangeContractSum(BigDecimal changeContractSum) {
        this.changeContractSum = changeContractSum;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public String getRentUnit() {
        return rentUnit == null ? "" : rentUnit.trim();
    }

    public void setRentUnit(String rentUnit) {
        this.rentUnit = rentUnit == null ? null : rentUnit.trim();
    }

    public Date getAlterRentStartDate() {
        return alterRentStartDate;
    }

    public void setAlterRentStartDate(Date alterRentStartDate) {
        this.alterRentStartDate = alterRentStartDate;
    }

    public Date getAlterRentEndDate() {
        return alterRentEndDate;
    }

    public void setAlterRentEndDate(Date alterRentEndDate) {
        this.alterRentEndDate = alterRentEndDate;
    }

    public BigDecimal getAlterTrrm() {
        return alterTrrm;
    }

    public void setAlterTrrm(BigDecimal alterTrrm) {
        this.alterTrrm = alterTrrm;
    }

    public BigDecimal getAlterPrice() {
        return alterPrice;
    }

    public void setAlterPrice(BigDecimal alterPrice) {
        this.alterPrice = alterPrice;
    }

    public BigDecimal getAlterAmt() {
        return alterAmt;
    }

    public void setAlterAmt(BigDecimal alterAmt) {
        this.alterAmt = alterAmt;
    }

    public BigDecimal getContrTrrm() {
        return contrTrrm;
    }

    public void setContrTrrm(BigDecimal contrTrrm) {
        this.contrTrrm = contrTrrm;
    }

    public String getIsNetPrice() {
        return isNetPrice == null ? "" : isNetPrice.trim();
    }

    public void setIsNetPrice(String isNetPrice) {
        this.isNetPrice = isNetPrice == null ? null : isNetPrice.trim();
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

    public BigDecimal getPriceNoTax() {
        return priceNoTax;
    }

    public void setPriceNoTax(BigDecimal priceNoTax) {
        this.priceNoTax = priceNoTax;
    }

    public BigDecimal getContractSumNoTax() {
        return contractSumNoTax;
    }

    public void setContractSumNoTax(BigDecimal contractSumNoTax) {
        this.contractSumNoTax = contractSumNoTax;
    }

    public BigDecimal getChangePriceNoTax() {
        return changePriceNoTax;
    }

    public void setChangePriceNoTax(BigDecimal changePriceNoTax) {
        this.changePriceNoTax = changePriceNoTax;
    }

    public BigDecimal getChangeContractSumNoTax() {
        return changeContractSumNoTax;
    }

    public void setChangeContractSumNoTax(BigDecimal changeContractSumNoTax) {
        this.changeContractSumNoTax = changeContractSumNoTax;
    }

    public BigDecimal getAlterPriceNoPrice() {
        return alterPriceNoPrice;
    }

    public void setAlterPriceNoPrice(BigDecimal alterPriceNoPrice) {
        this.alterPriceNoPrice = alterPriceNoPrice;
    }

    public BigDecimal getAlterAmtNoTax() {
        return alterAmtNoTax;
    }

    public void setAlterAmtNoTax(BigDecimal alterAmtNoTax) {
        this.alterAmtNoTax = alterAmtNoTax;
    }

    public BigDecimal getAlterAmtTax() {
        return alterAmtTax;
    }

    public void setAlterAmtTax(BigDecimal alterAmtTax) {
        this.alterAmtTax = alterAmtTax;
    }

    public BigDecimal getAlterPriceNoTax() {
        return alterPriceNoTax;
    }

    public void setAlterPriceNoTax(BigDecimal alterPriceNoTax) {
        this.alterPriceNoTax = alterPriceNoTax;
    }

    public String getContractID() {
        return contractID == null ? "" : contractID.trim();
    }

    public void setContractID(String contractID) {
        this.contractID = contractID == null ? null : contractID.trim();
    }

    public String getTaxRate() {
        return taxRate == null ? "" : taxRate.trim();
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate == null ? null : taxRate.trim();
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
