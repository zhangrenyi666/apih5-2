
window.configs = {
    dev: false,
    debugConsole: false,
    theme: "basic",

    // domain: "http://localhost:8086/web/",
    domain: "http://test.apih5.com:9094/web/",//王泽华
    // domain: "http://tz.fheb.cn:88/apiZjtz/",//测试地址
    // domain:"http://zj.bjzxkj.com/zjtz/#/zjtz/"
    // domain: "http://192.168.1.119:8080/web/",
    appInfo: {
        ureport: 'http://tz.fheb.cn:88/ureport/',
        // ureport: 'http://192.168.1.135:89/ureport/',
        logo: "http://weixin.fheb.cn:99/icon/logo.png",
        // id: 'zj_qyh_tz_id',//张仁一
        id: 'zj_tz_id',//王思阳
        title: "投资管理平台-本地",
        leftTopTitle: [{
            text: "中交一公局集团",
            style: { textAlign: "center", fontSize: "15px" }
        },
        {
            text: "投资管理平台",
            style: { textAlign: "center", fontSize: "16px" }
        }],
        copyright: "中交一公局集团",
        name: "",
        loginType: "1", // 1:普通账号密码；2：手机验证码快捷登录；3:企业号授权（也可以在app登录）；4:服务号授权；5、扫码登录  6、app登录  31、会弹出授权窗口的授权
        mainModule: "/zjtz/",
        helpHref: ""
    },
    loginFormDataEncrypt: false,
    //百度编辑器cdn地址 默认就是 下面 地址
    // ueCdn: "http://cdn.apih5.com/lib/react-ueditor/vendor/ueditor/",
    // ueCdn:"http://localhost:8000/",

    //登录页面轮播背景配置 配置后注意文件夹/src/view/login/img下是否存在对应的图片
    //地址也可以直接引用http://xxx || https://xxx
    loginPageConfig: {
        bgImgUrl: ["img1.png", "img2.png"], //背景图片
        rowText: ["中交一公局集团", "投资管理平台"] //文字描述 一项即一行
    },
    //是否可以切换项目 默认true
    // canChangeProject: true,

    storage: {
        timeout: 7 * 24 * 60 * 60 * 1000
    },
    wxSdk: {
        enabled: true,
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
            "onVoiceRecordEnd"
        ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    },
    apis: {
        // files:'http://10.11.59.56:1080/files',
        files:'http://192.168.1.13:1080/files',
        "unLock": "user/unLock", // 解锁用户
        openFlowHas: "openFlow?hasFlag=1", // 已办流程需要加上这个参数
        /* ----- ↓↓↓  通用接口开始 ↓↓↓ --------*/
        login: "user/login", //登录
        editPwd: "user/updateUserPwd", //改密
        resetPwd: "user/updateUserDefaultPwd", //强制个人重置密码接口
        resetUserPwd: "user/resetUserPwd", //重置密码 
        refreshAccessToken: "user/refreshAccessToken", //刷新token 
        upload: "upload", //上传接口
        getCorpInfo: "getCorpInfo", //获取公司信息
        getRouteData: "getRouteData", //获取路由数据
        getCaptchaCode: "getCaptchaCode",//获取登录验证码
        getSysWebBgDetails: "getSysWebBgDetails",//获取接口
        updateSysWebBg: "updateSysWebBg",

        // 组织机构-部门
        addSysDepartment: "addSysDepartment", //新增部门
        updateSysDepartment: "updateSysDepartment", //修改部门信息
        batchDeleteUpdateSysDepartment: "batchDeleteUpdateSysDepartment", //批量删除部门
        moveUpdateSysDepartment: "moveUpdateSysDepartment", //移动部门顺序

        // 组织机构-人员
        getSysUserListByBg: "user/getSysUserListByBg", // 后台人员-获取
        addSysUserInfoByBg: "user/addSysUserInfoByBg", // 后台人员-增加
        updateSysUserInfoByBg: "user/updateSysUserInfoByBg", // 后台人员-修改
        deleteSysUserInfoByBg: "user/deleteSysUserInfoByBg", // 后台人员-删除


        // 弹出框-组织机构人员
        getSysDepartmentTree: "getSysDepartmentTree",       // 组织机构-部门-获取
        getSysDepartmentUserAllTree: "getSysUserTree",         // 组织机构-部门+人员-获取
        getSysDepartmentTree: "getSysDepartmentTree",
        getSysUserTree: "getSysUserTree",
        getSysUserCurrentTree:"getSysUserCurrentTree",
        getSysDepartmentCurrentTree:"getSysDepartmentCurrentTree",

        // 收藏人员
        appGetSysFrequentContactsList: "appGetSysFrequentContactsList", //获取收藏的联系人
        appAddSysFrequentContacts: "appAddSysFrequentContacts",         //添加联系人到收藏
        appRemoveSysFrequentContacts: "appRemoveSysFrequentContacts",   //移除收藏联系人
        //后台-菜单相关
        getSysMenuAllTree: "getSysMenuAllTree", //获取菜单树结构
        addSysMenu: "addSysMenu", //新增菜单
        updateSysMenu: "updateSysMenu", //修改菜单信息
        updateSysMenuDetails: "updateSysMenuDetails",
        batchDeleteUpdateSysMenu: "batchDeleteUpdateSysMenu", //批量删除菜单
        moveUpdateSysMenu: "moveUpdateSysMenu", //移动菜单顺序
        getSysMenu: "getSysMenu", //获取菜单详情
        //后台-权限相关
        getSysRoleAllTree: "getSysRoleAllTree", // 权限
        addSysRole: "addSysRole", // 权限
        updateSysRole: "updateSysRole", // 权限
        batchDeleteUpdateSysRole: "batchDeleteUpdateSysRole", // 权限
        getSysRoleUserList: "getSysRoleUserList", // 权限
        updateSysRoleUser: "updateSysRoleUser", // 权限
        getSysRoleMenuList: "getSysRoleMenuList", // 权限
        updateSysRoleMenu: "updateSysRoleMenu", // 权限
        //后台-流程设置相关
        getBaseFlowCodeList: "getBaseFlowCodeList", // 流程
        baseFlowCodeImport: "baseFlowCodeImport", // 流程
        updateBaseFlowCode: "updateBaseFlowCode",//流程-编辑9.27
        getSysRoleMenuListByRole: "getSysRoleMenuListByRole",
        batchDeleteUpdateBaseFlowCode: "batchDeleteUpdateBaseFlowCode", // 流程
        getBaseFlowSponsorChooserList: "getBaseFlowSponsorChooserList", // 流程
        getFlowNameSelectList: "getFlowNameSelectList", // 流程
        addBaseFlowSponsorChooserByList: "addBaseFlowSponsorChooserByList", // 流程
        batchDeleteUpdateBaseFlowSponsorChooser: "batchDeleteUpdateBaseFlowSponsorChooser", // 流程
        updateBaseFlowSponsorChooserByList: "updateBaseFlowSponsorChooserByList", // 流程
        getBaseFlowStartSettingListByFlow: "getBaseFlowStartSettingListByFlow", //获取流程追加的节点
        addBaseFlowStartSettingByFlow: "addBaseFlowStartSettingByFlow", //提交流程追加的节点
        //后台-流程相关
        createOpenFlow: "createOpenFlow",
        openFlow: "openFlow",
        actionFlow: "actionFlow",
        getTodoList: "getTodoList",
        getHasTodoList: "getHasTodoList",
        getFlowCount:"getFlowCount",
        //字典组织层级[通用]
        addBaseCode: "addBaseCode",
        updateBaseCode: "updateBaseCode",
        getBaseCodeList: "getBaseCodeList",
        batchDeleteUpdateBaseCode: "batchDeleteUpdateBaseCode",
        pcUpdateBaseCodeOnTree: "pcUpdateBaseCodeOnTree", //更新数据字典[在树结构上编辑]   
        pcExchangeBaseCode: "pcExchangeBaseCode",
        moveUpdateBaseCode: "moveUpdateBaseCode",
        getBaseCodeTree: "getBaseCodeTree", //联动下拉
        getBaseCodeSelect: "getBaseCodeSelect",
        //获取app首页
        getSysMobilIndex: "getSysMobilIndex",
        getSysMobilIndexByData: "getSysMobilIndexByData",
        getSysWoaAddFlowList: "getSysWoaAddFlowList",
        getSysWoaFlowSelectDetail: "getSysWoaFlowSelectDetail",
        //项目获取切换等接口
        getZxQrcodePermissionObjectListByProject: "getZxQrcodePermissionObjectListByProject",
        changeZxQrcodePermissionProject: "changeZxQrcodePermissionProject",
        getZtEndPermissionListByProject: "getZtEndPermissionListByProject",
        changeZtEndProjectManagement: "changeZtEndProjectManagement",
        //项目获取切换等接口
        changeCompany: "user/changeCompany", //切换公司
        getZxWtPermissionListByProject: "getZxWtPermissionListByProject",
        changeZxWtProject: 'changeZxWtProject',
        //系统用户组 
        getSysUserGroupList: "getSysUserGroupList",
        addSysUserGroup: "addSysUserGroup",
        batchDeleteUpdateSysUserGroup: "batchDeleteUpdateSysUserGroup",
        updateSysUserGroup: "updateSysUserGroup",
        // 其它
        syncWeChatToSysInfo: "syncWeChatToSysInfo", //同步微信通讯录数据
        getBaseCodeUIConfig: 'getBaseCodeUIConfig',//查询组织层级前台页面配置
        getZjWoaFlowSelectDetails: "getZjWoaFlowSelectDetails", //流程列表下拉
        //获取页面按钮
        getSysMenuBtn: "getSysMenuBtn",
        /* ----- ↑↑↑  通用接口结束 ↑↑↑ --------*/
        //国家政策
        getZjTzPolicyCountryList: "getZjTzPolicyCountryList",
        addZjTzPolicyCountry: "addZjTzPolicyCountry",
        batchDeleteUpdateZjTzPolicyCountry: "batchDeleteUpdateZjTzPolicyCountry",
        updateZjTzPolicyCountry: "updateZjTzPolicyCountry",
        updateZjTzPolicyCountryPush: "updateZjTzPolicyCountryPush",
        updateZjTzPolicyCountryHomeShow: "updateZjTzPolicyCountryHomeShow",
        batchExportZjTzPolicyCountryFile:"batchExportZjTzPolicyCountryFile",
        //政策回复
        getZjTzPolicyCountryReplyList: "getZjTzPolicyCountryReplyList",
        updateZjTzPolicyCountryReply: "updateZjTzPolicyCountryReply",
        addZjTzPolicyCountryReply: "addZjTzPolicyCountryReply",
        batchDeleteUpdateZjTzPolicyCountryReply: "batchDeleteUpdateZjTzPolicyCountryReply",
        getZjTzPolicyCountryReplyRecordList: "getZjTzPolicyCountryReplyRecordList",
        //地方政策
        getZjTzPolicyLocalList: "getZjTzPolicyLocalList",
        addZjTzPolicyLocal: "addZjTzPolicyLocal",
        updateZjTzPolicyLocal: "updateZjTzPolicyLocal",
        batchDeleteUpdateZjTzPolicyLocal: "batchDeleteUpdateZjTzPolicyLocal",
        updateZjTzPolicyLocalPush: "updateZjTzPolicyLocalPush",
        updateZjTzPolicyLocalHomeShow: "updateZjTzPolicyLocalHomeShow",
        batchDeleteReleaseZjTzPolicyLocal: "batchDeleteReleaseZjTzPolicyLocal",
        batchDeleteRecallZjTzPolicyLocal: "batchDeleteRecallZjTzPolicyLocal",
        batchExportZjTzPolicyLocalFile:"batchExportZjTzPolicyLocalFile",
        //政策回复
        getZjTzPolicyLocalReplyList: "getZjTzPolicyLocalReplyList",
        updateZjTzPolicyLocalReply: "updateZjTzPolicyLocalReply",
        addZjTzPolicyLocalReply: "addZjTzPolicyLocalReply",
        batchDeleteUpdateZjTzPolicyLocalReply: "batchDeleteUpdateZjTzPolicyLocalReply",
        getZjTzPolicyLocalReplyRecordList: "getZjTzPolicyLocalReplyRecordList",
        // 规章制度
        getZjTzRulesList: "getZjTzRulesList",
        getZjTzRulesDetails: "getZjTzRulesDetails",
        addZjTzRules: "addZjTzRules",
        updateZjTzRules: "updateZjTzRules",
        batchDeleteUpdateZjTzRules: "batchDeleteUpdateZjTzRules",
        // batchHomeShowZjTzRules: "batchHomeShowZjTzRules",
        toHomeShowZjTzRules: "toHomeShowZjTzRules",
        // 重要文件
        getZjTzImportDocList: "getZjTzImportDocList",
        getZjTzImportDocDetails: "getZjTzImportDocDetails",
        addZjTzImportDoc: "addZjTzImportDoc",
        updateZjTzImportDoc: "updateZjTzImportDoc",
        batchDeleteUpdateZjTzImportDoc: "batchDeleteUpdateZjTzImportDoc",
        // batchHomeShowZjTzImportDoc: "batchHomeShowZjTzImportDoc",
        toHomeShowZjTzImportDoc: "toHomeShowZjTzImportDoc",
        // 宣贯视频
        getZjTzVideoList: "getZjTzVideoList",
        getZjTzVideoDetails: "getZjTzVideoDetails",
        addZjTzVideo: "addZjTzVideo",
        updateZjTzVideo: "updateZjTzVideo",
        batchDeleteUpdateZjTzVideo: "batchDeleteUpdateZjTzVideo",
        // batchHomeShowZjTzVideo:"batchHomeShowZjTzVideo"
        toHomeShowZjTzVideo: "toHomeShowZjTzVideo",
        batchApproveAgreeZjTzVideo: "batchApproveAgreeZjTzVideo",
        batchApproveRejectZjTzVideo: "batchApproveRejectZjTzVideo",
        getZjTzVideoHistoryList: "getZjTzVideoHistoryList",
        addZjTzVideoHistory: "addZjTzVideoHistory",
        // 项目管理
        getZjTzProManageList: "getZjTzProManageList",
        addZjTzProManage: "addZjTzProManage",
        updateZjTzProManage: "updateZjTzProManage",
        batchDeleteUpdateZjTzProManage: "batchDeleteUpdateZjTzProManage",
        getZjTzProManageDetails: "getZjTzProManageDetails",
        getZjTzProManageDetails: "getZjTzProManageDetails",
        getZjTzProShareholderInfoList: "getZjTzProShareholderInfoList",
        getZjTzProRebuyInfoList: "getZjTzProRebuyInfoList",
        // 股东--
        addZjTzProShareholderInfo: "addZjTzProShareholderInfo",
        updateZjTzProShareholderInfo: "updateZjTzProShareholderInfo",
        batchDeleteUpdateZjTzProShareholderInfo: "batchDeleteUpdateZjTzProShareholderInfo",
        addZjTzProRebuyInfo: "addZjTzProRebuyInfo",
        updateZjTzProRebuyInfo: "updateZjTzProRebuyInfo",
        batchDeleteUpdateZjTzProRebuyInfo: "batchDeleteUpdateZjTzProRebuyInfo",
        // 子公司
        getZjTzProSubprojectInfoList: "getZjTzProSubprojectInfoList",
        addZjTzProSubprojectInfo: "addZjTzProSubprojectInfo",
        updateZjTzProSubprojectInfo: "updateZjTzProSubprojectInfo",
        batchDeleteUpdateZjTzProSubprojectInfo: "batchDeleteUpdateZjTzProSubprojectInfo",
        batchDeleteRecallZjTzRules: "batchDeleteRecallZjTzRules",
        batchDeleteReleaseZjTzRules: "batchDeleteReleaseZjTzRules",
        batchDeleteReleaseZjTzImportDoc: "batchDeleteReleaseZjTzImportDoc",
        batchDeleteRecallZjTzImportDoc: "batchDeleteRecallZjTzImportDoc",
        batchDeleteReleaseZjTzVideo: "batchDeleteReleaseZjTzVideo",
        batchDeleteRecallZjTzVideo: "batchDeleteRecallZjTzVideo",
        getZjTzVideoNoteList: "getZjTzVideoNoteList",
        addZjTzVideoNote: "addZjTzVideoNote",
        // 政策
        batchDeleteRecallZjTzPolicyCountry: "batchDeleteRecallZjTzPolicyCountry",
        batchDeleteReleaseZjTzPolicyCountry: "batchDeleteReleaseZjTzPolicyCountry",
        // 其他资料
        getZjTzOtherDataList: "getZjTzOtherDataList",
        getZjTzOtherDataDetails: "getZjTzOtherDataDetails",
        addZjTzOtherData: "addZjTzOtherData",
        updateZjTzOtherData: "updateZjTzOtherData",
        batchDeleteUpdateZjTzOtherData: "batchDeleteUpdateZjTzOtherData",
        toHomeShowZjTzOtherData: "toHomeShowZjTzOtherData",
        batchDeleteReleaseZjTzOtherData: "batchDeleteReleaseZjTzOtherData",
        batchDeleteRecallZjTzOtherData: "batchDeleteRecallZjTzOtherData",
        batchExportZjTzOtherDataFile: "batchExportZjTzOtherDataFile",
        //投资规模控制
        addZjTzSizeControl: "addZjTzSizeControl",
        getZjTzSizeControlList: "getZjTzSizeControlList",
        getZjTzSizeControlDetails: "getZjTzSizeControlDetails",
        // 内层
        getZjTzSizeControlRecordList: "getZjTzSizeControlRecordList",
        getZjTzSizeControlRecordDetails: "getZjTzSizeControlRecordDetails",
        addZjTzSizeControlRecord: "addZjTzSizeControlRecord",
        updateZjTzSizeControlRecord: "updateZjTzSizeControlRecord",
        batchDeleteUpdateZjTzSizeControlRecord: "batchDeleteUpdateZjTzSizeControlRecord",
        updateZjTzSizeControlRecordForFlow: "updateZjTzSizeControlRecordForFlow",
        getZjTzPermissionListByProject: "getZjTzPermissionListByProject",
        getZjTzPermissionList: "getZjTzPermissionList",
        changeZjTzProjectManagement: "changeZjTzProjectManagement",
        // 设计流程管理
        getZjTzDesignFlowList: "getZjTzDesignFlowList",
        getZjTzDesignFlowDetails: "getZjTzDesignFlowDetails",
        addZjTzDesignFlow: "addZjTzDesignFlow",
        updateZjTzDesignFlow: "updateZjTzDesignFlow",
        batchDeleteUpdateZjTzDesignFlow: "batchDeleteUpdateZjTzDesignFlow",
        // 选择模板新增
        addZjTzPartManage: "addZjTzPartManage",
        // 设计流程管理--内层--列表
        getZjTzPartManageList: "getZjTzPartManageList",
        updateZjTzPartManage: "updateZjTzPartManage",
        getBaseCodeList1: "getBaseCodeList",
        // 插入
        insertZjTzPartManage: "insertZjTzPartManage",
        batchDeleteUpdateZjTzPartManage: "batchDeleteUpdateZjTzPartManage",
        //锁定/解锁
        batchLockUpdateZjTzPartManage: "batchLockUpdateZjTzPartManage",
        batchClearUpdateZjTzPartManage: "batchClearUpdateZjTzPartManage",
        // 标准化
        getZjTzDesignAdvistoryUnitStandardList: "getZjTzDesignAdvistoryUnitStandardList",
        getZjTzDesignAdvistoryUnitStandardListForHard: "getZjTzDesignAdvistoryUnitStandardListForHard",
        addZjTzDesignAdvistoryUnitStandard: "addZjTzDesignAdvistoryUnitStandard",
        updateZjTzDesignAdvistoryUnitStandard: "updateZjTzDesignAdvistoryUnitStandard",
        batchDeleteUpdateZjTzDesignAdvistoryUnitStandard: "batchDeleteUpdateZjTzDesignAdvistoryUnitStandard",
        setZjTzDesignAdvistoryUnitStandardLibrary: "setZjTzDesignAdvistoryUnitStandardLibrary",
        getZjTzDesignAdvistoryUnitRecordAllList: "getZjTzDesignAdvistoryUnitRecordAllList",
        // 设计单位管理
        getZjTzDesignAdvistoryUnitList: "getZjTzDesignAdvistoryUnitList",
        getZjTzDesignAdvistoryUnitDetails: "getZjTzDesignAdvistoryUnitDetails",
        addZjTzDesignAdvistoryUnit: "addZjTzDesignAdvistoryUnit",
        // 设计单位管理-内层列表
        getZjTzDesignAdvistoryUnitRecordList: "getZjTzDesignAdvistoryUnitRecordList",
        getZjTzDesignAdvistoryUnitRecordDetails: "getZjTzDesignAdvistoryUnitRecordDetails",
        addZjTzDesignAdvistoryUnitRecord: "addZjTzDesignAdvistoryUnitRecord",
        updateZjTzDesignAdvistoryUnitRecord: "updateZjTzDesignAdvistoryUnitRecord",
        batchDeleteUpdateZjTzDesignAdvistoryUnitRecord: "batchDeleteUpdateZjTzDesignAdvistoryUnitRecord",
        // 评价
        evaluateZjTzDesignAdvistoryUnitRecord: "evaluateZjTzDesignAdvistoryUnitRecord",
        // 设计单位统计列表
        getZjTzDesignAdvistoryUnitRecordTotalList: "getZjTzDesignAdvistoryUnitRecordTotalList",
        getZjTzDesignChangeList: "getZjTzDesignChangeList",
        addZjTzDesignChange: "addZjTzDesignChange",
        getZjTzDesignChangeRecordList: "getZjTzDesignChangeRecordList",
        addZjTzDesignChangeRecord: "addZjTzDesignChangeRecord",
        updateZjTzDesignChangeRecord: "updateZjTzDesignChangeRecord",
        batchDeleteUpdateZjTzDesignChangeRecord: "batchDeleteUpdateZjTzDesignChangeRecord",
        getZjTzBaseCodeList: "getZjTzBaseCodeList",
        addZjTzBaseCode: "addZjTzBaseCode",
        updateZjTzBaseCode: "updateZjTzBaseCode",
        batchDeleteUpdateZjTzBaseCode: "batchDeleteUpdateZjTzBaseCode",
        getZjTzComprehensiveSupList: "getZjTzComprehensiveSupList",
        addZjTzComprehensiveSup: "addZjTzComprehensiveSup",
        updateZjTzComprehensiveSup: "updateZjTzComprehensiveSup",
        batchReleaseZjTzComprehensiveSup: "batchReleaseZjTzComprehensiveSup",
        batchRecallZjTzComprehensiveSup: "batchRecallZjTzComprehensiveSup",
        correctiveZjTzComprehensiveSup: "correctiveZjTzComprehensiveSup",
        batchDeleteUpdateZjTzComprehensiveSup: "batchDeleteUpdateZjTzComprehensiveSup",
        getZjTzComprehensiveSupDetails: "getZjTzComprehensiveSupDetails",
        batchExportZjTzComprehensiveSupFile: "batchExportZjTzComprehensiveSupFile",
        replyZjTzComprehensiveSup: "replyZjTzComprehensiveSup",
        getZjTzComprehensiveSupReplyList: "getZjTzComprehensiveSupReplyList",
        addZjTzSupReport: "addZjTzSupReport",
        getZjTzAnnualAssessList: "getZjTzAnnualAssessList",
        addZjTzAnnualAssess: "addZjTzAnnualAssess",
        updateZjTzAnnualAssess: "updateZjTzAnnualAssess",
        batchDeleteUpdateZjTzAnnualAssess: "batchDeleteUpdateZjTzAnnualAssess",
        batchReleaseZjTzAnnualAssess: "batchReleaseZjTzAnnualAssess",
        batchRecallZjTzAnnualAssess: "batchRecallZjTzAnnualAssess",
        batchExportZjTzAnnualAssessFile: "batchExportZjTzAnnualAssessFile",
        getZjTzSupervisorMonthlyMeetingList: "getZjTzSupervisorMonthlyMeetingList",
        addZjTzSupervisorMonthlyMeetingAddFile: "addZjTzSupervisorMonthlyMeetingAddFile",
        updateZjTzSupervisorMonthlyMeetingAddFile: "updateZjTzSupervisorMonthlyMeetingAddFile",
        batchDeleteUpdateZjTzSupervisorMonthlyMeeting: "batchDeleteUpdateZjTzSupervisorMonthlyMeeting",
        getZjTzSafetyQualityCheckBulletinList: "getZjTzSafetyQualityCheckBulletinList",
        addZjTzSafetyQualityCheckBulletinAddFile: "addZjTzSafetyQualityCheckBulletinAddFile",
        updateZjTzSafetyQualityCheckBulletinAddFile: "updateZjTzSafetyQualityCheckBulletinAddFile",
        batchDeleteUpdateZjTzSafetyQualityCheckBulletin: "batchDeleteUpdateZjTzSafetyQualityCheckBulletin",
        batchRecallZjTzDesignAdvistoryUnitRecord: "batchRecallZjTzDesignAdvistoryUnitRecord",
        batchReleaseZjTzDesignAdvistoryUnitRecord: "batchReleaseZjTzDesignAdvistoryUnitRecord",
        //QHSE
        //安全管理制度
        getZjTzSecurityManagementSystemList: "getZjTzSecurityManagementSystemList",
        addZjTzSecurityManagementSystemAddFile: "addZjTzSecurityManagementSystemAddFile",
        updateZjTzSecurityManagementSystemAddFile: "updateZjTzSecurityManagementSystemAddFile",
        batchDeleteUpdateZjTzSecurityManagementSystem: "batchDeleteUpdateZjTzSecurityManagementSystem",
        //质量管理制度
        getZjTzQualityManagementSystemList: "getZjTzQualityManagementSystemList",
        addZjTzQualityManagementSystemAddFile: "addZjTzQualityManagementSystemAddFile",
        updateZjTzQualityManagementSystemAddFile: "updateZjTzQualityManagementSystemAddFile",
        batchDeleteUpdateZjTzQualityManagementSystem: "batchDeleteUpdateZjTzQualityManagementSystem",
        //其它管理制度
        getZjTzOtherManagementSystemList: "getZjTzOtherManagementSystemList",
        addZjTzOtherManagementSystemAddFile: "addZjTzOtherManagementSystemAddFile",
        updateZjTzOtherManagementSystemAddFile: "updateZjTzOtherManagementSystemAddFile",
        batchDeleteUpdateZjTzOtherManagementSystem: "batchDeleteUpdateZjTzOtherManagementSystem",
        //安全应急预案
        getZjTzSafetyEmergencyPlanList: "getZjTzSafetyEmergencyPlanList",
        addZjTzSafetyEmergencyPlanAddFile: "addZjTzSafetyEmergencyPlanAddFile",
        updateZjTzSafetyEmergencyPlanAddFile: "updateZjTzSafetyEmergencyPlanAddFile",
        batchDeleteUpdateZjTzSafetyEmergencyPlan: "batchDeleteUpdateZjTzSafetyEmergencyPlan",
        //组织机构及人员
        //安全管理机构
        getZjTzSafetyManagementOrganList: "getZjTzSafetyManagementOrganList",
        addZjTzSafetyManagementOrganAddFile: "addZjTzSafetyManagementOrganAddFile",
        updateZjTzSafetyManagementOrganAddFile: "updateZjTzSafetyManagementOrganAddFile",
        batchDeleteUpdateZjTzSafetyManagementOrgan: "batchDeleteUpdateZjTzSafetyManagementOrgan",
        //质量管理机构
        getZjTzQualityManagementOrganList: "getZjTzQualityManagementOrganList",
        addZjTzQualityManagementOrganAddFile: "addZjTzQualityManagementOrganAddFile",
        updateZjTzQualityManagementOrganAddFile: "updateZjTzQualityManagementOrganAddFile",
        batchDeleteUpdateZjTzQualityManagementOrgan: "batchDeleteUpdateZjTzQualityManagementOrgan",
        //责任书
        //与上级单位签订的安全责任书
        getZjTzSuperiorUnitSecurityDutyList: "getZjTzSuperiorUnitSecurityDutyList",
        addZjTzSuperiorUnitSecurityDutyAddFile: "addZjTzSuperiorUnitSecurityDutyAddFile",
        updateZjTzSuperiorUnitSecurityDutyAddFile: "updateZjTzSuperiorUnitSecurityDutyAddFile",
        batchDeleteUpdateZjTzSuperiorUnitSecurityDuty: "batchDeleteUpdateZjTzSuperiorUnitSecurityDuty",
        //与总承包签订安全责任书
        getZjTzGeneralContractSecurityDutyList: "getZjTzGeneralContractSecurityDutyList",
        addZjTzGeneralContractSecurityDutyAddFile: "addZjTzGeneralContractSecurityDutyAddFile",
        updateZjTzGeneralContractSecurityDutyAddFile: "updateZjTzGeneralContractSecurityDutyAddFile",
        batchDeleteUpdateZjTzGeneralContractSecurityDuty: "batchDeleteUpdateZjTzGeneralContractSecurityDuty",
        //与上级单位签订的质量责任书
        getZjTzSuperiorUnitQualityDutyList: "getZjTzSuperiorUnitQualityDutyList",
        addZjTzSuperiorUnitQualityDutyAddFile: "addZjTzSuperiorUnitQualityDutyAddFile",
        updateZjTzSuperiorUnitQualityDutyAddFile: "updateZjTzSuperiorUnitQualityDutyAddFile",
        batchDeleteUpdateZjTzSuperiorUnitQualityDuty: "batchDeleteUpdateZjTzSuperiorUnitQualityDuty",
        //与总承包签订质量责任书
        getZjTzGeneralContractQualityDutyList: "getZjTzGeneralContractQualityDutyList",
        addZjTzGeneralContractQualityDutyAddFile: "addZjTzGeneralContractQualityDutyAddFile",
        updateZjTzGeneralContractQualityDutyAddFile: "updateZjTzGeneralContractQualityDutyAddFile",
        batchDeleteUpdateZjTzGeneralContractQualityDuty: "batchDeleteUpdateZjTzGeneralContractQualityDuty",
        //其它责任书
        getZjTzOtherDutyList: "getZjTzOtherDutyList",
        addZjTzOtherDutyAddFile: "addZjTzOtherDutyAddFile",
        updateZjTzOtherDutyAddFile: "updateZjTzOtherDutyAddFile",
        batchDeleteUpdateZjTzOtherDuty: "batchDeleteUpdateZjTzOtherDuty",
        getZjTzBrandYearPointList: "getZjTzBrandYearPointList",
        addZjTzBrandYearPoint: "addZjTzBrandYearPoint",
        updateZjTzBrandYearPoint: "updateZjTzBrandYearPoint",
        batchDeleteUpdateZjTzBrandYearPoint: "batchDeleteUpdateZjTzBrandYearPoint",
        batchReleaseZjTzBrandYearPoint: "batchReleaseZjTzBrandYearPoint",
        batchRecallZjTzBrandYearPoint: "batchRecallZjTzBrandYearPoint",
        batchExportZjTzBrandYearPointFile: "batchExportZjTzBrandYearPointFile",
        toHomeShowZjTzBrandYearPoint: "toHomeShowZjTzBrandYearPoint",
        getZjTzBrandImplementPointList: "getZjTzBrandImplementPointList",
        addZjTzBrandImplementPoint: "addZjTzBrandImplementPoint",
        updateZjTzBrandImplementPoint: "updateZjTzBrandImplementPoint",
        batchReleaseZjTzBrandImplementPoint: "batchReleaseZjTzBrandImplementPoint",
        batchRecallZjTzBrandImplementPoint: "batchRecallZjTzBrandImplementPoint",
        toHomeShowZjTzBrandImplementPoint: "toHomeShowZjTzBrandImplementPoint",
        batchExportZjTzBrandImplementPointFile: "batchExportZjTzBrandImplementPointFile",
        batchDeleteUpdateZjTzBrandImplementPoint: "batchDeleteUpdateZjTzBrandImplementPoint",
        addZjTzBrandResultShow: "addZjTzBrandResultShow",
        getZjTzBrandResultShowList: "getZjTzBrandResultShowList",
        updateZjTzBrandResultShow: "updateZjTzBrandResultShow",
        batchReleaseZjTzBrandResultShow: "batchReleaseZjTzBrandResultShow",
        batchRecallZjTzBrandResultShow: "batchRecallZjTzBrandResultShow",
        batchExportZjTzBrandResultShowFile: "batchExportZjTzBrandResultShowFile",
        toHomeShowZjTzBrandResultShow: "toHomeShowZjTzBrandResultShow",
        getZjTzSpecialYearPointList: "getZjTzSpecialYearPointList",
        addZjTzSpecialYearPoint: "addZjTzSpecialYearPoint",
        updateZjTzSpecialYearPoint: "updateZjTzSpecialYearPoint",
        batchReleaseZjTzSpecialYearPoint: "batchReleaseZjTzSpecialYearPoint",
        batchRecallZjTzSpecialYearPoint: "batchRecallZjTzSpecialYearPoint",
        toHomeShowZjTzSpecialYearPoint: "toHomeShowZjTzSpecialYearPoint",
        batchExportZjTzSpecialYearPointFile: "batchExportZjTzSpecialYearPointFile",
        batchDeleteUpdateZjTzSpecialYearPoint: "batchDeleteUpdateZjTzSpecialYearPoint",
        getZjTzSpecialResultShowList: "getZjTzSpecialResultShowList",
        addZjTzSpecialResultShow: "addZjTzSpecialResultShow",
        updateZjTzSpecialResultShow: "updateZjTzSpecialResultShow",
        batchReleaseZjTzSpecialResultShow: "batchReleaseZjTzSpecialResultShow",
        batchRecallZjTzSpecialResultShow: "batchRecallZjTzSpecialResultShow",
        batchExportZjTzSpecialResultShowFile: "batchExportZjTzSpecialResultShowFile",
        batchDeleteUpdateZjTzSpecialResultShow: "batchDeleteUpdateZjTzSpecialResultShow",
        toHomeShowZjTzSpecialResultShow: "toHomeShowZjTzSpecialResultShow",
        batchDeleteUpdateZjTzBrandResultShow: "batchDeleteUpdateZjTzBrandResultShow",
        getZjTzNoteInstructSugList: "getZjTzNoteInstructSugList",
        getZjTzNoteInstructSugDetails:"getZjTzNoteInstructSugDetails",
        addZjTzNoteInstructSug: "addZjTzNoteInstructSug",
        updateZjTzNoteInstructSug: "updateZjTzNoteInstructSug",
        batchReleaseZjTzNoteInstructSug: "batchReleaseZjTzNoteInstructSug",
        batchRecallZjTzNoteInstructSug: "batchRecallZjTzNoteInstructSug",
        batchDeleteUpdateZjTzNoteInstructSug: "batchDeleteUpdateZjTzNoteInstructSug",
        batchExportZjTzNoteInstructSugFile: "batchExportZjTzNoteInstructSugFile",
        toHomeShowZjTzNoteInstructSug: "toHomeShowZjTzNoteInstructSug",
        getZjTzCompSupReportList: "getZjTzCompSupReportList",
        addZjTzCompSupReport: "addZjTzCompSupReport",
        updateZjTzCompSupReport: "updateZjTzCompSupReport",
        batchReleaseZjTzCompSupReport: "batchReleaseZjTzCompSupReport",
        batchRecallZjTzCompSupReport: "batchRecallZjTzCompSupReport",
        batchExportZjTzCompSupReportFile: "batchExportZjTzCompSupReportFile",
        batchDeleteUpdateZjTzCompSupReport: "batchDeleteUpdateZjTzCompSupReport",
        correctiveZjTzCompSupReport: "correctiveZjTzCompSupReport",
        getZjTzNoteInstructSugRecordList: "getZjTzNoteInstructSugRecordList",
        addZjTzNoteInstructSugRecord: "addZjTzNoteInstructSugRecord",
        getZjTzCompSupReportReplyList: "getZjTzCompSupReportReplyList",
        replyZjTzCompSupReport: "replyZjTzCompSupReport",
        // 千分制检查设置项
        getZjTzThousandCheckBaseList: "getZjTzThousandCheckBaseList",
        addZjTzThousandCheckBase: "addZjTzThousandCheckBase",
        updateZjTzThousandCheckBase: "updateZjTzThousandCheckBase",
        batchDeleteUpdateZjTzThousandCheckBase: "batchDeleteUpdateZjTzThousandCheckBase",
        // 千分制检查
        getZjTzThousandCheckList: "getZjTzThousandCheckList",
        addZjTzThousandCheck: "addZjTzThousandCheck",
        updateZjTzThousandCheck: "updateZjTzThousandCheck",
        batchDeleteUpdateZjTzThousandCheck: "batchDeleteUpdateZjTzThousandCheck",
        batchReleaseZjTzThousandCheck: "batchReleaseZjTzThousandCheck",
        batchRecallZjTzThousandCheck: "batchRecallZjTzThousandCheck",
        batchExportZjTzThousandCheckFile: "batchExportZjTzThousandCheckFile",
        getZjTzThousandDeductList: "getZjTzThousandDeductList",
        updateZjTzThousandDeduct: "updateZjTzThousandDeduct",
        batchImportZjTzThousandCheckBase: "batchImportZjTzThousandCheckBase",
        // 经营建设---项目标前核准
        getZjTzProPreApprovalList: "getZjTzProPreApprovalList",
        addZjTzProPreApproval: "addZjTzProPreApproval",
        updateZjTzProPreApproval: "updateZjTzProPreApproval",
        batchReleaseZjTzProPreApproval: "batchReleaseZjTzProPreApproval",
        batchRecallZjTzProPreApproval: "batchRecallZjTzProPreApproval",
        batchExportZjTzProPreApprovalFile: "batchExportZjTzProPreApprovalFile",
        batchDeleteUpdateZjTzProPreApproval: "batchDeleteUpdateZjTzProPreApproval",
        batchDeleteUpdateZjTzMonthlyMeet: "batchDeleteUpdateZjTzMonthlyMeet",
        updateZjTzMonthlyMeet: "updateZjTzMonthlyMeet",
        addZjTzMonthlyMeet: "addZjTzMonthlyMeet",
        getZjTzMonthlyMeetList: "getZjTzMonthlyMeetList",
        getZjTzProEventList: "getZjTzProEventList",
        addZjTzProEvent: "addZjTzProEvent",
        updateZjTzProEvent: "updateZjTzProEvent",
        batchDeleteUpdateZjTzProEvent: "batchDeleteUpdateZjTzProEvent",
        getZjTzPppTermBaseList: "getZjTzPppTermBaseList",
        addZjTzPppTermBase: "addZjTzPppTermBase",
        updateZjTzPppTermBase: "updateZjTzPppTermBase",
        batchDeleteUpdateZjTzPppTermBase: "batchDeleteUpdateZjTzPppTermBase",
        batchImportZjTzPppTermBase: "batchImportZjTzPppTermBase",
        getZjTzPppTermList: "getZjTzPppTermList",
        addZjTzPppTerm: "addZjTzPppTerm",
        updateZjTzPppTerm: "updateZjTzPppTerm",
        batchDeleteUpdateZjTzPppTerm: "batchDeleteUpdateZjTzPppTerm",
        getZjTzPppTermReplyList: "getZjTzPppTermReplyList",
        updateZjTzPppTermReply: "updateZjTzPppTermReply",
        getZjTzLifeCycleList: "getZjTzLifeCycleList",
        addZjTzLifeCycle: "addZjTzLifeCycle",
        updateZjTzLifeCycle: "updateZjTzLifeCycle",
        batchDeleteUpdateZjTzLifeCycle: "batchDeleteUpdateZjTzLifeCycle",
        updateZjTzLifeCycleFroFlow: "updateZjTzLifeCycleFroFlow",
        getZjTzLifeCycleDetails: "getZjTzLifeCycleDetails",
        getZjTzLifeCycleOpinionList: "getZjTzLifeCycleOpinionList",
        getZjTzLifeCycleChangeList: "getZjTzLifeCycleChangeList",
        getZjTzLifeCycleChangeDetails: "getZjTzLifeCycleChangeDetails",
        addZjTzLifeCycleChange: "addZjTzLifeCycleChange",
        updateZjTzLifeCycleChange: "updateZjTzLifeCycleChange",
        batchDeleteUpdateZjTzLifeCycleChange: "batchDeleteUpdateZjTzLifeCycleChange",
        updateZjTzLifeCycleChangeForFlow: "updateZjTzLifeCycleChangeForFlow",
        getZjTzLifeCycleChangeOpinionAndProIdList: "getZjTzLifeCycleChangeOpinionAndProIdList",
        getZjTzLifeCycleChangeOpinionList: "getZjTzLifeCycleChangeOpinionList",
        getZjTzRiskListBaseList: "getZjTzRiskListBaseList",
        getZjTzRiskListBaseDetails: "getZjTzRiskListBaseDetails",
        addZjTzRiskListBase: "addZjTzRiskListBase",
        updateZjTzRiskListBase: "updateZjTzRiskListBase",
        batchDeleteUpdateZjTzRiskListBase: "batchDeleteUpdateZjTzRiskListBase",
        batchClearUpdateZjTzRiskListBase: "batchClearUpdateZjTzRiskListBase", batchLockUpdateZjTzRiskListBase: "batchLockUpdateZjTzRiskListBase",
        getZjTzRiskList: "getZjTzRiskList",
        addZjTzRisk: "addZjTzRisk",
        updateZjTzRisk: "updateZjTzRisk",
        batchDeleteUpdateZjTzRisk: "batchDeleteUpdateZjTzRisk",
        getZjTzRiskDetailList: "getZjTzRiskDetailList",
        addZjTzRiskDetail: "addZjTzRiskDetail",
        updateZjTzRiskDetail: "updateZjTzRiskDetail",
        batchClearUpdateZjTzRiskDetail: "batchClearUpdateZjTzRiskDetail",
        batchLockUpdateZjTzRiskDetail: "batchLockUpdateZjTzRiskDetail",
        batchDeleteUpdateZjTzRiskDetail: "batchDeleteUpdateZjTzRiskDetail",
        getZjTzFinanceMonthList: "getZjTzFinanceMonthList",
        addZjTzFinanceMonth: "addZjTzFinanceMonth",
        updateZjTzFinanceMonth: "updateZjTzFinanceMonth",
        batchDeleteUpdateZjTzFinanceMonth: "batchDeleteUpdateZjTzFinanceMonth",
        getZjTzFinanceMonthDetails: "getZjTzFinanceMonthDetails",
        getZjTzDebtList: "getZjTzDebtList",
        addZjTzDebt: "addZjTzDebt",
        updateZjTzDebt: "updateZjTzDebt",
        batchDeleteUpdateZjTzDebt: "batchDeleteUpdateZjTzDebt",
        getZjTzDebtExcelList: "getZjTzDebtExcelList",
        batchImportZjTzDebtExcel: "batchImportZjTzDebtExcel",
        getZjTzProfitList: "getZjTzProfitList",
        getZjTzProfitDetails: "getZjTzProfitDetails",
        addZjTzProfit: "addZjTzProfit",
        updateZjTzProfit: "updateZjTzProfit",
        batchDeleteUpdateZjTzProfit: "batchDeleteUpdateZjTzProfit",
        getZjTzProfitExcelList: "getZjTzProfitExcelList",
        batchImportZjTzProfitExcel: "batchImportZjTzProfitExcel",
        getZjTzCashList: "getZjTzCashList",
        getZjTzCashDetails: "getZjTzCashDetails",
        addZjTzCash: "addZjTzCash",
        updateZjTzCash: "updateZjTzCash",
        batchDeleteUpdateZjTzCash: "batchDeleteUpdateZjTzCash",
        getZjTzCashExcelList: "getZjTzCashExcelList",
        batchImportZjTzCashExcel: "batchImportZjTzCashExcel",
        // 运营--???
        getZjTzRunSchemeList: "getZjTzRunSchemeList",
        addZjTzRunScheme: "addZjTzRunScheme",
        updateZjTzRunScheme: "updateZjTzRunScheme",
        batchDeleteUpdateZjTzRunScheme: "batchDeleteUpdateZjTzRunScheme",
        updateZjTzRunSchemeForFlow: "updateZjTzRunSchemeForFlow",
        getZjTzRunSchemeOpinionList: "getZjTzRunSchemeOpinionList",
        // 运营季报管理
        getZjTzRunQuarterlyList: "getZjTzRunQuarterlyList",
        getZjTzRunQuarterlyDetails: "getZjTzRunQuarterlyDetails",
        addZjTzRunQuarterly: "addZjTzRunQuarterly",
        updateZjTzRunQuarterly: "updateZjTzRunQuarterly",
        batchDeleteUpdateZjTzRunQuarterly: "batchDeleteUpdateZjTzRunQuarterly",
        getSysDepartmentListByCondition: "getSysDepartmentListByCondition",
        // 三会管理
        getZjTzThreeShareholderList: "getZjTzThreeShareholderList",
        getZjTzThreeShareholderDetails: "getZjTzThreeShareholderDetails",
        addZjTzThreeShareholder: "addZjTzThreeShareholder",
        updateZjTzThreeShareholder: "updateZjTzThreeShareholder",
        batchDeleteUpdateZjTzThreeShareholder: "batchDeleteUpdateZjTzThreeShareholder",
        updateZjTzThreeShareholderForFlow: "updateZjTzThreeShareholderForFlow",
        getZjTzThreeShareholderBillList: "getZjTzThreeShareholderBillList",
        addZjTzThreeShareholderBill: "addZjTzThreeShareholderBill",
        updateZjTzThreeShareholderBill: "updateZjTzThreeShareholderBill",
        batchDeleteUpdateZjTzThreeShareholderBill: "batchDeleteUpdateZjTzThreeShareholderBill",
        // 董事会
        getZjTzThreeDirectorList: "getZjTzThreeDirectorList",
        getZjTzThreeDirectorDetails: "getZjTzThreeDirectorDetails",
        addZjTzThreeDirector: "addZjTzThreeDirector",
        updateZjTzThreeDirector: "updateZjTzThreeDirector",
        batchDeleteUpdateZjTzThreeDirector: "batchDeleteUpdateZjTzThreeDirector",
        updateZjTzThreeDirectorForFlow: "updateZjTzThreeDirectorForFlow",
        getZjTzThreeDirectorBillList: "getZjTzThreeDirectorBillList",
        addZjTzThreeDirectorBill: "addZjTzThreeDirectorBill",
        updateZjTzThreeDirectorBill: "updateZjTzThreeDirectorBill",
        batchDeleteUpdateZjTzThreeDirectorBill: "batchDeleteUpdateZjTzThreeDirectorBill",
        // 监事会
        getZjTzThreeSupervisorList: "getZjTzThreeSupervisorList",
        getZjTzThreeSupervisorDetails: "getZjTzThreeSupervisorDetails",
        addZjTzThreeSupervisor: "addZjTzThreeSupervisor",
        updateZjTzThreeSupervisor: "updateZjTzThreeSupervisor",
        batchDeleteUpdateZjTzThreeSupervisor: "batchDeleteUpdateZjTzThreeSupervisor",
        updateZjTzThreeSupervisorForFlow: "updateZjTzThreeSupervisorForFlow",
        getZjTzThreeSupervisorBillList: "getZjTzThreeSupervisorBillList",
        addZjTzThreeSupervisorBill: "addZjTzThreeSupervisorBill",
        updateZjTzThreeSupervisorBill: "updateZjTzThreeSupervisorBill",
        batchDeleteUpdateZjTzThreeSupervisorBill: "batchDeleteUpdateZjTzThreeSupervisorBill",
        saveZjTzPppTermAllReply: "saveZjTzPppTermAllReply",
        "addZjTzPermission": "addZjTzPermission",
        "updateZjTzPermission": "updateZjTzPermission",
        "batchDeleteUpdateZjTzPermission": "batchDeleteUpdateZjTzPermission",
        saveZjTzThousandCheckAllDeduct: "saveZjTzThousandCheckAllDeduct",
        saveZjTzRiskAllDetail: "saveZjTzRiskAllDetail",
        getZjTzDesignChangeStatisticsList: "getZjTzDesignChangeStatisticsList",
        zjTzDesignChangeStatisticsList: "zjTzDesignChangeStatisticsList",
        //董监高会
        getZjTzExecutiveMeetList: "getZjTzExecutiveMeetList",
        getZjTzExecutiveMeetDetails: "getZjTzExecutiveMeetDetails",
        addZjTzExecutiveMeet: "addZjTzExecutiveMeet",
        updateZjTzExecutiveMeet: "updateZjTzExecutiveMeet",
        batchDeleteUpdateZjTzExecutiveMeet: "batchDeleteUpdateZjTzExecutiveMeet",
        getZjTzExecutiveMeetListFromProjectShareholder: "getZjTzExecutiveMeetListFromProjectShareholder",
        changeZjTzExecutiveMeet: "changeZjTzExecutiveMeet",
        correctiveZjTzExecutiveMeet: "correctiveZjTzExecutiveMeet",
        getZjTzExecutiveMeetChangeRecordList: "getZjTzExecutiveMeetChangeRecordList",
        getZjTzExecutiveMeetChangeRecordDetails: "getZjTzExecutiveMeetChangeRecordDetails",
        updateZjTzExecutiveMeetChangeRecord: "updateZjTzExecutiveMeetChangeRecord",
        batchDeleteUpdateZjTzExecutiveMeetChangeRecord: "batchDeleteUpdateZjTzExecutiveMeetChangeRecord",
        //安全质量统计
        getZjTzCountAqList: "getZjTzCountAqList",
        getZxHwAqHiddenDangerList: "getZxHwAqHiddenDangerList",
        batchReleaseZjTzRisk: "batchReleaseZjTzRisk",
        batchRecallZjTzRisk: "batchRecallZjTzRisk",
        "batchReleaseZjTzDesignFlow": "batchReleaseZjTzDesignFlow",
        "batchRecallZjTzDesignFlow": "batchRecallZjTzDesignFlow",
        batchExportZjTzRulesFile: "batchExportZjTzRulesFile",

        //人员库
        getZjTzEmployeeInfoList: "getZjTzEmployeeInfoList",
        addZjTzEmployeeInfo: "addZjTzEmployeeInfo",
        updateZjTzEmployeeInfo: "updateZjTzEmployeeInfo",
        batchDeleteUpdateZjTzEmployeeInfo: "batchDeleteUpdateZjTzEmployeeInfo",
        getZjTzEmployeeInfoDetails: "getZjTzEmployeeInfoDetails",

        //人员库-职执业资格
        addZjTzEmployeeQualification: "addZjTzEmployeeQualification",
        batchDeleteUpdateZjTzEmployeeQualification: "batchDeleteUpdateZjTzEmployeeQualification",
        getZjTzEmployeeQualificationList: "getZjTzEmployeeQualificationList",
        saveZjTzPartManageAllList: "saveZjTzPartManageAllList",
        //合规管理
        getZjTzComplianceDealList: "getZjTzComplianceDealList",
        getZjTzComplianceDealDetails: "getZjTzComplianceDealDetails",
        addZjTzComplianceDeal: "addZjTzComplianceDeal",
        updateZjTzComplianceDeal: "updateZjTzComplianceDeal",
        batchDeleteUpdateZjTzComplianceDeal: "batchDeleteUpdateZjTzComplianceDeal",
        getZjTzComplianceDetailList: "getZjTzComplianceDetailList",
        addZjTzComplianceDetail: "addZjTzComplianceDetail",
        updateZjTzComplianceDetail: "updateZjTzComplianceDetail",
        batchDeleteUpdateZjTzComplianceDetail: "batchDeleteUpdateZjTzComplianceDetail",
        saveZjTzComplianceDealAllDetail: "saveZjTzComplianceDealAllDetail",
        exportZjTzComplianceDetailList:"exportZjTzComplianceDetailList",

        //合规手续设置
        getZjTzComplianceBaseList: "getZjTzComplianceBaseList",
        getZjTzComplianceBaseDetails: "getZjTzComplianceBaseDetails",
        addZjTzComplianceBase: "addZjTzComplianceBase",
        updateZjTzComplianceBase: "updateZjTzComplianceBase",
        batchDeleteUpdateZjTzComplianceBase: "batchDeleteUpdateZjTzComplianceBase",

        //项目人员
        getZjTzProjectEmployeeList: "getZjTzProjectEmployeeList",
        addZjTzProjectEmployee: "addZjTzProjectEmployee",
        updateZjTzProjectEmployee: "updateZjTzProjectEmployee",
        batchDeleteUpdateZjTzProjectEmployee: "batchDeleteUpdateZjTzProjectEmployee",
        checkZjTzProjectEmployee: "checkZjTzProjectEmployee",
        recallZjTzProjectEmployee: "recallZjTzProjectEmployee",
        exportZjTzEmployeeInfo: "exportZjTzEmployeeInfo",
        //项目和人员关联表
        getZjTzProjectAndEmployeeList: "getZjTzProjectAndEmployeeList",
        addZjTzProjectAndEmployee: "addZjTzProjectAndEmployee",
        batchDeleteUpdateZjTzProjectAndEmployee: "batchDeleteUpdateZjTzProjectAndEmployee",
        addZjTzEmployeeInfoZjTzProjectAndEmployee: "addZjTzEmployeeInfoZjTzProjectAndEmployee",
        recallAddZjTzProjectEmployee: "recallAddZjTzProjectEmployee",
        getZjTzProjectEmployeeDetails: "getZjTzProjectEmployeeDetails",
        // 运营文件管理
        getZjTzRunFileList: "getZjTzRunFileList",
        addZjTzRunFile: "addZjTzRunFile",
        batchDeleteUpdateZjTzRunFile: "batchDeleteUpdateZjTzRunFile",
        updateZjTzRunFile: "updateZjTzRunFile",
        batchExportZjTzRunFile: "batchExportZjTzRunFile",
        // 融资落实情况月报-上报-9.18
        batchRecallZjTzFinanceMonth: "batchRecallZjTzFinanceMonth",
        batchReleaseZjTzFinanceMonth: "batchReleaseZjTzFinanceMonth",
        // 地方政策
        batchReturnZjTzPolicyLocal: "batchReturnZjTzPolicyLocal",
        batchReportZjTzPolicyLocal: "batchReportZjTzPolicyLocal",
        // 投资规模控制
        singleReleaseZjTzSizeControlRecord: "singleReleaseZjTzSizeControlRecord",
        singleRecallZjTzSizeControlRecord: "singleRecallZjTzSizeControlRecord",
        // PPP合同分析
        batchReleaseZjTzPppTerm: "batchReleaseZjTzPppTerm",
        batchRecallZjTzPppTerm: "batchRecallZjTzPppTerm",
        // 设计变更
        batchReleaseZjTzDesignChangeRecord: "batchReleaseZjTzDesignChangeRecord",
        batchRecallZjTzDesignChangeRecord: "batchRecallZjTzDesignChangeRecord",
        // 合同分析-导出
        zjTzPppTermReply: "zjTzPppTermReply",
        reportZjTzCompSupReport: "reportZjTzCompSupReport",
        getZjTzProManageListForHard: "getZjTzProManageListForHard",


        // 项目投资情况完成表--ok-----id显示不对--导出
        getZjTzInvXmtzqkMonthlyReportList: "getZjTzInvXmtzqkMonthlyReportList",
        //投资基础数据列表--缺字段
        getZjTzInvXmtzqkMonthlyReportListBasicData: "getZjTzInvXmtzqkMonthlyReportListBasicData",
        //资金基础数据列表
        getZjTzInvXmzjqkMonthlyReportListBasicData: "getZjTzInvXmzjqkMonthlyReportListBasicData",
        //投资基础数据详情--树表
        getZjTzInvXmtzqkMonthlyReportListBasicDataDetails: "getZjTzInvXmtzqkMonthlyReportListBasicDataDetails",
        //资金情况月报--ok-----id显示不对--导出
        getZjTzInvXmzjqkMonthlyReportList: "getZjTzInvXmzjqkMonthlyReportList",
        // 合规报表-ok
        getZjTzComplianceDetailListForReport: "getZjTzComplianceDetailListForReport",
        // 董监高报表-ok
        getZjTzExecutivePersonnelListForReport: "getZjTzExecutivePersonnelListForReport",
        // 采购回购报表-字段缺失--导出
        getZjTzInvXmhgqkMonthlyReportList: "getZjTzInvXmhgqkMonthlyReportList",
        getZjTzInvXmzjqkMonthlyReportListBasicDataDetails: "getZjTzInvXmzjqkMonthlyReportListBasicDataDetails",
        getZjTzInvXmhgqkMonthlyReportListBasicData: "getZjTzInvXmhgqkMonthlyReportListBasicData",
        getZjTzInvXmhgqkMonthlyReportListBasicDataDetails: "getZjTzInvXmhgqkMonthlyReportListBasicDataDetails",
        getZjTzThreeShareholderBillListForReport: "getZjTzThreeShareholderBillListForReport",
        getZjTzInvXmyyqkMonthlyReportListBasicData: "getZjTzInvXmyyqkMonthlyReportListBasicData",
        getZjTzInvXmyyqkMonthlyReportListBasicDataDetail:"getZjTzInvXmyyqkMonthlyReportListBasicDataDetail",
        //首页
        //计划进度
        getHomeProgressWarningPlanningProgress:"getHomeProgressWarningPlanningProgress",
        exportHomeProgressWarningPlanningProgress:"exportHomeProgressWarningPlanningProgress",
        //考核排名
        getHomeProgressWarningChecking:"getHomeProgressWarningChecking",
        //预警信息
        getHomeProgressWarningInfo:"getHomeProgressWarningInfo",
        exportHomeProjectStatus:"exportHomeProjectStatus",
        //年度目标完成情况
        getHomeProgressWarningCompleteStatus:"getHomeProgressWarningCompleteStatus",
        //工期预警
        getHomeProjectStatusAllProject:"getHomeProjectStatusAllProject",
        getHomeProjectStatusAllProjectAlertPage:"getHomeProjectStatusAllProjectAlertPage",
        //年度资金情况
        getHomeChartCapitalStatus:"getHomeChartCapitalStatus",
        //运营数据
        getHomeChartOperateData:"getHomeChartOperateData",
        //回购数据
        getHomeChartHgData:"getHomeChartHgData",
        //趋势数据
        getHomeChartTrendData:"getHomeChartTrendData",
        //项目管理单位统计
        getHomeChartComnameStatis:"getHomeChartComnameStatis",
        //地图总览
        getHomeRegionalOverviewZhtAndZja:"getHomeRegionalOverviewZhtAndZja",
        getHomeRegionalOverviewMap:"getHomeRegionalOverviewMap",
        getHomeProgressPlaningComname:"getHomeProgressPlaningComname",
        exportHomeRegionalOverviewProList:"exportHomeRegionalOverviewProList",
        //投资项目数
        getHomeRegionalOverviewProcessAndType:"getHomeRegionalOverviewProcessAndType",
        //项目列表
        getHomeRegionalOverviewProList:"getHomeRegionalOverviewProList",
        //宏观政策
        getZjTzPolicyCountryHomeAdvertMacro:"getZjTzPolicyCountryHomeAdvertMacro",
        getZjTzPolicyCountryDetails:"getZjTzPolicyCountryDetails",
        getZjTzPolicyLocalDetails:"getZjTzPolicyLocalDetails",
        //宣贯视频
        getZjTzVideoHome:"getZjTzVideoHome",
        //公告栏
        getHomeZjTzNoteInstructSug:"getHomeZjTzNoteInstructSug",
        //品牌建设
        getZjTzBrandYearPointHome:"getZjTzBrandYearPointHome",
        getZjTzBrandYearPointDetails:"getZjTzBrandYearPointDetails",
        getZjTzSpecialYearPointDetails:"getZjTzSpecialYearPointDetails",
        getZjTzBrandImplementPointDetails:"getZjTzBrandImplementPointDetails",
        //规章制度
        getZjTzRulesHome:"getZjTzRulesHome",
        //其他资料
        getZjTzOtherDataHome:"getZjTzOtherDataHome",
        //成果展示
        getHomeAdvertZjTzBrandResultShow:"getHomeAdvertZjTzBrandResultShow",
        getZjTzBrandResultShowDetails:"getZjTzBrandResultShowDetails",
        //项目简介
        getZjTzProManageDetails:"getZjTzProManageDetails",
        //预警
        getProjectPageWarning:"getProjectPageWarning",
        //总览
        getHomeRegionalOverviewZhtAndZja:"getHomeRegionalOverviewZhtAndZja",
        getConstructPageProduction: "getConstructPageProduction",
        getConstructPageCapital: 'getConstructPageCapital',
        getConstructPageProduction: "getConstructPageProduction",
        getConstructPageTrendData: "getConstructPageTrendData",
        getHomeProjectStatus: "getHomeProjectStatus",
        getConstructPageProductionRanking: "getConstructPageProductionRanking",
        getOperatePageCurrentPeriod: "getOperatePageCurrentPeriod",
        getOperatePageCommentAndIncome: "getOperatePageCommentAndIncome",
        getHgPageHgStatus: "getHgPageHgStatus",
        getHgPageCurrentPeriod: "getHgPageCurrentPeriod",
        getZjTzProRebuyInfoPlanList:'getZjTzProRebuyInfoPlanList',
        getZjTzProRebuyInfoPlanDetails:'getZjTzProRebuyInfoPlanDetails',
        addZjTzProRebuyInfoPlan:'addZjTzProRebuyInfoPlan',
        updateZjTzProRebuyInfoPlan:'updateZjTzProRebuyInfoPlan',
        batchDeleteUpdateZjTzProRebuyInfoPlan: 'batchDeleteUpdateZjTzProRebuyInfoPlan',
        getZjTzRiskAnalysisList: "getZjTzRiskAnalysisList",
        addZjTzRiskAnalysis: "addZjTzRiskAnalysis",
        updateZjTzRiskAnalysis: "updateZjTzRiskAnalysis",
        batchDeleteUpdateZjTzRiskAnalysis: "batchDeleteUpdateZjTzRiskAnalysis",
        batchReleaseZjTzRiskAnalysis: "batchReleaseZjTzRiskAnalysis",
        batchRecallZjTzRiskAnalysis: "batchRecallZjTzRiskAnalysis",
        batchExportZjTzRiskAnalysisFile: "batchExportZjTzRiskAnalysisFile",
        getZjTzEmployeeInfoList: "getZjTzEmployeeInfoList",
        printZjTzEmployeeInfo: 'printZjTzEmployeeInfo',
        getZjTzEmployeeInfoByIdCard: "getZjTzEmployeeInfoByIdCard",
        importProjectZjTzEmployeeInfo: "importProjectZjTzEmployeeInfo",
        leaveOfficeZjTzEmployeeInfoList: "leaveOfficeZjTzEmployeeInfoList",
        importProjectZjTzEmployeeInfoList: 'importProjectZjTzEmployeeInfoList',
        changeZjTzProManageToSign: 'changeZjTzProManageToSign',
        getProInvBasicList: "getProInvBasicList",
        updateProInvBasic: "updateProInvBasic",
        getZjTzProManageListNotUpdated: "getZjTzProManageListNotUpdated",
        addZjTzAerialVideo: 'addZjTzAerialVideo',
        updateZjTzAerialVideo: "updateZjTzAerialVideo",
        batchDeleteUpdateZjTzAerialVideo: "batchDeleteUpdateZjTzAerialVideo",
        getZjTzAerialVideoList:'getZjTzAerialVideoList'
    }
};
// function aa() {
//     console.log(JSON.stringify(window.configs.apis,null,4))
// }
// aa()
