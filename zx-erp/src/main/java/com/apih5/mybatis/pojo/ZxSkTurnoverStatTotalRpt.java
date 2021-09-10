package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkTurnoverStatTotalRpt extends BasePojo {
    // 主键
    private String zxSkTurnoverStatTotalRptId;

    // 物资编码
    private String resCode;

    // 物资名称
    private String resName;

    // 年初库存原值
    private BigDecimal lastInAmt;

    // 年初库存净值
    private BigDecimal lastremainAmt;

    // 本年累计收入金额
    private BigDecimal inAmt;

    // 本年累计摊销
    private BigDecimal shareAmts;

    // 开工累计摊销
    private BigDecimal shareAmtTotal;

    // 开累本年调出原值
    private BigDecimal allBuyAmt;

    // 开累本年调出净值
    private BigDecimal allCurrentAmt;

    // 开累本年报废原值
    private BigDecimal allOrigAmt;

    // 开累本年报废净值
    private BigDecimal allRemainAmt;

    // 期末库存原值
    private BigDecimal thisInAmt;

    // 期末库存净值
    private BigDecimal thisBadAmt;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    private String orgID;
    
	private String period;
    
    private String resID;
    
    private String beginMonth;
    
    private String endMonth;
    
    private Date beginDate;
    
    private Date endDate;
    
    private String yearBeginDate;
    
    private String isFinish;

    public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getResID() {
		return resID;
	}

	public void setResID(String resID) {
		this.resID = resID;
	}

	public String getBeginMonth() {
		return beginMonth;
	}

	public void setBeginMonth(String beginMonth) {
		this.beginMonth = beginMonth;
	}

	public String getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(String endMonth) {
		this.endMonth = endMonth;
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

	public String getYearBeginDate() {
		return yearBeginDate;
	}

	public void setYearBeginDate(String yearBeginDate) {
		this.yearBeginDate = yearBeginDate;
	}

	public String getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(String isFinish) {
		this.isFinish = isFinish;
	}
	
    public String getZxSkTurnoverStatTotalRptId() {
        return zxSkTurnoverStatTotalRptId == null ? "" : zxSkTurnoverStatTotalRptId.trim();
    }

    public void setZxSkTurnoverStatTotalRptId(String zxSkTurnoverStatTotalRptId) {
        this.zxSkTurnoverStatTotalRptId = zxSkTurnoverStatTotalRptId == null ? null : zxSkTurnoverStatTotalRptId.trim();
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

    public BigDecimal getLastInAmt() {
        return lastInAmt;
    }

    public void setLastInAmt(BigDecimal lastInAmt) {
        this.lastInAmt = lastInAmt;
    }

    public BigDecimal getLastremainAmt() {
        return lastremainAmt;
    }

    public void setLastremainAmt(BigDecimal lastremainAmt) {
        this.lastremainAmt = lastremainAmt;
    }

    public BigDecimal getInAmt() {
        return inAmt;
    }

    public void setInAmt(BigDecimal inAmt) {
        this.inAmt = inAmt;
    }

    public BigDecimal getShareAmts() {
        return shareAmts;
    }

    public void setShareAmts(BigDecimal shareAmts) {
        this.shareAmts = shareAmts;
    }

    public BigDecimal getShareAmtTotal() {
        return shareAmtTotal;
    }

    public void setShareAmtTotal(BigDecimal shareAmtTotal) {
        this.shareAmtTotal = shareAmtTotal;
    }

    public BigDecimal getAllBuyAmt() {
        return allBuyAmt;
    }

    public void setAllBuyAmt(BigDecimal allBuyAmt) {
        this.allBuyAmt = allBuyAmt;
    }

    public BigDecimal getAllCurrentAmt() {
        return allCurrentAmt;
    }

    public void setAllCurrentAmt(BigDecimal allCurrentAmt) {
        this.allCurrentAmt = allCurrentAmt;
    }

    public BigDecimal getAllOrigAmt() {
        return allOrigAmt;
    }

    public void setAllOrigAmt(BigDecimal allOrigAmt) {
        this.allOrigAmt = allOrigAmt;
    }

    public BigDecimal getAllRemainAmt() {
        return allRemainAmt;
    }

    public void setAllRemainAmt(BigDecimal allRemainAmt) {
        this.allRemainAmt = allRemainAmt;
    }

    public BigDecimal getThisInAmt() {
        return thisInAmt;
    }

    public void setThisInAmt(BigDecimal thisInAmt) {
        this.thisInAmt = thisInAmt;
    }

    public BigDecimal getThisBadAmt() {
        return thisBadAmt;
    }

    public void setThisBadAmt(BigDecimal thisBadAmt) {
        this.thisBadAmt = thisBadAmt;
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
