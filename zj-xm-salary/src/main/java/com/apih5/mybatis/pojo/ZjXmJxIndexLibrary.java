package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjXmJxIndexLibrary extends BasePojo {
	private String libraryId;

	private String deptId;

	private String deptName;

	private String projectId;

	private String projectName;

	private String costDutyIndex;

	private String targetValue;

	private String scoringStandard;

	private String dataSources;

	private String isMandatory;

	private Integer weightValue;

	private String isAutomaticScoring;

	private String forbidFlag;

	private String remarks;

	private Integer sort;

	private String delFlag;

	private Date createTime;

	private String createUser;

	private String createUserName;

	private Date modifyTime;

	private String modifyUser;

	private String modifyUserName;

	public String getLibraryId() {
		return libraryId == null ? "" : libraryId.trim();
	}

	public void setLibraryId(String libraryId) {
		this.libraryId = libraryId == null ? null : libraryId.trim();
	}

	public String getDeptId() {
		return deptId == null ? "" : deptId.trim();
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId == null ? null : deptId.trim();
	}

	public String getDeptName() {
		return deptName == null ? "" : deptName.trim();
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName == null ? null : deptName.trim();
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

	public Integer getWeightValue() {
		return weightValue;
	}

	public void setWeightValue(Integer weightValue) {
		this.weightValue = weightValue;
	}

	public String getIsAutomaticScoring() {
		return isAutomaticScoring == null ? "" : isAutomaticScoring.trim();
	}

	public void setIsAutomaticScoring(String isAutomaticScoring) {
		this.isAutomaticScoring = isAutomaticScoring == null ? null : isAutomaticScoring.trim();
	}

	public String getForbidFlag() {
		return forbidFlag == null ? "" : forbidFlag.trim();
	}

	public void setForbidFlag(String forbidFlag) {
		this.forbidFlag = forbidFlag == null ? null : forbidFlag.trim();
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
