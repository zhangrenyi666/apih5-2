package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZxEqEquipFactRealItem extends BasePojo {
    private String id;

    private String mainID;

    private String equipID;

    private String equipNo;

    private String equipName;

    private String spec;

    private String factory;

    private Date inDate;

    private String source;

    private String isOut;

    private Date editTime;

    private String carNo;

    private String supplier;

    private String realEquipID;

    private String realEquipNo;

    private String realEquipName;

    private String realUnit;

    private String realSpec;

    private String factItemID;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMainID() {
        return mainID == null ? "" : mainID.trim();
    }

    public void setMainID(String mainID) {
        this.mainID = mainID == null ? null : mainID.trim();
    }

    public String getEquipID() {
        return equipID == null ? "" : equipID.trim();
    }

    public void setEquipID(String equipID) {
        this.equipID = equipID == null ? null : equipID.trim();
    }

    public String getEquipNo() {
        return equipNo == null ? "" : equipNo.trim();
    }

    public void setEquipNo(String equipNo) {
        this.equipNo = equipNo == null ? null : equipNo.trim();
    }

    public String getEquipName() {
        return equipName == null ? "" : equipName.trim();
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName == null ? null : equipName.trim();
    }

    public String getSpec() {
        return spec == null ? "" : spec.trim();
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getFactory() {
        return factory == null ? "" : factory.trim();
    }

    public void setFactory(String factory) {
        this.factory = factory == null ? null : factory.trim();
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public String getSource() {
        return source == null ? "" : source.trim();
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getIsOut() {
        return isOut == null ? "" : isOut.trim();
    }

    public void setIsOut(String isOut) {
        this.isOut = isOut == null ? null : isOut.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getCarNo() {
        return carNo == null ? "" : carNo.trim();
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo == null ? null : carNo.trim();
    }

    public String getSupplier() {
        return supplier == null ? "" : supplier.trim();
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
    }

    public String getRealEquipID() {
        return realEquipID == null ? "" : realEquipID.trim();
    }

    public void setRealEquipID(String realEquipID) {
        this.realEquipID = realEquipID == null ? null : realEquipID.trim();
    }

    public String getRealEquipNo() {
        return realEquipNo == null ? "" : realEquipNo.trim();
    }

    public void setRealEquipNo(String realEquipNo) {
        this.realEquipNo = realEquipNo == null ? null : realEquipNo.trim();
    }

    public String getRealEquipName() {
        return realEquipName == null ? "" : realEquipName.trim();
    }

    public void setRealEquipName(String realEquipName) {
        this.realEquipName = realEquipName == null ? null : realEquipName.trim();
    }

    public String getRealUnit() {
        return realUnit == null ? "" : realUnit.trim();
    }

    public void setRealUnit(String realUnit) {
        this.realUnit = realUnit == null ? null : realUnit.trim();
    }

    public String getRealSpec() {
        return realSpec == null ? "" : realSpec.trim();
    }

    public void setRealSpec(String realSpec) {
        this.realSpec = realSpec == null ? null : realSpec.trim();
    }

    public String getFactItemID() {
        return factItemID == null ? "" : factItemID.trim();
    }

    public void setFactItemID(String factItemID) {
        this.factItemID = factItemID == null ? null : factItemID.trim();
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

