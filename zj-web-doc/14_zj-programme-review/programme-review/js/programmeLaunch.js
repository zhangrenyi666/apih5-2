var otherId = l.getUrlParam('id') || "";
var flowTemplateId = l.getUrlParam('id2') || "";
$("#tab-system").Huitab({
    index: 0
});
l.ajax("getFlowRunStart", { otherId: otherId, flowTemplateId: flowTemplateId }, function (data, message, success) {
    if (success) {
        var jsonContentTitle = data.jsonContentTitle
        var flowRunFileObjectList = data.flowRunFileObjectList || []
        var flowTemplateNoteList = data.flowTemplateNoteList || []
        //tab板块1：基本信息
        //表单1.1（非弹窗）：基本信息
        var jsonContentTitleObj = JSON.parse(jsonContentTitle)
        var jsonContent = JSON.parse(data.jsonContent)
        var conditions = []
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
        var detailForm = $('#detailForm').detailLayer({
            customBtnGroup: {
                btns: [
                ],
                callback: function (event, obj) { }
            },
            conditions: conditions
        })
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
                maxLen: parseInt(item.userNum),
                must: item.userEmptyFlag === "1",
                immutableAdd: index === 0 ? true : false,
            })
            flowJsonContent["flowTemplateNote" + index.toString()] = item.reviewUserObject
        }
        var flowForm = $('#flowForm').detailLayer({
            colOffset: 4,
            customBtnGroup: {
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
                            var flowDataObj = flowForm.getDatas()
                            var fileDataObj = fileForm.getDatas()
                            if (!flowDataObj.err.length) {
                                for (var index = 0; index < flowRunFileObjectList.length; index++) {
                                    flowRunFileObjectList[index].flowFileList = fileDataObj.data["flowFile" + index.toString()]
                                }
                                for (var index = 0; index < flowTemplateNoteList.length; index++) {
                                    flowTemplateNoteList[index].reviewUserObject = flowDataObj.data["flowTemplateNote" + index.toString()]
                                }
                                layer.confirm("确定发起？", { icon: 0, }, function (index) {
                                    var body = {
                                        otherId: otherId,
                                        jsonContentTitle: jsonContentTitle,
                                        jsonContent: JSON.stringify(jsonContent),
                                        flowRunFileObjectList: flowRunFileObjectList,
                                        flowTemplateId: flowTemplateId,
                                        flowTemplateNoteList: flowTemplateNoteList
                                    }
                                    l.ajax("addFlowRunStart", body, function (_d, _m, _s, _r) {
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
                                layer.alert(flowDataObj.err.join("<br/>"), { icon: 0, }, function (index) {
                                    layer.close(index);
                                });
                            }
                            break;
                        case "cancel":
                        default:
                            obj.close()
                            break;
                    }

                }
            },
            conditions: flowConditions
        })
        flowForm.close = function () {
            parent.layer.close(parent.myOpenLayer)
        }
        $("#flowTemplateImg").attr("src", flowTemplateImg);
        flowForm.setOpenData(flowJsonContent)
    }
})
