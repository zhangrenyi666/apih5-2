import { lazy } from "react"
import { basicHoc } from "apih5/modules/layouts";
const LoginPage = lazy(() => import("./login"));
const HomePage = lazy(() => import("./home"));
//首页
const App = lazy(() => import("./App"));
//科技成果详情
const detailCGApp = lazy(() => import('./App/detailCGApp'));
//行业科技信息详情
const detailHYApp = lazy(() => import('./App/detailHYApp'));
//科研课题详情
const detailKTApp = lazy(() => import('./App/detailKTApp'));
//栏目介绍
const News = lazy(() => import("./News"));
const NewsDetail = lazy(() => import("./News/detail"));
//事项区域
const SponsorList = lazy(() => import('./SponsorList'));
const SponsorDetail = lazy(() => import('./SponsorList/detail'));
const todoByzjSjTechnicalService = lazy(() => import('./FlowByJSFW/indexTodo'));
const hasTodoByzjSjTechnicalService = lazy(() => import('./FlowByJSFW/indexhasTodo'));
const FlowByJSFWForm = lazy(() => import('./FlowByJSFW/form'));
//功能模块
//在线咨询
const OnlineConsultationList = lazy(() => import('./OnlineConsultationList'));
//技术服务
const TechnicalServices = lazy(() => import('./TechnicalServices'));
const TechnicalServicesDetail = lazy(() => import('./TechnicalServices/detail'));
const TechnicalServicesAdd = lazy(() => import('./TechnicalServices/add'));
//专家介绍
const ExpertIntroduction = lazy(() => import('./ExpertIntroduction'));
const ExpertIntroductionDetail = lazy(() => import('./ExpertIntroduction/detail'));
//科技信息
const ScientificList = lazy(() => import('./ScientificList'));
//科技成果详情
const detailCG = lazy(() => import('./ScientificList/detailCG'));
const addCG = lazy(() => import('./ScientificList/addCG'));
//行业科技信息详情
const detailHY = lazy(() => import('./ScientificList/detailHY'));
const addHY = lazy(() => import('./ScientificList/addHY'));
//科研课题详情
const detailKT = lazy(() => import('./ScientificList/detailKT'));
const addKT = lazy(() => import('./ScientificList/addKT'));
//收藏列表
const scList = lazy(() => import('./ScientificList/scList'));
const detailSCCG = lazy(() => import('./ScientificList/detailSCCG'));
const detailSCHY = lazy(() => import('./ScientificList/detailSCHY'));
//发布列表
const fbList = lazy(() => import('./ScientificList/fbList'));
const detailFBCG = lazy(() => import('./ScientificList/detailFBCG'));
const detailFBHY = lazy(() => import('./ScientificList/detailFBHY'));
//统计分析
const StatisticalList = lazy(() => import('./StatisticalList'));
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
    /* 共通页面over */
    App: {
        mustLogin: true,
        Com: App
    },
    detailCGApp: {
        mustLogin: true,
        Com: detailCGApp
    },
    detailHYApp: {
        mustLogin: true,
        Com: detailHYApp
    },
    detailKTApp: {
        mustLogin: true,
        Com: detailKTApp
    },
    News: {
        mustLogin: true,
        Com: News
    },
    NewsDetail: {
        mustLogin: true,
        Com: NewsDetail
    },
    SponsorList: {
        mustLogin: true,
        Com: SponsorList
    },
    SponsorDetail: {
        mustLogin: true,
        Com: SponsorDetail
    },
    FlowByJSFWForm: {
        mustLogin: true,
        Com: FlowByJSFWForm
    },
    todoByzjSjTechnicalService: {
        mustLogin: true,
        Com: todoByzjSjTechnicalService
    },
    hasTodoByzjSjTechnicalService: {
        mustLogin: true,
        Com: hasTodoByzjSjTechnicalService
    },
    OnlineConsultationList: {
        mustLogin: true,
        Com: OnlineConsultationList
    },
    TechnicalServices: {
        mustLogin: true,
        Com: TechnicalServices
    },
    TechnicalServicesDetail: {
        mustLogin: true,
        Com: TechnicalServicesDetail
    },
    TechnicalServicesAdd: {
        mustLogin: true,
        Com: TechnicalServicesAdd
    },
    ExpertIntroduction: {
        mustLogin: true,
        Com: ExpertIntroduction
    },
    ExpertIntroductionDetail: {
        mustLogin: true,
        Com: ExpertIntroductionDetail
    },
    ScientificList: {
        mustLogin: true,
        Com: ScientificList
    },
    detailCG: {
        mustLogin: true,
        Com: detailCG
    },
    addCG: {
        mustLogin: true,
        Com: addCG
    },
    detailHY: {
        mustLogin: true,
        Com: detailHY
    },
    addHY: {
        mustLogin: true,
        Com: addHY
    },
    detailKT: {
        mustLogin: true,
        Com: detailKT
    },
    addKT: {
        mustLogin: true,
        Com: addKT
    },
    scList: {
        mustLogin: true,
        Com: scList
    },
    detailSCCG: {
        mustLogin: true,
        Com: detailSCCG
    },
    detailSCHY: {
        mustLogin: true,
        Com: detailSCHY
    },
    fbList: {
        mustLogin: true,
        Com: fbList
    },
    detailFBCG: {
        mustLogin: true,
        Com: detailFBCG
    },
    detailFBHY: {
        mustLogin: true,
        Com: detailFBHY
    },
    StatisticalList: {
        mustLogin: true,
        Com: StatisticalList
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
        label: "首页",
        children: [
            {
                label: "首页",
                defaultPath: "App",
                pathName: "App",
                comKey: "App",
                children: [
                    {
                        label: "科技成果详情",
                        defaultPath: "detailCGApp/0",
                        pathName: "detailCGApp/:infoId",
                        comKey: "detailCGApp"
                    },
                    {
                        label: "行业科技信息详情",
                        defaultPath: "detailHYApp/0",
                        pathName: "detailHYApp/:infoId",
                        comKey: "detailHYApp"
                    },
                    {
                        label: "科研课题详情",
                        defaultPath: "detailKTApp/0",
                        pathName: "detailKTApp/:topicId",
                        comKey: "detailKTApp"
                    }
                ]
            }
        ]
    },
    {
        label: "栏目介绍",
        children: [
            {
                label: "栏目介绍",
                defaultPath: "News",
                pathName: "News",
                comKey: "News",
                children: [
                    {
                        label: "栏目介绍详情",
                        defaultPath: "NewsDetail/0",
                        pathName: "NewsDetail/:newsId",
                        comKey: "NewsDetail"
                    }
                ]
            }
        ]
    },
    {
        label: "事项区域",
        children: [
            {
                label: "技术服务列表",
                defaultPath: "SponsorList",
                pathName: "SponsorList",
                comKey: "SponsorList",
                children: [
                    {
                        label: "技术服务详情",
                        defaultPath: "SponsorDetail/0",
                        pathName: "SponsorDetail/:serviceId",
                        comKey: "SponsorDetail"
                    }
                ]
            },
            {
                label: "事项区域待办",
                defaultPath: "todoByzjSjTechnicalService",
                pathName: "todoByzjSjTechnicalService",
                comKey: "todoByzjSjTechnicalService"
            },
            {
                label: "事项区域已办",
                defaultPath: "hasTodoByzjSjTechnicalService",
                pathName: "hasTodoByzjSjTechnicalService",
                comKey: "hasTodoByzjSjTechnicalService"
            },
            {
                label: '处理',
                defaultPath: "FlowByJSFWForm/zjSjTechnicalService/add/0/0",
                pathName: "FlowByJSFWForm/:flowId/:workId/:title/:status",
                comKey: "FlowByJSFWForm"
            }
        ]
    },
    {
        label: "功能模块",
        children: [
            {
                label: "在线咨询",
                defaultPath: "OnlineConsultationList/0",
                pathName: "OnlineConsultationList/:consultId",
                comKey: "OnlineConsultationList"
            },
            {
                label: "技术服务",
                defaultPath: "TechnicalServices/0",
                pathName: "TechnicalServices/:page",
                comKey: "TechnicalServices",
                children: [
                    {
                        label: "技术服务详情",
                        defaultPath: "TechnicalServicesDetail/0/0",
                        pathName: "TechnicalServicesDetail/:serviceId/:selectType",
                        comKey: "TechnicalServicesDetail"
                    },
                    {
                        label: "技术服务申请",
                        defaultPath: "TechnicalServicesAdd",
                        pathName: "TechnicalServicesAdd",
                        comKey: "TechnicalServicesAdd"
                    }
                ]
            },
            {
                label: "专家介绍",
                defaultPath: "ExpertIntroduction/0",
                pathName: "ExpertIntroduction/:itemId",
                comKey: "ExpertIntroduction",
                children: [
                    {
                        label: "专家详情",
                        defaultPath: "ExpertIntroductionDetail/0/0",
                        pathName: "ExpertIntroductionDetail/:itemId/:expertId",
                        comKey: "ExpertIntroductionDetail"
                    }
                ]
            },
            {
                label: "科技信息",
                defaultPath: "ScientificList/0",
                pathName: "ScientificList/:page",
                comKey: "ScientificList",
                children: [
                    {
                        label: "科技成果详情",
                        defaultPath: "detailCG/0",
                        pathName: "detailCG/:infoId",
                        comKey: "detailCG"
                    },
                    {
                        label: "科技成果新增",
                        defaultPath: "addCG",
                        pathName: "addCG",
                        comKey: "addCG"
                    },
                    {
                        label: "行业科技信息详情",
                        defaultPath: "detailHY/0",
                        pathName: "detailHY/:infoId",
                        comKey: "detailHY"
                    },
                    {
                        label: "行业科技信息新增",
                        defaultPath: "addHY",
                        pathName: "addHY",
                        comKey: "addHY"
                    },
                    {
                        label: "科研课题详情",
                        defaultPath: "detailKT/0",
                        pathName: "detailKT/:topicId",
                        comKey: "detailKT"
                    },
                    {
                        label: "科研课题新增",
                        defaultPath: "addKT",
                        pathName: "addKT",
                        comKey: "addKT"
                    },
                    {
                        label: "收藏列表",
                        defaultPath: "scList",
                        pathName: "scList",
                        comKey: "scList"
                    },
                    {
                        label: "收藏科技成果详情",
                        defaultPath: "detailSCCG/0",
                        pathName: "detailSCCG/:infoId",
                        comKey: "detailSCCG"
                    },
                    {
                        label: "收藏行业科技信息详情",
                        defaultPath: "detailSCHY/0",
                        pathName: "detailSCHY/:infoId",
                        comKey: "detailSCHY"
                    },
                    {
                        label: "发布列表",
                        defaultPath: "fbList",
                        pathName: "fbList",
                        comKey: "fbList"
                    },
                    {
                        label: "发布科技成果详情",
                        defaultPath: "detailFBCG/0",
                        pathName: "detailFBCG/:infoId",
                        comKey: "detailFBCG"
                    },
                    {
                        label: "发布行业科技信息详情",
                        defaultPath: "detailFBHY/0",
                        pathName: "detailFBHY/:infoId",
                        comKey: "detailFBHY"
                    }
                ]
            },
            {
                label: "统计分析",
                defaultPath: "StatisticalList",
                pathName: "StatisticalList",
                comKey: "StatisticalList"
            }
        ]
    },
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
