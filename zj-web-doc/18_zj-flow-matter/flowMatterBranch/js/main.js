var apiNames = {
    getDepartment: "apiPostSysDepartmentList/1", // oa部门获取
    getMember: "apiPostMember", // oa部门人员获取
    updateFiles: "updateFiles", // 文件上传
	getLoginUserDetail:"getLoginUserDetail",

    getZjYySealApplyList: "getZjYySealApplyList", // 查询用印申请列表
    addZjYySealApply: "addZjYySealApply", // 新增用印申请
    batchDeleteUpdateZjYySealApply: "batchDeleteUpdateZjYySealApply",//批量删除申请
    getSealApplyDetail: "getSealApplyDetail",//用印申请详情

    secretarySendSealApply: "secretarySendSealApply",//部门文秘 给 申请人    、部门负责人  、分管公司领导  发送消息
    applySendSealApplyToOffice: "applySendSealApplyToOffice",//经办人  发给 办公室归档人员

    addZjYySealApproval: "addZjYySealApproval",//新增审批用印

    getZjYySealArchiveList: "getZjYySealArchiveList",//查询归档列表
    addZjYySealArchive: "addZjYySealArchive",//确认归档
	
	
	//******************0720新流程变动的接口**************
	
	
	addFlowSealInLaunch:"addFlowSealInLaunch",//购置申请  --发起-- 时调用该接口===走自己的inset不要流程的insert===0718
	updateFlowSealAfterSubmit:"updateFlowSealAfterSubmit",//购置申请--发起-提交-- 时调用该接口===传workID或sszcid 都可以== 更新资产管理表的flow_status 由0待提交变成1审核中==0718
	getFlowSealDetailByWorkId:"getFlowSealDetailByWorkId",//购置申请---查看待办、已办的详情===传workID=======根据workId查询资产详情==0718	
	
	addFlowOutSealInLaunch:"addFlowOutSealInLaunch",
	updateFlowOutSealAfterSubmit:"updateFlowOutSealAfterSubmit",
	getFlowOutSealDetailByWorkId:"getFlowOutSealDetailByWorkId",
	
	addFlowProjectInLaunch:"addFlowProjectInLaunch",
	updateFlowProjectAfterSubmit:"updateFlowProjectAfterSubmit",
	getFlowProjectDetailByWorkId:"getFlowProjectDetailByWorkId",
	
	addFlowNeedsConfirmInLaunch:"addFlowNeedsConfirmInLaunch",
	updateFlowNeedsConfirmAfterSubmit:"updateFlowNeedsConfirmAfterSubmit",
	getFlowNeedsConfirmDetailByWorkId:"getFlowNeedsConfirmDetailByWorkId",
	
	
	
	
	
	//党费使用申请接口
	getZjFlowPartyFeeUseList:"getZjFlowPartyFeeUseList",//
	addZjFlowPartyFeeUse:"addZjFlowPartyFeeUse",//
	updateZjFlowPartyFeeUse:"updateZjFlowPartyFeeUse",//
	getZjFlowPartyFeeUseDetail:"getZjFlowPartyFeeUseDetail",//
	getZjFlowPartyFeeUnitList:"getZjFlowPartyFeeUnitList",//
	addZjFlowPartyFeeUnit:"addZjFlowPartyFeeUnit",//
	updateZjFlowPartyFeeUnit:"updateZjFlowPartyFeeUnit",//
	batchDeleteUpdateZjFlowPartyFeeUnit:"batchDeleteUpdateZjFlowPartyFeeUnit",//
	getZjFlowPartyFeeDetailList:"getZjFlowPartyFeeDetailList",//
	addZjFlowPartyFeeDetail:"addZjFlowPartyFeeDetail",//
	updateZjFlowPartyFeeDetail:"updateZjFlowPartyFeeDetail",//
	batchDeleteUpdateZjFlowPartyFeeDetail:"batchDeleteUpdateZjFlowPartyFeeDetail",//
	addZjFlowPartyFeeDetailTwo:"addZjFlowPartyFeeDetailTwo",//
	getZjFlowPartyFeeDetailAllList:"getZjFlowPartyFeeDetailAllList",//
	exportExcelZjFlowPartyFeeUse:"exportExcelZjFlowPartyFeeUse",//
	getZjFlowPartyFeeUnitDetail:"getZjFlowPartyFeeUnitDetail",//
	

	
	
	//请假申请
	addZjFlowLeaveApply:"addZjFlowLeaveApply",
	updateZjFlowLeaveApply:"updateZjFlowLeaveApply",
	getZjFlowLeaveApplyDetail:"getZjFlowLeaveApplyDetail",
	exportZjFlowLeaveApplyToWord:"exportZjFlowLeaveApplyToWord",//
	//加班申请
	addZjFlowOvertimeApply:"addZjFlowOvertimeApply",
	updateZjFlowOvertimeApply:"updateZjFlowOvertimeApply",
	getZjFlowOvertimeApplyDetail:"getZjFlowOvertimeApplyDetail",
	exportZjFlowOvertimeApplyToWord:"exportZjFlowOvertimeApplyToWord",//
	//出差申请
	addZjFlowTripApply:"addZjFlowTripApply",//
	updateZjFlowTripApply:"updateZjFlowTripApply",
	getZjFlowTripApplyDetail:"getZjFlowTripApplyDetail",
	getZjFlowTripApplyDetailList:"getZjFlowTripApplyDetailList",
	addZjFlowTripApplyDetail:"addZjFlowTripApplyDetail",
	batchDeleteUpdateZjFlowTripApplyDetail:"batchDeleteUpdateZjFlowTripApplyDetail",
	exportZjFlowTripApplyToWord:"exportZjFlowTripApplyToWord",//
	//纪委用印申请
	addZjFlowJwSealApply:"addZjFlowJwSealApply",
	updateZjFlowJwSealApply:"updateZjFlowJwSealApply",
	getZjFlowJwSealApplyDetail:"getZjFlowJwSealApplyDetail",
	exportZjFlowJwSealApplyToWord:"exportZjFlowJwSealApplyToWord",//
	//局机关事务申请
	addZjFlowAffairsApply:"addZjFlowAffairsApply",
	updateZjFlowAffairsApply:"updateZjFlowAffairsApply",
	getZjFlowAffairsApplyDetail:"getZjFlowAffairsApplyDetail",
	exportZjFlowAffairsApplyToWord:"exportZjFlowAffairsApplyToWord",//
	//因私出国申请
	addZjFlowGoAbroadApply:"addZjFlowGoAbroadApply",
	updateZjFlowGoAbroadApply:"updateZjFlowGoAbroadApply",
	getZjFlowGoAbroadApplyDetail:"getZjFlowGoAbroadApplyDetail",
	exportZjFlowGoAbroadApplyToWord:"exportZjFlowGoAbroadApplyToWord",//
	getZjFlowGoAbroadInfoList:"getZjFlowGoAbroadInfoList",
	addZjFlowGoAbroadInfo:"addZjFlowGoAbroadInfo",
	batchDeleteUpdateZjFlowGoAbroadInfo:"batchDeleteUpdateZjFlowGoAbroadInfo",//
	//车辆维修保养申请
	addZjFlowCarMaintenanceApply:"addZjFlowCarMaintenanceApply",
	updateZjFlowCarMaintenanceApply:"updateZjFlowCarMaintenanceApply",
	getZjFlowCarMaintenanceApplyDetail:"getZjFlowCarMaintenanceApplyDetail",
	exportZjFlowCarMaintenanceApplyToWord:"exportZjFlowCarMaintenanceApplyToWord",//
	
	batchDeleteUpdateZjYySealApply:"batchDeleteUpdateZjYySealApply",
	//5公司用印接口
	getZjFlowYyOutSealFiveList:"getZjFlowYyOutSealFiveList",
	addZjFlowYyOutSealFive:"addZjFlowYyOutSealFive",
	updateZjFlowYyOutSealFive:"updateZjFlowYyOutSealFive",
	batchDeleteUpdateZjFlowYyOutSealFive:"batchDeleteUpdateZjFlowYyOutSealFive",
	getZjFlowYyOutSealFiveDetail:"getZjFlowYyOutSealFiveDetail",
	//
	getZjFlowYySealFiveList:"getZjFlowYySealFiveList",
	addZjFlowYySealFive:"addZjFlowYySealFive",
	updateZjFlowYySealFive:"updateZjFlowYySealFive",
	batchDeleteUpdateZjFlowYySealFive:"batchDeleteUpdateZjFlowYySealFive",
	getZjFlowYySealFiveDetail:"getZjFlowYySealFiveDetail",
	
	
	
	
	
	
	getLoginUserDetail:"getLoginUserDetail",
	exportInfoNeedsConfirmToWord:"exportInfoNeedsConfirmToWord",
    exportSealToWord: "exportSealToWord",//导出word
    exportSealToExcel: "exportSealToExcel",//导出excel
    startFlow: "startFlow",
    submitFlow: "submitFlow",
    getSubmitFlow: "getSubmitFlow",	
    getTodoList: "getTodoList",
    getHasTodoList: "getHasTodoList",
    createOpenFlow:"createOpenFlow",
    openFlow: "openFlow",
    actionFlow: "actionFlow"
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

//window.lny = window.l = new Lny(apiNames,'http://weixin.fheb.cn:99/apiwoa/')
//  window.lny = window.l = new Lny(apiNames,'http://localhost:8080/web/')

 //window.lny = window.l = new Lny(apiNames,'http://192.168.1.119/apiwoa/')
 window.lny = window.l = new Lny(apiNames,'http://192.168.1.223:8080/web/')
 //window.lny = window.l = new Lny(apiNames,'http://192.168.1.118:8080/web/')
