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
	            			   a = '<a style="color:blue;" onclick="myOpen1(\' '+row.rowIndex+ '\',\'' + 'xq' + '\')" href="javascript:void(0);">'+data.taskMatter+'</a>'
	            		   }
	            		   return a;
	            	   }		
	               },
	               {
	            	   "targets": [4],//第几列
	            	   "data": "taskNumber",//接口对应字段
	            	   "title": "编号",//表头名
	            	   "defaultContent": "-",//默认显示
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
	               } ,    
	               {
	            	   "targets": [9],//第几列
	            	   "data": "endTime",//接口对应字段
	            	   "title": "要求完成期限",//表头名
	            	   "defaultContent": "-",//默认显示
	            	   "render": function (data) {
	            		   return l.dateFormat(new Date(data), "yyyy-MM-dd");
	            	   }
	               } ,  	                          
	               {
	            	   "targets": [10],//第几列
	            	   "width": 80,
	            	   "data": function (row) {
	            		   return row
	            	   },//接口对应字段
	            	   "title": "操作",//表头名
	            	   "render": function (data) {
	            		   var html = '';
	            		    html += '<span class="dropDown">'
				            html += '<a style="color:blue;"  href="javascript:void(0);" onclick="myOpen1(\' ' + data.rowIndex + '\',\'' + 'appAgree' + '\')" href="javascript:void(0);">同意</a>';
							html += '  -  ';
				            html += '<a style="color:blue;"  href="javascript:void(0);" onclick="myOpen1(\' ' + data.rowIndex + '\',\'' + 'appBack' + '\')" href="javascript:void(0);">驳回</a>';
                            html += '</span>'	
	            		 return html;
	            	   }
	               }
    ]
});

var form = $('#form').filterfrom({
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
var pager = $("#pager").page({
   remote: {//ajax请求配置
		url: l.getApiUrl("getZjOataskTodoList"),
		params: {
			todoFlag:"1"
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
//详情
var ruleDetailLayer = $('#ruleDetailLayer').detailLayer({
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
	            	 type: "pickTree",//
	            	 name: "oaAuditor",//接口字段名
	            	 label: "审核人",//
	            	 immutableAdd: true,
	            	 pickType: {
	            		 department: false,//部门列表对应接口字段名,false表示不开启该类
	            		 member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
	            	 }
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
	            	 name: "delayReason",//
	            	 label: "延期原因",//
	            	 placeholder: "请输入延期原因",
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
	             ],
})
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
		case "agree1"://同意  		 		
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {			   
				for(var i=0;i<checkedData.length;i++){
				 if(checkedData[i].state != '0' && checkedData[i].state != '2' && checkedData[i].state != '7'){
				    layer.alert("已经提交不能再次提交", { icon: 0, }, function (index) {
                    layer.close(index);
                    });	
                  break;					
				  }else{
				   layer.confirm("确定提交？", { icon: 0, }, function (index) {
				    checkedData[0].typeFlag = '0'//提交
                    l.ajax("batchSubmitZjXmRules", checkedData, function () {
                        pager.page('remote')
                    })
                    layer.close(index);
                   });
				}
			 }			   
            } 
            break;
		case "back1"://驳回
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {			
			  	for(var i=0;i<checkedData.length;i++){
				    if(checkedData[i].state != '0' && checkedData[i].state != '2'){
				        layer.alert("已经提交不能删除", { icon: 0, }, function (index) {
                        layer.close(index);
                       });	
                      break;					
				    }else{
				        layer.confirm("确定删除？", { icon: 0, }, function (index) {
                            l.ajax("batchDeleteUpdateZjXmRules", checkedData, function () {
                             pager.page('remote')
                            })
                          layer.close(index);
                        });
				    }
				}									   
            }
            break;
    }
})

function myOpen1(index, type) {
    var rowData = allData[Number(index)];
    var params = {};
        params.taskId = rowData.taskId;
		params.taskStateFlag = rowData.taskStateFlag;
    switch (type) {
        case 'xq':
            l.ajax('getZjOataskDetail', params, function (res) {
                ruleDetailLayer.open(res);
				$('#ruleDetailLayer .btn').hide();
            })
            break; 
		case 'appAgree':
		    params.operateFlag = '0';			
            l.ajax('updateZjOataskState', params, function (res) {
                pager.page('remote');		 
            })
            break;
		case 'appBack':
		    params.operateFlag = '1';
            l.ajax('updateZjOataskState', params, function (res) {
                pager.page('remote');				 
            })
            break;
    }
}
