var code = Apih5.getUrlParam('code')
var id = Apih5.getUrlParam('id');
var biaoti = Apih5.getUrlParam('biaoti');
var gangwei = Apih5.getUrlParam('gangwei');
var bumen = Apih5.getUrlParam('bumen');
var submitType = Apih5.getUrlParam('submitType');
var assessmentType = Apih5.getUrlParam('assessmentType');
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
            "width": 800,
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
            "width": 300,
            "defaultContent": "-",
            "render": function (data, _, rowData) {
                return "<input style='width:100%;height:50px;line-height: 40px;font-size:18px;padding:5px;box-sizing: border-box;' type='number' min=0 value=" + data + " onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'input' + "\",$(this))' />";
            }
        }
    ]
});
var pager = $("#pager").page({
    remote: {// ajax请求配置
        url: l.getApiUrl("getZjXmCqjxExecutiveAssistantDetailedList"),
        params: {
            executiveId: id
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                if(data.length>0){
                    $('#score').html("剩余分配分数“"+data[0].lastScore+"”分");
                }else{
                    $('#score').html("剩余分配分数“50”分");
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
            label: "分管领导",
            must: true,
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            }
        }
    ],
    onAdd: function (obj, _params) {
        l.ajax("zjXmCqjxExecutiveAssistantLaunch", _params, function (_d, _m, _s) {
            if (_s) {
                layer.alert(_m, { icon: 1, }, function (index) {
                    parent.pager.page('remote');
                    parent.layer.close(parent.myOpenLayerJH);
                });
            }
        })
    },
    onSave: function (obj, _params) {
        l.ajax("zjXmCqjxExecutiveAssistantLaunch", _params, function (_d, _m, _s) {
            if (_s) {
                layer.alert(_m, { icon: 1, }, function (index) {
                    parent.pager.page('remote');
                    parent.layer.close(parent.myOpenLayerJH);
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
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            l.ajax("addZjXmCqjxExecutiveAssistantDetailed", { executiveId:id }, function () {
                pager.page('remote')
            })
            break;
        case "del":
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.confirm("确定删除？", { icon: 0, }, function (index) {
                    l.ajax("batchDeleteUpdateZjXmCqjxExecutiveAssistantDetailed", checkedData, function () {
                        pager.page('remote')
                    })
                    layer.close(index);
                });
            }
            break;
        case "submit":
            l.ajax("zjXmCqjxExecutiveAssistantLaunch", { executiveId: id,assessmentType:assessmentType }, function (_d, _m, _s) {
                if (_s) {
                    layer.alert(_m, { icon: 1, }, function (index) {
                        parent.pager.page('remote');
                        parent.layer.close(parent.myOpenLayerJH);
                    });
                }                    
            })
            break;
        case "cancel":
            parent.layer.close(parent.myOpenLayerJH);
            break;
    }
})
// function myblur(rowData, type, value) {
//     var rowData = JSON.parse(decodeURI(rowData));
//     if (type === 'input') {
//         if (rowData.assessmentScore !== parseInt(value.val())) {
//             rowData.assessmentScore = parseInt(value.val());
//             l.ajax('updateZjXmCqjxExecutiveAssistantDetailed', rowData, function (data, message, success) {
//                 if (success) {
//                     pager.page('remote');
//                 } else {
//                     layer.alert(message, { icon: 0 });
//                     pager.page('remote');
//                 }
//             })
//         }
//     } else {
//         if (rowData.workPlan !== value.val()) {
//             rowData.workPlan = value.val();
//             l.ajax('updateZjXmCqjxExecutiveAssistantDetailed', rowData, function (data, message, success) {
//                 if (success) {
//                     pager.page('remote');
//                 } else {
//                     layer.alert(message, { icon: 0 });
//                     pager.page('remote');
//                 }
//             })
//         }
//     }
// }
function myblur(rowData, type, value) {
    var rowData = JSON.parse(decodeURI(rowData));
    if (type === 'input') {
        if (rowData.assessmentScore !== parseInt(value.val())) {
            rowData.assessmentScore = parseInt(value.val());
            l.ajax('updateZjXmCqjxExecutiveAssistantDetailed', rowData, function (data, message, success) {
                if (success) {
                    pager.page('remote');
                } else {
                    layer.alert(message, { icon: 0 });
                    pager.page('remote');
                }
            })
        }
    } else if(type === 'workPlan'){
        if (rowData.workPlan !== value.val()) {
            rowData.workPlan = value.val();
            l.ajax('updateZjXmCqjxExecutiveAssistantDetailed', rowData, function (data, message, success) {
                if (success) {
                    pager.page('remote');
                } else {
                    layer.alert(message, { icon: 0 });
                    pager.page('remote');
                }
            })
        }       
    }  else if(type === 'workTarget'){
        if (rowData.workTarget !== value.val()) {
            rowData.workTarget = value.val();
            l.ajax('updateZjXmCqjxExecutiveAssistantDetailed', rowData, function (data, message, success) {
                if (success) {
                    pager.page('remote');
                } else {
                    layer.alert(message, { icon: 0 });
                    pager.page('remote');
                }
            })
        }      
    } 
}