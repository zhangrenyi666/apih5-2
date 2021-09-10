package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjXmJxQuarterlyAssessment extends BasePojo {
    // 主键ID
    private String assessmentId;

    // 年月
    private Date yearMonth;

    // 第几季度
    private String quarter;

    // 季度考核标题
    private String assessmentTitle;

    // 通知内容
    private String noticeContent;

    // 通知状态
    private String noticeStatus;

    // 考核状态
    private String assessmentStatus;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getAssessmentId() {
        return assessmentId == null ? "" : assessmentId.trim();
    }

    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId == null ? null : assessmentId.trim();
    }

    public Date getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(Date yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getQuarter() {
        return quarter == null ? "" : quarter.trim();
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter == null ? null : quarter.trim();
    }

    public String getAssessmentTitle() {
        return assessmentTitle == null ? "" : assessmentTitle.trim();
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle == null ? null : assessmentTitle.trim();
    }

    public String getNoticeContent() {
        return noticeContent == null ? "" : noticeContent.trim();
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent == null ? null : noticeContent.trim();
    }

    public String getNoticeStatus() {
        return noticeStatus == null ? "" : noticeStatus.trim();
    }

    public void setNoticeStatus(String noticeStatus) {
        this.noticeStatus = noticeStatus == null ? null : noticeStatus.trim();
    }

    public String getAssessmentStatus() {
        return assessmentStatus == null ? "" : assessmentStatus.trim();
    }

    public void setAssessmentStatus(String assessmentStatus) {
        this.assessmentStatus = assessmentStatus == null ? null : assessmentStatus.trim();
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
