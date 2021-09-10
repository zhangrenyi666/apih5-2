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
            "title": "项目名称",//表头名
            "data": "projectName",//接口对应字段
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "title": "方案名称",//表头名
            "data": "schemeName",//接口对应字段
            "defaultContent": "-",//默认显示
        },
		 {
            "targets": [5],//第几列
            "title": "方案编号",//表头名
            "data": "schemeNumber",//接口对应字段
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "schemeLevel",//接口对应字段
            "title": "技术、安全等级",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "0":
                        r = "一定规模（安全）专项施工方案"
                        break;
                    case "1":
                        r = "超过一定规模（安全）专项施工方案"
                        break;
                    case "2":
                        r = "技术性风险性控制性分部分项工程（安全）专项施工方案"
                        break;
                    default:
                        r = "未知"
                        break;
                }
                return r
            }
        },
		{
            "targets": [7],//第几列
            "title": "技术等级、安全等级划分说明",//表头名
            "data": "hierarchyDescription",//接口对应字段
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [8],//第几列
            "data": "implementationTime",//接口对应字段
            "title": "方案计划实施时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return data ? l.dateFormat(new Date(data), "yyyy-MM-dd") : "-";
            }
        },
		{
            "targets": [9],//第几列
            "data": "compilingSubject",//接口对应字段
            "title": "编制主体",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//0：公司技术中心；1：单位技术分中心；2：单位技术部门；3：项目经理部
                var _data = ""
                switch (data) {
                    case "0":
                        _data = "公司技术中心"
                        break;
                    case "1":
                        _data = "单位技术分中心"
                        break;
                    case "2":
                        _data = "单位技术部门"
                        break;
                    case "3":
                        _data = "项目经理部"
                        break;
				    case "4":
                        _data = "专业分包单位"
                        break;
                    default:
                        _data = "未知"
                        break;
                }
                return _data
            }
        },
		{
            "targets": [10],//第几列
            "title": "清单通过时间",//表头名
            "data": "toExamineTime",//接口对应字段
            "defaultContent": "-",//默认显示
			"render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd")
            }
        },
		{
            "targets": [11],//第几列
            "title": "备注",//表头名
            "data": "remarks",//接口对应字段
            "defaultContent": "-",//默认显示
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
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
            label: "技术、安全等级",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 0,//输入字段的值
                    text: "一定规模（安全）专项施工方案",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "超过一定规模（安全）专项施工方案",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 2,//输入字段的值
                    text: "技术性风险性控制性分部分项工程（安全）专项施工方案",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        },
		/*
		{
            type: "text",//三种形式：text,select,date,
            name: "projectGeneralUser",//输入字段名
            label: "项目总工",//输入字段对杨的中文名称
            placeholder: "请输入项目总工",
        },*/	
		        {
            type: "select",
            name: "compilingSubject",
            label: "编制主体",
            selectList: [
                {
                    value: "",
                    text: "全部",
					selected: true//默认是否选中
                },			
                {
                    value: "0",
                    text: "公司技术中心"
                }, {
                    value: "1",
                    text: "单位技术分中心"
                }, {
                    value: "2",
                    text: "单位技术部门"
                }, {
                    value: "3",
                    text: "项目经理部"
                }, {
                    value: "4",
                    text: "专业分包单位"
                }
            ],//0：公司技术中心；1：单位技术分中心；2：单位技术部门；3：项目经理部
            immutableAdd: true
        },
        {
            type: "date",//text,select,date,
            name: "startTime",
            label: "方案计划实施开始",
            //defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd"),
            maxDate: "\'#F{$dp.$D(\\\'" + "logmax" + "\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmin"
        },
        {
            type: "date",//text,select,date,
            name: "endTime",
            label: "方案计划实施结束",
            //defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd"),
            //maxDate: "\'#F{$dp.$D(\\\'"+"logmin"+"\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmax"
        }		
    ],
    onSearch: function (arr) {//搜索按钮回调
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
        url: l.getApiUrl("getProgrammeApprovalStatisticsList"),
        params: {
            projectName: "",
            schemeNumber: "",
			schemeName:"",
			schemeLevel: "",
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
         case "export"://导出
            var params = {};
			params.schemeNumber = $('[name = "schemeNumber"]').val();
            params.unitName = $('[name = "unitName"]').val();
            params.projectName = $('[name = "projectName"]').val();
            params.schemeLevel = $('[name = "schemeLevel"]').val();
            params.projectGeneralUser = $('[name = "projectGeneralUser"]').val();
            params.compilingSubject = $('[name = "compilingSubject"]').val();
            params.startTime = $('[name = "startTime"]').val();
            params.endTime = $('[name = "endTime"]').val();
            layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
                l.ajax('schemeReviewStatusExportExcel', params, function (res) {
                    layer.alert("导出成功！", { icon: 0 }, function (index) {
                        layer.close(index);
                        window.location.href = res;
                    })
                })
            });
            break;
    }
})
