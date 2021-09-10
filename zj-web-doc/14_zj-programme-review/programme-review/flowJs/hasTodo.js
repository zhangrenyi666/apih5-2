var code = Apih5.getUrlParam('code');
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
	            	   "data": "title",//接口对应字段
	            	   "width": 300,
	            	   "title": "标题",//表头名
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
	            	   "width": 150,
	            	   "data": "endTime",//接口对应字段
	            	   "title": "结束时间",//表头名
	            	   "defaultContent": "-",//默认显示
	            	   "render": function (data) {
	            		   if(data == null){
	            			   return "-";
	            		   } else {
	            			   return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
	            		   }
	            	   }
	               },
	               {
	            	   "targets": [8],//第几列
	            	   "width": 100,
	            	   "data": "flowName",//接口对应字段
	            	   "title": "所属流程",//表头名
	            	   "defaultContent": "-",//默认显示
	               }
	               ]
});
var pager = $("#pager").page({
	remote: {// ajax请求配置
		url: l.getApiUrl("getHasTodoList"),
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

var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "title",//输入字段名
            label: "标题",//输入字段对杨的中文名称
            placeholder: "请输入标题",
        } ,
		{
            type: "text",//三种形式：text,select,date,
            name: "sendUserName",//输入字段名
            label: "递交者",//输入字段对杨的中文名称
            placeholder: "请输入递交者",
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
	case "word":	
		if (checkedData.length == 1) {
			if(checkedData[0].flowStatus !='结束'){
				layer.alert("还在审批中，不能进行导出", { icon: 0, }, function (index) {
					layer.close(index);
				});			
			}else{
				layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
					l.ajax('exportCropPurchaseToWord',checkedData[0],function(res){
						layer.alert("导出成功！", { icon: 0 }, function (index) {
							layer.close(index);
							window.location.href=res;
						})
					})
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
	}
})


var myOpenLayer;
function myOpen(title, data, url) {
    if(data.flowId == 'zjProLaunchFlowByOne'){//一级方案
		url = 'zjProLaunchFlowByOneProcess'
	}else if(data.flowId == 'zjProLaunchFlowByTwo'){//二级方案
		url = 'zjProLaunchFlowByTwoProcess'
	}else if(data.flowId == 'zjProLaunchFlowByThree'){//三级方案
		url = 'zjProLaunchFlowByThreeProcess'
	}else
	if(data.flowId == 'projectDepPurchasingProcess'){//项目部购置流程
		url = 'projectDepBuyprocess'
	}else if(data.flowId == 'cropPurchasingProcess'){//公司购置流程
		url = 'process'
	}else if(data.flowId == 'purchasingSystem'){//采购入库
		url = 'assetsFixCheckProcess'
	}else if(data.flowId == 'consumablesPurchase'){//消耗品采购入库领用
		url = 'assetsConsumeCheckProcess'
	}else if(data.flowId == 'projectTransfer'){//项目调拨
		url = 'assetsTranProProcess'
	}else if(data.flowId == 'cropTransfers'){//公司调拨
		url = 'assetsTranCorpProcess'
	}else if(data.flowId == 'projectScrap'){//项目报废
		url = 'assetsScrapProProcess'
	}else if(data.flowId == 'cropScrap'){//公司报废
		url = 'assetsScrapCorpProcess'
	}



	var options = {
			type: 2,
			title: title,
			content: url + ".html?workId=" + data.workId + "&trackId=" + data.trackId + "&nodeId=" + data.nodeId+"&code="+code
	}
	myOpenLayer = layer.open(options)
	layer.full(myOpenLayer)
}