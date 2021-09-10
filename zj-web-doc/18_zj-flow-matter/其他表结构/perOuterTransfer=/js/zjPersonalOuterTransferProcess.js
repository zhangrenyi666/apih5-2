var workId = l.getUrlParam('id') || "";////流程工作id
var $tabSystem = $("#tab-system")//模版顶级jq对象
var $tabBar = $('<div class="tabBar cl"></div>');//tab按钮控制条
var tabCons = []//tab内容页面组
//根据workId获取流程工作模版数据及数据
l.ajax("getSubmitFlow", { workId: workId }, function (_d, _m, _s, _r) {
    if (_s) {
        var workFormJson = {//流程模版json（升级版：根据流程工作id动态获取，当前前台写死）
            name: "外部调入人员流程表",
            tabs: [
                {
                    name: "基本信息",
                    type: "1",
                    isMain: "1",
                    tbName: "zjPersonalOuterTransfer",
                    tbIdName: "outerTransferId",
                    conditions: [
					    {
                           type: "hidden",//五种形式：text,select,date,hidden,textarea,
                           name: "outerTransferId",//输入字段名
                        },
                        {
                           type: "text",//
                           name: "reportedUnit",//
                           label: "上报单位",//
                           placeholder: "请输入上报单位",
                        },
                        {
                           type: "date",//text,select,date,
                           name: "reportedTime",
                           label: "上报时间",
                           defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd hh:mm:ss"),
                           maxDate: "\'#F{$dp.$D(\\\'logmax\\\')||\\\'%y-%M-%d\\\'}\'",
                           id: "logmin",
                        },
                        {
                           type: "text",//
                           name: "name",//
                           label: "姓名",//
                           placeholder: "请输入姓名",
                        },
                        {
                            type: "select",
                            name: "sex",
                            label: "性别"
                            ],
                        },
                        {
                            type: "date",//text,select,date,
                            name: "birth",
                            label: "出生年月",
                            defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd hh:mm:ss"),
                            maxDate: "\'#F{$dp.$D(\\\'logmax\\\')||\\\'%y-%M-%d\\\'}\'",
                            id: "logmin",
                        },
                        {
                            type: "select",
                            name: "nation",
                            label: "民族"
                            ],
                        },
                        {
                            type: "select",
                            name: "degreeOfEducation",
                            label: "文化程度"
                            ],
                        },
                        {
                            type: "select",
                            name: "maritalStatus",
                            label: "婚姻状态"
                            ],
                        },
                        {
                            type: "date",//
                            name: "joinWorkTime",//
                            label: "参加工作时间",//
                            dateFmt: "yyyy-MM-dd",
                            defaultValue: new Date(),
                            id:"joinWorkTime",
                        },
                        {
                            type: "select",
                            name: "politicsStatus",
                            label: "政治面貌"
                            ],
                        },
                        {
                            type: "text",//
                            name: "nativePlace",//
                            label: "籍贯",//
                            placeholder: "请输入籍贯",
                        },
                        {
                            type: "text",//
                            name: "professionalQualification",//
                            label: "职业资格",//
                            placeholder: "请输入职业资格",
                        },
                        {
                            type: "text",//
                            name: "duty",//
                            label: "职务",//
                            placeholder: "请输入职务",
                        },
                        {
                            type: "text",//
                            name: "professionalTitle",//
                            label: "职称",//
                            placeholder: "请输入职称",
                        },
                        {
                            type: "text",//
                            name: "currentWorkUnit",//
                            label: "现工作单位",//
                            placeholder: "请输入现工作单位",
                        },
                        {
                            type: "text",//
                            name: "adjustUnit",//
                            label: "拟调单位",//
                            placeholder: "请输入拟调单位",
                        },
                        {
                            type: "text",//
                            name: "domicilePlace",//
                            label: "户口所在地",//
                            placeholder: "请输入户口所在地",
                        },
                        {
                            type: "text",//
                            name: "familyAddress",//
                            label: "家庭住址",//
                            placeholder: "请输入家庭住址",
                        },
                        {
                            type: "date",//
                            name: "originalEducationStartTime",//
                            label: "开始时间（原始学历）",//
                            dateFmt: "yyyy-MM-dd",
                            defaultValue: new Date(),
                            id:"originalEducationStartTime",
                        },
                        {
                            type: "date",//
                            name: "originalEducationEndTime",//
                            label: "结束时间（原始学历）",//
                            dateFmt: "yyyy-MM-dd",
                            defaultValue: new Date(),
                            id:"originalEducationEndTime",
                        },
                        {
                            type: "text",//
                            name: "originalGraduateSchool",//
                            label: "毕业院校（原始学历）",//
                            placeholder: "请输入毕业院校（原始学历）",
                        },
                        {
                            type: "text",//
                            name: "originalMajor",//
                            label: "专业（原始学历）",//
                            placeholder: "请输入专业（原始学历）",
                        },
                        {
                            type: "text",//
                            name: "originalEductionalSystme",//
                            label: "学制（原始学历）",//
                            placeholder: "请输入学制（原始学历）",
                        },
                        {
                            type: "text",//
                            name: "originalDegree",//
                            label: "学位（原始学历）",//
                            placeholder: "请输入学位（原始学历）",
                        },
                        {
                            type: "select",
                            name: "originalSepFlag",
                            label: "是否统分（原始学历）"
                             ],
                        },
                        {
                            type: "date",//
                            name: "highestEducationStarTime",//
                            label: "开始时间（最高学历）",//
                            dateFmt: "yyyy-MM-dd",
                            defaultValue: new Date(),
                            id:"highestEducationStarTime",
                        },
                        {
                            type: "date",//
                            name: "highestEducationEndTime",//
                            label: "结束时间（最高学历）",//
                            dateFmt: "yyyy-MM-dd",
                            defaultValue: new Date(),
                            id:"highestEducationEndTime",
                        },
                        {
                            type: "text",//
                            name: "highestGraduateSchool",//
                            label: "毕业院校（最高学历）",//
                            placeholder: "请输入毕业院校（最高学历）",
                        },
                        {
                            type: "text",//
                            name: "highestMajor",//
                            label: "专业（最高学历）",//
                            placeholder: "请输入专业（最高学历）",
                        },
                        {
                            type: "text",//
                            name: "highestEducationSystem",//
                            label: "学制（最高学历）",//
                            placeholder: "请输入学制（最高学历）",
                        },
                        {
                            type: "text",//
                            name: "highestDegree",//
                            label: "学位（最高学历）",//
                            placeholder: "请输入学位（最高学历）",
                        },
                        {
                            type: "select",
                            name: "highestSepFlag",
                            label: "是否统分（最高学历）"
                                 ],
                        },
                        {
                            type: "text",//
                            name: "jobResume",//
                            label: "工作简历",//
                            placeholder: "请输入工作简历",
                        },
                        {
                            type: "text",//
                            name: "transferReason",//
                            label: "调动原因",//
                            placeholder: "请输入调动原因",
                        },
                        {
                            type: "text",//
                            name: "adjustPost",//
                            label: "拟调岗位",//
                            placeholder: "请输入拟调岗位",
                        },
                        {
                            type: "text",//
                            name: "loverName",//
                            label: "爱人姓名",//
                            placeholder: "请输入爱人姓名",
                        },
                        {
                            type: "text",//
                            name: "loverWorkUnit",//
                            label: "爱人工作单位",//
                            placeholder: "请输入爱人工作单位",
                        },
                        {
                            type: "text",//
                            name: "loverDomicilePlace",//
                            label: "爱人户口所在地",//
                            placeholder: "请输入爱人户口所在地",
                        },
                        {
                            type: "select",
                            name: "followFlag",
                            label: "是否随调"
                           ],
                        },
                        {
                            type: "text",//
                            name: "familyMembers",//
                            label: "家庭主要成员",//
                            placeholder: "请输入家庭主要成员",
                        },
                        {
                            type: "textarea",//
                            name: "reportedUnitOpinion",//
                            label: "上报单位意见",//
                            placeholder: "请输入上报单位意见",
                        },
                        {
                            type: "textarea",//
                            name: "exportUnitOpinion",//
                            label: "调出单位意见",//
                            placeholder: "请输入调出单位意见",
                        },
                        {
                            type: "textarea",//
                            name: "hrOpinion",//
                            label: "人力资源意见",//
                            placeholder: "请输入人力资源意见",
                        },
                        {
                            type: "textarea",//
                            name: "relevantDepartmentOpinion",//
                            label: "相关部门意见",//
                            placeholder: "请输入相关部门意见",
                        },
                        {
                            type: "textarea",//
                            name: "bureauApprovalOpinion",//
                            label: "局审批意见",//
                            placeholder: "请输入局审批意见",
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
                            name: "zjPersonalOuterTransferFile",//
                            label: "附件1",//
                            btnName: "添加附件",
                            projectName: "zjPersonalOuterTransfer",
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


