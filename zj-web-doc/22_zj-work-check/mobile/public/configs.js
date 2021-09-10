window.configs = {
  dev: false,
  debugConsole: false,
  // domain: "http://weixin.fheb.cn:99/apiwoa/",
  // domain: "http://wya.apih5.com/web/",
  //  domain: "http://124.152.210.148:8087/apilanzhou/",
  // domain: "http://dltest.apih5.com/web/",
  // domain: " http://sx.apih5.com:8082/sxoa/",
   domain: "http://192.168.1.223:8080/web/",
  // domain: "https://sjz.apih5.com/apisjzoa/",
  // domain: "http://114.116.12.219/apisxdeh/",
  appInfo: {
  logo: "http://47.96.150.231/logo/logo.png",
    id: "zj_qyh_woa_id", //zj_fwh_woa_zl_id sjz_qyh_woa_id  zj_qyh_woa_id
    // id: "sx_qyh_woa_id",//zj_fwh_woa_zl_id zj_qyh_woa_id
    title: "xxx公司",
    copyright: "@xxx公司",
    name: "sjzoa",
    loginType: "1", //1:普通账号密码；2：手机验证码快捷登录；3:企业号授权；4:服务号授权；5、扫码登录
    mainModule: "/app/",
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
      "openLocation"
    ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
  },
  apis: {
    login: "user/login", //登录
    getSysUserListByBg: "user/getSysUserListByBg",
    addSysUserInfoByBg: "user/addSysUserInfoByBg",
    updateSysUserInfoByBg: "user/updateSysUserInfoByBg",
    deleteSysUserInfoByBg: "user/deleteSysUserInfoByBg",
    upload: "upload", //上传接口
    getCorpInfo: "getCorpInfo", //获取公司信息
    getRouteData: "getRouteData", //获取路由数据
    getSysDepartmentUserAllTree: "getSysDepartmentUserAllTreeByZj", //获取通讯录树结构
    getSysDepartmentAllTree: "getWorkerTreeBySysDepartmentByZj?noPersonnel=1", //班组管理使用
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
    getZjFlowPartyFeeDetailAllList: "getZjFlowPartyFeeDetailAllList", //党费二级下拉
    getZjFlowPartyFeeUnitList: "getZjFlowPartyFeeUnitList", //申请单位

    //测试使用
    getSxDocumentInfoList: "getSxDocumentInfoList",
    getSysDepartmentList: "getSysDepartmentList",
	
	
	
	
	getZjJdjhYearSupPlanDetails:"getZjJdjhYearSupPlanDetails",
    getZjJdjhYearSupMatterList:"getZjJdjhYearSupMatterList",
    submitZjJdjhYearSupPlan:"submitZjJdjhYearSupPlan",

    getZjJdjhSupPlanExecutiveDetail:"getZjJdjhSupPlanExecutiveDetail",//执行情况待办查详情
	getMatterAndExecuteDetailList:"getMatterAndExecuteDetailList",//执行情况回复列表
	updateZjJdjhSupPlanExecutive:"updateZjJdjhSupPlanExecutive",//执行情况审批
	
	
	

    //党费流程下拉
    getZjFlowPartyFeeUnitList: "getZjFlowPartyFeeUnitList",
    getZjFlowPartyFeeDetailAllList: "getZjFlowPartyFeeDetailAllList"
  }
};
