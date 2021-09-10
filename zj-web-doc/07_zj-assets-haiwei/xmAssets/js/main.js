var apiNames = {
    getDepartment: "apiPostSysDepartmentList/1", // oa部门获取
    getMember: "apiPostMember", // oa部门人员获取
    updateFiles: "updateFiles", // 文件上传

    //资产管理人员
    getZjHwzcGlyList:"getZjHwzcGlyList",
    getZjHwzcGlyDetails:"getZjHwzcGlyDetails",
    addZjHwzcGly:"addZjHwzcGly",
    updateZjHwzcGly:"updateZjHwzcGly",
    batchDeleteUpdateZjHwzcGly:"batchDeleteUpdateZjHwzcGly",
    //
    getZjHwzcZcglList:"getZjHwzcZcglList",
    getZjHwzcZcglListByUserKey:"getZjHwzcZcglListByUserKey",
    getZjHwzcZcglDetails:"getZjHwzcZcglDetails",
    getZjHwzcZcglNeedApproveList:"getZjHwzcZcglNeedApproveList",
    updateZjHwzcZcglForCWBH:"updateZjHwzcZcglForCWBH",
    //
    
    getZjHwzcZcbfDetails:"getZjHwzcZcbfDetails",





    getAssetsStateList: "getAssetsStateList", // 资产状态列表
    getAssetsStateSelectList: "getAssetsStateSelectList", // 资产状态(下拉全部)
    addAssetsState: "addAssetsState", // 资产状态新增
    updateAssetsState: "updateAssetsState", // 资产状态修改
    batchDeleteAssetsState: "batchDeleteAssetsState", // 资产状态删除
    getBuyMannerList: "getBuyMannerList", // 购置方式列表
    getBuyMannerSelectAllList: "getBuyMannerSelectAllList", // 购置方式列表（全部）
    addBuyManner: "addBuyManner", // 购置方式新增
    updateBuyManner: "updateBuyManner", // 购置方式修改
    batchDeleteBuyManner: "batchDeleteBuyManner", // 购置方式删除
    getScrapTypeList: "getScrapTypeList", // 报废方式列表
    addScrapType: "addScrapType", // 报废方式新增
    updateScrapType: "updateScrapType", // 报废方式修改
    batchDeleteScrapType: "batchDeleteScrapType", // 报废方式删除
    getMaintainStateList: "getMaintainStateList", // 维修状态列表
    addMaintainState: "addMaintainState", // 维修状态新增
    updateMaintainState: "updateMaintainState", // 维修状态修改
    batchDeleteMaintainState: "batchDeleteMaintainState", // 维修状态删除
    getUnitList: "getUnitList", // 单位编号查询
    getUnitSelectAllList: "getUnitSelectAllList", // 单位编号下拉菜单
    addUnit: "addUnit", // 单位编号新增
    updateUnit: "updateUnit", // 单位编号修改
    deleteUnit: "deleteUnit", // 单位编号删除
    getUnitDetails: "getUnitDetails", // 单位编号详情查询 
    batchStartUnit: "batchStartUnit", // 单位编号批量启用
    batchStopUnit: "batchStopUnit", // 单位编号批量停用
    batchDeleteUnit: "batchDeleteUnit", // 单位编号批量删除
    getDepartmentList: "getzcDepartmentList", // 部门管理查询
    addDepartment: "addDepartment", // 部门管理新增
    updateDepartment: "updateDepartment", // 部门管理修改
    deleteDepartment: "deleteDepartment", // 部门管理删除
    getDepartmentDetails: "getDepartmentDetails", // 部门管理详情查询
    batchStartDepartment: "batchStartDepartment", // 部门管理批量启用
    batchStopDepartment: "batchStopDepartment", // 部门管理批量停用
    batchDeleteDepartment: "batchDeleteDepartment", // 部门管理批量删除
    getAssetsTypeList: "getAssetsTypeList", // 资产类型（大类）查询
    addAssetsType: "addAssetsType ", // 资产类型（大类）新增
    updateAssetsType: "updateAssetsType", // 资产类型（大类）修改
    deleteAssetsType: "deleteAssetsType", // 资产类型（大类）删除
    batchDeleteAssetsType: "batchDeleteAssetsType", // 资产类型（大类）批量删除
    getAssetsTypeXlList: "getAssetsTypeXlList", // 资产类型（小类）查询
    addAssetsTypeXl: "addAssetsTypeXl", // 资产类型（小类）新增
    updateAssetsTypeXl: "updateAssetsTypeXl", // 资产类型（小类）修改
    deleteAssetsTypeXl: "deleteAssetsTypeXl", // 资产类型（小类）删除
    batchDeleteAssetsTypeXl: "batchDeleteAssetsTypeXl", // 资产类型（小类）批量删除
    getAssetsTypeTreeList: "getAssetsTypeTreeList", // 资产类型大类集合及小类集合
    getAssetsTypeTreeAllList: "getAssetsTypeTreeAllList", // 资产类型大类集合及小类集合（含全部）
    getAssetsTypeDLAndXL: "getAssetsTypeDLAndXL", // 资产类型大类详情及小类集合
    getAssetsTypeTreeList: "getAssetsTypeTreeList", // 资产树表列表查询
    getAssetsTypeDetails: "getAssetsTypeDetails", // 资产类型详情查询
    batchStartAssetsType: "batchStartAssetsType", // 资产类型批量启用
    batchStopAssetsType: "batchStopAssetsType", // 资产类型批量停用
    batchDeleteAssetsType: "batchDeleteAssetsType", // 资产类型批量删除
    getAssetsScrapList: "getAssetsScrapList", // 资产报废管理列表查询
    addAssetsScrap: "addAssetsScrap", // 资产报废管理列表新增
    updateAssetsScrap: "updateAssetsScrap", // 资产报废管理列表修改
    revokeAssetsScrap: "revokeAssetsScrap", // 资产报废管理列表撤销
    scrapExportExcel: "scrapExportExcel", // 资产报废管理列表导出
    getRepairList: "getRepairList", // 资产维修列表查询
    addRepair: "addRepair", // 资产维修列表新增
    updateRepair: "updateRepair", // 资产维修列表修改
    deleteAssetsRepair: "deleteAssetsRepair", // 资产维修列表删除
    batchDeleteRepair: "batchDeleteRepair", // 资产维修批量删除
    updateAssetsRepairState: "updateAssetsRepairState", // 资产维修状态修改
    updateAssetsRepairState: "updateAssetsRepairState", // 资产维修导出
    getDiscountList: "getDiscountList", // 资产折旧列表查询
    addDiscount: "addDiscount", // 资产折旧列表新增
    updateDiscount: "updateDiscount", // 资产折旧列表修改
    batchDeleteDiscount: "batchDeleteDiscount", // 资产折旧列表删除
    discountExportExcel: "discountExportExcel", // 资产折旧导出
    getInventoryList: "getInventoryList", // 资产盘点列表查询
    addInventory: "addInventory", // 资产盘点列表新增
    updateInventory: "updateInventory", // 资产盘点列表修改
    batchDeletInventory: "batchDeletInventory", // 资产盘点列表删除
    getInventoryDetails: "getInventoryDetails", // 资产盘点详情
    updateInventoryState: "updateInventoryState", // 资产盘点列表处理-资产归位
    updateAssetDetails: "updateAssetDetails", // 资产盘点列表处理-更新资产管理表
    inventoryExportExcel: "inventoryExportExcel", // 资产盘点导出
    getCheckList: "getCheckList", // 资产验收单列表列表查询
    addCheck: "addCheck", // 资产验收单新增
    updateCheck: "updateCheck", // 资产验收单修改
    batchDeleteCheck: "batchDeleteCheck", // 资产验收单删除
    checkExportExcel: "checkExportExcel", // 资产验收导出
    getAssetsList: "getAssetsList", // 资产管理列表查询
    addAssets: "addAssets", // 资产管理列表增加
    updateAssets: "updateAssets", // 资产管理列表修改
    getAssetsDetails: "getAssetsDetails", // 资产管理列表详情
    batchDeleteAssets: "batchDeleteAssets", // 资产管理列表批量刪除
    batchInputAssets: "batchInputAssets", // 资产管理导入
    assetsExportExcel: "assetsExportExcel", // 资产管理导出
    getTwoDimensionCodeList: "getTwoDimensionCodeList", // 资产二维码管理列表查询
    getAssetsTypeDLAndXL: "getAssetsTypeDLAndXL", // 获得资产类型大类详情及小类集合
    getAssetsTypeTreeList: "getAssetsTypeTreeList", // 获得大类集合及小类集合
    assetsStateList: "assetsStateList", // 资产状态下拉菜单
    buyMannerList: "buyMannerList", // 购置方式下拉菜单
    getScrapTypeSelectAllList: "getScrapTypeSelectAllList", // 报废方式下拉菜单(全部)
    getMaintainStateSelectAllList: "getMaintainStateSelectAllList", // 维修状态下拉菜单(全部)
    supplierList: "supplierList", // 供应商下拉菜单
    codeList: "codeList", // 编号管理下拉菜单
    getInventorySelectAllList: "getInventorySelectAllList", // 盘点下拉
    getCheckSelectAllList: "getCheckSelectAllList", // 验收状态下拉（全部）
    getCopeStateSelectAllList: "getCopeStateSelectAllList", // 处理状态下拉菜单(全部)
    getAssetComparisonList: "getAssetComparisonList", // 资产对比
    batchRepair: "batchRepair", // 资产对比修复
    getLcAsstesList: "getLcAsstesList", // 浪潮管理
    batchInputLcAssets: "batchInputLcAssets", // 浪潮管理导入
    getAsstesHistoryList: "getAsstesHistoryList", // 资产历史
    getScrapHistoryList: "getScrapHistoryList", // 资产报废历史查询
    getHistoryState: "getHistoryState", // 资产历史统计
    getAssetsScrapDetails: "getAssetsScrapDetails", // 资产报废详情（微信端）
    getAssetsRepairDetails: "getAssetsRepairDetails", // 资产维修详情（微信端）
    getWeChatInventoryList: "getWeChatInventoryList", // 资产盘点列表（微信端）
    getInventoryDetails: "getInventoryDetails", // 资产盘点详情（微信端）
    getWeChatScrapList: "getWeChatScrapList", // 资产报废查询（微信端）
    getWeChatRepairList: "getWeChatRepairList", // 资产维修查询（微信端）
    getWeChatDiscountList: "getWeChatDiscountList", // 资产折旧列表（微信端）
    getWeChatDiscountDetails: "getWeChatDiscountDetails", // 资产折旧详情（微信端）
    getWeChatCheckList: "getWeChatCheckList", // 资产验收列表（微信端）
    getWeChatCheckDetails: "getWeChatCheckDetails", // 资产验收详情（微信端）
    getInventoryHistoryDetails: "getInventoryHistoryDetails", // 获取盘点历史详情
    getScrapHistoryDetails: "getScrapHistoryDetails", // 获取报废历史详情
    getDiscountHistoryDetails: "getDiscountHistoryDetails", // 获取折旧历史详情
    getRepairHistoryDetails: "getRepairHistoryDetails", // 获取维修历史详情
    batchAddInventory: "batchAddInventory", // 资产批量盘点新增
    scrapListExportExcel: "scrapListExportExcel", // 资产报废批量导出Excel
    getDepartmentSelectAllList: "getDepartmentSelectAllList", // 部门管理下拉菜单
    getinventoryTaskList: "getinventoryTaskList",//获取盘点任务列表
    addInventoryTask: "addInventoryTask",//新增盘点任务
    updateInventoryTask: "updateInventoryTask",//修改盘点任务
    batchAddInventoryHaveTitle: "batchAddInventoryHaveTitle",//批量盘点新增（有标题）
    addInventoryHaveTitle: "addInventoryHaveTitle",//盘点新增（有标题）
    batchDeleteInventoryTask: "batchDeleteInventoryTask",//批量删除盘点任务
    getAssetsNoInventoryTaskList: "getAssetsNoInventoryTaskList",//获取没有盘点任务的资产列表
    getAssetsInventoryTaskList: "getAssetsInventoryTaskList",//获取有盘点任务的资产列表
    batchAddAssetsInventoryTask: "batchAddAssetsInventoryTask",//批量添加盘点任务资产
    batchExportQRcodeVertical: "batchExportQRcodeVertical",//批量导出二维码（纵向）
    batchExportQRcodeTransverse: "batchExportQRcodeTransverse",//批量导出二维码（横向）



    getDetailByWorkId: "getDetailByWorkId",//根据workId查询资产详情
    getInReviewAssetsList: "getInReviewAssetsList",//获取==采购审核中的资产列表数据  
    getZjHwzcAssetsNeedApproveList: "getZjHwzcAssetsNeedApproveList",//获取需要进行验收的数据(zcztdm=2)报废、调拨申请的数据(zcztdm=1)
    getScrapDealList: "getScrapDealList",//获取==需要报废处理的数据




    startFlow: "startFlow",
    submitFlow: "submitFlow",
    getSubmitFlow: "getSubmitFlow",
    getTodoList: "getTodoList",
    getHasTodoList: "getHasTodoList",
    createOpenFlow: "createOpenFlow",
    openFlow: "openFlow",
    actionFlow: "actionFlow",

    exportCropPurchaseToWord: "exportCropPurchaseToWord",//公司购置资产导出word
    exportAssetsList: "exportAssetsList",//


    //厦门资产相关接口	
    getAssetsTypeTreeAllListForFlow: "getAssetsTypeTreeAllListForFlow",
    getZjXmAssetsList: "getZjXmAssetsList",//
    getZjXmAssetsTwoDimensionCodeList: "getZjXmAssetsTwoDimensionCodeList",//
    getZjXmAssetsScrapList: "getZjXmAssetsScrapList",//
    getZjXmAssetsDiscountList: "getZjXmAssetsDiscountList",//

    getZjXmAssetsInventoryList: "getZjXmAssetsInventoryList",//
    addZjXmAssetsInventory: "addZjXmAssetsInventory",//
    getZjHwzcPermissionList: "getZjHwzcPermissionList",//
    addZjHwzcPermission: "addZjHwzcPermission",//
    batchDeleteUpdateZjHwzcPermission: "batchDeleteUpdateZjHwzcPermission",//
    getZjHwzcPermissionTypeList: "getZjHwzcPermissionTypeList",//
    addZjHwzcPermissionType: "addZjHwzcPermissionType",//
    getZjXmAssetsComparisonList: "getZjXmAssetsComparisonList",//

    getZjHwzcZcwxList: "getZjHwzcZcwxList",
    getCzlSelectAllList: "getCzlSelectAllList",

    discountAlgorithmByTask: "discountAlgorithmByTask"
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
        pageSizeItems: [10, 50, 100, 500, 1000],
        remote: {
            pageIndexName: 'page',     //请求参数，当前页数，索引从1开始
            pageSizeName: 'limit',       //请求参数，每页数量
            totalName: 'totalNumber'              //指定返回数据的总数据量的字段名
        }
    };
}
//window.lny = window.l = new Lny(apiNames,'http://weixin.fheb.cn:99/apiwoa/')
window.lny = window.l = new Lny(apiNames, 'http://192.168.1.119:8080/web/')
// window.lny = window.l = new Lny(apiNames,' http://xm-oa.fheb.cn:88/apixiamengs/')