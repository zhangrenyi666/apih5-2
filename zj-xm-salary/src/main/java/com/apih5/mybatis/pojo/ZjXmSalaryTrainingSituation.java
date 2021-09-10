package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjXmSalaryTrainingSituation extends BasePojo {
	private String trainingId;

	private String extensionId;

	private Date startDate;

	private Date endDate;

	private String trainingName;

	private String trainingType;

	private String trainingMode;

	private BigDecimal trainingHours;

	private String trainingResult;

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
	// 培训附件
	private List<ZjXmSalaryUserAttachment> trainingAttachmentList;

	public List<ZjXmSalaryUserAttachment> getTrainingAttachmentList() {
		return trainingAttachmentList;
	}

	public void setTrainingAttachmentList(List<ZjXmSalaryUserAttachment> trainingAttachmentList) {
		this.trainingAttachmentList = trainingAttachmentList;
	}

	public String getTrainingId() {
		return trainingId == null ? "" : trainingId.trim();
	}

	public void setTrainingId(String trainingId) {
		this.trainingId = trainingId == null ? null : trainingId.trim();
	}

	public String getExtensionId() {
		return extensionId == null ? "" : extensionId.trim();
	}

	public void setExtensionId(String extensionId) {
		this.extensionId = extensionId == null ? null : extensionId.trim();
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getTrainingName() {
		return trainingName == null ? "" : trainingName.trim();
	}

	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName == null ? null : trainingName.trim();
	}

	public String getTrainingType() {
		return trainingType == null ? "" : trainingType.trim();
	}

	public void setTrainingType(String trainingType) {
		this.trainingType = trainingType == null ? null : trainingType.trim();
	}

	public String getTrainingMode() {
		return trainingMode == null ? "" : trainingMode.trim();
	}

	public void setTrainingMode(String trainingMode) {
		this.trainingMode = trainingMode == null ? null : trainingMode.trim();
	}

	public BigDecimal getTrainingHours() {
		return trainingHours;
	}

	public void setTrainingHours(BigDecimal trainingHours) {
		this.trainingHours = trainingHours;
	}

	public String getTrainingResult() {
		return trainingResult;
	}

	public void setTrainingResult(String trainingResult) {
		this.trainingResult = trainingResult;
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
