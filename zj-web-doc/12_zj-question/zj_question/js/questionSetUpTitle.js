var code = Apih5.getUrlParam('code')
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
            "targets": [1],//第几列
            "data": "title",//接口对应字段
            "title": "主题",//表头名
            "defaultContent": "2018年度问题整改",//默认显示
        },
        {
            "targets": [2],//第几列
            "data": "year",//接口对应字段
            "title": "年度",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "frequency",//接口对应字段
            "title": "次别",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "remarks",//接口对应字段
            "title": "备注",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "createUserName",//接口对应字段
            "title": "操作员",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "操作时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [7],//第几列
            "width": 80,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "添加问题",//表头名
            "render": function (data) {
                var html = '';
                html += '<span class="dropDown">'
                html += '<a style="color:blue;" href="javascript:void(0);" onclick="myOpen(\'问题筛选列表\',\'' + data.titleId + '\',\'' + 'questionScreenList' + '\',\'' + data.inventoryTitle + '\')">编辑清单</a>';
                html += '</span>'
                return html;
            }
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "meetingRoomTypeName",//输入字段名
            label: "主题",//输入字段对杨的中文名称
            placeholder: "请输入标题",
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
        url: l.getApiUrl("getZjXmQuestionTitleList"),
        params: {
            meetingRoomTypeName: "",
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
var detailLayer = $('#detailLayer').detailLayer({
    layerArea: ['50%', '60%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "titleId",//输入字段名
        },
        {
            type: "text",//
            name: "title",//
            label: "主题",//
            placeholder: "请输入标题",
            must: true,
            immutable: true
        },
        {
            type: "text",//
            name: "year",//
            label: "年度",//
            placeholder: "请输入年度",
            must: true,
            immutable: true
        },
        {
            type: "text",//
            name: "frequency",//
            label: "次别",//
            placeholder: "请输入次数",
            must: true,
            immutable: true
        },
        {
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入备注",

        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateZjXmQuestionTitle', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjXmQuestionTitle', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    }
})
function myOpen(title, id, url, titlen) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id + "&titlen=" + titlen + "&code=" + code
    }
    layer.full(layer.open(options))
}
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
                layer.confirm("确定删除？", { icon: 0, }, function (index) {
                    l.ajax("batchDeleteUpdateZjXmQuestionTitle", checkedData, function () {
                        pager.page('remote')
                    })
                    layer.close(index);
                });
            }
            break;
    }
})