var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
var assessmentYears = Apih5.getUrlParam('assessmentYears');
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
            "data": "createUserName",
            "title": "姓名",
            "defaultContent": "-"
        },
        {
            "targets": [3],
            "data": "quarterAverageScore",
            "title": "平均得分",
            "defaultContent": "-",
        },
        {
            "targets": [4],
            "data": "yearFinalScore",
            "title": "年度考核总分",
            "defaultContent": "-",
        },
        {
            "targets": [5],
            "data": "departmentName",
            "title": "所在部门",
            "defaultContent": "-"
        },
        {
            "targets": [6],
            "data": "position",
            "title": "岗位",
            "defaultContent": "-"
        },
        {
            "targets": [7],
            "data": "mobile",
            "title": "手机号",
            "defaultContent": "-"
        },
        {
            "targets": [8],//第几列
            // "data": "assessmentState",//接口对应字段
            "data": function (row) {
                return row
            },
            "title": "流程状态",//表头名
            "width": 100,
            "defaultContent": "-",//默认显示
            "render": function (data) {
                if(data.assessmentType === "2" || data.assessmentType === "1"){
                    if (data.assessmentState === '0') {
                        return '周边业绩考核';
                    } else if (data.assessmentState === '1') {
                        return '工作业绩';
                    } else if (data.assessmentState === '2') {
                        return '领导班子正职评价';
                    } else if (data.assessmentState === '3') {
                        return '督办工作评价';
                    } else if (data.assessmentState === '4') {
                        return '考核完成';
                    } else {
                        return '--';
                    }
                }else if(data.assessmentType === "3"){
                    if (data.assessmentState === '0') {
                        return '部门负责人评价';
                    } else if (data.assessmentState === '1') {
                        return '公司分管领导评价';
                    } else if (data.assessmentState === '2') {
                        return '公司主管领导评价';
                    } else if (data.assessmentState === '3') {
                        return '考核完成';
                    } else {
                        return '--';
                    }
                }
            }
        }
        // ,
        // {
        //     "targets": [8],//第几列
        //     "data": "assistantLock",//接口对应字段
        //     "title": "状态",//表头名
        //     "width": 100,
        //     "defaultContent": "-",//默认显示
        //     "render": function (data) {
        //         if(data === '1'){
        //             return '锁定';
        //         }else if(data === '0' || data === '2' || data === '3' || data === '4' || data === '5' || data === '6' || data === '7'){
        //             return '正常';
        //         }else{
        //             return '--'
        //         }
        //     }
        // },
        // {
        //     "targets": [9],
        //     "width": 80,
        //     "data": function (row) {
        //         return row
        //     },
        //     "title": "操作",//???-详情页面没看到
        //     "render": function (data) {
        //         var html = '';
        //         if(data.assistantLock === '1'){
        //             html += '<a style="color:blue;" onclick="myOpen(\'解除锁定\',\'' + data.executiveId + '\')">解除锁定</a>';
        //         }
        //         return html;
        //     }
        // }        
    ]
});

var form = $('#form').filterfrom({
    conditions: [
        {
            type: "text",
            name: "createUserName",
            label: "姓名",
            placeholder: "请输入姓名",
        },
        {
            type: "text",
            name: "deptName",
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
        url: l.getApiUrl("getZjXmCqjxYearAssistantList"),
        params: {
            managerId: l.getUrlParam('managerId')
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
$("body").on("click","button",function () {
    var name = $(this).attr("data-name");
    switch (name) {
        case "export":
            // window.location.href = "http://192.168.1.132:8081/ureport/excel?_u=file:年度考核汇总.xml&_n=年度考核汇总&url=http://192.168.1.132:8080/web/&assessmentYears="+assessmentYears;
            window.location.href = "http://10.11.240.2:89/ureport/excel?_u=file:ndexport.xml&_n=YearExport&url=http://10.11.240.2:88/apiCqgs/&assessmentYears="+assessmentYears;
            break;
    }
})
// $("body").on("click", "button", function () {
//     var checkedData = l.getTableCheckedData(table)
//     var name = $(this).attr("data-name");
//     switch (name) {
//         case "del":
//             if (checkedData.length == 0) {
//                 layer.alert("未选择任何项", { icon: 0, }, function (index) {
//                     layer.close(index);
//                 });
//             } else {
//                 layer.confirm("确定删除？", { icon: 0, }, function (index) {
//                     l.ajax("batchDeleteUpdateZjXmCqjxExecutiveAssistant", checkedData, function () {
//                         pager.page('remote')
//                     })
//                     layer.close(index);
//                 });
//                 //     }
//                 // }
//             }
//             break;
//     }
// })
function myOpen(title,executiveId, url) {
    l.ajax("zjXmCqjxExecutiveAssistantReleaseLock",{executiveId:executiveId},function () {
        pager.page('remote')
    })
}