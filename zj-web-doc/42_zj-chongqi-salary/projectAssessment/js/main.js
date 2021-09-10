var apiNames = {
    getDepartment: "apiPostSysDepartmentList/1", // oa部门获取
    getMember: "apiPostMember", // oa部门人员获取
    updateFiles: "updateFiles", // 文件上传
    // 设置项目绩效考核管理员
    getZjXmCqjxProjectSetupAdminList:"getZjXmCqjxProjectSetupAdminList",
    addZjXmCqjxProjectSetupAdmin:"addZjXmCqjxProjectSetupAdmin",
    batchDeleteUpdateZjXmCqjxProjectSetupAdmin:"batchDeleteUpdateZjXmCqjxProjectSetupAdmin",
    // 设置项目纪律性考核管理员
    getZjXmCqjxProjectSetupPersonnelList:"getZjXmCqjxProjectSetupPersonnelList",
    addZjXmCqjxProjectSetupPersonnel:"addZjXmCqjxProjectSetupPersonnel",
    batchDeleteUpdateZjXmCqjxProjectSetupPersonnel:"batchDeleteUpdateZjXmCqjxProjectSetupPersonnel",
    // 部门负责人设置
    getZjXmCqjxProjectDepartmentHeadList: "getZjXmCqjxProjectDepartmentHeadList",
    addZjXmCqjxProjectDepartmentHead: "addZjXmCqjxProjectDepartmentHead",
    updateZjXmCqjxProjectDepartmentHead: "updateZjXmCqjxProjectDepartmentHead",
    batchDeleteUpdateZjXmCqjxProjectDepartmentHead:"batchDeleteUpdateZjXmCqjxProjectDepartmentHead",
    // 绩效考核计划
    getZjXmCqjxProjectAssessmentManageList:"getZjXmCqjxProjectAssessmentManageList",
    addZjXmCqjxProjectAssessmentManage:"addZjXmCqjxProjectAssessmentManage",
    updateZjXmCqjxProjectAssessmentManage:"updateZjXmCqjxProjectAssessmentManage",
    batchDeleteUpdateZjXmCqjxProjectAssessmentManage:"batchDeleteUpdateZjXmCqjxProjectAssessmentManage",
    batchZjXmCqjxProjectAssessmentManageSendMessage: "batchZjXmCqjxProjectAssessmentManageSendMessage",
    zjXmCqjxProjectAssessmentManageDetailByQuarter:"zjXmCqjxProjectAssessmentManageDetailByQuarter",
    getZjXmCqjxProjectAssessmentManageListByDeptHeader:"getZjXmCqjxProjectAssessmentManageListByDeptHeader",
    //副总师、经理助理半年度绩效考核
    getZjXmCqjxProjectExecutiveAssistantList:"getZjXmCqjxProjectExecutiveAssistantList",
    zjXmCqjxProjectExecutiveAssistantFillIn:"zjXmCqjxProjectExecutiveAssistantFillIn",
    updateZjXmCqjxProjectExecutiveAssistantFillIn:"updateZjXmCqjxProjectExecutiveAssistantFillIn",
    zjXmCqjxProjectExecutiveAssistantLaunch:"zjXmCqjxProjectExecutiveAssistantLaunch",
    zjXmCqjxProjectExecutiveScoreLaunch:"zjXmCqjxProjectExecutiveScoreLaunch",
    checkZjXmCqjxProjectExecutiveAssistantLaunch:"checkZjXmCqjxProjectExecutiveAssistantLaunch",    
    zjXmCqjxProjectExecutiveAssistantReleaseLock:"zjXmCqjxProjectExecutiveAssistantReleaseLock", 
    zjXmCqjxProjectExecutiveAssistantSpecialLaunch:"zjXmCqjxProjectExecutiveAssistantSpecialLaunch", 
    //明细
    getZjXmCqjxProjectDeputyLeaderAssistantDetailedList:"getZjXmCqjxProjectDeputyLeaderAssistantDetailedList",
    addZjXmCqjxProjectDeputyLeaderAssistantDetailed:"addZjXmCqjxProjectDeputyLeaderAssistantDetailed",
    updateZjXmCqjxProjectDeputyLeaderAssistantDetailed:"updateZjXmCqjxProjectDeputyLeaderAssistantDetailed",
    batchDeleteUpdateZjXmCqjxProjectDeputyLeaderAssistantDetailed:"batchDeleteUpdateZjXmCqjxProjectDeputyLeaderAssistantDetailed",
    //部门正、副职季度绩效考核表
    getZjXmCqjxProjectDeptLeaderAssistantList:"getZjXmCqjxProjectDeptLeaderAssistantList",
    zjXmCqjxProjectDeptLeaderAssistantFillIn:"zjXmCqjxProjectDeptLeaderAssistantFillIn",
    zjXmCqjxProjectDeptLeaderAssistantLaunch:"zjXmCqjxProjectDeptLeaderAssistantLaunch",
    zjXmCqjxProjectDeptLeaderScoreLaunch:"zjXmCqjxProjectDeptLeaderScoreLaunch",
    zjXmCqjxProjectDeptLeaderAssistantSpecialLaunch:"zjXmCqjxProjectDeptLeaderAssistantSpecialLaunch",
    //明细
    getZjXmCqjxProjectSecretaryrAssistantDetailedList:"getZjXmCqjxProjectSecretaryrAssistantDetailedList",
    addZjXmCqjxProjectSecretaryrAssistantDetailed:"addZjXmCqjxProjectSecretaryrAssistantDetailed",
    updateZjXmCqjxProjectSecretaryrAssistantDetailed:"updateZjXmCqjxProjectSecretaryrAssistantDetailed",
    batchDeleteUpdateZjXmCqjxProjectSecretaryrAssistantDetailed:"batchDeleteUpdateZjXmCqjxProjectSecretaryrAssistantDetailed",
    //员工季度绩效考核表
    getZjXmCqjxProjectStaffAssistantList:"getZjXmCqjxProjectStaffAssistantList",
    zjXmCqjxProjectStaffAssistantFillIn:"zjXmCqjxProjectStaffAssistantFillIn",
    zjXmCqjxProjectStaffAssistantLaunch:"zjXmCqjxProjectStaffAssistantLaunch",
    zjXmCqjxProjectStaffScoreLaunch:"zjXmCqjxProjectStaffScoreLaunch",
    zjXmCqjxProjectStaffAssistantSpecialLaunch:"zjXmCqjxProjectStaffAssistantSpecialLaunch",
    //明细
    getZjXmCqjxProjectStaffAssistantDetailedList:"getZjXmCqjxProjectStaffAssistantDetailedList",
    addZjXmCqjxProjectStaffAssistantDetailed:"addZjXmCqjxProjectStaffAssistantDetailed",
    updateZjXmCqjxProjectStaffAssistantDetailed:"updateZjXmCqjxProjectStaffAssistantDetailed",
    batchDeleteUpdateZjXmCqjxProjectStaffAssistantDetailed:"batchDeleteUpdateZjXmCqjxProjectStaffAssistantDetailed",
    //领导待办
    getZjXmCqjxProjectAssistantTodoList:"getZjXmCqjxProjectAssistantTodoList",
    zjXmCqjxProjectAssistantChargeLeaderApproval:"zjXmCqjxProjectAssistantChargeLeaderApproval",
    zjXmCqjxProjectAssistantExecutiveLeaderApproval:"zjXmCqjxProjectAssistantExecutiveLeaderApproval",
    zjXmCqjxProjectAssistantChargeLeaderScore:"zjXmCqjxProjectAssistantChargeLeaderScore",
    zjXmCqjxProjectAssistantExecutiveLeaderScore:"zjXmCqjxProjectAssistantExecutiveLeaderScore",
    // 员工纪律性考核列表
    getZjXmCqjxProjectDisciplineAssessmentList:"getZjXmCqjxProjectDisciplineAssessmentList",
    getZjXmCqjxProjectDisciplineAssessmentMemberList:"getZjXmCqjxProjectDisciplineAssessmentMemberList",
    batchAddZjXmCqjxProjectDisciplineAssessmentMember:"batchAddZjXmCqjxProjectDisciplineAssessmentMember",
    zjXmCqjxProjectDisciplineAssessmentSubmit:"zjXmCqjxProjectDisciplineAssessmentSubmit",
    zjXmCqjxProjectDisciplineAssessmentToDoList:"zjXmCqjxProjectDisciplineAssessmentToDoList",
    zjXmCqjxProjectDisciplineAssessmentDeptApproval:"zjXmCqjxProjectDisciplineAssessmentDeptApproval",
    zjXmCqjxProjectDisciplineAssessmentExecutiveApproval:"zjXmCqjxProjectDisciplineAssessmentExecutiveApproval",
    //总体评价考核
    getZjXmCqjxProjectEvaluationAssistantTodoList:"getZjXmCqjxProjectEvaluationAssistantTodoList",
    updateZjXmCqjxProjectEvaluationApproval:"updateZjXmCqjxProjectEvaluationApproval",
    updateZjXmCqjxProjectSuperviseApproval:"updateZjXmCqjxProjectSuperviseApproval",
    getZjXmCqjxProjectOverallAssistantListByPrimaryKey:"getZjXmCqjxProjectOverallAssistantListByPrimaryKey",
    zjXmCqjxProjectEvaluationCadreScore:"zjXmCqjxProjectEvaluationCadreScore",
    zjXmCqjxProjectEvaluationOtherLeaderScore:"zjXmCqjxProjectEvaluationOtherLeaderScore",
    zjXmCqjxProjectEvaluationComChargeScore:"zjXmCqjxProjectEvaluationComChargeScore",
    zjXmCqjxProjectEvaluationComExecutiveScore:"zjXmCqjxProjectEvaluationComExecutiveScore",
    zjXmCqjxProjectEvaluationOfficeScore:"zjXmCqjxProjectEvaluationOfficeScore",
    getZjXmCqjxProjectOverallEvaluationAssistantList:"getZjXmCqjxProjectOverallEvaluationAssistantList",
    //考核暂存
    zjXmCqjxProjectExecutiveAssistantReleaseTempSave:"zjXmCqjxProjectExecutiveAssistantReleaseTempSave",
    //检查评分
    checkZjXmCqjxProjectAssistantScoreQualified:"checkZjXmCqjxProjectAssistantScoreQualified",
    //获取审核人下拉集合
    getZjXmCqjxProjectLeaderApprovalAllList:"getZjXmCqjxProjectLeaderApprovalAllList",
    //考核流程发起前检查所有计划流程是否走完
    checkZjXmCqjxProjectFinishPlanAssistant:"checkZjXmCqjxProjectFinishPlanAssistant",
    //项目月度考核综合排名
    getZjXmCqjxProjectComprehensiveRanking:"getZjXmCqjxProjectComprehensiveRanking",
    getZjXmCqjxProjectAssistantListByDeptLeader:"getZjXmCqjxProjectAssistantListByDeptLeader",
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

// window.apih5 = window.l = new Apih5(apiNames,'http://zj.bjzxkj.com/apiCqgs/')
 window.apih5 = window.l = new Apih5(apiNames,'http://192.168.1.132:8080/web/')
//window.apih5 = window.l = new Apih5(apiNames,'http://wyongan.xicp.net/web/')
// window.apih5 = window.l = new Apih5(apiNames,'http://123.146.170.235:88/apiCqgs/')
