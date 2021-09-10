import React from 'react';
import { Route, Redirect } from 'react-router-dom';
import { connect } from 'react-redux'
import MyPublic from "../Public"
import MyViews from '../../views'
const { pageComs, routerInfo: viewRouterInfo, } = MyViews
const { URI, appInfo: { mainModule } } = MyPublic
const mainRouterInfo = [
    {
        name: "",
        moduleName: mainModule,
        pathName: "/",
        redirect: "home"
    },
    {
        name: "",
        moduleName: mainModule,
        pathName: "home",
        comKey: "Home"
    },
    {
        name: "",
        moduleName: mainModule,
        pathName: "login",
        comKey: "Login"
    },
    {
        name: "",
        moduleName: mainModule,
        pathName: "error/:code",
        comKey: "Error"
    },
];

const createRouteData = (array, pid = "") => {
    let routeData = {};
    for (let index = 0; index < array.length; index++) {
        let { name = "", pathName = "", roles = {}, iconType = "", moduleName = "", redirect = "", children = [], comKey = "" } = array[index];
        let id = (pid ? pid + "_" : "") + index;
        let menuLv = pid.split("_").filter(i => i !== "").length
        if (children.length > 0) {
            if (pathName) {
                routeData[id] = { id, pid, roles: { ...roles }, menuLv, name, pathName, moduleName, iconType, comKey, redirect: comKey ? "" : (redirect || children[0].pathName || "") }
            }
            let _routeData = createRouteData(children, id)
            routeData = {
                ...routeData,
                ..._routeData
            }
        } else {
            if (pathName) {
                routeData[id] = { id, pid, menuLv, roles: { ...roles }, name, pathName, iconType, moduleName, redirect, comKey }
            }
        }
    }
    return routeData
}
let NotFounndCom = (props) => {
    return (
        <div>
            {`找不到该页面${props.location.pathname}`}
        </div>
    )
}
const selectCom = (state) => {
    return {
        ...state
    }
}


const MyComponent = (props) => {
    const uri = new URI()
    const { dispatch, corpInfo, actions: { setRedirectInfo, login, logout }, loginAndLogoutInfo: { loginInfo, redirectInfo, logoutInfo }, routerInfo: { routeData: _routeData, curKey } } = props
    const { redirect: curRedirect, comKey: curComKey, moduleName: curModuleName, pathName: curPathName } = _routeData[curKey]
    if (curRedirect) {
        if (redirectInfo && redirectInfo.moduleName === curModuleName && redirectInfo.fragment === curPathName) {
            dispatch(setRedirectInfo({ ...redirectInfo, pathName: curRedirect, fragment: curRedirect }))
            return <div />
        } else {
            return <Redirect to={curModuleName + curRedirect} />
        }
    } else {
        const curPageCom = pageComs[curComKey] || {}
        const { Com = NotFounndCom, mustLogin = false } = curPageCom
        if (!mustLogin && curPathName !== "login") {//直接访问不需要授权的页面
            return <Com {...props} />
        } else if (loginInfo && !loginInfo.loading) {//有登录信息
            if (redirectInfo) {//登录后尚未跳转至指定页面的
                let { moduleName: _moduleName = curModuleName, pathName: _pathName = curPathName, fragment = uri.fragment().split(uri.path())[1] } = redirectInfo
                if (_moduleName === curModuleName) {//指定跳转页面的模块与当前模块匹配
                    if (_pathName === "login" && curPathName !== `login`) {
                        dispatch(setRedirectInfo())//清空指定页面路由数据
                        return <div />
                    } else if (_pathName === "login") {
                        return <Redirect to={_moduleName} />
                    } else if (_pathName === curPathName) {//指定跳转的页面就是当前页面
                        dispatch(setRedirectInfo())//清空指定页面路由数据
                        return <div />
                    } else {
                        return <Redirect to={_moduleName + fragment} />
                    }
                } else {//不匹配跳转至其他模块
                    window.location.href = new URI().host(uri.host()).path(_moduleName).hash(_moduleName + fragment).toString()
                }
            } else {//已经登录正常访问授权页面
                return <Com {...props} />
            }
        } else if (logoutInfo) {//是否有退出信息
            const { loginType } = logoutInfo
            const code = uri.query(true)["code"]
            if ((loginType === "3" || loginType === "4" || loginType === "5") && code) {//微信重定向地址
                dispatch(login({ code, loginType }))//使用code登录
                return <div />
            } if (loginType === "3" || loginType === "4") {//微信oauth2授权
                window.location.href = corpInfo.uri
            } else {//有登出信息非微信授权登录强制跳转至登录页面
                if (uri.path() === mainModule) {
                    if (curPathName === "login") {//打开登录页面
                        return <Com {...props} />
                    } else {
                        return <Redirect to={`${mainModule}login`} />
                    }
                } else {
                    window.location.href = new URI().host(uri.host()).path(mainModule).hash(`${mainModule}login`).toString()
                }
            }
        } else {
            dispatch(logout(curPathName))
            return <div />
        }
    }
}




const createRoutes = (routeData) => {
    let routes = []
    for (const key in routeData) {
        if (routeData.hasOwnProperty(key)) {
            const uri = new URI()
            const { pathName, moduleName } = routeData[key];
        
            if (moduleName === uri.path() || moduleName === mainModule) {
                routes.push(
                    <Route
                        exact
                        key={key}
                        path={moduleName + (pathName === "/" ? "" : pathName)}
                        component={connect(selectCom)(props => <MyComponent {...props} routerInfo={{ routeData, curKey: key }} />)} />
                )
            }
        }
    }
    routes.push(
        <Route key={"-2"} component={
            (props) => {
                const uri = new URI()
                if (props.location.pathname === "/") {
                    return <Redirect to={uri.path()} />
                } else {
                    return <NotFounndCom {...props} />
                }
            }
        } />
    )
    return routes
}

const _createRoutes = (dynamicRouterInfo) => {
    const uri = new URI()
    function addModuleName(arr = []) {
        return arr.map((item) => {
            return {
                ...item,
                children: item.children ? addModuleName(item.children) : [],
                moduleName: uri.path()
            }
        })
    }
    let _viewRouterInfo = addModuleName(viewRouterInfo)
    let routerInfo = [].concat(mainRouterInfo, _viewRouterInfo, dynamicRouterInfo)
    let _routeData = createRouteData(routerInfo)
    return createRoutes(_routeData)
}
const MyRouter = {
    createRoutes: _createRoutes
}
export default MyRouter 