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

public class ZxCtOther extends BasePojoFlow {
    // 主键
    private String zxCtOtherId;

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

    // pp3
    private String pp3;

    // pp4
    private String pp4;

    // pp5
    private String pp5;

    // pp6
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

    // 合同类别（旧系统字段code7）
    private String contractCategory;

    // 合同序号
    private String contractNumber;

    // 业主合同编码
    private String ownerContractCode;

    // 同一公司
    private String oneFirm;

    // 是否局审批
    private String isFlag;

    // 发送局判断ID
    private String sendToJuId;

    // 上期末变更后金额
    private BigDecimal upAlterContractSum;

    // 是否局指审批
    private String isFlagZhb;

    // 发送局指判断ID
    private String sendToZhbId;

    // 不含税合同金额(万元)
    private BigDecimal contractCostNoTax;

    // 税率(%)
    private String taxRate;

    // 是否抵扣
    private String isDeduct;

    // 合同税额(万元)
    private BigDecimal contractCostTax;

    // 上期末变更后金额不含税
    private BigDecimal upAlterContractSumNoTax;

    // 所属公司id
    private String companyId;

    // 上期末变更后税额
    private String upAlterContractSumTax;

    // 所属公司名称
    private String companyName;

    // 所属项目id
    private String projectId;

    // 所属项目名称
    private String projectName;

    // 往来单位编号
    private String secondIdCode;

    // orgID
    private String orgId;

    // 往来单位编号
    private String secondIDCodeBh;

    // 往来单位名称
    private String secondIDCodeName;

    // 管理机构
    private String orgName;

    // 协作单位类型
    private String secondOrgType;

    // 备注
    private String remark;
    // 附件
    private List<ZxErpFile> zxErpFileList;
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

    // 流程id
    private String apih5FlowId;

    // 审核状态
    private String apih5FlowStatus;

    // work_id
    private String workId;

    // 审核节点状态
    private String apih5FlowNodeStatus;

    // 排序
    private int sort=0;

    // 正文附件
    private List<ZxErpFile> zxErpMainFileList;

    public List<ZxErpFile> getZxErpMainFileList() {
        return zxErpMainFileList;
    }

    public void setZxErpMainFileList(List<ZxErpFile> zxErpMainFileList) {
        this.zxErpMainFileList = zxErpMainFileList;
    }

    public String getZxCtOtherId() {
        return zxCtOtherId == null ? "" : zxCtOtherId.trim();
    }

    public void setZxCtOtherId(String zxCtOtherId) {
        this.zxCtOtherId = zxCtOtherId == null ? null : zxCtOtherId.trim();
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

    public String getIsFlag() {
        return isFlag == null ? "" : isFlag.trim();
    }

    public void setIsFlag(String isFlag) {
        this.isFlag = isFlag == null ? null : isFlag.trim();
    }

    public String getSendToJuId() {
        return sendToJuId == null ? "" : sendToJuId.trim();
    }

    public void setSendToJuId(String sendToJuId) {
        this.sendToJuId = sendToJuId == null ? null : sendToJuId.trim();
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

    public String getSendToZhbId() {
        return sendToZhbId == null ? "" : sendToZhbId.trim();
    }

    public void setSendToZhbId(String sendToZhbId) {
        this.sendToZhbId = sendToZhbId == null ? null : sendToZhbId.trim();
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

    public String getIsDeduct() {
        return isDeduct == null ? "" : isDeduct.trim();
    }

    public void setIsDeduct(String isDeduct) {
        this.isDeduct = isDeduct == null ? null : isDeduct.trim();
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

    public String getCompanyId() {
        return companyId == null ? "" : companyId.trim();
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getUpAlterContractSumTax() {
        return upAlterContractSumTax == null ? "" : upAlterContractSumTax.trim();
    }

    public void setUpAlterContractSumTax(String upAlterContractSumTax) {
        this.upAlterContractSumTax = upAlterContractSumTax == null ? null : upAlterContractSumTax.trim();
    }

    public String getCompanyName() {
        return companyName == null ? "" : companyName.trim();
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
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

    public String getSecondIdCode() {
        return secondIdCode == null ? "" : secondIdCode.trim();
    }

    public void setSecondIdCode(String secondIdCode) {
        this.secondIdCode = secondIdCode == null ? null : secondIdCode.trim();
    }

    public String getOrgId() {
        return orgId == null ? "" : orgId.trim();
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getSecondIDCodeBh() {
        return secondIDCodeBh == null ? "" : secondIDCodeBh.trim();
    }

    public void setSecondIDCodeBh(String secondIDCodeBh) {
        this.secondIDCodeBh = secondIDCodeBh == null ? null : secondIDCodeBh.trim();
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

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public String getApih5FlowStatus() {
        return apih5FlowStatus == null ? "" : apih5FlowStatus.trim();
    }

    public void setApih5FlowStatus(String apih5FlowStatus) {
        this.apih5FlowStatus = apih5FlowStatus == null ? null : apih5FlowStatus.trim();
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
}
