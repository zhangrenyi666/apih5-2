package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkTurnoverCheck extends BasePojo {
    // 主键
    private String id;

    // 收料单位ID
    private String orgID;

    // 收料单位
    private String orgName;

    // 单据编号
    private String billNo;

    // 发票号
    private String invoiceNo;

    // 冲账日期
    private Date busDate;

    // 物资来源
    private String materialSource;

    // 预收单编号
    private String ysdNo;

    // 预收单ID
    private String ysdID;

    // 金额
    private BigDecimal amt;

    // 物资员
    private String consignee;

    // 单据状态
    private String billStatus;

    // 采购类别
    private String purchType;

    // 合同ID
    private String contractID;

    // 合同名称
    private String contractName;

    // 供货单位ID
    private String outOrgID;

    // 供货单位
    private String outOrgName;

    // 税率
    private String taxRate;

    // 是否预收
    private String isDeduct;

    // 公司id
    private String companyId;

    // 公司名称
    private String companyName;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    // 资源编号
    private String resCode;

    // 物资名称
    private String resName;

    // 规格型号
    private String spec;

    // 计量单位
    private String resUnit;

    // 数量
    private BigDecimal inQty;

    // 购入单价合计
    private BigDecimal inPrice;

    // 购入不含税单价
    private BigDecimal inPriceNoTax;

    // 原值
    private BigDecimal inAmt;

    // 累计摊销
    private BigDecimal feeSum;

    // 净值
    private BigDecimal remainAmt;

    // 开始时间
    private String beginDate;

    // 结束时间
    private String endDate;

    // 物资编码id
    private String resID;

    private List<ZxSkTurnoverCheckItem> zxSkTurnoverCheckItemList;

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

    public List<ZxSkTurnoverCheckItem> getZxSkTurnoverCheckItemList() {
        return zxSkTurnoverCheckItemList;
    }

    public void setZxSkTurnoverCheckItemList(List<ZxSkTurnoverCheckItem> zxSkTurnoverCheckItemList) {
        this.zxSkTurnoverCheckItemList = zxSkTurnoverCheckItemList;
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

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getBillNo() {
        return billNo == null ? "" : billNo.trim();
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public String getInvoiceNo() {
        return invoiceNo == null ? "" : invoiceNo.trim();
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo == null ? null : invoiceNo.trim();
    }

    public Date getBusDate() {
        return busDate;
    }

    public void setBusDate(Date busDate) {
        this.busDate = busDate;
    }

    public String getMaterialSource() {
        return materialSource == null ? "" : materialSource.trim();
    }

    public void setMaterialSource(String materialSource) {
        this.materialSource = materialSource == null ? null : materialSource.trim();
    }

    public String getYsdNo() {
        return ysdNo == null ? "" : ysdNo.trim();
    }

    public void setYsdNo(String ysdNo) {
        this.ysdNo = ysdNo == null ? null : ysdNo.trim();
    }

    public String getYsdID() {
        return ysdID == null ? "" : ysdID.trim();
    }

    public void setYsdID(String ysdID) {
        this.ysdID = ysdID == null ? null : ysdID.trim();
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getConsignee() {
        return consignee == null ? "" : consignee.trim();
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee == null ? null : consignee.trim();
    }

    public String getBillStatus() {
        return billStatus == null ? "" : billStatus.trim();
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus == null ? null : billStatus.trim();
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

    public String getOutOrgID() {
        return outOrgID == null ? "" : outOrgID.trim();
    }

    public void setOutOrgID(String outOrgID) {
        this.outOrgID = outOrgID == null ? null : outOrgID.trim();
    }

    public String getOutOrgName() {
        return outOrgName == null ? "" : outOrgName.trim();
    }

    public void setOutOrgName(String outOrgName) {
        this.outOrgName = outOrgName == null ? null : outOrgName.trim();
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

    public String getResCode() {
        return resCode == null ? "" : resCode.trim();
    }

    public void setResCode(String resCode) {
        this.resCode = resCode == null ? null : resCode.trim();
    }

    public String getResName() {
        return resName == null ? "" : resName.trim();
    }

    public void setResName(String resName) {
        this.resName = resName == null ? null : resName.trim();
    }

    public String getSpec() {
        return spec == null ? "" : spec.trim();
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getResUnit() {
        return resUnit == null ? "" : resUnit.trim();
    }

    public void setResUnit(String resUnit) {
        this.resUnit = resUnit == null ? null : resUnit.trim();
    }

    public BigDecimal getInQty() {
        return inPrice;
    }

    public void setInQty(BigDecimal inQty) {
        this.inQty = inQty;
    }

    public BigDecimal getInPrice() {
        return inPrice;
    }

    public void setInPrice(BigDecimal inPrice) {
        this.inPrice = inPrice;
    }
    public BigDecimal getInPriceNoTax() {
        return inPriceNoTax;
    }

    public void setInPriceNoTax(BigDecimal inPriceNoTax) {
        this.inPriceNoTax = inPriceNoTax;
    }

    public BigDecimal getInAmt() { return inAmt; }

    public void setInAmt(BigDecimal inAmt) {
        this.inAmt = inAmt;
    }

    public BigDecimal getFeeSum() {
        return feeSum;
    }

    public void setFeeSum(BigDecimal feeSum) {
        this.feeSum = feeSum;
    }

    public BigDecimal getRemainAmt() {
        return remainAmt;
    }

    public void setRemainAmt(BigDecimal remainAmt) {
        this.remainAmt = remainAmt;
    }

    public String getBeginDate() {
        return beginDate == null ? "" : beginDate.trim();
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate == null ? null : beginDate.trim();
    }

    public String getEndDate() {
        return endDate == null ? "" : endDate.trim();
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate == null ? null : endDate.trim();
    }

    public String getResID() {
        return resID == null ? "" : resID.trim();
    }

    public void setResID(String resID) {
        this.resID = resID == null ? null : resID.trim();
    }

}
