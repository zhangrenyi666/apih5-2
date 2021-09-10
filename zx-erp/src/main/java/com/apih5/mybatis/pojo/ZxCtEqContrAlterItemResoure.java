package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import com.apih5.framework.entity.BasePojo;

public class ZxCtEqContrAlterItemResoure extends BasePojo {
    // 主键ID
    private String zxCtEqContrAlterItemResoureId;

    // 合同协议id
    private String contrAlterID;

    // 设备分类基础表中的parentID
    private String parentID;

    // 设备大类名称
    private String catParentName;

    // 设备分类基础表中的ID
    private String zxEqResCategoryId;

    // 设备编码
    private String catCode;

    // 设备名称
    private String catName;

    // 单位
    private String unit;

    // 型号
    private String spec;

    // 变更类型
    private String alterType;

    // 税率（%）
    private String taxRate;

    // 数量
    private BigDecimal qty;

    // 含税合同单价（元/台）
    private BigDecimal price;

    // 含税合同金额（元）
    private BigDecimal contractSum;

    // 不含税合同单价（元/台)
    private BigDecimal priceNoTax;

    // 不含税合同金额（元）
    private BigDecimal contractSumNoTax;

    // 不含税税额
    private BigDecimal contractSumTax;

    // 变更后数量
    private BigDecimal alterTrrm;

    // 变更后含税单价（元/台）
    private BigDecimal alterPrice;

    // 变更后不含税单价
    private BigDecimal alterPriceNoTax;

    // 变更后含税金额(元)
    private BigDecimal alterAmt;

    // 变更后不含税金额（元）
    private BigDecimal alterAmtNoTax;

    // 变更后税额（元）
    private BigDecimal alterAmtTax;

    // 租期(台班)
    private String pp10;

    // 计划开始时间
    private Date planStartDate;

    // 计划结束时间
    private Date planEndDate;

    // 租赁开始时间
    private Date actualStartDate;

    // 租赁结束时间
    private Date actualEndDate;

    // 合同明细id
    private String contrItemID;

    // 变更日期
    private Date changeDate;

    // 租赁单位
    private String rentUnit;

    // 备注
    private String remark;

    // 变更后租赁开始时间
    private Date alterRentStartDate;

    // 变更后租赁结束时间
    private Date alterRentEndDate;

    // 0
    private BigDecimal contrTrrm;

    // 0
    private String isNetPrice;

    // 0
    private String requestEdit;

    // 上期末变更后金额
    private BigDecimal upAlterContractSum;

    // 上期末变更后金额不含税
    private BigDecimal upAlterContractSumNoTax;

    // 上期末变更后税额
    private BigDecimal upAlterContractSumTax;

    public String getZxCtEqContrAlterItemResoureId() {
        return zxCtEqContrAlterItemResoureId == null ? "" : zxCtEqContrAlterItemResoureId.trim();
    }

    public void setZxCtEqContrAlterItemResoureId(String zxCtEqContrAlterItemResoureId) {
        this.zxCtEqContrAlterItemResoureId = zxCtEqContrAlterItemResoureId == null ? null : zxCtEqContrAlterItemResoureId.trim();
    }

    public String getContrAlterID() {
        return contrAlterID == null ? "" : contrAlterID.trim();
    }

    public void setContrAlterID(String contrAlterID) {
        this.contrAlterID = contrAlterID == null ? null : contrAlterID.trim();
    }

    public String getParentID() {
        return parentID == null ? "" : parentID.trim();
    }

    public void setParentID(String parentID) {
        this.parentID = parentID == null ? null : parentID.trim();
    }

    public String getCatParentName() {
        return catParentName == null ? "" : catParentName.trim();
    }

    public void setCatParentName(String catParentName) {
        this.catParentName = catParentName == null ? null : catParentName.trim();
    }

    public String getZxEqResCategoryId() {
        return zxEqResCategoryId == null ? "" : zxEqResCategoryId.trim();
    }

    public void setZxEqResCategoryId(String zxEqResCategoryId) {
        this.zxEqResCategoryId = zxEqResCategoryId == null ? null : zxEqResCategoryId.trim();
    }

    public String getCatCode() {
        return catCode == null ? "" : catCode.trim();
    }

    public void setCatCode(String catCode) {
        this.catCode = catCode == null ? null : catCode.trim();
    }

    public String getCatName() {
        return catName == null ? "" : catName.trim();
    }

    public void setCatName(String catName) {
        this.catName = catName == null ? null : catName.trim();
    }

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getSpec() {
        return spec == null ? "" : spec.trim();
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getAlterType() {
        return alterType == null ? "" : alterType.trim();
    }

    public void setAlterType(String alterType) {
        this.alterType = alterType == null ? null : alterType.trim();
    }

    public String getTaxRate() {
        return taxRate == null ? "" : taxRate.trim();
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate == null ? null : taxRate.trim();
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

    public BigDecimal getContractSumTax() {
        return contractSumTax;
    }

    public void setContractSumTax(BigDecimal contractSumTax) {
        this.contractSumTax = contractSumTax;
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

    public BigDecimal getAlterPriceNoTax() {
        return alterPriceNoTax;
    }

    public void setAlterPriceNoTax(BigDecimal alterPriceNoTax) {
        this.alterPriceNoTax = alterPriceNoTax;
    }

    public BigDecimal getAlterAmt() {
        return alterAmt;
    }

    public void setAlterAmt(BigDecimal alterAmt) {
        this.alterAmt = alterAmt;
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

    public String getPp10() {
        return pp10 == null ? "" : pp10.trim();
    }

    public void setPp10(String pp10) {
        this.pp10 = pp10 == null ? null : pp10.trim();
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

    public String getContrItemID() {
        return contrItemID == null ? "" : contrItemID.trim();
    }

    public void setContrItemID(String contrItemID) {
        this.contrItemID = contrItemID == null ? null : contrItemID.trim();
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

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public BigDecimal getUpAlterContractSum() {
        return upAlterContractSum;
    }

    public void setUpAlterContractSum(BigDecimal upAlterContractSum) {
        this.upAlterContractSum = upAlterContractSum;
    }

    public BigDecimal getUpAlterContractSumNoTax() {
        return upAlterContractSumNoTax;
    }

    public void setUpAlterContractSumNoTax(BigDecimal upAlterContractSumNoTax) {
        this.upAlterContractSumNoTax = upAlterContractSumNoTax;
    }

    public BigDecimal getUpAlterContractSumTax() {
        return upAlterContractSumTax;
    }

    public void setUpAlterContractSumTax(BigDecimal upAlterContractSumTax) {
        this.upAlterContractSumTax = upAlterContractSumTax;
    }

}
