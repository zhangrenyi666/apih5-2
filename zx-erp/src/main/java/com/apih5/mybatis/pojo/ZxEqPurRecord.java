package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZxEqPurRecord extends BasePojo {
    private String id;

    private String abcType;

    private String equipManNo;

    private String orgID;

    private String orgName;

    private String contractID;

    private String supplieID;

    private String source;

    private Date purDate;

    private String purPlace;

    private String useProjID;

    private String uesProject;

    private String financeNo;

    private String resCatalog;

    private String resCatalogID;

    private String equipno;

    private String spec;

    private String model;

    private String factory;

    private Date outfactoryDate;

    private String oldrate;

    private String mainFactory;

    private String mainspec;

    private BigDecimal mainpower;

    private String mainserial;

    private Date mainoutfactory;

    private String viceFactory;

    private String vicespec;

    private BigDecimal vicepower;

    private String viceserial;

    private Date viceoutfactory;

    private String downfactory;

    private String downspec;

    private String downserial;

    private Date downoutfactory;

    private BigDecimal orginalvalue;

    private BigDecimal vicevalue;

    private BigDecimal tranvalue;

    private BigDecimal totalvalue;

    private String passNo;

    private String isimported;

    private String remark;

    private String outFactorySerial;

    private String planNo;

    private String acceptNO;

    private String weight;

    private String heightlong;

    private String locality;

    private String isMadeinChina;

    private Date acceptanceDate;

    private String isWorkEquip;

    private String mainPowerStr;

    private String vicePowerStr;

    private BigDecimal power;

    private String depreciation;

    private String equipnoSecond;

    private String area;

    private String companyId;

    private String companyName;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZxEqPurAccessories> zxEqPurAccessoriesList;

    private List<ZxEqPurLib> zxEqPurLibList;

    private List<ZxErpFile> fileList;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAbcType() {
        return abcType == null ? "" : abcType.trim();
    }

    public void setAbcType(String abcType) {
        this.abcType = abcType == null ? null : abcType.trim();
    }

    public String getEquipManNo() {
        return equipManNo == null ? "" : equipManNo.trim();
    }

    public void setEquipManNo(String equipManNo) {
        this.equipManNo = equipManNo == null ? null : equipManNo.trim();
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

    public String getContractID() {
        return contractID == null ? "" : contractID.trim();
    }

    public void setContractID(String contractID) {
        this.contractID = contractID == null ? null : contractID.trim();
    }

    public String getSupplieID() {
        return supplieID == null ? "" : supplieID.trim();
    }

    public void setSupplieID(String supplieID) {
        this.supplieID = supplieID == null ? null : supplieID.trim();
    }

    public String getSource() {
        return source == null ? "" : source.trim();
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public Date getPurDate() {
        return purDate;
    }

    public void setPurDate(Date purDate) {
        this.purDate = purDate;
    }

    public String getPurPlace() {
        return purPlace == null ? "" : purPlace.trim();
    }

    public void setPurPlace(String purPlace) {
        this.purPlace = purPlace == null ? null : purPlace.trim();
    }

    public String getUseProjID() {
        return useProjID == null ? "" : useProjID.trim();
    }

    public void setUseProjID(String useProjID) {
        this.useProjID = useProjID == null ? null : useProjID.trim();
    }

    public String getUesProject() {
        return uesProject == null ? "" : uesProject.trim();
    }

    public void setUesProject(String uesProject) {
        this.uesProject = uesProject == null ? null : uesProject.trim();
    }

    public String getFinanceNo() {
        return financeNo == null ? "" : financeNo.trim();
    }

    public void setFinanceNo(String financeNo) {
        this.financeNo = financeNo == null ? null : financeNo.trim();
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

    public String getEquipno() {
        return equipno == null ? "" : equipno.trim();
    }

    public void setEquipno(String equipno) {
        this.equipno = equipno == null ? null : equipno.trim();
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

    public Date getOutfactoryDate() {
        return outfactoryDate;
    }

    public void setOutfactoryDate(Date outfactoryDate) {
        this.outfactoryDate = outfactoryDate;
    }

    public String getOldrate() {
        return oldrate == null ? "" : oldrate.trim();
    }

    public void setOldrate(String oldrate) {
        this.oldrate = oldrate == null ? null : oldrate.trim();
    }

    public String getMainFactory() {
        return mainFactory == null ? "" : mainFactory.trim();
    }

    public void setMainFactory(String mainFactory) {
        this.mainFactory = mainFactory == null ? null : mainFactory.trim();
    }

    public String getMainspec() {
        return mainspec == null ? "" : mainspec.trim();
    }

    public void setMainspec(String mainspec) {
        this.mainspec = mainspec == null ? null : mainspec.trim();
    }

    public BigDecimal getMainpower() {
        return mainpower;
    }

    public void setMainpower(BigDecimal mainpower) {
        this.mainpower = mainpower;
    }

    public String getMainserial() {
        return mainserial == null ? "" : mainserial.trim();
    }

    public void setMainserial(String mainserial) {
        this.mainserial = mainserial == null ? null : mainserial.trim();
    }

    public Date getMainoutfactory() {
        return mainoutfactory;
    }

    public void setMainoutfactory(Date mainoutfactory) {
        this.mainoutfactory = mainoutfactory;
    }

    public String getViceFactory() {
        return viceFactory == null ? "" : viceFactory.trim();
    }

    public void setViceFactory(String viceFactory) {
        this.viceFactory = viceFactory == null ? null : viceFactory.trim();
    }

    public String getVicespec() {
        return vicespec == null ? "" : vicespec.trim();
    }

    public void setVicespec(String vicespec) {
        this.vicespec = vicespec == null ? null : vicespec.trim();
    }

    public BigDecimal getVicepower() {
        return vicepower;
    }

    public void setVicepower(BigDecimal vicepower) {
        this.vicepower = vicepower;
    }

    public String getViceserial() {
        return viceserial == null ? "" : viceserial.trim();
    }

    public void setViceserial(String viceserial) {
        this.viceserial = viceserial == null ? null : viceserial.trim();
    }

    public Date getViceoutfactory() {
        return viceoutfactory;
    }

    public void setViceoutfactory(Date viceoutfactory) {
        this.viceoutfactory = viceoutfactory;
    }

    public String getDownfactory() {
        return downfactory == null ? "" : downfactory.trim();
    }

    public void setDownfactory(String downfactory) {
        this.downfactory = downfactory == null ? null : downfactory.trim();
    }

    public String getDownspec() {
        return downspec == null ? "" : downspec.trim();
    }

    public void setDownspec(String downspec) {
        this.downspec = downspec == null ? null : downspec.trim();
    }

    public String getDownserial() {
        return downserial == null ? "" : downserial.trim();
    }

    public void setDownserial(String downserial) {
        this.downserial = downserial == null ? null : downserial.trim();
    }

    public Date getDownoutfactory() {
        return downoutfactory;
    }

    public void setDownoutfactory(Date downoutfactory) {
        this.downoutfactory = downoutfactory;
    }

    public BigDecimal getOrginalvalue() {
        return orginalvalue;
    }

    public void setOrginalvalue(BigDecimal orginalvalue) {
        this.orginalvalue = orginalvalue;
    }

    public BigDecimal getVicevalue() {
        return vicevalue;
    }

    public void setVicevalue(BigDecimal vicevalue) {
        this.vicevalue = vicevalue;
    }

    public BigDecimal getTranvalue() {
        return tranvalue;
    }

    public void setTranvalue(BigDecimal tranvalue) {
        this.tranvalue = tranvalue;
    }

    public BigDecimal getTotalvalue() {
        return totalvalue;
    }

    public void setTotalvalue(BigDecimal totalvalue) {
        this.totalvalue = totalvalue;
    }

    public String getPassNo() {
        return passNo == null ? "" : passNo.trim();
    }

    public void setPassNo(String passNo) {
        this.passNo = passNo == null ? null : passNo.trim();
    }

    public String getIsimported() {
        return isimported == null ? "" : isimported.trim();
    }

    public void setIsimported(String isimported) {
        this.isimported = isimported == null ? null : isimported.trim();
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOutFactorySerial() {
        return outFactorySerial == null ? "" : outFactorySerial.trim();
    }

    public void setOutFactorySerial(String outFactorySerial) {
        this.outFactorySerial = outFactorySerial == null ? null : outFactorySerial.trim();
    }

    public String getPlanNo() {
        return planNo == null ? "" : planNo.trim();
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo == null ? null : planNo.trim();
    }

    public String getAcceptNO() {
        return acceptNO == null ? "" : acceptNO.trim();
    }

    public void setAcceptNO(String acceptNO) {
        this.acceptNO = acceptNO == null ? null : acceptNO.trim();
    }

    public String getWeight() {
        return weight == null ? "" : weight.trim();
    }

    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }

    public String getHeightlong() {
        return heightlong == null ? "" : heightlong.trim();
    }

    public void setHeightlong(String heightlong) {
        this.heightlong = heightlong == null ? null : heightlong.trim();
    }

    public String getLocality() {
        return locality == null ? "" : locality.trim();
    }

    public void setLocality(String locality) {
        this.locality = locality == null ? null : locality.trim();
    }

    public String getIsMadeinChina() {
        return isMadeinChina == null ? "" : isMadeinChina.trim();
    }

    public void setIsMadeinChina(String isMadeinChina) {
        this.isMadeinChina = isMadeinChina == null ? null : isMadeinChina.trim();
    }

    public Date getAcceptanceDate() {
        return acceptanceDate;
    }

    public void setAcceptanceDate(Date acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }

    public String getIsWorkEquip() {
        return isWorkEquip == null ? "" : isWorkEquip.trim();
    }

    public void setIsWorkEquip(String isWorkEquip) {
        this.isWorkEquip = isWorkEquip == null ? null : isWorkEquip.trim();
    }

    public String getMainPowerStr() {
        return mainPowerStr == null ? "" : mainPowerStr.trim();
    }

    public void setMainPowerStr(String mainPowerStr) {
        this.mainPowerStr = mainPowerStr == null ? null : mainPowerStr.trim();
    }

    public String getVicePowerStr() {
        return vicePowerStr == null ? "" : vicePowerStr.trim();
    }

    public void setVicePowerStr(String vicePowerStr) {
        this.vicePowerStr = vicePowerStr == null ? null : vicePowerStr.trim();
    }

    public BigDecimal getPower() {
        return power;
    }

    public void setPower(BigDecimal power) {
        this.power = power;
    }

    public String getDepreciation() {
        return depreciation == null ? "" : depreciation.trim();
    }

    public void setDepreciation(String depreciation) {
        this.depreciation = depreciation == null ? null : depreciation.trim();
    }

    public String getEquipnoSecond() {
        return equipnoSecond == null ? "" : equipnoSecond.trim();
    }

    public void setEquipnoSecond(String equipnoSecond) {
        this.equipnoSecond = equipnoSecond == null ? null : equipnoSecond.trim();
    }

    public String getArea() {
        return area == null ? "" : area.trim();
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getCompanyId() {
        return companyId == null ? "" : companyId.trim();
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCompanyName() {
        return companyName == null ? "" : companyName.trim();
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
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

    public List<ZxEqPurAccessories> getZxEqPurAccessoriesList() {
        return zxEqPurAccessoriesList;
    }

    public void setZxEqPurAccessoriesList(List<ZxEqPurAccessories> zxEqPurAccessoriesList) {
        this.zxEqPurAccessoriesList = zxEqPurAccessoriesList;
    }

    public List<ZxEqPurLib> getZxEqPurLibList() {
        return zxEqPurLibList;
    }

    public void setZxEqPurLibList(List<ZxEqPurLib> zxEqPurLibList) {
        this.zxEqPurLibList = zxEqPurLibList;
    }

    public List<ZxErpFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<ZxErpFile> fileList) {
        this.fileList = fileList;
    }

}

