package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojoFlow;
import com.google.common.collect.Lists;

public class ZxSaProjectSettleAudit extends BasePojoFlow {
	// 主键ID
	private String id;

	// 工程结算表ID
	private String billID;

	// 结算单编号
	private String billNo;

	// 项目ID
	private String orgID;

	// 项目名称
	private String orgName;

	// 结算期次
	private String period;

	// 合同ID
	private String contractID;

	// 合同编号
	private String contractNo;

	// 合同名称
	private String contractName;

	// 乙方ID
	private String secondID;

	// 合同乙方
	private String secondName;

	// 结算类型
	private String billType;

	// 结算期限开始时间
	private Date beginDate;

	// 结算期限结束时间
	private Date endDate;

	// 本期结算金额(元)
	private BigDecimal thisAmt;

	// 开累结算金额(元)
	private BigDecimal totalAmt;

	// 本期应支付金额(元)
	private BigDecimal thisPayAmt;

	// 开累应支付金额(元)
	private BigDecimal totalPayAmt;

	// 施工内容
	private String content;

	// 业务日期
	private Date businessDate;

	// 填报日期
	private Date reportDate;

	// 填报人
	private String reportPerson;

	// 计算人
	private String countPerson;

	// 复核人
	private String reCountPerson;

	// 发起人
	private String flowBeginPerson;

	// 流程ID
	private String workItemID;

	// 流程进度ID
	private String instProcessID;

	// 流程开始时间
	private Date flowBeginDate;

	// 流程结束时间
	private Date flowEndDate;

	// 审批状态
	private String auditStatus;

	// 最后编辑时间
	private Date editTime;

	// 公司ID
	private String comID;

	// 公司名称
	private String comName;

	// 公司排序
	private String comOrders;

	// 是否锁定
	private String flowLock;

	// isBack
	private String isBack;

	// 重新评审次数
	private int notPassNum = 0;

	// 是否上报局
	private String isReport;

	// appInsHistID
	private String appInsHistID;

	// 老流程ID
	private String oldWorkItemID;

	// isFlag
	private String isFlag;

	// sendToJuID
	private String sendToJuID;

	// isFlagZhb
	private String isFlagZhb;

	// isReportZhb
	private String isReportZhb;

	// sendToZhbID
	private String sendToZhbID;

	// appInsHistIDZhb
	private String appInsHistIDZhb;

	// beginPer
	private String beginPer;

	// 财务系统id
	private String fiId;

	// 结算方向
	private String setDir;

	// 核算单位内码
	private String accountUnitId;

	// 核算单位编号
	private String accountUnitCode;

	// 核算单位名称
	private String accountUnitName;

	// 考核单位Id
	private String assessUnitId;

	// 考核单位编号
	private String assessUnitCode;

	// 考核单位名称
	private String assessUnitName;

	// 责任单位Id
	private String responseUnitId;

	// 责任单位编号
	private String responseUnitCode;

	// 责任单位名称
	private String responseUnitName;

	// 核算部门内码
	private String accountDepId;

	// 核算部门编号
	private String accountDepCode;

	// 核算部门名称
	private String accountDepName;

	// 是否签认
	private String isSign;

	// 业务类型
	private String busiTypeId;

	// 项目编号
	private String projCode;

	// 债权人编号
	private String secondCode;

	// 计量确认日期
	private Date confirmDate;

	// 币种
	private String currency;

	// 汇率
	private String exchangeRate;

	// 附件张数
	private String numOfSheets;

	// 摘要
	private String summary;

	// 税率
	private String taxRate;

	// 款项性质内码
	private String payNatureId;

	// 款项性质编号
	private String payNatureCode;

	// 款项性质名称
	private String payNatureName;

	// 到期日期
	private Date ExpDate;

	// 预计付款日期
	private Date EstPayDate;

	// notDisplay
	private String notDisplay;

	// 本期结算不含税金额(元)
	private BigDecimal thisAmtNoTax;

	// 本期结算税额(元)
	private BigDecimal thisAmtTax;

	// 推送时间
	private Date sendDate;

	// 推送状态
	private String isSend;

	// secondIDCodeBh
	private String secondIDCodeBh;

	// 财务审批状态
	private String cwStatus;

	// 财务审批状态说明
	private String cwStatusRemark;

	// auditWorkitemID
	private String auditWorkitemID;

	// auditSys
	private String auditSys;

	// 交工日期
	private Date finishDate;

	// 结算金额差值
	private BigDecimal tcje;

	// 税额差值
	private BigDecimal tcse;

	// 调差后累计结算金额
	private BigDecimal tchljjsje;

	// 本期调差后税额
	private BigDecimal bqtchse;

	// 本期调整后结算金额
	private BigDecimal bqtchjsje;

	// 计税方法
	private String taxCountWay;

	// zjgcxm_nm
	private String ZJGCXMNM;

	// zjgcxm_xmbh
	private String ZJGCXMXMBH;

	// 项目
	private String ZJGCXMXMMC;

	// 本期调差后不含税金额
	private BigDecimal bqtchjsnotax;

	// tcnotax
	private BigDecimal tcnotax;

	// contractCost
	private BigDecimal contractCost;

	// zjxmhtb_nm
	private String zjxmhtbNm;

	// zjxmhtb_bh
	private String zjxmhtbBh;

	// zjxmhtb_mc
	private String zjxmhtbMc;

	// isRela
	private String isRela;

	// orgCertificate
	private String orgCertificate;

	// 是否为首次结算
	private String isFirst;

	// upWorkItemID
	private String upWorkItemID;

	// oaOrgID
	private String oaOrgID;

	// 含税合同金额(万元)
	private BigDecimal contractAmt;

	// 变更后含税合同金额(万元)
	private BigDecimal changeAmt;

	// 是否完工后结算
	private String isFished;

	// 是否可以反审核
	private String useCount;

	// 是否最大期次
	private String isMaxPeriod;

	// 签认单编号
	private String signedNo;

	// 完成下列工程开始日期
	private Date startDate;

	// 附图及附表(可另附页计算)
	private String attachmentInfo;

	// 工程质量评价
	private String appraisal;

	// 流水号
	private String serialNumber;

	// 结算单初始化顺序号
	private String initSerialNumber;

	// 结算单明细列表
	private List<ZxSaProjectSettleAuditItem> projectSettleAuditItemList;

	// 附件
	private List<ZxErpFile> zxErpFileList;

	// 填报人电话
	private String reportPersonTel;

	// 备注
	private String remark;

	// 清单含税合同金额(万元)
	private BigDecimal workContractAmt;

	// 清单变更后含税合同金额(万元)
	private BigDecimal workChangeAmt;

	// 清单本期清单结算含税金额(元)
	private BigDecimal workThisAmt;

	// 清单累计清单结算含税金额(元)
	private BigDecimal workTotalAmt;

	// 清单本期清单结算不含税金额(元)
	private BigDecimal workThisAmtNoTax;

	// 清单本期清单结算税额(元)
	private BigDecimal workThisAmtTax;

	// 支付项本期支付项结算含税金额(元)
	private BigDecimal payThisAmt;

	// 本期支付项结算不含税金额(元)
	private BigDecimal payThisAmtNoTax;

	// 支付项累支付项计支付项结算金额(元)
	private BigDecimal payTotalAmt;

	// 支付项物资调拨费用本期结算小计(元)
	private BigDecimal payMaterialAmt;

	// 支付项机械调拨费用本期结算小计(元)
	private BigDecimal payMachineAmt;

	// 支付项临时用工费本期结算小计(元)
	private BigDecimal payTempAmt;

	// 支付项奖罚金额本期结算小计(元)
	private BigDecimal payFineAmt;

	// 支付项补偿金额本期结算小计(元)
	private BigDecimal payRecoupAmt;

	// 支付项其他款项本期结算小计(元)
	private BigDecimal payOtherAmt;

	// 本期次结算单编号
	private int statementNo = 0;

	// 清单明细
	private List<ZxSaProjectWorkSettleItem> workSettleList;

	// 清单明细
	private List<ZxSaProjectWorkSettleItem> workSettleItemList;

	// 支付项明细
	private List<ZxSaProjectPaySettleItem> paySettleList;

	// 支付项明细
	private List<ZxSaProjectPaySettleItem> paySettleItemList;

	// 流程ID
	private String workId;

	// 流程状态
	private String apih5FlowStatus;

	// 意见
	private String opinionField;

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

	// 结算期次时间蹉
	private Date periodTimeWasted;

	// 清单主表主键ID
	private String projectWorkSettleId;

	// 支付项主表主键ID
	private String projectPaySettleId;

	// 公司id
	private String companyId;

	// 公司名称
	private String companyName;

	// 正文附件
	private List<ZxErpFile> textAttachmentList;

	// 意见11
	private String opinionField11;

	// 意见12
	private String opinionField12;

	// 意见13
	private String opinionField13;

	// 意见14
	private String opinionField14;

	// 意见15
	private String opinionField15;

	// 意见16
	private String opinionField16;

	// 结算情况
	private String settleType;

	// 是否抵扣
	private String isDeduct;

	// 排序
	private int sort = 0;

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

	public String getBillNo() {
		return billNo == null ? "" : billNo.trim();
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo == null ? null : billNo.trim();
	}

	public String getOrgID() {
		return orgID == null ? "" : orgID.trim();
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID == null ? null : orgID.trim();
	}

	public String getOrgName() {
		return orgName == null ? "" : orgName.trim();
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName == null ? null : orgName.trim();
	}

	public String getPeriod() {
		return period == null ? "" : period.trim();
	}

	public void setPeriod(String period) {
		this.period = period == null ? null : period.trim();
	}

	public String getContractID() {
		return contractID == null ? "" : contractID.trim();
	}

	public void setContractID(String contractID) {
		this.contractID = contractID == null ? null : contractID.trim();
	}

	public String getContractNo() {
		return contractNo == null ? "" : contractNo.trim();
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo == null ? null : contractNo.trim();
	}

	public String getContractName() {
		return contractName == null ? "" : contractName.trim();
	}

	public void setContractName(String contractName) {
		this.contractName = contractName == null ? null : contractName.trim();
	}

	public String getSecondID() {
		return secondID == null ? "" : secondID.trim();
	}

	public void setSecondID(String secondID) {
		this.secondID = secondID == null ? null : secondID.trim();
	}

	public String getSecondName() {
		return secondName == null ? "" : secondName.trim();
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName == null ? null : secondName.trim();
	}

	public String getBillType() {
		return billType == null ? "" : billType.trim();
	}

	public void setBillType(String billType) {
		this.billType = billType == null ? null : billType.trim();
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

	public BigDecimal getThisAmt() {
		return thisAmt;
	}

	public void setThisAmt(BigDecimal thisAmt) {
		this.thisAmt = thisAmt;
	}

	public BigDecimal getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

	public BigDecimal getThisPayAmt() {
		return thisPayAmt;
	}

	public void setThisPayAmt(BigDecimal thisPayAmt) {
		this.thisPayAmt = thisPayAmt;
	}

	public BigDecimal getTotalPayAmt() {
		return totalPayAmt;
	}

	public void setTotalPayAmt(BigDecimal totalPayAmt) {
		this.totalPayAmt = totalPayAmt;
	}

	public String getContent() {
		return content == null ? "" : content.trim();
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public Date getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(Date businessDate) {
		this.businessDate = businessDate;
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

	public String getCountPerson() {
		return countPerson == null ? "" : countPerson.trim();
	}

	public void setCountPerson(String countPerson) {
		this.countPerson = countPerson == null ? null : countPerson.trim();
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

	public String getWorkItemID() {
		return workItemID == null ? "" : workItemID.trim();
	}

	public void setWorkItemID(String workItemID) {
		this.workItemID = workItemID == null ? null : workItemID.trim();
	}

	public String getInstProcessID() {
		return instProcessID == null ? "" : instProcessID.trim();
	}

	public void setInstProcessID(String instProcessID) {
		this.instProcessID = instProcessID == null ? null : instProcessID.trim();
	}

	public Date getFlowBeginDate() {
		return flowBeginDate;
	}

	public void setFlowBeginDate(Date flowBeginDate) {
		this.flowBeginDate = flowBeginDate;
	}

	public Date getFlowEndDate() {
		return flowEndDate;
	}

	public void setFlowEndDate(Date flowEndDate) {
		this.flowEndDate = flowEndDate;
	}

	public String getAuditStatus() {
		return auditStatus == null ? "" : auditStatus.trim();
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus == null ? null : auditStatus.trim();
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public String getComID() {
		return comID == null ? "" : comID.trim();
	}

	public void setComID(String comID) {
		this.comID = comID == null ? null : comID.trim();
	}

	public String getComName() {
		return comName == null ? "" : comName.trim();
	}

	public void setComName(String comName) {
		this.comName = comName == null ? null : comName.trim();
	}

	public String getComOrders() {
		return comOrders == null ? "" : comOrders.trim();
	}

	public void setComOrders(String comOrders) {
		this.comOrders = comOrders == null ? null : comOrders.trim();
	}

	public String getFlowLock() {
		return flowLock == null ? "" : flowLock.trim();
	}

	public void setFlowLock(String flowLock) {
		this.flowLock = flowLock == null ? null : flowLock.trim();
	}

	public String getIsBack() {
		return isBack == null ? "" : isBack.trim();
	}

	public void setIsBack(String isBack) {
		this.isBack = isBack == null ? null : isBack.trim();
	}

	public int getNotPassNum() {
		return notPassNum;
	}

	public void setNotPassNum(int notPassNum) {
		this.notPassNum = notPassNum;
	}

	public String getIsReport() {
		return isReport == null ? "" : isReport.trim();
	}

	public void setIsReport(String isReport) {
		this.isReport = isReport == null ? null : isReport.trim();
	}

	public String getAppInsHistID() {
		return appInsHistID == null ? "" : appInsHistID.trim();
	}

	public void setAppInsHistID(String appInsHistID) {
		this.appInsHistID = appInsHistID == null ? null : appInsHistID.trim();
	}

	public String getOldWorkItemID() {
		return oldWorkItemID == null ? "" : oldWorkItemID.trim();
	}

	public void setOldWorkItemID(String oldWorkItemID) {
		this.oldWorkItemID = oldWorkItemID == null ? null : oldWorkItemID.trim();
	}

	public String getIsFlag() {
		return isFlag == null ? "" : isFlag.trim();
	}

	public void setIsFlag(String isFlag) {
		this.isFlag = isFlag == null ? null : isFlag.trim();
	}

	public String getSendToJuID() {
		return sendToJuID == null ? "" : sendToJuID.trim();
	}

	public void setSendToJuID(String sendToJuID) {
		this.sendToJuID = sendToJuID == null ? null : sendToJuID.trim();
	}

	public String getIsFlagZhb() {
		return isFlagZhb == null ? "" : isFlagZhb.trim();
	}

	public void setIsFlagZhb(String isFlagZhb) {
		this.isFlagZhb = isFlagZhb == null ? null : isFlagZhb.trim();
	}

	public String getIsReportZhb() {
		return isReportZhb == null ? "" : isReportZhb.trim();
	}

	public void setIsReportZhb(String isReportZhb) {
		this.isReportZhb = isReportZhb == null ? null : isReportZhb.trim();
	}

	public String getSendToZhbID() {
		return sendToZhbID == null ? "" : sendToZhbID.trim();
	}

	public void setSendToZhbID(String sendToZhbID) {
		this.sendToZhbID = sendToZhbID == null ? null : sendToZhbID.trim();
	}

	public String getAppInsHistIDZhb() {
		return appInsHistIDZhb == null ? "" : appInsHistIDZhb.trim();
	}

	public void setAppInsHistIDZhb(String appInsHistIDZhb) {
		this.appInsHistIDZhb = appInsHistIDZhb == null ? null : appInsHistIDZhb.trim();
	}

	public String getBeginPer() {
		return beginPer == null ? "" : beginPer.trim();
	}

	public void setBeginPer(String beginPer) {
		this.beginPer = beginPer == null ? null : beginPer.trim();
	}

	public String getFiId() {
		return fiId == null ? "" : fiId.trim();
	}

	public void setFiId(String fiId) {
		this.fiId = fiId == null ? null : fiId.trim();
	}

	public String getSetDir() {
		return setDir == null ? "" : setDir.trim();
	}

	public void setSetDir(String setDir) {
		this.setDir = setDir == null ? null : setDir.trim();
	}

	public String getAccountUnitId() {
		return accountUnitId == null ? "" : accountUnitId.trim();
	}

	public void setAccountUnitId(String accountUnitId) {
		this.accountUnitId = accountUnitId == null ? null : accountUnitId.trim();
	}

	public String getAccountUnitCode() {
		return accountUnitCode == null ? "" : accountUnitCode.trim();
	}

	public void setAccountUnitCode(String accountUnitCode) {
		this.accountUnitCode = accountUnitCode == null ? null : accountUnitCode.trim();
	}

	public String getAccountUnitName() {
		return accountUnitName == null ? "" : accountUnitName.trim();
	}

	public void setAccountUnitName(String accountUnitName) {
		this.accountUnitName = accountUnitName == null ? null : accountUnitName.trim();
	}

	public String getAssessUnitId() {
		return assessUnitId == null ? "" : assessUnitId.trim();
	}

	public void setAssessUnitId(String assessUnitId) {
		this.assessUnitId = assessUnitId == null ? null : assessUnitId.trim();
	}

	public String getAssessUnitCode() {
		return assessUnitCode == null ? "" : assessUnitCode.trim();
	}

	public void setAssessUnitCode(String assessUnitCode) {
		this.assessUnitCode = assessUnitCode == null ? null : assessUnitCode.trim();
	}

	public String getAssessUnitName() {
		return assessUnitName == null ? "" : assessUnitName.trim();
	}

	public void setAssessUnitName(String assessUnitName) {
		this.assessUnitName = assessUnitName == null ? null : assessUnitName.trim();
	}

	public String getResponseUnitId() {
		return responseUnitId == null ? "" : responseUnitId.trim();
	}

	public void setResponseUnitId(String responseUnitId) {
		this.responseUnitId = responseUnitId == null ? null : responseUnitId.trim();
	}

	public String getResponseUnitCode() {
		return responseUnitCode == null ? "" : responseUnitCode.trim();
	}

	public void setResponseUnitCode(String responseUnitCode) {
		this.responseUnitCode = responseUnitCode == null ? null : responseUnitCode.trim();
	}

	public String getResponseUnitName() {
		return responseUnitName == null ? "" : responseUnitName.trim();
	}

	public void setResponseUnitName(String responseUnitName) {
		this.responseUnitName = responseUnitName == null ? null : responseUnitName.trim();
	}

	public String getAccountDepId() {
		return accountDepId == null ? "" : accountDepId.trim();
	}

	public void setAccountDepId(String accountDepId) {
		this.accountDepId = accountDepId == null ? null : accountDepId.trim();
	}

	public String getAccountDepCode() {
		return accountDepCode == null ? "" : accountDepCode.trim();
	}

	public void setAccountDepCode(String accountDepCode) {
		this.accountDepCode = accountDepCode == null ? null : accountDepCode.trim();
	}

	public String getAccountDepName() {
		return accountDepName == null ? "" : accountDepName.trim();
	}

	public void setAccountDepName(String accountDepName) {
		this.accountDepName = accountDepName == null ? null : accountDepName.trim();
	}

	public String getIsSign() {
		return isSign == null ? "" : isSign.trim();
	}

	public void setIsSign(String isSign) {
		this.isSign = isSign == null ? null : isSign.trim();
	}

	public String getBusiTypeId() {
		return busiTypeId == null ? "" : busiTypeId.trim();
	}

	public void setBusiTypeId(String busiTypeId) {
		this.busiTypeId = busiTypeId == null ? null : busiTypeId.trim();
	}

	public String getProjCode() {
		return projCode == null ? "" : projCode.trim();
	}

	public void setProjCode(String projCode) {
		this.projCode = projCode == null ? null : projCode.trim();
	}

	public String getSecondCode() {
		return secondCode == null ? "" : secondCode.trim();
	}

	public void setSecondCode(String secondCode) {
		this.secondCode = secondCode == null ? null : secondCode.trim();
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getCurrency() {
		return currency == null ? "" : currency.trim();
	}

	public void setCurrency(String currency) {
		this.currency = currency == null ? null : currency.trim();
	}

	public String getExchangeRate() {
		return exchangeRate == null ? "" : exchangeRate.trim();
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate == null ? null : exchangeRate.trim();
	}

	public String getNumOfSheets() {
		return numOfSheets == null ? "" : numOfSheets.trim();
	}

	public void setNumOfSheets(String numOfSheets) {
		this.numOfSheets = numOfSheets == null ? null : numOfSheets.trim();
	}

	public String getSummary() {
		return summary == null ? "" : summary.trim();
	}

	public void setSummary(String summary) {
		this.summary = summary == null ? null : summary.trim();
	}

	public String getTaxRate() {
		return taxRate == null ? "" : taxRate.trim();
	}

	public void setTaxRate(String taxRate) {
		this.taxRate = taxRate == null ? null : taxRate.trim();
	}

	public String getPayNatureId() {
		return payNatureId == null ? "" : payNatureId.trim();
	}

	public void setPayNatureId(String payNatureId) {
		this.payNatureId = payNatureId == null ? null : payNatureId.trim();
	}

	public String getPayNatureCode() {
		return payNatureCode == null ? "" : payNatureCode.trim();
	}

	public void setPayNatureCode(String payNatureCode) {
		this.payNatureCode = payNatureCode == null ? null : payNatureCode.trim();
	}

	public String getPayNatureName() {
		return payNatureName == null ? "" : payNatureName.trim();
	}

	public void setPayNatureName(String payNatureName) {
		this.payNatureName = payNatureName == null ? null : payNatureName.trim();
	}

	public Date getExpDate() {
		return ExpDate;
	}

	public void setExpDate(Date ExpDate) {
		this.ExpDate = ExpDate;
	}

	public Date getEstPayDate() {
		return EstPayDate;
	}

	public void setEstPayDate(Date EstPayDate) {
		this.EstPayDate = EstPayDate;
	}

	public String getNotDisplay() {
		return notDisplay == null ? "" : notDisplay.trim();
	}

	public void setNotDisplay(String notDisplay) {
		this.notDisplay = notDisplay == null ? null : notDisplay.trim();
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

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getIsSend() {
		return isSend == null ? "" : isSend.trim();
	}

	public void setIsSend(String isSend) {
		this.isSend = isSend == null ? null : isSend.trim();
	}

	public String getSecondIDCodeBh() {
		return secondIDCodeBh == null ? "" : secondIDCodeBh.trim();
	}

	public void setSecondIDCodeBh(String secondIDCodeBh) {
		this.secondIDCodeBh = secondIDCodeBh == null ? null : secondIDCodeBh.trim();
	}

	public String getCwStatus() {
		return cwStatus == null ? "" : cwStatus.trim();
	}

	public void setCwStatus(String cwStatus) {
		this.cwStatus = cwStatus == null ? null : cwStatus.trim();
	}

	public String getCwStatusRemark() {
		return cwStatusRemark == null ? "" : cwStatusRemark.trim();
	}

	public void setCwStatusRemark(String cwStatusRemark) {
		this.cwStatusRemark = cwStatusRemark == null ? null : cwStatusRemark.trim();
	}

	public String getAuditWorkitemID() {
		return auditWorkitemID == null ? "" : auditWorkitemID.trim();
	}

	public void setAuditWorkitemID(String auditWorkitemID) {
		this.auditWorkitemID = auditWorkitemID == null ? null : auditWorkitemID.trim();
	}

	public String getAuditSys() {
		return auditSys == null ? "" : auditSys.trim();
	}

	public void setAuditSys(String auditSys) {
		this.auditSys = auditSys == null ? null : auditSys.trim();
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public BigDecimal getTcje() {
		return tcje;
	}

	public void setTcje(BigDecimal tcje) {
		this.tcje = tcje;
	}

	public BigDecimal getTcse() {
		return tcse;
	}

	public void setTcse(BigDecimal tcse) {
		this.tcse = tcse;
	}

	public BigDecimal getTchljjsje() {
		return tchljjsje;
	}

	public void setTchljjsje(BigDecimal tchljjsje) {
		this.tchljjsje = tchljjsje;
	}

	public BigDecimal getBqtchse() {
		return bqtchse;
	}

	public void setBqtchse(BigDecimal bqtchse) {
		this.bqtchse = bqtchse;
	}

	public BigDecimal getBqtchjsje() {
		return bqtchjsje;
	}

	public void setBqtchjsje(BigDecimal bqtchjsje) {
		this.bqtchjsje = bqtchjsje;
	}

	public String getTaxCountWay() {
		return taxCountWay == null ? "" : taxCountWay.trim();
	}

	public void setTaxCountWay(String taxCountWay) {
		this.taxCountWay = taxCountWay == null ? null : taxCountWay.trim();
	}

	public String getZJGCXMNM() {
		return ZJGCXMNM == null ? "" : ZJGCXMNM.trim();
	}

	public void setZJGCXMNM(String ZJGCXMNM) {
		this.ZJGCXMNM = ZJGCXMNM == null ? null : ZJGCXMNM.trim();
	}

	public String getZJGCXMXMBH() {
		return ZJGCXMXMBH == null ? "" : ZJGCXMXMBH.trim();
	}

	public void setZJGCXMXMBH(String ZJGCXMXMBH) {
		this.ZJGCXMXMBH = ZJGCXMXMBH == null ? null : ZJGCXMXMBH.trim();
	}

	public String getZJGCXMXMMC() {
		return ZJGCXMXMMC == null ? "" : ZJGCXMXMMC.trim();
	}

	public void setZJGCXMXMMC(String ZJGCXMXMMC) {
		this.ZJGCXMXMMC = ZJGCXMXMMC == null ? null : ZJGCXMXMMC.trim();
	}

	public BigDecimal getBqtchjsnotax() {
		return bqtchjsnotax;
	}

	public void setBqtchjsnotax(BigDecimal bqtchjsnotax) {
		this.bqtchjsnotax = bqtchjsnotax;
	}

	public BigDecimal getTcnotax() {
		return tcnotax;
	}

	public void setTcnotax(BigDecimal tcnotax) {
		this.tcnotax = tcnotax;
	}

	public BigDecimal getContractCost() {
		return contractCost;
	}

	public void setContractCost(BigDecimal contractCost) {
		this.contractCost = contractCost;
	}

	public String getZjxmhtbNm() {
		return zjxmhtbNm == null ? "" : zjxmhtbNm.trim();
	}

	public void setZjxmhtbNm(String zjxmhtbNm) {
		this.zjxmhtbNm = zjxmhtbNm == null ? null : zjxmhtbNm.trim();
	}

	public String getZjxmhtbBh() {
		return zjxmhtbBh == null ? "" : zjxmhtbBh.trim();
	}

	public void setZjxmhtbBh(String zjxmhtbBh) {
		this.zjxmhtbBh = zjxmhtbBh == null ? null : zjxmhtbBh.trim();
	}

	public String getZjxmhtbMc() {
		return zjxmhtbMc == null ? "" : zjxmhtbMc.trim();
	}

	public void setZjxmhtbMc(String zjxmhtbMc) {
		this.zjxmhtbMc = zjxmhtbMc == null ? null : zjxmhtbMc.trim();
	}

	public String getIsRela() {
		return isRela == null ? "" : isRela.trim();
	}

	public void setIsRela(String isRela) {
		this.isRela = isRela == null ? null : isRela.trim();
	}

	public String getOrgCertificate() {
		return orgCertificate == null ? "" : orgCertificate.trim();
	}

	public void setOrgCertificate(String orgCertificate) {
		this.orgCertificate = orgCertificate == null ? null : orgCertificate.trim();
	}

	public String getIsFirst() {
		return isFirst == null ? "" : isFirst.trim();
	}

	public void setIsFirst(String isFirst) {
		this.isFirst = isFirst == null ? null : isFirst.trim();
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

	public String getIsFished() {
		return isFished == null ? "" : isFished.trim();
	}

	public void setIsFished(String isFished) {
		this.isFished = isFished == null ? null : isFished.trim();
	}

	public String getUseCount() {
		return useCount == null ? "" : useCount.trim();
	}

	public void setUseCount(String useCount) {
		this.useCount = useCount == null ? null : useCount.trim();
	}

	public String getIsMaxPeriod() {
		return isMaxPeriod == null ? "" : isMaxPeriod.trim();
	}

	public void setIsMaxPeriod(String isMaxPeriod) {
		this.isMaxPeriod = isMaxPeriod == null ? null : isMaxPeriod.trim();
	}

	public String getSignedNo() {
		return signedNo == null ? "" : signedNo.trim();
	}

	public void setSignedNo(String signedNo) {
		this.signedNo = signedNo == null ? null : signedNo.trim();
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getAttachmentInfo() {
		return attachmentInfo == null ? "" : attachmentInfo.trim();
	}

	public void setAttachmentInfo(String attachmentInfo) {
		this.attachmentInfo = attachmentInfo == null ? null : attachmentInfo.trim();
	}

	public String getAppraisal() {
		return appraisal == null ? "" : appraisal.trim();
	}

	public void setAppraisal(String appraisal) {
		this.appraisal = appraisal == null ? null : appraisal.trim();
	}

	public String getSerialNumber() {
		return serialNumber == null ? "" : serialNumber.trim();
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber == null ? null : serialNumber.trim();
	}

	public String getInitSerialNumber() {
		return initSerialNumber == null ? "" : initSerialNumber.trim();
	}

	public void setInitSerialNumber(String initSerialNumber) {
		this.initSerialNumber = initSerialNumber == null ? null : initSerialNumber.trim();
	}

	public List<ZxSaProjectSettleAuditItem> getProjectSettleAuditItemList() {
		return projectSettleAuditItemList == null ? Lists.newArrayList() : projectSettleAuditItemList;
	}

	public void setProjectSettleAuditItemList(List<ZxSaProjectSettleAuditItem> projectSettleAuditItemList) {
		this.projectSettleAuditItemList = projectSettleAuditItemList == null ? null : projectSettleAuditItemList;
	}

	public List<ZxErpFile> getZxErpFileList() {
		return zxErpFileList == null ? Lists.newArrayList() : zxErpFileList;
	}

	public void setZxErpFileList(List<ZxErpFile> zxErpFileList) {
		this.zxErpFileList = zxErpFileList == null ? null : zxErpFileList;
	}

	public String getReportPersonTel() {
		return reportPersonTel == null ? "" : reportPersonTel.trim();
	}

	public void setReportPersonTel(String reportPersonTel) {
		this.reportPersonTel = reportPersonTel == null ? null : reportPersonTel.trim();
	}

	public String getRemark() {
		return remark == null ? "" : remark.trim();
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public BigDecimal getWorkContractAmt() {
		return workContractAmt;
	}

	public void setWorkContractAmt(BigDecimal workContractAmt) {
		this.workContractAmt = workContractAmt;
	}

	public BigDecimal getWorkChangeAmt() {
		return workChangeAmt;
	}

	public void setWorkChangeAmt(BigDecimal workChangeAmt) {
		this.workChangeAmt = workChangeAmt;
	}

	public BigDecimal getWorkThisAmt() {
		return workThisAmt;
	}

	public void setWorkThisAmt(BigDecimal workThisAmt) {
		this.workThisAmt = workThisAmt;
	}

	public BigDecimal getWorkTotalAmt() {
		return workTotalAmt;
	}

	public void setWorkTotalAmt(BigDecimal workTotalAmt) {
		this.workTotalAmt = workTotalAmt;
	}

	public BigDecimal getWorkThisAmtNoTax() {
		return workThisAmtNoTax;
	}

	public void setWorkThisAmtNoTax(BigDecimal workThisAmtNoTax) {
		this.workThisAmtNoTax = workThisAmtNoTax;
	}

	public BigDecimal getWorkThisAmtTax() {
		return workThisAmtTax;
	}

	public void setWorkThisAmtTax(BigDecimal workThisAmtTax) {
		this.workThisAmtTax = workThisAmtTax;
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

	public BigDecimal getPayTotalAmt() {
		return payTotalAmt;
	}

	public void setPayTotalAmt(BigDecimal payTotalAmt) {
		this.payTotalAmt = payTotalAmt;
	}

	public BigDecimal getPayMaterialAmt() {
		return payMaterialAmt;
	}

	public void setPayMaterialAmt(BigDecimal payMaterialAmt) {
		this.payMaterialAmt = payMaterialAmt;
	}

	public BigDecimal getPayMachineAmt() {
		return payMachineAmt;
	}

	public void setPayMachineAmt(BigDecimal payMachineAmt) {
		this.payMachineAmt = payMachineAmt;
	}

	public BigDecimal getPayTempAmt() {
		return payTempAmt;
	}

	public void setPayTempAmt(BigDecimal payTempAmt) {
		this.payTempAmt = payTempAmt;
	}

	public BigDecimal getPayFineAmt() {
		return payFineAmt;
	}

	public void setPayFineAmt(BigDecimal payFineAmt) {
		this.payFineAmt = payFineAmt;
	}

	public BigDecimal getPayRecoupAmt() {
		return payRecoupAmt;
	}

	public void setPayRecoupAmt(BigDecimal payRecoupAmt) {
		this.payRecoupAmt = payRecoupAmt;
	}

	public BigDecimal getPayOtherAmt() {
		return payOtherAmt;
	}

	public void setPayOtherAmt(BigDecimal payOtherAmt) {
		this.payOtherAmt = payOtherAmt;
	}

	public int getStatementNo() {
		return statementNo;
	}

	public void setStatementNo(int statementNo) {
		this.statementNo = statementNo;
	}

	public List<ZxSaProjectWorkSettleItem> getWorkSettleList() {
		return workSettleList == null ? Lists.newArrayList() : workSettleList;
	}

	public void setWorkSettleList(List<ZxSaProjectWorkSettleItem> workSettleList) {
		this.workSettleList = workSettleList == null ? null : workSettleList;
	}

	public List<ZxSaProjectWorkSettleItem> getWorkSettleItemList() {
		return workSettleItemList == null ? Lists.newArrayList() : workSettleItemList;
	}

	public void setWorkSettleItemList(List<ZxSaProjectWorkSettleItem> workSettleItemList) {
		this.workSettleItemList = workSettleItemList == null ? null : workSettleItemList;
	}

	public List<ZxSaProjectPaySettleItem> getPaySettleList() {
		return paySettleList == null ? Lists.newArrayList() : paySettleList;
	}

	public void setPaySettleList(List<ZxSaProjectPaySettleItem> paySettleList) {
		this.paySettleList = paySettleList == null ? null : paySettleList;
	}

	public List<ZxSaProjectPaySettleItem> getPaySettleItemList() {
		return paySettleItemList == null ? Lists.newArrayList() : paySettleItemList;
	}

	public void setPaySettleItemList(List<ZxSaProjectPaySettleItem> paySettleItemList) {
		this.paySettleItemList = paySettleItemList == null ? null : paySettleItemList;
	}

	public String getWorkId() {
		return workId == null ? "" : workId.trim();
	}

	public void setWorkId(String workId) {
		this.workId = workId == null ? null : workId.trim();
	}

	public String getApih5FlowStatus() {
		return apih5FlowStatus == null ? "" : apih5FlowStatus.trim();
	}

	public void setApih5FlowStatus(String apih5FlowStatus) {
		this.apih5FlowStatus = apih5FlowStatus == null ? null : apih5FlowStatus.trim();
	}

	public String getOpinionField() {
		return opinionField == null ? "" : opinionField.trim();
	}

	public void setOpinionField(String opinionField) {
		this.opinionField = opinionField == null ? null : opinionField.trim();
	}

	public String getOpinionField1() {
		return opinionField1 == null ? "" : opinionField1.trim();
	}

	public void setOpinionField1(String opinionField1) {
		this.opinionField1 = opinionField1 == null ? null : opinionField1.trim();
	}

	public String getOpinionField2() {
		return opinionField2 == null ? "" : opinionField2.trim();
	}

	public void setOpinionField2(String opinionField2) {
		this.opinionField2 = opinionField2 == null ? null : opinionField2.trim();
	}

	public String getOpinionField3() {
		return opinionField3 == null ? "" : opinionField3.trim();
	}

	public void setOpinionField3(String opinionField3) {
		this.opinionField3 = opinionField3 == null ? null : opinionField3.trim();
	}

	public String getOpinionField4() {
		return opinionField4 == null ? "" : opinionField4.trim();
	}

	public void setOpinionField4(String opinionField4) {
		this.opinionField4 = opinionField4 == null ? null : opinionField4.trim();
	}

	public String getOpinionField5() {
		return opinionField5 == null ? "" : opinionField5.trim();
	}

	public void setOpinionField5(String opinionField5) {
		this.opinionField5 = opinionField5 == null ? null : opinionField5.trim();
	}

	public String getOpinionField6() {
		return opinionField6 == null ? "" : opinionField6.trim();
	}

	public void setOpinionField6(String opinionField6) {
		this.opinionField6 = opinionField6 == null ? null : opinionField6.trim();
	}

	public String getOpinionField7() {
		return opinionField7 == null ? "" : opinionField7.trim();
	}

	public void setOpinionField7(String opinionField7) {
		this.opinionField7 = opinionField7 == null ? null : opinionField7.trim();
	}

	public String getOpinionField8() {
		return opinionField8 == null ? "" : opinionField8.trim();
	}

	public void setOpinionField8(String opinionField8) {
		this.opinionField8 = opinionField8 == null ? null : opinionField8.trim();
	}

	public String getOpinionField9() {
		return opinionField9 == null ? "" : opinionField9.trim();
	}

	public void setOpinionField9(String opinionField9) {
		this.opinionField9 = opinionField9 == null ? null : opinionField9.trim();
	}

	public String getOpinionField10() {
		return opinionField10 == null ? "" : opinionField10.trim();
	}

	public void setOpinionField10(String opinionField10) {
		this.opinionField10 = opinionField10 == null ? null : opinionField10.trim();
	}

	public Date getPeriodTimeWasted() {
		return periodTimeWasted;
	}

	public void setPeriodTimeWasted(Date periodTimeWasted) {
		this.periodTimeWasted = periodTimeWasted;
	}

	public String getProjectWorkSettleId() {
		return projectWorkSettleId == null ? "" : projectWorkSettleId.trim();
	}

	public void setProjectWorkSettleId(String projectWorkSettleId) {
		this.projectWorkSettleId = projectWorkSettleId == null ? null : projectWorkSettleId.trim();
	}

	public String getProjectPaySettleId() {
		return projectPaySettleId == null ? "" : projectPaySettleId.trim();
	}

	public void setProjectPaySettleId(String projectPaySettleId) {
		this.projectPaySettleId = projectPaySettleId == null ? null : projectPaySettleId.trim();
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

	public List<ZxErpFile> getTextAttachmentList() {
		return textAttachmentList == null ? Lists.newArrayList() : textAttachmentList;
	}

	public void setTextAttachmentList(List<ZxErpFile> textAttachmentList) {
		this.textAttachmentList = textAttachmentList == null ? null : textAttachmentList;
	}

	public String getOpinionField11() {
		return opinionField11 == null ? "" : opinionField11.trim();
	}

	public void setOpinionField11(String opinionField11) {
		this.opinionField11 = opinionField11 == null ? null : opinionField11.trim();
	}

	public String getOpinionField12() {
		return opinionField12 == null ? "" : opinionField12.trim();
	}

	public void setOpinionField12(String opinionField12) {
		this.opinionField12 = opinionField12 == null ? null : opinionField12.trim();
	}

	public String getOpinionField13() {
		return opinionField13 == null ? "" : opinionField13.trim();
	}

	public void setOpinionField13(String opinionField13) {
		this.opinionField13 = opinionField13 == null ? null : opinionField13.trim();
	}

	public String getOpinionField14() {
		return opinionField14 == null ? "" : opinionField14.trim();
	}

	public void setOpinionField14(String opinionField14) {
		this.opinionField14 = opinionField14 == null ? null : opinionField14.trim();
	}

	public String getOpinionField15() {
		return opinionField15 == null ? "" : opinionField15.trim();
	}

	public void setOpinionField15(String opinionField15) {
		this.opinionField15 = opinionField15 == null ? null : opinionField15.trim();
	}

	public String getOpinionField16() {
		return opinionField16 == null ? "" : opinionField16.trim();
	}

	public void setOpinionField16(String opinionField16) {
		this.opinionField16 = opinionField16 == null ? null : opinionField16.trim();
	}

	public String getSettleType() {
		return settleType == null ? "" : settleType.trim();
	}

	public void setSettleType(String settleType) {
		this.settleType = settleType == null ? null : settleType.trim();
	}

	public String getIsDeduct() {
		return isDeduct == null ? "" : isDeduct.trim();
	}

	public void setIsDeduct(String isDeduct) {
		this.isDeduct = isDeduct == null ? null : isDeduct.trim();
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
