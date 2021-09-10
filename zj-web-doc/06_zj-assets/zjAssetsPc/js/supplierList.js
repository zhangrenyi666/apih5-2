﻿var code = Lny.getUrlParam('code')
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
            "targets": [1],//第几列
            "data": "gysbh",//接口对应字段
            "title": "供应商编号",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [2],//第几列
            "data": "gysmc",//接口对应字段
            "title": "供应商名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "lxrxm",//接口对应字段
            "title": "联系人",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "lxrdh",//接口对应字段
            "title": "联系电话",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "zt",//接口对应字段
            "title": "状态",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "0":
                        r = "启用"
                        break
                    case "1":
                        r = "停用"
                        break
                }
                return r
            }
        },
        {
            "targets": [6],//第几列
            "data": "modifyUser",//接口对应字段
            "title": "操作员",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "操作时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
		{
            "targets": [8],//第几列
            "width": 100,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名
            "render": function (data) {
                var html = '';
                html += '<span class="dropDown">'
                html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">操作</a>';
                html += '<ul class="dropDown-menu menu radius box-shadow">';
                html += '<li><a href="javascript:void(0);" onclick="myOpen(\'供应商范围\',\'' + data.recordid + '\',\'' + data.gysmc + '\',\'' + 'supplyRangeList' + '\')">供应商范围</a></li>';
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
            name: "gysbh",//输入字段名
            label: "供应商编号",//输入字段对杨的中文名称
            placeholder: "请输入编号",
        },
        {
            type: "text",//三种形式：text,select,date,
            name: "gysmc",//输入字段名
            label: "供应商名称",//输入字段对杨的中文名称
            placeholder: "请输入名称",
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
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getSupplierList"),
        params: {
            gysmc: "",
            zt: "",
        },
        success: function (result) {
            Lny.log(result)
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
var detailLayer = $('#detailLayer').detailLayer({
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "recordid",//输入字段名
        },
        {
            type: "text",//
            name: "gysbh",//
            label: "供应商编号",//
            placeholder: "请输入供应商编号",
            must: true,
            immutable: true
        },
        {
            type: "text",//
            name: "gysmc",//
            label: "供应商名称",//
            placeholder: "请输入供应商名称",
            must: true
        },
        {
            type: "text",//
            name: "lxrxm",//
            label: "联系人",//
            placeholder: "请输入联系人",
        },
        {
            type: "text",//
            name: "lxrdh",//
            label: "联系电话",//
            placeholder: "请输入联系电话",
        },
        {
            type: "text",//
            name: "dzyx",//
            label: "电子邮箱",//
            placeholder: "请输入电子邮箱",
        },
        // {
        //     type: "address",
        //     name: "dzdm",
        //     label: "地址",
        //     must: true,
        //     ajax: {
        //         api: "getAddressCodeList",
        //         valueName: "recordid",
        //         textName: "dzmc",
        //         childrenName: "addressCodeList",//设置children对应的接口字段名称
        //     }
        // },
        {
            type: "select",
            name: "zt",
            label: "状态",
            selectList: [//当类型为select时，option配置
                {
                    value: 0,//输入字段的值
                    text: "启用",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "停用",//显示中文名
                    selected: false//默认是否选中
                }
            ],
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
            label: "供应商附件",//
            btnName: "添加图片",
            projectName: "zj-assets",
            fileType: ["jpg", "jpeg", "png", "gif"]
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateSupplier', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addSupplier', _params, function (data) {
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
            detailLayer.open();
            addressStyle({
                names: ['第一级', '第二级', '第三级'], //中文名
                must: [true, true, true], //是否必填
                domName: 'dzdm', //对应的是弹出层配置的name 
                detailLayerDom: '#detailLayer'  //对应的是弹出层的dom
            })
            break;
        case "edit":
            if (checkedData.length == 1) {
                detailLayer.open(checkedData[0]);
                addressStyle({
                    names: ['第一级', '第二级', '第三级'], //中文名
                    must: [true, true, true], //是否必填
                    domName: 'dzdm', //对应的是弹出层配置的name 
                    detailLayerDom: '#detailLayer'  //对应的是弹出层的dom
                })
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
					l.ajax("batchDeleteSupplier", checkedData, function () {
					pager.page('remote')
				})
                    layer.close(index);
                });
            }
            break;
    }
})

function myOpen(title, id,gysmc, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id+"&gysmc="+encodeURI(gysmc)
    }
	console.log('搜索参数是：',options)
    layer.full(layer.open(options))
}


//三级联动下拉 依靠修改address样式来实现的
function addressStyle(opt) {
    /*
        opt={  
                names:['第一级', '第二级', '第三级'], 中文名
                must:[true, true, true, true], 是否必填
                domName:'dzdm', 对应的是弹出层配置的name 
                detailLayerDom:'#detailLayer'  //对应的是弹出层的dom
            }
    */

    var opt = opt ? opt : {}
    var names = opt['names'] || ['选项一', '选项二', '选项三'],
        must = opt['must'] === true ? true : (opt['must'] || [true, true, true]),
        domName = opt['domName'] || 'dzdm',
        detailLayerDom = opt['detailLayerDom'] || '#detailLayer';

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
                    $(this).hide()
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