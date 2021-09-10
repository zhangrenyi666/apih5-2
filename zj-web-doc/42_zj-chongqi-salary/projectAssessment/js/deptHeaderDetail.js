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
            "data": "quarterScore",
            "title": "得分",
            "defaultContent": "-",
        },
        {
            "targets": [4],
            "data": "departmentName",
            "title": "所在部门",
            "defaultContent": "-"
        },
        {
            "targets": [5],
            "data": "position",
            "title": "岗位",
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
        url: l.getApiUrl("getZjXmCqjxProjectAssistantListByDeptLeader"),
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
    l.ajax("zjXmCqjxProjectExecutiveAssistantReleaseLock",{executiveId:executiveId},function () {
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
                    window.location.href = "http://192.168.1.123:8080/web/ureport/excel?_u=file:项目领导班子副职.xml&_n=项目领导班子副职绩效考核&url=http://192.168.1.123:8080/web/&executiveId="+checkedData[0].executiveId;
                }else if(assessmentType === "2"){
                    window.location.href = "http://192.168.1.123:8080/web/ureport/excel?_u=file:项目中层干部.xml&_n=项目中层干部绩效考核&url=http://192.168.1.123:8080/web/&executiveId="+checkedData[0].executiveId;
                }else if(assessmentType === "3"){
                    window.location.href = "http://192.168.1.123:8080/web/ureport/excel?_u=file:项目员工.xml&_n=项目员工绩效考核&url=http://192.168.1.123:8080/web/&executiveId="+checkedData[0].executiveId;
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