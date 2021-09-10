var userId = l.getUrlParam("code") || "";
var bmId = l.getUrlParam("bmId") || "";
var assessmentId = l.getUrlParam("assessmentId") || "";
Apih5.setCookie("code", userId, 30);
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
            render: function(data) {
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
            render: function(data) {
                return data + 1;
            }
        },
        {
            targets: [2], //第几列
            data: "evaluationType", //接口对应字段
            title: "评分题分类", //表头名
            defaultContent: "-", //默认显示
            width: 200,
            render: function(data) {
                //自定义输出
                var r = "未知类型";
                switch (data) {
                    case "0":
                        r = "各单位";
                        break;
                    case "1":
                        r = "总承包部、直属项目部等";
                        break;
					 case "2":
                        r = "项目公司";
                        break;
                }
                return r;
            }
        },
        {
            targets: [3], //第几列
            data: "companyName", //接口对应字段
            title: "公司名称", //表头名
            defaultContent: "-" //默认显示
        },
        {
            targets: [4], //第几列
            data: "selfUserName", //接口对应字段
            title: "自评人姓名", //表头名
            defaultContent: "-" //默认显示
        },
        {
            targets: [5], //第几列
            data: "selfTotalScore", //接口对应字段
            title: "自评得分", //表头名
            defaultContent: "-" //默认显示
        },
        {
            targets: [6], //第几列
            data: "reviewTotalScore", //接口对应字段
            title: "复评得分", //表头名
            defaultContent: "-" //默认显示
        },
        {
            targets: [7], //第几列
            data: "totalScore", //接口对应字段
            title: "综合得分", //表头名
            defaultContent: "-" //默认显示
        },
        {
            targets: [8], //第几列
            data: "createTime", //接口对应字段
            title: "上报时间", //表头名
            defaultContent: "-", //默认显示
            render: function(data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            targets: [9], //第几列
            width: 80,
            data: function(row) {
                return row;
            }, //接口对应字段
            title: "操作", //表头名
            render: function(data) {
                var html = "";
                html += '<span class="dropDown">';
                html +=
                    '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">操作</a>';
                html += '<ul class="dropDown-menu menu radius box-shadow">';
                html +=
                    "<li><a href=\"javascript:void(0);\" onclick=\"openReview('复评','" +
                    data.evaluationType +
                    "','" +
                    data.selfUserId +
                    "','" +
                    "')\">复评</a></li>";
                html +=
                    "<li><a href=\"javascript:void(0);\" onclick=\"openRate('得分率统计','" +
                    data.evaluationType +
                    "','" +
                    data.selfUserId +
                    "','" +
                    "')\">得分率统计</a></li>";
                html += "</ul></span>";
                return html;
            }
        }
    ]
});
var form = $("#form").filterfrom({
    conditions: [
        //表单项配置
        {
            type: "select",
            name: "evaluationType",
            label: "评分题分类",
            selectList: [
                //当类型为select时，option配置
                {
                    value: "", //输入字段的值
                    text: "全部", //显示中文名
                    selected: true //默认是否选中
                },
                {
                    value: 0, //输入字段的值
                    text: "各单位", //显示中文名
                    selected: false //默认是否选中
                },
                {
                    value: 1, //输入字段的值
                    text: "总承包部、直属项目部等", //显示中文名
                    selected: false //默认是否选中
                },
                {
                    value: 2, //输入字段的值
                    text: "项目公司", //显示中文名
                    selected: false //默认是否选中
                }
            ]
        },
        {
            type: "text", //三种形式：text,select,date,
            name: "companyName", //输入字段名
            label: "公司名称", //输入字段对杨的中文名称
            placeholder: "请输入要查询的公司名称"
        },
        {
            type: "text", //三种形式：text,select,date,
            name: "selfUserName", //输入字段名
            label: "自评人姓名", //输入字段对杨的中文名称
            placeholder: "请输入要查询的自评人姓名"
        }
    ],
    onSearch: function(arr) {
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
    onReset: function(arr) {
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
        url: l.getApiUrl("getUserCountListByReviewer"),
        params: {
            evaluationType: "",
            companyName: "",
            selfUserName: "",
            assessmentId: assessmentId
        },
        success: function(result) {
            if (result.success) {
                var data = result.data;
                $.each(data, function(index, item) {
                    item.rowIndex = index;
                });
                table
                    .clear()
                    .rows.add(data)
                    .draw();
            } else {
                layer.alert(result.message, { icon: 0 }, function(index) {
                    layer.close(index);
                });
            }
        }
    }
});

var detailLayer = $("#detailLayer").detailLayer({
    conditions: [
        {
            type: "hidden", //五种形式：text,select,date,hidden,textarea,
            name: "evaluationId" //输入字段名
        },
        {
            type: "text", //
            name: "companyName", //
            label: "公司名称", //
            placeholder: "请输入公司名称"
        },
        {
            type: "text", //
            name: "selfUserName", //
            label: "自评人姓名", //
            placeholder: "请输入自评人姓名"
        },
        {
            type: "text", //
            name: "selfTotalScore", //
            label: "自评总分", //
            placeholder: "请输入自评总分"
        }
    ],
    onSave: function(obj, _params) {
        var params = _params;
        params["userId"] = userId;
        params["bmId"] = bmId;
        l.ajax("updateUserCount", params, function(data) {
            pager.page("remote");
            obj.close();
        });
    },
    onAdd: function(obj, _params) {
        var params = _params;
        params["userId"] = userId;
        params["bmId"] = bmId;
        l.ajax("addUserCount", params, function(data) {
            pager.page("remote");
            obj.close();
        });
    }
});
$("body").on("click", "button", function() {
    var checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "export":
            //导出
            l.ajax(
                "exportUserCountListByReviewer",
                { assessmentId: assessmentId },
                function(data, message, success) {
                    if (success) {
                        layer.alert(message);
                        window.location.href = data;
                    } else {
                        layer.alert(result.message, { icon: 0 }, function(
                            index
                        ) {
                            layer.close(index);
                        });
                    }
                }
            );
            break;
        case "add":
            detailLayer.open();
            break;
        case "edit":
            if (checkedData.length == 1) {
                detailLayer.open(checkedData[0]);
            } else if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0 }, function(index) {
                    layer.close(index);
                });
            } else {
                layer.alert("只能选择一个", { icon: 0 }, function(index) {
                    layer.close(index);
                });
            }
            break;
        case "del":
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0 }, function(index) {
                    layer.close(index);
                });
            } else {
                l.delTableRow(
                    "evaluationId",
                    "ztLwLoborList",
                    "batchDeleteUserCount",
                    checkedData,
                    function(data) {
                        pager.page("remote");
                    }
                ); //  删除url地址
            }
            break;
    }
});

function openRate(title, evaluationType, selfUserId) {
    var options = {
        type: 2,
        title: title,
        content:
            "rate" +
            ".html?evaluationType=" +
            evaluationType +
            "&selfUserId=" +
            selfUserId +
            "&userId=" +
            userId +
            "&bmId=" +
            bmId +
            "&assessmentId=" +
            assessmentId
    };
    layer.full(layer.open(options));
}
function openReview(title, evaluationType, selfUserId) {
    var options = {
        type: 2,
        title: title,
        content:
            "review" +
            ".html?evaluationType=" +
            evaluationType +
            "&selfUserId=" +
            selfUserId +
            "&userId=" +
            userId +
            "&bmId=" +
            bmId +
            "&assessmentId=" +
            assessmentId
    };
    layer.full(layer.open(options));
}
