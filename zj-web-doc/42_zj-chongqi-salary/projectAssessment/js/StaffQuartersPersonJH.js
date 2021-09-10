var code = Apih5.getUrlParam('code')
var id = Apih5.getUrlParam('id');
var biaoti = Apih5.getUrlParam('biaoti');
var gangwei = Apih5.getUrlParam('gangwei');
var bumen = Apih5.getUrlParam('bumen');
var submitType = Apih5.getUrlParam('submitType');
var assessmentType = Apih5.getUrlParam('assessmentType');
var assessmentState = Apih5.getUrlParam('assessmentState');
Apih5.setCookie('code', code, 30);
var param = {
    executiveId: id,
    assessmentType: assessmentType,
    assessmentState: assessmentState
}
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
            "defaultContent": "-",
            "render": function (data) {
                return data + 1
            }
        },
        {
            "targets": [2],
            "data": "workPlan",
            "title": "工作计划",
            "defaultContent": "-",
            "width": 500,
            "render": function (data, _, rowData) {
                return "<textarea style='resize: none;width:100%;height:50px;padding:5px;box-sizing: border-box;' onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'workPlan' + "\",$(this))'>" + data + "</textarea>";
            }
        },
        {
            "targets": [3],
            "data": "workTarget",
            "title": "目标",
            "defaultContent": "-",
            "width": 500,
            "render": function (data, _, rowData) {
                return "<textarea style='resize: none;width:100%;height:50px;padding:5px;box-sizing: border-box;' onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'workTarget' + "\",$(this))'>" + data + "</textarea>";
            }
        },
        {
            "targets": [4],
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
        url: l.getApiUrl("getZjXmCqjxProjectStaffAssistantDetailedList"),
        params: {
            executiveId: id,
            workType: '0'
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                if(data.length>0){
                    $('#score').html("剩余分配分数“"+data[0].lastScore+"”分");
                }else{
                    $('#score').html("剩余分配分数“60”分");
                }                
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
            "defaultContent": "-",
            "render": function (data) {
                return data + 1
            }
        },
        {
            "targets": [2],
            "data": "workPlan",
            "title": "工作计划",
            "defaultContent": "-",
            "width": 500,
            "render": function (data, _, rowData) {
                return "<textarea style='resize: none;width:100%;height:50px;padding:5px;box-sizing: border-box;' onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'workPlan' + "\",$(this))'>" + data + "</textarea>";
            }
        },
        {
            "targets": [3],
            "data": "workTarget",
            "title": "目标",
            "defaultContent": "-",
            "width": 500,
            "render": function (data, _, rowData) {
                return "<textarea style='resize: none;width:100%;height:50px;padding:5px;box-sizing: border-box;' onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'workTarget' + "\",$(this))'>" + data + "</textarea>";
            }
        },
        {
            "targets": [4],
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
        url: l.getApiUrl("getZjXmCqjxProjectStaffAssistantDetailedList"),
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
            label: "部门负责人",
            must: true,
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            }
        }
    ],
    onAdd: function (obj, _params) {
        l.ajax("zjXmCqjxProjectStaffAssistantLaunch", _params, function (_d, _m, _s) {
            if (_s) {
                layer.alert(_m, { icon: 1, }, function (index) {
                    parent.pager.page('remote');
                    parent.layer.close(parent.myOpenLayerJH);
                });
            }
        })
    },
    onSave: function (obj, _params) {
        l.ajax("zjXmCqjxProjectStaffAssistantLaunch", _params, function (_d, _m, _s) {
            if (_s) {
                layer.alert(_m, { icon: 1, }, function (index) {
                    parent.pager.page('remote');
                    parent.layer.close(parent.myOpenLayerJH);
                });
            }
        })
    }
})
var detailLayers = $('#detailLayers').detailLayer({
    layerArea: ["80%", "80%"],
    conditions: [
        {
            type: "hidden",//
            name: "executiveId",//
            defaultValue: id
        },
        {
            type: "hidden",//
            name: "workType",//
            defaultValue: '0',
        },
        {
            type: "hidden",//
            name: "staffId",
        },
        {
            type: "text",
            name: "workPlan",
            label: "工作计划",
            placeholder: "请输入",
            must: true
        },
        {
            type: "text",
            name: "workTarget",
            label: "目标",
            placeholder: "请输入",
            must: true
        },
        {
            type: "number",
            name: "assessmentScore",
            label: "考核分",
            placeholder: "请输入",
            max: 50,
            must: true
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateZjXmCqjxProjectStaffAssistantDetailed', _params, function (_d, _m, _s) {
            if (_s) {
                pager.page('remote')
                obj.close()
            }
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjXmCqjxProjectStaffAssistantDetailed', _params, function (_d, _m, _s) {
            if (_s) {
                pager.page('remote')
                obj.close()
            }
        })
    }
})
var detailLayert = $('#detailLayert').detailLayer({
    layerArea: ["80%", "80%"],
    conditions: [
        {
            type: "hidden",//
            name: "executiveId",//
            defaultValue: id
        },
        {
            type: "hidden",//
            name: "workType",//
            defaultValue: '1',
        },
        {
            type: "hidden",//
            name: "staffId",
        },
        {
            type: "text",
            name: "workPlan",
            label: "工作计划",
            placeholder: "请输入",
            must: true
        },
        {
            type: "text",
            name: "workTarget",
            label: "目标",
            placeholder: "请输入",
            must: true
        },
        {
            type: "number",
            name: "assessmentScore",
            label: "考核分",
            placeholder: "请输入",
            max: 50,
            must: true
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateZjXmCqjxProjectStaffAssistantDetailed', _params, function (_d, _m, _s) {
            if (_s) {
                pagers.page('remote')
                obj.close()
            }
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjXmCqjxProjectStaffAssistantDetailed', _params, function (_d, _m, _s) {
            if (_s) {
                pagers.page('remote')
                obj.close()
            }
        })
    }
})
// 流程发起
var forwardLayer;
function forwardrAddPersonnel(data) {
    var datas = [{
        value: "",//输入字段的值
        text: "请选择",//显示中文名
        selected: true//默认是否选中
    }];
    if (data.length) {
        for (var i = 0; i < data.length; i++) {
            datas.push({
                value: data[i].oaUserId + "-" + data[i].oaUserName + "-" + data[i].oaOrgId,
                text: data[i].oaUserName
            })
        }
    }
    return forwardLayer = $('#forwardLayer').detailLayer({
        layerArea: ['40%', '60%'],
        layerTitle: ['流程发起'],
        conditions: [
            {
                type: "hidden",//
                name: "executiveId",//
            },
            {
                type: "hidden",//
                name: "assessmentType",//
            },
            {
                type: "hidden",//
                name: "assessmentState",//
            },
            {
                type: "select",
                // search: true,
                name: "approvalUserId",
                label: "审批人",
                selectList: datas,
                must: true,
            }
        ],
        onSave: function (obj, _params) {
            l.ajax('zjXmCqjxProjectStaffAssistantSpecialLaunch', _params, function (data) {
                layer.confirm("提交成功！", { icon: 1, }, function (index) {
                    // pager.page('remote')
                    obj.close()
                    parent['pager'].page('remote')
                    layer_close()
                    layer.close(index);
                });
            })
        }
    })
}
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
            defaultValue: id
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
            name: "position",
            label: "岗位",
            placeholder: "请输入",
            defaultValue: gangwei
        }
    ]
})

$('input[name="assessmentTitle"]').attr('disabled', true);
$('input[name="departmentName"]').attr('disabled', true);
$('input[name="position"]').attr('disabled', true);
$("body").on("click", "button", function () {
    var name = $(this).attr("data-name");
    var checkedData = l.getTableCheckedData(table)
    var checkedDatas = l.getTableCheckedData(tables)
    switch (name) {
        case "add":
            l.ajax("addZjXmCqjxProjectStaffAssistantDetailed", { executiveId:id, workType:'0' }, function () {
                pager.page('remote')
            })
            break;
        case "adds":
            l.ajax("addZjXmCqjxProjectStaffAssistantDetailed", { executiveId:id, workType:'1' }, function () {
                pagers.page('remote')
            })
            break;
        case "del":
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.confirm("确定删除？", { icon: 0, }, function (index) {
                    l.ajax("batchDeleteUpdateZjXmCqjxProjectStaffAssistantDetailed", checkedData, function () {
                        pager.page('remote')
                    })
                    layer.close(index);
                });
            }
            break;
        case "dels":
            if (checkedDatas.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.confirm("确定删除？", { icon: 0, }, function (index) {
                    l.ajax("batchDeleteUpdateZjXmCqjxProjectStaffAssistantDetailed", checkedDatas, function () {
                        pagers.page('remote')
                    })
                    layer.close(index);
                });
            }
            break;
            case "submit":
                if(assessmentState === "2"){
                    l.ajax("checkZjXmCqjxProjectAssistantScoreQualified", { executiveId: id }, function (data, message, success) {
                        if (success) {
                            l.ajax("zjXmCqjxProjectStaffAssistantLaunch", { executiveId: id }, function (_d, _m, _s) {
                                if (_s) {
                                    layer.alert(_m, { icon: 1, }, function (index) {
                                        parent.pager.page('remote');
                                        parent.layer.close(parent.myOpenLayerJH);
                                    });
                                }                    
                            })                    
                        }
                    })
                }else{
                    l.ajax("getZjXmCqjxProjectLeaderApprovalAllList", { executiveId: id, assessmentType: assessmentType,assessmentState:assessmentState }, function (data, message, success) {
                        if (success) {
                            if (data.length && data.length === 1) {
                                l.ajax("zjXmCqjxProjectStaffAssistantLaunch", { executiveId: id, assessmentType: assessmentType,assessmentState:assessmentState }, function (_d, _m, _s) {
                                    if (_s) {
                                        layer.alert(_m, { icon: 1, }, function (index) {
                                            parent.pager.page('remote');
                                            parent.layer.close(parent.myOpenLayerJH);
                                        });
                                    }
                                })
                            } else {
                                forwardLayer = forwardrAddPersonnel(data);
                                forwardLayer.open(param);
                            }
                        }
                    });
                }
                break;            
        case "cancel":
            parent.layer.close(parent.myOpenLayerJH);
            break;
    }
})
function myblur(rowData, type, value) {
    var rowData = JSON.parse(decodeURI(rowData));
    if (type === 'assessmentScore') {
        if (rowData.assessmentScore !== parseInt(value.val())) {
            rowData.assessmentScore = parseInt(value.val());
            l.ajax('updateZjXmCqjxProjectStaffAssistantDetailed', rowData, function (data, message, success) {
                if (success) {
                    $('#score').html("剩余分配分数“"+data.lastScore+"”分");                        
                    pager.page('remote');
                    pagers.page('remote');
                } else {
                    layer.alert(message, { icon: 0 });
                    pager.page('remote');
                    pagers.page('remote');
                }
            })
        }
    } else if(type === 'workPlan') {
        if (rowData.workPlan !== value.val()) {
            rowData.workPlan = value.val();
            l.ajax('updateZjXmCqjxProjectStaffAssistantDetailed', rowData, function (data, message, success) {
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
    } else if(type === 'workTarget') {
        if (rowData.workTarget !== value.val()) {
            rowData.workTarget = value.val();
            l.ajax('updateZjXmCqjxProjectStaffAssistantDetailed', rowData, function (data, message, success) {
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