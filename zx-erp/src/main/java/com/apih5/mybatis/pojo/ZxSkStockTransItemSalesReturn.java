package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZxSkStockTransItemSalesReturn extends BasePojo {
    private String zxSkStockTransItemSalesReturnId;

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

    private BigDecimal resAllFee;

    private String itemID;

    private BigDecimal inAmtAll;

    private BigDecimal inPriceNoTax;

    private BigDecimal resAllFeeNoTax;

    private BigDecimal resAllFeeTax;

    private BigDecimal ysFee;

    private BigDecimal inAmtTax;

    private String mainIdAndItemId;

    private String settlementStatus;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private String joinName;

    private int idNew;

    private BigDecimal maxNum;

    private BigDecimal isOutNumber;

    private String billNo;

    private BigDecimal isOutNumberStock;

    public BigDecimal getIsOutNumberStock() {
        return isOutNumberStock;
    }

    public void setIsOutNumberStock(BigDecimal isOutNumberStock) {
        this.isOutNumberStock = isOutNumberStock;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public BigDecimal getIsOutNumber() {
        return isOutNumber;
    }

    public void setIsOutNumber(BigDecimal isOutNumber) {
        this.isOutNumber = isOutNumber;
    }

    public ZxSkStockTransItemSalesReturn() { }

    public ZxSkStockTransItemSalesReturn(String resID, String resCode, String resName, String spec, String resUnit, BigDecimal inQty, BigDecimal inAmt) {
        this.resID = resID;
        this.resCode = resCode;
        this.resName = resName;
        this.spec = spec;
        this.resUnit = resUnit;
        this.inQty = inQty;
        this.inAmt = inAmt;
    }

    public BigDecimal getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(BigDecimal maxNum) {
        this.maxNum = maxNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIdNew() {
        return idNew;
    }

    public void setIdNew(int idNew) {
        this.idNew = idNew;
    }

    public String getJoinName() {
        return joinName;
    }

    public void setJoinName(String joinName) {
        this.joinName = joinName;
    }

    public String getZxSkStockTransItemSalesReturnId() {
        return zxSkStockTransItemSalesReturnId;
    }

    public void setZxSkStockTransItemSalesReturnId(String zxSkStockTransItemSalesReturnId) {
        this.zxSkStockTransItemSalesReturnId = zxSkStockTransItemSalesReturnId;
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

    public BigDecimal getResAllFee() {
        return resAllFee;
    }

    public void setResAllFee(BigDecimal resAllFee) {
        this.resAllFee = resAllFee;
    }

    public String getItemID() {
        return itemID == null ? "" : itemID.trim();
    }

    public void setItemID(String itemID) {
        this.itemID = itemID == null ? null : itemID.trim();
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

    public BigDecimal getYsFee() {
        return ysFee;
    }

    public void setYsFee(BigDecimal ysFee) {
        this.ysFee = ysFee;
    }

    public BigDecimal getInAmtTax() {
        return inAmtTax;
    }

    public void setInAmtTax(BigDecimal inAmtTax) {
        this.inAmtTax = inAmtTax;
    }

    public String getMainIdAndItemId() {
        return mainIdAndItemId == null ? "" : mainIdAndItemId.trim();
    }

    public void setMainIdAndItemId(String mainIdAndItemId) {
        this.mainIdAndItemId = mainIdAndItemId == null ? null : mainIdAndItemId.trim();
    }

    public String getSettlementStatus() {
        return settlementStatus == null ? "" : settlementStatus.trim();
    }

    public void setSettlementStatus(String settlementStatus) {
        this.settlementStatus = settlementStatus == null ? null : settlementStatus.trim();
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

