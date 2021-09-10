package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCtContract extends BasePojo {
    // 主键ID
    private String id;

    // 所属机构（项目ID）
    private String orgID;

    // 项目开工日期
    private Date actualStartDate;

    // 项目总工姓名（项目经理姓名）
    private String projectChiefName;

    // 项目总工电话（项目经理电话）
    private String projectChiefTel;

    // 项目全称
    private String projectName;

    // 项目简称
    private String shortName;

    // 项目所在地
    private String projectLocation;

    // 中标资质
    private String biddingQualification;

    // 项目所在省份简称
    private String provinceAbbreviation;

    // 标段号
    private String lotNo;

    // 实际开始日期
    private Date realBeginDate;

    // 实际结束时间
    private Date realEndDate;

    // 所属区域
    private String subArea;

    // 项目性质
    private String projectProperty;

    // 项目所在市级简称
    private String cityName;

    // 所属项目（项目名称）
    private String orgName;

    // 工程类别
    private String projType;

    // 施工单位
    private String locationInDeprFormula;

    // 所属事业部
    private String bizDep;

    // 是否参与结算指标考核
    private String isSettle;

    // 项目参建性质
    private String projQuality;

    // 合同名称
    private String contractName;

    // 甲方ID
    private String firstId;

    // 甲方名称
    private String firstName;

    // 甲方代表
    private String firstPrincipal;

    // 甲方联系电话
    private String firstUnitTel;

    // 乙方ID
    private String secondID;

    // 乙方名称
    private String secondName;

    // 乙方代表
    private String secondPrincipal;

    // 乙方联系电话
    private String secondUnitTel;

    // 甲方联系地址
    private String firstAddr;

    // 乙方联系地址
    private String secondAddr;

    // 业主合同编号
    private String contractNo;

    // 合同类型
    private String contractType;

    // 含税合同总价(万元)
    private BigDecimal contractCost;

    // 当前合同总造价（万元）
    private BigDecimal contractMoney;

    // 税率（%）
    private String taxRate;

    // 不含税合同总价（万元）
    private BigDecimal contractCostNoTax;

    // 合同税额（税金）
    private BigDecimal contractCostTax;

    // 清单金额(元)
    private BigDecimal qdMoney;

    // 中标资质方是否局资质
    private String isOfficial;

    // 签订日期
    private Date signatureDate;

    // 合同工期
    private String csTimeLimit;

    // 合同开始时间
    private Date planStartDate;

    // 合同结束时间
    private Date planEndDate;

    // 合同状态
    private String contractStatus;

    // 合同显示类型
    private String contractViewType;

    // 监理单位
    private String consultative;

    // 监理单位电话
    private String consultativeTel;

    // 设计单位
    private String designUnit;

    // 设计单位电话
    private String designPhone;

    // 设计单位位置
    private String designAddr;

    // 监理单位地址
    private String consultativeAddr;

    // 监理单位联系人
    private String consultativeUser;

    // 设计单位联系人
    private String designUser;

    // 合同种类
    private String contractSort;

    // 动员预付款(元)
    private BigDecimal dyyfkMoney;

    // 动员预付款起扣点(元)
    private BigDecimal dyyfkqkdMoney;

    // 扣回动员预付款比例(%)
    private BigDecimal khdyyfkPercent;

    // 动员预付款全额扣回起点(元)
    private BigDecimal dyyfkqekhdMoney;

    // 材料预付款比例(%)
    private BigDecimal clyfkPercent;

    // 材料预付款限额(元)
    private BigDecimal clyfkxeMoney;

    // 迟扣款利息(%)
    private BigDecimal ckklxPercent;

    // 迟扣款利息限额(元)
    private BigDecimal ckklxxeMoney;

    // 违约金限额(元)
    private BigDecimal wyjxeMoney;

    // 索赔金限额(元)
    private BigDecimal cpjexeMoney;

    // 保留金扣款比例(%)
    private BigDecimal bljblPercent;

    // 保留金起扣点(元)
    private BigDecimal bljqkdMoney;

    // 累计保留金限额(元)
    private BigDecimal ljkbljxeMoney;

    // 材料口回款比例（%）
    private BigDecimal clkhkblPercent;

    // 摘要
    private String subject;

    // 主要工程项目及工程数量
    private String projDetail;

    // 业主节点工期
    private String ownerNodeDeys;

    // 业主调差、变更定价原则等主要专用条款
    private String ownerSpecialClause;

    // 合同内容概述
    private String summaryOfContractContents;

    // 合同内容
    private String content;

    // 甲方代表身份证
    private String firstPrincipalIDCard;

    // 乙方代表身份证
    private String secondPrincipalIDCard;

    // 丙方
    private String thirdName;

    // 收付类型
    private String payType;

    // 所属处部
    private String locationInDepr;

    // 所属处部ID
    private String locationInDeprId;

    // 登记日期
    private Date registerDate;

    // 中标日期
    private Date bidDate;

    // 项目计划竣工日期
    private Date actualEndDate;

    // 工程规模
    private String projectSize;

    // 业主联系电话
    private String ownerLinkTel;

    // 项目经理姓名
    private String projectManager;

    // 项目经理电话
    private String projectManagerTel;

    // 是否有标的
    private String hasDetail;

    // 项目编号
    private String projectNo;

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

    // 代理人
    private String agent;

    // 经营指标
    private String manageIndex;

    // 施工单位
    private String implementUnit;

    // 经办人
    private String transactor;

    // 指定的
    private String nominated;

    // 转让合同
    private String assignContract;

    // 备注
    private String remarks;

    // pp1
    private String pp1;

    // pp2
    private String pp2;

    // pp3
    private String pp3;

    // pp4
    private String pp4;

    // pp5
    private String pp5;

    // pp6
    private String pp6;

    // pp8
    private String pp8;

    // pp9
    private String pp9;

    // pp10
    private String pp10;

    // 审核状态
    private String auditStatus;

    // 编辑时间
    private Date editTime;

    // 合同编码
    private String code;

    // 机构编码
    private String code1;

    // 成建单位简称
    private String contractor;

    // 项目简称
    private String code5;

    // 合同管理类别
    private String code7;

    // 合同序号
    private String code8;

    // 合同审批ID
    private String fromApplyID;

    // 业主合同功能码（原字段codeP1）
    private String ownerContractFunctionCode;

    // 同一公司
    private String code2T3;

    // 流程开始时间
    private Date flowBeginDate;

    // 流程结束时间
    private Date flowEndDate;

    // 质量保证金扣除比例（%）
    private BigDecimal qualityBailRate;

    // 安全生产保证金扣除比例（%）
    private BigDecimal prodSafeBailRate;

    // 农民工工资偿付保证金扣除比例（%）
    private BigDecimal payBailRate;

    // 是否局直属
    private String isJuProj;

    // 局直属项目名称ID
    private String juProjID;

    // 局直属项目名称
    private String juProjName;

    // 物资审查
    private String stockAudit;

    // 租金类型
    private String rentType;

    // 变更后含税合同金额（万元）
    private BigDecimal alterContractSum;

    // 结算情况
    private String settleType;

    // 材料来源
    private String materialSource;

    // 基本信息审核按钮
    private String contrStatus;

    // 是否抵扣
    private String isDeduct;

    // 变更后不含税合同总价（万元）
    private BigDecimal alterContractSumNoTax;

    // 变更后合同税额
    private BigDecimal alterContractSumTax;

    // 是否简易计税
    private String isEasyTax;

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
    private String comID;

    // notDisplay
    private String notDisplay;

    // 财务系统ID
    private String fiId;

    // 名义投标单位ID
    private String biddersId;

    // 名义投标单位编号
    private String biddersCode;

    // 名义投标单位
    private String biddersName;

    // 核算单位ID
    private String accountUnitId;

    // 核算单位编号
    private String accountUnitCode;

    // 核算单位
    private String accountUnitName;

    // 核算项目ID
    private String accountProjId;

    // 核算项目编号
    private String accountProjCode;

    // 核算项目
    private String accountProjName;

    // 项目属地
    private String projSite;

    // 业务板块
    private String busiSegments;

    // 项目资金来源
    private String projFundsSource;

    // 事业部
    private String division;

    // 税务备案号（名义投标单位/中标单位）
    private String taxFilingCode;

    // 增值税计税方法
    private String taxCountWay;

    // 增值税预征率（%）
    private String taxAdvanceRate;

    // 增值税使用税率（%）
    private String taxUseWay;

    // 是否属地预缴
    private String prepaidLand;

    // 预缴国税机关
    private String nationalTax;

    // 预缴国税机关联系方式
    private String nationalTaxTel;

    // 预缴国税机关地址
    private String nationalTaxAdds;

    // 项目部通讯地址
    private String projDepAdds;

    // 项目部邮编
    private String projDepZipCode;

    // 项目部电话
    private String projDepTel;

    // 项目部传真
    private String projDepFax;

    // 项目阶段
    private String projStage;

    // 项目经理固话
    private String pmFixedLine;

    // 项目经理邮箱
    private String pmMail;

    // 财务负责人
    private String fiCharge;

    // 财务负责人手机
    private String fiTel;

    // 财务负责人固话
    private String fiFixedLine;

    // 财务负责人邮箱
    private String fiMail;

    // 合约负责人
    private String ctrCharge;

    // 合约负责人手机
    private String ctrTel;

    // 合约负责人固话
    private String ctrFixedLine;

    // 合约负责人邮箱
    private String ctrMail;

    // 债权清收负责人
    private String dcLeader;

    // 债权清收负责人手机
    private String dcTel;

    // 债权清收负责人固话
    private String dcFixedLine;

    // 债权清收负责人邮箱
    private String dcMail;

    // 核算部门id财务
    private String accountDepId;

    // 核算部门编号
    private String accountDepCode;

    // 核算部门
    private String accountDepName;

    // 往来单位编号
    private String secondIDCode;

    // 录入人
    private String writer;

    // 录入日期
    private Date writeDate;

    // 财务合同状态
    private String fiCtrState;

    // 系统编号
    private String systemNo;

    // 币种编号
    private String currencyCode;

    // 合同性质
    private String ctrNature;

    // 复核人
    private String doubleCheckPerson;

    // 复核日期
    private Date doubleCheckDate;

    // 收支方向
    private String revenueDir;

    // 发票类型
    private String invoiceType;

    // 制式合同
    private String staCtr;

    // 重点合同
    private String keyCtr;

    // 合同更新状态
    private String ctrUpdateState;

    // 竣工决算日期
    private Date finalDate;

    // 投标状态
    private String bidStatus;

    // 质保金到期日期
    private Date premiumExpDate;

    // 垫付款到期日期
    private Date cushionExpDate;

    // 预付款比例(%)
    private BigDecimal advanceRate;

    // 进度款比例(%)
    private BigDecimal progressRate;

    // 竣工款比例(%)
    private BigDecimal completionRate;

    // 往来单位财务编号
    private String firstIdBh;

    // 往来单位主库编号
    private String firstIdSh;

    // 往来单位编号
    private String secondIDCodeBh;

    // 结算资产额
    private BigDecimal settleTotalAmt;

    // 财务审核状态
    private String cwStatus;

    // 财务合同ID
    private String zjxmhtbNm;

    // 财务合同编号
    private String zjxmhtbBh;

    // 合同清单合同数量确认状态
    private String worksAuditFlag;

    // 合同清单核审数量确认状
    private String worksVerificationFlag;

    // 所属公司ID
    private String companyId;

    // 甲方
    private String firstIdFormula;

    // 统一社会信用代码
    private String orgCertificate;

    // 国家
    private String country;

    // 机械及其他结算引用
    private int equipSettleUseCount=0;

    // 资产公司机械及其他结算引用
    private int zcgsEquipSettleUseCount=0;

    // 工程结算引用数
    private int projectSettleUseCount=0;

    // 乙方
    private String secondIDFormula;

    // 地区
    private String area;

    // 清单是否审核
    private String workBookStatusP1;

    // 清单是否审核
    private String workBookStatusP2;

    // 变更审核次数
    private int auditContrAlterCount=0;

    // 编码引用
    private int codeUsing=0;

    // 从合同ID
    private String fromContractID;

    // 投资合同编号
    private String investContractNo;

    // 资源类别ID
    private String resCategoryID;

    // 资源类别
    private String resCategoryName;

    // 排序
    private int sort=0;

    // 附件
    private List<ZxErpFile> attachment;

    // 保证金列表
    private List<ZxCtContrDetailAttr> bondList;

    // 所属公司名称
    private String companyName;

    // 项目特征
    private String projFea;

    // 期次检索
    private String season;

    // 合计产值金额
    private BigDecimal totalProduceAmt;

    // 主体完工日期
    private Date mainEndDate;

    // 归档日期
    private Date docDate;

    // 项目交工日期
    private Date deliveryDate;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public Date getActualStartDate() {
        return actualStartDate;
    }

    public void setActualStartDate(Date actualStartDate) {
        this.actualStartDate = actualStartDate;
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

    public String getProjectLocation() {
        return projectLocation == null ? "" : projectLocation.trim();
    }

    public void setProjectLocation(String projectLocation) {
        this.projectLocation = projectLocation == null ? null : projectLocation.trim();
    }

    public String getBiddingQualification() {
        return biddingQualification == null ? "" : biddingQualification.trim();
    }

    public void setBiddingQualification(String biddingQualification) {
        this.biddingQualification = biddingQualification == null ? null : biddingQualification.trim();
    }

    public String getProvinceAbbreviation() {
        return provinceAbbreviation == null ? "" : provinceAbbreviation.trim();
    }

    public void setProvinceAbbreviation(String provinceAbbreviation) {
        this.provinceAbbreviation = provinceAbbreviation == null ? null : provinceAbbreviation.trim();
    }

    public String getLotNo() {
        return lotNo == null ? "" : lotNo.trim();
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo == null ? null : lotNo.trim();
    }

    public Date getRealBeginDate() {
        return realBeginDate;
    }

    public void setRealBeginDate(Date realBeginDate) {
        this.realBeginDate = realBeginDate;
    }

    public Date getRealEndDate() {
        return realEndDate;
    }

    public void setRealEndDate(Date realEndDate) {
        this.realEndDate = realEndDate;
    }

    public String getSubArea() {
        return subArea == null ? "" : subArea.trim();
    }

    public void setSubArea(String subArea) {
        this.subArea = subArea == null ? null : subArea.trim();
    }

    public String getProjectProperty() {
        return projectProperty == null ? "" : projectProperty.trim();
    }

    public void setProjectProperty(String projectProperty) {
        this.projectProperty = projectProperty == null ? null : projectProperty.trim();
    }

    public String getCityName() {
        return cityName == null ? "" : cityName.trim();
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getProjType() {
        return projType == null ? "" : projType.trim();
    }

    public void setProjType(String projType) {
        this.projType = projType == null ? null : projType.trim();
    }

    public String getLocationInDeprFormula() {
        return locationInDeprFormula == null ? "" : locationInDeprFormula.trim();
    }

    public void setLocationInDeprFormula(String locationInDeprFormula) {
        this.locationInDeprFormula = locationInDeprFormula == null ? null : locationInDeprFormula.trim();
    }

    public String getBizDep() {
        return bizDep == null ? "" : bizDep.trim();
    }

    public void setBizDep(String bizDep) {
        this.bizDep = bizDep == null ? null : bizDep.trim();
    }

    public String getIsSettle() {
        return isSettle == null ? "" : isSettle.trim();
    }

    public void setIsSettle(String isSettle) {
        this.isSettle = isSettle == null ? null : isSettle.trim();
    }

    public String getProjQuality() {
        return projQuality == null ? "" : projQuality.trim();
    }

    public void setProjQuality(String projQuality) {
        this.projQuality = projQuality == null ? null : projQuality.trim();
    }

    public String getContractName() {
        return contractName == null ? "" : contractName.trim();
    }

    public void setContractName(String contractName) {
        this.contractName = contractName == null ? null : contractName.trim();
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

    public String getSecondUnitTel() {
        return secondUnitTel == null ? "" : secondUnitTel.trim();
    }

    public void setSecondUnitTel(String secondUnitTel) {
        this.secondUnitTel = secondUnitTel == null ? null : secondUnitTel.trim();
    }

    public String getFirstAddr() {
        return firstAddr == null ? "" : firstAddr.trim();
    }

    public void setFirstAddr(String firstAddr) {
        this.firstAddr = firstAddr == null ? null : firstAddr.trim();
    }

    public String getSecondAddr() {
        return secondAddr == null ? "" : secondAddr.trim();
    }

    public void setSecondAddr(String secondAddr) {
        this.secondAddr = secondAddr == null ? null : secondAddr.trim();
    }

    public String getContractNo() {
        return contractNo == null ? "" : contractNo.trim();
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public String getContractType() {
        return contractType == null ? "" : contractType.trim();
    }

    public void setContractType(String contractType) {
        this.contractType = contractType == null ? null : contractType.trim();
    }

    public BigDecimal getContractCost() {
        return contractCost;
    }

    public void setContractCost(BigDecimal contractCost) {
        this.contractCost = contractCost;
    }

    public BigDecimal getContractMoney() {
        return contractMoney;
    }

    public void setContractMoney(BigDecimal contractMoney) {
        this.contractMoney = contractMoney;
    }

    public String getTaxRate() {
        return taxRate == null ? "" : taxRate.trim();
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate == null ? null : taxRate.trim();
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

    public BigDecimal getQdMoney() {
        return qdMoney;
    }

    public void setQdMoney(BigDecimal qdMoney) {
        this.qdMoney = qdMoney;
    }

    public String getIsOfficial() {
        return isOfficial == null ? "" : isOfficial.trim();
    }

    public void setIsOfficial(String isOfficial) {
        this.isOfficial = isOfficial == null ? null : isOfficial.trim();
    }

    public Date getSignatureDate() {
        return signatureDate;
    }

    public void setSignatureDate(Date signatureDate) {
        this.signatureDate = signatureDate;
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

    public Date getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(Date planEndDate) {
        this.planEndDate = planEndDate;
    }

    public String getContractStatus() {
        return contractStatus == null ? "" : contractStatus.trim();
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus == null ? null : contractStatus.trim();
    }

    public String getContractViewType() {
        return contractViewType == null ? "" : contractViewType.trim();
    }

    public void setContractViewType(String contractViewType) {
        this.contractViewType = contractViewType == null ? null : contractViewType.trim();
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

    public String getDesignUnit() {
        return designUnit == null ? "" : designUnit.trim();
    }

    public void setDesignUnit(String designUnit) {
        this.designUnit = designUnit == null ? null : designUnit.trim();
    }

    public String getDesignPhone() {
        return designPhone == null ? "" : designPhone.trim();
    }

    public void setDesignPhone(String designPhone) {
        this.designPhone = designPhone == null ? null : designPhone.trim();
    }

    public String getDesignAddr() {
        return designAddr == null ? "" : designAddr.trim();
    }

    public void setDesignAddr(String designAddr) {
        this.designAddr = designAddr == null ? null : designAddr.trim();
    }

    public String getConsultativeAddr() {
        return consultativeAddr == null ? "" : consultativeAddr.trim();
    }

    public void setConsultativeAddr(String consultativeAddr) {
        this.consultativeAddr = consultativeAddr == null ? null : consultativeAddr.trim();
    }

    public String getConsultativeUser() {
        return consultativeUser == null ? "" : consultativeUser.trim();
    }

    public void setConsultativeUser(String consultativeUser) {
        this.consultativeUser = consultativeUser == null ? null : consultativeUser.trim();
    }

    public String getDesignUser() {
        return designUser == null ? "" : designUser.trim();
    }

    public void setDesignUser(String designUser) {
        this.designUser = designUser == null ? null : designUser.trim();
    }

    public String getContractSort() {
        return contractSort == null ? "" : contractSort.trim();
    }

    public void setContractSort(String contractSort) {
        this.contractSort = contractSort == null ? null : contractSort.trim();
    }

    public BigDecimal getDyyfkMoney() {
        return dyyfkMoney;
    }

    public void setDyyfkMoney(BigDecimal dyyfkMoney) {
        this.dyyfkMoney = dyyfkMoney;
    }

    public BigDecimal getDyyfkqkdMoney() {
        return dyyfkqkdMoney;
    }

    public void setDyyfkqkdMoney(BigDecimal dyyfkqkdMoney) {
        this.dyyfkqkdMoney = dyyfkqkdMoney;
    }

    public BigDecimal getKhdyyfkPercent() {
        return khdyyfkPercent;
    }

    public void setKhdyyfkPercent(BigDecimal khdyyfkPercent) {
        this.khdyyfkPercent = khdyyfkPercent;
    }

    public BigDecimal getDyyfkqekhdMoney() {
        return dyyfkqekhdMoney;
    }

    public void setDyyfkqekhdMoney(BigDecimal dyyfkqekhdMoney) {
        this.dyyfkqekhdMoney = dyyfkqekhdMoney;
    }

    public BigDecimal getClyfkPercent() {
        return clyfkPercent;
    }

    public void setClyfkPercent(BigDecimal clyfkPercent) {
        this.clyfkPercent = clyfkPercent;
    }

    public BigDecimal getClyfkxeMoney() {
        return clyfkxeMoney;
    }

    public void setClyfkxeMoney(BigDecimal clyfkxeMoney) {
        this.clyfkxeMoney = clyfkxeMoney;
    }

    public BigDecimal getCkklxPercent() {
        return ckklxPercent;
    }

    public void setCkklxPercent(BigDecimal ckklxPercent) {
        this.ckklxPercent = ckklxPercent;
    }

    public BigDecimal getCkklxxeMoney() {
        return ckklxxeMoney;
    }

    public void setCkklxxeMoney(BigDecimal ckklxxeMoney) {
        this.ckklxxeMoney = ckklxxeMoney;
    }

    public BigDecimal getWyjxeMoney() {
        return wyjxeMoney;
    }

    public void setWyjxeMoney(BigDecimal wyjxeMoney) {
        this.wyjxeMoney = wyjxeMoney;
    }

    public BigDecimal getCpjexeMoney() {
        return cpjexeMoney;
    }

    public void setCpjexeMoney(BigDecimal cpjexeMoney) {
        this.cpjexeMoney = cpjexeMoney;
    }

    public BigDecimal getBljblPercent() {
        return bljblPercent;
    }

    public void setBljblPercent(BigDecimal bljblPercent) {
        this.bljblPercent = bljblPercent;
    }

    public BigDecimal getBljqkdMoney() {
        return bljqkdMoney;
    }

    public void setBljqkdMoney(BigDecimal bljqkdMoney) {
        this.bljqkdMoney = bljqkdMoney;
    }

    public BigDecimal getLjkbljxeMoney() {
        return ljkbljxeMoney;
    }

    public void setLjkbljxeMoney(BigDecimal ljkbljxeMoney) {
        this.ljkbljxeMoney = ljkbljxeMoney;
    }

    public BigDecimal getClkhkblPercent() {
        return clkhkblPercent;
    }

    public void setClkhkblPercent(BigDecimal clkhkblPercent) {
        this.clkhkblPercent = clkhkblPercent;
    }

    public String getSubject() {
        return subject == null ? "" : subject.trim();
    }

    public void setSubject(String subject) {
        this.subject = subject == null ? null : subject.trim();
    }

    public String getProjDetail() {
        return projDetail == null ? "" : projDetail.trim();
    }

    public void setProjDetail(String projDetail) {
        this.projDetail = projDetail == null ? null : projDetail.trim();
    }

    public String getOwnerNodeDeys() {
        return ownerNodeDeys == null ? "" : ownerNodeDeys.trim();
    }

    public void setOwnerNodeDeys(String ownerNodeDeys) {
        this.ownerNodeDeys = ownerNodeDeys == null ? null : ownerNodeDeys.trim();
    }

    public String getOwnerSpecialClause() {
        return ownerSpecialClause == null ? "" : ownerSpecialClause.trim();
    }

    public void setOwnerSpecialClause(String ownerSpecialClause) {
        this.ownerSpecialClause = ownerSpecialClause == null ? null : ownerSpecialClause.trim();
    }

    public String getSummaryOfContractContents() {
        return summaryOfContractContents == null ? "" : summaryOfContractContents.trim();
    }

    public void setSummaryOfContractContents(String summaryOfContractContents) {
        this.summaryOfContractContents = summaryOfContractContents == null ? null : summaryOfContractContents.trim();
    }

    public String getContent() {
        return content == null ? "" : content.trim();
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getFirstPrincipalIDCard() {
        return firstPrincipalIDCard == null ? "" : firstPrincipalIDCard.trim();
    }

    public void setFirstPrincipalIDCard(String firstPrincipalIDCard) {
        this.firstPrincipalIDCard = firstPrincipalIDCard == null ? null : firstPrincipalIDCard.trim();
    }

    public String getSecondPrincipalIDCard() {
        return secondPrincipalIDCard == null ? "" : secondPrincipalIDCard.trim();
    }

    public void setSecondPrincipalIDCard(String secondPrincipalIDCard) {
        this.secondPrincipalIDCard = secondPrincipalIDCard == null ? null : secondPrincipalIDCard.trim();
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

    public String getLocationInDepr() {
        return locationInDepr == null ? "" : locationInDepr.trim();
    }

    public void setLocationInDepr(String locationInDepr) {
        this.locationInDepr = locationInDepr == null ? null : locationInDepr.trim();
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

    public Date getActualEndDate() {
        return actualEndDate;
    }

    public void setActualEndDate(Date actualEndDate) {
        this.actualEndDate = actualEndDate;
    }

    public String getProjectSize() {
        return projectSize == null ? "" : projectSize.trim();
    }

    public void setProjectSize(String projectSize) {
        this.projectSize = projectSize == null ? null : projectSize.trim();
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

    public String getHasDetail() {
        return hasDetail == null ? "" : hasDetail.trim();
    }

    public void setHasDetail(String hasDetail) {
        this.hasDetail = hasDetail == null ? null : hasDetail.trim();
    }

    public String getProjectNo() {
        return projectNo == null ? "" : projectNo.trim();
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo == null ? null : projectNo.trim();
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

    public String getTransactor() {
        return transactor == null ? "" : transactor.trim();
    }

    public void setTransactor(String transactor) {
        this.transactor = transactor == null ? null : transactor.trim();
    }

    public String getNominated() {
        return nominated == null ? "" : nominated.trim();
    }

    public void setNominated(String nominated) {
        this.nominated = nominated == null ? null : nominated.trim();
    }

    public String getAssignContract() {
        return assignContract == null ? "" : assignContract.trim();
    }

    public void setAssignContract(String assignContract) {
        this.assignContract = assignContract == null ? null : assignContract.trim();
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
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

    public String getPp6() {
        return pp6 == null ? "" : pp6.trim();
    }

    public void setPp6(String pp6) {
        this.pp6 = pp6 == null ? null : pp6.trim();
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

    public String getAuditStatus() {
        return auditStatus == null ? "" : auditStatus.trim();
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
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

    public String getContractor() {
        return contractor == null ? "" : contractor.trim();
    }

    public void setContractor(String contractor) {
        this.contractor = contractor == null ? null : contractor.trim();
    }

    public String getCode5() {
        return code5 == null ? "" : code5.trim();
    }

    public void setCode5(String code5) {
        this.code5 = code5 == null ? null : code5.trim();
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

    public String getOwnerContractFunctionCode() {
        return ownerContractFunctionCode == null ? "" : ownerContractFunctionCode.trim();
    }

    public void setOwnerContractFunctionCode(String ownerContractFunctionCode) {
        this.ownerContractFunctionCode = ownerContractFunctionCode == null ? null : ownerContractFunctionCode.trim();
    }

    public String getCode2T3() {
        return code2T3 == null ? "" : code2T3.trim();
    }

    public void setCode2T3(String code2T3) {
        this.code2T3 = code2T3 == null ? null : code2T3.trim();
    }

    public Date getFlowBeginDate() {
        return flowBeginDate;
    }

    public void setFlowBeginDate(Date flowBeginDate) {
        this.flowBeginDate = flowBeginDate;
    }

    public Date getFlowEndDate() {
        return flowEndDate;
    }

    public void setFlowEndDate(Date flowEndDate) {
        this.flowEndDate = flowEndDate;
    }

    public BigDecimal getQualityBailRate() {
        return qualityBailRate;
    }

    public void setQualityBailRate(BigDecimal qualityBailRate) {
        this.qualityBailRate = qualityBailRate;
    }

    public BigDecimal getProdSafeBailRate() {
        return prodSafeBailRate;
    }

    public void setProdSafeBailRate(BigDecimal prodSafeBailRate) {
        this.prodSafeBailRate = prodSafeBailRate;
    }

    public BigDecimal getPayBailRate() {
        return payBailRate;
    }

    public void setPayBailRate(BigDecimal payBailRate) {
        this.payBailRate = payBailRate;
    }

    public String getIsJuProj() {
        return isJuProj == null ? "" : isJuProj.trim();
    }

    public void setIsJuProj(String isJuProj) {
        this.isJuProj = isJuProj == null ? null : isJuProj.trim();
    }

    public String getJuProjID() {
        return juProjID == null ? "" : juProjID.trim();
    }

    public void setJuProjID(String juProjID) {
        this.juProjID = juProjID == null ? null : juProjID.trim();
    }

    public String getJuProjName() {
        return juProjName == null ? "" : juProjName.trim();
    }

    public void setJuProjName(String juProjName) {
        this.juProjName = juProjName == null ? null : juProjName.trim();
    }

    public String getStockAudit() {
        return stockAudit == null ? "" : stockAudit.trim();
    }

    public void setStockAudit(String stockAudit) {
        this.stockAudit = stockAudit == null ? null : stockAudit.trim();
    }

    public String getRentType() {
        return rentType == null ? "" : rentType.trim();
    }

    public void setRentType(String rentType) {
        this.rentType = rentType == null ? null : rentType.trim();
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

    public String getMaterialSource() {
        return materialSource == null ? "" : materialSource.trim();
    }

    public void setMaterialSource(String materialSource) {
        this.materialSource = materialSource == null ? null : materialSource.trim();
    }

    public String getContrStatus() {
        return contrStatus == null ? "" : contrStatus.trim();
    }

    public void setContrStatus(String contrStatus) {
        this.contrStatus = contrStatus == null ? null : contrStatus.trim();
    }

    public String getIsDeduct() {
        return isDeduct == null ? "" : isDeduct.trim();
    }

    public void setIsDeduct(String isDeduct) {
        this.isDeduct = isDeduct == null ? null : isDeduct.trim();
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

    public String getIsEasyTax() {
        return isEasyTax == null ? "" : isEasyTax.trim();
    }

    public void setIsEasyTax(String isEasyTax) {
        this.isEasyTax = isEasyTax == null ? null : isEasyTax.trim();
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

    public String getComID() {
        return comID == null ? "" : comID.trim();
    }

    public void setComID(String comID) {
        this.comID = comID == null ? null : comID.trim();
    }

    public String getNotDisplay() {
        return notDisplay == null ? "" : notDisplay.trim();
    }

    public void setNotDisplay(String notDisplay) {
        this.notDisplay = notDisplay == null ? null : notDisplay.trim();
    }

    public String getFiId() {
        return fiId == null ? "" : fiId.trim();
    }

    public void setFiId(String fiId) {
        this.fiId = fiId == null ? null : fiId.trim();
    }

    public String getBiddersId() {
        return biddersId == null ? "" : biddersId.trim();
    }

    public void setBiddersId(String biddersId) {
        this.biddersId = biddersId == null ? null : biddersId.trim();
    }

    public String getBiddersCode() {
        return biddersCode == null ? "" : biddersCode.trim();
    }

    public void setBiddersCode(String biddersCode) {
        this.biddersCode = biddersCode == null ? null : biddersCode.trim();
    }

    public String getBiddersName() {
        return biddersName == null ? "" : biddersName.trim();
    }

    public void setBiddersName(String biddersName) {
        this.biddersName = biddersName == null ? null : biddersName.trim();
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

    public String getAccountProjId() {
        return accountProjId == null ? "" : accountProjId.trim();
    }

    public void setAccountProjId(String accountProjId) {
        this.accountProjId = accountProjId == null ? null : accountProjId.trim();
    }

    public String getAccountProjCode() {
        return accountProjCode == null ? "" : accountProjCode.trim();
    }

    public void setAccountProjCode(String accountProjCode) {
        this.accountProjCode = accountProjCode == null ? null : accountProjCode.trim();
    }

    public String getAccountProjName() {
        return accountProjName == null ? "" : accountProjName.trim();
    }

    public void setAccountProjName(String accountProjName) {
        this.accountProjName = accountProjName == null ? null : accountProjName.trim();
    }

    public String getProjSite() {
        return projSite == null ? "" : projSite.trim();
    }

    public void setProjSite(String projSite) {
        this.projSite = projSite == null ? null : projSite.trim();
    }

    public String getBusiSegments() {
        return busiSegments == null ? "" : busiSegments.trim();
    }

    public void setBusiSegments(String busiSegments) {
        this.busiSegments = busiSegments == null ? null : busiSegments.trim();
    }

    public String getProjFundsSource() {
        return projFundsSource == null ? "" : projFundsSource.trim();
    }

    public void setProjFundsSource(String projFundsSource) {
        this.projFundsSource = projFundsSource == null ? null : projFundsSource.trim();
    }

    public String getDivision() {
        return division == null ? "" : division.trim();
    }

    public void setDivision(String division) {
        this.division = division == null ? null : division.trim();
    }

    public String getTaxFilingCode() {
        return taxFilingCode == null ? "" : taxFilingCode.trim();
    }

    public void setTaxFilingCode(String taxFilingCode) {
        this.taxFilingCode = taxFilingCode == null ? null : taxFilingCode.trim();
    }

    public String getTaxCountWay() {
        return taxCountWay == null ? "" : taxCountWay.trim();
    }

    public void setTaxCountWay(String taxCountWay) {
        this.taxCountWay = taxCountWay == null ? null : taxCountWay.trim();
    }

    public String getTaxAdvanceRate() {
        return taxAdvanceRate == null ? "" : taxAdvanceRate.trim();
    }

    public void setTaxAdvanceRate(String taxAdvanceRate) {
        this.taxAdvanceRate = taxAdvanceRate == null ? null : taxAdvanceRate.trim();
    }

    public String getTaxUseWay() {
        return taxUseWay == null ? "" : taxUseWay.trim();
    }

    public void setTaxUseWay(String taxUseWay) {
        this.taxUseWay = taxUseWay == null ? null : taxUseWay.trim();
    }

    public String getPrepaidLand() {
        return prepaidLand == null ? "" : prepaidLand.trim();
    }

    public void setPrepaidLand(String prepaidLand) {
        this.prepaidLand = prepaidLand == null ? null : prepaidLand.trim();
    }

    public String getNationalTax() {
        return nationalTax == null ? "" : nationalTax.trim();
    }

    public void setNationalTax(String nationalTax) {
        this.nationalTax = nationalTax == null ? null : nationalTax.trim();
    }

    public String getNationalTaxTel() {
        return nationalTaxTel == null ? "" : nationalTaxTel.trim();
    }

    public void setNationalTaxTel(String nationalTaxTel) {
        this.nationalTaxTel = nationalTaxTel == null ? null : nationalTaxTel.trim();
    }

    public String getNationalTaxAdds() {
        return nationalTaxAdds == null ? "" : nationalTaxAdds.trim();
    }

    public void setNationalTaxAdds(String nationalTaxAdds) {
        this.nationalTaxAdds = nationalTaxAdds == null ? null : nationalTaxAdds.trim();
    }

    public String getProjDepAdds() {
        return projDepAdds == null ? "" : projDepAdds.trim();
    }

    public void setProjDepAdds(String projDepAdds) {
        this.projDepAdds = projDepAdds == null ? null : projDepAdds.trim();
    }

    public String getProjDepZipCode() {
        return projDepZipCode == null ? "" : projDepZipCode.trim();
    }

    public void setProjDepZipCode(String projDepZipCode) {
        this.projDepZipCode = projDepZipCode == null ? null : projDepZipCode.trim();
    }

    public String getProjDepTel() {
        return projDepTel == null ? "" : projDepTel.trim();
    }

    public void setProjDepTel(String projDepTel) {
        this.projDepTel = projDepTel == null ? null : projDepTel.trim();
    }

    public String getProjDepFax() {
        return projDepFax == null ? "" : projDepFax.trim();
    }

    public void setProjDepFax(String projDepFax) {
        this.projDepFax = projDepFax == null ? null : projDepFax.trim();
    }

    public String getProjStage() {
        return projStage == null ? "" : projStage.trim();
    }

    public void setProjStage(String projStage) {
        this.projStage = projStage == null ? null : projStage.trim();
    }

    public String getPmFixedLine() {
        return pmFixedLine == null ? "" : pmFixedLine.trim();
    }

    public void setPmFixedLine(String pmFixedLine) {
        this.pmFixedLine = pmFixedLine == null ? null : pmFixedLine.trim();
    }

    public String getPmMail() {
        return pmMail == null ? "" : pmMail.trim();
    }

    public void setPmMail(String pmMail) {
        this.pmMail = pmMail == null ? null : pmMail.trim();
    }

    public String getFiCharge() {
        return fiCharge == null ? "" : fiCharge.trim();
    }

    public void setFiCharge(String fiCharge) {
        this.fiCharge = fiCharge == null ? null : fiCharge.trim();
    }

    public String getFiTel() {
        return fiTel == null ? "" : fiTel.trim();
    }

    public void setFiTel(String fiTel) {
        this.fiTel = fiTel == null ? null : fiTel.trim();
    }

    public String getFiFixedLine() {
        return fiFixedLine == null ? "" : fiFixedLine.trim();
    }

    public void setFiFixedLine(String fiFixedLine) {
        this.fiFixedLine = fiFixedLine == null ? null : fiFixedLine.trim();
    }

    public String getFiMail() {
        return fiMail == null ? "" : fiMail.trim();
    }

    public void setFiMail(String fiMail) {
        this.fiMail = fiMail == null ? null : fiMail.trim();
    }

    public String getCtrCharge() {
        return ctrCharge == null ? "" : ctrCharge.trim();
    }

    public void setCtrCharge(String ctrCharge) {
        this.ctrCharge = ctrCharge == null ? null : ctrCharge.trim();
    }

    public String getCtrTel() {
        return ctrTel == null ? "" : ctrTel.trim();
    }

    public void setCtrTel(String ctrTel) {
        this.ctrTel = ctrTel == null ? null : ctrTel.trim();
    }

    public String getCtrFixedLine() {
        return ctrFixedLine == null ? "" : ctrFixedLine.trim();
    }

    public void setCtrFixedLine(String ctrFixedLine) {
        this.ctrFixedLine = ctrFixedLine == null ? null : ctrFixedLine.trim();
    }

    public String getCtrMail() {
        return ctrMail == null ? "" : ctrMail.trim();
    }

    public void setCtrMail(String ctrMail) {
        this.ctrMail = ctrMail == null ? null : ctrMail.trim();
    }

    public String getDcLeader() {
        return dcLeader == null ? "" : dcLeader.trim();
    }

    public void setDcLeader(String dcLeader) {
        this.dcLeader = dcLeader == null ? null : dcLeader.trim();
    }

    public String getDcTel() {
        return dcTel == null ? "" : dcTel.trim();
    }

    public void setDcTel(String dcTel) {
        this.dcTel = dcTel == null ? null : dcTel.trim();
    }

    public String getDcFixedLine() {
        return dcFixedLine == null ? "" : dcFixedLine.trim();
    }

    public void setDcFixedLine(String dcFixedLine) {
        this.dcFixedLine = dcFixedLine == null ? null : dcFixedLine.trim();
    }

    public String getDcMail() {
        return dcMail == null ? "" : dcMail.trim();
    }

    public void setDcMail(String dcMail) {
        this.dcMail = dcMail == null ? null : dcMail.trim();
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

    public String getSecondIDCode() {
        return secondIDCode == null ? "" : secondIDCode.trim();
    }

    public void setSecondIDCode(String secondIDCode) {
        this.secondIDCode = secondIDCode == null ? null : secondIDCode.trim();
    }

    public String getWriter() {
        return writer == null ? "" : writer.trim();
    }

    public void setWriter(String writer) {
        this.writer = writer == null ? null : writer.trim();
    }

    public Date getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(Date writeDate) {
        this.writeDate = writeDate;
    }

    public String getFiCtrState() {
        return fiCtrState == null ? "" : fiCtrState.trim();
    }

    public void setFiCtrState(String fiCtrState) {
        this.fiCtrState = fiCtrState == null ? null : fiCtrState.trim();
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

    public String getDoubleCheckPerson() {
        return doubleCheckPerson == null ? "" : doubleCheckPerson.trim();
    }

    public void setDoubleCheckPerson(String doubleCheckPerson) {
        this.doubleCheckPerson = doubleCheckPerson == null ? null : doubleCheckPerson.trim();
    }

    public Date getDoubleCheckDate() {
        return doubleCheckDate;
    }

    public void setDoubleCheckDate(Date doubleCheckDate) {
        this.doubleCheckDate = doubleCheckDate;
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

    public String getCtrUpdateState() {
        return ctrUpdateState == null ? "" : ctrUpdateState.trim();
    }

    public void setCtrUpdateState(String ctrUpdateState) {
        this.ctrUpdateState = ctrUpdateState == null ? null : ctrUpdateState.trim();
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    public String getBidStatus() {
        return bidStatus == null ? "" : bidStatus.trim();
    }

    public void setBidStatus(String bidStatus) {
        this.bidStatus = bidStatus == null ? null : bidStatus.trim();
    }

    public Date getPremiumExpDate() {
        return premiumExpDate;
    }

    public void setPremiumExpDate(Date premiumExpDate) {
        this.premiumExpDate = premiumExpDate;
    }

    public Date getCushionExpDate() {
        return cushionExpDate;
    }

    public void setCushionExpDate(Date cushionExpDate) {
        this.cushionExpDate = cushionExpDate;
    }

    public BigDecimal getAdvanceRate() {
        return advanceRate;
    }

    public void setAdvanceRate(BigDecimal advanceRate) {
        this.advanceRate = advanceRate;
    }

    public BigDecimal getProgressRate() {
        return progressRate;
    }

    public void setProgressRate(BigDecimal progressRate) {
        this.progressRate = progressRate;
    }

    public BigDecimal getCompletionRate() {
        return completionRate;
    }

    public void setCompletionRate(BigDecimal completionRate) {
        this.completionRate = completionRate;
    }

    public String getFirstIdBh() {
        return firstIdBh == null ? "" : firstIdBh.trim();
    }

    public void setFirstIdBh(String firstIdBh) {
        this.firstIdBh = firstIdBh == null ? null : firstIdBh.trim();
    }

    public String getFirstIdSh() {
        return firstIdSh == null ? "" : firstIdSh.trim();
    }

    public void setFirstIdSh(String firstIdSh) {
        this.firstIdSh = firstIdSh == null ? null : firstIdSh.trim();
    }

    public String getSecondIDCodeBh() {
        return secondIDCodeBh == null ? "" : secondIDCodeBh.trim();
    }

    public void setSecondIDCodeBh(String secondIDCodeBh) {
        this.secondIDCodeBh = secondIDCodeBh == null ? null : secondIDCodeBh.trim();
    }

    public BigDecimal getSettleTotalAmt() {
        return settleTotalAmt;
    }

    public void setSettleTotalAmt(BigDecimal settleTotalAmt) {
        this.settleTotalAmt = settleTotalAmt;
    }

    public String getCwStatus() {
        return cwStatus == null ? "" : cwStatus.trim();
    }

    public void setCwStatus(String cwStatus) {
        this.cwStatus = cwStatus == null ? null : cwStatus.trim();
    }

    public String getZjxmhtbNm() {
        return zjxmhtbNm == null ? "" : zjxmhtbNm.trim();
    }

    public void setZjxmhtbNm(String zjxmhtbNm) {
        this.zjxmhtbNm = zjxmhtbNm == null ? null : zjxmhtbNm.trim();
    }

    public String getZjxmhtbBh() {
        return zjxmhtbBh == null ? "" : zjxmhtbBh.trim();
    }

    public void setZjxmhtbBh(String zjxmhtbBh) {
        this.zjxmhtbBh = zjxmhtbBh == null ? null : zjxmhtbBh.trim();
    }

    public String getWorksAuditFlag() {
        return worksAuditFlag == null ? "" : worksAuditFlag.trim();
    }

    public void setWorksAuditFlag(String worksAuditFlag) {
        this.worksAuditFlag = worksAuditFlag == null ? null : worksAuditFlag.trim();
    }

    public String getWorksVerificationFlag() {
        return worksVerificationFlag == null ? "" : worksVerificationFlag.trim();
    }

    public void setWorksVerificationFlag(String worksVerificationFlag) {
        this.worksVerificationFlag = worksVerificationFlag == null ? null : worksVerificationFlag.trim();
    }

    public String getCompanyId() {
        return companyId == null ? "" : companyId.trim();
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getFirstIdFormula() {
        return firstIdFormula == null ? "" : firstIdFormula.trim();
    }

    public void setFirstIdFormula(String firstIdFormula) {
        this.firstIdFormula = firstIdFormula == null ? null : firstIdFormula.trim();
    }

    public String getOrgCertificate() {
        return orgCertificate == null ? "" : orgCertificate.trim();
    }

    public void setOrgCertificate(String orgCertificate) {
        this.orgCertificate = orgCertificate == null ? null : orgCertificate.trim();
    }

    public String getCountry() {
        return country == null ? "" : country.trim();
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public int getEquipSettleUseCount() {
        return equipSettleUseCount;
    }

    public void setEquipSettleUseCount(int equipSettleUseCount) {
        this.equipSettleUseCount = equipSettleUseCount;
    }

    public int getZcgsEquipSettleUseCount() {
        return zcgsEquipSettleUseCount;
    }

    public void setZcgsEquipSettleUseCount(int zcgsEquipSettleUseCount) {
        this.zcgsEquipSettleUseCount = zcgsEquipSettleUseCount;
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

    public String getArea() {
        return area == null ? "" : area.trim();
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getWorkBookStatusP1() {
        return workBookStatusP1 == null ? "" : workBookStatusP1.trim();
    }

    public void setWorkBookStatusP1(String workBookStatusP1) {
        this.workBookStatusP1 = workBookStatusP1 == null ? null : workBookStatusP1.trim();
    }

    public String getWorkBookStatusP2() {
        return workBookStatusP2 == null ? "" : workBookStatusP2.trim();
    }

    public void setWorkBookStatusP2(String workBookStatusP2) {
        this.workBookStatusP2 = workBookStatusP2 == null ? null : workBookStatusP2.trim();
    }

    public int getAuditContrAlterCount() {
        return auditContrAlterCount;
    }

    public void setAuditContrAlterCount(int auditContrAlterCount) {
        this.auditContrAlterCount = auditContrAlterCount;
    }

    public int getCodeUsing() {
        return codeUsing;
    }

    public void setCodeUsing(int codeUsing) {
        this.codeUsing = codeUsing;
    }

    public String getFromContractID() {
        return fromContractID == null ? "" : fromContractID.trim();
    }

    public void setFromContractID(String fromContractID) {
        this.fromContractID = fromContractID == null ? null : fromContractID.trim();
    }

    public String getInvestContractNo() {
        return investContractNo == null ? "" : investContractNo.trim();
    }

    public void setInvestContractNo(String investContractNo) {
        this.investContractNo = investContractNo == null ? null : investContractNo.trim();
    }

    public String getResCategoryID() {
        return resCategoryID == null ? "" : resCategoryID.trim();
    }

    public void setResCategoryID(String resCategoryID) {
        this.resCategoryID = resCategoryID == null ? null : resCategoryID.trim();
    }

    public String getResCategoryName() {
        return resCategoryName == null ? "" : resCategoryName.trim();
    }

    public void setResCategoryName(String resCategoryName) {
        this.resCategoryName = resCategoryName == null ? null : resCategoryName.trim();
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<ZxErpFile> getAttachment() {
        return attachment == null ? Lists.newArrayList() : attachment;
    }

    public void setAttachment(List<ZxErpFile> attachment) {
        this.attachment = attachment == null ? null : attachment;
    }

    public List<ZxCtContrDetailAttr> getBondList() {
        return bondList == null ? Lists.newArrayList() : bondList;
    }

    public void setBondList(List<ZxCtContrDetailAttr> bondList) {
        this.bondList = bondList == null ? null : bondList;
    }

    public String getCompanyName() {
        return companyName == null ? "" : companyName.trim();
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getProjFea() {
        return projFea == null ? "" : projFea.trim();
    }

    public void setProjFea(String projFea) {
        this.projFea = projFea == null ? null : projFea.trim();
    }

    public String getSeason() {
        return season == null ? "" : season.trim();
    }

    public void setSeason(String season) {
        this.season = season == null ? null : season.trim();
    }

    public BigDecimal getTotalProduceAmt() {
        return totalProduceAmt;
    }

    public void setTotalProduceAmt(BigDecimal totalProduceAmt) {
        this.totalProduceAmt = totalProduceAmt;
    }

    public Date getMainEndDate() {
        return mainEndDate;
    }

    public void setMainEndDate(Date mainEndDate) {
        this.mainEndDate = mainEndDate;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

}
