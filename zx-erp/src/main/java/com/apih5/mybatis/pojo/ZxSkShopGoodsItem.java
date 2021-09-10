package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkShopGoodsItem extends BasePojo {
    // 主键
    private String id;

    // mainID
    private String mainID;

    // 物资ID
    private String resID;

    // 物资编号
    private String resCode;

    // 物资名称
    private String resName;

    // 规格型号
    private String spec;

    // 计量单位
    private String unit;

    // 进货日期
    private Date inDate;

    // 进货数量
    private BigDecimal inQty;

    // 存放地点
    private String place;

    // 供应商名称ID
    private String customerID;

    // 供应商名称
    private String customerName;

    // 收料员
    private String receClerk;

    // 产地或品牌
    private String production;

    // 存放地点ID
    private String whOrgID;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    private String joinName;

    public String getJoinName() {
        return joinName;
    }

    public void setJoinName(String joinName) {
        this.joinName = joinName;
    }

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

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public BigDecimal getInQty() {
        return inQty;
    }

    public void setInQty(BigDecimal inQty) {
        this.inQty = inQty;
    }

    public String getPlace() {
        return place == null ? "" : place.trim();
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public String getCustomerID() {
        return customerID == null ? "" : customerID.trim();
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID == null ? null : customerID.trim();
    }

    public String getCustomerName() {
        return customerName == null ? "" : customerName.trim();
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getReceClerk() {
        return receClerk == null ? "" : receClerk.trim();
    }

    public void setReceClerk(String receClerk) {
        this.receClerk = receClerk == null ? null : receClerk.trim();
    }

    public String getProduction() {
        return production == null ? "" : production.trim();
    }

    public void setProduction(String production) {
        this.production = production == null ? null : production.trim();
    }

    public String getWhOrgID() {
        return whOrgID == null ? "" : whOrgID.trim();
    }

    public void setWhOrgID(String whOrgID) {
        this.whOrgID = whOrgID == null ? null : whOrgID.trim();
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
