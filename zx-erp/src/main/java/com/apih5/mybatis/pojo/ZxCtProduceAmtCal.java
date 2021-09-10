package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;

public class ZxCtProduceAmtCal extends BasePojo {
	// 主键
	private String id;

	// 项目ID（关联ID）
	private String orgID;

	// 项目名称
	private String orgName;

	// 期次
	private String period;

	// 产值
	private BigDecimal produceAmt;

	// 登记人
	private String reporter;

	// 附件
	private List<ZxErpFile> attachment;

	// 期次
	private Date periodDate;

	// 备注
	private String remarks;

	// 排序
	private int sort = 0;

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

	public String getOrgName() {
		return orgName == null ? "" : orgName.trim();
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName == null ? null : orgName.trim();
	}

	public String getPeriod() {
		return period == null ? "" : period.trim();
	}

	public void setPeriod(String period) {
		this.period = period == null ? null : period.trim();
	}

	public BigDecimal getProduceAmt() {
		return produceAmt;
	}

	public void setProduceAmt(BigDecimal produceAmt) {
		this.produceAmt = produceAmt;
	}

	public String getReporter() {
		return reporter == null ? "" : reporter.trim();
	}

	public void setReporter(String reporter) {
		this.reporter = reporter == null ? null : reporter.trim();
	}

	public List<ZxErpFile> getAttachment() {
		return attachment == null ? Lists.newArrayList() : attachment;
	}

	public void setAttachment(List<ZxErpFile> attachment) {
		this.attachment = attachment == null ? null : attachment;
	}

	public Date getPeriodDate() {
		return periodDate;
	}

	public void setPeriodDate(Date periodDate) {
		this.periodDate = periodDate;
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
