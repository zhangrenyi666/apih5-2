var apiNames = {
   
    getAssetsDetails:"getAssetsDetails",//资产详情页
    addCheck:"addCheck",//验收新增
    getScrapTypeSelectAllList:'getScrapTypeSelectAllList',//资产下拉选择
   

    getInventoryList:"getInventoryList",//盘点列表
 

    
    getAssetsList:"getAssetsList",//资产管理列表
	
	
	
	getAssetsStateSelectList:"getAssetsStateSelectList",//下拉
	getDepartmentSelectAllList:'getDepartmentSelectAllList',//下拉
	getInventorySelectAllList:"getInventorySelectAllList",//下拉
	getInventoryList:"getInventoryList",//盘点列表
	getWeChatCheckList:"getWeChatCheckList",//资产验收
	 getHistoryState:'getHistoryState',//资产历史
	 getAssetsScrapDetails:'getAssetsScrapDetails',  
    addAssetsScrap:'addAssetsScrap',	 
	updateAssetsScrap:'updateAssetsScrap',
	revokeAssetsScrap:'revokeAssetsScrap',
	getWeChatScrapList:'getWeChatScrapList',
	getInventoryDetails:'getInventoryDetails',
	updateInventory:'updateInventory',
	addInventoryHaveTitle:'addInventoryHaveTitle',
	addInventory:'addInventory',
	getWeChatInventoryList:'getWeChatInventoryList',
	getMaintainStateSelectAllList:'getMaintainStateSelectAllList',
	getAssetsRepairDetails:'getAssetsRepairDetails',
	addRepair:'addRepair',
	updateRepair:'updateRepair',
	getWeChatRepairList:'getWeChatRepairList',
	
	getWeChatDiscountDetails:'getWeChatDiscountDetails',	
	getWeChatDiscountList:'getWeChatDiscountList',
	

	
	addCheck:"addCheck",//验收新增
	updateCheck:'updateCheck', 
	getWeChatCheckDetails:'getWeChatCheckDetails',
	
	 getWeChatAssetsDetails:'getWeChatAssetsDetails',
	 getBuyMannerSelectAllList:'getBuyMannerSelectAllList',
    getUnitSelectAllList:'getUnitSelectAllList',
    getSupplierSelectAllList:'getSupplierSelectAllList',
	  getScrapTypeSelectAllList:'getScrapTypeSelectAllList',//资产下拉选择
	
	
	
	
	
	
	
	
	
	
	
}
window.lny = window.l = new Lny(apiNames,window.api)
//window.lny = window.l = new Lny(apiNames, "http://192.168.47.1:8080/wdplus-web/")


