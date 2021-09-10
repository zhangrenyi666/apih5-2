package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;

public class ZxSaProjectFinish extends BasePojo {
	private String id;

	private String contractID;

	private String contractNo;

	private String contractName;

	private String orgID;

	private String orgName;

	private BigDecimal contractCost;

	private BigDecimal contractMoney;

	private String projectManager;

	private Date actualStartDate;

	private Date actualEndDate;

	private String csTimeLimit;

	private Date realBeginDate;

	private Date realEndDate;

	private Date realSettleEndDate;

	private Date determineFilingDate;

	private Date planSettleCloseDate;

	private Date realSettleCloseDate;

	private Date reportDate;

	private String reportPerson;

	private String remark;

	private String finishStatus;

	private String auditStatus;

	private String comID;

	private String comName;

	private String comOrders;

	private String combProp;

	private String pp1;

	private String pp2;

	private String pp3;

	private String pp4;

	private String pp5;

	private String pp6;

	private String pp7;

	private String pp8;

	private String pp9;

	private String pp10;

	private Date guidangDate;

	private String delFlag;

	private Date createTime;

	private String createUser;

	private String createUserName;

	private Date modifyTime;

	private String modifyUser;

	private String modifyUserName;
	
	private String settleTypeCode;

	private List<ZxSaProjectFinishItem> itemList;

	public String getId() {
		return id == null ? "" : id.trim();
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
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

	public BigDecimal getContractCost() {
		return contractCost;
	}

	public void setContractCost(BigDecimal contractCost) {
		this.contractCost = contractCost;
	}

	public BigDecimal getContractMoney() {
		return contractMoney;
	}

	public void setContractMoney(BigDecimal contractMoney) {
		this.contractMoney = contractMoney;
	}

	public String getProjectManager() {
		return projectManager == null ? "" : projectManager.trim();
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager == null ? null : projectManager.trim();
	}

	public Date getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(Date actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public Date getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(Date actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public String getCsTimeLimit() {
		return csTimeLimit == null ? "" : csTimeLimit.trim();
	}

	public void setCsTimeLimit(String csTimeLimit) {
		this.csTimeLimit = csTimeLimit == null ? null : csTimeLimit.trim();
	}

	public Date getRealBeginDate() {
		return realBeginDate;
	}

	public void setRealBeginDate(Date realBeginDate) {
		this.realBeginDate = realBeginDate;
	}

	public Date getRealEndDate() {
		return realEndDate;
	}

	public void setRealEndDate(Date realEndDate) {
		this.realEndDate = realEndDate;
	}

	public Date getRealSettleEndDate() {
		return realSettleEndDate;
	}

	public void setRealSettleEndDate(Date realSettleEndDate) {
		this.realSettleEndDate = realSettleEndDate;
	}

	public Date getDetermineFilingDate() {
		return determineFilingDate;
	}

	public void setDetermineFilingDate(Date determineFilingDate) {
		this.determineFilingDate = determineFilingDate;
	}

	public Date getPlanSettleCloseDate() {
		return planSettleCloseDate;
	}

	public void setPlanSettleCloseDate(Date planSettleCloseDate) {
		this.planSettleCloseDate = planSettleCloseDate;
	}

	public Date getRealSettleCloseDate() {
		return realSettleCloseDate;
	}

	public void setRealSettleCloseDate(Date realSettleCloseDate) {
		this.realSettleCloseDate = realSettleCloseDate;
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

	public String getFinishStatus() {
		return finishStatus == null ? "" : finishStatus.trim();
	}

	public void setFinishStatus(String finishStatus) {
		this.finishStatus = finishStatus == null ? null : finishStatus.trim();
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

	public Date getGuidangDate() {
		return guidangDate;
	}

	public void setGuidangDate(Date guidangDate) {
		this.guidangDate = guidangDate;
	}

	public String getDelFlag() {
		return delFlag == null ? "" : delFlag.trim();
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag == null ? null : delFlag.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser == null ? "" : createUser.trim();
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser == null ? null : createUser.trim();
	}

	public String getCreateUserName() {
		return createUserName == null ? "" : createUserName.trim();
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName == null ? null : createUserName.trim();
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyUser() {
		return modifyUser == null ? "" : modifyUser.trim();
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser == null ? null : modifyUser.trim();
	}

	public String getModifyUserName() {
		return modifyUserName == null ? "" : modifyUserName.trim();
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName == null ? null : modifyUserName.trim();
	}

	public String getSettleTypeCode() {
        return settleTypeCode == null ? "" : settleTypeCode.trim();
    }

    public void setSettleTypeCode(String settleTypeCode) {
        this.settleTypeCode = settleTypeCode == null ? null : settleTypeCode.trim();
    }
	
	public List<ZxSaProjectFinishItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<ZxSaProjectFinishItem> itemList) {
		this.itemList = itemList;
	}

}
