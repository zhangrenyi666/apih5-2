import localDB from '../localDB';
import { domain, apis } from "../../main"
import { Modal, Toast } from 'antd-mobile';
import { User } from '../../module';
const alert = Modal.alert;
const { get: getLocalDB, } = localDB
const myFetch = (url, body = {}, noToken = false) => {
    const token = getLocalDB("token") || ""//获取local存储的token
    if (!noToken && !token) {//默认情况下，noToken=false 就会执行判断，没有token的话就会返回code=000002
        return new Promise((resolve, reject) => {
            resolve({
                success: false,
                code: "000002",
                message: "登录状态失效！"
            })
        })
    }
    const myHeaders = new Headers({
        'Accept': 'application/json',
        'Content-Type': 'application/json;charset=utf-8',
        'X-Requested-With': 'XMLHttpRequest',
        'token': token,
    })
    const myInit = {
        method: 'POST',
        headers: myHeaders,
        mode: 'cors',
        cache: 'default',
        body: JSON.stringify(body)
    };
    const myRequest = new Request(domain.api + apis[url], myInit);
    return fetch(myRequest).then(response => {
        if (response.headers.get("content-type") === "application/json;charset=UTF-8") {
            return response.json()
        } else {
            return {
                success: false,
                code: "000003",
                message: "返回数据非JSON格式！"
            }
        }
    }).then(json => {
        const { message, success, code } = json
        Toast.hide()
        if (!success) {
            if (code === "3003" || code === "3004") {
                User.logout()
            } else {
                Toast.fail(message);
            }
        }
        return json
    }).catch(error => {
        const message = error.toString() === "TypeError: Failed to fetch" ? "网络异常，请检查您的网络连接后，刷新浏览器。" : error.toString()
        Toast.hide()
        Toast.fail(message, 0);
        return {
            success: false,
            code: "000001",
            message
        }
    })
}
export default myFetch