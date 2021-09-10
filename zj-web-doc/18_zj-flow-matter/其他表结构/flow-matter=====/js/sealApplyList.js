var code = Lny.getUrlParam('code');
Lny.setCookie('code', code, 30);
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
            "targets": [2],
            "data": "applySequence",
            "title": '序号',
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "sealApplyName",//接口对应字段
            "title": "用印申请名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "sealType",//接口对应字段
            "title": "印别",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                var text = ""
                switch (data) {
                    case "0": text = "公章";
                        break;
                    case "1": text = "手签章";
                        break;
                    case "2": text = "人名章";
                        break;
                    default: text = "未知";
                        break;
                }
                return text
            }
        },
        {
            "targets": [5],//第几列
            "data": "content",//接口对应字段
            "title": "用印内容",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "useNumber",//接口对应字段
            "title": "用印次数",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "sealUnitId",//接口对应字段
            "title": "用印单位",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [8],//第几列
            "data": "sendUnitId",//接口对应字段
            "title": "发往单位",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [9],//第几列
            "data": "secretaryName",//接口对应字段
            "title": "部门文秘",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [10],//第几列
            "data": "legalName",//接口对应字段
            "title": "合法合规人员",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [11],//第几列
            "data": "createUserName",//接口对应字段
            "title": "创建者",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [12],//第几列
            "data": "createTime",//接口对应字段
            "title": "创建时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [13],//第几列
            "width": 80,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名
            "render": function (data) {
                var html = '';
                html += '<span class="dropDown">'
                html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">操作</a>';
                html += '<ul class="dropDown-menu menu radius box-shadow">'
                html += '<li><a href="javascript:void(0);" onclick="applyDetail(' + data.rowIndex + ')">详情</a></li>';
                if (data.loginIdentifyFlag == "0" || data.loginIdentifyFlag == "4" || data.loginIdentifyFlag == "5") {//是申请人或文秘或归档人员

                } else {
                    html += '<li><a href="javascript:void(0);" onclick="approval(' + data.rowIndex + ')">审批</a></li>';
                }
                html += '</ul></span>'
                return html;
            }
        }
    ]
});

var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "applySequence",//输入字段名
            label: "序号",//输入字段对杨的中文名称
            placeholder: "请输入序号",
        }
    ],
    onSearch: function (arr) {//搜索按钮回调
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
        pager.page('remote', 0, _params)
    },
    onReset: function (arr) {//重置按钮回调
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
        url: l.getApiUrl("getZjYySealApplyList"),
        params: {

        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                allData = data;
                console.log('搜索参数是：', data)
                if (result.data.length > 0) {
                    if (result.data[0].loginIdentifyFlag == 0) {//申请人 登录身份判断  //0:申请人 1:合法合规人员；2: 部门负责人 3：分管公司领导 4：部门文秘   5 :办公室归档人员  6：删除用印驳回的用印人   
                        $("#delete").hide();
                        $("#head").hide();
                        $("#leader").hide();
                        $("#apply").hide();
                    } else if (result.data[0].loginIdentifyFlag == 4) {//部门文秘   
                        $("#insert").hide();
                        $("#update").hide();
                        $("#delete").hide();
                        $("#office").hide();
                    } else if (result.data[0].loginIdentifyFlag == 1) {//合法合规人员
                        $("#insert").hide();
                        $("#update").hide();
                        $("#head").hide();
                        $("#leader").hide();
                        $("#delete").hide();
                        $("#office").hide();
                    } else if (result.data[0].loginIdentifyFlag == 6) {//删除用印驳回的用印人
                        $("#insert").hide();
                        $("#update").hide();
                        $("#head").hide();
                        $("#leader").hide();
                        $("#office").hide();
                        $("#apply").hide();
                    } else {//领导人或负责人
                        $("#insert").hide();
                        $("#update").hide();
                        $("#delete").hide();
                        $("#head").hide();
                        $("#leader").hide();
                        $("#office").hide();
                        $("#apply").hide();
                    }
                }
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

var addDetailLayer = $('#addDetailLayer').detailLayer({
    layerArea: ['100%', '100%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "sealApplyId",//输入字段名
        },
        {
            type: "text",//
            name: "sealApplyName",//
            label: "用印申请名称",//
            placeholder: "用印申请名称",
            must: true
        },
        {
            type: "select",
            name: "sealType",
            label: "印别",
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
            placeholder: "用印申请内容",
            must: true
        },
        {
            type: "number",//
            name: "useNumber",//
            label: "用印次数",//
            placeholder: "请输入内容",
            must: true
        },
        {
            type: "text",//
            name: "sealUnitId",//
            label: "发往单位",//
            placeholder: "发往单位",
            must: true
        },
        {
            type: "text",//
            name: "sendUnitId",//
            label: "用印单位",//
            placeholder: "用印单位",
            must: true
        },
        {
            type: "pickTree",
            name: "oaDepartmentSecretary",
            label: "部门文秘",
            must: true,
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            }
        },
        {
            type: "pickTree",
            name: "oaLegalComplianceOfficer",
            label: "合法合规人员",
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            }
        },
        {
            type: "textarea",//
            name: "remark",//
            label: "备注",//
            placeholder: "请输入备注",
        },
        {
            type: "upload",//
            name: "fileList",//
            label: "附件",//
            btnName: "添加附件",
            projectName: "zjSeal",
            fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls"]
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateZjDdSupervision', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjYySealApply', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    }
})
//部门文秘 给  部门负责人 发送消息方法弹窗
var sendHeadLayer = $('#sendHeadLayer').detailLayer({
    layerArea: ['710px', '410px'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "sealApplyId",//输入字段名
        },
        {
            type: "pickTree",
            name: "oaDepartmentHead",
            label: "部门负责人",
            must: true,
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            }
        }
    ],
    onSave: function (obj, _params) {
        _params.sendTypeFlag = '1'
        l.ajax('secretarySendSealApply', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        _params.sendTypeFlag = '1'
        l.ajax('secretarySendSealApply', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    }
})
//部门文秘 给 分管公司领导  发送消息方法弹窗
var sendLeaderLayer = $('#sendLeaderLayer').detailLayer({
    layerArea: ['710px', '410px'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "sealApplyId",//输入字段名
        },
        {
            type: "pickTree",
            name: "oaCompanyLeader",
            label: "分管公司领导",
            must: true,
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            }
        }
    ],
    onSave: function (obj, _params) {
        _params.sendTypeFlag = '2'
        l.ajax('secretarySendSealApply', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        _params.sendTypeFlag = '2'
        l.ajax('secretarySendSealApply', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    }
})
//申请人发送办公室方法弹窗
var sendOfficelLayer = $('#sendOfficelLayer').detailLayer({
    layerArea: ['710px', '410px'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "sealApplyId",//输入字段名
        },
        {
            type: "pickTree",
            name: "oaOffice",
            label: "办公室归档人员",
            must: true,
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            }
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('applySendSealApplyToOffice', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('applySendSealApplyToOffice', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    }
})
//审批方法弹窗
var approvalLayer = $('#approvalLayer').detailLayer({
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "sealApplyId",//输入字段名
        },
        {
            type: "select",
            name: "sealType",
            label: "印别",
            immutableAdd: true,
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
            placeholder: "用印申请内容",
            immutableAdd: true
        },
        {
            type: "number",//
            name: "useNumber",//
            label: "用印次数",//
            placeholder: "请输入内容",
            immutableAdd: true
        },
        {
            type: "text",//
            name: "sealUnitId",//接口字段名
            label: "发往单位",//
            immutableAdd: true
        },
        {
            type: "text",//
            name: "sendUnitId",//接口字段名
            label: "用印单位",//
            immutableAdd: true
        },
        {
            type: "text",
            name: "secretaryName",
            label: "部门文秘",
            immutableAdd: true
        },
        {
            type: "text",
            name: "legalName",
            label: "合法合规人员",
            immutableAdd: true
        },
        {
            type: "textarea",//
            name: "remark",//
            label: "备注",//
            placeholder: "请输入备注",
            immutableAdd: true
        },
        {
            type: "textarea",//
            name: "approvalOpinion",//
            label: "审批意见",//
            must: true,
            placeholder: "请输入备注",
        },
    ],
    onSave: function (obj, _params) {
        l.ajax('addZjYySealApproval', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjYySealApproval', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    }
})
//详情方法弹窗
var applyDetailLayer = $('#applyDetailLayer').detailLayer({
    layerArea: ['100%', '100%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "sealApplyId",//输入字段名
        },
        {
            type: "select",
            name: "sealType",
            label: "印别",
            immutableAdd: true,
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
            placeholder: "用印申请内容",
            immutableAdd: true
        },
        {
            type: "number",//
            name: "useNumber",//
            label: "用印次数",//
            placeholder: "请输入内容",
            immutableAdd: true
        },
        {
            type: "text",//
            name: "sealUnitId",//接口字段名
            label: "发往单位",//
            immutableAdd: true
        },
        {
            type: "text",//
            name: "sendUnitId",//接口字段名
            label: "用印单位",//
            immutableAdd: true
        },
        {
            type: "text",
            name: "secretaryName",
            label: "部门文秘",
            immutableAdd: true
        },
        {
            type: "text",
            name: "legalName",
            label: "合法合规人员",
            immutableAdd: true
        },
        {
            type: "textarea",//
            name: "remark",//
            label: "备注",//
            placeholder: "请输入备注",
            immutableAdd: true
        },
        {
            type: "textarea",//
            name: "headOpinion",//
            label: "负责人审批意见",//
            immutableAdd: true
        },
        {
            type: "textarea",//
            name: "leaderOpinion",//
            label: "领导人审批意见",//
            immutableAdd: true
        },
    ]
})
//审批弹窗
function approval(rowIndex) {
    var checkedData = table.row(rowIndex).data()
    console.log('搜索参数是：', checkedData)
    if (checkedData.legalComplianceOfficerResult == '1' && checkedData.loginIdentifyFlag == '1') {
        layer.alert("合法人员已经审批过", { icon: 0, }, function (index) {
            layer.close(index);
        });
    } else if (checkedData.departmentHeadResult == '1' && checkedData.loginIdentifyFlag == '2') {
        layer.alert("负责人已经审批过", { icon: 0, }, function (index) {
            layer.close(index);
        });
    } else if (checkedData.chargeOfCompanyResult == '1' && checkedData.loginIdentifyFlag == '3') {
        layer.alert("领导人已经审批过", { icon: 0, }, function (index) {
            layer.close(index);
        });
    } else {
        approvalLayer.open(checkedData);
        $(".layui-layer-title").html('审批');
    }
}
//详情弹窗
function applyDetail(rowIndex) {
    var checkedData = table.row(rowIndex).data()
    l.ajax("getSealApplyDetail", checkedData, function (res) {
        console.log(res)
        applyDetailLayer.open(res);
        $('#applyDetailLayer .btn').hide();
    })
}

$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            addDetailLayer.open();
            break;
        case "edit":
            if (checkedData.length == 1) {
                addDetailLayer.open(checkedData[0]);
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
                layer.confirm("确定删除？", { icon: 0, }, function (index) {
                    l.ajax("batchDeleteUpdateZjYySealApply", checkedData, function () {
                        pager.page('remote')
                    })
                    layer.close(index);
                });
            }
            break;
        case "sendToHead"://发送给负责人
            if (checkedData.length == 1) {
                if (checkedData[0].departmentHeadId == "") {
                    sendHeadLayer.open(checkedData[0]);
                } else {
                    layer.alert("已经发生给负责人了", { icon: 0, }, function (index) {
                        layer.close(index);
                    });
                }
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
        case "sendToLeader"://发送给领导
            if (checkedData.length == 1) {
                var sendTypeFlag = '2';
                if (checkedData[0].chargeOfCompanyId == "") {
                    sendLeaderLayer.open(checkedData[0]);
                } else {
                    layer.alert("已经发生给领导了", { icon: 0, }, function (index) {
                        layer.close(index);
                    });
                }
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
        case "sendToApply"://发送给经办人
            if (checkedData.length == 1) {
                if (checkedData[0].departmentHeadResult == "1" && checkedData[0].chargeOfCompanyResult == "1") {
                    if (checkedData[0].state == "1") {
                        layer.alert("已经发生给经办人了", { icon: 0, }, function (index) {
                            layer.close(index);
                        });
                    } else {
                        layer.confirm("确定发送给申请人？", { icon: 0, }, function (index) {
                            checkedData[0].sendTypeFlag = '0'
                            l.ajax("secretarySendSealApply", checkedData[0], function () {
                                pager.page('remote')
                            })
                            layer.close(index);
                        });
                    }
                } else {
                    layer.alert("还在审批中", { icon: 0, }, function (index) {
                        layer.close(index);
                    });
                }
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
        case "sendToOffice"://发送给办公室
            if (checkedData.length == 1) {
                if (checkedData[0].state == "1") {
                    sendOfficelLayer.open(checkedData[0]);
                } else {
                    layer.alert("该用印还在审批中", { icon: 0, }, function (index) {
                        layer.close(index);
                    });
                }
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
        case "approve":
            myOpen("流程发起", "flowIdZj1", "zjInfoNeedsConfirmApprove")
            break;
        case "process":
            myOpen("流程处理", "", "zjInfoNeedsConfirmProcess")
            // if (checkedData.length == 1) {
            //     myOpen("流程处理", checkedData[0].workId, "process")
            // } else if (checkedData.length == 0) {
            //     layer.alert("未选择任何项", { icon: 0, }, function (index) {
            //         layer.close(index);
            //     });
            // } else {
            //     layer.alert("只能选择一个", { icon: 0, }, function (index) {
            //         layer.close(index);
            //     });
            // }

            break;
    }
})

function myOpen1(index, type) {
    var rowData = allData[Number(index)];
    layer.confirm("确定发布？", { icon: 0, }, function (index) {
        l.ajax("sendSupervision", rowData, function () {
            pager.page('remote')
        })
        layer.close(index);
    });
}

var myOpenLayer;
function myOpen(title, id, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id + '&code=' + code
    }
    myOpenLayer = layer.open(options)
    layer.full(myOpenLayer)
}
