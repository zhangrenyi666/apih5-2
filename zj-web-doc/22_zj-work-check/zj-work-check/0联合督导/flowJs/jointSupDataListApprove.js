var flowId = l.getUrlParam('flowId') || "jointSupDataList";//流程模版id
var code = l.getUrlParam('code');
var type = l.getUrlParam('type'); //详情需要将所有按钮隐藏 detail edit
var dataListId = l.getUrlParam('id') || "";
Lny.setCookie('code', code, 30);

var $tabSystem = $("#tab-system")//模版顶级jq对象
var $tabBar = $('<div class="tabBar cl"></div>');//tab按钮控制条
var tabCons = []//tab内容页面组
var table, pagerDom, detailForm;
//根据flowId获取流程发起模版数据及数据
var flowFormJson = {
    name: "用印申请",
    titleName: "sealApplyName",
    tabs: [
        {
            name: "基本信息",
            type: "1",//普通tab页1，附件tab页2，列表tab页面3
            isMain: "1",//是否为主表
            tbName: "zjLhddJointSupDataList",//表名
            tbIdName: "dataListId",//表主键id
            conditions: [
                {
                    type: "hidden",//
                    name: "flowId",//
                    label: "flowId",//
                    placeholder: "请输入",
                    defaultValue: 'jointSupDataList',
                },
                {
                    type: "hidden",//
                    name: "dataListId",//
                    label: "dataListId",//
                    placeholder: "请输入",
                    defaultValue: dataListId,
                },

                {
                    type: "text",//五种形式：text,select,date,hidden,textarea,
                    name: "dataListNumber",//输入字段名
                    label: "资料清单编号",
                    placeholder: "请输入",
                    must: true,
                    immutable:dataListId ? true : false
                },
                {
                    type: "text",//
                    name: "checkDep",//
                    label: "检查部门",//
                    placeholder: "请输入",
                    must: true
                },
                {
                    type: "text",//
                    name: "unitName",//
                    label: "单位名称",//
                    placeholder: "请输入",
                    must: true
                }
            ]
        },
        { //详情弹窗 
            name: "检查项",
            type: "3",//table是表格
            tbName: "",
            tbIdName: "fileId",
        }
    ]
}

$.each(flowFormJson.tabs, function (i, tabItem) {//第一次遍历flowFormJson.tabs
    var $tabBtn = $('<span>' + tabItem.name + '</span>')//创建tab按钮$对象
    $tabBar.append($tabBtn)//向tab按钮控制条插入tab按钮
    var $tabCon = $('<div class="tabCon" id="tab' + i.toString() + '"></div>')//创建tab内容页面$对象
    var customBtnGroup;//tab内容页面中表单的底部按钮组配置
    if (tabItem.isMain) {//如果是主表单
        customBtnGroup = {
            btns: type === 'detail' ? [] : [
                {
                    name: "add",
                    label: '<i class="Hui-iconfont">&#xe600;</i> 确定'
                },
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
                    case 'add':
                        var body = {}
                        for (var j = 0; j < flowFormJson.tabs.length; j++) {//第二次遍历flowFormJson.tabs，为了让return起作用所以不用$.each而采用for循环

                            var tabItemj = flowFormJson.tabs[j]//模版中tabs数组的遍历元素数据对象
                            if (tabCons[j].getDatas) {

                                var tabObjDatas = tabCons[j].getDatas();//tab内容页面组的遍历对象获取数据对象
                                if (tabObjDatas.err && tabObjDatas.err.length) {//判断是否有错误（字段不能为空、超过个数限制等）
                                    layer.alert(tabObjDatas.err.join("<br/>"), { icon: 0, }, function (index) {
                                        $tabSystem.Huitab({
                                            index: j
                                        });
                                        layer.close(index);
                                    });
                                    return//直接停止for循环，且for循环之后的代码也不执行
                                }
                                body = tabObjDatas.data;
                            }
                        }
                        var apiName = dataListId ? 'updateZjLhddJointSupDataList' : 'addZjLhddJointSupDataList';
                        l.ajax(apiName, body, function (data, message, success) {
                            if (success) {
                                dataListId = data.dataListId;
                                pager.page('remote', { dataListId: dataListId })
                                layer.alert(message, { icon: 0, }, function (index) {
                                    parent.pager.page('remote');
                                    layer.close(index);
                                    $("#tab-system").Huitab({
                                        index: 1
                                    });
                                });
                            }
                        })
                        break;
                    case "launch":
                        if (!dataListId) {
                            layer.alert("请先提交基本信息！", { icon: 0, }, function (index) {
                                layer.close(index);
                                $("#tab-system").Huitab({
                                    index: 0
                                });
                            });
                            return;
                        }
                        var body = {
                            flowId: flowId,//流程id
							dataListId:dataListId
                        }
                        for (var j = 0; j < flowFormJson.tabs.length; j++) {//第二次遍历flowFormJson.tabs，为了让return起作用所以不用$.each而采用for循环
                            var tabItemj = flowFormJson.tabs[j]//模版中tabs数组的遍历元素数据对象
                            if (tabCons[j].getDatas) {
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
								tabObjDatas.data.dataListId=dataListId
                                    //给主表赋值
                                    body["mainTableName"] = tabItemj.tbName;
                                    body["mainTablePrimaryIdName"] = tabItemj.tbIdName;
                                    body["mainTableDataObject"] = tabObjDatas.data;


                                    body["apiBody"] = {}
                                    //add by lny on 717
                                    for (var key in tabObjDatas.data) {
                                        body["apiBody"][key] = tabObjDatas.data[key]
                                    }
                                    //add by lny on 717



                                    body["title"] = tabObjDatas.data[flowFormJson.titleName];
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



                                //add by lny on 717
                                body["apiName"] = "submitZjLhddJointSupDataList"//购置申请发起时调用
                                //add by lny on 717

                            }

                        }
                        //流程发起特殊代码---开始
                        layer.confirm("确定发起？", { icon: 0, }, function (index) {//流程发起请求
                            l.ajax("createOpenFlow", body, function (_d, _m, _s, _r) {
                                if (_s) {//发起成功，返回workId
                                    loadFlow(_d.flowButtons, {
                                        otherBody: {
                                            nodeId: _d.flowNode.nodeId,
                                            trackId: _d.flowNode.trackId,
                                            workId: _d.workId,
                                            flowVars: _d.flowVars,
                                            nodeVars: _d.nodeVars,
                                            apih5FlowStatus: _d.apih5FlowStatus,
                                            apiName: "submitZjLhddJointSupDataList",
                                            apiBody: { workId: _d.workId, apih5FlowStatus: _d.apih5FlowStatus, dataListId: dataListId }
                                        },
                                        endFn: function (buttonClass) { obj.close() },
                                        callback: function (flowObj) {
                                            flowObj.flowSelectOpen(0)
                                        }
                                    })
                                }
                            })
                            layer.close(index);
                        });
                        //流程发起特殊代码---结束
                        break;
                    case "cancel":
                    default:
                        obj.close()
                        break;
                }
            }
        }
    } else {
        customBtnGroup = {
            btns: [],
            callback: function (dataName, obj) {
            }
        }
    }
    if (tabItem.type === '3') {
        //表格
        var $con = $('<div class="page-container"></div>');//
        var $table = $('<table id="table" class="table table-border table-bordered table-bg table-hover"></table>')
        var $cl = $('<div class="cl"></div>');
        pagerDom = $('<div id="pager" class="m-pagination f-r"></div>')
        var $btnCon = $('<div class="f-l mt-10"></div>');
        var $add = $('<button data-name="add" data-type="TzLand" class="btn size-S S ml-10  btn-primary" type="button"><i class="Hui-iconfont">&#xe600;</i> 新增</button>');
        var $update = $('<button data-name="edit" data-type="TzLand" class="btn size-S S ml-10  btn-primary" type="button"><i class="Hui-iconfont">&#xe6df;</i> 编辑</button>');
        var $del = $('<button data-name="del" data-type="TzLand" class="btn size-S S ml-10  btn-primary" type="button"><i class="Hui-iconfont">&#xe6e2;</i> 删除</button>');
        // var $launch = $('<button data-name="launch" data-type="TzLand" class="btn size-S  ml-10 btn-primary" type="button"><i class="Hui-iconfont">&#xe603;</i> 发起</button>');
        if (type !== 'detail') {
            $btnCon.append($add);
            $btnCon.append($update);
            // $btnCon.append($launch);
            $btnCon.append($del);
        }

        $cl.append($btnCon);
        $cl.append(pagerDom);
        $con.append($table);
        $con.append($cl);

        table = $table.DataTable({
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
                    "data": "checkItem",//接口对应字段
                    "title": "检查项",//表头名
                    "defaultContent": "-",//默认显示
                },
                {
                    "targets": [3],//第几列
                    "data": "needDataList",//接口对应字段
                    "title": "所需资料清单",//表头名
                    "defaultContent": "-",//默认显示
                },
                {
                    "targets": [4],//第几列
                    "data": "checkItem",//接口对应字段
                    "title": "检查项编号",//表头名
                    "defaultContent": "-",//默认显示
                },
                {
                    "targets": [5],//第几列
                    "data": "createUserName",//接口对应字段
                    "title": "操作者",//表头名
                    "defaultContent": "-",//默认显示
                },
                {
                    "targets": [6],//第几列
                    "data": "createTime",//接口对应字段
                    "title": "更新时间",//表头名
                    "defaultContent": "-",//默认显示
                    "render": function (data) {
                        return data ? l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss") : "-";
                    }
                }
            ]
        });
        $tabCon.append($con);
        tabCons[i] = $tabCon
    } else {
        detailForm = tabCons[i] = $tabCon.detailLayer({ customBtnGroup: customBtnGroup, conditions: tabItem.conditions })
    }
    tabCons[i].close = function () {
        parent.layer.close(parent.myOpenLayer)
    }

})

$tabSystem.append($tabBar).append(tabCons).Huitab({
    index: 0
});

//检查项
var pager = pagerDom.page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjLhddJointSupCheckItemList"),
        params: {
            dataListId: dataListId
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
loadPage()
function loadPage() {
    if (dataListId) {
        l.ajax("getZjLhddJointSupDataListDetails", { dataListId: dataListId }, function (data, message, success) {
            if (success) {
                detailForm.setOpenData(data)
            }
        })
    } else {
        detailForm.setOpenData({ memberList: { oaMemberList: [] } })
    }
}

var detailLayer = $('#detailLayer').detailLayer({
    layerArea: ['70%', '90%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "dataListId",//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "checkItemId",//输入字段名
        },
        {
            type: "textarea",//
            name: "checkItem",//
            label: "检查项",//
            placeholder: "请输入检查项",
        },
        {
            type: "textarea",//
            name: "needDataList",//
            label: "所需资料清单",//
            placeholder: "请输入所需资料清单",
        }
    ],
    onSave: function (obj, _params) {
        l.ajax("updateZjLhddJointSupCheckItem", _params, function (data, message, success) {
            if (success) {
                pager.page('remote', { dataListId: dataListId })
                layer.alert(message, { icon: 0, }, function (index) {
                    layer.close(index);
                    obj.close()
                });
            }
        })
    },
    onAdd: function (obj, _params) {
        l.ajax("addZjLhddJointSupCheckItem", _params, function (data, message, success) {
            if (success) {
                pager.page('remote', { dataListId: dataListId })
                layer.alert(message, { icon: 0, }, function (index) {
                    layer.close(index);
                    obj.close()
                });
            }
        })
    },
})
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            if (dataListId) {
                detailLayer.open({ isAdd: true, dataListId: dataListId })
            } else {
                layer.alert("请先切换到基本信息完善必填信息点击确认后再新增检查项！", { icon: 0, }, function (index) {
                    layer.close(index);
                    $("#tab-system").Huitab({
                        index: 0
                    });
                });
            }
            break;
        case "edit":
            if (checkedData.length == 1) {
                detailLayer.open(checkedData[0])
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
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.confirm("确定删除？", { icon: 0, }, function (index) {
                    l.ajax("batchDeleteUpdateZjLhddJointSupCheckItem", checkedData, function (data, success, message) {
                        if (success) {
                            pager.page('remote')
                        }
                    })
                    layer.close(index);
                });
            }
            break;

        case 'launch':
            var body = {
                flowId: flowId//流程id
            }
            for (var j = 0; j < flowFormJson.tabs.length; j++) {//第二次遍历flowFormJson.tabs，为了让return起作用所以不用$.each而采用for循环
                var tabItemj = flowFormJson.tabs[j]//模版中tabs数组的遍历元素数据对象
                if (tabCons[j].getDatas) {
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
                        body["mainTableName"] = tabItemj.tbName;
                        body["mainTablePrimaryIdName"] = tabItemj.tbIdName;
                        body["mainTableDataObject"] = tabObjDatas.data;


                        body["apiBody"] = {}
                        //add by lny on 717
                        for (var key in tabObjDatas.data) {
                            body["apiBody"][key] = tabObjDatas.data[key]
                        }
                        //add by lny on 717



                        body["title"] = tabObjDatas.data[flowFormJson.titleName];
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



                    //add by lny on 717
                    body["apiName"] = "submitZjLhddJointSupDataList"//购置申请发起时调用
                    //add by lny on 717

                }


            }
            //流程发起特殊代码---开始
            layer.confirm("确定发起？", { icon: 0, }, function (index) {//流程发起请求
                l.ajax("createOpenFlow", body, function (_d, _m, _s, _r) {
                    if (_s) {//发起成功，返回workId
                        loadFlow(_d.flowButtons, {
                            otherBody: {
                                nodeId: _d.flowNode.nodeId,
                                trackId: _d.flowNode.trackId,
                                workId: _d.workId,
                                flowVars: _d.flowVars,
                                nodeVars: _d.nodeVars,
                                apih5FlowStatus: _d.apih5FlowStatus,
                                apiName: "updateFlowSealAfterSubmit",
                                apiBody: { workId: _d.workId, apih5FlowStatus: _d.apih5FlowStatus }
                            },
                            endFn: function (buttonClass) { obj.close() },
                            callback: function (flowObj) {
                                flowObj.flowSelectOpen(0)
                            }
                        })
                    }
                })
                layer.close(index);
            });
            //流程发起特殊代码---结束 
            break;
        case "close":
            parent.layer.close(parent.myOpenLayer)
            break;

    }
})

