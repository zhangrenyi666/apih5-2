window.configs = {
    dev: false,
    debugConsole: false,
    // domain: "http://weixin.fheb.cn:8080/woa/",
    //domain: "http://47.96.150.231/apiyys10fb/",
    // domain: "http://dltest.apih5.com/web/",
    // domain: " http://sx.apih5.com:8082/sxoa/",
    domain: "http://192.168.1.223:8080/web/",
    // domain: "https://sjz.apih5.com/apisjzoa/",
    // domain: "http://wyongan.xicp.net/", 
    appInfo: {
        //id:'yys10fb_qyh_app_id',
         id: "zz_app_id",//zj_fwh_woa_zl_id sjz_qyh_woa_id  zj_qyh_woa_id
        // id: "sx_qyh_woa_id",//zj_fwh_woa_zl_id zj_qyh_woa_id
        title: "xxx公司",
        copyright: "@xxx公司",
        name: "sjzoa",
        loginType: "1",//1:普通账号密码；2：手机验证码快捷登录；3:企业号授权；4:服务号授权；5、扫码登录
        mainModule: "/hwnav/",
        helpHref: "",
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
            'getLocation',
            'openLocation'
        ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    },
    apis: {
        login: "user/login",//登录
        getSysUserListByBg: "user/getSysUserListByBg",
        addSysUserInfoByBg: "user/addSysUserInfoByBg",
        updateSysUserInfoByBg: "user/updateSysUserInfoByBg",
        deleteSysUserInfoByBg: "user/deleteSysUserInfoByBg",
        upload: "upload",//上传接口
        getCorpInfo: "getCorpInfo",//获取公司信息
        getRouteData: "getRouteData",//获取路由数据
        getSysDepartmentUserAllTree: "getSysDepartmentUserAllTreeByZj",//获取通讯录树结构
        addSysDepartment: "addSysDepartment",//新增部门
       
        getZjHwModuleList:'getZjHwModuleList', //----模块首页列表获取
        getZjHwModuleDetailByModuleId:'getZjHwModuleDetailByModuleId', //----查询子详情

    },

}
