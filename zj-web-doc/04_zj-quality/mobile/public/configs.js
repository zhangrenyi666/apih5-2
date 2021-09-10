window.configs = {
    dev: false,
    // domain: "http://192.168.1.155:8080/",
    domain: "http://weixin.fheb.cn:8080/woa/",
    // domain: "http://wyongan.xicp.net/", 
    appInfo: {
        id: "zj_qyh_woa_id",//zj_fwh_woa_zl_id zj_qyh_woa_id
        title: "中交一公局",
        copyright: "@中交一公局",
        name: "zjquality",
        loginType: "1",//1:普通账号密码；2：手机验证码快捷登录；3:企业号授权；4:服务号授权；5、扫码登录
        mainModule: "/main/",
        helpHref: "http://weixin.fheb.cn:8088/?furl=http://weixin.fheb.cn:89/zjtz/upload/技术质量支撑平台手册.pptx",
    },
    storage: {
        timeout: 365 * 7 * 24 * 60 * 60 * 1000
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
        getOtherOrgMemberList: "getOtherOrgMemberList",//获取可选人员列表
        addEnquiry: "addEnquiry",//新增问询
        getStateCountList: "getStateCountList",//获取各状态个数
        getUserStateList: "getUserStateList",//获取问询列表
        getEnquiryDetail: "getEnquiryDetail",//获取问询详情
        addAnswer: "addAnswer",//新增回答
        endEnquiry: "endEnquiry",//结束问询
        getFirstLevelList: "getFirstLevelList",//获取资料1级筛选项列表
        getSecondLevelList: "getSecondLevelList",//获取资料2级筛选项列表
        getInformationList: "getInformationList",//获取资料列表
        getInformationDetail: "getInformationDetail",//获取资料详情
        getSpecLevelList: "getSpecLevelList",//获取规范筛选项列表
        getSpecHomePage: "getSpecHomePage",//获取规范列表
        getSpecDetailInsertHistory: "getSpecDetailInsertHistory",//记录查看规范历史
        getDepartmentListByUserKey: "getDepartmentListByUserKey",//获取部门人员管理的部门列表
        getMemberListByDepartmentId: "getMemberListByDepartmentId",//获取部门人员管理的人员列表
        updateMemberIsLeader: "updateMemberIsLeader",//修改部门人员总工身份
        createZjBaseQrcode: "createZjBaseQrcode",//创建部门服务号二维码
        toRegisterZjSysUser: "toRegisterZjSysUser",//注册修改服务号人员基本信息
        batchDeleteByDepIdAndUserKey:"batchDeleteByDepIdAndUserKey"//移除人员
    }
}
