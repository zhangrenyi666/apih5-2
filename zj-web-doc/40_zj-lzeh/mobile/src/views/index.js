import {
    lazy
} from "react";
import {
    basicHoc
} from "apih5/modules/layouts";

//引用basicHoc进行简单封装
const basic = (Component, props = {

    //切换项目或者获取项目列表的接口
    apiNames: {
        getCompanyList: "getZxQrcodePermissionObjectListByProject",
        changeCompany: "changeZxQrcodePermissionProject",
    },

    //项目数据的一些字段名字
    projectKeys: {
        list: "projectList",
        value: "levelId", //levelId  projectId
        label: "levelShortName", //levelShortName  projectShortName
    },

}) => {
    return basicHoc(props)(Component);
}

const pageComs = {
    /* 共通页面start */
    Login: {
        mustLogin: false,
        Com: lazy(() => import("./login"))
    },
    App: {
        mustLogin: true,
        Com: lazy(() => import("./App"))
    },
    Add: {
        //应用页面不是首页
        mustLogin: true,
        Com: lazy(() => import("./Add"))
    },
    Me: {
        mustLogin: true,
        Com: lazy(() => import("./Me"))
    },
    Home: {
        mustLogin: true,
        Com: lazy(() => import("./home"))
    },
    // 计划进度管理
    AnnualSchedule: {
        // 年度计划管理
        mustLogin: true,
        Com: lazy(() => import("./ZjLzehScheduleManagement/AnnualSchedule"))
    },
    MonthlySchedule: {
        // 月度计划管理
        mustLogin: true,
        Com: lazy(() => import("./ZjLzehScheduleManagement/MonthlySchedule"))
    },
    AnnualScheduleDetail: {
        // 年度计划管理详情
        mustLogin: true,
        Com: lazy(() => import("./ZjLzehScheduleManagement/AnnualSchedule/AnnualScheduleDetail"))
    },
    MonthlyScheduleDetail: {
        // 月度计划管理详情
        mustLogin: true,
        Com: lazy(() => import("./ZjLzehScheduleManagement/MonthlySchedule/MonthlyScheduleDetail"))
    },
    Teamcore: {
        // 班组评分
        mustLogin: true,
        Com: lazy(() => import("./ZjLzehTeammanagement/Teamcore"))
    },
    PeopleCore: {
        // 任务人员统计表
        mustLogin: true,
        Com: lazy(() => import("./ZjLzehTeammanagement/PeopleCore"))
    },
    BusinessPlan: {
        // 经营目标任务计划
        mustLogin: true,
        Com: lazy(() => import("./BusinessPlan"))
    },
    BusinessPlanDetail: {
        // 经营目标任务计划详情
        mustLogin: true,
        Com: lazy(() => import("./BusinessPlan/detail"))
    },
    ProductionPlan: {
        // 生产目标任务计划
        mustLogin: true,
        Com: lazy(() => import("./ProductionPlan"))
    },
    ProductionPlanDetail: {
        // 生产目标任务详情
        mustLogin: true,
        Com: lazy(() => import("./ProductionPlan/detail"))
    },
    TaskCreate: {
        // 任务创建
        mustLogin: true,
        Com: lazy(() => import("./TaskManagement/TaskCreate"))
    },
    TaskReceive: {
        // 任务接收
        mustLogin: true,
        Com: lazy(() => import("./TaskManagement/TaskReceive"))
    },
    SafetyAndQuality: {
        // 质量安全表格
        mustLogin: true,
        Com: lazy(() => import("./SafetyAndQuality"))
    },
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

const routerInfo = [{
    label: "首页",
    children: [{
        label: "首页",
        defaultPath: "App",
        pathName: "App",
        comKey: "App"
    },]
},
{
    label: "更多",
    children: [{
        label: "更多",
        defaultPath: "Add",
        pathName: "Add",
        comKey: "Add"
    },


    ]
},
{
    label: "我的",
    children: [{
        label: "Me",
        defaultPath: "Me",
        pathName: "Me",
        comKey: "Me"
    }]
},
{
    label: "计划进度管理",
    children: [{
        label: "年度计划进度",
        defaultPath: "AnnualSchedule/0",
        pathName: "AnnualSchedule/:meta",
        comKey: "AnnualSchedule",
        children: [{
            label: "年度计划进度详情",
            defaultPath: "AnnualScheduleDetail/0",
            pathName: "AnnualScheduleDetail/:zjLzehYearPlanProgressId",
            comKey: "AnnualScheduleDetail"
        },]
    },
    {
        label: "月度计划进度",
        defaultPath: "MonthlySchedule/0",
        pathName: "MonthlySchedule/:meta",
        comKey: "MonthlySchedule",
        children: [{
            label: "月度计划进度详情",
            defaultPath: "MonthlyScheduleDetail/0",
            pathName: "MonthlyScheduleDetail/:zjLzehMonthPlanProgressId",
            comKey: "MonthlyScheduleDetail"
        },]
    }
    ]
},
{
    label: "图表统计",
    children: [
        {
            label: "班组统计表",
            defaultPath: "Teamcore/0",
            pathName: "Teamcore/:meta",
            comKey: "Teamcore",
        },
        {
            label: "任务人员统计表",
            defaultPath: "PeopleCore/0",
            pathName: "PeopleCore/:meta",
            comKey: "PeopleCore",
        },
    ]
},
{
    label: "安全质量表格",
    defaultPath: "SafetyAndQuality/0",
    pathName: "SafetyAndQuality/:meta",
    comKey: "SafetyAndQuality",
},
{
    label: "目标任务计划",
    children: [
        {
            label: "经营目标任务计划",
            defaultPath: "BusinessPlan/0",
            pathName: "BusinessPlan/:meta",
            comKey: "BusinessPlan",
            children: [
                {
                    label: "经营目标任务计划-详情",
                    defaultPath: "BusinessPlanDetail/0",
                    pathName: "BusinessPlanDetail/:zjLzehManageTaskPlanId",
                    comKey: "BusinessPlanDetail"
                },
            ]
        },
        {
            label: "生产目标任务计划",
            defaultPath: "ProductionPlan/0",
            pathName: "ProductionPlan/:meta",
            comKey: "ProductionPlan",
            children: [
                {
                    label: "经营目标任务计划-详情",
                    defaultPath: "ProductionPlanDetail/0",
                    pathName: "ProductionPlanDetail/:zjLzehProduceTaskPlanId",
                    comKey: "ProductionPlanDetail"
                },
            ]
        },
        {
            label: "任务创建",
            defaultPath: "TaskCreate/0",
            pathName: "TaskCreate/:meta",
            comKey: "TaskCreate",
        },
        {
            label: "任务接收",
            defaultPath: "TaskReceive/0",
            pathName: "TaskReceive/:meta",
            comKey: "TaskReceive",
        },
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