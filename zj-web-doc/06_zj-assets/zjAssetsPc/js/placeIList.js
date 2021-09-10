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
            "data": "cfddbh",//接口对应字段
            "title": "编号",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "cfddmc",//接口对应字段
            "title": "存放地点",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "bz",//接口对应字段
            "title": "备注",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "zt",//接口对应字段
            "title": "状态",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "0":
                        r = "启用"
                        break
                    case "1":
                        r = "停用"
                        break
                }
                return r
            }
        },
        {
            "targets": [6],//第几列
            "data": "modifyUser",//接口对应字段
            "title": "操作员",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "操作时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        }
    ]
});

var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "cfddmc",//输入字段名
            label: "存放地点",//输入字段对杨的中文名称
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

var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getPlaceIList"),
        params: {
            cfddmc: "",
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
            name: "recordid",//输入字段名
        },
        {
            type: "text",//
            name: "cfddbh",//
            label: "编号",//
            placeholder: "请输入编号",
            must: true,
            max: 99999,
		    immutable:true
        },
        {
            type: "text",//
            name: "cfddmc",//
            label: "存放地点",//
            placeholder: "请输入存放地点",
            must: true
        },
        {
            type: "textarea",//
            name: "bz",//
            label: "备注",//
            placeholder: "请输入备注",
        },
        {
            type: "select",
            name: "zt",
            label: "状态",
            selectList: [//当类型为select时，option配置
                {
                    value: 0,//输入字段的值
                    text: "启用",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "停用",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        }
    ],
    onSave: function (obj, _params) {
		var cfddbh = _params.cfddbh;//存放地点编号
        var cfddbhGS = /^[0-9]{1,20}$/.test(cfddbh);//资产编号格式限制
        if (cfddbhGS == false) {
            layer.alert("编号只允许为数字", { icon: 0, }, function (index) {
                layer.close(index);
            });
        }else 
        if(_params.cfddbh.length<6){
            l.ajax('updatePlaceI', _params, function (data) {
                pager.page('remote')
                obj.close()
            })
        }else{
            layer.alert("编号最大长度为5位数", { icon: 0, }, function (index) {
                layer.close(index);
            });
        }

    },
    onAdd: function (obj, _params) {
	    var cfddbh = _params.cfddbh;//存放地点编号
        var cfddbhGS = /^[0-9]{1,20}$/.test(cfddbh);//资产编号格式限制
        if (cfddbhGS == false) {
            layer.alert("编号只允许为数字", { icon: 0, }, function (index) {
                layer.close(index);
            });
        }else 
        if(_params.cfddbh.length<6){
            l.ajax('addPlaceI', _params, function (data) {
                pager.page('remote')
                obj.close()
            })
        }else{
            layer.alert("编号最大长度为5位数", { icon: 0, }, function (index) {
                layer.close(index);
            });
        }
    }
})

var leading = $('#leading').detailLayer({
     layerArea:['410px', '310px'],
    layerTitle:['导入'],
        conditions: [
        {
            type: "upload",
            maxLen:1,
            name: "importFileList",
            label: "导入文件",
            btnName: "选择文件",
			projectName:"zj-assets",
            fileType: ["xls", "xlsx"],
        }
    ],
    onAdd: function (obj, _params){
        if(_params.importFileList.length==0){
            layer.alert("请选择文件", { icon: 0, }, function (index) {
                layer.close(index);
            });
            return;
        }

        l.ajax('batchInputPlace', {"importFileList":_params.importFileList}, function (_params) {
            pager.page('remote')
            obj.close()
        })
    }
});

$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
        case "leading"://导入
            leading.open();
            break;
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
				l.ajax("batchDeletePlaceI", checkedData, function () {
					pager.page('remote')
				})
				layer.close(index);
            });
            }
            break;
    }
})