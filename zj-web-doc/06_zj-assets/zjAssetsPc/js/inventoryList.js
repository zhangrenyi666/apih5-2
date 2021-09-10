﻿var code = Lny.getUrlParam('code')
Lny.setCookie('code', code, 30)
var star=""//状态传值判断
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
            "data": "sszclx2Name",//接口对应字段
            "title": "所属小类",//表头名
            "defaultContent": "-"//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "zcmc",//接口对应字段
            "title": "资产名称",//表头名
            "defaultContent": "-"//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "pdrq",//接口对应字段
            "title": "盘点日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                if(data){
                    return l.dateFormat(new Date(data), "yyyy-MM-dd");
                }else{
                    return '-';
                }
            }
        },
        {
        	"targets": [5],//第几列
        	"data": "pdztName",//接口对应字段
        	"title": "盘点状态",//表头名
        	"defaultContent": "-",//默认显示
			 "render": function (data) {//自定义输出
                switch (data) {
                    case "1":
                    	data = "未盘点"
                        break;
                    case "2":
                    	data = "已盘点"
                        break;
                    case null:
                        data = "未盘点"
                }
                return data;
            }
        },
        {
            "targets": [6],//第几列
			"data": "cfddq",//接口对应字段
            "title": "存放地点(前)",//表头名
            "defaultContent": "-"//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "cfddh",//接口对应字段
            "title": "存放地点(后)",//表头名
            "defaultContent": "-"//默认显示
        },
        {
            "targets": [8],//第几列
            "data": "sybuidbeforeName",//接口对应字段  sybuidbeforeName   sybuidbeforeName
            "title": "使用部门(前）",//表头名
            "defaultContent": "-"//默认显示
        },
        {
            "targets": [9],//第几列
            "data": "sybuidafterName",//接口对应字段
            "title": "使用部门(后)",//表头名
            "defaultContent": "-"//默认显示
        },
        {
            "targets": [10],//第几列
            "data": "syrbefore",//接口对应字段
            "title": "使用者(前)",//表头名
            "defaultContent": "-"//默认显示
        },
        {
            "targets": [11],//第几列
            "data": "syrafter",//接口对应字段
            "title": "使用者(后)",//表头名
            "defaultContent": "-"//默认显示
        },
        {
            "targets": [12],//第几列
            "data": "copestateName",//接口对应字段
            "title": "处理状态",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                switch (data) {
                    case "1":
                    	data = "未处理"
                        break;
                    case "2":
                    	data = "已处理"
                        break;
                }
                return data;
            }
        },
        {
            "targets": [13],//第几列
            "data": "clfa",//接口对应字段
            "title": "处理方案",//表头名
            "defaultContent": "-"//默认显示
        },
        {
            "targets": [14],//第几列
            "data": "remarks",//接口对应字段
            "title": "备注",//表头名
            "defaultContent": "-"//默认显示
        },
        {
            "targets": [15],//第几列
            "data": "modifyUser",//接口对应字段
            "title": "盘点者",//表头名
            "defaultContent": "-"//默认显示
        },
        {
            "targets": [16],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "更新时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                if(data){
                    return l.dateFormat(new Date(data), "yyyy-MM-dd");
                }else{
                    return '-';
                }
            }
        }
    ]
});
var form = $('#form').filterfrom({
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
                    childrenName: "currentList"//设置children对应的接口字段名称
                }
            },
            {
                type: "select",
                name: "sszclx2",
                label: "资产小类",
                ajax: {
                    parent: "sszclx1",//如果有联动下拉，设置他被控制的下拉name
                    valueName: "recordid",
                    textName: "assetTypeIdAndName"
                }
            },
        {
            type: "text",//三种形式：text,select,date,
            name: "zcmc",//输入字段名
            label: "资产名称",//输入字段对杨的中文名称
            placeholder: "请输入资产名称"
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "sydwid",//输入字段名
            label: "使用单位",//输入字段对杨的中文名称
            placeholder: "请输入使用单位",
        },
        {
            type: "date",//text,select,date,
            name: "startTime",
            label: "盘点日期开始",
            //defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd"),
            maxDate: "\'#F{$dp.$D(\\\'" + "logmax" + "\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmin"
        },
        {
            type: "date",//text,select,date,
            name: "endTime",
            label: "盘点日期结束",
            //defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd"),
            //maxDate: "\'#F{$dp.$D(\\\'"+"logmin"+"\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmax"
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "inventoryTitle",//输入字段名
            label: "盘点任务标题",//输入字段对杨的中文名称
            placeholder: "请输入盘点任务标题"
        },
        {
            type: "select",
            name: "pdztdm",
            label: "盘点状态",
            selectList: [//当类型为select时，option配置
                         {
                             value: "",//输入字段的值
                             text: "全部",//显示中文名
                             selected: true//默认是否选中
                         },
                         {
                             value: 1,//输入字段的值
                             text: "未盘点",//显示中文名
                             selected: false//默认是否选中
                         },
                         {
                             value: 2,//输入字段的值
                             text: "已盘点",//显示中文名
                             selected: false//默认是否选中
                         }
                     ]
        },
        {
            type: "select",
            name: "copestate",
            label: "处理状态",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "未处理",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 2,//输入字段的值
                    text: "已处理",//显示中文名
                    selected: false//默认是否选中
                }
            ]
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
		//_params.currentPage = "1";
		//console.log(_params)
		pager.page('remote',0, _params)
    },
    onReset: function (arr) {//重置按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
        pager.page('remote', _params)
    }
});


var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getInventoryList"),
        params: {
            sszclx1: "",
            sszclx2: "",
            cfdd1: "",
            cfdd2: "",
            zcmc: "",
            startTime: "",
            endTime: "",
            copestate: ""
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                $.each(data, function (index, item) {
                    item.rowIndex = index
                });
                table.clear().rows.add(data).draw();
            } else {
                layer.alert(result.message, { icon: 0 }, function (index) {
                    layer.close(index);
                });
            }
        }
    }
});

//无任务编辑
var detailLayer = $('#detailLayer').detailLayer({
    layerArea:['100%', '100%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "recordid"//输入字段名
        },
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "sszcid"//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "sszcid"//输入字段名
        },
        {
            type: "text",//
            name: "zcmc",//
            label: "资产名称",//
            placeholder: "请输入资产名称",
            immutable:true
        },
        {
            type: "text",
            name: "inventoryTitle",
            label: "盘点标题",
            must: true
        },
        {
            type: "date",//
            name: "pdrq",//
            label: "盘点日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"pdrq",
            immutableAdd:true
        },
        {
            type: "select",
            name: "zcztdm",
            label: "资产状态(前)",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getAssetsStateSelectList",
                valueName: "dmbh",
                textName: "dmnr"
            },
            immutableAdd:true
        },
        {
            type: "select",
            name: "zcztdmafter",
            label: "资产状态(后)",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getAssetsStateSelectList",
                valueName: "dmbh",
                textName: "dmnr"
            },
            must: true
        },
		{
            type: "select",
            name: "cfddidbefore",
            label: "存放地点(前)",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getAssetsPlaceAllList",
                valueName: "recordid",
                textName: "cfddmc"
            },
			immutable:true
        },
		{
            type: "select",
            name: "cfddidafter",
            label: "存放地点(后)",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getAssetsPlaceAllList",
                valueName: "recordid",
                textName: "cfddmc"
            },
			 must: true
        },
        {
            type: "select",//
            name: "sybuidbefore",//
            label: "使用部门(前）",//
            placeholder: "请输入使用部门(前)",
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
            type: "select",//
            name: "sybuidafter",//
            label: "使用部门(后)",//
            placeholder: "请输入使用部门(后)",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getDepartmentSelectAllList",
                valueName: "bmbh",
                textName: "bmmc"
            },
            must: true
        },
        {
            type: "text",//
            name: "syrbefore",//
            label: "使用者(前)",//
            immutableAdd:true
        },
        {
            type: "text",//
            name: "syrafter",//
            label: "使用者(后)",//
            must: true
        },

        {
            type: "select",
            name: "pdjgdm",
            label: "盘点结果",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getInventorySelectAllList",
                valueName: "dmbh",
                textName: "dmnr"          
            },
            must: true
        },
        {
            type: "select",//
            name: "copestate",//
            label: "处理状态",//
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getCopeStateSelectAllList",
                valueName: "dmbh",
                textName: "dmnr",
            },
            must: true,
            immutableAdd:true
        },
        {
            type: "textarea",//
            name: "clfa",//
            label: "处理方案",//
            placeholder: "请输入处理方案"
        },
        {
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入备注"
        },
        {
            type: "upload",//
            name: "imageList",//
            label: "资产图片",//
            btnName: "添加图片",
			projectName:"zj-assets",
            fileType: ["jpg", "jpeg", "png", "gif"]
        }
    ],
    onSave: function (obj, _params) {
    
            l.ajax('updateInventory', _params, function (data) {
                pager.page('remote');
                obj.close()
            })
    },
    onAdd: function (obj, _params) {
            _params.zcztdm = _params.zcztdmafter;
            l.ajax('addInventory', _params, function (data) {
                // console.log('返回的数据是：',data)
                 pager.page('remote');
                 obj.close()
            })
    }
})
//无任务新增
var addDetailLayer = $('#addDetailLayer').detailLayer({
    layerArea:['100%', '100%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "recordid"//输入字段名
        },
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "sszcid"//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "sszcid"//输入字段名
        },
        {
            type: "text",
            name: "sszclx2Name",
            label: "所属小类",
            immutable:true
        },
        {
            type: "text",//
            name: "zcmc",//
            label: "资产名称",//
            placeholder: "请输入资产名称",
            immutable:true
        },
        {
            type: "text",
            name: "inventoryTitle",
            label: "盘点标题",
			 must: true
		
        },		
        {
            type: "date",//
            name: "pdrq",//
            label: "盘点日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"pdrq",
            immutableAdd:true
        },
        {
            type: "select",
            name: "zcztdm",
            label: "资产状态(前)",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getAssetsStateSelectList",
                valueName: "dmbh",
                textName: "dmnr"
            },
            immutableAdd:true
        },
        {
            type: "select",
            name: "zcztdmafter",
            label: "资产状态(后)",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getAssetsStateSelectList",
                valueName: "dmbh",
                textName: "dmnr"
            },
            must: true
        },
		{
            type: "select",
            name: "cfddidbefore",
            label: "存放地点(前)",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getAssetsPlaceAllList",
                valueName: "recordid",
                textName: "cfddmc"
            },
			immutable:true
        },
		{
            type: "select",
            name: "cfddidafter",
            label: "存放地点(后)",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getAssetsPlaceAllList",
                valueName: "recordid",
                textName: "cfddmc"
            },
			 must: true
        },
		{
            type: "text",
            name: "sybuidbeforeName",
            label: "使用部门(前）",
            immutable:true
        },		
        {
            type: "select",//
            name: "sybuidafter",//
            label: "使用部门(后)",//
            placeholder: "请输入使用部门(后)",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getDepartmentSelectAllList",
                valueName: "bmbh",
                textName: "bmmc"
            },
            must: true
        },       
        {
            type: "text",//
            name: "syrbefore",//
            label: "使用者(前)",//
            immutableAdd:true
        },
        {
            type: "text",//
            name: "syrafter",//
            label: "使用者(后)",//
            must: true
        },
        {
            type: "select",
            name: "pdjgdm",
            label: "盘点结果",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getInventorySelectAllList",
                valueName: "dmbh",
                textName: "dmnr"          
            },
            must: true
        },
        {
            type: "hidden",//
            name: "copestate",//
            label: "处理状态",//
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getCopeStateSelectAllList",
                valueName: "dmbh",
                textName: "dmnr",
            },
            immutableAdd:true
        },
        {
            type: "textarea",//
            name: "clfa",//
            label: "处理方案",//
            placeholder: "请输入处理方案"
        },
        {
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入备注"
        },
        {
            type: "upload",//
            name: "imageList",//
            label: "资产图片",//
            btnName: "添加图片",
			projectName:"zj-assets",
            fileType: ["jpg", "jpeg", "png", "gif"]
        }
    ],
    onSave: function (obj, _params) {
            l.ajax('addInventory', _params, function (data) {
                pager.page('remote');
                obj.close()
            })
    },
    onAdd: function (obj, _params) {
            _params.zcztdm = _params.zcztdmafter;
            // console.log('新增 参数是：',_params);
            l.ajax('addInventory', _params, function (data) {
                // console.log('返回的数据是：',data)
                 pager.page('remote');
                 obj.close()
            })
    }
})
//无任务批量新增
var batchAddLayer = $('#batchAddLayer').detailLayer({
    layerArea:['100%', '100%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "recordList"//输入字段名
        },
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "sszcList"//输入字段名
        },
        // {
        //     type: "hidden",//五种形式：text,select,date,hidden,textarea,
        //     name: "sszcid"//输入字段名
        // },
        {
            type: "text",
            name: "inventoryTitle",
            label: "盘点标题",
            must: true
        },
        {
            type: "date",//
            name: "pdrq",//
            label: "盘点日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"pdrq",
            immutableAdd:true
        },
        {
            type: "select",
            name: "zcztdmafter",
            label: "资产状态(后)",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getAssetsStateSelectList",
                valueName: "dmbh",
                textName: "dmnr"
            },
            must: true
        },       
		{
            type: "select",
            name: "cfddidafter",
            label: "存放地点(后)",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getAssetsPlaceAllList",
                valueName: "recordid",
                textName: "cfddmc"
            },
			 must: true
        },
        {
            type: "select",//
            name: "sybuidafter",//
            label: "使用部门(后)",//
            placeholder: "请输入使用部门(后)",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getDepartmentSelectAllList",
                valueName: "bmbh",
                textName: "bmmc"
            },
            must: true
        },
        {
            type: "text",//
            name: "syrafter",//接口字段名
            label: "使用人",//
            placeholder: "请输入使用人",
        },
        {
            type: "select",
            name: "pdjgdm",
            label: "盘点结果",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getInventorySelectAllList",
                valueName: "dmbh",
                textName: "dmnr"          
            },
            must: true
        },
        {
            type: "hidden",//
            name: "copestate",//
            label: "处理状态",//
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getCopeStateSelectAllList",
                valueName: "dmbh",
                textName: "dmnr",
            },
            immutableAdd:true
        },
        {
            type: "textarea",//
            name: "clfa",//
            label: "处理方案",//
            placeholder: "请输入处理方案"
        },
        {
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入备注"
        },
        {
            type: "upload",//
            name: "imageList",//
            label: "资产图片",//
            btnName: "添加图片",
			projectName:"zj-assets",
            fileType: ["jpg", "jpeg", "png", "gif"]
        }
    ],
    onSave: function (obj, _params) {
        //     console.log( '参数是：',_params)
            l.ajax('batchAddInventory', _params, function (data) {
                pager.page('remote');
                obj.close()
            })
    },
    onAdd: function (obj, _params) {       
        } 
})

//有任务新增
var taskAddDetailLayer = $('#taskAddDetailLayer').detailLayer({
    layerArea:['100%', '100%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "recordid"//输入字段名
        },
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "sszcid"//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "sszcid"//输入字段名
        },
        {
            type: "text",
            name: "sszclx2Name",
            label: "所属小类",
            immutable:true
        },
        {
            type: "text",//
            name: "zcmc",//
            label: "资产名称",//
            placeholder: "请输入资产名称",
            immutable:true
        },
        {
            type: "text",
            name: "inventoryTitle",
            label: "盘点标题",
			must: true,
			immutableAdd:true
		
        },		
        {
            type: "date",//
            name: "pdrq",//
            label: "盘点日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"pdrq",
            immutableAdd:true
        },
        {
            type: "select",
            name: "zcztdm",
            label: "资产状态(前)",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getAssetsStateSelectList",
                valueName: "dmbh",
                textName: "dmnr"
            },
            immutableAdd:true
        },
        {
            type: "select",
            name: "zcztdmafter",
            label: "资产状态(后)",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getAssetsStateSelectList",
                valueName: "dmbh",
                textName: "dmnr"
            },
            must: true
        },
		{
            type: "select",
            name: "cfddidbefore",
            label: "存放地点(前)",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getAssetsPlaceAllList",
                valueName: "recordid",
                textName: "cfddmc"
            },
			immutable:true
        },
		{
            type: "select",
            name: "cfddidafter",
            label: "存放地点(后)",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getAssetsPlaceAllList",
                valueName: "recordid",
                textName: "cfddmc"
            },
			 must: true
        },    
        {
            type: "select",//
			name: "sybuidbefore",
            label: "使用部门(前）",//
            placeholder: "请输入使用部门(前)",
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
            type: "select",//
            name: "sybuidafter",//
            label: "使用部门(后)",//
            placeholder: "请输入使用部门(后)",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getDepartmentSelectAllList",
                valueName: "bmbh",
                textName: "bmmc"
            },
            must: true
        },      
        {
            type: "text",//
            name: "syrbefore",//
            label: "使用者(前)",//
            immutableAdd:true
        },
        {
            type: "text",//
            name: "syrafter",//
            label: "使用者(后)",//
            must: true
        },
        {
            type: "select",
            name: "pdjgdm",
            label: "盘点结果",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getInventorySelectAllList",
                valueName: "dmbh",
                textName: "dmnr"          
            },
            must: true
        },
        {
            type: "hidden",//
            name: "copestate",//
            label: "处理状态",//
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getCopeStateSelectAllList",
                valueName: "dmbh",
                textName: "dmnr",
            },
            immutableAdd:true
        },
        {
            type: "textarea",//
            name: "clfa",//
            label: "处理方案",//
            placeholder: "请输入处理方案"
        },
        {
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入备注"
        },
        {
            type: "upload",//
            name: "imageList",//
            label: "资产图片",//
            btnName: "添加图片",
			projectName:"zj-assets",
            fileType: ["jpg", "jpeg", "png", "gif"]
        }
    ],
    onSave: function (obj, _params) {
            l.ajax('addInventoryHaveTitle', _params, function (data) {
                pager.page('remote');
                obj.close()
            })
    },
    onAdd: function (obj, _params) {
            _params.zcztdm = _params.zcztdmafter;
            // console.log('新增 参数是：',_params);
            l.ajax('addInventoryHaveTitle', _params, function (data) {
                // console.log('返回的数据是：',data)
                 pager.page('remote');
                 obj.close()
            })
    }
})
//有任务批量新增
var taskBatchAddDetailLayer = $('#taskBatchAddDetailLayer').detailLayer({
    layerArea:['100%', '100%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "recordList"//输入字段名
        },
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "sszcList"//输入字段名
        },
        {
            type: "text",
            name: "inventoryTitle",
            label: "盘点标题",
            must: true,
			immutableAdd:true
        },
        {
            type: "date",//
            name: "pdrq",//
            label: "盘点日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"pdrq",
            immutableAdd:true
        },
        {
            type: "select",
            name: "zcztdmafter",
            label: "资产状态(后)",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getAssetsStateSelectList",
                valueName: "dmbh",
                textName: "dmnr"
            },
            must: true
        },       
		{
            type: "select",
            name: "cfddidafter",
            label: "存放地点(后)",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getAssetsPlaceAllList",
                valueName: "recordid",
                textName: "cfddmc"
            },
			 must: true
        },
        {
            type: "select",//
            name: "sybuidafter",//
            label: "使用部门(后)",//
            placeholder: "请输入使用部门(后)",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getDepartmentSelectAllList",
                valueName: "bmbh",
                textName: "bmmc"
            },
            must: true
        },
        {
            type: "text",//
            name: "syrafter",//接口字段名
            label: "使用人",//
            placeholder: "请输入使用人",
        },

        {
            type: "select",
            name: "pdjgdm",
            label: "盘点结果",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getInventorySelectAllList",
                valueName: "dmbh",
                textName: "dmnr"          
            },
            must: true
        },
        {
            type: "hidden",//
            name: "copestate",//
            label: "处理状态",//
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getCopeStateSelectAllList",
                valueName: "dmbh",
                textName: "dmnr",
            },
            immutableAdd:true
        },
        {
            type: "textarea",//
            name: "clfa",//
            label: "处理方案",//
            placeholder: "请输入处理方案"
        },
        {
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入备注"
        },
        {
            type: "upload",//
            name: "imageList",//
            label: "资产图片",//
            btnName: "添加图片",
			projectName:"zj-assets",
            fileType: ["jpg", "jpeg", "png", "gif"]
        }
    ],
    onSave: function (obj, _params) {
        //     console.log( '参数是：',_params)
            l.ajax('batchAddInventoryHaveTitle', _params, function (data) {
                pager.page('remote');
                obj.close()
            })
    },

    onAdd: function (obj, _params) {       
        } 
});


$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table);
    // console.log('传入的数据是是',checkedData)
    var name = $(this).attr("data-name");
    var copeState;
    if(checkedData[0]){
        copeState=checkedData[0].copestate;
    }else{
        copeState='';
    }
    switch (name) {
        case "add":
            if(checkedData.length > 1){
                layer.alert("只能选择一项", { icon: 0 }, function (index) {
                    layer.close(index);
                });  
            }else if(checkedData.length == 0){
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                }); 
            }else if(!checkedData[0].pdrq){
				if(!checkedData[0].inventoryTitle){
					 addDetailLayer.open(checkedData[0]);
				}else{					
					 taskAddDetailLayer.open(checkedData[0]);
				}
               
            }else{
                layer.alert("资产已盘点过", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            }
            break;
        case "batchAdd":
            if(checkedData.length == 0){
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                }); 
            }else{
                var newData = [{
                    recordList:[],
                    sszcList:[],
                    sszclx2Name:checkedData[0].sszclx2Name,
					inventoryTitle:checkedData[0].inventoryTitle,
                    
                }];
                for(var i=0;i<checkedData.length;i++){
                    if(checkedData[i].pdrq){
                        layer.alert("资产已盘点过", { icon: 0 }, function (index) {
                            layer.close(index);
                        });
                        return;	
                    }else  if(checkedData[i].inventoryTitle !== checkedData[0].inventoryTitle){
                        layer.alert("盘点任务标题不一致，请重新选择", { icon: 0 }, function (index) {
                            layer.close(index);
                        });
                        return;
                    }else{
                        newData[0].recordList.push(checkedData[i].recordid);
                        newData[0].sszcList.push(checkedData[i].sszcid);

                        if(i == checkedData.length-1){
							
							
							if(checkedData[0].inventoryTitle != null){
								taskBatchAddDetailLayer.open(newData[0]);
							}else{
								 batchAddLayer.open(newData[0]);
							}
                        }
                    }
                }
            }
            break;
        case "edit":
            if(copeState=='2'){
                layer.alert("资产已被处理过！不能编辑了。", { icon: 0}, function (index) {
                    layer.close(index);
                });
                return;
            }
            if (checkedData.length == 1) {
                if(checkedData[0].pdrq){
                    // checkedData[0].isAdd=false;					
					 detailLayer.open(checkedData[0]);	                   
                }else{
                    layer.alert("资产未盘点不能编辑", { icon: 0 }, function (index) {
                        layer.close(index);
                    });
                }
            } else if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.alert("只能选择一个", { icon: 0}, function (index) {
                    layer.close(index);
                });
            }
            break;
        case "handle":
            // console.log('选择的数据是：',checkedData)
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            }else{
                for(var i= 0;i<checkedData.length;i++){
                    // console.log(checkedData[i])
                    if(checkedData[i].copestate== "2"){
                        layer.alert("所选盘点记录包含了已处理的记录，请重新选择", { icon: 0 }, function (index) {
                            layer.close(index);
                        });
                        star = 1;
                        break;
                    }else if(checkedData[i].copestate== ""){
                        layer.alert("资产未盘点不能处理", { icon: 0 }, function (index) {
                            layer.close(index);
                        });
                        star = 1;
                        break;
                    }else{
                        star="";
                    }
                }
            if(star == "1"){
            }else{
                layer.alert("<label><input name='copeState' type='radio' id='inputState1' value=''>更新资产管理列表</label><br><label><input name='copeState' type='radio' id='inputState2' value=''>资产归位</label> ", function (index) {
                    if(document.getElementById("inputState1").checked){
                        layer.alert("执行此操作后将同步更新资产管理列表中该资产信息。确定？",{ icon: 0 }, function (index) {
                            // console.log('发送的数据是：',{currentList:checkedData})
                            l.ajax('updateAssetDetails',{currentList:checkedData},function(res){
                                layer.alert("处理成功！", { icon: 0 }, function (index) {
                                    layer.close(index);
                                    pager.page('remote')
                                });
                            })

                        })
                    }else if(document.getElementById("inputState2").checked){
                        // console.log('发送的数据是：',{currentList:checkedData})
                        l.ajax('updateInventoryState',{currentList:checkedData},function(res){
                            layer.alert("处理成功！", { icon: 0 }, function (index) {
                                layer.close(index);
                                pager.page('remote')
                            });
                        })
                    }
                });
                }
            }
            break;
        case "derive"://导出
            layer.alert("确定导出数据吗？", { icon: 0, }, function (index) {
                l.ajax('inventoryExportExcel',checkedData[0],function(res){
                    layer.alert("导出成功！", { icon: 0 }, function (index) {
                        layer.close(index);
                        window.location.href=res;
                        // console.log(res)
                    })
                })
            });
            break;
		case "notice"://盘点通知	
		       layer.confirm("确定发布盘点任务？", { icon: 0, }, function (index) {
                    l.ajax("inventoryNotice", checkedData[0], function () {
                        pager.page('remote')
                    })
                    layer.close(index);
                });		
/* 		        layer.alert("确定发布盘点任务？", { icon: 0, }, function (index) {
                l.ajax('inventoryNotice',checkedData[0],function(){
                    layer.alert("通知成功！", { icon: 0 }, function (index) {
                        layer.close(index);
                        // console.log(res)
                    })
                })
            }); */
            break;
    }
});