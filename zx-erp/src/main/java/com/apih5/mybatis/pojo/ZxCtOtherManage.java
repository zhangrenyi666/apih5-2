package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCtOtherManage extends BasePojo {
    // 主键
    private String zxCtOtherManageId;

    // 合同编号
    private String contractNo;

    // 合同名称
    private String contractName;

    // 摘要
    private String subject;

    // 合同内容
    private String content;

    // 所属项目ID
    private String orgId;

    // 甲方ID
    private String firstId;

    // 合同甲方
    private String firstName;

    // 项目经理
    private String firstPrincipal;

    // 甲方代表身份证
    private String firstPrincipalIdCard;

    // 甲方联系电话
    private String firstUnitTel;

    // 乙方ID
    private String secondId;

    // 乙方名称
    private String secondName;

    // 乙方代表
    private String secondPrincipal;

    // 乙方代表身份证
    private String secondPrincipalIdCard;

    // 乙方(固话)
    private String secondUnitTel;

    // 丙方
    private String thirdName;

    // 收付类型
    private String payType;

    // 所属处部ID
    private String locationInDeprId;

    // 登记日期
    private Date registerDate;

    // 中标日期
    private Date bidDate;

    // 签订日期
    private Date signatureDate;

    // 含税合同总价(万元)
    private BigDecimal contractCost;

    // 合同工期(天)
    private String csTimeLimit;

    // 合同开工日期
    private Date planStartDate;

    // 实际开始时间
    private Date actualStartDate;

    // 合同竣工日期
    private Date planEndDate;

    // 实际结束时间
    private Date actualEndDate;

    // 监理单位
    private String consultative;

    // 监理单位电话
    private String consultativeTel;

    // 业主联系电话
    private String ownerLinkTel;

    // 项目经理
    private String projectManager;

    // 项目经理电话
    private String projectManagerTel;

    // 项目总工姓名
    private String projectChiefName;

    // 项目总工电话
    private String projectChiefTel;

    // 是否有标的
    private String hasDetail;

    // 合同状态
    private String contractStatus;

    // 项目编号
    private String projectNo;

    // 项目简称
    private String shortName;

    // 风险抵押金
    private BigDecimal ventureGuaranty;

    // 内部承包总价(万元)
    private BigDecimal innerContractSum;

    // 税率
    private String faxRate;

    // 管理费率
    private String manageRate;

    // 质保费率
    private String quanlityFeeRate;

    // 供货总额
    private BigDecimal goodsSum;

    // 付款约定
    private String paymentAssumpsit;

    // 装运方式约定
    private String transferAssumpsit;

    // 其他条款
    private String otherAssumpsit;

    // 乙方性质
    private String agent;

    // 经营指标
    private String manageIndex;

    // 乙方(手机)
    private String secondPhone;

    // 乙方(传真)
    private String secondFax;

    // 合同类型
    private String contractType;

    // 累计结算金额
    private BigDecimal totalSettleAmount;

    // 累计支付金额
    private BigDecimal totalPayAmount;

    // 清单
    private String pp8;

    // pp9
    private String pp9;

    // pp10
    private String pp10;

    // 合同编码
    private String contractCode;

    // 机构编码
    private String organCode;

    // 承建单位简称
    private String contractorAbbreviation;

    // 中标单位简称
    private String bidWinnerAbbreviation;

    // 项目所在省份简称
    private String projectProvinceAbbreviation;

    // 项目简称
    private String projectAbbreviation;

    // 标段号
    private String lotNo;

    // 合同类别
    private String contractCategory;

    // 合同序号
    private String contractNumber;

    // 合同评审ID（旧系统字段fromApplyId）
    private String zxCtOtherId;

    // 业主合同功能码
    private String ownerContractFunctionCode;

    // 同一公司
    private String oneFirm;

    // 质量保证金扣除比例（%）
    private String qualityBailRate;

    // 安全生产保证金扣除比例（%）
    private String prodSafeBailRate;

    // 农民工工资偿付保证金扣除比例（%）
    private String payBailRate;

    // 变更后含税合同金额(万元)
    private BigDecimal alterContractSum;

    // 结算情况
    private String settleType;

    // 不含税合同总价(万元)
    private BigDecimal contractCostNoTax;

    // 合同税额(万元)
    private BigDecimal contractCostTax;

    // 变更后不含税合同金额(万元)
    private BigDecimal alterContractSumNoTax;

    // 变更后合同税额(万元)
    private BigDecimal alterContractSumTax;

    // 税率(%)
    private String taxRate;

    // 是否抵扣
    private String isDeduct;

    // 授权委托人姓名
    private String wtrName;

    // 授权委托人身份证号
    private String wtrPhone;

    // 推荐人姓名
    private String referenceName;

    // 推荐人职务
    private String referencePost;

    // 推荐人电话
    private String referencePhone;

    // comID
    private String comId;

    // 财务系统ID
    private String fiId;

    // 核算单位ID
    private String accountUnitId;

    // 核算单位编号
    private String accountUnitCode;

    // 核算单位
    private String accountUnitName;

    // 复核日期
    private Date doubleCheckDate;

    // 录入日期
    private Date writeDate;

    // 核算部门财务id
    private String accountDepId;

    // 核算部门编号
    private String accountDepCode;

    // 核算部门
    private String accountDepName;

    // 往来单位编号
    private String secondIdCode;

    // 录入人
    private String writer;

    // 系统编号
    private String systemNo;

    // 币种编号
    private String currencyCode;

    // 合同性质
    private String ctrNature;

    // 合同更新状态
    private String ctrUpdateState;

    // 财务合同状态
    private String fiCtrState;

    // 收支方向
    private String revenueDir;

    // 发票类型
    private String invoiceType;

    // 制式合同
    private String staCtr;

    // 重点合同
    private String keyCtr;

    // notDisplay
    private String notDisplay;

    // 复核人
    private String doubleCheckPerson;

    // 往来单位编号
    private String secondIdCodeBh;

    // 备注
    private String remark;

    // 合同录用类型
    private String auditStatus;

    // 所属项目id
    private String projectId;

    // 所属项目名称
    private String projectName;

    // 排序
    private int sort=0;

    // 合同码ID
    private String contractCodeId;

    // 管理机构
    private String orgName;

    // 清单是否审核
    private String workBookStatusP2;

    // 机械及其他结算引用数
    private BigDecimal equipSettleUseCount;

    // 所属公司id
    private String companyId;

    // 所属公司名称
    private String companyName;

    // 附件
    private List<ZxErpFile> zxErpFileList;

    // 保证金列表
    private List<ZxCtOtherManageAmtRate> zxCtOtherManageAmtRateList;

    // work_id
    private String workId;

    public String getZxCtOtherManageId() {
        return zxCtOtherManageId == null ? "" : zxCtOtherManageId.trim();
    }

    public void setZxCtOtherManageId(String zxCtOtherManageId) {
        this.zxCtOtherManageId = zxCtOtherManageId == null ? null : zxCtOtherManageId.trim();
    }

    public String getContractNo() {
        return contractNo == null ? "" : contractNo.trim();
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public String getContractName() {
        return contractName == null ? "" : contractName.trim();
    }

    public void setContractName(String contractName) {
        this.contractName = contractName == null ? null : contractName.trim();
    }

    public String getSubject() {
        return subject == null ? "" : subject.trim();
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    public String getContent() {
        return content == null ? "" : content.trim();
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getOrgId() {
        return orgId == null ? "" : orgId.trim();
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getFirstId() {
        return firstId == null ? "" : firstId.trim();
    }

    public void setFirstId(String firstId) {
        this.firstId = firstId == null ? null : firstId.trim();
    }

    public String getFirstName() {
        return firstName == null ? "" : firstName.trim();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName == null ? null : firstName.trim();
    }

    public String getFirstPrincipal() {
        return firstPrincipal == null ? "" : firstPrincipal.trim();
    }

    public void setFirstPrincipal(String firstPrincipal) {
        this.firstPrincipal = firstPrincipal == null ? null : firstPrincipal.trim();
    }

    public String getFirstPrincipalIdCard() {
        return firstPrincipalIdCard == null ? "" : firstPrincipalIdCard.trim();
    }

    public void setFirstPrincipalIdCard(String firstPrincipalIdCard) {
        this.firstPrincipalIdCard = firstPrincipalIdCard == null ? null : firstPrincipalIdCard.trim();
    }

    public String getFirstUnitTel() {
        return firstUnitTel == null ? "" : firstUnitTel.trim();
    }

    public void setFirstUnitTel(String firstUnitTel) {
        this.firstUnitTel = firstUnitTel == null ? null : firstUnitTel.trim();
    }

    public String getSecondId() {
        return secondId == null ? "" : secondId.trim();
    }

    public void setSecondId(String secondId) {
        this.secondId = secondId == null ? null : secondId.trim();
    }

    public String getSecondName() {
        return secondName == null ? "" : secondName.trim();
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName == null ? null : secondName.trim();
    }

    public String getSecondPrincipal() {
        return secondPrincipal == null ? "" : secondPrincipal.trim();
    }

    public void setSecondPrincipal(String secondPrincipal) {
        this.secondPrincipal = secondPrincipal == null ? null : secondPrincipal.trim();
    }

    public String getSecondPrincipalIdCard() {
        return secondPrincipalIdCard == null ? "" : secondPrincipalIdCard.trim();
    }

    public void setSecondPrincipalIdCard(String secondPrincipalIdCard) {
        this.secondPrincipalIdCard = secondPrincipalIdCard == null ? null : secondPrincipalIdCard.trim();
    }

    public String getSecondUnitTel() {
        return secondUnitTel == null ? "" : secondUnitTel.trim();
    }

    public void setSecondUnitTel(String secondUnitTel) {
        this.secondUnitTel = secondUnitTel == null ? null : secondUnitTel.trim();
    }

    public String getThirdName() {
        return thirdName == null ? "" : thirdName.trim();
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName == null ? null : thirdName.trim();
    }

    public String getPayType() {
        return payType == null ? "" : payType.trim();
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getLocationInDeprId() {
        return locationInDeprId == null ? "" : locationInDeprId.trim();
    }

    public void setLocationInDeprId(String locationInDeprId) {
        this.locationInDeprId = locationInDeprId == null ? null : locationInDeprId.trim();
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getBidDate() {
        return bidDate;
    }

    public void setBidDate(Date bidDate) {
        this.bidDate = bidDate;
    }

    public Date getSignatureDate() {
        return signatureDate;
    }

    public void setSignatureDate(Date signatureDate) {
        this.signatureDate = signatureDate;
    }

    public BigDecimal getContractCost() {
        return contractCost;
    }

    public void setContractCost(BigDecimal contractCost) {
        this.contractCost = contractCost;
    }

    public String getCsTimeLimit() {
        return csTimeLimit == null ? "" : csTimeLimit.trim();
    }

    public void setCsTimeLimit(String csTimeLimit) {
        this.csTimeLimit = csTimeLimit == null ? null : csTimeLimit.trim();
    }

    public Date getPlanStartDate() {
        return planStartDate;
    }

    public void setPlanStartDate(Date planStartDate) {
        this.planStartDate = planStartDate;
    }

    public Date getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(Date actualStartDate) {
        this.actualStartDate = actualStartDate;
    }

    public Date getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(Date planEndDate) {
        this.planEndDate = planEndDate;
    }

    public Date getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(Date actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public String getConsultative() {
        return consultative == null ? "" : consultative.trim();
    }

    public void setConsultative(String consultative) {
        this.consultative = consultative == null ? null : consultative.trim();
    }

    public String getConsultativeTel() {
        return consultativeTel == null ? "" : consultativeTel.trim();
    }

    public void setConsultativeTel(String consultativeTel) {
        this.consultativeTel = consultativeTel == null ? null : consultativeTel.trim();
    }

    public String getOwnerLinkTel() {
        return ownerLinkTel == null ? "" : ownerLinkTel.trim();
    }

    public void setOwnerLinkTel(String ownerLinkTel) {
        this.ownerLinkTel = ownerLinkTel == null ? null : ownerLinkTel.trim();
    }

    public String getProjectManager() {
        return projectManager == null ? "" : projectManager.trim();
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager == null ? null : projectManager.trim();
    }

    public String getProjectManagerTel() {
        return projectManagerTel == null ? "" : projectManagerTel.trim();
    }

    public void setProjectManagerTel(String projectManagerTel) {
        this.projectManagerTel = projectManagerTel == null ? null : projectManagerTel.trim();
    }

    public String getProjectChiefName() {
        return projectChiefName == null ? "" : projectChiefName.trim();
    }

    public void setProjectChiefName(String projectChiefName) {
        this.projectChiefName = projectChiefName == null ? null : projectChiefName.trim();
    }

    public String getProjectChiefTel() {
        return projectChiefTel == null ? "" : projectChiefTel.trim();
    }

    public void setProjectChiefTel(String projectChiefTel) {
        this.projectChiefTel = projectChiefTel == null ? null : projectChiefTel.trim();
    }

    public String getHasDetail() {
        return hasDetail == null ? "" : hasDetail.trim();
    }

    public void setHasDetail(String hasDetail) {
        this.hasDetail = hasDetail == null ? null : hasDetail.trim();
    }

    public String getContractStatus() {
        return contractStatus == null ? "" : contractStatus.trim();
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus == null ? null : contractStatus.trim();
    }

    public String getProjectNo() {
        return projectNo == null ? "" : projectNo.trim();
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo == null ? null : projectNo.trim();
    }

    public String getShortName() {
        return shortName == null ? "" : shortName.trim();
    }

    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }

    public BigDecimal getVentureGuaranty() {
        return ventureGuaranty;
    }

    public void setVentureGuaranty(BigDecimal ventureGuaranty) {
        this.ventureGuaranty = ventureGuaranty;
    }

    public BigDecimal getInnerContractSum() {
        return innerContractSum;
    }

    public void setInnerContractSum(BigDecimal innerContractSum) {
        this.innerContractSum = innerContractSum;
    }

    public String getFaxRate() {
        return faxRate == null ? "" : faxRate.trim();
    }

    public void setFaxRate(String faxRate) {
        this.faxRate = faxRate == null ? null : faxRate.trim();
    }

    public String getManageRate() {
        return manageRate == null ? "" : manageRate.trim();
    }

    public void setManageRate(String manageRate) {
        this.manageRate = manageRate == null ? null : manageRate.trim();
    }

    public String getQuanlityFeeRate() {
        return quanlityFeeRate == null ? "" : quanlityFeeRate.trim();
    }

    public void setQuanlityFeeRate(String quanlityFeeRate) {
        this.quanlityFeeRate = quanlityFeeRate == null ? null : quanlityFeeRate.trim();
    }

    public BigDecimal getGoodsSum() {
        return goodsSum;
    }

    public void setGoodsSum(BigDecimal goodsSum) {
        this.goodsSum = goodsSum;
    }

    public String getPaymentAssumpsit() {
        return paymentAssumpsit == null ? "" : paymentAssumpsit.trim();
    }

    public void setPaymentAssumpsit(String paymentAssumpsit) {
        this.paymentAssumpsit = paymentAssumpsit == null ? null : paymentAssumpsit.trim();
    }

    public String getTransferAssumpsit() {
        return transferAssumpsit == null ? "" : transferAssumpsit.trim();
    }

    public void setTransferAssumpsit(String transferAssumpsit) {
        this.transferAssumpsit = transferAssumpsit == null ? null : transferAssumpsit.trim();
    }

    public String getOtherAssumpsit() {
        return otherAssumpsit == null ? "" : otherAssumpsit.trim();
    }

    public void setOtherAssumpsit(String otherAssumpsit) {
        this.otherAssumpsit = otherAssumpsit == null ? null : otherAssumpsit.trim();
    }

    public String getAgent() {
        return agent == null ? "" : agent.trim();
    }

    public void setAgent(String agent) {
        this.agent = agent == null ? null : agent.trim();
    }

    public String getManageIndex() {
        return manageIndex == null ? "" : manageIndex.trim();
    }

    public void setManageIndex(String manageIndex) {
        this.manageIndex = manageIndex == null ? null : manageIndex.trim();
    }

    public String getSecondPhone() {
        return secondPhone == null ? "" : secondPhone.trim();
    }

    public void setSecondPhone(String secondPhone) {
        this.secondPhone = secondPhone == null ? null : secondPhone.trim();
    }

    public String getSecondFax() {
        return secondFax == null ? "" : secondFax.trim();
    }

    public void setSecondFax(String secondFax) {
        this.secondFax = secondFax == null ? null : secondFax.trim();
    }

    public String getContractType() {
        return contractType == null ? "" : contractType.trim();
    }

    public void setContractType(String contractType) {
        this.contractType = contractType == null ? null : contractType.trim();
    }

    public BigDecimal getTotalSettleAmount() {
        return totalSettleAmount;
    }

    public void setTotalSettleAmount(BigDecimal totalSettleAmount) {
        this.totalSettleAmount = totalSettleAmount;
    }

    public BigDecimal getTotalPayAmount() {
        return totalPayAmount;
    }

    public void setTotalPayAmount(BigDecimal totalPayAmount) {
        this.totalPayAmount = totalPayAmount;
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

    public String getContractCode() {
        return contractCode == null ? "" : contractCode.trim();
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode == null ? null : contractCode.trim();
    }

    public String getOrganCode() {
        return organCode == null ? "" : organCode.trim();
    }

    public void setOrganCode(String organCode) {
        this.organCode = organCode == null ? null : organCode.trim();
    }

    public String getContractorAbbreviation() {
        return contractorAbbreviation == null ? "" : contractorAbbreviation.trim();
    }

    public void setContractorAbbreviation(String contractorAbbreviation) {
        this.contractorAbbreviation = contractorAbbreviation == null ? null : contractorAbbreviation.trim();
    }

    public String getBidWinnerAbbreviation() {
        return bidWinnerAbbreviation == null ? "" : bidWinnerAbbreviation.trim();
    }

    public void setBidWinnerAbbreviation(String bidWinnerAbbreviation) {
        this.bidWinnerAbbreviation = bidWinnerAbbreviation == null ? null : bidWinnerAbbreviation.trim();
    }

    public String getProjectProvinceAbbreviation() {
        return projectProvinceAbbreviation == null ? "" : projectProvinceAbbreviation.trim();
    }

    public void setProjectProvinceAbbreviation(String projectProvinceAbbreviation) {
        this.projectProvinceAbbreviation = projectProvinceAbbreviation == null ? null : projectProvinceAbbreviation.trim();
    }

    public String getProjectAbbreviation() {
        return projectAbbreviation == null ? "" : projectAbbreviation.trim();
    }

    public void setProjectAbbreviation(String projectAbbreviation) {
        this.projectAbbreviation = projectAbbreviation == null ? null : projectAbbreviation.trim();
    }

    public String getLotNo() {
        return lotNo == null ? "" : lotNo.trim();
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo == null ? null : lotNo.trim();
    }

    public String getContractCategory() {
        return contractCategory == null ? "" : contractCategory.trim();
    }

    public void setContractCategory(String contractCategory) {
        this.contractCategory = contractCategory == null ? null : contractCategory.trim();
    }

    public String getContractNumber() {
        return contractNumber == null ? "" : contractNumber.trim();
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber == null ? null : contractNumber.trim();
    }

    public String getZxCtOtherId() {
        return zxCtOtherId == null ? "" : zxCtOtherId.trim();
    }

    public void setZxCtOtherId(String zxCtOtherId) {
        this.zxCtOtherId = zxCtOtherId == null ? null : zxCtOtherId.trim();
    }

    public String getOwnerContractFunctionCode() {
        return ownerContractFunctionCode == null ? "" : ownerContractFunctionCode.trim();
    }

    public void setOwnerContractFunctionCode(String ownerContractFunctionCode) {
        this.ownerContractFunctionCode = ownerContractFunctionCode == null ? null : ownerContractFunctionCode.trim();
    }

    public String getOneFirm() {
        return oneFirm == null ? "" : oneFirm.trim();
    }

    public void setOneFirm(String oneFirm) {
        this.oneFirm = oneFirm == null ? null : oneFirm.trim();
    }

    public String getQualityBailRate() {
        return qualityBailRate == null ? "" : qualityBailRate.trim();
    }

    public void setQualityBailRate(String qualityBailRate) {
        this.qualityBailRate = qualityBailRate == null ? null : qualityBailRate.trim();
    }

    public String getProdSafeBailRate() {
        return prodSafeBailRate == null ? "" : prodSafeBailRate.trim();
    }

    public void setProdSafeBailRate(String prodSafeBailRate) {
        this.prodSafeBailRate = prodSafeBailRate == null ? null : prodSafeBailRate.trim();
    }

    public String getPayBailRate() {
        return payBailRate == null ? "" : payBailRate.trim();
    }

    public void setPayBailRate(String payBailRate) {
        this.payBailRate = payBailRate == null ? null : payBailRate.trim();
    }

    public BigDecimal getAlterContractSum() {
        return alterContractSum;
    }

    public void setAlterContractSum(BigDecimal alterContractSum) {
        this.alterContractSum = alterContractSum;
    }

    public String getSettleType() {
        return settleType == null ? "" : settleType.trim();
    }

    public void setSettleType(String settleType) {
        this.settleType = settleType == null ? null : settleType.trim();
    }

    public BigDecimal getContractCostNoTax() {
        return contractCostNoTax;
    }

    public void setContractCostNoTax(BigDecimal contractCostNoTax) {
        this.contractCostNoTax = contractCostNoTax;
    }

    public BigDecimal getContractCostTax() {
        return contractCostTax;
    }

    public void setContractCostTax(BigDecimal contractCostTax) {
        this.contractCostTax = contractCostTax;
    }

    public BigDecimal getAlterContractSumNoTax() {
        return alterContractSumNoTax;
    }

    public void setAlterContractSumNoTax(BigDecimal alterContractSumNoTax) {
        this.alterContractSumNoTax = alterContractSumNoTax;
    }

    public BigDecimal getAlterContractSumTax() {
        return alterContractSumTax;
    }

    public void setAlterContractSumTax(BigDecimal alterContractSumTax) {
        this.alterContractSumTax = alterContractSumTax;
    }

    public String getTaxRate() {
        return taxRate == null ? "" : taxRate.trim();
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate == null ? null : taxRate.trim();
    }

    public String getIsDeduct() {
        return isDeduct == null ? "" : isDeduct.trim();
    }

    public void setIsDeduct(String isDeduct) {
        this.isDeduct = isDeduct == null ? null : isDeduct.trim();
    }

    public String getWtrName() {
        return wtrName == null ? "" : wtrName.trim();
    }

    public void setWtrName(String wtrName) {
        this.wtrName = wtrName == null ? null : wtrName.trim();
    }

    public String getWtrPhone() {
        return wtrPhone == null ? "" : wtrPhone.trim();
    }

    public void setWtrPhone(String wtrPhone) {
        this.wtrPhone = wtrPhone == null ? null : wtrPhone.trim();
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

    public String getComId() {
        return comId == null ? "" : comId.trim();
    }

    public void setComId(String comId) {
        this.comId = comId == null ? null : comId.trim();
    }

    public String getFiId() {
        return fiId == null ? "" : fiId.trim();
    }

    public void setFiId(String fiId) {
        this.fiId = fiId == null ? null : fiId.trim();
    }

    public String getAccountUnitId() {
        return accountUnitId == null ? "" : accountUnitId.trim();
    }

    public void setAccountUnitId(String accountUnitId) {
        this.accountUnitId = accountUnitId == null ? null : accountUnitId.trim();
    }

    public String getAccountUnitCode() {
        return accountUnitCode == null ? "" : accountUnitCode.trim();
    }

    public void setAccountUnitCode(String accountUnitCode) {
        this.accountUnitCode = accountUnitCode == null ? null : accountUnitCode.trim();
    }

    public String getAccountUnitName() {
        return accountUnitName == null ? "" : accountUnitName.trim();
    }

    public void setAccountUnitName(String accountUnitName) {
        this.accountUnitName = accountUnitName == null ? null : accountUnitName.trim();
    }

    public Date getDoubleCheckDate() {
        return doubleCheckDate;
    }

    public void setDoubleCheckDate(Date doubleCheckDate) {
        this.doubleCheckDate = doubleCheckDate;
    }

    public Date getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(Date writeDate) {
        this.writeDate = writeDate;
    }

    public String getAccountDepId() {
        return accountDepId == null ? "" : accountDepId.trim();
    }

    public void setAccountDepId(String accountDepId) {
        this.accountDepId = accountDepId == null ? null : accountDepId.trim();
    }

    public String getAccountDepCode() {
        return accountDepCode == null ? "" : accountDepCode.trim();
    }

    public void setAccountDepCode(String accountDepCode) {
        this.accountDepCode = accountDepCode == null ? null : accountDepCode.trim();
    }

    public String getAccountDepName() {
        return accountDepName == null ? "" : accountDepName.trim();
    }

    public void setAccountDepName(String accountDepName) {
        this.accountDepName = accountDepName == null ? null : accountDepName.trim();
    }

    public String getSecondIdCode() {
        return secondIdCode == null ? "" : secondIdCode.trim();
    }

    public void setSecondIdCode(String secondIdCode) {
        this.secondIdCode = secondIdCode == null ? null : secondIdCode.trim();
    }

    public String getWriter() {
        return writer == null ? "" : writer.trim();
    }

    public void setWriter(String writer) {
        this.writer = writer == null ? null : writer.trim();
    }

    public String getSystemNo() {
        return systemNo == null ? "" : systemNo.trim();
    }

    public void setSystemNo(String systemNo) {
        this.systemNo = systemNo == null ? null : systemNo.trim();
    }

    public String getCurrencyCode() {
        return currencyCode == null ? "" : currencyCode.trim();
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode == null ? null : currencyCode.trim();
    }

    public String getCtrNature() {
        return ctrNature == null ? "" : ctrNature.trim();
    }

    public void setCtrNature(String ctrNature) {
        this.ctrNature = ctrNature == null ? null : ctrNature.trim();
    }

    public String getCtrUpdateState() {
        return ctrUpdateState == null ? "" : ctrUpdateState.trim();
    }

    public void setCtrUpdateState(String ctrUpdateState) {
        this.ctrUpdateState = ctrUpdateState == null ? null : ctrUpdateState.trim();
    }

    public String getFiCtrState() {
        return fiCtrState == null ? "" : fiCtrState.trim();
    }

    public void setFiCtrState(String fiCtrState) {
        this.fiCtrState = fiCtrState == null ? null : fiCtrState.trim();
    }

    public String getRevenueDir() {
        return revenueDir == null ? "" : revenueDir.trim();
    }

    public void setRevenueDir(String revenueDir) {
        this.revenueDir = revenueDir == null ? null : revenueDir.trim();
    }

    public String getInvoiceType() {
        return invoiceType == null ? "" : invoiceType.trim();
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType == null ? null : invoiceType.trim();
    }

    public String getStaCtr() {
        return staCtr == null ? "" : staCtr.trim();
    }

    public void setStaCtr(String staCtr) {
        this.staCtr = staCtr == null ? null : staCtr.trim();
    }

    public String getKeyCtr() {
        return keyCtr == null ? "" : keyCtr.trim();
    }

    public void setKeyCtr(String keyCtr) {
        this.keyCtr = keyCtr == null ? null : keyCtr.trim();
    }

    public String getNotDisplay() {
        return notDisplay == null ? "" : notDisplay.trim();
    }

    public void setNotDisplay(String notDisplay) {
        this.notDisplay = notDisplay == null ? null : notDisplay.trim();
    }

    public String getDoubleCheckPerson() {
        return doubleCheckPerson == null ? "" : doubleCheckPerson.trim();
    }

    public void setDoubleCheckPerson(String doubleCheckPerson) {
        this.doubleCheckPerson = doubleCheckPerson == null ? null : doubleCheckPerson.trim();
    }

    public String getSecondIdCodeBh() {
        return secondIdCodeBh == null ? "" : secondIdCodeBh.trim();
    }

    public void setSecondIdCodeBh(String secondIdCodeBh) {
        this.secondIdCodeBh = secondIdCodeBh == null ? null : secondIdCodeBh.trim();
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getAuditStatus() {
        return auditStatus == null ? "" : auditStatus.trim();
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public String getProjectId() {
        return projectId == null ? "" : projectId.trim();
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName.trim();
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getContractCodeId() {
        return contractCodeId == null ? "" : contractCodeId.trim();
    }

    public void setContractCodeId(String contractCodeId) {
        this.contractCodeId = contractCodeId == null ? null : contractCodeId.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getWorkBookStatusP2() {
        return workBookStatusP2 == null ? "" : workBookStatusP2.trim();
    }

    public void setWorkBookStatusP2(String workBookStatusP2) {
        this.workBookStatusP2 = workBookStatusP2 == null ? null : workBookStatusP2.trim();
    }

    public BigDecimal getEquipSettleUseCount() {
        return equipSettleUseCount;
    }

    public void setEquipSettleUseCount(BigDecimal equipSettleUseCount) {
        this.equipSettleUseCount = equipSettleUseCount;
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

    public List<ZxErpFile> getZxErpFileList() {
        return zxErpFileList;
    }

    public void setZxErpFileList(List<ZxErpFile> zxErpFileList) {
        this.zxErpFileList = zxErpFileList;
    }

    public List<ZxCtOtherManageAmtRate> getZxCtOtherManageAmtRateList() {
        return zxCtOtherManageAmtRateList;
    }

    public void setZxCtOtherManageAmtRateList(List<ZxCtOtherManageAmtRate> zxCtOtherManageAmtRateList) {
        this.zxCtOtherManageAmtRateList = zxCtOtherManageAmtRateList;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }
}
