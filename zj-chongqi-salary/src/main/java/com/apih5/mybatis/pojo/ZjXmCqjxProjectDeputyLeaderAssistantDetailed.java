package com.apih5.mybatis.pojo;

import java.util.Date;
import com.apih5.framework.entity.BasePojo;

public class ZjXmCqjxProjectDeputyLeaderAssistantDetailed extends BasePojo {
    private String executiveDetailId;

    private String managerId;

    private String executiveId;

    private String workPlan;
    
    private String workTarget;

    private String completion;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private double assessmentScore;

    private double chargeLeaderScore;

    private double executiveLeaderScore;

    private double executiveScore;
    
    private String lastScore;

    public String getLastScore() {
		return lastScore;
	}

	public void setLastScore(String lastScore) {
		this.lastScore = lastScore;
	}

	public String getWorkTarget() {
		return workTarget;
	}

	public void setWorkTarget(String workTarget) {
		this.workTarget = workTarget;
	}

	public double getAssessmentScore() {
		return assessmentScore;
	}

	public void setAssessmentScore(double assessmentScore) {
		this.assessmentScore = assessmentScore;
	}

	public double getChargeLeaderScore() {
		return chargeLeaderScore;
	}

	public void setChargeLeaderScore(double chargeLeaderScore) {
		this.chargeLeaderScore = chargeLeaderScore;
	}

	public double getExecutiveLeaderScore() {
		return executiveLeaderScore;
	}

	public void setExecutiveLeaderScore(double executiveLeaderScore) {
		this.executiveLeaderScore = executiveLeaderScore;
	}

	public double getExecutiveScore() {
		return executiveScore;
	}

	public void setExecutiveScore(double executiveScore) {
		this.executiveScore = executiveScore;
	}

	public String getExecutiveDetailId() {
        return executiveDetailId == null ? "" : executiveDetailId.trim();
    }

    public void setExecutiveDetailId(String executiveDetailId) {
        this.executiveDetailId = executiveDetailId == null ? null : executiveDetailId.trim();
    }

    public String getManagerId() {
        return managerId == null ? "" : managerId.trim();
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId == null ? null : managerId.trim();
    }

    public String getExecutiveId() {
        return executiveId == null ? "" : executiveId.trim();
    }

    public void setExecutiveId(String executiveId) {
        this.executiveId = executiveId == null ? null : executiveId.trim();
    }

    public String getWorkPlan() {
        return workPlan == null ? "" : workPlan.trim();
    }

    public void setWorkPlan(String workPlan) {
        this.workPlan = workPlan == null ? null : workPlan.trim();
    }

    public String getCompletion() {
        return completion == null ? "" : completion.trim();
    }

    public void setCompletion(String completion) {
        this.completion = completion == null ? null : completion.trim();
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

