// var userId = l.getUrlParam("userId") || ""
// var bmId = l.getUrlParam("bmId") || ""
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
            "title": 'No.',
            "width": 25,
            "render": function (data) {
                return data + 1
            }
        },
        {
            "targets": [2],//第几列
            "data": "enquiryTitle",//接口对应字段
            "title": "问询标题",//表头名
            "defaultContent": "-",//默认显示
            "width": 150,
        },
        // {
        //     "targets": [3],//第几列
        //     "data": "enquiryContent",//接口对应字段
        //     "title": "问询内容",//表头名
        //     "defaultContent": "-",//默认显示
        //     "width": 150,
        // },
        {
            "targets": [3],//第几列
            "data": "enquiryDiff",//接口对应字段
            "title": "问询区分",//表头名
            "defaultContent": "-",//默认显示
            "width": 100,
            "render": function (data) {//自定义输出
                var r = "未知类型";
                switch (data) {
                    case "0":
                        r = "技术交底"
                        break
                    case "1":
                        r = "关键工序管控"
                        break
                    case "2":
                        r = "非关键工序管控"
                        break
                    case "3":
                        r = "疑难问题"
                        break
                    case "4":
                        r = "互动"
                        break
                }
                return r
            }
        },
        {
            "targets": [4],//第几列
            "data": "levelDiff",//接口对应字段
            "title": "问询级别区分",//表头名
            "defaultContent": "-",//默认显示
            "width": 100,
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "1":
                        r = "紧急"
                        break
                    case "2":
                        r = "普通"
                        break
                }
                return r
            }
        },
        {
            "targets": [5],//第几列
            "data": "companyName",//接口对应字段  
            "title": "公司名称",//表头名
            "defaultContent": "-",//默认显示
            "width": 100,
        },
        {
            "targets": [6],//第几列
            "data": "departmentName",//接口对应字段  
            "title": "部门名称",//表头名
            "defaultContent": "-",//默认显示
            "width": 100,
        },
        {
            "targets": [7],//第几列
            "data": "enquiryUserName",//接口对应字段
            "title": "首次问询者",//表头名
            "defaultContent": "-",//默认显示
            "width": 100,
        },
        {
            "targets": [8],//第几列
            "data": "answerUserName",//接口对应字段
            "title": "首次回答者",//表头名
            "defaultContent": "-",//默认显示
            "width": 100,
        },
        {
            "targets": [9],//第几列
            "data": "endUserName",//接口对应字段
            "title": "结束者",//表头名
            "defaultContent": "-",//默认显示
            "width": 100,
        },

        {
            "targets": [10],//第几列
            "data": "longitude",//接口对应字段
            "title": "经度",//表头名
            "defaultContent": "-",//默认显示
            "width": 100,
        },
        {
            "targets": [11],//第几列
            "data": "latitude",//接口对应字段
            "title": "纬度",//表头名
            "defaultContent": "-",//默认显示
            "width": 100,
        },


        {
            "targets": [12],//第几列
            "data": "position",//接口对应字段
            "title": "地理位置",//表头名
            "defaultContent": "-",//默认显示
            "width": 100,
        },
        {
            "targets": [13],//第几列
            "data": "createTime",//接口对应字段
            "title": "起始时间",//表头名
            "defaultContent": "-",//默认显示
            "width": 120,
            "render": function (data) {
                if (data) {
                    return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
                } else {
                    return "-";
                }

            }
        },
        {
            "targets": [14],//第几列
            "data": "endDate",//接口对应字段
            "title": "结束时间",//表头名
            "defaultContent": "-",//默认显示
            "width": 120,
            "render": function (data) {
                if (data) {
                    return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
                } else {
                    return "-";
                }
            }
        },
        {
            "targets": [15],//第几列
            "width": 50,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名
            "render": function (data) {
                // Lny.log('数据是',data)
                var html = '';
                html += '<span class="dropDown">'
                html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">操作</a>';
                html += '<ul class="dropDown-menu menu radius box-shadow">';
                html += '<li><a href="javascript:void(0);" onclick="  myOpen(\'问询详情\',\'' + data.enquiryId + '\',\'' + 'bgEnquiryDetail' + '\')  ">详情</a></li>';
                html += '</ul></span>'
                return html;
            }
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "enquiryTitle",//输入字段名
            label: "问询标题",//输入字段对杨的中文名称
            placeholder: "请输入问询标题",
        },
        {
            type: "select",//
            name: "companyOaId",//
            label: "公司名称",
            placeholder: "请选择公司名称",
            must: true,
            ajax: {//如果需要接口获取数据需要添加该属性
                api: "getSysCompanySelect",//api名称
                valueName: "companyId",//设置value对应的接口字段名称
                textName: "companyName",//设置text对应的接口字段名称
            }
        },
        {
            type: "text",//三种形式：text,select,date,
            name: "departmentName",//输入字段名
            label: "部门名称",//输入字段对杨的中文名称
            placeholder: "请输入部门名称",
        },
        {
            type: "text",//三种形式：text,select,date,
            name: "enquiryUserName",//输入字段名
            label: "首次问询者",//输入字段对杨的中文名称
            placeholder: "请输入首次问询者姓名",
        },
        {
            type: "text",//三种形式：text,select,date,
            name: "answerUserName",//输入字段名
            label: "首次回答者",//输入字段对杨的中文名称
            placeholder: "请输入首次回答者姓名",
        },
        {
            type: "text",//三种形式：text,select,date,
            name: "endUserName",//输入字段名
            label: "结束问询者",//输入字段对杨的中文名称
            placeholder: "请输入结束问询者姓名",
        },
        {
            type: "select",
            name: "enquiryDiff",
            label: "问询区分",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 0,//输入字段的值
                    text: "技术交底",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "关键工序管控",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 2,//输入字段的值
                    text: "非关键工序管控",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 3,//输入字段的值
                    text: "疑难问题",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 4,//输入字段的值
                    text: "互动",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        },
        {
            type: "select",
            name: "levelDiff",
            label: "问询级别区分",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "紧急",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 2,//输入字段的值
                    text: "普通",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        },
        {
            type: "date",//text,select,date,
            name: "createTime",
            label: "起始时间",
            defaultValue: l.dateFormat(new Date(new Date().getTime() - 1000 * 60 * 60 * 24 * 30), "yyyy-MM-dd"),
            maxDate: "\'#F{$dp.$D(\\\'logmax\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmin",
        },
        {
            type: "date",//text,select,date,
            name: "endDate",
            label: "结束时间",
            defaultValue: l.dateFormat(new Date(), "yyyy-MM-dd"),
            minDate: "\'#F{$dp.$D(\\\'logmin\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmax",
        }
    ],
    onSearch: function (arr) {//搜索按钮回调
        var _params = {};
        //_params["userId"] = userId;
        //_params["bmId"] = bmId;
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].name == "createTime" || arr[i].name == "endDate") {
                if (arr[i].value) {
                    _params[arr[i].name] = arr[i].name == "endDate" ? l.dateToUnix(arr[i].value) + 1000 * 60 * 60 * 24 : l.dateToUnix(arr[i].value);
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
        //_params["userId"] = userId;
        //_params["bmId"] = bmId;
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].name == "createTime" || arr[i].name == "endDate") {
                if (arr[i].value) {
                    _params[arr[i].name] = arr[i].name == "endDate" ? l.dateToUnix(arr[i].value) + 1000 * 60 * 60 * 24 : l.dateToUnix(arr[i].value);
                } else {
                    _params[arr[i].name] = "";
                }
            } else {
                _params[arr[i].name] = arr[i].value;
            }
        }
        pager.page('remote', 0, _params)
    }
})
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getBgEnquiryList"),
        params: {
            enquiryTitle: "",
            enquiryUserName: "",
            answerUserName: "",
            endUserName: "",
            enquiryDiff: "",
            levelDiff: "",
            companyOaId: "",
            departmentName: "",
            createTime: new Date().getTime() - 1000 * 60 * 60 * 24 * 30,
            endDate: new Date().getTime() + 1000 * 60 * 60 * 24,
            // userId: userId,
            // bmId: bmId,
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
var detailLayer = $('#detailLayer').detailLayer({
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "enquiryId",//输入字段名
        },
        {
            type: "select",
            name: "enquiryDiff",
            label: "问询区分",
            selectList: [//当类型为select时，option配置
                {
                    value: 0,//输入字段的值
                    text: "技术交底",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "关键工序管控",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 2,//输入字段的值
                    text: "非关键工序管控",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 3,//输入字段的值
                    text: "疑难问题",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 4,//输入字段的值
                    text: "互动",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        },
        {
            type: "text",//
            name: "enquiryTitle",//
            label: "问询标题",//
            placeholder: "请输入问询标题",
            must: true
        },
        {
            type: "text",//
            name: "enquiryContent",//
            label: "问询内容",//
            placeholder: "请输入问询内容",
            must: true
        },
        {
            type: "text",//
            name: "enquiryUserId",//
            label: "问询者[用户ID]",//
            placeholder: "请输入问询者[用户ID]",
            must: true
        },
        {
            type: "date",//text,select,date,
            name: "enquiryDate",
            label: "问询时间",
            defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd hh:mm:ss"),
            maxDate: "\'#F{$dp.$D(\\\'logmax\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmin",
            must: true
        },
        {
            type: "select",
            name: "levelDiff",
            label: "问询级别区分",
            selectList: [//当类型为select时，option配置
                {
                    value: 1,//输入字段的值
                    text: "紧急",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 2,//输入字段的值
                    text: "普通",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        },
        {
            type: "text",//
            name: "answerUserId",//
            label: "指定回答者ID[用户ID]",//
            placeholder: "请输入指定回答者ID[用户ID]",
            must: true
        },
        {
            type: "text",//
            name: "longitude",//
            label: "经度",//
            placeholder: "请输入经度",
            must: true
        },
        {
            type: "text",//
            name: "latitude",//
            label: "纬度",//
            placeholder: "请输入纬度",
            must: true
        },
        {
            type: "date",//text,select,date,
            name: "endDate",
            label: "结束时间",
            defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd hh:mm:ss"),
            maxDate: "\'#F{$dp.$D(\\\'logmax\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmin",
            must: true
        },
        {
            type: "text",//
            name: "endUserId",//
            label: "结束者ID[用户ID]",//
            placeholder: "请输入结束者ID[用户ID]",
            must: true
        },
        {
            type: "text",//
            name: "position",//
            label: "地理位置",//
            placeholder: "请输入地理位置",
            must: true
        },
    ],
    onSave: function (obj, _params) {
        l.ajax('updateBgEnquiry', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addBgEnquiry', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    }
})
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            detailLayer.open();
            break;
        case "edit":
            if (checkedData.length == 1) {
                detailLayer.open(checkedData[0]);
            } else if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.alert("只能选择一个", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
            break;
        case "del":
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
                l.delTableRow("enquiryId", 'enquiryList', 'batchDeleteEnquiry', checkedData, function (data) {
                    pager.page('remote')
                })//  删除url地址
            }
            break;
    }
})
function myOpen(title, id, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id
    }
    layer.full(layer.open(options))
}
function public(voteId) {//发布
    l.ajax('sendVoteInfo', { voteId: voteId }, function (data, message) {
        layer.alert(message, { icon: 0, }, function (index) {
            layer.close(index);
            pager.page('remote')
        });
    })
}
