var nodeId = l.getUrlParam("nodeId") || "";
var flowId = l.getUrlParam("flowId") || "";
var workId = l.getUrlParam("workId") || "";
var trackId = l.getUrlParam("trackId") || "";
var title = l.getUrlParam("title") || "";
var status = l.getUrlParam("status") || "";
var workFormJson = {
    //流程模版json（升级版：根据流程工作id动态获取，当前前台写死）
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
                    type: "hidden",//
                    name: "workId",//
                    label: "流程id",//
                    placeholder: ""
                },
                {
                    type: "hidden",//
                    name: "apih5FlowStatus",//
                    label: "流程状态",//
                    placeholder: ""
                },
                {
                    type: "text",//
                    name: "name",//
                    label: "姓名",//
                    placeholder: "请输入",
                    must: true,
                    immutableAdd: true
                },
                {
                    type: "text",//
                    name: "dept",//
                    label: "部门",//
                    placeholder: "请输入",
                    must: true,
                    immutableAdd: true
                },
                {
                    type: "text",//
                    name: "duty",//
                    label: "职别",//
                    placeholder: "请输入",
                    must: true,
                    immutableAdd: true
                },
                {
                    type: "date",//
                    name: "planStart",//
                    label: "计划开始时间",//
                    placeholder: "请选择",
                    id: "planStart",
                    must: true,
                    dateFmt: "yyyy/MM/dd",
                    maxDate: "\'#F{$dp.$D(\\\'planEnd\\\')}\'",
                    immutableAdd: true
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
                    must: true,
                    immutableAdd: true
                },
                {
                    type: "date",//
                    name: "planEnd",//
                    label: "计划结束时间",//
                    placeholder: "请选择",
                    id: "planEnd",
                    must: true,
                    dateFmt: "yyyy/MM/dd",
                    minDate: "\'#F{$dp.$D(\\\'planStart\\\')}\'",
                    immutableAdd: true
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
                    must: true,
                    immutableAdd: true
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
                    must: true,
                    immutableAdd: true
                },
                {
                    type: "text",//
                    name: "vehicle",//
                    label: "交通工具",//
                    placeholder: "请输入",
                    must: true,
                    immutableAdd: true
                },
                {
                    type: "textarea",//
                    name: "reason",//
                    label: "出差事由",//
                    placeholder: "请输入",
                    must: true,
                    immutableAdd: true
                },
                {
                    type: "textarea",//
                    name: "byAirplane",//
                    label: "乘坐飞机原因",//
                    placeholder: "请输入",
                    immutableAdd: true
                },
                {
                    type: "textarea",//
                    name: "completeStatus",//
                    label: "主要任务完成情况",//
                    placeholder: "请输入",
                    // must: true,
                    immutableAdd: true
                },
                {
                    type: "date",//
                    name: "backDate",//
                    label: "销假日期",//
                    placeholder: "请选择",
                    id: "backDate",
                    // must: true,
                    dateFmt: "yyyy/MM/dd",
                    // minDate: "\'#F{\\\'%y-%M-%d\\\'}\'",
                    immutableAdd: true
                },
                {
                    type: "select",
                    name: "backDateType",
                    label: "销假时间段",
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
                    // must: true,
                    immutableAdd: true
                },
                {
                    type: "text",//
                    name: "actualDays",//
                    label: "实际出差天数",//
                    placeholder: "请输入",
                    // must: true,
                    immutableAdd: true
                },
                {
                    type: "number",//
                    name: "subsidyAmount",//
                    label: "补助金额",//
                    placeholder: "请输入",
                    // must: true,
                    immutableAdd: true
                },
                {
                    type: "textarea",//
                    name: "note",//
                    label: "情况说明",//
                    placeholder: "请输入",
                    // must: true,
                    immutableAdd: true
                },
                {
                    type: "textarea",//
                    name: "opinionField1",//
                    label: "部门领导意见",//
                    placeholder: "请输入",
                    immutable: true,
                },
                {
                    type: "textarea",//
                    name: "opinionField2",//
                    label: "分管领导意见",//
                    placeholder: "请输入",
                    immutable: true,
                },
                {
                    type: "textarea",//
                    name: "opinionField3",//
                    label: "公司主要领导意见",//
                    placeholder: "请输入",
                    immutable: true,
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
                    immutableAdd: true,
                    uploadUrl: 'uploadFilesByFileName',
                    fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
                }
            ]
        },
        {
            name: "操作历史",
            type: "5"
        }
    ]
};

//add by lny on 717
var _body = {
    title: title,
    nodeId: nodeId,
    trackId: trackId,
    workId: workId,
    flowId: flowId,
    apiName: "getZjFlowStElectionDetail",
    apiBody: { workId: workId }
};

l.ajax("openFlow", _body, function (_d, _m, _s, _r) {
    if (_s) {
        loadFlow(_d.flowButtons, {
            otherBody: {
                nodeId: _d.flowNode.nodeId,
                trackId: _d.flowNode.trackId,
                workId: _d.workId,
                flowId: _d.flowId,
                flowVars: _d.flowVars,
                nodeVars: _d.nodeVars,
                apiName: "updateZjFlowStElection"
            },
            submitFn: function () {
                parent.pager.page("remote");
                parent.layer.close(parent.myOpenLayer);
            },
            callback: function (flowObj) {
                var $tabSystem = $("#tab-system"); //模版顶级jq对象
                var $tabBar = $('<div class="tabBar cl"></div>'); //tab按钮控制条
                var tabCons = []; //tab内容页面组
                var mainTableDataObject = _d.mainTableDataObject; //主表数据对象
                var subTableObject = _d.subTableObject; //子表数据对象数组
                var flowWebUrl = _d.flowWebUrl || ""; //子表数据对象数组
                $.each(workFormJson.tabs, function (i, tabItem) {
                    //第一次遍历workFormJson.tabs
                    var $tabBtn = $("<span>" + tabItem.name + "</span>"); //创建tab按钮$对象
                    $tabBar.append($tabBtn); //向tab按钮控制条插入tab按钮
                    var $tabCon = $(
                        '<div class="tabCon" id="tab' +
                        i.toString() +
                        '"></div>'
                    ); //创建tab内容页面$对象

                    //操作历史start
                    if (tabItem.type === "5") {
                        var flowHistoryList = _d.flowHistoryList;
                        if (flowHistoryList && flowHistoryList.length) {
                            var $timeLineContainer = $(
                                '<div style="border-left:2px solid #e8e8e8;margin-top:20px;padding:20px 0px;"></div>'
                            );
                            for (var j = 0; j < flowHistoryList.length; j++) {
                                var item = flowHistoryList[j];
                                var $histItem = $(
                                    '<div style="position:relative;padding-left:20px;margin-bottom:20px;font-size:14px;color:#666;"></div>'
                                );
                                if (item.historyFlag == "2") {
                                    $histItem.css({
                                        color: "#000"
                                    });
                                }
                                var $histItemArr = $(
                                    '<div style="background:white;color:#1890ff;font-weight:800;height:15px;line-height:12px;font-size:25px;;left:-9px;top:0px;bottom:0px;margin:auto;position:absolute;">○</div>'
                                );
                                var $nodeName = $(
                                    "<div>节点名称：" +
                                    item["nodeName"] +
                                    "</div>"
                                ); //节点名称
                                var $nodePer = $(
                                    "<div>审核者：" +
                                    item["realName"] +
                                    "</div>"
                                ); //审核者

                                var $time = $(
                                    "<div>时间：" +
                                    (item["actionTime"]
                                        ? l.dateFormat(
                                            new Date(item["actionTime"]),
                                            "yyyy-MM-dd HH:mm:ss"
                                        )
                                        : "--") +
                                    "</div>"
                                ); //意见
                                $histItem.append($histItemArr); //空格圆
                                $histItem.append($nodeName);
                                $histItem.append($nodePer);

                                $histItem.append($time);
                                $timeLineContainer.append($histItem);
                            }
                            tabCons[i] = $tabCon.append($timeLineContainer);
                        } else {
                            tabCons[i] = $tabCon.append(
                                '<div style="color:#777">暂无操作历史</div>'
                            );
                        }
                    }
                    //操作历史end
                    else if (tabItem.type === "3") {
                        //列表tab
                    } else {
                        var customBtnGroup; //tab内容页面中表单的底部按钮组配置
                        if (tabItem.isMain) {
                            //如果是主表单
                            var btns = flowObj.btns;
                            btns.push({
                                name: "cancel",
                                label: "取消"
                            });
                            if (_d.nodeVars && _d.nodeVars.formEditFlag === '1') {
                                btns.push({
                                    name: "export",
                                    label: "导出"
                                })
                            }
                            customBtnGroup = {
                                btns: btns,
                                callback: function (dataName, obj) {
                                    switch (dataName) {
                                        case "export"://导出
                                            var editData = obj.getDatas().data;
                                            l.ajax("updateZjFlowStElection", editData, function (_d, _m, _s, _r) {
                                                if (_s) {//发起成功，返回workId
                                                    var params = {};
                                                    params.workId = workId;
                                                    window.location.href = 'http://weixin.fheb.cn:91/ureport/pdf?_u=file:zjFlowStElection.xml&url=' + l.domain + '&workId=' + params.workId;
                                                }
                                            })
                                            break;
                                        case "cancel":
                                            obj.close();
                                            break;
                                        default:
                                            var body = {};
                                            for (
                                                var j = 0;
                                                j < workFormJson.tabs.length;
                                                j++
                                            ) {
                                                //第二次遍历workFormJson.tabs，为了让return起作用所以不用$.each而采用for循环
                                                var tabItemj =
                                                    workFormJson.tabs[j]; //模版中tabs数组的遍历元素数据对象
                                                if (tabItemj.type === "4" || tabItemj.type === "5") {
                                                    //流程图tab
                                                } else if (
                                                    tabItemj.type === "3"
                                                ) {
                                                    //列表tab
                                                } else {
                                                    var tabObjDatas = tabCons[
                                                        j
                                                    ].getDatas(); //tab内容页面组的遍历对象获取数据对象
                                                    if (
                                                        tabObjDatas.err.length
                                                    ) {
                                                        //判断是否有错误（字段不能为空、超过个数限制等）
                                                        layer.alert(
                                                            tabObjDatas.err.join(
                                                                "<br/>"
                                                            ),
                                                            { icon: 0 },
                                                            function (index) {
                                                                $tabSystem.Huitab(
                                                                    {
                                                                        index: j
                                                                    }
                                                                );
                                                                layer.close(
                                                                    index
                                                                );
                                                            }
                                                        );
                                                        return; //直接停止for循环，且for循环之后的代码也不执行
                                                    }
                                                    if (tabItemj.isMain) {
                                                        //如果是主表
                                                        //给主表赋值
                                                        body["mainTableName"] =
                                                            tabItemj.tbName;
                                                        body[
                                                            "mainTablePrimaryIdName"
                                                        ] = tabItemj.tbIdName;
                                                        body[
                                                            "mainTableDataObject"
                                                        ] = tabObjDatas.data;

                                                        //add by lny on 717
                                                        body["apiBody"] = {};
                                                        for (var key in tabObjDatas.data) {
                                                            body["apiBody"][
                                                                key
                                                            ] =
                                                                tabObjDatas.data[
                                                                key
                                                                ];
                                                        }
                                                        //add by lny on 717

                                                        body["title"] = title;
                                                        body["opinionContent"] =
                                                            tabObjDatas.data[
                                                            "opinionContent"
                                                            ];
                                                    } else if (
                                                        tabItemj.type === "2"
                                                    ) {
                                                        //如果是附件类型子表，type==="2"
                                                        //给子表赋值-附件表
                                                        if (
                                                            !body[
                                                            "subTableObject"
                                                            ]
                                                        ) {
                                                            body[
                                                                "subTableObject"
                                                            ] = {};
                                                        }
                                                        for (var key in tabObjDatas.data) {
                                                            var subTableDataObject =
                                                                tabObjDatas
                                                                    .data[key];

                                                            //add by lny on 717
                                                            body["apiBody"][
                                                                key
                                                            ] =
                                                                tabObjDatas.data[
                                                                key
                                                                ];
                                                            //add by lny on 717

                                                            body[
                                                                "subTableObject"
                                                            ][key] = {
                                                                subTablePrimaryIdName:
                                                                    tabItemj.tbIdName,
                                                                subTableType:
                                                                    tabItemj.type,
                                                                subTableDataObject: subTableDataObject
                                                            };
                                                        }
                                                    } else {
                                                        //如果是普通类型子表，type==="1"，目前只有1和2
                                                        //给子表赋值-普通表
                                                        if (
                                                            !body[
                                                            "subTableObject"
                                                            ]
                                                        ) {
                                                            body[
                                                                "subTableObject"
                                                            ] = {};
                                                        }

                                                        //add by lny on 717
                                                        for (var key in tabObjDatas.data) {
                                                            body["apiBody"][
                                                                key
                                                            ] =
                                                                tabObjDatas.data[
                                                                key
                                                                ];
                                                        }
                                                        //add by lny on 717

                                                        body["subTableObject"][
                                                            tabItemj.tbName
                                                        ] = {
                                                            subTablePrimaryIdName:
                                                                tabItemj.tbIdName,
                                                            subTableType:
                                                                tabItemj.type,
                                                            subTableDataObject:
                                                                tabObjDatas.data
                                                        };
                                                    }
                                                }
                                            }
                                            //流程操作特殊代码---开始
                                            if (false) {
                                                layer.confirm(
                                                    "确定打印？",
                                                    { icon: 0 },
                                                    function (index) {
                                                        //流程发起请求
                                                        l.ajax(
                                                            buttonUrl,
                                                            body,
                                                            function (
                                                                data,
                                                                message,
                                                                success
                                                            ) {
                                                                if (success) {
                                                                    window.location.href = data;
                                                                    // window.open(data)
                                                                }
                                                            }
                                                        );
                                                        layer.close(index);
                                                    }
                                                );
                                            } else {
                                                flowObj.flowSelectOpen(
                                                    dataName,
                                                    body
                                                );
                                            }
                                        //流程操作特殊代码---结束
                                    }
                                }
                            };
                            var rowData = JSON.parse(_d.apiData);
                            if (_d.nodeVars && _d.nodeVars.formEditFlag === '1' && status !== 'Done') {
                                $.each(tabItem.conditions, function (i, itemName) {
                                    if (itemName.name === 'completeStatus' || itemName.name === 'backDate' || itemName.name === 'backDateType' || itemName.name === 'actualDays' || itemName.name === 'subsidyAmount' || itemName.name === 'note') {
                                        itemName.immutableAdd = false;
                                    }
                                })
                            } else if (!rowData.backDate) {
                                $.each(tabItem.conditions, function (i, itemName) {
                                    if (itemName.name === 'completeStatus' || itemName.name === 'backDate' || itemName.name === 'backDateType' || itemName.name === 'actualDays' || itemName.name === 'subsidyAmount' || itemName.name === 'note') {
                                        itemName.type = 'hidden';
                                    }
                                })
                            }
                            if (_d.nodeVars != null) {
                                //如果需要显示意见框
                                if (_d.nodeVars.opinionShowFlag === "1") {
                                    tabItem.conditions.push({
                                        type: "textarea", //
                                        name: "opinionContent", //
                                        label: "您的意见", //
                                        defaultValue: "同意",
                                        must: true,
                                        placeholder: "您的意见"
                                    });
                                }
                            }
                        } else {
                            customBtnGroup = {
                                btns: [],
                                callback: function (dataName, obj) { }
                            };
                        }
                        tabCons[i] = $tabCon.detailLayer({
                            customBtnGroup: customBtnGroup,
                            conditions: tabItem.conditions
                        });
                        //流程操作特殊代码（向各个表单中赋值）---开始

                        //add by lny on 717
                        var apiData = _d.apiData;
                        var apiName = _d.apiName;
                        if (apiName) {
                            tabCons[i].setOpenData(JSON.parse(apiData));
                        } else {
                            //add by lny on 717
                            if (tabItem.isMain) {
                                tabCons[i].setOpenData(mainTableDataObject);
                            } else if (tabItem.type === "2") {
                                var _subTableDataObject = {};
                                for (var key in subTableObject) {
                                    _subTableDataObject[key] =
                                        subTableObject[key].subTableDataObject;
                                }
                                tabCons[i].setOpenData(_subTableDataObject);
                            } else {
                                tabCons[i].setOpenData(
                                    subTableObject[tabItem.tbName]
                                        .subTableDataObject
                                );
                            }
                        }
                        //流程操作特殊代码（向各个表单中赋值）---结束
                        tabCons[i].close = function () {
                            parent.layer.close(parent.myOpenLayer);
                        };
                    }
                });
                $tabSystem
                    .append($tabBar)
                    .append(tabCons)
                    .Huitab({
                        index: 0
                    });
            }
        });
        $('input[name=actualDays]').on('change', function () {
            if ($('input[name=planDays]').val() !== $('input[name=actualDays]').val()) {
                $('textarea[name=note]').val('');
            } else {
                $('textarea[name=note]').val('无');
            }
        })
        // $('#backDate').on('blur', function () {
        //     if ($(this).val() && $('select[name=planStartType]').val() && $('#planStart').val() && $('select[name=backDateType]').val()) {
        //         var day1 = new Date($('#planStart').val());
        //         var day2 = new Date($(this).val());
        //         if ($('select[name=planStartType]').val() === $('select[name=backDateType]').val()) {
        //             var day3 = (day2 - day1) / (1000 * 60 * 60 * 24);
        //             $('input[name=actualDays]').val(day3.toString());
        //             if (day3 <= 15) {
        //                 $('input[name=subsidyAmount]').val(day3 * 80);
        //             } else if (day3 > 15 && day3 <= 30) {
        //                 $('input[name=subsidyAmount]').val(day3 * 50);
        //             } else if (day3 > 30) {
        //                 $('input[name=subsidyAmount]').val(day3 * 30);
        //             }
        //         } else if ($('select[name=planStartType]').val() === '0' && $('select[name=backDateType]').val() === '1') {
        //             var day3 = (day2 - day1) / (1000 * 60 * 60 * 24) + 1 - 0.5;
        //             $('input[name=actualDays]').val(day3.toString());
        //             if (day3 <= 15) {
        //                 $('input[name=subsidyAmount]').val(day3 * 80);
        //             } else if (day3 > 15 && day3 <= 30) {
        //                 $('input[name=subsidyAmount]').val(day3 * 50);
        //             } else if (day3 > 30) {
        //                 $('input[name=subsidyAmount]').val(day3 * 30);
        //             }
        //         } else if ($('select[name=planStartType]').val() === '1' && $('select[name=backDateType]').val() === '0') {
        //             if (l.dateFormat(new Date($(this).val()), "yyyy-MM-dd") === l.dateFormat(new Date($('#backDate').val()), "yyyy-MM-dd")) {
        //                 var day3 = -1;
        //                 $('input[name=actualDays]').val(day3.toString());
        //                 if (day3 <= 15) {
        //                     $('input[name=subsidyAmount]').val(day3 * 80);
        //                 } else if (day3 > 15 && day3 <= 30) {
        //                     $('input[name=subsidyAmount]').val(day3 * 50);
        //                 } else if (day3 > 30) {
        //                     $('input[name=subsidyAmount]').val(day3 * 30);
        //                 }
        //             } else {
        //                 var day3 = (day2 - day1) / (1000 * 60 * 60 * 24) - 0.5;
        //                 $('input[name=actualDays]').val(day3.toString());
        //                 if (day3 <= 15) {
        //                     $('input[name=subsidyAmount]').val(day3 * 80);
        //                 } else if (day3 > 15 && day3 <= 30) {
        //                     $('input[name=subsidyAmount]').val(day3 * 50);
        //                 } else if (day3 > 30) {
        //                     $('input[name=subsidyAmount]').val(day3 * 30);
        //                 }
        //             }
        //         }
        //         if ($('input[name=planDays]').val() !== $('input[name=actualDays]').val()) {
        //             $('textarea[name=note]').val('');
        //         } else {
        //             $('textarea[name=note]').val('无');
        //         }
        //     }
        // })
        // $('select[name=backDateType]').on('change', function () {
        //     if ($('#planStart').val() && $('select[name=planStartType]').val() && $('#backDate').val() && $('select[name=backDateType]').val()) {
        //         var day1 = new Date($('#planStart').val());
        //         var day2 = new Date($('#backDate').val());
        //         if ($('select[name=planStartType]').val() === $('select[name=backDateType]').val()) {
        //             var day3 = (day2 - day1) / (1000 * 60 * 60 * 24);
        //             $('input[name=actualDays]').val(day3.toString());
        //             if (day3 <= 15) {
        //                 $('input[name=subsidyAmount]').val(day3 * 80);
        //             } else if (day3 > 15 && day3 <= 30) {
        //                 $('input[name=subsidyAmount]').val(day3 * 50);
        //             } else if (day3 > 30) {
        //                 $('input[name=subsidyAmount]').val(day3 * 30);
        //             }
        //         } else if ($('select[name=planStartType]').val() === '0' && $('select[name=backDateType]').val() === '1') {
        //             var day3 = (day2 - day1) / (1000 * 60 * 60 * 24) + 1 - 0.5;
        //             $('input[name=actualDays]').val(day3.toString());
        //             if (day3 <= 15) {
        //                 $('input[name=subsidyAmount]').val(day3 * 80);
        //             } else if (day3 > 15 && day3 <= 30) {
        //                 $('input[name=subsidyAmount]').val(day3 * 50);
        //             } else if (day3 > 30) {
        //                 $('input[name=subsidyAmount]').val(day3 * 30);
        //             }
        //         } else if ($('select[name=planStartType]').val() === '1' && $('select[name=backDateType]').val() === '0') {
        //             if (l.dateFormat(new Date($('#planStart').val()), "yyyy-MM-dd") === l.dateFormat(new Date($('#backDate').val()), "yyyy-MM-dd")) {
        //                 var day3 = -1;
        //                 $('input[name=actualDays]').val(day3.toString());
        //                 if (day3 <= 15) {
        //                     $('input[name=subsidyAmount]').val(day3 * 80);
        //                 } else if (day3 > 15 && day3 <= 30) {
        //                     $('input[name=subsidyAmount]').val(day3 * 50);
        //                 } else if (day3 > 30) {
        //                     $('input[name=subsidyAmount]').val(day3 * 30);
        //                 }
        //             } else {
        //                 var day3 = (day2 - day1) / (1000 * 60 * 60 * 24) - 0.5;
        //                 $('input[name=actualDays]').val(day3.toString());
        //                 if (day3 <= 15) {
        //                     $('input[name=subsidyAmount]').val(day3 * 80);
        //                 } else if (day3 > 15 && day3 <= 30) {
        //                     $('input[name=subsidyAmount]').val(day3 * 50);
        //                 } else if (day3 > 30) {
        //                     $('input[name=subsidyAmount]').val(day3 * 30);
        //                 }
        //             }
        //         }
        //         if ($('input[name=planDays]').val() !== $('input[name=actualDays]').val()) {
        //             $('textarea[name=note]').val('');
        //         } else {
        //             $('textarea[name=note]').val('无');
        //         }
        //     }
        // })
    }
});
