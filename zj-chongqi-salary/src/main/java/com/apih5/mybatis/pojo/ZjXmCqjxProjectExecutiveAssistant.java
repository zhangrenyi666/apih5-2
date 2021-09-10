package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;

import com.apih5.framework.api.zjoa.entity.OADepartmentMember;
import com.apih5.framework.entity.BasePojo;

public class ZjXmCqjxProjectExecutiveAssistant extends BasePojo {
    private String executiveId;

    private String assessmentType;

    private String managerId;

    private Date assessmentYears;

    private String assessmentTitle;

    private String assessmentQuarter;

    private String assessmentState;

    private String position;

    private String chargeLeaderId;

    private String departmentId;

    private String chargeLeaderOption;

    private String executiveLeaderId;

    private String executiveLeaderOption;

    private double quarterScore;
    
    private double taskScore;
    
    private double cooperationScore;
    
    private double disciplineScore;

    private String assistantLock;

    private String leaderSee;

    private String taskFlag;

    private String cooperationFlag;

    private String disciplineFlag;

    private String deptCoefficient;

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
    
    private OADepartmentMember leaderList;//部门负责人
    
    private String departmentName;
    
    private String mobile;
    
    private String state;
    
    private Date dutyClosingEndDate;
    
    private Date scoreClosingEndDate;
    
    private Date firstDutyClosingEndDate;
    
    private Date firstScoreClosingEndDate;
    
    private Date finalDutyClosingEndDate;
    
    private Date finalScoreClosingEndDate;
    
    private String haveChangerLeader;//0：有分管领导；1：没有
    
    private String assistantLeaderApprovalId;
    
    private String leaderFlag;//0：分管领导；1：主管领导
    
    private String leaderOption;
    
    private String deptHeadOption;
    
    private String approvalUserId;
    
    private String rankNum;
    
	public String getRankNum() {
		return rankNum;
	}

	public void setRankNum(String rankNum) {
		this.rankNum = rankNum;
	}

	public String getApprovalUserId() {
		return approvalUserId;
	}

	public void setApprovalUserId(String approvalUserId) {
		this.approvalUserId = approvalUserId;
	}

	public String getDeptHeadOption() {
		return deptHeadOption;
	}

	public void setDeptHeadOption(String deptHeadOption) {
		this.deptHeadOption = deptHeadOption;
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

	private List<ZjXmCqjxProjectExecutiveAssistant> assistantList;

    public String getAssistantLeaderApprovalId() {
		return assistantLeaderApprovalId;
	}

	public void setAssistantLeaderApprovalId(String assistantLeaderApprovalId) {
		this.assistantLeaderApprovalId = assistantLeaderApprovalId;
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

	public String getIdentityFlag() {
		return identityFlag;
	}

	public void setIdentityFlag(String identityFlag) {
		this.identityFlag = identityFlag;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public String getApprovalFlag() {
		return approvalFlag;
	}

	public void setApprovalFlag(String approvalFlag) {
		this.approvalFlag = approvalFlag;
	}

	public OADepartmentMember getLeaderList() {
		return leaderList;
	}

	public void setLeaderList(OADepartmentMember leaderList) {
		this.leaderList = leaderList;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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

	public String getHaveChangerLeader() {
		return haveChangerLeader;
	}

	public void setHaveChangerLeader(String haveChangerLeader) {
		this.haveChangerLeader = haveChangerLeader;
	}

	public List<ZjXmCqjxProjectExecutiveAssistant> getAssistantList() {
		return assistantList;
	}

	public void setAssistantList(List<ZjXmCqjxProjectExecutiveAssistant> assistantList) {
		this.assistantList = assistantList;
	}

	public String getExecutiveId() {
        return executiveId == null ? "" : executiveId.trim();
    }

    public void setExecutiveId(String executiveId) {
        this.executiveId = executiveId == null ? null : executiveId.trim();
    }

    public String getAssessmentType() {
        return assessmentType == null ? "" : assessmentType.trim();
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType == null ? null : assessmentType.trim();
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

    public String getDepartmentId() {
        return departmentId == null ? "" : departmentId.trim();
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
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

    public String getAssistantLock() {
        return assistantLock == null ? "" : assistantLock.trim();
    }

    public void setAssistantLock(String assistantLock) {
        this.assistantLock = assistantLock == null ? null : assistantLock.trim();
    }

    public String getLeaderSee() {
        return leaderSee == null ? "" : leaderSee.trim();
    }

    public void setLeaderSee(String leaderSee) {
        this.leaderSee = leaderSee == null ? null : leaderSee.trim();
    }

    public String getTaskFlag() {
        return taskFlag == null ? "" : taskFlag.trim();
    }

    public void setTaskFlag(String taskFlag) {
        this.taskFlag = taskFlag == null ? null : taskFlag.trim();
    }

    public String getCooperationFlag() {
        return cooperationFlag == null ? "" : cooperationFlag.trim();
    }

    public void setCooperationFlag(String cooperationFlag) {
        this.cooperationFlag = cooperationFlag == null ? null : cooperationFlag.trim();
    }

    public String getDisciplineFlag() {
        return disciplineFlag == null ? "" : disciplineFlag.trim();
    }

    public void setDisciplineFlag(String disciplineFlag) {
        this.disciplineFlag = disciplineFlag == null ? null : disciplineFlag.trim();
    }

    public String getDeptCoefficient() {
        return deptCoefficient == null ? "" : deptCoefficient.trim();
    }

    public void setDeptCoefficient(String deptCoefficient) {
        this.deptCoefficient = deptCoefficient == null ? null : deptCoefficient.trim();
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

