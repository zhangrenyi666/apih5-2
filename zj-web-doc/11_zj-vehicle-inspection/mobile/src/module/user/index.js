import React from 'react';
import { connect } from 'react-redux';
import { Redirect } from 'react-router-dom';
import { push } from 'react-router-redux';
import { app } from '../../main';
import { localDB, myFetch, getCurUri } from '../../tools';
import { setStatus, statusTypes } from '../../actions'
import { Toast } from 'antd-mobile';
const { set: setLocalDB, get: getLocalDB, remove: removeLocalDB, clear: clearLocalDB } = localDB
const { USER, GUEST } = statusTypes
const isOauth2 = () => {
    return app.type === "3" || app.type === "4";
}
const isLogin = (status) => {
    return status !== GUEST
}
const loginByOauth2 = (uri) => {
    return new Promise((resolve, reject) => {
        const { code } = getCurUri();
        if (app.dev) {
            setTimeout(() => {
                setUserInfo({ realName: "临时用户" })
                setLocalDB("token", app.defaultToken)
                resolve({
                    success: true,
                    action: setStatus(USER),
                    message: "登录成功！"
                })
            }, 3000);
        } else if (!code) {
            window.location.href = uri
        } else {
            const _body = {
                loginType: app.type,
                accountId: app.id,
                code,
            }
            Toast.loading('loading..', 0);
            myFetch("login", _body, true).then((json) => {
                const { success, data, message } = json
                if (success && data && data.token) {
                    setUserInfo(data.userInfo)
                    setLocalDB("token", data.token)
                    resolve({
                        success: true,
                        action: setStatus(USER),
                        message: "登录成功！"
                    })
                } else {
                    resolve({
                        success: false,
                        action: setStatus(GUEST),
                        message
                    })
                }
            })
        }
    })
};//登录

const login = (body = {}) => {
    return new Promise((resolve, reject) => {
        if (app.dev) {
            setTimeout(() => {
                setUserInfo({ company: "一公司", realName: "临时用户" })
                setLocalDB("token", app.defaultToken)
                resolve({
                    success: true,
                    action: setStatus(USER),
                    message: "登录成功！"
                })
            }, 3000);
        } else {
            const _body = {
                loginType: app.type,
                accountId: app.id,
                ...body,
            }
            myFetch("login", _body, true).then((json) => {
                const { success, data, message } = json
                if (success && data && data.token) {
                    setUserInfo(data.userInfo)
                    setLocalDB("token", data.token)
                    resolve({
                        success: true,
                        action: setStatus(USER),
                        message: "登录成功！"
                    })
                } else {
                    resolve({
                        success: false,
                        action: setStatus(GUEST),
                        message
                    })
                }
            })
        }
    })
};//登录
const logout = () => {
    return new Promise((resolve, reject) => {
        clearLocalDB()
        const { noQueryStr, domainStr } = getCurUri();
        document.location.href = document.location.href === noQueryStr ? domainStr : noQueryStr
    })
};//退出
const tab = (company) => {
    return new Promise((resolve, reject) => {
        // setUserInfo({company})
        // setLocalDB("token", app.defaultToken)
        resolve({
            success: true,
            action: push(`/home`),
            message: "切换成功！"
        })
    })
};//退出
const setUserInfo = (userInfo) => {
    return setLocalDB("userInfo", userInfo)
};//获取信息
const getUserInfo = () => {
    const _userInfo = getLocalDB("userInfo")
    !_userInfo && logout()
    return _userInfo
};//获取信息
const removeUserInfo = () => {
    return removeLocalDB("userInfo")
};//获取信息
const withOAuth = (Component, onlyStatus) => {
    const select = (state) => {
        return {
            ...state,
            Component
        }
    }
    return connect(select)((props) => {
        const { status, Component, match: { url } } = props
        if (status === GUEST || (onlyStatus && status !== onlyStatus)) {
            !getLocalDB("redirect_url") && setLocalDB("redirect_url", url)
            return <Redirect to={onlyStatus ? -1 : `/login`} />
        } else {
            return <Component {...props} {...User} />
        }
    })
}
const User = {
    isOauth2,
    isLogin,
    login,
    loginByOauth2,
    logout,
    tab,
    setUserInfo,
    getUserInfo,
    removeUserInfo,
    withOAuth,
}
export default User