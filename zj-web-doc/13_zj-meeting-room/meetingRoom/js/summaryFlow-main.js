var apiNames = {
    //getDepartment: "apiPostSysDepartmentList/1", // oa部门获取
    getDepartment: "apiPostSysDepartmentList/2", // oa部门获取	 
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
    zjMeetingRoomChangeToPdf:"zjMeetingRoomChangeToPdf",//报表转PDF	
    getZjMeetingPlanSelectAllListByDeptId:"getZjMeetingPlanSelectAllListByDeptId",//获取选中部门下所有会议（下拉选择）
    getZjMeetingPlanFallInDetail:"getZjMeetingPlanFallInDetail",//获取计划会议详情  
    
    getZjMeetingPlanFallInListFallInYear:"getZjMeetingPlanFallInListFallInYear",
	
	startFlow: "startFlow",
    submitFlow: "submitFlow",
    getSubmitFlow: "getSubmitFlow",
    getTodoList: "getTodoList",
    getHasTodoList: "getHasTodoList",
    createOpenFlow:"createOpenFlow",
    openFlow: "openFlow",
    actionFlow: "actionFlow"
	
}
// window.apih5 = window.l = new Apih5(apiNames,'http://192.168.1.123:8080/web/')
window.lny = window.l = new Lny(apiNames,'http://192.168.1.123:8080/web/')
// window.apih5 = window.l = new Apih5(apiNames,'http://192.168.1.123:8080/web/')

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


