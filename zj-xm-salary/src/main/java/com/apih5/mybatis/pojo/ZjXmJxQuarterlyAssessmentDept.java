package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjXmJxQuarterlyAssessmentDept extends BasePojo {
	// 主键ID
	private String deptId;

	// 部门名称
	private String deptName;

	// 系统部门表id
	private String departmentId;

	// 系统部门表名称
	private String departmentName;

	// 是否是收尾项目
	private String isClosed;

	// 项目状态id
	private String projectStatus;

	// 项目状态名称
	private String projectStatusName;

	// 项目类型id
	private String projectType;

	// 项目类型名称
	private String projectTypeName;

	// 上限分数
	private BigDecimal upperLimitScore;

	// 备注
	private String remarks;

	// 排序
	private int sort = 0;

	// 业务扩展字段
	// 部门集合
	private JSONArray sysDeptArray;

	public JSONArray getSysDeptArray() {
		return sysDeptArray;
	}

	public void setSysDeptArray(JSONArray sysDeptArray) {
		this.sysDeptArray = sysDeptArray;
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

	public String getDepartmentId() {
		return departmentId == null ? "" : departmentId.trim();
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId == null ? null : departmentId.trim();
	}

	public String getDepartmentName() {
		return departmentName == null ? "" : departmentName.trim();
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName == null ? null : departmentName.trim();
	}

	public String getIsClosed() {
		return isClosed == null ? "" : isClosed.trim();
	}

	public void setIsClosed(String isClosed) {
		this.isClosed = isClosed == null ? null : isClosed.trim();
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

	public BigDecimal getUpperLimitScore() {
		return upperLimitScore;
	}

	public void setUpperLimitScore(BigDecimal upperLimitScore) {
		this.upperLimitScore = upperLimitScore;
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
