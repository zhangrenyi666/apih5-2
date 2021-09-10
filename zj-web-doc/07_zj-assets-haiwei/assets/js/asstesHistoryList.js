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
	            	   "targets": [0],//第几列
	            	   "data": "sszclx2Name",//接口对应字段
	            	   "title": "资产小类",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [1],//第几列
	            	   "data": "zcbh",//接口对应字段
	            	   "title": "资产编号",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [2],//第几列
	            	   "data": "zcmc",//接口对应字段
	            	   "title": "资产名称",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [3],//第几列
	            	   "data": "cwbh",//接口对应字段
	            	   "title": "财务编号",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [4],//第几列
	            	   "data": "xzldmName",//接口对应字段
	            	   "title": "残值率",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [5],//第几列
	            	   "data": "cfdd1",//接口对应字段
	            	   "title": "存放地点",//表头名
	            	   "defaultContent": "-"//默认显示
	               },
	               {
	            	   "targets": [6],//第几列
	            	   "data": "sybmName",//接口对应字段
	            	   "title": "使用部门",//表头名
	            	   "defaultContent": "-"//默认显示
	               },
	               {
	            	   "targets": [7],//第几列
	            	   "data": "grrq",//接口对应字段
	            	   "title": "购入日期",//表头名
	            	   "defaultContent": "-",//默认显示
	            	   "render": function (data) {
	            		   return l.dateFormat(new Date(data), "yyyy-MM-dd");
	            	   }
	               },
	               {
	            	   "targets": [8],//第几列
	            	   "data": "modifyUserName",//接口对应字段
	            	   "title": "操作员",//表头名
	            	   "defaultContent": "-",//默认显示
	               },
	               {
	            	   "targets": [9],//第几列
	            	   "data": "createTime",//接口对应字段
	            	   "title": "操作时间",//表头名
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
	            	   "title": "操作",//表头名
	            	   "render": function (data) {
	            		   var html = '';
	            		   html += '<span class="dropDown">'
	            			   html += '<a style="color:blue;" href="javascript:void(0);" onclick="myOpen(\'资产详情列表\',\'' + data.recordid + '\',\'' + 'assetDetailsList' + '\')">详情</a>';
	            		   html += '</span>'
	            			   return html;
	            	   }
	               }
	               ]
});
var pager = $("#pager").page({
	remote: {//ajax请求配置
		url: l.getApiUrl("getAssetsList"),
		params: {
			zcbh:"",
			cwbh:""
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

var form = $('#form').filterfrom({
	conditions: [//表单项配置
	             {
	            	 type: "text",//三种形式：text,select,date,
	            	 name: "zcbh",//输入字段名
	            	 label: "资产编号",//输入字段对杨的中文名称
	            	 placeholder: "请资产资产编号",
	             },
	             {
	            	 type: "text",//三种形式：text,select,date,
	            	 name: "cwbh",//输入字段名
	            	 label: "财务编号",//输入字段对杨的中文名称
	            	 placeholder: "请输入财务编号",
	             },
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
	            	 pager.page('remote',0, _params)
	             },
	             onReset: function (arr) {//重置按钮回调
	            	 var _params = {};
	            	 for (var i = 0; i < arr.length; i++) {
	            		 _params[arr[i].name] = arr[i].value;
	            	 }
	            	 pager.page('remote', _params)
	             }
})

var detailLayer = $('#detailLayer').detailLayer({
	conditions: [
	             ],
	             onSave: function (obj, _params) {
	            	 l.ajax('updateAsstesHistory', _params, function (data) {
	            		 pager.page('remote')
	            		 obj.close()
	            	 })
	             },
	             onAdd: function (obj, _params) {
	            	 l.ajax('addAsstesHistory', _params, function (data) {
	            		 pager.page('remote')
	            		 obj.close()
	            	 })
	             }
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
			l.delTableRow("recordid", 'currentList', 'batchDeleteAsstesHistory', checkedData, function (data) {
				pager.page('remote')
			})//  删除url地址
		}
		break;
	}
})
function myOpen(title, id, url) {
	var options = {
		type: 2,
		title: title,
		content: url + ".html?id=" + id+"&code="+code
	}
	layer.full(layer.open(options))
}
