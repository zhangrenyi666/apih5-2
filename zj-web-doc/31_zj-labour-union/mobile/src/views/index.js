import LoginPage from "./login";
import HomePage from "./home";

import { basicHoc } from "apih5/modules/layouts";
//工会主席审批
import ChairmanOfTheCompanyUnion from './ChairmanOfTheCompanyUnion';
import ChairmanOfTheCompanyUnionDetail from './ChairmanOfTheCompanyUnion/ChairmanOfTheCompanyUnionDetail';
import ChairmanOfTheCompanyUnionEdit from './ChairmanOfTheCompanyUnion/ChairmanOfTheCompanyUnionEdit';
//立案流程
import FlowByApplication from "./FlowByApplication";
import FlowFormByApplication from './FlowByApplication/form';
//部门落实流程
import FlowByProposal from "./FlowByProposal";
import FlowFormByProposal from './FlowByProposal/form';
//立案明细
import CaseListOfTheGroup from './CaseListOfTheGroup';
import CaseListOfTheGroupDetail from './CaseListOfTheGroup/detail';
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
    // projectKeys: {
    //     list: "projectList",
    //     value: "levelId",//levelId  projectId
    //     label: "levelShortName",//levelShortName  projectShortName
    // },


    //说明
    //获取项目列表的方法 getCompanyListFn(cb)   
    //cb(当前项目, 项目列表) 必须执行
    // getCompanyListFn: null

    // 中交切换项目案例 该方法存在getCompanyList配置将失去意义
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
    /* 共通页面over */
    ChairmanOfTheCompanyUnion: {
        mustLogin: true,
        Com: ChairmanOfTheCompanyUnion
    },
    ChairmanOfTheCompanyUnionDetail: {
        mustLogin: true,
        Com: ChairmanOfTheCompanyUnionDetail
    },
    ChairmanOfTheCompanyUnionEdit: {
        mustLogin: true,
        Com: ChairmanOfTheCompanyUnionEdit
    },
    FlowByApplication: {
        mustLogin: true,
        Com: FlowByApplication
    },
    FlowFormByApplication: {
        mustLogin: true,
        Com: FlowFormByApplication
    },
    FlowByProposal: {
        mustLogin: true,
        Com: FlowByProposal
    },
    FlowFormByProposal: {
        mustLogin: true,
        Com: FlowFormByProposal
    },
    CaseListOfTheGroup: {
        mustLogin: true,
        Com: CaseListOfTheGroup
    },
    CaseListOfTheGroupDetail: {
        mustLogin: true,
        Com: CaseListOfTheGroupDetail
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
        defaultPath: "Home",
        pathName: "Home",
        comKey: "Home",
        children: [
            {
                label: "公司工会主席审批列表",
                defaultPath: "ChairmanOfTheCompanyUnion",
                pathName: "ChairmanOfTheCompanyUnion",
                comKey: "ChairmanOfTheCompanyUnion"
            },
            {
                label: "公司工会主席审批详情",
                defaultPath: "ChairmanOfTheCompanyUnionDetail/0",
                pathName: "ChairmanOfTheCompanyUnionDetail/:proposalId",
                comKey: "ChairmanOfTheCompanyUnionDetail"
            },
            {
                label: "公司工会主席审批回复",
                defaultPath: "ChairmanOfTheCompanyUnionEdit/0",
                pathName: "ChairmanOfTheCompanyUnionEdit/:proposalId",
                comKey: "ChairmanOfTheCompanyUnionEdit"
            },
            {
                label: "立案待办列表",
                defaultPath: "FlowByApplicationAwait",
                pathName: "FlowByApplicationAwait",
                comKey: "FlowByApplication"
            },
            {
                label: "立案已办列表",
                defaultPath: "FlowByApplicationOver",
                pathName: "FlowByApplicationOver",
                comKey: "FlowByApplication"
            },
            {
                label: "立案流程发起",
                defaultPath: "FlowFormByApplication/zjLabourUnionFlowApplication/add/0",
                pathName: "FlowFormByApplication/:flowId/:workId/:title",
                comKey: "FlowFormByApplication",
                hide: true
            },
            {
                label: "部门落实待办列表",
                defaultPath: "FlowByProposalAwait",
                pathName: "FlowByProposalAwait",
                comKey: "FlowByProposal"
            },
            {
                label: "部门落实已办列表",
                defaultPath: "FlowByProposalOver",
                pathName: "FlowByProposalOver",
                comKey: "FlowByProposal"
            },
            {
                label: "部门落实流程发起",
                defaultPath: "FlowFormByProposal/zjLabourUnionProposal/add/0",
                pathName: "FlowFormByProposal/:flowId/:workId/:title",
                comKey: "FlowFormByProposal",
                hide: true
            },
            {
                label: "立案明细列表",
                defaultPath: "CaseListOfTheGroup/0/0",
                pathName: "CaseListOfTheGroup/:workId/:router",
                comKey: "CaseListOfTheGroup"
            },
            {
                label: "立案明细详情",
                defaultPath: "CaseListOfTheGroupDetail/0",
                pathName: "CaseListOfTheGroupDetail/:proposalId",
                comKey: "CaseListOfTheGroupDetail"
            }
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
