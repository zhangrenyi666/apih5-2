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
            "data": "yearAndMonth",//接口对应字段
            "title": "绩效奖统计年",//表头名
            "defaultContent": "-",//默认显示
        },			
/* 		{
            "targets": [1],//第几列
            "data": "toJsonData.otherInfo.ptjn",//接口对应字段  
            "title": "绩效总金额",//表头名
            "defaultContent": "-",//默认显示
        }, */
		{
            "targets": [1],//第几列
            "data": "toJsonData.otherInfo.pyf",//接口对应字段  
            "title": "当月应发绩效金额(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [2],//第几列
            "data": "toJsonData.otherInfo.pks",//接口对应字段  
            "title": "扣税金(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [3],//第几列
            "data": "toJsonData.otherInfo.psf",//接口对应字段  
            "title": "当月实发绩效金额(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [4],//第几列
            "data": "toJsonData.otherInfo.pbz",//接口对应字段  
			"width": 280,
            "title": "备注",//表头名
            "defaultContent": "-",//默认显示
			 "render": function (data) {
				 var text= data
				 switch (text){
					case "无": text="-";
					break;
				}
				return text 
			 }
        },
		{
             "targets": [5],//第几列
             "width": 50,
             "data": function (row) {
                 return row
             },//接口对应字段
             "title": "操作",//表头名
             "render": function (data) {
                 var html = '';
                 html += '<span class="dropDown">'
                 html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" onclick="myOpen(\'' + data.year + '\',\'' + data.month + '\',\'' + data.userId + '\')">   <aria-haspopup="true" aria-expanded="true">阅后即焚</a>';
                 html += '<ul class="dropDown-menu menu radius box-shadow">';
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
			name: "year",
            label: "年份",			
			selectList: [//当类型为select时，option配置
			{
				value:"",
				text:"全部"
			},
			{
				value:"2018",
				text:"2018年"
			},
			{
				value:"2019",
				text:"2019年"
			},
			{
				value:"2020",
				text:"2020年"
			}
            ],			
        }					
    ],
    onSearch: function (arr) {//搜索按钮回调
        var _params = {};
		//console.log(arr)
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
		 //console.log(arr)
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
        pager.page('remote', _params)
    }
})

var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getPCZjPerformDetail"),
        params: {
			userId: l.getUrlParam("userId")
			//userId: "wangsiyang_test",
        },
        success: function (result) {
			//console.log(result)
            if (result.success) {
                var data = result.data;
				//console.log(data[0].wageId)
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
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "wageId",//输入字段名
        },
        {
            type: "textarea",//
            name: "userId",//
            label: "姓名名",//
            placeholder: "请输入姓名名",
            must: true
        },
        {
            type: "textarea",//
            name: "yearAndMonth",//
            label: "工资年月",//
            placeholder: "请输入工资年月",
            must: true
        },
        {
            type: "text",//
            name: "jsonData",//
            label: "json数据",//
            placeholder: "请输入json数据",
            must: true
        },
    ],
    onSave: function (obj, _params) {
        l.ajax('updateZjWage', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjWage', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    }
})


$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
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
                l.delTableRow("wageId", 'zjWageList', 'batchDeleteZjWage', checkedData, function (data) {
                    pager.page('remote')
                })//  删除url地址
            }
            break;
    }
})

function myOpen(year,month,userId) {      
		 var params={
			month:month,
			year:year,
			userId:userId
		}
		    layer.confirm("确定销毁本月工资条？", { icon: 0,}, function (index) {
            l.ajax('deleteZjPerform', params, function (res) {			
                pager.page('remote')
            })
			layer.close(index);
		});	
}