import MyData from "../Data"
import MyStorage from "../Storage"
import MyPublic from "../Public"
import { createConsole } from "../Console"
const MyConsole = createConsole(`Fetch`)
const { dev, domain, apis } = MyPublic
const MyFetch = (apiName = "", body = {}, dataType = dev ? "3" : "0") => {
    //返回Promise
    return new Promise((resolve) => {
        switch (dataType) {//0、正常；1、不验证token；2、优先Storage；3、模拟数据
            case "0"://验证token
            case "1"://不验证token
                let token;
                if (dataType === "0") {
                    const _storageData = MyStorage.getItem("loginInfo")
                    if (_storageData && _storageData.data && !_storageData.isTimeout) {
                        token = _storageData.data["token"]
                    } else {
                        resolve({
                            success: false,
                            code: "901",
                            message: "登录信息过期或异常，需要重新获取！"
                        })
                        return
                    }
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
                const myRequest = new Request(domain + apis[apiName], myInit);
                fetch(myRequest).then(response => {
                    if (response.headers.get("content-type") === "application/json;charset=UTF-8") {
                        return response.json()
                    } else {
                        return {
                            success: false,
                            code: "902",
                            message: "返回数据非JSON格式！"
                        }
                    }
                }).then((result) => {
                    if (!result.success) {
                        if (result.message === "No message available") {
                            MyConsole.error(apiName + "接口不存在")
                            result.message = apiName + "接口不存在"
                        } else {
                            MyConsole.error(apiName, JSON.stringify(body), result.message === "No message available" ? (apiName + "接口不存在") : result.message)
                        }
                    }
                    resolve({ ...result })
                }).catch(error => {
                    resolve({ success: false, code: "903", message: error.toString() === "TypeError: Failed to fetch" ? "未连网络或请求地址错误" : error.toString() })
                    MyConsole.error(apiName, error.toString() === "TypeError: Failed to fetch" ? "未连网络或请求地址错误" : error.toString())
                })
                break;
            case "2":
                let _storageData = MyStorage.getItem(apiName)
                if (_storageData && _storageData.data && !_storageData.isTimeout) {
                    resolve({ success: true, message: "缓存数据获取成功！", data: _storageData.data })
                } else {
                    MyFetch(apiName, body, "0").then((result) => {
                        if (result.success) {
                            MyStorage.setItem(apiName, result.data)
                        }
                        resolve({ ...result })
                    })
                }
                break;
            case "3":
                MyData.get(apiName, body).then(data => {
                    resolve({ success: true, message: "模拟数据获取成功！", data })
                }).catch(data => {
                    resolve({ success: false, code: "904", message: "模拟数据获取失败！" })
                    MyConsole.error(apiName, "模拟数据获取失败！")
                })
                break;
            default:
                resolve({ success: false, code: "905", message: "请求类型错误!" })
                MyConsole.error(apiName, "请求类型错误")
                break;
        }
    })
}
export default MyFetch