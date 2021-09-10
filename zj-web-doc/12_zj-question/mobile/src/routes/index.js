import React from 'react';
import { User } from '../module';//导入User模块
import { blankLayout } from "../layouts"
import Login from './login';
import ErrPage from './errPage';
import Tab from './tab';
import Home from './home';
import Help from './help';
import ProblemAdd from './ProblemAdd'; //问题新增 
import ProblemList from './ProblemList'; //问题列表 
import ProblemDetail from './ProblemDetail'; //问题详情 
import ProblemDetails from './ProblemDetails'; //问题详情 
import ProblemDetailt from './ProblemDetailt'; //问题详情
import ProblemAlist from './ProblemAlist'; //定制清单列表 
import ProblemBlist from './ProblemBlist'; //定制清单新增
import ProblemAdds from './ProblemAdds'; //问题新增 
import ProblemAddt from './ProblemAddt'; //问题改进
const routeDatas = [
    {
        name: "首页",
        path: "/home",
        component: User.withOAuth(blankLayout(Home)),
    },
    {
        children: [
            {
                path: "/problemAdd/:pullpersonRoute",//问题新增  
                component: User.withOAuth(blankLayout(ProblemAdd)),
            },{
                path: "/problemList",//问题列表
                component: User.withOAuth(blankLayout(ProblemList)),
            },{
                path: "/ProblemDetail/:recordid/:personnelFlag",//问题详情
                component: User.withOAuth(blankLayout(ProblemDetail)),
                // component: ProblemDetail,
            },{
                path: "/ProblemDetails/:recordid/:personnelFlag/:flag",//问题详情
                component: User.withOAuth(blankLayout(ProblemDetails)),
                // component: ProblemDetail,
            },{
                path: "/ProblemDetailt/:recordid/:personnelFlag",//问题详情
                component: User.withOAuth(blankLayout(ProblemDetailt)),
                // component: ProblemDetail,
            },{
                path: "/ProblemAlist",//定制清单列表
                component: User.withOAuth(blankLayout(ProblemAlist)),
                // component: ProblemDetail,
            },{
                path: "/ProblemBlist",//定制清单新增
                component: User.withOAuth(blankLayout(ProblemBlist)),
                // component: ProblemDetail,
            },{
                path: "/problemAdds/:screenId/:projectId/:projectName/:questionTitle/:projectOrgId",//问题新增  
                component: User.withOAuth(blankLayout(ProblemAdds)),
            },{
                path: "/problemAddt/:screenId/:projectId/:projectName/:questionTitle/:projectOrgId",//问题新增  
                component: User.withOAuth(blankLayout(ProblemAddt)),
            }
        ]
    },
    {
        children: [
            {
                path: "/error/:errCode",
                component: blankLayout(ErrPage),
            },
            {
                path: "/",
                redirect: "/home",
            },
            {
                path: "/login",
                component: blankLayout(Login),
            },
            {
                path: "/tab",
                component: User.withOAuth(blankLayout(Tab)),
            },
            {
                path: "/help",
                component: User.withOAuth(blankLayout(Help)),
            }
        ]
    }
]
export const getRouteData = () => {
    return new Promise((resolve, reject) => {
        let { routeData, routeIdByPath } = getRoute(routeDatas)
        // console.log("路由数据：", routeData)
        // console.log("路由path→id映射：", routeIdByPath)
        resolve({ routeData, routeIdByPath })
    })
}
const getRoute = (array, pid = "") => {
    let routeData = {};
    let routeIdByPath = {};
    for (let index = 0; index < array.length; index++) {
        let { name = "", path = "", iconType = "", redirect = "", children = [], component } = array[index];
        let id = (pid ? pid + "_" : "") + index;
        let menuLv = pid.split("_").filter(i => i !== "").length
        if (children.length > 0) {
            if (path) {
                routeData[id] = { id, pid, menuLv, name, path, redirect: component ? "" : (redirect || children[0].path || ""), iconType, component }
                routeIdByPath[path] = id
            }
            let { routeData: _routeData, routeIdByPath: _routeIdByPath } = getRoute(children, id)
            routeData = {
                ...routeData,
                ..._routeData
            }
            routeIdByPath = {
                ...routeIdByPath,
                ..._routeIdByPath
            }
        } else {
            if (path) {
                routeData[id] = { id, pid, menuLv, name, path, redirect, iconType, component: component || blankLayout(() => <div />) }
                routeIdByPath[path] = id
            }
        }
    }
    return {
        routeData,
        routeIdByPath
    }
}
 
