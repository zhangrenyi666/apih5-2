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
            "width": 20,
            "title": '<input type="checkbox">',
            "render": function (data) {
                return '<input type="checkbox" name="checkbox" data-rowIndex="' + data + '" >';
            }
        },
		{
            "targets": [1],//第几列
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "主办部门",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data, index, row) {
                var title;
                if (data) {
                    title = '<a style="color:blue;"  onclick="detailOpen(\'详情\',\'' + JSON.stringify(data).replace(/"/g, '&quot;') + '\')" href="javascript:void(0);">' + data.oaUserName + '</a>'
                }
                return title;
            }
        },		
        {
            "targets": [2],//第几列
            "data": "fallInYear",//接口对应字段
            "title": "年度",//表头名
            "defaultContent": "-",//默认显示
        },
	    {
            "targets": [3],//第几列
            "data": "approvalState",//接口对应字段
            "title": "评审状态",//表头名
            "defaultContent": "-",//默认显示
			"render": function (data) {// 自定义输出
                var r = "未知";
                switch (data) {
				    case "0":
                        r = "未提交"
                        break
                    case "1":
                        r = "评审中"
                        break
                    case "2":
                        r = "通过"
                        break
                    case "3":
                        r = "驳回"
                        break
                }
                 return r
            }
        },        
        {
            "targets": [4],//第几列
            "data": "fallInUser",//接口对应字段
            "title": "填报人",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [5],//第几列
            "data": "fallInTime",//接口对应字段
            "title": "填报时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },		
		{
            "targets": [6],//第几列
            "data": "leaderOption",//接口对应字段
            "title": "部门负责人意见",//表头名
            "class":"text-overflow",
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [7],//第几列
            "data": "fallInRemakes",//接口对应字段
            "title": "备注",//表头名
            "defaultContent": "-",//默认显示
        },        
        {
            "targets": [8],//第几列
            "width": 100,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名
            "render": function (data) {
                var html = '';
                html += '<span class="dropDown">'
                if(data.approvalState == "0"){
                    if(data.deptFlag == "0"){
                        html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" onclick="myOpen(\'新增会议\',\'' + data.fallInDeptId + '\',\'' + data.oaUserName + '\',\'' + 'ZjMeetingPlanMeeting' + '\')">新增会议</a>';
                    }else{
                        html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" onclick="myOpen(\'查看会议\',\'' + data.fallInDeptId + '\',\'' + data.oaUserName + '\',\'' + 'ZjMeetingPlanMeeting' + '\')">查看会议</a>';
                    }
                }else if(data.approvalState == "1" || data.approvalState == "2"){
                    html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" onclick="myOpen(\'查看会议\',\'' + data.fallInDeptId + '\',\'' + data.oaUserName + '\',\'' + 'ZjMeetingPlanMeeting' + '\')">查看会议</a>';
                }else{
                    if(data.deptFlag == "0"){
                        html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" onclick="myOpen(\'修改会议\',\'' + data.fallInDeptId + '\',\'' + data.oaUserName + '\',\'' + 'ZjMeetingPlanMeeting' + '\')">修改会议</a>';                    
                    }else{
                        html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" onclick="myOpen(\'查看会议\',\'' + data.fallInDeptId + '\',\'' + data.oaUserName + '\',\'' + 'ZjMeetingPlanMeeting' + '\')">查看会议</a>';
                    }                    
                    
                }
                html += '</span>'
                return html;
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
            name: "approvalState",
            label: "评审状态",
            selectList: [//当类型为select时，option配置  0：未审批 1：审批中  2：通过 3：未通过   4：问题关闭
                {
                    value: "",
                    text: "全部"
                },
                {
                    value: "0",
                    text: "未提交"
                },
                {
                    value: "1",
                    text: "评审中"
                },
                {
                    value: "2",
                    text: "通过"
                },
                {
                    value: "3",
                    text: "驳回"
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
        url: l.getApiUrl("getZjMeetingPlanFallInInfoListAddDept"),
        params: {
           
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
				//console.log(data)
                $.each(data, function (index, item) {
                    item.rowIndex = index
                })
                table.clear().rows.add(data).draw();
                $.each($(".text-overflow"), function (index, item) {
                    $(".text-overflow").eq(index + 1).attr({"data-toggle":"tooltip","data-placemen":"top","title":data[index]["leaderOption"]});
                })
                $(function () { $("[data-toggle='tooltip']").tooltip()});                 
            } else {
                layer.alert(result.message, { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
        },
    }
});
var addLayer = $('#addLayer').detailLayer({
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
            must: true,
            lineNum: 1            
        },        
        {
            type: 'date',//
            name: "fallInYearTime",//
            label: "年度",//
            dateFmt: "yyyy",
            defaultValue: new Date(),
            id: "fallInYearTime",
            must: true,            
            lineNum: 1
        },
        {
            type: "text",//
            name: "fallInUser",//
            label: "填报人",//
            must: true,            
        },
        {
            type: 'date',//
            name: "fallInTime",//
            label: "填报时间",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "fallInTime",
            must: true,            
        },
        {
            type: "textarea",//
            name: "fallInRemakes",//
            label: "备注",//
        }
    ],
    onAdd: function (obj, _params) {     
            l.ajax('addZjMeetingPlanFallInInfoAddDept', _params, function (data,message,success) {
             if(success = "true"){
                layer.alert("新增成功！", { icon: 1, }, function (index) {
                    layer.close(index);
                    pager.page('remote')
                    obj.close()		                    
                });                 
				}else{
                pager.page('remote')	
				}
            })
    }
})

var editLayer = $('#editLayer').detailLayer({
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
            must: true,            
            lineNum: 1
        },
        {
            type: "text",//
            name: "fallInUser",//
            label: "填报人",//
            must: true,            
        },
        {
            type: 'date',//
            name: "fallInTime",//
            label: "填报时间",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "fallInTime",
            must: true,            
        },
        {
            type: "textarea",//
            name: "fallInRemakes",//
            label: "备注",//
        }
    ],
    onSave: function (obj, _params) {
            l.ajax('updateZjMeetingPlanFallInInfoAddDept', _params, function (data,message,success) {
             if(success = "true"){
                layer.alert("修改成功！", { icon: 1, }, function (index) {
                    layer.close(index);
                    pager.page('remote')
                    obj.close()		                    
                });  	
				}else{
                pager.page('remote')	
				}                
            })    
    },
})

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
function detailOpen(title,rowIndex){
    $(".layui-layer-title").html(title);    
    $('#detailLayer .btn').hide();    
    detailLayer.open(JSON.parse(rowIndex));    
}
function myOpen(title, id, sponsoringDept, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?&code=" + code+"&id="+id+"&sponsoringDept="+sponsoringDept
    }
    myOpenLayer = layer.open(options)
    layer.full(myOpenLayer)
}
function myOpenApply(title, id, url, flag) {
    var options = {
        type: 2,
        title: title,    
        content: url + ".html?&code=" + code+"&id="+id+"&flag="+flag
    }
    myOpenLayer = layer.open(options)
    layer.full(myOpenLayer)
}
//请求数据给第一个表单赋值
myRefresh();
function myRefresh() {
//flag:0；申请人身份；1:审批人身份
var param = null;
l.ajax('getZjMeetingPlanIsLeaderJurisdiction', null, function (data) {
    if(data == "1"){
        $("#add").hide();
        $("#edit").hide();
        $("#del").hide();
        $("#apply").hide();
    }else if(data == "0"){  
        $("#approval").hide();  
    }    
})
}
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
		case "add":
            addLayer.open();
            // myOpen('会议室申请发起','','checkAskDetial')
            break;
        case "edit":
            if (checkedData.length == 1) {
                if(checkedData[0].approvalState == "0" || checkedData[0].approvalState == "3"){
                    editLayer.open(checkedData[0]);	
                }else{
                    layer.alert("已经发送申请的项目无法修改！", { icon: 0, }, function (index) {
                        layer.close(index);
                    });                    
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
	  case "del":
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.confirm("确定删除？", { icon: 0, }, function (index) {
                    for(var i=0; i<checkedData.length; i++){
                        if(checkedData[i].approvalState == "1" || checkedData[i].approvalState == "2"){
                            layer.alert("所选的项目中包含审批中或已审批的数据！", { icon: 0 }, function (index) {
                                layer.close(index);
                            });
                            break;                            
                        }else if(checkedData[i].deptFlag == "1"){
                            layer.alert("所选的项目中包含其他部门的数据，没有权限删除！", { icon: 0 }, function (index) {
                                layer.close(index);
                            });
                            break; 
                        }else if(i === (checkedData.length-1)){
                            l.ajax("batchDeleteUpdateZjMeetingPlanFallInInfo", checkedData, function () {
                                layer.alert("删除成功！", { icon: 1, }, function (index) {
                                    layer.close(index);
                                    pager.page('remote')	                    
                                });                                 
                            })
                            layer.close(index);
                        }                        
                    }
                });
            }
            break;			
		case "apply":
            if (checkedData.length == 1) {
                if(checkedData[0].approvalState == "0" || checkedData[0].approvalState == "3"){
                    myOpenApply("年度计划评审申请", checkedData[0].fallInDeptId, "zjMeetingPlanReviewApply","0");	
                }else{
                    layer.alert("该项目已经发送申请了！", { icon: 0, }, function (index) {
                        layer.close(index);
                    });                    
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
		case "approval":
            if (checkedData.length == 1) {
                if(checkedData[0].approvalState == "1"){
                    if(checkedData[0].leaderFlag == "0"){
                        myOpenApply("年度计划评审审批", checkedData[0].fallInDeptId, "zjMeetingPlanReviewApply","1");
                    }else{
                        layer.alert("无权限审批该项目！", { icon: 0, }, function (index) {
                            layer.close(index);
                        });                         
                    }
                }else if(checkedData[0].approvalState == "2" || checkedData[0].approvalState == "3"){
                    layer.alert("该项目已经审批过了！", { icon: 0, }, function (index) {
                        layer.close(index);
                    }); 
                }else{
                    layer.alert("未发起的项目无法审批！", { icon: 0, }, function (index) {
                        layer.close(index);
                    });                     
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
    }
})
