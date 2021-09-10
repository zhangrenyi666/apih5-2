package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkColInvenItem extends BasePojo {
    // 主键
    private String id;

    // mainID
    private String mainID;

    // 物资ID
    private String resID;

    // 物资编码
    private String resCode;

    // 物资名称
    private String resName;

    // 规格型号
    private String spec;

    // 计量单位
    private String unit;

    // 盘点数量
    private BigDecimal qty;

    // 单价
    private BigDecimal price;

    // 金额
    private BigDecimal amt;

    // 物资类型
    private String resType;

    // 存放地点ID
    private String whOrgID;

    // 存放地点
    private String whOrgName;

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

    public String getMainID() {
        return mainID == null ? "" : mainID.trim();
    }

    public void setMainID(String mainID) {
        this.mainID = mainID == null ? null : mainID.trim();
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getResType() {
        return resType == null ? "" : resType.trim();
    }

    public void setResType(String resType) {
        this.resType = resType == null ? null : resType.trim();
    }

    public String getWhOrgID() {
        return whOrgID == null ? "" : whOrgID.trim();
    }

    public void setWhOrgID(String whOrgID) {
        this.whOrgID = whOrgID == null ? null : whOrgID.trim();
    }

    public String getWhOrgName() {
        return whOrgName == null ? "" : whOrgName.trim();
    }

    public void setWhOrgName(String whOrgName) {
        this.whOrgName = whOrgName == null ? null : whOrgName.trim();
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
