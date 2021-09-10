window.configs = {
  dev: false,
  debugConsole: false,
  canChangeLanguage: false,
  loginFormIncVerCode: false,
  language: "zh_CN",
  //dev主题支持自定义设置值 
  //theme: "dev",
  // 公司内请求时，将 C:\Windows\System32\drivers\etc\hosts 添加如下内容
  // 192.168.1.13  test.bjzxkj.com
  domain: "http://192.168.1.31:31055/",
  ntkoAddress: "http://cdn.apih5.com/ntko/zj",
  //模板下载地址
  templateAddress: "http://192.168.1.17:19090/system-server/downloadFile?filePath=import/",
  bugDomain: "",
  projectId: "apih5-web",
  pcPageUrl: "",
  titleUS: "",
  appInfo: {
    ureport: 'http://192.168.1.31/apiUreport/',
    logo: "http://weixin.fheb.cn:99/icon/logo.png",
    id: "zj_xiamengs_renzi",
    // id: "zj_erp",//zjerp
    title: "人事智能化管理系统",
    copyright: "中交一公局厦门公司",
    name: "",
    // webCodeLoginType:"6",
    loginType: "1",
    mainModule: "/xmrz/",
    helpHref: "",

    //登录服务信息
    grant_type: "password",
    client_id: "crcc-laowu",
    client_secret: "1234567",
  },
  loginPageConfig: {
    bgImgUrl: ["img1.png", "img2.png"],
    //背景图片
    rowText: ["人事智能化管理系统", "中交一公局厦门公司"] //文字描述 一项即一行

  },
  //是否可以切换项目 默认true
  canChangeProject: true,
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
      me: "user/me",
      getSysWebBg: "getSysWebBg",
      "unLock": "user/unLock", // 解锁用户
      /* ----- ↓↓↓  通用接口开始 ↓↓↓ --------*/
      getSysUserFlowRoleTree: "getSysUserFlowRoleTree", //流程权限左侧树结构
      getSysUserFlowLeftTree: "getSysUserFlowLeftTree", //流程权限左侧树结构
      getSysUserFlowSetUpTree: "getSysUserFlowSetUpTree", //流程权限左侧树结构
      addSysFlowUserByFlow: "addSysFlowUserByFlow", //新增流程用户选择表
      deleteUpdateSysFlowUser: "deleteUpdateSysFlowUser", //修改流程用户选择表
      ntkoUploadOffice: "upload", //修改流程用户选择表

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
      //上传接口
      getCorpInfo: "getCorpInfo",
      //获取公司信息
      getRouteData: "getRouteData",

      //获取登录验证码
      getSysWebBgDetails: "getSysWebBgDetails",
      //获取接口
      updateSysWebBg: "updateSysWebBg",
      // 组织机构-部门
      addSysDepartment: "addSysDepartmentXmrz",
      //新增部门
      updateSysDepartment: "updateSysDepartment",
      //修改部门信息
      batchDeleteUpdateSysDepartment: "batchDeleteUpdateSysDepartment",
      //批量删除部门
      moveUpdateSysDepartment: "moveUpdateSysDepartment",
      getSysDepartmentCurrentTree: "getSysDepartmentCurrentTree",
      getSysDepartmentAllTree: "getSysDepartmentCurrentTree",
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
      getSysCompanyBySelect: "getSysCompanyBySelect", // 危险源台账（公司）
      getTodoList: "getZxTodoList",
      getHasTodoList: "getZxHasTodoList",
      // 权限
      updateSysRoleMenu: "updateSysRoleMenu",
      //切换公司
      getZxWtPermissionListByProject: "getZxWtPermissionListByProject",
      changeZxWtProject: 'changeZxWtProject',
      getBaseJobLevelList: "getBaseJobLevelList",
      getBaseProjectConfList: 'getBaseProjectConfList',
      addBaseProjectConf: "addBaseProjectConf",
      getBaseCodeTreeByLevel: "getBaseCodeTreeByLevel",
      updateBaseProjectConf: "updateBaseProjectConf",
      batchDeleteUpdateBaseProjectConf: "batchDeleteUpdateBaseProjectConf",
      getBaseJobLevelList: "getBaseJobLevelList",
      updateBaseJobLevel: "updateBaseJobLevel",
      addBaseJobLevel: "addBaseJobLevel",
      batchDeleteUpdateBaseJobLevel: "batchDeleteUpdateBaseJobLevel",
      getSysMenuTreeByTop: "getSysMenuTreeByTop",
      getSysProjectDeptConfList: "getSysProjectDeptConfList",
      addSysProjectDeptConf: "addSysProjectDeptConf",
      updateSysProjectDeptConf: "updateSysProjectDeptConf",
      batchDeleteUpdateSysProjectDeptConf: "batchDeleteUpdateSysProjectDeptConf",
      getSysProjectDeptConfSelect: "getSysProjectDeptConfSelect",
      getSysProjectDeptConfByOverman: "getSysProjectDeptConfByOverman",
      getSysComDeptProById: 'getSysComDeptProById',
    },
    //业务服务
    "xm-salary-108": {
      // 暂时走业务代码-流程相关
      createOpenFlow: "createOpenFlow",
      openFlow: "openFlow",
      //提交流程追加的节点
      openFlowHas: "openFlow?hasFlag=1",
      openFlowByReport: "openFlowByReport",
      actionFlow: "actionFlow",


      //项目管理
      getZxCtWorksWorkNameTree: "getZxCtWorksWorkNameTree",
      //人员列表接口 
      //学历接口
      //工作履历
      //专业技术
      //合同管理
      //培训情况
      //家庭
      //健康
      //书
      "pcGetZjXmSalaryUserExtensionList": "pcGetZjXmSalaryUserExtensionList",
      "addZjXmSalaryUserExtension": "addZjXmSalaryUserExtension",
      "updateZjXmSalaryUserExtension": "updateZjXmSalaryUserExtension",
      "pcGetZjXmSalaryUserExtensionDetails": "pcGetZjXmSalaryUserExtensionDetails",
      "getZjXmSalaryEducationBackgroundList": "getZjXmSalaryEducationBackgroundList",
      "addZjXmSalaryEducationBackground": "addZjXmSalaryEducationBackground",
      "updateZjXmSalaryEducationBackground": "updateZjXmSalaryEducationBackground",
      "batchDeleteUpdateZjXmSalaryEducationBackground": "batchDeleteUpdateZjXmSalaryEducationBackground",
      "getZjXmSalaryWorkExperienceList": "getZjXmSalaryWorkExperienceList",
      "addZjXmSalaryWorkExperience": "addZjXmSalaryWorkExperience",
      "updateZjXmSalaryWorkExperience": "updateZjXmSalaryWorkExperience",
      "batchDeleteUpdateZjXmSalaryWorkExperience": "batchDeleteUpdateZjXmSalaryWorkExperience",
      "getZjXmSalaryProfessionalTechnologyList": "getZjXmSalaryProfessionalTechnologyList",
      "addZjXmSalaryProfessionalTechnology": "addZjXmSalaryProfessionalTechnology",
      "updateZjXmSalaryProfessionalTechnology": "updateZjXmSalaryProfessionalTechnology",
      "batchDeleteUpdateZjXmSalaryProfessionalTechnology": "batchDeleteUpdateZjXmSalaryProfessionalTechnology",
      "getZjXmSalaryContractManagementList": "getZjXmSalaryContractManagementList",
      "addZjXmSalaryContractManagement": "addZjXmSalaryContractManagement",
      "updateZjXmSalaryContractManagement": "updateZjXmSalaryContractManagement",
      "batchDeleteUpdateZjXmSalaryContractManagement": "batchDeleteUpdateZjXmSalaryContractManagement",
      "getZjXmSalaryTrainingSituationList": "getZjXmSalaryTrainingSituationList",
      "addZjXmSalaryTrainingSituation": "addZjXmSalaryTrainingSituation",
      "updateZjXmSalaryTrainingSituation": "updateZjXmSalaryTrainingSituation",
      "batchDeleteUpdateZjXmSalaryTrainingSituation": "batchDeleteUpdateZjXmSalaryTrainingSituation",
      "getZjXmSalaryFamilyBackgroundList": "getZjXmSalaryFamilyBackgroundList",
      "addZjXmSalaryFamilyBackground": "addZjXmSalaryFamilyBackground",
      "updateZjXmSalaryFamilyBackground": "updateZjXmSalaryFamilyBackground",
      "batchDeleteUpdateZjXmSalaryFamilyBackground": "batchDeleteUpdateZjXmSalaryFamilyBackground",
      "getZjXmSalaryHealthConditionList": "getZjXmSalaryHealthConditionList",
      "addZjXmSalaryHealthCondition": "addZjXmSalaryHealthCondition",
      "updateZjXmSalaryHealthCondition": "updateZjXmSalaryHealthCondition",
      "batchDeleteUpdateZjXmSalaryHealthCondition": "batchDeleteUpdateZjXmSalaryHealthCondition",
      "batchDeleteUpdateZjXmSalaryCertificateManagement": "batchDeleteUpdateZjXmSalaryCertificateManagement",
      "getZjXmSalaryCertificateManagementList": "getZjXmSalaryCertificateManagementList",
      "addZjXmSalaryCertificateManagement": "addZjXmSalaryCertificateManagement",
      "updateZjXmSalaryCertificateManagement": "updateZjXmSalaryCertificateManagement",
      "getZjXmSalaryPositionLevelSalarySelect": "getZjXmSalaryPositionLevelSalarySelect",
      // 人员信息 tab批量保存 ---------------------------------------------------------------------------------------------
      // 学历情况
      "batchAddZjXmSalaryEducationBackground": "batchAddZjXmSalaryEducationBackground",
      // 工作履历
      "batchAddZjXmSalaryWorkExperience": "batchAddZjXmSalaryWorkExperience",
      // 专业技术
      "batchAddZjXmSalaryProfessionalTechnology": "batchAddZjXmSalaryProfessionalTechnology",
      // 合同管理
      "batchAddZjXmSalaryContractManagement": "batchAddZjXmSalaryContractManagement",
      // 培训情况
      "batchAddZjXmSalaryTrainingSituation": "batchAddZjXmSalaryTrainingSituation",
      // 家庭状况
      "batchAddZjXmSalaryFamilyBackground": "batchAddZjXmSalaryFamilyBackground",
      // 健康情况
      "batchAddZjXmSalaryHealthCondition": "batchAddZjXmSalaryHealthCondition",
      // 证书管理
      "batchAddZjXmSalaryCertificateManagement": "batchAddZjXmSalaryCertificateManagement",
      // 政治面貌
      zjXmSalaryEmployApprovalId: "zjXmSalaryEmployApprovalId",
      // ----------------------------------------------------------------------------------------------------------------
      // 聘(录)报审审批
      getZjXmSalaryEmployApprovalList: "getZjXmSalaryEmployApprovalList", // 查询录聘用报审审批
      getZjXmSalaryEmployApprovalDetail: "getZjXmSalaryEmployApprovalDetail", // 查询详情录聘用报审审批
      addZjXmSalaryEmployApproval: "addZjXmSalaryEmployApproval", // 新增录聘用报审审批
      updateZjXmSalaryEmployApproval: "updateZjXmSalaryEmployApproval", // 修改录聘用报审审批
      batchDeleteUpdateZjXmSalaryEmployApproval: "batchDeleteUpdateZjXmSalaryEmployApproval", // 批量删除录聘用报审审批
      getZjXmSalaryEmployApprovalUserList: "getZjXmSalaryEmployApprovalUserList",
      // --------------------------------------------------------------------------------------------------------------
      getPositionLevelSalaryDetails: 'getPositionLevelSalaryDetails', // 查询岗薪

      getZjXmSalaryUserExtensionHistoryDetail: "getZjXmSalaryUserExtensionHistoryDetail",
      getZjXmSalaryEducationBackgroundHistoryList: "getZjXmSalaryEducationBackgroundHistoryList",
      batchEducationHistory: "batchEducationHistory",
      batchWorkExperienceHistory: "batchWorkExperienceHistory",
      getZjXmSalaryWorkExperienceHistoryList: "getZjXmSalaryWorkExperienceHistoryList",
      batchAddUpdateZjXmSalaryProfessionalTechnologyHistory: "batchAddUpdateZjXmSalaryProfessionalTechnologyHistory",
      getZjXmSalaryProfessionalTechnologyHistoryList: "getZjXmSalaryProfessionalTechnologyHistoryList",
      batchDeleteZjXmSalaryEducationBackgroundHistory: "batchDeleteZjXmSalaryEducationBackgroundHistory",
      batchDeleteZjXmSalaryWorkExperienceHistory: "batchDeleteZjXmSalaryWorkExperienceHistory",
      batchDeleteZjXmSalaryProfessionalTechnologyHistory: "batchDeleteZjXmSalaryProfessionalTechnologyHistory",
      getZjXmSalaryContractManagementHistoryList: "getZjXmSalaryContractManagementHistoryList",
      batchAddUpdateZjXmSalaryContractManagementHistory: "batchAddUpdateZjXmSalaryContractManagementHistory",
      batchDeleteZjXmSalaryContractManagementHistory: "batchDeleteZjXmSalaryContractManagementHistory",

      getZjXmSalaryTrainingSituationHistoryList: "getZjXmSalaryTrainingSituationHistoryList",
      batchAddUpdateZjXmSalaryTrainingSituationHistory: "batchAddUpdateZjXmSalaryTrainingSituationHistory",
      batchDeleteZjXmSalaryTrainingSituationHistory: "batchDeleteZjXmSalaryTrainingSituationHistory",

      getZjXmSalaryFamilyBackgroundHistoryList: "getZjXmSalaryFamilyBackgroundHistoryList",
      batchAddUpdateZjXmSalaryFamilyBackgroundHistory: "batchAddUpdateZjXmSalaryFamilyBackgroundHistory",
      batchDeleteUpdateZjXmSalaryFamilyBackgroundHistory: "batchDeleteUpdateZjXmSalaryFamilyBackgroundHistory",

      getZjXmSalaryHealthConditionHistoryList: "getZjXmSalaryHealthConditionHistoryList",
      batchAddUpdateZjXmSalaryHealthConditionHistory: "batchAddUpdateZjXmSalaryHealthConditionHistory",
      batchDeleteZjXmSalaryHealthConditionHistory: "batchDeleteZjXmSalaryHealthConditionHistory",

      getZjXmSalaryCertificateManagementHistoryList: "getZjXmSalaryCertificateManagementHistoryList",
      batchAddUpdateZjXmSalaryCertificateManagementHistory: "batchAddUpdateZjXmSalaryCertificateManagementHistory",
      batchDeleteZjXmSalaryCertificateManagementHistory: "batchDeleteZjXmSalaryCertificateManagementHistory",

      // 政治面貌 tab
      getZjXmSalaryPoliticalList: "getZjXmSalaryPoliticalList",
      getZjXmSalaryPoliticalDetail: "getZjXmSalaryPoliticalDetail",
      addZjXmSalaryPolitical: "addZjXmSalaryPolitical",
      updateZjXmSalaryPolitical: "updateZjXmSalaryPolitical",
      batchDeleteUpdateZjXmSalaryPolitical: "batchDeleteUpdateZjXmSalaryPolitical",
      getZjXmSalaryPoliticalHistoryList: "getZjXmSalaryPoliticalHistoryList",
      getZjXmSalaryPoliticalHistoryDetail: "getZjXmSalaryPoliticalHistoryDetail",
      addZjXmSalaryPoliticalHistory: "addZjXmSalaryPoliticalHistory",
      updateZjXmSalaryPoliticalHistory: "updateZjXmSalaryPoliticalHistory",
      batchDeleteUpdateZjXmSalaryPoliticalHistory: "batchDeleteUpdateZjXmSalaryPoliticalHistory",
      // -----------------岗薪报审审批----------------------------------------------------------
      addZjXmSalaryUserExtensionList: "addZjXmSalaryUserExtensionList",
      batchDeleteApproval: "batchDeleteApproval",
      getZjXmSalaryEmployApprovalUserList: "getZjXmSalaryEmployApprovalUserList	",
      getZjXmSalaryUserExtensionList: "getZjXmSalaryUserExtensionList",
      getZjXmSalaryUserExtensionHistoryList: "getZjXmSalaryUserExtensionHistoryList",
      getWorkZjXmSalaryUserExtensionHistoryDetail: "getWorkZjXmSalaryUserExtensionHistoryDetail",
      addZjXmSalaryUserExtensionHistory: "addZjXmSalaryUserExtensionHistory",
      getWorkManagementUserExtensionList: "getWorkManagementUserExtensionList",
      getWorkManagementUserExtensionDetails: "getWorkManagementUserExtensionDetails",
      updateZjXmSalaryUserExtensionHistory: "updateZjXmSalaryUserExtensionHistory",
      batchDeleteZjXmSalaryUserExtensionHistory: "batchDeleteZjXmSalaryUserExtensionHistory",
      batchDeleteZjXmSalaryUserExtensionHistoryEmploy: "batchDeleteZjXmSalaryUserExtensionHistoryEmploy",
      // --------------------------------------------------------------------------------------
      getZjXmSalaryOvermanApprovalList: "getZjXmSalaryOvermanApprovalList",
      addZjXmSalaryOvermanApproval: "addZjXmSalaryOvermanApproval",
      updateZjXmSalaryOvermanApproval: "updateZjXmSalaryOvermanApproval",
      getZjXmSalaryOvermanSubList: "getZjXmSalaryOvermanSubList",
      batchZjXmSalaryOvermanSub: "batchZjXmSalaryOvermanSub",
      batchDeleteZjXmSalaryOvermanSub: "batchDeleteZjXmSalaryOvermanSub",
      batchDeleteZjXmSalaryOvermanApproval: "batchDeleteZjXmSalaryOvermanApproval",
      getZjXmSalaryUserExtensionDetails: "getZjXmSalaryUserExtensionDetails",
      addZjXmSalaryUserReshuffle: "addZjXmSalaryUserReshuffle",
      updateZjXmSalaryUserReshuffle: "updateZjXmSalaryUserReshuffle",
      getZjXmSalaryUserReshuffleList: "getZjXmSalaryUserReshuffleList",
      getZjXmSalaryUserReshuffleDetail: "getZjXmSalaryUserReshuffleDetail",
      batchDeleteZjXmSalaryUserReshuffle: "batchDeleteZjXmSalaryUserReshuffle",
      checkUserStatus: "checkUserStatus",
      changeConfirmZjXmSalaryUserReshuffle: "changeConfirmZjXmSalaryUserReshuffle",
      getZjXmSalaryLevelSalaryLogList: "getZjXmSalaryLevelSalaryLogList",
      checkRequiredTab: "checkRequiredTab",
      addEmployUserExtensionHistory: "addEmployUserExtensionHistory",
      updateEmployUserExtensionHistory: "updateEmployUserExtensionHistory",
      checkQuitReRecruit: "checkQuitReRecruit",
      checkIdNumber: 'checkIdNumber',
      getQuitUserExtensionList: 'getQuitUserExtensionList',
      // 2021.07.15---税率表维护
      getZjXmSalaryTaxRateTableList: "getZjXmSalaryTaxRateTableList",
      addZjXmSalaryTaxRateTable: "addZjXmSalaryTaxRateTable",
      updateZjXmSalaryTaxRateTable: "updateZjXmSalaryTaxRateTable",
      deleteZjXmSalaryTaxRateTable: "deleteZjXmSalaryTaxRateTable",
      // 薪酬方案
      getZjXmSalaryCompensationPlanList: "getZjXmSalaryCompensationPlanList",
      addZjXmSalaryCompensationPlan: "addZjXmSalaryCompensationPlan",
      deleteZjXmSalaryCompensationPlan: "deleteZjXmSalaryCompensationPlan",
      getZjXmSalaryTaxRateTableListStatus: "getZjXmSalaryTaxRateTableListStatus",
      updateZjXmSalaryCompensationPlan: "updateZjXmSalaryCompensationPlan",
      // 方案详情
      getZjXmSalaryCompensationPlanDetailList: "getZjXmSalaryCompensationPlanDetailList",
      addZjXmSalaryCompensationPlanDetail: "addZjXmSalaryCompensationPlanDetail",
      batchDeleteUpdateZjXmSalaryCompensationPlanDetail: "batchDeleteUpdateZjXmSalaryCompensationPlanDetail",
      getZjXmSalarySalaryItemDictionaryListStatus: "getZjXmSalarySalaryItemDictionaryListStatus",
      // 人员
      getZjXmSalaryCompensationPlanPersonnelList: "getZjXmSalaryCompensationPlanPersonnelList",
      batchAddZjXmSalaryCompensationPlanPersonnel: "batchAddZjXmSalaryCompensationPlanPersonnel",
      batchDeleteUpdateZjXmSalaryCompensationPlanPersonnel: "batchDeleteUpdateZjXmSalaryCompensationPlanPersonnel",
      getZjXmSalaryCompensationPlanPersonnelListAdd: "getZjXmSalaryCompensationPlanPersonnelListAdd",
      // 薪酬字典
      getZjXmSalarySalaryItemDictionaryList: "getZjXmSalarySalaryItemDictionaryList",
      addZjXmSalarySalaryItemDictionary: "addZjXmSalarySalaryItemDictionary",
      batchDeleteUpdateZjXmSalarySalaryItemDictionary: "batchDeleteUpdateZjXmSalarySalaryItemDictionary",
      updateZjXmSalarySalaryItemDictionary: "updateZjXmSalarySalaryItemDictionary",

      //公积金抵扣上限 
      getZjXmSalaryProvidentFundUpLimitList: "getZjXmSalaryProvidentFundUpLimitList",
      addZjXmSalaryProvidentFundUpLimit: 'addZjXmSalaryProvidentFundUpLimit',
      batchDeleteUpdateZjXmSalaryProvidentFundUpLimit: 'batchDeleteUpdateZjXmSalaryProvidentFundUpLimit',
      updateZjXmSalaryProvidentFundUpLimit: "updateZjXmSalaryProvidentFundUpLimit",
      //企业年金抵扣上限 
      getZjXmSalaryEnterpriseAnnualSalaryUpLimitList: 'getZjXmSalaryEnterpriseAnnualSalaryUpLimitList',
      addZjXmSalaryEnterpriseAnnualSalaryUpLimit: 'addZjXmSalaryEnterpriseAnnualSalaryUpLimit',
      batchDeleteUpdateZjXmSalaryEnterpriseAnnualSalaryUpLimit: 'batchDeleteUpdateZjXmSalaryEnterpriseAnnualSalaryUpLimit',
      updateZjXmSalaryEnterpriseAnnualSalaryUpLimit: "updateZjXmSalaryEnterpriseAnnualSalaryUpLimit",
      // 公积金方案设置
      getZjXmSalaryProvidentFundSchemeList: 'getZjXmSalaryProvidentFundSchemeList',
      addZjXmSalaryProvidentFundScheme: 'addZjXmSalaryProvidentFundScheme',
      updateZjXmSalaryProvidentFundScheme: 'updateZjXmSalaryProvidentFundScheme',
      batchDeleteUpdateZjXmSalaryProvidentFundScheme: 'batchDeleteUpdateZjXmSalaryProvidentFundScheme',
      // 社保方案设置
      getZjXmSalarySocialSecuritySchemeList: 'getZjXmSalarySocialSecuritySchemeList',
      addZjXmSalarySocialSecurityScheme: 'addZjXmSalarySocialSecurityScheme',
      updateZjXmSalarySocialSecurityScheme: 'updateZjXmSalarySocialSecurityScheme',
      batchDeleteUpdateZjXmSalarySocialSecurityScheme: 'batchDeleteUpdateZjXmSalarySocialSecurityScheme',
      // 公积金维护
      getZjXmSalaryProvidentFundManagementList: 'getZjXmSalaryProvidentFundManagementList',
      addZjXmSalaryProvidentFundManagement: 'addZjXmSalaryProvidentFundManagement',
      updateZjXmSalaryProvidentFundManagement: "updateZjXmSalaryProvidentFundManagement",
      batchDeleteUpdateZjXmSalaryProvidentFundManagement: "batchDeleteUpdateZjXmSalaryProvidentFundManagement",
      getZjXmSalaryProvidentFundSchemeListStatus: "getZjXmSalaryProvidentFundSchemeListStatus",
      importZjXmSalaryProvidentFundManagementDetail: "importZjXmSalaryProvidentFundManagementDetail",
      getZjXmSalaryProvidentFundManagementDetailList: "getZjXmSalaryProvidentFundManagementDetailList",
      batchDeleteUpdateZjXmSalaryProvidentFundManagementDetail: "batchDeleteUpdateZjXmSalaryProvidentFundManagementDetail",
      updateZjXmSalaryProvidentFundManagementDetail: 'updateZjXmSalaryProvidentFundManagementDetail',
      getZjXmSalaryProvidentFundManagementDetailListAdd: 'getZjXmSalaryProvidentFundManagementDetailListAdd',
      addZjXmSalaryProvidentFundManagementDetail: "addZjXmSalaryProvidentFundManagementDetail",
      checkAddZjXmSalaryProvidentFundManagementDetail: 'checkAddZjXmSalaryProvidentFundManagementDetail',
      // 社保维护
      getZjXmSalarySocialSecurityManagementList: 'getZjXmSalarySocialSecurityManagementList',
      addZjXmSalarySocialSecurityManagement: 'addZjXmSalarySocialSecurityManagement',
      updateZjXmSalarySocialSecurityManagement: 'updateZjXmSalarySocialSecurityManagement',
      batchDeleteUpdateZjXmSalarySocialSecurityManagement: 'batchDeleteUpdateZjXmSalarySocialSecurityManagement',
      getZjXmSalarySocialSecuritySchemeListStatus: 'getZjXmSalarySocialSecuritySchemeListStatus',
      importZjXmSalarySocialSecurityManagementDetail: "importZjXmSalarySocialSecurityManagementDetail",
      getZjXmSalarySocialSecurityManagementDetailList: 'getZjXmSalarySocialSecurityManagementDetailList',
      getZjXmSalarySocialSecurityManagementDetailListAdd: 'getZjXmSalarySocialSecurityManagementDetailListAdd',
      checkAddZjXmSalarySocialSecurityManagementDetail: 'checkAddZjXmSalarySocialSecurityManagementDetail',
      updateZjXmSalarySocialSecurityManagementDetail: 'updateZjXmSalarySocialSecurityManagementDetail',
      batchDeleteUpdateZjXmSalarySocialSecurityManagementDetail: 'batchDeleteUpdateZjXmSalarySocialSecurityManagementDetail',
      addZjXmSalarySocialSecurityManagementDetail: 'addZjXmSalarySocialSecurityManagementDetail',
      checkZjXmSalaryProvidentFundSchemeArea: 'checkZjXmSalaryProvidentFundSchemeArea',
      checkZjXmSalarySocialSecuritySchemeArea: 'checkZjXmSalarySocialSecuritySchemeArea',
      checkZjXmSalarySocialSecurityManagementMonth: 'checkZjXmSalarySocialSecurityManagementMonth',
      checkZjXmSalaryProvidentFundManagementMonth: 'checkZjXmSalaryProvidentFundManagementMonth',
      getZjXmSalaryCompensationManagerList: 'getZjXmSalaryCompensationManagerList',
      // 薪酬数据维护
      getMaintainPlanList: 'getMaintainPlanList',
      getZjXmSalaryCompensationMaintainList: 'getZjXmSalaryCompensationMaintainList',
      addZjXmSalaryCompensationManager: 'addZjXmSalaryCompensationManager',
      updateZjXmSalaryCompensationManager: 'updateZjXmSalaryCompensationManager',
      updateZjXmSalaryCompensationPlanDetail: 'updateZjXmSalaryCompensationPlanDetail',
      checkZjXmSalaryCompensationPlanDetail: 'checkZjXmSalaryCompensationPlanDetail',
      getZjXmSalaryCompensationPlanDetailTaxCalculationMethod: 'getZjXmSalaryCompensationPlanDetailTaxCalculationMethod',
      batchUpdateZjXmSalaryCompensationMaintain: 'batchUpdateZjXmSalaryCompensationMaintain',
      getExpressionTabData: 'getExpressionTabData',
      // 启用状态
      updateZjXmSalaryTaxRateTableStatus: 'updateZjXmSalaryTaxRateTableStatus',
      updateZjXmSalarySalaryItemDictionaryStatus: 'updateZjXmSalarySalaryItemDictionaryStatus',
      deleteZjXmSalarySalaryItemDictionary: 'deleteZjXmSalarySalaryItemDictionary',
      updateZjXmSalaryCompensationPlanStatus: 'updateZjXmSalaryCompensationPlanStatus',
      updateZjXmSalaryProvidentFundSchemeStatus: 'updateZjXmSalaryProvidentFundSchemeStatus',
      deleteZjXmSalaryProvidentFundScheme: 'deleteZjXmSalaryProvidentFundScheme',
      updateZjXmSalarySocialSecuritySchemeStatus: 'updateZjXmSalarySocialSecuritySchemeStatus',
      deleteZjXmSalarySocialSecurityScheme: 'deleteZjXmSalarySocialSecurityScheme',
      checkZjXmSalaryTaxRateTableDeleteUpdate: 'checkZjXmSalaryTaxRateTableDeleteUpdate',
      checkZjXmSalarySalaryItemDictionaryDeleteUpdate: 'checkZjXmSalarySalaryItemDictionaryDeleteUpdate',
      checkZjXmSalaryCompensationPlanDeleteUpdate: 'checkZjXmSalaryCompensationPlanDeleteUpdate',
      checkZjXmSalaryProvidentFundSchemeDeleteUpdate: 'checkZjXmSalaryProvidentFundSchemeDeleteUpdate',
      checkZjXmSalarySocialSecuritySchemeDeleteUpdate: 'checkZjXmSalarySocialSecuritySchemeDeleteUpdate',
      getZjXmSalaryOvermanApprovalDetail: "getZjXmSalaryOvermanApprovalDetail",
      checkZjXmSalarySocialSecuritySchemeDeleteUpdate: 'checkZjXmSalarySocialSecuritySchemeDeleteUpdate',
      deleteZjXmSalarySocialSecurityManagement: 'deleteZjXmSalarySocialSecurityManagement',
      updateZjXmSalarySocialSecurityManagementStatus: 'updateZjXmSalarySocialSecurityManagementStatus',
      checkZjXmSalarySocialSecurityManagementDeleteUpdate: 'checkZjXmSalarySocialSecurityManagementDeleteUpdate',
      deleteZjXmSalaryProvidentFundManagement: 'deleteZjXmSalaryProvidentFundManagement',
      checkZjXmSalaryProvidentFundManagementDeleteUpdate: 'checkZjXmSalaryProvidentFundManagementDeleteUpdate',
      updateZjXmSalaryProvidentFundManagementStatus: 'updateZjXmSalaryProvidentFundManagementStatus',
      getZjXmSalaryCompensationMaintainFormula: 'getZjXmSalaryCompensationMaintainFormula',
      deleteZjXmSalaryCompensationManager: 'deleteZjXmSalaryCompensationManager',
      batchDeleteUpdateZjXmSalaryUserExtension: "batchDeleteUpdateZjXmSalaryUserExtension"

    }
  }
};

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

//将config中的接口格式化为json输出到控制台直接复制到接口管理的输入框保存即可
// function configApisToJsonStr(){
//     console.log(JSON.stringify(window.configs.apis, null, 4))
// }
// configApisToJsonStr() 
