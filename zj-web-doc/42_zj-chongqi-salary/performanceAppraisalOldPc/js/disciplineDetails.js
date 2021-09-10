var code = Apih5.getUrlParam('code');
Apih5.setCookie('code', code, 30);
var disciplineId = Apih5.getUrlParam('disciplineId');
var deptHeadOption = Apih5.getUrlParam('deptHeadOption');
var executiveLeaderOption = Apih5.getUrlParam('executiveLeaderOption');
var detailLayerOut = $('#detailLayerOut').detailLayer({
    layerArea: ["100%", "100%"],
    customBtnGroup: {
        btns: []
    },
    conditions: [
        {
            type: "textarea",
            name: "deptHeadOption",
            label: "部门负责人意见",
            defaultValue: deptHeadOption,
            placeholder: "请输入"
        },
        {
            type: "textarea",
            name: "executiveLeaderOption",
            label: "主管领导意见",
            defaultValue: executiveLeaderOption,
            placeholder: "请输入"
        }
    ]
})
$('textarea[name="deptHeadOption"]').attr('disabled', true);
$('textarea[name="executiveLeaderOption"]').attr('disabled', true);
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
            "data": "oaUserName",
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
            "data": "oaOrgName",
            "title": "所在部门",
            "defaultContent": "-"
        },
        {
            "targets": [4],
            "data": "score",
            "title": "得分",
            "defaultContent": "-"
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [
        {
            type: "text",
            name: "oaUserName",
            label: "姓名",
            placeholder: "请输入姓名",
        },
        {
            type: "text",
            name: "oaOrgName",
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
        url: l.getApiUrl("getZjXmCqjxDisciplineAssessmentMemberList"),
        params: {
            disciplineId: disciplineId
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