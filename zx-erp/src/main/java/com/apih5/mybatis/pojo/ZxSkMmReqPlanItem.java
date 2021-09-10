package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZxSkMmReqPlanItem extends BasePojo {
    private String id;

    private String mmReqPlanID;

    private String cbsID;

    private String cbsName;

    private String resID;

    private String resCode;

    private String resName;

    private String unit;

    private String spec;

    private BigDecimal price;

    private BigDecimal purNum;

    private BigDecimal totalMoney;

    private BigDecimal nextMonthNum;

    private BigDecimal nextMonthAmt;

    private String remark;

    private String combProp;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMmReqPlanID() {
        return mmReqPlanID == null ? "" : mmReqPlanID.trim();
    }

    public void setMmReqPlanID(String mmReqPlanID) {
        this.mmReqPlanID = mmReqPlanID == null ? null : mmReqPlanID.trim();
    }

    public String getCbsID() {
        return cbsID == null ? "" : cbsID.trim();
    }

    public void setCbsID(String cbsID) {
        this.cbsID = cbsID == null ? null : cbsID.trim();
    }

    public String getCbsName() {
        return cbsName == null ? "" : cbsName.trim();
    }

    public void setCbsName(String cbsName) {
        this.cbsName = cbsName == null ? null : cbsName.trim();
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

    public BigDecimal getPurNum() {
        return purNum;
    }

    public void setPurNum(BigDecimal purNum) {
        this.purNum = purNum;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getNextMonthNum() {
        return nextMonthNum;
    }

    public void setNextMonthNum(BigDecimal nextMonthNum) {
        this.nextMonthNum = nextMonthNum;
    }

    public BigDecimal getNextMonthAmt() {
        return nextMonthAmt;
    }

    public void setNextMonthAmt(BigDecimal nextMonthAmt) {
        this.nextMonthAmt = nextMonthAmt;
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

