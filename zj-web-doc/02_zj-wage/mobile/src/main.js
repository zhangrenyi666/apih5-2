/*!
 * lny-mobile-main v0.5.0
 * create by lny on 2017-1-11.
 * base on "antd-mobile": "^2.1.1", 
 */
import React from 'react';
import { Toast, Modal } from 'antd-mobile';//antd-mobile的loading弹窗
import { setInterval, clearInterval, setTimeout } from 'timers';
import { createHashHistory } from 'history'
import moment from 'moment';
import 'moment/locale/zh-cn';
const alert = Modal.alert;
const { wx, URI, lnyConfig } = window;//微信jssdk对象
const { jsApiList, dev, wxDebug, domain, apiDatas, appInfo, testDatas: { userInfo }, localStorage: { timeOut } } = lnyConfig;
const wxJsSdk = { inited: false, configing: false, ready: false, error: false, errCount: 0 }
//内部函数
const getApiData = (key) => {//通过api名称获取api路径
    const apiData = {
        name: "该接口未进行配置",
        url: getDomain() + key,
    }
    if (apiDatas[key]) {
        apiData["name"] = apiDatas[key].name;
        apiData["url"] = getDomain() + apiDatas[key].url;
    }
    return apiData
}
//基本函数
export const getDomain = (type) => {//获取当前url对象
    let _domain;
    switch (type) {
        case "file":
            _domain = domain["file"] || domain["api"]
            break;
        default:
            _domain = domain["api"]
            break;
    }
    return _domain
}
export const getCurUri = () => {//获取当前url对象
    const uri = new URI()
    const curUri = {
        uri,
        domainUri: uri.clone().query("").hash(""),//清空hash清空query
        url: uri.clone().hash("").toString(),//清空hash
        code: uri.query(true)["code"] || null,
        redirectPathname: uri.query(true)["state"] || null,
        domainStr: uri.clone().query("").hash("").toString(),//清空hash清空query
        noHashStr: uri.clone().hash("").toString(),//清空hash
        noQueryStr: uri.clone().query("").toString(),//清空query
    }
    return curUri
}
export const MyFetch = (apiName, body, callback) => {//封装fetch
    if (typeof body === "function") {
        callback = body;
        body = {};
    } else if (typeof callback !== "function") {
        callback = () => { }
    }
    const startTime = new Date().getTime()
    function errlog(json) {
        const endTime = new Date().getTime()
        console.log("请求名称：", getApiData(apiName).name)
        console.log("请求地址：", getApiData(apiName).url)
        console.log("请求参数：", JSON.stringify(body))
        console.log("请求返回：", json)
        console.log("请求用时：", (endTime - startTime) + "ms")
    }

    //加上token----1 获取token
    const { APPID: appid } = appInfo;
    const localDB = JSON.parse(localStorage.getItem("localDB_" + appid)) || {};

    fetch(getApiData(apiName).url, {
        mode: "cors", //no-cors
        // credentials: dev ? "omit" : 'include',
        method: 'POST',
        headers: {
            'token': localDB['userId'],//加上token----2
            'Accept': 'application/json',
            'Content-Type': 'application/json; charset=utf-8',
            'X-Requested-With': 'XMLHttpRequest'
        },
        body: JSON.stringify(body)
    }).then(response => {
        return response.json()
    }).then(json => {
        const { success, message, code } = json
        if (!success) {
            if (code === "3003" || code === "3004") {
                clearLocalDB()
            } else {
                Toast.offline(message, 2, () => { callback(json) });
            }
            errlog(json)
        } else {
            callback(json)
        }
    }).catch((err) => {
        let message = "抱歉，客户端异常!"
        if (err.toString() === "TypeError: Failed to fetch") {
            message = "抱歉，您的网络异常!"
        }
        const json = {
            success: null,
            data: null,
            message
        }
        errlog(json)
        Toast.offline(message, 2, () => { callback(json) });
    })
}
export const initLocalDB = (code, redirectPathname) => {//初始化本地数据
    Toast.loading("正在初始化本地数据", 0)
    const { TYPE: type, APPID } = appInfo;
    //接口-----3
    let body = {
        accountId: APPID,
        loginType: type === "Qyh" ? '3' : '4',
        code
    }
    let apiname = "login";
    MyFetch(apiname, body, ({ success, data }) => {
        if (success) {
            //将token存到本地----4
            const { token: userId, userInfo } = data;
            userInfo.name = userInfo.realName;//增加一个name字段
            setLocalDB(redirectPathname, { userId, userInfo })
            // updateOtherInfo(userId, (other) => {
            //     setLocalDB(redirectPathname, { userId, other })
            // })
        } else {
            setLocalDB(redirectPathname)
        }
    })
}
export const clearLocalDB = () => {
    const history = createHashHistory() 
    localStorage.clear()
    const { noQueryStr } = getCurUri(); 
    setTimeout(() => {
        document.location.href = noQueryStr
    }, 20)
    history.replace("/loading")
}
export const setLocalDB = (redirectPathname, data) => {

    const { APPID: appid } = appInfo;
    const localDB = data || {}
    const history = createHashHistory()
    localDB["createTime"] = new Date().getTime()
    localStorage.setItem("localDB_" + appid, JSON.stringify(localDB))
    let timer = setInterval(() => {
        let localDB = localStorage.getItem("localDB_" + appid)
        if (localDB) {
            clearInterval(timer)
            Toast.hide();
            setTimeout(() => {
                history.replace(redirectPathname)
            }, 0)
            history.replace("/loading")

        }
    }, 10)
}
export const updateOtherInfo = (userId, callback) => {
    MyFetch("getPermitUserInfoByUserid", { userid: userId }, ({ success, data }) => {
        let other = { createTime: new Date().getTime() };
        if (success && data) {
            const { isAllow, mobile, name } = data
            other = { isAllow, mobile, name, createTime: new Date().getTime() }
        }
        callback(other)
    })
}
export const getAllowToPath = () => {//判断是否授权
    if (dev) {
        return {
            state: 1,
            message: "当前为开发者模式。"
        }
    }
    const { APPID: appid } = appInfo;
    const localDBStr = localStorage.getItem("localDB_" + appid)
    if (localDBStr) {
        const localDB = JSON.parse(localDBStr);
        const { createTime, userId } = localDB;
        const curTime = new Date().getTime()
        const isTimeout = curTime - createTime > timeOut//超时
        if (isTimeout) {
            localStorage.removeItem("localDB_" + appid);
            toOauth2()
            return {
                state: 2,
                message: "授权信息已过期，正在重新验证用户权限。"
            }
        } else {
            if (userId) {
                return {
                    state: 1,
                    message: "成功"
                }
                // const { other } = localDB;
                // const { isAllow, mobile, name } = other
                // if (isAllow === "1") {
                //     let nameReg = /^用户.*$/;
                //     if (name && mobile && !nameReg.test(name)) {
                //         return {
                //             state: 1,
                //             message: "成功"
                //         }
                //     } else {
                //         return {
                //             state: 4,
                //             message: "用户信息有误，请联系管理员。"
                //         }
                //     }
                // } else {
                //     return {
                //         state: 3,
                //         message: "未在任何部门进行过信息注册或已被移出，如有疑问请联系相关部门主管。"
                //     }
                // }
            } else {
                return {
                    state: -1,
                    message: "无权限查看本系统，如有需求请联系统管理员。"
                }
            }
        }
    } else {
        const { code, redirectPathname } = getCurUri();
        if (code) {
            initLocalDB(code, redirectPathname)
            return {
                state: 0,
                message: "欢迎进入本系统，正在验证用户权限，请稍后。"
            }
        } else {
            toOauth2()
            return {
                state: -2,
                message: "欢迎进入本系统，正在验证用户权限，请稍后。"
            }
        }
    }
}
export const getUserInfo = (callback, forceUpdate) => {//获取登录用户信息
    if (dev) { callback(userInfo); return }
    const { APPID: appid } = appInfo;
    const localDB = JSON.parse(localStorage.getItem("localDB_" + appid))
    if (forceUpdate) {
        const { userId } = localDB
        updateOtherInfo(userId, (other) => {
            localDB["other"] = other
            localStorage.setItem("localDB_" + appid, JSON.stringify(localDB))
            callback(localDB)
        })
    } else {
        callback(localDB)

    }
}
const toOauth2 = () => {//配置OAuth2授权url
    const history = createHashHistory()
    const { location: { pathname } } = history
    const { domainUri } = getCurUri();
    const { APPID: appid, SCOPE: scope } = appInfo;
    let body = {
        accountId: appid,
        state: pathname,
        domianUrl: domainUri.toString()
    }
    MyFetch("getCorpInfo", body, ({ success, data }) => {
        if (success) {
            window.location.href = data.uri
        }
    })
};
export const getWxJsSdk = (callback) => {
    if (dev) {
        Toast.offline('测试模式下，微信功能不可用', 2, () => { callback({ wx, ...wxJsSdk }) })
    } else if (!wxJsSdk.inited) {
        Toast.offline('未进行初始化配置，微信功能不可用', 2, () => { callback({ wx, ...wxJsSdk }) })
    } else if (!wxJsSdk.ready) {
        Toast.loading('微信功能配置尚未完成，稍后重试', 2, () => { callback({ wx, ...wxJsSdk }) })
    } else if (wxJsSdk.error) {
        configWxJsSdk()
        Toast.offline('配置错误，微信功能不可用', 2, () => { callback({ wx, ...wxJsSdk }) })
    } else {
        callback({ wx, ...wxJsSdk })
    }
}
export const configWxJsSdk = () => {
    wxJsSdk.configing = true
    const { url, domainUri } = getCurUri();
    const { APPID: appid } = appInfo;
    const history = createHashHistory()
    const { location: { pathname } } = history
    let body = {
        accountId: appid,
        state: pathname,
        jssdkUrl: url,
        domianUrl: domainUri.toString()
    }
    MyFetch("getCorpInfo", body, ({ success, data }) => {
        if (success) {
            const { jssdkEntity: { appid: appId, noncestr: nonceStr, timestamp, signature } } = data;
            wx.config({
                debug: wxDebug, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId, // 必填，公众号的唯一标识
                timestamp, // 必填，生成签名的时间戳
                nonceStr, // 必填，生成签名的随机串
                signature, // 必填，签名，见附录1
                jsApiList,
            });
        } else {
            wxJsSdk.configing = false
        }
    })
}
export const initWxJsSdk = () => {//进行微信jssdk的config配置
    if (dev || wxJsSdk.inited) { return }
    wxJsSdk.inited = true
    wx.ready(function () {
        wxJsSdk.ready = true
        wxJsSdk.configing = false
    });
    wx.error(function (err) {
        wxJsSdk.error = true
        wxJsSdk.configing = false
    });
    configWxJsSdk()
}
//工具函数
export const getDeviceType = () => {//获取设备类型
    let u = navigator.userAgent;
    if (u.indexOf('Android') > -1 || u.indexOf('Linux') > -1) { //安卓手机
        return 'Android';
    } else if (u.indexOf('iPhone') > -1) { //苹果手机
        return 'Ios';
    } else if (u.indexOf('Windows Phone') > -1) { //winphone手机
        return 'Windows Phone';
    } else {
        return 'undefined';
    }
}
export const toDecimal2 = (x) => {//金融数字格式化（补全小数点后两位）
    let f = parseFloat(x);
    if (isNaN(f)) {
        return false;
    }
    let f2 = Math.round(x * 100) / 100;
    let s = f2.toString();
    let rs = s.indexOf('.');
    if (rs < 0) {
        rs = s.length;
        s += '.';
    }
    while (s.length <= rs + 2) {
        s += '0';
    }
    return s;
}
export class Base64 {//base64加密解密
    // private property  
    _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

    // public method for encoding  
    encode = (input) => {
        let output = "";
        let chr1, chr2, chr3, enc1, enc2, enc3, enc4;
        let i = 0;
        input = this._utf8_encode(input);
        while (i < input.length) {
            chr1 = input.charCodeAt(i++);
            chr2 = input.charCodeAt(i++);
            chr3 = input.charCodeAt(i++);
            enc1 = chr1 >> 2;
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
            enc4 = chr3 & 63;
            if (isNaN(chr2)) {
                enc3 = enc4 = 64;
            } else if (isNaN(chr3)) {
                enc4 = 64;
            }
            output = output +
                this._keyStr.charAt(enc1) + this._keyStr.charAt(enc2) +
                this._keyStr.charAt(enc3) + this._keyStr.charAt(enc4);
        }
        return output;
    }

    // public method for decoding  
    decode = (input) => {
        let output = "";
        let chr1, chr2, chr3;
        let enc1, enc2, enc3, enc4;
        let i = 0;
        input = input.replace(/[^A-Za-z0-9+/=]/g, "");
        while (i < input.length) {
            enc1 = this._keyStr.indexOf(input.charAt(i++));
            enc2 = this._keyStr.indexOf(input.charAt(i++));
            enc3 = this._keyStr.indexOf(input.charAt(i++));
            enc4 = this._keyStr.indexOf(input.charAt(i++));
            chr1 = (enc1 << 2) | (enc2 >> 4);
            chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
            chr3 = ((enc3 & 3) << 6) | enc4;
            output = output + String.fromCharCode(chr1);
            if (enc3 !== 64) {
                output = output + String.fromCharCode(chr2);
            }
            if (enc4 !== 64) {
                output = output + String.fromCharCode(chr3);
            }
        }
        output = this._utf8_decode(output);
        return output;
    }

    // private method for UTF-8 encoding  
    _utf8_encode = (string) => {
        string = string.replace(/\r\n/g, "\n");
        let utftext = "";
        for (let n = 0; n < string.length; n++) {
            let c = string.charCodeAt(n);
            if (c < 128) {
                utftext += String.fromCharCode(c);
            } else if ((c > 127) && (c < 2048)) {
                utftext += String.fromCharCode((c >> 6) | 192);
                utftext += String.fromCharCode((c & 63) | 128);
            } else {
                utftext += String.fromCharCode((c >> 12) | 224);
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);
                utftext += String.fromCharCode((c & 63) | 128);
            }

        }
        return utftext;
    }

    // private method for UTF-8 decoding  
    _utf8_decode = (utftext) => {
        let string = "";
        let i = 0;
        let c, c2, c3;
        c = c2 = 0;
        while (i < utftext.length) {
            c = utftext.charCodeAt(i);
            if (c < 128) {
                string += String.fromCharCode(c);
                i++;
            } else if ((c > 191) && (c < 224)) {
                c2 = utftext.charCodeAt(i + 1);
                string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
                i += 2;
            } else {
                c2 = utftext.charCodeAt(i + 1);
                c3 = utftext.charCodeAt(i + 2);
                string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
                i += 3;
            }
        }
        return string;
    }
}
export const pxToRem = (_s, pixelRatio) => {//像素转rem,倍数100
    let _x = _s.toString().replace(/px/i, '');
    return parseFloat(_x) / (pixelRatio || window.devicePixelRatio * 50);
}
export const remToPx = (_s, pixelRatio) => {//像素转rem,倍数100
    let _x = _s.toString().replace(/rem/i, '');
    return parseFloat(_x) * (pixelRatio || window.devicePixelRatio * 50);
}
export const fromNow = (preDate) => {
    const curDate = new Date().getTime();
    const difDate = Math.max(curDate - preDate, 0);
    let textDate = ""
    let count = 0;
    if (difDate < 60 * 1000) {//小于一分钟
        textDate = "刚刚"
    } else if (difDate < 60 * 60 * 1000) {//小于一小时
        count = parseInt(difDate / (60 * 1000), 10)
        textDate = count + "分钟前"
    } else if (difDate < 24 * 60 * 60 * 1000) {//小于一天
        count = parseInt(difDate / (60 * 60 * 1000), 10)
        textDate = count + "小时前"
    } else if (difDate < 7 * 24 * 60 * 60 * 1000) {//小于一周
        count = parseInt(difDate / (24 * 60 * 60 * 1000), 10)
        textDate = count + "天前"
    } else {
        if (new Date(preDate).getFullYear() !== new Date(curDate).getFullYear) {
            textDate = moment(preDate).format("YYYY-MM-DD")
        } else {
            textDate = moment(preDate).format("MM-DD")
        }
    }
    return textDate
}
//antd-mobile增强
export const CustomIcon = ({ type, className = '', size = 'md', ...restProps }) => (
    <svg
        className={`am-icon am-icon-${type.substr(1)} am-icon-${size} ${className}`}
        {...restProps}
    >
        <use xlinkHref={type} /> {/* svg-sprite-loader@0.3.x */}
        {/* <use xlinkHref={#${type.default.id}} /> */} {/* svg-sprite-loader@lastest */}
    </svg>
);

