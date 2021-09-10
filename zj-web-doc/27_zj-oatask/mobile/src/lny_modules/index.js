import App from '../App'
import React from 'react'
import { render } from 'react-dom'
import { Provider } from 'react-redux'
import { LocaleProvider } from 'antd';
import zh_CN from 'antd/lib/locale-provider/zh_CN';
import createSagaMiddleware from 'redux-saga'
import { createStore, combineReducers, applyMiddleware } from 'redux'
import { createHashHistory } from 'history'
import { connectRouter, routerMiddleware } from 'connected-react-router'
import 'moment/locale/zh-cn';
import MyPublic from './Public';//公共文件常量
import MyRedux from './Redux';//Redux管理中心
import MyUser from './User';//用户处理
import MyCorp from './Corp';//公司处理
import MyPostMessage from './PostMessage';//iframe接收消息
import { createConsole } from "./Console"
const MyConsole = createConsole(`Lny`)
const { sagas, reducers, actions } = MyRedux
const history = createHashHistory()
const historyMiddleware = routerMiddleware(history);
const sagaMiddleware = createSagaMiddleware();
const middleware = [historyMiddleware, sagaMiddleware]
//合并reducer
const reducer = combineReducers({
    ...reducers,
})
class Lny {
    start() {
        const { appInfo: { title } } = MyPublic
        document.title = title
        MyConsole.log("启动")
        MyCorp.init().then((corpInfo) => {
            MyConsole.log("corpInfo数据", corpInfo)
            MyPostMessage.listen((e) => { MyConsole.log("接收到的iframe消息：", e) })
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
                    <LocaleProvider locale={zh_CN} >
                        <App history={history} />
                    </LocaleProvider >
                </Provider>
                , rootDom);
        })
    }
}
export default new Lny()


