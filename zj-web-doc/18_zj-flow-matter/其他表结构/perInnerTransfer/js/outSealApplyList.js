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
            "data": "outSealUnit",//接口对应字段
            "title": "外携用印单位",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "sendUnit",//接口对应字段
            "title": "发往单位",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "applyUseTime",//接口对应字段
            "title": "用印时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [5],//第几列
            "data": "content",//接口对应字段
            "title": "内容",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "sealType",//接口对应字段
            "title": "印别",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                }
                return r
            }
        },
        {
            "targets": [7],//第几列
            "data": "useNumber",//接口对应字段
            "title": "用印次数",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [8],//第几列
            "data": "applyRealName",//接口对应字段
            "title": "经办人",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [9],//第几列
            "data": "applyUserPhone",//接口对应字段
            "title": "联系方式",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [10],//第几列
            "data": "legalOpinion",//接口对应字段
            "title": "合法合规人员",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [11],//第几列
            "data": "dephHeadOpinion",//接口对应字段
            "title": "部门负责人",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [12],//第几列
            "data": "officeArchiveOpinion",//接口对应字段
            "title": "办公室负责人",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [13],//第几列
            "data": "leaderOpinion",//接口对应字段
            "title": "公司分管领导",//表头名
            "defaultContent": "-",//默认显示
        }
    ]
});
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getOutSealApplyList"),
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
var detailLayer = $('#detailLayer').detailLayer({
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "outSealApplyId",//输入字段名
        },
        {
            type: "text",//
            name: "outSealUnit",//
            label: "外携用印单位",//
            placeholder: "请输入外携用印单位",
        },
        {
            type: "textarea",//
            name: "sendUnit",//
            label: "发往单位",//
            placeholder: "请输入发往单位",
        },
        {
            type: "date",//text,select,date,
            name: "applyUseTime",
            label: "用印时间",
            defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd hh:mm:ss"),
            maxDate: "\'#F{$dp.$D(\\\'logmax\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmin",
        },
        {
            type: "textarea",//
            name: "content",//
            label: "内容",//
            placeholder: "请输入内容",
        },
        {
            type: "select",
            name: "sealType",
            label: "印别"
            ],
        },
        {
            type: "number",//
            name: "useNumber",//
            label: "用印次数",//
            placeholder: "请输入用印次数",
        },
        {
            type: "text",//
            name: "applyRealName",//
            label: "经办人",//
            placeholder: "请输入经办人",
        },
        {
            type: "text",//
            name: "applyUserPhone",//
            label: "联系方式",//
            placeholder: "请输入联系方式",
        },
        {
            type: "textarea",//
            name: "legalOpinion",//
            label: "合法合规人员",//
            placeholder: "请输入合法合规人员",
        },
        {
            type: "textarea",//
            name: "dephHeadOpinion",//
            label: "部门负责人",//
            placeholder: "请输入部门负责人",
        },
        {
            type: "textarea",//
            name: "officeArchiveOpinion",//
            label: "办公室负责人",//
            placeholder: "请输入办公室负责人",
        },
        {
            type: "textarea",//
            name: "leaderOpinion",//
            label: "公司分管领导",//
            placeholder: "请输入公司分管领导",
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateOutSealApply', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addOutSealApply', _params, function (data) {
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
                l.delTableRow("outSealApplyId", 'sxAqJsonNodeList', 'batchDeleteOutSealApply', checkedData, function (data) {
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
function preview(voteId) {//预览
    window.open(l.domainName + 'initMVotePreview.do?voteId=' + voteId + '&previewFlag=1', 'newwindow', 'height=500,width=350,top=100,left=500,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no')
}
