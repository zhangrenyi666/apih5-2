package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCcOrgResource extends BasePojo {
    // 主键
    private String id;

    // 资源ID
    private String resID;

    // 资源编号
    private String resCode;

    // 等级
    private String resName;

    // 规格型号
    private String spec;

    // 单位
    private String unit;

    // 机构ID
    private String orgID;

    // 标准单价
    private BigDecimal unitPrice;

    // 预算单价
    private BigDecimal budgPrice;

    // 资源类别
    private String bizType;

    // 计价方式
    private String priceType;

    // 是否是具体资源
    private String isRes;

    // 资源类型
    private String resStyle;

    // 编制机构
    private String orgName;

    // 是否局下达
    private String isGroup;

    // 备注
    private String remark;

    // projectID
    private String projectID;

    // isComplex
    private int isComplex=0;

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

    // pp6
    private String pp6;

    // pp7
    private String pp7;

    // pp8
    private String pp8;

    // pp9
    private String pp9;

    // pp10
    private String pp10;

    // resABC
    private String resABC;

    // sendTime
    private Date sendTime;

    // isMainMaterial
    private String isMainMaterial;

    // isSporadicMaterial
    private String isSporadicMaterial;

    // isUse
    private String isUse;

    // combProp
    private String combProp;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getResID() {
        return resID == null ? "" : resID.trim();
    }

    public void setResID(String resID) {
        this.resID = resID == null ? null : resID.trim();
    }

    public String getResCode() {
        return resCode == null ? "" : resCode.trim();
    }

    public void setResCode(String resCode) {
        this.resCode = resCode == null ? null : resCode.trim();
    }

    public String getResName() {
        return resName == null ? "" : resName.trim();
    }

    public void setResName(String resName) {
        this.resName = resName == null ? null : resName.trim();
    }

    public String getSpec() {
        return spec == null ? "" : spec.trim();
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getBudgPrice() {
        return budgPrice;
    }

    public void setBudgPrice(BigDecimal budgPrice) {
        this.budgPrice = budgPrice;
    }

    public String getBizType() {
        return bizType == null ? "" : bizType.trim();
    }

    public void setBizType(String bizType) {
        this.bizType = bizType == null ? null : bizType.trim();
    }

    public String getPriceType() {
        return priceType == null ? "" : priceType.trim();
    }

    public void setPriceType(String priceType) {
        this.priceType = priceType == null ? null : priceType.trim();
    }

    public String getIsRes() {
        return isRes == null ? "" : isRes.trim();
    }

    public void setIsRes(String isRes) {
        this.isRes = isRes == null ? null : isRes.trim();
    }

    public String getResStyle() {
        return resStyle == null ? "" : resStyle.trim();
    }

    public void setResStyle(String resStyle) {
        this.resStyle = resStyle == null ? null : resStyle.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getIsGroup() {
        return isGroup == null ? "" : isGroup.trim();
    }

    public void setIsGroup(String isGroup) {
        this.isGroup = isGroup == null ? null : isGroup.trim();
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getProjectID() {
        return projectID == null ? "" : projectID.trim();
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID == null ? null : projectID.trim();
    }

    public int getIsComplex() {
        return isComplex;
    }

    public void setIsComplex(int isComplex) {
        this.isComplex = isComplex;
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

    public String getResABC() {
        return resABC == null ? "" : resABC.trim();
    }

    public void setResABC(String resABC) {
        this.resABC = resABC == null ? null : resABC.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getIsMainMaterial() {
        return isMainMaterial == null ? "" : isMainMaterial.trim();
    }

    public void setIsMainMaterial(String isMainMaterial) {
        this.isMainMaterial = isMainMaterial == null ? null : isMainMaterial.trim();
    }

    public String getIsSporadicMaterial() {
        return isSporadicMaterial == null ? "" : isSporadicMaterial.trim();
    }

    public void setIsSporadicMaterial(String isSporadicMaterial) {
        this.isSporadicMaterial = isSporadicMaterial == null ? null : isSporadicMaterial.trim();
    }

    public String getIsUse() {
        return isUse == null ? "" : isUse.trim();
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse == null ? null : isUse.trim();
    }

    public String getCombProp() {
        return combProp == null ? "" : combProp.trim();
    }

    public void setCombProp(String combProp) {
        this.combProp = combProp == null ? null : combProp.trim();
    }

}
