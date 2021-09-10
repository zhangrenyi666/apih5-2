import { lazy } from "react";
import { basicHoc } from "apih5/modules/layouts";
const LoginPage = lazy(() => import("./login"));
const HomePage = lazy(() => import("./home"));
// 移动端首页（非移动端请删除 --删除开始）
const App = lazy(() => import("./App"));
const News = lazy(() => import("./News"));
const Add = lazy(() => import("./Add"));
const AddressBook = lazy(() => import("./AddressBook"));
const Me = lazy(() => import("./Me"));
const TabsListMobile = lazy(() => import('./tabsListMobile'));
const ReadListMobile = lazy(() => import('./readListMobile'));
// 人员审批
// import FlowBysanPerson from "./FlowBysanPerson/index";
const FlowBysanPerson = lazy(() => import("./FlowBysanPerson/form"));
// 部长审批
const FlowBysanMinister = lazy(() => import("./FlowBysanMinister/form"));
// 人员审批明细--
const PersonPage = lazy(() => import('./PersonPage/index'));
const PersonPageDetail = lazy(() => import('./PersonPage/detail'));
// 部门领导审批
const MinisterPage = lazy(() => import('./MinisterPage/index'));
const MinisterPageDetail = lazy(() => import('./MinisterPage/detail'));
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
    App: {
        mustLogin: true,
        Com: App
    },
    News: {
        mustLogin: true,
        Com: News
    },
    Add: {
        //应用页面不是首页
        mustLogin: true,
        Com: Add
    },
    AddressBook: {
        //应用页面不是首页
        mustLogin: true,
        Com: AddressBook
    },
    Me: {
        mustLogin: true,
        Com: Me
    },
    Home: {
        mustLogin: true,
        Com: HomePage
    },
/* 共通页面over */
    TabsListMobile: {
        mustLogin: true,
        Com: TabsListMobile
    },
    ReadListMobile: {
        mustLogin: true,
        Com: ReadListMobile
    },
    FlowBysanPerson: {
        mustLogin: true,
        Com: FlowBysanPerson
    },
    FlowBysanPerson: {
        mustLogin: true,
        Com: FlowBysanPerson
    },
    FlowBysanMinister: {
        mustLogin: true,
        Com: FlowBysanMinister
    },
    // 人员审批明细
    PersonPage: {
        mustLogin: true,
        Com: PersonPage
    },
    PersonPageDetail: {
        mustLogin: true,
        Com: PersonPageDetail
    },
    MinisterPage: {
        mustLogin: true,
        Com: MinisterPage
    },
    MinisterPageDetail: {
        mustLogin: true,
        Com: MinisterPageDetail
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
                label: "人员审批",
                defaultPath: "PersonPage/0/0/0/0",
                pathName: "PersonPage/:workId/:status/:nodeName/:source",
                comKey: "PersonPage"
            },
            {
                label: "人员审批明细",
                defaultPath: "PersonPageDetail/0/0",
                pathName: "PersonPageDetail/:personInfoId/:source",
                comKey: "PersonPageDetail"
            },
            {
                label: "部门领导审批",
                defaultPath: "MinisterPage/0/0/0/0",
                pathName: "MinisterPage/:workId/:status/:nodeName/:source",
                comKey: "MinisterPage"
            },
            {
                label: "部门领导审批明细",
                defaultPath: "MinisterPageDetail/0/0",
                pathName: "MinisterPageDetail/:ministerInfoId/:source",
                comKey: "MinisterPageDetail"
            }
        ]
    },
    {
        label: "流程",
        children: [
            {
                label: "代办待办列表",
                defaultPath: "TabsListMobile",
                pathName: "TabsListMobile",
                comKey: "TabsListMobile"
            },
            {
                label: "待阅已阅列表",
                defaultPath: "ReadListMobile",
                pathName: "ReadListMobile",
                comKey: "ReadListMobile"
            },
            {
                label: '人员申请',
                defaultPath: "FlowBysanPerson/sanPerson/add/0",
                pathName: "FlowBysanPerson/:flowId/:workId/:title",
                comKey: "FlowBysanPerson"
            },
            {
                label: '部长申请',
                defaultPath: "FlowBysanMinister/sanMinister/add/0",
                pathName: "FlowBysanMinister/:flowId/:workId/:title",
                comKey: "FlowBysanMinister"
            }
        ]
    },
];
const reducers = {
    saveNodes(state = {}, action) {
        if (action.type === "saveNodes") {
            return action;
        } else {
            return state;
        }
    },
    saveNodesMinister(state = {}, action) {
        if (action.type === "saveNodes") {
            return action;
        } else {
            return state;
        }
    },
};
const actions = {
    saveNodes(allNode) {
        return { type: "saveNodes", node: allNode };
    },
    saveNodesMinister(allNode) {
        return { type: "saveNodes", node: allNode };
    },
};
const sagas = [];
const MyViews = {
    pageComs,
    routerInfo,
    reducers,
    sagas,
    actions
};
export default MyViews;
