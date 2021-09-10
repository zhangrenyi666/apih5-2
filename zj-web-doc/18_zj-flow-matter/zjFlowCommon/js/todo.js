var code = Lny.getUrlParam('code');
Lny.setCookie('code', code, 30);
var flowIdFlag = Lny.getUrlParam('flowId');

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
            "targets": [2],//第几列
            "data": "title",//接口对应字段
            "title": "标题",//表头名
            "width": 300,
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "sendUserName",//接口对应字段
            "title": "递交者",//表头名
            "defaultContent": "-",//默认显示
        },

        {
            "targets": [4],//第几列
            "data": "nodeName",//接口对应字段
            "title": "当前活动名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "flowStatus",//接口对应字段
            "title": "流程状态",//表头名
            "defaultContent": "-",//默认显示 
        },
        {
            "targets": [6],//第几列
            "width": 150,
            "data": "createTime",//接口对应字段
            "title": "发起时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [7],//第几列
            "width": 100,
            "data": "flowName",//接口对应字段
            "title": "所属流程",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [8],//第几列
            "width": 80,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//1：待办  2：未完成  3：延期审核中 4：完成审核中 5：已完成 6：未通过 
            "render": function (data) {
                var html = '';
                html += '<span class="dropDown">'
                if (data.flowId == 'lgsYongYin' || data.flowId == 'zjYongYin') {//6公司用印和局用印  是一个表
                    html += '<a style="color:blue;" href="javascript:void(0);" onclick="myOpen1(\' ' + data.rowIndex + '\',\'' + 'del6' + '\')" href="javascript:void(0);">删除</a>';
                } else if (data.flowId == 'wgsWxYongYin' || data.flowId == 'wgsYongYin') {
                    html += '<a style="color:blue;" href="javascript:void(0);" onclick="myOpen1(\' ' + data.rowIndex + '\',\'' + 'del5' + '\')" href="javascript:void(0);">删除</a>';
                } else {
                    html = '-';
                }
                html += '</span>'
                return html;
            }
        }
    ]
});

var allData;
var pager = $("#pager").page({
    remote: {// ajax请求配置
        url: l.getApiUrl("getTodoList"),
        params: {
            //flowId:flowIdFlag
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                allData = data;
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


var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "sendUserName",//输入字段名
            label: "递交者",//输入字段对杨的中文名称
            placeholder: "请输入递交者",
        },
        {
            type: "text",//三种形式：text,select,date,
            name: "keyword",//输入字段名
            label: "标题",//输入字段对杨的中文名称
            placeholder: "请输入标题",
        }
    ],
    onSearch: function (arr) {//搜索按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].name == "startDate" || arr[i].name == "endDate") {
                if (arr[i].value) {
                    _params[arr[i].name] = l.dateFormat(new Date(new Date(arr[i].value).getTime()), "yyyy-MM-dd");
                } else {
                    _params[arr[i].name] = "";
                }
            } else {
                _params[arr[i].name] = arr[i].value;
            }
        }
        pager.page('remote', 0, _params)
    },
    onReset: function (arr) {//重置按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
        pager.page('remote', _params)
    }
})


$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "process":
            if (checkedData.length == 1) {
                myOpen("流程处理", checkedData[0], "process")
            } else if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.alert("只能选择一个", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
            break;
    }
})


var myOpenLayer;
function myOpen(title, data, url) {
    if (data.flowId == 'zjYongYin') {//用印
        url = 'http://weixin.fheb.cn:99/flow-matter/process'
    } else if (data.flowId == 'zjYyOutSeal') {//外协用印
        url = 'http://weixin.fheb.cn:99/flow-matter/zjYyOutSealApplyProcess'
    } else if (data.flowId == 'flowIdZj1') {//需求确认
        url = 'http://weixin.fheb.cn:99/flow-matter/zjInfoNeedsConfirmProcess'
    } else if (data.flowId == 'flowId2') {//立项申请
        url = 'http://weixin.fheb.cn:99/flow-matter/zjInfoProjectApplyProcess'
    } else if (data.flowId == 'zjPartyFeeUse') {//党费使用申请
        url = 'http://weixin.fheb.cn:99/flow-matter/zjFlowPartyFeeUseProcess'
    } else if (data.flowId == 'jointSupApply') {//联合督导申请
        url = 'http://weixin.fheb.cn:99/zj-work-check/jointApplyProcess'
    } else if (data.flowId == 'jointSupDataList') {//-----资料清单
        url = 'http://weixin.fheb.cn:99/zj-work-check/jointSupDataListProcess'
    } else if (data.flowId == 'jointSupCheckIssue') {//-----检查问题清单
        url = 'http://weixin.fheb.cn:99/zj-work-check/jointSupCheckIssueProcess'
    } else if (data.flowId == 'jointSupCheckIssueReply') {//-----问题整改回复
        url = 'http://weixin.fheb.cn:99/zj-work-check/jointSupCheckIssueReplyProcess'
    } else if (data.flowId == 'yearSupPlanExecutive') {//-----监督计划执行情况审批
        url = 'http://weixin.fheb.cn:99/zj-work-check/jdjhYearSupPlanExecutiveProcess'
    } else if (data.flowId == 'yearSupPlan') {//-----年度
        url = 'http://weixin.fheb.cn:99/zj-work-check/jdjhYearSupPlanProcess'
    } else if (data.flowId == 'ZjMeetingRoom') {//会议室
        url = 'http://weixin.fheb.cn:99/zjMeetingRoom/zjMeetingRoomApplyProcess'
    } else if (data.flowId == 'zjLeaveApply') {//请假
        url = 'http://weixin.fheb.cn:99/flow-matter/zjFlowLeaveProcess'
    } else if (data.flowId == 'zjWorkApply') {//加班
        url = 'http://weixin.fheb.cn:99/flow-matter/zjFlowOvertimeProcess'
    } else if (data.flowId == 'zjTripApply') {//出差
        url = 'http://weixin.fheb.cn:99/flow-matter/zjFlowTripProcess'
    } else if (data.flowId == 'zjjwYongYin') {//纪委用印
        url = 'http://weixin.fheb.cn:99/flow-matter/zjFlowJwSealApplyProcess'
    } else if (data.flowId == 'zjAffairsApply') {//局机关事务
        url = 'http://weixin.fheb.cn:99/flow-matter/zjFlowAffairsApplyProcess'
    } else if (data.flowId == 'zjGoAbroad') {//因私出国申请（各单位）
        url = 'http://weixin.fheb.cn:99/flow-matter/zjFlowGoAbroadApplyProcess'
    } else if (data.flowId == 'zjjgGoAbroad') {//因私出国申请（机关）
        url = 'http://weixin.fheb.cn:99/flow-matter/zjFlowGoAbroadApplyJgProcess'
    } else if (data.flowId == 'lgsYongYin') {//6公司用印
        url = 'http://weixin.fheb.cn:99/flowMatterBranch/lgsSealProcess'
    } else if (data.flowId == 'zjZcCheck') {//资产验收
        url = 'http://weixin.fheb.cn:99/zjAssetsPc/assetsCheckProcess'
    } else if (data.flowId == 'zjZcScrap') {//资产报废
        url = 'http://weixin.fheb.cn:99/zjAssetsPc/assetsScrapProcess'
    } else if (data.flowId == 'zjDwYongYin') {//党委用印
        url = 'http://weixin.fheb.cn:99/flow-matter/zjDwSealProcess'
    } else if (data.flowId == 'wgsYongYin') {//5公司用印
        url = 'http://weixin.fheb.cn:99/flowMatterBranch/zjFlowYySealFiveProcess'
    } else if (data.flowId == 'wgsWxYongYin') {//5公司外携用印
        url = 'http://weixin.fheb.cn:99/flowMatterBranch/zjFlowYyOutSealFiveProcess'
    } else if (data.flowId == 'zcbZcTransfer') {//总承包资产调拨
        url = 'http://weixin.fheb.cn:99/zjAssetsPc/zcbZcTransferProcess'
    } else if (data.flowId == 'zjZcPurchaseCorp' || data.flowId == 'zjZcBuyForJu') {//----中交资产公司购置申请
        url = 'http://weixin.fheb.cn:99/zjAssetsPc/zjZcPurchaseProcess'
    } else if (data.flowId == 'sgsZcPurchaseCorp') {//三公司资产报废
        url = 'http://weixin.fheb.cn:99/zjAssetsPc/sgsZcScrapProcess'
    } else if (data.flowId == 'stZcPurchase') {//世通重工
        url = 'http://weixin.fheb.cn:99/zjAssetsPc/zjZcPurchaseProcessForStzg'
    } else if (data.flowId == 'stCcPurchase') {//世通重工出差
        url = 'http://weixin.fheb.cn:99/flow-matter/zjFlowStEvectionProcess'
    }



    var options = {
        type: 2,
        title: title,
        content: url + ".html?workId=" + data.workId + "&trackId=" + data.trackId + "&nodeId=" + data.nodeId + "&flowId=" + data.flowId + "&title=" + data.title + "&code=" + code + "&status=" + data.status
    }
    myOpenLayer = layer.open(options)
    layer.full(myOpenLayer)
}

function myOpen1(index, type) {
    var rowData = allData[Number(index)];
    var params = [];
    params[0] = rowData;
    switch (type) {
        case 'del6':
            layer.confirm("删除的数据无法恢复，请谨慎操作！！！确定删除？", { icon: 0, }, function (index) {
                l.ajax("batchDeleteUpdateZjYySealApply", params, function () {
                    pager.page('remote')
                })
                layer.close(index);
            });
            break;
        case 'del5':
            layer.confirm("删除的数据无法恢复，请谨慎操作！！！确定删除？", { icon: 0, }, function (index) {
                l.ajax("batchDeleteUpdateZjFlowYySealFive", params, function () {
                    pager.page('remote')
                })
                layer.close(index);
            });
            break;
    }

}