package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;

public class ZxEqToEquipSourceQueryPage extends BasePojo {
    // 主键
    private String zxEqToEquipSourceQueryPageId;

    // 机构名称
    private String departmentName;

    // 租凭数量
    private BigDecimal count1;

    // 租凭金额
    private BigDecimal amt1;

    // 协作队伍自带数量
    private BigDecimal count2;

    // 协作队伍自带金额
    private BigDecimal amt2;

    // 自有设备数量
    private BigDecimal count3;

    // 自有设备金额
    private BigDecimal amt3;

    // 总计数量
    private BigDecimal count4;

    // 总计金额
    private BigDecimal amt4;

    // 设备分类
    private String resCatalogID;

    // 开始时间
    private Date beginDate;

    // 结束时间
    private Date endDate;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    // 父节点ID
    private String parentID;
    
    // 项目机构ID
	private String orgID;
	
	private String resCatalogBsid;
	
	 // 所属公司名称
    private String companyName; 

	// 所属公司ID
    private String companyId;
    
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	
	private List<ZxEqToEquipSourceQueryPage> children;
	
    
    public List<ZxEqToEquipSourceQueryPage> getChildren() {
		return children;
	}

	public void setChildren(List<ZxEqToEquipSourceQueryPage> children) {
		this.children = children;
	}

	public String getResCatalogBsid() {
		return resCatalogBsid;
	}

	public void setResCatalogBsid(String resCatalogBsid) {
		this.resCatalogBsid = resCatalogBsid;
	}

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

    public String getZxEqToEquipSourceQueryPageId() {
        return zxEqToEquipSourceQueryPageId == null ? "" : zxEqToEquipSourceQueryPageId.trim();
    }

    public void setZxEqToEquipSourceQueryPageId(String zxEqToEquipSourceQueryPageId) {
        this.zxEqToEquipSourceQueryPageId = zxEqToEquipSourceQueryPageId == null ? null : zxEqToEquipSourceQueryPageId.trim();
    }

    public String getDepartmentName() {
        return departmentName == null ? "" : departmentName.trim();
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public BigDecimal getCount1() {
        return count1;
    }

    public void setCount1(BigDecimal count1) {
        this.count1 = count1;
    }

    public BigDecimal getAmt1() {
        return amt1;
    }

    public void setAmt1(BigDecimal amt1) {
        this.amt1 = amt1;
    }

    public BigDecimal getCount2() {
        return count2;
    }

    public void setCount2(BigDecimal count2) {
        this.count2 = count2;
    }

    public BigDecimal getAmt2() {
        return amt2;
    }

    public void setAmt2(BigDecimal amt2) {
        this.amt2 = amt2;
    }

    public BigDecimal getCount3() {
        return count3;
    }

    public void setCount3(BigDecimal count3) {
        this.count3 = count3;
    }

    public BigDecimal getAmt3() {
        return amt3;
    }

    public void setAmt3(BigDecimal amt3) {
        this.amt3 = amt3;
    }

    public BigDecimal getCount4() {
        return count4;
    }

    public void setCount4(BigDecimal count4) {
        this.count4 = count4;
    }

    public BigDecimal getAmt4() {
        return amt4;
    }

    public void setAmt4(BigDecimal amt4) {
        this.amt4 = amt4;
    }

    public String getResCatalogID() {
        return resCatalogID == null ? "" : resCatalogID.trim();
    }

    public void setResCatalogID(String resCatalogID) {
        this.resCatalogID = resCatalogID == null ? null : resCatalogID.trim();
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
