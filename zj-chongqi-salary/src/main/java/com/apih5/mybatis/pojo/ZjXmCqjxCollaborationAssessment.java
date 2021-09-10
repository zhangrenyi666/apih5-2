package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.api.zjoa.entity.OADepartmentMember;
import com.apih5.framework.entity.BasePojo;

public class ZjXmCqjxCollaborationAssessment extends BasePojo {
    private String collaborationId;

    private String managerId;
    
    private String assessmentType;

    private String collaborationState;

    private String collaborationTitle;

    private Date collaborationYears;
    
    private Date closingDate;

    private String collaborationQuarter;

    private String collaborationRemarks;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private OADepartmentMember assessmentRange;//考核范围
    
    private OADepartmentMember assessmentPersonnel;//考核范围人员
    
	public OADepartmentMember getAssessmentPersonnel() {
		return assessmentPersonnel;
	}

	public void setAssessmentPersonnel(OADepartmentMember assessmentPersonnel) {
		this.assessmentPersonnel = assessmentPersonnel;
	}

	public Date getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}

	public OADepartmentMember getAssessmentRange() {
		return assessmentRange;
	}

	public void setAssessmentRange(OADepartmentMember assessmentRange) {
		this.assessmentRange = assessmentRange;
	}

	public String getAssessmentType() {
		return assessmentType;
	}

	public void setAssessmentType(String assessmentType) {
		this.assessmentType = assessmentType;
	}

	public String getCollaborationId() {
        return collaborationId == null ? "" : collaborationId.trim();
    }

    public void setCollaborationId(String collaborationId) {
        this.collaborationId = collaborationId == null ? null : collaborationId.trim();
    }

    public String getManagerId() {
        return managerId == null ? "" : managerId.trim();
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId == null ? null : managerId.trim();
    }

    public String getCollaborationState() {
        return collaborationState == null ? "" : collaborationState.trim();
    }

    public void setCollaborationState(String collaborationState) {
        this.collaborationState = collaborationState == null ? null : collaborationState.trim();
    }

    public String getCollaborationTitle() {
        return collaborationTitle == null ? "" : collaborationTitle.trim();
    }

    public void setCollaborationTitle(String collaborationTitle) {
        this.collaborationTitle = collaborationTitle == null ? null : collaborationTitle.trim();
    }

    public Date getCollaborationYears() {
        return collaborationYears;
    }

    public void setCollaborationYears(Date collaborationYears) {
        this.collaborationYears = collaborationYears;
    }

    public String getCollaborationQuarter() {
        return collaborationQuarter == null ? "" : collaborationQuarter.trim();
    }

    public void setCollaborationQuarter(String collaborationQuarter) {
        this.collaborationQuarter = collaborationQuarter == null ? null : collaborationQuarter.trim();
    }

    public String getCollaborationRemarks() {
        return collaborationRemarks == null ? "" : collaborationRemarks.trim();
    }

    public void setCollaborationRemarks(String collaborationRemarks) {
        this.collaborationRemarks = collaborationRemarks == null ? null : collaborationRemarks.trim();
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

