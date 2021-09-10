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
	            	   "data": function (row) {
	            		   return row
	            	   },
	            	   "title": "季度目标",//表头名
	            	   "defaultContent": "-",//默认显示
	            	   "render": function (data) {
	            		   var a;				
	            		   if (data) {                   
	            			   a = '<a style="color:blue;"  href="javascript:void(0);" onclick="myOpen1(\' ' + data.rowIndex + '\',\'' + 'xq' + '\')" href="javascript:void(0);">' + data.supMatter + '</a>';

	            		   }
	            		   return a;
	            	   }			
	               },		
	               {
	            	   "targets": [3],//第几列
	            	   "data": "scheduleTime",//接口对应字段
	            	   "title": "计划实施时间",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [4],//第几列
	            	   "data": "principal",//接口对应字段
	            	   "title": "责任人",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
				   {
	            	   "targets": [5],//第几列
	            	   "data": "principal",//接口对应字段
	            	   "title": "完成情况",//表头名
	            	   "defaultContent": "-",//默认显示
	               }
	               ]
});



var allData;
//检查项
var pager = $("#pager").page({
	remote: {//ajax请求配置
		url: l.getApiUrl("getZjJdjhYearSupMatterList"),
		params: {
			yearSupPlanId: recordid,
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
var detailLayer = $('#detailLayer').detailLayer({
	layerArea:['100%', '100%'],
	conditions: [
	             {
	            	 type: "hidden",//五种形式：text,select,date,hidden,textarea,
	            	 name: "typeFlag",//输入字段名
	            	 defaultValue:"1",
	             },
	             {
	            	 type: "hidden",//五种形式：text,select,date,hidden,textarea,
	            	 name: "yearSupPlanId",//输入字段名
	             },
	             {
	            	 type: "hidden",//五种形式：text,select,date,hidden,textarea,
	            	 name: "yearSupMatterId",//输入字段名
	             }, 		
	             {
	            	 type: "textarea",//
	            	 name: "supMatter",//
	            	 label: "监督事项",//
	            	 placeholder: "请输入监督事项",
	            	 immutableAdd: true 
	             },
	             {
	            	 type: "text",//
	            	 name: "scheduleTime",//
	            	 label: "计划实施时间",//
	            	 placeholder: "请输入计划实施时间",
	            	 immutableAdd: true 
	             },
	             {
	            	 type: "text",//
	            	 name: "principal",//
	            	 label: "责任人",//
	            	 placeholder: "请输入",
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
	            	 type: "select",
	            	 name: "replayYear",
	            	 label: "选择回复的季度",
	            	 selectList: [//当类型为select时，option配置  0:全公司  1：机关  2：项目
	            	              {
	            	            	  value: "",
	            	            	  text: "请选择"
	            	              },
	            	              {
	            	            	  value: "0",
	            	            	  text: "第一季度"
	            	              },
	            	              {
	            	            	  value: "1",
	            	            	  text: "第二季度"
	            	              },
	            	              {
	            	            	  value: "2",
	            	            	  text: "第三季度"
	            	              },
	            	              {
	            	            	  value: "3",
	            	            	  text: "第四季度"
	            	              }		    	                	            
	            	              ],
	            	              must: true
	             },  
	             {
	            	 type: "textarea",//
	            	 name: "planCompletionCondition",//
	            	 label: "计划完成情况",//
	            	 placeholder: "请输入计划完成情况",
					 must: true
	             },	             	
	             {
	            	 type: "upload",//
	            	 name: "fileList",//
	            	 label: "监督工作结果",//
	            	 btnName: "附件",
	            	 projectName: "zj-work-check",
	            	 //uploadUrl:"uploadFilesByFileName",
	            	 fileType: ["jpg", "jpeg", "png", "gif"]
	             }
	             ],
	             onSave: function (obj, _params) {
	            	 l.ajax("addZjJdjhSupPlanExecutive", _params, function (data, message, success) {
	            		 if (success) {
	            			 pager.page('remote', { yearSupPlanId: recordid })
	            			 layer.alert(message, { icon: 0, }, function (index) {
	            				 layer.close(index);
	            				 obj.close()
	            			 });
	            		 }
	            	 })
	             },
	             onAdd: function (obj, _params) {
	            	 l.ajax("addZjJdjhSupPlanExecutive", _params, function (data, message, success) {
	            		 if (success) {
	            			 pager.page('remote', { yearSupPlanId: recordid })
	            			 layer.alert(message, { icon: 0, }, function (index) {
	            				 layer.close(index);
	            				 obj.close()
	            			 });
	            		 }
	            	 })
	             },
})

var exeDetailLayer = $('#exeDetailLayer').detailLayer({
	layerArea:['100%', '100%'],
	conditions: [
	            {
	            	 type: "hidden",//五种形式：text,select,date,hidden,textarea,
	            	 name: "typeFlag",//输入字段名
	            	 defaultValue:"1",
	             },
	             {
	            	 type: "hidden",//五种形式：text,select,date,hidden,textarea,
	            	 name: "yearSupPlanId",//输入字段名
	             },
	             {
	            	 type: "hidden",//五种形式：text,select,date,hidden,textarea,
	            	 name: "yearSupMatterId",//输入字段名
	             }, 		
	             {
	            	 type: "textarea",//
	            	 name: "supMatter",//
	            	 label: "监督事项",//
	            	 placeholder: "请输入监督事项",
	            	 immutableAdd: true 
	             },
	             {
	            	 type: "text",//
	            	 name: "scheduleTime",//
	            	 label: "计划实施时间",//
	            	 placeholder: "请输入计划实施时间",
	            	 immutableAdd: true 
	             },
	             {
	            	 type: "text",//
	            	 name: "principal",//
	            	 label: "责任人",//
	            	 placeholder: "请输入",
	            	 immutableAdd: true 
	             },
	             {
	            	 type: "textarea",//
	            	 name: "oneTargets",//
	            	 label: "第一季度工作目标",//
	            	 placeholder: "请输入",
	            	 immutableAdd: true 
	             },
	             {
	            	 type: "textarea",//
	            	 name: "oneContent",//
	            	 label: "第一季度执行情况",//
	            	 placeholder: "请输入",
	            	 immutableAdd: true 
	             },				 
	             {
	            	 type: "textarea",//
	            	 name: "twoTargets",//
	            	 label: "第二季度工作目标",//
	            	 placeholder: "请输入",
	            	 immutableAdd: true 
	             },
	             {
	            	 type: "textarea",//
	            	 name: "twoContent",//
	            	 label: "第二季度执行情况",//
	            	 placeholder: "请输入",
	            	 immutableAdd: true 
	             },	
	             {
	            	 type: "textarea",//
	            	 name: "threeTargets",//
	            	 label: "第三季度工作目标",//
	            	 placeholder: "请输入",
	            	 immutableAdd: true 
	             },
	             {
	            	 type: "textarea",//
	            	 name: "threeContent",//
	            	 label: "第三季度执行情况",//
	            	 placeholder: "请输入",
	            	 immutableAdd: true 
	             },	
	             {
	            	 type: "textarea",//
	            	 name: "fourTargets",//
	            	 label: "第四季度工作目标",//
	            	 placeholder: "请输入",
	            	 immutableAdd: true 
	             },
	             {
	            	 type: "textarea",//
	            	 name: "fourContent",//
	            	 label: "第四季度执行情况",//
	            	 placeholder: "请输入",
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
	            	 label: "监督工作结果",//
	            	 btnName: "附件",
	            	 projectName: "zj-work-check",
	            	 fileType: ["jpg", "jpeg", "png", "gif"]
	             }
	             ],
})

var addNewDetailLayer = $('#addNewDetailLayer').detailLayer({
	layerArea:['100%', '100%'],
	conditions: [	            
	             {
	            	 type: "hidden",//五种形式：text,select,date,hidden,textarea,
	            	 name: "yearSupPlanId",//输入字段名
	            	 defaultValue:l.getUrlParam('id')
	             },
	             {
	            	 type: "hidden",//五种形式：text,select,date,hidden,textarea,
	            	 name: "yearSupMatterId",//输入字段名					
	             }, 		
	             {
	            	 type: "textarea",//
	            	 name: "supMatter",//
	            	 label: "监督事项",//
	            	 placeholder: "请输入监督事项",
	             },
	             {
	            	 type: "text",//
	            	 name: "scheduleTime",//
	            	 label: "计划实施时间",//
	            	 placeholder: "请输入计划实施时间",
	             },
	             {
	            	 type: "text",//
	            	 name: "principal",//
	            	 label: "责任人",//
	            	 placeholder: "请输入",
	             },
	             {
	            	 type: "textarea",//
	            	 name: "oneTargets",//
	            	 label: "第一季度工作目标",//
	            	 placeholder: "请输入",
	             },
	             {
	            	 type: "textarea",//
	            	 name: "twoTargets",//
	            	 label: "第二季度工作目标",//
	            	 placeholder: "请输入",
	             },
	             {
	            	 type: "textarea",//
	            	 name: "threeTargets",//
	            	 label: "第三季度工作目标",//
	            	 placeholder: "请输入",
	             },
	             {
	            	 type: "textarea",//
	            	 name: "fourTargets",//
	            	 label: "第四季度工作目标",//
	            	 placeholder: "请输入",
	             },
	             {
	            	 type: "textarea",//
	            	 name: "remarks",//
	            	 label: "备注",//
	            	 placeholder: "请输入备注",
	             }	             
	             ],
	             onSave: function (obj, _params) {

	             },
	             onAdd: function (obj, _params) {
	            	 l.ajax("addZjJdjhYearSupMatter", _params, function (data, message, success) {
	            		 if (success) {
	            			 pager.page('remote', { yearSupPlanId: recordid })
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
	case "reply":
		if (checkedData.length == 1) {
			if(checkedData[0].state =='1'){
				layer.alert("已经回复了", { icon: 0, }, function (index) {
					layer.close(index);
				});
			}else{
				detailLayer.open(checkedData[0])
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
	case "close":
		parent.layer.close(parent.myOpenLayer)
		break;
	case "addNew":
		//detailLayer.open();
		addNewDetailLayer.open();
		break;       

	}
})

function myOpen1(index, type) {
	var rowData = allData[Number(index)];
	var params;
	switch (type) {
	case 'xq':
		params = {};
		params.yearSupMatterId = rowData.yearSupMatterId;
		l.ajax('getZjJdjhYearSupMatterDetail', params, function (res) {
			exeDetailLayer.open(res);
			$('#exeDetailLayer .btn').hide();
		})
		break;
	}
}

$("#tab-system").Huitab({
	index: 0
});
