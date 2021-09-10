package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjTzSafetyQualityCheckBulletin extends BasePojo {
    private String checkBulletinId;

    private String projectId;

    private String projectName;

    private String subprojectsId;

    private String subprojectsName;

    private String checkBulletinTitle;

    private String checkBulletinContent;

    private String checkUnit;

    private String checkRectificationImplementation;

    private Date checkDate;

    private Date checkBulletinDate;

    private String checkBulletinUser;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZjTzFile> checkBulletinFileList;
    
    private String projectIdSql;
    
    public String getProjectIdSql() {
        return projectIdSql == null ? "" : projectIdSql.trim();
    }

    public void setProjectIdSql(String projectIdSql) {
        this.projectIdSql = projectIdSql == null ? null : projectIdSql.trim();
    }
    
    public List<ZjTzFile> getCheckBulletinFileList() {
		return checkBulletinFileList;
	}

	public void setCheckBulletinFileList(List<ZjTzFile> checkBulletinFileList) {
		this.checkBulletinFileList = checkBulletinFileList;
	}

	public String getCheckBulletinId() {
        return checkBulletinId == null ? "" : checkBulletinId.trim();
    }

    public void setCheckBulletinId(String checkBulletinId) {
        this.checkBulletinId = checkBulletinId == null ? null : checkBulletinId.trim();
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

    public String getCheckBulletinTitle() {
        return checkBulletinTitle == null ? "" : checkBulletinTitle.trim();
    }

    public void setCheckBulletinTitle(String checkBulletinTitle) {
        this.checkBulletinTitle = checkBulletinTitle == null ? null : checkBulletinTitle.trim();
    }

    public String getCheckBulletinContent() {
        return checkBulletinContent == null ? "" : checkBulletinContent.trim();
    }

    public void setCheckBulletinContent(String checkBulletinContent) {
        this.checkBulletinContent = checkBulletinContent == null ? null : checkBulletinContent.trim();
    }

    public String getCheckUnit() {
        return checkUnit == null ? "" : checkUnit.trim();
    }

    public void setCheckUnit(String checkUnit) {
        this.checkUnit = checkUnit == null ? null : checkUnit.trim();
    }

    public String getCheckRectificationImplementation() {
        return checkRectificationImplementation == null ? "" : checkRectificationImplementation.trim();
    }

    public void setCheckRectificationImplementation(String checkRectificationImplementation) {
        this.checkRectificationImplementation = checkRectificationImplementation == null ? null : checkRectificationImplementation.trim();
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Date getCheckBulletinDate() {
        return checkBulletinDate;
    }

    public void setCheckBulletinDate(Date checkBulletinDate) {
        this.checkBulletinDate = checkBulletinDate;
    }

    public String getCheckBulletinUser() {
        return checkBulletinUser == null ? "" : checkBulletinUser.trim();
    }

    public void setCheckBulletinUser(String checkBulletinUser) {
        this.checkBulletinUser = checkBulletinUser == null ? null : checkBulletinUser.trim();
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

