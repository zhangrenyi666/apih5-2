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
//首页轮播
const slideshowList = lazy(() => import('./slideshowList'));
//新闻列表
const NewsList = lazy(() => import('./NewsList'));
//在线咨询
const OnlineConsultationList = lazy(() => import('./OnlineConsultationList'));
//技术服务
const TechnicalServices = lazy(() => import('./TechnicalServices'));
//技术服务(流程)
const FlowByJSFW = lazy(() => import('./FlowByJSFW'));
//专家介绍
const ExpertIntroduction = lazy(() => import('./ExpertIntroduction'));
const ExpertIntroductionDetail = lazy(() => import('./ExpertIntroductionDetail'));
//科技信息
const ScientificCGList = lazy(() => import('./ScientificCGList'));
const ScientificCGDetailList = lazy(() => import('./ScientificCGDetailList'));
const ScientificList = lazy(() => import('./ScientificList'));
const ScientificDetailList = lazy(() => import('./ScientificDetailList'));
const ScientificKTList = lazy(() => import('./ScientificKTList'));
const ScientificKTDetailList = lazy(() => import('./ScientificKTDetailList'));
//引用basicHoc进行简单封装
const basic = (Component,props = { 
    
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
    SystemUserGroup:{ 
        mustLogin: true,
        Com: SystemUserGroup
    },
    InterFaceManage:{
        mustLogin: true,
        Com: InterFaceManage
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
    slideshowList:{
        mustLogin: true,
        Com: slideshowList
    },
    NewsList:{
        mustLogin: true,
        Com: NewsList
    },
    OnlineConsultationList:{
        mustLogin: true,
        Com: OnlineConsultationList
    },
    TechnicalServices:{
        mustLogin: true,
        Com: TechnicalServices
    },
    FlowByJSFW:{
        mustLogin: true,
        Com: FlowByJSFW
    },
    ExpertIntroduction:{
        mustLogin: true,
        Com: ExpertIntroduction
    },
    ExpertIntroductionDetail:{
        mustLogin: true,
        Com: ExpertIntroductionDetail
    },
    ScientificList:{
        mustLogin: true,
        Com: ScientificList
    },
    ScientificCGList:{
        mustLogin: true,
        Com: ScientificCGList
    },
    ScientificKTList:{
        mustLogin: true,
        Com: ScientificKTList
    },
    ScientificDetailList:{
        mustLogin: true,
        Com: ScientificDetailList
    },
    ScientificCGDetailList:{
        mustLogin: true,
        Com: ScientificCGDetailList
    },
    ScientificKTDetailList:{
        mustLogin: true,
        Com: ScientificKTDetailList
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
    //     label: "在线咨询",
    //     children: [
    //         {
    //             label: "在线咨询",
    //             defaultPath: "OnlineConsultationList",
    //             pathName: "OnlineConsultationList",
    //             comKey: "OnlineConsultationList"
    //         },
    //     ]
    // },
    // {
    //     label: "技术服务",
    //     children: [
    //         {
    //             label: "技术服务",
    //             defaultPath: "TechnicalServices",
    //             pathName: "TechnicalServices",
    //             comKey: "TechnicalServices"
    //         },
    //         {
    //             label: "待办任务",
    //             defaultPath: "todoByzjSjTechnicalService",
    //             pathName: "todoByzjSjTechnicalService",
    //             comKey: "FlowByJSFW"
    //         },
    //         {
    //             label: "已完成服务",
    //             defaultPath: "hasTodoByzjSjTechnicalService",
    //             pathName: "hasTodoByzjSjTechnicalService",
    //             comKey: "FlowByJSFW"
    //         }
    //     ]
    // },
    // {
    //     label: "科技信息",
    //     children: [
    //         {
    //             label: "科技成果-管理",
    //             defaultPath: "ScientificCGList",
    //             pathName: "ScientificCGList",
    //             comKey: "ScientificCGList"
    //         },
    //         {
    //             label: "行业科技信息-管理",
    //             defaultPath: "ScientificList",
    //             pathName: "ScientificList",
    //             comKey: "ScientificList"
    //         },
    //         {
    //             label: "科研课题-管理",
    //             defaultPath: "ScientificKTList",
    //             pathName: "ScientificKTList",
    //             comKey: "ScientificKTList"
    //         },
    //         {
    //             label: "科技成果",
    //             defaultPath: "ScientificCGDetailList",
    //             pathName: "ScientificCGDetailList",
    //             comKey: "ScientificCGDetailList"
    //         },
    //         {
    //             label: "行业科技信息",
    //             defaultPath: "ScientificDetailList",
    //             pathName: "ScientificDetailList",
    //             comKey: "ScientificDetailList"
    //         },
    //         {
    //             label: "科研课题",
    //             defaultPath: "ScientificKTDetailList",
    //             pathName: "ScientificKTDetailList",
    //             comKey: "ScientificKTDetailList"
    //         }
    //     ]
    // },
    // {
    //     label: "专家管理",
    //     children: [
    //         {
    //             label: "专家管理-维护",
    //             defaultPath: "ExpertIntroduction",
    //             pathName: "ExpertIntroduction",
    //             comKey: "ExpertIntroduction"
    //         },
    //         {
    //             label: "专家管理",
    //             defaultPath: "ExpertIntroductionDetail",
    //             pathName: "ExpertIntroductionDetail",
    //             comKey: "ExpertIntroductionDetail"
    //         },
    //     ]
    // },
    // {
    //     label: "栏目介绍",
    //     children: [
    //         {
    //             label: "栏目介绍",
    //             defaultPath: "NewsList",
    //             pathName: "NewsList",
    //             comKey: "NewsList"
    //         },
    //     ]
    // },
    // {
    //     label: "轮播图",
    //     children: [
    //         {
    //             label: "轮播图",
    //             defaultPath: "slideshowList",
    //             pathName: "slideshowList",
    //             comKey: "slideshowList"
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
