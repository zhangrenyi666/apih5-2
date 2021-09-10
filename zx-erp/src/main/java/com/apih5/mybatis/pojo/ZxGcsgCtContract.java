package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;

public class ZxGcsgCtContract extends BasePojo {
    // 主键
    private String ctContractId;

    // 合同编号
    private String contractNo;

    // 合同名称
    private String contractName;

    // 合同类型
    private String contractType;

    // 摘要
    private String isOfficial;

    // 合同显示类型
    private String contractViewType;

    // 合同种类:合同种类(总价合同或单价合同)
    private String contractSort;

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

    // 项目经理
    private String firstPrincipal;

    // 甲方代表身份证
    private String firstPrincipalIDCard;

    // 甲方联系电话
    private String firstUnitTel;

    // 乙方ID
    private String secondID;

    // 乙方名称
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

    // 所属处部
    private String locationInDepr;

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

    // 工程规模
    private String projectSize;

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

    // 项目全称
    private String projectName;

    // 项目简称
    private String shortName;

    // 风险抵押金
    private BigDecimal ventureGuaranty;

    // 内部承包总价(万元)
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

    // 经办人
    private String transactor;

    // 指定的
    private String nominated;

    // 是否指定合同
    private String assignContract;

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

    // pp6
    private String pp6;

    // pp7
    private String pp7;

    // 清单
    private String pp8;

    // pp9
    private String pp9;

    // pp10
    private String pp10;

    // 乙方(手机)(pp1)
    private String secondPartyMobile;

    // 乙方(传真)(pp2)
    private String secondPartyFax;

    // 合同类型(pp3)
    private String contractManageType;

    // 累计结算金额(pp4)
    private BigDecimal accSettlementAmount;

    // 累计支付金额(pp5)
    private BigDecimal accPaymentAmount;

    // 清单(pp8)
    private String worksId;

    // 评审状态
    private String auditStatus;

    // 最后编辑时间
    private Date editTime;

    // 甲方地址
    private String firstAddr;

    // 设计单位电话
    private String designPhone;

    // 当前合同总造价（万元）
    private BigDecimal contractMoney;

    // 设计单位地址
    private String designAddr;

    // 监理单位电话:监理单位地址
    private String consultativeAddr;

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

    // 合同评审ID(fromApplyID)
    private String ctContrApplyId;

    // 业主合同功能码
    private String codeP1;

    // 同一公司
    private String code2T3;

    // 评审开始时间
    private Date flowBeginDate;

    // 评审结束时间
    private Date flowEndDate;

    // 质量保证金扣除比例（%）
    private BigDecimal qualityBailRate;

    // 安全生产保证金扣除比例（%）
    private BigDecimal prodSafeBailRate;

    // 农民工工资偿付保证金扣除比例（%）
    private BigDecimal payBailRate;

    // 实际开工日期
    private Date realBeginDate;

    // 实际结束日期
    private Date realEndDate;

    // 是否局属项目
    private String isJuProj;

    // 局直属项目ID
    private String juProjID;

    // 局直属项目名称
    private String juProjName;

    // 用于物资审核
    private String stockAudit;

    // 合同管理中的租赁类型
    private String rentType;

    // 变更后含税合同金额(万元)
    private BigDecimal alterContractSum;

    // 结算情况
    private String settleType;

    // 物资来源
    private String materialSource;

    // 基本信息审核按钮
    private String contrStatus;

    // 所属区域
    private String subArea;

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

    // 是否简易计税
    private String isEasyTax;

    // 【业主合同台账】_项目性质
    private String projectProperty;

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

    // 财务系统ID
    private String fiId;

    // 名义投标单位ID
    private String biddersId;

    // 名义投标单位编号
    private String biddersCode;

    // 名义投标单位
    private String biddersName;

    // 核算单位id
    private String accountUnitId;

    // 核算单位编号
    private String accountUnitCode;

    // 核算单位
    private String accountUnitName;

    // 核算项目Id
    private String accountProjId;

    // 核算项目编号
    private String accountProjCode;

    // 核算项目名称
    private String accountProjName;

    // 项目属地
    private String projSite;

    // 业务板块
    private String busiSegments;

    // 项目资金来源
    private String projFundsSource;

    // 事业部
    private String division;

    // 税务备案号
    private String taxFilingCode;

    // 增值税计税方法
    private String taxCountWay;

    // 增值税预征率
    private String taxAdvanceRate;

    // 增值税使用税率
    private String taxUseWay;

    // 是否属地预缴
    private String prepaidLand;

    // 预缴国税机关
    private String nationalTax;

    // 预缴国税机关联系方式
    private String nationalTaxTel;

    // 预交国税机关地址
    private String nationalTaxAdds;

    // 项目部通讯地址
    private String projDepAdds;

    // 邮编
    private String projDepZipCode;

    // 电话
    private String projDepTel;

    // 传真
    private String projDepFax;

    // 项目阶段
    private String projStage;

    // 固话
    private String pmFixedLine;

    // 邮箱
    private String pmMail;

    // 财务负责人
    private String fiCharge;

    // 手机
    private String fiTel;

    // 固话
    private String fiFixedLine;

    // 邮箱
    private String fiMail;

    // 合约负责人
    private String ctrCharge;

    // 手机
    private String ctrTel;

    // 固话
    private String ctrFixedLine;

    // 邮箱
    private String ctrMail;

    // 债权清收负责人
    private String dcLeader;

    // 手机
    private String dcTel;

    // 固话
    private String dcFixedLine;

    // 邮箱
    private String dcMail;

    // 复核日期
    private Date doubleCheckDate;

    // 录入日期
    private Date writeDate;

    // 核算部门id财务
    private String accountDepId;

    // 核算部门编号财务
    private String accountDepCode;

    // 核算部门名称财务
    private String accountDepName;

    // 往来单位编号
    private String secondIDCode;

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

    // 市级
    private String cityName;

    // notDisplay
    private String notDisplay;

    // 复核人
    private String doubleCheckPerson;

    // 竣工决算日期
    private Date finalDate;

    // 投标状态
    private String bidStatus;

    // 质保金到期日期
    private Date premiumExpDate;

    // 垫付款到期日期
    private Date cushionExpDate;

    // 预付款比例
    private BigDecimal advanceRate;

    // 进度款比例
    private BigDecimal progressRate;

    // 竣工款比例
    private BigDecimal completionRate;

    // 往来单位编号LSWLDW_WLDWBH
    private String firstIdBh;

    // 往来单位编号lswldw_sh
    private String firstIdSh;

    // 往来单位编号
    private String secondIDCodeBh;

    // 累计结算金额
    private BigDecimal settleTotalAmt;

    // 财务审核状态
    private String cwStatus;

    // 备注
    private String remarks;

    // 财务合同ID
    private String zjxmhtbNm;

    // 排序
    private int sort=0;

    // 财务合同编号
    private String zjxmhtbBh;

    // 往来单位名称
    private String secondIDCodeName;

    // 项目内码
    private String ZJGCXMNM;

    // 项目编号
    private String ZJGCXMXMBH;

    // 项目名称
    private String ZJGCXMXMMC;

    // 财务合同名称
    private String zjxmhtbMc;

    // 是否关联
    private String isRela;

    // 合同码ID
    private String contractCodeID;

    // 所属项目
    private String orgName;

    // 甲方
    private String firstIdFormula;

    // 合同乙方
    private String secondIDFormula;

    // 统一社会信用代码
    private String orgCertificate;

    // 所属处部
    private String locationInDeprFormula;

    // 区域(北京市广东省..)
    private String area;

    // 国家
    private String country;

    // 类型(公路铁路市政其他)
    private String projType;

    // 项目特征
    private String projFea;

    // 承包合同中清单是否审核
    private String workBookStatusP1;

    // 清单是否审核
    private String workBookStatusP2;

    // 物资变更是否有审核单据
    private int auditContrAlterCount=0;

    // 用于判断编号是否已被引用
    private int codeUsing=0;

    // 原合同编号ID
    private String fromContractID;

    // 物资结算引用数
    private int stockSettleUseCount=0;

    // 机械及其他结算引用
    private int equipSettleUseCount=0;

    // 资产公司结算引用用于明细是否可以编辑处理
    private int zcgsEquipSettleUseCount=0;

    // 工程结算引用数
    private int projectSettleUseCount=0;

    // 所属事业部
    private String bizDep;

    // 是否参与结算指标考核
    private String isSettle;

    // 是否局发送
    private String isJuSend;

    // 投资合同编号
    private String investContractNo;

    // 物资类别ID
    private String resCategoryID;

    // 物资类别
    private String resCategoryName;

    // 备注
    private String opinionField1;

    // 备注
    private String opinionField2;

    // 备注
    private String opinionField3;

    // 备注
    private String opinionField4;

    // 备注
    private String opinionField5;

    // 备注
    private String opinionField6;

    // 备注
    private String opinionField7;

    // 备注
    private String opinionField8;

    // 备注
    private String opinionField9;

    // 备注
    private String opinionField10;

    // 流程id
    private String apih5FlowId;

    // work_id
    private String workId;

    // 工序审核状态
    private String apih5FlowStatus;

    // 流程状态
    private String apih5FlowNodeStatus;

    // 保证金集合
    private List<ZxGcsgCtCoContractAmtRate> coContractAmtRateList;

    // 附件集合
    private List<ZxGcsgCommonAttachment> commonAttachmentList;

    // 项目id
    private String projectId;

    // 公司id
    private String companyId;

    // 公司名称
    private String companyName;

    public String getCtContractId() {
        return ctContractId == null ? "" : ctContractId.trim();
    }

    public void setCtContractId(String ctContractId) {
        this.ctContractId = ctContractId == null ? null : ctContractId.trim();
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

    public String getIsOfficial() {
        return isOfficial == null ? "" : isOfficial.trim();
    }

    public void setIsOfficial(String isOfficial) {
        this.isOfficial = isOfficial == null ? null : isOfficial.trim();
    }

    public String getContractViewType() {
        return contractViewType == null ? "" : contractViewType.trim();
    }

    public void setContractViewType(String contractViewType) {
        this.contractViewType = contractViewType == null ? null : contractViewType.trim();
    }

    public String getContractSort() {
        return contractSort == null ? "" : contractSort.trim();
    }

    public void setContractSort(String contractSort) {
        this.contractSort = contractSort == null ? null : contractSort.trim();
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

    public String getProjectSize() {
        return projectSize == null ? "" : projectSize.trim();
    }

    public void setProjectSize(String projectSize) {
        this.projectSize = projectSize == null ? null : projectSize.trim();
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

    public String getPp7() {
        return pp7 == null ? "" : pp7.trim();
    }

    public void setPp7(String pp7) {
        this.pp7 = pp7 == null ? null : pp7.trim();
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

    public String getSecondPartyMobile() {
        return secondPartyMobile == null ? "" : secondPartyMobile.trim();
    }

    public void setSecondPartyMobile(String secondPartyMobile) {
        this.secondPartyMobile = secondPartyMobile == null ? null : secondPartyMobile.trim();
    }

    public String getSecondPartyFax() {
        return secondPartyFax == null ? "" : secondPartyFax.trim();
    }

    public void setSecondPartyFax(String secondPartyFax) {
        this.secondPartyFax = secondPartyFax == null ? null : secondPartyFax.trim();
    }

    public String getContractManageType() {
        return contractManageType == null ? "" : contractManageType.trim();
    }

    public void setContractManageType(String contractManageType) {
        this.contractManageType = contractManageType == null ? null : contractManageType.trim();
    }

    public BigDecimal getAccSettlementAmount() {
        return accSettlementAmount;
    }

    public void setAccSettlementAmount(BigDecimal accSettlementAmount) {
        this.accSettlementAmount = accSettlementAmount;
    }

    public BigDecimal getAccPaymentAmount() {
        return accPaymentAmount;
    }

    public void setAccPaymentAmount(BigDecimal accPaymentAmount) {
        this.accPaymentAmount = accPaymentAmount;
    }

    public String getWorksId() {
        return worksId == null ? "" : worksId.trim();
    }

    public void setWorksId(String worksId) {
        this.worksId = worksId == null ? null : worksId.trim();
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

    public String getFirstAddr() {
        return firstAddr == null ? "" : firstAddr.trim();
    }

    public void setFirstAddr(String firstAddr) {
        this.firstAddr = firstAddr == null ? null : firstAddr.trim();
    }

    public String getDesignPhone() {
        return designPhone == null ? "" : designPhone.trim();
    }

    public void setDesignPhone(String designPhone) {
        this.designPhone = designPhone == null ? null : designPhone.trim();
    }

    public BigDecimal getContractMoney() {
        return contractMoney;
    }

    public void setContractMoney(BigDecimal contractMoney) {
        this.contractMoney = contractMoney;
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

    public String getCtContrApplyId() {
        return ctContrApplyId == null ? "" : ctContrApplyId.trim();
    }

    public void setCtContrApplyId(String ctContrApplyId) {
        this.ctContrApplyId = ctContrApplyId == null ? null : ctContrApplyId.trim();
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

    public String getSubArea() {
        return subArea == null ? "" : subArea.trim();
    }

    public void setSubArea(String subArea) {
        this.subArea = subArea == null ? null : subArea.trim();
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

    public String getIsEasyTax() {
        return isEasyTax == null ? "" : isEasyTax.trim();
    }

    public void setIsEasyTax(String isEasyTax) {
        this.isEasyTax = isEasyTax == null ? null : isEasyTax.trim();
    }

    public String getProjectProperty() {
        return projectProperty == null ? "" : projectProperty.trim();
    }

    public void setProjectProperty(String projectProperty) {
        this.projectProperty = projectProperty == null ? null : projectProperty.trim();
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

    public String getCityName() {
        return cityName == null ? "" : cityName.trim();
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
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

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getZjxmhtbNm() {
        return zjxmhtbNm == null ? "" : zjxmhtbNm.trim();
    }

    public void setZjxmhtbNm(String zjxmhtbNm) {
        this.zjxmhtbNm = zjxmhtbNm == null ? null : zjxmhtbNm.trim();
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getZjxmhtbBh() {
        return zjxmhtbBh == null ? "" : zjxmhtbBh.trim();
    }

    public void setZjxmhtbBh(String zjxmhtbBh) {
        this.zjxmhtbBh = zjxmhtbBh == null ? null : zjxmhtbBh.trim();
    }

    public String getSecondIDCodeName() {
        return secondIDCodeName == null ? "" : secondIDCodeName.trim();
    }

    public void setSecondIDCodeName(String secondIDCodeName) {
        this.secondIDCodeName = secondIDCodeName == null ? null : secondIDCodeName.trim();
    }

    public String getZJGCXMNM() {
        return ZJGCXMNM == null ? "" : ZJGCXMNM.trim();
    }

    public void setZJGCXMNM(String ZJGCXMNM) {
        this.ZJGCXMNM = ZJGCXMNM == null ? null : ZJGCXMNM.trim();
    }

    public String getZJGCXMXMBH() {
        return ZJGCXMXMBH == null ? "" : ZJGCXMXMBH.trim();
    }

    public void setZJGCXMXMBH(String ZJGCXMXMBH) {
        this.ZJGCXMXMBH = ZJGCXMXMBH == null ? null : ZJGCXMXMBH.trim();
    }

    public String getZJGCXMXMMC() {
        return ZJGCXMXMMC == null ? "" : ZJGCXMXMMC.trim();
    }

    public void setZJGCXMXMMC(String ZJGCXMXMMC) {
        this.ZJGCXMXMMC = ZJGCXMXMMC == null ? null : ZJGCXMXMMC.trim();
    }

    public String getZjxmhtbMc() {
        return zjxmhtbMc == null ? "" : zjxmhtbMc.trim();
    }

    public void setZjxmhtbMc(String zjxmhtbMc) {
        this.zjxmhtbMc = zjxmhtbMc == null ? null : zjxmhtbMc.trim();
    }

    public String getIsRela() {
        return isRela == null ? "" : isRela.trim();
    }

    public void setIsRela(String isRela) {
        this.isRela = isRela == null ? null : isRela.trim();
    }

    public String getContractCodeID() {
        return contractCodeID == null ? "" : contractCodeID.trim();
    }

    public void setContractCodeID(String contractCodeID) {
        this.contractCodeID = contractCodeID == null ? null : contractCodeID.trim();
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

    public String getSecondIDFormula() {
        return secondIDFormula == null ? "" : secondIDFormula.trim();
    }

    public void setSecondIDFormula(String secondIDFormula) {
        this.secondIDFormula = secondIDFormula == null ? null : secondIDFormula.trim();
    }

    public String getOrgCertificate() {
        return orgCertificate == null ? "" : orgCertificate.trim();
    }

    public void setOrgCertificate(String orgCertificate) {
        this.orgCertificate = orgCertificate == null ? null : orgCertificate.trim();
    }

    public String getLocationInDeprFormula() {
        return locationInDeprFormula == null ? "" : locationInDeprFormula.trim();
    }

    public void setLocationInDeprFormula(String locationInDeprFormula) {
        this.locationInDeprFormula = locationInDeprFormula == null ? null : locationInDeprFormula.trim();
    }

    public String getArea() {
        return area == null ? "" : area.trim();
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getCountry() {
        return country == null ? "" : country.trim();
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getProjType() {
        return projType == null ? "" : projType.trim();
    }

    public void setProjType(String projType) {
        this.projType = projType == null ? null : projType.trim();
    }

    public String getProjFea() {
        return projFea == null ? "" : projFea.trim();
    }

    public void setProjFea(String projFea) {
        this.projFea = projFea == null ? null : projFea.trim();
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

    public int getStockSettleUseCount() {
        return stockSettleUseCount;
    }

    public void setStockSettleUseCount(int stockSettleUseCount) {
        this.stockSettleUseCount = stockSettleUseCount;
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

    public String getIsJuSend() {
        return isJuSend == null ? "" : isJuSend.trim();
    }

    public void setIsJuSend(String isJuSend) {
        this.isJuSend = isJuSend == null ? null : isJuSend.trim();
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

    public String getOpinionField1() {
        return opinionField1 == null ? "" : opinionField1.trim();
    }

    public void setOpinionField1(String opinionField1) {
        this.opinionField1 = opinionField1 == null ? null : opinionField1.trim();
    }

    public String getOpinionField2() {
        return opinionField2 == null ? "" : opinionField2.trim();
    }

    public void setOpinionField2(String opinionField2) {
        this.opinionField2 = opinionField2 == null ? null : opinionField2.trim();
    }

    public String getOpinionField3() {
        return opinionField3 == null ? "" : opinionField3.trim();
    }

    public void setOpinionField3(String opinionField3) {
        this.opinionField3 = opinionField3 == null ? null : opinionField3.trim();
    }

    public String getOpinionField4() {
        return opinionField4 == null ? "" : opinionField4.trim();
    }

    public void setOpinionField4(String opinionField4) {
        this.opinionField4 = opinionField4 == null ? null : opinionField4.trim();
    }

    public String getOpinionField5() {
        return opinionField5 == null ? "" : opinionField5.trim();
    }

    public void setOpinionField5(String opinionField5) {
        this.opinionField5 = opinionField5 == null ? null : opinionField5.trim();
    }

    public String getOpinionField6() {
        return opinionField6 == null ? "" : opinionField6.trim();
    }

    public void setOpinionField6(String opinionField6) {
        this.opinionField6 = opinionField6 == null ? null : opinionField6.trim();
    }

    public String getOpinionField7() {
        return opinionField7 == null ? "" : opinionField7.trim();
    }

    public void setOpinionField7(String opinionField7) {
        this.opinionField7 = opinionField7 == null ? null : opinionField7.trim();
    }

    public String getOpinionField8() {
        return opinionField8 == null ? "" : opinionField8.trim();
    }

    public void setOpinionField8(String opinionField8) {
        this.opinionField8 = opinionField8 == null ? null : opinionField8.trim();
    }

    public String getOpinionField9() {
        return opinionField9 == null ? "" : opinionField9.trim();
    }

    public void setOpinionField9(String opinionField9) {
        this.opinionField9 = opinionField9 == null ? null : opinionField9.trim();
    }

    public String getOpinionField10() {
        return opinionField10 == null ? "" : opinionField10.trim();
    }

    public void setOpinionField10(String opinionField10) {
        this.opinionField10 = opinionField10 == null ? null : opinionField10.trim();
    }

    public String getApih5FlowId() {
        return apih5FlowId == null ? "" : apih5FlowId.trim();
    }

    public void setApih5FlowId(String apih5FlowId) {
        this.apih5FlowId = apih5FlowId == null ? null : apih5FlowId.trim();
    }

    public String getWorkId() {
        return workId == null ? "" : workId.trim();
    }

    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    public String getApih5FlowStatus() {
        return apih5FlowStatus == null ? "" : apih5FlowStatus.trim();
    }

    public void setApih5FlowStatus(String apih5FlowStatus) {
        this.apih5FlowStatus = apih5FlowStatus == null ? null : apih5FlowStatus.trim();
    }

    public String getApih5FlowNodeStatus() {
        return apih5FlowNodeStatus == null ? "" : apih5FlowNodeStatus.trim();
    }

    public void setApih5FlowNodeStatus(String apih5FlowNodeStatus) {
        this.apih5FlowNodeStatus = apih5FlowNodeStatus == null ? null : apih5FlowNodeStatus.trim();
    }

    public List<ZxGcsgCtCoContractAmtRate> getCoContractAmtRateList() {
        return coContractAmtRateList == null ? Lists.newArrayList() : coContractAmtRateList;
    }

    public void setCoContractAmtRateList(List<ZxGcsgCtCoContractAmtRate> coContractAmtRateList) {
        this.coContractAmtRateList = coContractAmtRateList == null ? null : coContractAmtRateList;
    }

    public List<ZxGcsgCommonAttachment> getCommonAttachmentList() {
        return commonAttachmentList == null ? Lists.newArrayList() : commonAttachmentList;
    }

    public void setCommonAttachmentList(List<ZxGcsgCommonAttachment> commonAttachmentList) {
        this.commonAttachmentList = commonAttachmentList == null ? null : commonAttachmentList;
    }

    public String getProjectId() {
        return projectId == null ? "" : projectId.trim();
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
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

}
