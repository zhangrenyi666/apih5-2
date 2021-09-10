window.configs = {
    dev: false,
    debugConsole: false, 
    // domain: "http://150.109.37.212/apiwoa/",
    // domain: "http://oa.h-zczx.com/apiwoa/",
    // domain: "http://weixin.fheb.cn:99/apiwoa/",
    domain: "http://wechat.zjyjhw.com/apihaiwei/", 
    // domain: "http://test.apih5.com/",
    // domain: "http://cx.apih5.com/web/",
    // domain: "http://47.96.150.231/apitongren/",
   // domain:"http://xa8bt70bri.51http.tech/apitongren/",
    // domain:"http://test.apih5.com/apitongren/",
    // domain: "http://dltest.apih5.com/web/",
    // domain: " http://sx.apih5.com:8082/sxoa/",
    //  domain: "http://192.168.1.119:8080/web/",
    // domain: "https://sjz.apih5.com/apisjzoa/",
    // domain: "http://114.116.12.219/apisxdeh/",
    // domain: "http://bur07iflue.51http.tech/web/",
	//domain: "http://192.168.1.118:8080/web/",
    appInfo: {
        logo: "http://47.96.150.231/logo/logo.png",
        //id: "tongren_qyh_app_id",
         id: "zj_qyh_woa_id",
        // id: "zx_qyh_woa_01",
        // id: "sxdeh_qyh_app_id",
		// id: "lanzhou_qyh_app_id",
        // id: "zj_qyh",
        title: "xxx公司",
        copyright: "@xxx公司",
        name: "sjzoa",
        loginType: "1", //1:普通账号密码；2：手机验证码快捷登录；3:企业号授权（也可以在app登录）；4:服务号授权；5、扫码登录  6、app登录  31、会弹出授权窗口的授权
        mainModule: "/lanzhou/",
        helpHref: ""
    },
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
        login: "user/login", //登录
        getSysUserListByBg: "user/getSysUserListByBg",
        addSysUserInfoByBg: "user/addSysUserInfoByBg",
        updateSysUserInfoByBg: "user/updateSysUserInfoByBg",
        deleteSysUserInfoByBg: "user/deleteSysUserInfoByBg",
        upload: "upload", //上传接口
        getSysDepartmentAllTree: "getSysDepartmentUserAllTree?noPersonnel=1", //通讯录-部门获取
        getSysDepartmentUserAllTree: "getSysDepartmentUserAllTree", //通讯录-部门的人员获取
        //getSysDepartmentUserAllTree: "getSysDepartmentUserAllTreeByZj",//获取通讯录树结构 zj
        appGetSysFrequentContactsList:"appGetSysFrequentContactsList", //获取收藏的联系人
        appAddSysFrequentContacts:"appAddSysFrequentContacts", //添加联系人到收藏
        appRemoveSysFrequentContacts:"appRemoveSysFrequentContacts", //移除收藏联系人
        getCorpInfo: "getCorpInfo", //获取公司信息
        getRouteData: "getRouteData", //获取路由数据
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
        getBaseFlowCodeList:"getBaseFlowCodeList",
        baseFlowCodeImport:"baseFlowCodeImport",
        batchDeleteUpdateBaseFlowCode:"batchDeleteUpdateBaseFlowCode",
        getBaseFlowSponsorChooserList:"getBaseFlowSponsorChooserList",
        getFlowNameSelectList:"getFlowNameSelectList",
        addBaseFlowSponsorChooserByList:"addBaseFlowSponsorChooserByList",
        batchDeleteUpdateBaseFlowSponsorChooser:"batchDeleteUpdateBaseFlowSponsorChooser",
        updateBaseFlowSponsorChooserByList:"updateBaseFlowSponsorChooserByList", 
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
        exportZxHwFlowToWord:"exportZxHwFlowToWord",//获取流程预览网址
        getHasTodoList: "getHasTodoList",
        getZxQrcodeQrcodeList: "getZxQrcodeQrcodeList",
        getZxHwHomeMobilIndex: "getZxHwHomeMobilIndex",
        getIndexviewDetails: "getIndexviewDetails",

        getZjHwZyResourceSupplierList:"getZjHwZyResourceSupplierList",//供应商列表
        getZjHwZyResourceSupplierDetail:"getZjHwZyResourceSupplierDetail",//供应商详情
        getZjHwZyResourceDepSelectAllList:"getZjHwZyResourceDepSelectAllList",//供应商管理部门

        getZjHwZyResourceSchemeList:"getZjHwZyResourceSchemeList",//技术质量成果库
        getZjHwZyResourceSchemeDetail:"getZjHwZyResourceSchemeDetail",//详情

        getZjHwZyResourceOrganizationList:"getZjHwZyResourceOrganizationList",//机构管理
        getZjHwZyResourceOrganizationDetail:"getZjHwZyResourceOrganizationDetail",//详情

        getZjHwZyResourceInventoryList:"getZjHwZyResourceInventoryList",//清单列表
        getZjHwZyResourceInventoryDetail:"getZjHwZyResourceInventoryDetail",//详情

        getZjHwZyResourcePersonnelList:"getZjHwZyResourcePersonnelList",//人员库列表
        getZjHwZyResourcePersonnelDetail:"getZjHwZyResourcePersonnelDetail",//详情

        getZjHwZyResourceCoursewareList:"getZjHwZyResourceCoursewareList",//课件管理
        getZjHwZyResourceCoursewareDetail:"getZjHwZyResourceCoursewareDetail",//详情

        getZjHwZyResourceMixProportionList:"getZjHwZyResourceMixProportionList",//配合比列表
        getZjHwZyResourceMixProportionDetail:"getZjHwZyResourceMixProportionDetail",//详情

        getZjHwZyResourceMaterialQuotaList:"getZjHwZyResourceMaterialQuotaList",//材料消耗定额列表
        getZjHwZyResourceMaterialQuotaDetail:"getZjHwZyResourceMaterialQuotaDetail",//详情

        getZjHwZyResourcePriceMaterialList:"getZjHwZyResourcePriceMaterialList",//物资限价
        getZjHwZyResourcePriceMaterialDetail:"getZjHwZyResourcePriceMaterialDetail",//详情

        getZjHwZyResourcePriceSubpackageMainList:"getZjHwZyResourcePriceSubpackageMainList",//分包限价
		getZjHwZyResourcePriceSubpackageDetailList:"getZjHwZyResourcePriceSubpackageDetailList",//
        getZjHwZyResourcePriceSubpackageDetailDetail:"getZjHwZyResourcePriceSubpackageDetailDetail",//详情

        getZjHwZyResourcePriceTempList:"getZjHwZyResourcePriceTempList",//临时限价
        getZjHwZyResourcePriceTempDetail:"getZjHwZyResourcePriceTempDetail",//详情
         
    }
};
