package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class BaseProjectConfJob extends BasePojo {
    // 主键
    private String baseProjectJobConfId;

    // 项目人员项目人员配置岗位表表主键
    private String baseProjectConfId;

    // 岗位
    private String jobType;

    // 人数类型
    private String numType;

    // 人数最小
    private Integer numMin;

    // 人数最大
    private Integer numMax;

    // 是否兼职
    private String jobFlag;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getBaseProjectJobConfId() {
        return baseProjectJobConfId == null ? "" : baseProjectJobConfId.trim();
    }

    public void setBaseProjectJobConfId(String baseProjectJobConfId) {
        this.baseProjectJobConfId = baseProjectJobConfId == null ? null : baseProjectJobConfId.trim();
    }

    public String getBaseProjectConfId() {
        return baseProjectConfId == null ? "" : baseProjectConfId.trim();
    }

    public void setBaseProjectConfId(String baseProjectConfId) {
        this.baseProjectConfId = baseProjectConfId == null ? null : baseProjectConfId.trim();
    }

    public String getJobType() {
        return jobType == null ? "" : jobType.trim();
    }

    public void setJobType(String jobType) {
        this.jobType = jobType == null ? null : jobType.trim();
    }

    public String getNumType() {
        return numType == null ? "" : numType.trim();
    }

    public void setNumType(String numType) {
        this.numType = numType == null ? null : numType.trim();
    }

    public Integer getNumMin() {
        return numMin;
    }

    public void setNumMin(Integer numMin) {
        this.numMin = numMin;
    }

    public Integer getNumMax() {
        return numMax;
    }

    public void setNumMax(Integer numMax) {
        this.numMax = numMax;
    }

    public String getJobFlag() {
        return jobFlag == null ? "" : jobFlag.trim();
    }

    public void setJobFlag(String jobFlag) {
        this.jobFlag = jobFlag == null ? null : jobFlag.trim();
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
