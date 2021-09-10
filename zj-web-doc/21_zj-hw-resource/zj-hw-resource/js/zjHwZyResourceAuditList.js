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
            "targets": [1],//第几列
            "data": "proType",//接口对应字段
            "title": "项目类型",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [2],//第几列
            "data": "proProvince",//接口对应字段
            "title": "项目所在省份",//表头名
            "defaultContent": "-",//默认显示
        },  
		{
            "targets": [3],//第几列
            "data": "tenderPrice",//接口对应字段
            "title": "项目中标价（万元）",//表头名
            "defaultContent": "-",//默认显示
        },      
        {
            "targets": [4],//第几列
            "data": "validContractPrice",//接口对应字段
            "title": "项目中有效合同价（万元）",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [5],//第几列
            "data": "compSettlementPrice",//接口对应字段
            "title": "项目终期计量/竣工结算价（万元）",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [6],//第几列
            "data": "auditAmount",//接口对应字段
            "title": "审计核减金额（万元）",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [7],//第几列
            "data": "finalSettlementPrice",//接口对应字段
            "title": "项目审计后最终结算价",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [8],//第几列
            "data": "ratio",//接口对应字段
            "title": "项目审计核减比例%",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [9],//第几列
            "data": "aduitTime",//接口对应字段
            "title": "审计时间",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [10],//第几列
            "width": 100,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名
            "render": function (data) {
                var html = '';           
                html += '<span class="dropDown">'
                html += '<a style="color:blue;" href="javascript:void(0);" onclick="myOpen(\'章节\',\'' + data.auditId + '\',\'' + data.proType + '\',\'' + 'zjHwZyResourceAuditChapterList' + '\')">章节</a>';
                 html += '</span>'	
                return html;
            }
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "proType",//输入字段名
            label: "项目类型",//输入字段对杨的中文名称
            placeholder: "请输入项目类型",
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
        url: l.getApiUrl("getZjHwZyResourceAuditList"),
        params: {},
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
   layerArea:['100%', '100%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "auditId",//输入字段名
        },
        {
            type: "text",//
            name: "proType",//
            label: "项目类型",//
            placeholder: "请输入项目类型",
            must: true
        },
		{
            type: "text",//
            name: "proProvince",//
            label: "项目所在省份",//
            placeholder: "请输入项目所在省份"
        },
		{
            type: "number",//
            name: "tenderPrice",//
            label: "项目中标价（万元）",//
            placeholder: "请输入项目中标价（万元）"
        },
		{
            type: "number",//
            name: "validContractPrice",//
            label: "项目中有效合同价（万元）",//
            placeholder: "请输入项目中有效合同价（万元）"
        },
        {
            type: "number",//
            name: "compSettlementPrice",//
            label: "项目终期计量/竣工结算价（万元）",//
            placeholder: "请输入项目终期计量/竣工结算价（万元）",
        },
		{
            type: "number",//
            name: "auditAmount",//
            label: "审计核减金额（万元）",//
            placeholder: "请输入审计核减金额（万元）"
        },
		{
            type: "number",//
            name: "finalSettlementPrice",//
            label: "项目审计后最终结算价",//
            placeholder: "请输入项目审计后最终结算价"
        },
		{
            type: "text",//
            name: "ratio",//
            label: "项目审计核减比例%",//
            placeholder: "请输入项目审计核减比例%"
        },
        {
            type: "text",//
            name: "aduitTime",//
            label: "审计开始时间",//
            placeholder: "请输入审计开始时间",
        }        
    ],
    onSave: function (obj, _params) {
      l.ajax('updateZjHwZyResourceAudit', _params, function (data) {
                pager.page('remote');
                obj.close()
            })
    },
    onAdd: function (obj, _params) {
         l.ajax('addZjHwZyResourceAudit', _params, function (data) {
                pager.page('remote')
                obj.close()
            });
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
			projectName:"zj-assets-haiwei",
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
        l.ajax('batchImportZjHwZyResourceAuditInventory', {"importFileList":_params.importFileList}, function (_params) {
            pager.page('remote')
            obj.close()
        })
    }
});

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
				l.ajax("batchDeleteUpdateZjHwZyResourceAudit", checkedData, function () {
					pager.page('remote')
				})
				layer.close(index);
            });
            }
            break;
		case "leading"://导入
            leading.open();
            break;
    }
})
function myOpen(title, id,proType, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id+"&proType="+encodeURI(proType)+"&code="+code
    }
    layer.full(layer.open(options))
}