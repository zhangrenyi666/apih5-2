package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkWornOutList extends BasePojo {
    // 主键
    private String zxSkWornOutListId;

    // 项目名称
    private String orgName;

    // 单据编号
    private String billNo;

    // 物资名称
    private String resName;

    // 单位
    private String unit;

    // 数量
    private BigDecimal stockQty;

    // 拟处理金额（万元）
    private BigDecimal originalAmt;

    // 上报审批日期
    private Date reportDate;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    private String orgID;

	private Date beginDate;
    
    private Date endDate;
    
    public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
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
	
    public String getZxSkWornOutListId() {
        return zxSkWornOutListId == null ? "" : zxSkWornOutListId.trim();
    }

    public void setZxSkWornOutListId(String zxSkWornOutListId) {
        this.zxSkWornOutListId = zxSkWornOutListId == null ? null : zxSkWornOutListId.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getBillNo() {
        return billNo == null ? "" : billNo.trim();
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public String getResName() {
        return resName == null ? "" : resName.trim();
    }

    public void setResName(String resName) {
        this.resName = resName == null ? null : resName.trim();
    }

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getStockQty() {
        return stockQty;
    }

    public void setStockQty(BigDecimal stockQty) {
        this.stockQty = stockQty;
    }

    public BigDecimal getOriginalAmt() {
        return originalAmt;
    }

    public void setOriginalAmt(BigDecimal originalAmt) {
        this.originalAmt = originalAmt;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
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
