var apiNames = {
    getDepartment: "getDepartment", // oa部门获取
    getMember: "getMember", // oa部门人员获取
    updaloadFiles: "updaloadFiles", // 文件上传
	getZjJfApplicationForApprovalList:"getZjJfApplicationForApprovalList",//机房审批列表查询
	getJfApprovalDetail:"getJfApprovalDetail",//出入机房审批详情接口
	addZjJfApproval:"addZjJfApproval",//出入申请表新增接口
	addZjJfApply:"addZjJfApply",//出入审批表新增接口
	addApprovalMember:"addApprovalMember",//新增审批人接口
	getJfApprovalUserList:"getJfApprovalUserList",//获取机房审批人接口
	getJfApprovalDetailForPC:"getJfApprovalDetailForPC",//出入机房审批详情接口（PC端）
    getApplyUserInfo:"getApplyUserInfo",//获取登录人的相关信息（PC端）
	applyExportExcel:"applyExportExcel",//导出方法
	getLoginUser:"getLoginUser",//获取当前登录人判断是否为管理员
	
	getZjJfApprovalList:"getZjJfApprovalList",//PC没用到
	getZjJfApplicationList:"getZjJfApplicationList",//PC没用到
	updateZjJfApplyUnit:"updateZjJfApplyUnit",//PC没用到
	updateZjJfImdepartmentLeader:"updateZjJfImdepartmentLeader",//PC没用到
	updateZjJfImdepartment:"updateZjJfImdepartment",//PC没用到

}
window.lny = window.l = new Lny(apiNames,'http://localhost:8080/')
