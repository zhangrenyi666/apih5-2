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
            "data": "chapter",//接口对应字段
            "title": "章节",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [2],//第几列
            "data": "inventoryNumber",//接口对应字段
            "title": "清单号",//表头名
            "defaultContent": "-",//默认显示
        },  
		{
            "targets": [3],//第几列
            "data": "inventoryName",//接口对应字段
            "title": "审减费用清单名称",//表头名
            "defaultContent": "-",//默认显示
        },     
        {
            "targets": [4],//第几列
            "data": "initAmount",//接口对应字段
            "title": "初审计核减金额（万元）",//表头名
            "defaultContent": "-",//默认显示
        },       
		{
            "targets": [5],//第几列
            "data": "initReason",//接口对应字段
            "title": "初审核减原因",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//0：材料调整 1：工程质量验收不合格 2：计价规则影响 3：清单重复计量 4：索赔审计扣减 5：图纸错误导致后期扣减 6：未按图纸施工 7：与招标文件不符 8：其他
                var r = "未知";
                switch (data) {
                    case "0":
                        r = "材料调整"
                        break
                    case "1":
                        r = "工程质量验收不合格"
                        break
					case "2":
                        r = "计价规则影响"
                        break
                    case "3":
                        r = "清单重复计量"
                        break
					case "4":
                        r = "索赔审计扣减"
                        break
                    case "5":
                        r = "图纸错误导致后期扣减"
                        break
					case "6":
                        r = "未按图纸施工"
                        break
                    case "7":
                        r = "与招标文件不符"
                        break 
					case "8":
                        r = "其他"
                        break
                }
                return r
            }
        },
		{
            "targets": [6],//第几列
            "data": "actualReason",//接口对应字段
            "title": "实际原因",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [7],//第几列
            "data": "auditReason",//接口对应字段
            "title": "审计核减依据、理由",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [8],//第几列
            "data": "finalAmount",//接口对应字段
            "title": "最终审减金额（万元）",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [9],//第几列
            "data": "finalReason",//接口对应字段
            "title": "终审减原因",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//0：材料调整 1：工程质量验收不合格 2：计价规则影响 3：清单重复计量 4：索赔审计扣减 5：图纸错误导致后期扣减 6：未按图纸施工 7：与招标文件不符 8：其他
                var r = "未知";
                switch (data) {
                    case "0":
                        r = "材料调整"
                        break
                    case "1":
                        r = "工程质量验收不合格"
                        break
					case "2":
                        r = "计价规则影响"
                        break
                    case "3":
                        r = "清单重复计量"
                        break
					case "4":
                        r = "索赔审计扣减"
                        break
                    case "5":
                        r = "图纸错误导致后期扣减"
                        break
					case "6":
                        r = "未按图纸施工"
                        break
                    case "7":
                        r = "与招标文件不符"
                        break 
					case "8":
                        r = "其他"
                        break
                }
                return r
            }
        },
		{
            "targets": [10],//第几列
            "data": "tryAmount",//接口对应字段
            "title": "争取减少金额（万元）",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [11],//第几列
            "data": "tryWay",//接口对应字段
            "title": "争取金额减少方式",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [12],//第几列
            "data": "remarks",//接口对应字段
            "title": "备注",//表头名
            "defaultContent": "-",//默认显示
        }
    ]
});
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjHwZyResourceAuditInventoryList"),
        params: {
            chapterId: l.getUrlParam("id")
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                $.each(data, function (index, item) {
                    item.rowIndex = index
                })
                table.clear().rows.add(data).draw();
                Lny.log(data)
            } else {
                layer.alert(result.message, { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
        },

    }
});
var detailLayer = $('#detailLayer').detailLayer({
layerArea: ["100%", "100%"],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "inventoryId",//输入字段名
        },
        {
            type: "text",//
            name: "chapter",//
            label: "章节",//
            defaultValue:l.getUrlParam("chapter") ,
            immutableAdd:true,
            must: true
        },      
        {
            type: "text",//
            name: "inventoryNumber",//
            label: "清单号",//
            placeholder: "请输入清单号",
			must: true
        },
		{
            type: "text",//
            name: "inventoryName",//
            label: "审减费用清单名称",//
            placeholder: "请输入审减费用清单名称",
            must: true
        },
		{
            type: "number",//
            name: "initAmount",//
            label: "初审计核减金额（万元）",//
            placeholder: "请输入初审计核减金额（万元）",
        },   
		{
            type: "select",
            name: "initReason",
            label: "初审核减原因",//0：材料调整 1：工程质量验收不合格 2：计价规则影响 3：清单重复计量 4：索赔审计扣减 5：图纸错误导致后期扣减 6：未按图纸施工 7：与招标文件不符 8：其他
			must: true,
            selectList: [//当类型为select时，option配置
                {
                    value: 0,//输入字段的值
                    text: "材料调整",//显示中文名
                },
                {
                    value: 1,//输入字段的值
                    text: "工程质量验收不合格",//显示中文名
                },
				{
                    value: 2,//输入字段的值
                    text: "计价规则影响",//显示中文名
                },
                {
                    value: 3,//输入字段的值
                    text: "清单重复计量",//显示中文名
                },
				{
                    value: 4,//输入字段的值
                    text: "材料调整",//显示中文名
                },
                {
                    value: 5,//输入字段的值
                    text: "图纸错误导致后期扣减",//显示中文名
                },
				{
                    value: 6,//输入字段的值
                    text: "未按图纸施工",//显示中文名
                },
                {
                    value: 7,//输入字段的值
                    text: "与招标文件不符",//显示中文名
                },
				{
                    value: 8,//输入字段的值
                    text: "其他",//显示中文名
                }
            ],
        },
		{
            type: "textarea",//
            name: "actualReason",//
            label: "实际原因",//
            placeholder: "请输入实际原因",
        },
		{
            type: "textarea",//
            name: "auditReason",//
            label: "审计核减依据、理由",//
            placeholder: "请输入审计核减依据、理由",
        },
		{
            type: "number",//
            name: "finalAmount",//
            label: "最终审减金额（万元）",//
            placeholder: "请输入最终审减金额（万元）",
        },
		{
            type: "select",
            name: "finalReason",
            label: "终审减原因",//0：材料调整 1：工程质量验收不合格 2：计价规则影响 3：清单重复计量 4：索赔审计扣减 5：图纸错误导致后期扣减 6：未按图纸施工 7：与招标文件不符 8：其他
			must: true,
            selectList: [//当类型为select时，option配置
                {
                    value: 0,//输入字段的值
                    text: "材料调整",//显示中文名
                },
                {
                    value: 1,//输入字段的值
                    text: "工程质量验收不合格",//显示中文名
                },
				{
                    value: 2,//输入字段的值
                    text: "计价规则影响",//显示中文名
                },
                {
                    value: 3,//输入字段的值
                    text: "清单重复计量",//显示中文名
                },
				{
                    value: 4,//输入字段的值
                    text: "材料调整",//显示中文名
                },
                {
                    value: 5,//输入字段的值
                    text: "图纸错误导致后期扣减",//显示中文名
                },
				{
                    value: 6,//输入字段的值
                    text: "未按图纸施工",//显示中文名
                },
                {
                    value: 7,//输入字段的值
                    text: "与招标文件不符",//显示中文名
                },
				{
                    value: 8,//输入字段的值
                    text: "其他",//显示中文名
                }
            ],
        },
        {
            type: "number",//
            name: "tryAmount",//
            label: "争取减少金额（万元）",//
            placeholder: "请输入争取减少金额（万元）",
        },
        {
            type: "textarea",//
            name: "tryWay",//
            label: "争取金额减少方式",//
            placeholder: "请输入争取金额减少方式",
        },
        {
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入备注",
        }      
    ],
    onSave: function (obj, _params) {
        _params.chapterId=l.getUrlParam("id")
        l.ajax('updateZjHwZyResourceAuditInventory', _params, function (data) {
                pager.page('remote')
                obj.close()
            })
    },
    onAdd: function (obj, _params) {
        _params.chapterId=l.getUrlParam("id");
        l.ajax('addZjHwZyResourceAuditInventory', _params, function (data) {
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
				l.ajax("batchDeleteUpdateZjHwZyResourceAuditInventory", checkedData, function () {
					pager.page('remote')
				})
				layer.close(index);
            });
            }
            break;
    }
})
