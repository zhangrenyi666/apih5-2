var code = Apih5.getUrlParam('code')
var id = Apih5.getUrlParam('id');
var biaoti = Apih5.getUrlParam('biaoti');
var gangwei = Apih5.getUrlParam('gangwei');
var bumen = Apih5.getUrlParam('bumen');
var submitType = Apih5.getUrlParam('submitType');
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
            "width": 300,
            "defaultContent": "-",
            "render": function (data, _, rowData) {
                return "<textarea style='resize: none;width:100%;height:50px;padding:5px;box-sizing: border-box;' onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",$(this))'>" + data + "</textarea>";
            }
        }
    ]
});
var pager = $("#pager").page({
    remote: {// ajax请求配置
        url: l.getApiUrl("getZjXmCqjxProjectSecretaryrAssistantDetailedList"),
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
            "width": 300,
            "defaultContent": "-",
            "render": function (data, _, rowData) {
                return "<textarea style='resize: none;width:100%;height:50px;padding:5px;box-sizing: border-box;' onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",$(this))'>" + data + "</textarea>";
            }
        }
    ]
});
var pagers = $("#pagers").page({
    remote: {// ajax请求配置
        url: l.getApiUrl("getZjXmCqjxProjectSecretaryrAssistantDetailedList"),
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
            label: "分管领导",
            must: true,
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            }
        }
    ],
    onAdd: function (obj, _params) {
        l.ajax("zjXmCqjxProjectDeptLeaderScoreLaunch", _params, function (_d, _m, _s) {
            if (_s) {
                layer.alert(_m, { icon: 1, }, function (index) {
                    parent.pager.page('remote');
                    parent.layer.close(parent.myOpenLayerKH);
                });
            }
        })
    },
    onSave: function (obj, _params) {
        l.ajax("zjXmCqjxProjectDeptLeaderScoreLaunch", _params, function (_d, _m, _s) {
            if (_s) {
                layer.alert(_m, { icon: 1, }, function (index) {
                    parent.pager.page('remote');
                    parent.layer.close(parent.myOpenLayerKH);
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
    switch (name) {
        case "submit":
            l.ajax("zjXmCqjxProjectDeptLeaderScoreLaunch", { executiveId: id }, function (_d, _m, _s) {
                if (_s) {
                    layer.alert(_m, { icon: 1, }, function (index) {
                        parent.pager.page('remote');
                        parent.layer.close(parent.myOpenLayerKH);
                    });
                }      
            })
            break;
        case "cancel":
            parent.layer.close(parent.myOpenLayerKH);
            break;
    }
})
function myblur(rowData, value) {
    var rowData = JSON.parse(decodeURI(rowData));
    if (rowData.completion !== value.val()) {
        rowData.completion = value.val();
        l.ajax('updateZjXmCqjxProjectSecretaryrAssistantDetailed', rowData, function (data, message, success) {
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