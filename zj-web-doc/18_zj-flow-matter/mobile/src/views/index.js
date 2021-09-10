import LoginPage from "./login";
import HomePage from "./home";
import { basic } from "./modules/layouts";
//pc端移动的自适应流程 党费
// import FlowByDF from "./FlowByDF";
// //pc端移动的自适应流程 用印
// import FlowByYY from "./FlowByYY";
// //pc端移动的自适应流程 外协用印
// import FlowByWXYY from "./FlowByWXYY";
// //pc端移动的自适应流程 会议室
import FlowByHYS from "./FlowByHYS";
// //pc端移动的自适应流程 会议室
import FlowByHYSJH from "./FlowByHYSJH";
import FlowByHYSNDJH from "./FlowByHYSNDJH";
import FlowByHYSPlan from "./FlowByHYSPlan";
// //pc端移动的自适应流程 监督计划
// import FlowByJDJH from "./FlowByJDJH";
// //pc端移动的自适应流程 执行情况
// import FlowByZXQK from "./FlowByZXQK";
// //pc端移动的自适应流程 信息化需求确认
// import FlowByXQQR from "./FlowByXQQR";
// //pc端移动的自适应流程 请假申请
// import FlowByLeave from "./FlowByLeave";
// //pc端移动的自适应流程 加班申请
// import FlowByOvertime from "./FlowByOvertime";
// //pc端移动的自适应流程 出差申请
// import FlowByTrip from "./FlowByTrip";
// //pc端移动的自适应流程 纪委用印申请
// import FlowByJwSeal from "./FlowByJwSeal";
// //pc端移动的自适应流程 局机关事务印申请
// import FlowByAffairs from "./FlowByAffairs";
// //pc端移动的自适应流程 因私出国申请（各单位）
// import FlowByGoAbroad from "./FlowByGoAbroad";
// //pc端移动的自适应流程 因私出国申请（机关）
// import FlowByGoAbroadJg from "./FlowByGoAbroadJg";
// //流程
// import FlowByDFForm from "./FlowByDF/form";
// import FlowByYYForm from "./FlowByYY/form";
// import FlowByWXYYForm from "./FlowByWXYY/form";
import FlowByHYSForm from "./FlowByHYS/form";
import FlowByHYSJHForm from "./FlowByHYSJH/form";
import FlowByHYSNDJHForm from "./FlowByHYSNDJH/form";
import FlowByHYSPlanForm from "./FlowByHYSPlan/form";
// import FlowByJDJHForm from "./FlowByJDJH/form";
// import FlowByZXQKForm from "./FlowByZXQK/form";
// import FlowByXQQRForm from "./FlowByXQQR/form";
// import FlowByLeaveForm from "./FlowByLeave/form";
// import FlowByOvertimeForm from "./FlowByOvertime/form";
// import FlowByTripForm from "./FlowByTrip/form";
// import FlowByJwSealForm from "./FlowByJwSeal/form";
// import FlowByAffairsForm from "./FlowByAffairs/form";
// import FlowByGoAbroadForm from "./FlowByGoAbroad/form";
// import FlowByGoAbroadJgForm from "./FlowByGoAbroadJg/form";
//会议报名列表
import Principles from './Principles';
import signInss from './Principles/signIns';
import largeSignIns from './Principles/largeSignIn';
import signInDetail from './Principles/signInDetail';
import signInsDetail from './Principles/signInsDetail';

//会议室详情
import meetingRoom from './meetingRoom';
//年会议审批列表
import worldYouthMeeting from './worldYouthMeeting';
import worldYouthMeetingDetail from './worldYouthMeeting/detail';
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
  // FlowByDF: {
  //   mustLogin: true,
  //   Com: basic(FlowByDF)
  // },
  // FlowByYY: {
  //   mustLogin: true,
  //   Com: basic(FlowByYY)
  // },
  // FlowByWXYY: {
  //   mustLogin: true,
  //   Com: basic(FlowByWXYY)
  // },
  FlowByHYS: {
    mustLogin: true,
    Com: basic(FlowByHYS)
  },
  FlowByHYSJH: {
    mustLogin: true,
    Com: basic(FlowByHYSJH)
  },
  FlowByHYSNDJH: {
    mustLogin: true,
    Com: basic(FlowByHYSNDJH)
  },
  FlowByHYSPlan: {
    mustLogin: true,
    Com: basic(FlowByHYSPlan)
  },
  // FlowByJDJH: {
  //   mustLogin: true,
  //   Com: basic(FlowByJDJH)
  // },
  // FlowByZXQK: {
  //   mustLogin: true,
  //   Com: basic(FlowByZXQK)
  // },
  // FlowByXQQR: {
  //   mustLogin: true,
  //   Com: basic(FlowByXQQR)
  // },
  // FlowByLeave: {
  //   mustLogin: true,
  //   Com: basic(FlowByLeave)
  // },
  // FlowByOvertime: {
  //   mustLogin: true,
  //   Com: basic(FlowByOvertime)
  // },
  // FlowByTrip: {
  //   mustLogin: true,
  //   Com: basic(FlowByTrip)
  // },
  // FlowByJwSeal: {
  //   mustLogin: true,
  //   Com: basic(FlowByJwSeal)
  // },
  // FlowByAffairs: {
  //   mustLogin: true,
  //   Com: basic(FlowByAffairs)
  // },
  // FlowByGoAbroad: {
  //   mustLogin: true,
  //   Com: basic(FlowByGoAbroad)
  // },
  // FlowByGoAbroadJg: {
  //   mustLogin: true,
  //   Com: basic(FlowByGoAbroadJg)
  // },
  // //
  // FlowByDFForm: {
  //   mustLogin: true,
  //   Com: basic(FlowByDFForm)
  // },
  // FlowByYYForm: {
  //   mustLogin: true,
  //   Com: basic(FlowByYYForm)
  // },
  // FlowByWXYYForm: {
  //   mustLogin: true,
  //   Com: basic(FlowByWXYYForm)
  // },
  FlowByHYSForm: {
    mustLogin: true,
    Com: basic(FlowByHYSForm)
  },
  FlowByHYSJHForm: {
    mustLogin: true,
    Com: basic(FlowByHYSJHForm)
  },
  FlowByHYSNDJHForm: {
    mustLogin: true,
    Com: basic(FlowByHYSNDJHForm)
  },
  FlowByHYSPlanForm: {
    mustLogin: true,
    Com: basic(FlowByHYSPlanForm)
  },
  // FlowByJDJHForm: {
  //   mustLogin: true,
  //   Com: basic(FlowByJDJHForm)
  // },
  // FlowByZXQKForm: {
  //   mustLogin: true,
  //   Com: basic(FlowByZXQKForm)
  // },
  // FlowByXQQRForm: {
  //   mustLogin: true,
  //   Com: basic(FlowByXQQRForm)
  // },
  // FlowByLeaveForm: {
  //   mustLogin: true,
  //   Com: basic(FlowByLeaveForm)
  // },
  // FlowByOvertimeForm: {
  //   mustLogin: true,
  //   Com: basic(FlowByOvertimeForm)
  // },
  // FlowByTripForm: {
  //   mustLogin: true,
  //   Com: basic(FlowByTripForm)
  // },
  // FlowByJwSealForm: {
  //   mustLogin: true,
  //   Com: basic(FlowByJwSealForm)
  // },
  // FlowByAffairsForm: {
  //   mustLogin: true,
  //   Com: basic(FlowByAffairsForm)
  // },
  // FlowByGoAbroadForm: {
  //   mustLogin: true,
  //   Com: basic(FlowByGoAbroadForm)
  // },
  // FlowByGoAbroadJgForm: {
  //   mustLogin: true,
  //   Com: basic(FlowByGoAbroadJgForm)
  // },
  Principles: {
    mustLogin: false,
    Com: Principles
  },
  signInss: {
    mustLogin: true,
    Com: signInss
  },
  largeSignIns: {
    mustLogin: true,
    Com: largeSignIns
  },
  signInDetail: {
    mustLogin: true,
    Com: signInDetail
  },
  signInsDetail: {
    mustLogin: true,
    Com: signInsDetail
  },
  meetingRoom: {
    mustLogin: true,
    Com: meetingRoom
  },
  worldYouthMeeting: {
    mustLogin: true,
    Com: worldYouthMeeting
  },
  worldYouthMeetingDetail: {
    mustLogin: true,
    Com: worldYouthMeetingDetail
  }
};
const routerInfo = [
  // {
  //     label: "中交用印流程",
  //     children: [
  //         {
  //             label: "流程列表",
  //             defaultPath: "FlowByzjYongYin/0/0",
  //             pathName: "FlowByzjYongYin/:flowId/:status", //流程id 流程类状态（待办已办）
  //             comKey: "FlowByzjYongYinList",
  //         },
  //         {
  //             label: "流程处理",
  //             defaultPath: "FlowByzjYongYin/0/0",
  //             pathName: "FlowByzjYongYin/:flowId/:status/:workId/:title", //流程id 流程类状态（待办已办） workId（等于0是创建流程其他是处理流程）
  //             comKey: "FlowByzjYongYinForm",
  //         }
  //     ]
  // },
  // {
  //     label: "中交外协用印流程",
  //     children: [
  //         {
  //             label: "流程列表",
  //             defaultPath: "FlowByzjOutYongYin/0/0",
  //             pathName: "FlowByzjOutYongYin/:flowId/:status", //流程id 流程类状态（待办已办）
  //             comKey: "FlowByzjOutYongYinList",
  //         },
  //         {
  //             label: "流程处理",
  //             defaultPath: "FlowByzjOutYongYin/0/0/0",
  //             pathName: "FlowByzjOutYongYin/:flowId/:status/:workId/:title", //流程id 流程类状态（待办已办） workId（等于0是创建流程其他是处理流程）
  //             comKey: "FlowByzjOutYongYinForm",
  //         }
  //     ]
  // }
  {
    label: "参会列表",
    defaultPath: "Principles",
    pathName: "Principles",
    comKey: "Principles",
  },
  {
    label: "人员签到",
    defaultPath: "signInss/0/0/0",
    pathName: "signInss/:reservationsId/:loginMobile/:flag",
    comKey: "signInss",
  },
  {
    label: "人员报名",
    defaultPath: "largeSignIns/0/0/0",
    pathName: "largeSignIns/:reservationsId/:loginMobile/:flag",
    comKey: "largeSignIns",
  },
  {
    label: "会议详情",
    defaultPath: "signInDetail/0/0/0",
    pathName: "signInDetail/:reservationsId/:loginMobile/:flag",
    comKey: "signInDetail",
  },
  {
    label: "未参加",
    defaultPath: "signInsDetail/0/0/0",
    pathName: "signInsDetail/:reservationsId/:loginMobile/:flag",
    comKey: "signInsDetail",
  },
  {
    label: "会议室详情",
    defaultPath: "meetingRoom",
    pathName: "meetingRoom",
    comKey: "meetingRoom",
  },
  {
    label: "审批管理",
    defaultPath: "worldYouthMeeting",
    pathName: "worldYouthMeeting",
    comKey: "worldYouthMeeting",
  },
  {
    label: "审批管理详情",
    defaultPath: "worldYouthMeetingDetail/0/0/0",
    pathName: "worldYouthMeetingDetail/:fallInDeptId/:fallInYear/:approvalState",
    comKey: "worldYouthMeetingDetail",
  },
  {
    label: "流程",
    children: [
      {
        label: "流程待办列表会议室",
        defaultPath: "todoByZjMeetingRoom",
        pathName: "todoByZjMeetingRoom",
        comKey: "FlowByHYS"
      },
      {
        label: "流程已办列表会议室",
        defaultPath: "hasTodoByZjMeetingRoom",
        pathName: "hasTodoByZjMeetingRoom",
        comKey: "FlowByHYS"
      },
      {
        label: "会议室",
        defaultPath: "FlowByZjMeetingRoom/ZjMeetingRoom/0/0",
        pathName: "FlowByZjMeetingRoom/:flowId/:workId/:title",
        comKey: "FlowByHYSForm",
        hide: true
      },
      {
        label: "流程待办列表会议室计划",
        defaultPath: "todoByZjMeetingPlanSummary",
        pathName: "todoByZjMeetingPlanSummary",
        comKey: "FlowByHYSJH"
      },
      {
        label: "流程已办列表会议室计划",
        defaultPath: "hasTodoByZjMeetingPlanSummary",
        pathName: "hasTodoByZjMeetingPlanSummary",
        comKey: "FlowByHYSJH"
      },
      {
        label: "会议室计划",
        defaultPath: "FlowByZjMeetingPlanSummary/ZjMeetingPlanSummary/0/0",
        pathName: "FlowByZjMeetingPlanSummary/:flowId/:workId/:title",
        comKey: "FlowByHYSJHForm",
        hide: true
      },
      {
        label: "流程待办列表年度会议室计划",
        defaultPath: "todoByZjMeetingPlanFallInSummary",
        pathName: "todoByZjMeetingPlanFallInSummary",
        comKey: "FlowByHYSNDJH"
      },
      {
        label: "流程已办列表年度会议室计划",
        defaultPath: "hasTodoByZjMeetingPlanFallInSummary",
        pathName: "hasTodoByZjMeetingPlanFallInSummary",
        comKey: "FlowByHYSNDJH"
      },
      {
        label: "年度会议室计划",
        defaultPath: "FlowByZjMeetingPlanFallInSummary/ZjMeetingPlanFallInSummary/0/0",
        pathName: "FlowByZjMeetingPlanFallInSummary/:flowId/:workId/:title",
        comKey: "FlowByHYSNDJHForm",
        hide: true
      },
      {
        label: "流程待办列表计划会议",
        defaultPath: "todoByZjMeetingPlanRoom",
        pathName: "todoByZjMeetingPlanRoom",
        comKey: "FlowByHYSNDJH"
      },
      {
        label: "流程已办列表计划会议",
        defaultPath: "hasTodoByZjMeetingPlanRoom",
        pathName: "hasTodoByZjMeetingPlanRoom",
        comKey: "FlowByHYSPlan"
      },
      {
        label: "年度计划会议",
        defaultPath: "FlowByZjMeetingPlanRoom/ZjMeetingPlanRoom/0/0",
        pathName: "FlowByZjMeetingPlanRoom/:flowId/:workId/:title",
        comKey: "FlowByHYSPlanForm",
        hide: true
      }
    ]
  }
  // {
  //   label: "流程",
  //   children: [
  //     {
  //       label: "流程待办列表用印",
  //       defaultPath: "todoByzjYongYin",
  //       pathName: "todoByzjYongYin",
  //       comKey: "FlowByYY"
  //     },
  //     {
  //       label: "流程已办列表用印",
  //       defaultPath: "hasTodoByzjYongYin",
  //       pathName: "hasTodoByzjYongYin",
  //       comKey: "FlowByYY"
  //     },
  //     {
  //       label: "外协流程待办列表用印",
  //       defaultPath: "todoByzjYyOutSeal",
  //       pathName: "todoByzjYyOutSeal",
  //       comKey: "FlowByWXYY"
  //     },
  //     {
  //       label: "外协流程已办列表用印",
  //       defaultPath: "hasTodoByzjYyOutSeal",
  //       pathName: "hasTodoByzjYyOutSeal",
  //       comKey: "FlowByWXYY"
  //     },
  //     {
  //       label: "流程待办列表党费",
  //       defaultPath: "todoByzjPartyFeeUse",
  //       pathName: "todoByzjPartyFeeUse",
  //       comKey: "FlowByDF"
  //     },
  //     {
  //       label: "流程已办列表党费",
  //       defaultPath: "hasTodoByzjPartyFeeUse",
  //       pathName: "hasTodoByzjPartyFeeUse",
  //       comKey: "FlowByDF"
  //     },
  //     {
  //       label: "流程待办列表会议室",
  //       defaultPath: "todoByZjMeetingRoom",
  //       pathName: "todoByZjMeetingRoom",
  //       comKey: "FlowByHYS"
  //     },
  //     {
  //       label: "流程已办列表会议室",
  //       defaultPath: "hasTodoByZjMeetingRoom",
  //       pathName: "hasTodoByZjMeetingRoom",
  //       comKey: "FlowByHYS"
  //     },
  //     {
  //       label: "流程待办列表会议室计划",
  //       defaultPath: "todoByZjMeetingPlanSummary",
  //       pathName: "todoByZjMeetingPlanSummary",
  //       comKey: "FlowByHYSJH"
  //     },
  //     {
  //       label: "流程已办列表会议室计划",
  //       defaultPath: "hasTodoByZjMeetingPlanSummary",
  //       pathName: "hasTodoByZjMeetingPlanSummary",
  //       comKey: "FlowByHYSJH"
  //     },
  //     {
  //       label: "流程待办列表监督计划",
  //       defaultPath: "todoByyearSupPlan",
  //       pathName: "todoByyearSupPlan",
  //       comKey: "FlowByJDJH"
  //     },
  //     {
  //       label: "流程已办列表监督计划",
  //       defaultPath: "hasTodoByyearSupPlan",
  //       pathName: "hasTodoByyearSupPlan",
  //       comKey: "FlowByJDJH"
  //     },
  //     {
  //       label: "流程待办列表执行情况",
  //       defaultPath: "todoByyearSupPlanExecutive",
  //       pathName: "todoByyearSupPlanExecutive",
  //       comKey: "FlowByZXQK"
  //     },
  //     {
  //       label: "流程已办列表执行情况",
  //       defaultPath: "hasTodoByyearSupPlanExecutive",
  //       pathName: "hasTodoByyearSupPlanExecutive",
  //       comKey: "FlowByZXQK"
  //     },
  //     {
  //       label: "流程待办列表信息化需求确认",
  //       defaultPath: "todoByflowIdZj1",
  //       pathName: "todoByflowIdZj1",
  //       comKey: "FlowByXQQR"
  //     },
  //     {
  //       label: "流程已办列表信息化需求确认",
  //       defaultPath: "hasTodoflowIdZj1",
  //       pathName: "hasTodoByflowIdZj1",
  //       comKey: "FlowByXQQR"
  //     },
  //     {
  //       label: "流程待办列表请假申请",
  //       defaultPath: "todoByzjLeaveApply",
  //       pathName: "todoByzjLeaveApply",
  //       comKey: "FlowByLeave"
  //     },
  //     {
  //       label: "流程已办列表请假申请",
  //       defaultPath: "hasTodoByzjLeaveApply",
  //       pathName: "hasTodoByzjLeaveApply",
  //       comKey: "FlowByLeave"
  //     },
  //     {
  //       label: "流程待办列表加班申请",
  //       defaultPath: "todoByzjWorkApply",
  //       pathName: "todoByzjWorkApply",
  //       comKey: "FlowByOvertime"
  //     },
  //     {
  //       label: "流程已办列表加班申请",
  //       defaultPath: "hasTodoByzjWorkApply",
  //       pathName: "hasTodoByzjWorkApply",
  //       comKey: "FlowByOvertime"
  //     },
  //     {
  //       label: "流程待办列表出差申请",
  //       defaultPath: "todoByzjTripApply",
  //       pathName: "todoByzjTripApply",
  //       comKey: "FlowByTrip"
  //     },
  //     {
  //       label: "流程已办列表出差申请",
  //       defaultPath: "hasTodoByzjTripApply",
  //       pathName: "hasTodoByzjTripApply",
  //       comKey: "FlowByTrip"
  //     },
  //     {
  //       label: "流程待办列表纪委用印申请",
  //       defaultPath: "todoByzjjwYongYin",
  //       pathName: "todoByzjjwYongYin",
  //       comKey: "FlowByJwSeal"
  //     },
  //     {
  //       label: "流程已办列表纪委用印申请",
  //       defaultPath: "hasTodoByzjjwYongYin",
  //       pathName: "hasTodoByzjjwYongYin",
  //       comKey: "FlowByJwSeal"
  //     },
  //     {
  //       label: "流程待办列表局机关事务申请",
  //       defaultPath: "todoByzjAffairsApply",
  //       pathName: "todoByzjAffairsApply",
  //       comKey: "FlowByAffairs"
  //     },
  //     {
  //       label: "流程已办列表局机关事务申请",
  //       defaultPath: "hasTodoByzjAffairsApply",
  //       pathName: "hasTodoByzjAffairsApply",
  //       comKey: "FlowByAffairs"
  //     },
  //     {
  //       label: "流程待办列表出国申请",
  //       defaultPath: "todoByzjGoAbroad",
  //       pathName: "todoByzjGoAbroad",
  //       comKey: "FlowByGoAbroad"
  //     },
  //     {
  //       label: "流程已办列表出国申请",
  //       defaultPath: "hasTodoByzjGoAbroad",
  //       pathName: "hasTodoByzjGoAbroad",
  //       comKey: "FlowByGoAbroad"
  //     },
  //     {
  //       label: "流程待办列表出国（机关）申请",
  //       defaultPath: "todoByzjjgGoAbroad",
  //       pathName: "todoByzjjgGoAbroad",
  //       comKey: "FlowByGoAbroadJg"
  //     },
  //     {
  //       label: "流程已办列表出国（机关）申请",
  //       defaultPath: "hasTodoByzjjgGoAbroad",
  //       pathName: "hasTodoByzjjgGoAbroad",
  //       comKey: "FlowByGoAbroadJg"
  //     },
  //     {
  //       label: "党费申请",
  //       defaultPath: "FlowByzjPartyFeeUse/zjPartyFeeUse/add/0",
  //       pathName: "FlowByzjPartyFeeUse/:flowId/:workId/:title",
  //       comKey: "FlowByDFForm",
  //       hide: true
  //     },
  //     {
  //       label: "用印申请",
  //       defaultPath: "FlowByzjYongYin/zjYongYin/add/0",
  //       pathName: "FlowByzjYongYin/:flowId/:workId/:title",
  //       comKey: "FlowByYYForm",
  //       hide: true
  //     },
  //     {
  //       label: "外协用印申请",
  //       defaultPath: "FlowByzjYyOutSeal/zjYyOutSeal/add/0",
  //       pathName: "FlowByzjYyOutSeal/:flowId/:workId/:title",
  //       comKey: "FlowByWXYYForm",
  //       hide: true
  //     },
  //     {
  //       label: "会议室",
  //       defaultPath: "FlowByZjMeetingRoom/ZjMeetingRoom/0/0",
  //       pathName: "FlowByZjMeetingRoom/:flowId/:workId/:title",
  //       comKey: "FlowByHYSForm",
  //       hide: true
  //     },
  //     {
  //       label: "会议室计划",
  //       defaultPath: "FlowByZjMeetingPlanSummary/ZjMeetingPlanSummary/0/0",
  //       pathName: "FlowByZjMeetingPlanSummary/:flowId/:workId/:title",
  //       comKey: "FlowByHYSJHForm",
  //       hide: true
  //     },
  //     {
  //       label: "监督计划",
  //       defaultPath: "FlowByyearSupPlan/yearSupPlan/0/0",
  //       pathName: "FlowByyearSupPlan/:flowId/:workId/:title",
  //       comKey: "FlowByJDJHForm",
  //       hide: true
  //     },
  //     {
  //       label: "执行情况",
  //       defaultPath: "FlowByyearSupPlanExecutive/yearSupPlanExecutive/0/0",
  //       pathName: "FlowByyearSupPlanExecutive/:flowId/:workId/:title",
  //       comKey: "FlowByZXQKForm",
  //       hide: true
  //     },
  //     {
  //       label: "信息化需求确认",
  //       defaultPath: "FlowByflowIdZj1/flowIdZj1/0/0",
  //       pathName: "FlowByflowIdZj1/:flowId/:workId/:title",
  //       comKey: "FlowByXQQRForm",
  //       hide: true
  //     },
  //     {
  //       label: "请假申请",
  //       defaultPath: "FlowByzjLeaveApply/zjLeaveApply/0/0",
  //       pathName: "FlowByzjLeaveApply/:flowId/:workId/:title",
  //       comKey: "FlowByLeaveForm",
  //       hide: true
  //     },
  //     {
  //       label: "加班申请",
  //       defaultPath: "FlowByzjWorkApply/zjWorkApply/0/0",
  //       pathName: "FlowByzjWorkApply/:flowId/:workId/:title",
  //       comKey: "FlowByOvertimeForm",
  //       hide: true
  //     },
  //     {
  //       label: "出差申请",
  //       defaultPath: "FlowByzjTripApply/zjTripApply/0/0",
  //       pathName: "FlowByzjTripApply/:flowId/:workId/:title",
  //       comKey: "FlowByTripForm",
  //       hide: true
  //     },
  //     {
  //       label: "纪委用印申请",
  //       defaultPath: "FlowByzjjwYongYin/zjjwYongYin/0/0",
  //       pathName: "FlowByzjjwYongYin/:flowId/:workId/:title",
  //       comKey: "FlowByJwSealForm",
  //       hide: true
  //     },
  //     {
  //       label: "纪委用印申请",
  //       defaultPath: "FlowByzjAffairsApply/zjAffairsApply/0/0",
  //       pathName: "FlowByzjAffairsApply/:flowId/:workId/:title",
  //       comKey: "FlowByAffairsForm",
  //       hide: true
  //     },
  //     {
  //       label: "出国各单位申请",
  //       defaultPath: "FlowByzjGoAbroad/zjGoAbroad/0/0",
  //       pathName: "FlowByzjGoAbroad/:flowId/:workId/:title",
  //       comKey: "FlowByGoAbroadForm",
  //       hide: true
  //     },
  //     {
  //       label: "出国(机关)申请",
  //       defaultPath: "FlowByzjjgGoAbroad/zjjgGoAbroad/0/0",
  //       pathName: "FlowByzjjgGoAbroad/:flowId/:workId/:title",
  //       comKey: "FlowByGoAbroadJgForm",
  //       hide: true
  //     }
  // ]
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
