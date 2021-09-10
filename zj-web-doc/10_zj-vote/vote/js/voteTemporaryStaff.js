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
            "data": "realName",// 接口对应字段
            "title": "外部人员",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [2],// 第几列
            "data": "mobile",// 接口对应字段
            "title": "手机号",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [3],// 第几列
            "data": "departmentName",// 接口对应字段
            "title": "所属部门",// 表头名
            "defaultContent": "-",// 默认显示
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "realName",//输入字段名
            label: "外部人员",//输入字段对杨的中文名称
            placeholder: "请输入外部人员"
        },
        {
            type: "text",//三种形式：text,select,date,
            name: "departmentName",//输入字段名
            label: "所属部门",//输入字段对杨的中文名称
            placeholder: "请输入所属部门"
        }        
    ],
    onSearch: function (arr) {//搜索按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].name == "startDate" || arr[i].name == "endDate") {
                if (arr[i].value) {
                    _params[arr[i].name] = l.dateFormat(new Date(new Date(arr[i].value).getTime()),"yyyy-MM-dd");
                } else {
                    _params[arr[i].name] = "";
                }
            } else {
                _params[arr[i].name] = arr[i].value;
            }
        }
        pager.page('remote',0,_params)
    },
    onReset: function (arr) {//重置按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
        pager.page('remote',_params)
    }
})
var pager = $("#pager").page({
    remote: {// ajax请求配置
        url: l.getApiUrl("getZjVoteTemporaryStaffListByCondition"),
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
	layerArea:['50%', '40%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "userKey",//输入字段名
        },
        {
            type: "text",//
            name: "realName",//
            label: "外部人员",//
            placeholder: "请输入外部人员",
            must: true	
        },
        {
            type: "text",//
            name: "mobile",//
            label: "手机号码",//
            placeholder: "请输入手机号码",
            must: true,
        },                
        {
            type: "pickTree",
            name: "departmentList",
            label: "所属部门",
            pickType: {
                department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
                member: false//成员列表对应接口字段名,false表示不开启该类
            },
            must: true
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateZjVoteTemporaryStaff', _params, function (data) {
            layer.alert("修改成功！", { icon: 1, }, function (index) {
                pager.page('remote')
                layer.close(index);
                obj.close()
            });
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjVoteTemporaryStaff', _params, function (data) {
            layer.alert("新增成功！", { icon: 1, }, function (index) {
                pager.page('remote')
                layer.close(index);
                obj.close()
            });
            })
    }
})

var editLayer = $('#editLayer').detailLayer({
	layerArea:['50%', '40%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "userKey",//输入字段名
        },
        {
            type: "text",//
            name: "realName",//
            label: "外部人员",//
            placeholder: "请输入外部人员",
            must: true	
        },
        {
            type: "text",//
            name: "mobile",//
            label: "手机号码",//
            placeholder: "请输入手机号码",
            must: true,
            immutableAdd:true,	            
        },                
        {
            type: "pickTree",
            name: "departmentList",
            label: "所属部门",
            pickType: {
                department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
                member: false//成员列表对应接口字段名,false表示不开启该类
            },
            must: true
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateZjVoteTemporaryStaff', _params, function (data) {
            layer.alert("修改成功！", { icon: 1, }, function (index) {
                pager.page('remote')
                layer.close(index);
                obj.close()
            });
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjVoteTemporaryStaff', _params, function (data) {
            layer.alert("新增成功！", { icon: 1, }, function (index) {
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
                editLayer.open(checkedData[0]);
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
                    l.ajax("batchDeleteUpdateZjVoteTemporaryStaff", checkedData, function () {
                        layer.alert("删除成功！", { icon: 1, }, function (index) {
                            pager.page('remote')
                            layer.close(index);
                        });
                    })
                    layer.close(index);
                });
            }
            break;
        // case "import"://导入
        //     leading.open();
        //     break;
        // case "derive"://导出
        //     var params = {}
        //     params.department = $('[name = "department"]').val();
        //     layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
        //         l.ajax('exportQuestionClassToExcel', params, function (res) {
        //             layer.alert("导出成功！", { icon: 0 }, function (index) {
        //                 layer.close(index);
        //                 window.location.href = res;
        //             })
        //         })
        //     });
        //     break;
        // case "download"://导入	   
        //     window.location.href = "http://localhost:8080/web/template/zj-question/问题导入模板.xlsx";//			
        //     break;
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