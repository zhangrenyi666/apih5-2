var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
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
            "title": 'No.',
            "width": 25,
            "render": function (data) {
                return data + 1
            }
        },
        {
            "targets": [1],//第几列
            "data": "collaborationTitle",//接口对应字段
            "title": "标题",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [2],//第几列
            "data": "collaborationYears",//接口对应字段
            "title": "考核年度",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy");
            }
        },
        {
            "targets": [3],//第几列
            "data": "collaborationQuarter",//接口对应字段
            "title": "考核季度",//表头名
            "defaultContent": "-",//默认显示
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
                } else {
                    return '--';
                }
            }
        },
        // {
        //     "targets": [4],//第几列
        //     "data": "assessmentType",//接口对应字段
        //     "title": "考核类别",//表头名
        //     "defaultContent": "-",//默认显示
        //     "render": function (data) {
        //         if (data === '1') {
        //             return '副总师、总助';
        //         } else if (data === '2') {
        //             return '部门正、副职';
        //         } else if (data === '3') {
        //             return '员工';
        //         } else {
        //             return '--';
        //         }
        //     }
        // },
        {
            "targets": [4],//第几列
            "data": "assessmentFlag",//接口对应字段
            "title": "考核状态",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                if (data === '0') {
                    return '评分中';
                } else if (data === '1') {
                    return '评分完成';
                } else {
                    return '--';
                }
            }
        },
        {
            "targets": [5],//第几列
            "data": "closingDate",//接口对应字段
            "title": "评分截止日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },        
        {
            "targets": [6],//第几列
            "data": "collaborationRemarks",//接口对应字段
            "title": "备注",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "操作时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [8],//第几列
            "width": 100,
            "data": function (row) {
                return row
            },
            "title": "操作", 
            "render": function (data) {
                var html = '';
                if(data.assessmentFlag === '0'){
                    html += '<a style="color:blue;" onclick="myOpen(\'员工评分\',\'' + encodeURI(JSON.stringify(data)) + '\',\'' + 'mutualEvaluationDetail' + '\')">评分</a>';
                }
                return html;
            }
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",
            name: "disciplineTitle",
            label: "标题",
            placeholder: "请输入标题",
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
        url: l.getApiUrl("getZjXmCqjxCollaborationAssessmentListByUser"),
        params: {
            // sarsType:"1"
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
var myOpenLayer;
function myOpen(title, rowData, url) {
    var rowData = JSON.parse(decodeURI(rowData));
    if(rowData.assessmentLock === "1"){
        layer.alert("当前操作已经超过了规定的截止日期而被锁定，请联系管理员！", { icon: 0, }, function (index) {
            layer.close(index);
        });        
    }else{
        var options = {
            type: 2,
            title: title,
            area: ['100%', '100%'],
            content: url + ".html?code=" + code + '&collaborationId=' + rowData.collaborationId
        };
        myOpenLayer = layer.open(options);
    }
}
