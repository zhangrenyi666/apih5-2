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
	            	   "targets": [1],//第几列
	            	   "data": "title",//接口对应字段
	            	   "title": "标题",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [2],//第几列
	            	   "data": "applyDepName",//接口对应字段
	            	   "title": "主责部门",//表头名
	            	   "defaultContent": "-",//默认显示
	            	   "render":function(data,index,row){
	            		   var a;
	            		   if(data){   
	            			   a = '<a style="color:blue;" onclick="myOpen1(\' '+row.applyId+ '\',\''+ 'jointSupDataListFlowEdit' + '\')" href="javascript:void(0);">'+data+'</a>'
	            		   }
	            		   return a;
	            	   }
	               },
	               {
	            	   "targets": [3],//第几列
	            	   "title": "填报人",//表头名
	            	   "data": "informant",//接口对应字段
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [4],//第几列
	            	   "data": "needDeptNameList",//接口对应字段
	            	   "title": "配合部门",//表头名
	            	   "defaultContent": "-",//默认显示
	            	   "render": function (data) {	
	            		   var  text = "";
	            		   for (var i = 0; i < data.length; i++) {         			  
	            			   var name =data[i].objectName
	            			   text += name+"       -";
	            		   }
	            		   return text;
	            	   }
	               },
	               {
	            	   "targets": [5],//第几列
	            	   "data": "informantTime",//接口对应字段
	            	   "title": "填报时间",//表头名
	            	   "defaultContent": "-",//默认显示
	            	   "render": function (data) {
	            		   return l.dateFormat(new Date(data), "yyyy-MM-dd");
	            	   }
	               },
	               {
	            	   "targets": [6],//第几列
	            	   "data": "apih5FlowStatus",//接口对应字段
	            	   "title": "审核状态",//表头名   0:全公司  1：机关  2：项目
	            	   "defaultContent": "-",//默认显示
	            	   "render": function (data) {
	            		   var text = ""
	            			   switch (data) {
	            			   case "0": text = "待提交";
	            			   break;
	            			   case "1": text = "审核中";
	            			   break;
	            			   case "2": text = "通过";
	            			   break;
	            			   case "3": text = "驳回";
	            			   break;
	            			   default: text = "-";
	            			   break;
	            			   }
	            		   return text
	            	   }
	               },		        
	               {
	            	   "targets": [7],//第几列
	            	   "data": "modifyTime",//接口对应字段
	            	   "title": "操作时间",//表头名
	            	   "defaultContent": "-",//默认显示
	            	   "render": function (data) {
	            		   return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
	            	   }
	               }
	               ]
});
var allData;
var pager = $("#pager").page({
	remote: {//ajax请求配置
		url: l.getApiUrl("getZjLhddJointSupApplyList"),
		params: {
			apih5FlowStatus: "2"
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

$("body").on("click", "button", function () {
	var checkedData = l.getTableCheckedData(table);
	var name = $(this).attr("data-name");
	switch (name) {
	// case "add":
	// 	myOpen('联合督导无流程新增','','jointSupDataListNoFlowAdd')
	// 	break;
	case "edit":
		if (checkedData.length == 1) {
			var applyId = checkedData[0].applyId
			if (checkedData[0].flowLaunchFlag === "0") {
				myOpen('资料清单确认流程--编辑', applyId, 'jointSupDataListFlowEdit', '')
			} else if (checkedData[0].flowLaunchFlag === "1") {
				myOpen('其他资料清单确认不是流程--编辑', applyId, 'jointSupDataListNoFlowAdd', '')
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
function myOpen(title, id, url, type) {
	var type = type || 'edit';
	var options = {
			type: 2,
			title: title,
			content: url + ".html?id=" + id + '&type=' + type + '&code=' + code
	}
	myOpenLayer = layer.open(options)
	layer.full(myOpenLayer)
}


function myOpen1(id, url) {
	var type ='';
	var options = {
			type: 2,
			title: '详情',
			content: url + ".html?id=" + id + '&type=' + type + '&code=' + code
	}
	myOpenLayer = layer.open(options)
	layer.full(myOpenLayer)
}
