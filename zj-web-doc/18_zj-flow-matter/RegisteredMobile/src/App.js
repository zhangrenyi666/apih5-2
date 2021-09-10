import React from 'react';
import { connect } from 'react-redux'
import { Switch } from 'react-router-dom'; 
import { ConnectedRouter } from 'connected-react-router'
import MyRouter from './apih5_modules/Router'

const App = (props) => {
    const { history,loginAndLogoutInfo: { loginInfo = {} } } = props;
    const userInfo = loginInfo ? loginInfo.userInfo : {};
    const menuTree = userInfo ? userInfo.menuTree : null;
    const menuTrees = !menuTree ? [] : ((Array.isArray(menuTree) && menuTree.length) ? menuTree : menuTree.children);

    return (
        <ConnectedRouter history={history}>
            <Switch>{MyRouter.createRoutes(menuTrees ? menuTrees : [])}</Switch>
        </ConnectedRouter>
    )
}
const selectApp = (state) => {
    return {
        loginAndLogoutInfo: state.loginAndLogoutInfo,
    }
}

export default connect(selectApp)(App);

