window.configs = {
    dev: false,
    debugConsole: false,
    // domain: "http://192.168.1.118:8080/web/",
    domain: "http://test.apih5.com:9091/web/",
    // domain: "http://192.168.1.115:8080/web/",
    // domain: "https://sjz.apih5.com/apisjzoa/",
    // domain: "http://wyongan.xicp.net/", 
    //问题项目地址列表需要跳转进去
    // voteUrl: 'http://wechat.zjyjhw.com/vote/mobile/',
    voteUrl: 'http://127.0.0.1:8000/',
    appInfo: {
        // id: "sjz_qyh_woa_id",//zj_fwh_woa_zl_id zj_qyh_woa_id
        id: "zj_qyh_woa_id",//zj_fwh_woa_zl_id zj_qyh_woa_id
        title: "投票",
        copyright: "@xxx公司",
        name: "sjzoa",
        loginType: "1",//1:普通账号密码；2：手机验证码快捷登录；3:企业号授权；4:服务号授权；5、扫码登录
        mainModule: "/vote/",
        helpHref: "",
    },
    storage: {
        timeout: 7 * 24 * 60 * 60 * 1000
    },
    
    //是否可以切换项目 默认true
    canChangeProject: false,
    wxSdk: {
        enabled: false,
        debug: false,
        jsApiList: [
            'downloadVoice',
            'downloadImage',
            'startRecord',
            'stopRecord',
            'uploadVoice',
            'playVoice',
            'stopVoice',
            'translateVoice',
            'chooseImage',
            'uploadImage',
            'closeWindow',
            'getLocation',
            'openLocation'
        ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
    },
    apis: {
        login: "user/login",//登录
        getSysUserListByBg: "user/getSysUserListByBg",
        addSysUserInfoByBg: "user/addSysUserInfoByBg",
        updateSysUserInfoByBg: "user/updateSysUserInfoByBg",
        deleteSysUserInfoByBg: "user/deleteSysUserInfoByBg",
        upload: "upload",//上传接口
        getCorpInfo: "getCorpInfo",//获取公司信息
        getRouteData: "getRouteData",//获取路由数据
        getSysDepartmentUserAllTree: "getSysDepartmentUserAllTree",//获取通讯录树结构
        addSysDepartment: "addSysDepartment",//新增部门
        updateSysDepartment: "updateSysDepartment",//修改部门信息
        batchDeleteUpdateSysDepartment: "batchDeleteUpdateSysDepartment",//批量删除部门
        moveUpdateSysDepartment: "moveUpdateSysDepartment",//移动部门顺序
        syncWeChatToSysInfo: "syncWeChatToSysInfo",//同步微信通讯录数据
        getSysMenuAllTree: "getSysMenuAllTree",//获取菜单树结构
        addSysMenu: "addSysMenu",//新增菜单
        updateSysMenu: "updateSysMenu",//修改菜单信息
        updateSysMenuDetails: "updateSysMenuDetails",
        batchDeleteUpdateSysMenu: "batchDeleteUpdateSysMenu",//批量删除菜单
        moveUpdateSysMenu: "moveUpdateSysMenu",//移动菜单顺序
        getSysMenu: "getSysMenu",//获取菜单详情
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
        getZxQrcodeQrcodeList: 'getZxQrcodeQrcodeList',
        getZxHwHomeMobilIndex: "getZxHwHomeMobilIndex",
        getIndexviewDetails: "getIndexviewDetails",
        getVoteListByVoterMobile: "getVoteListByVoterMobile",//获取列表


    },

}
