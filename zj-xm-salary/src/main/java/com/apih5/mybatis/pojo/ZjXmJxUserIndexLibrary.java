package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;

public class ZjXmJxUserIndexLibrary extends BasePojo {
	private String libraryId;

	private String userKey;

	private String realName;

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

	private String remarks;

	private Integer sort;

	private String delFlag;

	private Date createTime;

	private String createUser;

	private String createUserName;

	private Date modifyTime;

	private String modifyUser;

	private String modifyUserName;

	// 业务字段扩展
	// 集合
	private List<ZjXmJxUserIndexLibrary> userLibraryList;
	// 判断是清空还是保存0删除 1保存
	private String saveFlag;

	public String getSaveFlag() {
		return saveFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}

	public List<ZjXmJxUserIndexLibrary> getUserLibraryList() {
		return userLibraryList;
	}

	public void setUserLibraryList(List<ZjXmJxUserIndexLibrary> userLibraryList) {
		this.userLibraryList = userLibraryList;
	}

	public String getLibraryId() {
		return libraryId == null ? "" : libraryId.trim();
	}

	public void setLibraryId(String libraryId) {
		this.libraryId = libraryId == null ? null : libraryId.trim();
	}

	public String getUserKey() {
		return userKey == null ? "" : userKey.trim();
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey == null ? null : userKey.trim();
	}

	public String getRealName() {
		return realName == null ? "" : realName.trim();
	}

	public void setRealName(String realName) {
		this.realName = realName == null ? null : realName.trim();
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
