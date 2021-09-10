﻿var code = Apih5.getUrlParam('code')
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
            "title": "单位名称",//表头名
            "data": "unitName",//接口对应字段
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "projectName",//接口对应字段
            "title": "项目名称",//表头名
            "defaultContent": "-",//默认显示
        },

        {
            "targets": [4],//第几列
            "data": "schemeNumber",//接口对应字段
            "title": "方案编号",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "schemeName",//接口对应字段
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "方案名称",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                var a;
                if (data && data.reviewState !== "") {
                    a = '<a style="color:blue;"  onclick="myOpenByState(\'方案审批处理\',\'' + data.launchId + '\',\'' + data.schemeLevel + '\')" href="javascript:void(0);">' + data.schemeName + '</a>'
                } else {
                    a = data.schemeName
                }
                return a;
            }
        },
        {
            "targets": [6],//第几列
            "data": "implementationTime",//接口对应字段
            "title": "方案计划实施时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return data ? l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss") : "-";
            }
        },
        {
            "targets": [7],//第几列
            "data": "projectGeneralUser",//接口对应字段
            "title": "项目总工",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [8],//第几列
            "data": "programmingPerson",//接口对应字段
            "title": "方案编制者",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [9],//第几列
            "data": "reviewState",//接口对应字段
            "title": "评审状态",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "":
                        r = "未发起"
                        break
                    case "0":
                        r = "未评审"
                        break
                    case "1":
                        r = "评审通过"
                        break
                    case "2"://
                        r = "评审中"
                        break
                    case "3":
                        r = "评审未通过"
                        break
                    case "4"://
                        r = "评审已通过"
                        break
                }
                return r
            }
        },
		{
            "targets": [10],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "方案评审通过时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return data ? l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss") : "-";
            }
        },
        {
            "targets": [11],//第几列
            "data": "remarks",//接口对应字段
            "title": "备注",//表头名
            "defaultContent": "-",//默认显示
        }

    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
		{
            type: "text",//三种形式：text,select,date,
            name: "schemeName",//输入字段名
            label: "方案名称",//输入字段对杨的中文名称
            placeholder: "请输入方案名称",
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "schemeNumber",//输入字段名
            label: "方案编号",//输入字段对杨的中文名称
            placeholder: "请输入方案编号",
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "unitName",//输入字段名
            label: "单位名称",//输入字段对杨的中文名称
            placeholder: "请输入单位名称",
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "projectName",//输入字段名
            label: "项目名称",//输入字段对杨的中文名称
            placeholder: "请输入项目名称",
        },
        {
            type: "select",
            name: "schemeLevel",
            label: "技术等级",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: "0",
                    text: "Ⅲ级施工方案",
                    selected: false//默认是否选中
                }, {
                    value: "1",
                    text: "Ⅱ级施工方案",
                    selected: false//默认是否选中					
                }, {
                    value: "2",
                    text: "Ⅰ级施工方案",
                    selected: false//默认是否选中					
                }, {
                    value: "3",
                    text: "Ⅳ级施工方案",
                    selected: false//默认是否选中					
                }
            ],
        },
		{
            type: "select",
            name: "reviewState",
            label: "状态",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 2,//输入字段的值
                    text: "评审中",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 3,//输入字段的值
                    text: "评审未通过",//显示中文名
                    selected: false//默认是否选中
                },
				{
                    value: 4,//输入字段的值
                    text: "评审已通过",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "projectGeneralUser",//输入字段名
            label: "项目总工",//输入字段对杨的中文名称
            placeholder: "请输入项目总工",
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
        url: l.getApiUrl("getZjPrProgrammeLaunchList"),
        params: {
            projectName: "",
            projectState: "",
		    reviewState :"",
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
            myOpen('方案新建', "", 'programmeLaunchEdit')
            break;
        case "addIV":
            myOpen('方案新建', "", 'programmeLaunchEditByIV')
            break;			
        case "edit":
            if (checkedData.length == 1) {
                if (checkedData[0].reviewState === "") {
                    myOpen('方案编辑', checkedData[0].launchId, 'programmeLaunchEdit')
                } else {
                    layer.alert("已发起审批的不可编辑！", { icon: 0, }, function (index) {
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
                    if (item.reviewState !== "") {
                        can = false
                    }
                }
                if (can) {
                    layer.confirm("确定删除？", { icon: 0, }, function (index) {
                        l.ajax("batchDeleteUpdateZjPrProgrammeLaunch", checkedData, function () {
                            pager.page('remote')
                        })
                        layer.close(index);
                    });
                } else {
                    layer.alert("选择项中存在已提交审核的！", { icon: 0 }, function (index) {
                        layer.close(index);
                    });
                }
            }
            break;
        case "launch":
            if (checkedData.length == 1) {
                if (checkedData[0].reviewState === "") {
                    myOpen('方案发起', checkedData[0].launchId, 'programmeLaunch', checkedData[0].flowTemplateId)
                } else {
                    layer.alert("已发起审批的不可再发起！", { icon: 0, }, function (index) {
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
        case "approve":
            if (checkedData.length == 1) {
                if (checkedData[0].reviewState !== "") {
                    myOpen('方案审批处理', checkedData[0].launchId, 'programmeApprove')
                } else {
                    layer.alert("该方案不可评审！", { icon: 0, }, function (index) {
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
    }
})





var myOpenLayer;
function myOpen(title, id, url, id2) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id + "&id2=" + id2
    }
    myOpenLayer = layer.open(options)
    layer.full(myOpenLayer)
}
function myOpenByState(title, id, state, id2) {
	
	var url;
	if(state == '3'){
		url='programmeLaunchDetailByIV';
	}else{
		url='programmeApproveDetail';
	}
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id + "&id2=" + id2
    }
    myOpenLayer = layer.open(options)
    layer.full(myOpenLayer)
}
