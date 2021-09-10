import LoginPage from "./login";
import HomePage from "./home";
import App from "./App";
import News from "./News";
import Add from "./Add";
import AddressBook from "./AddressBook";
import Me from "./Me";

import { basicHoc } from "apih5/modules/layouts";
//标准化资料管理
import safetyStandardizationList from './safetyStandardizationList';
import safetyStandardizationDetail from './safetyStandardizationList/detail';
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
    safetyStandardizationList: {
        mustLogin: true,
        Com: safetyStandardizationList
    },
    safetyStandardizationDetail: {
        mustLogin: true,
        Com: safetyStandardizationDetail
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
                comKey: "App"
            }
        ]
    },
    {
        label: "新闻",
        children: [
            {
                label: "新闻",
                defaultPath: "News",
                pathName: "News",
                comKey: "News"
            }
        ]
    },
    {
        label: "更多",
        children: [
            {
                label: "更多",
                defaultPath: "Add",
                pathName: "Add",
                comKey: "Add"
            }
        ]
    },
    {
        label: "通讯录",
        children: [
            {
                label: "AddressBook",
                defaultPath: "AddressBook",
                pathName: "AddressBook",
                comKey: "AddressBook"
            }
        ]
    },
    {
        label: "我的",
        children: [
            {
                label: "Me",
                defaultPath: "Me",
                pathName: "Me",
                comKey: "Me"
            }
        ]
    },
    {
        label: "安全标准化管理列表",
        children: [
            {
                label: "安全标准化管理列表",
                defaultPath: "safetyStandardizationList/0",
                pathName: "safetyStandardizationList/:levelId",
                comKey: "safetyStandardizationList"
            },
            {
                label: "安全标准化管理详情",
                defaultPath: "safetyStandardizationDetail/0/0",
                pathName: "safetyStandardizationDetail/:levelId/:databaseId",
                comKey: "safetyStandardizationDetail"
            }
        ]
    }
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
