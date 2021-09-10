package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import com.apih5.framework.entity.BasePojo;

public class ZxEqCooEquipItem extends BasePojo {
    private String id;

    private String masID;

    private String equipName;

    private String spec;

    private String model;

    private String power;

    private String outfactory;

    private Date outfactoryDate;

    private BigDecimal orginalValue;

    private BigDecimal leftValue;

    private String oldrate;

    private Date beginDate;

    private Date endDate;

    private String remark;

    private Date editTime;

    private String equipNo;

    private String equipID;

    private String acceptance;

    private String isSepcial;

    private String inspReport;

    private String inspCert;

    private String opCert;

    private String isOut;

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

    public String getMasID() {
        return masID == null ? "" : masID.trim();
    }

    public void setMasID(String masID) {
        this.masID = masID == null ? null : masID.trim();
    }

    public String getEquipName() {
        return equipName == null ? "" : equipName.trim();
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName == null ? null : equipName.trim();
    }

    public String getSpec() {
        return spec == null ? "" : spec.trim();
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getModel() {
        return model == null ? "" : model.trim();
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getPower() {
        return power == null ? "" : power.trim();
    }

    public void setPower(String power) {
        this.power = power == null ? null : power.trim();
    }

    public String getOutfactory() {
        return outfactory == null ? "" : outfactory.trim();
    }

    public void setOutfactory(String outfactory) {
        this.outfactory = outfactory == null ? null : outfactory.trim();
    }

    public Date getOutfactoryDate() {
        return outfactoryDate;
    }

    public void setOutfactoryDate(Date outfactoryDate) {
        this.outfactoryDate = outfactoryDate;
    }

    public BigDecimal getOrginalValue() {
        return orginalValue;
    }

    public void setOrginalValue(BigDecimal orginalValue) {
        this.orginalValue = orginalValue;
    }

    public BigDecimal getLeftValue() {
        return leftValue;
    }

    public void setLeftValue(BigDecimal leftValue) {
        this.leftValue = leftValue;
    }

    public String getOldrate() {
        return oldrate == null ? "" : oldrate.trim();
    }

    public void setOldrate(String oldrate) {
        this.oldrate = oldrate == null ? null : oldrate.trim();
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getEquipNo() {
        return equipNo == null ? "" : equipNo.trim();
    }

    public void setEquipNo(String equipNo) {
        this.equipNo = equipNo == null ? null : equipNo.trim();
    }

    public String getEquipID() {
        return equipID == null ? "" : equipID.trim();
    }

    public void setEquipID(String equipID) {
        this.equipID = equipID == null ? null : equipID.trim();
    }

    public String getAcceptance() {
        return acceptance == null ? "" : acceptance.trim();
    }

    public void setAcceptance(String acceptance) {
        this.acceptance = acceptance == null ? null : acceptance.trim();
    }

    public String getIsSepcial() {
        return isSepcial == null ? "" : isSepcial.trim();
    }

    public void setIsSepcial(String isSepcial) {
        this.isSepcial = isSepcial == null ? null : isSepcial.trim();
    }

    public String getInspReport() {
        return inspReport == null ? "" : inspReport.trim();
    }

    public void setInspReport(String inspReport) {
        this.inspReport = inspReport == null ? null : inspReport.trim();
    }

    public String getInspCert() {
        return inspCert == null ? "" : inspCert.trim();
    }

    public void setInspCert(String inspCert) {
        this.inspCert = inspCert == null ? null : inspCert.trim();
    }

    public String getOpCert() {
        return opCert == null ? "" : opCert.trim();
    }

    public void setOpCert(String opCert) {
        this.opCert = opCert == null ? null : opCert.trim();
    }

    public String getIsOut() {
        return isOut == null ? "" : isOut.trim();
    }

    public void setIsOut(String isOut) {
        this.isOut = isOut == null ? null : isOut.trim();
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

