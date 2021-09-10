window.configs = {
    dev: false,
    debugConsole: false,
    // domain:"http://bur07iflue.51http.tech/web/",
    // domain: "http://192.168.1.118:8080/web/",
    // domain: "http://47.96.150.231/apitongren/",
    // domain: " http://sx.apih5.com:8082/sxoa/",
    domain: "http://192.168.1.123:8080/web/",
    // domain: "https://sjz.apih5.com/apisjzoa/",
    // domain:'http://weixin.fheb.cn:99/apiwoa/',
    // domain: "http://wyongan.xicp.net/", 
    appInfo: {
        // id: "tongren_qyh_app_id",//zj_fwh_woa_zl_id zj_qyh_woa_id
        id: "zj_qyh_woa_id",//zj_fwh_woa_zl_id zj_qyh_woa_id
        title: "",
        copyright: "@xxx公司",
        name: "sjzoa",
        loginType: "1",//1:普通账号密码；2：手机验证码快捷登录；3:企业号授权；4:服务号授权；5、扫码登录
        // loginType: "1",//1:普通账号密码；2：手机验证码快捷登录；3:企业号授权；4:服务号授权；5、扫码登录
        mainModule: "/zjMeetingRoomMobile/",
    },
    canChangeProject: false,
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
        refreshAccessToken: "user/refreshAccessToken", //刷新token 
        getSysUserListByBg: "user/getSysUserListByBg",
        addSysUserInfoByBg: "user/addSysUserInfoByBg",
        updateSysUserInfoByBg: "user/updateSysUserInfoByBg",
        deleteSysUserInfoByBg: "user/deleteSysUserInfoByBg",
        upload: "upload",//上传接口
        getCorpInfo: "getCorpInfo",//获取公司信息
        getRouteData: "getRouteData",//获取路由数据
        getSysDepartmentUserAllTree: "getSysDepartmentUserAllTree",//获取通讯录树结构
        addSysDepartment: "addSysDepartment",//新增部门
        updateSysDepartment: "updateSysDepartment",//修改部门信息
        batchDeleteUpdateSysDepartment: "batchDeleteUpdateSysDepartment",//批量删除部门
        moveUpdateSysDepartment: "moveUpdateSysDepartment",//移动部门顺序
        syncWeChatToSysInfo: "syncWeChatToSysInfo",//同步微信通讯录数据
        getSysMenuAllTree: "getSysMenuAllTree",//获取菜单树结构
        addSysMenu: "addSysMenu",//新增菜单
        updateSysMenu: "updateSysMenu",//修改菜单信息
        updateSysMenuDetails: "updateSysMenuDetails",
        batchDeleteUpdateSysMenu: "batchDeleteUpdateSysMenu",//批量删除菜单
        moveUpdateSysMenu: "moveUpdateSysMenu",//移动菜单顺序
        getSysMenu: "getSysMenu",//获取菜单详情
        getSysRoleAllTree: "getSysRoleAllTree",
        addSysRole: "addSysRole",
        updateSysRole: "updateSysRole",
        batchDeleteUpdateSysRole: "batchDeleteUpdateSysRole",
        getSysRoleUserList: "getSysRoleUserList",
        updateSysRoleUser: "updateSysRoleUser",
        getSysRoleMenuList: "getSysRoleMenuList",
        updateSysRoleMenu: "updateSysRoleMenu",
        createOpenFlow: "createOpenFlow",
        openFlow: "openFlow",
        actionFlow: "actionFlow",
        getTodoList: "getTodoList",
        getHasTodoList: "getHasTodoList",
        getZxQrcodeQrcodeList: 'getZxQrcodeQrcodeList',
        getZxHwHomeMobilIndex: "getZxHwHomeMobilIndex",
        getIndexviewDetails: "getIndexviewDetails",


        //测试使用
        getSxDocumentInfoList: 'getSxDocumentInfoList',
        getSysDepartmentList:'getSysDepartmentList',

        zjMeetingLoginForWechat:"zjMeetingLoginForWechat",//小型会议登录
        getZjMeetingDetailForWechat:"getZjMeetingDetailForWechat",//会议详情
        getZjMeetingDetailAddTokenForWechat:"getZjMeetingDetailAddTokenForWechat",//验证token会议详情
        getZjMeetingReceiptDetailAddTokenForWechat:"getZjMeetingReceiptDetailAddTokenForWechat",
        zjMeetingConfirmationForWechat:"zjMeetingConfirmationForWechat",//会议确认
        getZjMeetingListForWechat:"getZjMeetingListForWechat",//个人参会列表
        zjMeetingSingInForWechat:"zjMeetingSingInForWechat",//会议签到
        zjLargeMeetingSignUpForWechat:"zjLargeMeetingSignUpForWechat", //会议报名
        getZjMeetingDetailAddTokenNoEntryForWechat:"getZjMeetingDetailAddTokenNoEntryForWechat",//内部报名
        getZjMeetingRoomSituationListByDate:"getZjMeetingRoomSituationListByDate",//会议室查询
        getMeetingRoomAllList:"getMeetingRoomAllList",//会议室下拉
        getZjMeetingRoomList:"getZjMeetingRoomList",//会议室名称

        getZjXmHasTitleQuestionList:"getZjXmHasTitleQuestionList",//列表
        getZjXmQuestionTitleSelectAllList:"getZjXmQuestionTitleSelectAllList",//下拉
        zjInsideMeetingSingInForWechat:"zjInsideMeetingSingInForWechat",
        
        getZjMeetingPlanFallInInfoListForWechat:"getZjMeetingPlanFallInInfoListForWechat",//年会议审批列表
        getZjMeetingPlanFallInInfoDetailByDeptId:"getZjMeetingPlanFallInInfoDetailByDeptId",//详情
        zjMeetingPlanSubmitApproval:"zjMeetingPlanSubmitApproval",//审批
        getZjMeetingPlanFallInListFallInYear:"getZjMeetingPlanFallInListFallInYear",//详细信息
    },
}
