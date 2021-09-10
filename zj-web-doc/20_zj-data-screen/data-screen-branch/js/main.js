var apiNames = {
    getDepartment: "apiPostDepartment", // oa部门获取
    getMember: "apiPostMember", // oa部门人员获取
    updaloadFiles: "updaloadFiles", // 文件上传

    getZjSchemeConfirmationList: "getZjSchemeConfirmationList",//查询方案确认列表
    addZjSchemeConfirmation: "addZjSchemeConfirmation",//新增基本信息
    updateZjSchemeConfirmation: "updateZjSchemeConfirmation",//更新基础信息
    batchDeleteUpdateZjSchemeConfirmation: "batchDeleteUpdateZjSchemeConfirmation",//删除方案确认数据

    getZjSchemeDetailedList: "getZjSchemeDetailedList",//查询方案确认清单
    addZjSchemeDetailedList: "addZjSchemeDetailedList",//新增方案确认清单
    updateZjSchemeDetailedList: "updateZjSchemeDetailedList",//更新方案确认清单
    batchDeleteUpdateZjSchemeDetailedList: "batchDeleteUpdateZjSchemeDetailedList",//删除方案确认清单
    ZjSchemeConfirmationDetail: "ZjSchemeConfirmationDetail",//查询基础信息（详情）
    submitToExamine: "submitToExamine",//提交审核
    getZjPrProgrammeLaunchList: "getZjPrProgrammeLaunchList",//查询方案发起列表
    getzjPrProgrammeLaunchDetail:"getzjPrProgrammeLaunchDetail",//查询方案发起详情
    reviseTheScheme:"reviseTheScheme",//重新编辑修改状态
    addZjPrProgrammeLaunch: "addZjPrProgrammeLaunch",//方案发起新增
    updateZjPrProgrammeLaunch: "updateZjPrProgrammeLaunch",//更新方案发起
    batchDeleteUpdateZjPrProgrammeLaunch: "batchDeleteUpdateZjPrProgrammeLaunch",//删除方案发起
    getZjWoaProjectDataList: "getZjWoaProjectDataList",//从中交拉去项目下拉列表
    getZjSchemeConfirmationListBySelect: "getZjSchemeConfirmationListBySelect",//项目下拉列表
    getZjSchemeDetailedListSelectAllList: "getZjSchemeDetailedListSelectAllList",//项目编号下拉
    getFlowRunStart: "getFlowRunStart",//发起前获取详情
    addFlowRunStart: "addFlowRunStart",//发起方案审批
    getFlowRunReview: "getFlowRunReview",//发起后获取详情
    addFlowRunReview: "addFlowRunReview",//方案审批处理
    getZjPrWaitForHandlingProgrammeList: "getZjPrWaitForHandlingProgrammeList",//待审批方案
    getZjPrAlreadyHandledProgrammeList: "getZjPrAlreadyHandledProgrammeList",//已审批方案
	getFlowRunReviewDetail: "getFlowRunReviewDetail",//发起后获取详情
    exportWord: "exportWord",//打印
	
	
	getProgrammeApprovalStatisticsList: "getProgrammeApprovalStatisticsList",
    getSchemeReviewListList: "getSchemeReviewListList",
    getSchemeReviewerList: "getSchemeReviewerList",
    schemeReviewStatusExportExcel: "schemeReviewStatusExportExcel",
    schemeReviewListExportExcel: "schemeReviewListExportExcel",
    schemeReviewerExportExcel: "schemeReviewerExportExcel",
    getTemplateNoteAllList: "getTemplateNoteAllList",
	
	
	    getZjHaiKangOaMemberList: "getZjHaiKangOaMemberList",
    addZjHaiKangOaMember: "addZjHaiKangOaMember",
    updateZjHaiKangOaMember: "updateZjHaiKangOaMember",
    batchDeleteZjHaiKangOaMember: "batchDeleteZjHaiKangOaMember",
	
	    getZjInvestmentOaMemberList: "getZjInvestmentOaMemberList",
    addZjInvestmentOaMember: "addZjInvestmentOaMember",
    updateZjInvestmentOaMember: "updateZjInvestmentOaMember",
    batchDeleteZjInvestmentOaMember: "batchDeleteZjInvestmentOaMember",
	
		getZjSuperLinkComrankOaMemberList: "getZjSuperLinkComrankOaMemberList",
	addZjSuperLinkComrankOaMember: "addZjSuperLinkComrankOaMember",
	updateZjSuperLinkComrankOaMember: "updateZjSuperLinkComrankOaMember",
	batchDeleteUpdateZjSuperLinkComrankOaMember: "batchDeleteUpdateZjSuperLinkComrankOaMember",
	getZjSuperLinkConsettOaMemberList: "getZjSuperLinkConsettOaMemberList",
	addZjSuperLinkConsettOaMember: "addZjSuperLinkConsettOaMember",
	updateZjSuperLinkConsettOaMember: "updateZjSuperLinkConsettOaMember",
	batchDeleteUpdateZjSuperLinkConsettOaMember: "batchDeleteUpdateZjSuperLinkConsettOaMember",
	getZjSuperLinkConsignOaMemberList: "getZjSuperLinkConsignOaMemberList",
	addZjSuperLinkConsignOaMember: "addZjSuperLinkConsignOaMember",
	updateZjSuperLinkConsignOaMember: "updateZjSuperLinkConsignOaMember",
	batchDeleteUpdateZjSuperLinkConsignOaMember: "batchDeleteUpdateZjSuperLinkConsignOaMember",
	getZjSuperLinkEngtypeOaMemberList: "getZjSuperLinkEngtypeOaMemberList",
	addZjSuperLinkEngtypeOaMember: "addZjSuperLinkEngtypeOaMember",
	updateZjSuperLinkEngtypeOaMember: "updateZjSuperLinkEngtypeOaMember",
	batchDeleteUpdateZjSuperLinkEngtypeOaMember: "batchDeleteUpdateZjSuperLinkEngtypeOaMember",
	getZjSuperLinkEquipmentOaMemberList: "getZjSuperLinkEquipmentOaMemberList",
	addZjSuperLinkEquipmentOaMember: "addZjSuperLinkEquipmentOaMember",
	updateZjSuperLinkEquipmentOaMember: "updateZjSuperLinkEquipmentOaMember",
	batchDeleteUpdateZjSuperLinkEquipmentOaMember: "batchDeleteUpdateZjSuperLinkEquipmentOaMember",
	getZjSuperLinkMatestoOaMemberList: "getZjSuperLinkMatestoOaMemberList",
	addZjSuperLinkMatestoOaMember: "addZjSuperLinkMatestoOaMember",
	updateZjSuperLinkMatestoOaMember: "updateZjSuperLinkMatestoOaMember",
	batchDeleteUpdateZjSuperLinkMatestoOaMember: "batchDeleteUpdateZjSuperLinkMatestoOaMember",
	getZjSuperLinkResocatOaMemberList: "getZjSuperLinkResocatOaMemberList",
	addZjSuperLinkResocatOaMember: "addZjSuperLinkResocatOaMember",
	updateZjSuperLinkResocatOaMember: "updateZjSuperLinkResocatOaMember",
	batchDeleteUpdateZjSuperLinkResocatOaMember: "batchDeleteUpdateZjSuperLinkResocatOaMember",
}

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
        pageSizeItems: [10, 50, 100, 500, 1000],
        remote: {
            pageIndexName: 'page',     //请求参数，当前页数，索引从1开始
            pageSizeName: 'limit',       //请求参数，每页数量
            totalName: 'totalNumber'              //指定返回数据的总数据量的字段名
        }
    };
}
//window.lny = window.l = new Lny(apiNames, 'http://localhost:8080/')
window.lny = window.l = new Lny(apiNames, 'http://wechat.zjyjhw.com/apihaiwei/')
