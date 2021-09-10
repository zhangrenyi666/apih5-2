var flowId = l.getUrlParam('id') || "stCcPurchase";//流程模版id
var code = l.getUrlParam('code');
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
$tabTitle.html("出差申请")

switch (flowId) {
    case "stCcPurchase":
        flowFormJson = {//流程模版json（升级版：根据流程id动态获取，当前前台写死）
            name: "出差申请",
            titleName: "name",
            tabs: [
                {
                    name: "基本信息",
                    type: "1",//普通tab页1，附件tab页2，列表tab页面3
                    isMain: "1",//是否为主表
                    tbName: "StElection",//表名
                    tbIdName: "stElectionId",//表主键id
                    conditions: [
                        {
                            type: "hidden",//
                            name: "stElectionId",//
                            label: "主键id",//
                            placeholder: ""
                        },
                        {
                            type: "text",//
                            name: "name",//
                            label: "姓名",//
                            placeholder: "请输入",
                            must: true
                        },
                        {
                            type: "text",//
                            name: "dept",//
                            label: "部门",//
                            placeholder: "请输入",
                            must: true
                        },
                        {
                            type: "text",//
                            name: "duty",//
                            label: "职别",//
                            placeholder: "请输入",
                            must: true
                        },
                        {
                            type: "date",//
                            name: "planStart",//
                            label: "计划开始时间",//
                            placeholder: "请选择",
                            id: "planStart",
                            must: true,
                            dateFmt: "yyyy/MM/dd",
                            maxDate: "\'#F{$dp.$D(\\\'planEnd\\\')}\'"
                        },
                        {
                            type: "select",
                            name: "planStartType",
                            label: "计划开始段",
                            selectList: [//当类型为select时，option配置   0：公章、1:手签章、2:人名章
                                {
                                    value: "0",
                                    text: "上午"
                                },
                                {
                                    value: "1",
                                    text: "下午"
                                }
                            ],
                            must: true
                        },
                        {
                            type: "date",//
                            name: "planEnd",//
                            label: "计划结束时间",//
                            placeholder: "请选择",
                            id: "planEnd",
                            must: true,
                            dateFmt: "yyyy/MM/dd",
                            minDate: "\'#F{$dp.$D(\\\'planStart\\\')}\'"
                        },
                        {
                            type: "select",
                            name: "planEndType",
                            label: "计划结束段",
                            selectList: [//当类型为select时，option配置   0：公章、1:手签章、2:人名章
                                {
                                    value: "0",
                                    text: "上午"
                                },
                                {
                                    value: "1",
                                    text: "下午"
                                }
                            ],
                            must: true
                        },
                        {
                            type: "text",//
                            name: "planDays",//
                            label: "计划出差天数",//
                            placeholder: "请输入",
                            immutableAdd: true
                        },
                        {
                            type: "text",//
                            name: "businessPlace",//
                            label: "出差地点",//
                            placeholder: "请输入",
                            must: true
                        },
                        {
                            type: "text",//
                            name: "vehicle",//
                            label: "交通工具",//
                            placeholder: "请输入",
                            must: true
                        },
                        {
                            type: "textarea",//
                            name: "reason",//
                            label: "出差事由",//
                            placeholder: "请输入",
                            must: true
                        },
                        {
                            type: "textarea",//
                            name: "byAirplane",//
                            label: "乘坐飞机原因",//
                            placeholder: "请输入"
                        },
                        {
                            type: "textarea",//
                            name: "zhushi",//
                            label: "补助标准",//
                            defaultValue: "15天内(80元/天);\n 16~30天(50元/天);\n 31天以上(30元/天);",
                            immutableAdd: true
                        }
                    ]
                },
                {
                    name: "附件信息",
                    type: "2",
                    tbName: "",
                    tbIdName: "fileId",//附件表主键id名
                    conditions: [
                        {
                            type: "upload",//
                            name: "fileOldList",//附件表名
                            label: "附件1",//
                            btnName: "添加附件",
                            projectName: "zjSeal",
                            // immutableAdd: true,
                            uploadUrl: 'uploadFilesByFileName',
                            fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
                        }
                    ]
                }
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
                                body["title"] = tabObjDatas.data[flowFormJson.titleName] + '  ' + formatDate + '  出差申请';
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
                            } else {//如果是普通类型子表，type==="1"，目前只有1和2
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
                            body["apiName"] = "addZjFlowStElection"//购置申请发起时调用
                            //add by lny on 717



                        }
                        //流程发起特殊代码---开始
                        layer.confirm("确定发起？", { icon: 0, }, function (index) {//流程发起请求
                            l.ajax("createOpenFlow", body, function (_d, _m, _s, _r) {
                                if (_s) {//发起成功，返回workId
                                    var otherBodys = {
                                        title: body["title"],
                                        flowId: flowId,//流程id
                                        nodeId: _d.flowNode.nodeId,
                                        trackId: _d.flowNode.trackId,
                                        workId: _d.workId,
                                        flowVars: _d.flowVars,
                                        nodeVars: _d.nodeVars,
                                        apih5FlowStatus: _d.apih5FlowStatus,
                                        apiName: "updateZjFlowStElection",
                                        apiBody: _d.apiBody
                                    };
                                    otherBodys.apiBody.workId = _d.workId;
                                    otherBodys.apiBody.apih5FlowStatus = _d.apih5FlowStatus;
                                    loadFlow(_d.flowButtons, {
                                        otherBody: otherBodys,
                                        endFn: function (buttonClass) { obj.close() },
                                        callback: function (flowObj) {
                                            flowObj.flowSelectOpen(0);
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
    tabCons[i] = $tabCon.detailLayer({ customBtnGroup: customBtnGroup, conditions: tabItem.conditions })
    tabCons[i].setOpenData()
    tabCons[i].close = function () {
        custom_close()
    }
})


$tabSystem.append($tabBar).append(tabCons).Huitab({
    index: 0
});


function custom_close() {
    if
        (confirm("您确定要关闭本页吗？")) {
        window.opener = null;
        window.open('', '_self');
        window.close();
    }
    else { }
}
$('#planStart').on('blur', function () {
    if ($(this).val() && $('select[name=planStartType]').val() && $('#planEnd').val() && $('select[name=planEndType]').val()) {
        var day1 = new Date($(this).val());
        var day2 = new Date($('#planEnd').val());
        if ($('select[name=planStartType]').val() === $('select[name=planEndType]').val()) {
            var day3 = (day2 - day1) / (1000 * 60 * 60 * 24) + 1 - 0.5;
            $('input[name=planDays]').val(day3.toString());
        } else if ($('select[name=planStartType]').val() === '0' && $('select[name=planEndType]').val() === '1') {
            var day3 = (day2 - day1) / (1000 * 60 * 60 * 24) + 1;
            $('input[name=planDays]').val(day3.toString());
        } else if ($('select[name=planStartType]').val() === '1' && $('select[name=planEndType]').val() === '0') {
            if (l.dateFormat(new Date($(this).val()), "yyyy-MM-dd") === l.dateFormat(new Date($('#planEnd').val()), "yyyy-MM-dd")) {
                $('input[name=planDays]').val(day3.toString('-0.5'));
            } else {
                var day3 = (day2 - day1) / (1000 * 60 * 60 * 24);
                $('input[name=planDays]').val(day3.toString());
            }
        }
    }
})
$('select[name=planStartType]').on('change', function () {
    if ($('#planStart').val() && $('select[name=planStartType]').val() && $('#planEnd').val() && $('select[name=planEndType]').val()) {
        var day1 = new Date($('#planStart').val());
        var day2 = new Date($('#planEnd').val());
        if ($('select[name=planStartType]').val() === $('select[name=planEndType]').val()) {
            var day3 = (day2 - day1) / (1000 * 60 * 60 * 24) + 1 - 0.5;
            $('input[name=planDays]').val(day3.toString());
        } else if ($('select[name=planStartType]').val() === '0' && $('select[name=planEndType]').val() === '1') {
            var day3 = (day2 - day1) / (1000 * 60 * 60 * 24) + 1;
            $('input[name=planDays]').val(day3.toString());
        } else if ($('select[name=planStartType]').val() === '1' && $('select[name=planEndType]').val() === '0') {
            if (l.dateFormat(new Date($(this).val()), "yyyy-MM-dd") === l.dateFormat(new Date($('#planEnd').val()), "yyyy-MM-dd")) {
                $('input[name=planDays]').val(day3.toString('-0.5'));
            } else {
                var day3 = (day2 - day1) / (1000 * 60 * 60 * 24);
                $('input[name=planDays]').val(day3.toString());
            }
        }
    }
})
$('#planEnd').on('blur', function () {
    if ($(this).val() && $('select[name=planStartType]').val() && $('#planStart').val() && $('select[name=planEndType]').val()) {
        var day1 = new Date($('#planStart').val());
        var day2 = new Date($(this).val());
        if ($('select[name=planStartType]').val() === $('select[name=planEndType]').val()) {
            var day3 = (day2 - day1) / (1000 * 60 * 60 * 24) + 1 - 0.5;
            $('input[name=planDays]').val(day3.toString());
        } else if ($('select[name=planStartType]').val() === '0' && $('select[name=planEndType]').val() === '1') {
            var day3 = (day2 - day1) / (1000 * 60 * 60 * 24) + 1;
            $('input[name=planDays]').val(day3.toString());
        } else if ($('select[name=planStartType]').val() === '1' && $('select[name=planEndType]').val() === '0') {
            if (l.dateFormat(new Date($(this).val()), "yyyy-MM-dd") === l.dateFormat(new Date($('#planEnd').val()), "yyyy-MM-dd")) {
                $('input[name=planDays]').val(day3.toString('-0.5'));
            } else {
                var day3 = (day2 - day1) / (1000 * 60 * 60 * 24);
                $('input[name=planDays]').val(day3.toString());
            }
        }
    }
})
$('select[name=planEndType]').on('change', function () {
    if ($('#planStart').val() && $('select[name=planStartType]').val() && $('#planEnd').val() && $('select[name=planEndType]').val()) {
        var day1 = new Date($('#planStart').val());
        var day2 = new Date($('#planEnd').val());
        if ($('select[name=planStartType]').val() === $('select[name=planEndType]').val()) {
            var day3 = (day2 - day1) / (1000 * 60 * 60 * 24) + 1 - 0.5;
            $('input[name=planDays]').val(day3.toString());
        } else if ($('select[name=planStartType]').val() === '0' && $('select[name=planEndType]').val() === '1') {
            var day3 = (day2 - day1) / (1000 * 60 * 60 * 24) + 1;
            $('input[name=planDays]').val(day3.toString());
        } else if ($('select[name=planStartType]').val() === '1' && $('select[name=planEndType]').val() === '0') {
            if (l.dateFormat(new Date($(this).val()), "yyyy-MM-dd") === l.dateFormat(new Date($('#planEnd').val()), "yyyy-MM-dd")) {
                $('input[name=planDays]').val(day3.toString('-0.5'));
            } else {
                var day3 = (day2 - day1) / (1000 * 60 * 60 * 24);
                $('input[name=planDays]').val(day3.toString());
            }
        }
    }
})