package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;

public class ZxCtContrDetail extends BasePojo {
	// 主键ID
	private String contrDetailId;

	// 合同ID
	private String contractID;

	// 工程类别
	private String projType;

	// 项目性质
	private String projQuality;

	// 合同英文名称
	private String ename;

	// 合同位置
	private String location;

	// 地区
	private String area;

	// 合同长度
	private String length;

	// 清单金额（元）
	private BigDecimal qdMoney;

	// 计日工金额（元）
	private BigDecimal jrgMoney;

	// 暂定金额（元）
	private BigDecimal zdMoney;

	// 外币币种
	private String forergnCurrencyType;

	// 外币比重
	private String forergnCurrencyProportion;

	// 外币汇率（%）
	private BigDecimal forergnCurrencyExchangeRate;

	// 车道数
	private int driveways = 0;

	// 公路级别
	private String roadLevel;

	// 动员预付款总额（元）
	private BigDecimal dyyfkMoney;

	// 动员预付款起扣点（元）
	private BigDecimal dyyfkqkdMoney;

	// 扣回动员预付款比例（%）
	private BigDecimal khdyyfkPercent;

	// 动员预付款全额扣回点（元）
	private BigDecimal dyyfkqekhdMoney;

	// 物资预付款比例（%）
	private BigDecimal clyfkPercent;

	// 物资预付款限额（元）
	private BigDecimal clyfkxeMoney;

	// 物资扣回款比例（%）
	private BigDecimal clkhkblPercent;

	// 迟扣款利息（%。）
	private BigDecimal ckklxPercent;

	// 迟扣款利息限额（元）
	private BigDecimal ckklxxeMoney;

	// 违约金限额（元）
	private BigDecimal wyjxeMoney;

	// 索赔金额限额（元）
	private BigDecimal cpjexeMoney;

	// 保留金起扣点（元）
	private BigDecimal bljqkdMoney;

	// 累计扣保留金限额（元）
	private BigDecimal ljkbljxeMoney;

	// 保留金比例（%）
	private BigDecimal bljblPercent;

	// 调整指数
	private int adjustIndx = 0;

	// 工程项目及工程数量
	private String projDetail;

	// 业主节点工期
	private String ownerNodeDeys;

	// 业主主要专用条款
	private String ownerSpecialClause;

	// 项目员工人数
	private String projPersonTotal;

	// 其中外聘人数
	private String projOtherPerson;

	// 是否历史项目
	private String isHistory;

	// combProp
	private String combProp;

	// pp1
	private String pp1;

	// pp2
	private String pp2;

	// pp3
	private String pp3;

	// pp4
	private String pp4;

	// pp5
	private String pp5;

	// pp6
	private String pp6;

	// pp7
	private String pp7;

	// pp8
	private String pp8;

	// pp9
	private String pp9;

	// pp10
	private String pp10;

	// 编辑时间
	private Date editTime;

	// 国家
	private String country;

	// 项目特征
	private String projFea;

	// 合同条款子表（保证金）
	private List<ZxCtContrDetailAttr> zxCtContrDetailAttrList;

	// 备注
	private String remark;

	// 排序
	private int sort = 0;

	public String getContrDetailId() {
		return contrDetailId == null ? "" : contrDetailId.trim();
	}

	public void setContrDetailId(String contrDetailId) {
		this.contrDetailId = contrDetailId == null ? null : contrDetailId.trim();
	}

	public String getContractID() {
		return contractID == null ? "" : contractID.trim();
	}

	public void setContractID(String contractID) {
		this.contractID = contractID == null ? null : contractID.trim();
	}

	public String getProjType() {
		return projType == null ? "" : projType.trim();
	}

	public void setProjType(String projType) {
		this.projType = projType == null ? null : projType.trim();
	}

	public String getProjQuality() {
		return projQuality == null ? "" : projQuality.trim();
	}

	public void setProjQuality(String projQuality) {
		this.projQuality = projQuality == null ? null : projQuality.trim();
	}

	public String getEname() {
		return ename == null ? "" : ename.trim();
	}

	public void setEname(String ename) {
		this.ename = ename == null ? null : ename.trim();
	}

	public String getLocation() {
		return location == null ? "" : location.trim();
	}

	public void setLocation(String location) {
		this.location = location == null ? null : location.trim();
	}

	public String getArea() {
		return area == null ? "" : area.trim();
	}

	public void setArea(String area) {
		this.area = area == null ? null : area.trim();
	}

	public String getLength() {
		return length == null ? "" : length.trim();
	}

	public void setLength(String length) {
		this.length = length == null ? null : length.trim();
	}

	public BigDecimal getQdMoney() {
		return qdMoney;
	}

	public void setQdMoney(BigDecimal qdMoney) {
		this.qdMoney = qdMoney;
	}

	public BigDecimal getJrgMoney() {
		return jrgMoney;
	}

	public void setJrgMoney(BigDecimal jrgMoney) {
		this.jrgMoney = jrgMoney;
	}

	public BigDecimal getZdMoney() {
		return zdMoney;
	}

	public void setZdMoney(BigDecimal zdMoney) {
		this.zdMoney = zdMoney;
	}

	public String getForergnCurrencyType() {
		return forergnCurrencyType == null ? "" : forergnCurrencyType.trim();
	}

	public void setForergnCurrencyType(String forergnCurrencyType) {
		this.forergnCurrencyType = forergnCurrencyType == null ? null : forergnCurrencyType.trim();
	}

	public String getForergnCurrencyProportion() {
		return forergnCurrencyProportion == null ? "" : forergnCurrencyProportion.trim();
	}

	public void setForergnCurrencyProportion(String forergnCurrencyProportion) {
		this.forergnCurrencyProportion = forergnCurrencyProportion == null ? null : forergnCurrencyProportion.trim();
	}

	public BigDecimal getForergnCurrencyExchangeRate() {
		return forergnCurrencyExchangeRate;
	}

	public void setForergnCurrencyExchangeRate(BigDecimal forergnCurrencyExchangeRate) {
		this.forergnCurrencyExchangeRate = forergnCurrencyExchangeRate;
	}

	public int getDriveways() {
		return driveways;
	}

	public void setDriveways(int driveways) {
		this.driveways = driveways;
	}

	public String getRoadLevel() {
		return roadLevel == null ? "" : roadLevel.trim();
	}

	public void setRoadLevel(String roadLevel) {
		this.roadLevel = roadLevel == null ? null : roadLevel.trim();
	}

	public BigDecimal getDyyfkMoney() {
		return dyyfkMoney;
	}

	public void setDyyfkMoney(BigDecimal dyyfkMoney) {
		this.dyyfkMoney = dyyfkMoney;
	}

	public BigDecimal getDyyfkqkdMoney() {
		return dyyfkqkdMoney;
	}

	public void setDyyfkqkdMoney(BigDecimal dyyfkqkdMoney) {
		this.dyyfkqkdMoney = dyyfkqkdMoney;
	}

	public BigDecimal getKhdyyfkPercent() {
		return khdyyfkPercent;
	}

	public void setKhdyyfkPercent(BigDecimal khdyyfkPercent) {
		this.khdyyfkPercent = khdyyfkPercent;
	}

	public BigDecimal getDyyfkqekhdMoney() {
		return dyyfkqekhdMoney;
	}

	public void setDyyfkqekhdMoney(BigDecimal dyyfkqekhdMoney) {
		this.dyyfkqekhdMoney = dyyfkqekhdMoney;
	}

	public BigDecimal getClyfkPercent() {
		return clyfkPercent;
	}

	public void setClyfkPercent(BigDecimal clyfkPercent) {
		this.clyfkPercent = clyfkPercent;
	}

	public BigDecimal getClyfkxeMoney() {
		return clyfkxeMoney;
	}

	public void setClyfkxeMoney(BigDecimal clyfkxeMoney) {
		this.clyfkxeMoney = clyfkxeMoney;
	}

	public BigDecimal getClkhkblPercent() {
		return clkhkblPercent;
	}

	public void setClkhkblPercent(BigDecimal clkhkblPercent) {
		this.clkhkblPercent = clkhkblPercent;
	}

	public BigDecimal getCkklxPercent() {
		return ckklxPercent;
	}

	public void setCkklxPercent(BigDecimal ckklxPercent) {
		this.ckklxPercent = ckklxPercent;
	}

	public BigDecimal getCkklxxeMoney() {
		return ckklxxeMoney;
	}

	public void setCkklxxeMoney(BigDecimal ckklxxeMoney) {
		this.ckklxxeMoney = ckklxxeMoney;
	}

	public BigDecimal getWyjxeMoney() {
		return wyjxeMoney;
	}

	public void setWyjxeMoney(BigDecimal wyjxeMoney) {
		this.wyjxeMoney = wyjxeMoney;
	}

	public BigDecimal getCpjexeMoney() {
		return cpjexeMoney;
	}

	public void setCpjexeMoney(BigDecimal cpjexeMoney) {
		this.cpjexeMoney = cpjexeMoney;
	}

	public BigDecimal getBljqkdMoney() {
		return bljqkdMoney;
	}

	public void setBljqkdMoney(BigDecimal bljqkdMoney) {
		this.bljqkdMoney = bljqkdMoney;
	}

	public BigDecimal getLjkbljxeMoney() {
		return ljkbljxeMoney;
	}

	public void setLjkbljxeMoney(BigDecimal ljkbljxeMoney) {
		this.ljkbljxeMoney = ljkbljxeMoney;
	}

	public BigDecimal getBljblPercent() {
		return bljblPercent;
	}

	public void setBljblPercent(BigDecimal bljblPercent) {
		this.bljblPercent = bljblPercent;
	}

	public int getAdjustIndx() {
		return adjustIndx;
	}

	public void setAdjustIndx(int adjustIndx) {
		this.adjustIndx = adjustIndx;
	}

	public String getProjDetail() {
		return projDetail == null ? "" : projDetail.trim();
	}

	public void setProjDetail(String projDetail) {
		this.projDetail = projDetail == null ? null : projDetail.trim();
	}

	public String getOwnerNodeDeys() {
		return ownerNodeDeys == null ? "" : ownerNodeDeys.trim();
	}

	public void setOwnerNodeDeys(String ownerNodeDeys) {
		this.ownerNodeDeys = ownerNodeDeys == null ? null : ownerNodeDeys.trim();
	}

	public String getOwnerSpecialClause() {
		return ownerSpecialClause == null ? "" : ownerSpecialClause.trim();
	}

	public void setOwnerSpecialClause(String ownerSpecialClause) {
		this.ownerSpecialClause = ownerSpecialClause == null ? null : ownerSpecialClause.trim();
	}

	public String getProjPersonTotal() {
		return projPersonTotal == null ? "" : projPersonTotal.trim();
	}

	public void setProjPersonTotal(String projPersonTotal) {
		this.projPersonTotal = projPersonTotal == null ? null : projPersonTotal.trim();
	}

	public String getProjOtherPerson() {
		return projOtherPerson == null ? "" : projOtherPerson.trim();
	}

	public void setProjOtherPerson(String projOtherPerson) {
		this.projOtherPerson = projOtherPerson == null ? null : projOtherPerson.trim();
	}

	public String getIsHistory() {
		return isHistory == null ? "" : isHistory.trim();
	}

	public void setIsHistory(String isHistory) {
		this.isHistory = isHistory == null ? null : isHistory.trim();
	}

	public String getCombProp() {
		return combProp == null ? "" : combProp.trim();
	}

	public void setCombProp(String combProp) {
		this.combProp = combProp == null ? null : combProp.trim();
	}

	public String getPp1() {
		return pp1 == null ? "" : pp1.trim();
	}

	public void setPp1(String pp1) {
		this.pp1 = pp1 == null ? null : pp1.trim();
	}

	public String getPp2() {
		return pp2 == null ? "" : pp2.trim();
	}

	public void setPp2(String pp2) {
		this.pp2 = pp2 == null ? null : pp2.trim();
	}

	public String getPp3() {
		return pp3 == null ? "" : pp3.trim();
	}

	public void setPp3(String pp3) {
		this.pp3 = pp3 == null ? null : pp3.trim();
	}

	public String getPp4() {
		return pp4 == null ? "" : pp4.trim();
	}

	public void setPp4(String pp4) {
		this.pp4 = pp4 == null ? null : pp4.trim();
	}

	public String getPp5() {
		return pp5 == null ? "" : pp5.trim();
	}

	public void setPp5(String pp5) {
		this.pp5 = pp5 == null ? null : pp5.trim();
	}

	public String getPp6() {
		return pp6 == null ? "" : pp6.trim();
	}

	public void setPp6(String pp6) {
		this.pp6 = pp6 == null ? null : pp6.trim();
	}

	public String getPp7() {
		return pp7 == null ? "" : pp7.trim();
	}

	public void setPp7(String pp7) {
		this.pp7 = pp7 == null ? null : pp7.trim();
	}

	public String getPp8() {
		return pp8 == null ? "" : pp8.trim();
	}

	public void setPp8(String pp8) {
		this.pp8 = pp8 == null ? null : pp8.trim();
	}

	public String getPp9() {
		return pp9 == null ? "" : pp9.trim();
	}

	public void setPp9(String pp9) {
		this.pp9 = pp9 == null ? null : pp9.trim();
	}

	public String getPp10() {
		return pp10 == null ? "" : pp10.trim();
	}

	public void setPp10(String pp10) {
		this.pp10 = pp10 == null ? null : pp10.trim();
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public String getCountry() {
		return country == null ? "" : country.trim();
	}

	public void setCountry(String country) {
		this.country = country == null ? null : country.trim();
	}

	public String getProjFea() {
		return projFea == null ? "" : projFea.trim();
	}

	public void setProjFea(String projFea) {
		this.projFea = projFea == null ? null : projFea.trim();
	}

	public List<ZxCtContrDetailAttr> getZxCtContrDetailAttrList() {
		return zxCtContrDetailAttrList == null ? Lists.newArrayList() : zxCtContrDetailAttrList;
	}

	public void setZxCtContrDetailAttrList(List<ZxCtContrDetailAttr> zxCtContrDetailAttrList) {
		this.zxCtContrDetailAttrList = zxCtContrDetailAttrList == null ? null : zxCtContrDetailAttrList;
	}

	public String getRemark() {
		return remark == null ? "" : remark.trim();
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
