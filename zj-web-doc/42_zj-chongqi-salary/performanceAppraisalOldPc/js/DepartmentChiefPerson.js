var code = Apih5.getUrlParam('code')
var id = Apih5.getUrlParam('id');
var biaoti = Apih5.getUrlParam('biaoti');
var gangwei = Apih5.getUrlParam('gangwei');
var bumen = Apih5.getUrlParam('bumen');
var submitType = Apih5.getUrlParam('submitType');
var chargeLeaderOption = Apih5.getUrlParam('chargeLeaderOption');
var executiveLeaderOption = Apih5.getUrlParam('executiveLeaderOption');
// console.log(executiveLeaderOption);
Apih5.setCookie('code', code, 30)
if (submitType === 'detail') {
    $('.submit').hide();
}
var table;
var tables;
if (submitType === 'add') {
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
                    if(!data){
                        return "<input style='width:100%;height:50px;line-height: 40px;font-size:18px;padding:5px;box-sizing: border-box;' type='number' min=0 value='' onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'assessmentScore' + "\",$(this))' />";
                    }else{
                        return "<input style='width:100%;height:50px;line-height: 40px;font-size:18px;padding:5px;box-sizing: border-box;' type='number' min=0 value=" + data + " onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'assessmentScore' + "\",$(this))' />";
                    }
                }
            }
        ]
    });
    tables = $('#tables').DataTable({
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
                    if(!data){
                        return "<input style='width:100%;height:50px;line-height: 40px;font-size:18px;padding:5px;box-sizing: border-box;' type='number' min=0 value='' onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'assessmentScore' + "\",$(this))' />";
                    }else{
                        return "<input style='width:100%;height:50px;line-height: 40px;font-size:18px;padding:5px;box-sizing: border-box;' type='number' min=0 value=" + data + " onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'assessmentScore' + "\",$(this))' />";
                    }
                }
            }
        ]
    });
} else {
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
                "width": 400,
                "render": function (data, _, rowData) {
                    return "<textarea readonly style='resize: none;width:100%;height:50px;padding:5px;box-sizing: border-box;'>" + data + "</textarea>";
                }
            },
            {
                "targets": [2],
                "data": "workTarget",
                "title": "目标",
                "defaultContent": "-",
                "width": 400,
                "render": function (data, _, rowData) {
                    return "<textarea readonly style='resize: none;width:100%;height:50px;padding:5px;box-sizing: border-box;'>" + data + "</textarea>";
                }
            },
            {
                "targets": [3],
                "data": "assessmentScore",
                "title": "考核分",
                "defaultContent": "-",
                "width": 100,
                "render": function (data, _, rowData) {
                    return "<input readonly style='width:100%;height:50px;line-height: 40px;font-size:18px;padding:5px;box-sizing: border-box;' type='number' min=0 value=" + data + " />";
                }
            },
            {
                "targets": [4],
                "data": "completion",
                "title": "完成情况",
                "defaultContent": "-",
                "width": 300,
                "render": function (data, _, rowData) {
                    return "<textarea readonly style='resize: none;width:100%;height:50px;padding:5px;box-sizing: border-box;'>" + data + "</textarea>";
                }
            },
            {
                "targets": [5],
                "data": "chargeLeaderScore",
                "title": "分管领导评分",
                "defaultContent": "-",
                "width": 100,
                "render": function (data, _, rowData) {
                    return "<input readonly style='width:100%;height:50px;line-height: 40px;font-size:18px;padding:5px;box-sizing: border-box;' type='number' min=0 value=" + data + " />";
                }
            },
            {
                "targets": [6],
                "data": "executiveLeaderScore",
                "title": "主管领导评分",
                "defaultContent": "-",
                "width": 100,
                "render": function (data, _, rowData) {
                    return "<input readonly style='width:100%;height:50px;line-height: 40px;font-size:18px;padding:5px;box-sizing: border-box;' type='number' min=0 value=" + data + " />";
                }
            }
        ]
    });
    tables = $('#tables').DataTable({
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
                "width": 400,
                "render": function (data, _, rowData) {
                    return "<textarea readonly style='resize: none;width:100%;height:50px;padding:5px;box-sizing: border-box;'>" + data + "</textarea>";
                }
            },
            {
                "targets": [2],
                "data": "workTarget",
                "title": "目标",
                "defaultContent": "-",
                "width": 400,
                "render": function (data, _, rowData) {
                    return "<textarea readonly style='resize: none;width:100%;height:50px;padding:5px;box-sizing: border-box;'>" + data + "</textarea>";
                }
            },
            {
                "targets": [3],
                "data": "assessmentScore",
                "title": "考核分",
                "defaultContent": "-",
                "width": 100,
                "render": function (data, _, rowData) {
                    return "<input readonly style='width:100%;height:50px;line-height: 40px;font-size:18px;padding:5px;box-sizing: border-box;' type='number' min=0 value=" + data + " />";
                }
            },
            {
                "targets": [4],
                "data": "completion",
                "title": "完成情况",
                "defaultContent": "-",
                "width": 300,
                "render": function (data, _, rowData) {
                    return "<textarea readonly style='resize: none;width:100%;height:50px;padding:5px;box-sizing: border-box;'>" + data + "</textarea>";
                }
            },
            {
                "targets": [5],
                "data": "chargeLeaderScore",
                "title": "分管领导评分",
                "defaultContent": "-",
                "width": 100,
                "render": function (data, _, rowData) {
                    return "<input readonly style='width:100%;height:50px;line-height: 40px;font-size:18px;padding:5px;box-sizing: border-box;' type='number' min=0 value=" + data + " />";
                }
            },
            {
                "targets": [6],
                "data": "executiveLeaderScore",
                "title": "主管领导评分",
                "defaultContent": "-",
                "width": 100,
                "render": function (data, _, rowData) {
                    return "<input readonly style='width:100%;height:50px;line-height: 40px;font-size:18px;padding:5px;box-sizing: border-box;' type='number' min=0 value=" + data + " />";
                }
            }
        ]
    });
}
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
                if(data.length>0){
                    $('#scoreOne').html("剩余分配分数“"+data[0].lastScore+"”分");
                }else{
                    $('#scoreOne').html("剩余分配分数“40”分");
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
                if(data.length>0){
                    $('#scoreTwo').html("剩余分配分数“"+data[0].lastScore+"”分");
                }else{
                    $('#scoreTwo').html("剩余分配分数“10”分");
                }                  
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
//外层新增表单
var detailLayerOut;
if (submitType === 'detail') {
    detailLayerOut = $('#detailLayerOut').detailLayer({
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
            },
            {
                type: "textarea",
                name: "chargeLeaderOption",
                label: "分管领导意见",
                defaultValue: chargeLeaderOption,
                placeholder: "请输入"
            },
            {
                type: "textarea",
                name: "executiveLeaderOption",
                label: "主管领导意见",
                defaultValue: executiveLeaderOption,
                placeholder: "请输入"
            }
        ]
    })
} else {
    detailLayerOut = $('#detailLayerOut').detailLayer({
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
                type: "select",
                name: "position",
                label: "岗位",
                placeholder: "请输入",
                must: true,      
                defaultValue: gangwei,                          
                selectList: [
                    {
                        value: "",
                        text: "请选择"
                    },
                    {
                        value: "部长",
                        text: "部长"
                    },
                    {
                        value: "副部长",
                        text: "副部长"
                    },
                    {
                        value: "高级主管",
                        text: "高级主管"
                    },
                    {
                        value: "主管",
                        text: "主管"
                    },
                    {
                        value:"主办",
                        text:"主办"
                    },
                    {
                        value:"办事员",
                        text:"办事员"
                    },
                    {
                        value:"实习生",
                        text:"实习生"
                    }                    
                ]
            }
        ]
    })
}
$("select option[value="+ gangwei +"] ").attr("selected",true);
$('input[name="assessmentTitle"]').attr('disabled', true);
$('input[name="departmentName"]').attr('disabled', true);
$('textarea[name="chargeLeaderOption"]').attr('disabled', true);
$('textarea[name="executiveLeaderOption"]').attr('disabled', true);
if (submitType === 'detail') {
    $('input[name="position"]').attr('disabled', true);
}
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var checkedDatas = l.getTableCheckedData(tables);
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            l.ajax("addZjXmCqjxDeptLeaderAssistantDetailed", { executiveId:id, workType:'0' }, function () {
                pager.page('remote')
            })
            break;
        case "adds":
            l.ajax("addZjXmCqjxDeptLeaderAssistantDetailed", { executiveId:id, workType:'1' }, function () {
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
                    l.ajax("batchDeleteUpdateZjXmCqjxDeptLeaderAssistantDetailed", checkedData, function () {
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
                    l.ajax("batchDeleteUpdateZjXmCqjxDeptLeaderAssistantDetailed", checkedDatas, function () {
                        pagers.page('remote')
                    })
                    layer.close(index);
                });
            }
            break;
        case "submit":
            var positions = detailLayerOut.getDatas().data.position;
            if (positions) {
                var _params = {
                    executiveId: id,
                    position: positions
                }
                if (submitType === 'add') {
                    l.ajax("zjXmCqjxDeptLeaderAssistantFillIn", _params, function (_d, _m, _s) {
                        if (_s) {
                            layer.alert(_m, { icon: 1, }, function (index) {
                                parent.pager.page('remote');
                                parent.layer.close(parent.myOpenLayer);
                            });
                        }
                    })
                } else {
                    l.ajax("updateZjXmCqjxDeptLeaderAssistantFillIn", _params, function (_d, _m, _s) {
                        if (_s) {
                            layer.alert(_m, { icon: 1, }, function (index) {
                                parent.pager.page('remote');
                                parent.layer.close(parent.myOpenLayer);
                            });
                        }
                    })
                }
            } else {
                layer.alert("岗位必填!", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
            break;
        case "cancel":
            parent.layer.close(parent.myOpenLayer);
            break;
    }
})
function myblur(rowData, type, value) {
    var rowData = JSON.parse(decodeURI(rowData));
    if (type === 'assessmentScore') {
        if (value.val() && rowData.assessmentScore !== parseInt(value.val())) {
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
    } else if(type === 'workPlan') {
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
    } else if(type === 'workTarget') {
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