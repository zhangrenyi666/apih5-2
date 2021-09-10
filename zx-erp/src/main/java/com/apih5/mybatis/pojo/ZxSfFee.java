package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfFee extends BasePojo {
    // 主键
    private String zxSfFeeId;

    // 机构名称
    private String orgName;

    // 机构ID
    private String orgID;

    // 季度
    private Date season;

    // 本季度安全费用支出
    private BigDecimal feeAmt;

    // 备注
    private String notes;

    // 公司上报金额
    private BigDecimal compSendAmt;

    // 局批复金额
    private BigDecimal groupCheckAmt;

    // 公司批复金额
    private BigDecimal compCheckAmt;

    // 状态
    private String status;

    // 编辑时间
    private Date editTime;

    // 年份
    private String yearStr;

    // 是否局下达
    private String isGroup;

    // 是否公司下达
    private String isCompany;

    // 本季度产值
    private BigDecimal produceAmt;

    // 所属机构（项目ID）
    private String projectId;

    // 项目名称
    private String projectName;

    // 所属公司ID
    private String companyId;

    // 所属公司名称
    private String companyName;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    // 附件
    private List<ZxErpFile> fileList;

    // 在建合同额
    private BigDecimal zjCost;

    // 安全费用总预计（万元）
    private BigDecimal zjSfCost;

    // 已完合同额（万元）
    private BigDecimal endCost;

    // 已完合同额安全费用预计（万元）
    private BigDecimal endSfCost;

    // 实际支出
    private BigDecimal cost;


    // 工程类别
    private String projType;

    private List<Date> seasonQuery;

    
    public List<ZxErpFile> getFileList() {
		return fileList;
	}

	public void setFileList(List<ZxErpFile> fileList) {
		this.fileList = fileList;
	}

	public String getZxSfFeeId() {
        return zxSfFeeId == null ? "" : zxSfFeeId.trim();
    }

    public void setZxSfFeeId(String zxSfFeeId) {
        this.zxSfFeeId = zxSfFeeId == null ? null : zxSfFeeId.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public Date getSeason() {
        return season;
    }

    public void setSeason(Date season) {
        this.season = season;
    }

    public BigDecimal getFeeAmt() {
        return feeAmt;
    }

    public void setFeeAmt(BigDecimal feeAmt) {
        this.feeAmt = feeAmt;
    }

    public String getNotes() {
        return notes == null ? "" : notes.trim();
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public BigDecimal getCompSendAmt() {
        return compSendAmt;
    }

    public void setCompSendAmt(BigDecimal compSendAmt) {
        this.compSendAmt = compSendAmt;
    }

    public BigDecimal getGroupCheckAmt() {
        return groupCheckAmt;
    }

    public void setGroupCheckAmt(BigDecimal groupCheckAmt) {
        this.groupCheckAmt = groupCheckAmt;
    }

    public BigDecimal getCompCheckAmt() {
        return compCheckAmt;
    }

    public void setCompCheckAmt(BigDecimal compCheckAmt) {
        this.compCheckAmt = compCheckAmt;
    }

    public String getStatus() {
        return status == null ? "" : status.trim();
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getYearStr() {
        return yearStr == null ? "" : yearStr.trim();
    }

    public void setYearStr(String yearStr) {
        this.yearStr = yearStr == null ? null : yearStr.trim();
    }

    public String getIsGroup() {
        return isGroup == null ? "" : isGroup.trim();
    }

    public void setIsGroup(String isGroup) {
        this.isGroup = isGroup == null ? null : isGroup.trim();
    }

    public String getIsCompany() {
        return isCompany == null ? "" : isCompany.trim();
    }

    public void setIsCompany(String isCompany) {
        this.isCompany = isCompany == null ? null : isCompany.trim();
    }

    public BigDecimal getProduceAmt() {
        return produceAmt;
    }

    public void setProduceAmt(BigDecimal produceAmt) {
        this.produceAmt = produceAmt;
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

    public String getCompanyId() {
        return companyId == null ? "" : companyId.trim();
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCompanyName() {
        return companyName == null ? "" : companyName.trim();
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
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

    public BigDecimal getZjCost() {
        return zjCost;
    }

    public void setZjCost(BigDecimal zjCost) {
        this.zjCost = zjCost;
    }

    public BigDecimal getZjSfCost() {
        return zjSfCost;
    }

    public void setZjSfCost(BigDecimal zjSfCost) {
        this.zjSfCost = zjSfCost;
    }

    public BigDecimal getEndCost() {
        return endCost;
    }

    public void setEndCost(BigDecimal endCost) {
        this.endCost = endCost;
    }

    public BigDecimal getEndSfCost() {
        return endSfCost;
    }

    public void setEndSfCost(BigDecimal endSfCost) {
        this.endSfCost = endSfCost;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getProjType() {
        return projType;
    }

    public void setProjType(String projType) {
        this.projType = projType;
    }

    public List<Date> getSeasonQuery() {
        return seasonQuery;
    }

    public void setSeasonQuery(List<Date> seasonQuery) {
        this.seasonQuery = seasonQuery;
    }
}
