var code = Apih5.getUrlParam('code');
Apih5.setCookie('code', code, 30);
var flowIdFlag = Apih5.getUrlParam('flowId'); 

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
            "data": "title",//接口对应字段
            "title": "标题",//表头名
			"width": 300,
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "sendUserName",//接口对应字段
            "title": "递交者",//表头名
            "defaultContent": "-",//默认显示
        },

        {
            "targets": [4],//第几列
            "data": "nodeName",//接口对应字段
            "title": "当前活动名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "flowStatus",//接口对应字段
            "title": "流程状态",//表头名
            "defaultContent": "-",//默认显示 
        },
        {
            "targets": [6],//第几列
            "width": 150,
            "data": "createTime",//接口对应字段
            "title": "发起时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [7],//第几列
            "width": 100,
            "data": "flowName",//接口对应字段
            "title": "所属流程",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [8],//第几列
            "width": 80,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//1：待办  2：未完成  3：延期审核中 4：完成审核中 5：已完成 6：未通过 
            "render": function (data) {	
                var html = '';
                if (data.flowId === 'sanMinister') {
                   html = '<a style="color:blue;" href="./MinisterPerson.html?code=' + code + '&workId=' + data.workId + '&nodeName='+data.nodeName+'&status='+data.status+'">明细</a>';
                } else if (data.flowId === 'sanPerson') {
                    html = '<a style="color:blue;" href="./listPerson.html?code=' + code + '&workId=' + data.workId + '&nodeName='+data.nodeName+'&status='+data.status+'">明细</a>';
                } else {
                    html = '';
                }
                return html;
            }
	    }
    ]
});

var allData;
var pager = $("#pager").page({
    remote: {// ajax请求配置
       url: l.getApiUrl("getTodoList"),
       params: {
	      //flowId: 'hwfgApply'
	     flowId: 'sanPerson,sanMinister'
		 // flowId:flowIdFlag
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
					allData = data;
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


var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "sendUserName",//输入字段名
            label: "递交者",//输入字段对杨的中文名称
            placeholder: "请输入递交者",
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "title",//输入字段名
            label: "标题",//输入字段对杨的中文名称
            placeholder: "请输入标题",
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "flowName",//输入字段名
            label: "所属流程",//输入字段对杨的中文名称
            placeholder: "请输入所属流程",
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


$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "process":
            if (checkedData.length == 1) {
                myOpen("流程处理", checkedData[0], "process")
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
		case "process1":
            if (checkedData.length == 1) {
                myOpen("流程处理", checkedData[0], "zjYyOutSealApplyProcess")
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
		case "process2":
            if (checkedData.length == 1) {
                myOpen("流程处理", checkedData[0], "zjInfoNeedsConfirmProcess")
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
		case "process3":
            if (checkedData.length == 1) {
                myOpen("流程处理", checkedData[0], "zjInfoProjectApplyProcess")
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
			  layer.confirm("删除的数据无法恢复，请谨慎操作！！！确定删除？", { icon: 0, }, function (index) {
                            l.ajax("batchDeleteUpdateZjFlowTodo", checkedData, function () {
                             pager.page('remote')
                            })
                          layer.close(index);
                        });
			 /* for(var i=0;i<checkedData.length;i++){
				    if(checkedData[i].flowStatus != '退回'){
				        layer.alert("包含未退回的审批，不能删除，请重新选择！", { icon: 0, }, function (index) {
                        layer.close(index);
                       });	
                      break;					
				    }else{
				        layer.confirm("确定删除？", { icon: 0, }, function (index) {
                            l.ajax("batchDeleteUpdateZjFlowTodo", checkedData, function () {
                             pager.page('remote')
                            })
                          layer.close(index);
                        });
				    }
				}	 */ 			
		}
		break;		
    }
})


var myOpenLayer;
function myOpen(title, data, url) {
   if(data.flowId == 'sanPerson'){//海外
      url = 'returnWorkOutProcess'
   } else if (data.flowId == 'sanMinister') {
        url = 'returnWorkOutMinisterProcess'
   }
   
    var options = {
        type: 2,
        title: title,
        content: url + ".html?workId=" + data.workId + "&trackId=" + data.trackId + "&nodeId=" + data.nodeId + "&flowId=" + data.flowId + "&title=" + data.title+ "&code="+code+"&nodeName="+data.nodeName
    }
    myOpenLayer = layer.open(options)
    layer.full(myOpenLayer)
}

function myOpen1(index, type) {
	var rowData = allData[Number(index)];
	var params = [];
	params[0]=rowData;
	switch (type) {
	case 'del6':
	  layer.confirm("删除的数据无法恢复，请谨慎操作！！！确定删除？", { icon: 0, }, function (index) {
         l.ajax("batchDeleteUpdateZjYySealApply", params, function () {
                             pager.page('remote')
                            })
                          layer.close(index);
                        });
		break;
	case 'del5':
	  layer.confirm("删除的数据无法恢复，请谨慎操作！！！确定删除？", { icon: 0, }, function (index) {
         l.ajax("batchDeleteUpdateZjFlowYySealFive", params, function () {
                             pager.page('remote')
                            })
                          layer.close(index);
                        });
		break;
		
	}
}