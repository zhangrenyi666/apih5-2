var recordid = l.getUrlParam('id') || "";
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
             "data": function (row) {
                return row
            },
            "title": "监督事项",//表头名
            "defaultContent": "-",//默认显示
			"render": function (data) {
                var a;				
                if (data) {                   
					a = '<a style="color:blue;"  href="javascript:void(0);" onclick="myOpen1(\' ' + data.rowIndex + '\',\'' + 'xq' + '\')" href="javascript:void(0);">' + data.supMatter + '</a>';
					   
                }
                return a;
            }			
        },		
        {
            "targets": [3],//第几列
            "data": "remarks",//接口对应字段
            "title": "备注",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [4],//第几列
            "data": "planCompletionTime",//接口对应字段
            "title": "计划完成时间",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [5],//第几列
            "data": "planCompletionCondition",//接口对应字段
            "title": "计划完成情况",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [6],//第几列
            "data": "workEfficiency",//接口对应字段
            "title": "工作效果",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "executorName",//接口对应字段
            "title": "操作者",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [8],//第几列
            "data": "executorTime",//接口对应字段
            "title": "更新时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return data ? l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss") : "-";
            }
        }
    ]
});


var detailForm = $('#detailForm').detailLayer({
    conditions: [
         {
            type: "text",//五种形式：text,select,date,hidden,textarea,
            name: "number",//输入字段名
            label: "编号",
            must: true          
        },			
        {
            type: "pickTree",//
            name: "oaDutyDep",//接口字段名
            label: "监督责任部门",//
            must: true,
            pickType: {
                department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
                member: false,//成员列表对应接口字段名,false表示不开启该类
            }
        },
        {
            type: "text",
            name: "supObj",
            label: "监督对象",
            must: true
        }  
    ],
    onSave: function (obj, _params) {
        if (recordid) {
            _params.yearSupPlanId = recordid
            l.ajax("updateZjLhddJointSupCheckIssue", _params, function (data, message, success) {
                if (success) {
                    layer.alert(message, { icon: 0, }, function (index) {
                        parent.pager.page('remote')
                        layer.close(index);
                        $("#tab-system").Huitab({
                            index: 1
                        });
                    });
                }
            })
        } else {
            l.ajax("addZjLhddJointSupCheckIssue", _params, function (data, message, success) {
                if (success) {
                    recordid = data.yearSupPlanId
                    layer.alert(message, { icon: 0, }, function (index) {
                        parent.pager.page('remote')
                        layer.close(index);

                        $("#tab-system").Huitab({
                            index: 1
                        });

                        loadPage()
                    });
                }
            })
        }
    }
})

detailForm.close = function () {
    parent.layer.close(parent.myOpenLayer)
}
loadPage()
function loadPage() {
    if (recordid) {
        l.ajax("getZjJdjhYearSupPlanDetails", { yearSupPlanId: recordid }, function (data, message, success) {
            if (success) {
                detailForm.setOpenData(data)
            }
        })
    } else {
        detailForm.setOpenData({ memberList: { oaMemberList: [] } })
    }
}




var allData;
//检查项
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjJdjhYearSupMatterList"),
        params: {
            yearSupPlanId: recordid,
        },
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
var detailLayer = $('#detailLayer').detailLayer({
	layerArea:['70%', '90%'],
    conditions: [
         {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "typeFlag",//输入字段名
			defaultValue:"1",
        },
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "yearSupPlanId",//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "yearSupMatterId",//输入字段名
        }, 		
        {
            type: "textarea",//
            name: "supMatter",//
            label: "监督事项",//
            placeholder: "请输入监督事项",
			 immutableAdd: true 
        },
		{
            type: "text",//
            name: "scheduleTime",//
            label: "计划实施时间",//
            placeholder: "请输入计划实施时间",
			 immutableAdd: true 
        },
		{
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入备注",
			 immutableAdd: true 
        },
		{
            type: "text",//
            name: "planCompletionTime",//
            label: "计划完成时间",//
            placeholder: "请输入计划完成时间",
        },
		{
            type: "textarea",//
            name: "planCompletionCondition",//
            label: "计划完成情况",//
            placeholder: "请输入计划完成情况",
        },
		{
            type: "textarea",//
            name: "workEfficiency",//
            label: "工作效果",//
            placeholder: "请输入工作效果",
        },			
        
        {
            type: "upload",//
            name: "fileList",//
            label: "监督工作结果",//
            btnName: "附件",
            projectName: "zj-work-check",
            fileType: ["jpg", "jpeg", "png", "gif"]
        }
    ],
    onSave: function (obj, _params) {
        l.ajax("addZjJdjhSupPlanExecutive", _params, function (data, message, success) {
            if (success) {
                pager.page('remote', { yearSupPlanId: recordid })
                layer.alert(message, { icon: 0, }, function (index) {
                    layer.close(index);
                    obj.close()
                });
            }
        })
    },
    onAdd: function (obj, _params) {
        l.ajax("addZjJdjhSupPlanExecutive", _params, function (data, message, success) {
            if (success) {
                pager.page('remote', { yearSupPlanId: recordid })
                layer.alert(message, { icon: 0, }, function (index) {
                    layer.close(index);
                    obj.close()
                });
            }
        })
    },
})

var exeDetailLayer = $('#exeDetailLayer').detailLayer({
	layerArea:['70%', '90%'],
    conditions: [
         {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "typeFlag",//输入字段名
			defaultValue:"1",
        },
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "yearSupPlanId",//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "yearSupMatterId",//输入字段名
        }, 		
        {
            type: "textarea",//
            name: "supMatter",//
            label: "监督事项",//
            placeholder: "请输入监督事项",
			 immutableAdd: true 
        },
		{
            type: "text",//
            name: "scheduleTime",//
            label: "计划实施时间",//
            placeholder: "请输入计划实施时间",
			 immutableAdd: true 
        },
		{
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入备注",
			 immutableAdd: true 
        },
		{
            type: "text",//
            name: "planCompletionTime",//
            label: "计划完成时间",//
            placeholder: "请输入计划完成时间",
        },
		{
            type: "textarea",//
            name: "planCompletionCondition",//
            label: "计划完成情况",//
            placeholder: "请输入计划完成情况",
        },
		{
            type: "textarea",//
            name: "workEfficiency",//
            label: "工作效果",//
            placeholder: "请输入工作效果",
        },			
        
        {
            type: "upload",//
            name: "fileList",//
            label: "监督工作结果",//
            btnName: "附件",
            projectName: "zj-work-check",
            fileType: ["jpg", "jpeg", "png", "gif"]
        }
    ],
    onSave: function (obj, _params) {
       
    },
    onAdd: function (obj, _params) {
      
    },
})


$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "reply":
            if (checkedData.length == 1) {
               if(checkedData[0].state =='1'){
				  layer.alert("已经回复了", { icon: 0, }, function (index) {
                    layer.close(index);
                  });
				}else{
				 detailLayer.open(checkedData[0])
				}  
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
        case "close":
            parent.layer.close(parent.myOpenLayer)
            break;

    }
})

function myOpen1(index, type) {
    var rowData = allData[Number(index)];
    var params;
    switch (type) {
        case 'xq':
            params = {};
            params.yearSupMatterId = rowData.yearSupMatterId;
            l.ajax('getZjJdjhYearSupMatterDetail', params, function (res) {
                exeDetailLayer.open(res);
                $('#exeDetailLayer .btn').hide();
            })
            break;
    }
}

$("#tab-system").Huitab({
    index: 0
});
