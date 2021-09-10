var apiNames = {
    getDepartment:"getDepartment", // 资产状态列表
    getMember:"getMember", // 资产状态列表
    updaloadFiles:"updaloadFiles", // 资产状态列表
	
	getZjHwZyResourceDepList:"getZjHwZyResourceDepList",//查询部门管理列表
	addZjHwZyResourceDep:"addZjHwZyResourceDep",//新增部门管理
	updateZjHwZyResourceDep:"updateZjHwZyResourceDep",//修改部门管理
	batchDeleteUpdateZjHwZyResourceDep:"batchDeleteUpdateZjHwZyResourceDep",//批量删除部门管理
	getZjHwZyResourceDepSelectAllList:"getZjHwZyResourceDepSelectAllList",//部门管理下拉列表选择
	
	
	getZjHwZyResourceAreaList:"getZjHwZyResourceAreaList",//1区域列表查询
	addZjHwZyResourceArea:"addZjHwZyResourceArea",//1区域新增
	updateZjHwZyResourceAreaCity:"updateZjHwZyResourceAreaCity",//1.2.3修改共用
	batchDeleteUpdateZjHwZyResourceAreaCity:"batchDeleteUpdateZjHwZyResourceAreaCity",//1.2.3批量删除共用
    getZjHwZyResourceProvinceList:"getZjHwZyResourceProvinceList",//2省查询列表
	getZjHwZyResourceCityList:"getZjHwZyResourceCityList",//3城市查询列表
	addZjHwZyResourceTwo:"addZjHwZyResourceTwo",//2.3省市新增
	getResourceAreaCityAllList:"getResourceAreaCityAllList",//3级联动
	
	
	getZjHwZyResourceClassList:"getZjHwZyResourceClassList",//大类查询
	addZjHwZyResourceClass:"addZjHwZyResourceClass",//大类新增
	updateZjHwZyResourceClass:"updateZjHwZyResourceClass",//大类修改
	batchDeleteUpdateZjHwZyResourceClass:"batchDeleteUpdateZjHwZyResourceClass",//批量删除共同
	getZjHwZyResourceClassSmallList:"getZjHwZyResourceClassSmallList",//小类查询
	addZjHwZyResourceClassSmall:"addZjHwZyResourceClassSmall",//小类新增
	
	
	
	getZjHwZyResourceCoursewareList:"getZjHwZyResourceCoursewareList",//课件列表查询
	addZjHwZyResourceCourseware:"addZjHwZyResourceCourseware",//课件新增
	updateZjHwZyResourceCourseware:"updateZjHwZyResourceCourseware",//修改课件
	batchDeleteUpdateZjHwZyResourceCourseware:"batchDeleteUpdateZjHwZyResourceCourseware",//课件批量删除
	getZjHwZyResourceCoursewareDetail:"getZjHwZyResourceCoursewareDetail",//课件查详情
	batchImportZjHwZyResourceCourseware:"batchImportZjHwZyResourceCourseware",//课件批量导入
	
	
	getZjHwZyResourceInventoryList:"getZjHwZyResourceInventoryList",//清单列表查询
	addZjHwZyResourceInventory:"addZjHwZyResourceInventory",//新增清单
	updateZjHwZyResourceInventory:"updateZjHwZyResourceInventory",//修改清单列表
	batchDeleteUpdateZjHwZyResourceInventory:"batchDeleteUpdateZjHwZyResourceInventory",//批量删除清单	
	getZjHwZyResourceInventoryDetail:"getZjHwZyResourceInventoryDetail",//清单详情查询
	batchImportZjHwZyResourceInventory:"batchImportZjHwZyResourceInventory",//清单批量导入
	
	getZjHwZyResourceMixProportionList:"getZjHwZyResourceMixProportionList",//配合比查询列表
	addZjHwZyResourceMixProportion:"addZjHwZyResourceMixProportion",//新增配合比
	updateZjHwZyResourceMixProportion:"updateZjHwZyResourceMixProportion",//修改配合比
	batchDeleteUpdateZjHwZyResourceMixProportion:"batchDeleteUpdateZjHwZyResourceMixProportion",//批量删除配合比
	getZjHwZyResourceMixProportionDetail:"getZjHwZyResourceMixProportionDetail",//配合比查详情
	batchImportZjHwZyResourceMixProportion:"batchImportZjHwZyResourceMixProportion",//配合比批量导入
	
	
	getZjHwZyResourcePersonnelList:"getZjHwZyResourcePersonnelList",//人员库查列表
	addZjHwZyResourcePersonnel:"addZjHwZyResourcePersonnel",//新增人员库
	updateZjHwZyResourcePersonnel:"updateZjHwZyResourcePersonnel",//修改人员库
	batchDeleteUpdateZjHwZyResourcePersonnel:"batchDeleteUpdateZjHwZyResourcePersonnel",//批量删除人员库
	getZjHwZyResourcePersonnelDetail:"getZjHwZyResourcePersonnelDetail",//人员库查详情
	batchImportZjHwZyResourcePersonnel:"batchImportZjHwZyResourcePersonnel",//人员批量导入
	
	
	
	getZjHwZyResourceOrganizationList:"getZjHwZyResourceOrganizationList",//机构列表查询
	addZjHwZyResourceOrganization:"addZjHwZyResourceOrganization",//机构新增
	updateZjHwZyResourceOrganization:"updateZjHwZyResourceOrganization",//机构修改
	batchDeleteUpdateZjHwZyResourceOrganization:"batchDeleteUpdateZjHwZyResourceOrganization",//批量删除机构
	getZjHwZyResourceOrganizationDetail:"getZjHwZyResourceOrganizationDetail",//机构详情
	batchImportZjHwZyResourceOrganization:"batchImportZjHwZyResourceOrganization",//机构批量导入

	
	
	
	getZjHwZyResourceSchemeList:"getZjHwZyResourceSchemeList",//查询方案库列表
	addZjHwZyResourceScheme:"addZjHwZyResourceScheme",//新增方案库
	updateZjHwZyResourceScheme:"updateZjHwZyResourceScheme",//更新方案库
	batchDeleteUpdateZjHwZyResourceScheme:"batchDeleteUpdateZjHwZyResourceScheme",//批量删除方案库
	getZjHwZyResourceSchemeDetail:"getZjHwZyResourceSchemeDetail",//方案详情
	batchImportZjHwZyResourceScheme:"batchImportZjHwZyResourceScheme",//技术质量成果库批量导入
	
	
	getZjHwZyResourceSupplierList:"getZjHwZyResourceSupplierList",//查询供应商列表
	addZjHwZyResourceSupplier:"addZjHwZyResourceSupplier",//新增供应商
	updateZjHwZyResourceSupplier:"updateZjHwZyResourceSupplier",//更新供应商
	batchDeleteUpdateZjHwZyResourceSupplier:"batchDeleteUpdateZjHwZyResourceSupplier",//批量删除供应商
	getZjHwZyResourceSupplierDetail:"getZjHwZyResourceSupplierDetail",//供应商详情
	batchImportZjHwZyResourceSupplier:"batchImportZjHwZyResourceSupplier",//供应商批量导入
	
	
	
	getZjHwZyResourceMaterialQuotaList:"getZjHwZyResourceMaterialQuotaList",//材料消耗定额列表
	addZjHwZyResourceMaterialQuota:"addZjHwZyResourceMaterialQuota",//材料消耗定额新增
	updateZjHwZyResourceMaterialQuota:"updateZjHwZyResourceMaterialQuota",//材料消耗定额修改
	batchDeleteUpdateZjHwZyResourceMaterialQuota:"batchDeleteUpdateZjHwZyResourceMaterialQuota",//材料消耗定额批量删除
	getZjHwZyResourceMaterialQuotaDetail:"getZjHwZyResourceMaterialQuotaDetail",//材料消耗定额详情
	batchImportZjHwZyResourceMaterialQuota:"batchImportZjHwZyResourceMaterialQuota",//物资消耗批量导入
	//限价库相关
	getZjHwZyResourcePriceMaterialList:"getZjHwZyResourcePriceMaterialList",//
	addZjHwZyResourcePriceMaterial:"addZjHwZyResourcePriceMaterial",//
	updateZjHwZyResourcePriceMaterial:"updateZjHwZyResourcePriceMaterial",//
	batchDeleteUpdateZjHwZyResourcePriceMaterial:"batchDeleteUpdateZjHwZyResourcePriceMaterial",//
	getZjHwZyResourcePriceMaterialDetail:"getZjHwZyResourcePriceMaterialDetail",//
	batchImportZjHwZyResourcePriceMaterial:"batchImportZjHwZyResourcePriceMaterial",//物资限价批量导入
	//主
	getZjHwZyResourcePriceSubpackageMainList:"getZjHwZyResourcePriceSubpackageMainList",//
	addZjHwZyResourcePriceSubpackageMain:"addZjHwZyResourcePriceSubpackageMain",//
	updateZjHwZyResourcePriceSubpackageMain:"updateZjHwZyResourcePriceSubpackageMain",//
	batchDeleteUpdateZjHwZyResourcePriceSubpackageMain:"batchDeleteUpdateZjHwZyResourcePriceSubpackageMain",//
	//副
	getZjHwZyResourcePriceSubpackageDetailList:"getZjHwZyResourcePriceSubpackageDetailList",//
	addZjHwZyResourcePriceSubpackageDetail:"addZjHwZyResourcePriceSubpackageDetail",//
	updateZjHwZyResourcePriceSubpackageDetail:"updateZjHwZyResourcePriceSubpackageDetail",//
	batchDeleteUpdateZjHwZyResourcePriceSubpackageDetail:"batchDeleteUpdateZjHwZyResourcePriceSubpackageDetail",//
	getZjHwZyResourcePriceSubpackageDetailDetail:"getZjHwZyResourcePriceSubpackageDetailDetail",//
	exportExcelZjHwZyResourcePriceSubpackageDetail:"exportExcelZjHwZyResourcePriceSubpackageDetail",//
	batchImportZjHwZyResourcePriceSubpackage:"batchImportZjHwZyResourcePriceSubpackage",//分包限价批量导入
	
	
	
	
	
	getZjHwZyResourcePriceTempList:"getZjHwZyResourcePriceTempList",//
	addZjHwZyResourcePriceTemp:"addZjHwZyResourcePriceTemp",//
	updateZjHwZyResourcePriceTemp:"updateZjHwZyResourcePriceTemp",//
	batchDeleteUpdateZjHwZyResourcePriceTemp:"batchDeleteUpdateZjHwZyResourcePriceTemp",//
	getZjHwZyResourcePriceTempDetail:"getZjHwZyResourcePriceTempDetail",//
	batchImportZjHwZyResourcePriceTemp:"batchImportZjHwZyResourcePriceTemp",//临建限价批量导入
	
	
	
	//oa权限人员
	getZjHwZyResourceOaList:"getZjHwZyResourceOaList",
	addZjHwZyResourceOa:"addZjHwZyResourceOa",
	updateZjHwZyResourceOa:"updateZjHwZyResourceOa",
	batchDeleteUpdateZjHwZyResourceOa:"batchDeleteUpdateZjHwZyResourceOa",
	
	//20190617
	getZjHwZyResourceChangeClaimChapterList:"getZjHwZyResourceChangeClaimChapterList",
	addZjHwZyResourceChangeClaimChapter:"addZjHwZyResourceChangeClaimChapter",
	updateZjHwZyResourceChangeClaimChapter:"updateZjHwZyResourceChangeClaimChapter",
	batchDeleteUpdateZjHwZyResourceChangeClaimChapter:"batchDeleteUpdateZjHwZyResourceChangeClaimChapter",//
	getZjHwZyResourceChangeClaimClassifyList:"getZjHwZyResourceChangeClaimClassifyList",
	addZjHwZyResourceChangeClaimClassify:"addZjHwZyResourceChangeClaimClassify",
	updateZjHwZyResourceChangeClaimClassify:"updateZjHwZyResourceChangeClaimClassify",
	batchDeleteUpdateZjHwZyResourceChangeClaimClassify:"batchDeleteUpdateZjHwZyResourceChangeClaimClassify",//
	getZjHwZyResourceChangeClaimPointList:"getZjHwZyResourceChangeClaimPointList",
	addZjHwZyResourceChangeClaimPoint:"addZjHwZyResourceChangeClaimPoint",
	updateZjHwZyResourceChangeClaimPoint:"updateZjHwZyResourceChangeClaimPoint",
	batchDeleteUpdateZjHwZyResourceChangeClaimPoint:"batchDeleteUpdateZjHwZyResourceChangeClaimPoint",//
	batchImportResourceChangeClaimPoint:"batchImportResourceChangeClaimPoint",//
	//
	getZjHwZyResourceAuditList:"getZjHwZyResourceAuditList",
	addZjHwZyResourceAudit:"addZjHwZyResourceAudit",
	updateZjHwZyResourceAudit:"updateZjHwZyResourceAudit",
	batchDeleteUpdateZjHwZyResourceAudit:"batchDeleteUpdateZjHwZyResourceAudit",//
	getZjHwZyResourceAuditChapterList:"getZjHwZyResourceAuditChapterList",
	addZjHwZyResourceAuditChapter:"addZjHwZyResourceAuditChapter",
	updateZjHwZyResourceAuditChapter:"updateZjHwZyResourceAuditChapter",
	batchDeleteUpdateZjHwZyResourceAuditChapter:"batchDeleteUpdateZjHwZyResourceAuditChapter",//
	getZjHwZyResourceAuditInventoryList:"getZjHwZyResourceAuditInventoryList",
	addZjHwZyResourceAuditInventory:"addZjHwZyResourceAuditInventory",
	updateZjHwZyResourceAuditInventory:"updateZjHwZyResourceAuditInventory",
	batchDeleteUpdateZjHwZyResourceAuditInventory:"batchDeleteUpdateZjHwZyResourceAuditInventory",
	batchImportZjHwZyResourceAuditInventory:"batchImportZjHwZyResourceAuditInventory",//
	
	//getZjHwZyResourceChangeList
	getZjHwZyResourceChangeList:"getZjHwZyResourceChangeList",
	addZjHwZyResourceChange:"addZjHwZyResourceChange",
	updateZjHwZyResourceChange:"updateZjHwZyResourceChange",
	batchDeleteUpdateZjHwZyResourceChange:"batchDeleteUpdateZjHwZyResourceChange",
	getZjHwZyResourceChangeDetail:"getZjHwZyResourceChangeDetail",
	batchImportResourceChange:"batchImportResourceChange",
	
	getZjHwZyResourceAuditIssueList:"getZjHwZyResourceAuditIssueList",
	addZjHwZyResourceAuditIssue:"addZjHwZyResourceAuditIssue",
	updateZjHwZyResourceAuditIssue:"updateZjHwZyResourceAuditIssue",
	batchDeleteUpdateZjHwZyResourceAuditIssue:"batchDeleteUpdateZjHwZyResourceAuditIssue",
	getZjHwZyResourceAuditIssueDetail:"getZjHwZyResourceAuditIssueDetail",
	batchImportResourceAuditIssue:"batchImportResourceAuditIssue",
	
	
	
	
	batchDeleteUpdateZjZcSubasset:"batchDeleteUpdateZjZcSubasset"
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
//window.lny = window.l = new Lny(apiNames,'http://wechat.zjyjhw.com:89/haiwei/')
 //window.lny = window.l = new Lny(apiNames,'http://localhost:8080/web/')
 window.lny = window.l = new Lny(apiNames,'http://192.168.1.223:8080/web/')
//window.lny = window.l = new Lny(apiNames,'http://wechat.zjyjhw.com:89/haiwei/')