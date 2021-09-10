window.configs = {
    dev: false,
    debugConsole: false,
    domain: "http://192.168.1.223:8080/web/",
    // domain: "http://weixin.fheb.cn:99/apiwoa/",
    // domain: "http://test.apih5.com/apiztReport/",

    appInfo: {
        logo: "http://47.96.150.231/logo/logo.png",
        id: "zj_qyh_woa_id",
        // id: "zt12_qyh_suopei",
        title: "任务管理",
        copyright: "任务管理",
        name: "sjzoa",
        loginType: "1", //1:普通账号密码；2：手机验证码快捷登录；3:企业号授权（也可以在app登录）；4:服务号授权；5、扫码登录  6、app登录  31、会弹出授权窗口的授权
        mainModule: "/app/",
        helpHref: ""
    },
    //登录页面轮播背景配置 配置后注意文件夹/src/view/login/img下是否存在对应的图片
    //地址也可以直接引用http://xxx || https://xxx
    loginPageConfig: {
        bgImgUrl: ["img1.png","img2.png"],
        rowText: ["xxx有限公司","服务即营销 口碑即品牌"]
    },
    //是否可以切换项目 默认true
    canChangeProject: false,

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
        login: "user/login", //登录
        editPwd: "user/updateUserPwd",//改密
        getSysUserListByBg: "user/getSysUserListByBg",
        addSysUserInfoByBg: "user/addSysUserInfoByBg",
        updateSysUserInfoByBg: "user/updateSysUserInfoByBg",
        deleteSysUserInfoByBg: "user/deleteSysUserInfoByBg",
        upload: "upload", //上传接口
        getSysDepartmentAllTree: "getSysDepartmentUserAllTree?noPersonnel=1", //通讯录-部门获取
        // getSysDepartmentUserAllTree: "getSysDepartmentUserAllTree", //通讯录-部门的人员获取
        getSysDepartmentUserAllTree: "getSysDepartmentUserAllTreeByZj",//获取通讯录树结构 zj
        appGetSysFrequentContactsList: "appGetSysFrequentContactsList", //获取收藏的联系人
        appAddSysFrequentContacts: "appAddSysFrequentContacts", //添加联系人到收藏
        appRemoveSysFrequentContacts: "appRemoveSysFrequentContacts", //移除收藏联系人
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
        getBaseFlowCodeList: "getBaseFlowCodeList",
        baseFlowCodeImport: "baseFlowCodeImport",
        batchDeleteUpdateBaseFlowCode: "batchDeleteUpdateBaseFlowCode",
        getBaseFlowSponsorChooserList: "getBaseFlowSponsorChooserList",
        getFlowNameSelectList: "getFlowNameSelectList",
        addBaseFlowSponsorChooserByList: "addBaseFlowSponsorChooserByList",
        batchDeleteUpdateBaseFlowSponsorChooser:
            "batchDeleteUpdateBaseFlowSponsorChooser",
        updateBaseFlowSponsorChooserByList:
            "updateBaseFlowSponsorChooserByList",
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
        exportZxHwFlowToWord: "exportZxHwFlowToWord", //获取流程预览网址
        getHasTodoList: "getHasTodoList",
        getZxQrcodeQrcodeList: "getZxQrcodeQrcodeList",
        getZxHwHomeMobilIndex: "getZxHwHomeMobilIndex",
        getIndexviewDetails: "getIndexviewDetails",
        getZxQrcodePermissionObjectListByProject: "getZxQrcodePermissionObjectListByProject",
        moveUpdateSysMenu: "moveUpdateSysMenu", //菜单管理节点移动

        //项目获取切换等接口
        getZxQrcodePermissionObjectListByProject: "getZxQrcodePermissionObjectListByProject",
        changeZxQrcodePermissionProject: "changeZxQrcodePermissionProject",

        //字典组织层级[通用]
        addBaseCode: "addBaseCode",
        updateBaseCode: "updateBaseCode",
        getBaseCodeList: "getBaseCodeList",
        batchDeleteUpdateBaseCode: "batchDeleteUpdateBaseCode",
        pcUpdateBaseCodeOnTree: "pcUpdateBaseCodeOnTree", //更新数据字典[在树结构上编辑]   
        pcExchangeBaseCode: "pcExchangeBaseCode",

        //查询组织层级前台页面配置   
        getBaseCodeUIConfig: 'getBaseCodeUIConfig',

        getZjOataskTodoList:"getZjOataskTodoList",
        getZjOataskDetail:"getZjOataskDetail",
        updateZjOataskState:"updateZjOataskState",
        getZjOataskStatisticAnalysisList:"getZjOataskStatisticAnalysisList",
        addZjOataskForWeChat:"addZjOataskForWeChat",
        getLoginUserInfo:"getLoginUserInfo"
    }
};
