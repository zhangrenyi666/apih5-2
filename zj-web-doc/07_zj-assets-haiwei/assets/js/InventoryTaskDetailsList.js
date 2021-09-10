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
            "targets": [1],
            "data": "rowIndex",
            "title": 'No.',
            "width": 25,
            "render": function (data) {
                return data + 1
            }
        },
        {
            "targets": [2],//第几列
            "data": "sszclx1Name",//接口对应字段
            "title": "资产大类",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "sszclx2Name",//接口对应字段
            "title": "资产小类",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "zcbh",//接口对应字段
            "title": "资产编号",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "zcmc",//接口对应字段
            "title": "资产名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "grrq",//接口对应字段
            "title": "购入日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd");
            }
        },
        {
            "targets": [7],//第几列
            "data": "zcztName",//接口对应字段
            "title": "资产状态",//表头名
            "defaultContent": "-",//默认显示
        }
    ]
});

var form = $('#form').filterfrom({
	maxLength:3,
    conditions: [//表单项配置
        {
            type: "select",
            name: "sszclx1",
            label: "资产大类",
            ajax: {//如果需要接口获取数据需要添加该属性
                api: "getAssetsTypeTreeAllList",//api名称
                child: "sszclx2",//如果有联动下拉，设置被他控制的下拉name
                valueName: "recordid",//设置value对应的接口字段名称
                textName: "assetTypeIdAndName",//设置text对应的接口字段名称
                childrenName: "currentList",//设置children对应的接口字段名称
            },
        },
        {
            type: "select",
            name: "sszclx2",
            label: "资产小类",
            ajax: {
                parent: "sszclx1",//如果有联动下拉，设置他被控制的下拉name
                valueName: "recordid",
                textName: "assetTypeIdAndName",
            },
        },  
		{
            type: "text",//三种形式：text,select,date,
            name: "sydwid",//输入字段名
            label: "使用单位",//输入字段对杨的中文名称
            placeholder: "请输入使用单位",
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "syr",//输入字段名
            label: "使用人",//输入字段对杨的中文名称
            placeholder: "请输入使用人",
        },	
		{
            type: "date",//text,select,date,
            name: "startTime",
            label: "购入日期开始",
            //defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd"),
            maxDate: "\'#F{$dp.$D(\\\'" + "logmax" + "\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmin"
        },
        {
            type: "date",//text,select,date,
            name: "endTime",
            label: "购入日期结束",
            //defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd"),
            //maxDate: "\'#F{$dp.$D(\\\'"+"logmin"+"\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmax"
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
        // console.log('搜索参数是：',_params)
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
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getAssetsNoInventoryTaskList"),
        params: {
            sszclx1: "",
            sszclx2: "",
            cfdd1: "",
            cfdd2: "",
            zcmc: "",
            sybm: "",
            syr: "",
            cwbh: "",
            zcztdm: "",
			sydw:"",
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
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


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
var table2 = $('#table2').DataTable({
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
            "targets": [1],
            "data": "rowIndex",
            "title": 'No.',
            "width": 25,
            "render": function (data) {
                return data + 1
            }
        },
        {
            "targets": [2],//第几列
            "data": "sszclx1Name",//接口对应字段
            "title": "资产大类",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "sszclx2Name",//接口对应字段
            "title": "资产小类",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "zcbh",//接口对应字段
            "title": "资产编号",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "zcmc",//接口对应字段
            "title": "资产名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "grrq",//接口对应字段
            "title": "购入日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd");
            }
        },
        {
            "targets": [7],//第几列
            "data": "zcztName",//接口对应字段
            "title": "资产状态",//表头名
            "defaultContent": "-",//默认显示
        }
    ]
});

var form2 = $('#form2').filterfrom({
	maxLength:3,
    conditions: [//表单项配置
        {
            type: "select",
            name: "sszclx1",
            label: "资产大类",
            ajax: {//如果需要接口获取数据需要添加该属性
                api: "getAssetsTypeTreeAllList",//api名称
                child: "sszclx2",//如果有联动下拉，设置被他控制的下拉name
                valueName: "recordid",//设置value对应的接口字段名称
                textName: "assetTypeIdAndName",//设置text对应的接口字段名称
                childrenName: "currentList",//设置children对应的接口字段名称
            },
        },
         {
            type: "select",
            name: "sszclx2",
            label: "资产小类",
            ajax: {
                parent: "sszclx1",//如果有联动下拉，设置他被控制的下拉name
                valueName: "recordid",
                textName: "assetTypeIdAndName",
            },
        },  
		{
            type: "text",//三种形式：text,select,date,
            name: "sydwid",//输入字段名
            label: "使用单位",//输入字段对杨的中文名称
            placeholder: "请输入使用单位",
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "syr",//输入字段名
            label: "使用人",//输入字段对杨的中文名称
            placeholder: "请输入使用人",
        },	
		{
            type: "date",//text,select,date,
            name: "startTime",
            label: "购入日期开始",
            //defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd"),
            maxDate: "\'#F{$dp.$D(\\\'" + "logmax" + "\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmin"
        },
        {
            type: "date",//text,select,date,
            name: "endTime",
            label: "购入日期结束",
            //defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd"),
            //maxDate: "\'#F{$dp.$D(\\\'"+"logmin"+"\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmax"
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
		 // console.log('搜索参数是：',_params)
        pager2.page('remote', _params)
    },
    onReset: function (arr) {//重置按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
        pager2.page('remote', _params)
    }
})
	
var pager2 = $("#pager2").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getAssetsInventoryTaskList"),
		
        params: {
			inventoryTitle:Lny.getUrlParam("titlen"),
            sszclx1: "",
            sszclx2: "",
            cfdd1: "",
            cfdd2: "",
            zcmc: "",
            sybm: "",
            syr: "",
            cwbh: "",
            zcztdm: "",
			sydw:"",
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                $.each(data, function (index, item) {
                    item.rowIndex = index
                })
                table2.clear().rows.add(data).draw();
            } else {
                layer.alert(result.message, { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
        },
    }
});
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

var detailLayer = $('#detailLayer').detailLayer({
    layerArea:['100%', '100%'],
    conditions: [
        {
            type: "select",
            name: "sszclx1",
            label: "资产大类",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getAssetsTypeTreeAllList",
                child: "sszclx2",
                valueName: "recordid",
                textName: "assetTypeIdAndName",
                childrenName: "currentList",
            },
            immutableAdd:true
        },
        {
            type: "select",
            name: "sszclx2",
            label: "资产小类",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                parent: "sszclx1",
                valueName: "recordid",
                textName: "assetTypeIdAndName",
            },
             immutableAdd:true
        },
        {
            type: 'text',//
            name: "zcbh",//
            label: "资产编号",//
            placeholder: "请输入资产编号",
            immutableAdd:true
        },
		
        {
            type: "text",//
            name: "zcmc",//
            label: "资产名称",//
            placeholder: "请输入资产名称",
             immutableAdd:true
        },
		{
            type: "text",//
            name: "zcyz",//
            label: "资产原值（元）",//
            placeholder: "请输入资产原值（元）",
             immutableAdd:true
        },
			{
            type: "select",
            name: "xzl",
            label: "残值率（%）",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getCzlSelectAllList",
                valueName: "dmbh",
                textName: "dmnr"
            },
			 immutableAdd:true
        },
		{
            type: "number",//
            name: "synx",//
            label: "使用年限（年）",//
            placeholder: "请输入使用年限（年）",
			 immutableAdd:true,
            max: 99
        },
		{
            type: "number",//
            name: "bxts",//
            label: "保修天数（天）",//
            placeholder: "请输入保修天数（天）",
			 immutableAdd:true,
            max: 9999999999
        },        
		{
            type: "text",//
            name: "ggxh",//
            label: "规格型号",//
            placeholder: "请输入规格型号",
			 immutableAdd:true
        },
		{
            type: "date",//
            name: "grrq",//
            label: "购入日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"grrq",
            immutableAdd:true
        },
		{
            type: "select",
            name: "grfsdm",
            label: "购入方式",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getBuyMannerSelectAllList",
                valueName: "dmbh",
                textName: "dmnr"
            },
			 immutableAdd:true
        },
        {
            type: "hidden",//
            name: "cgbh",//
            label: "采购编号",//
            placeholder: "请输入采购编号",
			 immutableAdd:true
        },
		{
            type: "pickTree",
            name: "glrymc",
            label: "管理员",
            pickType: {
            department: false,//部门列表对应接口字段名,false表示不开启该类
            member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            },
			 immutableAdd:true
        },
        {
            type: "select",
            name: "cqdwid",
            label: "产权单位",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getUnitSelectAllList",
                valueName: "dwbh",
                textName: "dwmc"
            },
            immutableAdd:true
        },
		 {
            type: "pickTree",//
            name: "oaSydw",//接口字段名
            label: "使用单位",//
            immutableAdd:true,
            pickType: {
                department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
                member: false,//成员列表对应接口字段名,false表示不开启该类
            },
			 immutableAdd:true
        },

        {
            type: "select",//
            name: "sybmid",//接口字段名
            label: "使用部门",//
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getDepartmentSelectAllList",
                valueName: "bmbh",
                textName: "bmmc"
            },
			 immutableAdd:true
        },
        {
            type: "text",
            name: "cfdd1",
            label: "存放地点",
            placeholder: "请输入存放地点",
            immutableAdd:true
        },
        {
            type: "text",//
            name: "syr",//接口字段名
            label: "使用人",//
            placeholder: "请输入使用人",
			 immutableAdd:true
        },
        {
            type: "hidden",//
            name: "zcyt",//
            label: "资产用途",//
            placeholder: "请输入资产用途",
			 immutableAdd:true
        },              	
        {
            type: "hidden",//
            name: "zctm",//
            label: "资产条码",//
            placeholder: "请输入资产条码",
			 immutableAdd:true
        },       
        {
            type: "hidden",//
            name: "djph",//
            label: "登记批号",//
            placeholder: "请输入登记批号",
			 immutableAdd:true
        },
        {
            type: "date",//
            name: "scrq",//
            label: "出厂日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"scrq",
			 immutableAdd:true
        },    
        {
            type: "text",//
            name: "serialNumber",//
            label: "出厂编号",//
            placeholder: "请输入出厂编号",
			 immutableAdd:true
        },		
        {
            type: "hidden",//
            name: "nynx",//
            label: "耐用年限（年）",//
            placeholder: "请输入耐用年限（年）",
            max: 9999999999,
			 immutableAdd:true
        },
		{
            type: "select",//
            name: "brandId",//
            label: "资产品牌",
            placeholder: "请输入资产品牌",
			selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getAssetsBrandSelectAllList",
                valueName: "recordid",
                textName: "brandName",
            },
            immutableAdd:true
        },
        {
            type: "select",
            name: "gys",
            label: "供应商",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getSupplierSelectAllList",
                valueName: "recordid",
                textName: "gysmc",
            },  
           immutableAdd:true			
        },
        {
            type: "textarea",//
            name: "bz",//
            label: "备注",//
            placeholder: "请输入备注",
			 immutableAdd:true
        },
        {
            type: "upload",//
            name: "imageList",//
            label: "资产图片",//
            btnName: "添加图片",
			immutableAdd:true,
            fileType: ["jpg", "jpeg", "png", "gif"]
        }
    ],
    onSave: function (obj, _params) {
        obj.close()
    },
    onAdd: function (obj, _params) {
        obj.close()
    }
});
$("body").on("click", "button", function () {
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            var checkedData = l.getTableCheckedData(table)				
		 if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
				for(var i=0;i<checkedData.length;i++){
                    if(checkedData[i].zcztdm == 2  ){
                        layer.alert("资产未验收", { icon: 0 }, function (index) {
                            layer.close(index);
                        });
                        return;
                    }else  if(checkedData[i].zcztdm == 3){
                        layer.alert("资产不合格", { icon: 0 }, function (index) {
                            layer.close(index);
                        });
                        return;
                    }else  if(checkedData[i].zcztdm == 4){
                        layer.alert("资产已丢失", { icon: 0 }, function (index) {
                            layer.close(index);
                        });
                        return;
                    }else  if(checkedData[i].zcztdm == 5){
                        layer.alert("资产已报废", { icon: 0 }, function (index) {
                            layer.close(index);
                        });
                        return;
                    }					
				}
				
				var body={
					currentList:checkedData,
					inventoryTitle:Lny.getUrlParam("titlen"),
					//recordid:Lny.getUrlParam("id"),
				}
				//console.log(body)
                l.ajax("batchAddAssetsInventoryTask",body,function(data){
					 pager.page('remote')
				     pager2.page('remote')
				})
			}
            break;		
        case "edit":
		 var checkedData = l.getTableCheckedData(table)
		 // console.log('数据是:',checkedData[0])
            if (checkedData.length == 1) {
               // detailLayer.open(checkedData[0]);
				 var params;
				  params = {};
               params.recordid = checkedData[0].recordid;
			   //console.log('数据是:',params)
				 l.ajax('getAssetsDetails',params,function(res){
                detailLayer.open(res);
				 })
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
            var checkedData = l.getTableCheckedData(table2)
            console.log(checkedData)
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
			}
			 else {
				for(var i=0;i<checkedData.length;i++){
                    if(checkedData[i].pdztdm == 2  ){
                        layer.alert("该资产已盘点", { icon: 0 }, function (index) {
                            layer.close(index);
                        });
                        return;
                    }		
				}					
                /*  l.delTableRow("recordid", 'currentList', 'batchDeleteAssetsInventoryTask', checkedData, function (data) {
                     pager.page('remote')
				     pager2.page('remote')
                 })//  删除url地址 */
				 	l.ajax("batchDeleteAssetsInventoryTask", checkedData, function () {
					pager.page('remote')
					pager2.page('remote')
				})
            }
            break;
    }
})