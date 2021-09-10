var code = Lny.getUrlParam('code')
Lny.setCookie('code', code, 30)
var table = $('#table').DataTable({
    "info": false,//是否显示数据信息
    "paging": false,//是否开启自带分页
    "ordering": false, //是否开启DataTables的高度自适应
    "processing": false,//是否显示‘进度’提示
    "searching": false,//是否开启自带搜索
    "autoWidth": false,//是否监听宽度变化
    "columnDefs": [//表格列配置
        {
            "targets": [0],
            "data": "rowIndex",
            "width": 13,
            "title": '<input type="checkbox">',
            "render": function (data) {
                return '<input type="checkbox" name="checkbox" data-rowIndex="' + data + '" >';
            }
        },	
        {
            "targets": [1],//第几列
            "data": "supperClass",//接口对应字段
            "title": "类别",//表头名
            "defaultContent": "-",//默认显示
			 "render": function (data) {
                var text = ""
                switch (data) {
                    case "0": text = "分包供应商";
                        break;
                    case "1": text = "物资供应商";
                        break;
                    case "2": text = "机械租赁供应商";
                        break;
					case "3": text = "广告宣传供应商";
                        break;
                    case "4": text = "信息供应商";
                        break;
					case "5": text = "安全供应商";
                        break;
                    case "6": text = "其他供应商";
                        break;
                    default: text = "未知";
                        break;
                }
                return text
            }
        },	
        {
            "targets": [2],//第几列
            "data": "unitName",//接口对应字段
            "title": "单位名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "warehouseTime",//接口对应字段
            "title": "入库时间",//表头名
            "defaultContent": "-",//默认显示
			"render": function (data) {
		    	return (data) ? l.dateFormat(new Date(data), "yyyy-MM-dd") : "-";
            }
        },		       
        {
            "targets": [4],//第几列
            "data": "linkman",//接口对应字段
            "title": "联系人",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "businessScope",//接口对应字段
            "title": "经营范围",//表头名
            "defaultContent": "-",//默认显示
        }, 	
        {
            "targets": [6],//第几列
            "data": "unifiedSocialCreditCode",//接口对应字段
            "title": "统一社会信用代码",//表头名
            "defaultContent": "-",//默认显示
        }, 			
        {
            "targets": [7],//第几列
            "data": "modifyUserName",//接口对应字段
            "title": "操作员",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [8],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "上传时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
		{
            "targets": [9],//第几列
            "width": 100,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名
			"render": function (data) {
                var html = '';
                html += '<span class="dropDown">'
			    html += '<a style="color:blue;"  href="javascript:void(0);" onclick="myOpen1(\' ' + data.rowIndex + '\',\'' + 'xq' + '\')" href="javascript:void(0);">详情</a>';
                html += '</span>'	
                return html;
            }           
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "select",
            name: "supperClass",
            label: "类别",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 0,//输入字段的值
                    text: "分包供应商",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "物资供应商",//显示中文名
                    selected: false//默认是否选中
                },
				{
                    value: 2,//输入字段的值
                    text: "机械租赁供应商",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 3,//输入字段的值
                    text: "广告宣传供应商",//显示中文名
                    selected: false//默认是否选中
                },
				{
                    value: 4,//输入字段的值
                    text: "信息供应商",//显示中文名
                    selected: false//默认是否选中
                },
				{
                    value: 5,//输入字段的值
                    text: "安全供应商",//显示中文名
                    selected: false//默认是否选中
                },
				{
                    value: 6,//输入字段的值
                    text: "其他供应商",//显示中文名
                    selected: false//默认是否选中
                }	
            ],
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "key",//输入字段名
            label: "关键字",//输入字段对杨的中文名称
            placeholder: "请输入关键字",
        }
        
    ],
    onSearch: function (arr) {//搜索按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].name == "startDate" || arr[i].name == "endDate") {
                if (arr[i].value) {
                    _params[arr[i].name] = l.dateFormat(new Date(new Date(arr[i].value).getTime()), "yyyy-MM-dd");
                } else {
                    _params[arr[i].name] = "";
                }
            } else {
                _params[arr[i].name] = arr[i].value;
            }
        }
        pager.page('remote',0, _params)
    },
    onReset: function (arr) {//重置按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
        pager.page('remote', _params)
    }
})
var allData;
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjHwZyResourceSupplierList"),
        params: {        
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
				allData = data;
                $.each(data, function (index, item) {
                    item.rowIndex = index
                })
                table.clear().rows.add(data).draw();
            } else {
                layer.alert(result.message, { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
        },
    }
});

var detailLayer = $('#detailLayer').detailLayer({
layerArea: ["100%", "100%"],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "supplierId",//输入字段名
        },
        {
            type: "select",//
            name: "supperClass",//
            label: "类别",//
            selectList: [//当类型为select时，option配置
			    {
                    value: 0,//输入字段的值
                    text: "分包供应商",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "物资供应商",//显示中文名
                    selected: false//默认是否选中
                },
				{
                    value: 2,//输入字段的值
                    text: "机械租赁供应商",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 3,//输入字段的值
                    text: "广告宣传供应商",//显示中文名
                    selected: false//默认是否选中
                },
				{
                    value: 4,//输入字段的值
                    text: "信息供应商",//显示中文名
                    selected: false//默认是否选中
                },
				{
                    value: 5,//输入字段的值
                    text: "安全供应商",//显示中文名
                    selected: false//默认是否选中
                },
				{
                    value: 6,//输入字段的值
                    text: "其他供应商",//显示中文名
                    selected: false//默认是否选中
                }		
            ],
            must: true
        },
        {
            type: "text",//
            name: "resourceNumber",//
            label: "资源编号",//
            placeholder: "请输入资源编号",
            must: true
        },
		{
            type: "textarea",//
            name: "businessScope",//
            label: "经营范围",//
            placeholder: "请输入经营范围"
        },
		{
            type: "select",
            name: "supplierDepId",
            label: "管理部门",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getZjHwZyResourceDepSelectAllList",
                valueName: "depId",
                textName: "depName"
            },
			must: true
        },
		/* {
            type: "pickTree",
            name: "oaDepName",
            label: "管理部门",
            pickType: {
                department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
                member: false,//成员列表对应接口字段名,false表示不开启该类
            },
        },	 */	
		{
            type: "text",//
            name: "businessPlace",//
            label: "经营地",//
            placeholder: "请输入经营地",
            must: true
        },
		/* {
            type: "text",//
            name: "aptitudeName",//
            label: "资质名称",//
            placeholder: "请输入资质名称"
        }, */
		{
            type: "text",//
            name: "aptitudeLevel",//
            label: "资质等级",//
            placeholder: "请输入资质等级"
        },
		{
            type: "text",//
            name: "registeredAddress",//
            label: "注册地",//
            placeholder: "请输入注册地"
        },
		{
            type: "text",//
            name: "unitName",//
            label: "单位名称",//
            placeholder: "请输入单位名称",
            must: true
        }, 
		{
            type: "text",//
            name: "unifiedSocialCreditCode",//
            label: "统一社会信用代码",//
            placeholder: "请输入统一社会信用代码"
        },
		{
            type: "number",//
            name: "registeredFund",//
            label: "注册资金（万元）",//
            placeholder: "注册资金（万元）",
            must: true
        },
		{
            type: "text",//
            name: "legalRepresentative",//
            label: "法定代表人",//
            placeholder: "请输入法定代表人"
        },
		{
            type: "text",//
            name: "idCard",//
            label: "身份证号",//
            placeholder: "请输入身份证号"
        },
		{
            type: "text",//
            name: "linkman",//
            label: "联系人",//
            placeholder: "请输入联系人"
        },
		{
            type: "text",//
            name: "contactWay",//
            label: "联系方式",//
            placeholder: "请输入联系方式",
            must: true
        },
		{
            type: "date",//
            name: "warehouseTime",//
            label: "入库时间",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id: "warehouseTime",
        },						
		{
            type: "text",//
            name: "useProject",//
            label: "使用项目",//
            placeholder: "请输入使用项目",
            must: true
        },
		{
            type: "text",//
            name: "initRecommendationUnit",//
            label: "初始推荐单位",//
            placeholder: "请输入初始推荐单位"
        },
		{
            type: "text",//
            name: "projectEvaluation",//
            label: "项目评价情况",//
            placeholder: "请输入项目评价情况"
        },
		{
            type: "text",//
            name: "corpEvaluation",//
            label: "公司评价情况",//
            placeholder: "请输入公司评价情况"
        },
		{
            type: "text",//
            name: "cooperationPerformance",//
            label: "合作业绩",//
            placeholder: "请输入合作业绩"
        },
		{
            type: "text",//
            name: "cooperativePerformanceAmount",//
            label: "合作业绩金额(万元)",//
            placeholder: "请输入合作业绩金额(万元)"
        },	
        {
            type: "upload",//
            name: "supplierFileList",//
            label: "附件上传",//
            btnName: "选择文件",
            projectName: "zj-hw-resource",
			uploadUrl:"uploadFilesByFileName",
			maxLen: 1,
            fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf","zip","rar","dwg"]
        } ,
        {
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入备注"
        }	
		
    ],
    onSave: function (obj, _params) {
         l.ajax('updateZjHwZyResourceSupplier', _params, function (data) {
                pager.page('remote')
                obj.close()
            })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjHwZyResourceSupplier', _params, function (data) {
                pager.page('remote')
                obj.close()
            })
    }
})


//详情
var supplierDetailLayer = $('#supplierDetailLayer').detailLayer({
layerArea: ["100%", "100%"],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "supplierId",//输入字段名
        },
        {
            type: "select",//
            name: "supperClass",//
            label: "类别",//
            selectList: [//当类型为select时，option配置
                {
                    value: 0,//输入字段的值
                    text: "分包供应商",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "物资供应商",//显示中文名
                    selected: false//默认是否选中
                },
				{
                    value: 2,//输入字段的值
                    text: "机械租赁供应商",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 3,//输入字段的值
                    text: "广告宣传供应商",//显示中文名
                    selected: false//默认是否选中
                },
				{
                    value: 4,//输入字段的值
                    text: "信息供应商",//显示中文名
                    selected: false//默认是否选中
                },
				{
                    value: 5,//输入字段的值
                    text: "安全供应商",//显示中文名
                    selected: false//默认是否选中
                },
				{
                    value: 6,//输入字段的值
                    text: "其他供应商",//显示中文名
                    selected: false//默认是否选中
                }		
            ],
            immutableAdd: true
        },
        {
            type: "text",//
            name: "resourceNumber",//
            label: "资源编号",//
            placeholder: "请输入资源编号",
            immutableAdd: true
        },
		{
            type: "textarea",//
            name: "businessScope",//
            label: "经营范围",//
            placeholder: "请输入经营范围",
			 immutableAdd: true
        },
		{
            type: "text",//
            name: "supplierDepName",//
            label: "管理部门",//
            placeholder: "请输入管理部门",
			immutableAdd: true
        },
		{
            type: "text",//
            name: "businessPlace",//
            label: "经营地",//
            placeholder: "请输入经营地",
            immutableAdd: true
        },
		{
            type: "text",//
            name: "aptitudeLevel",//
            label: "资质等级",//
            placeholder: "请输入资质等级",
            immutableAdd: true
        },
		{
            type: "text",//
            name: "registeredAddress",//
            label: "注册地",//
            placeholder: "请输入注册地",
			immutableAdd: true
        },
		 {
            type: "text",//
            name: "unitName",//
            label: "单位名称",//
            placeholder: "请输入单位名称",
            immutableAdd: true
        }, 
		{
            type: "text",//
            name: "unifiedSocialCreditCode",//
            label: "统一社会信用代码",//
            placeholder: "请输入统一社会信用代码",
			immutableAdd: true
        },
		{
            type: "number",//
            name: "registeredFund",//
            label: "注册资金（万元）",//
            placeholder: "注册资金（万元）",
            immutableAdd: true
        },
		{
            type: "text",//
            name: "legalRepresentative",//
            label: "法定代表人",//
            placeholder: "请输入法定代表人",
			immutableAdd: true
        },
		{
            type: "text",//
            name: "idCard",//
            label: "身份证号",//
            placeholder: "请输入身份证号",
            immutableAdd: true
        },
		{
            type: "text",//
            name: "linkman",//
            label: "联系人",//
            placeholder: "请输入联系人",
			immutableAdd: true
        },
		{
            type: "text",//
            name: "contactWay",//
            label: "联系方式",//
            placeholder: "请输入联系方式",
            immutableAdd: true
        },
		{
            type: "date",//
            name: "warehouseTime",//
            label: "入库时间",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id: "warehouseTime",
			immutableAdd: true
        },						
		{
            type: "text",//
            name: "useProject",//
            label: "使用项目",//
            placeholder: "请输入使用项目",
            immutableAdd: true
        },
		{
            type: "text",//
            name: "initRecommendationUnit",//
            label: "初始推荐单位",//
            placeholder: "请输入初始推荐单位",
			immutableAdd: true
        },
		{
            type: "text",//
            name: "projectEvaluation",//
            label: "项目评价情况",//
            placeholder: "请输入项目评价情况",
            immutableAdd: true
        },
		{
            type: "text",//
            name: "corpEvaluation",//
            label: "公司评价情况",//
            placeholder: "请输入公司评价情况",
			immutableAdd: true
        },
		{
            type: "text",//
            name: "cooperationPerformance",//
            label: "合作业绩",//
            placeholder: "请输入合作业绩",
            immutableAdd: true
        },
		{
            type: "text",//
            name: "cooperativePerformanceAmount",//
            label: "合作业绩金额(万元)",//
            placeholder: "请输入合作业绩金额(万元)",
			immutableAdd: true
        },		
        {
            type: "upload",//
            name: "supplierFileList",//
            label: "附件上传1",//
            btnName: "选择文件",
            projectName: "zj-hw-resource",
			uploadUrl:"uploadFilesByFileName",
			maxLen: 1,
            fileType: ["xls", "xlsx"],
			immutableAdd: true
        } ,
        {
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入备注",
			 immutableAdd: true
        }	
		
    ],
})

var leading = $('#leading').detailLayer({
    layerArea:['410px', '310px'],
    layerTitle:['导入'],
        conditions: [
        {
            type: "upload",
            maxLen:1,
            name: "importFileList",
            label: "导入文件",
            btnName: "选择文件",
			projectName:"zj-assets-haiwei",
            fileType: ["xls", "xlsx"],
        }
    ],
    onAdd: function (obj, _params){
        if(_params.importFileList.length==0){
            layer.alert("请选择文件", { icon: 0, }, function (index) {
                layer.close(index);
            });
            return;
        }
        l.ajax('batchImportZjHwZyResourceSupplier', {"importFileList":_params.importFileList}, function (_params) {
            pager.page('remote')
            obj.close()
        })
    }
});

$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            detailLayer.open();
            break;
        case "edit":
            if (checkedData.length == 1) {
                detailLayer.open(checkedData[0]);
            } else if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.alert("只能选择一个", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
            break;
        case "del":
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
				 layer.confirm("确定删除？", { icon: 0,}, function (index) {
				l.ajax("batchDeleteUpdateZjHwZyResourceSupplier", checkedData, function () {
					pager.page('remote')
				})
				layer.close(index);
            });
            }
            break;
		case "leading"://导入
            leading.open();
            break;
    }
})

function myOpen1(index, type) {
    var rowData = allData[Number(index)];
    var params;
    switch (type) {
        case 'xq':
            params = {};
            params.supplierId = rowData.supplierId;
            //console.log('params:',params)
            l.ajax('getZjHwZyResourceSupplierDetail', params, function (res) {
                supplierDetailLayer.open(res);
                $('#supplierDetailLayer .btn').hide();
            })
            break;
    }
}