var code = Apih5.getUrlParam('code')
var fallInDeptId = Apih5.getUrlParam('id')
var sponsoringDept = Apih5.getUrlParam('sponsoringDept')
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
            "width": 25,
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
            "targets": [2],// 第几列
            "data": "meetingTimeStr",// 接口对应字段          
            "title": "会议时间",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [3],//第几列
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "会议名称",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data, index, row) {
                var title;
                if (data) {
                    title = '<a style="color:blue;"  onclick="myOpen(\'详情\',\'' + JSON.stringify(data).replace(/"/g, '&quot;') + '\')" href="javascript:void(0);">' + data.meetingNameStr + '</a>'
                }
                return title;
            }
        },
        {
            "targets": [4],// 第几列
            "data": "meetingForm",// 接口对应字段
            "title": "会议形式",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [5],// 第几列
            "data": "joinObject",// 接口对应字段
            "title": "参加对象",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [6],// 第几列
            "data": "joinNumber",// 接口对应字段
            "title": "人数",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [7],// 第几列
            "data": "sponsoringDept",// 接口对应字段
            "title": "主办部门",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [8],// 第几列
            "data": "coOperationDept",// 接口对应字段
            "title": "协办部门",// 表头名           
            // "class":"f-els3_3",  
            "class": "text-overflow",
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [9],// 第几列
            "data": "budgetaryCost",// 接口对应字段
            "title": "预算费用（万元）",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [10],// 第几列
            "data": "meetingRemakes",// 接口对应字段
            "title": "备注",// 表头名
            "defaultContent": "-",// 默认显示
        }
    ]
});
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjMeetingPlanFallInListDeptId"),
        params: {
            fallInDeptId: fallInDeptId
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                //console.log(data)
                $.each(data, function (index, item) {
                    item.rowIndex = index
                })
                table.clear().rows.add(data).draw();
                $.each($(".text-overflow"), function (index, item) {
                    $(".text-overflow").eq(index + 1).attr({"data-toggle":"tooltip","data-placemen":"top","title":data[index]["coOperationDept"]});
                })
                $(function () { $("[data-toggle='tooltip']").tooltip()});
            } else {
                layer.alert(result.message, { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
        },
    }
});
var detailLayer = $('#detailLayer').detailLayer({
    layerArea: ['60%', '70%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "fallInId",//输入字段名
            lineNum: 1
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "fallInDeptId",//输入字段名
            lineNum: 1
        },
        {
            type: "text",//
            name: "meetingTimeStr",//
            label: "会议时间",//
            immutableAdd: true,
            lineNum: 2
        },
        {
            type: "text",//
            name: "meetingNameStr",//
            label: "会议名称",//
            immutableAdd: true,
            lineNum: 2
        },
        {
            type: "text",//
            name: "meetingForm",//
            label: "会议形式",//
            immutableAdd: true,
            lineNum: 3
        },
        {
            type: "text",//
            name: "joinObject",//
            label: "参加对象",//
            immutableAdd: true,
            lineNum: 3
        },
        {
            type: "text",//
            name: "joinNumber",//
            label: "人数",//
            immutableAdd: true,
            lineNum: 4
        },
        {
            type: "text",//
            name: "budgetaryCost",//
            label: "预算费用（万元）",//
            immutableAdd: true,
            lineNum: 4
        },
        {
            type: "text",//
            name: "sponsoringDept",//
            label: "主办部门",//
            immutableAdd: true,
        },
        {
            type: "pickTree",
            name: "coOperationDeptList",
            label: "协办部门",
            pickType: {
                department: "oaDepartmentList",// 部门列表对应接口字段名,false表示不开启该类
                member: false,// 成员列表对应接口字段名,false表示不开启该类               
            },
            immutableAdd: true,
        },
        {
            type: "textarea",//
            name: "meetingRemakes",//
            label: "备注",//
            immutableAdd: true,
        }
    ]
})
var addLayer = $('#addLayer').detailLayer({
    layerArea: ['60%', '70%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "fallInId",//输入字段名
            lineNum: 1
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "fallInDeptId",//输入字段名
            lineNum: 1
        },
        {
            type: "text",//
            name: "meetingTimeStr",//
            label: "会议时间",//
            must: true,
            lineNum: 2
        },
        {
            type: "text",//
            name: "meetingNameStr",//
            label: "会议名称",//
            must: true,
            lineNum: 2
        },
        {
            type: "select",
            name: "meetingForm",
            label: "会议形式",
            selectList: [//当类型为select时，option配置
                {
                    value: "常规会议",//输入字段的值
                    text: "常规会议",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: "仅硬视频",//输入字段的值
                    text: "仅硬视频",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: "仅软视频",//输入字段的值
                    text: "仅软视频",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: "硬视频+软视频",//输入字段的值
                    text: "硬视频+软视频",//显示中文名
                    selected: false//默认是否选中
                }
            ],
            must: true,
            lineNum: 3
        },
        {
            type: "text",//
            name: "joinObject",//
            label: "参加对象",//
            must: true,
            lineNum: 3
        },
        {
            type: "text",//
            name: "joinNumber",//
            label: "人数",//
            must: true,
            lineNum: 4
        },
        {
            type: "text",//
            name: "budgetaryCost",//
            label: "预算费用（万元）",//
            must: true,
            lineNum: 4
        },
        {
            type: "text",//
            name: "sponsoringDept",//
            label: "主办部门",//
            immutableAdd: true,
        },
        {
            type: "pickTree",
            name: "coOperationDeptList",
            label: "协办部门",
            pickType: {
                department: "oaDepartmentList",// 部门列表对应接口字段名,false表示不开启该类
                member: false,// 成员列表对应接口字段名,false表示不开启该类               
            },
            must: true,
        },
        {
            type: "textarea",//
            name: "meetingRemakes",//
            label: "备注",//
        }
    ],
    onSave: function (obj, _params) {
        console.log(_params);
        l.ajax('updateZjMeetingPlanFallInByDeptId', _params, function (data, message, success) {
            if (success = "true") {
                layer.alert("修改成功！", { icon: 1, }, function (index) {
                    layer.close(index);
                    pager.page('remote')
                    obj.close()
                });
            } else {
                pager.page('remote')
            }
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjMeetingPlanFallInByDeptId', _params, function (data, message, success) {
            if (success = "true") {
                layer.alert("新增成功！", { icon: 1, }, function (index) {
                    layer.close(index);
                    pager.page('remote')
                    obj.close()
                });
            } else {
                pager.page('remote')
            }
        })
    }
})
function myOpen(title, rowIndex) {
    $(".layui-layer-title").html(title);
    $('#detailLayer .btn').hide();
    detailLayer.open(JSON.parse(rowIndex));
}
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            addLayer.open({ isAdd: true, fallInDeptId: fallInDeptId, sponsoringDept: sponsoringDept });
            break;
        case "edit":
            if (checkedData.length == 1) {
                addLayer.open(checkedData[0]);
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
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.confirm("确定删除？", { icon: 0, }, function (index) {
                    l.ajax("batchDeleteUpdateZjMeetingPlanFallIn", checkedData, function () {
                        layer.alert("删除成功！", { icon: 1, }, function (index) {
                            layer.close(index);
                            pager.page('remote')
                            obj.close()
                        });
                    })
                    layer.close(index);
                });
            }
            break;
    }
})
