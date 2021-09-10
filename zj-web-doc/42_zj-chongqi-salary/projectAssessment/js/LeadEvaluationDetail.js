var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30);
var overallEvaluationId = Apih5.getUrlParam('overallEvaluationId');
var assessmentTitle = Apih5.getUrlParam('title');
var position = Apih5.getUrlParam('position');
var dept = Apih5.getUrlParam('dept');
var createUserName = Apih5.getUrlParam('createUserName');
var table;
    table = $('#table').DataTable({
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
                "defaultContent": "-",
                "render": function (data) {
                    return data + 1
                }
            },
            {
                "targets": [1],
                "data": "itemText",
                "title": "考核项",
                "textalign":"center",
                "defaultContent": "-"
            },  
            {
                "targets": [2],
                "data": "assistantScore",
                "title": "考核分",
                "textalign":"center",
                "defaultContent": "-"
            },  
            {
                "targets": [3],
                "data": "stateText",
                "title": "评价人",
                "textalign":"center",
                "defaultContent": "-"
            },  
            {
                "targets": [4],
                "data": "itemScore",
                "title": "得分",
                "textalign":"center",
                "defaultContent": "-"
            }
        ]
    });
var pager = $("#pager").page({
    remote: {// ajax请求配置
        url: l.getApiUrl("getZjXmCqjxProjectOverallAssistantListByPrimaryKey"),
        params: {
            overallEvaluationId: overallEvaluationId,
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
//外层新增表单
var detailLayerOut = $('#detailLayerOut').detailLayer({
    layerArea: ["100%", "100%"],
    customBtnGroup: {
        btns: []
    },
    conditions: [
        {
            type: "text",
            name: "assessmentTitle",
            label: "考核标题",
            placeholder: "请输入",
            defaultValue: assessmentTitle,
            lineNum: 1
        },
        {
            type: "text",
            name: "departmentName",
            label: "考核项目",
            placeholder: "请输入",
            defaultValue: dept,
            lineNum: 1
        },
        {
            type: "text",
            name: "createUserName",
            label: "姓名",
            placeholder: "请输入",
            defaultValue: createUserName,
            lineNum: 2
        }
        // ,
        // {
        //     type: "text",
        //     name: "position",
        //     label: "岗位",
        //     placeholder: "请输入",
        //     defaultValue: position,
        //     lineNum: 2
        // }
    ]
})
$('input[name="assessmentTitle"]').attr('disabled', true);
$('input[name="departmentName"]').attr('disabled', true);
$('input[name="position"]').attr('disabled', true);
$('input[name="createUserName"]').attr('disabled', true);