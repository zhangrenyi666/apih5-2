package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONArray;

import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.entity.BasePojo;

public class ZjXmJxUserScoringLeader extends BasePojo {
	private String scoringLeaderId;

	private String userKey;

	private String realName;

	private String deptId;

	private String deptName;

	private String projectId;

	private String projectName;

	private String leaderId;

	private String leaderName;

	private String remarks;

	private Integer sort;

	private String delFlag;

	private Date createTime;

	private String createUser;

	private String createUserName;

	private Date modifyTime;

	private String modifyUser;

	private String modifyUserName;

	// 评分领导
	private JSONArray leaderArray;

	// 人员集合
	private List<SysUser> userList;

	public List<SysUser> getUserList() {
		return userList;
	}

	public void setUserList(List<SysUser> userList) {
		this.userList = userList;
	}

	public JSONArray getLeaderArray() {
		return leaderArray;
	}

	public void setLeaderArray(JSONArray leaderArray) {
		this.leaderArray = leaderArray;
	}

	public String getScoringLeaderId() {
		return scoringLeaderId == null ? "" : scoringLeaderId.trim();
	}

	public void setScoringLeaderId(String scoringLeaderId) {
		this.scoringLeaderId = scoringLeaderId == null ? null : scoringLeaderId.trim();
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
