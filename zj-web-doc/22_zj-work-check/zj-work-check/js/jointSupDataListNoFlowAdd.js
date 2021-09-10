var recordid = l.getUrlParam('id') || "";
console.log(recordid)
$("#tab-system").Huitab({index: 0});
var detailForm = $('#detailForm').detailLayer({
	conditions: [
	             {
	            	 type: "hidden",//
	            	 name: "applyId",//
	            	 label: "主键id",//
	            	 placeholder: "",
	             },
	             {
	            	 type: "hidden",//
	            	 name: "flowLaunchFlag",//
	            	 label: "是否是流程发起",//0:是   1:不是
	            	 defaultValue:"1",
	            	 must: true
	             },	            
	             {
	            	 type: "text",//
	            	 name: "title",//
	            	 label: "标题",//
	            	 placeholder: "请输入标题",
	            	 must: true
	             },
	             {
	            	 type: "pickTree",//
	            	 name: "oaApplyDep",//接口字段名
	            	 label: "主责部门",//
	            	 must: true,
	            	 pickType: {
	            		 department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
	            		 member: false,//成员列表对应接口字段名,false表示不开启该类
	            	 }
	             },
	             {
	            	 type: "pickTree",//
	            	 name: "oaNeedDep",//接口字段名
	            	 label: "配合的部门",//
	            	 pickType: {
	            		 department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
	            		 member: false,//成员列表对应接口字段名,false表示不开启该类
	            	 }
	             },	
				 {
		    	                	type: "pickTree",//
		    	                	name: "oaCheckObject",//接口字段名
		    	                	label: "检查对象",//
		    	                	must: true,
		    	                	pickType: {
		    	                		department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
		    	                		member: false,//成员列表对应接口字段名,false表示不开启该类
		    	                	}
		    	                },
	             {
	            	 type: "text",//
	            	 name: "informant",//
	            	 label: "填报人",//
	            	 placeholder: "请输入填报人",
	            	 must: true
	             },
	             {
	            	 type: "date",//
	            	 name: "informantTime",//
	            	 label: "填报时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id:"informantTime"
	             }
	             ],
	             onSave: function (obj, _params) {				 
	            	 if(_params.applyId){
	            		 l.ajax("updateZjLhddJointSupApplyNoFlow", _params, function (data, message, success) {
	            			 if (success) {
	            				 layer.alert(message, { icon: 0, }, function (index) {
	            					 parent.pager.page('remote')
	            					 layer.close(index);
	            					 $("#tab-system").Huitab({ index: 1});
	            				 });
	            			 }
	            		 })

	            	 }else{
	            		 l.ajax("addZjLhddJointSupApply", _params, function (data, message, success) {
	            			 if (success) {
	            				 recordid = data.applyId
	            				 pager.page('remote', { applyId: recordid })
	            				 layer.alert(message, { icon: 0, }, function (index) {
	            					 layer.close(index);
	            					 $("#tab-system").Huitab({index: 1});
	            				 });
	            			 }
	            		 })				 
	            	 }	            		 
	             }
})

detailForm.close = function () {
	parent.layer.close(parent.myOpenLayer)
}
//详情
l.ajax("getZjLhddJointSupApplyDetailsByWorkId", { applyId: recordid }, function (data, message, success) {
	var initTimer = setInterval(function () {
		if (detailForm.isAjaxInit()) {
			clearInterval(initTimer)
			detailForm.setOpenData(data) 
		}
	}, 1)
})

//联合督导任务分配
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
	            	   "data": "checkItem",//接口对应字段
	            	   "title": "检查项",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [3],//第几列
	            	   "data": "checkRequir",//接口对应字段
	            	   "title": "检查要点要求",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [4],//第几列
	            	   "data": "responCoopDep",//接口对应字段
	            	   "title": "负责（配合）部门",//表头名
	            	   "defaultContent": "-",//默认显示
	               },      
	               {
	            	   "targets": [5],//第几列
	            	   "data": "depContactPerName",//接口对应字段
	            	   "title": "各部们联络人",//表头名
	            	   "defaultContent": "-",//默认显示
	               }      
	               ]
});

var allData;
var pager = $("#pager").page({
	remote: {//ajax请求配置
		url: l.getApiUrl("getZjLhddJointSupTaskList"),
		params: {
			applyId:recordid
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
	            	 name: "taskId",//输入字段名
	             },
	             {
	            	 type: "hidden",//五种形式：text,select,date,hidden,textarea,
	            	 name: "applyId",//输入字段名
	             },
	             {
	            	 type: "text",//五种形式：text,select,date,hidden,textarea,
	            	 name: "checkItem",//输入字段名
	            	 label: "检查项",
	            	 must: true
	             },
	             {
	            	 type: "text",
	            	 name: "checkRequir",
	            	 label: "检查要点要求",
	            	 must: true
	             },
	             {
	            	 type: "text",
	            	 name: "responCoopDep",
	            	 label: "负责（配合）部门",
	            	 must: true
	             },
	             {
	            	 type: "pickTree",
	            	 name: "oaDepContactPer",
	            	 label: "各部们联络人",
	            	 must: true,
	            	 pickType: {
	            		 department: false,//部门列表对应接口字段名,false表示不开启该类
	            		 member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
	            	 }
	             }
	             ],
	             onSave: function (obj, _params) {
	            	 l.ajax("updateZjLhddJointSupTask", _params, function (data, message, success) {
	            		 if (success) {
	            			 pager.page('remote', { applyId: recordid })
	            			 layer.alert(message, { icon: 0, }, function (index) {
	            				 layer.close(index);
	            				 obj.close()
	            			 });
	            		 }
	            	 })
	             },
	             onAdd: function (obj, _params) {
	            	 l.ajax("addZjLhddJointSupTask", _params, function (data, message, success) {
	            		 if (success) {
	            			 pager.page('remote', { applyId: recordid })
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
			detailLayer.open({ isAdd: true, applyId: recordid })
		} else {
			layer.alert("请先切换到基本信息完善必填信息点击确认后再新增任务分配！", { icon: 0, }, function (index) {
				layer.close(index);
				$("#tab-system").Huitab({index: 0});
			});
		}
		break;
	case "edit":
		if (checkedData.length == 1) {
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
			layer.alert("未选择任何项", { icon: 0, }, function (index) {
				layer.close(index);
			});
		}else {
			layer.confirm("确定删除？", { icon: 0, }, function (index) {
				l.ajax("batchDeleteUpdateZjLhddJointSupTask", checkedData, function () {
					pager.page('remote')
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



//检查结果列表
var table2 = $('#table2').DataTable({
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
	            	   "title": 'No.',
	            	   "width": 25,
	            	   "render": function (data) {
	            		   return data + 1
	            	   }
	               },       
	               {
	            	   "targets": [1],//第几列
	            	   "data": "checkDetail",//接口对应字段
	            	   "title": "检查项明细",//表头名
	            	   "defaultContent": "-",//默认显示
	               },		
	               {
	            	   "targets": [2],//第几列
	            	   "data": "requirInfo",//接口对应字段
	            	   "title": "所需资料说明",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [3],//第几列
	            	   "data": "replyContent",//接口对应字段
	            	   "title": "回复内容",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [4],//第几列
	            	   "data": "replyFileList",//接口对应字段
	            	   "title": "回复附件",//表头名
	            	   "defaultContent": "-",//默认显示
	            	   "render": function (data) {
	            		   if(data !=null){
	            			   var domain = window.lny.domain;
	            			   var dom = '';	
	            			   for (var i = 0; i < data.length; i++) {				   
	            				   var fileUrl =data[i].fileUrl,
	            				   fileName = data[i].fileName; 
	            				   dom += '<li style="list-style:none;">';								   
	            				   dom += '<img style="width:40px;" src=' + fileUrl + '/>'; 
	            				   dom += '</li>'; 	   
	            			   }						   
	            			   return dom;
	            		   }
	            	   }
	               },
	               {
	            	   "targets": [5],//第几列
	            	   "data": "replyName",//接口对应字段
	            	   "title": "回复者",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [6],//第几列
	            	   "data": "replyTime",//接口对应字段
	            	   "title": "回复时间",//表头名
	            	   "defaultContent": "-",//默认显示
	            	   "render": function (data) {
	            		   return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
	            	   }
	               }
	               ]
});

var pager2 = $("#pager2").page({
	remote: {//ajax请求配置
		url: l.getApiUrl("getZjLhddJointSupCheckDetailList"),
		params: {
			taskId: l.getUrlParam("id")
		},
		success: function (result) {
			if (result.success) {
				var data = result.data;
				$.each(data, function (index, item) {
					item.rowIndex = index
				})
				table2.clear().rows.add(data).draw();
			} else {
				layer.alert(result.message, { icon: 0, }, function (index) {
					layer.close(index);
				});
			}
		},
	}
});


