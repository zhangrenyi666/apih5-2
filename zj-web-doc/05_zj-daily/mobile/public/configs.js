window.configs = {
    dev: false,
    // domain: "http://192.168.1.110:8080/",
    // domain: "http://api.fheb.cn:8080/woa/",
    // domain: "http://wyongan.xicp.net/", 
    domain: "http://weixin.fheb.cn:99/apiwoa/",  
    appInfo: {
        id: "zj_qyh_woa_id",//zj_fwh_woa_zl_id zj_qyh_woa_id
        title: "中交一公局",
        copyright: "@中交一公局",
        name: "zjdaily",
        loginType: "1",//1:普通账号密码；2：手机验证码快捷登录；3:企业号授权；4:服务号授权；5、扫码登录
        mainModule: "/main/",
        helpHref: "http://weixin.fheb.cn:8088/?furl=http://weixin.fheb.cn:89/zjtz/upload/技术质量支撑平台手册.pptx",
    },
    storage: {
        timeout: 7 * 24 * 60 * 60 * 1000
    },
    wxSdk: {
        enabled: false,
        debug: false,
        jsApiList: [
            'downloadVoice',
            'downloadImage',
            'startRecord',
            'stopRecord',
            'uploadVoice',
            'playVoice',
            'stopVoice',
            'translateVoice',
            'chooseImage',
            'uploadImage',
            'closeWindow',
            'getLocation'
        ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    },
    apis: {
        login: "user/login",//登录
        upload: "upload",//上传接口
        getCorpInfo: "getCorpInfo",//获取公司信息
        getRouteData: "getRouteData",//获取路由数据
        getInvestCompanyListByMsg: "getInvestCompanyListByMsg",
        getInvestCompanyInfo: "getInvestCompanyInfo",
        getDailyFiveNumbers: "getDailyFiveNumbers",
        getTzLandList: "getTzLandList2",
        getTzDesignChartList: "getTzDesignChartList2",
        getTzProjectFundsList: "getTzProjectFundsList2",
        getTzAssessmentList: "getTzAssessmentList2",
        getTzGraphicProgressList: "getTzGraphicProgressList2",
        sendDailyMsg: "sendDailyMsg",
        getMessageMemberList: "getMessageMemberList"
    }
}
