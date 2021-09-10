package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZjLzehTeamScore extends BasePojo {
    // 主键
    private String zjLzehTeamScoreId;

    // 评分月份
    private Date scoreMonth;

    // 班组数量
    private int teamNum=0;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getZjLzehTeamScoreId() {
        return zjLzehTeamScoreId == null ? "" : zjLzehTeamScoreId.trim();
    }

    public void setZjLzehTeamScoreId(String zjLzehTeamScoreId) {
        this.zjLzehTeamScoreId = zjLzehTeamScoreId == null ? null : zjLzehTeamScoreId.trim();
    }

    public Date getScoreMonth() {
        return scoreMonth;
    }

    public void setScoreMonth(Date scoreMonth) {
        this.scoreMonth = scoreMonth;
    }

    public int getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(int teamNum) {
        this.teamNum = teamNum;
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
