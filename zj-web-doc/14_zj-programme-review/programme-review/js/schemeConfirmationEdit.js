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



var detailForm = $('#detailForm').detailLayer({
    conditions: [
        {
            type: "select",
            name: "projectClass",
            label: "项目类别",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "请选择",//显示中文名
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
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "projectName",//输入字段名
            label: "项目名称",
            must: true,
            immutableAdd: true
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "contractNo",//输入字段名
            label: "合同编号",
            must: true,
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
			must: true
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
            immutableAdd: true
        },			
        {
            type: "text",
            name: "projectChiefEng",
            label: "项目总工",
            must: true
        },					
        {
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入内容",
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
            must: true
        },
        {
            type: "textarea",//
            name: "chiefEngineerExamineOpinion",//
            label: "总工审批意见",//
            placeholder: "请输入内容",
            immutableAdd: true
        },
        {
            type: "textarea",//
            name: "bureauExamineOpinion",//
            label: "局审批意见",//
            placeholder: "请输入内容",
            immutableAdd: true
        }
    ],
    onSave: function (obj, _params) {
        if (recordid) {
            _params.recordid = recordid
            l.ajax("updateZjSchemeConfirmation", _params, function (data, message, success) {
                if (success) {
                    layer.alert(message, { icon: 0, }, function (index) {
                        parent.pager.page('remote')
                        layer.close(index);
                        $("#tab-system").Huitab({
                            index: 1
                        });
                    });
                }
            })
        } else {
            l.ajax("addZjSchemeConfirmation", _params, function (data, message, success) {
                if (success) {
                    recordid = data.recordid
                    layer.alert(message, { icon: 0, }, function (index) {
                        parent.pager.page('remote')
                        layer.close(index);

                        $("#tab-system").Huitab({
                            index: 1
                        });

                        loadPage()
                    });
                }
            })
        }
    }
})
var $projectInput = $('<div class="row cl"><label class="form-label  col-2 f-l"><i style="color:red;">* </i>项目名称：</label><div class="col-10 f-l"><input type="text" class="input-text SearchSelect"  onfocus="SearchSelect({ apiName: \'getZjWoaProjectDataList\', otherKey: \'projectState\' })" value="" name="projectName"></div></div>')
detailForm.find("form").prepend($projectInput)
detailForm.close = function () {
    parent.layer.close(parent.myOpenLayer)
}
loadPage()
function loadPage() {
    if (recordid) {
        l.ajax("ZjSchemeConfirmationDetail", { recordid: recordid }, function (data, message, success) {
            if (success) {
                var projectName = data.projectName || ""
                $projectInput.find("input").attr("disabled", "disabled").val(projectName)
                detailForm.setOpenData(data)
            }
        })
    } else {
        detailForm.setOpenData({ memberList: { oaMemberList: [] } })
    }
}









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
		layerArea:['70%', '90%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "recordid",//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "detailedListId",//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "contractNo",//输入字段名
            label: "合同编号",
            must: true
        },
        {
            type: "select",
            name: "schemeType",
            label: "方案类型",
            selectList: [
                {
                    value: "0",
                    text: "房建"
                }, {
                    value: "1",
                    text: "轨道"
                }, {
                    value: "2",
                    text: "铁路"
                }, {
                    value: "3",
                    text: "路桥"
                }, {
                    value: "4",
                    text: "隧道"
                }
            ]//0：市政房建，1：路基路面；2：铁路轨道；3：桥梁；4：隧道；
        },
        {
            type: "select",
            name: "schemeLevel",
            label: "专项施工方案等级",
            selectList: [
                {
                    value: "0",
                    text: "Ⅲ级施工方案"
                }, {
                    value: "1",
                    text: "Ⅱ级施工方案"
                }, {
                    value: "2",
                    text: "Ⅰ级施工方案"
                }, {
                    value: "3",
                    text: "Ⅳ级施工方案"
                }
            ]//0：一定规模（安全）专项施工方案；1：超过一定规模（安全）专项施工方案；3：技术性风险性控制性分部分项工程（安全）专项施工方案
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
            ]//0：公司技术中心；1：单位技术分中心；2：单位技术部门；3：项目经理部
        },
        {
            type: "text",
            name: "schemeName",
            label: "方案名称",
            must: true
        },
        {
            type: "text",
            name: "schemeNumber",
            label: "方案编号",
            immutableAdd: true,
        },
        {
            type: "date",//text,select,date,
            name: "implementationTime",
            id: "implementationTime",
            label: "方案计划实施时间",
            defaultValue: new Date().getTime(),
        },
        {
            type: "textarea",//
            name: "hierarchyDescription",//
            label: "技术安全等级划分说明",//
            placeholder: "请输入内容",
        },
        {
            type: "text",//五种形式：text,select,date,hidden,textarea,
            name: "projectEngMinister",//输入字段名
            label: "工程部长"
        },		
		{
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入内容",
        }
    ],
    onSave: function (obj, _params) {
        l.ajax("updateZjSchemeDetailedList", _params, function (data, message, success) {
            if (success) {
                pager.page('remote', { recordid: recordid })
                layer.alert(message, { icon: 0, }, function (index) {
                    layer.close(index);
                    obj.close()
                });
            }
        })
    },
    onAdd: function (obj, _params) {
        l.ajax("addZjSchemeDetailedList", _params, function (data, message, success) {
            if (success) {
                pager.page('remote', { recordid: recordid })
                layer.alert(message, { icon: 0, }, function (index) {
                    layer.close(index);
                    obj.close()
                });
            }
        })
    },
})
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            if (recordid) {
                detailLayer.open({ isAdd: true, recordid: recordid, contractNo: detailForm.getDatas().data.contractNo })
            } else {
                layer.alert("请先切换到基本信息完善必填信息点击确认后再新增方案！", { icon: 0, }, function (index) {
                    layer.close(index);
                    $("#tab-system").Huitab({
                        index: 0
                    });
                });
            }
            break;
        case "edit":
            if (checkedData.length == 1) {
                checkedData[0].contractNo = detailForm.getDatas().data.contractNo
                detailLayer.open(checkedData[0])
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
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.confirm("确定删除？", { icon: 0, }, function (index) {
                    l.ajax("batchDeleteUpdateZjSchemeDetailedList", checkedData, function (data, success, message) {
                        if (success) {
                            pager.page('remote')
                        }
                    })
                    layer.close(index);
                });
            }
            break;
        case "close":
            parent.layer.close(parent.myOpenLayer)
            break;

    }
})
$("#tab-system").Huitab({
    index: 0
});





var searchSelectDiv;
var curELe;
var searchSelectDatas = {}
var searchSelectErr = false
/**
 * addEventlistener兼容函数
 * ie9以上正常使用addEventlistener函数
 * ie7、ie8用传递的function的function.prototype储存经过处理的事件
 * function.prototype["_" + type]：记录处理后的信息
 * function.prototype["_" + type]._function <function>：经过处理的事件
 * function.prototype["_" + type]._element  <array>   ：已经绑定的dom
 */
var ua = navigator.userAgent.toLowerCase();
var isIE = /msie/.test(ua);
/*** addEventlistener ***/
var addListener = (function () {
    if (document.addEventListener) {
        /* ie9以上正常使用addEventListener */
        return function (element, type, fun, useCapture) {
            element.addEventListener(type, fun, useCapture ? useCapture : false);
        };
    } else {
        /* ie7、ie8使用attachEvent */
        return function (element, type, fun) {
            type = type !== "input" ? type : "propertychange"
            if (!fun.prototype["_" + type]) {
                /* 该事件第一次绑定 */
                fun.prototype["_" + type] = {
                    _function: function (event) {
                        fun.call(element, event);
                    },
                    _element: [element]
                };
                element.attachEvent("on" + type, fun.prototype["_" + type]._function);
            } else {
                /* 该事件被绑定过 */
                var s = true;
                // 判断当前的element是否已经绑定过该事件
                for (var i in fun.prototype["_" + type]._element) {
                    if (fun.prototype["_" + type]._element[i] === element) {
                        s = false;
                        break;
                    }
                }
                // 当前的element没有绑定过该事件
                if (s === true) {
                    element.attachEvent("on" + type, fun.prototype["_" + type]._function);
                    fun.prototype["_" + type]._element.push(element);
                }
            }
        };
    }
})();
/*** removeEventlistener ***/
var removeListener = (function () {
    if (document.addEventListener) {
        /* ie9以上正常使用removeEventListener */
        return function (element, type, fun) {
            element.removeEventListener(type, fun);
        };
    } else {
        /* ie7、ie8使用detachEvent */
        return function (element, type, fun) {
            type = type !== "input" ? type : "propertychange"
            element.detachEvent("on" + type, fun.prototype["_" + type]._function);
            if (fun.prototype["_" + type]._element.length === 1) {
                // 该事件只有一个element监听，删除function.prototype["_" + type]
                delete fun.prototype["_" + type];
            } else {
                // 该事件只有多个element监听，从function.prototype["_" + type]._element数组中删除该element
                for (var i in fun.prototype["_" + type]._element) {
                    if (fun.prototype["_" + type]._element[i] === element) {
                        fun.prototype["_" + type]._element.splice(i, 1);
                        break;
                    }
                }
            }
        };
    }
})();
function setSearchSelect(searchSelectData) {
    if (searchSelectData) {
        curELe.value = searchSelectData[curELe.name] || ""
        searchSelectDatas[curELe.name] = searchSelectData
    } else {
        delete searchSelectDatas[curELe.name];
    }
    var selectData = searchSelectDatas[curELe.name] || {}
    var formData = detailForm.getDatas().data
    var conditions = detailForm.conditions
    if (curELe.name === "projectName") {
        $("input[name=schemeNumber]").val("")
    }
    function isImm(k, arrs) {
        var is = false
        for (var index = 0; index < arrs.length; index++) {
            if (arrs[index].name === k) {
                is = arrs[index].immutableAdd || false
                break
            }
        }
        return is
    }
    var newData = {}
    for (var key in formData) {
        if (isImm(key, conditions)) {
            newData[key] = selectData[key]
        } else {
            newData[key] = formData[key]
        }
    }

    detailForm.setOpenData(newData)
    closeSearchSelect()
}
function blurSearchSelect() {
    var event = this.event || window.event;
    var target = event.target || event.srcElement; //获取document 对象的引用 
    if (target !== curELe) {
        if (curELe && curELe.value) {
            searchSelectErr = true
            if (confirm("未从列表中选取项目！")) {
                curELe.value = searchSelectDatas[curELe.name][curELe.name] || ""
            }
        } else {
            setSearchSelect()
        }
    }
}
function closeSearchSelect() {
    searchSelectErr = false
    searchSelectDiv.style.display = "none"
    curELe = null
    removeListener(document, "mousedown", blurSearchSelect)
}
function SearchSelect(options) {
    options = options || { apiName: "api", otherKey: "other" }
    var apiName = options.apiName || "api"
    var otherKey = options.otherKey || "other"
    var otherValue = getOtherValue(otherKey)

    var event = this.event || window.event;
    var target = event.target || event.srcElement; //获取document 对象的引用 
    if (curELe === target && !searchSelectErr) {
        return
    }
    if (searchSelectErr) {
        if (curELe !== target) {
            target.blur()
        }
        return
    }
    curELe = target
    searchSelectDatas[curELe.name] = {}
    var tpos = getPos(curELe)
    function getPos(ele) {
        var pEle = ele.offsetParent
        if (pEle.nodeName !== "BODY") {
            return {
                top: ele.offsetTop + getPos(pEle).top,
                left: ele.offsetLeft + getPos(pEle).left
            }
        } else {
            return {
                top: ele.offsetTop,
                left: ele.offsetLeft
            }
        }
    }
    if (!searchSelectDiv) {
        searchSelectDiv = document.createElement("div")
        var iframe = document.createElement("iframe")
        searchSelectDiv.appendChild(iframe)
        document.body.appendChild(searchSelectDiv)
    }
    searchSelectDiv.style.display = "block"
    searchSelectDiv.style.position = "absolute"
    searchSelectDiv.style.top = tpos.top + curELe.offsetHeight + "px"
    searchSelectDiv.style.left = tpos.left + "px"
    searchSelectDiv.style.zIndex = "100006"
    var searchSelectiframe = searchSelectDiv.children[0]
    searchSelectiframe.setAttribute('hideFocus', true, 0);
    searchSelectiframe.setAttribute('width', "240px", 0);
    searchSelectiframe.setAttribute('height', "200px", 0);
    searchSelectiframe.setAttribute('frameborder', "0", 0);
    searchSelectiframe.setAttribute('border', "0", 0);
    searchSelectiframe.setAttribute('scrolling', "no", 0);
    searchSelectiframe.setAttribute('src', "searchSelect.html?otherKey=" + otherKey + "&otherValue=" + otherValue + "&keywordName=" + curELe.name + "&apiName=" + apiName + "&keyword=", 0);
    function inputFun() {
        searchSelectErr = false
        searchSelectiframe.setAttribute('src', "searchSelect.html?otherKey=" + otherKey + "&otherValue=" + otherValue + "&keywordName=" + curELe.name + "&apiName=" + apiName + "&keyword=" + curELe.value, 0);
    }
    addListener(curELe, "input", inputFun)
    addListener(document, "mousedown", blurSearchSelect)
}
function getOtherValue(otherKey) {
    switch (otherKey) {
        case "projectState":
            return "2"
            break;
        case "recordid":
            return detailForm.getDatas().data.recordid
            break;
        default:
            return ""
            break;
    }
}
function detailLook(rowIndex) {
    detailLayer.open(table.row(rowIndex).data())
}





