import LoginPage from "./login";
import HomePage from "./home";
import { basic } from "./modules/layouts";
//pc端移动的自适应流程 监督计划
import FlowByJDJH from "./FlowByJDJH";
//pc端移动的自适应流程 执行情况
import FlowByZXQK from "./FlowByZXQK";
//流程
import FlowByJDJHForm from "./FlowByJDJH/form";
import FlowByZXQKForm from "./FlowByZXQK/form";
const pageComs = {
  Login: {
    mustLogin: false,
    Com: LoginPage
  },
  Home: {
    mustLogin: true,
    // Com: basic(HomePage)
    Com: HomePage
  },

  /* /共通页面 */
  FlowByJDJH: {
    mustLogin: true,
    Com: basic(FlowByJDJH)
  },
  FlowByZXQK: {
    mustLogin: true,
    Com: basic(FlowByZXQK)
  },
  FlowByJDJHForm: {
    mustLogin: true,
    Com: basic(FlowByJDJHForm)
  },
  FlowByZXQKForm: {
    mustLogin: true,
    Com: basic(FlowByZXQKForm)
  }
};
const routerInfo = [
  {
    label: "流程",
    children: [
      {
        label: "流程待办列表监督计划",
        defaultPath: "todoByyearSupPlan",
        pathName: "todoByyearSupPlan",
        comKey: "FlowByJDJH"
      },
      {
        label: "流程已办列表监督计划",
        defaultPath: "hasTodoByyearSupPlan",
        pathName: "hasTodoByyearSupPlan",
        comKey: "FlowByJDJH"
      },
      {
        label: "流程待办列表执行情况",
        defaultPath: "todoByyearSupPlanExecutive",
        pathName: "todoByyearSupPlanExecutive",
        comKey: "FlowByZXQK"
      },
      {
        label: "流程已办列表执行情况",
        defaultPath: "hasTodoByyearSupPlanExecutive",
        pathName: "hasTodoByyearSupPlanExecutive",
        comKey: "FlowByZXQK"
      },
      {
        label: "监督计划",
        defaultPath: "FlowByyearSupPlan/yearSupPlan/0/0",
        pathName: "FlowByyearSupPlan/:flowId/:workId/:title",
        comKey: "FlowByJDJHForm",
        hide: true
      },
      {
        label: "执行情况",
        defaultPath: "FlowByyearSupPlanExecutive/yearSupPlanExecutive/0/0",
        pathName: "FlowByyearSupPlanExecutive/:flowId/:workId/:title",
        comKey: "FlowByZXQKForm",
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
