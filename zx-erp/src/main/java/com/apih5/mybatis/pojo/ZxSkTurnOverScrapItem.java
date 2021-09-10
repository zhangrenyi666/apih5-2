package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkTurnOverScrapItem extends BasePojo {
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

    // 购入数量
    private BigDecimal buyQty;

    // 购入单价
    private BigDecimal buyPrice;

    // 购入金额
    private BigDecimal buyAmt;

    // 净值
    private BigDecimal currentAmt;

    // 处理数量
    private BigDecimal discardQty;

    // 处理单价
    private BigDecimal discardPrice;

    // 处理金额
    private BigDecimal discardAmt;

    // 入库日期
    private Date inBusDate;

    // 库存数量
    private BigDecimal stockQty;

    // 原值
    private BigDecimal originalAmt;

    // 退货原值
    private BigDecimal thisOriginalAmt;

    // 退货净值
    private BigDecimal thisRemainAmt;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

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

    public BigDecimal getBuyQty() {
        return buyQty;
    }

    public void setBuyQty(BigDecimal buyQty) {
        this.buyQty = buyQty;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BigDecimal getBuyAmt() {
        return buyAmt;
    }

    public void setBuyAmt(BigDecimal buyAmt) {
        this.buyAmt = buyAmt;
    }

    public BigDecimal getCurrentAmt() {
        return currentAmt;
    }

    public void setCurrentAmt(BigDecimal currentAmt) {
        this.currentAmt = currentAmt;
    }

    public BigDecimal getDiscardQty() {
        return discardQty;
    }

    public void setDiscardQty(BigDecimal discardQty) {
        this.discardQty = discardQty;
    }

    public BigDecimal getDiscardPrice() {
        return discardPrice;
    }

    public void setDiscardPrice(BigDecimal discardPrice) {
        this.discardPrice = discardPrice;
    }

    public BigDecimal getDiscardAmt() {
        return discardAmt;
    }

    public void setDiscardAmt(BigDecimal discardAmt) {
        this.discardAmt = discardAmt;
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

    public BigDecimal getOriginalAmt() {
        return originalAmt;
    }

    public void setOriginalAmt(BigDecimal originalAmt) {
        this.originalAmt = originalAmt;
    }

    public BigDecimal getThisOriginalAmt() {
        return thisOriginalAmt;
    }

    public void setThisOriginalAmt(BigDecimal thisOriginalAmt) {
        this.thisOriginalAmt = thisOriginalAmt;
    }

    public BigDecimal getThisRemainAmt() {
        return thisRemainAmt;
    }

    public void setThisRemainAmt(BigDecimal thisRemainAmt) {
        this.thisRemainAmt = thisRemainAmt;
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
