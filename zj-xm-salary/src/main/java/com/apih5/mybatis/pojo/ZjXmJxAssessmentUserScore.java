package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import com.apih5.framework.entity.BasePojo;
import cn.hutool.json.JSONArray;

public class ZjXmJxAssessmentUserScore extends BasePojo {
	private String scoreId;

	private String assessmentId;

	private String auditeeKey;

	private String auditeeName;

	private String auditeeIdNumber;

	private String auditeePosition;

	private String auditeeRankType;

	private String auditeeLastType;

	private String auditeeUserType;

	private String auditeeType;

	private String auditeeDeptId;

	private String auditeeDeptName;

	private String auditeeProId;

	private String auditeeProName;

	private String assessmentType;

	private String confirmStatus;

	private Date notifiedTime;

	private BigDecimal score;

	private BigDecimal principalScore;

	private BigDecimal secretaryScore;

	private String reviewerPosition;

	private String reviewerKey;

	private String reviewerName;

	private Date auditTime;

	private String auditStatus;

	private String deductReason;

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
	// 月度考核年月
	private Date yearMonth;
	// 月度考核标题
	private String assessmentTitle;
	// 月度考核通知状态
	private String noticeStatus;
	// 任务考核完成比例
	private String taskRatio;
	// 周边考核完成情况
	private String peripheryComp;
	// 正职评分完成情况
	private String principalComp;
	// 综合评分完成情况
	private String compositeComp;
	// 人员集合
	private JSONArray userArray;
	// 综合评价得分数组
	//private JSONArray scoreArray;
	// 任务考核得分
	private BigDecimal taskTotalScore;
	// 周边考核得分
	private BigDecimal peripheryTotalScore;
	// 正职评分得分
	private BigDecimal principalTotalScore;
	// 综合评分得分
	private BigDecimal compositeTotalScore;
	// 系统扣分
	private BigDecimal systemTotalScore;
	// 总分
	private BigDecimal totalScore;
	// 排名
	private String rankNo;
	// 任务考核得分及权重
	private String taskStr;
	// 周边考核得分及权重
	private String peripheryStr;
	// 正职考核得分及权重
	private String principalStr;
	// 定时任务起始时间差值
	private Integer startValue;
	// 定时任务结束时间差值
	private Integer endValue;
	// 综合评价扣分原因数组
	private JSONArray deductReasonArray;

	public JSONArray getDeductReasonArray() {
		return deductReasonArray;
	}

	public void setDeductReasonArray(JSONArray deductReasonArray) {
		this.deductReasonArray = deductReasonArray;
	}

	public Integer getStartValue() {
		return startValue;
	}

	public void setStartValue(Integer startValue) {
		this.startValue = startValue;
	}

	public Integer getEndValue() {
		return endValue;
	}

	public void setEndValue(Integer endValue) {
		this.endValue = endValue;
	}

	public String getTaskStr() {
		return taskStr;
	}

	public void setTaskStr(String taskStr) {
		this.taskStr = taskStr;
	}

	public String getPeripheryStr() {
		return peripheryStr;
	}

	public void setPeripheryStr(String peripheryStr) {
		this.peripheryStr = peripheryStr;
	}

	public String getPrincipalStr() {
		return principalStr;
	}

	public void setPrincipalStr(String principalStr) {
		this.principalStr = principalStr;
	}

	public BigDecimal getSystemTotalScore() {
		return systemTotalScore;
	}

	public void setSystemTotalScore(BigDecimal systemTotalScore) {
		this.systemTotalScore = systemTotalScore;
	}

	public String getCompositeComp() {
		return compositeComp;
	}

	public void setCompositeComp(String compositeComp) {
		this.compositeComp = compositeComp;
	}

	public String getRankNo() {
		return rankNo;
	}

	public void setRankNo(String rankNo) {
		this.rankNo = rankNo;
	}

//	public JSONArray getScoreArray() {
//		return scoreArray;
//	}
//
//	public void setScoreArray(JSONArray scoreArray) {
//		this.scoreArray = scoreArray;
//	}

	public BigDecimal getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(BigDecimal totalScore) {
		this.totalScore = totalScore;
	}

	public String getNoticeStatus() {
		return noticeStatus;
	}

	public void setNoticeStatus(String noticeStatus) {
		this.noticeStatus = noticeStatus;
	}

//	public BigDecimal getAverageTotalScore() {
//		return averageTotalScore;
//	}
//
//	public void setAverageTotalScore(BigDecimal averageTotalScore) {
//		this.averageTotalScore = averageTotalScore;
//	}

	public BigDecimal getTaskTotalScore() {
		return taskTotalScore;
	}

	public void setTaskTotalScore(BigDecimal taskTotalScore) {
		this.taskTotalScore = taskTotalScore;
	}

	public BigDecimal getPeripheryTotalScore() {
		return peripheryTotalScore;
	}

	public void setPeripheryTotalScore(BigDecimal peripheryTotalScore) {
		this.peripheryTotalScore = peripheryTotalScore;
	}

	public BigDecimal getPrincipalTotalScore() {
		return principalTotalScore;
	}

	public void setPrincipalTotalScore(BigDecimal principalTotalScore) {
		this.principalTotalScore = principalTotalScore;
	}

	public BigDecimal getCompositeTotalScore() {
		return compositeTotalScore;
	}

	public void setCompositeTotalScore(BigDecimal compositeTotalScore) {
		this.compositeTotalScore = compositeTotalScore;
	}

	public JSONArray getUserArray() {
		return userArray;
	}

	public void setUserArray(JSONArray userArray) {
		this.userArray = userArray;
	}

	public String getTaskRatio() {
		return taskRatio;
	}

	public void setTaskRatio(String taskRatio) {
		this.taskRatio = taskRatio;
	}

	public String getPeripheryComp() {
		return peripheryComp;
	}

	public void setPeripheryComp(String peripheryComp) {
		this.peripheryComp = peripheryComp;
	}

	public String getPrincipalComp() {
		return principalComp;
	}

	public void setPrincipalComp(String principalComp) {
		this.principalComp = principalComp;
	}

	public Date getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(Date yearMonth) {
		this.yearMonth = yearMonth;
	}

	public String getAssessmentTitle() {
		return assessmentTitle;
	}

	public void setAssessmentTitle(String assessmentTitle) {
		this.assessmentTitle = assessmentTitle;
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

	public String getAuditeeIdNumber() {
		return auditeeIdNumber == null ? "" : auditeeIdNumber.trim();
	}

	public void setAuditeeIdNumber(String auditeeIdNumber) {
		this.auditeeIdNumber = auditeeIdNumber == null ? null : auditeeIdNumber.trim();
	}

	public String getAuditeePosition() {
		return auditeePosition == null ? "" : auditeePosition.trim();
	}

	public void setAuditeePosition(String auditeePosition) {
		this.auditeePosition = auditeePosition == null ? null : auditeePosition.trim();
	}

	public String getAuditeeRankType() {
		return auditeeRankType == null ? "" : auditeeRankType.trim();
	}

	public void setAuditeeRankType(String auditeeRankType) {
		this.auditeeRankType = auditeeRankType == null ? null : auditeeRankType.trim();
	}

	public String getAuditeeLastType() {
		return auditeeLastType == null ? "" : auditeeLastType.trim();
	}

	public void setAuditeeLastType(String auditeeLastType) {
		this.auditeeLastType = auditeeLastType == null ? null : auditeeLastType.trim();
	}

	public String getAuditeeUserType() {
		return auditeeUserType == null ? "" : auditeeUserType.trim();
	}

	public void setAuditeeUserType(String auditeeUserType) {
		this.auditeeUserType = auditeeUserType == null ? null : auditeeUserType.trim();
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

	public String getAssessmentType() {
		return assessmentType == null ? "" : assessmentType.trim();
	}

	public void setAssessmentType(String assessmentType) {
		this.assessmentType = assessmentType == null ? null : assessmentType.trim();
	}

	public String getConfirmStatus() {
		return confirmStatus == null ? "" : confirmStatus.trim();
	}

	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus == null ? null : confirmStatus.trim();
	}

	public Date getNotifiedTime() {
		return notifiedTime;
	}

	public void setNotifiedTime(Date notifiedTime) {
		this.notifiedTime = notifiedTime;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public BigDecimal getPrincipalScore() {
		return principalScore;
	}

	public void setPrincipalScore(BigDecimal principalScore) {
		this.principalScore = principalScore;
	}

	public BigDecimal getSecretaryScore() {
		return secretaryScore;
	}

	public void setSecretaryScore(BigDecimal secretaryScore) {
		this.secretaryScore = secretaryScore;
	}

	public String getReviewerPosition() {
		return reviewerPosition == null ? "" : reviewerPosition.trim();
	}

	public void setReviewerPosition(String reviewerPosition) {
		this.reviewerPosition = reviewerPosition == null ? null : reviewerPosition.trim();
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

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditStatus() {
		return auditStatus == null ? "" : auditStatus.trim();
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus == null ? null : auditStatus.trim();
	}

	public String getDeductReason() {
		return deductReason == null ? "" : deductReason.trim();
	}

	public void setDeductReason(String deductReason) {
		this.deductReason = deductReason == null ? null : deductReason.trim();
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
