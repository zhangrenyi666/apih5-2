/*!
 * lny-mobile-main v0.5.0
 * create by lny on 2017-1-11.
 */
window.lnyConfig = {
    dev: false,//是否为开发，如果为true：1、不执行微信授权，使用testDatas；2、不启动jssdk；3、origin使用dev版本
    wxDebug: false,
    jsApiList: ['downloadVoice', 'downloadImage', 'startRecord', 'stopRecord', 'uploadVoice', 'playVoice', 'stopVoice', 'translateVoice', 'chooseImage', 'uploadImage', 'closeWindow'], // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    localStorage: {
        timeOut: 7 * 24 * 60 * 60 * 1000
    },
    domain: {
        api: "http://api.fheb.cn:8080/woa/",
        // api: "http://192.168.1.151:8080/",
        file: null,//上传文件类的主域名，如果为null，与prod或dev相同
    },
    testDatas: {
        userInfo: {
            userId: "wangzongming_test",
        }
    },
    appInfo: {
        // APPID: 'wxd44aa66e4b577b70',//中交服务号
        // APPID: 'wx324d746ab634abf3',//中交企业号
        APPID:'zj_qyh_weoa_id',//微钉
        SCOPE: "snsapi_base",
        // TYPE: "Fwh",//服务号
        TYPE: "Qyh",//企业号
    },
    apiDatas: {
        //-----------------type:0分割线-----------------
        getWeChatJSSdkConfig: {
            type: "0",
            name: "获取微信jssdk配置（企业号）",
            url: "getWeChatJSSdkConfig",
        },
        getFwhWeChatJSSdk: {
            type: "0",
            name: "获取微信jssdk配置（服务号）",
            url: "getFwhWeChatJSSdk",
        }, 
        login: {
            type: "0",
            name: "获取微信jssdk配置（服务号）",
            url: "user/login",
        },
        getCorpInfo: {
            type: "0",
            name: "获取getCorpInfo",
            url: "getCorpInfo",
        }
    }



}

