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

public class ZxCtOtherSupplyAgreement extends BasePojoFlow {
    // 主键
    private String zxCtOtherSupplyAgreementId;

    // 甲方名称
    private String firstName;

    // 是否抵扣
    private String isDeduct;

    // 评审状态
    private String status;

    // pp5
    private String pp5;

    // 公文任务ID
    private String workitemId;

    // 补充协议编号
    private String contractNo;

    // 补充协议名称（旧系统是pp3，iect_ContrApply表）
    private String supplyAgreementName;

    // 合同名称
    private String contractName;

    // 合同类型
    private String contractType;

    // 甲方ID
    private String firstId;

    // 管理机构
    private String orgName;

    // 管理机构id
    private String orgId;

    // 乙方ID
    private String secondId;

    // 乙方名称
    private String secondName;

    // 合同签定人
    private String agent;

    // 含税合同金额(万元)
    private BigDecimal contractCost;

    // 不含税合同金额(万元)
    private BigDecimal contractCostNoTax;

    // 合同税额(万元)
    private BigDecimal contractCostTax;

    // 上期末变更后合同金额
    private BigDecimal upAlterContractSum;

    // 上期末变更后金额不含税
    private BigDecimal upAlterContractSumNoTax;

    // 上期末变更后税额
    private BigDecimal upAlterContractSumTax;

    // 变更后含税金额
    private BigDecimal alterContractSum;

    // 变更后不含税金额
    private BigDecimal alterContractSumNoTax;

    // 变更后税额
    private BigDecimal alterContractSumTax;

    // 税率(%)
    private String taxRate;

    // 开工日期
    private Date startDate;

    // 竣工日期
    private Date endDate;

    // 合同工期
    private String csTimeLimit;

    // 合同内容
    private String content;

    // 发起人
    private String beginPer;

    // 是否局审批
    private String isFlag;

    // 是否局指审批
    private String isFlagZhb;

    // pp1
    private String pp1;

    // pp2
    private String pp2;

    // pp7
    private String pp7;

    // 清单
    private String pp8;

    // pp9
    private String pp9;

    // pp10
    private String pp10;

    // combProp
    private String combProp;

    // 流程实例ID
    private String instProcessId;

    // 发送局判断ID
    private String sendToJuId;

    // 发送局指判断ID
    private String sendToZhbId;

    // 分公司id
    private String comId;

    // 本期不含税变更增减金额(万元)
    private BigDecimal applyAmountNoTax;

    // 本期变更增减税额(万元)
    private BigDecimal applyAmountTax;

    // 本期含税变更增减金额(万元)
    private BigDecimal applyAmount;

    // 变更等级
    private String alterLevel;

    // 状态
    private String audit;

    // 合同ID（旧系统字段contractId）
    private String zxCtOtherManageId;

    // 申报文号
    private String applyNo;

    // 提出单位
    private String proposer;

    // 变更内容
    private String alterContent;

    // 变更原因
    private String alterReason;

    // 系统分类
    private String systemType;

    // 界面显示类型
    private String viewType;

    // 发生时间
    private Date happenDate;

    // 申报日期
    private Date applyDate;

    // 申报延期天数
    private String applyDelay;

    // 批复单位
    private String replyUnit;

    // 批复日期
    private Date replyDate;

    // 批复延期天数
    private String replyDelay;

    // 批复状态
    private String replyStatus;

    // 需要公司协助
    private String companyHelp;

    // 生效操作日期
    private Date takeEffectDate;

    // 生效操作人
    private String takeEffectMan;

    // 索赔工期
    private String claimPeriod;

    // 记录人
    private String recorder;

    // 记录日期
    private Date recordDate;

    // (IP的合同ID)
    private String ipContractId;

    // 备注
    private String remark;

    // 附件
    private List<ZxErpFile> zxErpFileList;
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

    // work_id
    private String workId;

    // 审核节点状态
    private String apih5FlowNodeStatus;

    // 所属项目id
    private String projectId;

    // 所属项目名称
    private String projectName;

    // 所属公司id
    private String companyId;

    // 所属公司名称
    private String companyName;

    // 排序
    private int sort=0;

    // 附件
    private List<ZxErpFile> zxZhengWenFileList;

    public String getZxCtOtherSupplyAgreementId() {
        return zxCtOtherSupplyAgreementId == null ? "" : zxCtOtherSupplyAgreementId.trim();
    }

    public void setZxCtOtherSupplyAgreementId(String zxCtOtherSupplyAgreementId) {
        this.zxCtOtherSupplyAgreementId = zxCtOtherSupplyAgreementId == null ? null : zxCtOtherSupplyAgreementId.trim();
    }

    public String getFirstName() {
        return firstName == null ? "" : firstName.trim();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName == null ? null : firstName.trim();
    }

    public String getIsDeduct() {
        return isDeduct == null ? "" : isDeduct.trim();
    }

    public void setIsDeduct(String isDeduct) {
        this.isDeduct = isDeduct == null ? null : isDeduct.trim();
    }

    public String getStatus() {
        return status == null ? "" : status.trim();
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getPp5() {
        return pp5 == null ? "" : pp5.trim();
    }

    public void setPp5(String pp5) {
        this.pp5 = pp5 == null ? null : pp5.trim();
    }

    public String getWorkitemId() {
        return workitemId == null ? "" : workitemId.trim();
    }

    public void setWorkitemId(String workitemId) {
        this.workitemId = workitemId == null ? null : workitemId.trim();
    }

    public String getContractNo() {
        return contractNo == null ? "" : contractNo.trim();
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public String getSupplyAgreementName() {
        return supplyAgreementName == null ? "" : supplyAgreementName.trim();
    }

    public void setSupplyAgreementName(String supplyAgreementName) {
        this.supplyAgreementName = supplyAgreementName == null ? null : supplyAgreementName.trim();
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

    public String getFirstId() {
        return firstId == null ? "" : firstId.trim();
    }

    public void setFirstId(String firstId) {
        this.firstId = firstId == null ? null : firstId.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getOrgId() {
        return orgId == null ? "" : orgId.trim();
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
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

    public String getAgent() {
        return agent == null ? "" : agent.trim();
    }

    public void setAgent(String agent) {
        this.agent = agent == null ? null : agent.trim();
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

    public String getTaxRate() {
        return taxRate == null ? "" : taxRate.trim();
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate == null ? null : taxRate.trim();
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

    public String getContent() {
        return content == null ? "" : content.trim();
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getBeginPer() {
        return beginPer == null ? "" : beginPer.trim();
    }

    public void setBeginPer(String beginPer) {
        this.beginPer = beginPer == null ? null : beginPer.trim();
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

    public String getCombProp() {
        return combProp == null ? "" : combProp.trim();
    }

    public void setCombProp(String combProp) {
        this.combProp = combProp == null ? null : combProp.trim();
    }

    public String getInstProcessId() {
        return instProcessId == null ? "" : instProcessId.trim();
    }

    public void setInstProcessId(String instProcessId) {
        this.instProcessId = instProcessId == null ? null : instProcessId.trim();
    }

    public String getSendToJuId() {
        return sendToJuId == null ? "" : sendToJuId.trim();
    }

    public void setSendToJuId(String sendToJuId) {
        this.sendToJuId = sendToJuId == null ? null : sendToJuId.trim();
    }

    public String getSendToZhbId() {
        return sendToZhbId == null ? "" : sendToZhbId.trim();
    }

    public void setSendToZhbId(String sendToZhbId) {
        this.sendToZhbId = sendToZhbId == null ? null : sendToZhbId.trim();
    }

    public String getComId() {
        return comId == null ? "" : comId.trim();
    }

    public void setComId(String comId) {
        this.comId = comId == null ? null : comId.trim();
    }

    public BigDecimal getApplyAmountNoTax() {
        return applyAmountNoTax;
    }

    public void setApplyAmountNoTax(BigDecimal applyAmountNoTax) {
        this.applyAmountNoTax = applyAmountNoTax;
    }

    public BigDecimal getApplyAmountTax() {
        return applyAmountTax;
    }

    public void setApplyAmountTax(BigDecimal applyAmountTax) {
        this.applyAmountTax = applyAmountTax;
    }

    public BigDecimal getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(BigDecimal applyAmount) {
        this.applyAmount = applyAmount;
    }

    public String getAlterLevel() {
        return alterLevel == null ? "" : alterLevel.trim();
    }

    public void setAlterLevel(String alterLevel) {
        this.alterLevel = alterLevel == null ? null : alterLevel.trim();
    }

    public String getAudit() {
        return audit == null ? "" : audit.trim();
    }

    public void setAudit(String audit) {
        this.audit = audit == null ? null : audit.trim();
    }

    public String getZxCtOtherManageId() {
        return zxCtOtherManageId == null ? "" : zxCtOtherManageId.trim();
    }

    public void setZxCtOtherManageId(String zxCtOtherManageId) {
        this.zxCtOtherManageId = zxCtOtherManageId == null ? null : zxCtOtherManageId.trim();
    }

    public String getApplyNo() {
        return applyNo == null ? "" : applyNo.trim();
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo == null ? null : applyNo.trim();
    }

    public String getProposer() {
        return proposer == null ? "" : proposer.trim();
    }

    public void setProposer(String proposer) {
        this.proposer = proposer == null ? null : proposer.trim();
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

    public String getSystemType() {
        return systemType == null ? "" : systemType.trim();
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType == null ? null : systemType.trim();
    }

    public String getViewType() {
        return viewType == null ? "" : viewType.trim();
    }

    public void setViewType(String viewType) {
        this.viewType = viewType == null ? null : viewType.trim();
    }

    public Date getHappenDate() {
        return happenDate;
    }

    public void setHappenDate(Date happenDate) {
        this.happenDate = happenDate;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplyDelay() {
        return applyDelay == null ? "" : applyDelay.trim();
    }

    public void setApplyDelay(String applyDelay) {
        this.applyDelay = applyDelay == null ? null : applyDelay.trim();
    }

    public String getReplyUnit() {
        return replyUnit == null ? "" : replyUnit.trim();
    }

    public void setReplyUnit(String replyUnit) {
        this.replyUnit = replyUnit == null ? null : replyUnit.trim();
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public String getReplyDelay() {
        return replyDelay == null ? "" : replyDelay.trim();
    }

    public void setReplyDelay(String replyDelay) {
        this.replyDelay = replyDelay == null ? null : replyDelay.trim();
    }

    public String getReplyStatus() {
        return replyStatus == null ? "" : replyStatus.trim();
    }

    public void setReplyStatus(String replyStatus) {
        this.replyStatus = replyStatus == null ? null : replyStatus.trim();
    }

    public String getCompanyHelp() {
        return companyHelp == null ? "" : companyHelp.trim();
    }

    public void setCompanyHelp(String companyHelp) {
        this.companyHelp = companyHelp == null ? null : companyHelp.trim();
    }

    public Date getTakeEffectDate() {
        return takeEffectDate;
    }

    public void setTakeEffectDate(Date takeEffectDate) {
        this.takeEffectDate = takeEffectDate;
    }

    public String getTakeEffectMan() {
        return takeEffectMan == null ? "" : takeEffectMan.trim();
    }

    public void setTakeEffectMan(String takeEffectMan) {
        this.takeEffectMan = takeEffectMan == null ? null : takeEffectMan.trim();
    }

    public String getClaimPeriod() {
        return claimPeriod == null ? "" : claimPeriod.trim();
    }

    public void setClaimPeriod(String claimPeriod) {
        this.claimPeriod = claimPeriod == null ? null : claimPeriod.trim();
    }

    public String getRecorder() {
        return recorder == null ? "" : recorder.trim();
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder == null ? null : recorder.trim();
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getIpContractId() {
        return ipContractId == null ? "" : ipContractId.trim();
    }

    public void setIpContractId(String ipContractId) {
        this.ipContractId = ipContractId == null ? null : ipContractId.trim();
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public List<ZxErpFile> getZxZhengWenFileList() {
        return zxZhengWenFileList;
    }

    public void setZxZhengWenFileList(List<ZxErpFile> zxZhengWenFileList) {
        this.zxZhengWenFileList = zxZhengWenFileList;
    }
}
