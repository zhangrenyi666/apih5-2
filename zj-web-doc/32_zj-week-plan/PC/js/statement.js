var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
var table = table = $('#table').DataTable({
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
            "width": 75,
            "render": function (data) {
                return data + 1
            }
        },{
            "targets": [1],//第几列
            "data": "oaMemberName",//接口对应字段
            "title": "领导姓名",//表头名
            "width":250,
            "defaultContent": "-"//默认显示
        },{
            "targets": [2],//第几列
            "data": "weekName",//接口对应字段
            "title": "日期",//表头名
            "width":250,
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "mondayContent",//接口对应字段
            "title": "事项",
            "defaultContent": "-",//默认显示
        }
    ]
});

var pager = $("#pager").page({
    remote: {// ajax请求配置
        url: l.getApiUrl("getZjLdzbLeaderPlanReportForm"),
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
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "select",
            name: "oaMemberId",
            label: "领导姓名",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getZjLdzbOaMemberAllSelectList",
                valueName: "otherId",
                textName: "oaUserName",
            },
            immutableAdd: true
        },
        {
            type: "date",//text,select,date,
            name: "startTime",
            label: "开始时间",
            id: "logmin",
            immutableAdd: true
        },
        {
            type: "date",//text,select,date,
            name: "endTime",
            label: "结束时间",
            id: "logmax",
            immutableAdd: true
        }
    ],
    onSearch: function (arr) {//搜索按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].name == "startTime" || arr[i].name == "endTime") {
                if (arr[i].value) {
                    _params[arr[i].name] = l.dateFormat(new Date(new Date(arr[i].value).getTime()), "yyyy-MM-dd");
                } else {
                    _params[arr[i].name] = "";
                }
            } else {
                _params[arr[i].name] = arr[i].value;
            }
        }
        pager.page('remote', 0, _params)      
    },
    onReset: function (arr) {//重置按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
        pager.page('remote', _params)
    }
})
$('.btn').parent().append('<button data-name="derive" class="btn ml-20  btn-primary btn-v" type="button"><i class="Hui-iconfont">&#xe645;</i> 导出</button>');
$('.lin').append('<button class="btn btn-primary btn-h">返回</button>');
$("body").on("click", "button", function () {
    var name = $(this).attr("data-name");
    switch (name) {
        case "derive"://导出
            var params = {};
            params.oaMemberId = $('[name = "oaMemberId"]').val();
            params.startTime = $('[name = "startTime"]').val();
            params.endTime = $('[name = "endTime"]').val();
            layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
                l.ajax('ldzbLeaderPlanReportFormExport', params, function (res) {
                    layer.alert("导出成功！", { icon: 0 }, function (index) {
                        layer.close(index);
                        window.location.href = res;
                    })
                })
            });
            break;
    }
})
$('.btn-h').click(function(){
    history.go(-1);
})