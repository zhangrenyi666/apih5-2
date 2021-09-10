window.configs = {
    dev: false,
    debugConsole: false, 
	domain: "http://192.168.1.110:8080/web/",
	// domain: "http://192.168.1.118:8080/web/",
    // domain: "http://192.168.1.119/apizjsjjb/",
    // domain: "http://111.207.117.69/apijb/",
    // domain: "http://jb.ccfourth.com/apijb/",
    
    appInfo: {
        logo: "",
        // id: "zj_sj_jb", 
        id:"zj_sj_jb_ht",
        title: "中交四公局举报系统",
        copyright: "中交四公局举报系统",
        name: "sjzoa",
        loginType: "1", //1:普通账号密码；2：手机验证码快捷登录；3:企业号授权（也可以在app登录）；4:服务号授权；5、扫码登录  6、app登录  31、会弹出授权窗口的授权
        mainModule: "/fwh/",
        helpHref: ""
    },
    storage: {
        timeout: 7 * 24 * 60 * 60 * 1000
    },
    wxSdk: {
        enabled: false,
        debug: false,
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
            "closeWindow",
            "getLocation",
            "openLocation",
            "onVoiceRecordEnd",
            "hideOptionMenu"
        ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    },
    apis: {
        login: "user/login", //登录
        refreshAccessToken: "user/refreshAccessToken", //刷新token 
        getSysUserListByBg: "user/getSysUserListByBg",
        addSysUserInfoByBg: "user/addSysUserInfoByBg",
        updateSysUserInfoByBg: "user/updateSysUserInfoByBg",
        deleteSysUserInfoByBg: "user/deleteSysUserInfoByBg",
        upload: "upload", //上传接口
        getSysDepartmentAllTree: "getSysDepartmentUserAllTree?noPersonnel=1", //通讯录-部门获取
        getSysDepartmentUserAllTree: "getSysDepartmentUserAllTreeBy",//获取通讯录树结构 zj
        appGetSysFrequentContactsList:"appGetSysFrequentContactsList", //获取收藏的联系人
        appAddSysFrequentContacts:"appAddSysFrequentContacts", //添加联系人到收藏
        appRemoveSysFrequentContacts:"appRemoveSysFrequentContacts", //移除收藏联系人
        getCorpInfo: "getCorpInfo", //获取公司信息
        getRouteData: "getRouteData", //获取路由数据
        addSysDepartment: "addSysDepartment", //新增部门
        updateSysDepartment: "updateSysDepartment", //修改部门信息
        batchDeleteUpdateSysDepartment: "batchDeleteUpdateSysDepartment", //批量删除部门
        moveUpdateSysDepartment: "moveUpdateSysDepartment", //移动部门顺序
        syncWeChatToSysInfo: "syncWeChatToSysInfo", //同步微信通讯录数据
        getSysMenuAllTree: "getSysMenuAllTree", //获取菜单树结构
        addSysMenu: "addSysMenu", //新增菜单
        updateSysMenu: "updateSysMenu", //修改菜单信息
        updateSysMenuDetails: "updateSysMenuDetails",
        batchDeleteUpdateSysMenu: "batchDeleteUpdateSysMenu", //批量删除菜单
        moveUpdateSysMenu: "moveUpdateSysMenu", //移动菜单顺序
        getSysMenu: "getSysMenu", //获取菜单详情
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
        getZxQrcodeQrcodeList: "getZxQrcodeQrcodeList",
        getZxHwHomeMobilIndex: "getZxHwHomeMobilIndex",
        getIndexviewDetails: "getIndexviewDetails",
        
        getZjHomePageData:"getZjHomePageData",//首页信息
        getNearlyWeekChart:"getNearlyWeekChart",//曝光页面统计图
        getZjHandlingSituationData:"getZjHandlingSituationData",//解决列表
        getZjProjectLevelList:"getZjProjectLevelList",//所属项目
        addZjTechnicalQualityReportInfo:"addZjTechnicalQualityReportInfo",//添加技术质量举报信息
        getZjJszlDetails:"getZjJszlDetails",
        getZjAqyhDetails:"getZjAqyhDetails",
        getZjWgwjDetails:"getZjWgwjDetails",
        getZjOperationRecordList:"getZjOperationRecordList",//进度条
        addZjViolationReportInfo:"addZjViolationReportInfo",//添加违规违纪举报信息
        addZjHiddenDangerReportInfo:"addZjHiddenDangerReportInfo",//添加安全隐患
        addZjProblemFeedback:"addZjProblemFeedback",//添加服务信息
        getZjServiceTypeList:"getZjServiceTypeList",//服务类型
        getPersonalInfoDetails:"getPersonalInfoDetails",//个人中心
        getZjTechnicalQualityReportInfoList:"getZjTechnicalQualityReportInfoList",//技术质量举报
        getZjJszlDetails:"getZjJszlDetails",//技术质量举报详情
        getZjHiddenDangerReportInfoList:"getZjHiddenDangerReportInfoList",////安全隐患举报
        getZjAqyhDetails:"getZjAqyhDetails",//安全隐患举报详情
        getZjViolationReportInfoList:"getZjViolationReportInfoList",//违规违纪举报
        getZjWgwjDetails:"getZjWgwjDetails",//违规违纪举报详情
        getZjServiceTypeList:"getZjServiceTypeList",//获取服务类型
        getZjFeedbackProblemList:"getZjFeedbackProblemList",//服务列表
        getZjProblemFeedbackDetails:"getZjProblemFeedbackDetails",//服务详情
        addZjFeedbackRecovery:"addZjFeedbackRecovery",//添加回复内容
        getBonusList:"getBonusList",//奖金列表
        getTypicalExposureListByMove:"getTypicalExposureListByMove",//典型曝光
        getReportingList:"getReportingList",//举报获奖
        getContributionList:"getContributionList",//投稿获奖
        getZjTechnicalQualityReportInfoList:"getZjTechnicalQualityReportInfoList",//技术质量
        getZjHiddenDangerReportInfoList:"getZjHiddenDangerReportInfoList",//安全环保
        getBaseCodeSelect: "getBaseCodeSelect", //普通下拉
        getZjUseNoticeDetails:"getZjUseNoticeDetails",
        zjSendMsgCode:"zjSendMsgCode",//短信验证
    }
};
