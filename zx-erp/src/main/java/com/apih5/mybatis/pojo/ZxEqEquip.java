package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

import cn.hutool.json.JSONObject;

public class ZxEqEquip extends BasePojo {
    private String id;

    private String purRecordID;

    private String abcType;

    private String resCatalogID;

    private String isWorkEquip;

    private String isMadeinChina;

    private String financeNo;

    private String useOrgID;

    private String useOrgName;

    private Date purDate;

    private String usePlace;

    private String ownerOrgId;

    private String ownerOrgName;

    private String manageOrgID;

    private String equipName;

    private String spec;

    private String model;

    private BigDecimal power;

    private String source;

    private String viceserial;

    private String downfactory;

    private Date viceoutfactory;

    private BigDecimal vicepower;

    private String vicespec;

    private String viceFactory;

    private BigDecimal mainpower;

    private Date mainoutfactory;

    private String mainserial;

    private String mainspec;

    private String mainFactory;

    private String downspec;

    private String downserial;

    private Date downoutfactory;

    private String heightlong;

    private String weight;

    private BigDecimal orginalValue;

    private String depreImportmonth;

    private BigDecimal leftValue;

    private BigDecimal fobPrice;

    private BigDecimal fobAmount;

    private BigDecimal discountAmount;

    private Date acceptanceDate;

    private String passNo;

    private String status;

    private Date outFactoryDate;

    private String outFactorySerial;

    private String isimportant;

    private Date changeDate;

    private String remark;

    private BigDecimal actualDepreAmt;

    private Date regdate;

    private Integer depreciationMonth;

    private String depreciation;

    private String equipNo;

    private String factory;

    private String checkStatus;

    private String isDelete;

    private String mainPowerStr;

    private String vicePowerStr;

    private String planNo;

    private String plancode;

    private String attachmentID;

    private String isXianzhi;

    private String qrcodeName;

    private String qrcodeUrl;

    private String qrcodeContent;

    private String qrcodeDownUrl;

    private String companyId;

    private String companyName;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private String downoutfactoryStr;

    private String outFactoryDateStr;

    private String acceptanceDateStr;

    private String viceoutfactoryStr;

    private String mainoutfactoryStr;

    private String abcTypeName;

    private String orgID;

    private String outOrgID;

    private List<ZxErpFile> fileList;

    private BigDecimal financeOrginalValue;

    private Date beginDate;

    private Date endDate;
    
    private BigDecimal depreamout;
    
    private String statusFlag;
    
    private String statusFlagForMoveUseOrg;
    
    private int leftMonth;

    private String purDateStr;
    private String purDateYearStr;
    
    private String selectTypeFlag;
    
    private Date purDateStart;
    
    private Date purDateEnd;
    
    private String ureportFlag;
    
    private String departmentPath;
    private String departmentParentId;
    private String numberFlag;
    private String departmentId;
    
    private JSONObject orginalValueSearch;
    
    private String departmentName;
    private int numTotal;
    private int numA;
    private int numB;
    private int numC;
    private int numD;
    private BigDecimal orgTotal;
    private BigDecimal orgA;
    private BigDecimal orgB;
    private BigDecimal orgC;
    private BigDecimal orgD;
    private BigDecimal leftTotal;
    private BigDecimal leftA;
    private BigDecimal leftB;
    private BigDecimal leftC;
    private BigDecimal leftD;
    
    private int countIn;
    private int countOut;
    private BigDecimal powerIn;
    private BigDecimal powerOut;
    private BigDecimal leftIn;
    private BigDecimal leftOut;
    private BigDecimal orgIn;
    private BigDecimal orgOut;
    
    
    
    
    
    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPurRecordID() {
        return purRecordID == null ? "" : purRecordID.trim();
    }

    public void setPurRecordID(String purRecordID) {
        this.purRecordID = purRecordID == null ? null : purRecordID.trim();
    }

    public String getAbcType() {
        return abcType == null ? "" : abcType.trim();
    }

    public void setAbcType(String abcType) {
        this.abcType = abcType == null ? null : abcType.trim();
    }

    public String getResCatalogID() {
        return resCatalogID == null ? "" : resCatalogID.trim();
    }

    public void setResCatalogID(String resCatalogID) {
        this.resCatalogID = resCatalogID == null ? null : resCatalogID.trim();
    }

    public String getIsWorkEquip() {
        return isWorkEquip == null ? "" : isWorkEquip.trim();
    }

    public void setIsWorkEquip(String isWorkEquip) {
        this.isWorkEquip = isWorkEquip == null ? null : isWorkEquip.trim();
    }

    public String getIsMadeinChina() {
        return isMadeinChina == null ? "" : isMadeinChina.trim();
    }

    public void setIsMadeinChina(String isMadeinChina) {
        this.isMadeinChina = isMadeinChina == null ? null : isMadeinChina.trim();
    }

    public String getFinanceNo() {
        return financeNo == null ? "" : financeNo.trim();
    }

    public void setFinanceNo(String financeNo) {
        this.financeNo = financeNo == null ? null : financeNo.trim();
    }

    public String getUseOrgID() {
        return useOrgID == null ? "" : useOrgID.trim();
    }

    public void setUseOrgID(String useOrgID) {
        this.useOrgID = useOrgID == null ? null : useOrgID.trim();
    }

    public String getUseOrgName() {
        return useOrgName == null ? "" : useOrgName.trim();
    }

    public void setUseOrgName(String useOrgName) {
        this.useOrgName = useOrgName == null ? null : useOrgName.trim();
    }

    public Date getPurDate() {
        return purDate;
    }

    public void setPurDate(Date purDate) {
        this.purDate = purDate;
    }

    public String getUsePlace() {
        return usePlace == null ? "" : usePlace.trim();
    }

    public void setUsePlace(String usePlace) {
        this.usePlace = usePlace == null ? null : usePlace.trim();
    }

    public String getOwnerOrgId() {
        return ownerOrgId == null ? "" : ownerOrgId.trim();
    }

    public void setOwnerOrgId(String ownerOrgId) {
        this.ownerOrgId = ownerOrgId == null ? null : ownerOrgId.trim();
    }

    public String getOwnerOrgName() {
        return ownerOrgName == null ? "" : ownerOrgName.trim();
    }

    public void setOwnerOrgName(String ownerOrgName) {
        this.ownerOrgName = ownerOrgName == null ? null : ownerOrgName.trim();
    }

    public String getManageOrgID() {
        return manageOrgID == null ? "" : manageOrgID.trim();
    }

    public void setManageOrgID(String manageOrgID) {
        this.manageOrgID = manageOrgID == null ? null : manageOrgID.trim();
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

    public BigDecimal getPower() {
        return power;
    }

    public void setPower(BigDecimal power) {
        this.power = power;
    }

    public String getSource() {
        return source == null ? "" : source.trim();
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getViceserial() {
        return viceserial == null ? "" : viceserial.trim();
    }

    public void setViceserial(String viceserial) {
        this.viceserial = viceserial == null ? null : viceserial.trim();
    }

    public String getDownfactory() {
        return downfactory == null ? "" : downfactory.trim();
    }

    public void setDownfactory(String downfactory) {
        this.downfactory = downfactory == null ? null : downfactory.trim();
    }

    public Date getViceoutfactory() {
        return viceoutfactory;
    }

    public void setViceoutfactory(Date viceoutfactory) {
        this.viceoutfactory = viceoutfactory;
    }

    public BigDecimal getVicepower() {
        return vicepower;
    }

    public void setVicepower(BigDecimal vicepower) {
        this.vicepower = vicepower;
    }

    public String getVicespec() {
        return vicespec == null ? "" : vicespec.trim();
    }

    public void setVicespec(String vicespec) {
        this.vicespec = vicespec == null ? null : vicespec.trim();
    }

    public String getViceFactory() {
        return viceFactory == null ? "" : viceFactory.trim();
    }

    public void setViceFactory(String viceFactory) {
        this.viceFactory = viceFactory == null ? null : viceFactory.trim();
    }

    public BigDecimal getMainpower() {
        return mainpower;
    }

    public void setMainpower(BigDecimal mainpower) {
        this.mainpower = mainpower;
    }

    public Date getMainoutfactory() {
        return mainoutfactory;
    }

    public void setMainoutfactory(Date mainoutfactory) {
        this.mainoutfactory = mainoutfactory;
    }

    public String getMainserial() {
        return mainserial == null ? "" : mainserial.trim();
    }

    public void setMainserial(String mainserial) {
        this.mainserial = mainserial == null ? null : mainserial.trim();
    }

    public String getMainspec() {
        return mainspec == null ? "" : mainspec.trim();
    }

    public void setMainspec(String mainspec) {
        this.mainspec = mainspec == null ? null : mainspec.trim();
    }

    public String getMainFactory() {
        return mainFactory == null ? "" : mainFactory.trim();
    }

    public void setMainFactory(String mainFactory) {
        this.mainFactory = mainFactory == null ? null : mainFactory.trim();
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

    public String getHeightlong() {
        return heightlong == null ? "" : heightlong.trim();
    }

    public void setHeightlong(String heightlong) {
        this.heightlong = heightlong == null ? null : heightlong.trim();
    }

    public String getWeight() {
        return weight == null ? "" : weight.trim();
    }

    public void setWeight(String weight) {
        this.weight = weight == null ? null : weight.trim();
    }

    public BigDecimal getOrginalValue() {
        return orginalValue;
    }

    public void setOrginalValue(BigDecimal orginalValue) {
        this.orginalValue = orginalValue;
    }

    public String getDepreImportmonth() {
        return depreImportmonth == null ? "" : depreImportmonth.trim();
    }

    public void setDepreImportmonth(String depreImportmonth) {
        this.depreImportmonth = depreImportmonth == null ? null : depreImportmonth.trim();
    }

    public BigDecimal getLeftValue() {
        return leftValue;
    }

    public void setLeftValue(BigDecimal leftValue) {
        this.leftValue = leftValue;
    }

    public BigDecimal getFobPrice() {
        return fobPrice;
    }

    public void setFobPrice(BigDecimal fobPrice) {
        this.fobPrice = fobPrice;
    }

    public BigDecimal getFobAmount() {
        return fobAmount;
    }

    public void setFobAmount(BigDecimal fobAmount) {
        this.fobAmount = fobAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Date getAcceptanceDate() {
        return acceptanceDate;
    }

    public void setAcceptanceDate(Date acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }

    public String getPassNo() {
        return passNo == null ? "" : passNo.trim();
    }

    public void setPassNo(String passNo) {
        this.passNo = passNo == null ? null : passNo.trim();
    }

    public String getStatus() {
        return status == null ? "" : status.trim();
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getOutFactoryDate() {
        return outFactoryDate;
    }

    public void setOutFactoryDate(Date outFactoryDate) {
        this.outFactoryDate = outFactoryDate;
    }

    public String getOutFactorySerial() {
        return outFactorySerial == null ? "" : outFactorySerial.trim();
    }

    public void setOutFactorySerial(String outFactorySerial) {
        this.outFactorySerial = outFactorySerial == null ? null : outFactorySerial.trim();
    }

    public String getIsimportant() {
        return isimportant == null ? "" : isimportant.trim();
    }

    public void setIsimportant(String isimportant) {
        this.isimportant = isimportant == null ? null : isimportant.trim();
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public BigDecimal getActualDepreAmt() {
        return actualDepreAmt;
    }

    public void setActualDepreAmt(BigDecimal actualDepreAmt) {
        this.actualDepreAmt = actualDepreAmt;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public Integer getDepreciationMonth() {
        return depreciationMonth;
    }

    public void setDepreciationMonth(Integer depreciationMonth) {
        this.depreciationMonth = depreciationMonth;
    }

    public String getDepreciation() {
        return depreciation == null ? "" : depreciation.trim();
    }

    public void setDepreciation(String depreciation) {
        this.depreciation = depreciation == null ? null : depreciation.trim();
    }

    public String getEquipNo() {
        return equipNo == null ? "" : equipNo.trim();
    }

    public void setEquipNo(String equipNo) {
        this.equipNo = equipNo == null ? null : equipNo.trim();
    }

    public String getFactory() {
        return factory == null ? "" : factory.trim();
    }

    public void setFactory(String factory) {
        this.factory = factory == null ? null : factory.trim();
    }

    public String getCheckStatus() {
        return checkStatus == null ? "" : checkStatus.trim();
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus == null ? null : checkStatus.trim();
    }

    public String getIsDelete() {
        return isDelete == null ? "" : isDelete.trim();
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete == null ? null : isDelete.trim();
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

    public String getPlanNo() {
        return planNo == null ? "" : planNo.trim();
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo == null ? null : planNo.trim();
    }

    public String getPlancode() {
        return plancode == null ? "" : plancode.trim();
    }

    public void setPlancode(String plancode) {
        this.plancode = plancode == null ? null : plancode.trim();
    }

    public String getAttachmentID() {
        return attachmentID == null ? "" : attachmentID.trim();
    }

    public void setAttachmentID(String attachmentID) {
        this.attachmentID = attachmentID == null ? null : attachmentID.trim();
    }

    public String getIsXianzhi() {
        return isXianzhi == null ? "" : isXianzhi.trim();
    }

    public void setIsXianzhi(String isXianzhi) {
        this.isXianzhi = isXianzhi == null ? null : isXianzhi.trim();
    }

    public String getQrcodeName() {
        return qrcodeName == null ? "" : qrcodeName.trim();
    }

    public void setQrcodeName(String qrcodeName) {
        this.qrcodeName = qrcodeName == null ? null : qrcodeName.trim();
    }

    public String getQrcodeUrl() {
        return qrcodeUrl == null ? "" : qrcodeUrl.trim();
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl == null ? null : qrcodeUrl.trim();
    }

    public String getQrcodeContent() {
        return qrcodeContent == null ? "" : qrcodeContent.trim();
    }

    public void setQrcodeContent(String qrcodeContent) {
        this.qrcodeContent = qrcodeContent == null ? null : qrcodeContent.trim();
    }

    public String getQrcodeDownUrl() {
        return qrcodeDownUrl == null ? "" : qrcodeDownUrl.trim();
    }

    public void setQrcodeDownUrl(String qrcodeDownUrl) {
        this.qrcodeDownUrl = qrcodeDownUrl == null ? null : qrcodeDownUrl.trim();
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

    public String getDownoutfactoryStr() {
        return downoutfactoryStr == null ? "" : downoutfactoryStr.trim();
    }

    public void setDownoutfactoryStr(String downoutfactoryStr) {
        this.downoutfactoryStr = downoutfactoryStr == null ? null : downoutfactoryStr.trim();
    }

    public String getOutFactoryDateStr() {
        return outFactoryDateStr == null ? "" : outFactoryDateStr.trim();
    }

    public void setOutFactoryDateStr(String outFactoryDateStr) {
        this.outFactoryDateStr = outFactoryDateStr == null ? null : outFactoryDateStr.trim();
    }

    public String getAcceptanceDateStr() {
        return acceptanceDateStr == null ? "" : acceptanceDateStr.trim();
    }

    public void setAcceptanceDateStr(String acceptanceDateStr) {
        this.acceptanceDateStr = acceptanceDateStr == null ? null : acceptanceDateStr.trim();
    }

    public String getViceoutfactoryStr() {
        return viceoutfactoryStr == null ? "" : viceoutfactoryStr.trim();
    }

    public void setViceoutfactoryStr(String viceoutfactoryStr) {
        this.viceoutfactoryStr = viceoutfactoryStr == null ? null : viceoutfactoryStr.trim();
    }

    public String getMainoutfactoryStr() {
        return mainoutfactoryStr == null ? "" : mainoutfactoryStr.trim();
    }

    public void setMainoutfactoryStr(String mainoutfactoryStr) {
        this.mainoutfactoryStr = mainoutfactoryStr == null ? null : mainoutfactoryStr.trim();
    }

    public String getAbcTypeName() {
        return abcTypeName == null ? "" : abcTypeName.trim();
    }

    public void setAbcTypeName(String abcTypeName) {
        this.abcTypeName = abcTypeName == null ? null : abcTypeName.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getOutOrgID() {
        return outOrgID == null ? "" : outOrgID.trim();
    }

    public void setOutOrgID(String outOrgID) {
        this.outOrgID = outOrgID == null ? null : outOrgID.trim();
    }

    public List<ZxErpFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<ZxErpFile> fileList) {
        this.fileList = fileList;
    }

	public BigDecimal getFinanceOrginalValue() {
		return financeOrginalValue;
	}

	public void setFinanceOrginalValue(BigDecimal financeOrginalValue) {
		this.financeOrginalValue = financeOrginalValue;
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

	public BigDecimal getDepreamout() {
		return depreamout;
	}

	public void setDepreamout(BigDecimal depreamout) {
		this.depreamout = depreamout;
	}

	public String getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	public int getLeftMonth() {
		return leftMonth;
	}

	public void setLeftMonth(int leftMonth) {
		this.leftMonth = leftMonth;
	}

	public String getStatusFlagForMoveUseOrg() {
		return statusFlagForMoveUseOrg;
	}

	public void setStatusFlagForMoveUseOrg(String statusFlagForMoveUseOrg) {
		this.statusFlagForMoveUseOrg = statusFlagForMoveUseOrg;
	}

	public String getPurDateStr() {
		return purDateStr;
	}

	public void setPurDateStr(String purDateStr) {
		this.purDateStr = purDateStr;
	}

	public String getPurDateYearStr() {
		return purDateYearStr;
	}

	public void setPurDateYearStr(String purDateYearStr) {
 		this.purDateYearStr = purDateYearStr;
	}

	public String getSelectTypeFlag() {
		return selectTypeFlag;
	}

	public void setSelectTypeFlag(String selectTypeFlag) {
		this.selectTypeFlag = selectTypeFlag;
	}

	public Date getPurDateStart() {
		return purDateStart;
	}

	public void setPurDateStart(Date purDateStart) {
		this.purDateStart = purDateStart;
	}

	public Date getPurDateEnd() {
		return purDateEnd;
	}

	public void setPurDateEnd(Date purDateEnd) {
		this.purDateEnd = purDateEnd;
	}

	public String getUreportFlag() {
		return ureportFlag;
	}

	public void setUreportFlag(String ureportFlag) {
		this.ureportFlag = ureportFlag;
	}

	public String getDepartmentPath() {
		return departmentPath;
	}

	public void setDepartmentPath(String departmentPath) {
		this.departmentPath = departmentPath;
	}

	public String getDepartmentParentId() {
		return departmentParentId;
	}

	public void setDepartmentParentId(String departmentParentId) {
		this.departmentParentId = departmentParentId;
	}

	public String getNumberFlag() {
		return numberFlag;
	}

	public void setNumberFlag(String numberFlag) {
		this.numberFlag = numberFlag;
	}
	
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public JSONObject getOrginalValueSearch() {
		return orginalValueSearch;
	}

	public void setOrginalValueSearch(JSONObject orginalValueSearch) {
		this.orginalValueSearch = orginalValueSearch;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public int getNumTotal() {
		return numTotal;
	}

	public void setNumTotal(int numTotal) {
		this.numTotal = numTotal;
	}

	public int getNumA() {
		return numA;
	}

	public void setNumA(int numA) {
		this.numA = numA;
	}

	public int getNumB() {
		return numB;
	}

	public void setNumB(int numB) {
		this.numB = numB;
	}

	public int getNumC() {
		return numC;
	}

	public void setNumC(int numC) {
		this.numC = numC;
	}

	public int getNumD() {
		return numD;
	}

	public void setNumD(int numD) {
		this.numD = numD;
	}

	public BigDecimal getOrgTotal() {
		return orgTotal;
	}

	public void setOrgTotal(BigDecimal orgTotal) {
		this.orgTotal = orgTotal;
	}

	public BigDecimal getOrgA() {
		return orgA;
	}

	public void setOrgA(BigDecimal orgA) {
		this.orgA = orgA;
	}

	public BigDecimal getOrgB() {
		return orgB;
	}

	public void setOrgB(BigDecimal orgB) {
		this.orgB = orgB;
	}

	public BigDecimal getOrgC() {
		return orgC;
	}

	public void setOrgC(BigDecimal orgC) {
		this.orgC = orgC;
	}

	public BigDecimal getOrgD() {
		return orgD;
	}

	public void setOrgD(BigDecimal orgD) {
		this.orgD = orgD;
	}

	public BigDecimal getLeftTotal() {
		return leftTotal;
	}

	public void setLeftTotal(BigDecimal leftTotal) {
		this.leftTotal = leftTotal;
	}

	public BigDecimal getLeftA() {
		return leftA;
	}

	public void setLeftA(BigDecimal leftA) {
		this.leftA = leftA;
	}

	public BigDecimal getLeftB() {
		return leftB;
	}

	public void setLeftB(BigDecimal leftB) {
		this.leftB = leftB;
	}

	public BigDecimal getLeftC() {
		return leftC;
	}

	public void setLeftC(BigDecimal leftC) {
		this.leftC = leftC;
	}

	public BigDecimal getLeftD() {
		return leftD;
	}

	public void setLeftD(BigDecimal leftD) {
		this.leftD = leftD;
	}

	public int getCountIn() {
		return countIn;
	}

	public void setCountIn(int countIn) {
		this.countIn = countIn;
	}

	public int getCountOut() {
		return countOut;
	}

	public void setCountOut(int countOut) {
		this.countOut = countOut;
	}

	public BigDecimal getPowerIn() {
		return powerIn;
	}

	public void setPowerIn(BigDecimal powerIn) {
		this.powerIn = powerIn;
	}

	public BigDecimal getPowerOut() {
		return powerOut;
	}

	public void setPowerOut(BigDecimal powerOut) {
		this.powerOut = powerOut;
	}

	public BigDecimal getLeftIn() {
		return leftIn;
	}

	public void setLeftIn(BigDecimal leftIn) {
		this.leftIn = leftIn;
	}

	public BigDecimal getLeftOut() {
		return leftOut;
	}

	public void setLeftOut(BigDecimal leftOut) {
		this.leftOut = leftOut;
	}

	public BigDecimal getOrgIn() {
		return orgIn;
	}

	public void setOrgIn(BigDecimal orgIn) {
		this.orgIn = orgIn;
	}

	public BigDecimal getOrgOut() {
		return orgOut;
	}

	public void setOrgOut(BigDecimal orgOut) {
		this.orgOut = orgOut;
	}
	
}