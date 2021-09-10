package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojoFlow;
import com.google.common.collect.Lists;

public class ZxCtEqContrSupply extends BasePojoFlow {
    // 主键
    private String zxCtEqContrSupplyId;

    // 补充协议编号
    private String contractNo;

    // 补充协议名称
    private String supplyName;

    // 合同id
    private String contractID;

    // 合同名称
    private String contractName;

    // 合同类型
    private String contractType;

    // 甲方id
    private String firstID;

    // 甲方名称
    private String firstName;

    // 乙方id
    private String secondID;

    // 乙方名称
    private String secondName;

    // 开工日期
    private Date startDate;

    // 补充协议清单id
    private String contrAlterID;

    // 竣工日期
    private Date endDate;

    // 合同工期
    private String csTimeLimit;

    // 合同签订人
    private String agent;

    // 合同内容
    private String content;

    // 税率%
    private String taxRate;

    // 含税合同金额（万元）
    private BigDecimal contractCost;

    // 不含税合同金额(万元)
    private BigDecimal contractCostNoTax;

    // 合同税额(万元)
    private BigDecimal contractCostTax;

    // 本期含税增减金额(万元)
    private BigDecimal thisAmount;

    // 本期不含税增减金额(万元)
    private BigDecimal thisAmountNoTax;

    // 本期增减税额(万元)
    private BigDecimal thisAmountTax;

    // 变更后含税金额(万元)
    private BigDecimal alterContractSum;

    // 变更后不含税金额(万元)
    private BigDecimal alterContractSumNoTax;

    // 变更后税额
    private BigDecimal alterContractSumTax;

    // 清单
    private String pp7;

    // pp8
    private String pp8;

    // 上期末变更后金额
    private BigDecimal upAlterContractSum;

    // 上期末变更后金额不含税
    private BigDecimal upAlterContractSumNoTax;

    // 上期末变更后税额
    private BigDecimal upAlterContractSumTax;

    // pp9
    private String pp9;

    // pp10
    private String pp10;

    // name
    private String code;

    // 机构编码
    private String code1;

    // 承建单位简称
    private String code2;

    // 中标单位简称
    private String code3;

    // 项目所在省份简称
    private String code4;

    // 项目简称
    private String code5;

    // 标段号
    private String code6;

    // 合同类别
    private String code7;

    // 合同序号
    private String code8;

    // pp1
    private String pp1;

    // pp2
    private String pp2;

    // 业主合同编码
    private String codeP1;

    // 同一合同
    private String code2T3;

    // 0
    private Date flowBeginDate;

    // 0
    private Date flowEndDate;

    // 是否局审批
    private String isFlag;

    // 0
    private String isReport;

    // 0
    private String appInsHistID;

    // 发送局判断id
    private String sendToJuID;

    // 0
    private String materialSource;

    // 现场负责人
    private String legalPerson;

    // 委托代理人
    private String agentPerson;

    // 法定代表人
    private String chargePerson;

    // 是否局指审批
    private String isFlagZhb;

    // 0
    private String isReportZhb;

    // 发送局指判断id
    private String sendToZhbID;

    // 0
    private String appInsHistIDZhb;

    // 0
    private String isDeduct;

    // 0
    private String fromContractID;

    // 0
    private String fromContractNo;

    // 0
    private String fromContractName;

    // 授权委托人姓名
    private String wtrName;

    // 授权委托人身份证号
    private String wtrPhone;

    // 推荐人姓名
    private String referenceName;

    // 拖见人职务
    private String referencePost;

    // 推荐人电话
    private String referencePhone;

    // comID
    private String comID;

    // 财务系统id
    private String fiId;

    // 0
    private String actDepartment;

    // 0
    private String actDepartmentBM;

    // 0
    private String actDepartmentID;

    // 0
    private String biddersId;

    // 0
    private String biddersCode;

    // 0
    private String biddersName;

    // 核算单位id
    private String accountUnitId;

    // 核算单位编号
    private String accountUnitCode;

    // 核算单位
    private String accountUnitName;

    // 0
    private String accountProjId;

    // 0
    private String accountProjCode;

    // 0
    private String accountProjName;

    // 0
    private String projSite;

    // 0
    private String busiSegments;

    // 0
    private String projFundsSource;

    // 0
    private String division;

    // 0
    private String taxFilingCode;

    // 0
    private String taxCountWay;

    // 0
    private String taxAdvanceRate;

    // 0
    private String taxUseWay;

    // 0
    private String prepaidLand;

    // 0
    private String nationalTax;

    // 0
    private String nationalTaxTel;

    // 0
    private String nationalTaxAdds;

    // 0
    private String projDepAdds;

    // 0
    private String projDepZipCode;

    // 0
    private String projDepTel;

    // 0
    private String projDepFax;

    // 0
    private String projStage;

    // 0
    private String pmFixedLine;

    // 0
    private String pmMail;

    // 0
    private String fiCharge;

    // 0
    private String fiTel;

    // 0
    private String fiFixedLine;

    // 0
    private String fiMail;

    // 0
    private String ctrCharge;

    // 0
    private String ctrTel;

    // 0
    private String ctrFixedLine;

    // 0
    private String ctrMail;

    // 0
    private String dcLeader;

    // 0
    private String dcTel;

    // 0
    private String dcFixedLine;

    // 0
    private String dcMail;

    // 0
    private Date doubleCheckDate;

    // 录入日期
    private Date writeDate;

    // 核算部门id
    private String accountDepId;

    // 核算部门编号
    private String accountDepCode;

    // 虎算部门
    private String accountDepName;

    // 往来单位编号
    private String secondIDCode;

    // 录入人
    private String writer;

    // 系统编号
    private String systemNo;

    // 0
    private String currencyCode;

    // 0
    private String ctrNature;

    // 0
    private String ctrUpdateState;

    // 0
    private String fiCtrState;

    // 0
    private String revenueDir;

    // 发票类型
    private String invoiceType;

    // 0
    private String staCtr;

    // 重点合同
    private String keyCtr;

    // orgID
    private String orgID;

    // 0
    private String notDisplay;

    // 复核人
    private String doubleCheckPerson;

    // 往来单位编号
    private String secondIDCodeBh;

    // auditWorkitemID
    private String auditWorkitemID;

    // auditSys
    private String auditSys;

    // 往来单位名称
    private String secondIDCodeName;

    // orgName
    private String orgName;

    // 协作单位类型
    private String secondOrgType;

    // 0
    private String firstOAorgID;

    // 是否通过云电商进行招标
    private String isBiddedOnCloud;

    // 招标方式
    private String bidType;

    // 0
    private String cloudBidNo;

    // 组织招标主体
    private String bidOrgType;

    // 各单位主管部门是否参与
    private String isAllDepartJoin;

    // 0
    private String joinType;

    // 各单位主管部门是否参与评标
    private String isDepartJoinBid;

    // 0
    private String contractDepart;

    // 包件编号
    private String packageNo;

    // 0
    private String cloudEcoID;

    // 投资合同编号
    private String investContractNo;

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

    // 备注
    private String remark;

    // 变更内容
    private String alterContent;

    // 变更原因
    private String alterReason;

    // 批复单位
    private String supplyUnit;

    // 批复日期
    private Date replyDate;

    // 附件list
    private List<ZxErpFile> zxErpFileList;
    
    //正文List
    private List<ZxErpFile> zxErpMainFileList;

    // 清单list
    private List<ZxCtEqContrItem> zxCtEqContrItemList;

    // 状态不为2的标识
    private String apih5FlowStatusNot2Flag;

    public String getZxCtEqContrSupplyId() {
        return zxCtEqContrSupplyId == null ? "" : zxCtEqContrSupplyId.trim();
    }

    public void setZxCtEqContrSupplyId(String zxCtEqContrSupplyId) {
        this.zxCtEqContrSupplyId = zxCtEqContrSupplyId == null ? null : zxCtEqContrSupplyId.trim();
    }

    public String getContractNo() {
        return contractNo == null ? "" : contractNo.trim();
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public String getSupplyName() {
        return supplyName == null ? "" : supplyName.trim();
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName == null ? null : supplyName.trim();
    }

    public String getContractID() {
        return contractID == null ? "" : contractID.trim();
    }

    public void setContractID(String contractID) {
        this.contractID = contractID == null ? null : contractID.trim();
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

    public String getFirstID() {
        return firstID == null ? "" : firstID.trim();
    }

    public void setFirstID(String firstID) {
        this.firstID = firstID == null ? null : firstID.trim();
    }

    public String getFirstName() {
        return firstName == null ? "" : firstName.trim();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName == null ? null : firstName.trim();
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getContrAlterID() {
        return contrAlterID == null ? "" : contrAlterID.trim();
    }

    public void setContrAlterID(String contrAlterID) {
        this.contrAlterID = contrAlterID == null ? null : contrAlterID.trim();
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

    public String getTaxRate() {
        return taxRate == null ? "" : taxRate.trim();
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate == null ? null : taxRate.trim();
    }

    public BigDecimal getContractCost() {
        return contractCost;
    }

    public void setContractCost(BigDecimal contractCost) {
        this.contractCost = contractCost;
    }

    public BigDecimal getContractCostNoTax() {
        return contractCostNoTax;
    }

    public void setContractCostNoTax(BigDecimal contractCostNoTax) {
        this.contractCostNoTax = contractCostNoTax;
    }

    public BigDecimal getContractCostTax() {
        return contractCostTax;
    }

    public void setContractCostTax(BigDecimal contractCostTax) {
        this.contractCostTax = contractCostTax;
    }

    public BigDecimal getThisAmount() {
        return thisAmount;
    }

    public void setThisAmount(BigDecimal thisAmount) {
        this.thisAmount = thisAmount;
    }

    public BigDecimal getThisAmountNoTax() {
        return thisAmountNoTax;
    }

    public void setThisAmountNoTax(BigDecimal thisAmountNoTax) {
        this.thisAmountNoTax = thisAmountNoTax;
    }

    public BigDecimal getThisAmountTax() {
        return thisAmountTax;
    }

    public void setThisAmountTax(BigDecimal thisAmountTax) {
        this.thisAmountTax = thisAmountTax;
    }

    public BigDecimal getAlterContractSum() {
        return alterContractSum;
    }

    public void setAlterContractSum(BigDecimal alterContractSum) {
        this.alterContractSum = alterContractSum;
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

    public String getPp7() {
        return pp7 == null ? "" : pp7.trim();
    }

    public void setPp7(String pp7) {
        this.pp7 = pp7 == null ? null : pp7.trim();
    }

    public String getPp8() {
        return pp8 == null ? "" : pp8.trim();
    }

    public void setPp8(String pp8) {
        this.pp8 = pp8 == null ? null : pp8.trim();
    }

    public BigDecimal getUpAlterContractSum() {
        return upAlterContractSum;
    }

    public void setUpAlterContractSum(BigDecimal upAlterContractSum) {
        this.upAlterContractSum = upAlterContractSum;
    }

    public BigDecimal getUpAlterContractSumNoTax() {
        return upAlterContractSumNoTax;
    }

    public void setUpAlterContractSumNoTax(BigDecimal upAlterContractSumNoTax) {
        this.upAlterContractSumNoTax = upAlterContractSumNoTax;
    }

    public BigDecimal getUpAlterContractSumTax() {
        return upAlterContractSumTax;
    }

    public void setUpAlterContractSumTax(BigDecimal upAlterContractSumTax) {
        this.upAlterContractSumTax = upAlterContractSumTax;
    }

    public String getPp9() {
        return pp9 == null ? "" : pp9.trim();
    }

    public void setPp9(String pp9) {
        this.pp9 = pp9 == null ? null : pp9.trim();
    }

    public String getPp10() {
        return pp10 == null ? "" : pp10.trim();
    }

    public void setPp10(String pp10) {
        this.pp10 = pp10 == null ? null : pp10.trim();
    }

    public String getCode() {
        return code == null ? "" : code.trim();
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getCode1() {
        return code1 == null ? "" : code1.trim();
    }

    public void setCode1(String code1) {
        this.code1 = code1 == null ? null : code1.trim();
    }

    public String getCode2() {
        return code2 == null ? "" : code2.trim();
    }

    public void setCode2(String code2) {
        this.code2 = code2 == null ? null : code2.trim();
    }

    public String getCode3() {
        return code3 == null ? "" : code3.trim();
    }

    public void setCode3(String code3) {
        this.code3 = code3 == null ? null : code3.trim();
    }

    public String getCode4() {
        return code4 == null ? "" : code4.trim();
    }

    public void setCode4(String code4) {
        this.code4 = code4 == null ? null : code4.trim();
    }

    public String getCode5() {
        return code5 == null ? "" : code5.trim();
    }

    public void setCode5(String code5) {
        this.code5 = code5 == null ? null : code5.trim();
    }

    public String getCode6() {
        return code6 == null ? "" : code6.trim();
    }

    public void setCode6(String code6) {
        this.code6 = code6 == null ? null : code6.trim();
    }

    public String getCode7() {
        return code7 == null ? "" : code7.trim();
    }

    public void setCode7(String code7) {
        this.code7 = code7 == null ? null : code7.trim();
    }

    public String getCode8() {
        return code8 == null ? "" : code8.trim();
    }

    public void setCode8(String code8) {
        this.code8 = code8 == null ? null : code8.trim();
    }

    public String getPp1() {
        return pp1 == null ? "" : pp1.trim();
    }

    public void setPp1(String pp1) {
        this.pp1 = pp1 == null ? null : pp1.trim();
    }

    public String getPp2() {
        return pp2 == null ? "" : pp2.trim();
    }

    public void setPp2(String pp2) {
        this.pp2 = pp2 == null ? null : pp2.trim();
    }

    public String getCodeP1() {
        return codeP1 == null ? "" : codeP1.trim();
    }

    public void setCodeP1(String codeP1) {
        this.codeP1 = codeP1 == null ? null : codeP1.trim();
    }

    public String getCode2T3() {
        return code2T3 == null ? "" : code2T3.trim();
    }

    public void setCode2T3(String code2T3) {
        this.code2T3 = code2T3 == null ? null : code2T3.trim();
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

    public String getAppInsHistID() {
        return appInsHistID == null ? "" : appInsHistID.trim();
    }

    public void setAppInsHistID(String appInsHistID) {
        this.appInsHistID = appInsHistID == null ? null : appInsHistID.trim();
    }

    public String getSendToJuID() {
        return sendToJuID == null ? "" : sendToJuID.trim();
    }

    public void setSendToJuID(String sendToJuID) {
        this.sendToJuID = sendToJuID == null ? null : sendToJuID.trim();
    }

    public String getMaterialSource() {
        return materialSource == null ? "" : materialSource.trim();
    }

    public void setMaterialSource(String materialSource) {
        this.materialSource = materialSource == null ? null : materialSource.trim();
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

    public String getIsDeduct() {
        return isDeduct == null ? "" : isDeduct.trim();
    }

    public void setIsDeduct(String isDeduct) {
        this.isDeduct = isDeduct == null ? null : isDeduct.trim();
    }

    public String getFromContractID() {
        return fromContractID == null ? "" : fromContractID.trim();
    }

    public void setFromContractID(String fromContractID) {
        this.fromContractID = fromContractID == null ? null : fromContractID.trim();
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

    public String getComID() {
        return comID == null ? "" : comID.trim();
    }

    public void setComID(String comID) {
        this.comID = comID == null ? null : comID.trim();
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

    public String getActDepartmentBM() {
        return actDepartmentBM == null ? "" : actDepartmentBM.trim();
    }

    public void setActDepartmentBM(String actDepartmentBM) {
        this.actDepartmentBM = actDepartmentBM == null ? null : actDepartmentBM.trim();
    }

    public String getActDepartmentID() {
        return actDepartmentID == null ? "" : actDepartmentID.trim();
    }

    public void setActDepartmentID(String actDepartmentID) {
        this.actDepartmentID = actDepartmentID == null ? null : actDepartmentID.trim();
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

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
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

    public String getFirstOAorgID() {
        return firstOAorgID == null ? "" : firstOAorgID.trim();
    }

    public void setFirstOAorgID(String firstOAorgID) {
        this.firstOAorgID = firstOAorgID == null ? null : firstOAorgID.trim();
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

    public String getCloudEcoID() {
        return cloudEcoID == null ? "" : cloudEcoID.trim();
    }

    public void setCloudEcoID(String cloudEcoID) {
        this.cloudEcoID = cloudEcoID == null ? null : cloudEcoID.trim();
    }

    public String getInvestContractNo() {
        return investContractNo == null ? "" : investContractNo.trim();
    }

    public void setInvestContractNo(String investContractNo) {
        this.investContractNo = investContractNo == null ? null : investContractNo.trim();
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

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getAlterContent() {
        return alterContent == null ? "" : alterContent.trim();
    }

    public void setAlterContent(String alterContent) {
        this.alterContent = alterContent == null ? null : alterContent.trim();
    }

    public String getAlterReason() {
        return alterReason == null ? "" : alterReason.trim();
    }

    public void setAlterReason(String alterReason) {
        this.alterReason = alterReason == null ? null : alterReason.trim();
    }

    public String getSupplyUnit() {
        return supplyUnit == null ? "" : supplyUnit.trim();
    }

    public void setSupplyUnit(String supplyUnit) {
        this.supplyUnit = supplyUnit == null ? null : supplyUnit.trim();
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
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

	public List<ZxCtEqContrItem> getZxCtEqContrItemList() {
        return zxCtEqContrItemList == null ? Lists.newArrayList() : zxCtEqContrItemList;
    }

    public void setZxCtEqContrItemList(List<ZxCtEqContrItem> zxCtEqContrItemList) {
        this.zxCtEqContrItemList = zxCtEqContrItemList == null ? null : zxCtEqContrItemList;
    }

    public String getApih5FlowStatusNot2Flag() {
        return apih5FlowStatusNot2Flag == null ? "" : apih5FlowStatusNot2Flag.trim();
    }

    public void setApih5FlowStatusNot2Flag(String apih5FlowStatusNot2Flag) {
        this.apih5FlowStatusNot2Flag = apih5FlowStatusNot2Flag == null ? null : apih5FlowStatusNot2Flag.trim();
    }

}
