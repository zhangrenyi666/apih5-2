var code = l.getUrlParam("code") || "";
var bmId = l.getUrlParam("bmId") || "";
Lny.setCookie("code", code, 30);
var table = $("#table").DataTable({
    info: false, //是否显示数据信息
    paging: false, //是否开启自带分页
    ordering: false, //是否开启DataTables的高度自适应
    processing: false, //是否显示‘进度’提示
    searching: false, //是否开启自带搜索
    autoWidth: false, //是否监听宽度变化
    columnDefs: [
        //表格列配置
        {
            targets: [0],
            data: "rowIndex",
            width: 13,
            title: '<input type="checkbox">',
            render: function (data) {
                return (
                    '<input type="checkbox" name="checkbox" data-rowIndex="' +
                    data +
                    '" >'
                );
            }
        },
        {
            targets: [1],
            data: "rowIndex",
            title: "No.",
            width: 25,
            render: function (data) {
                return data + 1;
            }
        },

        {
            targets: [2], //第几列
            data: "title", //接口对应字段
            title: "考核标题", //表头名
            defaultContent: "-" //默认显示
        },
        {
            targets: [3], //第几列
            data: "require", //接口对应字段
            title: "考核要求", //表头名
            defaultContent: "-" //默认显示
        },

        {
            targets: [4], //第几列
            data: "status", //接口对应字段
            title: "考核活动状态", //表头名 0:已发起 1:进行中 2:已结束
            defaultContent: "-", //默认显示
            render: function (data) {
                //自定义输出
                var r = "未知类型";
                switch (data) {
                    case "0":
                        r = "已创建";
                        break;
                    case "1":
                        r = "进行中";
                        break;
                    case "2":
                        r = "已结束";
                        break;
                }
                return r;
            }
        },
        {
            targets: [5], //第几列
            data: "startTime", //接口对应字段
            title: "考核发起时间", //表头名
            defaultContent: "-", //默认显示
            render: function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        // {
        //     targets: [6], //第几列
        //     data: "endTime", //接口对应字段
        //     title: "考核截止时间", //表头名
        //     defaultContent: "-", //默认显示
        //     render: function(data) {
        //         return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
        //     }
        // },
        {
            targets: [6], //第几列
            width: 80,
            data: function (row) {
                return row;
            }, //接口对应字段
            title: "操作", //表头名
            render: function (data) {
                var html = "";
                html += '<span class="dropDown">';
                html +=
                    '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">操作</a>';
                html += '<ul class="dropDown-menu menu radius box-shadow">';
                html +=
                    "<li><a href=\"javascript:void(0);\" onclick=\"openReview('zjScoreEditList', '各单位信息化系统情况打分表','" +
                    data.assessmentId +
                    "')\">打分</a></li>";
                return html;
            }
        }
    ]
});

var pager = $("#pager").page({
    remote: {
        //ajax请求配置
        url: l.getApiUrl("getZjScoreAnnualAssessmentHasToDoList"),
        params: {},
        success: function (result) {
            if (result.success) {
                var data = result.data;
                $.each(data, function (index, item) {
                    item.rowIndex = index;
                });
                table
                    .clear()
                    .rows.add(data)
                    .draw();
            } else {
                layer.alert(result.message, { icon: 0 }, function (index) {
                    layer.close(index);
                });
            }
        }
    }
});
function openReview(url, title, assessmentId) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?assessmentId=" + assessmentId + "&code=" + code
    };
    layer.full(layer.open(options));
}
