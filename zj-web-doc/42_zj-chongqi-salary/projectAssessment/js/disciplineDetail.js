var code = Apih5.getUrlParam('code');
Apih5.setCookie('code', code, 30);
var disciplineId = Apih5.getUrlParam('disciplineId');
var datas = [];
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
            "render": function (data) {
                return data + 1
            }
        },
        {
            "targets": [2],
            "data": "oaUserName",
            "title": "姓名",
            "defaultContent": "-"
        },
        {
            "targets": [3],
            "data": "mobile",
            "title": "手机号",
            "defaultContent": "-"
        },
        {
            "targets": [4],
            "data": "oaOrgName",
            "title": "所在部门",
            "defaultContent": "-"
        },
        {
            "targets": [5],
            "data": "score",
            "title": "得分（满分10分）",
            "width":100,
            "defaultContent": "-",
            "render": function (data, _, rowData) {
                if(!data){
                    // data = 0;
                    return "<input style='height:40px;width:100%;font-size:18px;padding: 0 5px;box-sizing: border-box;' type='number' min='1' max='5' value='' onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'score' + "\",$(this))' />";
                }
                return "<input style='height:40px;width:100%;font-size:18px;padding: 0 5px;box-sizing: border-box;' type='number' min='1' max='5' value=" + data + " onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'score' + "\",$(this))' />";
            }
        },
        {
            "targets": [6],
            "data": "disciplineDetailedContent",
            "title": "备注（如果扣分，请写明扣分原因）",
            "width":400,
            "defaultContent": "-",
            "render": function (data, _, rowData) {
                if(!data){
                    data = '';
                }
                // return "<input style='width:100%;font-size:18px;padding: 0 5px;box-sizing: border-box;' type='number' min='1' max='5' value=" + data + " onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'disciplineRemarks' + "\",$(this))' />";
                return "<textarea style='height:40px;width:100%;font-size:18px;padding: 0 5px;box-sizing: border-box;' onblur='myblur(\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + 'disciplineDetailedContent' + "\",$(this))'>" + data + "</textarea>";
            }
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
                datas = data;                
                table.clear().rows.add(data).draw();
            } else {
                layer.alert(result.message, { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
        },
    }
});
function myblur(rowData,type, value) {
    var rowData = JSON.parse(decodeURI(rowData));
    if (type === 'score') {
        if (rowData.score !== parseFloat(value.val()) && value.val()) {
            rowData.score = parseFloat(value.val());
            l.ajax('batchAddZjXmCqjxProjectDisciplineAssessmentMember', [rowData], function (data, message, success) {
                if (success) {
                    pager.page('remote');
                    parent.pager.page('remote')
                } else {
                    layer.alert(message, { icon: 0 });
                    pager.page('remote');
                }
            })
        }
    } else if(type === 'disciplineDetailedContent'){
        if (rowData.disciplineDetailedContent !== value.val()) {
            rowData.disciplineDetailedContent = value.val();
            l.ajax('batchAddZjXmCqjxProjectDisciplineAssessmentMember', [rowData], function (data, message, success) {
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
$("body").on("click", "button", function () {
    var name = $(this).attr("data-name");
    switch (name) {
        case "temporary":
            layer.alert("暂存成功！", { icon: 1 }, function (index) {
                parent.layer.close(parent.myOpenLayer);
            });
            break;
        case "submit":
            layer.confirm("确定提交吗？", { icon: 3, }, function (index) {
                l.ajax("zjXmCqjxProjectDisciplineAssessmentSubmit", {disciplineId:disciplineId}, function (data, message, success) {
                    if (success) {
                        parent.pager.page('remote');
                        parent.layer.close(parent.myOpenLayer);
                    }
                })
            });
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
    var flag2 = ev.keyCode == 8 && !(t == "textarea" || t == "number");
    //判断 
    if (flag2) return false;
}
//禁止后退键 作用于Firefox、Opera
document.onkeypress = forbidBackSpace;
//禁止后退键  作用于IE、Chrome
document.onkeydown = forbidBackSpace;