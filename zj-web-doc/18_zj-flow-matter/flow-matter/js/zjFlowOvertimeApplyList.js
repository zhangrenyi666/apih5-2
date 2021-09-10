var code = Lny.getUrlParam('code');
Lny.setCookie('code', code, 30);

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
            "data": "overtimeTitle",//接口对应字段
			"width": 300,
            "title": "标题",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "name",//接口对应字段
            "title": "姓名",//表头名
            "defaultContent": "-",//默认显示
        },

        {
            "targets": [4],//第几列
            "data": "objectName",//接口对应字段
            "title": "部门",//表头名
            "defaultContent": "-",//默认显示
        },       
        {
            "targets": [5],//第几列
            "data": "applyDate",//接口对应字段
            "title": "申请日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
		{
            "targets": [6],//第几列
            "data": "startTime",//接口对应字段
            "title": "加班日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
				if(data == null){
					return "-";
				} else {
					return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
				}
            }
        },
		{
            "targets": [7],//第几列
            "data": "overtimeDays",//接口对应字段
            "title": "天数",//表头名
            "defaultContent": "-",//默认显示 
        },
		{
            "targets": [8],//第几列
            "data": "offDays",//接口对应字段
            "title": "倒休天数（天）",//表头名
            "defaultContent": "-",//默认显示 
        },       
        {
            "targets": [9],//第几列
            "data": "overtimeReason",//接口对应字段
            "title": "请假事由",//表头名
            "defaultContent": "-",//默认显示
        }
    ]
});
var pager = $("#pager").page({
    remote: {// ajax请求配置
        url: l.getApiUrl("getZjFlowOvertimeApplyList"),
         params: {
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

var overtimeTitle = '';
var objectName = '';
var name = '';
var form = $('#form').filterfrom({
    conditions: [//表单项配置
         {
            type: "text",//三种形式：text,select,date,
            name: "overtimeTitle",//输入字段名
            label: "标题",//输入字段对杨的中文名称
            placeholder: "请输入标题",
        },	
		 {
            type: "text",//三种形式：text,select,date,
            name: "objectName",//输入字段名
            label: "部门",//输入字段对杨的中文名称
            placeholder: "请输入部门",
        },	
		{
            type: "text",//三种形式：text,select,date,
            name: "name",//输入字段名
            label: "姓名",//输入字段对杨的中文名称
            placeholder: "请输入姓名",
        },	
		{
            type: "date",//三种形式：text,select,date,
            name: "applyDate",//输入字段名
            label: "申请时间",//输入字段对杨的中文名称
            placeholder: "请输入申请时间",
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
		overtimeTitle = arr[0].value;
		objectName = arr[0].value;
		name = arr[0].value;
        pager.page('remote', 0, _params)
    },
    onReset: function (arr) {//重置按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
		overtimeTitle = '';
		objectName = '';
		name = '';
        pager.page('remote', _params)
    }
});

$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
        case "exportOpen"://导入
            // var width = (window.screen.availWidth * 0.9);
            // var height =( window.screen.availHeight * 0.85);
            // var left = (window.screen.availWidth * 0.05);
            // var top =( window.screen.availHeight * 0.05);
            // window.open('http://192.168.1.223:8081/ureport/excel?_u=file:hwzyAuditIssue.xml&url=' + l.domain + '&proType='+proType, '', 'width='+ width +',height='+ height +',scrollbars=yes,top='+ top +',left='+ left);
            window.location.href = 'http://weixin.fheb.cn:91/ureport/excel?_u=file:zjFlowOvertimeApplyList.xml&url=' + l.domain + '&objectName='+objectName;
           // window.location.href = 'http://wechat.zjyjhw.com:88/haiwei/ureport/excel?_u=file:hwzyAuditIssue.xml&url=' + l.domain + '&proType='+proType;
            break;
    }
})