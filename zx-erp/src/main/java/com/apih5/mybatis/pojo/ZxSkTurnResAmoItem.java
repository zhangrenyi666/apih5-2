package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkTurnResAmoItem extends BasePojo {
    // 主键
    private String zxSkTurnResAmoItemId;

    // 物资编号
    private String resCode;

    // 物资名称
    private String resName;

    // 规格
    private String spec;

    // 单位
    private String resUnit;

    // 数量
    private BigDecimal buyQty;

    // 购入单价
    private BigDecimal buyPrice;

    // 原值
    private BigDecimal buyAmt;

    // 本期摊销金额
    private BigDecimal shareAmt;

    // 累计摊销金额
    private BigDecimal allShareAmt;

    // 期末净值
    private BigDecimal currentAmt;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    private String orgID;

	private String resID;
    
    private Date beginDate;
    
    private Date endDate;
    
    public String getOrgID() {
  		return orgID;
  	}

  	public void setOrgID(String orgID) {
  		this.orgID = orgID;
  	}

  	public String getResID() {
  		return resID;
  	}

  	public void setResID(String resID) {
  		this.resID = resID;
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

    public String getZxSkTurnResAmoItemId() {
        return zxSkTurnResAmoItemId == null ? "" : zxSkTurnResAmoItemId.trim();
    }

    public void setZxSkTurnResAmoItemId(String zxSkTurnResAmoItemId) {
        this.zxSkTurnResAmoItemId = zxSkTurnResAmoItemId == null ? null : zxSkTurnResAmoItemId.trim();
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

    public BigDecimal getBuyQty() {
        return buyQty;
    }

    public void setBuyQty(BigDecimal buyQty) {
        this.buyQty = buyQty;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BigDecimal getBuyAmt() {
        return buyAmt;
    }

    public void setBuyAmt(BigDecimal buyAmt) {
        this.buyAmt = buyAmt;
    }

    public BigDecimal getShareAmt() {
        return shareAmt;
    }

    public void setShareAmt(BigDecimal shareAmt) {
        this.shareAmt = shareAmt;
    }

    public BigDecimal getAllShareAmt() {
        return allShareAmt;
    }

    public void setAllShareAmt(BigDecimal allShareAmt) {
        this.allShareAmt = allShareAmt;
    }

    public BigDecimal getCurrentAmt() {
        return currentAmt;
    }

    public void setCurrentAmt(BigDecimal currentAmt) {
        this.currentAmt = currentAmt;
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
