package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojoFlow;

public class ZxCtSuppliesLeaseSettlementList extends BasePojoFlow {
    // 主键
    private String zxCtSuppliesLeaseSettlementListId;

    // 最后编辑时间
    private Date editTime;

    // 状态
    private String auditStatus;

    // 重新评审次数
    private int notPassNum=0;

    // 债权人编号
    private String secondIDCodeBh;

    // 债权人编号
    private String secondCode;

    // 摘要
    private String summary;

    // 责任单位名称
    private String responseUnitName;

    // 责任单位编号
    private String responseUnitCode;

    // 预计付款日期
    private Date estPayDate;

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

    // 推送状态
    private String isSend;

    // 所属公司ID
    private String companyId;
    
    // 推送时间
    private Date sendDate;

    // 统一社会信用代码
    private String orgCertificate;

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

    // 税额差值
    private BigDecimal tcse;

    // 是否锁定
    private String flowLock;

    // 是否首次结算
    private String isFirst;

    // 是否上报局
    private String isReport;

    // 是否签认
    private String isSign;

    // 是否被审核
    private String isAudit;

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

    // 考核单位名称
    private String assessUnitName;

    // 考核单位编号
    private String assessUnitCode;

    // 开累应付金额(元)
    private BigDecimal totalPayAmt;

    // 开累结算金额(元)
    private BigDecimal totalAmt;

    // 结算期限开始时间
    private Date upTotalAmt;

    // 结算期限结束时间
    private Date endDate;

    // 结算期次
    private String period;

    // 结算内容
    private String content;

    // 结算类型
    private String billType;

    // 结算金额差值
    private BigDecimal tcje;

    // 结算方向
    private String setDir;

    // 结算单编号
    private String billNo;

    // 计算人
    private String countPerson;

    // 汇率
    private String exchangeRate;

    // 核算单位内码
    private String accountUnitId;

    // 核算单位名称
    private String accountUnitName;

    // 核算部门编号
    private String accountDepCode;

    // 合同乙方
    private String secondName;

    // 合同名称
    private String contractName;

    // 合同类型
    private String contrType;

    // 合同类别
    private String code7;

    // 合同编号
    private String contractNo;

    // 合同ID
    private String contractID;

    // 复核人
    private String reCountPerson;

    // 附件张数
    private String numOfSheets;

    // 发起人
    private String flowBeginPerson;

    // 调差后累计结算金额
    private BigDecimal tchljjsje;

    // 到期日期
    private Date expDate;

    // 创建时间
    private Date createTime;

    // 财务系统id
    private String fiId;

    // 财务审批状态说明
    private String cwStatusRemark;

    // 财务审批状态
    private String cwStatus;

    // 币种
    private String currency;

    // 本期应付金额(元)
    private BigDecimal thisPayAmt;

    // 本期结算税额(元)
    private BigDecimal thisAmtTax;

    // 本期结算金额(元)
    private BigDecimal thisAmt;

    // 本期结算不含税金额(元)
    private BigDecimal thisAmtNoTax;

    // 本期调整后结算金额
    private BigDecimal bqtchjsje;

    // 本期调差后税额
    private BigDecimal bqtchse;

    // zjgcxm_xmbh
    private String zjgcxmXmbh;

    // zjgcxm_nm
    private String zjgcxmNm;

    // upWorkItemID
    private String upWorkItemID;

    // taxRate
    private String taxRate;

    // oaOrgID
    private String oaOrgID;

    // notDisplay
    private String notDisplay;

    // isDeduct
    private String isDeduct;

    // contractCost
    private BigDecimal contractCost;

    // 租用物资质量状况评价
    private String appraisal;

    // 签认单编号
    private String signedNo;

    // 其他
    private String otherInfo;

    // 本期结算开始日期
    private Date startDate;

    // 引用签认单未审核数量
    private int useSignedOrder=0;

    // 是否最大期次
    private int isMaxPeriod=0;

    // 是否完工后结算
    private String isFished;

    // 是否使用
    private int useCount=0;

    // 流水号
    private String serialNumber;

    // 结算表初始化顺序号
    private String initSerialNumber;

    // 含税合同金额(万)
    private BigDecimal contractAmt;

    // 变更后含税合同金额(万)
    private BigDecimal changeAmt;

    // 本期清单结算金额(元)
    private BigDecimal thisEquipAmt;

    // 本期清单结算金额(元)
    private BigDecimal thisEquipAmtNoTax;
    
    // 本期清单结算不含税金额(元)
    private BigDecimal thisEquipAmtTax;

    // 开累清单结算金额(元)
    private BigDecimal totalEquipAmt;
    
    //本期支付项结算金额(元)
    private BigDecimal payThisAmt;
    
    //本期支付项结算不含税金额(元)
    private BigDecimal payThisAmtNoTax;
    
    //本期支付项结算税额(元)
    private BigDecimal payThisAmtTax;
    
    //累计支付项结算金额(元)
    private BigDecimal payTotalAmt;
    
    //本期运杂费（元）
    private BigDecimal payInOutAmt;
    
    //本期损耗费（元）
    private BigDecimal payFoodAmt;
    
    //本期奖罚金（元）
    private BigDecimal payFineAmt;
    
    //本期其他款项（元）
    private BigDecimal payOtherAmt;
    
    //上期末清单结算金额(元)
    private BigDecimal upAmt;
    
    //上期末累计支付项结算金额(元)
    private BigDecimal payUpAmt;
    
//    //上期末结算金额
//    private BigDecimal upAlterAmt;
//    
//    //上期末结算税额
//    private BigDecimal upAlterAmtTax;
//    
//    //上期末结算不含税金额
//    private BigDecimal upAlterAmtNoTax;

    // 备注
    private String remarks;
    
    // 说明
    private String instructions;
    
    //结算期限开始时间
    private Date beginDate;
    
    // 结算期次
    private Date periodDate;

    // 意见1
    private String opinionField1;

    // 意见2
    private String opinionField2;

    // 意见3
    private String opinionField3;

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
    
    private List<ZxErpFile> settlementFileList;
    
    private List<ZxErpFile> documentFileList;
    
    private List<ZxCtSuppliesLeaseCampChangeIncrease> campChangeIncreaseList;
    
    private String flag;
    
//    public BigDecimal getUpAlterAmt() {
//		return upAlterAmt;
//	}
//
//	public void setUpAlterAmt(BigDecimal upAlterAmt) {
//		this.upAlterAmt = upAlterAmt;
//	}
//
//	public BigDecimal getUpAlterAmtTax() {
//		return upAlterAmtTax;
//	}
//
//	public void setUpAlterAmtTax(BigDecimal upAlterAmtTax) {
//		this.upAlterAmtTax = upAlterAmtTax;
//	}
//
//	public BigDecimal getUpAlterAmtNoTax() {
//		return upAlterAmtNoTax;
//	}
//
//	public void setUpAlterAmtNoTax(BigDecimal upAlterAmtNoTax) {
//		this.upAlterAmtNoTax = upAlterAmtNoTax;
//	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public List<ZxCtSuppliesLeaseCampChangeIncrease> getCampChangeIncreaseList() {
		return campChangeIncreaseList;
	}

	public void setCampChangeIncreaseList(List<ZxCtSuppliesLeaseCampChangeIncrease> campChangeIncreaseList) {
		this.campChangeIncreaseList = campChangeIncreaseList;
	}

	public String getOpinionField1() {
		return opinionField1;
	}

	public List<ZxErpFile> getDocumentFileList() {
		return documentFileList;
	}

	public void setDocumentFileList(List<ZxErpFile> documentFileList) {
		this.documentFileList = documentFileList;
	}

	public BigDecimal getThisEquipAmtNoTax() {
		return thisEquipAmtNoTax;
	}

	public void setThisEquipAmtNoTax(BigDecimal thisEquipAmtNoTax) {
		this.thisEquipAmtNoTax = thisEquipAmtNoTax;
	}

	public BigDecimal getThisEquipAmtTax() {
		return thisEquipAmtTax;
	}

	public void setThisEquipAmtTax(BigDecimal thisEquipAmtTax) {
		this.thisEquipAmtTax = thisEquipAmtTax;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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

	public Date getPeriodDate() {
		return periodDate;
	}

	public void setPeriodDate(Date periodDate) {
		this.periodDate = periodDate;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public BigDecimal getPayUpAmt() {
		return payUpAmt;
	}

	public void setPayUpAmt(BigDecimal payUpAmt) {
		this.payUpAmt = payUpAmt;
	}

	public BigDecimal getUpAmt() {
		return upAmt;
	}

	public void setUpAmt(BigDecimal upAmt) {
		this.upAmt = upAmt;
	}

	public BigDecimal getPayThisAmt() {
		return payThisAmt;
	}

	public void setPayThisAmt(BigDecimal payThisAmt) {
		this.payThisAmt = payThisAmt;
	}

	public BigDecimal getPayThisAmtNoTax() {
		return payThisAmtNoTax;
	}

	public void setPayThisAmtNoTax(BigDecimal payThisAmtNoTax) {
		this.payThisAmtNoTax = payThisAmtNoTax;
	}

	public BigDecimal getPayThisAmtTax() {
		return payThisAmtTax;
	}

	public void setPayThisAmtTax(BigDecimal payThisAmtTax) {
		this.payThisAmtTax = payThisAmtTax;
	}

	public BigDecimal getPayTotalAmt() {
		return payTotalAmt;
	}

	public void setPayTotalAmt(BigDecimal payTotalAmt) {
		this.payTotalAmt = payTotalAmt;
	}

	public BigDecimal getPayInOutAmt() {
		return payInOutAmt;
	}

	public void setPayInOutAmt(BigDecimal payInOutAmt) {
		this.payInOutAmt = payInOutAmt;
	}

	public BigDecimal getPayFoodAmt() {
		return payFoodAmt;
	}

	public void setPayFoodAmt(BigDecimal payFoodAmt) {
		this.payFoodAmt = payFoodAmt;
	}

	public BigDecimal getPayFineAmt() {
		return payFineAmt;
	}

	public void setPayFineAmt(BigDecimal payFineAmt) {
		this.payFineAmt = payFineAmt;
	}

	public BigDecimal getPayOtherAmt() {
		return payOtherAmt;
	}

	public void setPayOtherAmt(BigDecimal payOtherAmt) {
		this.payOtherAmt = payOtherAmt;
	}

	// 排序
    private int sort=0;

    public List<ZxErpFile> getSettlementFileList() {
		return settlementFileList;
	}

	public void setSettlementFileList(List<ZxErpFile> settlementFileList) {
		this.settlementFileList = settlementFileList;
	}

	public String getZxCtSuppliesLeaseSettlementListId() {
        return zxCtSuppliesLeaseSettlementListId == null ? "" : zxCtSuppliesLeaseSettlementListId.trim();
    }

    public void setZxCtSuppliesLeaseSettlementListId(String zxCtSuppliesLeaseSettlementListId) {
        this.zxCtSuppliesLeaseSettlementListId = zxCtSuppliesLeaseSettlementListId == null ? null : zxCtSuppliesLeaseSettlementListId.trim();
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

    public String getSecondIDCodeBh() {
        return secondIDCodeBh == null ? "" : secondIDCodeBh.trim();
    }

    public void setSecondIDCodeBh(String secondIDCodeBh) {
        this.secondIDCodeBh = secondIDCodeBh == null ? null : secondIDCodeBh.trim();
    }

    public String getSecondCode() {
        return secondCode == null ? "" : secondCode.trim();
    }

    public void setSecondCode(String secondCode) {
        this.secondCode = secondCode == null ? null : secondCode.trim();
    }

    public String getSummary() {
        return summary == null ? "" : summary.trim();
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getResponseUnitName() {
        return responseUnitName == null ? "" : responseUnitName.trim();
    }

    public void setResponseUnitName(String responseUnitName) {
        this.responseUnitName = responseUnitName == null ? null : responseUnitName.trim();
    }

    public String getResponseUnitCode() {
        return responseUnitCode == null ? "" : responseUnitCode.trim();
    }

    public void setResponseUnitCode(String responseUnitCode) {
        this.responseUnitCode = responseUnitCode == null ? null : responseUnitCode.trim();
    }

    public Date getEstPayDate() {
        return estPayDate;
    }

    public void setEstPayDate(Date estPayDate) {
        this.estPayDate = estPayDate;
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

    public String getIsSend() {
        return isSend == null ? "" : isSend.trim();
    }

    public void setIsSend(String isSend) {
        this.isSend = isSend == null ? null : isSend.trim();
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getOrgCertificate() {
        return orgCertificate == null ? "" : orgCertificate.trim();
    }

    public void setOrgCertificate(String orgCertificate) {
        this.orgCertificate = orgCertificate == null ? null : orgCertificate.trim();
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

    public BigDecimal getTcse() {
        return tcse;
    }

    public void setTcse(BigDecimal tcse) {
        this.tcse = tcse;
    }

    public String getFlowLock() {
        return flowLock == null ? "" : flowLock.trim();
    }

    public void setFlowLock(String flowLock) {
        this.flowLock = flowLock == null ? null : flowLock.trim();
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

    public String getIsSign() {
        return isSign == null ? "" : isSign.trim();
    }

    public void setIsSign(String isSign) {
        this.isSign = isSign == null ? null : isSign.trim();
    }

    public String getIsAudit() {
        return isAudit == null ? "" : isAudit.trim();
    }

    public void setIsAudit(String isAudit) {
        this.isAudit = isAudit == null ? null : isAudit.trim();
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

    public String getAssessUnitName() {
        return assessUnitName == null ? "" : assessUnitName.trim();
    }

    public void setAssessUnitName(String assessUnitName) {
        this.assessUnitName = assessUnitName == null ? null : assessUnitName.trim();
    }

    public String getAssessUnitCode() {
        return assessUnitCode == null ? "" : assessUnitCode.trim();
    }

    public void setAssessUnitCode(String assessUnitCode) {
        this.assessUnitCode = assessUnitCode == null ? null : assessUnitCode.trim();
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

    public Date getUpTotalAmt() {
        return upTotalAmt;
    }

    public void setUpTotalAmt(Date upTotalAmt) {
        this.upTotalAmt = upTotalAmt;
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

    public BigDecimal getTcje() {
        return tcje;
    }

    public void setTcje(BigDecimal tcje) {
        this.tcje = tcje;
    }

    public String getSetDir() {
        return setDir == null ? "" : setDir.trim();
    }

    public void setSetDir(String setDir) {
        this.setDir = setDir == null ? null : setDir.trim();
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

    public String getExchangeRate() {
        return exchangeRate == null ? "" : exchangeRate.trim();
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate == null ? null : exchangeRate.trim();
    }

    public String getAccountUnitId() {
        return accountUnitId == null ? "" : accountUnitId.trim();
    }

    public void setAccountUnitId(String accountUnitId) {
        this.accountUnitId = accountUnitId == null ? null : accountUnitId.trim();
    }

    public String getAccountUnitName() {
        return accountUnitName == null ? "" : accountUnitName.trim();
    }

    public void setAccountUnitName(String accountUnitName) {
        this.accountUnitName = accountUnitName == null ? null : accountUnitName.trim();
    }

    public String getAccountDepCode() {
        return accountDepCode == null ? "" : accountDepCode.trim();
    }

    public void setAccountDepCode(String accountDepCode) {
        this.accountDepCode = accountDepCode == null ? null : accountDepCode.trim();
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

    public String getContrType() {
        return contrType == null ? "" : contrType.trim();
    }

    public void setContrType(String contrType) {
        this.contrType = contrType == null ? null : contrType.trim();
    }

    public String getCode7() {
        return code7 == null ? "" : code7.trim();
    }

    public void setCode7(String code7) {
        this.code7 = code7 == null ? null : code7.trim();
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

    public String getNumOfSheets() {
        return numOfSheets == null ? "" : numOfSheets.trim();
    }

    public void setNumOfSheets(String numOfSheets) {
        this.numOfSheets = numOfSheets == null ? null : numOfSheets.trim();
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

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFiId() {
        return fiId == null ? "" : fiId.trim();
    }

    public void setFiId(String fiId) {
        this.fiId = fiId == null ? null : fiId.trim();
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

    public String getCurrency() {
        return currency == null ? "" : currency.trim();
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public BigDecimal getThisPayAmt() {
        return thisPayAmt;
    }

    public void setThisPayAmt(BigDecimal thisPayAmt) {
        this.thisPayAmt = thisPayAmt;
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

    public BigDecimal getBqtchjsje() {
        return bqtchjsje;
    }

    public void setBqtchjsje(BigDecimal bqtchjsje) {
        this.bqtchjsje = bqtchjsje;
    }

    public BigDecimal getBqtchse() {
        return bqtchse;
    }

    public void setBqtchse(BigDecimal bqtchse) {
        this.bqtchse = bqtchse;
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

    public String getTaxRate() {
        return taxRate == null ? "" : taxRate.trim();
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate == null ? null : taxRate.trim();
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

    public String getIsDeduct() {
        return isDeduct == null ? "" : isDeduct.trim();
    }

    public void setIsDeduct(String isDeduct) {
        this.isDeduct = isDeduct == null ? null : isDeduct.trim();
    }

    public BigDecimal getContractCost() {
        return contractCost;
    }

    public void setContractCost(BigDecimal contractCost) {
        this.contractCost = contractCost;
    }

    public String getAppraisal() {
        return appraisal == null ? "" : appraisal.trim();
    }

    public void setAppraisal(String appraisal) {
        this.appraisal = appraisal == null ? null : appraisal.trim();
    }

    public String getSignedNo() {
        return signedNo == null ? "" : signedNo.trim();
    }

    public void setSignedNo(String signedNo) {
        this.signedNo = signedNo == null ? null : signedNo.trim();
    }

    public String getOtherInfo() {
        return otherInfo == null ? "" : otherInfo.trim();
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo == null ? null : otherInfo.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getUseSignedOrder() {
        return useSignedOrder;
    }

    public void setUseSignedOrder(int useSignedOrder) {
        this.useSignedOrder = useSignedOrder;
    }

    public int getIsMaxPeriod() {
        return isMaxPeriod;
    }

    public void setIsMaxPeriod(int isMaxPeriod) {
        this.isMaxPeriod = isMaxPeriod;
    }

    public String getIsFished() {
        return isFished == null ? "" : isFished.trim();
    }

    public void setIsFished(String isFished) {
        this.isFished = isFished == null ? null : isFished.trim();
    }

    public int getUseCount() {
        return useCount;
    }

    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }

    public String getSerialNumber() {
        return serialNumber == null ? "" : serialNumber.trim();
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
    }

    public BigDecimal getTotalEquipAmt() {
        return totalEquipAmt;
    }

    public void setTotalEquipAmt(BigDecimal totalEquipAmt) {
        this.totalEquipAmt = totalEquipAmt;
    }

    public String getInitSerialNumber() {
        return initSerialNumber == null ? "" : initSerialNumber.trim();
    }

    public void setInitSerialNumber(String initSerialNumber) {
        this.initSerialNumber = initSerialNumber == null ? null : initSerialNumber.trim();
    }

    public BigDecimal getContractAmt() {
        return contractAmt;
    }

    public void setContractAmt(BigDecimal contractAmt) {
        this.contractAmt = contractAmt;
    }

    public BigDecimal getChangeAmt() {
        return changeAmt;
    }

    public void setChangeAmt(BigDecimal changeAmt) {
        this.changeAmt = changeAmt;
    }

    public BigDecimal getThisEquipAmt() {
        return thisEquipAmt;
    }

    public void setThisEquipAmt(BigDecimal thisEquipAmt) {
        this.thisEquipAmt = thisEquipAmt;
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
