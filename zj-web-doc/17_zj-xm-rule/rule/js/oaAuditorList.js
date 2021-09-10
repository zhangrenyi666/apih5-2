var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30);
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
            "data": "objectName",//接口对应字段
            "title": "审核人",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [3],//第几列
            "data": "modifyUserName",//接口对应字段
            "title": "操作员",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "操作时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        }
    ]
});

var allData;
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjXmOaDepartmentList"),
        params: {
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
				 allData = data;
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
	layerArea:['50%', '40%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "oaDepartmentId",//输入字段名
        },
		{
            type: "pickTree",
            name: "oaAuditor",
            label: "审核人员",
			must: true,
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            }
        }
    ],
    onSave: function (obj, _params) {
            l.ajax('updateZjXmOaDepartment', _params, function (data) {
                pager.page('remote')
                obj.close()
            })    
    },
    onAdd: function (obj, _params) {     
            l.ajax('addZjXmOaDepartment', _params, function (data) {
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
				l.ajax("batchDeleteUpdateZjXmOaDepartment", checkedData, function () {
					pager.page('remote')
				})
				layer.close(index);
            });
            }
            break;
    }
})