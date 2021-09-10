package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;

public class SysMenu extends BasePojo {
    private String menuId;

    private String menuName;

    private String menuUrl;

    private String menuParentId;

    private int sort;

    private String menuPath;

    private String menuPathName;

    private String menuFlag;

    private int pGId;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private String userKey;

    private String menuToMove;

    private String menuParent;

    private String menuAfter;

    private String menuBefore;

    private String oldMenuPath;

    private String oldMenuPathName;

    public String getMenuId() {
        return menuId == null ? "" : menuId.trim();
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
    }

    public String getMenuName() {
        return menuName == null ? "" : menuName.trim();
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getMenuUrl() {
        return menuUrl == null ? "" : menuUrl.trim();
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public String getMenuParentId() {
        return menuParentId == null ? "" : menuParentId.trim();
    }

    public void setMenuParentId(String menuParentId) {
        this.menuParentId = menuParentId == null ? null : menuParentId.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getMenuPath() {
        return menuPath == null ? "" : menuPath.trim();
    }

    public void setMenuPath(String menuPath) {
        this.menuPath = menuPath == null ? null : menuPath.trim();
    }

    public String getMenuPathName() {
        return menuPathName == null ? "" : menuPathName.trim();
    }

    public void setMenuPathName(String menuPathName) {
        this.menuPathName = menuPathName == null ? null : menuPathName.trim();
    }

    public String getMenuFlag() {
        return menuFlag == null ? "" : menuFlag.trim();
    }

    public void setMenuFlag(String menuFlag) {
        this.menuFlag = menuFlag == null ? null : menuFlag.trim();
    }

    public int getPGId() {
        return pGId;
    }

    public void setPGId(int pGId) {
        this.pGId = pGId;
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

    public String getUserKey() {
        return userKey == null ? "" : userKey.trim();
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey == null ? null : userKey.trim();
    }

    public String getMenuToMove() {
        return menuToMove == null ? "" : menuToMove.trim();
    }

    public void setMenuToMove(String menuToMove) {
        this.menuToMove = menuToMove == null ? null : menuToMove.trim();
    }

    public String getMenuParent() {
        return menuParent == null ? "" : menuParent.trim();
    }

    public void setMenuParent(String menuParent) {
        this.menuParent = menuParent == null ? null : menuParent.trim();
    }

    public String getMenuAfter() {
        return menuAfter == null ? "" : menuAfter.trim();
    }

    public void setMenuAfter(String menuAfter) {
        this.menuAfter = menuAfter == null ? null : menuAfter.trim();
    }

    public String getMenuBefore() {
        return menuBefore == null ? "" : menuBefore.trim();
    }

    public void setMenuBefore(String menuBefore) {
        this.menuBefore = menuBefore == null ? null : menuBefore.trim();
    }

    public String getOldMenuPath() {
        return oldMenuPath == null ? "" : oldMenuPath.trim();
    }

    public void setOldMenuPath(String oldMenuPath) {
        this.oldMenuPath = oldMenuPath == null ? null : oldMenuPath.trim();
    }

    public String getOldMenuPathName() {
        return oldMenuPathName == null ? "" : oldMenuPathName.trim();
    }

    public void setOldMenuPathName(String oldMenuPathName) {
        this.oldMenuPathName = oldMenuPathName == null ? null : oldMenuPathName.trim();
    }

}

