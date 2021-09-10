package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkWornOutItem extends BasePojo {
    // 主键
    private String zxSkWornOutItemId;

    // 单据
    private String billID;

    // 物资编码ID
    private String resID;

    // 物资名称
    private String resName;

    // 物资编码
    private String resCode;

    // 单位
    private String unit;

    // 数量
    private BigDecimal qty;

    // 总金额(万元)
    private BigDecimal amt;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getZxSkWornOutItemId() {
        return zxSkWornOutItemId == null ? "" : zxSkWornOutItemId.trim();
    }

    public void setZxSkWornOutItemId(String zxSkWornOutItemId) {
        this.zxSkWornOutItemId = zxSkWornOutItemId == null ? null : zxSkWornOutItemId.trim();
    }

    public String getBillID() {
        return billID == null ? "" : billID.trim();
    }

    public void setBillID(String billID) {
        this.billID = billID == null ? null : billID.trim();
    }

    public String getResID() {
        return resID == null ? "" : resID.trim();
    }

    public void setResID(String resID) {
        this.resID = resID == null ? null : resID.trim();
    }

    public String getResName() {
        return resName == null ? "" : resName.trim();
    }

    public void setResName(String resName) {
        this.resName = resName == null ? null : resName.trim();
    }

    public String getResCode() {
        return resCode == null ? "" : resCode.trim();
    }

    public void setResCode(String resCode) {
        this.resCode = resCode == null ? null : resCode.trim();
    }

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
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
