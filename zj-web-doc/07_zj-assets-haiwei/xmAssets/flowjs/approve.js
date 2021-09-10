var flowId = l.getUrlParam('id') || "zjXmZcBuy";//流程模版id
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
var $tabTitle = $("#tab-title")//模版title
$tabTitle.html("厦门资产购置申请")

switch (flowId) {
    case "zjXmZcBuy":
        flowFormJson = {//流程模版json（升级版：根据流程id动态获取，当前前台写死）
            name: "厦门资产购置申请",
            titleName: "zcmc",
            tabs: [
                {
                    name: "基本信息",
                    type: "1",
                    isMain: "1",
                    tbName: "zjHwzcZcgl",
                    tbIdName: "recordid",
                    conditions: [
                        {
                            type: "hidden",//
                            name: "recordid",//
                            label: "主键id",//
                            placeholder: "",
                            lineNum: 1
                        },
                        {
                            type: "hidden",//
                            name: "zcztdm",//
                            label: "资产状态代码（默认为未验收2）",//
                            defaultValue: "2",
                            placeholder: "",
                            lineNum: 1
                        },
                        {
                            type: "hidden",//
                            name: "czl",//
                            label: "残值率",//
                            defaultValue: "0",
                            placeholder: "",
                            lineNum: 1
                        },
                        {
                            type: "select",
                            name: "typeAssets",
                            label: "资产类别",
                            must: true,
                            selectList: [//当类型为select时，option配置  0:全公司  1：机关  2：项目
                                {
                                    value: "",
                                    text: "---"
                                },
                                {
                                    value: "0",
                                    text: "行政固定资产"
                                },
                                {
                                    value: "1",
                                    text: "信息化固定资产"
                                },
                                {
                                    value: "2",
                                    text: "实验器材、测量仪器"
                                }
                            ],
                            lineNum: 2
                        },
                        {
                            type: "pickTree",//
                            name: "oaSydw",//接口字段名
                            label: "使用单位",//
                            must: true,
                            pickType: {
                                department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
                                member: false,//成员列表对应接口字段名,false表示不开启该类
                            },
                            lineNum: 2
                        },
                        {
                            type: "date",//
                            name: "appTime",//
                            label: "申请时间",//
                            dateFmt: "yyyy-MM-dd",
                            defaultValue: new Date(),
                            id: "appTime",
                            lineNum: 2
                        },
                        {
                            type: "select",
                            name: "sszclx1",
                            label: "资产大类",
                            selectList: [//当类型为select时，option配置
                            ],
                            ajax: {
                                api: "getAssetsTypeTreeAllList",
                                child: "sszclx2",
                                valueName: "recordid",
                                textName: "assetTypeIdAndName",
                                childrenName: "currentList",
                            },
                            must: true,
                            lineNum: 3
                        },
                        {
                            type: "select",
                            name: "sszclx2",
                            label: "资产小类",
                            selectList: [//当类型为select时，option配置
                            ],
                            ajax: {
                                parent: "sszclx1",
                                valueName: "recordid",
                                textName: "assetTypeIdAndName",
                            },
                            must: true,
                            lineNum: 3
                        },
                        {
                            type: "text",//
                            name: "ggxh",//
                            label: "规格型号",//
                            placeholder: "请输入规格型号",
                            must: true,
                            lineNum: 3
                        },
                        {
                            type: "number",//
                            name: "quantity",//
                            label: "数量",//		    	                	
                            placeholder: "请输入数量",
                            defaultValue: "1",
                            must: true,
                            lineNum: 4
                        },
                        {
                            type: "text",//
                            name: "zcyz",//
                            label: "预估单价（元）",//
                            placeholder: "请输入预估单价（元）",
                            must: true,
                            lineNum: 4
                        },
                        {
                            type: "select",//
                            name: "glryid",//
                            label: "管理员",
                            must: true,
                            search: true,
                            placeholder: "请输入",
                            selectList: [//当类型为select时，option配置
                            ],
                            ajax: {
                                api: "getZjHwzcGlyList",
                                valueName: "recordid",
                                textName: "glyName",
                            },
                            lineNum: 4
                        },
                        {
                            type: "text",//
                            name: "brand",//
                            label: "厂家品牌",//
                            placeholder: "请输入厂家品牌",
                            lineNum: 5
                        },
                        // {
                        // 	type: "text",//
                        // 	name: "syr",//
                        // 	label: "使用人",//
                        // 	placeholder: "请输入使用人",
                        //     lineNum: 5
                        // },
                        {
                            type: "pickTree",
                            name: "oaSyr",
                            label: "使用人",
                            must: true,
                            pickType: {
                                department: false,//部门列表对应接口字段名,false表示不开启该类
                                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
                            },
                            lineNum: 5
                        },
                        {
                            type: "select",//
                            name: "sybmid",//
                            label: "使用部门",
                            search: true,
                            placeholder: "请输入使用部门",
                            selectList: [//当类型为select时，option配置
                            ],
                            ajax: {
                                api: "getDepartmentSelectAllList",
                                valueName: "recordid",
                                textName: "bmmc",
                            },
                            lineNum: 5
                        },
                        {
                            type: "textarea",//
                            name: "appReason",//
                            label: "申购原因",//
                            placeholder: "请输入申购原因",
                            must: true,
                            lineNum: 6
                        }
                    ]
                },
                {
                    name: "附件信息",
                    type: "2",
                    tbName: "",
                    tbIdName: "recordid",// fileId
                    conditions: [
                        {
                            type: "upload",//
                            name: "imageList",//
                            label: "附件",//
                            btnName: "添加附件",
                            projectName: "zj-xm-assets",
                            uploadUrl: 'uploadFilesByFileName',
                            fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
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
                            //当资产类型为电脑的时候使用人必填
                            if (tabObjDatas.data.sszclx1 === '1C02OTF0J0GK0D6C0B0A00009293C8D7' && tabObjDatas.data.sszclx1 === '1C02OTF0J0GK0D6C0B0A00009293C8D7') {
                                if (tabObjDatas.data.oaSyr === '') {
                                    layer.alert("该分类下使用人必填", { icon: 0 }, function (index) {
                                        layer.close(index);
                                    });
                                    return//直接停止for循环，且for循环之后的代码也不执行
                                }
                            }
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
                                body["apiTitle"] = "setZjXmzcFlowTitle";
                            } else if (tabItemj.type === "2") {//如果是附件类型子表，type==="2"
                                //给子表赋值-附件表
                                if (!body["subTableObject"]) { body["subTableObject"] = {} }
                                for (var key in tabObjDatas.data) {
                                    var subTableDataObject = tabObjDatas.data[key];
                                    body["apiBody"][key] = tabObjDatas.data[key]
                                    body["subTableObject"][key] = { subTablePrimaryIdName: tabItemj.tbIdName, subTableType: tabItemj.type, subTableDataObject: subTableDataObject }
                                }
                            } else {//如果是普通类型子表，type==="1"，目前只有1和2
                                //给子表赋值-普通表
                                if (!body["subTableObject"]) { body["subTableObject"] = {} }
                                for (var key in tabObjDatas.data) {
                                    body["apiBody"][key] = tabObjDatas.data[key]
                                }
                                body["subTableObject"][tabItemj.tbName] = { subTablePrimaryIdName: tabItemj.tbIdName, subTableType: tabItemj.type, subTableDataObject: tabObjDatas.data }
                            }
                            //add by lny on 717
                            body["apiName"] = "addZjHwzcZcgl"//购置申请发起时调用
                            //add by lny on 717
                        }
                        l.ajax("getZjHwzcZcglUser", body.apiBody, function (_d, _m, _s, _r) {
                            if (_s) {
                                //流程发起特殊代码---开始
                                layer.confirm("确定发起？", { icon: 0, }, function (index) {//流程发起请求
                                    l.ajax("createOpenFlow", body, function (_d, _m, _s, _r) {
                                        if (_s) {//发起成功，返回workId
                                            loadFlow(_d.flowButtons, {
                                                otherBody: {
                                                    title: _d["title"],
                                                    flowId: flowId,//流程id
                                                    nodeId: _d.flowNode.nodeId,
                                                    trackId: _d.flowNode.trackId,
                                                    workId: _d.workId,
                                                    flowVars: _d.flowVars,
                                                    nodeVars: _d.nodeVars,
                                                    apih5FlowStatus: _d.apih5FlowStatus,
                                                    apiName: "updateZjHwzcZcgl",
                                                    apiBody: { workId: _d.workId, apih5FlowStatus: _d.apih5FlowStatus, title: _d["title"] }
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
                            }
                        })
                        break;
                    case "cancel":
                        obj.close()
                        break;
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
        parent.pager.page('remote')
        parent.layer.close(parent.myOpenLayer)
    }
})
$tabSystem.append($tabBar).append(tabCons).Huitab({
    index: 0
});