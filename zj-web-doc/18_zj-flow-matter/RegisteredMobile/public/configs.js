
window.configs = {
    dev: false,
    debugConsole: false,
    domain: "http://192.168.1.223:8080/web/",

    appInfo: {
        id: "zj_qyh_woa_yiqing",

        //正常网站标题和网页中左上角标题(leftTopTitle不存在时)
        title: "新版框架测试",

        copyright: "@xxx公司",
        name: "",
        loginType: "1", // 1:普通账号密码；2：手机验证码快捷登录；3:企业号授权（也可以在app登录）；4:服务号授权；5、扫码登录  6、app登录  31、会弹出授权窗口的授权
        mainModule: "/RegisteredMobile/",
        helpHref: ""
    },
    //百度编辑器cdn地址 默认就是 下面 地址
    // ueCdn: "http://cdn.apih5.com/lib/react-ueditor/vendor/ueditor/",
    // ueCdn:"http://localhost:8000/",

    //登录页面轮播背景配置 配置后注意文件夹/src/view/login/img下是否存在对应的图片
    //地址也可以直接引用http://xxx || https://xxx
    loginPageConfig: {
        bgImgUrl: ["img1.png","img2.png"], //背景图片
        rowText: ["xxx有限公司","xxxx系统"] //文字描述 一项即一行
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
        /* ----- ↓↓↓  通用接口开始 ↓↓↓ --------*/
        login: "user/login", //登录
        editPwd: "user/updateUserPwd", //改密
        resetPwd: "user/updateUserDefaultPwd", //强制个人重置密码接口
        resetUserPwd: "user/resetUserPwd", //重置密码 
        refreshAccessToken: "user/refreshAccessToken", //刷新token 
        upload: "upload", //上传接口
        getCorpInfo: "getCorpInfo", //获取公司信息
        getRouteData: "getRouteData", //获取路由数据
        getCaptchaCode:"getCaptchaCode",//获取登录验证码
        getSysWebBgDetails:"getSysWebBgDetails",//获取接口
        updateSysWebBg:"updateSysWebBg",

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
        getSysDepartmentAllTree: "getSysDepartmentTree",       // 组织机构-部门-获取
        getSysDepartmentUserAllTree: "getSysUserTree",         // 组织机构-部门+人员-获取
        // getSysDepartmentTreeByZj  // 中交组织机构-获取【本公司】
        // getSysDepartmentTreeByZjAddOther  // 中交组织机构-获取【本公司+局】
        // getSysUserTreeByZj  // 中交人员-获取【本公司】
        // getSysUserTreeByZjAddOther  // 中交人员-获取【本公司+局】

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
        getSysUserGroupList:"getSysUserGroupList",
        addSysUserGroup:"addSysUserGroup",
        batchDeleteUpdateSysUserGroup:"batchDeleteUpdateSysUserGroup",
        updateSysUserGroup:"updateSysUserGroup",


        // 其它
        syncWeChatToSysInfo: "syncWeChatToSysInfo", //同步微信通讯录数据
        getBaseCodeUIConfig: 'getBaseCodeUIConfig',//查询组织层级前台页面配置
        getZjWoaFlowSelectDetails:"getZjWoaFlowSelectDetails", //流程列表下拉
        /* ----- ↑↑↑  通用接口结束 ↑↑↑ --------*/



        //  测试使用------------------复制请删除

        //?其他未知	
        // addZxHwTlProjectLevel:"addZxHwTlProjectLevel",
        // getZxHwHomeMobilIndex: "getZxHwHomeMobilIndex",
        // getIndexviewDetails: "getIndexviewDetails",
        // getZjFlowPartyFeeUnitList: "getZjFlowPartyFeeUnitList",
        // getZjFlowPartyFeeDetailAllList: "getZjFlowPartyFeeDetailAllList",
        // getZxQrcodeTechnicalBasisList: "getZxQrcodeSafetyEducationList?authority=1",
        // addZxQrcodeTechnicalBasis: "addZxQrcodeTechnicalBasis",
        // batchDeleteUpdateZxQrcodeTechnicalBasis: "batchDeleteUpdateZxQrcodeTechnicalBasis",
        // getSysDepartmentListByCondition: "getSysDepartmentListByCondition",
        // appGetGxProjectLevel: "appGetGxProjectLevel",
        add: "add",
        del: "del",
        update: "update",
        submit: "submit",
        // addRow:"addRow",
        // getZxQrcodeOrganizationLevelList: "getZxQrcodeOrganizationLevelList",
        // getZjSchemeDetailedListSelectAllList: "getZjSchemeDetailedListSelectAllList",
        // getZjWoaProjectDataList: "getZjWoaProjectDataList",
        // getZxHwTlProjectLevelList:"getZxHwTlProjectLevelList",
        //  测试使用----------------复制请删除
    }
};

//将config中的接口格式化为json输出到控制台直接复制到接口管理的输入框保存即可
// function configApisToJsonStr(){
//     console.log(JSON.stringify(window.configs.apis, null, 4))
// }
// configApisToJsonStr()