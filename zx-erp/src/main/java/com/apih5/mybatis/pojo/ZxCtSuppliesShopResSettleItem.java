package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;

public class ZxCtSuppliesShopResSettleItem extends BasePojo {
    // 主键
    private String zxCtSuppliesShopResSettleItemId;

    // 最后编辑时间
    private Date editTime;

    // 主表ID
    private String mainID;

    // 序号
    private int orderNum=0;

    // 物资名称
    private String resName;

    // 物资编码
    private String resCode;

    // 物资ID
    private String resID;

    // 所属公司排序
    private String comOrders;

    // 所属公司名称
    private String comName;

    // 所属公司ID
    private String comID;

    // 税率
    private String taxRate;

    // 收料退货单明细ID
    private String stockBillItemID;

    // 收料单(或退货单)编号
    private String stockBillNo;

    // 收料单(或退货单)ID
    private String stockBillID;

    // 是否预收
    private String precollecte;

    // 签认单明细ID
    private String signedOrderItemID;

    // 签认单编号
    private String signedNo;

    // 签认单ID
    private String signedOrderID;

    // 结算期次
    private String period;

    // 合同清单ID
    private String contrItemID;

    // 合同ID
    private String contractID;

    // 规格型号
    private String spec;

    // 单位
    private String unit;

    // 本期税额(元)
    private BigDecimal thisAmtTax;

    // 本期数量
    private BigDecimal thisQty;

    // 本期含税金额(元)
    private BigDecimal thisAmt;

    // 本期含税单价(元)
    private BigDecimal thisPrice;

    // 本期单价(元)
    private String isChoosePrice;

    // 本期不含税金额(元)
    private BigDecimal thisAmtNoTax;

    // 备注
    private String remarks;
    
    // 单据类型
    private String bizType;
    
    private String zxCtSuppliesShopSettlementId;
    
    private List<ZxSkStockTransferInitialReceipt> zxCtSuppliesShopResSettleItemList;

    // 排序
    private int sort=0;

    public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getZxCtSuppliesShopSettlementId() {
		return zxCtSuppliesShopSettlementId;
	}

	public void setZxCtSuppliesShopSettlementId(String zxCtSuppliesShopSettlementId) {
		this.zxCtSuppliesShopSettlementId = zxCtSuppliesShopSettlementId;
	}

	public List<ZxSkStockTransferInitialReceipt> getZxCtSuppliesShopResSettleItemList() {
		return zxCtSuppliesShopResSettleItemList;
	}

	public void setZxCtSuppliesShopResSettleItemList(
			List<ZxSkStockTransferInitialReceipt> zxCtSuppliesShopResSettleItemList) {
		this.zxCtSuppliesShopResSettleItemList = zxCtSuppliesShopResSettleItemList;
	}

	public String getZxCtSuppliesShopResSettleItemId() {
        return zxCtSuppliesShopResSettleItemId == null ? "" : zxCtSuppliesShopResSettleItemId.trim();
    }

    public void setZxCtSuppliesShopResSettleItemId(String zxCtSuppliesShopResSettleItemId) {
        this.zxCtSuppliesShopResSettleItemId = zxCtSuppliesShopResSettleItemId == null ? null : zxCtSuppliesShopResSettleItemId.trim();
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

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
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

    public String getResID() {
        return resID == null ? "" : resID.trim();
    }

    public void setResID(String resID) {
        this.resID = resID == null ? null : resID.trim();
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

    public String getStockBillItemID() {
        return stockBillItemID == null ? "" : stockBillItemID.trim();
    }

    public void setStockBillItemID(String stockBillItemID) {
        this.stockBillItemID = stockBillItemID == null ? null : stockBillItemID.trim();
    }

    public String getStockBillNo() {
        return stockBillNo == null ? "" : stockBillNo.trim();
    }

    public void setStockBillNo(String stockBillNo) {
        this.stockBillNo = stockBillNo == null ? null : stockBillNo.trim();
    }

    public String getStockBillID() {
        return stockBillID == null ? "" : stockBillID.trim();
    }

    public void setStockBillID(String stockBillID) {
        this.stockBillID = stockBillID == null ? null : stockBillID.trim();
    }

    public String getPrecollecte() {
        return precollecte == null ? "" : precollecte.trim();
    }

    public void setPrecollecte(String precollecte) {
        this.precollecte = precollecte == null ? null : precollecte.trim();
    }

    public String getSignedOrderItemID() {
        return signedOrderItemID == null ? "" : signedOrderItemID.trim();
    }

    public void setSignedOrderItemID(String signedOrderItemID) {
        this.signedOrderItemID = signedOrderItemID == null ? null : signedOrderItemID.trim();
    }

    public String getSignedNo() {
        return signedNo == null ? "" : signedNo.trim();
    }

    public void setSignedNo(String signedNo) {
        this.signedNo = signedNo == null ? null : signedNo.trim();
    }

    public String getSignedOrderID() {
        return signedOrderID == null ? "" : signedOrderID.trim();
    }

    public void setSignedOrderID(String signedOrderID) {
        this.signedOrderID = signedOrderID == null ? null : signedOrderID.trim();
    }

    public String getPeriod() {
        return period == null ? "" : period.trim();
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getContrItemID() {
        return contrItemID == null ? "" : contrItemID.trim();
    }

    public void setContrItemID(String contrItemID) {
        this.contrItemID = contrItemID == null ? null : contrItemID.trim();
    }

    public String getContractID() {
        return contractID == null ? "" : contractID.trim();
    }

    public void setContractID(String contractID) {
        this.contractID = contractID == null ? null : contractID.trim();
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

    public BigDecimal getThisAmtTax() {
        return thisAmtTax;
    }

    public void setThisAmtTax(BigDecimal thisAmtTax) {
        this.thisAmtTax = thisAmtTax;
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

    public String getIsChoosePrice() {
        return isChoosePrice == null ? "" : isChoosePrice.trim();
    }

    public void setIsChoosePrice(String isChoosePrice) {
        this.isChoosePrice = isChoosePrice == null ? null : isChoosePrice.trim();
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
