package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkLimitPriceRep extends BasePojo {
    // 主键
    private String zxSkLimitPriceRepId;

    // 期次
    private String period;

    //期次时间戳
    private Date periodTime;

    // 项目名称
    private String projectName;

    // 物资大类
    private String resourceName;

    //物资id
    private String workId;

    // 物资名称
    private String workName;
    
    // 物资编号
    private String resourceNo;

	// 规格型号
    private String spec;

    // 计量单位
    private String unit;

    // 所在省份
    private String province;

    // 限价
    private BigDecimal price;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    private String projectId;
    

	private String resTypeID;
    
    // 所属公司ID
    private String companyId;

	// 所属公司名称
    private String companyName;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public Date getPeriodTime() {
        return periodTime;
    }

    public void setPeriodTime(Date periodTime) {
        this.periodTime = periodTime;
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
	
    public String getResourceNo() {
		return resourceNo;
	}

	public void setResourceNo(String resourceNo) {
		this.resourceNo = resourceNo;
	}

    public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getResTypeID() {
		return resTypeID;
	}

	public void setResTypeID(String resTypeID) {
		this.resTypeID = resTypeID;
	}
	
    public String getZxSkLimitPriceRepId() {
        return zxSkLimitPriceRepId == null ? "" : zxSkLimitPriceRepId.trim();
    }

    public void setZxSkLimitPriceRepId(String zxSkLimitPriceRepId) {
        this.zxSkLimitPriceRepId = zxSkLimitPriceRepId == null ? null : zxSkLimitPriceRepId.trim();
    }

    public String getPeriod() {
        return period == null ? "" : period.trim();
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName.trim();
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getResourceName() {
        return resourceName == null ? "" : resourceName.trim();
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName == null ? null : resourceName.trim();
    }

    public String getWorkName() {
        return workName == null ? "" : workName.trim();
    }

    public void setWorkName(String workName) {
        this.workName = workName == null ? null : workName.trim();
    }

    public String getSpec() {
        return spec == null ? "" : spec.trim();
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getProvince() {
        return province == null ? "" : province.trim();
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
