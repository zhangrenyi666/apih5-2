var informationId = l.getUrlParam('id') || "";
var project = "";
var title = "";
var departmentId = "";
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
            "width": 35,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名
            "render": function (data) {
                var html = '';
                html += '<span class="dropDown">'
                if (data.readFlag == "1") {
                 html += '<a style="padding-bottom:10px;" onmouseenter="myOpens($(this))" onmouseleave="myOpena($(this))" href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" onclick="confirmTableRow('+data.rowIndex+')"><span style="color:#6B8E23; font-weight:800;">通&nbsp;&nbsp;过</span></a>';
                 html += '<a style="padding-bottom:10px;" onmouseenter="myOpens($(this))" onmouseleave="myOpena($(this))" href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" onclick="improvementTableRow(' + data.rowIndex + ')"><span style="color:#6B8E23; font-weight:800;">改&nbsp;&nbsp;进</a>';                 
                 html += '<a onmouseenter="myOpens($(this))" onmouseleave="myOpena($(this))" href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" onclick="changeTableRow(' + data.rowIndex + ')"><span style="color:#6B8E23; font-weight:800;">整&nbsp;&nbsp;改</a>';
                }else if(data.readFlag == "2"){
                    html += '<a style="padding-bottom:10px;" onmouseenter="myOpens($(this))" onmouseleave="myOpena($(this))" href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" onclick="confirmTableRow('+data.rowIndex+')"><span style="color:#CD0000; font-weight:800;">通&nbsp;&nbsp;过</span></a>';
                    html += '<a style="padding-bottom:10px;" onmouseenter="myOpens($(this))" onmouseleave="myOpena($(this))" href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" onclick="improvementTableRow(' + data.rowIndex + ')"><span style="color:#CD0000; font-weight:800;">改&nbsp;&nbsp进</a>';	
                    html += '<a onmouseenter="myOpens($(this))" onmouseleave="myOpena($(this))" href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" onclick="changeTableRow(' + data.rowIndex + ')"><span style="color:#CD0000; font-weight:800;">整&nbsp;&nbsp改</a>';	
				} else{
                html += '<a style="padding-bottom:10px;" onmouseenter="myOpens($(this))" onmouseleave="myOpena($(this))" href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" onclick="confirmTableRow('+data.rowIndex+')">通&nbsp;&nbsp;过</a>';
				html += '<a style="padding-bottom:10px;" onmouseenter="myOpens($(this))" onmouseleave="myOpena($(this))" href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" onclick="improvementTableRow(' + data.rowIndex + ')">改&nbsp;&nbsp;进</a>';               
				html += '<a onmouseenter="myOpens($(this))" onmouseleave="myOpena($(this))" href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" onclick="changeTableRow(' + data.rowIndex + ')">整&nbsp;&nbsp;改</a>';
				}				
                html += '</span>'
                return html;
            }
        },   
        {
            "targets": [3],//第几列
            "data": function (row) {
                return row;
            },
            "title": "类别",//表头名
            "render": function (data) {
                // alert(!data.remarks);
                if(!data.remarks){
                        if (data.readFlag == "1") {
                            return '<a style="color:blue;"  onclick="myOpenByRemarks(\'查看备注\',\'' + data.remarks + '\')" href="javascript:void(0);"><span style="color:#6B8E23; font-weight:800;">' + data.questionClassName + '</span></a>';
                        } else if(data.readFlag == "2"){
                            return '<a style="color:blue;"  onclick="myOpenByRemarks(\'查看备注\',\'' + data.remarks + '\')" href="javascript:void(0);"><span style="color:#CD0000; font-weight:800;">' + data.questionClassName + '</span></a>';                  
                        } else{
                            return '<span>' + data.questionClassName + '</span>';
                        }
                }else{
                    if (data.readFlag == "1") {
                        return '<a style="color:blue;"  onclick="myOpenByRemarks(\'查看备注\',\'' + data.remarks + '\')" href="javascript:void(0);"><span style="color:#6B8E23; font-weight:800;">' + data.questionClassName + '</span></a>';
                    } else if(data.readFlag == "2"){
                        return '<a style="color:blue;"  onclick="myOpenByRemarks(\'查看备注\',\'' + data.remarks + '\')" href="javascript:void(0);"><span style="color:#CD0000; font-weight:800;">' + data.questionClassName + '</span></a>';                  
                    } else{
                        return '<a style="color:blue;"  onclick="myOpenByRemarks(\'查看备注\',\'' + data.remarks + '\')" href="javascript:void(0);">' + data.questionClassName + '</a>';
                    }
                }
            }
        },              	       	
        {
            "targets": [4],//第几列
            "data": function (row) {
                return row;
            },
            "title": "检查项",//表头名
            "render": function (data) {
                if (data.readFlag == "1") {
                     return '<span style="color:#6B8E23; font-weight:800;">' + data.questionCheckItemName + '</span>';
                } else if(data.readFlag == "2"){
                    return '<span style="color:#CD0000; font-weight:800;">' + data.questionCheckItemName + '</span>';
                }else{
					 return data.questionCheckItemName;
				}
            }
        },
        {
            "targets": [5],//第几列
            "data": function (row) {
                return row;
            },
            "title": "部门",//表头名
            "render": function (data) {
                if (data.readFlag == "1") {
                     return '<span style="color:#6B8E23; font-weight:800;">' + data.departmentName + '</span>';
                }  else if(data.readFlag == "2"){
                    return '<span style="color:#CD0000; font-weight:800;">' + data.departmentName + '</span>';
                }else{
					 return data.departmentName;
				}
            }
        }		
    ]
});
function myOpenByRemarks(title,id){
    remarksLayer.open({
        remarks:id
    })
    $('#remarksLayer .btn').hide();    
}
function myOpens(aa){
    aa.css('backgroundColor','#7EC0EE');

}
function myOpena(li){
    li.css('backgroundColor','white');

}
//
var remarksLayer = $('#remarksLayer').detailLayer({
    layerArea:['30%', '30%'],    
    layerTitle:['备注'],    
    conditions: [
        {
            type: "textarea",
            name: "remarks", 
            label: "备注",
            placeholder: '无',
            immutableAdd:true
        }
    ]
})
var detailForm = $('#detailForm').detailLayer({
    conditions: [
				{
					type: "select",
					name: "projectId",
					label: "项目",
					search: true,
					selectList: [//当类型为select时，option配置
					 ],
					ajax: {
					api: "getZjQuestionPorjectAllList",
					valueName: "departmentId",
					textName: "departmentName"
				},
					must: true,
			 },	
        {
            type: "select",
            name: "title",
            label: "主题",
			search: true,			
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getZjXmQuestionTitleAndQueSelectAllList",
                child: "departmentId",
                valueName: "titleId",
                textName: "title",
                childrenName: "zjXmQuestionTitleList",
            },
            must: true
        },
        {
            type: "select",
            name: "departmentId",
            label: "检查部门",
			search: true,			
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                parent: "title",
                valueName: "titleId",
                textName: "title",
            },
            must: true
        },			 
			//  {
			// 		type: "select",
			// 		name: "title",
			// 		label: "主题",
			// 		search: true,					
			// 		selectList: [//当类型为select时，option配置
			// 		 ],
			// 		ajax: {
			// 		api: "getZjXmQuestionTitleSelectAllList",
			// 		valueName: "titleId",
			// 		textName: "title"
			// 	},
			// 		must: true,
			//  },	
        {
            type: 'date',//
            name: "date",//
            label: "日期",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "dateOfQuestion",
            must: true 
        }
		
    ],
    onSave: function (obj, _params) {
        l.ajax("addZjXmQuestionInformation", _params, function (data, message, success) {
            if (success) {
                //alert("hhhhh");
                informationId = data.informationId;
                project = data.departmentId;
                title = data.title;
                departmentId = _params.departmentId;    
                layer.alert(message, { icon: 1, }, function (index) {
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

})

function improvementTableRow(rowIndex){
    var checkedData=table.row(rowIndex).data();
	if(checkedData.readFlag == "2"){
	  layer.alert("已经整改过了！", { icon: 0 }, function (index) {     		 
		    layer.close(index);
         }) 	
	}else{
            detailLayer.open({ isAdd: true, 
                               screenId: checkedData.screenId,
                               informationId: informationId,
                               departmentId:checkedData.departmentId,
                               questionId:checkedData.questionClassId,
                               questionClassId:checkedData.questionCheckItemId,
                               questionDescribe:checkedData.questionDescribe,
                               projectId: project,
                               questionTitle:title,
                               flag:"1"
                            })
	}      
}

function changeTableRow(rowIndex){
    var checkedData=table.row(rowIndex).data();
    //console.log(checkedData);
	if(checkedData.readFlag == "2"){
	  layer.alert("已经整改过了！", { icon: 0 }, function (index) {     		 
		    layer.close(index);
         }) 	
	}else{
            detailLayer.open({ isAdd: true, 
                               screenId: checkedData.screenId,
                               informationId: informationId,
                               departmentId:checkedData.departmentId,
                               questionId:checkedData.questionClassId,
                               questionClassId:checkedData.questionCheckItemId,
                               questionDescribe:checkedData.questionDescribe,
                               projectId: project,
                               questionTitle:title,
                               flag:"0"
                            })
	}      
}

function confirmTableRow(rowIndex){
    var checkedData=table.row(rowIndex).data();
	if(checkedData.readFlag == "1"){
	  layer.alert("已经确认过了！", { icon: 0 }, function (index) {     		 
		    layer.close(index);
         }) 	
	} else if(checkedData.readFlag == "2"){
        layer.alert("已经整改过了！", { icon: 0 }, function (index) {     		 
		    layer.close(index);
         }) 	        
    }else{
		var param = {};
		param.checkDepartmentId = checkedData.departmentId;
		param.checkDepartmentName =  checkedData.departmentName;
		param.checkQuestionCheckItemId =  checkedData.questionCheckItemId;
		param.checkQuestionCheckItemName =  checkedData.questionCheckItemName;
		param.checkQuestionClassId =  checkedData.questionClassId;
		param.checkQuestionClassName =  checkedData.questionClassName;
		param.checkQuestionTitleId =  checkedData.questionTitleId;
		param.checkTitleId =  checkedData.questionTitleId;
		param.checkProjectId =  project;
		  l.ajax("addQuestionCheckQuestion", param, function () {
			 layer.alert("操作成功！", { icon: 1 }, function (index) {     
				pager.page('remote');			 
				layer.close(index);
			 }) 		  
		  }) 		
	}      
}
//关闭按钮触发事件
detailForm.close = function () {
	parent.pager.page('remote')
    parent.layer.close(parent.myOpenLayer)
}
loadPage()
var pager;
var form;
form = $('#form').filterfrom({
	maxLength:3,
    conditions: [//表单项配置
      	// 	{
        //     type: "text",//
        //     name: "departmentName",//
        //     label: "部门",//
		// 	placeholder: "请输入部门",
        // },
        {
            type: "text",//
            name: "questionClassName",//
            label: "类别",//
			placeholder: "请输入部门",
        },
        {
            type: "text",//
            name: "questionCheckItemName",//
            label: "检查项",//
			placeholder: "请输入部门",
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
        // console.log('搜索参数是：',_params)
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
pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjQueApprovalListByInformationId"),
        params: {
            questionTitle: title,
			informationId:informationId,
			project:project,
            // departmentName:"",
            departmentId:departmentId
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
function loadPage() {
    if (informationId) {
        l.ajax("getZjXmQuestionInformationDetail", { informationId: informationId }, function (data, message, success) {
            if (success) {
                detailForm.setOpenData(data)
            }
        });
        l.ajax("getZjQuestionPersonnelAllListByProject", { project: project }, function (data, message, success) {
            if (success) {
                detailLayer = detailLayera(data);
            }
        });	
		var _params = {};
		_params.questionTitle = title
		_params.informationId = informationId;
        _params.project = project;
		_params.departmentId = departmentId;        
        pager.page('remote', _params)
    } else {
        detailForm.setOpenData({ memberList: { oaMemberList: [] } })
    }
}
var detailLayer
function detailLayera(data){
	//console.log(data);
	var datas = [{value: "",//输入字段的值
                 text: "请选择",//显示中文名
                 selected: true//默认是否选中
				}];
	if(data.length){
		for(var i = 0; i< data.length; i++){
			datas.push({value:data[i].userid+"-"+data[i].name+"-"+data[i].orgId,
			text:data[i].name,
			selected: false})
		}
	}		
	//console.log(data)
	return detailLayer = $('#detailLayer').detailLayer({
		layerArea:['70%', '90%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "informationId",//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "departmentId",//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "screenId",//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "questionId",//输入字段名
        },        
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "questionClassId",//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "projectId",//输入字段名
        },	
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "questionTitle",//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "flag",//输入字段名
        },        	                		
        {
            type: "textarea",
            name: "questionDescribe", 
            label: "问题描述",
            placeholder: '请输入',
            must: true
        },		
        {
            type: "upload",//
            name: "imageListForPc",//
            label: "问题附件",//
            btnName: "添加",
            projectName: "zj_question_picture",
            uploadUrl: 'uploadFilesByFileName',			
            //fileType: ["jpg", "jpeg", "png", "gif"]
        },
		{
			type: "select",
            name: "personInChargeId",
            search: true,		
			label: "整改人员",
			selectList: datas,
			must: true,
		},			
        {
            type: 'date',//
            name: "answerClosingDate",//
            label: "回答截止日期",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "answerClosingDate",
            //immutableAdd:true
        }
    ],
    onAdd: function (obj, _params) {
        if(_params.flag == "1"){
            l.ajax("addZjQueImprovementAddInformationId", _params, function (data, message, success) {
                if (success) {
                    pager.page('remote', { title: title })
                    layer.alert(message, { icon: 1, }, function (index) {
                        layer.close(index);
                        obj.close()
                    });
                }
            })
        }else{
            l.ajax("addZjQueRectificationAddInformationId", _params, function (data, message, success) {
                if (success) {
                    pager.page('remote', { title: title })
                    layer.alert(message, { icon: 1, }, function (index) {
                        layer.close(index);
                        obj.close()
                    });
                }
            })
        }
    },
})
}
 
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            if (informationId){
				if (checkedData.length == 1) {
                detailLayer.open({ isAdd: true, informationId: informationId,
				                   departmentId:checkedData[0].departmentId,
								   questionId:checkedData[0].questionClassId,
								   questionClassId:checkedData[0].questionCheckItemId,
                                   questionDescribe:checkedData[0].questionDescribe,
                                   questionTitle:title
								   })
            } else if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.alert("只能选择一个", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
            } else {
                layer.alert("请先切换到基本信息完善必填信息点击确认后再新增问题！", { icon: 0, }, function (index) {
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
		parent.pager.page('remote')		
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