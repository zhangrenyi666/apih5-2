package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjConsumableInventory extends BasePojo {
    private String inventoryId;

    private String setId;

    private String category;

    private String brand;

    private String model;

    private String colour;

    private Integer nowNum;

    private Integer totalInNum;

    private Integer totalUseNum;

    private Integer appNum;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public String getInventoryId() {
        return inventoryId == null ? "" : inventoryId.trim();
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId == null ? null : inventoryId.trim();
    }

    public String getSetId() {
        return setId == null ? "" : setId.trim();
    }

    public void setSetId(String setId) {
        this.setId = setId == null ? null : setId.trim();
    }

    public String getCategory() {
        return category == null ? "" : category.trim();
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getBrand() {
        return brand == null ? "" : brand.trim();
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    public String getModel() {
        return model == null ? "" : model.trim();
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getColour() {
        return colour == null ? "" : colour.trim();
    }

    public void setColour(String colour) {
        this.colour = colour == null ? null : colour.trim();
    }

    public Integer getNowNum() {
        return nowNum;
    }

    public void setNowNum(Integer nowNum) {
        this.nowNum = nowNum;
    }

    public Integer getTotalInNum() {
        return totalInNum;
    }

    public void setTotalInNum(Integer totalInNum) {
        this.totalInNum = totalInNum;
    }

    public Integer getTotalUseNum() {
        return totalUseNum;
    }

    public void setTotalUseNum(Integer totalUseNum) {
        this.totalUseNum = totalUseNum;
    }

    public Integer getAppNum() {
        return appNum;
    }

    public void setAppNum(Integer appNum) {
        this.appNum = appNum;
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

