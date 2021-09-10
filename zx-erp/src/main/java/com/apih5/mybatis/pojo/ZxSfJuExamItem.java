package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfJuExamItem extends BasePojo {
    // 主键
    private String zxSfJuExamItemId;

    // 主表ID
    private String examID;

    // 考核项目
    private String examName;

    // 满分
    private BigDecimal allScore;

    // 自评
    private BigDecimal selfScore;

    // 考评
    private BigDecimal examScore;

    // 备注
    private String notes;

    // 编辑时间
    private Date editTime;

    // 考核内容
    private String examContext;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getZxSfJuExamItemId() {
        return zxSfJuExamItemId == null ? "" : zxSfJuExamItemId.trim();
    }

    public void setZxSfJuExamItemId(String zxSfJuExamItemId) {
        this.zxSfJuExamItemId = zxSfJuExamItemId == null ? null : zxSfJuExamItemId.trim();
    }

    public String getExamID() {
        return examID == null ? "" : examID.trim();
    }

    public void setExamID(String examID) {
        this.examID = examID == null ? null : examID.trim();
    }

    public String getExamName() {
        return examName == null ? "" : examName.trim();
    }

    public void setExamName(String examName) {
        this.examName = examName == null ? null : examName.trim();
    }

    public BigDecimal getAllScore() {
        return allScore;
    }

    public void setAllScore(BigDecimal allScore) {
        this.allScore = allScore;
    }

    public BigDecimal getSelfScore() {
        return selfScore;
    }

    public void setSelfScore(BigDecimal selfScore) {
        this.selfScore = selfScore;
    }

    public BigDecimal getExamScore() {
        return examScore;
    }

    public void setExamScore(BigDecimal examScore) {
        this.examScore = examScore;
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

    public String getExamContext() {
        return examContext == null ? "" : examContext.trim();
    }

    public void setExamContext(String examContext) {
        this.examContext = examContext == null ? null : examContext.trim();
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
