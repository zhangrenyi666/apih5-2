package com.apih5.entity;

/**
 * 用户entity
 */
public class SysUserOtherEntity {
	private String userId;

	private String realName;

	private String mobile;

	private String accountCorpId;

	private String accountAppType;

	private String userType;

	// 第三方用户使用
	private String otherCorpId;

	// 第三方用户使用
	private String otherCorpSecret;

	private String token;
	
	private String loginId;

	public String getUserId() {
		return userId;
	}

	public String getRealName() {
		return realName;
	}

	public String getMobile() {
		return mobile;
	}

	public String getAccountCorpId() {
		return accountCorpId;
	}

	public String getAccountAppType() {
		return accountAppType;
	}

	public String getUserType() {
		return userType;
	}

	public String getOtherCorpId() {
		return otherCorpId;
	}

	public String getOtherCorpSecret() {
		return otherCorpSecret;
	}

	public String getToken() {
		return token;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setAccountCorpId(String accountCorpId) {
		this.accountCorpId = accountCorpId;
	}

	public void setAccountAppType(String accountAppType) {
		this.accountAppType = accountAppType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public void setOtherCorpId(String otherCorpId) {
		this.otherCorpId = otherCorpId;
	}

	public void setOtherCorpSecret(String otherCorpSecret) {
		this.otherCorpSecret = otherCorpSecret;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
}
