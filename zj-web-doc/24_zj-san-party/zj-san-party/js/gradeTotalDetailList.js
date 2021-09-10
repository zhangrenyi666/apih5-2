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
            "data": "perName",//接口对应字段
            "title": "姓名",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [2],//第几列
            "data": "gradeTypeName",//接口对应字段
            "title": "奖励名称",//表头名
            "defaultContent": "-",//默认显示
        }, 
		{
	            	   "targets": [3],//第几列
	            	   "data": "date",//接口对应字段
	            	   "title": "获得时间",//表头名
	            	   "defaultContent": "-",//默认显示
	            	   "render": function (data) {
	            		   return l.dateFormat(new Date(data), "yyyy-MM-dd");
	            	   }
	               },
        {
            "targets": [4],//第几列
            "data": "score",//接口对应字段
            "title": "分值",//表头名
            "defaultContent": "-",//默认显示
        }				
		
    ]
});
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjSanDjGradeTotalDetailList"),
        params: {
		 perName: l.getUrlParam("id")
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

