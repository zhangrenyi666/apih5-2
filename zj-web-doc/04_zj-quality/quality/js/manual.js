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
            "data": "specTitle",//接口对应字段
            "title": "手册标题",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "specStd",//接口对应字段
            "title": "手册标准",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "0":
                        r = "国标"
                        break
                    case "1":
                        r = "行标"
                        break
                }
                return r
            }
        },
        {
            "targets": [4],//第几列
            "data": "specNumber",//接口对应字段
            "title": "实施号",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "specDate",//接口对应字段
            "title": "实施日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd");
            }
        },
        {
            "targets": [6],//第几列
            "data": "specLevel",//接口对应字段  
            "title": "手册分类",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return data ? (data.specLevelName ? data.specLevelName : "") : ""
            }
        },
        {
            "targets": [7],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "更新时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                if (data) {
                    return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
                } else {
                    return "-";
                }

            }
        },
        {
            "targets": [8],//第几列
            "data": "modifyUser",//接口对应字段
            "title": "更新者",//表头名
            "defaultContent": "-",//默认显示
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "specTitle",//输入字段名
            label: "手册标题",//输入字段对杨的中文名称
            placeholder: "请输入手册标题",
        },
        {
            type: "hidden",//
            name: "specType",//
            defaultValue:'0',
        },
        {
            type: "select",//
            name: "specLevelId",//
            label: "手册分类",
            placeholder: "请输入手册地址",
            must: true,
            ajax: {//如果需要接口获取数据需要添加该属性
                api: "getSpecLevelSelect",//api名称
                valueName: "specLevelId",//设置value对应的接口字段名称
                textName: "specLevelName",//设置text对应的接口字段名称
            }
        },
        {
            type: "select",
            name: "specStd",
            label: "手册标准",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 0,//输入字段的值
                    text: "国标",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "行标",//显示中文名
                    selected: false//默认是否选中
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
        pager.page('remote',0, _params)
    },
    onReset: function (arr) {//重置按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
        pager.page('remote',0, _params)
    }
})
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getSpecBgList"),
        params: {
            specTitle: "",
            specStd: "",
            specLevelId: "",
            specType:'1'
        },
        success: function (result) {
            ///Lny.log(result.data)
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
            name: "specId",//输入字段名
        },
        {
            type: "text",//
            name: "specTitle",//
            label: "手册标题",//
            placeholder: "请输入手册标题",
            must: true
        },
        {
            type: "hidden",//
            name: "specType",//
            defaultValue:'1',
            // label: "规范区分",
        },
        {
            type: "text",//
            name: "specNumber",//
            label: "实施号",//
            placeholder: "请输入实施号",
            must: true
        },
        {
            type: "date",//text,select,date,
            name: "specDate",
            label: "实施日期",
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            // minDate: "\'#F{\\\'%y-%M-%d\\\'}\'",
            id: "logmax",
            must: true
        },
        {
            type: "select",
            name: "specStd",
            label: "手册标准",
            selectList: [//当类型为select时，option配置
                {
                    value: 0,//输入字段的值
                    text: "国标",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "行标",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        },
        {
            type: "select",//
            name: "specLevelId",//
            label: "手册分类",
            placeholder: "请输入手册地址",
            must: true,
            ajax: {//如果需要接口获取数据需要添加该属性
                api: "getSpecLevelBgList",//api名称
                valueName: "specLevelId",//设置value对应的接口字段名称
                textName: "specLevelName",//设置text对应的接口字段名称
            }
        },
        {
            type: "upload",//
            name: "specAccessoryList",
            label: "文件上传",//
            btnName: "上传文件",
            maxLen: 1,
            must: true,
            fileType: ["pdf", "xls", "doc", "pdfx", "docx", "xlsx"]
        }
    ],
    onSave: function (obj, _params) {
        //    Lny.log(_params)
        var new_params = {}
        for (var key in _params) {
            if (_params.hasOwnProperty(key)) {
                if (key == "specAccessoryList") {
                    if(_params[key].length == 0){
                        layer.alert('请上传文件！');
                        return;
                    }
                    new_params[key] = [{ accessoryAddress: _params[key][0].fileUrl, accessoryName: _params[key][0].fileName }];
                } else {
                    new_params[key] = _params[key]
                }
            }
        }
        //     Lny.log(new_params)
        l.ajax('updateSpec', new_params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        var new_params = {}
        for (var key in _params) {
            if (_params.hasOwnProperty(key)) {
                if (key == "specAccessoryList") {
                    if(_params[key].length == 0){
                        layer.alert('请上传文件！');
                        return;
                    }
                    new_params[key] = [{ "accessoryAddress": _params[key][0].fileUrl, "accessoryName": _params[key][0].fileName }];
                } else {
                    new_params[key] = _params[key]
                }
            }
        }
        Lny.log('参数是：',new_params)
        l.ajax('addSpec', new_params, function (data) {
            //console.log('数据是',data)
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
                var newCheckedData = {}
                for (var key in checkedData[0]) {
                    if (checkedData[0].hasOwnProperty(key)) {
                        if (key == "specAccessoryList") {
                            newCheckedData[key] = [{ fileUrl: checkedData[0][key][0].accessoryAddress, fileName: checkedData[0][key][0].accessoryName }]
                        } else {
                            newCheckedData[key] = checkedData[0][key];
                        }
                    }
                }
                detailLayer.open(newCheckedData);
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

            if (checkedData.length == 1) {
                //   Lny.log(checkedData)
                l.delTableRowById("specId", 'deleteSpec', checkedData[0], function (data) {
                    pager.page('remote')
                })//  删除url地址
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
