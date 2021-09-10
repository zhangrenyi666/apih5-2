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
            "data": "infoTitle",//接口对应字段
            "title": "标题",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                if (data.length > 10) {
                    var str = data.slice(0, 10) + '...';
                    return str;
                } else {
                    return data;
                }
            }
        },
        {
            "targets": [3],//第几列
            "data": "infoAuthor",//接口对应字段
            "title": "作者",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "firstLevel",//接口对应字段
            "title": "一级分类",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                if (data && data.firstLevelName) {
                    return data.firstLevelName
                } else {
                    return '-'
                }
            }
        },
        {
            "targets": [5],//第几列
            "data": "secondLevel",//接口对应字段
            "title": "二级分类",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                if (data && data.secondLevelName) {
                    return data.secondLevelName
                } else {
                    return '-'
                }
            }
        },
        {
            "targets": [6],//第几列
            "data": "infoContent",//接口对应字段
            "title": "内容文本",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                if (data.length > 20) {
                    var str = data.slice(0, 20) + '...';
                    return str;
                } else {
                    return data;
                }
            }
        },
        {
            "targets": [7],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "更新时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                if (data) {
                    return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
                } else {
                    return "-";
                }
            }
        },
        {
            "targets": [8],//第几列
            "data": "modifyUser",//接口对应字段
            "title": "更新者",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [9],//第几列
            "width": 80,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "图片",//表头名
            "render": function (data) {
                var html = '';
                html += '<a href="javascript:void(0);" onclick="  myOpen(\'内容图片\',\'' + data.infoId + '\',\'' + 'accessoryPic' + '\')  ">查看</a>';
                return html;
            }
        },


        {
            "targets": [10],//第几列
            "width": 80,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "视频",//表头名
            "render": function (data) {
                var html = '';
                html += '<a href="javascript:void(0);" onclick="  myOpen(\'视频列表\',\'' + data.infoId + '\',\'' + 'accessoryVideo' + '\')  ">查看</a>';
                return html;
            }
        },
        {
            "targets": [11],//第几列
            "width": 80,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "附件",//表头名
            "render": function (data) {
                var html = '';
                html += '<a href="javascript:void(0);" onclick="  myOpen(\'附件列表\',\'' + data.infoId + '\',\'' + 'accessoryPdf' + '\')  ">查看</a>';
                return html;
            }
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "infoTitle",//输入字段名
            label: "指导资料标题",//输入字段对杨的中文名称
            placeholder: "请输入指导资料标题",
        },
        {
            type: "select",
            name: "firstLevelId",
            label: "一级分类",
            ajax: {//如果需要接口获取数据需要添加该属性
                api: "getFirAndSecList",//api名称
                child: "secondLevelId",//如果有联动下拉，设置被他控制的下拉name
                valueName: "firstLevelId",//设置value对应的接口字段名称
                textName: "firstLevelName",//设置text对应的接口字段名称
                childrenName: "secondLevelList",//设置children对应的接口字段名称
            },
        },
        {
            type: "select",
            name: "secondLevelId",
            label: "二级分类",
            ajax: {
                parent: "firstLevelId",//如果有联动下拉，设置他被控制的下拉name
                valueName: "secondLevelId",
                textName: "secondLevelName",
            },
        }
    ],
    onSearch: function (arr) {//搜索按钮回调
        var _secondLevelList = [];
        var _params = {};
        var _flag = "";
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].name == "firstLevelId") {
                if(arr[i].value) {
                    _flag = arr[i].value;
                }
             }
            if (arr[i].name == "secondLevelId") {
                if (arr[i].value) {
                    var _secondLevelId = {"secondLevelId":arr[i].value};
                    _secondLevelList.push(_secondLevelId);
                    // _params[arr[i].name] = "";
                    _params["secondLevelList"] = _secondLevelList;
                } else {
                    _params["secondLevelList"] = [];
                    _params[arr[i].name] = "";
                }
            } else {
                _params[arr[i].name] = arr[i].value;
            }
        }
        if(!_flag) {
            _params["secondLevelList"] = [];
        }
        pager.page('remote',0, _params)
    },
    onReset: function (arr) {//重置按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            if(arr[i].name == "firstLevelId") {
                _params[arr[i].name] = "";
            }else if(arr[i].name == "secondLevelId"){
                _params["secondLevelList"] = [];
            }else { 
                _params[arr[i].name] = arr[i].value;
            }
        }  
        pager.page('remote',0, _params)
    }
})
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getInformationList"),
        params: {
            infoTitle: "",
            firstLevelId: "",
            secondLevelId: "",
            secondLevelList: [],
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                //console.dir(data)
                $.each(data, function (index, item) {
                    item.rowIndex = index
                })
                table.clear().rows.add(data).draw();
                // Lny.log(result)
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
            name: "infoId",//输入字段名
        },
        {
            type: "select",
            name: "firstLevelId",
            label: "一级分类",
            ajax: {//如果需要接口获取数据需要添加该属性
                api: "getFirAndSecList",//api名称
                child: "secondLevelId",//如果有联动下拉，设置被他控制的下拉name
                valueName: "firstLevelId",//设置value对应的接口字段名称
                textName: "firstLevelName",//设置text对应的接口字段名称
                childrenName: "secondLevelList",//设置children对应的接口字段名称
            }
        },
        {
            type: "select",
            name: "secondLevelId",
            label: "二级分类",
            ajax: {
                parent: "firstLevelId",//如果有联动下拉，设置他被控制的下拉name
                valueName: "secondLevelId",
                textName: "secondLevelName",
            },
        },
        {
            type: "text",//
            name: "infoTitle",//
            label: "资料标题",//
            placeholder: "请输入资料标题",
            must: true
        },
        {
            type: "textarea",//
            name: "infoContent",//
            label: "资料内容",//
            placeholder: "请输入资料内容"
            // must: true
        },
        {
            type: "text",//
            name: "infoAuthor",//
            label: "资料作者",//
            placeholder: "请输入资料作者",
            must: true
        }

    ],
    onSave: function (obj, _params) {
        l.ajax('updateInformation', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addInformation', _params, function (data) {
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
                l.delTableRowById("infoId", 'deleteInformation', checkedData[0], function (data) {
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
