window.configs = {
  dev: false,
  debugConsole: false,
  canChangeLanguage: false,
  language: "zh_CN",
  //dev主题支持自定义设置值 
  theme: "dev",
  // 测试使用的前端地址
  // http://192.168.1.57:30532/zjerp

  // 公司内请求时，将 C:\Windows\System32\drivers\etc\hosts 添加如下内容
  domain: "http://test.bjzxkj.com:32141/",
  // domain: "http://192.168.1.34:18090/",
  ntkoAddress: "http://cdn.apih5.com/ntko/zj",

  //模板下载地址
  templateAddress: "http://test.bjzxkj.com:18090/system-server/downloadFile?filePath=import/",
  bugDomain: "",
  loginFormIncVerCode: false,
  projectId: "apih5-web",
  pcPageUrl: "",
  titleUS: "",
  appInfo: {
    // ureport: 'http://192.168.1.57:30532/apiUreport/',
    ureport: 'http://test.bjzxkj.com:30532/apiUreport/',
    logo: "http://weixin.fheb.cn:99/icon/logo.png",
    id: "zj_erp",
    title: "系统升级-新",
    copyright: "",
    name: "",
    // webCodeLoginType:"6",
    loginType: "1",
    mainModule: "/zjerp/",
    helpHref: "",

    //登录服务信息
    grant_type: "password",
    client_id: "crcc-laowu",
    client_secret: "1234567",
  },
  loginPageConfig: {
    bgImgUrl: ["img1.png", "img2.png"],
    //背景图片
    rowText: ["系统升级-新", "集成系统"] //文字描述 一项即一行

  },
  // 相关下载， 存在这个数据时在头像下面将会多一个 相关下载按钮
  // 数据格式同步 qnn-table 组件数据格式
  downloads: [
    {
      name: "公文编辑控件",
      url: "http://cdn.apih5.com/ntko/zj/officecontrol/ntko.exe"
    }
  ],
  // 是否可以切换项目 默认true
  canChangeProject: true,
  // canChangeProject: false,
  // 是否有锁定项目下拉功能
  lockProject: true,
  storage: {
    timeout: 7 * 24 * 60 * 60 * 1000
  },
  wxSdk: {
    enabled: false,
    debug: false,
    jsApiList: ["downloadVoice", "downloadImage", "startRecord", "stopRecord", "uploadVoice", "playVoice", "stopVoice", "translateVoice", "chooseImage", "uploadImage", "closeWindow", "getLocation", "openLocation", "onVoiceRecordEnd", "openDefaultBrowser"] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2

  },
  apis: {
    //不用加服务名前缀直接配置即可
    login: "auth-server/oauth/token",
    refreshAccessToken: "auth-server/oauth/token",
    //获取路由数据
    getCaptchaCode: "auth-server/oauth/getCaptchaCode",

    //共通服务
    "system-server": {
      // 下面几个接口不能放到接口管理里面，不能注释。
      getSysWebBg: "getSysWebBg",
      getSysWebBgList: "getSysWebBgList",
      getSysMenuTreeByTop: "getSysMenuTreeByTop",
      getCorpInfo: "getCorpInfo",

      getSysUserTreeToListByDept: "getSysUserTreeToListByDept",
      me: "user/me",
      "unLock": "user/unLock", // 解锁用户
      /* ----- ↓↓↓  通用接口开始 ↓↓↓ --------*/
      getSysUserFlowRoleTree: "getSysUserFlowRoleTree", //流程权限左侧树结构
      getSysUserFlowLeftTree: "getSysUserFlowLeftTree", //流程权限左侧树结构
      getSysUserFlowSetUpTree: "getSysUserFlowSetUpTree", //流程权限左侧树结构
      addSysFlowUserByFlow: "addSysFlowUserByFlow", //新增流程用户选择表
      deleteUpdateSysFlowUser: "deleteUpdateSysFlowUser", //修改流程用户选择表
      ntkoUploadOffice: "upload", //修改流程用户选择表
      getSysUserTreeToListByDept: "getSysUserTreeToListByDept", // 根据部门id获取人员list
      getSysMenuTreeByTop: "getSysMenuTreeByTop", //根据部门id获取菜单数据
      getSysPermissionListByLock: "getSysPermissionListByLock", //获取锁定项目列表
      changeSysPermissionListByLock: "changeSysPermissionListByLock", //切换锁定项目
      getSysProjectDetailKey: 'getSysProjectDetailKey',

      //非加密登录
      // login: "user/login?WEBFlag=Ab25DB", //加密登录
      editPwd: "user/updateUserPwd",
      //改密
      resetPwd: "user/updateUserDefaultPwd",
      //强制个人重置密码接口
      resetUserPwd: "user/resetUserPwd",
      //重置密码 
      // refreshAccessToken: "user/refreshAccessToken",
      //刷新token 
      upload: "upload",
      ntkoUploadOffice: "upload",
      //获取公司信息
      getRouteData: "getRouteData",

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
      getSysUserTreeByZjAddOther: 'getSysUserTreeByZjAddOther',
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
      getSysDepartmentCurrentTree: "getSysDepartmentCurrentTree",
      getSysDepartmentCurrentTreeByZb: "getSysDepartmentCurrentTreeByZb",
      // 部门
      getSysDepartmentCurrentTreeAll: "getSysDepartmentCurrentTreeAll",
      // 部门
      getSysUserCurrentTree: "getSysUserCurrentTree",
      getSysUserTreeByCompany: "getSysUserTreeByCompany",
      getSysUserTreeByJu: "getSysUserTreeByJu",
      getSysUserTree: "getSysUserCurrentTree",
      //部门+人员
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
      getSysRoleMenuListByRole: "getSysRoleMenuListByRole",
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
      // 已办流程需要加上这个参数

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
      getZtEndPermissionListByProject: "getZtEndPermissionListByProject",
      changeZtEndProjectManagement: "changeZtEndProjectManagement",
      //项目获取切换等接口
      changeCompany: "user/changeCompany",

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
      //组织架构新版接口
      "getSysDepartmentListByCondition": "getSysDepartmentProList",
      "addSysDepartment": "addSysDepartment",
      "updateSysDepartment": "updateSysDepartment",
      "batchDeleteUpdateSysDepartment": "batchDeleteUpdateSysDepartment",
      //项目管理
      getSysProjectList: "getSysProjectList",
      addSysProjectByRelation: "addSysProjectByRelation",
      updateSysProjectByRelation: "updateSysProjectByRelation",
      batchDeleteUpdateSysProjectByRelation: "batchDeleteUpdateSysProjectByRelation",
      getSysPermissionListByProject: "getSysPermissionListByProjectRight",

      //移动部门顺序
      changeSysProject: "changeSysProject",

      // 报表
      getSysProjectDetail: "getSysProjectDetail", // 项目安全检查汇总表
      getSysProjectBySelect: "getSysProjectBySelect", // 
      getSysCompanyProject: "getSysCompanyProject", // 重大危险源台账（项目）
      getSysCompanyProjectList: "getSysCompanyProjectList",
      getSysCompanyInProOrPro: "getSysCompanyInProOrPro",
      getSysCompanyBySelect: "getSysCompanyBySelect", // 危险源台账（公司）
      getSysCompanyBySelectByDept: "getSysCompanyBySelectByDept", // 危险源台账（公司）
      getTodoList: "getZxTodoList",
      batchDeleteZxGcsgCtContrApplyInitiated: "batchDeleteZxGcsgCtContrApplyInitiated", // 删除待办数据
      getHasTodoList: "getZxHasTodoList",
      getZxReadList: "getZxReadList",
      getZxHasReadList: "getZxHasReadList",
      // 权限
      updateSysRoleMenu: "updateSysRoleMenu",
      //切换公司
      getZxWtPermissionListByProject: "getZxWtPermissionListByProject",
      changeZxWtProject: 'changeZxWtProject',
      "getBaseFlowUserTree": "getBaseFlowUserTree",

      //定时任务
      "getZxQrtzJobDetailsListByJoin": "getZxQrtzJobDetailsListByJoin",
      "addZxQrtzJob": "addZxQrtzJob",
      "removeZxQrtzJob": "removeZxQrtzJob",
      "pauseZxQrtzJob": "pauseZxQrtzJob",
      "resumeZxQrtzJob": "resumeZxQrtzJob",
      "getZxQrtzLogList": "getZxQrtzLogList",

      // 流程执行人
      getBaseFlowCodeTree: "getBaseFlowCodeTree", // 左侧树结构
      getBaseFlowUserList: "getBaseFlowUserList", // 右侧表格数据
      getSysUserCurrentTree: "getSysUserCurrentTree", // 拉人接口
      getBaseFlowUserListByTree: "getBaseFlowUserListByTree", // 拉人接口
      addBaseFlowUserCode: "addBaseFlowUserCode", // 保存接口
      batchDeleteUpdateBaseFlowUser: "batchDeleteUpdateBaseFlowUser", //删除接口
      getBaseFlowUserListByTree: "getBaseFlowUserListByTree", // 人员回显
      updateBaseFlowUser: 'updateBaseFlowUser', // 更新流程执行人

      // 流程条件设置
      getBaseFlowRuleList: "getBaseFlowRuleList", //列表接口
      addBaseFlowRule: "addBaseFlowRule", //新增
      batchDeleteUpdateBaseFlowRule: "batchDeleteUpdateBaseFlowRule", //删除
      getBaseFlowCodeSelect: "getBaseFlowCodeSelect", //流程下拉
      updateBaseFlowRule: "updateBaseFlowRule",
      // 启用、禁用接口
      updateBaseFlowRuleEnable: "updateBaseFlowRuleEnable",
      updateBaseFlowRuleDisable: "updateBaseFlowRuleDisable"
    },

    //业务服务
    "erp-server": {
      // 暂时走业务代码-流程相关
      createOpenFlow: "createOpenFlow",
      openFlow: "openFlow",
      deleteFlowBiz: "deleteFlowBiz",
      //提交流程追加的节点
      openFlowHas: "openFlow?hasFlag=1",
      openFlowByReport: "openFlowByReport",
      actionFlow: "actionFlow",
      // 安全事故
      getZxSfSWAccidentList: "getZxSfSWAccidentList",
      updateZxSfSWAccident: "updateZxSfSWAccident",
      batchDeleteUpdateZxSfSWAccident: "batchDeleteUpdateZxSfSWAccident",
      getZxSfSWAccidentItemList: "getZxSfSWAccidentItemList",
      batchDeleteUpdateZxSfSWAccidentItem: "batchDeleteUpdateZxSfSWAccidentItem",
      getZxSfAccidentList: "getZxSfAccidentList",
      updateZxSfAccident: "updateZxSfAccident",
      batchDeleteUpdateZxSfAccident: "batchDeleteUpdateZxSfAccident",
      getZxSfAccidentItemList: "getZxSfAccidentItemList",
      batchDeleteUpdateZxSfAccidentItem: "batchDeleteUpdateZxSfAccidentItem",
      getZxSfEAccidentList: "getZxSfEAccidentList",
      updateZxSfEAccident: "updateZxSfEAccident",
      batchDeleteUpdateZxSfEAccident: "batchDeleteUpdateZxSfEAccident",
      getZxSfEAccidentItemList: "getZxSfEAccidentItemList",
      batchDeleteUpdateZxSfEAccidentItem: "batchDeleteUpdateZxSfEAccidentItem",
      getZxSfOtherAddFileReportList: "getZxSfOtherAddFileReportList",
      addZxSfOtherAddFileReport: "addZxSfOtherAddFileReport",
      updateZxSfOtherAddFileReport: "updateZxSfOtherAddFileReport",
      batchDeleteUpdateZxSfOtherAddFileReport: "batchDeleteUpdateZxSfOtherAddFileReport",
      getZxSfOtherAddFilePressList: "getZxSfOtherAddFilePressList",
      addZxSfOtherAddFilePress: "addZxSfOtherAddFilePress",
      updateZxSfOtherAddFilePress: "updateZxSfOtherAddFilePress",
      batchDeleteUpdateZxSfOtherAddFilePress: "batchDeleteUpdateZxSfOtherAddFilePress",
      addZxSfAccident: "addZxSfAccident",
      addZxSfAccidentItem: "addZxSfAccidentItem",
      addZxSfEAccident: "addZxSfEAccident",
      addZxSfEAccidentItem: "addZxSfEAccidentItem",
      addZxSfSWAccident: "addZxSfSWAccident",

      addZxSfSWAccidentItem: "addZxSfSWAccidentItem",

      batchSaveUpdateZxSfSWAccidentItem: "batchSaveUpdateZxSfSWAccidentItem", // 船舶水上交通事故批量保存
      batchSaveUpdateZxSfAccidentItem: "batchSaveUpdateZxSfAccidentItem", // 伤亡事故情况统计批量保存
      batchSaveUpdateZxSfEAccidentItem: "batchSaveUpdateZxSfEAccidentItem", // 职工伤亡事故年度统计表批量保存

      //信用评价报表
      getZxCrProjectEvaluationReports: 'getZxCrProjectEvaluationReports',
      getZxCrHalfYearCreditEvaItemReports: 'getZxCrHalfYearCreditEvaItemReports',
      getZxCrJYearCreditEvaItemReports: 'getZxCrJYearCreditEvaItemReports',
      getOverYearsZxCrJYearCreditEvaItemReports: 'getOverYearsZxCrJYearCreditEvaItemReports',
      getZxCrAALevelCustomerReports: 'getZxCrAALevelCustomerReports',
      getABCZxCrJYearCreditEvaItemReports: 'getABCZxCrJYearCreditEvaItemReports',
      getDZxCrJYearCreditEvaItemReports: 'getDZxCrJYearCreditEvaItemReports',
      getUnCheckCreditEvaItemReports: 'getUnCheckCreditEvaItemReports',
      // 安全检查查询
      getZxSfCheckCompany: "getZxSfCheckCompany", // 公司信息安全检查查询
      getZxSfCheckComList: "getZxSfCheckComList", // 公司列表
      getZxSfCheckOrgList: "getZxSfCheckOrgList",// 项目（状态区分）安全检查查询
      getZxSfCheckGuiDangList: "getZxSfCheckGuiDangList",//归档
      getZxSfCheckJiaoGongList: "getZxSfCheckJiaoGongList",//交工
      getZxSfCheckWanGongList: "getZxSfCheckWanGongList",//完工
      getZxSfCheckKaiGongList: "getZxSfCheckKaiGongList",//开工
      // 安全费用查询
      getZxSfFeeCompany: "getZxSfFeeCompany", // 查询公司安全费用查询
      getZxSfFeeProjectProduceAmt: 'getZxSfFeeProjectProduceAmt',
      getZxSfFeeOrgList: "getZxSfFeeOrgList", // 查询项目列表安全费用查询
      getZxSfFeeGuiDangList: "getZxSfFeeGuiDangList", // 查询归档项目列表
      getZxSfFeeJiaoGongList: "getZxSfFeeJiaoGongList", // 查询交工项目列表
      getZxSfFeeWanGangList: "getZxSfFeeWanGangList", // 查询完工项目列表
      getZxSfFeeKaiGongList: "getZxSfFeeKaiGongList", // 查询开工项目列表
      getZxSfFeeCompanyList: "getZxSfFeeCompanyList", //查询公司列表安全费用查询
      getZxSfFeeJuInfo: "getZxSfFeeJuInfo",
      //安全教育培训
      getZxSfEduCom: "getZxSfEduCom",
      getZxSfEduOrgList: "getZxSfEduOrgList",
      getZxSfEduGuiDangList: "getZxSfEduGuiDangList",
      getZxSfEduJiaoGongList: "getZxSfEduJiaoGongList",
      getZxSfEduWanGongList: "getZxSfEduWanGongList",
      getZxSfEduKaiGongList: "getZxSfEduKaiGongList",
      getZxSfEduComList: "getZxSfEduComList",
      getZxSfEduKaiJuInfo: "getZxSfEduKaiJuInfo",
      // 学历
      getZxSfProEmpMainComInfo: "getZxSfProEmpMainComInfo",
      getZxSfProEmpMainOrgList: "getZxSfProEmpMainOrgList",
      getZxSfProEmpMainGuiDangList: "getZxSfProEmpMainGuiDangList",
      getZxSfProEmpMainJiaoGongList: "getZxSfProEmpMainJiaoGongList",
      getZxSfProEmpMainWanGongList: "getZxSfProEmpMainWanGongList",
      getZxSfProEmpMainKaiGongList: "getZxSfProEmpMainKaiGongList",
      getZxSfProEmpMainComList: "getZxSfProEmpMainComList",
      getZxSfProEmpMainJuInfo: "getZxSfProEmpMainJuInfo",
      //职称
      getZxSfProEmpMainZhiChengComInfo: "getZxSfProEmpMainZhiChengComInfo",
      getZxSfProEmpMainZhiChengOrgList: "getZxSfProEmpMainZhiChengOrgList",
      getZxSfProEmpMainZhiChengGuiDangList: "getZxSfProEmpMainZhiChengGuiDangList",
      getZxSfProEmpMainZhiChengJiaoGongList: "getZxSfProEmpMainZhiChengJiaoGongList",
      getZxSfProEmpMainZhiChengWanGongList: "getZxSfProEmpMainZhiChengWanGongList",
      getZxSfProEmpMainZhiChengKaiGongList: "getZxSfProEmpMainZhiChengKaiGongList",
      getZxSfProEmpMainZhiChengJuInfo: "getZxSfProEmpMainZhiChengJuInfo",
      getZxSfProEmpMainZhiChengComList: "getZxSfProEmpMainZhiChengComList",
      // 工作年限
      getZxSfProEmpMainWorkAgeComInfo: "getZxSfProEmpMainWorkAgeComInfo",
      getZxSfProEmpMainpWorkAgeOrgList: "getZxSfProEmpMainpWorkAgeOrgList",
      getZxSfProEmpMainWorkAgeGuiDangList: "getZxSfProEmpMainWorkAgeGuiDangList",
      getZxSfProEmpMainWorkAgeJiaoGongList: "getZxSfProEmpMainWorkAgeJiaoGongList",
      getZxSfProEmpMainWorkAgeWanGongList: "getZxSfProEmpMainWorkAgeWanGongList",
      getZxSfProEmpMainWorkAgeKaiGongList: "getZxSfProEmpMainWorkAgeKaiGongList",
      getZxSfProEmpMainWorkAgeJuInfo: "getZxSfProEmpMainWorkAgeJuInfo",
      getZxSfProEmpMainWorkAgeComList: "getZxSfProEmpMainWorkAgeComList",
      // 安全目标计划
      getZxSfPlanTargetComInfo: "getZxSfPlanTargetComInfo",
      getZxSfPlanTargetOrgList: "getZxSfPlanTargetOrgList",
      getZxSfPlanTargetGuiDangList: "getZxSfPlanTargetGuiDangList",
      getZxSfPlanTargetJiaoGongList: "getZxSfPlanTargetJiaoGongList",
      getZxSfPlanTargetWanGongList: "getZxSfPlanTargetWanGongList",
      getZxSfPlanTargetKaiGongList: "getZxSfPlanTargetKaiGongList",
      getZxSfPlanTargetComList: "getZxSfPlanTargetComList",

      getZxSfSpecialEmpItemDetailFormList: "getZxSfSpecialEmpItemDetailFormList", //特种作业人员统计表（项目） 
      getZxSfSpecialEmpItemDetailFormComList: "getZxSfSpecialEmpItemDetailFormComList",//特种作业人员统计表（公司）
      getZxSfEquManageItemUreportFormList: "getZxSfEquManageItemUreportFormList", // 特种设备台账
      getZxSfAccidentItemFormList: "getZxSfAccidentItemFormList", // 伤亡事故请开给你统计分析表（项目）
      getZxSfAccidentItemFormComList: "getZxSfAccidentItemFormComList",// 伤亡事故请开给你统计分析表（公司） 
      getZxSfAccidentItemUReportFormYearList: "getZxSfAccidentItemUReportFormYearList", // 企业职工伤亡事故年度统计分析表（项目）
      getZxSfAccidentItemUReportFormYearComList: "getZxSfAccidentItemUReportFormYearComList", // 企业职工伤亡事故年度统计分析表（公司）
      getZxSfSWAccidentUReportFormList: "getZxSfSWAccidentUReportFormList", // 船舶水上交通事故统计表（项目）
      getZxSfSWAccidentUReportFormComList: "getZxSfSWAccidentUReportFormComList", // 船舶水上交通事故统计表（公司）
      getZxSfEduUReportFormList: "getZxSfEduUReportFormList", // 花名册
      getZxSfProjectEmployeeFormList: "getZxSfProjectEmployeeFormList", // 安全管理人员统计表（项目）
      getZxSfProjectEmployeeFormComList: "getZxSfProjectEmployeeFormComList", // 安全管理人员统计表（公司）

      getFormZxSfCheckList: "getFormZxSfCheckList",
      getZxSfHazardRoomAttUreportFormList: "getZxSfHazardRoomAttUreportFormList",
      getZxSfHazardRoomAttUreportFormComList: "getZxSfHazardRoomAttUreportFormComList",



      /* ----- ↑↑↑  通用接口结束 ↑↑↑ --------*/


      //  业财一体化
      //  核算单位挂接
      getZxSaFiBzdwMainList: 'getZxSaFiBzdwMainList',
      getZxSaBzdwHookupBylsbzdwDwbh: 'getZxSaBzdwHookupBylsbzdwDwbh',
      getZxCtContractOrgNotInHookUp: 'getZxCtContractOrgNotInHookUp',
      addZxSaBzdwHookup: 'addZxSaBzdwHookup',
      getZxSaFiBzdwChildList: 'getZxSaFiBzdwChildList',
      batchSaveZxSaBzdwHookupBylsbzdwDwbh: 'batchSaveZxSaBzdwHookupBylsbzdwDwbh',
      batchDeleteUpdateZxSaBzdwHookup: 'batchDeleteUpdateZxSaBzdwHookup',
      // 设备租赁管理财务信息
      getZxSaFiZjxmhtbInHookup: 'getZxSaFiZjxmhtbInHookup',
      getZxSaFiWldwList: 'getZxSaFiWldwList',
      getZxSaFiZjxmhtbList: 'getZxSaFiZjxmhtbList',
      getZxSaFiBmzdList: 'getZxSaFiBmzdList',
      getZxSaEquipSettleAuditTotalAmt: 'getZxSaEquipSettleAuditTotalAmt',
      getZxSaSettleAuditInvoiceList: 'getZxSaSettleAuditInvoiceList',
      batchSaveZxSaSettleAuditInvoice: 'batchSaveZxSaSettleAuditInvoice',
      getZxSaSettleAuditDatacheckByMainId: 'getZxSaSettleAuditDatacheckByMainId',
      updateEqZxSaSettleAuditDatacheck: 'updateEqZxSaSettleAuditDatacheck',
      getZxSaSettleAuditArticleList: 'getZxSaSettleAuditArticleList',
      selectZxSaiKxxzXiaLa: 'selectZxSaiKxxzXiaLa',
      getZxSaFiQwzqzwbXialaList: 'getZxSaFiQwzqzwbXialaList',
      batchSavexSaSettleAuditArticle: 'batchSavexSaSettleAuditArticle',
      getZxSaSettleAuditMeteringList: 'getZxSaSettleAuditMeteringList',
      batchSaveZxSaSettleAuditMetering: 'batchSaveZxSaSettleAuditMetering',
      getZxSaSettleAuditCostaccountList: 'getZxSaSettleAuditCostaccountList',
      selectZxSaFiFyxmTree: 'selectZxSaFiFyxmTree',
      getZxSaFiFyxmTreeChildList: 'getZxSaFiFyxmTreeChildList',
      getZxSaFiXmlbList: 'getZxSaFiXmlbList',
      getZxSaFiHsxmListTree: 'getZxSaFiHsxmListTree',
      getZxSaFiHsxmListChildTreeList: 'getZxSaFiHsxmListChildTreeList',
      getZxSaFiCpzdXiaLaList: 'getZxSaFiCpzdXiaLaList',
      batchSaveaZxSaSettleAuditCostaccount: 'batchSaveaZxSaSettleAuditCostaccount',
      importZxSaSettleAuditInvoice: 'importZxSaSettleAuditInvoice',
      pushZxSaEquipSettleAudit: 'pushZxSaEquipSettleAudit',
      getZxSaFiOplcjdList: 'getZxSaFiOplcjdList',
      updateZxSaEquipSettleAuditById: 'updateZxSaEquipSettleAuditById',
      getZxSaEquipSettleAuditAmtAndTax: 'getZxSaEquipSettleAuditAmtAndTax',
      //物资采购财务信息
      getZxSaFiZjxmhtbInSpHookup: 'getZxSaFiZjxmhtbInSpHookup',
      updateZxCtSuppliesContract: 'updateZxCtSuppliesContract',
      updateZxCtSuppliesLeaseSettlementListById: 'updateZxCtSuppliesLeaseSettlementListById',
      //其他合同财务信息
      getZxSaFiZjxmhtbInOtherHookup: 'getZxSaFiZjxmhtbInOtherHookup',
      updateZxCtOtherManageById: 'updateZxCtOtherManageById',

      //结算单推送
      pushZxSaProjectSettleAuditToLc: "pushZxSaProjectSettleAuditToLc",
      batchUpdateGcZxSaSettleAuditInvoice: 'batchUpdateGcZxSaSettleAuditInvoice',
      batchSaveGcZxSaSettleAuditCostaccount: 'batchSaveGcZxSaSettleAuditCostaccount',
      batchSaveGcZxSaSettleAuditArticle: 'batchSaveGcZxSaSettleAuditArticle',
      batchSaveGcZxSaSettleAuditMetering: 'batchSaveGcZxSaSettleAuditMetering',
      getZxSaFiZjxmhtbInGcHookup: 'getZxSaFiZjxmhtbInGcHookup',
      importGcZxSaSettleAuditInvoice: 'importGcZxSaSettleAuditInvoice',
      pushZxSaProjectSettleAuditToLc: 'pushZxSaProjectSettleAuditToLc',
      batchUpdateSpZxSaSettleAuditInvoice: 'batchUpdateSpZxSaSettleAuditInvoice',
      importSpZxSaSettleAuditInvoice: 'importSpZxSaSettleAuditInvoice',
      batchSaveSpZxSaSettleAuditArticle: 'batchSaveSpZxSaSettleAuditArticle',
      updateSpZxSaSettleAuditDatacheck: 'updateSpZxSaSettleAuditDatacheck',
      batchSaveSpZxSaSettleAuditMetering: 'batchSaveSpZxSaSettleAuditMetering',
      batchSaveSpZxSaSettleAuditCostaccount: 'batchSaveSpZxSaSettleAuditCostaccount',
      getliesLeaseSettlementThisAmtAndTax: 'getliesLeaseSettlementThisAmtAndTax',
      pushSpZxSaEquipSettleAuditToLc: 'pushSpZxSaEquipSettleAuditToLc',
      batchSaveOtherZxSaSettleAuditArticle: 'batchSaveOtherZxSaSettleAuditArticle',
      pushSpZxSaEquipSettleAuditToLc: 'pushSpZxSaEquipSettleAuditToLc',
      //其他结算单推送
      importOtherZxSaSettleAuditInvoice: 'importOtherZxSaSettleAuditInvoice',
      batchUpdateOtherZxSaSettleAuditInvoice: 'batchUpdateOtherZxSaSettleAuditInvoice',
      getZxSaOtherSettleAuditAmtAndTax: 'getZxSaOtherSettleAuditAmtAndTax',
      updateOtherZxSaSettleAuditDatacheck: 'updateOtherZxSaSettleAuditDatacheck',
      batchSaveOtherZxSaSettleAuditMetering: 'batchSaveOtherZxSaSettleAuditMetering',
      batchSaveOtherZxSaSettleAuditCostaccount: 'batchSaveOtherZxSaSettleAuditCostaccount',
      updateZxSaOtherEquipSettleById: 'updateZxSaOtherEquipSettleById',
      pushZxSaOtherEquipSettleToLc: 'pushZxSaOtherEquipSettleToLc',
      updateZxSaSettleAuditDatacheck: 'updateZxSaSettleAuditDatacheck',

      //业财结算信息
      getGSQWJSList: 'getGSQWJSList',

      //报表-----设备来源查询
      ureportZxEqToEquipSourceQueryPageIdle: 'ureportZxEqToEquipSourceQueryPageIdle',
      ureportZxSkLimitPriceRepIdle: 'ureportZxSkLimitPriceRepIdle',
      ureportZxSkTurnoverStatTotalRpt: 'ureportZxSkTurnoverStatTotalRpt',
      getZxEqEquipListForEquipSource: 'getZxEqEquipListForEquipSource',
      //abc设备资产分布
      getZxEqEquipListForABCDDistribute: 'getZxEqEquipListForABCDDistribute',
      //设备分类查询
      getZxEqEquipListForEquipClassify: 'getZxEqEquipListForEquipClassify',
      //设备种类资产分布
      getZxEqEquipListForEquipClassifyDistribute: 'getZxEqEquipListForEquipClassifyDistribute',
      //设备资产五年趋势分析
      getZxEqEquipListForFiveYearTrend: 'getZxEqEquipListForFiveYearTrend',
      //同比分析
      getZxEqEquipListForComparedWith: 'getZxEqEquipListForComparedWith',
      //运营采集季报
      getZxEqEquipListForRunQuarterly: 'getZxEqEquipListForRunQuarterly',
      //设备购置记录
      getZxEqEquipListForMainABCDCase: 'getZxEqEquipListForMainABCDCase',
      getZxEqPurRecordList: "getZxEqPurRecordList",
      getZxEqPurRecordDetails: "getZxEqPurRecordDetails",
      addZxEqPurRecord: "addZxEqPurRecord",
      updateZxEqPurRecord: "updateZxEqPurRecord",
      getZxEqResCategoryList: "getZxEqResCategoryList",
      batchDeleteUpdateZxEqPurRecord: "batchDeleteUpdateZxEqPurRecord",
      requestNumberZxEqPurRecord: "requestNumberZxEqPurRecord",
      writeNumberZxEqPurRecord: "writeNumberZxEqPurRecord",
      auditZxEqPurRecord: "auditZxEqPurRecord",
      reverseAuditZxEqPurRecord: "reverseAuditZxEqPurRecord",
      returnZxEqPurRecord: "returnZxEqPurRecord",
      getZxCtEqContractListForEqMan: "getZxCtEqContractListForEqMan",
      //设备台账记录
      getZxEqEquipList: "getZxEqEquipList",
      getZxEqEquipDetails: "getZxEqEquipDetails",
      updateZxEqEquip: "updateZxEqEquip",
      getZxEqMoveUseOrgListForTab: "getZxEqMoveUseOrgListForTab",
      getZxEqEquipDepreciationListForTab: "getZxEqEquipDepreciationListForTab",
      getZxEqMoveUseOrgItemListForTab: "getZxEqMoveUseOrgItemListForTab",
      getZxEqMoveSupervisorListForTab: "getZxEqMoveSupervisorListForTab",
      getZxEqMoveSupervisorItemListForTab: "getZxEqMoveSupervisorItemListForTab",
      getZxEqEquipDepreciationItemListForTab: "getZxEqEquipDepreciationItemListForTab",
      unusedZxEqEquip: "unusedZxEqEquip",
      recoverZxEqEquip: "recoverZxEqEquip",
      //设备租赁限价采集
      getZxEqEquipLimitPriceList: "getZxEqEquipLimitPriceList",
      getZxEqEquipLimitPriceDetails: "getZxEqEquipLimitPriceDetails",
      addZxEqEquipLimitPrice: "addZxEqEquipLimitPrice",
      updateZxEqEquipLimitPrice: "updateZxEqEquipLimitPrice",
      batchDeleteUpdateZxEqEquipLimitPrice: "batchDeleteUpdateZxEqEquipLimitPrice",
      copyZxEqEquipLimitPrice: "copyZxEqEquipLimitPrice",
      getZxEqEquipLimitPriceApplyNo: "getZxEqEquipLimitPriceApplyNo",
      checkZxEqEquipLimitPrice: "checkZxEqEquipLimitPrice",
      //设备异动管理
      //公司内调拨
      getZxEqMoveUseOrgList: "getZxEqMoveUseOrgList",
      getZxEqMoveUseOrgDetails: "getZxEqMoveUseOrgDetails",
      addZxEqMoveUseOrg: "addZxEqMoveUseOrg",
      updateZxEqMoveUseOrg: "updateZxEqMoveUseOrg",
      outConfirmZxEqMoveUseOrg: "outConfirmZxEqMoveUseOrg",
      inConfirmZxEqMoveUseOrg: "inConfirmZxEqMoveUseOrg",
      getZxEqEquipListForMoveUseOrg: "getZxEqEquipListForMoveUseOrg",
      batchDeleteUpdateZxEqMoveUseOrg: "batchDeleteUpdateZxEqMoveUseOrg",
      //资产折旧
      getZxEqEquipDepreciationList: "getZxEqEquipDepreciationList",
      getZxEqEquipDepreciationDetails: "getZxEqEquipDepreciationDetails",
      addZxEqEquipDepreciation: "addZxEqEquipDepreciation",
      updateZxEqEquipDepreciation: "updateZxEqEquipDepreciation",
      auditZxEqEquipDepreciation: "auditZxEqEquipDepreciation",
      getZxEqEquipListForDepreciation: "getZxEqEquipListForDepreciation",
      batchDeleteUpdateZxEqEquipDepreciation: "batchDeleteUpdateZxEqEquipDepreciation",
      getZxEqEquipListForDepreciationRemove: "getZxEqEquipListForDepreciationRemove",
      //局内调拨
      getZxEqMoveSupervisorList: "getZxEqMoveSupervisorList",
      getZxEqMoveSupervisorDetails: "getZxEqMoveSupervisorDetails",
      addZxEqMoveSupervisor: "addZxEqMoveSupervisor",
      updateZxEqMoveSupervisor: "updateZxEqMoveSupervisor",
      getZxEqMoveUseOrgItemListForTab: "getZxEqMoveUseOrgItemListForTab",
      outConfirmZxEqMoveSupervisor: "outConfirmZxEqMoveSupervisor",
      inConfirmZxEqMoveSupervisor: "inConfirmZxEqMoveSupervisor",
      batchDeleteUpdateZxEqMoveSupervisor: "batchDeleteUpdateZxEqMoveSupervisor",
      batchRequestZxEqMoveSupervisorNum: "batchRequestZxEqMoveSupervisorNum",
      writeZxEqMoveSupervisorNum: "writeZxEqMoveSupervisorNum",
      //资产转让
      getZxEqAssetSellList: "getZxEqAssetSellList",
      getZxEqAssetSellDetails: "getZxEqAssetSellDetails",
      addZxEqAssetSell: "addZxEqAssetSell",
      updateZxEqAssetSell: "updateZxEqAssetSell",
      reportZxEqAssetSell: "reportZxEqAssetSell",
      auditAgreeZxEqAssetSell: "auditAgreeZxEqAssetSell",
      auditRefuseZxEqAssetSell: "auditRefuseZxEqAssetSell",
      batchDeleteUpdateZxEqAssetSell: "batchDeleteUpdateZxEqAssetSell",
      //设备报废
      getZxEqEquipScrapeList: "getZxEqEquipScrapeList",
      getZxEqEquipScrapeDetails: "getZxEqEquipScrapeDetails",
      addZxEqEquipScrape: "addZxEqEquipScrape",
      updateZxEqEquipScrape: "updateZxEqEquipScrape",
      reportZxEqEquipScrape: "reportZxEqEquipScrape",
      batchDeleteUpdateZxEqEquipScrape: "batchDeleteUpdateZxEqEquipScrape",
      agreeZxEqEquipScrape: "agreeZxEqEquipScrape",
      refuseZxEqEquipScrape: "refuseZxEqEquipScrape",
      //租赁设备管理
      getZxEqOuterEquipList: "getZxEqOuterEquipList",
      getZxEqOuterEquipDetails: "getZxEqOuterEquipDetails",
      addZxEqOuterEquip: "addZxEqOuterEquip",
      updateZxEqOuterEquip: "updateZxEqOuterEquip",
      getZxCtEqContractListForOuterEquip: "getZxCtEqContractListForOuterEquip",
      batchDeleteUpdateZxEqOuterEquip: "batchDeleteUpdateZxEqOuterEquip",
      getZxCtEqContrItemListForContractID: "getZxCtEqContrItemListForContractID",
      //设备报表管理
      ureportZxEqExternalEquipmentUsageIdle: 'ureportZxEqExternalEquipmentUsageIdle',
      //公司车辆报表
      ureportZxEqEquipListVehicleIdle: 'ureportZxEqEquipListVehicleIdle',
      //机械设备台账
      getZxEqEquipListForReport: 'getZxEqEquipListForReport',
      //公司机械设备报表
      ureportZxEqEquipListMechanicsIdle: 'ureportZxEqEquipListMechanicsIdle',
      //公司机械设备人员
      ureportZxEqEemployeeListIdle: 'ureportZxEqEemployeeListIdle',
      //主要机械设备ABC情况表
      ureportZxEqAbcMachineryIdle: 'ureportZxEqAbcMachineryIdle',
      //设备租赁限价查询列表
      ureportZxEqEquipLimitPriceVOIdle: 'ureportZxEqEquipLimitPriceVOIdle',
      //机械车辆使用情况报表(项目)
      ureportZxEqVehicleUsageProjectIdle: 'ureportZxEqVehicleUsageProjectIdle',
      //机械车辆使用情况报表(公司)
      ureportZxEqVehicleUsageCompanyIdle: 'ureportZxEqVehicleUsageCompanyIdle',
      getZxEqEWorkListForReport: 'getZxEqEWorkListForReport',
      //自有车辆配备情况统计表
      getZxEqEquipZiYouCLForm: 'getZxEqEquipZiYouCLForm',
      //租赁车辆配备情况统计表
      getZuLinCLForm: 'getZuLinCLForm',
      getZxEqOuterEquipListForCar: 'getZxEqOuterEquipListForCar',

      //物资报表管理
      ureportZxSkReceivingDynamicIdle: 'ureportZxSkReceivingDynamicIdle',
      //物资收发存汇总月报表
      ureportZxSkResMoveMonthSMPList: 'ureportZxSkResMoveMonthSMPList',
      //物资购进、消费与库存总值表
      ureportZxSkResInOutStockAllAmt: 'ureportZxSkResInOutStockAllAmt',
      //物资月度需用计划汇总表
      ureportZxPuMMReqPlanIdle: 'ureportZxPuMMReqPlanIdle',
      //项目物资出库台账
      ureportZxSkResourceOut: 'ureportZxSkResourceOut',
      //物资合同与采购对比台账
      ureportZxCtContractContrastRptIdle: 'ureportZxCtContractContrastRptIdle',
      //周转材料摊销明细账
      ureportZxSkTurnResAmo: 'ureportZxSkTurnResAmo',
      //物资收发存季度报表
      ureportZxSkStockTransferRptIdle: 'ureportZxSkStockTransferRptIdle',
      //废旧物资处理台账
      ureportZxSkWornOutListIdle: 'ureportZxSkWornOutListIdle',
      //周转材料统计表
      ureportZxSkTurnoverTotalRptIdle: 'ureportZxSkTurnoverTotalRptIdle',
      //中交物资收发存季度统计报表
      ureportZxSkStockTransferRptNewIdle: 'ureportZxSkStockTransferRptNewIdle',
      //
      batchSaveUpdateZxSfSpecialEmpItem: 'batchSaveUpdateZxSfSpecialEmpItem',

      //外租机械设备运转记录
      getZxEqRentOutEqRecordList: "getZxEqRentOutEqRecordList",
      getZxEqRentOutEqRecordDetails: "getZxEqRentOutEqRecordDetails",
      addZxEqRentOutEqRecord: "addZxEqRentOutEqRecord",
      updateZxEqRentOutEqRecord: "updateZxEqRentOutEqRecord",
      auditZxEqRentOutEqRecord: "auditZxEqRentOutEqRecord",
      importZxEqRentOutEqRecordList: "importZxEqRentOutEqRecordList",
      batchDeleteUpdateZxEqRentOutEqRecord: "batchDeleteUpdateZxEqRentOutEqRecord",
      getZxEqOuterEquipListForRecord: "getZxEqOuterEquipListForRecord",
      //自有设备管理
      getZxEqUseEquipList: "getZxEqUseEquipList",
      getZxEqUseEquipDetails: "getZxEqUseEquipDetails",
      //运转记录
      getZxEqEWorkList: "getZxEqEWorkList",
      getZxEqEWorkDetails: "getZxEqEWorkDetails",
      addZxEqEWork: "addZxEqEWork",
      updateZxEqEWork: "updateZxEqEWork",
      batchDeleteUpdateZxEqEWork: "batchDeleteUpdateZxEqEWork",
      //入场信息登记
      getZxEqUseEquipInList: "getZxEqUseEquipInList",
      getZxEqUseEquipInDetails: "getZxEqUseEquipInDetails",
      addZxEqUseEquipIn: "addZxEqUseEquipIn",
      updateZxEqUseEquipIn: "updateZxEqUseEquipIn",
      batchDeleteUpdateZxEqUseEquipIn: "batchDeleteUpdateZxEqUseEquipIn",
      //协作单位自带设备入场登记
      getZxEqCooEquipList: "getZxEqCooEquipList",
      getZxEqCooEquipDetails: "getZxEqCooEquipDetails",
      addZxEqCooEquip: "addZxEqCooEquip",
      updateZxEqCooEquip: "updateZxEqCooEquip",
      importZxEqCooEquipList: "importZxEqCooEquipList",
      batchDeleteUpdateZxEqCooEquip: "batchDeleteUpdateZxEqCooEquip",
      sbglGetZxGcsgCtContractSelect: "sbglGetZxGcsgCtContractSelect",
      //设备应验收台账(月)
      getZxEqEquipFactList: "getZxEqEquipFactList",
      getZxEqEquipFactDetails: "getZxEqEquipFactDetails",
      addZxEqEquipFact: "addZxEqEquipFact",
      updateZxEqEquipFact: "updateZxEqEquipFact",
      auditZxEqEquipFact: "auditZxEqEquipFact",
      batchDeleteUpdateZxEqEquipFact: "batchDeleteUpdateZxEqEquipFact",
      getZxEqEquipFactListForCopy: "getZxEqEquipFactListForCopy",
      //设备实际验收台账(月)
      getZxEqEquipFactRealList: "getZxEqEquipFactRealList",
      syncZxEqEquipFactReal: "syncZxEqEquipFactReal",
      addZxEqEquipFactReal: "addZxEqEquipFactReal",
      getZxEqEquipFactRealDetails: "getZxEqEquipFactRealDetails",
      updateZxEqEquipFactReal: "updateZxEqEquipFactReal",
      auditZxEqEquipFactReal: "auditZxEqEquipFactReal",
      batchDeleteUpdateZxEqEquipFactReal: "batchDeleteUpdateZxEqEquipFactReal",
      getZxEqEquipListForRealFact: "getZxEqEquipListForRealFact",
      //设备分类
      getZxEqResCategoryTree: "getZxEqResCategoryTree",
      getZxEqResCategoryDetails: "getZxEqResCategoryDetails",
      addZxEqResCategory: "addZxEqResCategory",
      updateZxEqResCategory: "updateZxEqResCategory",
      batchDeleteUpdateZxEqResCategory: "batchDeleteUpdateZxEqResCategory",
      batchStartUpdateZxEqResCategory: "batchStartUpdateZxEqResCategory",
      batchStopUpdateZxEqResCategory: "batchStopUpdateZxEqResCategory",
      getZxEqResCategoryItemList: "getZxEqResCategoryItemList",
      //设备ABC分类
      getZxEqGlobalCodeList: "getZxEqGlobalCodeList",
      getZxEqGlobalCodeDetails: "getZxEqGlobalCodeDetails",
      addZxEqGlobalCode: "addZxEqGlobalCode",
      updateZxEqGlobalCode: "updateZxEqGlobalCode",
      batchDeleteUpdateZxEqGlobalCode: "batchDeleteUpdateZxEqGlobalCode",
      //职工人数设置
      getZxEqEmployeeNumList: "getZxEqEmployeeNumList",
      getZxEqEmployeeNumDetails: "getZxEqEmployeeNumDetails",
      addZxEqEmployeeNum: "addZxEqEmployeeNum",
      updateZxEqEmployeeNum: "updateZxEqEmployeeNum",
      batchDeleteUpdateZxEqEmployeeNum: "batchDeleteUpdateZxEqEmployeeNum",
      //机械人员岗位设置
      getZxEqMachineJobsList: "getZxEqMachineJobsList",
      getZxEqMachineJobsDetails: "getZxEqMachineJobsDetails",
      addZxEqMachineJobs: "addZxEqMachineJobs",
      updateZxEqMachineJobs: "updateZxEqMachineJobs",
      batchDeleteUpdateZxEqMachineJobs: "batchDeleteUpdateZxEqMachineJobs",
      //机械设备人员
      getZxEqEProjectEmployeeList: "getZxEqEProjectEmployeeList",
      getZxEqEProjectEmployeeDetails: "getZxEqEProjectEmployeeDetails",
      addZxEqEProjectEmployee: "addZxEqEProjectEmployee",
      updateZxEqEProjectEmployee: "updateZxEqEProjectEmployee",
      batchDeleteUpdateZxEqEProjectEmployee: "batchDeleteUpdateZxEqEProjectEmployee",
      // 物资管理-物资总需用计划
      getZxSkTtReqPlanList: "getZxSkTtReqPlanList",
      addZxSkTtReqPlan: "addZxSkTtReqPlan",
      updateZxSkTtReqPlanCheckOver: "updateZxSkTtReqPlanCheckOver",
      updateZxSkTtReqPlan: "updateZxSkTtReqPlan",
      batchDeleteUpdateZxSkTtReqPlan: "batchDeleteUpdateZxSkTtReqPlan",
      // 月度
      getZxSkMmReqPlanList: "getZxSkMmReqPlanList",
      getZxSkMmReqPlanDetails: "getZxSkMmReqPlanDetails",
      addZxSkMmReqPlan: "addZxSkMmReqPlan",
      updateZxSkMmReqPlan: "updateZxSkMmReqPlan",
      batchDeleteUpdateZxSkMmReqPlan: "batchDeleteUpdateZxSkMmReqPlan",

      //新增设备汇总表
      getAddZxEqEquipTotalList: 'getAddZxEqEquipTotalList',
      //自有车辆配备情况统计表
      getZxEqEquipTotalListForCar: 'getZxEqEquipTotalListForCar',
      // 采购计划
      getZxSkMonthPurList: "getZxSkMonthPurList",
      getZxSkMonthPurDetails: "getZxSkMonthPurDetails",
      addZxSkMonthPur: "addZxSkMonthPur",
      updateZxSkMonthPur: "updateZxSkMonthPur",
      batchDeleteUpdateZxSkMonthPur: "batchDeleteUpdateZxSkMonthPur",
      // 零星采购
      getZxSkSporadicList: "getZxSkSporadicList",
      getZxSkSporadicDetails: "getZxSkSporadicDetails",
      addZxSkSporadic: "addZxSkSporadic",
      updateZxSkSporadic: "updateZxSkSporadic",
      batchDeleteUpdateZxSkSporadic: "batchDeleteUpdateZxSkSporadic",
      // 项目
      // selectZxSkTtReqPlanProject:"selectZxSkTtReqPlanProject",
      getZxEqIecmOrgList: "getZxEqIecmOrgList",
      // 分部分项
      getZxEqIecsCBSOrgId: "getZxEqIecsCBSOrgId",
      // 物资编码
      getZxSkResourceMaterialsListNameJoin: "getZxSkResourceMaterialsListNameJoin",
      // 审核
      checkZxSkTtReqPlanList: "checkZxSkTtReqPlanList",
      //材料分类
      getZxSkResCategoryMaterialsTree: "getZxSkResCategoryMaterialsTree",
      getZxSkResCategoryMaterialsList: "getZxSkResCategoryMaterialsList",
      getZxSkResCategoryMaterialsDetails: "getZxSkResCategoryMaterialsDetails",
      addZxSkResCategoryMaterials: "addZxSkResCategoryMaterials",
      updateZxSkResCategoryMaterials: "updateZxSkResCategoryMaterials",
      batchDeleteUpdateZxSkResCategoryMaterials: "batchDeleteUpdateZxSkResCategoryMaterials",
      batchStartUpdateZxSkResCategoryMaterials: "batchStartUpdateZxSkResCategoryMaterials",
      batchStopUpdateZxSkResCategoryMaterials: "batchStopUpdateZxSkResCategoryMaterials",
      //材料编码
      getZxSkResCategoryMaterialsAllResource: "getZxSkResCategoryMaterialsAllResource",
      addZxSkResourceMaterials: "addZxSkResourceMaterials",
      updateZxSkResourceMaterials: "updateZxSkResourceMaterials",
      batchDeleteUpdateZxSkResourceMaterials: "batchDeleteUpdateZxSkResourceMaterials",
      batchStartUpdateZxSkResourceMaterials: "batchStartUpdateZxSkResourceMaterials",
      batchStopUpdateZxSkResourceMaterials: "batchStopUpdateZxSkResourceMaterials",
      //分部分项和内部单位
      getZxEqIecsCBSList: "getZxEqIecsCBSList",
      getZxEqIecsCBSDetails: "getZxEqIecsCBSDetails",
      addZxEqIecsCBS: "addZxEqIecsCBS",
      updateZxEqIecsCBS: "updateZxEqIecsCBS",
      batchDeleteUpdateZxEqIecsCBS: "batchDeleteUpdateZxEqIecsCBS",
      getZxEqIecsCBSTree: "getZxEqIecsCBSTree",
      //仓库设置
      getZxSkWarehouseList: "getZxSkWarehouseList",
      getZxSkWarehouseDetails: "getZxSkWarehouseDetails",
      addZxSkWarehouse: "addZxSkWarehouse",
      updateZxSkWarehouse: "updateZxSkWarehouse",
      batchDeleteUpdateZxSkWarehouse: "batchDeleteUpdateZxSkWarehouse",
      //物资编码查询
      getZxSkResourceMaterialsList: "getZxSkResourceMaterialsList",
      // 限价数据采集
      getZxSkLimitPriceList: "getZxSkLimitPriceList",
      getZxSkLimitPriceDetails: "getZxSkLimitPriceDetails",
      addZxSkLimitPrice: "addZxSkLimitPrice",
      updateZxSkLimitPrice: "updateZxSkLimitPrice",
      batchDeleteUpdateZxSkLimitPrice: "batchDeleteUpdateZxSkLimitPrice",
      getZxSkResCategoryMaterialsListResource: "getZxSkResCategoryMaterialsListResource",
      getZxSkResourceMaterialsListNameJoinResource: "getZxSkResourceMaterialsListNameJoinResource",
      getZxSkResCategoryMaterialsAll: "getZxSkResCategoryMaterialsAll",
      getZxSkLimitPriceBase: "getZxSkLimitPriceBase",
      // 调整
      getZxSkLimitPriceAdjustList: "getZxSkLimitPriceAdjustList",
      getZxSkLimitPriceAdjustDetails: "getZxSkLimitPriceAdjustDetails",
      addZxSkLimitPriceAdjust: "addZxSkLimitPriceAdjust",
      updateZxSkLimitPriceAdjust: "updateZxSkLimitPriceAdjust",
      batchDeleteUpdateZxSkLimitPriceAdjust: "batchDeleteUpdateZxSkLimitPriceAdjust",
      getZxSkLimitPriceList: "getZxSkLimitPriceList",
      getZxSkLimitPriceAdjustNo: "getZxSkLimitPriceAdjustNo",
      // 预收初始单
      getZxSkStockTransferInitialReceiptList: "getZxSkStockTransferInitialReceiptList",
      getZxSkStockTransferInitialReceiptDetails: "getZxSkStockTransferInitialReceiptDetails",
      addZxSkStockTransferInitialReceipt: "addZxSkStockTransferInitialReceipt",
      updateZxSkStockTransferInitialReceipt: "updateZxSkStockTransferInitialReceipt",
      batchDeleteUpdateZxSkStockTransferInitialReceipt: "batchDeleteUpdateZxSkStockTransferInitialReceipt",
      // 审核
      checkZxSkStockTransferInitialReceipt: "checkZxSkStockTransferInitialReceipt",
      // 仓库
      getZxSkWarehouseList: "getZxSkWarehouseList",
      // 获取单据编号
      getZxSkStockTransferReceivingNo: "getZxSkStockTransferReceivingNo",
      // 供货单位
      getZxEqIecrCustomerNewList: "getZxEqIecrCustomerNewList",
      // 收料单
      getZxSkStockTransferReceivingList: "getZxSkStockTransferReceivingList",
      getZxSkStockTransferReceivingDetails: "getZxSkStockTransferReceivingDetails",
      addZxSkStockTransferReceiving: "addZxSkStockTransferReceiving",
      updateZxSkStockTransferReceiving: "updateZxSkStockTransferReceiving",
      batchDeleteUpdateZxSkStockTransferReceiving: "batchDeleteUpdateZxSkStockTransferReceiving",
      checkZxSkStockTransferReceiving: "checkZxSkStockTransferReceiving",
      getZxSkStockDataList: "getZxSkStockDataList",
      //国家设置
      getZxCtGlobalCodeList: "getZxCtGlobalCodeList",
      getZxCtGlobalCodeDetails: "getZxCtGlobalCodeDetails",
      addZxCtGlobalCode: "addZxCtGlobalCode",
      updateZxCtGlobalCode: "updateZxCtGlobalCode",
      batchDeleteUpdateZxCtGlobalCode: "batchDeleteUpdateZxCtGlobalCode",
      //分包标准工序库
      getZxCtContrProcessTree: "getZxCtContrProcessTree",
      getZxCtContrProcessList: "getZxCtContrProcessList",
      getZxCtContrProcessItemList: "getZxCtContrProcessItemList",
      getZxCtContrProcessDetails: "getZxCtContrProcessDetails",
      addZxCtContrProcess: "addZxCtContrProcess",
      updateZxCtContrProcess: "updateZxCtContrProcess",
      batchDeleteUpdateZxCtContrProcess: "batchDeleteUpdateZxCtContrProcess",
      //分包计价规则库 
      getZxCtValuationRulesList: "getZxCtValuationRulesList",
      getZxCtValuationRulesDetails: "getZxCtValuationRulesDetails",
      addZxCtValuationRules: "addZxCtValuationRules",
      updateZxCtValuationRules: "updateZxCtValuationRules",
      batchDeleteUpdateZxCtValuationRules: "batchDeleteUpdateZxCtValuationRules",
      //标准工序库
      getZxCtSZProcessTree: "getZxCtSZProcessTree",
      getZxCtSZProcessList: "getZxCtSZProcessList",
      getZxCtSZProcessItemList: "getZxCtSZProcessItemList",
      getZxCtSZProcessDetails: "getZxCtSZProcessDetails",
      addZxCtSZProcess: "addZxCtSZProcess",
      updateZxCtSZProcess: "updateZxCtSZProcess",
      batchDeleteUpdateZxCtSZProcess: "batchDeleteUpdateZxCtSZProcess",
      //计价规则库
      getZxCtValuationSZRulesList: "getZxCtValuationSZRulesList",
      getZxCtValuationSZRulesDetails: "getZxCtValuationSZRulesDetails",
      addZxCtValuationSZRules: "addZxCtValuationSZRules",
      updateZxCtValuationSZRules: "updateZxCtValuationSZRules",
      batchDeleteUpdateZxCtValuationSZRules: "batchDeleteUpdateZxCtValuationSZRules",
      //云电商分包定标数据
      getZxCtCloudEcoList: "getZxCtCloudEcoList",
      getZxCtCloudEcoDetails: "getZxCtCloudEcoDetails",
      addZxCtCloudEco: "addZxCtCloudEco",
      updateZxCtCloudEco: "updateZxCtCloudEco",
      batchDeleteUpdateZxCtCloudEco: "batchDeleteUpdateZxCtCloudEco",
      batchInputZxCtCloudEco: "batchInputZxCtCloudEco",
      //项目业务过滤
      getZxCmFilterOrgList: "getZxCmFilterOrgList",
      getZxCmFilterOrgDetail: "getZxCmFilterOrgDetail",
      updateZxCmFilterOrg: "updateZxCmFilterOrg",
      addZxCmFilterOrg: "addZxCmFilterOrg",
      batchDeleteUpdateZxCmFilterOrg: "batchDeleteUpdateZxCmFilterOrg",
      //业主合同台账
      getZxCtContractList: "getZxCtContractList",
      getZxCtContractDetail: "getZxCtContractDetail",
      addZxCtContract: "addZxCtContract",
      updateZxCtContract: "updateZxCtContract",
      batchDeleteUpdateZxCtContract: "batchDeleteUpdateZxCtContract",
      updateZxCtContractContrStatus: "updateZxCtContractContrStatus",
      //业主合同清单
      getZxCtWorksWorkNameTree: "getZxCtWorksWorkNameTree",
      getZxCtWorksTreeList: "getZxCtWorksTreeList",
      getZxCtWorkBookList: "getZxCtWorkBookList",
      zxCtContractQuantity: "zxCtContractQuantity",
      zxCtVerificationQuantity: "zxCtVerificationQuantity",
      updateZxCtWorksList: "updateZxCtWorksList",
      saveZxCtWorksList: "saveZxCtWorksList",
      batchDeleteUpdateZxCtWorks: "batchDeleteUpdateZxCtWorks",
      importZxCtWorks: "importZxCtWorks",
      getZxCtWorkAlterSingleList: "getZxCtWorkAlterSingleList",
      getZxCtWorksDetail: "getZxCtWorksDetail",
      addZxCtWorksChange: "addZxCtWorksChange",
      addZxCtWorkAlterSingle: "addZxCtWorkAlterSingle",
      zxCtCancelWorkAlterSingle: "zxCtCancelWorkAlterSingle",
      //合同变更
      getZxCtAlterList: "getZxCtAlterList",
      getZxCtAlterDetail: "getZxCtAlterDetail",
      addZxCtAlter: "addZxCtAlter",
      updateZxCtAlter: "updateZxCtAlter",
      batchDeleteUpdateZxCtAlter: "batchDeleteUpdateZxCtAlter",
      zxCtAlterAudit: "zxCtAlterAudit",
      zxCtAlterReporting: "zxCtAlterReporting",
      //索赔
      getZxCtContrClaimList: "getZxCtContrClaimList",
      getZxCtContrClaimDetail: "getZxCtContrClaimDetail",
      addZxCtContrClaim: "addZxCtContrClaim",
      updateZxCtContrClaim: "updateZxCtContrClaim",
      batchDeleteUpdateZxCtContrClaim: "batchDeleteUpdateZxCtContrClaim",
      //产值
      getZxCtProduceAmtCalList: "getZxCtProduceAmtCalList",
      getZxCtProduceAmtCalDetail: "getZxCtProduceAmtCalDetail",
      addZxCtProduceAmtCal: "addZxCtProduceAmtCal",
      updateZxCtProduceAmtCal: "updateZxCtProduceAmtCal",
      batchDeleteUpdateZxCtProduceAmtCal: "batchDeleteUpdateZxCtProduceAmtCal",
      //计量
      getZxCtBalanceList: "getZxCtBalanceList",
      getZxCtBalanceDetail: "getZxCtBalanceDetail",
      addZxCtBalance: "addZxCtBalance",
      updateZxCtBalance: "updateZxCtBalance",
      batchDeleteUpdateZxCtBalance: "batchDeleteUpdateZxCtBalance",
      //清单计量
      getZxCtWorksBalanceList: "getZxCtWorksBalanceList",
      //支付项计量
      getZxStMiddleItemBalanceList: "getZxStMiddleItemBalanceList",
      updateZxStMiddleItemBalance: "updateZxStMiddleItemBalance",
      //清单计量编辑
      getZxCtWorksBalanceEditList: "getZxCtWorksBalanceEditList",
      updateZxCtWorksBalanceList: "updateZxCtWorksBalanceList",
      //暂定金、计日工计量
      getZxCtDayworkList: "getZxCtDayworkList",
      addZxCtDaywork: "addZxCtDaywork",
      updateZxCtDaywork: "updateZxCtDaywork",
      batchDeleteUpdateZxCtDaywork: "batchDeleteUpdateZxCtDaywork",
      //信用评价
      getScoreStatistics: 'getScoreStatistics',
      projectEvaluationScoreUpdateBath: 'projectEvaluationScoreUpdateBath',
      projectEvaluationBadUpdateBath: 'projectEvaluationBadUpdateBath',
      getZxCtContrEvaluateList: "getZxCtContrEvaluateList",
      getZxCtContrEvaluateDetail: "getZxCtContrEvaluateDetail",
      addZxCtContrEvaluate: "addZxCtContrEvaluate",
      updateZxCtContrEvaluate: "updateZxCtContrEvaluate",
      batchDeleteUpdateZxCtContrEvaluate: "batchDeleteUpdateZxCtContrEvaluate",
      //对外经营合同评审
      //物资管理合同
      getZxCtSuppliesContrApplyList: "getZxCtSuppliesContrApplyList",
      getZxCtSuppliesContrApplyDetail: "getZxCtSuppliesContrApplyDetail",
      addZxCtSuppliesContrApply: "addZxCtSuppliesContrApply",
      updateZxCtSuppliesContrApply: "updateZxCtSuppliesContrApply",
      batchDeleteUpdateZxCtSuppliesContrApplyFlow: "batchDeleteUpdateZxCtSuppliesContrApplyFlow",
      getZxCtSuppliesContrNoByCode: "getZxCtSuppliesContrNoByCode",
      //采购类
      getZxCtSuppliesContrApplyShopListList: "getZxCtSuppliesContrApplyShopListList",
      getZxCtSuppliesContrApplyShopListDetail: "getZxCtSuppliesContrApplyShopListDetail",
      addZxCtSuppliesContrApplyShopList: "addZxCtSuppliesContrApplyShopList",
      updateZxCtSuppliesContrApplyShopListByApplyId: "updateZxCtSuppliesContrApplyShopListByApplyId",
      batchDeleteUpdateZxCtSuppliesContrApplyShopList: "batchDeleteUpdateZxCtSuppliesContrApplyShopList",
      batchDeleteZxCtSuppliesContrApplyShopListByApplyId: "batchDeleteZxCtSuppliesContrApplyShopListByApplyId",
      //租赁类
      getZxCtSuppliesContrApplyLeaseListList: "getZxCtSuppliesContrApplyLeaseListList",
      getZxCtSuppliesContrApplyLeaseListDetail: "getZxCtSuppliesContrApplyLeaseListDetail",
      addZxCtSuppliesContrApplyLeaseList: "addZxCtSuppliesContrApplyLeaseList",
      updateZxCtSuppliesContrApplyLeaseListByApplyId: "updateZxCtSuppliesContrApplyLeaseListByApplyId",
      batchDeleteUpdateZxCtSuppliesContrApplyLeaseList: "batchDeleteUpdateZxCtSuppliesContrApplyLeaseList",
      //物资补充协议
      getZxCtSuppliesContrReplenishAgreementListByContrId: "getZxCtSuppliesContrReplenishAgreementListByContrId",
      getZxCtSuppliesContrReplenishAgreementDetail: "getZxCtSuppliesContrReplenishAgreementDetail",
      updateZxCtSuppliesContrReplenishAgreementByContrId: "updateZxCtSuppliesContrReplenishAgreementByContrId",
      addZxCtSuppliesContrReplenishAgreementByContrId: "addZxCtSuppliesContrReplenishAgreementByContrId",
      batchDeleteUpdateZxCtSuppliesContrReplenishAgreement: "batchDeleteUpdateZxCtSuppliesContrReplenishAgreement",
      getZxCtSuppliesContrReplenishLeaseDetailList: "getZxCtSuppliesContrReplenishLeaseDetailList",
      getZxCtSuppliesContrReplenishShopDetailList: "getZxCtSuppliesContrReplenishShopDetailList",
      addZxCtSupContrReplLeaseDetailBycontrAlterID: "addZxCtSupContrReplLeaseDetailBycontrAlterID",
      updateZxCtSupContrReplLeaseDetailBycontrAlterID: "updateZxCtSupContrReplLeaseDetailBycontrAlterID",
      getZxCtSuppliesContrReplenishAgreementList: "getZxCtSuppliesContrReplenishAgreementList",
      updateZxCtSuppliesContrReplenishAgreement: "updateZxCtSuppliesContrReplenishAgreement",
      getZxCtSuppliesContractListAddAgreementNum: "getZxCtSuppliesContractListAddAgreementNum",
      //租赁类
      getZxCtSuppliesContrReplenishLeaseListList: "getZxCtSuppliesContrReplenishLeaseListList",
      getZxCtSuppliesContrReplenishLeaseListDetail: "getZxCtSuppliesContrReplenishLeaseListDetail",
      updateZxCtSuppliesContrReplenishLeaseList: "updateZxCtSuppliesContrReplenishLeaseList",
      addZxCtSuppliesContrApplyLeaseListByApplyId: "addZxCtSuppliesContrApplyLeaseListByApplyId",
      batchDeleteUpdateZxCtSuppliesContrReplenishLeaseList: "batchDeleteUpdateZxCtSuppliesContrReplenishLeaseList",
      getZxCtSuppliesContrLeaseListList: "getZxCtSuppliesContrLeaseListList",
      //采购类
      getZxCtSuppliesContrReplenishShopListList: "getZxCtSuppliesContrReplenishShopListList",
      getZxCtSuppliesContrReplenishShopListDetail: "getZxCtSuppliesContrReplenishShopListDetail",
      updateZxCtSuppliesContrReplenishShopList: "updateZxCtSuppliesContrReplenishShopList",
      addZxCtSuppliesContrApplyShopListByApplyId: "addZxCtSuppliesContrApplyShopListByApplyId",
      batchDeleteUpdateZxCtSuppliesContrReplenishShopList: "batchDeleteUpdateZxCtSuppliesContrReplenishShopList",
      getZxCtSuppliesContrShopListList: "getZxCtSuppliesContrShopListList",
      getZxCtSuppliesContractListByOrgId: "getZxCtSuppliesContractListByOrgId",
      //对外经营合同管理
      //物资合同管理
      getZxCtSuppliesContractDetail: "getZxCtSuppliesContractDetail",
      addZxCtSuppliesContract: "addZxCtSuppliesContract",
      updateZxCtSuppliesContract: "updateZxCtSuppliesContract",
      updateZxCtSuppliesContractByContId: "updateZxCtSuppliesContractByContId",
      batchDeleteUpdateZxCtSuppliesContract: "batchDeleteUpdateZxCtSuppliesContract",
      batchAddZxCtSuppliesContrApplyShopList: "batchAddZxCtSuppliesContrApplyShopList",
      batchAddZxCtSuppliesContrApplyLeaseList: "batchAddZxCtSuppliesContrApplyLeaseList",
      batchAddZxCtSuppliesMarginRatioByConID: "batchAddZxCtSuppliesMarginRatioByConID",
      //物资合同管理保证金设置
      getZxCtSuppliesMarginRatioList: "getZxCtSuppliesMarginRatioList",
      addZxCtSuppliesMarginRatio: "addZxCtSuppliesMarginRatio",
      updateZxCtSuppliesMarginRatio: "updateZxCtSuppliesMarginRatio",
      batchDeleteUpdateZxCtSuppliesMarginRatio: "batchDeleteUpdateZxCtSuppliesMarginRatio",
      // 领料
      getZxSkStockTransferMaterialRequisitionList: "getZxSkStockTransferMaterialRequisitionList",
      getZxSkStockTransferMaterialRequisitionDetails: "getZxSkStockTransferMaterialRequisitionDetails",
      addZxSkStockTransferMaterialRequisition: "addZxSkStockTransferMaterialRequisition",
      updateZxSkStockTransferMaterialRequisition: "updateZxSkStockTransferMaterialRequisition",
      batchDeleteUpdateZxSkStockTransferMaterialRequisition: "batchDeleteUpdateZxSkStockTransferMaterialRequisition",
      checkZxSkStockTransferMaterialRequisition: "checkZxSkStockTransferMaterialRequisition",
      getZxSkStockTransferMaterialRequisitionNo: "getZxSkStockTransferMaterialRequisitionNo",
      getZxEqIecsCBSList: "getZxEqIecsCBSList",
      getZxSkStockResCategoryDataList: "getZxSkStockResCategoryDataList",
      getZxSkStockList: "getZxSkStockList",
      // 退库单
      getZxSkStockTransferWithdrawalList: "getZxSkStockTransferWithdrawalList",
      getZxSkStockTransferWithdrawalDetails: "getZxSkStockTransferWithdrawalDetails",
      addZxSkStockTransferWithdrawal: "addZxSkStockTransferWithdrawal",
      updateZxSkStockTransferWithdrawal: "updateZxSkStockTransferWithdrawal",
      batchDeleteUpdateZxSkStockTransferWithdrawal: "batchDeleteUpdateZxSkStockTransferWithdrawal",
      checkZxSkStockTransferWithdrawal: "checkZxSkStockTransferWithdrawal",
      getZxSkStockTransferWithdrawalNo: "getZxSkStockTransferWithdrawalNo",
      getZxSkStockTransferMaterialRequisitionInOrgName: "getZxSkStockTransferMaterialRequisitionInOrgName",
      getZxSkStockTransferMaterialRequisitionCbsName: "getZxSkStockTransferMaterialRequisitionCbsName",
      getZxSkStockTransferMaterialRequisitionResName: "getZxSkStockTransferMaterialRequisitionResName",
      //合同管理-对外经营合同评审-设备合同管理
      generateZxCtEqContrApplyContractNo: 'generateZxCtEqContrApplyContractNo',
      batchSaveUpdateZxCtEqContrItem: 'batchSaveUpdateZxCtEqContrItem',
      //获取甲方
      getZxCtEqContrItemDetail: 'getZxCtEqContrItemDetail',
      getZxCtEqContrApplyDetail: 'getZxCtEqContrApplyDetail',
      getZxCtEqContrApplyList: 'getZxCtEqContrApplyList',
      addZxCtEqContrApply: 'addZxCtEqContrApply',
      updateZxCtEqContrApply: 'updateZxCtEqContrApply',
      batchDeleteUpdateZxCtEqContrApply: 'batchDeleteUpdateZxCtEqContrApply',
      getZxCtEqContrItemList: 'getZxCtEqContrItemList',
      updateZxCtEqContrItem: 'updateZxCtEqContrItem',
      addZxCtEqContrItem: 'addZxCtEqContrItem',
      batchDeleteUpdateZxCtEqContrItem: 'batchDeleteUpdateZxCtEqContrItem',
      getZxCtEqContrItemForLimitPriceList: 'getZxCtEqContrItemForLimitPriceList',
      //合同管理-对外经营合同评审-附属合同
      batchUpdateZxCtFsSideAgreementInventory: 'batchUpdateZxCtFsSideAgreementInventory',
      getZxCtFsContractNo: 'getZxCtFsContractNo',
      getZxCtFsContractReviewDetail: 'getZxCtFsContractReviewDetail',
      exportZxCtFsContractReview: 'exportZxCtFsContractReview',
      getZxCtContractListByStatus: 'getZxCtContractListByStatus',
      getZxBuBudgetBookDifference: 'getZxBuBudgetBookDifference',
      getZxBuBudgetBookBudgetAmt: 'getZxBuBudgetBookBudgetAmt',
      //查询甲方名称
      getZxCrCustomerExtAttrListAll: 'getZxCrCustomerExtAttrListAll',
      //查询乙方名称
      getZxCtFsContractReviewList: 'getZxCtFsContractReviewList',
      addZxCtFsContractReview: 'addZxCtFsContractReview',
      updateZxCtFsContractReview: 'updateZxCtFsContractReview',
      batchDeleteUpdateZxCtFsContractReview: 'batchDeleteUpdateZxCtFsContractReview',
      getZxCtFsContractReviewDetailList: 'getZxCtFsContractReviewDetailList',
      updateZxCtFsContractReviewDetail: 'updateZxCtFsContractReviewDetail',
      addZxCtFsContractReviewDetail: 'addZxCtFsContractReviewDetail',
      batchDeleteUpdateZxCtFsContractReviewDetail: 'batchDeleteUpdateZxCtFsContractReviewDetail',
      batchUpdateZxCtFsContractReviewandDetail: 'batchUpdateZxCtFsContractReviewandDetail',
      batchUpdateContractBond: 'batchUpdateContractBond',
      //合同管理-对外经营合同管理-设备合同管理
      recoverZxCtEqContract: 'recoverZxCtEqContract',
      batchSaveUpdateZxCtEqCoContractAmtRate: 'batchSaveUpdateZxCtEqCoContractAmtRate',
      //恢复执行
      getZxCtEqContractList: 'getZxCtEqContractList',
      getZxCtEqContractDetail: 'getZxCtEqContractDetail',
      updateZxCtEqContract: 'updateZxCtEqContract',
      batchDeleteUpdateZxCtEqContract: 'batchDeleteUpdateZxCtEqContract',
      getZxCtEqContrItemListForContract: 'getZxCtEqContrItemListForContract',
      getZxCtEqContrSupplyListBycontractID: 'getZxCtEqContrSupplyListBycontractID',
      //合同管理- 设备合同管理 - 保证金
      batchDeleteUpdateZxCtEqCoContractAmtRate: 'batchDeleteUpdateZxCtEqCoContractAmtRate',
      getZxCtEqCoContractAmtRateList: 'getZxCtEqCoContractAmtRateList',
      addZxCtEqCoContractAmtRate: 'addZxCtEqCoContractAmtRate',
      updateZxCtEqCoContractAmtRate: "updateZxCtEqCoContractAmtRate",
      //合同管理-补充协议-设备合同补充协议
      generateZxCtEqContrSupplyContractNo: 'generateZxCtEqContrSupplyContractNo',
      batchSaveUpdateZxCtOtherSupplyAgreementWorks: 'batchSaveUpdateZxCtOtherSupplyAgreementWorks',
      //获取补充协议编号
      batchSaveUpdateZxCtEqContrAlterItemResoure: 'batchSaveUpdateZxCtEqContrAlterItemResoure',
      getZxCtEqContrSupplyDetail: 'getZxCtEqContrSupplyDetail',
      getZxCtEqContrSupplyList: 'getZxCtEqContrSupplyList',
      updateZxCtEqContrSupply: 'updateZxCtEqContrSupply',
      updateZxCtEqContrSupplyForContractTab: 'updateZxCtEqContrSupplyForContractTab',
      addZxCtEqContrSupply: 'addZxCtEqContrSupply',
      batchDeleteUpdateZxCtEqContrSupply: 'batchDeleteUpdateZxCtEqContrSupply',
      getZxCtEqContrAlterItemResoureList: 'getZxCtEqContrAlterItemResoureList',
      updateZxCtEqContrAlterItemResoure: 'updateZxCtEqContrAlterItemResoure',
      addZxCtEqContrAlterItemResoure: 'addZxCtEqContrAlterItemResoure',
      batchDeleteUpdateZxCtEqContrAlterItemResoure: 'batchDeleteUpdateZxCtEqContrAlterItemResoure',
      getZxCtFsContractListForAgrement: 'getZxCtFsContractListForAgrement',
      //合同管理-对外经营合同管理-附属合同
      getZxCtFsContractDetail: 'getZxCtFsContractDetail',
      getZxCtFsContractList: 'getZxCtFsContractList',
      updateZxCtFsContract: 'updateZxCtFsContract',
      addZxCtFsContract: 'addZxCtFsContract',
      batchDeleteUpdateZxCtFsContract: 'batchDeleteUpdateZxCtFsContract',
      getAllZxCtFsReviewDetailList: 'getAllZxCtFsReviewDetailList',
      //合同管理-补充协议-附属合同
      //列表
      getContractNo: 'getContractNo',
      getZxCtFsSideAgreementDetail: 'getZxCtFsSideAgreementDetail',
      getZxCtFsSideAgreementList: 'getZxCtFsSideAgreementList',
      updateZxCtFsSideAgreement: 'updateZxCtFsSideAgreement',
      addZxCtFsSideAgreement: 'addZxCtFsSideAgreement',
      batchDeleteUpdateZxCtFsSideAgreement: 'batchDeleteUpdateZxCtFsSideAgreement',
      //清单
      getAllReviewDetailListByContract: 'getAllReviewDetailListByContract',
      getZxCtFsSideAgreementInventoryList: 'getZxCtFsSideAgreementInventoryList',
      updateZxCtFsSideAgreementInventory: 'updateZxCtFsSideAgreementInventory',
      addZxCtFsSideAgreementInventory: 'addZxCtFsSideAgreementInventory',
      batchDeleteUpdateZxCtFsSideAgreementInventory: 'batchDeleteUpdateZxCtFsSideAgreementInventory',
      //保证金
      getZxCtFsContractBondList: 'getZxCtFsContractBondList',
      updateZxCtFsContractBond: 'updateZxCtFsContractBond',
      addZxCtFsContractBond: 'addZxCtFsContractBond',
      batchDeleteUpdateZxCtFsContractBond: 'batchDeleteUpdateZxCtFsContractBond',
      //合同管理-对外经营合同评审-其他合同
      getZxCtOtherContractNo: 'getZxCtOtherContractNo',
      batchAddUpdateZxCtOtherWorks: 'batchAddUpdateZxCtOtherWorks',
      batchSaveUpdateZxCtOtherManageAmtRate: 'batchSaveUpdateZxCtOtherManageAmtRate',
      //合同编号生成
      getZxCtOtherDetail: 'getZxCtOtherDetail',
      zxCtOtherReviewApply: "zxCtOtherReviewApply",
      getZxCtOtherList: "getZxCtOtherList",
      updateZxCtOther: "updateZxCtOther",
      addZxCtOther: "addZxCtOther",
      batchDeleteUpdateZxCtOther: "batchDeleteUpdateZxCtOther",
      getZxCtOtherWorksList: "getZxCtOtherWorksList",
      updateZxCtOtherWorks: "updateZxCtOtherWorks",
      addZxCtOtherWorks: "addZxCtOtherWorks",
      batchDeleteUpdateZxCtOtherWorks: "batchDeleteUpdateZxCtOtherWorks",
      //项目管理-施组评审-施组方案审批-施组审批发起
      getZxScGroupSchemeList: 'getZxScGroupSchemeList',
      updateZxScGroupScheme: 'updateZxScGroupScheme',
      addZxScGroupScheme: 'addZxScGroupScheme',
      batchDeleteUpdateZxScGroupScheme: 'batchDeleteUpdateZxScGroupScheme',
      getZxScGroupSchemeSequence: 'getZxScGroupSchemeSequence',
      //合同管理-对外经营合同管理-其他合同
      getZxCtOtherManageDetail: 'getZxCtOtherManageDetail',
      zxCtOtherManageRegainExecute: 'zxCtOtherManageRegainExecute',
      //恢复执行
      getZxCtOtherManageList: 'getZxCtOtherManageList',
      updateZxCtOtherManage: 'updateZxCtOtherManage',
      addZxCtOtherManage: 'addZxCtOtherManage',
      batchDeleteUpdateZxCtOtherManage: 'batchDeleteUpdateZxCtOtherManage',
      getZxCtOtherManageAmtRateList: "getZxCtOtherManageAmtRateList",
      //合同管理-对外经营合同管理-其他合同-保证金比例
      getZxCtOtherManageAmtRateList: 'getZxCtOtherManageAmtRateList',
      updateZxCtOtherManageAmtRate: 'updateZxCtOtherManageAmtRate',
      addZxCtOtherManageAmtRate: 'addZxCtOtherManageAmtRate',
      batchDeleteUpdateZxCtOtherManageAmtRate: 'batchDeleteUpdateZxCtOtherManageAmtRate',
      //合同管理-补充协议-其它类补充协议
      getZxCtOtherSupplyAgreementDetail: 'getZxCtOtherSupplyAgreementDetail',
      getZxCtOtherSupplyAgreementContractNo: 'getZxCtOtherSupplyAgreementContractNo',
      //查询合同编号
      getZxCtOtherSupplyAgreementList: 'getZxCtOtherSupplyAgreementList',
      updateZxCtOtherSupplyAgreement: 'updateZxCtOtherSupplyAgreement',
      addZxCtOtherSupplyAgreement: 'addZxCtOtherSupplyAgreement',
      batchDeleteUpdateZxCtOtherSupplyAgreement: 'batchDeleteUpdateZxCtOtherSupplyAgreement',
      getZxCtOtherSupplyAgreementWorksList: 'getZxCtOtherSupplyAgreementWorksList',
      updateZxCtOtherSupplyAgreementWorks: 'updateZxCtOtherSupplyAgreementWorks',
      addZxCtOtherSupplyAgreementWorks: 'addZxCtOtherSupplyAgreementWorks',
      batchDeleteUpdateZxCtOtherSupplyAgreementWorks: 'batchDeleteUpdateZxCtOtherSupplyAgreementWorks',
      zxCtOtherSupplyAgreementReviewApply: 'zxCtOtherSupplyAgreementReviewApply',
      zxSaOtherEquipSettleReviewApply: 'zxSaOtherEquipSettleReviewApply',
      //结算管理- 关联完工审核
      getZxSaProjectUnFinishList: 'getZxSaProjectUnFinishList',
      //结算管理-工程类类结算
      zxSaProjectSettleAuditSubmitFile: 'zxSaProjectSettleAuditSubmitFile',
      getZxSaProjectWorkSettleItemList: 'getZxSaProjectWorkSettleItemList',
      exportZxSaProjectSettleAuditCountersignature: 'exportZxSaProjectSettleAuditCountersignature',//回签单下载链接获取
      exportZxSaProjectSettleAuditItemList: 'exportZxSaProjectSettleAuditItemList',
      exportZxSaProjectSettleAuditGcjsjsb: 'exportZxSaProjectSettleAuditGcjsjsb',
      exportZxSaProjectSettleAuditGcjsjsbDyb: 'exportZxSaProjectSettleAuditGcjsjsbDyb',
      exportZxSaProjectSettleAuditGcjsjsdYgz: 'exportZxSaProjectSettleAuditGcjsjsdYgz',
      getZxSaProjectSettleAuditYgzInfo: 'getZxSaProjectSettleAuditYgzInfo',//营改增
      exportZxSaProjectSettleAuditYgzInfo: 'exportZxSaProjectSettleAuditYgzInfo',//营改增导出
      getZxSaProjectSettleAuditList: 'getZxSaProjectSettleAuditList',
      updateZxSaProjectSettleAudit: 'updateZxSaProjectSettleAudit',
      addZxSaProjectSettleAudit: 'addZxSaProjectSettleAudit',
      batchDeleteUpdateZxSaProjectSettleAudit: 'batchDeleteUpdateZxSaProjectSettleAudit',
      getZxSaProjectSettleAuditProjectList: 'getZxSaProjectSettleAuditProjectList', //项目名称获取
      getZxSaProjectSettleAuditContractNoList: 'getZxSaProjectSettleAuditContractNoList', //合同编号获取
      getZxSaProjectSettleAuditDetail: 'getZxSaProjectSettleAuditDetail',
      batchSaveOrUpdateZxSaProjectWorkSettleItem: 'batchSaveOrUpdateZxSaProjectWorkSettleItem',
      batchSaveOrUpdateZxSaProjectPaySettleItem: 'batchSaveOrUpdateZxSaProjectPaySettleItem',
      //结算管理-工程类类结算-统计项
      getZxSaProjectSettleAuditItemList: 'getZxSaProjectSettleAuditItemList',
      updateZxSaProjectSettleAuditItem: 'updateZxSaProjectSettleAuditItem',
      //结算管理-工程类类结算-清单明细
      updateZxSaProjectWorkSettleItem: 'updateZxSaProjectWorkSettleItem',
      //结算管理-工程类类结算-支付项明细
      getZxSaProjectPaySettleItemList: 'getZxSaProjectPaySettleItemList',
      updateZxSaProjectPaySettleItem: 'updateZxSaProjectPaySettleItem',
      addZxSaProjectPaySettleItem: 'addZxSaProjectPaySettleItem',
      batchDeleteUpdateZxSaProjectPaySettleItem: 'batchDeleteUpdateZxSaProjectPaySettleItem',
      //结算管理-其它类结算
      getUreportZxSaOtherEquipSettleList: 'getUreportZxSaOtherEquipSettleList',
      getZxSaOtherEquipSettleDetail: 'getZxSaOtherEquipSettleDetail',
      getZxSaOtherEquipSettleBillNo: 'getZxSaOtherEquipSettleBillNo',
      getZxSaOtherEquipSettleList: 'getZxSaOtherEquipSettleList',
      updateZxSaOtherEquipSettle: 'updateZxSaOtherEquipSettle',
      addZxSaOtherEquipSettle: 'addZxSaOtherEquipSettle',
      batchDeleteUpdateZxSaOtherEquipSettle: 'batchDeleteUpdateZxSaOtherEquipSettle',
      getZxSaOtherEquipSettleContractNo: 'getZxSaOtherEquipSettleContractNo',
      //结算管理-其他类细目结算
      getZxSaOtherEquipResSettleDetail: 'getZxSaOtherEquipResSettleDetail',
      updateZxSaOtherEquipResSettle: 'updateZxSaOtherEquipResSettle',
      //结算管理- 其他类细目结算清单明细
      getZxSaOtherEquipResSettleList: 'getZxSaOtherEquipResSettleList',
      updateZxSaOtherEquipResSettleItem: 'updateZxSaOtherEquipResSettleItem',
      addZxSaOtherEquipResSettleItem: 'addZxSaOtherEquipResSettleItem',
      batchDeleteUpdateZxSaOtherEquipResSettleItem: 'batchDeleteUpdateZxSaOtherEquipResSettleItem',
      //结算管理-其他类支付项结算
      zxCtSuppliesShopSettleSubmitFile: 'zxCtSuppliesShopSettleSubmitFile',
      batchSaveupdateZxSaOtherEquipResSettleItem: 'batchSaveupdateZxSaOtherEquipResSettleItem',
      batchSaveUpdateZxSaOtherEquipPaySettleItem: 'batchSaveUpdateZxSaOtherEquipPaySettleItem',
      getZxSaOtherEquipPaySettleList: 'getZxSaOtherEquipPaySettleList',
      updateZxSaOtherEquipPaySettle: 'updateZxSaOtherEquipPaySettle',
      addZxSaOtherEquipPaySettle: 'addZxSaOtherEquipPaySettle',
      batchDeleteUpdateZxSaOtherEquipPaySettle: 'batchDeleteUpdateZxSaOtherEquipPaySettle',
      apiName: 'getZxCtOtherDetail',
      //结算管理-其他类支付项结算清单明细
      getZxSaOtherEquipPaySettleItemList: 'getZxSaOtherEquipPaySettleItemList',
      updateZxSaOtherEquipPaySettleItem: 'updateZxSaOtherEquipPaySettleItem',
      addZxSaOtherEquipPaySettleItem: 'addZxSaOtherEquipPaySettleItem',
      batchDeleteUpdateZxSaOtherEquipPaySettleItem: 'batchDeleteUpdateZxSaOtherEquipPaySettleItem',
      //结算管理-其他类-统计项
      getZxSaOtherEquipSettleItemList: 'getZxSaOtherEquipSettleItemList',
      updateZxSaOtherEquipSettleItem: 'updateZxSaOtherEquipSettleItem',
      addZxSaOtherEquipSettleItem: 'addZxSaOtherEquipSettleItem',
      batchDeleteUpdateZxSaOtherEquipSettleItem: 'batchDeleteUpdateZxSaOtherEquipSettleItem',
      //结算管理-机械租赁类结算
      updateZxSaEquipSettleAuditForFile: 'updateZxSaEquipSettleAuditForFile',
      taxZxSaEquipSettleAudit: 'taxZxSaEquipSettleAudit',//营改增
      getZxSaEquipSettleAuditDetail: 'getZxSaEquipSettleAuditDetail',
      getZxSaEquipSettleAuditList: 'getZxSaEquipSettleAuditList',
      updateZxSaEquipSettleAudit: 'updateZxSaEquipSettleAudit',
      addZxSaEquipSettleAudit: 'addZxSaEquipSettleAudit',
      batchDeleteUpdateZxSaEquipSettleAudit: 'batchDeleteUpdateZxSaEquipSettleAudit',
      getZxCtEqContractListByOrgId: 'getZxCtEqContractListByOrgId',
      generateZxSaEquipSettleAuditAutoNum: 'generateZxSaEquipSettleAuditAutoNum',
      //结算管理-机械租赁类结算-清单明细
      batchUpdateZxSaEquipResSettleItem: 'batchUpdateZxSaEquipResSettleItem',
      getZxSaEquipResSettleItemList: 'getZxSaEquipResSettleItemList',
      addZxSaEquipResSettleItem: 'addZxSaEquipResSettleItem',
      updateZxSaEquipResSettleItem: 'updateZxSaEquipResSettleItem',
      getZxSaEquipResSettleItemDetail: 'getZxSaEquipResSettleItemDetail',
      //结算管理-机械租赁类结算-统计项
      getZxSaEquipSettleAuditItemList: 'getZxSaEquipSettleAuditItemList',
      updateZxSaEquipSettleAuditItem: 'updateZxSaEquipSettleAuditItem',
      //结算管理-机械租赁-支付项
      batchSaveUpdateZxSaEquipPaySettleItem: 'batchSaveUpdateZxSaEquipPaySettleItem',
      getZxSaProjectSetItemList: 'getZxSaProjectSetItemList',
      getZxSaEquipPaySettleItemList: 'getZxSaEquipPaySettleItemList',
      updateZxSaEquipPaySettleItem: 'updateZxSaEquipPaySettleItem',
      addZxSaEquipPaySettleItem: 'addZxSaEquipPaySettleItem',
      batchDeleteUpdateZxSaEquipPaySettleItem: 'batchDeleteUpdateZxSaEquipPaySettleItem',
      //结算管理 -附属类结算表
      zxSaFsSettlementSubmitFile: 'zxSaFsSettlementSubmitFile',
      getZxSaFsSettlementCount: 'getZxSaFsSettlementCount',
      getZxCtFsContractXlList: 'getZxCtFsContractXlList',
      getZxSaFsSettlementList: 'getZxSaFsSettlementList',
      updateZxSaFsSettlement: 'updateZxSaFsSettlement',
      addZxSaFsSettlement: 'addZxSaFsSettlement',
      batchDeleteUpdateZxSaFsSettlement: 'batchDeleteUpdateZxSaFsSettlement',
      getZxSaFsSettlementReport: 'getZxSaFsSettlementReport',//营改增
      getZxSaFsSettlementDetail: 'getZxSaFsSettlementDetail',
      getZxSaFsSettlementListDetail: 'getZxSaFsSettlementListDetail',
      //结算管理 --附属类结算清单明细
      batchUpdateInventorySettlementDetailList: 'batchUpdateInventorySettlementDetailList',
      batchUpdatePayDetailAndPay: 'batchUpdatePayDetailAndPay',
      getOrgList: 'getOrgList',
      getZxSaFsInventory: 'getZxSaFsInventory',
      getZxSaFsInventorySettlementDetailList: 'getZxSaFsInventorySettlementDetailList',
      updateZxSaFsInventorySettlementDetail: 'updateZxSaFsInventorySettlementDetail',
      //结算管理 --附属类结算支付项明细
      getZxSaFsPaySettlement: 'getZxSaFsPaySettlement',
      getZxSaFsPaySettlementDetailList: 'getZxSaFsPaySettlementDetailList',
      updateZxSaFsPaySettlementDetail: 'updateZxSaFsPaySettlementDetail',
      addZxSaFsPaySettlementDetail: 'addZxSaFsPaySettlementDetail',
      batchDeleteUpdateZxSaFsPaySettlementDetail: 'batchDeleteUpdateZxSaFsPaySettlementDetail',
      //结算管理 --附属类统计项
      getZxSaFsStatisticsDetailList: 'getZxSaFsStatisticsDetailList',
      updateZxSaFsStatisticsDetail: 'updateZxSaFsStatisticsDetail',
      addZxSaFsStatisticsDetail: "addZxSaFsStatisticsDetail",
      batchDeleteUpdateZxSaFsStatisticsDetail: 'batchDeleteUpdateZxSaFsStatisticsDetail',
      //结算管理 --物资租赁结算表
      batchAddZxCtSuppliesLeasePaySettlementItem: 'batchAddZxCtSuppliesLeasePaySettlementItem',
      batchUpdateZxCtSuppliesLeaseResSettleItemByConID: 'batchUpdateZxCtSuppliesLeaseResSettleItemByConID',
      getZxCtSuppliesLeaseCampChangeIncrease: 'getZxCtSuppliesLeaseCampChangeIncrease',//营改增
      getZxCtSuppliesLeaseSettlementListListByOrgId: 'getZxCtSuppliesLeaseSettlementListListByOrgId',
      getZxCtSuppliesLeaseSettlementListDetail: 'getZxCtSuppliesLeaseSettlementListDetail',
      updateZxCtSuppliesLeaseSettlementListByOrgId: 'updateZxCtSuppliesLeaseSettlementListByOrgId',
      addZxCtSuppliesLeaseSettlementListByOrgId: 'addZxCtSuppliesLeaseSettlementListByOrgId',
      batchDeleteUpdateZxCtSuppliesLeaseSettlementList: 'batchDeleteUpdateZxCtSuppliesLeaseSettlementList',
      //结算管理 --物资租赁清单
      getZxCtSupReceivingAndReturnListByResID: 'getZxCtSupReceivingAndReturnListByResID',
      getZxCtSuppliesLeaseResSettleItemListByConID: 'getZxCtSuppliesLeaseResSettleItemListByConID',
      updateZxCtSuppliesLeaseResSettleItemByConID: 'updateZxCtSuppliesLeaseResSettleItemByConID',
      updateZxCtSuppliesLeaseResSettleItem: 'updateZxCtSuppliesLeaseResSettleItem',
      //结算管理 --物资租赁支付项
      getZxCtSuppliesLeasePaySettlementItemListByContID: "getZxCtSuppliesLeasePaySettlementItemListByContID",
      updateZxCtSuppliesLeasePaySettlementItemByPayId: 'updateZxCtSuppliesLeasePaySettlementItemByPayId',
      addZxCtSuppliesLeasePaySettlementItemByPayId: 'addZxCtSuppliesLeasePaySettlementItemByPayId',
      batchDeleteUpdateZxCtSuppliesLeasePaySettlementItemByPayId: 'batchDeleteUpdateZxCtSuppliesLeasePaySettlementItemByPayId',
      //结算管理 --物资租赁统计项
      getZxCtSuppliesLeaseSettlementItemListByConID: 'getZxCtSuppliesLeaseSettlementItemListByConID',
      updateZxCtSuppliesLeaseSettlementItem: 'updateZxCtSuppliesLeaseSettlementItem',
      //结算管理 --物资采购结算表
      zxCtSuppliesShopSettleSubmitFile: 'zxCtSuppliesShopSettleSubmitFile',
      zxCtSuppliesLeaseSettleSubmitFile: 'zxCtSuppliesLeaseSettleSubmitFile',
      batchAddZxCtSuppliesShopPaySettlementItem: 'batchAddZxCtSuppliesShopPaySettlementItem',
      getZxCtSuppliesShopCampChangeIncrease: 'getZxCtSuppliesShopCampChangeIncrease',//营改增
      getZxCtSuppliesShopSettlementListFlowDetail: 'getZxCtSuppliesShopSettlementListFlowDetail',
      updateZxCtSuppliesLeaseSettlementItemByConID: 'updateZxCtSuppliesLeaseSettlementItemByConID',
      getZxCtSuppliesShopSettlementListDetail: 'getZxCtSuppliesShopSettlementListDetail',
      getZxCtSuppliesShopSettlementListListByOrgId: 'getZxCtSuppliesShopSettlementListListByOrgId',
      updateZxCtSuppliesShopSettlementListByOrgId: 'updateZxCtSuppliesShopSettlementListByOrgId',
      addZxCtSuppliesShopSettlementListByOrgId: 'addZxCtSuppliesShopSettlementListByOrgId',
      batchDeleteUpdateZxCtSuppliesShopSettlementList: 'batchDeleteUpdateZxCtSuppliesShopSettlementList',
      getZxCtSuppliesContrShopListByWorkID: 'getZxCtSuppliesContrShopListByWorkID',
      addZxCtSuppliesShopResSettleItemList: 'addZxCtSuppliesShopResSettleItemList',
      // checkZxCtSuppliesShopSettlementListThisPrice: 'checkZxCtSuppliesShopSettlementListThisPrice',
      //结算管理 --物资采购清单
      getZxCtSupReceivingAndReturnListByResID: 'getZxCtSupReceivingAndReturnListByResID',
      //获取单据编号
      addZxCtSuppliesShopResSettleItemByStockId: 'addZxCtSuppliesShopResSettleItemByStockId',
      //根据收料单ID新增数据
      getZxCtSuppliesShopResSettleItemListByContID: 'getZxCtSuppliesShopResSettleItemListByContID',
      updateZxCtSuppliesShopResSettleItem: 'updateZxCtSuppliesShopResSettleItem',
      updateZxCtSuppliesShopResSettleItemByContID: 'updateZxCtSuppliesShopResSettleItemByContID',
      //结算管理 --物资采购支付项
      getZxSaProjectSetListAllListByOrgId: 'getZxSaProjectSetListAllListByOrgId',
      getZxCtSuppliesShopPaySettlementItemListByContID: "getZxCtSuppliesShopPaySettlementItemListByContID",
      updateZxCtSuppliesShopPaySettlementItemByPayId: 'updateZxCtSuppliesShopPaySettlementItemByPayId',
      addZxCtSuppliesShopPaySettlementItemByPayId: 'addZxCtSuppliesShopPaySettlementItemByPayId',
      batchDeleteUpdateZxCtSuppliesShopPaySettlementItemByPayId: 'batchDeleteUpdateZxCtSuppliesShopPaySettlementItemByPayId',
      //结算管理 --物资采购统计项
      getZxCtSuppliesShopSettlementItemListByConID: 'getZxCtSuppliesShopSettlementItemListByConID',
      updateZxCtSuppliesShopSettlementItem: 'updateZxCtSuppliesShopSettlementItem',

      // 结算管理 -- 结算金额章节划分与成本分析
      getZxSaProjectSettleAnalysisList: "getZxSaProjectSettleAnalysisList",  // 查询结算金额章节划分与成本分析表
      getZxSaProjectSettleAnalysisDetail: "getZxSaProjectSettleAnalysisDetail", // 查询详情结算金额章节划分与成本分析表
      updateZxSaProjectSettleAnalysisL: "updateZxSaProjectSettleAnalysis", // 修改结算金额章节划分与成本分析表
      addZxSaProjectSettleAnalysis: "addZxSaProjectSettleAnalysis",   // 新增结算金额章节划分与成本分析表
      batchDeleteUpdateZxSaProjectSettleAnalysis: "batchDeleteUpdateZxSaProjectSettleAnalysis",  // 批量删除结算金额章节划分与成本分析表

      getZxSaProjectContractNoList: "getZxSaProjectContractNoList",
      getZxSaProjectSettleAuditItemByContractId: "getZxSaProjectSettleAuditItemByContractId",
      getZxSaProjectSettleAnalysisItemLiJiInfo: "getZxSaProjectSettleAnalysisItemLiJiInfo",

      getZxSaProjectSettleAnalysisItemList: "getZxSaProjectSettleAnalysisItemList",
      getZxSaProjectSettleAnalysisItemDetail: "getZxSaProjectSettleAnalysisItemDetail",
      updateZxSaProjectSettleAnalysisItem: "updateZxSaProjectSettleAnalysisItem",
      addZxSaProjectSettleAnalysisItem: "addZxSaProjectSettleAnalysisItem",
      batchDeleteUpdateZxSaProjectSettleAnalysisItem: "batchDeleteUpdateZxSaProjectSettleAnalysisItem",
      updateZxSaProjectSettleAnalysisAndItem: "updateZxSaProjectSettleAnalysisAndItem",


      //结算管理
      //基础设置
      getZxSaProjectSetList: "getZxSaProjectSetList",
      getZxSaProjectSetDetails: "getZxSaProjectSetDetails",
      addZxSaProjectSet: "addZxSaProjectSet",
      updateZxSaProjectSet: "updateZxSaProjectSet",
      batchDeleteUpdateZxSaProjectSet: "batchDeleteUpdateZxSaProjectSet",
      //完工审核
      getZxSaProjectFinishList: "getZxSaProjectFinishList",
      getZxSaProjectFinishDetails: "getZxSaProjectFinishDetails",
      addZxSaProjectFinish: "addZxSaProjectFinish",
      updateZxSaProjectFinish: "updateZxSaProjectFinish",
      batchDeleteUpdateZxSaProjectFinish: "batchDeleteUpdateZxSaProjectFinish",
      syncZxSaProjectFinish: "syncZxSaProjectFinish",
      //信用评价
      //资质标准+企业性质+工程类别
      getZxCcResCategoryTree: 'getZxCcResCategoryTree',
      addZxCcResCategory: 'addZxCcResCategory',
      updateZxCcResCategory: 'updateZxCcResCategory',
      batchDeleteUpdateZxCcResCategory: 'batchDeleteUpdateZxCcResCategory',
      getZxCcResCategoryList: 'getZxCcResCategoryList',
      //专业化分类目录
      getZxCrColCategoryTree: 'getZxCrColCategoryTree',
      updateZxCrColCategory: 'updateZxCrColCategory',
      addZxCrColCategory: 'addZxCrColCategory',
      batchDeleteUpdateZxCrColCategory: 'batchDeleteUpdateZxCrColCategory',
      getZxCrColCategoryList: 'getZxCrColCategoryList',
      //专业化分类资源库
      getZxCrColResourceList: 'getZxCrColResourceList',
      updateZxCrColResource: 'updateZxCrColResource',
      addZxCrColResource: 'addZxCrColResource',
      batchDeleteUpdateZxCrColResource: 'batchDeleteUpdateZxCrColResource',
      //项目信用评价考核标准库
      getZxCrProjCreditScoreList: 'getZxCrProjCreditScoreList',
      getZxCrProjCreditBadBehaList: 'getZxCrProjCreditBadBehaList',
      addZxCrProjCreditBadBeha: 'addZxCrProjCreditBadBeha',
      addZxCrProjCreditScore: 'addZxCrProjCreditScore',
      updateZxCrProjCreditScore: 'updateZxCrProjCreditScore',
      batchDeleteUpdateZxCrProjCreditScore: 'batchDeleteUpdateZxCrProjCreditScore',
      updateZxCrProjCreditBadBeha: 'updateZxCrProjCreditBadBeha',
      batchDeleteUpdateZxCrProjCreditBadBeha: 'batchDeleteUpdateZxCrProjCreditBadBeha',
      //资质标准等级
      getZxCcOrgResourceList: 'getZxCcOrgResourceList',
      updateZxCcOrgResource: 'updateZxCcOrgResource',
      addZxCcOrgResource: 'addZxCcOrgResource',
      batchDeleteUpdateZxCcOrgResource: 'batchDeleteUpdateZxCcOrgResource',
      //建造合同
      getZxCtContrJzBaseDropDownList: 'getZxCtContrJzBaseDropDownList',
      //清单基础数据
      getZxCtContrJzItemBaseList: 'getZxCtContrJzItemBaseList',
      updateZxCtContrJzItemBase: 'updateZxCtContrJzItemBase',
      addZxCtContrJzItemBase: 'addZxCtContrJzItemBase',
      batchDeleteUpdateZxCtContrJzItemBase: 'batchDeleteUpdateZxCtContrJzItemBase',
      //项目清单建立
      batchSaveUpdateZxCtContrJzItemBase: 'batchSaveUpdateZxCtContrJzItemBase',
      addZxCtContrJzItemForCs: 'addZxCtContrJzItemForCs',
      getZxCtContrJzBaseList: 'getZxCtContrJzBaseList',
      updateZxCtContrJzBase: 'updateZxCtContrJzBase',
      addZxCtContrJzBase: 'addZxCtContrJzBase',
      batchDeleteUpdateZxCtContrJzBase: 'batchDeleteUpdateZxCtContrJzBase',
      //初始建造合同
      getZxCtContrCsjzList: "getZxCtContrCsjzList",
      updateZxCtContrCsjz: "updateZxCtContrCsjz",
      addZxCtContrCsjz: 'addZxCtContrCsjz',
      batchDeleteUpdateZxCtContrCsjz: 'batchDeleteUpdateZxCtContrCsjz',
      getZxCtContrCsjzItemList: 'getZxCtContrCsjzItemList',
      addZxCtContrJzItemForCs: 'addZxCtContrJzItemForCs',
      // 当前建造合同
      addZxCtContrDqjz: 'addZxCtContrDqjz',
      batchDeleteUpdateZxCtContrDqjz: 'batchDeleteUpdateZxCtContrDqjz',
      getZxCtContrDqjzList: 'getZxCtContrDqjzList',
      updateZxCtContrDqjz: 'updateZxCtContrDqjz',
      addZxCtContrJzItemForDq: 'addZxCtContrJzItemForDq',
      getZxCtContrCsjzListApih5FlowStatus2ForDq: 'getZxCtContrCsjzListApih5FlowStatus2ForDq',
      getZxCtContrDqjzItemList: "getZxCtContrDqjzItemList",
      getZxSkStockTransferMaterialRequisitionResName: "getZxSkStockTransferMaterialRequisitionResName",
      // 退货单
      getZxSkStockTransferSalesReturnList: "getZxSkStockTransferSalesReturnList",
      getZxSkStockTransferSalesReturnDetails: "getZxSkStockTransferSalesReturnDetails",
      addZxSkStockTransferSalesReturn: "addZxSkStockTransferSalesReturn",
      updateZxSkStockTransferSalesReturn: "updateZxSkStockTransferSalesReturn",
      batchDeleteUpdateZxSkStockTransferSalesReturn: "batchDeleteUpdateZxSkStockTransferSalesReturn",
      getZxSkStockTransferSalesReturnOutOrgName: "getZxSkStockTransferSalesReturnOutOrgName",
      getZxSkStockTransferSalesReturnResourceName: "getZxSkStockTransferSalesReturnResourceName",
      getZxSkStockTransferSalesReturnNo: "getZxSkStockTransferSalesReturnNo",
      getZxSkStockTransferSalesReturnResName: "getZxSkStockTransferSalesReturnResName",
      checkZxSkStockTransferSalesReturn: "checkZxSkStockTransferSalesReturn",
      getZxSkStockTransferMaterialRequisitionResourceName: "getZxSkStockTransferMaterialRequisitionResourceName",
      // 调拨单
      getZxSkStockTransferTransferOrderList: "getZxSkStockTransferTransferOrderList",
      getZxSkStockTransferTransferOrderDetails: "getZxSkStockTransferTransferOrderDetails",
      addZxSkStockTransferTransferOrder: "addZxSkStockTransferTransferOrder",
      updateZxSkStockTransferTransferOrder: "updateZxSkStockTransferTransferOrder",
      batchDeleteUpdateZxSkStockTransferTransferOrder: "batchDeleteUpdateZxSkStockTransferTransferOrder",
      getZxSkStockTransferTransferOrderNo: "getZxSkStockTransferTransferOrderNo",
      // 盘盈入库单
      getZxSkStockTransferDiskFillingStorageList: "getZxSkStockTransferDiskFillingStorageList",
      getZxSkStockTransferDiskFillingStorageDetails: "getZxSkStockTransferDiskFillingStorageDetails",
      addZxSkStockTransferDiskFillingStorage: "addZxSkStockTransferDiskFillingStorage",
      updateZxSkStockTransferDiskFillingStorage: "updateZxSkStockTransferDiskFillingStorage",
      batchDeleteUpdateZxSkStockTransferDiskFillingStorage: "batchDeleteUpdateZxSkStockTransferDiskFillingStorage",
      checkZxSkStockTransferDiskFillingStorage: "checkZxSkStockTransferDiskFillingStorage",
      getZxSkStockTransferDiskFillingStorageNo: "getZxSkStockTransferDiskFillingStorageNo",
      // 盘亏出库单
      getZxSkStockTransferDishedOutList: "getZxSkStockTransferDishedOutList",
      getZxSkStockTransferDishedOutDetails: "getZxSkStockTransferDishedOutDetails",
      addZxSkStockTransferDishedOut: "addZxSkStockTransferDishedOut",
      updateZxSkStockTransferDishedOut: "updateZxSkStockTransferDishedOut",
      batchDeleteUpdateZxSkStockTransferDishedOut: "batchDeleteUpdateZxSkStockTransferDishedOut",
      checkZxSkStockTransferTransferOrder: "checkZxSkStockTransferTransferOrder",
      checkZxSkStockTransferTransferOrderIn: "checkZxSkStockTransferTransferOrderIn",
      checkZxSkStockTransferTransferOrderOut: "checkZxSkStockTransferTransferOrderOut",
      getZxSkStockTransferDishedOutNo: "getZxSkStockTransferDishedOutNo",
      checkZxSkStockTransferDishedOut: "checkZxSkStockTransferDishedOut",
      // 冲账单
      getZxSkInvoiceList: "getZxSkInvoiceList",
      addZxSkInvoice: "addZxSkInvoice",
      updateZxSkInvoice: "updateZxSkInvoice",
      batchDeleteUpdateZxSkInvoice: "batchDeleteUpdateZxSkInvoice",
      getZxSkInvoiceNo: "getZxSkInvoiceNo",
      getZxSkInvoiceReceivableOrder: "getZxSkInvoiceReceivableOrder",
      getZxSkInvoiceOutOrg: "getZxSkInvoiceOutOrg",
      getZxSkInvoiceResource: "getZxSkInvoiceResource",
      checkZxSkInvoice: "checkZxSkInvoice",
      // 仓库盘点
      getZxSkMakeList: "getZxSkMakeList",
      getZxSkMakeDetail: "getZxSkMakeDetail",
      addZxSkMake: "addZxSkMake",
      updateZxSkMake: "updateZxSkMake",
      batchDeleteUpdateZxSkMake: "batchDeleteUpdateZxSkMake",
      checkZxSkMake: "checkZxSkMake",
      startZxSkMake: "startZxSkMake",
      // 协作队伍库存盘点
      getZxSkColInvenList: "getZxSkColInvenList",
      getZxSkColInvenDetail: "getZxSkColInvenDetail",
      addZxSkColInven: "addZxSkColInven",
      updateZxSkColInven: "updateZxSkColInven",
      batchDeleteUpdateZxSkColInven: "batchDeleteUpdateZxSkColInven",
      getZxSkColInvenInOrgList: "getZxSkColInvenInOrgList",
      getZxSkColInvenResourceList: "getZxSkColInvenResourceList",
      // 进货台账
      getZxSkShopGoodsList: "getZxSkShopGoodsList",
      getZxSkShopGoodsDetail: "getZxSkShopGoodsDetail",
      addZxSkShopGoods: "addZxSkShopGoods",
      updateZxSkShopGoods: "updateZxSkShopGoods",
      batchDeleteUpdateZxSkShopGoods: "batchDeleteUpdateZxSkShopGoods",
      // 物资购置申请
      getZxSkPurchaseList: "getZxSkPurchaseList",
      getZxSkPurchaseDetail: "getZxSkPurchaseDetail",
      addZxSkPurchase: "addZxSkPurchase",
      updateZxSkPurchase: "updateZxSkPurchase",
      batchDeleteUpdateZxSkPurchase: "batchDeleteUpdateZxSkPurchase",
      getZxSkPurchaseNo: "getZxSkPurchaseNo",
      // 周转材料入库
      getZxSkTurnoverInList: "getZxSkTurnoverInList",
      getZxSkTurnoverInDetail: "getZxSkTurnoverInDetail",
      addZxSkTurnoverIn: "addZxSkTurnoverIn",
      updateZxSkTurnoverIn: "updateZxSkTurnoverIn",
      batchDeleteUpdateZxSkTurnoverIn: "batchDeleteUpdateZxSkTurnoverIn",
      checkZxSkTurnoverIn: "checkZxSkTurnoverIn",
      getZxSkTurnoverInResourceList: "getZxSkTurnoverInResourceList",
      getZxSkTurnoverInNo: "getZxSkTurnoverInNo",
      checkZxSkTurnoverIn: "checkZxSkTurnoverIn",
      // 周转材料出库
      getZxSkTurnoverOutList: "getZxSkTurnoverOutList",
      getZxSkTurnoverOutDetai: "getZxSkTurnoverOutDetail",
      addZxSkTurnoverOut: "addZxSkTurnoverOut",
      updateZxSkTurnoverOut: "updateZxSkTurnoverOut",
      batchDeleteUpdateZxSkTurnoverOut: "batchDeleteUpdateZxSkTurnoverOut",
      getZxSkTurnoverOutNo: "getZxSkTurnoverOutNo",
      getZxSkTurnoverOutResourceList: "getZxSkTurnoverOutResourceList",
      checkZxSkTurnoverOut: "checkZxSkTurnoverOut",
      returnZxSkTurnoverOut: "returnZxSkTurnoverOut",
      // 周转材料调拨
      getZxSkTurnOverTransferList: "getZxSkTurnOverTransferList",
      getZxSkTurnOverTransferDetail: "getZxSkTurnOverTransferDetail",
      addZxSkTurnOverTransfer: "addZxSkTurnOverTransfer",
      updateZxSkTurnOverTransfer: "updateZxSkTurnOverTransfer",
      batchDeleteUpdateZxSkTurnOverTransfer: "batchDeleteUpdateZxSkTurnOverTransfer",
      getZxSkTurnOverTransferNo: "getZxSkTurnOverTransferNo",
      getZxSkTurnOverTransferResourceList: "getZxSkTurnOverTransferResourceList",
      checkZxSkTurnOverTransfer: "checkZxSkTurnOverTransfer",
      counterCheckZxSkTurnOverTransfer: "counterCheckZxSkTurnOverTransfer",
      // 周转材料退货
      getZxSkReturnsList: "getZxSkReturnsList",
      getZxSkReturnsDetail: "getZxSkReturnsDetail",
      addZxSkReturns: "addZxSkReturns",
      updateZxSkReturns: "updateZxSkReturns",
      batchDeleteUpdateZxSkReturns: "batchDeleteUpdateZxSkReturns",
      getZxSkReturnsNo: "getZxSkReturnsNo",
      getZxSkReturnsResourceList: "getZxSkReturnsResourceList",
      checkZxSkReturns: "checkZxSkReturns",
      // 周转材料报废
      getZxSkTurnOverScrapList: "getZxSkTurnOverScrapList",
      getZxSkTurnOverScrapDetail: "getZxSkTurnOverScrapDetail",
      addZxSkTurnOverScrap: "addZxSkTurnOverScrap",
      updateZxSkTurnOverScrap: "updateZxSkTurnOverScrap",
      batchDeleteUpdateZxSkTurnOverScrap: "batchDeleteUpdateZxSkTurnOverScrap",
      getZxSkTurnOverScrapNo: "getZxSkTurnOverScrapNo",
      // 周转材料摊销
      getZxSkTurnOverFeeShareList: "getZxSkTurnOverFeeShareList",
      getZxSkTurnOverFeeShareDetail: "getZxSkTurnOverFeeShareDetail",
      addZxSkTurnOverFeeShare: "addZxSkTurnOverFeeShare",
      updateZxSkTurnOverFeeShare: "updateZxSkTurnOverFeeShare",
      batchDeleteUpdateZxSkTurnOverFeeShare: "batchDeleteUpdateZxSkTurnOverFeeShare",
      getZxSkTurnOverFeeShareNo: "getZxSkTurnOverFeeShareNo",
      getZxSkTurnOverFeeShareResourceList: "getZxSkTurnOverFeeShareResourceList",
      counterCheckZxSkTurnOverFeeShare: "counterCheckZxSkTurnOverFeeShare",
      // 周转材料冲帐
      getZxSkTurnoverCheckList: "getZxSkTurnoverCheckList",
      getZxSkTurnoverCheckDetail: "getZxSkTurnoverCheckDetail",
      addZxSkTurnoverCheck: "addZxSkTurnoverCheck",
      updateZxSkTurnoverCheck: "updateZxSkTurnoverCheck",
      batchDeleteUpdateZxSkTurnoverCheck: "batchDeleteUpdateZxSkTurnoverCheck",
      getZxSkTurnoverCheckNo: "getZxSkTurnoverCheckNo",
      getZxSkTurnOverScrapResourceList: "getZxSkTurnOverScrapResourceList",
      checkZxSkTurnOverFeeShare: "checkZxSkTurnOverFeeShare",
      getZxSkTurnoverCheckReceive: "getZxSkTurnoverCheckReceive",
      checkZxSkTurnoverCheck: "checkZxSkTurnoverCheck",
      // 废旧物资管理
      getZxSkWornOutList: "getZxSkWornOutList",
      updateZxSkWornOut: "updateZxSkWornOut",
      addZxSkWornOut: "addZxSkWornOut",
      batchDeleteUpdateZxSkWornOut: "batchDeleteUpdateZxSkWornOut",
      getZxSkWornOutNo: "getZxSkWornOutNo",
      // 物资价差凉差核算(月)
      getZxSkStockDifMonthList: "getZxSkStockDifMonthList",
      updateZxSkStockDifMonth: "updateZxSkStockDifMonth",
      addZxSkStockDifMonth: "addZxSkStockDifMonth",
      batchDeleteUpdateZxSkStockDifMonth: "batchDeleteUpdateZxSkStockDifMonth",
      checkZxSkStockDifMonth: "checkZxSkStockDifMonth",
      counterCheckZxSkStockDifMonth: "counterCheckZxSkStockDifMonth",
      // 施工协作单位
      getZxCrCustomerExtAttrList: "getZxCrCustomerExtAttrList",
      updateZxCrCustomerExtAttr: "updateZxCrCustomerExtAttr",
      addZxCrCustomerExtAttr: "addZxCrCustomerExtAttr",
      batchDeleteUpdateZxCrCustomerExtAttr: "batchDeleteUpdateZxCrCustomerExtAttr",
      getZxCrCustomerExtAttrDetail: "getZxCrCustomerExtAttrDetail",
      getZxCrCustomerExtAttrDetailRetrieval: "getZxCrCustomerExtAttrDetailRetrieval",
      getZxCrCustomerNewList: "getZxCrCustomerNewList",
      getZxCrCustomerExtAttrDetailRetrieval: "getZxCrCustomerExtAttrDetailRetrieval",
      getZxCrCustomerExtAttrAll: "getZxCrCustomerExtAttrAll",
      //领料单位新增
      addZxCrCustomerExtAttrPicking: "addZxCrCustomerExtAttrPicking",
      updateZxCrCustomerExtAttrPicking: "updateZxCrCustomerExtAttrPicking",
      getZxCrCustomerExtAttrPickingList: "getZxCrCustomerExtAttrPickingList",
      // 协作单位基础信息登记
      getZxCrCustomerInfoList: "getZxCrCustomerInfoList",
      updateZxCrCustomerInfo: "updateZxCrCustomerInfo",
      addZxCrCustomerInfo: "addZxCrCustomerInfo",
      batchDeleteUpdateZxCrCustomerInfo: "batchDeleteUpdateZxCrCustomerInfo",
      getZxCrCustomerHonorList: "getZxCrCustomerHonorList",
      getZxCrCustomerInfoQaList: "getZxCrCustomerInfoQaList",
      getZxCrCustomerInfoDireList: "getZxCrCustomerInfoDireList",
      getZxCrCustomerInfoDatumList: "getZxCrCustomerInfoDatumList",
      //折算系数
      getZxBuProjectTypeCheckTree: "getZxBuProjectTypeCheckTree",
      getZxBuProjectTypeCheckTreeList: "getZxBuProjectTypeCheckTreeList",
      batchDeleteUpdateZxBuProjectTypeCheck: "batchDeleteUpdateZxBuProjectTypeCheck",
      updateZxBuProjectTypeCheck: "updateZxBuProjectTypeCheck",
      addZxBuProjectTypeCheck: "addZxBuProjectTypeCheck",
      //项目工程类别划分
      getZxBuProjectTypeList: "getZxBuProjectTypeList",
      addZxBuProjectType: "addZxBuProjectType",
      updateZxBuProjectType: "updateZxBuProjectType",
      batchDeleteUpdateZxBuProjectType: "batchDeleteUpdateZxBuProjectType",
      getZxBuProjectTypeCheckProjectType: "getZxBuProjectTypeCheckProjectType",
      checkZxBuProjectType: "checkZxBuProjectType",
      //标准清单库
      getZxBuWorksTree: "getZxBuWorksTree",
      getZxBuWorksTreeList: "getZxBuWorksTreeList",
      addZxBuWorks: "addZxBuWorks",
      updateZxBuWorks: "updateZxBuWorks",
      batchDeleteUpdateZxBuWorks: "batchDeleteUpdateZxBuWorks",
      getZxBuWorksDetail: "getZxBuWorksDetail",
      //标准清单工序
      getZxBuYgjResTechnicsList: "getZxBuYgjResTechnicsList",
      addZxBuYgjResTechnics: "addZxBuYgjResTechnics",
      updateZxBuYgjResTechnics: "updateZxBuYgjResTechnics",
      batchDeleteUpdateZxBuYgjResTechnics: "batchDeleteUpdateZxBuYgjResTechnics",
      //清单关联
      getZxBuWorksTreeListAll: "getZxBuWorksTreeListAll",
      relevanceZxBuYgjResTechnics: "relevanceZxBuYgjResTechnics",
      getZxBuProjectTypeProjectList: "getZxBuProjectTypeProjectList",
      getZxBuProjectTypeCheckOver: "getZxBuProjectTypeCheckOver",
      getZxBuWorksNoName: "getZxBuWorksNoName",
      removeRelevanceZxBuYgjResTechnics: "removeRelevanceZxBuYgjResTechnics",
      //四项费用标准
      getZxEqAssetSellItemList: 'getZxEqAssetSellItemList',
      getZxBuYgjLiveStandardTree: "getZxBuYgjLiveStandardTree",
      getZxBuYgjLiveStandardTreeList: "getZxBuYgjLiveStandardTreeList",
      addZxBuYgjLiveStandard: "addZxBuYgjLiveStandard",
      updateZxBuYgjLiveStandard: "updateZxBuYgjLiveStandard",
      batchDeleteUpdateZxBuYgjLiveStandard: "batchDeleteUpdateZxBuYgjLiveStandard",
      //标后材料价格表
      getZxBuStockPriceList: "getZxBuStockPriceList",
      getZxBuStockPriceItemList: "getZxBuStockPriceItemList",
      addZxBuStockPrice: "addZxBuStockPrice",
      updateZxBuStockPrice: "updateZxBuStockPrice",
      batchDeleteUpdateZxBuStockPrice: "batchDeleteUpdateZxBuStockPrice",
      importZxBuStockPrice: "importZxBuStockPrice",
      //标后材料关联
      getZxBuStockPriceItemResAll: "getZxBuStockPriceItemResAll",
      removeRelevanceZxBuWorksToStockPrice: "removeRelevanceZxBuWorksToStockPrice",
      relevanceZxBuWorksToStockPrice: "relevanceZxBuWorksToStockPrice",
      getZxBuWorksResNoName: "getZxBuWorksResNoName",
      updateZxBuWorksToStockPriceCoe: "updateZxBuWorksToStockPriceCoe",
      //标后明细
      updateZxBuStockPriceItem: "updateZxBuStockPriceItem",
      getBaseZxBuStockPriceItemList: "getBaseZxBuStockPriceItemList",
      addZxBuStockPriceItem: "addZxBuStockPriceItem",
      batchDeleteUpdateZxBuStockPriceItem: "batchDeleteUpdateZxBuStockPriceItem",
      //标后预算编制
      getZxBuBudgetBookList: "getZxBuBudgetBookList",
      getZxBuBudgetBookDetail: "getZxBuBudgetBookDetail",
      addZxBuBudgetBook: "addZxBuBudgetBook",
      updateZxBuBudgetBook: "updateZxBuBudgetBook",
      batchDeleteUpdateZxBuBudgetBook: "batchDeleteUpdateZxBuBudgetBook",
      getZxCtContractDetailByOrgID: "getZxCtContractDetailByOrgID",
      getZxBuControlTreeList: "getZxBuControlTreeList",
      initZxBuYgLiveFeeList: "initZxBuYgLiveFeeList", // 初始化
      getZxBuYgLiveFeeTree: "getZxBuYgLiveFeeTree",
      getZxBuYgLiveFeeTreeList: "getZxBuYgLiveFeeTreeList",
      addZxBuYgjLiveFee: "addZxBuYgjLiveFee",
      batchDeleteUpdateZxBuYgjLiveFee: "batchDeleteUpdateZxBuYgjLiveFee",
      updateZxBuYgjLiveFee: "updateZxBuYgjLiveFee",
      getZxBuBudgetBookCheckOver: "getZxBuBudgetBookCheckOver",
      dataUpdateZxBuBudgetBookAfter: "dataUpdateZxBuBudgetBookAfter",
      dataUpdateZxBuBudgetBookConstruction: "dataUpdateZxBuBudgetBookConstruction",
      auditZxBuBudgetBook: "auditZxBuBudgetBook",
      checkZxBuBudgetBook: "checkZxBuBudgetBook",
      //标后预算编制/明细
      getZxBuBudgetDetailsList: "getZxBuBudgetDetailsList",
      getZxBuBudgetDetailsDetail: "getZxBuBudgetDetailsDetail",
      addZxBuBudgetDetails: "addZxBuBudgetDetails",
      updateZxBuBudgetDetails: "updateZxBuBudgetDetails",
      batchDeleteUpdateZxBuBudgetDetails: "batchDeleteUpdateZxBuBudgetDetails",
      //标后预算编制/工程量清单综合基价费用
      updateZxBuYgjResTechnicsList: "updateZxBuYgjResTechnicsList",
      getZxBuYgjResTechnicsAndQDList: "getZxBuYgjResTechnicsAndQDList",
      getZxBuProjectTypeName: "getZxBuProjectTypeName",
      copyZxBuBudgetBook: "copyZxBuBudgetBook", // 复制标后预算接口
      updateZxBuControl: "updateZxBuControl",
      updateZxBuYgjResTechnicsListConstruction: "updateZxBuYgjResTechnicsListConstruction",

      checkZxBuBudgetBook: "checkZxBuBudgetBook",
      //  预算 - 清单关联
      automaticLinkZxBuYgjResTechnics: "automaticLinkZxBuYgjResTechnics", // 自动关联 
      cancelLinkZxBuYgjResTechnics: "cancelLinkZxBuYgjResTechnics", // 取消关联

      // 预算 批量修改
      saveZxBuYgLiveFeeList: "saveZxBuYgLiveFeeList",

      // 项目信用评价
      getZxCrProjectEvaluationList: "getZxCrProjectEvaluationList",
      getProjectListByCreditEvaItem: 'getProjectListByCreditEvaItem',
      batchUpdateZxCrHalfYearCreditEvaItem: 'batchUpdateZxCrHalfYearCreditEvaItem',
      getZxCrProjectEvaluationDetail: "getZxCrProjectEvaluationDetail",
      updateZxCrProjectEvaluation: "updateZxCrProjectEvaluation",
      addZxCrProjectEvaluation: "addZxCrProjectEvaluation",
      batchDeleteUpdateZxCrProjectEvaluation: "batchDeleteUpdateZxCrProjectEvaluation",
      getZxCrProjectEvaluationBadList: "getZxCrProjectEvaluationBadList",
      getZxCrProjectEvaluationScoreList: "getZxCrProjectEvaluationScoreList",
      getZxCrCustomerInfoListAll: "getZxCrCustomerInfoListAll",
      getZxCrProjectEvaluationScoreDetailOne: "getZxCrProjectEvaluationScoreDetailOne",
      getZxCrProjectEvaluationBadDetailOne: "getZxCrProjectEvaluationBadDetailOne",
      updateAuditStatus: "updateAuditStatus",
      updateAuditStatusOut: "updateAuditStatusOut",
      // 公司半年评价
      getZxCrHalfYearCreditEvaList: "getZxCrHalfYearCreditEvaList",
      getZxCrHalfYearCreditEvaItemList: "getZxCrHalfYearCreditEvaItemList",
      addZxCrHalfYearCreditEva: "addZxCrHalfYearCreditEva",
      updateZxCrHalfYearCreditEva: "updateZxCrHalfYearCreditEva",
      batchDeleteUpdateZxCrHalfYearCreditEva: "batchDeleteUpdateZxCrHalfYearCreditEva",
      getZxCrColCategoryTreeShu: "getZxCrColCategoryTreeShu",
      getZxCtSuppliesContractList: 'getZxCtSuppliesContractList',
      //温
      //checkOnlyByObj:"checkOnlyByObj",
      //项目管理： 安全计划目标
      getZxSfPlanTargetList: "getZxSfPlanTargetList",
      addZxSfPlanTarget: "addZxSfPlanTarget",
      updateZxSfPlanTarget: "updateZxSfPlanTarget",
      batchDeleteUpdateZxSfPlanTarget: "batchDeleteUpdateZxSfPlanTarget",
      getZxSfPlanTargetCheckProjectType: "getZxSfPlanTargetCheckProjectType",
      checkZxSfPlanTarget: "checkZxSfPlanTarget",
      updateZxCrHalfYearCreditEvaAuditStatus: "updateZxCrHalfYearCreditEvaAuditStatus",
      getZxCrQuarterCreditEvaList: "getZxCrQuarterCreditEvaList",
      getZxCrProjectEvaluationListCatName: "getZxCrProjectEvaluationListCatName",
      getZxCrProjectEvaluationListResName: "getZxCrProjectEvaluationListResName",
      getZxCrJYearCreditEvaList: "getZxCrJYearCreditEvaList",
      getZxCrJYearCreditEvaItemList: "getZxCrJYearCreditEvaItemList",
      getCompanyListByCustomer: 'getCompanyListByCustomer',
      addZxCrJYearCreditEva: "addZxCrJYearCreditEva",
      updateZxCrJYearCreditEva: "updateZxCrJYearCreditEva",
      batchDeleteUpdateZxCrJYearCreditEva: "batchDeleteUpdateZxCrJYearCreditEva",
      getZxCrJYearCreditEvaItemInit: "getZxCrJYearCreditEvaItemInit",
      updateZxCrJYearCreditEvaItemAll: "updateZxCrJYearCreditEvaItemAll",
      batchDeleteUpdateZxCrCustomerExtAttr: "batchDeleteUpdateZxCrCustomerExtAttr",
      //项目管理： 紧急预案
      getZxSfOtherAddFileList: 'getZxSfOtherAddFileList',
      batchDeleteUpdateZxSfOtherAddFile: 'batchDeleteUpdateZxSfOtherAddFile',
      updateZxSfOtherAddFile: 'updateZxSfOtherAddFile',
      addZxSfOtherAddFile: 'addZxSfOtherAddFile',
      //项目管理： 安全费用
      getZxSfFeeList: 'getZxSfFeeList',
      batchDeleteUpdateZxSfFee: 'batchDeleteUpdateZxSfFee',
      updateZxSfFee: 'updateZxSfFee',
      addZxSfFee: 'addZxSfFee',
      //项目管理：安全管理综合考核
      getZxSfExamItemList: 'getZxSfExamItemList',
      getZxSfExamList: 'getZxSfExamList',
      addZxSfExam: 'addZxSfExam',
      updateZxSfExam: 'updateZxSfExam',
      batchDeleteUpdateZxSfExam: 'batchDeleteUpdateZxSfExam',
      addZxSfExamItem: 'addZxSfExamItem',
      updateZxSfExamItem: 'updateZxSfExamItem',
      batchDeleteUpdateZxSfExamItem: 'batchDeleteUpdateZxSfExamItem',
      ureportZxEqToEquipSourceQueryPageIdle: 'ureportZxEqToEquipSourceQueryPageIdle',
      updateZxSfExamStatus: 'updateZxSfExamStatus',
      updateZxSfExamStatusju: 'updateZxSfExamStatusju',
      batchUpdateZxSfExamItem: 'batchUpdateZxSfExamItem',
      //项目管理：基础代码
      getZxCmGlobalCodeList: 'getZxCmGlobalCodeList',
      addZxCmGlobalCode: 'addZxCmGlobalCode',
      updateZxCmGlobalCode: 'updateZxCmGlobalCode',
      batchDeleteUpdateZxCmGlobalCode: 'batchDeleteUpdateZxCmGlobalCode',
      getZxSfAccessRoomList: 'getZxSfAccessRoomList',
      addZxSfAccessRoom: 'addZxSfAccessRoom',
      updateZxSfAccessRoom: 'updateZxSfAccessRoom',
      batchDeleteUpdateZxSfAccessRoom: 'batchDeleteUpdateZxSfAccessRoom',
      //项目管理：特种作业人员台账
      getZxSfSpecialEmpList: 'getZxSfSpecialEmpList',
      updateZxSfSpecialEmp: 'updateZxSfSpecialEmp',
      addZxSfSpecialEmp: 'addZxSfSpecialEmp',
      batchDeleteUpdateZxSfSpecialEmp: 'batchDeleteUpdateZxSfSpecialEmp',
      getZxSfSpecialEmpItemList: 'getZxSfSpecialEmpItemList',
      updateZxSfSpecialEmpItem: 'updateZxSfSpecialEmpItem',
      addZxSfSpecialEmpItem: 'addZxSfSpecialEmpItem',
      batchDeleteUpdateZxSfSpecialEmpItem: 'batchDeleteUpdateZxSfSpecialEmpItem',
      // 项目月度量差表
      getStockDifMonthFormList: "getStockDifMonthFormList",
      // 项目季度量差表
      getStockDifMonthFormJDList: "getStockDifMonthFormJDList",
      // 物资动态账
      getReceivingDynamicItem: "getReceivingDynamicItem",
      getUreportZxSkTurnoverCheckList: "getUreportZxSkTurnoverCheckList",
      ureportZxSkTurnoverInReport: "ureportZxSkTurnoverInReport",
      getResMoveMonthMPItem: "getResMoveMonthMPItem",
      getZxCtContractList: "getZxCtContractList",
      getZxSkStockTransferInitialReceiptNo: "getZxSkStockTransferInitialReceiptNo",
      getZxSkWarehouseByParentOrgIDList: "getZxSkWarehouseByParentOrgIDList",
      getZxSkLimitPriceByLimitNoList: "getZxSkLimitPriceByLimitNoList",
      getZxEqIecsCBSPickingList: "getZxEqIecsCBSPickingList",
      // 测试接口----byLM
      getYunShuContractList: "getYunShuContractList",
      getQiTaContractList: "getQiTaContractList",
      // addYunShuFeiList: 'addYunShuFeiList',
      // addQiTaFeiList: 'addQiTaFeiList',
      // 测试接口----byLM
      getZxCtSuppliesContractListByFirstId: "getZxCtSuppliesContractListByFirstId",
      getZxCtSuppliesContrShopListListByContID: "getZxCtSuppliesContrShopListListByContID",
      getZxSkTurnoverCheckSupplierList: "getZxSkTurnoverCheckSupplierList",
      getZxSkResCategoryMaterialsListResourceNotRevolve: "getZxSkResCategoryMaterialsListResourceNotRevolve",
      getZxSkResourceMaterialsListNameJoinNotRevolve: "getZxSkResourceMaterialsListNameJoinNotRevolve",
      addZxSkStockFee: "addZxSkStockFee",
      updateZxSkStockFee: "updateZxSkStockFee",
      getZxSkStockFeeDetail: "getZxSkStockFeeDetail",
      getZxSkHttreqPlanItemList: "getZxSkHttreqPlanItemList",

      //工程施工合同  --评审
      psGetZxGcsgCtContrApplyList: "psGetZxGcsgCtContrApplyList",
      psAddZxGcsgCtContrApply: "psAddZxGcsgCtContrApply", //新增工程施工类合同评审并生成相关数据
      psUpdateZxGcsgCtContrApply: "psUpdateZxGcsgCtContrApply", //更新工程施工类合同评审并更新清单
      batchDeleteUpdateZxGcsgCtContrApply: "batchDeleteUpdateZxGcsgCtContrApply", //删除工程施工类合同评审补充协议表
      psGetZxGcsgCtContrApplyContractNo: "psGetZxGcsgCtContrApplyContractNo", //合同评审中合同编码序号获取  
      getZxCrCustomerExtAttrEngineeringList: "getZxCrCustomerExtAttrEngineeringList",//乙方下拉
      getZxGcsgCtContrApplyWorksTree: "getZxGcsgCtContrApplyWorksTree", //    清单的左侧树形接口
      getZxGcsgCtContrApplyWorksListAmount: "getZxGcsgCtContrApplyWorksListAmount", //    清单列表接口 
      gcsgGetZxCtSZProcessSelect: "gcsgGetZxCtSZProcessSelect", // 分包合同评审/管理/补充协议中标准工序库二级下拉  
      gcsgGetZxCtSZProcessList: "gcsgGetZxCtSZProcessList", //分包合同评审/管理/补充协议中获取标准工序 
      autoHookZxGcsgCtContrApplyWorksProcess: "autoHookZxGcsgCtContrApplyWorksProcess", //合同评审点击自动挂接工序  
      getZxGcsgCtContrApplyWorksEditSubList: "getZxGcsgCtContrApplyWorksEditSubList", //合同评审点击编辑下级清单时获取数据
      batchEditSubListZxGcsgCtContrApplyWorks: "batchEditSubListZxGcsgCtContrApplyWorks", //合同评审编辑下级清单时批量保存数据[增改删]
      gcsgGetZxCtValuationSZRulesList: "gcsgGetZxCtValuationSZRulesList", //分包合同评审/管理/补充协议中获取计价规则列表  
      manualHookZxGcsgCtContrApplyWorksRule: "manualHookZxGcsgCtContrApplyWorksRule", //合同评审选中清单挂接计价规则   
      gcsgGetZxCtSZProcessList: "gcsgGetZxCtSZProcessList", //分包合同评审/管理/补充协议中获取标准工序 gcsgGetZxCtSZProcessList  参数有，baseType,parentID--下拉id,applyAlterWorksId--评审清单id,ctContrApplyId--评审id
      manualHookZxGcsgCtContrApplyWorksProcess: "manualHookZxGcsgCtContrApplyWorksProcess", //合同评审选中清单挂接工序
      getZxGcsgCtContrApplyWorksListProcess: "getZxGcsgCtContrApplyWorksListProcess", //合同评审tab页清单工序挂接台账  
      getZxGcsgCtPriceSysList: "getZxGcsgCtPriceSysList", //查询合同单价分析表
      psSyncHookDataZxGcsgCtPriceSys: "psSyncHookDataZxGcsgCtPriceSys",// 合同评审tab页合同单价分析同步挂接数据   
      psglbxGetZxGcsgCtPriceSysDetails: "psglbxGetZxGcsgCtPriceSysDetails", //合同评审/管理/补充协议tab页合同单价分析查看详情  
      ctPriceSysItemList: "ctPriceSysItemList", //单价分析明细集合
      psglbxUpdateZxGcsgCtPriceSysAndItem: "psglbxUpdateZxGcsgCtPriceSysAndItem", //合同评审/管理/补充协议tab页合同单价分析修改后保存
      psCheckZxGcsgCtContrApplyBeforeFlow: "psCheckZxGcsgCtContrApplyBeforeFlow",// 合同评审发起评审申请check
      psCheckZxGcsgCtContrApplyInFlow: "psCheckZxGcsgCtContrApplyInFlow",// 合同评审发起评审申请check(apollo)     
      getSysDepartmentUserAllTree: "getSysDepartmentUserAllTree", //流程人员搜索
      importZxGcsgCtContrApplyWorks: "importZxGcsgCtContrApplyWorks", //导入工程合同评审清单信息(旧)
      resumeZxGcsgCtContractStatus: "resumeZxGcsgCtContractStatus", //工程施工类合同管理列表恢复执行 
      batchDeleteUpdateZxGcsgCtContract: "batchDeleteUpdateZxGcsgCtContract", //删除工程施工类合同管理表
      gcsgGetZxCtCloudEcoSelect: "gcsgGetZxCtCloudEcoSelect",   //工程施工合同评审获取云电商分包定标数据 
      psExportZxGcsgCtContrApplyExcel: "exportZxGcsgCtContrApplyPsExcel", //工程施工类合同评审导出列表页[客户端流文件]   
      glExportZxGcsgCtContractExcel: "exportZxGcsgCtContractGlExcel", //工程施工类合同管理导出列表页[客户端流文件]  
      bxExportZxGcsgCtContrApplyExcel: "exportZxGcsgCtContrApplyBxExcel", //工程施工类补充协议导出列表页[客户端流文件]  
      getZxGcsgCtContrApplyWorksEditAllList: "getZxGcsgCtContrApplyWorksEditAllList", // 合同评审点击批量编辑清单时获取数据  
      batchEditAllListZxGcsgCtContrApplyWorks: "batchEditAllListZxGcsgCtContrApplyWorks", // 合同评审批量编辑清单时批量保存数据[改删]   
      psUpdateZxGcsgCtContrApplyByTemp: 'psUpdateZxGcsgCtContrApplyByTemp',
      gcsgGetZxCtSZProcessSelectedOne: "gcsgGetZxCtSZProcessSelectedOne", // 点击清单挂接按钮获取已挂接工序的二级下拉id
      getZxGcsgCtContrApplyWorksListByFlow: "getZxGcsgCtContrApplyWorksListByFlow", // 清单查看-合同审批
      getZxGcsgCcCoAlterWorkListByFlow: "getZxGcsgCcCoAlterWorkListByFlow",  // 清单查看-补充协议
      checkBatchDeleteZxGcsgCtContract: "checkBatchDeleteZxGcsgCtContract", // 删除接口check

      //工程施工合同  --合同管理
      getZxGcsgCtContractList: "getZxGcsgCtContractList", //查询工程施工类合同管理表 详情接口
      getZxGcsgCtContractDetails: "getZxGcsgCtContractDetails", //查询工程施工类合同管理详情
      saveZxGcsgCtContractInfo: "saveZxGcsgCtContractInfo", //工程施工类合同管理信息保存接口
      getZxGcsgCcWorksTree: "getZxGcsgCcWorksTree", // 查询工程施工类合同管理清单左侧树形结构  
      importZxGcsgCcWorks: "importZxGcsgCcWorks", //导入工程施工类合同管理清单信息(旧)  
      autoHookZxGcsgCcWorksProcess: "autoHookZxGcsgCcWorksProcess", //合同管理点击自动挂接工序   参数ctContractId,inputWorkType
      getZxGcsgCcWorksListAmount: "getZxGcsgCcWorksListAmount", //合同管理点击清单获取数据[合计金额]
      manualHookZxGcsgCcWorksProcess: "manualHookZxGcsgCcWorksProcess", //合同管理选中清单挂接工序
      manualHookZxGcsgCcWorksRule: "manualHookZxGcsgCcWorksRule", // 合同管理选中清单挂接计价规则     
      getZxGcsgCcWorksListProcess: "getZxGcsgCcWorksListProcess", //合同管理tab页清单工序挂接台账 
      glSyncHookDataZxGcsgCtPriceSys: "glSyncHookDataZxGcsgCtPriceSys", //合同管理tab页合同单价分析同步挂接数据
      bxGetZxGcsgCtContrApplyList: "bxGetZxGcsgCtContrApplyList", //查询工程施工类合同补充协议   
      getZxGcsgSaCoWorkLinkWorkLeftTree: "getZxGcsgSaCoWorkLinkWorkLeftTree", //分包合同管理tab页-分包清单与业主合同清单关联左侧树  
      getZxCtWorksRightTree: "getZxCtWorksRightTree",// 分包合同管理tab页-分包清单与业主合同清单关联右侧树  
      clickAddOrDeleteZxGcsgSaCoWorkLinkWork: "clickAddOrDeleteZxGcsgSaCoWorkLinkWork", // 点击右侧业主清单编号新增、删除  
      updateZxGcsgSaCoWorkLinkWorkIsMain: "updateZxGcsgSaCoWorkLinkWorkIsMain",// 点击左侧分包清单是否主项-验证业主清单是否为叶子节点  
      getZxSaProjectSettleAuditList: "getZxSaProjectSettleAuditList", //结算单
      getZxSaProjectSettleAuditDetail: "getZxSaProjectSettleAuditDetail", //结算单详情
      psGetZxGcsgCtContrApplyDetailsByFlow: "psGetZxGcsgCtContrApplyDetailsByFlow", //流程中详情接口
      updateZxGcsgCtContract: "updateZxGcsgCtContract",

      // 补充协议  --合同管理
      getZxGcsgCcWorksSelect: "getZxGcsgCcWorksSelect", //补充协议归属主合同清单编号/清单名称下拉 
      bxGetZxGcsgCtContrApplyList: "bxGetZxGcsgCtContrApplyList", //查询工程施工类合同补充协议 
      bxAddZxGcsgCtContrApply: "bxAddZxGcsgCtContrApply", //新增工程施工类合同补充协议
      bxUpdateZxGcsgCtContrApply: "bxUpdateZxGcsgCtContrApply", //查询工程施工类合同补充协议 
      batchDeleteUpdateZxGcsgCtContrApply: "batchDeleteUpdateZxGcsgCtContrApply", //删除工程施工类合同评审补充协议表 
      bxGetZxGcsgCtContrApplyContractNo: "bxGetZxGcsgCtContrApplyContractNo", // 补充协议中补充协议编码序号获取  
      getZxGcsgCcCoAlterDetailsWorkList: "getZxGcsgCcCoAlterDetailsWorkList", //工程类合同补充协议获取清单详情及明细列表    
      saveZxGcsgCcCoAlterDetailsWorkList: "saveZxGcsgCcCoAlterDetailsWorkList", //工程类合同补充协议保存清单详情及明细列表 
      getZxGcsgCcCoAlterWorkListAmount: "getZxGcsgCcCoAlterWorkListAmount", //协议点击清单获取数据[合计金额]   
      manualHookZxGcsgCcCoAlterWorkProcess: "manualHookZxGcsgCcCoAlterWorkProcess", //补充协议选中新增类型的清单挂接工序   
      manualHookZxGcsgCcCoAlterWorkRule: "manualHookZxGcsgCcCoAlterWorkRule", // 补充协议选中清单挂接计价规则  
      getZxGcsgCcCoAlterWorkListProcess: "getZxGcsgCcCoAlterWorkListProcess", //补充协议tab页清单工序挂接台账  参数 补充协议idctContrApplyId
      bxSyncHookDataZxGcsgCtPriceSys: "bxSyncHookDataZxGcsgCtPriceSys", // 补充协议tab页合同单价分析同步挂接数据   
      bxCheckZxGcsgCtContrApplyBeforeFlow: "bxCheckZxGcsgCtContrApplyBeforeFlow", //补充协议发起评审申请check   参数ctContrApplyId 补充协议id
      bxCheckZxGcsgCtContrApplyInFlow: "bxCheckZxGcsgCtContrApplyInFlow", //补充协议发起评审申请check(apollo)
      bxGetZxGcsgCtContractSelect: "bxGetZxGcsgCtContractSelect", //获取合同管理新接口 

      getZxSfHazardRoomList: "getZxSfHazardRoomList",
      addZxSfHazardRoom: "addZxSfHazardRoom",
      updateZxSfHazardRoom: "updateZxSfHazardRoom",
      batchDeleteUpdateZxSfHazardRoom: "batchDeleteUpdateZxSfHazardRoom",
      getZxSfJuAccessRoomList: "getZxSfJuAccessRoomList",
      addZxSfJuAccessRoom: "addZxSfJuAccessRoom",
      updateZxSfJuAccessRoom: "updateZxSfJuAccessRoom",
      batchDeleteUpdateZxSfJuAccessRoom: "batchDeleteUpdateZxSfJuAccessRoom",
      getZxSfOtherAddFileList: "getZxSfOtherAddFileList",
      addZxSfOtherAddFile: "addZxSfOtherAddFile",
      updateZxSfOtherAddFile: "updateZxSfOtherAddFile",
      batchDeleteUpdateZxSfOtherAddFile: "batchDeleteUpdateZxSfOtherAddFile",
      updateZxSfCheckIsReported: "updateZxSfCheckIsReported",
      updateZxSfCheckCheckAgainStatus: "updateZxSfCheckCheckAgainStatus",
      getZxSfCheckListAll: "getZxSfCheckListAll",
      addZxSfCheck: "addZxSfCheck",
      updateZxSfCheck: "updateZxSfCheck",
      batchDeleteUpdateZxSfCheck: "batchDeleteUpdateZxSfCheck",
      //上报公司安全检查  
      updateZxSfCheckIsReportedCompany: "updateZxSfCheckIsReportedCompany",
      //复查公司安全检查  
      updateZxSfCheckCheckAgainStatusCompany: "updateZxSfCheckCheckAgainStatusCompany",
      //查询公司安全检查   
      getZxSfCheckListAllCompany: "getZxSfCheckListAllCompany",
      //下达公司安全检查    
      updateZxSfCheckIsSendCompany: "updateZxSfCheckIsSendCompany",

      //复查局安全检查   
      updateZxSfCheckAgainStatusBureau: "updateZxSfCheckAgainStatusBureau",
      //查询局安全检查   
      getZxSfCheckListAllBureau: "getZxSfCheckListAllBureau",
      //下达局安全检查   
      updateZxSfCheckIsSendBureau: "updateZxSfCheckIsSendBureau",

      getZxSfHazardMainList: "getZxSfHazardMainList",   //查询 
      addZxSfHazardMain: "addZxSfHazardMain",  //新增 
      updateZxSfHazardMain: "updateZxSfHazardMain",   //修改 
      batchDeleteUpdateZxSfHazardMain: "batchDeleteUpdateZxSfHazardMain",  //删除
      getZxSfEduList: "getZxSfEduList", //安全教育培訓查询
      addZxSfEdu: "addZxSfEdu",   //新增 
      updateZxSfEdu: "updateZxSfEdu",  //修改 
      batchDeleteUpdateZxSfEdu: "batchDeleteUpdateZxSfEdu",  //删除
      getZxSfAccessList: "getZxSfAccessList",  //协作队伍安全考核查询
      addZxSfAccess: "addZxSfAccess",  //新增 
      updateZxSfAccess: "updateZxSfAccess",  //修改
      batchDeleteUpdateZxSfAccess: "batchDeleteUpdateZxSfAccess",  //删除
      getZxSfHazardRoomAttList: "getZxSfHazardRoomAttList",
      getZxSfHazardRoomAttProArea: "getZxSfHazardRoomAttProArea",   //查询重大危险源过程（区域）
      getZxSfHazardRoomAttDoing: "getZxSfHazardRoomAttDoing",    //查询重大危险源行为（活动）或设备=环境
      getZxSfHazardRoomAttRiskFactors: "getZxSfHazardRoomAttRiskFactors",    //查询重大危险源危险因素,
      updateZxSfAccessOutDate: "updateZxSfAccessOutDate",//退場
      updateZxSfAccessOutDateReturn: "updateZxSfAccessOutDateReturn",//撤销退場
      getZxCtContractList: "getZxCtContractList",
      getZxCrCustomerInfoListOne: "getZxCrCustomerInfoListOne",
      updateZxCrJYearCreditEvaAuditStatus: "updateZxCrJYearCreditEvaAuditStatus",
      getZxSfEquManageList: "getZxSfEquManageList",  //查询
      addZxSfEquManage: "addZxSfEquManage", //新增 
      updateZxSfEquManage: "updateZxSfEquManage",  //修改 
      ureportZxSkReceivingDynamicList: "ureportZxSkReceivingDynamicList",   //删除
      getZxSfProjectEmpMainList: "getZxSfProjectEmpMainList",  //查询
      addZxSfProjectEmpMain: "addZxSfProjectEmpMain", //新增 
      updateZxSfProjectEmpMain: "updateZxSfProjectEmpMain",  //修改 
      batchDeleteUpdateZxSfProjectEmpMain: "batchDeleteUpdateZxSfProjectEmpMain",   //删除
      getZxCtContractListByCompanyId: "getZxCtContractListByCompanyId",
      getZxSkResourceMaterialsListNotRevolving: "getZxSkResourceMaterialsListNotRevolving",
      getZxSfExamItemList: "getZxSfExamItemList",
      getZxSkStockTransferOrderReceiveOrg: "getZxSkStockTransferOrderReceiveOrg",
      ureportZxSkMosResMovStatRep: "ureportZxSkMosResMovStatRep",
      ureportZxSkCustomerOutRes: "ureportZxSkCustomerOutRes",
      ureportZxSkResMoveMonthMPList: 'ureportZxSkResMoveMonthMPList',
      ureportZxSkResWasAdmListGetDataList: "ureportZxSkResWasAdmListGetDataList",
      ureportZxSkResWasAdmListGetTitle: "ureportZxSkResWasAdmListGetTitle",

      //集中结算管控指标输出台账
      getZxSaSettleControlLedgerList: "getZxSaSettleControlLedgerList",
      getZxSaStaticSettleList: "getZxSaStaticSettleList",
      exportZxSaCentralizedSettlement: "exportZxSaCentralizedSettlement",
      getZxSaSettleControlLedgerGcsgYzxList: "getZxSaSettleControlLedgerGcsgYzxList",
      getZxSaSettleControlLedgerGcsgList: "getZxSaSettleControlLedgerGcsgList",

      //累计结算成本比指标填报
      getZxSaCostRatioList: "getZxSaCostRatioList",
      getZxSaCostRatioDetail: "getZxSaCostRatioDetail",
      addZxSaCostRatio: "addZxSaCostRatio",
      updateZxSaCostRatio: "updateZxSaCostRatio",
      batchDeleteUpdateZxSaCostRatio: "batchDeleteUpdateZxSaCostRatio",
      getZxSaCostRatioTotalAmt: "getZxSaCostRatioTotalAmt",

      //物资报表
      //项目月度量差表 
      exportZxSkStockDifMonthQtySumProj: "exportZxSkStockDifMonthQtySumProj",
      exportZxSkStockDifMonthQtySumProjHead: "exportZxSkStockDifMonthQtySumProjHead",
      //项目季度量差表
      exportZxSkStockDifMonthQtySumProjJidu: "exportZxSkStockDifMonthQtySumProjJidu",
      exportZxSkStockDifMonthQtySumProjJiduHead: "exportZxSkStockDifMonthQtySumProjJiduHead",
      //公司量差汇总表1(按物资类别)
      exportZxSkStockDifMonthQtySumComJidu: "exportZxSkStockDifMonthQtySumComJidu",
      exportZxSkStockDifMonthQtySumComJiduHead: "exportZxSkStockDifMonthQtySumComJiduHead",
      //公司量差汇总表1(按项目)
      exportZxSkStockDifMonthQtySumComJiduProj: "exportZxSkStockDifMonthQtySumComJiduProj",
      exportZxSkStockDifMonthQtySumComJiduProjHead: "exportZxSkStockDifMonthQtySumComJiduProjHead",
      //物资收发存季度报表
      exportZxSkStockTransferRptWen: "exportZxSkStockTransferRptWen",
      //周转材料摊销明细帐
      exportZxSkTurnResAmoItemWen: "exportZxSkTurnResAmoItemWen",
      //物资合同与采购对比台账
      exportZxSkContractContrastRptWen: "exportZxSkContractContrastRptWen",
      //周转材料统计报表
      exportZxSkTurnoverTotalRptWen: "exportZxSkTurnoverTotalRptWen",
      //中交物资收发存季度统计报表
      exportZxSkStockTransferRptNewWen: "exportZxSkStockTransferRptNewWen",
      //废旧物资审批台账
      exportZxSkWornOutListWen: "exportZxSkWornOutListWen",

      //负结算数据统计
      getZxSaProjectSettleAuditEarlyWarningList: "getZxSaProjectSettleAuditEarlyWarningList",

      //合同类型结算统计
      exportZxCtContrBalanceRpt: "exportZxCtContrBalanceRpt",
      //工程计量支付汇总表
      exportZxCtProjBalPayTotalRpt: "exportZxCtProjBalPayTotalRpt",
      //业主结算明细表(公路、市政)
      exportZxCtOwnerBalanceListRpt: "exportZxCtOwnerBalanceListRpt",
      exportZxCtOwnerBalanceListRptHead: "exportZxCtOwnerBalanceListRptHead",
      //主合同台账
      exportZxCtContractP1JuRpt: "exportZxCtContractP1JuRpt",
      //建造合同台账
      exportZxCsDqRpt: "exportZxCsDqRpt",
      exportZxctCsdqLineChart: "exportZxctCsdqLineChart",
      //合同偏差统计表
      exportZxCtContrDeviRpt: "exportZxCtContrDeviRpt",
      //合同审批率统计
      exportZxCtContractApprovalPerP2: "exportZxCtContractApprovalPerP2",
      //工程合同（招标）统计表
      psGetZxGcsgCtContrApplyBiddingByUReport: "psGetZxGcsgCtContrApplyBiddingByUReport",
      //合同单价分析表
      getZxGcsgCtPriceSysAndItemByUReport: "getZxGcsgCtPriceSysAndItemByUReport",
      //合同评审台账(工程类)
      psGetZxGcsgCtContrApplyListByUReport: "psGetZxGcsgCtContrApplyListByUReport",
      getZxSkResCategoryMaterialsListResourceNotRevolveSecondResTypeID: "getZxSkResCategoryMaterialsListResourceNotRevolveSecondResTypeID",
      //施工内容及计价规则表
      getZxGcsgCcWorksListProcessByUReport: "getZxGcsgCcWorksListProcessByUReport",
      //对外经营合同台账
      getForeignBusinessContractAccountByUReport: "getForeignBusinessContractAccountByUReport",
      //工程结算台账（公司）
      exportZxSaCompanyAccounts: "exportZxSaCompanyAccounts",
      //工程结算台账（项目）
      exportZxSaProCalculateInfo: "exportZxSaProCalculateInfo",
      //机械结算台账（公司）
      exportZxSaEquipSettleAuditCompRpt: "exportZxSaEquipSettleAuditCompRpt",
      //机械结算台账（项目）
      exportZxSaEquipSettleAuditRpt: "exportZxSaEquipSettleAuditRpt",
      //其它结算台账（公司）
      exportZxSaEquipSettleAuditComp21Rpt: "exportZxSaEquipSettleAuditComp21Rpt",
      //其它结算台账（项目）
      exportZxSaEquipSettleAudit21Rpt: "exportZxSaEquipSettleAudit21Rpt",
      //物资采购结算台账（公司）
      exportZxSaStockSettleAuditP5: "exportZxSaStockSettleAuditP5",
      //物资采购结算台账（项目）
      exportZxSaStockSettleTotalRptColumn: "exportZxSaStockSettleTotalRptColumn",
      exportZxSaStockSettleTotalRptRow: "exportZxSaStockSettleTotalRptRow",
      //物资租赁结算台账（公司）
      exportZxSaEquipSettleAuditCompP5: "exportZxSaEquipSettleAuditCompP5",
      //物资租赁结算台账（项目）
      exportZxSaEquipSettleAuditProjP5: "exportZxSaEquipSettleAuditProjP5",
      //支付项结算情况明细表
      exportZxSaProjPayRpt: "exportZxSaProjPayRpt",
      //支付项结算情况汇总表
      exportZxSaProjPayCompRpt: "exportZxSaProjPayCompRpt",
      //退回结算查询
      getZxSaReturnSettlement: "getZxSaReturnSettlement",
      exportZxSaReturnSettlement: "exportZxSaReturnSettlement",
      //工程类合同结算执行台账
      getZxGcsgSettle: "getZxGcsgSettle",
      exportZxGcsgSettle: "exportZxGcsgSettle",
      //结算应支付台账
      getZxProjectSettleAccountsPayable: "getZxProjectSettleAccountsPayable",
      exportZxProjectSettleAccountsPayable: "exportZxProjectSettleAccountsPayable",
      //完工项目查询
      exportZxSaFinishProjSettleInfo: "exportZxSaFinishProjSettleInfo",
      exportZxCtContractForFinish: "exportZxCtContractForFinish",
      //资料库管理
      getZxZlkFolderLevelListByPermission: "getZxZlkFolderLevelListByPermission",
      addZxZlkFolderLevelByChild: "addZxZlkFolderLevelByChild",
      updateZxZlkFolderLevelByRename: "updateZxZlkFolderLevelByRename",
      checkDeleteZxZlkFolderLevel: "checkDeleteZxZlkFolderLevel",
      cascadeDeleteZxZlkFolderLevel: "cascadeDeleteZxZlkFolderLevel",
      copyAddZxZlkFolderLevel: "copyAddZxZlkFolderLevel",
      moveZxZlkFolderLevel: "moveZxZlkFolderLevel",
      getZxZlkFolderPermissionListBySetUp: "getZxZlkFolderPermissionListBySetUp",
      confirmUpdateZxZlkFolderPermission: "confirmUpdateZxZlkFolderPermission",
      getZxZlkFileTitleListByFolder: "getZxZlkFileTitleListByFolder",
      addZxZlkFileTitleByFolder: "addZxZlkFileTitleByFolder",
      updateZxZlkFileTitleByFolder: "updateZxZlkFileTitleByFolder",
      deleteZxZlkFileTitleByFolder: "deleteZxZlkFileTitleByFolder",
      copyZxZlkFileTitleByFolder: "copyZxZlkFileTitleByFolder",
      cutZxZlkFileTitleByFolder: "cutZxZlkFileTitleByFolder",
      downloadZxZlkFileTitleByDoubleClick: "downloadZxZlkFileTitleByDoubleClick",
      confirmAddZxZlkFileTitleByArchiver: "confirmAddZxZlkFileTitleByArchiver",
      // 成本管理
      getZxCtCpStatisticsFinHighwayEngList: 'getZxCtCpStatisticsFinHighwayEngList',
      ureportZxPuMMReqPlan: "ureportZxPuMMReqPlan",
      batchDeleteUpdateZxSfEquManage: "batchDeleteUpdateZxSfEquManage",
      ureportZxPuMMReqPlan: "ureportZxPuMMReqPlan",
      getZxSfSpecialEmpItemDetailFormList: "getZxSfSpecialEmpItemDetailFormList",
      getZxCtPriceSysItemDataListByItemData: "getZxCtPriceSysItemDataListByItemData",
      getZxCtCpStatisticsFinHighwayYearAllList: "getZxCtCpStatisticsFinHighwayYearAllList",
      batchUpdateZxCtCpStatisticsFinThisPeriodData: "batchUpdateZxCtCpStatisticsFinThisPeriodData",
      getZxCtSubContractLpriceReviewList: "getZxCtSubContractLpriceReviewList",
      batchDeleteUpdateZxCtSubContractLpriceReview: "batchDeleteUpdateZxCtSubContractLpriceReview",

      // 危险源-局
      getZxSfHazardmainJuList: "getZxSfHazardmainJuList",

      getZxSfHazardmainJuList: "getZxSfHazardmainJuList",

      addZxSfHazardmainJu: "addZxSfHazardmainJu",

      updateZxSfHazardmainJu: "updateZxSfHazardmainJu",

      batchDeleteUpdateZxSfHazardmainJu: "batchDeleteUpdateZxSfHazardmainJu",

      getZxStMiddleItemList: "getZxStMiddleItemList",
      addZxStMiddleItem: "addZxStMiddleItem",
      batchDeleteUpdateZxStMiddleItem: "batchDeleteUpdateZxStMiddleItem",
      updateZxStMiddleItem: "updateZxStMiddleItem",


      // 安全查询
      getFormZxSfCheckJuInfo: "getFormZxSfCheckJuInfo",
      getZxSfProEmpMainWorkAgJuInfo: "getZxSfProEmpMainWorkAgJuInfo",
      getFsContractReviewDetailList: "getFsContractReviewDetailList",
      getZxCtFsContractQiTaList: "getZxCtFsContractQiTaList",
      getZxCtFsContractYunShuList: "getZxCtFsContractYunShuList",
      getZxCtContractListByOrgId: 'getZxCtContractListByOrgId',
      getZxCtSuppliesContrShopListListByContIDAddPage:"getZxCtSuppliesContrShopListListByContIDAddPage"
    }
  }
}

var servers = window.configs.apis, cassifyApis = {};
for (var server in servers) {
  var serverApis = servers[server];
  if (typeof serverApis === "string") {
    cassifyApis[server] = serverApis;
  } else {
    for (var api in serverApis) {
      var apiName = serverApis[api];
      cassifyApis[api] = server + '/' + apiName;
    }
  }
}
window.configs.apis = cassifyApis;

// 获取流程表单左上角列表接口的方法
window.qnnGetTodoDataFetchConfig = function (curList, props) {
  var url = props.match.url;
  var myPublic = props.myPublic.appInfo.mainModule;
  if (url === myPublic + "Closed") {
    return {
      apiName: "getZxHasReadList"
    }
  }
  if (url === myPublic + "Undocumented") {
    return {
      apiName: "getZxReadList"
    }
  }
  return {
    apiName: curList === "todo" ? "getTodoList" : "getHasTodoList"
  }
};

//将config中的接口格式化为json输出到控制台直接复制到接口管理的输入框保存即可
// function configApisToJsonStr(){
//     console.log(JSON.stringify(window.configs.apis, null, 4))
// }
// configApisToJsonStr() 
