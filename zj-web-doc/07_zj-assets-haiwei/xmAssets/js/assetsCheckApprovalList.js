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
			"data": "sszclx1Name",//接口对应字段
			"title": "资产大类",//表头名
			"defaultContent": "-"//默认显示
		},
		{
			"targets": [3],//第几列
			"data": "sszclx2Name",//接口对应字段
			"title": "资产小类",//表头名
			"defaultContent": "-",//默认显示
			"render": function (data, index, row) {
				var a;
				if (data) {
					a = '<a style="color:blue;" onclick="myOpen1(\' ' + row.rowIndex + '\',\'' + 'bf' + '\')" href="javascript:void(0);">' + data + '</a>'
				}
				return a;
			}
		},
		{
			"targets": [4],//第几列
			"data": "apih5FlowStatus",//接口对应字段
			"title": "审核状态",//表头名   0:全公司  1：机关  2：项目
			"defaultContent": "-",//默认显示
			"render": function (data) {
				var text = ""
				switch (data) {
					case "1": text = "审核中";
						break;
					case "2": text = "通过";
						break;
					case "3": text = "驳回";
						break;
					default: text = "未知";
						break;
				}
				return text;
			}
		},
		{
			"targets": [5],//第几列
			"data": "appTime",//接口对应字段
			"title": "申请时间",//表头名
			"defaultContent": "-",//默认显示
			"render": function (data) {
				return l.dateFormat(new Date(data), "yyyy-MM-dd");
			}
		}
	]
});

var form = $('#form').filterfrom({
	conditions: [//表单项配置
		{
			type: "select",
			name: "sszclx1",
			label: "资产大类",
			ajax: {//如果需要接口获取数据需要添加该属性
				api: "getAssetsTypeTreeAllList",//api名称
				child: "sszclx2",//如果有联动下拉，设置被他控制的下拉name
				valueName: "recordid",//设置value对应的接口字段名称
				textName: "assetTypeIdAndName",//设置text对应的接口字段名称
				childrenName: "currentList",//设置children对应的接口字段名称
			},
		},
		{
			type: "select",
			name: "sszclx2",
			label: "资产小类",
			ajax: {
				parent: "sszclx1",//如果有联动下拉，设置他被控制的下拉name
				valueName: "recordid",
				textName: "assetTypeIdAndName",
			},
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
		url: l.getApiUrl("getZjHwzcZcglNeedApproveList"),
		params: {
			zcztdm: "2"
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
	layerArea: ['100%', '100%'],
	conditions: [
		{
			type: "select",
			name: "typeAssets",
			label: "资产类别",
			immutableAdd: true,
			selectList: [//当类型为select时，option配置  0:全公司  1：机关  2：项目
				{
					value: "",
					text: "---"
				},
				{
					value: "0",
					text: "行政固定资产"
				},
				{
					value: "1",
					text: "信息化固定资产"
				},
				{
					value: "2",
					text: "实验器材、测量仪器"
				}
			],
		},
		{
			type: "pickTree",//
			name: "oaSydw",//接口字段名
			label: "使用单位",//
			immutableAdd: true,
			pickType: {
				department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
				member: false,//成员列表对应接口字段名,false表示不开启该类
			}
		},
		{
			type: "date",//
			name: "appTime",//
			label: "申请时间",//
			dateFmt: "yyyy-MM-dd",
			immutableAdd: true,
			id: "appTime"
		},
		{
			type: "select",
			name: "sszclx1",
			label: "资产大类",
			selectList: [//当类型为select时，option配置
			],
			ajax: {
				api: "getAssetsTypeTreeAllList",
				child: "sszclx2",
				valueName: "recordid",
				textName: "assetTypeIdAndName",
				childrenName: "currentList",
			},
			immutableAdd: true
		},
		{
			type: "select",
			name: "sszclx2",
			label: "资产小类",
			selectList: [//当类型为select时，option配置
			],
			ajax: {
				parent: "sszclx1",
				valueName: "recordid",
				textName: "assetTypeIdAndName",
			},
			immutableAdd: true
		},
		{
			type: "text",//
			name: "ggxh",//
			label: "规格型号",//
			placeholder: "请输入规格型号",
			immutableAdd: true
		},
		{
			type: "number",//
			name: "quantity",//
			label: "数量",//		    	                	
			placeholder: "请输入数量",
			immutableAdd: true
		},
		{
			type: "text",//
			name: "zcyz",//
			label: "预估单价（元）",//
			placeholder: "请输入预估单价（元）",
			immutableAdd: true
		},
		{
			type: "text",//
			name: "brand",//
			label: "厂家品牌",//
			placeholder: "请输入厂家品牌",
			immutableAdd: true
		},
		{
			type: "text",//
			name: "syr",//
			label: "使用人",//
			placeholder: "请输入使用人",
			immutableAdd: true
		},
		{
			type: "select",//
			name: "sybmid",//
			label: "使用部门",
			placeholder: "请输入使用部门",
			selectList: [//当类型为select时，option配置
			],
			ajax: {
				api: "getDepartmentSelectAllList",
				valueName: "recordid",
				textName: "bmmc",
			},
			immutableAdd: true
		},
		{
			type: "number",
			name: "czl",
			label: "残值率（%）",
			immutableAdd: true
		},
		{
			type: "number",//
			name: "synx",//
			label: "使用年限（年）",//
			placeholder: "请输入使用年限（年）",
			immutableAdd: true
		},
		{
			type: "textarea",//
			name: "appReason",//
			label: "申购原因",//
			placeholder: "请输入申购原因",
			immutableAdd: true
		},
		{
			type: "upload",//
			name: "imageList",//
			label: "资产图片",//
			btnName: "添加图片",
			projectName: "zj-xm-assets",
			fileType: ["jpg", "jpeg", "png", "gif"],
			immutableAdd: true
		}
	],
})

$("body").on("click", "button", function () {
	var checkedData = l.getTableCheckedData(table);
	var name = $(this).attr("data-name");
	switch (name) {
		case "fixCheck":
			if (checkedData.length == 1) {
				myOpen('验收申请', checkedData[0], 'assetsFixCheckApproval')
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

function myOpen(title, data, url) {
	var options = {
		type: 2,
		title: title,
		content: url + ".html?workId=" + data.workId + "&nodeId=" + data.nodeId + "&code=" + code + "&sszcid=" + data.recordid
	}
	myOpenLayer = layer.open(options)
	layer.full(myOpenLayer)
}

function myOpen1(index, type) {
	var rowData = allData[Number(index)];
	var params;
	switch (type) {
		case 'bf':
			params = {};
			params.recordid = rowData.recordid;
			l.ajax('getZjHwzcZcglDetails', params, function (res) {
				detailLayer.open(res);
				$('#detailLayer .btn').hide();
			})
			break;
	}
}