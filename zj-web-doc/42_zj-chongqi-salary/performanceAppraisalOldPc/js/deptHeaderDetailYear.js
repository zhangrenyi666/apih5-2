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
            "title": "季度平均得分",
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
            "data": "mobile",
            "title": "手机号",
            "defaultContent": "-"
        }     
    ]
});

var form = $('#form').filterfrom({
    conditions: [
        {
            type: "text",
            name: "name",
            label: "姓名",
            placeholder: "请输入姓名",
        },
        {
            type: "text",
            name: "companyName",
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
        url: l.getApiUrl("getZjXmCqjxYearAssistantListByDeptLeader"),
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
function myOpen(title,executiveId, url) {
    l.ajax("zjXmCqjxExecutiveAssistantReleaseLock",{executiveId:executiveId},function () {
        pager.page('remote')
    })
}
$("body").on("click","button",function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
        case "export":
            if (checkedData.length == 1) {
                if(assessmentType === "1"){
                    //  window.location.href = "http://192.168.1.132:8081/ureport/excel?_u=file:副总师年度.xml&_n=副总师绩效考核&url=http://192.168.1.132:8080/web/&executiveId="+checkedData[0].yearAssistantId;
                    window.location.href = "http://10.11.240.2:89/ureport/excel?_u=fzs.xml&_n=副总师年度绩效考核&url=http://10.11.240.2:88/apiCqgs/&executiveId="+checkedData[0].yearAssistantId;
                }else if(assessmentType === "2"){
                    //  window.location.href = "http://192.168.1.132:8081/ureport/excel?_u=file:部门正副职年度.xml&_n=部门正副职绩效考核&url=http://192.168.1.132:8080/web/&executiveId="+checkedData[0].yearAssistantId;
                    window.location.href = "http://10.11.240.2:89/ureport/excel?_u=deptLeaderYear.xml&_n=deptLeaderYear&url=http://10.11.240.2:88/apiCqgs/&executiveId="+checkedData[0].yearAssistantId;

                }else if(assessmentType === "3"){
                    //  window.location.href = "http://192.168.1.132:8081/ureport/excel?_u=file:员工年度.xml&_n=员工绩效考核&url=http://192.168.1.132:8080/web/&executiveId="+checkedData[0].yearAssistantId;
                    window.location.href = "http://10.11.240.2:89/ureport/excel?_u=staffYear.xml&_n=staffYear&url=http://10.11.240.2:88/apiCqgs/&executiveId="+checkedData[0].yearAssistantId;

                }
            } else if (checkedData.length == 0) {
                layer.alert("未选择任何项",{ icon: 0,},function (index) {
                    layer.close(index);
                });
            } else {
                layer.alert("只能选择一个",{ icon: 0,},function (index) {
                    layer.close(index);
                });
            }
            break;
    }
})