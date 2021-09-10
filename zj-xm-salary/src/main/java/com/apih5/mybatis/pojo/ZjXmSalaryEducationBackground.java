package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjXmSalaryEducationBackground extends BasePojo {
	private String educationId;

	private String extensionId;

	private Date enrollmentDate;

	private Date graduateDate;

	private String graduateSchool;

	private String education;

	private String degree;

	private String major;

	private Date degreeAwardDate;

	private String isFirstEducation;

	private String isHighestEducation;

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
	// 学历附件
	private List<ZjXmSalaryUserAttachment> educationAttachmentList;
	// 学位附件
	private List<ZjXmSalaryUserAttachment> degreeAttachmentList;

	public List<ZjXmSalaryUserAttachment> getEducationAttachmentList() {
		return educationAttachmentList;
	}

	public void setEducationAttachmentList(List<ZjXmSalaryUserAttachment> educationAttachmentList) {
		this.educationAttachmentList = educationAttachmentList;
	}

	public List<ZjXmSalaryUserAttachment> getDegreeAttachmentList() {
		return degreeAttachmentList;
	}

	public void setDegreeAttachmentList(List<ZjXmSalaryUserAttachment> degreeAttachmentList) {
		this.degreeAttachmentList = degreeAttachmentList;
	}

	public String getEducationId() {
		return educationId == null ? "" : educationId.trim();
	}

	public void setEducationId(String educationId) {
		this.educationId = educationId == null ? null : educationId.trim();
	}

	public String getExtensionId() {
		return extensionId == null ? "" : extensionId.trim();
	}

	public void setExtensionId(String extensionId) {
		this.extensionId = extensionId == null ? null : extensionId.trim();
	}

	public Date getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(Date enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	public Date getGraduateDate() {
		return graduateDate;
	}

	public void setGraduateDate(Date graduateDate) {
		this.graduateDate = graduateDate;
	}

	public String getGraduateSchool() {
		return graduateSchool == null ? "" : graduateSchool.trim();
	}

	public void setGraduateSchool(String graduateSchool) {
		this.graduateSchool = graduateSchool == null ? null : graduateSchool.trim();
	}

	public String getEducation() {
		return education == null ? "" : education.trim();
	}

	public void setEducation(String education) {
		this.education = education == null ? null : education.trim();
	}

	public String getDegree() {
		return degree == null ? "" : degree.trim();
	}

	public void setDegree(String degree) {
		this.degree = degree == null ? null : degree.trim();
	}

	public String getMajor() {
		return major == null ? "" : major.trim();
	}

	public void setMajor(String major) {
		this.major = major == null ? null : major.trim();
	}

	public Date getDegreeAwardDate() {
		return degreeAwardDate;
	}

	public void setDegreeAwardDate(Date degreeAwardDate) {
		this.degreeAwardDate = degreeAwardDate;
	}

	public String getIsFirstEducation() {
		return isFirstEducation == null ? "" : isFirstEducation.trim();
	}

	public void setIsFirstEducation(String isFirstEducation) {
		this.isFirstEducation = isFirstEducation == null ? null : isFirstEducation.trim();
	}

	public String getIsHighestEducation() {
		return isHighestEducation == null ? "" : isHighestEducation.trim();
	}

	public void setIsHighestEducation(String isHighestEducation) {
		this.isHighestEducation = isHighestEducation == null ? null : isHighestEducation.trim();
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
