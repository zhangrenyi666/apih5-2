window.configs = {
  dev: false,
  debugConsole: false,
  //主题 basic(默认黑色主题菜单) dev(蓝色主题菜单)
  // theme: "dev",
  //是否显示切换语言的按钮 
  canChangeLanguage: false,
  // 当前语言  "en_US", // en_US | zh_CN(默认)
  language: function () {
    var locd = localStorage.getItem('zt17_app_lw_language') || '{}';
    return JSON.parse(locd).d || "zh_CN";
  }(),
  // domain: "http://114.115.200.165:88/apiZjLzeh/",
  // domain: "http://114.115.200.165:88/apiZjLzeh/",
  domain: 'http://192.168.1.110:8080/web/',//孙成刚
  // domain: "http://apih5:8080/web/",
  // domain: 'http://192.168.1.136:8080/web/',//孙成刚
  //不配置这个地址将不会发送bug到服务器
  bugDomain: "",
  //pc端页面地址 只有在微信里面打开移动端页面时候才会跳转
  pcPageUrl: "",
  //是否开启登录 验证码 默认true
  loginFormIncVerCode: false,
  appInfo: {
    logo: "http://47.96.150.231/logo/logo.png",
    id: "zj_lzeh_demo_id",
    // id: "zj_erp",
    //正常网站标题和网页中左上角标题(leftTopTitle不存在时)
    title: "泸州市二环路高新区AB段工程项目信息化系统",
    titleUS: "framework testing",
    copyright: "泸州市二环路高新区AB段工程项目信息化系统",
    copyrightUS: "@",
    name: "",
    loginType: "1",
    //
    mainModule: "/lzeh/",
    helpHref: ""
  },
  //百度编辑器cdn地址 默认就是 下面 地址
  // ueCdn: "http://cdn.apih5.com/lib/react-ueditor/vendor/ueditor/",
  // ueCdn:"http://localhost:8000/",
  //登录页面轮播背景配置 配置后注意文件夹/src/view/login/img下是否存在对应的图片
  //地址也可以直接引用http://xxx || https://xxx
  loginPageConfig: {
    bgImgUrl: ["img1.png", "img2.png"],
    //背景图片
    rowText: ["泸州市二环路高新区AB段工程项目信息化系统"],
    //文字描述 一项即一行
    rowTextUS: ["xxxlimited company", "xxxxsystem"] //文字描述 一项即一行

  },
  //是否可以切换项目 默认true
  canChangeProject: false,
  storage: {
    timeout: 7 * 24 * 60 * 60 * 1000
  },
  wxSdk: {
    enabled: true,
    debug: false,
    jsApiList: ["downloadVoice", "downloadImage", "startRecord", "stopRecord", "uploadVoice", "playVoice", "stopVoice", "translateVoice", "chooseImage", "uploadImage", "closeWindow", "getLocation", "openLocation", "onVoiceRecordEnd", "openDefaultBrowser"] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2

  },
  apis: {
    "unLock": "user/unLock",
    // 解锁用户
    "openFlowHas": "openFlow?hasFlag=1",
    // 已办流程需要加上这个参数
    "openFlowHas": "openFlow?hasFlag=1",
    // 已办流程需要加上这个参数

    /* ----- ↓↓↓  通用接口开始 ↓↓↓ --------*/
    login: "user/login",
    //非加密登录
    // login: "user/login?WEBFlag=Ab25DB", //加密登录
    editPwd: "user/updateUserPwd",
    //改密
    resetPwd: "user/updateUserDefaultPwd",
    //强制个人重置密码接口
    resetUserPwd: "user/resetUserPwd",
    //重置密码 
    refreshAccessToken: "user/refreshAccessToken",
    //刷新token 
    upload: "upload",
    //上传接口
    getCorpInfo: "getCorpInfo",
    //获取公司信息
    getRouteData: "getRouteData",
    //获取路由数据
    getCaptchaCode: "getCaptchaCode",
    //获取登录验证码
    getSysWebBgDetails: "getSysWebBgDetails",
    //获取接口
    updateSysWebBg: "updateSysWebBg",
    // 组织机构-部门
    addSysDepartment: "addSysDepartment",
    //新增部门
    updateSysDepartment: "updateSysDepartment",
    //修改部门信息
    batchDeleteUpdateSysDepartment: "batchDeleteUpdateSysDepartment",
    //批量删除部门
    moveUpdateSysDepartment: "moveUpdateSysDepartment",
    //移动部门顺序
    // 组织机构-人员
    getSysUserListByBg: "user/getSysUserListByBg",
    // 后台人员-获取
    addSysUserInfoByBg: "user/addSysUserInfoByBg",
    // 后台人员-增加
    updateSysUserInfoByBg: "user/updateSysUserInfoByBg",
    // 后台人员-修改
    deleteSysUserInfoByBg: "user/deleteSysUserInfoByBg",
    // 后台人员-删除
    // 弹出框-组织机构人员
    getSysDepartmentAllTree: "getSysDepartmentTree",
    // 组织机构-部门-获取 
    getSysDepartmentUserAllTree: "getSysUserTree",
    // 组织机构-部门+人员-获取
    // getSysDepartmentTreeByZj  // 中交组织机构-获取【本公司】
    // getSysDepartmentTreeByZjAddOther  // 中交组织机构-获取【本公司+局】
    // getSysUserTreeByZj  // 中交人员-获取【本公司】
    // getSysUserTreeByZjAddOther  // 中交人员-获取【本公司+局】
    // 收藏人员
    appGetSysFrequentContactsList: "appGetSysFrequentContactsList",
    //获取收藏的联系人
    appAddSysFrequentContacts: "appAddSysFrequentContacts",
    //添加联系人到收藏
    appRemoveSysFrequentContacts: "appRemoveSysFrequentContacts",
    //移除收藏联系人
    //后台-菜单相关
    getSysMenuAllTree: "getSysMenuAllTree",
    //获取菜单树结构
    addSysMenu: "addSysMenu",
    //新增菜单
    updateSysMenu: "updateSysMenu",
    //修改菜单信息
    updateSysMenuDetails: "updateSysMenuDetails",
    batchDeleteUpdateSysMenu: "batchDeleteUpdateSysMenu",
    //批量删除菜单
    moveUpdateSysMenu: "moveUpdateSysMenu",
    //移动菜单顺序
    getSysMenu: "getSysMenu",
    //获取菜单详情
    //后台-权限相关
    getSysRoleAllTree: "getSysRoleAllTree",
    // 权限
    addSysRole: "addSysRole",
    // 权限
    updateSysRole: "updateSysRole",
    // 权限
    batchDeleteUpdateSysRole: "batchDeleteUpdateSysRole",
    // 权限
    getSysRoleUserList: "getSysRoleUserList",
    // 权限
    updateSysRoleUser: "updateSysRoleUser",
    // 权限
    getSysRoleMenuList: "getSysRoleMenuList",
    // 权限
    updateSysRoleMenu: "updateSysRoleMenu",
    // 权限
    //后台-流程设置相关
    getBaseFlowCodeList: "getBaseFlowCodeList",
    // 流程
    baseFlowCodeImport: "baseFlowCodeImport",
    // 流程
    batchDeleteUpdateBaseFlowCode: "batchDeleteUpdateBaseFlowCode",
    // 流程
    getBaseFlowSponsorChooserList: "getBaseFlowSponsorChooserList",
    // 流程
    getFlowNameSelectList: "getFlowNameSelectList",
    // 流程
    addBaseFlowSponsorChooserByList: "addBaseFlowSponsorChooserByList",
    // 流程
    batchDeleteUpdateBaseFlowSponsorChooser: "batchDeleteUpdateBaseFlowSponsorChooser",
    // 流程
    updateBaseFlowSponsorChooserByList: "updateBaseFlowSponsorChooserByList",
    // 流程
    getBaseFlowStartSettingListByFlow: "getBaseFlowStartSettingListByFlow",
    //获取流程追加的节点
    addBaseFlowStartSettingByFlow: "addBaseFlowStartSettingByFlow",
    //提交流程追加的节点
    //后台-流程相关
    createOpenFlow: "createOpenFlow",
    openFlow: "openFlow",
    openFlowByReport: "openFlowByReport",
    actionFlow: "actionFlow",
    getTodoList: "getTodoList",
    getHasTodoList: "getHasTodoList",
    //字典组织层级[通用]
    addBaseCode: "addBaseCode",
    updateBaseCode: "updateBaseCode",
    getBaseCodeList: "getBaseCodeList",
    batchDeleteUpdateBaseCode: "batchDeleteUpdateBaseCode",
    pcUpdateBaseCodeOnTree: "pcUpdateBaseCodeOnTree",
    //更新数据字典[在树结构上编辑]   
    pcExchangeBaseCode: "pcExchangeBaseCode",
    moveUpdateBaseCode: "moveUpdateBaseCode",
    getBaseCodeTree: "getBaseCodeTree",
    //联动下拉
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
    changeCompany: "user/changeCompany",
    //切换公司
    getZxWtPermissionListByProject: "getZxWtPermissionListByProject",
    changeZxWtProject: 'changeZxWtProject',
    //系统用户组 
    getSysUserGroupList: "getSysUserGroupList",
    addSysUserGroup: "addSysUserGroup",
    batchDeleteUpdateSysUserGroup: "batchDeleteUpdateSysUserGroup",
    updateSysUserGroup: "updateSysUserGroup",
    // 其它
    syncWeChatToSysInfo: "syncWeChatToSysInfo",
    //同步微信通讯录数据
    getBaseCodeUIConfig: 'getBaseCodeUIConfig',
    //查询组织层级前台页面配置
    getZjWoaFlowSelectDetails: "getZjWoaFlowSelectDetails",
    //流程列表下拉
    //移动人员
    moveUpdateSysUser: "user/moveUpdateSysUser",
    //获取页面按钮
    getSysMenuBtn: "getSysMenuBtn",
    //大屏维护
    //产值完成情况
    getZjLzehProductionList: "getZjLzehProductionList",
    updateZjLzehProduction: "updateZjLzehProduction",
    //钢筋超市
    getZjLzehRebarSupermarketList: "getZjLzehRebarSupermarketList",
    updateZjLzehRebarSupermarket: "updateZjLzehRebarSupermarket",
    //劳务实名制
    getZjLzehLaborRealNameList: "getZjLzehLaborRealNameList",
    updateZjLzehLaborRealName: "updateZjLzehLaborRealName",
    //宣传片维护
    getZjLzehVideoList: "getZjLzehVideoList",
    addZjLzehVideo: "addZjLzehVideo",
    updateZjLzehVideo: "updateZjLzehVideo",
    batchDeleteUpdateZjLzehVideo: "batchDeleteUpdateZjLzehVideo",
    updateZjLzehVideoValue: "updateZjLzehVideoValue",
    //安全检查统计
    getZjLzehSafetyInspectionStatisticsList: "getZjLzehSafetyInspectionStatisticsList",
    addZjLzehSafetyInspectionStatistics: "addZjLzehSafetyInspectionStatistics",
    updateZjLzehSafetyInspectionStatistics: "updateZjLzehSafetyInspectionStatistics",
    batchDeleteUpdateZjLzehSafetyInspectionStatistics: "batchDeleteUpdateZjLzehSafetyInspectionStatistics",
    //质量检查统计
    getZjLzehQualityInspectionStatisticsList: "getZjLzehQualityInspectionStatisticsList",
    addZjLzehQualityInspectionStatistics: "addZjLzehQualityInspectionStatistics",
    updateZjLzehQualityInspectionStatistics: "updateZjLzehQualityInspectionStatistics",
    batchDeleteUpdateZjLzehQualityInspectionStatistics: "batchDeleteUpdateZjLzehQualityInspectionStatistics",
    //安全检查管理
    getZjLzehSafetyInspectionManagementList: "getZjLzehSafetyInspectionManagementList",
    addZjLzehSafetyInspectionManagement: "addZjLzehSafetyInspectionManagement",
    updateZjLzehSafetyInspectionManagement: "updateZjLzehSafetyInspectionManagement",
    batchDeleteUpdateZjLzehSafetyInspectionManagement: "batchDeleteUpdateZjLzehSafetyInspectionManagement",
    //班组信息
    getZjLzehTeamInformationList: "getZjLzehTeamInformationList",
    addZjLzehTeamInformation: "addZjLzehTeamInformation",
    updateZjLzehTeamInformation: "updateZjLzehTeamInformation",
    batchDeleteUpdateZjLzehTeamInformation: "batchDeleteUpdateZjLzehTeamInformation",
    updateZjLzehTeamInformationTeam: "updateZjLzehTeamInformationTeam",
    getZjLzehTeamList: "getZjLzehTeamList",
    //组织架构新版接口
    "getSysDepartmentListByCondition": "getSysDepartmentListByCondition",
    "addSysDepartment": "addSysDepartment",
    "updateSysDepartment": "updateSysDepartment",
    "batchDeleteUpdateSysDepartment": "batchDeleteUpdateSysDepartment",

    /* ----- ↑↑↑  通用接口结束 ↑↑↑ --------*/
    // 大屏接口
    getZjLzehVideo: "getZjLzehVideo",
    getZjLzehTeamInformationList: "getZjLzehTeamInformationList",
    getZjLzehAccessToken: "getZjLzehAccessToken",
    getZjLzehSafetyInspectionManagementBySumDangerLevel: "getZjLzehSafetyInspectionManagementBySumDangerLevel",
    getZjLzehSafetyInspectionManagementBySumTroubleLevel: "getZjLzehSafetyInspectionManagementBySumTroubleLevel",
    getZjLzehSafetyInspectionManagementBySumDangerType: "getZjLzehSafetyInspectionManagementBySumDangerType",
    otherApiGetNewestFinishedData: "getZjLzehDataCenterDynamicInfo",
    getZjLzehPageDataForView: "getZjLzehPageDataForView",
    getZjLzehDataCenterDynamicInfo: "getZjLzehDataCenterDynamicInfo",
    getZjLzehWeather: "getZjLzehWeather",
    //安全质量
    getZxHwZlTroubleList: "getZxHwZlTroubleList",
    getZxHwZlTroubleDetails: "getZxHwZlTroubleDetails",
    getZxHwAqHiddenDangerList: "getZxHwAqHiddenDangerList",
    getZxHwAqHiddenDangerDetails: "getZxHwAqHiddenDangerDetails",
    exportZjLzehQualityRectificatReply: "exportZjLzehQualityRectificatReply",
    exportZjLzehQualityRectificatSheet: "exportZjLzehQualityRectificatSheet",
    exportZjLzehHiddenDangerRectificatReply: "exportZjLzehHiddenDangerRectificatReply",
    exportZjLzehHiddenDangerRectificatInstructBook: "exportZjLzehHiddenDangerRectificatInstructBook",
    // 1、质量整改回复（exportZjLzehQualityRectificatReply） 参数（troubleTitle、opinionField2、opinionField3）
    // 2、质量整改单（exportZjLzehQualityRectificatSheet） 参数（troubleTitle、troubleContent、troubleRequire）
    // 3、隐患整改回复单（exportZjLzehHiddenDangerRectificatReply） 参数（dangerTitle）
    // 4、隐患整改指令书（exportZjLzehHiddenDangerRectificatInstructBook） 参数（dangerTitle、dangerContent、dangerRequire）
    // -----计划进度管理--------------------------------------------------------------------------------------------
    // 年度
    getZjLzehYearPlanProgressList: "getZjLzehYearPlanProgressList",
    // 查询年计划进度表
    getZjLzehYearPlanProgressDetail: "getZjLzehYearPlanProgressDetail",
    // 查询详情年计划进度表
    addZjLzehYearPlanProgress: "addZjLzehYearPlanProgress",
    // 新增年计划进度表
    updateZjLzehYearPlanProgress: "updateZjLzehYearPlanProgress",
    // 更新年计划进度表
    batchDeleteUpdateZjLzehYearPlanProgress: "batchDeleteUpdateZjLzehYearPlanProgress",
    // 删除年计划进度表
    // 月度
    getZjLzehMonthPlanProgressList: "getZjLzehMonthPlanProgressList",
    // 查询月计划进度表
    getZjLzehMonthPlanProgressDetail: "getZjLzehMonthPlanProgressDetail",
    // 查询详情月计划进度表
    addZjLzehMonthPlanProgress: "addZjLzehMonthPlanProgress",
    // 新增月计划进度表
    updateZjLzehMonthPlanProgress: "updateZjLzehMonthPlanProgress",
    // 修改月计划进度表
    batchDeleteUpdateZjLzehMonthPlanProgress: "batchDeleteUpdateZjLzehMonthPlanProgress",
    // 批量删除月计划进度表
    // -----班组管理------------------------------------------------------------------------------------------------
    // 班组库
    getZjLzehTeamManagementList: "getZjLzehTeamManagementList",
    // 查询班组管理
    getZjLzehTeamManagementDetail: "getZjLzehTeamManagementDetail",
    // 查询详情班组管理
    addZjLzehTeamManagement: "addZjLzehTeamManagement",
    // 新增班组管理
    updateZjLzehTeamManagement: "updateZjLzehTeamManagement",
    // 修改班组管理
    batchDeleteUpdateZjLzehTeamManagement: "batchDeleteUpdateZjLzehTeamManagement",
    // 批量删除班组管理
    getZjLzehTeamManagementisScoreCount: "getZjLzehTeamManagementisScoreCount",
    // 查询参与评分的班组数量
    // 班组评分管理
    getZjLzehTeamScoreDetail: "getZjLzehTeamScoreDetail",
    // 查询详情班组评分管理
    addZjLzehTeamScore: "addZjLzehTeamScore",
    // 新增班组评分管理
    updateZjLzehTeamScore: "updateZjLzehTeamScore",
    // 修改班组评分管理
    batchDeleteUpdateZjLzehTeamScore: "batchDeleteUpdateZjLzehTeamScore",
    // 批量删除班组评分管理
    getZjLzehTeamScoreList: "getZjLzehTeamScoreList",
    // 查询班组评分管理
    // 班组评分明细
    getZjLzehTeamScoreItemList: "getZjLzehTeamScoreItemList",
    // 查询班组评分明细
    getZjLzehTeamScoreItemDetail: "getZjLzehTeamScoreItemDetail",
    // 查询详情班组评分明细
    addZjLzehTeamScoreItem: "addZjLzehTeamScoreItem",
    // 新增班组评分明细
    updateZjLzehTeamScoreItem: "updateZjLzehTeamScoreItem",
    // 修改班组评分明细
    getZjLzehTeamScoreItemChartList: "getZjLzehTeamScoreItemChartList",
    // 查询班组评分明细折线图
    getZjLzehTeamScoreItemgetChartByMonth: "getZjLzehTeamScoreItemgetChartByMonth",
    // 查询月班组评分明细柱状图
    getZjLzehTeamScoreItemByTeamScoreId: "getZjLzehTeamScoreItemByTeamScoreId",
    // 根据主表ID查询班组评分明细
    // getZjLzehTeamScoreItemChartList:"getZjLzehTeamScoreItemChartList", // 查询班组评分明细折线图
    // 目标任务
    // 新增
    getZjLzehManageTaskPlanList: 'getZjLzehManageTaskPlanList',
    addZjLzehManageTaskPlan: 'addZjLzehManageTaskPlan',
    updateZjLzehManageTaskPlan: 'updateZjLzehManageTaskPlan',
    batchDeleteUpdateZjLzehManageTaskPlan: 'batchDeleteUpdateZjLzehManageTaskPlan',
    // 导出
    getZjLzehManageTaskPlanItemList: 'getZjLzehManageTaskPlanItemList',
    getZjLzehManageTaskPlanItemDetail: 'getZjLzehManageTaskPlanItemDetail',
    updateZjLzehManageTaskPlanItem: 'updateZjLzehManageTaskPlanItem',
    addZjLzehManageTaskPlanItem: 'addZjLzehManageTaskPlanItem',
    importManageTaskPlanItem: 'importManageTaskPlanItem',
    batchDeleteUpdateZjLzehManageTaskPlanItem: 'batchDeleteUpdateZjLzehManageTaskPlanItem',
    // 生产目标任务
    // 新增
    addZjLzehProduceTaskPlan: 'addZjLzehProduceTaskPlan',
    getZjLzehProduceTaskPlanList: 'getZjLzehProduceTaskPlanList',
    updateZjLzehProduceTaskPlan: 'updateZjLzehProduceTaskPlan',
    batchDeleteUpdateZjLzehProduceTaskPlan: 'batchDeleteUpdateZjLzehProduceTaskPlan',
    // 导入
    getZjLzehProduceTaskPlanItemList: 'getZjLzehProduceTaskPlanItemList',
    addZjLzehProduceTaskPlanItem: 'addZjLzehProduceTaskPlanItem',
    importProduceTaskPlanItem: 'importProduceTaskPlanItem',
    // -----临时任务管理------------------------------------------------------------------------------------------------
    // - 临时任务管理
    // 创建临时任务
    getZjLzehTempTaskManageList: "getZjLzehTempTaskManageList",
    // 查询临时任务管理
    getZjLzehTempTaskManageDetail: "getZjLzehTempTaskManageDetail",
    // 查询详情临时任务管理
    updateZjLzehTempTaskManage: "updateZjLzehTempTaskManage",
    // 修改临时任务管理
    addZjLzehTempTaskManage: "addZjLzehTempTaskManage",
    // 新增临时任务管理
    batchDeleteUpdateZjLzehTempTaskManage: "batchDeleteUpdateZjLzehTempTaskManage",
    // 批量删除临时任务管理
    getZjLzehTempTaskManageTree: "getZjLzehTempTaskManageTree",
    // 查询临时任务管理树
    getZjLzehTempTaskManageTreeList: "getZjLzehTempTaskManageTreeList",
    selectZjLzehTempTaskManageListByPersonMonth: "selectZjLzehTempTaskManageListByPersonMonth",
    // 统计跳转查询
    getZjLzehTaskCensusItemChartList: "getZjLzehTaskCensusItemChartList",
    // 查询任务统计明细图表
    getTaskCensusItemChartByCenMonth: "getTaskCensusItemChartByCenMonth",
    // 接受临时任务
    // - 临时任务沟通
    getZjLzehTempTaskCommunicateList: "getZjLzehTempTaskCommunicateList",
    // 查询临时任务沟通
    getZjLzehTempTaskCommunicateDetail: "getZjLzehTempTaskCommunicateDetail",
    // 查询详情临时任务沟通
    updateZjLzehTempTaskCommunicate: "updateZjLzehTempTaskCommunicate",
    // 修改临时任务沟通
    addZjLzehTempTaskCommunicate: "addZjLzehTempTaskCommunicate",
    // 新增临时任务沟通
    batchDeleteUpdateZjLzehTempTaskCommunicate: "batchDeleteUpdateZjLzehTempTaskCommunicate",
    // 批量删除临时任务沟通
    getZjLzehTempTaskManageListByPerson: "getZjLzehTempTaskManageListByPerson",
    // -----任务统计------------------------------------------------------------------------------------------------
    getZjLzehTaskCensusList: "getZjLzehTaskCensusList",
    // 查询任务统计
    getZjLzehTaskCensusDetail: "getZjLzehTaskCensusDetail",
    // 查询详情任务统计
    updateZjLzehTaskCensus: "updateZjLzehTaskCensus",
    // 修改任务统计
    addZjLzehTaskCensus: "addZjLzehTaskCensus",
    // 新增任务统计
    batchDeleteUpdateZjLzehTaskCensus: "batchDeleteUpdateZjLzehTaskCensus",
    // 批量删除任务统计
    // -----任务统计明细------------------------------------------------------------------------------------------------
    getZjLzehTaskCensusItemList: "getZjLzehTaskCensusItemList",
    // 查询任务统计明细
    getZjLzehTaskCensusItemDetail: "getZjLzehTaskCensusItemDetail",
    // 查询详情任务统计明细
    updateZjLzehTaskCensusItem: "updateZjLzehTaskCensusItem",
    // 修改任务统计明细
    addZjLzehTaskCensusItem: "addZjLzehTaskCensusItem",
    // 新增任务统计明细
    batchDeleteUpdateZjLzehTaskCensusItem: "batchDeleteUpdateZjLzehTaskCensusItem", // 批量删除任务统计明细

    exportManageTaskPlanItem: 'exportManageTaskPlanItem',
    exportProduceTaskPlanItem: 'exportProduceTaskPlanItem',
    addZjLzehTeamInformation: "addZjLzehTeamInformation",
    updateZjLzehTeamInformation: "updateZjLzehTeamInformation",
    batchDeleteUpdateZjLzehTeamInformation: "batchDeleteUpdateZjLzehTeamInformation",
    
  },
  projectId: "zj-xm-salary"
}; //将config中的接口格式化为json输出到控制台直接复制到接口管理的输入框保存即可
// function configApisToJsonStr(){
//     console.log(JSON.stringify(window.configs.apis, null, 4))
// }
// configApisToJsonStr()