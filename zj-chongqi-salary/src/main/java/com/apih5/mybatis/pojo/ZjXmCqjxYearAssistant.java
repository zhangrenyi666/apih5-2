package com.apih5.mybatis.pojo;

import java.util.Date;
import com.apih5.framework.entity.BasePojo;

public class ZjXmCqjxYearAssistant extends BasePojo {
    private String yearAssistantId;

    private String assessmentType;

    private String managerId;

    private Date assessmentYears;

    private String assessmentTitle;

    private String assessmentQuarter;

    private String assessmentState;

    private String position;

    private String departmentId;

    private String assistantLock;

    private String leaderSee;

    private String delFlag;
    
    private double quarterAverageScore;
    
    private double comprehensiveScore;
    
    private double yearFinalScore;
    
    private String itemOne;
    
    private String itemTwo;
    
    private String itemThree;
    
    private String itemFour;
    
    private String itemFive;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private String state;
    
    private String yearScoreApprovalId;
    
    private String itemText;
    
    private String itemScore;
    
    private String itemType;
    
    private String departmentName;
    
    private String mobile;
    
    private double leaderScore;
    
    private String detailText;
    
    private String detailTarget;
    
    private String detailScore;

	public String getDetailTarget() {
		return detailTarget;
	}

	public void setDetailTarget(String detailTarget) {
		this.detailTarget = detailTarget;
	}

	public String getDetailText() {
		return detailText;
	}

	public void setDetailText(String detailText) {
		this.detailText = detailText;
	}

	public String getDetailScore() {
		return detailScore;
	}

	public void setDetailScore(String detailScore) {
		this.detailScore = detailScore;
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

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public double getLeaderScore() {
		return leaderScore;
	}

	public void setLeaderScore(double leaderScore) {
		this.leaderScore = leaderScore;
	}

	public String getItemText() {
		return itemText;
	}

	public void setItemText(String itemText) {
		this.itemText = itemText;
	}

	public String getItemScore() {
		return itemScore;
	}

	public void setItemScore(String itemScore) {
		this.itemScore = itemScore;
	}

	public String getYearScoreApprovalId() {
		return yearScoreApprovalId;
	}

	public void setYearScoreApprovalId(String yearScoreApprovalId) {
		this.yearScoreApprovalId = yearScoreApprovalId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getItemOne() {
		return itemOne;
	}

	public void setItemOne(String itemOne) {
		this.itemOne = itemOne;
	}

	public String getItemTwo() {
		return itemTwo;
	}

	public void setItemTwo(String itemTwo) {
		this.itemTwo = itemTwo;
	}

	public String getItemThree() {
		return itemThree;
	}

	public void setItemThree(String itemThree) {
		this.itemThree = itemThree;
	}

	public String getItemFour() {
		return itemFour;
	}

	public void setItemFour(String itemFour) {
		this.itemFour = itemFour;
	}

	public String getItemFive() {
		return itemFive;
	}

	public void setItemFive(String itemFive) {
		this.itemFive = itemFive;
	}

	public String getYearAssistantId() {
        return yearAssistantId == null ? "" : yearAssistantId.trim();
    }

    public void setYearAssistantId(String yearAssistantId) {
        this.yearAssistantId = yearAssistantId == null ? null : yearAssistantId.trim();
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

    public String getDepartmentId() {
        return departmentId == null ? "" : departmentId.trim();
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
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

