var apiNames = {
    getDepartment:"getDepartment", // 资产状态列表
    getMember:"getMember", // 资产状态列表
    updaloadFiles:"updaloadFiles", // 资产状态列表
	
    getAssetsStateList:"getZjAssetsStateList", // 资产状态列表
    getAssetsStateSelectList:"getZjAssetsStateSelectList", // 资产状态(下拉全部)
    addAssetsState:"addZjAssetsState", // 资产状态新增
    updateAssetsState:"updateZjAssetsState", // 资产状态修改
    batchDeleteAssetsState:"batchDeleteZjAssetsState", // 资产状态删除
	
    getBuyMannerList:"getZjBuyMannerList", // 购置方式列表
    getBuyMannerSelectAllList:"getZjBuyMannerSelectAllList", // 购置方式列表（全部）
    addBuyManner:"addZjBuyManner", // 购置方式新增
    updateBuyManner:"updateZjBuyManner", // 购置方式修改
    batchDeleteBuyManner:"batchDeleteZjBuyManner", // 购置方式删除
	
    getScrapTypeList:"getZjScrapTypeList", // 报废方式列表
    addScrapType:"addZjScrapType", // 报废方式新增
    updateScrapType:"updateZjScrapType", // 报废方式修改
    batchDeleteScrapType:"batchDeleteZjScrapType", // 报废方式删除
	getScrapTypeSelectAllList:"getZjScrapTypeSelectAllList", // 报废方式下拉菜单(全部)
	
    getMaintainStateList:"getZjRepairStateList", // 维修状态列表
    addMaintainState:"addZjRepairState", // 维修状态新增
    updateMaintainState:"updateZjRepairState", // 维修状态修改
    batchDeleteMaintainState:"batchDeleteZjRepairState", // 维修状态删除
	getMaintainStateSelectAllList:"getZjRepairStateSelectAllList", // 维修状态下拉菜单(全部)

	
    getUnitList:"getZjUnitList", // 单位编号查询
    getUnitSelectAllList : "getZjUnitSelectAllList", // 单位编号下拉菜单
    addUnit:"addZjUnit", // 单位编号新增
    updateUnit:"updateZjUnit", // 单位编号修改
    getUnitDetails:"getZjUnitDetails", // 单位编号详情查询 
    batchDeleteUnit:"batchDeleteZjUnit", // 单位编号批量删除
	
    getDepartmentList:"getZjDepartmentList", // 部门管理查询
	getDepartmentSelectAllList:"getZjDepartmentSelectAllList", // 部门管理下拉菜单
    addDepartment:"addZjDepartment", // 部门管理新增
    updateDepartment:"updateZjDepartment", // 部门管理修改
    getDepartmentDetails:"getZjDepartmentDetails", // 部门管理详情查询
    batchDeleteDepartment:"batchDeleteZjDepartment", // 部门管理批量删除

    getSupplierList:"getZjSupplierList", // 供应商管理查询
    getSupplierSelectAllList:"getZjSupplierSelectAllList", // 供应商管理查询（全部）
    addSupplier:"addZjSupplier", // 供应商管理新增
    updateSupplier:"updateZjSupplier", // 供应商管理修改
    getSupplierDetails:"getZjSupplierDetails", // 供应商详情查询
    batchDeleteSupplier:"batchDeleteZjSupplier", // 供应商批量删除	
		
	getAssetsBrandList:"getZjBrandList", // 品牌列表
    addBrand:"addZjBrand", // 品牌增加
    updateAssetsBrand:"updateZjBrand", // 品牌修改
    batchDeleteAssetsBrand:"batchDeleteZjBrand", // 品牌批量删除
    getAssetsBrandSelectAllList:"getZjBrandSelectAllList", // 品牌下拉查询
	
	addCzl:"addSalvage", // 残值率新增
    updateCzl:"updateSalvage", // 残值率修改
    getCzlList:"getSalvageList", // 残值率查列表
    getCzlSelectAllList:"getSalvageSelectAllList", // 残值率下拉菜单
    batchDeleteCzl:"batchDeleteSalvage", // 残值率批量删除
		
    getAssetsTypeList:"getZjAssetsTypeList", // 资产类型（大类）查询
    addAssetsType:"addZjAssetsType", // 资产类型（大类）新增
    updateAssetsType:"updateZjAssetsType", // 资产类型（大类）修改
    batchDeleteAssetsType:"batchDeleteZjAssetsType", // 资产类型（大类）批量删除
    getAssetsTypeXlList:"getZjAssetsTypeXlList", // 资产类型（小类）查询
    addAssetsTypeXl:"addZjAssetsTypeXl", // 资产类型（小类）新增
    updateAssetsTypeXl:"updateZjAssetsTypeXl", // 资产类型（小类）修改
    getAssetsTypeTreeList:"getZjAssetsTypeTreeList", // 资产类型大类集合及小类集合
    getAssetsTypeTreeAllList:"getZjAssetsTypeTreeAllList", // 资产类型大类集合及小类集合（含全部）
    getAssetsTypeDLAndXL:"getZjAssetsTypeDLAndXL", // 资产类型大类详情及小类集合
    getAssetsTypeDetails:"getZjAssetsTypeDetails", // 资产类型详情查询	
	
    getAssetsScrapList:"getZjScrapList", // 资产报废管理列表查询
    updateAssetsScrap:"updateZjScrap", // 资产报废管理列表修改
    revokeAssetsScrap:"revokeZjScrap", // 资产报废管理列表撤销
	scrapListExportExcel:"zjScrapListExportExcel", // 资产报废批量导出Excel
	getAssetsScrapDetails:"getZjScrapDetails", // 资产报废详情（微信端）
	
    getRepairList:"getZjRepairList", // 资产维修列表查询
    addRepair:"addZjRepair", // 资产维修列表新增
    updateRepair:"updateZjRepair", // 资产维修列表修改
	updateAssetsRepairState:"updateZjZcRepairState", // 资产维修状态修改
	getWeChatRepairList:"getWeChatZjRepairList", // 资产维修查询（微信端）
	getAssetsRepairDetails:"getZjRepairDetails", // 资产维修详情（微信端）
   
    getDiscountList:"getZjDiscountList", // 资产折旧列表查询
    discountExportExcel:"zjDiscountExportExcel", // 资产折旧导出
    getWeChatDiscountDetails:"getWeChatZjDiscountDetails", // 资产折旧详情（微信端）
	
	getCheckList:"getZjCheckList", // 资产验收单列表列表查询
    addCheck:"addZjCheck", // 资产验收单新增
    updateCheck:"updateZjCheck", // 资产验收单修改
    batchDeleteCheck:"batchDeleteZjCheck", // 资产验收单删除
    getCheckSelectAllList:"getZjCheckSelectAllList", // 验收状态下拉（全部）
    getWeChatCheckList:"getWeChatZjCheckList", // 资产验收列表（微信端）
    getWeChatCheckDetails:"getWeChatZjCheckDetails", // 资产验收详情（微信端）
		
    getInventoryList:"getZjInventoryList", // 资产盘点列表查询
    addInventory:"addZjInventory", // 资产盘点列表新增
    updateInventory:"updateZjInventory", // 资产盘点列表修改
    batchDeletInventory:"batchDeletZjInventory", // 资产盘点列表删除
    getInventoryDetails:"getZjInventoryDetails", // 资产盘点详情
    updateInventoryState:"updateZjInventoryState", // 资产盘点列表处理-资产归位
    updateAssetDetails:"updateZjAssetDetails", // 资产盘点列表处理-更新资产管理表
    inventoryExportExcel:"zjInventoryExportExcel", // 资产盘点导出
	getCopeStateSelectAllList:"getZjCopeStateSelectAllList", // 处理状态下拉菜单(全部)
    getInventorySelectAllList:"getZjInventorySelectAllList", // 盘点下拉    
    getWeChatInventoryList:"getWeChatZjInventoryList", // 资产盘点列表（微信端）
    inventoryNotice:"zjInventoryNotice", // 盘点通知
	getinventoryTaskList:"getZjInventoryTaskList",//获取盘点任务列表
	addInventoryTask:"addZjInventoryTask",//新增盘点任务
    updateInventoryTask:"updateZjInventoryTask",//修改盘点任务
	batchAddInventoryHaveTitle:"batchAddZjInventoryHaveTitle",//批量盘点新增（有标题）
	addInventoryHaveTitle:"addZjInventoryHaveTitle",//盘点新增（有标题）
	batchAddInventory:"batchAddZjInventory", // 资产批量盘点新增
	batchDeleteInventoryTask:"batchDeleteZjInventoryTask",//批量删除盘点任务
	batchAddAssetsInventoryTask:"batchAddZjAssetsInventoryTask",//批量添加盘点任务资产
    batchDeleteAssetsInventoryTask:"batchDeleteZjAssetsInventoryTask",//批量移除盘点任务资产
	
    getAssetsList:"getZjAssetsList", // 资产管理列表查询
    addAssets:"addZjAssets", // 资产管理列表增加
    updateAssets:"updateZjAssets", // 资产管理列表修改
    getAssetsDetails:"getZjAssetsPcDetails", // 资产管理列表详情
    batchDeleteAssets:"batchDeleteZjAssets", // 资产管理列表批量刪除
    batchInputAssets:"batchInputZjAssets", // 资产管理导入
    assetsExportExcel:"zjAssetsExportExcel", // 资产管理导出
	getAssetsNoInventoryTaskList:"getZjAssetsNoInventoryTaskList",//获取没有盘点任务的资产列表
	getAssetsInventoryTaskList:"getZjAssetsInventoryTaskList",//获取有盘点任务的资产列表
	
    getTwoDimensionCodeList:"getZjQrcodeList", // 资产二维码管理列表查询
	batchExportZjZcQrcodeVertical:"batchExportZjZcQrcodeVertical",
	batchExportZjZcQrcodeTransverse:"batchExportZjZcQrcodeTransverse",
  
    getCompanyPermissionList:"getZjCompanyPermissionList", // 权限管理查询
    addCompanyPermission:"addZjCompanyPermission", // 权限管理新增
    updateCompanyPermission:"updateZjCompanyPermission", // 权限管理修改
    batchDeleteCompanyPermission:"batchDeleteZjCompanyPermission", // 权限管理批量删除
    addPermissionAssets:"addZjPermissionAssets",//增加资产  --权限页
    getPermissionAssetsList:"getZjPermissionAssetsList",//获取资产列表  --权限页
    batchDeletePermissionAssets:"batchDeleteZjPermissionAssets",//删除资产 --权限页
    addOaGly:"addZjOaGly",//增加用户   --权限页
    getOaGlyList:"getZjOaGlyList",//获取用户  --权限页
  
    getAssetComparisonList:"getZjAssetComparisonList", // 资产对比
    batchRepair:"ZjAssetComparisonBatchRepair", // 资产对比修复
	
    getLcAsstesList:"getZjLcAsstesList", // 浪潮管理
    batchInputLcAssets:"batchInputZjLcAssets", // 浪潮管理导入
	

    getHistoryState:"getZjHistoryState", // 资产历史统计
    getInventoryHistoryDetails:"getZjInventoryDetails", // 获取盘点历史详情
    getScrapHistoryDetails:"getZjScrapFlowDetail", // 获取报废历史详情
    getDiscountHistoryDetails:"getWeChatZjDiscountDetails", // 获取折旧历史详情
    getRepairHistoryDetails:"getZjRepairDetails", // 获取维修历史详情
	
	
	batchInputPlace:"batchInputPlace",//存放地点导入
	
	getPlaceIList: "getPlaceIList", // 存放地点（一级）查询
    addPlaceI: "addPlaceI", // 存放地点（一级）新增
    updatePlaceI: "updatePlaceI", // 存放地点（一级）修改
    deletePlaceI: "deletePlaceI", // 存放地点（一级）删除
    batchDeletePlaceI: "batchDeletePlaceI", // 存放地点（一级）批量删除
    getPlaceTList: "getPlaceTList", // 存放地点（二级）查询
    addPlaceT: "addPlaceT", // 存放地点（二级）新增
    updatePlaceT: "updatePlaceT", // 存放地点（二级）修改
    deletePlaceT: "deletePlaceT", // 存放地点（二级）删除
    batchDeletePlaceT: "batchDeletePlaceT", // 存放地点（二级）批量删除
    getAssetsPlaceList: "getAssetsPlaceList", // 获取一级存放地点集合及二级存放地点集合
    getAssetsPlaceAllList: "getAssetsPlaceAllList", // 获取一级存放地点集合及二级存放地点集合（全部）
    getAssetsPlace: "getAssetsPlace", // 获得一级存放地点详情 及二级存放地点集合
    getPlaceDetails: "getPlaceDetails", // 存放地点详情查询 
    batchStartPlace: "batchStartPlace", // 存放地点批量启用
    batchStopPlace: "batchStopPlace", // 存放地点批量停用
	
    getZjZcSupplyRangeList:"getZjZcSupplyRangeList",//供应范围查询
	addZjZcSupplyRange:"addZjZcSupplyRange",//供应范围新增
	updateZjZcSupplyRange:"updateZjZcSupplyRange",//供应范围修改
	batchDeleteZjZcSupplyRange:"batchDeleteZjZcSupplyRange",//供应范围删除

	getZjSupplierAllList:"getZjSupplierAllList",//供应商下拉菜单
	
	modifyZjStateByTask:"modifyZjStateByTask",//定时修改状态	
    discountZjAlgorithmByTask:"discountZjAlgorithmByTask",//定时触发折旧算法
    
    getZjSupplierSelectAllList:'getZjSupplierSelectAllList',
    
    getAddressCodeList:'getAddressCodeList',
    getZjZcSubassetList:'getZjZcSubassetList',
    addZjZcSubasset:'addZjZcSubasset',
    updateZjZcSubasset:'updateZjZcSubasset',
	batchDeleteUpdateZjZcSubasset:"batchDeleteUpdateZjZcSubasset",
	//0717
	getZjZcDurableYearList:"batchInputZjAssets",
	addZjZcDurableYear:"addZjZcDurableYear",
	
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
window.lny = window.l = new Lny(apiNames,'http://192.168.1.223:8080/web/')
// window.lny = window.l = new Lny(apiNames,'http://weixin.fheb.cn:99/apiwoa/')
//window.lny = window.l = new Lny(apiNames,'http://wechat.zjyjhw.com:89/haiwei/')