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
            "data": "applyDepName",//接口对应字段
            "title": "申请部门",//表头名
            "defaultContent": "-",//默认显示
			"render":function(data,index,row){
                var a;
                if(data){   
                    a = '<a style="color:blue;" onclick="myOpen1(\' '+row.rowIndex+ '\',\'' + 'bf' + '\')" href="javascript:void(0);">'+data+'</a>'
                }
                return a;
            }
        },
        {
            "targets": [3],//第几列
            "data": "applyReason",//接口对应字段
            "title": "申请理由",//表头名
            "defaultContent": "-",//默认显示
        },       
		{
            "targets": [4],//第几列
            "data": "apih5FlowStatus",//接口对应字段
            "title": "审核状态",//表头名   0:全公司  1：机关  2：项目
            "defaultContent": "-",//默认显示
            "render": function (data) {
                var text = ""
                switch (data) {
				    case "0": text = "待提交";
                        break;
                    case "1": text = "审核中";
                        break;
                    case "2": text = "通过";
                        break;
                    case "3": text = "驳回";
                        break;
                    default: text = "未知";
                        break;
                }
                return text
            }
        },		        
		{
            "targets": [5],//第几列
            "data": "modifyUserName",//接口对应字段
            "title": "操作员",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "操作时间",//表头名
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
            name: "applyDep",//输入字段名
            label: "申请部门",//输入字段对杨的中文名称
            placeholder: "请输入申请部门",
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
         // console.log('搜索参数是：',_params)
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
        url: l.getApiUrl("getZjLhddJointSupApplyList"),
        params: {
			
        },
        success: function (result) {
            if (result.success) {			
                var data = result.data;
				//console.log('params:',data)
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

var detailLayer = $('#detailLayer').detailLayer({
     layerArea:['100%', '100%'],
     conditions: [		
        {
            type: "text",//
            name: "applyDepName",//
            label: "申请部门",//
            placeholder: "请输入申请部门",
            immutableAdd:true
        },
		{
            type: "textarea",//
            name: "applyReason",//
            label: "申请理由",//
            placeholder: "请输入申请理由",
            immutableAdd:true
        },
        {
            type: "pickTree",//
            name: "oaNeedDep",//接口字段名
            label: "需要配合的部门",//
           immutableAdd:true,
            pickType: {
                department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
                member: false,//成员列表对应接口字段名,false表示不开启该类
            }
        },		
		{
            type: "textarea",//
            name: "opinionField1",//
            label: "部门总经理审批",//
            placeholder: "请输入",
            immutableAdd:true
        },
		{
            type: "textarea",//
            name: "opinionField2",//
            label: "监督委员牵头部门批复",//
            placeholder: "请输入",
            immutableAdd:true
        },
		{
            type: "textarea",//
            name: "opinionField3",//
            label: "监督委员会分管领导批",//
            placeholder: "请输入",
            immutableAdd:true
        },
		{
            type: "textarea",//
            name: "opinionField4",//
            label: "审计部联络人批复",//
            placeholder: "请输入",
            immutableAdd:true
        },
        {
            type: "upload",//
            name: "fileList",//
            label: "附件",//
            btnName: "添加附件",
			projectName:"zj-work-check",
            fileType: ["jpg", "jpeg", "png", "gif"],
			immutableAdd:true
        }
    ],
})



$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            myOpen('联合督导申请新增','','jointApplyApprove')
            break;	
    }
})

function myOpen(title, id, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id+"&code="+code
			
    }
    myOpenLayer = layer.open(options)
    layer.full(myOpenLayer)
}

function myOpen1(index, type) {
	 var rowData = allData[Number(index)];
    var params;
    switch (type){
        case 'bf':
            params = {};
            params.applyId = rowData.applyId;            
            l.ajax('getZjLhddJointSupApplyDetailsByWorkId',params,function(res){
                detailLayer.open(res);
                $('#detailLayer .btn').hide();
            })
            break;	
    }
}