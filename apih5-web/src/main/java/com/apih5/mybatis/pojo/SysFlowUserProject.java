package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class SysFlowUserProject extends BasePojo {
    // 主键
    private String workId;

    // 项目ID
    private String projectId;

    // 项目名称
    private String projectName;

    // 公司ID
    private String companyId;

    // 公司名称
    private String companyName;

    // 局ID
    private String juId;

    // 局名称
    private String juName;

    // 总部ID
    private String headquartersId;

    // 总部名称
    private String headquartersName;

    // 托管ID
    private String delegateId;

    // 托管名称
    private String delegateName;

    public String getWorkId() {
        return workId == null ? "" : workId.trim();
    }

    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    public String getProjectId() {
        return projectId == null ? "" : projectId.trim();
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName.trim();
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getCompanyId() {
        return companyId == null ? "" : companyId.trim();
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCompanyName() {
        return companyName == null ? "" : companyName.trim();
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getJuId() {
        return juId == null ? "" : juId.trim();
    }

    public void setJuId(String juId) {
        this.juId = juId == null ? null : juId.trim();
    }

    public String getJuName() {
        return juName == null ? "" : juName.trim();
    }

    public void setJuName(String juName) {
        this.juName = juName == null ? null : juName.trim();
    }

    public String getHeadquartersId() {
        return headquartersId == null ? "" : headquartersId.trim();
    }

    public void setHeadquartersId(String headquartersId) {
        this.headquartersId = headquartersId == null ? null : headquartersId.trim();
    }

    public String getHeadquartersName() {
        return headquartersName == null ? "" : headquartersName.trim();
    }

    public void setHeadquartersName(String headquartersName) {
        this.headquartersName = headquartersName == null ? null : headquartersName.trim();
    }

    public String getDelegateId() {
        return delegateId == null ? "" : delegateId.trim();
    }

    public void setDelegateId(String delegateId) {
        this.delegateId = delegateId == null ? null : delegateId.trim();
    }

    public String getDelegateName() {
        return delegateName == null ? "" : delegateName.trim();
    }

    public void setDelegateName(String delegateName) {
        this.delegateName = delegateName == null ? null : delegateName.trim();
    }

}
