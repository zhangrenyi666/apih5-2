var code = Apih5.getUrlParam('code')
var disciplineId = Apih5.getUrlParam('disciplineId');
var state = Apih5.getUrlParam('state');
var deptHeadOption = Apih5.getUrlParam('chargeLeaderOption');
var leaderOption = Apih5.getUrlParam('leaderOption');
var executiveLeaderOption = Apih5.getUrlParam('executiveLeaderOption');
var assistantLeaderApprovalId = Apih5.getUrlParam('assistantLeaderApprovalId');
if(state == "5"){
    executiveLeaderOption = "";
}
if(state == "3"){
    deptHeadOption = "";
}
Apih5.setCookie('code', code, 30);
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
            "title": 'No.',
            "width": 25,
            "render": function (data) {
                return data + 1
            }
        },
        {
            "targets": [1],
            "data": "oaUserName",
            "title": "姓名",
            "defaultContent": "-"
        },
        {
            "targets": [2],
            "data": "mobile",
            "title": "手机号",
            "defaultContent": "-"
        },
        {
            "targets": [3],
            "data": "oaOrgName",
            "title": "所在部门",
            "defaultContent": "-"
        },
        {
            "targets": [4],
            "data": "score",
            "title": "得分",
            "defaultContent": "-"
        },
        {
            "targets": [5],
            "data": "disciplineDetailedContent",
            "title": "备注",
            "defaultContent": "-"
        }        
    ]
});
var form = $('#form').filterfrom({
    conditions: [
        {
            type: "text",
            name: "oaUserName",
            label: "姓名",
            placeholder: "请输入姓名",
        },
        {
            type: "text",
            name: "oaOrgName",
            label: "所在部门",
            placeholder: "请输入部门",
        }
    ],
    onSearch: function (arr) {
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
                _params[arr[i].name] = arr[i].value;
        }
        pager.page('remote', _params)
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
        url: l.getApiUrl("getZjXmCqjxProjectDisciplineAssessmentMemberList"),
        params: {
            disciplineId: disciplineId
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
var detailLayer = $('#detailLayer').detailLayer({
    layerArea: ["50%", "50%"],
    conditions: [
        {
            type: "hidden",//
            name: "executiveId",//
            defaultValue: disciplineId
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
        if (rowData.deptHeadOption) {
            _params.deptHeadOption = rowData.deptHeadOption
        } else {
            _params.deptHeadOption = '同意';
        }
        l.ajax("zjXmCqjxProjectDisciplineAssessmentDeptApproval", _params, function (_d, _m, _s) {
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
        if (rowData.deptHeadOption) {
            _params.deptHeadOption = rowData.deptHeadOption
        } else {
            _params.deptHeadOption = '同意';
        }
        l.ajax("zjXmCqjxProjectDisciplineAssessmentDeptApproval", _params, function (_d, _m, _s) {
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
            name: "disciplineId",
            defaultValue: disciplineId
        },
        {
            type: "hidden",
            name: "assistantLeaderApprovalId",
            defaultValue: assistantLeaderApprovalId
        },
        {
            type: "textarea",
            name: "deptHeadOption",
            label: "部门负责人意见",
            defaultValue: deptHeadOption ? deptHeadOption : '同意',
            placeholder: "请输入"
        },
        {
            type: "textarea",
            name: "executiveLeaderOption",
            label: "主管领导意见",
            defaultValue: state ==='5' && !executiveLeaderOption ? '同意' : executiveLeaderOption,
            placeholder: "请输入"
        }
    ]
})
if (state === '3') {
    $('textarea[name="executiveLeaderOption"]').attr('disabled', true);
    if(leaderOption){
        $('textarea[name="deptHeadOption"]').text(leaderOption);    
    }
} else if (state === '5') {
    if(leaderOption){
        $('textarea[name="executiveLeaderOption"]').text(leaderOption);    
    }
    $('textarea[name="deptHeadOption"]').attr('disabled', true);
}
$("body").on("click", "button", function () {
    var name = $(this).attr("data-name");
    switch (name) {
        case "submit":
                    if (state === '3') {
                        var rowData = detailLayerOut.getDatas().data;
                        rowData.approvalFlag = '0';
                        if (rowData.deptHeadOption) {
                            rowData.deptHeadOption = rowData.deptHeadOption
                        } else {
                            rowData.deptHeadOption = '同意';
                        }
                        l.ajax("zjXmCqjxProjectDisciplineAssessmentDeptApproval", rowData, function (_d, _m, _s) {
                            if (_s) {
                                layer.alert(_m, { icon: 1, }, function (index) {
                                    parent.pager.page('remote');
                                    parent.layer.close(parent.myOpenLayerSH);
                                });
                            }
                        })
                    } else {
                        var rowData = detailLayerOut.getDatas().data;
                        rowData.approvalFlag = '0';
                        if (rowData.executiveLeaderOption) {
                            rowData.executiveLeaderOption = rowData.executiveLeaderOption
                        } else {
                            rowData.executiveLeaderOption = '同意';
                        }                        
                        l.ajax("zjXmCqjxProjectDisciplineAssessmentExecutiveApproval", rowData, function (_d, _m, _s) {
                            if (_s) {
                                layer.alert(_m, { icon: 1, }, function (index) {
                                    parent.pager.page('remote');
                                    parent.layer.close(parent.myOpenLayerSH);
                                });
                            }
                        })
                    }
            break;
        case "sendBack":
            if (state === '3') {
                var rowData = detailLayerOut.getDatas().data;
                rowData.approvalFlag = '1';
                if (rowData.deptHeadOption && rowData.deptHeadOption !== '同意') {
                    rowData.deptHeadOption = rowData.deptHeadOption
                } else {
                    rowData.deptHeadOption = '不同意';
                }
                l.ajax("zjXmCqjxProjectDisciplineAssessmentDeptApproval", rowData, function (data, message, success) {
                    if (success) {
                        parent.pager.page('remote');
                        layer.alert(message, { icon: 1 }, function (index) {
                            parent.layer.close(parent.myOpenLayerSH);
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
                l.ajax("zjXmCqjxProjectDisciplineAssessmentExecutiveApproval", rowData, function (data, message, success) {
                    if (success) {
                        parent.pager.page('remote');
                        layer.alert(message, { icon: 1 }, function (index) {
                            parent.layer.close(parent.myOpenLayerSH);
                        });
                    } else {
                        layer.alert(message, { icon: 0 });
                    }
                })
            }
            break;
        case "cancel":
            parent.layer.close(parent.myOpenLayerSH);
            break;
        case "tempSave":
            var rowData = detailLayerOut.getDatas().data;            
            if(state != "3"){
                rowData.leaderFlag = "1";
            }else{
                rowData.leaderFlag = "0";
            }
            l.ajax("zjXmCqjxProjectExecutiveAssistantReleaseTempSave", rowData, function (data, message, success) {
                if (success) {
                    layer.alert("暂存成功", { icon: 1 }, function (index) {
                        parent.pager.page('remote');
                        parent.layer.close(parent.myOpenLayerSH);
                    });
                } else {
                    layer.alert("失败！", { icon: 0 });
                }
            })
            break;
    }
})
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