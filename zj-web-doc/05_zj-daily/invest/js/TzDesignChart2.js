var tableTzDesignChart = $('#tableTzDesignChart').DataTable({
    "info": false,
    "lengthChange": false,
    "ordering": false, //是否开启DataTables的高度自适应，当数据条数不够分页数据条数的时候，插件高度是否随数据条数而改变     
    "paging": false,
    "processing": false,
    "searching": false,
    "autoWidth": false,
    "columnDefs": [
        {
            "targets": [0],
            "data": getKeyName("TzDesignChart","Id"),
            "width": 13,
            "title": '',
            "render": function (data) {
                return '<input type="checkbox" name="' + getKeyName("TzDesignChart","Id") + '" data-id="' + data + '">';
            }
        }, {
            "targets": [1],
            "data": "progressDesc",
            "title": "进展描述",
            "width": 200,
            "defaultContent": "-",//默认显示
			"render": function (data) {
                return data.length > 60 ? data.substring(0, 60)+"..." : data;
            }
        }, {
            "targets": [2],
            "data": "changes",
            "title": "有无变化",
            "defaultContent": "-",
            "render": function (data) {
                return data == 0 ? "无" : data == 1 ? "有" : "未知";
            }
        }, {
            "targets": [3],
            "data": "changeDesc",
            "title": "变化描述",
            "width": 200,
            "defaultContent": "-",//默认显示
			"render": function (data) {
                return data.length > 60 ? data.substring(0, 60)+"..." : data;
            }
        }, {
            "targets": [4],
            "data": "checkState",
            "title": "审核状态",
            "defaultContent": "-",
            "render": function (data) {
                return data == 0 ? "未审核" : data == 1 ? "已审核" : "未知";
            }
        }, {
            "targets": [5],
            "data": "fillDate",
            "title": "周报日期",
            "defaultContent": "-",
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd");
            }
        }, {
            "targets": [6],
            "data": "modifyTime",
            "title": "操作日期",
            "defaultContent": "-",
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        }, {
            "targets": [7],
            "data": "accessoryList",
            "title": "附件",
            "defaultContent": "-",
            "render": function (data) {
                
                var arr = '';
                $.each(data, function (i, v) {
                    arr += '<a href="' + v.url + '">' + v.fileName + '</a><br>'
                })
                return arr;
            }
        }, {
            "targets": [8],
            "title": "操作",
            "width": 80,
            "data": getKeyName("TzDesignChart","Id"),
            "render": function (data) {
                var html = '';
                html += '<span class="dropDown">';
                html += '<a class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">操作</a>';
                html += '<ul class="dropDown-menu menu radius box-shadow">';
                html += '<li><a href="#" onclick="openDetail(\'TzDesignChart\',\'2\',\'' + data + '\')">详情</a></li>';
                //html += '<li><a href="#" onclick="openDetail(\'TzDesignChart\',\'2\',\'' + data + '\')">复制新增</a></li>';
                //html += '<li><a href="#" onclick="restReview(\'TzDesignChart\',\'' + data + '\')">反审核</a></li>';
                //html += '<li><a href="#" onclick="del(\'TzDesignChart\',\'' + data + '\')">删除</a></li>';
                html += '</ul></span>';
                return html;
            }
        }]
});
var pagerTzDesignChart = $("#pagerTzDesignChart").page({
    remote: {
        url: l.getApiUrl("getTzDesignChartList2"),
        params: { companyId: companyId, startDate: startDate, endDate: endDate },
        success: function (result) {
            if (result.success) {
                tableTzDesignChart.clear().rows.add(result.data).draw();
            } else {
                alert(result.message)
            }
        },
    }
});


