var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
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
            "data": "questionClassName",// 接口对应字段
            "title": "分类",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [3],// 第几列
            "data": "questionTitle",// 接口对应字段
            "title": "标题",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [4],// 第几列
            "data": "remarks",// 接口对应字段
            "title": "备注",// 表头名
            "defaultContent": "-",// 默认显示
        },
	
		{
            "targets": [5],// 第几列
            "data": "modifyUserName",// 接口对应字段
            "title": "创建者",// 表头名
            "defaultContent": "-",// 默认显示
        },
	    {
            "targets": [6],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "创建时间",//表头名
            "defaultContent": "-",//默认显示
			"render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        }
    ]
});
//判断当前用户是否问题管理人员
l.ajax('getQuestionManagementFlag',{}, function (res) {
	if(res == "1"){
		$("#add").hide();
		$("#edit").hide();
		$("#del").hide();
	}
})
var form = $('#form').filterfrom({
    conditions: [// 表单项配置
		{
            type: "text",//
            name: "questionTitle",//
            label: "问题标题",//
			placeholder: "请输入问题标题",
        }

    ],
    onSearch: function (arr) {// 搜索按钮回调
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
        pager.page('remote',0, _params)
    },
    onReset: function (arr) {// 重置按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
        pager.page('remote', _params)
    }
})
var pager = $("#pager").page({
    remote: {// ajax请求配置
        url: l.getApiUrl("getZjQuestionManagerList"),
        params: {
		  questionClassId : Apih5.getUrlParam('id')
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

// 新增
var detailLayer = $('#detailLayer').detailLayer({
    conditions: [
		{
            type: "hidden",//
            name: "questionId",//
        },
		{
            type: "hidden",//
            name: "questionClassId",//
            defaultValue:l.getUrlParam("id")
        },		
        {
            type: "text",//
            name: "department",//
            label: "部门",//
            defaultValue:l.getUrlParam("departmentName"),
            immutableAdd:true,			
        },
        {
            type: "text",//
            name: "className",//
            label: "类型",//
            defaultValue:l.getUrlParam("className"),
            immutableAdd:true,			
        },		
        {
            type: "text",//
            name: "questionTitle",//
            label: "问题",//
        },
		{
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateZjQuestionManager', _params, function (data) {
                layer.alert("修改成功！", { icon: 1, }, function (index) {
            pager.page('remote');
            layer.close(index);			
            obj.close();					
                });			
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjQuestionManager', _params, function (data) {
                layer.alert("新增成功！", { icon: 1, }, function (index) {
            pager.page('remote');
            layer.close(index);			
            obj.close();					
                });	
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
				layer.confirm("确定删除？", { icon: 3,}, function (index) {
				l.ajax("batchDeleteUpdateZjQuestionManager", checkedData, function () {
                layer.alert("删除成功！", { icon: 1, }, function (index) {
					pager.page('remote')					
                    layer.close(index);
					
                });					
				})
				layer.close(index);
            });
            }
            break;
    }
})