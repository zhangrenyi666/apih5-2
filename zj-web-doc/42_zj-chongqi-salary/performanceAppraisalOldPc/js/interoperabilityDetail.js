var code = Apih5.getUrlParam('code');
Apih5.setCookie('code', code, 30);
var collaborationId = Apih5.getUrlParam('collaborationId');
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
            "targets": [1],
            "data": "oaUserName",
            "title": "姓名",
            "defaultContent": "-",
            "render": function (data, _, rowData) {
                var html = '<a style="color:blue;" onclick="myOpen(\'个人评分详细\',\'' + encodeURI(JSON.stringify(rowData)) + '\',\'' + 'interoperabilityDetails' + '\')">' + data + '</a>';
                return html;
            }            
        },
        {
            "targets": [2],
            "data": "mobile",
            "title": "手机号",
            "defaultContent": "-"
        },
        {
            "targets": [3],
            "data": "oaOrgName",
            "title": "所在部门",
            "defaultContent": "-"
        },
        {
            "targets": [4],
            "data": "assessmentScore",
            "title": "得分",
            "defaultContent": "-"
        },
        {
            "targets": [5],
            "data": "assessmentFlag",
            "title": "评分状态",
            "defaultContent": "-",
            "render": function (data) {
                if(data === '0'){
                    return '未完成';
                }else if(data === '1'){
                    return '已完成';
                }else{
                    return '--';
                }
            }
        },
        // {
        //     "targets": [6],
        //     "data": "effectiveNum",
        //     "title": "有效票数",
        //     "defaultContent": "-"
        // },
        // {
        //     "targets": [7],
        //     "data": "invalidNum",
        //     "title": "无效票数",
        //     "defaultContent": "-"
        // },
        {
            "targets": [6],//第几列
            "data": "assessmentLock",//接口对应字段
            "title": "状态",//表头名
            "width": 100,
            "defaultContent": "-",//默认显示
            "render": function (data) {
                if(data === '1'){
                    return '锁定';
                }else if(data === '0' || data === '2' || data === '3' || data === '4' || data === '5' || data === '6' || data === '7'){
                    return '正常';
                }else{
                    return '--'
                }
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
                if(data.assessmentLock === '1'){
                    html += '<a style="color:blue;" onclick="unlock(\'' + data.zcOaId + '\')">解除锁定</a>';
                }
                return html;
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
        url: l.getApiUrl("getZjXmCqjxCollaborationAssessmentMemberList"),
        params: {
            collaborationId: collaborationId
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
var myOpenLayer;
function myOpen(title, rowData, url) {
    var rowData = JSON.parse(decodeURI(rowData));
    var options = {
        type: 2,
        title: title,
        area: ['100%', '100%'],
        content: url + ".html?code=" + code + '&collaborationId=' + rowData.otherId + '&oaUserId=' + rowData.oaUserId
    };
    myOpenLayer = layer.open(options);
}
$("body").on("click","button",function () {
    var name = $(this).attr("data-name");
    switch (name) {
        case "export":
            // window.location.href = "http://192.168.1.123:8081/ureport/excel?_u=file:协作性考核导出.xml&_n=协作性考核&url=http://192.168.1.123:8080/web/&collaborationId="+collaborationId;
            window.location.href = "http://10.11.240.2:89/ureport/excel?_u=file:XZXexport.xml&_n=collaborativeExport&url=http://10.11.240.2:88/apiCqgs/&collaborationId="+collaborationId;
            break;
    }
})
function unlock(executiveId) {
    l.ajax("zjXmCqjxCollaborationAssistantReleaseLock",{zcOaId:executiveId},function () {
        pager.page('remote')
    })
}