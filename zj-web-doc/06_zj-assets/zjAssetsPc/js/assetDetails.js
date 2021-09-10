var recordid = Lny.getUrlParam('id')
$("#tab-system").Huitab({ index: 0 })
var mainDetailLayer = $('#mainDetailLayer').detailLayer({
    layerArea: ['100%', '100%'],
    customBtnGroup: {
        btns: [],
        callback: function () { }
    },
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
            immutableAdd: true
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
            immutableAdd: true
        },
        {
            type: 'text',//
            name: "zcbh",//
            label: "资产编号",//
            placeholder: "请输入资产编号",
            immutableAdd: true
        },
        {
            type: "text",//
            name: "zcmc",//
            label: "资产名称",//
            placeholder: "请输入资产名称",
            immutableAdd: true
        },
        {
            type: "text",//
            name: "zcyz",//
            label: "资产原值（元）",//
            placeholder: "请输入资产原值（元）",
            immutableAdd: true
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
            immutableAdd: true
        },
        {
            type: "number",//
            name: "synx",//
            label: "使用年限（年）",//
            placeholder: "请输入使用年限（年）",
            immutableAdd: true
        },
        {
            type: "number",//
            name: "bxts",//
            label: "保修天数（月）",//
            placeholder: "请输入保修天数（月）",
            immutableAdd: true
        },
        {
            type: "text",//
            name: "ggxh",//
            label: "规格型号",//
            placeholder: "请输入规格型号",
            immutableAdd: true
        },
        {
            type: "date",//
            name: "grrq",//
            label: "购入日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id: "grrq",
            immutableAdd: true
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
            immutableAdd: true
        },
        {
            type: "hidden",//
            name: "cgbh",//
            label: "采购编号",//
            placeholder: "请输入采购编号",
            immutableAdd: true
        },
        {
            type: "pickTree",
            name: "glrymc",
            label: "管理员",
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            },
            immutableAdd: true
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
            immutableAdd: true
        },
        {
            type: "pickTree",//
            name: "oaSydw",//接口字段名
            label: "使用单位",//
            immutableAdd: true,
            pickType: {
                department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
                member: false,//成员列表对应接口字段名,false表示不开启该类
            }
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
            immutableAdd: true
        },
        {
            type: "text",
            name: "cfddmc",
            label: "存放地点",
            placeholder: "请输入存放地点",
            immutableAdd: true
        },
        {
            type: "text",//
            name: "syr",//接口字段名
            label: "使用人",//
            placeholder: "请输入使用人",
            immutableAdd: true
        },
        {
            type: "date",//
            name: "scrq",//
            label: "出厂日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id: "scrq",
            immutableAdd: true
        },
        {
            type: "text",//
            name: "serialNumber",//
            label: "出厂编号",//
            placeholder: "请输入出厂编号",
            immutableAdd: true
        },
        {
            type: "hidden",//
            name: "nynx",//
            label: "耐用年限（年）",//
            placeholder: "请输入耐用年限（年）",
            immutableAdd: true
        },
        {
            type: "text",//
            name: "gysName",//
            label: "供应商",//
            immutableAdd: true,
            placeholder: "请输入供应商"
        },
        {
            type: "text",//
            name: "rangeName",//
            label: "供应范围",//
            immutableAdd: true,
            placeholder: "请输入供应范围"
        },
        {
            type: "text",//
            name: "brandName",//
            label: "品牌",//
            immutableAdd: true,
            placeholder: "请输入品牌"
        },
        {
            type: "textarea",//
            name: "bz",//
            label: "备注",//
            placeholder: "请输入备注",
            immutableAdd: true
        },
        {
            type: "upload",//
            name: "imageList",//
            label: "资产图片",//
            btnName: "添加图片",
            projectName: "zj-assets",
            fileType: ["jpg", "jpeg", "png", "gif"],
            immutableAdd: true
        }
    ]
})
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
            "title": 'No.',
            "width": 25,
            "render": function (data) {
                return data + 1
            }
        },
        {
            "targets": [1],//第几列
            "data": "subName",//接口对应字段
            "title": "资产名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [2],//第几列
            "data": "subRemarks",//接口对应字段
            "title": "备注",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "modifyUserName",//接口对应字段
            "title": "操作员",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "操作时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        }
    ]
});
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjZcSubassetList"),
        params: {
            mainAssteId: recordid
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
if(recordid){
	l.ajax("getAssetsDetails", { recordid: recordid }, function (data, message, success) {
		if(success){
			mainDetailLayer.setOpenData(data)
		}
	})
}

$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
        case "close":
            parent.layer.close(parent.myOpenLayer)
            break;
    }
})