var code = Lny.getUrlParam('code');
Lny.setCookie('code', code, 30);

// supervisionCompanyId

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
            "title": "标题",//表头名
            "data": function (row) {
                return row
            },//接口对应字段
            "defaultContent": "-",//默认显示
            "render": function (data, index, row) {
                var a;
                if (data) {
                    a = '<a style="color:blue;"  onclick="myOpen(\'督导详情\',\'' + data.supervisionId + '\',\'' + 'supervisionDetail' + '\')" href="javascript:void(0);">' + data.title + '</a>'
                }
                return a;
            }
        },
        {
            "targets": [3],//第几列
            "data": "content",//接口对应字段
            "title": "内容",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "state",//接口对应字段
            "title": "状态",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                var text = ""
                switch (data) {
                    case "0": text = "已发布";
                        break;
                    case "1": text = "未发布";
                        break;
                    case "2": text = "发布失败";
                        break;
                    default: text = "未知";
                        break;
                }
                return text
            }
        },
        {
            "targets": [5],//第几列
            "width": 300,
            "data": "createUserName",//接口对应字段
            "title": "创建者",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "width": 300,
            "data": "createTime",//接口对应字段
            "title": "创建时间",//表头名
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
            "title": "操作",//表头名
            "render": function (data) {
                var html = '';
                html += '<span class="dropDown">'
                html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">操作</a>';
                html += '<ul class="dropDown-menu menu radius box-shadow">'
                html += '<li><a href="javascript:void(0);" onclick="myOpen1(' + data.rowIndex + ')">发布</a></li>';
                html += '<li><a href="javascript:void(0);" onclick="myOpen(\'统计签字\',\'' + data.supervisionId + '\',\'' + 'signatureList' + '\')">统计签字</a></li>';
                html += '</ul></span>'
                return html;
            }
        }
    ]
});

var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "title",//输入字段名
            label: "督导标题",//输入字段对杨的中文名称
            placeholder: "请输入督导标题",
        },
        {
            type: "select",
            name: "state",
            label: "状态",
            selectList: [//当类型为select时，option配置
                {
                    value: "",
                    text: "全部"
                },
                {
                    value: "0",
                    text: "已发布"
                },
                {
                    value: "1",
                    text: "未发布"
                }
            ],
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

var allData;
var pager = $("#pager").page({
    remote: {// ajax请求配置
        url: l.getApiUrl("getZjDdSupervisionList"),
        params: {

        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                allData = data;
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

var addDetailLayer = $('#addDetailLayer').detailLayer({
    layerArea: ['100%', '100%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "supervisionId",//输入字段名
        },
        {
            type: "text",//
            name: "title",//
            label: "标题",//
            placeholder: "请输入标题",
            must: true
        },
        {
            type: "text",//
            name: "content",//
            label: "内容",//
            placeholder: "请输入内容",
            must: true
        },
        {
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入备注",
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateZjDdSupervision', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjDdSupervision', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    }
});

$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            addDetailLayer.open();
            break;
        case "edit":
            if (checkedData.length == 1) {
                addDetailLayer.open(checkedData[0]);
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
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.confirm("确定删除？", { icon: 0, }, function (index) {
                    l.ajax("batchDeleteUpdateZjDdSupervision", checkedData, function () {
                        pager.page('remote')
                    })
                    layer.close(index);
                });
            }
            break;
        case "send"://发布督导   getFileListForWechatByUserId   sendSupervision
            if (checkedData.length == 1) {
                layer.confirm("确定发布？", { icon: 0, }, function (index) {
                    l.ajax("sendSupervision", checkedData[0], function () {
                        pager.page('remote')
                    })
                    layer.close(index);
                });
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

function myOpen1(index, type) {
    var rowData = allData[Number(index)];
    layer.confirm("确定发布？", { icon: 0, }, function (index) {
        l.ajax("sendSupervision", rowData, function () {
            pager.page('remote')
        })
        layer.close(index);
    });
}

var myOpenLayer;
function myOpen(title, id, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?supervisionId=" + id
    }
    myOpenLayer = layer.open(options)
    layer.full(myOpenLayer)
}
