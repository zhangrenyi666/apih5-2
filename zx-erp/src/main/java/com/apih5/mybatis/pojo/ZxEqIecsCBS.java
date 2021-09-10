package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZxEqIecsCBS extends BasePojo {
    private String iecsCBSID;

    private String orgID;

    private String orgName;

    private String code;

    private String name;

    private String unit;

    private Date updateTime;

    private String ecbsID;

    private String bsid;

    private String parentID;

    private BigDecimal contrQty;

    private BigDecimal alterQty;

    private BigDecimal contrTotalPrice;

    private BigDecimal alterTotalPrice;

    private String cbsType;

    private Integer deleted;

    private String remark;

    private String combProp;

    private String pp1;

    private String pp2;

    private String pp3;

    private String pp4;

    private String pp5;

    private String pp6;

    private String pp7;

    private String pp8;

    private String pp9;

    private String pp10;

    private Date editTime;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZxEqIecsCBS> childrenList;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getIecsCBSID() {
        return iecsCBSID == null ? "" : iecsCBSID.trim();
    }

    public void setIecsCBSID(String iecsCBSID) {
        this.iecsCBSID = iecsCBSID == null ? null : iecsCBSID.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getCode() {
        return code == null ? "" : code.trim();
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name == null ? "" : name.trim();
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getEcbsID() {
        return ecbsID == null ? "" : ecbsID.trim();
    }

    public void setEcbsID(String ecbsID) {
        this.ecbsID = ecbsID == null ? null : ecbsID.trim();
    }

    public String getBsid() {
        return bsid == null ? "" : bsid.trim();
    }

    public void setBsid(String bsid) {
        this.bsid = bsid == null ? null : bsid.trim();
    }

    public String getParentID() {
        return parentID == null ? "" : parentID.trim();
    }

    public void setParentID(String parentID) {
        this.parentID = parentID == null ? null : parentID.trim();
    }

    public BigDecimal getContrQty() {
        return contrQty;
    }

    public void setContrQty(BigDecimal contrQty) {
        this.contrQty = contrQty;
    }

    public BigDecimal getAlterQty() {
        return alterQty;
    }

    public void setAlterQty(BigDecimal alterQty) {
        this.alterQty = alterQty;
    }

    public BigDecimal getContrTotalPrice() {
        return contrTotalPrice;
    }

    public void setContrTotalPrice(BigDecimal contrTotalPrice) {
        this.contrTotalPrice = contrTotalPrice;
    }

    public BigDecimal getAlterTotalPrice() {
        return alterTotalPrice;
    }

    public void setAlterTotalPrice(BigDecimal alterTotalPrice) {
        this.alterTotalPrice = alterTotalPrice;
    }

    public String getCbsType() {
        return cbsType == null ? "" : cbsType.trim();
    }

    public void setCbsType(String cbsType) {
        this.cbsType = cbsType == null ? null : cbsType.trim();
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
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

    public List<ZxEqIecsCBS> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<ZxEqIecsCBS> childrenList) {
        this.childrenList = childrenList;
    }

}

