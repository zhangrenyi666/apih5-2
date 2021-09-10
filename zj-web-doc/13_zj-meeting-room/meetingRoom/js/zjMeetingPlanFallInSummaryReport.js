var code = Apih5.getUrlParam('code')
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
            "targets": [1],//第几列
            "data": "meetingTimeStr",//接口对应字段
            "title": "会议时间",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [2],//第几列
            "data": "meetingNameStr",//接口对应字段
            "title": "会议名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "meetingForm",//接口对应字段
            "title": "会议形式",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "joinObject",//接口对应字段
            "title": "参加对象",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "joinNumber",//接口对应字段
            "title": "人数（人）",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "sponsoringDept",//接口对应字段
            "title": "主办部门",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "coOperationDept",//接口对应字段
            "title": "协办部门",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "budgetaryCost",//接口对应字段
            "title": "预算费用（万元）",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "meetingRemakes",//接口对应字段
            "title": "备注",//表头名
            "defaultContent": "-",//默认显示
        }         
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "date",//
            name: "fallInYearTime",//
            label: "年度",//
            dateFmt: "yyyy",
            id: "fallInYearTime"
        },  
        {
            type: "text",//
            name: "sponsoringDept",//
            label: "主办部门",//         
        },               
        {
            type: "text",//
            name: "meetingTimeStr",//
            label: "会议时间",//         
        },               
        {
            type: "text",//
            name: "meetingForm",//
            label: "会议形式",//         
        },               
    ],
    onSearch: function (arr) {//搜索按钮回调
        var _params = {
            yearApprovalState:"2"            
        };
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].name == "fallInYearTime") {
                if (arr[i].value) {
                    _params[arr[i].name] = l.dateFormat(new Date(new Date(arr[i].value).getTime()), "yyyy-MM-dd");
                } else {
                    _params[arr[i].name] = "";
                }
            } else {
                _params[arr[i].name] = arr[i].value;
            }
        }
        pager.page('remote',0, _params)
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
        url: l.getApiUrl("getZjMeetingPlanFallInSummaryList"),
        params: {
            yearApprovalState:"2"
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
				//console.log(data)
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
    switch (name) {		
            case "export":
                layer.confirm("确定删除？", { icon: 0, }, function (index) {
                    l.ajax("batchDeleteUpdateZjMeetingPlanFallIn", checkedData, function () {
                        pager.page('remote')
                    })
                    layer.close(index);
                });
                break;      
    }
})
