package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjTzOtherDuty extends BasePojo {
    private String otherDutyId;

    private String projectId;

    private String projectName;

    private String subprojectsId;

    private String subprojectsName;

    private String otherDutyTitle;

    private String otherDutyContent;

    private Date otherDutyDate;

    private String otherDutyUser;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private List<ZjTzFile> otherDutyFileList;
    
    private String projectIdSql;
    
    public String getProjectIdSql() {
        return projectIdSql == null ? "" : projectIdSql.trim();
    }

    public void setProjectIdSql(String projectIdSql) {
        this.projectIdSql = projectIdSql == null ? null : projectIdSql.trim();
    }
    
    public List<ZjTzFile> getOtherDutyFileList() {
		return otherDutyFileList;
	}

	public void setOtherDutyFileList(List<ZjTzFile> otherDutyFileList) {
		this.otherDutyFileList = otherDutyFileList;
	}

	public String getOtherDutyId() {
        return otherDutyId == null ? "" : otherDutyId.trim();
    }

    public void setOtherDutyId(String otherDutyId) {
        this.otherDutyId = otherDutyId == null ? null : otherDutyId.trim();
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

    public String getOtherDutyTitle() {
        return otherDutyTitle == null ? "" : otherDutyTitle.trim();
    }

    public void setOtherDutyTitle(String otherDutyTitle) {
        this.otherDutyTitle = otherDutyTitle == null ? null : otherDutyTitle.trim();
    }

    public String getOtherDutyContent() {
        return otherDutyContent == null ? "" : otherDutyContent.trim();
    }

    public void setOtherDutyContent(String otherDutyContent) {
        this.otherDutyContent = otherDutyContent == null ? null : otherDutyContent.trim();
    }

    public Date getOtherDutyDate() {
        return otherDutyDate;
    }

    public void setOtherDutyDate(Date otherDutyDate) {
        this.otherDutyDate = otherDutyDate;
    }

    public String getOtherDutyUser() {
        return otherDutyUser == null ? "" : otherDutyUser.trim();
    }

    public void setOtherDutyUser(String otherDutyUser) {
        this.otherDutyUser = otherDutyUser == null ? null : otherDutyUser.trim();
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

