import { app, wx, wxConfigs } from '../../main';
import { myFetch, getCurUri } from '../../tools';
import { Toast } from 'antd-mobile';
const { debug, jsApiList, enabled } = wxConfigs
const getCorpInfo = () => {
    return new Promise((resolve, reject) => {
        const { domainStr, noHashStr, uri } = getCurUri();
        const _body = {
            jssdkUrl: enabled ? noHashStr : "",
            domianUrl: domainStr,
            accountId: app.id,
            state: uri.hash()
        }
        if(app.dev){ 
            resolve({
                relName:'dev模式测试'
            })
        }else{
            Toast.loading('loading..', 0);
            myFetch("getCorpInfo", _body, true).then((json) => {
                const { success, data } = json
                if (success && data) {
                    const { jssdkEntity: { appid: appId, noncestr: nonceStr, timestamp, signature } } = data;
                    if (enabled) {
                        wx.config({
                            debug, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                            appId, // 必填，公众号的唯一标识
                            timestamp, // 必填，生成签名的时间戳
                            nonceStr, // 必填，生成签名的随机串
                            signature, // 必填，签名，见附录1
                            jsApiList, // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                        });
                    }
                    resolve(data)
                }
            })
        }
        
    })
}

const Corp = {
    getCorpInfo
}
export default Corp