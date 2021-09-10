package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZxSkStockTransItemReceiving extends BasePojo {
    private String id;

    private String billID;

    private String resID;

    private String batchNo;

    private String resCode;

    private String resName;

    private String spec;

    private String resUnit;

    private BigDecimal stdPrice;

    private BigDecimal inFee;

    private BigDecimal inQty;

    private BigDecimal inPrice;

    private BigDecimal oldInPrice;

    private BigDecimal inAmt;

    private String muID;

    private String workID;

    private String muName;

    private String workName;

    private BigDecimal outQty;

    private BigDecimal outFee;

    private BigDecimal outCostPrice;

    private BigDecimal outCostAmt;

    private BigDecimal outPrice;

    private BigDecimal outAmt;

    private Integer isDepreciation;

    private String remark;

    private String materialsExpensesDetailID ;

    private String materialsTransportationDetailID ;

    private BigDecimal shouldQty;

    private String qualityNo;

    private String precollecte;

    private BigDecimal resAllFee;

    private BigDecimal invoiceCumulateQty;

    private BigDecimal invoiceCumulateAmt;

    private BigDecimal inFeeTotal;

    private BigDecimal ysFeeTotal;

    private BigDecimal inAmtAll;

    private BigDecimal inPriceNoTax;

    private BigDecimal resAllFeeNoTax;

    private BigDecimal resAllFeeTax;

    private BigDecimal inAmtNoTax;

    private BigDecimal ysFee;

    private String settlementStatus;

    private BigDecimal isOutNumber;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private String stockTransItemID;

    //税率
    private String taxRate;

    //单据编号
    private String stockTransBillNo;

    private BigDecimal amtTotal;

    private BigDecimal amt;

    private BigDecimal stockAmt;

    private BigDecimal unitPrice;

    private BigDecimal stockAmtTax;

    private BigDecimal stockAmtNoTax;

    private BigDecimal unitPriceNoTax;

    private BigDecimal qty;

    private BigDecimal tempQty;

    private String isDeduct;

    private String stockTransID;

    private String resSpec;

    private String oldAmt;

    private BigDecimal oldQty;

    private String bizType;

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public ZxSkStockTransItemReceiving() {}

    public ZxSkStockTransItemReceiving(String resID, String resCode, String resName, String spec, String resUnit, BigDecimal inQty, BigDecimal inAmt) {
        this.resID = resID;
        this.resCode = resCode;
        this.resName = resName;
        this.spec = spec;
        this.resUnit = resUnit;
        this.inQty = inQty;
        this.inAmt = inAmt;
    }

    public BigDecimal getOldQty() {
        return oldQty;
    }

    public void setOldQty(BigDecimal oldQty) {
        this.oldQty = oldQty;
    }

    public String getOldAmt() {
        return oldAmt;
    }

    public void setOldAmt(String oldAmt) {
        this.oldAmt = oldAmt;
    }

    public BigDecimal getOldInPrice() {
        return oldInPrice;
    }

    public void setOldInPrice(BigDecimal oldInPrice) {
        this.oldInPrice = oldInPrice;
    }

    public String getResSpec() {
        return resSpec;
    }

    public void setResSpec(String resSpec) {
        this.resSpec = resSpec;
    }

    public BigDecimal getTempQty() {
        return tempQty;
    }

    public void setTempQty(BigDecimal tempQty) {
        this.tempQty = tempQty;
    }

    public String getStockTransID() {
        return stockTransID;
    }

    public void setStockTransID(String stockTransID) {
        this.stockTransID = stockTransID;
    }

    public String getIsDeduct() {
        return isDeduct;
    }

    public void setIsDeduct(String isDeduct) {
        this.isDeduct = isDeduct;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public String getStockTransItemID() {
        return stockTransItemID;
    }

    public void setStockTransItemID(String stockTransItemID) {
        this.stockTransItemID = stockTransItemID;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getStockTransBillNo() {
        return stockTransBillNo;
    }

    public void setStockTransBillNo(String stockTransBillNo) {
        this.stockTransBillNo = stockTransBillNo;
    }

    public BigDecimal getAmtTotal() {
        return amtTotal;
    }

    public void setAmtTotal(BigDecimal amtTotal) {
        this.amtTotal = amtTotal;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public BigDecimal getStockAmt() {
        return stockAmt;
    }

    public void setStockAmt(BigDecimal stockAmt) {
        this.stockAmt = stockAmt;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getStockAmtTax() {
        return stockAmtTax;
    }

    public void setStockAmtTax(BigDecimal stockAmtTax) {
        this.stockAmtTax = stockAmtTax;
    }

    public BigDecimal getStockAmtNoTax() {
        return stockAmtNoTax;
    }

    public void setStockAmtNoTax(BigDecimal stockAmtNoTax) {
        this.stockAmtNoTax = stockAmtNoTax;
    }

    public BigDecimal getUnitPriceNoTax() {
        return unitPriceNoTax;
    }

    public void setUnitPriceNoTax(BigDecimal unitPriceNoTax) {
        this.unitPriceNoTax = unitPriceNoTax;
    }

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBillID() {
        return billID == null ? "" : billID.trim();
    }

    public void setBillID(String billID) {
        this.billID = billID == null ? null : billID.trim();
    }

    public String getResID() {
        return resID == null ? "" : resID.trim();
    }

    public void setResID(String resID) {
        this.resID = resID == null ? null : resID.trim();
    }

    public String getBatchNo() {
        return batchNo == null ? "" : batchNo.trim();
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
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

    public BigDecimal getStdPrice() {
        return stdPrice;
    }

    public void setStdPrice(BigDecimal stdPrice) {
        this.stdPrice = stdPrice;
    }

    public BigDecimal getInFee() {
        return inFee;
    }

    public void setInFee(BigDecimal inFee) {
        this.inFee = inFee;
    }

    public BigDecimal getInQty() {
        return inQty;
    }

    public void setInQty(BigDecimal inQty) {
        this.inQty = inQty;
    }

    public BigDecimal getInPrice() {
        return inPrice;
    }

    public void setInPrice(BigDecimal inPrice) {
        this.inPrice = inPrice;
    }

    public BigDecimal getInAmt() {
        return inAmt;
    }

    public void setInAmt(BigDecimal inAmt) {
        this.inAmt = inAmt;
    }

    public String getMuID() {
        return muID == null ? "" : muID.trim();
    }

    public void setMuID(String muID) {
        this.muID = muID == null ? null : muID.trim();
    }

    public String getWorkID() {
        return workID == null ? "" : workID.trim();
    }

    public void setWorkID(String workID) {
        this.workID = workID == null ? null : workID.trim();
    }

    public String getMuName() {
        return muName == null ? "" : muName.trim();
    }

    public void setMuName(String muName) {
        this.muName = muName == null ? null : muName.trim();
    }

    public String getWorkName() {
        return workName == null ? "" : workName.trim();
    }

    public void setWorkName(String workName) {
        this.workName = workName == null ? null : workName.trim();
    }

    public BigDecimal getOutQty() {
        return outQty;
    }

    public void setOutQty(BigDecimal outQty) {
        this.outQty = outQty;
    }

    public BigDecimal getOutFee() {
        return outFee;
    }

    public void setOutFee(BigDecimal outFee) {
        this.outFee = outFee;
    }

    public BigDecimal getOutCostPrice() {
        return outCostPrice;
    }

    public void setOutCostPrice(BigDecimal outCostPrice) {
        this.outCostPrice = outCostPrice;
    }

    public BigDecimal getOutCostAmt() {
        return outCostAmt;
    }

    public void setOutCostAmt(BigDecimal outCostAmt) {
        this.outCostAmt = outCostAmt;
    }

    public BigDecimal getOutPrice() {
        return outPrice;
    }

    public void setOutPrice(BigDecimal outPrice) {
        this.outPrice = outPrice;
    }

    public BigDecimal getOutAmt() {
        return outAmt;
    }

    public void setOutAmt(BigDecimal outAmt) {
        this.outAmt = outAmt;
    }

    public Integer getIsDepreciation() {
        return isDepreciation;
    }

    public void setIsDepreciation(Integer isDepreciation) {
        this.isDepreciation = isDepreciation;
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getMaterialsExpensesDetailID () {
        return materialsExpensesDetailID  == null ? "" : materialsExpensesDetailID .trim();
    }

    public void setMaterialsExpensesDetailID (String materialsExpensesDetailID ) {
        this.materialsExpensesDetailID  = materialsExpensesDetailID  == null ? null : materialsExpensesDetailID .trim();
    }

    public String getMaterialsTransportationDetailID () {
        return materialsTransportationDetailID  == null ? "" : materialsTransportationDetailID .trim();
    }

    public void setMaterialsTransportationDetailID (String materialsTransportationDetailID ) {
        this.materialsTransportationDetailID  = materialsTransportationDetailID  == null ? null : materialsTransportationDetailID .trim();
    }

    public BigDecimal getShouldQty() {
        return shouldQty;
    }

    public void setShouldQty(BigDecimal shouldQty) {
        this.shouldQty = shouldQty;
    }

    public String getQualityNo() {
        return qualityNo == null ? "" : qualityNo.trim();
    }

    public void setQualityNo(String qualityNo) {
        this.qualityNo = qualityNo == null ? null : qualityNo.trim();
    }

    public String getPrecollecte() {
        return precollecte == null ? "" : precollecte.trim();
    }

    public void setPrecollecte(String precollecte) {
        this.precollecte = precollecte == null ? null : precollecte.trim();
    }

    public BigDecimal getResAllFee() {
        return resAllFee;
    }

    public void setResAllFee(BigDecimal resAllFee) {
        this.resAllFee = resAllFee;
    }

    public BigDecimal getInvoiceCumulateQty() {
        return invoiceCumulateQty;
    }

    public void setInvoiceCumulateQty(BigDecimal invoiceCumulateQty) {
        this.invoiceCumulateQty = invoiceCumulateQty;
    }

    public BigDecimal getInvoiceCumulateAmt() {
        return invoiceCumulateAmt;
    }

    public void setInvoiceCumulateAmt(BigDecimal invoiceCumulateAmt) {
        this.invoiceCumulateAmt = invoiceCumulateAmt;
    }

    public BigDecimal getInFeeTotal() {
        return inFeeTotal;
    }

    public void setInFeeTotal(BigDecimal inFeeTotal) {
        this.inFeeTotal = inFeeTotal;
    }

    public BigDecimal getYsFeeTotal() {
        return ysFeeTotal;
    }

    public void setYsFeeTotal(BigDecimal ysFeeTotal) {
        this.ysFeeTotal = ysFeeTotal;
    }

    public BigDecimal getInAmtAll() {
        return inAmtAll;
    }

    public void setInAmtAll(BigDecimal inAmtAll) {
        this.inAmtAll = inAmtAll;
    }

    public BigDecimal getInPriceNoTax() {
        return inPriceNoTax;
    }

    public void setInPriceNoTax(BigDecimal inPriceNoTax) {
        this.inPriceNoTax = inPriceNoTax;
    }

    public BigDecimal getResAllFeeNoTax() {
        return resAllFeeNoTax;
    }

    public void setResAllFeeNoTax(BigDecimal resAllFeeNoTax) {
        this.resAllFeeNoTax = resAllFeeNoTax;
    }

    public BigDecimal getResAllFeeTax() {
        return resAllFeeTax;
    }

    public void setResAllFeeTax(BigDecimal resAllFeeTax) {
        this.resAllFeeTax = resAllFeeTax;
    }

    public BigDecimal getInAmtNoTax() {
        return inAmtNoTax;
    }

    public void setInAmtNoTax(BigDecimal inAmtNoTax) {
        this.inAmtNoTax = inAmtNoTax;
    }

    public BigDecimal getYsFee() {
        return ysFee;
    }

    public void setYsFee(BigDecimal ysFee) {
        this.ysFee = ysFee;
    }

    public String getSettlementStatus() {
        return settlementStatus == null ? "" : settlementStatus.trim();
    }

    public void setSettlementStatus(String settlementStatus) {
        this.settlementStatus = settlementStatus == null ? null : settlementStatus.trim();
    }

    public BigDecimal getIsOutNumber() {
        return isOutNumber;
    }

    public void setIsOutNumber(BigDecimal isOutNumber) {
        this.isOutNumber = isOutNumber;
    }

    public String getDelFlag() {
        return delFlag == null ? "" : delFlag.trim();
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser == null ? "" : createUser.trim();
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getCreateUserName() {
        return createUserName == null ? "" : createUserName.trim();
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser == null ? "" : modifyUser.trim();
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    public String getModifyUserName() {
        return modifyUserName == null ? "" : modifyUserName.trim();
    }

    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName == null ? null : modifyUserName.trim();
    }

}

