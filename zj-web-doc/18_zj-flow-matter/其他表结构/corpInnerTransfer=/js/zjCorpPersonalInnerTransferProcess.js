var workId = l.getUrlParam('id') || "";////流程工作id
var $tabSystem = $("#tab-system")//模版顶级jq对象
var $tabBar = $('<div class="tabBar cl"></div>');//tab按钮控制条
var tabCons = []//tab内容页面组
//根据workId获取流程工作模版数据及数据
l.ajax("getSubmitFlow", { workId: workId }, function (_d, _m, _s, _r) {
    if (_s) {
        var workFormJson = {//流程模版json（升级版：根据流程工作id动态获取，当前前台写死）
            name: "公司机关调入人员申请",
            tabs: [
                {
                    name: "基本信息",
                    type: "1",
                    isMain: "1",
                    tbName: "zjCorpPersonalInnerTransfer",
                    tbIdName: "corpInnerTransferId",
                    conditions: [
						{
                           type: "hidden",//五种形式：text,select,date,hidden,textarea,
                           name: "corpInnerTransferId",//输入字段名
                        },
                        {
                           type: "text",//
                           name: "serialNumber",//
                           label: "编号",//
                           placeholder: "请输入编号",
                        },
                        {
                           type: "date",//text,select,date,
                           name: "transferTime",
                           label: "调出日期",
                           defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd hh:mm:ss"),
                           maxDate: "\'#F{$dp.$D(\\\'logmax\\\')||\\\'%y-%M-%d\\\'}\'",
                           id: "logmin",
                        },
                        {
                           type: "text",//
                           name: "transferName",//
                           label: "调入人",//
                           placeholder: "请输入调入人",
                        },
                        {
                           type: "text",//
                           name: "relation",//
                           label: "关系",//
                           placeholder: "请输入关系",
                        },
                        {
                           type: "text",//
                           name: "name",//
                           label: "姓名",//
                           placeholder: "请输入姓名",
                        },
                        {
                           type: "text",//
                           name: "post",//
                           label: "岗位",//
                           placeholder: "请输入岗位",
                        },
                        {
                           type: "text",//
                           name: "originalPost",//
                           label: "原工作岗位",//
                           placeholder: "请输入原工作岗位",
                        },
                        {
                           type: "text",//
                           name: "newPost",//
                           label: "新工作岗位",//
                           placeholder: "请输入新工作岗位",
                        },
                        {
                           type: "text",//
                           name: "postSalary",//
                           label: "岗位薪资",//
                           placeholder: "请输入岗位薪资",
                        },
                        {
                           type: "textarea",//
                           name: "hrOpinion",//
                           label: "人力资源处负责人意见",//
                           placeholder: "请输入人力资源处负责人意见",
                        },
                        {
                           type: "textarea",//
                           name: "relatedSignList",//
                           label: "相关人员签字",//
                           placeholder: "请输入相关人员签字",
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
                            name: "zjCorpPersonalInnerTransferFile",//
                            label: "附件1",//
                            btnName: "添加附件",
                            projectName: "zjCorpPersonalInnerTransfer",
                            immutable: true,
                            uploadUrl:'uploadFiles3',
                            fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls"]
                        }
                    ]
                }
            ]
        }
        var buttonList = _d.buttonList//可显示的底部按钮组
        var mainTableObject = _d.mainTableObject//主表数据对象
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
                        name: btnIndex + "_" + btnItem.buttonId,
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
                                        body["mainTableObject"] = tabObjDatas.data;
                                        body["title"] = tabObjDatas.data['projectTitle'];
                                        body["opinionContent"] = tabObjDatas.data['opinionContent'];
                                    } else if (tabItemj.type === "2") {//如果是附件类型子表，type==="2"
                                        //给子表赋值-附件表
                                        if (!body["subTableObject"]) { body["subTableObject"] = {} }
                                        for (var key in tabObjDatas.data) {
                                            var data = tabObjDatas.data[key];
                                            body["subTableObject"][key] = { subFlowFormId: tabItemj.tbIdName, subTableType: tabItemj.type, subTableObject: data }
                                        }
                                    } else {//如果是普通类型子表，type==="1"，目前只有1和2
                                        //给子表赋值-普通表
                                        if (!body["subTableObject"]) { body["subTableObject"] = {} }
                                        body["subTableObject"][tabItemj.tbName] = { subFlowFormId: tabItemj.tbIdName, subTableType: tabItemj.type, subTableObject: tabObjDatas.data }
                                    }
                                }

                                //流程操作特殊代码---开始
                                var dataNameArr = dataName.split("_")
                                body["buttonId"] = dataNameArr[1]//发起的操作id
                                body["workId"] = workId//流程工作id
                                var nextShowFlowInfoList = buttonList[dataNameArr[0]].nextShowFlowInfoList//下一流程可选支线
                                var reviewPickType = buttonList[dataNameArr[0]].reviewPickType//选人组件配置
                                var reviewUserObject = buttonList[dataNameArr[0]].reviewUserObject//选人组建默认数据
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
                                                pickType: reviewPickType
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
                                        l.ajax('submitFlow', body, function (data) {
                                            if (success) {
                                                parent.pager.page('remote')
                                                obj.close()
                                            }
                                        })
                                        layer.close(index);
                                    });
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
                tabCons[i].setOpenData(mainTableObject)
            } else if (tabItem.type === "2") {
                var _subTableObject = {}
                for (var key in subTableObject) {
                    _subTableObject[key] = subTableObject[key].data
                }
                tabCons[i].setOpenData(_subTableObject)
            } else {
                tabCons[i].setOpenData(subTableObject[tabItem.tbName].data)
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


