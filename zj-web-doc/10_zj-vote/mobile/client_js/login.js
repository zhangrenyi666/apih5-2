/*
登录文件 
在引用前需要先将 jquery.js && layer.js 引入 
*/

//全局变量
var globalVar = {
    //api
    // api:'http://weixin.fheb.cn:8080/woa/'
    api: "http://wechat.zjyjhw.com/apihaiwei/",
    // api: "http://test.apih5.com:9091/web/",
    //app
    app: {
        id: 'zj_qyh_woa_id', //zj_qyh_woa_id  zj_qyh_woa_id_test
        type: '3'
    },
    //登录需要请求的接口
    apiName: {
        getCorpInfo: "getCorpInfo",
        login: "user/login",
    },

    //登录状态   0未登录  1已登录
    loginStatus: '0',

    //token有效期
    tokenValidity: 7 * 24 * 60 * 60 * 1000  //7天
}

//所有的方法
var fnObj = {
    //本文件的ajax
    getUrlParam: function (k) {//获取地址栏参数，k未键名
        var m = new RegExp("(^|&)" + k + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(m);
        if (r != null) return decodeURI(r[2]); return null;
    },
    myAjax: function (apiName,params,success,errorFn) {
        //loading层
        //loading带文字 

        var _load = layer.open({
            type: 2
        });
        $.ajax({
            url: globalVar.api + apiName,
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(params),
            contentType: "application/json; charset=utf-8",
            beforeSend: function (xhr) {
                xhr.setRequestHeader('X-Requested-With','XMLHttpRequest');
                // 新增携带token请求
                var token = window.getUserInfo().token
                xhr.setRequestHeader('token',token);//
            },
            complete: function (result) {
                layer.close(_load)
            },
            error: function (result,status,error) {//服务器响应失败处理函数 
                layer.open({
                    content: error
                    ,btn: '我知道了'
                });
                if (errorFn) {
                    errorFn()
                }
            },
            success: function (result) { 
                if (result.success) {
                    success(result);
                } else {

                    if (result.code == '3003' || result.code == '3004') {
                        //重新获取code登录
                        window.reset();
                    } else if (result.code === "3007") { 
                        window.myAjax("user/refreshAccessToken",{ token: window.getUserInfo().token },function (data) {

                            var result = {
                                success: data.success,
                                message: data.message,
                                data: data.data,
                            }
                            if (result.success) {
                                //重新设置token

                                //保存用户数据
                                var userInfo = {
                                    token: result.data || '',
                                    userInfo: window.getUserInfo().userInfo,
                                    time: new Date().getTime(),
                                }
                                localStorage.removeItem('userInfo');
                                localStorage.setItem('userInfo',JSON.stringify(userInfo));

                                window.myAjax(apiName,params,function (data) {
                                    if (data.success) {
                                        success(data);
                                    } else {
                                        layer.open({
                                            content: result.message
                                            ,btn: '我知道了'
                                        });
                                    }
                                })
                            } else { 
                                layer.open({
                                    content: result.message
                                    ,btn: '我知道了'
                                });
                            }
                        })

                    } else {
                        layer.open({
                            content: result.message
                            ,btn: '我知道了'
                        });
                    }
                }
            }
        })
    },
    //获取登录状态
    getUserInfo: function () {
        var _u = window.localStorage.getItem('userInfo');
        if (_u) {
            return JSON.parse(_u);
        } else {
            return {}
        }
    },
    clearUserInfo: function () {
        window.localStorage.removeItem('userInfo');
    },

    //重置本地数据和地址栏数据
    reset: function () {
        //先清除本地数据
        fnObj.clearUserInfo();
        //重新设置网址
        setTimeout(function () {
            var hash = window.location.hash;
            var origin = window.location.origin;
            var pathname = window.location.pathname;
            var search = window.location.search;
            var _haveCode = search.search(/(code=)/gi);
            var _strNewB;

            if (_haveCode !== -1) {
                console.log('需要过滤掉code')
                //有code需要把code去掉
                var _a = search && search.replace('?','');
                var _b = _a && _a.split('&');
                var _newB = [];
                if (!_b) { //防止错误
                    console.log('no _b 不进行跳转')
                    return;
                }
                for (var i = 0; i < _b.length; i++) {
                    if (_b[i].search(/(code=)/gi) == -1) {
                        _newB.push(_b[i]);
                    }
                }
                //将保存下来的search参数又放回网址上去，除了code
                _strNewB = _newB.join('&');//var 
                if (_strNewB) {
                    window.location.href = origin + pathname + _strNewB + '#' + hash;
                } else {
                    window.location.href = origin + pathname + '#' + hash;
                }

            } else {
                console.log('不需要过滤code')
                window.location.href = origin + pathname + search + '#' + hash;
            }



        },200)
    },

    //设置url参数 重复的将会被替换
    setUrl: function (params) {
        //params是一段string  eg: code = '8888'; 
        var hash = window.location.hash;
        var origin = window.location.origin;
        var pathname = window.location.pathname;
        var search = window.location.search + '&' + params;
        console.log('登录成功重置url search:',search)
        window.location.href = origin + pathname + search + '#' + hash;
    },

    //设置登录状态
    setLoginStateus: function (cb) {
        // 1判断有没有缓存数据（有缓存数据且没有过期将不用登录）
        // 2如果需要重新登录  先判断有么有code然后在操作
        var _locUserInfo = fnObj.getUserInfo();

        var _token = _locUserInfo.token;
        var _time = _locUserInfo.time,nowTime = new Date().getTime();
        if (_token && (nowTime - _time < globalVar.tokenValidity)) {
            //当token存在并且保质期没过将走这  
            //设置浏览器地址code值为token 
            // console.log('登录状态中...');
            if (cb) {
                cb()
            }

        } else {
            //需要登录
            //判断地址栏有么有code
            var haveCode = /(code=)/ig;
            var winUrl = window.location.href;
            if (haveCode.test(winUrl)) {
                //已经有code了 需要去登录
                const _body = {
                    loginType: globalVar.app.type,
                    accountId: globalVar.app.id,
                    code: fnObj.getUrlParam('code'),
                }
                fnObj.myAjax(globalVar.apiName.login,_body,function (result) {
                    if (result.code === '3003' || result.code === '3004') {
                        //重新获取code登录   实际上这一步不会出现 3003 3004
                        fnObj.reset();
                    } else if (result.success) {
                        var data = result.data;
                        //保存用户数据
                        var userInfo = {
                            token: data.token || '',
                            userInfo: data.userInfo,
                            time: new Date().getTime(),
                        }
                        localStorage.removeItem('userInfo');
                        localStorage.setItem('userInfo',JSON.stringify(userInfo));


                        fnObj.setUrl('')

                        if (cb) {
                            cb()
                        }
                    }
                })
            } else {
                //没有code 需要获取code
                //先重置本地数据
                console.log('需要登录 先重置本地数据 需要清除浏览器网址code')
                fnObj.reset();

                var _body = {
                    jssdkUrl: "",
                    domianUrl: window.location.href,
                    accountId: globalVar.app.id,
                    state: window.location.hash
                }

                fnObj.myAjax(globalVar.apiName.getCorpInfo,_body,function (result) {
                    var uri = result.data.uri;
                    window.location.href = uri;
                })
            }
        }

    },
    //登录
    login: function (cb) {
        //设置信息 登录完后有回调
        fnObj.setLoginStateus(cb);
    }
}

//执行登录 
window.getUserInfo = fnObj.getUserInfo;
window.login = fnObj.login;
window.myAjax = fnObj.myAjax;
window.reset = fnObj.reset;//重新登录
window.api = globalVar.api;//全局api
window.getUrlParam = fnObj.getUrlParam;
