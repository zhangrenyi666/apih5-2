package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCmGlobalCode extends BasePojo {
    // 主键
    private String zxCmGlobalCodeId;

    // 分类ID
    private String categoryID;

    // 代码描述
    private String globalDesc;

    // 是否启用
    private String enable;

    // 是否默认选项
    private String selected;

    // 编辑时间
    private Date editTime;

    // 比率
    private String percentage;

    // 地区
    private String region;

    // 所属机构（项目ID）
    private String projectId;

    // 项目名称
    private String projectName;

    // 所属公司ID
    private String companyId;

    // 所属公司Name
    private String companyName;

    // 代码编号
    private String globalID;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getZxCmGlobalCodeId() {
        return zxCmGlobalCodeId == null ? "" : zxCmGlobalCodeId.trim();
    }

    public void setZxCmGlobalCodeId(String zxCmGlobalCodeId) {
        this.zxCmGlobalCodeId = zxCmGlobalCodeId == null ? null : zxCmGlobalCodeId.trim();
    }

    public String getCategoryID() {
        return categoryID == null ? "" : categoryID.trim();
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID == null ? null : categoryID.trim();
    }

    public String getGlobalDesc() {
        return globalDesc == null ? "" : globalDesc.trim();
    }

    public void setGlobalDesc(String globalDesc) {
        this.globalDesc = globalDesc == null ? null : globalDesc.trim();
    }

    public String getEnable() {
        return enable == null ? "" : enable.trim();
    }

    public void setEnable(String enable) {
        this.enable = enable == null ? null : enable.trim();
    }

    public String getSelected() {
        return selected == null ? "" : selected.trim();
    }

    public void setSelected(String selected) {
        this.selected = selected == null ? null : selected.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getPercentage() {
        return percentage == null ? "" : percentage.trim();
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage == null ? null : percentage.trim();
    }

    public String getRegion() {
        return region == null ? "" : region.trim();
    }

    public void setRegion(String region) {
        this.region = region == null ? null : region.trim();
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

    public String getGlobalID() {
        return globalID == null ? "" : globalID.trim();
    }

    public void setGlobalID(String globalID) {
        this.globalID = globalID == null ? null : globalID.trim();
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
