package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkMakeItem extends BasePojo {
    // 主键
    private String id;

    // 盘点单ID
    private String makeInvID;

    // 库存ID
    private String stockID;

    // 物资ID
    private String resID;

    // 账面数量
    private BigDecimal accountsQty;

    // 账面金额
    private BigDecimal accountsAmt;

    // 盘点数量
    private BigDecimal makeInvQty;

    // 盘点金额
    private BigDecimal makeInvAmt;

    // 计算金额
    private BigDecimal countAmt;

    // 盈亏数量
    private BigDecimal diffQty;

    // 盈亏金额
    private BigDecimal diffAmt;

    // 批号
    private String batchNo;

    // 物资编码
    private String resCode;

    // 物资名称
    private String resName;

    // 规格型号
    private String spec;

    // 单位
    private String resUnit;

    // 单价
    private BigDecimal stockPrice;

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

    public String getMakeInvID() {
        return makeInvID == null ? "" : makeInvID.trim();
    }

    public void setMakeInvID(String makeInvID) {
        this.makeInvID = makeInvID == null ? null : makeInvID.trim();
    }

    public String getStockID() {
        return stockID == null ? "" : stockID.trim();
    }

    public void setStockID(String stockID) {
        this.stockID = stockID == null ? null : stockID.trim();
    }

    public String getResID() {
        return resID == null ? "" : resID.trim();
    }

    public void setResID(String resID) {
        this.resID = resID == null ? null : resID.trim();
    }

    public BigDecimal getAccountsQty() {
        return accountsQty;
    }

    public void setAccountsQty(BigDecimal accountsQty) {
        this.accountsQty = accountsQty;
    }

    public BigDecimal getAccountsAmt() {
        return accountsAmt;
    }

    public void setAccountsAmt(BigDecimal accountsAmt) {
        this.accountsAmt = accountsAmt;
    }

    public BigDecimal getMakeInvQty() {
        return makeInvQty;
    }

    public void setMakeInvQty(BigDecimal makeInvQty) {
        this.makeInvQty = makeInvQty;
    }

    public BigDecimal getMakeInvAmt() {
        return makeInvAmt;
    }

    public void setMakeInvAmt(BigDecimal makeInvAmt) {
        this.makeInvAmt = makeInvAmt;
    }

    public BigDecimal getCountAmt() {
        return countAmt;
    }

    public void setCountAmt(BigDecimal countAmt) {
        this.countAmt = countAmt;
    }

    public BigDecimal getDiffQty() {
        return diffQty;
    }

    public void setDiffQty(BigDecimal diffQty) {
        this.diffQty = diffQty;
    }

    public BigDecimal getDiffAmt() {
        return diffAmt;
    }

    public void setDiffAmt(BigDecimal diffAmt) {
        this.diffAmt = diffAmt;
    }

    public String getBatchNo() {
        return batchNo == null ? "" : batchNo.trim();
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
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

    public BigDecimal getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(BigDecimal stockPrice) {
        this.stockPrice = stockPrice;
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
