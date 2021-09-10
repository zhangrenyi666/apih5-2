// 常量声明
export const { dev,domain,appInfo,storage,wxSdk,apis,other } = window.configs
export const others = window.others || {}
export const androidApi = window.android_api
export const wx = window.wx
export const WwLogin = window.WwLogin
export const URI = window.URI;

export const getCurUri = () => {//获取当前url对象
    const uri = new URI()
    const curUri = {
        uri,
        domainStr: uri.clone().query("").hash("").toString(),//清空hash清空query
        noHashStr: uri.clone().hash("").toString(),//清空hash
        noQueryStr: uri.clone().query("").toString(),//清空query
        code: uri.query(true)["code"] || null,
        state: uri.query(true)["state"] || null,
    }
    return curUri
}

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
    androidApi,
    getCurUri
}
export default MyPublic
