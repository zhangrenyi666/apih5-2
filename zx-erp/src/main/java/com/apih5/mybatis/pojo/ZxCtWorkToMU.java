package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCtWorkToMU extends BasePojo {
    // 主键
    private String id;

    // 项目机构
    private String orgID;

    // 管理单元
    private String muID;

    // 清单书
    private String workBookID;

    // 工程量清单
    private String workID;

    // 当前清单已经挂到管理单元上的总接量
    private BigDecimal totalLinkQuantity;

    // 挂接数量
    private BigDecimal linkQuantity;

    // 挂接单价
    private BigDecimal linkPrice;

    // 原挂接数量
    private BigDecimal oriQuantity;

    // 原挂接单价
    private BigDecimal oriPrice;

    // 预计变更后数量
    private BigDecimal expectChangeQty;

    // 预计变更后单价
    private BigDecimal expectChangePrice;

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

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getMuID() {
        return muID == null ? "" : muID.trim();
    }

    public void setMuID(String muID) {
        this.muID = muID == null ? null : muID.trim();
    }

    public String getWorkBookID() {
        return workBookID == null ? "" : workBookID.trim();
    }

    public void setWorkBookID(String workBookID) {
        this.workBookID = workBookID == null ? null : workBookID.trim();
    }

    public String getWorkID() {
        return workID == null ? "" : workID.trim();
    }

    public void setWorkID(String workID) {
        this.workID = workID == null ? null : workID.trim();
    }

    public BigDecimal getTotalLinkQuantity() {
        return totalLinkQuantity;
    }

    public void setTotalLinkQuantity(BigDecimal totalLinkQuantity) {
        this.totalLinkQuantity = totalLinkQuantity;
    }

    public BigDecimal getLinkQuantity() {
        return linkQuantity;
    }

    public void setLinkQuantity(BigDecimal linkQuantity) {
        this.linkQuantity = linkQuantity;
    }

    public BigDecimal getLinkPrice() {
        return linkPrice;
    }

    public void setLinkPrice(BigDecimal linkPrice) {
        this.linkPrice = linkPrice;
    }

    public BigDecimal getOriQuantity() {
        return oriQuantity;
    }

    public void setOriQuantity(BigDecimal oriQuantity) {
        this.oriQuantity = oriQuantity;
    }

    public BigDecimal getOriPrice() {
        return oriPrice;
    }

    public void setOriPrice(BigDecimal oriPrice) {
        this.oriPrice = oriPrice;
    }

    public BigDecimal getExpectChangeQty() {
        return expectChangeQty;
    }

    public void setExpectChangeQty(BigDecimal expectChangeQty) {
        this.expectChangeQty = expectChangeQty;
    }

    public BigDecimal getExpectChangePrice() {
        return expectChangePrice;
    }

    public void setExpectChangePrice(BigDecimal expectChangePrice) {
        this.expectChangePrice = expectChangePrice;
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
