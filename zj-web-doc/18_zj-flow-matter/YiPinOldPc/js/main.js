var apiNames = {
    getDepartment: "apiPostSysDepartmentList/1", // oa部门获取
    getMember: "apiPostMember", // oa部门人员获取
    updateFiles: "updateFiles", // 文件上传


    startFlow: "startFlow",
    submitFlow: "submitFlow",
    getSubmitFlow: "getSubmitFlow",
    getTodoList: "getTodoList",
    getHasTodoList: "getHasTodoList",
    createOpenFlow: "createOpenFlow",
    openFlow: "openFlow",
    actionFlow: "actionFlow",

    getZjFlowPersonApproveList: "getZjFlowPersonApproveList",
    getZjFlowPersonApproveDetail: "getZjFlowPersonApproveDetail",
    addZjFlowPersonApprove: "addZjFlowPersonApprove",
    updateZjFlowPersonApprove: "updateZjFlowPersonApprove",	
    batchDeleteUpdateZjFlowPersonApprove: "batchDeleteUpdateZjFlowPersonApprove",

    getZjHwSarsUserDetails:'getZjHwSarsUserDetails',
    addZjFlowPersonInfo:"addZjFlowPersonInfo",
    updateZjFlowPersonInfo:"updateZjFlowPersonInfo",
    batchDeleteUpdateZjFlowPersonInfo:"batchDeleteUpdateZjFlowPersonInfo",
	approveZjFlowPersonInfo:"approveZjFlowPersonInfo",
	getTodoListByReport:"getTodoListByReport",
	getHasTodoListByReport:"getHasTodoListByReport",
// 详情接口
    getZjWoaYipinOrderDetails: "getZjWoaYipinOrderDetails",
    updateZjWoaYipinOrder: "updateZjWoaYipinOrder",
    getZjWoaYipinInfoList:"getZjWoaYipinInfoList"



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
        pageSizeItems: [10,50,100,500,1000],
        remote: {
            pageIndexName: 'page',     //请求参数，当前页数，索引从1开始
            pageSizeName: 'limit',       //请求参数，每页数量
            totalName: 'totalNumber'              //指定返回数据的总数据量的字段名
        }
    };
}
// window.apih5 = window.l = new Apih5(apiNames,'http://weixin.fheb.cn:99/apiwoa/')
 window.apih5 = window.l = new Apih5(apiNames,'http://192.168.1.119:8080/web/')
