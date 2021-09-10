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
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "专家姓名",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data, index, row) {
                var a;
                if (data) {
                    a = '<a style="color:blue;"  onclick="myOpen(\'已审批方案查看\',\'' + data.objectUserKey + '\',\'' + 'prAlreadyHandledProgrammeList' + '\',\'' +1+ '\')" href="javascript:void(0);">' + data.objectName + '</a>'
                }
                return a;
            }
        },		
		{
            "targets": [3],//第几列
            'data': 'noteName',
            "title": "专家职称",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [4],//第几列
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "待审批方案（个）",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data, index, row) {
                var a;
                if (data) {
                    a = '<a style="color:blue;"  onclick="myOpen(\'待办方案查看\',\'' + data.objectUserKey + '\',\'' + 'getSchemeWaitForHandlingList' + '\',\'' +1+ '\')" href="javascript:void(0);">' + data.totalReview + '</a>'
                }
                return a;
            }
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "objectName",//输入字段名
            label: "专家名称",//输入字段对杨的中文名称
            placeholder: "请输入专家名称",
        },
		{
            type: "select",
            name: "noteName",
            label: "专家职称",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getTemplateNoteAllList",
                valueName: "noteName",
                textName: "noteName"
            },
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
        url: l.getApiUrl("getSchemeWaitForHandlingReviewerList"),
        params: {
            projectName: "",
            schemeNumber: "",
			schemeName:"",
			schemeLevel: "",
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
    checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "export"://导出
            var params = {};
            layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
                l.ajax('schemeReviewerExportExcel', params, function (res) {
                    layer.alert("导出成功！", { icon: 0 }, function (index) {
                        layer.close(index);
                        window.location.href = res;
                    })
                })
            });
            break;
}
})

var myOpenLayer;
function myOpen(title, id, url,flowTemplateId) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id + "&code=" + code + "&flowTemplateId=" + flowTemplateId
    }
    myOpenLayer = layer.open(options)
    layer.full(myOpenLayer)
}
