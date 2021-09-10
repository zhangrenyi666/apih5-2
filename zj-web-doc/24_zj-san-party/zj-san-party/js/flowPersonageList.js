var exportFlag = l.getUrlParam("id")
var apih5FlowStatus = l.getUrlParam("apih5FlowStatus")
var apih5FlowStatusFlag = l.getUrlParam("apih5FlowStatusFlag")
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
	            	   "data": "perName",//接口对应字段
	            	   "title": "申请人",//表头名
	            	   "defaultContent": "-",//默认显示			
	               },	
	               {
	            	   "targets": [3],//第几列
	            	   "data": "proNameNew",//接口对应字段
	            	   "title": "项目名称",//表头名
	            	   "defaultContent": "-",//默认显示			
	               },
	               {
	            	   "targets": [4],//第几列
	            	   "data": "duty",//接口对应字段
	            	   "title": "职务",//表头名
	            	   "defaultContent": "-",//默认显示			
	               },	              
	               {
	            	   "targets": [5],//第几列
	            	   "data": "gradeTypeName",//接口对应字段
	            	   "title": "个人奖励评分名称",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [6],//第几列
	            	   "data": "className",//接口对应字段
	            	   "title": "类别",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [7],//第几列
	            	   "data": "scopeName",//接口对应字段
	            	   "title": "奖励范围",//表头名
	            	   "defaultContent": "-",//默认显示
	               },				   
	               {
	            	   "targets": [8],//第几列
	            	   "data": "score",//接口对应字段
	            	   "title": "积分",//表头名
	            	   "defaultContent": "-",//默认显示	            	   
	               },
	               {
	            	   "targets": [9],//第几列
	            	   "data": "content",//接口对应字段
	            	   "title": "内容",//表头名
	            	   "defaultContent": "-",//默认显示	
					   "render":function(data,index,row){
	            		   var a;
	            		   if(data){   
	            			   a = '<a style="color:blue;" onclick="myOpen1(\' '+row.rowIndex+ '\',\'' + 'xq' + '\')" href="javascript:void(0);">'+data+'</a>'
	            		   }
	            		   return a;
	            	   }
	               },
				   {
	            	   "targets": [10],//第几列
	            	   "data": "opinionField10",//接口对应字段
	            	   "title": "审核人",//表头名
	            	   "defaultContent": "-",//默认显示
	               },	
	               {
	            	   "targets": [11],//第几列
	            	   "data": "apih5FlowStatus",//接口对应字段
	            	   "title": "状态",//表头名
	            	   "defaultContent": "-",//默认显示
	            	   "render": function (data) {
	            		   var text = ""
	            			   switch (data) {
	            			   case "0": text = "待提交";
	            			   break;
	            			   case "1": text = "审批中";
	            			   break;
	            			   case "2": text = "审批完成";
	            			   break;
							   case "3": text = "退回";
	            			   break;
	            			   default: text = "未知";
	            			   break;
	            			   }
	            		   return text
	            	   }
	               },
	               {
	            	   "targets": [12],//第几列
	            	   "data": "date",//接口对应字段
	            	   "title": "时间",//表头名
	            	   "defaultContent": "-",//默认显示
	            	   "render": function (data) {
	            		   return l.dateFormat(new Date(data), "yyyy-MM-dd");
	            	   }
	               },				    
				   {
			         "targets": [13],//第几列
			         "width": 80,
                     "data": function (row) {
                       return row
                      },//接口对应字段
			         "title": "操作历史",//表头名
			         "render": function (data) {
			          var html = '';
				          html += '<span class="dropDown">'
				          html += '<a style="color:blue;"  href="javascript:void(0);" onclick="myOpen1(\' ' + data.rowIndex + '\',\'' + 'flowDetail' + '\')" href="javascript:void(0);">操作历史</a>';
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
            name: "proName",//输入字段名
            label: "项目名称",//输入字段对杨的中文名称
            placeholder: "请输入项目名称",
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "personRegisterId",//输入字段名
            label: "申请人",//输入字段对杨的中文名称
            placeholder: "请输入申请人",
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
var allData;
var pager = $("#pager").page({
	remote: {//ajax请求配置
		url: l.getApiUrl("getZjSanDjFlowPersonageList"),
		params: {
		apih5FlowStatus:apih5FlowStatus,
		apih5FlowStatusFlag:apih5FlowStatusFlag
		},
		success: function (result) {
			if (result.success) {
				var data = result.data;
				allData = data;				
				if (exportFlag) {
                     $("#add").hide();
                     $("#addAge").hide();
                     //$("#delete").hide();
                    
                }else{
				 $("#derive").hide();
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
	layerArea:['100%', '100%'],
	conditions: [
	             {
	            	 type: "hidden",//五种形式：text,select,date,hidden,textarea,
	            	 name: "personageId",//输入字段名
	             },
	             {
	            	 type: "text",//
	            	 name: "perName",//
	            	 label: "申请人",//
	            	 placeholder: "请输入申请人",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "text",//
	            	 name: "proNameNew",//
	            	 label: "项目名称",//
	            	 placeholder: "请输入项目名称",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "text",//
	            	 name: "duty",//
	            	 label: "职务",//
	            	 placeholder: "请输入职务",
	            	 immutableAdd: true
	             },		
	             {
	            	 type: "text",//
	            	 name: "gradeTypeName",//
	            	 label: "个人奖励名称",//
	            	 placeholder: "请输入",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "text",//
	            	 name: "className",//
	            	 label: "类别",//
	            	 placeholder: "请输入",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "text",//
	            	 name: "scopeName",//
	            	 label: "奖励范围",//
	            	 placeholder: "请输入",
	            	 immutableAdd: true
	             },	
	             {
	            	 type: "text",//
	            	 name: "score",//
	            	 label: "积分",//
	            	 placeholder: "请输入",
	            	 immutableAdd: true
	             },		    	               	                        	
	             {
	            	 type: "text",//
	            	 name: "content",//
	            	 label: "内容",//
	            	 placeholder: "请输入内容",
	            	 immutableAdd: true
	             },							  	               
	             {
	            	 type: "date",//
	            	 name: "date",//
	            	 label: "时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id: "date",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "upload",//
	            	 name: "fileList",//
	            	 label: "附件上传",//
	            	 btnName: "选择文件",
	            	 projectName: "zj-san-party",
	            	 immutableAdd: true,
	            	 maxLen: 1,
	            	 uploadUrl: 'uploadFilesByFileName',
	            	 fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]		    	       
	             }       
	             ],	            				
})


$("body").on("click", "button", function () {
	var checkedData = l.getTableCheckedData(table);
	var name = $(this).attr("data-name");
	switch (name) {
	case "add":
		myOpen('个人奖励申请流程发起','','flowPersonageApprove')
		break;
	case "addAge":
            layer.confirm("注意：工龄申请每个人只可以申请一次，确定是第一次进行工龄申请？", { icon: 0, }, function (index) {   
                layer.close(index);			
		       myOpen('工龄申请流程发起','','flowPersonageAgeApprove')
            });
		break;
	case "del":
		if (checkedData.length == 0) {
			layer.alert("未选择任何项", { icon: 0, }, function (index) {
				layer.close(index);
			});
		} else {
		    layer.confirm("确定删除，删除的数据不可恢复，请谨慎操作？", { icon: 0, }, function (index) {
                            l.ajax("batchDeleteUpdateZjSanDjFlowPersonage", checkedData, function () {
                             pager.page('remote')
                            })
                          layer.close(index);
            });
			/* for(var i=0;i<checkedData.length;i++){
				    if(checkedData[i].apih5FlowStatus != '0'){
				        layer.alert("包含已提交的审批，不能删除，请重新选择！", { icon: 0, }, function (index) {
                        layer.close(index);
                       });	
                      break;					
				    }else{
				        layer.confirm("确定删除？", { icon: 0, }, function (index) {
                            l.ajax("batchDeleteUpdateZjSanDjFlowPersonage", checkedData, function () {
                             pager.page('remote')
                            })
                          layer.close(index);
                        });
				    }
				}	 */				
		}
		break;
	case "derive"://导出
            var params = {};
            params.deriveType =exportFlag;
			params.apih5FlowStatus = apih5FlowStatus;
			params.apih5FlowStatusFlag = apih5FlowStatusFlag;
            layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
                l.ajax('exportExcelZjSanDjFlowPersonageList', params, function (res) {
                    layer.alert("导出成功！", { icon: 0 }, function (index) {
                        layer.close(index);
                        window.location.href = res;
                    })
                })
            });
            break;
	}
})

var myOpenLayer;
function myOpen(title, id, url) {
	var options = {
			type: 2,
			title: title,
			content: url + ".html?id=" + id+"&code="+code
	};
	myOpenLayer = layer.open(options);
	layer.full(myOpenLayer);
};

function myOpen1(index, type) {
	var rowData = allData[Number(index)];
	var params;
	switch (type){
	case 'xq':
		params = {};
		params.personageId = rowData.personageId;
		l.ajax('getZjSanDjFlowPersonageDetail',params,function(res){
			detailLayer.open(res);
			$('#detailLayer .btn').hide();
		})
		break;	
	case 'flowDetail':
			var options = {
				type: 2,
				title: '操作历史',
				content: "operatingHistory.html?id=" + rowData.workId + "&code=" + code
			}
			layer.full(layer.open(options))
			break;
	}
}