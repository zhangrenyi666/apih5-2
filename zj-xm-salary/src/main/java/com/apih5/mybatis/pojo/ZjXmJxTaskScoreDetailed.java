package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjXmJxTaskScoreDetailed extends BasePojo {
	private String detailedId;

	private String scoreId;

	private String libraryId;

	private String assessmentId;

	private String auditeeKey;

	private String auditeeName;

	private String auditeeDeptId;

	private String auditeeDeptName;

	private String auditeeProId;

	private String auditeeProName;

	private String reviewerKey;

	private String reviewerName;

	private String hrUserKey;

	private String hrName;

	private String costDutyIndex;

	private String targetValue;

	private String scoringStandard;

	private Integer weightValue;

	private BigDecimal score;

	private String appealStatus;

	private String appealOpinion;

	private String hrOpinion;

	private String completion;

	private String superiorOpinion;

	private String dataSources;

	private String isMandatory;

	private String isAutomaticScoring;

	private String remarks;

	private Integer sort;

	private String delFlag;

	private Date createTime;

	private String createUser;

	private String createUserName;

	private Date modifyTime;

	private String modifyUser;

	private String modifyUserName;

	// 业务扩展字段
	// 确认任务考核选人字段
	private JSONArray userArray;
	// 集合
	private List<ZjXmJxTaskScoreDetailed> scoreDetailedList;
	// 月度考核年月
	private Date yearMonth;
	// 领导审核状态
	private String auditStatus;
	// 被考核者确认状态
	private String confirmStatus;
	// 任务业绩总得分
	private BigDecimal taskScore;
	// 身份证号码
	private String idNumber;

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public BigDecimal getTaskScore() {
		return taskScore;
	}

	public void setTaskScore(BigDecimal taskScore) {
		this.taskScore = taskScore;
	}

	public String getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Date getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(Date yearMonth) {
		this.yearMonth = yearMonth;
	}

	public List<ZjXmJxTaskScoreDetailed> getScoreDetailedList() {
		return scoreDetailedList;
	}

	public void setScoreDetailedList(List<ZjXmJxTaskScoreDetailed> scoreDetailedList) {
		this.scoreDetailedList = scoreDetailedList;
	}

	public JSONArray getUserArray() {
		return userArray;
	}

	public void setUserArray(JSONArray userArray) {
		this.userArray = userArray;
	}

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

	public String getLibraryId() {
		return libraryId == null ? "" : libraryId.trim();
	}

	public void setLibraryId(String libraryId) {
		this.libraryId = libraryId == null ? null : libraryId.trim();
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

	public String getCostDutyIndex() {
		return costDutyIndex == null ? "" : costDutyIndex.trim();
	}

	public void setCostDutyIndex(String costDutyIndex) {
		this.costDutyIndex = costDutyIndex == null ? null : costDutyIndex.trim();
	}

	public String getTargetValue() {
		return targetValue == null ? "" : targetValue.trim();
	}

	public void setTargetValue(String targetValue) {
		this.targetValue = targetValue == null ? null : targetValue.trim();
	}

	public String getScoringStandard() {
		return scoringStandard == null ? "" : scoringStandard.trim();
	}

	public void setScoringStandard(String scoringStandard) {
		this.scoringStandard = scoringStandard == null ? null : scoringStandard.trim();
	}

	public Integer getWeightValue() {
		return weightValue;
	}

	public void setWeightValue(Integer weightValue) {
		this.weightValue = weightValue;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public String getAppealStatus() {
		return appealStatus == null ? "" : appealStatus.trim();
	}

	public void setAppealStatus(String appealStatus) {
		this.appealStatus = appealStatus == null ? null : appealStatus.trim();
	}

	public String getAppealOpinion() {
		return appealOpinion == null ? "" : appealOpinion.trim();
	}

	public void setAppealOpinion(String appealOpinion) {
		this.appealOpinion = appealOpinion == null ? null : appealOpinion.trim();
	}

	public String getHrOpinion() {
		return hrOpinion == null ? "" : hrOpinion.trim();
	}

	public void setHrOpinion(String hrOpinion) {
		this.hrOpinion = hrOpinion == null ? null : hrOpinion.trim();
	}

	public String getCompletion() {
		return completion == null ? "" : completion.trim();
	}

	public void setCompletion(String completion) {
		this.completion = completion == null ? null : completion.trim();
	}

	public String getSuperiorOpinion() {
		return superiorOpinion == null ? "" : superiorOpinion.trim();
	}

	public void setSuperiorOpinion(String superiorOpinion) {
		this.superiorOpinion = superiorOpinion == null ? null : superiorOpinion.trim();
	}

	public String getDataSources() {
		return dataSources == null ? "" : dataSources.trim();
	}

	public void setDataSources(String dataSources) {
		this.dataSources = dataSources == null ? null : dataSources.trim();
	}

	public String getIsMandatory() {
		return isMandatory == null ? "" : isMandatory.trim();
	}

	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory == null ? null : isMandatory.trim();
	}

	public String getIsAutomaticScoring() {
		return isAutomaticScoring == null ? "" : isAutomaticScoring.trim();
	}

	public void setIsAutomaticScoring(String isAutomaticScoring) {
		this.isAutomaticScoring = isAutomaticScoring == null ? null : isAutomaticScoring.trim();
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
