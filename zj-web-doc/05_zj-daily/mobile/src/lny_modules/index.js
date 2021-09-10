import App from '../App'
import React from 'react'
import { render } from 'react-dom'
import { Provider } from 'react-redux'
import createSagaMiddleware from 'redux-saga'
import { createStore, combineReducers, applyMiddleware } from 'redux'
import { createHashHistory } from 'history'
import { connectRouter, routerMiddleware } from 'connected-react-router'
import 'moment/locale/zh-cn';
import MyPublic from './Public';//公共文件常量
import MyFetch from './Fetch';//数据请求
import MyRedux from './Redux';//Redux管理中心
import MyUser from './User';//用户处理
import MyCorp from './Corp';//公司处理
import MyPostMessage from './PostMessage';//iframe接受消息
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
        MyCorp.init().then((corpInfo) => {
 
            MyPostMessage.listen((e) => {
                console.log(e)
            })
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
                    myFetch: MyFetch,
                    myPublic: MyPublic,
                    actions,
                    corpInfo,
                },
                applyMiddleware(...middleware)
            )
            // store.subscribe(() => { console.log(store.getState()) })
            //运行saga的effect
            sagaMiddleware.run(sagas)
            const rootDom = document.getElementById('root')
        
            render(
                <Provider store={store}>
                    <App history={history} />
                </Provider>
                , rootDom);
        })
    }
}
export default new Lny()


