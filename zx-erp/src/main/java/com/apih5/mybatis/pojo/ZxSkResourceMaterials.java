package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZxSkResourceMaterials extends BasePojo {
    private String id;

    private String categoryID;

    private String resCode;

    private String resName;

    private String unit;

    private String spec;

    private String tempNo;

    private String resStyle;

    private String bizType;

    private String enableFlag;

    private String orgLevel;

    private String orgID;

    private String orgName;

    private BigDecimal refPrice;

    private String refpriceType;

    private Integer isComplex;

    private String remark;

    private String combProp;

    private String sourceCode;

    private String abcCategory;

    private String isGroup;

    private Date sendTime;

    private String isMainMaterial;

    private String isSporadicMaterial;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private String joinName;

    private String label;

    private String value;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCategoryID() {
        return categoryID == null ? "" : categoryID.trim();
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID == null ? null : categoryID.trim();
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

    public String getTempNo() {
        return tempNo == null ? "" : tempNo.trim();
    }

    public void setTempNo(String tempNo) {
        this.tempNo = tempNo == null ? null : tempNo.trim();
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

    public String getEnableFlag() {
        return enableFlag == null ? "" : enableFlag.trim();
    }

    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag == null ? null : enableFlag.trim();
    }

    public String getOrgLevel() {
        return orgLevel == null ? "" : orgLevel.trim();
    }

    public void setOrgLevel(String orgLevel) {
        this.orgLevel = orgLevel == null ? null : orgLevel.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public BigDecimal getRefPrice() {
        return refPrice;
    }

    public void setRefPrice(BigDecimal refPrice) {
        this.refPrice = refPrice;
    }

    public String getRefpriceType() {
        return refpriceType == null ? "" : refpriceType.trim();
    }

    public void setRefpriceType(String refpriceType) {
        this.refpriceType = refpriceType == null ? null : refpriceType.trim();
    }

    public Integer getIsComplex() {
        return isComplex;
    }

    public void setIsComplex(Integer isComplex) {
        this.isComplex = isComplex;
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

    public String getSourceCode() {
        return sourceCode == null ? "" : sourceCode.trim();
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode == null ? null : sourceCode.trim();
    }

    public String getAbcCategory() {
        return abcCategory == null ? "" : abcCategory.trim();
    }

    public void setAbcCategory(String abcCategory) {
        this.abcCategory = abcCategory == null ? null : abcCategory.trim();
    }

    public String getIsGroup() {
        return isGroup == null ? "" : isGroup.trim();
    }

    public void setIsGroup(String isGroup) {
        this.isGroup = isGroup == null ? null : isGroup.trim();
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

    public String getJoinName() {
        return joinName == null ? "" : joinName.trim();
    }

    public void setJoinName(String joinName) {
        this.joinName = joinName == null ? null : joinName.trim();
    }

    public String getLabel() {
        return label == null ? "" : label.trim();
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public String getValue() {
        return value == null ? "" : value.trim();
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

}

