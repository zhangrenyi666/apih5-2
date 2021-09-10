package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkStockTransferRpt extends BasePojo {
    // 主键
    private String zxSkStockTransferRptId;

    // 序号
    private String Num;

    // 物资名称
    private String resName;

    // 计量单位数量
    private String spec;

    // 计量单位金额
    private String unit;

    // 期初结存数量
    private BigDecimal qcinQty;

    // 期初结存金额
    private BigDecimal qcinAmt;

    // 本季收入甲供数量
    private BigDecimal c1Qty;

    // 本季收入甲供金额
    private BigDecimal c1Amt;

    // 本季收入甲供平均单价
    private BigDecimal c1Price;

    // 本季收入自采数量
    private BigDecimal c2Qty;

    // 本季收入自采金额
    private BigDecimal c2Amt;

    // 本季收入自采平均单价
    private BigDecimal c2Price;

    // 本季收入其他数量
    private BigDecimal c3Qty;

    // 本季收入其他金额
    private BigDecimal c3Amt;

    // 本季收入其他平均单价
    private BigDecimal c3Price;

    // 合计数量
    private BigDecimal hQty;

    // 合计金额
    private BigDecimal hAmt;

    // 自年初收入合计数量
    private BigDecimal totalQtyS;

    // 自年初收入合计金额
    private BigDecimal totalAmtS;

    // 本季发出消耗数量
    private BigDecimal f1outQty;

    // 本季发出消耗金额
    private BigDecimal f1outAmt;

    // 本季发出其他数量
    private BigDecimal f1outQtyQ;

    // 本季发出其他金额
    private BigDecimal f1outAmtQ;

    // 本季发出合计数量
    private BigDecimal qtyT;

    // 本季发出合计金额
    private BigDecimal AmtT;

    // 自年初发出合计数量
    private BigDecimal f1outQtyN;

    // 自年初发出合计金额
    private BigDecimal f1outAmtN;

    // 本季结存数量
    private BigDecimal tQty;

    // 本季结存金额
    private BigDecimal tAmt;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    private String orgID;

	private String period;
    
    private Date beginDate;
    
    private Date endDate;
    
    private Date firstDate;

    public BigDecimal gethQty() {
		return hQty;
	}

	public void sethQty(BigDecimal hQty) {
		this.hQty = hQty;
	}

	public BigDecimal gethAmt() {
		return hAmt;
	}

	public void sethAmt(BigDecimal hAmt) {
		this.hAmt = hAmt;
	}

	public BigDecimal gettQty() {
		return tQty;
	}

	public void settQty(BigDecimal tQty) {
		this.tQty = tQty;
	}

	public BigDecimal gettAmt() {
		return tAmt;
	}

	public void settAmt(BigDecimal tAmt) {
		this.tAmt = tAmt;
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
	
    public String getZxSkStockTransferRptId() {
        return zxSkStockTransferRptId == null ? "" : zxSkStockTransferRptId.trim();
    }

    public void setZxSkStockTransferRptId(String zxSkStockTransferRptId) {
        this.zxSkStockTransferRptId = zxSkStockTransferRptId == null ? null : zxSkStockTransferRptId.trim();
    }

    public String getNum() {
        return Num == null ? "" : Num.trim();
    }

    public void setNum(String Num) {
        this.Num = Num == null ? null : Num.trim();
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

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
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

    public BigDecimal getC1Price() {
        return c1Price;
    }

    public void setC1Price(BigDecimal c1Price) {
        this.c1Price = c1Price;
    }

    public BigDecimal getC2Qty() {
        return c2Qty;
    }

    public void setC2Qty(BigDecimal c2Qty) {
        this.c2Qty = c2Qty;
    }

    public BigDecimal getC2Amt() {
        return c2Amt;
    }

    public void setC2Amt(BigDecimal c2Amt) {
        this.c2Amt = c2Amt;
    }

    public BigDecimal getC2Price() {
        return c2Price;
    }

    public void setC2Price(BigDecimal c2Price) {
        this.c2Price = c2Price;
    }

    public BigDecimal getC3Qty() {
        return c3Qty;
    }

    public void setC3Qty(BigDecimal c3Qty) {
        this.c3Qty = c3Qty;
    }

    public BigDecimal getC3Amt() {
        return c3Amt;
    }

    public void setC3Amt(BigDecimal c3Amt) {
        this.c3Amt = c3Amt;
    }

    public BigDecimal getC3Price() {
        return c3Price;
    }

    public void setC3Price(BigDecimal c3Price) {
        this.c3Price = c3Price;
    }

    public BigDecimal getHQty() {
        return hQty;
    }

    public void setHQty(BigDecimal hQty) {
        this.hQty = hQty;
    }

    public BigDecimal getHAmt() {
        return hAmt;
    }

    public void setHAmt(BigDecimal hAmt) {
        this.hAmt = hAmt;
    }

    public BigDecimal getTotalQtyS() {
        return totalQtyS;
    }

    public void setTotalQtyS(BigDecimal totalQtyS) {
        this.totalQtyS = totalQtyS;
    }

    public BigDecimal getTotalAmtS() {
        return totalAmtS;
    }

    public void setTotalAmtS(BigDecimal totalAmtS) {
        this.totalAmtS = totalAmtS;
    }

    public BigDecimal getF1outQty() {
        return f1outQty;
    }

    public void setF1outQty(BigDecimal f1outQty) {
        this.f1outQty = f1outQty;
    }

    public BigDecimal getF1outAmt() {
        return f1outAmt;
    }

    public void setF1outAmt(BigDecimal f1outAmt) {
        this.f1outAmt = f1outAmt;
    }

    public BigDecimal getF1outQtyQ() {
        return f1outQtyQ;
    }

    public void setF1outQtyQ(BigDecimal f1outQtyQ) {
        this.f1outQtyQ = f1outQtyQ;
    }

    public BigDecimal getF1outAmtQ() {
        return f1outAmtQ;
    }

    public void setF1outAmtQ(BigDecimal f1outAmtQ) {
        this.f1outAmtQ = f1outAmtQ;
    }

    public BigDecimal getQtyT() {
        return qtyT;
    }

    public void setQtyT(BigDecimal qtyT) {
        this.qtyT = qtyT;
    }

    public BigDecimal getAmtT() {
        return AmtT;
    }

    public void setAmtT(BigDecimal AmtT) {
        this.AmtT = AmtT;
    }

    public BigDecimal getF1outQtyN() {
        return f1outQtyN;
    }

    public void setF1outQtyN(BigDecimal f1outQtyN) {
        this.f1outQtyN = f1outQtyN;
    }

    public BigDecimal getF1outAmtN() {
        return f1outAmtN;
    }

    public void setF1outAmtN(BigDecimal f1outAmtN) {
        this.f1outAmtN = f1outAmtN;
    }

    public BigDecimal getTQty() {
        return tQty;
    }

    public void setTQty(BigDecimal tQty) {
        this.tQty = tQty;
    }

    public BigDecimal getTAmt() {
        return tAmt;
    }

    public void setTAmt(BigDecimal tAmt) {
        this.tAmt = tAmt;
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
