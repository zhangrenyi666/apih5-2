/*!
 * lny-mobile-main v0.5.0
 * create by lny on 2017-12-27.
 */
const lnyConfig = window.lnyConfig = {
    dev: false,//是否为开发，如果为true：1、不执行微信授权，使用testDatas；2、不启动jssdk；3、origin使用dev版本
    wxDebug: false,
    jsApiList: ['downloadVoice', 'downloadImage', 'startRecord', 'stopRecord', 'uploadVoice', 'playVoice', 'stopVoice', 'translateVoice', 'chooseImage', 'uploadImage', 'closeWindow'], // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    
    localStorage: {
        timeOut: 300000
    }, 
    domain: {
        // api: "http://192.168.1.112:8080/",
        api: "http://api.fheb.cn:8080/woa/",
        file: null,//上传文件类的主域名，如果为null，与prod或dev相同
    },
    testDatas: {
        userInfo: {
            userId: "liningyuan_test1",
        }
    },
    appInfo: {
        APPID:'zj_qyh_weoa_id',//中交
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
        getWeChatUserInfoByCode: {
            type: "0",
            name: "获取用户id（企业号）",
            url: "getWeChatUserInfoByCode",
        },
        getWeChatFwhUserInfoByCode: {
            type: "0",
            name: "获取用户id（服务号）",
            url: "getWeChatFwhUserInfoByCode",
        },
        //-----------------type:1（userId相关）分割线-----------------
        //-----------------type:2（业务相关）分割线-----------------
        getZjInvoiceList: {
            type: "2",
            name: "获取发票抬头列表",
            url: "getZjInvoiceList",
        },
        getInvoiceDetails: {
            type: "2",
            name: "获取发票抬头详情",
            url: "getInvoiceDetails",
        },
        getCorpInfo:{
            type: "0",
            name: "获取getCorpInfo",
            url: "getCorpInfo",
        },
        login: {
            type: "0",
            name: "获取微信jssdk配置（服务号）",
            url: "user/login",
        }
    }
}

