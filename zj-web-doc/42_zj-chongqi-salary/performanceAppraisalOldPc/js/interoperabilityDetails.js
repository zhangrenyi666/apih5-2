var code = Apih5.getUrlParam('code');
Apih5.setCookie('code', code, 30);
var collaborationId = Apih5.getUrlParam('collaborationId');
var oaUserId = Apih5.getUrlParam('oaUserId');
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
            "targets": [1],
            "data": "userName",
            "title": "姓名",
            "defaultContent": "-"
        },
        {
            "targets": [2],
            "data": "mobile",
            "title": "手机号",
            "defaultContent": "-"
        },
        {
            "targets": [3],
            "data": "departmentName",
            "title": "所在部门",
            "defaultContent": "-"
        },
        {
            "targets": [4],
            "data": "collaborationDetailedScore",
            "title": "评分",
            "defaultContent": "-"
        }            
    ]
});
var form = $('#form').filterfrom({
    conditions: [
        {
            type: "text",
            name: "userName",
            label: "姓名",
            placeholder: "请输入姓名",
        },
        {
            type: "text",
            name: "departmentName",
            label: "所在部门",
            placeholder: "请输入部门",
        }
    ],
    onSearch: function (arr) {
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
                _params[arr[i].name] = arr[i].value;
        }
        pager.page('remote', _params)
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
        url: l.getApiUrl("getZjXmCqjxCollaborationAssessmentDetailedList"),
        params: {
            collaborationtId: collaborationId,
            createUser:oaUserId
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
var myOpenLayer;
function myOpen(title, rowData, url) {
    var rowData = JSON.parse(decodeURI(rowData));
    var options = {
        type: 2,
        title: title,
        area: ['100%', '100%'],
        content: url + ".html?code=" + code + '&collaborationId=' + rowData.collaborationId
    };
    myOpenLayer = layer.open(options);
}