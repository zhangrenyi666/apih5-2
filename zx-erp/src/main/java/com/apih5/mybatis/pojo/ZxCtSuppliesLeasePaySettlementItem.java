package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCtSuppliesLeasePaySettlementItem extends BasePojo {
    // 主键
    private String zxCtSuppliesLeasePaySettlementItemId;

    // 最后编辑时间
    private Date editTime;

    // 主表ID
    private String mainID;

    // 支付项类型
    private String payType;

    // 支付项ID
    private String payID;

    // 序号
    private int orderNum=0;

    // 所属公司排序
    private String comOrders;

    // 所属公司名称
    private String comName;

    // 所属公司ID
    private String comID;

    // 税率(%)
    private String taxRate;

    // 数量
    private BigDecimal qty;

    // 是否被修改
    private String isFixed;

    // 上期末数量
    private BigDecimal upQty;

    // 上期末结算金额(元)
    private BigDecimal upAmt;

    // 名称
    private String payName;

    // 结算期次
    private String period;

    // 结算单编号
    private String billNo;

    // 合同类型
    private String contrType;

    // 合同ID
    private String contractID;

    // 单位
    private String unit;

    // 单价(元)
    private BigDecimal price;

    // 编号
    private String payNo;

    // 本期结算税额(元)
    private BigDecimal thisAmtTax;

    // 本期结算金额(元)
    private BigDecimal thisAmt;

    // 本期结算不含税金额(元)
    private BigDecimal thisAmtNoTax;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    //上期末累计支付项结算金额(元)
    private BigDecimal upAmtByPay;
    
    //结算主表ID
    private String zxCtSuppliesLeaseSettlementListId;

	public BigDecimal getUpAmtByPay() {
		return upAmtByPay;
	}

	public void setUpAmtByPay(BigDecimal upAmtByPay) {
		this.upAmtByPay = upAmtByPay;
	}

	public String getZxCtSuppliesLeaseSettlementListId() {
		return zxCtSuppliesLeaseSettlementListId;
	}

	public void setZxCtSuppliesLeaseSettlementListId(String zxCtSuppliesLeaseSettlementListId) {
		this.zxCtSuppliesLeaseSettlementListId = zxCtSuppliesLeaseSettlementListId;
	}

	public String getZxCtSuppliesLeasePaySettlementItemId() {
        return zxCtSuppliesLeasePaySettlementItemId == null ? "" : zxCtSuppliesLeasePaySettlementItemId.trim();
    }

    public void setZxCtSuppliesLeasePaySettlementItemId(String zxCtSuppliesLeasePaySettlementItemId) {
        this.zxCtSuppliesLeasePaySettlementItemId = zxCtSuppliesLeasePaySettlementItemId == null ? null : zxCtSuppliesLeasePaySettlementItemId.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getMainID() {
        return mainID == null ? "" : mainID.trim();
    }

    public void setMainID(String mainID) {
        this.mainID = mainID == null ? null : mainID.trim();
    }

    public String getPayType() {
        return payType == null ? "" : payType.trim();
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getPayID() {
        return payID == null ? "" : payID.trim();
    }

    public void setPayID(String payID) {
        this.payID = payID == null ? null : payID.trim();
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getComOrders() {
        return comOrders == null ? "" : comOrders.trim();
    }

    public void setComOrders(String comOrders) {
        this.comOrders = comOrders == null ? null : comOrders.trim();
    }

    public String getComName() {
        return comName == null ? "" : comName.trim();
    }

    public void setComName(String comName) {
        this.comName = comName == null ? null : comName.trim();
    }

    public String getComID() {
        return comID == null ? "" : comID.trim();
    }

    public void setComID(String comID) {
        this.comID = comID == null ? null : comID.trim();
    }

    public String getTaxRate() {
        return taxRate == null ? "" : taxRate.trim();
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate == null ? null : taxRate.trim();
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public String getIsFixed() {
        return isFixed == null ? "" : isFixed.trim();
    }

    public void setIsFixed(String isFixed) {
        this.isFixed = isFixed == null ? null : isFixed.trim();
    }

    public BigDecimal getUpQty() {
        return upQty;
    }

    public void setUpQty(BigDecimal upQty) {
        this.upQty = upQty;
    }

    public BigDecimal getUpAmt() {
        return upAmt;
    }

    public void setUpAmt(BigDecimal upAmt) {
        this.upAmt = upAmt;
    }

    public String getPayName() {
        return payName == null ? "" : payName.trim();
    }

    public void setPayName(String payName) {
        this.payName = payName == null ? null : payName.trim();
    }

    public String getPeriod() {
        return period == null ? "" : period.trim();
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getBillNo() {
        return billNo == null ? "" : billNo.trim();
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public String getContrType() {
        return contrType == null ? "" : contrType.trim();
    }

    public void setContrType(String contrType) {
        this.contrType = contrType == null ? null : contrType.trim();
    }

    public String getContractID() {
        return contractID == null ? "" : contractID.trim();
    }

    public void setContractID(String contractID) {
        this.contractID = contractID == null ? null : contractID.trim();
    }

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPayNo() {
        return payNo == null ? "" : payNo.trim();
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo == null ? null : payNo.trim();
    }

    public BigDecimal getThisAmtTax() {
        return thisAmtTax;
    }

    public void setThisAmtTax(BigDecimal thisAmtTax) {
        this.thisAmtTax = thisAmtTax;
    }

    public BigDecimal getThisAmt() {
        return thisAmt;
    }

    public void setThisAmt(BigDecimal thisAmt) {
        this.thisAmt = thisAmt;
    }

    public BigDecimal getThisAmtNoTax() {
        return thisAmtNoTax;
    }

    public void setThisAmtNoTax(BigDecimal thisAmtNoTax) {
        this.thisAmtNoTax = thisAmtNoTax;
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
