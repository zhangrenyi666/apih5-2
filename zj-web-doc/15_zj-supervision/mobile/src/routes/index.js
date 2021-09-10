import React from 'react';
import { User } from '../module';//导入User模块
import { blankLayout } from "../layouts"
import Login from './login';
import ErrPage from './errPage';
import Tab from './tab';
import Home from './home';
import Help from './help'; 
import MList from './MList'; // 列表 
import Preview from './Preview'; //预览
import Sign from './Sign';//签名页面
import Detail from './Detail';//详情页面

import Test from './test';

const routeDatas = [
    {
        name: "首页",
        path: "/home",
        component: User.withOAuth(blankLayout(MList)),
    },
    {
        children: [
            {
                path: "/list",//问题列表
                component: User.withOAuth(blankLayout(MList)),
            }, {
                path: "/test",// 
                component: Test,
            }, 
            {
                path:'/Detail/:fileId',
                component: User.withOAuth(blankLayout(Detail)), 
            }, 
            {
                path:'/Sign/:fileId',
                component: User.withOAuth(blankLayout(Sign)), 
            }, 
            {
                path:'/Preview',
                component: User.withOAuth(blankLayout(Preview)), 
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
 
