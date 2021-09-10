package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkInvoice extends BasePojo {
    // 主键
    private String id;

    // 登记单位
    private String orgID;

    // 开票单位
    private String makeoutOrgID;

    // 发票号
    private String invoiceNo;

    // 冲账日期
    private Date makeoutDate;

    // 顾客
    private String customer;

    // 入账金额
    private BigDecimal amt;

    // 仓库ID
    private String whOrgID;

    // 仓库
    private String warehouseName;

    // 供方名称
    private String outOrgName;

    // 供方ID
    private String outOrgID;

    // 单据状态
    private String billStatus;

    // 预收单
    private String ysdID;

    // 预收单编号
    private String ysdNo;

    // 单据编号
    private String receNo;

    // 物资类别ID
    private String resourceID;

    // 物资类别
    private String resourceName;

    // 物资来源
    private String materialSource;

    // 采购类别
    private String purchType;

    // 合同ID号
    private String contractID;

    // 合同名称
    private String contractName;

    // 总金额
    private BigDecimal amtTotal;

    // 项目名称
    private String makeoutOrgName;

    // 公司id
    private String companyId;

    // 公司名称
    private String companyName;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    //公司id
    private String companyID;

    //明细
    private List<ZxSkInvoiceItem> zxSkInvoiceItemList;

    private List<Date> timeList;

    private List<BigDecimal> amtList;

    public List<BigDecimal> getAmtList() {
        return amtList;
    }

    public void setAmtList(List<BigDecimal> amtList) {
        this.amtList = amtList;
    }

    public List<Date> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<Date> timeList) {
        this.timeList = timeList;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public List<ZxSkInvoiceItem> getZxSkInvoiceItemList() {
        return zxSkInvoiceItemList;
    }

    public void setZxSkInvoiceItemList(List<ZxSkInvoiceItem> zxSkInvoiceItemList) {
        this.zxSkInvoiceItemList = zxSkInvoiceItemList;
    }

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getMakeoutOrgID() {
        return makeoutOrgID == null ? "" : makeoutOrgID.trim();
    }

    public void setMakeoutOrgID(String makeoutOrgID) {
        this.makeoutOrgID = makeoutOrgID == null ? null : makeoutOrgID.trim();
    }

    public String getInvoiceNo() {
        return invoiceNo == null ? "" : invoiceNo.trim();
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo == null ? null : invoiceNo.trim();
    }

    public Date getMakeoutDate() {
        return makeoutDate;
    }

    public void setMakeoutDate(Date makeoutDate) {
        this.makeoutDate = makeoutDate;
    }

    public String getCustomer() {
        return customer == null ? "" : customer.trim();
    }

    public void setCustomer(String customer) {
        this.customer = customer == null ? null : customer.trim();
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getWhOrgID() {
        return whOrgID == null ? "" : whOrgID.trim();
    }

    public void setWhOrgID(String whOrgID) {
        this.whOrgID = whOrgID == null ? null : whOrgID.trim();
    }

    public String getWarehouseName() {
        return warehouseName == null ? "" : warehouseName.trim();
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName == null ? null : warehouseName.trim();
    }

    public String getOutOrgName() {
        return outOrgName == null ? "" : outOrgName.trim();
    }

    public void setOutOrgName(String outOrgName) {
        this.outOrgName = outOrgName == null ? null : outOrgName.trim();
    }

    public String getOutOrgID() {
        return outOrgID == null ? "" : outOrgID.trim();
    }

    public void setOutOrgID(String outOrgID) {
        this.outOrgID = outOrgID == null ? null : outOrgID.trim();
    }

    public String getBillStatus() {
        return billStatus == null ? "" : billStatus.trim();
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus == null ? null : billStatus.trim();
    }

    public String getYsdID() {
        return ysdID == null ? "" : ysdID.trim();
    }

    public void setYsdID(String ysdID) {
        this.ysdID = ysdID == null ? null : ysdID.trim();
    }

    public String getYsdNo() {
        return ysdNo == null ? "" : ysdNo.trim();
    }

    public void setYsdNo(String ysdNo) {
        this.ysdNo = ysdNo == null ? null : ysdNo.trim();
    }

    public String getReceNo() {
        return receNo == null ? "" : receNo.trim();
    }

    public void setReceNo(String receNo) {
        this.receNo = receNo == null ? null : receNo.trim();
    }

    public String getResourceID() {
        return resourceID == null ? "" : resourceID.trim();
    }

    public void setResourceID(String resourceID) {
        this.resourceID = resourceID == null ? null : resourceID.trim();
    }

    public String getResourceName() {
        return resourceName == null ? "" : resourceName.trim();
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName == null ? null : resourceName.trim();
    }

    public String getMaterialSource() {
        return materialSource == null ? "" : materialSource.trim();
    }

    public void setMaterialSource(String materialSource) {
        this.materialSource = materialSource == null ? null : materialSource.trim();
    }

    public String getPurchType() {
        return purchType == null ? "" : purchType.trim();
    }

    public void setPurchType(String purchType) {
        this.purchType = purchType == null ? null : purchType.trim();
    }

    public String getContractID() {
        return contractID == null ? "" : contractID.trim();
    }

    public void setContractID(String contractID) {
        this.contractID = contractID == null ? null : contractID.trim();
    }

    public String getContractName() {
        return contractName == null ? "" : contractName.trim();
    }

    public void setContractName(String contractName) {
        this.contractName = contractName == null ? null : contractName.trim();
    }

    public BigDecimal getAmtTotal() {
        return amtTotal;
    }

    public void setAmtTotal(BigDecimal amtTotal) {
        this.amtTotal = amtTotal;
    }

    public String getMakeoutOrgName() {
        return makeoutOrgName == null ? "" : makeoutOrgName.trim();
    }

    public void setMakeoutOrgName(String makeoutOrgName) {
        this.makeoutOrgName = makeoutOrgName == null ? null : makeoutOrgName.trim();
    }

    public String getCompanyId() {
        return companyId == null ? "" : companyId.trim();
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCompanyName() {
        return companyName == null ? "" : companyName.trim();
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
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
