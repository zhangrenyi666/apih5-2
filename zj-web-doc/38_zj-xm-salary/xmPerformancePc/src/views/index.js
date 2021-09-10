import { lazy } from "react";
import { basicHoc } from "apih5/modules/layouts";
const ContactsTreeTablePage = lazy(() => import("apih5/pages/contactsTreeTable"));
const LoginPage = lazy(() => import("./login"));
const HomePage = lazy(() => import("./home"));
const ContactsPage = lazy(() => import("./contacts"));
const MenusPage = lazy(() => import("./menus"));
const RolesPage = lazy(() => import("./roles"));
const FlowType = lazy(() => import("./FlowType"));
const TrainList = lazy(() => import("./TrainList"));
const BaseCode = lazy(() => import("./BaseCode"));
const SystemUserGroup = lazy(() => import("./SystemUserGroup"));
const InterFaceManage = lazy(() => import("./InterFaceManage"));
const IframePage = lazy(() => import("apih5/pages/IframePage"));
//发起考核
const Areview = lazy(() => import("./Areview"));
//任务考核
const AssessmentOfTheTask = lazy(() => import("./AssessmentOfTheTask"));
//我要评分
const IWantToScore = lazy(() => import("./IWantToScore"));
//我的得分
const MyScore = lazy(() => import("./MyScore"));
//统计分析
const StatisticAnalysis = lazy(() => import("./StatisticAnalysis"));
//申诉管理
const ComplaintManagement = lazy(() => import("./ComplaintManagement"));
//指标库
const IndicatorsOfLibrary = lazy(() => import("./IndicatorsOfLibrary"));
//个人指标库
const MIndicatorsOfLibrary = lazy(() => import("./MIndicatorsOfLibrary"));
//权重设置
const TheWeighting = lazy(() => import("./TheWeighting"));
//周边考核分数规则
const AroundTheRules = lazy(() => import("./AroundTheRules"));
//月度考核评分汇总
const MonthlyAssessmentScoreSummary = lazy(() => import("./MonthlyAssessmentScoreSummary"));
//月度考核评分排名汇总
const MonthlyAssessmentScoreRankingSummary = lazy(() => import("./MonthlyAssessmentScoreRankingSummary"));
//年度绩效考核得分汇总表
const SummaryOfAnnualPerformanceAppraisalScores = lazy(() => import("./SummaryOfAnnualPerformanceAppraisalScores"));
//员工得分明细表
const EmployeeScoreDetails = lazy(() => import("./EmployeeScoreDetails"));
//部门设置非收尾
const DepartmentSetsNonEnding = lazy(() => import("./DepartmentSetsNonEnding"));
//部门设置收尾
const DepartmentSetupClosure = lazy(() => import("./DepartmentSetupClosure"));
//季度指标库非收尾
const TheQuarterlyIndicatorLibraryIsNotClosed = lazy(() => import("./TheQuarterlyIndicatorLibraryIsNotClosed"));
//季度指标库收尾
const CloseTheQuarterlyInventoryOfIndicators = lazy(() => import("./CloseTheQuarterlyInventoryOfIndicators"));
//项目季度权重非收尾
const ProjectQuarterlyWeightingIsNotClosed = lazy(() => import("./ProjectQuarterlyWeightingIsNotClosed"));
//项目季度权重收尾
const CloseTheProjectQuarterlyWeight = lazy(() => import("./CloseTheProjectQuarterlyWeight"));
//季度发起考核
const QuarterlyAssessment = lazy(() => import("./QuarterlyAssessment"));
//季度评分非收尾
const TheQuarterlyScoreIsNotFinalised = lazy(() => import("./TheQuarterlyScoreIsNotFinalised"));
//季度评分收尾
const QuarterlyScoreClosing = lazy(() => import("./QuarterlyScoreClosing"));
//季度评分统计非收尾
const QuarterlyScoringStatisticsAreNotClosed = lazy(() => import("./QuarterlyScoringStatisticsAreNotClosed"));
//季度评分统计收尾
const StatisticalEndOfTheQuarterlyScore = lazy(() => import("./StatisticalEndOfTheQuarterlyScore"));
//季度评分汇总非收尾
const QuarterlyScoreSummaryIsNotFinal = lazy(() => import("./QuarterlyScoreSummaryIsNotFinal"));
//季度评分汇总收尾
const TheEndOfTheQuarterlyScoreSummary = lazy(() => import("./TheEndOfTheQuarterlyScoreSummary"));

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
        getCompanyList: "getZxQrcodePermissionObjectListByProject",
        changeCompany: "changeZxQrcodePermissionProject",

        //中交切换公司接口
        // changeCompany: "changeCompany",
    },

    //项目数据的一些字段名字
    projectKeys: {
        list: "projectList",
        value: "projectId",//levelId  projectId
        label: "projectName",//levelShortName  projectShortName
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
        Com: LoginPage
    },
    Home: {
        mustLogin: true,
        Com: HomePage
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

    IframePage: {
        mustLogin: true,
        Com: IframePage
    },
    SetTimeOutPage: {
        mustLogin: true,
        Com: lazy(() => import("apih5/pages/SetTimeOutPage"))
    },
    SetTimeOutLogPage: {
        mustLogin: true,
        Com: lazy(() => import("apih5/pages/SetTimeOutLogPage"))
    },
    /* 共通页面over */
    Areview: {
        mustLogin: true,
        Com: Areview
    },
    AssessmentOfTheTask: {
        mustLogin: true,
        Com: AssessmentOfTheTask
    },
    IWantToScore: {
        mustLogin: true,
        Com: IWantToScore
    },
    MyScore: {
        mustLogin: true,
        Com: MyScore
    },
    StatisticAnalysis: {
        mustLogin: true,
        Com: StatisticAnalysis
    },
    ComplaintManagement: {
        mustLogin: true,
        Com: ComplaintManagement
    },
    IndicatorsOfLibrary: {
        mustLogin: true,
        Com: IndicatorsOfLibrary
    },
    MIndicatorsOfLibrary: {
        mustLogin: true,
        Com: MIndicatorsOfLibrary
    },
    TheWeighting: {
        mustLogin: true,
        Com: TheWeighting
    },
    AroundTheRules: {
        mustLogin: true,
        Com: AroundTheRules
    },
    MonthlyAssessmentScoreSummary: {
        mustLogin: true,
        Com: MonthlyAssessmentScoreSummary
    },
    MonthlyAssessmentScoreRankingSummary: {
        mustLogin: true,
        Com: MonthlyAssessmentScoreRankingSummary
    },
    SummaryOfAnnualPerformanceAppraisalScores: {
        mustLogin: true,
        Com: SummaryOfAnnualPerformanceAppraisalScores
    },
    EmployeeScoreDetails: {
        mustLogin: true,
        Com: EmployeeScoreDetails
    },
    DepartmentSetsNonEnding: {
        mustLogin: true,
        Com: DepartmentSetsNonEnding
    },
    DepartmentSetupClosure: {
        mustLogin: true,
        Com: DepartmentSetupClosure
    },
    TheQuarterlyIndicatorLibraryIsNotClosed: {
        mustLogin: true,
        Com: TheQuarterlyIndicatorLibraryIsNotClosed
    },
    CloseTheQuarterlyInventoryOfIndicators: {
        mustLogin: true,
        Com: CloseTheQuarterlyInventoryOfIndicators
    },
    ProjectQuarterlyWeightingIsNotClosed: {
        mustLogin: true,
        Com: ProjectQuarterlyWeightingIsNotClosed
    },
    CloseTheProjectQuarterlyWeight: {
        mustLogin: true,
        Com: CloseTheProjectQuarterlyWeight
    },
    QuarterlyAssessment: {
        mustLogin: true,
        Com: QuarterlyAssessment
    },
    TheQuarterlyScoreIsNotFinalised: {
        mustLogin: true,
        Com: TheQuarterlyScoreIsNotFinalised
    },
    QuarterlyScoreClosing: {
        mustLogin: true,
        Com: QuarterlyScoreClosing
    },
    QuarterlyScoringStatisticsAreNotClosed: {
        mustLogin: true,
        Com: QuarterlyScoringStatisticsAreNotClosed
    },
    StatisticalEndOfTheQuarterlyScore: {
        mustLogin: true,
        Com: StatisticalEndOfTheQuarterlyScore
    },
    QuarterlyScoreSummaryIsNotFinal: {
        mustLogin: true,
        Com: QuarterlyScoreSummaryIsNotFinal
    },
    TheEndOfTheQuarterlyScoreSummary: {
        mustLogin: true,
        Com: TheEndOfTheQuarterlyScoreSummary
    }
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
    // {
    //     label: "外部链接内嵌",
    //     children: [
    //         {
    //             label: "百度",
    //             defaultPath: "baiDu",
    //             pathName: "baiDu", 
    //             comKey: "IframePage",
    //             url: "http://baidu.com"
    //         }
    //     ]
    // },
    // {
    //     label: "项目季度绩效",
    //     children: [
    //         {
    //             label: "季度发起考核",
    //             defaultPath: "QuarterlyAssessment",
    //             pathName: "QuarterlyAssessment", 
    //             comKey: "QuarterlyAssessment"
    //         },
    //         {
    //             label: "季度评分非收尾",
    //             defaultPath: "TheQuarterlyScoreIsNotFinalised",
    //             pathName: "TheQuarterlyScoreIsNotFinalised", 
    //             comKey: "TheQuarterlyScoreIsNotFinalised"
    //         },
    //         {
    //             label: "季度评分收尾",
    //             defaultPath: "QuarterlyScoreClosing",
    //             pathName: "QuarterlyScoreClosing", 
    //             comKey: "QuarterlyScoreClosing"
    //         },
    //         {
    //             label: "季度评分统计非收尾",
    //             defaultPath: "QuarterlyScoringStatisticsAreNotClosed",
    //             pathName: "QuarterlyScoringStatisticsAreNotClosed", 
    //             comKey: "QuarterlyScoringStatisticsAreNotClosed"
    //         },
    //         {
    //             label: "季度评分统计收尾",
    //             defaultPath: "StatisticalEndOfTheQuarterlyScore",
    //             pathName: "StatisticalEndOfTheQuarterlyScore", 
    //             comKey: "StatisticalEndOfTheQuarterlyScore"
    //         },
    //         {
    //             label: "季度评分汇总非收尾",
    //             defaultPath: "QuarterlyScoreSummaryIsNotFinal",
    //             pathName: "QuarterlyScoreSummaryIsNotFinal", 
    //             comKey: "QuarterlyScoreSummaryIsNotFinal"
    //         },
    //         {
    //             label: "季度评分汇总收尾",
    //             defaultPath: "TheEndOfTheQuarterlyScoreSummary",
    //             pathName: "TheEndOfTheQuarterlyScoreSummary", 
    //             comKey: "TheEndOfTheQuarterlyScoreSummary"
    //         }
    //     ]
    // },
    // {
    //     label: "季度指标库",
    //     children: [
    //         {
    //             label: "部门设置非收尾",
    //             defaultPath: "DepartmentSetsNonEnding",
    //             pathName: "DepartmentSetsNonEnding",
    //             comKey: "DepartmentSetsNonEnding"
    //         },
    //         {
    //             label: "部门设置收尾",
    //             defaultPath: "DepartmentSetupClosure",
    //             pathName: "DepartmentSetupClosure",
    //             comKey: "DepartmentSetupClosure"
    //         },
    //         {
    //             label: "季度指标库非收尾",
    //             defaultPath: "TheQuarterlyIndicatorLibraryIsNotClosed",
    //             pathName: "TheQuarterlyIndicatorLibraryIsNotClosed",
    //             comKey: "TheQuarterlyIndicatorLibraryIsNotClosed"
    //         },
    //         {
    //             label: "季度指标库收尾",
    //             defaultPath: "CloseTheQuarterlyInventoryOfIndicators",
    //             pathName: "CloseTheQuarterlyInventoryOfIndicators",
    //             comKey: "CloseTheQuarterlyInventoryOfIndicators"
    //         },
    //         {
    //             label: "项目季度权重非收尾",
    //             defaultPath: "ProjectQuarterlyWeightingIsNotClosed",
    //             pathName: "ProjectQuarterlyWeightingIsNotClosed",
    //             comKey: "ProjectQuarterlyWeightingIsNotClosed"
    //         },
    //         {
    //             label: "项目季度权重收尾",
    //             defaultPath: "CloseTheProjectQuarterlyWeight",
    //             pathName: "CloseTheProjectQuarterlyWeight",
    //             comKey: "CloseTheProjectQuarterlyWeight"
    //         }
    //     ]
    // }, 

    //系统用户组
    // {
    //     "defaultPath": "SystemUserGroup",
    //     "moduleName": "/zxlw/",
    //     "pathName": "SystemUserGroup",
    //     "comKey": "SystemUserGroup"
    // }
    //qnnTable组件生成 
    // {
    //     "defaultPath": "GenerateQnnTable/0",
    //     "moduleName": "/zxlw/",
    //     "pathName": "GenerateQnnTable/:componentKey",
    //     "comKey": "GenerateQnnTable"
    // }
    // {
    //    label: "项目考核",
    //    children: [
    //        {
    //            label: "发起考核",
    //            defaultPath: "Areview",
    //            pathName: "Areview",
    //            comKey: "Areview"
    //        },
    //        {
    //            label: "任务考核",
    //            defaultPath: "AssessmentOfTheTask",
    //            pathName: "AssessmentOfTheTask",
    //            comKey: "AssessmentOfTheTask"
    //        },
    //        {
    //            label: "我要评分",
    //            defaultPath: "IWantToScore",
    //            pathName: "IWantToScore",
    //            comKey: "IWantToScore"
    //        },
    //        {
    //            label: "我的得分",
    //            defaultPath: "MyScore",
    //            pathName: "MyScore",
    //            comKey: "MyScore"
    //        },
    //     //    {
    //     //        label: "统计分析",
    //     //        defaultPath: "StatisticAnalysis",
    //     //        pathName: "StatisticAnalysis",
    //     //        comKey: "StatisticAnalysis"
    //     //    },
    //        {
    //            label: "申诉管理",
    //            defaultPath: "ComplaintManagement",
    //            pathName: "ComplaintManagement",
    //            comKey: "ComplaintManagement"
    //        }
    //    ]
    // },
    // {
    //    label: "基础管理",
    //    children: [
    //        {
    //            label: "指标库",
    //            defaultPath: "IndicatorsOfLibrary",
    //            pathName: "IndicatorsOfLibrary",
    //            comKey: "IndicatorsOfLibrary"
    //        },
    //        {
    //            label: "个人指标库",
    //            defaultPath: "MIndicatorsOfLibrary",
    //            pathName: "MIndicatorsOfLibrary",
    //            comKey: "MIndicatorsOfLibrary"
    //        },
    //        {
    //            label: "权重设置",
    //            defaultPath: "TheWeighting",
    //            pathName: "TheWeighting",
    //            comKey: "TheWeighting"
    //        },
    //        {
    //            label: "周边考核分数规则",
    //            defaultPath: "AroundTheRules",
    //            pathName: "AroundTheRules",
    //            comKey: "AroundTheRules"
    //        }
    //    ]
    // },
    // {
    //     label: "月度考核评分汇总",
    //     children: [
    //         {
    //             label: "月度考核评分汇总表",
    //             defaultPath: "MonthlyAssessmentScoreSummary",
    //             pathName: "MonthlyAssessmentScoreSummary",
    //             comKey: "MonthlyAssessmentScoreSummary"
    //         },
    //         {
    //             label: "月度考核评分排名汇总表",
    //             defaultPath: "MonthlyAssessmentScoreRankingSummary",
    //             pathName: "MonthlyAssessmentScoreRankingSummary",
    //             comKey: "MonthlyAssessmentScoreRankingSummary"
    //         },
    //         {
    //             label: "年度绩效考核得分汇总表",
    //             defaultPath: "SummaryOfAnnualPerformanceAppraisalScores",
    //             pathName: "SummaryOfAnnualPerformanceAppraisalScores",
    //             comKey: "SummaryOfAnnualPerformanceAppraisalScores"
    //         },
    //         {
    //             label: "员工得分明细表",
    //             defaultPath: "EmployeeScoreDetails",
    //             pathName: "EmployeeScoreDetails",
    //             comKey: "EmployeeScoreDetails"
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
