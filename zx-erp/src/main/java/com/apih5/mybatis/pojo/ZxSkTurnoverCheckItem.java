package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkTurnoverCheckItem extends BasePojo {
    // 主键
    private String id;

    // 单据ID
    private String billID;

    // 批次
    private String batchNo;

    // 物资ID
    private String resID;

    // 资源编号
    private String resCode;

    // 物资名称
    private String resName;

    // 规格型号
    private String spec;

    // 计量单位
    private String resUnit;

    // 转移单明细ID
    private String stockTransItemID;

    // 预收单ID
    private String stockTransID;

    // 预收单号
    private String stockTransBillNo;

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

    //旧的累计摊销
    private BigDecimal oldFeeSum;

    // 净值
    private BigDecimal remainAmt;

    // 累计摊销单价
    private BigDecimal feePrice;

    // 未冲数量
    private BigDecimal unCheckQty;

    // 税率(%)
    private String taxRate;

    // 是否抵扣
    private String isDeduct;

    // 购入不含税单价
    private BigDecimal inPriceNoTax;

    // 购入单价税额
    private BigDecimal inPriceTax;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public BigDecimal getOldQty() {
        return oldQty;
    }

    public void setOldQty(BigDecimal oldQty) {
        this.oldQty = oldQty;
    }

    public BigDecimal getOldFeeSum() {
        return oldFeeSum;
    }

    public void setOldFeeSum(BigDecimal oldFeeSum) {
        this.oldFeeSum = oldFeeSum;
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

    public String getStockTransItemID() {
        return stockTransItemID == null ? "" : stockTransItemID.trim();
    }

    public void setStockTransItemID(String stockTransItemID) {
        this.stockTransItemID = stockTransItemID == null ? null : stockTransItemID.trim();
    }

    public String getStockTransID() {
        return stockTransID == null ? "" : stockTransID.trim();
    }

    public void setStockTransID(String stockTransID) {
        this.stockTransID = stockTransID == null ? null : stockTransID.trim();
    }

    public String getStockTransBillNo() {
        return stockTransBillNo == null ? "" : stockTransBillNo.trim();
    }

    public void setStockTransBillNo(String stockTransBillNo) {
        this.stockTransBillNo = stockTransBillNo == null ? null : stockTransBillNo.trim();
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

    public BigDecimal getFeePrice() {
        return feePrice;
    }

    public void setFeePrice(BigDecimal feePrice) {
        this.feePrice = feePrice;
    }

    public BigDecimal getUnCheckQty() {
        return unCheckQty;
    }

    public void setUnCheckQty(BigDecimal unCheckQty) {
        this.unCheckQty = unCheckQty;
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

    public BigDecimal getInPriceNoTax() {
        return inPriceNoTax;
    }

    public void setInPriceNoTax(BigDecimal inPriceNoTax) {
        this.inPriceNoTax = inPriceNoTax;
    }

    public BigDecimal getInPriceTax() {
        return inPriceTax;
    }

    public void setInPriceTax(BigDecimal inPriceTax) {
        this.inPriceTax = inPriceTax;
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
