package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjLzehProduction extends BasePojo {
    private String productionId;

    private BigDecimal totalContractAmount;

    private BigDecimal accumulatedCompletionAmount;

    private BigDecimal currentYearSchemeAmount;

    private BigDecimal currentYearCompletionAmount;

    private BigDecimal currentMonthSchemeAmount;

    private BigDecimal currentMonthCompletionAmount;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public String getProductionId() {
        return productionId == null ? "" : productionId.trim();
    }

    public void setProductionId(String productionId) {
        this.productionId = productionId == null ? null : productionId.trim();
    }

    public BigDecimal getTotalContractAmount() {
        return totalContractAmount;
    }

    public void setTotalContractAmount(BigDecimal totalContractAmount) {
        this.totalContractAmount = totalContractAmount;
    }

    public BigDecimal getAccumulatedCompletionAmount() {
        return accumulatedCompletionAmount;
    }

    public void setAccumulatedCompletionAmount(BigDecimal accumulatedCompletionAmount) {
        this.accumulatedCompletionAmount = accumulatedCompletionAmount;
    }

    public BigDecimal getCurrentYearSchemeAmount() {
        return currentYearSchemeAmount;
    }

    public void setCurrentYearSchemeAmount(BigDecimal currentYearSchemeAmount) {
        this.currentYearSchemeAmount = currentYearSchemeAmount;
    }

    public BigDecimal getCurrentYearCompletionAmount() {
        return currentYearCompletionAmount;
    }

    public void setCurrentYearCompletionAmount(BigDecimal currentYearCompletionAmount) {
        this.currentYearCompletionAmount = currentYearCompletionAmount;
    }

    public BigDecimal getCurrentMonthSchemeAmount() {
        return currentMonthSchemeAmount;
    }

    public void setCurrentMonthSchemeAmount(BigDecimal currentMonthSchemeAmount) {
        this.currentMonthSchemeAmount = currentMonthSchemeAmount;
    }

    public BigDecimal getCurrentMonthCompletionAmount() {
        return currentMonthCompletionAmount;
    }

    public void setCurrentMonthCompletionAmount(BigDecimal currentMonthCompletionAmount) {
        this.currentMonthCompletionAmount = currentMonthCompletionAmount;
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

