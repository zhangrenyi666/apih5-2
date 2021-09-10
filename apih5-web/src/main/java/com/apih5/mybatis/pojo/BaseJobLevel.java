package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class BaseJobLevel extends BasePojo {
    // 主键
    private String baseJobLevelId;

    // 公司类型
    private String companyId;

    // 项目薪酬类型（级别）
    private String payLevelType;

    // 岗位级别名称
    private String jobLevelName;

    // 岗薪等级明细
    private List<BaseJobLevelSub> baseJobLevelSubList;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getBaseJobLevelId() {
        return baseJobLevelId == null ? "" : baseJobLevelId.trim();
    }

    public void setBaseJobLevelId(String baseJobLevelId) {
        this.baseJobLevelId = baseJobLevelId == null ? null : baseJobLevelId.trim();
    }

    public String getCompanyId() {
        return companyId == null ? "" : companyId.trim();
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getPayLevelType() {
        return payLevelType == null ? "" : payLevelType.trim();
    }

    public void setPayLevelType(String payLevelType) {
        this.payLevelType = payLevelType == null ? null : payLevelType.trim();
    }

    public String getJobLevelName() {
        return jobLevelName == null ? "" : jobLevelName.trim();
    }

    public void setJobLevelName(String jobLevelName) {
        this.jobLevelName = jobLevelName == null ? null : jobLevelName.trim();
    }

    public List<BaseJobLevelSub> getBaseJobLevelSubList() {
        return baseJobLevelSubList == null ? Lists.newArrayList() : baseJobLevelSubList;
    }

    public void setBaseJobLevelSubList(List<BaseJobLevelSub> baseJobLevelSubList) {
        this.baseJobLevelSubList = baseJobLevelSubList == null ? null : baseJobLevelSubList;
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
