package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZxEqUseEquipIn extends BasePojo {
    private String id;

    private String useEquipID;

    private String ownerOrgID;

    private String ownerOrg;

    private String useOrg;

    private String useOrgId;

    private String locality;

    private String equipMuID;

    private String equipCbsID;

    private String resCatalog;

    private String resCatalogID;

    private String equipNo;

    private String equipName;

    private String spec;

    private String model;

    private String brandName;

    private String measureUnit;

    private String origin;

    private String equipType;

    private BigDecimal powerValue;

    private String powerUnit;

    private BigDecimal motoAbility;

    private String abilityUnit;

    private String fuelExpendUnit;

    private BigDecimal unitFuelExpend;

    private String operater;

    private String technicalPosition;

    private String useStatus;

    private Date inDate;

    private Date outDate;

    private String remark;

    private String refEquipID;

    private String bizType;

    private String acceptance;

    private String isSepcial;

    private String inspReport;

    private String inspCert;

    private String opCert;

    private Date mainoutfactory;

    private String outfactory;

    private String orginalValue;

    private Date regdate;

    private String planNo;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZxErpFile> fileList;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUseEquipID() {
        return useEquipID == null ? "" : useEquipID.trim();
    }

    public void setUseEquipID(String useEquipID) {
        this.useEquipID = useEquipID == null ? null : useEquipID.trim();
    }

    public String getOwnerOrgID() {
        return ownerOrgID == null ? "" : ownerOrgID.trim();
    }

    public void setOwnerOrgID(String ownerOrgID) {
        this.ownerOrgID = ownerOrgID == null ? null : ownerOrgID.trim();
    }

    public String getOwnerOrg() {
        return ownerOrg == null ? "" : ownerOrg.trim();
    }

    public void setOwnerOrg(String ownerOrg) {
        this.ownerOrg = ownerOrg == null ? null : ownerOrg.trim();
    }

    public String getUseOrg() {
        return useOrg == null ? "" : useOrg.trim();
    }

    public void setUseOrg(String useOrg) {
        this.useOrg = useOrg == null ? null : useOrg.trim();
    }

    public String getUseOrgId() {
        return useOrgId == null ? "" : useOrgId.trim();
    }

    public void setUseOrgId(String useOrgId) {
        this.useOrgId = useOrgId == null ? null : useOrgId.trim();
    }

    public String getLocality() {
        return locality == null ? "" : locality.trim();
    }

    public void setLocality(String locality) {
        this.locality = locality == null ? null : locality.trim();
    }

    public String getEquipMuID() {
        return equipMuID == null ? "" : equipMuID.trim();
    }

    public void setEquipMuID(String equipMuID) {
        this.equipMuID = equipMuID == null ? null : equipMuID.trim();
    }

    public String getEquipCbsID() {
        return equipCbsID == null ? "" : equipCbsID.trim();
    }

    public void setEquipCbsID(String equipCbsID) {
        this.equipCbsID = equipCbsID == null ? null : equipCbsID.trim();
    }

    public String getResCatalog() {
        return resCatalog == null ? "" : resCatalog.trim();
    }

    public void setResCatalog(String resCatalog) {
        this.resCatalog = resCatalog == null ? null : resCatalog.trim();
    }

    public String getResCatalogID() {
        return resCatalogID == null ? "" : resCatalogID.trim();
    }

    public void setResCatalogID(String resCatalogID) {
        this.resCatalogID = resCatalogID == null ? null : resCatalogID.trim();
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

    public String getBrandName() {
        return brandName == null ? "" : brandName.trim();
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public String getMeasureUnit() {
        return measureUnit == null ? "" : measureUnit.trim();
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit == null ? null : measureUnit.trim();
    }

    public String getOrigin() {
        return origin == null ? "" : origin.trim();
    }

    public void setOrigin(String origin) {
        this.origin = origin == null ? null : origin.trim();
    }

    public String getEquipType() {
        return equipType == null ? "" : equipType.trim();
    }

    public void setEquipType(String equipType) {
        this.equipType = equipType == null ? null : equipType.trim();
    }

    public BigDecimal getPowerValue() {
        return powerValue;
    }

    public void setPowerValue(BigDecimal powerValue) {
        this.powerValue = powerValue;
    }

    public String getPowerUnit() {
        return powerUnit == null ? "" : powerUnit.trim();
    }

    public void setPowerUnit(String powerUnit) {
        this.powerUnit = powerUnit == null ? null : powerUnit.trim();
    }

    public BigDecimal getMotoAbility() {
        return motoAbility;
    }

    public void setMotoAbility(BigDecimal motoAbility) {
        this.motoAbility = motoAbility;
    }

    public String getAbilityUnit() {
        return abilityUnit == null ? "" : abilityUnit.trim();
    }

    public void setAbilityUnit(String abilityUnit) {
        this.abilityUnit = abilityUnit == null ? null : abilityUnit.trim();
    }

    public String getFuelExpendUnit() {
        return fuelExpendUnit == null ? "" : fuelExpendUnit.trim();
    }

    public void setFuelExpendUnit(String fuelExpendUnit) {
        this.fuelExpendUnit = fuelExpendUnit == null ? null : fuelExpendUnit.trim();
    }

    public BigDecimal getUnitFuelExpend() {
        return unitFuelExpend;
    }

    public void setUnitFuelExpend(BigDecimal unitFuelExpend) {
        this.unitFuelExpend = unitFuelExpend;
    }

    public String getOperater() {
        return operater == null ? "" : operater.trim();
    }

    public void setOperater(String operater) {
        this.operater = operater == null ? null : operater.trim();
    }

    public String getTechnicalPosition() {
        return technicalPosition == null ? "" : technicalPosition.trim();
    }

    public void setTechnicalPosition(String technicalPosition) {
        this.technicalPosition = technicalPosition == null ? null : technicalPosition.trim();
    }

    public String getUseStatus() {
        return useStatus == null ? "" : useStatus.trim();
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus == null ? null : useStatus.trim();
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getRefEquipID() {
        return refEquipID == null ? "" : refEquipID.trim();
    }

    public void setRefEquipID(String refEquipID) {
        this.refEquipID = refEquipID == null ? null : refEquipID.trim();
    }

    public String getBizType() {
        return bizType == null ? "" : bizType.trim();
    }

    public void setBizType(String bizType) {
        this.bizType = bizType == null ? null : bizType.trim();
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

    public Date getMainoutfactory() {
        return mainoutfactory;
    }

    public void setMainoutfactory(Date mainoutfactory) {
        this.mainoutfactory = mainoutfactory;
    }

    public String getOutfactory() {
        return outfactory == null ? "" : outfactory.trim();
    }

    public void setOutfactory(String outfactory) {
        this.outfactory = outfactory == null ? null : outfactory.trim();
    }

    public String getOrginalValue() {
        return orginalValue == null ? "" : orginalValue.trim();
    }

    public void setOrginalValue(String orginalValue) {
        this.orginalValue = orginalValue == null ? null : orginalValue.trim();
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public String getPlanNo() {
        return planNo == null ? "" : planNo.trim();
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo == null ? null : planNo.trim();
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

    public List<ZxErpFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<ZxErpFile> fileList) {
        this.fileList = fileList;
    }

}

