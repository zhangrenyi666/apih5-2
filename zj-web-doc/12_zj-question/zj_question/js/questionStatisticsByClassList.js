var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
var table = $('#table').DataTable({
    "info": false,// 是否显示数据信息
    "paging": false,// 是否开启自带分页
    "ordering": false, // 是否开启DataTables的高度自适应
    "processing": false,// 是否显示‘进度’提示
    "searching": false,// 是否开启自带搜索
    "autoWidth": false,// 是否监听宽度变化
    "columnDefs": [// 表格列配置
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
            "targets": [2],// 第几列
            "data": "projectName",// 接口对应字段
            "title": "项目",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [3],// 第几列
            "data": "questionClassName",// 接口对应字段
            "title": "类别",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [4],// 第几列
            "data": "questionTitleName",// 接口对应字段
            "title": "检查项",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [5],// 第几列
            "data": "questionNum",// 接口对应字段
            "title": "出现次数",// 表头名
            "defaultContent": "-",// 默认显示
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [// 表单项配置
        {
            type: "text",//
            name: "projectName",//
            label: "项目",//
            placeholder: "请输入问题标题",
        }
    ],
    onSearch: function (arr) {// 搜索按钮回调
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
        pager.page('remote', 0, _params)
    },
    onReset: function (arr) {// 重置按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
        pager.page('remote', _params)
    }
})
var pager = $("#pager").page({
    remote: {// ajax请求配置
        url: l.getApiUrl("getQuestionStatisticsListGroupByClassName"),
        params: {
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
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    var params;
    switch (name) {
        case "export"://导出
            var params = {};
            params.schemeNumber = $('[name = "schemeNumber"]').val();
            params.unitName = $('[name = "unitName"]').val();
            layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
                l.ajax('xmQuestionGroupByClassNameExport', params, function (res) {
                    layer.alert("导出成功！", { icon: 0 }, function (index) {
                        layer.close(index);
                        window.location.href = res;
                    })
                })
            });
            break;
    }
})