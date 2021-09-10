import App from '../App'
import React from 'react'
import { render } from 'react-dom'
import { Provider } from 'react-redux'
import { message as Msg,ConfigProvider } from 'antd';//LocaleProvider,
// import { LocaleProvider } from "antd-mobile"
import zh_CN from 'antd/lib/locale-provider/zh_CN';
import createSagaMiddleware from 'redux-saga'
import { createStore,combineReducers,applyMiddleware } from 'redux'
import { createHashHistory } from 'history'
import { connectRouter,routerMiddleware } from 'connected-react-router'
import 'moment/locale/zh-cn';
import MyPublic from './Public';//公共文件常量
import MyRedux from './Redux';//Redux管理中心
import MyUser from './User';//用户处理
import MyCorp from './Corp';//公司处理
import MyPostMessage from './PostMessage';//iframe接收消息
import { createConsole } from "./Console"
import myFtch from './Fetch';
import MyStorage from "./Storage"
import Toast from "./Toast"

const MyConsole = createConsole(`Lny`)
const { sagas,reducers,actions } = MyRedux
// const { Paragraph,Text } = Typography;
const history = createHashHistory()
const historyMiddleware = routerMiddleware(history);
const sagaMiddleware = createSagaMiddleware();
const middleware = [historyMiddleware,sagaMiddleware]
//合并reducer
const reducer = combineReducers({
    ...reducers,
    router:connectRouter(history)
})
class Apih5 {
    //也就是直接使用地址栏token登录
    //非code方式登录
    _start() {

        const { appInfo: { title } } = MyPublic
        document.title = title
        MyConsole.log("启动")
        MyCorp.init().then((corpInfo) => {
            MyConsole.log("corpInfo数据",corpInfo)
            MyPostMessage.listen((e) => { MyConsole.log("接收到的iframe消息：",e) })
            //创建store
            const loginInfo = MyUser.getLoginInfo()
            const logoutInfo = MyUser.getLogoutInfo()
            const redirectInfo = MyUser.getRedirectInfo()
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
            render(
                <Provider store={store}>
                    <ConfigProvider locale={zh_CN}>
                        <App history={history} />
                    </ConfigProvider>
                </Provider>
                ,rootDom);
        })
    }

    //code方式登录
    start() {
        const { appInfo: { webCodeLoginType,id } } = MyPublic

        //请求接口文件的接口
        if (!window.configs.apis['getSysWebBg']) {
            window.configs.apis['getSysWebBg'] = "getSysWebBg";
        }

        //请求getCorpInfo接口的配置
        if (!window.configs.apis['getCorpInfo']) {
            window.configs.apis['getCorpInfo'] = "getCorpInfo";
        }

        //请求config文件 
        myFtch('getSysWebBg',{
            bgId: window.configs.appInfo.mainModule.replace(/(\/)/ig,'')
        },"1").then(({ success,message,data }) => {
            if (success) {

                //计算表达式的值
                const evil = function (fn) {
                    var Fn = Function;  //一个变量指向Function，防止有些前端编译工具报错
                    return new Fn('return ' + fn)();
                }


                let decodedData = window.atob(data);
                // eval(`window.configs.apis = ${decodedData}`);
                let apis = JSON.parse(JSON.stringify({ ...window.configs.apis }));
                evil(`window.configs.apis = ${decodedData}`);
                window.configs.apis = {
                    ...window.configs.apis,
                    ...apis
                }
                //继续正常流程
                let codeName = this.getUrlParam("codeName") || "webCode"; //用于自定义code名字 
                let webCode = null;
                if (codeName) {
                    webCode = this.getUrlParam("webCode");
                }

                //配置了webCodeLoginType 浏览器地址未携带code出现提示
                if (webCodeLoginType && !webCode) {
                    Toast.hide();
                    render(
                        <div style={{ textAlign: "center" }}>
                            <div style={{ color: "red",marginTop: '24px' }}>
                                无权限登录！
                            </div>
                        </div>
                        ,document.getElementById('root'));
                    //不在继续执行
                    return;
                }

                if (webCode) {
                    myFtch("login",{
                        loginType: webCodeLoginType,
                        code: webCode,
                        accountId: id
                    },"1").then(({ data,success,message }) => {
                        if (success) {
                            MyStorage.removeItem("logoutInfo").then(() => {
                                MyStorage.setItem("loginInfo",{ ...data }).then((loginInfo) => {
                                    this._start();
                                })
                            })
                        } else {
                            Msg.error(message)
                        }
                    })
                } else {
                    this._start();
                }

            } else {
                Msg.error(message)
            }
        })
    }

    getUrlParam(k) {//获取地址栏参数，k为键名
        var m = new RegExp("(^|&)" + k + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(m);
        if (r != null) return decodeURI(r[2]); return null;
    }
}
export default new Apih5()


