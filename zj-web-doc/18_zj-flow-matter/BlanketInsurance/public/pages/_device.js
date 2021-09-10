//浏览器操作判断 
//需要在 config 中配置  pcPageUrl:"http://xxx/xxx",   否则不生效

//需要注意的是移动端和pc端只是token共享可能存在网址不同 所以必须使用webCode方式进行登录 而且登录页面后每次进入pc端都需要携带webCode
//移动端跳转点到pc端后
//pc端需要判断网址是否包含webCode
//如果有webCode需要更改pc端配置中的登录模式
var _webCode = getUrlParam("webCode");
if (_webCode) {
    window.configs.appInfo.webCodeLoginType = "6";
    window.configs.appInfo.loginType = "1";
}

//@token 用户token 
//@params corp信息
// ()=>boolean false为不跳转 true为跳转 
//pc端环境下直接跳转pc地址
window._apih5ToPc = (token,params) => {
    if ((navigator.userAgent.match(/(MicroMessenger)/i))) {
        var isMobile = navigator.userAgent.match(/(iPhone|iPod|ios|iPad|Android)/i);
        // isMobile = false;  //测试代码 
        if (!isMobile) {
            //pc端环境
            //pc需要跳转页面
            if (window.configs.pcPageUrl) {
                var curUrl = window.location.origin + window.location.pathname; 
              
                //当前地址如果不等于配置的的地址将会跳转
                if (window.configs.pcPageUrl.replace(/\//ig,'') !== curUrl.replace(/\//ig,'')) {

                    var toUrl = token ? window.configs.pcPageUrl + '?webCode=' + token : window.configs.pcPageUrl;

                    var wxUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + params.corpId + "&redirect_uri=" + toUrl + "&response_type=code&scope=snsapi_base&agentid=" + params.agentId + "&state=STATE#wechat_redirect"; // 在默认浏览器打开redirect_uri，并附加code参数；也可以直接指定要打开的url，此时不会附带上code参数。

                    wx.ready && wx.ready(function () {
                        if (wx.invoke) {
                            wx.invoke('openDefaultBrowser',{
                                'url': wxUrl
                            },function (res) {
                                if (res.err_msg != "openDefaultBrowser:ok") {
                                    //错误处理
                                    alert('跳转错误：',res.err_msg)
                                } else {
                                    //关闭浏览器
                                    wx.closeWindow && wx.closeWindow();
                                }
                            });
                        } else {
                            alert('跳转失败！请检查浏览器：仅支持 企业微信PC版2.3及以上版本。')
                        }

                    })
                }
            } else {
                console.error('跳转pc端需配置 window.configs.pcPageUrl')
            }
            return true;
        } else {
            //移动端环境 
            return false;
        }
    }
}


function getUrlParam(k) {//获取地址栏参数，k为键名
    var m = new RegExp("(^|&)" + k + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(m);
    if (r != null) return decodeURI(r[2]); return null;
}