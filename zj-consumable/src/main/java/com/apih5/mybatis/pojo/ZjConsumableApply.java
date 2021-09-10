package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjConsumableApply extends BasePojo {
    private String applyId;

    private String setId;

    private String categoryName;

    private String category;

    private String brandName;

    private String brand;

    private String modelName;

    private String model;

    private String colourName;

    private String colour;

    private String deptName;

    private String userKey;

    private String name;

    private Date appDate;

    private Integer applyNum;

    private Integer mostNum;

    private String status;

    private String useStatus;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private Integer leftNum;
    
    private String statusIsNot0Flag;


    public String getApplyId() {
        return applyId == null ? "" : applyId.trim();
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public String getSetId() {
        return setId == null ? "" : setId.trim();
    }

    public void setSetId(String setId) {
        this.setId = setId == null ? null : setId.trim();
    }

    public String getCategoryName() {
        return categoryName == null ? "" : categoryName.trim();
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public String getCategory() {
        return category == null ? "" : category.trim();
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getBrandName() {
        return brandName == null ? "" : brandName.trim();
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public String getBrand() {
        return brand == null ? "" : brand.trim();
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    public String getModelName() {
        return modelName == null ? "" : modelName.trim();
    }

    public void setModelName(String modelName) {
        this.modelName = modelName == null ? null : modelName.trim();
    }

    public String getModel() {
        return model == null ? "" : model.trim();
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getColourName() {
        return colourName == null ? "" : colourName.trim();
    }

    public void setColourName(String colourName) {
        this.colourName = colourName == null ? null : colourName.trim();
    }

    public String getColour() {
        return colour == null ? "" : colour.trim();
    }

    public void setColour(String colour) {
        this.colour = colour == null ? null : colour.trim();
    }

    public String getDeptName() {
        return deptName == null ? "" : deptName.trim();
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public String getUserKey() {
        return userKey == null ? "" : userKey.trim();
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey == null ? null : userKey.trim();
    }

    public String getName() {
        return name == null ? "" : name.trim();
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getAppDate() {
        return appDate;
    }

    public void setAppDate(Date appDate) {
        this.appDate = appDate;
    }

    public Integer getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(Integer applyNum) {
        this.applyNum = applyNum;
    }

    public Integer getMostNum() {
        return mostNum;
    }

    public void setMostNum(Integer mostNum) {
        this.mostNum = mostNum;
    }

    public String getStatus() {
        return status == null ? "" : status.trim();
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getUseStatus() {
        return useStatus == null ? "" : useStatus.trim();
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus == null ? null : useStatus.trim();
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
    
    public Integer getLeftNum() {
		return leftNum;
	}

	public void setLeftNum(Integer leftNum) {
		this.leftNum = leftNum;
	}

	public String getStatusIsNot0Flag() {
		return statusIsNot0Flag;
	}

	public void setStatusIsNot0Flag(String statusIsNot0Flag) {
		this.statusIsNot0Flag = statusIsNot0Flag;
	}

}

