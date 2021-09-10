var apiNames = {
    getDepartment: "getDepartment", // oa部门获取
    getMember: "getMember", // oa部门人员获取
    updateFiles: "updateFiles", // 文件上传
    getZjWageList: "getZjWageList", // 工资全部数据列表
    batchInputWage: "batchInputWage", // 工资批量导入
    getZjWageDetail: "getZjWageDetail", // 微信端查看工资详情
    deleteZjWage: "deleteZjWage", // 工资阅后即焚
	sendWage:"sendWage",//工资发消息
	getSendNum:"getSendNum",//工资发送的人数
	getPCZjWageDetail:"getPCZjWageDetail", //PC端查看工资详情list
	getZjPerformList:"getZjPerformList",//绩效奖全部数据列表
	batchInputPerform:"batchInputPerform",///绩效奖批量导入
	getSendPerformNum:"getSendPerformNum",//绩效奖发送人数
	sendPerform:"sendPerform",//绩效奖发消息
	getPCZjPerformDetail:"getPCZjPerformDetail",//PC端查看绩效详情list
	deleteZjPerform:"deleteZjPerform",//绩效奖阅后即焚
	zjPerform:"zjPerform",//微信端查看绩效详情
	
	//20190603
	getZjGzdYearList:"getZjGzdYearList",
	addZjGzdYear:"addZjGzdYear",
	batchDeleteUpdateZjGzdYear:"batchDeleteUpdateZjGzdYear",//
	getZjGzdAnnualIncomeList:"getZjGzdAnnualIncomeList",
	batchInputZjGzdAnnualIncome:"batchInputZjGzdAnnualIncome",
	getZjGzdAnnualIncomeSendNum:"getZjGzdAnnualIncomeSendNum",
	sendZjGzdAnnualIncome:"sendZjGzdAnnualIncome",
	getZjGzdAnnualIncomeDetailList:"getZjGzdAnnualIncomeDetailList",
	zjGzdAnnualIncomeOperate:"zjGzdAnnualIncomeOperate",
	getZjGzdAnnualIncomeDetail:"getZjGzdAnnualIncomeDetail",
	zjGzdAnnualIncomeExportExcel:"zjGzdAnnualIncomeExportExcel",
	//年金
	getZjAnnuityYearList:"getZjAnnuityYearList",
	addZjAnnuityYear:"addZjAnnuityYear",
	batchDeleteUpdateZjAnnuityYear:"batchDeleteUpdateZjAnnuityYear",
	getZjAnnuityPersonList:"getZjAnnuityPersonList",
	addZjAnnuityPerson:"addZjAnnuityPerson",
	sendZjAnnuityPerson:"sendZjAnnuityPerson",
	
	
	
	
	
}
//window.lny = window.l = new Lny(apiNames,'http://weixin.fheb.cn:89/zjtz/')
//window.lny = window.l = new Lny(apiNames,'http://192.168.1.223:8080/web/')
window.lny = window.l = new Lny(apiNames,'http://192.168.1.223:99/web/')

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


