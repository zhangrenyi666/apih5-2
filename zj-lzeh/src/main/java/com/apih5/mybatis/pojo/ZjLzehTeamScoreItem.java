package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZjLzehTeamScoreItem extends BasePojo {
    // 主键
    private String zjLzehTeamItemId;

    // 评分管理ID(主表ID)
    private String zjLzehTeamScoreId;

    // 班组ID
    private String teamId;

    // 施工进度
    private BigDecimal buildProgress;

    // 施工安全
    private BigDecimal buildSafe;

    // 施工质量
    private BigDecimal buildQuality;

    // 文明施工
    private BigDecimal buildCivilized;

    // 当月总分
    private BigDecimal monthScore = new BigDecimal("0");

    // 当月排名
    private int monthRank=0;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    //平均分
    private BigDecimal avgScore;

    // 评分月份
    private Date scoreMonth;

    //班组名称
    private String teamName;

    //公司名称
    private String companyName;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public BigDecimal getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(BigDecimal avgScore) {
        this.avgScore = avgScore;
    }

    public Date getScoreMonth() {
        return scoreMonth;
    }

    public void setScoreMonth(Date scoreMonth) {
        this.scoreMonth = scoreMonth;
    }

    public String getZjLzehTeamItemId() {
        return zjLzehTeamItemId == null ? "" : zjLzehTeamItemId.trim();
    }

    public void setZjLzehTeamItemId(String zjLzehTeamItemId) {
        this.zjLzehTeamItemId = zjLzehTeamItemId == null ? null : zjLzehTeamItemId.trim();
    }

    public String getZjLzehTeamScoreId() {
        return zjLzehTeamScoreId == null ? "" : zjLzehTeamScoreId.trim();
    }

    public void setZjLzehTeamScoreId(String zjLzehTeamScoreId) {
        this.zjLzehTeamScoreId = zjLzehTeamScoreId == null ? null : zjLzehTeamScoreId.trim();
    }

    public String getTeamId() {
        return teamId == null ? "" : teamId.trim();
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId == null ? null : teamId.trim();
    }

    public BigDecimal getBuildProgress() {
        return buildProgress;
    }

    public void setBuildProgress(BigDecimal buildProgress) {
        this.buildProgress = buildProgress;
    }

    public BigDecimal getBuildSafe() {
        return buildSafe;
    }

    public void setBuildSafe(BigDecimal buildSafe) {
        this.buildSafe = buildSafe;
    }

    public BigDecimal getBuildQuality() {
        return buildQuality;
    }

    public void setBuildQuality(BigDecimal buildQuality) {
        this.buildQuality = buildQuality;
    }

    public BigDecimal getBuildCivilized() {
        return buildCivilized;
    }

    public void setBuildCivilized(BigDecimal buildCivilized) {
        this.buildCivilized = buildCivilized;
    }

    public BigDecimal getMonthScore() {
        return monthScore;
    }

    public void setMonthScore(BigDecimal monthScore) {
        this.monthScore = monthScore;
    }

    public int getMonthRank() {
        return monthRank;
    }

    public void setMonthRank(int monthRank) {
        this.monthRank = monthRank;
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
