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
            "data": "unitName",//接口对应字段
            "title": "单位名称",//表头名
            "defaultContent": "-",//默认显示 
        },
        {
            "targets": [3],//第几列
            "data": "name",//接口对应字段
            "title": "姓名",//表头名
            "defaultContent": "-",//默认显示 
        },
        {
            "targets": [4],//第几列
            "data": "sex",//接口对应字段
            "title": "性别",//表头名
            "defaultContent": "-",//默认显示 
            render: function (data) {
                if (data === '0') {
                    return '男'
                } else {
                    return '女'
                }
            }
        },
        {
            "targets": [5],//第几列
            "data": "birth",//接口对应字段
            "title": "出生日期",//表头名
            "defaultContent": "-",//默认显示 
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd");
            }
        },
        {
            "targets": [6],//第几列
            "data": "national",//接口对应字段
            "title": "民族",//表头名
            "defaultContent": "-",//默认显示 
        }, {
            "targets": [7],//第几列
            "data": "nativePlace",//接口对应字段
            "title": "籍贯",//表头名
            "defaultContent": "-",//默认显示 
        },
        {
            "targets": [8],//第几列
            "data": "education",//接口对应字段
            "title": "学历",//表头名
            "defaultContent": "-",//默认显示 
        },
        {
            "targets": [9],//第几列
            "data": "school",//接口对应字段
            "title": "毕业院校",//表头名
            "defaultContent": "-",//默认显示 
        },
        {
            "targets": [10],//第几列
            "data": "major",//接口对应字段
            "title": "所属专业",//表头名
            "defaultContent": "-",//默认显示 
        },
        {
            "targets": [11],//第几列
            "data": "employDept",//接口对应字段
            "title": "入聘部门",//表头名
            "defaultContent": "-",//默认显示 
        },
        {
            "targets": [12],//第几列
            "data": "deptNum",//接口对应字段
            "title": "聘入部门现有人员数量",//表头名
            "defaultContent": "-",//默认显示 
        },
        {
            "targets": [13],//第几列
            "data": "hireThat",//接口对应字段
            "title": "聘用说明",//表头名
            "defaultContent": "-",//默认显示 
        },
        {
            "targets": [14],//第几列
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
                        return "<textarea class='textareas' onblur='myblur(\"" + rowData.personInfoId + "\",\"" + rowData.opinionField1 + "\",1,$(this))'>" + data + "</textarea>";
                    } else {
                        return data
                    }
                }
                
                
            }
        },
        {
            "targets": [15],//第几列
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
                        return "<textarea class='textareas' onblur='myblur(\"" + rowData.personInfoId + "\",\"" + rowData.opinionField2 + "\",2,$(this))'>" + data + "</textarea>";
                    } else {
                        return data
                    }
                }
                
                
            }
        }
    ]
});
var params = {};
if (l.getUrlParam('personApproveId')) {
    params = {
        personApproveId: l.getUrlParam('personApproveId')
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
        url: l.getApiUrl("getZjFlowPersonInfoList"),
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
            name: "personApproveId",//输入字段名
            defaultValue: l.getUrlParam('personApproveId')
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "personInfoId",//输入字段名
        },

        {
            type: "text",//
            name: "unitName",//
            label: "单位名称",//
            placeholder: "请输入",
            must: true
        },
        {
            type: "text",//
            name: "name",//
            label: "姓名",//
            placeholder: "请输入",
            must: true
        },
        {
            type: "select",//
            name: "sex",//
            label: "性别",//
            must: true,
            selectList: [
                {
                    text: "男",
                    value: "0"
                },
                {
                    text: "女",
                    value: "1"
                }
            ]
        },
        {
            type: "date",//
            name: "birth",//
            label: "出生日期",//
            placeholder: "请输入",
            // must: true
        },
        {
            type: "text",//
            name: "national",//
            label: "民族",// 
            placeholder: "请输入",
            must: true
        },
        {
            type: "text",// 
            name: "nativePlace",//接口对应字段
            label: "籍贯",//表头名
            placeholder: "请输入",
            // must: true
        },
        {
            type: "text",
            name: "education",
            label: "学历",
            must: true
        },
        {
            type: "text",
            name: "school",
            label: "毕业院校"
        },
        {
            type: "text",
            name: "major",
            label: "所属专业",
            must: true
        },
        {
            type: "text",
            name: "employDept",
            label: "入聘部门",
            must: true
        },
        {
            type: "number",
            name: "deptNum",
            label: "聘入部门现有人员数量"
        },
        {
            type: "textarea",
            name: "hireThat",
            label: "聘用说明",
            must: true
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateZjFlowPersonInfo', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        if (!(/^\+?[0-9][0-9]*$/.test(_params.deptNum))) {
            layer.alert("请输入正整数!", { icon: 0, }, function (index) {
                layer.close(index);
            });
            return false;
        } else {
            l.ajax('addZjFlowPersonInfo', _params, function (data) {
                pager.page('remote')
                obj.close()
            })
        }
    }
})

function myblur(personInfoId, text, falg, textarea) {
    if (text.split('\n').join('').split('\r').join('') != textarea.val().split('\n').join('').split('\r').join('')) {
        var params = {
            
        };
        if (falg == '1') {
            params.personInfoId = personInfoId,
            params.opinionField1 =textarea.val()
        } else {
            params.personInfoId = personInfoId,
            params.opinionField2 =textarea.val()
        }
        l.ajax("approveZjFlowPersonInfo", params, function () {

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
                    l.ajax("batchDeleteUpdateZjFlowPersonInfo", checkedData, function () {
                        pager.page('remote')
                    })
                    layer.close(index);
                });
            }
            break;
    }
})
