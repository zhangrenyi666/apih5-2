package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZxEqEquipFact extends BasePojo {
    private String id;

    private String orgID;

    private String orgName;

    private Date periodDate;

    private String period;

    private String reporterID;

    private String reporter;

    private String depID;

    private String depName;

    private Date reportDate;

    private String remark;

    private String comID;

    private String comName;

    private Date editTime;

    private Date copyPeriodDate;

    private String copyPeriod;

    private String auditStatus;

    private String isUse;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZxErpFile> fileList;

    private List<ZxEqEquipFactItem> itemList;
    
    private int source1;
    private int source2;
    private int source3;
    private String idFlag;

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

    public Date getPeriodDate() {
        return periodDate;
    }

    public void setPeriodDate(Date periodDate) {
        this.periodDate = periodDate;
    }

    public String getPeriod() {
        return period == null ? "" : period.trim();
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
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

    public String getDepID() {
        return depID == null ? "" : depID.trim();
    }

    public void setDepID(String depID) {
        this.depID = depID == null ? null : depID.trim();
    }

    public String getDepName() {
        return depName == null ? "" : depName.trim();
    }

    public void setDepName(String depName) {
        this.depName = depName == null ? null : depName.trim();
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public Date getCopyPeriodDate() {
        return copyPeriodDate;
    }

    public void setCopyPeriodDate(Date copyPeriodDate) {
        this.copyPeriodDate = copyPeriodDate;
    }

    public String getCopyPeriod() {
        return copyPeriod == null ? "" : copyPeriod.trim();
    }

    public void setCopyPeriod(String copyPeriod) {
        this.copyPeriod = copyPeriod == null ? null : copyPeriod.trim();
    }

    public String getAuditStatus() {
        return auditStatus == null ? "" : auditStatus.trim();
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public String getIsUse() {
        return isUse == null ? "" : isUse.trim();
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse == null ? null : isUse.trim();
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

    public List<ZxErpFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<ZxErpFile> fileList) {
        this.fileList = fileList;
    }

    public List<ZxEqEquipFactItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<ZxEqEquipFactItem> itemList) {
        this.itemList = itemList;
    }

	public int getSource1() {
		return source1;
	}

	public void setSource1(int source1) {
		this.source1 = source1;
	}

	public int getSource2() {
		return source2;
	}

	public void setSource2(int source2) {
		this.source2 = source2;
	}

	public int getSource3() {
		return source3;
	}

	public void setSource3(int source3) {
		this.source3 = source3;
	}

	public String getIdFlag() {
		return idFlag;
	}

	public void setIdFlag(String idFlag) {
		this.idFlag = idFlag;
	}
	
}