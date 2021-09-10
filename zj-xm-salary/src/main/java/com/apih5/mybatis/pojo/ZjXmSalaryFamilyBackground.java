package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjXmSalaryFamilyBackground extends BasePojo {
    private String familyId;

    private String extensionId;

    private String relationship;

    private String name;

    private String unitPosition;

    private String address;

    private String phoneNumber;

    private String isUrgentLinkMan;

    private String remarks;

    private Integer sort;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public String getFamilyId() {
        return familyId == null ? "" : familyId.trim();
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId == null ? null : familyId.trim();
    }

    public String getExtensionId() {
        return extensionId == null ? "" : extensionId.trim();
    }

    public void setExtensionId(String extensionId) {
        this.extensionId = extensionId == null ? null : extensionId.trim();
    }

    public String getRelationship() {
        return relationship == null ? "" : relationship.trim();
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship == null ? null : relationship.trim();
    }

    public String getName() {
        return name == null ? "" : name.trim();
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUnitPosition() {
        return unitPosition == null ? "" : unitPosition.trim();
    }

    public void setUnitPosition(String unitPosition) {
        this.unitPosition = unitPosition == null ? null : unitPosition.trim();
    }

    public String getAddress() {
        return address == null ? "" : address.trim();
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber == null ? "" : phoneNumber.trim();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public String getIsUrgentLinkMan() {
        return isUrgentLinkMan == null ? "" : isUrgentLinkMan.trim();
    }

    public void setIsUrgentLinkMan(String isUrgentLinkMan) {
        this.isUrgentLinkMan = isUrgentLinkMan == null ? null : isUrgentLinkMan.trim();
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

