package com.apih5.mybatis.pojo;

import java.util.Date;
import com.apih5.framework.entity.BasePojo;

public class ZxErpFile extends BasePojo {
    private String uid;

    private String otherId;

    private String otherType;

    private String name;

    private String size;

    private String type;

    private String url;

    private String thumbUrl;

    private String mobileUrl;

    private String mobileThumbUrl;

    private String relativeUrl;

    private String relativeThumbUrl;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public String getUid() {
        return uid == null ? "" : uid.trim();
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getOtherId() {
        return otherId == null ? "" : otherId.trim();
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId == null ? null : otherId.trim();
    }

    public String getOtherType() {
        return otherType == null ? "" : otherType.trim();
    }

    public void setOtherType(String otherType) {
        this.otherType = otherType == null ? null : otherType.trim();
    }

    public String getName() {
        return name == null ? "" : name.trim();
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSize() {
        return size == null ? "" : size.trim();
    }

    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }

    public String getType() {
        return type == null ? "" : type.trim();
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getUrl() {
        return url == null ? "" : url.trim();
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getThumbUrl() {
        return thumbUrl == null ? "" : thumbUrl.trim();
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl == null ? null : thumbUrl.trim();
    }

    public String getMobileUrl() {
        return mobileUrl == null ? "" : mobileUrl.trim();
    }

    public void setMobileUrl(String mobileUrl) {
        this.mobileUrl = mobileUrl == null ? null : mobileUrl.trim();
    }

    public String getMobileThumbUrl() {
        return mobileThumbUrl == null ? "" : mobileThumbUrl.trim();
    }

    public void setMobileThumbUrl(String mobileThumbUrl) {
        this.mobileThumbUrl = mobileThumbUrl == null ? null : mobileThumbUrl.trim();
    }

    public String getRelativeUrl() {
        return relativeUrl == null ? "" : relativeUrl.trim();
    }

    public void setRelativeUrl(String relativeUrl) {
        this.relativeUrl = relativeUrl == null ? null : relativeUrl.trim();
    }

    public String getRelativeThumbUrl() {
        return relativeThumbUrl == null ? "" : relativeThumbUrl.trim();
    }

    public void setRelativeThumbUrl(String relativeThumbUrl) {
        this.relativeThumbUrl = relativeThumbUrl == null ? null : relativeThumbUrl.trim();
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

