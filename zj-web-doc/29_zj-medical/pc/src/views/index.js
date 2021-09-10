import { basicHoc } from "apih5/modules/layouts";
 import LoginPage from "./login";
import HomePage from "./home";
import ContactsPage from "./contacts";
import MenusPage from "./menus";
import RolesPage from "./roles";
import FlowType from "./FlowType";
import TrainList from "./TrainList";
import BaseCode from "./BaseCode"; 

import App from "./App";
import News from "./News";
import Add from "./Add";
import AddressBook from "./AddressBook";
import Me from "./Me";

import StatisticAnalysis from "./StatisticAnalysis";
import HospitalChoice from "./HospitalChoice";
import InterFaceManage from "./InterFaceManage";


//引用basicHoc进行简单封装
const basic = (Component,props = {

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
        Com: basic(HomePage)
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
    /* 共通页面over */
    StatisticAnalysis: {
        mustLogin: true,
        Com: basic(StatisticAnalysis)
    },
    HospitalChoice: {
        mustLogin: true,
        Com: basic(HospitalChoice)
    },
    InterFaceManage:{
        mustLogin: true,
        Com: InterFaceManage
    },
};

//遍历所有页面强制让页面包裹basic
let noIncs = ["Login"]; //不需要包裹baise的页面key
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
    //     label: "医院管理",
    //     children: [
    //         {
    //             label: "医院选择",
    //             defaultPath: "HospitalChoice",
    //             pathName: "HospitalChoice",
    //             comKey: "HospitalChoice"
    //         },
    //         {
    //             label: "统计分析",
    //             defaultPath: "StatisticAnalysis/mobile",
    //             pathName: "StatisticAnalysis/:meta",
    //             comKey: "StatisticAnalysis"
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
