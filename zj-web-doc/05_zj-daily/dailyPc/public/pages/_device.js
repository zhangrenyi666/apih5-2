//浏览器操作判断 
//需要在 config 中配置  pcPageUrl:"http://xxx/xxx",   否则不生效
if ((navigator.userAgent.match(/(MicroMessenger)/i))) {
    if (!navigator.userAgent.match(/(iPhone|iPod|ios|iPad|Android)/i)) {
        //pc需要跳转页面
        if (window.configs.pcPageUrl) {
            var curUrl = window.location.origin + window.location.pathname;
            var exg = new RegExp(curUrl,'ig');
            if (window.configs.pcPageUrl && !exg.test(window.configs.pcPageUrl)) {
                window.location.href = window.configs.pcPageUrl;
            }
        }
    }
}