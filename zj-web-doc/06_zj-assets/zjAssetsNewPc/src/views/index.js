import { lazy } from "react"
import { basicHoc } from "apih5/modules/layouts";
const LoginPage = lazy(() => import("./login"));
const HomePage = lazy(() => import("./home"));
const ContactsPage = lazy(() => import("./contacts"));
const MenusPage = lazy(() => import("./menus"));
const RolesPage = lazy(() => import("./roles"));
const FlowType = lazy(() => import("./FlowType"));
const TrainList = lazy(() => import("./TrainList"));
const BaseCode = lazy(() => import("./BaseCode"));

const Iframs = lazy(() => import("./Iframs"));

//引用basicHoc进行简单封装
const basic = (Component, props = {

    //是否开启待办数量
    // isOpenTodoNum:true,
    //获取待办数量的接口 
    // getTodoNumApiName:"getZjPrProgrammeTodoCount",
    //获取待办数量轮询时间间隔默认10 单位s
    // getTodoNumTime:60 * 30,

    //切换项目或者获取项目列表的接口
    // apiNames: {
    //     getCompanyList: "getZxQrcodePermissionObjectListByProject",
    //     changeCompany: "changeZxQrcodePermissionProject",

    //     //中交切换公司接口
    //     // changeCompany: "changeCompany",
    // },

    // //项目数据的一些字段名字
    projectKeys: {
        list: "companyList",
        value: "companyId",//levelId  projectId
        label: "companyName",//levelShortName  projectShortName
    },


    //说明
    //获取项目列表的方法 getCompanyListFn(cb)   
    //cb(当前项目, 项目列表) 必须执行
    // getCompanyListFn: null

    //中交切换项目案例 该方法存在getCompanyList配置将失去意义
    getCompanyListFn: (pageProps, cb) => {
        let {
            loginAndLogoutInfo: {
                loginInfo: {
                    userInfo: { companyList }
                }
            }
        } = pageProps;
        let _curCompany = null;


        companyList =
            companyList &&
            companyList.map(item => {
                const { companyName, companyId, companySelectFlag } = item;
                let _item = {
                    ...item,
                    value: companyId,
                    realLabel: companyName,
                    label: (companyName && companyName.length > 7) ? companyName.substr(0, 7) + '...' : companyName
                }

                if (companySelectFlag === 1) {
                    //当前选择公司
                    _curCompany = _item;
                }
                return {
                    ..._item
                };
            });

        if (!_curCompany && companyList && companyList.length) {
            _curCompany = companyList[0];
        }
        cb(_curCompany, companyList);
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

    /* 共通页面over */
    Iframs: {
        mustLogin: true,
        Com: Iframs
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
        label: "固定资产",
        children: [
            {
                label: "资产管理",
                showType:'0',
                children: [
                    {
                        label: "待办",
                        defaultPath: "todo",
                        pathName: "todo",
                        comKey: "Iframs",
                        url: "http://weixin.fheb.cn:99/zjAssetsPc/todo.html"
                    },
                    {
                        label: "已办",
                        defaultPath: "hasTodo",
                        pathName: "hasTodo",
                        comKey: "Iframs",
                        url: "http://weixin.fheb.cn:99/zjAssetsPc/hasTodo.html"
                    },
                    {
                        label: "资产管理",
                        defaultPath: "a",
                        pathName: "a",
                        comKey: "Iframs",
                        url: "http://weixin.fheb.cn:99/zjAssetsPc/assetsList.html"
                    },
                    {
                        label: "资产报废",
                        defaultPath: "a1",
                        pathName: "a1",
                        comKey: "Iframs",
                        url: "http://weixin.fheb.cn:99/zjAssetsPc/assetsScrapList.html"
                    },
                    {
                        label: "资产维修",
                        defaultPath: "a2",
                        pathName: "a2",
                        comKey: "Iframs",
                        url: "http://weixin.fheb.cn:99/zjAssetsPc/repairList.html"
                    },
                    {
                        label: "资产折旧",
                        defaultPath: "a3",
                        pathName: "a3",
                        comKey: "Iframs",
                        url: "http://weixin.fheb.cn:99/zjAssetsPc/discountList.html"
                    },
                    {
                        label: "资产盘点",
                        defaultPath: "a4",
                        pathName: "a4",
                        comKey: "Iframs",
                        url: "http://weixin.fheb.cn:99/zjAssetsPc/inventoryList.html"
                    },
                    {
                        label: "资产验收",
                        defaultPath: "a5",
                        pathName: "a5",
                        comKey: "Iframs",
                        url: "http://weixin.fheb.cn:99/zjAssetsPc/checkList.html"
                    },
                    {
                        label: "资产二维码",
                        defaultPath: "a6",
                        pathName: "a6",
                        comKey: "Iframs",
                        url: "http://weixin.fheb.cn:99/zjAssetsPc/twoDimensionCodeList.html"
                    },
                    {
                        label: "资产追溯",
                        defaultPath: "a7",
                        pathName: "a7",
                        comKey: "Iframs",
                        url: "http://weixin.fheb.cn:99/zjAssetsPc/asstesHistoryList.html"
                    },
                    {
                        label: "资产盘点任务",
                        defaultPath: "a8",
                        pathName: "a8",
                        comKey: "Iframs",
                        url: "http://weixin.fheb.cn:99/zjAssetsPc/assetsInventoryTask.html"
                    },
                    {
                        label: "资产对比",
                        defaultPath: "a9",
                        pathName: "a9",
                        comKey: "Iframs",
                        url: "http://weixin.fheb.cn:99/zjAssetsPc/assetComparisonList.html"
                    },
                    {
                        label: "浪潮管理",
                        defaultPath: "a10",
                        pathName: "a10",
                        comKey: "Iframs",
                        url: "http://weixin.fheb.cn:99/zjAssetsPc/lcAsstesList.html"
                    },
                ]
            },
            {
                label: "基础管理",
                showType:'0',
                children: [
                    {
                        label: "折旧年限",
                        defaultPath: "b",
                        pathName: "b",
                        comKey: "Iframs",
                        url: "http://weixin.fheb.cn:99/zjAssetsPc/zjZcDurableYearList.html",
                    },
                    {
                        label: "资产状态",
                        defaultPath: "b1",
                        pathName: "b1",
                        comKey: "Iframs",
                        url: "http://weixin.fheb.cn:99/zjAssetsPc/assetsStateList.html"
                    },
                    {
                        label: "购置方式",
                        defaultPath: "b2",
                        pathName: "b2",
                        comKey: "Iframs",
                        url: "http://weixin.fheb.cn:99/zjAssetsPc/buyMannerList.html"
                    },
                    {
                        label: "报废方式",
                        defaultPath: "b3",
                        pathName: "b3",
                        comKey: "Iframs",
                        url: "http://weixin.fheb.cn:99/zjAssetsPc/scrapTypeList.html"
                    },
                    {
                        label: "维修状态",
                        defaultPath: "b4",
                        pathName: "b4",
                        comKey: "Iframs",
                        url: "http://weixin.fheb.cn:99/zjAssetsPc/maintainStateList.html"
                    },
                    {
                        label: "单位编号",
                        defaultPath: "b5",
                        pathName: "b5",
                        comKey: "Iframs",
                        url: "http://weixin.fheb.cn:99/zjAssetsPc/unitList.html"
                    },
                    {
                        label: "供应商管理",
                        defaultPath: "b6",
                        pathName: "b6",
                        comKey: "Iframs",
                        url: "http://weixin.fheb.cn:99/zjAssetsPc/supplierList.html"
                    },
                    {
                        label: "品牌管理",
                        defaultPath: "b7",
                        pathName: "b7",
                        comKey: "Iframs",
                        url: "http://weixin.fheb.cn:99/zjAssetsPc/assetsBrandList.html"
                    },
                    {
                        label: "资产类型",
                        defaultPath: "b8",
                        pathName: "b8",
                        comKey: "Iframs",
                        url: "http://weixin.fheb.cn:99/zjAssetsPc/assetsTypeList.html"
                    },
                    {
                        label: "存放地点",
                        defaultPath: "b9",
                        pathName: "b9",
                        comKey: "Iframs",
                        url: "http://weixin.fheb.cn:99/zjAssetsPc/placeIList.html"
                    },
                    {
                        label: "权限管理",
                        defaultPath: "b10",
                        pathName: "b10",
                        comKey: "Iframs",
                        url: "http://weixin.fheb.cn:99/zjAssetsPc/CompanyPermissionList.html"
                    },
                    {
                        label: "部门管理",
                        defaultPath: "b11",
                        pathName: "b11",
                        comKey: "Iframs",
                        url: "http://weixin.fheb.cn:99/zjAssetsPc/departmentList.html"
                    },
                ]
            },
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
