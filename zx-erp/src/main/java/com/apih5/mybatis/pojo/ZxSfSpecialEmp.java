package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfSpecialEmp extends BasePojo {
    // 主键
    private String zxSfSpecialEmpId;

    // 项目名称
    private String orgName;

    // 项目ID
    private String orgID;

    // 单位负责人
    private String manager;

    // 填报人
    private String creator;

    // 填报日期
    private Date createDate;

    // 编辑时间
    private Date editTime;

    // 所属公司ID
    private String companyId;

    // 所属公司名称
    private String companyName;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    // 附件
    private List<ZxErpFile> fileList;
    
    // 明细
    private List<ZxSfSpecialEmpItem> zxSfSpecialEmpItemList;

    private String projectId;

    private String checkDateMark;

    public String getCheckDateMark() {
        return checkDateMark;
    }

    public void setCheckDateMark(String checkDateMark) {
        this.checkDateMark = checkDateMark;
    }

    public List<ZxSfSpecialEmpItem> getZxSfSpecialEmpItemList() {
		return zxSfSpecialEmpItemList;
	}

	public void setZxSfSpecialEmpItemList(List<ZxSfSpecialEmpItem> zxSfSpecialEmpItemList) {
		this.zxSfSpecialEmpItemList = zxSfSpecialEmpItemList;
	}

	public List<ZxErpFile> getFileList() {
		return fileList;
	}

	public void setFileList(List<ZxErpFile> fileList) {
		this.fileList = fileList;
	}

	public String getZxSfSpecialEmpId() {
        return zxSfSpecialEmpId == null ? "" : zxSfSpecialEmpId.trim();
    }

    public void setZxSfSpecialEmpId(String zxSfSpecialEmpId) {
        this.zxSfSpecialEmpId = zxSfSpecialEmpId == null ? null : zxSfSpecialEmpId.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getManager() {
        return manager == null ? "" : manager.trim();
    }

    public void setManager(String manager) {
        this.manager = manager == null ? null : manager.trim();
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

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
