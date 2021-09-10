package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjTzSafetyEmergencyPlan extends BasePojo {
    private String emergencyPlanId;

    private String projectId;

    private String projectName;

    private String subprojectsId;

    private String subprojectsName;

    private String emergencyPlanTitle;

    private String emergencyPlanContent;

    private Date emergencyPlanDate;

    private String emergencyPlanUser;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private List<ZjTzFile> emergencyPlanFileList;
    
    private String projectIdSql;
    
    public String getProjectIdSql() {
        return projectIdSql == null ? "" : projectIdSql.trim();
    }

    public void setProjectIdSql(String projectIdSql) {
        this.projectIdSql = projectIdSql == null ? null : projectIdSql.trim();
    }
    
	public List<ZjTzFile> getEmergencyPlanFileList() {
		return emergencyPlanFileList;
	}

	public void setEmergencyPlanFileList(List<ZjTzFile> emergencyPlanFileList) {
		this.emergencyPlanFileList = emergencyPlanFileList;
	}

	public String getEmergencyPlanId() {
        return emergencyPlanId == null ? "" : emergencyPlanId.trim();
    }

    public void setEmergencyPlanId(String emergencyPlanId) {
        this.emergencyPlanId = emergencyPlanId == null ? null : emergencyPlanId.trim();
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

    public String getEmergencyPlanTitle() {
        return emergencyPlanTitle == null ? "" : emergencyPlanTitle.trim();
    }

    public void setEmergencyPlanTitle(String emergencyPlanTitle) {
        this.emergencyPlanTitle = emergencyPlanTitle == null ? null : emergencyPlanTitle.trim();
    }

    public String getEmergencyPlanContent() {
        return emergencyPlanContent == null ? "" : emergencyPlanContent.trim();
    }

    public void setEmergencyPlanContent(String emergencyPlanContent) {
        this.emergencyPlanContent = emergencyPlanContent == null ? null : emergencyPlanContent.trim();
    }

    public Date getEmergencyPlanDate() {
        return emergencyPlanDate;
    }

    public void setEmergencyPlanDate(Date emergencyPlanDate) {
        this.emergencyPlanDate = emergencyPlanDate;
    }

    public String getEmergencyPlanUser() {
        return emergencyPlanUser == null ? "" : emergencyPlanUser.trim();
    }

    public void setEmergencyPlanUser(String emergencyPlanUser) {
        this.emergencyPlanUser = emergencyPlanUser == null ? null : emergencyPlanUser.trim();
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

