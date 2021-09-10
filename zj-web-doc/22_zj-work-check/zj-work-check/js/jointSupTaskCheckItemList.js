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
            "data": "checkItem",//接口对应字段
            "title": "检查项",//表头名
            "defaultContent": "-",//默认显示
        },		
        {
            "targets": [2],//第几列
            "data": "checkRequir",//接口对应字段
            "title": "检查要点要求",//表头名
            "defaultContent": "-",//默认显示
        },        
        {
            "targets": [3],//第几列
            "data": "depContactPerName",//接口对应字段
            "title": "各部们联络人",//表头名
            "defaultContent": "-",//默认显示
        },							        
		{
            "targets": [4],//第几列
            "width": 100,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名			
            "render": function (data) {
				//data.checkObjectNameList = JSON.stringify(data.checkObjectNameList);
				//console.log(data.checkObjectNameList)
                var html = '';
                html += '<span class="dropDown">'
                html += '<a style="color:blue;" href="javascript:void(0);" onclick="myOpen(\'检查项详情添加\',\'' + data.taskId + '\',\'' + data.checkItem + '\',\'' + data.checkObjectNameList + '\',\'' + 'jointSupTaskCheckItemDetailList' + '\')">检查项详情添加</a>';
                html += '</ul></span>'
                return html;
            }
        }
    ]
});


var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjLhddJointSupTaskList"),
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

function myOpen(title, id,checkItem, checkObjectNameList,url) {
	//checkObjectNameList = JSON.parse(checkObjectNameList);
	//console.log(checkObjectNameList);
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id+"&checkItem="+encodeURI(checkItem)+"&code="+code+"&assignUnit="+checkObjectNameList
    }
    layer.full(layer.open(options))
}