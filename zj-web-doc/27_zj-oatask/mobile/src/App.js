import React from 'react';
import { connect } from 'react-redux'
import { Switch } from 'react-router-dom';
import { ConnectedRouter } from 'connected-react-router'
import MyRouter from './lny_modules/Router'
const App = (props) => {
    const { history,loginAndLogoutInfo: { loginInfo } } = props
    // console.log("初始化App") 
    return (
        <ConnectedRouter history={history}>
            <Switch>
                {MyRouter.createRoutes((loginInfo && loginInfo.userInfo && loginInfo.userInfo.menuTree) ? loginInfo.userInfo.menuTree.children || [] : [])}
            </Switch>
        </ConnectedRouter>
    )
}
const selectApp = (state) => {
    return {
        loginAndLogoutInfo: state.loginAndLogoutInfo,

    }
}


export default connect(selectApp)(App);

