package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCtFsContract extends BasePojo {
    // 主键
    private String zxCtFsContractId;

    // 合同编号
    private String contractNo;

    // 合同名称
    private String contractName;

    // 合同类型
    private String contractType;

    // 摘要
    private String subject;

    // 合同内容
    private String content;

    // 所属项目ID
    private String orgID;

    // 甲方ID
    private String firstId;

    // 合同甲方
    private String firstName;

    // 项目经理姓名
    private String firstPrincipal;

    // 甲方代表身份证
    private String firstPrincipalIDCard;

    // 甲方联系电话
    private String firstUnitTel;

    // 乙方ID
    private String secondID;

    // 合同乙方
    private String secondName;

    // 乙方代表
    private String secondPrincipal;

    // 乙方代表身份证
    private String secondPrincipalIDCard;

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

    // 签订时间
    private Date signatureDate;

    // 含税合同总价(万元)
    private BigDecimal contractCost;

    // 合同工期(天)
    private String csTimeLimit;

    // 合同开始时间
    private Date planStartDate;

    // 实际开始时间
    private Date actualStartDate;

    // 合同结束时间
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

    // 合同状态
    private String contractStatus;

    // 项目编号
    private String projectNo;

    // 项目全称
    private String projectName;

    // 项目简称
    private String shortName;

    // 风险抵押金
    private BigDecimal ventureGuaranty;

    // 内部承包总价
    private BigDecimal innerContractSum;

    // 税率
    private BigDecimal faxRate;

    // 管理费率
    private BigDecimal manageRate;

    // 质保费率
    private BigDecimal quanlityFeeRate;

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

    // 施工单位
    private String implementUnit;

    // 设计单位
    private String designUnit;

    // 乙方(手机)
    private String pp1;

    // 乙方(传真)
    private String pp2;

    // 合同类型
    private String pp3;

    // 累计结算金额
    private String pp4;

    // 累计支付金额
    private String pp5;

    // pp9
    private String pp9;

    // 资金流向
    private String pp10;

    // name
    private String code;

    // 机构编码
    private String code1;

    // 承建单位简称
    private String code2;

    // 中标单位简称
    private String code3;

    // 项目所在省份简称
    private String code4;

    // 项目简称
    private String code5;

    // 标段号
    private String code6;

    // 合同类别
    private String code7;

    // 合同序号
    private String code8;

    // 合同评审ID
    private String fromApplyID;

    // 业主合同功能码
    private String codeP1;

    // 同一公司
    private String code2T3;

    // 变更后含税合同金额（万元）
    private BigDecimal alterContractSum;

    // 结算情况
    private String settleType;

    // 税率(%)
    private String taxRate;

    // 是否抵扣
    private String isDeduct;

    // 不含税合同总价(万元)
    private BigDecimal contractCostNoTax;

    // 合同税额
    private BigDecimal contractCostTax;

    // 变更后不含税合同金额(万元)
    private BigDecimal alterContractSumNoTax;

    // 变更后合同税额(万元)
    private BigDecimal alterContractSumTax;

    // comID
    private String comID;

    // 物资结算引用数
    private int stockSettleUseCount=0;

    // 所属项目
    private String orgName;

    // 甲方
    private String firstIdFormula;

    // 机械及其他结算引用数
    private int equipSettleUseCount=0;

    // 工程结算引用数
    private int projectSettleUseCount=0;

    // 乙方
    private String secondIDFormula;

    // 所属处部
    private String locationInDeprFormula;

    // 变更审核次数
    private int auditContrAlterCount=0;

    // 录入类型
    private String audit;

    // 附属合同评审ID
    private String contractReviewId;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    private String fromContractNo;

    private String workId;

    // 附件list
    private List<ZxErpFile> zxErpFileList;

    private List<ZxCtFsContractReviewDetail> ReviewDetailList;

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public List<ZxCtFsContractReviewDetail> getReviewDetailList() {
        return ReviewDetailList;
    }

    public void setReviewDetailList(List<ZxCtFsContractReviewDetail> reviewDetailList) {
        ReviewDetailList = reviewDetailList;
    }

    public String getFromContractNo() {
        return fromContractNo;
    }

    public void setFromContractNo(String fromContractNo) {
        this.fromContractNo = fromContractNo;
    }

    public List<ZxErpFile> getZxErpFileList() {
        return zxErpFileList;
    }

    public void setZxErpFileList(List<ZxErpFile> zxErpFileList) {
        this.zxErpFileList = zxErpFileList;
    }

    public String getZxCtFsContractId() {
        return zxCtFsContractId == null ? "" : zxCtFsContractId.trim();
    }

    public void setZxCtFsContractId(String zxCtFsContractId) {
        this.zxCtFsContractId = zxCtFsContractId == null ? null : zxCtFsContractId.trim();
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

    public String getContractType() {
        return contractType == null ? "" : contractType.trim();
    }

    public void setContractType(String contractType) {
        this.contractType = contractType == null ? null : contractType.trim();
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

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
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

    public String getFirstPrincipalIDCard() {
        return firstPrincipalIDCard == null ? "" : firstPrincipalIDCard.trim();
    }

    public void setFirstPrincipalIDCard(String firstPrincipalIDCard) {
        this.firstPrincipalIDCard = firstPrincipalIDCard == null ? null : firstPrincipalIDCard.trim();
    }

    public String getFirstUnitTel() {
        return firstUnitTel == null ? "" : firstUnitTel.trim();
    }

    public void setFirstUnitTel(String firstUnitTel) {
        this.firstUnitTel = firstUnitTel == null ? null : firstUnitTel.trim();
    }

    public String getSecondID() {
        return secondID == null ? "" : secondID.trim();
    }

    public void setSecondID(String secondID) {
        this.secondID = secondID == null ? null : secondID.trim();
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

    public String getSecondPrincipalIDCard() {
        return secondPrincipalIDCard == null ? "" : secondPrincipalIDCard.trim();
    }

    public void setSecondPrincipalIDCard(String secondPrincipalIDCard) {
        this.secondPrincipalIDCard = secondPrincipalIDCard == null ? null : secondPrincipalIDCard.trim();
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

    public String getProjectName() {
        return projectName == null ? "" : projectName.trim();
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
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

    public BigDecimal getFaxRate() {
        return faxRate;
    }

    public void setFaxRate(BigDecimal faxRate) {
        this.faxRate = faxRate;
    }

    public BigDecimal getManageRate() {
        return manageRate;
    }

    public void setManageRate(BigDecimal manageRate) {
        this.manageRate = manageRate;
    }

    public BigDecimal getQuanlityFeeRate() {
        return quanlityFeeRate;
    }

    public void setQuanlityFeeRate(BigDecimal quanlityFeeRate) {
        this.quanlityFeeRate = quanlityFeeRate;
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

    public String getImplementUnit() {
        return implementUnit == null ? "" : implementUnit.trim();
    }

    public void setImplementUnit(String implementUnit) {
        this.implementUnit = implementUnit == null ? null : implementUnit.trim();
    }

    public String getDesignUnit() {
        return designUnit == null ? "" : designUnit.trim();
    }

    public void setDesignUnit(String designUnit) {
        this.designUnit = designUnit == null ? null : designUnit.trim();
    }

    public String getPp1() {
        return pp1 == null ? "" : pp1.trim();
    }

    public void setPp1(String pp1) {
        this.pp1 = pp1 == null ? null : pp1.trim();
    }

    public String getPp2() {
        return pp2 == null ? "" : pp2.trim();
    }

    public void setPp2(String pp2) {
        this.pp2 = pp2 == null ? null : pp2.trim();
    }

    public String getPp3() {
        return pp3 == null ? "" : pp3.trim();
    }

    public void setPp3(String pp3) {
        this.pp3 = pp3 == null ? null : pp3.trim();
    }

    public String getPp4() {
        return pp4 == null ? "" : pp4.trim();
    }

    public void setPp4(String pp4) {
        this.pp4 = pp4 == null ? null : pp4.trim();
    }

    public String getPp5() {
        return pp5 == null ? "" : pp5.trim();
    }

    public void setPp5(String pp5) {
        this.pp5 = pp5 == null ? null : pp5.trim();
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

    public String getCode() {
        return code == null ? "" : code.trim();
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getCode1() {
        return code1 == null ? "" : code1.trim();
    }

    public void setCode1(String code1) {
        this.code1 = code1 == null ? null : code1.trim();
    }

    public String getCode2() {
        return code2 == null ? "" : code2.trim();
    }

    public void setCode2(String code2) {
        this.code2 = code2 == null ? null : code2.trim();
    }

    public String getCode3() {
        return code3 == null ? "" : code3.trim();
    }

    public void setCode3(String code3) {
        this.code3 = code3 == null ? null : code3.trim();
    }

    public String getCode4() {
        return code4 == null ? "" : code4.trim();
    }

    public void setCode4(String code4) {
        this.code4 = code4 == null ? null : code4.trim();
    }

    public String getCode5() {
        return code5 == null ? "" : code5.trim();
    }

    public void setCode5(String code5) {
        this.code5 = code5 == null ? null : code5.trim();
    }

    public String getCode6() {
        return code6 == null ? "" : code6.trim();
    }

    public void setCode6(String code6) {
        this.code6 = code6 == null ? null : code6.trim();
    }

    public String getCode7() {
        return code7 == null ? "" : code7.trim();
    }

    public void setCode7(String code7) {
        this.code7 = code7 == null ? null : code7.trim();
    }

    public String getCode8() {
        return code8 == null ? "" : code8.trim();
    }

    public void setCode8(String code8) {
        this.code8 = code8 == null ? null : code8.trim();
    }

    public String getFromApplyID() {
        return fromApplyID == null ? "" : fromApplyID.trim();
    }

    public void setFromApplyID(String fromApplyID) {
        this.fromApplyID = fromApplyID == null ? null : fromApplyID.trim();
    }

    public String getCodeP1() {
        return codeP1 == null ? "" : codeP1.trim();
    }

    public void setCodeP1(String codeP1) {
        this.codeP1 = codeP1 == null ? null : codeP1.trim();
    }

    public String getCode2T3() {
        return code2T3 == null ? "" : code2T3.trim();
    }

    public void setCode2T3(String code2T3) {
        this.code2T3 = code2T3 == null ? null : code2T3.trim();
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

    public String getComID() {
        return comID == null ? "" : comID.trim();
    }

    public void setComID(String comID) {
        this.comID = comID == null ? null : comID.trim();
    }

    public int getStockSettleUseCount() {
        return stockSettleUseCount;
    }

    public void setStockSettleUseCount(int stockSettleUseCount) {
        this.stockSettleUseCount = stockSettleUseCount;
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getFirstIdFormula() {
        return firstIdFormula == null ? "" : firstIdFormula.trim();
    }

    public void setFirstIdFormula(String firstIdFormula) {
        this.firstIdFormula = firstIdFormula == null ? null : firstIdFormula.trim();
    }

    public int getEquipSettleUseCount() {
        return equipSettleUseCount;
    }

    public void setEquipSettleUseCount(int equipSettleUseCount) {
        this.equipSettleUseCount = equipSettleUseCount;
    }

    public int getProjectSettleUseCount() {
        return projectSettleUseCount;
    }

    public void setProjectSettleUseCount(int projectSettleUseCount) {
        this.projectSettleUseCount = projectSettleUseCount;
    }

    public String getSecondIDFormula() {
        return secondIDFormula == null ? "" : secondIDFormula.trim();
    }

    public void setSecondIDFormula(String secondIDFormula) {
        this.secondIDFormula = secondIDFormula == null ? null : secondIDFormula.trim();
    }

    public String getLocationInDeprFormula() {
        return locationInDeprFormula == null ? "" : locationInDeprFormula.trim();
    }

    public void setLocationInDeprFormula(String locationInDeprFormula) {
        this.locationInDeprFormula = locationInDeprFormula == null ? null : locationInDeprFormula.trim();
    }

    public int getAuditContrAlterCount() {
        return auditContrAlterCount;
    }

    public void setAuditContrAlterCount(int auditContrAlterCount) {
        this.auditContrAlterCount = auditContrAlterCount;
    }

    public String getAudit() {
        return audit == null ? "" : audit.trim();
    }

    public void setAudit(String audit) {
        this.audit = audit == null ? null : audit.trim();
    }

    public String getContractReviewId() {
        return contractReviewId == null ? "" : contractReviewId.trim();
    }

    public void setContractReviewId(String contractReviewId) {
        this.contractReviewId = contractReviewId == null ? null : contractReviewId.trim();
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
