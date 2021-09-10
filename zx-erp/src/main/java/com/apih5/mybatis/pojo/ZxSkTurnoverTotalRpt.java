package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkTurnoverTotalRpt extends BasePojo {
    // 主键
    private String zxSkTurnoverTotalRptId;

    // 编码
    private String resCode;

    // 物资名称
    private String resName;

    // 规格
    private String spec;

    // 单位
    private String resUnit;

    // 单价
    private String inPrice;

    // 年初库存数量
    private BigDecimal lastQty;

    // 年初库存原值
    private BigDecimal lastInAmt;

    // 年初库存净值
    private BigDecimal lastremainAmt;

    // 本期收入数量
    private BigDecimal inQtya;

    // 本期收入累积数量
    private BigDecimal inQty;

    // 本期收入金额
    private BigDecimal inAmta;

    // 本期收入累积金额
    private BigDecimal inAmt;

    // 本期摊销
    private BigDecimal shareAmt;

    // 本年累计摊销
    private BigDecimal shareAmts;

    // 开工累计摊销
    private BigDecimal shareAmtTotal;

    // 本期调出数量
    private BigDecimal thisDiscQty;

    // 本年开累调出数量
    private BigDecimal allOutQty;

    // 开工累计调出数量
    private BigDecimal totalOutQty;

    // 本期调出原值
    private BigDecimal thisBuyAmt;

    // 本期调出净值
    private BigDecimal thisCurrentAmt;

    // 开累本年调出原值
    private BigDecimal allBuyAmt;

    // 开累本年调出净值
    private BigDecimal allCurrentAmt;

    // 开工累计原值
    private BigDecimal totalBuyAmt;

    // 开工累计净值
    private BigDecimal totalCurrentAmt;

    // 本期报废数量
    private BigDecimal thisBadQty;

    // 本年开累报废数量
    private BigDecimal allDiscQty;

    // 开工累计数量
    private BigDecimal totalDiscQty;

    // 本期报废原值
    private BigDecimal thisOrigAmt;

    // 本期报废净值
    private BigDecimal thisRemainAmt;

    // 开累本年报废原值
    private BigDecimal allOrigAmt;

    // 开累本年报废净值
    private BigDecimal allRemainAmt;

    // 开工累计原值
    private BigDecimal totalOrigAmt;

    // 开工累计净值
    private BigDecimal totalRemainAmt;

    // 期末库存数量
    private BigDecimal thisQty;

    // 在库数量
    private BigDecimal inUse;

    // 在用数量
    private BigDecimal atUse;

    // 期末库存原值
    private BigDecimal thisInAmt;

    // 期末库存净值
    private BigDecimal thisBadAmt;

    // 所属单位
    private BigDecimal orgName;

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
    
    private Date yearBeginDate;
    
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

	public Date getYearBeginDate() {
		return yearBeginDate;
	}

	public void setYearBeginDate(Date yearBeginDate) {
		this.yearBeginDate = yearBeginDate;
	}

	public String getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(String isFinish) {
		this.isFinish = isFinish;
	}
    
    public String getZxSkTurnoverTotalRptId() {
        return zxSkTurnoverTotalRptId == null ? "" : zxSkTurnoverTotalRptId.trim();
    }

    public void setZxSkTurnoverTotalRptId(String zxSkTurnoverTotalRptId) {
        this.zxSkTurnoverTotalRptId = zxSkTurnoverTotalRptId == null ? null : zxSkTurnoverTotalRptId.trim();
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

    public String getInPrice() {
        return inPrice == null ? "" : inPrice.trim();
    }

    public void setInPrice(String inPrice) {
        this.inPrice = inPrice == null ? null : inPrice.trim();
    }

    public BigDecimal getLastQty() {
        return lastQty;
    }

    public void setLastQty(BigDecimal lastQty) {
        this.lastQty = lastQty;
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

    public BigDecimal getInQtya() {
        return inQtya;
    }

    public void setInQtya(BigDecimal inQtya) {
        this.inQtya = inQtya;
    }

    public BigDecimal getInQty() {
        return inQty;
    }

    public void setInQty(BigDecimal inQty) {
        this.inQty = inQty;
    }

    public BigDecimal getInAmta() {
        return inAmta;
    }

    public void setInAmta(BigDecimal inAmta) {
        this.inAmta = inAmta;
    }

    public BigDecimal getInAmt() {
        return inAmt;
    }

    public void setInAmt(BigDecimal inAmt) {
        this.inAmt = inAmt;
    }

    public BigDecimal getShareAmt() {
        return shareAmt;
    }

    public void setShareAmt(BigDecimal shareAmt) {
        this.shareAmt = shareAmt;
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

    public BigDecimal getThisDiscQty() {
        return thisDiscQty;
    }

    public void setThisDiscQty(BigDecimal thisDiscQty) {
        this.thisDiscQty = thisDiscQty;
    }

    public BigDecimal getAllOutQty() {
        return allOutQty;
    }

    public void setAllOutQty(BigDecimal allOutQty) {
        this.allOutQty = allOutQty;
    }

    public BigDecimal getTotalOutQty() {
        return totalOutQty;
    }

    public void setTotalOutQty(BigDecimal totalOutQty) {
        this.totalOutQty = totalOutQty;
    }

    public BigDecimal getThisBuyAmt() {
        return thisBuyAmt;
    }

    public void setThisBuyAmt(BigDecimal thisBuyAmt) {
        this.thisBuyAmt = thisBuyAmt;
    }

    public BigDecimal getThisCurrentAmt() {
        return thisCurrentAmt;
    }

    public void setThisCurrentAmt(BigDecimal thisCurrentAmt) {
        this.thisCurrentAmt = thisCurrentAmt;
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

    public BigDecimal getTotalBuyAmt() {
        return totalBuyAmt;
    }

    public void setTotalBuyAmt(BigDecimal totalBuyAmt) {
        this.totalBuyAmt = totalBuyAmt;
    }

    public BigDecimal getTotalCurrentAmt() {
        return totalCurrentAmt;
    }

    public void setTotalCurrentAmt(BigDecimal totalCurrentAmt) {
        this.totalCurrentAmt = totalCurrentAmt;
    }

    public BigDecimal getThisBadQty() {
        return thisBadQty;
    }

    public void setThisBadQty(BigDecimal thisBadQty) {
        this.thisBadQty = thisBadQty;
    }

    public BigDecimal getAllDiscQty() {
        return allDiscQty;
    }

    public void setAllDiscQty(BigDecimal allDiscQty) {
        this.allDiscQty = allDiscQty;
    }

    public BigDecimal getTotalDiscQty() {
        return totalDiscQty;
    }

    public void setTotalDiscQty(BigDecimal totalDiscQty) {
        this.totalDiscQty = totalDiscQty;
    }

    public BigDecimal getThisOrigAmt() {
        return thisOrigAmt;
    }

    public void setThisOrigAmt(BigDecimal thisOrigAmt) {
        this.thisOrigAmt = thisOrigAmt;
    }

    public BigDecimal getThisRemainAmt() {
        return thisRemainAmt;
    }

    public void setThisRemainAmt(BigDecimal thisRemainAmt) {
        this.thisRemainAmt = thisRemainAmt;
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

    public BigDecimal getTotalOrigAmt() {
        return totalOrigAmt;
    }

    public void setTotalOrigAmt(BigDecimal totalOrigAmt) {
        this.totalOrigAmt = totalOrigAmt;
    }

    public BigDecimal getTotalRemainAmt() {
        return totalRemainAmt;
    }

    public void setTotalRemainAmt(BigDecimal totalRemainAmt) {
        this.totalRemainAmt = totalRemainAmt;
    }

    public BigDecimal getThisQty() {
        return thisQty;
    }

    public void setThisQty(BigDecimal thisQty) {
        this.thisQty = thisQty;
    }

    public BigDecimal getInUse() {
        return inUse;
    }

    public void setInUse(BigDecimal inUse) {
        this.inUse = inUse;
    }

    public BigDecimal getAtUse() {
        return atUse;
    }

    public void setAtUse(BigDecimal atUse) {
        this.atUse = atUse;
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

    public BigDecimal getOrgName() {
        return orgName;
    }

    public void setOrgName(BigDecimal orgName) {
        this.orgName = orgName;
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
