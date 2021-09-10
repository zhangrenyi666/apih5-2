var code = Apih5.getUrlParam('code');
Apih5.setCookie('code', code, 30);
var flowIdFlag = Apih5.getUrlParam('flowId');

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
			"width": 300,
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "sendUserName",//接口对应字段
            "title": "发起人",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "width": 150,
            "data": "createTime",//接口对应字段
            "title": "发起时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [5],//第几列
            "data": "flowStatus",//接口对应字段
            "title": "流程状态",//表头名
            "defaultContent": "-",//默认显示 
        }
    ]
});
var pager = $("#pager").page({
    remote: {// ajax请求配置
        url: l.getApiUrl("getHasTodoList"),
        params: {
            flowId: 'zjypFlow'
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


var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "sendUserName",//输入字段名
            label: "发起人",//输入字段对杨的中文名称
            placeholder: "请输入发起人",
        },
        {
            type: "text",//三种形式：text,select,date,
            name: "keyword",//输入字段名
            label: "标题",//输入字段对杨的中文名称
            placeholder: "请输入标题",
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


$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");

    switch (name) {
        case "process":
            if (checkedData.length == 1) {
                myOpen("流程处理", checkedData[0], "process")
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
        case "process1":
            if (checkedData.length == 1) {
                myOpen("流程处理", checkedData[0], "zjYyOutSealApplyProcess")
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
        case "process2":
            if (checkedData.length == 1) {
                myOpen("流程处理", checkedData[0], "zjInfoNeedsConfirmProcess")
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
        case "process3":
            if (checkedData.length == 1) {
                myOpen("流程处理", checkedData[0], "zjInfoProjectApplyProcess")
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
        case "derivePerson"://临聘人员导出
            var workIdList = '';
            var notExport = [];
            if (checkedData.length > 0) {
                for (var i = 0; i < checkedData.length; i++) {
                    if (checkedData[i].flowId === 'zjypFlow') {
                        if (i < checkedData.length - 1) {
                            workIdList += '\'' + checkedData[i].workId + '\',';
                        } else {
                            workIdList += '\'' + checkedData[i].workId + '\''
                        }
                    } else {
                        notExport.push(checkedData[i].workId);
                    }
                }
                if (notExport.length > 0) {
                    layer.alert("请选择临聘人员流程导出", { icon: 0, }, function (index) {
                        layer.close(index);
                        pager.page('remote')
                    });
                } else {
                    layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
                        window.location.href = 'http://192.168.1.223:89/ureport/excel?_u=file:sanZjFlowPersonInfo.xml&url=' + l.domain + '&delFlag=0&workIdList=' + workIdList;
                        layer.close(index);
                        pager.page('remote')
                    });
                }
            } else {
                layer.alert("请选择数据", { icon: 0, }, function (index) {
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
                layer.confirm("删除的数据无法恢复，请谨慎操作！！！确定删除？", { icon: 0, }, function (index) {
                    l.ajax("batchDeleteUpdateZjFlowTodo", checkedData, function () {
                        pager.page('remote')
                    })
                    layer.close(index);
                });
                /* for(var i=0;i<checkedData.length;i++){
                       if(checkedData[i].flowStatus != '退回'){
                           layer.alert("包含未退回的审批，不能删除，请重新选择！", { icon: 0, }, function (index) {
                           layer.close(index);
                          });	
                         break;					
                       }else{
                           layer.confirm("确定删除？", { icon: 0, }, function (index) {
                               l.ajax("batchDeleteUpdateZjFlowTodo", checkedData, function () {
                                pager.page('remote')
                               })
                             layer.close(index);
                           });
                       }
                   }	 */
            }
            break;
    }
})


var myOpenLayer;
function myOpen(title, data, url) {
    if (data.flowId == 'zjypFlow') {//海外
        url = 'returnWorkOutProcess'
    }


    var options = {
        type: 2,
        title: title,
        content: url + ".html?workId=" + data.workId + "&trackId=" + data.trackId + "&nodeId=" + data.nodeId + "&flowId=" + data.flowId + "&title=" + data.title + "&code=" + code + "&nodeName=" + data.nodeName
    }
    myOpenLayer = layer.open(options)
    layer.full(myOpenLayer)
}
