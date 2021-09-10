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
            "data": "cropName",//接口对应字段
            "title": "所持特种证件编号",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "projectName",//接口对应字段
            "title": "发证日期",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "drillName",//接口对应字段
            "title": "到期日期",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "drillNumber",//接口对应字段
            "title": "附件",//表头名
            "defaultContent": "-",//默认显示
        }
    ]
});
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjLaborEmergencyDrillList"),
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
            type: "select",
            name: "specialOperationNumber",
            label: "特种作业证件",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "电作作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 2,//输入字段的值
                    text: "金属焊接、切割作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 3,//输入字段的值
                    text: "起重机械（含电梯）作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 4,//输入字段的值
                    text: "企业内机动车辆驾驶",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 5,//输入字段的值
                    text: "登高架设作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 6,//输入字段的值
                    text: "锅炉作业（含水质化验）",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 7,//输入字段的值
                    text: "压力容器作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 8,//输入字段的值
                    text: "制冷作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 9,//输入字段的值
                    text: "爆破作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 10,//输入字段的值
                    text: "危险物品作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 11,//输入字段的值
                    text: "其它作业",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        },
        {
            type: "date",//
            name: "specialOperationStartTime",//
            label: "发证日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"specialOperationStartTime",
        },
        {
            type: "date",//
            name: "specialOperationEndTime",//
            label: "到期日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"specialOperationEndTime",
        },
		{
            type: "upload",//
            name: "specialOperationFileList",//
            label: "特种作业证件上传",//
            btnName: "添加特种作业证件",
            projectName: "zj-labor-real-name",
            fileType: ["jpg", "jpeg", "png", "gif"]
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateZjLaborEmergencyDrill', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjLaborEmergencyDrill', _params, function (data) {
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
				   layer.confirm("确定删除？", { icon: 0,}, function (index) {            
					   l.ajax("batchDeleteZjLaborEmergencyDrill", checkedData, function () {
					   pager.page('remote')
				    })
				  layer.close(index);
               });
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
