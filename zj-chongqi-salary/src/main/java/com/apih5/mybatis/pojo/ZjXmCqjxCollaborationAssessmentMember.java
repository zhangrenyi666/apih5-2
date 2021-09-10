package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjXmCqjxCollaborationAssessmentMember extends BasePojo {
    private String zcOaId;

    private String otherId;
    
    private String managerId;

    private String otherType;

    private String oaDepartmentMemberFlag;

    private String oaUserId;

    private String oaUserName;

    private String oaOrgId;

    private String oaOrgName;

    private String mobile;

    private String assessmentFlag;
    
    private String assessmentLock;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private double assessmentScore;
    
    private String option;
    
    private String collaborationTitle;

    private Date collaborationYears;

    private String collaborationQuarter;

    private String collaborationRemarks;
    
    private String collaborationId;
    
    private String assessmentType;
    
    private String effectiveFlag;
    
    private String effectiveNum;
    
    private String invalidNum;
    
    private Date closingDate;
    
    private int index;

	public String getAssessmentLock() {
		return assessmentLock;
	}

	public void setAssessmentLock(String assessmentLock) {
		this.assessmentLock = assessmentLock;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getEffectiveNum() {
		return effectiveNum;
	}

	public void setEffectiveNum(String effectiveNum) {
		this.effectiveNum = effectiveNum;
	}

	public String getInvalidNum() {
		return invalidNum;
	}

	public void setInvalidNum(String invalidNum) {
		this.invalidNum = invalidNum;
	}

	public Date getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}

	public String getEffectiveFlag() {
		return effectiveFlag;
	}

	public void setEffectiveFlag(String effectiveFlag) {
		this.effectiveFlag = effectiveFlag;
	}

	public String getAssessmentType() {
		return assessmentType;
	}

	public void setAssessmentType(String assessmentType) {
		this.assessmentType = assessmentType;
	}

	public String getCollaborationId() {
		return collaborationId;
	}

	public void setCollaborationId(String collaborationId) {
		this.collaborationId = collaborationId;
	}

	public String getCollaborationTitle() {
		return collaborationTitle;
	}

	public void setCollaborationTitle(String collaborationTitle) {
		this.collaborationTitle = collaborationTitle;
	}

	public Date getCollaborationYears() {
		return collaborationYears;
	}

	public void setCollaborationYears(Date collaborationYears) {
		this.collaborationYears = collaborationYears;
	}

	public String getCollaborationQuarter() {
		return collaborationQuarter;
	}

	public void setCollaborationQuarter(String collaborationQuarter) {
		this.collaborationQuarter = collaborationQuarter;
	}

	public String getCollaborationRemarks() {
		return collaborationRemarks;
	}

	public void setCollaborationRemarks(String collaborationRemarks) {
		this.collaborationRemarks = collaborationRemarks;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public double getAssessmentScore() {
		return assessmentScore;
	}

	public void setAssessmentScore(double assessmentScore) {
		this.assessmentScore = assessmentScore;
	}

	public String getZcOaId() {
        return zcOaId == null ? "" : zcOaId.trim();
    }

    public void setZcOaId(String zcOaId) {
        this.zcOaId = zcOaId == null ? null : zcOaId.trim();
    }

    public String getOtherId() {
        return otherId == null ? "" : otherId.trim();
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId == null ? null : otherId.trim();
    }

    public String getOtherType() {
        return otherType == null ? "" : otherType.trim();
    }

    public void setOtherType(String otherType) {
        this.otherType = otherType == null ? null : otherType.trim();
    }

    public String getOaDepartmentMemberFlag() {
        return oaDepartmentMemberFlag == null ? "" : oaDepartmentMemberFlag.trim();
    }

    public void setOaDepartmentMemberFlag(String oaDepartmentMemberFlag) {
        this.oaDepartmentMemberFlag = oaDepartmentMemberFlag == null ? null : oaDepartmentMemberFlag.trim();
    }

    public String getOaUserId() {
        return oaUserId == null ? "" : oaUserId.trim();
    }

    public void setOaUserId(String oaUserId) {
        this.oaUserId = oaUserId == null ? null : oaUserId.trim();
    }

    public String getOaUserName() {
        return oaUserName == null ? "" : oaUserName.trim();
    }

    public void setOaUserName(String oaUserName) {
        this.oaUserName = oaUserName == null ? null : oaUserName.trim();
    }

    public String getOaOrgId() {
        return oaOrgId == null ? "" : oaOrgId.trim();
    }

    public void setOaOrgId(String oaOrgId) {
        this.oaOrgId = oaOrgId == null ? null : oaOrgId.trim();
    }

    public String getOaOrgName() {
        return oaOrgName == null ? "" : oaOrgName.trim();
    }

    public void setOaOrgName(String oaOrgName) {
        this.oaOrgName = oaOrgName == null ? null : oaOrgName.trim();
    }

    public String getMobile() {
        return mobile == null ? "" : mobile.trim();
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getAssessmentFlag() {
        return assessmentFlag == null ? "" : assessmentFlag.trim();
    }

    public void setAssessmentFlag(String assessmentFlag) {
        this.assessmentFlag = assessmentFlag == null ? null : assessmentFlag.trim();
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

