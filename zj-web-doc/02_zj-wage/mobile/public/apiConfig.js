/*!
 * lny-mobile-main v0.5.0
 * create by lny on 2017-1-11.
 */
const lnyConfig = (window.lnyConfig = {
    dev: true, //是否为开发，如果为true：1、不执行微信授权，使用testDatas；2、不启动jssdk；3、origin使用dev版本
    wxDebug: false,
    jsApiList: [
        "downloadVoice",
        "downloadImage",
        "startRecord",
        "stopRecord",
        "uploadVoice",
        "playVoice",
        "stopVoice",
        "translateVoice",
        "chooseImage",
        "uploadImage",
        "closeWindow"
    ], // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    localStorage: {
        timeOut: 7 * 24 * 60 * 60 * 1000
    },
    domain: {
        // api: "http://weixin.fheb.cn:8080/woa/",
        api: "http://weixin.fheb.cn:99/apiwoa/",
        // api: "http://192.168.1.115:8080/",
        // api: "http://wyongan.xicp.net/",
        file: null //上传文件类的主域名，如果为null，与prod或dev相同
    },
    testDatas: {
        userInfo: {
            // userId: "liningyuan_test1",
            userId: "18842637839"
        }
    },
    appInfo: {
        // APPID: 'wxd44aa66e4b577b70',//中交服务号
        // APPID: 'wx324d746ab634abf3',//中交企业号
        // APPID: "zj_qyh_weoa_id", //微钉
        APPID: "zj_qyh_woa_id",
        // TYPE: "Fwh",//服务号
        TYPE: "Qyh" //企业号
    },
    apiDatas: {
        //-----------------type:0分割线-----------------
        getWeChatJSSdkConfig: {
            type: "0",
            name: "获取微信jssdk配置（企业号）",
            url: "getWeChatJSSdkConfig"
        },
        getFwhWeChatJSSdk: {
            type: "0",
            name: "获取微信jssdk配置（服务号）",
            url: "getFwhWeChatJSSdk"
        },
        getWeChatUserInfoByCode: {
            type: "0",
            name: "获取用户id（企业号）",
            url: "getWeChatUserInfoByCode"
        },
        getWeChatFwhUserInfoByCode: {
            type: "0",
            name: "获取用户id（服务号）",
            url: "getWeChatFwhUserInfoByCode"
        },
        //-----------------type:1（userId相关）分割线-----------------
        batchDeleteZjWage: {
            type: "1",
            name: "阅后即焚-删除用的",
            url: "batchDeleteZjWage"
        },
        getZjWageDetail: {
            type: "1",
            name: "获取详情数据",
            url: "getZjWageDetail"
        },
        login: {
            type: "0",
            name: "获取微信jssdk配置（服务号）",
            url: "user/login"
        },
        getCorpInfo: {
            type: "0",
            name: "获取getCorpInfo",
            url: "getCorpInfo"
        },
        getZjPerformDetail: {
            type: "0",
            name: "获取绩效详情",
            url: "getZjPerformDetail"
        }
        //-----------------type:2分割线-----------------
        //指导资料，规范，施工手册
    }
});
