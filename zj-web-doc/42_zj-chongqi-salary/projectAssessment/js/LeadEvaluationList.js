var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
var title = "评分<br>(保留一位小数)";
tables(title);
    // l.ajax('getZjXmCqjxProjectEvaluationAssistantTodoList', {}, function (data, message, success) {
    //     if (success) {
    //         if(data.length>0){
    //             title = "评分（总分"+data[0].itemScore+"分）<br>(保留一位小数)";
    //         }
    //         tables(title);
    //     }else{
    //         tables(title);
    //     }
    // })
var table;
function tables(title){
    return table = $('#table').DataTable({
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
                "render": function (data) {
                    return data + 1
                }
            },
            {
                "targets": [1],
                "data": "createUserName",
                "title": "被考核人",
                "defaultContent": "-"
            },
            {
                "targets": [2],
                "data": "assessmentYears",
                "title": "考核月度",
                "defaultContent": "-",
                "render": function (data) {
                    return l.dateFormat(new Date(data), "yyyy-MM");
                }
            },
            // {
            //     "targets": [3],
            //     "data": "assessmentQuarter",
            //     "title": "考核季度",
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
                "targets": [3],
                "data": "assessmentType",
                "title": "考核类别",
                "defaultContent": "-",
                "render": function (data) {
                    if (data === '0') {
                        return '全部';
                    } else if (data === '1') {
                        return '项目领导班子副职';
                    } else if (data === '2') {
                        return '项目中层干部';
                    } else if (data === '3') {
                        return '项目员工';
                    } else {
                        return '--';
                    }
                }
            }, 
            {
                "targets": [4],
                "data": "itemText",
                "title": "考核项",
                "textalign":"center",
                "defaultContent": "-"
            },                 
            {
                "targets": [5],
                "data": "itemScore",
                "title": "考核分",
                "defaultContent": "-"
            },                 
            {
                "targets": [6],
                "data": "leaderScore",
                "title": title,
                "defaultContent": "-",
                "width": 110,
                "render": function (data, _, rowData) {
                    if(!data){
                        return "<input style='height:40px;width:100%;font-size:18px;padding: 0 5px;box-sizing: border-box;' read=false type='number' value='' onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'leaderScore' + "\",$(this))' />";
                    }
                    // return "<input style='height:40px;width:100%;font-size:18px;padding: 0 5px;box-sizing: border-box;' type='number' min='1' max='5' value=" + data + " onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'score' + "\",$(this))' />";                    
                    return "<input style='width:100%;height:50px;line-height: 40px;font-size:18px;padding:5px;box-sizing: border-box;' read=false type='number' min=0 value=" + data + " onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'leaderScore' + "\",$(this))' />";       
                    // return "<input style='width:100%;height:50px;line-height: 40px;font-size:18px;padding:5px;box-sizing: border-box;' type='number' min=0 value=" + data + " onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'executiveLeaderScore' + "\",$(this))' />";
                    //    return "<input style='height:40px;width:100%;font-size:18px;padding: 0 5px;box-sizing: border-box;' type='number' min='1' value=" + data + " onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'leaderScore' + "\",$(this))' />";                
                }
            },
            {
                "targets": [7],//第几列
                "width": 100,
                "data": function (row) {
                    return row
                },
                "title": "操作", 
                "render": function (data) {
                    var html= '';
                    // html += '<div><a style="padding-bottom:10px;" href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true" onclick="confirmTableRow('+data.rowIndex+')">通&nbsp;&nbsp;过</span></div>';          
                    // html += '<div style="padding-bottom:3px;"><a style="color:blue;" onclick="myOpen(\'流程详情\',\'' + data.rowIndex  + '\',\'' + 'LeadEvaluationDetail' + '\')">流程详情</div></a>';
                    html += '<div style="padding-bottom:3px;"><a style="color:blue;" onclick="save()">暂&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;存</a></div>';
                    html += '<div style="padding-bottom:3px;"><a style="color:blue;" onclick="submit('+data.rowIndex+')">提&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;交</a></div>';
                    // html += '</ul>'
                    // html += '</span>'                
                    return html;
                }
            }        
        ]
    });
}
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjXmCqjxProjectEvaluationAssistantTodoList"),
        params: {
            // sarsType:"1"
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                // console.log(data)
                // if(data.length>0){
                //     title = "评分（总分"+data[0].itemScore+"分）<br>(保留一位小数)";
                // }
                $.each(data, function (index, item) {
                    item.rowIndex = index
                })
                // tables(title);
                table.clear().rows.add(data).draw();
            } else {
                layer.alert(result.message, { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
        }
    }
});
function myblur(rowData, type, value) {
    var rowData = JSON.parse(decodeURI(rowData));
    if (type === 'leaderScore') {
        if(rowData.flowType === "1"){
            if (rowData.leaderScore !== parseFloat(value.val())) {
                rowData.leaderScore = parseFloat(value.val());
                l.ajax('updateZjXmCqjxProjectEvaluationApproval', rowData, function (data, message, success) {
                    if (success) {
                        pager.page('remote');
                    } else {
                        layer.alert(message, { icon: 0 });
                        pager.page('remote');
                    }
                })
            }
        }else{
            if (rowData.leaderScore !== parseFloat(value.val())) {
                rowData.leaderScore = parseFloat(value.val());
                l.ajax('updateZjXmCqjxProjectSuperviseApproval', rowData, function (data, message, success) {
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
}
function submit(rowIndex){
    var checkedData=table.row(rowIndex).data();    
    layer.confirm("确定提交吗？", { icon: 3, }, function (index) {
        if(checkedData.flowType === "2"){
            l.ajax("zjXmCqjxProjectEvaluationOfficeScore", checkedData, function (data, message, success) {
                if (success) {
                    pager.page('remote');
                    layer.close(index);
                }
            })
        }else
        if(checkedData.assessmentState === "0"){
            l.ajax("zjXmCqjxProjectEvaluationCadreScore", checkedData, function (data, message, success) {
                if (success) {
                    pager.page('remote');
                    layer.close(index);
                }
            })
        }else if(checkedData.assessmentState === "1"){
            l.ajax("zjXmCqjxProjectEvaluationOtherLeaderScore", checkedData, function (data, message, success) {
                if (success) {
                    pager.page('remote');
                    layer.close(index);
                }
            })
        }else if(checkedData.assessmentState === "2"){
            l.ajax("zjXmCqjxProjectEvaluationComChargeScore", checkedData, function (data, message, success) {
                if (success) {
                    pager.page('remote');
                    layer.close(index);
                }
            })
        }else if(checkedData.assessmentState === "3"){
            l.ajax("zjXmCqjxProjectEvaluationComExecutiveScore", checkedData, function (data, message, success) {
                if (success) {
                    pager.page('remote');
                    layer.close(index);
                }
            })
        }
    });    
}
function save(){
    layer.alert("保存成功！", { icon: 1 }, function (index) {     		 
        layer.close(index);
     }) 
}
function myOpen(title,rowIndex,url){
    var checkedData=table.row(rowIndex).data();       
    var options = {
        type: 2,
        title: title,
        area: ['70%', '70%'],
        // content: url + ".html?code=" + code + '&disciplineId=' + disciplineId
        content: url + ".html?code=" + code + '&overallEvaluationId=' + checkedData.overallEvaluationId +'&createUserName=' + checkedData.createUserName +'&title=' + checkedData.assessmentTitle +'&position=' + checkedData.position +'&dept=' + checkedData.departmentName
    };
    myOpenLayerSH = layer.open(options);
}
//阻止删除键后退方法
function forbidBackSpace(e) {
    var ev = e || window.event; //获取event对象 
    var obj = ev.target || ev.srcElement; //获取事件源 
    var t = obj.getAttribute('read') || ''; //获取事件源类型 
    // console.log(t);
    var flag2 = ev.keyCode == 8 && !t;
    //判断 
    //if (flag2 || flag1) return false;    
	if (flag2) return false;
}
//禁止后退键 作用于Firefox、Opera
document.onkeypress = forbidBackSpace;
//禁止后退键  作用于IE、Chrome
document.onkeydown = forbidBackSpace;