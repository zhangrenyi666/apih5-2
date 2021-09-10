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
	            			   a = '<a style="color:blue;" onclick="myOpen1(\' '+row.rowIndex+ '\',\'' + 'bf' + '\')" href="javascript:void(0);">'+data+'</a>'
	            		   }
	            		   return a;
	            	   }
	               },
	               {
	            	   "targets": [3],//第几列
	            	   "data": "informant",//接口对应字段
	            	   "title": "填报人",//表头名
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
	            		   return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
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
	            			   default: text = "未知";
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
			oneFlag:"0"		
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
	            	 type: "text",//
	            	 name: "serialNumber",//
	            	 label: "编号",//
	            	 placeholder: "请输入编号",
	            	 immutableAdd:true
	             },
	             {
	            	 type: "text",//
	            	 name: "title",//
	            	 label: "标题",//
	            	 placeholder: "请输入标题",
	            	 immutableAdd:true
	             },
	             {
	            	 type: "text",//
	            	 name: "applyDepName",//
	            	 label: "主责部门",//
	            	 placeholder: "请输入主责部门",
	            	 immutableAdd:true
	             },		
	             {
	            	 type: "pickTree",//
	            	 name: "oaNeedDep",//接口字段名
	            	 label: "配合的部门",//
	            	 immutableAdd:true,
	            	 pickType: {
	            		 department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
	            		 member: false,//成员列表对应接口字段名,false表示不开启该类
	            	 }
	             },
				 {
		    	                	type: "pickTree",//
		    	                	name: "oaCheckObject",//接口字段名
		    	                	label: "检查对象",//
		    	                	immutableAdd: true,
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
	            	 immutableAdd:true
	             },
	             {
	            	 type: "date",//
	            	 name: "informantTime",//
	            	 label: "填报人时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id:"informantTime",
	            	 immutableAdd:true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "opinionField1",//
	            	 label: "部门总经理审批",//
	            	 placeholder: "请输入",
	            	 immutableAdd:true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "opinionField2",//
	            	 label: "监督委员牵头部门批复",//
	            	 placeholder: "请输入",
	            	 immutableAdd:true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "opinionField3",//
	            	 label: "监督委员会分管领导批",//
	            	 placeholder: "请输入",
	            	 immutableAdd:true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "opinionField4",//
	            	 label: "审计部联络人批复",//
	            	 placeholder: "请输入",
	            	 immutableAdd:true
	             },
	             {
	            	 type: "upload",//
	            	 name: "fileList",//
	            	 label: "附件",//
	            	 btnName: "添加附件",
	            	 projectName:"zj-work-check",
	            	 fileType: ["jpg", "jpeg", "png", "gif"],
	            	 immutableAdd:true
	             }
	             ],
})



$("body").on("click", "button", function () {
	var checkedData = l.getTableCheckedData(table);
	var name = $(this).attr("data-name");
	switch (name) {
	case "add":
		myOpen('联合督导申请新增','','jointApplyApprove')
		break;
	case "otherAdd":
		myOpen('督导申请新增','','jointApplyOtherApprove')
		break;	
	}
	
})

function myOpen(title, id, url) {
	var options = {
			type: 2,
			title: title,
			content: url + ".html?id=" + id+"&code="+code

	}
	myOpenLayer = layer.open(options)
	layer.full(myOpenLayer)
}

function myOpen1(index, type) {
	var rowData = allData[Number(index)];
	var params;
	switch (type){
	case 'bf':
		params = {};
		params.applyId = rowData.applyId;            
		l.ajax('getZjLhddJointSupApplyDetailsByWorkId',params,function(res){
			detailLayer.open(res);
			$('#detailLayer .btn').hide();
		})
		break;	
	}
}