package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfCheckItem extends BasePojo {
    // 主键
    private String zxSfCheckItemId;

    // 主表ID
    private String checkID;

    // 存在隐患
    private String riskInfo;

    // 拟整改措施
    private String changeInfo;

    // 复查日期
    private Date checkDate;

    // 复查人员
    private String checker;

    // 复查结论
    private String checkResult;

    // 编辑时间
    private Date editTime;

    // 整改期限
    private Date lastCheckDate;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getZxSfCheckItemId() {
        return zxSfCheckItemId == null ? "" : zxSfCheckItemId.trim();
    }

    public void setZxSfCheckItemId(String zxSfCheckItemId) {
        this.zxSfCheckItemId = zxSfCheckItemId == null ? null : zxSfCheckItemId.trim();
    }

    public String getCheckID() {
        return checkID == null ? "" : checkID.trim();
    }

    public void setCheckID(String checkID) {
        this.checkID = checkID == null ? null : checkID.trim();
    }

    public String getRiskInfo() {
        return riskInfo == null ? "" : riskInfo.trim();
    }

    public void setRiskInfo(String riskInfo) {
        this.riskInfo = riskInfo == null ? null : riskInfo.trim();
    }

    public String getChangeInfo() {
        return changeInfo == null ? "" : changeInfo.trim();
    }

    public void setChangeInfo(String changeInfo) {
        this.changeInfo = changeInfo == null ? null : changeInfo.trim();
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getChecker() {
        return checker == null ? "" : checker.trim();
    }

    public void setChecker(String checker) {
        this.checker = checker == null ? null : checker.trim();
    }

    public String getCheckResult() {
        return checkResult == null ? "" : checkResult.trim();
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult == null ? null : checkResult.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public Date getLastCheckDate() {
        return lastCheckDate;
    }

    public void setLastCheckDate(Date lastCheckDate) {
        this.lastCheckDate = lastCheckDate;
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
