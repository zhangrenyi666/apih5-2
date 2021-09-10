package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfJuAccessRoom extends BasePojo {
    // 主键
    private String zxSfJuAccessRoomId;

    // 考核项目
    private String examName;

    // 考核内容
    private String examContext;

    // 满分
    private String allScore;

    // 备注
    private String notes;

    // 编辑时间
    private Date editTime;

    // 排序
    private String orderNo;

    // 所属机构（项目ID）
    private String projectId;

    // 项目名称
    private String projectName;

    // 所属公司ID
    private String companyId;

    // 所属公司名称
    private String companyName;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getZxSfJuAccessRoomId() {
        return zxSfJuAccessRoomId == null ? "" : zxSfJuAccessRoomId.trim();
    }

    public void setZxSfJuAccessRoomId(String zxSfJuAccessRoomId) {
        this.zxSfJuAccessRoomId = zxSfJuAccessRoomId == null ? null : zxSfJuAccessRoomId.trim();
    }

    public String getExamName() {
        return examName == null ? "" : examName.trim();
    }

    public void setExamName(String examName) {
        this.examName = examName == null ? null : examName.trim();
    }

    public String getExamContext() {
        return examContext == null ? "" : examContext.trim();
    }

    public void setExamContext(String examContext) {
        this.examContext = examContext == null ? null : examContext.trim();
    }

    public String getAllScore() {
        return allScore == null ? "" : allScore.trim();
    }

    public void setAllScore(String allScore) {
        this.allScore = allScore == null ? null : allScore.trim();
    }

    public String getNotes() {
        return notes == null ? "" : notes.trim();
    }

    public void setNotes(String notes) {
        this.notes = notes == null ? null : notes.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getOrderNo() {
        return orderNo == null ? "" : orderNo.trim();
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
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
