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
	            	   "data": "moduleTitle",//接口对应字段
	            	   "title": "模块标题",//表头名
	            	   "defaultContent": "-",//默认显示					
	               },	
	               {
	            	   targets: [3], //第几列
	            	   data: "fileList", //接口对应字段
	            	   title: "模块图标", //表头名
	            	   defaultContent: "-", //默认显示	
	            	   "render": function (data) {
	            		   var domain = window.lny.domain;
	            		   var dom = '';	            
	            		   var fileUrl =data[0].moduleImgUrl,
	            		   fileName = data[0].moduleImgName; 
	            		   dom += '<li style="list-style:none;">';								   
	            		   dom += '<img style="width:40px;" src=' + fileUrl + '/>'; 
	            		   dom += '</li>'; 	            		   
	            		   return dom;
	            	   }
	               },
	               {
	            	   "targets": [4],//第几列
	            	   "data": "moduleType",//接口对应字段
	            	   "title": "模块类型",//表头名   0:全公司  1：机关  2：项目
	            	   "defaultContent": "-",//默认显示
	            	   "render": function (data) {
	            		   var text = ""
	            			   switch (data) {
	            			   case "0": text = "链接";
	            			   break;
	            			   case "1": text = "二维码";
	            			   break;	            		
	            			   default: text = "未知";
	            			   break;
	            			   }
	            		   return text
	            	   }
	               },
	               {
	            	   "targets": [5],//第几列
	            	   "data": "webLink",//接口对应字段
	            	   "title": "链接",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [6],//第几列
	            	   "data": "sort",//接口对应字段
	            	   "title": "排序",//表头名
	            	   "defaultContent": "-",//默认显示
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
	            			   if(data.moduleType == '0'){
	            				   html += "---";
	            			   }else{  
	            				   html += '<a style="color:blue;"  href="javascript:void(0);" onclick="myOpen1(\' ' + data.rowIndex + '\',\'' + 'subpage' + '\')" href="javascript:void(0);">子页面</a>';
	            				   //html += '<a style="color:blue;"  href="javascript:void(0);" onclick="myOpen(\'' + data.moduleId + '\',\'' + 'zjHwModuleSubList' + '\',\'' + '子页面' + '\')" href="javascript:void(0);">子页面</a>';
	            			   } 					   
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
	            	 name: "moduleTitle",//输入字段名
	            	 label: "模块标题",//输入字段对杨的中文名称
	            	 placeholder: "请输入",
	             },
	             {
	            	 type: "select",
	            	 name: "moduleType",
	            	 label: "模块类型",
	            	 selectList: [//当类型为select时，option配置					
	            	              {
	            	            	  value: "",
	            	            	  text: "全部"
	            	              },
	            	              {
	            	            	  value: "0",
	            	            	  text: "链接"
	            	              },
	            	              {
	            	            	  value: "1",
	            	            	  text: "二维码"
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
var pager = $("#pager").page({
	remote: {//ajax请求配置
		url: l.getApiUrl("getZjHwModuleList"),
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
//新增 && 编辑
var detailLayer = $('#detailLayer').detailLayer({
	layerArea:['90%', '70%'],
	conditions: [
	             {
	            	 type: "hidden",//五种形式：text,select,date,hidden,textarea,
	            	 name: "moduleId",//输入字段名
	             },
	             {
	            	 type: "select",
	            	 name: "moduleType",
	            	 label: "模块类型",
	            	 must: true,
					 immutable:true,
	            	 selectList: [//当类型为select时，option配置  0:全公司  1：机关  2：项目
	            	              {
	            	            	  value: "",
	            	            	  text: "---"
	            	              },
	            	              {
	            	            	  value: "0",
	            	            	  text: "链接"
	            	              },
	            	              {
	            	            	  value: "1",
	            	            	  text: "二维码"
	            	              }
	            	              ],
	             },
	             {
	            	 type: "text",//三种形式：text,select,date,
	            	 name: "moduleTitle",//输入字段名
	            	 label: "模块标题",//输入字段对杨的中文名称
	            	 placeholder: "请输入",
	            	 must: true
	             },	
	             {
	            	 type: "upload",//
	            	 name: "fileList",//
	            	 label: "模块图标",//
	            	 btnName: "添加图标",
	            	 projectName: "zj-hw-nav",
	            	 uploadUrl:"uploadFilesByFileName",
	            	 maxLen: 1,
	            	 must: true,
	            	 fileType: ["jpg", "jpeg", "png", "gif"]
	             },
	             {
	            	 type: "text",//三种形式：text,select,date,
	            	 name: "webLink",//输入字段名
	            	 label: "链接",//输入字段对杨的中文名称
	            	 placeholder: "请输入",
	            	 must: true
	             },
	             {
	            	 type: "text",//三种形式：text,select,date,
	            	 name: "sort",//输入字段名
	            	 label: "排序",//输入字段对杨的中文名称
	            	 placeholder: "请输入"
	             }	
	             ],
	             onSave: function (obj, _params) {
	            	 var sortFormat = _params.sort;//排序格式限制
	            	 var yearGs = /^[Z0-9 \d+(\.\d+)?]{1,4}$/.test(sortFormat);//年格式限制        
	            	 if (yearGs == false) {
	            		 layer.alert("只能输入数字且最大长度为4", { icon: 0 }, function (index) {
	            			 layer.close(index);
	            		 });
	            	 } else {
	            		 l.ajax('updateZjHwModule', _params, function (data) {
	            			 pager.page('remote')
	            			 obj.close()
	            		 })   
	            	 }	   
	             },
	             onAdd: function (obj, _params) {
	            	 var sortFormat = _params.sort;//年格式限制	
	            	 var yearGs = /^[Z0-9 \d+(\.\d+)?]{1,4}$/.test(sortFormat);//年格式限制     
	            	 if (yearGs == false) {
	            		 layer.alert("只能输入数字且最大长度为4", { icon: 0 }, function (index) {
	            			 layer.close(index);
	            		 });
	            	 } else {
	            		 l.ajax('addZjHwModule', _params, function (data) {
	            			 pager.page('remote')
	            			 obj.close()
	            		 })   
	            	 }	   		      
	             }
})
var moduleDetailId = ""
//子页面新增
var subAdd = $('#subAdd').detailLayer({
	layerArea:['70%', '60%'],
	conditions: [
	             {
	            	 type: "hidden",//五种形式：text,select,date,hidden,textarea,
	            	 name: "moduleDetailId",//输入字段名
	             },
	             {
	            	 type: "hidden",//五种形式：text,select,date,hidden,textarea,
	            	 name: "moduleId",//输入字段名
	             },
	             {
	            	 type: "text",//三种形式：text,select,date,
	            	 name: "detailTitle",//输入字段名
	            	 label: "详情标题",//输入字段对杨的中文名称
	            	 placeholder: "请输入",
					 immutable:moduleDetailId ? true : false,
					// immutableAdd : true,
	            	 must: true
	             },	
	             {
	            	 type: "upload",//
	            	 name: "fileList",//
	            	 label: "详情图标",//
	            	 btnName: "添加图标",
	            	 projectName: "zj-hw-nav",
	            	 uploadUrl:"uploadFilesByFileName",
	            	 maxLen: 1,
	            	 must: true,
					 immutable:moduleDetailId ? true : false,
	            	 fileType: ["jpg", "jpeg", "png", "gif"]
	             },
	             {
	            	 type: "text",//三种形式：text,select,date,
	            	 name: "webLink",//输入字段名
	            	 label: "链接",//输入字段对杨的中文名称
	            	 placeholder: "请输入",
					 immutable:moduleDetailId ? true : false,
	            	 must: true
	             }	            
	             ],
	             onSave: function (obj, _params) {
	            	 l.ajax('addZjHwModuleDetail', _params, function (data) {
	            		 pager.page('remote')
	            		 obj.close()
	            	 })  
	             },
	             onAdd: function (obj, _params) {
	            	 l.ajax('addZjHwModuleDetail', _params, function (data) {
	            		 pager.page('remote')
	            		 obj.close()
	            	 })   		      
	             }
})
//子页面查看
var subDetail = $('#subDetail').detailLayer({
	layerArea:['70%', '60%'],
	conditions: [
	             {
	            	 type: "hidden",//五种形式：text,select,date,hidden,textarea,
	            	 name: "moduleDetailId",//输入字段名
	             },
	             {
	            	 type: "hidden",//五种形式：text,select,date,hidden,textarea,
	            	 name: "moduleId",//输入字段名
	             },
	             {
	            	 type: "text",//三种形式：text,select,date,
	            	 name: "detailTitle",//输入字段名
	            	 label: "详情标题",//输入字段对杨的中文名称
	            	 placeholder: "请输入",
	            	 immutableAdd: true
	             },	
	             {
	            	 type: "upload",//
	            	 name: "fileList",//
	            	 label: "详情图标",//
	            	 btnName: "添加图标",
	            	 projectName: "zj-hw-nav",
	            	 uploadUrl:"uploadFilesByFileName",
	            	 maxLen: 1,
	            	 immutableAdd: true,
	            	 fileType: ["jpg", "jpeg", "png", "gif"]
	             },
	             {
	            	 type: "text",//三种形式：text,select,date,
	            	 name: "webLink",//输入字段名
	            	 label: "链接",//输入字段对杨的中文名称
	            	 placeholder: "请输入",
	            	 immutableAdd: true
	             }	            
	             ],
})
$("body").on("click", "button", function () {
	var checkedData = l.getTableCheckedData(table)
	var name = $(this).attr("data-name");
	switch (name) {
	case "add":
		detailLayer.open();
		break;				
	case "edit":
		if (checkedData.length == 1) {
			detailLayer.open(checkedData[0]);         
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
		} else {			
			layer.confirm("确定删除？", { icon: 0, }, function (index) {
				l.ajax("batchDeleteUpdateZjHwModule", checkedData, function () {
					pager.page('remote')
				})
				layer.close(index);
			});								   
		}
		break;		
	}
})

function myOpen1(index, type) {
	var rowData = allData[Number(index)];
	var params;
	switch (type) {
	case 'subpage':
		params = {};
		params.moduleId = rowData.moduleId;
		l.ajax('getZjHwModuleDetailByModuleId', params, function (res) {		
			if(res.moduleDetailId == ""){//新增
				subAdd.open(params);
			}else{//查看
				subDetail.open(res);
				$('#subDetail .btn').hide();
			}                        
		})
		break;
	}
}


var myOpenLayer;
function myOpen( id, url,title) {
	var options = {
			type: 2,
			title: title,
			content: url + ".html?id=" + id+"&code="+code
	};
	myOpenLayer = layer.open(options);
	layer.full(myOpenLayer);
}