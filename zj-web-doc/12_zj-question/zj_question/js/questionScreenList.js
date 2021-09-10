var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
var id = Apih5.getUrlParam('id')
var table = $('#table').DataTable({
    "info": false,//是否显示数据信息
    "paging": false,//是否开启自带分页
    "ordering": false, //是否开启DataTables的高度自适应
    "processing": false,//是否显示‘进度’提示
    "searching": false,//是否开启自带搜索
    "autoWidth": false,//是否监听宽度变化
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
            "data": "questionYear",// 接口对应字段
            "title": "年份",// 表头名
            "width": 30,
            "defaultContent": "-",// 默认显示
        },          
        {
            "targets": [3],// 第几列
            "data": "departmentName",// 接口对应字段
            "title": "部门",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [4],// 第几列
            "data": "questionClassName",// 接口对应字段
            "title": "类别名称",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [5],// 第几列
            "data": "questionCheckItemName",// 接口对应字段
            "title": "检查项",// 表头名
            "defaultContent": "-",// 默认显示
        }
    ]
});

var form = $('#form').filterfrom({
    maxLength: 3,
    conditions: [//表单项配置
        {
            type: "select",
            name: "questionYear",
            label: "问题年份",					
            selectList: [//当类型为select时，option配置
             ],
            ajax: {
            api: "getQuestionYearSelectList",
            valueName: "questionYear",
            textName: "className"
        },
     },	        
        {
            type: "text",//
            name: "departmentName",//
            label: "部门",//
            placeholder: "请输入部门",
        },
        {
            type: "text",//
            name: "questionClassName",//
            label: "类别名称",//
            placeholder: "请输入类别名称",
        },
        {
            type: "text",//
            name: "questionCheckItemName",//
            label: "检查项",//
            placeholder: "请输入检查项",
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
        url: l.getApiUrl("getZjXmNoTitleQuestionList"),
        params: {
            questionTitleId: id
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


//////////////////////////////////////////////第二个table////////////////////////////////////////////////////////////////////////
var table2 = $('#table2').DataTable({
    "info": false,//是否显示数据信息
    "paging": false,//是否开启自带分页
    "ordering": false, //是否开启DataTables的高度自适应
    "processing": false,//是否显示‘进度’提示
    "searching": false,//是否开启自带搜索
    "autoWidth": false,//是否监听宽度变化
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
            "data": "questionYear",// 接口对应字段
            "width": 30,            
            "title": "年份",// 表头名
            "defaultContent": "-",// 默认显示
        },        
        {
            "targets": [3],// 第几列
            "data": "departmentName",// 接口对应字段
            "title": "部门",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [4],// 第几列
            "data": "questionClassName",// 接口对应字段
            "title": "类别名称",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [5],// 第几列
            "data": "questionCheckItemName",// 接口对应字段
            "title": "检查项",// 表头名
            "defaultContent": "-",// 默认显示
        }
    ]
});

var form2 = $('#form2').filterfrom({
    maxLength: 3,
    conditions: [//表单项配置  
        {
            type: "select",
            name: "questionYear",
            label: "问题年份",					
            selectList: [//当类型为select时，option配置
             ],
            ajax: {
            api: "getQuestionYearSelectList",
            valueName: "questionYear",
            textName: "className"
        },
     },	        
        {
            type: "text",//
            name: "departmentName",//
            label: "部门",//
            placeholder: "请输入部门",
        },
        {
            type: "text",//
            name: "questionClassName",//
            label: "类别名称",//
            placeholder: "请输入类别名称",
        },
        {
            type: "text",//
            name: "questionCheckItemName",//
            label: "检查项",//
            placeholder: "请输入检查项",
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
        pager2.page('remote', _params)
    },
    onReset: function (arr) {//重置按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
        pager2.page('remote', _params)
    }
})

var pager2 = $("#pager2").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjXmHasTitleQuestionList"),
        params: {
            questionTitleId: id
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                $.each(data, function (index, item) {
                    item.rowIndex = index
                })
                table2.clear().rows.add(data).draw();
            } else {
                layer.alert(result.message, { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
        },
    }
});
$("body").on("click", "button", function () {
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            var checkedData = l.getTableCheckedData(table)
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
                var body = {
                    zjXmQuestionScreenList: checkedData,
                    questionTitleId: id,
                    //recordid:Apih5.getUrlParam("id"),
                }
                //console.log(body)
                l.ajax("batchAddZjXmQuestionScreen", body, function (data) {
                    layer.alert("添加成功！", { icon: 1, }, function (index) {
                        layer.close(index);
                    });
                    pager.page('remote')
                    pager2.page('remote')
                })
            }
            break;
        case "del":
            var checkedData = l.getTableCheckedData(table2)
            //console.log(checkedData)
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
            else {
                l.ajax("batchDeleteUpdateZjXmQuestionScreen", checkedData, function () {
                    layer.alert("移除成功！", { icon: 1, }, function (index) {
                        layer.close(index);
                    });
                    pager.page('remote')
                    pager2.page('remote')
                })
            }
            break;
    }
})