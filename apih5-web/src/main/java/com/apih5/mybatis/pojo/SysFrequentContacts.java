package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;

public class SysFrequentContacts extends BasePojo {
	private String frequentContactsId;

	private String loginUserKey;

	private String userKey;

	private String userId;

	private String realName;

	private String mobile;

	private String identityCard;

	private String gender;

	private String departmentId;

	private String departmentName;

	private String showDepartmentName;

	private Date expirationDate;

	private Integer count;

	private String delFlag;

	private Date createTime;

	private String createUser;

	private String createUserName;

	private Date modifyTime;

	private String modifyUser;

	private String modifyUserName;

	private List<SysFrequentContacts> sysFrequentContactsList;

	private Date startDate;

	private Date endDate;

	private String value;

	private String label;

	private String type;

	private String valuePid;

	public String getValuePid() {
		return valuePid;
	}

	public void setValuePid(String valuePid) {
		this.valuePid = valuePid;
	}

	public String getValue() {
		return value == null ? "" : value.trim();
	}

	public void setValue(String value) {
		this.value = value == null ? null : value.trim();
	}

	public String getLabel() {
		return label == null ? "" : label.trim();
	}

	public void setLabel(String label) {
		this.label = label == null ? null : label.trim();
	}

	public String getType() {
		return type == null ? "" : type.trim();
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
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

	public List<SysFrequentContacts> getSysFrequentContactsList() {
		return sysFrequentContactsList;
	}

	public void setSysFrequentContactsList(List<SysFrequentContacts> sysFrequentContactsList) {
		this.sysFrequentContactsList = sysFrequentContactsList;
	}

	public String getFrequentContactsId() {
		return frequentContactsId == null ? "" : frequentContactsId.trim();
	}

	public void setFrequentContactsId(String frequentContactsId) {
		this.frequentContactsId = frequentContactsId == null ? null : frequentContactsId.trim();
	}

	public String getLoginUserKey() {
		return loginUserKey == null ? "" : loginUserKey.trim();
	}

	public void setLoginUserKey(String loginUserKey) {
		this.loginUserKey = loginUserKey == null ? null : loginUserKey.trim();
	}

	public String getUserKey() {
		return userKey == null ? "" : userKey.trim();
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey == null ? null : userKey.trim();
	}

	public String getUserId() {
		return userId == null ? "" : userId.trim();
	}

	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	public String getRealName() {
		return realName == null ? "" : realName.trim();
	}

	public void setRealName(String realName) {
		this.realName = realName == null ? null : realName.trim();
	}

	public String getMobile() {
		return mobile == null ? "" : mobile.trim();
	}

	public void setMobile(String mobile) {
		this.mobile = mobile == null ? null : mobile.trim();
	}

	public String getIdentityCard() {
		return identityCard == null ? "" : identityCard.trim();
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard == null ? null : identityCard.trim();
	}

	public String getGender() {
		return gender == null ? "" : gender.trim();
	}

	public void setGender(String gender) {
		this.gender = gender == null ? null : gender.trim();
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getShowDepartmentName() {
		return showDepartmentName;
	}

	public void setShowDepartmentName(String showDepartmentName) {
		this.showDepartmentName = showDepartmentName;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
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
