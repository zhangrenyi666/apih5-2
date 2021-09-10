package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZxEqEquipScrape extends BasePojo {
    private String id;

    private String orgID;

    private Date periodDate;

    private String period;

    private String equipID;

    private String equipName;

    private String financeNo;

    private String spec;

    private String factory;

    private String enginerno;

    private String downserial;

    private Date outFactoryDate;

    private String requireYear;

    private String actualYear;

    private BigDecimal orginalValue;

    private BigDecimal deprevalue;

    private BigDecimal leftvalue;

    private String place;

    private String passNo;

    private String scrapeReason;

    private String option1;

    private String handleway;

    private String option2;

    private String auditStatus;

    private String remark;

    private Date editTime;

    private String approvalNo;

    private Date scrapeDate;

    private String comID;

    private String comName;

    private String orgName;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZxErpFile> fileList;
    
    private String outFactoryDateStr;
    
    private String seeFlagForJu;

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

    public String getEquipID() {
        return equipID == null ? "" : equipID.trim();
    }

    public void setEquipID(String equipID) {
        this.equipID = equipID == null ? null : equipID.trim();
    }

    public String getEquipName() {
        return equipName == null ? "" : equipName.trim();
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName == null ? null : equipName.trim();
    }

    public String getFinanceNo() {
        return financeNo == null ? "" : financeNo.trim();
    }

    public void setFinanceNo(String financeNo) {
        this.financeNo = financeNo == null ? null : financeNo.trim();
    }

    public String getSpec() {
        return spec == null ? "" : spec.trim();
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getFactory() {
        return factory == null ? "" : factory.trim();
    }

    public void setFactory(String factory) {
        this.factory = factory == null ? null : factory.trim();
    }

    public String getEnginerno() {
        return enginerno == null ? "" : enginerno.trim();
    }

    public void setEnginerno(String enginerno) {
        this.enginerno = enginerno == null ? null : enginerno.trim();
    }

    public String getDownserial() {
        return downserial == null ? "" : downserial.trim();
    }

    public void setDownserial(String downserial) {
        this.downserial = downserial == null ? null : downserial.trim();
    }

    public Date getOutFactoryDate() {
        return outFactoryDate;
    }

    public void setOutFactoryDate(Date outFactoryDate) {
        this.outFactoryDate = outFactoryDate;
    }

    public String getRequireYear() {
        return requireYear == null ? "" : requireYear.trim();
    }

    public void setRequireYear(String requireYear) {
        this.requireYear = requireYear == null ? null : requireYear.trim();
    }

    public String getActualYear() {
        return actualYear == null ? "" : actualYear.trim();
    }

    public void setActualYear(String actualYear) {
        this.actualYear = actualYear == null ? null : actualYear.trim();
    }

    public BigDecimal getOrginalValue() {
        return orginalValue;
    }

    public void setOrginalValue(BigDecimal orginalValue) {
        this.orginalValue = orginalValue;
    }

    public BigDecimal getDeprevalue() {
        return deprevalue;
    }

    public void setDeprevalue(BigDecimal deprevalue) {
        this.deprevalue = deprevalue;
    }

    public BigDecimal getLeftvalue() {
        return leftvalue;
    }

    public void setLeftvalue(BigDecimal leftvalue) {
        this.leftvalue = leftvalue;
    }

    public String getPlace() {
        return place == null ? "" : place.trim();
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public String getPassNo() {
        return passNo == null ? "" : passNo.trim();
    }

    public void setPassNo(String passNo) {
        this.passNo = passNo == null ? null : passNo.trim();
    }

    public String getScrapeReason() {
        return scrapeReason == null ? "" : scrapeReason.trim();
    }

    public void setScrapeReason(String scrapeReason) {
        this.scrapeReason = scrapeReason == null ? null : scrapeReason.trim();
    }

    public String getOption1() {
        return option1 == null ? "" : option1.trim();
    }

    public void setOption1(String option1) {
        this.option1 = option1 == null ? null : option1.trim();
    }

    public String getHandleway() {
        return handleway == null ? "" : handleway.trim();
    }

    public void setHandleway(String handleway) {
        this.handleway = handleway == null ? null : handleway.trim();
    }

    public String getOption2() {
        return option2 == null ? "" : option2.trim();
    }

    public void setOption2(String option2) {
        this.option2 = option2 == null ? null : option2.trim();
    }

    public String getAuditStatus() {
        return auditStatus == null ? "" : auditStatus.trim();
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getApprovalNo() {
        return approvalNo == null ? "" : approvalNo.trim();
    }

    public void setApprovalNo(String approvalNo) {
        this.approvalNo = approvalNo == null ? null : approvalNo.trim();
    }

    public Date getScrapeDate() {
        return scrapeDate;
    }

    public void setScrapeDate(Date scrapeDate) {
        this.scrapeDate = scrapeDate;
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

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
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

	public String getOutFactoryDateStr() {
		return outFactoryDateStr;
	}

	public void setOutFactoryDateStr(String outFactoryDateStr) {
		this.outFactoryDateStr = outFactoryDateStr;
	}

	public String getSeeFlagForJu() {
		return seeFlagForJu;
	}

	public void setSeeFlagForJu(String seeFlagForJu) {
		this.seeFlagForJu = seeFlagForJu;
	}
    
}