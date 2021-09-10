var code = Lny.getUrlParam('code')
Lny.setCookie('code', code, 30)
var table = $('#table').DataTable({
    "info": false, //是否显示数据信息
    "paging": false, //是否开启自带分页
    "ordering": false, //是否开启DataTables的高度自适应
    "processing": false, //是否显示‘进度’提示
    "searching": false, //是否开启自带搜索
    "autoWidth": false, //是否监听宽度变化
    "columnDefs": [ //表格列配置
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
            "targets": [2], //第几列
            "data": "documentTitle", //接口对应字段
            "title": "资料标题", //表头名
            "defaultContent": "-", //默认显示
        },
        {
            "targets": [3], //第几列
            "data": "documentType", //接口对应字段
            "title": "资料类型", //表头名
            "defaultContent": "-", //默认显示
            "render": function (data) { //自定义输出
                var r = "未知类型";
                switch (data) {
                    case "0":
                        r = "参考资料"
                        break
                    case "1":
                        r = "规范"
                        break
                    case "2":
                        r = "施工手册"
                        break
                }
                return r
            }
        },
        {
            "targets": [4], //第几列
            "data": "companyName", //接口对应字段
            "title": "公司名称", //表头名
            "defaultContent": "-", //默认显示
        },
        {
            "targets": [5], //第几列
            "data": "userId", //接口对应字段
            "title": "用户ID", //表头名
            "defaultContent": "-", //默认显示
        },
        {
            "targets": [6], //第几列
            "data": "userName", //接口对应字段
            "title": "用户姓名", //表头名
            "defaultContent": "-", //默认显示
        },
        {
            "targets": [7], //第几列
            "data": "createTime", //接口对应字段
            "title": "访问时间", //表头名
            "defaultContent": "-", //默认显示
            "render": function (data) {
                if(data) {
                    return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
                }else {
                    return "-";
                }
            }
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [ //表单项配置
        {
            type: "text", //三种形式：text,select,date,
            name: "documentTitle", //输入字段名
            label: "资料标题", //输入字段对杨的中文名称
            placeholder: "请输入文档资料标题",
        },
        {
            type: "select",//
            name: "companyOaId",//
            label: "公司名称",
            placeholder: "请选择公司名称",
            must: true,
            ajax: {//如果需要接口获取数据需要添加该属性
                api: "getPcBaseCompanySelect",//api名称
                valueName: "oaId",//设置value对应的接口字段名称
                textName: "name",//设置text对应的接口字段名称
            }
        },
        // {
        //     type: "text", //三种形式：text,select,date,
        //     name: "companyName", //输入字段名
        //     label: "公司名称", //输入字段对杨的中文名称
        //     placeholder: "请输入公司名称",
        // },
        {
            type: "text", //三种形式：text,select,date,
            name: "userName", //输入字段名
            label: "用户姓名", //输入字段对杨的中文名称
            placeholder: "请输入访问用户姓名",
        },
        {
            type: "select",
            name: "documentType",
            label: "资料类型",
            selectList: [ //当类型为select时，option配置
                {
                    value: "", //输入字段的值
                    text: "全部", //显示中文名
                    selected: true //默认是否选中
                },
                {
                    value: 0, //输入字段的值
                    text: "参考资料", //显示中文名
                    selected: false //默认是否选中
                },
                {
                    value: 1, //输入字段的值
                    text: "规范", //显示中文名
                    selected: false //默认是否选中
                },
                {
                    value: 2, //输入字段的值
                    text: "施工手册", //显示中文名
                    selected: false //默认是否选中
                }
            ]
        },
        {
            type: "date", //text,select,date,
            name: "startDate",
            label: "起始时间",
            defaultValue: l.dateFormat(new Date(new Date().getTime() - 1000 * 60 * 60 * 24 * 30), "yyyy-MM-dd"),
            maxDate: "\'#F{$dp.$D(\\\'logmax\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmin",
        },
        {
            type: "date", //text,select,date,
            name: "endDate",
            label: "结束时间",
            defaultValue: l.dateFormat(new Date(), "yyyy-MM-dd"),
            minDate: "\'#F{$dp.$D(\\\'logmin\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmax",
        }
    ],
    onSearch: function (arr) { //搜索按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].name == "startDate" || arr[i].name == "endDate") {
                if (arr[i].value) {
                    _params[arr[i].name] = arr[i].name == "endDate" ? l.dateToUnix(arr[i].value) + 1000 * 60 * 60 * 24 : l.dateToUnix(arr[i].value);
                } else {
                    _params[arr[i].name] = "";
                }
            } else {
                _params[arr[i].name] = arr[i].value;
            }
        }
        pager.page('remote',0, _params)
    },
    onReset: function (arr) { //重置按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].name == "startDate" || arr[i].name == "endDate") {
                if (arr[i].value) {
                    _params[arr[i].name] = arr[i].name == "endDate" ? l.dateToUnix(arr[i].value) + 1000 * 60 * 60 * 24 : l.dateToUnix(arr[i].value);
                } else {
                    _params[arr[i].name] = "";
                }
            } else {
                _params[arr[i].name] = arr[i].value;
            }
        }
        pager.page('remote',0, _params)
    }
})
var pager = $("#pager").page({
    remote: { //ajax请求配置
        url: l.getApiUrl("getZjBrowseHistoryList"),
        params: {
            documentTitle: "",
            documentType: "",
            userName: "",
            companyName: "",
            startDate: new Date().getTime() - 1000 * 60 * 60 * 24 * 30,
            endDate: new Date().getTime() + 1000 * 60 * 60 * 24,
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                $.each(data, function (index, item) {
                    item.rowIndex = index
                })
                table.clear().rows.add(data).draw();
            } else {
                layer.alert(result.message, {
                    icon: 0,
                }, function (index) {
                    layer.close(index);
                });
            }
        },
    }
});
var detailLayer = $('#detailLayer').detailLayer({
    conditions: [{
            type: "hidden", //五种形式：text,select,date,hidden,textarea,
            name: "historyId", //输入字段名
        },
        {
            type: "text", //
            name: "documentId", //
            label: "资料id", //
            placeholder: "请输入文档资料id",
            must: true
        },
        {
            type: "text", //
            name: "documentTitle", //
            label: "资料标题", //
            placeholder: "请输入文档资料标题",
            must: true
        },
        {
            type: "select",
            name: "documentType",
            label: "资料类型",
            selectList: [ //当类型为select时，option配置
                {
                    value: 0, //输入字段的值
                    text: "参考资料", //显示中文名
                    selected: false //默认是否选中
                },
                {
                    value: 1, //输入字段的值
                    text: "规范", //显示中文名
                    selected: false //默认是否选中
                },
                {
                    value: 2, //输入字段的值
                    text: "施工手册", //显示中文名
                    selected: false //默认是否选中
                }
            ]
        },
        {
            type: "text", //
            name: "userId", //
            label: "用户ID", //
            placeholder: "请输入访问用户ID",
            must: true
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateZjBrowseHistory', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjBrowseHistory', _params, function (data) {
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
                layer.alert("未选择任何项", {
                    icon: 0,
                }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.alert("只能选择一个", {
                    icon: 0,
                }, function (index) {
                    layer.close(index);
                });
            }
            break;
        case "del":
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", {
                    icon: 0,
                }, function (index) {
                    layer.close(index);
                });
            } else {
                l.ajax("batchDeleteZjBrowseHistory", checkedData, function () {
                    pager.page('remote')
				})
                // l.delTableRow("historyId", 'zjBrowseHistoryList', 'batchDeleteZjBrowseHistory', checkedData, function (data) {
                //     pager.page('remote')
                // }) //  删除url地址
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

function public(voteId) { //发布
    l.ajax('sendVoteInfo', {
        voteId: voteId
    }, function (data, message) {
        layer.alert(message, {
            icon: 0,
        }, function (index) {
            layer.close(index);
            pager.page('remote')
        });
    })
}

function preview(voteId) { //预览
    window.open(l.domainName + 'initMVotePreview.do?voteId=' + voteId + '&previewFlag=1', 'newwindow', 'height=500,width=350,top=100,left=500,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no')
}