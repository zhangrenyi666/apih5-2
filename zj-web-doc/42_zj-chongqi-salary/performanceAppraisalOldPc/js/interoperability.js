var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
var selectDataList = [
    {
        value:new Date(((new Date().getFullYear() - 2) + '/' + (new Date().getMonth() + 1) + '/' + new Date().getDate())).getTime(),
        text:(new Date().getFullYear() - 2).toString()
    },
    {
        value:new Date(((new Date().getFullYear() - 1) + '/' + (new Date().getMonth() + 1) + '/' + new Date().getDate())).getTime(),
        text:(new Date().getFullYear() - 1).toString()
    },
    {
        value:new Date(((new Date().getFullYear()) + '/' + (new Date().getMonth() + 1) + '/' + new Date().getDate())).getTime(),
        text:new Date().getFullYear().toString()
    },
    {
        value:new Date(((new Date().getFullYear() + 1) + '/' + (new Date().getMonth() + 1) + '/' + new Date().getDate())).getTime(),
        text:(new Date().getFullYear() + 1).toString()
    },
    {
        value:new Date(((new Date().getFullYear() + 2) + '/' + (new Date().getMonth() + 1) + '/' + new Date().getDate())).getTime(),
        text:(new Date().getFullYear() + 2).toString()
    },
    {
        value:new Date(((new Date().getFullYear() + 3) + '/' + (new Date().getMonth() + 1) + '/' + new Date().getDate())).getTime(),
        text:(new Date().getFullYear() + 3).toString()
    }
];
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
            "data": "collaborationTitle",//接口对应字段
            "title": "标题",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data, _, rowData) {
                var html = '<a style="color:blue;" onclick="myOpen(\'协作性评分详情\',\'' + encodeURI(JSON.stringify(rowData)) + '\',\'' + 'interoperabilityDetail' + '\')">' + data + '</a>';
                return html;
            }
        },
        {
            "targets": [3],//第几列
            "data": "collaborationYears",//接口对应字段
            "title": "考核年度",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy");
            }
        },
        {
            "targets": [4],//第几列
            "data": "collaborationQuarter",//接口对应字段
            "title": "考核季度",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                if (data === '0') {
                    return '第一季度';
                } else if (data === '1') {
                    return '第二季度';
                } else if (data === '2') {
                    return '第三季度';
                } else if (data === '3') {
                    return '第四季度';
                } else if (data === '4') {
                    return '上半年度';
                } else if (data === '5') {
                    return '下半年度';
                } else {
                    return '--';
                }
            }
        },
        // {
        //     "targets": [5],//第几列
        //     "data": "assessmentType",//接口对应字段
        //     "title": "考核类别",//表头名
        //     "defaultContent": "-",//默认显示
        //     "render": function (data) {
        //         if (data === '1') {
        //             return '副总师';
        //         } else if (data === '2') {
        //             return '部门正、副职';
        //         } else if (data === '3') {
        //             return '员工';
        //         } else {
        //             return '--';
        //         }
        //     }
        // },
        {
            "targets": [5],//第几列
            "data": "collaborationState",//接口对应字段
            "title": "考核状态",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                if (data === '0') {
                    return '未评分';
                } else if (data === '1') {
                    return '评分中';
                } else if (data === '2') {
                    return '评分完成';
                } else {
                    return '--';
                }
            }
        },
        {
            "targets": [6],//第几列
            "data": "collaborationRemarks",//接口对应字段
            "title": "备注",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "操作时间",//表头名
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
            name: "disciplineTitle",
            label: "标题",
            placeholder: "请输入标题",
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
        url: l.getApiUrl("getZjXmCqjxCollaborationAssessmentList"),
        params: {
            // sarsType:"1"
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

        }
    }
});
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "send":
            if (checkedData.length > 0) {
                layer.confirm("确定发起吗？", { icon: 0, }, function (index) {
                    if(checkedData[0].collaborationState != '0'){
                        layer.alert("不能重复发起！", { icon: 0, }, function (index) {
                            layer.close(index);
                        });
                    }else{
                        l.ajax("zjXmCqjxCollaborationAssessmentSendMessage", checkedData, function () {
                            pager.page('remote')
                        })
                        layer.close(index);
                    }                    
                });
            } else {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
            break;
        case "add":
            detailLayer.open();
            break;
            case "del":
                if (checkedData.length == 0) {
                    layer.alert("未选择任何项",{ icon: 0,},function (index) {
                        layer.close(index);
                    });
                } else {
                    for(var i = 0; i < checkedData.length; i++){
                        if(checkedData[i].collaborationState != '0'){
                            layer.alert("所选中的数据包含已发起的数据，无法删除！", { icon: 0, }, function (index) {
                                layer.close(index);
                            });
                            break;
                        }else if(i === checkedData.length - 1){
                            layer.confirm("确定删除？",{ icon: 0,},function (index) {
                                l.ajax("batchDeleteUpdateZjXmCqjxCollaborationAssessment",checkedData,function () {
                                    pager.page('remote')
                                })
                                layer.close(index);
                            });
                        } 
                    }                   
                }
                break;            
        case "edit":
            if (checkedData.length == 1) {
                if(checkedData[0].collaborationState != '0'){
                    layer.alert("已发起的数据无法编辑", { icon: 0, }, function (index) {
                        layer.close(index);
                    });
                }else{
                    detailLayer.open(checkedData[0]);
                }
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
var detailLayer = $('#detailLayer').detailLayer({
    layerArea: ["80%","80%"],
    conditions: [
        {
            type: "hidden",
            name: "collaborationId",
        },
		/*
        {
            type: "pickTree",
            name: "assessmentRange",
            label: "考核范围",
            pickType: {
                department: "oaDepartmentList",// 部门列表对应接口字段名,false表示不开启该类
                member: false,// 成员列表对应接口字段名,false表示不开启该类
            },
            must: true
        },
		*/
        {
            type: "pickTree",
            name: "assessmentPersonnel",
            label: "考核人员",
            pickType: {
                department: false,// 部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList",// 成员列表对应接口字段名,false表示不开启该类
            },
            must: true
        },
        {
            type: "text",
            name: "collaborationTitle",
            label: "标题",
            placeholder: "请输入",
            must: true
        },
        {
            type: "select",
            name: "collaborationYears",
            label: "年度",
            placeholder: "请选择",
            defaultValue:new Date(((new Date().getFullYear()) + '/' + (new Date().getMonth() + 1) + '/' + new Date().getDate())).getTime(),
            selectList: selectDataList,
            must: true
        },
        {
            type: "select",
            name: "collaborationQuarter",
            label: "季度",
            placeholder: "请选择",
            must: true,
            selectList: [
                {
                    value:"0",
                    text:"第一季度"
                },
                {
                    value:"1",
                    text:"第二季度"
                },
                {
                    value:"2",
                    text:"第三季度"
                },
                {
                    value:"3",
                    text:"第四季度"
                },
                {
                    value:"4",
                    text:"上半年度"
                },
                {
                    value:"5",
                    text:"下半年度"
                }
            ]
        },
        {
            type: "date",//
            name: "closingDate",//
            label: "评分截止日期",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id:"closingDate",
            must: true          
        },           
        {
            type: "textarea",
            name: "collaborationRemarks",
            label: "备注",
            placeholder: "请输入"
        }
    ],
    onSave: function (obj,_params) {
        if(_params.assessmentYears){
            _params.assessmentYears = parseInt(_params.assessmentYears);
        }
        l.ajax('updateZjXmCqjxCollaborationAssessment',_params,function (_d, _m, _s) {
            if (_s) {
                pager.page('remote')
                obj.close()
            }
        })
    },
    onAdd: function (obj,_params) {
        if(_params.assessmentYears){
            _params.assessmentYears = parseInt(_params.assessmentYears);
        }
        l.ajax('addZjXmCqjxCollaborationAssessment',_params,function (_d, _m, _s) {
            if (_s) {
                pager.page('remote')
                obj.close()
            }
        })
    }
})
var myOpenLayer;
function myOpen(title, rowData, url) {
    var rowData = JSON.parse(decodeURI(rowData));
    var options = {
        type: 2,
        title: title,
        area: ['100%', '100%'],
        content: url + ".html?code=" + code + '&collaborationId=' + rowData.collaborationId
    };
    myOpenLayer = layer.open(options);
}
