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
            "width": 25,
            "title": 'No.',
            "render": function (data) {
                return data + 1
            }
        },
        {
            "targets": [2],//第几列
            "data": "leaderName",//接口对应字段
            "title": "负责人",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data,display,rowData) {
                if(rowData.showFlag === '1'){
                    return '<a style="color:blue;" href="javascript:void(0);" onclick="myOpen2(\'周工作完成情况详情\',\'' + rowData.weekPlanId + '\')">'+ data +'</a>'
                }else{
                    return data;
                }
            }
        },
        {
            "targets": [3],//第几列
            "data": "undertakeDeptName",//接口对应字段
            "title": "承办部门",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "reportDate",//接口对应字段
            "title": "填报日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd");
            }
        },
        {
            "targets": [5],//第几列
            "width": 100,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名
            "render": function (data) {
                var html = '';
                html += '<span class="dropDown">'
                html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">操作</a>';
	            html += '<ul class="dropDown-menu menu radius box-shadow">'	
                html += '<li><a style="color:blue;" href="javascript:void(0);" onclick="myOpen(\'周工作任务事项列表\',\'' + data.weekPlanId + '\')"">周工作计划</a></li>';
                html += '</ul></span>'
                return html;
            }
        }
    ]
});
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjOataskWeekPlanTotalList"),
        params: {},
        success: function (result) {
            if (result.success) {
                var data = result.data;
                allData = data;
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
        case "export":
             window.location.href = 'http://192.168.1.223:8081/ureport/excel?_u=file:zjOataskWeekPlanTotalList.xml&url=' + l.domain + '&delFlag=' + '0';
            break;
    }
}); 
function myOpen(title, id) {
    var options = {
        type: 2,
        title: title,
        content: "zjWeeklyWorkTasksDetailList.html?id=" + id+"&code="+code
    }
    layer.full(layer.open(options))
}
function myOpen2(title, id) {
    var options = {
        type: 2,
        title: title,
        content: "zjCompletionOfWeeklyWorkPlanDetail.html?id=" + id+"&code="+code
    }
    layer.full(layer.open(options))
}