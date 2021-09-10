package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxEqToCurrentAnalyseQueryPageChart extends BasePojo {
    // 主键
    private String zxEqToCurrentAnalyseQueryPageChartId;

    // 年
    private String year;

    // 原值
    private BigDecimal amt;

    // 部门ID
    private String departmentId;

    // 部门路径
    private String departmentPath;

    // 部门名称
    private String departmentName;

    // 设备分类
    private String resCatalogID;

    // ABC分类
    private String abcType;

    // 年
    private Date currentYear;

    // 数量1（台）
    private BigDecimal count1;

    // 原值1（万元）
    private BigDecimal orginalValue1;

    // 数量2（台）
    private BigDecimal count2;

    // 原值（万元）
    private BigDecimal orginalValue2;

    // 数量3（台）
    private BigDecimal count3;

    // 原值3（万元）
    private BigDecimal orginalValue3;

    // 数量4（台）
    private BigDecimal count4;

    // 原值4（万元）
    private BigDecimal orginalValue4;

    // 数量5（台）
    private BigDecimal count5;

    // 原值5（万元）
    private BigDecimal orginalValue5;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    private String orgID;

	private String resCatalogBsid;
	
	private String parentID;
	
    public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	public String getResCatalogBsid() {
		return resCatalogBsid;
	}

	public void setResCatalogBsid(String resCatalogBsid) {
		this.resCatalogBsid = resCatalogBsid;
	}

    public String getZxEqToCurrentAnalyseQueryPageChartId() {
        return zxEqToCurrentAnalyseQueryPageChartId == null ? "" : zxEqToCurrentAnalyseQueryPageChartId.trim();
    }

    public void setZxEqToCurrentAnalyseQueryPageChartId(String zxEqToCurrentAnalyseQueryPageChartId) {
        this.zxEqToCurrentAnalyseQueryPageChartId = zxEqToCurrentAnalyseQueryPageChartId == null ? null : zxEqToCurrentAnalyseQueryPageChartId.trim();
    }

    public String getYear() {
        return year == null ? "" : year.trim();
    }

    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getDepartmentId() {
        return departmentId == null ? "" : departmentId.trim();
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }

    public String getDepartmentPath() {
        return departmentPath == null ? "" : departmentPath.trim();
    }

    public void setDepartmentPath(String departmentPath) {
        this.departmentPath = departmentPath == null ? null : departmentPath.trim();
    }

    public String getDepartmentName() {
        return departmentName == null ? "" : departmentName.trim();
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public String getResCatalogID() {
        return resCatalogID == null ? "" : resCatalogID.trim();
    }

    public void setResCatalogID(String resCatalogID) {
        this.resCatalogID = resCatalogID == null ? null : resCatalogID.trim();
    }

    public String getAbcType() {
        return abcType == null ? "" : abcType.trim();
    }

    public void setAbcType(String abcType) {
        this.abcType = abcType == null ? null : abcType.trim();
    }

    public Date getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(Date currentYear) {
        this.currentYear = currentYear;
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

    public BigDecimal getCount5() {
        return count5;
    }

    public void setCount5(BigDecimal count5) {
        this.count5 = count5;
    }

    public BigDecimal getOrginalValue5() {
        return orginalValue5;
    }

    public void setOrginalValue5(BigDecimal orginalValue5) {
        this.orginalValue5 = orginalValue5;
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
