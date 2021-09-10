import { lazy } from "react";
import { basicHoc } from "apih5/modules/layouts";

//大屏维护
//产值完成情况
const ZjLzehProduction = lazy(() => import("./ZjLzehProduction"));
//宣传片维护
const ZjLzehVideo = lazy(() => import("./ZjLzehVideo"));
//安全检查统计
const ZjLzehSafetyInspectionStatistics = lazy(() => import("./ZjLzehSafetyInspectionStatistics"));
//质量检查统计
const ZjLzehQualityInspectionStatistics = lazy(() => import("./ZjLzehQualityInspectionStatistics"));
//安全检查管理
const ZjLzehSafetyInspectionManagement = lazy(() => import("./ZjLzehSafetyInspectionManagement"));
//劳务实名制
const ZjLzehLaborRealName = lazy(() => import("./ZjLzehLaborRealName"));
//班组信息
const ZjLzehTeamInformation = lazy(() => import("./ZjLzehTeamInformation"));
//钢筋超市
const ZjLzehRebarSupermarket = lazy(() => import("./ZjLzehRebarSupermarket"));
//经营目标任务计划-新增
const ZjLzehOperationtargettaskplan = lazy(() => import("./ZjLzehOperationtargettaskplan"));
//经营目标任务计划-导入
// const ZjLzehOperationtargettaskplanimport = lazy(() => import("./ZjLzehOperationtargettaskplanimport"));
//生产目标任务计划-新增
const ZjLzehObjectivetaskplanAdd = lazy(() => import("./ZjLzehObjectivetaskplanAdd"));
//生产目标任务计划-导入
// const ZjLzehObjectivetaskplanImport = lazy(() => import("./ZjLzehObjectivetaskplanImport"));


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
        value: "levelId",//levelId  projectId
        label: "levelShortName",//levelShortName  projectShortName
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
    /* 共通页面over */

    //大屏维护
    ZjLzehProduction: {
        mustLogin: true,
        Com: ZjLzehProduction
    },
    ZjLzehVideo: {
        mustLogin: true,
        Com: ZjLzehVideo
    },
    ZjLzehSafetyInspectionStatistics: {
        mustLogin: true,
        Com: ZjLzehSafetyInspectionStatistics
    },
    ZjLzehQualityInspectionStatistics: {
        mustLogin: true,
        Com: ZjLzehQualityInspectionStatistics
    },
    ZjLzehSafetyInspectionManagement: {
        mustLogin: true,
        Com: ZjLzehSafetyInspectionManagement
    },
    ZjLzehLaborRealName: {
        mustLogin: true,
        Com: ZjLzehLaborRealName
    },
    ZjLzehTeamInformation: {
        mustLogin: true,
        Com: ZjLzehTeamInformation
    },
    ZjLzehRebarSupermarket: {
        mustLogin: true,
        Com: ZjLzehRebarSupermarket
    },
    ZjLzehOperationtargettaskplan: {
        mustLogin: true,
        Com: ZjLzehOperationtargettaskplan
    },
    ZjLzehObjectivetaskplanAdd: {
        mustLogin: true,
        Com: ZjLzehObjectivetaskplanAdd
    },
    QualityInspectionStatus: {
        mustLogin: true,
        Com: lazy(() => import("./QualityInspectionStatus"))
    },
    HiddenDangerDetectionSituation: {
        mustLogin: true,
        Com: lazy(() => import("./HiddenDangerDetectionSituation"))
    },
    // 班组管理
    TeamScoringManagement: {
        // 班组评分管理
        mustLogin: true,
        Com: lazy(() => import("./ZjLzehTeamManagement/TeamScoringManagement"))
    },
    TeamLibrarySetting: {
        // 班组库设置
        mustLogin: true,
        Com: lazy(() => import("./ZjLzehTeamManagement/TeamLibrarySetting"))
    },
    // 计划进度管理
    AnnualSchedule: {
        // 年度计划管理
        mustLogin: true,
        Com: lazy(() => import("./ZjLzehScheduleManagement/AnnualSchedule"))
    },
    MonthlySchedule: {
        // 月度计划管理
        mustLogin: true,
        Com: lazy(() => import("./ZjLzehScheduleManagement/MonthlySchedule"))
    },
    // 图表统计
    TeamStatistics: {
        // 班组统计表
        mustLogin: true,
        Com: lazy(() => import("./ZjLzehChartStatistics/TeamStatistics"))
    },
    PersonnelStatistics: {
        // 任务人员统计表
        mustLogin: true,
        Com: lazy(() => import("./ZjLzehChartStatistics/PersonnelStatistics"))
    },
    // 临时任务管理
    AcceptTaskManagement: {
        // 接受任务管理
        mustLogin: true,
        Com: lazy(() => import("./ZjLzehTemporaryTaskManagement/AcceptTaskManagement"))
    },
    AllTasksView: {
        // 全部任务查看
        mustLogin: true,
        Com: lazy(() => import("./ZjLzehTemporaryTaskManagement/AllTasksView"))
    },
    CreateTaskManagement: {
        // 创建任务管理
        mustLogin: true,
        Com: lazy(() => import("./ZjLzehTemporaryTaskManagement/CreateTaskManagement"))
    },
    ScoringByMonthlyStaff: {
        // 按月人员打分
        mustLogin: true,
        Com: lazy(() => import("./ZjLzehTemporaryTaskManagement/ScoringByMonthlyStaff"))
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
    //     label: "案例",
    //     children: [
    //         {
    //             label: "更多",
    //             defaultPath: "Add",
    //             pathName: "Add",
    //             comKey: "Add"
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
    //     label: "接口管理",
    //     children: [
    //         {
    //             label: "InterFaceManage",
    //             defaultPath: "InterFaceManage",
    //             pathName: "InterFaceManage",
    //             comKey: "InterFaceManage"
    //         }
    //     ]
    // },
    // {
    //     label: "大屏维护",
    //     children: [
    //         {
    //             label: "产值完成情况",
    //             defaultPath: "ZjLzehProduction",
    //             pathName: "ZjLzehProduction",
    //             comKey: "ZjLzehProduction"
    //         },
    //         {
    //             label: "宣传片维护",
    //             defaultPath: "ZjLzehVideo",
    //             pathName: "ZjLzehVideo",
    //             comKey: "ZjLzehVideo"
    //         },
    //         {
    //             label: "安全检查统计",
    //             defaultPath: "ZjLzehSafetyInspectionStatistics",
    //             pathName: "ZjLzehSafetyInspectionStatistics",
    //             comKey: "ZjLzehSafetyInspectionStatistics"
    //         },
    //         {
    //             label: "质量检查统计",
    //             defaultPath: "ZjLzehQualityInspectionStatistics",
    //             pathName: "ZjLzehQualityInspectionStatistics",
    //             comKey: "ZjLzehQualityInspectionStatistics"
    //         },
    //         {
    //             label: "安全检查管理",
    //             defaultPath: "ZjLzehSafetyInspectionManagement",
    //             pathName: "ZjLzehSafetyInspectionManagement",
    //             comKey: "ZjLzehSafetyInspectionManagement"
    //         },
    //         {
    //             label: "劳务实名制",
    //             defaultPath: "ZjLzehLaborRealName",
    //             pathName: "ZjLzehLaborRealName",
    //             comKey: "ZjLzehLaborRealName"
    //         },
    //         {
    //             label: "班组信息",
    //             defaultPath: "ZjLzehTeamInformation",
    //             pathName: "ZjLzehTeamInformation",
    //             comKey: "ZjLzehTeamInformation"
    //         },
    //         {
    //             label: "钢筋超市",
    //             defaultPath: "ZjLzehRebarSupermarket",
    //             pathName: "ZjLzehRebarSupermarket",
    //             comKey: "ZjLzehRebarSupermarket"
    //         },

    //     ]
    // },
    // {
    //     label: "质量安全",
    //     children: [
    //         {
    //             label: "质量检查情况",
    //             defaultPath: "QualityInspectionStatus",
    //             pathName: "QualityInspectionStatus",
    //             comKey: "QualityInspectionStatus"
    //         },
    //         {
    //             label: "隐患排查情况",
    //             defaultPath: "HiddenDangerDetectionSituation",
    //             pathName: "HiddenDangerDetectionSituation",
    //             comKey: "HiddenDangerDetectionSituation"
    //         }
    //     ]
    // },
    // {
    //     label: "计划进度管理",
    //     children: [
    //         {
    //             label: "年度计划进度",
    //             defaultPath: "AnnualSchedule",
    //             pathName: "AnnualSchedule",
    //             comKey: "AnnualSchedule"
    //         },
    //         {
    //             label: "月度计划进度",
    //             defaultPath: "MonthlySchedule",
    //             pathName: "MonthlySchedule",
    //             comKey: "MonthlySchedule"
    //         }
    //     ]
    // },
    // {
    //     label: "班组管理",
    //     children: [
    //         {
    //             label: "班组评分管理",
    //             defaultPath: "TeamScoringManagement",
    //             pathName: "TeamScoringManagement",
    //             comKey: "TeamScoringManagement"
    //         },
    //         {
    //             label: "班组库设置",
    //             defaultPath: "TeamLibrarySetting",
    //             pathName: "TeamLibrarySetting",
    //             comKey: "TeamLibrarySetting"
    //         }
    //     ]
    // },
    // {
    //     label: "图表统计",
    //     children: [
    //         {
    //             label: "班组统计表",
    //             defaultPath: "TeamStatistics",
    //             pathName: "TeamStatistics",
    //             comKey: "TeamStatistics"
    //         },
    //         {
    //             label: "任务人员统计表",
    //             defaultPath: "PersonnelStatistics",
    //             pathName: "PersonnelStatistics",
    //             comKey: "PersonnelStatistics"
    //         },
    //     ]
    // },
    // {
    //     label: "目标任务计划",
    //     children: [
    //         {
    //             label: "经营目标任务计划",
    //             defaultPath: "ZjLzehOperationtargettaskplan",
    //             pathName: "ZjLzehOperationtargettaskplan",
    //             comKey: "ZjLzehOperationtargettaskplan"
    //         },
    //         {
    //             label: "生产目标任务计划",
    //             defaultPath: "ZjLzehObjectivetaskplanAdd",
    //             pathName: "ZjLzehObjectivetaskplanAdd",
    //             comKey: "ZjLzehObjectivetaskplanAdd"
    //         },
    //         {
    //             label: "临时任务管理",
    //             children: [
    //                 {
    //                     label: "临时任务管理",
    //                     children: [
    //                         {
    //                             label: "创建任务管理",
    //                             defaultPath: "CreateTaskManagement",
    //                             pathName: "CreateTaskManagement",
    //                             comKey: "CreateTaskManagement"
    //                         },
    //                         {
    //                             label: "接受任务管理",
    //                             defaultPath: "AcceptTaskManagement",
    //                             pathName: "AcceptTaskManagement",
    //                             comKey: "AcceptTaskManagement"
    //                         },
    //                         {
    //                             label: "按月人员打分",
    //                             defaultPath: "ScoringByMonthlyStaff",
    //                             pathName: "ScoringByMonthlyStaff",
    //                             comKey: "ScoringByMonthlyStaff"
    //                         },
    //                         {
    //                             label: "全部任务查看",
    //                             defaultPath: "AllTasksView",
    //                             pathName: "AllTasksView",
    //                             comKey: "AllTasksView"
    //                         },
    //                     ]
    //                 },
    //             ]
    //         },

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
