import { lazy } from "react"
import { basicHoc } from "apih5/modules/layouts";
 import LoginPage from "./login";
const HomePage = lazy(() => import("./home"));
//pc端移动的自适应流程 海威资产购置--公司
const FlowByZjHwzcZCGL = lazy(() => import("./FlowByZjHwzcZCGL"));
//pc端移动的自适应流程 海威资产项目购置
const FlowByZjHwzcZCGLForPro = lazy(() => import("./FlowByZjHwzcZCGLForPro"));
//pc端移动的自适应流程 海威资产采购入库
const FlowByZjHwzcZCYS = lazy(() => import("./FlowByZjHwzcZCYS"));
//pc端移动的自适应流程 海威资产项目调拨
const FlowByZjHwzcZCDBForPro = lazy(() => import("./FlowByZjHwzcZCDBForPro"));
//pc端移动的自适应流程 海威资产公司调拨
const FlowByZjHwzcZCDBForCom = lazy(() => import("./FlowByZjHwzcZCDBForCom"));
//pc端移动的自适应流程 海威资产项目报废
const FlowByZjHwzcZCBFForPro = lazy(() => import("./FlowByZjHwzcZCBFForPro"));
//pc端移动的自适应流程 海威资产公司报废
const FlowByZjHwzcZCBFForCom = lazy(() => import("./FlowByZjHwzcZCBFForCom"));

//流程
const FlowByZjHwzcZCGLForm = lazy(() => import("./FlowByZjHwzcZCGL/form"));
const FlowByZjHwzcZCGLForProForm = lazy(() => import("./FlowByZjHwzcZCGLForPro/form"));
const FlowByZjHwzcZCYSForm = lazy(() => import("./FlowByZjHwzcZCYS/form"));
const FlowByZjHwzcZCDBForProForm = lazy(() => import("./FlowByZjHwzcZCDBForPro/form"));
const FlowByZjHwzcZCDBForComForm = lazy(() => import("./FlowByZjHwzcZCDBForCom/form"));
const FlowByZjHwzcZCBFForProForm = lazy(() => import("./FlowByZjHwzcZCBFForPro/form"));
const FlowByZjHwzcZCBFForComForm = lazy(() => import("./FlowByZjHwzcZCBFForCom/form"));


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
    Com: HomePage
  },

  /* /共通页面 */
  FlowByZjHwzcZCGL: {
    mustLogin: true,
    Com: basic(FlowByZjHwzcZCGL)
  },
  FlowByZjHwzcZCGLForPro: {
    mustLogin: true,
    Com: basic(FlowByZjHwzcZCGLForPro)
  },   
  FlowByZjHwzcZCYS: {
    mustLogin: true,
    Com: basic(FlowByZjHwzcZCYS)
  },
  FlowByZjHwzcZCDBForPro: {
    mustLogin: true,
    Com: basic(FlowByZjHwzcZCDBForPro)
  },
  FlowByZjHwzcZCDBForCom: {
    mustLogin: true,
    Com: basic(FlowByZjHwzcZCDBForCom)
  }, 
  FlowByZjHwzcZCBFForPro: {
    mustLogin: true,
    Com: basic(FlowByZjHwzcZCBFForPro)
  },
  FlowByZjHwzcZCBFForCom: {
    mustLogin: true,
    Com: basic(FlowByZjHwzcZCBFForCom)
  },  
 // ***********************
  FlowByZjHwzcZCGLForm: {
    mustLogin: true,
    Com: basic(FlowByZjHwzcZCGLForm)
  },
  FlowByZjHwzcZCGLForProForm: {
    mustLogin: true,
    Com: basic(FlowByZjHwzcZCGLForProForm)
  },
  FlowByZjHwzcZCYSForm: {
    mustLogin: true,
    Com: basic(FlowByZjHwzcZCYSForm)
  },
  FlowByZjHwzcZCDBForProForm: {
    mustLogin: true,
    Com: basic(FlowByZjHwzcZCDBForProForm)
  },
  FlowByZjHwzcZCDBForComForm: {
    mustLogin: true,
    Com: basic(FlowByZjHwzcZCDBForComForm)
  },
  FlowByZjHwzcZCBFForProForm: {
    mustLogin: true,
    Com: basic(FlowByZjHwzcZCBFForProForm)
  },
  FlowByZjHwzcZCBFForComForm: {
    mustLogin: true,
    Com: basic(FlowByZjHwzcZCBFForComForm)
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
  {
    label: "流程",
    children: [
	   {
        label: "待办-海威资产购置--公司",
        defaultPath: "todoBycropPurchasingProcess",
        pathName: "todoBycropPurchasingProcess",
        comKey: "FlowByZjHwzcZCGL"
      },
	  {
        label: "已办-海威资产购置--公司",
        defaultPath: "hasTodoBycropPurchasingProcess",
        pathName: "hasTodoBycropPurchasingProcess",
        comKey: "FlowByZjHwzcZCGL"
      },
	  {
        label: "待办-海威资产购置--项目",
        defaultPath: "todoByprojectDepPurchasingProcess",
        pathName: "todoByprojectDepPurchasingProcess",
        comKey: "FlowByZjHwzcZCGLForPro"
      },
	  {
        label: "已办-海威资产购置--项目",
        defaultPath: "hasTodoByprojectDepPurchasingProcess",
        pathName: "hasTodoByprojectDepPurchasingProcess",
        comKey: "FlowByZjHwzcZCGLForPro"
      },
	  {
        label: "待办-海威资产采购入库",
        defaultPath: "todoBypurchasingSystem",
        pathName: "todoBypurchasingSystem",
        comKey: "FlowByZjHwzcZCYS"
      },
	  {
        label: "已办-海威资产采购入库",
        defaultPath: "hasTodoBypurchasingSystem",
        pathName: "hasTodoBypurchasingSystem",
        comKey: "FlowByZjHwzcZCYS"
      }, 
	  {
        label: "待办-海威资产项目调拨",
        defaultPath: "todoByprojectTransfer",
        pathName: "todoByprojectTransfer",
        comKey: "FlowByZjHwzcZCDBForPro"
      },
	  {
        label: "已办-海威资产项目调拨",
        defaultPath: "hasTodoByprojectTransfer",
        pathName: "hasTodoByprojectTransfer",
        comKey: "FlowByZjHwzcZCDBForPro"
      },
	  {
        label: "待办-海威资产公司调拨",
        defaultPath: "todoBycropTransfers",
        pathName: "todoBycropTransfers",
        comKey: "FlowByZjHwzcZCDBForCom"
      },
	  {
        label: "已办-海威资产公司调拨",
        defaultPath: "hasTodoBycropTransfers",
        pathName: "hasTodoBycropTransfers",
        comKey: "FlowByZjHwzcZCDBForCom"
      },
	  {
        label: "待办-海威资产项目报废",
        defaultPath: "todoByprojectScrap",
        pathName: "todoByprojectScrap",
        comKey: "FlowByZjHwzcZCBFForPro"
      },
	  {
        label: "已办-海威资产项目报废",
        defaultPath: "hasTodoByprojectScrap",
        pathName: "hasTodoByprojectScrap",
        comKey: "FlowByZjHwzcZCBFForPro"
      },
	  {
        label: "待办-海威资产公司报废",
        defaultPath: "todoBycropScrap",
        pathName: "todoBycropScrap",
        comKey: "FlowByZjHwzcZCBFForCom"
      },
	  {
        label: "已办-海威资产公司报废",
        defaultPath: "hasTodoBycropScrap",
        pathName: "hasTodoBycropScrap",
        comKey: "FlowByZjHwzcZCBFForCom"
      },
//*************************************
	  {
        label: "海威资产购置--公司",
defaultPath:          "FlowBycropPurchasingProcess/cropPurchasingProcess/0/0/0",
pathName:          "FlowBycropPurchasingProcess/:flowId/:workId/:title/:status",
        comKey: "FlowByZjHwzcZCGLForm",
        hide: true
      },
	  {
        label: "海威资产购置--项目",
defaultPath:          "FlowByprojectDepPurchasingProcess/projectDepPurchasingProcess/0/0/0",
pathName:          "FlowByprojectDepPurchasingProcess/:flowId/:workId/:title/:status",
        comKey: "FlowByZjHwzcZCGLForProForm",
        hide: true
      },
	  {
        label: "海威资产采购入库",
defaultPath:          "FlowBypurchasingSystem/purchasingSystem/0/0/0",
pathName:          "FlowBypurchasingSystem/:flowId/:workId/:title/:status",
        comKey: "FlowByZjHwzcZCYSForm",
        hide: true
      },
	  {
        label: "海威资产项目调拨",
defaultPath:          "FlowByprojectTransfer/projectTransfer/0/0/0",
pathName:          "FlowByprojectTransfer/:flowId/:workId/:title/:status",
        comKey: "FlowByZjHwzcZCDBForProForm",
        hide: true
      },
	  {
        label: "海威资产项目调拨",
defaultPath:          "FlowBycropTransfers/cropTransfers/0/0/0",
pathName:          "FlowBycropTransfers/:flowId/:workId/:title/:status",
        comKey: "FlowByZjHwzcZCDBForComForm",
        hide: true
      },
	  {
        label: "海威资产项目报废",
defaultPath:          "FlowByprojectScrap/projectScrap/0/0/0",
pathName:          "FlowByprojectScrap/:flowId/:workId/:title/:status",
        comKey: "FlowByZjHwzcZCBFForProForm",
        hide: true
      },
	  {
        label: "海威资产公司报废",
defaultPath:          "FlowBycropScrap/cropScrap/0/0/0",
pathName:          "FlowBycropScrap/:flowId/:workId/:title/:status",
        comKey: "FlowByZjHwzcZCBFForComForm",
        hide: true
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
