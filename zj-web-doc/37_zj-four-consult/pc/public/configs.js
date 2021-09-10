
window.configs = {
    dev: false,
    debugConsole: false,
    //主题 basic(默认黑色主题菜单) dev(蓝色主题菜单)
    // theme: "dev",
    language: "zh_CN", // en_US | zh_CN(默认)
    // domain: "http://weixin.fheb.cn:99/apiwoa/",
    domain: "http://192.168.1.109:8080/web/",
    // domain: "http://uat.bjzxkj.com:99/apiZt17lw/",
    // domain: "http://10.15.51.190/apiJsfw/",
    appInfo: {
        logo: "http://47.96.150.231/logo/logo.png",
        id: "zj_sj_jsfwpt_id",
        // id: "zj_sj_jsfwpt_id",
        // id: "zt17_app_lw", 
        title: "中交四公局技术服务平台",
        copyright: "@中交四公局",
        name: "",
        //code登录方式 给后台的 loginType token错误或者网址为携带code将跳转到提示页面
        webCodeLoginType:"6",
        loginType: "1", // 1:普通账号密码；2：手机验证码快捷登录；3/32:企业号授权（也可以在app登录）；4:服务号授权；5、扫码登录  6、app登录  31、会弹出授权窗口的授权
        mainModule: "/jsfw/",
        helpHref: ""
    },
    //百度编辑器cdn地址 默认就是 下面 地址
    // ueCdn: "http://cdn.apih5.com/lib/react-ueditor/vendor/ueditor/",
    // ueCdn:"http://localhost:8000/",

    //登录页面轮播背景配置 配置后注意文件夹/src/view/login/img下是否存在对应的图片
    //地址也可以直接引用http://xxx || https://xxx
    loginPageConfig: {
        bgImgUrl: ["img1.png","img2.png"], //背景图片
        rowText: ["中交四公局技术服务咨询平台","固基修道 履方致远"] //文字描述 一项即一行
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
            "onVoiceRecordEnd",
            "openDefaultBrowser"
        ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    },
    apis:{}
    // apis: {
    //       "unLock": "user/unLock", // 解锁用户
    //     "openFlowHas": "openFlow?hasFlag=1", // 已办流程需要加上这个参数
    //     /* ----- ↓↓↓  通用接口开始 ↓↓↓ --------*/
    //     login: "user/login", //登录
    //     editPwd: "user/updateUserPwd", //改密
    //     resetPwd: "user/updateUserDefaultPwd", //强制个人重置密码接口
    //     resetUserPwd: "user/resetUserPwd", //重置密码 
    //     refreshAccessToken: "user/refreshAccessToken", //刷新token 
    //     upload: "upload", //上传接口
    //     getCorpInfo: "getCorpInfo", //获取公司信息
    //     getRouteData: "getRouteData", //获取路由数据
    //     getCaptchaCode: "getCaptchaCode",//获取登录验证码
    //     getSysWebBgDetails: "getSysWebBgDetails",//获取接口
    //     updateSysWebBg: "updateSysWebBg",

    //     // 组织机构-部门
    //     addSysDepartment: "addSysDepartment", //新增部门
    //     updateSysDepartment: "updateSysDepartment", //修改部门信息
    //     batchDeleteUpdateSysDepartment: "batchDeleteUpdateSysDepartment", //批量删除部门
    //     moveUpdateSysDepartment: "moveUpdateSysDepartment", //移动部门顺序

    //     // 组织机构-人员
    //     getSysUserListByBg: "user/getSysUserListByBg", // 后台人员-获取
    //     addSysUserInfoByBg: "user/addSysUserInfoByBg", // 后台人员-增加
    //     updateSysUserInfoByBg: "user/updateSysUserInfoByBg", // 后台人员-修改
    //     deleteSysUserInfoByBg: "user/deleteSysUserInfoByBg", // 后台人员-删除


    //     // 弹出框-组织机构人员
    //     getSysDepartmentAllTree: "getSysDepartmentTree",       // 组织机构-部门-获取
    //     getSysDepartmentUserAllTree: "getSysUserTree",         // 组织机构-部门+人员-获取
    //     getSysUserTree: "getSysUserTree",
    //     // getSysDepartmentTreeByZj  // 中交组织机构-获取【本公司】
    //     // getSysDepartmentTreeByZjAddOther  // 中交组织机构-获取【本公司+局】
    //     // getSysUserTreeByZj  // 中交人员-获取【本公司】
    //     // getSysUserTreeByZjAddOther  // 中交人员-获取【本公司+局】

    //     // 收藏人员
    //     appGetSysFrequentContactsList: "appGetSysFrequentContactsList", //获取收藏的联系人
    //     appAddSysFrequentContacts: "appAddSysFrequentContacts",         //添加联系人到收藏
    //     appRemoveSysFrequentContacts: "appRemoveSysFrequentContacts",   //移除收藏联系人

    //     //后台-菜单相关
    //     getSysMenuAllTree: "getSysMenuAllTree", //获取菜单树结构
    //     addSysMenu: "addSysMenu", //新增菜单
    //     updateSysMenu: "updateSysMenu", //修改菜单信息
    //     updateSysMenuDetails: "updateSysMenuDetails",
    //     batchDeleteUpdateSysMenu: "batchDeleteUpdateSysMenu", //批量删除菜单
    //     moveUpdateSysMenu: "moveUpdateSysMenu", //移动菜单顺序
    //     getSysMenu: "getSysMenu", //获取菜单详情

    //     //后台-权限相关
    //     getSysRoleAllTree: "getSysRoleAllTree", // 权限
    //     addSysRole: "addSysRole", // 权限
    //     updateSysRole: "updateSysRole", // 权限
    //     batchDeleteUpdateSysRole: "batchDeleteUpdateSysRole", // 权限
    //     getSysRoleUserList: "getSysRoleUserList", // 权限
    //     updateSysRoleUser: "updateSysRoleUser", // 权限
    //     getSysRoleMenuList: "getSysRoleMenuList", // 权限
    //     updateSysRoleMenu: "updateSysRoleMenu", // 权限

    //     //后台-流程设置相关
    //     getBaseFlowCodeList: "getBaseFlowCodeList", // 流程
    //     baseFlowCodeImport: "baseFlowCodeImport", // 流程
    //     batchDeleteUpdateBaseFlowCode: "batchDeleteUpdateBaseFlowCode", // 流程
    //     getBaseFlowSponsorChooserList: "getBaseFlowSponsorChooserList", // 流程
    //     getFlowNameSelectList: "getFlowNameSelectList", // 流程
    //     addBaseFlowSponsorChooserByList: "addBaseFlowSponsorChooserByList", // 流程
    //     batchDeleteUpdateBaseFlowSponsorChooser: "batchDeleteUpdateBaseFlowSponsorChooser", // 流程
    //     updateBaseFlowSponsorChooserByList: "updateBaseFlowSponsorChooserByList", // 流程
    //     getBaseFlowStartSettingListByFlow: "getBaseFlowStartSettingListByFlow", //获取流程追加的节点
    //     addBaseFlowStartSettingByFlow: "addBaseFlowStartSettingByFlow", //提交流程追加的节点

    //     //后台-流程相关
    //     createOpenFlow: "createOpenFlow",
    //     openFlow: "openFlow",
    //     actionFlow: "actionFlow",
    //     // getTodoList: "getTodoList",
    //     // getHasTodoList: "getHasTodoList",
    //     getTodoList: "getZXTodoList",
    //     getHasTodoList: "getZXHasTodoList",
    //     openFlowByReport:"openFlowByReport",

    //     //字典组织层级[通用]
    //     addBaseCode: "addBaseCode",
    //     updateBaseCode: "updateBaseCode",
    //     getBaseCodeList: "getBaseCodeList",
    //     batchDeleteUpdateBaseCode: "batchDeleteUpdateBaseCode",
    //     pcUpdateBaseCodeOnTree: "pcUpdateBaseCodeOnTree", //更新数据字典[在树结构上编辑]   
    //     pcExchangeBaseCode: "pcExchangeBaseCode",
    //     moveUpdateBaseCode: "moveUpdateBaseCode",
    //     getBaseCodeTree: "getBaseCodeTree", //联动下拉
    //     getBaseCodeSelect: "getBaseCodeSelect",

    //     //获取app首页
    //     getSysMobilIndex: "getSysMobilIndex",
    //     getSysMobilIndexByData: "getSysMobilIndexByData",
    //     getSysWoaAddFlowList: "getSysWoaAddFlowList",
    //     getSysWoaFlowSelectDetail: "getSysWoaFlowSelectDetail",

    //     //项目获取切换等接口
    //     getZxQrcodePermissionObjectListByProject: "getZxQrcodePermissionObjectListByProject",
    //     changeZxQrcodePermissionProject: "changeZxQrcodePermissionProject",
    //     getZtEndPermissionListByProject: "getZtEndPermissionListByProject",
    //     changeZtEndProjectManagement: "changeZtEndProjectManagement",

    //     //项目获取切换等接口
    //     changeCompany: "user/changeCompany", //切换公司
    //     getZxWtPermissionListByProject: "getZxWtPermissionListByProject",
    //     changeZxWtProject: 'changeZxWtProject',

    //     //系统用户组 
    //     getSysUserGroupList: "getSysUserGroupList",
    //     addSysUserGroup: "addSysUserGroup",
    //     batchDeleteUpdateSysUserGroup: "batchDeleteUpdateSysUserGroup",
    //     updateSysUserGroup: "updateSysUserGroup",


    //     // 其它
    //     syncWeChatToSysInfo: "syncWeChatToSysInfo", //同步微信通讯录数据
    //     getBaseCodeUIConfig: 'getBaseCodeUIConfig',//查询组织层级前台页面配置
    //     getZjWoaFlowSelectDetails: "getZjWoaFlowSelectDetails", //流程列表下拉

    //     //获取页面按钮
    //     getSysMenuBtn: "getSysMenuBtn",

    //     /* ----- ↑↑↑  通用接口结束 ↑↑↑ --------*/
    //     //首页轮播
    //     getZjSjConsultBannerList:"getZjSjConsultBannerList",
    //     addZjSjConsultBanner:"addZjSjConsultBanner",
    //     updateZjSjConsultBanner:"updateZjSjConsultBanner",
    //     batchDeleteUpdateZjSjConsultBanner:"batchDeleteUpdateZjSjConsultBanner",

    //     //新闻通知
    //     getZjSjConsultNewsList:"getZjSjConsultNewsList",
    //     addZjSjConsultNews:"addZjSjConsultNews",
    //     updateZjSjConsultNews:"updateZjSjConsultNews",
    //     batchDeleteUpdateZjSjConsultNews:"batchDeleteUpdateZjSjConsultNews",

    //     //在线咨询
    //     getZjSjConsultOnlineConsultList:"getZjSjConsultOnlineConsultList",
    //     addZjSjConsultOnlineConsult:"addZjSjConsultOnlineConsult",
    //     updateZjSjConsultOnlineConsult:"updateZjSjConsultOnlineConsult",
    //     batchDeleteUpdateZjSjConsultOnlineConsult:"batchDeleteUpdateZjSjConsultOnlineConsult",
    //     getZjSjConsultOnlineConsultReplyRecordList:"getZjSjConsultOnlineConsultReplyRecordList",
    //     addZjSjConsultOnlineConsultReplyRecord:"addZjSjConsultOnlineConsultReplyRecord",
    //     updateZjSjConsultOnlineConsultReplyRecord:"updateZjSjConsultOnlineConsultReplyRecord",
    //     getZjSjConsultOnlineConsultDetails:"getZjSjConsultOnlineConsultDetails",
    //     addZjSjConsultOnlineConsultReadFlag:"addZjSjConsultOnlineConsultReadFlag",
    //     getZjSjConsultExpertTree:"getZjSjConsultExpertTree",
    //     batchDeleteUpdateZjSjConsultOnlineConsultReplyRecord:"batchDeleteUpdateZjSjConsultOnlineConsultReplyRecord",

    //     //技术服务
    //     getZjSjConsultTechnicalServiceFlowList:"getZjSjConsultTechnicalServiceFlowList",
    //     addZjSjConsultTechnicalServiceFlow:"addZjSjConsultTechnicalServiceFlow",
    //     updateZjSjConsultTechnicalServiceFlow:"updateZjSjConsultTechnicalServiceFlow",
    //     getZjSjConsultTechnicalServiceFlowDetails:"getZjSjConsultTechnicalServiceFlowDetails",
    //     batchDeleteUpdateZjSjConsultTechnicalServiceFlow:"batchDeleteUpdateZjSjConsultTechnicalServiceFlow",
    //     zjSjConsultApprovalTechnicalService:"zjSjConsultApprovalTechnicalService",
    //     getZjSjConsultFlowId:"getZjSjConsultFlowId",

    //     //专家列表
    //     getZjSjConsultExpertList:"getZjSjConsultExpertList",
    //     addZjSjConsultExpert:"addZjSjConsultExpert",
    //     updateZjSjConsultExpert:"updateZjSjConsultExpert",
    //     batchDeleteUpdateZjSjConsultExpert:"batchDeleteUpdateZjSjConsultExpert",
    //     importZjSjConsultExpert:"importZjSjConsultExpert",
    //     getSysDepartmentListByCondition:"getSysDepartmentListByCondition",
    //     getSysUserDetails:"user/getSysUserDetails",

    //     //科技信息
    //     getZjSjConsultScientificInformationList:"getZjSjConsultScientificInformationList",
    //     addZjSjConsultScientificInformation:"addZjSjConsultScientificInformation",
    //     updateZjSjConsultScientificInformation:"updateZjSjConsultScientificInformation",
    //     batchDeleteUpdateZjSjConsultScientificInformation:"batchDeleteUpdateZjSjConsultScientificInformation",
    //     getZjSjConsultScientificInformationDetails:"getZjSjConsultScientificInformationDetails",
    //     addZjSjConsultFavorite:"addZjSjConsultFavorite",
    //     cancelZjSjConsultFavorite:"cancelZjSjConsultFavorite",
    //     importZjSjConsultScientificInformation:"importZjSjConsultScientificInformation",
    //     //科研课题
    //     getZjSjConsultScientificResearchTopicList:"getZjSjConsultScientificResearchTopicList",
    //     addZjSjConsultScientificResearchTopic:"addZjSjConsultScientificResearchTopic",
    //     updateZjSjConsultScientificResearchTopic:"updateZjSjConsultScientificResearchTopic",
    //     batchDeleteUpdateZjSjConsultScientificResearchTopic:"batchDeleteUpdateZjSjConsultScientificResearchTopic",
    //     importZjSjConsultScientificResearchTopic:"importZjSjConsultScientificResearchTopic",
    //     exportZjSjConsultScientificResearchTopic:"exportZjSjConsultScientificResearchTopic",

    //     //统计
    //     getZjSjConsultDeptStatistics:"getZjSjConsultDeptStatistics"

    // }
};

//将config中的接口格式化为json输出到控制台直接复制到接口管理的输入框保存即可
// function configApisToJsonStr(){
//     console.log(JSON.stringify(window.configs.apis, null, 4))
// }
// configApisToJsonStr()
