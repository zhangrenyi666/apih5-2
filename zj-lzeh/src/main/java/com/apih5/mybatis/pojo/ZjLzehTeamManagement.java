package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZjLzehTeamManagement extends BasePojo {
    // 主键
    private String zjLzehTeamManagementId;

    // 公司名称
    private String companyName;

    // 班组名称
    private String teamName;

    // 班组ID
    private String teamId;

    // 班组人数
    private int teamPerson=0;

    // 班组组长
    private String teamLeader;

    // 联系方式
    private String phone;

    // 公司ID
    private String companyId;

    // 公司简称
    private String companyAbbreviation;

    // 是否评分
    private String isScore;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    //班组简称
    private String teamAbbreviation;

    private int scoreNum;

    public int getScoreNum() {
        return scoreNum;
    }

    public void setScoreNum(int scoreNum) {
        this.scoreNum = scoreNum;
    }

    public String getTeamAbbreviation() {
        return teamAbbreviation;
    }

    public void setTeamAbbreviation(String teamAbbreviation) {
        this.teamAbbreviation = teamAbbreviation;
    }

    public String getZjLzehTeamManagementId() {
        return zjLzehTeamManagementId == null ? "" : zjLzehTeamManagementId.trim();
    }

    public void setZjLzehTeamManagementId(String zjLzehTeamManagementId) {
        this.zjLzehTeamManagementId = zjLzehTeamManagementId == null ? null : zjLzehTeamManagementId.trim();
    }

    public String getCompanyName() {
        return companyName == null ? "" : companyName.trim();
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getTeamName() {
        return teamName == null ? "" : teamName.trim();
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName == null ? null : teamName.trim();
    }

    public String getTeamId() {
        return teamId == null ? "" : teamId.trim();
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId == null ? null : teamId.trim();
    }

    public int getTeamPerson() {
        return teamPerson;
    }

    public void setTeamPerson(int teamPerson) {
        this.teamPerson = teamPerson;
    }

    public String getTeamLeader() {
        return teamLeader == null ? "" : teamLeader.trim();
    }

    public void setTeamLeader(String teamLeader) {
        this.teamLeader = teamLeader == null ? null : teamLeader.trim();
    }

    public String getPhone() {
        return phone == null ? "" : phone.trim();
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getCompanyId() {
        return companyId == null ? "" : companyId.trim();
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCompanyAbbreviation() {
        return companyAbbreviation == null ? "" : companyAbbreviation.trim();
    }

    public void setCompanyAbbreviation(String companyAbbreviation) {
        this.companyAbbreviation = companyAbbreviation == null ? null : companyAbbreviation.trim();
    }

    public String getIsScore() {
        return isScore == null ? "" : isScore.trim();
    }

    public void setIsScore(String isScore) {
        this.isScore = isScore == null ? null : isScore.trim();
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
