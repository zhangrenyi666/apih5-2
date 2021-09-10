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
            "data": "flowName",//接口对应字段
            "title": "流程名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "todoCount",//接口对应字段
            "title": "待办数量",//表头名
            "defaultContent": "-",//默认显示
			"render":function(data,index,row){
                var a;
                if(row.flowId =='sanDjBusiness'){   
                   a = '<a style="color:blue;" onclick="myOpen1(\''+row.flowId+ '\',\''+ 'flowPersonageList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjFourNew'){   
                   a = '<a style="color:blue;" onclick="myOpen1(\''+row.flowId+ '\',\''+ 'flowCollectiveList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjSkill'){   
                   a = '<a style="color:blue;" onclick="myOpen1(\''+row.flowId+ '\',\''+ 'flowSkillInnovationList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjScientific'){   
                   a = '<a style="color:blue;" onclick="myOpen1(\''+row.flowId+ '\',\''+ 'flowProprietaryTechnologyList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjMonograp'){   
                   a = '<a style="color:blue;" onclick="myOpen1(\''+row.flowId+ '\',\''+ 'flowPatentList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjSuggest'){   
                   a = '<a style="color:blue;" onclick="myOpen1(\''+row.flowId+ '\',\''+ 'flowSuggestList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjPaper'){   
                   a = '<a style="color:blue;" onclick="myOpen1(\''+row.flowId+ '\',\''+ 'flowPaperList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjProfessionalTitle'){   
                   a = '<a style="color:blue;" onclick="myOpen1(\''+row.flowId+ '\',\''+ 'flowSkillStandardList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjQc'){   
                   a = '<a style="color:blue;" onclick="myOpen1(\''+row.flowId+ '\',\''+ 'flowQcList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjHonor'){   
                   a = '<a style="color:blue;" onclick="myOpen1(\''+row.flowId+ '\',\''+ 'flowRdProjectsList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjCredential'){   
                   a = '<a style="color:blue;" onclick="myOpen1(\''+row.flowId+ '\',\''+ 'flowConstructStandardList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjCollege'){   
                   a = '<a style="color:blue;" onclick="myOpen1(\''+row.flowId+ '\',\''+ 'flowCertificateList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjEducation'){   
                   a = '<a style="color:blue;" onclick="myOpen1(\''+row.flowId+ '\',\''+ 'flowProfessionalList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjQcComprehensive'){   
                   a = '<a style="color:blue;" onclick="myOpen1(\''+row.flowId+ '\',\''+ 'flowComprehensiveList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjQcBusinessElv'){   
                   a = '<a style="color:blue;" onclick="myOpen1(\''+row.flowId+ '\',\''+ 'flowBusinessList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }
			    return a;
            }
        },        
        {
            "targets": [4],//第几列
            "data": "hasTodoCount",//接口对应字段
            "title": "已办数量",//表头名
            "defaultContent": "-",//默认显示
			"render":function(data,index,row){
                var a;
                if(row.flowId =='sanDjBusiness'){   
                   a = '<a style="color:blue;" onclick="myOpen2(\''+row.flowId+ '\',\''+ 'flowPersonageList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjFourNew'){   
                   a = '<a style="color:blue;" onclick="myOpen2(\''+row.flowId+ '\',\''+ 'flowCollectiveList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjSkill'){   
                   a = '<a style="color:blue;" onclick="myOpen2(\''+row.flowId+ '\',\''+ 'flowSkillInnovationList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjScientific'){   
                   a = '<a style="color:blue;" onclick="myOpen2(\''+row.flowId+ '\',\''+ 'flowProprietaryTechnologyList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjMonograp'){   
                   a = '<a style="color:blue;" onclick="myOpen2(\''+row.flowId+ '\',\''+ 'flowPatentList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjSuggest'){   
                   a = '<a style="color:blue;" onclick="myOpen2(\''+row.flowId+ '\',\''+ 'flowSuggestList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjPaper'){   
                   a = '<a style="color:blue;" onclick="myOpen2(\''+row.flowId+ '\',\''+ 'flowPaperList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjProfessionalTitle'){   
                   a = '<a style="color:blue;" onclick="myOpen2(\''+row.flowId+ '\',\''+ 'flowSkillStandardList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjQc'){   
                   a = '<a style="color:blue;" onclick="myOpen2(\''+row.flowId+ '\',\''+ 'flowQcList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjHonor'){   
                   a = '<a style="color:blue;" onclick="myOpen2(\''+row.flowId+ '\',\''+ 'flowRdProjectsList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjCredential'){   
                   a = '<a style="color:blue;" onclick="myOpen2(\''+row.flowId+ '\',\''+ 'flowConstructStandardList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjCollege'){   
                   a = '<a style="color:blue;" onclick="myOpen2(\''+row.flowId+ '\',\''+ 'flowCertificateList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjEducation'){   
                   a = '<a style="color:blue;" onclick="myOpen2(\''+row.flowId+ '\',\''+ 'flowProfessionalList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjQcComprehensive'){   
                   a = '<a style="color:blue;" onclick="myOpen2(\''+row.flowId+ '\',\''+ 'flowComprehensiveList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }else if(row.flowId =='sanDjQcBusinessElv'){   
                   a = '<a style="color:blue;" onclick="myOpen2(\''+row.flowId+ '\',\''+ 'flowBusinessList' + '\')" href="javascript:void(0);">'+data+'</a>'
                }
			    return a;
            }
        },
		{
            "targets": [5],//第几列
            "data": "totalCount",//接口对应字段
            "title": "合计",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [6],//第几列
            "data": "hasPer",//接口对应字段
            "title": "已办占比",//表头名
            "defaultContent": "-",//默认显示
        }
		
        
        
        
    ]
});


var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "date",//text,select,date,
            name: "startTime",
            label: "查看日期",
            //defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd"),
            maxDate: "\'#F{$dp.$D(\\\'" + "logmax" + "\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmin"
        },
        {
            type: "date",//text,select,date,
            name: "endTime",
            label: "比较日期",
            //defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd"),
            //maxDate: "\'#F{$dp.$D(\\\'"+"logmin"+"\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmax"
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
        pager.page('remote', 0, _params)
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
        url: l.getApiUrl("getZjSanDjGradeFlowTotalList"),//getZjSanDjGradeFlowTotalList  getFlowCountByAll
        params: {
		//flowId:"sanDjBusiness,sanDjFourNew,sanDjStyleGame,sanDjSkill,sanDjScientific,sanDjMonograp,sanDjSuggest,sanDjPaper,sanDjProfessionalTitle,sanDjQc,sanDjHonor,sanDjCredential,sanDjCollege,sanDjEducation,sanDjQcComprehensive,sanDjQcBusinessElv"
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
            name: "recordId",//输入字段名
        }, 
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "gradeType",//输入字段名
			defaultValue:"ZSXYJL",
        },  
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "gradeTypeName",//输入字段名
			defaultValue:"掌上学院奖励",
        },  
        {
            type: "text",//
            name: "scoreName",//
            label: "名称",//
            placeholder: "请输入名称",
            must: true
        },
        {
            type: "number",//
            name: "score",//
            label: "分值",//
            placeholder: "请输入分值",
			must: true
        }        
    ],
    onSave: function (obj, _params) {
        l.ajax('updateZjSanDjGradeStandard', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjSanDjGradeStandard', _params, function (data) {
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
				l.ajax("batchDeleteUpdateZjSanDjGradeStandard", checkedData, function () {
					pager.page('remote')
				})
				layer.close(index);
            });
            }
            break;
    }
})

function myOpen1(id, url) {
    var type ='';
    var options = {
        type: 2,
        title: '待办详情',
        content: url + ".html?id="+id+'&type=' + type + '&code=' + code + '&apih5FlowStatusFlag=' + 2
    }
    myOpenLayer = layer.open(options)
    layer.full(myOpenLayer)
}
function myOpen2(id, url) {
    var type ='';
    var options = {
        type: 2,
        title: '已办详情',
        content: url + ".html?id="+id+'&type=' + type + '&code=' + code + '&apih5FlowStatus=' + 2
    }
    myOpenLayer = layer.open(options)
    layer.full(myOpenLayer)
}