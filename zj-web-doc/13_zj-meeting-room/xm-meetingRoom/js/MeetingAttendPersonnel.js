var code = Lny.getUrlParam('code')
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
            "data": "meetingRoomTitle",//接口对应字段
            "title": "会议名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "meetingRoomTypeId",//接口对应字段
            "title": "会议类型",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [4],//第几列
            "data": "meetingRoomNumber",//接口对应字段
            "title": "房间号",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [5],//第几列
            "data": "meetingRoomName",//接口对应字段
            "title": "房间名",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [6],//第几列
            "data": "meetingStartTime",//接口对应字段
            "title": "会议开始时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
		{
            "targets": [7],//第几列
            "data": "meetingEndTime",//接口对应字段
            "title": "会议结束时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },		
        {
            "targets": [8],//第几列
            "data": "attendPersonnel",//接口对应字段
            "title": "参会人数",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [9],//第几列
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "已签到人数",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data, index, row) {
                var a;
                if (data) {
                    a = '<a style="color:blue;"  onclick="myOpen(\'已签到人员列表\',\'' + data.recordid + '\',\'' + 'zjMeetingHaveSignedInPersonnel' + '\')" href="javascript:void(0);">' + data.haveSignedInNumber + '</a>'
                }
                return a;
            }
        },
		{
            "targets": [10],//第几列
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "未签到人数",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data, index, row) {
                var a;
                if (data) {
                    a = '<a style="color:blue;"  onclick="myOpen(\'未签到人员列表\',\'' + data.recordid + '\',\'' + 'zjMeetingNotSignInPersonnel' + '\')" href="javascript:void(0);">' + data.notSignInNumber + '</a>'
                }
                return a;
            }
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
	/*
		{
            type: "select",
            name: "questionClassId",
            label: "会议室",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getMeetingRoomAllList",
                valueName: "recordid",
                textName: "meetingRoomName"
            },
        },
		*/
		{
            type: "text",//三种形式：text,select,date,
            name: "meetingRoomTitle",//输入字段名
            label: "会议名称",//输入字段对杨的中文名称
            placeholder: "请输入会议名称",
        },
		{
            type: "date",//text,select,date,
            name: "startTime",
            label: "会议开始时间选择",
            //defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd"),
            maxDate: "\'#F{$dp.$D(\\\'" + "logmax" + "\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmin"
        },
        {
            type: "date",//text,select,date,
            name: "endTime",
            label: "会议开始时间截止",
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
        url: l.getApiUrl("getZjMeetingAttendPersonnelList"),
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
    switch (name) {
        case "derive"://导出
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            }else if (checkedData.length == 1) {
                layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
                    l.ajax('zjMeetingSingInExportExcel',checkedData[0],function(res){
                        layer.alert("导出成功！", { icon: 0 }, function (index) {
                            layer.close(index);
                            // console.log(res)
                            window.location.href=res;
                        })
                    })
                });

            }else{
                layer.alert("只能选择一个", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            }
            break;
    }
});
function myOpen(title, id, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id +"&code=" + code
    }
    myOpenLayer = layer.open(options)
    layer.full(myOpenLayer)
}
