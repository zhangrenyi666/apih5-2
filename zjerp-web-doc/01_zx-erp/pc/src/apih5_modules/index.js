import App from '../App'
import React from 'react'
import { render } from 'react-dom'
import { Provider } from 'react-redux'
import { message as Msg, ConfigProvider } from 'antd';
import zh_CN from 'antd/lib/locale-provider/zh_CN';
import en_US from 'antd/lib/locale-provider/en_US';
import createSagaMiddleware from 'redux-saga'
import { createStore, combineReducers, applyMiddleware } from 'redux'
import { createHashHistory } from 'history'
import { connectRouter, routerMiddleware } from 'connected-react-router'
import 'moment/locale/zh-cn';
import MyPublic from './Public';//公共文件常量
import MyRedux from './Redux';//Redux管理中心
import MyUser from './User';//用户处理
import MyCorp from './Corp';//公司处理
// import MyPostMessage from './PostMessage';//iframe接收消息
import { createConsole } from "./Console"
import myFtch from './Fetch';
import MyStorage from "./Storage"
import Toast from "./Toast"

const { language = "zh_CN" } = window.configs;
const languages = {
    zh_CN: zh_CN,
    en_US: en_US,
}
const MyConsole = createConsole(`Lny`)
const { sagas, reducers, actions } = MyRedux
// const { Paragraph,Text } = Typography;
const history = createHashHistory()
const historyMiddleware = routerMiddleware(history);
const sagaMiddleware = createSagaMiddleware();
const middleware = [historyMiddleware, sagaMiddleware]
//合并reducer
const reducer = combineReducers({
    ...reducers,
    router: connectRouter(history), 
})


class Apih5 {
    //也就是直接使用地址栏token登录
    //非code方式登录
    _start() {
        const { appInfo: { title } } = MyPublic
        document.title = title
        MyConsole.log("启动")
        MyCorp.init().then((corpInfo) => {
            MyConsole.log("corpInfo数据", corpInfo)
            // MyPostMessage.listen((e) => { MyConsole.log("接收到的iframe消息：", e) })
            //创建store
            const loginInfo = MyUser.getLoginInfo()
            const logoutInfo = MyUser.getLogoutInfo()
            const redirectInfo = MyUser.getRedirectInfo();
            const store = createStore(
                connectRouter(history)(reducer),
                {
                    loginAndLogoutInfo: {
                        loginInfo: (loginInfo && loginInfo.loading) ? null : loginInfo,
                        redirectInfo,
                        logoutInfo,
                    },
                    actions,
                    corpInfo,
                },
                applyMiddleware(...middleware)
            )
            //运行saga的effect
            sagaMiddleware.run(sagas)
            const rootDom = document.getElementById('root')
            const RootReactRender = (<Provider store={store}>
                <ConfigProvider locale={languages[language]}>
                    <App history={history} />
                </ConfigProvider>
            </Provider>);

            render(RootReactRender, rootDom);
        })
    }

    //判断是否会走code登录
    //ture 走code登录
    //faslse 继续走正常登陆流程
    start() {
        // 先获取菜单数据
        this.getMenuData().then(() => {
            //是否是热更新渲染
            //只有开发环境下存在
            const hotRender = window.hotRender;

            //需要判断是否是热更新
            //热更新时候无需去请求：接口配置、webCode登录 (因为第一次已经请求过了)
            if (hotRender) {
                this._start();
            } else {
                //获取接口配置
                this.getApis().then(() => {
                    //判断是否需要走一步 webCode 登录
                    this.webCodeLogin().then(() => {
                        this._start();
                    })
                })
            }
        });
    }

    //是否需要走webCode登录
    webCodeLogin() {
        return new Promise((resolve) => {

            const { appInfo: { webCodeLoginType, id } } = MyPublic
            //继续正常流程
            const codeName = this.getUrlParam("codeName") || "webCode"; //用于自定义code名字 
            let webCode = null;
            if (codeName) {
                webCode = this.getUrlParam("webCode");
            }

            //配置了webCodeLoginType 浏览器地址未携带code出现提示
            if (webCodeLoginType && !webCode) {
                Toast.hide();
                render(
                    <div style={{ textAlign: "center" }}>
                        <div style={{ color: "red", marginTop: '24px' }}>
                            无权限登录！
                    </div>
                    </div>
                    , document.getElementById('root'));
                //不在继续执行
                return;
            }

            //需要使用webCode登录
            if (webCode) {
                myFtch("login", {
                    loginType: webCodeLoginType,
                    code: webCode,
                    accountId: id
                }, "1").then(({ data, success, message }) => {
                    if (success) {
                        MyStorage.removeItem("logoutInfo").then(() => {
                            MyStorage.setItem("loginInfo", { ...data }).then((loginInfo) => {
                                resolve();
                            })
                        })
                    } else {
                        Msg.error(message)
                    }
                })
            } else {
                resolve();
            }
        })
    }

    //获取config.apis  获取接口配置json
    getApis() {
        return new Promise((resolve) => {

            //请求接口文件的接口
            if (!window.configs.apis['getSysWebBg']) {
                window.configs.apis['getSysWebBg'] = "getSysWebBg";
            }

            //请求getCorpInfo接口的配置
            if (!window.configs.apis['getCorpInfo']) {
                window.configs.apis['getCorpInfo'] = "getCorpInfo";
            }

            //请求config文件 
            myFtch('getSysWebBg', {
                bgId: window.configs.appInfo.mainModule.replace(/(\/)/ig, '')
            }, "1").then(({ success, message, data }) => {
                if (success) {

                    //计算表达式的值
                    const evil = function (fn) {
                        var Fn = Function;  //一个变量指向Function，防止有些前端编译工具报错
                        return new Fn('return ' + fn)();
                    }

                    const decodedData = window.atob(data);
                    try {
                        const apis = JSON.parse(JSON.stringify({ ...window.configs.apis }));
                        evil(`window.configs.apis = ${decodedData}`);
                        window.configs.apis = {
                            ...window.configs.apis,
                            ...apis
                        }
                    } catch (err) {
                        console.warn("获取接口解析失败！")
                    }

                    //继续正常流程
                    resolve()

                } else {
                    Msg.error(message)
                }
            })
        })
    }

    //获取菜单数据
    getMenuData() {
        return new Promise((resolve, reject) => {

            const { appInfo: { grant_type, client_id, client_secret } } = window.configs;
            const isNewVer = grant_type && client_id && client_secret;

            const loginInfo = MyStorage.getItem("loginInfo")?.data;

            // 如果 loginInfo 存在时，需要去请求菜单数据，如果没有 loginInfo 信息时，需要去登陆，登陆成功后在获取菜单数据即可
            if (loginInfo && loginInfo.userInfo && isNewVer) { 
                //请求config文件 
                myFtch('getSysMenuTreeByTop', {}).then(async ({ success, message, data }) => {
                    if (success) {
                        // data.menuTree = {
                        //     label: "根菜单"
                        //     , title: ""
                        //     , type: "0"
                        //     , value: "9999999999"
                        //     , valuePid: "0"
                        // }
                        //更新菜单缓存 
                        await MyUser.updateUserInfo({ menuTree: data?.menuTree });
                        //继续正常流程
                        resolve(data?.menuTree)
                    } else {
                        Msg.error(message);
                        reject()
                    }
                })
            } else {
                resolve()
            }
        })
    }

    //获取地址栏参数，k为键名
    getUrlParam(k) {
        var m = new RegExp("(^|&)" + k + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(m);
        if (r != null) return decodeURI(r[2]); return null;
    }
}
export default new Apih5()


