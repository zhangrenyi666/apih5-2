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

public class ZxCtFsSideAgreement extends BasePojoFlow {
    // 主键
    private String zxCtFsSideAgreementId;

    // 补充协议编号
    private String contractNo;

    // 合同名称
    private String contractName;

    // 合同类型
    private String contractType;

    // 含税合同金额(万元)
    private BigDecimal contractCost;

    // 甲方ID
    private String firstID;

    // 甲方名称
    private String firstName;

    // 乙方ID
    private String secondID;

    // 乙方名称
    private String secondName;

    // 开工日期
    private Date startDate;

    // 竣工日期
    private Date endDate;

    // 合同工期
    private String csTimeLimit;

    // 合同签定人
    private String agent;

    // 合同内容
    private String content;

    // 发起人
    private String beginPer;

    // 评审状态
    private String status;

    // combProp
    private String combProp;

    // pp1
    private String pp1;

    // pp2
    private String pp2;

    // 补充协议名称
    private String pp3;

    // 本期含税增减金额(万元)
    private BigDecimal pp4;

    // pp5
    private String pp5;

    // 合同名称ID
    private String pp6;

    // pp7
    private String pp7;

    // 清单
    private String pp8;

    // pp9
    private String pp9;

    // pp10
    private String pp10;

    // 流程实例ID
    private String instProcessId;

    // 公文任务ID
    private String workitemID;

    // name
    private String code;

    // 变更后含税金额
    private BigDecimal alterContractSum;

    // 是否局审批
    private String isFlag;

    // 发送局判断ID
    private String sendToJuID;

    // 物资来源
    private String materialSource;

    // 上期末变更后合同金额
    private BigDecimal upAlterContractSum;

    // 是否局指审批
    private String isFlagZhb;

    // 发送局指判断ID
    private String sendToZhbID;

    // 税率(%)
    private String taxRate;

    // 是否抵扣
    private String isDeduct;

    // 不含税合同金额(万元)
    private BigDecimal contractCostNoTax;

    // 变更后不含税金额
    private BigDecimal alterContractSumNoTax;

    // 变更后税额
    private BigDecimal alterContractSumTax;

    // 本期不含税增减金额(万元)
    private BigDecimal pp4NoTax;

    // 本期增减税额(万元)
    private BigDecimal pp4Tax;

    // 合同税额(万元)
    private BigDecimal contractCostTax;

    // upAlterContractSumNoTax
    private BigDecimal upAlterContractSumNoTax;

    // comID
    private String comID;

    // upAlterContractSumTax
    private BigDecimal upAlterContractSumTax;

    // orgID
    private String orgID;

    // 管理机构
    private String orgName;

    // 变更等级
    private String alterLevel;

    // 提出单位
    private String proposer;

    // 变更内容
    private String alterContent;

    // 变更原因
    private String alterReason;

    // 发生时间
    private Date happenDate;

    // 本期含税变更增减金额(万元)
    private BigDecimal applyAmount;

    // 申报日期
    private Date applyDate;

    // 申报文号
    private String applyNo;

    // 申报延期天数
    private String applyDelay;

    // 变更后含税合同金额(万元)
    private BigDecimal replyAmount;

    // 批复日期
    private Date replyDate;

    // 补充协议书编号
    private String replyNo;

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

    // 状态
    private String auditStatus;

    // 记录人
    private String recorder;

    // 记录日期
    private Date recordDate;

    // 本期不含税变更增减金额(万元)
    private BigDecimal applyAmountNoTax;

    // 本期变更增减税额(万元)
    private BigDecimal applyAmountTax;

    // 变更后不含税合同金额(万元)
    private BigDecimal replyAmountNoTax;

    // 变更后合同税额(万元)
    private BigDecimal replyAmountTax;

    // 不含税合同金额(万元)
    private BigDecimal pp2NoTax;

    // 合同税额(万元)
    private BigDecimal pp2Tax;

    // 界面显示类型
    private String viewType;

    // 协作清单书ID
    private String coBookId;

    // 索赔工期
    private String claimPeriod;

    // 系统分类
    private String systemType;

    // 附属合同ID
    private String zxCtFsContractId;

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
    private String apih5FlowStatus ;

    // 审核节点状态
    private String apih5FlowNodeStatus;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    //清单list
    private List<ZxCtFsSideAgreementInventory> inventoryList;

    //文件列表（文件工具）
    private List<ZxErpFile> zxErpFileList;

    //正文文件列表（文件工具）
    private List<ZxErpFile> zxZhengWenFileList;

    public List<ZxErpFile> getZxZhengWenFileList() {
        return zxZhengWenFileList;
    }

    public void setZxZhengWenFileList(List<ZxErpFile> zxZhengWenFileList) {
        this.zxZhengWenFileList = zxZhengWenFileList;
    }

    public List<ZxErpFile> getZxErpFileList() {
        return zxErpFileList;
    }

    public void setZxErpFileList(List<ZxErpFile> zxErpFileList) {
        this.zxErpFileList = zxErpFileList;
    }

    public List<ZxCtFsSideAgreementInventory> getInventoryList() {
        return inventoryList;
    }

    public void setInventoryList(List<ZxCtFsSideAgreementInventory> inventoryList) {
        this.inventoryList = inventoryList;
    }

    public String getZxCtFsSideAgreementId() {
        return zxCtFsSideAgreementId == null ? "" : zxCtFsSideAgreementId.trim();
    }

    public void setZxCtFsSideAgreementId(String zxCtFsSideAgreementId) {
        this.zxCtFsSideAgreementId = zxCtFsSideAgreementId == null ? null : zxCtFsSideAgreementId.trim();
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

    public String getCombProp() {
        return combProp == null ? "" : combProp.trim();
    }

    public void setCombProp(String combProp) {
        this.combProp = combProp == null ? null : combProp.trim();
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

    public String getPp3() {
        return pp3 == null ? "" : pp3.trim();
    }

    public void setPp3(String pp3) {
        this.pp3 = pp3 == null ? null : pp3.trim();
    }

    public BigDecimal getPp4() {
        return pp4;
    }

    public void setPp4(BigDecimal pp4) {
        this.pp4 = pp4;
    }

    public BigDecimal getPp4NoTax() {
        return pp4NoTax;
    }

    public void setPp4NoTax(BigDecimal pp4NoTax) {
        this.pp4NoTax = pp4NoTax;
    }

    public String getPp5() {
        return pp5 == null ? "" : pp5.trim();
    }

    public void setPp5(String pp5) {
        this.pp5 = pp5 == null ? null : pp5.trim();
    }

    public String getPp6() {
        return pp6 == null ? "" : pp6.trim();
    }

    public void setPp6(String pp6) {
        this.pp6 = pp6 == null ? null : pp6.trim();
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

    public String getInstProcessId() {
        return instProcessId == null ? "" : instProcessId.trim();
    }

    public void setInstProcessId(String instProcessId) {
        this.instProcessId = instProcessId == null ? null : instProcessId.trim();
    }

    public String getWorkitemID() {
        return workitemID == null ? "" : workitemID.trim();
    }

    public void setWorkitemID(String workitemID) {
        this.workitemID = workitemID == null ? null : workitemID.trim();
    }

    public String getCode() {
        return code == null ? "" : code.trim();
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
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

    public BigDecimal getUpAlterContractSum() {
        return upAlterContractSum;
    }

    public void setUpAlterContractSum(BigDecimal upAlterContractSum) {
        this.upAlterContractSum = upAlterContractSum;
    }

    public String getIsFlagZhb() {
        return isFlagZhb == null ? "" : isFlagZhb.trim();
    }

    public void setIsFlagZhb(String isFlagZhb) {
        this.isFlagZhb = isFlagZhb == null ? null : isFlagZhb.trim();
    }

    public String getSendToZhbID() {
        return sendToZhbID == null ? "" : sendToZhbID.trim();
    }

    public void setSendToZhbID(String sendToZhbID) {
        this.sendToZhbID = sendToZhbID == null ? null : sendToZhbID.trim();
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

    public BigDecimal getContractCostNoTax() {
        return contractCostNoTax;
    }

    public void setContractCostNoTax(BigDecimal contractCostNoTax) {
        this.contractCostNoTax = contractCostNoTax;
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



    public BigDecimal getPp4Tax() {
        return pp4Tax;
    }

    public void setPp4Tax(BigDecimal pp4Tax) {
        this.pp4Tax = pp4Tax;
    }

    public BigDecimal getContractCostTax() {
        return contractCostTax;
    }

    public void setContractCostTax(BigDecimal contractCostTax) {
        this.contractCostTax = contractCostTax;
    }

    public BigDecimal getUpAlterContractSumNoTax() {
        return upAlterContractSumNoTax;
    }

    public void setUpAlterContractSumNoTax(BigDecimal upAlterContractSumNoTax) {
        this.upAlterContractSumNoTax = upAlterContractSumNoTax;
    }

    public String getComID() {
        return comID == null ? "" : comID.trim();
    }

    public void setComID(String comID) {
        this.comID = comID == null ? null : comID.trim();
    }

    public BigDecimal getUpAlterContractSumTax() {
        return upAlterContractSumTax;
    }

    public void setUpAlterContractSumTax(BigDecimal upAlterContractSumTax) {
        this.upAlterContractSumTax = upAlterContractSumTax;
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

    public String getAlterLevel() {
        return alterLevel == null ? "" : alterLevel.trim();
    }

    public void setAlterLevel(String alterLevel) {
        this.alterLevel = alterLevel == null ? null : alterLevel.trim();
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

    public Date getHappenDate() {
        return happenDate;
    }

    public void setHappenDate(Date happenDate) {
        this.happenDate = happenDate;
    }

    public BigDecimal getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(BigDecimal applyAmount) {
        this.applyAmount = applyAmount;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplyNo() {
        return applyNo == null ? "" : applyNo.trim();
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo == null ? null : applyNo.trim();
    }

    public String getApplyDelay() {
        return applyDelay == null ? "" : applyDelay.trim();
    }

    public void setApplyDelay(String applyDelay) {
        this.applyDelay = applyDelay == null ? null : applyDelay.trim();
    }

    public BigDecimal getReplyAmount() {
        return replyAmount;
    }

    public void setReplyAmount(BigDecimal replyAmount) {
        this.replyAmount = replyAmount;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public String getReplyNo() {
        return replyNo == null ? "" : replyNo.trim();
    }

    public void setReplyNo(String replyNo) {
        this.replyNo = replyNo == null ? null : replyNo.trim();
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

    public String getAuditStatus() {
        return auditStatus == null ? "" : auditStatus.trim();
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
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

    public BigDecimal getReplyAmountNoTax() {
        return replyAmountNoTax;
    }

    public void setReplyAmountNoTax(BigDecimal replyAmountNoTax) {
        this.replyAmountNoTax = replyAmountNoTax;
    }

    public BigDecimal getReplyAmountTax() {
        return replyAmountTax;
    }

    public void setReplyAmountTax(BigDecimal replyAmountTax) {
        this.replyAmountTax = replyAmountTax;
    }

    public BigDecimal getPp2NoTax() {
        return pp2NoTax;
    }

    public void setPp2NoTax(BigDecimal pp2NoTax) {
        this.pp2NoTax = pp2NoTax;
    }

    public BigDecimal getPp2Tax() {
        return pp2Tax;
    }

    public void setPp2Tax(BigDecimal pp2Tax) {
        this.pp2Tax = pp2Tax;
    }

    public String getViewType() {
        return viewType == null ? "" : viewType.trim();
    }

    public void setViewType(String viewType) {
        this.viewType = viewType == null ? null : viewType.trim();
    }

    public String getCoBookId() {
        return coBookId == null ? "" : coBookId.trim();
    }

    public void setCoBookId(String coBookId) {
        this.coBookId = coBookId == null ? null : coBookId.trim();
    }

    public String getClaimPeriod() {
        return claimPeriod == null ? "" : claimPeriod.trim();
    }

    public void setClaimPeriod(String claimPeriod) {
        this.claimPeriod = claimPeriod == null ? null : claimPeriod.trim();
    }

    public String getSystemType() {
        return systemType == null ? "" : systemType.trim();
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType == null ? null : systemType.trim();
    }

    public String getZxCtFsContractId() {
        return zxCtFsContractId == null ? "" : zxCtFsContractId.trim();
    }

    public void setZxCtFsContractId(String zxCtFsContractId) {
        this.zxCtFsContractId = zxCtFsContractId == null ? null : zxCtFsContractId.trim();
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

    public String getApih5FlowId() {
        return apih5FlowId == null ? "" : apih5FlowId.trim();
    }

    public void setApih5FlowId(String apih5FlowId) {
        this.apih5FlowId = apih5FlowId == null ? null : apih5FlowId.trim();
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

    public String getApih5FlowNodeStatus() {
        return apih5FlowNodeStatus == null ? "" : apih5FlowNodeStatus.trim();
    }

    public void setApih5FlowNodeStatus(String apih5FlowNodeStatus) {
        this.apih5FlowNodeStatus = apih5FlowNodeStatus == null ? null : apih5FlowNodeStatus.trim();
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
