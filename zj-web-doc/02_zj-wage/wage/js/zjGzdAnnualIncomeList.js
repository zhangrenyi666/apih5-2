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
            "data": "yearName",//接口对应字段
            "title": "年份",//表头名
            "defaultContent": "-",//默认显示
        },			
		{
            "targets": [3],//第几列
            "data": "sendTime",//接口对应字段
            "title": "发送时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
				if(data != null){
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
				}
            }
        },
		{
            "targets": [4],//第几列
            "data": "sendFlag",//接口对应字段
            "title": "发送状态",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
				var text=""
				switch (data){
					case "0": text="未发送";
					break;
					case "1": text="已发送";
					break;
					case "2": text="发送失败";
					break;
					default: text="未知";
					break;
				}
				return text
            }
        },		
		{
            "targets": [5],//第几列
            "data": "readTime",//接口对应字段
            "title": "查看时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
				if(data != null){
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
				}
            }
        },      
		{
            "targets": [6],//第几列
            "data": "createTime",//接口对应字段
            "title": "导入时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        }		
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "userName",//输入字段名
            label: "姓名",//输入字段对杨的中文名称
            placeholder: "请输入姓名",
        },
        {
            type: "select", 
			name: "sendFlag",
            label: "发送状态",			
			selectList: [//当类型为select时，option配置
			{
				value:"",
				text:"全部"
			},
			{
				value:"0",
				text:"未发送"
			},
			{
				value:"1",
				text:"已发送"
			},
			{
				value:"2",
				text:"发送失败"
			}
            ],			
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
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjGzdAnnualIncomeList"),
        params: {
		 yearId: l.getUrlParam("id"),
		 yearName: l.getUrlParam("year")
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
	
var leading = $('#leading').detailLayer({
    layerArea:['500px', '500px'],
    layerTitle:['导入'],	
        conditions: [				
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "yearId",//输入字段名
			defaultValue:l.getUrlParam("id"),
			must: true,
			immutableAdd: true
        },		
        {
            type: "text", 
			name: "year",
            label: "年份",			
			defaultValue:l.getUrlParam("year") ,
			must: true,
			immutableAdd: true
        },					
        {
            type: "upload",
            maxLen:1,
            name: "zjGzdAnnualIncomeList",
            label: "导入文件",
            btnName: "选择文件",
			projectName:"zj-wage",
            fileType: ["xls", "xlsx"],
        }
    ],
    onAdd: function (obj, _params){
        if(_params.zjGzdAnnualIncomeList.length==0){
            layer.alert("请选择文件", { icon: 0, }, function (index) {
                layer.close(index);
            });
            return;
        }       
        l.ajax('batchInputZjGzdAnnualIncome', {"zjGzdAnnualIncomeList":_params.zjGzdAnnualIncomeList,"yearId":_params.yearId}, function  (data,message,success) {		
			layer.alert("成功导入"+data+"人工资数据！")	 
			obj.close()
			pager.page('remote')
        })
    }
})




var send = $('#send').detailLayer({
    layerArea:['500px', '240px'],
    layerTitle:['发送'],	
        conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "yearId",//输入字段名
			defaultValue:l.getUrlParam("id"),
			must: true,
			immutableAdd: true
        },		
        {
            type: "text", 
			name: "year",
            label: "年份",			
			defaultValue:l.getUrlParam("year") ,
			must: true,
			immutableAdd: true
        }			
    ],
    onAdd: function (obj, _params){
	    l.ajax('getZjGzdAnnualIncomeSendNum', {"yearId":_params.yearId}, function (data,message,success) {		
			if(data=="0"){
				layer.alert("该年份未导入任何工资数据或该年份工资已发送！")				
			}else{				
			  layer.confirm("发现"+data+"人的工资条，确定发送？", { icon: 0,}, function (index) {
              l.ajax('sendZjGzdAnnualIncome', {"yearId":_params.yearId}, function (data,message,success) {
				  if(success){
                     if(data=="0"){
				        layer.alert("工资单全部发送成功！")				
			          }else{
						layer.alert("有"+data+"人工资条发送失败！")	 
					  }
					  layer.close(index);
					  obj.close()
					  pager.page('remote')
				    }
                });          
		      })
			}
       }); 
    }
});


$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    var name = $(this).attr("data-name");
    switch (name) {
		case "leading"://导入
            leading.open();
            break;
		case "send"://发送
            send.open();		       
            break;		
    }
})
