var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
var id = Apih5.getUrlParam('id')
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
            "width": 100,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名
			"render": function (data) {
                var html = '';
                html += '<span class="dropDown">'
                html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" onclick="myOpen(\'查看人员\',\'' + data.recordid + '\',\'' + 'zjMeetingAttendPersonnelReceiptDetail' + '\')">查看人员</a>';
                html += '</span>'
                return html;
            }
        }
    ]
});	
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
    var checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "export"://导出
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            }else if (checkedData.length == 1) {
                layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
                    l.ajax('zjMeetingReceiptExportExcel',checkedData[0],function(res){
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
			/*
            var params = {};
			params.reservationsId = id;
            layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
                l.ajax('zjMeetingReceiptExportExcel', params, function (res) {
                    layer.alert("导出成功！", { icon: 0 }, function (index) {
                        layer.close(index);
                        window.location.href = res;
                    })
                })
            });
			*/
            break;
    }
})
function myOpen(title, id, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id +"&code=" + code
    }
    myOpenLayer = layer.open(options)
    layer.full(myOpenLayer)
}
