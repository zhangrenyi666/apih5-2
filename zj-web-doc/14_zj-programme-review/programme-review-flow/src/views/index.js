import { basicHoc } from "apih5/modules/layouts";
import LoginPage from "./login";
import HomePage from "./home";
import ContactsPage from "./contacts";
import MenusPage from "./menus";
import RolesPage from "./roles";
// import BaseCode from "./BaseCode";
// import { basic,blank } from "./modules/layouts";
import detail from './detail';
import launch from './launch';
import reviewState from './reviewState';
import pJudgesTodo from './pJudgesTodo';
import pJudges from './pJudges';
import zjPrTodoList from './zjPrTodoList';
import zjPrAnonymous from './zjPrAnonymous';
import zjEvaluationInfo from './zjEvaluationInfo'; 
import detailList from './detailList';
import flowAdmin from './flowAdmin';
import zjPrHasTodoList from './zjPrHasTodoList';
import InterFaceManage from "./InterFaceManage";
import externalLaunch from "./externalLaunch";
import zjProjectSupervise from "./zjProjectSupervise";

//引用basicHoc进行简单封装
const basic = (Component,props = {

    //是否开启待办数量
    isOpenTodoNum:true,
    //获取待办数量的接口 
    getTodoNumApiName:"getZjPrProgrammeTodoCount",
    //获取待办数量轮询时间间隔默认10 单位s
    getTodoNumTime:60 * 30,

    //切换项目或者获取项目列表的接口
    apiNames: { 
        //中交切换公司接口
        changeCompany: "changeCompany",
    },

    //项目数据的一些字段名字
    projectKeys: {
        list: "companyList",
        value: "companyId",//levelId  projectId
        label: "companyName",//levelShortName  projectShortName
    },


    //获取项目列表的方法 getCompanyListFn(cb)   
    //cb(当前项目, 项目列表) 必须执行
    // getCompanyListFn: null

    //中交切换项目案例 该方法存在getCompanyList配置将失去意义
    getCompanyListFn: (pageProps,cb) => {
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
                const { companyName,companyId,companySelectFlag } = item;
                let _item = {
                    ...item,
                    value: companyId,
                    realLabel: companyName,
                    label: (companyName && companyName.length > 7) ? companyName.substr(0,7) + '...' : companyName
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
        cb(_curCompany,companyList);
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
    detail: {
        mustLogin: true,
        Com: detail
    },
    launch: {
        mustLogin: true,
        Com: launch
    },
    reviewState: {
        mustLogin: true,
        Com: reviewState
    },
    pJudges: {
        mustLogin: true,
        Com: pJudges
    },
    pJudgesTodo: {
        mustLogin: true,
        Com: pJudgesTodo
    },
    zjPrTodoList: {
        mustLogin: true,
        Com: zjPrTodoList
    },
    zjPrHasTodoList: {
        mustLogin: true,
        // Com: zjPrHasTodoList
        Com: zjPrTodoList
    },
    zjPrAnonymous: {
        mustLogin: true,
        Com: zjPrAnonymous
    },
    zjEvaluationInfo: {
        mustLogin: true,
        Com: zjEvaluationInfo
    },
    detailList: {
        mustLogin: true,
        Com: detailList
    },  
    InterFaceManage:{
        mustLogin: true,
        Com: InterFaceManage
    },                      
    flowAdmin: {
        mustLogin: true,
        Com: flowAdmin
    },                        
    externalLaunch: {
        mustLogin: true,
        Com: externalLaunch
    },                        
    zjProjectSupervise: {
        mustLogin: true,
        Com: zjProjectSupervise
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
