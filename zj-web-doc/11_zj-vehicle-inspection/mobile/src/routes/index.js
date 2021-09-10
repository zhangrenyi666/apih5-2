import React from 'react';
import { User } from '../module';//导入User模块
import { blankLayout } from "../layouts"
import Login from './login';
import ErrPage from './errPage';
import Tab from './tab';
import Home from './home';
import Help from './help';
import ApplyAdd from './applyAdd'; //车辆年检新增
import InsuranceAdd from './insuranceAdd';//车辆保险新增
import ApplyList from './applyList';//申请列表
import CarList from './carList';//车辆列表
import CarDetail from './carDetail';//车辆详情
import ApplyDetail from './applyDetail';//申请详情
import ExamineList from './examineList';//审批列表
import ApprovalDetail from './examineDetail';//审批详情
import MessageWeb from './message';//消息通知页面

import Test from './test';

const routeDatas = [
    {
        name: "首页",
        path: "/home",
        component: User.withOAuth(blankLayout(Home)),
    },
    {
        children: [
            {
                path: "/applyList",//申请列表 
                component: User.withOAuth(blankLayout(ApplyList)),
            }, 
            {
                path: "/applyAdd/:id",//年检新增
                component: User.withOAuth(blankLayout(ApplyAdd)),
            },{
                path: "/insuranceAdd/:id",//保险新增
                component: User.withOAuth(blankLayout(InsuranceAdd)),
            },{
                path: "/carList",//车辆列表
                component: User.withOAuth(blankLayout(CarList)),
            },{
                path: "/carDetail/:id",//车辆列表
                component: User.withOAuth(blankLayout(CarDetail)),
            },{
                path: "/applyDetail/:approvalId",//申请列表
                component: User.withOAuth(blankLayout(ApplyDetail)),
            },{
                path: "/examineList",//审批列表  
                component: User.withOAuth(blankLayout(ExamineList)),
            },{
                path: "/approvalDetail/:approvalId",//申请列表
                component: User.withOAuth(blankLayout(ApprovalDetail)),
            },{
                path: "/message/:id/:approvalType",//申请列表
                component:  User.withOAuth(blankLayout(MessageWeb)),
            },{
                path: "/test",//申请列表
                component: Test,
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
 
