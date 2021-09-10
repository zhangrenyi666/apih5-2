package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjXmCqjxCollaborationAssessmentDetailed extends BasePojo {
    private String collaborationDetailedId;

    private String collaborationtId;

    private String memberId;

    private String userKey;

    private String userName;

    private String departmentId;

    private String departmentName;

    private String mobile;

    private String collaborationDetailedScore;

    private String collaborationAnonymous;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private String effectiveFlag;
    
    private double score;

    public String getEffectiveFlag() {
		return effectiveFlag;
	}

	public void setEffectiveFlag(String effectiveFlag) {
		this.effectiveFlag = effectiveFlag;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getCollaborationDetailedId() {
        return collaborationDetailedId == null ? "" : collaborationDetailedId.trim();
    }

    public void setCollaborationDetailedId(String collaborationDetailedId) {
        this.collaborationDetailedId = collaborationDetailedId == null ? null : collaborationDetailedId.trim();
    }

    public String getCollaborationtId() {
        return collaborationtId == null ? "" : collaborationtId.trim();
    }

    public void setCollaborationtId(String collaborationtId) {
        this.collaborationtId = collaborationtId == null ? null : collaborationtId.trim();
    }

    public String getMemberId() {
        return memberId == null ? "" : memberId.trim();
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    public String getUserKey() {
        return userKey == null ? "" : userKey.trim();
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey == null ? null : userKey.trim();
    }

    public String getUserName() {
        return userName == null ? "" : userName.trim();
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getDepartmentId() {
        return departmentId == null ? "" : departmentId.trim();
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }

    public String getDepartmentName() {
        return departmentName == null ? "" : departmentName.trim();
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public String getMobile() {
        return mobile == null ? "" : mobile.trim();
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getCollaborationDetailedScore() {
        return collaborationDetailedScore == null ? "" : collaborationDetailedScore.trim();
    }

    public void setCollaborationDetailedScore(String collaborationDetailedScore) {
        this.collaborationDetailedScore = collaborationDetailedScore == null ? null : collaborationDetailedScore.trim();
    }

    public String getCollaborationAnonymous() {
        return collaborationAnonymous == null ? "" : collaborationAnonymous.trim();
    }

    public void setCollaborationAnonymous(String collaborationAnonymous) {
        this.collaborationAnonymous = collaborationAnonymous == null ? null : collaborationAnonymous.trim();
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

