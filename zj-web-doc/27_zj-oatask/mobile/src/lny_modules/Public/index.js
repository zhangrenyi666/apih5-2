// 常量声明
export const { dev, domain, appInfo, storage, wxSdk, apis, other } = window.configs
export const others = window.others || {}
export const androidApi = window.android_api
export const wx = window.wx
export const WwLogin = window.WwLogin
export const URI = window.URI
const MyPublic = {
    dev,
    domain,
    appInfo,
    storage,
    wxSdk,
    apis,
    wx,
    WwLogin,
    others,
    URI,
    androidApi
}
export default MyPublic
