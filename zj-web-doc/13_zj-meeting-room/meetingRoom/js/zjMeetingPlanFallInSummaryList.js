var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
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
            "data": "fallInYear",//接口对应字段
            "title": "年度",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [2],//第几列
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "上报部门个数",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data, index, row) {
                var title;
                if (data) {
                    title = '<a style="color:blue;"  onclick="detailOpen(\'查看\',\'' + data.fallInYear + '\',\'zjMeetingPlanFallInSummaryDetail\')" href="javascript:void(0);">' + data.deptNum + '</a>'
                }
                return title;
            }
        },             
	    {
            "targets": [3],//第几列
            "data": "yearApprovalState",//接口对应字段
            "title": "评审状态",//表头名
            "defaultContent": "-",//默认显示
			"render": function (data) {// 自定义输出
                var r = "未知";
                switch (data) {
				    case "0":
                        r = "未发起"
                        break
                    case "1":
                        r = "正在审批"
                        break
                    case "2":
                        r = "审批通过"
                        break
                }
                 return r
            }
        }           
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "date",//
            name: "fallInYearTime",//
            label: "年度",//
            dateFmt: "yyyy",
            id: "fallInYearTime"
        },
        {
            type: "select",
            name: "yearApprovalState",
            label: "评审状态",
            selectList: [//当类型为select时，option配置  0：未审批 1：审批中  2：通过 3：未通过   4：问题关闭
                {
                    value: "",
                    text: "全部"
                },
                {
                    value: "0",
                    text: "未发起"
                },
                {
                    value: "1",
                    text: "正在审批"
                },
                {
                    value: "2",
                    text: "审批通过"
                }
            ],
        },          
    ],
    onSearch: function (arr) {//搜索按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].name == "fallInYearTime") {
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
        url: l.getApiUrl("getZjMeetingPlanFallInSummaryList"),
        params: {
            approvalState:2
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
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
	layerArea:['60%', '50%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "fallInDeptId",//输入字段名
        },
        {
            type: "pickTree",
            name: "fallInDeptList",
            label: "部门",
            pickType: {
                department: "oaDepartmentList",// 部门列表对应接口字段名,false表示不开启该类
                member: false,// 成员列表对应接口字段名,false表示不开启该类
            },
            immutableAdd: true,
            lineNum: 1            
        },        
        {
            type: 'date',//
            name: "fallInYearTime",//
            label: "年度",//
            dateFmt: "yyyy",
            defaultValue: new Date(),
            id: "fallInYearTime",
            immutableAdd: true,            
            lineNum: 1
        },
        {
            type: "text",//
            name: "fallInUser",//
            label: "填报人",//
            immutableAdd: true,          
        },
        {
            type: 'date',//
            name: "fallInTime",//
            label: "填报时间",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "fallInTime",
            immutableAdd: true,            
        },
        {
            type: "textarea",//
            name: "fallInRemakes",//
            label: "备注",//
            immutableAdd: true,            
        }
    ]
})
function detailOpen(title, fallInYear, url) {
    var options = {
        type: 2,
        title: title,
        area: ['85%', '70%'],        
        content: url + ".html?&code=" + code+"&fallInYear="+fallInYear
    }
    myOpenLayer = layer.open(options)
    // layer.full(myOpenLayer)
}
function myOpenApply(title, id,fallInYear, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?code=" + code+"&deptId="+id+"&fallInYear="+fallInYear
    }
    myOpenLayer = layer.open(options)
    layer.full(myOpenLayer)
}
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {		
            case "apply":
                if (checkedData.length == 1) {
                    myOpenApply("年度计划评审申请", checkedData[0].fallInDeptId,checkedData[0].fallInYear, "zjMeetingPlanSummaryApprove");	
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
            case "return":
                if (checkedData.length == 0) {
                    layer.alert("未选择任何项", { icon: 0 }, function (index) {
                        layer.close(index);
                    });
                } else {
                    layer.confirm("确定退回？", { icon: 0, }, function (index) {
                        for(var i=0; i<checkedData.length; i++){
                            if(checkedData[i].yearApprovalState != "0"){
                                layer.alert("所选的项目中包含审批中或已审批的数据！", { icon: 0 }, function (index) {
                                    layer.close(index);
                                });
                                break;                            
                            }else if(i === (checkedData.length-1)){
                                l.ajax("zjMeetingPlanSummaryReturn", checkedData, function () {
                                    layer.alert("退回成功！", { icon: 1 }, function (index) {
                                        pager.page('remote')                                        
                                        layer.close(index);
                                    });                                    
                                })
                                layer.close(index);
                            }                        
                        }
                    });
                }
                break;	            
    }
})
