package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZxSkStockTransItemTransferOrder extends BasePojo {
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

    private String workName;

    private BigDecimal outQty;

    private BigDecimal outFee;

    private BigDecimal outCostPrice;

    private BigDecimal outCostAmt;

    private BigDecimal outPrice;

    private BigDecimal outAmt;

    private Integer isDepreciation;

    private String remark;

    private BigDecimal shouldQty;

    private BigDecimal transOutAmt;

    private BigDecimal inAmtAll;

    private BigDecimal inAmtNoTax;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public ZxSkStockTransItemTransferOrder() { }

    public ZxSkStockTransItemTransferOrder(String resID, String resCode, String resName, String spec, String resUnit, BigDecimal stdPrice, BigDecimal outQty, BigDecimal outAmt) {
        this.resID = resID;
        this.resCode = resCode;
        this.resName = resName;
        this.spec = spec;
        this.resUnit = resUnit;
        this.stdPrice = stdPrice;
        this.outQty = outQty;
        this.outAmt = outAmt;
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

    public BigDecimal getShouldQty() {
        return shouldQty;
    }

    public void setShouldQty(BigDecimal shouldQty) {
        this.shouldQty = shouldQty;
    }

    public BigDecimal getTransOutAmt() {
        return transOutAmt;
    }

    public void setTransOutAmt(BigDecimal transOutAmt) {
        this.transOutAmt = transOutAmt;
    }

    public BigDecimal getInAmtAll() {
        return inAmtAll;
    }

    public void setInAmtAll(BigDecimal inAmtAll) {
        this.inAmtAll = inAmtAll;
    }

    public BigDecimal getInAmtNoTax() {
        return inAmtNoTax;
    }

    public void setInAmtNoTax(BigDecimal inAmtNoTax) {
        this.inAmtNoTax = inAmtNoTax;
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

