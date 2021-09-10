package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class BaseJobLevelSub extends BasePojo {
    // 主键
    private String baseJobLevelSubId;

    // 主表id
    private String baseJobLevelId;

    // 档位
    private String gear;

    // 岗薪
    private BigDecimal salary;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getBaseJobLevelSubId() {
        return baseJobLevelSubId == null ? "" : baseJobLevelSubId.trim();
    }

    public void setBaseJobLevelSubId(String baseJobLevelSubId) {
        this.baseJobLevelSubId = baseJobLevelSubId == null ? null : baseJobLevelSubId.trim();
    }

    public String getBaseJobLevelId() {
        return baseJobLevelId == null ? "" : baseJobLevelId.trim();
    }

    public void setBaseJobLevelId(String baseJobLevelId) {
        this.baseJobLevelId = baseJobLevelId == null ? null : baseJobLevelId.trim();
    }

    public String getGear() {
        return gear == null ? "" : gear.trim();
    }

    public void setGear(String gear) {
        this.gear = gear == null ? null : gear.trim();
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
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
