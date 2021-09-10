var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
var selectDataList = [
    {
        value: new Date(((new Date().getFullYear() - 2) + '/' + (new Date().getMonth() + 1) + '/' + new Date().getDate())).getTime(),
        text: (new Date().getFullYear() - 2).toString()
    },
    {
        value: new Date(((new Date().getFullYear() - 1) + '/' + (new Date().getMonth() + 1) + '/' + new Date().getDate())).getTime(),
        text: (new Date().getFullYear() - 1).toString()
    },
    {
        value: new Date(((new Date().getFullYear()) + '/' + (new Date().getMonth() + 1) + '/' + new Date().getDate())).getTime(),
        text: new Date().getFullYear().toString()
    },
    {
        value: new Date(((new Date().getFullYear() + 1) + '/' + (new Date().getMonth() + 1) + '/' + new Date().getDate())).getTime(),
        text: (new Date().getFullYear() + 1).toString()
    },
    {
        value: new Date(((new Date().getFullYear() + 2) + '/' + (new Date().getMonth() + 1) + '/' + new Date().getDate())).getTime(),
        text: (new Date().getFullYear() + 2).toString()
    },
    {
        value: new Date(((new Date().getFullYear() + 3) + '/' + (new Date().getMonth() + 1) + '/' + new Date().getDate())).getTime(),
        text: (new Date().getFullYear() + 3).toString()
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
            "data": "assessmentTitle",//接口对应字段
            "title": "标题",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data, _, rowData) {
                var html = '<a style="color:blue;" onclick="myOpen(\'考核详情\',\'' + encodeURI(JSON.stringify(rowData)) + '\',\'' + 'evaluationListPerson' + '\')">' + data + '</a>';
                return html;
            }
        },
        {
            "targets": [3],//第几列
            "data": "assessmentYears",//接口对应字段
            "title": "考核月度",//表头名
            "defaultContent": "-",//默认显示,
            "width": 100,
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM");
            }
        },
        // {
        //     "targets": [4],//第几列
        //     "width": 100,
        //     "data": "assessmentQuarter",//接口对应字段
        //     "title": "考核月度",//表头名
        //     "defaultContent": "-",//默认显示,
        //     "render": function (data) {
        //         if (data === "0") {
        //             return '第一月度'
        //         } else if (data === "1") {
        //             return '第二月度'
        //         } else if (data === "2") {
        //             return '第三月度'
        //         } else if (data === "3") {
        //             return '第四月度'
        //         } else if (data === '4') {
        //             return '第五月度';
        //         } else if (data === '5') {
        //             return '第六月度';
        //         } else if (data === '6') {
        //             return '第七月度';
        //         } else if (data === '7') {
        //             return '第八月度';
        //         } else if (data === '8') {
        //             return '第九月度';
        //         } else if (data === '9') {
        //             return '第十月度';
        //         } else if (data === '10') {
        //             return '第十一月度';
        //         } else if (data === '11') {
        //             return '第十二月度';
        //         } else {
        //             return '--'
        //         }
        //     }
        // },
        {
            "targets": [4],//第几列
            "width": 100,
            "data": "assessmentType",//接口对应字段
            "title": "考核类别",//表头名
            "defaultContent": "-",//默认显示,
            "render": function (data) {
                if (data === "1") {
                    return '领导班子副职'
                } else if (data === "2") {
                    return '项目中层干部'
                } else if (data === "3") {
                    return '员工'
                } else {
                    return '--'
                }
            }
        },
        {
            "targets": [5],//第几列
            "data": "assessmentRemarks",//接口对应字段
            "title": "备注",//表头名
            "width": 100,
            "defaultContent": "-",//默认显示
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",
            name: "assessmentTitle",//输入字段名
            label: "标题",//输入字段对杨的中文名称
            placeholder: "请输入标题",
        }
    ],
    onSearch: function (arr) {//搜索按钮回调
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
        url: l.getApiUrl("getZjXmCqjxProjectAssessmentManageList"),
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
//年度计划弹窗
var yearDetailLayer = $('#yearDetailLayer').detailLayer({
    layerArea: ["100%", "100%"],
    conditions: [
        {
            type: "hidden",
            name: "managerId",
        },
        {
            type: "pickTree",
            name: "assessmentMemberList",
            label: "考核人员",
            must: true,
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            }
        },
        {
            type: "text",
            name: "assessmentTitle",
            label: "标题",
            placeholder: "请输入",
            must: true
        },
        {
            type: "select",
            name: "assessmentType",
            label: "考核类别",
            placeholder: "请选择",
            must: true,
            selectList: [
                {
                    value: "1",
                    text: "副总师、总助"
                },
                {
                    value: "2",
                    text: "部门正、副职"
                },
                {
                    value: "3",
                    text: "员工"
                }
            ],
        },
        {
            type: "select",
            name: "assessmentYears",
            label: "年度",
            placeholder: "请选择",
            defaultValue: new Date(((new Date().getFullYear()) + '/' + (new Date().getMonth() + 1) + '/' + new Date().getDate())).getTime(),
            selectList: selectDataList,
            must: true
        },
        // {
        //     type: "select",
        //     name: "assessmentQuarter",
        //     label: "季度",
        //     placeholder: "请选择",
        //     must: true,
        //     selectList: [
        //         {
        //             value:"0",
        //             text:"第一季度"
        //         },
        //         {
        //             value:"1",
        //             text:"第二季度"
        //         },
        //         {
        //             value:"2",
        //             text:"第三季度"
        //         },
        //         {
        //             value:"3",
        //             text:"第四季度"
        //         },
        //         {
        //             value:"4",
        //             text:"上半年度"
        //         },
        //         {
        //             value:"5",
        //             text:"下半年度"
        //         }
        //     ]
        // },
        {
            type: "date",//
            name: "dutyClosingEndDate",//
            label: "计划填报截止日期结束",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "dutyClosingEndDate",
            must: true
        },
        {
            type: "date",//
            name: "firstDutyClosingEndDate",//
            label: "初步审核截止日期结束",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "firstDutyClosingEndDate",
            must: true
        },
        {
            type: "date",//
            name: "finalDutyClosingEndDate",//
            label: "最终审核截止日期结束",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "finalDutyClosingEndDate",
            must: true
        },
        {
            type: "date",//
            name: "scoreClosingEndDate",//
            label: "考核填报截止日期结束",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "scoreClosingEndDate",
            must: true
        },
        {
            type: "date",//
            name: "firstScoreClosingEndDate",//
            label: "初步评分截止日期结束",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "firstScoreClosingEndDate",
            must: true
        },
        {
            type: "date",//
            name: "finalScoreClosingEndDate",//
            label: "最终评分截止日期结束",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "finalScoreClosingEndDate",
            must: true
        },
        {
            type: "textarea",
            name: "assessmentRemarks",
            label: "备注",
            placeholder: "请输入"
        }
    ],
    onSave: function (obj, _params) {
        if (_params.assessmentYears) {
            _params.assessmentYears = parseFloat(_params.assessmentYears);
        }
        l.ajax('updateZjXmCqjxProjectAssessmentManage', _params, function (_d, _m, _s) {
            if (_s) {
                pager.page('remote')
                obj.close()
            }
        })
    },
    onAdd: function (obj, _params) {
        if (_params.assessmentYears) {
            _params.assessmentYears = parseFloat(_params.assessmentYears);
            _params.assessmentQuarter = "6";
        }
        l.ajax('addZjXmCqjxProjectAssessmentManage', _params, function (_d, _m, _s) {
            if (_s) {
                pager.page('remote')
                obj.close()
            }
        })
    }
})
//月度计划弹窗
var detailLayer = $('#detailLayer').detailLayer({
    layerArea: ["100%", "100%"],
    conditions: [
        {
            type: "hidden",
            name: "managerId",
        },
        {
            type: "pickTree",
            name: "assessmentDeptList",
            label: "考核项目",
            pickType: {
                department: "oaDepartmentList",// 部门列表对应接口字段名,false表示不开启该类
                member: false,// 成员列表对应接口字段名,false表示不开启该类
            },
            must: true
        },
        {
            type: "pickTree",
            name: "assessmentMemberList",
            label: "考核人员",
            must: true,
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            }
        },
        {
            type: "text",
            name: "assessmentTitle",
            label: "标题",
            placeholder: "请输入",
            must: true
        },
        {
            type: "select",
            name: "assessmentType",
            label: "考核类别",
            placeholder: "请选择",
            must: true,
            selectList: [
                {
                    value: "1",
                    text: "领导班子副职"
                },
                {
                    value: "2",
                    text: "项目中层干部"
                },
                {
                    value: "3",
                    text: "员工"
                }
            ],
        },
        {
            type: "select",
            name: "assessmentYears",
            label: "年度",
            placeholder: "请选择",
            defaultValue: new Date(((new Date().getFullYear()) + '/' + (new Date().getMonth() + 1) + '/' + new Date().getDate())).getTime(),
            selectList: selectDataList,
            must: true
        },
        {
            type: "select",
            name: "assessmentQuarter",
            label: "月度",
            placeholder: "请选择",
            must: true,
            selectList: [
                {
                    value: "0",
                    text: "第一月度"
                },
                {
                    value: "1",
                    text: "第二月度"
                },
                {
                    value: "2",
                    text: "第三月度"
                },
                {
                    value: "3",
                    text: "第四月度"
                },
                {
                    value: "4",
                    text: "第五月度"
                },
                {
                    value: "5",
                    text: "第六月度"
                },
                {
                    value: "6",
                    text: "第七月度"
                },
                {
                    value: "7",
                    text: "第八月度"
                },
                {
                    value: "8",
                    text: "第九月度"
                },
                {
                    value: "9",
                    text: "第十月度"
                },
                {
                    value: "10",
                    text: "第十一月度"
                },
                {
                    value: "11",
                    text: "第十二月度"
                }
                // ,
                // {
                //     value: "5",
                //     text: "下半年度"
                // }
            ]
        },
        {
            type: "date",//
            name: "dutyClosingEndDate",//
            label: "计划填报截止日期结束",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "dutyClosingEndDate",
            must: true
        },	
        {
            type: "date",//
            name: "firstDutyClosingEndDate",//
            label: "初步审核截止日期结束",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "firstDutyClosingEndDate",
            must: true
        },
        {
            type: "date",//
            name: "finalDutyClosingEndDate",//
            label: "最终审核截止日期结束",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "finalDutyClosingEndDate",
            must: true
        },
        {
            type: "date",//
            name: "scoreClosingEndDate",//
            label: "考核填报截止日期结束",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "scoreClosingEndDate",
            must: true
        },
        {
            type: "date",//
            name: "firstScoreClosingEndDate",//
            label: "初步评分截止日期结束",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "firstScoreClosingEndDate",
            must: true
        },
        {
            type: "date",//
            name: "finalScoreClosingEndDate",//
            label: "最终评分截止日期结束",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "finalScoreClosingEndDate",
            must: true
        },
        {
            type: "textarea",
            name: "assessmentRemarks",
            label: "备注",
            placeholder: "请输入"
        }
    ],
    onSave: function (obj, _params) {
        if (_params.assessmentYears) {
            _params.assessmentYears = parseFloat(_params.assessmentYears);
        }
        l.ajax('updateZjXmCqjxProjectAssessmentManage', _params, function (_d, _m, _s) {
            if (_s) {
                pager.page('remote')
                obj.close()
            }
        })
    },
    onAdd: function (obj, _params) {
        if (_params.assessmentYears) {
            _params.assessmentYears = parseFloat(_params.assessmentYears);
        }
        l.ajax('addZjXmCqjxProjectAssessmentManage', _params, function (_d, _m, _s) {
            if (_s) {
                pager.page('remote')
                obj.close()
            }
        })
    }
})

$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            detailLayer.open();
            // layer.confirm('是否新增年度绩效考核计划？', {
            //     btn: ['是', '否'] //可以无限个按钮
            // }, function (index, layero) {
            //     yearDetailLayer.open();
            //     layer.close(index);
            // }, function (index) {
            //     detailLayer.open();
            // });
            break;
        case "edit":
            if (checkedData.length == 1) {
                if (checkedData[0].apih5FlowStatus == 1 || checkedData[0].apih5FlowStatus == 2) {
                    layer.alert("已经发起了不能编辑", { icon: 0, }, function (index) {
                        layer.close(index);
                    });
                } else {
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
        case "yearAssLaunch":
            if (checkedData.length == 1) {
                if (!checkedData[0].assessmentQuarter == 6) {
                    layer.alert("请选择正确的流程发起", { icon: 0, }, function (index) {
                        layer.close(index);
                    });
                } else {
                    layer.confirm("确定发起？", { icon: 0, }, function (index) {
                        l.ajax("batchZjXmCqjxProjectAssessmentManageSendMessage", checkedData[0], function () {
                            pager.page('remote')
                        })
                        layer.close(index);
                    });
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
        case "planLaunch":
            if (checkedData.length >= 1) {
                layer.confirm("确定发起？", { icon: 0, }, function (index) {
                    for (i = 0; i < checkedData.length; i++) {
                        checkedData[i].state = "1";
                    }
                    l.ajax("batchZjXmCqjxProjectAssessmentManageSendMessage", checkedData, function () {
                        pager.page('remote')
                    })
                    layer.close(index);
                });

            } else {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
            break;
        case "assLaunch":
            if (checkedData.length >= 1) {
                layer.confirm("确定发起？", { icon: 0, }, function (index) {
                    for (i = 0; i < checkedData.length; i++) {
                        // console.log(checkedData[i].state);
                        if (checkedData[i].state == "1") {
                            checkedData[i].state = "2";
                        } else {
                            layer.alert("请先发起计划流程！", { icon: 0, }, function (index) {
                                layer.close(index);
                            });
                            break;
                        }
                        if (i == checkedData.length - 1) {
                            l.ajax("batchZjXmCqjxProjectAssessmentManageSendMessage", checkedData, function () {
                                pager.page('remote')
                            })
                            layer.close(index);
                        }
                    }
                });

            } else {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
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
                // for(var i = 0; i < checkedData.length; i++){
                //     if(checkedData[i].apih5FlowStatus == 1 && checkedData[i].apih5FlowStatus == 2){
                //         layer.alert("流程已经发起不能删除!",{ icon: 0,},function (index) {
                //             layer.close(index);
                //         });
                //         break;
                //     }else if(i === checkedData.length - 1){
                layer.confirm("确定删除？", { icon: 0, }, function (index) {
                    l.ajax("batchDeleteUpdateZjXmCqjxProjectAssessmentManage", checkedData, function () {
                        pager.page('remote')
                    })
                    layer.close(index);
                });
                //     }
                // }
            }
            break;
    }
})
var myOpenLayer;
function myOpen(title, rowData, url) {
    var rowData = JSON.parse(decodeURI(rowData));
    l.ajax("batchCheckZjXmCqjxExecutiveAssistantLaunch", rowData, function () {
    })
    var options = {
        type: 2,
        title: title,
        area: ['100%', '100%'],
        content: url + ".html?code=" + code + '&managerId=' + rowData.managerId + '&assessmentType=' + rowData.assessmentType
    };
    myOpenLayer = layer.open(options);
}
