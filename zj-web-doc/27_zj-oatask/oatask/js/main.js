var apiNames = {
    getDepartment: "getDepartment", // oa部门获取
    getMember: "getMember", // oa部门人员获取
    updateFiles: "updateFiles", // 文件上传
	getBaseCodeSelect:"getBaseCodeSelect",//
	getLoginUserInfo:"getLoginUserInfo",//自动获取登录人的信息
	uploadFilesByFileName:"uploadFilesByFileName",
	exportExcelZjOataskAllList:"exportExcelZjOataskAllList",
	exportExcelZjOataskOneList:"exportExcelZjOataskOneList",
	exportExcelZjOataskTwoList:"exportExcelZjOataskTwoList",
	exportExcelZjOataskThreeList:"exportExcelZjOataskThreeList",
	exportExcelZjOataskFourList:"exportExcelZjOataskFourList",
	getZjOataskTodoList:"getZjOataskTodoList",
	
	
    
	getZjOataskList:"getZjOataskList",
	addZjOatask:"addZjOatask",
	updateZjOatask:"updateZjOatask",
	updateZjOataskByEdit:"updateZjOataskByEdit",
	updateZjOataskState:"updateZjOataskState",
	getZjOataskDetail:"getZjOataskDetail",
    batchDeleteUpdateZjOatask:"batchDeleteUpdateZjOatask",
    getZjOataskStatisticAnalysisList:"getZjOataskStatisticAnalysisList",
    getZjOataskRollingList:"getZjOataskRollingList",
    getZjOataskColumnList:"getZjOataskColumnList",
	getZjOataskSelfList:"getZjOataskSelfList",
    getZjOataskDailyList:"getZjOataskDailyList",
    
    getZjOataskWeekPlanList:"getZjOataskWeekPlanList",
    addZjOataskWeekPlan:"addZjOataskWeekPlan",
    batchDeleteUpdateZjOataskWeekPlan:"batchDeleteUpdateZjOataskWeekPlan",
    getZjOataskWeekPlanDetail:"getZjOataskWeekPlanDetail",

    getZjOataskWeekPlanItemList:"getZjOataskWeekPlanItemList",
    addZjOataskWeekPlanItem:"addZjOataskWeekPlanItem",
    updateZjOataskWeekPlanItem:"updateZjOataskWeekPlanItem",
    batchDeleteUpdateZjOataskWeekPlanItem:"batchDeleteUpdateZjOataskWeekPlanItem",
    getZjOataskTaskMatterList:"getZjOataskTaskMatterList",

    getZjOataskWeekPlanItemPerformanceList:"getZjOataskWeekPlanItemPerformanceList",
    getZjOataskWeekPlanDetail:"getZjOataskWeekPlanDetail",
    updateZjOataskWeekPlanItemPerformance:"updateZjOataskWeekPlanItemPerformance",

    getZjOataskWeekPlanTotalList:"getZjOataskWeekPlanTotalList"
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
//ndow.lny = window.l = new Lny(apiNames,'http://weixin.fheb.cn:98/apiwoa/')
//window.lny = window.l = new Lny(apiNames,'http://xa8bt70bri.51http.tech/apiwoa/')
//window.lny = window.l = new Lny(apiNames,'http://weixin.fheb.cn:8080/woa/')
window.lny = window.l = new Lny(apiNames,'http://weixin.fheb.cn:99/apiwoa/')
// window.lny = window.l = new Lny(apiNames,'http://192.168.1.223:8080/web/')
