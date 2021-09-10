package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkMake extends BasePojo {
    // 主键
    private String id;

    // 仓库机构ID
    private String whOrgID;

    // 盘点日期
    private Date makeInvDate;

    // 经手人
    private String handler;

    // 负责人
    private String principal;

    // 状态
    private String status;

    // 盘点仓库
    private String warehouseName;

    // 盘点状态
    private String plmm;

    // 项目id
    private String projectId;

    // 项目名称
    private String projectName;

    // 公司id
    private String companyId;

    // 公司名称
    private String companyName;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    //明细
    List<ZxSkMakeItem> zxSkMakeItemList;

    //附件
    List<ZxErpFile> fileList;

    private String companyID;

    private List<Date> timeList;

    public List<Date> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<Date> timeList) {
        this.timeList = timeList;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public List<ZxErpFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<ZxErpFile> fileList) {
        this.fileList = fileList;
    }

    public List<ZxSkMakeItem> getZxSkMakeItemList() {
        return zxSkMakeItemList;
    }

    public void setZxSkMakeItemList(List<ZxSkMakeItem> zxSkMakeItemList) {
        this.zxSkMakeItemList = zxSkMakeItemList;
    }

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getWhOrgID() {
        return whOrgID == null ? "" : whOrgID.trim();
    }

    public void setWhOrgID(String whOrgID) {
        this.whOrgID = whOrgID == null ? null : whOrgID.trim();
    }

    public Date getMakeInvDate() {
        return makeInvDate;
    }

    public void setMakeInvDate(Date makeInvDate) {
        this.makeInvDate = makeInvDate;
    }

    public String getHandler() {
        return handler == null ? "" : handler.trim();
    }

    public void setHandler(String handler) {
        this.handler = handler == null ? null : handler.trim();
    }

    public String getPrincipal() {
        return principal == null ? "" : principal.trim();
    }

    public void setPrincipal(String principal) {
        this.principal = principal == null ? null : principal.trim();
    }

    public String getStatus() {
        return status == null ? "" : status.trim();
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getWarehouseName() {
        return warehouseName == null ? "" : warehouseName.trim();
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName == null ? null : warehouseName.trim();
    }

    public String getPlmm() {
        return plmm == null ? "" : plmm.trim();
    }

    public void setPlmm(String plmm) {
        this.plmm = plmm == null ? null : plmm.trim();
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
