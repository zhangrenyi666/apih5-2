import MyFetch from "../Fetch"
import MyData from "../Data"
import MyToast from "../Toast"
import MyPublic from "../Public"
import { createConsole } from "../Console"
import { Toast } from 'antd-mobile';
const MyConsole = createConsole(`Fetch`)
const { wx, URI, appInfo: { id: accountId }, dev, wxSdk: { jsApiList, enabled, debug } } = MyPublic
const init = () => { //初始化
    MyToast.hide()
    const uri = new URI()
    return new Promise((resolve) => {
        const body = {
            accountId,
            jssdkUrl: enabled ? uri.clone().hash("").toString() : "",
            domianUrl: uri.clone().query("").hash("").toString(),
            state: uri.hash()
        }
        Toast.loading("loading...", 0)
        MyFetch("getCorpInfo", { ...body }, "1").then((result) => {
            const { success, data, message } = result
            if (success) {
                Toast.hide()
                MyData.get("routerInfo").then((routerInfo) => {
                    if (enabled) {
                        configWx(data.jssdkEntity).then(() => {
                            resolve({ ...data, routerInfo })
                        }).catch(() => {
                            init()
                        })
                    } else {
                        resolve({ ...data, routerInfo })
                    }
                })
            } else {
                Toast.offline(message, 0)
            }
        })
    })
}
const configWx = (jssdkEntity) => {
    return new Promise((resolve, reject) => {
        if (!dev) {
            const { appid: appId, noncestr: nonceStr, timestamp, signature } = jssdkEntity;
            wx.config({
                debug, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId, // 必填，公众号的唯一标识
                timestamp, // 必填，生成签名的时间戳
                nonceStr, // 必填，生成签名的随机串
                signature, // 必填，签名，见附录1
                jsApiList, // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
            });
            wx.ready(() => {
                MyConsole.info("ready")
                resolve()
            })
            wx.error((res) => {
                MyConsole.error(res)
                reject()
            })
        } else {
            resolve()
        }
    })
}
const MyCorp = {
    init,
    configWx
}
export default MyCorp  