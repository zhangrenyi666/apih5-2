var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
var table = $('#table').DataTable({
    "info": false,
    "paging": false,
    "ordering": false,
    "processing": false,
    "searching": false,
    "autoWidth": false,
    "columnDefs": [
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
            "targets": [1],
            "data": "createUserName",
            "title": "被考核人",
            "defaultContent": "-"
        },
        {
            "targets": [2],
            "data": "assessmentYears",
            "title": "考核月度",
            "defaultContent": "-",
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM");
            }
        },
        // {
        //     "targets": [3],
        //     "data": "assessmentQuarter",
        //     "title": "考核月度",
        //     "defaultContent": "-",
        //     "render": function (data) {
        //         if (data === "0") {
        //             return '第一月度'
        //         } else if (data === "1") {
        //             return '第二月度'
        //         } else if (data === "2") {
        //             return '第三月度'
        //         } else if (data === "3") {
        //             return '第四月度'
        //         } else if (data === '4') {
        //             return '第五月度';
        //         } else if (data === '5') {
        //             return '第六月度';
        //         } else if (data === '6') {
        //             return '第七月度';
        //         } else if (data === '7') {
        //             return '第八月度';
        //         } else if (data === '8') {
        //             return '第九月度';
        //         } else if (data === '9') {
        //             return '第十月度';
        //         } else if (data === '10') {
        //             return '第十一月度';
        //         } else if (data === '11') {
        //             return '第十二月度';
        //         } else {
        //             return '--'
        //         }
        //     }
        // },
        {
            "targets": [3],
            "data": "assessmentType",
            "title": "考核类别",
            "defaultContent": "-",
            "render": function (data) {
                if (data === '0') {
                    return '全部';
                } else if (data === '1') {
                    return '项目领导班子副职';
                } else if (data === '2') {
                    return '项目中层干部';
                } else if (data === '3') {
                    return '项目员工';
                } else {
                    return '--';
                }
            }
        },
        {
            "targets": [4],
            "data": "firstDutyClosingEndDate",
            "title": "审核截止日期",
            "defaultContent": "-",
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [5],
            "data": "firstScoreClosingEndDate",
            "title": "评分截止日期",
            "defaultContent": "-",
            "render": function (data) {
                if(data){
                    return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
                }else{
                    return "";
                }
            }
        },            
        {
            "targets": [6],
            "data": "modifyTime",
            "title": "发起时间",
            "defaultContent": "-",
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [7],//第几列
            "width": 100,
            "data": function (row) {
                return row
            },
            "title": "操作", 
            "render": function (data) {
                //领导班子副职
                if(data.assessmentType === '1'){
                    if(data.assessmentState === '1' || data.assessmentState === '4'){//项目主管领导审批
                        return '<a style="color:blue;" onclick="myOpenEJH(\'项目主管领导审核\',\'' + data.createUserName + '\',\'' + data.executiveId + '\',\'' + data.assessmentTitle + '\',\'' + data.position + '\',\'' + data.departmentName + '\',\'' + data.assessmentState + '\',\'' + data.chargeLeaderOption + '\',\'' + data.executiveLeaderOption + '\',\'' + data.assistantLock + '\',\'' + data.managerId + '\',\'' + data.assistantLeaderApprovalId + '\',\'' + data.haveChangerLeader + '\',\'' + data.leaderOption + '\',\'' + data.assessmentType + '\',\'' + 'LeadTheBacklogEJH' + '\')">项目主管领导审核</a>';
                    }else if(data.assessmentState === '3'){//公司分管领导
                        return '<a style="color:blue;" onclick="myOpenEJH(\'公司分管领导审核\',\'' + data.createUserName + '\',\'' + data.executiveId + '\',\'' + data.assessmentTitle + '\',\'' + data.position + '\',\'' + data.departmentName + '\',\'' + data.assessmentState + '\',\'' + data.chargeLeaderOption + '\',\'' + data.executiveLeaderOption + '\',\'' + data.assistantLock + '\',\'' + data.managerId + '\',\'' + data.assistantLeaderApprovalId + '\',\'' + data.haveChangerLeader + '\',\'' + data.leaderOption + '\',\'' + data.assessmentType + '\',\'' + 'LeadTheBacklogEJH' + '\')">公司分管领导审核</a>';
                    }else if(data.assessmentState === '6'){//项目主管领导评分
                        return '<a style="color:blue;" onclick="myOpenEKH(\'项目主管领导评分\',\'' + data.createUserName + '\',\'' + data.executiveId + '\',\'' + data.assessmentTitle + '\',\'' + data.position + '\',\'' + data.departmentName + '\',\'' + data.assessmentState + '\',\'' + data.assistantLock + '\',\'' + data.managerId + '\',\'' + data.assistantLeaderApprovalId + '\',\'' + data.haveChangerLeader + '\',\'' + data.leaderOption + '\',\'' + data.assessmentType + '\',\'' + 'LeadTheBacklogEKH' + '\')">项目主管领导评分</a>';
                    }else if(data.assessmentState === '7'){//公司分管领导评分
                        return '<a style="color:blue;" onclick="myOpenEKH(\'公司分管领导评分\',\'' + data.createUserName + '\',\'' + data.executiveId + '\',\'' + data.assessmentTitle + '\',\'' + data.position + '\',\'' + data.departmentName + '\',\'' + data.assessmentState + '\',\'' + data.assistantLock + '\',\'' + data.managerId + '\',\'' + data.assistantLeaderApprovalId + '\',\'' + data.haveChangerLeader + '\',\'' + data.leaderOption + '\',\'' + data.assessmentType + '\',\'' + 'LeadTheBacklogEKH' + '\')">公司分管领导评分</a>';
                    }
                }else if(data.assessmentType === '2'){
                    if(data.assessmentState === '1' || data.assessmentState === '4'){//项目分管领导审批
                        return '<a style="color:blue;" onclick="myOpenEJH(\'项目分管领导审核\',\'' + data.createUserName + '\',\'' + data.executiveId + '\',\'' + data.assessmentTitle + '\',\'' + data.position + '\',\'' + data.departmentName + '\',\'' + data.assessmentState + '\',\'' + data.chargeLeaderOption + '\',\'' + data.executiveLeaderOption + '\',\'' + data.assistantLock + '\',\'' + data.managerId + '\',\'' + data.assistantLeaderApprovalId + '\',\'' + data.haveChangerLeader + '\',\'' + data.leaderOption + '\',\'' + data.assessmentType + '\',\'' + 'LeadTheBacklogDJH' + '\')">项目分管领导审核</a>';
                    }else if(data.assessmentState === '3'){//项目主管领导审批
                        return '<a style="color:blue;" onclick="myOpenEJH(\'项目主管领导审核\',\'' + data.createUserName + '\',\'' + data.executiveId + '\',\'' + data.assessmentTitle + '\',\'' + data.position + '\',\'' + data.departmentName + '\',\'' + data.assessmentState + '\',\'' + data.chargeLeaderOption + '\',\'' + data.executiveLeaderOption + '\',\'' + data.assistantLock + '\',\'' + data.managerId + '\',\'' + data.assistantLeaderApprovalId + '\',\'' + data.haveChangerLeader + '\',\'' + data.leaderOption + '\',\'' + data.assessmentType + '\',\'' + 'LeadTheBacklogDJH' + '\')">项目主管领导审核</a>';
                    }else if(data.assessmentState === '6'){//项目分管领导评分
                        return '<a style="color:blue;" onclick="myOpenEKH(\'项目分管领导评分\',\'' + data.createUserName + '\',\'' + data.executiveId + '\',\'' + data.assessmentTitle + '\',\'' + data.position + '\',\'' + data.departmentName + '\',\'' + data.assessmentState + '\',\'' + data.assistantLock + '\',\'' + data.managerId + '\',\'' + data.assistantLeaderApprovalId + '\',\'' + data.haveChangerLeader + '\',\'' + data.leaderOption + '\',\'' + data.assessmentType + '\',\'' + 'LeadTheBacklogDKH' + '\')">项目分管领导评分</a>';
                    }else if(data.assessmentState === '7'){//项目主管领导评分
                        return '<a style="color:blue;" onclick="myOpenEKH(\'项目主管领导评分\',\'' + data.createUserName + '\',\'' + data.executiveId + '\',\'' + data.assessmentTitle + '\',\'' + data.position + '\',\'' + data.departmentName + '\',\'' + data.assessmentState + '\',\'' + data.assistantLock + '\',\'' + data.managerId + '\',\'' + data.assistantLeaderApprovalId + '\',\'' + data.haveChangerLeader + '\',\'' + data.leaderOption + '\',\'' + data.assessmentType + '\',\'' + 'LeadTheBacklogDKH' + '\')">项目主管领导评分</a>';
                    }
                }else if(data.assessmentType === '3'){
                    if(data.assessmentState === '1' || data.assessmentState === '4'){//项目负责人审批
                        return '<a style="color:blue;" onclick="myOpenEJH(\'部门负责人审核\',\'' + data.createUserName + '\',\'' + data.executiveId + '\',\'' + data.assessmentTitle + '\',\'' + data.position + '\',\'' + data.departmentName + '\',\'' + data.assessmentState + '\',\'' + data.chargeLeaderOption + '\',\'' + data.executiveLeaderOption + '\',\'' + data.assistantLock + '\',\'' + data.managerId + '\',\'' + data.assistantLeaderApprovalId + '\',\'' + data.haveChangerLeader + '\',\'' + data.leaderOption + '\',\'' + data.assessmentType + '\',\'' + 'LeadTheBacklogSJH' + '\')">部门负责人审批</a>';
                    }else if(data.assessmentState === '3'){//项目分管领导审批
                        if(data.haveChangerLeader === "1"){
                            return '<a style="color:blue;" onclick="myOpenEJH(\'项目主管领导审核\',\'' + data.createUserName + '\',\'' + data.executiveId + '\',\'' + data.assessmentTitle + '\',\'' + data.position + '\',\'' + data.departmentName + '\',\'' + data.assessmentState + '\',\'' + data.chargeLeaderOption + '\',\'' + data.executiveLeaderOption + '\',\'' + data.assistantLock + '\',\'' + data.managerId + '\',\'' + data.assistantLeaderApprovalId + '\',\'' + data.haveChangerLeader + '\',\'' + data.leaderOption + '\',\'' + data.assessmentType + '\',\'' + 'LeadTheBacklogSJH' + '\')">项目主管领导审批</a>';
                        }else{
                            return '<a style="color:blue;" onclick="myOpenEJH(\'项目分管领导审核\',\'' + data.createUserName + '\',\'' + data.executiveId + '\',\'' + data.assessmentTitle + '\',\'' + data.position + '\',\'' + data.departmentName + '\',\'' + data.assessmentState + '\',\'' + data.chargeLeaderOption + '\',\'' + data.executiveLeaderOption + '\',\'' + data.assistantLock + '\',\'' + data.managerId + '\',\'' + data.assistantLeaderApprovalId + '\',\'' + data.haveChangerLeader + '\',\'' + data.leaderOption + '\',\'' + data.assessmentType + '\',\'' + 'LeadTheBacklogSJH' + '\')">项目分管领导审批</a>';
                        }
                    }else if(data.assessmentState === '6'){//项目负责人评分
                        return '<a style="color:blue;" onclick="myOpenEKH(\'部门负责人评分\',\'' + data.createUserName + '\',\'' + data.executiveId + '\',\'' + data.assessmentTitle + '\',\'' + data.position + '\',\'' + data.departmentName + '\',\'' + data.assessmentState + '\',\'' + data.assistantLock + '\',\'' + data.managerId + '\',\'' + data.assistantLeaderApprovalId + '\',\'' + data.haveChangerLeader + '\',\'' + data.leaderOption + '\',\'' + data.assessmentType + '\',\'' + 'LeadTheBacklogSKH' + '\')">部门负责人评分</a>';
                    }else if(data.assessmentState === '7'){//项目分管领导评分
                        if(data.haveChangerLeader === "1"){
                            return '<a style="color:blue;" onclick="myOpenEKH(\'项目主管领导评分\',\'' + data.createUserName + '\',\'' + data.executiveId + '\',\'' + data.assessmentTitle + '\',\'' + data.position + '\',\'' + data.departmentName + '\',\'' + data.assessmentState + '\',\'' + data.assistantLock + '\',\'' + data.managerId + '\',\'' + data.assistantLeaderApprovalId + '\',\'' + data.haveChangerLeader + '\',\'' + data.leaderOption + '\',\'' + data.assessmentType + '\',\'' + 'LeadTheBacklogSKH' + '\')">项目主管领导评分</a>';
                        }else{
                            return '<a style="color:blue;" onclick="myOpenEKH(\'项目分管领导评分\',\'' + data.createUserName + '\',\'' + data.executiveId + '\',\'' + data.assessmentTitle + '\',\'' + data.position + '\',\'' + data.departmentName + '\',\'' + data.assessmentState + '\',\'' + data.assistantLock + '\',\'' + data.managerId + '\',\'' + data.assistantLeaderApprovalId + '\',\'' + data.haveChangerLeader + '\',\'' + data.leaderOption + '\',\'' + data.assessmentType + '\',\'' + 'LeadTheBacklogSKH' + '\')">项目分管领导评分</a>';
                        }
                    }
                }
            }
        }
    ]
});
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjXmCqjxProjectAssistantTodoList"),
        params: {
            // sarsType:"1"
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
        }
    }
});
var myOpenLayerEJH;
function myOpenEJH(title,createUserName, id, biaoti, gangwei, bumen, state, chargeLeaderOption, executiveLeaderOption, assistantLock, managerId, assistantLeaderApprovalId, haveChangerLeader,leaderOption,assessmentType,url) {
    var chargeLeaderOption = chargeLeaderOption.replace(/<br>/g,"\r\n");
    var executiveLeaderOption = executiveLeaderOption.replace(/<br>/g,"\r\n");
    chargeLeaderOption = encodeURI(chargeLeaderOption);
    executiveLeaderOption = encodeURI(executiveLeaderOption);
    l.ajax("checkZjXmCqjxProjectExecutiveAssistantLaunch",{executiveId:id,assessmentState:state,assistantLock:assistantLock,managerId:managerId},function (_d, _m, _s) {
        if(_s){
            var options = {
                type: 2,
                title: title,
                area: ['100%', '100%'],
                content: url + ".html?code=" + code + '&id=' + id + '&biaoti=' + biaoti + '&gangwei=' + gangwei + '&bumen=' + bumen + '&state=' + state + '&chargeLeaderOption=' + chargeLeaderOption + '&executiveLeaderOption=' + executiveLeaderOption + '&assistantLeaderApprovalId=' + assistantLeaderApprovalId+ '&haveChangerLeader=' + haveChangerLeader+ '&createUserName=' + createUserName+ '&leaderOption=' + leaderOption+ '&assessmentType=' + assessmentType
            };
            myOpenLayerEJH = layer.open(options);
        }
    })    
}
var myOpenLayerEKH;
function myOpenEKH(title,createUserName, id, biaoti, gangwei, bumen, state, assistantLock,managerId, assistantLeaderApprovalId, haveChangerLeader,leaderOption, assessmentType,url) {
    l.ajax("checkZjXmCqjxProjectExecutiveAssistantLaunch",{executiveId:id,assessmentState:state,assistantLock:assistantLock,managerId:managerId},function (_d, _m, _s) {
        if(_s){
            var options = {
                type: 2,
                title: title,
                area: ['100%', '100%'],
                content: url + ".html?code=" + code + '&id=' + id + '&biaoti=' + biaoti + '&gangwei=' + gangwei + '&bumen=' + bumen + '&state=' + state + '&assistantLeaderApprovalId=' + assistantLeaderApprovalId+ '&haveChangerLeader=' + haveChangerLeader+ '&createUserName=' + createUserName+ '&leaderOption=' + leaderOption
            };
            myOpenLayerEKH = layer.open(options);
        }
    })
}


