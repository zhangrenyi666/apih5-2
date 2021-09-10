var code = Apih5.getUrlParam('code')
var id = Apih5.getUrlParam('id');
var biaoti = Apih5.getUrlParam('biaoti');
var gangwei = Apih5.getUrlParam('gangwei');
var bumen = Apih5.getUrlParam('bumen');
var state = Apih5.getUrlParam('state');
var leaderOption = Apih5.getUrlParam('leaderOption');
var createUserName = Apih5.getUrlParam('createUserName');
if (state === "3") {
    var chargeLeaderOption = Apih5.getUrlParam('chargeLeaderOption');
} else {
    var chargeLeaderOption = "";
}
if (state === "4") {
    var executiveLeaderOption = Apih5.getUrlParam('executiveLeaderOption');
} else {
    var executiveLeaderOption = "";
}
var haveChangerLeader = Apih5.getUrlParam('haveChangerLeader');
// var chargeLeaderOption = Apih5.getUrlParam('chargeLeaderOption');
// var executiveLeaderOption = Apih5.getUrlParam('executiveLeaderOption');
var assistantLeaderApprovalId = Apih5.getUrlParam('assistantLeaderApprovalId');
Apih5.setCookie('code', code, 30);
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
            "defaultContent": "-",
            "render": function (data) {
                return data + 1
            }
        },
        {
            "targets": [1],
            "data": "workPlan",
            "title": "工作计划",
            "defaultContent": "-",
            "width": 500,
            "render": function (data, _, rowData) {
                return "<textarea style='resize: none;width:100%;height:50px;padding:5px;box-sizing: border-box;' onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'workPlan' + "\",$(this))'>" + data + "</textarea>";
            }
        },
        {
            "targets": [2],
            "data": "workTarget",
            "title": "目标",
            "defaultContent": "-",
            "width": 500,
            "render": function (data, _, rowData) {
                return "<textarea style='resize: none;width:100%;height:50px;padding:5px;box-sizing: border-box;' onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'workTarget' + "\",$(this))'>" + data + "</textarea>";
            }
        },
        {
            "targets": [3],
            "data": "assessmentScore",
            "title": "考核分",
            "defaultContent": "-",
            "width": 200,
            "render": function (data, _, rowData) {
                return "<input style='width:100%;height:50px;line-height: 40px;font-size:18px;padding:5px;box-sizing: border-box;' type='number' min=0 value=" + data + " onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'assessmentScore' + "\",$(this))' />";
            }
        }
    ]
});
var pager = $("#pager").page({
    remote: {// ajax请求配置
        url: l.getApiUrl("getZjXmCqjxDeptLeaderAssistantDetailedList"),
        params: {
            executiveId: id,
            workType: '0'
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
var tables = $('#tables').DataTable({
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
            "defaultContent": "-",
            "render": function (data) {
                return data + 1
            }
        },
        {
            "targets": [1],
            "data": "workPlan",
            "title": "工作计划",
            "defaultContent": "-",
            "width": 500,
            "render": function (data, _, rowData) {
                return "<textarea style='resize: none;width:100%;height:50px;padding:5px;box-sizing: border-box;' onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'workPlan' + "\",$(this))'>" + data + "</textarea>";
            }
        },
        {
            "targets": [2],
            "data": "workTarget",
            "title": "目标",
            "defaultContent": "-",
            "width": 500,
            "render": function (data, _, rowData) {
                return "<textarea style='resize: none;width:100%;height:50px;padding:5px;box-sizing: border-box;' onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'workTarget' + "\",$(this))'>" + data + "</textarea>";
            }
        },
        {
            "targets": [3],
            "data": "assessmentScore",
            "title": "考核分",
            "defaultContent": "-",
            "width": 200,
            "render": function (data, _, rowData) {
                return "<input style='width:100%;height:50px;line-height: 40px;font-size:18px;padding:5px;box-sizing: border-box;' type='number' min=0 value=" + data + " onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'assessmentScore' + "\",$(this))' />";
            }
        }
    ]
});
var pagers = $("#pagers").page({
    remote: {// ajax请求配置
        url: l.getApiUrl("getZjXmCqjxDeptLeaderAssistantDetailedList"),
        params: {
            executiveId: id,
            workType: '1'
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                $.each(data, function (index, item) {
                    item.rowIndex = index
                })
                tables.clear().rows.add(data).draw();
            } else {
                layer.alert(result.message, { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
        }
    }
});
var detailLayer = $('#detailLayer').detailLayer({
    layerArea: ["50%", "50%"],
    conditions: [
        {
            type: "hidden",//
            name: "executiveId",//
            defaultValue: id
        },
        {
            type: "hidden",//
            name: "assistantLeaderApprovalId",//
            defaultValue: assistantLeaderApprovalId
        },
        {
            type: "hidden",//
            name: "departmentName",//
            defaultValue: bumen
        },
        {
            type: "hidden",//
            name: "position",//
            defaultValue: gangwei
        },
        {
            type: "pickTree",
            name: "leaderList",
            label: "主管领导",
            must: true,
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            }
        }
    ],
    onAdd: function (obj, _params) {
        var rowData = detailLayerOut.getDatas().data;
        _params.approvalFlag = '0';
        if (rowData.chargeLeaderOption) {
            _params.chargeLeaderOption = rowData.chargeLeaderOption
        } else {
            _params.chargeLeaderOption = '同意';
        }
        l.ajax("zjXmCqjxAssistantChargeLeaderApproval", _params, function (_d, _m, _s) {
            if (_s) {
                layer.alert(_m, { icon: 1, }, function (index) {
                    parent.pager.page('remote');
                    parent.layer.close(parent.myOpenLayerEJH);
                });
            }
        })
    },
    onSave: function (obj, _params) {
        var rowData = detailLayerOut.getDatas().data;
        _params.approvalFlag = '0';
        if (rowData.chargeLeaderOption) {
            _params.chargeLeaderOption = rowData.chargeLeaderOption
        } else {
            _params.chargeLeaderOption = '同意';
        }
        l.ajax("zjXmCqjxAssistantChargeLeaderApproval", _params, function (_d, _m, _s) {
            if (_s) {
                layer.alert(_m, { icon: 1, }, function (index) {
                    parent.pager.page('remote');
                    parent.layer.close(parent.myOpenLayerEJH);
                });
            }
        })
    }
})
//外层新增表单
var detailLayerOut = $('#detailLayerOut').detailLayer({
    layerArea: ["100%", "100%"],
    customBtnGroup: {
        btns: []
    },
    conditions: [
        {
            type: "hidden",
            name: "executiveId",
            defaultValue: id,
            lineNum: 0
        },
        {
            type: "hidden",
            name: "assistantLeaderApprovalId",
            defaultValue: assistantLeaderApprovalId,
            lineNum: 0
        },
        {
            type: "text",
            name: "assessmentTitle",
            label: "填报标题",
            placeholder: "请输入",
            defaultValue: biaoti,
            lineNum: 1
        },
        {
            type: "text",
            name: "departmentName",
            label: "填报部门",
            placeholder: "请输入",
            defaultValue: bumen,
            lineNum: 1
        },
        {
            type: "text",
            name: "createUserName",
            label: "姓名",
            placeholder: "请输入",
            defaultValue: createUserName,
            lineNum: 2
        },
        {
            type: "text",
            name: "position",
            label: "岗位",
            placeholder: "请输入",
            defaultValue: gangwei,
            lineNum: 2
        },
        {
            type: "textarea",
            name: "chargeLeaderOption",
            label: "分管领导意见",
            defaultValue: chargeLeaderOption,
            // defaultValue: chargeLeaderOption ? chargeLeaderOption : '同意',
            placeholder: "请输入"
        },
        {
            type: "textarea",
            name: "executiveLeaderOption",
            label: "主管领导意见",
            // defaultValue: state ==='3' && !executiveLeaderOption ? '同意' : executiveLeaderOption,
            defaultValue: executiveLeaderOption,
            placeholder: "请输入"
        }
    ]
})

$('input[name="assessmentTitle"]').attr('disabled', true);
$('input[name="departmentName"]').attr('disabled', true);
$('input[name="position"]').attr('disabled', true);
$('input[name="createUserName"]').attr('disabled', true);
if (state === '1' || state === '4') {
    $('textarea[name="executiveLeaderOption"]').attr('disabled', true);
    if (leaderOption) {
        $('textarea[name="chargeLeaderOption"]').text(leaderOption);
    } else {
        $('textarea[name="chargeLeaderOption"]').text("同意");
    }
} else if (state === '3') {
    $('textarea[name="chargeLeaderOption"]').attr('disabled', true);
    if (leaderOption) {
        $('textarea[name="executiveLeaderOption"]').text(leaderOption);
    } else {
        $('textarea[name="executiveLeaderOption"]').text("同意");
    }
}
$("body").on("click", "button", function () {
    var name = $(this).attr("data-name");
    switch (name) {
        case "submit":
            l.ajax("checkZjXmCqjxAssistantScoreQualified", { executiveId: id }, function (data, message, success) {
                if (success) {
                    if (state === '3') {
                        var rowData = detailLayerOut.getDatas().data;
                        rowData.approvalFlag = '0';
                        if (rowData.executiveLeaderOption) {
                            rowData.executiveLeaderOption = rowData.executiveLeaderOption
                        } else {
                            rowData.executiveLeaderOption = '同意';
                        }
                        l.ajax("zjXmCqjxAssistantExecutiveLeaderApproval", rowData, function (_d, _m, _s) {
                            if (_s) {
                                layer.alert(_m, { icon: 1, }, function (index) {
                                    parent.pager.page('remote');
                                    parent.layer.close(parent.myOpenLayerEJH);
                                });
                            }
                        })
                    } else {
                        var rowData = detailLayerOut.getDatas().data;
                        rowData.approvalFlag = '0';
                        if (rowData.chargeLeaderOption) {
                            rowData.chargeLeaderOption = rowData.chargeLeaderOption
                        } else {
                            rowData.chargeLeaderOption = '同意';
                        }
                        l.ajax("zjXmCqjxAssistantChargeLeaderApproval", rowData, function (_d, _m, _s) {
                            if (_s) {
                                layer.alert(_m, { icon: 1, }, function (index) {
                                    parent.pager.page('remote');
                                    parent.layer.close(parent.myOpenLayerEJH);
                                });
                            }
                        })
                    }
                }
            })
            break;
        case "sendBack":
            if (state === '1' || state === '4') {
                var rowData = detailLayerOut.getDatas().data;
                rowData.approvalFlag = '1';
                if (rowData.chargeLeaderOption && rowData.chargeLeaderOption !== '同意') {
                    rowData.chargeLeaderOption = rowData.chargeLeaderOption
                } else {
                    rowData.chargeLeaderOption = '不同意';
                }
                l.ajax("zjXmCqjxAssistantChargeLeaderApproval", rowData, function (data, message, success) {
                    if (success) {
                        parent.pager.page('remote');
                        layer.alert(message, { icon: 1 }, function (index) {
                            parent.layer.close(parent.myOpenLayerEJH);
                        });
                    } else {
                        layer.alert(message, { icon: 0 });
                    }
                })
            } else {
                var rowData = detailLayerOut.getDatas().data;
                rowData.approvalFlag = '1';
                if (rowData.executiveLeaderOption && rowData.executiveLeaderOption !== '同意') {
                    rowData.executiveLeaderOption = rowData.executiveLeaderOption
                } else {
                    rowData.executiveLeaderOption = '不同意';
                }
                l.ajax("zjXmCqjxAssistantExecutiveLeaderApproval", rowData, function (data, message, success) {
                    if (success) {
                        parent.pager.page('remote');
                        layer.alert(message, { icon: 1 }, function (index) {
                            parent.layer.close(parent.myOpenLayerEJH);
                        });
                    } else {
                        layer.alert(message, { icon: 0 });
                    }
                })
            }
            break;
        case "cancel":
            parent.layer.close(parent.myOpenLayerEJH);
            break;
        case "tempSave":
            var rowData = detailLayerOut.getDatas().data;
            if (state != "3") {
                rowData.leaderFlag = "0";
            } else {
                rowData.leaderFlag = "1";
            }
            l.ajax("zjXmCqjxExecutiveAssistantReleaseTempSave", rowData, function (data, message, success) {
                if (success) {
                    layer.alert("暂存成功", { icon: 1 }, function (index) {
                        parent.pager.page('remote');
                        parent.layer.close(parent.myOpenLayerEJH);
                    });
                } else {
                    layer.alert("失败！", { icon: 0 });
                }
            })
            break;
    }
})
function myblur(rowData, type, value) {
    var rowData = JSON.parse(decodeURI(rowData));
    if (type === 'assessmentScore') {
        if (rowData.assessmentScore !== parseInt(value.val())) {
            rowData.assessmentScore = parseInt(value.val());
            l.ajax('updateZjXmCqjxDeptLeaderAssistantDetailed', rowData, function (data, message, success) {
                if (success) {
                    pager.page('remote');
                    pagers.page('remote');
                } else {
                    layer.alert(message, { icon: 0 });
                    pager.page('remote');
                    pagers.page('remote');
                }
            })
        }
    } else if (type === 'workPlan') {
        if (rowData.workPlan !== value.val()) {
            rowData.workPlan = value.val();
            l.ajax('updateZjXmCqjxDeptLeaderAssistantDetailed', rowData, function (data, message, success) {
                if (success) {
                    pager.page('remote');
                    pagers.page('remote');
                } else {
                    layer.alert(message, { icon: 0 });
                    pager.page('remote');
                    pagers.page('remote');
                }
            })
        }
    } else if (type === 'workTarget') {
        if (rowData.workTarget !== value.val()) {
            rowData.workTarget = value.val();
            l.ajax('updateZjXmCqjxDeptLeaderAssistantDetailed', rowData, function (data, message, success) {
                if (success) {
                    pager.page('remote');
                    pagers.page('remote');
                } else {
                    layer.alert(message, { icon: 0 });
                    pager.page('remote');
                    pagers.page('remote');
                }
            })
        }
    }
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
    var flag2 = ev.keyCode == 8 && !(t == "textarea" || t == "number");
    //判断 
    if (flag2) return false;
}
//禁止后退键 作用于Firefox、Opera
document.onkeypress = forbidBackSpace;
//禁止后退键  作用于IE、Chrome
document.onkeydown = forbidBackSpace;