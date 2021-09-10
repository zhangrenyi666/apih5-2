$.support.cors = true;
var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串 
var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器 
window.w = {
    // domain: 'http://weixin.fheb.cn:8080/woa/',  
    domain: window.config.domain[window.config.model] ,//'http://192.168.1.107:8081/web/', //'http://192.168.1.107:8081/web/',
    index:window.config.provinceUrl[window.config.model], //'/index.html', //全国地图地址  http://weixin.fheb.cn:99/data-screen/index.html
    getApiUrl: function (apiName) {
        apiName = apiName || "";
        // if (!this.apiNames[apiName]) {
        //     alert(apiName + "尚未在mainjs中定义")
        // }
        // return this.domain + this.apiNames[apiName]
        return this.domain + apiName
    },
    ajax: function (apiName, params, success) {
        var params = params || {}; 
        $.support.cors = true;
        $.ajax({
            url: this.getApiUrl(apiName),
            type: 'post',
            dataType: 'json',
            data: params ? JSON.stringify(params) : '',
            contentType: "application/json; charset=utf-8",
            beforeSend: function (xhr) {
                xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
            },
            error: function (result, status, error) {//服务器响应失败处理函数 
                alert('错误:' + status);
                // console.log(error.code)
                // console.log(result)
            },
            success: function (result) {
                success(result.data, result.message, result.success);
            }
        })
    },
    get:function(apiName, params, success){
        var params = params; 
        $.support.cors = true;
        $.ajax({
            url: this.getApiUrl(apiName),
            type: 'get', 
            data: params, 
            beforeSend: function (xhr) {
                xhr.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
            },
            error: function (result, status, error) {//服务器响应失败处理函数 
                alert('错误:' + status); 
            },
            success: function (result) {
                success(result);
            }
        })
    },
    //判断当前浏览类型  
    //判断是否是IE浏览器   不是ie返回"-1"
    isIE: function () {
        var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串 
        var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器 
        if (isIE) {
            return "1";
        }
        else {
            return "-1";
        }
    },
    getUrlParam: function (k, windowObj) {//获取地址栏参数，k未键名
        var windowObj = windowObj || window
        var m = new RegExp("(^|&)" + k + "=([^&]*)(&|$)");
        var r = windowObj.location.search.substr(1).match(m);
        if (r != null) return decodeURI(r[2]); return null;
    }
}
