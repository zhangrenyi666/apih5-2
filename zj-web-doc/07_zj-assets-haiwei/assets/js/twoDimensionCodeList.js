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
            "data": "sydwName",//接口对应字段
            "title": "使用单位",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "zcztdm",//接口对应字段
            "title": "资产状态",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "1":
                        r = "正常使用";
                        break;
                    case "2":
                        r = "未验收";
                        break;
                    case "3":
                        r = "已报废";
                        break;
                    case "4":
                        r = "闲置";
                        break;
                }
                return r
            }
        },	
		{
            "targets": [8],//第几列
            "data": "grrq",//接口对应字段
            "title": "购入日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd");
            }
        },	
        {
            "targets": [9],//第几列
            "data": "syr",//接口对应字段
            "title": "操作员",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [10],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "操作时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd");
            }
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "zcbh",//输入字段名
            label: "资产编号",//输入字段对杨的中文名称
            placeholder: "请输入资产名称",
        },		
		{
            type: "text",//三种形式：text,select,date,
            name: "cwbh",//输入字段名
            label: "财务编号",//输入字段对杨的中文名称
            placeholder: "请输入资产名称",
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "sydwid",//输入字段名
            label: "使用单位",//输入字段对杨的中文名称
            placeholder: "请输入使用单位",
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
        url: l.getApiUrl("getTwoDimensionCodeList"),
        params: {
			zcbh: "",
            cwbh: ""
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
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
   //console.log(checkedData)
    var ewmlj = checkedData[0].ewmlj;//二维码路径
    var zcbh = checkedData[0].zcbh;//资产编码
    var zcmc = checkedData[0].zcmc;//资产名称

    switch (name) {
		case "derive":
		if(checkedData.length == 0){
			layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
		}else{
			l.delTableRow("recordid", 'currentList', 'batchExportQRcodeVertical', checkedData, function (res) {
				 layer.alert("导出成功！", { icon: 0 }, function (index) {
                        layer.close(index);
                        window.location.href=res;
                    })
                })//  删除url地址
		}
		    break;
        case "previewer":
            if (checkedData.length == 1) {
                layer.alert("<div style='text-align: center'><span>资产编码：" + zcbh + "</span><br><span >资产名称：" + zcmc + "</span></div><div align='center'><image  src='" + ewmlj + "'></image></div>", function (index) {
                    layer.close(index);
                });
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
		case "cut":
		if(checkedData.length == 0){
			layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
		}else{
			l.delTableRow("recordid", 'currentList', 'batchExportQRcodeTransverse', checkedData, function (res) {
				 layer.alert("导出成功！", { icon: 0 }, function (index) {
                        layer.close(index);
                        window.location.href=res;
                    })
                })//  删除url地址
		}
		    break;
/*       case "cut"://批量导出（横向）
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
                var currentList = [];
                for (var i = 0; i < checkedData.length; i++) {
                    // currentList.push({ recordid: checkedData[i].recordid });
                    currentList.push(checkedData[i].recordid);
                }
                if (currentList.length) {
                // window.location.href = 'http://192.168.1.223:8081/ureport/excel?_u=file:zjZcQrcodeListToTransverse.xml&url=' + l.domain + '&idList=' + JSON.stringify(currentList) + '&code=' + code;                    
                    window.location.href = 'http://192.168.1.223:8081/ureport/preview?_u=file:hwzcQrcodeListToTransverse.xml&url=' + l.domain + '&idList=' + currentList;
                }
            }
            break; */
    }
})
function print(num, name, src) {
    window.open("./twoCode.html?num=" + num + "&name=" + name + "&src=" + src, 'no','','channelmode=0&directories=0&location=0&menubar=0&resizable=0&scrollbars=0&status=0&titlebar=0&toolbar=0&width=240&height=320')
}
