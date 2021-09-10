import React,{ Suspense } from 'react';
import { connect } from 'react-redux'
import { Switch } from 'react-router-dom';
import { ConnectedRouter } from 'connected-react-router'
import MyRouter from './apih5_modules/Router'
import { LoadingOutlined } from '@ant-design/icons';
import { Spin } from "antd"

const App = (props) => {
    const { history,loginAndLogoutInfo: { loginInfo = {} } } = props;
    const userInfo = loginInfo ? loginInfo.userInfo : {};
    const menuTree = userInfo ? userInfo.menuTree : null;
    const menuTrees = !menuTree ? [] : ((Array.isArray(menuTree) && menuTree.length) ? menuTree : menuTree.children);
    const loading = <div style={{ margin: '50px auto',textAlign: "center" }}><Spin tip="loading..." indicator={<LoadingOutlined style={{ fontSize: 24 }} spin />} /></div>;

    return (
        <ConnectedRouter history={history}>
            <Suspense fallback={loading}>
                <Switch>{MyRouter.createRoutes(menuTrees ? menuTrees : [])}</Switch>
            </Suspense>
        </ConnectedRouter>
    )
}
const selectApp = (state) => {
    return {
        loginAndLogoutInfo: state.loginAndLogoutInfo,
    }
}

export default connect(selectApp)(App);

