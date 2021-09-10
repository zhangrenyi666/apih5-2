package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZjLzehYearPlanProgress extends BasePojo {
    // 主键
    private String zjLzehYearPlanProgressId;

    // 年度
    private Date year;

    // 计划年产值
    private BigDecimal planYearOutValue;

    // 实际年产值
    private BigDecimal yearOutValue;

    // 任务完成率
    private BigDecimal completeRate;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    //展示年份
    private String showYear;


    //文件列表（文件工具）
    private List<ZjLzehFile> fileList;

    public String getShowYear() {
        return showYear;
    }

    public void setShowYear(String showYear) {
        this.showYear = showYear;
    }

    public List<ZjLzehFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<ZjLzehFile> fileList) {
        this.fileList = fileList;
    }

    public String getZjLzehYearPlanProgressId() {
        return zjLzehYearPlanProgressId == null ? "" : zjLzehYearPlanProgressId.trim();
    }

    public void setZjLzehYearPlanProgressId(String zjLzehYearPlanProgressId) {
        this.zjLzehYearPlanProgressId = zjLzehYearPlanProgressId == null ? null : zjLzehYearPlanProgressId.trim();
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public BigDecimal getPlanYearOutValue() {
        return planYearOutValue;
    }

    public void setPlanYearOutValue(BigDecimal planYearOutValue) {
        this.planYearOutValue = planYearOutValue;
    }

    public BigDecimal getYearOutValue() {
        return yearOutValue;
    }

    public void setYearOutValue(BigDecimal yearOutValue) {
        this.yearOutValue = yearOutValue;
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
