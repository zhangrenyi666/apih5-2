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
            "data": "zcbh",//接口对应字段
            "title": "资产编号",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "zcmc",//接口对应字段
            "title": "资产名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "ggxh",//接口对应字段
            "title": "规格型号",//表头名
            "defaultContent": "-",//默认显示
        },
	    {
           "targets": [5],//第几列
            "data": "cwbh",//接口对应字段
            "title": "财务管理编号",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "ysdh",//接口对应字段
            "title": "验收单号",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "ysbmName",//接口对应字段
            "title": "验收部门",//表头名
            "defaultContent": "-",//默认显示
        }, 
        {
            "targets": [8],//第几列
            "data": "modifyUser",//接口对应字段
            "title": "验收人",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [9],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "验收时间",//表头名
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
            name: "zcmc",//输入字段名
            label: "资产名称",//输入字段对杨的中文名称
            placeholder: "请输入资产名称",
        },

        {
            type: "date",//text,select,date,
            name: "startTime",
            label: "验收日期开始",
            maxDate: "\'#F{$dp.$D(\\\'"+"logmax"+"\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmin",
        },
        {
            type: "date",//text,select,date,
            name: "endTime",
            label: "验收日期结束",
            //maxDate: "\'#F{$dp.$D(\\\'"+"logmax"+"\\\')||\\\'%y-%M-%d\\\'}\'",
            minDate: "\'#F{$dp.$D(\\\'"+"logmin"+"\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmax",
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
        pager.page('remote',0, _params)
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
        url: l.getApiUrl("getCheckList"),
        params: {
            sszclx1: "",
            sszclx2: "",
            cfdd1: "",
            cfdd2: "",
            zcmc: "",
            checkState: "",
            startTime: "",
            endTime: "",
        },
        success: function (result) {
            //console.log('返回数据是：',result)
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
    layerArea:['100%', '100%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "recordid",//输入字段名
        },
		  {
            type: "hidden",//隐藏
            name: "sszcid",//所属资产id
        },
        {
            type: "text",//
            name: "zcmc",//
            label: "资产名称",//
            placeholder: "请输入资产名称",
            immutable:true,
        },
        {
            type: "text",//
            name: "ggxh",//
            label: "规格型号",//
            placeholder: "请输入规格型号",
            immutable:true,
        },
        {
            type: "text",//
            name: "ysdh",//
            label: "验收单号",//
            placeholder: "请输入验收单号",
            must: true
        },
		{
            type: "text",//
            name: "cwbh",//
            label: "财务编号",//
            placeholder: "请输入财务编号",
			must: true,
			immutable:true
           
        },
		{
            type: "date",//
            name: "rzrq",//
            label: "入账日期",//
			dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
			id:"rzrq",
            must: true
            
        },
        {
            type: "textarea",//
            name: "remarks",//
            label: "验收说明",//
            placeholder: "请输入验收说明",
        }
    ],
    onSave: function (obj, _params) {
   
            l.ajax('updateCheck', _params, function (data) {
                pager.page('remote')
                obj.close()
            })

    },
    onAdd: function (obj, _params) {
        l.ajax('addCheck', _params, function (data) {
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
                l.delTableRow("recordid", 'currentList', 'batchDeleteCheck', checkedData, function (data) {
                    pager.page('remote')
                })//  删除url地址
            }
            break;
        case "derive"://导出
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }else if (checkedData.length == 1) {
                layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
                    l.ajax('checkExportExcel',checkedData[0],function(res){
                        layer.alert("导出成功！", { icon: 0 }, function (index) {
                            layer.close(index);
                            window.location.href=res;
                        })
                    })
                });

            }else{
                layer.alert("只能选择一个", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
            break;
    }
})