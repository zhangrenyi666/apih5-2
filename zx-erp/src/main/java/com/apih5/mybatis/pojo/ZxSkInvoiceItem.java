package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkInvoiceItem extends BasePojo {
    // 主键
    private String id;

    // 发票ID
    private String invoiceID;

    // 物资ID
    private String resID;

    // 冲账数量
    private BigDecimal qty;

    // 含税单价
    private BigDecimal unitPrice;

    // 其它费入账
    private BigDecimal otherExpense;

    // 入账金额
    private BigDecimal amt;

    // 其他费明细ID
    private String otherExpenseItemID;

    // 运输费明细ID
    private String ysFeeItemID;

    // 收料单其它费明细id
    private String ReceivingOtherExpenseItemID;

    // 收料单运输费明细id
    private String ReceivingYsFeeItemID;

    // 关联的合同ID
    private String contractID;

    // 预收物资明细ID
    private String stockTransItemID;

    // 采购单价
    private BigDecimal stdPrice;

    // 含税金额
    private BigDecimal stockAmt;

    // 预收单ID
    private String stockTransID;

    // 预收单号
    private String stockTransBillNo;

    // 税率(%)
    private String taxRate;

    // 是否抵扣
    private String isDeduct;

    // 不含税单价
    private BigDecimal unitPriceNoTax;

    // 不含税金额
    private BigDecimal stockAmtNoTax;

    // 税额
    private BigDecimal stockAmtTax;

    // 运输费入账
    private BigDecimal ysFee;

    // 总价
    private BigDecimal amtTotal;

    // 运输费总价
    private BigDecimal ysFeeTotal;

    // 其它费总价
    private BigDecimal otherExpenseTotal;

    // 预收数量
    private BigDecimal tempQty;

    // 物资编码
    private String resCode;

    // 物资名称
    private String resName;

    // 物资规格
    private String resSpec;

    // 计量单位
    private String resUnit;

    // 原含税单价
    private BigDecimal oldInPrice;

    // 原入账金额
    private BigDecimal oldAmt;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    //冲账金额
    private BigDecimal money;

    private BigDecimal oldQty;

    public ZxSkInvoiceItem() {
    }

    public ZxSkInvoiceItem(String stockTransBillNo) {
        this.stockTransBillNo = stockTransBillNo;
    }

    public BigDecimal getOldQty() {
        return oldQty;
    }

    public void setOldQty(BigDecimal oldQty) {
        this.oldQty = oldQty;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getInvoiceID() {
        return invoiceID == null ? "" : invoiceID.trim();
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID == null ? null : invoiceID.trim();
    }

    public String getResID() {
        return resID == null ? "" : resID.trim();
    }

    public void setResID(String resID) {
        this.resID = resID == null ? null : resID.trim();
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getOtherExpense() {
        return otherExpense;
    }

    public void setOtherExpense(BigDecimal otherExpense) {
        this.otherExpense = otherExpense;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getOtherExpenseItemID() {
        return otherExpenseItemID == null ? "" : otherExpenseItemID.trim();
    }

    public void setOtherExpenseItemID(String otherExpenseItemID) {
        this.otherExpenseItemID = otherExpenseItemID == null ? null : otherExpenseItemID.trim();
    }

    public String getYsFeeItemID() {
        return ysFeeItemID == null ? "" : ysFeeItemID.trim();
    }

    public void setYsFeeItemID(String ysFeeItemID) {
        this.ysFeeItemID = ysFeeItemID == null ? null : ysFeeItemID.trim();
    }

    public String getReceivingOtherExpenseItemID() {
        return ReceivingOtherExpenseItemID == null ? "" : ReceivingOtherExpenseItemID.trim();
    }

    public void setReceivingOtherExpenseItemID(String ReceivingOtherExpenseItemID) {
        this.ReceivingOtherExpenseItemID = ReceivingOtherExpenseItemID == null ? null : ReceivingOtherExpenseItemID.trim();
    }

    public String getReceivingYsFeeItemID() {
        return ReceivingYsFeeItemID == null ? "" : ReceivingYsFeeItemID.trim();
    }

    public void setReceivingYsFeeItemID(String ReceivingYsFeeItemID) {
        this.ReceivingYsFeeItemID = ReceivingYsFeeItemID == null ? null : ReceivingYsFeeItemID.trim();
    }

    public String getContractID() {
        return contractID == null ? "" : contractID.trim();
    }

    public void setContractID(String contractID) {
        this.contractID = contractID == null ? null : contractID.trim();
    }

    public String getStockTransItemID() {
        return stockTransItemID == null ? "" : stockTransItemID.trim();
    }

    public void setStockTransItemID(String stockTransItemID) {
        this.stockTransItemID = stockTransItemID == null ? null : stockTransItemID.trim();
    }

    public BigDecimal getStdPrice() {
        return stdPrice;
    }

    public void setStdPrice(BigDecimal stdPrice) {
        this.stdPrice = stdPrice;
    }

    public BigDecimal getStockAmt() {
        return stockAmt;
    }

    public void setStockAmt(BigDecimal stockAmt) {
        this.stockAmt = stockAmt;
    }

    public String getStockTransID() {
        return stockTransID == null ? "" : stockTransID.trim();
    }

    public void setStockTransID(String stockTransID) {
        this.stockTransID = stockTransID == null ? null : stockTransID.trim();
    }

    public String getStockTransBillNo() {
        return stockTransBillNo == null ? "" : stockTransBillNo.trim();
    }

    public void setStockTransBillNo(String stockTransBillNo) {
        this.stockTransBillNo = stockTransBillNo == null ? null : stockTransBillNo.trim();
    }

    public String getTaxRate() {
        return taxRate == null ? "" : taxRate.trim();
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate == null ? null : taxRate.trim();
    }

    public String getIsDeduct() {
        return isDeduct == null ? "" : isDeduct.trim();
    }

    public void setIsDeduct(String isDeduct) {
        this.isDeduct = isDeduct == null ? null : isDeduct.trim();
    }

    public BigDecimal getUnitPriceNoTax() {
        return unitPriceNoTax;
    }

    public void setUnitPriceNoTax(BigDecimal unitPriceNoTax) {
        this.unitPriceNoTax = unitPriceNoTax;
    }

    public BigDecimal getStockAmtNoTax() {
        return stockAmtNoTax;
    }

    public void setStockAmtNoTax(BigDecimal stockAmtNoTax) {
        this.stockAmtNoTax = stockAmtNoTax;
    }

    public BigDecimal getStockAmtTax() {
        return stockAmtTax;
    }

    public void setStockAmtTax(BigDecimal stockAmtTax) {
        this.stockAmtTax = stockAmtTax;
    }

    public BigDecimal getYsFee() {
        return ysFee;
    }

    public void setYsFee(BigDecimal ysFee) {
        this.ysFee = ysFee;
    }

    public BigDecimal getAmtTotal() {
        return amtTotal;
    }

    public void setAmtTotal(BigDecimal amtTotal) {
        this.amtTotal = amtTotal;
    }

    public BigDecimal getYsFeeTotal() {
        return ysFeeTotal;
    }

    public void setYsFeeTotal(BigDecimal ysFeeTotal) {
        this.ysFeeTotal = ysFeeTotal;
    }

    public BigDecimal getOtherExpenseTotal() {
        return otherExpenseTotal;
    }

    public void setOtherExpenseTotal(BigDecimal otherExpenseTotal) {
        this.otherExpenseTotal = otherExpenseTotal;
    }

    public BigDecimal getTempQty() {
        return tempQty;
    }

    public void setTempQty(BigDecimal tempQty) {
        this.tempQty = tempQty;
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

    public String getResSpec() {
        return resSpec == null ? "" : resSpec.trim();
    }

    public void setResSpec(String resSpec) {
        this.resSpec = resSpec == null ? null : resSpec.trim();
    }

    public String getResUnit() {
        return resUnit == null ? "" : resUnit.trim();
    }

    public void setResUnit(String resUnit) {
        this.resUnit = resUnit == null ? null : resUnit.trim();
    }

    public BigDecimal getOldInPrice() {
        return oldInPrice;
    }

    public void setOldInPrice(BigDecimal oldInPrice) {
        this.oldInPrice = oldInPrice;
    }

    public BigDecimal getoldAmt() {
        return oldAmt;
    }

    public void setoldAmt(BigDecimal oldAmt) {
        this.oldAmt = oldAmt;
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
