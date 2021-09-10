package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkReturnsItem extends BasePojo {
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

    // 退货数量
    private BigDecimal returnQty;

    // 入库日期
    private Date inBusDate;

    // 库存量
    private BigDecimal stockQty;

    // 入库单价
    private BigDecimal stockPrice;

    // 退货金额
    private BigDecimal returnAmt;

    // 退货单价合计
    private BigDecimal returnPrice;

    // 退货原值
    private BigDecimal originAmt;

    // 累计摊销
    private BigDecimal feeSum;

    // 退货净值
    private BigDecimal remainAmt;

    // 退货单价税金
    private BigDecimal returnPriceTax;

    // 退货不含税单价
    private BigDecimal returnPriceNoTax;

    // 退货税额
    private BigDecimal returnAmtTax;

    // 结算状态
    private String settlementStatus;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public ZxSkReturnsItem() {
    }

    public ZxSkReturnsItem(String batchNo, BigDecimal returnQty, BigDecimal originAmt, BigDecimal remainAmt) {
        this.batchNo = batchNo;
        this.returnQty = returnQty;
        this.originAmt = originAmt;
        this.remainAmt = remainAmt;
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

    public BigDecimal getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(BigDecimal returnQty) {
        this.returnQty = returnQty;
    }

    public Date getInBusDate() {
        return inBusDate;
    }

    public void setInBusDate(Date inBusDate) {
        this.inBusDate = inBusDate;
    }

    public BigDecimal getStockQty() {
        return stockQty;
    }

    public void setStockQty(BigDecimal stockQty) {
        this.stockQty = stockQty;
    }

    public BigDecimal getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(BigDecimal stockPrice) {
        this.stockPrice = stockPrice;
    }

    public BigDecimal getReturnAmt() {
        return returnAmt;
    }

    public void setReturnAmt(BigDecimal returnAmt) {
        this.returnAmt = returnAmt;
    }

    public BigDecimal getReturnPrice() {
        return returnPrice;
    }

    public void setReturnPrice(BigDecimal returnPrice) {
        this.returnPrice = returnPrice;
    }

    public BigDecimal getOriginAmt() {
        return originAmt;
    }

    public void setOriginAmt(BigDecimal originAmt) {
        this.originAmt = originAmt;
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

    public BigDecimal getReturnPriceTax() {
        return returnPriceTax;
    }

    public void setReturnPriceTax(BigDecimal returnPriceTax) {
        this.returnPriceTax = returnPriceTax;
    }

    public BigDecimal getReturnPriceNoTax() {
        return returnPriceNoTax;
    }

    public void setReturnPriceNoTax(BigDecimal returnPriceNoTax) {
        this.returnPriceNoTax = returnPriceNoTax;
    }

    public BigDecimal getReturnAmtTax() {
        return returnAmtTax;
    }

    public void setReturnAmtTax(BigDecimal returnAmtTax) {
        this.returnAmtTax = returnAmtTax;
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
