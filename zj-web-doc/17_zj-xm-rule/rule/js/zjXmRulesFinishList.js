var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30);
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
			"data": function (row) {
                return row
            },//接口对应字段
            "title": "制度名称",//表头名
            "defaultContent": "-",//默认显示
			"render": function (data,_,rowData) {
                var text = ""
                text=(data.remindFlag==="0"?"<span style='color:green;border:1px solid green'>新</span>&nbsp;":"")+data.ruleName
                if(rowData.mainPoint){
                    return '<div style="cursor:pointer" data-toggle="tooltip" title='+ rowData.mainPoint.replace(/(\r\n)|(\n)|(<br\/>)/g,'&#13;').replace(/ /g,'') +'>'+ text +'</div>';
                }else{
                    return '<div style="cursor:pointer" data-toggle="tooltip" title="无">'+ text +'</div>';
                }
            }			
        },
		{
            "targets": [3],//第几列
            "data": "referenceNumber",//接口对应字段
            "title": "文号",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [4],//第几列
            "data": "departmentName",//接口对应字段
            "title": "部门",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [5],//第几列
            "data": "tryScopeId",//接口对应字段
            "title": "适用范围",//表头名   0:全公司  1：公司总部  2：项目
            "defaultContent": "-"
        },
		{
            "targets": [6],//第几列
            "data": "state",//接口对应字段
            "title": "状态",//表头名   0:审核中 1：审核完成  2：废弃审核中
            "defaultContent": "-",//默认显示
            "render": function (data) {
                var text = ""
                switch (data) {
                    case "0": text = "新建（待提交）";
                        break;
                    case "1": text = "待审核";
                        break;
                    case "2": text = "审核驳回";
                        break;
					case "3": text = "审核完成（在使用）";
                        break;
					case "4": text = "驳回待审核";
                        break;	
					case "5": text = "废弃";
                        break;
					case "6": text = "废弃待审核";
                        break;
					case "7": text = "废弃驳回";
                        break;
                    default: text = "未知";
                        break;
                }
                return text
            }
        },
		{
            "targets": [7],//第几列
            "data": "abandonFlag",//接口对应字段
            "title": "是否是修订",//表头名   0:审核中 1：审核完成  2：废弃审核中
            "defaultContent": "-",//默认显示
            "render": function (data) {
                var text = ""
                switch (data) {
                    case "0": text = "否";
                        break;
                    case "1": text = "是";
                        break;
                    default: text = "未知";
                        break;
                }
                return text
            }
        },
		{
            "targets": [8],//第几列
            "data": "createUserName",//接口对应字段
            "title": "创建者",//表头名
            "defaultContent": "-",//默认显示
        },	
		{
            "targets": [9],//第几列
            "width": 80,
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

var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "ruleName",//输入字段名
            label: "制度名称",//输入字段对杨的中文名称
            placeholder: "请输入制度名称",
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "referenceNumber",//输入字段名
            label: "文号",//输入字段对杨的中文名称
            placeholder: "请输入文号",
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "departmentId",//输入字段名
            label: "部门",//输入字段对杨的中文名称
            placeholder: "请输入部门",
        },
		{
            type: "select",
            name: "tryScopeId",
            label: "适用范围",
            selectList: [//当类型为select时，option配置  0:全公司  1：机关  2：项目
                {
                    value: "",
                    text: "----"
                },
				{
                    value: "全公司",
                    text: "全公司"
                },
                {
                    value: "公司总部",
                    text: "公司总部"
                },
                {
                    value: "各单位",
                    text: "各单位"
                },
                {
                    value: "各项目",
                    text: "各项目"
                },
                {
                    value: "各子分公司",
                    text: "各子分公司"
                },
                {
                    value: "投资项目公司",
                    text: "投资项目公司"
                },
                {
                    value: "房建类项目",
                    text: "房建类项目"
                },
                {
                    value: "市政类项目",
                    text: "市政类项目"
                },
                {
                    value: "公路类项目",
                    text: "公路类项目"
                },
                {
                    value: "铁轨类项目",
                    text: "铁轨类项目"
                },
                {
                    value: "其他",
                    text: "其他"
                },
                {
                    value: "营销中心",
                    text: "营销中心"
                }
            ],
        }   
    ],
    onSearch: function (arr) {//搜索按钮回调
        var tryScopeId = [];
        $("select[name='tryScopeId'] option:selected").each(function () {
            tryScopeId.push($(this).val());
        })
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].name == "tryScopeId") {
                _params[arr[i].name] = tryScopeId.join(',');
            } else {
                _params[arr[i].name] = arr[i].value;
            }
        }
        pager.page('remote', 0, _params)
    },
    onReset: function (arr) {//重置按钮回调
        pager.page('remote', { tryScopeId: '', ruleName:'', referenceNumber:'', departmentId:'' });
    }
})
var allData;

var approvalFlag;

var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getMergeRulesList"),
        params: {
		 finishFlag:1
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
				 allData = data;			 
				    if (result.data.length > 0) {
                    if (result.data[0].mergeDelFlag == 0) {
                        $("#delete").hide();
                    }
                }			 
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
layerArea:['50%', '80%'],
    conditions: [
	    {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "ruleId",//输入字段名
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "ruleName",//输入字段名
            label: "制度名称",//输入字段对杨的中文名称
            placeholder: "请输入制度名称",
			immutableAdd: true
        },
		{
            type: "address",
            name: "referenceOne",
            label: "文号",
            immutableAdd: true,
			defaultValue: '厦_办发_2017',
            ajax: {
                api: "getAllList",
                valueName: "referenceNumberName",//number  recordId
                textName: "referenceNumberName",
                childrenName: "currentList",//设置children对应的接口字段名称
            }
        },  
		{
            type: "number",//三种形式：text,select,date,
            name: "referenceThree",//输入字段名
            label: "编号",//输入字段对杨的中文名称
            placeholder: "请输入文号",
			immutableAdd: true
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "departmentName",//输入字段名
            label: "部门",//输入字段对杨的中文名称
            placeholder: "请输入部门",
			immutableAdd: true
        },
		{
            type: "checkbox",
            name: "tryScopeIdList",
            label: "适用范围",
            immutableAdd: true,
            optionData: [//当类型为select时，option配置  0:全公司  1：机关  2：项目
                {
                    value: "全公司",
                    label: "全公司"
                },
                {
                    value: "公司总部",
                    label: "公司总部"
                },  
                {
                    value: "各单位",
                    label: "各单位"
                },
                {
                    value: "各项目",
                    label: "各项目"
                },
                {
                    value: "各子分公司",
                    label: "各子分公司"
                },
                {
                    value: "投资项目公司",
                    label: "投资项目公司"
                },
                {
                    value: "房建类项目",
                    label: "房建类项目"
                },
                {
                    value: "市政类项目",
                    label: "市政类项目"
                },
                {
                    value: "公路类项目",
                    label: "公路类项目"
                },
                {
                    value: "铁轨类项目",
                    label: "铁轨类项目"
                },
                {
                    value: "其他",
                    label: "其他"
                },
                {
                    value: "营销中心",
                    label: "营销中心"
                }
            ],
        },       
		{
            type: "textarea",//三种形式：text,select,date,
            name: "mainPoint",//输入字段名
            label: "制度要点",//输入字段对杨的中文名称
            placeholder: "请输入制度名称",
			immutableAdd: true
        },
		{
		    type: "upload",//
			name: "ruleList",//
			label: "制度附件",//
			btnName: "添加制度附件",
			projectName: "zj-xm-rule",
			uploadUrl:"uploadFilesByFileName",
			maxLen: 1,
			immutableAdd: true,
			fileType: ["docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
		},
		{
            type: "option",//三种形式：text,select,date,
            name: "flowList",//输入字段名
            label: "流程图及名称",//输入字段对杨的中文名称
            immutableAdd: true,
            placeholder: "请输入流程图名称"
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateZjXmRules', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('updateZjXmRules', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    }
})

var ruleDetailLayer = $('#ruleDetailLayer').detailLayer({
layerArea:['50%', '80%'],
    conditions: [
	    {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "ruleId",//输入字段名
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "ruleName",//输入字段名
            label: "制度名称",//输入字段对杨的中文名称
            placeholder: "请输入制度名称",
			immutableAdd: true
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "referenceNumber",//输入字段名
            label: "文号",//输入字段对杨的中文名称
            placeholder: "请输入文号",
			immutableAdd: true
        },
        {
            type: "text",//三种形式：text,select,date,
            name: "departmentName",//输入字段名
            label: "部门",//输入字段对杨的中文名称
            placeholder: "请输入部门",
			immutableAdd: true
        },		
		{
            type: "checkbox",
            name: "tryScopeIdList",
            label: "适用范围",
            immutableAdd: true,
            optionData: [//当类型为select时，option配置  0:全公司  1：机关  2：项目
                {
                    value: "全公司",
                    label: "全公司"
                },
                {
                    value: "公司总部",
                    label: "公司总部"
                },
                {
                    value: "各单位",
                    label: "各单位"
                },
                {
                    value: "各项目",
                    label: "各项目"
                },
                {
                    value: "各子分公司",
                    label: "各子分公司"
                },
                {
                    value: "投资项目公司",
                    label: "投资项目公司"
                },
                {
                    value: "房建类项目",
                    label: "房建类项目"
                },
                {
                    value: "市政类项目",
                    label: "市政类项目"
                },
                {
                    value: "公路类项目",
                    label: "公路类项目"
                },
                {
                    value: "铁轨类项目",
                    label: "铁轨类项目"
                },
                {
                    value: "其他",
                    label: "其他"
                },
                {
                    value: "营销中心",
                    label: "营销中心"
                }
            ],
        },      
		{
            type: "textarea",//三种形式：text,select,date,
            name: "mainPoint",//输入字段名
            label: "制度要点",//输入字段对杨的中文名称
            placeholder: "请输入制度名称",
			immutableAdd: true
        },
		{
		    type: "upload",//
			name: "ruleList",//
			label: "制度(含附件)",//
			btnName: "添加制度(含附件)",
			projectName: "zj-xm-rule",
			uploadUrl:"uploadFilesByFileName",
			maxLen: 1,
			immutableAdd: true,
			fileType: ["docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
		},
		{
            type: "option",//三种形式：text,select,date,
            name: "flowList",//输入字段名
            label: "流程图及名称",//输入字段对杨的中文名称
            immutableAdd: true,
            placeholder: "请输入流程图名称"
        }
    ]
})

//修订
var reviseLayer = $('#reviseLayer').detailLayer({
layerArea:['50%', '80%'],
    conditions: [
	    {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "ruleId",//输入字段名
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "ruleName",//输入字段名
            label: "废弃制度名称",//输入字段对杨的中文名称
            placeholder: "请输入制度名称",
			immutableAdd: true
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "newRuleName",//输入字段名
            label: "新制度名称",//输入字段对杨的中文名称
            placeholder: "请输入制度名称",
			must: true
        },
		{
            type: "select",
            name: "referenceOne",//reference_one
            label: "发文单位",
            ajax: {
                api: "getTwoLinkAllList",
                child: "referenceTwo",//reference_two
                valueName: "recordId",
                textName: "referenceNumberName",
                childrenName: "currentList",
            },
            must: true,
			lineNum: 3
        },
		{
            type: "select",
            name: "referenceTwo",
            label: "发文部门",
            ajax: {
                parent: "referenceOne",
                valueName: "recordId",
                textName: "referenceNumberName",
            },
            must: true,
			lineNum: 3
        },  
		{
            type: "text",//三种形式：text,select,date,
            name: "referenceThree",//输入字段名
            label: "年",//输入字段对杨的中文名称
            placeholder: "请输入年",
			must: true,
			lineNum: 4
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "referenceFour",//输入字段名
            label: "号",//输入字段对杨的中文名称
            placeholder: "请输入文号",
			must: true,
			lineNum: 4
        },  

		{
            type: "pickTree",//
            name: "oaDepartment",//接口字段名
            label: "部门",//
            must: true,
            pickType: {
                department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
                member: false,//成员列表对应接口字段名,false表示不开启该类
            }
        },
		{
            type: "checkbox",
            name: "tryScopeIdList",
            label: "适用范围",
            must: true,
            optionData: [//当类型为select时，option配置  0:全公司  1：机关  2：项目
                {
                    value: "全公司",
                    label: "全公司"
                },
                {
                    value: "公司总部",
                    label: "公司总部"
                },
                {
                    value: "各单位",
                    label: "各单位"
                },
                {
                    value: "各项目",
                    label: "各项目"
                },
                {
                    value: "各子分公司",
                    label: "各子分公司"
                },
                {
                    value: "投资项目公司",
                    label: "投资项目公司"
                },
                {
                    value: "房建类项目",
                    label: "房建类项目"
                },
                {
                    value: "市政类项目",
                    label: "市政类项目"
                },
                {
                    value: "公路类项目",
                    label: "公路类项目"
                },
                {
                    value: "铁轨类项目",
                    label: "铁轨类项目"
                },
                {
                    value: "其他",
                    label: "其他"
                },
                {
                    value: "营销中心",
                    label: "营销中心"
                }
            ],
        },        
		{
            type: "textarea",//三种形式：text,select,date,
            name: "mainPoint",//输入字段名
            label: "制度要点",//输入字段对杨的中文名称
            placeholder: "请输入制度名称",
			must: true
        },
		{
		    type: "upload",//
			name: "ruleList",//
			label: "制度(含附件)",//
			btnName: "添加制度(含附件)",
			projectName: "zj-xm-rule",
			uploadUrl:"uploadFilesByFileName",
			maxLen: 1,
			must: true,
			fileType: ["docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
		},
		{
            type: "option",//三种形式：text,select,date,
            name: "flowList",//输入字段名
            label: "流程图及名称",//输入字段对杨的中文名称
            placeholder: "请输入流程图名称"
        }
    ],
    onSave: function (obj, _params) {
	 layer.confirm("确定修订？", { icon: 0, }, function (index) {
        l.ajax('reviseZjXmRules', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
		 layer.close(index);
		 });	
    },
    onAdd: function (obj, _params) {
        l.ajax('reviseZjXmRules', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    }
})
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
		case "merge"://mergeWord   mergeWord2  wordToPDFAndMergePDF
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }else if(checkedData.length == 1){
			    layer.alert("至少选择两项制度进行合并汇编", { icon: 0, }, function (index) {
                    layer.close(index);
                });
			}else { 
			       layer.confirm("确定合并？", { icon: 0, }, function (index) {
                       l.ajax("wordToPDFAndMergePDF", checkedData, function (res) {
                            layer.alert("合并成功！", { icon: 0 }, function (index) {
                            layer.close(index);
                            window.location.href = res;
                          })
                       })
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
			  	for(var i=0;i<checkedData.length;i++){
				        layer.confirm("确定删除？", { icon: 0, }, function (index) {
                            l.ajax("batchDeleteUpdateZjXmRules", checkedData, function () {
                             pager.page('remote')
                            })
                          layer.close(index);
                        });
				    }									   
            }
            break;
	    case "revise11"://修订
            if (checkedData.length == 1) {
			   if(checkedData[0].state == '3' || checkedData[0].state == '7'){
                checkedData[0].tryScopeIdList = checkedData[0].tryScopeId.split(',');
                reviseLayer.open(checkedData[0]);		      
			   }else{
			       layer.alert("未审核无法修订", { icon: 0, }, function (index) {
                    layer.close(index);
                });
			   }                
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
		case "abandon"://废弃  
		    if (checkedData.length == 1) {
			   layer.confirm("确定废弃？", { icon: 0, }, function (index) {
				    checkedData[0].typeFlag = '4'//废弃提交
                    l.ajax("batchSubmitZjXmRules", checkedData, function () {
                        pager.page('remote')
                    })
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
    }
})

function myOpen1(index, type) {
    var rowData = allData[Number(index)];
    var params;
    switch (type) {
        case 'xq':
            params = {};
            params.ruleId = rowData.ruleId;
            // console.log('params:',params)
            l.ajax('getZjXmRulesDetail', params, function (res) {
                res.tryScopeIdList = res.tryScopeId.split(',');
                ruleDetailLayer.open(res);
                $('#ruleDetailLayer .btn').hide();
            })
            break;
		 case 'bj':
            params = {};
            params.ruleId = rowData.ruleId;
            // console.log('params:',params)
            l.ajax('getZjXmRulesDetail', params, function (res) {
                detailLayer.open(res);
            })
            break;
    }
}

function myOpen(title, id, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id
    }
    layer.full(layer.open(options))
}
function public(voteId) {//发布
    l.ajax('sendVoteInfo', { voteId: voteId }, function (data, message) {
        layer.alert(message, { icon: 0, }, function (index) {
            layer.close(index);
            pager.page('remote')
        });
    })
}
function preview(voteId) {//预览
    window.open(l.domainName + 'initMVotePreview.do?voteId=' + voteId + '&previewFlag=1', 'newwindow', 'height=500,width=350,top=100,left=500,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no')
}
$(function () { $("[data-toggle='tooltip']").tooltip()});
