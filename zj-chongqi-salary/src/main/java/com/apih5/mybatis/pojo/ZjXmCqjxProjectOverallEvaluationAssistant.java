package com.apih5.mybatis.pojo;

import java.util.Date;
import com.apih5.framework.entity.BasePojo;

public class ZjXmCqjxProjectOverallEvaluationAssistant extends BasePojo {
    private String overallEvaluationId;

    private String assessmentType;

    private String managerId;

    private Date assessmentYears;

    private String assessmentTitle;

    private String assessmentQuarter;

    private String assessmentState;

    private String position;

    private String itemOneScore;

    private String itemTwoScore;

    private String itemThreeScore;

    private String itemFourScore;

    private String itemFiveScore;

    private String assistantLock;

    private String leaderSee;

    private String quarterScore;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private String assistantLeaderApprovalId;
    
    private String flowType;
    
    private String itemText;
    
    private String itemScore;
    
    private String state;
    
    private double leaderScore;
    
    private String assistantScore;
    
    private String departmentName;
    
    private String stateText;
    
    private String mobile;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStateText() {
		return stateText;
	}

	public void setStateText(String stateText) {
		this.stateText = stateText;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getAssistantScore() {
		return assistantScore;
	}

	public void setAssistantScore(String assistantScore) {
		this.assistantScore = assistantScore;
	}

	public double getLeaderScore() {
		return leaderScore;
	}

	public void setLeaderScore(double leaderScore) {
		this.leaderScore = leaderScore;
	}

	public String getItemScore() {
		return itemScore;
	}

	public void setItemScore(String itemScore) {
		this.itemScore = itemScore;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAssistantLeaderApprovalId() {
		return assistantLeaderApprovalId;
	}

	public void setAssistantLeaderApprovalId(String assistantLeaderApprovalId) {
		this.assistantLeaderApprovalId = assistantLeaderApprovalId;
	}

	public String getFlowType() {
		return flowType;
	}

	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}

	public String getItemText() {
		return itemText;
	}

	public void setItemText(String itemText) {
		this.itemText = itemText;
	}

	public String getOverallEvaluationId() {
        return overallEvaluationId == null ? "" : overallEvaluationId.trim();
    }

    public void setOverallEvaluationId(String overallEvaluationId) {
        this.overallEvaluationId = overallEvaluationId == null ? null : overallEvaluationId.trim();
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

    public String getItemOneScore() {
        return itemOneScore == null ? "" : itemOneScore.trim();
    }

    public void setItemOneScore(String itemOneScore) {
        this.itemOneScore = itemOneScore == null ? null : itemOneScore.trim();
    }

    public String getItemTwoScore() {
        return itemTwoScore == null ? "" : itemTwoScore.trim();
    }

    public void setItemTwoScore(String itemTwoScore) {
        this.itemTwoScore = itemTwoScore == null ? null : itemTwoScore.trim();
    }

    public String getItemThreeScore() {
        return itemThreeScore == null ? "" : itemThreeScore.trim();
    }

    public void setItemThreeScore(String itemThreeScore) {
        this.itemThreeScore = itemThreeScore == null ? null : itemThreeScore.trim();
    }

    public String getItemFourScore() {
        return itemFourScore == null ? "" : itemFourScore.trim();
    }

    public void setItemFourScore(String itemFourScore) {
        this.itemFourScore = itemFourScore == null ? null : itemFourScore.trim();
    }

    public String getItemFiveScore() {
        return itemFiveScore == null ? "" : itemFiveScore.trim();
    }

    public void setItemFiveScore(String itemFiveScore) {
        this.itemFiveScore = itemFiveScore == null ? null : itemFiveScore.trim();
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

    public String getQuarterScore() {
        return quarterScore == null ? "" : quarterScore.trim();
    }

    public void setQuarterScore(String quarterScore) {
        this.quarterScore = quarterScore == null ? null : quarterScore.trim();
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

