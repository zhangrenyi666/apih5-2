var recordid = l.getUrlParam('id') || "";
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
            "data": "code3Name",//接口对应字段
            "title": "中标资质",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "unitName",//接口对应字段
            "title": "承担施工任务单位名称",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [4],//第几列
            "data": "projectName",//接口对应字段
            "title": "项目名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "projectClass",//接口对应字段
            "title": "项目类别",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "";
                switch (data) {
                    case "0":
                        r = "公路市政"
                        break
                    case "1":
                        r = "铁路轨道"
                        break
                    case "2":
                        r = "市政房建"
                        break
                }
                return r
            }
        },	
        {
            "targets": [6],//第几列
            "data": "schemeType",//接口对应字段
            "title": "方案类型",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//0：市政房建，1：路基路面；2：铁路轨道；3：桥梁；4：隧道；
                var _data = ""
                switch (data) {
                    case "0":
                        _data = "房建"
                        break;
                    case "1":
                        _data = "轨道"
                        break;
                    case "2":
                        _data = "铁路"
                        break;
                    case "3":
                        _data = "路桥"
                        break;
                    case "4":
                        _data = "隧道"
                        break;

                    default:
                        _data = "未知"
                        break;
                }
                return _data
            }
        },	
        {
            "targets": [7],//第几列
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "方案名称",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                var a = '<a style="color:blue;"  onclick="detailLook(' + data.rowIndex + ')" href="javascript:void(0);">' + data.schemeName + '</a>'
                return a;
            }
        },		
        {
            "targets": [8],//第几列
            "data": "schemeNumber",//接口对应字段
            "title": "方案编号",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [9],//第几列
            "data": "schemeLevel",//接口对应字段
            "title": "专项施工方案等级",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//0：一定规模（安全）专项施工方案；1：超过一定规模（安全）专项施工方案；3：技术性风险性控制性分部分项工程（安全）专项施工方案
                var _data = ""
                switch (data) {
                    case "0":
                        _data = "Ⅲ级施工方案"
                        break;
                    case "1":
                        _data = "Ⅱ级施工方案"
                        break;
                    case "2":
                        _data = "Ⅰ级施工方案"
                        break;
				    case "3":
                        _data = "Ⅳ级施工方案"
                        break;
                    default:
                        _data = "未知"
                        break;
                }
                return _data
            }
        },
        {
            "targets": [10],//第几列
            "data": "hierarchyDescription",//接口对应字段
            "title": "技术安全等级划分说明",//表头名
            "defaultContent": "-",//默认显示
        },	
        {
            "targets": [11],//第几列
            "data": "dutyExpert",//接口对应字段
            "title": "责任专家",//表头名
            "defaultContent": "-",//默认显示
        },		
        {
            "targets": [12],//第几列
            "data": "implementationTime",//接口对应字段
            "title": "方案计划实施时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd")
            }
        },		
        {
            "targets": [13],//第几列
            "data": "compilingSubject",//接口对应字段
            "title": "编制主体",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//0：公司技术中心；1：单位技术分中心；2：单位技术部门；3：项目经理部
                var _data = ""
                switch (data) {
                    case "0":
                        _data = "公司技术中心"
                        break;
                    case "1":
                        _data = "单位技术分中心"
                        break;
                    case "2":
                        _data = "单位技术部门"
                        break;
                    case "3":
                        _data = "项目经理部"
                        break;
                    case "4":
                        _data = "专业分包单位"
                        break;						
                    default:
                        _data = "未知"
                        break;
                }
                return _data
            }
        },
        {
            "targets": [14],//第几列
            "data": "reviewState",//接口对应字段
            "title": "评审状态",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未发起";
                switch (data) {
                    case "":
                        r = "未发起"
                        break
                    case "0":
                        r = "未评审"
                        break
                    case "1":
                        r = "评审通过"
                        break
                    case "2"://
                        r = "评审中"
                        break
                    case "3":
                        r = "评审未通过"
                        break
                    case "4"://
                        r = "评审已通过"
                        break
                }
                return r
            }
        },		
        {
            "targets": [15],//第几列
            "data": "implementationTime",//接口对应字段
            "title": "清单通过时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd")
            }
        },
        {
            "targets": [16],//第几列
            "data": "projectChiefEng",//接口对应字段
            "title": "项目总工",//表头名
            "defaultContent": "-",//默认显示
        },	
        {
            "targets": [17],//第几列
            "data": "projectEngMinister",//接口对应字段
            "title": "工程部长",//表头名
            "defaultContent": "-",//默认显示
        },			
		{
            "targets": [18],//第几列
            "data": "remarks",//接口对应字段
            "title": "备注",//表头名
            "defaultContent": "-",//默认显示
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [// 表单项配置
			        {
            type: "select",
            name: "projectClass",
            label: "项目类别",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 0,//输入字段的值
                    text: "公路市政",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "铁路轨道",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 2,//输入字段的值
                    text: "市政房建",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        },
        {
            type: "select",
            name: "schemeType",
            label: "方案类型",
            selectList: [
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },			
                {
                    value: "0",
                    text: "房建",
					selected: false//默认是否选中
                }, {
                    value: "1",
                    text: "轨道",
					selected: false//默认是否选中
                }, {
                    value: "2",
                    text: "铁路",
					selected: false//默认是否选中
                }, {
                    value: "3",
                    text: "路桥",
					selected: false//默认是否选中
                }, {
                    value: "4",
                    text: "隧道",
					selected: false//默认是否选中
                }
            ]//0：市政房建，1：路基路面；2：铁路轨道；3：桥梁；4：隧道；
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
         // console.log('搜索参数是：',_params)
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
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjSchemeDetailedList"),
        params: {
            recordid: recordid
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
    customBtnGroup: {
        btns: [
            {
                name: "close",
                label: "关闭"
            }
        ],
        callback: function (dataName, obj) { obj.close() }
    },
	layerArea:['70%', '90%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "detailedListId",//输入字段名
        },
        {
            type: "select",
            name: "schemeType",
            label: "方案类型",
            selectList: [
                {
                    value: "0",
                    text: "市政房建"
                }, {
                    value: "1",
                    text: "路基路面"
                }, {
                    value: "2",
                    text: "铁路轨道"
                }, {
                    value: "3",
                    text: "桥梁"
                }, {
                    value: "4",
                    text: "隧道"
                }
            ],//0：市政房建，1：路基路面；2：铁路轨道；3：桥梁；4：隧道；
            immutableAdd: true
        },
        {
            type: "select",
            name: "schemeLevel",
            label: "专项施工方案等级",
            selectList: [
                {
                    value: "0",
                    text: "一定规模（安全）专项施工方案"
                }, {
                    value: "1",
                    text: "超过一定规模（安全）专项施工方案"
                }, {
                    value: "2",
                    text: "技术性风险性控制性分部分项工程（安全）专项施工方案"
                }
            ],//0：一定规模（安全）专项施工方案；1：超过一定规模（安全）专项施工方案；3：技术性风险性控制性分部分项工程（安全）专项施工方案
            immutableAdd: true
        },
        {
            type: "select",
            name: "compilingSubject",
            label: "编制主体",
            selectList: [
                {
                    value: "0",
                    text: "公司技术中心"
                }, {
                    value: "1",
                    text: "单位技术分中心"
                }, {
                    value: "2",
                    text: "单位技术部门"
                }, {
                    value: "3",
                    text: "项目经理部"
                }, {
                    value: "4",
                    text: "专业分包单位"
                }
            ],//0：公司技术中心；1：单位技术分中心；2：单位技术部门；3：项目经理部
            immutableAdd: true
        },
        {
            type: "text",
            name: "schemeName",
            label: "方案名称",
            immutableAdd: true
        },
        {
            type: "text",
            name: "schemeNumber",
            label: "方案编号",
            immutableAdd: true
        },
        {
            type: "date",//text,select,date,
            name: "implementationTime",
            id: "implementationTime",
            label: "方案计划实施时间",
            immutableAdd: true
        },
        {
            type: "textarea",//
            name: "hierarchyDescription",//
            label: "技术安全等级划分说明",//
            placeholder: "请输入内容",
            immutableAdd: true
        },
        {
            type: "text",//五种形式：text,select,date,hidden,textarea,
            name: "projectEngMinister",//输入字段名
            label: "工程部长",
            immutableAdd: true
        },			
		{
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入内容",
			immutableAdd: true
        }
    ]
})
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "close":
            parent.layer.close(parent.myOpenLayer)
            break;
    }
})
$("#tab-system").Huitab({
    index: 0
});
function detailLook(rowIndex) {
    detailLayer.open(table.row(rowIndex).data())
}

l.ajax("ZjSchemeConfirmationDetail", { recordid: recordid }, function (data, message, success) {
    if (success) {
        var immutableAdd = data.chiefEngineerExamineResult === "0" || data.chiefEngineerExamineResult === "1" || data.projectState === "0"
        var detailForm = $('#detailForm').detailLayer({
            customBtnGroup: {
                btns: immutableAdd ? [
                    {
                        name: "close",
                        label: "关闭"
                    }

                ] : [
                        {
                            name: "agree",
                            label: '<i class="Hui-iconfont">&#xe6e1;</i> 通过'
                        },
                        {
                            name: "reject",
                            label: '<i class="Hui-iconfont">&#xe6dd;</i> 不通过'
                        },
                        {
                            name: "close",
                            label: "关闭"
                        }
                    ],
                callback: function (dataName, obj) {
                    switch (dataName) {
                        case "agree":
                        case "reject":
                            var dataObj = obj.getDatas()
                            var body = dataObj.data
                            body.recordid = recordid
                            body.chiefEngineerExamineResult = dataName === "agree" ? "1" : "0"
                            layer.confirm("确定此操作么？", { icon: 0, }, function (index) {
                                layer.close(index);
                                l.ajax("updateZjSchemeConfirmation", body, function (data, message, success) {
                                    if (success) {
                                        parent.pager.page('remote')
                                        layer.alert(message, { icon: 0, }, function (index2) {
                                            layer.close(index2);
                                            parent.layer.close(parent.myOpenLayer)
                                        });
                                    }
                                })
                            });
                            break;
                        case "close":
                        default:
                            obj.close()
                            break;
                    }
                }
            },
            conditions: [
                {
                    type: "hidden",//五种形式：text,select,date,hidden,textarea,
                    name: "projectId",//输入字段名
                    label: "项目名称",
                    immutableAdd: true
                },
                {
                    type: "hidden",//五种形式：text,select,date,hidden,textarea,
                    name: "projectName",//输入字段名
                    label: "项目名称",
                    immutableAdd: true
                },
				{
            type: "select",
            name: "unitId",
            label: "单位名称",
            selectList: [//当类型为select时，option配置
			    {
                    value: "",//输入字段的值
                    text: "请选择",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: '4d0026M1211f18fd7cMc3cf724c68492c819757e60a238b17d4',//输入字段的值
                    text: "一公司",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: '4d0026M1211f198db8Mc3cf724c68492c819757e60a238b17d4',//输入字段的值
                    text: "二公司",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: '1f33ecbM1215114069dMc3cf724c68492c819757e60a238b17d4',//输入字段的值
                    text: "三公司",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: '4d0026M1211f1b6529Mc3cf724c68492c819757e60a238b17d4',//输入字段的值
                    text: "四公司",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: '848c58M11ddc135570Mcac6cc7252f26ad204ac504de95ccb51',//输入字段的值
                    text: "五公司",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: '4d0026M1211f1bbfe4Mc3cf724c68492c819757e60a238b17d4',//输入字段的值
                    text: "六公司",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: '4d0026M1211f20f203Mc3cf724c68492c819757e60a238b17d4',//输入字段的值
                    text: "桥隧公司",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: '4d0026M1211f214b76Mc3cf724c68492c819757e60a238b17d4',//输入字段的值
                    text: "厦门公司",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 0,//输入字段的值
                    text: "海外事业部",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: '2fcb4fM11e4c91e321Mcac6cc7252f26ad204ac504de95ccb51',//输入字段的值
                    text: "海威公司",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: '4d0026M1211f206d02Mc3cf724c68492c819757e60a238b17d4',//输入字段的值
                    text: "总承包公司",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: '4d0026M1211f1f4cdcMc3cf724c68492c819757e60a238b17d4',//输入字段的值
                    text: "七公司",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: '15c46b5M137e357f29cM9ce72a2dc070b141941f7d5b228cc039',//输入字段的值
                    text: "北京建筑分公司",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: '4d0026M1211f1e7c84Mc3cf724c68492c819757e60a238b17d4',//输入字段的值
                    text: "世通重工",//显示中文名
                    selected: false//默认是否选中
                }															
            ],
                immutableAdd: true
        },
				/*
                {
                    type: "hidden",
                    name: "unitName",
                    label: "单位名称",
                    immutableAdd: true,
                },
				*/
                {
                    type: "text",
                    name: "engineeringType",
                    label: "工程类别",
                    immutableAdd: true,
                },
				        {
            type: "text",
            name: "code3Name",
            label: "中标资质",
            immutableAdd: true
        },					
                {
                    type: "text",
                    name: "province",
                    label: "所属省份",
                    immutableAdd: true
                },
                {
                    type: "date",//text,select,date,
                    name: "projectStartDate",
                    id: "projectStartDate",
                    label: "项目开工日期",
                    immutableAdd: true
                },
                {
                    type: "date",//text,select,date,
                    name: "projectEndDate",
                    id: "projectEndDate",
                    label: "项目竣工日期",
                    immutableAdd: true
                },
                {
                    type: "text",//
                    name: "projectContractAmount",//
                    label: "项目合同金额",//
                    placeholder: "请输入内容",
                    immutableAdd: true
                },
				        {
            type: "text",
            name: "projectChiefEng",
            label: "项目总工",
            immutableAdd: true
        },	
                {
                    type: "textarea",//
                    name: "remarks",//
                    label: "备注",//
                    placeholder: "请输入内容",
                    immutableAdd: true
                },
                {
                    type: "pickTree",
                    name: "chiefEngineer",
                    label: "单位总工",
                    maxLen: 1,
                    pickType: {
                        department: false,//部门列表对应接口字段名,false表示不开启该类
                        member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
                    },
                    immutableAdd: true
                },
                {
                    type: "textarea",//
                    name: "chiefEngineerExamineOpinion",//
                    label: "总工审批意见",//
                    placeholder: "请输入内容",
                    immutableAdd: immutableAdd
                },
                {
                    type: "textarea",//
                    name: "bureauExamineOpinion",//
                    label: "局审批意见",//
                    placeholder: "请输入内容",
                    immutableAdd: true
                }
            ]
        })
        detailForm.close = function () {
            parent.layer.close(parent.myOpenLayer)
        }
        detailForm.find("form").prepend($('<div class="row cl"><label class="form-label  col-2 f-l"><i style="color:red;">* </i>项目名称：</label><div class="col-10 f-l"><input type="text" class="input-text SearchSelect" onfocus="SearchSelect()" disabled="disabled" value="' + data.projectName + '" name="projectData"></div></div>'))
        detailForm.setOpenData(data)
    }
})






