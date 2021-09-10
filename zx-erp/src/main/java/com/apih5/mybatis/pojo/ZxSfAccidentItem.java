package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfAccidentItem extends BasePojo {
    // 主键
    private String zxSfAccidentItemId;

    // 事故类别
    private String a6;

    // 轻伤
    private BigDecimal a10;

    // 事故单位ID
    private String projId;

    // 事故原因
    private String a5;

    // 非企业人员死亡
    private BigDecimal a11;

    // 事故结案批复单位
    private String a16;

    // 主表ID
    private String accidentId;

    // 操作工作日（工日）
    private BigDecimal a15;

    // 重伤
    private BigDecimal a9;

    // 工程分类及等级、建设类型
    private String a4;

    // 非企业人员轻伤
    private BigDecimal a13;

    // 伤亡人员姓名
    private String a7;

    // 非企业人员重伤
    private BigDecimal a12;

    // 事故单位名称
    private String a3;

    // 直接经济损失（万元）
    private BigDecimal a14;

    // 备注
    private String notes;

    // 死亡
    private BigDecimal a8;

    // 发生事故时间
    private Date a1;

    // 事故地点
    private String a2;

    // 排序
    private int sort=0;

    private String companyId;

    private String companyName;

    private String orgName;

    private Date year;

    private String month;

    private String xuhao;

    private BigDecimal deadLv;
    private BigDecimal hitlv;
    private BigDecimal monthLv;

    private BigDecimal heji;

    private BigDecimal avgEmpNum;

    private String orgId;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public BigDecimal getHeji() {
        return heji;
    }

    public void setHeji(BigDecimal heji) {
        this.heji = heji;
    }

    public BigDecimal getAvgEmpNum() {
        return avgEmpNum;
    }

    public void setAvgEmpNum(BigDecimal avgEmpNum) {
        this.avgEmpNum = avgEmpNum;
    }

    public BigDecimal getDeadLv() {
        return deadLv;
    }

    public void setDeadLv(BigDecimal deadLv) {
        this.deadLv = deadLv;
    }

    public BigDecimal getHitlv() {
        return hitlv;
    }

    public void setHitlv(BigDecimal hitlv) {
        this.hitlv = hitlv;
    }

    public BigDecimal getMonthLv() {
        return monthLv;
    }

    public void setMonthLv(BigDecimal monthLv) {
        this.monthLv = monthLv;
    }

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

    public String getXuhao() {
        return xuhao;
    }

    public void setXuhao(String xuhao) {
        this.xuhao = xuhao;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getZxSfAccidentItemId() {
        return zxSfAccidentItemId == null ? "" : zxSfAccidentItemId.trim();
    }

    public void setZxSfAccidentItemId(String zxSfAccidentItemId) {
        this.zxSfAccidentItemId = zxSfAccidentItemId == null ? null : zxSfAccidentItemId.trim();
    }

    public String getA6() {
        return a6 == null ? "" : a6.trim();
    }

    public void setA6(String a6) {
        this.a6 = a6 == null ? null : a6.trim();
    }

    public BigDecimal getA10() {
        return a10;
    }

    public void setA10(BigDecimal a10) {
        this.a10 = a10;
    }

    public String getProjId() {
        return projId == null ? "" : projId.trim();
    }

    public void setProjId(String projId) {
        this.projId = projId == null ? null : projId.trim();
    }

    public String getA5() {
        return a5 == null ? "" : a5.trim();
    }

    public void setA5(String a5) {
        this.a5 = a5 == null ? null : a5.trim();
    }

    public BigDecimal getA11() {
        return a11;
    }

    public void setA11(BigDecimal a11) {
        this.a11 = a11;
    }

    public String getA16() {
        return a16 == null ? "" : a16.trim();
    }

    public void setA16(String a16) {
        this.a16 = a16 == null ? null : a16.trim();
    }

    public String getAccidentId() {
        return accidentId == null ? "" : accidentId.trim();
    }

    public void setAccidentId(String accidentId) {
        this.accidentId = accidentId == null ? null : accidentId.trim();
    }

    public BigDecimal getA15() {
        return a15;
    }

    public void setA15(BigDecimal a15) {
        this.a15 = a15;
    }

    public BigDecimal getA9() {
        return a9;
    }

    public void setA9(BigDecimal a9) {
        this.a9 = a9;
    }

    public String getA4() {
        return a4 == null ? "" : a4.trim();
    }

    public void setA4(String a4) {
        this.a4 = a4 == null ? null : a4.trim();
    }

    public BigDecimal getA13() {
        return a13;
    }

    public void setA13(BigDecimal a13) {
        this.a13 = a13;
    }

    public String getA7() {
        return a7 == null ? "" : a7.trim();
    }

    public void setA7(String a7) {
        this.a7 = a7 == null ? null : a7.trim();
    }

    public BigDecimal getA12() {
        return a12;
    }

    public void setA12(BigDecimal a12) {
        this.a12 = a12;
    }

    public String getA3() {
        return a3 == null ? "" : a3.trim();
    }

    public void setA3(String a3) {
        this.a3 = a3 == null ? null : a3.trim();
    }

    public BigDecimal getA14() {
        return a14;
    }

    public void setA14(BigDecimal a14) {
        this.a14 = a14;
    }

    public String getNotes() {
        return notes == null ? "" : notes.trim();
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public BigDecimal getA8() {
        return a8;
    }

    public void setA8(BigDecimal a8) {
        this.a8 = a8;
    }

    public Date getA1() {
        return a1;
    }

    public void setA1(Date a1) {
        this.a1 = a1;
    }

    public String getA2() {
        return a2 == null ? "" : a2.trim();
    }

    public void setA2(String a2) {
        this.a2 = a2 == null ? null : a2.trim();
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

}
