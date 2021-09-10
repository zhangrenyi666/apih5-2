package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxEqEquipIntegratedQuery extends BasePojo {
    // 主键
    private String zxEqEquipIntegratedQueryId;

    // 机构名称
    private String departmentName;

    // 机构ID
    private String departmentId;

    // 排顺
    private String sort;

    // A类台数
    private BigDecimal count1;

    // A类原值
    private BigDecimal orginalValue1;

    // A类净值
    private BigDecimal leftValue1;

    // B类台数
    private BigDecimal count2;

    // B类原值
    private BigDecimal orginalValue2;

    // B类净值
    private BigDecimal leftValue2;

    // C类台数
    private BigDecimal count3;

    // C类原值
    private BigDecimal orginalValue3;

    // C类净值
    private BigDecimal leftValue3;

    // D类台数
    private BigDecimal count4;

    // D类原值
    private BigDecimal orginalValue4;

    // D类净值
    private BigDecimal leftValue4;

    // 备注
    private String remarks;
    
    private String parentID;

	private String periodYear;
    
    public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public String getPeriodYear() {
		return periodYear;
	}

	public void setPeriodYear(String periodYear) {
		this.periodYear = periodYear;
	}

    public String getZxEqEquipIntegratedQueryId() {
        return zxEqEquipIntegratedQueryId == null ? "" : zxEqEquipIntegratedQueryId.trim();
    }

    public void setZxEqEquipIntegratedQueryId(String zxEqEquipIntegratedQueryId) {
        this.zxEqEquipIntegratedQueryId = zxEqEquipIntegratedQueryId == null ? null : zxEqEquipIntegratedQueryId.trim();
    }

    public String getDepartmentName() {
        return departmentName == null ? "" : departmentName.trim();
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public String getDepartmentId() {
        return departmentId == null ? "" : departmentId.trim();
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }

    public String getSort() {
        return sort == null ? "" : sort.trim();
    }

    public void setSort(String sort) {
        this.sort = sort == null ? null : sort.trim();
    }

    public BigDecimal getCount1() {
        return count1;
    }

    public void setCount1(BigDecimal count1) {
        this.count1 = count1;
    }

    public BigDecimal getOrginalValue1() {
        return orginalValue1;
    }

    public void setOrginalValue1(BigDecimal orginalValue1) {
        this.orginalValue1 = orginalValue1;
    }

    public BigDecimal getLeftValue1() {
        return leftValue1;
    }

    public void setLeftValue1(BigDecimal leftValue1) {
        this.leftValue1 = leftValue1;
    }

    public BigDecimal getCount2() {
        return count2;
    }

    public void setCount2(BigDecimal count2) {
        this.count2 = count2;
    }

    public BigDecimal getOrginalValue2() {
        return orginalValue2;
    }

    public void setOrginalValue2(BigDecimal orginalValue2) {
        this.orginalValue2 = orginalValue2;
    }

    public BigDecimal getLeftValue2() {
        return leftValue2;
    }

    public void setLeftValue2(BigDecimal leftValue2) {
        this.leftValue2 = leftValue2;
    }

    public BigDecimal getCount3() {
        return count3;
    }

    public void setCount3(BigDecimal count3) {
        this.count3 = count3;
    }

    public BigDecimal getOrginalValue3() {
        return orginalValue3;
    }

    public void setOrginalValue3(BigDecimal orginalValue3) {
        this.orginalValue3 = orginalValue3;
    }

    public BigDecimal getLeftValue3() {
        return leftValue3;
    }

    public void setLeftValue3(BigDecimal leftValue3) {
        this.leftValue3 = leftValue3;
    }

    public BigDecimal getCount4() {
        return count4;
    }

    public void setCount4(BigDecimal count4) {
        this.count4 = count4;
    }

    public BigDecimal getOrginalValue4() {
        return orginalValue4;
    }

    public void setOrginalValue4(BigDecimal orginalValue4) {
        this.orginalValue4 = orginalValue4;
    }

    public BigDecimal getLeftValue4() {
        return leftValue4;
    }

    public void setLeftValue4(BigDecimal leftValue4) {
        this.leftValue4 = leftValue4;
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

}
