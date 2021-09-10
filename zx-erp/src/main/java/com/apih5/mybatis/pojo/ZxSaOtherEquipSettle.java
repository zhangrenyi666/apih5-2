package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.apih5.framework.entity.BasePojoFlow;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSaOtherEquipSettle extends BasePojoFlow {
    // 主键
    private String zxSaOtherEquipSettleId;

    // 重新评审次数
    private String notPassNum;

    // 计税方法
    private String taxCountWay;

    // 本期结算不含税金额(元)
    private BigDecimal thisAmtNoTax;

    // 本期结算税额(元)
    private BigDecimal thisAmtTax;

    // 推送时间
    private Date sendDate;

    // 推送状态
    private String isSend;

    // 税率
    private String taxRate;

    // 是否抵扣
    private String isDeduct;

    // upWorkItemID
    private String upWorkItemId;

    // oaOrgID
    private String oaOrgId;

    // contractCost
    private String contractCost;

    // 是否锁定
    private String flowLock;

    // 财务系统id
    private String fiId;

    // 结算内容
    private String content;

    // 财务审批状态
    private String cwStatus;

    // 财务审批状态说明
    private String cwStatusRemark;

    // 调差后累计结算金额
    private BigDecimal tchljjsje;

    // 本期调整后结算金额
    private BigDecimal bqtchjsje;

    // 结算金额差值
    private BigDecimal tcje;

    // 税额差值
    private BigDecimal tcse;

    // 本期调差后税额
    private BigDecimal bqtchse;

    // 债权人编号
    private String secondIdCodeBh;

    // 是否上报局
    private String isReport;

    // 款项性质内码
    private String payNatureId;

    // 款项性质编号
    private String payNatureCode;

    // 款项性质名称
    private String payNatureName;

    // 到期日期
    private Date expDate;

    // 预计付款日期
    private Date estPayDate;

    // notDisplay
    private String notDisplay;

    // 截止到上期累计金额(元)
    private BigDecimal upTotalAmt;

    // 结算期次
    private String period;

    // 结算期限开始时间
    private Date beginDate;

    // 本期结算含税金额(元)
    private BigDecimal thisAmt;

    // 填报日期
    private Date reportDate;

    // 流程结束时间
    private Date flowEndDate;

    // 结算单编号
    private String billNo;

    // 项目ID
    private String orgId;

    // 项目名称
    private String orgName;

    // 合同ID（旧系统字段contractId）
    private String zxCtOtherManageId;

    // 合同编号
    private String contractNo;

    // 合同名称
    private String contractName;

    // 乙方ID
    private String secondId;

    // 合同乙方
    private String secondName;

    // 结算类型
    private String billType;

    // 结算期限结束时间
    private Date endDate;

    // 开累结算金额(元)
    private BigDecimal totalAmt;

    // 本期应付金额(元)
    private BigDecimal thisPayAmt;

    // 开累应付金额(元)
    private BigDecimal totalPayAmt;

    // 业务日期
    private Date businessDate;

    // 填报人
    private String reportPerson;

    // 填报人电话
    private String reportPersonPhone;

    // 计算人
    private String countPerson;

    // 复核人
    private String reCountPerson;

    // 发起人
    private String flowBeginPerson;

    // 流程ID
    private String workItemId;

    // 流程进度ID
    private String instProcessId;

    // 流程开始时间
    private Date flowBeginDate;

    // 状态
    private String auditStatus;

    // 所属公司ID
    private String companyId;

    // 所属公司名称
    private String companyName;

    // 所属公司排序
    private String comOrders;

    // 是否首次结算
    private String isFirst;

    // 统一社会信用代码
    private String orgCertificate;

    // 老流程ID
    private String oldWorkItemId;

    // 合同类型
    private String contrType;

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

    // 引用签认单未审核数量
    private BigDecimal useSignedOrder;

    // 开累清单结算金额(元)
    private BigDecimal totalEquipAmt;

    // 含税合同金额(万元)
    private BigDecimal contractAmt;

    // 变更后含税合同金额(万元)
    private BigDecimal changeAmt;

    // 结算表初始化顺序号
    private String initSerialNumber;

    // 本期清单结算金额(元)
    private BigDecimal thisEquipAmt;

    // 流水号
    private String serialNumber;

    // 是否使用
    private String useCount;

    // 是否最大期次
    private String isMaxPeriod;

    // 是否完工后结算
    private String isFished;

    // 结算期次时间戳
    private Date periodDate;

    // 签认单编号
    private String signedNo;

    // 附图及附表(可另附页计算)
    private String otherInfo;

    // 完成下列内容的开始日期
    private Date startDate;

    // 验收情况
    private String appraisal;

    // 流程id
    private String apih5FlowId;

    // 审核状态
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

    // 备注10
    private String opinionField10;
    // 附件
    private List<ZxErpFile> zxErpFileList;
    // 统计项子表list
    private List<ZxSaOtherEquipSettleItem> zxSaOtherEquipSettleItemList;
    // work_id
    private String workId;

    // 审核节点状态
    private String apih5FlowNodeStatus;

    // 所属项目id
    private String projectId;

    // 所属项目名称
    private String projectName;

    // 备注
    private String remark;

    // 排序
    private int sort=0;

    // 报表相关新加的字段
    // 清单编号
    private String equipCode;

    // 结算内容
    private String equipName;

    // 单位
    private String unit;

    // 含税单价（元）
    private BigDecimal contractPrice;

    // 不含税单价（元）
    private BigDecimal priceNoTax;

    // 税金（元）
    private BigDecimal contractPriceTax;

    // 变更后含税单价（元）
    private BigDecimal alterPrice;

    // 变更后不含税单价（元）
    private BigDecimal alterPriceNoTax;

    // 变更后税金（元）
    private BigDecimal alterPriceTax;

    // 原合同数量
    private BigDecimal contractQty;

    // 原合同不含税金额
    private BigDecimal contractAmtNoTax;

    // 原合同税额
    private BigDecimal contractAmtTax;

    // 变更合同数量
    private BigDecimal changeQty;

    // 变更合同不含税金额
    private BigDecimal changeAmtNoTax;

    // 变更合同税额
    private BigDecimal changeAmtTax;

    // 本期数量
    private BigDecimal thisQty;

    // 至上期末累计数量
    private BigDecimal upQty;

    // 至上期末累计含税金额
    private BigDecimal upAmt;

    // 至上期末累计不含税金额
    private BigDecimal upAmtNoTax;

    // 至上期末累计税额
    private BigDecimal upAmtTax;

    // 至本期末累计数量
    private BigDecimal totalQty;

    // 至本期末累计不含税金额
    private BigDecimal totalAmtNoTax;

    // 至本期末累计税额
    private BigDecimal totalAmtTax;

    //文件列表（评审公文正文）
    private List<ZxErpFile> documentFileList;

    public String getZxSaOtherEquipSettleId() {
        return zxSaOtherEquipSettleId == null ? "" : zxSaOtherEquipSettleId.trim();
    }

    public void setZxSaOtherEquipSettleId(String zxSaOtherEquipSettleId) {
        this.zxSaOtherEquipSettleId = zxSaOtherEquipSettleId == null ? null : zxSaOtherEquipSettleId.trim();
    }

    public String getNotPassNum() {
        return notPassNum == null ? "" : notPassNum.trim();
    }

    public void setNotPassNum(String notPassNum) {
        this.notPassNum = notPassNum == null ? null : notPassNum.trim();
    }

    public String getTaxCountWay() {
        return taxCountWay == null ? "" : taxCountWay.trim();
    }

    public void setTaxCountWay(String taxCountWay) {
        this.taxCountWay = taxCountWay == null ? null : taxCountWay.trim();
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

    public String getUpWorkItemId() {
        return upWorkItemId == null ? "" : upWorkItemId.trim();
    }

    public void setUpWorkItemId(String upWorkItemId) {
        this.upWorkItemId = upWorkItemId == null ? null : upWorkItemId.trim();
    }

    public String getOaOrgId() {
        return oaOrgId == null ? "" : oaOrgId.trim();
    }

    public void setOaOrgId(String oaOrgId) {
        this.oaOrgId = oaOrgId == null ? null : oaOrgId.trim();
    }

    public String getContractCost() {
        return contractCost == null ? "" : contractCost.trim();
    }

    public void setContractCost(String contractCost) {
        this.contractCost = contractCost == null ? null : contractCost.trim();
    }

    public String getFlowLock() {
        return flowLock == null ? "" : flowLock.trim();
    }

    public void setFlowLock(String flowLock) {
        this.flowLock = flowLock == null ? null : flowLock.trim();
    }

    public String getFiId() {
        return fiId == null ? "" : fiId.trim();
    }

    public void setFiId(String fiId) {
        this.fiId = fiId == null ? null : fiId.trim();
    }

    public String getContent() {
        return content == null ? "" : content.trim();
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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

    public BigDecimal getTchljjsje() {
        return tchljjsje;
    }

    public void setTchljjsje(BigDecimal tchljjsje) {
        this.tchljjsje = tchljjsje;
    }

    public BigDecimal getBqtchjsje() {
        return bqtchjsje;
    }

    public void setBqtchjsje(BigDecimal bqtchjsje) {
        this.bqtchjsje = bqtchjsje;
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

    public BigDecimal getBqtchse() {
        return bqtchse;
    }

    public void setBqtchse(BigDecimal bqtchse) {
        this.bqtchse = bqtchse;
    }

    public String getSecondIdCodeBh() {
        return secondIdCodeBh == null ? "" : secondIdCodeBh.trim();
    }

    public void setSecondIdCodeBh(String secondIdCodeBh) {
        this.secondIdCodeBh = secondIdCodeBh == null ? null : secondIdCodeBh.trim();
    }

    public String getIsReport() {
        return isReport == null ? "" : isReport.trim();
    }

    public void setIsReport(String isReport) {
        this.isReport = isReport == null ? null : isReport.trim();
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

    public String getNotDisplay() {
        return notDisplay == null ? "" : notDisplay.trim();
    }

    public void setNotDisplay(String notDisplay) {
        this.notDisplay = notDisplay == null ? null : notDisplay.trim();
    }

    public BigDecimal getUpTotalAmt() {
        return upTotalAmt;
    }

    public void setUpTotalAmt(BigDecimal upTotalAmt) {
        this.upTotalAmt = upTotalAmt;
    }

    public String getPeriod() {
        return period == null ? "" : period.trim();
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public BigDecimal getThisAmt() {
        return thisAmt;
    }

    public void setThisAmt(BigDecimal thisAmt) {
        this.thisAmt = thisAmt;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Date getFlowEndDate() {
        return flowEndDate;
    }

    public void setFlowEndDate(Date flowEndDate) {
        this.flowEndDate = flowEndDate;
    }

    public String getBillNo() {
        return billNo == null ? "" : billNo.trim();
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public String getOrgId() {
        return orgId == null ? "" : orgId.trim();
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getZxCtOtherManageId() {
        return zxCtOtherManageId == null ? "" : zxCtOtherManageId.trim();
    }

    public void setZxCtOtherManageId(String zxCtOtherManageId) {
        this.zxCtOtherManageId = zxCtOtherManageId == null ? null : zxCtOtherManageId.trim();
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

    public String getSecondId() {
        return secondId == null ? "" : secondId.trim();
    }

    public void setSecondId(String secondId) {
        this.secondId = secondId == null ? null : secondId.trim();
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public String getReportPerson() {
        return reportPerson == null ? "" : reportPerson.trim();
    }

    public void setReportPerson(String reportPerson) {
        this.reportPerson = reportPerson == null ? null : reportPerson.trim();
    }

    public String getReportPersonPhone() {
        return reportPersonPhone == null ? "" : reportPersonPhone.trim();
    }

    public void setReportPersonPhone(String reportPersonPhone) {
        this.reportPersonPhone = reportPersonPhone == null ? null : reportPersonPhone.trim();
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

    public String getWorkItemId() {
        return workItemId == null ? "" : workItemId.trim();
    }

    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId == null ? null : workItemId.trim();
    }

    public String getInstProcessId() {
        return instProcessId == null ? "" : instProcessId.trim();
    }

    public void setInstProcessId(String instProcessId) {
        this.instProcessId = instProcessId == null ? null : instProcessId.trim();
    }

    public Date getFlowBeginDate() {
        return flowBeginDate;
    }

    public void setFlowBeginDate(Date flowBeginDate) {
        this.flowBeginDate = flowBeginDate;
    }

    public String getAuditStatus() {
        return auditStatus == null ? "" : auditStatus.trim();
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
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

    public String getComOrders() {
        return comOrders == null ? "" : comOrders.trim();
    }

    public void setComOrders(String comOrders) {
        this.comOrders = comOrders == null ? null : comOrders.trim();
    }

    public String getIsFirst() {
        return isFirst == null ? "" : isFirst.trim();
    }

    public void setIsFirst(String isFirst) {
        this.isFirst = isFirst == null ? null : isFirst.trim();
    }

    public String getOrgCertificate() {
        return orgCertificate == null ? "" : orgCertificate.trim();
    }

    public void setOrgCertificate(String orgCertificate) {
        this.orgCertificate = orgCertificate == null ? null : orgCertificate.trim();
    }

    public String getOldWorkItemId() {
        return oldWorkItemId == null ? "" : oldWorkItemId.trim();
    }

    public void setOldWorkItemId(String oldWorkItemId) {
        this.oldWorkItemId = oldWorkItemId == null ? null : oldWorkItemId.trim();
    }

    public String getContrType() {
        return contrType == null ? "" : contrType.trim();
    }

    public void setContrType(String contrType) {
        this.contrType = contrType == null ? null : contrType.trim();
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

    public BigDecimal getUseSignedOrder() {
        return useSignedOrder;
    }

    public void setUseSignedOrder(BigDecimal useSignedOrder) {
        this.useSignedOrder = useSignedOrder;
    }

    public BigDecimal getTotalEquipAmt() {
        return totalEquipAmt;
    }

    public void setTotalEquipAmt(BigDecimal totalEquipAmt) {
        this.totalEquipAmt = totalEquipAmt;
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

    public String getInitSerialNumber() {
        return initSerialNumber == null ? "" : initSerialNumber.trim();
    }

    public void setInitSerialNumber(String initSerialNumber) {
        this.initSerialNumber = initSerialNumber == null ? null : initSerialNumber.trim();
    }

    public BigDecimal getThisEquipAmt() {
        return thisEquipAmt;
    }

    public void setThisEquipAmt(BigDecimal thisEquipAmt) {
        this.thisEquipAmt = thisEquipAmt;
    }

    public String getSerialNumber() {
        return serialNumber == null ? "" : serialNumber.trim();
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
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

    public String getIsFished() {
        return isFished == null ? "" : isFished.trim();
    }

    public void setIsFished(String isFished) {
        this.isFished = isFished == null ? null : isFished.trim();
    }

    public Date getPeriodDate() {
        return periodDate;
    }

    public void setPeriodDate(Date periodDate) {
        this.periodDate = periodDate;
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

    public String getAppraisal() {
        return appraisal == null ? "" : appraisal.trim();
    }

    public void setAppraisal(String appraisal) {
        this.appraisal = appraisal == null ? null : appraisal.trim();
    }

    public String getApih5FlowId() {
        return apih5FlowId == null ? "" : apih5FlowId.trim();
    }

    public void setApih5FlowId(String apih5FlowId) {
        this.apih5FlowId = apih5FlowId == null ? null : apih5FlowId.trim();
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

    public String getWorkId() {
        return workId == null ? "" : workId.trim();
    }

    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    public String getApih5FlowNodeStatus() {
        return apih5FlowNodeStatus == null ? "" : apih5FlowNodeStatus.trim();
    }

    public void setApih5FlowNodeStatus(String apih5FlowNodeStatus) {
        this.apih5FlowNodeStatus = apih5FlowNodeStatus == null ? null : apih5FlowNodeStatus.trim();
    }

    public String getProjectId() {
        return projectId == null ? "" : projectId.trim();
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName.trim();
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<ZxErpFile> getZxErpFileList() {
        return zxErpFileList;
    }

    public void setZxErpFileList(List<ZxErpFile> zxErpFileList) {
        this.zxErpFileList = zxErpFileList;
    }

    public List<ZxSaOtherEquipSettleItem> getZxSaOtherEquipSettleItemList() {
        return zxSaOtherEquipSettleItemList;
    }

    public void setZxSaOtherEquipSettleItemList(List<ZxSaOtherEquipSettleItem> zxSaOtherEquipSettleItemList) {
        this.zxSaOtherEquipSettleItemList = zxSaOtherEquipSettleItemList;
    }


    public String getEquipCode() {
        return equipCode == null ? "" : equipCode.trim();
    }

    public void setEquipCode(String equipCode) {
        this.equipCode = equipCode == null ? null : equipCode.trim();
    }

    public String getEquipName() {
        return equipName == null ? "" : equipName.trim();
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName == null ? null : equipName.trim();
    }

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getContractPrice() {
        return contractPrice;
    }

    public void setContractPrice(BigDecimal contractPrice) {
        this.contractPrice = contractPrice;
    }

    public BigDecimal getPriceNoTax() {
        return priceNoTax;
    }

    public void setPriceNoTax(BigDecimal priceNoTax) {
        this.priceNoTax = priceNoTax;
    }

    public BigDecimal getContractPriceTax() {
        return contractPriceTax;
    }

    public void setContractPriceTax(BigDecimal contractPriceTax) {
        this.contractPriceTax = contractPriceTax;
    }

    public BigDecimal getAlterPrice() {
        return alterPrice;
    }

    public void setAlterPrice(BigDecimal alterPrice) {
        this.alterPrice = alterPrice;
    }

    public BigDecimal getAlterPriceNoTax() {
        return alterPriceNoTax;
    }

    public void setAlterPriceNoTax(BigDecimal alterPriceNoTax) {
        this.alterPriceNoTax = alterPriceNoTax;
    }

    public BigDecimal getAlterPriceTax() {
        return alterPriceTax;
    }

    public void setAlterPriceTax(BigDecimal alterPriceTax) {
        this.alterPriceTax = alterPriceTax;
    }

    public BigDecimal getContractQty() { return contractQty; }

    public void setContractQty(BigDecimal contractQty) {
        this.contractQty = contractQty;
    }

    public BigDecimal getContractAmtNoTax() {
        return contractAmtNoTax;
    }

    public void setContractAmtNoTax(BigDecimal contractAmtNoTax) {
        this.contractAmtNoTax = contractAmtNoTax;
    }

    public BigDecimal getContractAmtTax() {
        return contractAmtTax;
    }

    public void setContractAmtTax(BigDecimal contractAmtTax) {
        this.contractAmtTax = contractAmtTax;
    }

    public BigDecimal getChangeQty() {
        return changeQty;
    }

    public void setChangeQty(BigDecimal changeQty) {
        this.changeQty = changeQty;
    }

    public BigDecimal getChangeAmtNoTax() {
        return changeAmtNoTax;
    }

    public void setChangeAmtNoTax(BigDecimal changeAmtNoTax) {
        this.changeAmtNoTax = changeAmtNoTax;
    }

    public BigDecimal getChangeAmtTax() {
        return changeAmtTax;
    }

    public void setChangeAmtTax(BigDecimal changeAmtTax) {
        this.changeAmtTax = changeAmtTax;
    }

    public BigDecimal getThisQty() {
        return thisQty;
    }

    public void setThisQty(BigDecimal thisQty) {
        this.thisQty = thisQty;
    }

    public BigDecimal getUpQty() {
        return upQty;
    }

    public void setUpQty(BigDecimal upQty) {
        this.upQty = upQty;
    }

    public BigDecimal getUpAmt() {
        return upAmt;
    }

    public void setUpAmt(BigDecimal upAmt) {
        this.upAmt = upAmt;
    }

    public BigDecimal getUpAmtNoTax() {
        return upAmtNoTax;
    }

    public void setUpAmtNoTax(BigDecimal upAmtNoTax) {
        this.upAmtNoTax = upAmtNoTax;
    }

    public BigDecimal getUpAmtTax() {
        return upAmtTax;
    }

    public void setUpAmtTax(BigDecimal upAmtTax) {
        this.upAmtTax = upAmtTax;
    }

    public BigDecimal getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(BigDecimal totalQty) {
        this.totalQty = totalQty;
    }

    public BigDecimal getTotalAmtNoTax() {
        return totalAmtNoTax;
    }

    public void setTotalAmtNoTax(BigDecimal totalAmtNoTax) {
        this.totalAmtNoTax = totalAmtNoTax;
    }

    public BigDecimal getTotalAmtTax() {
        return totalAmtTax;
    }

    public void setTotalAmtTax(BigDecimal totalAmtTax) {
        this.totalAmtTax = totalAmtTax;
    }

    public List<ZxErpFile> getDocumentFileList() {
        return documentFileList;
    }

    public void setDocumentFileList(List<ZxErpFile> documentFileList) {
        this.documentFileList = documentFileList;
    }
}
