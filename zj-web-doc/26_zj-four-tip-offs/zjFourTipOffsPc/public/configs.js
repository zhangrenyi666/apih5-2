window.configs = {
	dev: false,
	debugConsole: false,
	// domain: "http://192.168.1.110:8080/web/",
	// domain: "http://192.168.1.118:8080/web/",
	// domain: "http://127.0.0.1:8080/web/",
	// domain: "http://test.apih5.com/apizjsjjb/",
	domain: "http://jb.ccfourth.com/apijb/",
	appInfo: {
		ureport: 'http://192.168.1.110:8081/ureport/',
		logo: "http://47.96.150.231/logo/logo.png",
		id:"zj_sj_jb_ht",
		title: "中交四公局",
		copyright: "中交四公局",
		name: "jb",
		loginType: "1",  //1:普通账号密码；2：手机验证码快捷登录；3:企业号授权；4:服务号授权；5、扫码登录
		mainModule: "/jb/",
		helpHref: "",
		leftTopTitle: [{
            text: "中交四公局",
            style: { textAlign: "center",fontSize: "15px" }
        },
        {
            text: "有奖举报系统",
            style: { textAlign: "center",fontSize: "16px" }
        }],
	},
	canChangeProject: false,
	storage: {
		timeout: 7 * 24 * 60 * 60 * 1000
	},
	loginPageConfig: {
		bgImgUrl: ["img2.png", "img2.png"], //背景图片
		rowText: ["中交四公局", "举报平台"] //文字描述 一项即一行
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
        "openFlowHas": "openFlow?hasFlag=1", // 已办流程需要加上这个参数
		/* ----- ↓↓↓  通用接口开始 ↓↓↓ --------*/
		login: "user/login", //登录
		// login: "user/login?WEBFlag=Ab25DB", //加密登录
		editPwd: "user/updateUserPwd", //改密
		resetPwd: "user/updateUserDefaultPwd", //强制个人重置密码接口
		resetUserPwd: "user/resetUserPwd", //重置密码 
		refreshAccessToken: "user/refreshAccessToken", //刷新token 
        upload: "upload", //上传接口
        getCorpInfo: "getCorpInfo", //获取公司信息
        getRouteData: "getRouteData", //获取路由数据

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


		addZjServiceType: "addZjServiceType",//新增服务类型
		batchDeleteUpdateZjServiceType: "batchDeleteUpdateZjServiceType",//批量删除
		getZjServiceTypeList: "getZjServiceTypeList",//查询服务类型列表

		pcGetZjFeedbackProblemList: "pcGetZjFeedbackProblemList",//问题反馈列表查询
		addZjProblemFeedback: "addZjProblemFeedback",//新增问题反馈
		updateZjProblemFeedback: "updateZjProblemFeedback",//问题反馈修改
		getZjQuestionLevelList: "getZjQuestionLevelList",//问题反馈等级下拉
		batchDeleteUpdateZjProblemFeedback: "batchDeleteUpdateZjProblemFeedback",//批量删除问题反馈



		addZjServiceType: "addZjServiceType",//新增服务类型
		batchDeleteUpdateZjServiceType: "batchDeleteUpdateZjServiceType",//批量删除
		getZjServiceTypeList: "getZjServiceTypeList",//查询服务类型列表

		getZjFeedbackProblemList: "getZjFeedbackProblemList",//问题反馈列表查询
		addZjProblemFeedback: "addZjProblemFeedback",//新增问题反馈
		updateZjProblemFeedback: "updateZjProblemFeedback",//问题反馈修改
		getZjQuestionLevelList: "getZjQuestionLevelList",//问题反馈等级下拉
		batchDeleteUpdateZjProblemFeedback: "batchDeleteUpdateZjProblemFeedback",//批量删除问题反馈


		getTypicalExposureList: "getTypicalExposureList",//典型问题曝光列表
		getReportingList: "getReportingList",//举报获奖名单
		getContributionList: "getContributionList",//投稿获奖名单
		pcGetZjTechnicalQualityReportInfoList: "pcGetZjTechnicalQualityReportInfoList",//实名举报、匿名举报列表
		batchDeleteUpdateZjTechnicalQualityReportInfo: "batchDeleteUpdateZjTechnicalQualityReportInfo",//实名举报、匿名举报删除
		getZjProjectLevelList: "getZjProjectLevelList",//所属项目
		updateZjTechnicalQualityReportInfo: "updateZjTechnicalQualityReportInfo",//修改举报信息
		pcGetZjViolationReportInfoList: "pcGetZjViolationReportInfoList",
		updateZjViolationReportInfo: "updateZjViolationReportInfo",
		batchDeleteUpdateZjViolationReportInfo: "batchDeleteUpdateZjViolationReportInfo",
		batchExposureZjWgwj: "batchExposureZjWgwj",
		pcGetZjWgwjDetails: "pcGetZjWgwjDetails",
		batchExposureZjJszl: "batchExposureZjJszl",//批量曝光
		pcGetZjJszlDetails: "pcGetZjJszlDetails",//举报信息详情
		pcGetZjHiddenDangerReportInfoList: "pcGetZjHiddenDangerReportInfoList",
		updateZjHiddenDangerReportInfo: "updateZjHiddenDangerReportInfo",
		batchDeleteUpdateZjHiddenDangerReportInfo: "batchDeleteUpdateZjHiddenDangerReportInfo",
		batchExposureZjAqyh: "batchExposureZjAqyh",
		pcGetZjAqyhDetails: "pcGetZjAqyhDetails",
		pcGetZjUseNoticeDetails: "pcGetZjUseNoticeDetails",//获取使用须知
		addZjUseNotice: "addZjUseNotice",//使用须知新增或修改
		updateZjUseNotice:"updateZjUseNotice",
		getZjExposureVisibleList: "getZjExposureVisibleList",//曝光可见列表
		addZjExposureVisibleList: "addZjExposureVisibleList",//添加曝光可见对象
		batchDeleteUpdateZjExposureVisibleObject: "batchDeleteUpdateZjExposureVisibleObject",//批量删除曝光可见对象
		getZjOperationRecordList: "getZjOperationRecordList",//操作记录
		getZjReportBonusDetails: "getZjReportBonusDetails",//奖金详情
		addZjReportBonus: "addZjReportBonus",//申请奖金
		updateZjReportBonus: "updateZjReportBonus",//修改奖金
		pcGetZjProblemFeedbackDetails: "pcGetZjProblemFeedbackDetails",//反馈问题详情

		getZjQuestionLevelList: "getZjQuestionLevelList",//问题等级
		addZjQuestionLevel: "addZjQuestionLevel",
		updateZjQuestionLevel: "updateZjQuestionLevel",
		batchDeleteUpdateZjQuestionLevel: "batchDeleteUpdateZjQuestionLevel",

		getZjServiceLevelList: "getZjServiceLevelList",//服务等级
		addZjServiceLevel: "addZjServiceLevel",
		updateZjServiceLevel: "updateZjServiceLevel",
		batchDeleteUpdateZjServiceLevel: "batchDeleteUpdateZjServiceLevel",
		updateZjServiceType: "updateZjServiceType",//服务类型更新
		batchDeleteUpdateZjReportInfo: "batchDeleteUpdateZjReportInfo",//删除举报获奖名单
		batchDeleteUpdateZjProblemFeedback: "batchDeleteUpdateZjProblemFeedback",//删除投稿获奖名单

		getZszlWgwjAqyhNum:"getZszlWgwjAqyhNum",//数量
		getJszlReportDataStatistics:"getJszlReportDataStatistics",//技术质量图表
		getReportCategoryFrequency:"getReportCategoryFrequency",//举报频次
		getWgwjReportDataStatistics:"getWgwjReportDataStatistics",//违规违纪图表
		getAqyhReportDataStatistics:"getAqyhReportDataStatistics",//安环隐患图表

		exportZjTechnicalQualityReport:"exportZjTechnicalQualityReport",//技术质量导出
		exportZjViolationReport:"exportZjViolationReport",//违规违纪
		exportZjHiddenDangerReport:"exportZjHiddenDangerReport"//安环隐患
	}
};
