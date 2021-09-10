package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCcResCategory extends BasePojo {
    // 主键
    private String id;

    // parentID
    private String parentID;

    // bsid
    private String bsid;

    // 编号
    private String catCode;

    // 资质名称
    private String catName;

    // 单位
    private String unit;

    // 规格型号
    private String spec;

    // 单价
    private BigDecimal price;

    // 资源类型
    private String resStyle;

    // 业务类型
    private String bizType;

    // 备注
    private String remarks;

    // combProp
    private String combProp;

    // pp1
    private String pp1;

    // pp2
    private String pp2;

    // pp3
    private String pp3;

    // pp4
    private String pp4;

    // pp5
    private String pp5;

    // pp1
    private String pp6;

    // pp7
    private String pp7;

    // pp8
    private String pp8;

    // pp9
    private String pp9;

    // pp10
    private String pp10;

    // 是否已下达
    private String isGroup;

    // orgID
    private String orgID;

    // parentName
    private String parentName;

    // childrenList
    private List<ZxCcResCategory> childrenList;

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

    public String getBsid() {
        return bsid == null ? "" : bsid.trim();
    }

    public void setBsid(String bsid) {
        this.bsid = bsid == null ? null : bsid.trim();
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

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getSpec() {
        return spec == null ? "" : spec.trim();
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getResStyle() {
        return resStyle == null ? "" : resStyle.trim();
    }

    public void setResStyle(String resStyle) {
        this.resStyle = resStyle == null ? null : resStyle.trim();
    }

    public String getBizType() {
        return bizType == null ? "" : bizType.trim();
    }

    public void setBizType(String bizType) {
        this.bizType = bizType == null ? null : bizType.trim();
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getCombProp() {
        return combProp == null ? "" : combProp.trim();
    }

    public void setCombProp(String combProp) {
        this.combProp = combProp == null ? null : combProp.trim();
    }

    public String getPp1() {
        return pp1 == null ? "" : pp1.trim();
    }

    public void setPp1(String pp1) {
        this.pp1 = pp1 == null ? null : pp1.trim();
    }

    public String getPp2() {
        return pp2 == null ? "" : pp2.trim();
    }

    public void setPp2(String pp2) {
        this.pp2 = pp2 == null ? null : pp2.trim();
    }

    public String getPp3() {
        return pp3 == null ? "" : pp3.trim();
    }

    public void setPp3(String pp3) {
        this.pp3 = pp3 == null ? null : pp3.trim();
    }

    public String getPp4() {
        return pp4 == null ? "" : pp4.trim();
    }

    public void setPp4(String pp4) {
        this.pp4 = pp4 == null ? null : pp4.trim();
    }

    public String getPp5() {
        return pp5 == null ? "" : pp5.trim();
    }

    public void setPp5(String pp5) {
        this.pp5 = pp5 == null ? null : pp5.trim();
    }

    public String getPp6() {
        return pp6 == null ? "" : pp6.trim();
    }

    public void setPp6(String pp6) {
        this.pp6 = pp6 == null ? null : pp6.trim();
    }

    public String getPp7() {
        return pp7 == null ? "" : pp7.trim();
    }

    public void setPp7(String pp7) {
        this.pp7 = pp7 == null ? null : pp7.trim();
    }

    public String getPp8() {
        return pp8 == null ? "" : pp8.trim();
    }

    public void setPp8(String pp8) {
        this.pp8 = pp8 == null ? null : pp8.trim();
    }

    public String getPp9() {
        return pp9 == null ? "" : pp9.trim();
    }

    public void setPp9(String pp9) {
        this.pp9 = pp9 == null ? null : pp9.trim();
    }

    public String getPp10() {
        return pp10 == null ? "" : pp10.trim();
    }

    public void setPp10(String pp10) {
        this.pp10 = pp10 == null ? null : pp10.trim();
    }

    public String getIsGroup() {
        return isGroup == null ? "" : isGroup.trim();
    }

    public void setIsGroup(String isGroup) {
        this.isGroup = isGroup == null ? null : isGroup.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getParentName() {
        return parentName == null ? "" : parentName.trim();
    }

    public void setParentName(String parentName) {
        this.parentName = parentName == null ? null : parentName.trim();
    }

    public List<ZxCcResCategory> getChildrenList() {
        return childrenList == null ? Lists.newArrayList() : childrenList;
    }

    public void setChildrenList(List<ZxCcResCategory> childrenList) {
        this.childrenList = childrenList == null ? null : childrenList;
    }

}
