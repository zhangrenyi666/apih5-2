var table = $('#table').DataTable({
    "info": false,//是否显示数据信息
    "paging": false,//是否开启自带分页
    "ordering": false, //是否开启DataTables的高度自适应
    "processing": false,//是否显示‘进度’提示
    "searching": false,//是否开启自带搜索
    "autoWidth": false,//是否监听宽度变化
    "columnDefs": [//表格列配置
        {
            "targets": [0],
            "data": "rowIndex",
            "width": 13,
            "title": '<input type="checkbox">',
            "render": function (data) {
                return '<input type="checkbox" name="checkbox" data-rowIndex="' + data + '" >';
            }
        },
        {
            "targets": [1],
            "data": "rowIndex",
            "title": 'No.',
            "width": 25,
            "render": function (data) {
                return data + 1
            }
        },
        {
            "targets": [2],//第几列
            "data": "reportedUnit",//接口对应字段
            "title": "上报单位",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "reportedTime",//接口对应字段
            "title": "上报时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [4],//第几列
            "data": "name",//接口对应字段
            "title": "姓名",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "sex",//接口对应字段
            "title": "性别",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                }
                return r
            }
        },
        {
            "targets": [6],//第几列
            "data": "birth",//接口对应字段
            "title": "出生年月",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [7],//第几列
            "data": "nation",//接口对应字段
            "title": "民族",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                }
                return r
            }
        },
        {
            "targets": [8],//第几列
            "data": "degreeOfEducation",//接口对应字段
            "title": "文化程度",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                }
                return r
            }
        },
        {
            "targets": [9],//第几列
            "data": "maritalStatus",//接口对应字段
            "title": "婚姻状态",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                }
                return r
            }
        },
        {
            "targets": [10],//第几列
            "data": "joinWorkTime",//接口对应字段
            "title": "参加工作时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd");
            }
        },
        {
            "targets": [11],//第几列
            "data": "politicsStatus",//接口对应字段
            "title": "政治面貌",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                }
                return r
            }
        },
        {
            "targets": [12],//第几列
            "data": "nativePlace",//接口对应字段
            "title": "籍贯",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [13],//第几列
            "data": "professionalQualification",//接口对应字段
            "title": "职业资格",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [14],//第几列
            "data": "duty",//接口对应字段
            "title": "职务",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [15],//第几列
            "data": "professionalTitle",//接口对应字段
            "title": "职称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [16],//第几列
            "data": "currentWorkUnit",//接口对应字段
            "title": "现工作单位",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [17],//第几列
            "data": "adjustUnit",//接口对应字段
            "title": "拟调单位",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [18],//第几列
            "data": "domicilePlace",//接口对应字段
            "title": "户口所在地",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [19],//第几列
            "data": "familyAddress",//接口对应字段
            "title": "家庭住址",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [20],//第几列
            "data": "originalEducationStartTime",//接口对应字段
            "title": "开始时间（原始学历）",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd");
            }
        },
        {
            "targets": [21],//第几列
            "data": "originalEducationEndTime",//接口对应字段
            "title": "结束时间（原始学历）",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd");
            }
        },
        {
            "targets": [22],//第几列
            "data": "originalGraduateSchool",//接口对应字段
            "title": "毕业院校（原始学历）",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [23],//第几列
            "data": "originalMajor",//接口对应字段
            "title": "专业（原始学历）",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [24],//第几列
            "data": "originalEductionalSystme",//接口对应字段
            "title": "学制（原始学历）",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [25],//第几列
            "data": "originalDegree",//接口对应字段
            "title": "学位（原始学历）",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [26],//第几列
            "data": "originalSepFlag",//接口对应字段
            "title": "是否统分（原始学历）",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                }
                return r
            }
        },
        {
            "targets": [27],//第几列
            "data": "highestEducationStarTime",//接口对应字段
            "title": "开始时间（最高学历）",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd");
            }
        },
        {
            "targets": [28],//第几列
            "data": "highestEducationEndTime",//接口对应字段
            "title": "结束时间（最高学历）",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd");
            }
        },
        {
            "targets": [29],//第几列
            "data": "highestGraduateSchool",//接口对应字段
            "title": "毕业院校（最高学历）",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [30],//第几列
            "data": "highestMajor",//接口对应字段
            "title": "专业（最高学历）",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [31],//第几列
            "data": "highestEducationSystem",//接口对应字段
            "title": "学制（最高学历）",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [32],//第几列
            "data": "highestDegree",//接口对应字段
            "title": "学位（最高学历）",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [33],//第几列
            "data": "highestSepFlag",//接口对应字段
            "title": "是否统分（最高学历）",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                }
                return r
            }
        },
        {
            "targets": [34],//第几列
            "data": "jobResume",//接口对应字段
            "title": "工作简历",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [35],//第几列
            "data": "transferReason",//接口对应字段
            "title": "调动原因",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [36],//第几列
            "data": "adjustPost",//接口对应字段
            "title": "拟调岗位",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [37],//第几列
            "data": "loverName",//接口对应字段
            "title": "爱人姓名",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [38],//第几列
            "data": "loverWorkUnit",//接口对应字段
            "title": "爱人工作单位",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [39],//第几列
            "data": "loverDomicilePlace",//接口对应字段
            "title": "爱人户口所在地",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [40],//第几列
            "data": "followFlag",//接口对应字段
            "title": "是否随调",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                }
                return r
            }
        },
        {
            "targets": [41],//第几列
            "data": "familyMembers",//接口对应字段
            "title": "家庭主要成员",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [42],//第几列
            "data": "reportedUnitOpinion",//接口对应字段
            "title": "上报单位意见",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [43],//第几列
            "data": "exportUnitOpinion",//接口对应字段
            "title": "调出单位意见",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [44],//第几列
            "data": "hrOpinion",//接口对应字段
            "title": "人力资源意见",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [45],//第几列
            "data": "relevantDepartmentOpinion",//接口对应字段
            "title": "相关部门意见",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [46],//第几列
            "data": "bureauApprovalOpinion",//接口对应字段
            "title": "局审批意见",//表头名
            "defaultContent": "-",//默认显示
        }
    ]
});
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjPersonalInnerTransferList"),
        params: {
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                $.each(data, function (index, item) {
                    item.rowIndex = index
                })
                table.clear().rows.add(data).draw();
            } else {
                layer.alert(result.message, { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
        },
    }
});
var detailLayer = $('#detailLayer').detailLayer({
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
    ],
    onSave: function (obj, _params) {
        l.ajax('updateZjPersonalInnerTransfer', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjPersonalInnerTransfer', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    }
})
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            detailLayer.open();
            break;
        case "edit":
            if (checkedData.length == 1) {
                detailLayer.open(checkedData[0]);
            } else if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.alert("只能选择一个", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
            break;
        case "del":
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
                l.delTableRow("innerTransferId", 'sxAqJsonNodeList', 'batchDeleteZjPersonalInnerTransfer', checkedData, function (data) {
                    pager.page('remote')
                })//  删除url地址
            }
            break;
    }
})
function myOpen(title, id, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id
    }
    layer.full(layer.open(options))
}
function public(voteId) {//发布
    l.ajax('sendVoteInfo', { voteId: voteId }, function (data, message) {
        layer.alert(message, { icon: 0, }, function (index) {
            layer.close(index);
            pager.page('remote')
        });
    })
}
function preview(voteId) {//预览
    window.open(l.domainName + 'initMVotePreview.do?voteId=' + voteId + '&previewFlag=1', 'newwindow', 'height=500,width=350,top=100,left=500,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no')
}
