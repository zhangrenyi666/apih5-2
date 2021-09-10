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
// import GenerateQnnTable from "./GenerateQnnTablePage";
const InterFaceManage = lazy(() => import("./InterFaceManage"));
const GoodsYear = lazy(() => import("./GoodsYear"));
const Goods = lazy(() => import("./Goods"));
const GoodsList = lazy(() => import("./GoodsList"));
 


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
    }

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
    // GenerateQnnTable:{ 
    //     mustLogin: true,
    //     Com: GenerateQnnTable
    // },
    InterFaceManage:{
        mustLogin: true,
        Com: InterFaceManage
    },
    GoodsYear: {
        mustLogin: true,
        Com: GoodsYear   
    },
    Goods: {
        mustLogin: true,
        Com: Goods
    },
    GoodsList: {
        mustLogin: true,
        Com: GoodsList
    }
    /* 共通页面over */


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
    //     label: "公会物品发放",
    //     children: [
    //         {
    //             label: "年份设置",
    //             defaultPath: "GoodsYear",
    //             pathName: "GoodsYear",
    //             comKey: "GoodsYear"
    //         },
    //         {
    //             label: "物品发放管理",
    //             defaultPath: "Goods",
    //             pathName: "Goods",
    //             comKey: "Goods"
    //         },
    //         {
    //             label: "编码查看",
    //             defaultPath: "GoodsList",
    //             pathName: "GoodsList",
    //             comKey: "GoodsList"
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
