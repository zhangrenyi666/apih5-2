package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCrCustomerExtAttr extends BasePojo {
    // 主键
    private String zxCrCustomerExtAttrId;

    // 客商信誉
    private String creditStanding;

    // 负责人手机
    private String pricinpalMobile;

    // 法人单位联系电话
    private String pricinpalTel;

    // 法人单位传真
    private String pricinpalFax;

    // 负责人邮箱
    private String pricinpalEmail;

    // 法人单位邮编
    private String pricinpalPostCode;

    // 法人单位通讯地址
    private String pricinpalAddr;

    // 客商ID
    private String customerID;

    // 编号
    private String customerNO;

    // 客商产生的机构ID
    private String proOrgID;

    // 性质
    private String kind;

    // 负责人身份证
    private String personInChargeIdNumber;

    // 注册单位(个人)名称
    private String customerName;

    // 组织机构代码证
    private String organizationCode;

    // 内部单位
    private String relat;

    // 法人代表
    private String legalRepresentative;

    // 法人委托人
    private String legalPersonClient;

    // 单位注册时间
    private Date registrationTime;

    // 单位注册地区
    private String unitRegistrationArea;

    // 注册资本金
    private BigDecimal registeredCapital;

    // 资质证书编号
    private String qualificationCertificateNo;

    // 安全生产许可证编号
    private String safetyProductionLicenseNo;

    // 资质范围等级
    private String scopeAndGradeOfQualification;

    // 经营范围
    private String natureOfBusiness;

    // 法人单位手机
    private String mobilePhoneOfLegalEntity;

    // 公司类型
    private String companyType;

    // 局内协作经历
    private String experienceOfInhouseCollaboration;

    // 正在服务项目
    private String projectInService;

    // 客商固话
    private String fixedLineTelephoneOfMerchants;

    // 客商传真
    private String faxOfMerchants;

    // 营业执照编号
    private String businessLicenseNumber;

    // 客户类型
    private String type;
    
    // 客户类型1
    private String type1;
    
	// 客户类型2
    private String type2;

    // 联系人
    private String linkmanName;

    // 联系人电话
    private String linkmanTel;

    // 商客名称
    private String searchTheCustomerName;
    
    // 项目名称
    private String entryName;

	// 附件
    private List<ZxErpFile> fileList;
    
    // 类别区分List
    private String typeList;
    
	// 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    // 所属机构（项目ID）
    private String projectId; 
    
	// 所属公司ID
    private String companyId;
    
    // 所属公司Name
    private String companyName;

	// 项目名称
    private String projectName;
    // 协作单位基础信息登记的数据
    // 主键
    private String zxCrCustomerInfoId;

	// 统一社会信用代码
    private String orgCertificate;

    // 注册资本金(元)
    private BigDecimal regMoney;

    // 法定代表人
    private String corparation;

    // 法定代表人身份证号码
    private String pricinpalIDCard;

    // 营业执照注册号
    private String licenceNO;

    // 企业资质证书编号
    private String qualificateNo;

    // 企业税务登记证号
    private String taxRegNo;

    // 企业资质等级
    private String qualifiLevel;

    // 证照是否过期
    private String dateStatus;

    // 是否战略供应商
    private String strategicSupplier;

    // 银行授信额度
    private BigDecimal creditLineAmt;

    // 推荐单位
    private String referenceOrg;

    // 推荐人姓名
    private String referenceName;

    // 推荐人职务
    private String referencePost;

    // 推荐人联系电话
    private String referencePhone;

    // 曾用名
    private String usedNames;

    // 黑名单
    private String isBlack;

    // 项目名称
    private String orgName;

    // 状态
    private String auditStatus;

    // 所在省份
    private String province;

    // 所在区域
    private String area;

    // 营业范围
    private String scope;

    // 安全生产许可证编码
    private String safeCode;

    // 纳税人识别号
    private String taxpayerNum;

    // 纳税人类别
    private String taxpayerType;

    // 开户行名称
    private String bankName;

    // 开户行账号
    private String bankAccount;

    // 是否已占号
    private String useFlag;

    // 实缴资本金（元）
    private BigDecimal realRegMoney;

    // 企业性质
    private String businessType;

    // 营业执照有效期至
    private Date licenceDate;

    // 企业资质证书有效期至
    private Date qualificateDate;

    // 企业税务登记有效期至
    private Date taxRegDate;

    // 安全生产许可证有效期至
    private Date safeBookDate;

    // 占号维护单位
    private String comOrgName;

    // 是否需要复审
    private String isNeedfushen;

    // 复核状态
    private String fuheStatus;

    // 复审状态
    private String fushenStatus;
    
    // 所属机构（项目ID）
    private String orgID; 

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getZxCrCustomerExtAttrId() {
        return zxCrCustomerExtAttrId == null ? "" : zxCrCustomerExtAttrId.trim();
    }

    public void setZxCrCustomerExtAttrId(String zxCrCustomerExtAttrId) {
        this.zxCrCustomerExtAttrId = zxCrCustomerExtAttrId == null ? null : zxCrCustomerExtAttrId.trim();
    }

    public String getCreditStanding() {
        return creditStanding == null ? "" : creditStanding.trim();
    }

    public void setCreditStanding(String creditStanding) {
        this.creditStanding = creditStanding == null ? null : creditStanding.trim();
    }

    public String getPricinpalMobile() {
        return pricinpalMobile == null ? "" : pricinpalMobile.trim();
    }

    public void setPricinpalMobile(String pricinpalMobile) {
        this.pricinpalMobile = pricinpalMobile == null ? null : pricinpalMobile.trim();
    }

    public String getPricinpalTel() {
        return pricinpalTel == null ? "" : pricinpalTel.trim();
    }

    public void setPricinpalTel(String pricinpalTel) {
        this.pricinpalTel = pricinpalTel == null ? null : pricinpalTel.trim();
    }

    public String getPricinpalFax() {
        return pricinpalFax == null ? "" : pricinpalFax.trim();
    }

    public void setPricinpalFax(String pricinpalFax) {
        this.pricinpalFax = pricinpalFax == null ? null : pricinpalFax.trim();
    }

    public String getPricinpalEmail() {
        return pricinpalEmail == null ? "" : pricinpalEmail.trim();
    }

    public void setPricinpalEmail(String pricinpalEmail) {
        this.pricinpalEmail = pricinpalEmail == null ? null : pricinpalEmail.trim();
    }

    public String getPricinpalPostCode() {
        return pricinpalPostCode == null ? "" : pricinpalPostCode.trim();
    }

    public void setPricinpalPostCode(String pricinpalPostCode) {
        this.pricinpalPostCode = pricinpalPostCode == null ? null : pricinpalPostCode.trim();
    }

    public String getPricinpalAddr() {
        return pricinpalAddr == null ? "" : pricinpalAddr.trim();
    }

    public void setPricinpalAddr(String pricinpalAddr) {
        this.pricinpalAddr = pricinpalAddr == null ? null : pricinpalAddr.trim();
    }

    public String getCustomerID() {
        return customerID == null ? "" : customerID.trim();
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID == null ? null : customerID.trim();
    }

    public String getCustomerNO() {
        return customerNO == null ? "" : customerNO.trim();
    }

    public void setCustomerNO(String customerNO) {
        this.customerNO = customerNO == null ? null : customerNO.trim();
    }

    public String getProOrgID() {
        return proOrgID == null ? "" : proOrgID.trim();
    }

    public void setProOrgID(String proOrgID) {
        this.proOrgID = proOrgID == null ? null : proOrgID.trim();
    }

    public String getKind() {
        return kind == null ? "" : kind.trim();
    }

    public void setKind(String kind) {
        this.kind = kind == null ? null : kind.trim();
    }

    public String getPersonInChargeIdNumber() {
        return personInChargeIdNumber == null ? "" : personInChargeIdNumber.trim();
    }

    public void setPersonInChargeIdNumber(String personInChargeIdNumber) {
        this.personInChargeIdNumber = personInChargeIdNumber == null ? null : personInChargeIdNumber.trim();
    }

    public String getCustomerName() {
        return customerName == null ? "" : customerName.trim();
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getOrganizationCode() {
        return organizationCode == null ? "" : organizationCode.trim();
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode == null ? null : organizationCode.trim();
    }

    public String getRelat() {
        return relat == null ? "" : relat.trim();
    }

    public void setRelat(String relat) {
        this.relat = relat == null ? null : relat.trim();
    }

    public String getLegalRepresentative() {
        return legalRepresentative == null ? "" : legalRepresentative.trim();
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative == null ? null : legalRepresentative.trim();
    }

    public String getLegalPersonClient() {
        return legalPersonClient == null ? "" : legalPersonClient.trim();
    }

    public void setLegalPersonClient(String legalPersonClient) {
        this.legalPersonClient = legalPersonClient == null ? null : legalPersonClient.trim();
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getUnitRegistrationArea() {
        return unitRegistrationArea == null ? "" : unitRegistrationArea.trim();
    }

    public void setUnitRegistrationArea(String unitRegistrationArea) {
        this.unitRegistrationArea = unitRegistrationArea == null ? null : unitRegistrationArea.trim();
    }

    public BigDecimal getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(BigDecimal registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public String getQualificationCertificateNo() {
        return qualificationCertificateNo == null ? "" : qualificationCertificateNo.trim();
    }

    public void setQualificationCertificateNo(String qualificationCertificateNo) {
        this.qualificationCertificateNo = qualificationCertificateNo == null ? null : qualificationCertificateNo.trim();
    }

    public String getSafetyProductionLicenseNo() {
        return safetyProductionLicenseNo == null ? "" : safetyProductionLicenseNo.trim();
    }

    public void setSafetyProductionLicenseNo(String safetyProductionLicenseNo) {
        this.safetyProductionLicenseNo = safetyProductionLicenseNo == null ? null : safetyProductionLicenseNo.trim();
    }

    public String getScopeAndGradeOfQualification() {
        return scopeAndGradeOfQualification == null ? "" : scopeAndGradeOfQualification.trim();
    }

    public void setScopeAndGradeOfQualification(String scopeAndGradeOfQualification) {
        this.scopeAndGradeOfQualification = scopeAndGradeOfQualification == null ? null : scopeAndGradeOfQualification.trim();
    }

    public String getNatureOfBusiness() {
        return natureOfBusiness == null ? "" : natureOfBusiness.trim();
    }

    public void setNatureOfBusiness(String natureOfBusiness) {
        this.natureOfBusiness = natureOfBusiness == null ? null : natureOfBusiness.trim();
    }

    public String getMobilePhoneOfLegalEntity() {
        return mobilePhoneOfLegalEntity == null ? "" : mobilePhoneOfLegalEntity.trim();
    }

    public void setMobilePhoneOfLegalEntity(String mobilePhoneOfLegalEntity) {
        this.mobilePhoneOfLegalEntity = mobilePhoneOfLegalEntity == null ? null : mobilePhoneOfLegalEntity.trim();
    }

    public String getCompanyType() {
        return companyType == null ? "" : companyType.trim();
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType == null ? null : companyType.trim();
    }

    public String getExperienceOfInhouseCollaboration() {
        return experienceOfInhouseCollaboration == null ? "" : experienceOfInhouseCollaboration.trim();
    }

    public void setExperienceOfInhouseCollaboration(String experienceOfInhouseCollaboration) {
        this.experienceOfInhouseCollaboration = experienceOfInhouseCollaboration == null ? null : experienceOfInhouseCollaboration.trim();
    }

    public String getProjectInService() {
        return projectInService == null ? "" : projectInService.trim();
    }

    public void setProjectInService(String projectInService) {
        this.projectInService = projectInService == null ? null : projectInService.trim();
    }

    public String getFixedLineTelephoneOfMerchants() {
        return fixedLineTelephoneOfMerchants == null ? "" : fixedLineTelephoneOfMerchants.trim();
    }

    public void setFixedLineTelephoneOfMerchants(String fixedLineTelephoneOfMerchants) {
        this.fixedLineTelephoneOfMerchants = fixedLineTelephoneOfMerchants == null ? null : fixedLineTelephoneOfMerchants.trim();
    }

    public String getFaxOfMerchants() {
        return faxOfMerchants == null ? "" : faxOfMerchants.trim();
    }

    public void setFaxOfMerchants(String faxOfMerchants) {
        this.faxOfMerchants = faxOfMerchants == null ? null : faxOfMerchants.trim();
    }

    public String getBusinessLicenseNumber() {
        return businessLicenseNumber == null ? "" : businessLicenseNumber.trim();
    }

    public void setBusinessLicenseNumber(String businessLicenseNumber) {
        this.businessLicenseNumber = businessLicenseNumber == null ? null : businessLicenseNumber.trim();
    }

    public String getType() {
        return type == null ? "" : type.trim();
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getLinkmanName() {
        return linkmanName == null ? "" : linkmanName.trim();
    }

    public void setLinkmanName(String linkmanName) {
        this.linkmanName = linkmanName == null ? null : linkmanName.trim();
    }

    public String getLinkmanTel() {
        return linkmanTel == null ? "" : linkmanTel.trim();
    }

    public void setLinkmanTel(String linkmanTel) {
        this.linkmanTel = linkmanTel == null ? null : linkmanTel.trim();
    }

    public String getSearchTheCustomerName() {
        return searchTheCustomerName == null ? "" : searchTheCustomerName.trim();
    }

    public void setSearchTheCustomerName(String searchTheCustomerName) {
        this.searchTheCustomerName = searchTheCustomerName == null ? null : searchTheCustomerName.trim();
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<ZxErpFile> getFileList() {
		return fileList;
	}

	public void setFileList(List<ZxErpFile> fileList) {
		this.fileList = fileList;
	}
	
	public String getEntryName() {
		return entryName;
	}

	public void setEntryName(String entryName) {
		this.entryName = entryName;
	}
	
	public String getTypeList() {
		return typeList;
	}

	public void setTypeList(String typeList) {
		this.typeList = typeList;
	}
	
	public String getType1() {
		return type1;
	}

	public void setType1(String type1) {
		this.type1 = type1;
	}

	public String getType2() {
		return type2;
	}

	public void setType2(String type2) {
		this.type2 = type2;
	}
		public String getZxCrCustomerInfoId() {
			return zxCrCustomerInfoId;
		}

		public void setZxCrCustomerInfoId(String zxCrCustomerInfoId) {
			this.zxCrCustomerInfoId = zxCrCustomerInfoId;
		}

		public String getOrgCertificate() {
			return orgCertificate;
		}

		public void setOrgCertificate(String orgCertificate) {
			this.orgCertificate = orgCertificate;
		}

		public BigDecimal getRegMoney() {
			return regMoney;
		}

		public void setRegMoney(BigDecimal regMoney) {
			this.regMoney = regMoney;
		}

		public String getCorparation() {
			return corparation;
		}

		public void setCorparation(String corparation) {
			this.corparation = corparation;
		}

		public String getPricinpalIDCard() {
			return pricinpalIDCard;
		}

		public void setPricinpalIDCard(String pricinpalIDCard) {
			this.pricinpalIDCard = pricinpalIDCard;
		}

		public String getLicenceNO() {
			return licenceNO;
		}

		public void setLicenceNO(String licenceNO) {
			this.licenceNO = licenceNO;
		}

		public String getQualificateNo() {
			return qualificateNo;
		}

		public void setQualificateNo(String qualificateNo) {
			this.qualificateNo = qualificateNo;
		}

		public String getTaxRegNo() {
			return taxRegNo;
		}

		public void setTaxRegNo(String taxRegNo) {
			this.taxRegNo = taxRegNo;
		}

		public String getQualifiLevel() {
			return qualifiLevel;
		}

		public void setQualifiLevel(String qualifiLevel) {
			this.qualifiLevel = qualifiLevel;
		}

		public String getDateStatus() {
			return dateStatus;
		}

		public void setDateStatus(String dateStatus) {
			this.dateStatus = dateStatus;
		}

		public String getStrategicSupplier() {
			return strategicSupplier;
		}

		public void setStrategicSupplier(String strategicSupplier) {
			this.strategicSupplier = strategicSupplier;
		}

		public BigDecimal getCreditLineAmt() {
			return creditLineAmt;
		}

		public void setCreditLineAmt(BigDecimal creditLineAmt) {
			this.creditLineAmt = creditLineAmt;
		}

		public String getReferenceOrg() {
			return referenceOrg;
		}

		public void setReferenceOrg(String referenceOrg) {
			this.referenceOrg = referenceOrg;
		}

		public String getReferenceName() {
			return referenceName;
		}

		public void setReferenceName(String referenceName) {
			this.referenceName = referenceName;
		}

		public String getReferencePost() {
			return referencePost;
		}

		public void setReferencePost(String referencePost) {
			this.referencePost = referencePost;
		}

		public String getReferencePhone() {
			return referencePhone;
		}

		public void setReferencePhone(String referencePhone) {
			this.referencePhone = referencePhone;
		}

		public String getUsedNames() {
			return usedNames;
		}

		public void setUsedNames(String usedNames) {
			this.usedNames = usedNames;
		}

		public String getIsBlack() {
			return isBlack;
		}

		public void setIsBlack(String isBlack) {
			this.isBlack = isBlack;
		}

		public String getOrgName() {
			return orgName;
		}

		public void setOrgName(String orgName) {
			this.orgName = orgName;
		}

		public String getAuditStatus() {
			return auditStatus;
		}

		public void setAuditStatus(String auditStatus) {
			this.auditStatus = auditStatus;
		}

		public String getProvince() {
			return province;
		}

		public void setProvince(String province) {
			this.province = province;
		}

		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
		}

		public String getScope() {
			return scope;
		}

		public void setScope(String scope) {
			this.scope = scope;
		}

		public String getSafeCode() {
			return safeCode;
		}

		public void setSafeCode(String safeCode) {
			this.safeCode = safeCode;
		}

		public String getTaxpayerNum() {
			return taxpayerNum;
		}

		public void setTaxpayerNum(String taxpayerNum) {
			this.taxpayerNum = taxpayerNum;
		}

		public String getTaxpayerType() {
			return taxpayerType;
		}

		public void setTaxpayerType(String taxpayerType) {
			this.taxpayerType = taxpayerType;
		}

		public String getBankName() {
			return bankName;
		}

		public void setBankName(String bankName) {
			this.bankName = bankName;
		}

		public String getBankAccount() {
			return bankAccount;
		}

		public void setBankAccount(String bankAccount) {
			this.bankAccount = bankAccount;
		}

		public String getUseFlag() {
			return useFlag;
		}

		public void setUseFlag(String useFlag) {
			this.useFlag = useFlag;
		}

		public BigDecimal getRealRegMoney() {
			return realRegMoney;
		}

		public void setRealRegMoney(BigDecimal realRegMoney) {
			this.realRegMoney = realRegMoney;
		}

		public String getBusinessType() {
			return businessType;
		}

		public void setBusinessType(String businessType) {
			this.businessType = businessType;
		}

		public Date getLicenceDate() {
			return licenceDate;
		}

		public void setLicenceDate(Date licenceDate) {
			this.licenceDate = licenceDate;
		}

		public Date getQualificateDate() {
			return qualificateDate;
		}

		public void setQualificateDate(Date qualificateDate) {
			this.qualificateDate = qualificateDate;
		}

		public Date getTaxRegDate() {
			return taxRegDate;
		}

		public void setTaxRegDate(Date taxRegDate) {
			this.taxRegDate = taxRegDate;
		}

		public Date getSafeBookDate() {
			return safeBookDate;
		}

		public void setSafeBookDate(Date safeBookDate) {
			this.safeBookDate = safeBookDate;
		}

		public String getComOrgName() {
			return comOrgName;
		}

		public void setComOrgName(String comOrgName) {
			this.comOrgName = comOrgName;
		}

		public String getIsNeedfushen() {
			return isNeedfushen;
		}

		public void setIsNeedfushen(String isNeedfushen) {
			this.isNeedfushen = isNeedfushen;
		}

		public String getFuheStatus() {
			return fuheStatus;
		}

		public void setFuheStatus(String fuheStatus) {
			this.fuheStatus = fuheStatus;
		}

		public String getFushenStatus() {
			return fushenStatus;
		}

		public void setFushenStatus(String fushenStatus) {
			this.fushenStatus = fushenStatus;
		}

		public String getOrgID() {
			return orgID;
		}

		public void setOrgID(String orgID) {
			this.orgID = orgID;
		}
}
