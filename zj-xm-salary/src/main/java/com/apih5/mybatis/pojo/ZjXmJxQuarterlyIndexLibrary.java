package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;

public class ZjXmJxQuarterlyIndexLibrary extends BasePojo {
	// 主键ID
	private String libraryId;

	// 部门ID
	private String deptId;

	// 部门名称
	private String deptName;

	// 项目状态id
	private String projectStatus;

	// 项目状态名称
	private String projectStatusName;

	// 项目类型id
	private String projectType;

	// 项目类型名称
	private String projectTypeName;

	// 考核标题
	private String libraryTitle;

	// 考核内容
	private String libraryContent;

	// 考核责任人key
	private String personLiableKey;

	// 考核责任人姓名
	private String personLiableName;

	// 是否是固定分数
	private String isFixedScore;

	// 分数
	private BigDecimal score;

	// 加/减分下限
	private BigDecimal lowerLimitScore;

	// 加/减分上限
	private BigDecimal upperLimitScore;

	// 是否是收尾项目
	private String isClosed;

	// 备注
	private String remarks;

	// 排序
	private int sort = 0;

	// 业务扩展字段
	// 考核责任人数组
	private JSONArray personLiableArray;

	public JSONArray getPersonLiableArray() {
		return personLiableArray;
	}

	public void setPersonLiableArray(JSONArray personLiableArray) {
		this.personLiableArray = personLiableArray;
	}

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

	public String getProjectStatus() {
		return projectStatus == null ? "" : projectStatus.trim();
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus == null ? null : projectStatus.trim();
	}

	public String getProjectStatusName() {
		return projectStatusName == null ? "" : projectStatusName.trim();
	}

	public void setProjectStatusName(String projectStatusName) {
		this.projectStatusName = projectStatusName == null ? null : projectStatusName.trim();
	}

	public String getProjectType() {
		return projectType == null ? "" : projectType.trim();
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType == null ? null : projectType.trim();
	}

	public String getProjectTypeName() {
		return projectTypeName == null ? "" : projectTypeName.trim();
	}

	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName == null ? null : projectTypeName.trim();
	}

	public String getLibraryTitle() {
		return libraryTitle == null ? "" : libraryTitle.trim();
	}

	public void setLibraryTitle(String libraryTitle) {
		this.libraryTitle = libraryTitle == null ? null : libraryTitle.trim();
	}

	public String getLibraryContent() {
		return libraryContent == null ? "" : libraryContent.trim();
	}

	public void setLibraryContent(String libraryContent) {
		this.libraryContent = libraryContent == null ? null : libraryContent.trim();
	}

	public String getPersonLiableKey() {
		return personLiableKey == null ? "" : personLiableKey.trim();
	}

	public void setPersonLiableKey(String personLiableKey) {
		this.personLiableKey = personLiableKey == null ? null : personLiableKey.trim();
	}

	public String getPersonLiableName() {
		return personLiableName == null ? "" : personLiableName.trim();
	}

	public void setPersonLiableName(String personLiableName) {
		this.personLiableName = personLiableName == null ? null : personLiableName.trim();
	}

	public String getIsFixedScore() {
		return isFixedScore == null ? "" : isFixedScore.trim();
	}

	public void setIsFixedScore(String isFixedScore) {
		this.isFixedScore = isFixedScore == null ? null : isFixedScore.trim();
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public BigDecimal getLowerLimitScore() {
		return lowerLimitScore;
	}

	public void setLowerLimitScore(BigDecimal lowerLimitScore) {
		this.lowerLimitScore = lowerLimitScore;
	}

	public BigDecimal getUpperLimitScore() {
		return upperLimitScore;
	}

	public void setUpperLimitScore(BigDecimal upperLimitScore) {
		this.upperLimitScore = upperLimitScore;
	}

	public String getIsClosed() {
		return isClosed == null ? "" : isClosed.trim();
	}

	public void setIsClosed(String isClosed) {
		this.isClosed = isClosed == null ? null : isClosed.trim();
	}

	public String getRemarks() {
		return remarks == null ? "" : remarks.trim();
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks == null ? null : remarks.trim();
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
