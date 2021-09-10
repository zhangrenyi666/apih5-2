package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzCompSupContent extends BasePojo {
    private String supContentId;

    private String compSupReportId;

    private String typeId;

    private String typeName;

    private String detail;

    private String needCorrectiveId;

    private String needCorrectiveName;

    private String correctiveCase;

    private Date correctiveDate;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZjTzFile> zjTzFileList;

    public String getSupContentId() {
        return supContentId == null ? "" : supContentId.trim();
    }

    public void setSupContentId(String supContentId) {
        this.supContentId = supContentId == null ? null : supContentId.trim();
    }

    public String getCompSupReportId() {
        return compSupReportId == null ? "" : compSupReportId.trim();
    }

    public void setCompSupReportId(String compSupReportId) {
        this.compSupReportId = compSupReportId == null ? null : compSupReportId.trim();
    }

    public String getTypeId() {
        return typeId == null ? "" : typeId.trim();
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    public String getTypeName() {
        return typeName == null ? "" : typeName.trim();
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getDetail() {
        return detail == null ? "" : detail.trim();
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public String getNeedCorrectiveId() {
        return needCorrectiveId == null ? "" : needCorrectiveId.trim();
    }

    public void setNeedCorrectiveId(String needCorrectiveId) {
        this.needCorrectiveId = needCorrectiveId == null ? null : needCorrectiveId.trim();
    }

    public String getNeedCorrectiveName() {
        return needCorrectiveName == null ? "" : needCorrectiveName.trim();
    }

    public void setNeedCorrectiveName(String needCorrectiveName) {
        this.needCorrectiveName = needCorrectiveName == null ? null : needCorrectiveName.trim();
    }

    public String getCorrectiveCase() {
        return correctiveCase == null ? "" : correctiveCase.trim();
    }

    public void setCorrectiveCase(String correctiveCase) {
        this.correctiveCase = correctiveCase == null ? null : correctiveCase.trim();
    }

    public Date getCorrectiveDate() {
        return correctiveDate;
    }

    public void setCorrectiveDate(Date correctiveDate) {
        this.correctiveDate = correctiveDate;
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

