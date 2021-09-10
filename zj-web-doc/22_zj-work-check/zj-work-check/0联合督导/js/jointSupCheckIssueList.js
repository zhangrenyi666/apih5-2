﻿﻿var code = Lny.getUrlParam('code')
Lny.setCookie('code', code, 30)
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
            "title": "问题清单编号",//表头名
            "data": "issueNumber",//接口对应字段
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "title": "检查部门",//表头名
            "data": "checkDepName",//接口对应字段
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "单位名称",//表头名		
            "defaultContent": "-",//默认显示
			"render": function (data, index, row) {
                var a;
                if (data) {//approve                  
                    a = '<a style="color:blue;"  onclick="myOpen(\'问题清单确认\',\'' + data.checkIssueId + '\',\'' + 'jointSupCheckIssueApprove' + '\',\'' + 'detail' + '\')" href="javascript:void(0);">' + data.unitNameName + '</a>'
                }
                return a;
            }
            /* "render": function (data, index, row) {
                var a;
                if (data) {
                    a = '<a style="color:blue;"  onclick="myOpen(\'问题清单确认\',\'' + data.checkIssueId + '\',\'' + 'jointSupCheckIssueListEdit' + '\')" href="javascript:void(0);">' + data.unitNameName + '</a>'
                }
                return a;
            } */
        },
		 {
            "targets": [5],//第几列
            "data": "launchFlag",//接口对应字段
            "title": "状态",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "modifyUserName",//接口对应字段
            "title": "操作者",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "更新时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return data ? l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss") : "-";
            }
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "checkDep",//输入字段名
            label: "检查部门",//输入字段对杨的中文名称
            placeholder: "请输入检查部门",
        },
        {
            type: "text",//三种形式：text,select,date,
            name: "unitName",//输入字段名
            label: "单位名称",//输入字段对杨的中文名称
            placeholder: "请输入使用单位",
        }
    ],
    onSearch: function (arr) {//搜索按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
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
        url: l.getApiUrl("getZjLhddJointSupCheckIssueList"),
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
    var checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            myOpen('问题清单确认--新增', "", 'jointSupCheckIssueApprove')
            break;
        case "edit":
            if (checkedData.length == 1) {
                var recordid = checkedData[0].checkIssueId
				var workId = checkedData[0].workId
                if (checkedData[0].apih5FlowStatus === "0") {
                    myOpen('问题清单确认--编辑', recordid, 'jointSupCheckIssueApprove', 'edit', workId)
                } else {
                    layer.alert("该流程已经发起了，无法编辑。", { icon: 0, }, function (index) {
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
        case "del":
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            } else {
                var can = true
                for (var index = 0; index < checkedData.length; index++) {
                    var item = checkedData[index];
                    if (item.apih5FlowStatus !== "0") {
                        can = false
                    }
                }
                if (can) {
                    layer.confirm("确定删除？", { icon: 0, }, function (index) {
                        l.ajax("batchDeleteUpdateZjLhddJointSupCheckIssue", checkedData, function (data, message, success) {
                            if (success) {
                                pager.page('remote')
                                layer.alert(message, { icon: 0 }, function (index2) {
                                    layer.close(index2);
                                });
                            }
                        })
                        layer.close(index);
                    });
                } else {
                    layer.alert("选择项中存在已提交过评审的不可删除！", { icon: 0 }, function (index) {
                        layer.close(index);
                    });
                }
            }
            break;
    }
})





var myOpenLayer;
function myOpen(title, id, url, type) {
    var type = type || 'edit';
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id + '&type=' + type + '&code=' + code
    }
    myOpenLayer = layer.open(options)
    layer.full(myOpenLayer)
}
