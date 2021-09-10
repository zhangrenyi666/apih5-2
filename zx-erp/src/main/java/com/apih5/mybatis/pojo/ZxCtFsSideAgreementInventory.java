package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCtFsSideAgreementInventory extends BasePojo {
    // 主键
    private String zxCtFsSideAgreementInventoryId;

    // 补充协议ID
    private String zxCtFsSideAgreementId;

    // 清单编号
    private String workNo;

    // 清单名称
    private String workName;

    // 规格型号
    private String spec;

    // 单位
    private String unit;

    // 物资类别
    private String workType;

    // 单位
    private String treenode;

    // 数量
    private Integer qty;

    // 含税单价
    private BigDecimal price;

    // 含税金额
    private BigDecimal contractSum;

    // 界面展现类型
    private String viewType;

    // 计划开始时间
    private Date planStartDate;

    // 计划结束时间
    private Date planEndDate;

    // 实际开始时间
    private Date actualStartDate;

    // 实际结束时间
    private Date actualEndDate;

    // 物资类别ID
    private String workTypeID;

    // 物资编码ID
    private String workID;

    // 变更后数量
    private int changeQty=0;

    // 变更后单价
    private BigDecimal changePrice;

    // 变更后含税金额
    private BigDecimal changeContractSum;

    // 合同明细ID
    private String conRevDetailId;

    // 变更日期
    private Date changeDate;

    // 租期单位
    private String rentUnit;

    // 变更后租期
    private BigDecimal alterTrrm;

    // 租期
    private BigDecimal contrTrrm;

    // 变更类型
    private String alterType;

    // 上期末变更后金额
    private BigDecimal upAlterContractSum;

    // 不含税单价
    private BigDecimal priceNoTax;

    // 不含税金额
    private BigDecimal contractSumNoTax;

    // 变更后不含税金额
    private BigDecimal changeContractSumNoTax;

    // 变更后不含税单价
    private BigDecimal changePriceNoTax;

    // 税率(%)
    private String taxRate;

    // 上期末变更后金额不含税
    private BigDecimal upAlterContractSumNoTax;

    // 不含税税额
    private BigDecimal contractSumTax;

    // 变更后税额
    private BigDecimal changeContractSumTax;

    // 上期末变更后税额
    private BigDecimal upAlterContractSumTax;

    // 备注
    private String remarks;

    // 是否抵扣
    private String isDeduct;

    // 是否变更
    private String isChanged;

    // 排序
    private int sort=0;

    private String zxCtFsContractId;

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getIsChanged() {
        return isChanged;
    }

    public void setIsChanged(String isChanged) {
        this.isChanged = isChanged;
    }

    public String getIsDeduct() {
        return isDeduct== null ? "" : isDeduct.trim();
    }


    public void setIsDeduct(String isDeduct) {
        this.isDeduct = isDeduct == null ? null : isDeduct.trim();;
    }

    public String getZxCtFsContractId() {
        return zxCtFsContractId;
    }

    public void setZxCtFsContractId(String zxCtFsContractId) {
        this.zxCtFsContractId = zxCtFsContractId;
    }

    public String getZxCtFsSideAgreementInventoryId() {
        return zxCtFsSideAgreementInventoryId == null ? "" : zxCtFsSideAgreementInventoryId.trim();
    }

    public void setZxCtFsSideAgreementInventoryId(String zxCtFsSideAgreementInventoryId) {
        this.zxCtFsSideAgreementInventoryId = zxCtFsSideAgreementInventoryId == null ? null : zxCtFsSideAgreementInventoryId.trim();
    }

    public String getZxCtFsSideAgreementId() {
        return zxCtFsSideAgreementId == null ? "" : zxCtFsSideAgreementId.trim();
    }

    public void setZxCtFsSideAgreementId(String zxCtFsSideAgreementId) {
        this.zxCtFsSideAgreementId = zxCtFsSideAgreementId == null ? null : zxCtFsSideAgreementId.trim();
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

    public int getChangeQty() {
        return changeQty;
    }

    public void setChangeQty(int changeQty) {
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

    public String getConRevDetailId() {
        return conRevDetailId == null ? "" : conRevDetailId.trim();
    }

    public void setConRevDetailId(String conRevDetailId) {
        this.conRevDetailId = conRevDetailId == null ? null : conRevDetailId.trim();
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

    public BigDecimal getAlterTrrm() {
        return alterTrrm;
    }

    public void setAlterTrrm(BigDecimal alterTrrm) {
        this.alterTrrm = alterTrrm;
    }

    public BigDecimal getContrTrrm() {
        return contrTrrm;
    }

    public void setContrTrrm(BigDecimal contrTrrm) {
        this.contrTrrm = contrTrrm;
    }

    public String getAlterType() {
        return alterType == null ? "" : alterType.trim();
    }

    public void setAlterType(String alterType) {
        this.alterType = alterType == null ? null : alterType.trim();
    }

    public BigDecimal getUpAlterContractSum() {
        return upAlterContractSum;
    }

    public void setUpAlterContractSum(BigDecimal upAlterContractSum) {
        this.upAlterContractSum = upAlterContractSum;
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

    public BigDecimal getChangeContractSumNoTax() {
        return changeContractSumNoTax;
    }

    public void setChangeContractSumNoTax(BigDecimal changeContractSumNoTax) {
        this.changeContractSumNoTax = changeContractSumNoTax;
    }

    public BigDecimal getChangePriceNoTax() {
        return changePriceNoTax;
    }

    public void setChangePriceNoTax(BigDecimal changePriceNoTax) {
        this.changePriceNoTax = changePriceNoTax;
    }

    public String getTaxRate() {
        return taxRate == null ? "" : taxRate.trim();
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate == null ? null : taxRate.trim();
    }

    public BigDecimal getUpAlterContractSumNoTax() {
        return upAlterContractSumNoTax;
    }

    public void setUpAlterContractSumNoTax(BigDecimal upAlterContractSumNoTax) {
        this.upAlterContractSumNoTax = upAlterContractSumNoTax;
    }

    public BigDecimal getContractSumTax() {
        return contractSumTax;
    }

    public void setContractSumTax(BigDecimal contractSumTax) {
        this.contractSumTax = contractSumTax;
    }

    public BigDecimal getChangeContractSumTax() {
        return changeContractSumTax;
    }

    public void setChangeContractSumTax(BigDecimal changeContractSumTax) {
        this.changeContractSumTax = changeContractSumTax;
    }

    public BigDecimal getUpAlterContractSumTax() {
        return upAlterContractSumTax;
    }

    public void setUpAlterContractSumTax(BigDecimal upAlterContractSumTax) {
        this.upAlterContractSumTax = upAlterContractSumTax;
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
