//qnn-table全局配置
window.qnnTableGlobalConfig = {

};

window.configs = {
    dev: false,
    debugConsole: false,
    //主题 basic(默认黑色主题菜单) dev(蓝色主题菜单)
    // theme: "dev",
    domain: "http://weixin.fheb.cn:99/apiwoa/",
    // domain: "http://test.apih5.com:9090/web/",
    // domain: "http://192.168.1.119:8080/web/",
    appInfo: {
        ureport: 'http://weixin.fheb.cn:91/ureport/',
        logo: "http://weixin.fheb.cn:99/icon/logo.png",
        id: "zj_qyh_woa_id",
        // id: "zt12_qyh_suopei",
        //正常网站标题和网页中左上角标题(leftTopTitle不存在时)
        title: "中交一公局集团",
        copyright: "@中交",
        //code登录方式 给后台的 loginType token错误或者网址为携带code将跳转到提示页面
        // webCodeLoginType:"6",
        loginType: "1", // 1:普通账号密码；2：手机验证码快捷登录；3:企业号授权（也可以在app登录）；4:服务号授权；5、扫码登录  6、app登录  31、会弹出授权窗口的授权
        mainModule: "/zjFlowDepositPaymentPc/",
        helpHref: ""
    },
    //百度编辑器cdn地址 默认就是 下面 地址
    // ueCdn: "http://cdn.apih5.com/lib/react-ueditor/vendor/ueditor/",
    // ueCdn:"http://localhost:8000/",

    //登录页面轮播背景配置 配置后注意文件夹/src/view/login/img下是否存在对应的图片
    //地址也可以直接引用http://xxx || https://xxx
    loginPageConfig: {},
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
        "unLock": "user/unLock", // 解锁用户
        "openFlowHas": "openFlow?hasFlag=1", // 已办流程需要加上这个参数
                 /* ----- ↓↓↓  通用接口开始 ↓↓↓ --------*/
        login: "user/login", //登录
        refreshAccessToken: "user/refreshAccessToken", //刷新token 
        editPwd: "user/updateUserPwd", //改密
		resetUserPwd: "user/resetUserPwd", //重置密码 
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
        getSysDepartmentUserAllTree: "getSysUserCurrentTreeByZj",         // 组织机构-部门+人员-获取
		 getSysUserCurrentTreeByZj: "getSysUserCurrentTreeByZj",         // 组织机构-部门+人员-获取
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
        addBaseFlowStartSettingByFlow:"addBaseFlowStartSettingByFlow", //提交流程追加的节点
		
		//后台-流程相关
        createOpenFlow: "createOpenFlow",
        openFlow: "openFlow",
        actionFlow: "actionFlow",
        getTodoList: "getTodoList",
        exportZxHwFlowToWord: "exportZxHwFlowToWord", //获取流程预览网址
        getHasTodoList: "getHasTodoList",
        getZxQrcodeQrcodeList: "getZxQrcodeQrcodeList",

        //字典组织层级[通用]
        addBaseCode: "addBaseCode",
        updateBaseCode: "updateBaseCode",
        getBaseCodeList: "getBaseCodeList",
        batchDeleteUpdateBaseCode: "batchDeleteUpdateBaseCode",
        pcUpdateBaseCodeOnTree: "pcUpdateBaseCodeOnTree", //更新数据字典[在树结构上编辑]   
        pcExchangeBaseCode: "pcExchangeBaseCode",
        moveUpdateBaseCode:"moveUpdateBaseCode",
		
		//获取app首页
        getSysMobilIndex: "getSysMobilIndex",
        getSysMobilIndexByData: "getSysMobilIndexByData",
		
		//项目获取切换等接口
        getZxQrcodePermissionObjectListByProject: "getZxQrcodePermissionObjectListByProject",
        changeZxQrcodePermissionProject: "changeZxQrcodePermissionProject",
        getZtEndPermissionListByProject  : "getZtEndPermissionListByProject  ",
        changeZtEndProjectManagement:"changeZtEndProjectManagement",
		
		// 其它
		syncWeChatToSysInfo: "syncWeChatToSysInfo", //同步微信通讯录数据
		getBaseCodeUIConfig: 'getBaseCodeUIConfig',//查询组织层级前台页面配置
		
		//项目获取切换等接口
        changeCompany: "user/changeCompany", //切换公司
        /* ----- ↑↑↑  通用接口结束 ↑↑↑ --------*/


		//保证金支付接口
		addZjFlowDepositPayment:"addZjFlowDepositPayment",
		updateZjFlowDepositPayment:"updateZjFlowDepositPayment",
		getZjFlowDepositPaymentDetail:"getZjFlowDepositPaymentDetail",
		
        getSysUserTreeByZjAddOther:"getSysUserTreeByZjAddOther",
        
        //保证金台账
        getZjFlowDepositStandingBookList:"getZjFlowDepositStandingBookList",
        exportZjFlowDepositStandingBookList:"exportZjFlowDepositStandingBookList",
        
        //战略合作协议
        getZjFlowStrategicCooperationList:"getZjFlowStrategicCooperationList",
        addZjFlowStrategicCooperation:"addZjFlowStrategicCooperation",
        updateZjFlowStrategicCooperation:"updateZjFlowStrategicCooperation",
        batchDeleteUpdateZjFlowStrategicCooperation:"batchDeleteUpdateZjFlowStrategicCooperation",
        getZjFlowStrategicCooperationDetail:"getZjFlowStrategicCooperationDetail"
        
    }
};
