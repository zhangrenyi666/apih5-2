var apiNames = {
    getDepartment: "apiPostSysDepartmentList/1", // oa部门获取
    //getDepartment: "apiPostSysDepartmentList/2", // oa部门获取	 
    getMember: "apiPostMember", // oa部门人员获取
    updateFiles: "updateFiles", // 文件上传
	
    getZjMeetingRoomTypeList: "getZjMeetingRoomTypeList", // 查询会议室类型列表
    addZjMeetingRoomType: "addZjMeetingRoomType", // 新增会议室类型
    updateZjMeetingRoomType: "updateZjMeetingRoomType", // 更新会议室类型
    batchDeleteUpdateZjMeetingRoomType: "batchDeleteUpdateZjMeetingRoomType", // 删除会议室类型
	getZjMeetingRoomTypeSelectAllList:"getZjMeetingRoomTypeSelectAllList", //获取会议室类型下拉菜单
	getZjMeetingRoomList:"getZjMeetingRoomList",//查询会议室列表
	addZjMeetingRoom:"addZjMeetingRoom",//新增会议室
	updateZjMeetingRoom:"updateZjMeetingRoom",//更新会议室
	batchDeleteUpdateZjMeetingRoom:"batchDeleteUpdateZjMeetingRoom",//删除会议室
	getMeetingRoomAllList:"getMeetingRoomAllList",//会议室管理下拉
	getMeetingRoomAllListForFlow:"getMeetingRoomAllListForFlow",//会议室流程下拉
	getZjMeetingRoomSituationList:"getZjMeetingRoomSituationList",
	
	getZjMeetingReservationsList:"getZjMeetingReservationsList",
	addZjMeetingReservations:"addZjMeetingReservations",
	updateZjMeetingReservations:"updateZjMeetingReservations",
	batchDeleteUpdateZjMeetingReservations:"batchDeleteUpdateZjMeetingReservations",
	meetingRoomSubmission:"meetingRoomSubmission",
	getZjMeetingRoomApprovalList:"getZjMeetingRoomApprovalList",
	addZjMeetingRoomApproval:"addZjMeetingRoomApproval",
	updateZjMeetingRoomApproval:"updateZjMeetingRoomApproval",
	batchDeleteUpdateZjMeetingRoomApproval:"batchDeleteUpdateZjMeetingRoomApproval",
	addLargeZjMeetingReservations:"addLargeZjMeetingReservations",
	getLargeZjMeetingReservationsList:"getLargeZjMeetingReservationsList",
	getLeaderZjMeetingReservationsList:"getLeaderZjMeetingReservationsList",
	getZjMeetingAttendPersonnelList:"getZjMeetingAttendPersonnelList",
	getZjMeetingNotSignInPersonnelList:"getZjMeetingNotSignInPersonnelList",
	getZjMeetingHaveSignedInPersonnelList:"getZjMeetingHaveSignedInPersonnelList",
	getZjMeetingReceiptListByRecordid:"getZjMeetingReceiptListByRecordid",
	zjMeetingReceiptExportExcel:"zjMeetingReceiptExportExcel",
	zjMeetingSingInExportExcel:"zjMeetingSingInExportExcel",
	
	getZjMeetingReservationsFlowList:"getZjMeetingReservationsFlowList",
	addZjMeetingReservationsFlow:"addZjMeetingReservationsFlow",
	updateZjMeetingReservationsFlow:"updateZjMeetingReservationsFlow",
	getZjMeetingApplyFlowDetailByWorkId:"getZjMeetingApplyFlowDetailByWorkId",
	batchDeleteUpdateZjMeetingReservationsFlow:"batchDeleteUpdateZjMeetingReservationsFlow",
    getZjMeetingRoomSituationList:"getZjMeetingRoomSituationList",//会议室使用情况
    getZjMeetingSysDepartmentListAllList:"getZjMeetingSysDepartmentListAllList",//会议室使用情况
    zjMeetingReservationFlowExportExcel:"zjMeetingReservationFlowExportExcel",//会议室使用情况
    zjMeetingSingUpExportExcel:"zjMeetingSingUpExportExcel",//会议室使用情况
    getZjMeetingOutsideMeetingList:"getZjMeetingOutsideMeetingList",//会议室使用情况
    addZjMeetingOutsideMeeting:"addZjMeetingOutsideMeeting",//会议室使用情况
    updateZjMeetingOutsideMeeting:"updateZjMeetingOutsideMeeting",//会议室使用情况
    batchDeleteUpdateZjMeetingOutsideMeeting:"batchDeleteUpdateZjMeetingOutsideMeeting",//会议室使用情况
    getZjMeetingEditPersonnelList:"getZjMeetingEditPersonnelList",//会议室使用情况
    addZjMeetingEditPersonnel:"addZjMeetingEditPersonnel",//会议室使用情况
    updateZjMeetingEditPersonnel:"updateZjMeetingEditPersonnel",//会议室使用情况
    batchDeleteUpdateZjMeetingEditPersonnel:"batchDeleteUpdateZjMeetingEditPersonnel",//会议室使用情况
    getZjMeetingRoomEditJurisdiction:"getZjMeetingRoomEditJurisdiction",//会议室使用情况
    addZjMeetingOutsideMeetingSituation:"addZjMeetingOutsideMeetingSituation",//编辑修改
    getZjMeetingInsideMeetingList:"getZjMeetingInsideMeetingList",//查询内部会议	
    addZjMeetingInsideMeeting:"addZjMeetingInsideMeeting",//新增内部会议	
    updateMeetingInsideMeeting:"updateMeetingInsideMeeting",//更新内部会议	
    batchDeleteUpdateZjMeetingInsideMeeting:"batchDeleteUpdateZjMeetingInsideMeeting",//删除内部会议	
    getZjMeetingApplyFlowConflictByReservationsTime:"getZjMeetingApplyFlowConflictByReservationsTime",//查询会议室预定时间是否冲突	
	zjMeetingInsideMeetingSingInExportExcel:"zjMeetingInsideMeetingSingInExportExcel",//内部会议签到导出
	zjMeetingRoomChangeToPdf:"zjMeetingRoomChangeToPdf",//报表转PDF
	
	getZjMeetingReserveTimingRemindList:"getZjMeetingReserveTimingRemindList",//查询会议提醒
	addZjMeetingReserveTimingRemind:"addZjMeetingReserveTimingRemind",//新增会议并增加提醒
	addZjFlowMeetingReserveTimingRemind:"addZjFlowMeetingReserveTimingRemind",//新增流程会议提醒
	addZjInsideMeetingReserveTimingRemind:"addZjInsideMeetingReserveTimingRemind",//新增内部会议提醒
    batchDeleteZjMeetingTimingRemindAndPerson:"batchDeleteZjMeetingTimingRemindAndPerson",//删除会议提醒
    updateZjMeetingReserveTimingRemind:"updateZjMeetingReserveTimingRemind",//修改会议并增加提醒
    updateZjFlowMeetingReserveTimingRemind:"updateZjFlowMeetingReserveTimingRemind",//修改流程会议提醒
    
	getZjMeetingInsideMeetingSelectAllList:"getZjMeetingInsideMeetingSelectAllList",//获取内部会议下拉集合
	getZjMeetingInsideMeetingDetail:"getZjMeetingInsideMeetingDetail",//获取内部会议详情
	getZjMeetingFlowSelectAllList:"getZjMeetingFlowSelectAllList",//获取会议室预定下拉集合	
    zjMeetingRoomRemindSendMessage:"zjMeetingRoomRemindSendMessage",//手动发消息提醒
    getZjMeetingPlanFallInInfoListAddDept:"getZjMeetingPlanFallInInfoListAddDept",//查询计划填报基本信息
    updateZjMeetingPlanFallInInfoAddDept:"updateZjMeetingPlanFallInInfoAddDept",//更新计划填报基本信息
    addZjMeetingPlanFallInInfoAddDept:"addZjMeetingPlanFallInInfoAddDept",//新增计划填报基本信息
    getZjMeetingPlanFallInInfoDetailByDeptId:"getZjMeetingPlanFallInInfoDetailByDeptId",//查询计划填报基本信息详情
    batchDeleteUpdateZjMeetingPlanFallInInfo:"batchDeleteUpdateZjMeetingPlanFallInInfo",//删除计划填报基本信息
    getZjMeetingPlanFallInListDeptId:"getZjMeetingPlanFallInListDeptId",//查询计划填报
    addZjMeetingPlanFallInByDeptId:"addZjMeetingPlanFallInByDeptId",//新增计划填报
    updateZjMeetingPlanFallInByDeptId:"updateZjMeetingPlanFallInByDeptId",//更新计划填报
    batchDeleteUpdateZjMeetingPlanFallIn:"batchDeleteUpdateZjMeetingPlanFallIn",//删除计划填报
    zjMeetingPlanSubmitApply:"zjMeetingPlanSubmitApply",//提交申请
    zjMeetingPlanSubmitApproval:"zjMeetingPlanSubmitApproval",//部门负责人提交审批
    getZjMeetingPlanFallInListFallInYear:"getZjMeetingPlanFallInListFallInYear",//根据年度获取所有部门下的会议
    getZjMeetingPlanFallInSummaryList:"getZjMeetingPlanFallInSummaryList",//查询计划填报汇总
    getZjMeetingPlanIsLeaderJurisdiction:"getZjMeetingPlanIsLeaderJurisdiction",//查询用户是否拥有领导权限
    zjMeetingPlanSummaryReturn:"zjMeetingPlanSummaryReturn",//汇总退回
    getZjmeetingHaveMealsStatisticsList:"getZjmeetingHaveMealsStatisticsList",//查询会议室用餐统计列表

	
	startFlow: "startFlow",
    submitFlow: "submitFlow",
    getSubmitFlow: "getSubmitFlow",
    getTodoList: "getTodoList",
    getHasTodoList: "getHasTodoList",
    createOpenFlow:"createOpenFlow",
    openFlow: "openFlow",
    actionFlow: "actionFlow"
	
}
// window.apih5 = window.l = new Apih5(apiNames,'http://weixin.fheb.cn:99/apiwoa/')
window.apih5 = window.l = new Apih5(apiNames,'http://192.168.1.123:8080/web/')

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


