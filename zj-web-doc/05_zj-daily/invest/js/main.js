var apiNames = {
    getDepartment: "getDepartment",
    getMember: "getMember",
    uploadFiles : "uploadFiles",
    getCompanyDetails: "getCompanyDetails",
    getInvestCompanyList: "getInvestCompanyList",
    addInvestCompany: "addInvestCompany",
    batchDeleteInvestCompany: "batchDeleteInvestCompany",
    getInvestCompanyInfo: "getInvestCompanyInfo",

    getTzLandList: "getTzLandList",
	getTzLandList2: "getTzLandList2",
    addTzLand: "addTzLand",
    copyAddTzLand: "copyAddTzLand",
    batchDeleteTzLand: "batchDeleteTzLand",
    updateTzLand: "updateTzLand",
    getTzLandDetails: "getTzLandDetails",

    getTzDesignChartList: "getTzDesignChartList",
    addTzDesignChart: "addTzDesignChart",
    copyAddTzDesignChart: "copyAddTzDesignChart",
    batchDeleteTzDesignChart: "batchDeleteTzDesignChart",
    updateTzDesignChart: "updateTzDesignChart",
    getTzDesignChartDetails: "getDesignChartDetails",

    getTzProjectFundsList: "getTzProjectFundsList",
    addTzProjectFunds: "addTzProjectFunds",
    copyAddTzProjectFunds: "copyAddTzProjectFunds",
    batchDeleteTzProjectFunds: "batchDeleteTzProjectFunds",
    updateTzProjectFunds: "updateTzProjectFunds",
    getTzProjectFundsDetails: "getProjectFundsDetails",

    getTzAssessmentList: "getTzAssessmentList",
    addTzAssessment: "addTzAssessment",
    copyAddTzAssessment: "copyAddTzAssessment",
    batchDeleteTzAssessment: "batchDeleteTzAssessment",
    updateTzAssessment: "updateTzAssessment",
    getTzAssessmentDetails: "getAssessmentDetails",

    getTzGraphicProgressList: "getTzGraphicProgressList",
    addTzGraphicProgress: "addTzGraphicProgress",
    copyAddTzGraphicProgress: "copyAddTzGraphicProgress",
    batchDeleteTzGraphicProgress: "batchDeleteTzGraphicProgress",
    updateTzGraphicProgress: "updateTzGraphicProgress",
    getTzGraphicProgressDetails: "getGraphicProgressDetails",

    listDailyDaysNum: "listDailyDaysNum",


    getDepartment: "getDepartment", // oa部门获取
    getMember: "getMember", // oa部门人员获取
    updateFiles: "updateFiles", // 文件上传
    getInvestCompanyList: "getInvestCompanyList", // 投资公司管理
    addInvestCompany: "addInvestCompany", // 投资公司管理新增
    updateInvestCompany: "updateInvestCompany", // 投资公司管理修改
	updateDailyCompany: "updateDailyCompany",//填报时修改公司
    batchDeleteInvestCompany: "batchDeleteInvestCompany", // 投资公司管理批量删除
    getInvestDailySheetList: "getInvestDailySheetList", // 周报填报
    addInvestDailySheet: "addInvestDailySheet", // 周报填报
    updateInvestDailySheet: "updateInvestDailySheet", // 周报填报修改
    batchDelteInvestDailySheet: "batchDelteInvestDailySheet", // 周报填报删除
	getInvestCompanyInfo: "getInvestCompanyInfo",
	getTotalDailyDaysNumList: "getTotalDailyDaysNumList",
	sendDailyMsg: "sendDailyMsg",
	sendDailyMsgByPc : "sendDailyMsgByPc",
	reviewTzLand: "reviewTzLand",
    restReviewTzLand: "restReviewTzLand",
	getTzAssessmentList2: "getTzAssessmentList2",
	getTzDesignChartList2 : "getTzDesignChartList2",
	getTzGraphicProgressList2 : "getTzGraphicProgressList2",
	getTzProjectFundsList2: "getTzProjectFundsList2",
	restReviewTzDesignChart: "restReviewTzDesignChart",
	reviewTzDesignChart: "reviewTzDesignChart",	
	restReviewTzAssessment: "restReviewTzAssessment",
	reviewTzAssessment: "reviewTzAssessment",	
	restReviewTzGraphicProgress: "restReviewTzGraphicProgress",
	reviewTzGraphicProgress: "reviewTzGraphicProgress",	
	restReviewTzProjectFunds: "restReviewTzProjectFunds",
	reviewTzProjectFunds: "reviewTzProjectFunds",	
    getMessageMemberList:"getMessageMemberList",
	getMessageMemberListByPc:"getMessageMemberListByPc",
	getReviewList : "getReviewList",
	updateReview : "updateReview",
	getRestReviewList : "getRestReviewList",
	restUpdateReview : "restUpdateReview",
	getTzDictionaryList : "getTzDictionaryList",
	addTzDictionary : "addTzDictionary",
	updateTzDictionary : "updateTzDictionary",
    batchDeleteTzDictionary : "batchDeleteTzDictionary",
    getSendRecordList : "getSendRecordList", //周报发送情况的集合
    getReadRecordList : "getReadRecordList", //周报阅读情况的集合
    batchDeleteTzLogRecord : "batchDeleteTzLogRecord",//批量删除日志记录
    updateTzLogRecord : "updateTzLogRecord", //修改日志记录
    addTzLogRecord : "addTzLogRecord" //新增日志记录
}
// window.lny = window.l = new Lny(apiNames)
// window.lny = window.l = new Lny(apiNames, "http://haiwei-oa.fheb.cn:89/haiwei/")
// window.lny = window.l = new Lny(apiNames, "http://wyongan.xicp.net/wdplus-web/")
// window.lny = window.l = new Lny(apiNames, "http://qyh.apih5.com/")
window.lny = window.l = new Lny(apiNames, "http://localhost:8080/")

function getKeyName(_urlkey, type) {
    var _keyName = "";

    if (type == "Id") {
        switch (_urlkey) {
            case "TzLand":
                _keyName = "landId"
                break;
            case "TzDesignChart":
                _keyName = "designChartId"
                break;
            case "TzProjectFunds":
                _keyName = "projectFundsId"
                break;
            case "TzAssessment":
                _keyName = "assessmentId"
                break;
            case "TzGraphicProgress":
                _keyName = "progressId"
                break;
        }
    } else if (type == "List") {
        switch (_urlkey) {
            case "TzLand":
                _keyName = "landList"
                break;
            case "TzDesignChart":
                _keyName = "tzDesignChartList"
                break;
            case "TzProjectFunds":
                _keyName = "tzProjectFundsList"
                break;
            case "TzAssessment":
                _keyName = "tzAssessmentList"
                break;
            case "TzGraphicProgress":
                _keyName = "tzGraphicProgressList"
                break;
        }
    }
    return _keyName
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
        pageSizeItems: [10, 20, 40],
        remote: {
            pageIndexName: 'page',     //请求参数，当前页数，索引从1开始
            pageSizeName: 'limit',       //请求参数，每页数量
            totalName: 'totalNumber'              //指定返回数据的总数据量的字段名
        }
    };
}