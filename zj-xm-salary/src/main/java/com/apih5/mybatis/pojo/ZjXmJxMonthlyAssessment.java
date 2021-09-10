package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;
import com.apih5.framework.entity.TreeNodeEntity;

public class ZjXmJxMonthlyAssessment extends BasePojo {
	private String assessmentId;

	private Date yearMonth;

	private String projectId;

	private String projectName;

	private String assessmentTitle;

	private String noticeStatus;

	private String taskStatus;

	private String taskRemarks;

	private String peripheryStatus;

	private String peripheryRemarks;

	private Integer deputyPeerWeight;

	private Integer deputySubordinateWeight;

	private Integer leaderSuperiorWeight;

	private Integer leaderPeerWeight;

	private Integer leaderSubordinateWeight;

	private Integer employeeSuperiorWeight;

	private Integer employeePeerWeight;

	private String principalStatus;

	private String principalRemarks;

	private String compositeStatus;

	private String compositeRemarks;

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
	// 人员集合
	private JSONArray userArray;
	// 项目副职集合
	private JSONArray deputyArray;
	// 部门负责人集合
	private JSONArray leaderArray;
	// 普通员工集合
	private JSONArray employeeArray;

	public JSONArray getDeputyArray() {
		return deputyArray;
	}

	public void setDeputyArray(JSONArray deputyArray) {
		this.deputyArray = deputyArray;
	}

	public JSONArray getLeaderArray() {
		return leaderArray;
	}

	public void setLeaderArray(JSONArray leaderArray) {
		this.leaderArray = leaderArray;
	}

	public JSONArray getEmployeeArray() {
		return employeeArray;
	}

	public void setEmployeeArray(JSONArray employeeArray) {
		this.employeeArray = employeeArray;
	}

	public JSONArray getUserArray() {
		return userArray;
	}

	public void setUserArray(JSONArray userArray) {
		this.userArray = userArray;
	}

	public String getAssessmentId() {
		return assessmentId == null ? "" : assessmentId.trim();
	}

	public void setAssessmentId(String assessmentId) {
		this.assessmentId = assessmentId == null ? null : assessmentId.trim();
	}

	public Date getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(Date yearMonth) {
		this.yearMonth = yearMonth;
	}

	public String getProjectId() {
		return projectId == null ? "" : projectId.trim();
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId == null ? null : projectId.trim();
	}

	public String getProjectName() {
		return projectName == null ? "" : projectName.trim();
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName == null ? null : projectName.trim();
	}

	public String getAssessmentTitle() {
		return assessmentTitle == null ? "" : assessmentTitle.trim();
	}

	public void setAssessmentTitle(String assessmentTitle) {
		this.assessmentTitle = assessmentTitle == null ? null : assessmentTitle.trim();
	}

	public String getNoticeStatus() {
		return noticeStatus == null ? "" : noticeStatus.trim();
	}

	public void setNoticeStatus(String noticeStatus) {
		this.noticeStatus = noticeStatus == null ? null : noticeStatus.trim();
	}

	public String getTaskStatus() {
		return taskStatus == null ? "" : taskStatus.trim();
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus == null ? null : taskStatus.trim();
	}

	public String getTaskRemarks() {
		return taskRemarks == null ? "" : taskRemarks.trim();
	}

	public void setTaskRemarks(String taskRemarks) {
		this.taskRemarks = taskRemarks == null ? null : taskRemarks.trim();
	}

	public String getPeripheryStatus() {
		return peripheryStatus == null ? "" : peripheryStatus.trim();
	}

	public void setPeripheryStatus(String peripheryStatus) {
		this.peripheryStatus = peripheryStatus == null ? null : peripheryStatus.trim();
	}

	public String getPeripheryRemarks() {
		return peripheryRemarks == null ? "" : peripheryRemarks.trim();
	}

	public void setPeripheryRemarks(String peripheryRemarks) {
		this.peripheryRemarks = peripheryRemarks == null ? null : peripheryRemarks.trim();
	}

	public Integer getDeputyPeerWeight() {
		return deputyPeerWeight;
	}

	public void setDeputyPeerWeight(Integer deputyPeerWeight) {
		this.deputyPeerWeight = deputyPeerWeight;
	}

	public Integer getDeputySubordinateWeight() {
		return deputySubordinateWeight;
	}

	public void setDeputySubordinateWeight(Integer deputySubordinateWeight) {
		this.deputySubordinateWeight = deputySubordinateWeight;
	}

	public Integer getLeaderSuperiorWeight() {
		return leaderSuperiorWeight;
	}

	public void setLeaderSuperiorWeight(Integer leaderSuperiorWeight) {
		this.leaderSuperiorWeight = leaderSuperiorWeight;
	}

	public Integer getLeaderPeerWeight() {
		return leaderPeerWeight;
	}

	public void setLeaderPeerWeight(Integer leaderPeerWeight) {
		this.leaderPeerWeight = leaderPeerWeight;
	}

	public Integer getLeaderSubordinateWeight() {
		return leaderSubordinateWeight;
	}

	public void setLeaderSubordinateWeight(Integer leaderSubordinateWeight) {
		this.leaderSubordinateWeight = leaderSubordinateWeight;
	}

	public Integer getEmployeeSuperiorWeight() {
		return employeeSuperiorWeight;
	}

	public void setEmployeeSuperiorWeight(Integer employeeSuperiorWeight) {
		this.employeeSuperiorWeight = employeeSuperiorWeight;
	}

	public Integer getEmployeePeerWeight() {
		return employeePeerWeight;
	}

	public void setEmployeePeerWeight(Integer employeePeerWeight) {
		this.employeePeerWeight = employeePeerWeight;
	}

	public String getPrincipalStatus() {
		return principalStatus == null ? "" : principalStatus.trim();
	}

	public void setPrincipalStatus(String principalStatus) {
		this.principalStatus = principalStatus == null ? null : principalStatus.trim();
	}

	public String getPrincipalRemarks() {
		return principalRemarks == null ? "" : principalRemarks.trim();
	}

	public void setPrincipalRemarks(String principalRemarks) {
		this.principalRemarks = principalRemarks == null ? null : principalRemarks.trim();
	}

	public String getCompositeStatus() {
		return compositeStatus == null ? "" : compositeStatus.trim();
	}

	public void setCompositeStatus(String compositeStatus) {
		this.compositeStatus = compositeStatus == null ? null : compositeStatus.trim();
	}

	public String getCompositeRemarks() {
		return compositeRemarks == null ? "" : compositeRemarks.trim();
	}

	public void setCompositeRemarks(String compositeRemarks) {
		this.compositeRemarks = compositeRemarks == null ? null : compositeRemarks.trim();
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
