package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZjLzehTaskCensus extends BasePojo {
    // 主键
    private String zjLzehTaskCensusId;

    // 统计月份
    private Date censusMonth;

    // 人数
    private Integer personQty;

    // 备注
    private String remarks;

    // 排序
    private Integer sort;

    public String getZjLzehTaskCensusId() {
        return zjLzehTaskCensusId == null ? "" : zjLzehTaskCensusId.trim();
    }

    public void setZjLzehTaskCensusId(String zjLzehTaskCensusId) {
        this.zjLzehTaskCensusId = zjLzehTaskCensusId == null ? null : zjLzehTaskCensusId.trim();
    }

    public Date getCensusMonth() {
        return censusMonth;
    }

    public void setCensusMonth(Date censusMonth) {
        this.censusMonth = censusMonth;
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Integer getPersonQty() {
        return personQty;
    }

    public void setPersonQty(Integer personQty) {
        this.personQty = personQty;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
