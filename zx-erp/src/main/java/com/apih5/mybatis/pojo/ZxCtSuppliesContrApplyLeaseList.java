package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCtSuppliesContrApplyLeaseList extends BasePojo {
    // 主键
    private String applyLeaseListId;

    // 租期单位
    private String rentUnit;

    // 租期
    private BigDecimal contrTrrm;

    // 要求修改
    private String requestEdit;

    // 修改日期
    private String editDate;

    // 修改人
    private String editUserID;

    // 修改人
    private String editUserName;

    // 物资名称
    private String workName;

    // 物资类别ID
    private String workTypeID;

    // 物资类别
    private String workType;

    // 物资规格
    private String spec;

    // 物资编码ID
    private String workID;

    // 物资编码
    private String workNo;

    // 税率(%)
    private String taxRate;

    // 税额
    private BigDecimal contractSumTax;

    // 数量
    private BigDecimal qty;

    // 是否网价
    private String isNetPrice;

    // 是否抵扣
    private String isDeduct;

    // 实际开始时间
    private Date actualStartDate;

    // 实际结束时间
    private Date actualEndDate;

    // 评审ID
    private String pp5;

    // 界面展现类型
    private String viewType;

    // 计划开始时间
    private Date planStartDate;

    // 计划结束时间
    private Date planEndDate;

    // 合同ID
    private String contractID;

    // 含税合同金额
    private BigDecimal contractSum;

    // 含税合同单价
    private BigDecimal price;

    // 单位
    private String treenode;

    // 单位
    private String unit;

    // 不含税合同金额
    private BigDecimal contractSumNoTax;

    // 不含税合同单价
    private BigDecimal priceNoTax;

    // 变更日期
    private Date changeDate;

    // 变更后租期
    private BigDecimal alterTrrm;

    // 变更后税额
    private BigDecimal changeContractSumTax;

    // 变更后数量
    private BigDecimal changeQty;

    // 变更后含税金额
    private BigDecimal changeContractSum;

    // 变更后含税单价
    private BigDecimal changePrice;

    // 变更后不含税金额
    private BigDecimal changeContractSumNoTax;

    // 变更后不含税单价
    private BigDecimal changePriceNoTax;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getApplyLeaseListId() {
        return applyLeaseListId == null ? "" : applyLeaseListId.trim();
    }

    public void setApplyLeaseListId(String applyLeaseListId) {
        this.applyLeaseListId = applyLeaseListId == null ? null : applyLeaseListId.trim();
    }

    public String getRentUnit() {
        return rentUnit == null ? "" : rentUnit.trim();
    }

    public void setRentUnit(String rentUnit) {
        this.rentUnit = rentUnit == null ? null : rentUnit.trim();
    }

    public BigDecimal getContrTrrm() {
        return contrTrrm;
    }

    public void setContrTrrm(BigDecimal contrTrrm) {
        this.contrTrrm = contrTrrm;
    }

    public String getRequestEdit() {
        return requestEdit == null ? "" : requestEdit.trim();
    }

    public void setRequestEdit(String requestEdit) {
        this.requestEdit = requestEdit == null ? null : requestEdit.trim();
    }

    public String getEditDate() {
        return editDate == null ? "" : editDate.trim();
    }

    public void setEditDate(String editDate) {
        this.editDate = editDate == null ? null : editDate.trim();
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

    public String getWorkName() {
        return workName == null ? "" : workName.trim();
    }

    public void setWorkName(String workName) {
        this.workName = workName == null ? null : workName.trim();
    }

    public String getWorkTypeID() {
        return workTypeID == null ? "" : workTypeID.trim();
    }

    public void setWorkTypeID(String workTypeID) {
        this.workTypeID = workTypeID == null ? null : workTypeID.trim();
    }

    public String getWorkType() {
        return workType == null ? "" : workType.trim();
    }

    public void setWorkType(String workType) {
        this.workType = workType == null ? null : workType.trim();
    }

    public String getSpec() {
        return spec == null ? "" : spec.trim();
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
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

    public String getTaxRate() {
        return taxRate == null ? "" : taxRate.trim();
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate == null ? null : taxRate.trim();
    }

    public BigDecimal getContractSumTax() {
        return contractSumTax;
    }

    public void setContractSumTax(BigDecimal contractSumTax) {
        this.contractSumTax = contractSumTax;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public String getIsNetPrice() {
        return isNetPrice == null ? "" : isNetPrice.trim();
    }

    public void setIsNetPrice(String isNetPrice) {
        this.isNetPrice = isNetPrice == null ? null : isNetPrice.trim();
    }

    public String getIsDeduct() {
        return isDeduct == null ? "" : isDeduct.trim();
    }

    public void setIsDeduct(String isDeduct) {
        this.isDeduct = isDeduct == null ? null : isDeduct.trim();
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

    public String getPp5() {
        return pp5 == null ? "" : pp5.trim();
    }

    public void setPp5(String pp5) {
        this.pp5 = pp5 == null ? null : pp5.trim();
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

    public String getContractID() {
        return contractID == null ? "" : contractID.trim();
    }

    public void setContractID(String contractID) {
        this.contractID = contractID == null ? null : contractID.trim();
    }

    public BigDecimal getContractSum() {
        return contractSum;
    }

    public void setContractSum(BigDecimal contractSum) {
        this.contractSum = contractSum;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTreenode() {
        return treenode == null ? "" : treenode.trim();
    }

    public void setTreenode(String treenode) {
        this.treenode = treenode == null ? null : treenode.trim();
    }

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getContractSumNoTax() {
        return contractSumNoTax;
    }

    public void setContractSumNoTax(BigDecimal contractSumNoTax) {
        this.contractSumNoTax = contractSumNoTax;
    }

    public BigDecimal getPriceNoTax() {
        return priceNoTax;
    }

    public void setPriceNoTax(BigDecimal priceNoTax) {
        this.priceNoTax = priceNoTax;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public BigDecimal getAlterTrrm() {
        return alterTrrm;
    }

    public void setAlterTrrm(BigDecimal alterTrrm) {
        this.alterTrrm = alterTrrm;
    }

    public BigDecimal getChangeContractSumTax() {
        return changeContractSumTax;
    }

    public void setChangeContractSumTax(BigDecimal changeContractSumTax) {
        this.changeContractSumTax = changeContractSumTax;
    }

    public BigDecimal getChangeQty() {
        return changeQty;
    }

    public void setChangeQty(BigDecimal changeQty) {
        this.changeQty = changeQty;
    }

    public BigDecimal getChangeContractSum() {
        return changeContractSum;
    }

    public void setChangeContractSum(BigDecimal changeContractSum) {
        this.changeContractSum = changeContractSum;
    }

    public BigDecimal getChangePrice() {
        return changePrice;
    }

    public void setChangePrice(BigDecimal changePrice) {
        this.changePrice = changePrice;
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
