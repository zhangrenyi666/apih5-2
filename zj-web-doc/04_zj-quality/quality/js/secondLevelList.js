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
            "data": "secondLevelName",//接口对应字段
            "title": "二级分类名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "secondLevelOrder",//接口对应字段
            "title": "表示顺序",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "firstLevelName",//接口对应字段
            "title": "一级分类名称",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                // var r = "未知";
                var r = l.getUrlParam("firstLevelName");
                switch (data) {
                }
                return r
            }
        },
        {
            "targets": [5],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "更新时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [6],//第几列
            "data": "modifyUser",//接口对应字段
            "title": "更新者",//表头名
            "defaultContent": "-",//默认显示
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
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
        pager.page('remote',0, _params)
    }
})
var pager = $("#pager").page({  
    remote: {//ajax请求配置
        url: l.getApiUrl("getSecondLevelList"),
        params: {
          firstLevelId:l.getUrlParam("id") 
        },
        success: function (result) {
            //Lny.log(result)
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
            name: "secondLevelId",//输入字段名
        },
        {
            type: "text",//
            name: "firstLevelName",//
            label: "一级分类名称",//
            defaultValue:l.getUrlParam("firstLevelName") ,
            immutableAdd:true,
            must: true
        },
        {
            type: "text",//
            name: "secondLevelName",//
            label: "二级分类名称",//
            placeholder: "请输入二级分类名称",
            must: true
        },
        {
            type: "text",//
            name: "secondLevelOrder",//
            label: "表示顺序",//
            placeholder: "请输入表示顺序",
            must: true
        }
    ],
    onSave: function (obj, _params) {
         _params.firstLevelId=l.getUrlParam("id")  //设置一级分类列表id
        l.ajax('updateSecondLevel', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        _params.firstLevelId=l.getUrlParam("id")  //设置一级分类列表id
        l.ajax('addSecondLevel', _params, function (data) {
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
            if (checkedData.length == 1) {
                //   Lny.log(checkedData)
                l.delTableRowById("secondLevelId", 'deleteSecondLevel', checkedData[0], function (data) {
                    pager.page('remote')
                })//  删除url地址
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
    }
})
function myOpen(title, id, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id
    }
    layer.full(layer.open(options))
}
function public(voteId) {//发布
    l.ajax('sendVoteInfo', { voteId: voteId }, function (data, message) {
        layer.alert(message, { icon: 0, }, function (index) {
            layer.close(index);
            pager.page('remote')
        });
    })
}
function preview(voteId) {//预览
    window.open(l.domainName + 'initMVotePreview.do?voteId=' + voteId + '&previewFlag=1', 'newwindow', 'height=500,width=350,top=100,left=500,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no')
}
