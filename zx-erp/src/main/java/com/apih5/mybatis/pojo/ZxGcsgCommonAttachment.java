package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxGcsgCommonAttachment extends BasePojo {
    // 主键
    private String uid;

    // 其他表主键
    private String otherId;

    // 业务表类型
    private String otherType;

    // 文件名称
    private String name;

    // 大小
    private String size;

    // 文件格式
    private String type;

    // WEB文件地址
    private String url;

    // WEB缩略图文件地址
    private String thumbUrl;

    // 手机文件地址
    private String mobileUrl;

    // 手机缩略图文件地址
    private String mobileThumbUrl;

    // 相对路径文件地址
    private String relativeUrl;

    // 相对路径缩略图文件地址
    private String relativeThumbUrl;

    // 附件分类
    private String fileType;

    // 相对路径(新)
    private String path;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

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

    public String getFileType() {
        return fileType == null ? "" : fileType.trim();
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public String getPath() {
        return path == null ? "" : path.trim();
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

}
