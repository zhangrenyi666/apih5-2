package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;

import com.apih5.framework.entity.BasePojo;

public class ZxEqToCompareAnalyseQueryPage extends BasePojo {
    // 主键
    private String zxEqToCompareAnalyseQueryPageId;

    // 机构ID
    private String departmentId;

    // 机构名称
    private String departmentName;

    // 设备ID
    private String resCatalogID;

    // 设备名称
    private String resCatalogName;

    // ABC分类
    private String abcType;

    // 上期数量（台）
    private BigDecimal count1;

    // 上期原值（万元）
    private BigDecimal orginalValue1;

    // 本期数量（台）
    private BigDecimal count2;

    // 本期原值（万元）
    private BigDecimal orginalValue2;

    // 同比增长
    private BigDecimal compareAddRate;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    private String parentID;

	private Date beginDate;
    
    private Date endDate;
    
    private Date lastBeginDate;
    
    private Date lastEndDate;
    
    private String orgID;
    
    public String getOrgid() {
		return orgID;
	}

	public void setOrgid(String orgid) {
		this.orgID = orgid;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getLastBeginDate() {
		return lastBeginDate;
	}

	public void setLastBeginDate(Date lastBeginDate) {
		this.lastBeginDate = lastBeginDate;
	}

	public Date getLastEndDate() {
		return lastEndDate;
	}

	public void setLastEndDate(Date lastEndDate) {
		this.lastEndDate = lastEndDate;
	}

    public String getZxEqToCompareAnalyseQueryPageId() {
        return zxEqToCompareAnalyseQueryPageId == null ? "" : zxEqToCompareAnalyseQueryPageId.trim();
    }

    public void setZxEqToCompareAnalyseQueryPageId(String zxEqToCompareAnalyseQueryPageId) {
        this.zxEqToCompareAnalyseQueryPageId = zxEqToCompareAnalyseQueryPageId == null ? null : zxEqToCompareAnalyseQueryPageId.trim();
    }

    public String getDepartmentId() {
        return departmentId == null ? "" : departmentId.trim();
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
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

    public String getResCatalogName() {
        return resCatalogName == null ? "" : resCatalogName.trim();
    }

    public void setResCatalogName(String resCatalogName) {
        this.resCatalogName = resCatalogName == null ? null : resCatalogName.trim();
    }

    public String getAbcType() {
        return abcType == null ? "" : abcType.trim();
    }

    public void setAbcType(String abcType) {
        this.abcType = abcType == null ? null : abcType.trim();
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

    public BigDecimal getCompareAddRate() {
        return compareAddRate;
    }

    public void setCompareAddRate(BigDecimal compareAddRate) {
        this.compareAddRate = compareAddRate;
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
