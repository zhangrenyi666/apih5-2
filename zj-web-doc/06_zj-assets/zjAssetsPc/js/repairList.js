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
            "data": "zcmc",//接口对应字段
            "title": "资产名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "synx",//接口对应字段
            "title": "规定使用年限（年）",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "bxts",//接口对应字段
            "title": "保修天数（天）",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "bxrq",//接口对应字段
            "title": "报修日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd");
            }
        },
        {
            "targets": [6],//第几列
            "data": "wxztName",//接口对应字段
            "title": "维修状态",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "wxf",//接口对应字段
            "title": "维修方",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [8],//第几列
            "data": "wxfy",//接口对应字段
            "title": "维修费用（元）",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [9],//第几列
            "data": "gzsm",//接口对应字段
            "title": "故障说明",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [10],//第几列
            "data": "wxrq",//接口对应字段
            "title": "完成日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                if (data != null) {
                    return l.dateFormat(new Date(data), "yyyy-MM-dd");
                }
            }
        },
        {
            "targets": [11],//第几列
            "data": "modifyUserName",//接口对应字段
            "title": "操作员",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [12],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "操作时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "zcmc",//输入字段名
            label: "资产名称",//输入字段对杨的中文名称
            placeholder: "请输入资产名称",
        },
        {
            type: "select",
            name: "wxzt",
            label: "维修状态",
            ajax: {//如果需要接口获取数据需要添加该属性
                api: "getMaintainStateSelectAllList",//api名称
                valueName: "dmbh",//设置value对应的接口字段名称
                textName: "dmnr",//设置text对应的接口字段名称
            },
        },
        {
            type: "text",//三种形式：text,select,date,
            name: "wxf",//输入字段名
            label: "维修方",//输入字段对杨的中文名称
            placeholder: "请输入维修方",
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
        pager.page('remote', 0, _params)
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
        url: l.getApiUrl("getRepairList"),
        params: {},
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
var detailLayer = $('#detailLayer').detailLayer({
    layerArea: ['100%', '100%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "recordid",//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "sszcid",//输入字段名
        },
        {
            type: "text",//
            name: "zcmc",//
            label: "资产名称",//
            placeholder: "请输入资产名称",
            immutable: true,
        },
        {
            type: "number",//
            name: "synx",//
            label: "规定使用年限（年）",//
            placeholder: "请输入规定使用年限（年）",
            immutable: true,
        },
        {
            type: "number",//
            name: "bxts",//
            label: "保修天数（天）",//
            placeholder: "请输入保修天数（天）",
            immutable: true
        },
        {
            type: "date",//
            name: "bxrq",//
            label: "报修日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id: "bxrq",
            must: true
        },
        {
            type: "select",
            name: "wxztdm",
            label: "维修状态",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getMaintainStateSelectAllList",
                valueName: "dmbh",
                textName: "dmnr"
            },
            must: true
        },
        {
            type: "text",//
            name: "wxf",//
            label: "维修方",//
            placeholder: "请输入维修方",
            must: true
        },
        {
            type: "number",//
            name: "wxfy",//
            label: "维修费用（元）",//
            placeholder: "请输入维修费用（元）",
            max: 999999999
        },
        {
            type: "textarea",//
            name: "gzsm",//
            label: "故障说明",//
            placeholder: "请输入故障说明",
        },
        {
            type: "date",//
            name: "wxrq",//
            label: "完成日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id: "wxrq",
        },
        {
            type: "upload",//
            name: "imageList",//
            label: "资产图片",//
            btnName: "添加图片",
            projectName: "zj-assets",
            fileType: ["jpg", "jpeg", "png", "gif"]
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateRepair', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addRepair', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    }
})
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
                l.delTableRow("recordid", 'currentList', 'batchDeleteRepair', checkedData, function (data) {
                    pager.page('remote')
                })//  删除url地址
            }
            break;
        case "derive"://导出
            layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
                l.ajax('assetsExportExcel', {}, function (res) {
                    layer.alert("导出成功！", { icon: 0 }, function (index) {
                        layer.close(index);
                        window.location.href = res;
                        // console.log("导出数据是：",res)
                    })
                })
            });
            break;
    }
})