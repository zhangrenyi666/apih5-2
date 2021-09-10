package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfSourceOfDanger extends BasePojo {
    // 主键
    private String zxSfSourceOfDangerId;

    // 父id
    private String pId;

    // 类型
    private Integer type;

    // 名称
    private String name;

    // 作业条件危险性评价（D）
    private BigDecimal dee;

    // 作业条件危险性评价（C）
    private BigDecimal cee;

    // 作业条件危险性评价（B）
    private BigDecimal bee;

    // 作业条件危险性评价（L）
    private BigDecimal lee;

    // 风险等级
    private String riskLevel;

    // 状态
    private String status;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    private List<ZxSfSourceOfDanger> children;

    public String getZxSfSourceOfDangerId() {
        return zxSfSourceOfDangerId == null ? "" : zxSfSourceOfDangerId.trim();
    }

    public void setZxSfSourceOfDangerId(String zxSfSourceOfDangerId) {
        this.zxSfSourceOfDangerId = zxSfSourceOfDangerId == null ? null : zxSfSourceOfDangerId.trim();
    }

    public String getPId() {
        return pId == null ? "" : pId.trim();
    }

    public void setPId(String pId) {
        this.pId = pId == null ? null : pId.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name == null ? "" : name.trim();
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getDee() {
        return dee;
    }

    public void setDee(BigDecimal dee) {
        this.dee = dee;
    }

    public BigDecimal getCee() {
        return cee;
    }

    public void setCee(BigDecimal cee) {
        this.cee = cee;
    }

    public BigDecimal getBee() {
        return bee;
    }

    public void setBee(BigDecimal bee) {
        this.bee = bee;
    }

    public BigDecimal getLee() {
        return lee;
    }

    public void setLee(BigDecimal lee) {
        this.lee = lee;
    }

    public String getRiskLevel() {
        return riskLevel == null ? "" : riskLevel.trim();
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel == null ? null : riskLevel.trim();
    }

    public String getStatus() {
        return status == null ? "" : status.trim();
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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

    public List<ZxSfSourceOfDanger> getChildren() {
        return children;
    }

    public void setChildren(List<ZxSfSourceOfDanger> children) {
        this.children = children;
    }
}
