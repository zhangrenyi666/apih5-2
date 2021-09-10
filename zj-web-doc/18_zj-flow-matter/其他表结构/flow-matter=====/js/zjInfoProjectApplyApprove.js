var flowId = l.getUrlParam('id') || "";//流程模版id
var code = l.getUrlParam('code');
Lny.setCookie('code', code, 30);

var $tabSystem = $("#tab-system")//模版顶级jq对象
var $tabBar = $('<div class="tabBar cl"></div>');//tab按钮控制条
var tabCons = []//tab内容页面组
//根据flowId获取流程发起模版数据及数据
var flowFormJson = {
    name: "",
    tabs: []
}

switch (flowId) {
    case "flowId2":
        flowFormJson = {//流程模版json（升级版：根据流程id动态获取，当前前台写死）
            name: "信息化立项申请",
            titleName: "projectTitle",
            tabs: [
                {
                    name: "基本信息",
                    type: "1",
                    isMain: "1",
                    tbName: "zjInfoProjectApply",
                    tbIdName: "projectApplyId",
                    conditions: [
                        {
                            type: "hidden",//五种形式：text,select,date,hidden,textarea,
                            name: "projectApplyId",//输入字段名
                        },
                        {
                            type: "text",//
                            name: "projectTitle",//
                            label: "标题",//
                            placeholder: "请输入标题",
                            must: true
                        },
                        {
                            type: "textarea",//
                            name: "projectcontentPoints",//
                            label: "内容要点",//
                            placeholder: "请输入内容要点",
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
                            name: "zjInfoProjectApplyFile",//
                            label: "附件1",//
                            btnName: "添加附件",
                            projectName: "zjInfoProjectApply",
                            fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls"]
                        }
                    ]
                }
            ]
        }
        break;
    default:
        break;
}




$.each(flowFormJson.tabs, function (i, tabItem) {//第一次遍历flowFormJson.tabs
    var $tabBtn = $('<span>' + tabItem.name + '</span>')//创建tab按钮$对象
    $tabBar.append($tabBtn)//向tab按钮控制条插入tab按钮
    var $tabCon = $('<div class="tabCon" id="tab' + i.toString() + '"></div>')//创建tab内容页面$对象
    var customBtnGroup;//tab内容页面中表单的底部按钮组配置
    if (tabItem.isMain) {//如果是主表单
        customBtnGroup = {
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
                        var body = {
                            flowId: flowId//流程id
                        }
                        for (var j = 0; j < flowFormJson.tabs.length; j++) {//第二次遍历flowFormJson.tabs，为了让return起作用所以不用$.each而采用for循环
                            var tabItemj = flowFormJson.tabs[j]//模版中tabs数组的遍历元素数据对象
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
                                body["title"] = tabObjDatas.data[flowFormJson.titleName];
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
                        //流程发起特殊代码---开始
                        layer.confirm("确定发起？", { icon: 0, }, function (index) {//流程发起请求
                            l.ajax("startFlow", body, function (_d, _m, _s, _r) {
                                if (_s) {//发起成功，返回workId
                                    var submitBody = _d
                                    var nextShowFlowInfoList = _d.buttonList[0].nextShowFlowInfoList//下一流程可选支线数组
                                    submitBody["buttonId"] = _d.buttonList[0].buttonId//发起的操作id
                                    var reviewPickType = _d.buttonList[0].reviewPickType//选人组件配置
                                    var reviewUserObject = _d.buttonList[0].reviewUserObject//选人组建默认数据
                                    var reviewObjectDisableFlag = _d.buttonList[0].reviewObjectDisableFlag//选人组建是否禁用
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
                                                submitBody["reviewNodeId"] = _params.reviewNodeId
                                                submitBody["reviewUserObject"] = _params.reviewUserObject
                                                l.ajax('submitFlow', submitBody, function (data) {
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
                                        // parent.pager.page('remote')
                                        obj.close()
                                    }

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
    tabCons[i] = $tabCon.detailLayer({ customBtnGroup: customBtnGroup, conditions: tabItem.conditions })
    tabCons[i].setOpenData()
    tabCons[i].close = function () {
        parent.layer.close(parent.myOpenLayer)
    }
})


$tabSystem.append($tabBar).append(tabCons).Huitab({
    index: 0
});


