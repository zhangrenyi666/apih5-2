package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjConsumableSet extends BasePojo {
    private String setId;

    private String category;

    private String brand;

    private String model;

    private String colour;

    private String useState;

    private String codePid;

    private String typeFlag;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private String categoryEq;

    private String brandEq;

    private String modelEq;

    private String colourEq;

    private List<ZjConsumableSet> childrenList;

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

    public String getUseState() {
        return useState == null ? "" : useState.trim();
    }

    public void setUseState(String useState) {
        this.useState = useState == null ? null : useState.trim();
    }

    public String getCodePid() {
        return codePid == null ? "" : codePid.trim();
    }

    public void setCodePid(String codePid) {
        this.codePid = codePid == null ? null : codePid.trim();
    }

    public String getTypeFlag() {
        return typeFlag == null ? "" : typeFlag.trim();
    }

    public void setTypeFlag(String typeFlag) {
        this.typeFlag = typeFlag == null ? null : typeFlag.trim();
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

    public String getCategoryEq() {
        return categoryEq == null ? "" : categoryEq.trim();
    }

    public void setCategoryEq(String categoryEq) {
        this.categoryEq = categoryEq == null ? null : categoryEq.trim();
    }

    public String getBrandEq() {
        return brandEq == null ? "" : brandEq.trim();
    }

    public void setBrandEq(String brandEq) {
        this.brandEq = brandEq == null ? null : brandEq.trim();
    }

    public String getModelEq() {
        return modelEq == null ? "" : modelEq.trim();
    }

    public void setModelEq(String modelEq) {
        this.modelEq = modelEq == null ? null : modelEq.trim();
    }

    public String getColourEq() {
        return colourEq == null ? "" : colourEq.trim();
    }

    public void setColourEq(String colourEq) {
        this.colourEq = colourEq == null ? null : colourEq.trim();
    }

	public List<ZjConsumableSet> getChildrenList() {
		return childrenList;
	}

	public void setChildrenList(List<ZjConsumableSet> childrenList) {
		this.childrenList = childrenList;
	}
    
}

