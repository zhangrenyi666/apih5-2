import React from "react";
import { Route,Redirect } from "react-router-dom";
import { Modal } from "antd-mobile";
import { connect } from "react-redux";
import MyPublic from "../Public";
import MyFetch,{ upload } from "../Fetch"; //数据请求
import MyViews from "../../views";
import { createConsole } from "../Console";
const MyConsole = createConsole(`Router`);
const { pageComs,routerInfo: viewRouterInfo } = MyViews;
const {
    URI,
    appInfo: { mainModule },
    androidApi
} = MyPublic; //

const mainRouterInfo = [
    {
        label: "首页",
        moduleName: mainModule,
        pathName: "/",
        comKey: "Home"
    },
    {
        label: "",
        moduleName: mainModule,
        pathName: "setting",
        comKey: "Setting"
    },
    {
        label: "",
        moduleName: mainModule,
        pathName: "login",
        comKey: "Login"
    },
    {
        label: "",
        moduleName: mainModule,
        pathName: "error/:code",
        comKey: "Error"
    }
];

const createRouteData = (array,pid = "") => {
    let routeData = {};
    let routeTree = [];
    for (let index = 0; index < array.length; index++) {
        let {
            hide = false,
            label = "",
            defaultPath = "",
            pathName = "",
            iconType = "",
            moduleName = "",
            redirect = "",
            children = [],
            comKey = "",
            value,
            valuePid,
            showType,
            ...other
        } = array[index];
        let id = (pid ? pid + "_" : "") + index;
        let menuLv = pid.split("_").filter(i => i !== "").length;
        if (children.length > 0) {
            let {
                routeData: _routeData,
                routeTree: _routeTree
            } = createRouteData(children,id);
            routeData[id] = {
                ...other,
                _value: value,
                _valuePid: valuePid,
                _showType: showType,
                hide,
                id,
                pid,
                menuLv,
                label,
                defaultPath:
                    defaultPath ||
                    pathName ||
                    _routeTree[0].defaultPath ||
                    _routeTree[0].pathName ||
                    "",
                pathName,
                moduleName: moduleName || _routeTree[0].moduleName || "",
                iconType,
                comKey,
                redirect: comKey
                    ? ""
                    : redirect ||
                    children[0].pathName ||
                    children[0].redirect ||
                    "",
                children,
                routeTree: _routeTree
            };
            routeData = {
                ...other,
                ...routeData,
                ..._routeData
            };
        } else {
            routeData[id] = {
                ...other,
                _value: value,
                _valuePid: valuePid,
                _showType: showType,
                hide,
                id,
                pid,
                menuLv,
                label,
                defaultPath: defaultPath || pathName,
                pathName,
                moduleName,
                iconType,
                comKey,
                redirect,
                children: [],
                routeTree: []
            };
        }
        routeTree.push(routeData[id]);
    }
    return {
        routeData,
        routeTree
    };
};


let NotFounndCom = props => {
    const {
        dispatch,
        actions: { setRedirectInfo }
    } = props;

    const tzFn = (tzTimer) => {
        if (tzTimer) {
            clearTimeout(tzTimer);
        }

        dispatch(
            setRedirectInfo({
                moduleName: mainModule,
                pathName: "/",
                fragment: ""
            })
        );
        if (androidApi) {
            androidApi.closeActivity();
        } else {
            window.location.href = new URI()
                .host(new URI().host())
                .path(mainModule)
                .hash(`${mainModule}`)
                .toString();
        }
    }

    const tzTimer = setTimeout(() => {
        tzFn();
    },1000);

    return (
        <div style={{
            textAlign: "center",
            fontSize: "22px",
            color: '#777',
            padding: '24px 0px'
        }}>
            <p>找不到该页面！</p>
            <p>路由地址：{props.location.pathname}</p>
            <p>{1}秒后将跳转到首页（<a
                onClick={() => {
                    tzFn(tzTimer);
                }}
            >手动跳转到</a>）</p>
        </div>
    );
};

const MyComponent = props => {
    const uri = new URI();
    let path = uri.path();
    let pathArr = path.split("/");
    let realModuleName = path;
    if (pathArr[pathArr.length - 1] !== "") {
        realModuleName = path + "/";
    }
    const {
        dispatch,
        corpInfo,
        actions: { setRedirectInfo,login,logout },
        loginAndLogoutInfo,
        routerInfo: { routeData: _routeData,curKey },
        match = {}
    } = props;
    let { loginInfo,redirectInfo,logoutInfo } = loginAndLogoutInfo;
    const {
        redirect: curRedirect,
        comKey: curComKey,
        moduleName: curModuleName,
        pathName: curPathName
    } = _routeData[curKey];

    if (loginInfo && androidApi) {
        loginInfo.loginType = "6";
    }
    if (logoutInfo && androidApi) {
        logoutInfo.loginType = "6";
    }

    const myLogout = () => {
        dispatch(logout(curPathName));
    };

    const myFetch = (...arg) => {
        return MyFetch(...arg).then(result => {
            if (result.code === "3003" || result.code === "3004") {
                //token过期
                if (androidApi) {
                    androidApi.tokenBeOverdue(result.message);
                } else {
                    myLogout();
                }
            } else if (result.code === "901") {
                //登录信息过期
                if (androidApi) {
                    androidApi.tokenBeOverdue(result.message);
                } else {
                    Modal.alert(
                        "身份信息过期提示",
                        "登录信息过期是否重新登录？",
                        [
                            { text: "取消",onPress: () => { } },
                            {
                                text: "确定",
                                onPress: () => {
                                    myLogout();
                                }
                            }
                        ]
                    );
                }
                return { ...result };
            }
            return result;
        });
    };

    const myUpload = (uploadUrl) => {
        return (e) => {
            return upload(uploadUrl)(e).then((result) => {
                if (result.code === "3003" || result.code === "3004") {
                    //token过期
                    if (androidApi) {
                        androidApi.tokenBeOverdue(result.message);
                    } else {
                        myLogout();
                    }
                } else if (result.code === "901") {
                    //登录信息过期
                    if (androidApi) {
                        androidApi.tokenBeOverdue(result.message);
                    } else {
                        Modal.alert(
                            "身份信息过期提示",
                            "登录信息过期是否重新登录？",
                            [
                                { text: "取消",onPress: () => { } },
                                {
                                    text: "确定",
                                    onPress: () => {
                                        myLogout();
                                    }
                                }
                            ]
                        );
                    }
                    return { ...result };
                }
                return result;
            })
        }
    }

    if (curRedirect) {
        MyConsole.log("有重定向地址",curRedirect);
        if (
            redirectInfo &&
            redirectInfo.moduleName === curModuleName &&
            redirectInfo.fragment === curPathName
        ) {
            dispatch(
                setRedirectInfo({
                    ...redirectInfo,
                    pathName: curRedirect,
                    fragment: curRedirect
                })
            );
            return <div />;
        } else {
            return <Redirect to={curModuleName + curRedirect} />;
        }
    } else {
        MyConsole.log("无重定向地址");
        const curPageCom = pageComs[curComKey] || {};
        const { Com = NotFounndCom,mustLogin = false } = curPageCom;
        if (!mustLogin && curPathName !== "login") {
            //直接访问不需要授权的页面
            MyConsole.log("直接访问不需要授权的页面");
            return (
                <Com
                    {...props}
                    myUpload={myUpload}
                    myFetch={myFetch}
                    myPublic={MyPublic}
                    myLogout={myLogout}
                />
            );
        } else if (loginInfo && !loginInfo.error && !loginInfo.loading) {
            //有登录信息
            MyConsole.log("有登录信息,token:",loginInfo.token);
            if (redirectInfo) {
                //登录后尚未跳转至指定页面的
                MyConsole.log("重定向信息尚未清除");
                let {
                    moduleName: _moduleName = curModuleName,
                    pathName: _pathName = curPathName,
                    fragment = ""
                } = redirectInfo;
                fragment = fragment === "/" ? "" : fragment;

                if (_moduleName === curModuleName) {
                    //指定跳转页面的模块与当前模块匹配
                    MyConsole.log("模块匹配");
                    if (_pathName === "login" && curPathName !== `login`) {
                        MyConsole.log("匹配到登录页面地址，清空重定向信息");
                        dispatch(setRedirectInfo()); //清空指定页面路由数据
                        return <div />;
                    } else if (_pathName === "login") {
                        MyConsole.log("重定向信息已清空，向登录页面跳转");
                        return <Redirect to={_moduleName} />;
                    } else if (_pathName === curPathName) {
                        //指定跳转的页面就是当前页面
                        MyConsole.log("匹配到正常页面地址，清空重定向信息");
                        dispatch(setRedirectInfo()); //清空指定页面路由数据
                        return <div />;
                    } else {
                        MyConsole.log("重定向信息已清空，向正常页面跳转");
                        //当前地址
                        let curUrl = match.url;
                        let targetUrl = _moduleName + fragment;
                        //该情况跳转
                        if (curUrl !== targetUrl) {
                            return <Redirect to={_moduleName + fragment} />;
                        }

                        dispatch(setRedirectInfo()); //清空指定页面路由数据
                        return <div />;

                        // return <Com {...props} />; 
                    }
                } else {
                    //不匹配跳转至其他模块
                    MyConsole.log("模块不匹配");
                    window.location.href = new URI()
                        .host(uri.host())
                        .path(_moduleName)
                        .hash(_moduleName + fragment)
                        .toString();
                    return <NotFounndCom {...props} />;
                }
            } else {
                //已经登录正常访问授权页面
                MyConsole.log("正常实例化授权页面");
                return (
                    <Com
                        {...props}
                        myFetch={myFetch}
                        myUpload={myUpload}
                        myPublic={MyPublic}
                        myLogout={myLogout}
                    />
                );
            }
        } else if (logoutInfo) {
            //是否有退出信息
            MyConsole.log("有登出信息",logoutInfo);
            const { loginType } = logoutInfo;
            const code = uri.query(true)["code"];
            if (loginType === "6") {
                MyConsole.log("登录类型为[6]-App内嵌页面登录");
                if (androidApi) {
                    const token = androidApi.getLoginToken();
                    dispatch(login({ code: token,loginType })); //使用token登录
                    return <div />;
                } else {
                    return (
                        <div style={{ textAlign: "center" }}>
                            {"请在App内打开."}
                        </div>
                    );
                }
            } else if (
                (loginType === "3" || loginType === "32" || loginType === "4" || loginType === "5" || loginType === "31") &&
                code
            ) {

                //二维码登录模式直接取出code参数后重新打开页面
                if (loginType === "5" && loginInfo && loginInfo.error) {
                    myLogout();
                    return;
                }


                //微信重定向地址
                MyConsole.log("登录类型为[3,4,5]-微信重定携带code");
                dispatch(login({ code,loginType })); //使用code登录
                if (loginInfo && loginInfo.error) {
                    return (
                        <div style={{ textAlign: "center" }}>
                            {"微信授权失败，尝试点击"}
                            <a
                                onClick={() => {
                                    myLogout();
                                }}
                            >
                                {"重新登录"}
                            </a>
                            {"，或请联系管理员。"}
                        </div>
                    );
                } else if (loginInfo && loginInfo.loading) {
                    return (
                        <div style={{ textAlign: "center" }}>
                            {"微信授权登录中..."}
                        </div>
                    );
                } else {
                    return (
                        <div style={{ textAlign: "center" }}>
                            {"马上进行微信授权登录。"}
                        </div>
                    );
                }
            }
            if (loginType === "3" || loginType === "32" || loginType === "4" || loginType === "31") {
                //微信oauth2授权
                MyConsole.log("登录类型为[3,4]-微信重定未携带code");
                window.location.href = corpInfo.uri;
                return (
                    <div style={{ textAlign: "center" }}>
                        {"微信授权请求中..."}
                    </div>
                );
            } else {
                //有登出信息非微信授权登录强制跳转至登录页面
                MyConsole.log("登录类型为[1,5]-进入登录页面");
                if (realModuleName === mainModule && curPathName === "login") {
                    return (
                        <Com
                            {...props}
                            myFetch={myFetch}
                            myUpload={myUpload}
                            myPublic={MyPublic}
                            myLogout={myLogout}
                        />
                    );
                } else {
                    window.location.href = new URI()
                        .host(uri.host())
                        .path(mainModule)
                        .hash(`${mainModule}login`)
                        .toString();
                    return <div />;
                }
            }
        } else {
            MyConsole.log(
                "访问授权页或登录页，但登录登出信息均不存在，设置登出信息"
            );
            myLogout();
            return <div />;
        }
    }
};

const createRoutes = (routeData,routeTree) => {
    let routes = [];
    let selectCom = state => {
        return {
            ...state
        };
    };
    for (const key in routeData) {
        const { pathName,moduleName } = routeData[key];
        if (pathName) {
            const uri = new URI();
            let path = uri.path();
            let pathArr = path.split("/");
            let realModuleName = path;
            if (pathArr[pathArr.length - 1] !== "") {
                realModuleName = path + "/";
            }
            if (moduleName === realModuleName || moduleName === mainModule) {
                routes.push(
                    <Route
                        exact
                        key={key}
                        path={moduleName + (pathName === "/" ? "" : pathName)}
                        component={connect(selectCom)(props => {
                            MyConsole.log("已找到匹配的路由，id为：",key);
                            return (
                                <MyComponent
                                    {...props}
                                    routerInfo={{
                                        routeData,
                                        routeTree,
                                        curKey: key
                                    }}
                                />
                            );
                        })}
                    />
                );
            }
        }
    }
    routes.push(
        <Route
            key={"-2"}
            component={connect(selectCom)(props => {
                const uri = new URI();
                MyConsole.log("未找到匹配的路由，uri为",uri);
                let path = uri.path();
                let pathArr = path.split("/");
                let realModuleName = path;
                if (pathArr[pathArr.length - 1] !== "") {
                    realModuleName = path + "/";
                }
                const {
                    dispatch,
                    corpInfo,
                    actions: { setRedirectInfo,login },
                    loginAndLogoutInfo: { loginInfo,redirectInfo,logoutInfo }
                } = props;
                if (props.location.pathname === "/") {
                    return <Redirect to={realModuleName} />;
                } else {
                    if (logoutInfo) {
                        //是否有退出信息
                        const { loginType } = logoutInfo;
                        const code = uri.query(true)["code"];
                        if (loginType === "6") {
                            const token = androidApi.getLoginToken();
                            dispatch(login({ code: token,loginType })); //使用token登录
                            return <div />;
                        } else if (
                            (loginType === "3" || loginType === "32" ||
                                loginType === "4" ||
                                loginType === "5" || loginType === "31") &&
                            code
                        ) {
                            //微信重定向地址
                            dispatch(login({ code,loginType })); //使用code登录
                            return <div />;
                        }
                        if (loginType === "3" || loginType === "32" || loginType === "4" || loginType === "31") {
                            //微信oauth2授权
                            window.location.href = corpInfo.uri;
                        } else {
                            //有登出信息非微信授权登录强制跳转至登录页面
                            if (realModuleName === mainModule) {
                                return <Redirect to={`${mainModule}login`} />;
                            } else {
                                window.location.href = new URI()
                                    .host(uri.host())
                                    .path(mainModule)
                                    .hash(`${mainModule}login`)
                                    .toString();
                            }
                        }
                    }
                    if (loginInfo && !loginInfo.loading) {
                        if (redirectInfo) {
                            dispatch(setRedirectInfo());
                            return <div />;
                        } else {
                            return <NotFounndCom {...props} />;
                        }
                    } else {
                        return <Redirect to={realModuleName} />;
                    }
                }
            })}
        />
    );
    return routes;
};
const isJSON = str => {
    if (typeof str === "string") {
        try {
            JSON.parse(str);
            if (str.indexOf("{") > -1) {
                return true;
            } else {
                return false;
            }
        } catch (e) {
            return false;
        }
    }
    return false;
};

const _createRoutes = dynamicRouterInfo => {
    function loop(infoTree) {
        if (infoTree && infoTree.length) {
            return infoTree.map(item => {
                let title = isJSON(item.title) ? JSON.parse(item.title) : {};
                return {
                    ...item,
                    ...title,
                    children: loop(item.children)
                };
            });
        } else {
            return [];
        }
    }
    dynamicRouterInfo = loop(dynamicRouterInfo);
    const uri = new URI();
    let path = uri.path();
    let pathArr = path.split("/");
    let realModuleName = path;
    if (pathArr[pathArr.length - 1] !== "") {
        realModuleName = path + "/";
    }

    function addModuleName(arr = []) {
        return arr.map(item => {
            return {
                ...item,
                children: item.children ? addModuleName(item.children) : [],
                moduleName: realModuleName
            };
        });
    }
    let _viewRouterInfo = addModuleName(viewRouterInfo);
    MyConsole.log("主要路由数据",mainRouterInfo);
    MyConsole.log("本地路由数据",_viewRouterInfo);
    MyConsole.log("异步路由数据",dynamicRouterInfo);
    const routerInfo = [].concat(
        mainRouterInfo,
        _viewRouterInfo,
        dynamicRouterInfo
    );
    const _routeData = createRouteData(routerInfo);
    MyConsole.log("结构化路由数据",_routeData);
    const { routeData,routeTree } = _routeData;
    const _routes = createRoutes(routeData,routeTree);
    return _routes;
};
const MyRouter = {
    createRoutes: _createRoutes
};
export default MyRouter;
