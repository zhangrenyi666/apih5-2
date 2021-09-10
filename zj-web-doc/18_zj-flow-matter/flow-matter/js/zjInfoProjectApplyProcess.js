var nodeId = l.getUrlParam('nodeId') || "Node1";
var flowId = l.getUrlParam("flowId") || "";
var workId = l.getUrlParam('workId') || "HZ2881f76458a99c016458a99ee70001";
var trackId = l.getUrlParam('trackId') || "HZ2881f76458a99c016458a99efe0002";
var title = l.getUrlParam("title") || "";
var code = l.getUrlParam('code');
Lny.setCookie('code', code, 30);

var workFormJson = {//流程模版json（升级版：根据流程工作id动态获取，当前前台写死）
    name: "信息化立项申请",
    titleName: "projectTitle",
    tabs: [
        {
            name: "基本信息",
            type: "1",
            isMain: "1",
            tbName: "zjInfoProjectApply",
            tbIdName: "projectApplyId",
            conditions: [
                {
                    type: "hidden",//五种形式：text,select,date,hidden,textarea,
                    name: "projectApplyId",//输入字段名
                },
                {
                    type: "select",
                    name: "documentType",
                    label: "公文类型",
                    selectList: [//当类型为select时，option配置   0：公章、1:手签章、2:人名章
                        {
                            value: "0",
                            text: "请示批复"
                        },
                        {
                            value: "1",
                            text: "通知通报"
                        },
                        {
                            value: "2",
                            text: "报告函"
                        }
                    ],
                    immutableAdd: true
                },
                {
                    type: "text",//
                    name: "projectTitle",//
                    label: "标题",//
                    placeholder: "",
                    immutable: true,
                },
                {
                    type: "textarea",//
                    name: "projectcontentPoints",//
                    label: "内容要点",//
                    placeholder: "",
                    immutable: true,
                },
                {
                    type: "textarea",//
                    name: "applyDepartmentOpinion",//
                    label: "部门负责人意见",//
                    placeholder: "",
                    immutable: true,
                },
                {
                    type: "textarea",//
                    name: "infoManageLeaderOpinion",//
                    label: "信息化管理部门意见",//
                    placeholder: "",
                    immutable: true,
                },
                {
                    type: "textarea",//
                    name: "signDepartmentOpinion",//
                    label: "各部门会签意见",//
                    placeholder: "",
                    immutable: true,
                },
				{
                    type: "textarea",//
                    name: "departmentLeaderOpinion",//
                    label: "部门领导审核意见",//
                    placeholder: "",
                    immutable: true,
                },
                {
                    type: "textarea",//
                    name: "chargeOmpanyLeaderOpinion",//
                    label: "分管领导意见",//
                    placeholder: "",
                    immutable: true,
                },
                {
                    type: "textarea",//
                    name: "mainLeaderOpinion",//
                    label: "主要领导意见",//
                    placeholder: "",
                    immutable: true,
                }
            ]
        },
        {
            name: "附件信息",
            type: "2",
            tbName: "",
            tbIdName: "fileId",
            conditions: [
                {
                    type: "upload",//
                    name: "zjInfoProjectApplyFile",//
                    label: "附件1",//
                    btnName: "添加附件",
                    projectName: "zjInfoProjectApply",
                    fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls"]
                }
            ]
        },
        {
            name: "操作历史",
            type: "5"
        }
    ]
}



//add by lny on 717
var _body = {
    title: title,
    nodeId: nodeId,
    trackId: trackId,
    workId: workId,
    flowId: flowId,
    apiName: "getFlowProjectDetailByWorkId",
    apiBody: { workId: workId }
}



l.ajax("openFlow", _body, function (_d, _m, _s, _r) {
    if (_s) {
        loadFlow(_d.flowButtons, {
            otherBody: {
                nodeId: _d.flowNode.nodeId,
                trackId: _d.flowNode.trackId,
                workId: _d.workId,
                flowVars: _d.flowVars,
                nodeVars: _d.nodeVars,
                apiName: "updateFlowProjectAfterSubmit"
            },
            submitFn: function () {
                parent.pager.page('remote')
                parent.layer.close(parent.myOpenLayer)
            },
            callback: function (flowObj) {
                var $tabSystem = $("#tab-system")//模版顶级jq对象
                var $tabBar = $('<div class="tabBar cl"></div>');//tab按钮控制条
                var tabCons = []//tab内容页面组
                var mainTableDataObject = _d.mainTableDataObject//主表数据对象
                var subTableObject = _d.subTableObject//子表数据对象数组
                var flowWebUrl = _d.flowWebUrl || ""//子表数据对象数组
                $.each(workFormJson.tabs, function (i, tabItem) {//第一次遍历workFormJson.tabs
                    var $tabBtn = $('<span>' + tabItem.name + '</span>')//创建tab按钮$对象
                    $tabBar.append($tabBtn)//向tab按钮控制条插入tab按钮
                    var $tabCon = $('<div class="tabCon" id="tab' + i.toString() + '"></div>')//创建tab内容页面$对象                    
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
                                    })
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
                        var customBtnGroup;//tab内容页面中表单的底部按钮组配置
                        if (tabItem.isMain) {//如果是主表单
                            var btns = flowObj.btns
                            btns.push({
                                name: "cancel",
                                label: "取消"
                            })
                            customBtnGroup = {
                                btns: btns,
                                callback: function (dataName, obj) {
                                    switch (dataName) {
                                        case "cancel":
                                            obj.close()
                                            break
                                        default:
                                            var body = {}
                                            for (var j = 0; j < workFormJson.tabs.length; j++) {//第二次遍历workFormJson.tabs，为了让return起作用所以不用$.each而采用for循环
                                                var tabItemj = workFormJson.tabs[j]//模版中tabs数组的遍历元素数据对象
                                                if (tabItemj.type === "4" || tabItemj.type === "5") {
                                                    //流程图tab
                                                } else if (tabItemj.type === "3") {
                                                    //列表tab
                                                } else {
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
                                                        body["mainTableName"] = tabItemj.tbName
                                                        body["mainTablePrimaryIdName"] = tabItemj.tbIdName
                                                        body["mainTableDataObject"] = tabObjDatas.data;


                                                        //add by lny on 717
                                                        body["apiBody"] = {}
                                                        for (var key in tabObjDatas.data) {
                                                            body["apiBody"][key] = tabObjDatas.data[key]
                                                        }
                                                        //add by lny on 717


                                                        body["title"] = title;
                                                        // body["title"] = tabObjDatas.data[workFormJson.titleName];
                                                        body["opinionContent"] = tabObjDatas.data['opinionContent'];
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
                                                }
                                            }
                                            //流程操作特殊代码---开始
                                            if (false) {
                                                layer.confirm("确定打印？", { icon: 0, }, function (index) {//流程发起请求
                                                    l.ajax(buttonUrl, body, function (data, message, success) {
                                                        if (success) {
                                                            window.location.href = data
                                                            // window.open(data)
                                                        }
                                                    })
                                                    layer.close(index);
                                                });
                                            } else {
                                                flowObj.flowSelectOpen(dataName, body)
                                            }
                                        //流程操作特殊代码---结束
                                    }
                                }
                            }


                            if (_d.nodeVars != null) {
                                //如果需要显示意见框 
                                if (_d.nodeVars.opinionShowFlag === '1') {
                                    tabItem.conditions.push({
                                        type: "textarea",//
                                        name: "opinionContent",//
                                        label: "您的意见",//
                                        defaultValue: "同意",
                                        must: true,
                                        placeholder: "您的意见",
                                    })
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
                        //流程操作特殊代码（向各个表单中赋值）---开始


                        //add by lny on 717
                        var apiData = _d.apiData
                        var apiName = _d.apiName
                        if (apiName) {
                            tabCons[i].setOpenData(JSON.parse(apiData))
                        } else {
                            //add by lny on 717
                            if (tabItem.isMain) {
                                tabCons[i].setOpenData(mainTableDataObject)
                            } else if (tabItem.type === "2") {
                                var _subTableDataObject = {}
                                for (var key in subTableObject) {
                                    _subTableDataObject[key] = subTableObject[key].subTableDataObject
                                }
                                tabCons[i].setOpenData(_subTableDataObject)
                            } else {
                                tabCons[i].setOpenData(subTableObject[tabItem.tbName].subTableDataObject)
                            }
                        }
                        //流程操作特殊代码（向各个表单中赋值）---结束
                        tabCons[i].close = function () {
                            parent.layer.close(parent.myOpenLayer)
                        }
                    }
                })
                $tabSystem.append($tabBar).append(tabCons).Huitab({
                    index: 0
                });

            }
        })
    }
})
