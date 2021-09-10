package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojoFlow;
import com.google.common.collect.Lists;

public class ZxCtContrDqjz extends BasePojoFlow {
    // 主键
    private String id;

    // 项目名称id
    private String orgID;

    // 项目名称
    private String orgName;

    // 建立时间
    private Date buildTime;

    // 发起人id
    private String reporterID;

    // 发起人
    private String reporter;

    // 初始预计总收入
    private BigDecimal csBudgetAmt;

    // 当前预计总收入
    private BigDecimal bhBudgetAmt;

    // 初始预计总成本
    private BigDecimal bhBudgetCost;

    // 当前预计总成本
    private BigDecimal sgBudgetCost;

    // 流程实例id
    private String instProcessId;

    // workId
    private String workId;

    // 审批状态
    private String apih5FlowStatus;

    // 意见
    private String opinionField1;

    // 意见
    private String opinionField2;

    // 意见
    private String opinionField3;

    // 意见
    private String opinionField4;

    // 意见
    private String opinionField5;

    // 意见
    private String opinionField6;

    // 意见
    private String opinionField7;

    // 意见
    private String opinionField8;

    // 意见
    private String opinionField9;

    // 意见
    private String opinionField10;

    // 清单
    private String item;

    // 说明
    private String remark;

    // isReport
    private String isReport;

    // isFlag
    private String isFlag;

    // auditSys
    private String auditSys;

    // auditWorkitemID
    private String auditWorkitemID;

    // 上期预计总收入
    private BigDecimal bhBudgetAmtUp;

    // 上期预计总成本
    private BigDecimal sgBudgetCostUp;

    // isFlagZhb
    private String isFlagZhb;

    // contractAudit
    private String contractAudit;

    // 清单list
    private List<ZxCtContrJzItem> zxCtContrJzItemList;

    // 公司id
    private String comID;

    // 公司name
    private String comName;
    
    private List<ZxCtContrJzItem> itemList;
    
    private List<ZxCtContrJzItemBase> itemBaseList;
    
    private String subType2;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public Date getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(Date buildTime) {
        this.buildTime = buildTime;
    }

    public String getReporterID() {
        return reporterID == null ? "" : reporterID.trim();
    }

    public void setReporterID(String reporterID) {
        this.reporterID = reporterID == null ? null : reporterID.trim();
    }

    public String getReporter() {
        return reporter == null ? "" : reporter.trim();
    }

    public void setReporter(String reporter) {
        this.reporter = reporter == null ? null : reporter.trim();
    }

    public BigDecimal getCsBudgetAmt() {
        return csBudgetAmt;
    }

    public void setCsBudgetAmt(BigDecimal csBudgetAmt) {
        this.csBudgetAmt = csBudgetAmt;
    }

    public BigDecimal getBhBudgetAmt() {
        return bhBudgetAmt;
    }

    public void setBhBudgetAmt(BigDecimal bhBudgetAmt) {
        this.bhBudgetAmt = bhBudgetAmt;
    }

    public BigDecimal getBhBudgetCost() {
        return bhBudgetCost;
    }

    public void setBhBudgetCost(BigDecimal bhBudgetCost) {
        this.bhBudgetCost = bhBudgetCost;
    }

    public BigDecimal getSgBudgetCost() {
        return sgBudgetCost;
    }

    public void setSgBudgetCost(BigDecimal sgBudgetCost) {
        this.sgBudgetCost = sgBudgetCost;
    }

    public String getInstProcessId() {
        return instProcessId == null ? "" : instProcessId.trim();
    }

    public void setInstProcessId(String instProcessId) {
        this.instProcessId = instProcessId == null ? null : instProcessId.trim();
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

    public String getItem() {
        return item == null ? "" : item.trim();
    }

    public void setItem(String item) {
        this.item = item == null ? null : item.trim();
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getIsReport() {
        return isReport == null ? "" : isReport.trim();
    }

    public void setIsReport(String isReport) {
        this.isReport = isReport == null ? null : isReport.trim();
    }

    public String getIsFlag() {
        return isFlag == null ? "" : isFlag.trim();
    }

    public void setIsFlag(String isFlag) {
        this.isFlag = isFlag == null ? null : isFlag.trim();
    }

    public String getAuditSys() {
        return auditSys == null ? "" : auditSys.trim();
    }

    public void setAuditSys(String auditSys) {
        this.auditSys = auditSys == null ? null : auditSys.trim();
    }

    public String getAuditWorkitemID() {
        return auditWorkitemID == null ? "" : auditWorkitemID.trim();
    }

    public void setAuditWorkitemID(String auditWorkitemID) {
        this.auditWorkitemID = auditWorkitemID == null ? null : auditWorkitemID.trim();
    }

    public BigDecimal getBhBudgetAmtUp() {
        return bhBudgetAmtUp;
    }

    public void setBhBudgetAmtUp(BigDecimal bhBudgetAmtUp) {
        this.bhBudgetAmtUp = bhBudgetAmtUp;
    }

    public BigDecimal getSgBudgetCostUp() {
        return sgBudgetCostUp;
    }

    public void setSgBudgetCostUp(BigDecimal sgBudgetCostUp) {
        this.sgBudgetCostUp = sgBudgetCostUp;
    }

    public String getIsFlagZhb() {
        return isFlagZhb == null ? "" : isFlagZhb.trim();
    }

    public void setIsFlagZhb(String isFlagZhb) {
        this.isFlagZhb = isFlagZhb == null ? null : isFlagZhb.trim();
    }

    public String getContractAudit() {
        return contractAudit == null ? "" : contractAudit.trim();
    }

    public void setContractAudit(String contractAudit) {
        this.contractAudit = contractAudit == null ? null : contractAudit.trim();
    }

    public List<ZxCtContrJzItem> getZxCtContrJzItemList() {
        return zxCtContrJzItemList == null ? Lists.newArrayList() : zxCtContrJzItemList;
    }

    public void setZxCtContrJzItemList(List<ZxCtContrJzItem> zxCtContrJzItemList) {
        this.zxCtContrJzItemList = zxCtContrJzItemList == null ? null : zxCtContrJzItemList;
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

	public List<ZxCtContrJzItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<ZxCtContrJzItem> itemList) {
		this.itemList = itemList;
	}

	public List<ZxCtContrJzItemBase> getItemBaseList() {
		return itemBaseList;
	}

	public void setItemBaseList(List<ZxCtContrJzItemBase> itemBaseList) {
		this.itemBaseList = itemBaseList;
	}

	public String getSubType2() {
		return subType2;
	}

	public void setSubType2(String subType2) {
		this.subType2 = subType2;
	}

}
