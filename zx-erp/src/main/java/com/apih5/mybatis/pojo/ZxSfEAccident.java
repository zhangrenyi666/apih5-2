package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfEAccident extends BasePojo {
    // 主键
    private String zxSfEAccidentId;

    // 机构ID
    private String orgId;

    // 备注
    private String notes;

    // 填报时间
    private Date createDate;

    // 填报人
    private String creator;

    // 年份
    private String year;

    // 填表单位
    private String orgName;

    // 所属公司id
    private String companyId;

    // 所属公司名称
    private String companyName;

    // 所属项目id
    private String projectId;

    // 所属项目名称
    private String projectName;

    // 排序
    private int sort=0;

    // 企业职工伤亡事故年度统计分析表明细列表
    private List<ZxSfEAccidentItem> zxSfEAccidentItemList;

    // 附件
    private List<ZxErpFile> zxErpFileList;

    // 期次(时间类型)
    private Date datePeriod;

    public Date getDatePeriod() {
        return datePeriod;
    }

    public void setDatePeriod(Date datePeriod) {
        this.datePeriod = datePeriod;
    }

    public String getZxSfEAccidentId() {
        return zxSfEAccidentId == null ? "" : zxSfEAccidentId.trim();
    }

    public void setZxSfEAccidentId(String zxSfEAccidentId) {
        this.zxSfEAccidentId = zxSfEAccidentId == null ? null : zxSfEAccidentId.trim();
    }

    public String getOrgId() {
        return orgId == null ? "" : orgId.trim();
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getNotes() {
        return notes == null ? "" : notes.trim();
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreator() {
        return creator == null ? "" : creator.trim();
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getYear() {
        return year == null ? "" : year.trim();
    }

    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<ZxSfEAccidentItem> getZxSfEAccidentItemList() {
        return zxSfEAccidentItemList;
    }

    public void setZxSfEAccidentItemList(List<ZxSfEAccidentItem> zxSfEAccidentItemList) {
        this.zxSfEAccidentItemList = zxSfEAccidentItemList;
    }

    public List<ZxErpFile> getZxErpFileList() {
        return zxErpFileList;
    }

    public void setZxErpFileList(List<ZxErpFile> zxErpFileList) {
        this.zxErpFileList = zxErpFileList;
    }

}
