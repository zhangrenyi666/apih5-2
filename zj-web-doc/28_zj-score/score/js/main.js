var apiNames = {
    getZjScoreAnnualAssessmentList:"getZjScoreAnnualAssessmentList",//查询年度信息系统应用打分考核
    addZjScoreAnnualAssessment:"addZjScoreAnnualAssessment",//新增年度信息系统应用打分考核
    updateZjScoreAnnualAssessment:"updateZjScoreAnnualAssessment",//更新年度信息系统应用打分考核
    batchDeleteUpdateZjScoreAnnualAssessment:"batchDeleteUpdateZjScoreAnnualAssessment",//删除年度信息系统应用打分考核
    getZjScoreScoringItemList:"getZjScoreScoringItemList",//查询打分项
    addZjScoreScoringItem:"addZjScoreScoringItem",//新增打分项
    updateZjScoreScoringItem:"updateZjScoreScoringItem",//更新打分项
    batchDeleteUpdateZjScoreScoringItem:"batchDeleteUpdateZjScoreScoringItem",//删除打分项
    pcGetZjScoreCompanyList:"pcGetZjScoreCompanyList",//查询公司
    addZjScoreCompany:"addZjScoreCompany",
    updateZjScoreCompany:"updateZjScoreCompany",
    batchDeleteUpdateZjScoreCompany:"batchDeleteUpdateZjScoreCompany" ,
    batchDistributionZjScoreCompanyDetail:"batchDistributionZjScoreCompanyDetail",//批量添加打分项
    getZjScoreScoringItemListByCompanyId:"getZjScoreScoringItemListByCompanyId",//打分项列表
    batchDeleteUpdateZjScoreCompanyDetail:"batchDeleteUpdateZjScoreCompanyDetail",//删除公司得分详情
    getZjScoreCompanyDetailListByAuditor:"getZjScoreCompanyDetailListByAuditor",//根据登录人员获取需要打分的公司
    batchUpdateZjScoreCompanyDetail:"batchUpdateZjScoreCompanyDetail",//打分者批量提交打分
    getZjScoreScoringItemListHeader:"getZjScoreScoringItemListHeader",//查询打分项表头
    pcGetZjScoreCompanyDetailList:"pcGetZjScoreCompanyDetailList",//查询打分项列表
    exportZjScoreCompanyDetail:"exportZjScoreCompanyDetail",//导出管理员查看的统计页面
    getZjScoreAnnualAssessmentHasToDoList:"getZjScoreAnnualAssessmentHasToDoList",//根据登陆的打分者部门获取待办的打分活动列表 
    getZjScoreAnnualAssessmentListByAuditor:"getZjScoreAnnualAssessmentListByAuditor"
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
// window.lny = window.l = new Lny(apiNames,'http://192.168.1.155:8080/web/')
window.lny = window.l = new Lny(apiNames,'http://weixin.fheb.cn:99/apiwoa/')
