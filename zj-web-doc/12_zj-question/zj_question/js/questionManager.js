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
            "targets": [1],// 第几列
            "data": "classId",// 接口对应字段
            "title": "编号",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [2],// 第几列
            "data": "questionYear",// 接口对应字段
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
            "data": "className",// 接口对应字段
            "title": "类别名称",// 表头名
            "defaultContent": "-",// 默认显示
        },
        // {
        //     "targets": [5],// 第几列
        //     "data": "remarks",// 接口对应字段
        //     "title": "备注",// 表头名
        //     "defaultContent": "-",// 默认显示
        // },
        {
            "targets": [5],// 第几列
            "data": "modifyUserName",// 接口对应字段
            "title": "操作人",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [6],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "创建时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [7],//第几列
            "width": 100,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名
            "render": function (data) {
                var html = '';
                html += '<span class="dropDown">'
                html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" onclick="myOpen(\'编辑检查项\',\'' + data.recordid + '\',\'' + data.departmentName + '\',\'' + data.className + '\',\'' + 'addQuestion' + '\')">编辑检查项</a>';
                html += '</span>'
                return html;
            }
        }
    ]
});
//判断当前用户是否问题管理人员
l.ajax('getQuestionManagementFlag', {}, function (res) {
    //console.log(res)
    if (res == "1") {
        $("#add").hide();
        $("#edit").hide();
        $("#del").hide();
        $("#import").hide();
        $("#download").hide();
    }
})
var form = $('#form').filterfrom({
    conditions: [// 表单项配置
        {
            type: "text",//
            name: "departmentName",//
            label: "部门",//
            placeholder: "请输入部门",
        },
        {
            type: "text",//
            name: "className",//
            label: "类别名称",//
            placeholder: "请输入类别名称",
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
        url: l.getApiUrl("getZjQuestionClassList"),
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

var detailLayer = $('#detailLayer').detailLayer({
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "recordid",//输入字段名
        },
        {
            type: "hidden",//
            name: "classId"//
        },
        {
            type: "pickTree",
            name: "oaDepartment",
            label: "部门",
            pickType: {
                department: "oaDepartmentList",// 部门列表对应接口字段名,false表示不开启该类
                member: false,// 成员列表对应接口字段名,false表示不开启该类
            },
            must: true
        },
        {
            type: "text",//
            name: "questionYear",//
            label: "问题年限",//
            must: true
        },        
        {
            type: "text",//
            name: "className",//
            label: "类别名称",//
            must: true
        },
        {
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateZjQuestionClass', _params, function (data) {
            layer.alert("修改成功！", { icon: 1, }, function (index) {
                pager.page('remote')
                layer.close(index);
                obj.close()
            });
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjQuestionClass', _params, function (data) {
            layer.alert("新增成功", { icon: 1, }, function (index) {
                pager.page('remote')
                layer.close(index);
                obj.close()
            });
        })
    }
})
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    var params;
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
                layer.confirm("确定删除？", { icon: 3, }, function (index) {
                    l.ajax("batchDeleteUpdateZjQuestionClass", checkedData, function () {
                        layer.alert("删除成功！", { icon: 1, }, function (index) {
                            pager.page('remote')
                            layer.close(index);
                        });
                    })
                    layer.close(index);
                });
            }
            break;
        case "import"://导入
            leading.open();
            break;
        case "derive"://导出
            var params = {}
            params.department = $('[name = "department"]').val();
            layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
                l.ajax('exportQuestionClassToExcel', params, function (res) {
                    layer.alert("导出成功！", { icon: 0 }, function (index) {
                        layer.close(index);
                        window.location.href = res;
                    })
                })
            });
            break;
        case "download"://导入	   
            window.location.href = "http://localhost:8080/web/template/zj-question/问题导入模板.xlsx";//			
            break;
    }
})
var leading = $('#leading').detailLayer({
    layerArea: ['410px', '310px'],
    layerTitle: ['导入'],
    conditions: [
        {
            type: "upload",
            maxLen: 1,
            name: "importFileList",
            label: "导入文件",
            btnName: "选择文件",
            projectName: "zj-question",
            fileType: ["xls", "xlsx"],
        }
    ],
    onAdd: function (obj, _params) {
        if (_params.importFileList.length == 0) {
            layer.alert("请选择文件", { icon: 0, }, function (index) {
                layer.close(index);
            });
            return;
        }
        l.ajax('batchImportXmQuestion', { "importFileList": _params.importFileList }, function (_params) {
            pager.page('remote')
            obj.close()
        })
    }
});
function myOpen(title, id, departmentName, className, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id + "&departmentName=" + encodeURI(departmentName) + "&className=" + encodeURI(className) + "&code=" + code
    }
    layer.full(layer.open(options))
}