package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCtOtherWorks extends BasePojo {
    // 主键
    private String zxCtOtherWorksId;

    // 含税合同金额
    private BigDecimal contractSum;

    // 变更后租赁结束时间
    private Date alterRentEndDate;

    // 合同ID
    private String contractId;

    // 界面展现类型
    private String viewType;

    // 清单编号
    private String workNo;

    // 清单名称
    private String workName;

    // 型号
    private String spec;

    // 租赁单位
    private String rentUnit;

    // 计量单位
    private String unit;

    // 设备ID(暂用workType)
    private String workType;

    // 层次
    private String treeNode;

    // 租赁开始时间
    private Date actualStartDate;

    // 租赁结束时间
    private Date actualEndDate;

    // 租期(台班)
    private String leaseTerm;

    // 合同数量
    private BigDecimal qty;

    // 含税合同单价
    private BigDecimal price;

    // 变更后租赁开始时间
    private Date alterRentStartDate;

    // 变更后数量
    private BigDecimal changeQty;

    // 变更后含税单价
    private BigDecimal changePrice;

    // 变更后含税金额
    private BigDecimal changeContractSum;

    // 计划开始时间
    private Date planStartDate;

    // 计划结束时间
    private Date planEndDate;

    // 不含税合同单价
    private BigDecimal priceNoTax;

    // 税额
    private BigDecimal contractSumTax;

    // 不含税合同金额
    private BigDecimal contractSumNoTax;

    // 变更后不含税单价
    private BigDecimal changePriceNoTax;

    // 变更后税额
    private BigDecimal changeContractSumTax;

    // 变更后不含税金额
    private BigDecimal changeContractSumNoTax;

    // 税率(%)
    private String taxRate;

    // 是否抵扣
    private String isDeduct;

    // 评审ID（旧系统字段pp5）
    private String zxCtOtherId;

    // 要求修改
    private String requestEdit;

    // 其他合同管理外键id
    private String zxCtOtherManageId;

    // 备注
    private String remark;

    private List<ZxErpFile> zxErpFileList;
    // 排序
    private int sort=0;

    public String getZxCtOtherWorksId() {
        return zxCtOtherWorksId == null ? "" : zxCtOtherWorksId.trim();
    }

    public void setZxCtOtherWorksId(String zxCtOtherWorksId) {
        this.zxCtOtherWorksId = zxCtOtherWorksId == null ? null : zxCtOtherWorksId.trim();
    }

    public BigDecimal getContractSum() {
        return contractSum;
    }

    public void setContractSum(BigDecimal contractSum) {
        this.contractSum = contractSum;
    }

    public Date getAlterRentEndDate() {
        return alterRentEndDate;
    }

    public void setAlterRentEndDate(Date alterRentEndDate) {
        this.alterRentEndDate = alterRentEndDate;
    }

    public String getContractId() {
        return contractId == null ? "" : contractId.trim();
    }

    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    public String getViewType() {
        return viewType == null ? "" : viewType.trim();
    }

    public void setViewType(String viewType) {
        this.viewType = viewType == null ? null : viewType.trim();
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

    public String getRentUnit() {
        return rentUnit == null ? "" : rentUnit.trim();
    }

    public void setRentUnit(String rentUnit) {
        this.rentUnit = rentUnit == null ? null : rentUnit.trim();
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

    public String getTreeNode() {
        return treeNode == null ? "" : treeNode.trim();
    }

    public void setTreeNode(String treeNode) {
        this.treeNode = treeNode == null ? null : treeNode.trim();
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

    public String getLeaseTerm() {
        return leaseTerm == null ? "" : leaseTerm.trim();
    }

    public void setLeaseTerm(String leaseTerm) {
        this.leaseTerm = leaseTerm == null ? null : leaseTerm.trim();
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

    public Date getAlterRentStartDate() {
        return alterRentStartDate;
    }

    public void setAlterRentStartDate(Date alterRentStartDate) {
        this.alterRentStartDate = alterRentStartDate;
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

    public BigDecimal getPriceNoTax() {
        return priceNoTax;
    }

    public void setPriceNoTax(BigDecimal priceNoTax) {
        this.priceNoTax = priceNoTax;
    }

    public BigDecimal getContractSumTax() {
        return contractSumTax;
    }

    public void setContractSumTax(BigDecimal contractSumTax) {
        this.contractSumTax = contractSumTax;
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

    public BigDecimal getChangeContractSumTax() {
        return changeContractSumTax;
    }

    public void setChangeContractSumTax(BigDecimal changeContractSumTax) {
        this.changeContractSumTax = changeContractSumTax;
    }

    public BigDecimal getChangeContractSumNoTax() {
        return changeContractSumNoTax;
    }

    public void setChangeContractSumNoTax(BigDecimal changeContractSumNoTax) {
        this.changeContractSumNoTax = changeContractSumNoTax;
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

    public String getZxCtOtherId() {
        return zxCtOtherId == null ? "" : zxCtOtherId.trim();
    }

    public void setZxCtOtherId(String zxCtOtherId) {
        this.zxCtOtherId = zxCtOtherId == null ? null : zxCtOtherId.trim();
    }

    public String getRequestEdit() {
        return requestEdit == null ? "" : requestEdit.trim();
    }

    public void setRequestEdit(String requestEdit) {
        this.requestEdit = requestEdit == null ? null : requestEdit.trim();
    }

    public String getZxCtOtherManageId() {
        return zxCtOtherManageId == null ? "" : zxCtOtherManageId.trim();
    }

    public void setZxCtOtherManageId(String zxCtOtherManageId) {
        this.zxCtOtherManageId = zxCtOtherManageId == null ? null : zxCtOtherManageId.trim();
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

    public List<ZxErpFile> getZxErpFileList() {
        return zxErpFileList;
    }

    public void setZxErpFileList(List<ZxErpFile> zxErpFileList) {
        this.zxErpFileList = zxErpFileList;
    }
}
