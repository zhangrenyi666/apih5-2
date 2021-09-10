package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjXmCqjxProjectAssistantLeaderApproval extends BasePojo {
    private String assistantLeaderApprovalId;

    private String executiveId;

    private String otherType;

    private String leaderId;

    private String leaderName;

    private String leaderOrgId;

    private String leaderOption;

    private String leaderScore;

    private String approvalFlag;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public String getAssistantLeaderApprovalId() {
        return assistantLeaderApprovalId == null ? "" : assistantLeaderApprovalId.trim();
    }

    public void setAssistantLeaderApprovalId(String assistantLeaderApprovalId) {
        this.assistantLeaderApprovalId = assistantLeaderApprovalId == null ? null : assistantLeaderApprovalId.trim();
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

    public String getLeaderOption() {
        return leaderOption == null ? "" : leaderOption.trim();
    }

    public void setLeaderOption(String leaderOption) {
        this.leaderOption = leaderOption == null ? null : leaderOption.trim();
    }

    public String getLeaderScore() {
        return leaderScore == null ? "" : leaderScore.trim();
    }

    public void setLeaderScore(String leaderScore) {
        this.leaderScore = leaderScore == null ? null : leaderScore.trim();
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

