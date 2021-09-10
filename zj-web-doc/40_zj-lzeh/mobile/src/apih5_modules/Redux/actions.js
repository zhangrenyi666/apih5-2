//声明action常量
import MyViews from '../../views'
const { actions: viewActions } = MyViews
export const SET_LOGOUTANDLOGOUTINFO = 'SET_LOGOUTANDLOGOUTINFO';//设置登录信息
export const TAB_LOGINTYPE = 'TAB_LOGINTYPE'
export const SET_FETCHDATAS = 'SET_FETCHDATAS'
export const LOGIN_REQUEST = 'LOGIN_REQUEST';//设置公司信息
export const SET_REDIRECTINFO = 'SET_REDIRECTINFO';//设置公司信息
export const LOGOUT = 'LOGOUT';//设置公司信息
export const LOGIN_ERROR = 'LOGIN_ERROR'
export const FETCH = 'FETCH'
export const loginTypes = {
    SUPER_ADMINISTRATOR: "SUPER_ADMINISTRATOR",
    ADMINISTRATOR: "ADMINISTRATOR",
    USER: "USER",
    GUEST: "GUEST"
}
export function setLoginAndLogoutInfo(data) {
    return { type: SET_LOGOUTANDLOGOUTINFO,data }
}
export function tabLoginType(loginType) {
    return { type: TAB_LOGINTYPE,loginType }
}
export function setRedirectInfo(redirectInfo) {
    return { type: SET_REDIRECTINFO,redirectInfo }
}
export function login(action) {
    return { type: LOGIN_REQUEST,...action }
}
export function logout(pathName) {
    return { type: LOGOUT,pathName }
}



export function sagaFetch(apiName,option = {}) {
    return { type: FETCH,apiName,...option }
}
 







const actions = {
    setLoginAndLogoutInfo,
    sagaFetch,
    setRedirectInfo,
    login,
    logout,
    tabLoginType, 
    ...viewActions
}
export default actions
