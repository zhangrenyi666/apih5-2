package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjTzQualityManagementSystem extends BasePojo {
    private String qualityId;

    private String projectId;

    private String projectName;

    private String subprojectsId;

    private String subprojectsName;

    private String qualityTitle;

    private String qualityContent;

    private Date qualityRegisterDate;

    private String qualityRegisterUser;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private String projectIdSql;
    
    private List<ZjTzFile> qualityFileList;
    
    public String getProjectIdSql() {
        return projectIdSql == null ? "" : projectIdSql.trim();
    }

    public void setProjectIdSql(String projectIdSql) {
        this.projectIdSql = projectIdSql == null ? null : projectIdSql.trim();
    }

	public List<ZjTzFile> getQualityFileList() {
		return qualityFileList;
	}

	public void setQualityFileList(List<ZjTzFile> qualityFileList) {
		this.qualityFileList = qualityFileList;
	}

	public String getQualityId() {
        return qualityId == null ? "" : qualityId.trim();
    }

    public void setQualityId(String qualityId) {
        this.qualityId = qualityId == null ? null : qualityId.trim();
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

    public String getQualityTitle() {
        return qualityTitle == null ? "" : qualityTitle.trim();
    }

    public void setQualityTitle(String qualityTitle) {
        this.qualityTitle = qualityTitle == null ? null : qualityTitle.trim();
    }

    public String getQualityContent() {
        return qualityContent == null ? "" : qualityContent.trim();
    }

    public void setQualityContent(String qualityContent) {
        this.qualityContent = qualityContent == null ? null : qualityContent.trim();
    }

    public Date getQualityRegisterDate() {
        return qualityRegisterDate;
    }

    public void setQualityRegisterDate(Date qualityRegisterDate) {
        this.qualityRegisterDate = qualityRegisterDate;
    }

    public String getQualityRegisterUser() {
        return qualityRegisterUser == null ? "" : qualityRegisterUser.trim();
    }

    public void setQualityRegisterUser(String qualityRegisterUser) {
        this.qualityRegisterUser = qualityRegisterUser == null ? null : qualityRegisterUser.trim();
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

