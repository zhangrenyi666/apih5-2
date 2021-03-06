var code = l.getUrlParam("code") || "";
var bmId = l.getUrlParam("bmId") || "";
Apih5.setCookie("code", code, 30);
var table = null;
var form = $("#form").filterfrom({
    conditions: [
        //表单项配置
        {
            type: "select",
            name: "status",
            label: "考核活动状态", //0:已发起 1:进行中 2:已结束
            selectList: [
                //当类型为select时，option配置
                {
                    value: "", //输入字段的值
                    text: "全部", //显示中文名
                    selected: true //默认是否选中
                },
                {
                    value: 0, //输入字段的值
                    text: "已发起", //显示中文名
                    selected: false //默认是否选中
                },
                {
                    value: 1, //输入字段的值
                    text: "已结束", //显示中文名
                    selected: false //默认是否选中
                }
            ]
        },
        {
            type: "text", //三种形式：text,select,date,
            name: "title", //输入字段名
            label: "考核标题", //输入字段对杨的中文名称
            placeholder: "请输入要查询的考核标题"
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
var pager = null;
getUserType();
//身份判断
function getUserType() {
    l.ajax("isOrNotZjZpEvaluationAuditor", {}, function (
        data,
        message,
        success
    ) {
        if (success) {
            var isAuditor = data.isAuditor; //0不是  1是
            pager = $("#pager").page({
                remote: {
                    //ajax请求配置
                    url: l.getApiUrl("getZjZpAnnualAssessmentList"),
                    params: {},
                    success: function (result) {
                        if (result.success) {
                            var data = result.data;
                            // for (var i = 0; i < result.data.length; i++) {
                            //     if (result.data[i].assessmentId !== '1D0OP6L232CVFA330B0A00007D892624') {
                            //         data[0] = result.data[3];
                            //         data[1] = result.data[1];
                            //         data[2] = result.data[2];
                            //         data[3] = result.data[0];
                            //     }
                            // }
                            $.each(data, function (index, item) {
                                item.rowIndex = index;
                            });
                            table
                                .clear()
                                .rows.add(data)
                                .draw();
                        } else {
                            layer.alert(result.message, { icon: 0 }, function (
                                index
                            ) {
                                layer.close(index);
                            });
                        }
                    }
                }
            });

            table = $("#table").DataTable({
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
                        width: 200,
                        render: function (data) {
                            //自定义输出
                            var r = "未知类型";
                            switch (data) {
                                case "0":
                                    r = "已发起";
                                    break;
                                case "1":
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
                            return l.dateFormat(
                                new Date(data),
                                "yyyy-MM-dd HH:mm:ss"
                            );
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
                    // }
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
                            html +=
                                '<ul class="dropDown-menu menu radius box-shadow">';
                            if (isAuditor == "0") {
                                html +=
                                    "<li><a href=\"javascript:void(0);\" onclick=\"openReview('self', '自评审核','" +
                                    data.assessmentId +
                                    "')\">自评</a></li>";
                            } else {
                                html +=
                                    "<li><a href=\"javascript:void(0);\" onclick=\"openReview('self', '自评审核','" +
                                    data.assessmentId +
                                    "')\">自评审核</a></li>";
                            }
                            html += "</ul></span>";
                            return html;
                        }
                    }
                ]
            });
        } else {
            layer.alert(message);
        }
    });
}

var detailLayer = $("#detailLayer").detailLayer({
    conditions: [
        {
            type: "hidden", //五种形式：text,select,date,hidden,textarea,
            name: "assessmentId" //输入字段名
        },
        {
            type: "text", //
            name: "title", //
            label: "考核标题", //
            placeholder: "请输入考核标题",
            must: true
        },
        {
            type: "textarea", //
            name: "require", //
            label: "考核要求", //
            placeholder: "请输入考核要求",
            must: true
        },
        {
            type: "date", //
            name: "startTime", //
            label: "考核发起时间", //
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "endTime",
            must: true
            // immutableAdd: true
        },
        // {
        //     type: "date", //
        //     name: "endTime", //
        //     label: "考核截止时间", //
        //     dateFmt: "yyyy-MM-dd HH:mm:ss",
        //     defaultValue: new Date(),
        //     id: "endTime",
        //     must: true
        //     // immutableAdd: true
        // },
        {
            type: "select",
            name: "status",
            label: "考核活动状态", //0:已发起 1:进行中 2:已结束
            defaultValue: 0,
            selectList: [
                //当类型为select时，option配置
                {
                    value: 0, //输入字段的值
                    text: "已发起", //显示中文名
                    selected: false //默认是否选中
                },
                {
                    value: 1, //输入字段的值
                    text: "已结束", //显示中文名
                    selected: false //默认是否选中
                }
            ]
        }
    ],
    onSave: function (obj, _params) {
        var params = _params;
        l.ajax("updateZjZpAnnualAssessment", params, function (data) {
            pager.page("remote");
            obj.close();
        });
    },
    onAdd: function (obj, _params) {
        var params = _params;
        l.ajax("addZjZpAnnualAssessment", params, function (data) {
            pager.page("remote");
            obj.close();
        });
    }
});
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            detailLayer.open();
            break;
        case "edit":
            if (checkedData.length == 1) {
                detailLayer.open(checkedData[0]);
            } else if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.alert("只能选择一个", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            }
            break;
        case "del":
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            } else {
                l.ajax(
                    "batchDeleteUpdateZjZpAnnualAssessment",
                    checkedData,
                    function (data, message, success) {
                        if (success) {
                            pager.page("remote");
                            layer.alert(message);
                        } else {
                            layer.alert(message, { icon: 0 }, function (index) {
                                layer.close(index);
                            });
                        }
                    }
                );
                //
                // l.delTableRow(
                //     "evaluationId",
                //     "ztLwLoborList",
                //     "batchDeleteUserCount",
                //     checkedData,
                //     function(data) {
                //         pager.page("remote");
                //     }
                // ); //  删除url地址
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
            bmId
    };
    layer.full(layer.open(options));
}
function openReview(url, title, assessmentId) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?assessmentId=" + assessmentId + "&code=" + code
    };
    layer.full(layer.open(options));
}
