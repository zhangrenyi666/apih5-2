var otherId = l.getUrlParam('id') || "";
$("#tab-system").Huitab({
    index: 0
});
l.ajax("getFlowRunReviewDetail", { otherId: otherId }, function (data, message, success) {
    if (success) {
        var flowRunId = data.flowRunId//流程id
        var jsonContentTitle = data.jsonContentTitle//信息模版
        var flowRunFileObjectList = data.flowRunFileObjectList || []
        var flowTemplateNoteList = data.flowTemplateNoteList || []
        //tab板块1：基本信息
        var nextReviewUserFlag = data.nextReviewUserFlag//下一步审批人员判断：0可以选择1不可选择
        var nextReviewUserObject = data.nextReviewUserObject//下一步审批人员
        var userNum = data.userNum//下一步审批人员最大人数
        //表单1.2（非弹窗）：基本信息
        var jsonContentTitleObj = JSON.parse(jsonContentTitle)
        var jsonContent = JSON.parse(data.jsonContent)
        var conditions = []
        var btnShowFlag = data.btnShowFlag//操作按钮显示判断：0打印&关闭1审核操作2重新发起操作
        var btns = [];
        switch (btnShowFlag) {
            case "0":
                btns = [
                    {
                        name: "print",
                        label: '<i class="Hui-iconfont">&#xe652;</i> 打印',
                    },
                    {
                        name: "close",
                        label: "关闭"
                    }
                ]
                break;
            case "1":
                btns = [
                    {
                        name: "agree",
                        label: '<i class="Hui-iconfont">&#xe6e1;</i> 通过'
                    },
                    {
                        name: "reject",
                        label: '<i class="Hui-iconfont">&#xe6dd;</i> 不通过'
                    },
                    {
                        name: "print",
                        label: '<i class="Hui-iconfont">&#xe652;</i> 打印'
                    },
                    {
                        name: "close",
                        label: "关闭"
                    }
                ]
                break;
            case "2":
                btns = [
                    {
                        name: "relaunch",
                        label: '<i class="Hui-iconfont">&#xe603;</i> 重新发起'
                    },
                    {
                        name: "print",
                        label: '<i class="Hui-iconfont">&#xe652;</i> 打印'
                    },
                    {
                        name: "close",
                        label: "关闭"
                    }
                ]
                break;

            default:
                break;
        }
        for (var i = 0; i < jsonContentTitleObj.length; i++) {
            var item = jsonContentTitleObj[i];
            conditions.push({
                type: item.type,
                name: item.name,
                label: item.label,
                immutableAdd: item.immutableAdd,
                lineNum: item.lineNum
            })
        }
        if (btnShowFlag === "1") {
            conditions.push({
                type: "textarea",
                name: "opinionContent",
                label: "您的审批意见",
                placeholder: "请输入您的审批意见",
            })
        }
        var detailForm = $('#detailForm').detailLayer({
            customBtnGroup: {
                btns: btns,
                callback: function (dataName, obj) {
                    var detailDataObj = detailForm.getDatas()
                    var fileDataObj = fileForm.getDatas()
                    var opinionContent = detailDataObj.data.opinionContent
                    detailDataObj.data.opinionContent = ""
                    for (var index = 0; index < flowRunFileObjectList.length; index++) {
                        flowRunFileObjectList[index].flowFileList = fileDataObj.data["flowFile" + index.toString()]
                    }
                    switch (dataName) {
                        case "agree":
                            if (nextReviewUserFlag === "1") {
                                layer.confirm("通过该方案审批？", { icon: 0, }, function (index) {
                                    var body = {
                                        flowRunId: flowRunId,
                                        reviewUserState: "3",
                                        opinionContent: opinionContent,
                                        jsonContentTitle: jsonContentTitle,
                                        jsonContent: JSON.stringify(detailDataObj.data),
                                        flowRunFileObjectList: flowRunFileObjectList
                                    }
                                    l.ajax("addFlowRunReview", body, function (_d, _m, _s, _r) {
                                        if (_s) {
                                            layer.alert(_m, { icon: 0, }, function (index2) {
                                                parent.pager.page('remote')
                                                layer.close(index2);
                                                obj.close()
                                            });
                                        }
                                    })
                                    layer.close(index);
                                });
                            } else {
                                //表单1.1（弹窗）：下一级审批人员确认及其审批意见填写
                                var detailLayer = $('#detailLayer').detailLayer({
                                    conditions: [
                                        {
                                            type: "pickTree",
                                            name: "nextReviewUserObject",
                                            label: "下一步流程人员",
                                            pickType: {
                                                department: false,//部门列表对应接口字段名,false表示不开启该类
                                                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
                                            },
                                            maxLen: parseInt(userNum),
                                            must: true
                                        },
                                    ],
                                    onSave: function (obj, _params) {
                                        layer.confirm("通过该方案审批并向下一级发起申请？", { icon: 0, }, function (index) {
                                            var body = {
                                                flowRunId: flowRunId,
                                                reviewUserState: "3",
                                                opinionContent: opinionContent,                                          
                                                jsonContentTitle: jsonContentTitle,
                                                jsonContent: JSON.stringify(detailDataObj.data),
                                                nextReviewUserObject: _params.nextReviewUserObject,
                                                flowRunFileObjectList: flowRunFileObjectList
                                            }
                                            l.ajax("addFlowRunReview", body, function (_d, _m, _s, _r) {
                                                if (_s) {
                                                    layer.alert(_m, { icon: 0, }, function (index) {
                                                        parent.pager.page('remote')
                                                        layer.close(index);
                                                        obj.close()
                                                    });
                                                }
                                            })
                                            layer.close(index);
                                        });
                                    }
                                })
                                detailLayer.close = function () {
                                    parent.layer.close(parent.myOpenLayer)
                                }
                                detailLayer.open({
                                    nextReviewUserObject: nextReviewUserObject,
                                })
                            }
                            break;
                        case "reject":
                            layer.confirm("驳回该方案审批？", { icon: 0, }, function (index) {
                                var body = {
                                    flowRunId: flowRunId,
                                    reviewUserState: "4",
                                    opinionContent: opinionContent,
                                    jsonContentTitle: jsonContentTitle,
                                    jsonContent: JSON.stringify(detailDataObj.data),
                                    flowRunFileObjectList: flowRunFileObjectList
                                }
                                l.ajax("addFlowRunReview", body, function (_d, _m, _s, _r) {
                                    if (_s) {
                                        layer.alert(_m, { icon: 0, }, function (index2) {
                                            parent.pager.page('remote')
                                            layer.close(index2);
                                            obj.close()
                                        });
                                    }
                                })
                                layer.close(index);
                            });
                            break;
                        case "print":
                            l.ajax("exportWord", { otherId: otherId }, function (_d, _m, _s, _r) {
                                if (_s) {
                                    window.open(_d)
                                }
                            })
                            break;
                        case "relaunch":
                            layer.confirm("确定重新发起么？", { icon: 0, }, function (index) {
                                var body = {
                                    flowRunId: flowRunId,
                                    reviewUserState: "5",
                                    jsonContentTitle: jsonContentTitle,
                                    jsonContent: JSON.stringify(detailDataObj.data),
                                    flowRunFileObjectList: flowRunFileObjectList
                                }
                                l.ajax("addFlowRunReview", body, function (_d, _m, _s, _r) {
                                    if (_s) {
                                        layer.alert(_m, { icon: 0, }, function (index2) {
                                            parent.pager.page('remote')
                                            layer.close(index2);
                                            obj.close()
                                        });
                                    }
                                })
                                layer.close(index);
                            });
                            break;
                        case "close":
                        default:
                            obj.close()
                            break;
                    }
                }
            },
            conditions: conditions
        })
        detailForm.close = function () {
            parent.layer.close(parent.myOpenLayer)
        }
        detailForm.setOpenData(jsonContent)


        //tab板块2：附件信息
        //表单2.1（非弹窗）：附件信息
        var fileConditions = []
        var fileJsonContent = {}
        for (var index = 0; index < flowRunFileObjectList.length; index++) {
            var item = flowRunFileObjectList[index];
            fileConditions.push({
                type: "upload",
                name: "flowFile" + index.toString(),
                projectName: "zj-programme-review",
                label: item.flowRunFileName,
                immutableAdd: item.operationFlag === "1" ? false : true,
            })
            fileJsonContent["flowFile" + index.toString()] = item.flowFileList || []
        }
        var fileForm = $('#fileForm').detailLayer({
            customBtnGroup: {
                btns: [],
                callback: function (event, obj) { }
            },
            conditions: fileConditions
        })
        fileForm.setOpenData(fileJsonContent)


        //tab板块3：流程信息
        //表单3.1（非弹窗）：流程信息
        var flowTemplateImg = data.flowTemplateImg
        var flowJsonContent = {}
        var flowConditions = []
        for (var index = 0; index < flowTemplateNoteList.length; index++) {
            var item = flowTemplateNoteList[index];
            flowConditions.push({
                type: "pickTree",
                name: "flowTemplateNote" + index.toString(),
                label: item.noteName,
                labelCol: 4,
                conCol: 8,
                pickType: {
                    department: false,//部门列表对应接口字段名,false表示不开启该类
                    member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
                },
                immutableAdd: true,
            })
            flowJsonContent["flowTemplateNote" + index.toString()] = item.reviewUserObject
        }
        var flowForm = $('#flowForm').detailLayer({
            colOffset: 4,
            customBtnGroup: {
                btns: [],
                callback: function (event, obj) {
                }
            },
            conditions: flowConditions
        })
        $("#flowTemplateImg").attr("src", flowTemplateImg)
        flowForm.setOpenData(flowJsonContent)
    }
})
