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
            "data": "rowIndex",
            "width": 13,
            "title": '<input type="checkbox">',
            "render": function (data) {
                return '<input type="checkbox" name="checkbox" data-rowIndex="' + data + '" >';
            }
        }, {
            "targets": [1],
            "data": "progressDesc",
            "title": "进展描述",
            "width": 200,
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return data.length > 60 ? data.substring(0, 60) + "..." : data;
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
                return data.length > 60 ? data.substring(0, 60) + "..." : data;
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
            "data": function (rowData) { return rowData },
            "render": function (rowData) {
                var checkState = rowData["checkState"]
                var data = rowData[getKeyName("TzDesignChart", "Id")]
                var html = '';
                html += '<span class="dropDown">';
                html += '<a class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">操作</a>';
                html += '<ul class="dropDown-menu menu radius box-shadow">';
                if (checkState == 0) {
                    html += '<li><a href="#" onclick="openDetail(\'TzDesignChart\',\'1\',\'' + data + '\')">编辑</a></li>';
                } else {
                    html += '<li><a href="#" onclick="openDetail2(\'TzDesignChart\',\'' + data + '\')">查看</a></li>';
                }
                html += '<li><a href="#" onclick="openDetail(\'TzDesignChart\',\'2\',\'' + data + '\')">复制新增</a></li>';
                //html += '<li><a href="#" onclick="review(\'TzDesignChart\',\'' + data + '\')">审核</a></li>';
                html += '<li><a href="#" onclick="del(\'TzDesignChart\',\'' + data + '\')">删除</a></li>';
                html += '</ul></span>';
                return html;
            }
        }]
});
var pagerTzDesignChart = $("#pagerTzDesignChart").page({
    remote: {
        url: l.getApiUrl("getTzDesignChartList"),
        params: { companyId: companyId },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                $.each(data, function (index, item) {
                    item.rowIndex = index
                })
                tableTzDesignChart.clear().rows.add(data).draw();
            } else {
                alert(result.message)
            }
        },
    }
});


