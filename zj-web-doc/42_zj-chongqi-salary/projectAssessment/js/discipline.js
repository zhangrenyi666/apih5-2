var code = Apih5.getUrlParam('code')
Apih5.setCookie('code',code,30)
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
            "data": "disciplineTitle",//接口对应字段
            "title": "标题",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data,_,rowData) {
                var html = '<a style="color:blue;" onclick="myOpens(\'纪律性评分详情\',\'' + encodeURI(JSON.stringify(rowData)) + '\',\'' + 'disciplineDetails' + '\')">' + data + '</a>';
                return html;
            }
        },
        {
            "targets": [2],//第几列
            "data": "disciplineYears",//接口对应字段
            "title": "考核月度",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM");
            }
        },
        // {
        //     "targets": [3],//第几列
        //     "data": "disciplineQuarter",//接口对应字段
        //     "title": "考核季度",//表头名
        //     "defaultContent": "-",//默认显示
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
            "targets": [3],//第几列
            "data": "assessmentType",//接口对应字段
            "title": "考核类别",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                if (data === '1') {
                    return '领导班子副职';
                } else if (data === '2') {
                    return '中层干部';
                } else if (data === '3') {
                    return '员工';
                } else {
                    return '--';
                }
            }
        },
        {
            "targets": [4],//第几列
            "data": "disciplineState",//接口对应字段
            "title": "考核状态",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                if (data === '0') {
                    return '未评分';
                } else if (data === '1') {
                    return '评分中';
                } else if (data === '2') {
                    return '评分完成';
                } else if (data === '3') {
                    return '部门负责人审核';
                } else if (data === '4') {
                    return '部门负责人驳回';
                } else if (data === '5') {
                    return '主管领导审核';
                } else if (data === '6') {
                    return '主管领导驳回';
                } else {
                    return '--';
                }
            }
        },
        {
            "targets": [5],//第几列
            "data": "disciplineRemarks",//接口对应字段
            "title": "备注",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "操作时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [7],
            "width": 80,
            "data": function (row) {
                return row
            },
            "title": "操作",//???-详情页面没看到
            "render": function (data) {
                var html = '';
                // console.log(data);
                if(data.disciplineState != '2' && data.disciplineState != '3' && data.disciplineState != '5'){
                // if(data.disciplineState != '3'){
                    html += '<a style="color:blue;" onclick="myOpen(\'纪律性评分\',\'' + encodeURI(JSON.stringify(data)) + '\',\'' + 'disciplineDetail' + '\')">纪律性评分</a>';
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
        url: l.getApiUrl("getZjXmCqjxProjectDisciplineAssessmentList"),
        params: {
			// sarsType:"1"
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                $.each(data,function (index,item) {
                    item.rowIndex = index
                })
                table.clear().rows.add(data).draw();
                
            } else {
                layer.alert(result.message,{ icon: 0,},function (index) {
                    layer.close(index);
                });
            }
            
        }
    }
});
var myOpenLayer;
function myOpen(title,rowData, url) {
    var rowData = JSON.parse(decodeURI(rowData));
    var options = {
        type: 2,
        title: title,
        area: ['100%', '100%'],
        content: url + ".html?code=" + code + '&disciplineId=' + rowData.disciplineId
    };
    myOpenLayer = layer.open(options);
}
var myOpenLayers;
function myOpens(title,rowData, url) {
    var rowData = JSON.parse(decodeURI(rowData));
    if(!rowData.deptHeadOption){
        rowData.deptHeadOption = "";
    }
    if(!rowData.executiveLeaderOption){
        rowData.executiveLeaderOption = "";
    }
    var deptHeadOption = rowData.deptHeadOption.replace(/<br>/g,"\r\n");
    var executiveLeaderOption = rowData.executiveLeaderOption.replace(/<br>/g,"\r\n");
    deptHeadOption = encodeURI(deptHeadOption);
    executiveLeaderOption = encodeURI(executiveLeaderOption);
    var options = {
        type: 2,
        title: title,
        area: ['100%', '100%'],
        content: url + ".html?code=" + code + '&disciplineId=' + rowData.disciplineId + '&deptHeadOption=' + deptHeadOption + '&executiveLeaderOption=' + executiveLeaderOption
    };
    myOpenLayers = layer.open(options);
}
