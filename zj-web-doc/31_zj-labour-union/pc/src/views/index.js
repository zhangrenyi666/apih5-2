import LoginPage from "./login";
import HomePage from "./home";
import ContactsPage from "./contacts";
import MenusPage from "./menus";
import RolesPage from "./roles";
import FlowType from "./FlowType";
import TrainList from "./TrainList";
import BaseCode from "./BaseCode";
import SystemUserGroup from './SystemUserGroup';
import InterFaceManage from './InterFaceManage';
//各部门提案
import DepartmentalProposals from './DepartmentalProposals';
//公司汇总
import CompanySummary from './CompanySummary';
//公司工会主席
import ChairmanOfTheCompanyUnion from './ChairmanOfTheCompanyUnion';
//集团汇总
import TheGroupAggregation from './TheGroupAggregation';
//部门落实
import DepartmentToCarryOutThe from './DepartmentToCarryOutThe';
//集团立案
import GroupAcase from './GroupAcase';
//立案明细
import CaseListOfTheGroup from './GroupAcase/CaseListOfTheGroup';
import { basicHoc } from "apih5/modules/layouts";
//立案流程
import FlowByApplicationList from "./FlowByApplication";
//提案汇总
import BillSummaryList from './BillSummaryList';
//完成情况汇总
import PerformanceList from './PerformanceList';
//引用basicHoc进行简单封装
const basic = (Component, props = {

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
    /* 共通页面over */
    DepartmentalProposals: {
        mustLogin: true,
        Com: DepartmentalProposals
    },
    CompanySummary: {
        mustLogin: true,
        Com: CompanySummary
    },
    ChairmanOfTheCompanyUnion: {
        mustLogin: true,
        Com: ChairmanOfTheCompanyUnion
    },
    TheGroupAggregation: {
        mustLogin: true,
        Com: TheGroupAggregation
    },
    DepartmentToCarryOutThe: {
        mustLogin: true,
        Com: DepartmentToCarryOutThe
    },
    GroupAcase: {
        mustLogin: true,
        Com: GroupAcase
    },
    CaseListOfTheGroup: {
        mustLogin: true,
        Com: CaseListOfTheGroup
    },
    FlowByApplicationList: {
        mustLogin: true,
        Com: FlowByApplicationList
    },
    BillSummaryList: {
        mustLogin: true,
        Com: BillSummaryList
    },
    PerformanceList: {
        mustLogin: true,
        Com: PerformanceList
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
    // {
    //     label: "职工代表提案",
    //     children: [
    //         {
    //             label: "职工代表提案",
    //             defaultPath: "DepartmentalProposals",
    //             pathName: "DepartmentalProposals",
    //             comKey: "DepartmentalProposals"
    //         },
    //         {
    //             label: "公司汇总",
    //             defaultPath: "CompanySummary",
    //             pathName: "CompanySummary",
    //             comKey: "CompanySummary"
    //         },
    //         {
    //             label: "公司工会主席",
    //             defaultPath: "ChairmanOfTheCompanyUnion",
    //             pathName: "ChairmanOfTheCompanyUnion",
    //             comKey: "ChairmanOfTheCompanyUnion"
    //         },
    //         {
    //             label: "集团公司汇总",
    //             defaultPath: "TheGroupAggregation",
    //             pathName: "TheGroupAggregation",
    //             comKey: "TheGroupAggregation"
    //         },
    //         {
    //             label: "立案流程发起",
    //             defaultPath: "GroupAcase",
    //             pathName: "GroupAcase",
    //             comKey: "GroupAcase",
    //         },
    //         {
    //             label: "立案明细",
    //             defaultPath: "CaseListOfTheGroup/0/0/0",
    //             pathName: "CaseListOfTheGroup/:applicationId/:workId/:apih5FlowStatus",
    //             comKey: "CaseListOfTheGroup",
    //             hide: true
    //         },
    //         {
    //             label: "部门落实",
    //             defaultPath: "DepartmentToCarryOutThe",
    //             pathName: "DepartmentToCarryOutThe",
    //             comKey: "DepartmentToCarryOutThe"
    //         },
    //         {
    //             label: '流程待办',
    //             defaultPath: "FlowByApplicationAwait",
    //             pathName: "FlowByApplicationAwait",
    //             comKey: "FlowByApplicationList",
    //         },
    //         {
    //             label: '流程已办',
    //             defaultPath: "FlowByApplicationOver",
    //             pathName: "FlowByApplicationOver",
    //             comKey: "FlowByApplicationList",
    //         },
    //         {
    //             label: '提案汇总列表',
    //             defaultPath: "BillSummaryList",
    //             pathName: "BillSummaryList",
    //             comKey: "BillSummaryList",
    //         },
    //         {
    //             label: '完成情况汇总列表',
    //             defaultPath: "PerformanceList",
    //             pathName: "PerformanceList",
    //             comKey: "PerformanceList",
    //         }
    //     ]
    // },
    // {
    //     label: "字典管理",
    //     children: [
    //         {
    //             label: "常用编码",
    //             defaultPath: "BaseCode",
    //             pathName: "BaseCode",
    //             comKey: "BaseCode"
    //         }
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
