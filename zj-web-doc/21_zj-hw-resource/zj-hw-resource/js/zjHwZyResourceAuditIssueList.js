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
            "data": "number",//接口对应字段
            "title": "序号",//表头名
            "defaultContent": "-",//默认显示
            "width": 13,
        },
        {
            "targets": [2],//第几列
            "data": "proType",//接口对应字段
            "title": "项目类型",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [3],//第几列
            "data": "proProvince",//接口对应字段
            "title": "项目所在省份",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [4],//第几列
            "data": "tenderPrice",//接口对应字段
            "title": "项目中标价（万元）",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [5],//第几列
            "data": "validContractPrice",//接口对应字段
            "title": "项目中有效合同价（万元）",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [6],//第几列
            "data": "compSettlementPrice",//接口对应字段
            "title": "项目终期计量/竣工结算价（万元）",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [7],//第几列
            "data": "auditAmount",//接口对应字段
            "title": "审计核减金额（万元）",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [8],//第几列
            "data": "finalSettlementPrice",//接口对应字段
            "title": "项目审计后最终结算价",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [9],//第几列
            "data": "ratio",//接口对应字段
            "title": "项目审计核减比例%",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [10],//第几列
            "data": "aduitTime",//接口对应字段
            "title": "审计时间",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [11],//第几列
            "data": "chapter",//接口对应字段
            "title": "章节",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [12],//第几列
            "data": "inventoryNumber",//接口对应字段
            "title": "清单号",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [13],//第几列
            "data": "inventoryName",//接口对应字段
            "title": "审减费用清单名称",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [14],//第几列
            "data": "initAmount",//接口对应字段
            "title": "初审计核减金额（万元）",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [15],//第几列
            "data": "initReason",//接口对应字段
            "title": "初审核减原因",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
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
            "targets": [16],//第几列
            "data": "actualReason",//接口对应字段
            "title": "实际原因",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [17],//第几列
            "data": "auditReason",//接口对应字段
            "title": "审计核减依据、理由",//表头名
            "defaultContent": "-",//默认显示
            "width": 100,
        },
        {
            "targets": [18],//第几列
            "data": "finalAmount",//接口对应字段
            "title": "最终审减金额（万元）",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [19],//第几列
            "data": "finalReason",//接口对应字段
            "title": "终审减原因",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
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
            "targets": [20],//第几列
            "data": "tryAmount",//接口对应字段
            "title": "争取减少金额（万元）",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [21],//第几列
            "data": "tryWay",//接口对应字段
            "title": "争取金额减少方式",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [22],//第几列
            "data": "remarks",//接口对应字段
            "title": "备注",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [23],//第几列
            "width": 50,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名
            "render": function (data) {
                var html = '';
                html += '<span class="dropDown">'
                html += '<a style="color:blue;"  href="javascript:void(0);" onclick="myOpen1(\' ' + data.rowIndex + '\',\'' + 'xq' + '\')" href="javascript:void(0);">详情</a>';
                html += '</span>'
                return html;
            }
        }
    ]
});
var proType = '';
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
        proType = arr[0].value;
        pager.page('remote', 0, _params);
    },
    onReset: function (arr) {//重置按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
        proType = '';
        pager.page('remote', _params)
    }
})

var allData;
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjHwZyResourceAuditIssueList"),
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
            name: "auditIssueId",//输入字段名
        },
		{
            type: "text",//
            name: "number",//
            label: "序号",//
            placeholder: "请输入序号"
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
        },
        {
            type: "text",//
            name: "chapter",//
            label: "章节名称",//
            placeholder: "请输入章节名称",
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
        },
		{
            type: "upload",//
            name: "fileList",//
            label: "附件上传",//
            btnName: "选择文件",
            projectName: "zj-hw-resource",
            uploadUrl: "uploadFilesByFileName",
            //maxLen: 1,
            fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf", "zip", "rar", "dwg"]
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateZjHwZyResourceAuditIssue', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjHwZyResourceAuditIssue', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    }
})

var seeLayer = $('#seeLayer').detailLayer({
    layerArea: ["100%", "100%"],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "auditIssueId",//输入字段名
        },
		{
            type: "text",//
            name: "number",//
            label: "序号",//
            placeholder: "请输入序号",
			 immutableAdd: true
        },
        {
            type: "text",//
            name: "proType",//
            label: "项目类型",//
            placeholder: "请输入项目类型",
           immutableAdd: true
        },
        {
            type: "text",//
            name: "proProvince",//
            label: "项目所在省份",//
            placeholder: "请输入项目所在省份",
			immutableAdd: true
        },
        {
            type: "number",//
            name: "tenderPrice",//
            label: "项目中标价（万元）",//
            placeholder: "请输入项目中标价（万元）",
			immutableAdd: true
        },
        {
            type: "number",//
            name: "validContractPrice",//
            label: "项目中有效合同价（万元）",//
            placeholder: "请输入项目中有效合同价（万元）",
			immutableAdd: true
        },
        {
            type: "number",//
            name: "compSettlementPrice",//
            label: "项目终期计量/竣工结算价（万元）",//
            placeholder: "请输入项目终期计量/竣工结算价（万元）",
			immutableAdd: true
        },
        {
            type: "number",//
            name: "auditAmount",//
            label: "审计核减金额（万元）",//
            placeholder: "请输入审计核减金额（万元）",
			immutableAdd: true
        },
        {
            type: "number",//
            name: "finalSettlementPrice",//
            label: "项目审计后最终结算价",//
            placeholder: "请输入项目审计后最终结算价",
			immutableAdd: true
        },
        {
            type: "text",//
            name: "ratio",//
            label: "项目审计核减比例%",//
            placeholder: "请输入项目审计核减比例%",
			immutableAdd: true
        },
        {
            type: "text",//
            name: "aduitTime",//
            label: "审计开始时间",//
            placeholder: "请输入审计开始时间",
			immutableAdd: true
        },
        {
            type: "text",//
            name: "chapter",//
            label: "章节名称",//
            placeholder: "请输入章节名称",
            immutableAdd: true
        },
        {
            type: "text",//
            name: "inventoryNumber",//
            label: "清单号",//
            placeholder: "请输入清单号",
            immutableAdd: true
        },
        {
            type: "text",//
            name: "inventoryName",//
            label: "审减费用清单名称",//
            placeholder: "请输入审减费用清单名称",
            immutableAdd: true
        },
        {
            type: "number",//
            name: "initAmount",//
            label: "初审计核减金额（万元）",//
            placeholder: "请输入初审计核减金额（万元）",
			immutableAdd: true
        },
        {
            type: "select",
            name: "initReason",
            label: "初审核减原因",//0：材料调整 1：工程质量验收不合格 2：计价规则影响 3：清单重复计量 4：索赔审计扣减 5：图纸错误导致后期扣减 6：未按图纸施工 7：与招标文件不符 8：其他
            immutableAdd: true,
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
			immutableAdd: true
        },
        {
            type: "textarea",//
            name: "auditReason",//
            label: "审计核减依据、理由",//
            placeholder: "请输入审计核减依据、理由",
			immutableAdd: true
        },
        {
            type: "number",//
            name: "finalAmount",//
            label: "最终审减金额（万元）",//
            placeholder: "请输入最终审减金额（万元）",
			immutableAdd: true
        },
        {
            type: "select",
            name: "finalReason",
            label: "终审减原因",//0：材料调整 1：工程质量验收不合格 2：计价规则影响 3：清单重复计量 4：索赔审计扣减 5：图纸错误导致后期扣减 6：未按图纸施工 7：与招标文件不符 8：其他
            immutableAdd: true,
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
			immutableAdd: true
        },
        {
            type: "textarea",//
            name: "tryWay",//
            label: "争取金额减少方式",//
            placeholder: "请输入争取金额减少方式",
			immutableAdd: true
        },
        {
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入备注",
			immutableAdd: true
        },
		{
            type: "upload",//
            name: "fileList",//
            label: "附件上传",//
            btnName: "选择文件",
            projectName: "zj-hw-resource",
            uploadUrl: "uploadFilesByFileName",
			immutableAdd: true,
            //maxLen: 1,
            fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf", "zip", "rar", "dwg"]
        }
    ],
})

var leading = $('#leading').detailLayer({
    layerArea: ['410px', '310px'],
    layerTitle: ['导入'],
    conditions: [
        {
            type: "upload",
            maxLen: 1,
            name: "importFileList",
            label: "导入文件",
            btnName: "选择文件",
            projectName: "zj-assets-haiwei",
            fileType: ["xls", "xlsx"],
        }
    ],
    onAdd: function (obj, _params) {
        if (_params.importFileList.length == 0) {
            layer.alert("请选择文件", { icon: 0, }, function (index) {
                layer.close(index);
            });
            return;
        }
        l.ajax('batchImportResourceAuditIssue', { "importFileList": _params.importFileList }, function (_params) {
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
        case "leading"://导入
            leading.open();
            break;
        case "exportOpen"://导入
            // var width = (window.screen.availWidth * 0.9);
            // var height =( window.screen.availHeight * 0.85);
            // var left = (window.screen.availWidth * 0.05);
            // var top =( window.screen.availHeight * 0.05);
            // window.open('http://192.168.1.223:8081/ureport/excel?_u=file:hwzyAuditIssue.xml&url=' + l.domain + '&proType='+proType, '', 'width='+ width +',height='+ height +',scrollbars=yes,top='+ top +',left='+ left);
            window.location.href = 'http://192.168.1.223:8081/ureport/excel?_u=file:hwzyAuditIssue.xml&url=' + l.domain + '&proType='+proType;
           // window.location.href = 'http://wechat.zjyjhw.com:88/haiwei/ureport/excel?_u=file:hwzyAuditIssue.xml&url=' + l.domain + '&proType='+proType;
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
                layer.confirm("确定删除？", { icon: 0, }, function (index) {
                    l.ajax("batchDeleteUpdateZjHwZyResourceAuditIssue", checkedData, function () {
                        pager.page('remote')
                    })
                    layer.close(index);
                });
            }
            break;
    }
})


function myOpen1(index, type) {
    var rowData = allData[Number(index)];
    var params;
    switch (type) {
        case 'xq':
            params = {};
            params.auditIssueId = rowData.auditIssueId;
            l.ajax('getZjHwZyResourceAuditIssueDetail', params, function (res) {
                seeLayer.open(res);
                $('#seeLayer .btn').hide();
            })
            break;
    }
}
