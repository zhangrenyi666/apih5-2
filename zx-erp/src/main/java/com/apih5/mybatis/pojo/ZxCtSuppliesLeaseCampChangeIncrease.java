package com.apih5.mybatis.pojo;

import java.math.BigDecimal;

public class ZxCtSuppliesLeaseCampChangeIncrease {

	//项目名称
    private String orgName;
    //合同编号
    private String contractNo;
    //签认单编号
    private String signedNo;
    //结算单编号
    private String billNo;
    //结算期次
    private String period;
    //日期
    private String modifyTimeStr;    
    //物资大类
    private String workType;
    //物资编码
    private String workNo;
    //物资名称
    private String workName;
    //规格型号
    private String spec;
    //单位
    private String unit;
    //租期单位
    private String rentUnit;
    //含税合同单价（变更后）
    private String changePrice;
    //不含税合同单价（变更后）
    private String changePriceNoTax;
    //税金
    private String tax;
    //数量
    private String qty;
    //租期
    private String contrTrrm;
    //含税合同金额
    private String contractSum;
    //不含税合同金额
    private String contractSumNoTax;
    //税额
    private String contractTax;
    //变更后数量
    private String changeQty;
    //租期
    private String alterTrrm;
    //变更后含税合同金额
    private String changeContractSum;
    //变更后不含税合同金额
    private String changeContractSumNoTax;
    //变更后合同税额
    private String changeContractTax;
    //数量*租期（本期）
    private String signThisAmt;
    //含税金额（本期）
    private String thisAmt;
    //不含税金额（本期）
    private String thisAmtNoTax;
    //税额（本期）
    private String thisAmtTax;
    //数量*租期（至上期）
    private String upQty;
    //含税金额（至上期）
    private String upAmt;
    //不含税金额（至上期）
    private String upAmtNoTax;
    //税额（至上期）
    private String upAmtTax;
    //数量*租期（本期）
    private String totalQty;
    //含税金额（本期）
    private String totalAmt;
    //不含税金额（本期）
    private String totalAmtNoTax;
    //税额（本期）
    private String totalAmtTax;
    //税率
    private String taxRate;
    //是否抵扣
    private String isDeduct;
    //备注
    private String remark;
    //含税单价（采购）
    private String price;
    //不含税单价（采购）
    private String priceNoTax;
    //变更后税金（采购）
    private String changeTax;
    //含税平均单价（本期）
    private String thisPrice;
    //不含税平均单价（本期）
    private String thisPriceNoTax;
    //税金（本期）
    private String thisTax;
    //含税平均单价（至上期）
    private String upPrice;
    //不含税平均单价（至上期）
    private String upPriceNoTax;
    //税金（至上期）
    private String upTax;    
    //含税平均单价（至本期）
    private String totalPrice;
    //不含税平均单价（至本期）
    private String totalNoTax;
    //税金（至本期）
    private String totalTax;
    //收料单/退货单编号
    private String stockBillNo;    
    //本期运杂费
    private String transportAmt;
    //本期数量（采购）
    private String thisQty;   
    //本期其他款项
    private String otherAmt;   
    //本期奖罚金
    private String fineAmt;    
    //本期垫资费
    private String padTariffAmt;
    //序号
    private String firstNum;    
    //数量小计
    private String qtySubtotal;    
    //金额小计
    private String amtSubtotal;    
    //数量合计
    private String qtyTotal;    
    //金额合计
    private String amtTotal;
    //合同ID
    private String contractID;
    //物资ID
    private String resID;    
    
	public String getResID() {
		return resID;
	}

	public void setResID(String resID) {
		this.resID = resID;
	}

	public String getContractID() {
		return contractID;
	}

	public void setContractID(String contractID) {
		this.contractID = contractID;
	}

	public String getThisQty() {
		return thisQty;
	}

	public void setThisQty(String thisQty) {
		this.thisQty = thisQty;
	}

	public String getQtySubtotal() {
		return qtySubtotal;
	}

	public void setQtySubtotal(String qtySubtotal) {
		this.qtySubtotal = qtySubtotal;
	}

	public String getAmtSubtotal() {
		return amtSubtotal;
	}

	public void setAmtSubtotal(String amtSubtotal) {
		this.amtSubtotal = amtSubtotal;
	}

	public String getQtyTotal() {
		return qtyTotal;
	}

	public void setQtyTotal(String qtyTotal) {
		this.qtyTotal = qtyTotal;
	}

	public String getAmtTotal() {
		return amtTotal;
	}

	public void setAmtTotal(String amtTotal) {
		this.amtTotal = amtTotal;
	}

	public String getFirstNum() {
		return firstNum;
	}

	public void setFirstNum(String firstNum) {
		this.firstNum = firstNum;
	}

	public String getTransportAmt() {
		return transportAmt;
	}

	public void setTransportAmt(String transportAmt) {
		this.transportAmt = transportAmt;
	}

	public String getOtherAmt() {
		return otherAmt;
	}

	public void setOtherAmt(String otherAmt) {
		this.otherAmt = otherAmt;
	}

	public String getFineAmt() {
		return fineAmt;
	}

	public void setFineAmt(String fineAmt) {
		this.fineAmt = fineAmt;
	}

	public String getPadTariffAmt() {
		return padTariffAmt;
	}

	public void setPadTariffAmt(String padTariffAmt) {
		this.padTariffAmt = padTariffAmt;
	}

	public String getStockBillNo() {
		return stockBillNo;
	}

	public void setStockBillNo(String stockBillNo) {
		this.stockBillNo = stockBillNo;
	}

	public String getModifyTimeStr() {
		return modifyTimeStr;
	}

	public void setModifyTimeStr(String modifyTimeStr) {
		this.modifyTimeStr = modifyTimeStr;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getSignedNo() {
		return signedNo;
	}

	public void setSignedNo(String signedNo) {
		this.signedNo = signedNo;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPriceNoTax() {
		return priceNoTax;
	}

	public void setPriceNoTax(String priceNoTax) {
		this.priceNoTax = priceNoTax;
	}

	public String getChangeTax() {
		return changeTax;
	}

	public void setChangeTax(String changeTax) {
		this.changeTax = changeTax;
	}

	public String getThisPrice() {
		return thisPrice;
	}

	public void setThisPrice(String thisPrice) {
		this.thisPrice = thisPrice;
	}

	public String getThisPriceNoTax() {
		return thisPriceNoTax;
	}

	public void setThisPriceNoTax(String thisPriceNoTax) {
		this.thisPriceNoTax = thisPriceNoTax;
	}

	public String getThisTax() {
		return thisTax;
	}

	public void setThisTax(String thisTax) {
		this.thisTax = thisTax;
	}

	public String getUpPrice() {
		return upPrice;
	}

	public void setUpPrice(String upPrice) {
		this.upPrice = upPrice;
	}

	public String getUpPriceNoTax() {
		return upPriceNoTax;
	}

	public void setUpPriceNoTax(String upPriceNoTax) {
		this.upPriceNoTax = upPriceNoTax;
	}

	public String getUpTax() {
		return upTax;
	}

	public void setUpTax(String upTax) {
		this.upTax = upTax;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getTotalNoTax() {
		return totalNoTax;
	}

	public void setTotalNoTax(String totalNoTax) {
		this.totalNoTax = totalNoTax;
	}

	public String getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(String totalTax) {
		this.totalTax = totalTax;
	}

	public String getIsDeduct() {
		return isDeduct;
	}

	public void setIsDeduct(String isDeduct) {
		this.isDeduct = isDeduct;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getWorkNo() {
		return workNo;
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
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

	public String getChangePrice() {
		return changePrice;
	}

	public void setChangePrice(String changePrice) {
		this.changePrice = changePrice;
	}

	public String getChangePriceNoTax() {
		return changePriceNoTax;
	}

	public void setChangePriceNoTax(String changePriceNoTax) {
		this.changePriceNoTax = changePriceNoTax;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getContrTrrm() {
		return contrTrrm;
	}

	public void setContrTrrm(String contrTrrm) {
		this.contrTrrm = contrTrrm;
	}

	public String getContractSum() {
		return contractSum;
	}

	public void setContractSum(String contractSum) {
		this.contractSum = contractSum;
	}

	public String getContractSumNoTax() {
		return contractSumNoTax;
	}

	public void setContractSumNoTax(String contractSumNoTax) {
		this.contractSumNoTax = contractSumNoTax;
	}

	public String getContractTax() {
		return contractTax;
	}

	public void setContractTax(String contractTax) {
		this.contractTax = contractTax;
	}

	public String getChangeQty() {
		return changeQty;
	}

	public void setChangeQty(String changeQty) {
		this.changeQty = changeQty;
	}

	public String getAlterTrrm() {
		return alterTrrm;
	}

	public void setAlterTrrm(String alterTrrm) {
		this.alterTrrm = alterTrrm;
	}

	public String getChangeContractSum() {
		return changeContractSum;
	}

	public void setChangeContractSum(String changeContractSum) {
		this.changeContractSum = changeContractSum;
	}

	public String getChangeContractSumNoTax() {
		return changeContractSumNoTax;
	}

	public void setChangeContractSumNoTax(String changeContractSumNoTax) {
		this.changeContractSumNoTax = changeContractSumNoTax;
	}

	public String getChangeContractTax() {
		return changeContractTax;
	}

	public void setChangeContractTax(String changeContractTax) {
		this.changeContractTax = changeContractTax;
	}

	public String getSignThisAmt() {
		return signThisAmt;
	}

	public void setSignThisAmt(String signThisAmt) {
		this.signThisAmt = signThisAmt;
	}

	public String getThisAmt() {
		return thisAmt;
	}

	public void setThisAmt(String thisAmt) {
		this.thisAmt = thisAmt;
	}

	public String getThisAmtNoTax() {
		return thisAmtNoTax;
	}

	public void setThisAmtNoTax(String thisAmtNoTax) {
		this.thisAmtNoTax = thisAmtNoTax;
	}

	public String getThisAmtTax() {
		return thisAmtTax;
	}

	public void setThisAmtTax(String thisAmtTax) {
		this.thisAmtTax = thisAmtTax;
	}

	public String getUpQty() {
		return upQty;
	}

	public void setUpQty(String upQty) {
		this.upQty = upQty;
	}

	public String getUpAmt() {
		return upAmt;
	}

	public void setUpAmt(String upAmt) {
		this.upAmt = upAmt;
	}

	public String getUpAmtNoTax() {
		return upAmtNoTax;
	}

	public void setUpAmtNoTax(String upAmtNoTax) {
		this.upAmtNoTax = upAmtNoTax;
	}

	public String getUpAmtTax() {
		return upAmtTax;
	}

	public void setUpAmtTax(String upAmtTax) {
		this.upAmtTax = upAmtTax;
	}

	public String getTotalQty() {
		return totalQty;
	}

	public void setTotalQty(String totalQty) {
		this.totalQty = totalQty;
	}

	public String getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}

	public String getTotalAmtNoTax() {
		return totalAmtNoTax;
	}

	public void setTotalAmtNoTax(String totalAmtNoTax) {
		this.totalAmtNoTax = totalAmtNoTax;
	}

	public String getTotalAmtTax() {
		return totalAmtTax;
	}

	public void setTotalAmtTax(String totalAmtTax) {
		this.totalAmtTax = totalAmtTax;
	}

	public String getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate;
	}

	public String getRentUnit() {
		return rentUnit;
	}

	public void setRentUnit(String rentUnit) {
		this.rentUnit = rentUnit;
	}

}
