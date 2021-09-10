package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;
import com.apih5.framework.entity.BasePojoFlow;

public class ZxCtSuppliesShopSettlementList extends BasePojoFlow {
    // 主键
    private String zxCtSuppliesShopSettlementId;

    // 最后编辑时间
    private Date editTime;

    // 状态
    private String auditStatus;

    // 重新评审次数
    private int notPassNum=0;

    // 引用结算表是否审核
    private String isAudit;

    // 乙方ID
    private String secondID;

    // 业务日期
    private Date businessDate;

    // 项目名称
    private String orgName;

    // 项目ID
    private String orgID;

    // 项目
    private String zjgcxmXmmc;

    // 物资结算表ID
    private String billID;

    // 填报日期
    private Date reportDate;

    // 填报人
    private String reportPerson;

    // 所属公司排序
    private String comOrders;

    // 所属公司名称
    private String comName;

    // 所属公司ID
    private String comID;

    // 税率(%)
    private String taxRate;

    // 是否首次结算
    private String isFirst;

    // 是否上报局
    private String isReport;

    // 是否抵扣
    private String isDeduct;

    // 流程开始时间
    private Date flowBeginDate;

    // 流程进度ID
    private String instProcessID;

    // 流程结束时间
    private Date flowEndDate;

    // 流程ID
    private String workItemID;

    // 老流程ID
    private String oldWorkItemID;

    // 开累应付金额(元)
    private BigDecimal totalPayAmt;

    // 开累结算金额(元)
    private BigDecimal totalAmt;

    // 结算期限开始时间
    private Date beginDate;

    // 结算期限结束时间
    private Date endDate;
    
    //单据结束日期
    private Date documentsEndTime;

    // 结算期次
    private String period;

    // 结算内容
    private String content;

    // 结算类型
    private String billType;

    // 结算单编号
    private String billNo;

    // 计算人
    private String countPerson;

    // 计税方法
    private String taxCountWay;

    // 合同乙方
    private String secondName;

    // 合同名称
    private String contractName;

    // 合同编号
    private String contractNo;

    // 合同ID
    private String contractID;

    // 复核人
    private String reCountPerson;

    // 发起人
    private String flowBeginPerson;

    // 调差后累计结算金额
    private BigDecimal tchljjsje;

    // 财务审批状态说明
    private String cwStatusRemark;

    // 财务审批状态
    private String cwStatus;

    // 本期应付金额(元)
    private BigDecimal thisPayAmt;

    // 本期结算金额(元)
    private BigDecimal thisAmt;

    // 本期调整后结算金额
    private BigDecimal bqtchjsje;

    // zjgcxm_xmbh
    private String zjgcxmXmbh;

    // zjgcxm_nm
    private String zjgcxmNm;

    // upWorkItemID
    private String upWorkItemID;

    // oaOrgID
    private String oaOrgID;

    // notDisplay
    private String notDisplay;

    // 选择单据批次
    private int saveNumbers=0;

    // 物资质量评价
    private String appraisal;

    // 是否被引用
    private int useCount=0;

    // 是否0数量最终结算
    private String pp1;

    // 签认单编号
    private String signedNo;

    // 流水号
    private String serialNumber;

    // 结算类型
    private String saType;

    // 结算单初始化顺序号
    private String initSerialNumber;

    // 单据开始日期
    private Date startDate;

    // 单据编号ID
    private String stockBillIDs;

    // 单据编号
    private String stockBillNos;

    // 上期末结算金额
    private BigDecimal upAmt;

    // 签认单编号
    private String signedNos;

    // 签认单ID
    private String signedOrders;
    
    //是否完工后结算
    private String isFished;

    // 含税合同金额(万元)
    private BigDecimal contractAmt;

    // 本期物资细目含税结算税额(元)
    private BigDecimal thisAmtTax;

    // 本期物资细目不含税结算金额(元)
    private BigDecimal thisAmtNoTax;
    
    // 累计物资细目含税结算金额(元)
    private BigDecimal resTotalAmt;
    
    // 本期物资细目含税结算金额(元)
    private BigDecimal resThisAmt;
    
    //本期物资细目不含税结算金额(元)
    private BigDecimal resThisAmtNoTax;
    
    //本期物资细目含税结算税额(元)
    private BigDecimal resThisAmtTax;
    
    //变更后含税合同金额
    private BigDecimal alterContractSum;

    // 备注
    private String remarks;
    
    private String instructions;
    /**************前端所需要字段*********************/
    //变更后含税合同金额
    private BigDecimal changeAmt;
    
    //本期支付项结算含税金额（支付项）
    private BigDecimal thisAmtByPay;
    
    //本期支付项结算不含税金额（支付项）
    private BigDecimal thisAmtNoTaxByPay;
    
    //本期支付项结算税额（支付项）
    private BigDecimal thisAmtTaxByPay;
    
    //本期支付项结算税额（支付项）
    private BigDecimal upAmtByPay;
    
    //本期运杂费（支付项）
    private BigDecimal transportAmt;
    
    //本期垫资费（支付项）
    private BigDecimal padTariffAmt;
    
    //本期奖罚金（支付项）
    private BigDecimal fineAmt;
    
    //本期其他款项（支付项）
    private BigDecimal otherAmt;
    
    //累计支付项结算金额（支付项）
    private BigDecimal totalAmtByPay;

    // 意见1
    private String opinionField1;

    // 意见2
    private String opinionField2;

    // 意见3
    private String opinionField3;

    // 所属公司ID
    private String companyId;

    // 意见4
    private String opinionField4;

    // 意见5
    private String opinionField5;

    // 意见6
    private String opinionField6;

    // 意见7
    private String opinionField7;

    // 意见8
    private String opinionField8;

    // 意见9
    private String opinionField9;

    // 意见10
    private String opinionField10;

    // 流程ID
    private String apih5FlowId;

    // work_id
    private String workId;

    // 审核状态
    private String apih5FlowStatus;

    // 审核节点状态
    private String apih5FlowNodeStatus;
    
    private String opinionField;
    
    private String opinionContent;
    
    private Date periodDate;
    
    private String stockBillID;
    
    private List<ZxErpFile> documentFileList;
    
    private String flag;
    
    private List<ZxCtSuppliesLeaseCampChangeIncrease> campChangeIncreaseList;
    
    private List<ZxCtSuppliesShopResSettleItem> shopResSettleItemList;
    
    public List<ZxCtSuppliesLeaseCampChangeIncrease> getCampChangeIncreaseList() {
		return campChangeIncreaseList;
	}

	public List<ZxCtSuppliesShopResSettleItem> getShopResSettleItemList() {
		return shopResSettleItemList;
	}

	public void setShopResSettleItemList(List<ZxCtSuppliesShopResSettleItem> shopResSettleItemList) {
		this.shopResSettleItemList = shopResSettleItemList;
	}

	public void setCampChangeIncreaseList(List<ZxCtSuppliesLeaseCampChangeIncrease> campChangeIncreaseList) {
		this.campChangeIncreaseList = campChangeIncreaseList;
	}

	public List<ZxErpFile> getDocumentFileList() {
		return documentFileList;
	}

	public void setDocumentFileList(List<ZxErpFile> documentFileList) {
		this.documentFileList = documentFileList;
	}

	public BigDecimal getResThisAmtNoTax() {
		return resThisAmtNoTax;
	}

	public void setResThisAmtNoTax(BigDecimal resThisAmtNoTax) {
		this.resThisAmtNoTax = resThisAmtNoTax;
	}

	public BigDecimal getResThisAmtTax() {
		return resThisAmtTax;
	}

	public void setResThisAmtTax(BigDecimal resThisAmtTax) {
		this.resThisAmtTax = resThisAmtTax;
	}

	public BigDecimal getResTotalAmt() {
		return resTotalAmt;
	}

	public void setResTotalAmt(BigDecimal resTotalAmt) {
		this.resTotalAmt = resTotalAmt;
	}

	public BigDecimal getResThisAmt() {
		return resThisAmt;
	}

	public void setResThisAmt(BigDecimal resThisAmt) {
		this.resThisAmt = resThisAmt;
	}

	public BigDecimal getTotalAmtByPay() {
		return totalAmtByPay;
	}

	public void setTotalAmtByPay(BigDecimal totalAmtByPay) {
		this.totalAmtByPay = totalAmtByPay;
	}

	public String getStockBillID() {
		return stockBillID;
	}

	public void setStockBillID(String stockBillID) {
		this.stockBillID = stockBillID;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Date getPeriodDate() {
		return periodDate;
	}

	public void setPeriodDate(Date periodDate) {
		this.periodDate = periodDate;
	}

	public String getOpinionField1() {
		return opinionField1;
	}

	public void setOpinionField1(String opinionField1) {
		this.opinionField1 = opinionField1;
	}

	public String getOpinionField2() {
		return opinionField2;
	}

	public void setOpinionField2(String opinionField2) {
		this.opinionField2 = opinionField2;
	}

	public String getOpinionField3() {
		return opinionField3;
	}

	public void setOpinionField3(String opinionField3) {
		this.opinionField3 = opinionField3;
	}

	public String getOpinionField4() {
		return opinionField4;
	}

	public void setOpinionField4(String opinionField4) {
		this.opinionField4 = opinionField4;
	}

	public String getOpinionField5() {
		return opinionField5;
	}

	public void setOpinionField5(String opinionField5) {
		this.opinionField5 = opinionField5;
	}

	public String getOpinionField6() {
		return opinionField6;
	}

	public void setOpinionField6(String opinionField6) {
		this.opinionField6 = opinionField6;
	}

	public String getOpinionField7() {
		return opinionField7;
	}

	public void setOpinionField7(String opinionField7) {
		this.opinionField7 = opinionField7;
	}

	public String getOpinionField8() {
		return opinionField8;
	}

	public void setOpinionField8(String opinionField8) {
		this.opinionField8 = opinionField8;
	}

	public String getOpinionField9() {
		return opinionField9;
	}

	public void setOpinionField9(String opinionField9) {
		this.opinionField9 = opinionField9;
	}

	public String getOpinionField10() {
		return opinionField10;
	}

	public void setOpinionField10(String opinionField10) {
		this.opinionField10 = opinionField10;
	}

	public String getApih5FlowId() {
		return apih5FlowId;
	}

	public void setApih5FlowId(String apih5FlowId) {
		this.apih5FlowId = apih5FlowId;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public String getApih5FlowStatus() {
		return apih5FlowStatus;
	}

	public void setApih5FlowStatus(String apih5FlowStatus) {
		this.apih5FlowStatus = apih5FlowStatus;
	}

	public String getApih5FlowNodeStatus() {
		return apih5FlowNodeStatus;
	}

	public void setApih5FlowNodeStatus(String apih5FlowNodeStatus) {
		this.apih5FlowNodeStatus = apih5FlowNodeStatus;
	}

	public String getOpinionField() {
		return opinionField;
	}

	public void setOpinionField(String opinionField) {
		this.opinionField = opinionField;
	}

	public String getOpinionContent() {
		return opinionContent;
	}

	public void setOpinionContent(String opinionContent) {
		this.opinionContent = opinionContent;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getIsFished() {
		return isFished;
	}

	public void setIsFished(String isFished) {
		this.isFished = isFished;
	}

	private List<ZxErpFile> settlementFileList;
    
    public BigDecimal getChangeAmt() {
		return changeAmt;
	}

	public void setChangeAmt(BigDecimal changeAmt) {
		this.changeAmt = changeAmt;
	}

	public BigDecimal getThisAmtByPay() {
		return thisAmtByPay;
	}

	public void setThisAmtByPay(BigDecimal thisAmtByPay) {
		this.thisAmtByPay = thisAmtByPay;
	}

	public BigDecimal getThisAmtNoTaxByPay() {
		return thisAmtNoTaxByPay;
	}

	public void setThisAmtNoTaxByPay(BigDecimal thisAmtNoTaxByPay) {
		this.thisAmtNoTaxByPay = thisAmtNoTaxByPay;
	}

	public BigDecimal getThisAmtTaxByPay() {
		return thisAmtTaxByPay;
	}

	public void setThisAmtTaxByPay(BigDecimal thisAmtTaxByPay) {
		this.thisAmtTaxByPay = thisAmtTaxByPay;
	}

	public BigDecimal getUpAmtByPay() {
		return upAmtByPay;
	}

	public void setUpAmtByPay(BigDecimal upAmtByPay) {
		this.upAmtByPay = upAmtByPay;
	}

	public BigDecimal getTransportAmt() {
		return transportAmt;
	}

	public void setTransportAmt(BigDecimal transportAmt) {
		this.transportAmt = transportAmt;
	}

	public BigDecimal getPadTariffAmt() {
		return padTariffAmt;
	}

	public void setPadTariffAmt(BigDecimal padTariffAmt) {
		this.padTariffAmt = padTariffAmt;
	}

	public BigDecimal getFineAmt() {
		return fineAmt;
	}

	public void setFineAmt(BigDecimal fineAmt) {
		this.fineAmt = fineAmt;
	}

	public BigDecimal getOtherAmt() {
		return otherAmt;
	}

	public void setOtherAmt(BigDecimal otherAmt) {
		this.otherAmt = otherAmt;
	}

	// 排序
    private int sort=0;

	public BigDecimal getAlterContractSum() {
		return alterContractSum;
	}

	public void setAlterContractSum(BigDecimal alterContractSum) {
		this.alterContractSum = alterContractSum;
	}

	public List<ZxErpFile> getSettlementFileList() {
		return settlementFileList;
	}

	public void setSettlementFileList(List<ZxErpFile> settlementFileList) {
		this.settlementFileList = settlementFileList;
	}

	public String getZxCtSuppliesShopSettlementId() {
        return zxCtSuppliesShopSettlementId == null ? "" : zxCtSuppliesShopSettlementId.trim();
    }

    public void setZxCtSuppliesShopSettlementId(String zxCtSuppliesShopSettlementId) {
        this.zxCtSuppliesShopSettlementId = zxCtSuppliesShopSettlementId == null ? null : zxCtSuppliesShopSettlementId.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getAuditStatus() {
        return auditStatus == null ? "" : auditStatus.trim();
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public int getNotPassNum() {
        return notPassNum;
    }

    public void setNotPassNum(int notPassNum) {
        this.notPassNum = notPassNum;
    }

    public String getIsAudit() {
        return isAudit == null ? "" : isAudit.trim();
    }

    public void setIsAudit(String isAudit) {
        this.isAudit = isAudit == null ? null : isAudit.trim();
    }

    public String getSecondID() {
        return secondID == null ? "" : secondID.trim();
    }

    public void setSecondID(String secondID) {
        this.secondID = secondID == null ? null : secondID.trim();
    }

    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getZjgcxmXmmc() {
        return zjgcxmXmmc == null ? "" : zjgcxmXmmc.trim();
    }

    public void setZjgcxmXmmc(String zjgcxmXmmc) {
        this.zjgcxmXmmc = zjgcxmXmmc == null ? null : zjgcxmXmmc.trim();
    }

    public String getBillID() {
        return billID == null ? "" : billID.trim();
    }

    public void setBillID(String billID) {
        this.billID = billID == null ? null : billID.trim();
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportPerson() {
        return reportPerson == null ? "" : reportPerson.trim();
    }

    public void setReportPerson(String reportPerson) {
        this.reportPerson = reportPerson == null ? null : reportPerson.trim();
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

    public String getIsFirst() {
        return isFirst == null ? "" : isFirst.trim();
    }

    public void setIsFirst(String isFirst) {
        this.isFirst = isFirst == null ? null : isFirst.trim();
    }

    public String getIsReport() {
        return isReport == null ? "" : isReport.trim();
    }

    public void setIsReport(String isReport) {
        this.isReport = isReport == null ? null : isReport.trim();
    }

    public String getIsDeduct() {
        return isDeduct == null ? "" : isDeduct.trim();
    }

    public void setIsDeduct(String isDeduct) {
        this.isDeduct = isDeduct == null ? null : isDeduct.trim();
    }

    public Date getFlowBeginDate() {
        return flowBeginDate;
    }

    public void setFlowBeginDate(Date flowBeginDate) {
        this.flowBeginDate = flowBeginDate;
    }

    public String getInstProcessID() {
        return instProcessID == null ? "" : instProcessID.trim();
    }

    public void setInstProcessID(String instProcessID) {
        this.instProcessID = instProcessID == null ? null : instProcessID.trim();
    }

    public Date getFlowEndDate() {
        return flowEndDate;
    }

    public void setFlowEndDate(Date flowEndDate) {
        this.flowEndDate = flowEndDate;
    }

    public String getWorkItemID() {
        return workItemID == null ? "" : workItemID.trim();
    }

    public void setWorkItemID(String workItemID) {
        this.workItemID = workItemID == null ? null : workItemID.trim();
    }

    public String getOldWorkItemID() {
        return oldWorkItemID == null ? "" : oldWorkItemID.trim();
    }

    public void setOldWorkItemID(String oldWorkItemID) {
        this.oldWorkItemID = oldWorkItemID == null ? null : oldWorkItemID.trim();
    }

    public BigDecimal getTotalPayAmt() {
        return totalPayAmt;
    }

    public void setTotalPayAmt(BigDecimal totalPayAmt) {
        this.totalPayAmt = totalPayAmt;
    }

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
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

    public String getPeriod() {
        return period == null ? "" : period.trim();
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getContent() {
        return content == null ? "" : content.trim();
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getBillType() {
        return billType == null ? "" : billType.trim();
    }

    public void setBillType(String billType) {
        this.billType = billType == null ? null : billType.trim();
    }

    public String getBillNo() {
        return billNo == null ? "" : billNo.trim();
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public String getCountPerson() {
        return countPerson == null ? "" : countPerson.trim();
    }

    public void setCountPerson(String countPerson) {
        this.countPerson = countPerson == null ? null : countPerson.trim();
    }

    public String getTaxCountWay() {
        return taxCountWay == null ? "" : taxCountWay.trim();
    }

    public void setTaxCountWay(String taxCountWay) {
        this.taxCountWay = taxCountWay == null ? null : taxCountWay.trim();
    }

    public String getSecondName() {
        return secondName == null ? "" : secondName.trim();
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName == null ? null : secondName.trim();
    }

    public String getContractName() {
        return contractName == null ? "" : contractName.trim();
    }

    public void setContractName(String contractName) {
        this.contractName = contractName == null ? null : contractName.trim();
    }

    public String getContractNo() {
        return contractNo == null ? "" : contractNo.trim();
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public String getContractID() {
        return contractID == null ? "" : contractID.trim();
    }

    public void setContractID(String contractID) {
        this.contractID = contractID == null ? null : contractID.trim();
    }

    public String getReCountPerson() {
        return reCountPerson == null ? "" : reCountPerson.trim();
    }

    public void setReCountPerson(String reCountPerson) {
        this.reCountPerson = reCountPerson == null ? null : reCountPerson.trim();
    }

    public String getFlowBeginPerson() {
        return flowBeginPerson == null ? "" : flowBeginPerson.trim();
    }

    public void setFlowBeginPerson(String flowBeginPerson) {
        this.flowBeginPerson = flowBeginPerson == null ? null : flowBeginPerson.trim();
    }

    public BigDecimal getTchljjsje() {
        return tchljjsje;
    }

    public void setTchljjsje(BigDecimal tchljjsje) {
        this.tchljjsje = tchljjsje;
    }

    public String getCwStatusRemark() {
        return cwStatusRemark == null ? "" : cwStatusRemark.trim();
    }

    public void setCwStatusRemark(String cwStatusRemark) {
        this.cwStatusRemark = cwStatusRemark == null ? null : cwStatusRemark.trim();
    }

    public String getCwStatus() {
        return cwStatus == null ? "" : cwStatus.trim();
    }

    public void setCwStatus(String cwStatus) {
        this.cwStatus = cwStatus == null ? null : cwStatus.trim();
    }

    public BigDecimal getThisPayAmt() {
        return thisPayAmt;
    }

    public void setThisPayAmt(BigDecimal thisPayAmt) {
        this.thisPayAmt = thisPayAmt;
    }

    public BigDecimal getThisAmt() {
        return thisAmt;
    }

    public void setThisAmt(BigDecimal thisAmt) {
        this.thisAmt = thisAmt;
    }

    public BigDecimal getBqtchjsje() {
        return bqtchjsje;
    }

    public void setBqtchjsje(BigDecimal bqtchjsje) {
        this.bqtchjsje = bqtchjsje;
    }

    public String getZjgcxmXmbh() {
        return zjgcxmXmbh == null ? "" : zjgcxmXmbh.trim();
    }

    public void setZjgcxmXmbh(String zjgcxmXmbh) {
        this.zjgcxmXmbh = zjgcxmXmbh == null ? null : zjgcxmXmbh.trim();
    }

    public String getZjgcxmNm() {
        return zjgcxmNm == null ? "" : zjgcxmNm.trim();
    }

    public void setZjgcxmNm(String zjgcxmNm) {
        this.zjgcxmNm = zjgcxmNm == null ? null : zjgcxmNm.trim();
    }

    public String getUpWorkItemID() {
        return upWorkItemID == null ? "" : upWorkItemID.trim();
    }

    public void setUpWorkItemID(String upWorkItemID) {
        this.upWorkItemID = upWorkItemID == null ? null : upWorkItemID.trim();
    }

    public String getOaOrgID() {
        return oaOrgID == null ? "" : oaOrgID.trim();
    }

    public void setOaOrgID(String oaOrgID) {
        this.oaOrgID = oaOrgID == null ? null : oaOrgID.trim();
    }

    public String getNotDisplay() {
        return notDisplay == null ? "" : notDisplay.trim();
    }

    public void setNotDisplay(String notDisplay) {
        this.notDisplay = notDisplay == null ? null : notDisplay.trim();
    }

    public int getSaveNumbers() {
        return saveNumbers;
    }

    public void setSaveNumbers(int saveNumbers) {
        this.saveNumbers = saveNumbers;
    }

    public String getAppraisal() {
        return appraisal == null ? "" : appraisal.trim();
    }

    public void setAppraisal(String appraisal) {
        this.appraisal = appraisal == null ? null : appraisal.trim();
    }

    public int getUseCount() {
        return useCount;
    }

    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }

    public String getPp1() {
        return pp1 == null ? "" : pp1.trim();
    }

    public void setPp1(String pp1) {
        this.pp1 = pp1 == null ? null : pp1.trim();
    }

    public String getSignedNo() {
        return signedNo == null ? "" : signedNo.trim();
    }

    public void setSignedNo(String signedNo) {
        this.signedNo = signedNo == null ? null : signedNo.trim();
    }

    public String getSerialNumber() {
        return serialNumber == null ? "" : serialNumber.trim();
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
    }

    public String getSaType() {
        return saType == null ? "" : saType.trim();
    }

    public void setSaType(String saType) {
        this.saType = saType == null ? null : saType.trim();
    }

    public String getInitSerialNumber() {
        return initSerialNumber == null ? "" : initSerialNumber.trim();
    }

    public void setInitSerialNumber(String initSerialNumber) {
        this.initSerialNumber = initSerialNumber == null ? null : initSerialNumber.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getStockBillIDs() {
        return stockBillIDs == null ? "" : stockBillIDs.trim();
    }

    public void setStockBillIDs(String stockBillIDs) {
        this.stockBillIDs = stockBillIDs == null ? null : stockBillIDs.trim();
    }

    public String getStockBillNos() {
        return stockBillNos == null ? "" : stockBillNos.trim();
    }

    public void setStockBillNos(String stockBillNos) {
        this.stockBillNos = stockBillNos == null ? null : stockBillNos.trim();
    }

    public BigDecimal getUpAmt() {
        return upAmt;
    }

    public void setUpAmt(BigDecimal upAmt) {
        this.upAmt = upAmt;
    }

    public String getSignedNos() {
        return signedNos == null ? "" : signedNos.trim();
    }

    public void setSignedNos(String signedNos) {
        this.signedNos = signedNos == null ? null : signedNos.trim();
    }

    public String getSignedOrders() {
        return signedOrders == null ? "" : signedOrders.trim();
    }

    public void setSignedOrders(String signedOrders) {
        this.signedOrders = signedOrders == null ? null : signedOrders.trim();
    }

    public BigDecimal getContractAmt() {
        return contractAmt;
    }

    public void setContractAmt(BigDecimal contractAmt) {
        this.contractAmt = contractAmt;
    }

    public BigDecimal getThisAmtTax() {
        return thisAmtTax;
    }

    public void setThisAmtTax(BigDecimal thisAmtTax) {
        this.thisAmtTax = thisAmtTax;
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

	public Date getDocumentsEndTime() {
		return documentsEndTime;
	}

	public void setDocumentsEndTime(Date documentsEndTime) {
		this.documentsEndTime = documentsEndTime;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
