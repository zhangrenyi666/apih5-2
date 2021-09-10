import { lazy } from "react";
import { basicHoc } from "apih5/modules/layouts";
const ContactsTreeTablePage = lazy(() => import("apih5/pages/contactsTreeTable"));
const LoginPage = lazy(() => import("./login"));
const AuthorizationData = lazy(() => import("./AuthorizationData"));
const HomePage = lazy(() => import("./home/index"));
const ContactsPage = lazy(() => import("./contacts"));
const MenusPage = lazy(() => import("./menus"));
const RolesPage = lazy(() => import("./roles"));
const FlowType = lazy(() => import("./FlowType"));
const TrainList = lazy(() => import("./TrainList"));
const BaseCode = lazy(() => import("./BaseCode"));
const SystemUserGroup = lazy(() => import("./SystemUserGroup"));
const InterFaceManage = lazy(() => import("./InterFaceManage"));
const Policy = lazy(() => import("./Policy"));
const PolicyDetail = lazy(() => import('./Policy/detail'));
const Place = lazy(() => import("./Place"));
const PlaceDetail = lazy(() => import('./Place/detail'));
const PolicyResponses = lazy(() => import('./PolicyResponses'));
const PolicyResponsesDetail = lazy(() => import('./PolicyResponses/detail'));
const PlaceResponses = lazy(() => import('./PlaceResponses'));
const PlaceResponsesDetail = lazy(() => import('./PlaceResponses/detail'));
const videoDetail = lazy(() => import("./PublicityVideo/detail"));
const videoReplayDetail = lazy(() => import("./PublicityVideo/replayDetail"));
const PublicityVideo = lazy(() => import("./PublicityVideo"));
const RulesRegulations = lazy(() => import("./RulesRegulations"));
const ImportantDocuments = lazy(() => import("./ImportantDocuments"));
const Project = lazy(() => import("./Project"));
const ProjectNotSigned = lazy(() => import("./ProjectNotSigned"));
const OtherInformation = lazy(() => import("./OtherInformation"));
const InvestmentScaleControl = lazy(() => import("./InvestmentScaleControl"));
const InvestmentScaleControlDetail = lazy(() => import("./InvestmentScaleControl/detail"));
// 设计流程管理
const DesignProcessManagement = lazy(() => import("./DesignProcessManagement"));
const DesignProcessManagementDetail = lazy(() => import("./DesignProcessManagement/detailCopy"));

const DesignProcessManagementYujingDetail = lazy(() => import("./DesignProcessManagement/yujingDetail"));
// 设计咨询单位管理
// 设计咨询单位标准化
const unitMangeStandard = lazy(() => import("./DesignUnitManagement/unitMangeStandard"));
const unitMangeStandardDetail = lazy(() => import("./DesignUnitManagement/unitMangeStandard/detail"));
// 设计单位管理
const unitMange = lazy(() => import("./DesignUnitManagement/unitMange"));
const unitMangeDetail = lazy(() => import("./DesignUnitManagement/unitMange/detail"));
// 设计单位统计查询
const unitMangeSearch = lazy(() => import("./DesignUnitManagement/unitMangeSearch"));
// 咨询单位管理
const ConsultingUnitManage = lazy(() => import("./DesignUnitManagement/ConsultingUnitManage"));
const ConsultingUnitManageDetail = lazy(() => import("./DesignUnitManagement/ConsultingUnitManage/detail"));
// 设计单位统计查询
const ConsultingUnitMangeSearch = lazy(() => import("./DesignUnitManagement/ConsultingUnitMangeSearch"));
// 设计变更管理
const DesignChangeManage = lazy(() => import("./DesignChangeManage"));
const DesignChangeManageDetail = lazy(() => import("./DesignChangeManage/detail"));
// 设计变更查询
const DesignChangeManageSearch = lazy(() => import("./DesignChangeManageSearch"));
// 添加模板
const AddMuBan = lazy(() => import("./AddMuBan"));
const AddMuBanDetail = lazy(() => import("./AddMuBan/detail"));
//项目综合督查
const ProjectComprehensiveSupervision = lazy(() => import("./ProjectComprehensiveSupervision"));
// 项目整改落实回复
const RectificationReply = lazy(() => import("./RectificationReply"));
//QHSE
//安全管理制度
const SafetyManagementSystemList = lazy(() => import("./SafetyManagementSystemList"));
//质量管理制度
const QualityControlSystemList = lazy(() => import("./QualityControlSystemList"));
//其它管理制度
const OtherManagementSystemsList = lazy(() => import("./OtherManagementSystemsList"));
//安全应急预案
const SafetyEmergencyPlanList = lazy(() => import("./SafetyEmergencyPlanList"));
//组织机构及人员
//安全管理机构
const SafetyManagementOrganizationList = lazy(() => import("./SafetyManagementOrganizationList"));
//质量管理机构
const QualityControlOrganizationList = lazy(() => import("./QualityControlOrganizationList"));
//责任书
//与上级单位签订的安全责任书
const SuperiorUnitSafetyList = lazy(() => import("./SuperiorUnitSafetyList"));
//与总承包签订的安全责任书
const GeneralContractingSafetyList = lazy(() => import("./GeneralContractingSafetyList"));
//与上级单位签订的质量责任书
const SuperiorUnitQualityList = lazy(() => import("./SuperiorUnitQualityList"));
//与总承包签订的质量责任书
const GeneralContractingQualityList = lazy(() => import("./GeneralContractingQualityList"));
//其它责任书
const OtherResponsibilitiesList = lazy(() => import("./OtherResponsibilitiesList"));
// 项目年度考核
const ProjectYearAssessment = lazy(() => import("./ProjectYearAssessment"));
// 监理月度例会
const MonthlySupervisionMeeting = lazy(() => import("./MonthlySupervisionMeeting"));
// 安全质量检查通报
const SafetyInspectionNotice = lazy(() => import("./SafetyInspectionNotice"));
//品牌建设-年底总纲要点
const YearKeyPoints = lazy(() => import("./YearKeyPoints"));
//项目、专项实施方案要点
const KeyPointsPlan = lazy(() => import("./KeyPointsPlan"));
//品牌建设-成果展示
const AchievementShow = lazy(() => import("./AchievementShow"));
//专项活动-成果展示
const YearSpecialActivities = lazy(() => import("./YearSpecialActivities"));
//专项活动-成果展示
const AchievementShowSpecial = lazy(() => import("./AchievementShowSpecial"));
//公告指令建议
const AdviceInstructions = lazy(() => import("./AdviceInstructions"));
//公告指令建议--推送记录
const AdviceInstructionsDetail = lazy(() => import("./AdviceInstructions/detail"));
//投资事业部-督查报告
const ComprehensiveSupervisionReport = lazy(() => import("./ComprehensiveSupervisionReport"));
// 投资事业部-督查报告回复
const CompreRectificationReply = lazy(() => import("./CompreRectificationReply"));
// 千分制检查
const ThousandthSystemInspection = lazy(() => import("./ThousandthSystemInspection/index"));
const ThousandthSystemInspectionDetail = lazy(() => import("./ThousandthSystemInspection/detail"));
const ThousandthSet = lazy(() => import("./ThousandthSet"));

// 经营/建设
// 项目标前核准
const PreBidApproval = lazy(() => import("./PreBidApproval"));
// 风险及条款设置
const RiskAndTermSetting = lazy(() => import("./RiskAndTermSetting"));
const ContractAnalysis = lazy(() => import("./ContractAnalysis/index"));
const ContractAnalysisDetail = lazy(() => import("./ContractAnalysis/detail"));
const MonthDo = lazy(() => import("./MonthDo"));
const ProjectMilestones = lazy(() => import("./ProjectMilestones"));
// 全生命周期策划
const LifeCyclePlanning = lazy(() => import("./LifeCyclePlanning/index"));
const LifeCyclePlanningDetail = lazy(() => import("./LifeCyclePlanning/detail"));
const LifeCycleChange = lazy(() => import("./LifeCycleChange/index"));
const LifeCycleChangeDetail = lazy(() => import("./LifeCycleChange/detail"));
// 待办已办
const TodoHasTo = lazy(() => import("./TodoHasTo"));
// 风险清单设置
const RiskListSetting = lazy(() => import("./RiskListSetting"));
const RiskMange = lazy(() => import("./RiskMange"));
const RiskMangeDetail = lazy(() => import("./RiskMange/detailCopy"));

// 一公局未落实融资（融资暂停）情况月报
const MonthlyReport = lazy(() => import("./MonthlyReport"));
// 资产负债情况表
const MbalanceSheet = lazy(() => import("./MbalanceSheet"));
const MbalanceSheetDetail = lazy(() => import("./MbalanceSheet/detail"));
// 利润表
const IncomeStatement = lazy(() => import("./IncomeStatement"));
const IncomeStatementDetail = lazy(() => import("./IncomeStatement/detail"));
// 现金流量表
const CashFlowStatement = lazy(() => import("./CashFlowStatement"));
const CashFlowStatementDetail = lazy(() => import("./CashFlowStatement/detail"));
// 运营方案
const WorkPlanning = lazy(() => import("./WorkPlanning/index"));
const WorkPlanningDetail = lazy(() => import("./WorkPlanning/detail"));
// 运营季报管理
const WorkPlanMange = lazy(() => import("./WorkPlanMange"));
// 三会管理
const ThreeMeetingMange = lazy(() => import("./ThreeMeetingMange/index"));
const ThreeMeetingMangeDetail = lazy(() => import("./ThreeMeetingMange/detail"));
// 三会管理-董事会
const ThreeMeetingMangeShareholder = lazy(() => import("./ThreeMeetingMangeShareholder/index"));
const ThreeMeetingMangeShareholderDetail = lazy(() => import("./ThreeMeetingMangeShareholder/detail"));
// 三会管理监事会
const ThreeMeetingMangeSupervisors = lazy(() => import("./ThreeMeetingMangeSupervisors/index"));
const ThreeMeetingMangeSupervisorsDetail = lazy(() => import("./ThreeMeetingMangeSupervisors/detail"));
// 董监高会
const ThreeMeetingMangeDongPrisonHigh = lazy(() => import("./ThreeMeetingMangeDongPrisonHigh/index"));
const ThreeMeetingMangeDongPrisonHighDetail = lazy(() => import("./ThreeMeetingMangeDongPrisonHigh/detail"));
//安全质量统计
const CountSq = lazy(() => import("./CountSq"));
const CountSqDetail = lazy(() => import("./CountSq/detail"));
// 人员库
const PersonnelDatabase = lazy(() => import("./PersonnelDatabase"));
const ProjectPersonnel = lazy(() => import("./ProjectPersonnel"));
//合规管理
const ComplianceToDealWith = lazy(() => import("./ComplianceToDealWith"));
const ComplianceToDealWithDetail = lazy(() => import("./ComplianceToDealWith/detail"));
//合规手续设置
const SettingOfComplianceProcedures = lazy(() => import("./SettingOfComplianceProcedures"));
const ZjTzRunFile = lazy(() => import("./ZjTzRunFile"));
// 计量支付
const CongraPay = lazy(() => import("./CongraPay"));
const ProjectProcurement = lazy(() => import("./ReportManagement/ProjectProcurement"));
const LegalComplianceReport = lazy(() => import("./ReportManagement/LegalComplianceReport"));
const PersonnelInformationReport = lazy(() => import("./ReportManagement/PersonnelInformationReport"));
const CompletionOfProjectInvestment = lazy(() => import("./ReportManagement/CompletionOfProjectInvestment"));
const ProjectFunds = lazy(() => import("./ReportManagement/ProjectFunds"));
const DongJianGaoReport = lazy(() => import("./ReportManagement/DongJianGaoReport"));
const ThreeSessions = lazy(() => import("./ReportManagement/ThreeSessions"));
const Implementation = lazy(() => import("./ReportManagement/Implementation"));
const ProjectInvestment = lazy(() => import("./BasicData/ProjectInvestment/index"));
const ProjectInvestmentDetail = lazy(() => import("./BasicData/ProjectInvestment/detail"));
const ProjectInvestmentZj = lazy(() => import("./BasicData/ProjectInvestmentZj/index"));
const ProjectInvestmentZjDetail = lazy(() => import("./BasicData/ProjectInvestmentZj/detail"));
const ProjectInvestmentHg = lazy(() => import("./BasicData/ProjectInvestmentHg/index"));
const ProjectInvestmentHgDetail = lazy(() => import("./BasicData/ProjectInvestmentHg/detail"));
const ProjectInvestmentYy = lazy(() => import("./BasicData/ProjectInvestmentYy/index"));
const ProjectInvestmentYyDetail = lazy(() => import("./BasicData/ProjectInvestmentYy/detail"));
const RiskManageAnalysisReport = lazy(() => import("./RiskManageAnalysisReport/index"));
const PersonnelTalentPool = lazy(() => import("./PersonnelTalentPool/index"));
const jiCaiPingTai = lazy(() => import("./jiCaiPingTai"));
const AvideoUpload = lazy(() => import("./AvideoUpload"));
const AvideoShow = lazy(() => import("./AvideoShow"));

//主页
const HomeNew = lazy(() => import("./DataTableByZJTZ/index"));
//广而告之
// const Advertisement = lazy(() => import("./Advertisement"));
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
        getCompanyList: "getZjTzPermissionListByProject",
        changeCompany: "changeZjTzProjectManagement",

        //中交切换公司接口
        // changeCompany: "changeCompany",
    },

    //项目数据的一些字段名字
    projectKeys: {
        list: "projectList",
        value: "projectId",
        label: "projectShortName"
    },


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
        Com: LoginPage
    },
    AuthorizationData: {
        mustLogin: true,
        Com: AuthorizationData
    },
    Home: {
        mustLogin: true,
        Com: HomePage
        // Com: home
    },
       Contacts: {
        mustLogin: true,
        Com: ContactsPage
    },
    Menus: {
        mustLogin: true,
        Com: MenusPage
    },
    Roles: {
        mustLogin: true,
        Com: RolesPage
    },
    FlowType: {
        mustLogin: true,
        Com: FlowType
    },
    TrainList: {
        mustLogin: true,
        Com: TrainList
    },
    BaseCode: {
        mustLogin: true,
        Com: BaseCode
    },
    SystemUserGroup: {
        mustLogin: true,
        Com: SystemUserGroup
    },
    InterFaceManage: {
        mustLogin: true,
        Com: InterFaceManage
    },
    GenerateQnnTable: {
        mustLogin: true,
        Com: lazy(() => import("apih5/pages/GenerateQnnTablePage"))
    },
    // 定时任务
    SetTimeOutPage: {
        mustLogin: true,
        Com: lazy(() => import("apih5/pages/SetTimeOutPage"))
    },
    SetTimeOutLogPage: {
        mustLogin: true,
        Com: lazy(() => import("apih5/pages/SetTimeOutLogPage"))
    },

    /* 共通页面over */
    Policy: {
        mustLogin: true,
        Com: Policy
    },
    PolicyDetail: {
        mustLogin: true,
        Com: PolicyDetail
    },
    Place: {
        mustLogin: true,
        Com: Place
    },
    PlaceDetail: {
        mustLogin: true,
        Com: PlaceDetail
    },
    PolicyResponses: {
        mustLogin: true,
        Com: PolicyResponses
    },
    PolicyResponsesDetail: {
        mustLogin: true,
        Com: PolicyResponsesDetail
    },
    PlaceResponses: {
        mustLogin: true,
        Com: PlaceResponses
    },
    PlaceResponsesDetail: {
        mustLogin: true,
        Com: PlaceResponsesDetail
    },
    RulesRegulations: {
        mustLogin: true,
        Com: RulesRegulations
    },
    ImportantDocuments: {
        mustLogin: true,
        Com: ImportantDocuments
    },
    PublicityVideo: {
        mustLogin: true,
        Com: PublicityVideo
    },
    videoDetail: {
        mustLogin: true,
        Com: videoDetail
    },
    videoReplayDetail: {
        mustLogin: true,
        Com: videoReplayDetail
    },
    Project: {
        mustLogin: true,
        Com: Project
    },
    ProjectNotSigned: {
        mustLogin: true,
        Com: ProjectNotSigned
    },
    OtherInformation: {
        mustLogin: true,
        Com: OtherInformation
    },
    InvestmentScaleControl: {
        mustLogin: true,
        Com: InvestmentScaleControl
    },
    InvestmentScaleControlDetail: {
        mustLogin: true,
        Com: InvestmentScaleControlDetail
    },
    DesignProcessManagement: {
        mustLogin: true,
        Com: DesignProcessManagement
    },
    DesignProcessManagementDetail: {
        mustLogin: true,
        Com: DesignProcessManagementDetail
    },
    DesignProcessManagementYujingDetail: {
        mustLogin: true,
        Com: DesignProcessManagementYujingDetail
    },
    unitMangeStandard: {
        mustLogin: true,
        Com: unitMangeStandard
    },
    unitMangeStandardDetail: {
        mustLogin: true,
        Com: unitMangeStandardDetail
    },
    unitMange: {
        mustLogin: true,
        Com: unitMange
    },
    unitMangeDetail: {
        mustLogin: true,
        Com: unitMangeDetail
    },
    unitMangeSearch: {
        mustLogin: true,
        Com: unitMangeSearch
    },
    ConsultingUnitManage: {
        mustLogin: true,
        Com: ConsultingUnitManage
    },
    ConsultingUnitManageDetail: {
        mustLogin: true,
        Com: ConsultingUnitManageDetail
    },
    ConsultingUnitMangeSearch: {
        mustLogin: true,
        Com: ConsultingUnitMangeSearch
    },
    DesignChangeManage: {
        mustLogin: true,
        Com: DesignChangeManage
    },
    DesignChangeManageDetail: {
        mustLogin: true,
        Com: DesignChangeManageDetail
    },
    DesignChangeManageSearch: {
        mustLogin: true,
        Com: DesignChangeManageSearch
    },
    AddMuBan: {
        mustLogin: true,
        Com: AddMuBan
    },
    AddMuBanDetail: {
        mustLogin: true,
        Com: AddMuBanDetail
    },
    ProjectComprehensiveSupervision: {
        mustLogin: true,
        Com: ProjectComprehensiveSupervision
    },
    RectificationReply: {
        mustLogin: true,
        Com: RectificationReply
    },
    SafetyManagementSystemList: {
        mustLogin: true,
        Com: SafetyManagementSystemList
    },
    QualityControlSystemList: {
        mustLogin: true,
        Com: QualityControlSystemList
    },
    OtherManagementSystemsList: {
        mustLogin: true,
        Com: OtherManagementSystemsList
    },
    SafetyEmergencyPlanList: {
        mustLogin: true,
        Com: SafetyEmergencyPlanList
    },
    SafetyManagementOrganizationList: {
        mustLogin: true,
        Com: SafetyManagementOrganizationList
    },
    QualityControlOrganizationList: {
        mustLogin: true,
        Com: QualityControlOrganizationList
    },
    SuperiorUnitSafetyList: {
        mustLogin: true,
        Com: SuperiorUnitSafetyList
    },
    GeneralContractingSafetyList: {
        mustLogin: true,
        Com: GeneralContractingSafetyList
    },
    SuperiorUnitQualityList: {
        mustLogin: true,
        Com: SuperiorUnitQualityList
    },
    GeneralContractingQualityList: {
        mustLogin: true,
        Com: GeneralContractingQualityList
    },
    OtherResponsibilitiesList: {
        mustLogin: true,
        Com: OtherResponsibilitiesList
    },
    ProjectYearAssessment: {
        mustLogin: true,
        Com: ProjectYearAssessment
    },
    MonthlySupervisionMeeting: {
        mustLogin: true,
        Com: MonthlySupervisionMeeting
    },
    SafetyInspectionNotice: {
        mustLogin: true,
        Com: SafetyInspectionNotice
    },
    YearKeyPoints: {
        mustLogin: true,
        Com: YearKeyPoints
    },
    KeyPointsPlan: {
        mustLogin: true,
        Com: KeyPointsPlan
    },
    AchievementShow: {
        mustLogin: true,
        Com: AchievementShow
    },
    YearSpecialActivities: {
        mustLogin: true,
        Com: YearSpecialActivities
    },
    AchievementShowSpecial: {
        mustLogin: true,
        Com: AchievementShowSpecial
    },
    AdviceInstructions: {
        mustLogin: true,
        Com: AdviceInstructions
    },
    AdviceInstructionsDetail: {
        mustLogin: true,
        Com: AdviceInstructionsDetail
    },
    ComprehensiveSupervisionReport: {
        mustLogin: true,
        Com: ComprehensiveSupervisionReport
    },
    CompreRectificationReply: {
        mustLogin: true,
        Com: CompreRectificationReply
    },
    ThousandthSystemInspection: {
        mustLogin: true,
        Com: ThousandthSystemInspection
    },
    ThousandthSystemInspectionDetail: {
        mustLogin: true,
        Com: ThousandthSystemInspectionDetail
    },
    ThousandthSet: {
        mustLogin: true,
        Com: ThousandthSet
    },
    PreBidApproval: {
        mustLogin: true,
        Com: PreBidApproval
    },
    RiskAndTermSetting: {
        mustLogin: true,
        Com: RiskAndTermSetting
    },
    ContractAnalysis: {
        mustLogin: true,
        Com: ContractAnalysis
    },
    ContractAnalysisDetail: {
        mustLogin: true,
        Com: ContractAnalysisDetail
    },
    MonthDo: {
        mustLogin: true,
        Com: MonthDo
    },
    ProjectMilestones: {
        mustLogin: true,
        Com: ProjectMilestones
    },
    LifeCyclePlanning: {
        mustLogin: true,
        Com: LifeCyclePlanning
    },
    LifeCyclePlanningDetail: {
        mustLogin: true,
        Com: LifeCyclePlanningDetail
    },
    LifeCycleChange: {
        mustLogin: true,
        Com: LifeCycleChange
    },
    LifeCycleChangeDetail: {
        mustLogin: true,
        Com: LifeCycleChangeDetail
    },
    TodoHasTo: {
        mustLogin: true,
        Com: TodoHasTo
    },
    RiskListSetting: {
        mustLogin: true,
        Com: RiskListSetting
    },
    RiskMange: {
        mustLogin: true,
        Com: RiskMange
    },
    RiskMangeDetail: {
        mustLogin: true,
        Com: RiskMangeDetail
    },
    MonthlyReport: {
        mustLogin: true,
        Com: MonthlyReport
    },
    MbalanceSheet: {
        mustLogin: true,
        Com: MbalanceSheet
    },
    MbalanceSheetDetail: {
        mustLogin: true,
        Com: MbalanceSheetDetail
    },
    IncomeStatement: {
        mustLogin: true,
        Com: IncomeStatement
    },
    IncomeStatementDetail: {
        mustLogin: true,
        Com: IncomeStatementDetail
    },
    CashFlowStatement: {
        mustLogin: true,
        Com: CashFlowStatement
    },
    CashFlowStatementDetail: {
        mustLogin: true,
        Com: CashFlowStatementDetail
    },
    WorkPlanning: {
        mustLogin: true,
        Com: WorkPlanning
    },
    WorkPlanningDetail: {
        mustLogin: true,
        Com: WorkPlanningDetail
    },
    WorkPlanMange: {
        mustLogin: true,
        Com: WorkPlanMange
    },
    ThreeMeetingMange: {
        mustLogin: true,
        Com: ThreeMeetingMange
    },
    ThreeMeetingMangeDetail: {
        mustLogin: true,
        Com: ThreeMeetingMangeDetail
    },
    ThreeMeetingMangeShareholder: {
        mustLogin: true,
        Com: ThreeMeetingMangeShareholder
    },
    ThreeMeetingMangeShareholderDetail: {
        mustLogin: true,
        Com: ThreeMeetingMangeShareholderDetail
    },
    ThreeMeetingMangeSupervisors: {
        mustLogin: true,
        Com: ThreeMeetingMangeSupervisors
    },
    ThreeMeetingMangeSupervisorsDetail: {
        mustLogin: true,
        Com: ThreeMeetingMangeSupervisorsDetail
    },
    ThreeMeetingMangeDongPrisonHigh: {
        mustLogin: true,
        Com: ThreeMeetingMangeDongPrisonHigh
    },
    ThreeMeetingMangeDongPrisonHighDetail: {
        mustLogin: true,
        Com: ThreeMeetingMangeDongPrisonHighDetail
    },
    CountSq: {
        mustLogin: true,
        Com: CountSq
    },
    CountSqDetail: {
        mustLogin: true,
        Com: CountSqDetail
    },
    PersonnelDatabase: {
        mustLogin: true,
        Com: PersonnelDatabase
    },
    ComplianceToDealWith: {
        mustLogin: true,
        Com: ComplianceToDealWith
    },
    ComplianceToDealWithDetail: {
        mustLogin: true,
        Com: ComplianceToDealWithDetail
    },
    SettingOfComplianceProcedures: {
        mustLogin: true,
        Com: SettingOfComplianceProcedures
    },

    ProjectPersonnel: {
        mustLogin: true,
        Com: ProjectPersonnel
    },
    ZjTzRunFile: {
        mustLogin: true,
        Com: ZjTzRunFile
    },
    CongraPay: {
        mustLogin: true,
        Com: CongraPay
    },
    ProjectProcurement: {
        mustLogin: true,
        Com: ProjectProcurement
    },
    LegalComplianceReport: {
        mustLogin: true,
        Com: LegalComplianceReport
    },
    PersonnelInformationReport:{
        mustLogin: true,
        Com: PersonnelInformationReport
    },
    CompletionOfProjectInvestment: {
        mustLogin: true,
        Com: CompletionOfProjectInvestment
    },
    ProjectFunds: {
        mustLogin: true,
        Com: ProjectFunds
    },
    DongJianGaoReport: {
        mustLogin: true,
        Com: DongJianGaoReport
    },
    ThreeSessions: {
        mustLogin: true,
        Com: ThreeSessions
    },
    Implementation: {
        mustLogin: true,
        Com: Implementation
    },
    ProjectInvestment: {
        mustLogin: true,
        Com: ProjectInvestment
    },
    ProjectInvestmentDetail: {
        mustLogin: true,
        Com: ProjectInvestmentDetail
    },
    ProjectInvestmentZj: {
        mustLogin: true,
        Com: ProjectInvestmentZj
    },
    ProjectInvestmentZjDetail: {
        mustLogin: true,
        Com: ProjectInvestmentZjDetail
    },
    ProjectInvestmentHg: {
        mustLogin: true,
        Com: ProjectInvestmentHg
    },
    ProjectInvestmentHgDetail: {
        mustLogin: true,
        Com: ProjectInvestmentHgDetail
    },
    ProjectInvestmentYy: {
        mustLogin: true,
        Com: ProjectInvestmentYy
    },
    ProjectInvestmentYyDetail: {
        mustLogin: true,
        Com: ProjectInvestmentYyDetail
    },
    RiskManageAnalysisReport: {
        mustLogin: true,
        Com: RiskManageAnalysisReport
    },
    PersonnelTalentPool: {
        mustLogin: true,
        Com:PersonnelTalentPool
    },
    jiCaiPingTai: {
        mustLogin: true,
        Com: jiCaiPingTai
    },
    HomeNew: {
        mustLogin: true,
        Com: HomeNew
    },
    AvideoUpload:{
        mustLogin: true,
        Com: AvideoUpload
    },
    AvideoShow: {
        mustLogin: true,
        Com: AvideoShow
    }
};

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
    // {
    //     label:'项目全线视频航拍',
    //     type: "1",
    //     children:[
    //         {
    //             label: "视频上传",
    //             defaultPath: "AvideoUpload",
    //             pathName: "AvideoUpload",
    //             comKey: "AvideoUpload",
    //         },
    //         {
    //             label: "视频展示",
    //             defaultPath: "AvideoShow",
    //             pathName: "AvideoShow",
    //             comKey: "AvideoShow",
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
