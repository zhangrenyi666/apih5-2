package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojoFlow;

public class ZxCtSuppliesContrApply extends BasePojoFlow {
    // 主键
    private String applyId;

    // 中标单位简称
    private String code3;

    // 乙方名称
    private String secondName;

    // 乙方ID
    private String secondID;

    // 业主合同功能码
    private String codeP1;

    // 所属公司ID
    private String companyId;
    
    // 协作单位类型
    private String secondOrgType;

    // 项目所在省份简称
    private String code4;

    // 项目简称
    private String code5;

    // 物资来源
    private String materialSource;

    // 往来单位名称
    private String secondIDCodeName;

    // 往来单位编号
    private String secondIDCodeBh;

    // 往来单位编号
    private String secondIDCode;

    // 同一公司
    private String code2T3;

    // 税率(%)
    private String taxRate;

    // 是否局指审批
    private String isFlagZhb;

    // 是否局审批
    private String isFlag;

    // 是否抵扣
    private String isDeduct;

    // 上期末变更后税额
    private BigDecimal upAlterContractSumTax;

    // 上期末变更后金额不含税
    private BigDecimal upAlterContractSumNoTax;

    // 上期末变更后金额
    private BigDecimal upAlterContractSum;

    // 清单
    private String pp8;

    // 评审状态
    private String status;

    // 流程实例ID
    private String instProcessId;

    // 开工日期
    private Date startDate;

    // 竣工日期
    private Date endDate;

    // 甲方名称
    private String firstName;

    // 甲方ID
    private String firstID;

    // 机构编码
    private String code1;

    // 核算单位编号
    private String accountUnitCode;
    
    private String shopNumber;
    
    private String shopWay;

    // 核算单位ID
    private String accountUnitId;

    // 核算单位
    private String accountUnitName;

    // 核算部门编号
    private String accountDepCode;

    // 核算部门id
    private String accountDepId;

    // 核算部门
    private String accountDepName;

    // 合同序号
    private String code8;

    // 合同税额(万元)
    private BigDecimal contractCostTax;

    // 合同签定人
    private String agent;

    // 合同内容
    private String content;

    // 合同名称
    private String contractName;

    // 合同类型
    private String contractType;

    // 合同类别
    private String code7;

    // 合同工期
    private String csTimeLimit;

    // 合同编码
    private String code;

    // 合同编号
    private String contractNo;

    // 含税合同金额(万元)
    private BigDecimal contractCost;

    // 管理机构
    private String orgName;

    // 公文任务ID
    private String workitemID;

    // 发送局指判断ID
    private String sendToZhbID;

    // 发送局判断ID
    private String sendToJuID;

    // 发起人
    private String beginPer;

    // 承建单位简称
    private String code2;

    // 不含税合同金额(万元)
    private BigDecimal contractCostNoTax;

    // 标段号
    private String code6;

    // pp9
    private String pp9;

    // pp7
    private String pp7;

    // pp6
    private String pp6;

    // pp5
    private String pp5;

    // pp4
    private String pp4;

    // pp3
    private String pp3;

    // pp2
    private String pp2;

    // pp10
    private String pp10;

    // pp1
    private String pp1;

    // orgID
    private String orgID;

    // comID
    private String comID;

    // combProp
    private String combProp;

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

    // 物资类别ID
    private String resCategoryID;

    // 物资类别
    private String resCategoryName;

    // 备注
    private String remarks;
    
    private String contractNum;//对下合同编号
    
    private List<ZxErpFile> applyFileList;
    // 排序
    private int sort=0;

    public List<ZxErpFile> getApplyFileList() {
		return applyFileList;
	}

	public void setApplyFileList(List<ZxErpFile> applyFileList) {
		this.applyFileList = applyFileList;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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

	public String getApplyId() {
        return applyId == null ? "" : applyId.trim();
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public String getCode3() {
        return code3 == null ? "" : code3.trim();
    }

    public void setCode3(String code3) {
        this.code3 = code3 == null ? null : code3.trim();
    }

    public String getSecondName() {
        return secondName == null ? "" : secondName.trim();
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName == null ? null : secondName.trim();
    }

    public String getSecondID() {
        return secondID == null ? "" : secondID.trim();
    }

    public void setSecondID(String secondID) {
        this.secondID = secondID == null ? null : secondID.trim();
    }

    public String getCodeP1() {
        return codeP1 == null ? "" : codeP1.trim();
    }

    public void setCodeP1(String codeP1) {
        this.codeP1 = codeP1 == null ? null : codeP1.trim();
    }

    public String getSecondOrgType() {
        return secondOrgType == null ? "" : secondOrgType.trim();
    }

    public void setSecondOrgType(String secondOrgType) {
        this.secondOrgType = secondOrgType == null ? null : secondOrgType.trim();
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

    public String getMaterialSource() {
        return materialSource == null ? "" : materialSource.trim();
    }

    public void setMaterialSource(String materialSource) {
        this.materialSource = materialSource == null ? null : materialSource.trim();
    }

    public String getSecondIDCodeName() {
        return secondIDCodeName == null ? "" : secondIDCodeName.trim();
    }

    public void setSecondIDCodeName(String secondIDCodeName) {
        this.secondIDCodeName = secondIDCodeName == null ? null : secondIDCodeName.trim();
    }

    public String getSecondIDCodeBh() {
        return secondIDCodeBh == null ? "" : secondIDCodeBh.trim();
    }

    public void setSecondIDCodeBh(String secondIDCodeBh) {
        this.secondIDCodeBh = secondIDCodeBh == null ? null : secondIDCodeBh.trim();
    }

    public String getSecondIDCode() {
        return secondIDCode == null ? "" : secondIDCode.trim();
    }

    public void setSecondIDCode(String secondIDCode) {
        this.secondIDCode = secondIDCode == null ? null : secondIDCode.trim();
    }

    public String getCode2T3() {
        return code2T3 == null ? "" : code2T3.trim();
    }

    public void setCode2T3(String code2T3) {
        this.code2T3 = code2T3 == null ? null : code2T3.trim();
    }

    public String getTaxRate() {
        return taxRate == null ? "" : taxRate.trim();
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate == null ? null : taxRate.trim();
    }

    public String getIsFlagZhb() {
        return isFlagZhb == null ? "" : isFlagZhb.trim();
    }

    public void setIsFlagZhb(String isFlagZhb) {
        this.isFlagZhb = isFlagZhb == null ? null : isFlagZhb.trim();
    }

    public String getIsFlag() {
        return isFlag == null ? "" : isFlag.trim();
    }

    public void setIsFlag(String isFlag) {
        this.isFlag = isFlag == null ? null : isFlag.trim();
    }

    public String getIsDeduct() {
        return isDeduct == null ? "" : isDeduct.trim();
    }

    public void setIsDeduct(String isDeduct) {
        this.isDeduct = isDeduct == null ? null : isDeduct.trim();
    }

    public BigDecimal getUpAlterContractSumTax() {
        return upAlterContractSumTax;
    }

    public void setUpAlterContractSumTax(BigDecimal upAlterContractSumTax) {
        this.upAlterContractSumTax = upAlterContractSumTax;
    }

    public BigDecimal getUpAlterContractSumNoTax() {
        return upAlterContractSumNoTax;
    }

    public void setUpAlterContractSumNoTax(BigDecimal upAlterContractSumNoTax) {
        this.upAlterContractSumNoTax = upAlterContractSumNoTax;
    }

    public BigDecimal getUpAlterContractSum() {
        return upAlterContractSum;
    }

    public void setUpAlterContractSum(BigDecimal upAlterContractSum) {
        this.upAlterContractSum = upAlterContractSum;
    }

    public String getPp8() {
        return pp8 == null ? "" : pp8.trim();
    }

    public void setPp8(String pp8) {
        this.pp8 = pp8 == null ? null : pp8.trim();
    }

    public String getStatus() {
        return status == null ? "" : status.trim();
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getInstProcessId() {
        return instProcessId == null ? "" : instProcessId.trim();
    }

    public void setInstProcessId(String instProcessId) {
        this.instProcessId = instProcessId == null ? null : instProcessId.trim();
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

    public String getFirstName() {
        return firstName == null ? "" : firstName.trim();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName == null ? null : firstName.trim();
    }

    public String getFirstID() {
        return firstID == null ? "" : firstID.trim();
    }

    public void setFirstID(String firstID) {
        this.firstID = firstID == null ? null : firstID.trim();
    }

    public String getCode1() {
        return code1 == null ? "" : code1.trim();
    }

    public void setCode1(String code1) {
        this.code1 = code1 == null ? null : code1.trim();
    }

    public String getAccountUnitCode() {
        return accountUnitCode == null ? "" : accountUnitCode.trim();
    }

    public void setAccountUnitCode(String accountUnitCode) {
        this.accountUnitCode = accountUnitCode == null ? null : accountUnitCode.trim();
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

    public String getAccountDepId() {
        return accountDepId == null ? "" : accountDepId.trim();
    }

    public void setAccountDepId(String accountDepId) {
        this.accountDepId = accountDepId == null ? null : accountDepId.trim();
    }

    public String getAccountDepName() {
        return accountDepName == null ? "" : accountDepName.trim();
    }

    public void setAccountDepName(String accountDepName) {
        this.accountDepName = accountDepName == null ? null : accountDepName.trim();
    }

    public String getCode8() {
        return code8 == null ? "" : code8.trim();
    }

    public void setCode8(String code8) {
        this.code8 = code8 == null ? null : code8.trim();
    }

    public BigDecimal getContractCostTax() {
        return contractCostTax;
    }

    public void setContractCostTax(BigDecimal contractCostTax) {
        this.contractCostTax = contractCostTax;
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

    public String getCode7() {
        return code7 == null ? "" : code7.trim();
    }

    public void setCode7(String code7) {
        this.code7 = code7 == null ? null : code7.trim();
    }

    public String getCsTimeLimit() {
        return csTimeLimit == null ? "" : csTimeLimit.trim();
    }

    public void setCsTimeLimit(String csTimeLimit) {
        this.csTimeLimit = csTimeLimit == null ? null : csTimeLimit.trim();
    }

    public String getCode() {
        return code == null ? "" : code.trim();
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getContractNo() {
        return contractNo == null ? "" : contractNo.trim();
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public BigDecimal getContractCost() {
        return contractCost;
    }

    public void setContractCost(BigDecimal contractCost) {
        this.contractCost = contractCost;
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getWorkitemID() {
        return workitemID == null ? "" : workitemID.trim();
    }

    public void setWorkitemID(String workitemID) {
        this.workitemID = workitemID == null ? null : workitemID.trim();
    }

    public String getSendToZhbID() {
        return sendToZhbID == null ? "" : sendToZhbID.trim();
    }

    public void setSendToZhbID(String sendToZhbID) {
        this.sendToZhbID = sendToZhbID == null ? null : sendToZhbID.trim();
    }

    public String getSendToJuID() {
        return sendToJuID == null ? "" : sendToJuID.trim();
    }

    public void setSendToJuID(String sendToJuID) {
        this.sendToJuID = sendToJuID == null ? null : sendToJuID.trim();
    }

    public String getBeginPer() {
        return beginPer == null ? "" : beginPer.trim();
    }

    public void setBeginPer(String beginPer) {
        this.beginPer = beginPer == null ? null : beginPer.trim();
    }

    public String getCode2() {
        return code2 == null ? "" : code2.trim();
    }

    public void setCode2(String code2) {
        this.code2 = code2 == null ? null : code2.trim();
    }

    public BigDecimal getContractCostNoTax() {
        return contractCostNoTax;
    }

    public void setContractCostNoTax(BigDecimal contractCostNoTax) {
        this.contractCostNoTax = contractCostNoTax;
    }

    public String getCode6() {
        return code6 == null ? "" : code6.trim();
    }

    public void setCode6(String code6) {
        this.code6 = code6 == null ? null : code6.trim();
    }

    public String getPp9() {
        return pp9 == null ? "" : pp9.trim();
    }

    public void setPp9(String pp9) {
        this.pp9 = pp9 == null ? null : pp9.trim();
    }

    public String getPp7() {
        return pp7 == null ? "" : pp7.trim();
    }

    public void setPp7(String pp7) {
        this.pp7 = pp7 == null ? null : pp7.trim();
    }

    public String getPp6() {
        return pp6 == null ? "" : pp6.trim();
    }

    public void setPp6(String pp6) {
        this.pp6 = pp6 == null ? null : pp6.trim();
    }

    public String getPp5() {
        return pp5 == null ? "" : pp5.trim();
    }

    public void setPp5(String pp5) {
        this.pp5 = pp5 == null ? null : pp5.trim();
    }

    public String getPp4() {
        return pp4 == null ? "" : pp4.trim();
    }

    public void setPp4(String pp4) {
        this.pp4 = pp4 == null ? null : pp4.trim();
    }

    public String getPp3() {
        return pp3 == null ? "" : pp3.trim();
    }

    public void setPp3(String pp3) {
        this.pp3 = pp3 == null ? null : pp3.trim();
    }

    public String getPp2() {
        return pp2 == null ? "" : pp2.trim();
    }

    public void setPp2(String pp2) {
        this.pp2 = pp2 == null ? null : pp2.trim();
    }

    public String getPp10() {
        return pp10 == null ? "" : pp10.trim();
    }

    public void setPp10(String pp10) {
        this.pp10 = pp10 == null ? null : pp10.trim();
    }

    public String getPp1() {
        return pp1 == null ? "" : pp1.trim();
    }

    public void setPp1(String pp1) {
        this.pp1 = pp1 == null ? null : pp1.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getComID() {
        return comID == null ? "" : comID.trim();
    }

    public void setComID(String comID) {
        this.comID = comID == null ? null : comID.trim();
    }

    public String getCombProp() {
        return combProp == null ? "" : combProp.trim();
    }

    public void setCombProp(String combProp) {
        this.combProp = combProp == null ? null : combProp.trim();
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

    public String getResCategoryID() {
        return resCategoryID == null ? "" : resCategoryID.trim();
    }

    public void setResCategoryID(String resCategoryID) {
        this.resCategoryID = resCategoryID == null ? null : resCategoryID.trim();
    }

    public String getResCategoryName() {
        return resCategoryName == null ? "" : resCategoryName.trim();
    }

    public void setResCategoryName(String resCategoryName) {
        this.resCategoryName = resCategoryName == null ? null : resCategoryName.trim();
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getShopNumber() {
		return shopNumber;
	}

	public void setShopNumber(String shopNumber) {
		this.shopNumber = shopNumber;
	}

	public String getShopWay() {
		return shopWay;
	}

	public void setShopWay(String shopWay) {
		this.shopWay = shopWay;
	}

	public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

}
