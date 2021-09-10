var flowId = "sanMinister";
var ministerApproveId = l.getUrlParam('ministerApproveId');
var code = l.getUrlParam('code');
Apih5.setCookie('code',code,30);
// updateZjHwSarsByCreate
var $tabSystem = $("#tab-system")//模版顶级jq对象
var $tabBar = $('<div class="tabBar cl"></div>');//tab按钮控制条
var tabCons = []//tab内容页面组
//根据flowId获取流程发起模版数据及数据
var flowFormJson = {
    name: "",
    tabs: []
}

var $tabTitle = $("#tab-title")//模版title
$tabTitle.html("三公司项目临聘人员审批申请")
switch (flowId) {
    case "sanMinister":
        flowFormJson = {//流程模版json（升级版：根据流程id动态获取，当前前台写死）
            name: "三公司项目临聘人员审批申请",
            titleName: "",
            tabs: [
                {
                    name: "基本信息",
                    type: "1",//普通tab页1，附件tab页2，列表tab页面3
                    isMain: "1",//是否为主表
                    tbName: "sanMinister",//表名
                    tbIdName: "ministerApproveId",//表主键id
                    conditions: [
                        {
                            type: "hidden",//
                            name: "ministerApproveId",//
                            label: "主键id",//
                            // defaultValue: ministerApproveId,
                            placeholder: ""
                        },
                        {
                            type: "text",//
                            name: "proName",//
                            label: "项目名称",//
                            placeholder: "请输入",
                            // defaultValue:data.proName,
                            must: true,
                            immutableAdd: true
                        },
                        {
                            type: "text",//
                            name: "proLeader",//
                            label: "项目负责人",//
                            placeholder: "请输入",
                            // defaultValue:data.proLeader,
                            must: true,
                            immutableAdd: true
                        },
                        {
                            type: "text",//
                            name: "ministerApproveTitle",//
                            label: "标题",//
                            // defaultValue:data.ministerApproveTitle,
                            placeholder: "请输入",
                            must: true,
                            immutableAdd: true
                        },
                        {
                            type: "upload",//
                            name: "fileList",//附件表名
                            label: "附件",//
                            btnName: "添加附件",
                            // defaultValue:data.fileList,
                            projectName: "zjSeal",
                            // immutableAdd: true,
                            uploadUrl: 'uploadFilesByFileName',
                            fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"],
                            immutableAdd: true
                        }
                        
                    ]
                }
            ]
        }
        break;
    default:
        break;
}
l.ajax("getZjFlowMinisterApproveDetail",{ ministerApproveId: ministerApproveId },function (data,message,success) {
    if (data) {
        $.each(flowFormJson.tabs,function (i,tabItem) {//第一次遍历flowFormJson.tabs
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
                    callback: function (dataName,obj) {
                        switch (dataName) {
                            case "launch":
                                var body = {
                                    flowId: flowId//流程id
                                }
                                for (var j = 0; j < flowFormJson.tabs.length; j++) {//第二次遍历flowFormJson.tabs，为了让return起作用所以不用$.each而采用for循环
                                    var tabItemj = flowFormJson.tabs[j]//模版中tabs数组的遍历元素数据对象
                                    var tabObjDatas = tabCons[j].getDatas();//tab内容页面组的遍历对象获取数据对象
                                    if (tabObjDatas.err.length) {//判断是否有错误（字段不能为空、超过个数限制等）
                                        layer.alert(tabObjDatas.err.join("<br/>"),{ icon: 0,},function (index) {
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
                                        //add by apih5 on 717
                                        for (var key in tabObjDatas.data) {
                                            body["apiBody"][key] = tabObjDatas.data[key]
                                        }
                                        //add by apih5 on 717 
                                        body["title"] = tabObjDatas.data['ministerApproveTitle'];

                                    } else if (tabItemj.type === "2") {//如果是附件类型子表，type==="2"
                                        //给子表赋值-附件表
                                        if (!body["subTableObject"]) { body["subTableObject"] = {} }
                                        for (var key in tabObjDatas.data) {
                                            var subTableDataObject = tabObjDatas.data[key];


                                            //add by apih5 on 717
                                            body["apiBody"][key] = tabObjDatas.data[key]
                                            //add by apih5 on 717


                                            body["subTableObject"][key] = { subTablePrimaryIdName: tabItemj.tbIdName,subTableType: tabItemj.type,subTableDataObject: subTableDataObject }
                                        }
                                    } else {//如果是普通类型子表，type==="1"，目前只有1和2
                                        //给子表赋值-普通表
                                        if (!body["subTableObject"]) { body["subTableObject"] = {} }
                                        //add by apih5 on 717
                                        for (var key in tabObjDatas.data) {
                                            body["apiBody"][key] = tabObjDatas.data[key]
                                        }
                                        //add by apih5 on 717
                                        body["subTableObject"][tabItemj.tbName] = { subTablePrimaryIdName: tabItemj.tbIdName,subTableType: tabItemj.type,subTableDataObject: tabObjDatas.data }
                                    }
                                    //add by apih5 on 717
                                    body["apiName"] = "updateZjFlowMinisterApproveForFlow"//购置申请发起时调用
                                    //add by apih5 on 717
                                }
                                //流程发起特殊代码---开始
                                layer.confirm("确定发起？",{ icon: 0,},function (index) {//流程发起请求
                                    l.ajax("createOpenFlow",body,function (_d,_m,_s,_r) {
                                        if (_s) {//发起成功，返回workId
                                            loadFlow(_d.flowButtons,{
                                                otherBody: {
                                                    title: body["title"],
                                                    flowId: flowId,//流程id
                                                    nodeId: _d.flowNode.nodeId,
                                                    trackId: _d.flowNode.trackId,
                                                    workId: _d.workId,
                                                    flowVars: _d.flowVars,
                                                    nodeVars: _d.nodeVars,
                                                    apih5FlowStatus: _d.apih5FlowStatus,
                                                    apiName: "updateZjFlowMinisterApproveForFlow",
                                                    apiBody: { workId: _d.workId,apih5FlowStatus: _d.apih5FlowStatus }
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
                    callback: function (dataName,obj) {
                    }
                }
            }
            tabCons[i] = $tabCon.detailLayer({ customBtnGroup: customBtnGroup,conditions: tabItem.conditions })
            tabCons[i].setOpenData(data)
            tabCons[i].close = function () {
                custom_close()
            }
        })

        $tabSystem.append($tabBar).append(tabCons).Huitab({
            index: 0
        });



    }
})




function custom_close() {
    if
        (confirm("您确定要关闭本页吗？")) {
        window.history.go(-1)
        // window.opener = null;
        // window.open('','_self');
        // window.close();
    }
    else { }
}