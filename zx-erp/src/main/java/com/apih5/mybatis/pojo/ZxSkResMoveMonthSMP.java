package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkResMoveMonthSMP extends BasePojo {
    // 主键
    private String zxSkResMoveMonthSMPId;

    // 物资编号
    private String resCode;

    // 物资名称
    private String resName;

    // 本月收入
    private BigDecimal spce;

    // 上月结存数量
    private BigDecimal stock;

	// 上月结存数量
    private BigDecimal stockQty;

    // 合计
    private BigDecimal resUnit;

    // 上月结存平均单价（元）
    private BigDecimal stockPrice;

    // 上月结存金额（元）
    private BigDecimal stockAmt;

    // 甲供
    private BigDecimal orsQty;

    // 其他
    private BigDecimal otrQty;

    // 自购
    private BigDecimal serQty;

    // 预收
    private BigDecimal obuQty;

    // 甲控
    private BigDecimal ocsQty;

    // 本月收入合计数量
    private BigDecimal inQty;

    // 本月收入合计金额
    private BigDecimal inAmt;

    // 本月收入合计平均单价（元）
    private BigDecimal inPrice;

    // 工程耗用数量
    private BigDecimal oswQty;

    // 工程耗用平均单价（元）
    private BigDecimal oswPrice;

    // 调拨
    private BigDecimal otkQty;

    // 工程耗用金额
    private BigDecimal oswAmt;

    // 调拨金额
    private BigDecimal otkAmt;

    // 调拨平均单价（元）
    private BigDecimal otkPrice;

    // 盈亏数量
    private BigDecimal vinQty;

    // 盈亏金额
    private BigDecimal vinAmt;

    // 本月结存数量
    private BigDecimal thisQty;

    // 本月结存金额
    private BigDecimal thisAmt;

    // 本月结存平均单价（元）
    private BigDecimal thisPrice;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    private String appOrgID;

	private String orgID;
    
    private String resID;
    
    private String period;
    
    private String isFinish;
    
    private Date beginDate;
    
    private Date endDate;
    
    public BigDecimal getStock() {
 		return stock;
 	}

 	public void setStock(BigDecimal stock) {
 		this.stock = stock;
 	}
    
    public String getAppOrgID() {
		return appOrgID;
	}

	public void setAppOrgID(String appOrgID) {
		this.appOrgID = appOrgID;
	}

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

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(String isFinish) {
		this.isFinish = isFinish;
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

    public String getZxSkResMoveMonthSMPId() {
        return zxSkResMoveMonthSMPId == null ? "" : zxSkResMoveMonthSMPId.trim();
    }

    public void setZxSkResMoveMonthSMPId(String zxSkResMoveMonthSMPId) {
        this.zxSkResMoveMonthSMPId = zxSkResMoveMonthSMPId == null ? null : zxSkResMoveMonthSMPId.trim();
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

    public BigDecimal getSpce() {
        return spce;
    }

    public void setSpce(BigDecimal spce) {
        this.spce = spce;
    }

    public BigDecimal getStockQty() {
        return stockQty;
    }

    public void setStockQty(BigDecimal stockQty) {
        this.stockQty = stockQty;
    }

    public BigDecimal getResUnit() {
        return resUnit;
    }

    public void setResUnit(BigDecimal resUnit) {
        this.resUnit = resUnit;
    }

    public BigDecimal getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(BigDecimal stockPrice) {
        this.stockPrice = stockPrice;
    }

    public BigDecimal getStockAmt() {
        return stockAmt;
    }

    public void setStockAmt(BigDecimal stockAmt) {
        this.stockAmt = stockAmt;
    }

    public BigDecimal getOrsQty() {
        return orsQty;
    }

    public void setOrsQty(BigDecimal orsQty) {
        this.orsQty = orsQty;
    }

    public BigDecimal getOtrQty() {
        return otrQty;
    }

    public void setOtrQty(BigDecimal otrQty) {
        this.otrQty = otrQty;
    }

    public BigDecimal getSerQty() {
        return serQty;
    }

    public void setSerQty(BigDecimal serQty) {
        this.serQty = serQty;
    }

    public BigDecimal getObuQty() {
        return obuQty;
    }

    public void setObuQty(BigDecimal obuQty) {
        this.obuQty = obuQty;
    }

    public BigDecimal getOcsQty() {
        return ocsQty;
    }

    public void setOcsQty(BigDecimal ocsQty) {
        this.ocsQty = ocsQty;
    }

    public BigDecimal getInQty() {
        return inQty;
    }

    public void setInQty(BigDecimal inQty) {
        this.inQty = inQty;
    }

    public BigDecimal getInAmt() {
        return inAmt;
    }

    public void setInAmt(BigDecimal inAmt) {
        this.inAmt = inAmt;
    }

    public BigDecimal getInPrice() {
        return inPrice;
    }

    public void setInPrice(BigDecimal inPrice) {
        this.inPrice = inPrice;
    }

    public BigDecimal getOswQty() {
        return oswQty;
    }

    public void setOswQty(BigDecimal oswQty) {
        this.oswQty = oswQty;
    }

    public BigDecimal getOswPrice() {
        return oswPrice;
    }

    public void setOswPrice(BigDecimal oswPrice) {
        this.oswPrice = oswPrice;
    }

    public BigDecimal getOtkQty() {
        return otkQty;
    }

    public void setOtkQty(BigDecimal otkQty) {
        this.otkQty = otkQty;
    }

    public BigDecimal getOswAmt() {
        return oswAmt;
    }

    public void setOswAmt(BigDecimal oswAmt) {
        this.oswAmt = oswAmt;
    }

    public BigDecimal getOtkAmt() {
        return otkAmt;
    }

    public void setOtkAmt(BigDecimal otkAmt) {
        this.otkAmt = otkAmt;
    }

    public BigDecimal getOtkPrice() {
        return otkPrice;
    }

    public void setOtkPrice(BigDecimal otkPrice) {
        this.otkPrice = otkPrice;
    }

    public BigDecimal getVinQty() {
        return vinQty;
    }

    public void setVinQty(BigDecimal vinQty) {
        this.vinQty = vinQty;
    }

    public BigDecimal getVinAmt() {
        return vinAmt;
    }

    public void setVinAmt(BigDecimal vinAmt) {
        this.vinAmt = vinAmt;
    }

    public BigDecimal getThisQty() {
        return thisQty;
    }

    public void setThisQty(BigDecimal thisQty) {
        this.thisQty = thisQty;
    }

    public BigDecimal getThisAmt() {
        return thisAmt;
    }

    public void setThisAmt(BigDecimal thisAmt) {
        this.thisAmt = thisAmt;
    }

    public BigDecimal getThisPrice() {
        return thisPrice;
    }

    public void setThisPrice(BigDecimal thisPrice) {
        this.thisPrice = thisPrice;
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
