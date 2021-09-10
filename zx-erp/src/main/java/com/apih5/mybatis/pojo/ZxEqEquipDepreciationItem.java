package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import com.apih5.framework.entity.BasePojo;

public class ZxEqEquipDepreciationItem extends BasePojo {
    private String id;

    private String masID;

    private String equipID;

    private String useOrgID;

    private BigDecimal depreamout;

    private BigDecimal financeOrginalValue;

    private BigDecimal orginalValue;

    private BigDecimal leftValue;

    private String remark;

    private String orgID;

    private String auditStatus;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private String equipNo;

    private String equipName;

    private String financeNo;

    private String spec;

    private String model;

    private String factory;

    private Date outFactoryDate;

    private Date depreDate;

    private Date depreperiodDate;

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

    public String getEquipID() {
        return equipID == null ? "" : equipID.trim();
    }

    public void setEquipID(String equipID) {
        this.equipID = equipID == null ? null : equipID.trim();
    }

    public String getUseOrgID() {
        return useOrgID == null ? "" : useOrgID.trim();
    }

    public void setUseOrgID(String useOrgID) {
        this.useOrgID = useOrgID == null ? null : useOrgID.trim();
    }

    public BigDecimal getDepreamout() {
        return depreamout;
    }

    public void setDepreamout(BigDecimal depreamout) {
        this.depreamout = depreamout;
    }

    public BigDecimal getFinanceOrginalValue() {
        return financeOrginalValue;
    }

    public void setFinanceOrginalValue(BigDecimal financeOrginalValue) {
        this.financeOrginalValue = financeOrginalValue;
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

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getAuditStatus() {
        return auditStatus == null ? "" : auditStatus.trim();
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
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

    public String getEquipNo() {
        return equipNo == null ? "" : equipNo.trim();
    }

    public void setEquipNo(String equipNo) {
        this.equipNo = equipNo == null ? null : equipNo.trim();
    }

    public String getEquipName() {
        return equipName == null ? "" : equipName.trim();
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName == null ? null : equipName.trim();
    }

    public String getFinanceNo() {
        return financeNo == null ? "" : financeNo.trim();
    }

    public void setFinanceNo(String financeNo) {
        this.financeNo = financeNo == null ? null : financeNo.trim();
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

    public String getFactory() {
        return factory == null ? "" : factory.trim();
    }

    public void setFactory(String factory) {
        this.factory = factory == null ? null : factory.trim();
    }

    public Date getOutFactoryDate() {
        return outFactoryDate;
    }

    public void setOutFactoryDate(Date outFactoryDate) {
        this.outFactoryDate = outFactoryDate;
    }

    public Date getDepreDate() {
        return depreDate;
    }

    public void setDepreDate(Date depreDate) {
        this.depreDate = depreDate;
    }

    public Date getDepreperiodDate() {
        return depreperiodDate;
    }

    public void setDepreperiodDate(Date depreperiodDate) {
        this.depreperiodDate = depreperiodDate;
    }

}

