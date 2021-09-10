package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class SysProjectDeptConf extends BasePojo {
    // 主键
    private String sysProjectDeptConfId;

    // 部门ID
    private String departmentId;

    // 岗位ID
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

    public String getSysProjectDeptConfId() {
        return sysProjectDeptConfId == null ? "" : sysProjectDeptConfId.trim();
    }

    public void setSysProjectDeptConfId(String sysProjectDeptConfId) {
        this.sysProjectDeptConfId = sysProjectDeptConfId == null ? null : sysProjectDeptConfId.trim();
    }

    public String getDepartmentId() {
        return departmentId == null ? "" : departmentId.trim();
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
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
