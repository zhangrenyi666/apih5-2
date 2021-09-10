var code = l.getUrlParam("code") || "";
var userId = l.getUrlParam("code") || "";
var bmId = l.getUrlParam("bmId") || "";
var assessmentId = l.getUrlParam("assessmentId") || "";
var companyId = l.getUrlParam("companyId") || "";
Lny.setCookie("code", userId, 30);

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
            data: "content", //接口对应字段
            title: "打分项内容", //表头名
            defaultContent: "-", //默认显示
        },
        {
            targets: [3], //第几列
            data: "rule", //接口对应字段
            title: "打分规则", //表头名
            defaultContent: "-", //默认显示
        },
        {
            targets: [4], //第几列
            data: "standardScore", //接口对应字段
            title: "标准分明细", //表头名
            defaultContent: "-", //默认显示
        },
        {
            targets: [5], //第几列
            data: "departmentType", //接口对应字段
            title: "考核部门分类", //表头名
            defaultContent: "-", //默认显示
            render: function (data) {
                //自定义输出
                var r = "未知类型";
                switch (data) {
                    case "0":
                        r = "办公室";
                        break;
                    case "1":
                        r = "合同管理与结算中心";
                        break;
                    case "2":
                        r = "资源管理与采购中心";
                        break;
                    case "3":
                        r = "工程管理部";
                        break;
                    case "4":
                        r = "科学技术质量部";
                        break;
                    case "5":
                        r = "安全监督部";
                        break;
                    case "6":
                        r = "运营管理部";
                        break;
                    case "7":
                        r = "法律部";
                        break;
                    case "8":
                        r = "投资事业部";
                        break;
                }
                return r;
            }
        },
        {
            targets: [6], //第几列
            data: "moduleType", //接口对应字段
            title: "打分项模块分类", //表头名
            defaultContent: "-", //默认显示
            render: function (data) {
                //自定义输出
                var r = "未知类型";
                switch (data) {
                    case "0":
                        r = "一公局自建";
                        break;
                    case "1":
                        r = "隧道局自建";
                        break;
                    case "2":
                        r = "集团统建";
                        break;
                }
                return r;
            }
        }
        // {
        //     "targets": [8],//第几列
        //     "width": 80,
        //     "data": function (row) {
        //         return row
        //     },//接口对应字段
        //     "title": "操作",//表头名
        //     "render": function (data) {
        //         var html = '';
        //         html += '<span class="dropDown">'
        //         html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">操作</a>';
        //         html += '<ul class="dropDown-menu menu radius box-shadow">';
        //         html += '</ul></span>'
        //         return html;
        //     }
        // }
    ]
});
var form = $("#form").filterfrom({
    conditions: [
        //表单项配置
        {
            type: "text", //三种形式：text,select,date,
            name: "content", //输入字段名
            label: "打分项内容", //输入字段对杨的中文名称
            placeholder: "请输入要查询的打分项内容"
        },
        {
            type: "select",
            name: "moduleType",
            label: "打分项模块分类",
            selectList: [
                //当类型为select时，option配置
                {
                    value: "", //输入字段的值
                    text: "全部", //显示中文名
                    selected: true //默认是否选中
                },
                {
                    value: 0, //输入字段的值
                    text: "一公局自建", //显示中文名
                    selected: false //默认是否选中
                },
                {
                    value: 1, //输入字段的值
                    text: "隧道局自建", //显示中文名
                    selected: false //默认是否选中
                },
                {
                    value: 2, //输入字段的值
                    text: "集团统建", //显示中文名
                    selected: false //默认是否选中
                }
            ]
        }
    ],
    onSearch: function (arr) {
        //搜索按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            // if (arr[i].name == "startDate" || arr[i].name == "endDate") {
            //     if (arr[i].value) {
            //         _params[arr[i].name] = arr[i].name == "endDate" ? l.dateToUnix(arr[i].value) + 1000 * 60 * 60 * 24 : l.dateToUnix(arr[i].value);
            //     } else {
            //         _params[arr[i].name] = "";
            //     }
            // } else {
            _params[arr[i].name] = arr[i].value;
            // }
        }
        pager.page("remote", 0, _params);
    },
    onReset: function (arr) {
        //重置按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            //     if (arr[i].name == "startDate" || arr[i].name == "endDate") {
            //         if (arr[i].value) {
            //             _params[arr[i].name] = arr[i].name == "endDate" ? l.dateToUnix(arr[i].value) + 1000 * 60 * 60 * 24 : l.dateToUnix(arr[i].value);
            //         } else {
            //             _params[arr[i].name] = "";
            //         }
            //     } else {
            _params[arr[i].name] = arr[i].value;
            // }
        }
        pager.page("remote", 0, _params);
    }
});
var pager = $("#pager").page({
    remote: {
        //ajax请求配置
        url: l.getApiUrl("getZjScoreScoringItemListByCompanyId"),
        params: {
            content: "",
            moduleType: "",
            companyId: companyId
        },
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
                layer.alert(
                    result.message,
                    {
                        icon: 0
                    },
                    function (index) {
                        layer.close(index);
                    }
                );
            }
        }
    }
});
function openReview(url, title, assessmentId,zjScoreCompanyList) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?assessmentId=" + assessmentId + "&zjScoreCompanyList=" + zjScoreCompanyList + "&code=" + code
    };
    layer.full(layer.open(options));
}
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "del":
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            } else {
                l.ajax("batchDeleteUpdateZjScoreCompanyDetail", checkedData, function (
                    data,
                    message,
                    success
                ) {
                    if (success) {
                        pager.page("remote");
                        parent.pager.page('remote');
                        layer.alert(message);
                    } else {
                        layer.alert(message, { icon: 0 }, function (index) {
                            layer.close(index);
                        });
                    }
                });
            }
            break;
        case "addAll":
            var zjScoreCompanyList = companyId;
            openReview('zjZpEvaluationAddList', '批量添加打分项', assessmentId, zjScoreCompanyList);
            break;
    }
});
