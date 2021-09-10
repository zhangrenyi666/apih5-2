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
            "data": "zcmc",//接口对应字段
            "title": "资产名称",//表头名
            "defaultContent": "-"//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "zcyz",//接口对应字段
            "title": "资产原值",//表头名
            "defaultContent": "-"//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "zccz",//接口对应字段
            "title": "资产残值（元）",//表头名
            "defaultContent": "-"//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "synx",//接口对应字段
            "title": "规定使用年限",//表头名
            "defaultContent": "-"//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "bffsName",//接口对应字段
            "title": "报废方式",//表头名
            "defaultContent": "-"//默认显示
        },
		  {
            "targets": [7],//第几列
            "data": "ysynx",//接口对应字段
            "title": "已使用年限",//表头名
            "defaultContent": "-"//默认显示
        },
        {
            "targets": [8],//第几列
            "data": "zcxzbfyy",//接口对应字段
            "title": "报废原因",//表头名
            "defaultContent": "-"//默认显示
        },
        {
            "targets": [9],//第几列
            "data": "createUserName",//接口对应字段
            "title": "操作员",//表头名
            "defaultContent": "-"//默认显示
        },
        {
            "targets": [10],//第几列
            "data": "modifyTime",//接口对应字段
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
            type: "text",//三种形式：text,select,date,
            name: "zcmc",//输入字段名
            label: "资产名称",//输入字段对杨的中文名称
            placeholder: "请输入资产名称"
        },
        {
            type: "select",
            name: "dmfl",
            label: "报废方式",
            ajax: {//如果需要接口获取数据需要添加该属性
                api: "getScrapTypeSelectAllList",//api名称
                valueName: "dmbh",//设置value对应的接口字段名称
                textName: "dmnr"//设置text对应的接口字段名称
            }
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
        pager.page('remote',0, _params)
    },
    onReset: function (arr) {//重置按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
        pager.page('remote', _params)
    }
});
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjHwzcZcbfList"),
        params: {},
        success: function (result) {
            if (result.success) {
                var data = result.data;
                $.each(data, function (index, item) {
                    item.rowIndex = index
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
var detailLayer = $('#detailLayer').detailLayer({
    layerArea:['100%', '100%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "recordid"//输入字段名
        },
        {
            type: "text",//
            name: "zcmc",//
            label: "资产名称",//
            placeholder: "请输入资产名称",
            immutable:true
        },
        {
            type: "number",//
            name: "zcyz",//
            label: "资产原值（元）",//
            placeholder: "请输入资产原值（元）",
            immutable:true
        },
        {
            type: "number",//
            name: "zccz",//
            label: "资产残值（元）",//
            placeholder: "请输入残值（元）",
            immutable:true
        },
        {
            type: "number",//
            name: "synx",//
            label: "规定使用年限（年）",//
            placeholder: "请输入规定使用年限（年）",
            immutable:true
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
            must: true
        },
        {
            type: "number",//
            name: "ysynx",//
            label: "已使用年限（年）",//
            placeholder: "请输入已使用年限（年）",
            max:99999
        },
        {
            type: "textarea",//
            name: "zcxzbfyy",//
            label: "报废原因",//
            placeholder: "请输入报废原因",
            must: true
        },
        {
            type: "upload",//
            name: "imageList",//
            label: "资产图片",//
            btnName: "添加图片",
			projectName:"zj-assets-haiwei",
            fileType: ["jpg", "jpeg", "png", "gif"]
        }
    ],
    onSave: function (obj, _params) {
            l.ajax('updateAssetsScrap', _params, function (data) {
                pager.page('remote');
                obj.close()
            })
        
    },
    onAdd: function (obj, _params) {
        l.ajax('addAssetsScrap', _params, function (data) {
            pager.page('remote');
            obj.close()
        })
    }
});
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            detailLayer.open();
            break;
        case "edit":
            if (checkedData.length == 1) {
                detailLayer.open(checkedData[0]);
            } else if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.alert("只能选择一个", { icon: 0}, function (index) {
                    layer.close(index);
                });
            }
            break;
        case "revoke":
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            } else if(checkedData.length == 1){
                l.ajax('revokeAssetsScrap', {recordid: checkedData[0].recordid}, function (data, message){
                    //if(message=="ok"){
					if(message=="数据更新成功。"){
                        layer.alert("操作成功", { icon: 0 }, function (index) {
                            layer.close(index);
                            pager.page('remote')
                        })
                    }
                })
            }else {
                layer.alert("操作失败只能选择一个", { icon: 0 }, function (index) {
                    layer.close(index);
                    pager.page('remote')
                })
            }
            break;
        case "derive"://导出
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            } else if (checkedData.length == 1) {
                window.location.href = 'http://wechat.zjyjhw.com:88/ureport/excel?_u=file:zjHwzcZcbf.xml&url=' + l.domain + '&recordid=' + checkedData[0].recordid;

            } else {
                layer.alert("只能选择一个", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            }
			break;
        case 'batchDerive'://批量导出
            var params={};
            params.sydwName = $('[name = "zcmc"]').val();
            params.startTime =  $('[name = "dmfl"]').val();
            layer.alert("确定导出此数据？", { icon: 0 }, function (index) {
                l.ajax("scrapListExportExcel",params,function(res){
                    window.location.href=res;
                    layer.close(index);
                });
            });
            break;
    }
});
