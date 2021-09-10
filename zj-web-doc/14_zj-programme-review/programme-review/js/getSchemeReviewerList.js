var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
var table = $('#table').DataTable({
    "info": false,//是否显示数据信息
    "paging": false,//是否开启自带分页
    "ordering": false, //是否开启DataTables的高度自适应
    "processing": false,//是否显示‘进度’提示
    "searching": false,//是否开启自带搜索
    "autoWidth": false,//是否监听宽度变化
    "initComplete": function (settings) {
        var innerTh = '<th rowspan="1"></th>';
		innerTh += '<th colspan="4" style="text-align:center;"></th>';
        innerTh += '<th colspan="2" style="text-align:center;">一定规模（安全）专项施工方案</th>';
		innerTh += '<th colspan="2" style="text-align:center;">超过一定规模（安全）专项施工方案</th>';
		innerTh += '<th colspan="2" style="text-align:center;">技术性风险性控制性分部分项工程（安全）专项施工方案</th>';
       
        $('#table').find("thead").prepend(innerTh);
    },
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
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "专家姓名",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data, index, row) {
                var a;
                if (data) {
                    a = '<a style="color:blue;"  onclick="myOpen(\'已审批方案查看\',\'' + data.objectUserKey + '\',\'' + 'prAlreadyHandledProgrammeList' + '\',\'' +1+ '\')" href="javascript:void(0);">' + data.objectName + '</a>'
                }
                return a;
            }
        },		
		{
            "targets": [3],//第几列
            'data': 'noteName',
            "title": "专家职称",//表头名
            "defaultContent": "-",//默认显示
        },
		        {
            "targets": [4],//第几列
            'data': 'totalReview',
            "title": "审核总次数（次）",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [5],//第几列
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "驳回次数（次）",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data, index, row) {
                var a;
                if (data) {
                    a = '<a style="color:blue;"  onclick="myOpen(\'驳回方案查看\',\'' + data.objectUserKey + '\',\'' + 'prRejectProgrammeList' + '\',\'' + 1 + '\')" href="javascript:void(0);">' + data.programmeOneRejectCount + '</a>'
                }
                return a;
            }
        },	
		/*
        {
            "targets": [5],//第几列
            "data": "programmeOneRejectCount",//接口对应字段
            "title": "驳回次数（次）",//表头名
            "defaultContent": "-",//默认显示
        },
		*/
        {
            "targets": [6],//第几列
            "data": "programmeOneAdoptCount",//接口对应字段
            "title": "通过次数（次）",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [7],//第几列
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "驳回次数（次）",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data, index, row) {
                var a;
                if (data) {
                    a = '<a style="color:blue;"  onclick="myOpen(\'驳回方案查看\',\'' + data.objectUserKey + '\',\'' + 'prRejectProgrammeList' + '\',\'' + 2 + '\')" href="javascript:void(0);">' + data.programmeTwoRejectCount + '</a>'
                }
                return a;
            }
        },
		/*
        {
            "targets": [7],//第几列
            "data": "programmeTwoRejectCount",//接口对应字段
            "title": "驳回次数（次）",//表头名
            "defaultContent": "-",//默认显示
        },
		*/
        {
            "targets": [8],//第几列
            "data": "programmeTwoAdoptCount",//接口对应字段
            "title": "通过次数（次）",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [9],//第几列
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "驳回次数（次）",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data, index, row) {
                var a;
                if (data) {
                    a = '<a style="color:blue;"  onclick="myOpen(\'驳回方案查看\',\'' + data.objectUserKey + '\',\'' + 'prRejectProgrammeList' + '\',\'' + 3 + '\')" href="javascript:void(0);">' + data.programmeThreeRejectCount + '</a>'
                }
                return a;
            }
        },
		/*
        {

            "targets": [9],//第几列
            "data": "programmeThreeRejectCount",//接口对应字段
            "title": "驳回次数（次）",//表头名
            "defaultContent": "-",//默认显示
        },
		*/
		{

            "targets": [10],//第几列
            "data": "programmeThreeAdoptCount",//接口对应字段
            "title": "通过次数（次）",//表头名
            "defaultContent": "-",//默认显示
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "objectName",//输入字段名
            label: "专家名称",//输入字段对杨的中文名称
            placeholder: "请输入专家名称",
        },
		{
            type: "select",
            name: "noteName",
            label: "专家职称",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getTemplateNoteAllList",
                valueName: "noteName",
                textName: "noteName"
            },
        }
		/*
        {
            type: "text",//三种形式：text,select,date,
            name: "schemeName",//输入字段名
            label: "项目名称",//输入字段对杨的中文名称
            placeholder: "请输入使用单位",
        },		
        {
            type: "select",
            name: "schemeLevel",
            label: "状态",
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
*/		
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
        url: l.getApiUrl("getSchemeReviewerList"),
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
    checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "export"://导出
            var params = {};
            //console.log(params);
            layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
                l.ajax('schemeReviewerExportExcel', params, function (res) {
                    layer.alert("导出成功！", { icon: 0 }, function (index) {
                        layer.close(index);
                        window.location.href = res;
                    })
                })
            });
            break;
}
})

var myOpenLayer;
function myOpen(title, id, url,flowTemplateId) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id + "&code=" + code + "&flowTemplateId=" + flowTemplateId
    }
    myOpenLayer = layer.open(options)
    layer.full(myOpenLayer)
}
