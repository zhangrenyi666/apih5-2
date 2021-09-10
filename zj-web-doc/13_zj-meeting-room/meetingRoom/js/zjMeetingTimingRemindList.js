var code = Apih5.getUrlParam('code');
Apih5.setCookie('code',code,30);
var table = $('#table').DataTable({
    "info": false,// 是否显示数据信息
    "paging": false,// 是否开启自带分页
    "ordering": false, // 是否开启DataTables的高度自适应
    "processing": false,// 是否显示‘进度’提示
    "searching": false,// 是否开启自带搜索
    "autoWidth": false,// 是否监听宽度变化
    "columnDefs": [// 表格列配置
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
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "会议名称",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data,index,row) {
                var title;
                if (data) {
                    if(data.typeFlag === "0"){
                        title = '<a style="color:blue;"  onclick="flowDelOpen(\'详情\',\'' + data.rowIndex + '\')" href="javascript:void(0);">' + data.meetingRoomTitle + '</a>'
                    }else if(data.typeFlag === "2"){
                        title = '<a style="color:blue;"  onclick="delOpen(\'详情\',\'' + data.rowIndex + '\')" href="javascript:void(0);">' + data.meetingRoomTitle + '</a>'
                    }
                }
                return title;
            }
        },
        {
            "targets": [2],// 第几列
            "data": "meetingPlace",// 接口对应字段
            "title": "会议地点",// 表头名
            "defaultContent": "-"// 默认显示
        },
        {
            "targets": [3],//第几列
            "data": "meetingStartTime",//接口对应字段
            "title": "会议开始时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data),"yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [4],//第几列
            "data": "meetingEndTime",//接口对应字段
            "title": "会议结束时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data),"yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [5],// 第几列
            "data": "remindTime",// 接口对应字段
            "title": "会议提醒时间",// 表头名
            "defaultContent": "-"// 默认显示
        },
        {
            "targets": [6],// 第几列
            "data": "modifyUserName",// 接口对应字段
            "title": "操作人",// 表头名
            "defaultContent": "-"// 默认显示
        },
        {
            "targets": [7],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "创建时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data),"yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [8],//第几列
            "width": 80,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名
            "render": function (data) {
                // console.log(data.typeFlag === "0");
                var html = '';
                html += '<span class="dropDown">'
                if (data.typeFlag === "0") {//流程会议 confirmTableRow('+data.rowIndex+')"
                    html += '<a style="color:blue;" href="javascript:void(0);" onclick="myOpenFlow(\'修改流程会议\',\'' + data.rowIndex + '\')">修改</a>';
                } else if (data.typeFlag === "1") {//内部会议
                    html += '<a style="color:blue;" href="javascript:void(0);" onclick="myOpenInside(\'修改内部会议\',\'' + data.rowIndex + '\')">修改</a>';
                } else if (data.typeFlag === "2") {//新增内部会议
                    html += '<a style="color:blue;" href="javascript:void(0);" onclick="myOpenInside(\'修改新增内部会议\',\'' + data.rowIndex + '\')">修改</a>';
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
            type: "text",//三种形式：text,select,date,
            name: "meetingTitle",//输入字段名
            label: "会议名称",//输入字段对杨的中文名称
            placeholder: "请输入会议名称"
        }
    ],
    onSearch: function (arr) {//搜索按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].name == "startDate" || arr[i].name == "endDate") {
                if (arr[i].value) {
                    _params[arr[i].name] = l.dateFormat(new Date(new Date(arr[i].value).getTime()),"yyyy-MM-dd");
                } else {
                    _params[arr[i].name] = "";
                }
            } else {
                _params[arr[i].name] = arr[i].value;
            }
        }
        pager.page('remote',0,_params)
    },
    onReset: function (arr) {//重置按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
        pager.page('remote',_params)
    }
})
var pager = $("#pager").page({
    remote: {// ajax请求配置
        url: l.getApiUrl("getZjMeetingReserveTimingRemindList"),
        params: {
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
        },
    }
});

var detailLayer = $('#detailLayer').detailLayer({
    layerArea: ['70%','75%'],
    conditions: [
        {
            type: "date",//
            name: "meetingStartTime",//
            label: "开始时间",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            must: true,
            lineNum: 1
        },
        {
            type: "date",//
            name: "meetingEndTime",//
            label: "结束时间",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            must: true,
            lineNum: 1
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "remindId"//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "insideId"//输入字段名
        },        
        {
            type: "text",//
            name: "meetingRoomTitle",//
            label: "会议名称",//
            placeholder: "请输入会议名称",
            must: true	
        },
        {
            type: "text",//
            name: "meetingPlace",//
            label: "会议地点",//
            placeholder: "请输入会议地点",
            must: true

        },        
        {
            type: "textarea",//
            name: "meetingRoomContent",//
            label: "会议内容",//
            placeholder: "请输入会议内容"
        },
        {
            type: "text",//
            name: "compere",//
            label: "主持人",//
            placeholder: "请输入会议名称"
        },
        {
            type: "pickTree",
            name: "meetingPersonList",
            label: "参会人员",
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList"//成员列表对应接口字段名,false表示不开启该类
            }
        },
        {
            type: "pickTree",
            name: "meetingDepartmentList",
            label: "参会部门",
            pickType: {
                department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
                member: false//成员列表对应接口字段名,false表示不开启该类
            }
            // must: true,
        },
        {
            type: "checkbox",//
            name: "remindTimeStr",//
            label: "会议提醒",//   
            optionData: [
                {
                    label: "1小时",
                    value: "1小时",
                },
                {
                    label: "3小时",
                    value: "3小时"
                },
                {
                    label: "24小时",
                    value: "24小时"
                }
            ]
        }
    ],
    onSave: function (obj,_params) {
        l.ajax('updateZjMeetingReserveTimingRemind',_params,function (data, message, success) {
            if(success){
                layer.alert(message,{ icon: 1,},function (index) {
                    layer.close(index);
                    pager.page('remote')
                    obj.close()                
                });   
            }
        })
    },
    onAdd: function (obj,_params) {
        l.ajax('addZjMeetingReserveTimingRemind',_params,function (data, message, success) {
            if(success){
                layer.alert(message,{ icon: 1,},function (index) {
                    layer.close(index);
                    pager.page('remote')
                    obj.close()                
                });   
            }
        })
    }
})
//新增提醒详情查看
var delLayer = $('#delLayer').detailLayer({
    layerArea: ['70%','75%'],
    conditions: [
        {
            type: "date",//
            name: "meetingStartTime",//
            label: "开始时间",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            immutableAdd:true,
            lineNum: 1
        },
        {
            type: "date",//
            name: "meetingEndTime",//
            label: "结束时间",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            immutableAdd:true,
            lineNum: 1
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "remindId"//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "insideId"//输入字段名
        },        
        {
            type: "text",//
            name: "meetingRoomTitle",//
            label: "会议名称",//
            placeholder: "请输入会议名称",
            immutableAdd:true
        },
        {
            type: "text",//
            name: "meetingPlace",//
            label: "会议地点",//
            placeholder: "请输入会议地点",
            immutableAdd:true

        },        
        {
            type: "textarea",//
            name: "meetingRoomContent",//
            label: "会议内容",//
            placeholder: "请输入会议内容",
            immutableAdd:true
        },
        {
            type: "text",//
            name: "compere",//
            label: "主持人",//
            placeholder: "请输入会议名称",
            immutableAdd:true
        },
        {
            type: "pickTree",
            name: "meetingPersonList",
            label: "参会人员",
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList"//成员列表对应接口字段名,false表示不开启该类
            },
            immutableAdd:true
        },
        {
            type: "pickTree",
            name: "meetingDepartmentList",
            label: "参会部门",
            pickType: {
                department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
                member: false//成员列表对应接口字段名,false表示不开启该类
            },
            immutableAdd:true
        },
        {
            type: "checkbox",//
            name: "remindTimeStr",//
            label: "会议提醒",//   
            immutableAdd:true,            
            optionData: [
                {
                    label: "1小时",
                    value: "1小时",
                },
                {
                    label: "3小时",
                    value: "3小时"
                },
                {
                    label: "24小时",
                    value: "24小时"
                }
            ]
        }
    ],
})
//流程会议提醒
var flowDetailLayer = $('#flowDetailLayer').detailLayer({
    layerArea: ['70%','75%'],
    conditions: [
        {
            type: "date",//
            name: "meetingStartTime",//
            label: "开始时间",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            // defaultValue: new Date(),
            must: true,
            immutableAdd:true,
            lineNum: 1
        },
        {
            type: "date",//
            name: "meetingEndTime",//
            label: "结束时间",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            // defaultValue: new Date(),
            must: true,
            immutableAdd:true,            
            lineNum: 1
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "remindId"//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "reservationsId"//输入字段名
        },              
        {
            type: "select",//
            name: "flowMeetingId",//
            label: "会议名称",        
            search: true,    
            placeholder: "请输入会议地点",
            ajax: {
                api: "getZjMeetingFlowSelectAllList",//api名称
                valueName: "recordid",//设置value对应的接口字段名称
                textName: "meetingRoomTitle"//设置text对应的接口字段名称 
            },
            must: true,
            linkage: {
                //字段:下拉值
                meetingRoomContent: "meetingContent",
                meetingRoomName: "meetingRoomName",
                meetingStartTime:"meetingStartTime",
                meetingEndTime:"meetingEndTime",
                compere:"convenor"
            }
        },       
        {
            type: "text",//
            name: "meetingRoomName",//
            label: "会议地点",//
            placeholder: "请输入会议名称",
            immutableAdd:true,
            must: true

        },
        {
            type: "textarea",//
            name: "meetingRoomContent",//
            label: "会议内容",//
            // placeholder: "请输入会议内容",
            immutableAdd:true           
        },
        {
            type: "text",//
            name: "compere",//
            label: "主持人",//
            // placeholder: "请输入主持人",
            immutableAdd:true           
        },
        {
            type: "pickTree",
            name: "meetingPersonList",
            label: "参会人员",
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList"//成员列表对应接口字段名,false表示不开启该类
            }
        },
        {
            type: "pickTree",
            name: "meetingDepartmentList",
            label: "参会部门",
            pickType: {
                department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
                member: false//成员列表对应接口字段名,false表示不开启该类
            }
            // mus: true,
        },
        {
            type: "checkbox",//
            name: "remindTimeStr",//
            label: "会议提醒",//   
            optionData: [
                {
                    label: "1小时",
                    value: "1小时",
                },
                {
                    label: "3小时",
                    value: "3小时"
                },
                {
                    label: "24小时",
                    value: "24小时"
                }
            ]
        }
    ],
    onSave: function (obj,_params) {
        l.ajax('updateZjFlowMeetingReserveTimingRemind',_params,function (data, message, success) {
            if(success){
                layer.alert(message,{ icon: 1,},function (index) {
                    layer.close(index);
                    pager.page('remote')
                    obj.close()                
                });   
            }
        })
    },
    onAdd: function (obj,_params) {
        l.ajax('addZjFlowMeetingReserveTimingRemind',_params,function (data, message, success) {
            if(success){
                layer.alert(message,{ icon: 1,},function (index) {
                    layer.close(index);
                    pager.page('remote')
                    obj.close()                
                });   
            }
        })
    }
})
//流程会议提醒详情
var flowDelLayer = $('#flowDelLayer').detailLayer({
    layerTitle:['详情'],
    layerArea: ['70%','75%'],
    conditions: [
        {
            type: "date",//
            name: "meetingStartTime",//
            label: "开始时间",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            // defaultValue: new Date(),
            immutableAdd:true,
            lineNum: 1
        },
        {
            type: "date",//
            name: "meetingEndTime",//
            label: "结束时间",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            // defaultValue: new Date(),
            immutableAdd:true,            
            lineNum: 1
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "remindId"//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "reservationsId"//输入字段名
        },              
        {
            type: "select",//
            name: "flowMeetingId",//
            label: "会议名称",        
            immutableAdd:true,  
            // search: true, 
            placeholder: "请输入会议地点",
            ajax: {
                api: "getZjMeetingFlowSelectAllList",//api名称
                valueName: "recordid",//设置value对应的接口字段名称
                textName: "meetingRoomTitle"//设置text对应的接口字段名称 
            },
            linkage: {
                //字段:下拉值
                meetingRoomContent: "meetingContent",
                meetingRoomName: "meetingRoomName",
                meetingStartTime:"meetingStartTime",
                meetingEndTime:"meetingEndTime",
                compere:"convenor"
            }
        },        
        {
            type: "text",//
            name: "meetingRoomName",//
            label: "会议地点",//
            placeholder: "请输入会议名称",
            immutableAdd:true
        },
        {
            type: "textarea",//
            name: "meetingRoomContent",//
            label: "会议内容",//
            // placeholder: "请输入会议内容",
            immutableAdd:true          
        },
        {
            type: "text",//
            name: "compere",//
            label: "主持人",//
            // placeholder: "请输入主持人",
            immutableAdd:true          
        },
        {
            type: "pickTree",
            name: "meetingPersonList",
            label: "参会人员",
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList"//成员列表对应接口字段名,false表示不开启该类
            },
            immutableAdd:true  
        },
        {
            type: "pickTree",
            name: "meetingDepartmentList",
            label: "参会部门",
            pickType: {
                department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
                member: false//成员列表对应接口字段名,false表示不开启该类
            },
            immutableAdd:true             
            // must: true,
        },
        {
            type: "checkbox",//
            name: "remindTimeStr",//
            label: "会议提醒",// 
            immutableAdd:true,              
            optionData: [
                {
                    label: "1小时",
                    value: "1小时",
                },
                {
                    label: "3小时",
                    value: "3小时"
                },
                {
                    label: "24小时",
                    value: "24小时"
                }
            ]
        }
    ],
})
//内部会议提醒
var inSideDetailLayer = $('#inSideDetailLayer').detailLayer({
    layerArea: ['70%','75%'],
    conditions: [
        {
            type: "date",//
            name: "meetingStartTime",//
            label: "开始时间",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            must: true,
            lineNum: 1
        },
        {
            type: "date",//
            name: "meetingEndTime",//
            label: "结束时间",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            must: true,
            lineNum: 1
        },
        {
            type: "text",//
            name: "meetingRoomTitle",//
            label: "会议名称",//
            placeholder: "请输入会议名称",
            must: true

        },
        {
            type: "select",//
            name: "meetingRoomId",//
            label: "会议地点",        
            search: true,    
            placeholder: "请输入会议地点",
            ajax: {
                api: "getZjMeetingInsideMeetingSelectAllList",//api名称
                valueName: "insideMeetingId",//设置value对应的接口字段名称
                textName: "insideMeetingTitle"//设置text对应的接口字段名称 
            },
            must: true,
            linkage: {
                //字段:下拉值
                meetingRoomContent: "meetingContent",
                meetingRoomTitle1: "meetingRoomTitle",
                meetingStartTime:"meetingStartTime",
                meetingEndTime:"meetingEndTime"
            },

        },        
        {
            type: "textarea",//
            name: "meetingRoomContent",//
            label: "会议内容",//
            placeholder: "请输入会议内容"
        },
        {
            type: "text",//
            name: "meetingRoomTitle",//
            label: "主持人",//
            placeholder: "请输入会议名称"
        },
        {
            type: "pickTree",
            name: "meetingPersonList",
            label: "参会人员",
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList"//成员列表对应接口字段名,false表示不开启该类
            }
        },
        {
            type: "pickTree",
            name: "meetingDepartmentList",
            label: "参会部门",
            pickType: {
                department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
                member: false//成员列表对应接口字段名,false表示不开启该类
            },
            must: true
        },
        {
            type: "checkbox",//
            name: "remindTimeStr",//
            label: "会议提醒",//   
            optionData: [
                {
                    label: "1小时",
                    value: "1小时",
                },
                {
                    label: "3小时",
                    value: "3小时"
                },
                {
                    label: "24小时",
                    value: "24小时"
                }
            ]
        }
    ],
    onSave: function (obj,_params) {
        l.ajax('addZjMeetingReserveTimingRemind',_params,function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj,_params) {
        l.ajax('addZjMeetingEditPersonnel',_params,function (data) {
            pager.page('remote')
            obj.close()
        })
    }
})
function myOpenInside(title,rowIndex) {
    var checkedData=table.row(rowIndex).data();
    detailLayer.open(checkedData);         
}    
function myOpenFlow(title,rowIndex) {
    var checkedData=table.row(rowIndex).data();
    flowDetailLayer.open(checkedData);         
}    
function flowDelOpen(title,rowIndex) {
    var checkedData=table.row(rowIndex).data();
    flowDelLayer.open(checkedData);
    $('#flowDelLayer .btn').hide();       
}    
function delOpen(title,rowIndex) {
    var checkedData=table.row(rowIndex).data();
    delLayer.open(checkedData);
    $('#delLayer .btn').hide();       
}    

$("body").on("click","button",function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            detailLayer.open();
            break;
        case "addInside":
            inSideDetailLayer.open();
            break;
        case "addFlow":
            flowDetailLayer.open();
            break;
        case "del":
            if (checkedData.length == 0) {
                layer.alert("未选择任何项",{ icon: 0},function (index) {
                    layer.close(index);
                });
            } else {
                layer.confirm("确定删除？",{ icon: 3},function (index) {
                    l.ajax("batchDeleteZjMeetingTimingRemindAndPerson",checkedData,function (data, message, success) {
                        if(success){
                            layer.alert(message,{ icon: 1},function (index) {
                                layer.close(index);
                                pager.page('remote')             
                            });   
                        }
                    })
                    layer.close(index);
                });
            }
            break;
        case "send":
                layer.confirm("确定发送？",{ icon: 3},function (index) {
                    l.ajax("zjMeetingRoomRemindSendMessage",checkedData,function (data, message, success) {
                        if(success){
                            layer.alert(message,{ icon: 1},function (index) {
                                layer.close(index);
                                pager.page('remote')             
                            });   
                        }
                    })
                    layer.close(index);
                });
            break;
    }
}); 