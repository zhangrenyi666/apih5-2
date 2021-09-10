package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCrColCategory extends BasePojo {
    // 主键
    private String id;

    // 父编号
    private String parentID;

    // 单位工程代码
    private String catCode;

    // 单位工程
    private String catName;

    // bsid
    private String bsid;

    // resStyle
    private String resStyle;

    // 备注
    private String remark;

    // 发送时间
    private Date sendTime;

    // bizType
    private String bizType;

    // 子类List
    private List<ZxCrColCategory> childrenList;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getParentID() {
        return parentID == null ? "" : parentID.trim();
    }

    public void setParentID(String parentID) {
        this.parentID = parentID == null ? null : parentID.trim();
    }

    public String getCatCode() {
        return catCode == null ? "" : catCode.trim();
    }

    public void setCatCode(String catCode) {
        this.catCode = catCode == null ? null : catCode.trim();
    }

    public String getCatName() {
        return catName == null ? "" : catName.trim();
    }

    public void setCatName(String catName) {
        this.catName = catName == null ? null : catName.trim();
    }

    public String getBsid() {
        return bsid == null ? "" : bsid.trim();
    }

    public void setBsid(String bsid) {
        this.bsid = bsid == null ? null : bsid.trim();
    }

    public String getResStyle() {
        return resStyle == null ? "" : resStyle.trim();
    }

    public void setResStyle(String resStyle) {
        this.resStyle = resStyle == null ? null : resStyle.trim();
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getBizType() {
        return bizType == null ? "" : bizType.trim();
    }

    public void setBizType(String bizType) {
        this.bizType = bizType == null ? null : bizType.trim();
    }

    public List<ZxCrColCategory> getChildrenList() {
        return childrenList == null ? Lists.newArrayList() : childrenList;
    }

    public void setChildrenList(List<ZxCrColCategory> childrenList) {
        this.childrenList = childrenList == null ? null : childrenList;
    }

}
