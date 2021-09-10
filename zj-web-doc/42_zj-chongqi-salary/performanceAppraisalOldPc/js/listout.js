var code = Apih5.getUrlParam('code')
Apih5.setCookie('code',code,30)
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
            "data": "departmentName",//接口对应字段 
            "title": "部门",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "departmentLeaderName",//接口对应字段
            "title": "副总师、经理助理和部门正、副职",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "projectLeaderName",//接口对应字段
            "title": "所属部门正职",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "userName",//接口对应字段
            "title": "部门负责人",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "chargeLeaderName",//接口对应字段
            "title": "分管领导",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "executiveLeaderName",//接口对应字段
            "title": "主管领导",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [8],//第几列
            "data": "remarks",//接口对应字段
            "title": "备注",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [9],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "更新时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",
            name: "departmentName",
            label: "部门",
            placeholder: "请输入部门",
        }
    ],
    onSearch: function (arr) {
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
                _params[arr[i].name] = arr[i].value;
        }
        pager.page('remote', _params)
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
        url: l.getApiUrl("getZjXmCqjxDepartmentHeadList"),
        params: {
			// sarsType:"1"
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                $.each(data,function (index,item) {
                    item.rowIndex = index
                })
                table.clear().rows.add(data).draw();
                
            } else {
                layer.alert(result.message,{ icon: 0,},function (index) {
                    layer.close(index);
                });
            }
            
        }
    }
});
var detailLayer = $('#detailLayer').detailLayer({
    layerArea: ["60%","70%"],
    conditions: [
        {
            type: "hidden",
            name: "departmentHeadId",
        },
        {
            type: "pickTree",
            name: "department",
            label: "部门",
            pickType: {
                department: "oaDepartmentList",// 部门列表对应接口字段名,false表示不开启该类
                member: false,// 成员列表对应接口字段名,false表示不开启该类
            },
            must: true
        },
        {
            type: "pickTree",
            name: "departmentLeader",
            label: "副总师、经理助理和部门正、副职",
            // must: true,
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            }
        },
        {
            type: "pickTree",
            name: "projectLeader",
            label: "所属部门正职",
            // must: true,
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            }
        },
        {
            type: "pickTree",
            name: "departmentHead",
            label: "部门负责人",
            // must: true,
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            }
        },
        {
            type: "pickTree",
            name: "chargeLeader",
            label: "分管领导",
            // must: true,
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            }
        },
        {
            type: "pickTree",
            name: "executiveLeader",
            label: "主管领导",
            // must: true,
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            }
        },
        {
            type: "textarea",
            name: "remarks",
            label: "备注",
            placeholder: "请输入"
        }
       
    ],
    onSave: function (obj,_params) {
        l.ajax('updateZjXmCqjxDepartmentHead',_params,function (_d, _m, _s) {
            if (_s) {
                pager.page('remote')
                obj.close()
            }
        })
    },
    onAdd: function (obj,_params) {
        l.ajax('addZjXmCqjxDepartmentHead',_params,function (_d, _m, _s) {
            if (_s) {
                pager.page('remote')
                obj.close()
            }
        })
    }
})

$("body").on("click","button",function () {
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
                layer.alert("未选择任何项",{ icon: 0,},function (index) {
                    layer.close(index);
                });
            } else {
                layer.alert("只能选择一个",{ icon: 0,},function (index) {
                    layer.close(index);
                });
            }
            break;
        case "del":
            if (checkedData.length == 0) {
                layer.alert("未选择任何项",{ icon: 0,},function (index) {
                    layer.close(index);
                });
            } else {
                for(var i = 0; i < checkedData.length; i++){
                    if(i === checkedData.length - 1){
                        layer.confirm("确定删除？",{ icon: 0,},function (index) {
                            l.ajax("batchDeleteUpdateZjXmCqjxDepartmentHead",checkedData,function () {
                                pager.page('remote')
                            })
                            layer.close(index);
                        });
                    }
                }
            }
            break;
    }
})
