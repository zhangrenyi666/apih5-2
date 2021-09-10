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
            "data": "perName1",//接口对应字段
            "title": "姓名",//表头名
            "defaultContent": "-",//默认显示
			 "render":function(data,index,row){
	            		   var a;
	            		   if(data){
	            			   a = '<a style="color:blue;" onclick="myOpen1(\' '+row.perName+ '\',\'' + 'gradeTotalDetailList' + '\')" href="javascript:void(0);">'+data+'</a>'
	            		   }
	            		   return a;
	            	   }
        },
		
	
        {
            "targets": [2],//第几列
            "data": "proNameNew",//接口对应字段
            "title": "项目名称",//表头名
            "defaultContent": "-",//默认显示
        },        
        {
            "targets": [3],//第几列
            "data": "duty",//接口对应字段
            "title": "职务",//表头名
            "defaultContent": "-",//默认显示
        },					
		{
            "targets": [4],//第几列
            "data": "award1",//接口对应字段
            "title": "个人得分",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "award2",//接口对应字段
            "title": "集体得分",//表头名
            "defaultContent": "-",//默认显示
        },        
		{
            "targets": [6],//第几列
            "data": "award4",//接口对应字段
            "title": "技术创新成果得分",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "award5",//接口对应字段
            "title": "专有科技成果得分",//表头名
            "defaultContent": "-",//默认显示
        },        
        {
            "targets": [8],//第几列
            "data": "award6",//接口对应字段
            "title": "专利得分",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [9],//第几列
            "data": "award7",//接口对应字段
            "title": "合理化建议及技术革新成果、五小得分",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [10],//第几列
            "data": "award8",//接口对应字段
            "title": "学术著作、论文得分",//表头名
            "defaultContent": "-",//默认显示
        },        
        {
            "targets": [11],//第几列
            "data": "award9",//接口对应字段
            "title": "施工工法、技术标准得分",//表头名
            "defaultContent": "-",//默认显示
        },						
		{
            "targets": [12],//第几列
            "data": "award10",//接口对应字段
            "title": "QC成果得分",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [13],//第几列
            "data": "award11",//接口对应字段
            "title": "科技研发项目得分",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [14],//第几列
            "data": "award12",//接口对应字段
            "title": "施工工艺标准得分",//表头名
            "defaultContent": "-",//默认显示
        },  
		{
            "targets": [15],//第几列
            "data": "award13",//接口对应字段
            "title": "证件奖励得分",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [16],//第几列
            "data": "award14",//接口对应字段
            "title": "职称奖励得分",//表头名
            "defaultContent": "-",//默认显示
        },    
		{
            "targets": [17],//第几列
            "data": "award15",//接口对应字段
            "title": "综合评价奖励得分",//表头名
            "defaultContent": "-",//默认显示
        },    
		{
            "targets": [18],//第几列
            "data": "award16",//接口对应字段
            "title": "业务评价奖励得分",//表头名
            "defaultContent": "-",//默认显示
        },               
		{
            "targets": [19],//第几列
            "data": "sum",//接口对应字段
            "title": "积分",//表头名
            "defaultContent": "-",//默认显示
        }	
    ]
});

var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "perName",//输入字段名
            label: "姓名",//输入字段对杨的中文名称
            placeholder: "请输入姓名",
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "proName",//输入字段名projectId
            label: "项目",//输入字段对杨的中文名称
            placeholder: "请输入姓名",
        },
		
		{
            type: "date",//text,select,date,
            name: "startTime",
            label: "时间开始",
            //defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd"),
            maxDate: "\'#F{$dp.$D(\\\'" + "logmax" + "\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmin"
        },
        {
            type: "date",//text,select,date,
            name: "endTime",
            label: "时间结束",
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

var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjSanDjGradeTotalList"),
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

$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
		case "derive"://汇总统计导出
		 var params = {}	
             params.startTime = $('[name = "startTime"]').val();  
			 params.endTime = $('[name = "endTime"]').val();  
             layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
                l.ajax('exportZjSanDjGradeTotal', params, function (res) {
                    layer.alert("导出成功！", { icon: 0 }, function (index) {
                        layer.close(index);
                        window.location.href = res;
                    })
                })
            }); 			
            break
    }
})


function myOpen1(id, url) {
    var options = {
        type: 2,
        title: '详情',
        content: url + ".html?id=" + id+"&code="+code
    }
    layer.full(layer.open(options))
}