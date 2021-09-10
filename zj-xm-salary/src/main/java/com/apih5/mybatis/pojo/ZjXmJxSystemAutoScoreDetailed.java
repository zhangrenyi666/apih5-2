package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjXmJxSystemAutoScoreDetailed extends BasePojo {
    private String detailedId;

    private String scoreId;

    private String assessmentId;

    private String auditeeKey;

    private String auditeeName;

    private String auditeeType;

    private String auditeeDeptId;

    private String auditeeDeptName;

    private String auditeeProId;

    private String auditeeProName;

    private String reviewerKey;

    private String reviewerName;

    private String hrUserKey;

    private String hrName;

    private BigDecimal score;

    private String reviewerType;

    private String auditStatus;

    private String reasonSources;

    private String deductionType;

    private String remarks;

    private Integer sort;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public String getDetailedId() {
        return detailedId == null ? "" : detailedId.trim();
    }

    public void setDetailedId(String detailedId) {
        this.detailedId = detailedId == null ? null : detailedId.trim();
    }

    public String getScoreId() {
        return scoreId == null ? "" : scoreId.trim();
    }

    public void setScoreId(String scoreId) {
        this.scoreId = scoreId == null ? null : scoreId.trim();
    }

    public String getAssessmentId() {
        return assessmentId == null ? "" : assessmentId.trim();
    }

    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId == null ? null : assessmentId.trim();
    }

    public String getAuditeeKey() {
        return auditeeKey == null ? "" : auditeeKey.trim();
    }

    public void setAuditeeKey(String auditeeKey) {
        this.auditeeKey = auditeeKey == null ? null : auditeeKey.trim();
    }

    public String getAuditeeName() {
        return auditeeName == null ? "" : auditeeName.trim();
    }

    public void setAuditeeName(String auditeeName) {
        this.auditeeName = auditeeName == null ? null : auditeeName.trim();
    }

    public String getAuditeeType() {
        return auditeeType == null ? "" : auditeeType.trim();
    }

    public void setAuditeeType(String auditeeType) {
        this.auditeeType = auditeeType == null ? null : auditeeType.trim();
    }

    public String getAuditeeDeptId() {
        return auditeeDeptId == null ? "" : auditeeDeptId.trim();
    }

    public void setAuditeeDeptId(String auditeeDeptId) {
        this.auditeeDeptId = auditeeDeptId == null ? null : auditeeDeptId.trim();
    }

    public String getAuditeeDeptName() {
        return auditeeDeptName == null ? "" : auditeeDeptName.trim();
    }

    public void setAuditeeDeptName(String auditeeDeptName) {
        this.auditeeDeptName = auditeeDeptName == null ? null : auditeeDeptName.trim();
    }

    public String getAuditeeProId() {
        return auditeeProId == null ? "" : auditeeProId.trim();
    }

    public void setAuditeeProId(String auditeeProId) {
        this.auditeeProId = auditeeProId == null ? null : auditeeProId.trim();
    }

    public String getAuditeeProName() {
        return auditeeProName == null ? "" : auditeeProName.trim();
    }

    public void setAuditeeProName(String auditeeProName) {
        this.auditeeProName = auditeeProName == null ? null : auditeeProName.trim();
    }

    public String getReviewerKey() {
        return reviewerKey == null ? "" : reviewerKey.trim();
    }

    public void setReviewerKey(String reviewerKey) {
        this.reviewerKey = reviewerKey == null ? null : reviewerKey.trim();
    }

    public String getReviewerName() {
        return reviewerName == null ? "" : reviewerName.trim();
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName == null ? null : reviewerName.trim();
    }

    public String getHrUserKey() {
        return hrUserKey == null ? "" : hrUserKey.trim();
    }

    public void setHrUserKey(String hrUserKey) {
        this.hrUserKey = hrUserKey == null ? null : hrUserKey.trim();
    }

    public String getHrName() {
        return hrName == null ? "" : hrName.trim();
    }

    public void setHrName(String hrName) {
        this.hrName = hrName == null ? null : hrName.trim();
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getReviewerType() {
        return reviewerType == null ? "" : reviewerType.trim();
    }

    public void setReviewerType(String reviewerType) {
        this.reviewerType = reviewerType == null ? null : reviewerType.trim();
    }

    public String getAuditStatus() {
        return auditStatus == null ? "" : auditStatus.trim();
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public String getReasonSources() {
        return reasonSources == null ? "" : reasonSources.trim();
    }

    public void setReasonSources(String reasonSources) {
        this.reasonSources = reasonSources == null ? null : reasonSources.trim();
    }

    public String getDeductionType() {
        return deductionType == null ? "" : deductionType.trim();
    }

    public void setDeductionType(String deductionType) {
        this.deductionType = deductionType == null ? null : deductionType.trim();
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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

