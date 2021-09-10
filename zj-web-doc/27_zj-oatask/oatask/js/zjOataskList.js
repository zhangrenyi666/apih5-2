var code = Lny.getUrlParam('code')
Lny.setCookie('code', code, 30)
//全部
var table0 = $('#table0').DataTable({
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
	            	   "data": "taskStateFlag",//接口对应字段
	            	   "title": "状态",//表头名
	            	   "defaultContent": "-",//默认显示
	            	   "render": function (data) {//1：待办  2：未完成  3：延期审核中 4：完成审核中 5：已完成 6：未通过
	            		   var text = ""
	            			   switch (data) {
	            			   case "1": text = "待办";
	            			   break;
	            			   case "2": text = "未完成";
	            			   break;
	            			   case "3": text = "延期审核中";
	            			   break;
	            			   case "4": text = "完成审核中";
	            			   break;
	            			   case "5": text = "已完成";
	            			   break;
	            			   case "6": text = "未通过";
	            			   break;
	            			   default: text = "未知";
	            			   break;
	            			   }
	            		   return text
	            	   } 			
	               },
	               {
	            	   "targets": [3],//第几列
	            	   "data": function (row) {
	            		   return row
	            	   },//接口对应字段
	            	   "title": "任务事项",//表头名
	            	   "defaultContent": "-",//默认显示
	            	   "render":function(data,index,row){
	            		   var a;
	            		   if(data){   
	            			   a = '<a style="color:blue;" onclick="myOpen(\' '+row.rowIndex+ '\',\'' + 'xq' + '\')" href="javascript:void(0);">'+data.taskMatter+'</a>'
	            		   }
	            		   return a;
	            	   }		
	               },
	               {
	            	   "targets": [4],//第几列
	            	   "data": function (row) {
	            		   return row
	            	   },//接口对应字段
	            	   "title": "编号",//表头名
	            	   "defaultContent": "-",//默认显示
	            	   "render": function (data) {
	            		   var text = ""					   
	            			   text=(data.redFlag==="0"?"<font color='red'>"+data.taskNumber+"</font>":data.taskNumber)                       
	            			   return text
	            	   }	
	               },
	               {
	            	   "targets": [5],//第几列
	            	   "data": "undertakeDeptName",//接口对应字段
	            	   "title": "承办部门",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [6],//第几列
	            	   "data": "leaderName",//接口对应字段
	            	   "title": "负责人",//表头名   0:全公司  1：机关  2：项目
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [7],//第几列
	            	   "data": "content",//接口对应字段
	            	   "title": "内容摘要",//表头名   0:审核中 1：审核完成  2：废弃审核中
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [8],//第几列
	            	   "data": "createTime",//接口对应字段
	            	   "title": "下发时间",//表头名
	            	   "defaultContent": "-",//默认显示
	            	   "render": function (data) {
	            		   return l.dateFormat(new Date(data), "yyyy-MM-dd");
	            	   }
	               },    
	               {
	            	   "targets": [9],//第几列
	            	   "data": "endTime",//接口对应字段
	            	   "title": "要求完成期限",//表头名
	            	   "defaultContent": "-",//默认显示
	            	   "render": function (data) {
	            		   return l.dateFormat(new Date(data), "yyyy-MM-dd");
	            	   }
	               },  	              
	               {
	            	   "targets": [10],//第几列
	            	   "width": 80,
	            	   "data": function (row) {
	            		   return row
	            	   },//接口对应字段
	            	   "title": "操作",//1：待办  2：未完成  3：延期审核中 4：完成审核中 5：已完成 6：未通过 
	            	   "render": function (data) {
	            		   var html = '';
	            		   html += '<span class="dropDown">'
	            			   html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">操作</a>';
	            		   html += '<ul class="dropDown-menu menu radius box-shadow">'						  
	            			   if(data.controlFlag =='1'){
	            				   if(data.taskStateFlag =='2'){//-------------2：未完成 的可以进行延期和完成申请操作
	            					   html += '<li><a href="javascript:void(0);" onclick="myOpen(\' ' + data.rowIndex + '\',\'' + 'delay' + '\')" href="javascript:void(0);">延期申请</a></li>';
	            					   html += '<li><a href="javascript:void(0);" onclick="myOpen(\' ' + data.rowIndex + '\',\'' + 'finish' + '\')" href="javascript:void(0);">完成申请</a></li>';
	            				   }else if(data.taskStateFlag =='6'){//-------------6：未通过  编辑重新发起
	            					   html += '<li><a href="javascript:void(0);" onclick="myOpen(\' ' + data.rowIndex + '\',\'' + 'edit' + '\')" href="javascript:void(0);">编辑</a></li>';
	            				   }else{ //1：待办  2：未完成  3：延期审核中 4：完成审核中 5：已完成 6：未通过   --1.3.4.5的状态不让操作
	            					   html = '-';
	            				   }
	            			   }else{
	            				   html = '-';
	            			   }
	            		   html += '</ul></span>'
	            			   return html;
	            	   }
	               }
	               ]
});
//1
var table1 = $('#table1').DataTable({
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
	            	   "title": "任务事项",//表头名
	            	   "defaultContent": "-",//默认显示
	            	   "render":function(data,index,row){
	            		   var a;
	            		   if(data){   
	            			   a = '<a style="color:blue;" onclick="myOpen1(\' '+row.rowIndex+ '\',\'' + 'xq' + '\')" href="javascript:void(0);">'+data.taskMatter+'</a>'
	            		   }
	            		   return a;
	            	   }		
	               },
	               {
	            	   "targets": [3],//第几列
	            	   "data": "leaderName",//接口对应字段
	            	   "title": "责任人",//表头名   0:全公司  1：机关  2：项目
	            	   "defaultContent": "-",//默认显示
	               },   
	               {
	            	   "targets": [4],//第几列
	            	   "data": "endTime",//接口对应字段
	            	   "title": "要求完成期限",//表头名
	            	   "defaultContent": "-",//默认显示
	            	   "render": function (data) {
	            		   return l.dateFormat(new Date(data), "yyyy-MM-dd");
	            	   }
	               }
	               ]
});
//2
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
	            	   "data": "leaderName",//接口对应字段
	            	   "title": "姓名",//表头名   0:全公司  1：机关  2：项目
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [3],//第几列
	            	   "data": "totalNum",//接口对应字段
	            	   "title": "任务数量",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [4],//第几列
	            	   "data": "finishedNum",//接口对应字段
	            	   "title": "已完成",//表头名
	            	   "defaultContent": "-",//默认显示
	               },	              
	               {
	            	   "targets": [5],//第几列
	            	   "data": "unfinishedNum",//接口对应字段
	            	   "title": "未完成",//表头名   0:审核中 1：审核完成  2：废弃审核中
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [6],//第几列
	            	   "data": "unoverdueNum",//接口对应字段
	            	   "title": "超期未完成",//表头名   0:审核中 1：审核完成  2：废弃审核中
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [7],//第几列
	            	   "data": "overdueNum",//接口对应字段
	            	   "title": "超期已完成",//表头名   0:审核中 1：审核完成  2：废弃审核中
	            	   "defaultContent": "-",//默认显示
	               }	 
	               ]
});
//3
var table3 = $('#table3').DataTable({
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
	            	   "data": "undertakeDeptName",//接口对应字段
	            	   "title": "部门",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [3],//第几列
	            	   "data": "leaderName",//接口对应字段
	            	   "title": "成员",//表头名
	            	   "defaultContent": "-",//默认显示
	               }	                       
	               ]
});
//4
var table4 = $('#table4').DataTable({
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
	            	   "title": "任务事项",//表头名
	            	   "defaultContent": "-",//默认显示
	            	   "render":function(data,index,row){
	            		   var a;
	            		   if(data){   
	            			   a = '<a style="color:blue;" onclick="myOpen4(\' '+row.rowIndex+ '\',\'' + 'xq' + '\')" href="javascript:void(0);">'+data.taskMatter+'</a>'
	            		   }
	            		   return a;
	            	   }		
	               },	              
	               {
	            	   "targets": [3],//第几列
	            	   "data": "endTime",//接口对应字段
	            	   "title": "要求完成期限",//表头名
	            	   "defaultContent": "-",//默认显示
	            	   "render": function (data) {
	            		   return l.dateFormat(new Date(data), "yyyy-MM-dd");
	            	   }
	               },
	               {
	            	   "targets": [4],//第几列
	            	   "data": "performance",//接口对应字段
	            	   "title": "完成情况",//表头名
	            	   "defaultContent": "-",//默认显示
	               }
	               ]
});
var form = $('#form0').filterfrom({
	conditions: [//表单项配置
	             {
	            	 type: "select",
	            	 name: "taskSourceId",
	            	 label: "任务来源",
	            	 selectList: [//当类型为select时，option配置					
	            	              {
	            	            	  value: "",
	            	            	  text: "全部"
	            	              },
	            	              {
	            	            	  value: "0",
	            	            	  text: "年度重点工作"
	            	              },
	            	              {
	            	            	  value: "2",
	            	            	  text: "日常性工作"
	            	              },
	            	              {
	            	            	  value: "3",
	            	            	  text: "其他"
	            	              }
	            	              ],
	             },
	             {
	            	 type: "text",//三种形式：text,select,date,
	            	 name: "taskMatter",//输入字段名
	            	 label: "任务事项",//输入字段对杨的中文名称
	            	 placeholder: "请输入任务事项",
	             },
	             {
	            	 type: "text",//三种形式：text,select,date,
	            	 name: "leaderName",//输入字段名
	            	 label: "负责人",//输入字段对杨的中文名称
	            	 placeholder: "请输入负责人",
	             },	
	             {
	            	 type: "select",
	            	 name: "taskStateFlag",
	            	 label: "状态",
	            	 selectList: [//当类型为select时，option配置//1：待办  2：未完成  3：延期审核中 4：完成审核中 5：已完成 6：未通过			
	            	              {
	            	            	  value: "",
	            	            	  text: "全部"
	            	              },
	            	              {
	            	            	  value: "1",
	            	            	  text: "待办"
	            	              },
	            	              {
	            	            	  value: "2",
	            	            	  text: "未完成"
	            	              },
	            	              {
	            	            	  value: "3",
	            	            	  text: "延期审核中"
	            	              },
	            	              {
	            	            	  value: "4",
	            	            	  text: "完成审核中"
	            	              },
	            	              {
	            	            	  value: "5",
	            	            	  text: "已完成"
	            	              },
	            	              {
	            	            	  value: "6",
	            	            	  text: "未通过"
	            	              }
	            	              ],
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
var allData;
//全部
var pager = $("#pager0").page({
	remote: {//ajax请求配置
		url: l.getApiUrl("getZjOataskList"),
		params: {},
		success: function (result) {
			if (result.success) {
				var data = result.data;
				allData = data;
				$.each(data, function (index, item) {
					item.rowIndex = index
				})
				table0.clear().rows.add(data).draw();
			} else {
				layer.alert(result.message, { icon: 0, }, function (index) {
					layer.close(index);
				});
			}
		},
	}
});
//1
var form1 = $('#form1').filterfrom({
	conditions: [//表单项配置
	             {
	            	 type: "text",//三种形式：text,select,date,
	            	 name: "leaderName",//输入字段名
	            	 label: "负责人",//输入字段对杨的中文名称
	            	 placeholder: "请输入负责人",
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
	            	 pager1.page('remote', 0, _params)
	             },
	             onReset: function (arr) {//重置按钮回调
	            	 var _params = {};
	            	 for (var i = 0; i < arr.length; i++) {
	            		 _params[arr[i].name] = arr[i].value;
	            	 }
	            	 pager1.page('remote', _params)
	             }
})
var allData1;
var pager1 = $("#pager1").page({
	remote: {//ajax请求配置
		url: l.getApiUrl("getZjOataskList"),
		params: {
		},
		success: function (result) {
			if (result.success) {
				var data = result.data;
				allData1 = data;
				$.each(data, function (index, item) {
					item.rowIndex = index
				})
				table1.clear().rows.add(data).draw();
			} else {
				layer.alert(result.message, { icon: 0, }, function (index) {
					layer.close(index);
				});
			}
		},
	}
});
//2
var form2 = $('#form2').filterfrom({
	conditions: [//表单项配置
	             {
	            	 type: "text",//三种形式：text,select,date,
	            	 name: "leaderName",//输入字段名
	            	 label: "负责人",//输入字段对杨的中文名称
	            	 placeholder: "请输入负责人",
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
	            	 pager2.page('remote', 0, _params)
	             },
	             onReset: function (arr) {//重置按钮回调
	            	 var _params = {};
	            	 for (var i = 0; i < arr.length; i++) {
	            		 _params[arr[i].name] = arr[i].value;
	            	 }
	            	 pager2.page('remote', _params)
	             }
})
var allData2;
var pager2 = $("#pager2").page({
	remote: {//ajax请求配置
		url: l.getApiUrl("getZjOataskDailyList"),
		params: {			
		},
		success: function (result) {
			if (result.success) {
				var data = result.data;
				allData2 = data;
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
//3
var allData3;
var pager3 = $("#pager3").page({
	remote: {//ajax请求配置
		url: l.getApiUrl("getZjOataskSelfList"),
		params: {
		},
		success: function (result) {
			if (result.success) {
				var data = result.data;
				allData3 = data;
				$.each(data, function (index, item) {
					item.rowIndex = index
				})
				table3.clear().rows.add(data).draw();
			} else {
				layer.alert(result.message, { icon: 0, }, function (index) {
					layer.close(index);
				});
			}
		},
	}
});
//4
var allData4;
var pager4 = $("#pager4").page({
	remote: {//ajax请求配置
		url: l.getApiUrl("getZjOataskList"),
		params: {
		},
		success: function (result) {
			if (result.success) {
				var data = result.data;
				allData4 = data;
				$.each(data, function (index, item) {
					item.rowIndex = index
				})
				table4.clear().rows.add(data).draw();
			} else {
				layer.alert(result.message, { icon: 0, }, function (index) {
					layer.close(index);
				});
			}
		},
	}
});
//延期操作
var delay0 = $('#delay0').detailLayer({
	layerArea:['100%', '100%'],
	conditions: [
	             {
	            	 type: "hidden",//
	            	 name: "taskId",//
	            	 label: "主键id",//
	            	 placeholder: "",
	             },
	             {
	            	 type: "hidden",//
	            	 name: "operateFlag",//
	            	 label: "主键id",//
	            	 placeholder: "",
	            	 defaultValue:"0"
	             },
	             {
	            	 type: "select",
	            	 name: "taskSourceId",
	            	 label: "任务来源",
	            	 immutableAdd: true,
	            	 selectList: [//当类型为select时，option配置					
	            	              {
	            	            	  value: "0",
	            	            	  text: "年度重点工作"
	            	              },
	            	              {
	            	            	  value: "2",
	            	            	  text: "日常性工作"
	            	              },
	            	              {
	            	            	  value: "3",
	            	            	  text: "其他"
	            	              }
	            	              ],
	             },	     
	             {
	            	 type: "text",//
	            	 name: "taskNumber",//
	            	 label: "编号",//
	            	 placeholder: "请输入编号",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "taskMatter",//
	            	 label: "任务事项",//
	            	 placeholder: "请输入任务事项",
	            	 immutableAdd: true
	             },	        
	             {
	            	 type: "textarea",//
	            	 name: "content",//
	            	 label: "内容摘要",//
	            	 placeholder: "请输入内容摘要",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "text",//
	            	 name: "undertakeDeptName",//
	            	 label: "承办部门",//
	            	 placeholder: "请输入承办部门",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "text",//
	            	 name: "leaderName",//
	            	 label: "负责人",//
	            	 placeholder: "请输入负责人",
	            	 immutableAdd: true
	             },	             	             
	             {
	            	 type: "text",//
	            	 name: "auditorName",//
	            	 label: "审核人",//
	            	 placeholder: "请输入审核人",
	            	 immutableAdd: true
	             },	 	
	             {
	            	 type: "date",//
	            	 name: "startTime",//
	            	 label: "任务期限开始时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id:"startTime",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "date",//
	            	 name: "endTime",//
	            	 label: "任务期限结束时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id:"endTime",
	            	 must: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "delayReason",//
	            	 label: "延期原因",//
	            	 placeholder: "请输入延期原因",
	            	 must: true
	             },
	             {
	            	 type: "upload",//
	            	 name: "fileList",//
	            	 label: "附件",//
	            	 btnName: "添加附件",
	            	 projectName: "zj-oatask",
	            	 uploadUrl: 'uploadFilesByFileName',
	            	 immutableAdd: true,
	            	 fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
	             }	
	             ],
	             onSave: function (obj, _params) {
	            	 l.ajax('updateZjOatask', _params, function (_params) {
	            		 pager.page('remote')
	            		 obj.close()
	            	 })
	             }   
})
//完成操作
var finish0 = $('#finish0').detailLayer({
	layerArea:['100%', '100%'],
	conditions: [
	             {
	            	 type: "hidden",//
	            	 name: "taskId",//
	            	 label: "主键id",//
	            	 placeholder: "",
	             },
	             {
	            	 type: "hidden",//
	            	 name: "operateFlag",//
	            	 label: "主键id",//
	            	 placeholder: "",
	            	 defaultValue:"1"
	             },
	             {
	            	 type: "select",
	            	 name: "taskSourceId",
	            	 label: "任务来源",
	            	 immutableAdd: true,
	            	 selectList: [//当类型为select时，option配置					
	            	              {
	            	            	  value: "0",
	            	            	  text: "年度重点工作"
	            	              },
	            	              {
	            	            	  value: "2",
	            	            	  text: "日常性工作"
	            	              },
	            	              {
	            	            	  value: "3",
	            	            	  text: "其他"
	            	              }
	            	              ],
	             },	     
	             {
	            	 type: "text",//
	            	 name: "taskNumber",//
	            	 label: "编号",//
	            	 placeholder: "请输入编号",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "taskMatter",//
	            	 label: "任务事项",//
	            	 placeholder: "请输入任务事项",
	            	 immutableAdd: true
	             },	        
	             {
	            	 type: "textarea",//
	            	 name: "content",//
	            	 label: "内容摘要",//
	            	 placeholder: "请输入内容摘要",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "text",//
	            	 name: "undertakeDeptName",//
	            	 label: "承办部门",//
	            	 placeholder: "请输入承办部门",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "text",//
	            	 name: "leaderName",//
	            	 label: "负责人",//
	            	 placeholder: "请输入负责人",
	            	 immutableAdd: true
	             },	             	             
	             {
	            	 type: "text",//
	            	 name: "auditorName",//
	            	 label: "审核人",//
	            	 placeholder: "请输入审核人",
	            	 immutableAdd: true
	             },	 	
	             {
	            	 type: "date",//
	            	 name: "startTime",//
	            	 label: "任务期限开始时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id:"startTime",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "date",//
	            	 name: "endTime",//
	            	 label: "任务期限结束时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id:"endTime",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "select",
	            	 name: "performanceState",
	            	 label: "完成状态",
	            	 selectList: [//当类型为select时，option配置//1：待办  2：未完成  3：延期审核中 4：完成审核中 5：已完成 6：未通过			
	            	              {
	            	            	  value: "0",
	            	            	  text: "完成中"
	            	              },
	            	              {
	            	            	  value: "1",
	            	            	  text: "已完成"
	            	              }
	            	              ],
	            	              must: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "performance",//
	            	 label: "完成情况",//
	            	 placeholder: "请输入完成情况",
	            	 must: true
	             },
	             {
	            	 type: "upload",//
	            	 name: "fileList",//
	            	 label: "附件",//
	            	 btnName: "添加附件",
	            	 projectName: "zj-oatask",
	            	 uploadUrl: 'uploadFilesByFileName',
	            	 immutableAdd: true,
	            	 fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
	             }	
	             ],
	             onSave: function (obj, _params) {
	            	 l.ajax('updateZjOatask', _params, function (_params) {
	            		 pager.page('remote')
	            		 obj.close()
	            	 })
	             }   
})
//编辑
var editDetailLayer = $('#editDetailLayer0').detailLayer({
	layerArea:['100%', '100%'],
	conditions: [
	             {
	            	 type: "hidden",//
	            	 name: "taskId",//
	            	 label: "主键id",//
	            	 placeholder: "",
	             },
	             {
	            	 type: "select",
	            	 name: "taskSourceId",
	            	 label: "任务来源",
	            	 selectList: [//当类型为select时，option配置					
	            	              {
	            	            	  value: "0",
	            	            	  text: "年度重点工作"
	            	              },
	            	              {
	            	            	  value: "2",
	            	            	  text: "日常性工作"
	            	              },
	            	              {
	            	            	  value: "3",
	            	            	  text: "其他"
	            	              }
	            	              ],
	             },	     
	             {
	            	 type: "text",//
	            	 name: "taskNumber",//
	            	 label: "编号",//
	            	 placeholder: "请输入编号",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "taskMatter",//
	            	 label: "任务事项",//
	            	 placeholder: "请输入任务事项"
	             },
	             {
	            	 type: "date",//
	            	 name: "startTime",//
	            	 label: "任务期限开始时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id:"startTime"
	             },
	             {
	            	 type: "date",//
	            	 name: "endTime",//
	            	 label: "任务期限结束时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id:"endTime"
	             },
	             {
	            	 type: "textarea",//
	            	 name: "content",//
	            	 label: "内容摘要",//
	            	 placeholder: "请输入内容摘要"
	             },
	             {
	            	 type: "text",//
	            	 name: "undertakeDeptName",//
	            	 label: "承办部门",//
	            	 placeholder: "请输入承办部门",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "text",//
	            	 name: "leaderName",//
	            	 label: "负责人",//
	            	 placeholder: "请输入负责人",
	            	 immutableAdd: true
	             },	 
	             {
	            	 type: "text",//
	            	 name: "auditorName",//
	            	 label: "审核人",//
	            	 placeholder: "请输入审核人",
	            	 immutableAdd: true
	             },	 	
	             {
	            	 type: "upload",//
	            	 name: "fileList",//
	            	 label: "附件1",//
	            	 btnName: "添加附件",
	            	 projectName: "zj-oatask",
	            	 uploadUrl: 'uploadFilesByFileName',
	            	 immutableAdd: true,
	            	 fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
	             }	
	             ],
	             onSave: function (obj, _params) {
	            	 l.ajax('updateZjOataskByEdit', _params, function (_params) {
	            		 pager.page('remote')
	            		 obj.close()
	            	 })
	             }  				 
})
//详情
var mainDetailLayer = $('#detailLayer0').detailLayer({
	layerArea:['100%', '100%'],
	conditions: [
	             {
	            	 type: "hidden",//
	            	 name: "taskId",//
	            	 label: "主键id",//
	            	 placeholder: "",
	             },
	             {
	            	 type: "select",
	            	 name: "taskSourceId",
	            	 label: "任务来源",
	            	 immutableAdd: true,
	            	 selectList: [//当类型为select时，option配置					
	            	              {
	            	            	  value: "0",
	            	            	  text: "年度重点工作"
	            	              },
	            	              {
	            	            	  value: "2",
	            	            	  text: "日常性工作"
	            	              },
	            	              {
	            	            	  value: "3",
	            	            	  text: "其他"
	            	              }
	            	              ],
	             },	     
	             {
	            	 type: "text",//
	            	 name: "taskNumber",//
	            	 label: "编号",//
	            	 placeholder: "请输入编号",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "taskMatter",//
	            	 label: "任务事项",//
	            	 placeholder: "请输入任务事项",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "date",//
	            	 name: "startTime",//
	            	 label: "任务期限开始时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id:"startTime",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "date",//
	            	 name: "endTime",//
	            	 label: "任务期限结束时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id:"endTime",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "content",//
	            	 label: "内容摘要",//
	            	 placeholder: "请输入内容摘要",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "text",//
	            	 name: "undertakeDeptName",//
	            	 label: "承办部门",//
	            	 placeholder: "请输入承办部门",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "text",//
	            	 name: "leaderName",//
	            	 label: "负责人",//
	            	 placeholder: "请输入负责人",
	            	 immutableAdd: true
	             },	
	             {
	            	 type: "text",//
	            	 name: "auditorName",//
	            	 label: "审核人",//
	            	 placeholder: "请输入审核人",
	            	 immutableAdd: true
	             },	 	            
	             {
	            	 type: "textarea",//
	            	 name: "delayReason",//
	            	 label: "延期原因",//
	            	 placeholder: "请输入",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "performance",//
	            	 label: "完成情况",//
	            	 placeholder: "请输入",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "upload",//
	            	 name: "fileList",//
	            	 label: "附件",//
	            	 btnName: "添加附件",
	            	 projectName: "zj-oatask",
	            	 uploadUrl: 'uploadFilesByFileName',
	            	 immutableAdd: true,
	            	 fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
	             }	
	             ]		 
})
mainDetailLayer.close = function () {
	parent.layer.close(parent.myOpenLayer)
}
//1--延期操作
var delay1 = $('#delay1').detailLayer({
	layerArea:['100%', '100%'],
	conditions: [
	             {
	            	 type: "hidden",//
	            	 name: "taskId",//
	            	 label: "主键id",//
	            	 placeholder: "",
	             },
	             {
	            	 type: "select",
	            	 name: "taskSourceId",
	            	 label: "任务来源",
	            	 immutableAdd: true,
	            	 selectList: [//当类型为select时，option配置					
	            	              {
	            	            	  value: "0",
	            	            	  text: "年度重点工作"
	            	              },
	            	              {
	            	            	  value: "2",
	            	            	  text: "日常性工作"
	            	              },
	            	              {
	            	            	  value: "3",
	            	            	  text: "其他"
	            	              }
	            	              ],
	             },	     
	             {
	            	 type: "text",//
	            	 name: "taskNumber",//
	            	 label: "编号",//
	            	 placeholder: "请输入编号",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "taskMatter",//
	            	 label: "任务事项",//
	            	 placeholder: "请输入任务事项",
	            	 immutableAdd: true
	             },	        
	             {
	            	 type: "textarea",//
	            	 name: "content",//
	            	 label: "内容摘要",//
	            	 placeholder: "请输入内容摘要",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "text",//
	            	 name: "undertakeDeptName",//
	            	 label: "承办部门",//
	            	 placeholder: "请输入承办部门",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "text",//
	            	 name: "leaderName",//
	            	 label: "负责人",//
	            	 placeholder: "请输入负责人",
	            	 immutableAdd: true
	             },	             	             
	             {
	            	 type: "text",//
	            	 name: "auditorName",//
	            	 label: "审核人",//
	            	 placeholder: "请输入审核人",
	            	 immutableAdd: true
	             },	 	
	             {
	            	 type: "date",//
	            	 name: "startTime",//
	            	 label: "任务期限开始时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id:"startTime",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "date",//
	            	 name: "endTime",//
	            	 label: "任务期限结束时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id:"endTime",
	            	 must: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "delayReason",//
	            	 label: "延期原因",//
	            	 placeholder: "请输入延期原因",
	            	 must: true
	             },
	             {
	            	 type: "upload",//
	            	 name: "fileList",//
	            	 label: "附件",//
	            	 btnName: "添加附件",
	            	 projectName: "zj-oatask",
	            	 uploadUrl: 'uploadFilesByFileName',
	            	 immutableAdd: true,
	            	 fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
	             }	
	             ],
	             onSave: function (obj, _params) {
	            	 l.ajax('updateZjOatask', _params, function (_params) {
	            		 pager1.page('remote')
	            		 obj.close()
	            	 })
	             }   
})
//详情
var mainDetailLayer1 = $('#detailLayer1').detailLayer({
	layerArea:['100%', '100%'],
	conditions: [
	             {
	            	 type: "hidden",//
	            	 name: "taskId",//
	            	 label: "主键id",//
	            	 placeholder: "",
	             },
	             {
	            	 type: "select",
	            	 name: "taskSourceId",
	            	 label: "任务来源",
	            	 immutableAdd: true,
	            	 selectList: [//当类型为select时，option配置					
	            	              {
	            	            	  value: "0",
	            	            	  text: "年度重点工作"
	            	              },
	            	              {
	            	            	  value: "2",
	            	            	  text: "日常性工作"
	            	              },
	            	              {
	            	            	  value: "3",
	            	            	  text: "其他"
	            	              }
	            	              ],
	             },	     
	             {
	            	 type: "text",//
	            	 name: "taskNumber",//
	            	 label: "编号",//
	            	 placeholder: "请输入编号",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "taskMatter",//
	            	 label: "任务事项",//
	            	 placeholder: "请输入任务事项",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "date",//
	            	 name: "startTime",//
	            	 label: "任务期限开始时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id:"startTime",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "date",//
	            	 name: "endTime",//
	            	 label: "任务期限结束时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id:"endTime",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "content",//
	            	 label: "内容摘要",//
	            	 placeholder: "请输入内容摘要",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "text",//
	            	 name: "undertakeDeptName",//
	            	 label: "承办部门",//
	            	 placeholder: "请输入承办部门",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "text",//
	            	 name: "leaderName",//
	            	 label: "负责人",//
	            	 placeholder: "请输入负责人",
	            	 immutableAdd: true
	             },	 
	             {
	            	 type: "text",//
	            	 name: "auditorName",//
	            	 label: "审核人",//
	            	 placeholder: "请输入审核人",
	            	 immutableAdd: true
	             },	 	
	             {
	            	 type: "textarea",//
	            	 name: "delayReason",//
	            	 label: "延期原因",//
	            	 placeholder: "请输入",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "performance",//
	            	 label: "完成情况",//
	            	 placeholder: "请输入",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "upload",//
	            	 name: "fileList",//
	            	 label: "附件1",//
	            	 btnName: "添加附件",
	            	 projectName: "zj-oatask",
	            	 uploadUrl: 'uploadFilesByFileName',
	            	 immutableAdd: true,
	            	 fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
	             }	
	             ]  
})
mainDetailLayer1.close = function () {
	parent.layer.close(parent.myOpenLayer)
}
//2--延期操作
var delay2 = $('#delay2').detailLayer({
	layerArea:['100%', '100%'],
	conditions: [
	             {
	            	 type: "hidden",//
	            	 name: "taskId",//
	            	 label: "主键id",//
	            	 placeholder: "",
	             },
	             {
	            	 type: "select",
	            	 name: "taskSourceId",
	            	 label: "任务来源",
	            	 immutableAdd: true,
	            	 selectList: [//当类型为select时，option配置					
	            	              {
	            	            	  value: "0",
	            	            	  text: "年度重点工作"
	            	              },
	            	              {
	            	            	  value: "2",
	            	            	  text: "日常性工作"
	            	              },
	            	              {
	            	            	  value: "3",
	            	            	  text: "其他"
	            	              }
	            	              ],
	             },	     
	             {
	            	 type: "text",//
	            	 name: "taskNumber",//
	            	 label: "编号",//
	            	 placeholder: "请输入编号",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "taskMatter",//
	            	 label: "任务事项",//
	            	 placeholder: "请输入任务事项",
	            	 immutableAdd: true
	             },	        
	             {
	            	 type: "textarea",//
	            	 name: "content",//
	            	 label: "内容摘要",//
	            	 placeholder: "请输入内容摘要",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "text",//
	            	 name: "undertakeDeptName",//
	            	 label: "承办部门",//
	            	 placeholder: "请输入承办部门",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "text",//
	            	 name: "leaderName",//
	            	 label: "负责人",//
	            	 placeholder: "请输入负责人",
	            	 immutableAdd: true
	             },	             	             
	             {
	            	 type: "text",//
	            	 name: "auditorName",//
	            	 label: "审核人",//
	            	 placeholder: "请输入审核人",
	            	 immutableAdd: true
	             },	 	
	             {
	            	 type: "date",//
	            	 name: "startTime",//
	            	 label: "任务期限开始时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id:"startTime",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "date",//
	            	 name: "endTime",//
	            	 label: "任务期限结束时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id:"endTime",
	            	 must: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "delayReason",//
	            	 label: "延期原因",//
	            	 placeholder: "请输入延期原因",
	            	 must: true
	             },
	             {
	            	 type: "upload",//
	            	 name: "fileList",//
	            	 label: "附件",//
	            	 btnName: "添加附件",
	            	 projectName: "zj-oatask",
	            	 uploadUrl: 'uploadFilesByFileName',
	            	 immutableAdd: true,
	            	 fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
	             }	
	             ],
	             onSave: function (obj, _params) {
	            	 l.ajax('updateZjOatask', _params, function (_params) {
	            		 pager2.page('remote')
	            		 obj.close()
	            	 })
	             }   
})
//详情
var mainDetailLayer2 = $('#detailLayer2').detailLayer({
	layerArea:['100%', '100%'],
	conditions: [
	             {
	            	 type: "hidden",//
	            	 name: "taskId",//
	            	 label: "主键id",//
	            	 placeholder: "",
	             },
	             {
	            	 type: "select",
	            	 name: "taskSourceId",
	            	 label: "任务来源",
	            	 immutableAdd: true,
	            	 selectList: [//当类型为select时，option配置					
	            	              {
	            	            	  value: "0",
	            	            	  text: "年度重点工作"
	            	              },
	            	              {
	            	            	  value: "2",
	            	            	  text: "日常性工作"
	            	              },
	            	              {
	            	            	  value: "3",
	            	            	  text: "其他"
	            	              }
	            	              ],
	             },	     
	             {
	            	 type: "text",//
	            	 name: "taskNumber",//
	            	 label: "编号",//
	            	 placeholder: "请输入编号",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "taskMatter",//
	            	 label: "任务事项",//
	            	 placeholder: "请输入任务事项",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "date",//
	            	 name: "startTime",//
	            	 label: "任务期限开始时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id:"startTime",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "date",//
	            	 name: "endTime",//
	            	 label: "任务期限结束时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id:"endTime",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "content",//
	            	 label: "内容摘要",//
	            	 placeholder: "请输入内容摘要",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "text",//
	            	 name: "undertakeDeptName",//
	            	 label: "承办部门",//
	            	 placeholder: "请输入承办部门",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "text",//
	            	 name: "leaderName",//
	            	 label: "负责人",//
	            	 placeholder: "请输入负责人",
	            	 immutableAdd: true
	             },	 
	             {
	            	 type: "text",//
	            	 name: "auditorName",//
	            	 label: "审核人",//
	            	 placeholder: "请输入审核人",
	            	 immutableAdd: true
	             },	 	
	             {
	            	 type: "textarea",//
	            	 name: "delayReason",//
	            	 label: "延期原因",//
	            	 placeholder: "请输入",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "performance",//
	            	 label: "完成情况",//
	            	 placeholder: "请输入",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "upload",//
	            	 name: "fileList",//
	            	 label: "附件1",//
	            	 btnName: "添加附件",
	            	 projectName: "zj-oatask",
	            	 uploadUrl: 'uploadFilesByFileName',
	            	 immutableAdd: true,
	            	 fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
	             }	
	             ]  
})
mainDetailLayer2.close = function () {
	parent.layer.close(parent.myOpenLayer)
}
//3--延期操作
var delay3 = $('#delay3').detailLayer({
	layerArea:['100%', '100%'],
	conditions: [
	             {
	            	 type: "hidden",//
	            	 name: "taskId",//
	            	 label: "主键id",//
	            	 placeholder: "",
	             },
	             {
	            	 type: "select",
	            	 name: "taskSourceId",
	            	 label: "任务来源",
	            	 immutableAdd: true,
	            	 selectList: [//当类型为select时，option配置					
	            	              {
	            	            	  value: "0",
	            	            	  text: "年度重点工作"
	            	              },
	            	              {
	            	            	  value: "2",
	            	            	  text: "日常性工作"
	            	              },
	            	              {
	            	            	  value: "3",
	            	            	  text: "其他"
	            	              }
	            	              ],
	             },	     
	             {
	            	 type: "text",//
	            	 name: "taskNumber",//
	            	 label: "编号",//
	            	 placeholder: "请输入编号",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "taskMatter",//
	            	 label: "任务事项",//
	            	 placeholder: "请输入任务事项",
	            	 immutableAdd: true
	             },	        
	             {
	            	 type: "textarea",//
	            	 name: "content",//
	            	 label: "内容摘要",//
	            	 placeholder: "请输入内容摘要",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "text",//
	            	 name: "undertakeDeptName",//
	            	 label: "承办部门",//
	            	 placeholder: "请输入承办部门",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "text",//
	            	 name: "leaderName",//
	            	 label: "负责人",//
	            	 placeholder: "请输入负责人",
	            	 immutableAdd: true
	             },	             	             
	             {
	            	 type: "text",//
	            	 name: "auditorName",//
	            	 label: "审核人",//
	            	 placeholder: "请输入审核人",
	            	 immutableAdd: true
	             },	 	
	             {
	            	 type: "date",//
	            	 name: "startTime",//
	            	 label: "任务期限开始时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id:"startTime",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "date",//
	            	 name: "endTime",//
	            	 label: "任务期限结束时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id:"endTime",
	            	 must: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "delayReason",//
	            	 label: "延期原因",//
	            	 placeholder: "请输入延期原因",
	            	 must: true
	             },
	             {
	            	 type: "upload",//
	            	 name: "fileList",//
	            	 label: "附件",//
	            	 btnName: "添加附件",
	            	 projectName: "zj-oatask",
	            	 uploadUrl: 'uploadFilesByFileName',
	            	 immutableAdd: true,
	            	 fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
	             }	
	             ],
	             onSave: function (obj, _params) {
	            	 l.ajax('updateZjOatask', _params, function (_params) {
	            		 pager3.page('remote')
	            		 obj.close()
	            	 })
	             }   
})
//详情
var mainDetailLayer3 = $('#detailLayer3').detailLayer({
	layerArea:['100%', '100%'],
	conditions: [
	             {
	            	 type: "hidden",//
	            	 name: "taskId",//
	            	 label: "主键id",//
	            	 placeholder: "",
	             },
	             {
	            	 type: "select",
	            	 name: "taskSourceId",
	            	 label: "任务来源",
	            	 immutableAdd: true,
	            	 selectList: [//当类型为select时，option配置					
	            	              {
	            	            	  value: "0",
	            	            	  text: "年度重点工作"
	            	              },
	            	              {
	            	            	  value: "1",
	            	            	  text: "持续性工作"
	            	              },
	            	              {
	            	            	  value: "2",
	            	            	  text: "日常性工作"
	            	              },
	            	              {
	            	            	  value: "3",
	            	            	  text: "其他"
	            	              }
	            	              ],
	             },	     
	             {
	            	 type: "text",//
	            	 name: "taskNumber",//
	            	 label: "编号",//
	            	 placeholder: "请输入编号",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "taskMatter",//
	            	 label: "任务事项",//
	            	 placeholder: "请输入任务事项",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "date",//
	            	 name: "startTime",//
	            	 label: "任务期限开始时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id:"startTime",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "date",//
	            	 name: "endTime",//
	            	 label: "任务期限结束时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id:"endTime",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "content",//
	            	 label: "内容摘要",//
	            	 placeholder: "请输入内容摘要",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "text",//
	            	 name: "undertakeDeptName",//
	            	 label: "承办部门",//
	            	 placeholder: "请输入承办部门",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "text",//
	            	 name: "leaderName",//
	            	 label: "负责人",//
	            	 placeholder: "请输入负责人",
	            	 immutableAdd: true
	             },	 
	             {
	            	 type: "text",//
	            	 name: "auditorName",//
	            	 label: "审核人",//
	            	 placeholder: "请输入审核人",
	            	 immutableAdd: true
	             },	 	
	             {
	            	 type: "textarea",//
	            	 name: "delayReason",//
	            	 label: "延期原因",//
	            	 placeholder: "请输入",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "performance",//
	            	 label: "完成情况",//
	            	 placeholder: "请输入",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "upload",//
	            	 name: "fileList",//
	            	 label: "附件1",//
	            	 btnName: "添加附件",
	            	 projectName: "zj-oatask",
	            	 uploadUrl: 'uploadFilesByFileName',
	            	 immutableAdd: true,
	            	 fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
	             }	
	             ]  
})
mainDetailLayer3.close = function () {
	parent.layer.close(parent.myOpenLayer)
}
//4--延期操作
var delay4 = $('#delay4').detailLayer({
	layerArea:['100%', '100%'],
	conditions: [
	             {
	            	 type: "hidden",//
	            	 name: "taskId",//
	            	 label: "主键id",//
	            	 placeholder: "",
	             },
	             {
	            	 type: "select",
	            	 name: "taskSourceId",
	            	 label: "任务来源",
	            	 immutableAdd: true,
	            	 selectList: [//当类型为select时，option配置					
	            	              {
	            	            	  value: "0",
	            	            	  text: "年度重点工作"
	            	              },
	            	              {
	            	            	  value: "1",
	            	            	  text: "持续性工作"
	            	              },
	            	              {
	            	            	  value: "2",
	            	            	  text: "日常性工作"
	            	              },
	            	              {
	            	            	  value: "3",
	            	            	  text: "其他"
	            	              }
	            	              ],
	             },	     
	             {
	            	 type: "text",//
	            	 name: "taskNumber",//
	            	 label: "编号",//
	            	 placeholder: "请输入编号",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "taskMatter",//
	            	 label: "任务事项",//
	            	 placeholder: "请输入任务事项",
	            	 immutableAdd: true
	             },	        
	             {
	            	 type: "textarea",//
	            	 name: "content",//
	            	 label: "内容摘要",//
	            	 placeholder: "请输入内容摘要",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "text",//
	            	 name: "undertakeDeptName",//
	            	 label: "承办部门",//
	            	 placeholder: "请输入承办部门",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "text",//
	            	 name: "leaderName",//
	            	 label: "负责人",//
	            	 placeholder: "请输入负责人",
	            	 immutableAdd: true
	             },	             	             
	             {
	            	 type: "text",//
	            	 name: "auditorName",//
	            	 label: "审核人",//
	            	 placeholder: "请输入审核人",
	            	 immutableAdd: true
	             },	 	
	             {
	            	 type: "date",//
	            	 name: "startTime",//
	            	 label: "任务期限开始时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id:"startTime",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "date",//
	            	 name: "endTime",//
	            	 label: "任务期限结束时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id:"endTime",
	            	 must: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "delayReason",//
	            	 label: "延期原因",//
	            	 placeholder: "请输入延期原因",
	            	 must: true
	             },
	             {
	            	 type: "upload",//
	            	 name: "fileList",//
	            	 label: "附件",//
	            	 btnName: "添加附件",
	            	 projectName: "zj-oatask",
	            	 uploadUrl: 'uploadFilesByFileName',
	            	 immutableAdd: true,
	            	 fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
	             }	
	             ],
	             onSave: function (obj, _params) {
	            	 l.ajax('updateZjOatask', _params, function (_params) {
	            		 pager4.page('remote')
	            		 obj.close()
	            	 })
	             }   
})
//详情
var mainDetailLayer4 = $('#detailLayer4').detailLayer({
	layerArea:['100%', '100%'],
	conditions: [
	             {
	            	 type: "hidden",//
	            	 name: "taskId",//
	            	 label: "主键id",//
	            	 placeholder: "",
	             },
	             {
	            	 type: "select",
	            	 name: "taskSourceId",
	            	 label: "任务来源",
	            	 immutableAdd: true,
	            	 selectList: [//当类型为select时，option配置					
	            	              {
	            	            	  value: "0",
	            	            	  text: "年度重点工作"
	            	              },
	            	              {
	            	            	  value: "1",
	            	            	  text: "持续性工作"
	            	              },
	            	              {
	            	            	  value: "2",
	            	            	  text: "日常性工作"
	            	              },
	            	              {
	            	            	  value: "3",
	            	            	  text: "其他"
	            	              }
	            	              ],
	             },	     
	             {
	            	 type: "text",//
	            	 name: "taskNumber",//
	            	 label: "编号",//
	            	 placeholder: "请输入编号",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "taskMatter",//
	            	 label: "任务事项",//
	            	 placeholder: "请输入任务事项",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "date",//
	            	 name: "startTime",//
	            	 label: "任务期限开始时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id:"startTime",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "date",//
	            	 name: "endTime",//
	            	 label: "任务期限结束时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id:"endTime",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "content",//
	            	 label: "内容摘要",//
	            	 placeholder: "请输入内容摘要",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "text",//
	            	 name: "undertakeDeptName",//
	            	 label: "承办部门",//
	            	 placeholder: "请输入承办部门",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "text",//
	            	 name: "leaderName",//
	            	 label: "负责人",//
	            	 placeholder: "请输入负责人",
	            	 immutableAdd: true
	             },	 
	             {
	            	 type: "text",//
	            	 name: "auditorName",//
	            	 label: "审核人",//
	            	 placeholder: "请输入审核人",
	            	 immutableAdd: true
	             },	 	
	             {
	            	 type: "textarea",//
	            	 name: "delayReason",//
	            	 label: "延期原因",//
	            	 placeholder: "请输入",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "performance",//
	            	 label: "完成情况",//
	            	 placeholder: "请输入",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "upload",//
	            	 name: "fileList",//
	            	 label: "附件1",//
	            	 btnName: "添加附件",
	            	 projectName: "zj-oatask",
	            	 uploadUrl: 'uploadFilesByFileName',
	            	 immutableAdd: true,
	            	 fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
	             }	
	             ]  
})
mainDetailLayer4.close = function () {
	parent.layer.close(parent.myOpenLayer)
}
$("body").on("click", "button", function () {
	var checkedData = l.getTableCheckedData(table0)
	var name = $(this).attr("data-name");
	switch (name) {
	case "derive"://导出
		var params = {}
		//params.departmentId = $('[name = "departmentId"]').val();//部门			
		layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
			l.ajax('exportExcelZjOataskAllList', params, function (res) {
				layer.alert("导出成功！", { icon: 0 }, function (index) {
					layer.close(index);
					window.location.href = res;
				})
			})
		}); 			
		break;
	case "derive1"://导出
		var params = {}			
		layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
			l.ajax('exportExcelZjOataskOneList', params, function (res) {
				layer.alert("导出成功！", { icon: 0 }, function (index) {
					layer.close(index);
					window.location.href = res;
				})
			})
		}); 			
		break;
	case "derive2"://导出
		var params = {}			
		layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
			l.ajax('exportExcelZjOataskTwoList', params, function (res) {
				layer.alert("导出成功！", { icon: 0 }, function (index) {
					layer.close(index);
					window.location.href = res;
				})
			})
		}); 			
		break;
	case "derive3"://导出
		var params = {}			
		layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
			l.ajax('exportExcelZjOataskThreeList', params, function (res) {
				layer.alert("导出成功！", { icon: 0 }, function (index) {
					layer.close(index);
					window.location.href = res;
				})
			})
		}); 			
		break;
	case "derive4"://导出
		var params = {}			
		layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
			l.ajax('exportExcelZjOataskFourList', params, function (res) {
				layer.alert("导出成功！", { icon: 0 }, function (index) {
					layer.close(index);
					window.location.href = res;
				})
			})
		}); 			
		break;		
	}
})
function myOpen(index, type) {
	var rowData = allData[Number(index)];
	var params = {};
	params.taskId = rowData.taskId;
	switch (type) {
	case 'xq':
		l.ajax('getZjOataskDetail', params, function (res) {
			mainDetailLayer.open(res);
			$('#detailLayer0 .btn').hide();
		})
		break;
	case 'delay':
		l.ajax('getZjOataskDetail', params, function (res) {
			delay0.open(res);
		})
		break;
	case 'edit':
		l.ajax('getZjOataskDetail', params, function (res) {
			editDetailLayer.open(res);
		})
		break;		
	case 'finish':
		l.ajax('getZjOataskDetail', params, function (res) {
			finish0.open(res);
		})
		break;
		break;
	}
}
function myOpen1(index, type) {
	var rowData = allData1[Number(index)];
	var params = {};
	params.taskId = rowData.taskId;
	switch (type) {
	case 'xq':
		l.ajax('getZjOataskDetail', params, function (res) {
			mainDetailLayer1.open(res);
			$('#detailLayer1 .btn').hide();
		})
		break;
	case 'delay':
		l.ajax('getZjOataskDetail', params, function (res) {
			delay1.open(res);
		})
		break;
	case 'finish':
		params.operateFlag = '2';
		layer.confirm("确定已经完成？", { icon: 0, }, function (index) {
			l.ajax('updateZjOataskState', params, function (res) {
				pager1.page('remote')
				layer.close(index);
			})
		});	   
		break;
	}
}
function myOpen2(index, type) {
	var rowData = allData2[Number(index)];
	var params = {};
	params.taskId = rowData.taskId;
	switch (type) {
	case 'xq':
		l.ajax('getZjOataskDetail', params, function (res) {
			mainDetailLayer2.open(res);
			$('#detailLayer2 .btn').hide();
		})
		break;
	case 'delay':
		l.ajax('getZjOataskDetail', params, function (res) {
			delay2.open(res);
		})
		break;
	case 'finish':
		params.operateFlag = '2';
		layer.confirm("确定已经完成？", { icon: 0, }, function (index) {
			l.ajax('updateZjOataskState', params, function (res) {
				pager2.page('remote')
				layer.close(index);
			})
		});	   
		break;
	}
}
function myOpen3(index, type) {
	var rowData = allData3[Number(index)];
	var params = {};
	params.taskId = rowData.taskId;
	switch (type) {
	case 'xq':
		l.ajax('getZjOataskDetail', params, function (res) {
			mainDetailLayer3.open(res);
			$('#detailLayer3.btn').hide();
		})
		break;
	case 'delay':
		l.ajax('getZjOataskDetail', params, function (res) {
			delay3.open(res);
		})
		break;
	case 'finish':
		params.operateFlag = '2';
		layer.confirm("确定已经完成？", { icon: 0, }, function (index) {
			l.ajax('updateZjOataskState', params, function (res) {
				pager3.page('remote')
				layer.close(index);
			})
		});	   
		break;
	}
}
function myOpen4(index, type) {
	var rowData = allData4[Number(index)];
	var params = {};
	params.taskId = rowData.taskId;
	switch (type) {
	case 'xq':
		l.ajax('getZjOataskDetail', params, function (res) {
			mainDetailLayer4.open(res);
			$('#detailLayer4 .btn').hide();
		})
		break;
	case 'delay':
		l.ajax('getZjOataskDetail', params, function (res) {
			delay4.open(res);
		})
		break;
	case 'finish':
		params.operateFlag = '2';
		layer.confirm("确定已经完成？", { icon: 0, }, function (index) {
			l.ajax('updateZjOataskState', params, function (res) {
				pager4.page('remote')
				layer.close(index);
			})
		});	   
		break;
	}
}
$("#tab-system").Huitab({index: 0});