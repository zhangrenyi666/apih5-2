var apiNames = {
    getDepartment: "apiPostSysDepartmentList/2", // oa部门获取
    getDepartment: "apiPostDepartment", // oa部门获取
    //getMember: "apiPostMember", // oa部门人员获取
    getMember: "apiPostMember", // oa部门人员获取
    updaloadFiles: "updaloadFiles", // 文件上传

    //领导周报
    addZjPxjhWeekPlanAddDept:"addZjPxjhWeekPlanAddDept",
    updateZjPxjhWeekPlanAddDept:"updateZjPxjhWeekPlanAddDept",
    getZjPxjhEditJurisdictionMember:"getZjPxjhEditJurisdictionMember",//权限
    getZjPxjhWeekPlanWeekList:"getZjPxjhWeekPlanWeekList",//本周
    selectZjPxjhWeekTableTitle:"selectZjPxjhWeekTableTitle",//本周
    getZjPxjhWeekPlanNextWeekList:"getZjPxjhWeekPlanNextWeekList",//下周
    selectZjPxjhNextWeekTableTitle:"selectZjPxjhNextWeekTableTitle",//下周
	
	getZjPxjhJurisdictionMemberList:"getZjPxjhJurisdictionMemberList",//查询OA查看权限
    addZjPxjhJurisdictionMember:"addZjPxjhJurisdictionMember",//新增OA查看权限
    batchDeleteUpdateZjPxjhJurisdictionMember:"batchDeleteUpdateZjPxjhJurisdictionMember",//删除OA查看权限
    getZjPxjhEditJurisdictionMemberList:"getZjPxjhEditJurisdictionMemberList",//查询OA编辑权限
    addZjPxjhEditJurisdictionMember:"addZjPxjhEditJurisdictionMember",//新增OA编辑权限
    batchDeleteUpdateZjPxjhEditJurisdictionMember:"batchDeleteUpdateZjPxjhEditJurisdictionMember",//删除OA编辑权限
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
window.apih5 = window.l = new Apih5(apiNames, 'http://192.168.1.123:8080/web/')
//window.apih5 = window.l = new Apih5(apiNames, 'http://localhost:8080/web/')
// window.apih5 = window.l = new Apih5(apiNames,'http://weixin.fheb.cn:99/apiwoa/')
