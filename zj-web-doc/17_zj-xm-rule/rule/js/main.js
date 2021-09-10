var apiNames = {
   getDepartment: "getDepartment", // oa部门获取
    getMember: "getMember", // oa部门人员获取
    updateFiles: "updateFiles", // 文件上传
	
    getZjXmRulesList: "getZjXmRulesList", // 查询制度列表
	addZjXmRules:"addZjXmRules",//新增
	updateZjXmRules:"updateZjXmRules",//修改
	getZjXmRulesDetail:"getZjXmRulesDetail",//查看制度详情
	batchDeleteUpdateZjXmRules:"batchDeleteUpdateZjXmRules",//批量删除
	batchSubmitZjXmRules:"batchSubmitZjXmRules",//批量提交制度	
	reviseZjXmRules:"reviseZjXmRules",//修订制度
    getZjXmRuleRecordList:"getZjXmRuleRecordList",//年度制度统计列表
	
	mergeWord:"mergeWord",//合并Word
	mergeWord2:"mergeWord2",//根据目录模板合并word
	wordToPDFAndMergePDF:"wordToPDFAndMergePDF",//先将 word转PDF 再合并PDF
	
	getZjXmOaDepartmentList:"getZjXmOaDepartmentList",//OA审核人员查询列表
	addZjXmOaDepartment:"addZjXmOaDepartment",//新增OA审核人员
	updateZjXmOaDepartment:"updateZjXmOaDepartment",//修改OA审核人员
	batchDeleteUpdateZjXmOaDepartment:"batchDeleteUpdateZjXmOaDepartment",//批量删除OA审核人员
	
	
	getZjXmRuleOaMergeList:"getZjXmRuleOaMergeList",//oa 汇编人员列表查询
	addZjXmRuleOaMerge:"addZjXmRuleOaMerge",//oa汇编人新增
	updateZjXmRuleOaMerge:"updateZjXmRuleOaMerge",//修改OA汇编人员
	batchDeleteUpdateZjXmRuleOaMerge:"batchDeleteUpdateZjXmRuleOaMerge",//批量删除OA汇编人员
	getZjXmRuleOaMergeFlag:"getZjXmRuleOaMergeFlag",//判断是否威汇编人员
	batchBackoutZjXmRules:"batchBackoutZjXmRules",//撤销驳回
	exportExcelInUseRuleList:"exportExcelInUseRuleList",//
	
	
	
	
	batchDownloadRuleFile:"batchDownloadRuleFile",//批量下载
	exportExcelRuleRecordList:"exportExcelRuleRecordList",//导出
	getMergeRulesList:"getMergeRulesList",//汇编页面列表查询
	getZjXmReferenceNumberList:"getZjXmReferenceNumberList",//1文号列表
	addZjXmReferenceNumber:"addZjXmReferenceNumber",//1文号新增
	updateZjXmReferenceNumber:"updateZjXmReferenceNumber",//1.2.3文号修改	
	addZjTwoReferenceNumber:"addZjTwoReferenceNumber",//2.3文号新增	
	getTwoReferenceNumberList:"getTwoReferenceNumberList",//2文号列表查询
	getZjXmYearReferenceNumberList:"getZjXmYearReferenceNumberList",//3文号列表查询
	batchDeleteUpdateZjXmReferenceNumber:"batchDeleteUpdateZjXmReferenceNumber",//1.2.3文号删除
	getTwoLinkAllList:"getTwoLinkAllList",//2级联动
	getAllList:"getAllList",//3级联动
	getZjXmRuleRecordListForChart:"getZjXmRuleRecordListForChart" //统计图
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
window.apih5 = window.l = new Apih5(apiNames,'http://192.168.1.223:8080/web/')
// window.apih5 = window.l = new Apih5(apiNames,'http://xm-oa.fheb.cn:88/apixiamengs/')
