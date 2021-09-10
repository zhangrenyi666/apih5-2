var apiNames = {
    getDepartment: "apiPostSysDepartmentList/2", // oa部门获取
    //getDepartment: "apiPostDepartment", // oa部门获取
    //getMember: "apiPostMember", // oa部门人员获取
    getMember: "apiPostMember", // oa部门人员获取
    updaloadFiles: "updaloadFiles", // 文件上传
	
    getZjLdzbLeaderPlanWeekList:"getZjLdzbLeaderPlanWeekList",//本周列表
    getZjLdzbLeaderPlanNextWeekList:"getZjLdzbLeaderPlanNextWeekList",//下周列表
    selectWeekTableTitle:"selectWeekTableTitle",//本周表头
    selectNextWeekTableTitle:"selectNextWeekTableTitle", //下周表头
    getZjLdzbOaMemberAllSelectList:"getZjLdzbOaMemberAllSelectList",//下拉名单
    getZjLdzbEditJurisdictionMember:"getZjLdzbEditJurisdictionMember",//点击修改
    addZjLdzbLeaderPlan:"addZjLdzbLeaderPlan",//新增下周
    getZjLdzbLeaderPlanStatistics:"getZjLdzbLeaderPlanStatistics",//统计列表
    updateZjLdzbLeaderPlan1:"updateZjLdzbLeaderPlan1", //修改接口
    getZjLdzbOaMemberList: "getZjLdzbOaMemberList", // 获取领导集合
    addZjLdzbOaMember: "addZjLdzbOaMember", // 新增领导人员
    updateZjLdzbOaMember: "updateZjLdzbOaMember", // 修改领导人员
    batchDeleteUpdateZjLdzbOaMember: "batchDeleteUpdateZjLdzbOaMember", // 批量删除领导人员
	getZjLdzbJurisdictionMemberList: "getZjLdzbJurisdictionMemberList", // 获取权限人员集合
    addZjLdzbJurisdictionMember: "addZjLdzbJurisdictionMember", // 新增权限人员
    batchDeleteUpdateZjLdzbJurisdictionMember: "batchDeleteUpdateZjLdzbJurisdictionMember", // 批量删除权限人员
    getZjLdzbLeaderPlanReportForm:"getZjLdzbLeaderPlanReportForm",//列表接口
    ldzbLeaderPlanStatisticsExport:"ldzbLeaderPlanStatisticsExport", //列表接口
    ldzbLeaderPlanReportFormExport:"ldzbLeaderPlanReportFormExport", //列表接口
    getZjLdzbEditJurisdictionMemberList:"getZjLdzbEditJurisdictionMemberList", //列表接口
    addZjLdzbEditJurisdictionMember:"addZjLdzbEditJurisdictionMember", //列表接口
    updateZjLdzbEditJurisdictionMember:"updateZjLdzbEditJurisdictionMember", //列表接口
    batchDeleteUpdateZjLdzbEditJurisdictionMember:"batchDeleteUpdateZjLdzbEditJurisdictionMember", //列表接口


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
//window.apih5 = window.l = new Apih5(apiNames, 'http://192.168.1.109:8080/')
//window.apih5 = window.l = new Apih5(apiNames, 'http://localhost:8080/web/')
window.apih5 = window.l = new Apih5(apiNames,'http://weixin.fheb.cn:99/apiwoa/')
