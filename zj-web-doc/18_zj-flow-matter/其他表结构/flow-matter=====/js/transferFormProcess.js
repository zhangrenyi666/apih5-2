var workId = l.getUrlParam('id') || "";////流程工作id
var $tabSystem = $("#tab-system")//模版顶级jq对象
var $tabBar = $('<div class="tabBar cl"></div>');//tab按钮控制条
var tabCons = []//tab内容页面组
//根据workId获取流程工作模版数据及数据
l.ajax("getSubmitFlow", { workId: workId }, function (_d, _m, _s, _r) {
    if (_s) {
        var workFormJson = {//流程模版json（升级版：根据流程工作id动态获取，当前前台写死）
            name: "调动审批",
            titleName: "sealApplyName",
            tabs: [
                {
                    name: "基本信息",
                    type: "1",
                    isMain: "1",
                    tbName: "zjPersonalInnerTransfer",
                    tbIdName: "innerTransferId",
                    conditions: [
                        {
                            type: "hidden",//五种形式：text,select,date,hidden,textarea,
                            name: "innerTransferId",//输入字段名
                        },
                        {
                            type: "text",//
                            name: "reportedUnit",//
                            label: "上报单位",//
                            placeholder: "请输入上报单位",
                            must: true
                        },
                        {
                            type: "date",//text,select,date,
                            name: "reportedTime",
                            label: "上报时间",
                            maxDate: "\'#F{\\\'%y-%M-%d\\\'}\'",
                            must: true
                        },
                        {
                            type: "text",//
                            name: "name",//
                            label: "姓名",//
                            placeholder: "请输入姓名",
                            must: true
                        },
                        {
                            type: "select",
                            name: "sex",
                            label: "性别",
                            selectList: [//当类型为select时，option配置
                                {
                                    value: 1,//输入字段的值
                                    text: "男",//显示中文名
                                    selected: false//默认是否选中
                                },
                                {
                                    value: 2,//输入字段的值
                                    text: "女",//显示中文名
                                    selected: false//默认是否选中
                                }
                            ],
                            must: true
                        },
                        {
                            type: "date",//text,select,date,
                            name: "birth",
                            label: "出生年月",
                            must: true
                        },
                        {
                            type: "text",//
                            name: "nation",//
                            label: "民族",//
                            placeholder: "请输入民族",
                            must: true
                        },
                        {
                            type: "select",
                            name: "degreeOfEducation",
                            label: "文化程度",
                            selectList: [//当类型为select时，option配置
                                {
                                    value: 1,//输入字段的值
                                    text: "高中",//显示中文名
                                    selected: false//默认是否选中
                                },
                                {
                                    value: 2,//输入字段的值
                                    text: "大学本科 ",//显示中文名
                                    selected: false//默认是否选中
                                },
                                {
                                    value: 3,//输入字段的值
                                    text: "硕士",//显示中文名
                                    selected: false//默认是否选中
                                }
                            ],
                            must: true
                        },
                        {
                            type: "select",
                            name: "maritalStatus",
                            label: "婚姻状态",
                            selectList: [//当类型为select时，option配置
                                {
                                    value: 1,//输入字段的值
                                    text: "已婚",//显示中文名
                                    selected: false//默认是否选中
                                },
                                {
                                    value: 2,//输入字段的值
                                    text: "未婚",//显示中文名
                                    selected: false//默认是否选中
                                }
                            ],
                            must: true
                        },
                        {
                            type: "date",//
                            name: "joinWorkTime",//
                            label: "参加工作时间",//
                            dateFmt: "yyyy-MM-dd",
                            must: true
                        },
                        {
                            type: "select",
                            name: "politicsStatus",
                            label: "政治面貌",
                            selectList: [//当类型为select时，option配置
                                {
                                    value: 1,//输入字段的值
                                    text: "群众",//显示中文名
                                    selected: false//默认是否选中
                                },
                                {
                                    value: 2,//输入字段的值
                                    text: "团员 ",//显示中文名
                                    selected: false//默认是否选中
                                },
                                {
                                    value: 2,//输入字段的值
                                    text: "党员",//显示中文名
                                    selected: false//默认是否选中
                                }
                            ],
                            must: true
                        },
                        {
                            type: "text",//
                            name: "nativePlace",//
                            label: "籍贯",//
                            placeholder: "请输入籍贯",
                            must: true
                        },
                        {
                            type: "text",//
                            name: "professionalQualification",//
                            label: "职业资格",//
                            placeholder: "请输入职业资格",
                            must: true
                        },
                        {
                            type: "text",//
                            name: "duty",//
                            label: "职务",//
                            placeholder: "请输入职务",
                            must: true
                        },
                        {
                            type: "text",//
                            name: "professionalTitle",//
                            label: "职称",//
                            placeholder: "请输入职称",
                            must: true
                        },
                        {
                            type: "text",//
                            name: "currentWorkUnit",//
                            label: "现工作单位",//
                            placeholder: "请输入现工作单位",
                            must: true
                        },
                        {
                            type: "text",//
                            name: "adjustUnit",//
                            label: "拟调单位",//
                            placeholder: "请输入拟调单位",
                            must: true
                        },
                        {
                            type: "text",//
                            name: "domicilePlace",//
                            label: "户口所在地",//
                            placeholder: "请输入户口所在地",
                            must: true
                        },
                        {
                            type: "text",//
                            name: "familyAddress",//
                            label: "家庭住址",//
                            placeholder: "请输入家庭住址",
                            must: true
                        },
                        {
                            type: "date",//
                            name: "originalEducationStartTime",//
                            label: "开始时间",//
                            dateFmt: "yyyy-MM-dd",
                            defaultValue: new Date(),
                            maxDate: "\'#F{$dp.$D(\\\'originalEducationEndTime\\\')||\\\'%y-%M-%d\\\'}\'",
                            id: "originalEducationStartTime",
                            must: true
                        },
                        {
                            type: "date",//
                            name: "originalEducationEndTime",//
                            label: "结束时间",//
                            dateFmt: "yyyy-MM-dd",
                            defaultValue: new Date(),
                            minDate: "\'#F{$dp.$D(\\\'originalEducationStartTime\\\')||\\\'%y-%M-%d\\\'}\'",
                            id: "originalEducationEndTime",
                            must: true
                        },
                        {
                            type: "text",//
                            name: "originalGraduateSchool",//
                            label: "毕业院校",//
                            placeholder: "请输入毕业院校",
                            must: true
                        },
                        {
                            type: "text",//
                            name: "originalMajor",//
                            label: "专业",//
                            placeholder: "请输入专业",
                            must: true
                        },
                        {
                            type: "text",//
                            name: "originalEductionalSystme",//
                            label: "学制",//
                            placeholder: "请输入学制",
                            must: true
                        },
                        {
                            type: "text",//
                            name: "originalDegree",//
                            label: "学位",//
                            placeholder: "请输入学位",
                            must: true
                        },
                        {
                            type: "select",
                            name: "originalSepFlag",
                            label: "是否统分",
                            selectList: [//当类型为select时，option配置
                                {
                                    value: 1,//输入字段的值
                                    text: "是",//显示中文名
                                    selected: false//默认是否选中
                                },
                                {
                                    value: 2,//输入字段的值
                                    text: "否",//显示中文名
                                    selected: false//默认是否选中
                                }
                            ],
                            must: true
                        },
                        {
                            type: "date",//
                            name: "highestEducationStarTime",//
                            label: "开始时间",//
                            dateFmt: "yyyy-MM-dd",
                            defaultValue: new Date(),
                            maxDate: "\'#F{$dp.$D(\\\'highestEducationEndTime\\\')||\\\'%y-%M-%d\\\'}\'",
                            id: "highestEducationStarTime",
                            must: true
                        },
                        {
                            type: "date",//
                            name: "highestEducationEndTime",//
                            label: "结束时间",//
                            dateFmt: "yyyy-MM-dd",
                            defaultValue: new Date(),
                            minDate: "\'#F{$dp.$D(\\\'highestEducationStarTime\\\')||\\\'%y-%M-%d\\\'}\'",
                            id: "highestEducationEndTime",
                            must: true
                        },
                        {
                            type: "text",//
                            name: "highestGraduateSchool",//
                            label: "毕业院校",//
                            placeholder: "请输入毕业院校",
                            must: true
                        },
                        {
                            type: "text",//
                            name: "highestMajor",//
                            label: "专业",//
                            placeholder: "请输入专业",
                            must: true
                        },
                        {
                            type: "text",//
                            name: "highestEducationSystem",//
                            label: "学制",//
                            placeholder: "请输入学制",
                            must: true
                        },
                        {
                            type: "text",//
                            name: "highestDegree",//
                            label: "学位",//
                            placeholder: "请输入学位",
                            must: true
                        },
                        {
                            type: "select",
                            name: "highestSepFlag",
                            label: "是否统分",
                            selectList: [//当类型为select时，option配置
                                {
                                    value: 1,//输入字段的值
                                    text: "是",//显示中文名
                                    selected: false//默认是否选中
                                },
                                {
                                    value: 2,//输入字段的值
                                    text: "否",//显示中文名
                                    selected: false//默认是否选中
                                }
                            ],
                            must: true
                        },
                        {
                            type: "textarea",//
                            name: "jobResume",//
                            label: "工作简历",//
                            placeholder: "请输入工作简历",
                            must: true
                        },
                        {
                            type: "textarea",//
                            name: "transferReason",//
                            label: "调动原因",//
                            placeholder: "请输入调动原因",
                            must: true
                        },
                        {
                            type: "text",//
                            name: "adjustPost",//
                            label: "拟调岗位",//
                            placeholder: "请输入拟调岗位",
                            must: true
                        },
                        {
                            type: "text",//
                            name: "loverName",//
                            label: "爱人姓名",//
                            placeholder: "请输入爱人姓名",
                            must: true
                        },
                        {
                            type: "text",//
                            name: "loverWorkUnit",//
                            label: "爱人工作单位",//
                            placeholder: "请输入爱人工作单位",
                            must: true
                        },
                        {
                            type: "text",//
                            name: "loverDomicilePlace",//
                            label: "爱人户口所在地",//
                            placeholder: "请输入爱人户口所在地",
                            must: true
                        },
                        {
                            type: "select",
                            name: "followFlag",
                            label: "是否随调",
                            selectList: [//当类型为select时，option配置
                                {
                                    value: 1,//输入字段的值
                                    text: "是",//显示中文名
                                    selected: false//默认是否选中
                                },
                                {
                                    value: 2,//输入字段的值
                                    text: "否",//显示中文名
                                    selected: false//默认是否选中
                                }
                            ],
                            must: true
                        },
                        {
                            type: "textarea",//
                            name: "familyMembers",//
                            label: "家庭主要成员",//
                            placeholder: "请输入家庭主要成员",
                            must: true
                        },
                        {
                            type: "textarea",//
                            name: "reportedUnitOpinion",//
                            label: "上报单位意见",//
                            placeholder: "请输入上报单位意见",
                            must: true
                        },
                        {
                            type: "textarea",//
                            name: "exportUnitOpinion",//
                            label: "调出单位意见",//
                            placeholder: "请输入调出单位意见",
                            must: true
                        },
                        {
                            type: "textarea",//
                            name: "hrOpinion",//
                            label: "人力资源意见",//
                            placeholder: "请输入人力资源意见",
                            must: true
                        },
                        {
                            type: "textarea",//
                            name: "relevantDepartmentOpinion",//
                            label: "相关部门意见",//
                            placeholder: "请输入相关部门意见",
                            must: true
                        },
                        {
                            type: "textarea",//
                            name: "bureauApprovalOpinion",//
                            label: "局审批意见",//
                            placeholder: "请输入局审批意见",
                            must: true
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
                            name: "zjPersonalInnerTransferFile",//
                            label: "附件1",//
                            btnName: "添加附件",
                            projectName: "zjPersonalInnerTransfer",
                            fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls"]
                        }
                    ]
                }
            ]
        }
        //
        var $table = $('<table class="table table-border table-bordered radius"/>')
        var $tr = $("<tr/>")
        var $th = $("<th/>")
        var $td = $("<td/>")
        $table.append($tr.clone().append($th.clone().attr("colspan", "12").addClass("text-c").html("调动审批表")))
        var line0 = $tr.clone()
        var cell0_0 = $th.clone().attr("data-cellName", "reportedUnit")
        var cell0_1 = $td.clone().attr("colspan", "7").attr("data-cellName", "reportedUnit")
        var cell0_2 = $td.clone().attr("colspan", "4").attr("data-cellName", "reportedTime")
        line0.append(cell0_0).append(cell0_1).append(cell0_2)
        $table.append(line0)
        var line1 = $tr.clone()
        var cell1_0 = $th.clone().attr("data-cellName", "name")
        var cell1_1 = $td.clone().attr("data-cellName", "name")
        var cell1_2 = $th.clone().attr("data-cellName", "sex")
        var cell1_3 = $td.clone().attr("data-cellName", "sex")
        var cell1_4 = $th.clone().attr("data-cellName", "birth")
        var cell1_5 = $td.clone().attr("data-cellName", "birth")
        var cell1_6 = $th.clone().attr("data-cellName", "nation")
        var cell1_7 = $td.clone().attr("data-cellName", "nation")
        var cell1_8 = $th.clone().attr("data-cellName", "degreeOfEducation")
        var cell1_9 = $td.clone().attr("data-cellName", "degreeOfEducation")
        var cell1_10 = $th.clone().attr("data-cellName", "maritalStatus")
        var cell1_11 = $td.clone().attr("data-cellName", "maritalStatus")
        line1.append(cell1_0).append(cell1_1).append(cell1_2).append(cell1_3).append(cell1_4).append(cell1_5).append(cell1_6).append(cell1_7).append(cell1_8).append(cell1_9).append(cell1_10).append(cell1_11)
        $table.append(line1)
        var line2 = $tr.clone()
        var cell2_0 = $th.clone().attr("data-cellName", "joinWorkTime")
        var cell2_1 = $td.clone().attr("data-cellName", "joinWorkTime")
        var cell2_2 = $th.clone().attr("data-cellName", "politicsStatus")
        var cell2_3 = $td.clone().attr("data-cellName", "politicsStatus")
        var cell2_4 = $th.clone().attr("data-cellName", "nativePlace")
        var cell2_5 = $td.clone().attr("data-cellName", "nativePlace")
        var cell2_6 = $th.clone().attr("data-cellName", "professionalQualification")
        var cell2_7 = $td.clone().attr("data-cellName", "professionalQualification")
        var cell2_8 = $th.clone().attr("data-cellName", "duty")
        var cell2_9 = $td.clone().attr("data-cellName", "duty")
        var cell2_10 = $th.clone().attr("data-cellName", "professionalTitle")
        var cell2_11 = $td.clone().attr("data-cellName", "professionalTitle")
        line2.append(cell2_0).append(cell2_1).append(cell2_2).append(cell2_3).append(cell2_4).append(cell2_5).append(cell2_6).append(cell2_7).append(cell2_8).append(cell2_9).append(cell2_10).append(cell2_11)
        $table.append(line2)
        var line3 = $tr.clone()
        var cell3_0 = $th.clone().attr("data-cellName", "currentWorkUnit")
        var cell3_1 = $td.clone().attr("colspan", "4").attr("data-cellName", "currentWorkUnit")
        var cell3_2 = $th.clone().attr("data-cellName", "adjustUnit")
        var cell3_3 = $td.clone().attr("colspan", "6").attr("data-cellName", "adjustUnit")
        line3.append(cell3_0).append(cell3_1).append(cell3_2).append(cell3_3)
        $table.append(line3)
        var line4 = $tr.clone()
        var cell4_0 = $th.clone().attr("data-cellName", "domicilePlace")
        var cell4_1 = $td.clone().attr("colspan", "5").attr("data-cellName", "domicilePlace")
        var cell4_2 = $th.clone().attr("colspan", "2").attr("data-cellName", "familyAddress")
        var cell4_3 = $td.clone().attr("colspan", "4").attr("data-cellName", "familyAddress")
        line4.append(cell4_0).append(cell4_1).append(cell4_2).append(cell4_3)
        $table.append(line4)
        var line5 = $tr.clone()
        var cell5_0 = $th.clone().attr("rowspan", "2").html("原始学历")
        var cell5_1 = $th.clone().attr("colspan", "2").attr("data-cellName", "originalEducationStartTime")
        var cell5_2 = $th.clone().attr("colspan", "2").attr("data-cellName", "originalEducationEndTime")
        var cell5_3 = $th.clone().attr("data-cellName", "originalGraduateSchool")
        var cell5_4 = $th.clone().attr("colspan", "2").attr("data-cellName", "originalMajor")
        var cell5_5 = $th.clone().attr("data-cellName", "originalEductionalSystme")
        var cell5_6 = $th.clone().attr("colspan", "2").attr("data-cellName", "originalDegree")
        var cell5_7 = $th.clone().attr("data-cellName", "originalSepFlag")
        line5.append(cell5_0).append(cell5_1).append(cell5_2).append(cell5_3).append(cell5_4).append(cell5_5).append(cell5_6).append(cell5_7)
        $table.append(line5)
        var line6 = $tr.clone()
        var cell6_0 = $td.clone().attr("colspan", "2").attr("data-cellName", "originalEducationStartTime")
        var cell6_1 = $td.clone().attr("colspan", "2").attr("data-cellName", "originalEducationEndTime")
        var cell6_2 = $td.clone().attr("data-cellName", "originalGraduateSchool")
        var cell6_3 = $td.clone().attr("colspan", "2").attr("data-cellName", "originalMajor")
        var cell6_4 = $td.clone().attr("data-cellName", "originalEductionalSystme")
        var cell6_5 = $td.clone().attr("colspan", "2").attr("data-cellName", "originalDegree")
        var cell6_6 = $td.clone().attr("data-cellName", "originalSepFlag")
        line6.append(cell6_0).append(cell6_1).append(cell6_2).append(cell6_3).append(cell6_4).append(cell6_5).append(cell6_6)
        $table.append(line6)
        var line7 = $tr.clone()
        var cell7_0 = $th.clone().attr("rowspan", "2").html("最高学历")
        var cell7_1 = $th.clone().attr("colspan", "2").attr("data-cellName", "highestEducationStarTime")
        var cell7_2 = $th.clone().attr("colspan", "2").attr("data-cellName", "highestEducationEndTime")
        var cell7_3 = $th.clone().attr("data-cellName", "highestGraduateSchool")
        var cell7_4 = $th.clone().attr("colspan", "2").attr("data-cellName", "highestMajor")
        var cell7_5 = $th.clone().attr("data-cellName", "highestEducationSystem")
        var cell7_6 = $th.clone().attr("colspan", "2").attr("data-cellName", "highestDegree")
        var cell7_7 = $th.clone().attr("data-cellName", "highestSepFlag")
        line7.append(cell7_0).append(cell7_1).append(cell7_2).append(cell7_3).append(cell7_4).append(cell7_5).append(cell7_6).append(cell7_7)
        $table.append(line7)
        var line8 = $tr.clone()
        var cell8_0 = $td.clone().attr("colspan", "2").attr("data-cellName", "highestEducationStarTime")
        var cell8_1 = $td.clone().attr("colspan", "2").attr("data-cellName", "highestEducationEndTime")
        var cell8_2 = $td.clone().attr("data-cellName", "highestGraduateSchool")
        var cell8_3 = $td.clone().attr("colspan", "2").attr("data-cellName", "highestMajor")
        var cell8_4 = $td.clone().attr("data-cellName", "highestEducationSystem")
        var cell8_5 = $td.clone().attr("colspan", "2").attr("data-cellName", "highestDegree")
        var cell8_6 = $td.clone().attr("data-cellName", "highestSepFlag")
        line8.append(cell8_0).append(cell8_1).append(cell8_2).append(cell8_3).append(cell8_4).append(cell8_5).append(cell8_6)
        $table.append(line8)
        var line9 = $tr.clone()
        var cell9_0 = $th.clone().attr("data-cellName", "jobResume")
        var cell9_1 = $td.clone().attr("colspan", "11").attr("data-cellName", "jobResume")
        line9.append(cell9_0).append(cell9_1)
        $table.append(line9)
        var line10 = $tr.clone()
        var cell10_0 = $th.clone().attr("data-cellName", "transferReason")
        var cell10_1 = $td.clone().attr("colspan", "11").attr("data-cellName", "transferReason")
        line10.append(cell10_0).append(cell10_1)
        $table.append(line10)
        var line11 = $tr.clone()
        var cell11_0 = $th.clone().attr("data-cellName", "adjustPost")
        var cell11_1 = $td.clone().attr("colspan", "11").attr("data-cellName", "adjustPost")
        line11.append(cell11_0).append(cell11_1)
        $table.append(line11)
        var line12 = $tr.clone()
        var cell12_0 = $th.clone().attr("data-cellName", "loverName")
        var cell12_1 = $td.clone().attr("data-cellName", "loverName")
        var cell12_2 = $th.clone().attr("data-cellName", "loverWorkUnit")
        var cell12_3 = $td.clone().attr("colspan", "3").attr("data-cellName", "loverWorkUnit")
        var cell12_4 = $th.clone().attr("colspan", "2").attr("data-cellName", "loverDomicilePlace")
        var cell12_5 = $td.clone().attr("colspan", "2").attr("data-cellName", "loverDomicilePlace")
        var cell12_6 = $th.clone().attr("data-cellName", "followFlag")
        var cell12_7 = $td.clone().attr("data-cellName", "followFlag")
        line12.append(cell12_0).append(cell12_1).append(cell12_2).append(cell12_3).append(cell12_4).append(cell12_5).append(cell12_6).append(cell12_7)
        $table.append(line12)
        var line13 = $tr.clone()
        var cell13_0 = $th.clone().attr("data-cellName", "familyMembers")
        var cell13_1 = $td.clone().attr("colspan", "11").attr("data-cellName", "familyMembers")
        line13.append(cell13_0).append(cell13_1)
        $table.append(line13)
        var line14 = $tr.clone()
        var cell14_0 = $th.clone().attr("data-cellName", "reportedUnitOpinion")
        var cell14_1 = $td.clone().attr("colspan", "11").attr("data-cellName", "reportedUnitOpinion")
        line14.append(cell14_0).append(cell14_1)
        $table.append(line14)
        var line15 = $tr.clone()
        var cell15_0 = $th.clone().attr("data-cellName", "exportUnitOpinion")
        var cell15_1 = $td.clone().attr("colspan", "11").attr("data-cellName", "exportUnitOpinion")
        line15.append(cell15_0).append(cell15_1)
        $table.append(line15)
        var line16 = $tr.clone()
        var cell16_0 = $th.clone().attr("data-cellName", "hrOpinion")
        var cell16_1 = $td.clone().attr("colspan", "11").attr("data-cellName", "hrOpinion")
        line16.append(cell16_0).append(cell16_1)
        $table.append(line16)
        var line17 = $tr.clone()
        var cell17_0 = $th.clone().attr("data-cellName", "relevantDepartmentOpinion")
        var cell17_1 = $td.clone().attr("colspan", "11").attr("data-cellName", "relevantDepartmentOpinion")
        line17.append(cell17_0).append(cell17_1)
        $table.append(line17)
        var line18 = $tr.clone()
        var cell18_0 = $th.clone().attr("data-cellName", "bureauApprovalOpinion")
        var cell18_1 = $td.clone().attr("colspan", "11").attr("data-cellName", "bureauApprovalOpinion")
        line18.append(cell18_0).append(cell18_1)
        $table.append(line18)
        //

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
                                            var data = tabObjDatas.data[key];
                                            body["subTableObject"][key] = { subTablePrimaryIdName: tabItemj.tbIdName, subTableType: tabItemj.type, subTableDataObject: data }
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
                                                window.open(data)
                                                obj.close()
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
            tabCons[i] = $tabCon.detailLayer({ customBtnGroup: customBtnGroup, conditions: tabItem.conditions, $formTable: i === 0 ? $table : null })
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


