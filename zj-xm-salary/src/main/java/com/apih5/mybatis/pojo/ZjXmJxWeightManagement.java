package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjXmJxWeightManagement extends BasePojo {
    private String managementId;

    private Integer taskWeight;

    private Integer peripheryWeight;

    private Integer principalWeight;

    private Integer evaluateWeight;

    private Date yearMonth;

    private String remarks;

    private Integer sort;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public String getManagementId() {
        return managementId == null ? "" : managementId.trim();
    }

    public void setManagementId(String managementId) {
        this.managementId = managementId == null ? null : managementId.trim();
    }

    public Integer getTaskWeight() {
        return taskWeight;
    }

    public void setTaskWeight(Integer taskWeight) {
        this.taskWeight = taskWeight;
    }

    public Integer getPeripheryWeight() {
        return peripheryWeight;
    }

    public void setPeripheryWeight(Integer peripheryWeight) {
        this.peripheryWeight = peripheryWeight;
    }

    public Integer getPrincipalWeight() {
        return principalWeight;
    }

    public void setPrincipalWeight(Integer principalWeight) {
        this.principalWeight = principalWeight;
    }

    public Integer getEvaluateWeight() {
        return evaluateWeight;
    }

    public void setEvaluateWeight(Integer evaluateWeight) {
        this.evaluateWeight = evaluateWeight;
    }

    public Date getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(Date yearMonth) {
        this.yearMonth = yearMonth;
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

