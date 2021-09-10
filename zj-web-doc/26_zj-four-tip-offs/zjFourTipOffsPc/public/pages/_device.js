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

//@toMobile function 非pc时候将会执行
//pc端环境下直接跳转pc地址
window._apih5ToPc = (token,toMobile) => {
    if ((navigator.userAgent.match(/(MicroMessenger)/i))) {
        var isMobile = navigator.userAgent.match(/(iPhone|iPod|ios|iPad|Android)/i);
        isMobile = false;
        if (!isMobile) {
            //pc端环境
            //pc需要跳转页面
            if (window.configs.pcPageUrl) {
                var curUrl = window.location.origin + window.location.pathname;
                var exg = new RegExp(curUrl,'ig');
                if (!exg.test(window.configs.pcPageUrl)) {
                    var toUrl = token ? window.configs.pcPageUrl + '?webCode=' + token : window.configs.pcPageUrl;
                    window.location.href = toUrl;
                }
            } else {
                console.error('跳转pc端需配置 window.configs.pcPageUrl')
            }
        } else {
            //移动端环境
            toMobile && toMobile()
        }
    }
}


function getUrlParam(k) {//获取地址栏参数，k为键名
    var m = new RegExp("(^|&)" + k + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(m);
    if (r != null) return decodeURI(r[2]); return null;
}