var code = Lny.getUrlParam('code')
Lny.setCookie('code',code,30)
var detailForm = $('#detailForm').detailLayer({
	conditions: [
	             {
	            	 type: "hidden",//
	            	 name: "taskId",//
	            	 label: "主键id",//
	            	 placeholder: "",
	             },
				 {
	            	 type: "hidden",//
	            	 name: "taskType",//
	            	 label: "任务类型",//0：发起 1：创建
					 defaultValue:"1"
	             },
	             {
	            	 type: "select",
	            	 name: "taskSourceId",
	            	 label: "任务来源",
					 must: true,
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
	            	 type: "textarea",//
	            	 name: "taskMatter",//
	            	 label: "任务事项",//
	            	 placeholder: "请输入任务事项",
	            	 must: true
	             },
	             {
	            	 type: "date",//
	            	 name: "startTime",//
	            	 label: "任务期限开始时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id: "startTime",
	            	 must: true
	             },
	             {
	            	 type: "date",//
	            	 name: "endTime",//
	            	 label: "任务期限结束时间",//
	            	 dateFmt: "yyyy-MM-dd",
	            	 defaultValue: new Date(),
	            	 id: "endTime",
	            	 must: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "content",//
	            	 label: "内容摘要",//
	            	 placeholder: "请输入内容摘要",
	            	 must: true
	             },
	             {
	            	 type: "pickTree",//
	            	 name: "oaUndertakeDept",//接口字段名
	            	 label: "承办部门",//
	            	 must: true,
	            	 pickType: {
	            		 department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
	            		 member: false,//成员列表对应接口字段名,false表示不开启该类
	            	 }
	             },
	             {
	            	 type: "pickTree",//
	            	 name: "oaLeader",//接口字段名
	            	 label: "负责人",//
	            	 must: true,
	            	 pickType: {
	            		 department: false,//部门列表对应接口字段名,false表示不开启该类
	            		 member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
	            	 }
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
	            	 type: "upload",//
	            	 name: "fileList",//
	            	 label: "附件",//
	            	 btnName: "添加附件",
	            	 projectName: "zj-oatask",
	            	 uploadUrl: 'uploadFilesByFileName',
	            	 fileType: ["jpg","jpeg","png","gif","docx","xls","doc","ppt","xlsx","pptx","xlsm","pdf"]
	             }
	             ],
	             onSave: function (obj,_params) {
	            	 l.ajax('addZjOatask',_params,function (data) {
	            		 window.opener = null; 
	            		 window.open("","_self",""); 
	            		 window.close();
	            		 if (window) { 
	            			 window.location.href = "about:blank"; 
	            		 }
	            	 })
	             },
	             onAdd: function (obj,_params) {
	            	 l.ajax('addZjOatask',_params,function (data) { 
	            		 window.opener = null; 
	            		 window.open("","_self",""); 
	            		 window.close();
	            		 if (window) { 
	            			 window.location.href = "about:blank"; 
	            		 }
	            	 })
	             }
})

detailForm.close = function () {
	parent.layer.close(parent.myOpenLayer)
}

l.ajax('getLoginUserInfo',{},function (data,message,success) {
	if (success) {
		var _data = data[0];
		var _d = {
				oaUndertakeDept: _data.oaUndertakeDept,
				oaAuditor: _data.oaLeader,
		};
		detailForm.setOpenData(_d);
	} else {
		layer.alert(message,{ icon: 0,},function (index) {
			layer.close(index);
		});
	}
})
