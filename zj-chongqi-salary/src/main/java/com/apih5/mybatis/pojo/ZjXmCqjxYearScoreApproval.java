package com.apih5.mybatis.pojo;

import java.util.Date;
import com.apih5.framework.entity.BasePojo;

public class ZjXmCqjxYearScoreApproval extends BasePojo {
    private String yearScoreApprovalId;

    private String executiveId;

    private String otherType;

    private String itemType;

    private String leaderId;

    private String leaderName;

    private String leaderOrgId;

    private double leaderScore;

    private String approvalFlag;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private double itemScore;

	public double getItemScore() {
		return itemScore;
	}

	public void setItemScore(double itemScore) {
		this.itemScore = itemScore;
	}

	public String getYearScoreApprovalId() {
        return yearScoreApprovalId == null ? "" : yearScoreApprovalId.trim();
    }

    public void setYearScoreApprovalId(String yearScoreApprovalId) {
        this.yearScoreApprovalId = yearScoreApprovalId == null ? null : yearScoreApprovalId.trim();
    }

    public String getExecutiveId() {
        return executiveId == null ? "" : executiveId.trim();
    }

    public void setExecutiveId(String executiveId) {
        this.executiveId = executiveId == null ? null : executiveId.trim();
    }

    public String getOtherType() {
        return otherType == null ? "" : otherType.trim();
    }

    public void setOtherType(String otherType) {
        this.otherType = otherType == null ? null : otherType.trim();
    }

    public String getItemType() {
        return itemType == null ? "" : itemType.trim();
    }

    public void setItemType(String itemType) {
        this.itemType = itemType == null ? null : itemType.trim();
    }

    public String getLeaderId() {
        return leaderId == null ? "" : leaderId.trim();
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId == null ? null : leaderId.trim();
    }

    public String getLeaderName() {
        return leaderName == null ? "" : leaderName.trim();
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName == null ? null : leaderName.trim();
    }

    public String getLeaderOrgId() {
        return leaderOrgId == null ? "" : leaderOrgId.trim();
    }

    public void setLeaderOrgId(String leaderOrgId) {
        this.leaderOrgId = leaderOrgId == null ? null : leaderOrgId.trim();
    }

    public double getLeaderScore() {
		return leaderScore;
	}

	public void setLeaderScore(double leaderScore) {
		this.leaderScore = leaderScore;
	}

	public String getApprovalFlag() {
        return approvalFlag == null ? "" : approvalFlag.trim();
    }

    public void setApprovalFlag(String approvalFlag) {
        this.approvalFlag = approvalFlag == null ? null : approvalFlag.trim();
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

