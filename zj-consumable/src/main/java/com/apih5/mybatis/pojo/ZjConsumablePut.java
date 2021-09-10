package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjConsumablePut extends BasePojo {
    private String putId;

    private String setId;

    private String categoryName;

    private String category;

    private String brandName;

    private String brand;

    private String modelName;

    private String model;

    private String colourName;

    private String colour;

    private Integer thisAddNum;

    private Date putTime;

    private String remark;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private List<Date> putTimeSearch;
    
    private Date startTime;
    
    private Date endTime;
    
    public String getPutId() {
        return putId == null ? "" : putId.trim();
    }

    public void setPutId(String putId) {
        this.putId = putId == null ? null : putId.trim();
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

    public Integer getThisAddNum() {
        return thisAddNum;
    }

    public void setThisAddNum(Integer thisAddNum) {
        this.thisAddNum = thisAddNum;
    }

    public Date getPutTime() {
        return putTime;
    }

    public void setPutTime(Date putTime) {
        this.putTime = putTime;
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public List<Date> getPutTimeSearch() {
		return putTimeSearch;
	}

	public void setPutTimeSearch(List<Date> putTimeSearch) {
		this.putTimeSearch = putTimeSearch;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}

