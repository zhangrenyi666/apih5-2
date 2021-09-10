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
            "title": '名次',
            "width": 25,
            "render": function (data) {
                return data + 1
            }
        },
        {
            "targets": [1],
            // "data": "createUserName",
            "title": "姓名",
            "data": function (row) {
                return row;
            },
            "defaultContent": "-",
            "render": function (data) {
                if (data.rankNum === "3") {
                    return '<span style="color:red;">' + data.createUserName + '</span>';
                } else {
                    return data.createUserName;
                }
            }
        },
        {
            "targets": [2],
            // "data": "assessmentYears",
            "title": "考核年度",
            "data": function (row) {
                return row;
            },
            "defaultContent": "-",
            "render": function (data) {
                if (data.rankNum === "3") {
                    return '<span style="color:red;">' + l.dateFormat(new Date(data.assessmentYears), "yyyy-MM") + '</span>';
                } else {
                    return l.dateFormat(new Date(data.assessmentYears), "yyyy-MM");
                }
            }           
        },        
        {
            "targets": [3],//第几列
            // "width": 100,
            // "data": "assessmentType",//接口对应字段
            "title": "考核类别",//表头名
            "data": function (row) {
                return row;
            },
            "defaultContent": "-",//默认显示,
            "render": function (data) {
                if (data.rankNum === "3") {
                if (data.assessmentType === "1") {
                    return '<span style="color:red;">领导班子副职</span>';
                } else if (data.assessmentType === "2") {
                    return '<span style="color:red;">项目中层干部</span>';
                } else if (data.assessmentType === "3") {
                    return '<span style="color:red;">员工</span>';
                } else {
                    return '--'
                }
            }else{
                if (data.assessmentType === "1") {
                    return '领导班子副职'
                } else if (data.assessmentType === "2") {
                    return '项目中层干部'
                } else if (data.assessmentType === "3") {
                    return '员工'
                } else {
                    return '--'
                }
            }
            }  
        },
        {
            "targets": [4],
            // "data": "quarterScore",
            "data": function (row) {
                return row;
            },
            "title": "月度得分",
            "defaultContent": "-",
            "render": function (data) {
                if (data.rankNum === "3") {
                    return '<span style="color:red;">' + data.quarterScore + '</span>';
                } else {
                    return data.quarterScore;
                }
            }
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [
        {
            type: "date",
            name: "years",
            label: "考核年月",
            defaultValue: new Date().getFullYear() + '-' + (new Date().getMonth()+1),
            dateFmt: "yyyy-MM",
            id: 'years',
            placeholder: "请选择",
        },
        {
            type: "select",
            name: "assessmentType",
            label: "考核类别",
            placeholder: "请选择",
            must: true,
            selectList: [
                {
                    value: "1",
                    text: "领导班子副职"
                },
                {
                    value: "2",
                    text: "项目中层干部"
                },
                {
                    value: "3",
                    text: "员工"
                }
            ],
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
        url: l.getApiUrl("getZjXmCqjxProjectComprehensiveRanking"),
        params: {
            assessmentType: "1",
            years : new Date().getFullYear() + '-' + (new Date().getMonth()+1)
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
                                myOpenJH('计划流程发起', checkedData[0].executiveId, checkedData[0].assessmentTitle, checkedData[0].position, checkedData[0].departmentName, checkedData[0].assessmentType, 'add', 'ExecutiveAssistantPersonJH');
                            }
                        })
                    } else if(checkedData[0].assessmentState === '4'){
                        if(checkedData[0].assessmentState === '4' && checkedData[0].haveChangerLeader === '1'){
                            l.ajax("checkZjXmCqjxProjectExecutiveAssistantLaunch",checkedData[0],function (_d, _m, _s) {
                                if(_s){
                                    myOpenJH('计划流程发起', checkedData[0].executiveId, checkedData[0].assessmentTitle, checkedData[0].position, checkedData[0].departmentName, checkedData[0].assessmentType, 'add', 'ExecutiveAssistantPersonJH');
                                }
                            })
                        }else{
                            layer.alert("请勿重复发起！", { icon: 0, }, function (index) {
                                layer.close(index);
                            });                            
                        }
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
                    layer.alert("审核数据不能发起", { icon: 0, }, function (index) {
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
function myOpenJH(title, id, biaoti, gangwei, bumen, assessmentType,submitType, url) {
    var options = {
        type: 2,
        title: title,
        area: ['100%', '100%'],
        content: url + ".html?code=" + code + '&id=' + id + '&biaoti=' + biaoti + '&gangwei=' + gangwei + '&bumen=' + bumen + '&submitType=' + submitType+ '&assessmentType=' + assessmentType
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

