var apiNames = {
    getDepartment: "getDepartment", // 资产状态列表
    getMember: "getMember", // 资产状态列表
    updaloadFiles: "updaloadFiles", // 资产状态列表
	getZjCjCarRemindList:"getZjCjCarRemindList",//
	addZjCjCarRemind:"addZjCjCarRemind",
	updateZjCjCarRemind:"updateZjCjCarRemind",
	batchDeleteZjCjCarRemind:"batchDeleteZjCjCarRemind",
	getDetailList:"getDetailList",
	updateZjCjApplicationForApproval:"updateZjCjApplicationForApproval",//车检、保险审批
	addZjCjApplicationForApproval:"addZjCjApplicationForApproval",//车检、保险申请
	getApplyDetail:"getApplyDetail",//获取申请详情
	getCjLoginUser:"getCjLoginUser",//获取登录人是否是管理员
}

window.lny = window.l = new Lny(apiNames,'http://localhost:8080/')
