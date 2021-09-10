var recordid = l.getUrlParam('id') || "";
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
            "data": "supMatter",//接口对应字段
            "title": "监督事项",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "scheduleTime",//接口对应字段
            "title": "计划实施时间",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "remarks",//接口对应字段
            "title": "备注",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "createUserName",//接口对应字段
            "title": "操作者",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "createTime",//接口对应字段
            "title": "更新时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return data ? l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss") : "-";
            }
        }
    ]
});


var detailForm = $('#detailForm').detailLayer({
    conditions: [
        {
            type: "text",//五种形式：text,select,date,hidden,textarea,
            name: "number",//输入字段名
            label: "编号",
            must: true          
        },			
        {
            type: "pickTree",//
            name: "oaDutyDep",//接口字段名
            label: "监督责任部门",//
            must: true,
            pickType: {
                department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
                member: false,//成员列表对应接口字段名,false表示不开启该类
            }
        },
        {
            type: "text",
            name: "supObj",
            label: "监督对象",
            must: true
        }  
    ],
    onSave: function (obj, _params) {
        if (recordid) {
            _params.yearSupPlanId = recordid
            l.ajax("updateZjJdjhYearSupPlan", _params, function (data, message, success) {
                if (success) {
                    layer.alert(message, { icon: 0, }, function (index) {
                        parent.pager.page('remote')
                        layer.close(index);
                        $("#tab-system").Huitab({
                            index: 1
                        });
                    });
                }
            })
        } else {
            l.ajax("addZjJdjhYearSupPlan", _params, function (data, message, success) {
                if (success) {
                    recordid = data.yearSupPlanId
                    layer.alert(message, { icon: 0, }, function (index) {
                        parent.pager.page('remote')
                        layer.close(index);

                        $("#tab-system").Huitab({
                            index: 1
                        });

                        loadPage()
                    });
                }
            })
        }
    }
})

detailForm.close = function () {
    parent.layer.close(parent.myOpenLayer)
}
loadPage()
function loadPage() {
    if (recordid) {
        l.ajax("getZjJdjhYearSupPlanDetails", { yearSupPlanId: recordid }, function (data, message, success) {
            if (success) {
                detailForm.setOpenData(data)
            }
        })
    } else {
        detailForm.setOpenData({ memberList: { oaMemberList: [] } })
    }
}





//检查项
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjJdjhYearSupMatterList"),
        params: {
            yearSupPlanId: recordid
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
		layerArea:['70%', '90%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "yearSupPlanId",//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "yearSupMatterId",//输入字段名
        },       
        {
            type: "textarea",//
            name: "supMatter",//
            label: "监督事项",//
            placeholder: "请输入监督事项",
        },
		{
            type: "text",//
            name: "scheduleTime",//
            label: "计划实施时间",//
            placeholder: "请输入计划实施时间",
        },
		{
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入备注",
        }
    ],
    onSave: function (obj, _params) {
        l.ajax("updateZjJdjhYearSupMatter", _params, function (data, message, success) {
            if (success) {
                pager.page('remote', { yearSupPlanId: recordid })
                layer.alert(message, { icon: 0, }, function (index) {
                    layer.close(index);
                    obj.close()
                });
            }
        })
    },
    onAdd: function (obj, _params) {
        l.ajax("addZjJdjhYearSupMatter", _params, function (data, message, success) {
            if (success) {
                pager.page('remote', { yearSupPlanId: recordid })
                layer.alert(message, { icon: 0, }, function (index) {
                    layer.close(index);
                    obj.close()
                });
            }
        })
    },
})
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            if (recordid) {
                detailLayer.open({ isAdd: true, yearSupPlanId: recordid })
            } else {
                layer.alert("请先切换到基本信息完善必填信息点击确认后再新增检查项！", { icon: 0, }, function (index) {
                    layer.close(index);
                    $("#tab-system").Huitab({
                        index: 0
                    });
                });
            }
            break;
        case "edit":
            if (checkedData.length == 1) {
                detailLayer.open(checkedData[0])
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
                    l.ajax("batchDeleteUpdateZjJdjhYearSupMatter", checkedData, function (data, success, message) {
                        if (success) {
                            pager.page('remote')
                        }
                    })
                    layer.close(index);
                });
            }
            break;
        case "close":
            parent.layer.close(parent.myOpenLayer)
            break;

    }
})
$("#tab-system").Huitab({
    index: 0
});
