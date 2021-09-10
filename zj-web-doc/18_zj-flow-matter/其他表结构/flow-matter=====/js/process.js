var workId = l.getUrlParam('id') || "";////流程工作id
var $tabSystem = $("#tab-system")//模版顶级jq对象
var $tabBar = $('<div class="tabBar cl"></div>');//tab按钮控制条
var tabCons = []//tab内容页面组
//根据workId获取流程工作模版数据及数据
l.ajax("getSubmitFlow", { workId: workId }, function (_d, _m, _s, _r) {
    if (_s) {
        var workFormJson = {//流程模版json（升级版：根据流程工作id动态获取，当前前台写死）
            name: "用印申请",
            titleName: "sealApplyName",
            tabs: [
                {
                    name: "基本信息",
                    type: "1",
                    isMain: "1",
                    tbName: "zjYySealApply",
                    tbIdName: "sealApplyId",
                    conditions: [
                        {
                            type: "hidden",//
                            name: "sealApplyId",//
                            label: "主键id",// 
                        },
                        {
                            type: "text",//
                            name: "sealApplyName",//
                            label: "用印申请名称",//
                            placeholder: "",
                            immutable: true,
                        },
                        {
                            type: "select",
                            name: "sealType",
                            label: "印别",
                            immutable: true,
                            selectList: [//当类型为select时，option配置   0：公章、1:手签章、2:人名章
                                {
                                    value: "",
                                    text: "全部"
                                },
                                {
                                    value: "0",
                                    text: "公章"
                                },
                                {
                                    value: "1",
                                    text: "手签章"
                                },
                                {
                                    value: "2",
                                    text: "人名章"
                                }
                            ],
                        },
                        {
                            type: "textarea",//
                            name: "content",//
                            label: "用印申请内容",//
                            placeholder: "",
                            immutable: true,
                        },
                        {
                            type: "number",//
                            name: "useNumber",//
                            label: "用印次数",//
                            placeholder: "",
                            immutable: true,
                        },
                        {
                            type: "text",//
                            name: "sealUnitId",//
                            label: "发往单位",//
                            placeholder: "",
                            immutable: true
                        },
                        {
                            type: "text",//
                            name: "sendUnitId",//
                            label: "用印单位",//
                            placeholder: "",
                            immutable: true
                        },
                        {
                            type: "textarea",//
                            name: "remark",//
                            label: "备注",//
                            immutable: true,
                            placeholder: ""
                        },
                        {
                            type: "textarea",//
                            name: "legalOpinion",//
                            label: "合法合规人员审批意见",//
                            placeholder: "",
                            immutable: true,
                        },
                        {
                            type: "textarea",//
                            name: "depHeadOpinion",//
                            label: "部门负责人审批意见",//
                            placeholder: "",
                            immutable: true,
                        },
                        {
                            type: "textarea",//
                            name: "leaderOpinion",//
                            label: "分管公司领导审批意见",//
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
                            name: "zjYySealFile",//
                            label: "附件1",//
                            btnName: "添加附件",
                            projectName: "zjSeal",
                            fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls"]
                        }
                    ]
                }
            ]
        }
        var buttonList = _d.buttonList//可显示的底部按钮组
        var mainTableDataObject = _d.mainTableDataObject//主表数据对象
        var subTableObject = _d.subTableObject//子表数据对象数组
        $.each(workFormJson.tabs, function (i, tabItem) {//第一次遍历workFormJson.tabs
            var $tabBtn = $('<span>' + tabItem.name + '</span>')//创建tab按钮$对象
            $tabBar.append($tabBtn)//向tab按钮控制条插入tab按钮
            var $tabCon = $('<div class="tabCon" id="tab' + i.toString() + '"></div>')//创建tab内容页面$对象
            var customBtnGroup;//tab内容页面中表单的底部按钮组配置
            if (tabItem.isMain) {//如果是主表单
                var btns = []
                $.each(buttonList, function (btnIndex, btnItem) {
                    btns.push({
                        name: btnIndex + "_" + btnItem.buttonId + "_" + btnItem.buttonType + "_" + btnItem.buttonUrl,
                        label: btnItem.buttonName
                    })
                })
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
                                        body["title"] = tabObjDatas.data[workFormJson.titleName];
                                        body["opinionContent"] = tabObjDatas.data['opinionContent'];
                                    } else if (tabItemj.type === "2") {//如果是附件类型子表，type==="2"
                                        //给子表赋值-附件表
                                        if (!body["subTableObject"]) { body["subTableObject"] = {} }
                                        for (var key in tabObjDatas.data) {
                                            var subTableDataObject = tabObjDatas.data[key];
                                            body["subTableObject"][key] = { subTablePrimaryIdName: tabItemj.tbIdName, subTableType: tabItemj.type, subTableDataObject: subTableDataObject }
                                        }
                                    } else {//如果是普通类型子表，type==="1"，目前只有1和2
                                        //给子表赋值-普通表
                                        if (!body["subTableObject"]) { body["subTableObject"] = {} }
                                        body["subTableObject"][tabItemj.tbName] = { subTablePrimaryIdName: tabItemj.tbIdName, subTableType: tabItemj.type, subTableDataObject: tabObjDatas.data }
                                    }
                                }

                                //流程操作特殊代码---开始
                                var dataNameArr = dataName.split("_")
                                var btnIndex = dataNameArr[0]
                                var buttonId = dataNameArr[1]
                                var buttonType = dataNameArr[2]
                                var buttonUrl = dataNameArr[3]
                                body["buttonId"] = buttonId//发起的操作id
                                body["workId"] = workId//流程工作id
                                if (buttonType === "3") {
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
                                    var nextShowFlowInfoList = buttonList[btnIndex].nextShowFlowInfoList//下一流程可选支线
                                    var reviewPickType = buttonList[btnIndex].reviewPickType//选人组件配置
                                    var reviewUserObject = buttonList[btnIndex].reviewUserObject//选人组建默认数据
                                    var reviewObjectDisableFlag = _d.buttonList[btnIndex].reviewObjectDisableFlag//选人组建是否禁用
                                    if (nextShowFlowInfoList.length) {
                                        var selectList = []
                                        $.each(nextShowFlowInfoList, function (_, selectItem) {
                                            if (!selectItem.nextDoneNodeFlag) {
                                                selectList.push({
                                                    value: selectItem.nextNodeId,
                                                    text: selectItem.nextNodeName,
                                                    disabled: selectItem.nextDoneNode,
                                                })
                                            }
                                        })
                                        var conditions = []
                                        if (reviewPickType) {//未配置就不用进行人员选择
                                            conditions = [
                                                {
                                                    type: "select",
                                                    name: "reviewNodeId",
                                                    label: "流程操作",
                                                    must: true,
                                                    selectList: selectList
                                                },
                                                {
                                                    type: "pickTree",
                                                    name: "reviewUserObject",
                                                    label: "人员选择",
                                                    must: true,
                                                    pickType: reviewPickType,
                                                    immutableAdd: reviewObjectDisableFlag === "1"
                                                    // pickType: {
                                                    //     member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
                                                    // }
                                                }
                                            ]
                                        } else {
                                            conditions = [
                                                {
                                                    type: "select",
                                                    name: "reviewNodeId",
                                                    label: "流程操作",
                                                    must: true,
                                                    selectList: selectList
                                                }
                                            ]
                                        }
                                        var detailLayer = $("#detailLayer").detailLayer({
                                            conditions: conditions,
                                            onAdd: function (detaiObj, _params) {
                                                body["reviewNodeId"] = _params.reviewNodeId
                                                body["reviewUserObject"] = _params.reviewUserObject
                                                l.ajax('submitFlow', body, function (data) {
                                                    parent.pager.page('remote')
                                                    obj.close()
                                                    detaiObj.close()
                                                })
                                            }
                                        })
                                        detailLayer.open({
                                            isAdd: true,
                                            reviewUserObject: reviewUserObject
                                        })//打开选择下一流程人员选择的弹窗
                                    } else {
                                        layer.confirm("确定提交？", { icon: 0, }, function (index) {//流程发起请求
                                            l.ajax('submitFlow', body, function (data, message, success) {
                                                if (success) {
                                                    parent.pager.page('remote')
                                                    obj.close()
                                                }
                                            })
                                            layer.close(index);
                                        });
                                    }
                                }
                                //流程操作特殊代码---结束

                                break;
                        }
                    }
                }

                //如果需要显示意见框 
                if (_d.opinionShowFlag === '1') {
                    tabItem.conditions.push({
                        type: "textarea",//
                        name: "opinionContent",//
                        label: "您的意见",//
                        placeholder: "您的意见",
                    })
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
            //流程操作特殊代码（向各个表单中赋值）---结束
            tabCons[i].close = function () {
                parent.layer.close(parent.myOpenLayer)
            }
        })
        $tabSystem.append($tabBar).append(tabCons).Huitab({
            index: 0
        });

    }
})


