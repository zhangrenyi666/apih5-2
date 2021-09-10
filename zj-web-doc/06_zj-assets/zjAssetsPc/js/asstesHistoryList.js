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
			"data": "czl",//接口对应字段
			"title": "残值率",//表头名
			"defaultContent": "-",//默认显示
		},
		{
			"targets": [5],//第几列
			"data": "cfddmc",//接口对应字段
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

function myOpen(title, id, url) {
	var options = {
		type: 2,
		title: title,
		content: url + ".html?id=" + id
	}
	layer.full(layer.open(options))
}