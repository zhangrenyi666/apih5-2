// var table1;var pager1;
var fallInYear = Lny.getUrlParam('fallInYear')
var flowId = Lny.getUrlParam('id') || "ZjMeetingPlanSummary";//流程模版id
var flowIdAdd = Lny.getUrlParam('flowId') || "ZjMeetingPlanSummary";//流程模版id
var code = Lny.getUrlParam('code');
Lny.setCookie('code', code, 30);

var $tabSystem = $("#tab-system")//模版顶级jq对象
var $tabBar = $('<div class="tabBar cl"></div>');//tab按钮控制条
var tabCons = []//tab内容页面组
//根据flowId获取流程发起模版数据及数据
var flowFormJson = {
    name: "",
    tabs: []
}
var $tabTitle = $("#tab-title")//模版title
$tabTitle.html("中交会议计划汇总")
//列表 === 3的流程tab使用的变量
var pager1;
var table1;
switch (flowId) {
    case "ZjMeetingPlanSummary":
        flowFormJson = {//流程模版json（升级版：根据流程id动态获取，当前前台写死）
            name: "中交会议计划汇总",
            titleName: "sealApplyName",
            tabs: [
                {
                    name: "基本信息",
                    type: "1",//普通tab页1，附件tab页2，列表tab页面3
                    isMain: "1",//是否为主表
                    tbName: "zjMeetingPlanFlow",//表名
                    tbIdName: "planFlowId",//表主键id
                    conditions: [
                        {
                            type: "hidden",//
                            name: "planFlowId",//
                            label: "主键id",//
                            placeholder: ""
                        },
                        {
                            type: "hidden",//
                            name: "fallInYear",//
                            label: "年限",//
                            placeholder: ""
                        },
                        {
                            type: "qnnTable",//
                            name: "qnnTableTest",//
                            // label: "万能表格测试",//   
                            createFormCb: function (tableId, pagerId) {
                                //表格创建完毕  正常请求数据
                                // console.log("表格创建完毕，可以请求数据");
                                table1 = $("#" + tableId).DataTable({
                                    info: false, //是否显示数据信息
                                    paging: false, //是否开启自带分页
                                    ordering: false, //是否开启DataTables的高度自适应
                                    processing: false, //是否显示‘进度’提示
                                    searching: false, //是否开启自带搜索
                                    autoWidth: false, //是否监听宽度变化
                                    columnDefs: [
                                        //表格列配置
                                        {
                                            targets: [0],
                                            data: "rowIndex",
                                            title: "No.",
                                            width: 25,
                                            render: function (data) {
                                                return data + 1;
                                            }
                                        },
                                        {
                                            targets: [1], //第几列
                                            data: "fallInYear", //接口对应字段
                                            title: "年度", //表头名
                                            defaultContent: "-", //默认显示
                                        },
                                        {
                                            targets: [2], //第几列
                                            data: "meetingTimeStr", //接口对应字段
                                            title: "会议时间", //表头名
                                            defaultContent: "-", //默认显示
                                        },
                                        {
                                            targets: [3], //第几列
                                            data: "meetingNameStr", //接口对应字段
                                            title: "会议名称", //表头名
                                            defaultContent: "-", //默认显示
                                        },
                                        {
                                            targets: [4], //第几列
                                            data: "meetingForm", //接口对应字段
                                            title: "会议形式", //表头名
                                            defaultContent: "-", //默认显示
                                        },
                                        {
                                            targets: [5], //第几列
                                            data: "joinObject", //接口对应字段
                                            title: "参加对象", //表头名
                                            defaultContent: "-", //默认显示
                                        },
                                        {
                                            targets: [6], //第几列
                                            data: "joinNumber", //接口对应字段
                                            title: "人数", //表头名
                                            defaultContent: "-", //默认显示
                                        },
                                        {
                                            targets: [7], //第几列
                                            data: "sponsoringDept", //接口对应字段
                                            title: "主办部门", //表头名
                                            defaultContent: "-", //默认显示
                                        },
                                        {
                                            targets: [8], //第几列
                                            data: "coOperationDept", //接口对应字段
                                            class: "text-overflow",
                                            title: "协办部门", //表头名
                                            defaultContent: "-", //默认显示
                                        },
                                        {
                                            targets: [9], //第几列
                                            data: "budgetaryCost", //接口对应字段                                              
                                            title: "预算费用（万元）", //表头名
                                            defaultContent: "-", //默认显示
                                        },
                                        {
                                            targets: [10], //第几列
                                            data: "meetingRemakes", //接口对应字段                                              
                                            title: "备注", //表头名
                                            defaultContent: "-", //默认显示
                                        }
                                    ]
                                });
                                pager1 = $("#" + pagerId).page({
                                    remote: {
                                        //ajax请求配置
                                        url: l.getApiUrl("getZjMeetingPlanFallInListFallInYear"),
                                        params: {
                                            fallInYear: fallInYear,
                                            approvalState: "2"
                                        },
                                        success: function (result) {
                                            if (result.success) {
                                                var data = result.data;
                                                $.each(data, function (index, item) {
                                                    item.rowIndex = index;
                                                });
                                                table1
                                                    .clear()    
                                                    .rows.add(data)
                                                    .draw();
                                            } else {
                                                layer.alert(
                                                    result.message,
                                                    {
                                                        icon: 0
                                                    },
                                                    function (index) {
                                                        layer.close(index);
                                                    }
                                                );
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    ]
                },
            ]
        }
        break;
    default:
        break;
}
$.each(flowFormJson.tabs, function (i, tabItem) {//第一次遍历flowFormJson.tabs
    var $tabBtn = $('<span>' + tabItem.name + '</span>')//创建tab按钮$对象
    $tabBar.append($tabBtn)//向tab按钮控制条插入tab按钮
    var $tabCon = $('<div class="tabCon" id="tab' + i.toString() + '"></div>')//创建tab内容页面$对象
    var customBtnGroup;//tab内容页面中表单的底部按钮组配置
    if (tabItem.isMain) {//如果是主表单
        customBtnGroup = {
            btns: [
                {
                    name: "launch",
                    label: '<i class="Hui-iconfont">&#xe603;</i> 发起'
                },
                {
                    name: "cancel",
                    label: "取消"
                }
            ],
            callback: function (dataName, obj) {
                switch (dataName) {
                    case "launch":
                        var body = {
                            flowId: flowId//流程id
                        }
                        for (var j = 0; j < flowFormJson.tabs.length; j++) {//第二次遍历flowFormJson.tabs，为了让return起作用所以不用$.each而采用for循环
                            var tabItemj = flowFormJson.tabs[j]//模版中tabs数组的遍历元素数据对象
                            var tabObjDatas = tabCons[j].getDatas();//tab内容页面组的遍历对象获取数据对象
                            if (tabObjDatas.err.length) {//判断是否有错误（字段不能为空、超过个数限制等）
                                layer.alert(tabObjDatas.err.join("<br/>"), { icon: 0, }, function (index) {
                                    $tabSystem.Huitab({
                                        index: j
                                    });
                                    layer.close(index);
                                });
                                return//直接停止for循环，且for循环之后的代码也不执行
                            }
                            if (tabItemj.isMain) {//如果是主表
                                //给主表赋值
                                body["mainTableName"] = tabItemj.tbName;
                                body["mainTablePrimaryIdName"] = tabItemj.tbIdName;
                                body["mainTableDataObject"] = tabObjDatas.data;
                                body["apiBody"] = {}
                                //add by lny on 717
                                for (var key in tabObjDatas.data) {
                                    body["apiBody"][key] = tabObjDatas.data[key]
                                }
                                body["apiBody"]['fallInYear'] = fallInYear                            
                                //add by lny on 717 
                                // title: ['applyUserId', 'sendTime', '用印申请'], //标题字段
                                // body["title"] = tabObjDatas.data[flowFormJson.titleName];
                                function sup(n) { return (n < 10) ? '0' + n : n; }
                                var now = new Date();
                                var y = now.getFullYear();
                                var m = sup(now.getMonth() + 1);
                                var d = sup(now.getDate());
                                var h = sup(now.getHours());
                                var mm = sup(now.getMinutes());
                                var s = sup(now.getSeconds());
                                var formatDate = y + '-' + m + '-' + d + ' ' + h + ':' + mm + ':' + s;
                                body["title"] = formatDate + '  中交会议计划汇总申请';
                            } else if (tabItemj.type === "2") {//如果是附件类型子表，type==="2"
                                //给子表赋值-附件表
                                if (!body["subTableObject"]) { body["subTableObject"] = {} }
                                for (var key in tabObjDatas.data) {
                                    var subTableDataObject = tabObjDatas.data[key];
                                    //add by lny on 717
                                    body["apiBody"][key] = tabObjDatas.data[key]
                                    //add by lny on 717
                                    body["subTableObject"][key] = { subTablePrimaryIdName: tabItemj.tbIdName, subTableType: tabItemj.type, subTableDataObject: subTableDataObject }
                                }
                            }
                            //测试ing
                            else if (tabItemj.type === "3") {
                                // var tConfig = 
                                // console.log(123)
                            }
                            //测试ing
                            else {//如果是普通类型子表，type==="1"，目前只有1和2
                                //给子表赋值-普通表
                                if (!body["subTableObject"]) { body["subTableObject"] = {} }
                                //add by lny on 717
                                for (var key in tabObjDatas.data) {
                                    body["apiBody"][key] = tabObjDatas.data[key]
                                }
                                //add by lny on 717
                                body["subTableObject"][tabItemj.tbName] = { subTablePrimaryIdName: tabItemj.tbIdName, subTableType: tabItemj.type, subTableDataObject: tabObjDatas.data }
                            }
                            //add by lny on 717
                            body["apiName"] = "addZjMeetingPlanFlow"//购置申请发起时调用
                            //add by lny on 717
                        }
                        //流程发起特殊代码---开始
                        layer.confirm("确定发起？", { icon: 0, }, function (index) {//流程发起请求
                            l.ajax("createOpenFlow", body, function (_d, _m, _s, _r) {
                                if (_s) {//发起成功，返回workId
                                    loadFlow(_d.flowButtons, {
                                        otherBody: {
                                            title: body["title"],
                                            flowId: flowId,//流程id
                                            nodeId: _d.flowNode.nodeId,
                                            trackId: _d.flowNode.trackId,
                                            workId: _d.workId,
                                            flowVars: _d.flowVars,
                                            nodeVars: _d.nodeVars,
                                            apih5FlowStatus: _d.apih5FlowStatus,
                                            apiName: "updateZjMeetingPlanFlow",
                                            apiBody: { workId: _d.workId, apih5FlowStatus: _d.apih5FlowStatus, fallInYear:fallInYear}
                                        },
                                        endFn: function (buttonClass) { obj.close() },
                                        callback: function (flowObj) {
                                            flowObj.flowSelectOpen(0)
                                        }
                                    })
                                }
                            })
                            layer.close(index);
                        });
                        //流程发起特殊代码---结束
                        break;
                    case "cancel":
                    default:
                        obj.close()
                        break;
                }
            }
        }
    } else {
        customBtnGroup = {
            btns: [],
            callback: function (dataName, obj) {
            }
        }
    }
    //新增tab===3的代码 start
    //tab类型等于3的处理
    if (tabItem.type === "3") {
        //插入表格需要的dom元素
        var $tableDom = $('<table id="table1" class="table table-border table-bordered table-bg table-hover"></table>');
        var $tcontainer = $('<div class="tabCon" id="tab' + i.toString() + '"></div>');
        $tcontainer.append($tableDom);
        tabCons[i] = $tcontainer;
        // $tabCon.append($tcontainer)
        //注意，这里需要小小的异步一下
        setTimeout(function () {
            table = $("#table1").DataTable((tabItem.conditions()));

            pager1 = $("#pager").page({
                remote: {
                    //ajax请求配置
                    url: l.getApiUrl("getZjScoreScoringItemList"),
                    params: {},
                    success: function (result) {
                        if (result.success) {
                            var data = result.data;
                            $.each(data, function (index, item) {
                                item.rowIndex = index;
                            });
                            table1
                                .clear()
                                .rows.add(data)
                                .draw();
                        } else {
                            layer.alert(
                                result.message,
                                {
                                    icon: 0
                                },
                                function (index) {
                                    layer.close(index);
                                }
                            );
                        }
                    }
                }
            });
        }, 10)
    } else {
        tabCons[i] = $tabCon.detailLayer({ customBtnGroup: customBtnGroup, conditions: tabItem.conditions })
        tabCons[i].setOpenData();
        tabCons[i].close = function () {
            parent.pager.page('remote')
            parent.layer.close(parent.myOpenLayer)		
        }
    }
    //新增tab===3的代码 over
})
$tabSystem.append($tabBar).append(tabCons).Huitab({
    index: 0
});