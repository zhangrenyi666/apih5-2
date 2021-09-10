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
			"targets": [2],//第几列
			"data": "sszclx1Name",//接口对应字段
			"title": "资产大类",//表头名
			"defaultContent": "-",//默认显示
		},
		{
			"targets": [3],//第几列
			"data": "sszclx2Name",//接口对应字段
			"title": "资产小类",//表头名
			"defaultContent": "-"//默认显示
		},
		{
			"targets": [4],//第几列
			"data": "zcbh",//接口对应字段
			"title": "资产编号",//表头名
			"defaultContent": "-",//默认显示
		},
		{
			"targets": [5],//第几列
			"data": "cwbh",//接口对应字段
			"title": "财务编号",//表头名
			"defaultContent": "-",//默认显示
		},
		{
			"targets": [6],//第几列
			"data": "czl",//接口对应字段
			"title": "残值率",//表头名
			"defaultContent": "-",//默认显示
		},
		{
			"targets": [7],//第几列
			"data": "cfdd1",//接口对应字段
			"title": "存放地点",//表头名
			"defaultContent": "-"//默认显示
		},
		{
			"targets": [8],//第几列
			"data": "sybmName",//接口对应字段
			"title": "使用部门",//表头名
			"defaultContent": "-"//默认显示
		},
		{
			"targets": [9],//第几列
			"data": "grrq",//接口对应字段
			"title": "购入日期",//表头名
			"defaultContent": "-",//默认显示
			"render": function (data) {
				return l.dateFormat(new Date(data), "yyyy-MM-dd");
			}
		},
		{
			"targets": [10],//第几列
			"data": "modifyUserName",//接口对应字段
			"title": "操作员",//表头名
			"defaultContent": "-",//默认显示
		},
		{
			"targets": [11],//第几列
			"data": "createTime",//接口对应字段
			"title": "操作时间",//表头名
			"defaultContent": "-",//默认显示
			"render": function (data) {
				return l.dateFormat(new Date(data), "yyyy-MM-dd");
			}
		},
		{
			"targets": [12],//第几列
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
		url: l.getApiUrl("getZjHwzcZcglList"),
		params: {},
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

function myOpen(title, id, url) {
	var options = {
		type: 2,
		title: title,
		content: url + ".html?id=" + id + "&code=" + code
	}
	layer.full(layer.open(options))
}
