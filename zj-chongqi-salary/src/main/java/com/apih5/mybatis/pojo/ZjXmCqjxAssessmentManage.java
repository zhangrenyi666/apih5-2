package com.apih5.mybatis.pojo;

import java.util.Date;

import com.apih5.framework.api.zjoa.entity.OADepartmentMember;
import com.apih5.framework.entity.BasePojo;

public class ZjXmCqjxAssessmentManage extends BasePojo {
    private String managerId;

    private Date assessmentYears;

    private String assessmentDept;
    
    private String assessmentType;

    private String assessmentDeptName;

    private String assessmentDeptOrgId;

    private String assessmentTitle;

    private String assessmentQuarter;

    private String assessmentRemarks;

    private String delFlag;

    private Date createTime;
    
    private Date dutyClosingStartDate;
    
    private Date dutyClosingEndDate;
    
    private Date firstDutyDlosingStartDate;
    
    private Date firstDutyClosingEndDate;
    
    private Date finalDutyClosingStartDate;
    
    private Date finalDutyClosingEndDate;
    
    private Date scoreClosingStartDate;
    
    private Date scoreClosingEndDate;
    
    private Date firstScoreClosingStartDate;
    
    private Date firstScoreClosingEndDate;
    
    private Date finalScoreClosingStartDate;
    
    private Date finalScoreClosingEndDate;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private OADepartmentMember assessmentDeptList;//考核部门
    
    private OADepartmentMember assessmentMemberList;//考核部门
    
    private String oaUserId;
    
    private String oaUserName;
    
    private String oaOrgId;
    
    private String state;//0:未发送 1：已发送
    
    private String deptName;//考核详情使用的部门名称
    
    private String mobile;//考核详情使用的手机号
    
    private String deptId;//考核详情使用的手机号

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public Date getDutyClosingStartDate() {
		return dutyClosingStartDate;
	}

	public void setDutyClosingStartDate(Date dutyClosingStartDate) {
		this.dutyClosingStartDate = dutyClosingStartDate;
	}

	public Date getDutyClosingEndDate() {
		return dutyClosingEndDate;
	}

	public void setDutyClosingEndDate(Date dutyClosingEndDate) {
		this.dutyClosingEndDate = dutyClosingEndDate;
	}

	public Date getFirstDutyDlosingStartDate() {
		return firstDutyDlosingStartDate;
	}

	public void setFirstDutyDlosingStartDate(Date firstDutyDlosingStartDate) {
		this.firstDutyDlosingStartDate = firstDutyDlosingStartDate;
	}

	public Date getFirstDutyClosingEndDate() {
		return firstDutyClosingEndDate;
	}

	public void setFirstDutyClosingEndDate(Date firstDutyClosingEndDate) {
		this.firstDutyClosingEndDate = firstDutyClosingEndDate;
	}

	public Date getFinalDutyClosingStartDate() {
		return finalDutyClosingStartDate;
	}

	public void setFinalDutyClosingStartDate(Date finalDutyClosingStartDate) {
		this.finalDutyClosingStartDate = finalDutyClosingStartDate;
	}

	public Date getFinalDutyClosingEndDate() {
		return finalDutyClosingEndDate;
	}

	public void setFinalDutyClosingEndDate(Date finalDutyClosingEndDate) {
		this.finalDutyClosingEndDate = finalDutyClosingEndDate;
	}

	public Date getScoreClosingStartDate() {
		return scoreClosingStartDate;
	}

	public void setScoreClosingStartDate(Date scoreClosingStartDate) {
		this.scoreClosingStartDate = scoreClosingStartDate;
	}

	public Date getScoreClosingEndDate() {
		return scoreClosingEndDate;
	}

	public void setScoreClosingEndDate(Date scoreClosingEndDate) {
		this.scoreClosingEndDate = scoreClosingEndDate;
	}

	public Date getFirstScoreClosingStartDate() {
		return firstScoreClosingStartDate;
	}

	public void setFirstScoreClosingStartDate(Date firstScoreClosingStartDate) {
		this.firstScoreClosingStartDate = firstScoreClosingStartDate;
	}

	public Date getFirstScoreClosingEndDate() {
		return firstScoreClosingEndDate;
	}

	public void setFirstScoreClosingEndDate(Date firstScoreClosingEndDate) {
		this.firstScoreClosingEndDate = firstScoreClosingEndDate;
	}

	public Date getFinalScoreClosingStartDate() {
		return finalScoreClosingStartDate;
	}

	public void setFinalScoreClosingStartDate(Date finalScoreClosingStartDate) {
		this.finalScoreClosingStartDate = finalScoreClosingStartDate;
	}

	public Date getFinalScoreClosingEndDate() {
		return finalScoreClosingEndDate;
	}

	public void setFinalScoreClosingEndDate(Date finalScoreClosingEndDate) {
		this.finalScoreClosingEndDate = finalScoreClosingEndDate;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAssessmentType() {
		return assessmentType;
	}

	public void setAssessmentType(String assessmentType) {
		this.assessmentType = assessmentType;
	}

	public OADepartmentMember getAssessmentMemberList() {
		return assessmentMemberList;
	}

	public void setAssessmentMemberList(OADepartmentMember assessmentMemberList) {
		this.assessmentMemberList = assessmentMemberList;
	}

	public String getOaUserId() {
		return oaUserId;
	}

	public void setOaUserId(String oaUserId) {
		this.oaUserId = oaUserId;
	}

	public String getOaUserName() {
		return oaUserName;
	}

	public void setOaUserName(String oaUserName) {
		this.oaUserName = oaUserName;
	}

	public String getOaOrgId() {
		return oaOrgId;
	}

	public void setOaOrgId(String oaOrgId) {
		this.oaOrgId = oaOrgId;
	}

	public OADepartmentMember getAssessmentDeptList() {
		return assessmentDeptList;
	}

	public void setAssessmentDeptList(OADepartmentMember assessmentDeptList) {
		this.assessmentDeptList = assessmentDeptList;
	}

	public String getManagerId() {
        return managerId == null ? "" : managerId.trim();
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId == null ? null : managerId.trim();
    }

    public Date getAssessmentYears() {
        return assessmentYears;
    }

    public void setAssessmentYears(Date assessmentYears) {
        this.assessmentYears = assessmentYears;
    }

    public String getAssessmentDept() {
        return assessmentDept == null ? "" : assessmentDept.trim();
    }

    public void setAssessmentDept(String assessmentDept) {
        this.assessmentDept = assessmentDept == null ? null : assessmentDept.trim();
    }

    public String getAssessmentDeptName() {
        return assessmentDeptName == null ? "" : assessmentDeptName.trim();
    }

    public void setAssessmentDeptName(String assessmentDeptName) {
        this.assessmentDeptName = assessmentDeptName == null ? null : assessmentDeptName.trim();
    }

    public String getAssessmentDeptOrgId() {
        return assessmentDeptOrgId == null ? "" : assessmentDeptOrgId.trim();
    }

    public void setAssessmentDeptOrgId(String assessmentDeptOrgId) {
        this.assessmentDeptOrgId = assessmentDeptOrgId == null ? null : assessmentDeptOrgId.trim();
    }

    public String getAssessmentTitle() {
        return assessmentTitle == null ? "" : assessmentTitle.trim();
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle == null ? null : assessmentTitle.trim();
    }

    public String getAssessmentQuarter() {
        return assessmentQuarter == null ? "" : assessmentQuarter.trim();
    }

    public void setAssessmentQuarter(String assessmentQuarter) {
        this.assessmentQuarter = assessmentQuarter == null ? null : assessmentQuarter.trim();
    }

    public String getAssessmentRemarks() {
        return assessmentRemarks == null ? "" : assessmentRemarks.trim();
    }

    public void setAssessmentRemarks(String assessmentRemarks) {
        this.assessmentRemarks = assessmentRemarks == null ? null : assessmentRemarks.trim();
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

