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
            "targets": [1],//第几列
            "data": "czlxbh",//接口对应字段
            "title": "编号",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [2],//第几列
            "data": "zclxmc",//接口对应字段
            "title": "大分类名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "bz",//接口对应字段
            "title": "备注",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
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
            "targets": [5],//第几列
            "data": "modifyUser",//接口对应字段
            "title": "操作员",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "操作时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [7],//第几列
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
                html += '<li><a href="javascript:void(0);" onclick="myOpen(\'资产小分类\',\'' + data.recordid + '\',\'' + data.zclxmc + '\',\'' + 'assetsTypeXlList' + '\')">资产小分类</a></li>';
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
            name: "zclxmc",//输入字段名
            label: "大分类名称",//输入字段对杨的中文名称
            placeholder: "请输入大分类名称",
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
})
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getAssetsTypeList"),
        params: {
            zclxmc: "",
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
var detailLayer = $('#detailLayer').detailLayer({
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "recordid",//输入字段名
        },
        {
            type: "text",//
            name: "czlxbh",//
            label: "编号",//
            placeholder: "请输入编号",
            //max: 99999,//设置最大值，默认是无穷大
            must: true,
            immutable:true
        },
        {
            type: "text",//
            name: "zclxmc",//
            label: "大分类名称",//
            placeholder: "请输入大分类名称",
            must: true
        },
        {
            type: "textarea",//
            name: "bz",//
            label: "备注",//
            placeholder: "请输入备注",
        },
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
        }
    ],
    onSave: function (obj, _params) {
        var czlxbh= _params.czlxbh;//编号
        var czlxbhGs = /^[a-zA-Z0-9]{1,5}$/.test(czlxbh);//编号格式限制
        if(czlxbhGs==false){
            layer.alert("编号只允许为英文或数字最大长度为5", { icon: 0, }, function (index) {
                layer.close(index);
            });
        }else{
            l.ajax('updateAssetsType', _params, function (data) {
                pager.page('remote');
                obj.close()
            })
        }
    },
    onAdd: function (obj, _params) {
        var czlxbh= _params.czlxbh;//编号
        var czlxbhGs = /^[a-zA-Z0-9]{1,5}$/.test(czlxbh);//编号格式限制
        if(czlxbhGs==false){
            layer.alert("编号只允许为英文或数字最大长度为5", { icon: 0, }, function (index) {
                layer.close(index);
            });
        }else {
            l.ajax('addAssetsType', _params, function (data) {
                pager.page('remote')
                obj.close()
            });
        }
    }
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
				layer.confirm("确定删除？", { icon: 0,}, function (index) {
				l.ajax("batchDeleteAssetsType", checkedData, function () {
					pager.page('remote')
				})
				layer.close(index);
            });
            }
            break;
    }
})
function myOpen(title, id,zclxmc, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id+"&zclxmc="+encodeURI(zclxmc)
    }
    layer.full(layer.open(options))
}