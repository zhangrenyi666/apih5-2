var code = Lny.getUrlParam('code')
Lny.setCookie('code', code, 30)
var table = $('#table').DataTable({
    "info": false,// 是否显示数据信息
    "paging": false,// 是否开启自带分页
    "ordering": false, // 是否开启DataTables的高度自适应
    "processing": false,// 是否显示‘进度’提示
    "searching": false,// 是否开启自带搜索
    "autoWidth": false,// 是否监听宽度变化
    "columnDefs": [// 表格列配置
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
            "targets": [1],// 第几列
            "data": "oaUserName",// 接口对应字段
            "title": "权限人员",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [2],// 第几列
            "data": "remarks",// 接口对应字段
            "title": "备注",// 表头名
            "defaultContent": "-",// 默认显示
        },
		{
            "targets": [3],// 第几列
            "data": "modifyUserName",// 接口对应字段
            "title": "操作人",// 表头名
            "defaultContent": "-",// 默认显示
        },
	    {
            "targets": [4],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "创建时间",//表头名
            "defaultContent": "-",//默认显示
			"render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        }
    ]
});
var pager = $("#pager").page({
    remote: {// ajax请求配置
        url: l.getApiUrl("getZjMeetingEditPersonnelList"),
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
	layerArea:['50%', '40%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "zcOaId",//输入字段名
        },
		{
            type: "pickTree",
            name: "oaEditMember",
            label: "领导人员",
			must: true,
            pickType: {
                department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
                member: false,//成员列表对应接口字段名,false表示不开启该类
            }
        },
		{
            type: "textarea",//textarea   text 
            name: "remarks",//
            label: "备注",//
        },
    ],
    onSave: function (obj, _params) {
        l.ajax('addZjMeetingEditPersonnel', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjMeetingEditPersonnel', _params, function (data) {
                pager.page('remote')
                obj.close()
            })
    }
})
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
	var params;
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
				l.ajax("batchDeleteUpdateZjMeetingEditPersonnel", checkedData, function () {
					pager.page('remote')
				})
				layer.close(index);
            });
            }
            break;
	    case "import"://导入
            leading.open();
            break;
    }
})