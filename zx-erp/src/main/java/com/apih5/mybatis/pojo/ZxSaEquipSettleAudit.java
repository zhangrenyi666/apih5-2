package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojoFlow;
import com.google.common.collect.Lists;

public class ZxSaEquipSettleAudit extends BasePojoFlow {
    // 主键ID
    private String zxSaEquipSettleAuditId;

    // 物资结算表id
    private String billID;

    // 自动编号
    private int autoNum=0;

    // 结算单编号
    private String billNo;

    // 结算次数
    private int countNo=0;

    // 项目id
    private String orgID;

    // 签认单编号
    private String signedNos;

    // 项目名称
    private String orgName;

    // 结算期次
    private String period;

    // 结算期次(给前台用的date类型)
    private Date periodDate;

    // 合同id
    private String contractID;

    // 合同编号
    private String contractNo;

    // 合同名称
    private String contractName;

    // 乙方id
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

    // 本期应付金额(元)
    private BigDecimal thisPayAmt;

    // 开累应付金额(元)
    private BigDecimal totalPayAmt;

    // 业务日期
    private Date businessDate;

    // 填报日期
    private Date reportDate;

    // 填报人
    private String reportPerson;

    // 备注
    private String remark;

    // 计算人
    private String countPerson;

    // 复核人
    private String reCountPerson;

    // 发起人
    private String flowBeginPerson;

    // 机械性能及运转情况评价
    private String appraisal;

    // 流程进度id
    private String instProcessID;

    // 流程开始时间
    private Date flowBeginDate;

    // 流程结束时间
    private Date flowEndDate;

    // 状态
    private String auditStatus;

    // 所属公司id
    private String comID;

    // 所属公司名称
    private String comName;

    // 所属公司排序
    private String comOrders;

    // 合同类型
    private String contrType;

    // 是否锁定
    private String flowLock;

    // 结算内容
    private String content;

    // 0
    private String isBack;

    // 重新评审次数
    private int notPassNum=0;

    // 老流程id
    private String oldWorkItemID;

    // 是否上报局
    private String isReport;

    // 0
    private String appInsHistID;

    // 0
    private String payRemark;

    // 0
    private String shortName;

    // 0
    private String notDisplay;

    // 0
    private String isFlag;

    // 0
    private String isFlagZhb;

    // 推送时间
    private Date sendDate;

    // 推送状态
    private String isSend;

    // 财务审批状态
    private String cwStatus;

    // 财务审批状态说明
    private String cwStatusRemark;

    // 合同类别
    private String code7;

    // 结算金额差值
    private BigDecimal tcje;

    // 税额差值
    private BigDecimal tcse;

    // 调差后累计结算金额
    private BigDecimal tchljjsje;

    // 本期调差后税额
    private BigDecimal bqtchse;

    // 本期结算不含税金额(元)
    private BigDecimal thisAmtNoTax;

    // 本期结算税额(元)
    private BigDecimal thisAmtTax;

    // 本期调整后结算金额
    private BigDecimal bqtchjsje;

    // 计税方法
    private String taxCountWay;

    // 0
    private String ZJGCXMNM;

    // 0
    private String ZJGCXMXMBH;

    // 项目
    private String ZJGCXMXMMC;

    // 核算单位内码
    private String accountUnitId;

    // 核算单位编码
    private String accountUnitCode;

    // 核算单位名称
    private String accountUnitName;

    // 核算部门内码
    private String accountDepId;

    // 核算部门编号
    private String accountDepCode;

    // 核算部门名称
    private String accountDepName;

    // 债权人编号
    private String secondCode;

    // 债权人编号
    private String secondIDCodeBh;

    // 0
    private BigDecimal contractCost;

    // 是否被审核
    private String isAudit;

    // 0
    private String upWorkItemID;

    // 0
    private String oaOrgID;

    // 0
    private String taxRate;

    // 是否抵扣
    private String isDeduct;

    // 是否首次结算
    private String isFirst;

    // 0
    private BigDecimal bqtchjsnotax;

    // 0
    private String zjxmhtbNm;

    // 0
    private String zjxmhtbBh;

    // 0
    private String zjxmhtbMc;

    // 0
    private String isRela;

    // 统一社会信用代码
    private String orgCertificate;

    // 结算方向
    private String setDir;

    // 是否签认
    private String isSign;

    // 币种
    private String currency;

    // 汇率
    private String exchangeRate;

    // 附件张数
    private String numOfSheets;

    // 计量确认日期
    private Date confirmDate;

    // 摘要
    private String summary;

    // 到期日期
    private Date expDate;

    // 预计付款日期
    private Date estPayDate;

    // 财务系统id
    private String fiId;

    // 责任单位名称
    private String responseUnitName;

    // 责任单位编码
    private String responseUnitCode;

    // 考核单位名称
    private String assessUnitName;

    // 考核单位编码
    private String assessUnitCode;

    // 流程id
    private String workId;

    // 流程状态
    private String apih5FlowStatus;

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

    // 附件list
    private List<ZxErpFile> zxErpFileList;
    
    //正文List
    private List<ZxErpFile> zxErpMainFileList;

    // 结算单明细list
    private List<ZxSaEquipSettleAuditItem> zxSaEquipSettleAuditItemList;

    // 清单明细list
    private List<ZxSaEquipResSettleItem> zxSaEquipResSettleItemList;

    // 清单里面的字段=主键id
    private String zxSaEquipResSettleId;

    // 含税合同金额(元)
    private BigDecimal contractAmt;

    // 变更后含税合同金额(元)
    private BigDecimal changeAmt;

    // 上期末清单结算金额(元)
    private BigDecimal upAmtRes;

    // 本期清单结算含税金额(元)
    private BigDecimal thisAmtRes;

    // 累计清单结算含税金额(元)
    private BigDecimal totalAmtRes;

    // 本期清单结算不含税金额(元)
    private BigDecimal thisAmtNoTaxRes;

    // 本期清单结算税额(元)
    private BigDecimal thisAmtTaxRes;

    // 支付项里面的字段=主键id
    private String zxSaEquipPaySettleId;

    // 本期支付项结算金额(元)
    private BigDecimal thisAmtPay;

    // 累计支付项结算金额(元)
    private BigDecimal totalAmtPay;

    // 上期末累计支付项结算金额(元)
    private BigDecimal upAmtPay;

    // 本期进退场费
    private BigDecimal inOutAmt;

    // 上期末进退场费
    private BigDecimal upInOutAmt;

    // 本期奖罚
    private BigDecimal fineAmt;

    // 上期末奖罚
    private BigDecimal upFineAmt;

    // 本期伙食费
    private BigDecimal foodAmt;

    // 上期末伙食费
    private BigDecimal upFoodAmt;

    // 本期其他款项
    private BigDecimal otherAmt;

    // 上期末其他款项
    private BigDecimal upOtherAmt;

    // 本期末支付项结算不含税金额(元）
    private BigDecimal thisAmtNoTaxPay;

    // 本期末支付项结算金额(元)
    private BigDecimal thisAmtTaxPay;

    //是否是已最终结算
    private String settleTypeFlag;
    
    //查询审核未通过的所有数据
    private String apih5FlowStatusFlagNo2;
    
    // 支付项list
    private List<ZxSaEquipPaySettleItem> zxSaEquipPaySettleItemList;
    
    public String getZxSaEquipSettleAuditId() {
        return zxSaEquipSettleAuditId == null ? "" : zxSaEquipSettleAuditId.trim();
    }

    public void setZxSaEquipSettleAuditId(String zxSaEquipSettleAuditId) {
        this.zxSaEquipSettleAuditId = zxSaEquipSettleAuditId == null ? null : zxSaEquipSettleAuditId.trim();
    }

    public String getBillID() {
        return billID == null ? "" : billID.trim();
    }

    public void setBillID(String billID) {
        this.billID = billID == null ? null : billID.trim();
    }

    public int getAutoNum() {
		return autoNum;
	}

	public void setAutoNum(int autoNum) {
		this.autoNum = autoNum;
	}

	public String getBillNo() {
        return billNo == null ? "" : billNo.trim();
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public int getCountNo() {
        return countNo;
    }

    public void setCountNo(int countNo) {
        this.countNo = countNo;
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getSignedNos() {
        return signedNos == null ? "" : signedNos.trim();
    }

    public void setSignedNos(String signedNos) {
        this.signedNos = signedNos == null ? null : signedNos.trim();
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

    public Date getPeriodDate() {
        return periodDate;
    }

    public void setPeriodDate(Date periodDate) {
        this.periodDate = periodDate;
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

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public String getAppraisal() {
        return appraisal == null ? "" : appraisal.trim();
    }

    public void setAppraisal(String appraisal) {
        this.appraisal = appraisal == null ? null : appraisal.trim();
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

    public String getContrType() {
        return contrType == null ? "" : contrType.trim();
    }

    public void setContrType(String contrType) {
        this.contrType = contrType == null ? null : contrType.trim();
    }

    public String getFlowLock() {
        return flowLock == null ? "" : flowLock.trim();
    }

    public void setFlowLock(String flowLock) {
        this.flowLock = flowLock == null ? null : flowLock.trim();
    }

    public String getContent() {
        return content == null ? "" : content.trim();
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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

    public String getOldWorkItemID() {
        return oldWorkItemID == null ? "" : oldWorkItemID.trim();
    }

    public void setOldWorkItemID(String oldWorkItemID) {
        this.oldWorkItemID = oldWorkItemID == null ? null : oldWorkItemID.trim();
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

    public String getPayRemark() {
        return payRemark == null ? "" : payRemark.trim();
    }

    public void setPayRemark(String payRemark) {
        this.payRemark = payRemark == null ? null : payRemark.trim();
    }

    public String getShortName() {
        return shortName == null ? "" : shortName.trim();
    }

    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    public String getNotDisplay() {
        return notDisplay == null ? "" : notDisplay.trim();
    }

    public void setNotDisplay(String notDisplay) {
        this.notDisplay = notDisplay == null ? null : notDisplay.trim();
    }

    public String getIsFlag() {
        return isFlag == null ? "" : isFlag.trim();
    }

    public void setIsFlag(String isFlag) {
        this.isFlag = isFlag == null ? null : isFlag.trim();
    }

    public String getIsFlagZhb() {
        return isFlagZhb == null ? "" : isFlagZhb.trim();
    }

    public void setIsFlagZhb(String isFlagZhb) {
        this.isFlagZhb = isFlagZhb == null ? null : isFlagZhb.trim();
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

    public String getCode7() {
        return code7 == null ? "" : code7.trim();
    }

    public void setCode7(String code7) {
        this.code7 = code7 == null ? null : code7.trim();
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

    public String getSecondCode() {
        return secondCode == null ? "" : secondCode.trim();
    }

    public void setSecondCode(String secondCode) {
        this.secondCode = secondCode == null ? null : secondCode.trim();
    }

    public String getSecondIDCodeBh() {
        return secondIDCodeBh == null ? "" : secondIDCodeBh.trim();
    }

    public void setSecondIDCodeBh(String secondIDCodeBh) {
        this.secondIDCodeBh = secondIDCodeBh == null ? null : secondIDCodeBh.trim();
    }

    public BigDecimal getContractCost() {
        return contractCost;
    }

    public void setContractCost(BigDecimal contractCost) {
        this.contractCost = contractCost;
    }

    public String getIsAudit() {
        return isAudit == null ? "" : isAudit.trim();
    }

    public void setIsAudit(String isAudit) {
        this.isAudit = isAudit == null ? null : isAudit.trim();
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

    public String getIsFirst() {
        return isFirst == null ? "" : isFirst.trim();
    }

    public void setIsFirst(String isFirst) {
        this.isFirst = isFirst == null ? null : isFirst.trim();
    }

    public BigDecimal getBqtchjsnotax() {
        return bqtchjsnotax;
    }

    public void setBqtchjsnotax(BigDecimal bqtchjsnotax) {
        this.bqtchjsnotax = bqtchjsnotax;
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

    public String getSetDir() {
        return setDir == null ? "" : setDir.trim();
    }

    public void setSetDir(String setDir) {
        this.setDir = setDir == null ? null : setDir.trim();
    }

    public String getIsSign() {
        return isSign == null ? "" : isSign.trim();
    }

    public void setIsSign(String isSign) {
        this.isSign = isSign == null ? null : isSign.trim();
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

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getSummary() {
        return summary == null ? "" : summary.trim();
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public Date getEstPayDate() {
        return estPayDate;
    }

    public void setEstPayDate(Date estPayDate) {
        this.estPayDate = estPayDate;
    }

    public String getFiId() {
        return fiId == null ? "" : fiId.trim();
    }

    public void setFiId(String fiId) {
        this.fiId = fiId == null ? null : fiId.trim();
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

    public List<ZxErpFile> getZxErpFileList() {
        return zxErpFileList == null ? Lists.newArrayList() : zxErpFileList;
    }

    public void setZxErpFileList(List<ZxErpFile> zxErpFileList) {
        this.zxErpFileList = zxErpFileList == null ? null : zxErpFileList;
    }
    
    public List<ZxErpFile> getZxErpMainFileList() {
		return zxErpMainFileList;
	}

	public void setZxErpMainFileList(List<ZxErpFile> zxErpMainFileList) {
		this.zxErpMainFileList = zxErpMainFileList;
	}

	public List<ZxSaEquipSettleAuditItem> getZxSaEquipSettleAuditItemList() {
        return zxSaEquipSettleAuditItemList == null ? Lists.newArrayList() : zxSaEquipSettleAuditItemList;
    }

    public void setZxSaEquipSettleAuditItemList(List<ZxSaEquipSettleAuditItem> zxSaEquipSettleAuditItemList) {
        this.zxSaEquipSettleAuditItemList = zxSaEquipSettleAuditItemList == null ? null : zxSaEquipSettleAuditItemList;
    }

    public List<ZxSaEquipResSettleItem> getZxSaEquipResSettleItemList() {
        return zxSaEquipResSettleItemList == null ? Lists.newArrayList() : zxSaEquipResSettleItemList;
    }

    public void setZxSaEquipResSettleItemList(List<ZxSaEquipResSettleItem> zxSaEquipResSettleItemList) {
        this.zxSaEquipResSettleItemList = zxSaEquipResSettleItemList == null ? null : zxSaEquipResSettleItemList;
    }

    public String getZxSaEquipResSettleId() {
        return zxSaEquipResSettleId == null ? "" : zxSaEquipResSettleId.trim();
    }

    public void setZxSaEquipResSettleId(String zxSaEquipResSettleId) {
        this.zxSaEquipResSettleId = zxSaEquipResSettleId == null ? null : zxSaEquipResSettleId.trim();
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

    public BigDecimal getUpAmtRes() {
        return upAmtRes;
    }

    public void setUpAmtRes(BigDecimal upAmtRes) {
        this.upAmtRes = upAmtRes;
    }

    public BigDecimal getThisAmtRes() {
        return thisAmtRes;
    }

    public void setThisAmtRes(BigDecimal thisAmtRes) {
        this.thisAmtRes = thisAmtRes;
    }

    public BigDecimal getTotalAmtRes() {
        return totalAmtRes;
    }

    public void setTotalAmtRes(BigDecimal totalAmtRes) {
        this.totalAmtRes = totalAmtRes;
    }

    public BigDecimal getThisAmtNoTaxRes() {
        return thisAmtNoTaxRes;
    }

    public void setThisAmtNoTaxRes(BigDecimal thisAmtNoTaxRes) {
        this.thisAmtNoTaxRes = thisAmtNoTaxRes;
    }

    public BigDecimal getThisAmtTaxRes() {
        return thisAmtTaxRes;
    }

    public void setThisAmtTaxRes(BigDecimal thisAmtTaxRes) {
        this.thisAmtTaxRes = thisAmtTaxRes;
    }

    public String getZxSaEquipPaySettleId() {
        return zxSaEquipPaySettleId == null ? "" : zxSaEquipPaySettleId.trim();
    }

    public void setZxSaEquipPaySettleId(String zxSaEquipPaySettleId) {
        this.zxSaEquipPaySettleId = zxSaEquipPaySettleId == null ? null : zxSaEquipPaySettleId.trim();
    }

    public BigDecimal getThisAmtPay() {
        return thisAmtPay;
    }

    public void setThisAmtPay(BigDecimal thisAmtPay) {
        this.thisAmtPay = thisAmtPay;
    }

    public BigDecimal getTotalAmtPay() {
        return totalAmtPay;
    }

    public void setTotalAmtPay(BigDecimal totalAmtPay) {
        this.totalAmtPay = totalAmtPay;
    }

    public BigDecimal getUpAmtPay() {
        return upAmtPay;
    }

    public void setUpAmtPay(BigDecimal upAmtPay) {
        this.upAmtPay = upAmtPay;
    }

    public BigDecimal getInOutAmt() {
        return inOutAmt;
    }

    public void setInOutAmt(BigDecimal inOutAmt) {
        this.inOutAmt = inOutAmt;
    }

    public BigDecimal getUpInOutAmt() {
        return upInOutAmt;
    }

    public void setUpInOutAmt(BigDecimal upInOutAmt) {
        this.upInOutAmt = upInOutAmt;
    }

    public BigDecimal getFineAmt() {
        return fineAmt;
    }

    public void setFineAmt(BigDecimal fineAmt) {
        this.fineAmt = fineAmt;
    }

    public BigDecimal getUpFineAmt() {
        return upFineAmt;
    }

    public void setUpFineAmt(BigDecimal upFineAmt) {
        this.upFineAmt = upFineAmt;
    }

    public BigDecimal getFoodAmt() {
        return foodAmt;
    }

    public void setFoodAmt(BigDecimal foodAmt) {
        this.foodAmt = foodAmt;
    }

    public BigDecimal getUpFoodAmt() {
        return upFoodAmt;
    }

    public void setUpFoodAmt(BigDecimal upFoodAmt) {
        this.upFoodAmt = upFoodAmt;
    }

    public BigDecimal getOtherAmt() {
        return otherAmt;
    }

    public void setOtherAmt(BigDecimal otherAmt) {
        this.otherAmt = otherAmt;
    }

    public BigDecimal getUpOtherAmt() {
        return upOtherAmt;
    }

    public void setUpOtherAmt(BigDecimal upOtherAmt) {
        this.upOtherAmt = upOtherAmt;
    }

    public BigDecimal getThisAmtNoTaxPay() {
        return thisAmtNoTaxPay;
    }

    public void setThisAmtNoTaxPay(BigDecimal thisAmtNoTaxPay) {
        this.thisAmtNoTaxPay = thisAmtNoTaxPay;
    }

    public BigDecimal getThisAmtTaxPay() {
        return thisAmtTaxPay;
    }

    public void setThisAmtTaxPay(BigDecimal thisAmtTaxPay) {
        this.thisAmtTaxPay = thisAmtTaxPay;
    }
    
    public String getSettleTypeFlag() {
		return settleTypeFlag;
	}

	public void setSettleTypeFlag(String settleTypeFlag) {
		this.settleTypeFlag = settleTypeFlag;
	}

	public String getApih5FlowStatusFlagNo2() {
		return apih5FlowStatusFlagNo2;
	}

	public void setApih5FlowStatusFlagNo2(String apih5FlowStatusFlagNo2) {
		this.apih5FlowStatusFlagNo2 = apih5FlowStatusFlagNo2;
	}

	public List<ZxSaEquipPaySettleItem> getZxSaEquipPaySettleItemList() {
		return zxSaEquipPaySettleItemList;
	}

	public void setZxSaEquipPaySettleItemList(List<ZxSaEquipPaySettleItem> zxSaEquipPaySettleItemList) {
		this.zxSaEquipPaySettleItemList = zxSaEquipPaySettleItemList;
	}

}