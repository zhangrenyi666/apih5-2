﻿var code = Lny.getUrlParam('code')
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
            "title": "监督责任部门",//表头名
            "data": "supDutyDepName",//接口对应字段
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "填报人",//表头名		
            "defaultContent": "-",//默认显示
            "render": function (data, index, row) {
                var a;
                if (data) {     
                      a = '<a style="color:blue;"  onclick="myOpen(\'年度监督确认\',\'' + data.yearSupPlanId + '\',\'' + 'jdjhyearSupPlanApprove' + '\',\'' + 'detail' + '\')" href="javascript:void(0);">' + data.informant + '</a>'
				}
                return a;
            }
        },  
		{
            "targets": [4],//第几列
            "title": "年度",//表头名
            "data": "year",//接口对应字段
            "defaultContent": "-",//默认显示
			"render": function (data) {
                var text = ""
                switch (data) {
                    case "0": text = "2019";
                        break;
                    case "1": text = "2020";
                        break;
					case "2": text = "2021";
                        break;
					case "3": text = "2022";
                        break;
					case "4": text = "2023";
                        break;
                    default: text = "未知";
                        break;
                }
                return text
            }
        },
		{
            "targets": [5],//第几列
            "title": "季度",//表头名
            "data": "quarter",//接口对应字段
            "defaultContent": "-",//默认显示
			"render": function (data) {
                var text = ""
                switch (data) {
                    case "0": text = "第一季度";
                        break;
                    case "1": text = "第二季度";
                        break;
					case "2": text = "第三季度";
                        break;
					case "3": text = "第四季度";
                        break;
                    default: text = "未知";
                        break;
                }
                return text
            }
        },
		{
            "targets": [6],//第几列
            "title": "任务个数",//表头名
            "data": "taskNumber",//接口对应字段
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [7],//第几列
            "title": "状态",//表头名
            "data": "apih5FlowStatus",//接口对应字段
            "defaultContent": "-",//默认显示
			"render": function (data) {
                var text = ""
                switch (data) {
                    case "0": text = "待提交";
                        break;
                    case "1": text = "审批中";
                        break;
					case "2": text = "审批结束";
                        break;
                    default: text = "未知";
                        break;
                }
                return text
            }
        },
        {
            "targets": [8],//第几列
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
            name: "supDutyDep",//输入字段名
            label: "监督责任部门",//输入字段对杨的中文名称
            placeholder: "请输入监督责任部门",
        },
		{
            type: "select",//三种形式：text,select,date,
            name: "year",//输入字段名
            label: "年度",//输入字段对杨的中文名称          
			 selectList: [           
            {
              value: "",
              text: "请选择"
            },
            {
              value: "0",
              text: "2019"
            },
            {
              value: "1",
              text: "2020"
            },
            {
              value: "2",
              text: "2021"
            },
            {
              value: "3",
              text: "2022"
            },
            {
              value: "4",
              text: "2023"
            }
          ]
        },
        {
          type: "select",
          name: "quarter",
          label: "季度",
          selectList: [
            //当类型为select时，option配置  0:全公司  1：机关  2：项目
            {
              value: "",
              text: "请选择 "
            },
            {
              value: "0",
              text: "第一季度"
            },
            {
              value: "1",
              text: "第二季度"
            },
            {
              value: "2",
              text: "第三季度"
            },
            {
              value: "3",
              text: "第四季度"
            }
          ]
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
        url: l.getApiUrl("getZjJdjhYearSupPlanList"),
        params: { },
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
            //myOpen('季度监督确认--新增', "", 'jdjhYearSupPlanListEdit')
			myOpen('年度监督确认--新增', "", 'jdjhYearSupPlanApprove')
            break;
        case "edit":
            if (checkedData.length == 1) {
                var recordid = checkedData[0].yearSupPlanId
				var workId = checkedData[0].workId
                if (checkedData[0].apih5FlowStatus === "0") {
                    //myOpen('季度监督确认--编辑', recordid, 'jdjhYearSupPlanListEdit')
					 myOpen('年度监督确认--编辑', recordid, 'jdjhYearSupPlanApprove', 'edit', workId)
                } else {
                    layer.alert("该项已提交评审，不可编辑？", { icon: 0, }, function (index) {
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
                        l.ajax("batchDeleteUpdateZjJdjhYearSupPlan", checkedData, function (data, message, success) {
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
