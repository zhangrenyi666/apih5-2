
window.configs = {
    dev: false,
    debugConsole: false,
    language: "zh_CN", // en_US | zh_CN(默认)

    // domain:'http://zj.bjzxkj.com/apiZjerp/',
    domain: "http://192.168.1.105:8080/web/",
    // domain: "http://172.16.0.122:9097/apiXmrz/",
    // domain: "http://apih5:8080/web/",
    // domain: "http://apih5:88/apiXmrz/",
    //不配置这个地址将不会发送bug到服务器
    bugDomain: "",

    //是否开启登录 验证码 默认true
    loginFormIncVerCode: false,

    appInfo: {
        id: 'zj_xiamengs_renzi',
        //正常网站标题和网页中左上角标题(leftTopTitle不存在时)
        title: "绩效考核系统",
        copyright: "@厦门",
        name: "",
        //code登录方式 给后台的 loginType token错误或者网址为携带code将跳转到提示页面
        // webCodeLoginType:"6",
        loginType: "1", // 1:普通账号密码；2：手机验证码快捷登录；3/32:企业号授权（也可以在app登录）；4:服务号授权；5、扫码登录  6、app登录  31、会弹出授权窗口的授权
        mainModule: "/xmrz/",
        helpHref: ""
    },
    //百度编辑器cdn地址 默认就是 下面 地址
    // ueCdn: "http://cdn.apih5.com/lib/react-ueditor/vendor/ueditor/",
    // ueCdn:"http://localhost:8000/",

    //登录页面轮播背景配置 配置后注意文件夹/src/view/login/img下是否存在对应的图片
    //地址也可以直接引用http://xxx || https://xxx
    loginPageConfig: {
        bgImgUrl: ["img1.png","img2.png"], //背景图片
        rowText: ["厦门公司绩效考核系统","固基修道   履方致远"], //文字描述 一项即一行
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
        "unLock": "user/unLock", // 解锁用户
        "openFlowHas": "openFlow?hasFlag=1", // 已办流程需要加上这个参数
        /* ----- ↓↓↓  通用接口开始 ↓↓↓ --------*/
        login: "user/login", //非加密登录
        // login: "user/login?WEBFlag=Ab25DB", //加密登录
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
        getSysRoleMenuListByRole: "getSysRoleMenuListByRole",

        // 组织机构-部门
        addSysDepartment: "addSysDepartment", //新增部门
        updateSysDepartment: "updateSysDepartment", //修改部门信息
        batchDeleteUpdateSysDepartment: "batchDeleteUpdateSysDepartment", //批量删除部门
        moveUpdateSysDepartment: "moveUpdateSysDepartment", //移动部门顺序

        // 组织机构-人员
        getSysUserListByBg: "user/getSysUserListByBg", // 后台人员-获取
        getSysUserListByBgXMJX:"user/getSysUserListByBgXMJX",
        addSysUserInfoByBg: "user/addSysUserInfoByBg", // 后台人员-增加
        updateSysUserInfoByBg: "user/updateSysUserInfoByBg", // 后台人员-修改
        deleteSysUserInfoByBg: "user/deleteSysUserInfoByBg", // 后台人员-删除


        // 弹出框-组织机构人员
        getSysDepartmentAllTree: "getSysDepartmentTreeByZjXmrz",       // 组织机构-部门-获取 
        getSysDepartmentUserAllTree: "getSysUserTreeByZjXmrz",         // 组织机构-部门+人员-获取
        // getSysDepartmentTreeByZj  // 中交组织机构-获取【本公司】
        // getSysDepartmentTreeByZjAddOther  // 中交组织机构-获取【本公司+局】
        // getSysUserTreeByZj  // 中交人员-获取【本公司】
        // getSysUserTreeByZjAddOther  // 中交人员-获取【本公司+局】
        getSysDepartmentCurrentTree:"getSysDepartmentTreeByZjXmrz",
        // getSysUserCurrentTree:"getSysUserCurrentTree",
        getSysDepartmentTree:"getSysDepartmentTreeByZjXmrz",
        getSysUserTree:"getSysUserTreeByZjXmrz",

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
        getZxQrcodePermissionObjectListByProject: "getSysDepartmentProList",
        changeZxQrcodePermissionProject: "changeSysDepartmentPro",
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

        //移动人员
        moveUpdateSysUser: "user/moveUpdateSysUser",

        //获取页面按钮
        getSysMenuBtn: "getSysMenuBtn",

        //组织架构新版接口
        "getSysDepartmentListByCondition": "getSysDepartmentListByCondition",
        "addSysDepartment": "addSysDepartment",
        "updateSysDepartment": "updateSysDepartment",
        "batchDeleteUpdateSysDepartment": "batchDeleteUpdateSysDepartment",

        //定时任务
        "getZxQrtzJobDetailsListByJoin": "getZxQrtzJobDetailsListByJoin",
        "addZxQrtzJob": "addZxQrtzJob",
        "removeZxQrtzJob": "removeZxQrtzJob",
        "pauseZxQrtzJob": "pauseZxQrtzJob",
        "resumeZxQrtzJob": "resumeZxQrtzJob",
        "getZxQrtzLogList": "getZxQrtzLogList", // addRow:"addRow",
        /* ----- ↑↑↑  通用接口结束 ↑↑↑ --------*/
        //指标库
        getZjXmJxIndexLibraryList:"getZjXmJxIndexLibraryList",
        addZjXmJxIndexLibrary:"addZjXmJxIndexLibrary",
        updateZjXmJxIndexLibrary:"updateZjXmJxIndexLibrary",
        batchDeleteUpdateZjXmJxIndexLibrary:"batchDeleteUpdateZjXmJxIndexLibrary",
        //个人指标库
        getZjXmJxUserIndexLibraryList:"getZjXmJxUserIndexLibraryList",
        batchSubmitZjXmJxUserIndexLibrary:"batchSubmitZjXmJxUserIndexLibrary",
        updateSysUserScoringLeader:"user/updateSysUserScoringLeader",
        setUpZjXmJxUserScoringLeader:"setUpZjXmJxUserScoringLeader",
        getSysDepartmentListByProjectXmrz:"getSysDepartmentListByProjectXmrz",
        //权重设置
        getZjXmJxWeightManagementList:"getZjXmJxWeightManagementList",
        addZjXmJxWeightManagement:"addZjXmJxWeightManagement",
        updateZjXmJxWeightManagement:"updateZjXmJxWeightManagement",
        batchDeleteUpdateZjXmJxWeightManagement:"batchDeleteUpdateZjXmJxWeightManagement",
        //周边考核分数规则
        getValidZjXmJxPeripheryScoreRule:"getValidZjXmJxPeripheryScoreRule",
        setUpZjXmJxPeripheryScoreRule:"setUpZjXmJxPeripheryScoreRule",
        //发起考核
        getZjXmJxMonthlyAssessmentList:"getZjXmJxMonthlyAssessmentList",
        cascadeAddZjXmJxMonthlyAssessment:"cascadeAddZjXmJxMonthlyAssessment",
        updateZjXmJxMonthlyAssessment:"updateZjXmJxMonthlyAssessment",
        getZjXmJxMonthlyAssessmentDetails:"getZjXmJxMonthlyAssessmentDetails",
        batchDeleteUpdateZjXmJxMonthlyAssessment:"batchDeleteUpdateZjXmJxMonthlyAssessment",
        getZjXmProUserListBySysUser:"getZjXmProUserListBySysUser",
        //任务考核
        getZjXmJxMonthlyAssessmentTaskDetails:"getZjXmJxMonthlyAssessmentTaskDetails",
        submitZjXmJxMonthlyAssessmentForTask:"submitZjXmJxMonthlyAssessmentForTask",
        cancelZjXmJxTaskScoreDetailedByLeader:"cancelZjXmJxTaskScoreDetailedByLeader",
        //周边考核
        submitZjXmJxMonthlyAssessmentForPeriphery:"submitZjXmJxMonthlyAssessmentForPeriphery",
        //项目正职
        getZjXmJxMonthlyAssessmentPrincipalDetails:"getZjXmJxMonthlyAssessmentPrincipalDetails",
        submitZjXmJxMonthlyAssessmentForPrincipal:"submitZjXmJxMonthlyAssessmentForPrincipal",
        //发送通知
        sendZjXmJxMonthlyAssessmentNotice:"sendZjXmJxMonthlyAssessmentNotice",
        //任务考核
        getZjXmJxAssessmentUserScoreListByTaskAuditee:"getZjXmJxAssessmentUserScoreListByTaskAuditee",
        getZjXmJxTaskScoreDetailedListByAuditee:"getZjXmJxTaskScoreDetailedListByAuditee",
        //申诉
        appealZjXmJxTaskScoreDetailedByAuditee:"appealZjXmJxTaskScoreDetailedByAuditee",
        //暂存
        tempOrConfirmZjXmJxTaskScoreDetailedByAuditee:"tempOrConfirmZjXmJxTaskScoreDetailedByAuditee",
        //申诉管理
        getZjXmJxTaskScoreDetailedListByHR:"getZjXmJxTaskScoreDetailedListByHR",
        rejectOrConfirmZjXmJxTaskScoreDetailedByHR:"rejectOrConfirmZjXmJxTaskScoreDetailedByHR",
        getZjXmJxTaskScoreDetailedRecordList:"getZjXmJxTaskScoreDetailedRecordList",
        //我要评分
        getZjXmJxAssessmentUserScoreListByReviewer:"getZjXmJxAssessmentUserScoreListByReviewer",
        getZjXmJxAssessmentUserScoreListByTaskReviewer:"getZjXmJxAssessmentUserScoreListByTaskReviewer",//任务考核
        rejectOrSubmitZjXmJxTaskScoreDetailedByLeader:"rejectOrSubmitZjXmJxTaskScoreDetailedByLeader",
        getZjXmJxPeripheryScoreDetailedListByReviewer:"getZjXmJxPeripheryScoreDetailedListByReviewer",//周边考核
        tempOrSubmitZjXmJxPeripheryScoreDetailedList:"tempOrSubmitZjXmJxPeripheryScoreDetailedList",
        getZjXmJxPrincipalScoreDetailedListByReviewer:"getZjXmJxPrincipalScoreDetailedListByReviewer",//项目正职
        tempOrSubmitZjXmJxPrincipalScoreDetailedList:"tempOrSubmitZjXmJxPrincipalScoreDetailedList",
        getZjXmJxAssessmentUserScoreListByCompositeReviewer:"getZjXmJxAssessmentUserScoreListByCompositeReviewer",//综合评价
        batchSubmitZjXmJxAssessmentUserScoreBySecretary:"batchSubmitZjXmJxAssessmentUserScoreBySecretary",
        //我的得分
        getZjXmJxAssessmentUserScoreListByAuditee:"getZjXmJxAssessmentUserScoreListByAuditee",

        //月度考核评分汇总表
        getZjXmJxMonthlyAssessmentSummaryList:"getZjXmJxMonthlyAssessmentSummaryList",

        //导出月度考核汇总末位人员页面
        exportLastZjXmJxMonthlyAssessmentSummary:"exportLastZjXmJxMonthlyAssessmentSummary",

        //导出月度考核汇总排名页面
        exportRankZjXmJxMonthlyAssessmentSummary:"exportRankZjXmJxMonthlyAssessmentSummary",

        //年度绩效考核得分汇总表
        getZjXmJxAnnualAssessmentSummaryList:"getZjXmJxAnnualAssessmentSummaryList",

        //员工得分明细表
        getZjXmJxTaskScoreDetailedTaskExcel:"getZjXmJxTaskScoreDetailedTaskExcel",
        exportZjXmJxTaskScoreDetailedTaskExcel:"exportZjXmJxTaskScoreDetailedTaskExcel",

        getZjXmJxPeripheryScoreDetailedPeripheryExcelColumn:"getZjXmJxPeripheryScoreDetailedPeripheryExcelColumn",
        getZjXmJxPeripheryScoreDetailedPeripheryExcel:"getZjXmJxPeripheryScoreDetailedPeripheryExcel",
        exportZjXmJxPeripheryScoreDetailedPeripheryExcel:"exportZjXmJxPeripheryScoreDetailedPeripheryExcel",

        getZjXmJxPrincipalScoreDetailedPrincipalExcel:"getZjXmJxPrincipalScoreDetailedPrincipalExcel",
        exportZjXmJxPrincipalScoreDetailedPrincipalExcel:"exportZjXmJxPrincipalScoreDetailedPrincipalExcel",

        getZjXmJxAssessmentUserScoreCompositeExcel:"getZjXmJxAssessmentUserScoreCompositeExcel",
        exportZjXmJxAssessmentUserScoreCompositeExcel:"exportZjXmJxAssessmentUserScoreCompositeExcel",

        //部门设置
        getZjXmJxQuarterlyAssessmentDeptList:"getZjXmJxQuarterlyAssessmentDeptList",
        addZjXmJxQuarterlyAssessmentDeptToWeight:"addZjXmJxQuarterlyAssessmentDeptToWeight",
        updateZjXmJxQuarterlyAssessmentDeptToWeight:"updateZjXmJxQuarterlyAssessmentDeptToWeight",
        batchDeleteUpdateZjXmJxQuarterlyAssessmentDeptToWeight:"batchDeleteUpdateZjXmJxQuarterlyAssessmentDeptToWeight",

        //季度考核指标库非收尾
        getZjXmJxQuarterlyIndexLibraryList:"getZjXmJxQuarterlyIndexLibraryList",
        addZjXmJxQuarterlyIndexLibrary:"addZjXmJxQuarterlyIndexLibrary",
        updateZjXmJxQuarterlyIndexLibrary:"updateZjXmJxQuarterlyIndexLibrary",
        batchDeleteUpdateZjXmJxQuarterlyIndexLibrary:"batchDeleteUpdateZjXmJxQuarterlyIndexLibrary",

        //项目季度权重非收尾
        getZjXmJxQuarterlyWeightManagementList:"getZjXmJxQuarterlyWeightManagementList",
        singleUpdateZjXmJxQuarterlyWeightManagement:"singleUpdateZjXmJxQuarterlyWeightManagement",

        //项目季度考核发起
        getZjXmJxQuarterlyAssessmentList:"getZjXmJxQuarterlyAssessmentList",
        getZjXmJxQuarterlyAssessmentDetails:"getZjXmJxQuarterlyAssessmentDetails",
        updateZjXmJxQuarterlyAssessment:"updateZjXmJxQuarterlyAssessment",
        batchDeleteUpdateZjXmJxQuarterlyAssessment:"batchDeleteUpdateZjXmJxQuarterlyAssessment",
        cascadeAddZjXmJxQuarterlyAssessment:"cascadeAddZjXmJxQuarterlyAssessment",
        getZjXmJxQuarterlyLibraryDetailsList:"getZjXmJxQuarterlyLibraryDetailsList",
        batchDeleteUpdateZjXmJxQuarterlyLibraryDetails:"batchDeleteUpdateZjXmJxQuarterlyLibraryDetails",
        batchConfirmZjXmJxQuarterlyLibraryDetails:"batchConfirmZjXmJxQuarterlyLibraryDetails",
        checkZjXmJxQuarterlyLibraryDetailsConfirmStatus:"checkZjXmJxQuarterlyLibraryDetailsConfirmStatus",
        getZjXmJxQuarterlyWeightDetailsList:"getZjXmJxQuarterlyWeightDetailsList",
        getZjXmJxQuarterlyProjectDetailsList:"getZjXmJxQuarterlyProjectDetailsList",
        batchConfirmZjXmJxQuarterlyProjectDetails:"batchConfirmZjXmJxQuarterlyProjectDetails",
        batchDeleteUpdateZjXmJxQuarterlyProjectDetails:"batchDeleteUpdateZjXmJxQuarterlyProjectDetails",
        checkZjXmJxQuarterlyProjectDetailsConfirmStatus:"checkZjXmJxQuarterlyProjectDetailsConfirmStatus",

        //季度评分
        getZjXmJxQuarterlyProjectDetailsDeptColumn:"getZjXmJxQuarterlyProjectDetailsDeptColumn",//表头
        getZjXmJxQuarterlyProjectDetailsByPersonLiable:"getZjXmJxQuarterlyProjectDetailsByPersonLiable",
        submitZjXmJxQuarterlyProjectDetailsByPersonLiable:"submitZjXmJxQuarterlyProjectDetailsByPersonLiable",
        checkZjXmJxQuarterlyProjectDetailsActualScore:"checkZjXmJxQuarterlyProjectDetailsActualScore",

        //季度评分统计
        countZjXmJxQuarterlyProjectDetailsByDept:"countZjXmJxQuarterlyProjectDetailsByDept",
        exportZjXmJxQuarterlyProjectDetailsDeptExcel:"exportZjXmJxQuarterlyProjectDetailsDeptExcel",

        //季度评分汇总
        getZjXmJxQuarterlyProjectDetailsWeightColumn:"getZjXmJxQuarterlyProjectDetailsWeightColumn",
        countZjXmJxQuarterlyProjectDetailsByWeight:"countZjXmJxQuarterlyProjectDetailsByWeight",
        exportZjXmJxQuarterlyProjectDetailsWeightExcel:"exportZjXmJxQuarterlyProjectDetailsWeightExcel"
    }
};

//将config中的接口格式化为json输出到控制台直接复制到接口管理的输入框保存即可
// function configApisToJsonStr(){
//     console.log(JSON.stringify(window.configs.apis, null, 4))
// }
// configApisToJsonStr()
