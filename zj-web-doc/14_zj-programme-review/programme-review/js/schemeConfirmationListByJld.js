﻿var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
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
            "title": "单位名称",//表头名
            "data": "unitName",//接口对应字段
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "项目名称",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data, index, row) {
                var a;
                if (data) {
                    a = '<a style="color:blue;"  onclick="myOpen(\'方案等级确认\',\'' + data.recordid + '\',\'' + 'schemeConfirmationDetailByJld' + '\')" href="javascript:void(0);">' + data.projectName + '</a>'
                }
                return a;
            }
        },
        {
            "targets": [4],//第几列
            "data": "bureauExamineType",//接口对应字段
            "title": "处理状态",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "0":
                        r = "已驳回"
                        break
                    case "1":
                        r = "已通过"
                        break
                    case "2":
                        r = "未评审"
                        break
                }
                return r
            }
        },
        {
            "targets": [5],//第几列
            "data": "createUserName",//接口对应字段
            "title": "提交人",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "remarks",//接口对应字段
            "title": "提交备注",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "chiefEngineerName",//接口对应字段
            "title": "总工",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [8],//第几列
            "data": "chiefEngineerExamineOpinion",//接口对应字段
            "title": "总工意见",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [9],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "更新时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return data ? l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss") : "-";
            }
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "projectName",//输入字段名
            label: "项目名称",//输入字段对杨的中文名称
            placeholder: "请输入使用单位",
        },
        {
            type: "text",//三种形式：text,select,date,
            name: "unitName",//输入字段名
            label: "单位名称",//输入字段对杨的中文名称
            placeholder: "请输入使用单位",
        },
        {
            type: "select",
            name: "bureauExamineType",
            label: "处理状态",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 0,//输入字段的值
                    text: "已驳回",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "已通过",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 2,//输入字段的值
                    text: "未评审",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        }
    ],
    onSearch: function (arr) {//搜索按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
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
        url: l.getApiUrl("getZjSchemeConfirmationList"),
        params: {
            projectName: "",
            projectState: "",
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





$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "approve":
            if (checkedData.length == 1) {
                myOpen('方案等级确认', checkedData[0].recordid, 'schemeConfirmationDetailByJld')
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
    }
})





var myOpenLayer;
function myOpen(title, id, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id
    }
    myOpenLayer = layer.open(options)
    layer.full(myOpenLayer)
}
