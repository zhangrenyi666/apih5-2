package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjTzSupervisorMonthlyMeeting extends BasePojo {
    private String monthlyMeetingId;

    private String projectId;

    private String projectName;

    private String subprojectsId;

    private String subprojectsName;

    private String monthlyMeetingMonth;

    private String monthlyMeetingContent;

    private Date monthlyMeetingDate;

    private String monthlyMeetingUser;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private List<ZjTzFile> monthlyMeetingFileList;
    
    private String projectIdSql;
    
    public String getProjectIdSql() {
        return projectIdSql == null ? "" : projectIdSql.trim();
    }

    public void setProjectIdSql(String projectIdSql) {
        this.projectIdSql = projectIdSql == null ? null : projectIdSql.trim();
    }
        
    private String monthlyMeetingMonthStr;    

    public String getMonthlyMeetingMonthStr() {
		return monthlyMeetingMonthStr;
	}

	public void setMonthlyMeetingMonthStr(String monthlyMeetingMonthStr) {
		this.monthlyMeetingMonthStr = monthlyMeetingMonthStr;
	}

	public List<ZjTzFile> getMonthlyMeetingFileList() {
		return monthlyMeetingFileList;
	}

	public void setMonthlyMeetingFileList(List<ZjTzFile> monthlyMeetingFileList) {
		this.monthlyMeetingFileList = monthlyMeetingFileList;
	}

	public String getMonthlyMeetingId() {
        return monthlyMeetingId == null ? "" : monthlyMeetingId.trim();
    }

    public void setMonthlyMeetingId(String monthlyMeetingId) {
        this.monthlyMeetingId = monthlyMeetingId == null ? null : monthlyMeetingId.trim();
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

    public String getSubprojectsId() {
        return subprojectsId == null ? "" : subprojectsId.trim();
    }

    public void setSubprojectsId(String subprojectsId) {
        this.subprojectsId = subprojectsId == null ? null : subprojectsId.trim();
    }

    public String getSubprojectsName() {
        return subprojectsName == null ? "" : subprojectsName.trim();
    }

    public void setSubprojectsName(String subprojectsName) {
        this.subprojectsName = subprojectsName == null ? null : subprojectsName.trim();
    }

    public String getMonthlyMeetingMonth() {
        return monthlyMeetingMonth == null ? "" : monthlyMeetingMonth.trim();
    }

    public void setMonthlyMeetingMonth(String monthlyMeetingMonth) {
        this.monthlyMeetingMonth = monthlyMeetingMonth == null ? null : monthlyMeetingMonth.trim();
    }

    public String getMonthlyMeetingContent() {
        return monthlyMeetingContent == null ? "" : monthlyMeetingContent.trim();
    }

    public void setMonthlyMeetingContent(String monthlyMeetingContent) {
        this.monthlyMeetingContent = monthlyMeetingContent == null ? null : monthlyMeetingContent.trim();
    }

    public Date getMonthlyMeetingDate() {
        return monthlyMeetingDate;
    }

    public void setMonthlyMeetingDate(Date monthlyMeetingDate) {
        this.monthlyMeetingDate = monthlyMeetingDate;
    }

    public String getMonthlyMeetingUser() {
        return monthlyMeetingUser == null ? "" : monthlyMeetingUser.trim();
    }

    public void setMonthlyMeetingUser(String monthlyMeetingUser) {
        this.monthlyMeetingUser = monthlyMeetingUser == null ? null : monthlyMeetingUser.trim();
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

}

