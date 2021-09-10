//qnn-table全局配置
window.qnnTableGlobalConfig = {

};

window.configs = {
    dev: false,
    debugConsole: false,
    // domain: "http://weixin.fheb.cn:99/apiwoa/",
    // domain: "http://weixin.fheb.cn:98/apifangan/",
    // domain: "http://test.apih5.com:9091/web/",
    // domain: "http://test.apih5.com:9091/web/",
    // domain: "http://localhost:8080/web/",
       domain: "http://192.168.1.132:8080/web/",
    //   domain: "http://192.168.1.105:8080/web/",
    appInfo: {
        id: "zj_qyh_woa_id",
        title: "中交一公局集团",
        copyright: "微平台",
        name: "woa",
        //webCodeLoginType:"8", //6:app登陆模式；8:缓存登录模式【将当前的值替换loginType】
        loginType: "1",//1:普通账号密码；2：手机验证码快捷登录；3:企业号授权；4:服务号授权；5、扫码登录
        mainModule: "/programmeReview/",
        helpHref: "",
        logo: "http://weixin.fheb.cn:99/icon/logo.png",
    },
    //登录页面轮播背景配置 配置后注意文件夹/src/view/login/img下是否存在对应的图片
    //地址也可以直接引用http://xxx || https://xxx
    loginPageConfig: {
        bgImgUrl: ["img1.png","img2.png"], //背景图片
        rowText: ["中交一公局集团","固基修道 履方致远"] //文字描述 一项即一行
    },
    //是否可以切换项目 默认true
    canChangeProject: true,

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
        resetUserPwd: "user/resetUserPwd", //重置密码 
        upload: "upload", //上传接口
        getCorpInfo: "getCorpInfo", //获取公司信息
        getRouteData: "getRouteData", //获取路由数据
        refreshAccessToken: "user/refreshAccessToken", //获取路由数据

        // 组织机构-部门
        addSysDepartment: "addSysDepartment", //新增部门
        updateSysDepartment: "updateSysDepartment", //修改部门信息
        batchDeleteUpdateSysDepartment: "batchDeleteUpdateSysDepartment", //批量删除部门
        moveUpdateSysDepartment: "moveUpdateSysDepartment", //移动部门顺序
        getSysUserTreeByZjAddOther: "getSysUserTreeByZjAddOther", //移动部门顺序

        // 组织机构-人员
        getSysUserListByBg: "user/getSysUserListByBg", // 后台人员-获取
        addSysUserInfoByBg: "user/addSysUserInfoByBg", // 后台人员-增加
        updateSysUserInfoByBg: "user/updateSysUserInfoByBg", // 后台人员-修改
        deleteSysUserInfoByBg: "user/deleteSysUserInfoByBg", // 后台人员-删除


        // 弹出框-组织机构人员
        getSysDepartmentAllTree: "getSysDepartmentTreeByZjAddOther",       // 组织机构-部门-获取
        getSysDepartmentUserAllTree: "getSysUserTreeByZjAddOther",         // 组织机构-部门+人员-获取
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
        openFlowByReport: "openFlowByReport",

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
		getSysWoaAddFlowList:"getSysWoaAddFlowList",
		getSysWoaFlowSelectDetail:"getSysWoaFlowSelectDetail",

        //项目获取切换等接口
        getZxQrcodePermissionObjectListByProject: "getZxQrcodePermissionObjectListByProject",
        changeZxQrcodePermissionProject: "changeZxQrcodePermissionProject",
        getZtEndPermissionListByProject: "getZtEndPermissionListByProject  ",
        changeZtEndProjectManagement: "changeZtEndProjectManagement",

        // 其它
        syncWeChatToSysInfo: "syncWeChatToSysInfo", //同步微信通讯录数据
        getBaseCodeUIConfig: 'getBaseCodeUIConfig',//查询组织层级前台页面配置
        /* ----- ↑↑↑  通用接口结束 ↑↑↑ --------*/

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
        //方案评审接口
        initZjPrProgrammeConfirmList: "initZjPrProgrammeConfirmList",//方案等级清单确认入口
        initZjPrReviewStateStatisticsList: "initZjPrReviewStateStatisticsList",//方案评审状态统计入口
        initZjPrPJudgesStatisticsList: "initZjPrPJudgesStatisticsList",//方案评审人统计入口
        initZjPrPJudgesTodoStatisticsList: "initZjPrPJudgesTodoStatisticsList",//评审人待办方案统计入口
        getZjPrProgrammeLaunchListNew: "getZjPrProgrammeLaunchListNew",//查询方案发起
        getzjPrProgrammeLaunchDetail: "getzjPrProgrammeLaunchDetail",//查询方案详情
        getZjSchemeConfirmationListBySelect: "getZjSchemeConfirmationListBySelect",//查询方案确认下拉
        getZjSchemeDetailedListSelectAllList: "getZjSchemeDetailedListSelectAllList",//查询方案确认清单下拉列表（没有四级方案）
        addZjPrProgrammeLaunchNew: "addZjPrProgrammeLaunchNew",//新增方案发起
        updateZjPrProgrammeLaunch: "updateZjPrProgrammeLaunch",//更新方案发起
        batchDeleteUpdateZjPrProgrammeLaunch: "batchDeleteUpdateZjPrProgrammeLaunch",//删除未发起方案
        getzjPrProgrammeLaunchDetailByIV: "getzjPrProgrammeLaunchDetailByIV",//查询方案详情(四级方案)
        getZjSchemeDetailedListSelectAllListOnlyIv: "getZjSchemeDetailedListSelectAllListOnlyIv",//查询方案下所有的四级方案
        addZjPrProgrammeLaunchByIVNew: "addZjPrProgrammeLaunchByIVNew",//四级方案新增
        getZjPrExpertAnonymousList: "getZjPrExpertAnonymousList",//获取专家匿名集合
        updateZjPrProgrammeExpertAnonymous: "updateZjPrProgrammeExpertAnonymous",//修改专家匿名
        getZjPrExpertAnonymousDetail: "getZjPrExpertAnonymousDetail",//获取专家匿名详情
        getZjPrExpertAnonymousListByUserList: "getZjPrExpertAnonymousListByUserList",//根据人员获取匿名
        getSchemeReviewListList: "getSchemeReviewListList",//方案评审清单统计
        getPrEvaluationInfoStatisticsList: "getPrEvaluationInfoStatisticsList",//方案评审施工方案考评信息统计
        updateZjPrEvaluationInfo: "updateZjPrEvaluationInfo",//更新方案评审考评信息
        getZjPrReviewerStatisticsList: "getZjPrReviewerStatisticsList",//查询方案评审人统计列表
        getZjSchemeConfirmationList: "getZjSchemeConfirmationList",//查询方案清单列表
        addZjSchemeConfirmationNew: "addZjSchemeConfirmationNew",//新增方案清单
        updateZjSchemeConfirmation: "updateZjSchemeConfirmation",//修改方案清单
        ZjSchemeConfirmationDetail: "ZjSchemeConfirmationDetail",//查询方案清单详情
        batchDeleteUpdateZjSchemeConfirmation: "batchDeleteUpdateZjSchemeConfirmation",//批量未发起删除方案清单
        submitToExamine: "submitToExamine",//方案清单提交审批
        getZjSchemeDetailedList: "getZjSchemeDetailedList",//根据清单ID获取方案列表
        addZjSchemeDetailedList: "addZjSchemeDetailedList",//新增清单方案
        updateZjSchemeDetailedList: "updateZjSchemeDetailedList",//修改清单方案
        batchDeleteUpdateZjSchemeDetailedList: "batchDeleteUpdateZjSchemeDetailedList",//批量删除清单方案
        getZjWoaProjectDataList: "getZjWoaProjectDataList",//获取OA项目列表（旧）
        getZjPrYgjProjectSuperviseList: "getZjPrYgjProjectSuperviseList",//获取OA项目列表（新）
        getQfProvidePageDataList: "getQfProvidePageDataList",//获取隧道局项目
        addZjSchemeConfirmationSdjNew: "addZjSchemeConfirmationSdjNew",//新增隧道局项目清单
        reviseTheScheme: "reviseTheScheme",//重新修改方案
        getZjSchemeConfirmationListByUser: "getZjSchemeConfirmationListByUser",//查询方案确认（按权限访问页面）
        updateZjSchemeConfirmation: "updateZjSchemeConfirmation",//更新方案清单确认
        addSdjSchemeDetailedList: "addSdjSchemeDetailedList",//新增隧道局方案确认清单
        getZjPrReviewerTodoStatisticsList: "getZjPrReviewerTodoStatisticsList",//查询方案评审人待办统计列表        
        getZjPrProgrammeTodoCount:"getZjPrProgrammeTodoCount",//获取个人待办数量（提醒）
        zjPrSchemeChangeToPdf:"zjPrSchemeChangeToPdf",//方案报表word转换pdf
        getZjPrHasTodoListByReviewer:"getZjPrHasTodoListByReviewer",//查询方案评审人已办列表（统计详情查看）
        getZjPrTodoListByReviewer:"getZjPrTodoListByReviewer",//查询方案评审人待办列表（统计详情查看）
        getTodoListByReport:"getTodoListByReport",//获取所有流程待办（流程后台）
        batchDeleteZjPrProgrammeLaunchFlow:"batchDeleteZjPrProgrammeLaunchFlow",//删除流程（流程后台）
        addZjPrProgrammeExpertAnonymous:"addZjPrProgrammeExpertAnonymous",//新增专家匿名
        batchDeleteUpdateZjPrProgrammeExpertAnonymous:"batchDeleteUpdateZjPrProgrammeExpertAnonymous",//删除专家匿名
        //项目获取切等接口
        changeCompany: "user/changeCompany", //切换公司

        //常用信息
        getZjPrProgrammeCommonOpinionsList:"getZjPrProgrammeCommonOpinionsList",
        updateZjPrProgrammeCommonOpinions:"updateZjPrProgrammeCommonOpinions",
        batchDeleteUpdateZjPrProgrammeCommonOpinions:"batchDeleteUpdateZjPrProgrammeCommonOpinions",
        getZjSchemeAdoptDetailedListAllList:"getZjSchemeAdoptDetailedListAllList",
        addZjPrExtemalLaunchFlow:"addZjPrExtemalLaunchFlow",
        updateZjPrExtemalLaunchFlow:"updateZjPrExtemalLaunchFlow",
        getZjPrExtemalLaunchFlowDetailByWorkId:"getZjPrExtemalLaunchFlowDetailByWorkId",
        getZjPrExtemalLaunchFlowAddLaunchList:"getZjPrExtemalLaunchFlowAddLaunchList",
        getCaptchaCode:"getCaptchaCode",
        //隧道局项目接口
        getZjPrSdjProjectSuperviseList:"getZjPrSdjProjectSuperviseList",//查询隧道局项目
        getZjPrProjectSuperviseList:"getZjPrProjectSuperviseList",//查询隧道局项目
        addZjPrSdjProjectSupervise:"addZjPrSdjProjectSupervise",//新增隧道局项目
        getZjPrSdjProjectSuperviseDetail:"getZjPrSdjProjectSuperviseDetail",//获取隧道局项目详情
        updateZjPrSdjProjectSupervise:"updateZjPrSdjProjectSupervise",//更新隧道局项目
        batchDeleteUpdateZjPrSdjProjectSupervise:"batchDeleteUpdateZjPrSdjProjectSupervise",//删除隧道局项目
        addZxHwZhBridgeProgressIfor:"addZxHwZhBridgeProgressIfor",//删除隧道局项目
        batchDeleteUpdateZjPrProgrammeExpertAnonymous:"batchDeleteUpdateZjPrProgrammeExpertAnonymous",//删除隧道局项目
        



    }
};
