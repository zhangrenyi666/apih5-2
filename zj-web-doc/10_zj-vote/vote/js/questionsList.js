var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
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
            "targets": [1],//第几列
            "data": "questionsOrder",//接口对应字段
            "title": "问题排序",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [2],//第几列
            "data": "title",//接口对应字段
            "title": "题目标题",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "questionsNo",//接口对应字段
            "title": "序号类型",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "无序号";
                switch (data) {
                    case "1":
                        r = "字母序号"
                        break
                    case "2":
                        r = "数字序号"
                        break
                    case "0":
                    default:
                        r = "无序号"
                        break
                }
                return r
            }
        },
        {
            "targets": [4],//第几列
            "data": "questionsDifCode",//接口对应字段
            "title": "题目类型",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "1":
                        r = "打分"
                        break
                    case "2":
                        r = "单选"
                        break
                    case "3":
                        r = "多选"
                        break
                    case "4":
                        r = "问答"
                        break
                    case "5":
                        r = "评分"
                        break
                }
                return r
            }
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "title",//输入字段名
            label: "题目标题",//输入字段对杨的中文名称
            placeholder: "请输入完整名称",
        }
    ],
    onSearch: function (arr) {//搜索按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].name == "startDate" || arr[i].name == "endDate") {
                if (arr[i].value) {
                    _params[arr[i].name] = l.dateFormat(new Date(new Date(arr[i].value).getTime()), "yyyy-MM-dd");
                } else {
                    _params[arr[i].name] = "";
                }
            } else {
                _params[arr[i].name] = arr[i].value;
            }
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
var detailLayer = $('#detailLayer').detailLayer({
    layerArea: ["100%", "100%"],
    controlKey: "questionsDifCode",
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "voteId",//输入字段名
            defaultValue: l.getUrlParam("id")
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "questionsId",//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "questionsDifCode",//输入字段名
        },
        {
            type: "number",//
            name: "questionsOrder",//
            label: "问题排序",//
            defaultValue: 1,
            placeholder: "请输入问题排序",
            must: true,
        },
        {
            type: "text",//
            name: "title",//
            label: "题目标题",//
            placeholder: "请输入题目标题",
            must: true
        },
        {
            type: "select",
            name: "questionsNo",
            label: "序号类型",
            selectList: [//当类型为select时，option配置
                {
                    value: 0,//输入字段的值
                    text: "无序号",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "字母序号",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 2,//输入字段的值
                    text: "数字序号",//显示中文名
                    selected: false//默认是否选中
                }
            ],
            must: true,
            controlHide: "4,5"
        },
        {
            type: "option",//
            name: "questionsDetailsList",//
            label: "问题选项",//
            must: true,
            controlLabel: "1",//第一种类型的时候修改label
            controlHide: "4,5"//第四种类型的时候隐藏
        },
        {
            type: "number",//
            name: "min",//
            label: "评分最小值",//
            must: true,
            controlHide: "1,2,3,4"//第四种类型的时候隐藏
        },
        {
            type: "number",//
            name: "max",//
            label: "评分最大值",//
            must: true,
            controlHide: "1,2,3,4"//第四种类型的时候隐藏
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateQuestions', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addQuestions', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    }
});
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getQuestionsList"),
        params: {
            voteId: l.getUrlParam("id"),
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                var questionsDefaultOrder = 1;//模式给1
                $.each(data, function (index, item) {
                    item.rowIndex = index
                    questionsDefaultOrder = item.questionsDefaultOrder;
                })
                table.clear().rows.add(data).draw();
                detailLayer.resetDefaultValue("questionsOrder", questionsDefaultOrder)
            } else {
                layer.alert(result.message, { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
        }
    }
});

$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
        case "addDaFen":
            detailLayer.open({
                isAdd: true,
                layerTitle: "新增打分题",
                questionsDifCode: 1
            });
            break;
        case "addDanXuan":
            detailLayer.open({
                isAdd: true,
                layerTitle: "新增单选题",
                questionsDifCode: 2
            });
            break;
        case "addDunXuan":
            detailLayer.open({
                isAdd: true,
                layerTitle: "新增多选题",
                questionsDifCode: 3
            });
            break;
        case "addWenDa":
            detailLayer.open({
                isAdd: true,
                layerTitle: "新增问答题",
                questionsDifCode: 4
            });
            break;
        case "addPingFen":
            detailLayer.open({
                isAdd: true,
                layerTitle: "新增评分题",
                questionsDifCode: 5
            });
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
                l.delTableRow("questionsId", 'questionsList', 'batchDeleteQuestions', checkedData, function (data) {
                    pager.page('remote')
                })//  删除url地址
            }
            break;
    }
})
