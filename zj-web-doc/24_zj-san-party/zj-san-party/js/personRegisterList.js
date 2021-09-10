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
            "targets": [1],//第几列
            "data": "empNo",//接口对应字段
            "title": "员工编号",//表头名
            "defaultContent": "-",//默认显示	
        },	 
		{
            "targets": [2],//第几列
           "data": function (row) {
                return row
            },//接口对应字段
            "title": "姓名",//表头名
            "defaultContent": "-",//默认显示
			"render":function(data,index,row){
                var a;
                if(data){   
                   a = '<a style="color:blue;"  onclick="myOpen(\'人员信息详情\',\'' + data.personRegisterId + '\',\'' + 'personRegisterInfoDetail' + '\')" href="javascript:void(0);">' + data.perName + '</a>'
                }
                return a;
            }				
        },	
        {
            "targets": [3],//第几列
            "data": "proName",//接口对应字段
            "title": "所在项目",//表头名
            "defaultContent": "-",//默认显示
        },       
		{
            "targets": [4],//第几列
            "data": "nation",//接口对应字段
            "title": "民族",//表头名
            "defaultContent": "-",//默认显示
        },		               
		{
            "targets": [5],//第几列
            "data": "sex",//接口对应字段
            "title": "性别",//表头名
            "defaultContent": "-",//默认显示
				"render": function (data) {
                var text = ""
                switch (data) {
                    case "0": text = "男";
                        break;
                    case "1": text = "女";
                        break;                 
                    default: text = "未知";
                        break;
                }
                return text
            }
        },
		{
            "targets": [6],//第几列
            "data": "nativePlace",//接口对应字段
            "title": "籍贯",//表头名
            "defaultContent": "-",//默认显示
        },	
        {
            "targets": [7],//第几列
            "data": "politicsStatus",//接口对应字段
            "title": "政治面貌",//表头名
            "defaultContent": "-",//默认显示
			"render": function (data) {
                var text = ""
                switch (data) {
                    case "0": text = "正式党员";
                        break;
                    case "1": text = "团员";
                        break;                  
					case "2": text = "群众";
                        break;
					case "3": text = "预备党员";
                        break;
					case "4": text = "中共党员";
                        break;
                    default: text = "未知";
                        break;
                }
                return text
            }
        },
		{
            "targets": [8],//第几列
            "data": "depName",//接口对应字段
            "title": "部门",//表头名
            "defaultContent": "-",//默认显示
        },	
		{
            "targets": [9],//第几列
            "data": "postRank",//接口对应字段
            "title": "岗位级别",//表头名
            "defaultContent": "-",//默认显示
        },	
        {
            "targets": [10],//第几列
            "data": "duty",//接口对应字段
            "title": "职务",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [11],//第几列
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
            name: "empNo",//输入字段名
            label: "员工编号",//输入字段对杨的中文名称
            placeholder: "请输入员工编号",
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "projectId",//输入字段名
            label: "所在项目",//输入字段对杨的中文名称
            placeholder: "请输入所在项目",
        },
		{
            type: "text",//三种形式：text,select,date,
            name: "perName",//输入字段名
            label: "姓名",//输入字段对杨的中文名称
            placeholder: "请输入名称",
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
        url: l.getApiUrl("getZjSanDjPersonRegisterList"),
        params: {seeFlag:"1"},
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

var leading = $('#leading').detailLayer({
    layerArea:['410px', '310px'],
    layerTitle:['导入'],
        conditions: [
        {
            type: "upload",
            maxLen:1,
            name: "importFileList",
            label: "导入文件",
            btnName: "选择文件",
			projectName:"zj-san-party",
            fileType: ["xls", "xlsx"],
        }
    ],
    onAdd: function (obj, _params){
        if(_params.importFileList.length==0){
            layer.alert("请选择文件", { icon: 0, }, function (index) {
                layer.close(index);
            });
            return;
        }
        l.ajax('importZjSanDjPersonRegister', {"importFileList":_params.importFileList}, function (_params) {
            pager.page('remote')
            obj.close()
        })
    }
});

var transferLayer = $('#transferLayer').detailLayer({
    layerArea:['70%', '90%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "transferRecordId",//输入字段名
        }, 
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "personRegisterId",//输入字段名
        }, 
		{
            type: "date",//
            name: "actionDate",//
            label: "调令日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id: "actionDate"
        },
		{
            type: "text",//
            name: "perName",//
            label: "姓名",//
            placeholder: "请输入姓名",
            immutableAdd: true
        }, 		
		{
            type: "pickTree",
            name: "oaProject",
            label: "调出单位",
			immutableAdd: true,
            pickType: {
                department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
                member: false,//成员列表对应接口字段名,false表示不开启该类
            }
        },
		{
            type: "pickTree",
            name: "oaTranProject",
            label: "调入单位",
			must: true,
            pickType: {
                department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
                member: false,//成员列表对应接口字段名,false表示不开启该类
            }
        },
		{
            type: "text",//
            name: "depName",//
            label: "原部门",//
            placeholder: "请输入原部门",
            immutableAdd: true
        },
		{
            type: "date",//
            name: "reportDate",//
            label: "报道日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id: "reportDate"
        },
		{
            type: "text",//
            name: "postRank",//
            label: "原岗位级别",//
            placeholder: "请输入原岗位级别",
			immutableAdd: true
        },
		{
            type: "text",//
            name: "duty",//
            label: "原职务",//
            placeholder: "请输入原职务",
			immutableAdd: true
        },
        {
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入"
        }           
    ],
    onSave: function (obj, _params) {
        l.ajax('addZjSanDjPersonTransferRecord', _params, function (data) {
                pager.page('remote')
                obj.close()
            })
    }
})
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
		   myOpen('人员信息新增','','personRegisterInfoAdd')
            break;
        case "edit":
            if (checkedData.length == 1) {
			 var personRegisterId = checkedData[0].personRegisterId
			    myOpen('人员信息编辑',personRegisterId,'personRegisterInfoAdd')
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
				 layer.confirm("确定删除？", { icon: 0,}, function (index) {
				l.ajax("batchDeleteUpdateZjSanDjPersonRegister", checkedData, function () {
					pager.page('remote')
				})
				layer.close(index);
            });
            }
            break;
		case "leading"://导入
            leading.open();
            break;
		case "transfer"://人员调动
            if (checkedData.length == 1) {
               // transferLayer.open(checkedData[0]);
				myOpenFlow('人员调动审批', checkedData[0], 'flowTranApprove')
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
    }
})

var myOpenLayer
function myOpen(title, id, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id+ "&code=" + code 
    }
    myOpenLayer = layer.open(options)
    layer.full(myOpenLayer)
}

function myOpenFlow(title, data, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?workId=" + data.workId + "&nodeId=" + data.nodeId + "&code=" + code + "&personRegisterId=" + data.personRegisterId
    }
    myOpenLayer = layer.open(options)
    layer.full(myOpenLayer)
}