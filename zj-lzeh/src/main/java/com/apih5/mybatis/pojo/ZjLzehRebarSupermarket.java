package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjLzehRebarSupermarket extends BasePojo {
    private String rebarSupermarketId;

    private BigDecimal rebarRequirementNumber;

    private BigDecimal cumulativeUsageNumber;

    private BigDecimal cumulativeHasBeenReceivingNumber;

    private BigDecimal remainingStockNumber;

    private BigDecimal cumulativeTotalWaste;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public String getRebarSupermarketId() {
        return rebarSupermarketId == null ? "" : rebarSupermarketId.trim();
    }

    public void setRebarSupermarketId(String rebarSupermarketId) {
        this.rebarSupermarketId = rebarSupermarketId == null ? null : rebarSupermarketId.trim();
    }

    public BigDecimal getRebarRequirementNumber() {
        return rebarRequirementNumber;
    }

    public void setRebarRequirementNumber(BigDecimal rebarRequirementNumber) {
        this.rebarRequirementNumber = rebarRequirementNumber;
    }

    public BigDecimal getCumulativeUsageNumber() {
        return cumulativeUsageNumber;
    }

    public void setCumulativeUsageNumber(BigDecimal cumulativeUsageNumber) {
        this.cumulativeUsageNumber = cumulativeUsageNumber;
    }

    public BigDecimal getCumulativeHasBeenReceivingNumber() {
        return cumulativeHasBeenReceivingNumber;
    }

    public void setCumulativeHasBeenReceivingNumber(BigDecimal cumulativeHasBeenReceivingNumber) {
        this.cumulativeHasBeenReceivingNumber = cumulativeHasBeenReceivingNumber;
    }

    public BigDecimal getRemainingStockNumber() {
        return remainingStockNumber;
    }

    public void setRemainingStockNumber(BigDecimal remainingStockNumber) {
        this.remainingStockNumber = remainingStockNumber;
    }

    public BigDecimal getCumulativeTotalWaste() {
        return cumulativeTotalWaste;
    }

    public void setCumulativeTotalWaste(BigDecimal cumulativeTotalWaste) {
        this.cumulativeTotalWaste = cumulativeTotalWaste;
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

