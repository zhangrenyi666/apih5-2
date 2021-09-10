package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxEqAbcMachinery extends BasePojo {
    // 主键
    private String zxEqAbcMachineryId;

    // 单位名称
    private String orgname;

    // 期末机械台数合计
    private BigDecimal numsum;

    // 期末机械台数A类
    private BigDecimal num1;

    // 期末机械台数B类
    private BigDecimal num2;

    // 期末机械台数C类
    private BigDecimal num3;

    // 期末机械台数D类
    private BigDecimal num4;

    // 期末原值（万元）合计
    private BigDecimal yamtsum;

    // 期末原值（万元）A类
    private BigDecimal yamt1;

    // 期末原值（万元）B类
    private BigDecimal yamt2;

    // 期末原值（万元）C类
    private BigDecimal yamt3;

    // 期末原值（万元）D类
    private BigDecimal yamt4;

    // 期末净值（万元）合计
    private BigDecimal jamtsum;

    // 期末净值（万元）A类
    private BigDecimal jamt1;

    // 期末净值（万元）B类
    private BigDecimal jamt2;

    // 期末净值（万元）C类
    private BigDecimal jamt3;

    // 期末净值（万元）D类
    private BigDecimal jamt4;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    private Date period;

	private String orgID;
	
	public Date getPeriod() {
		return period;
	}

	public void setPeriod(Date period) {
		this.period = period;
	}

	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

    public String getZxEqAbcMachineryId() {
        return zxEqAbcMachineryId == null ? "" : zxEqAbcMachineryId.trim();
    }

    public void setZxEqAbcMachineryId(String zxEqAbcMachineryId) {
        this.zxEqAbcMachineryId = zxEqAbcMachineryId == null ? null : zxEqAbcMachineryId.trim();
    }

    public String getOrgname() {
        return orgname == null ? "" : orgname.trim();
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname == null ? null : orgname.trim();
    }

    public BigDecimal getNumsum() {
        return numsum;
    }

    public void setNumsum(BigDecimal numsum) {
        this.numsum = numsum;
    }

    public BigDecimal getNum1() {
        return num1;
    }

    public void setNum1(BigDecimal num1) {
        this.num1 = num1;
    }

    public BigDecimal getNum2() {
        return num2;
    }

    public void setNum2(BigDecimal num2) {
        this.num2 = num2;
    }

    public BigDecimal getNum3() {
        return num3;
    }

    public void setNum3(BigDecimal num3) {
        this.num3 = num3;
    }

    public BigDecimal getNum4() {
        return num4;
    }

    public void setNum4(BigDecimal num4) {
        this.num4 = num4;
    }

    public BigDecimal getYamtsum() {
        return yamtsum;
    }

    public void setYamtsum(BigDecimal yamtsum) {
        this.yamtsum = yamtsum;
    }

    public BigDecimal getYamt1() {
        return yamt1;
    }

    public void setYamt1(BigDecimal yamt1) {
        this.yamt1 = yamt1;
    }

    public BigDecimal getYamt2() {
        return yamt2;
    }

    public void setYamt2(BigDecimal yamt2) {
        this.yamt2 = yamt2;
    }

    public BigDecimal getYamt3() {
        return yamt3;
    }

    public void setYamt3(BigDecimal yamt3) {
        this.yamt3 = yamt3;
    }

    public BigDecimal getYamt4() {
        return yamt4;
    }

    public void setYamt4(BigDecimal yamt4) {
        this.yamt4 = yamt4;
    }

    public BigDecimal getJamtsum() {
        return jamtsum;
    }

    public void setJamtsum(BigDecimal jamtsum) {
        this.jamtsum = jamtsum;
    }

    public BigDecimal getJamt1() {
        return jamt1;
    }

    public void setJamt1(BigDecimal jamt1) {
        this.jamt1 = jamt1;
    }

    public BigDecimal getJamt2() {
        return jamt2;
    }

    public void setJamt2(BigDecimal jamt2) {
        this.jamt2 = jamt2;
    }

    public BigDecimal getJamt3() {
        return jamt3;
    }

    public void setJamt3(BigDecimal jamt3) {
        this.jamt3 = jamt3;
    }

    public BigDecimal getJamt4() {
        return jamt4;
    }

    public void setJamt4(BigDecimal jamt4) {
        this.jamt4 = jamt4;
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
