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
            "data": "dictKey",//接口对应字段
            "title": "字典Key",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "dictGroupKey",//接口对应字段
            "title": "字典组key",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "dictName",//接口对应字段
            "title": "字典名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "dictValue1",//接口对应字段
            "title": "字典值1",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "dictValue2",//接口对应字段
            "title": "字典值2",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "dictValue3",//接口对应字段
            "title": "字典值3",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [8],//第几列
            "data": "dictValue4",//接口对应字段
            "title": "字典值4",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [9],//第几列
            "data": "dictValue5",//接口对应字段
            "title": "字典值5",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [10],//第几列
            "data": "dictValue6",//接口对应字段
            "title": "字典值6",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [11],//第几列
            "data": "dictValue7",//接口对应字段
            "title": "字典值7",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [12],//第几列
            "data": "dictValue8",//接口对应字段
            "title": "字典值8",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [13],//第几列
            "data": "dictValue9",//接口对应字段
            "title": "字典值9",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [14],//第几列
            "data": "dictValue10",//接口对应字段
            "title": "字典值10",//表头名
            "defaultContent": "-",//默认显示
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "dictKey",//输入字段名
            label: "字典Key",//输入字段对杨的中文名称
            placeholder: "请输入字典Key",
        },
        {
            type: "text",//三种形式：text,select,date,
            name: "dictGroupKey",//输入字段名
            label: "字典组key",//输入字段对杨的中文名称
            placeholder: "请输入字典组key",
        },
        {
            type: "text",//三种形式：text,select,date,
            name: "dictName",//输入字段名
            label: "字典名称",//输入字段对杨的中文名称
            placeholder: "请输入字典名称",
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
        pager.page('remote', _params)
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
        url: l.getApiUrl("getTzDictionaryList"),
        params: {
            dictKey: "",
            dictGroupKey: "",
            dictName: "",
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
    conditions: [
        {
            type: "text",//
            name: "dictKey",//
            label: "字典Key",//
            placeholder: "请输入字典Key",
            must: true
        },
        {
            type: "text",//
            name: "dictGroupKey",//
            label: "字典组key",//
            placeholder: "请输入字典组key",
            must: true
        },
        {
            type: "text",//
            name: "dictName",//
            label: "字典名称",//
            placeholder: "请输入字典名称",
            must: true
        },
        {
            type: "text",//
            name: "dictValue1",//
            label: "字典值1",//
            placeholder: "请输入字典值1",
        },
        {
            type: "text",//
            name: "dictValue2",//
            label: "字典值2",//
            placeholder: "请输入字典值2",
        },
        {
            type: "text",//
            name: "dictValue3",//
            label: "字典值3",//
            placeholder: "请输入字典值3",
        },
        {
            type: "text",//
            name: "dictValue4",//
            label: "字典值4",//
            placeholder: "请输入字典值4",
        },
        {
            type: "text",//
            name: "dictValue5",//
            label: "字典值5",//
            placeholder: "请输入字典值5",
        },
        {
            type: "text",//
            name: "dictValue6",//
            label: "字典值6",//
            placeholder: "请输入字典值6",
        },
        {
            type: "text",//
            name: "dictValue7",//
            label: "字典值7",//
            placeholder: "请输入字典值7",
        },
        {
            type: "text",//
            name: "dictValue8",//
            label: "字典值8",//
            placeholder: "请输入字典值8",
        },
        {
            type: "text",//
            name: "dictValue9",//
            label: "字典值9",//
            placeholder: "请输入字典值9",
        },
        {
            type: "text",//
            name: "dictValue10",//
            label: "字典值10",//
            placeholder: "请输入字典值10",
        },
    ],
    onSave: function (obj, _params) {
        l.ajax('updateTzDictionary', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addTzDictionary', _params, function (data) {
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
                l.ajax("batchDeleteTzDictionary", checkedData, function () {
                    pager.page('remote')
				})
            }
            break;
    }
})
function myOpen(title, id, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id
    }
    layer.full(layer.open(options))
}
function public(voteId) {//发布
    l.ajax('sendVoteInfo', { voteId: voteId }, function (data, message) {
        layer.alert(message, { icon: 0, }, function (index) {
            layer.close(index);
            pager.page('remote')
        });
    })
}
function preview(voteId) {//预览
    window.open(l.domainName + 'initMVotePreview.do?voteId=' + voteId + '&previewFlag=1', 'newwindow', 'height=500,width=350,top=100,left=500,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no')
}
