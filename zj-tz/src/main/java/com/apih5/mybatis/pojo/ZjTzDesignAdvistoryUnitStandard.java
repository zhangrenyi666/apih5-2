package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzDesignAdvistoryUnitStandard extends BasePojo {
    private String designAdvistoryUnitStandardId;

    private String unitName;

    private String orgCode;

    private String unitTypeId;

    private String unitTypeName;

    private String inOutUnitId;

    private String inOutUnitName;

    private String libraryId;

    private String libraryName;

    private String remarks;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZjTzFile> zjTzFileList;

    private List<ZjTzQuality> zjTzQualityList;

    private String majorTypeName;

    private String correspondQualityName;

    public String getDesignAdvistoryUnitStandardId() {
        return designAdvistoryUnitStandardId == null ? "" : designAdvistoryUnitStandardId.trim();
    }

    public void setDesignAdvistoryUnitStandardId(String designAdvistoryUnitStandardId) {
        this.designAdvistoryUnitStandardId = designAdvistoryUnitStandardId == null ? null : designAdvistoryUnitStandardId.trim();
    }

    public String getUnitName() {
        return unitName == null ? "" : unitName.trim();
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public String getOrgCode() {
        return orgCode == null ? "" : orgCode.trim();
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public String getUnitTypeId() {
        return unitTypeId == null ? "" : unitTypeId.trim();
    }

    public void setUnitTypeId(String unitTypeId) {
        this.unitTypeId = unitTypeId == null ? null : unitTypeId.trim();
    }

    public String getUnitTypeName() {
        return unitTypeName == null ? "" : unitTypeName.trim();
    }

    public void setUnitTypeName(String unitTypeName) {
        this.unitTypeName = unitTypeName == null ? null : unitTypeName.trim();
    }

    public String getInOutUnitId() {
        return inOutUnitId == null ? "" : inOutUnitId.trim();
    }

    public void setInOutUnitId(String inOutUnitId) {
        this.inOutUnitId = inOutUnitId == null ? null : inOutUnitId.trim();
    }

    public String getInOutUnitName() {
        return inOutUnitName == null ? "" : inOutUnitName.trim();
    }

    public void setInOutUnitName(String inOutUnitName) {
        this.inOutUnitName = inOutUnitName == null ? null : inOutUnitName.trim();
    }

    public String getLibraryId() {
        return libraryId == null ? "" : libraryId.trim();
    }

    public void setLibraryId(String libraryId) {
        this.libraryId = libraryId == null ? null : libraryId.trim();
    }

    public String getLibraryName() {
        return libraryName == null ? "" : libraryName.trim();
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName == null ? null : libraryName.trim();
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
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

    public List<ZjTzQuality> getZjTzQualityList() {
        return zjTzQualityList;
    }

    public void setZjTzQualityList(List<ZjTzQuality> zjTzQualityList) {
        this.zjTzQualityList = zjTzQualityList;
    }

    public String getMajorTypeName() {
        return majorTypeName == null ? "" : majorTypeName.trim();
    }

    public void setMajorTypeName(String majorTypeName) {
        this.majorTypeName = majorTypeName == null ? null : majorTypeName.trim();
    }

    public String getCorrespondQualityName() {
        return correspondQualityName == null ? "" : correspondQualityName.trim();
    }

    public void setCorrespondQualityName(String correspondQualityName) {
        this.correspondQualityName = correspondQualityName == null ? null : correspondQualityName.trim();
    }

}

