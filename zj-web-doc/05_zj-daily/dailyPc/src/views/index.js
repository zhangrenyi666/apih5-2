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
const SystemUserGroup = lazy(() => import('./SystemUserGroup'));

//项目基础数据
const BasicProjectData = lazy(() => import('./basicProjectData'));
const BasicProjectDataDetail = lazy(() => import('./basicProjectData/detail'));
//运营日报填写
const DailyOperations = lazy(() => import('./dailyOperations'));
//统计分析
const StatisticAnalysis = lazy(() => import('./statisticAnalysis'));
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
    /* 共通页面over */
    BasicProjectData: {
        mustLogin: true,
        Com: BasicProjectData
    },
    BasicProjectDataDetail: {
        mustLogin: true,
        Com: BasicProjectDataDetail
    },
    DailyOperations: {
        mustLogin: true,
        Com: DailyOperations
    },
    StatisticAnalysis: {
        mustLogin: true,
        Com: StatisticAnalysis
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
    //     label: "投资-运营日报",
    //     children: [
    //         {
    //             label: "项目基础数据",
    //             defaultPath: "BasicProjectData",
    //             pathName: "BasicProjectData",
    //             comKey: "BasicProjectData",
    //             children: [
    //                 {
    //                     label: "项目基础数据明细",
    //                     defaultPath: "BasicProjectDataDetail/0",
    //                     pathName: "BasicProjectDataDetail/:proInfoId",
    //                     comKey: "BasicProjectDataDetail",
    //                     hide:true
    //                 }
    //             ]
    //         },
    //         {
    //             label: "运营日报填写",
    //             defaultPath: "DailyOperations",
    //             pathName: "DailyOperations",
    //             comKey: "DailyOperations",
    //         },
    //         {
    //             label: "统计分析",
    //             defaultPath: "StatisticAnalysis",
    //             pathName: "StatisticAnalysis",
    //             comKey: "StatisticAnalysis",
    //         }
    //     ]
    // },
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
