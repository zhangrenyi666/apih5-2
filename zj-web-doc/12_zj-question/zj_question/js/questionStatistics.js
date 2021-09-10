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
            "data": "flowType",// 接口对应字段
            "title": "流程类型",// 表头名
            "defaultContent": "-",// 默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "0":
                        r = "整改项"
                        break
                    case "1":
                        r = "建议项"
                        break
                }
                return r
            }
        },                
        {
            "targets": [4],// 第几列
            "data": "questionTitle",// 接口对应字段
            "title": "主题",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [5],// 第几列
            "data": "projectName",// 接口对应字段
            "title": "项目",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [6],// 第几列
            "data": "questionClassName",// 接口对应字段
            "title": "类别",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [7],// 第几列
            "data": "questionTitleName",// 接口对应字段
            "title": "检查项",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [8],// 第几列
            "data": "questionDescribe",// 接口对应字段
            "title": "问题描述",// 表头名
            "defaultContent": "-",// 默认显示
        },
		/*
        {
            "targets": [7],// 第几列
            "data": "questionState",// 接口对应字段
            "title": "整改进度",// 表头名
            "defaultContent": "-",// 默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "0":
                        r = "未提交"
                        break
                    case "1":
                        r = "初审中"
                        break
                    case "2":
                        r = "初审通过"
                        break
                    //case "3":
                    //    r = "未通过"
                    //    break
                    case "4":
                        r = "问题关闭"
                        break
                    case "5":
                        r = "通过审核"
                        break
                    case "6":
                        r = "通过审核"
                        break
                    case "7":
                        r = "延时整改完成"
                        break
                }
                return r
            }
        },
*/		
        {
            "targets": [9],// 第几列
            "data": "questionState",// 接口对应字段
            "title": "整改进度",// 表头名
            "defaultContent": "-",// 默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "0":
                        r = "未提交"
                        break
                    case "1":
                        r = "初审中"
                        break
                    case "2":
                        r = "初审通过"
                        break
                    case "3":
                        r = "初审驳回"
                        break
                    case "4":
                        r = "通过审核"
                        break
                    case "5":
                        r = "复审驳回"
                        break
                    case "6":
                        r = "复审驳回"
                        break
                    case "7":
                        r = "延时整改完成"
                        break
                }
                return r
            }
        },
        {
            "targets": [10],// 第几列
            "data": "checkDepartmentName",// 接口对应字段
            "title": "检查部门",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [11],// 第几列
            "data": "createUserName",// 接口对应字段
            "title": "创建人",// 表头名
            "defaultContent": "-",// 默认显示
        },        
        {
            "targets": [12],// 第几列
            "data": "createTime",// 接口对应字段
            "title": "提出时间",// 表头名
            "defaultContent": "-",// 默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [13],//第几列
            "data": "dateOfAnswer",//接口对应字段
            "title": "解决时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                if (data != null) {
                    return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
                }
            }
        },
        {
            "targets": [14],//第几列
            "width": 100,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名
            "render": function (data) {
                var html = '';
                html += '<span class="dropDown">'
                html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">操作</a>';
                html += '<ul class="dropDown-menu menu radius box-shadow">'
                html += '<li><a onmouseenter="myOpens($(this))" onmouseleave="myOpena($(this))" href="javascript:void(0);" onclick="myOpen(\'详情\',\'' + data.recordid + '\',\'' + 'questionDetail' + '\')">详情</a></li>';
                html += '<li><a onmouseenter="myOpens($(this))" onmouseleave="myOpena($(this))" href="javascript:void(0);" onclick="myOpen(\'历史追溯\',\'' + data.recordid + '\',\'' + 'questionHistory' + '\')">历史追溯</a></li>';
                html += '</ul></span>'
                return html;
            }
        }        
    ]
});
function myOpens(aa) {
    aa.css('backgroundColor', '#7EC0EE');

}
function myOpena(li) {
    li.css('backgroundColor', 'white');

}
function myOpen(title, id, url, projectId) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id + "&code=" + code + "&projectId=" + projectId
    }
    layer.full(layer.open(options))
}
var form = $('#form').filterfrom({
    conditions: [// 表单项配置
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
            name: "questionTitle",//
            label: "主题",//
            placeholder: "请输入问题标题",
        },
        {
            type: "select",
            name: "flowType",
            label: "流程类型",
            selectList: [//当类型为select时，option配置  0：未审批 1：审批中  2：通过 3：未通过   4：问题关闭
                {
                    value: "",
                    text: "全部"
                },
                {
                    value: "0",
                    text: "整改项"
                },
                {
                    value: "1",
                    text: "建议项"
                }
            ],
        },         
        {
            type: "text",//
            name: "projectName",//
            label: "项目",//
            placeholder: "请输入项目",
        },
        {
            type: "text",//
            name: "departmentName",//
            label: "检查部门",//
            placeholder: "请输入检查部门",
        },        
        
        {
            type: "select",
            name: "questionState",
            label: "整改进度",
            selectList: [//当类型为select时，option配置  0：未审批 1：审批中  2：通过 3：未通过   4：问题关闭
                {
                    value: "",
                    text: "全部"
                },
                {
                    value: "0",
                    text: "未提交"
                },
                {
                    value: "1",
                    text: "初审中"
                },
                {
                    value: "2",
                    text: "初审通过"
                },
                {
                    value: "3",
                    text: "初审驳回"
                },
                {
                    value: "4",
                    text: "通过审核"
                },
                {
                    value: "5",
                    text: "复审驳回"
                },
                {
                    value: "6",
                    text: "复审驳回"
                },
                {
                    value: "7",
                    text: "延时整改完成"
                }

            ],
        },
        {
            type: "date",//text,select,date,
            name: "startTime",
            label: "问题提出开始",
            //defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd"),
            maxDate: "\'#F{$dp.$D(\\\'" + "logmax" + "\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmin"
        },
        {
            type: "date",//text,select,date,
            name: "endTime",
            label: "问题提出结束",
            //defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd"),
            //maxDate: "\'#F{$dp.$D(\\\'"+"logmin"+"\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmax"
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
        url: l.getApiUrl("getQuestionStatisticsList"),
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

$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    var params;
    switch (name) {
        case "export"://导出
            var params = {};
            params.schemeNumber = $('[name = "schemeNumber"]').val();
            params.unitName = $('[name = "unitName"]').val();
            layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
                l.ajax('xmQuestionExport', params, function (res) {
                    layer.alert("导出成功！", { icon: 0 }, function (index) {
                        layer.close(index);
                        window.location.href = res;
                    })
                })
            });
            break;
    }
})