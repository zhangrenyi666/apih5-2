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
            "data": "proName",//接口对应字段
            "title": "项目名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "proLeader",//接口对应字段
            "title": "项目负责人",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "ministerApproveTitle",//接口对应字段
            "title": "标题",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "createUserName",//接口对应字段
            "title": "发起人",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "createTime",//接口对应字段
            "title": "发起时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [7],//第几列
            "data": "apih5FlowStatus",//接口对应字段
            "title": "流程状态",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                if (data === '') {
                    return '暂存';
                } else if (data === '0') {
                    return '已发起';
                } else if (data === '1') {
                    return '审核中';
                } else if (data === '2') {
                    return '审核结束';
                } else if (data === '3') {
                    return '退回';
                } else {
                    return '--';
                }
            }
        },
        {
            "targets": [8],//第几列
            "width": 80,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//1：待办  2：未完成  3：延期审核中 4：完成审核中 5：已完成 6：未通过 
            "render": function (data) {
                var html = '<a style="color:blue;" href="./MinisterPerson.html?code=' + code + '&ministerApproveId=' + data.ministerApproveId + '">人员明细</a>';
                return html;
            }
        }
    ]
});

var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjFlowMinisterApproveList"),
        params: {
			// sarsType:"1"
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
            name: "ministerApproveId",//输入字段名
        },
        {
            type: "text",//
            name: "proName",//
            label: "项目名称",//
            placeholder: "请输入",
            must: true
        },
        {
            type: "text",//
            name: "proLeader",//
            label: "项目负责人",//
            placeholder: "请输入",
            must: true
        },
        {
            type: "text",//
            name: "ministerApproveTitle",//
            label: "标题",//
            placeholder: "请输入",
            must: true
        },
        {
            type: "upload",//
            name: "fileList",//附件表名
            label: "附件",//
            btnName: "添加附件",
            projectName: "zjSeal",
            // immutableAdd: true,
            uploadUrl: 'uploadFilesByFileName',
            fileType: ["jpg","jpeg","png","gif","docx","xls","doc","ppt","xlsx","pptx","xlsm","pdf"]
        }
    ],
    onSave: function (obj,_params) {
        l.ajax('updateZjFlowMinisterApprove',_params,function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj,_params) {
        l.ajax('addZjFlowMinisterApprove',_params,function (data) {
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
                if (checkedData[0].apih5FlowStatus) {
                    layer.alert("流程已经发起不能重复提交!",{ icon: 0,},function (index) {
                        layer.close(index);
                    });
                    
                } else {
                    if (checkedData[0].infoList.length>0) {
                        var ministerApproveId = checkedData[0].ministerApproveId;
                        window.location.href = './returnWorkApproveMinisterOut.html?code=' + code + '&ministerApproveId=' + ministerApproveId;
                    } else {
                        layer.alert("请先填写明细!",{ icon: 0,},function (index) {
                            layer.close(index);
                        });
                    }
                    
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
                            l.ajax("batchDeleteUpdateZjFlowMinisterApprove",checkedData,function () {
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
