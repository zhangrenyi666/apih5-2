package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxEqYyglJbb extends BasePojo {
    // 主键
    private String zxEqYyglJbbId;

    // 单位
    private String orgName;

    // 期次
    private String period;

    // 总台数（台）境内
    private BigDecimal count1;

    // 总台数（台）境外
    private BigDecimal count2;
    
    // 总台数（台）境外
    private BigDecimal countAll;

	// 总功率（kw）境内
    private BigDecimal power1;

    // 总功率（kw）境外
    private BigDecimal power2;
    
    // 总功率（kw）境外
    private BigDecimal powerAll;

    // 固定资产净值（万元）境内
    private BigDecimal leftValue1;

    // 固定资产净值（万元）境外
    private BigDecimal leftValue2;
    
    // 固定资产净值（万元）境外
    private BigDecimal leftValueAll;

    // 固定资产原值（万元）境内
    private BigDecimal orginalValue1;

    // 固定资产原值（万元）境外
    private BigDecimal orginalValue2;
    
    // 固定资产原值（万元）境外
    private BigDecimal orginalValueAll;

    // 台头名
    private BigDecimal reportTitle;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    private String orgID;

	private String periodType;
	
    public BigDecimal getCountAll() {
		return countAll;
	}

	public void setCountAll(BigDecimal countAll) {
		this.countAll = countAll;
	}

	public BigDecimal getPowerAll() {
		return powerAll;
	}

	public void setPowerAll(BigDecimal powerAll) {
		this.powerAll = powerAll;
	}

	public BigDecimal getLeftValueAll() {
		return leftValueAll;
	}

	public void setLeftValueAll(BigDecimal leftValueAll) {
		this.leftValueAll = leftValueAll;
	}

	public BigDecimal getOrginalValueAll() {
		return orginalValueAll;
	}

	public void setOrginalValueAll(BigDecimal orginalValueAll) {
		this.orginalValueAll = orginalValueAll;
	}
    
    public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}
	
    public String getZxEqYyglJbbId() {
        return zxEqYyglJbbId == null ? "" : zxEqYyglJbbId.trim();
    }

    public void setZxEqYyglJbbId(String zxEqYyglJbbId) {
        this.zxEqYyglJbbId = zxEqYyglJbbId == null ? null : zxEqYyglJbbId.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getPeriod() {
        return period == null ? "" : period.trim();
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public BigDecimal getCount1() {
        return count1;
    }

    public void setCount1(BigDecimal count1) {
        this.count1 = count1;
    }

    public BigDecimal getCount2() {
        return count2;
    }

    public void setCount2(BigDecimal count2) {
        this.count2 = count2;
    }

    public BigDecimal getPower1() {
        return power1;
    }

    public void setPower1(BigDecimal power1) {
        this.power1 = power1;
    }

    public BigDecimal getPower2() {
        return power2;
    }

    public void setPower2(BigDecimal power2) {
        this.power2 = power2;
    }

    public BigDecimal getLeftValue1() {
        return leftValue1;
    }

    public void setLeftValue1(BigDecimal leftValue1) {
        this.leftValue1 = leftValue1;
    }

    public BigDecimal getLeftValue2() {
        return leftValue2;
    }

    public void setLeftValue2(BigDecimal leftValue2) {
        this.leftValue2 = leftValue2;
    }

    public BigDecimal getOrginalValue1() {
        return orginalValue1;
    }

    public void setOrginalValue1(BigDecimal orginalValue1) {
        this.orginalValue1 = orginalValue1;
    }

    public BigDecimal getOrginalValue2() {
        return orginalValue2;
    }

    public void setOrginalValue2(BigDecimal orginalValue2) {
        this.orginalValue2 = orginalValue2;
    }

    public BigDecimal getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(BigDecimal reportTitle) {
        this.reportTitle = reportTitle;
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
