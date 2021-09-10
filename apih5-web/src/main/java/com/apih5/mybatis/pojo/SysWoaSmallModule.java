package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;

public class SysWoaSmallModule extends BasePojo {
    private String smallModuleId;

    private String todoShow;

    private String smallModuleType;

    private String smallDataCountId;

    private String smallModuleTitle;

    private String smallModuleLink;

    private String smallAppLink;

    private String smallWebLink;

    private String smallModuleIcon;

    private String smallModuleEventIcon;

    private String largelModuleId;

    private String pcMenuRoleFlag;

    private String pcMenuId;

    private Integer smallModuleSort;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private int smallModuleCount = 0;

    public String getSmallModuleId() {
        return smallModuleId == null ? "" : smallModuleId.trim();
    }

    public void setSmallModuleId(String smallModuleId) {
        this.smallModuleId = smallModuleId == null ? null : smallModuleId.trim();
    }

    public String getTodoShow() {
        return todoShow == null ? "" : todoShow.trim();
    }

    public void setTodoShow(String todoShow) {
        this.todoShow = todoShow == null ? null : todoShow.trim();
    }

    public String getSmallModuleType() {
        return smallModuleType == null ? "" : smallModuleType.trim();
    }

    public void setSmallModuleType(String smallModuleType) {
        this.smallModuleType = smallModuleType == null ? null : smallModuleType.trim();
    }

    public String getSmallDataCountId() {
        return smallDataCountId == null ? "" : smallDataCountId.trim();
    }

    public void setSmallDataCountId(String smallDataCountId) {
        this.smallDataCountId = smallDataCountId == null ? null : smallDataCountId.trim();
    }

    public String getSmallModuleTitle() {
        return smallModuleTitle == null ? "" : smallModuleTitle.trim();
    }

    public void setSmallModuleTitle(String smallModuleTitle) {
        this.smallModuleTitle = smallModuleTitle == null ? null : smallModuleTitle.trim();
    }

    public String getSmallModuleLink() {
        return smallModuleLink == null ? "" : smallModuleLink.trim();
    }

    public void setSmallModuleLink(String smallModuleLink) {
        this.smallModuleLink = smallModuleLink == null ? null : smallModuleLink.trim();
    }

    public String getSmallAppLink() {
        return smallAppLink == null ? "" : smallAppLink.trim();
    }

    public void setSmallAppLink(String smallAppLink) {
        this.smallAppLink = smallAppLink == null ? null : smallAppLink.trim();
    }

    public String getSmallWebLink() {
        return smallWebLink == null ? "" : smallWebLink.trim();
    }

    public void setSmallWebLink(String smallWebLink) {
        this.smallWebLink = smallWebLink == null ? null : smallWebLink.trim();
    }

    public String getSmallModuleIcon() {
        return smallModuleIcon == null ? "" : smallModuleIcon.trim();
    }

    public void setSmallModuleIcon(String smallModuleIcon) {
        this.smallModuleIcon = smallModuleIcon == null ? null : smallModuleIcon.trim();
    }

    public String getSmallModuleEventIcon() {
        return smallModuleEventIcon == null ? "" : smallModuleEventIcon.trim();
    }

    public void setSmallModuleEventIcon(String smallModuleEventIcon) {
        this.smallModuleEventIcon = smallModuleEventIcon == null ? null : smallModuleEventIcon.trim();
    }

    public String getLargelModuleId() {
        return largelModuleId == null ? "" : largelModuleId.trim();
    }

    public void setLargelModuleId(String largelModuleId) {
        this.largelModuleId = largelModuleId == null ? null : largelModuleId.trim();
    }

    public String getPcMenuRoleFlag() {
        return pcMenuRoleFlag == null ? "" : pcMenuRoleFlag.trim();
    }

    public void setPcMenuRoleFlag(String pcMenuRoleFlag) {
        this.pcMenuRoleFlag = pcMenuRoleFlag == null ? null : pcMenuRoleFlag.trim();
    }

    public String getPcMenuId() {
        return pcMenuId == null ? "" : pcMenuId.trim();
    }

    public void setPcMenuId(String pcMenuId) {
        this.pcMenuId = pcMenuId == null ? null : pcMenuId.trim();
    }

    public Integer getSmallModuleSort() {
        return smallModuleSort;
    }

    public void setSmallModuleSort(Integer smallModuleSort) {
        this.smallModuleSort = smallModuleSort;
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

