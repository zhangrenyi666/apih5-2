import LoginPage from "./login";
import HomePage from "./home";
import ContactsPage from "./contacts";
import MenusPage from "./menus";
import RolesPage from "./roles";
import FlowType from "./FlowType";
import TrainList from "./TrainList";
import BaseCode from "./BaseCode";

import { basicHoc } from "apih5/modules/layouts";

import Iframs from "./Iframs";

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
//    {
//         label: "事项申请", 
//         children: [
//             {
//                 label: "用印申请",
//                 defaultPath: "approve",
//                 pathName: "approve",
//                 comKey: "Iframs",
//                 url: "http://weixin.fheb.cn:99/flow-matter/approve.html"
//             },
//             {
//                 label: "外携用印申请",
//                 defaultPath: "zjYyOutSealApplyApprove",
//                 pathName: "zjYyOutSealApplyApprove",
//                 comKey: "Iframs",
//                 url: "http://weixin.fheb.cn:99/flow-matter/zjYyOutSealApplyApprove.html"
//             },
//             {
//                 label: "因私出国(机关)申请",
//                 defaultPath: "zjFlowGoAbroadApplyJgApprove",
//                 pathName: "zjFlowGoAbroadApplyJgApprove",
//                 comKey: "Iframs",
//                 url: "http://weixin.fheb.cn:99/flow-matter/zjFlowGoAbroadApplyJgApprove.html"
//             },
//             {
//                 label: "因私出国(各单位)申请",
//                 defaultPath: "zjFlowGoAbroadApplyApprove",
//                 pathName: "zjFlowGoAbroadApplyApprove",
//                 comKey: "Iframs",
//                 url: "http://weixin.fheb.cn:99/flow-matter/zjFlowGoAbroadApplyApprove.html"
//             },
//             {
//                 label: "纪委用印申请",
//                 defaultPath: "zjFlowJwSealApplyApprove",
//                 pathName: "zjFlowJwSealApplyApprove",
//                 comKey: "Iframs",
//                 url: "http://weixin.fheb.cn:99/flow-matter/zjFlowJwSealApplyApprove.html"
//             }
//         ]
//     },
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
