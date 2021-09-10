var apiNames = {
    getDepartment: "apiPostSysDepartmentList/1", // oa部门获取
    getMember: "apiPostMember", // oa部门人员获取
    updateFiles: "updateFiles", // 文件上传
	
	getZjHwModuleList:"getZjHwModuleList",//查模块列表
	addZjHwModule:"addZjHwModule",//新增模块
	updateZjHwModule:"updateZjHwModule",//修改模块
	batchDeleteUpdateZjHwModule:"batchDeleteUpdateZjHwModule",//批量删除模块
	
	getZjHwModuleDetailList:"getZjHwModuleDetailList",//子页面列表查询
	addZjHwModuleDetail:"addZjHwModuleDetail",//新增子
	getZjHwModuleDetailByModuleId:"getZjHwModuleDetailByModuleId",//查询子详情
	
   
	getAllList:"getAllList"//3级联动
}
if($.fn.page){
	        $.fn.page.defaults = {
            pageSize: 1000,
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
window.lny = window.l = new Lny(apiNames,'http://192.168.1.223:8080/web/')
//window.lny = window.l = new Lny(apiNames,'http://weixin.fheb.cn:8080/woa/')
