import { lazy } from "react";
import { basicHoc } from "apih5/modules/layouts";

const ContactsTreeTablePage = lazy(() => import("apih5/pages/contactsTreeTable"));

//引用basicHoc进行简单封装
const basic = (Component, props = {

    //是否开启待办数量
    // isOpenTodoNum:true,
    //获取待办数量的接口 
    // getTodoNumApiName:"getZjPrProgrammeTodoCount",
    //获取待办数量轮询时间间隔默认10 单位s
    // getTodoNumTime:60 * 30,

    //切换项目或者获取项目列表的接口
    apiNames: {
        getCompanyList: "getSysPermissionListByProject",
        changeCompany: "changeSysProject",
        //中交切换公司接口
        // changeCompany: "changeCompany",
    },

    //项目数据的一些字段名字
    projectKeys: {
        list: "projectList",
        value: "departmentId",//levelId  projectId
        label: "departmentName",//levelShortName  projectShortName
    },


    //说明
    //获取项目列表的方法 getCompanyListFn(cb)   
    //cb(当前项目, 项目列表) 必须执行
    // getCompanyListFn: null

    //中交切换项目案例 该方法存在getCompanyList配置将失去意义
    // getCompanyListFn: (pageProps,cb) => {
    //     let {
    //         loginAndLogoutInfo: {
    //             loginInfo: {
    //                 userInfo: { companyList }
    //             }
    //         }
    //     } = pageProps;
    //     let _curCompany = null;


    //     companyList =
    //         companyList &&
    //         companyList.map(item => {
    //             const { companyName,companyId,companySelectFlag } = item;
    //             let _item = {
    //                 ...item,
    //                 value: companyId,
    //                 realLabel: companyName,
    //                 label: (companyName && companyName.length > 7) ? companyName.substr(0,7) + '...' : companyName
    //             }

    //             if (companySelectFlag === 1) {
    //                 //当前选择公司
    //                 _curCompany = _item;
    //             }
    //             return {
    //                 ..._item
    //             };
    //         });

    //     if (!_curCompany && companyList && companyList.length) {
    //         _curCompany = companyList[0];
    //     }
    //     cb(_curCompany,companyList);
    // }

}) => {
    return basicHoc(props)(Component);
}



const pageComs = {
    /* 共通页面start */
    ContactsTreeTablePage: {
        mustLogin: true,
        Com: ContactsTreeTablePage
    },
    Login: {
        mustLogin: false,
        Com: lazy(() => import("./login"))
    },
    Home: {
        mustLogin: true,
        Com: lazy(() => import("./home"))
    },
    Contacts: {
        mustLogin: true,
        Com: lazy(() => import("./contacts"))
    },
    Menus: {
        mustLogin: true,
        Com: lazy(() => import("./menus"))
    },
    Roles: {
        mustLogin: true,
        Com: lazy(() => import("./roles"))
    },
    FlowType: {
        mustLogin: true,
        Com: lazy(() => import("./FlowType"))
    },
    TrainList: {
        mustLogin: true,
        Com: lazy(() => import("./TrainList"))
    },
    BaseCode: {
        mustLogin: true,
        Com: lazy(() => import("./BaseCode"))
    },
    SystemUserGroup: {
        mustLogin: true,
        Com: lazy(() => import("./SystemUserGroup"))
    },
    InterFaceManage: {
        mustLogin: true,
        Com: lazy(() => import("./InterFaceManage"))
    },

    IframePage: {
        mustLogin: true,
        Com: lazy(() => import("apih5/pages/IframePage"))
    },
    SetTimeOutPage: {
        mustLogin: true,
        Com: lazy(() => import("apih5/pages/SetTimeOutPage"))
    },
    SetTimeOutLogPage: {
        mustLogin: true,
        Com: lazy(() => import("apih5/pages/SetTimeOutLogPage"))
    },
    rolesTwo: {
        mustLogin: true,
        Com: lazy(() => import("apih5/pages/rolesTwo"))
    },
    ProjectManage: {
        mustLogin: true,
        Com: lazy(() => import("apih5/pages/ProjectManage"))
    },

    // 执行人设置
    ExecutorSetting: {
        mustLogin: true,
        Com: lazy(() => import("apih5/pages/flowSetting/ExecutorSetting"))
    },
    FlowCondition: {
        mustLogin: true,
        Com: lazy(() => import("apih5/pages/flowSetting/FlowCondition"))
    },

    /* 共通页面over */
    //项目立项
    ProjectMangerment: {
        mustLogin: true,
        Com: lazy(() => import("./ProjectMangerment"))
    },
    //设备购置记录
    //设备管理
    EquipmentPurchaseRecord: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/EquipmentPurchaseRecord"))
    },
    EquipPesron: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/EquipPerson"))
    },

    CompanyEquipmentLedger: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/CompanyEquipmentLedger"))
    },
    EquipmentAbnormalOperationManagement: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/EquipmentAbnormalOperationManagement"))
    },
    ClassificationOfEquipment: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/ClassificationOfEquipment"))
    },
    ClassificationOfABCEquipment: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/ClassificationOfABCEquipment"))
    },
    StaffingSet: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/StaffingSet"))
    },
    PositionSettingOfMechanicalPersonnel: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/PositionSettingOfMechanicalPersonnel"))
    },
    MaterExternalEquipmentUsageReport: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/MaterExternalEquipmentUsageReport"))
    },
    VehicleUsageCompanyReport: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/VehicleUsageCompanyReport"))
    },
    VehicleUsageProjectReport: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/VehicleUsageProjectReport"))
    },
    EquipmentVerificationLedger: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/EquipmentVerificationLedger"))
    },
    ActualEquipmentAcceptanceLedger: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/ActualEquipmentAcceptanceLedger"))
    },
    MechanicalEquipmentPersonnel: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/MechanicalEquipmentPersonnel"))
    },
    CooperativeUnitsWithTheirOwnEquipmentToEnterTheRegistration: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/CooperativeUnitsWithTheirOwnEquipmentToEnterTheRegistration"))
    },
    CollectionOfEquipmentLeasingPriceLimit: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/CollectionOfEquipmentLeasingPriceLimit"))
    },
    //分析查询
    SearchDeviceSource: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/SearchDeviceSource"))
    },
    AbcMachineDistribute: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/AbcMachineDistribute"))
    },
    SearchForEquip: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/SearchForEquip"))
    },
    SearchForEquipClass: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/SearchForEquipClass"))
    },
    SearchForEquipPrice: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/SearchForEquipPrice"))
    },
    DistributionOfEquipmentAssets: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/DistributionOfEquipmentAssets"))
    },
    EquipmentTrendInFiveYears: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/EquipmentTrendInFiveYears"))
    },
    ComparedAnalysis: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/ComparedAnalysis"))
    },
    GlobalEquipmentLeasingPriceLimit: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/GlobalEquipmentLeasingPriceLimit"))
    },
    ManagementOfLeasedEquipment: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/ManagementOfLeasedEquipment"))
    },
    TheirOwnEquipment: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/TheirOwnEquipment"))
    },
    TypeOfEquipmentMaintenance: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/TypeOfEquipmentMaintenance"))
    },
    SourcesOfEquipment: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/SourcesOfEquipment"))
    },
    ZiYouCLForm: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/ZiYouCLForm"))
    },
    ZuLinCLForm: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/ZuLinCLForm"))
    },
    AbcMachinery: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/AbcMachinery"))
    },
    EquipLimitPriceVOReport: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/EquipLimitPriceVOReport"))
    },
    EquipSummaryListOfAdd: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/EquipSummaryListOfAdd"))
    },
    ComEquip: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/ComEquip"))
    },
    ComCar: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/ComCar"))
    },
    EquipmentForm: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/EquipmentForm"))
    },
    CollectionOfEquipmentLeasingPriceLimitForm: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/CollectionOfEquipmentLeasingPriceLimitForm"))
    },
    IdleEquipmentForm: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/IdleEquipmentForm"))
    },
    CollectBusinessForQuarterlyForm: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/CollectBusinessForQuarterlyForm"))
    },
    ComCarForm: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/ComCarForm"))
    },
    ComEquipForm: {
        mustLogin: true,
        Com: lazy(() => import("./EquipmentManagement/ComEquipForm"))
    },
    //物资管理
    ImportTax: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/ImportTax"))
    },
    ImportTaxMonth: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/ImportTaxMonth/indexCopy"))
    },
    ImportTaxCatch: {//采购
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/ImportTaxCatch/indexCopy"))
    },
    ImportTaxPurchase: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/ImportTaxPurchase/indexCopy"))
    },
    ImportTaxChange: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/ImportTaxChange/indexCopy"))
    },
    ImportPriceLimitManage: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/ImportPriceLimitManage/index"))
    },
    ImportPriceLimitChange: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/ImportPriceLimitChange/index"))
    },
    ImportTaxCollectTicket: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/ImportTaxCollectTicket"))
    },
    ImportTaxMaterialReceipt: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/ImportTaxMaterialReceipt"))
    },
    ImportTaxWarehouse: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/ImportTaxWarehouse"))
    },
    ImportTaxMaterialRequisition: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/ImportTaxMaterialRequisition"))
    },
    BureauUnifiedMaterialClassificationSetting: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/BureauUnifiedMaterialClassificationSetting"))
    },
    BureauUniformMaterialCodingSettings: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/BureauUniformMaterialCodingSettings"))
    },
    MaterialCodeQuery: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/MaterialCodeQuery"))
    },
    InternalUnitSetting: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/InternalUnitSetting"))
    },
    PartialItemization: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/PartialItemization"))
    },
    TheWarehouseIsSet: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/TheWarehouseIsSet"))
    },
    PuMMReqPlan: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/aReport/PuMMReqPlan"))
    },
    ResourceOutList: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/aReport/ResourceOutList"))
    },
    // TurnWriteOff:{
    //     mustLogin: true,
    //     Com: lazy(() => import("./MaterialsManagement/TurnWriteOff"))
    // },

    //业财一体化
    AccountingUnitConnected: {
        mustLogin: true,
        Com: lazy(() => import("./IntegrationOfBusinessAndFinance/AccountingUnitConnected"))
    },
    PushForEngineer: {
        mustLogin: true,
        Com: lazy(() => import("./IntegrationOfBusinessAndFinance/PushForEngineer"))
    },
    FinancialContractInfor: {
        mustLogin: true,
        Com: lazy(() => import("./IntegrationOfBusinessAndFinance/AuxiliaryTool/FinancialContractInfor"))
    },
    FinancialBond: {
        mustLogin: true,
        Com: lazy(() => import("./IntegrationOfBusinessAndFinance/AuxiliaryTool/FinancialBond"))
    },
    FinancialSystemForBtype: {
        mustLogin: true,
        Com: lazy(() => import("./IntegrationOfBusinessAndFinance/AuxiliaryTool/FinancialSystemForBtype"))
    },
    FinancialSystemForAccounting: {
        mustLogin: true,
        Com: lazy(() => import("./IntegrationOfBusinessAndFinance/AuxiliaryTool/FinancialSystemForAccounting"))
    },
    FinancialForEngineer: {
        mustLogin: true,
        Com: lazy(() => import("./IntegrationOfBusinessAndFinance/AuxiliaryTool/FinancialForEngineer"))
    },
    UpdataFinancialBond: {
        mustLogin: true,
        Com: lazy(() => import("./IntegrationOfBusinessAndFinance/AuxiliaryTool/UpdataFinancialBond"))
    },
    SettlementInformation: {
        mustLogin: true,
        Com: lazy(() => import("./IntegrationOfBusinessAndFinance/AuxiliaryTool/SettlementInformation"))
    },
    PushForEquipment: {
        mustLogin: true,
        Com: lazy(() => import("./IntegrationOfBusinessAndFinance/PushForEquipment"))
    },
    PushForMaterialsRent: {
        mustLogin: true,
        Com: lazy(() => import("./IntegrationOfBusinessAndFinance/PushForMaterialsRent"))
    },
    PushForOther: {
        mustLogin: true,
        Com: lazy(() => import("./IntegrationOfBusinessAndFinance/PushForOther"))
    },
    //合同管理
    ProjectBusinessFiltering: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/ProjectBusinessFiltering"))
    },
    CountriesSet: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/CountriesSet"))
    },
    ProjectConstructionContract: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/ProjectConstructionContract"))
    },
    MaterialManagementContract: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/MaterialManagementContract"))
    },
    MachineryManagementContract: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/MachineryManagementContract"))
    },
    OtherContract: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/OtherContract"))
    },
    CollateralContract: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/CollateralContract"))
    },
    SupplementaryAgreementForEngineeringConstruction: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/SupplementaryAgreementForEngineeringConstruction"))
    },
    SupplementaryAgreementOnMaterialManagement: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/SupplementaryAgreementOnMaterialManagement"))
    },
    SupplementaryAgreementToTheEquipmentContract: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/SupplementaryAgreementToTheEquipmentContract"))
    },
    OtherSupplementaryAgreements: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/OtherSupplementaryAgreements"))
    },
    SupplementaryAgreementToSubsidiaryContract: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/SupplementaryAgreementToSubsidiaryContract"))
    },
    ProjectConstructionContractManagement: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/ProjectConstructionContractManagement"))
    },
    MaterialManagementContractManagement: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/MaterialManagementContractManagement"))
    },
    MachineryManagementContractManagement: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/MachineryManagementContractManagement"))
    },
    OtherContractManagement: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/OtherContractManagement"))
    },
    CollateralContractManagement: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/CollateralContractManagement"))
    },
    CloudECommerceContractorSubcontractsCalibrationData: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/CloudECommerceContractorSubcontractsCalibrationData"))
    },
    SubcontractStandardProcessLibrary: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/SubcontractStandardProcessLibrary"))
    },
    SubcontractPricingRuleBase: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/SubcontractPricingRuleBase"))
    },
    MunicipalStandardProcessLibrary: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/MunicipalStandardProcessLibrary"))
    },
    MunicipalPricingRuleBase: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/MunicipalPricingRuleBase"))
    },
    RailwaySubcontractingProcessLibrary: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/RailwaySubcontractingProcessLibrary"))
    },
    RailwaySubcontractPricingRuleBase: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/RailwaySubcontractPricingRuleBase"))
    },
    TrackSubcontractingProcessLibrary: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/TrackSubcontractingProcessLibrary"))
    },
    TrackSubcontractPricingRuleBase: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/TrackSubcontractPricingRuleBase"))
    },
    HouseBuildingSubcontractingProcessLibrary: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/HouseBuildingSubcontractingProcessLibrary"))
    },
    HouseBuildingSubcontractPricingRuleBase: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/HouseBuildingSubcontractPricingRuleBase"))
    },
    OwnerSContractAccount: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/OwnerSContractAccount"))
    },
    //合同管理 -- 报表
    ContractTypeSettlementStatistics: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/ContractTypeSettlementStatistics"))
    },
    EngineeringMeteringPaymentSummaryTable: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/EngineeringMeteringPaymentSummaryTable"))
    },
    OwnersSettlementSchedule: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/OwnersSettlementSchedule"))
    },
    OwnerContractLedgerStatement: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/OwnerContractLedgerStatement"))
    },
    StatisticalTableOfContractDeviation: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/StatisticalTableOfContractDeviation"))
    },
    ContractApprovalRateStatistics: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/ContractApprovalRateStatistics"))
    },
    ContractReviewLedger: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/ContractReviewLedger"))
    },
    ConstructionContentAndValuationRules: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/ConstructionContentAndValuationRules"))
    },
    ContractUnitPriceAnalysis: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/ContractUnitPriceAnalysis"))
    },
    BiddingForProjectContract: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/BiddingForProjectContract"))
    },
    AccountForForeignBusinessContracts: {
        mustLogin: true,
        Com: lazy(() => import("./ContractManagement/AccountForForeignBusinessContracts"))
    },
    //结算管理 -- 分析查询
    CentralizedSettlementControl: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/CentralizedSettlementControl"))
    },
    CompletedProjectEnquiry: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/CompletedProjectEnquiry"))
    },
    CentralizedSettlementIndexControlLedger: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/CentralizedSettlementIndexControlLedger"))
    },
    //结算管理 -- 工程类结算问题预警
    NegativeSettlementDataStatistics: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/NegativeSettlementDataStatistics"))
    },
    TimelinessOfSettlementDataStatistics: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/TimelinessOfSettlementDataStatistics"))
    },
    ZeroSettlementOrSimilarZeroSettlementDataStatistics: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/ZeroSettlementOrSimilarZeroSettlementDataStatistics"))
    },
    NotTimelyDataStatistics: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/NotTimelyDataStatistics"))
    },
    TimelyDataStatisticsOfSettlementApproval: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/TimelyDataStatisticsOfSettlementApproval"))
    },
    //结算管理 -- 数据填报
    CumulativeSettlementCostRatioIndex: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/CumulativeSettlementCostRatioIndex"))
    },
    //结算管理 -- 数据填报 -- 结算金额章节划分与成本分析
    ChapterDivisionCostAnalysisSettlementAmount: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/ChapterDivisionCostAnalysisSettlementAmount"))
    },
    //结算管理 -- 台账报表
    ProjectSettlementLedgerCompany: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/ProjectSettlementLedgerCompany"))
    },
    ProjectSettlementLedgerProject: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/ProjectSettlementLedgerProject"))
    },
    MechanicalSettlementAndLedgerCompany: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/MechanicalSettlementAndLedgerCompany"))
    },
    MechanicalSettlementLedgerItems: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/MechanicalSettlementLedgerItems"))
    },
    OtherBillingAndBookkeepingCompanies: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/OtherBillingAndBookkeepingCompanies"))
    },
    OtherAccountItems: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/OtherAccountItems"))
    },
    MaterialPurchaseSettlementLedgerCompany: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/MaterialPurchaseSettlementLedgerCompany"))
    },
    MaterialPurchaseSettlementLedgerProject: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/MaterialPurchaseSettlementLedgerProject"))
    },
    MaterialLeaseSettlementLedgerCompany: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/MaterialLeaseSettlementLedgerCompany"))
    },
    MaterialLeaseSettlementLedgerProject: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/MaterialLeaseSettlementLedgerProject"))
    },
    ScheduleOfPaymentSettlement: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/ScheduleOfPaymentSettlement"))
    },
    SummaryOfSettlementOfPaymentItems: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/SummaryOfSettlementOfPaymentItems"))
    },
    BackToTheSettlement: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/BackToTheSettlement"))
    },
    ProjectContractSettlementAndExecutionLedger: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/ProjectContractSettlementAndExecutionLedger"))
    },
    SettlementShouldPayTheLedger: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/SettlementShouldPayTheLedger"))
    },
    //结算管理 -----其它类结算
    OtherSettlement: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/OtherSettlement"))
    },
    //结算管理 -----附属类结算
    AffiliatFinalStatement: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/AffiliatFinalStatement"))
    },
    //工程类结算
    EngineeringSettlement: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/EngineeringSettlement"))
    },
    //物资采购类结算
    MaterialPurchaseSettlement: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/MaterialPurchaseSettlement"))
    },
    //物资租赁类结算
    MaterialLeasingSettlement: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/MaterialLeasingSettlement"))
    },
    MachineRentCountList: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/MachineRentCountList"))
    },
    SetUpTheProcessExecutor: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/SetUpTheProcessExecutor"))
    },
    ListSectionPartitionSettings: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/ListSectionPartitionSettings"))
    },
    EngineeringSettlementAndPaymentSetting: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/EngineeringSettlementAndPaymentSetting"))
    },
    MaterialPurchaseSettlementAndPaymentSetting: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/MaterialPurchaseSettlementAndPaymentSetting"))
    },
    MachineryLeasingSettlementAndPaymentSetting: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/MachineryLeasingSettlementAndPaymentSetting"))
    },
    MaterialLeasingSettlementAndPaymentSetting: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/MaterialLeasingSettlementAndPaymentSetting"))
    },
    OtherSettlementAndPaymentSettings: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/OtherSettlementAndPaymentSettings"))
    },
    CompletionOfAudit: {
        mustLogin: true,
        Com: lazy(() => import("./SettlementManagement/CompletionOfAudit"))
    },
    //待办
    TodoHasTo: {
        mustLogin: true,
        Com: lazy(() => import("./TodoHasTo/index"))
    },

    //物资管理
    ImportTaxWithdrawal: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/ImportTaxWithdrawal"))
    },
    ImportTaxOutTicket: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/ImportTaxOutTicket"))
    },
    //信用评价
    ProfessionalClass: {
        mustLogin: true,
        Com: lazy(() => import("./CreditEvaluation/ProfessionalClass"))
    },
    StatusStandard: {
        mustLogin: true,
        Com: lazy(() => import("./CreditEvaluation/StatusStandard"))
    },
    ProjectClass: {
        mustLogin: true,
        Com: lazy(() => import("./CreditEvaluation/ProjectClass"))
    },
    FirmQuality: {
        mustLogin: true,
        Com: lazy(() => import("./CreditEvaluation/FirmQuality"))
    },
    ProjectCreditScore: {
        mustLogin: true,
        Com: lazy(() => import("./CreditEvaluation/ProjectCreditScore"))
    },
    ProfessionalClassBin: {
        mustLogin: true,
        Com: lazy(() => import("./CreditEvaluation/ProfessionalClassBin"))
    },
    StatusStandardLevel: {
        mustLogin: true,
        Com: lazy(() => import("./CreditEvaluation/StatusStandardLevel"))
    },

    //核算支付
    PrimalBuildContractData: {
        mustLogin: true,
        Com: lazy(() => import("./CountPay/PrimalBuildContractData"))
    },
    CurrentBuildContractData: {
        mustLogin: true,
        Com: lazy(() => import("./CountPay/CurrentBuildContractData"))
    },
    BuildPrimalProjectList: {
        mustLogin: true,
        Com: lazy(() => import("./CountPay/BuildPrimalProjectList"))
    },
    ConstructionScheme: {
        mustLogin: true,
        Com: lazy(() => import("./ProjectMangerment/ConstructionScheme"))
    },
    BuildCurrentProjectList: {
        mustLogin: true,
        Com: lazy(() => import("./CountPay/BuildCurrentProjectList"))
    },
    PrimalBuildContract: {
        mustLogin: true,
        Com: lazy(() => import("./CountPay/PrimalBuildContract"))
    },
    CurrentBuildContract: {
        mustLogin: true,
        Com: lazy(() => import("./CountPay/CurrentBuildContract"))
    },
    //结算支付 -- 建造合同台账
    ConstructionContractLedger: {
        mustLogin: true,
        Com: lazy(() => import("./CountPay/ConstructionContractLedger"))
    },

    ImportTaxTransferOrder: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/ImportTaxTransferOrder"))
    },
    ImportTaxWinningOrder: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/ImportTaxWinningOrder"))
    },
    ImportTaxOutOrder: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/ImportTaxOutOrder"))
    },
    ImportTaxOffsetSheet: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/ImportTaxOffsetSheet"))
    },
    ImportTaxWarehouseInventory: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/ImportTaxWarehouseInventory"))
    },
    ImportTaxCollaborationTeam: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/ImportTaxCollaborationTeam"))
    },
    ImportTaxPurchaseAccount: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/ImportTaxPurchaseAccount"))
    },
    PurchaseApplication: {
        mustLogin: true,
        Com: lazy(() => import("./TurnoverMaterialManagement/PurchaseApplication"))
    },
    Warehousing: {
        mustLogin: true,
        Com: lazy(() => import("./TurnoverMaterialManagement/Warehousing"))
    },
    WarehouseOut: {
        mustLogin: true,
        Com: lazy(() => import("./TurnoverMaterialManagement/WarehouseOut/index"))
    },
    Allocation: {
        mustLogin: true,
        Com: lazy(() => import("./TurnoverMaterialManagement/Allocation"))
    },
    ReturnGoods: {
        mustLogin: true,
        Com: lazy(() => import("./TurnoverMaterialManagement/ReturnGoods"))
    },
    Scrap: {
        mustLogin: true,
        Com: lazy(() => import("./TurnoverMaterialManagement/Scrap"))
    },
    AmortizationOfWeeklyTimber: {
        mustLogin: true,
        Com: lazy(() => import("./TurnoverMaterialManagement/AmortizationOfWeeklyTimber"))
    },
    ImportTaxOffsetSheetTur: {
        mustLogin: true,
        Com: lazy(() => import("./TurnoverMaterialManagement/ImportTaxOffsetSheetTur"))
    },
    WasteMaterialsManagement: {
        mustLogin: true,
        Com: lazy(() => import("./TurnoverMaterialManagement/WasteMaterialsManagement"))
    },
    MaterialPriceMonth: {
        mustLogin: true,
        Com: lazy(() => import("./TurnoverMaterialManagement/MaterialPriceMonth"))
    },
    ConstructionCooperationUnit: {
        mustLogin: true,
        Com: lazy(() => import("./CooperationUnitManagement/ConstructionCooperationUnit"))
    },
    //核算支付/预算管理/项目工程类别划分
    ProjectEngineeringCategory: {
        mustLogin: true,
        Com: lazy(() => import("./budget/ProjectEngineeringCategory"))
    },
    //核算支付/预算标准/折算系数
    ConvertCoefficient: {
        mustLogin: true,
        Com: lazy(() => import("./budget/ConvertCoefficient"))
    },
    //核算支付/预算标准/工程类别划分标准
    ProjectEngineeringCategoryCriterion: {
        mustLogin: true,
        Com: lazy(() => import("./budget/ProjectEngineeringCategoryCriterion"))
    },
    //核算支付/预算标准/四项费用标准
    ExpenseStandard: {
        mustLogin: true,
        Com: lazy(() => import("./budget/ExpenseStandard"))
    },
    //核算支付/预算标准/标准清单库
    StandardQdwarehouse: {
        mustLogin: true,
        Com: lazy(() => import("./budget/StandardQdwarehouse"))
    },
    //核算支付/预算标准/标准清单工序
    StandardQdwarehouseProcess: {
        mustLogin: true,
        Com: lazy(() => import("./budget/StandardQdwarehouseProcess"))
    },
    //核算支付/预算管理/清单管理
    Qdrelevance: {
        mustLogin: true,
        Com: lazy(() => import("./budget/Qdrelevance"))
    },
    //核算支付/项目信息/预算管理项目信息
    BudgetProjectInformation: {
        mustLogin: true,
        Com: lazy(() => import("./budget/BudgetProjectInformation"))
    },
    //核算支付/预算管理/标后预算/标后材料价格表
    AfterMaterial: {
        mustLogin: true,
        Com: lazy(() => import("./budget/AfterMaterial"))
    },
    //核算支付/预算管理/标后预算/标后复合材料价格表
    AfterRecombinationMaterial: {
        mustLogin: true,
        Com: lazy(() => import("./budget/AfterRecombinationMaterial"))
    },
    //核算支付/预算管理/标后预算/标后材料关联
    AfterMaterialRelevance: {
        mustLogin: true,
        Com: lazy(() => import("./budget/AfterMaterialRelevance"))
    },
    //核算支付/预算管理/标后预算/标后预算编制
    AfterBudgeting: {
        mustLogin: true,
        Com: lazy(() => import("./budget/AfterBudgeting"))
    },
    //核算支付/预算管理/标后预算/标后预算编制/明细
    AfterBudgetingDetial: {
        mustLogin: true,
        Com: lazy(() => import("./budget/AfterBudgetingDetial"))
    },
    //核算支付/预算管理/标后预算/标后预算编制/工程量清单综合基价费用
    AfterBudgetingProjectCost: {
        mustLogin: true,
        Com: lazy(() => import("./budget/AfterBudgetingProjectCost"))
    },
    //核算支付/预算管理/标后预算/标后预算编制/现场文明施工费
    AfterBudgetingSiteCost: {
        mustLogin: true,
        Com: lazy(() => import("./budget/AfterBudgetingSiteCost"))
    },
    //核算支付/预算管理/标后预算/标后预算编制/临时设施费
    AfterBudgetingFacilityCost: {
        mustLogin: true,
        Com: lazy(() => import("./budget/AfterBudgetingFacilityCost"))
    },
    //核算支付/预算管理/标后预算/标后预算编制/经理部管理费
    AfterBudgetingIntendanceCost: {
        mustLogin: true,
        Com: lazy(() => import("./budget/AfterBudgetingIntendanceCost"))
    },
    //核算支付/预算管理/施工预算/标后材料价格表
    ConstructionMaterial: {
        mustLogin: true,
        Com: lazy(() => import("./budget/ConstructionMaterial"))
    },
    //核算支付/预算管理/施工预算/标后复合材料价格表
    ConstructionRecombinationMaterial: {
        mustLogin: true,
        Com: lazy(() => import("./budget/ConstructionRecombinationMaterial"))
    },
    //核算支付/预算管理/施工预算/标后材料关联
    ConstructionMaterialRelevance: {
        mustLogin: true,
        Com: lazy(() => import("./budget/ConstructionMaterialRelevance"))
    },
    //核算支付/预算管理/施工预算/标后预算编制
    ConstructionBudgeting: {
        mustLogin: true,
        Com: lazy(() => import("./budget/ConstructionBudgeting"))
    },
    //核算支付/预算管理/施工预算/标后预算编制/期次
    ConstructionBudgetingIssue: {
        mustLogin: true,
        Com: lazy(() => import("./budget/ConstructionBudgetingIssue"))
    },
    //核算支付/预算管理/施工预算/标后预算编制/明细
    ConstructionBudgetingDetial: {
        mustLogin: true,
        Com: lazy(() => import("./budget/ConstructionBudgetingDetial"))
    },
    //核算支付/预算管理/施工预算/标后预算编制/明细
    ConstructionBudgetingDetialList: {
        mustLogin: true,
        Com: lazy(() => import("./budget/ConstructionBudgetingDetialList"))
    },
    //核算支付/预算管理/施工预算/标后预算编制/工程量清单内工程
    ConstructionBudgetingProjectCost: {
        mustLogin: true,
        Com: lazy(() => import("./budget/ConstructionBudgetingProjectCost"))
    },
    //核算支付/预算管理/施工预算/标后预算编制/现场文明施工费
    ConstructionBudgetingSiteCost: {
        mustLogin: true,
        Com: lazy(() => import("./budget/ConstructionBudgetingSiteCost"))
    },
    //核算支付/预算管理/施工预算/标后预算编制/临时设施费
    ConstructionBudgetingFacilityCost: {
        mustLogin: true,
        Com: lazy(() => import("./budget/ConstructionBudgetingFacilityCost"))
    },
    //核算支付/预算管理/施工预算/标后预算编制/经理部管理费
    ConstructionBudgetingIntendanceCost: {
        mustLogin: true,
        Com: lazy(() => import("./budget/ConstructionBudgetingIntendanceCost"))
    },
    EquipmentManufacturer: {
        mustLogin: true,
        Com: lazy(() => import("./CooperationUnitManagement/EquipmentManufacturer"))
    },
    EquipmentLessors: {
        mustLogin: true,
        Com: lazy(() => import("./CooperationUnitManagement/EquipmentLessors"))
    },
    EquipmentDealers: {
        mustLogin: true,
        Com: lazy(() => import("./CooperationUnitManagement/EquipmentDealers"))
    },
    MaterialManufacturer: {
        mustLogin: true,
        Com: lazy(() => import("./CooperationUnitManagement/MaterialManufacturer"))
    },
    MaterialRenter: {
        mustLogin: true,
        Com: lazy(() => import("./CooperationUnitManagement/MaterialRenter"))
    },
    MaterialDistributor: {
        mustLogin: true,
        Com: lazy(() => import("./CooperationUnitManagement/MaterialDistributor"))
    },
    OtherCollaborationUnits: {
        mustLogin: true,
        Com: lazy(() => import("./CooperationUnitManagement/OtherCollaborationUnits"))
    },
    SupplierAccount: {
        mustLogin: true,
        Com: lazy(() => import("./CooperationUnitManagement/SupplierAccount"))
    },
    BasicInformationRegistrationOfCooperativeUnits: {
        mustLogin: true,
        Com: lazy(() => import("./CreditEvaluation/BasicInformationRegistrationOfCooperativeUnits"))
    },
    ProjectCreditEvaluation: {
        mustLogin: true,
        Com: lazy(() => import("./CreditEvaluation/ProjectCreditEvaluation"))
    },
    QuarterlyCreditEvaluation: {
        mustLogin: true,
        Com: lazy(() => import("./CreditEvaluation/QuarterlyCreditEvaluation"))
    },
    //安全管理
    //安全目标计划
    SafePlanTarget: {
        mustLogin: true,
        Com: lazy(() => import("./safe/planTarget"))
    },
    SpecialOperationsList: {
        mustLogin: true,
        Com: lazy(() => import("./safe/SpecialOperationsList"))
    },
    ContingencyPlan: {
        mustLogin: true,
        Com: lazy(() => import("./safe/ContingencyPlan"))
    },
    DangerousSourceDatabase: {
        mustLogin: true,
        Com: lazy(() => import("./safe/DangerousSourceDatabase"))
    },
    SafetyOrganization: {
        mustLogin: true,
        Com: lazy(() => import("./safe/SafetyOrganization"))
    },
    SafeCheckProject: {
        mustLogin: true,
        Com: lazy(() => import("./safe/SafeCheckProject"))
    },
    //------------------------------
    // 安全检查查询
    SecurityCheckQuery: {
        mustLogin: true,
        Com: lazy(() => import("./safe/SecurityCheckQuery"))
    },
    // 安全费用查询
    SecurityFeeQuery: {
        mustLogin: true,
        Com: lazy(() => import("./safe/SecurityFeeQuery"))
    },
    // 安全教育培训查询
    SafetyEducationTrainingInquiry: {
        mustLogin: true,
        Com: lazy(() => import("./safe/SafetyEducationTrainingInquiry"))
    },
    // 安全人员检查（学历）
    SafetyPersonnelInspectionEducation: {
        mustLogin: true,
        Com: lazy(() => import("./safe/SafetyPersonnelInspectionEducation"))
    },
    // 安全人员检查（职称）
    SecurityPersonnelInquiryProfessionalTitle: {
        mustLogin: true,
        Com: lazy(() => import("./safe/SecurityPersonnelInquiryProfessionalTitle"))
    },
    // 安全人员检查（工作年限）
    SecurityPersonnelInquiryWorkingYears: {
        mustLogin: true,
        Com: lazy(() => import("./safe/SecurityPersonnelInquiryWorkingYears"))
    },
    // 安全目标计划
    SafetyTargetPlan: {
        mustLogin: true,
        Com: lazy(() => import("./safe/SafetyTargetPlan"))
    },

    // 报表
    // 项目安全检查汇总表
    SummaryProjectSafetyInspection: {
        mustLogin: true,
        Com: lazy(() => import("./safe/SummaryProjectSafetyInspection"))
    },
    // 重大危险源台账（项目）
    MajorHazardAccountProject: {
        mustLogin: true,
        Com: lazy(() => import("./safe/MajorHazardAccountProject"))
    },
    // 危险源台账（公司）
    HazardSourceAccountCompany: {
        mustLogin: true,
        Com: lazy(() => import("./safe/HazardSourceAccountCompany"))
    },
    // 特种作业人员统计表（项目）
    StatisticalTableSpecialOperationPersonnelItem: {
        mustLogin: true,
        Com: lazy(() => import("./safe/StatisticalTableSpecialOperationPersonnelItem"))
    },
    // 特种作业人员统计表（公司）
    StatisticsSpecialOperationPersonnelCompany: {
        mustLogin: true,
        Com: lazy(() => import("./safe/StatisticsSpecialOperationPersonnelCompany"))
    },
    // 特种设备台账（公司）
    SpecialEquipmentAccountCompany: {
        mustLogin: true,
        Com: lazy(() => import("./safe/SpecialEquipmentAccountCompany"))
    },
    // 伤亡事故情况统计分析表（项目）
    StatisticalAnalysisCasualtyAccidentsProject: {
        mustLogin: true,
        Com: lazy(() => import("./safe/StatisticalAnalysisCasualtyAccidentsProject"))
    },
    // 伤亡事故情况统计分析表（公司）
    StatisticalAnalysisCasualtiesCompany: {
        mustLogin: true,
        Com: lazy(() => import("./safe/StatisticalAnalysisCasualtiesCompany"))
    },
    // 企业职工伤亡事故年度统计分析表（项目）
    AnnualStatisticalAnalysisTableCasualtyAccidentsEnterpriseEmployeesProject: {
        mustLogin: true,
        Com: lazy(() => import("./safe/AnnualStatisticalAnalysisTableCasualtyAccidentsEnterpriseEmployeesProject"))
    },
    // 企业职工伤亡事故年度统计分析表（公司）
    AnnualStatisticalAnalysisTableCasualtyAccidentsEnterpriseEmployeesCompany: {
        mustLogin: true,
        Com: lazy(() => import("./safe/AnnualStatisticalAnalysisTableCasualtyAccidentsEnterpriseEmployeesCompany"))
    },
    // 船舶水上交通事故统计表（项目）
    StatisticalTableMarineTrafficAccidentsItem: {
        mustLogin: true,
        Com: lazy(() => import("./safe/StatisticalTableMarineTrafficAccidentsItem"))
    },
    // 船舶水上交通事故统计表（公司）
    StatisticalTableMarineTrafficAccidentsCompany: {
        mustLogin: true,
        Com: lazy(() => import("./safe/StatisticalTableMarineTrafficAccidentsCompany"))
    },
    // 安全教育花名册
    SafetyEducationRoster: {
        mustLogin: true,
        Com: lazy(() => import("./safe/SafetyEducationRoster"))
    },
    // 安全管理人员统计表（项目）
    StatisticalTableSafetyManagementPersonnelProject: {
        mustLogin: true,
        Com: lazy(() => import("./safe/StatisticalTableSafetyManagementPersonnelProject"))
    },
    // 安全管理人员统计表（公司）
    StatisticalTableSafetyManagementPersonnelCompany: {
        mustLogin: true,
        Com: lazy(() => import("./safe/StatisticalTableSafetyManagementPersonnelCompany"))
    },
    //------------------------------
    SafeCheckCompany: {
        mustLogin: true,
        Com: lazy(() => import("./safe/SafeCheckCompany"))
    },
    SafeCheckJu: {
        mustLogin: true,
        Com: lazy(() => import("./safe/SafeCheckJu"))
    },
    DangerousSourceBigProject: {
        mustLogin: true,
        Com: lazy(() => import("./safe/DangerousSourceBigProject"))
    },
    DangerousSourceBigJu: {
        mustLogin: true,
        Com: lazy(() => import("./safe/DangerousSourceBigJu"))
    },
    DangerousSourceBigCompany: {
        mustLogin: true,
        Com: lazy(() => import("./safe/DangerousSourceBigCompany"))
    },
    SafetyEducationAndTraining: {
        mustLogin: true,
        Com: lazy(() => import("./safe/SafetyEducationAndTraining"))
    },
    SafetyAssessOfCooperationTeam: {
        mustLogin: true,
        Com: lazy(() => import("./safe/SafetyAssessOfCooperationTeam"))
    },
    ProjectSpecialEquipmentAccount: {
        mustLogin: true,
        Com: lazy(() => import("./safe/ProjectSpecialEquipmentAccount"))
    },
    SafetyManagementPersonnel: {
        mustLogin: true,
        Com: lazy(() => import("./safe/SafetyManagementPersonnel"))
    },
    SafetyOrganizationSeason: {
        mustLogin: true,
        Com: lazy(() => import("./safe/SafetyOrganizationSeason"))
    },
    SafetyAccident: {
        mustLogin: true,
        Com: lazy(() => import("./safe/SafetyAccident"))
    },
    BureauAssessmentDatabase: {
        mustLogin: true,
        Com: lazy(() => import("./safe/BureauAssessmentDatabase"))
    },
    SafetyPrice: {
        mustLogin: true,
        Com: lazy(() => import("./safe/SafetyPrice"))
    },
    SafetyManagementAssessment: {
        mustLogin: true,
        Com: lazy(() => import("./safe/SafetyManagementAssessment"))
    },
    CodeBase: {
        mustLogin: true,
        Com: lazy(() => import("./safe/CodeBase"))
    },
    QuarterlyCreditEvaluationJu: {
        mustLogin: true,
        Com: lazy(() => import("./CreditEvaluation/QuarterlyCreditEvaluationJu"))
    },
    ProjectCreditEvaluationForQuarterly: {
        mustLogin: true,
        Com: lazy(() => import("./CreditEvaluation/ProjectCreditEvaluationForQuarterly"))
    },
    CompanyCreditEvaluationForHalfYears: {
        mustLogin: true,
        Com: lazy(() => import("./CreditEvaluation/CompanyCreditEvaluationForHalfYears"))
    },
    JuCrediEvaluationForYears: {
        mustLogin: true,
        Com: lazy(() => import("./CreditEvaluation/JuCrediEvaluationForYears"))
    },
    JuCreduEvaluationForOverYears: {
        mustLogin: true,
        Com: lazy(() => import("./CreditEvaluation/JuCreduEvaluationForOverYears"))
    },
    JuAAForm: {
        mustLogin: true,
        Com: lazy(() => import("./CreditEvaluation/JuAAForm"))
    },
    BelowStandardListForTeam: {
        mustLogin: true,
        Com: lazy(() => import("./CreditEvaluation/BelowStandardListForTeam"))
    },
    NoEvaluationListForQuarter: {
        mustLogin: true,
        Com: lazy(() => import("./CreditEvaluation/NoEvaluationListForQuarter"))
    },
    JuABCMenu: {
        mustLogin: true,
        Com: lazy(() => import("./CreditEvaluation/JuABCMenu"))
    },
    HighwayEngine: {
        mustLogin: true,
        Com: lazy(() => import("./CostControl/CostDatabase/HighwayEngine"))
    },
    ProjectPriceLimitReport: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/aReport/ProjectPriceLimitReport"))
    },
    CompanyVolumeDifferenceSummaryOne: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/aReport/CompanyVolumeDifferenceSummaryOne"))
    },
    CompanyVolumeDifferenceSummaryProject: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/aReport/CompanyVolumeDifferenceSummaryProject"))
    },
    CompanyVolumeDifferenceSummarytouzi: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/aReport/CompanyVolumeDifferenceSummaryProject"))
    },
    ProjectMonthlyMeasurementDifferenceTable: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/aReport/ProjectMonthlyMeasurementDifferenceTable"))
    },
    QuarterlyProjectVolumeDifferenceTable: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/aReport/QuarterlyProjectVolumeDifferenceTable"))
    },
    ReceiptAndStoreMaterialQuarterlyReport: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/aReport/ReceiptAndStoreMaterialQuarterlyReport"))
    },
    AmortizationLedgerOfRevolvingMaterials: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/aReport/AmortizationLedgerOfRevolvingMaterials"))
    },
    ContrastMaterialContractAndPurchaseLedger: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/aReport/ContrastMaterialContractAndPurchaseLedger"))
    },
    StatisticalReportOfRevolvingMaterials: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/aReport/StatisticalReportOfRevolvingMaterials"))
    },
    QuarterlyStatisticalReportOnMaterialReceiptAndStorageOfCCCC: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/aReport/QuarterlyStatisticalReportOnMaterialReceiptAndStorageOfCCCC"))
    },
    ApprovalLedgerForWasteMaterials: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/aReport/ApprovalLedgerForWasteMaterials"))
    },
    MainMaterReport: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/aReport/MainMaterReport"))
    },
    MaterBuyReport: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/aReport/MaterBuyReport"))
    },
    MaterRunReport: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/aReport/MaterRunReport"))
    },
    MaterResMoveMonthSMPReport: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/aReport/MaterResMoveMonthSMPReport"))
    },
    MaterRialReport: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/aReport/MaterRialReport"))
    },
    MaterUseReport: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/aReport/MaterUseReport"))
    },
    MaterMonthUseReport: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/aReport/MaterMonthUseReport"))
    },
    // MaterialReceiptLedgerReport: {
    //     mustLogin: true,
    //     Com: lazy(() => import("./MaterialsManagement/MaterialReceiptLedgerReport"))
    // },
    MaterRuKuReport: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/aReport/MaterRuKuReport"))
    },
    MaterTotalReport: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/aReport/MaterTotalReport"))
    },
    LingliaoDanWei: {
        mustLogin: true,
        Com: lazy(() => import("./MaterialsManagement/LingliaoDanWei"))
    },
    SubcontractingPriceLimit: {
        mustLogin: true,
        Com: lazy(() => import("./ProjectMangerment/SubcontractingPriceLimit"))
    },
    //综合管理
    Agenda: {
        mustLogin: true,
        Com: lazy(() => import("./IntegratedManagement/Agenda"))
    },
    DatabaseManagement: {
        mustLogin: true,
        Com: lazy(() => import("./IntegratedManagement/DatabaseManagement"))
    },
    InterimPaymentItemSettings: {
        mustLogin: true,
        Com: lazy(() => import("./sysTem/InterimPaymentItemSettings"))
    },

};

//遍历所有页面强制让页面包裹basic
//不需要包裹baise的页面key
let noIncs = ["Login"];
for (const key in pageComs) {
    if (pageComs.hasOwnProperty(key)) {
        const element = pageComs[key];
        if (!noIncs.includes(key) && element.mustLogin) {
            element.Com = basic(element.Com);
            pageComs[key] = element;
        }
    }
}

const routerInfo = [
    {
        label: '首页',
        children: [
            {
                label: "待办",
                defaultPath: "TodoList",
                pathName: "TodoList",
                comKey: "TodoHasTo"
            },
            {
                label: "已办",
                defaultPath: "HasTodoList",
                pathName: "HasTodoList",
                comKey: "TodoHasTo"
            },
            {
                label: "未归档",
                defaultPath: "Undocumented",
                pathName: "Undocumented",
                comKey: "TodoHasTo"
            },
            {
                label: "已归档",
                defaultPath: "Closed",
                pathName: "Closed",
                comKey: "TodoHasTo"
            },

            // {
            //     label: "重大危险源(局)",
            //     defaultPath: "DangerousSourceBigJu",
            //     pathName: "DangerousSourceBigJu",
            //     comKey: "DangerousSourceBigJu"
            // },
            // {
            //     label: "中期支付项设置",
            //     defaultPath: "InterimPaymentItemSettings",
            //     pathName: "InterimPaymentItemSettings",
            //     comKey: "InterimPaymentItemSettings"
            // },
        ]
    },

    // {
    //     label: '流程设置',
    //     children: [
    //         {
    //             label: "流程执行人设置",
    //             defaultPath: "ExecutorSetting",
    //             pathName: "ExecutorSetting",
    //             comKey: "ExecutorSetting"
    //         },
    //         {
    //             label: "流程条件设置",
    //             defaultPath: "FlowCondition",
    //             pathName: "FlowCondition",
    //             comKey: "FlowCondition"
    //         }
    //     ]

    // },


    // {
    //     label: '物资报表',
    //     children: [
    //         // {
    //         //     label: "公司量差汇总表3（按投资事业部）",
    //         //     defaultPath: "CompanyVolumeDifferenceSummarytouzi",
    //         //     pathName: "CompanyVolumeDifferenceSummarytouzi",
    //         //     comKey: "CompanyVolumeDifferenceSummarytouzi"
    //         // },
    //         {
    //             label: "项目月度量差表",
    //             defaultPath: "ProjectMonthlyMeasurementDifferenceTable",
    //             pathName: "ProjectMonthlyMeasurementDifferenceTable",
    //             comKey: "ProjectMonthlyMeasurementDifferenceTable"
    //         },
    //         {
    //             label: "项目季度量差表",
    //             defaultPath: "QuarterlyProjectVolumeDifferenceTable",
    //             pathName: "QuarterlyProjectVolumeDifferenceTable",
    //             comKey: "QuarterlyProjectVolumeDifferenceTable"
    //         },
    //         {
    //             label: "公司量差汇总表1(按物资类别)",
    //             defaultPath: "CompanyVolumeDifferenceSummaryOne",
    //             pathName: "CompanyVolumeDifferenceSummaryOne",
    //             comKey: "CompanyVolumeDifferenceSummaryOne"
    //         },
    //         {
    //             label: "公司量差汇总表2(按项目)",
    //             defaultPath: "CompanyVolumeDifferenceSummaryProject",
    //             pathName: "CompanyVolumeDifferenceSummaryProject",
    //             comKey: "CompanyVolumeDifferenceSummaryProject"
    //         },
    //         {
    //             label: "物资收发存季度报表",
    //             defaultPath: "ReceiptAndStoreMaterialQuarterlyReport",
    //             pathName: "ReceiptAndStoreMaterialQuarterlyReport",
    //             comKey: "ReceiptAndStoreMaterialQuarterlyReport"
    //         },
    //         {
    //             label: "周转材料摊销明细帐",
    //             defaultPath: "AmortizationLedgerOfRevolvingMaterials",
    //             pathName: "AmortizationLedgerOfRevolvingMaterials",
    //             comKey: "AmortizationLedgerOfRevolvingMaterials"
    //         },
    //         {
    //             label: "物资合同与采购对比台账",
    //             defaultPath: "ContrastMaterialContractAndPurchaseLedger",
    //             pathName: "ContrastMaterialContractAndPurchaseLedger",
    //             comKey: "ContrastMaterialContractAndPurchaseLedger"
    //         },
    //         {
    //             label: "周转材料统计报表",
    //             defaultPath: "StatisticalReportOfRevolvingMaterials",
    //             pathName: "StatisticalReportOfRevolvingMaterials",
    //             comKey: "StatisticalReportOfRevolvingMaterials"
    //         },
    //         {
    //             label: "中交物资收发存季度统计报表",
    //             defaultPath: "QuarterlyStatisticalReportOnMaterialReceiptAndStorageOfCCCC",
    //             pathName: "QuarterlyStatisticalReportOnMaterialReceiptAndStorageOfCCCC",
    //             comKey: "QuarterlyStatisticalReportOnMaterialReceiptAndStorageOfCCCC"
    //         },
    //         {
    //             label: "废旧物资审批台账",
    //             defaultPath: "ApprovalLedgerForWasteMaterials",
    //             pathName: "ApprovalLedgerForWasteMaterials",
    //             comKey: "ApprovalLedgerForWasteMaterials"
    //         },
    //     ]
    // },
    // {
    //     label: "资产资源",
    //     children: [
    //         {
    //             label: "设备管理",
    //             children: [
    //                 {
    //                     label: "分析查询",
    //                     children: [
    //                         {
    //                             label: "设备来源查询",
    //                             defaultPath: "SearchDeviceSource",
    //                             pathName: "SearchDeviceSource",
    //                             comKey: "SearchDeviceSource"
    //                         },
    //                     ]
    //                 },
    //                 {
    //                     label: "设备租赁限价管理",
    //                     children: [
    //                         {
    //                             label: "设备租赁限价采集",
    //                             defaultPath: "CollectionOfEquipmentLeasingPriceLimit",
    //                             pathName: "CollectionOfEquipmentLeasingPriceLimit",
    //                             comKey: "CollectionOfEquipmentLeasingPriceLimit"
    //                         },
    //                         // {
    //                         //     label: "全局设备租赁限价列表",
    //                         //     defaultPath: "GlobalEquipmentLeasingPriceLimit",
    //                         //     pathName: "GlobalEquipmentLeasingPriceLimit",
    //                         //     comKey: "GlobalEquipmentLeasingPriceLimit"
    //                         // }
    //                     ]
    //                 },
    //                 {
    //                     label: "公司设备管理",
    //                     children: [
    //                         {
    //                             label: "设备购置记录",
    //                             defaultPath: "EquipmentPurchaseRecord",
    //                             pathName: "EquipmentPurchaseRecord",
    //                             comKey: "EquipmentPurchaseRecord"
    //                         },
    //                         {
    //                             label: "公司设备台账",
    //                             defaultPath: "CompanyEquipmentLedger",
    //                             pathName: "CompanyEquipmentLedger",
    //                             comKey: "CompanyEquipmentLedger"
    //                         },
    //                         {
    //                             label: "设备异动管理",
    //                             defaultPath: "EquipmentAbnormalOperationManagement",
    //                             pathName: "EquipmentAbnormalOperationManagement",
    //                             comKey: "EquipmentAbnormalOperationManagement"
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     label: "项目设备管理",
    //                     children: [
    //                         {
    //                             label: "设备租赁管理",
    //                             defaultPath: "ManagementOfLeasedEquipment",
    //                             pathName: "ManagementOfLeasedEquipment",
    //                             comKey: "ManagementOfLeasedEquipment"
    //                         },
    //                         {
    //                             label: "协作单位设备入场登记",
    //                             defaultPath: "CooperativeUnitsWithTheirOwnEquipmentToEnterTheRegistration",
    //                             pathName: "CooperativeUnitsWithTheirOwnEquipmentToEnterTheRegistration",
    //                             comKey: "CooperativeUnitsWithTheirOwnEquipmentToEnterTheRegistration"
    //                         },
    //                         {
    //                             label: "自有设备管理",
    //                             defaultPath: "TheirOwnEquipment",
    //                             pathName: "TheirOwnEquipment",
    //                             comKey: "TheirOwnEquipment"
    //                         },
    //                         {
    //                             label: "机械设备人员",
    //                             defaultPath: "MechanicalEquipmentPersonnel",
    //                             pathName: "MechanicalEquipmentPersonnel",
    //                             comKey: "MechanicalEquipmentPersonnel"
    //                         },
    //                         {
    //                             label: "设备应验收台账(月)",
    //                             defaultPath: "EquipmentVerificationLedger",
    //                             pathName: "EquipmentVerificationLedger",
    //                             comKey: "EquipmentVerificationLedger"
    //                         },
    //                         {
    //                             label: "设备实际验收台账(月)",
    //                             defaultPath: "ActualEquipmentAcceptanceLedger",
    //                             pathName: "ActualEquipmentAcceptanceLedger",
    //                             comKey: "ActualEquipmentAcceptanceLedger"
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     label: "基础设置",
    //                     children: [
    //                         {
    //                             label: "设备分类",
    //                             defaultPath: "ClassificationOfEquipment",
    //                             pathName: "ClassificationOfEquipment",
    //                             comKey: "ClassificationOfEquipment"
    //                         },
    //                         {
    //                             label: "设备ABCD分类",
    //                             defaultPath: "ClassificationOfABCEquipment",
    //                             pathName: "ClassificationOfABCEquipment",
    //                             comKey: "ClassificationOfABCEquipment"
    //                         },
    //                         {
    //                             label: "设备维保类型",
    //                             defaultPath: "TypeOfEquipmentMaintenance",
    //                             pathName: "TypeOfEquipmentMaintenance",
    //                             comKey: "TypeOfEquipmentMaintenance"
    //                         },
    //                         {
    //                             label: "设备来源",
    //                             defaultPath: "SourcesOfEquipment",
    //                             pathName: "SourcesOfEquipment",
    //                             comKey: "SourcesOfEquipment"
    //                         },
    //                         {
    //                             label: "职工人数设置",
    //                             defaultPath: "StaffingSet",
    //                             pathName: "StaffingSet",
    //                             comKey: "StaffingSet"
    //                         },
    //                         {
    //                             label: "机械人员岗位设置",
    //                             defaultPath: "PositionSettingOfMechanicalPersonnel",
    //                             pathName: "PositionSettingOfMechanicalPersonnel",
    //                             comKey: "PositionSettingOfMechanicalPersonnel"
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     label: "设备报表管理",
    //                     children: [
    //                         {
    //                             label: "租用外部机械设备使用情况表",
    //                             defaultPath: "MaterExternalEquipmentUsageReport",
    //                             pathName: "MaterExternalEquipmentUsageReport",
    //                             comKey: "MaterExternalEquipmentUsageReport"
    //                         },
    //                         {
    //                             label: "机械车辆使用情况报表（公司）",
    //                             defaultPath: "VehicleUsageCompanyReport",
    //                             pathName: "VehicleUsageCompanyReport",
    //                             comKey: "VehicleUsageCompanyReport"
    //                         },
    //                         {
    //                             label: "机械车辆使用情况报表（项目）",
    //                             defaultPath: "VehicleUsageProjectReport",
    //                             pathName: "VehicleUsageProjectReport",
    //                             comKey: "VehicleUsageProjectReport"
    //                         },
    //                         {
    //                             label: "自有车辆配备情况统计表",
    //                             defaultPath: "ZiYouCLForm",
    //                             pathName: "ZiYouCLForm",
    //                             comKey: "ZiYouCLForm"
    //                         }, {
    //                             label: "租赁车辆配备情况统计表",
    //                             defaultPath: "ZuLinCLForm",
    //                             pathName: "ZuLinCLForm",
    //                             comKey: "ZuLinCLForm"
    //                         }, {
    //                             label: "主要机械设备ABC情况表",
    //                             defaultPath: "AbcMachinery",
    //                             pathName: "AbcMachinery",
    //                             comKey: "AbcMachinery"
    //                         },
    //                         {
    //                             label: "设备租赁限价查询列表",
    //                             defaultPath: "EquipLimitPriceVOReport",
    //                             pathName: "EquipLimitPriceVOReport",
    //                             comKey: "EquipLimitPriceVOReport"
    //                         },
    //                         {
    //                             label: "机械设备人员表",
    //                             defaultPath: "EquipPesron",
    //                             pathName: "EquipPesron",
    //                             comKey: "EquipPesron"
    //                         },
    //                         {
    //                             label: "公司机械设备表",
    //                             defaultPath: "ComEquip",
    //                             pathName: "ComEquip",
    //                             comKey: "ComEquip"
    //                         }, {
    //                             label: "公司机械设备表",
    //                             defaultPath: "ComCar",
    //                             pathName: "ComCar",
    //                             comKey: "ComCar"
    //                         },

    //                     ]
    //                 },
    //             ]
    //         },
    //         {
    //             label: "物资管理",
    //             children: [
    //                 {
    //                     label: "物资计划管理",
    //                     children: [
    //                         {
    //                             label: "物资总需用计划",
    //                             defaultPath: "ImportTax",
    //                             pathName: "ImportTax",
    //                             comKey: "ImportTax"
    //                         },
    //                         {
    //                             label: "物资月度需用计划",
    //                             defaultPath: "ImportTaxMonth",
    //                             pathName: "ImportTaxMonth",
    //                             comKey: "ImportTaxMonth"
    //                         },
    //                         {
    //                             label: "物资月度采购计划",
    //                             defaultPath: "ImportTaxCatch",
    //                             pathName: "ImportTaxCatch",
    //                             comKey: "ImportTaxCatch"
    //                         },
    //                         {
    //                             label: "项目零星采购",
    //                             defaultPath: "ImportTaxPurchase",
    //                             pathName: "ImportTaxPurchase",
    //                             comKey: "ImportTaxPurchase"
    //                         },
    //                         {
    //                             label: "物资总需用计划变更",
    //                             defaultPath: "ImportTaxChange",
    //                             pathName: "ImportTaxChange",
    //                             comKey: "ImportTaxChange"
    //                         },

    //                     ]
    //                 },
    //                 {
    //                     label: "物资限价管理",
    //                     children: [
    //                         {
    //                             label: "限价数据采集",
    //                             defaultPath: "ImportPriceLimitManage",
    //                             pathName: "ImportPriceLimitManage",
    //                             comKey: "ImportPriceLimitManage"
    //                         },
    //                         {
    //                             label: "限价数据调整",
    //                             defaultPath: "ImportPriceLimitChange/0",
    //                             pathName: "ImportPriceLimitChange/:limitNo",
    //                             comKey: "ImportPriceLimitChange",
    //                         },
    //                         {
    //                             label: "项目限价明细报表",
    //                             defaultPath: "ProjectPriceLimitReport",
    //                             pathName: "ProjectPriceLimitReport",
    //                             comKey: "ProjectPriceLimitReport",
    //                         }

    //                     ]
    //                 },
    //                 {
    //                     label: "物资收发存管理",
    //                     children: [
    //                         {
    //                             label: "预收初始单",
    //                             defaultPath: "ImportTaxCollectTicket",
    //                             pathName: "ImportTaxCollectTicket",
    //                             comKey: "ImportTaxCollectTicket"
    //                         },
    //                         {
    //                             label: "收料单",
    //                             defaultPath: "ImportTaxMaterialReceipt",
    //                             pathName: "ImportTaxMaterialReceipt",
    //                             comKey: "ImportTaxMaterialReceipt"
    //                         },
    //                         {
    //                             label: "领料单",
    //                             defaultPath: "ImportTaxMaterialRequisition",
    //                             pathName: "ImportTaxMaterialRequisition",
    //                             comKey: "ImportTaxMaterialRequisition"
    //                         },
    //                         {
    //                             label: "库存",
    //                             defaultPath: "ImportTaxWarehouse",
    //                             pathName: "ImportTaxWarehouse",
    //                             comKey: "ImportTaxWarehouse"
    //                         },
    //                         {
    //                             label: "退库单",
    //                             defaultPath: "ImportTaxWithdrawal",
    //                             pathName: "ImportTaxWithdrawal",
    //                             comKey: "ImportTaxWithdrawal"
    //                         },
    //                         {
    //                             label: "退货单",
    //                             defaultPath: "ImportTaxOutTicket",
    //                             pathName: "ImportTaxOutTicket",
    //                             comKey: "ImportTaxOutTicket"
    //                         },
    //                         {
    //                             label: "调拨单",
    //                             defaultPath: "ImportTaxTransferOrder",
    //                             pathName: "ImportTaxTransferOrder",
    //                             comKey: "ImportTaxTransferOrder"
    //                         },
    //                         {
    //                             label: "盘盈入库单",
    //                             defaultPath: "ImportTaxWinningOrder",
    //                             pathName: "ImportTaxWinningOrder",
    //                             comKey: "ImportTaxWinningOrder"
    //                         },
    //                         {
    //                             label: "盘亏出库单",
    //                             defaultPath: "ImportTaxOutOrder",
    //                             pathName: "ImportTaxOutOrder",
    //                             comKey: "ImportTaxOutOrder"
    //                         },
    //                         {
    //                             label: "冲账单",
    //                             defaultPath: "ImportTaxOffsetSheet",
    //                             pathName: "ImportTaxOffsetSheet",
    //                             comKey: "ImportTaxOffsetSheet"
    //                         },
    //                         {
    //                             label: "仓库盘点",
    //                             defaultPath: "ImportTaxWarehouseInventory",
    //                             pathName: "ImportTaxWarehouseInventory",
    //                             comKey: "ImportTaxWarehouseInventory"
    //                         },
    //                         {
    //                             label: "协作队伍仓库盘点",
    //                             defaultPath: "ImportTaxCollaborationTeam",
    //                             pathName: "ImportTaxCollaborationTeam",
    //                             comKey: "ImportTaxCollaborationTeam"
    //                         },
    //                         {
    //                             label: "进货台账",
    //                             defaultPath: "ImportTaxPurchaseAccount",
    //                             pathName: "ImportTaxPurchaseAccount",
    //                             comKey: "ImportTaxPurchaseAccount"
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     label: "周转材料管理",
    //                     children: [
    //                         {
    //                             label: "购置申请",
    //                             defaultPath: "PurchaseApplication",
    //                             pathName: "PurchaseApplication",
    //                             comKey: "PurchaseApplication"
    //                         },
    //                         {
    //                             label: "入库",
    //                             defaultPath: "Warehousing",
    //                             pathName: "Warehousing",
    //                             comKey: "Warehousing"
    //                         },
    //                         {
    //                             label: "出库",
    //                             defaultPath: "WarehouseOut",
    //                             pathName: "WarehouseOut",
    //                             comKey: "WarehouseOut"
    //                         },
    //                         {
    //                             label: "调拨",
    //                             defaultPath: "Allocation",
    //                             pathName: "Allocation",
    //                             comKey: "Allocation"
    //                         },
    //                         {
    //                             label: "退货",
    //                             defaultPath: "ReturnGoods",
    //                             pathName: "ReturnGoods",
    //                             comKey: "ReturnGoods"
    //                         },
    //                         {
    //                             label: "报废",
    //                             defaultPath: "Scrap",
    //                             pathName: "Scrap",
    //                             comKey: "Scrap"
    //                         },
    //                         {
    //                             label: "周材摊销",
    //                             defaultPath: "AmortizationOfWeeklyTimber",
    //                             pathName: "AmortizationOfWeeklyTimber",
    //                             comKey: "AmortizationOfWeeklyTimber"
    //                         },
    //                         // {
    //                         //     label: "冲账单",
    //                         //     defaultPath: "ImportTaxOffsetSheetTur",
    //                         //     pathName: "ImportTaxOffsetSheetTur",
    //                         //     comKey: "ImportTaxOffsetSheetTur"
    //                         // },


    //                     ]
    //                 },
    //                 {
    //                     label: "废旧物资管理",
    //                     children: [
    //                         {
    //                             label: "废旧物资管理",
    //                             defaultPath: "WasteMaterialsManagement",
    //                             pathName: "WasteMaterialsManagement",
    //                             comKey: "WasteMaterialsManagement"
    //                         },
    //                         {
    //                             label: "废旧物资处理台账",
    //                             defaultPath: "WornOutList",
    //                             pathName: "WornOutList",
    //                             comKey: "WornOutList"
    //                         },

    //                     ]
    //                 },
    //                 {
    //                     label: "物资报表管理",
    //                     children: [
    //                         {
    //                             label: "主要物资收、发、存统计报表",
    //                             defaultPath: "MainMaterReport",
    //                             pathName: "MainMaterReport",
    //                             comKey: "MainMaterReport"
    //                         },
    //                         {
    //                             label: "物资购进、消费与库存总值表",
    //                             defaultPath: "MaterBuyReport",
    //                             pathName: "MaterBuyReport",
    //                             comKey: "MaterBuyReport"
    //                         },
    //                         {
    //                             label: "物资动态账",
    //                             defaultPath: "MaterRunReport",
    //                             pathName: "MaterRunReport",
    //                             comKey: "MaterRunReport"
    //                         },
    //                         {
    //                             label: "物资收发存汇总月报表",
    //                             defaultPath: "MaterResMoveMonthSMPReport",
    //                             pathName: "MaterResMoveMonthSMPReport",
    //                             comKey: "MaterResMoveMonthSMPReport"
    //                         },
    //                         {
    //                             label: "收料单台账",
    //                             defaultPath: "MaterRialReport",
    //                             pathName: "MaterRialReport",
    //                             comKey: "MaterRialReport"
    //                         },
    //                         {
    //                             label: "物资合同与采购对比台账",
    //                             defaultPath: "MaterContractContrastRptReport",
    //                             pathName: "MaterContractContrastRptReport",
    //                             comKey: "MaterContractContrastRptReport"
    //                         },
    //                         {
    //                             label: "物资耗用分配表",
    //                             defaultPath: "MaterUseReport",
    //                             pathName: "MaterUseReport",
    //                             comKey: "MaterUseReport"
    //                         },
    //                         {
    //                             label: "物资收发存月报表",
    //                             defaultPath: "MaterMonthUseReport",
    //                             pathName: "MaterMonthUseReport",
    //                             comKey: "MaterMonthUseReport"
    //                         },
    //                         {
    //                             label: "中交物资收发存季度统计报表",
    //                             defaultPath: "MaterStockTransferRptNewReport",
    //                             pathName: "MaterStockTransferRptNewReport",
    //                             comKey: "MaterStockTransferRptNewReport"
    //                         },
    //                         {
    //                             label: "物资月度需用计划汇总表",//???
    //                             defaultPath: "PuMMReqPlan",
    //                             pathName: "PuMMReqPlan",
    //                             comKey: "PuMMReqPlan"
    //                         },
    //                         {
    //                             label: "项目物资出库台账",//???
    //                             defaultPath: "ResourceOutList",
    //                             pathName: "ResourceOutList",
    //                             comKey: "ResourceOutList"
    //                         },
    //                         {
    //                             label: "物资收发存季度报表",//???
    //                             defaultPath: "StockTransferRpt",
    //                             pathName: "StockTransferRpt",
    //                             comKey: "StockTransferRpt"
    //                         },
    //                         // {
    //                         //     label: "收料单台账",//???
    //                         //     defaultPath: "MaterialReceiptLedgerReport",
    //                         //     pathName: "MaterialReceiptLedgerReport",
    //                         //     comKey: "MaterialReceiptLedgerReport"
    //                         // },
    //                         // {
    //                         //     label: "物资收发存汇总月报表",
    //                         //     defaultPath: "MaterUseReport",
    //                         //     pathName: "MaterUseReport",
    //                         //     comKey: "MaterUseReport"
    //                         // },
    //                     ]
    //                 },
    //                 {
    //                     label: "周转材报表管理",
    //                     children: [
    //                         {
    //                             label: "周转材料统计汇总报表",
    //                             defaultPath: "MaterTotalReport",
    //                             pathName: "MaterTotalReport",
    //                             comKey: "MaterTotalReport"
    //                         },
    //                         // {
    //                         //     label: "周转材料摊销明细账",
    //                         //     defaultPath: "MaterBuyReport",
    //                         //     pathName: "MaterBuyReport",
    //                         //     comKey: "MaterBuyReport"
    //                         // },
    //                         {
    //                             label: "周转材料统计报表",
    //                             defaultPath: "MaterTurnoverTotalRptReport",
    //                             pathName: "MaterTurnoverTotalRptReport",
    //                             comKey: "MaterTurnoverTotalRptReport"
    //                         },
    //                         {
    //                             label: "周转材料入库台账",
    //                             defaultPath: "MaterRuKuReport",
    //                             pathName: "MaterRuKuReport",
    //                             comKey: "MaterRuKuReport"
    //                         },
    //                         {
    //                             label: "周转材料摊销明细账",
    //                             defaultPath: "TurnResAmoItem",
    //                             pathName: "TurnResAmoItem",
    //                             comKey: "TurnResAmoItem"
    //                         },
    //                         // {
    //                         //     label: "周转材冲账台账",
    //                         //     defaultPath: "TurnWriteOff",
    //                         //     pathName: "TurnWriteOff",
    //                         //     comKey: "TurnWriteOff"

    //                         // }

    //                     ]
    //                 },
    //                 {
    //                     label: "物资价差量差核算",
    //                     children: [
    //                         {
    //                             label: "物资价差量差核算(月)",
    //                             defaultPath: "MaterialPriceMonth",
    //                             pathName: "MaterialPriceMonth",
    //                             comKey: "MaterialPriceMonth"
    //                         },
    //                     ]
    //                 },
    //                 {
    //                     label: "基础设置",
    //                     children: [
    //                         {
    //                             label: "局统一材料分类设置",
    //                             defaultPath: "BureauUnifiedMaterialClassificationSetting",
    //                             pathName: "BureauUnifiedMaterialClassificationSetting",
    //                             comKey: "BureauUnifiedMaterialClassificationSetting"
    //                         },
    //                         {
    //                             label: "局统一材料编码设置",
    //                             defaultPath: "BureauUniformMaterialCodingSettings",
    //                             pathName: "BureauUniformMaterialCodingSettings",
    //                             comKey: "BureauUniformMaterialCodingSettings"
    //                         },
    //                         {
    //                             label: "分部分项设置",
    //                             defaultPath: "PartialItemization",
    //                             pathName: "PartialItemization",
    //                             comKey: "PartialItemization"
    //                         },
    //                         {
    //                             label: "内部单位设置",
    //                             defaultPath: "InternalUnitSetting",
    //                             pathName: "InternalUnitSetting",
    //                             comKey: "InternalUnitSetting"
    //                         },
    //                         {
    //                             label: "领料单位设置",
    //                             defaultPath: "LingliaoDanWei",
    //                             pathName: "LingliaoDanWei",
    //                             comKey: "LingliaoDanWei"
    //                         },
    //                         {
    //                             label: "仓库设置",
    //                             defaultPath: "TheWarehouseIsSet",
    //                             pathName: "TheWarehouseIsSet",
    //                             comKey: "TheWarehouseIsSet"
    //                         },
    //                         {
    //                             label: "物资编码查询",
    //                             defaultPath: "MaterialCodeQuery",
    //                             pathName: "MaterialCodeQuery",
    //                             comKey: "MaterialCodeQuery"
    //                         }
    //                     ]
    //                 }
    //             ]
    //         },
    //         {
    //             label: "协作单位管理",
    //             children: [
    //                 {
    //                     label: "协作单位管理",
    //                     children: [
    //                         {
    //                             label: "施工协作单位",
    //                             defaultPath: "ConstructionCooperationUnit",
    //                             pathName: "ConstructionCooperationUnit",
    //                             comKey: "ConstructionCooperationUnit"
    //                         },
    //                         {
    //                             label: "设备生产商",
    //                             defaultPath: "EquipmentManufacturer",
    //                             pathName: "EquipmentManufacturer",
    //                             comKey: "EquipmentManufacturer"
    //                         },
    //                         {
    //                             label: "设备出租商",
    //                             defaultPath: "EquipmentLessors",
    //                             pathName: "EquipmentLessors",
    //                             comKey: "EquipmentLessors"
    //                         },
    //                         {
    //                             label: "设备经销商",
    //                             defaultPath: "EquipmentDealers",
    //                             pathName: "EquipmentDealers",
    //                             comKey: "EquipmentDealers"
    //                         },
    //                         {
    //                             label: "材料生产商",
    //                             defaultPath: "MaterialManufacturer",
    //                             pathName: "MaterialManufacturer",
    //                             comKey: "MaterialManufacturer"
    //                         },
    //                         {
    //                             label: "材料租赁商",
    //                             defaultPath: "MaterialRenter",
    //                             pathName: "MaterialRenter",
    //                             comKey: "MaterialRenter"
    //                         },
    //                         {
    //                             label: "材料经销商",
    //                             defaultPath: "MaterialDistributor",
    //                             pathName: "MaterialDistributor",
    //                             comKey: "MaterialDistributor"
    //                         },
    //                         {
    //                             label: "其他协作单位",
    //                             defaultPath: "OtherCollaborationUnits",
    //                             pathName: "OtherCollaborationUnits",
    //                             comKey: "OtherCollaborationUnits"
    //                         },
    //                         {
    //                             label: "供应商台账",
    //                             defaultPath: "SupplierAccount",
    //                             pathName: "SupplierAccount",
    //                             comKey: "SupplierAccount"
    //                         },

    //                     ]
    //                 }
    //             ]
    //         },
    //         {
    //             label: "信用评价",
    //             children: [
    //                 {
    //                     label: "个人事项",
    //                     children: [
    //                         {
    //                             //未开发
    //                             label: "信用评价代办",
    //                             defaultPath: "CollectionOfEquipmentLeasingPriceLimit",
    //                             pathName: "CollectionOfEquipmentLeasingPriceLimit",
    //                             comKey: "CollectionOfEquipmentLeasingPriceLimit"
    //                         },
    //                         {
    //                             //未开发
    //                             label: "信用评价已办",
    //                             defaultPath: "CollectionOfEquipmentLeasingPriceLimit",
    //                             pathName: "CollectionOfEquipmentLeasingPriceLimit",
    //                             comKey: "CollectionOfEquipmentLeasingPriceLimit"
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     label: "信用评价查询",
    //                     children: [
    //                         {
    //                             //未开发
    //                             label: "公司季度信用评价查询",
    //                             defaultPath: "CollectionOfEquipmentLeasingPriceLimit",
    //                             pathName: "CollectionOfEquipmentLeasingPriceLimit",
    //                             comKey: "CollectionOfEquipmentLeasingPriceLimit"
    //                         },
    //                         {
    //                             //未开发
    //                             label: "公司半年信用评价查询",
    //                             defaultPath: "CollectionOfEquipmentLeasingPriceLimit",
    //                             pathName: "CollectionOfEquipmentLeasingPriceLimit",
    //                             comKey: "CollectionOfEquipmentLeasingPriceLimit"
    //                         },
    //                         {
    //                             //未开发
    //                             label: "协作单位基本信息查询",
    //                             defaultPath: "CollectionOfEquipmentLeasingPriceLimit",
    //                             pathName: "CollectionOfEquipmentLeasingPriceLimit",
    //                             comKey: "CollectionOfEquipmentLeasingPriceLimit"
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     label: "协作单位基础信息",
    //                     children: [
    //                         {
    //                             label: "协作单位基础信息登记",
    //                             defaultPath: "BasicInformationRegistrationOfCooperativeUnits",
    //                             pathName: "BasicInformationRegistrationOfCooperativeUnits",
    //                             comKey: "BasicInformationRegistrationOfCooperativeUnits"
    //                         },
    //                     ]
    //                 },
    //                 {
    //                     label: "信用评价",
    //                     children: [
    //                         {
    //                             label: "项目信用评价",
    //                             defaultPath: "ProjectCreditEvaluation",
    //                             pathName: "ProjectCreditEvaluation",
    //                             comKey: "ProjectCreditEvaluation"
    //                         },
    //                         {
    //                             label: "公司半年信用评价",
    //                             defaultPath: "QuarterlyCreditEvaluation",
    //                             pathName: "QuarterlyCreditEvaluation",
    //                             comKey: "QuarterlyCreditEvaluation"
    //                         },
    //                         {

    //                             label: "局年度信用评价",
    //                             defaultPath: "QuarterlyCreditEvaluationJu",
    //                             pathName: "QuarterlyCreditEvaluationJu",
    //                             comKey: "QuarterlyCreditEvaluationJu"
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     label: "基础设置",
    //                     children: [
    //                         {
    //                             label: "专业化分类目录",
    //                             defaultPath: "ProfessionalClass",
    //                             pathName: "ProfessionalClass",
    //                             comKey: "ProfessionalClass"
    //                         },
    //                         {
    //                             label: "专业化分类资源库",
    //                             defaultPath: "ProfessionalClassBin",
    //                             pathName: "ProfessionalClassBin",
    //                             comKey: "ProfessionalClassBin"
    //                         },
    //                         {
    //                             label: "项目信用评价考核标准库",
    //                             defaultPath: "ProjectCreditScore",
    //                             pathName: "ProjectCreditScore",
    //                             comKey: "ProjectCreditScore"
    //                         },
    //                         {
    //                             label: "资质标准",
    //                             defaultPath: "StatusStandard",
    //                             pathName: "StatusStandard",
    //                             comKey: "StatusStandard"
    //                         },
    //                         {
    //                             label: "资质标准等级",
    //                             defaultPath: "StatusStandardLevel",
    //                             pathName: "StatusStandardLevel",
    //                             comKey: "StatusStandardLevel"
    //                         },
    //                         {
    //                             label: "企业性质",
    //                             defaultPath: "FirmQuality",
    //                             pathName: "FirmQuality",
    //                             comKey: "FirmQuality"
    //                         },
    //                         {
    //                             label: "工程类别",
    //                             defaultPath: "ProjectClass",
    //                             pathName: "ProjectClass",
    //                             comKey: "ProjectClass"
    //                         },
    //                     ]
    //                 },
    //                 // {
    //                 //     label: "信用评价台账",
    //                 //     children: [
    //                 //         {
    //                 //             //未开发
    //                 //             label: "项目信用评价台账",
    //                 //             defaultPath: "ClassificationOfEquipment",
    //                 //             pathName: "ClassificationOfEquipment",
    //                 //             comKey: "ClassificationOfEquipment"
    //                 //         },
    //                 //         {
    //                 //             //未开发
    //                 //             label: "公司季度信用评价台账",
    //                 //             defaultPath: "ClassificationOfEquipment",
    //                 //             pathName: "ClassificationOfEquipment",
    //                 //             comKey: "ClassificationOfEquipment"
    //                 //         },
    //                 //         {
    //                 //             //未开发
    //                 //             label: "公司半年信用评价台账",
    //                 //             defaultPath: "ClassificationOfEquipment",
    //                 //             pathName: "ClassificationOfEquipment",
    //                 //             comKey: "ClassificationOfEquipment"
    //                 //         },
    //                 //         {
    //                 //             //未开发
    //                 //             label: "双A协作单位",
    //                 //             defaultPath: "ClassificationOfEquipment",
    //                 //             pathName: "ClassificationOfEquipment",
    //                 //             comKey: "ClassificationOfEquipment"
    //                 //         },
    //                 //         {
    //                 //             //未开发
    //                 //             label: "局A、B、C级目录",
    //                 //             defaultPath: "ClassificationOfEquipment",
    //                 //             pathName: "ClassificationOfEquipment",
    //                 //             comKey: "ClassificationOfEquipment"
    //                 //         },
    //                 //         {
    //                 //             //未开发
    //                 //             label: "局不合格名录",
    //                 //             defaultPath: "ClassificationOfEquipment",
    //                 //             pathName: "ClassificationOfEquipment",
    //                 //             comKey: "ClassificationOfEquipment"
    //                 //         },
    //                 //         {
    //                 //             //未开发
    //                 //             label: "季度未评价单位名录",
    //                 //             defaultPath: "ClassificationOfEquipment",
    //                 //             pathName: "ClassificationOfEquipment",
    //                 //             comKey: "ClassificationOfEquipment"
    //                 //         },
    //                 //     ]
    //                 // }
    //             ]
    //         },
    //     ]
    // },
    // {
    //     label: "合同结算",
    //     children: [
    //         {
    //             label: "合同管理",
    //             children: [
    //                 {
    //                     label: "业主合同管理",
    //                     children: [
    //                         {
    //                             label: "业主合同台账",
    //                             defaultPath: "OwnerSContractAccount",
    //                             pathName: "OwnerSContractAccount",
    //                             comKey: "OwnerSContractAccount"
    //                         },
    //                         {
    //                             label: "项目业务过滤",
    //                             defaultPath: "ProjectBusinessFiltering",
    //                             pathName: "ProjectBusinessFiltering",
    //                             comKey: "ProjectBusinessFiltering"
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     label: "对外经营合同评审",
    //                     children: [
    //                         {
    //                             label: "工程施工合同",
    //                             defaultPath: "ProjectConstructionContract",
    //                             pathName: "ProjectConstructionContract",
    //                             comKey: "ProjectConstructionContract"
    //                         },
    //                         {
    //                             label: "物资管理合同",
    //                             defaultPath: "MaterialManagementContract",
    //                             pathName: "MaterialManagementContract",
    //                             comKey: "MaterialManagementContract"
    //                         },
    //                         {
    //                             label: "设备管理合同",
    //                             defaultPath: "MachineryManagementContract",
    //                             pathName: "MachineryManagementContract",
    //                             comKey: "MachineryManagementContract"
    //                         },
    //                         {
    //                             label: "其它合同",
    //                             defaultPath: "OtherContract",
    //                             pathName: "OtherContract",
    //                             comKey: "OtherContract"
    //                         },
    //                         {
    //                             label: "附属合同",
    //                             defaultPath: "CollateralContract",
    //                             pathName: "CollateralContract",
    //                             comKey: "CollateralContract"
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     label: "补充协议",
    //                     children: [
    //                         {
    //                             label: "工程施工类补充协议",
    //                             defaultPath: "SupplementaryAgreementForEngineeringConstruction",
    //                             pathName: "SupplementaryAgreementForEngineeringConstruction",
    //                             comKey: "SupplementaryAgreementForEngineeringConstruction"
    //                         },
    //                         {
    //                             label: "物资管理类补充协议",
    //                             defaultPath: "SupplementaryAgreementOnMaterialManagement",
    //                             pathName: "SupplementaryAgreementOnMaterialManagement",
    //                             comKey: "SupplementaryAgreementOnMaterialManagement"
    //                         },
    //                         {
    //                             label: "设备合同补充协议",
    //                             defaultPath: "SupplementaryAgreementToTheEquipmentContract",
    //                             pathName: "SupplementaryAgreementToTheEquipmentContract",
    //                             comKey: "SupplementaryAgreementToTheEquipmentContract"
    //                         },
    //                         {
    //                             label: "其它类补充协议",
    //                             defaultPath: "OtherSupplementaryAgreements",
    //                             pathName: "OtherSupplementaryAgreements",
    //                             comKey: "OtherSupplementaryAgreements"
    //                         },
    //                         {
    //                             label: "附属合同补充协议",
    //                             defaultPath: "SupplementaryAgreementToSubsidiaryContract",
    //                             pathName: "SupplementaryAgreementToSubsidiaryContract",
    //                             comKey: "SupplementaryAgreementToSubsidiaryContract"
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     label: "对外经营合同管理",
    //                     children: [
    //                         {
    //                             label: "工程施工合同",
    //                             defaultPath: "ProjectConstructionContractManagement",
    //                             pathName: "ProjectConstructionContractManagement",
    //                             comKey: "ProjectConstructionContractManagement"
    //                         },
    //                         {
    //                             label: "物资管理合同",
    //                             defaultPath: "MaterialManagementContractManagement",
    //                             pathName: "MaterialManagementContractManagement",
    //                             comKey: "MaterialManagementContractManagement"
    //                         },
    //                         {
    //                             label: "设备管理合同",
    //                             defaultPath: "MachineryManagementContractManagement",
    //                             pathName: "MachineryManagementContractManagement",
    //                             comKey: "MachineryManagementContractManagement"
    //                         },
    //                         {
    //                             label: "其它合同",
    //                             defaultPath: "OtherContractManagement",
    //                             pathName: "OtherContractManagement",
    //                             comKey: "OtherContractManagement"
    //                         },
    //                         {
    //                             label: "附属合同",
    //                             defaultPath: "CollateralContractManagement",
    //                             pathName: "CollateralContractManagement",
    //                             comKey: "CollateralContractManagement"
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     label: "系统设置",
    //                     children: [
    //                         {
    //                             label: "国家设置",
    //                             defaultPath: "CountriesSet",
    //                             pathName: "CountriesSet",
    //                             comKey: "CountriesSet"
    //                         },
    //                         {
    //                             label: "云电商分包定标数据",
    //                             defaultPath: "CloudECommerceContractorSubcontractsCalibrationData",
    //                             pathName: "CloudECommerceContractorSubcontractsCalibrationData",
    //                             comKey: "CloudECommerceContractorSubcontractsCalibrationData"
    //                         },
    //                         {
    //                             label: "分包合同标准工序库",
    //                             defaultPath: "SubcontractStandardProcessLibrary",
    //                             pathName: "SubcontractStandardProcessLibrary",
    //                             comKey: "SubcontractStandardProcessLibrary"
    //                         },
    //                         {
    //                             label: "分包计价规则库",
    //                             defaultPath: "SubcontractPricingRuleBase",
    //                             pathName: "SubcontractPricingRuleBase",
    //                             comKey: "SubcontractPricingRuleBase"
    //                         },
    //                         {
    //                             label: "市政标准工序库",
    //                             defaultPath: "MunicipalStandardProcessLibrary",
    //                             pathName: "MunicipalStandardProcessLibrary",
    //                             comKey: "MunicipalStandardProcessLibrary"
    //                         },
    //                         {
    //                             label: "市政计价规则库",
    //                             defaultPath: "MunicipalPricingRuleBase",
    //                             pathName: "MunicipalPricingRuleBase",
    //                             comKey: "MunicipalPricingRuleBase"
    //                         },
    //                         {
    //                             label: "铁路分包工序库",
    //                             defaultPath: "RailwaySubcontractingProcessLibrary",
    //                             pathName: "RailwaySubcontractingProcessLibrary",
    //                             comKey: "RailwaySubcontractingProcessLibrary"
    //                         },
    //                         {
    //                             label: "铁路分包计价规则库",
    //                             defaultPath: "RailwaySubcontractPricingRuleBase",
    //                             pathName: "RailwaySubcontractPricingRuleBase",
    //                             comKey: "RailwaySubcontractPricingRuleBase"
    //                         },
    //                         {
    //                             label: "轨道分包工序库",
    //                             defaultPath: "TrackSubcontractingProcessLibrary",
    //                             pathName: "TrackSubcontractingProcessLibrary",
    //                             comKey: "TrackSubcontractingProcessLibrary"
    //                         },
    //                         {
    //                             label: "轨道分包计价规则库",
    //                             defaultPath: "TrackSubcontractPricingRuleBase",
    //                             pathName: "TrackSubcontractPricingRuleBase",
    //                             comKey: "TrackSubcontractPricingRuleBase"
    //                         },
    //                         {
    //                             label: "房建分包工序库",
    //                             defaultPath: "HouseBuildingSubcontractingProcessLibrary",
    //                             pathName: "HouseBuildingSubcontractingProcessLibrary",
    //                             comKey: "HouseBuildingSubcontractingProcessLibrary"
    //                         },
    //                         {
    //                             label: "房建分包计价规则库",
    //                             defaultPath: "HouseBuildingSubcontractPricingRuleBase",
    //                             pathName: "HouseBuildingSubcontractPricingRuleBase",
    //                             comKey: "HouseBuildingSubcontractPricingRuleBase"
    //                         }
    //                     ]
    //                 },
    // {
    //     label: "报表",
    //     children: [
    //         {
    //             label: "合同类型结算统计",
    //             defaultPath: "ContractTypeSettlementStatistics",
    //             pathName: "ContractTypeSettlementStatistics",
    //             comKey: "ContractTypeSettlementStatistics"
    //         },
    //         {
    //             label: "工程计量支付汇总表",
    //             defaultPath: "EngineeringMeteringPaymentSummaryTable",
    //             pathName: "EngineeringMeteringPaymentSummaryTable",
    //             comKey: "EngineeringMeteringPaymentSummaryTable"
    //         },
    //         {
    //             label: "业主结算明细表(公路、市政)",
    //             defaultPath: "OwnersSettlementSchedule",
    //             pathName: "OwnersSettlementSchedule",
    //             comKey: "OwnersSettlementSchedule"
    //         },
    //         {
    //             label: "业主合同台账",
    //             defaultPath: "OwnerContractLedgerStatement",
    //             pathName: "OwnerContractLedgerStatement",
    //             comKey: "OwnerContractLedgerStatement"
    //         },
    //         {
    //             label: "合同偏差统计表",
    //             defaultPath: "StatisticalTableOfContractDeviation",
    //             pathName: "StatisticalTableOfContractDeviation",
    //             comKey: "StatisticalTableOfContractDeviation"
    //         },
    //         {
    //             label: "合同审批率统计",
    //             defaultPath: "ContractApprovalRateStatistics",
    //             pathName: "ContractApprovalRateStatistics",
    //             comKey: "ContractApprovalRateStatistics"
    //         },
    //         {
    //             label: "合同评审台账(工程类)",
    //             defaultPath: "ContractReviewLedger",
    //             pathName: "ContractReviewLedger",
    //             comKey: "ContractReviewLedger"
    //         },
    //         {
    //             label: "施工内容及计价规则表",
    //             defaultPath: "ConstructionContentAndValuationRules",
    //             pathName: "ConstructionContentAndValuationRules",
    //             comKey: "ConstructionContentAndValuationRules"
    //         },
    //         {
    //             label: "合同单价分析表",
    //             defaultPath: "ContractUnitPriceAnalysis",
    //             pathName: "ContractUnitPriceAnalysis",
    //             comKey: "ContractUnitPriceAnalysis"
    //         },
    //         {
    //             label: "工程合同（招标）统计表",
    //             defaultPath: "BiddingForProjectContract",
    //             pathName: "BiddingForProjectContract",
    //             comKey: "BiddingForProjectContract"
    //         },
    //         {
    //             label: "对外经营合同台帐",
    //             defaultPath: "AccountForForeignBusinessContracts",
    //             pathName: "AccountForForeignBusinessContracts",
    //             comKey: "AccountForForeignBusinessContracts"
    //         }
    //     ]
    // },
    //             ]
    //         },
    //     ]
    // }
    //         {
    //             label: "结算管理",
    //             children: [
    // {
    //     label: "分析查询",
    //     children: [
    //         {
    //             label: "集中结算管控指标输出台账",
    //             defaultPath: "CentralizedSettlementControl",
    //             pathName: "CentralizedSettlementControl",
    //             comKey: "CentralizedSettlementControl",
    //         },
    //         {
    //             label: "完工项目查询",
    //             defaultPath: "CompletedProjectEnquiry",
    //             pathName: "CompletedProjectEnquiry",
    //             comKey: "CompletedProjectEnquiry",
    //         },
    //         {
    //             label: "集中结算指标管控台账",
    //             defaultPath: "CentralizedSettlementIndexControlLedger",
    //             pathName: "CentralizedSettlementIndexControlLedger",
    //             comKey: "CentralizedSettlementIndexControlLedger",
    //         }
    //     ]
    // },
    // {
    //     label: "数据填报",
    //     children: [
    //         {
    //             label: "累计结算成本比指标填报",
    //             defaultPath: "CumulativeSettlementCostRatioIndex",
    //             pathName: "CumulativeSettlementCostRatioIndex",
    //             comKey: "CumulativeSettlementCostRatioIndex",
    //         }
    //     ]
    // },
    // {
    //     label: '工程类结算问题预警',
    //     children: [
    //         {
    //             label: "负结算数据统计",
    //             defaultPath: "NegativeSettlementDataStatistics",
    //             pathName: "NegativeSettlementDataStatistics",
    //             comKey: "NegativeSettlementDataStatistics",
    //         },
    //         {
    //             label: "结算及时性数据统计",
    //             defaultPath: "TimelinessOfSettlementDataStatistics",
    //             pathName: "TimelinessOfSettlementDataStatistics",
    //             comKey: "TimelinessOfSettlementDataStatistics",
    //         },
    //         {
    //             label: "零结算或类似零结算数据统计",
    //             defaultPath: "ZeroSettlementOrSimilarZeroSettlementDataStatistics",
    //             pathName: "ZeroSettlementOrSimilarZeroSettlementDataStatistics",
    //             comKey: "ZeroSettlementOrSimilarZeroSettlementDataStatistics",
    //         },
    //         {
    //             label: "入账不及时数据统计",
    //             defaultPath: "NotTimelyDataStatistics",
    //             pathName: "NotTimelyDataStatistics",
    //             comKey: "NotTimelyDataStatistics",
    //         },
    //         {
    //             label: "结算审批及时性数据统计",
    //             defaultPath: "TimelyDataStatisticsOfSettlementApproval",
    //             pathName: "TimelyDataStatisticsOfSettlementApproval",
    //             comKey: "TimelyDataStatisticsOfSettlementApproval",
    //         }
    //     ]
    // }
    // {
    //     label: "台账报表",
    //     children: [
    //         {
    //             label: "工程结算台账(公司)",
    //             defaultPath: "ProjectSettlementLedgerCompany",
    //             pathName: "ProjectSettlementLedgerCompany",
    //             comKey: "ProjectSettlementLedgerCompany",
    //         },
    //         {
    //             label: "工程结算台账(项目)",
    //             defaultPath: "ProjectSettlementLedgerProject",
    //             pathName: "ProjectSettlementLedgerProject",
    //             comKey: "ProjectSettlementLedgerProject",
    //         },
    //         {
    //             label: "机械结算台账(公司)",
    //             defaultPath: "MechanicalSettlementAndLedgerCompany",
    //             pathName: "MechanicalSettlementAndLedgerCompany",
    //             comKey: "MechanicalSettlementAndLedgerCompany",
    //         },
    //         {
    //             label: "机械结算台账(项目)",
    //             defaultPath: "MechanicalSettlementLedgerItems",
    //             pathName: "MechanicalSettlementLedgerItems",
    //             comKey: "MechanicalSettlementLedgerItems",
    //         },
    //         {
    //             label: "其它结算台账(公司)",
    //             defaultPath: "OtherBillingAndBookkeepingCompanies",
    //             pathName: "OtherBillingAndBookkeepingCompanies",
    //             comKey: "OtherBillingAndBookkeepingCompanies",
    //         },
    //         {
    //             label: "其它结算台账(项目)",
    //             defaultPath: "OtherAccountItems",
    //             pathName: "OtherAccountItems",
    //             comKey: "OtherAccountItems",
    //         },
    //         {
    //             label: "物资采购结算台账(公司)",
    //             defaultPath: "MaterialPurchaseSettlementLedgerCompany",
    //             pathName: "MaterialPurchaseSettlementLedgerCompany",
    //             comKey: "MaterialPurchaseSettlementLedgerCompany",
    //         },
    //         {
    //             label: "物资采购结算台账(项目)",
    //             defaultPath: "MaterialPurchaseSettlementLedgerProject",
    //             pathName: "MaterialPurchaseSettlementLedgerProject",
    //             comKey: "MaterialPurchaseSettlementLedgerProject",
    //         },
    //         {
    //             label: "物资租赁结算台账(公司)",
    //             defaultPath: "MaterialLeaseSettlementLedgerCompany",
    //             pathName: "MaterialLeaseSettlementLedgerCompany",
    //             comKey: "MaterialLeaseSettlementLedgerCompany",
    //         },
    //         {
    //             label: "物资租赁结算台账(项目)",
    //             defaultPath: "MaterialLeaseSettlementLedgerProject",
    //             pathName: "MaterialLeaseSettlementLedgerProject",
    //             comKey: "MaterialLeaseSettlementLedgerProject",
    //         },
    //         {
    //             label: "支付项结算情况明细表",
    //             defaultPath: "ScheduleOfPaymentSettlement",
    //             pathName: "ScheduleOfPaymentSettlement",
    //             comKey: "ScheduleOfPaymentSettlement",
    //         },
    //         {
    //             label: "支付项结算情况汇总表",
    //             defaultPath: "SummaryOfSettlementOfPaymentItems",
    //             pathName: "SummaryOfSettlementOfPaymentItems",
    //             comKey: "SummaryOfSettlementOfPaymentItems",
    //         },
    //         {
    //             label: "退回结算查询",
    //             defaultPath: "BackToTheSettlement",
    //             pathName: "BackToTheSettlement",
    //             comKey: "BackToTheSettlement",
    //         },
    //         {
    //             label: "工程类合同结算执行台账",
    //             defaultPath: "ProjectContractSettlementAndExecutionLedger",
    //             pathName: "ProjectContractSettlementAndExecutionLedger",
    //             comKey: "ProjectContractSettlementAndExecutionLedger",
    //         },
    //         {
    //             label: "结算应支付台账",
    //             defaultPath: "SettlementShouldPayTheLedger",
    //             pathName: "SettlementShouldPayTheLedger",
    //             comKey: "SettlementShouldPayTheLedger",
    //         },
    //     ]
    // },
    //                 {
    //                     label: "工程类类结算",
    //                     children: [
    //                         {
    //                             label: "工程类类结算单",
    //                             defaultPath: "EngineeringSettlement",
    //                             pathName: "EngineeringSettlement",
    //                             comKey: "EngineeringSettlement",
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     label: "物资采购类结算",
    //                     children: [
    //                         {
    //                             label: "物资采购类结算单",
    //                             defaultPath: "MaterialPurchaseSettlement",
    //                             pathName: "MaterialPurchaseSettlement",
    //                             comKey: "MaterialPurchaseSettlement",
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     label: "机械租赁类结算",
    //                     children: [
    //                         {
    //                             label: "机械租赁结算单",
    //                             defaultPath: "MachineRentCountList",
    //                             pathName: "MachineRentCountList",
    //                             comKey: "MachineRentCountList",
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     label: "物资租赁类结算",
    //                     children: [
    //                         {
    //                             label: "物资租赁类结算单",
    //                             defaultPath: "MaterialLeasingSettlement",
    //                             pathName: "MaterialLeasingSettlement",
    //                             comKey: "MaterialLeasingSettlement",
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     label: "其它类结算",
    //                     children: [
    //                         {
    //                             label: "其它类结算单",
    //                             defaultPath: "OtherSettlement",
    //                             pathName: "OtherSettlement",
    //                             comKey: "OtherSettlement",
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     label: "附属类结算",
    //                     children: [
    //                         {
    //                             label: "附属类结算单",
    //                             defaultPath: "AffiliatFinalStatement",
    //                             pathName: "AffiliatFinalStatement",
    //                             comKey: "AffiliatFinalStatement",
    //                         }

    //                     ]
    //                 },
    //                 {
    //                     label: "基础设置",
    //                     children: [
    //                         // {
    //                         //     label: "流程执行人设置",
    //                         //     defaultPath: "SetUpTheProcessExecutor",
    //                         //     pathName: "SetUpTheProcessExecutor",
    //                         //     comKey: "SetUpTheProcessExecutor",
    //                         // },
    //                         {
    //                             label: "清单章节划分设置",
    //                             defaultPath: "ListSectionPartitionSettings",
    //                             pathName: "ListSectionPartitionSettings",
    //                             comKey: "ListSectionPartitionSettings",
    //                         },
    //                         {
    //                             label: "工程类结算支付设置",
    //                             defaultPath: "EngineeringSettlementAndPaymentSetting",
    //                             pathName: "EngineeringSettlementAndPaymentSetting",
    //                             comKey: "EngineeringSettlementAndPaymentSetting",
    //                         },
    //                         {
    //                             label: "物资采购类结算支付设置",
    //                             defaultPath: "MaterialPurchaseSettlementAndPaymentSetting",
    //                             pathName: "MaterialPurchaseSettlementAndPaymentSetting",
    //                             comKey: "MaterialPurchaseSettlementAndPaymentSetting",
    //                         },
    //                         {
    //                             label: "机械租赁类结算支付设置",
    //                             defaultPath: "MachineryLeasingSettlementAndPaymentSetting",
    //                             pathName: "MachineryLeasingSettlementAndPaymentSetting",
    //                             comKey: "MachineryLeasingSettlementAndPaymentSetting",
    //                         },
    //                         {
    //                             label: "物资租赁类结算支付设置",
    //                             defaultPath: "MaterialLeasingSettlementAndPaymentSetting",
    //                             pathName: "MaterialLeasingSettlementAndPaymentSetting",
    //                             comKey: "MaterialLeasingSettlementAndPaymentSetting",
    //                         },
    //                         {
    //                             label: "其它类结算支付设置",
    //                             defaultPath: "OtherSettlementAndPaymentSettings",
    //                             pathName: "OtherSettlementAndPaymentSettings",
    //                             comKey: "OtherSettlementAndPaymentSettings",
    //                         },
    //                         {
    //                             label: "完工审核",
    //                             defaultPath: "CompletionOfAudit",
    //                             pathName: "CompletionOfAudit",
    //                             comKey: "CompletionOfAudit",
    //                         },
    //                     ]
    //                 }
    //             ]
    //         },
    //     ]
    // },
    // {
    //     label: '核算支付',
    //     children: [
    //         {
    //             label: "建造合同",
    //             children: [
    // {
    //     label: "查询",
    //     children:[
    //         {
    //             label: "建造合同台账",
    //             defaultPath: "ConstructionContractLedger",
    //             pathName: "ConstructionContractLedger",
    //             comKey: "ConstructionContractLedger"
    //         },
    //     ]
    // },
    //                 {
    //                     label: "项目清单建立",
    //                     children: [
    //                         {
    //                             label: "项目初始建造合同清单",
    //                             defaultPath: "BuildPrimalProjectList",
    //                             pathName: "BuildPrimalProjectList",
    //                             comKey: "BuildPrimalProjectList"
    //                         },
    //                         {
    //                             label: "项目当前建造合同清单",
    //                             defaultPath: "BuildCurrentProjectList",
    //                             pathName: "BuildCurrentProjectList",
    //                             comKey: "BuildCurrentProjectList"
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     label: "建造合同审批",
    //                     children: [
    //                         {
    //                             label: "初始建造合同",
    //                             defaultPath: "PrimalBuildContract",
    //                             pathName: "PrimalBuildContract",
    //                             comKey: "PrimalBuildContract"
    //                         },
    //                         {
    //                             label: "当前建造合同",
    //                             defaultPath: "CurrentBuildContract",
    //                             pathName: "CurrentBuildContract",
    //                             comKey: "CurrentBuildContract"
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     label: "清单基础数据",
    //                     children: [
    //                         {
    //                             label: "初始建造合同清单基础数据",
    //                             defaultPath: "PrimalBuildContractData",
    //                             pathName: "PrimalBuildContractData",
    //                             comKey: "PrimalBuildContractData"
    //                         },
    //                         {
    //                             label: "当前建造合同清单基础数据",
    //                             defaultPath: "CurrentBuildContractData",
    //                             pathName: "CurrentBuildContractData",
    //                             comKey: "CurrentBuildContractData"
    //                         }
    //                     ]
    //                 },
    //                 // {
    //                 //     label: "查询",
    //                 //     children: [
    //                 //         {
    //                 //             label: "建造合同台账",
    //                 //             defaultPath: "PrimalBuildContractData",
    //                 //             pathName: "PrimalBuildContractData",
    //                 //             comKey: "PrimalBuildContractData"
    //                 //         },
    //                 //     ]
    //                 // },

    //             ]
    //         },
    //         {
    //             label: "预算管理",
    //             children: [
    //                 {
    //                     label: "初始化设置",
    //                     children: [
    //                         {
    //                             label: "项目工程类别划分",
    //                             defaultPath: "ProjectEngineeringCategory",
    //                             pathName: "ProjectEngineeringCategory",
    //                             comKey: "ProjectEngineeringCategory"
    //                         },
    //                         {
    //                             label: "清单关联",
    //                             defaultPath: "Qdrelevance",
    //                             pathName: "Qdrelevance",
    //                             comKey: "Qdrelevance"
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     label: "标后预算",
    //                     children: [
    //                         {
    //                             label: "标后材料价格表",
    //                             defaultPath: "AfterMaterial",
    //                             pathName: "AfterMaterial",
    //                             comKey: "AfterMaterial"
    //                         },
    //                         {
    //                             label: "标后复合材料价格表",
    //                             defaultPath: "AfterRecombinationMaterial",
    //                             pathName: "AfterRecombinationMaterial",
    //                             comKey: "AfterRecombinationMaterial"
    //                         },
    //                         {
    //                             label: "标后材料关联",
    //                             defaultPath: "AfterMaterialRelevance",
    //                             pathName: "AfterMaterialRelevance",
    //                             comKey: "AfterMaterialRelevance"
    //                         },
    //                         {
    //                             label: "标后预算编制",
    //                             defaultPath: "AfterBudgeting",
    //                             pathName: "AfterBudgeting",
    //                             comKey: "AfterBudgeting"
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     label: "施工预算",
    //                     children: [
    //                         {
    //                             label: "施工材料价格表",
    //                             defaultPath: "ConstructionMaterial",
    //                             pathName: "ConstructionMaterial",
    //                             comKey: "ConstructionMaterial"
    //                         },
    //                         {
    //                             label: "施工复合材料价格表",
    //                             defaultPath: "ConstructionRecombinationMaterial",
    //                             pathName: "ConstructionRecombinationMaterial",
    //                             comKey: "ConstructionRecombinationMaterial"
    //                         },
    //                         {
    //                             label: "施工材料关联",
    //                             defaultPath: "ConstructionMaterialRelevance",
    //                             pathName: "ConstructionMaterialRelevance",
    //                             comKey: "ConstructionMaterialRelevance"
    //                         },
    //                         {
    //                             label: "施工预算编制",
    //                             defaultPath: "ConstructionBudgeting",
    //                             pathName: "ConstructionBudgeting",
    //                             comKey: "ConstructionBudgeting"
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     label: "项目信息",
    //                     children: [
    //                         {
    //                             label: "预算管理项目信息",
    //                             defaultPath: "BudgetProjectInformation",
    //                             pathName: "BudgetProjectInformation",
    //                             comKey: "BudgetProjectInformation"
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     label: "预算标准",
    //                     children: [
    //                         {
    //                             label: "标准清单库",
    //                             defaultPath: "StandardQdwarehouse",
    //                             pathName: "StandardQdwarehouse",
    //                             comKey: "StandardQdwarehouse"
    //                         },
    //                         {
    //                             label: "标准清单工序",
    //                             defaultPath: "StandardQdwarehouseProcess",
    //                             pathName: "StandardQdwarehouseProcess",
    //                             comKey: "StandardQdwarehouseProcess"
    //                         },
    //                         {
    //                             label: "四项费用标准",
    //                             defaultPath: "ExpenseStandard",
    //                             pathName: "ExpenseStandard",
    //                             comKey: "ExpenseStandard"
    //                         },
    //                         {
    //                             label: "折算系数",
    //                             defaultPath: "ConvertCoefficient",
    //                             pathName: "ConvertCoefficient",
    //                             comKey: "ConvertCoefficient"
    //                         },
    //                         {
    //                             label: "工程类别划分标准",
    //                             defaultPath: "ProjectEngineeringCategoryCriterion",
    //                             pathName: "ProjectEngineeringCategoryCriterion",
    //                             comKey: "ProjectEngineeringCategoryCriterion"
    //                         }
    //                     ]
    //                 },

    //             ]
    //         },
    //         {
    //             label: "成本管理(新)",
    //             children: [
    //                 {
    //                     label: "成本数据库",
    //                     children: [
    //                         {
    //                             label: "公路工程",
    //                             defaultPath: "HighwayEngine",
    //                             pathName: "HighwayEngine",
    //                             comKey: "HighwayEngine"
    //                         }
    //                     ]
    //                 }
    //             ]
    //         }

    //     ]
    // },
    // {
    //     label: '项目管理',
    //     children: [
    //         {
    //             label: "施组评审",
    //             children: [
    //                 {
    //                     label: "施组方案审批",
    //                     children: [
    //                         {
    //                             label: "施组审批发起",
    //                             defaultPath: "ConstructionScheme",
    //                             pathName: "ConstructionScheme",
    //                             comKey: "ConstructionScheme"
    //                         }

    //                     ]
    //                 },


    //             ]
    //         },
    //         {
    //             label: "安全管理",
    //             children: [
    //                 {
    //                     label: "安全管理",
    //                     children: [
    //                         {
    //                             label: "安全目标计划",
    //                             defaultPath: "SafePlanTarget",
    //                             pathName: "SafePlanTarget",
    //                             comKey: "SafePlanTarget"
    //                         },
    //                         {
    //                             label: "应急预案",
    //                             defaultPath: "ContingencyPlan",
    //                             pathName: "ContingencyPlan",
    //                             comKey: "ContingencyPlan"
    //                         },
    //                         {
    //                             label: "安全费用",
    //                             defaultPath: "SafetyPrice",
    //                             pathName: "SafetyPrice",
    //                             comKey: "SafetyPrice"
    //                         },
    //                         {
    //                             label: "安全管理综合考核",
    //                             defaultPath: "SafetyManagementAssessment",
    //                             pathName: "SafetyManagementAssessment",
    //                             comKey: "SafetyManagementAssessment"
    //                         },
    //                         {
    //                             label: "安全组织机构",
    //                             defaultPath: "SafetyOrganization",
    //                             pathName: "SafetyOrganization",
    //                             comKey: "SafetyOrganization"
    //                         },
    //                         {
    //                             label: "安全工作情况季报",
    //                             defaultPath: "SafetyOrganizationSeason",
    //                             pathName: "SafetyOrganizationSeason",
    //                             comKey: "SafetyOrganizationSeason"
    //                         },
    //                         {
    //                             label: "安全检查(项目)",
    //                             defaultPath: "SafeCheckProject",
    //                             pathName: "SafeCheckProject",
    //                             comKey: "SafeCheckProject"
    //                         },
    //                         {
    //                             label: "安全检查(公司)",
    //                             defaultPath: "SafeCheckCompany",
    //                             pathName: "SafeCheckCompany",
    //                             comKey: "SafeCheckCompany"
    //                         },
    //                         {
    //                             label: "安全检查(局)",
    //                             defaultPath: "SafeCheckJu",
    //                             pathName: "SafeCheckJu",
    //                             comKey: "SafeCheckJu"
    //                         },
    //                         {
    //                             label: "重大危险源(项目)",
    //                             defaultPath: "DangerousSourceBigProject",
    //                             pathName: "DangerousSourceBigProject",
    //                             comKey: "DangerousSourceBigProject"
    //                         },
    //                         {
    //                             label: "重大危险源(公司)",
    //                             defaultPath: "DangerousSourceBigCompany",
    //                             pathName: "DangerousSourceBigCompany",
    //                             comKey: "DangerousSourceBigCompany"
    //                         },
    //                         {
    //                             label: "安全教育培训",
    //                             defaultPath: "SafetyEducationAndTraining",
    //                             pathName: "SafetyEducationAndTraining",
    //                             comKey: "SafetyEducationAndTraining"
    //                         },
    //                         {
    //                             label: "协作队伍安全考核",
    //                             defaultPath: "SafetyAssessOfCooperationTeam",
    //                             pathName: "SafetyAssessOfCooperationTeam",
    //                             comKey: "SafetyAssessOfCooperationTeam"
    //                         },
    //                         {
    //                             label: "项目特种设备台账",
    //                             defaultPath: "ProjectSpecialEquipmentAccount",
    //                             pathName: "ProjectSpecialEquipmentAccount",
    //                             comKey: "ProjectSpecialEquipmentAccount"
    //                         },
    //                         {
    //                             label: "安全管理人员",
    //                             defaultPath: "SafetyManagementPersonnel",
    //                             pathName: "SafetyManagementPersonnel",
    //                             comKey: "SafetyManagementPersonnel"
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     label: "基础代码",
    //                     children: [
    //                         {
    //                             label: "特种作业人员台账",
    //                             defaultPath: "SpecialOperationsList",
    //                             pathName: "SpecialOperationsList",
    //                             comKey: "SpecialOperationsList"
    //                         },
    //                         {
    //                             label: "基础代码",
    //                             defaultPath: "CodeBase",
    //                             pathName: "CodeBase",
    //                             comKey: "CodeBase"
    //                         },
    //                         {
    //                             label: "危险源头台帐库",
    //                             defaultPath: "DangerousSourceDatabase",
    //                             pathName: "DangerousSourceDatabase",
    //                             comKey: "DangerousSourceDatabase"
    //                         },
    //                         {
    //                             label: "局考核库",
    //                             defaultPath: "BureauAssessmentDatabase",
    //                             pathName: "BureauAssessmentDatabase",
    //                             comKey: "BureauAssessmentDatabase"
    //                         },
    //                     ]
    //                 },
    //                 {
    //                     label: '安全查询',
    //                     children: [
    //                         {
    //                             label: "安全检查查询",
    //                             defaultPath: "SecurityCheckQuery",
    //                             pathName: "SecurityCheckQuery",
    //                             comKey: "SecurityCheckQuery"
    //                         },
    //                         {
    //                             label: "安全费用查询",
    //                             defaultPath: "SecurityFeeQuery",
    //                             pathName: "SecurityFeeQuery",
    //                             comKey: "SecurityFeeQuery"
    //                         },
    //                         {
    //                             label: "安全教育培训查询",
    //                             defaultPath: "SafetyEducationTrainingInquiry",
    //                             pathName: "SafetyEducationTrainingInquiry",
    //                             comKey: "SafetyEducationTrainingInquiry"
    //                         },
    //                         {
    //                             label: "安全人员检查（学历）",
    //                             defaultPath: "SafetyPersonnelInspectionEducation",
    //                             pathName: "SafetyPersonnelInspectionEducation",
    //                             comKey: "SafetyPersonnelInspectionEducation"
    //                         },
    //                         {
    //                             label: "安全人员检查（职称）",
    //                             defaultPath: "SecurityPersonnelInquiryProfessionalTitle",
    //                             pathName: "SecurityPersonnelInquiryProfessionalTitle",
    //                             comKey: "SecurityPersonnelInquiryProfessionalTitle"
    //                         },
    //                         {
    //                             label: "安全人员检查（工作年限）",
    //                             defaultPath: "SecurityPersonnelInquiryWorkingYears",
    //                             pathName: "SecurityPersonnelInquiryWorkingYears",
    //                             comKey: "SecurityPersonnelInquiryWorkingYears"
    //                         },
    //                         {
    //                             label: "安全目标计划",
    //                             defaultPath: "SafetyTargetPlan",
    //                             pathName: "SafetyTargetPlan",
    //                             comKey: "SafetyTargetPlan"
    //                         }
    //                     ]
    //                 },
    //                 {
    //                     label: '报表',
    //                     children: [
    //                         {
    //                             label: "项目安全检查汇总表",
    //                             defaultPath: "SummaryProjectSafetyInspection",
    //                             pathName: "SummaryProjectSafetyInspection",
    //                             comKey: "SummaryProjectSafetyInspection"
    //                         },
    //                         {
    //                             label: "重大危险源台账（项目）",
    //                             defaultPath: "MajorHazardAccountProject",
    //                             pathName: "MajorHazardAccountProject",
    //                             comKey: "MajorHazardAccountProject"
    //                         },
    //                         {
    //                             label: "危险源台账（公司）",
    //                             defaultPath: "HazardSourceAccountCompany",
    //                             pathName: "HazardSourceAccountCompany",
    //                             comKey: "HazardSourceAccountCompany"
    //                         },
    //                         {
    //                             label: "特种作业人员统计表（项目）",
    //                             defaultPath: "StatisticalTableSpecialOperationPersonnelItem",
    //                             pathName: "StatisticalTableSpecialOperationPersonnelItem",
    //                             comKey: "StatisticalTableSpecialOperationPersonnelItem"
    //                         },
    //                         {
    //                             label: "特种作业人员统计表（公司）",
    //                             defaultPath: "StatisticsSpecialOperationPersonnelCompany",
    //                             pathName: "StatisticsSpecialOperationPersonnelCompany",
    //                             comKey: "StatisticsSpecialOperationPersonnelCompany"
    //                         },
    //                         {
    //                             label: "特种设备台账（公司）",
    //                             defaultPath: "SpecialEquipmentAccountCompany",
    //                             pathName: "SpecialEquipmentAccountCompany",
    //                             comKey: "SpecialEquipmentAccountCompany"
    //                         },
    //                         {
    //                             label: "伤亡事故情况统计分析表（项目）",
    //                             defaultPath: "StatisticalAnalysisCasualtyAccidentsProject",
    //                             pathName: "StatisticalAnalysisCasualtyAccidentsProject",
    //                             comKey: "StatisticalAnalysisCasualtyAccidentsProject"
    //                         },
    //                         {
    //                             label: "伤亡事故情况统计分析表（公司）",
    //                             defaultPath: "StatisticalAnalysisCasualtiesCompany",
    //                             pathName: "StatisticalAnalysisCasualtiesCompany",
    //                             comKey: "StatisticalAnalysisCasualtiesCompany"
    //                         },
    //                         {
    //                             label: "企业职工伤亡事故年度统计分析表（项目）",
    //                             defaultPath: "AnnualStatisticalAnalysisTableCasualtyAccidentsEnterpriseEmployeesProject",
    //                             pathName: "AnnualStatisticalAnalysisTableCasualtyAccidentsEnterpriseEmployeesProject",
    //                             comKey: "AnnualStatisticalAnalysisTableCasualtyAccidentsEnterpriseEmployeesProject"
    //                         },
    //                         {
    //                             label: "企业职工伤亡事故年度统计分析表（公司）",
    //                             defaultPath: "AnnualStatisticalAnalysisTableCasualtyAccidentsEnterpriseEmployeesCompany",
    //                             pathName: "AnnualStatisticalAnalysisTableCasualtyAccidentsEnterpriseEmployeesCompany",
    //                             comKey: "AnnualStatisticalAnalysisTableCasualtyAccidentsEnterpriseEmployeesCompany"
    //                         },
    //                         {
    //                             label: "船舶水上交通事故统计表（项目）",
    //                             defaultPath: "StatisticalTableMarineTrafficAccidentsItem",
    //                             pathName: "StatisticalTableMarineTrafficAccidentsItem",
    //                             comKey: "StatisticalTableMarineTrafficAccidentsItem"
    //                         },
    //                         {
    //                             label: "船舶水上交通事故统计表（公司）",
    //                             defaultPath: "StatisticalTableMarineTrafficAccidentsCompany",
    //                             pathName: "StatisticalTableMarineTrafficAccidentsCompany",
    //                             comKey: "StatisticalTableMarineTrafficAccidentsCompany"
    //                         },
    //                         {
    //                             label: "安全教育花名册",
    //                             defaultPath: "SafetyEducationRoster",
    //                             pathName: "SafetyEducationRoster",
    //                             comKey: "SafetyEducationRoster"
    //                         },
    //                         {
    //                             label: "安全管理人员统计表（项目）",
    //                             defaultPath: "StatisticalTableSafetyManagementPersonnelProject",
    //                             pathName: "StatisticalTableSafetyManagementPersonnelProject",
    //                             comKey: "StatisticalTableSafetyManagementPersonnelProject"
    //                         },
    //                         {
    //                             label: "安全管理人员统计表（公司）",
    //                             defaultPath: "StatisticalTableSafetyManagementPersonnelCompany",
    //                             pathName: "StatisticalTableSafetyManagementPersonnelCompany",
    //                             comKey: "StatisticalTableSafetyManagementPersonnelCompany"
    //                         },
    //                     ]
    //                 }
    //             ]
    //         },

    //     ]
    // },

    // {
    //     label: "综合管理",
    //     children: [
    //         {
    //             label: "待办",
    //             defaultPath: "Agenda",
    //             pathName: "Agenda",
    //             comKey: "Agenda"
    //         },
    //         {
    //             label: "资料库管理",
    //             defaultPath: "DatabaseManagement",
    //             pathName: "DatabaseManagement",
    //             comKey: "DatabaseManagement"
    //         }
    //     ]
    // }

];
const reducers = {};
const actions = {};
const sagas = [];
const MyViews = {
    pageComs,
    routerInfo,
    reducers,
    sagas,
    actions
};
export default MyViews;
