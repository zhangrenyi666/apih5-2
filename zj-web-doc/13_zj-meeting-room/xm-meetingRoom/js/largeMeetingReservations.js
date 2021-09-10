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
            "data": "meetingRoomTypeName",//接口对应字段
            "title": "会议类型",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [2],//第几列
            "data": "meetingRoomNumber",//接口对应字段
            "title": "房间号",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [3],//第几列
            "data": "meetingRoomName",//接口对应字段
            "title": "房间名",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [4],//第几列
            "data": "meetingStartTime",//接口对应字段
            "title": "会议开始时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
		{
            "targets": [5],//第几列
            "data": "meetingEndTime",//接口对应字段
            "title": "会议结束时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },		
        {
            "targets": [6],//第几列
            "data": "contactsUser",//接口对应字段
            "title": "负责人",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "meetingRoomTitle",//接口对应字段
            "title": "会议标题",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [8],//第几列
            "data": "modifyUserName",//接口对应字段
            "title": "参会人",//表头名
            "defaultContent": "-",//默认显示
        },
				{
            "targets": [9],//第几列
            "data": "meetingState",//接口对应字段
            "title": "状态",//表头名
            "defaultContent": "-",//默认显示
			"render": function (data) {// 自定义输出
                var r = "未知";
                switch (data) {
				    case "0":
                        r = "未开始"
                        break
                    case "1":
                        r = "进行中"
                        break
                    case "2":
                        r = "已结束"
                        break
                }
                 return r
            }
        },
				{
            "targets": [10],//第几列
            "data": "modifyUserName",//接口对应字段
            "title": "预订人",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [11],//第几列
            "data": "reservationsTime",//接口对应字段
            "title": "预定日期",//表头名
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
		{
            type: "date",//text,select,date,
            name: "startTime",
            label: "预定日期开始",
            //defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd"),
            maxDate: "\'#F{$dp.$D(\\\'" + "logmax" + "\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmin"
        },
        {
            type: "date",//text,select,date,
            name: "endTime",
            label: "预定日期结束",
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
        url: l.getApiUrl("getLargeZjMeetingReservationsList"),
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
	layerArea:['100%', '100%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "recordid",//输入字段名
        },
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
		{
            type: "text",//
            name: "meetingRoomTitle",//
            label: "会议名称",//
            placeholder: "请输入会议名称",
            must: true,
		    immutable:true
        },		
		{
            type: "select",
            name: "meetingRoomId",
            label: "会议地点",
            ajax: {//如果需要接口获取数据需要添加该属性
                api: "getMeetingRoomAllList",//api名称
                valueName: "recordid",//设置value对应的接口字段名称
                textName: "meetingRoomName",//设置text对应的接口字段名称
            },
			must: true,
        },
		{
            type: "date",//
            name: "meetingStartTime",//
            label: "会议开始时间",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "meetingStartTime",
            must: true
        },	
        {
            type: "date",//
            name: "meetingEndTime",//
            label: "会议结束时间",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "meetingEndTime",
            must: true
        },	
		/*
        {
            type: "date",//
            name: "reservationsTime",//
            label: "会议时间",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "reservationsTime",
            must: true
        },
        */		
		{
            type: "text",//
            name: "convenor",//
            label: "召集人",//
            placeholder: "请输入召集人",
            must: true,
		    immutable:true
        },	
		{
            type: "text",//
            name: "contactsUser",//
            label: "联系人",//
            placeholder: "请输入联系人",
            must: true,
		    immutable:true
        },
	    {
            type: "text",//
            name: "contactsTel",//
            label: "联系人电话",//
            placeholder: "请输入联系人电话",
            must: true,
		    immutable:true
        },
		{
            type: "textarea",//
            name: "meetingContent",//
            label: "会议内容",//
            placeholder: "请输入会议内容",
        },
		/*
		{
            type: "select",
            name: "approvalResult",
            label: "会议通知",
            selectList: [//当类型为select时，option配置
			    {
                    value: 0,//输入字段的值
                    text: "不通知",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "通知",//显示中文名
                    selected: false//默认是否选中
                }
            ],
			//immutable:true
        },
		*/
		{
            type: "upload",//
            name: "zjMeetingEnclosureList",//
            label: "会议文件",//
            btnName: "添加文件",
            projectName: "zj-assets111",
            fileType: ["doc","docx", "pdf"],
        }
    ],
    onSave: function (obj, _params) {
            l.ajax('addLargeZjMeetingReservations', _params, function (data) {
                pager.page('remote')
                obj.close()
            })    
    },
    onAdd: function (obj, _params) {     
            l.ajax('addLargeZjMeetingReservations', _params, function (data,message,success) {
				if(success = "true"){
				pager.page('remote')
				obj.close()		
				}else{
                pager.page('remote')	
				}

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
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.confirm("确定删除？", { icon: 0, }, function (index) {
                    l.ajax("batchDeleteUpdateZjMeetingReservations", checkedData, function () {
                        pager.page('remote')
                    })
                    layer.close(index);
                });
            }
            break;
        case "signUp":
            if (checkedData.length == 1) {
				window.location.href = window.lny.domain+"downloadMeetingRoomSingInQrcode?qrcodeName="+checkedData[0].signUpQrcode;			
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
        case "signIn":
            if (checkedData.length == 1) {
				window.location.href = window.lny.domain+"downloadMeetingRoomSingInQrcode?qrcodeName="+checkedData[0].signInQrcode;	
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
			/*
        case "submit":
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            } else {
                var can = true
                for (var index = 0; index < checkedData.length; index++) {
                    var item = checkedData[index];
                    if (item.meetingState !== "0") {
                        can = false
                    }
                }
                if (can) {
                    layer.confirm("一旦提交将不可编辑，确定提交？", { icon: 0, }, function (index) {
                        l.ajax("meetingRoomSubmission", checkedData, function () {
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
			*/
    }
})
/*
function add(){
    var options = {
        type: 2,
        title: "会议室申请",
        content:"addReservations.html?"
    }
    layer.full(layer.open(options))
}
*/
