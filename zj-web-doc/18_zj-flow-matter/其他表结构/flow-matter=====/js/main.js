var apiNames = {
    getDepartment: "apiPostDepartment2", // oa部门获取
    getMember: "apiPostMember", // oa部门人员获取
    updateFiles: "updateFiles", // 文件上传
	
    getZjYySealApplyList: "getZjYySealApplyList", // 查询用印申请列表
    addZjYySealApply: "addZjYySealApply", // 新增用印申请
	batchDeleteUpdateZjYySealApply:"batchDeleteUpdateZjYySealApply",//批量删除申请
	getSealApplyDetail:"getSealApplyDetail",//用印申请详情
	
	secretarySendSealApply: "secretarySendSealApply",//部门文秘 给 申请人    、部门负责人  、分管公司领导  发送消息
	applySendSealApplyToOffice:"applySendSealApplyToOffice",//经办人  发给 办公室归档人员
	
	addZjYySealApproval:"addZjYySealApproval",//新增审批用印
	
	getZjYySealArchiveList:"getZjYySealArchiveList",//查询归档列表
	addZjYySealArchive:"addZjYySealArchive",//确认归档
	
	exportSealToWord:"exportSealToWord",//导出word
    exportSealToExcel:"exportSealToExcel",//导出excel
    startFlow:"startFlow",
    submitFlow:"submitFlow",
    getSubmitFlow:"getSubmitFlow",
    getTodoList:"getTodoList",
    getHasTodoList:"getHasTodoList",
	createOpenFlow:"createOpenFlow",
    openFlow: "openFlow",
    actionFlow: "actionFlow",
    getSignatureCompanyTotalList:'getSignatureCompanyTotalList'//签字统计的第一个接口==获取督导公司列表
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
window.lny = window.l = new Lny(apiNames,'http://192.168.1.119:8080/web/')
