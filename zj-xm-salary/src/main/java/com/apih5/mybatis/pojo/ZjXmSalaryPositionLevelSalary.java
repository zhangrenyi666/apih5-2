package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjXmSalaryPositionLevelSalary extends BasePojo {
	private String levelSalaryId;

	private String projectType;

	private String positionType;

	private String positionLevel;

	private String positionGrade;

	private BigDecimal positionSalary;

	private String pid;

	private String pidAll;

	private String pnameAll;

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
	// 岗薪集合
	// private List<ZjXmSalaryPositionLevelSalary> positionSalaryList;

	public String getLevelSalaryId() {
		return levelSalaryId == null ? "" : levelSalaryId.trim();
	}

	public void setLevelSalaryId(String levelSalaryId) {
		this.levelSalaryId = levelSalaryId == null ? null : levelSalaryId.trim();
	}

	public String getProjectType() {
		return projectType == null ? "" : projectType.trim();
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType == null ? null : projectType.trim();
	}

	public String getPositionType() {
		return positionType == null ? "" : positionType.trim();
	}

	public void setPositionType(String positionType) {
		this.positionType = positionType == null ? null : positionType.trim();
	}

	public String getPositionLevel() {
		return positionLevel == null ? "" : positionLevel.trim();
	}

	public void setPositionLevel(String positionLevel) {
		this.positionLevel = positionLevel == null ? null : positionLevel.trim();
	}

	public String getPositionGrade() {
		return positionGrade == null ? "" : positionGrade.trim();
	}

	public void setPositionGrade(String positionGrade) {
		this.positionGrade = positionGrade == null ? null : positionGrade.trim();
	}

	public BigDecimal getPositionSalary() {
		return positionSalary;
	}

	public void setPositionSalary(BigDecimal positionSalary) {
		this.positionSalary = positionSalary;
	}

	public String getPid() {
		return pid == null ? "" : pid.trim();
	}

	public void setPid(String pid) {
		this.pid = pid == null ? null : pid.trim();
	}

	public String getPidAll() {
		return pidAll == null ? "" : pidAll.trim();
	}

	public void setPidAll(String pidAll) {
		this.pidAll = pidAll == null ? null : pidAll.trim();
	}

	public String getPnameAll() {
		return pnameAll == null ? "" : pnameAll.trim();
	}

	public void setPnameAll(String pnameAll) {
		this.pnameAll = pnameAll == null ? null : pnameAll.trim();
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
