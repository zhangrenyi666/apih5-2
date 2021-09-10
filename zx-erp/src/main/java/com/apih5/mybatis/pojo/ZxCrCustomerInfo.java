package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCrCustomerInfo extends BasePojo {
    // 主键
    private String zxCrCustomerInfoId;

    // 统一社会信用代码
    private String orgCertificate;

    // 协作单位名称
    private String customerName;

    // 注册资本金(元)
    private BigDecimal regMoney;

    // 法定代表人
    private String corparation;

    // 法定代表人身份证号码
    private String pricinpalIDCard;

    // 法定代表人电话
    private String pricinpalMobile;

    // 企业详细地址
    private String pricinpalAddr;

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

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    
    // 所属机构（项目ID）
    private String orgID; 

	// 所属公司名称
    private String companyName; 
    
	// 所属公司ID
    private String companyId;
    
    // 等级
    private String quLevel;

    public String getQuLevel() {
		return quLevel;
	}

	public void setQuLevel(String quLevel) {
		this.quLevel = quLevel;
	}

	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
    public String getZxCrCustomerInfoId() {
        return zxCrCustomerInfoId == null ? "" : zxCrCustomerInfoId.trim();
    }

    public void setZxCrCustomerInfoId(String zxCrCustomerInfoId) {
        this.zxCrCustomerInfoId = zxCrCustomerInfoId == null ? null : zxCrCustomerInfoId.trim();
    }

    public String getOrgCertificate() {
        return orgCertificate == null ? "" : orgCertificate.trim();
    }

    public void setOrgCertificate(String orgCertificate) {
        this.orgCertificate = orgCertificate == null ? null : orgCertificate.trim();
    }

    public String getCustomerName() {
        return customerName == null ? "" : customerName.trim();
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public BigDecimal getRegMoney() {
        return regMoney;
    }

    public void setRegMoney(BigDecimal regMoney) {
        this.regMoney = regMoney;
    }

    public String getCorparation() {
        return corparation == null ? "" : corparation.trim();
    }

    public void setCorparation(String corparation) {
        this.corparation = corparation == null ? null : corparation.trim();
    }

    public String getPricinpalIDCard() {
        return pricinpalIDCard == null ? "" : pricinpalIDCard.trim();
    }

    public void setPricinpalIDCard(String pricinpalIDCard) {
        this.pricinpalIDCard = pricinpalIDCard == null ? null : pricinpalIDCard.trim();
    }

    public String getPricinpalMobile() {
        return pricinpalMobile == null ? "" : pricinpalMobile.trim();
    }

    public void setPricinpalMobile(String pricinpalMobile) {
        this.pricinpalMobile = pricinpalMobile == null ? null : pricinpalMobile.trim();
    }

    public String getPricinpalAddr() {
        return pricinpalAddr == null ? "" : pricinpalAddr.trim();
    }

    public void setPricinpalAddr(String pricinpalAddr) {
        this.pricinpalAddr = pricinpalAddr == null ? null : pricinpalAddr.trim();
    }

    public String getLicenceNO() {
        return licenceNO == null ? "" : licenceNO.trim();
    }

    public void setLicenceNO(String licenceNO) {
        this.licenceNO = licenceNO == null ? null : licenceNO.trim();
    }

    public String getQualificateNo() {
        return qualificateNo == null ? "" : qualificateNo.trim();
    }

    public void setQualificateNo(String qualificateNo) {
        this.qualificateNo = qualificateNo == null ? null : qualificateNo.trim();
    }

    public String getTaxRegNo() {
        return taxRegNo == null ? "" : taxRegNo.trim();
    }

    public void setTaxRegNo(String taxRegNo) {
        this.taxRegNo = taxRegNo == null ? null : taxRegNo.trim();
    }

    public String getQualifiLevel() {
        return qualifiLevel == null ? "" : qualifiLevel.trim();
    }

    public void setQualifiLevel(String qualifiLevel) {
        this.qualifiLevel = qualifiLevel == null ? null : qualifiLevel.trim();
    }

    public String getDateStatus() {
        return dateStatus == null ? "" : dateStatus.trim();
    }

    public void setDateStatus(String dateStatus) {
        this.dateStatus = dateStatus == null ? null : dateStatus.trim();
    }

    public String getStrategicSupplier() {
        return strategicSupplier == null ? "" : strategicSupplier.trim();
    }

    public void setStrategicSupplier(String strategicSupplier) {
        this.strategicSupplier = strategicSupplier == null ? null : strategicSupplier.trim();
    }

    public BigDecimal getCreditLineAmt() {
        return creditLineAmt;
    }

    public void setCreditLineAmt(BigDecimal creditLineAmt) {
        this.creditLineAmt = creditLineAmt;
    }

    public String getReferenceOrg() {
        return referenceOrg == null ? "" : referenceOrg.trim();
    }

    public void setReferenceOrg(String referenceOrg) {
        this.referenceOrg = referenceOrg == null ? null : referenceOrg.trim();
    }

    public String getReferenceName() {
        return referenceName == null ? "" : referenceName.trim();
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName == null ? null : referenceName.trim();
    }

    public String getReferencePost() {
        return referencePost == null ? "" : referencePost.trim();
    }

    public void setReferencePost(String referencePost) {
        this.referencePost = referencePost == null ? null : referencePost.trim();
    }

    public String getReferencePhone() {
        return referencePhone == null ? "" : referencePhone.trim();
    }

    public void setReferencePhone(String referencePhone) {
        this.referencePhone = referencePhone == null ? null : referencePhone.trim();
    }

    public String getUsedNames() {
        return usedNames == null ? "" : usedNames.trim();
    }

    public void setUsedNames(String usedNames) {
        this.usedNames = usedNames == null ? null : usedNames.trim();
    }

    public String getIsBlack() {
        return isBlack == null ? "" : isBlack.trim();
    }

    public void setIsBlack(String isBlack) {
        this.isBlack = isBlack == null ? null : isBlack.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getAuditStatus() {
        return auditStatus == null ? "" : auditStatus.trim();
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public String getProvince() {
        return province == null ? "" : province.trim();
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getArea() {
        return area == null ? "" : area.trim();
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getScope() {
        return scope == null ? "" : scope.trim();
    }

    public void setScope(String scope) {
        this.scope = scope == null ? null : scope.trim();
    }

    public String getSafeCode() {
        return safeCode == null ? "" : safeCode.trim();
    }

    public void setSafeCode(String safeCode) {
        this.safeCode = safeCode == null ? null : safeCode.trim();
    }

    public String getTaxpayerNum() {
        return taxpayerNum == null ? "" : taxpayerNum.trim();
    }

    public void setTaxpayerNum(String taxpayerNum) {
        this.taxpayerNum = taxpayerNum == null ? null : taxpayerNum.trim();
    }

    public String getTaxpayerType() {
        return taxpayerType == null ? "" : taxpayerType.trim();
    }

    public void setTaxpayerType(String taxpayerType) {
        this.taxpayerType = taxpayerType == null ? null : taxpayerType.trim();
    }

    public String getBankName() {
        return bankName == null ? "" : bankName.trim();
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankAccount() {
        return bankAccount == null ? "" : bankAccount.trim();
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount == null ? null : bankAccount.trim();
    }

    public String getUseFlag() {
        return useFlag == null ? "" : useFlag.trim();
    }

    public void setUseFlag(String useFlag) {
        this.useFlag = useFlag == null ? null : useFlag.trim();
    }

    public BigDecimal getRealRegMoney() {
        return realRegMoney;
    }

    public void setRealRegMoney(BigDecimal realRegMoney) {
        this.realRegMoney = realRegMoney;
    }

    public String getBusinessType() {
        return businessType == null ? "" : businessType.trim();
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
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
        return comOrgName == null ? "" : comOrgName.trim();
    }

    public void setComOrgName(String comOrgName) {
        this.comOrgName = comOrgName == null ? null : comOrgName.trim();
    }

    public String getIsNeedfushen() {
        return isNeedfushen == null ? "" : isNeedfushen.trim();
    }

    public void setIsNeedfushen(String isNeedfushen) {
        this.isNeedfushen = isNeedfushen == null ? null : isNeedfushen.trim();
    }

    public String getFuheStatus() {
        return fuheStatus == null ? "" : fuheStatus.trim();
    }

    public void setFuheStatus(String fuheStatus) {
        this.fuheStatus = fuheStatus == null ? null : fuheStatus.trim();
    }

    public String getFushenStatus() {
        return fushenStatus == null ? "" : fushenStatus.trim();
    }

    public void setFushenStatus(String fushenStatus) {
        this.fushenStatus = fushenStatus == null ? null : fushenStatus.trim();
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

}
