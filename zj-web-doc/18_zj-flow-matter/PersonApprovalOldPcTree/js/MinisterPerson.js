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
            "data": "name",//接口对应字段
            "title": "姓名",//表头名
            "defaultContent": "-",//默认显示 
        },
        {
            "targets": [3],//第几列
            "data": "category",//接口对应字段
            "title": "人员类别",//表头名
            "defaultContent": "-",//默认显示 
        },
        {
            "targets": [4],//第几列
            "data": "originalPosition",//接口对应字段
            "title": "原职务",//表头名
            "defaultContent": "-",//默认显示 
        },
        {
            "targets": [5],//第几列
            "data": "appDept",//接口对应字段
            "title": "申请部门",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "appLevel",//接口对应字段
            "title": "申请级别",//表头名
            "defaultContent": "-",//默认显示 
        }, {
            "targets": [7],//第几列
            "data": "basicCondition",//接口对应字段
            "title": "基本条件",//表头名
            "defaultContent": "-",//默认显示 
        },
        {
            "targets": [8],//第几列
            "data": "opinionField1",//接口对应字段
            "title": "人力资源部资格初审意见",//表头名
            "defaultContent": "-",//默认显示 
            render: function (data, display, rowData) {
                if (l.getUrlParam('status') === 'Done') {
                    return data
                } else {
                    if (l.getUrlParam('nodeName') === '发起人') {
                        return data
                    } else if(l.getUrlParam('nodeName') === '人力资源初审'){
                        return "<textarea class='textareas' onblur='myblur(\"" + rowData.ministerInfoId + "\",\"" + rowData.opinionField1 + "\",1,$(this))'>" + data + "</textarea>";
                    } else {
                        return data
                    }
                }
                
                
            }
        },
        {
            "targets": [9],//第几列
            "data": "opinionField2",//接口对应字段
            "title": "审批意见",//表头名
            "defaultContent": "-",//默认显示 
            render: function (data, display, rowData) {
                if (l.getUrlParam('status') === 'Done') {
                    return data
                } else {
                    if (l.getUrlParam('nodeName') === '发起人') {
                        return data
                    } else if (l.getUrlParam('nodeName') === '公司主管领导') {
                        return "<textarea class='textareas' onblur='myblur(\"" + rowData.ministerInfoId + "\",\"" + rowData.opinionField2 + "\",2,$(this))'>" + data + "</textarea>";
                    } else {
                        return data
                    }
                }
                
                
            }
        }
    ]
});
var params = {};
if (l.getUrlParam('ministerApproveId')) {
    params = {
        ministerApproveId: l.getUrlParam('ministerApproveId')
    };
    // $('#shenpi').css('display', 'none');
} else if (l.getUrlParam('workId')) {
    params = {
        workId: l.getUrlParam('workId')
    };
    $('#save').css('display', 'none');
    $('#delete').css('display', 'none');
    $('#update').css('display', 'none');
}
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjFlowMinisterInfoList"),
        params: params,
        success: function (result) {
            if (result.success) {
                var data = result.data;
                if (result.data.length > 0) {
                    if (result.data[0].apih5FlowStatus == 1 || result.data[0].apih5FlowStatus == 2) {
                        $("#save").hide();
                        $("#update").hide();
                        $("#delete").hide();
                    }
                }

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
    layerArea: ["100%", "100%"],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "ministerApproveId",//输入字段名
            defaultValue: l.getUrlParam('ministerApproveId')
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "ministerInfoId",//输入字段名
        },
        {
            type: "text",//
            name: "name",//
            label: "姓名",//
            placeholder: "请输入",
            must: true
        },
        {
            type: "text",//
            name: "category",//
            label: "人员类别",//
            placeholder: "请输入",
            must: true
        },
        {
            type: "text",//
            name: "originalPosition",//
            label: "原职务",//
            placeholder: "请输入",
            must: true
        },
        {
            type: "text",//
            name: "appDept",//
            label: "申请部门",//
            placeholder: "请输入",
            must: true
        },
        {
            type: "text",//
            name: "appLevel",//
            label: "申请级别",//
            placeholder: "请输入",
            must: true
        },
        {
            type: "text",//
            name: "basicCondition",//
            label: "基本条件",//
            placeholder: "请输入",
            must: true
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateZjFlowMinisterInfo', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjFlowMinisterInfo', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    }
})

function myblur(ministerInfoId, text, falg, textarea) {
    if (text.split('\n').join('').split('\r').join('') != textarea.val().split('\n').join('').split('\r').join('')) {
        var params = {
            
        };
        if (falg == '1') {
            params.ministerInfoId = ministerInfoId,
            params.opinionField1 =textarea.val()
        } else {
            params.ministerInfoId = ministerInfoId,
            params.opinionField2 =textarea.val()
        }
        l.ajax("approveZjFlowMinisterInfo", params, function () {

        })
    }
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
        case "goback":
            window.history.go(-1)
            break;
        case "del":
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.confirm("确定删除？", { icon: 0, }, function (index) {
                    l.ajax("batchDeleteUpdateZjFlowMinisterInfo", checkedData, function () {
                        pager.page('remote')
                    })
                    layer.close(index);
                });
            }
            break;
    }
})
