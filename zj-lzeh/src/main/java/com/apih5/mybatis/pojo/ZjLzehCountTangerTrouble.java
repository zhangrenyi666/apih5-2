package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZjLzehCountTangerTrouble extends BasePojo {
    // 主键
    private String zjLzehCountTangerTroubleId;

    // 安全统计ID
    private String countDangerId;

    // 检查时间
    private String checkDate;

    // 年份
    private String year;

    // 隐患总数
    private Integer totalNum;

    // 已整改数
    private Integer finishNum;

    // 本月隐患总数
    private Integer monthTotalNum;

    // 本月已整改数
    private Integer monthFinishNum;

    // 分部名称
    private String levelName;

    // 分部id
    private String levelId;

    // 质量统计ID
    private String countTroubleId;

    // 总百分比
    private BigDecimal totalPercent;

    // 月百分比
    private BigDecimal monthPercent;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getZjLzehCountTangerTroubleId() {
        return zjLzehCountTangerTroubleId == null ? "" : zjLzehCountTangerTroubleId.trim();
    }

    public void setZjLzehCountTangerTroubleId(String zjLzehCountTangerTroubleId) {
        this.zjLzehCountTangerTroubleId = zjLzehCountTangerTroubleId == null ? null : zjLzehCountTangerTroubleId.trim();
    }

    public String getCountDangerId() {
        return countDangerId == null ? "" : countDangerId.trim();
    }

    public void setCountDangerId(String countDangerId) {
        this.countDangerId = countDangerId == null ? null : countDangerId.trim();
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getFinishNum() {
        return finishNum;
    }

    public void setFinishNum(Integer finishNum) {
        this.finishNum = finishNum;
    }

    public Integer getMonthTotalNum() {
        return monthTotalNum;
    }

    public void setMonthTotalNum(Integer monthTotalNum) {
        this.monthTotalNum = monthTotalNum;
    }

    public Integer getMonthFinishNum() {
        return monthFinishNum;
    }

    public void setMonthFinishNum(Integer monthFinishNum) {
        this.monthFinishNum = monthFinishNum;
    }

    public String getLevelName() {
        return levelName == null ? "" : levelName.trim();
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName == null ? null : levelName.trim();
    }

    public String getLevelId() {
        return levelId == null ? "" : levelId.trim();
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId == null ? null : levelId.trim();
    }

    public String getCountTroubleId() {
        return countTroubleId == null ? "" : countTroubleId.trim();
    }

    public void setCountTroubleId(String countTroubleId) {
        this.countTroubleId = countTroubleId == null ? null : countTroubleId.trim();
    }

    public BigDecimal getTotalPercent() {
        return totalPercent;
    }

    public void setTotalPercent(BigDecimal totalPercent) {
        this.totalPercent = totalPercent;
    }

    public BigDecimal getMonthPercent() {
        return monthPercent;
    }

    public void setMonthPercent(BigDecimal monthPercent) {
        this.monthPercent = monthPercent;
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

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
