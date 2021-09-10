package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzThreeDirector extends BasePojo {
    private String threeDirectorId;

    private String projectId;

    private String projectName;

    private String periodId;

    private String periodName;

    private String meetNumberId;

    private String meetNumberName;

    private String meetTypeId;

    private String meetTypeName;

    private Date meetDate;

    private String meetPlace;

    private String meetVoteId;

    private String meetVoteName;

    private String originalId;

    private String originalName;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZjTzFile> zjTzFileList1;

    private List<ZjTzFile> zjTzFileList2;

    private List<ZjTzFile> zjTzFileList3;

    private List<ZjTzThreeDirectorBill> billList;

    private String projectIdSql;
    
    private List<Date> meetDateList;
    
    private Date startTime;
    
    private Date endTime;

    public String getThreeDirectorId() {
        return threeDirectorId == null ? "" : threeDirectorId.trim();
    }

    public void setThreeDirectorId(String threeDirectorId) {
        this.threeDirectorId = threeDirectorId == null ? null : threeDirectorId.trim();
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

    public String getMeetNumberId() {
        return meetNumberId == null ? "" : meetNumberId.trim();
    }

    public void setMeetNumberId(String meetNumberId) {
        this.meetNumberId = meetNumberId == null ? null : meetNumberId.trim();
    }

    public String getMeetNumberName() {
        return meetNumberName == null ? "" : meetNumberName.trim();
    }

    public void setMeetNumberName(String meetNumberName) {
        this.meetNumberName = meetNumberName == null ? null : meetNumberName.trim();
    }

    public String getMeetTypeId() {
        return meetTypeId == null ? "" : meetTypeId.trim();
    }

    public void setMeetTypeId(String meetTypeId) {
        this.meetTypeId = meetTypeId == null ? null : meetTypeId.trim();
    }

    public String getMeetTypeName() {
        return meetTypeName == null ? "" : meetTypeName.trim();
    }

    public void setMeetTypeName(String meetTypeName) {
        this.meetTypeName = meetTypeName == null ? null : meetTypeName.trim();
    }

    public Date getMeetDate() {
        return meetDate;
    }

    public void setMeetDate(Date meetDate) {
        this.meetDate = meetDate;
    }

    public String getMeetPlace() {
        return meetPlace == null ? "" : meetPlace.trim();
    }

    public void setMeetPlace(String meetPlace) {
        this.meetPlace = meetPlace == null ? null : meetPlace.trim();
    }

    public String getMeetVoteId() {
        return meetVoteId == null ? "" : meetVoteId.trim();
    }

    public void setMeetVoteId(String meetVoteId) {
        this.meetVoteId = meetVoteId == null ? null : meetVoteId.trim();
    }

    public String getMeetVoteName() {
        return meetVoteName == null ? "" : meetVoteName.trim();
    }

    public void setMeetVoteName(String meetVoteName) {
        this.meetVoteName = meetVoteName == null ? null : meetVoteName.trim();
    }

    public String getOriginalId() {
        return originalId == null ? "" : originalId.trim();
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId == null ? null : originalId.trim();
    }

    public String getOriginalName() {
        return originalName == null ? "" : originalName.trim();
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName == null ? null : originalName.trim();
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

    public List<ZjTzFile> getZjTzFileList1() {
        return zjTzFileList1;
    }

    public void setZjTzFileList1(List<ZjTzFile> zjTzFileList1) {
        this.zjTzFileList1 = zjTzFileList1;
    }

    public List<ZjTzFile> getZjTzFileList2() {
        return zjTzFileList2;
    }

    public void setZjTzFileList2(List<ZjTzFile> zjTzFileList2) {
        this.zjTzFileList2 = zjTzFileList2;
    }

    public List<ZjTzFile> getZjTzFileList3() {
        return zjTzFileList3;
    }

    public void setZjTzFileList3(List<ZjTzFile> zjTzFileList3) {
        this.zjTzFileList3 = zjTzFileList3;
    }

    public List<ZjTzThreeDirectorBill> getBillList() {
        return billList;
    }

    public void setBillList(List<ZjTzThreeDirectorBill> billList) {
        this.billList = billList;
    }

    public String getProjectIdSql() {
        return projectIdSql == null ? "" : projectIdSql.trim();
    }

    public void setProjectIdSql(String projectIdSql) {
        this.projectIdSql = projectIdSql == null ? null : projectIdSql.trim();
    }

	public List<Date> getMeetDateList() {
		return meetDateList;
	}

	public void setMeetDateList(List<Date> meetDateList) {
		this.meetDateList = meetDateList;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}