var code = Lny.getUrlParam('code')
Lny.setCookie('code', code, 30)
﻿var table = $('#table').DataTable({
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
            "data": "gysbh",//接口对应字段
            "title": "供应商编号",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [2],//第几列
            "data": "gysmc",//接口对应字段
            "title": "供应商名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "lxrxm",//接口对应字段
            "title": "联系人",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "lxrdh",//接口对应字段
            "title": "联系电话",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "zt",//接口对应字段
            "title": "状态",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "0":
                        r = "启用"
                        break
                    case "1":
                        r = "停用"
                        break
                }
                return r
            }
        },
        {
            "targets": [6],//第几列
            "data": "modifyUser",//接口对应字段
            "title": "操作员",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
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
            name: "gysbh",//输入字段名
            label: "供应商编号",//输入字段对杨的中文名称
            placeholder: "请输入编号",
        },
        {
            type: "text",//三种形式：text,select,date,
            name: "gysmc",//输入字段名
            label: "供应商名称",//输入字段对杨的中文名称
            placeholder: "请输入名称",
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
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getSupplierList"),
        params: {
            gysmc: "",
            zt: "",
        },
        success: function (result) {
            Lny.log(result)
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
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "recordid",//输入字段名
        },
        {
            type: "text",//
            name: "gysbh",//
            label: "供应商编号",//
            placeholder: "请输入供应商编号",
            must: true,
			immutable:true
        },
        {
            type: "text",//
            name: "gysmc",//
            label: "供应商名称",//
            placeholder: "请输入供应商名称",
            must: true
        },
        {
            type: "text",//
            name: "lxrxm",//
            label: "联系人",//
            placeholder: "请输入联系人",
        },
        {
            type: "text",//
            name: "lxrdh",//
            label: "联系电话",//
            placeholder: "请输入联系电话",
        },
        {
            type: "text",//
            name: "dzyx",//
            label: "电子邮箱",//
            placeholder: "请输入电子邮箱",
        },
       /*  {
            type: "address",
            name: "dzdm",
            label: "地址",
            must: true,
            ajax: {
                api: "getAddressCodeList",
                valueName: "recordid",
                textName: "dzmc",
                childrenName: "addressCodeList",//设置children对应的接口字段名称
            }

        }, */
        {
            type: "text",//
            name: "scxkz",//
            label: "生产许可证",//
            placeholder: "请输入生产许可证",
        },		
		{
            type: "date",//text,select,date,
            name: "scxkzyxqks",
            label: "生产许可证有效期",
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            maxDate: "\'#F{$dp.$D(\\\'logmax\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmin",
        },
        {
            type: "date",//text,select,date,
            name: "scxkzyxqjs",
            label: "至",
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date().setMonth(new Date().getMonth() + 1),
            maxDate: new Date().setFullYear(new Date().getFullYear() + 1),
            minDate: "\'#F{$dp.$D(\\\'logmin\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmax",
        },
        {
            type: "text",//
            name: "jyxkz",//
            label: "经营许可证",//
            placeholder: "请输入经营许可证",
        },
		{
            type: "date",//text,select,date,
            name: "jyxkzyxqks",
            label: "经营许可证有效期",
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            maxDate: "\'#F{$dp.$D(\\\'logmax\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmin",
        },
        {
            type: "date",//text,select,date,
            name: "jyxkzyxqjs",
            label: "至",
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date().setMonth(new Date().getMonth() + 1),
            maxDate: new Date().setFullYear(new Date().getFullYear() + 1),
            minDate: "\'#F{$dp.$D(\\\'logmin\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmax",
        },
        {
            type: "textarea",//
            name: "bz",//
            label: "备注",//
            placeholder: "请输入备注",
        },
        {
            type: "select",
            name: "zt",
            label: "状态",
            selectList: [//当类型为select时，option配置
                {
                    value: 0,//输入字段的值
                    text: "启用",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "停用",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateSupplier', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addSupplier', _params, function (data) {
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
				layer.confirm("确定删除？", { icon: 0,}, function (index) {
                /* l.delTableRow("recordid", 'currentList', 'batchDeleteSupplier', checkedData, function (data) {
                    pager.page('remote')
                })//  删除url地址 */
				l.ajax("batchDeleteSupplier", checkedData, function () {
					pager.page('remote')
				})
				layer.close(index);
            });
            }
            break;
    }
})