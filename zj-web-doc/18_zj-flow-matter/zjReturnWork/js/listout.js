var code = Apih5.getUrlParam('code')
Apih5.setCookie('code',code,30)
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
            "data": "title",//接口对应字段
            "title": "标题",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "applyCompanyName",//接口对应字段
            "title": "申请单位",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "country",//接口对应字段
            "title": "所属国家",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "region",//接口对应字段
            "title": "所属区域",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [6],//第几列
            "data": "apih5FlowStatus",//接口对应字段
            "title": "评审状态",//表头名
            "defaultContent": "-",//默认显示
			"render": function (data) {
                var text = ""
                switch (data) {
                    case "0": text = "未发起";
                        break;
                    case "1": text = "评审中";
                        break;
                    case "2": text = "评审中";
                        break;
					case "3": text = "评审中";
                        break;
                    default: text = "未发起";
                        break;
                }
                return text
            }
        },
        {
            "targets": [7],//第几列
            "data": "returnDate",//接口对应字段
            "title": "返岗日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return (data) ? l.dateFormat(new Date(data), "yyyy-MM-dd") : "-";
            }
        },
        {
            "targets": [8],//第几列
            "data": "applyDate",//接口对应字段
            "title": "申请日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return (data) ? l.dateFormat(new Date(data), "yyyy-MM-dd") : "-";
            }
        },
        {
            "targets": [9],//第几列
            "data": "applyProjectName",//接口对应字段
            "title": "去往项目名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [10],//第几列
            "data": "cooperationName",//接口对应字段
            "title": "协作单位名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [11],//第几列
            "width": 80,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//1：待办  2：未完成  3：延期审核中 4：完成审核中 5：已完成 6：未通过 
            "render": function (data) {
                var html = '<a style="color:blue;" href="./listPerson.html?code=' + code + '&sarsId=' + data.sarsId +'&sarsType=1'+ '">人员明细</a>';
                return html;
            }
        }
    ]
});

var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjHwSarsList"),
        params: {
			sarsType:"1"
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                $.each(data,function (index,item) {
                    item.rowIndex = index
                })
                table.clear().rows.add(data).draw();
            } else {
                layer.alert(result.message,{ icon: 0,},function (index) {
                    layer.close(index);
                });
            }
        }
    }
});
var detailLayer = $('#detailLayer').detailLayer({
    layerArea: ["100%","100%"],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "sarsId",//输入字段名
        },
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "sarsType",//输入字段名
			defaultValue:'1'
        },
        {
            type: "text",//
            name: "title",//
            label: "标题",//
            placeholder: "请输入",
            must: true
        },
        {
            type: "text",//
            name: "applyCompanyName",//
            label: "申请单位",//
            placeholder: "请输入",
            must: true
        },
        {
            type: "text",//
            name: "country",//
            label: "所在国家",//
            placeholder: "请输入",
            must: true
        },
        {
            type: "text",//
            name: "region",//
            label: "所属区域",//
            placeholder: "请输入",
            must: true
        },
        {
            type: "date",//
            name: "returnDate",//
            label: "返岗日期",//
            placeholder: "请选择",
            must: true,
            defaultValue: new Date()
        },
        {
            type: "date",//
            name: "applyDate",//
            label: "申请日期",//
            placeholder: "请选择",
            must: true,
			immutableAdd: true,
            defaultValue: new Date()
        },
        {
            type: "text",//
            name: "applyProjectName",//
            label: "去往项目名称",//
            placeholder: "请输入",
            must: true
        },
        {
            type: "text",//
            name: "cooperationName",//
            label: "协作单位名称",//
            placeholder: "请输入",
            must: true
        }
    ],
    onSave: function (obj,_params) {
        l.ajax('updateZjHwSars',_params,function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj,_params) {
        l.ajax('addZjHwSars',_params,function (data) {
            pager.page('remote')
            obj.close()
        })
    }
})

$("body").on("click","button",function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            detailLayer.open();
            break;
        case "edit":
            if (checkedData.length == 1) {
				//console.log(checkedData[0].apih5FlowStatus)
				if(checkedData[0].apih5FlowStatus ==1 || checkedData[0].apih5FlowStatus ==2){
				 layer.alert("已经发起了不能编辑",{ icon: 0,},function (index) {
                    layer.close(index);
                 });	
				}else{
				detailLayer.open(checkedData[0]);	
				}
				
				
                
            } else if (checkedData.length == 0) {
                layer.alert("未选择任何项",{ icon: 0,},function (index) {
                    layer.close(index);
                });
            } else {
                layer.alert("只能选择一个",{ icon: 0,},function (index) {
                    layer.close(index);
                });
            }
            break;
        case "faqi":
            if (checkedData.length == 1) {
				if(checkedData[0].apih5FlowStatus ==1 || checkedData[0].apih5FlowStatus ==2){
					 layer.alert("流程已经发起不能重复提交!",{ icon: 0,},function (index) {
                    layer.close(index);
                });
				}else{
					 //直接去发起流程页面
                var sarsId = checkedData[0].sarsId;
                window.location.href = './returnWorkApproveOut.html?code=' + code + '&sarsId=' + sarsId+'&sarsType=1';
                // myOpen('方案等级确认', checkedData[0].recordid, 'schemeConfirmationDetailByJld')
                // console.log(sarsId)
				}
               
            } else if (checkedData.length == 0) {
                layer.alert("未选择任何项",{ icon: 0,},function (index) {
                    layer.close(index);
                });
            } else {
                layer.alert("只能选择一个",{ icon: 0,},function (index) {
                    layer.close(index);
                });
            }
            break;
        case "del":
            if (checkedData.length == 0) {
                layer.alert("未选择任何项",{ icon: 0,},function (index) {
                    layer.close(index);
                });
            } else {
                for(var i = 0; i < checkedData.length; i++){
                    if(checkedData[i].apih5FlowStatus == 1 && checkedData[i].apih5FlowStatus == 2){
                        layer.alert("流程已经发起不能删除!",{ icon: 0,},function (index) {
                            layer.close(index);
                        });
                        break;
                    }else if(i === checkedData.length - 1){
                        layer.confirm("确定删除？",{ icon: 0,},function (index) {
                            l.ajax("batchDeleteUpdateZjHwSars",checkedData,function () {
                                pager.page('remote')
                            })
                            layer.close(index);
                        });
                    }
                }
            }
            break;
    }
})
