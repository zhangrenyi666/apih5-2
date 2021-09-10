var code = Lny.getUrlParam('code')
Lny.setCookie('code', code, 30)
var table = $('#table').DataTable({
    "info": false,//是否显示数据信息
    "paging": false,//是否开启自带分页
    "ordering": false, //是否开启DataTables的高度自适应
    "processing": false,//是否显示‘进度’提示
    "searching": false,//是否开启自带搜索
    "autoWidth": false,//是否监听宽度变化	
 /* 	"initComplete": function (settings) {
        var innerTh = '<th rowspan="0"></th>';
        innerTh += '<th colspan="9" style="text-align:center;">应付部分</th>';
		innerTh += '<th colspan="1" style="text-align:center;">工资总额</th>';
        innerTh += '<th colspan="8" style="text-align:center;">应扣部分</th>';
		innerTh += '<th colspan="1" style="text-align:center;">实付金额</th>';
		innerTh += '<th colspan="1" style="text-align:center;">开发奖</th>';
		innerTh += '<th colspan="1" style="text-align:center;">绩效奖</th>';
		innerTh += '<th colspan="1" style="text-align:center;"></th>';
		innerTh += '<th colspan="4" style="text-align:center;"></th>';
        $('#table').find("thead").prepend(innerTh);
    },  */ 
    "columnDefs": [//表格列配置
        {
            "targets": [0],//第几列
            "data": "yearAndMonth",//接口对应字段
            "title": "工资年月",//表头名
            "defaultContent": "-",//默认显示
        },			
		{
            "targets": [1],//第几列
            "data": "toJsonData.yingFa.gwxz",//接口对应字段  
            "title": "岗位薪资(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [2],//第几列
            "data": "toJsonData.yingFa.gljt",//接口对应字段  
            "title": "工龄津贴(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [3],//第几列
            "data": "toJsonData.yingFa.dszn",//接口对应字段  
            "title": "独生子女(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [4],//第几列
            "data": "toJsonData.yingFa.bgz",//接口对应字段  
            "title": "补工资(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [5],//第几列
            "data": "toJsonData.yingFa.jbf",//接口对应字段  
            "title": "加班费(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [6],//第几列
            "data": "toJsonData.yingFa.xljt",//接口对应字段  
            "title": "学历津贴(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [7],//第几列
            "data": "toJsonData.yingFa.zcjt",//接口对应字段  
            "title": "职称津贴(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [8],//第几列
            "data": "toJsonData.yingFa.zsbt",//接口对应字段  
            "title": "证书补贴(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [9],//第几列
            "data": "toJsonData.yingFa.qt",//接口对应字段  
            "title": "其他(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [10],//第几列
            "data": "toJsonData.otherInfo.gzze",//接口对应字段  
            "title": "工资总额(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [11],//第几列
            "data": "toJsonData.yignfu.kgz",//接口对应字段  
            "title": "扣工资(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [12],//第几列
            "data": "toJsonData.yignfu.gjj",//接口对应字段  
            "title": "公积金(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [13],//第几列
            "data": "toJsonData.yignfu.ylj",//接口对应字段  
            "title": "养老金(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [14],//第几列
            "data": "toJsonData.yignfu.syj",//接口对应字段  
            "title": "失业金(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [15],//第几列
            "data": "toJsonData.yignfu.ylkj",//接口对应字段  
            "title": "医疗扣缴(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [16],//第几列
            "data": "toJsonData.yignfu.de",//接口对应字段  
            "title": "大额(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [17],//第几列
            "data": "toJsonData.yignfu.nj",//接口对应字段  
            "title": "年金(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [18],//第几列
            "data": "toJsonData.yignfu.ksj",//接口对应字段  
            "title": "扣税金(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [19],//第几列
            "data": "toJsonData.otherInfo.sfgz",//接口对应字段  
            "title": "实付金额(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		/* {
            "targets": [20],//第几列
            "data": "toJsonData.otherInfo.kfj",//接口对应字段  
            "title": "开发奖(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [21],//第几列
            "data": "toJsonData.otherInfo.jxj",//接口对应字段  
            "title": "绩效奖(元)",//表头名
            "defaultContent": "-",//默认显示
        }, */
		{
            "targets": [20],//第几列
            "data": "toJsonData.otherInfo.bz",//接口对应字段  
			"width": 280,
            "title": "备注",//表头名
            "defaultContent": "-",//默认显示
			 "render": function (data) {
				 //console.log(data)
				 //var text=""
				 var text=data
				 //switch (data){
				 switch (text){
					case "无": text="-";
					break;
				}
				return text 
			 }
        }, 
		{
             "targets": [21],//第几列
             "width": 50,
             "data": function (row) {
                 return row
             },//接口对应字段
             "title": "操作",//表头名
             "render": function (data) {
                 var html = '';
                 html += '<span class="dropDown">'
                 html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" onclick="myOpen(\'' + data.year + '\',\'' + data.month + '\',\'' + data.userId + '\')">  <aria-haspopup="true" aria-expanded="true">阅后即焚</a>';
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
        },		
		{
            type: "select",
            name: "month",
            label: "月份",
			selectList: [//当类型为select时，option配置
            {
				value:"",
				text:"全部"
			},
			{
				value:"1",
				text:"1月"
			},
			{
				value:"2",
				text:"2月"
			},
			{
				value:"3",
				text:"3月"
			},
			{
				value:"4",
				text:"4月"
			},
			{
				value:"5",
				text:"5月"
			},
			{
				value:"6",
				text:"6月"
			},
			{
				value:"7",
				text:"7月"
			},
			{
				value:"8",
				text:"8月"
			},
			{
				value:"9",
				text:"9月"
			},
			{
				value:"10",
				text:"10月"
			},
			{
				value:"11",
				text:"11月"
			},
			{
				value:"12",
				text:"12月"
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
        url: l.getApiUrl("getPCZjWageDetail"),
        params: {
			userId: l.getUrlParam("userId")
			//userId: "wangsiyang_test",
        },
		
        success: function (result) {
		
			//console.log(result)
            if (result.success) {
                var data = result.data;
				//console.log(data[0].wageId)
					//console.log(data)
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
                l.delTableRow("wageId", 'zjWageList', 'deleteZjWage', checkedData, function (data) {
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
            l.ajax('deleteZjWage', params, function (res) {			
                pager.page('remote')
            })
			layer.close(index);
		});	
}