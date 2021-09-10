package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzQuality extends BasePojo {
    private String qualityId;

    private String designAdvistoryUnitStandardId;

    private String majorTypeId;

    private String majorTypeName;

    private String correspondQualityId;

    private String correspondQualityName;

    private String registeredUser;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZjTzFile> zjTzFileList;

    public String getQualityId() {
        return qualityId == null ? "" : qualityId.trim();
    }

    public void setQualityId(String qualityId) {
        this.qualityId = qualityId == null ? null : qualityId.trim();
    }

    public String getDesignAdvistoryUnitStandardId() {
        return designAdvistoryUnitStandardId == null ? "" : designAdvistoryUnitStandardId.trim();
    }

    public void setDesignAdvistoryUnitStandardId(String designAdvistoryUnitStandardId) {
        this.designAdvistoryUnitStandardId = designAdvistoryUnitStandardId == null ? null : designAdvistoryUnitStandardId.trim();
    }

    public String getMajorTypeId() {
        return majorTypeId == null ? "" : majorTypeId.trim();
    }

    public void setMajorTypeId(String majorTypeId) {
        this.majorTypeId = majorTypeId == null ? null : majorTypeId.trim();
    }

    public String getMajorTypeName() {
        return majorTypeName == null ? "" : majorTypeName.trim();
    }

    public void setMajorTypeName(String majorTypeName) {
        this.majorTypeName = majorTypeName == null ? null : majorTypeName.trim();
    }

    public String getCorrespondQualityId() {
        return correspondQualityId == null ? "" : correspondQualityId.trim();
    }

    public void setCorrespondQualityId(String correspondQualityId) {
        this.correspondQualityId = correspondQualityId == null ? null : correspondQualityId.trim();
    }

    public String getCorrespondQualityName() {
        return correspondQualityName == null ? "" : correspondQualityName.trim();
    }

    public void setCorrespondQualityName(String correspondQualityName) {
        this.correspondQualityName = correspondQualityName == null ? null : correspondQualityName.trim();
    }

    public String getRegisteredUser() {
        return registeredUser == null ? "" : registeredUser.trim();
    }

    public void setRegisteredUser(String registeredUser) {
        this.registeredUser = registeredUser == null ? null : registeredUser.trim();
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

    public List<ZjTzFile> getZjTzFileList() {
        return zjTzFileList;
    }

    public void setZjTzFileList(List<ZjTzFile> zjTzFileList) {
        this.zjTzFileList = zjTzFileList;
    }

}

