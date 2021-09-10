package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class BaseProjectConf extends BasePojo {
    // 主键
    private String baseProjectConfId;

    // 工程类型
    private String projType;

    // 项目级别
    private String proLevel;

    // 部门ID
    private String departmentId;

    // 部门名称
    private String departmentName;

    // 部门人数
    private Integer departmentNum;

    // 岗位项目人员配置字典表明细
    private List<BaseProjectConfJob> baseProjectConfJobList;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getBaseProjectConfId() {
        return baseProjectConfId == null ? "" : baseProjectConfId.trim();
    }

    public void setBaseProjectConfId(String baseProjectConfId) {
        this.baseProjectConfId = baseProjectConfId == null ? null : baseProjectConfId.trim();
    }

    public String getProjType() {
        return projType == null ? "" : projType.trim();
    }

    public void setProjType(String projType) {
        this.projType = projType == null ? null : projType.trim();
    }

    public String getProLevel() {
        return proLevel == null ? "" : proLevel.trim();
    }

    public void setProLevel(String proLevel) {
        this.proLevel = proLevel == null ? null : proLevel.trim();
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName == null ? "" : departmentName.trim();
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public Integer getDepartmentNum() {
        return departmentNum;
    }

    public void setDepartmentNum(Integer departmentNum) {
        this.departmentNum = departmentNum;
    }

    public List<BaseProjectConfJob> getBaseProjectConfJobList() {
        return baseProjectConfJobList == null ? Lists.newArrayList() : baseProjectConfJobList;
    }

    public void setBaseProjectConfJobList(List<BaseProjectConfJob> baseProjectConfJobList) {
        this.baseProjectConfJobList = baseProjectConfJobList == null ? null : baseProjectConfJobList;
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
