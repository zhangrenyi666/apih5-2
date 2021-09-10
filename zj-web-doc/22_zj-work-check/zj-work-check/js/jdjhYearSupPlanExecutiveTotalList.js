﻿var code = Lny.getUrlParam('code')
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
	            	   "title": 'No.',
	            	   "width": 25,
	            	   "render": function (data) {
	            		   return data + 1
	            	   }
	               },
	               {
	            	   targets: [1], //第几列
	            	   title: "监督责任部门", //表头名
	            	   data: "supDutyDepName", //接口对应字段
	            	   defaultContent: "-" //默认显示
	               },					   
	               {
	            	   targets: [2], //第几列
	            	   title: "监督事项", //表头名
	            	   data: "supMatter", //接口对应字段
	            	   defaultContent: "-" //默认显示
	               },					   	               
	               {
	            	   targets: [3], //第几列
	            	   title: "配合部门", //表头名
	            	   data: "coopDept", //接口对应字段
	            	   defaultContent: "-" //默认显示
	               },
	               {
	            	   targets: [4], //第几列
	            	   "title": "年度",//表头名
	            	   "data": "year",//接口对应字段
	            	   "defaultContent": "-",//默认显示
	            	   "render": function (data) {
	            		   var text = ""
	            			   switch (data) {
	            			   case "0": text = "2019";
	            			   break;
	            			   case "1": text = "2020";
	            			   break;
	            			   case "2": text = "2021";
	            			   break;
	            			   case "3": text = "2022";
	            			   break;
	            			   case "4": text = "2023";
	            			   break;
	            			   default: text = "未知";
	            			   break;
	            			   }
	            		   return text
	            	   }
	               },
	               {
	            	   "targets": [5],//第几列
	            	   "title": "季度",//表头名
	            	   "data": "quarterPlan",//接口对应字段
	            	   "defaultContent": "-",//默认显示
	            	   "render": function (data) {
	            		   var text = ""
	            			   switch (data) {
	            			   case "0": text = "第一季度";
	            			   break;
	            			   case "1": text = "第二季度";
	            			   break;
	            			   case "2": text = "第三季度";
	            			   break;
	            			   case "3": text = "第四季度";
	            			   break;
	            			   default: text = "未知";
	            			   break;
	            			   }
	            		   return text
	            	   }
	               },					
	               {
	            	   targets: [6], //第几列
	            	   title: "计划完成时间", //表头名
	            	   data: "scheduleTime", //接口对应字段
	            	   defaultContent: "-", //默认显示
	            	   "render": function (data) {
	            		   return data ? l.dateFormat(new Date(data), "yyyy-MM-dd") : "-";
	            	   }
	               },
	               {
	            	   "targets": [7],//第几列
	            	   "data": "principal",//接口对应字段
	            	   "title": "责任人",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [8],//第几列
	            	   "data": "remarks",//接口对应字段
	            	   "title": "备注",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [9],//第几列
	            	   "data": "oneTargets",//接口对应字段
	            	   "title": "一季度进展情况",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [10],//第几列
	            	   "data": "twoTargets",//接口对应字段
	            	   "title": "二季度进展情况",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [11],//第几列
	            	   "data": "threeTargets",//接口对应字段
	            	   "title": "三季度进展情况",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [12],//第几列
	            	   "data": "fourTargets",//接口对应字段
	            	   "title": "四季度进展情况",//表头名
	            	   "defaultContent": "-",//默认显示
	               }		 			   

	               ]
});
var form = $('#form').filterfrom({
	conditions: [//表单项配置
	             {
	            	 type: "text",//三种形式：text,select,date,
	            	 name: "supDutyDep",//输入字段名
	            	 label: "监督责任部门",//输入字段对杨的中文名称
	            	 placeholder: "请输入监督责任部门",
	             },
	             {
	            	 type: "select",//三种形式：text,select,date,
	            	 name: "year",//输入字段名
	            	 label: "年度",//输入字段对杨的中文名称          
	            	 selectList: [           
	            	              {
	            	            	  value: "",
	            	            	  text: "请选择"
	            	              },
	            	              {
	            	            	  value: "0",
	            	            	  text: "2019"
	            	              },
	            	              {
	            	            	  value: "1",
	            	            	  text: "2020"
	            	              },
	            	              {
	            	            	  value: "2",
	            	            	  text: "2021"
	            	              },
	            	              {
	            	            	  value: "3",
	            	            	  text: "2022"
	            	              },
	            	              {
	            	            	  value: "4",
	            	            	  text: "2023"
	            	              }
	            	              ]
	             },
	             {
	                 type: "select",
	                 name: "quarterPlan",
	                 label: "季度",
	                 selectList: [//当类型为select时，option配置  0:全公司  1：机关  2：项目
	                   {
	                     value: "",
	                     text: "请选择 "
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
	                 ]
	               }		
	             ],
	             onSearch: function (arr) {//搜索按钮回调
	            	 var _params = {};
	            	 for (var i = 0; i < arr.length; i++) {
	            		 _params[arr[i].name] = arr[i].value;
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
		url: l.getApiUrl("getZjJdjhSupPlanExecutiveTotalList"),
		params: {},
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
	            	 name: "twoTargets",//
	            	 label: "第二季度工作目标",//
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
	            	 name: "fourTargets",//
	            	 label: "第四季度工作目标",//
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
	            	 type: "text",//
	            	 name: "planCompletionTime",//
	            	 label: "计划完成时间",//
	            	 placeholder: "请输入计划完成时间",
	            	 immutableAdd: true 
	             },
	             {
	            	 type: "textarea",//
	            	 name: "planCompletionCondition",//
	            	 label: "计划完成情况",//
	            	 placeholder: "请输入计划完成情况",
	            	 immutableAdd: true 
	             },	             		
	             {
	            	 type: "upload",//
	            	 name: "fileList",//
	            	 label: "监督工作结果",//
	            	 btnName: "附件",
	            	 projectName: "zj-work-check",
	            	 immutableAdd: true, 
	            	 fileType: ["jpg", "jpeg", "png", "gif"]
	             }
	             ],
})

$("body").on("click", "button", function () {
	var checkedData = l.getTableCheckedData(table)
	var name = $(this).attr("data-name");
	switch (name) {
	case "derive"://党费使用导出
		var params = {}
		var params={};
		params.supDutyDep = $('[name = "supDutyDep"]').val();
		params.year =  $('[name = "year"]').val();

		layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
			l.ajax('exportExcelZjJdjhSupPlanExecutiveTotal', params, function (res) {
				layer.alert("导出成功！", { icon: 0 }, function (index) {
					layer.close(index);
					window.location.href = res;
				})
			})
		}); 			
		break
	}
})


var myOpenLayer;
function myOpen1(index, type) {
	var rowData = allData[Number(index)];
	var params;
	switch (type) {
	case 'xq':
		params = {};
		params.supPlanExecutiveConditionId = rowData.supPlanExecutiveConditionId;
		l.ajax('getZjJdjhSupPlanExecutiveDetail', params, function (res) {
			exeDetailLayer.open(res);
			$('#exeDetailLayer .btn').hide();
		})
		break;
	}
}
