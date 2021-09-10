var apiNames = {
    getDepartment: "getDepartment", // oa部门获取
    getMember: "getMember", // oa部门人员获取
    updateFiles: "updateFiles", // 文件上传
    getZjZpEvaluationList: "getZjZpEvaluationList", // 评分列表获取
    getUserCountListByReviewer: "getUserCountListByReviewer", // 复评人查看的列表页
    addZjZpEvaluation: "addZjZpEvaluation", //评分表新增
    updateZjZpEvaluation: "updateZjZpEvaluation", //评分表修改
    batchDeleteZjZpEvaluation: "batchDeleteZjZpEvaluation", //评分表批量删除

    IsOrNotEvaluationByUserId: "IsOrNotEvaluationByUserId", //判断是否答过题
    getEvaluationListByUserId: "getEvaluationListByUserId", //获取答题数据
    batchAddUserDetailToDraft: "batchAddUserDetailToDraft", //保存自评数据
    batchAddZjZpUserDetail: "batchAddZjZpUserDetail", //提交自评数据
    getEvaluationListByReviewer: "getEvaluationListByReviewer", //获取答题数据
    batchUpdateZjZpUserDetail: "batchUpdateZjZpUserDetail", //提交复评数据
    getUserScoreRateRankList: "getUserScoreRateRankList", //获取统计数据

    getZjZpAnnualAssessmentList: "getZjZpAnnualAssessmentList",//查询年度评分考核
    addZjZpAnnualAssessment: "addZjZpAnnualAssessment",//新增年度评分考核
    updateZjZpAnnualAssessment: "updateZjZpAnnualAssessment",//更新年度评分考核
    batchDeleteUpdateZjZpAnnualAssessment: "batchDeleteUpdateZjZpAnnualAssessment",//删除年度评分考核
    isOrNotZjZpEvaluationAuditor: "isOrNotZjZpEvaluationAuditor", //判断登陆者是否是审核者
    getZjZpEvaluationAuditorSelect: "getZjZpEvaluationAuditorSelect", //获取自评审核人下拉
    auditorAgreeZjZpUserDetail: "auditorAgreeZjZpUserDetail", //审核人同意提交接口   
    auditorRejectZjZpUserDetail: "auditorRejectZjZpUserDetail", //审核人驳回接口
    getZjZpEvaluationListByAuditor: "getZjZpEvaluationListByAuditor", //审核者获取自评者的提交内容
    exportUserCountListByReviewer: "exportUserCountListByReviewer", //复评导出

    // 登录账号密码
    // 110105196604244154  123456

    // moduleId    moduleName  assessmentId
    // 查询评分题库工作模块分类
    getZjZpEvaluationModuleList: "getZjZpEvaluationModuleList",

    // 新增评分题库工作模块分类 
    addZjZpEvaluationModule: "addZjZpEvaluationModule",

    // 更新评分题库工作模块分类 
    updateZjZpEvaluationModule: "updateZjZpEvaluationModule",

    // 删除评分题库工作模块分类  
    batchDeleteUpdateZjZpEvaluationModule: "batchDeleteUpdateZjZpEvaluationModule"


};
if ($.fn.page) {
    $.fn.page.defaults = {
        pageSize: 10,
        pageBtnCount: 9,
        firstBtnText: "首页",
        lastBtnText: "尾页",
        prevBtnText: "上一页",
        nextBtnText: "下一页",
        showJump: true,
        jumpBtnText: "GO",
        showPageSizes: true,
        pageSizeItems: [10,50,100,500,1000],
        remote: {
            pageIndexName: "page", //请求参数，当前页数，索引从1开始
            pageSizeName: "limit", //请求参数，每页数量
            totalName: "totalNumber" //指定返回数据的总数据量的字段名
        }
    };
}
// window.apih5 = window.l = new Apih5(apiNames)
window.apih5 = window.l = new Apih5(apiNames,'http://test.apih5.com:9092/web/')
//window.apih5 = window.l = new Apih5(apiNames, 'http://weixin.fheb.cn:89/zjtz/')
// window.apih5 = window.l = new Apih5(apiNames,"http://localhost:8080/wdplus-web/")
// window.apih5 = window.l = new Apih5(apiNames,"http://weixin.fheb.cn:99/apiwoa/");

var LocalDB = function () {
    this.storage = {};
};
LocalDB.prototype.setItem = function (key,value) {
    this.storage[key] = value;
};
LocalDB.prototype.getItem = function (key) {
    var value = this.storage[key];
    return value;
};

function toNumber(v) {
    return isNaN(parseFloat(v,10))
        ? 0
        : parseInt(parseFloat(v,10).toFixed(1) * 10) / 10;
}
function toRows(row,rowspan) {
    var newRow = row;
    for (var i = 1; i < rowspan; i++) {
        newRow = newRow + "&" + (row + i);
    }
    return newRow;
}
