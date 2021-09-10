package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import com.apih5.framework.entity.BasePojo;

public class ZxSaEquipPaySettleItem extends BasePojo {
    // 主键
    private String zxSaEquipPaySettleItemId;

    // 支付项结算id
    private String zxSaEquipPaySettleId;

    // 结算期次
    private String period;

    // 结算单编号
    private String billNo;

    // 合同id
    private String contractID;

    // 支付项id
    private String payID;

    // 编号
    private String payNo;

    // 支付项类型
    private String payType;

    // 名称
    private String payName;

    // 单位
    private String unit;

    // 数量
    private BigDecimal qty;

    // 单价(元)
    private BigDecimal price;

    // 本期结算金额(元)
    private BigDecimal thisAmt;

    // 本期结算不含税金额(元)
    private BigDecimal thisAmtNoTax;

    // 本期结算税额(元)
    private BigDecimal thisAmtTax;

    // 税率(%)
    private String taxRate;

    // 上期末结算金额(元)
    private BigDecimal upAmt;

    // 上期末数量
    private BigDecimal upQty;

    // 所属公司排序
    private String comOrders;

    // 是否被修改
    private String isFixed;

    // 所属公司名称
    private String comName;

    // 所属公司id
    private String comID;

    // 合同类型
    private String contrType;

    // 备注
    private String remark;

    // 重复校验事是否传主键id
    private String selectMainIdFlag;
    
    private String zxSaEquipSettleAuditId;
    
    // 至本期末累计结算数量
    private BigDecimal totalQty;

    // 至本期末累计结算金额(元)
    private BigDecimal totalAmt;

    public String getZxSaEquipPaySettleItemId() {
        return zxSaEquipPaySettleItemId == null ? "" : zxSaEquipPaySettleItemId.trim();
    }

    public void setZxSaEquipPaySettleItemId(String zxSaEquipPaySettleItemId) {
        this.zxSaEquipPaySettleItemId = zxSaEquipPaySettleItemId == null ? null : zxSaEquipPaySettleItemId.trim();
    }

    public String getZxSaEquipPaySettleId() {
        return zxSaEquipPaySettleId == null ? "" : zxSaEquipPaySettleId.trim();
    }

    public void setZxSaEquipPaySettleId(String zxSaEquipPaySettleId) {
        this.zxSaEquipPaySettleId = zxSaEquipPaySettleId == null ? null : zxSaEquipPaySettleId.trim();
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

    public String getContractID() {
        return contractID == null ? "" : contractID.trim();
    }

    public void setContractID(String contractID) {
        this.contractID = contractID == null ? null : contractID.trim();
    }

    public String getPayID() {
        return payID == null ? "" : payID.trim();
    }

    public void setPayID(String payID) {
        this.payID = payID == null ? null : payID.trim();
    }

    public String getPayNo() {
        return payNo == null ? "" : payNo.trim();
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo == null ? null : payNo.trim();
    }

    public String getPayType() {
        return payType == null ? "" : payType.trim();
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getPayName() {
        return payName == null ? "" : payName.trim();
    }

    public void setPayName(String payName) {
        this.payName = payName == null ? null : payName.trim();
    }

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public BigDecimal getThisAmtTax() {
        return thisAmtTax;
    }

    public void setThisAmtTax(BigDecimal thisAmtTax) {
        this.thisAmtTax = thisAmtTax;
    }

    public String getTaxRate() {
        return taxRate == null ? "" : taxRate.trim();
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate == null ? null : taxRate.trim();
    }

    public BigDecimal getUpAmt() {
        return upAmt;
    }

    public void setUpAmt(BigDecimal upAmt) {
        this.upAmt = upAmt;
    }

    public BigDecimal getUpQty() {
        return upQty;
    }

    public void setUpQty(BigDecimal upQty) {
        this.upQty = upQty;
    }

    public String getComOrders() {
        return comOrders == null ? "" : comOrders.trim();
    }

    public void setComOrders(String comOrders) {
        this.comOrders = comOrders == null ? null : comOrders.trim();
    }

    public String getIsFixed() {
        return isFixed == null ? "" : isFixed.trim();
    }

    public void setIsFixed(String isFixed) {
        this.isFixed = isFixed == null ? null : isFixed.trim();
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

    public String getContrType() {
        return contrType == null ? "" : contrType.trim();
    }

    public void setContrType(String contrType) {
        this.contrType = contrType == null ? null : contrType.trim();
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getSelectMainIdFlag() {
        return selectMainIdFlag == null ? "" : selectMainIdFlag.trim();
    }

    public void setSelectMainIdFlag(String selectMainIdFlag) {
        this.selectMainIdFlag = selectMainIdFlag == null ? null : selectMainIdFlag.trim();
    }

	public String getZxSaEquipSettleAuditId() {
		return zxSaEquipSettleAuditId;
	}

	public void setZxSaEquipSettleAuditId(String zxSaEquipSettleAuditId) {
		this.zxSaEquipSettleAuditId = zxSaEquipSettleAuditId;
	}

	public BigDecimal getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(BigDecimal totalQty) {
		this.totalQty = totalQty;
	}

	public BigDecimal getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}
	
}
