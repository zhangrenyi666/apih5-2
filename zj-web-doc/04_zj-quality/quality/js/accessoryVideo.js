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
            "data": "accessoryName",
            "title": '名称'
        },
        {
            "targets": [2],//第几列
            "data": "accessoryAddress",//接口对应字段
            "title": "地址",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                if (data.length > 20) {
                    var str = data.slice(0, 20) + '...';

                } else {
                    var str = data;
                }
                var html = '';
                html += '<a target="_blank" href="' + data + '">' + str + '</a>';
                return html;
            }
        },
        {
            "targets": [3],//第几列
            "data": "accessorySize",//接口对应字段
            "title": "大小",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "accessoryDesc",//接口对应字段
            "title": "描述",//表头名
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
            "targets": [5],//第几列
            "data": "accessoryTime",//接口对应字段
            "title": "时长",//表头名
            "defaultContent": "-",//默认显示
        }
    ]
});

var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getInformationDetail"),
        params: {
            infoId: l.getUrlParam('id'),
        },
        success: function (result) {
            //Lny.log(result)
            if (result.success) {
                var data = result.data.videoAccessoryList || [];
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
            name: "infoId",//输入字段名
            defaultValue: l.getUrlParam('id')
        },
        {
            type: 'hidden',
            name: 'accessoryLevel',
            defaultValue: 1
        },
        {
            type: 'hidden',
            name: 'accessoryId'
        },
        {
            type: "text",//
            name: "accessoryName",//
            label: "名称",//
            placeholder: "请输入附件名称",
            must: true
        },
        {
            type: "textarea",//
            name: "accessoryDesc",//
            label: "描述",//
            placeholder: "请输入附件描述",
            must: true
        },
        {
            type: "number",//
            name: "accessorySize",//
            label: "大小(m)",//
            placeholder: "请输入附件大小",
            must: true
        },
        {
            type: "number",//
            name: "accessoryTime",//
            label: "时长(s)",//
            placeholder: "请输入播放时长",
            must: true
        },
        {
            type: "upload",//
            name: "imageAddress",//
            maxLen: 1,
            label: "封面上传",//
            btnName: "选择封面",
            fileType: ["jpg", "jpeg", "png", "gif", "bmp"],
            must: true
        },
        {
            type: "upload",//
            name: "accessoryAddress",//
            maxLen: 1,
            projectName:"zjquality",
            label: "视频上传",//
            btnName: "选择视频",
            fileType: ["mp4"],
            must: true
        }
    ],
    onSave: function (obj, _params) {
        var new_params = {}
        if (_params.imageAddress.length == 0) {
            layer.alert("必须上传一个封面！", { icon: 0, }, function (index) {
                layer.close(index);
            });
            return;
        }
        if (_params.accessoryAddress.length == 0) {
            layer.alert("必须上传一个视频！", { icon: 0, }, function (index) {
                layer.close(index);
            });
            return;
        }
        new_params.accessoryName = _params.accessoryName;
        new_params.accessoryTime = _params.accessoryTime;
        new_params.accessorySize = _params.accessorySize;
        new_params.accessoryDesc = _params.accessoryDesc;
        new_params.imageAddress = _params.imageAddress[0].fileUrl;
        new_params.accessoryAddress = _params.accessoryAddress[0].fileUrl;
        new_params.accessoryLevel = _params.accessoryLevel;
        new_params.accessoryId = _params.accessoryId;
      //  Lny.log(new_params);
        l.ajax('updateAccessory', new_params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        var new_params = {}
        if (_params.imageAddress.length == 0) {
            layer.alert("必须上传一个封面！", { icon: 0, }, function (index) {
                layer.close(index);
            });
            return;
        }
        if (_params.accessoryAddress.length == 0) {
            layer.alert("必须上传一个视频！", { icon: 0, }, function (index) {
                layer.close(index);
            });
            return;
        }
        new_params.accessoryName = _params.accessoryName;
        new_params.accessorySize = _params.accessorySize;
        new_params.accessoryTime = _params.accessoryTime;
        new_params.accessoryDesc = _params.accessoryDesc;
        new_params.imageAddress = _params.imageAddress[0].fileUrl;
        new_params.accessoryAddress = _params.accessoryAddress[0].fileUrl;
        new_params.accessoryLevel = _params.accessoryLevel;
        new_params.infoId = _params.infoId;
    //    console.dir(new_params)
        l.ajax('addAccessory', new_params, function (data) {
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
                var newCheckedData = {}
                newCheckedData.accessoryName = checkedData[0].accessoryName;
                newCheckedData.accessorySize = checkedData[0].accessorySize;
                newCheckedData.accessoryDesc = checkedData[0].accessoryDesc;
                newCheckedData.accessoryTime = checkedData[0].accessoryTime;
                newCheckedData.accessoryId = checkedData[0].accessoryId;
                newCheckedData.imageAddress = [{ "fileUrl": checkedData[0].imageAddress, "fileName": "封面" }];
                newCheckedData.accessoryAddress = [{ "fileUrl": checkedData[0].accessoryAddress, "fileName": "视频" }];
                detailLayer.open(newCheckedData);
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
                l.delTableRowById("accessoryId", 'deleteAccessory', checkedData[0], function (data) {
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




