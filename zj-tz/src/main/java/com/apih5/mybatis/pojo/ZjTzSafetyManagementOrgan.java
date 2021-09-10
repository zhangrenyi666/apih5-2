package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjTzSafetyManagementOrgan extends BasePojo {
    private String safetyOrganId;

    private String projectId;

    private String projectName;

    private String subprojectsId;

    private String subprojectsName;

    private String safetyOrganTitle;

    private String safetyOrganContent;

    private Date safetyOrganDate;

    private String safetyOrganUser;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private List<ZjTzFile> safetyOrganFileList;
    
    private String projectIdSql;
    
    public String getProjectIdSql() {
        return projectIdSql == null ? "" : projectIdSql.trim();
    }

    public void setProjectIdSql(String projectIdSql) {
        this.projectIdSql = projectIdSql == null ? null : projectIdSql.trim();
    }
    
    public List<ZjTzFile> getSafetyOrganFileList() {
		return safetyOrganFileList;
	}

	public void setSafetyOrganFileList(List<ZjTzFile> safetyOrganFileList) {
		this.safetyOrganFileList = safetyOrganFileList;
	}

	public String getSafetyOrganId() {
        return safetyOrganId == null ? "" : safetyOrganId.trim();
    }

    public void setSafetyOrganId(String safetyOrganId) {
        this.safetyOrganId = safetyOrganId == null ? null : safetyOrganId.trim();
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

    public String getSafetyOrganTitle() {
        return safetyOrganTitle == null ? "" : safetyOrganTitle.trim();
    }

    public void setSafetyOrganTitle(String safetyOrganTitle) {
        this.safetyOrganTitle = safetyOrganTitle == null ? null : safetyOrganTitle.trim();
    }

    public String getSafetyOrganContent() {
        return safetyOrganContent == null ? "" : safetyOrganContent.trim();
    }

    public void setSafetyOrganContent(String safetyOrganContent) {
        this.safetyOrganContent = safetyOrganContent == null ? null : safetyOrganContent.trim();
    }

    public Date getSafetyOrganDate() {
        return safetyOrganDate;
    }

    public void setSafetyOrganDate(Date safetyOrganDate) {
        this.safetyOrganDate = safetyOrganDate;
    }

    public String getSafetyOrganUser() {
        return safetyOrganUser == null ? "" : safetyOrganUser.trim();
    }

    public void setSafetyOrganUser(String safetyOrganUser) {
        this.safetyOrganUser = safetyOrganUser == null ? null : safetyOrganUser.trim();
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

