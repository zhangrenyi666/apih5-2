package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfSWAccident extends BasePojo {
    // 主键
    private String zxSfSWAccidentId;

    // 机构ID
    private String orgId;

    // 填表人
    private String creator;

    // 单位负责人
    private String manager;

    // 备注
    private String notes;

    // 处（科）负责人
    private String deManager;

    // 期次
    private String period;

    // 期次(时间类型)
    private Date datePeriod;

    // 填报日期
    private Date bizDate;

    // 填报单位
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

    // 船舶水上交通事故明细列表
    private List<ZxSfSWAccidentItem> zxSfSWAccidentItemList;

    // 附件
    private List<ZxErpFile> zxErpFileList;

    public Date getDatePeriod() {
        return datePeriod;
    }

    public void setDatePeriod(Date datePeriod) {
        this.datePeriod = datePeriod;
    }

    public String getZxSfSWAccidentId() {
        return zxSfSWAccidentId == null ? "" : zxSfSWAccidentId.trim();
    }

    public void setZxSfSWAccidentId(String zxSfSWAccidentId) {
        this.zxSfSWAccidentId = zxSfSWAccidentId == null ? null : zxSfSWAccidentId.trim();
    }

    public String getOrgId() {
        return orgId == null ? "" : orgId.trim();
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getCreator() {
        return creator == null ? "" : creator.trim();
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getManager() {
        return manager == null ? "" : manager.trim();
    }

    public void setManager(String manager) {
        this.manager = manager == null ? null : manager.trim();
    }

    public String getNotes() {
        return notes == null ? "" : notes.trim();
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public String getDeManager() {
        return deManager == null ? "" : deManager.trim();
    }

    public void setDeManager(String deManager) {
        this.deManager = deManager == null ? null : deManager.trim();
    }

    public String getPeriod() {
        return period == null ? "" : period.trim();
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public Date getBizDate() {
        return bizDate;
    }

    public void setBizDate(Date bizDate) {
        this.bizDate = bizDate;
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

    public List<ZxSfSWAccidentItem> getZxSfSWAccidentItemList() {
        return zxSfSWAccidentItemList;
    }

    public void setZxSfSWAccidentItemList(List<ZxSfSWAccidentItem> zxSfSWAccidentItemList) {
        this.zxSfSWAccidentItemList = zxSfSWAccidentItemList;
    }

    public List<ZxErpFile> getZxErpFileList() {
        return zxErpFileList;
    }

    public void setZxErpFileList(List<ZxErpFile> zxErpFileList) {
        this.zxErpFileList = zxErpFileList;
    }
}
