package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;

public class SysWoaAddFlow extends BasePojo {
    private String addPageId;

    private String moduleType;

    private String moduleTitle;

    private String moduleLink;

    private String moduleAppLink;

    private String moduleIcon;

    private String companyId;

    private Integer moduleSort;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private int smallModuleCount = 0;

    public String getAddPageId() {
        return addPageId == null ? "" : addPageId.trim();
    }

    public void setAddPageId(String addPageId) {
        this.addPageId = addPageId == null ? null : addPageId.trim();
    }

    public String getModuleType() {
        return moduleType == null ? "" : moduleType.trim();
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType == null ? null : moduleType.trim();
    }

    public String getModuleTitle() {
        return moduleTitle == null ? "" : moduleTitle.trim();
    }

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle == null ? null : moduleTitle.trim();
    }

    public String getModuleLink() {
        return moduleLink == null ? "" : moduleLink.trim();
    }

    public void setModuleLink(String moduleLink) {
        this.moduleLink = moduleLink == null ? null : moduleLink.trim();
    }

    public String getModuleAppLink() {
        return moduleAppLink == null ? "" : moduleAppLink.trim();
    }

    public void setModuleAppLink(String moduleAppLink) {
        this.moduleAppLink = moduleAppLink == null ? null : moduleAppLink.trim();
    }

    public String getModuleIcon() {
        return moduleIcon == null ? "" : moduleIcon.trim();
    }

    public void setModuleIcon(String moduleIcon) {
        this.moduleIcon = moduleIcon == null ? null : moduleIcon.trim();
    }

    public String getCompanyId() {
        return companyId == null ? "" : companyId.trim();
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public Integer getModuleSort() {
        return moduleSort;
    }

    public void setModuleSort(Integer moduleSort) {
        this.moduleSort = moduleSort;
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

    public int getSmallModuleCount() {
        return smallModuleCount;
    }

    public void setSmallModuleCount(int smallModuleCount) {
        this.smallModuleCount = smallModuleCount;
    }

}

