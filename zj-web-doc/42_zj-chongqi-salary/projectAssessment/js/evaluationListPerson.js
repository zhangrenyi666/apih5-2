var code = Apih5.getUrlParam('code')
var assessmentType = Apih5.getUrlParam('assessmentType')
Apih5.setCookie('code', code, 30)
var table = $('#table').DataTable({
    "info": false,//是否显示数据信息
    "paging": false,//是否开启自带分页
    "ordering": false, //是否开启DataTables的高度自适应
    "processing": false,//是否显示‘进度’提示
    "searching": false,//是否开启自带搜索
    "autoWidth": false,//是否监听宽度变化
    "columnDefs": [//表格列配置
        // {
        //     "targets": [0],
        //     "data": "rowIndex",
        //     "width": 13,
        //     "title": '<input type="checkbox">',
        //     "render": function (data) {
        //         return '<input type="checkbox" name="checkbox" data-rowIndex="' + data + '" >';
        //     }
        // },
        // {
        //     "targets": [1],
        //     "data": "rowIndex",
        //     "title": 'No.',
        //     "width": 25,
        //     "render": function (data) {
        //         return data + 1
        //     }
        // },
        {
            "targets": [0],
            "data": "createUserName",
            "title": "姓名",
            "defaultContent": "-"
        },
        {
            "targets": [1],
            "data": "quarterScore",
            "title": "得分",
            "defaultContent": "-",
        },
        {
            "targets": [2],
            "data": "departmentName",
            "title": "所在部门",
            "defaultContent": "-"
        },
        {
            "targets": [3],
            "data": "mobile",
            "title": "手机号",
            "defaultContent": "-"
        },
        {
            "targets": [4],//第几列
            "data": "assessmentState",//接口对应字段
            "title": "流程状态",//表头名
            "width": 100,
            "defaultContent": "-",//默认显示
            "render": function (data) {
                if (assessmentType === "1") {
                    if (data === '0') {
                        return '项目中层干部评分';
                    } else if (data === '1') {
                        return '项目其他领导班子副职评分';
                    } else if (data === '2') {
                        return '公司分管领导评分';
                    } else if (data === '3') {
                        return '公司主管领导评分';
                    } else if (data === '4') {
                        return '评分完成';
                    }
                } else if (assessmentType === "2") {
                    if (data === '0') {
                        return '项目中层干部评分';
                    } else if (data === '1') {
                        return '部门员工评分';
                    } else if (data === '2') {
                        return '项目分管领导评分';
                    } else if (data === '3') {
                        return '项目主管领导评分';
                    } else if (data === '4') {
                        return '评分完成';
                    }
                } else if (assessmentType === "3") {
                    if (data === '0') {
                        return '部门负责人评分';
                    } else if (data === '1') {
                        return '部门员工评分';
                    } else if (data === '2') {
                        return '项目分管领导评分';
                    } else if (data === '3') {
                        return '项目主管领导评分';
                    } else if (data === '4') {
                        return '评分完成';
                    }
                }
            }
        }
        // ,
        // {
        //     "targets": [6],//第几列
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
        //     "targets": [7],
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
        url: l.getApiUrl("getZjXmCqjxProjectOverallEvaluationAssistantList"),
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
function myOpen(title, executiveId, url) {
    l.ajax("zjXmCqjxProjectExecutiveAssistantReleaseLock", { executiveId: executiveId }, function () {
        pager.page('remote')
    })
}