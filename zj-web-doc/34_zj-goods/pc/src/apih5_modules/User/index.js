import { Toast } from 'antd-mobile';
import MyStorage from "../Storage"
import MyFetch from "../Fetch"
import MyPublic from "../Public"
//常量声明
const { appInfo,URI,WwLogin,getCurUri } = MyPublic
export const GUEST = "GUEST"//来宾
export const USER = "USER"//用户
export const ADMIN = "ADMIN"//管理员

const { language = "zh_CN" } = window.configs;
const isEn = language === "en_US";
const lanObj = {
    "登录成功": isEn ? "login successfully" : "登录成功",
}

const login = (action) => { //登录
    return new Promise((resolve,reject) => {
        const body = { accountId: appInfo.id,...action }
        Toast.loading('loading...',0);
        MyFetch("login",body,"1").then((result) => {
            let { data,success,message } = result;

            let logoutInfo = getLogoutInfo();
            if (success) {
                Toast.success(lanObj["登录成功"],1.5);
                MyStorage.removeItem("logoutInfo").then(() => {
                    MyStorage.setItem("loginInfo",{ ...data }).then((loginInfo) => {
                        MyStorage.setItem("redirectInfo",{ ...logoutInfo }).then((redirectInfo) => {
                            resolve({ loginInfo,redirectInfo,logoutInfo: null })
                        })
                    })
                })
            } else {
                switch (action.loginType) {
                    case "3":
                    case "4":
                    case "5":
                    case "31":
                        message = "微信授权失败"
                        break;
                    default:
                        break;
                }
                Toast.offline(message,2,() => {
                    let logoutInfo = getLogoutInfo();
                    logoutInfo.username = body.userId;
                    logoutInfo.password = body.userPwd;
                    MyStorage.setItem("loginInfo",{ error: true,loading: false }).then((loginInfo) => {
                        reject({ loginInfo,redirectInfo: null,logoutInfo })
                    })
                })
            }
        })
    })
}
const updateUserInfo = (data) => {
    let { token,userInfo } = getLoginInfo()
    let _userInfo = { ...userInfo,...data }
    let _loginInfo = { token,userInfo: _userInfo }
    return new Promise((resolve,reject) => {
        MyStorage.setItem("loginInfo",_loginInfo).then(() => {
            resolve(_userInfo)
        })
    })
}
const logout = (pathName) => {//退出
    const uri = new URI()
    let path = uri.path()
    let pathArr = path.split("/")
    let realModuleName = path
    if (pathArr[pathArr.length - 1] !== "") {
        realModuleName = path + "/"
    }
    let logoutInfo = getLogoutInfo()
    let loginType = MyPublic.appInfo.loginType
    if (!logoutInfo) {
        logoutInfo = { loginType }
    } else {
        loginType = logoutInfo.loginType
    }
    return new Promise((resolve) => {
        let fragment = uri.fragment().split(path)[1]
        const data = { ...logoutInfo,moduleName: realModuleName,pathName: pathName ? pathName : fragment,fragment }
        setLogoutInfo(data).then(() => {
            const code = uri.query(true)["code"]
            if ((loginType === "3" || loginType === "32" || loginType === "4" || loginType === "5" || loginType === "31") && code) {
                window.location.href = uri.clone().query("").toString()
            } else {
                resolve(data)
            }
        })
    })
}
const tabLoginType = (loginType) => {//退出
    return new Promise((resolve) => {
        let logoutInfo = getLogoutInfo()
        if (logoutInfo) {
            setLogoutInfo({ ...logoutInfo,loginType }).then((logoutInfo) => {
                resolve(logoutInfo)
            })
        } else {
            logout().then((logoutInfo) => {
                setLogoutInfo({ ...logoutInfo,loginType }).then((logoutInfo) => {
                    resolve(logoutInfo)
                })
            })
        }
    })
}

const openLoginLoading = () => {//设置登录信息
    return new Promise((resolve) => {
        let logoutInfo = getLogoutInfo()
        if (logoutInfo) {
            MyStorage.setItem("loginInfo",{ loading: true }).then((loginInfo) => {
                resolve({ loginInfo,logoutInfo })
            })
        } else {
            logout().then((logoutInfo) => {
                MyStorage.setItem("loginInfo",{ loading: true }).then((loginInfo) => {
                    resolve({ loginInfo,logoutInfo })
                })

            })
        }
    })
}
const setRedirectInfo = (newRedirectInfo = null) => {//设置登录信息
    return new Promise((resolve) => {
        let loginInfo = getLoginInfo()
        MyStorage.setItem("redirectInfo",newRedirectInfo).then((redirectInfo) => {
            resolve({ loginInfo,redirectInfo })
        })
    })
}
const setLogoutInfo = (data = {}) => {//设置退出信息
    return new Promise((resolve) => {
        MyStorage.removeItem("loginInfo").then(() => {
            MyStorage.setItem("logoutInfo",data).then(() => {
                resolve(data)
            })
        })
    })
}
const getLoginInfo = () => {//获取登录信息
    return MyStorage.getItem("loginInfo") ? MyStorage.getItem("loginInfo").data : null
}
const getLogoutInfo = () => {//获取登录信息
    return MyStorage.getItem("logoutInfo") ? MyStorage.getItem("logoutInfo").data : null
}
const getRedirectInfo = () => {//获取登录信息
    return MyStorage.getItem("redirectInfo") ? MyStorage.getItem("redirectInfo").data : null
}

//二维码登录
//不能写箭头函数
const loginByWwLogin = function (docId) {
    let { corpId: appid,agentId: agentid } = this.props.corpInfo;
    return new Promise((resolve,reject) => {
        const { domainStr: redirect_uri,uri } = getCurUri();
        const _param = {
            id: docId,
            appid,
            agentid,
            redirect_uri,
            state: uri.hash()
        }
        MyStorage.setItem("isNewCode",true)
        WwLogin(_param);
    })
};



const MyUser = {
    login,
    logout,
    tabLoginType,
    openLoginLoading,
    setRedirectInfo,
    getRedirectInfo,
    getLoginInfo,
    updateUserInfo,
    getLogoutInfo,

    loginByWwLogin,

}
export default MyUser  