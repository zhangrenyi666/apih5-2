var apiNames = {
    getDepartment: "apiPostSysDepartmentList/1", // oa部门获取
    getMember: "apiPostMember", // oa部门人员获取
    updateFiles: "updateFiles", // 文件上传


    startFlow: "startFlow",
    submitFlow: "submitFlow",
    getSubmitFlow: "getSubmitFlow",
    getTodoList: "getTodoList",
    getHasTodoList: "getHasTodoList",
    createOpenFlow: "createOpenFlow",
    openFlow: "openFlow",
    actionFlow: "actionFlow",

    getZjFlowPersonApproveList: "getZjFlowPersonApproveList",
    getZjFlowPersonApproveDetail: "getZjFlowPersonApproveDetail",
    addZjFlowPersonApprove: "addZjFlowPersonApprove",
    updateZjFlowPersonApprove: "updateZjFlowPersonApprove",	
    batchDeleteUpdateZjFlowPersonApprove: "batchDeleteUpdateZjFlowPersonApprove",

    getZjFlowPersonInfoList:'getZjFlowPersonInfoList',
    getZjHwSarsUserDetails:'getZjHwSarsUserDetails',
    addZjFlowPersonInfo:"addZjFlowPersonInfo",
    updateZjFlowPersonInfo:"updateZjFlowPersonInfo",
    batchDeleteUpdateZjFlowPersonInfo:"batchDeleteUpdateZjFlowPersonInfo",
	approveZjFlowPersonInfo:"approveZjFlowPersonInfo",
	updateZjFlowPersonApproveForFlow:"updateZjFlowPersonApproveForFlow",
	getTodoListByReport:"getTodoListByReport",
	getHasTodoListByReport:"getHasTodoListByReport",

    // ---
    getZjFlowMinisterApproveList: "getZjFlowMinisterApproveList",
    getZjFlowMinisterApproveDetail: "getZjFlowMinisterApproveDetail",
    addZjFlowMinisterApprove: "addZjFlowMinisterApprove",
    updateZjFlowMinisterApprove: "updateZjFlowMinisterApprove",	
    batchDeleteUpdateZjFlowMinisterApprove: "batchDeleteUpdateZjFlowMinisterApprove",
    getZjFlowMinisterInfoList: 'getZjFlowMinisterInfoList',
    addZjFlowMinisterInfo: "addZjFlowMinisterInfo",
    updateZjFlowMinisterInfo:"updateZjFlowMinisterInfo",
    batchDeleteUpdateZjFlowMinisterInfo:"batchDeleteUpdateZjFlowMinisterInfo",
	approveZjFlowMinisterInfo:"approveZjFlowMinisterInfo",
    updateZjFlowMinisterApproveForFlow:"updateZjFlowMinisterApproveForFlow",
	batchDeleteUpdateZjFlowTodo:"batchDeleteUpdateZjFlowTodo",
    getHasReadedList: "getHasReadedList",
    getReadedList: "getReadedList",
    // 部门负责人设置
    getZjXmCqjxDepartmentHeadList: "getZjXmCqjxDepartmentHeadList",
    addZjXmCqjxDepartmentHead: "addZjXmCqjxDepartmentHead",
    updateZjXmCqjxDepartmentHead: "updateZjXmCqjxDepartmentHead",
    batchDeleteUpdateZjXmCqjxDepartmentHead:"batchDeleteUpdateZjXmCqjxDepartmentHead",
    // 绩效考核计划
    getZjXmCqjxAssessmentManageList:"getZjXmCqjxAssessmentManageList",
    addZjXmCqjxAssessmentManage:"addZjXmCqjxAssessmentManage",
    updateZjXmCqjxAssessmentManage:"updateZjXmCqjxAssessmentManage",
    batchDeleteUpdateZjXmCqjxAssessmentManage:"batchDeleteUpdateZjXmCqjxAssessmentManage",
    zjXmCqjxAssessmentManageSendMessage: "zjXmCqjxAssessmentManageSendMessage",
    batchZjXmCqjxAssessmentManageSendMessage: "batchZjXmCqjxAssessmentManageSendMessage",
    zjXmCqjxAssessmentManageDetailByQuarter:"zjXmCqjxAssessmentManageDetailByQuarter",
    getZjXmCqjxAssessmentManageListByDeptHeader:"getZjXmCqjxAssessmentManageListByDeptHeader",
    getZjXmCqjxAssistantListByDeptLeader:"getZjXmCqjxAssistantListByDeptLeader",
    //副总师、经理助理半年度绩效考核
    getZjXmCqjxExecutiveAssistantList:"getZjXmCqjxExecutiveAssistantList",
    zjXmCqjxExecutiveAssistantFillIn:"zjXmCqjxExecutiveAssistantFillIn",
    updateZjXmCqjxExecutiveAssistantFillIn:"updateZjXmCqjxExecutiveAssistantFillIn",
    zjXmCqjxExecutiveAssistantLaunch:"zjXmCqjxExecutiveAssistantLaunch",
    zjXmCqjxExecutiveScoreLaunch:"zjXmCqjxExecutiveScoreLaunch",
    checkZjXmCqjxExecutiveAssistantLaunch:"checkZjXmCqjxExecutiveAssistantLaunch",    
    zjXmCqjxExecutiveAssistantReleaseLock:"zjXmCqjxExecutiveAssistantReleaseLock",    
    checkZjXmCqjxAssessmentManageState:"checkZjXmCqjxAssessmentManageState",    
    batchDeleteUpdateZjXmCqjxExecutiveAssistant:"batchDeleteUpdateZjXmCqjxExecutiveAssistant",    
    //明细
    getZjXmCqjxExecutiveAssistantDetailedList:"getZjXmCqjxExecutiveAssistantDetailedList",
    addZjXmCqjxExecutiveAssistantDetailed:"addZjXmCqjxExecutiveAssistantDetailed",
    updateZjXmCqjxExecutiveAssistantDetailed:"updateZjXmCqjxExecutiveAssistantDetailed",
    batchDeleteUpdateZjXmCqjxExecutiveAssistantDetailed:"batchDeleteUpdateZjXmCqjxExecutiveAssistantDetailed",
    //部门正、副职季度绩效考核表
    getZjXmCqjxDeptLeaderAssistantList:"getZjXmCqjxDeptLeaderAssistantList",
    zjXmCqjxDeptLeaderAssistantFillIn:"zjXmCqjxDeptLeaderAssistantFillIn",
    zjXmCqjxDeptLeaderAssistantLaunch:"zjXmCqjxDeptLeaderAssistantLaunch",
    zjXmCqjxDeptLeaderScoreLaunch:"zjXmCqjxDeptLeaderScoreLaunch",
    //明细
    getZjXmCqjxDeptLeaderAssistantDetailedList:"getZjXmCqjxDeptLeaderAssistantDetailedList",
    addZjXmCqjxDeptLeaderAssistantDetailed:"addZjXmCqjxDeptLeaderAssistantDetailed",
    updateZjXmCqjxDeptLeaderAssistantDetailed:"updateZjXmCqjxDeptLeaderAssistantDetailed",
    batchDeleteUpdateZjXmCqjxDeptLeaderAssistantDetailed:"batchDeleteUpdateZjXmCqjxDeptLeaderAssistantDetailed",
    //员工季度绩效考核表
    getZjXmCqjxStaffAssistantList:"getZjXmCqjxStaffAssistantList",
    zjXmCqjxStaffAssistantFillIn:"zjXmCqjxStaffAssistantFillIn",
    zjXmCqjxStaffAssistantLaunch:"zjXmCqjxStaffAssistantLaunch",
    zjXmCqjxStaffScoreLaunch:"zjXmCqjxStaffScoreLaunch",
    //明细
    getZjXmCqjxStaffAssistantDetailedList:"getZjXmCqjxStaffAssistantDetailedList",
    addZjXmCqjxStaffAssistantDetailed:"addZjXmCqjxStaffAssistantDetailed",
    updateZjXmCqjxStaffAssistantDetailed:"updateZjXmCqjxStaffAssistantDetailed",
    batchDeleteUpdateZjXmCqjxStaffAssistantDetailed:"batchDeleteUpdateZjXmCqjxStaffAssistantDetailed",
    //领导待办
    getZjXmCqjxAssistantTodoList:"getZjXmCqjxAssistantTodoList",
    zjXmCqjxAssistantChargeLeaderApproval:"zjXmCqjxAssistantChargeLeaderApproval",
    zjXmCqjxAssistantExecutiveLeaderApproval:"zjXmCqjxAssistantExecutiveLeaderApproval",
    zjXmCqjxAssistantChargeLeaderScore:"zjXmCqjxAssistantChargeLeaderScore",
    zjXmCqjxAssistantExecutiveLeaderScore:"zjXmCqjxAssistantExecutiveLeaderScore",
    //员工纪律性考核列表
    getZjXmCqjxDisciplineAssessmentList:"getZjXmCqjxDisciplineAssessmentList",
    getZjXmCqjxDisciplineAssessmentMemberList:"getZjXmCqjxDisciplineAssessmentMemberList",
    getZjXmCqjxDisciplineAssessmentDetailedList:"getZjXmCqjxDisciplineAssessmentDetailedList",
    batchAddZjXmCqjxDisciplineAssessmentMember:"batchAddZjXmCqjxDisciplineAssessmentMember",
    zjXmCqjxDisciplineAssessmentSubmit:"zjXmCqjxDisciplineAssessmentSubmit",
    zjXmCqjxDisciplineAssessmentToDoList:"zjXmCqjxDisciplineAssessmentToDoList",
    zjXmCqjxDisciplineAssessmentDeptApproval:"zjXmCqjxDisciplineAssessmentDeptApproval",
    zjXmCqjxDisciplineAssessmentExecutiveApproval:"zjXmCqjxDisciplineAssessmentExecutiveApproval",
    getZjXmCqjxCollaborationAssessmentDetailedList:"getZjXmCqjxCollaborationAssessmentDetailedList",
    //员工协作性考核列表
    getZjXmCqjxCollaborationAssessmentList:"getZjXmCqjxCollaborationAssessmentList",
    zjXmCqjxCollaborationAssessmentSendMessage:"zjXmCqjxCollaborationAssessmentSendMessage",
    getZjXmCqjxCollaborationAssessmentMemberList:"getZjXmCqjxCollaborationAssessmentMemberList",
    getZjXmCqjxCollaborationAssessmentListByUser:"getZjXmCqjxCollaborationAssessmentListByUser",
    getZjXmCqjxCollaborationMemberDetailsNoSelf:"getZjXmCqjxCollaborationMemberDetailsNoSelf",
    batchAddZjXmCqjxCollaborationAssessmentDetailed:"batchAddZjXmCqjxCollaborationAssessmentDetailed",
    addZjXmCqjxCollaborationAssessment:"addZjXmCqjxCollaborationAssessment",
    updateZjXmCqjxCollaborationAssessment:"updateZjXmCqjxCollaborationAssessment",
    addZjXmCqjxCollaborationAssessmentDetailedByMember:"addZjXmCqjxCollaborationAssessmentDetailedByMember",
    batchDeleteUpdateZjXmCqjxCollaborationAssessment:"batchDeleteUpdateZjXmCqjxCollaborationAssessment",
    zjXmCqjxCollaborationAssistantReleaseLock:"zjXmCqjxCollaborationAssistantReleaseLock",
    //根据员工考核ID获取审核人集合
    getZjXmCqjxAssistantOaLeaderListByExecutiveId:"getZjXmCqjxAssistantOaLeaderListByExecutiveId",
    checkZjXmCqjxAssistantScoreQualified:"checkZjXmCqjxAssistantScoreQualified",
    batchCheckZjXmCqjxExecutiveAssistantLaunch:"batchCheckZjXmCqjxExecutiveAssistantLaunch",
    zjXmCqjxExecutiveAssistantReleaseTempSave:"zjXmCqjxExecutiveAssistantReleaseTempSave",
    //年度考核
    addZjXmCqjxYearAssistantByManagerId:"addZjXmCqjxYearAssistantByManagerId",
    getZjXmCqjxEvaluationAssistantTodoList:"getZjXmCqjxEvaluationAssistantTodoList",
    zjXmCqjxSurroundAssistantApproval:"zjXmCqjxSurroundAssistantApproval",
    zjXmCqjxServiceRolePlayApproval:"zjXmCqjxServiceRolePlayApproval",
    zjXmCqjxWorkPerformanceApproval:"zjXmCqjxWorkPerformanceApproval",
    zjXmCqjxSupervisoryWorkApproval:"zjXmCqjxSupervisoryWorkApproval",    
    updateZjXmCqjxYearScoreApproval:"updateZjXmCqjxYearScoreApproval",    
    getZjXmCqjxProjectEvaluationAssistantTodoList:"getZjXmCqjxProjectEvaluationAssistantTodoList",    
    getZjXmCqjxYearAssistantList:"getZjXmCqjxYearAssistantList",    
    getZjXmCqjxYearAssistantScoreDetail:"getZjXmCqjxYearAssistantScoreDetail",    
    getZjXmCqjxExecutiveAssistantListByYear:"getZjXmCqjxExecutiveAssistantListByYear",    
    getZjXmCqjxYearAssistantListByDeptLeader:"getZjXmCqjxYearAssistantListByDeptLeader",    



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
        pageSizeItems: [10,50,100,500,1000],
        remote: {
            pageIndexName: 'page',     //请求参数，当前页数，索引从1开始
            pageSizeName: 'limit',       //请求参数，每页数量
            totalName: 'totalNumber'              //指定返回数据的总数据量的字段名
        }
    }; 
}

 window.apih5 = window.l = new Apih5(apiNames,'http://localhost:8080/web/')
// window.apih5 = window.l = new Apih5(apiNames,'http://192.168.1.223:99/web/')
//window.apih5 = window.l = new Apih5(apiNames,'http://wyongan.xicp.net/web/')
// window.apih5 = window.l = new Apih5(apiNames,'http://123.146.170.235:88/apiCqgs/')
