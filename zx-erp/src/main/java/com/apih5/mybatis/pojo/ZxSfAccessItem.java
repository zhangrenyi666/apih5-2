package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfAccessItem extends BasePojo {
    // 主键
    private String zxSfAccessItemId;

    // 主表ID
    private String accessID;

    // 日期
    private Date bizDate;

    // 安全状况记录
    private String safeRecord;

    // 违章记录
    private String wrongRecord;

    // 整改情况
    private String changeInfo;

    // 考核评价
    private String examInfo;

    // 编辑时间
    private String editTime;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    private Date inDate;

    private Date outDate;

    public String getZxSfAccessItemId() {
        return zxSfAccessItemId == null ? "" : zxSfAccessItemId.trim();
    }

    public void setZxSfAccessItemId(String zxSfAccessItemId) {
        this.zxSfAccessItemId = zxSfAccessItemId == null ? null : zxSfAccessItemId.trim();
    }

    public String getAccessID() {
        return accessID == null ? "" : accessID.trim();
    }

    public void setAccessID(String accessID) {
        this.accessID = accessID == null ? null : accessID.trim();
    }

    public Date getBizDate() {
        return bizDate;
    }

    public void setBizDate(Date bizDate) {
        this.bizDate = bizDate;
    }

    public String getSafeRecord() {
        return safeRecord == null ? "" : safeRecord.trim();
    }

    public void setSafeRecord(String safeRecord) {
        this.safeRecord = safeRecord == null ? null : safeRecord.trim();
    }

    public String getWrongRecord() {
        return wrongRecord == null ? "" : wrongRecord.trim();
    }

    public void setWrongRecord(String wrongRecord) {
        this.wrongRecord = wrongRecord == null ? null : wrongRecord.trim();
    }

    public String getChangeInfo() {
        return changeInfo == null ? "" : changeInfo.trim();
    }

    public void setChangeInfo(String changeInfo) {
        this.changeInfo = changeInfo == null ? null : changeInfo.trim();
    }

    public String getExamInfo() {
        return examInfo == null ? "" : examInfo.trim();
    }

    public void setExamInfo(String examInfo) {
        this.examInfo = examInfo == null ? null : examInfo.trim();
    }

    public String getEditTime() {
        return editTime == null ? "" : editTime.trim();
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime == null ? null : editTime.trim();
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

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }
}
