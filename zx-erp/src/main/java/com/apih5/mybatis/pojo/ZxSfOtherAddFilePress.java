package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfOtherAddFilePress extends BasePojo {
    // 主键
    private String zxSfOtherAddFilePressId;

    // 名称
    private String title;

    // 填报人
    private String creator;

    // 填报日期
    private Date createDate;

    // 机构名称
    private String orgName;

    // 机构ID
    private String orgId;

    // 费用
    private BigDecimal amt;

    // 类型
    private String category;

    // 备注
    private String notes;

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

    // 附件
    private List<ZxErpFile> zxErpFileList;

    public String getZxSfOtherAddFilePressId() {
        return zxSfOtherAddFilePressId == null ? "" : zxSfOtherAddFilePressId.trim();
    }

    public void setZxSfOtherAddFilePressId(String zxSfOtherAddFilePressId) {
        this.zxSfOtherAddFilePressId = zxSfOtherAddFilePressId == null ? null : zxSfOtherAddFilePressId.trim();
    }

    public String getTitle() {
        return title == null ? "" : title.trim();
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getCreator() {
        return creator == null ? "" : creator.trim();
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getOrgId() {
        return orgId == null ? "" : orgId.trim();
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getCategory() {
        return category == null ? "" : category.trim();
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getNotes() {
        return notes == null ? "" : notes.trim();
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
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

    public List<ZxErpFile> getZxErpFileList() {
        return zxErpFileList;
    }

    public void setZxErpFileList(List<ZxErpFile> zxErpFileList) {
        this.zxErpFileList = zxErpFileList;
    }


}
