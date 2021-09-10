package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCtOtherSupplyAgreementWorks extends BasePojo {
    // 主键
    private String zxCtOtherSupplyAgreementWorksId;

    // 变更类型
    private String alterType;

    // 计划结束时间
    private Date planEndDate;

    // 合同变更ID（旧系统字段contrAlterId）
    private String zxCtOtherSupplyAgreementId;

    // 合同明细ID（旧系统字段contrItemId）
    private String zxCtOtherWorksId;

    // 界面展现类型
    private String viewType;

    // 物资类别ID
    private String workTypeId;

    // 物资类别
    private String workType;

    // 物资编码ID
    private String workId;

    // 清单编号
    private String workNo;

    // 清单名称
    private String workName;

    // 规格型号
    private String spec;

    // 单位
    private String unit;

    // 数量
    private BigDecimal qty;

    // 租期
    private String contrTrrm;

    // 含税单价
    private BigDecimal price;

    // 含税金额
    private BigDecimal contractSum;

    // 变更后数量
    private BigDecimal changeQty;

    // 变更后单价
    private BigDecimal changePrice;

    // 租期单位
    private String rentUnit;

    // 变更后租期
    private String alterTrrm;

    // 变更后含税金额
    private BigDecimal changeContractSum;

    // 计划开始时间
    private Date planStartDate;

    // 实际开始时间
    private Date actualStartDate;

    // 实际结束时间
    private Date actualEndDate;

    // 变更日期
    private Date changeDate;

    // 变更后不含税单价
    private BigDecimal changePriceNoTax;

    // 不含税单价
    private BigDecimal priceNoTax;

    // 不含税金额
    private BigDecimal contractSumNoTax;

    // 不含税税额
    private BigDecimal contractSumTax;

    // 税率(%)
    private String taxRate;

    // 是否抵扣
    private String isDeduct;

    // 变更后不含税金额
    private BigDecimal changeContractSumNoTax;

    // 变更后税额
    private BigDecimal changeContractSumTax;

    // 上期末变更后金额
    private BigDecimal upAlterContractSum;

    // 上期末变更后金额不含税
    private BigDecimal upAlterContractSumNoTax;

    // 上期末变更后税额
    private BigDecimal upAlterContractSumTax;

    // 备注
    private String remark;

    // 排序
    private int sort=0;

    public String getZxCtOtherSupplyAgreementWorksId() {
        return zxCtOtherSupplyAgreementWorksId == null ? "" : zxCtOtherSupplyAgreementWorksId.trim();
    }

    public void setZxCtOtherSupplyAgreementWorksId(String zxCtOtherSupplyAgreementWorksId) {
        this.zxCtOtherSupplyAgreementWorksId = zxCtOtherSupplyAgreementWorksId == null ? null : zxCtOtherSupplyAgreementWorksId.trim();
    }

    public String getAlterType() {
        return alterType == null ? "" : alterType.trim();
    }

    public void setAlterType(String alterType) {
        this.alterType = alterType == null ? null : alterType.trim();
    }

    public Date getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(Date planEndDate) {
        this.planEndDate = planEndDate;
    }

    public String getZxCtOtherSupplyAgreementId() {
        return zxCtOtherSupplyAgreementId == null ? "" : zxCtOtherSupplyAgreementId.trim();
    }

    public void setZxCtOtherSupplyAgreementId(String zxCtOtherSupplyAgreementId) {
        this.zxCtOtherSupplyAgreementId = zxCtOtherSupplyAgreementId == null ? null : zxCtOtherSupplyAgreementId.trim();
    }

    public String getZxCtOtherWorksId() {
        return zxCtOtherWorksId == null ? "" : zxCtOtherWorksId.trim();
    }

    public void setZxCtOtherWorksId(String zxCtOtherWorksId) {
        this.zxCtOtherWorksId = zxCtOtherWorksId == null ? null : zxCtOtherWorksId.trim();
    }

    public String getViewType() {
        return viewType == null ? "" : viewType.trim();
    }

    public void setViewType(String viewType) {
        this.viewType = viewType == null ? null : viewType.trim();
    }

    public String getWorkTypeId() {
        return workTypeId == null ? "" : workTypeId.trim();
    }

    public void setWorkTypeId(String workTypeId) {
        this.workTypeId = workTypeId == null ? null : workTypeId.trim();
    }

    public String getWorkType() {
        return workType == null ? "" : workType.trim();
    }

    public void setWorkType(String workType) {
        this.workType = workType == null ? null : workType.trim();
    }

    public String getWorkId() {
        return workId == null ? "" : workId.trim();
    }

    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
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

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public String getContrTrrm() {
        return contrTrrm == null ? "" : contrTrrm.trim();
    }

    public void setContrTrrm(String contrTrrm) {
        this.contrTrrm = contrTrrm == null ? null : contrTrrm.trim();
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

    public String getRentUnit() {
        return rentUnit == null ? "" : rentUnit.trim();
    }

    public void setRentUnit(String rentUnit) {
        this.rentUnit = rentUnit == null ? null : rentUnit.trim();
    }

    public String getAlterTrrm() {
        return alterTrrm == null ? "" : alterTrrm.trim();
    }

    public void setAlterTrrm(String alterTrrm) {
        this.alterTrrm = alterTrrm == null ? null : alterTrrm.trim();
    }

    public BigDecimal getChangeContractSum() {
        return changeContractSum;
    }

    public void setChangeContractSum(BigDecimal changeContractSum) {
        this.changeContractSum = changeContractSum;
    }

    public Date getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(Date planStartDate) {
        this.planStartDate = planStartDate;
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

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public BigDecimal getChangePriceNoTax() {
        return changePriceNoTax;
    }

    public void setChangePriceNoTax(BigDecimal changePriceNoTax) {
        this.changePriceNoTax = changePriceNoTax;
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

    public String getTaxRate() {
        return taxRate == null ? "" : taxRate.trim();
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate == null ? null : taxRate.trim();
    }

    public String getIsDeduct() {
        return isDeduct == null ? "" : isDeduct.trim();
    }

    public void setIsDeduct(String isDeduct) {
        this.isDeduct = isDeduct == null ? null : isDeduct.trim();
    }

    public BigDecimal getChangeContractSumNoTax() {
        return changeContractSumNoTax;
    }

    public void setChangeContractSumNoTax(BigDecimal changeContractSumNoTax) {
        this.changeContractSumNoTax = changeContractSumNoTax;
    }

    public BigDecimal getChangeContractSumTax() {
        return changeContractSumTax;
    }

    public void setChangeContractSumTax(BigDecimal changeContractSumTax) {
        this.changeContractSumTax = changeContractSumTax;
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
