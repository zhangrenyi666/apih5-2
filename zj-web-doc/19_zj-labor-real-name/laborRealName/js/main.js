var apiNames = {
    getDepartment: "getDepartment", // oa部门获取
    getMember: "getMember", // oa部门人员获取
    updateFiles: "updateFiles", // 文件上传
	
	
	
	getZjLaborPerInfoBaseList:"getZjLaborPerInfoBaseList",//查询人员信息列表
	addZjLaborPerInfoBase:"addZjLaborPerInfoBase",//新增人员信息
	updateZjLaborPerInfoBase:"updateZjLaborPerInfoBase",//修改人员信息
	batchDeleteUpdateZjLaborPerInfoBase:"batchDeleteUpdateZjLaborPerInfoBase",//批量删除人员信息
	getZjLaborPerInfoBaseDetail:"getZjLaborPerInfoBaseDetail",//查看人员信息详情
	
	
	getZjLaborSafeEduCaseList:"getZjLaborSafeEduCaseList",//查询安全教育情况列表
	addZjLaborSafeEduCase:"addZjLaborSafeEduCase",//新增安全教育情况
	updateZjLaborSafeEduCase:"updateZjLaborSafeEduCase",//修改安全教育情况
	batchDeleteUpdateZjLaborSafeEduCase:"batchDeleteUpdateZjLaborSafeEduCase",//批量删除安全教育情况
	
	getZjLaborSafeTechnicalDisclosureList:"getZjLaborSafeTechnicalDisclosureList",//查询安全技术交底列表
	addZjLaborSafeTechnicalDisclosure:"addZjLaborSafeTechnicalDisclosure",//新增安全技术交底
	updateZjLaborSafeTechnicalDisclosure:"updateZjLaborSafeTechnicalDisclosure",//修改安全技术交底
	batchDeleteUpdateZjLaborSafeTechnicalDisclosure:"batchDeleteUpdateZjLaborSafeTechnicalDisclosure",//批量删除安全教育交底
	
	getZjLaborEmergencyDrillList:"getZjLaborEmergencyDrillList",//查询应急演练列表
	addZjLaborEmergencyDrill:"addZjLaborEmergencyDrill",//新增应急演练
	updateZjLaborEmergencyDrill:"updateZjLaborEmergencyDrill",//修改应急演练
	batchDeleteUpdateZjLaborEmergencyDrill:"batchDeleteUpdateZjLaborEmergencyDrill",//批量删除应急演练
	
	
	
	
	getZjLaborNativePlaceList:"getZjLaborNativePlaceList",
	addZjLaborNativePlace:"addZjLaborNativePlace",
	updateZjLaborNativePlace:"updateZjLaborNativePlace",
	getZjLaborNativePlaceListCity:"getZjLaborNativePlaceListCity",
	addZjLaborNativePlaceCity:"addZjLaborNativePlaceCity",
	updateZjLaborNativePlaceCity:"updateZjLaborNativePlaceCity",
	batchDeleteUpdateZjLaborNativePlace:"batchDeleteUpdateZjLaborNativePlace",
	getNativePlaceSelectAllList:"getNativePlaceSelectAllList",
	
	
	
    getAgeList:"getAgeList", //查列表==年龄
    addPerAge:"addPerAge", //新增==年龄
    updatePerAge:"updatePerAge", //修改==年龄
    batchDeleteUpdatePerAge:"batchDeleteUpdatePerAge", //批量删除==年龄
	getPerAgeSelectAllList:"getPerAgeSelectAllList",//状态下拉菜单==年龄
	
	getWorkingYearList:"getWorkingYearList",
	addWorkingYear:"addWorkingYear",
	updateWorkingYear:"updateWorkingYear",
	batchDeleteUpdateWorkingYear:"batchDeleteUpdateWorkingYear",
	getWorkingYearSelectAllList:"getWorkingYearSelectAllList",
	
	getSafetyWorkYearList:"getSafetyWorkYearList",
	addSafetyWorkYear:"addSafetyWorkYear",
	updateSafetyWorkYear:"updateSafetyWorkYear",
	batchDeleteUpdateSafetyWorkYear:"batchDeleteUpdateSafetyWorkYear",
	getSafetyWorkYearSelectAllList:"getSafetyWorkYearSelectAllList",
	
	
}
 
window.lny = window.l = new Lny(apiNames,'http://192.168.1.103:8080/web/')