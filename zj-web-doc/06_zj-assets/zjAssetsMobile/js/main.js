var apiNames = {
    getWeChatAssetsDetails:'getWeChatZjAssetsDetails',//资产详情
	getAssetsStateSelectList:"getZjAssetsStateSelectList", // 资产状态(下拉全部)
	getInventorySelectAllList:"getZjInventorySelectAllList", // 盘点下拉 
    getCopeStateSelectAllList:"getZjCopeStateSelectAllList", // 处理状态下拉菜单(全部)	
	getDepartmentSelectAllList:"getZjDepartmentSelectAllList", // 部门管理下拉菜单
	getBuyMannerSelectAllList:"getZjBuyMannerSelectAllList", // 购置方式列表（全部）
	getAssetsPlaceAllList: "getAssetsPlaceAllList", // 获取一级存放地点集合及二级存放地点集合（全部）
	addInventory:"addZjInventory", // 资产盘点列表新增
	getHistoryState:"getZjHistoryState", // 资产历史统计
	getWeChatInventoryList:"getWeChatZjInventoryList", // 资产盘点列表（微信端）
	getInventoryDetails:"getZjInventoryDetails", // 资产盘点详情
	updateAssetDetails:"updateZjAssetDetails", // 资产盘点列表处理-更新资产管理表
	updateInventoryState:"updateZjInventoryState", // 资产盘点列表处理-资产归位
	updateInventory:"updateZjInventory", // 资产盘点列表修改
	getMaintainStateSelectAllList:"getZjRepairStateSelectAllList", // 维修状态下拉菜单(全部)
	addRepair:"addZjRepair", // 资产维修列表新增
	updateRepair:"updateZjRepair", // 资产维修列表修改
	getWeChatRepairList:"getWeChatZjRepairList", // 资产维修查询（微信端）
	getAssetsRepairDetails:"getZjRepairDetails", // 资产维修详情（微信端）
   
	
	
	
	
	
	
	getInventoryList:"getInventoryList",//盘点列表
	getWeChatCheckList:"getWeChatCheckList",//资产验收
	getAssetsScrapDetails:'getAssetsScrapDetails',  
    addAssetsScrap:'addAssetsScrap',	 
	updateAssetsScrap:'updateAssetsScrap',
	revokeAssetsScrap:'revokeAssetsScrap',
	getWeChatScrapList:'getWeChatScrapList',
	addInventoryHaveTitle:'addInventoryHaveTitle',		
	getWeChatDiscountDetails:'getWeChatDiscountDetails',	
	getWeChatDiscountList:'getWeChatDiscountList',	
	getWeChatCheckDetails:'getWeChatCheckDetails',
    getUnitSelectAllList:'getUnitSelectAllList',
    getSupplierSelectAllList:'getSupplierSelectAllList',
    getScrapTypeSelectAllList:'getScrapTypeSelectAllList',//资产下拉选择
	

	
	
}
window.lny = window.l = new Lny(apiNames,'http://weixin.fheb.cn:99/apiwoa/')


