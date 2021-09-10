package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.poi.hpsf.Decimal;

public class ZxCtFsContractReviewDetail extends BasePojo {
    // 主键
    private String conRevDetailId;

    // 合同ID
    private String zxCtFsContractId;

    // 清单编号
    private String workNo;

    // 清单名称
    private String workName;

    // 型号
    private String spec;

    // 计量单位
    private String unit;

    // 设备ID(暂用workType)
    private String workType;

    // 层次
    private String treenode;

    // 合同数量
    private Integer qty;

    // 含税合同单价
    private BigDecimal price;

    // 含税合同金额
    private BigDecimal contractSum;

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

    // 评审ID
    private String contractReviewId;

    // 租期(台班)
    private String pp10;

    // 变更后数量
    private Integer changeQty;

    // 变更后含税单价
    private BigDecimal changePrice;

    // 变更后含税金额
    private BigDecimal changeContractSum;

    // 租赁单位
    private String rentUnit;

    // 变更后租赁开始时间
    private Date alterRentStartDate;

    // 变更后租赁结束时间
    private Date alterRentEndDate;

    // 要求修改
    private String requestEdit;

    // 修改人
    private String editUserId;

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

    // 变更后不含税金额
    private BigDecimal changeContractSumNoTax;

    // 税率(%)
    private String taxRate;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    //是否抵扣
    private String isDeduct;


//税额
    private BigDecimal contractSumTax;

   //文件列表（文件工具）
    private List<ZxErpFile> zxErpFileList;

    public BigDecimal getContractSumTax() {
        return contractSumTax;
    }

    public void setContractSumTax(BigDecimal contractSumTax) {
        this.contractSumTax = contractSumTax;
    }

    public String getIsDeduct() {
        return isDeduct;
    }

    public void setIsDeduct(String isDeduct) {
        this.isDeduct = isDeduct;
    }

    public String getConRevDetailId() {
        return conRevDetailId == null ? "" : conRevDetailId.trim();
    }

    public void setConRevDetailId(String conRevDetailId) {
        this.conRevDetailId = conRevDetailId == null ? null : conRevDetailId.trim();
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

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
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

    public String getPp10() {
        return pp10 == null ? "" : pp10.trim();
    }

    public void setPp10(String pp10) {
        this.pp10 = pp10 == null ? null : pp10.trim();
    }

    public Integer getChangeQty() {
        return changeQty;
    }

    public void setChangeQty(Integer changeQty) {
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

    public String getRequestEdit() {
        return requestEdit == null ? "" : requestEdit.trim();
    }

    public void setRequestEdit(String requestEdit) {
        this.requestEdit = requestEdit == null ? null : requestEdit.trim();
    }

    public String getEditUserId() {
        return editUserId == null ? "" : editUserId.trim();
    }

    public void setEditUserId(String editUserId) {
        this.editUserId = editUserId == null ? null : editUserId.trim();
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

    public List<ZxErpFile> getZxErpFileList() {
        return zxErpFileList;
    }

    public void setZxErpFileList(List<ZxErpFile> zxErpFileList) {
        this.zxErpFileList = zxErpFileList;
    }

    public String getZxCtFsContractId() {
        return zxCtFsContractId;
    }

    public void setZxCtFsContractId(String zxCtFsContractId) {
        this.zxCtFsContractId = zxCtFsContractId;
    }

    public String getContractReviewId() {
        return contractReviewId;
    }

    public void setContractReviewId(String contractReviewId) {
        this.contractReviewId = contractReviewId;
    }
}
