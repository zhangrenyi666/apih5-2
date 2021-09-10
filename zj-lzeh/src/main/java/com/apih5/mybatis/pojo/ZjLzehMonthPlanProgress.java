package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZjLzehMonthPlanProgress extends BasePojo {
    // 主键
    private String zjLzehMonthPlanProgressId;

    // 月份
    private Date month;

    // 计划月产值
    private BigDecimal planMonthOutValue;

    // 实际月产值
    private BigDecimal monthOutValue;

    // 任务完成率
    private BigDecimal completeRate;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    //文件列表（文件工具）
    private List<ZjLzehFile> fileList;

    private String showMonth;

    public String getShowMonth() {
        return showMonth;
    }

    public void setShowMonth(String showMonth) {
        this.showMonth = showMonth;
    }

    public List<ZjLzehFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<ZjLzehFile> fileList) {
        this.fileList = fileList;
    }

    public String getZjLzehMonthPlanProgressId() {
        return zjLzehMonthPlanProgressId == null ? "" : zjLzehMonthPlanProgressId.trim();
    }

    public void setZjLzehMonthPlanProgressId(String zjLzehMonthPlanProgressId) {
        this.zjLzehMonthPlanProgressId = zjLzehMonthPlanProgressId == null ? null : zjLzehMonthPlanProgressId.trim();
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public BigDecimal getPlanMonthOutValue() {
        return planMonthOutValue;
    }

    public void setPlanMonthOutValue(BigDecimal planMonthOutValue) {
        this.planMonthOutValue = planMonthOutValue;
    }

    public BigDecimal getMonthOutValue() {
        return monthOutValue;
    }

    public void setMonthOutValue(BigDecimal monthOutValue) {
        this.monthOutValue = monthOutValue;
    }

    public BigDecimal getCompleteRate() {
        return completeRate;
    }

    public void setCompleteRate(BigDecimal completeRate) {
        this.completeRate = completeRate;
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
