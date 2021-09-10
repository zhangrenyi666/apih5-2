package com.apih5.entity;

import java.util.Date;

/**
 * 账户信息Form
 * 
 * @author www.weidingplus.com
 *
 */
public class AccountInfoForm {

	/**
	 * 公众号ID
	 */
	private String id;

	/**
	 * 企业名称
	 */
	private String name;

	/**
	 * 企业所属行业
	 */
	private String trade;

	/**
	 * 企业所属省
	 */
	private String province;

	/**
	 * 企业所属市
	 */
	private String city;

	/**
	 * 企业所属区
	 */
	private String district;

	/**
	 * 企业详细地址
	 */
	private String address;

	/**
	 * 公众号TOKEN
	 */
	private String token;

	/**
	 * 微信号
	 */
	private String wechatId;

	/**
	 * 公众类型区分CODE
	 */
	private String difCode;

	/**
	 * 公众号描述
	 */
	private String description;

	/**
	 * 公众号CorpID
	 */
	private String corpId;

	/**
	 * 公众号CorpSecret
	 */
	private String corpSecret;

	/**
	 * 公众号ACCESSTOKEN
	 */
	private String accessToken;

	/**
	 * 原始ID
	 */
	private String originalId;

	/**
	 * 邮箱
	 */
	private String email;

	private String modifyTimeStr;

	private Date modifyTime;

	public String getId() {
		return id == null ? "" : id.trim();
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name == null ? "" : name.trim();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getToken() {
		return token == null ? "" : token.trim();
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getWechatId() {
		return wechatId == null ? "" : wechatId.trim();
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getDifCode() {
		return difCode == null ? "" : difCode.trim();
	}

	public void setDifCode(String difCode) {
		this.difCode = difCode;
	}

	public String getDescription() {
		return description == null ? "" : description.trim();
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCorpId() {
		return corpId == null ? "" : corpId.trim();
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public String getCorpSecret() {
		return corpSecret == null ? "" : corpSecret.trim();
	}

	public void setCorpSecret(String corpSecret) {
		this.corpSecret = corpSecret;
	}

	public String getAccessToken() {
		return accessToken == null ? "" : accessToken.trim();
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getOriginalId() {
		return originalId == null ? "" : originalId.trim();
	}

	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}

	public String getEmail() {
		return email == null ? "" : email.trim();
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProvince() {
		return province == null ? "" : province.trim();
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city == null ? "" : city.trim();
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district == null ? "" : district.trim();
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return address == null ? "" : address.trim();
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTrade() {
		return trade == null ? "" : trade.trim();
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	public String getModifyTimeStr() {
		return modifyTimeStr == null ? "" : modifyTimeStr;
	}

	public void setModifyTimeStr(String modifyTimeStr) {
		this.modifyTimeStr = modifyTimeStr;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

}
