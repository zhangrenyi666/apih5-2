import { lazy } from "react";
import { basicHoc } from "apih5/modules/layouts"; 


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

    ConsumablesSettings: {
        mustLogin: true,
        Com: lazy(() => import("./ConsumablesSettings/indexTree"))
        // Com: lazy(() => import("./ConsumablesSettings"))
    },
    ConsumablesStorageAccount: {
        mustLogin: true,
        Com: lazy(() => import("./ConsumablesStorageAccount"))
    },
    Stock: {
        mustLogin: true,
        Com: lazy(() => import("./Stock"))
    },
    ConsumablesApplicationAccount: {
        mustLogin: true,
        Com: lazy(() => import("./ConsumablesApplicationAccount"))
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
        label: "耗材领用",
        children: [
            {
                label: "耗材设置",
                defaultPath: "ConsumablesSettings",
                pathName: "ConsumablesSettings",
                comKey: "ConsumablesSettings"
            },
            {
                label: "耗材入库台账",
                defaultPath: "ConsumablesStorageAccount",
                pathName: "ConsumablesStorageAccount",
                comKey: "ConsumablesStorageAccount"
            },
            {
                label: "耗材申领台账",
                defaultPath: "ConsumablesApplicationAccount",
                pathName: "ConsumablesApplicationAccount",
                comKey: "ConsumablesApplicationAccount"
            },
            {
                label: "库存表",
                defaultPath: "Stock",
                pathName: "Stock",
                comKey: "Stock"
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
