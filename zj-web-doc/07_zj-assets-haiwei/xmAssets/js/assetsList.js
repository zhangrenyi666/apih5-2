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
			"data": "typeAssets",//接口对应字段
			"title": "资产类别",//表头名   0:行政固定资产  1：信息化固定资产  2：实验器材、测量仪器
			"defaultContent": "-",//默认显示
			"render": function (data) {
				var text = ""
				switch (data) {
					case "0": text = "行政固定资产";
						break;
					case "1": text = "信息化固定资产";
						break;
					case "2": text = "实验器材、测量仪器";
						break;
					default: text = "未知";
						break;
				}
				return text
			}
		},
		{
			"targets": [3],//第几列
			"data": "sszclx1Name",//接口对应字段
			"title": "资产大类",//表头名
			"defaultContent": "-",//默认显示
		},
		{
			"targets": [4],//第几列
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
			"targets": [5],//第几列
			"data": "zcbh",//接口对应字段
			"title": "资产编号",//表头名
			"defaultContent": "-",//默认显示			
		},
		{
			"targets": [6],//第几列
			"data": "cwbh",//接口对应字段
			"title": "财务编码",//表头名
			"defaultContent": "-",//默认显示			
		},
		{
			"targets": [7],//第几列
			"data": "sydwName",//接口对应字段
			"title": "使用单位",//表头名
			"defaultContent": "-",//默认显示
		},
		{
			"targets": [8],//第几列
			"data": "zcztName",//接口对应字段
			"title": "资产状态",//表头名
			"defaultContent": "-",//默认显示
		},
		{
			"targets": [9],//第几列
			"data": "wxztdmName",//接口对应字段
			"title": "维修状态",//表头名
			"defaultContent": "-",//默认显示
		},
		{
			"targets": [10],//第几列
			"data": "createTime",//接口对应字段
			"title": "操作时间",//表头名
			"defaultContent": "-",//默认显示
			"render": function (data) {
				return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
			}
		},
		{
			"targets": [11],//第几列
			"width": 80,
			"data": function (row) {
				return row
			},//接口对应字段
			"title": "操作",//表头名
			"render": function (data) {
				var html = '';
				html += '<span class="dropDown">'
				html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">操作</a>';
				html += '<ul class="dropDown-menu menu radius box-shadow">'

				if (data.cwbh == "") {//财务编码为空时请录入财务编码
					html += '<li><a href="javascript:void(0);" onclick="editTableRow(' + data.rowIndex + ')">请录入财务编码</a></li>';
				} else {
					if (data.zcztdm == "1") {//正常使用                 
						if (data.wxztdm == "2" || data.wxztdm == null || data.wxztdm == "") {//维修状态 完成
							html += '<li><a href="javascript:void(0);" onclick="myOpen(\'资产维修新增\',\'' + data.recordid + '\',\'' + 'assetsRepairList' + '\')">资产维修</a></li>';
						} else {
							html = '-';
						}
					} else if (data.zcztdm == "3") {//已报废
						html = '-';
					} else if (data.zcztdm == "4") {//闲置
						html = '<li><a href="javascript:void(0);" onclick="myOpen(\'资产调拨\',\'' + data.recordid + '\',\'' + 'assetsXZList' + '\')">资产恢复</a></li>';
					}
				}
				html += '</ul></span>'
				return html;
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
		},
		{
			type: "text",//三种形式：text,select,date,
			name: "zcbh",//输入字段名
			label: "资产编号",//输入字段对杨的中文名称
			placeholder: "请输入资产编号",
		},
		{
			type: "text",//三种形式：text,select,date,
			name: "sybmid",//输入字段名
			label: "使用部门",//输入字段对杨的中文名称
			placeholder: "请输入使用部门",
		},
		{
			type: "text",//三种形式：text,select,date,
			name: "sydwid",//输入字段名
			label: "使用单位",//输入字段对杨的中文名称
			placeholder: "请输入使用单位",
		},
		{
			type: "text",//三种形式：text,select,date,
			name: "syr",//输入字段名
			label: "使用人",//输入字段对杨的中文名称
			placeholder: "请输入使用人",
		},
		{
			type: "select",
			name: "zcztdm",
			label: "资产状态",
			ajax: {//如果需要接口获取数据需要添加该属性
				api: "getAssetsStateSelectList",//api名称
				valueName: "dmbh",//设置value对应的接口字段名称
				textName: "dmnr",//设置text对应的接口字段名称
			},
		},
		{
			type: "date",//text,select,date,
			name: "startTime",
			label: "购入日期开始",
			//defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd"),
			maxDate: "\'#F{$dp.$D(\\\'" + "logmax" + "\\\')||\\\'%y-%M-%d\\\'}\'",
			id: "logmin"
		},
		{
			type: "date",//text,select,date,
			name: "endTime",
			label: "购入日期结束",
			//defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd"),
			//maxDate: "\'#F{$dp.$D(\\\'"+"logmin"+"\\\')||\\\'%y-%M-%d\\\'}\'",
			id: "logmax"
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
		url: l.getApiUrl("getZjHwzcZcglList"),
		params: {
			assetsListFlag: "1" //查看不是验收中的所有资产
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

var editDetailLayer = $('#editDetailLayer').detailLayer({
	layerArea: ['100%', '100%'],
	conditions: [
		{
			type: "hidden",//五种形式：text,select,date,hidden,textarea,
			name: "recordid",//输入字段名
		},
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
			type: 'text',//
			name: "cwbh",//
			label: "财务编码",//
			placeholder: "请输入财务编码",
			must: true
		},
		{
			type: "date",//
			name: "rzrq",//
			label: "入账日期",//
			dateFmt: "yyyy-MM-dd",
			defaultValue: new Date(),
			id: "rzrq",
			must: true
		},
		{
			type: "select",
			name: "zcyt",
			label: "折旧类型",
			must: true,
			selectList: [//当类型为select时，option配置  0:全公司  1：机关  2：项目
				{
					value: "",
					text: "---"
				},
				{
					value: "0",
					text: "上月折旧"
				},
				{
					value: "1",
					text: "本月折旧"
				},
				{
					value: "2",
					text: "下月折旧"
				}
			],
		},
		{
			type: "text",//
			name: "zcyz",//
			label: "资产原值（元）",//
			placeholder: "请输入资产原值（元）",
			immutableAdd: true
		},
		{
			type: "number",//
			name: "czl",//
			label: "残值率（%）",//
			placeholder: "请输入残值率（%）",
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
			type: "text",//
			name: "ggxh",//
			label: "规格型号",//
			placeholder: "请输入规格型号",
			immutableAdd: true
		},
		{
			type: "select",
			name: "grfsdm",
			label: "购入方式",
			immutableAdd: true,
			selectList: [//当类型为select时，option配置
			],
			ajax: {
				api: "getBuyMannerSelectAllList",
				valueName: "dmbh",
				textName: "dmnr"
			},
		},
		{
			type: "select",//
			name: "glryid",//
			label: "管理员",
			immutableAdd: true,
			placeholder: "请输入",
			selectList: [//当类型为select时，option配置
			],
			ajax: {
				api: "getZjHwzcGlyList",
				valueName: "recordid",
				textName: "glyName",
			}
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
			type: "select",//
			name: "sybmid",//
			label: "使用部门",
			placeholder: "请输入使用部门",
			immutableAdd: true,
			selectList: [//当类型为select时，option配置
			],
			ajax: {
				api: "getDepartmentSelectAllList",
				valueName: "recordid",
				textName: "bmmc",
			}
		},
		{
			type: "text",
			name: "cfdd1",
			label: "存放地点",
			placeholder: "请输入存放地点",
			immutableAdd: true
		},
		{
			type: "text",//
			name: "syr",//接口字段名
			label: "使用人",//
			placeholder: "请输入使用人",
			immutableAdd: true
		},
		{
			type: "text",//
			name: "brand",//接口字段名
			label: "厂商品牌",//
			placeholder: "请输入厂商品牌",
			immutableAdd: true
		},
		{
			type: "textarea",//
			name: "bz",//
			label: "备注",//
			placeholder: "请输入备注",
			immutableAdd: true
		},
		{
			type: "upload",//
			name: "imageList",//
			label: "资产图片",//
			btnName: "添加图片",
			projectName: "zj-assets-haiwei",
			immutableAdd: true,
			fileType: ["jpg", "jpeg", "png", "gif"]
		}
	],
	onSave: function (obj, _params) {
		l.ajax('updateZjHwzcZcglForCWBH', _params, function (_params) {
			pager.page('remote');
			obj.close()
		})
	}
});

var detailLayer = $('#detailLayer').detailLayer({
	layerArea: ['100%', '100%'],
	conditions: [
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
			type: 'text',//
			name: "zcbh",//
			label: "资产编号",//
			placeholder: "请输入资产编号",
			immutableAdd: true
		},
		{
			type: "text",//
			name: "zcyz",//
			label: "资产原值（元）",//
			placeholder: "请输入资产原值（元）",
			immutableAdd: true
		},
		{
			type: "number",//
			name: "czl",//
			label: "残值率（%）",
			placeholder: "请输入",
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
			type: "text",//
			name: "ggxh",//
			label: "规格型号",//
			placeholder: "请输入规格型号",
			immutableAdd: true
		},
		{
			type: "select",
			name: "grfsdm",
			label: "购入方式",
			selectList: [//当类型为select时，option配置
			],
			ajax: {
				api: "getBuyMannerSelectAllList",
				valueName: "dmbh",
				textName: "dmnr"
			},
			immutableAdd: true
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
			type: "select",//
			name: "sybmid",//
			label: "使用部门",
			placeholder: "请输入使用部门",
			immutableAdd: true,
			selectList: [//当类型为select时，option配置
			],
			ajax: {
				api: "getDepartmentSelectAllList",
				valueName: "recordid",
				textName: "bmmc",
			}
		},
		{
			type: "text",
			name: "cfdd1",
			label: "存放地点",
			placeholder: "请输入存放地点",
			immutableAdd: true
		},
		{
			type: "text",//
			name: "syr",//接口字段名
			label: "使用人",//
			placeholder: "请输入使用人",
			immutableAdd: true
		},
		{
			type: "text",//
			name: "brand",//接口字段名
			label: "厂商品牌",//
			placeholder: "请输入厂商品牌",
			immutableAdd: true
		},
		{
			type: "textarea",//
			name: "bz",//
			label: "备注",//
			placeholder: "请输入备注",
			immutableAdd: true
		},
		{
			type: "upload",//
			name: "imageList",//
			label: "资产图片",//
			btnName: "添加图片",
			projectName: "zj-assets-haiwei",
			fileType: ["jpg", "jpeg", "png", "gif"],
			immutableAdd: true
		}
	],
})

var leading = $('#leading').detailLayer({
	layerArea: ['410px', '310px'],
	layerTitle: ['导入'],
	conditions: [
		{
			type: "upload",
			maxLen: 1,
			name: "importFileList",
			label: "导入文件",
			btnName: "选择文件",
			projectName: "zj-assets-haiwei",
			fileType: ["xls", "xlsx"],
		}
	],
	onAdd: function (obj, _params) {
		if (_params.importFileList.length == 0) {
			layer.alert("请选择文件", { icon: 0, }, function (index) {
				layer.close(index);
			});
			return;
		}

		l.ajax('batchInputAssets', { "importFileList": _params.importFileList }, function (_params) {
			pager.page('remote')
			obj.close()
		})
	}
});


$("body").on("click", "button", function () {
	var checkedData = l.getTableCheckedData(table);
	var name = $(this).attr("data-name");
	switch (name) {
		case "leading"://导入
			leading.open();
			break;
		case "derive"://导出
			var params = {};
			params.sydwName = $('[name = "sydwid"]').val();
			params.startTime = $('[name = "startTime"]').val();
			params.endTime = $('[name = "endTime"]').val();
			layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
				l.ajax('assetsExportExcel', params, function (res) {
					layer.alert("导出成功！", { icon: 0 }, function (index) {
						layer.close(index);
						window.location.href = res;
					})
				})
			});
			break;
	}
})

function editTableRow(rowIndex) {
	var checkedData = table.row(rowIndex).data()
	editDetailLayer.open(checkedData);
}

function myOpen(title, id, url) {
	var options = {
		type: 2,
		title: title,
		content: url + ".html?id=" + id + "&code=" + code
	}
	layer.full(layer.open(options))
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