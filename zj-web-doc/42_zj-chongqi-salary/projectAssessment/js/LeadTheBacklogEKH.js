var code = Apih5.getUrlParam('code')
var id = Apih5.getUrlParam('id');
var biaoti = Apih5.getUrlParam('biaoti');
var gangwei = Apih5.getUrlParam('gangwei');
var bumen = Apih5.getUrlParam('bumen');
var state = Apih5.getUrlParam('state');
var createUserName = Apih5.getUrlParam('createUserName');
var haveChangerLeader = Apih5.getUrlParam('haveChangerLeader');
var assistantLeaderApprovalId = Apih5.getUrlParam('assistantLeaderApprovalId');
Apih5.setCookie('code', code, 30);
var table;
if(state === '6'){
    table = $('#table').DataTable({
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
                    return "<textarea readonly style='resize: none;width:100%;height:50px;padding:5px;box-sizing: border-box;'>" + data + "</textarea>";
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
                "width": 100,
                "defaultContent": "-",
                "render": function (data, _, rowData) {
                    return "<input readonly style='width:100%;height:50px;line-height: 40px;font-size:18px;padding:5px;box-sizing: border-box;' type='number' min=0 value=" + data + " />";
                }
            },
            {
                "targets": [4],
                "data": "completion",
                "title": "完成情况",
                "width": 400,
                "defaultContent": "-",
                "render": function (data, _, rowData) {
                    return "<textarea readonly style='resize: none;width:100%;height:50px;padding:5px;box-sizing: border-box;'>" + data + "</textarea>";
                }
            },
            {
                "targets": [5],
                "data": "chargeLeaderScore",
                "title": "主管领导评分<br>（保留一位小数）",
                "defaultContent": "-",
                "width": 120,
                "render": function (data, _, rowData) {
                    if(!data){
                        // data = 0;
                        return "<input style='height:40px;width:100%;font-size:18px;padding: 0 5px;box-sizing: border-box;' type='number' value='' onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'chargeLeaderScore' + "\",$(this))' />";
                    }
                    // return "<input style='height:40px;width:100%;font-size:18px;padding: 0 5px;box-sizing: border-box;' type='number' min='1' max='5' value=" + data + " onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'score' + "\",$(this))' />";                    
                    return "<input style='width:100%;height:50px;line-height: 40px;font-size:18px;padding:5px;box-sizing: border-box;' type='number' min=0 value=" + data + " onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'chargeLeaderScore' + "\",$(this))' />";                    
                    // return "<input style='width:100%;height:50px;line-height: 40px;font-size:18px;padding:5px;box-sizing: border-box;' type='number' min=0 value=" + data + " onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'chargeLeaderScore' + "\",$(this))' />";
                }
            }
        ]
    });
}else{
    table = $('#table').DataTable({
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
                    return "<textarea readonly style='resize: none;width:100%;height:50px;padding:5px;box-sizing: border-box;'>" + data + "</textarea>";
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
                "width": 100,
                "defaultContent": "-",
                "render": function (data, _, rowData) {
                    return "<input readonly style='width:100%;height:50px;line-height: 40px;font-size:18px;padding:5px;box-sizing: border-box;' type='number' min=0 value=" + data + " />";
                }
            },
            {
                "targets": [4],
                "data": "completion",
                "title": "完成情况",
                "width": 400,
                "defaultContent": "-",
                "render": function (data, _, rowData) {
                    return "<textarea readonly style='resize: none;width:100%;height:50px;padding:5px;box-sizing: border-box;'>" + data + "</textarea>";
                }
            },
            {
                "targets": [5],
                "data": "chargeLeaderScore",
                "title": "主管领导评分",
                "defaultContent": "-",
                "width": 100,
                "render": function (data, _, rowData) {
                    return "<input readonly style='width:100%;height:50px;line-height: 40px;font-size:18px;padding:5px;box-sizing: border-box;' type='number' min=0 value=" + data + " />";
                }
            },
            {
                "targets": [6],
                "data": "executiveLeaderScore",
                "title": "公司分管领导评分<br>（保留一位小数）",
                "defaultContent": "-",
                "width": 140,
                "render": function (data, _, rowData) {
                    if(!data){
                        // data = 0;
                        return "<input style='height:40px;width:100%;font-size:18px;padding: 0 5px;box-sizing: border-box;' type='number' value='' onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'executiveLeaderScore' + "\",$(this))' />";
                    }
                    // return "<input style='height:40px;width:100%;font-size:18px;padding: 0 5px;box-sizing: border-box;' type='number' min='1' max='5' value=" + data + " onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'score' + "\",$(this))' />";                    
                    return "<input style='width:100%;height:50px;line-height: 40px;font-size:18px;padding:5px;box-sizing: border-box;' type='number' min=0 value=" + data + " onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'executiveLeaderScore' + "\",$(this))' />";
                }
            }
        ]
    });
}
var pager = $("#pager").page({
    remote: {// ajax请求配置
        url: l.getApiUrl("getZjXmCqjxProjectDeputyLeaderAssistantDetailedList"),
        params: {
            executiveId: id
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
        _params.approvalFlag = '0';
        l.ajax("zjXmCqjxProjectAssistantChargeLeaderScore", _params, function (_d, _m, _s) {
            if (_s) {
                layer.alert(_m, { icon: 1, }, function (index) {
                    parent.pager.page('remote');
                    parent.layer.close(parent.myOpenLayerEKH);
                });
            }
        })
    },
    onSave: function (obj, _params) {
        _params.approvalFlag = '0';
        l.ajax("zjXmCqjxProjectAssistantChargeLeaderScore", _params, function (_d, _m, _s) {
            if (_s) {
                layer.alert(_m, { icon: 1, }, function (index) {
                    parent.pager.page('remote');
                    parent.layer.close(parent.myOpenLayerEKH);
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
            type: "hidden",//
            name: "assistantLeaderApprovalId",//
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
        }
    ]
})

$('input[name="assessmentTitle"]').attr('disabled', true);
$('input[name="departmentName"]').attr('disabled', true);
$('input[name="position"]').attr('disabled', true);
$('input[name="createUserName"]').attr('disabled', true);
$('textarea[name="executiveLeaderOption"]').attr('disabled', true);
$('textarea[name="chargeLeaderOption"]').attr('disabled', true);
$("body").on("click", "button", function () {
    var name = $(this).attr("data-name");
    switch (name) {
        case "submit":
            if (state === '7') {
                var rowData = detailLayerOut.getDatas().data;
                rowData.approvalFlag = '0';
                rowData.haveChangerLeader = haveChangerLeader;
                l.ajax("zjXmCqjxProjectAssistantExecutiveLeaderScore", rowData, function (_d, _m, _s) {
                    if (_s) {
                        parent.pager.page('remote');
                        layer.alert(_m, { icon: 1, }, function (index) {
                            parent.layer.close(parent.myOpenLayerEKH);
                        });
                    }
                })
            } else {
                var rowData = detailLayerOut.getDatas().data;                
                rowData.approvalFlag = '0';
                rowData.haveChangerLeader = haveChangerLeader;                
                l.ajax("zjXmCqjxProjectAssistantChargeLeaderScore", rowData, function (_d, _m, _s) {
                    if (_s) {
                        parent.pager.page('remote');
                        layer.alert(_m, { icon: 1, }, function (index) {
                            parent.layer.close(parent.myOpenLayerEKH);
                        });
                    }
                })
                // l.ajax("getZjXmCqjxAssistantOaLeaderListByExecutiveId", { executiveId: id }, function (_d, _m, _s) {
                //     if (_s) {
                //         if (_d.leaderList) {
                //             detailLayer.open({ leaderList: _d.leaderList });
                //         } else {
                //             detailLayer.open();
                //         }
                //     }
                // })
            }
            break;
        case "cancel":
            parent.layer.close(parent.myOpenLayerEKH);
            break;
        case "tempSave":
            layer.alert("暂存成功！", { icon: 1, }, function (index) {
                parent.layer.close(parent.myOpenLayerEKH);
            });
    }
})
function myblur(rowData, type, value) {
    var rowData = JSON.parse(decodeURI(rowData));
    if (type === 'chargeLeaderScore') {
        if (rowData.chargeLeaderScore !== parseFloat(value.val())) {
            rowData.chargeLeaderScore = parseFloat(value.val());
            l.ajax('updateZjXmCqjxProjectDeputyLeaderAssistantDetailed', rowData, function (data, message, success) {
                if (success) {
                    //pager.page('remote');
                } else {
                    layer.alert(message, { icon: 0 });
                    pager.page('remote');
                }
            })
        }
    } else {
        if (rowData.executiveLeaderScore !== parseFloat(value.val())) {
            rowData.executiveLeaderScore = parseFloat(value.val());
            l.ajax('updateZjXmCqjxProjectDeputyLeaderAssistantDetailed', rowData, function (data, message, success) {
                if (success) {
                    //pager.page('remote');
                } else {
                    layer.alert(message, { icon: 0 });
                    pager.page('remote');
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