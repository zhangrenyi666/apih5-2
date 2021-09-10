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
            "data": "yearName",//接口对应字段
            "title": "年份",//表头名
            "defaultContent": "-",//默认显示
        },			
		{
            "targets": [2],//第几列
            "data": "one",//接口对应字段
            "title": "1月(元)",//表头名
            "defaultContent": "-",//默认显示
        },		
		{
            "targets": [3],//第几列
            "data": "two",//接口对应字段
            "title": "2月(元)",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "three",//接口对应字段
            "title": "3月(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [5],//第几列
            "data": "four",//接口对应字段
            "title": "4月(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [6],//第几列
            "data": "five",//接口对应字段
            "title": "5月(元)",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "six",//接口对应字段
            "title": "6月(元)",//表头名
            "defaultContent": "-",//默认显示
        },		
		{
            "targets": [8],//第几列
            "data": "seven",//接口对应字段
            "title": "7月(元)",//表头名
            "defaultContent": "-",//默认显示
        },    
		{
            "targets": [9],//第几列
            "data": "eight",//接口对应字段
            "title": "8月(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [10],//第几列
            "data": "nine",//接口对应字段
            "title": "9月(元)",//表头名
            "defaultContent": "-",//默认显示
        },   
		{
            "targets": [11],//第几列
            "data": "ten",//接口对应字段
            "title": "10月(元)",//表头名
            "defaultContent": "-",//默认显示
        },   
		{
            "targets": [12],//第几列
            "data": "eleven",//接口对应字段
            "title": "11月(元)",//表头名
            "defaultContent": "-",//默认显示
        },   
		{
            "targets": [13],//第几列
            "data": "twelve",//接口对应字段
            "title": "12月(元)",//表头名
            "defaultContent": "-",//默认显示
        },     
		{
            "targets": [13],//第几列
            "data": "baseTotal",//接口对应字段
            "title": "当年发放基本薪酬合计(元)",//表头名
            "defaultContent": "-",//默认显示
        },   
		{
            "targets": [14],//第几列
            "data": "performTotal",//接口对应字段
            "title": "当年发放绩效薪酬合计(元)",//表头名
            "defaultContent": "-",//默认显示
        },   
		{
            "targets": [15],//第几列
            "data": "awardTotal",//接口对应字段
            "title": "各类津贴及奖励合计(元)",//表头名
            "defaultContent": "-",//默认显示
        },   
		{
            "targets": [16],//第几列
            "data": "otherTotal",//接口对应字段
            "title": "当年其他单位发放合计(元)",//表头名
            "defaultContent": "-",//默认显示
        }, 
        {
            "targets": [17],//第几列
            "data": "yearToatl",//接口对应字段
            "title": "年收入总计(元)",//表头名
            "defaultContent": "-",//默认显示
        },   	
        {
            "targets": [18],//第几列
            "data": "declareBase",//接口对应字段
            "title": "本年申报基数(元)",//表头名
            "defaultContent": "-",//默认显示
        },
 		{
	            	   "targets": [19],//第几列
	            	   "width": 80,
	            	   "data": function (row) {
	            		   return row
	            	   },//接口对应字段
	            	   "title": "操作",//1：待办  2：未完成  3：延期审核中 4：完成审核中 5：已完成 6：未通过 
	            	   "render": function (data) {
	            		   var html = '';
	            		   html += '<span class="dropDown">'
	            		    html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">操作</a>';
	            		    html += '<ul class="dropDown-menu menu radius box-shadow">'
							 if(data.readTime == '' || data.readTime == null){
							  html += '<li><a href="javascript:void(0);" onclick="myOpen(\' ' + data.rowIndex + '\',\'' + 'agree' + '\')" href="javascript:void(0);">同意</a></li>';
	            			  html += '<li><a href="javascript:void(0);" onclick="myOpen(\' ' + data.rowIndex + '\',\'' + 'unAgree' + '\')" href="javascript:void(0);">不同意</a></li>';							
							 }else{
							  html = '-';
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
        url: l.getApiUrl("getZjGzdAnnualIncomeDetailList"),
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

//
var detailLayer = $('#detailLayer').detailLayer({
	layerArea:['40%', '40%'],
	conditions: [
	             {
	            	 type: "hidden",//
	            	 name: "incomeId",//
	            	 label: "主键id",//
	            	 placeholder: "",
	             }, 
	             {
	            	 type: "textarea",//
	            	 name: "opinion",//
	            	 label: "意见",//
	            	 placeholder: "请输入意见",
	            	 must: true
	             }	
	             ],
	             onSave: function (obj, _params) {
	            	 l.ajax('zjGzdAnnualIncomeOperate', _params, function (_params) {
	            		 pager.page('remote')
	            		 obj.close()
	            	 })
	             }  				 
})


function myOpen(index, type) {
	var rowData = allData[Number(index)];
	var params = {};
	params.incomeId = rowData.incomeId;
	switch (type) {
	case 'agree':
		l.ajax('zjGzdAnnualIncomeOperate', params, function (res) {
		})
		break;
	case 'unAgree':
/* 	params.userId = rowData.userId;
	params.YearId = rowData.YearId; */
		
			detailLayer.open(params);
		
		break;		
	}
}