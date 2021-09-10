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
            "targets": [2],
            "data": "createUserName",
            "title": "姓名",
            "defaultContent": "-",
            "render": function (data, index, row) {
                return '<a style="color:blue;" onclick="myOpenAdd(\'工作计划详情\',\'' + row.executiveId + '\',\'' + row.assessmentTitle + '\',\'' + row.position + '\',\'' + row.departmentName + '\',\'' + row.chargeLeaderOption + '\',\'' + row.executiveLeaderOption + '\',\'' + 'detail' + '\',\'' + 'ExecutiveAssistantPerson' + '\',\'' + row.managerId + '\',\'' + row.createUser + '\')">' + data + '</a>'
            }
        },
        {
            "targets": [3],
            "data": "assessmentTitle",
            "title": "考核标题",
            "defaultContent": "-",
        },
        {
            "targets": [4],
            "data": "assessmentYears",
            "title": "考核月度",
            "defaultContent": "-",
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM");
            }
        },
        // {
        //     "targets": [5],
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
            "targets": [5],
            "data": "assessmentState",
            "title": "审核状态",
            "defaultContent": "-",
            "render": function (data) {
                if (data === '0') {
                    return '未发起审核';
                } else if (data === '1') {
                    return '项目主管领导审核';
                } else if (data === '2') {
                    return '项目主管领导退回';
                } else if (data === '3') {
                    return '公司分管领导审核';
                } else if (data === '4') {
                    return '公司分管领导退回';
                } else if (data === '5') {
                    return '未发起评分';
                } else if (data === '6') {
                    return '项目主管领导评分';
                } else if (data === '7') {
                    return '公司分管领导评分';
                } else if (data === '8') {
                    return '责任指标评分完成';
                } else {
                    return '--';
                }
            }
        },
        {
            "targets": [6],
            "data": "dutyClosingEndDate",
            "title": "计划填报截止日期",
            "defaultContent": "-",
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [7],
            "data": "modifyTime",
            "title": "更新时间",
            "defaultContent": "-",
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [
        {
            type: "date",
            name: "years",
            label: "填报月度",
            dateFmt: "yyyy-MM",
            id: 'years',
            placeholder: "请选择"
        },
        // {
        //     type: "select",
        //     name: "assessmentQuarter",
        //     label: "填报月度",
        //     placeholder: "请选择",
        //     selectList: [
        //         {
        //             value: "0",
        //             text: "第一月度"
        //         },
        //         {
        //             value: "1",
        //             text: "第二月度"
        //         },
        //         {
        //             value: "2",
        //             text: "第三月度"
        //         },
        //         {
        //             value: "3",
        //             text: "第四月度"
        //         },
        //         {
        //             value: "4",
        //             text: "第五月度"
        //         },
        //         {
        //             value: "5",
        //             text: "第六月度"
        //         },
        //         {
        //             value: "6",
        //             text: "第七月度"
        //         },
        //         {
        //             value: "7",
        //             text: "第八月度"
        //         },
        //         {
        //             value: "8",
        //             text: "第九月度"
        //         },
        //         {
        //             value: "9",
        //             text: "第十月度"
        //         },
        //         {
        //             value: "10",
        //             text: "第十一月度"
        //         },
        //         {
        //             value: "11",
        //             text: "第十二月度"
        //         }
        //     ]
        // },
        {
            type: "select",
            name: "assessmentState",
            label: "审核状态",
            placeholder: "请选择",
            selectList: [
                {
                    value: "",
                    text: "全部"
                },
                {
                    value: "0",
                    text: "未发起审核"
                },
                {
                    value: "1",
                    text: "项目主管领导审核"
                },
                {
                    value: "2",
                    text: "项目主管领导退回"
                },
                {
                    value: "3",
                    text: "公司分管领导审核"
                },
                {
                    value: "4",
                    text: "公司分管领导退回"
                },
                {
                    value: "5",
                    text: "未发起评分"
                },
                {
                    value: "6",
                    text: "项目主管领导评分"
                },
                {
                    value: "7",
                    text: "公司分管领导评分"
                },
                {
                    value: "8",
                    text: "责任指标评分完成"
                }
            ]
        }
    ],
    onSearch: function (arr) {
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
        pager.page('remote', _params)
    },
    onReset: function (arr) {
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
        pager.page('remote', _params)
    }
})
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjXmCqjxProjectExecutiveAssistantList"),
        params: {
            assessmentType: "1"
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

$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            if (checkedData.length == 1) {
                if (checkedData[0].assessmentState === '0' || checkedData[0].assessmentState === '2') {
                    myOpenAdd('工作计划填报', checkedData[0].executiveId, checkedData[0].assessmentTitle, checkedData[0].position, checkedData[0].departmentName, checkedData[0].chargeLeaderOption, checkedData[0].executiveLeaderOption, 'add', 'ExecutiveAssistantPerson');
                } else {
                    layer.alert("审核数据不能填报", { icon: 0, }, function (index) {
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
        case "edit":
            if (checkedData.length == 1) {
                if (checkedData[0].assessmentState === '0' || checkedData[0].assessmentState === '2') {
                    myOpenAdd('工作计划修改', checkedData[0].executiveId, checkedData[0].assessmentTitle, checkedData[0].position, checkedData[0].departmentName, checkedData[0].chargeLeaderOption, checkedData[0].executiveLeaderOption, 'edit', 'ExecutiveAssistantPerson');
                } else {
                    layer.alert("审核数据不能编辑", { icon: 0, }, function (index) {
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
        case "jhfaqi":
            if (checkedData.length == 1) {
                if (checkedData[0].position) {
                    if (checkedData[0].assessmentState === '0' || checkedData[0].assessmentState === '2') {
                        l.ajax("checkZjXmCqjxProjectExecutiveAssistantLaunch",checkedData[0],function (_d, _m, _s) {
                            if(_s){
                                myOpenJH('计划流程发起', checkedData[0].executiveId, checkedData[0].assessmentTitle, checkedData[0].position, checkedData[0].departmentName, checkedData[0].assessmentType,checkedData[0].assessmentState, 'add', 'ExecutiveAssistantPersonJH');
                            }
                        })
                    }else{
                        layer.alert("请勿重复发起！", { icon: 0, }, function (index) {
                            layer.close(index);
                        });
                    }
                }else{
                    layer.alert("工作计划未填", { icon: 0, }, function (index) {
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
        case "khfaqi":
            if (checkedData.length == 1) {
                if (checkedData[0].assessmentState === '5') {
                    l.ajax("checkZjXmCqjxProjectExecutiveAssistantLaunch",checkedData[0],function (_d, _m, _s) {
                        if(_s){
                            myOpenKH('考核流程发起', checkedData[0].executiveId, checkedData[0].assessmentTitle, checkedData[0].position, checkedData[0].departmentName, 'add', 'ExecutiveAssistantPersonKH');
                        }
                    })                    
                } else {
                    layer.alert("计划流程审批完成后才能发起！", { icon: 0, }, function (index) {
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
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
                for (var i = 0; i < checkedData.length; i++) {
                    if (checkedData[i].apih5FlowStatus == 1 && checkedData[i].apih5FlowStatus == 2) {
                        layer.alert("流程已经发起不能删除!", { icon: 0, }, function (index) {
                            layer.close(index);
                        });
                        break;
                    } else if (i === checkedData.length - 1) {
                        layer.confirm("确定删除？", { icon: 0, }, function (index) {
                            l.ajax("batchDeleteUpdateZjFlowMinisterApprove", checkedData, function () {
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
var myOpenLayer;
function myOpenAdd(title, id, biaoti, gangwei, bumen, chargeLeaderOption, executiveLeaderOption, submitType, url,managerId,createUser) {
    var chargeLeaderOption = chargeLeaderOption.replace(/<br>/g,"\r\n");
    var executiveLeaderOption = executiveLeaderOption.replace(/<br>/g,"\r\n");
    chargeLeaderOption = encodeURI(chargeLeaderOption);
    executiveLeaderOption = encodeURI(executiveLeaderOption);
    var options = {
        type: 2,
        title: title,
        closeBtn: false,
        area: ['100%', '100%'],
        content: url + ".html?code=" + code + '&id=' + id + '&biaoti=' + biaoti + '&gangwei=' + gangwei + '&bumen=' + bumen + '&chargeLeaderOption=' + chargeLeaderOption + '&executiveLeaderOption=' + executiveLeaderOption + '&submitType=' + submitType + '&managerId=' + managerId + '&createUser=' + createUser
    };
    myOpenLayer = layer.open(options);
}
var myOpenLayerJH;
function myOpenJH(title, id, biaoti, gangwei, bumen, assessmentType,assessmentState,submitType, url) {
    var options = {
        type: 2,
        title: title,
        area: ['100%', '100%'],
        content: url + ".html?code=" + code + '&id=' + id + '&biaoti=' + biaoti + '&gangwei=' + gangwei + '&bumen=' + bumen + '&submitType=' + submitType+ '&assessmentType=' + assessmentType+ '&assessmentState=' + assessmentState
    };
    myOpenLayerJH = layer.open(options);
}
var myOpenLayerKH;
function myOpenKH(title, id, biaoti, gangwei, bumen, submitType, url) {
    var options = {
        type: 2,
        title: title,
        area: ['100%', '100%'],
        content: url + ".html?code=" + code + '&id=' + id + '&biaoti=' + biaoti + '&gangwei=' + gangwei + '&bumen=' + bumen + '&submitType=' + submitType
    };
    myOpenLayerKH = layer.open(options);
}

//阻止删除键后退方法
function forbidBackSpace(e) {
    var ev = e || window.event; //获取event对象 
    var obj = ev.target || ev.srcElement; //获取事件源 
    var t = obj.type || obj.getAttribute('type'); //获取事件源类型 
    //获取作为判断条件的事件类型 
    var vReadOnly = obj.readOnly;
    var vDisabled = obj.disabled;
    //处理undefined值情况 
    vReadOnly = (vReadOnly == undefined) ? false : vReadOnly;
    vDisabled = (vDisabled == undefined) ? true : vDisabled;
    //当敲Backspace键时，事件源类型为密码或单行、多行文本的， 
    //并且readOnly属性为true或disabled属性为true的，则退格键失效 
    // var flag1 = ev.keyCode == 8 && (t == "text" || t == "textarea") && (vReadOnly == true || vDisabled == true);
    //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效 
    var flag2 = ev.keyCode == 8 && !(t == "textarea");
    //判断 
    if (flag2) return false;
}
//禁止后退键 作用于Firefox、Opera
document.onkeypress = forbidBackSpace;
//禁止后退键  作用于IE、Chrome
document.onkeydown = forbidBackSpace;