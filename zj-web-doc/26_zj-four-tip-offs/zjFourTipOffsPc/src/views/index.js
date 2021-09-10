import { lazy } from "react"
import { basicHoc } from "apih5/modules/layouts";
 import LoginPage from "./login";
const HomePage = lazy(() => import("./home"));
const ContactsPage = lazy(() => import("./contacts"));
const MenusPage = lazy(() => import("./menus"));
const RolesPage = lazy(() => import("./roles"));
const FlowType = lazy(() => import("./FlowType"));
const TrainList = lazy(() => import("./TrainList"));
const InterFaceManage = lazy(() => import('./InterFaceManage'));
//技术质量举报
const zjFourTipOffsAutonym = lazy(() => import("./zjFourTipOffsAutonym"));
const zjFourTipOffsCryptonym = lazy(() => import("./zjFourTipOffsCryptonym"));
//违规违纪举报
const zjFourTipOffsAutonymW = lazy(() => import("./zjFourTipOffsAutonymW"));
const zjFourTipOffsCryptonymW = lazy(() => import("./zjFourTipOffsCryptonymW"));
//安全隐患举报
const zjFourTipOffsAutonymA = lazy(() => import("./zjFourTipOffsAutonymA"));
const zjFourTipOffsCryptonymA = lazy(() => import("./zjFourTipOffsCryptonymA"));
//服务
const zjFourTipOffsProblemFeedback = lazy(() => import("./zjFourTipOffsProblemFeedback"));
//曝光台
const zjFourTipOffsTypicalProblemExpose = lazy(() => import("./zjFourTipOffsTypicalProblemExpose"));
const zjFourTipOffsReportWinner = lazy(() => import("./zjFourTipOffsReportWinner"));
const zjFourTipOffsContributeWinner = lazy(() => import("./zjFourTipOffsContributeWinner"));
//基础设置
const zjFourTipOffsServiceType = lazy(() => import("./zjFourTipOffsServiceType"));
const zjFourTipOffsNoticeForUse = lazy(() => import("./zjFourTipOffsNoticeForUse"));
const zjFourTipOffsTheProblemLevel = lazy(() => import("./zjFourTipOffsTheProblemLevel"));
const zjFourTipOffsGradeOfService = lazy(() => import("./zjFourTipOffsGradeOfService"));
//字典管理
const BaseCode = lazy(() => import('./BaseCode'));

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
	FlowType: {
        mustLogin: true,
        Com: FlowType
    },
    TrainList: {
        mustLogin: true,
        Com: TrainList
	},
	InterFaceManage: {
        mustLogin: true,
        Com: InterFaceManage
    },
	zjFourTipOffsAutonym: {
		mustLogin: true,
		Com: zjFourTipOffsAutonym
	},
	zjFourTipOffsCryptonym: {
		mustLogin: true,
		Com: zjFourTipOffsCryptonym
	},
	zjFourTipOffsAutonymW: {
		mustLogin: true,
		Com: zjFourTipOffsAutonymW
	},
	zjFourTipOffsCryptonymW: {
		mustLogin: true,
		Com: zjFourTipOffsCryptonymW
	},
	zjFourTipOffsAutonymA: {
		mustLogin: true,
		Com: zjFourTipOffsAutonymA
	},
	zjFourTipOffsCryptonymA: {
		mustLogin: true,
		Com: zjFourTipOffsCryptonymA
	},
	zjFourTipOffsServiceType: {
		mustLogin: true,
		Com: zjFourTipOffsServiceType
	},
	zjFourTipOffsNoticeForUse: {
		mustLogin: true,
		Com: zjFourTipOffsNoticeForUse
	},
	zjFourTipOffsProblemFeedback: {
		mustLogin: true,
		Com: zjFourTipOffsProblemFeedback
	},
	zjFourTipOffsTypicalProblemExpose: {
		mustLogin: true,
		Com: zjFourTipOffsTypicalProblemExpose
	},
	zjFourTipOffsReportWinner: {
		mustLogin: true,
		Com: zjFourTipOffsReportWinner
	},
	zjFourTipOffsContributeWinner: {
		mustLogin: true,
		Com: zjFourTipOffsContributeWinner
	},
	zjFourTipOffsTheProblemLevel: {
		mustLogin: true,
		Com: zjFourTipOffsTheProblemLevel
	},
	zjFourTipOffsGradeOfService: {
		mustLogin: true,
		Com: zjFourTipOffsGradeOfService
	},
	BaseCode: {
		mustLogin: true,
		Com: basic(BaseCode)
	}
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
