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
            "title": 'No.',
            "width": 25,
            "render": function (data) {
                return data + 1
            }
        },
		{
            "targets": [1],//第几列
            "data": "actionDate",//接口对应字段
            "title": "调令日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd");
            }
        },
        {
            "targets": [2],//第几列
            "data": "perName",//接口对应字段
            "title": "姓名",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "outUnitName",//接口对应字段
            "title": "调出单位",//表头名
            "defaultContent": "-",//默认显示
        },        
        {
            "targets": [4],//第几列
            "data": "proName",//接口对应字段
            "title": "调入单位",//表头名
            "defaultContent": "-",//默认显示
        },	
		{
            "targets": [5],//第几列
            "data": "reportDate",//接口对应字段
            "title": "报道日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd");
            }
        },    
        {
            "targets": [6],//第几列
            "data": "depName",//接口对应字段
            "title": "原部门",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [7],//第几列
            "data": "duty",//接口对应字段
            "title": "原职位",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [8],//第几列
            "data": "postRank",//接口对应字段
            "title": "原岗位等级",//表头名
            "defaultContent": "-",//默认显示
        },        
        {
            "targets": [9],//第几列
            "data": "remarks",//接口对应字段
            "title": "备注",//表头名
            "defaultContent": "-",//默认显示
        }		
    ]
});

var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjSanDjPersonTransferRecordList"),
        params: {},
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
		case "derive"://汇总统计导出
		 var params = {}	
             params.startTime = $('[name = "startTime"]').val();  
			 params.endTime = $('[name = "endTime"]').val();  
             layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
                l.ajax('exportZjSanDjGradeTotal', params, function (res) {
                    layer.alert("导出成功！", { icon: 0 }, function (index) {
                        layer.close(index);
                        window.location.href = res;
                    })
                })
            }); 			
            break
    }
})