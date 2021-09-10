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
	            				   dom += '<a style="width:40px;color:blue;" href=' + fileUrl + '>'+ fileName +'</a>'; 
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
						   return data ? l.dateFormat(new Date(data), "yyyy-MM-dd") : "-";
	            	   }
	               },
	               {
	            	   "targets": [7],//第几列
	            	   "width": 80,
	            	   "data": function (row) {
	            		   return row
	            	   },//接口对应字段
	            	   "title": "操作",//表头名
	            	   "render": function (data) {
	            		   var html = '';
	            		   html += '<span class="dropDown">'
	            			   html += '<a style="color:blue;"  href="javascript:void(0);" onclick="myOpen1(\' ' + data.rowIndex + '\',\'' + 'xq' + '\')" href="javascript:void(0);">回复</a>';
	            		   html += '</span>'	
	            			   return html;
	            	   }
	               }
	               ]
});
var allData;
var pager = $("#pager").page({
	remote: {//ajax请求配置
		url: l.getApiUrl("getZjLhddJointSupCheckDetailList"),
		params: {
			taskId: l.getUrlParam("id")
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
	conditions: [
	             {
	            	 type: "hidden",//五种形式：text,select,date,hidden,textarea,
	            	 name: "checkReplyId",//输入字段名
	             },
	             {
	            	 type: "hidden",//五种形式：text,select,date,hidden,textarea,
	            	 name: "checkDetailId",//输入字段名
	            	 defaultValue:l.getUrlParam("id"),
	             },
	             {
	            	 type: "text",//
	            	 name: "checkItem",//
	            	 label: "检查项",//
	            	 defaultValue:l.getUrlParam("checkItem") ,
	            	 immutableAdd:true,
	            	 must: true
	             }, 
				 {
	            	 type: "textarea",//
	            	 name: "requirInfo",//
	            	 label: "所需资料说明",//
	            	 placeholder: "请输入所需资料说明",
	            	 immutableAdd: true
	             },
	             {
	            	 type: "textarea",//
	            	 name: "replyContent",//
	            	 label: "回复内容",//
	            	 placeholder: "请输入回复内容",
	            	 must: true
	             },
	             {
	            	 type: "upload",//
	            	 name: "replyFileList",//
	            	 label: "回复附件",//
	            	 btnName: "添加附件",
	            	 projectName: "zj-work-check",
	            	 uploadUrl: 'uploadFilesByFileName',
	            	 fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
	             }
	             ],
				 
				 onSave: function (obj, _params) {
        if (_params.checkReplyId) {
          l.ajax('updateZjLhddJointSupCheckReply', _params, function (data) {
	            		 pager.page('remote')
	            		 obj.close()
	            	 })
        } else {
             l.ajax('addZjLhddJointSupCheckReply', _params, function (data) {
	            		 pager.page('remote')
	            		 obj.close()
	            	 })
        }
    }
})

function myOpen1(index, type) {
	var rowData = allData[Number(index)];
	switch (type) {
	case 'xq':
		detailLayer.open(rowData);
		break;
	}
}