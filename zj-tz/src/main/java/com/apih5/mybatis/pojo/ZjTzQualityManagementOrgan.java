package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;

public class ZjTzQualityManagementOrgan extends BasePojo {
    private String qualityOrganId;

    private String projectId;

    private String projectName;

    private String subprojectsId;

    private String subprojectsName;

    private String qualityOrganTitle;

    private String qualityOrganContent;

    private Date qualityOrganDate;

    private String qualityOrganUser;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private List<ZjTzFile> qualityOrganFileList;
    
    private String projectIdSql;
    
    public String getProjectIdSql() {
        return projectIdSql == null ? "" : projectIdSql.trim();
    }

    public void setProjectIdSql(String projectIdSql) {
        this.projectIdSql = projectIdSql == null ? null : projectIdSql.trim();
    }
    
    public List<ZjTzFile> getQualityOrganFileList() {
		return qualityOrganFileList;
	}

	public void setQualityOrganFileList(List<ZjTzFile> qualityOrganFileList) {
		this.qualityOrganFileList = qualityOrganFileList;
	}

	public String getQualityOrganId() {
        return qualityOrganId == null ? "" : qualityOrganId.trim();
    }

    public void setQualityOrganId(String qualityOrganId) {
        this.qualityOrganId = qualityOrganId == null ? null : qualityOrganId.trim();
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

    public String getQualityOrganTitle() {
        return qualityOrganTitle == null ? "" : qualityOrganTitle.trim();
    }

    public void setQualityOrganTitle(String qualityOrganTitle) {
        this.qualityOrganTitle = qualityOrganTitle == null ? null : qualityOrganTitle.trim();
    }

    public String getQualityOrganContent() {
        return qualityOrganContent == null ? "" : qualityOrganContent.trim();
    }

    public void setQualityOrganContent(String qualityOrganContent) {
        this.qualityOrganContent = qualityOrganContent == null ? null : qualityOrganContent.trim();
    }

    public Date getQualityOrganDate() {
        return qualityOrganDate;
    }

    public void setQualityOrganDate(Date qualityOrganDate) {
        this.qualityOrganDate = qualityOrganDate;
    }

    public String getQualityOrganUser() {
        return qualityOrganUser == null ? "" : qualityOrganUser.trim();
    }

    public void setQualityOrganUser(String qualityOrganUser) {
        this.qualityOrganUser = qualityOrganUser == null ? null : qualityOrganUser.trim();
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

