package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCtContrApply extends BasePojo {
    // 主键
    private String id;

    // 合同编号
    private String contractNo;

    // 合同名称
    private String contractName;

    // 合同类型
    private String contractType;

    // 含税合同金额(万元)
    private BigDecimal contractCost;

    // 甲方ID
    private String firstId;

    // 甲方名称
    private String firstName;

    // 乙方ID
    private String secondId;

    // 乙方名称
    private String secondName;

    // 开工日期
    private Date startDate;

    // 竣工日期
    private Date endDate;

    // 合同工期
    private String csTimeLimit;

    // 合同签订人
    private String agent;

    // 合同内容
    private String content;

    // 备注
    private String remark;

    // 发起人
    private String beginPer;

    // 评审状态
    private String status;

    // 补充协议
    private String supplyAgreement;

    // pp4
    private String pp4;

    // pp5
    private String pp5;

    // 合同名称Id
    private String contractNameId;

    // pp10
    private String pp10;

    // 流程实例ID
    private String instProcessId;

    // 公文任务ID
    private String workitemId;

    // 合同编码
    private String contractCode;

    // 机构编码
    private String organCode;

    // 承建单位简称
    private String contractorAbbreviation;

    // 中标单位简称
    private String bidWinnerAbbreviation;

    // 项目所在省份简称
    private String projectProvinceAbbreviation;

    // 项目简称
    private String projectAbbreviation;

    // 标段号
    private String lotNo;

    // 合同类别
    private String contractCategory;

    // 合同序号
    private String contractNumber;

    // 业主合同编码
    private String ownerContractCode;

    // 同一公司
    private String oneFirm;

    // 流程开始时间
    private Date flowBeginDate;

    // 流程结束时间
    private Date flowEndDate;

    // alterContractSum
    private BigDecimal alterContractSum;

    // 是否局审批
    private String isFlag;

    // 是否报告
    private String isReport;

    // appInsHistId
    private String appInsHistId;

    // 发送局判断ID
    private String sendToJuId;

    // materialSource
    private String materialSource;

    // 上期末变更后金额
    private BigDecimal upAlterContractSum;

    // 现场负责人
    private String legalPerson;

    // 委托代理人
    private String agentPerson;

    // 法定代表人
    private String chargePerson;

    // 是否局指审批
    private String isFlagZhb;

    // isReportZhb
    private String isReportZhb;

    // 发送局指判断ID
    private String sendToZhbId;

    // appInsHistIDZhb
    private String appInsHistIDZhb;

    // 不含税合同金额(万元)
    private BigDecimal contractCostNoTax;

    // 税率(%)
    private String taxRate;

    // 上期末变更后金额不含税
    private BigDecimal alterContractSumNoTax;

    // 上期末变更后税额
    private BigDecimal alterContractSumTax;

    // pp4NoTax
    private BigDecimal pp4NoTax;

    // pp4Tax
    private BigDecimal pp4Tax;

    // 是否抵扣
    private String isDeduct;

    // fromContractId
    private String fromContractId;

    // fromContractNo
    private String fromContractNo;

    // fromContractName
    private String fromContractName;

    // 合同税额(万元)
    private BigDecimal contractCostTax;

    // 授权委托人姓名
    private String wtrName;

    // 授权委托人身份证号
    private String wtrPhone;

    // 推荐人姓名
    private String referenceName;

    // 推荐人职务
    private String referencePost;

    // 推荐人电话
    private String referencePhone;

    // 上期末变更后金额不含税
    private BigDecimal upAlterContractSumNoTax;

    // comId
    private String comId;

    // 上期末变更后税额
    private BigDecimal upAlterContractSumTax;

    // 财务系统ID
    private String fiId;

    // actDepartment
    private String actDepartment;

    // actDepartmentBM
    private String actDepartmentBm;

    // actDepartmentId
    private String actDepartmentId;

    // biddersId
    private String biddersId;

    // biddersCode
    private String biddersCode;

    // biddersName
    private String biddersName;

    // 核算单位ID
    private String accountUnitId;

    // 核算单位编号
    private String accountUnitCode;

    // 核算单位
    private String accountUnitName;

    // accountProjId
    private String accountProjId;

    // accountProjCode
    private String accountProjCode;

    // accountProjName
    private String accountProjName;

    // projSite
    private String projSite;

    // busiSegments
    private String busiSegments;

    // projFundsSource
    private String projFundsSource;

    // division
    private String division;

    // taxFilingCode
    private String taxFilingCode;

    // taxCountWay
    private String taxCountWay;

    // taxAdvanceRate
    private String taxAdvanceRate;

    // taxUseWay
    private String taxUseWay;

    // prepaidLand
    private String prepaidLand;

    // nationalTax
    private String nationalTax;

    // nationalTaxTel
    private String nationalTaxTel;

    // nationalTaxAdds
    private String nationalTaxAdds;

    // projDepAdds
    private String projDepAdds;

    // projDepZipCode
    private String projDepZipCode;

    // projDepTel
    private String projDepTel;

    // projDepFax
    private String projDepFax;

    // projStage
    private String projStage;

    // pmFixedLine
    private String pmFixedLine;

    // pmMail
    private String pmMail;

    // fiCharge
    private String fiCharge;

    // fiTel
    private String fiTel;

    // fiFixedLine
    private String fiFixedLine;

    // fiMail
    private String fiMail;

    // ctrCharge
    private String ctrCharge;

    // ctrTel
    private String ctrTel;

    // ctrFixedLine
    private String ctrFixedLine;

    // ctrMail
    private String ctrMail;

    // dcLeader
    private String dcLeader;

    // dcTel
    private String dcTel;

    // dcFixedLine
    private String dcFixedLine;

    // dcMail
    private String dcMail;

    // 复核日期
    private Date doubleCheckDate;

    // 录入日期
    private Date writeDate;

    // 核算部门id
    private String accountDepId;

    // 核算部门编号
    private String accountDepCode;

    // 核算部门
    private String accountDepName;

    // 往来单位编号
    private String secondIDCode;

    // 录入人
    private String writer;

    // 系统编号
    private String systemNo;

    // 币种编号
    private String currencyCode;

    // 合同性质
    private String ctrNature;

    // 合同更新状态
    private String ctrUpdateState;

    // 财务合同状态
    private String fiCtrState;

    // 收支方向
    private String revenueDir;

    // 发票类型
    private String invoiceType;

    // 制式合同
    private String staCtr;

    // 重点合同
    private String keyCtr;

    // 管理机构ID
    private String orgId;

    // notDisplay
    private String notDisplay;

    // 复核人
    private String doubleCheckPerson;

    // 往来单位编号
    private String secondIDCodeBh;

    // auditWorkitemID
    private String auditWorkitemId;

    // auditSys
    private String auditSys;

    // 往来单位名称
    private String secondIDCodeName;

    // 管理机构
    private String orgName;

    // 协作单位类型
    private String secondOrgType;

    // firstOAorgID
    private String firstOAorgId;

    // 是否通过云电商进行招标
    private String isBiddedOnCloud;

    // 招标方式
    private String bidType;

    // 云电商招标方案编号
    private String cloudBidNo;

    // 组织招标主体
    private String bidOrgType;

    // 各单位主管部门是否参与
    private String isAllDepartJoin;

    // 参与方式
    private String joinType;

    // 各单位主管部门是否参与评标
    private String isDepartJoinBid;

    // 合同所属事业部
    private String contractDepart;

    // 包件编号
    private String packageNo;

    // 包件ID
    private String cloudEcoId;

    // 投资合同编号
    private String investContractNo;

    // resCategoryID
    private String resCategoryId;

    // resCategoryName
    private String resCategoryName;

    // 附件
    private List<ZxErpFile> fileList;

    // 工程施工合同清单明细
    private List<ZxCtContrApplyWorks> zxCtContrApplyWorksList;
    // 排序
    private int sort=0;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getContractType() {
        return contractType == null ? "" : contractType.trim();
    }

    public void setContractType(String contractType) {
        this.contractType = contractType == null ? null : contractType.trim();
    }

    public BigDecimal getContractCost() {
        return contractCost;
    }

    public void setContractCost(BigDecimal contractCost) {
        this.contractCost = contractCost;
    }

    public String getFirstId() {
        return firstId == null ? "" : firstId.trim();
    }

    public void setFirstId(String firstId) {
        this.firstId = firstId == null ? null : firstId.trim();
    }

    public String getFirstName() {
        return firstName == null ? "" : firstName.trim();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName == null ? null : firstName.trim();
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCsTimeLimit() {
        return csTimeLimit == null ? "" : csTimeLimit.trim();
    }

    public void setCsTimeLimit(String csTimeLimit) {
        this.csTimeLimit = csTimeLimit == null ? null : csTimeLimit.trim();
    }

    public String getAgent() {
        return agent == null ? "" : agent.trim();
    }

    public void setAgent(String agent) {
        this.agent = agent == null ? null : agent.trim();
    }

    public String getContent() {
        return content == null ? "" : content.trim();
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getBeginPer() {
        return beginPer == null ? "" : beginPer.trim();
    }

    public void setBeginPer(String beginPer) {
        this.beginPer = beginPer == null ? null : beginPer.trim();
    }

    public String getStatus() {
        return status == null ? "" : status.trim();
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getSupplyAgreement() {
        return supplyAgreement == null ? "" : supplyAgreement.trim();
    }

    public void setSupplyAgreement(String supplyAgreement) {
        this.supplyAgreement = supplyAgreement == null ? null : supplyAgreement.trim();
    }

    public String getPp4() {
        return pp4 == null ? "" : pp4.trim();
    }

    public void setPp4(String pp4) {
        this.pp4 = pp4 == null ? null : pp4.trim();
    }

    public String getPp5() {
        return pp5 == null ? "" : pp5.trim();
    }

    public void setPp5(String pp5) {
        this.pp5 = pp5 == null ? null : pp5.trim();
    }

    public String getContractNameId() {
        return contractNameId == null ? "" : contractNameId.trim();
    }

    public void setContractNameId(String contractNameId) {
        this.contractNameId = contractNameId == null ? null : contractNameId.trim();
    }

    public String getPp10() {
        return pp10 == null ? "" : pp10.trim();
    }

    public void setPp10(String pp10) {
        this.pp10 = pp10 == null ? null : pp10.trim();
    }

    public String getInstProcessId() {
        return instProcessId == null ? "" : instProcessId.trim();
    }

    public void setInstProcessId(String instProcessId) {
        this.instProcessId = instProcessId == null ? null : instProcessId.trim();
    }

    public String getWorkitemId() {
        return workitemId == null ? "" : workitemId.trim();
    }

    public void setWorkitemId(String workitemId) {
        this.workitemId = workitemId == null ? null : workitemId.trim();
    }

    public String getContractCode() {
        return contractCode == null ? "" : contractCode.trim();
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode == null ? null : contractCode.trim();
    }

    public String getOrganCode() {
        return organCode == null ? "" : organCode.trim();
    }

    public void setOrganCode(String organCode) {
        this.organCode = organCode == null ? null : organCode.trim();
    }

    public String getContractorAbbreviation() {
        return contractorAbbreviation == null ? "" : contractorAbbreviation.trim();
    }

    public void setContractorAbbreviation(String contractorAbbreviation) {
        this.contractorAbbreviation = contractorAbbreviation == null ? null : contractorAbbreviation.trim();
    }

    public String getBidWinnerAbbreviation() {
        return bidWinnerAbbreviation == null ? "" : bidWinnerAbbreviation.trim();
    }

    public void setBidWinnerAbbreviation(String bidWinnerAbbreviation) {
        this.bidWinnerAbbreviation = bidWinnerAbbreviation == null ? null : bidWinnerAbbreviation.trim();
    }

    public String getProjectProvinceAbbreviation() {
        return projectProvinceAbbreviation == null ? "" : projectProvinceAbbreviation.trim();
    }

    public void setProjectProvinceAbbreviation(String projectProvinceAbbreviation) {
        this.projectProvinceAbbreviation = projectProvinceAbbreviation == null ? null : projectProvinceAbbreviation.trim();
    }

    public String getProjectAbbreviation() {
        return projectAbbreviation == null ? "" : projectAbbreviation.trim();
    }

    public void setProjectAbbreviation(String projectAbbreviation) {
        this.projectAbbreviation = projectAbbreviation == null ? null : projectAbbreviation.trim();
    }

    public String getLotNo() {
        return lotNo == null ? "" : lotNo.trim();
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo == null ? null : lotNo.trim();
    }

    public String getContractCategory() {
        return contractCategory == null ? "" : contractCategory.trim();
    }

    public void setContractCategory(String contractCategory) {
        this.contractCategory = contractCategory == null ? null : contractCategory.trim();
    }

    public String getContractNumber() {
        return contractNumber == null ? "" : contractNumber.trim();
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber == null ? null : contractNumber.trim();
    }

    public String getOwnerContractCode() {
        return ownerContractCode == null ? "" : ownerContractCode.trim();
    }

    public void setOwnerContractCode(String ownerContractCode) {
        this.ownerContractCode = ownerContractCode == null ? null : ownerContractCode.trim();
    }

    public String getOneFirm() {
        return oneFirm == null ? "" : oneFirm.trim();
    }

    public void setOneFirm(String oneFirm) {
        this.oneFirm = oneFirm == null ? null : oneFirm.trim();
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

    public BigDecimal getAlterContractSum() {
        return alterContractSum;
    }

    public void setAlterContractSum(BigDecimal alterContractSum) {
        this.alterContractSum = alterContractSum;
    }

    public String getIsFlag() {
        return isFlag == null ? "" : isFlag.trim();
    }

    public void setIsFlag(String isFlag) {
        this.isFlag = isFlag == null ? null : isFlag.trim();
    }

    public String getIsReport() {
        return isReport == null ? "" : isReport.trim();
    }

    public void setIsReport(String isReport) {
        this.isReport = isReport == null ? null : isReport.trim();
    }

    public String getAppInsHistId() {
        return appInsHistId == null ? "" : appInsHistId.trim();
    }

    public void setAppInsHistId(String appInsHistId) {
        this.appInsHistId = appInsHistId == null ? null : appInsHistId.trim();
    }

    public String getSendToJuId() {
        return sendToJuId == null ? "" : sendToJuId.trim();
    }

    public void setSendToJuId(String sendToJuId) {
        this.sendToJuId = sendToJuId == null ? null : sendToJuId.trim();
    }

    public String getMaterialSource() {
        return materialSource == null ? "" : materialSource.trim();
    }

    public void setMaterialSource(String materialSource) {
        this.materialSource = materialSource == null ? null : materialSource.trim();
    }

    public BigDecimal getUpAlterContractSum() {
        return upAlterContractSum;
    }

    public void setUpAlterContractSum(BigDecimal upAlterContractSum) {
        this.upAlterContractSum = upAlterContractSum;
    }

    public String getLegalPerson() {
        return legalPerson == null ? "" : legalPerson.trim();
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson == null ? null : legalPerson.trim();
    }

    public String getAgentPerson() {
        return agentPerson == null ? "" : agentPerson.trim();
    }

    public void setAgentPerson(String agentPerson) {
        this.agentPerson = agentPerson == null ? null : agentPerson.trim();
    }

    public String getChargePerson() {
        return chargePerson == null ? "" : chargePerson.trim();
    }

    public void setChargePerson(String chargePerson) {
        this.chargePerson = chargePerson == null ? null : chargePerson.trim();
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

    public String getSendToZhbId() {
        return sendToZhbId == null ? "" : sendToZhbId.trim();
    }

    public void setSendToZhbId(String sendToZhbId) {
        this.sendToZhbId = sendToZhbId == null ? null : sendToZhbId.trim();
    }

    public String getAppInsHistIDZhb() {
        return appInsHistIDZhb == null ? "" : appInsHistIDZhb.trim();
    }

    public void setAppInsHistIDZhb(String appInsHistIDZhb) {
        this.appInsHistIDZhb = appInsHistIDZhb == null ? null : appInsHistIDZhb.trim();
    }

    public BigDecimal getContractCostNoTax() {
        return contractCostNoTax;
    }

    public void setContractCostNoTax(BigDecimal contractCostNoTax) {
        this.contractCostNoTax = contractCostNoTax;
    }

    public String getTaxRate() {
        return taxRate == null ? "" : taxRate.trim();
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate == null ? null : taxRate.trim();
    }

    public BigDecimal getAlterContractSumNoTax() {
        return alterContractSumNoTax;
    }

    public void setAlterContractSumNoTax(BigDecimal alterContractSumNoTax) {
        this.alterContractSumNoTax = alterContractSumNoTax;
    }

    public BigDecimal getAlterContractSumTax() {
        return alterContractSumTax;
    }

    public void setAlterContractSumTax(BigDecimal alterContractSumTax) {
        this.alterContractSumTax = alterContractSumTax;
    }

    public BigDecimal getPp4NoTax() {
        return pp4NoTax;
    }

    public void setPp4NoTax(BigDecimal pp4NoTax) {
        this.pp4NoTax = pp4NoTax;
    }

    public BigDecimal getPp4Tax() {
        return pp4Tax;
    }

    public void setPp4Tax(BigDecimal pp4Tax) {
        this.pp4Tax = pp4Tax;
    }

    public String getIsDeduct() {
        return isDeduct == null ? "" : isDeduct.trim();
    }

    public void setIsDeduct(String isDeduct) {
        this.isDeduct = isDeduct == null ? null : isDeduct.trim();
    }

    public String getFromContractId() {
        return fromContractId == null ? "" : fromContractId.trim();
    }

    public void setFromContractId(String fromContractId) {
        this.fromContractId = fromContractId == null ? null : fromContractId.trim();
    }

    public String getFromContractNo() {
        return fromContractNo == null ? "" : fromContractNo.trim();
    }

    public void setFromContractNo(String fromContractNo) {
        this.fromContractNo = fromContractNo == null ? null : fromContractNo.trim();
    }

    public String getFromContractName() {
        return fromContractName == null ? "" : fromContractName.trim();
    }

    public void setFromContractName(String fromContractName) {
        this.fromContractName = fromContractName == null ? null : fromContractName.trim();
    }

    public BigDecimal getContractCostTax() {
        return contractCostTax;
    }

    public void setContractCostTax(BigDecimal contractCostTax) {
        this.contractCostTax = contractCostTax;
    }

    public String getWtrName() {
        return wtrName == null ? "" : wtrName.trim();
    }

    public void setWtrName(String wtrName) {
        this.wtrName = wtrName == null ? null : wtrName.trim();
    }

    public String getWtrPhone() {
        return wtrPhone == null ? "" : wtrPhone.trim();
    }

    public void setWtrPhone(String wtrPhone) {
        this.wtrPhone = wtrPhone == null ? null : wtrPhone.trim();
    }

    public String getReferenceName() {
        return referenceName == null ? "" : referenceName.trim();
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName == null ? null : referenceName.trim();
    }

    public String getReferencePost() {
        return referencePost == null ? "" : referencePost.trim();
    }

    public void setReferencePost(String referencePost) {
        this.referencePost = referencePost == null ? null : referencePost.trim();
    }

    public String getReferencePhone() {
        return referencePhone == null ? "" : referencePhone.trim();
    }

    public void setReferencePhone(String referencePhone) {
        this.referencePhone = referencePhone == null ? null : referencePhone.trim();
    }

    public BigDecimal getUpAlterContractSumNoTax() {
        return upAlterContractSumNoTax;
    }

    public void setUpAlterContractSumNoTax(BigDecimal upAlterContractSumNoTax) {
        this.upAlterContractSumNoTax = upAlterContractSumNoTax;
    }

    public String getComId() {
        return comId == null ? "" : comId.trim();
    }

    public void setComId(String comId) {
        this.comId = comId == null ? null : comId.trim();
    }

    public BigDecimal getUpAlterContractSumTax() {
        return upAlterContractSumTax;
    }

    public void setUpAlterContractSumTax(BigDecimal upAlterContractSumTax) {
        this.upAlterContractSumTax = upAlterContractSumTax;
    }

    public String getFiId() {
        return fiId == null ? "" : fiId.trim();
    }

    public void setFiId(String fiId) {
        this.fiId = fiId == null ? null : fiId.trim();
    }

    public String getActDepartment() {
        return actDepartment == null ? "" : actDepartment.trim();
    }

    public void setActDepartment(String actDepartment) {
        this.actDepartment = actDepartment == null ? null : actDepartment.trim();
    }

    public String getActDepartmentBm() {
        return actDepartmentBm == null ? "" : actDepartmentBm.trim();
    }

    public void setActDepartmentBm(String actDepartmentBm) {
        this.actDepartmentBm = actDepartmentBm == null ? null : actDepartmentBm.trim();
    }

    public String getActDepartmentId() {
        return actDepartmentId == null ? "" : actDepartmentId.trim();
    }

    public void setActDepartmentId(String actDepartmentId) {
        this.actDepartmentId = actDepartmentId == null ? null : actDepartmentId.trim();
    }

    public String getBiddersId() {
        return biddersId == null ? "" : biddersId.trim();
    }

    public void setBiddersId(String biddersId) {
        this.biddersId = biddersId == null ? null : biddersId.trim();
    }

    public String getBiddersCode() {
        return biddersCode == null ? "" : biddersCode.trim();
    }

    public void setBiddersCode(String biddersCode) {
        this.biddersCode = biddersCode == null ? null : biddersCode.trim();
    }

    public String getBiddersName() {
        return biddersName == null ? "" : biddersName.trim();
    }

    public void setBiddersName(String biddersName) {
        this.biddersName = biddersName == null ? null : biddersName.trim();
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

    public String getAccountProjId() {
        return accountProjId == null ? "" : accountProjId.trim();
    }

    public void setAccountProjId(String accountProjId) {
        this.accountProjId = accountProjId == null ? null : accountProjId.trim();
    }

    public String getAccountProjCode() {
        return accountProjCode == null ? "" : accountProjCode.trim();
    }

    public void setAccountProjCode(String accountProjCode) {
        this.accountProjCode = accountProjCode == null ? null : accountProjCode.trim();
    }

    public String getAccountProjName() {
        return accountProjName == null ? "" : accountProjName.trim();
    }

    public void setAccountProjName(String accountProjName) {
        this.accountProjName = accountProjName == null ? null : accountProjName.trim();
    }

    public String getProjSite() {
        return projSite == null ? "" : projSite.trim();
    }

    public void setProjSite(String projSite) {
        this.projSite = projSite == null ? null : projSite.trim();
    }

    public String getBusiSegments() {
        return busiSegments == null ? "" : busiSegments.trim();
    }

    public void setBusiSegments(String busiSegments) {
        this.busiSegments = busiSegments == null ? null : busiSegments.trim();
    }

    public String getProjFundsSource() {
        return projFundsSource == null ? "" : projFundsSource.trim();
    }

    public void setProjFundsSource(String projFundsSource) {
        this.projFundsSource = projFundsSource == null ? null : projFundsSource.trim();
    }

    public String getDivision() {
        return division == null ? "" : division.trim();
    }

    public void setDivision(String division) {
        this.division = division == null ? null : division.trim();
    }

    public String getTaxFilingCode() {
        return taxFilingCode == null ? "" : taxFilingCode.trim();
    }

    public void setTaxFilingCode(String taxFilingCode) {
        this.taxFilingCode = taxFilingCode == null ? null : taxFilingCode.trim();
    }

    public String getTaxCountWay() {
        return taxCountWay == null ? "" : taxCountWay.trim();
    }

    public void setTaxCountWay(String taxCountWay) {
        this.taxCountWay = taxCountWay == null ? null : taxCountWay.trim();
    }

    public String getTaxAdvanceRate() {
        return taxAdvanceRate == null ? "" : taxAdvanceRate.trim();
    }

    public void setTaxAdvanceRate(String taxAdvanceRate) {
        this.taxAdvanceRate = taxAdvanceRate == null ? null : taxAdvanceRate.trim();
    }

    public String getTaxUseWay() {
        return taxUseWay == null ? "" : taxUseWay.trim();
    }

    public void setTaxUseWay(String taxUseWay) {
        this.taxUseWay = taxUseWay == null ? null : taxUseWay.trim();
    }

    public String getPrepaidLand() {
        return prepaidLand == null ? "" : prepaidLand.trim();
    }

    public void setPrepaidLand(String prepaidLand) {
        this.prepaidLand = prepaidLand == null ? null : prepaidLand.trim();
    }

    public String getNationalTax() {
        return nationalTax == null ? "" : nationalTax.trim();
    }

    public void setNationalTax(String nationalTax) {
        this.nationalTax = nationalTax == null ? null : nationalTax.trim();
    }

    public String getNationalTaxTel() {
        return nationalTaxTel == null ? "" : nationalTaxTel.trim();
    }

    public void setNationalTaxTel(String nationalTaxTel) {
        this.nationalTaxTel = nationalTaxTel == null ? null : nationalTaxTel.trim();
    }

    public String getNationalTaxAdds() {
        return nationalTaxAdds == null ? "" : nationalTaxAdds.trim();
    }

    public void setNationalTaxAdds(String nationalTaxAdds) {
        this.nationalTaxAdds = nationalTaxAdds == null ? null : nationalTaxAdds.trim();
    }

    public String getProjDepAdds() {
        return projDepAdds == null ? "" : projDepAdds.trim();
    }

    public void setProjDepAdds(String projDepAdds) {
        this.projDepAdds = projDepAdds == null ? null : projDepAdds.trim();
    }

    public String getProjDepZipCode() {
        return projDepZipCode == null ? "" : projDepZipCode.trim();
    }

    public void setProjDepZipCode(String projDepZipCode) {
        this.projDepZipCode = projDepZipCode == null ? null : projDepZipCode.trim();
    }

    public String getProjDepTel() {
        return projDepTel == null ? "" : projDepTel.trim();
    }

    public void setProjDepTel(String projDepTel) {
        this.projDepTel = projDepTel == null ? null : projDepTel.trim();
    }

    public String getProjDepFax() {
        return projDepFax == null ? "" : projDepFax.trim();
    }

    public void setProjDepFax(String projDepFax) {
        this.projDepFax = projDepFax == null ? null : projDepFax.trim();
    }

    public String getProjStage() {
        return projStage == null ? "" : projStage.trim();
    }

    public void setProjStage(String projStage) {
        this.projStage = projStage == null ? null : projStage.trim();
    }

    public String getPmFixedLine() {
        return pmFixedLine == null ? "" : pmFixedLine.trim();
    }

    public void setPmFixedLine(String pmFixedLine) {
        this.pmFixedLine = pmFixedLine == null ? null : pmFixedLine.trim();
    }

    public String getPmMail() {
        return pmMail == null ? "" : pmMail.trim();
    }

    public void setPmMail(String pmMail) {
        this.pmMail = pmMail == null ? null : pmMail.trim();
    }

    public String getFiCharge() {
        return fiCharge == null ? "" : fiCharge.trim();
    }

    public void setFiCharge(String fiCharge) {
        this.fiCharge = fiCharge == null ? null : fiCharge.trim();
    }

    public String getFiTel() {
        return fiTel == null ? "" : fiTel.trim();
    }

    public void setFiTel(String fiTel) {
        this.fiTel = fiTel == null ? null : fiTel.trim();
    }

    public String getFiFixedLine() {
        return fiFixedLine == null ? "" : fiFixedLine.trim();
    }

    public void setFiFixedLine(String fiFixedLine) {
        this.fiFixedLine = fiFixedLine == null ? null : fiFixedLine.trim();
    }

    public String getFiMail() {
        return fiMail == null ? "" : fiMail.trim();
    }

    public void setFiMail(String fiMail) {
        this.fiMail = fiMail == null ? null : fiMail.trim();
    }

    public String getCtrCharge() {
        return ctrCharge == null ? "" : ctrCharge.trim();
    }

    public void setCtrCharge(String ctrCharge) {
        this.ctrCharge = ctrCharge == null ? null : ctrCharge.trim();
    }

    public String getCtrTel() {
        return ctrTel == null ? "" : ctrTel.trim();
    }

    public void setCtrTel(String ctrTel) {
        this.ctrTel = ctrTel == null ? null : ctrTel.trim();
    }

    public String getCtrFixedLine() {
        return ctrFixedLine == null ? "" : ctrFixedLine.trim();
    }

    public void setCtrFixedLine(String ctrFixedLine) {
        this.ctrFixedLine = ctrFixedLine == null ? null : ctrFixedLine.trim();
    }

    public String getCtrMail() {
        return ctrMail == null ? "" : ctrMail.trim();
    }

    public void setCtrMail(String ctrMail) {
        this.ctrMail = ctrMail == null ? null : ctrMail.trim();
    }

    public String getDcLeader() {
        return dcLeader == null ? "" : dcLeader.trim();
    }

    public void setDcLeader(String dcLeader) {
        this.dcLeader = dcLeader == null ? null : dcLeader.trim();
    }

    public String getDcTel() {
        return dcTel == null ? "" : dcTel.trim();
    }

    public void setDcTel(String dcTel) {
        this.dcTel = dcTel == null ? null : dcTel.trim();
    }

    public String getDcFixedLine() {
        return dcFixedLine == null ? "" : dcFixedLine.trim();
    }

    public void setDcFixedLine(String dcFixedLine) {
        this.dcFixedLine = dcFixedLine == null ? null : dcFixedLine.trim();
    }

    public String getDcMail() {
        return dcMail == null ? "" : dcMail.trim();
    }

    public void setDcMail(String dcMail) {
        this.dcMail = dcMail == null ? null : dcMail.trim();
    }

    public Date getDoubleCheckDate() {
        return doubleCheckDate;
    }

    public void setDoubleCheckDate(Date doubleCheckDate) {
        this.doubleCheckDate = doubleCheckDate;
    }

    public Date getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(Date writeDate) {
        this.writeDate = writeDate;
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

    public String getSecondIDCode() {
        return secondIDCode == null ? "" : secondIDCode.trim();
    }

    public void setSecondIDCode(String secondIDCode) {
        this.secondIDCode = secondIDCode == null ? null : secondIDCode.trim();
    }

    public String getWriter() {
        return writer == null ? "" : writer.trim();
    }

    public void setWriter(String writer) {
        this.writer = writer == null ? null : writer.trim();
    }

    public String getSystemNo() {
        return systemNo == null ? "" : systemNo.trim();
    }

    public void setSystemNo(String systemNo) {
        this.systemNo = systemNo == null ? null : systemNo.trim();
    }

    public String getCurrencyCode() {
        return currencyCode == null ? "" : currencyCode.trim();
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode == null ? null : currencyCode.trim();
    }

    public String getCtrNature() {
        return ctrNature == null ? "" : ctrNature.trim();
    }

    public void setCtrNature(String ctrNature) {
        this.ctrNature = ctrNature == null ? null : ctrNature.trim();
    }

    public String getCtrUpdateState() {
        return ctrUpdateState == null ? "" : ctrUpdateState.trim();
    }

    public void setCtrUpdateState(String ctrUpdateState) {
        this.ctrUpdateState = ctrUpdateState == null ? null : ctrUpdateState.trim();
    }

    public String getFiCtrState() {
        return fiCtrState == null ? "" : fiCtrState.trim();
    }

    public void setFiCtrState(String fiCtrState) {
        this.fiCtrState = fiCtrState == null ? null : fiCtrState.trim();
    }

    public String getRevenueDir() {
        return revenueDir == null ? "" : revenueDir.trim();
    }

    public void setRevenueDir(String revenueDir) {
        this.revenueDir = revenueDir == null ? null : revenueDir.trim();
    }

    public String getInvoiceType() {
        return invoiceType == null ? "" : invoiceType.trim();
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType == null ? null : invoiceType.trim();
    }

    public String getStaCtr() {
        return staCtr == null ? "" : staCtr.trim();
    }

    public void setStaCtr(String staCtr) {
        this.staCtr = staCtr == null ? null : staCtr.trim();
    }

    public String getKeyCtr() {
        return keyCtr == null ? "" : keyCtr.trim();
    }

    public void setKeyCtr(String keyCtr) {
        this.keyCtr = keyCtr == null ? null : keyCtr.trim();
    }

    public String getOrgId() {
        return orgId == null ? "" : orgId.trim();
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getNotDisplay() {
        return notDisplay == null ? "" : notDisplay.trim();
    }

    public void setNotDisplay(String notDisplay) {
        this.notDisplay = notDisplay == null ? null : notDisplay.trim();
    }

    public String getDoubleCheckPerson() {
        return doubleCheckPerson == null ? "" : doubleCheckPerson.trim();
    }

    public void setDoubleCheckPerson(String doubleCheckPerson) {
        this.doubleCheckPerson = doubleCheckPerson == null ? null : doubleCheckPerson.trim();
    }

    public String getSecondIDCodeBh() {
        return secondIDCodeBh == null ? "" : secondIDCodeBh.trim();
    }

    public void setSecondIDCodeBh(String secondIDCodeBh) {
        this.secondIDCodeBh = secondIDCodeBh == null ? null : secondIDCodeBh.trim();
    }

    public String getAuditWorkitemId() {
        return auditWorkitemId == null ? "" : auditWorkitemId.trim();
    }

    public void setAuditWorkitemId(String auditWorkitemId) {
        this.auditWorkitemId = auditWorkitemId == null ? null : auditWorkitemId.trim();
    }

    public String getAuditSys() {
        return auditSys == null ? "" : auditSys.trim();
    }

    public void setAuditSys(String auditSys) {
        this.auditSys = auditSys == null ? null : auditSys.trim();
    }

    public String getSecondIDCodeName() {
        return secondIDCodeName == null ? "" : secondIDCodeName.trim();
    }

    public void setSecondIDCodeName(String secondIDCodeName) {
        this.secondIDCodeName = secondIDCodeName == null ? null : secondIDCodeName.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getSecondOrgType() {
        return secondOrgType == null ? "" : secondOrgType.trim();
    }

    public void setSecondOrgType(String secondOrgType) {
        this.secondOrgType = secondOrgType == null ? null : secondOrgType.trim();
    }

    public String getFirstOAorgId() {
        return firstOAorgId == null ? "" : firstOAorgId.trim();
    }

    public void setFirstOAorgId(String firstOAorgId) {
        this.firstOAorgId = firstOAorgId == null ? null : firstOAorgId.trim();
    }

    public String getIsBiddedOnCloud() {
        return isBiddedOnCloud == null ? "" : isBiddedOnCloud.trim();
    }

    public void setIsBiddedOnCloud(String isBiddedOnCloud) {
        this.isBiddedOnCloud = isBiddedOnCloud == null ? null : isBiddedOnCloud.trim();
    }

    public String getBidType() {
        return bidType == null ? "" : bidType.trim();
    }

    public void setBidType(String bidType) {
        this.bidType = bidType == null ? null : bidType.trim();
    }

    public String getCloudBidNo() {
        return cloudBidNo == null ? "" : cloudBidNo.trim();
    }

    public void setCloudBidNo(String cloudBidNo) {
        this.cloudBidNo = cloudBidNo == null ? null : cloudBidNo.trim();
    }

    public String getBidOrgType() {
        return bidOrgType == null ? "" : bidOrgType.trim();
    }

    public void setBidOrgType(String bidOrgType) {
        this.bidOrgType = bidOrgType == null ? null : bidOrgType.trim();
    }

    public String getIsAllDepartJoin() {
        return isAllDepartJoin == null ? "" : isAllDepartJoin.trim();
    }

    public void setIsAllDepartJoin(String isAllDepartJoin) {
        this.isAllDepartJoin = isAllDepartJoin == null ? null : isAllDepartJoin.trim();
    }

    public String getJoinType() {
        return joinType == null ? "" : joinType.trim();
    }

    public void setJoinType(String joinType) {
        this.joinType = joinType == null ? null : joinType.trim();
    }

    public String getIsDepartJoinBid() {
        return isDepartJoinBid == null ? "" : isDepartJoinBid.trim();
    }

    public void setIsDepartJoinBid(String isDepartJoinBid) {
        this.isDepartJoinBid = isDepartJoinBid == null ? null : isDepartJoinBid.trim();
    }

    public String getContractDepart() {
        return contractDepart == null ? "" : contractDepart.trim();
    }

    public void setContractDepart(String contractDepart) {
        this.contractDepart = contractDepart == null ? null : contractDepart.trim();
    }

    public String getPackageNo() {
        return packageNo == null ? "" : packageNo.trim();
    }

    public void setPackageNo(String packageNo) {
        this.packageNo = packageNo == null ? null : packageNo.trim();
    }

    public String getCloudEcoId() {
        return cloudEcoId == null ? "" : cloudEcoId.trim();
    }

    public void setCloudEcoId(String cloudEcoId) {
        this.cloudEcoId = cloudEcoId == null ? null : cloudEcoId.trim();
    }

    public String getInvestContractNo() {
        return investContractNo == null ? "" : investContractNo.trim();
    }

    public void setInvestContractNo(String investContractNo) {
        this.investContractNo = investContractNo == null ? null : investContractNo.trim();
    }

    public String getResCategoryId() {
        return resCategoryId == null ? "" : resCategoryId.trim();
    }

    public void setResCategoryId(String resCategoryId) {
        this.resCategoryId = resCategoryId == null ? null : resCategoryId.trim();
    }

    public String getResCategoryName() {
        return resCategoryName == null ? "" : resCategoryName.trim();
    }

    public void setResCategoryName(String resCategoryName) {
        this.resCategoryName = resCategoryName == null ? null : resCategoryName.trim();
    }

    public List<ZxErpFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<ZxErpFile> fileList) {
        this.fileList = fileList;
    }

    public List<ZxCtContrApplyWorks> getZxCtContrApplyWorksList() {
        return zxCtContrApplyWorksList;
    }

    public void setZxCtContrApplyWorksList(List<ZxCtContrApplyWorks> zxCtContrApplyWorksList) {
        this.zxCtContrApplyWorksList = zxCtContrApplyWorksList;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

}
