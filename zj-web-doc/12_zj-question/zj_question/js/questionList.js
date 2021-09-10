var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
var table = $('#table').DataTable({
    "info": false,// 是否显示数据信息
    "paging": false,// 是否开启自带分页
    "ordering": false, // 是否开启DataTables的高度自适应
    "processing": false,// 是否显示‘进度’提示
    "searching": false,// 是否开启自带搜索
    "autoWidth": false,// 是否监听宽度变化
    "columnDefs": [// 表格列配置
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
            "targets": [2],// 第几列
            "data": "title",// 接口对应字段
            "title": "主题",// 表头名
            "defaultContent": "-",// 默认显示 
        },
        {
            "targets": [3],// 第几列
            "data": "flowType",// 接口对应字段
            "title": "流程类型",// 表头名
            "defaultContent": "-",// 默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "0":
                        r = "整改项"
                        break
                    case "1":
                        r = "建议项"
                        break
                }
                return r
            }
        },
        {
            "targets": [4],// 第几列
            "data": "project",// 接口对应字段
            "title": "项目",// 表头名
            "defaultContent": "-",// 默认显示 
        },
        {
            "targets": [5],// 第几列
            "data": "questionTitleName",// 接口对应字段
            "title": "检查项",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [6],// 第几列 
            "data": "questionDescribe",// 接口对应字段
            "title": "问题描述",// 表头名
            "defaultContent": "-",// 默认显示  
        },
        {
            "targets": [7],// 第几列
            "data": "questionState",// 接口对应字段
            "title": "整改进度",// 表头名
            "defaultContent": "-",// 默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "0":
                        r = "未提交"
                        break
                    case "1":
                        r = "初审中"
                        break
                    case "2":
                        r = "初审通过"
                        break
                    case "4":
                        r = "通过审核"
                        break
                    case "5":
                        r = "复审驳回"
                        break
                    case "6":
                        r = "复审驳回"
                        break
                    case "3":
                        r = "初审驳回"
                        break
                }
                return r
            }
        },

        {
            "targets": [8],// 第几列
            "data": "checkDepartmentName",// 接口对应字段
            "title": "检查部门",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [9],// 第几列
            "data": "createUserName",// 接口对应字段
            "title": "检查人",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [10],//第几列
            "data": "createTime",//接口对应字段
            "title": "创建时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [11],//第几列
            "width": 100,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名
            "render": function (data) {
                var html = '';
                html += '<span class="dropDown">'
                html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">操作</a>';
                html += '<ul class="dropDown-menu menu radius box-shadow">'
                //提问人				
                if (data.personnelFlag == "2") {
                    html += '<li><a onmouseenter="myOpens($(this))" onmouseleave="myOpena($(this))" href="javascript:void(0);" onclick="myOpen(\'详情\',\'' + data.recordid + '\',\'' + 'questionAskDetail' + '\')">详情</a></li>';
                    if (data.questionState == '2') {
                        html += '<li><a onmouseenter="myOpens($(this))" onmouseleave="myOpena($(this))" href="javascript:void(0);" onclick="myOpen(\'查看回复\',\'' + data.recordid + '\',\'' + 'checkAskDetial' + '\',\'' + data.projectId + '\')">查看回复</a></li>';
                    }
                } else
                    //整改人   				 
                    if (data.personnelFlag == "1") {
                        html += '<li><a onmouseenter="myOpens($(this))" onmouseleave="myOpena($(this))" href="javascript:void(0);" onclick="myOpen(\'详情\',\'' + data.recordid + '\',\'' + 'questionDetail' + '\')">详情</a></li>';
                        if (data.questionState == '0') {
                            if (data.flowType == "1") {
                                html += '<li><a onmouseenter="myOpens($(this))" onmouseleave="myOpena($(this))" href="javascript:void(0);" onclick="improvementQuestion(' + data.rowIndex + ')">回执</a></li>';
                            } else {
                                html += '<li><a onmouseenter="myOpens($(this))" onmouseleave="myOpena($(this))" href="javascript:void(0);" onclick="firstAnswerQuestion(' + data.rowIndex + ')">回复</a></li>';
                            }
                        } else if (data.questionState == '5') {
                            html += '<li><a onmouseenter="myOpens($(this))" onmouseleave="myOpena($(this))" href="javascript:void(0);" onclick="myOpen(\'查看问题\',\'' + data.recordid + '\',\'' + 'answerPeopleDetial' + '\')">查看问题</a></li>';
                        }
                    } else
                        //项目经理				 
                        if (data.personnelFlag == "0") {
                            html += '<li><a onmouseenter="myOpens($(this))" onmouseleave="myOpena($(this))" href="javascript:void(0);" onclick="myOpen(\'详情\',\'' + data.recordid + '\',\'' + 'questionDetail' + '\')">详情</a></li>';
                            if (data.questionState == '2' || data.questionState == '4') {//问题申请 ==2 已解决  4 问题关闭

                            } else if (data.questionState == '0') {	//问题申请 ==0 未解决 

                            } else {
                                html += '<li><a onmouseenter="myOpens($(this))" onmouseleave="myOpena($(this))" href="javascript:void(0);" onclick="myOpen(\'项目经理处理\',\'' + data.recordid + '\',\'' + 'leaderDetail' + '\')">项目经理处理</a></li>';
                            }
                        } else
                            //多重身份
                            if (data.personnelFlag == "4") {
                                html += '<li><a onmouseenter="myOpens($(this))" onmouseleave="myOpena($(this))" href="javascript:void(0);" onclick="myOpen(\'详情\',\'' + data.recordid + '\',\'' + 'questionDetail' + '\')">详情</a></li>';
                                // if (data.questionState == '0') {
                                    if (data.questionState == '0') {
                                        if (data.flowType == "1") {
                                            html += '<li><a onmouseenter="myOpens($(this))" onmouseleave="myOpena($(this))" href="javascript:void(0);" onclick="improvementQuestion(' + data.rowIndex + ')">回执</a></li>';
                                        } else {
                                            html += '<li><a onmouseenter="myOpens($(this))" onmouseleave="myOpena($(this))" href="javascript:void(0);" onclick="firstAnswerQuestion(' + data.rowIndex + ')">回复</a></li>';
                                        }
                                    } else if (data.questionState == '5') {
                                        html += '<li><a onmouseenter="myOpens($(this))" onmouseleave="myOpena($(this))" href="javascript:void(0);" onclick="myOpen(\'查看恢复\',\'' + data.recordid + '\',\'' + 'answerPeopleDetial' + '\')">查看回复</a></li>';
                                    } else if (data.questionState == '4' || data.questionState == '2') {//问题申请 ==2 已解决  4 问题关闭

                                    } else {
                                        html += '<li><a onmouseenter="myOpens($(this))" onmouseleave="myOpena($(this))" href="javascript:void(0);" onclick="myOpen(\'项目经理处理\',\'' + data.recordid + '\',\'' + 'leaderDetail' + '\')">项目经理处理</a></li>';
                                    }
                                // }
                            } else {
                                html += '<li><a onmouseenter="myOpens($(this))" onmouseleave="myOpena($(this))" href="javascript:void(0);" onclick="myOpen(\'详情\',\'' + data.recordid + '\',\'' + 'questionDetail' + '\')">详情</a></li>';
                            }
                html += '<li><a onmouseenter="myOpens($(this))" onmouseleave="myOpena($(this))" href="javascript:void(0);" onclick="myOpen(\'历史追溯\',\'' + data.recordid + '\',\'' + 'questionHistory' + '\')">历史追溯</a></li>';
                html += '</ul></span>'
                return html;
            }
        }
    ]
});
function myOpens(aa) {
    aa.css('backgroundColor', '#7EC0EE');

}
function myOpena(li) {
    li.css('backgroundColor', 'white');

}

//判断当前用户是否问题申请人员================只有问题申请人员能看见问题新增按钮========0810
l.ajax('judgeQuestionApplyPersonFlag', {}, function (res) {
    //console.log(res)
    if (res == "1") {
        //console.log("dasdasdsadasd")
        $("#addQuestion").hide();
    }
})
//判断当前用户是否管理员================只有管理员能看见删除按钮========0810
l.ajax('judgeQuestionApplyAdminFlag', {}, function (res) {
    //console.log(res)
    if (res == "1") {
        //console.log("dasdasdsadasd")
        $("#delete").hide();
    }
})

var form = $('#form').filterfrom({
    conditions: [// 表单项配置
        {
            type: "text",//
            name: "title",//
            label: "主题",//
            placeholder: "请输入问题标题",
        },
        // {
        //     type: "text",//
        //     name: "project",//
        //     label: "项目",//
        //     placeholder: "请输入项目名称",
        // },
        // {
        //     type: "text",//
        //     name: "departmentName",//
        //     label: "检查部门",//
        //     placeholder: "请输入检查部门",
        // },
        {
            type: "select",
            name: "flowType",
            label: "流程类型",
            selectList: [//当类型为select时，option配置  0：未审批 1：审批中  2：通过 3：未通过   4：问题关闭
                {
                    value: "",
                    text: "全部"
                },
                {
                    value: "0",
                    text: "整改项"
                },
                {
                    value: "1",
                    text: "建议项"
                }
            ],
        },        
        {
            type: "text",//
            name: "project",//
            label: "项目",//
            placeholder: "请输入项目名称",
        },
        {
            type: "text",//
            name: "departmentName",//
            label: "检查部门",//
            placeholder: "请输入检查部门",
        },
        {
            type: "text",//
            name: "createUserName",//
            label: "检查人",//
            placeholder: "请输入检查人",
        },
        {
            type: "select",
            name: "questionState",
            label: "整改进度",
            selectList: [//当类型为select时，option配置  0：未审批 1：审批中  2：通过 3：未通过   4：问题关闭
                {
                    value: "",
                    text: "全部"
                },
                {
                    value: "0",
                    text: "未提交"
                },
                {
                    value: "1",
                    text: "初审中"
                },
                {
                    value: "2",
                    text: "初审通过"
                },
                {
                    value: "3",
                    text: "初审驳回"
                },
                {
                    value: "4",
                    text: "通过审核"
                },
                {
                    value: "5",
                    text: "复审驳回"
                }
				/*,
                {
                    value: "6",
                    text: "复审驳回"
                }
				*/
            ],
        },
        {
            type: "text",//
            name: "questionTitleName",//
            label: "检查项",//
            placeholder: "请输入检查项",
        }
    ],
    onSearch: function (arr) {// 搜索按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].name == "startDate" || arr[i].name == "endDate") {
                if (arr[i].value) {
                    _params[arr[i].name] = l.dateFormat(new Date(new Date(arr[i].value).getTime()), "yyyy-MM-dd");
                } else {
                    _params[arr[i].name] = "";
                }
            } else {
                _params[arr[i].name] = arr[i].value;
            }
        }
        // console.log('搜索参数是：',_params)
        pager.page('remote', 0, _params)
    },
    onReset: function (arr) {// 重置按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
        pager.page('remote', _params)
    }
})

var allData;
var pager = $("#pager").page({
    remote: {// ajax请求配置
        url: l.getApiUrl("getZjQuestionApprovalList"),
        params: {
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                allData = data;
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

// 新增问题申请
// var addApprovalLayer = $('#addApprovalLayer').detailLayer({
//     layerArea: ['50%', '90%'],
//     conditions: [
//         {
//             type: "hidden",//
//             name: "questionId",//
//         },
//         {
//             type: "select",
//             name: "xzl",
//             label: "项目",
//             search: true,
//             selectList: [//当类型为select时，option配置
//             ],
//             ajax: {
//                 api: "getQuestionAllListByClassId",
//                 valueName: "classId",
//                 textName: "questionTitle"
//             },
//             must: true
//         },
//         {
//             type: 'date',//
//             name: "dateOfQuestion",//
//             label: "日期",//
//             dateFmt: "yyyy-MM-dd HH:mm:ss",
//             defaultValue: new Date(),
//             id: "dateOfQuestion",
//         },
//         {
//             type: "linkage",
//             name: "联动组",
//             children: {
//                 type: "selectSearch",
//                 name: "departmentId",
//                 label: "部门",
//                 placeholder: '请选择',
//                 ajax: {
//                     api: "getQuestionOneGradeSelectList",
//                     valueName: "department",
//                     textName: "departmentName",
//                     searchParamsField: 'departmentName' //搜索时给后台的搜索文字字段名
//                 },
//                 children: {
//                     type: "selectSearch",
//                     name: "questionId",
//                     label: "类别",
//                     placeholder: '请选择',
//                     immutableAdd: true,
//                     ajax: {
//                         api: "getQuestionTwoGradeSelectList",
//                         valueName: "recordid",
//                         textName: "questionTitle",
//                         searchParamsField: 'questionTitle', //搜索时给后台的字段
//                         parentParamsField: 'department', //搜索时给后台的父级值的字段名 
//                     },
//                     children: {
//                         type: "selectSearch",
//                         name: "questionClassId",
//                         label: "检查项",
//                         placeholder: '请选择',
//                         immutableAdd: true,
//                         ajax: {
//                             api: "getQuestionThreeGradeSelectList",
//                             valueName: "questionId",
//                             textName: "questionTitle",
//                             searchParamsField: 'questionTitle', //搜索时给后台的字段
//                             parentParamsField: 'recordid', //搜索时给后台的父级值的字段名 
//                             setChildrenValue: {//子集是非下拉时才能有此属性
//                                 api: "getQuestionFourGradeSelectList",
//                                 parentParamsField: 'questionId', //搜索时给后台的父级值的字段名 
//                                 textName: "questionDescribe",
//                             }
//                         },
//                         children: {
//                             type: "textarea",
//                             name: "questionDescribe",
//                             label: "问题描述",
//                             placeholder: '请输入',
//                             immutableAdd: true
//                         }
//                     }
//                 }
//             },
//         },
//         {
//             type: "upload",//
//             name: "imageListForPc",//
//             label: "问题图片",//
//             btnName: "添加图片",
//             projectName: "zj_question_picture",
//             fileType: ["jpg", "jpeg", "png", "gif"]
//         },
//         {
//             type: "pickTree",
//             name: "oaPersonInChargeUser",
//             label: "指定人",
//             pickType: {
//                 department: false,//部门列表对应接口字段名,false表示不开启该类
//                 member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
//             },
//         },
//         {
//             type: 'date',//
//             name: "answerClosingDate",//
//             label: "回答截止日期",//
//             dateFmt: "yyyy-MM-dd HH:mm:ss",
//             defaultValue: new Date(),
//             id: "answerClosingDate",
//             //immutableAdd:true
//         }
//     ],
//     onSave: function (obj, _params) {
//         if (_params.oaProject.oaDepartmentList.length > 1) {
//             layer.alert("项目只可选一个", { icon: 0 }, function (index) {
//                 layer.close(index);
//             });
//         } else if (_params.oaPersonInChargeUser.oaMemberList.length > 1) {
//             layer.alert("指定人只可选一个", { icon: 0 }, function (index) {
//                 layer.close(index);
//             });
//         } else {
//             l.ajax('updateZjQuestionApproval', _params, function (data) {
//                 pager.page('remote')
//                 obj.close()
//             })
//         }

//     },
//     onAdd: function (obj, _params) {
//         if (_params.oaProject.oaDepartmentList.length > 1) {
//             layer.alert("项目只可选一个", { icon: 0 }, function (index) {
//                 layer.close(index);
//             });
//         } else if (_params.oaPersonInChargeUser.oaMemberList.length > 1) {
//             layer.alert("指定人只可选一个", { icon: 0 }, function (index) {
//                 layer.close(index);
//             });
//         } else {
//             l.ajax('addZjQuestionApproval', _params, function (data) {
//                 pager.page('remote')
//                 obj.close()
//             })
//         }
//     }
// })
var improvementLayer = $('#improvementLayer').detailLayer({
    layerTitle:['详情'],
    // customBtnGroup:{btns:[{name:"onSave",label:"已阅"},{name:"close",label:"关闭"}],callback:function(){
    //     $('#improvementLayer').detailLayer({
    //         onSave:function(obj){
    //             // console.log(obj);
    //         }
    //     })
    // }},
    layerArea: ['100%', '100%'],
    conditions: [
        {
            type: "hidden",//
            name: "recordid",//
        },
        {
            type: "text",//
            name: "project",//
            label: "项目",//
            immutableAdd: true
        },
        {
            type: 'date',//
            name: "dateOfQuestion",//
            // label: "提问日期",//
            label: "问题提出时间",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "dateOfQuestion",
            immutableAdd: true
        },
        {
            type: "text",//
            name: "departmentName",//
            label: "部门",//
            immutableAdd: true
        },
        {
            type: "text",//
            name: "questionClassName",//
            label: "类别",//
            immutableAdd: true
        },
        {
            type: "text",//
            name: "questionTitleName",//
            label: "检查项",//
            immutableAdd: true
        },
        {
            type: "textarea",//textarea   text 
            name: "questionDescribe",//
            label: "问题描述",//
            immutableAdd: true,
            render: function (v) {
                // console.log(v)
            }
        },
        {
            type: "upload",//
            name: "imageListForPc",//
            label: "问题附件",//
            btnName: "添加",
            projectName: "zj_question_picture",
            //fileType: ["jpg", "jpeg", "png", "gif"],
            immutableAdd: true
        },
        {
            type: 'date',//
            name: "answerClosingDate",//
            label: "回答截止日期",//
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            id: "answerClosingDate",
            immutableAdd: true
        },
        {
            type: "text",//
            name: "createUserName",//
            label: "提问人",//
            immutableAdd: true
        },
        // {
        //     type: "textarea",//
        //     name: "askContent",//
        //     label: "问题回复",//
        //     placeholder: "请输入备注",
        //     must: true
        // },
        // {
        //     type: "upload",//
        //     name: "answerImageListForPc",//
        //     label: "回复图片",//
        //     btnName: "添加图片",
        //     projectName: "zj_question_picture",
        //     fileType: ["jpg", "jpeg", "png", "gif"]
        // },
        // {
        //     type: "select",
        //     name: "oaProjectManagerId",
        //     label: "项目经理",
        //     selectList: datas,
        //     must: true,
        // }
    ],
    onSave: function (obj, _params) {
        _params.askId = _params.recordid;
        _params.checkDepartmentName = _params.departmentName;
        _params.firstAnswerFlag = '0'
        l.ajax('updateQueImprovementItemByChanger', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        _params.askId = _params.recordid;
        _params.checkDepartmentName = _params.departmentName;
        _params.firstAnswerFlag = '0'
        l.ajax('updateQueImprovementItemByChanger', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    }
})
// 首次回答问题
var firstAnswerLayer;
function firstAnswerAddPersonnel(data) {
    var datas = [{
        value: "",//输入字段的值
        text: "请选择",//显示中文名
        selected: true//默认是否选中
    }];
    if (data.length) {
        for (var i = 0; i < data.length; i++) {
            datas.push({
                value: data[i].userid + "-" + data[i].name + "-" + data[i].orgId,
                text: data[i].name,
                selected: false
            })
        }
    }
    return firstAnswerLayer = $('#firstAnswerLayer').detailLayer({
        layerArea: ['100%', '100%'],
        conditions: [
            {
                type: "hidden",//
                name: "recordid",//
            },
            {
                type: "text",//
                name: "project",//
                label: "项目",//
                immutableAdd: true
            },
            {
                type: 'date',//
                name: "dateOfQuestion",//
                // label: "提问日期",//
                label: "问题提出时间",//                
                dateFmt: "yyyy-MM-dd HH:mm:ss",
                defaultValue: new Date(),
                id: "dateOfQuestion",
                immutableAdd: true
            },
            {
                type: "text",//
                name: "departmentName",//
                label: "部门",//
                immutableAdd: true
            },
            {
                type: "text",//
                name: "questionClassName",//
                label: "类别",//
                immutableAdd: true
            },
            {
                type: "text",//
                name: "questionTitleName",//
                label: "检查项",//
                immutableAdd: true
            },
            {
                type: "textarea",//textarea   text 
                name: "questionDescribe",//
                label: "问题描述",//
                immutableAdd: true,
                render: function (v) {
                    // console.log(v)
                }
            },
            {
                type: "upload",//
                name: "imageListForPc",//
                label: "问题附件",//
                btnName: "添加",
                projectName: "zj_question_picture",
                //fileType: ["jpg", "jpeg", "png", "gif"],
                immutableAdd: true
            },
            {
                type: 'date',//
                name: "answerClosingDate",//
                label: "回答截止日期",//
                dateFmt: "yyyy-MM-dd HH:mm:ss",
                defaultValue: new Date(),
                id: "answerClosingDate",
                immutableAdd: true
            },
            {
                type: "text",//
                name: "createUserName",//
                label: "提问人",//
                immutableAdd: true
            },
            {
                type: "textarea",//
                name: "askContent",//
                label: "问题回复",//
                placeholder: "请输入备注",
                must: true
            },
            {
                type: "upload",//
                name: "answerImageListForPc",//
                label: "回复附件",//
                btnName: "添加",
                projectName: "zj_question_picture",
                //fileType: ["jpg", "jpeg", "png", "gif"]
            },
            {
                type: "select",
                name: "oaProjectManagerId",
                label: "项目经理",
                selectList: datas,
                must: true,
            }
        ],
        onSave: function (obj, _params) {
            _params.askId = _params.recordid;
            _params.checkDepartmentName = _params.departmentName;
            _params.firstAnswerFlag = '0'
            l.ajax('addZjQuestionAnswer', _params, function (data) {
                pager.page('remote')
                obj.close()
            })
        },
        onAdd: function (obj, _params) {
            _params.askId = _params.recordid;
            _params.checkDepartmentName = _params.departmentName;
            _params.firstAnswerFlag = '0'
            l.ajax('addZjQuestionAnswer', _params, function (data) {
                pager.page('remote')
                obj.close()
            })
        }
    })
}

$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    var params;
    switch (name) {
        case "add":
            myOpenAdd('问题整改---新增', "", 'batchAllQuestion')
            break;
        case "del":
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.confirm("确定删除？", { icon: 0, }, function (index) {
                    l.ajax("batchDeleteUpdateZjQuestionApprovalByAdmin", checkedData, function () {
                        pager.page('remote')
                    })
                    layer.close(index);
                });
            }
            break;
    }
})
//首次回答问题
function improvementQuestion(rowIndex) {
    var checkedData = table.row(rowIndex).data();
    improvementLayer.open(checkedData);        
    // console.log($('#improvementLayer').detailLayer());
}
//首次回答问题
function firstAnswerQuestion(rowIndex) {
    var checkedData = table.row(rowIndex).data();
    l.ajax("getZjQuestionPersonnelAllListByProject", { project: checkedData.projectId }, function (data, message, success) {
        if (success) {
            firstAnswerLayer = firstAnswerAddPersonnel(data);
            firstAnswerLayer.open(checkedData); 
        }
    });
    // $(".layui-layer-title").html('整改回复');
    $(".layui-layer-title").html('回执');
}
function myOpen(title, id, url, projectId) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id + "&code=" + code + "&projectId=" + projectId
    }
    layer.full(layer.open(options))
}
function myOpenAdd(title, id, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id
    }
    myOpenLayer = layer.open(options)
    layer.full(myOpenLayer)
}
function refeash(obj) {
    layer_close()
}


