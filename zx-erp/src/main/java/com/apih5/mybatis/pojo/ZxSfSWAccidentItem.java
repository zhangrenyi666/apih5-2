package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfSWAccidentItem extends BasePojo {
    // 主键
    private String zxSfSWAccidentItemId;

    // 自沉
    private BigDecimal a13;

    // 事故件数（合计）
    private BigDecimal a3;

    // 总艘数（艘）
    private BigDecimal a17;

    // 浪损
    private BigDecimal a10;

    // 功率（千瓦）
    private BigDecimal a19;

    // 事故直接经济损失（万元）
    private BigDecimal a24;

    // 触礁
    private BigDecimal a8;

    // 触损
    private BigDecimal a9;

    // 碰撞
    private BigDecimal a6;

    // 机动船艘数
    private BigDecimal a20;

    // 主表ID
    private String swaID;

    // 火灾爆炸
    private BigDecimal a11;

    // 总吨数（吨）
    private BigDecimal a18;

    // 搁浅
    private BigDecimal a7;

    // 事故件数（重大）
    private BigDecimal a4;

    // 风灾
    private BigDecimal a12;

    // 船舶种类
    private String a1;

    // 事故件数（大）
    private BigDecimal a5;

    // 受伤
    private BigDecimal a15;

    // 备注
    private String notes;

    // 其它
    private BigDecimal a14;

    // 统计期内船舶总数（艘）
    private BigDecimal a2;

    // 非机动船总吨数
    private BigDecimal a23;

    // 机动船总吨数
    private BigDecimal a21;

    // 非机动船艘数
    private BigDecimal a22;

    // 死亡失踪
    private BigDecimal a16;

    // 排序
    private int sort=0;

    private String orgID;

    private String orgName;

    private Date period;

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

    public String getOrgID() {
        return orgID;
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }

    public String getZxSfSWAccidentItemId() {
        return zxSfSWAccidentItemId == null ? "" : zxSfSWAccidentItemId.trim();
    }

    public void setZxSfSWAccidentItemId(String zxSfSWAccidentItemId) {
        this.zxSfSWAccidentItemId = zxSfSWAccidentItemId == null ? null : zxSfSWAccidentItemId.trim();
    }

    public BigDecimal getA13() {
        return a13 == null ? new BigDecimal("0") : a13;
    }

    public void setA13(BigDecimal a13) {
        this.a13 = a13;
    }

    public BigDecimal getA3() {
        return a3 == null ? new BigDecimal("0") : a3;
    }

    public void setA3(BigDecimal a3) {
        this.a3 = a3;
    }

    public BigDecimal getA17() {
        return a17 == null ? new BigDecimal("0") : a17;
    }

    public void setA17(BigDecimal a17) {
        this.a17 = a17;
    }

    public BigDecimal getA10() {
        return a10 == null ? new BigDecimal("0") : a10;
    }

    public void setA10(BigDecimal a10) {
        this.a10 = a10;
    }

    public BigDecimal getA19() {
        return a19 == null ? new BigDecimal("0") : a19;
    }

    public void setA19(BigDecimal a19) {
        this.a19 = a19;
    }

    public BigDecimal getA24() {
        return a24 == null ? new BigDecimal("0") : a24;
    }

    public void setA24(BigDecimal a24) {
        this.a24 = a24;
    }

    public BigDecimal getA8() {
        return a8 == null ? new BigDecimal("0") : a8;
    }

    public void setA8(BigDecimal a8) {
        this.a8 = a8;
    }

    public BigDecimal getA9() {
        return a9 == null ? new BigDecimal("0") : a9;
    }

    public void setA9(BigDecimal a9) {
        this.a9 = a9;
    }

    public BigDecimal getA6() {
        return a6 == null ? new BigDecimal("0") : a6;
    }

    public void setA6(BigDecimal a6) {
        this.a6 = a6;
    }

    public BigDecimal getA20() {
        return a20 == null ? new BigDecimal("0") : a20;
    }

    public void setA20(BigDecimal a20) {
        this.a20 = a20;
    }

    public String getSwaID() {
        return swaID == null ? "" : swaID.trim();
    }

    public void setSwaID(String swaID) {
        this.swaID = swaID == null ? null : swaID.trim();
    }

    public BigDecimal getA11() {
        return a11 == null ? new BigDecimal("0") : a11;
    }

    public void setA11(BigDecimal a11) {
        this.a11 = a11;
    }

    public BigDecimal getA18() {
        return a18 == null ? new BigDecimal("0") : a18;
    }

    public void setA18(BigDecimal a18) {
        this.a18 = a18;
    }

    public BigDecimal getA7() {
        return a7 == null ? new BigDecimal("0") : a7;
    }

    public void setA7(BigDecimal a7) {
        this.a7 = a7;
    }

    public BigDecimal getA4() {
        return a4 == null ? new BigDecimal("0") : a4;
    }

    public void setA4(BigDecimal a4) {
        this.a4 = a4;
    }

    public BigDecimal getA12() {
        return a12 == null ? new BigDecimal("0") : a12;
    }

    public void setA12(BigDecimal a12) {
        this.a12 = a12;
    }

    public String getA1() {
        return a1 == null ? "" : a1.trim();
    }

    public void setA1(String a1) {
        this.a1 = a1 == null ? null : a1.trim();
    }

    public BigDecimal getA5() {
        return a5 == null ? new BigDecimal("0") : a5;
    }

    public void setA5(BigDecimal a5) {
        this.a5 = a5;
    }

    public BigDecimal getA15() {
        return a15 == null ? new BigDecimal("0") : a15;
    }

    public void setA15(BigDecimal a15) {
        this.a15 = a15;
    }

    public String getNotes() {
        return notes == null ? "" : notes.trim();
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public BigDecimal getA14() {
        return a14 == null ? new BigDecimal("0") : a14;
    }

    public void setA14(BigDecimal a14) {
        this.a14 = a14;
    }

    public BigDecimal getA2() {
        return a2 == null ? new BigDecimal("0") : a2;
    }

    public void setA2(BigDecimal a2) {
        this.a2 = a2;
    }

    public BigDecimal getA23() {
        return a23 == null ? new BigDecimal("0") : a23;
    }

    public void setA23(BigDecimal a23) {
        this.a23 = a23;
    }

    public BigDecimal getA21() {
        return a21 == null ? new BigDecimal("0") : a21;
    }

    public void setA21(BigDecimal a21) {
        this.a21 = a21;
    }

    public BigDecimal getA22() {
        return a22 == null ? new BigDecimal("0") : a22;
    }

    public void setA22(BigDecimal a22) {
        this.a22 = a22;
    }

    public BigDecimal getA16() {
        return a16 == null ? new BigDecimal("0") : a16;
    }

    public void setA16(BigDecimal a16) {
        this.a16 = a16;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

}
