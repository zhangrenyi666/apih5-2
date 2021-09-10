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
            "data": "attendPersonnelUser",//接口对应字段
            "title": "参会人员姓名",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [4],//第几列
            "data": "department",//接口对应字段
            "title": "参会人员部门",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [5],//第几列
            "data": "telephone",//接口对应字段
            "title": "参会人员电话",//表头名
            "defaultContent": "-",//默认显示
        }
		
		,
		{
            "targets": [6],//第几列
            "data": "signInTime",//接口对应字段
            "title": "签到时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        }
	    /*
		{
            "targets": [6],//第几列
            "data": "signInType",//接口对应字段
            "title": "签到类型",//表头名
            "defaultContent": "-",//默认显示
			"render": function (data) {// 自定义输出
                var r = "未知";
                switch (data) {
				    case "0":
                        r = "正常签到"
                        break
                    case "1":
                        r = "补签"
                        break
                }
                 return r
            }
        }
		,
		{
            "targets": [7],//第几列
            "data": "supplementNote",//接口对应字段
            "title": "补签原因",//表头名
            "defaultContent": "-",//默认显示
        }
		*/		
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
	    {
            type: "text",//三种形式：text,select,date,
            name: "attendPersonnelUser",//输入字段名
            label: "参会人员姓名",//输入字段对杨的中文名称
            placeholder: "请输入会议名称",
        }
		/*
		{
            type: "date",//text,select,date,
            name: "startTime",
            label: "签到时间开始",
            //defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd"),
            maxDate: "\'#F{$dp.$D(\\\'" + "logmax" + "\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmin"
        },
        {
            type: "date",//text,select,date,
            name: "endTime",
            label: "签到时间结束",
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
        url: l.getApiUrl("getZjMeetingHaveSignedInPersonnelList"),
        params: {
           reservationsId : Apih5.getUrlParam('id')
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

