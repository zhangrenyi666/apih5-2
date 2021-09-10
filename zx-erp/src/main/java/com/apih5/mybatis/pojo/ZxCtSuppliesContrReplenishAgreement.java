package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojoFlow;

public class ZxCtSuppliesContrReplenishAgreement extends BasePojoFlow {
    // 主键
    private String replenishAgreementId;

    // 乙方名称
    private String secondName;

    // 乙方ID
    private String secondID;

    // 物资来源
    private String materialSource;

    // 税率(%)
    private String taxRate;

    // 是否局指审批
    private String isFlagZhb;

    // 所属公司ID
    private String companyId;
    
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

    // 合同税额(万元)
    private BigDecimal contractCostTax;

    // 合同签定人
    private String agent;

    // 合同内容
    private String content;

    // 合同名称ID
    private String pp6;

    // 合同名称
    private String contractName;

    // 合同类型
    private String contractType;

    // 合同类型
    private String code7;

    // 合同工期
    private String csTimeLimit;

    // 含税合同金额(万元)
    private BigDecimal contractCost;

    // 公文任务ID
    private String workitemID;

    // 发送局指判断ID
    private String sendToZhbID;

    // 发送局判断ID
    private String sendToJuID;

    // 发起人
    private String beginPer;

    // 不含税合同金额(万元)
    private BigDecimal contractCostNoTax;

    // 补充协议名称
    private String pp3;

    // 补充协议编号
    private String contractNo;

    // 变更后税额(万元)
    private BigDecimal alterContractSumTax;

    // 变更后含税金额(万元)
    private BigDecimal alterContractSum;

    // 变更后不含税金额(万元)
    private BigDecimal alterContractSumNoTax;

    // 本期增减税额(万元)
    private BigDecimal pp4Tax;

    // 本期含税增减金额(万元)
    private String pp4;

    // 本期不含税增减金额(万元)
    private BigDecimal pp4NoTax;

    // pp9
    private String pp9;

    // pp7
    private String pp7;

    // pp5
    private String pp5;

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

    // 备注
    private String remarks;
    
    private String shopNumber;
    
    private String shopWay;

    // 批复日期
    private Date replyDate;

    // 批复单位
    private String replyUnit;

    // 变更原因
    private String alterReason;

    // 变更内容
    private String alterContent;

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
    
    private String zxCtSuppliesContractChangeId;
    
    private List<ZxCtSuppliesContrReplenishShopDetail> replenishShopDetailedList;
    
    private List<ZxCtSuppliesContrReplenishLeaseDetail> replenishLeaseDetailedList;
    
    private List<ZxErpFile> replenishAgreementFileList;
    
    private List<ZxErpFile> replenishShopListFileList;
    
    private List<ZxErpFile> documentFileList;

    public List<ZxErpFile> getDocumentFileList() {
		return documentFileList;
	}

	public void setDocumentFileList(List<ZxErpFile> documentFileList) {
		this.documentFileList = documentFileList;
	}

	public List<ZxErpFile> getReplenishAgreementFileList() {
		return replenishAgreementFileList;
	}

	public void setReplenishAgreementFileList(List<ZxErpFile> replenishAgreementFileList) {
		this.replenishAgreementFileList = replenishAgreementFileList;
	}

	public List<ZxErpFile> getReplenishShopListFileList() {
		return replenishShopListFileList;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public void setReplenishShopListFileList(List<ZxErpFile> replenishShopListFileList) {
		this.replenishShopListFileList = replenishShopListFileList;
	}

	public List<ZxCtSuppliesContrReplenishShopDetail> getReplenishShopDetailedList() {
		return replenishShopDetailedList;
	}

	public void setReplenishShopDetailedList(List<ZxCtSuppliesContrReplenishShopDetail> replenishShopDetailedList) {
		this.replenishShopDetailedList = replenishShopDetailedList;
	}

	public List<ZxCtSuppliesContrReplenishLeaseDetail> getReplenishLeaseDetailedList() {
		return replenishLeaseDetailedList;
	}

	public void setReplenishLeaseDetailedList(List<ZxCtSuppliesContrReplenishLeaseDetail> replenishLeaseDetailedList) {
		this.replenishLeaseDetailedList = replenishLeaseDetailedList;
	}

	public String getZxCtSuppliesContractChangeId() {
		return zxCtSuppliesContractChangeId;
	}

	public void setZxCtSuppliesContractChangeId(String zxCtSuppliesContractChangeId) {
		this.zxCtSuppliesContractChangeId = zxCtSuppliesContractChangeId;
	}

	public String getOpinionField1() {
		return opinionField1;
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

	// 排序
    private int sort=0;

	public String getReplyUnit() {
		return replyUnit;
	}

	public void setReplyUnit(String replyUnit) {
		this.replyUnit = replyUnit;
	}

	public Date getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(Date replyDate) {
		this.replyDate = replyDate;
	}

	public String getAlterReason() {
		return alterReason;
	}

	public void setAlterReason(String alterReason) {
		this.alterReason = alterReason;
	}

	public String getAlterContent() {
		return alterContent;
	}

	public void setAlterContent(String alterContent) {
		this.alterContent = alterContent;
	}

	public String getReplenishAgreementId() {
        return replenishAgreementId == null ? "" : replenishAgreementId.trim();
    }

    public void setReplenishAgreementId(String replenishAgreementId) {
        this.replenishAgreementId = replenishAgreementId == null ? null : replenishAgreementId.trim();
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

    public String getMaterialSource() {
        return materialSource == null ? "" : materialSource.trim();
    }

    public void setMaterialSource(String materialSource) {
        this.materialSource = materialSource == null ? null : materialSource.trim();
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

    public String getPp6() {
        return pp6 == null ? "" : pp6.trim();
    }

    public void setPp6(String pp6) {
        this.pp6 = pp6 == null ? null : pp6.trim();
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

    public BigDecimal getContractCost() {
        return contractCost;
    }

    public void setContractCost(BigDecimal contractCost) {
        this.contractCost = contractCost;
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

    public BigDecimal getContractCostNoTax() {
        return contractCostNoTax;
    }

    public void setContractCostNoTax(BigDecimal contractCostNoTax) {
        this.contractCostNoTax = contractCostNoTax;
    }

    public String getPp3() {
        return pp3 == null ? "" : pp3.trim();
    }

    public void setPp3(String pp3) {
        this.pp3 = pp3 == null ? null : pp3.trim();
    }

    public String getContractNo() {
        return contractNo == null ? "" : contractNo.trim();
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public BigDecimal getAlterContractSumTax() {
        return alterContractSumTax;
    }

    public void setAlterContractSumTax(BigDecimal alterContractSumTax) {
        this.alterContractSumTax = alterContractSumTax;
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

    public BigDecimal getPp4Tax() {
        return pp4Tax;
    }

    public void setPp4Tax(BigDecimal pp4Tax) {
        this.pp4Tax = pp4Tax;
    }

    public String getPp4() {
        return pp4 == null ? "" : pp4.trim();
    }

    public void setPp4(String pp4) {
        this.pp4 = pp4 == null ? null : pp4.trim();
    }

    public BigDecimal getPp4NoTax() {
        return pp4NoTax;
    }

    public void setPp4NoTax(BigDecimal pp4NoTax) {
        this.pp4NoTax = pp4NoTax;
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

    public String getPp5() {
        return pp5 == null ? "" : pp5.trim();
    }

    public void setPp5(String pp5) {
        this.pp5 = pp5 == null ? null : pp5.trim();
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
