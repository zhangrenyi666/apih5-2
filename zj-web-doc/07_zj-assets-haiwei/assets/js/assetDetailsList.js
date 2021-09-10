var code = Lny.getUrlParam('code')
Lny.setCookie('code', code, 30)
var allData;
var pager = $("#pager").page({
    remote: {// ajax请求配置
        url: l.getApiUrl("getHistoryState"),
        params: {
            recordid: l.getUrlParam("id")
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                allData = data;





                for (var i = 0; i < allData.length; i++) {
                    // console.log(allData[i]);
                    var liClass = '';
                    if (i % 2 == 0) {
                        liClass = 'timerShaft-li a-right'
                    } else {
                        liClass = 'timerShaft-li a-left'
                    }
                    var $li = $('<li class="' + liClass + '"></li>');
                    var dot = $('<div class="dot"></div>');//小圆点
                    var arr = $('<div class="arr"></div>');//小箭头


                    var $timerShaftCon = $('<div class="timerShaft-li-con cl"></div>');
                    var $con = $('<div class="con">' +
                        '<div >资产名称:<span>' + allData[i].zcmc + '</span></div>' +
                        '<div >资产编号:<span>' + (allData[i].zcbh || '-') + '</span></div>' +
                        '<div>报废状态:<span style="color:blue;cursor:pointer;" onclick="myOpen1(\' ' + i + '\',\'' + 'bf' + '\')" >' + (allData[i].zcztName || '-') + '</span></div>' +
                        '<div>维修状态:<span style="color:blue;cursor:pointer;" onclick="myOpen1(\' ' + i + '\',\'' + 'wx' + '\')" >' + (allData[i].wxztdmName || '-') + '</span></div>' +
                        '<div>维修周期:<span>' + (allData[i].wxzq || '-') + '</span></div>' +
                        '<div>折旧状态:<span style="color:blue;cursor:pointer;" onclick="myOpen1(\' ' + i + '\',\'' + 'zj' + '\')" >' + (allData[i].zjztdmName || '-') + '</span></div>' +
                        '<div>盘点状态:<span style="color:blue;cursor:pointer;" onclick="myOpen1(\' ' + i + '\',\'' + 'pd' + '\')" > ' + (allData[i].pdztdmName || '-') + '</span></div>' +
                        '<div>操作员:<span>' + (allData[i].modifyUser || '-') + '</span></div>' +
                        '<div>操作时间:<span>' + (l.dateFormat(new Date(allData[i].modifyTime), "yyyy-MM-dd") || '-') + '</span></div>' + '</div>'); 
                        $timerShaftCon.append($con);

                    $con.append(arr);
                    $con.append(dot);
                    $li.append($timerShaftCon);

                    $('.timerShaft-ul').append($li)
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



var bfDetailLayer = $('#bfDetailLayer').detailLayer({
    layerArea: ['100%', '100%'],
    conditions: [
        {
            type: "text",//
            name: "zcmc",//
            label: "资产名称",//
            placeholder: "请输入资产名称",
            immutable: true
        },
        {
            type: "number",
            name: "zcyz",//
            label: "资产原值（元）",//
            placeholder: "请输入资产原值（元）",
            immutable: true
        },
        {
            type: "number",//
            name: "zccz",//
            label: "资产残值（元）",//
            placeholder: "请输入残值（元）",
            immutable: true
        },
        {
            type: "number",//
            name: "synx",//
            label: "规定使用年限（年）",//
            placeholder: "请输入规定使用年限（年）",
            immutable: true
        },
        {
            type: "select",
            name: "bffsdm",
            label: "报废方式",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getScrapTypeSelectAllList",
                valueName: "dmbh",
                textName: "dmnr"
            },
            immutable: true
        },
        {
            type: "number",//
            name: "ysynx",//
            label: "已使用年限（年）",//
            placeholder: "请输入已使用年限（年）",
            max: 99999,
            immutable: true
        },
        {
            type: "textarea",//
            name: "zcxzbfyy",//
            label: "报废原因",//
            placeholder: "请输入报废原因",
            immutable: true
        },
        {
            type: "upload",//
            name: "imageList",//
            label: "资产图片",//
            immutableAdd: true
        }
    ]
})

var wxDetailLayer = $('#wxDetailLayer').detailLayer({
    layerArea: ['100%', '100%'],
    conditions: [
        {
            type: "text",//
            name: "zcmc",//
            label: "资产名称",//
            placeholder: "请输入资产名称",
            immutable: true
        },
        {
            type: "number",
            name: "synx",//
            label: "规定使用年限（年）",//
            immutable: true
        },
        {
            type: "number",//
            name: "bxts",//
            label: "保修天数（天）",//
            immutable: true
        },
        {
            type: "date",//
            name: "bxrq",//
            label: "报修日期",//
            id: 'bxrq',
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            immutable: true
        },
        {
            type: "text",//
            name: "wxztName",//
            label: "维修状态",
            immutable: true
        },
        {
            type: "text",//
            name: "wxf",//
            label: "维修方",
            immutable: true
        },
        {
            type: "text",//
            name: "wxfy",//
            label: "维修费用（元）",
            immutable: true
        },
        {
            type: "textarea",//
            name: "gzsm",//
            label: "故障说明",
            immutable: true
        },
        {
            type: "date",//
            name: "wxrq",//
            label: "完成日期",
			id: 'wxrq',
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            immutable: true
        },
        {
            type: "upload",//
            name: "imageList",//
            label: "资产图片",//
            immutableAdd: true
        }
    ]
})

var zjDetailLayer = $('#zjDetailLayer').detailLayer({
    layerArea: ['100%', '100%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "recordid",//输入字段名
        },
        {
            type: "text",//
            name: "zcmc",//
            label: "资产名称",//
            placeholder: "请输入资产名称",
            immutable: true,
        },
        {
            type: "text",//
            name: "zcyz",//
            label: "资产原值（元）",//
            placeholder: "请输入资产原值（元）",
            immutable: true,
        },
        {
            type: "select",
            name: "xzl",
            label: "残值率（%）",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getCzlSelectAllList",
                valueName: "dmbh",
                textName: "dmnr"
            },
            immutable: true,
        },
        {
            type: "number",//
            name: "zzjyf",//
            label: "总折旧月份（月）",//
            placeholder: "请输入总折旧月份（月）",
            max: 9999999999,
            immutable: true
        },
        {
            type: "number",//
            name: "zjny",//
            label: "已折旧月份（月）",//
            placeholder: "请输入已折旧月份（月）",
            max: 9999999999,
            immutable: true
        },
        {
            type: "number",//
            name: "yzj",//
            label: "月折旧（元）",//
            placeholder: "请输入月折旧（元）",
            max: 9999999999,
            immutable: true
        },
        {
            type: "number",//
            name: "ljzj",//
            label: "累计折旧（元）",//
            placeholder: "请输入累计折旧（元）",
            max: 999,
            immutable: true
        },
        {
            type: "number",//
            name: "zxjz",//
            label: "资产净值（元）",//
            placeholder: "请输入资产净值（元）",
            max: 9999999999,
            immutable: true
        }
    ]
})

var pdDetailLayer = $('#pdDetailLayer').detailLayer({
    layerArea: ['100%', '100%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "recordid"//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "sszcid"//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "sszcid"//输入字段名
        },
        {
            type: "text",
            name: "sszclx2Name",
            label: "所属小类",
            immutableAdd: true,
        },
        {
            type: "text",//
            name: "zcmc",//
            label: "资产名称",//
            placeholder: "请输入资产名称",
            immutable: true
        },
        {
            type: "text",
            name: "inventoryTitle",
            label: "盘点标题",
            immutableAdd: true
        },
        {
            type: "date",//
            name: "pdrq",//
            label: "盘点日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id: "pdrq",
            immutableAdd: true
        },
        {
            type: "select",
            name: "zcztdm",
            label: "资产状态(前)",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getAssetsStateSelectList",
                valueName: "dmbh",
                textName: "dmnr"
            },
            immutableAdd: true
        },
        {
            type: "select",
            name: "zcztdmafter",
            label: "资产状态(后)",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getAssetsStateSelectList",
                valueName: "dmbh",
                textName: "dmnr"
            },
            immutableAdd: true
        },
        {
            type: "text",
            name: "cfddidbefore",
            label: "存放地点(前)",
            placeholder: "请输入存放地点(前)",
            immutable: true
        },
        {
            type: "text",
            name: "cfddidafter",
            label: "存放地点(后)",
            placeholder: "请输入存放地点(后)",
            immutableAdd: true
        },
        {
            type: "select",//
            name: "sybuidbefore",//
            label: "使用部门(前）",//
            placeholder: "请输入使用部门(前)",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getDepartmentSelectAllList",
                valueName: "bmbh",
                textName: "bmmc"
            },
            immutableAdd: true
        },
        {
            type: "select",//
            name: "sybuidafter",//
            label: "使用部门(后)",//
            placeholder: "请输入使用部门(后)",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getDepartmentSelectAllList",
                valueName: "bmbh",
                textName: "bmmc"
            },
            immutableAdd: true
        },
        {
            type: "text",//
            name: "syrbefore",//
            label: "使用者(前)",//
            immutableAdd: true
        },
        {
            type: "text",//
            name: "syrafter",//
            label: "使用者(后)",//
            immutableAdd: true
        },
        {
            type: "select",
            name: "pdjgdm",
            label: "盘点结果",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getInventorySelectAllList",
                valueName: "dmbh",
                textName: "dmnr"
            },
            immutableAdd: true
        },
        {
            type: "select",//
            name: "copestate",//
            label: "处理状态",//
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getCopeStateSelectAllList",
                valueName: "dmbh",
                textName: "dmnr",
            },
            immutableAdd: true
        },
        {
            type: "textarea",//
            name: "clfa",//
            label: "处理方案",//
            placeholder: "请输入处理方案",
            immutableAdd: true
        },
        {
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入备注",
            immutableAdd: true
        },
        {
            type: "upload",//
            name: "imageList",//
            label: "资产图片",//
            immutableAdd: true
        }
    ]
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
                l.delTableRow("recordid", 'currentList', 'batchDeleteLcAsstes', checkedData, function (data) {
                    pager.page('remote')
                })//  删除url地址
            }
            break;
    }
})

function myOpen1(index, type) {
    // console.log(type)
    var rowData = allData[Number(index)];
    var params;
    switch (type) {
        case 'bf':
            params = {};
            params.recordid = rowData.recordid;
            l.ajax('getZjHwzcZcbfDetails', params, function (res) {
                bfDetailLayer.open(res);
                $('#bfDetailLayer .btn').hide();
            })
            break;
        case 'wx':
            params = {};
            params.recordid = rowData.recordid;
            l.ajax('getAssetsRepairDetails', params, function (res) {
                wxDetailLayer.open(res);
                $('#wxDetailLayer .btn').hide();
            })
            break;
        case 'zj':
            params = {};
            params.recordid = rowData.recordid;
            l.ajax('getWeChatDiscountDetails', params, function (res) {
                zjDetailLayer.open(res);
                $('#zjDetailLayer .btn').hide();
            })
            break;
        case 'pd':
            params = {};
            params.recordid = rowData.recordid;
            l.ajax('getInventoryDetails', params, function (res) {
                // console.log(res)
                pdDetailLayer.open(res);
                $('#pdDetailLayer .btn').hide();
            })
            break;
    }
}