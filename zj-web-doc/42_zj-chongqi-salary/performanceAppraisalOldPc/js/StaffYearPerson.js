var code = Apih5.getUrlParam('code')
var id = Apih5.getUrlParam('id');
var biaoti = Apih5.getUrlParam('biaoti');
var gangwei = Apih5.getUrlParam('gangwei');
var bumen = Apih5.getUrlParam('bumen');
var assessmentYears = Apih5.getUrlParam('assessmentYears');
var yearFinalScore = Apih5.getUrlParam('yearFinalScore');
var quarterAverageScore = Apih5.getUrlParam('quarterAverageScore');
var submitType = Apih5.getUrlParam('submitType');
var chargeLeaderOption = Apih5.getUrlParam('chargeLeaderOption');
var executiveLeaderOption = Apih5.getUrlParam('executiveLeaderOption');
var haveChangerLeader = Apih5.getUrlParam('haveChangerLeader');
Apih5.setCookie('code', code, 30)
if (submitType === 'detail') {
    $('.submit').hide();
}
var table;
var tables;
if (submitType === 'add') {
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
                "data": "assessmentQuarter",
                "title": "季度",
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
                    }  else if (data === '6') {
                        return '年度';
                    } else {
                        return '--';
                    }
                }                
            },
            {
                "targets": [2],
                "data": "quarterScore",
                "title": "季度得分",
                "defaultContent": "-",
            },
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
                "data": "detailText",
                "title": "评价指标",
                "defaultContent": "-",
                "render": function (data, index, row) {
                    if(row.assessmentType === "1"){
                        if (data === '1') {
                            return '周边业绩考核';
                        } else if (data === '2') {
                            return '对公司各单位服务和指导职能的发挥';
                        } else if (data === '3') {
                            return '工作成绩';
                        } else if (data === '4') {
                            return '公司主要领导总体评价';
                        } else {
                            return '--';
                        }
                    }else if(row.assessmentType === "2"){
                        if (data === '1') {
                            return '周边业绩考核';
                        } else if (data === '2') {
                            return '对公司各单位服务和指导职能的发挥';
                        } else if (data === '3') {
                            return '工作成绩';
                        } else if (data === '4') {
                            return '公司主要领导总体评价';
                        } else if (data === '5') {
                            return '督办工作';
                        } else {
                            return '--';
                        }
                    }else if(row.assessmentType === "3"){
                        if (data === '1') {
                            return '工作任务完成情况';
                        } else if (data === '2') {
                            return '工作积极性';
                        } else if (data === '3') {
                            return '部门负责人评价';
                        } else if (data === '4') {
                            return '公司分管领导评价';
                        } else if (data === '5') {
                            return '公司主管领导评价';
                        } else {
                            return '--';
                        }
                    }
                }                
            },
            {
                "targets": [2],
                "data": "detailTarget",
                "title": "权重",
                "defaultContent": "-",
                "render": function (data, index, row) {
                    if(row.assessmentType === "1"){
                        if (data === '1') {
                            return '满分8分';
                        } else if (data === '2') {
                            return '满分8分';
                        } else if (data === '3') {
                            return '满分12分';
                        } else if (data === '4') {
                            return '满分12分';
                        } else {
                            return '--';
                        }
                    }else if(row.assessmentType === "2"){
                        if (data === '1') {
                            return '满分5分';
                        } else if (data === '2') {
                            return '满分10分';
                        } else if (data === '3') {
                            return '满分10分';
                        } else if (data === '4') {
                            return '满分10分';
                        } else if (data === '5') {
                            return '满分5分';
                        } else {
                            return '--';
                        }
                    }else if(row.assessmentType === "3"){
                        if (data === '1') {
                            return '满分8分';
                        } else if (data === '2') {
                            return '满分8分';
                        } else if (data === '3') {
                            return '满分8分';
                        } else if (data === '4') {
                            return '满分8分';
                        } else if (data === '5') {
                            return '满分8分';
                        } else {
                            return '--';
                        }
                    }
                }                  
            },
            {
                "targets": [3],
                "data": "detailScore",
                "title": "得分",
                "defaultContent": "-",
            }            
        ]
    });
}
var pager = $("#pager").page({
    remote: {// ajax请求配置
        url: l.getApiUrl("getZjXmCqjxExecutiveAssistantListByYear"),
        params: {
            // yearAssistantId: id,
            assessmentYears:assessmentYears,
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
var pagers = $("#pagers").page({
    remote: {// ajax请求配置
        url: l.getApiUrl("getZjXmCqjxYearAssistantScoreDetail"),
        params: {
            yearAssistantId: id,
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
// 内层新增表单
var detailLayer = $('#detailLayer').detailLayer({
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
        l.ajax('updateZjXmCqjxStaffAssistantDetailed', _params, function (_d, _m, _s) {
            if (_s) {
                pager.page('remote')
                obj.close()
            }
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjXmCqjxStaffAssistantDetailed', _params, function (_d, _m, _s) {
            if (_s) {
                pager.page('remote')
                obj.close()
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
        l.ajax('updateZjXmCqjxStaffAssistantDetailed', _params, function (_d, _m, _s) {
            if (_s) {
                pagers.page('remote')
                obj.close()
            }
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjXmCqjxStaffAssistantDetailed', _params, function (_d, _m, _s) {
            if (_s) {
                pagers.page('remote')
                obj.close()
            }
        })
    }
})
//外层新增表单
var detailLayerOut;
if(submitType === 'detail'){
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
                label: "考核标题",
                placeholder: "请输入",
                defaultValue: biaoti,
                lineNum: 1
            },
            {
                type: "text",
                name: "departmentName",
                label: "考核部门",
                placeholder: "请输入",
                defaultValue: bumen,
                lineNum: 1
            },
            {
                type: "text",
                name: "quarterAverageScore",
                label: "季度平均得分",
                placeholder: "请输入",
                defaultValue: quarterAverageScore,
                lineNum: 3                
            },           
            {
                type: "text",
                name: "yearFinalScore",
                label: "年度最终得分",
                placeholder: "请输入",
                defaultValue: yearFinalScore,
                lineNum: 3                
            }            
        ]
    })
}else{
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
$('input[name="quarterAverageScore"]').attr('disabled', true);
$('input[name="yearFinalScore"]').attr('disabled', true);
if (submitType === 'detail') {
    $('input[name="position"]').attr('disabled', true);
}
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table);
    var checkedDatas = l.getTableCheckedData(tables);
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            l.ajax("addZjXmCqjxStaffAssistantDetailed", { executiveId:id, workType:'0' }, function () {
                pager.page('remote')
            })
            break;
        case "adds":
            l.ajax("addZjXmCqjxStaffAssistantDetailed", { executiveId:id, workType:'1' }, function () {
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
                    l.ajax("batchDeleteUpdateZjXmCqjxStaffAssistantDetailed", checkedData, function () {
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
                    l.ajax("batchDeleteUpdateZjXmCqjxStaffAssistantDetailed", checkedDatas, function () {
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
                    l.ajax("zjXmCqjxStaffAssistantFillIn", _params, function (_d, _m, _s) {
                        if (_s) {
                            layer.alert(_m, { icon: 1, }, function (index) {
                                parent.pager.page('remote');
                                parent.layer.close(parent.myOpenLayer);
                            });
                        }
                    })
                } else {
                    l.ajax("updateZjXmCqjxStaffAssistantFillIn", _params, function (_d, _m, _s) {
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
            l.ajax('updateZjXmCqjxStaffAssistantDetailed', rowData, function (data, message, success) {
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
            l.ajax('updateZjXmCqjxStaffAssistantDetailed', rowData, function (data, message, success) {
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
            l.ajax('updateZjXmCqjxStaffAssistantDetailed', rowData, function (data, message, success) {
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