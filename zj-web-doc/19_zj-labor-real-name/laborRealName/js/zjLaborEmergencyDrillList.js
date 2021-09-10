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
            "data": "cropName",//接口对应字段
            "title": "公司名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "projectName",//接口对应字段
            "title": "项目名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "drillName",//接口对应字段
            "title": "演练名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "drillTime",//接口对应字段
            "title": "时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [6],//第几列
            "data": "drillContent",//接口对应字段
            "title": "内容",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "drillKind",//接口对应字段
            "title": "种类",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [8],//第几列
            "data": "drillNumber",//接口对应字段
            "title": "次数",//表头名
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
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "emergencyDrillId",//输入字段名
        },
        {
            type: "text",//
            name: "cropName",//
            label: "公司名称",//
            placeholder: "请输入公司名称",
            must: true
        },
        {
            type: "text",//
            name: "projectName",//
            label: "项目名称",//
            placeholder: "请输入项目名称",
            must: true
        },
        {
            type: "text",//
            name: "drillName",//
            label: "演练名称",//
            placeholder: "请输入演练名称",
            must: true
        },
		{
            type: "date",//
            name: "drillTime",//
            label: "时间",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id: "drillTime",
            must: true
        },       
        {
            type: "textarea",//
            name: "drillContent",//
            label: "内容",//
            placeholder: "请输入内容",
            must: true
        },
        {
            type: "text",
            name: "drillKind",
            label: "种类",
            placeholder: "请输入种类"
        },
        {
            type: "number",//
            name: "drillNumber",//
            label: "次数",//
            placeholder: "请输入次数",
            must: true
        },
		{
            type: "upload",//
            name: "emergencyDrillList",//
            label: "附件上传",//
            btnName: "添加附件",
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
