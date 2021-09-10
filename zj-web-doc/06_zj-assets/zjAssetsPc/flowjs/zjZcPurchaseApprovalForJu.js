var flowId = l.getUrlParam('id') || "zjZcBuyForJu";//流程模版id
var recordid = l.getUrlParam("id") || "";//主键id
var code = l.getUrlParam('code');
Lny.setCookie('code', code, 30);
var $tabSystem = $("#tab-system")//模版顶级jq对象
var $tabBar = $('<div class="tabBar cl"></div>');//tab按钮控制条
var tabCons = []//tab内容页面组
var table, pagerDom, detailForm;
var $tabTitle = $("#tab-title")//模版title
$tabTitle.html("局资产购置申请")

//根据flowId获取流程发起模版数据及数据
var flowFormJson = {//流程模版json（升级版：根据流程id动态获取，当前前台写死）
    name: "局资产购置申请",
    titleName: "zcmc",
    tabs: [
        {
            name: "基本信息",
            type: "1",
            isMain: "1",
            tbName: "zjZcManage",
            tbIdName: "recordid",
            conditions: [
                {
                    type: "hidden",//
                    name: "recordid",//
                    label: "主键id",//
                    placeholder: "",
                },
                {
                    type: "select",
                    name: "sszclx1",
                    label: "资产大类",
                    ajax: {
                        api: "getAssetsTypeTreeAllList",
                        child: "sszclx2",
                        valueName: "recordid",
                        textName: "assetTypeIdAndName",
                        childrenName: "currentList",
                    },
                    must: true
                },
                {
                    type: "select",
                    name: "sszclx2",
                    label: "资产小类",
                    search: true,
                    ajax: {
                        parent: "sszclx1",
                        valueName: "recordid",
                        textName: "assetTypeIdAndName",
                    },
                    must: true
                },
                {
                    type: "text",//
                    name: "zcmc",//
                    label: "资产名称",//
                    placeholder: "请输入资产名称",
                    must: true
                },
                {
                    type: "text",//
                    name: "zcyz",//
                    label: "资产原值（元）",//
                    placeholder: "请输入资产原值（元）",
                    must: true
                },
                {
                    type: "select",
                    name: "czlId",
                    label: "残值率（%）",
                    selectList: [//当类型为select时，option配置
                    ],
                    ajax: {
                        api: "getCzlSelectAllList",
                        valueName: "dmbh",
                        textName: "dmnr"
                    },
                    must: true
                },
                {
                    type: "select",
                    name: "synxId",
                    label: "使用年限（年）",
                    selectList: [//当类型为select时，option配置
                    ],
                    ajax: {
                        api: "getZjZcDurableYearList",
                        valueName: "durableYearId",
                        textName: "year"
                    },
                    must: true
                },
                {
                    type: "number",//
                    name: "bxts",//
                    label: "保修天数（月）",//
                    placeholder: "请输入保修天数（月）",
                    must: true,
                    max: 9999999999
                },
                {
                    type: "text",//
                    name: "ggxh",//
                    label: "规格型号",//
                    placeholder: "请输入规格型号",
                    must: true
                },
                {
                    type: "date",//
                    name: "grrq",//
                    label: "购入日期",//
                    dateFmt: "yyyy-MM-dd",
                    defaultValue: new Date(),
                    id: "grrq",
                    must: true
                },
                {
                    type: "select",
                    name: "grfsdm",
                    label: "购入方式",
                    selectList: [//当类型为select时，option配置
                    ],
                    ajax: {
                        api: "getBuyMannerSelectAllList",
                        valueName: "dmbh",
                        textName: "dmnr"
                    },
                },
                {
                    type: "hidden",//
                    name: "cgbh",//
                    label: "采购编号",//
                    placeholder: "请输入采购编号",
                },
                {
                    type: "pickTree",
                    name: "glrymc",
                    label: "管理员",
                    pickType: {
                        department: false,//部门列表对应接口字段名,false表示不开启该类
                        member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
                    }
                },
                {
                    type: "select",
                    name: "cqdwid",
                    label: "产权单位",
                    selectList: [//当类型为select时，option配置
                    ],
                    ajax: {
                        api: "getUnitSelectAllList",
                        valueName: "dwbh",
                        textName: "dwmc"
                    },
                    must: true
                },
                {
                    type: "pickTree",//
                    name: "oaSydw",//接口字段名
                    label: "使用单位",//
                    // must: true,
                    pickType: {
                        department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
                        member: false,//成员列表对应接口字段名,false表示不开启该类
                    }
                },
                {
                    type: "select",//
                    name: "sybmid",//接口字段名
                    label: "使用部门",//
                    selectList: [//当类型为select时，option配置
                    ],
                    ajax: {
                        api: "getDepartmentSelectAllList",
                        valueName: "bmbh",
                        textName: "bmmc"
                    }
                },
                {
                    type: "select",
                    name: "cfdd1",
                    label: "存放地点",
                    selectList: [//当类型为select时，option配置
                    ],
                    ajax: {
                        api: "getAssetsPlaceAllList",
                        valueName: "recordid",
                        textName: "cfddmc"
                    },
                },
                {
                    type: "text",//
                    name: "syr",//接口字段名
                    label: "使用人",//
                    placeholder: "请输入使用人",
                },
                {
                    type: "date",//
                    name: "scrq",//
                    label: "出厂日期",//
                    dateFmt: "yyyy-MM-dd",
                    defaultValue: new Date(),
                    id: "scrq"
                },
                {
                    type: "text",//
                    name: "serialNumber",//
                    label: "出厂编号",//
                    placeholder: "请输入出厂编号"
                },              
                {
                    type: "linkage",
                    name: "联动组",
                    children: {
                        type: "selectSearch",
                        name: "gysId",
                        label: "供应商",
                        placeholder: '请选择',
                        ajax: {
                            api: "getZjSupplierList",
                            valueName: "recordid",
                            textName: "gysmc",
                            searchParamsField: 'gysmc' //搜索时给后台的搜索文字字段名
                        },
                        children: {
                            type: "selectSearch",
                            name: "rangeId",
                            label: "供应范围",
                            placeholder: '请选择',
                            immutableAdd: true,  
                            ajax: {
                                api: "getZjZcSupplyRangeList",
                                valueName: "recordid",
                                textName: "supplyGoodsName",
                                searchParamsField: 'supplyGoodsName', //搜索时给后台的字段
                                parentParamsField: 'supplierId' //搜索时给后台的父级值的字段名                                
                            },
                            children: {
                                type: "selectSearch",
                                name: "brandId",
                                label: "品牌",
                                placeholder: '请选择',
                                immutableAdd: true,
                                ajax: {
                                    api: "getZjBrandList",
                                    valueName: "recordid",
                                    textName: "brandName"                                  
                                }
                            }
                        }
                    },
                },          
                {
                    type: "textarea",//
                    name: "bz",//
                    label: "备注",//
                    placeholder: "请输入备注",
                },
                {
                    type: "upload",//
                    name: "imageList",//
                    label: "资产图片",//
                    btnName: "添加图片",
                    projectName: "zj-assets",
                    uploadUrl: 'uploadFilesByFileName',
                    fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf"]
                }
            ]
        },
        {
            //子资产清单
            name: "子资产清单",
            type: "3", //table是表格
            tbName: "",
            tbIdName: "fileId"
        }
    ]
};

$.each(flowFormJson.tabs, function (i, tabItem) {//第一次遍历flowFormJson.tabs
    var $tabBtn = $('<span>' + tabItem.name + '</span>')//创建tab按钮$对象
    $tabBar.append($tabBtn)//向tab按钮控制条插入tab按钮
    var $tabCon = $('<div class="tabCon" id="tab' + i.toString() + '"></div>')//创建tab内容页面$对象
    var customBtnGroup;//tab内容页面中表单的底部按钮组配置
    if (tabItem.isMain) {//如果是主表单
        customBtnGroup = {
            btns: [
                {
                    name: "add",
                    label: '<i class="Hui-iconfont">&#xe600;</i> 确定'
                },
                {
                    name: "launch",
                    label: '<i class="Hui-iconfont">&#xe603;</i> 发起'
                }
            ],
            callback: function (dataName, obj) {
                switch (dataName) {
                    case "add":
                        var body = {};
                        for (var j = 0; j < flowFormJson.tabs.length; j++) {
                            //第二次遍历flowFormJson.tabs，为了让return起作用所以不用$.each而采用for循环
                            var tabItemj = flowFormJson.tabs[j]; //模版中tabs数组的遍历元素数据对象
                            if (tabCons[j].getDatas) {
                                var tabObjDatas = tabCons[j].getDatas(); //tab内容页面组的遍历对象获取数据对象
                                if (tabObjDatas.err && tabObjDatas.err.length) {
                                    //判断是否有错误（字段不能为空、超过个数限制等）
                                    layer.alert(tabObjDatas.err.join("<br/>"), { icon: 0 }, function (index) {
                                        $tabSystem.Huitab({ index: j });
                                        layer.close(index);
                                    }
                                    );
                                    return; //直接停止for循环，且for循环之后的代码也不执行
                                }
                                body = tabObjDatas.data;
                            }
                        }
                        l.ajax("addZjAssetsFlow", body, function (data, message, success) {
                            if (success) {
                                $('button[data-name="tab0add"]').css({'display':"none"});
                                recordid = data.recordid;
                                pager.page("remote", { recordid: recordid });
                                layer.confirm(message + "需要添加子资产么？", { icon: 0, }, function (index) {
                                    layer.close(index);
                                    $("#tab-system").Huitab({ index: 1 });
                                },
                                 function (index) {                                
                                    layer.close(index);
                                });
                            }
                        });
                        break;
                    case "launch":
                        if (!recordid) {
                            layer.alert("请先提交基本信息！", { icon: 0 }, function (index) {
                                layer.close(index);
                                $("#tab-system").Huitab({
                                    index: 0
                                });
                            });
                            return;
                        }
                        var body = {
                            flowId: flowId,//流程id
                            recordid: recordid
                        }
                        for (var j = 0; j < flowFormJson.tabs.length; j++) {//第二次遍历flowFormJson.tabs，为了让return起作用所以不用$.each而采用for循环
                            var tabItemj = flowFormJson.tabs[j]; //模版中tabs数组的遍历元素数据对象                   
                            if (tabCons[j].getDatas) {
                                var tabObjDatas = tabCons[j].getDatas(); //tab内容页面组的遍历对象获取数据对象
                                if (tabObjDatas.err.length) {
                                    //判断是否有错误（字段不能为空、超过个数限制等）
                                    layer.alert(tabObjDatas.err.join("<br/>"), { icon: 0 }, function (index) {
                                        $tabSystem.Huitab({ index: j });
                                        layer.close(index);
                                    }
                                    );
                                    return; //直接停止for循环，且for循环之后的代码也不执行
                                }
                                if (tabItemj.isMain) {//如果是主表
                                    tabObjDatas.data.recordid = recordid;
                                    //给主表赋值
                                    body["mainTableName"] = tabItemj.tbName;
                                    body["mainTablePrimaryIdName"] = tabItemj.tbIdName;
                                    body["mainTableDataObject"] = tabObjDatas.data;
                                    body["apiBody"] = {}
                                    for (var key in tabObjDatas.data) {
                                        body["apiBody"][key] = tabObjDatas.data[key]
                                    }
                                    body["title"] = flowFormJson.name;
                                } else {//如果是普通类型子表，type==="1"，目前只有1和2
                                    //给子表赋值-普通表
                                    if (!body["subTableObject"]) { body["subTableObject"] = {} }
                                    for (var key in tabObjDatas.data) {
                                        body["apiBody"][key] = tabObjDatas.data[key]
                                    }
                                    body["subTableObject"][tabItemj.tbName] = { subTablePrimaryIdName: tabItemj.tbIdName, subTableType: tabItemj.type, subTableDataObject: tabObjDatas.data }
                                }
                                body["apiName"] = "updateZjAssetsFlow"
                            }
                        }
                        //流程发起特殊代码---开始
                        layer.confirm("确定发起？", { icon: 0, }, function (index) {//流程发起请求
                            l.ajax("createOpenFlow", body, function (_d, _m, _s, _r) {
                                if (_s) {//发起成功，返回workId
                                    loadFlow(_d.flowButtons, {
                                        otherBody: {
                                            title: body["title"],
                                            nodeId: _d.flowNode.nodeId,
                                            trackId: _d.flowNode.trackId,
                                            workId: _d.workId,
                                            flowVars: _d.flowVars,
                                            nodeVars: _d.nodeVars,
                                            apih5FlowStatus: _d.apih5FlowStatus,
                                            flowId: flowId,//流程id
                                            apiName: "updateZjAssetsFlow",
                                            apiBody: { workId: _d.workId, apih5FlowStatus: _d.apih5FlowStatus, recordid: recordid }
                                        },
                                        endFn: function (buttonClass) { obj.close() },
                                        callback: function (flowObj) {
                                            flowObj.flowSelectOpen(0)
                                        }
                                    });
                                }
                            });
                            layer.close(index);
                        });
                        //流程发起特殊代码---结束
                        break;
                    case "cancel":
                        obj.close()
                        break;
                    default:
                        obj.close()
                        break;
                }
            }
        };
    } else {
        customBtnGroup = {
            btns: [],
            callback: function (dataName, obj) {
            }
        };
    }

    if (tabItem.type === "3") {
        //表格
        var $con = $('<div class="page-container"></div>'); //
        var $table = $('<table id="table" class="table table-border table-bordered table-bg table-hover"></table>');
        var $cl = $('<div class="cl"></div>');
        pagerDom = $('<div id="pager" class="m-pagination f-r"></div>');
        var $btnCon = $('<div class="f-l mt-10"></div>');
        var $add = $('<button data-name="add" data-type="TzLand" class="btn size-S S ml-10  btn-primary" type="button"><i class="Hui-iconfont">&#xe600;</i> 新增</button>');
        var $edit = $('<button data-name="edit" data-type="TzLand" class="btn size-S S ml-10  btn-primary" type="button"><i class="Hui-iconfont">&#xe6df;</i> 编辑</button>');
        var $del = $('<button data-name="del" data-type="TzLand" class="btn size-S S ml-10  btn-primary" type="button"><i class="Hui-iconfont">&#xe6e2;</i> 删除</button>');

        $btnCon.append($add);
        $btnCon.append($edit);
        $btnCon.append($del);
        $cl.append($btnCon);
        $cl.append(pagerDom);
        $con.append($table);
        $con.append($cl);

        table = $table.DataTable({
            info: false, //是否显示数据信息
            paging: false, //是否开启自带分页
            ordering: false, //是否开启DataTables的高度自适应
            processing: false, //是否显示‘进度’提示
            searching: false, //是否开启自带搜索
            autoWidth: false, //是否监听宽度变化
            columnDefs: [//表格列配置
                {
                    targets: [0],
                    data: "rowIndex",
                    width: 13,
                    title: '<input type="checkbox">',
                    render: function (data) {
                        return '<input type="checkbox" name="checkbox" data-rowIndex="' + data + '" >';
                    }
                },
                {
                    targets: [1],
                    data: "rowIndex",
                    title: "No.",
                    width: 25,
                    render: function (data) {
                        return data + 1;
                    }
                },
                {
                    targets: [2], //第几列
                    data: "subName", //接口对应字段
                    title: "资产名称", //表头名
                    defaultContent: "-" //默认显示
                },
                {
                    targets: [3], //第几列
                    data: "subRemarks", //接口对应字段
                    title: "备注姓名", //表头名
                    defaultContent: "-" //默认显示
                },
                {
                    targets: [4], //第几列
                    data: "modifyUserName", //接口对应字段
                    title: "操作员", //表头名
                    defaultContent: "-" //默认显示
                },
                {
                    targets: [5], //第几列
                    data: "modifyTime", //接口对应字段
                    title: "操作时间", //表头名
                    defaultContent: "-",//默认显示
                    render: function (data) {
                        return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
                    }
                }
            ]
        });
        $tabCon.append($con);
        tabCons[i] = $tabCon;
    } else {
        detailForm = tabCons[i] = $tabCon.detailLayer({
            customBtnGroup: customBtnGroup,
            conditions: tabItem.conditions
        });
    }
    tabCons[i].close = function () {
        parent.pager.page('remote')
		parent.layer.close(parent.myOpenLayer)       
    }
});

$tabSystem.append($tabBar).append(tabCons).Huitab({ index: 0 });
//子资产列表
var pager = pagerDom.page({
    remote: {
        //ajax请求配置
        url: l.getApiUrl("getZjZcSubassetList"),
        params: {
            mainAssteId: recordid
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                $.each(data, function (index, item) {
                    item.rowIndex = index;
                });
                table.clear().rows.add(data).draw();
            } else {
                layer.alert(result.message, { icon: 0 }, function (index) {
                    layer.close(index);
                });
            }
        }
    }
});
loadPage();
function loadPage() {
    if (recordid) {
        l.ajax("getZjAssetsPcDetails", { recordid: recordid }, function (data, message, success) {
            if (success) {
                detailForm.setOpenData(data);
            }
        }
        );
    } else {
        detailForm.setOpenData({ memberList: { oaMemberList: [] } });
    }
}
//新增和编辑
var detailLayer = $('#detailLayer').detailLayer({
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "mainAssteId",//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "subassetsId",//输入字段名
        },
        {
            type: "text",//
            name: "subName",//
            label: "资产名称",//
            placeholder: "请输入资产名称",
            must: true
        },
        {
            type: "textarea",//
            name: "subRemarks",//
            label: "备注",//
            placeholder: "请输入备注",
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateZjZcSubasset', _params, function (data) {
            pager.page('remote', { mainAssteId: recordid })
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjZcSubasset', _params, function (data) {
            pager.page('remote', { mainAssteId: recordid })
            obj.close()
        })
    }
});
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            if (recordid) {
                detailLayer.open({ isAdd: true, mainAssteId: recordid });
            } else {
                layer.alert("请先保存基本信息！", { icon: 0 }, function (index) {
                    layer.close(index);
                    $("#tab-system").Huitab({ index: 0 });
                }
                );
            }
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
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.confirm("确定删除？", { icon: 0 }, function (index) {
                    l.ajax("batchDeleteUpdateZjZcSubasset", checkedData, function (data, success, message) {
                        if (success) {
                            pager.page("remote");
                        }
                    });
                    layer.close(index);
                });
            }
            break;
    }
});