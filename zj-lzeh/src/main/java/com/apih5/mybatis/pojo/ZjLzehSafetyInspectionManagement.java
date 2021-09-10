package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;

public class ZjLzehSafetyInspectionManagement extends BasePojo {
	private String safetyInspectionManagementId;

	private Integer orderFlag;

	private String name;

	private Integer totalNumber;

	private Integer removeNumber;

	private String delFlag;

	private Date createTime;

	private String createUser;

	private String createUserName;

	private Date modifyTime;

	private String modifyUser;

	private String modifyUserName;

	private String content;

	private Integer count;

	private Integer total;

	private String dangerlevel;

	private String troublelevel;

	private Integer solved;

	private Integer unsolved;

	private String opinionField2;

	private String opinionField3;

	private String troubleId;

	private String troubleTitle;

	private String troubleContent;

	private String troubleRequire;

	private String dangerId;

	private String dangerTitle;

	private String dangerContent;

	private String dangerRequire;

	private List<ZjLzehFile> fileList;

	public String getDangerTitle() {
		return dangerTitle;
	}

	public void setDangerTitle(String dangerTitle) {
		this.dangerTitle = dangerTitle;
	}

	public String getDangerContent() {
		return dangerContent;
	}

	public void setDangerContent(String dangerContent) {
		this.dangerContent = dangerContent;
	}

	public String getDangerRequire() {
		return dangerRequire;
	}

	public void setDangerRequire(String dangerRequire) {
		this.dangerRequire = dangerRequire;
	}

	public String getTroubleContent() {
		return troubleContent;
	}

	public void setTroubleContent(String troubleContent) {
		this.troubleContent = troubleContent;
	}

	public String getTroubleRequire() {
		return troubleRequire;
	}

	public void setTroubleRequire(String troubleRequire) {
		this.troubleRequire = troubleRequire;
	}

	public String getOpinionField2() {
		return opinionField2;
	}

	public void setOpinionField2(String opinionField2) {
		this.opinionField2 = opinionField2;
	}

	public String getOpinionField3() {
		return opinionField3;
	}

	public void setOpinionField3(String opinionField3) {
		this.opinionField3 = opinionField3;
	}

	public String getTroubleTitle() {
		return troubleTitle;
	}

	public void setTroubleTitle(String troubleTitle) {
		this.troubleTitle = troubleTitle;
	}

	public String getDangerlevel() {
		return dangerlevel;
	}

	public void setDangerlevel(String dangerlevel) {
		this.dangerlevel = dangerlevel;
	}

	public String getTroublelevel() {
		return troublelevel;
	}

	public void setTroublelevel(String troublelevel) {
		this.troublelevel = troublelevel;
	}

	public Integer getSolved() {
		return solved;
	}

	public void setSolved(Integer solved) {
		this.solved = solved;
	}

	public Integer getUnsolved() {
		return unsolved;
	}

	public void setUnsolved(Integer unsolved) {
		this.unsolved = unsolved;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public String getSafetyInspectionManagementId() {
		return safetyInspectionManagementId == null ? "" : safetyInspectionManagementId.trim();
	}

	public void setSafetyInspectionManagementId(String safetyInspectionManagementId) {
		this.safetyInspectionManagementId = safetyInspectionManagementId == null ? null
				: safetyInspectionManagementId.trim();
	}

	public Integer getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(Integer orderFlag) {
		this.orderFlag = orderFlag;
	}

	public String getName() {
		return name == null ? "" : name.trim();
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}

	public Integer getRemoveNumber() {
		return removeNumber;
	}

	public void setRemoveNumber(Integer removeNumber) {
		this.removeNumber = removeNumber;
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

	public List<ZjLzehFile> getFileList() {
		return fileList;
	}

	public void setFileList(List<ZjLzehFile> fileList) {
		this.fileList = fileList;
	}

	public String getTroubleId() {
		return troubleId;
	}

	public void setTroubleId(String troubleId) {
		this.troubleId = troubleId;
	}

	public String getDangerId() {
		return dangerId;
	}

	public void setDangerId(String dangerId) {
		this.dangerId = dangerId;
	}

}
