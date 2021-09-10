var apiNames = {
    getDepartment: "getDepartment", // oa部门获取
    getMember: "getMember", // oa部门人员获取
    updateFiles: "updateFiles", // 文件上传
    getSpecBgList: "getSpecBgList", // 规范列表后台查询
    addSpec: "addSpec", // 规范列表后台新增
    updateSpec: "updateSpec", // 规范列表后台修改
    deleteSpec: "deleteSpec", // 规范列表后台删除
    getSpecLevelBgList: "getSpecLevelBgList", // 规范分类列表后台查询
    addSpecLevel: "addSpecLevel", // 规范分类列表后台新增
    updateSpecLevel: "updateSpecLevel", // 规范分类列表后台修改
    deleteSpecLevel: "deleteSpecLevel", // 规范分类列表后台删除
    getInformationList: "getInformationList", // 指导资料列表后台查询
    addInformation: "addInformation", // 指导资料列表后台新增
    updateInformation: "updateInformation", // 指导资料列表后台修改
    deleteInformation: "deleteInformation", // 指导资料列表后台删除
    getFirstLevelBgList: "getFirstLevelBgList", // 指导资料一级分类列表后台查询
    addFirstLevel: "addFirstLevel", // 指导资料一级分类新增
    updateFirstLevel: "updateFirstLevel", // 指导资料一级分类修改
    cascadeDeleteFirstLevel: "cascadeDeleteFirstLevel", // 指导资料一级分类单个级联删除
    batchCascadeDelFirSec: "batchCascadeDelFirSec", // 指导资料一级分类批量级联删除
    getFirstLevelDetails: "getFirstLevelDetails", // 指导资料一级分类获取详情
    getFirDetailAndSecList: "getFirDetailAndSecList", // 指导资料一级分类获取详情及二级分类集合
    getFirAndSecList: "getFirAndSecList", // 指导资料一级分类集合及二级分类集合下拉
    getSecondLevelList: "getSecondLevelList", // 指导资料二级分类列表后台查询
    addSecondLevel: "addSecondLevel", // 指导资料二级分类新增
    updateSecondLevel: "updateSecondLevel", // 指导资料二级分类修改
    deleteSecondLevel: "deleteSecondLevel", // 指导资料二级分类单个删除
    batchDeleteSecondLevel: "batchDeleteSecondLevel", // 指导资料二级分类批量删除
    getSecondLevelDetails: "getSecondLevelDetails", // 指导资料二级分类详情
    getInformationDetail:"getInformationDetail", //附件
    addAccessory:"addAccessory" ,  //新增附件
    deleteAccessory:"deleteAccessory",  //附件删除
    updateAccessory:"updateAccessory",  //附件保存
    importCandidateList:"importCandidateList",//Excel导入抽奖候选人
    exportWinnerList:"exportWinnerList",//中奖人员导出Excel
    getKscjCandidateList:"getKscjCandidateList",//抽奖候选人列表查询
    getCandidateList:"getCandidateList",//获取抽奖候选人集合
    getCandidateDetail:"getCandidateDetail",//获取抽奖候选人详情
    updateCandidate:"updateCandidate",//修改抽奖候选人
    deleteCandidate:"deleteCandidate",//删除抽奖候选人
    getKscjWinnerList:"getKscjWinnerList",//中奖人员列表查询
    getWinnerList:"getWinnerList",//获取中奖人员集合
    getWinnerDetail:"getWinnerDetail",//获取中奖人员详情
    updateWinner:"updateWinner",//修改中奖人员
    deleteWinner:"deleteWinner",//删除中奖人员
    batchDelWinner:"batchDelWinner",//批量删除中奖人员
    batchAddWinner:"batchAddWinner",//提交中奖人员
    batchDelCandidate:"batchDelCandidate",//批量删除抽奖候选人
	getLotteryList:"getLotteryList", // 抽奖活动创建列表
    addLottery:"addLottery", // 新增抽奖活动创建列表
    updateLottery:"updateLottery", // 修改抽奖活动创建列表
    batchDeleteLottery:"batchDeleteLottery", // 删除活动创建列表
    getLotteryDetail:"getLotteryDetail", // 详情抽奖活动创建列表
    getLotteryList:"getLotteryList",//考试活动名称
    checkLottery:"checkLottery",//是否能抽奖
    getMemberList:"getMemberList",//获取人员列表
    getMemberDetail:'getMemberDetail',//获取人员详情
    updateMember:'updateMember',//修改人员
    batchDeleteMember:'batchDeleteMember',//批量删除
    addMember:'addMember',//人员增加
    getCompanyList:'getCompanyList',//获取公司列表
    addCompany:'addCompany',//公司新增
    getCompanyDetail:'getCompanyDetail',//公司详情
    updateCompany:'updateCompany',//公司修改
    batchDeleteCompany:'batchDeleteCompany',//公司批量添加
    getBgEnquiryList:'getBgEnquiryList',//后台获取问询信息集合
    getEnquiryDetailByPc:'getEnquiryDetailByPc',//后台获取问询详情
    batchAddMember:'batchAddMember',//从oa系统拉取 批量新增用户
    toDrawAndReturnResult:'toDrawAndReturnResult',//抽奖接口
    getZjBrowseHistoryList:'getZjBrowseHistoryList',//获取文档资料浏览记录列表
    addZjBrowseHistory:'addZjBrowseHistory',//文档资料浏览记录新增
    updateZjBrowseHistory:'updateZjBrowseHistory',//文档资料浏览记录修改
    batchDeleteZjBrowseHistory:'batchDeleteZjBrowseHistory',//文档资料浏览记录批量删除
    getSpecLevelSelect:'getSpecLevelSelect', //pc规范分类下拉列表
    getSysCompanySelect: 'getSysCompanySelect',
}
 window.lny = window.l = new Lny(apiNames,'http://192.168.1.155:8080/')
// window.lny = window.l = new Lny(apiNames,'http://weixin.fheb.cn:89/zjtz/')
// window.lny = window.l = new Lny(apiNames,"http://localhost:8080/wdplus-web/")

if ($.fn.page) {
    $.fn.page.defaults = {
        pageSize: 10,
        pageBtnCount: 9,
        firstBtnText: '首页',
        lastBtnText: '尾页',
        prevBtnText: '上一页',
        nextBtnText: '下一页',
        showJump: true,
        jumpBtnText: 'GO',
        showPageSizes: true,
        pageSizeItems: [10, 20, 40],
        remote: {
            pageIndexName: 'page',     //请求参数，当前页数，索引从1开始
            pageSizeName: 'limit',       //请求参数，每页数量
            totalName: 'totalNumber'              //指定返回数据的总数据量的字段名
        }
    };
}