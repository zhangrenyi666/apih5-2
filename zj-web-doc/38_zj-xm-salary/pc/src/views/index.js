
import { lazy } from "react";
import { basicHoc } from "apih5/modules/layouts";
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
    //待办
    TodoHasTo: {
        mustLogin: true,
        Com: lazy(() => import("./TodoHasTo/index"))
    },
    //项目管理
    ProjectMangerment: {
        mustLogin: true,
        Com: lazy(() => import("./ProjectMangerment"))
    },
    //岗位等级
    PostGrades: {
        mustLogin: true,
        Com: lazy(() => import("./PostGrades"))
    },
    //项目人员配置
    ProjectStaffing: {
        mustLogin: true,
        Com: lazy(() => import("./ProjectStaffing"))
    },
    /* 共通页面over */
    //人员管理
    PersonPage: {
        mustLogin: true,
        Com: lazy(() => import("./Person"))
    },
    // 人员异动
    PersonnelTurnover: {
        mustLogin: true,
        Com: lazy(() => import("./PersonnelTurnover"))
    },
    //工作管理 ----------------------------↓↓↓↓↓↓------------------

    HiredToSubmitForApproval: { // 录用报审
        mustLogin: true,
        Com: lazy(() => import("./workManagement/HiredToSubmitForApproval"))
    },
    PostSalaryReportExaminationApproval: {  // 岗薪报审审批
        mustLogin: true,
        Com: lazy(() => import("./workManagement/PostSalaryReportExaminationApproval"))
    },
    ReportExaminationApproval: {    // 报备报审审批
        mustLogin: true,
        Com: lazy(() => import("./workManagement/ReportExaminationApproval"))
    },
    EmployeeTransferReportApproval: {   // 员工转岗报审审批
        mustLogin: true,
        Com: lazy(() => import("./workManagement/EmployeeTransferReportApproval"))
    },
    ReserveCadresReportExaminationApproval: {   // 后备干部报审审批
        mustLogin: true,
        Com: lazy(() => import("./workManagement/ReserveCadresReportExaminationApproval"))
    },
    ProjectOverstaffedApproval: {   // 项目超员审批
        mustLogin: true,
        Com: lazy(() => import("./workManagement/ProjectOverstaffedApproval"))
    },
    // ------------------------------------↑↑↑↑↑↑-------------------
    TaxRateTableMaintenance: {
        mustLogin: true,
        Com: lazy(() => import("./WageManagement/TaxRateTableMaintenance"))
    },
    SocialSecurityMaintenance: {
        mustLogin: true,
        Com: lazy(() => import("./WageManagement/SocialSecurityMaintenance/index.js"))
    },
    detailndex: {
        mustLogin: true,
        Com: lazy(() => import("./WageManagement/SocialSecurityMaintenance/detailndex.js"))
    },
    ProvidentFundMaintenance: {
        mustLogin: true,
        Com: lazy(() => import("./WageManagement/ProvidentFundMaintenance/index.js"))
    },
    ProvidentFundMaintenancedetailndex: {
        mustLogin: true,
        Com: lazy(() => import("./WageManagement/ProvidentFundMaintenance/detailndex.js"))
    },
    CompensationPlan: {
        mustLogin: true,
        Com: lazy(() => import("./WageManagement/CompensationPlan/index.js"))
    },
    CompensationPlandetailndex: {
        mustLogin: true,
        Com: lazy(() => import("./WageManagement/CompensationPlan/detailndex.js"))
    },
    CompensationPlanPageDetail: {
        mustLogin: true,
        Com: lazy(() => import("./WageManagement/CompensationPlan/PageDetail.js"))
    },
    SalaryDataMaintenance: {
        mustLogin: true,
        Com: lazy(() => import("./WageManagement/SalaryDataMaintenance/index.js"))
    },
    SalaryDataMaintenancedetailndex: {
        mustLogin: true,
        Com: lazy(() => import("./WageManagement/SalaryDataMaintenance/detailndex.js"))
    },
    SararySearch: {
        mustLogin: true,
        Com: lazy(() => import("./WageManagement/SararySearch/index.js"))
    },
    AccumulationFundDown: {
        mustLogin: true,
        Com: lazy(() => import("./WageManagement/AccumulationFundDown/index.js"))
    },
    AccumulationFund: {
        mustLogin: true,
        Com: lazy(() => import("./WageManagement/AccumulationFund/index.js"))
    },
    ProvidentFundSchemeSetting: {
        mustLogin: true,
        Com: lazy(() => import("./WageManagement/ProvidentFundSchemeSetting/index.js"))
    },
    SocialSecuritySchemeSetting: {
        mustLogin: true,
        Com: lazy(() => import("./WageManagement/SocialSecuritySchemeSetting/index.js"))
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
            // {
            //     label: "分包限价",
            //     defaultPath: "SubcontractingPriceLimit",
            //     pathName: "SubcontractingPriceLimit",
            //     comKey: "SubcontractingPriceLimit"
            // },

        ]
    },
    // {
    //     label: "工资管理",
    //     children: [
    //         // {
    //         //     label: "税率表维护",
    //         //     defaultPath: "TaxRateTableMaintenance",
    //         //     pathName: "TaxRateTableMaintenance",
    //         //     comKey: "TaxRateTableMaintenance",
    //         // },
    //         // {
    //         //     label: "社保维护",
    //         //     defaultPath: "SocialSecurityMaintenance",
    //         //     pathName: "SocialSecurityMaintenance",
    //         //     comKey: "SocialSecurityMaintenance",
    //         // },
    //         // {
    //         //     label: "社保维护明细",
    //         //     defaultPath: "detailndex/0/0",
    //         //     pathName: "detailndex/:zhujian/:dod",
    //         //     comKey: "detailndex",
    //         //     hide: true
    //         // },
    //         // {
    //         //     label: "公积金维护",
    //         //     defaultPath: "ProvidentFundMaintenance",
    //         //     pathName: "ProvidentFundMaintenance",
    //         //     comKey: "ProvidentFundMaintenance",
    //         // },
    //         // {
    //         //     label: "公积金维护明细",
    //         //     defaultPath: "ProvidentFundMaintenancedetailndex/0/0",
    //         //     pathName: "ProvidentFundMaintenancedetailndex/:zhujian/:dod",
    //         //     comKey: "ProvidentFundMaintenancedetailndex",
    //         //     hide: true
    //         // },
    //         // {
    //         //     label: "公积金抵扣上限",
    //         //     defaultPath: "AccumulationFund",
    //         //     pathName: "AccumulationFund",
    //         //     comKey: "AccumulationFund"
    //         // },
    //         // {
    //         //     label: "企业年金抵扣上限",
    //         //     defaultPath: "AccumulationFundDown",
    //         //     pathName: "AccumulationFundDown",
    //         //     comKey: "AccumulationFundDown"
    //         // },
    //         // {
    //         //     label: "薪酬字典",
    //         //     defaultPath: "SararySearch",
    //         //     pathName: "SararySearch",
    //         //     comKey: "SararySearch"
    //         // },

    //         // {
    //         //     label: "薪酬方案",
    //         //     defaultPath: "CompensationPlan",
    //         //     pathName: "CompensationPlan",
    //         //     comKey: "CompensationPlan",
    //         // },
    //         // {
    //         //     label: "人员维护",
    //         //     defaultPath: "CompensationPlandetailndex/0/0/0",
    //         //     pathName: "CompensationPlandetailndex/:compensationPlanId/:planName/:taxTypeId",
    //         //     comKey: "CompensationPlandetailndex",
    //         //     hide: true
    //         // },
    //         // {
    //         //     label: "方案详情",
    //         //     defaultPath: "CompensationPlanPageDetail/0",
    //         //     pathName: "CompensationPlanPageDetail/:compensationPlanId",
    //         //     comKey: "CompensationPlanPageDetail",
    //         //     hide: true
    //         // },
    //         // {
    //         //     label: "薪酬数据维护",
    //         //     defaultPath: "SalaryDataMaintenance",
    //         //     pathName: "SalaryDataMaintenance",
    //         //     comKey: "SalaryDataMaintenance",
    //         // },
    //         // {
    //         //     label: "薪酬数据维护明细",
    //         //     defaultPath: "SalaryDataMaintenancedetailndex/0",
    //         //     pathName: "SalaryDataMaintenancedetailndex/:zhujian",
    //         //     comKey: "SalaryDataMaintenancedetailndex",
    //         //     hide: true
    //         // },
    //         // {
    //         //     label: "公积金方案设置",
    //         //     defaultPath: "ProvidentFundSchemeSetting",
    //         //     pathName: "ProvidentFundSchemeSetting",
    //         //     comKey: "ProvidentFundSchemeSetting",
    //         // },
    //         // {
    //         //     label: "社保方案设置",
    //         //     defaultPath: "SocialSecuritySchemeSetting",
    //         //     pathName: "SocialSecuritySchemeSetting",
    //         //     comKey: "SocialSecuritySchemeSetting",
    //         // },

    //     ]
    // },
    // {
    //     label: "人员管理",
    //     children: [
    //         {
    //             label: "人员信息",
    //             defaultPath: "PersonPage",
    //             pathName: "PersonPage",
    //             comKey: "PersonPage",
    //         },
    //         {
    //             label: "人员异动",
    //             defaultPath: "PersonPage",
    //             pathName: "PersonPage",
    //             comKey: "PersonPage",
    //         }
    //     ]
    // },
    // {
    //     label: "工作管理",
    //     children: [
    //         {
    //             label: "录(聘)用报审审批",
    //             defaultPath: "HiredToSubmitForApproval",
    //             pathName: "HiredToSubmitForApproval",
    //             comKey: "HiredToSubmitForApproval",
    //         },
    //         {
    //             label: "岗薪报审审批",
    //             defaultPath: "PostSalaryReportExaminationApproval",
    //             pathName: "PostSalaryReportExaminationApproval",
    //             comKey: "PostSalaryReportExaminationApproval",
    //         },
    //         {
    //             label: "报备报审审批",
    //             defaultPath: "ReportExaminationApproval",
    //             pathName: "ReportExaminationApproval",
    //             comKey: "ReportExaminationApproval",
    //         },
    //         {
    //             label: "员工转岗报审审批",
    //             defaultPath: "EmployeeTransferReportApproval",
    //             pathName: "EmployeeTransferReportApproval",
    //             comKey: "EmployeeTransferReportApproval",
    //         },
    //         {
    //             label: "后备干部报审审批",
    //             defaultPath: "ReserveCadresReportExaminationApproval",
    //             pathName: "ReserveCadresReportExaminationApproval",
    //             comKey: "ReserveCadresReportExaminationApproval",
    //         },
    //         {
    //             label: "项目超员审批",
    //             defaultPath: "ProjectOverstaffedApproval",
    //             pathName: "ProjectOverstaffedApproval",
    //             comKey: "ProjectOverstaffedApproval",
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
