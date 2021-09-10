import React from 'react';
const Home = (props) => {
    const { dispatch, actions: { tabLoginType, login }, loginAndLogoutInfo: { loginInfo, logoutInfo },  myPublic } = props
    const { appInfo } = myPublic
    if (loginInfo && loginInfo.loading) {
        // 登录中。。。
    }
    let loginType = appInfo.loginType
    if (logoutInfo) {
        loginType = logoutInfo.loginType
    }
    return (
        <div>
            <div onClick={() => { dispatch(login({ userId: "admin", userPwd: "123456", loginType: "1" })) }}>{"登录"}</div>
            <div onClick={() => { dispatch(tabLoginType(loginType === "1" ? "5" : "1")) }}>{"切换登录方式"}</div>
        </div>
    )
}

export default Home