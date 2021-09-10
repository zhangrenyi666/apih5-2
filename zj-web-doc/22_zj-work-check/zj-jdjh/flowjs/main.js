var apiNames = {
    getDepartment: "getDepartment", // 资产状态列表
    getMember: "getMember", // 资产状态列表
    updaloadFiles: "updaloadFiles", // 资产状态列表
	 
   
	
	
	
	getZjLhddJointSupApplyList:"getZjLhddJointSupApplyList",//查列表
	addZjLhddJointSupApply:"addZjLhddJointSupApply",//新增
	updateZjLhddJointSupApply:"updateZjLhddJointSupApply",//更新
	getZjLhddJointSupApplyDetailsByWorkId:"getZjLhddJointSupApplyDetailsByWorkId",//详情
	

	

	
	
	
    startFlow: "startFlow",
    submitFlow: "submitFlow",
    getSubmitFlow: "getSubmitFlow",
    getTodoList: "getTodoList",
    getHasTodoList: "getHasTodoList",
    createOpenFlow:"createOpenFlow",
    openFlow: "openFlow",
    actionFlow: "actionFlow"
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
window.lny = window.l = new Lny(apiNames,'http://localhost:8080/web/')