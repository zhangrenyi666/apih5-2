var recordid = Lny.getUrlParam('id')
$("#tab-system").Huitab({ index: 0 })
var mainDetailLayer = $('#mainDetailLayer').detailLayer({
    layerArea: ['100%', '100%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "recordid",//输入字段名
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
            immutableAdd: true,
            must: true
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
            immutableAdd: true,
            must: true
        },
        {
            type: 'text',//
            name: "zcbh",//
            label: "资产编号",//
            placeholder: "请输入资产编号",
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
            type: "number",//
            name: "synx",//
            label: "使用年限（年）",//
            placeholder: "请输入使用年限（年）",
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
            }
        },
        {
            type: "pickTree",//
            name: "oaSydw",//接口字段名
            label: "使用单位",//
          
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
            }
        },
        {
            type: "text",//
            name: "syr",//接口字段名
            label: "使用人",//
            placeholder: "请输入使用人"
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
            type: "hidden",//
            name: "nynx",//
            label: "耐用年限（年）",//
            placeholder: "请输入耐用年限（年）"
        },
        {
            type: "text",//
            name: "gysName",//
            label: "供应商",//
             immutableAdd: true,
            placeholder: "请输入供应商"
        },
        {
            type: "text",//
            name: "rangeName",//
            label: "供应范围",//
            immutableAdd: true,
            placeholder: "请输入供应范围"
        },
        {
            type: "text",//
            name: "brandName",//
            label: "品牌",//
            immutableAdd: true,
            placeholder: "请输入品牌"
        },
        {
            type: "textarea",//
            name: "bz",//
            label: "备注",//
            placeholder: "请输入备注"
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
    onSave: function (obj, _params) {
        var zcyz = _params.zcyz;//资产原值（元）
        var zcyzGs = /^[Z0-9 \d+(\.\d+)?]{1,20}$/.test(zcyz);//资产原值格式限制
        if (_params.glrymc.oaMemberList.length > 1) {
            layer.alert("管理员只可选一个", { icon: 0 }, function (index) {
                layer.close(index);
            });
        } else if (zcyzGs == false) {
            layer.alert("资产原值只允许数字最大长度为30", { icon: 0 }, function (index) {
                layer.close(index);
            });
        } else {
            l.ajax('updateAssets', _params, function (data, message, success) {
                if (success) {
                    parent.pager.page('remote');
                    obj.close()
                }
            })
        }
    },
    onAdd: function (obj, _params) {
    }
})
mainDetailLayer.close = function () {
    parent.layer.close(parent.myOpenLayer)
}
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
            "data": "subName",//接口对应字段
            "title": "资产名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "subRemarks",//接口对应字段
            "title": "备注",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "modifyUserName",//接口对应字段
            "title": "操作员",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "操作时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        }
    ]
});
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjZcSubassetList"),
        params: {
            mainAssteId: recordid
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

l.ajax("getAssetsDetails", { recordid: recordid }, function (data, message, success) {
    mainDetailLayer.setOpenData(data)
})

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
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjZcSubasset', _params, function (data) {
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
            if (recordid) {
                detailLayer.open({ isAdd: true, mainAssteId: recordid });
            } else {
                layer.alert("请先保存基本信息！", { icon: 0, }, function (index) {
                    $("#tab-system").Huitab({ index: 0 })
                    layer.close(index);
                });
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
                layer.confirm("确定删除？", { icon: 0, }, function (index) {
                    l.ajax("batchDeleteUpdateZjZcSubasset", checkedData, function () {
                        pager.page('remote')
                    })
                    layer.close(index);
                });
            }
            break;
        case "close":
            layer.confirm("基本信息如果做了修改，确定已经保存了么？", { icon: 0, }, function (index) {
                parent.layer.close(parent.myOpenLayer)
                layer.close(index);
            });
            break;
    }
})