package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkResourceOut extends BasePojo {
    // 主键
    private String zxSkResourceOutId;

    // 出库类型
    private String outType;

    // 出库日期
    private String busDate;

    // 领料/调拨单位
    private String inOrgName;

    // 物资类别
    private String resourceName;

    // 物资编号
    private String resCode;

    // 物资名称
    private String resName;

    // 规格
    private String spec;

    // 单位
    private String resUnit;

    // 数量
    private BigDecimal outQty;

    // 出库单价
    private BigDecimal stdPrice;

    // 金额
    private BigDecimal outAmt;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    private String orgID;
    
	private String appOrgID;
    
    private Date beginDate;
    
    private Date endDate;
    
    private String resTypeID;
    
    private String resID;
    
    public String getOrgID() {
  		return orgID;
  	}

  	public void setOrgID(String orgID) {
  		this.orgID = orgID;
  	}

  	public String getAppOrgID() {
  		return appOrgID;
  	}

  	public void setAppOrgID(String appOrgID) {
  		this.appOrgID = appOrgID;
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

  	public String getResTypeID() {
  		return resTypeID;
  	}

  	public void setResTypeID(String resTypeID) {
  		this.resTypeID = resTypeID;
  	}

  	public String getResID() {
  		return resID;
  	}

  	public void setResID(String resID) {
  		this.resID = resID;
  	}

    public String getZxSkResourceOutId() {
        return zxSkResourceOutId == null ? "" : zxSkResourceOutId.trim();
    }

    public void setZxSkResourceOutId(String zxSkResourceOutId) {
        this.zxSkResourceOutId = zxSkResourceOutId == null ? null : zxSkResourceOutId.trim();
    }

    public String getOutType() {
        return outType == null ? "" : outType.trim();
    }

    public void setOutType(String outType) {
        this.outType = outType == null ? null : outType.trim();
    }

    public String getBusDate() {
        return busDate == null ? "" : busDate.trim();
    }

    public void setBusDate(String busDate) {
        this.busDate = busDate == null ? null : busDate.trim();
    }

    public String getInOrgName() {
        return inOrgName == null ? "" : inOrgName.trim();
    }

    public void setInOrgName(String inOrgName) {
        this.inOrgName = inOrgName == null ? null : inOrgName.trim();
    }

    public String getResourceName() {
        return resourceName == null ? "" : resourceName.trim();
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName == null ? null : resourceName.trim();
    }

    public String getResCode() {
        return resCode == null ? "" : resCode.trim();
    }

    public void setResCode(String resCode) {
        this.resCode = resCode == null ? null : resCode.trim();
    }

    public String getResName() {
        return resName == null ? "" : resName.trim();
    }

    public void setResName(String resName) {
        this.resName = resName == null ? null : resName.trim();
    }

    public String getSpec() {
        return spec == null ? "" : spec.trim();
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getResUnit() {
        return resUnit == null ? "" : resUnit.trim();
    }

    public void setResUnit(String resUnit) {
        this.resUnit = resUnit == null ? null : resUnit.trim();
    }

    public BigDecimal getOutQty() {
        return outQty;
    }

    public void setOutQty(BigDecimal outQty) {
        this.outQty = outQty;
    }

    public BigDecimal getStdPrice() {
        return stdPrice;
    }

    public void setStdPrice(BigDecimal stdPrice) {
        this.stdPrice = stdPrice;
    }

    public BigDecimal getOutAmt() {
        return outAmt;
    }

    public void setOutAmt(BigDecimal outAmt) {
        this.outAmt = outAmt;
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
