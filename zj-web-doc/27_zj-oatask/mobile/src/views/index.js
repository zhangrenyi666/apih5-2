import LoginPage from "./login";
import HomePage from "./home";
import ContactsPage from "./contacts";
import MenusPage from "./menus";
import RolesPage from "./roles";
import BaseCode from "./BaseCode";
import { basic, blank } from "./modules/layouts";

//任务清单
import ResponsibilityList from './ResponsibilityList';
import TodoMobileDetail from './ResponsibilityList/TodoMobileDetail';
import HasTodoMobileDetail from './ResponsibilityList/HasTodoMobileDetail';

//统计页面
import StatisticsList from './StatisticsList';
//任务分派
import StatisticsAdd from './StatisticsAdd';
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
    ResponsibilityList: {
        mustLogin: true,
        Com: ResponsibilityList
    },
    TodoMobileDetail: {
        mustLogin: true,
        Com: TodoMobileDetail
    },
    HasTodoMobileDetail: {
        mustLogin: true,
        Com: HasTodoMobileDetail
    },
    StatisticsList: {
        mustLogin: true,
        Com: StatisticsList
    },
    StatisticsAdd: {
        mustLogin: true,
        Com: StatisticsAdd
    },
};
const routerInfo = [
    {
        label: "任务清单",
        children: [
            {
                label: "任务清单列表",
                defaultPath: "ResponsibilityList/0",
                pathName: "ResponsibilityList/:page",
                comKey: "ResponsibilityList",
                children: [
                    {
                        label: "任务清单详情",
                        defaultPath: "TodoMobileDetail/0",
                        pathName: "TodoMobileDetail/:taskId",
                        comKey: "TodoMobileDetail",
                    },
                    {
                        label: "任务清单详情",
                        defaultPath: "HasTodoMobileDetail/0",
                        pathName: "HasTodoMobileDetail/:taskId",
                        comKey: "HasTodoMobileDetail",
                    }
                ]
            }
        ]
    },
    {
        label: "统计列表",
        children: [
            {
                label: "统计列表",
                defaultPath: "StatisticsList",
                pathName: "StatisticsList",
                comKey: "StatisticsList",
            }
        ]
    },
    {
        label: "任务分派",
        defaultPath: "StatisticsAdd",
        pathName: "StatisticsAdd",
        comKey: "StatisticsAdd",
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
