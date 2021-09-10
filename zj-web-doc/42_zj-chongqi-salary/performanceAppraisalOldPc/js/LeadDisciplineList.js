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
            "title": "提交人",
            "defaultContent": "-"
        },
        {
            "targets": [2],
            "data": "disciplineYears",
            "title": "考核年度",
            "defaultContent": "-",
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy");
            }
        },
        {
            "targets": [3],
            "data": "disciplineQuarter",
            "title": "考核季度",
            "defaultContent": "-",
            "render": function (data) {
                if (data === '0') {
                    return '第一季度';
                } else if (data === '1') {
                    return '第二季度';
                } else if (data === '2') {
                    return '第三季度';
                } else if (data === '3') {
                    return '第四季度';
                } else if (data === '4') {
                    return '上半年度';
                } else if (data === '5') {
                    return '下半年度';
                } else {
                    return '--';
                }
            }
        },
        {
            "targets": [4],
            "data": "assessmentType",
            "title": "考核类别",
            "defaultContent": "-",
            "render": function (data) {
                if (data === '0') {
                    return '全部';
                } else if (data === '1') {
                    return '副总师、总助';
                } else if (data === '2') {
                    return '部门正、副职';
                } else if (data === '3') {
                    return '员工';
                } else {
                    return '--';
                }
            }
        },
        // {
        //     "targets": [5],
        //     "data": "firstDutyClosingEndDate",
        //     "title": "审核截止日期",
        //     "defaultContent": "-",
        //     "render": function (data) {
        //         return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
        //     }
        // },
        // {
        //     "targets": [6],
        //     "data": "firstScoreClosingEndDate",
        //     "title": "评分截止日期",
        //     "defaultContent": "-",
        //     "render": function (data) {
        //         if(data){
        //             return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
        //         }else{
        //             return "";
        //         }
        //     }
        // },            
        {
            "targets": [5],
            "data": "modifyTime",
            "title": "发起时间",
            "defaultContent": "-",
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [6],//第几列
            "width": 100,
            "data": function (row) {
                return row
            },
            "title": "操作", 
            "render": function (data) {
                if(data.disciplineState === '3'){
                    return '<a style="color:blue;" onclick="myOpenSH(\'部门负责人审核\',\'' + data.disciplineId + '\',\'' + data.disciplineState + '\',\'' + data.deptHeadOption + '\',\'' + data.executiveLeaderOption + '\',\'' + data.assistantLeaderApprovalId + '\',\'' + data.leaderOption + '\',\'' + 'LeadDisciplineSH' + '\')">部门负责人审批</a>';
                }else if(data.disciplineState === '5'){
                    return '<a style="color:blue;" onclick="myOpenSH(\'主管领导审核\',\'' + data.disciplineId + '\',\'' + data.disciplineState + '\',\'' + data.deptHeadOption + '\',\'' + data.executiveLeaderOption + '\',\'' + data.assistantLeaderApprovalId + '\',\'' + data.leaderOption + '\',\'' + 'LeadDisciplineSH' + '\')">主管领导审批</a>';
                }else{
                    return '--';
                }
            }
        }
    ]
});
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("zjXmCqjxDisciplineAssessmentToDoList"),
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
var myOpenLayerSH;
function myOpenSH(title, disciplineId, state, chargeLeaderOption, executiveLeaderOption, assistantLeaderApprovalId, leaderOption,url) {
    if(chargeLeaderOption === 'undefined'){
        chargeLeaderOption = "";
    }
    if(executiveLeaderOption === 'undefined'){
        executiveLeaderOption = "";
    }
    var chargeLeaderOption = chargeLeaderOption.replace(/<br>/g,"\r\n");
    var executiveLeaderOption = executiveLeaderOption.replace(/<br>/g,"\r\n");
    chargeLeaderOption = encodeURI(chargeLeaderOption);
    executiveLeaderOption = encodeURI(executiveLeaderOption);
    var options = {
        type: 2,
        title: title,
        area: ['100%', '100%'],
        // content: url + ".html?code=" + code + '&disciplineId=' + disciplineId
        content: url + ".html?code=" + code + '&disciplineId=' + disciplineId + '&state=' + state + '&chargeLeaderOption=' + chargeLeaderOption + '&executiveLeaderOption=' + executiveLeaderOption + '&assistantLeaderApprovalId=' + assistantLeaderApprovalId + '&leaderOption=' + leaderOption
    };
    myOpenLayerSH = layer.open(options);
    // l.ajax("checkZjXmCqjxExecutiveAssistantLaunch",{executiveId:id,assessmentState:state,assistantLock:assistantLock,managerId:managerId},function (_d, _m, _s) {
    //     if(_s){
    //         var options = {
    //             type: 2,
    //             title: title,
    //             area: ['100%', '100%'],
    //             content: url + ".html?code=" + code + '&disciplineId=' + disciplineId + '&state=' + state + '&chargeLeaderOption=' + chargeLeaderOption + '&executiveLeaderOption=' + executiveLeaderOption
    //         };
    //         myOpenLayerSH = layer.open(options);
    //     }
    // })    
}

