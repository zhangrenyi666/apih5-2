import { lazy } from "react";
import { basicHoc } from "apih5/modules/layouts"; 
import UserCountListByReviewer from "./userCountListByReviewer";

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
        Com: lazy(() => import("./login"))
    },
    Home: {
        mustLogin: true,
        Com: lazy(() => import("./userCountListByReviewer"))
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
    GenerateQnnTable: {
        mustLogin: true,
        Com: lazy(() => import("apih5/pages/GenerateQnnTablePage"))
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


    UserCountListByReviewer: {
        mustLogin: true,
        Com: basic(UserCountListByReviewer)
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
        label: "信息化考核统计列表",
        defaultPath: "/",
        pathName: "/",
        comKey: "UserCountListByReviewer" 
    }
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
