package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;

public class ZjTzExecutiveMeetChangeRecord extends BasePojo {
    private String executiveMeetChangeRecordId;

    private String executiveMeetId;

    private String projectId;

    private String periodId;

    private String periodName;

    private Date businessDate;

    private Date startDate;

    private Date endDate;

    private String remarks;

    private String statusId;

    private String statusName;

    private String conentDesc;

    private String legal;

    private Integer changeNum;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZjTzFile> zjTzFileList;

    private List<ZjTzExecutivePersonnel> personnelList;

    public String getExecutiveMeetChangeRecordId() {
        return executiveMeetChangeRecordId == null ? "" : executiveMeetChangeRecordId.trim();
    }

    public void setExecutiveMeetChangeRecordId(String executiveMeetChangeRecordId) {
        this.executiveMeetChangeRecordId = executiveMeetChangeRecordId == null ? null : executiveMeetChangeRecordId.trim();
    }

    public String getExecutiveMeetId() {
        return executiveMeetId == null ? "" : executiveMeetId.trim();
    }

    public void setExecutiveMeetId(String executiveMeetId) {
        this.executiveMeetId = executiveMeetId == null ? null : executiveMeetId.trim();
    }

    public String getProjectId() {
        return projectId == null ? "" : projectId.trim();
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getPeriodId() {
        return periodId == null ? "" : periodId.trim();
    }

    public void setPeriodId(String periodId) {
        this.periodId = periodId == null ? null : periodId.trim();
    }

    public String getPeriodName() {
        return periodName == null ? "" : periodName.trim();
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName == null ? null : periodName.trim();
    }

    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
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

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getStatusId() {
        return statusId == null ? "" : statusId.trim();
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId == null ? null : statusId.trim();
    }

    public String getStatusName() {
        return statusName == null ? "" : statusName.trim();
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName == null ? null : statusName.trim();
    }

    public String getConentDesc() {
        return conentDesc == null ? "" : conentDesc.trim();
    }

    public void setConentDesc(String conentDesc) {
        this.conentDesc = conentDesc == null ? null : conentDesc.trim();
    }

    public String getLegal() {
        return legal == null ? "" : legal.trim();
    }

    public void setLegal(String legal) {
        this.legal = legal == null ? null : legal.trim();
    }

    public Integer getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(Integer changeNum) {
        this.changeNum = changeNum;
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

    public List<ZjTzFile> getZjTzFileList() {
        return zjTzFileList;
    }

    public void setZjTzFileList(List<ZjTzFile> zjTzFileList) {
        this.zjTzFileList = zjTzFileList;
    }

    public List<ZjTzExecutivePersonnel> getPersonnelList() {
        return personnelList;
    }

    public void setPersonnelList(List<ZjTzExecutivePersonnel> personnelList) {
        this.personnelList = personnelList;
    }

}

