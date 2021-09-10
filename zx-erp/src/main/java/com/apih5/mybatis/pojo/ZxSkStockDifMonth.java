package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkStockDifMonth extends BasePojo {
    // 主键
    private String zxSkStockDifMonthId;

    // 公司名称ID
    private String comID;

    // 公司名称
    private String comName;

    // 项目名称ID
    private String projectID;

    // 项目名称
    private String projectName;

    // 期次
    private String period;
    //传给前端的期次
    private Date periodDate;

    // 审核状态
    private String status;

    // 填报人
    private String reporter;

    // 填报时间
    private Date reportDate;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    List<ZxSkStockDifMonthItem> zxSkStockDifMonthItemList;

    public List<ZxSkStockDifMonthItem> getZxSkStockDifMonthItemList() {
        return zxSkStockDifMonthItemList;
    }

    public void setZxSkStockDifMonthItemList(List<ZxSkStockDifMonthItem> zxSkStockDifMonthItemList) {
        this.zxSkStockDifMonthItemList = zxSkStockDifMonthItemList;
    }

    public Date getPeriodDate() {
        return periodDate;
    }

    public void setPeriodDate(Date periodDate) {
        this.periodDate = periodDate;
    }

    public String getZxSkStockDifMonthId() {
        return zxSkStockDifMonthId == null ? "" : zxSkStockDifMonthId.trim();
    }

    public void setZxSkStockDifMonthId(String zxSkStockDifMonthId) {
        this.zxSkStockDifMonthId = zxSkStockDifMonthId == null ? null : zxSkStockDifMonthId.trim();
    }

    public String getComID() {
        return comID == null ? "" : comID.trim();
    }

    public void setComID(String comID) {
        this.comID = comID == null ? null : comID.trim();
    }

    public String getComName() {
        return comName == null ? "" : comName.trim();
    }

    public void setComName(String comName) {
        this.comName = comName == null ? null : comName.trim();
    }

    public String getProjectID() {
        return projectID == null ? "" : projectID.trim();
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID == null ? null : projectID.trim();
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName.trim();
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getPeriod() {
        return period == null ? "" : period.trim();
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getStatus() {
        return status == null ? "" : status.trim();
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getReporter() {
        return reporter == null ? "" : reporter.trim();
    }

    public void setReporter(String reporter) {
        this.reporter = reporter == null ? null : reporter.trim();
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
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
