package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkTurnoverInItem extends BasePojo {
    // 主键
    private String id;

    // 单据ID
    private String billID;

    // 批次
    private String batchNo;

    // 物资ID
    private String resID;

    // 物资编码
    private String resCode;

    // 物资名称
    private String resName;

    // 规格型号
    private String spec;

    // 计量单位
    private String resUnit;

    // 数量
    private BigDecimal inQty;

    //旧的数量
    private BigDecimal oldQty;

    // 购入单价合计
    private BigDecimal inPrice;

    // 原值
    private BigDecimal inAmt;

    // 累计摊销
    private BigDecimal feeSum;

    //旧的摊销
    private BigDecimal oldFeeSum;

    // 净值
    private BigDecimal remainAmt;

    // 是否预收
    private String precollecte;

    // checkQty
    private BigDecimal checkQty;

    // 购入单价税额
    private BigDecimal inPriceTax;

    // 购入单价
    private BigDecimal inPriceNoTax;

    // 总值
    private BigDecimal inAmtTotal;

    // 结算状态
    private String settlementStatus;

    // 备注
    private String remarks;

    //预收单单号
    private String stockTransBillNo;

    // 排序
    private int sort=0;

    private String taxRate;

    private String isDeduct;

    //计价方式
    private String pricingManner;

    //编制机构
    private String planningAuthorities;

    public BigDecimal getOldFeeSum() {
        return oldFeeSum;
    }

    public void setOldFeeSum(BigDecimal oldFeeSum) {
        this.oldFeeSum = oldFeeSum;
    }

    public String getPricingManner() {
        return pricingManner;
    }

    public void setPricingManner(String pricingManner) {
        this.pricingManner = pricingManner;
    }

    public String getPlanningAuthorities() {
        return planningAuthorities;
    }

    public void setPlanningAuthorities(String planningAuthorities) {
        this.planningAuthorities = planningAuthorities;
    }

    public String getIsDeduct() {
        return isDeduct;
    }

    public void setIsDeduct(String isDeduct) {
        this.isDeduct = isDeduct;
    }

    public BigDecimal getOldQty() {
        return oldQty;
    }

    public void setOldQty(BigDecimal oldQty) {
        this.oldQty = oldQty;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getStockTransBillNo() {
        return stockTransBillNo;
    }

    public void setStockTransBillNo(String stockTransBillNo) {
        this.stockTransBillNo = stockTransBillNo;
    }

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBillID() {
        return billID == null ? "" : billID.trim();
    }

    public void setBillID(String billID) {
        this.billID = billID == null ? null : billID.trim();
    }

    public String getBatchNo() {
        return batchNo == null ? "" : batchNo.trim();
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }

    public String getResID() {
        return resID == null ? "" : resID.trim();
    }

    public void setResID(String resID) {
        this.resID = resID == null ? null : resID.trim();
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
        return inQty;
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

    public BigDecimal getInAmt() {
        return inAmt;
    }

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

    public String getPrecollecte() {
        return precollecte == null ? "" : precollecte.trim();
    }

    public void setPrecollecte(String precollecte) {
        this.precollecte = precollecte == null ? null : precollecte.trim();
    }

    public BigDecimal getCheckQty() {
        return checkQty;
    }

    public void setCheckQty(BigDecimal checkQty) {
        this.checkQty = checkQty;
    }

    public BigDecimal getInPriceTax() {
        return inPriceTax;
    }

    public void setInPriceTax(BigDecimal inPriceTax) {
        this.inPriceTax = inPriceTax;
    }

    public BigDecimal getInPriceNoTax() {
        return inPriceNoTax;
    }

    public void setInPriceNoTax(BigDecimal inPriceNoTax) {
        this.inPriceNoTax = inPriceNoTax;
    }

    public BigDecimal getInAmtTotal() {
        return inAmtTotal;
    }

    public void setInAmtTotal(BigDecimal inAmtTotal) {
        this.inAmtTotal = inAmtTotal;
    }

    public String getSettlementStatus() {
        return settlementStatus == null ? "" : settlementStatus.trim();
    }

    public void setSettlementStatus(String settlementStatus) {
        this.settlementStatus = settlementStatus == null ? null : settlementStatus.trim();
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
