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
            "targets": [1],//第几列
            "data": "meetingRoomNumber",//接口对应字段
            "title": "房间号",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [2],//第几列
            "data": "meetingRoomName",//接口对应字段
            "title": "房间名",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "personInChargeName",//接口对应字段
            "title": "负责人",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "personInChargeTel",//接口对应字段
            "title": "负责人电话",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "meetingRoomCapacity",//接口对应字段
            "title": "可容纳人数",//表头名
            "defaultContent": "-",//默认显示
        },		
		{
            "targets": [6],//第几列
            "data": "modifyUserName",//接口对应字段
            "title": "操作员",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "操作时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "meetingRoomNumber",//输入字段名
            label: "会议室编号",//输入字段对杨的中文名称
            placeholder: "请输入会议室编号",
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "meetingRoomName",//输入字段名
            label: "会议室名称",//输入字段对杨的中文名称
            placeholder: "请输入会议室名称",
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
        url: l.getApiUrl("getZjMeetingRoomList"),
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
var detailLayer = $('#detailLayer').detailLayer({
	layerArea:['50%', '70%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "recordid",//输入字段名
        },
		{
            type: "text",//
            name: "meetingRoomNumber",//
            label: "房间号",//
            placeholder: "请输入房间号",
            must: true,
		    immutable:true
        },
		{
            type: "text",//
            name: "meetingRoomName",//
            label: "房间名称",//
            placeholder: "请输入会议室",
            must: true,
        },
		{
            type: "text",//
            name: "meetingRoomCapacity",//
            label: "可容纳人数",//
            placeholder: "请输入可容纳人数",
            must: true,
        },		
		/*
		{
            type: "select",
            name: "meetingRoomTypeId",
            label: "会议室类型",
            ajax: {//如果需要接口获取数据需要添加该属性
                api: "getZjMeetingRoomTypeSelectAllList",//api名称
                valueName: "meetingRoomTypeId",//设置value对应的接口字段名称
                textName: "meetingRoomTypeName",//设置text对应的接口字段名称
            },
			must: true,
        },
		*/
		{
            type: "pickTree",
            name: "oaPersonInCharge",
            label: "负责人",
            pickType: {
            department: false,//部门列表对应接口字段名,false表示不开启该类
            member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            },
			must: true,
        },
        {
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入备注",

        }
    ],
    onSave: function (obj, _params) {
            l.ajax('updateZjMeetingRoom', _params, function (data) {
                pager.page('remote')
                obj.close()
            })    
    },
    onAdd: function (obj, _params) {     
            l.ajax('addZjMeetingRoom', _params, function (data) {
                pager.page('remote')
                obj.close()
            })
    }
})
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            detailLayer.open();
            break;
        case "edit":
            if (checkedData.length == 1) {
                detailLayer.open(checkedData[0]);
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
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
				 layer.confirm("确定删除？", { icon: 0,}, function (index) {
				l.ajax("batchDeleteUpdateZjMeetingRoom", checkedData, function () {
					pager.page('remote')
				})
				layer.close(index);
            });
            }
            break;
    }
})