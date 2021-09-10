package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjXmJxPeripheryScoreRule extends BasePojo {
    private String ruleId;

    private Integer deputyDivisor;

    private Integer leaderDivisor;

    private Integer employeeDivisor;

    private Date yearMonth;

    private String isInvalid;

    private String remarks;

    private Integer sort;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public String getRuleId() {
        return ruleId == null ? "" : ruleId.trim();
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId == null ? null : ruleId.trim();
    }

    public Integer getDeputyDivisor() {
        return deputyDivisor;
    }

    public void setDeputyDivisor(Integer deputyDivisor) {
        this.deputyDivisor = deputyDivisor;
    }

    public Integer getLeaderDivisor() {
        return leaderDivisor;
    }

    public void setLeaderDivisor(Integer leaderDivisor) {
        this.leaderDivisor = leaderDivisor;
    }

    public Integer getEmployeeDivisor() {
        return employeeDivisor;
    }

    public void setEmployeeDivisor(Integer employeeDivisor) {
        this.employeeDivisor = employeeDivisor;
    }

    public Date getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(Date yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getIsInvalid() {
        return isInvalid == null ? "" : isInvalid.trim();
    }

    public void setIsInvalid(String isInvalid) {
        this.isInvalid = isInvalid == null ? null : isInvalid.trim();
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getDelFlag() {
        return delFlag == null ? "" : delFlag.trim();
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser == null ? "" : createUser.trim();
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getCreateUserName() {
        return createUserName == null ? "" : createUserName.trim();
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser == null ? "" : modifyUser.trim();
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    public String getModifyUserName() {
        return modifyUserName == null ? "" : modifyUserName.trim();
    }

    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName == null ? null : modifyUserName.trim();
    }

}

