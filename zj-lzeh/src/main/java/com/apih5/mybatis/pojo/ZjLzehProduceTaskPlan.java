package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZjLzehProduceTaskPlan extends BasePojo {
    // 主键
    private String zjLzehProduceTaskPlanId;

    // 月份
    private Date month;

    // 任务数
    private Integer taskQty;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getZjLzehProduceTaskPlanId() {
        return zjLzehProduceTaskPlanId == null ? "" : zjLzehProduceTaskPlanId.trim();
    }

    public void setZjLzehProduceTaskPlanId(String zjLzehProduceTaskPlanId) {
        this.zjLzehProduceTaskPlanId = zjLzehProduceTaskPlanId == null ? null : zjLzehProduceTaskPlanId.trim();
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getTaskQty() {
        return taskQty;
    }

    public void setTaskQty(Integer taskQty) {
        this.taskQty = taskQty;
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
