package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfCost extends BasePojo {
    // 主键
    private String zxSfCostId;

    // 公司名称
    private String companyName;

    // 公司id
    private String companyId;

    // 项目ID
    private String orgID;

    // 项目名称
    private String orgName;

    // 在建合同额
    private BigDecimal zjCost;

    // 安全费用总预计（万元）
    private BigDecimal zjSfCost;

    // 已完合同额（万元）
    private BigDecimal endCost;

    // 已完合同额安全费用预计（万元）
    private BigDecimal endSfCost;

    // 实际支出
    private BigDecimal cost;

    // 季度
    private String season;

    // 工程类别
    private String projType;

    public String getZxSfCostId() {
        return zxSfCostId == null ? "" : zxSfCostId.trim();
    }

    public void setZxSfCostId(String zxSfCostId) {
        this.zxSfCostId = zxSfCostId == null ? null : zxSfCostId.trim();
    }

    public String getCompanyName() {
        return companyName == null ? "" : companyName.trim();
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getCompanyId() {
        return companyId == null ? "" : companyId.trim();
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
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

    public BigDecimal getZjCost() {
        return zjCost;
    }

    public void setZjCost(BigDecimal zjCost) {
        this.zjCost = zjCost;
    }

    public BigDecimal getZjSfCost() {
        return zjSfCost;
    }

    public void setZjSfCost(BigDecimal zjSfCost) {
        this.zjSfCost = zjSfCost;
    }

    public BigDecimal getEndCost() {
        return endCost;
    }

    public void setEndCost(BigDecimal endCost) {
        this.endCost = endCost;
    }

    public BigDecimal getEndSfCost() {
        return endSfCost;
    }

    public void setEndSfCost(BigDecimal endSfCost) {
        this.endSfCost = endSfCost;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getSeason() {
        return season == null ? "" : season.trim();
    }

    public void setSeason(String season) {
        this.season = season == null ? null : season.trim();
    }

    public String getProjType() {
        return projType == null ? "" : projType.trim();
    }

    public void setProjType(String projType) {
        this.projType = projType == null ? null : projType.trim();
    }

}
