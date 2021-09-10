var apiNames = {
   /*   getDepartment: "tempPostDepartmentXiamen", // oa部门获取
    getMember: "tempMemberXiamen", // oa部门人员获取
    updateFiles: "updateFiles", // 文件上传 */
    getDepartment: "apiPostSysDepartmentList/1", // oa部门获取
    getMember: "apiPostMember", // oa部门人员获取
    updateFiles: "uploadFilesByFileName", // 文件上传
	
    getZjQuestionClassList: "getZjQuestionClassList", // 查询问题类别
    addZjQuestionClass: "addZjQuestionClass", // 新增问题类别
    updateZjQuestionClass: "updateZjQuestionClass", // 更新问题类别
    batchDeleteUpdateZjQuestionClass: "batchDeleteUpdateZjQuestionClass", // 批量删除问题类别
	getQuestionClassAllList: "getQuestionClassAllList", // 问题类别下拉列表
	
    getZjQuestionManagerList: "getZjQuestionManagerList", // 查询问题管理
    addZjQuestionManager: "addZjQuestionManager", // 新增问题管理
    updateZjQuestionManager: "updateZjQuestionManager", // 更新问题管理
    batchDeleteUpdateZjQuestionManager: "batchDeleteUpdateZjQuestionManager", // 删除问题管理
	
    getZjQuestionApprovalList: "getZjQuestionApprovalList", // 查询问题审批
    addZjQuestionApproval: "addZjQuestionApproval", // 新增问题审批
    getAskAndAnswerDetail: "getAskAndAnswerDetail", // 获取问答详情
    getQuestionDetail: "getQuestionDetail", // 获取问题详情
	
	addZjQuestionAsk:"addZjQuestionAsk",//新增追问
	addZjQuestionAnswer:"addZjQuestionAnswer",//新增回答
	getQuestionAllListByClassId:"getQuestionAllListByClassId",//问题联动下拉列表
	leaderAgree:"leaderAgree",//领导人同意
	leaderReject:"leaderReject",//领导人驳回
	sendRemind:"sendRemind",//发送提醒
	questionClose:"questionClose",//问题关闭
	
	sendRemindForWechat:"sendRemindForWechat",//发送提醒（微信端）

	getQuestionStatisticsList:"getQuestionStatisticsList",//问题统计列表
	batchImportXmQuestion:"batchImportXmQuestion",//问题导入
    exportQuestionClassToExcel:"exportQuestionClassToExcel",//问题批量导出	
	//getQuestionSelectThreeAllList:"getQuestionSelectThreeAllList",//三级联动
	xmQuestionExport:"xmQuestionExport",//问题统计列表导出Excel
	
	getZjXmQuestionSetupMemberList:"getZjXmQuestionSetupMemberList",//查询设置问题管理员
	addZjXmQuestionSetupMember:"addZjXmQuestionSetupMember",//新增设置问题管理员
	updateZjXmQuestionSetupMember:"updateZjXmQuestionSetupMember",//更新设置问题管理员
	batchDeleteUpdateZjXmQuestionSetupMember:"batchDeleteUpdateZjXmQuestionSetupMember",//删除设置问题管理员
	
	getQuestionManagementFlag:"getQuestionManagementFlag",//查询是否为问题管理人员
	
	getQuestionOneGradeSelectList:'getQuestionOneGradeSelectList',//问题下拉一级
    getQuestionTwoGradeSelectList:'getQuestionTwoGradeSelectList',//问题下拉二级
    getQuestionThreeGradeSelectList:'getQuestionThreeGradeSelectList',//问题下拉三级
    getQuestionFourGradeSelectList:'getQuestionFourGradeSelectList',//问题下拉四级
	
	getZjXmQuestionApplyPersonList:"getZjXmQuestionApplyPersonList",//查问题申请人列表
	addZjXmQuestionApplyPerson:"addZjXmQuestionApplyPerson",//新增问题申请人
	updateZjXmQuestionApplyPerson:"updateZjXmQuestionApplyPerson",//修改问题申请人
	batchDeleteUpdateZjXmQuestionApplyPerson:"batchDeleteUpdateZjXmQuestionApplyPerson",//批量删除问题申请人
	judgeQuestionApplyPersonFlag:"judgeQuestionApplyPersonFlag",//判断是否为问题申请人员
	

    getQuestionListReportForm:"getQuestionListReportForm", //问题统计查询
    getZjQuestionApprovalListByquestionId:"getZjQuestionApprovalListByquestionId",//根据w问题ID查询审批列表
    questionListReportFormExport:"questionListReportFormExport",//问题统计列表数据导出
	
    getZjXmQuestionScreenMemberList:"getZjXmQuestionScreenMemberList",//查询问题筛选人员
    addZjXmQuestionScreenMember:"addZjXmQuestionScreenMember",//新增问题筛选人员
    updateZjXmQuestionScreenMember:"updateZjXmQuestionScreenMember",//更新问题筛选人员
    batchDeleteUpdateZjXmQuestionScreenMember:"batchDeleteUpdateZjXmQuestionScreenMember",//删除问题筛选人员
	
    getZjXmQuestionTitleList:"getZjXmQuestionTitleList",//查询问题标题管理
    addZjXmQuestionTitle:"addZjXmQuestionTitle",//新增问题标题管理
    updateZjXmQuestionTitle:"updateZjXmQuestionTitle",//更新问题标题管理
    batchDeleteUpdateZjXmQuestionTitle:"batchDeleteUpdateZjXmQuestionTitle",//删除问题标题管理
	
    getZjQuestionPorjectAllList:"getZjQuestionPorjectAllList",//查询项目下拉集合
	
    getZjXmQuestionInformationList:"getZjXmQuestionInformationList",//查询问题基本信息
    addZjXmQuestionInformation:"addZjXmQuestionInformation",//新增问题基本信息
    updateZjXmQuestionInformation:"updateZjXmQuestionInformation",//更新问题基本信息
    batchDeleteUpdateZjXmQuestionInformation:"batchDeleteUpdateZjXmQuestionInformation",//删除问题基本信息
    getZjQueApprovalListByInformationId:"getZjQueApprovalListByInformationId",//根据基本信息ID获取数据
    //addZjQuestionApprovalAddInformationId:"addZjQuestionApprovalAddInformationId",//根据基本信息ID新增问题审批
	
    getZjXmHasTitleQuestionList:"getZjXmHasTitleQuestionList",//查询有标题的问题
    getZjXmNoTitleQuestionList:"getZjXmNoTitleQuestionList",//查询没有标题的问题
	
    getZjXmQuestionScreenList:"getZjXmQuestionScreenList",//查询问题筛选
    batchDeleteUpdateZjXmQuestionScreen:"batchDeleteUpdateZjXmQuestionScreen",//删除问题筛选
    batchAddZjXmQuestionScreen:"batchAddZjXmQuestionScreen",//批量添加问题
	
    getZjXmQuestionTitleSelectAllList:"getZjXmQuestionTitleSelectAllList",//查询问题标题管理下拉集合
    getZjQuestionPersonnelAllListByProject:"getZjQuestionPersonnelAllListByProject",//根据项目查询人员集合
    getZjXmQuestionInformationDetail:"getZjXmQuestionInformationDetail",//根据信息主键ID查询数据
    getQuestionDetailByAskUser:"getQuestionDetailByAskUser",//查询问题详情（提问者）
    getQuestionStatisticsListGroupByClassName:"getQuestionStatisticsListGroupByClassName",//查询问题统计（根据分类分组）
    xmQuestionGroupByClassNameExport:"xmQuestionGroupByClassNameExport",//问题统计导出（根据分类分组）
    judgeQuestionApplyAdminFlag:"judgeQuestionApplyAdminFlag",//查询当前用户是否是管理员
    batchDeleteUpdateZjQuestionApprovalByAdmin:"batchDeleteUpdateZjQuestionApprovalByAdmin",//删除问题审批及关联数据
    addQuestionCheckQuestion:"addQuestionCheckQuestion",//添加个人整改项
    zjQuestionApprovalChooseForwardUser:"zjQuestionApprovalChooseForwardUser",//添加转发人
    getZjXmQuestionOfficeLeaderList:"getZjXmQuestionOfficeLeaderList",//查询机关领导
    addZjXmQuestionOfficeLeaderByPc:"addZjXmQuestionOfficeLeaderByPc",//新增机关领导
    updateZjXmQuestionOfficeLeader:"updateZjXmQuestionOfficeLeader",//更新机关领导
    batchDeleteUpdateZjXmQuestionOfficeLeader:"batchDeleteUpdateZjXmQuestionOfficeLeader",//删除机关领导
    getZjXmQuestionOfficeLeaderAllList:"getZjXmQuestionOfficeLeaderAllList",//获取机关领导下拉接口
	getQuestionHistoryByQuestionApprovalId:"getQuestionHistoryByQuestionApprovalId",
    getQuestionYearSelectList:"getQuestionYearSelectList",
    addZjQueRectificationAddInformationId:"addZjQueRectificationAddInformationId",//根据基本信息ID新增问题审批（整改项）
    addZjQueImprovementAddInformationId:"addZjQueImprovementAddInformationId",//根据基本信息ID新增问题审批（改进项）
    updateQueImprovementItemByChanger:"updateQueImprovementItemByChanger",//整改人阅读（改进项）
    getZjXmQuestionTitleAndQueSelectAllList:"getZjXmQuestionTitleAndQueSelectAllList",//整改人阅读（改进项）
	
    getZjXmQuestionVisitMemberList:"getZjXmQuestionVisitMemberList",//整改人阅读（改进项）
    saveZjXmQuestionVisitMember:"saveZjXmQuestionVisitMember",//整改人阅读（改进项）
    batchDeleteUpdateZjXmQuestionVisitMember:"batchDeleteUpdateZjXmQuestionVisitMember",//整改人阅读（改进项）	
	
}
if($.fn.page){
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
            pageSizeItems: [10, 50, 100,500,1000],
            remote: {
                pageIndexName: 'page',     //请求参数，当前页数，索引从1开始
                pageSizeName: 'limit',       //请求参数，每页数量
                totalName: 'totalNumber'              //指定返回数据的总数据量的字段名
            }
        };
}
//window.apih5 = window.l = new Apih5(apiNames,'http://192.168.1.115:8080/web/')
//window.apih5 = window.l = new Apih5(apiNames,'http://weixin.fheb.cn:99/apiwoa/')
 window.apih5 = window.l = new Apih5(apiNames,' http://xm-oa.fheb.cn:88/apixiamengs/')
