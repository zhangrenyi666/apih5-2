package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfEAccidentItem extends BasePojo {
    // 主键
    private String zxSfEAccidentItemId;

    // 月负伤频率（‰）
    private String a10;

    // 备注
    private String notes;

    // 直接经济损失（万元）
    private BigDecimal a6;

    // 单位
    private String orgName;

    // 死亡
    private BigDecimal a3;

    // 机构ID
    private String orgId;

    // 主表ID
    private String eaId;

    // 重伤
    private BigDecimal a4;

    // 重伤率（‰）
    private String a9;

    // 死亡率（‰）
    private String a8;

    // 伤亡人数合计
    private BigDecimal a2;

    // 操作工作日（工日）
    private BigDecimal a7;

    // 轻伤
    private BigDecimal a5;

    // 职工平均人数
    private BigDecimal a1;

    // 排序
    private int sort=0;

    private  Date year;
    private String companyId;
    private String companyName;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public String getZxSfEAccidentItemId() {
        return zxSfEAccidentItemId == null ? "" : zxSfEAccidentItemId.trim();
    }

    public void setZxSfEAccidentItemId(String zxSfEAccidentItemId) {
        this.zxSfEAccidentItemId = zxSfEAccidentItemId == null ? null : zxSfEAccidentItemId.trim();
    }

    public String getA10() {
        return a10 == null ? "" : a10.trim();
    }

    public void setA10(String a10) {
        this.a10 = a10 == null ? null : a10.trim();
    }

    public String getNotes() {
        return notes == null ? "" : notes.trim();
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public BigDecimal getA6() {
        return a6== null ? new BigDecimal("0") : a6;
    }

    public void setA6(BigDecimal a6) {
        this.a6 = a6;
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public BigDecimal getA3() {
        return a3== null ? new BigDecimal("0") : a3;
    }

    public void setA3(BigDecimal a3) {
        this.a3 = a3;
    }

    public String getOrgId() {
        return orgId == null ? "" : orgId.trim();
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getEaId() {
        return eaId == null ? "" : eaId.trim();
    }

    public void setEaId(String eaId) {
        this.eaId = eaId == null ? null : eaId.trim();
    }

    public BigDecimal getA4() {
        return a4== null ? new BigDecimal("0") : a4;
    }

    public void setA4(BigDecimal a4) {
        this.a4 = a4;
    }

    public String getA9() {
        return a9 == null ? "" : a9.trim();
    }

    public void setA9(String a9) {
        this.a9 = a9 == null ? null : a9.trim();
    }

    public String getA8() {
        return a8 == null ? "" : a8.trim();
    }

    public void setA8(String a8) {
        this.a8 = a8 == null ? null : a8.trim();
    }

    public BigDecimal getA2() {
        return a2== null ? new BigDecimal("0") : a2;
    }

    public void setA2(BigDecimal a2) {
        this.a2 = a2;
    }

    public BigDecimal getA7() {
        return a7== null ? new BigDecimal("0") : a7;
    }

    public void setA7(BigDecimal a7) {
        this.a7 = a7;
    }

    public BigDecimal getA5() {
       return a5== null ? new BigDecimal("0") : a5;
    }

    public void setA5(BigDecimal a5) {
        this.a5 = a5;
    }

    public BigDecimal getA1() {
        return a1== null ? new BigDecimal("0") : a1;
    }

    public void setA1(BigDecimal a1) {
        this.a1 = a1;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

}
