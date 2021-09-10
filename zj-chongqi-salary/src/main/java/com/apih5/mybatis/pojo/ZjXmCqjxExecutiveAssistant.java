package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;

import com.apih5.framework.api.zjoa.entity.OADepartmentMember;
import com.apih5.framework.entity.BasePojo;

public class ZjXmCqjxExecutiveAssistant extends BasePojo {
    private String executiveId;

    private String managerId;
    
    private String assistantLeaderApprovalId;
    
    private String assessmentType;

    private Date assessmentYears;

    private String assessmentTitle;

    private String assessmentQuarter;

    private String assessmentState;

    private String position;

    private String chargeLeaderId;

    private String chargeLeaderOption;

    private String executiveLeaderId;

    private String executiveLeaderOption;
    
    private String departmentId;

    private double quarterScore;
    
    private double taskScore;
    
    private double cooperationScore;
    
    private double quarterAverageScore;
    
    private double comprehensiveScore;
    
    private double yearFinalScore;
    
    private double disciplineScore;

    private String assistantLock;//0：正常；1：锁定；2：解锁
    
    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private String identityFlag;//未使用该字段
    
    private String years;//日期检索条件使用
    
    private String approvalFlag;//0：同意；1：退回
    
    private String leaderSee;//0：不可见；1：可见；
    
    private OADepartmentMember leaderList;//部门负责人
    
    private String taskFlag;
    
    private String cooperationFlag;
    
    private String disciplineFlag;
    
    private String departmentName;
    
    private String mobile;
    
    private String deptCoefficient;
    
    private String state;
    
    private Date dutyClosingEndDate;
    
    private Date scoreClosingEndDate;
    
    private Date firstDutyClosingEndDate;
    
    private Date firstScoreClosingEndDate;
    
    private Date finalDutyClosingEndDate;
    
    private Date finalScoreClosingEndDate;
    
    private String haveChangerLeader;//0：有分管领导；1：没有
    
    private String leaderFlag;
    
    private String deptHeadOption;
    
    private String leaderOption;
    
    private String assistantFlag;

	public String getAssistantFlag() {
		return assistantFlag;
	}

	public void setAssistantFlag(String assistantFlag) {
		this.assistantFlag = assistantFlag;
	}

	public double getQuarterAverageScore() {
		return quarterAverageScore;
	}

	public void setQuarterAverageScore(double quarterAverageScore) {
		this.quarterAverageScore = quarterAverageScore;
	}

	public double getComprehensiveScore() {
		return comprehensiveScore;
	}

	public void setComprehensiveScore(double comprehensiveScore) {
		this.comprehensiveScore = comprehensiveScore;
	}

	public double getYearFinalScore() {
		return yearFinalScore;
	}

	public void setYearFinalScore(double yearFinalScore) {
		this.yearFinalScore = yearFinalScore;
	}

	public String getLeaderOption() {
		return leaderOption;
	}

	public void setLeaderOption(String leaderOption) {
		this.leaderOption = leaderOption;
	}

	public String getLeaderFlag() {
		return leaderFlag;
	}

	public void setLeaderFlag(String leaderFlag) {
		this.leaderFlag = leaderFlag;
	}

	public String getDeptHeadOption() {
		return deptHeadOption;
	}

	public void setDeptHeadOption(String deptHeadOption) {
		this.deptHeadOption = deptHeadOption;
	}

	private List<ZjXmCqjxExecutiveAssistant> assistantList;

	public String getHaveChangerLeader() {
		return haveChangerLeader;
	}

	public void setHaveChangerLeader(String haveChangerLeader) {
		this.haveChangerLeader = haveChangerLeader;
	}

	public List<ZjXmCqjxExecutiveAssistant> getAssistantList() {
		return assistantList;
	}

	public void setAssistantList(List<ZjXmCqjxExecutiveAssistant> assistantList) {
		this.assistantList = assistantList;
	}

	public String getAssistantLeaderApprovalId() {
		return assistantLeaderApprovalId;
	}

	public void setAssistantLeaderApprovalId(String assistantLeaderApprovalId) {
		this.assistantLeaderApprovalId = assistantLeaderApprovalId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getLeaderSee() {
		return leaderSee;
	}

	public void setLeaderSee(String leaderSee) {
		this.leaderSee = leaderSee;
	}

	public Date getFirstDutyClosingEndDate() {
		return firstDutyClosingEndDate;
	}

	public void setFirstDutyClosingEndDate(Date firstDutyClosingEndDate) {
		this.firstDutyClosingEndDate = firstDutyClosingEndDate;
	}

	public Date getFirstScoreClosingEndDate() {
		return firstScoreClosingEndDate;
	}

	public void setFirstScoreClosingEndDate(Date firstScoreClosingEndDate) {
		this.firstScoreClosingEndDate = firstScoreClosingEndDate;
	}

	public Date getFinalDutyClosingEndDate() {
		return finalDutyClosingEndDate;
	}

	public void setFinalDutyClosingEndDate(Date finalDutyClosingEndDate) {
		this.finalDutyClosingEndDate = finalDutyClosingEndDate;
	}

	public Date getFinalScoreClosingEndDate() {
		return finalScoreClosingEndDate;
	}

	public void setFinalScoreClosingEndDate(Date finalScoreClosingEndDate) {
		this.finalScoreClosingEndDate = finalScoreClosingEndDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getDutyClosingEndDate() {
		return dutyClosingEndDate;
	}

	public void setDutyClosingEndDate(Date dutyClosingEndDate) {
		this.dutyClosingEndDate = dutyClosingEndDate;
	}

	public Date getScoreClosingEndDate() {
		return scoreClosingEndDate;
	}

	public void setScoreClosingEndDate(Date scoreClosingEndDate) {
		this.scoreClosingEndDate = scoreClosingEndDate;
	}

	public String getAssistantLock() {
		return assistantLock;
	}

	public void setAssistantLock(String assistantLock) {
		this.assistantLock = assistantLock;
	}

	public String getDeptCoefficient() {
		return deptCoefficient;
	}

	public void setDeptCoefficient(String deptCoefficient) {
		this.deptCoefficient = deptCoefficient;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getTaskFlag() {
		return taskFlag;
	}

	public void setTaskFlag(String taskFlag) {
		this.taskFlag = taskFlag;
	}

	public String getCooperationFlag() {
		return cooperationFlag;
	}

	public void setCooperationFlag(String cooperationFlag) {
		this.cooperationFlag = cooperationFlag;
	}

	public String getDisciplineFlag() {
		return disciplineFlag;
	}

	public void setDisciplineFlag(String disciplineFlag) {
		this.disciplineFlag = disciplineFlag;
	}

	public double getQuarterScore() {
		return quarterScore;
	}

	public void setQuarterScore(double quarterScore) {
		this.quarterScore = quarterScore;
	}

	public double getTaskScore() {
		return taskScore;
	}

	public void setTaskScore(double taskScore) {
		this.taskScore = taskScore;
	}

	public double getCooperationScore() {
		return cooperationScore;
	}

	public void setCooperationScore(double cooperationScore) {
		this.cooperationScore = cooperationScore;
	}

	public double getDisciplineScore() {
		return disciplineScore;
	}

	public void setDisciplineScore(double disciplineScore) {
		this.disciplineScore = disciplineScore;
	}

	public String getApprovalFlag() {
		return approvalFlag;
	}

	public void setApprovalFlag(String approvalFlag) {
		this.approvalFlag = approvalFlag;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public String getIdentityFlag() {
		return identityFlag;
	}

	public void setIdentityFlag(String identityFlag) {
		this.identityFlag = identityFlag;
	}

	public String getAssessmentType() {
		return assessmentType;
	}

	public void setAssessmentType(String assessmentType) {
		this.assessmentType = assessmentType;
	}

	public OADepartmentMember getLeaderList() {
		return leaderList;
	}

	public void setLeaderList(OADepartmentMember leaderList) {
		this.leaderList = leaderList;
	}

	public String getExecutiveId() {
        return executiveId == null ? "" : executiveId.trim();
    }

    public void setExecutiveId(String executiveId) {
        this.executiveId = executiveId == null ? null : executiveId.trim();
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

    public String getAssessmentState() {
        return assessmentState == null ? "" : assessmentState.trim();
    }

    public void setAssessmentState(String assessmentState) {
        this.assessmentState = assessmentState == null ? null : assessmentState.trim();
    }

    public String getPosition() {
        return position == null ? "" : position.trim();
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getChargeLeaderId() {
        return chargeLeaderId == null ? "" : chargeLeaderId.trim();
    }

    public void setChargeLeaderId(String chargeLeaderId) {
        this.chargeLeaderId = chargeLeaderId == null ? null : chargeLeaderId.trim();
    }

    public String getChargeLeaderOption() {
        return chargeLeaderOption == null ? "" : chargeLeaderOption.trim();
    }

    public void setChargeLeaderOption(String chargeLeaderOption) {
        this.chargeLeaderOption = chargeLeaderOption == null ? null : chargeLeaderOption.trim();
    }

    public String getExecutiveLeaderId() {
        return executiveLeaderId == null ? "" : executiveLeaderId.trim();
    }

    public void setExecutiveLeaderId(String executiveLeaderId) {
        this.executiveLeaderId = executiveLeaderId == null ? null : executiveLeaderId.trim();
    }

    public String getExecutiveLeaderOption() {
        return executiveLeaderOption == null ? "" : executiveLeaderOption.trim();
    }

    public void setExecutiveLeaderOption(String executiveLeaderOption) {
        this.executiveLeaderOption = executiveLeaderOption == null ? null : executiveLeaderOption.trim();
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

