package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfPlanTarget extends BasePojo {
    // 主键
    private String zxSfPlanTargetId;

    // 项目名称
    private String orgName;

    // 因工死亡率%
    private String deadRate;

    // 重伤率%
    private String injuresRate;

    // 轻伤率%
    private String slightlyRate;

    // 隐患整改率%
    private String hidChageRate;

    // 项目ID
    private String orgID;

    private String companyId;

    // 更新时间
    private Date editTime;

    // 公司名称
    private String compName;

    // 年度
    private String year;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    //文件列表（文件工具）
    private List<ZxErpFile> zxErpFileList;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public List<ZxErpFile> getZxErpFileList() {
        return zxErpFileList;
    }

    public void setZxErpFileList(List<ZxErpFile> zxErpFileList) {
        this.zxErpFileList = zxErpFileList;
    }

    public String getZxSfPlanTargetId() {
        return zxSfPlanTargetId == null ? "" : zxSfPlanTargetId.trim();
    }

    public void setZxSfPlanTargetId(String zxSfPlanTargetId) {
        this.zxSfPlanTargetId = zxSfPlanTargetId == null ? null : zxSfPlanTargetId.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getDeadRate() {
        return deadRate == null ? "" : deadRate.trim();
    }

    public void setDeadRate(String deadRate) {
        this.deadRate = deadRate == null ? null : deadRate.trim();
    }

    public String getInjuresRate() {
        return injuresRate == null ? "" : injuresRate.trim();
    }

    public void setInjuresRate(String injuresRate) {
        this.injuresRate = injuresRate == null ? null : injuresRate.trim();
    }

    public String getSlightlyRate() {
        return slightlyRate == null ? "" : slightlyRate.trim();
    }

    public void setSlightlyRate(String slightlyRate) {
        this.slightlyRate = slightlyRate == null ? null : slightlyRate.trim();
    }

    public String getHidChageRate() {
        return hidChageRate == null ? "" : hidChageRate.trim();
    }

    public void setHidChageRate(String hidChageRate) {
        this.hidChageRate = hidChageRate == null ? null : hidChageRate.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getCompName() {
        return compName == null ? "" : compName.trim();
    }

    public void setCompName(String compName) {
        this.compName = compName == null ? null : compName.trim();
    }

    public String getYear() {
        return year == null ? "" : year.trim();
    }

    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
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
