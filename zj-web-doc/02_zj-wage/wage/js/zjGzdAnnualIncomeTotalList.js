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
            "data": "userName",//接口对应字段
            "title": "姓名",//表头名
            "defaultContent": "-",//默认显示
        },			
		{
            "targets": [2],//第几列
            "data": "idCard",//接口对应字段
            "title": "身份证号",//表头名
            "defaultContent": "-",//默认显示
        },		
		{
            "targets": [3],//第几列
            "data": "yearToatl",//接口对应字段
            "title": "年收入总计(元)",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "declareBase",//接口对应字段
            "title": "本年申报基数(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [5],//第几列
            "data": "opinion",//接口对应字段
            "title": "意见",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [6],//第几列
            "data": "readTime",//接口对应字段
            "title": "确认时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
				if(data != null){
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
				}
            }
        },	
		{
            "targets": [7],//第几列
            "data": "readTime",//接口对应字段
            "title": "查看时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
				if(data != null){
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
				}
            }
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "yearName",//输入字段名
            label: "年份",//输入字段对杨的中文名称
            placeholder: "请输入年份",
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
var allData;
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjGzdAnnualIncomeList"),
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

$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "derive"://导出
            var params = {};
            params.yearName = $('[name = "yearName"]').val();
            layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
                l.ajax('zjGzdAnnualIncomeExportExcel', params, function (res) {
                    layer.alert("导出成功！", { icon: 0 }, function (index) {
                        layer.close(index);
                        window.location.href = res;
                    })
                })
            });
            break;
    }
})