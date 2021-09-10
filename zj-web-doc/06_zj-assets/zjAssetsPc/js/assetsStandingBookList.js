var code = Lny.getUrlParam('code')
Lny.setCookie('code', code, 30)
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
            "data": "sszclx1Name",//接口对应字段
            "title": "资产大类",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "sszclx2Name",//接口对应字段
            "title": "资产小类",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "资产编号",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                var a;
                if (data) {
                    a = '<a style="color:blue;" onclick="myOpen(\'资产详情\',\'' + data.recordid + '\',\'' + 'assetDetails' + '\')" href="javascript:void(0);">' + data.zcbh + '</a>'
                }
                return a;
            }
        },
        {
            "targets": [5],//第几列
            "data": "zcmc",//接口对应字段
            "title": "资产名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "sydwName",//接口对应字段
            "title": "使用单位",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "grrq",//接口对应字段
            "title": "购入日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return(data) ? l.dateFormat(new Date(data), "yyyy-MM-dd") :"-";
            }
        },
        {
            "targets": [8],//第几列
            "data": "zcztName",//接口对应字段
            "title": "资产状态",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [9],//第几列
            "data": "wxztdmName",//接口对应字段
            "title": "维修状态",//表头名
            "defaultContent": "-",//默认显示
        },

        {
            "targets": [10],//第几列
            "data": "glryName",//接口对应字段
            "title": "管理员",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [11],//第几列
            "data": "createTime",//接口对应字段
            "title": "操作时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        }
    ]
});

var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "select",
            name: "sszclx1",
            label: "资产大类",
            ajax: {//如果需要接口获取数据需要添加该属性
                api: "getAssetsTypeTreeAllList",//api名称
                child: "sszclx2",//如果有联动下拉，设置被他控制的下拉name
                valueName: "recordid",//设置value对应的接口字段名称
                textName: "assetTypeIdAndName",//设置text对应的接口字段名称
                childrenName: "currentList",//设置children对应的接口字段名称
            },
        },
        {
            type: "select",
            name: "sszclx2",
            label: "资产小类",
            ajax: {
                parent: "sszclx1",//如果有联动下拉，设置他被控制的下拉name
                valueName: "recordid",
                textName: "assetTypeIdAndName",
            },
        },
        {
            type: "text",//三种形式：text,select,date,
            name: "zcbh",//输入字段名
            label: "资产编号",//输入字段对杨的中文名称
            placeholder: "请输入资产编号",
        },
        {
            type: "text",//三种形式：text,select,date,
            name: "zcmc",//输入字段名
            label: "资产名称",//输入字段对杨的中文名称
            placeholder: "请输入资产名称",
        },
        {
            type: "text",//三种形式：text,select,date,
            name: "glryid",//输入字段名
            label: "管理人员",//输入字段对杨的中文名称
            placeholder: "请输入管理人员",
        },
        {
            type: "text",//三种形式：text,select,date,
            name: "sybmid",//输入字段名
            label: "使用部门",//输入字段对杨的中文名称
            placeholder: "请输入使用部门",
        },
        {
            type: "text",//三种形式：text,select,date,
            name: "sydwid",//输入字段名
            label: "使用单位",//输入字段对杨的中文名称
            placeholder: "请输入使用单位",
        },
        {
            type: "text",//三种形式：text,select,date,
            name: "syr",//输入字段名
            label: "使用人",//输入字段对杨的中文名称
            placeholder: "请输入使用人",
        },
        {
            type: "select",
            name: "zcztdm",
            label: "资产状态",
            ajax: {//如果需要接口获取数据需要添加该属性
                api: "getAssetsStateSelectList",//api名称
                valueName: "dmbh",//设置value对应的接口字段名称
                textName: "dmnr",//设置text对应的接口字段名称
            },
        },
        {
            type: "date",//text,select,date,
            name: "startTime",
            label: "购入日期开始",
            //defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd"),
            maxDate: "\'#F{$dp.$D(\\\'" + "logmax" + "\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmin"
        },
        {
            type: "date",//text,select,date,
            name: "endTime",
            label: "购入日期结束",
            //defaultValue: l.dateFormat(new Date(new Date().setDate(1)), "yyyy-MM-dd"),
            //maxDate: "\'#F{$dp.$D(\\\'"+"logmin"+"\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmax"
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
var userId = l.getUrlParam("userAccount");
var orgID = l.getUrlParam("orgID");
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getAssetsList"),
        params: {
            sszclx1: "",
            sszclx2: "",
            cfdd1: "",
            cfdd2: "",
            zcmc: "",
            sybm: "",
            syr: "",
            cwbh: "",
            zcztdm: "",
            sydw: "",
            sessionId: userId,
            departmentOrgId: orgID
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                //console.log('params:',data)
                allData = data;
                if (result.data.length > 0) {
                    if (result.data[0].isCompany == 0) {
                        $("#delete").hide();
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

var detailLayer = $('#detailLayer').detailLayer({
    layerArea: ['100%', '100%'],
    conditions: [
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
            immutableAdd: true
        },
        {
            type: "select",
            name: "sszclx2",
            label: "资产小类",
            //search: true,
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                parent: "sszclx1",
                valueName: "recordid",
                textName: "assetTypeIdAndName",
            },
            immutableAdd: true
        },
        {
            type: 'text',//
            name: "zcbh",//
            label: "资产编号",//
            placeholder: "请输入资产编号",
            immutableAdd: true
        },
        {
            type: "text",//
            name: "zcmc",//
            label: "资产名称",//
            placeholder: "请输入资产名称",
            immutableAdd: true
        },
        {
            type: "text",//
            name: "zcyz",//
            label: "资产原值（元）",//
            placeholder: "请输入资产原值（元）",
            immutableAdd: true
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
            immutableAdd: true
        },
        {
            type: "number",//
            name: "synx",//
            label: "使用年限（年）",//
            placeholder: "请输入使用年限（年）",
            immutableAdd: true
        },
        {
            type: "number",//
            name: "bxts",//
            label: "保修天数（月）",//
            placeholder: "请输入保修天数（月）",
            immutableAdd: true
        },
        {
            type: "text",//
            name: "ggxh",//
            label: "规格型号",//
            placeholder: "请输入规格型号",
            immutableAdd: true
        },
        {
            type: "date",//
            name: "grrq",//
            label: "购入日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id: "grrq",
            immutableAdd: true
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
            immutableAdd: true
        },
        {
            type: "hidden",//
            name: "cgbh",//
            label: "采购编号",//
            placeholder: "请输入采购编号",
            immutableAdd: true
        },
        {
            type: "pickTree",
            name: "glrymc",
            label: "管理员",
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            },
            immutableAdd: true
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
            immutableAdd: true
        },
        {
            type: "pickTree",//
            name: "oaSydw",//接口字段名
            label: "使用单位",//
            immutableAdd: true,
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
            },
            immutableAdd: true
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
            immutableAdd: true
        },
        {
            type: "text",//
            name: "syr",//接口字段名
            label: "使用人",//
            placeholder: "请输入使用人",
            immutableAdd: true
        },
        {
            type: "date",//
            name: "scrq",//
            label: "出厂日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id: "scrq",
            immutableAdd: true
        },
        {
            type: "text",//
            name: "serialNumber",//
            label: "出厂编号",//
            placeholder: "请输入出厂编号",
            immutableAdd: true
        },
        {
            type: "hidden",//
            name: "nynx",//
            label: "耐用年限（年）",//
            placeholder: "请输入耐用年限（年）",
            immutableAdd: true
        },
        {
            type: "select",//
            name: "manufacturerId",//
            label: "生产厂家",
            placeholder: "请输入资产品牌",
            selectList: [//当类型为select时，option配置
            ],
            ajax: {
                api: "getManufacturerSelectAllList",
                valueName: "recordid",
                textName: "manufacturerName",
            },
            immutableAdd: true
        },
        {
            type: "address",
            name: "gys",
            label: "地址",
			defaultValue: '0_0_0',
            //defaultValue: '1',
            ajax: {
                api: "getZjSupplierSelectAllList",
                valueName: "number",
                textName: "dzmc",
                childrenName: "addressCodeList",//设置children对应的接口字段名称
            },
            immutableAdd: true
        },
        {
            type: "textarea",//
            name: "bz",//
            label: "备注",//
            placeholder: "请输入备注",
            immutableAdd: true
        },
        {
            type: "upload",//
            name: "imageList",//
            label: "资产图片",//
            btnName: "添加图片",
            projectName: "zj-assets",
            fileType: ["jpg", "jpeg", "png", "gif"],
            immutableAdd: true
        }
    ],
})

var leading = $('#leading').detailLayer({
    layerArea: ['410px', '310px'],
    layerTitle: ['导入'],
    conditions: [
        {
            type: "upload",
            maxLen: 1,
            name: "importFileList",
            label: "导入文件",
            btnName: "选择文件",
            projectName: "zj-assets",
            fileType: ["xls", "xlsx"],
        }
    ],
    onAdd: function (obj, _params) {
        if (_params.importFileList.length == 0) {
            layer.alert("请选择文件", { icon: 0, }, function (index) {
                layer.close(index);
            });
            return;
        }
        l.ajax('batchInputAssets', { "importFileList": _params.importFileList }, function (_params) {
            pager.page('remote')
            obj.close()
        })
    }
});

$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "derive"://导出
            var params = {};
            params.sydwName = $('[name = "sydwid"]').val();
            params.startTime = $('[name = "startTime"]').val();
            params.endTime = $('[name = "endTime"]').val();
            //console.log(params);
            layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
                l.ajax('assetsExportExcel', params, function (res) {
                    layer.alert("导出成功！", { icon: 0 }, function (index) {
                        layer.close(index);
                        window.location.href = res;
                    })
                })
            });
            break;
    }
})

var myOpenLayer
function myOpen(title, id, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id
    }
    myOpenLayer = layer.open(options)
    layer.full(myOpenLayer)
}

function myOpen1(index, type) {
    var rowData = allData[Number(index)];
    var params;
    switch (type) {
        case 'bf':
            params = {};
            params.recordid = rowData.recordid;
            // console.log('params:',params)
            l.ajax('getAssetsDetails', params, function (res) {
                detailLayer.open(res);
                addressStyle({
                    names: ['供应商', '供应商品', '商品品牌'], //中文名
                    must: [false, false, false], //是否必填
                    domName: 'gys', //对应的是弹出层配置的name 
                    detailLayerDom: '#detailLayer'  //对应的是弹出层的dom
                })
                $('#detailLayer .btn').hide();
            })
            break;
    }
}


//三级联动下拉 依靠修改address样式来实现的
function addressStyle(opt) {
    /*
        opt={  
                names:['第一级', '第二级', '第三级'], 中文名
                must:[true, true, true, true], 是否必填
                domName:'dzdm', 对应的是弹出层配置的name 
                detailLayerDom:'#addDetailLayer'  //对应的是弹出层的dom
            }
    */

    var opt = opt ? opt : {}
    var names = opt['names'] || ['供应商', '供应商品', '商品品牌'],
        must = opt['must'] === true ? true : (opt['must'] || [true, true, true]),
        domName = opt['domName'] || 'dzdm',
        detailLayerDom = opt['detailLayerDom'] || '#addDetailLayer';

    //设置label隐藏
    $(detailLayerDom + ' select[name="' + domName + '"]')
        .parent().parent().siblings().hide();

    var _con = $(detailLayerDom + ' select[name="' + domName + '"]').siblings()
    _con.each(function (index, ele) {
        $(ele).hide();
    })

    $(detailLayerDom + ' select[name="' + domName + '"]')
        .parent().parent().attr('class', 'row f-l col-12');

    //设置select容器的style
    $(detailLayerDom + ' select[name="' + domName + '"]')
        .parent().attr('class', 'row f-l col-12');

    //设置新label
    $(detailLayerDom + ' select[name="' + domName + '"]').each(function (index, ele) {
        $(ele).before('<label class="m-label form-label col-2 f-l"><span><i style="color:red;display:' + (must[index] ? "" : "none") + '">* </i>' + (names[index]) + '：</span></label>')
        $(ele).on('change', function () {
            $(this).parent().siblings().each(function (index, ele) {
                var _html = $(ele).find('select').html();
                if (!_html) {
                    $(this).nextSbiling.hide()
                    $(this).hide();

                } else {
                    $(this).show()
                }
            })
        })
        //没有
        // var _v = $(ele).val() 
        // if (_v == 'null' || !_v) {
        //     $(ele).parent().hide()
        // }
    })

    //select的style 
    $(detailLayerDom + ' select[name="' + domName + '"]')
        .attr('class', 'col-9 f-l').css({
            'font-size': '14px',
            'height': '31px',
            'line-height': '1.42857',
            'padding': '4px',
            'margin-left': '18px',
            'transition': 'all .2s linear 0s',
            'box-sizing': 'border-box',
            'border': 'solid 1px #ddd',
        })
}
