var apiNames = {
    getDepartment: "getDepartment", // oa部门获取
    getMember: "getMember", // oa部门人员获取
    updateFiles: "updateFiles", // 文件上传

    getXmZlDatabaseLevelList:"getXmZlDatabaseLevelList",//字典层级
    addXmZlDatabaseLevel:"addXmZlDatabaseLevel",
    updateXmZlDatabaseLevel:"updateXmZlDatabaseLevel",
    batchDeleteUpdateXmZlDatabaseLevel:"batchDeleteUpdateXmZlDatabaseLevel",

    getXmZlDatabaseList:"getXmZlDatabaseList",//资料列表
    addXmZlDatabase:"addXmZlDatabase",
    updateXmZlDatabase:"updateXmZlDatabase",
    batchDeleteUpdateXmZlDatabase:"batchDeleteUpdateXmZlDatabase",

    //安全风险清单
    getXmZlSafetyRiskListLevelList:"getXmZlSafetyRiskListLevelList",//字典层级
    addXmZlSafetyRiskListLevel:"addXmZlSafetyRiskListLevel",
    updateXmZlSafetyRiskListLevel:"updateXmZlSafetyRiskListLevel",
    batchDeleteUpdateXmZlSafetyRiskListLevel:"batchDeleteUpdateXmZlSafetyRiskListLevel",
    getXmZlSafetyRiskListLevelDetail:"getXmZlSafetyRiskListLevelDetail",

    getXmZlSafetyRiskListList:"getXmZlSafetyRiskListList",//资料列表
    addXmZlSafetyRiskList:"addXmZlSafetyRiskList",
    updateXmZlSafetyRiskList:"updateXmZlSafetyRiskList",
    batchDeleteUpdateXmZlSafetyRiskList:"batchDeleteUpdateXmZlSafetyRiskList",
    getXmZlSafetyRiskListDetail:"getXmZlSafetyRiskListDetail"

}
window.apih5 = window.l = new Apih5(apiNames, "http://xm-oa.fheb.cn:88/apixiamengs/")
// window.apih5 = window.l = new Apih5(apiNames, "http://192.168.1.110:8080/web/")

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