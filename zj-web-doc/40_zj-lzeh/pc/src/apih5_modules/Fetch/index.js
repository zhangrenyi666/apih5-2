import MyData from "../Data";
import MyStorage from "../Storage";
import MyPublic from "../Public";
import { createConsole } from "../Console";
import { fetch as fetchPolyfill } from 'whatwg-fetch'
import { message as Msg, notification } from "antd";
import $ from 'jquery'

// //是否正在刷新token
// window._refreshTokenIng = false;
// //刷新完token后需要执行的请求函数
// window._refreshedNeedExecAjax = [];

const { language = "zh_CN" } = window.configs;
const isEn = language === "en_US";
const lanObj = {
    "901": isEn ? "Login information expired or abnormal, need to get again!" : "登录信息过期或异常，需要重新获取！",
    "902": isEn ? "Return data in non-json format!" : "返回数据非JSON格式！",
    "903": isEn ? "Unconnected network or request address error" : "未连网络或请求地址错误",
    "904": isEn ? "Simulation data acquisition failed!" : "模拟数据获取失败！",
    "905": isEn ? "Request type error" : "请求类型错误",
}
const MyConsole = createConsole(`Fetch`);
let {
    dev,
    domain,
    apis,
    androidApi,
} = MyPublic;

/**
 * @dataType  是否验证token
 * @toApisObj 是否去apis中匹配接口
 * @headers   一些自定义的头信息 
*/
const MyFetch = (apiName = "", body = {}, dataType = dev ? "3" : "0", toApisObj = true, headers = {}, ) => {

    apis = {
        ...window.configs.apis,
        ...apis
    }

    //返回Promise
    return new Promise(resolve => {
        switch (
        dataType //0、正常；1、不验证token；2、优先Storage；3、模拟数据
        ) {
            case "0": //验证token
            case "1": //不验证token
                let token;
                let access_token;
                if (dataType === "0") {
                    if (androidApi) {
                        //如果是android环境下
                        token = androidApi.getLoginToken();
                    } else {
                        //非微信环境下
                        const _storageData = MyStorage.getItem("loginInfo");
                        if (
                            _storageData &&
                            _storageData.data &&
                            !_storageData.isTimeout
                        ) {
                            token = _storageData.data["token"];
                            access_token = _storageData.data["access_token"];
                        } else {
                            resolve({
                                success: false,
                                code: "901",
                                message: lanObj["901"]
                            });
                            return;
                        }
                    }
                } 
                //令牌
                const tokenObj = { token };
                if (access_token) tokenObj.Authorization = "Bearer " + access_token; 

                const myInit = {
                    method: "POST",
                    headers: {
                        Accept: "application/json",
                        "Content-Type": "application/json;charset=utf-8",
                        "X-Requested-With": "XMLHttpRequest",
                        ...tokenObj,
                        ...headers
                    },
                    mode: "cors",
                    cache: "default",
                    body: JSON.stringify(body)
                };

                if (!window.fetch) {
                    window.fetch = fetchPolyfill;
                }

                const _apiName = toApisObj ? apis[apiName] : apiName;
                if (!_apiName) {
                    // Msg.error(`${apiName}接口未添加到config中.`,0);
                    notification.error({
                        message: '系统遇到问题，请联系运维人员',
                        description: `${apiName}接口未添加到config中.`,
                        duration: null
                    });
                    return;
                }
               
                fetch(domain + _apiName, myInit)
                    .then(response => {
                        const ct = response.headers.get("content-type");
                        if (ct === "application/json;charset=UTF-8" || ct === "application/json") {
                            return response.json();
                        } else {
                            return {
                                success: false,
                                code: "902",
                                message: lanObj["902"]
                            };
                        }
                    })
                    .then(result => {
                        if (!result.success) {
                            if (result.message === "No message available") {
                                MyConsole.error(apiName + "接口不存在");
                                result.message = apiName + "接口不存在";
                            } else {
                                MyConsole.error(
                                    apiName,
                                    JSON.stringify(body),
                                    result.message
                                );
                            }
                        }
                        //只有这这可能操作函数返回的resolve
                        //为了避免用户要执行的请求动作失效如果返回3007必须在这块拦截而且刷新token后再次执行重复动作并且resolve
                        if (result.code === "3007" || result.code === "A0230") { 
                            const loginInfo = MyStorage.getItem("loginInfo").data;
                            const { apis, appInfo: { grant_type, client_id, client_secret } } = window.configs;
                            const isNewVer = grant_type && client_id && client_secret;
                            if (isNewVer && !loginInfo.refresh_token) {
                                //需要重新登录
                                resolve({
                                    success: false,
                                    code: "901",
                                    message: lanObj["901"]
                                });
                                return;
                            }
                            const loginApiGetStr = !isNewVer ? "refreshAccessToken" : `${apis["refreshAccessToken"]}?grant_type=refresh_token&client_id=${client_id}&client_secret=${client_secret}&refresh_token=${loginInfo.refresh_token}`;
                      
                            MyFetch(loginApiGetStr, { token: loginInfo.token }, "1", !isNewVer).then(({ success, data, message, code }) => { 
                                if (success) { 

                                    let newLoginInfo = {};
                                    if (isNewVer) {
                                        newLoginInfo = data;
                                    } else {
                                        newLoginInfo["token"] = data;
                                    }
                                    let _loginInfo = {
                                        ...loginInfo,
                                        ...newLoginInfo
                                    };

                                    MyStorage.setItem("loginInfo", { ..._loginInfo }).then((loginInfo) => {
                                        //重新执行失败的请求   
                                        MyFetch(apiName, body, dataType).then((result) => {
                                            if (result.success) {
                                                resolve({ ...result });
                                            } else {
                                                if (result.code === '-1') {
                                                    notification.error({
                                                        message: '系统遇到问题，请联系运维人员',
                                                        description: result.message,
                                                        duration: 3
                                                    });
                                                } else {
                                                    notification.warn({
                                                        message: '提示',
                                                        description: result.message,
                                                        duration: 3
                                                    });
                                                }
                                            }
                                        })
                                    })
                                } else {
                                    if (code === '-1') {
                                        notification.error({
                                            message: '系统遇到问题，请联系运维人员',
                                            description: message,
                                            duration: 3
                                        });
                                    } else {
                                        notification.warn({
                                            message: '提示',
                                            description: message,
                                            duration: 3
                                        });
                                    }
                                }
                            })
                        } else {
                            resolve({ ...result });
                        }

                        // resolve({ ...result });
                    })
                    .catch(error => {
                        resolve({
                            success: false,
                            code: "903",
                            message:
                                error.toString() ===
                                    "TypeError: Failed to fetch"
                                    ? lanObj["903"] : error.toString()
                        });
                        MyConsole.error(
                            apiName,
                            error.toString() === "TypeError: Failed to fetch"
                                ? lanObj["903"] : error.toString()
                        );
                    });
                // .finally((res)=>{
                //     console.log(res)
                // })
                break;
            case "2":
                let _storageData = MyStorage.getItem(apiName);
                if (
                    _storageData &&
                    _storageData.data &&
                    !_storageData.isTimeout
                ) {
                    resolve({
                        success: true,
                        message: "缓存数据获取成功！",
                        data: _storageData.data
                    });
                } else {
                    MyFetch(apiName, body, "0").then(result => {
                        if (result.success) {
                            MyStorage.setItem(apiName, result.data);
                        }
                        resolve({ ...result });
                    });
                }
                break;
            case "3":
                MyData.get(apiName, body)
                    .then(data => {
                        resolve({
                            success: true,
                            message: "模拟数据获取成功！",
                            data
                        });
                    })
                    .catch(data => {
                        resolve({
                            success: false,
                            code: "904",
                            message: lanObj["904"]
                        });
                        MyConsole.error(apiName, "模拟数据获取失败！");
                    });
                break;
            default:
                resolve({
                    success: false,
                    code: "905",
                    message: lanObj["905"]
                });
                MyConsole.error(apiName, "请求类型错误");
                break;
        }
    });
};


//upload(uploadUrl)().then((res)=>{})
const upload = (uploadUrl) => {

    return (e) => {
        Msg.loading('loading...', 0)
        return new Promise(function (resolve, reject) {

            let token;
            let access_token;
            if (androidApi) {
                //如果是android环境下
                token = androidApi.getLoginToken();
            } else {
                //非微信环境下
                const _storageData = MyStorage.getItem("loginInfo");
                if (
                    _storageData &&
                    _storageData.data &&
                    !_storageData.isTimeout
                ) {
                    token = _storageData.data["token"];
                    access_token = _storageData.data["access_token"];
                } else {
                    resolve({
                        success: false,
                        code: "901",
                        message: lanObj["901"]
                    });
                    return;
                }
            }

            let file = e.target.files[0];
            let fd = new FormData();
            fd.append("fileToUpload", file);
            let apiAddress = (uploadUrl.indexOf("http") !== -1) ? uploadUrl : domain + apis[uploadUrl];
            if (!apiAddress) {
                // Msg.error(`${uploadUrl}接口未添加到config中,或者直接使用http地址`,0);

                notification.error({
                    message: '系统遇到问题，请联系运维人员',
                    description: `${uploadUrl}接口未添加到config中,或者直接使用http地址`,
                    duration: null
                });
                return;
            }
            //令牌
            const tokenObj = { token };
            if (access_token) tokenObj.access_token = "Bearer " + access_token;

            $.ajax({
                url: apiAddress,
                type: "POST",
                data: fd,
                processData: false,
                contentType: false,
                dataType: 'json',
                // async: false,
                headers: {
                    ...tokenObj
                },
                success: function ({ data, success, message, code }) {
                    Msg.destroy();
                    if (code === "3007") {
                        //需要去刷新缓存  
                        // window._refreshTokenIng = true;
                        const loginInfo = MyStorage.getItem("loginInfo").data;
                        const { apis, appInfo: { grant_type, client_id, client_secret } } = window.configs;
                        const isNewVer = grant_type && client_id && client_secret;
                        const loginApiGetStr = !isNewVer ? "refreshAccessToken" : `${apis["refreshAccessToken"]}?grant_type=${grant_type}&client_id=${client_id}&client_secret=${client_secret}&refresh_token=${loginInfo.refresh_token}`;

                        MyFetch(loginApiGetStr, { token: loginInfo.token }, "1", !isNewVer).then(({ success, data, message, code }) => {
                            // window._refreshTokenIng = false;
                            if (success) {
                                let _loginInfo = {
                                    ...loginInfo,
                                    token: data
                                };
                                MyStorage.setItem("loginInfo", { ..._loginInfo }).then((loginInfo) => {
                                    //重新执行失败的请求   
                                    upload(uploadUrl)(e).then((res) => {
                                        resolve(res);
                                    })
                                })
                            } else {
                                if (code === '-1') {
                                    notification.error({
                                        message: '系统遇到问题，请联系运维人员',
                                        description: message,
                                        duration: 3
                                    });
                                } else {
                                    notification.warn({
                                        message: '提示',
                                        description: message,
                                        duration: 3
                                    });
                                }
                            }
                        })

                    } else {
                        //成功后的回调事件
                        if (success) {
                            resolve({ data, success, message, code });
                        } else {
                            reject({ data, success, message, code })
                            if (code === '-1') {
                                notification.error({
                                    message: '系统遇到问题，请联系运维人员',
                                    description: message,
                                    duration: 3
                                });
                            } else {
                                notification.warn({
                                    message: '提示',
                                    description: message,
                                    duration: 3
                                });
                            }
                        }
                    }
                },
                error: function (error) {
                    reject({ data: null, success: false, message: error.statusText })
                    notification.error({
                        message: '系统遇到问题，请联系运维人员',
                        description: `error:${error.statusText}`,
                        duration: 3
                    });
                    Msg.destroy();
                }
            })
        })
    }


}

export { upload };
export default MyFetch;
