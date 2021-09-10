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

const zjProZcProIntroduce = lazy(() => import("./zjProZcProIntroduce"));
const zjProZcVideo = lazy(() => import("./zjProZcVideo"));
const zjProZcProtectLayer = lazy(() => import("./zjProZcProtectLayer"));
const zjProZcQs = lazy(() => import("./zjProZcQs"));
const zjProZcSafeScore = lazy(() => import("./zjProZcSafeScore"));
const zjProZcSmartSpray = lazy(() => import("./zjProZcSmartSpray"));

//在这里添加t梁管理,t梁明细,天气明细
const zjProZcTbeamAdministration = lazy(() => import("./zjProZcTbeamAdministration"))
const zjProZcTbeamDetailed = lazy(() => import("./zjProZcTbeamDetailed"))
// const zjProZcweather = lazy(() => import("./zjProZcweather"))


const zjProZcTbeamPedestalUseCondition = lazy(() => import("./zjProZcTbeamPedestalUseCondition"));
// const zjProZcTbeamPedestalUseCondition = lazy(() => import("./zjProZcTbeamPedestalUseCondition/indexCopy"));
const Tension = lazy(() => import("./Tension"));
const Grouting = lazy(() => import("./Grouting"));

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
    /* 共通页面over */
    zjProZcProIntroduce: {
        mustLogin: true,
        Com: zjProZcProIntroduce
    },
    zjProZcVideo: {
        mustLogin: true,
        Com: zjProZcVideo
    },
    zjProZcProtectLayer: {
        mustLogin: true,
        Com: zjProZcProtectLayer
    },
    zjProZcQs: {
        mustLogin: true,
        Com: zjProZcQs
    },
    zjProZcSafeScore: {
        mustLogin: true,
        Com: zjProZcSafeScore
    },
    zjProZcSmartSpray: {
        mustLogin: true,
        Com: zjProZcSmartSpray
    },
    //张启明添加:t梁管理,t梁明细,天气明细
    zjProZcTbeamAdministration: {
        mustLogin: true,
        Com: zjProZcTbeamAdministration
    },
    zjProZcTbeamDetailed: {
        mustLogin: true,
        Com: zjProZcTbeamDetailed
    },
    // zjProZcweather: {
    //     mustLogin: true,
    //     Com: zjProZcweather
    // },
    zjProZcTbeamPedestalUseCondition: {
        mustLogin: true,
        Com: zjProZcTbeamPedestalUseCondition
    },
    Tension: {
        mustLogin: true,
        Com: Tension
    },
    Grouting: {
        mustLogin: true,
        Com: Grouting
    },

    //大屏各个模块的详情页面
    DataTableDetailByQS: {
        mustLogin: true,
        Com: lazy(() => import("./DataTableByHome/componentsDetailPage/QS"))
    },
    DataTableDetailByUseOfPedestal: {
        mustLogin: true,
        Com: lazy(() => import("./DataTableByHome/componentsDetailPage/UseOfPedestal"))
    },
    DataTableDetailByPrefabricate: {
        mustLogin: true,
        Com: lazy(() => import("./DataTableByHome/componentsDetailPage/Prefabricate"))
    },
    DataTableDetailByTension: {
        mustLogin: true,
        Com: lazy(() => import("./DataTableByHome/componentsDetailPage/Tension"))
    },
    DataTableDetailByPressMortar: {
        mustLogin: true,
        Com: lazy(() => import("./DataTableByHome/componentsDetailPage/PressMortar"))
    },
    DataTableDetailByBaoHuCeng: {
        mustLogin: true,
        Com: lazy(() => import("./DataTableByHome/componentsDetailPage/BaoHuCeng"))
    },

    DataTableDetailByZjProZcSmartSpray: {
        mustLogin: true,
        Com: lazy(() => import("./DataTableByHome/componentsDetailPage/zjProZcSmartSpray"))
    },

    DataTableDetailByZjProZcSafeScoreByPerson: {
        mustLogin: true,
        Com: lazy(() => import("./DataTableByHome/componentsDetailPage/zjProZcSafeScoreByPerson"))
    },
    DataTableDetailByNumberOfWorkersOnSite: {
        mustLogin: true,
        Com: lazy(() => import("./DataTableByHome/componentsDetailPage/NumberOfWorkersOnSite"))
    }
};

//遍历所有页面强制让页面包裹basic
//不需要包裹baise的页面key
let noIncs = [
    "Login",
    "DataTableDetailByQS",'DataTableDetailByUseOfPedestal',
    'DataTableDetailByPrefabricate',
    "DataTableDetailByTension",
    "DataTableDetailByPressMortar",
    "DataTableDetailByBaoHuCeng",
    "DataTableDetailByZjProZcSmartSpray",
    "DataTableDetailByZjProZcSafeScoreByPerson",
    "DataTableDetailByNumberOfWorkersOnSite"
]; //, "Home"
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
    //{
    //   label: "后台管理",
    //   children: [
    //       {
    //          label: "工程简介",
    //          defaultPath: "zjProZcProIntroduce",
    //          pathName: "zjProZcProIntroduce",
    //          comKey: "zjProZcProIntroduce"
    //       },
    //       {
    //          label: "宣传视频",
    //          defaultPath: "zjProZcVideo",
    //          pathName: "zjProZcVideo",
    //          comKey: "zjProZcVideo"
    //       },
    //        {
    //          label: "T梁台座使用情况",
    //          defaultPath: "zjProZcTbeamPedestalUseCondition",
    //          pathName: "zjProZcTbeamPedestalUseCondition",
    //          comKey: "zjProZcTbeamPedestalUseCondition"
    //       },
    //       {
    //           label: "张拉",
    //           defaultPath: "Tension",
    //           pathName: "Tension",
    //           comKey: "Tension"
    //       },
    //       {
    //           label: "压浆",
    //           defaultPath: "Grouting",
    //           pathName: "Grouting",
    //           comKey: "Grouting"
    //        },
    //        {
    //          label: "安全积分",
    //          defaultPath: "zjProZcSafeScore",
    //          pathName: "zjProZcSafeScore",
    //          comKey: "zjProZcSafeScore"
    //       },
    //        {
    //          label: "保护层",
    //          defaultPath: "zjProZcProtectLayer",
    //          pathName: "zjProZcProtectLayer",
    //          comKey: "zjProZcProtectLayer"
    //       },
    //        {
    //          label: "质量安全",
    //          defaultPath: "zjProZcQs",
    //          pathName: "zjProZcQs",
    //          comKey: "zjProZcQs"
    //       },
    //        {
    //          label: "智能喷淋",
    //          defaultPath: "zjProZcSmartSpray",
    //          pathName: "zjProZcSmartSpray",
    //          comKey: "zjProZcSmartSpray"
    //       },
    //        {
    //           label: "T梁管理",
    //           defaultPath: "zjProZcTbeamAdministration",
    //           pathName: "zjProZcTbeamAdministration",
    //           comKey: "zjProZcTbeamAdministration"
    //       },
    //        {
    //           label: "T梁明细",
    //           defaultPath: "zjProZcTbeamDetailed",
    //           pathName: "zjProZcTbeamDetailed",
    //           comKey: "zjProZcTbeamDetailed"
    //       },
    //        {
    //           label: "天气明细",
    //           defaultPath: "zjProZcweather",
    //           pathName: "zjProZcweather",
    //           comKey: "zjProZcweather"
    //       }
    //   ]
    //


    {
        label: "大屏个模块的详情页面集合",
        hide: true,
        children: [
            {
                label: "质量安全信息",
                defaultPath: "DataTableDetailByQS",
                pathName: "DataTableDetailByQS",
                comKey: "DataTableDetailByQS"
            },
            {
                label: "台座使用情况",
                defaultPath: "DataTableDetailByUseOfPedestal",
                pathName: "DataTableDetailByUseOfPedestal",
                comKey: "DataTableDetailByUseOfPedestal"
            },
            {
                label: "台座使用情况",
                defaultPath: "DataTableDetailByPrefabricate",
                pathName: "DataTableDetailByPrefabricate",
                comKey: "DataTableDetailByPrefabricate"
            },
            {
                label: "预应力张拉监控",
                defaultPath: "DataTableDetailByTension",
                pathName: "DataTableDetailByTension",
                comKey: "DataTableDetailByTension"
            },
            {
                label: "智能压浆监控",
                defaultPath: "DataTableDetailByPressMortar",
                pathName: "DataTableDetailByPressMortar",
                comKey: "DataTableDetailByPressMortar"
            },
            {
                label: "保护层",
                defaultPath: "DataTableDetailByBaoHuCeng",
                pathName: "DataTableDetailByBaoHuCeng",
                comKey: "DataTableDetailByBaoHuCeng"
            },
            {
                label: "喷淋养护信息",
                defaultPath: "DataTableDetailByZjProZcSmartSpray",
                pathName: "DataTableDetailByZjProZcSmartSpray",
                comKey: "DataTableDetailByZjProZcSmartSpray"
            },
            {
                label: "人员安全积分排名",
                defaultPath: "DataTableDetailByZjProZcSafeScoreByPerson",
                pathName: "DataTableDetailByZjProZcSafeScoreByPerson",
                comKey: "DataTableDetailByZjProZcSafeScoreByPerson"
            },
            {
                label: "在场作业人数",
                defaultPath: "DataTableDetailByNumberOfWorkersOnSite",
                pathName: "DataTableDetailByNumberOfWorkersOnSite",
                comKey: "DataTableDetailByNumberOfWorkersOnSite"
            },
            

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
