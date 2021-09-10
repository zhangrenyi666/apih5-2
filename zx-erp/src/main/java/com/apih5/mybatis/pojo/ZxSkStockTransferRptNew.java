package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkStockTransferRptNew extends BasePojo {
    // 主键
    private String zxSkStockTransferRptNewId;

    // 类别
    private String num;

    // 物资名称
    private String resName;

    // 特征字符
    private String resType;

    // 计量单位数量
    private String spec;

	// 计量单位金额
    private String unit;

    // 本季期初库存数量
    private BigDecimal qcinQty;

    // 本季期初库存金额
    private BigDecimal qcinAmt;
    
    // 本季期初库存金额总和
    private BigDecimal qcinAmtAll;

	// 本季收入合计数量
    private BigDecimal c4Qty;

    // 本季收入合计金额
    private BigDecimal c4Amt;
    
    // 本季收入合计金额总和
    private BigDecimal c4AmtAll;

    // 甲供数量
    private BigDecimal c1Qty;

    // 甲供金额
    private BigDecimal c1Amt;
    
    // 甲供金额总和
    private BigDecimal c1AmtAll;

    // 本季发出(消耗)数量
    private BigDecimal f3outQty;

    // 本季发出(消耗)金额
    private BigDecimal f3outAmt;
    
    // 本季发出(消耗)金额总和
    private BigDecimal f3outAmtAll;

    // 境外工程的国内采购数量
    private String s1;

	// 境外工程的国内采购金额
    private String s2;

    // 占本期营业额的比重
    private BigDecimal s3;

    // 本季期末库存数量
    private BigDecimal curQty;

    // 本季期末库存金额
    private BigDecimal curAmt;
    
    // 本季期末库存金额总和
    private BigDecimal curAmtAll;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    private Date beginDate;

	private Date endDate;
    
    private Date firstDate;
    
    private String totalAmt;
    
    private String orgID;

	private String period;
	
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
    
    public String getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
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

	public Date getFirstDate() {
		return firstDate;
	}

	public void setFirstDate(Date firstDate) {
		this.firstDate = firstDate;
	}

    public String getZxSkStockTransferRptNewId() {
        return zxSkStockTransferRptNewId == null ? "" : zxSkStockTransferRptNewId.trim();
    }

    public void setZxSkStockTransferRptNewId(String zxSkStockTransferRptNewId) {
        this.zxSkStockTransferRptNewId = zxSkStockTransferRptNewId == null ? null : zxSkStockTransferRptNewId.trim();
    }

    public String getNum() {
        return num == null ? "" : num.trim();
    }

    public void setNum(String Num) {
        this.num = Num == null ? null : num.trim();
    }

    public String getResName() {
        return resName == null ? "" : resName.trim();
    }

    public void setResName(String resName) {
        this.resName = resName == null ? null : resName.trim();
    }

    public String getResType() {
        return resType == null ? "" : resType.trim();
    }

    public void setResType(String resType) {
        this.resType = resType == null ? null : resType.trim();
    }

    public String getSpec() {
  		return spec;
  	}

  	public void setSpec(String spec) {
  		this.spec = spec;
  	}

  	public String getUnit() {
  		return unit;
  	}

  	public void setUnit(String unit) {
  		this.unit = unit;
  	}

    public BigDecimal getQcinQty() {
        return qcinQty;
    }

    public void setQcinQty(BigDecimal qcinQty) {
        this.qcinQty = qcinQty;
    }

    public BigDecimal getQcinAmt() {
        return qcinAmt;
    }

    public void setQcinAmt(BigDecimal qcinAmt) {
        this.qcinAmt = qcinAmt;
    }

    public BigDecimal getC4Qty() {
        return c4Qty;
    }

    public void setC4Qty(BigDecimal c4Qty) {
        this.c4Qty = c4Qty;
    }

    public BigDecimal getC4Amt() {
        return c4Amt;
    }

    public void setC4Amt(BigDecimal c4Amt) {
        this.c4Amt = c4Amt;
    }

    public BigDecimal getC1Qty() {
        return c1Qty;
    }

    public void setC1Qty(BigDecimal c1Qty) {
        this.c1Qty = c1Qty;
    }

    public BigDecimal getC1Amt() {
        return c1Amt;
    }

    public void setC1Amt(BigDecimal c1Amt) {
        this.c1Amt = c1Amt;
    }

    public BigDecimal getF3outQty() {
        return f3outQty;
    }

    public void setF3outQty(BigDecimal f3outQty) {
        this.f3outQty = f3outQty;
    }

    public BigDecimal getF3outAmt() {
        return f3outAmt;
    }

    public void setF3outAmt(BigDecimal f3outAmt) {
        this.f3outAmt = f3outAmt;
    }

    public String getS1() {
		return s1;
	}

	public void setS1(String s1) {
		this.s1 = s1;
	}

	public String getS2() {
		return s2;
	}

	public void setS2(String s2) {
		this.s2 = s2;
	}

    public BigDecimal getS3() {
        return s3;
    }

    public void setS3(BigDecimal s3) {
        this.s3 = s3;
    }

    public BigDecimal getCurQty() {
        return curQty;
    }

    public void setCurQty(BigDecimal curQty) {
        this.curQty = curQty;
    }

    public BigDecimal getCurAmt() {
        return curAmt;
    }

    public void setCurAmt(BigDecimal curAmt) {
        this.curAmt = curAmt;
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

    public BigDecimal getQcinAmtAll() {
 		return qcinAmtAll;
 	}

 	public void setQcinAmtAll(BigDecimal qcinAmtAll) {
 		this.qcinAmtAll = qcinAmtAll;
 	}

 	public BigDecimal getC4AmtAll() {
 		return c4AmtAll;
 	}

 	public void setC4AmtAll(BigDecimal c4AmtAll) {
 		this.c4AmtAll = c4AmtAll;
 	}

 	public BigDecimal getC1AmtAll() {
 		return c1AmtAll;
 	}

 	public void setC1AmtAll(BigDecimal c1AmtAll) {
 		this.c1AmtAll = c1AmtAll;
 	}

 	public BigDecimal getF3outAmtAll() {
 		return f3outAmtAll;
 	}

 	public void setF3outAmtAll(BigDecimal f3outAmtAll) {
 		this.f3outAmtAll = f3outAmtAll;
 	}

 	public BigDecimal getCurAmtAll() {
 		return curAmtAll;
 	}

 	public void setCurAmtAll(BigDecimal curAmtAll) {
 		this.curAmtAll = curAmtAll;
 	}
}
