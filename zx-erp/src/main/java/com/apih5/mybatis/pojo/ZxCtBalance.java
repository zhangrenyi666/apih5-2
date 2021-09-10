package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;

public class ZxCtBalance extends BasePojo {
	// 附件
	private List<ZxErpFile> attachment;

	// 主键ID
	private String id;

	// 项目ID
	private String orgID;

	// 项目ID
	private String workBookID;

	// 合同ID
	private String contractID;

	// 编号
	private String balNo;

	// 计量单类型
	private String balType;

	// 计量时间
	private String period;

	// 开累计量(元)
	private BigDecimal totalbalAmt;

	// 本期计量(元)
	private BigDecimal balAmt;

	// 日期
	private Date busDate;

	// 结算期限开始时间
	private Date startDate;

	// 结算期限结束时间
	private Date endDate;

	// 业务日期
	private Date createDate;

	// 记录人
	private String reporter;

	// 审核人
	private String auditor;

	// 审核日期
	private Date auditDate;

	// 审核意见
	private String auditMsg;

	// 评审状态
	private String auditStatus;

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

	// 编号
	private String no;

	// 申请日期
	private Date applyDate;

	// 驻地监理确认日期
	private Date stationRlyDate;

	// 总监确认日期
	private Date majorRlyDate;

	// 业主批复日期
	private Date ownerRlyDate;

	// 申请金额
	private BigDecimal applyamt;

	// 驻地监理确认金额
	private BigDecimal stationRlyamt;

	// 总监确定金额
	private BigDecimal majorRlyamt;

	// 业主批复金额
	private BigDecimal ownerRlyamt;

	// 最后编辑时间
	private Date editTime;

	// 期次
	private String caption;

	// 统一社会信用代码
	private String orgCertificate;

	// 本期应付金额(元)
	private BigDecimal thisPayAmt;

	// 开累应付金额(元)
	private BigDecimal totalPayAmt;

	// 本期结算金额(元)
	private BigDecimal thisAmt;

	// 本期结算不含税金额(元)
	private BigDecimal thisAmtNoTax;

	// 本期结算税额(元)
	private BigDecimal thisAmtTax;

	// 开累结算金额(元)
	private BigDecimal totalAmt;

	// 结算金额差值
	private BigDecimal tcje;

	// 税额差值
	private BigDecimal tcse;

	// 调差后累计结算金额
	private BigDecimal tchljjsje;

	// 本期调差后税额
	private BigDecimal bqtchse;

	// 本期调差后结算金额
	private BigDecimal bqtchjsje;

	// 截止到上期累计金额(元)
	private BigDecimal upTotalAmt;

	// zjgcxm_xmbh
	private String zjgcxmXmbh;

	// zjgcxm_nm
	private String zjgcxmNm;

	// 项目
	private String zjgcxmXmmc;

	// 填报人
	private String reportPerson;

	// 结算方向
	private String setDir;

	// 核算单位内码
	private String accountUnitId;

	// 核算单位编号
	private String accountUnitCode;

	// 核算单位名称
	private String accountUnitName;

	// 核算部门内码
	private String accountDepId;

	// 核算部门编号
	private String accountDepCode;

	// 核算部门名称
	private String accountDepName;

	// 是否签认
	private String isSign;

	// 债权人编号
	private String secondCode;

	// 债权人编号
	private String secondIDCodeBh;

	// 计量确认日期
	private Date confirmDate;

	// 币种
	private String currency;

	// 汇率
	private String exchangeRate;

	// 计税方法
	private String taxCountWay;

	// 附件张数
	private String numOfSheets;

	// 摘要
	private String summary;

	// 预计付款日期
	private Date estPayDate;

	// 到期日期
	private Date expDate;

	// 计算人
	private String countPerson;

	// 推送状态
	private String isSend;

	// 所属公司ID
	private String comID;

	// 推送时间
	private Date sendDate;

	// 财务审批状态说明
	private String cwStatusRemark;

	// 所属公司名称
	private String comName;

	// 复核人
	private String reCountPerson;

	// 上期为计量（元）
	private BigDecimal totalLastBalAmt;

	// 备注
	private String remarks;

	// 排序
	private int sort = 0;

	public List<ZxErpFile> getAttachment() {
		return attachment == null ? Lists.newArrayList() : attachment;
	}

	public void setAttachment(List<ZxErpFile> attachment) {
		this.attachment = attachment == null ? null : attachment;
	}

	public String getId() {
		return id == null ? "" : id.trim();
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getOrgID() {
		return orgID == null ? "" : orgID.trim();
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID == null ? null : orgID.trim();
	}

	public String getWorkBookID() {
		return workBookID == null ? "" : workBookID.trim();
	}

	public void setWorkBookID(String workBookID) {
		this.workBookID = workBookID == null ? null : workBookID.trim();
	}

	public String getContractID() {
		return contractID == null ? "" : contractID.trim();
	}

	public void setContractID(String contractID) {
		this.contractID = contractID == null ? null : contractID.trim();
	}

	public String getBalNo() {
		return balNo == null ? "" : balNo.trim();
	}

	public void setBalNo(String balNo) {
		this.balNo = balNo == null ? null : balNo.trim();
	}

	public String getBalType() {
		return balType == null ? "" : balType.trim();
	}

	public void setBalType(String balType) {
		this.balType = balType == null ? null : balType.trim();
	}

	public String getPeriod() {
		return period == null ? "" : period.trim();
	}

	public void setPeriod(String period) {
		this.period = period == null ? null : period.trim();
	}

	public BigDecimal getTotalbalAmt() {
		return totalbalAmt;
	}

	public void setTotalbalAmt(BigDecimal totalbalAmt) {
		this.totalbalAmt = totalbalAmt;
	}

	public BigDecimal getBalAmt() {
		return balAmt;
	}

	public void setBalAmt(BigDecimal balAmt) {
		this.balAmt = balAmt;
	}

	public Date getBusDate() {
		return busDate;
	}

	public void setBusDate(Date busDate) {
		this.busDate = busDate;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getReporter() {
		return reporter == null ? "" : reporter.trim();
	}

	public void setReporter(String reporter) {
		this.reporter = reporter == null ? null : reporter.trim();
	}

	public String getAuditor() {
		return auditor == null ? "" : auditor.trim();
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor == null ? null : auditor.trim();
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getAuditMsg() {
		return auditMsg == null ? "" : auditMsg.trim();
	}

	public void setAuditMsg(String auditMsg) {
		this.auditMsg = auditMsg == null ? null : auditMsg.trim();
	}

	public String getAuditStatus() {
		return auditStatus == null ? "" : auditStatus.trim();
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus == null ? null : auditStatus.trim();
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

	public String getNo() {
		return no == null ? "" : no.trim();
	}

	public void setNo(String no) {
		this.no = no == null ? null : no.trim();
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getStationRlyDate() {
		return stationRlyDate;
	}

	public void setStationRlyDate(Date stationRlyDate) {
		this.stationRlyDate = stationRlyDate;
	}

	public Date getMajorRlyDate() {
		return majorRlyDate;
	}

	public void setMajorRlyDate(Date majorRlyDate) {
		this.majorRlyDate = majorRlyDate;
	}

	public Date getOwnerRlyDate() {
		return ownerRlyDate;
	}

	public void setOwnerRlyDate(Date ownerRlyDate) {
		this.ownerRlyDate = ownerRlyDate;
	}

	public BigDecimal getApplyamt() {
		return applyamt;
	}

	public void setApplyamt(BigDecimal applyamt) {
		this.applyamt = applyamt;
	}

	public BigDecimal getStationRlyamt() {
		return stationRlyamt;
	}

	public void setStationRlyamt(BigDecimal stationRlyamt) {
		this.stationRlyamt = stationRlyamt;
	}

	public BigDecimal getMajorRlyamt() {
		return majorRlyamt;
	}

	public void setMajorRlyamt(BigDecimal majorRlyamt) {
		this.majorRlyamt = majorRlyamt;
	}

	public BigDecimal getOwnerRlyamt() {
		return ownerRlyamt;
	}

	public void setOwnerRlyamt(BigDecimal ownerRlyamt) {
		this.ownerRlyamt = ownerRlyamt;
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public String getCaption() {
		return caption == null ? "" : caption.trim();
	}

	public void setCaption(String caption) {
		this.caption = caption == null ? null : caption.trim();
	}

	public String getOrgCertificate() {
		return orgCertificate == null ? "" : orgCertificate.trim();
	}

	public void setOrgCertificate(String orgCertificate) {
		this.orgCertificate = orgCertificate == null ? null : orgCertificate.trim();
	}

	public BigDecimal getThisPayAmt() {
		return thisPayAmt;
	}

	public void setThisPayAmt(BigDecimal thisPayAmt) {
		this.thisPayAmt = thisPayAmt;
	}

	public BigDecimal getTotalPayAmt() {
		return totalPayAmt;
	}

	public void setTotalPayAmt(BigDecimal totalPayAmt) {
		this.totalPayAmt = totalPayAmt;
	}

	public BigDecimal getThisAmt() {
		return thisAmt;
	}

	public void setThisAmt(BigDecimal thisAmt) {
		this.thisAmt = thisAmt;
	}

	public BigDecimal getThisAmtNoTax() {
		return thisAmtNoTax;
	}

	public void setThisAmtNoTax(BigDecimal thisAmtNoTax) {
		this.thisAmtNoTax = thisAmtNoTax;
	}

	public BigDecimal getThisAmtTax() {
		return thisAmtTax;
	}

	public void setThisAmtTax(BigDecimal thisAmtTax) {
		this.thisAmtTax = thisAmtTax;
	}

	public BigDecimal getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

	public BigDecimal getTcje() {
		return tcje;
	}

	public void setTcje(BigDecimal tcje) {
		this.tcje = tcje;
	}

	public BigDecimal getTcse() {
		return tcse;
	}

	public void setTcse(BigDecimal tcse) {
		this.tcse = tcse;
	}

	public BigDecimal getTchljjsje() {
		return tchljjsje;
	}

	public void setTchljjsje(BigDecimal tchljjsje) {
		this.tchljjsje = tchljjsje;
	}

	public BigDecimal getBqtchse() {
		return bqtchse;
	}

	public void setBqtchse(BigDecimal bqtchse) {
		this.bqtchse = bqtchse;
	}

	public BigDecimal getBqtchjsje() {
		return bqtchjsje;
	}

	public void setBqtchjsje(BigDecimal bqtchjsje) {
		this.bqtchjsje = bqtchjsje;
	}

	public BigDecimal getUpTotalAmt() {
		return upTotalAmt;
	}

	public void setUpTotalAmt(BigDecimal upTotalAmt) {
		this.upTotalAmt = upTotalAmt;
	}

	public String getZjgcxmXmbh() {
		return zjgcxmXmbh == null ? "" : zjgcxmXmbh.trim();
	}

	public void setZjgcxmXmbh(String zjgcxmXmbh) {
		this.zjgcxmXmbh = zjgcxmXmbh == null ? null : zjgcxmXmbh.trim();
	}

	public String getZjgcxmNm() {
		return zjgcxmNm == null ? "" : zjgcxmNm.trim();
	}

	public void setZjgcxmNm(String zjgcxmNm) {
		this.zjgcxmNm = zjgcxmNm == null ? null : zjgcxmNm.trim();
	}

	public String getZjgcxmXmmc() {
		return zjgcxmXmmc == null ? "" : zjgcxmXmmc.trim();
	}

	public void setZjgcxmXmmc(String zjgcxmXmmc) {
		this.zjgcxmXmmc = zjgcxmXmmc == null ? null : zjgcxmXmmc.trim();
	}

	public String getReportPerson() {
		return reportPerson == null ? "" : reportPerson.trim();
	}

	public void setReportPerson(String reportPerson) {
		this.reportPerson = reportPerson == null ? null : reportPerson.trim();
	}

	public String getSetDir() {
		return setDir == null ? "" : setDir.trim();
	}

	public void setSetDir(String setDir) {
		this.setDir = setDir == null ? null : setDir.trim();
	}

	public String getAccountUnitId() {
		return accountUnitId == null ? "" : accountUnitId.trim();
	}

	public void setAccountUnitId(String accountUnitId) {
		this.accountUnitId = accountUnitId == null ? null : accountUnitId.trim();
	}

	public String getAccountUnitCode() {
		return accountUnitCode == null ? "" : accountUnitCode.trim();
	}

	public void setAccountUnitCode(String accountUnitCode) {
		this.accountUnitCode = accountUnitCode == null ? null : accountUnitCode.trim();
	}

	public String getAccountUnitName() {
		return accountUnitName == null ? "" : accountUnitName.trim();
	}

	public void setAccountUnitName(String accountUnitName) {
		this.accountUnitName = accountUnitName == null ? null : accountUnitName.trim();
	}

	public String getAccountDepId() {
		return accountDepId == null ? "" : accountDepId.trim();
	}

	public void setAccountDepId(String accountDepId) {
		this.accountDepId = accountDepId == null ? null : accountDepId.trim();
	}

	public String getAccountDepCode() {
		return accountDepCode == null ? "" : accountDepCode.trim();
	}

	public void setAccountDepCode(String accountDepCode) {
		this.accountDepCode = accountDepCode == null ? null : accountDepCode.trim();
	}

	public String getAccountDepName() {
		return accountDepName == null ? "" : accountDepName.trim();
	}

	public void setAccountDepName(String accountDepName) {
		this.accountDepName = accountDepName == null ? null : accountDepName.trim();
	}

	public String getIsSign() {
		return isSign == null ? "" : isSign.trim();
	}

	public void setIsSign(String isSign) {
		this.isSign = isSign == null ? null : isSign.trim();
	}

	public String getSecondCode() {
		return secondCode == null ? "" : secondCode.trim();
	}

	public void setSecondCode(String secondCode) {
		this.secondCode = secondCode == null ? null : secondCode.trim();
	}

	public String getSecondIDCodeBh() {
		return secondIDCodeBh == null ? "" : secondIDCodeBh.trim();
	}

	public void setSecondIDCodeBh(String secondIDCodeBh) {
		this.secondIDCodeBh = secondIDCodeBh == null ? null : secondIDCodeBh.trim();
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public String getCurrency() {
		return currency == null ? "" : currency.trim();
	}

	public void setCurrency(String currency) {
		this.currency = currency == null ? null : currency.trim();
	}

	public String getExchangeRate() {
		return exchangeRate == null ? "" : exchangeRate.trim();
	}

	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate == null ? null : exchangeRate.trim();
	}

	public String getTaxCountWay() {
		return taxCountWay == null ? "" : taxCountWay.trim();
	}

	public void setTaxCountWay(String taxCountWay) {
		this.taxCountWay = taxCountWay == null ? null : taxCountWay.trim();
	}

	public String getNumOfSheets() {
		return numOfSheets == null ? "" : numOfSheets.trim();
	}

	public void setNumOfSheets(String numOfSheets) {
		this.numOfSheets = numOfSheets == null ? null : numOfSheets.trim();
	}

	public String getSummary() {
		return summary == null ? "" : summary.trim();
	}

	public void setSummary(String summary) {
		this.summary = summary == null ? null : summary.trim();
	}

	public Date getEstPayDate() {
		return estPayDate;
	}

	public void setEstPayDate(Date estPayDate) {
		this.estPayDate = estPayDate;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	public String getCountPerson() {
		return countPerson == null ? "" : countPerson.trim();
	}

	public void setCountPerson(String countPerson) {
		this.countPerson = countPerson == null ? null : countPerson.trim();
	}

	public String getIsSend() {
		return isSend == null ? "" : isSend.trim();
	}

	public void setIsSend(String isSend) {
		this.isSend = isSend == null ? null : isSend.trim();
	}

	public String getComID() {
		return comID == null ? "" : comID.trim();
	}

	public void setComID(String comID) {
		this.comID = comID == null ? null : comID.trim();
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getCwStatusRemark() {
		return cwStatusRemark == null ? "" : cwStatusRemark.trim();
	}

	public void setCwStatusRemark(String cwStatusRemark) {
		this.cwStatusRemark = cwStatusRemark == null ? null : cwStatusRemark.trim();
	}

	public String getComName() {
		return comName == null ? "" : comName.trim();
	}

	public void setComName(String comName) {
		this.comName = comName == null ? null : comName.trim();
	}

	public String getReCountPerson() {
		return reCountPerson == null ? "" : reCountPerson.trim();
	}

	public void setReCountPerson(String reCountPerson) {
		this.reCountPerson = reCountPerson == null ? null : reCountPerson.trim();
	}

	public BigDecimal getTotalLastBalAmt() {
		return totalLastBalAmt;
	}

	public void setTotalLastBalAmt(BigDecimal totalLastBalAmt) {
		this.totalLastBalAmt = totalLastBalAmt;
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
