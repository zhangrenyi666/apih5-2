package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkResInOutStockAllAmt extends BasePojo {
    // 主键
    private String zxSkResInOutStockAllAmtId;

    // 物资类别
    private String resName;

    // 统计字母或代码
    private String resCode;

    // 年初结存(数量)
    private BigDecimal stockAmt;
    
    private String spec;

	// 累计收入
    private BigDecimal obuAmt;

    // 累计收入合计
    private BigDecimal totalAmt;

    // 累计收入总收入金额
    private BigDecimal totalAmtAll;

    // 累计消费
    private BigDecimal oswAmt;

    // 累计拨出
    private BigDecimal otkAmt;

    // 盈亏
    private BigDecimal vinAmt;

    // 期末库存
    private BigDecimal thisAmt;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    private String orgID;

	private String period;
    
    private String isFinish;
    
    private String resID;
    
    private Date beginDate;
    
    private Date endDate;
    
    public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}
    
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

 	public String getIsFinish() {
 		return isFinish;
 	}

 	public void setIsFinish(String isFinish) {
 		this.isFinish = isFinish;
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

    public String getZxSkResInOutStockAllAmtId() {
        return zxSkResInOutStockAllAmtId == null ? "" : zxSkResInOutStockAllAmtId.trim();
    }

    public void setZxSkResInOutStockAllAmtId(String zxSkResInOutStockAllAmtId) {
        this.zxSkResInOutStockAllAmtId = zxSkResInOutStockAllAmtId == null ? null : zxSkResInOutStockAllAmtId.trim();
    }

    public String getResName() {
        return resName == null ? "" : resName.trim();
    }

    public void setResName(String resName) {
        this.resName = resName == null ? null : resName.trim();
    }

    public String getResCode() {
        return resCode == null ? "" : resCode.trim();
    }

    public void setResCode(String resCode) {
        this.resCode = resCode == null ? null : resCode.trim();
    }

    public BigDecimal getStockAmt() {
        return stockAmt;
    }

    public void setStockAmt(BigDecimal stockAmt) {
        this.stockAmt = stockAmt;
    }

    public BigDecimal getObuAmt() {
        return obuAmt;
    }

    public void setObuAmt(BigDecimal obuAmt) {
        this.obuAmt = obuAmt;
    }

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

    public BigDecimal getTotalAmtAll() {
        return totalAmtAll;
    }

    public void setTotalAmtAll(BigDecimal totalAmtAll) {
        this.totalAmtAll = totalAmtAll;
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

    public BigDecimal getVinAmt() {
        return vinAmt;
    }

    public void setVinAmt(BigDecimal vinAmt) {
        this.vinAmt = vinAmt;
    }

    public BigDecimal getThisAmt() {
        return thisAmt;
    }

    public void setThisAmt(BigDecimal thisAmt) {
        this.thisAmt = thisAmt;
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
