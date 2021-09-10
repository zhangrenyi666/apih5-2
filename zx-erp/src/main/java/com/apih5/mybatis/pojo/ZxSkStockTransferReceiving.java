package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZxSkStockTransferReceiving extends BasePojo {
    private String id;

    private String orgID;

    private String whOrgID;

    private String outOrgID;

    private String inOrgID;

    private String bizType;

    private String billNo;

    private String materialSource;

    private String purpose;

    private Date busDate;

    private String outOrgName;

    private String inOrgName;

    private BigDecimal totalAmt;

    private String outtransactor;

    private String intransactor;

    private String waretransactor;

    private String buyer;

    private String consignee;

    private String auditor;

    private String voucherNo;

    private String contractNo;

    private String invoiceNo;

    private Integer billType;

    private String billFlag;

    private String billStatus;

    private String reporter;

    private String deductAmtType;

    private String remark;

    private String combProp;

    private String purchaseContractID;

    private String purchaseContract;

    private String loginOrgID;

    private String resourceID;

    private String resourceName;

    private String cbsID;

    private String cbsName;

    private BigDecimal invoiceNum;

    private String outOrgType;

    private String purchType;

    private String taxRate;

    private String isDeduct;

    private String warehouseName;

    private String orNotPrecollecte;

    private String companyId;

    private String companyName;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZxSkStockTransItemReceiving> zxSkStockTransItemReceivingList;

    private String stockTransID;

    private List<Date> timeList;

    private String time;
    
    private String isSelect;
    
    private String mainID;//结算表主键

    //开始时间
    private Date startTime;

    //结束时间
    private Date endTime;

    private List<BigDecimal> amtList;

    private String orgID2;

    public String getOrgID2() {
        return orgID2;
    }

    public void setOrgID2(String orgID2) {
        this.orgID2 = orgID2;
    }

    public List<BigDecimal> getAmtList() {
        return amtList;
    }

    public void setAmtList(List<BigDecimal> amtList) {
        this.amtList = amtList;
    }

    public String getIsSelect() {
		return isSelect;
	}

	public String getMainID() {
		return mainID;
	}

	public void setMainID(String mainID) {
		this.mainID = mainID;
	}
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

	public void setIsSelect(String isSelect) {
		this.isSelect = isSelect;
	}

	public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Date> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<Date> timeList) {
        this.timeList = timeList;
    }

    public String getStockTransID() {
        return stockTransID;
    }

    public void setStockTransID(String stockTransID) {
        this.stockTransID = stockTransID;
    }

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getWhOrgID() {
        return whOrgID == null ? "" : whOrgID.trim();
    }

    public void setWhOrgID(String whOrgID) {
        this.whOrgID = whOrgID == null ? null : whOrgID.trim();
    }

    public String getOutOrgID() {
        return outOrgID == null ? "" : outOrgID.trim();
    }

    public void setOutOrgID(String outOrgID) {
        this.outOrgID = outOrgID == null ? null : outOrgID.trim();
    }

    public String getInOrgID() {
        return inOrgID == null ? "" : inOrgID.trim();
    }

    public void setInOrgID(String inOrgID) {
        this.inOrgID = inOrgID == null ? null : inOrgID.trim();
    }

    public String getBizType() {
        return bizType == null ? "" : bizType.trim();
    }

    public void setBizType(String bizType) {
        this.bizType = bizType == null ? null : bizType.trim();
    }

    public String getBillNo() {
        return billNo == null ? "" : billNo.trim();
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public String getMaterialSource() {
        return materialSource == null ? "" : materialSource.trim();
    }

    public void setMaterialSource(String materialSource) {
        this.materialSource = materialSource == null ? null : materialSource.trim();
    }

    public String getPurpose() {
        return purpose == null ? "" : purpose.trim();
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose == null ? null : purpose.trim();
    }

    public Date getBusDate() {
        return busDate;
    }

    public void setBusDate(Date busDate) {
        this.busDate = busDate;
    }

    public String getOutOrgName() {
        return outOrgName == null ? "" : outOrgName.trim();
    }

    public void setOutOrgName(String outOrgName) {
        this.outOrgName = outOrgName == null ? null : outOrgName.trim();
    }

    public String getInOrgName() {
        return inOrgName == null ? "" : inOrgName.trim();
    }

    public void setInOrgName(String inOrgName) {
        this.inOrgName = inOrgName == null ? null : inOrgName.trim();
    }

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getOuttransactor() {
        return outtransactor == null ? "" : outtransactor.trim();
    }

    public void setOuttransactor(String outtransactor) {
        this.outtransactor = outtransactor == null ? null : outtransactor.trim();
    }

    public String getIntransactor() {
        return intransactor == null ? "" : intransactor.trim();
    }

    public void setIntransactor(String intransactor) {
        this.intransactor = intransactor == null ? null : intransactor.trim();
    }

    public String getWaretransactor() {
        return waretransactor == null ? "" : waretransactor.trim();
    }

    public void setWaretransactor(String waretransactor) {
        this.waretransactor = waretransactor == null ? null : waretransactor.trim();
    }

    public String getBuyer() {
        return buyer == null ? "" : buyer.trim();
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer == null ? null : buyer.trim();
    }

    public String getConsignee() {
        return consignee == null ? "" : consignee.trim();
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee == null ? null : consignee.trim();
    }

    public String getAuditor() {
        return auditor == null ? "" : auditor.trim();
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    public String getVoucherNo() {
        return voucherNo == null ? "" : voucherNo.trim();
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo == null ? null : voucherNo.trim();
    }

    public String getContractNo() {
        return contractNo == null ? "" : contractNo.trim();
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public String getInvoiceNo() {
        return invoiceNo == null ? "" : invoiceNo.trim();
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo == null ? null : invoiceNo.trim();
    }

    public Integer getBillType() {
        return billType;
    }

    public void setBillType(Integer billType) {
        this.billType = billType;
    }

    public String getBillFlag() {
        return billFlag == null ? "" : billFlag.trim();
    }

    public void setBillFlag(String billFlag) {
        this.billFlag = billFlag == null ? null : billFlag.trim();
    }

    public String getBillStatus() {
        return billStatus == null ? "" : billStatus.trim();
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus == null ? null : billStatus.trim();
    }

    public String getReporter() {
        return reporter == null ? "" : reporter.trim();
    }

    public void setReporter(String reporter) {
        this.reporter = reporter == null ? null : reporter.trim();
    }

    public String getDeductAmtType() {
        return deductAmtType == null ? "" : deductAmtType.trim();
    }

    public void setDeductAmtType(String deductAmtType) {
        this.deductAmtType = deductAmtType == null ? null : deductAmtType.trim();
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCombProp() {
        return combProp == null ? "" : combProp.trim();
    }

    public void setCombProp(String combProp) {
        this.combProp = combProp == null ? null : combProp.trim();
    }

    public String getPurchaseContractID() {
        return purchaseContractID == null ? "" : purchaseContractID.trim();
    }

    public void setPurchaseContractID(String purchaseContractID) {
        this.purchaseContractID = purchaseContractID == null ? null : purchaseContractID.trim();
    }

    public String getPurchaseContract() {
        return purchaseContract == null ? "" : purchaseContract.trim();
    }

    public void setPurchaseContract(String purchaseContract) {
        this.purchaseContract = purchaseContract == null ? null : purchaseContract.trim();
    }

    public String getLoginOrgID() {
        return loginOrgID == null ? "" : loginOrgID.trim();
    }

    public void setLoginOrgID(String loginOrgID) {
        this.loginOrgID = loginOrgID == null ? null : loginOrgID.trim();
    }

    public String getResourceID() {
        return resourceID == null ? "" : resourceID.trim();
    }

    public void setResourceID(String resourceID) {
        this.resourceID = resourceID == null ? null : resourceID.trim();
    }

    public String getResourceName() {
        return resourceName == null ? "" : resourceName.trim();
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName == null ? null : resourceName.trim();
    }

    public String getCbsID() {
        return cbsID == null ? "" : cbsID.trim();
    }

    public void setCbsID(String cbsID) {
        this.cbsID = cbsID == null ? null : cbsID.trim();
    }

    public String getCbsName() {
        return cbsName == null ? "" : cbsName.trim();
    }

    public void setCbsName(String cbsName) {
        this.cbsName = cbsName == null ? null : cbsName.trim();
    }

    public BigDecimal getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(BigDecimal invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public String getOutOrgType() {
        return outOrgType == null ? "" : outOrgType.trim();
    }

    public void setOutOrgType(String outOrgType) {
        this.outOrgType = outOrgType == null ? null : outOrgType.trim();
    }

    public String getPurchType() {
        return purchType == null ? "" : purchType.trim();
    }

    public void setPurchType(String purchType) {
        this.purchType = purchType == null ? null : purchType.trim();
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

    public String getWarehouseName() {
        return warehouseName == null ? "" : warehouseName.trim();
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName == null ? null : warehouseName.trim();
    }

    public String getOrNotPrecollecte() {
        return orNotPrecollecte == null ? "" : orNotPrecollecte.trim();
    }

    public void setOrNotPrecollecte(String orNotPrecollecte) {
        this.orNotPrecollecte = orNotPrecollecte == null ? null : orNotPrecollecte.trim();
    }

    public String getCompanyId() {
        return companyId == null ? "" : companyId.trim();
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCompanyName() {
        return companyName == null ? "" : companyName.trim();
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
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

    public List<ZxSkStockTransItemReceiving> getZxSkStockTransItemReceivingList() {
        return zxSkStockTransItemReceivingList;
    }

    public void setZxSkStockTransItemReceivingList(List<ZxSkStockTransItemReceiving> zxSkStockTransItemReceivingList) {
        this.zxSkStockTransItemReceivingList = zxSkStockTransItemReceivingList;
    }


}

