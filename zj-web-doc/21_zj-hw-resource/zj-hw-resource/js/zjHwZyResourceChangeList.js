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
            "data": "classType",//接口对应字段
            "title": "类别",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "0":
                        r = "公路工程"
                        break
                    case "1":
                        r = "市政工程"
                        break
                    case "2":
                        r = "铁路工程"
                        break
                    case "3":
                        r = "房建工程"
                        break
                }
                return r
            }
        },
        {
            "targets": [2],//第几列
            "data": "chapter",//接口对应字段
            "title": "章节名称",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [3],//第几列
            "data": "classify",//接口对应字段
            "title": "变更归类",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [4],//第几列
            "data": "point",//接口对应字段
            "title": "变更项点",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [5],//第几列
            "data": "reason",//接口对应字段
            "title": "变更理由",//表头名
            "defaultContent": "-",//默认显示
            "width": 80,
        },
        {
            "targets": [6],//第几列
            "data": "successKey",//接口对应字段
            "title": "变更成功关键点",//表头名
            "defaultContent": "-",//默认显示
            "width": 80,
        },
        {
            "targets": [7],//第几列
            "data": "listItem",//接口对应字段
            "title": "涉及费用清单项",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [8],//第几列
            "data": "reportAmount",//接口对应字段
            "title": "上报金额(元)",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [9],//第几列
            "data": "approvalAmount",//接口对应字段
            "title": "批复金额(元)",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [10],//第几列
            "data": "supportMaterial",//接口对应字段
            "title": "所需支撑性材料",//表头名
            "defaultContent": "-",//默认显示
            "width": 80,
        },
        {
            "targets": [11],//第几列
            "data": "ext1",//接口对应字段
            "title": "东北片区",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [12],//第几列
            "data": "ext2",//接口对应字段
            "title": "华北地区",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [13],//第几列
            "data": "ext3",//接口对应字段
            "title": "华中片区",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [14],//第几列
            "data": "ext4",//接口对应字段
            "title": "华东地区",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [15],//第几列
            "data": "ext5",//接口对应字段
            "title": "华南地区",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [16],//第几列
            "data": "ext6",//接口对应字段
            "title": "西南地区",//表头名
            "defaultContent": "-",//默认显示
            "width": 50,
        },
        {
            "targets": [17],//第几列
            "width": 50,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名
            "render": function (data) {
                var html = '';
                html += '<span class="dropDown">'
                html += '<a style="color:blue;"  href="javascript:void(0);" onclick="myOpen1(\' ' + data.rowIndex + '\',\'' + 'xq' + '\')" href="javascript:void(0);">详情</a>';
                html += '</span>'
                return html;
            }
        }
    ]
});
var classType = '';
var chapter = '';
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "select",
            name: "classType",
            label: "类别",
            defaultValue: '0',
            selectList: [//当类型为select时，option配置
                {
                    value: 0,//输入字段的值
                    text: "公路工程",//显示中文名
                },
                {
                    value: 1,//输入字段的值
                    text: "市政工程",//显示中文名
                },
                {
                    value: 2,//输入字段的值
                    text: "铁路工程",//显示中文名
                },
                {
                    value: 3,//输入字段的值
                    text: "房建工程",//显示中文名
                }
            ],
        },
        {
            type: "text",//三种形式：text,select,date,
            name: "key",//输入字段名
            label: "关键字",//输入字段对杨的中文名称
            placeholder: "请输入关键字",
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
        classType =  arr[0].value;
        chapter =  arr[1].value;
        pager.page('remote', 0, _params)
    },
    onReset: function (arr) {//重置按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
        classType =  '';
        chapter =  '';
        pager.page('remote', _params)
    }
})

var allData;
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjHwZyResourceChangeList"),
        params: {
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                allData = data;
                $.each(data, function (index, item) {
                    item.rowIndex = index
                })
                table.clear().rows.add(data).draw();
                Lny.log(data)
            } else {
                layer.alert(result.message, { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
        },

    }
});



var detailLayer = $('#detailLayer').detailLayer({
    layerArea: ["100%", "100%"],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "changeId",//输入字段名
        },
        {
            type: "select",
            name: "classType",
            label: "类别",
            must: true,
            selectList: [//当类型为select时，option配置
                {
                    value: 0,//输入字段的值
                    text: "公路工程",//显示中文名
                },
                {
                    value: 1,//输入字段的值
                    text: "市政工程",//显示中文名
                },
                {
                    value: 2,//输入字段的值
                    text: "铁路工程",//显示中文名
                },
                {
                    value: 3,//输入字段的值
                    text: "房建工程",//显示中文名
                }
            ],
        },
        {
            type: "text",//
            name: "chapter",//
            label: "章节名称",//
            placeholder: "请输入章节名称",
            must: true
        },
        {
            type: "text",//
            name: "classify",//
            label: "变更归类",//
            must: true
        },
        {
            type: "text",//
            name: "point",//
            label: "变更项点",//
            placeholder: "请输入变更项点",
            must: true
        },
        {
            type: "textarea",//
            name: "reason",//
            label: "变更理由",//
            placeholder: "请输入变更理由",
        },
        {
            type: "text",//
            name: "successKey",//
            label: "变更成功关键点",//
            placeholder: "请输入变更成功关键点",
        },
        {
            type: "text",//
            name: "listItem",//
            label: "涉及费用清单项",//
            placeholder: "请输入涉及费用清单项",
        },
        {
            type: "number",//
            name: "reportAmount",//
            label: "上报金额(元)",//
            placeholder: "请输入上报金额(元)",
        },
        {
            type: "number",//
            name: "approvalAmount",//
            label: "批复金额(元)",//
            placeholder: "请输入批复金额(元)",
        },
        {
            type: "textarea",//
            name: "supportMaterial",//
            label: "所需支撑性材料",//
            placeholder: "请输入所需支撑性材料",
        },
        {
            type: "textarea",//
            name: "ext1",//
            label: "东北片区",//
            placeholder: "请输入东北片区",
        },
        {
            type: "textarea",//
            name: "ext2",//
            label: "华北地区",//
            placeholder: "请输入华北地区",
        },
        {
            type: "textarea",//
            name: "ext3",//
            label: "华中片区",//
            placeholder: "请输入华中片区",
        },
        {
            type: "textarea",//
            name: "ext4",//
            label: "华东地区",//
            placeholder: "请输入华东地区",
        },
        {
            type: "textarea",//
            name: "ext5",//
            label: "华南地区",//
            placeholder: "请输入华南地区",
        },
        {
            type: "textarea",//
            name: "ext6",//
            label: "西南地区",//
            placeholder: "请输入西南地区",
        },
        {
            type: "upload",//
            name: "fileList",//
            label: "附件上传",//
            btnName: "选择文件",
            projectName: "zj-hw-resource",
            uploadUrl: "uploadFilesByFileName",
            //maxLen: 1,
            fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf", "zip", "rar", "dwg"]
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateZjHwZyResourceChange', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjHwZyResourceChange', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    }
})

var seeLayer = $('#seeLayer').detailLayer({
    layerArea: ["100%", "100%"],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "changeId",//输入字段名
        },
        {
            type: "select",
            name: "classType",
            label: "类别",
            immutableAdd: true,
            selectList: [//当类型为select时，option配置
                {
                    value: 0,//输入字段的值
                    text: "公路工程",//显示中文名
                },
                {
                    value: 1,//输入字段的值
                    text: "市政工程",//显示中文名
                },
                {
                    value: 2,//输入字段的值
                    text: "铁路工程",//显示中文名
                },
                {
                    value: 3,//输入字段的值
                    text: "房建工程",//显示中文名
                }
            ],
        },
        {
            type: "text",//
            name: "chapter",//
            label: "章节名称",//
            placeholder: "请输入章节名称",
            immutableAdd: true
        },
        {
            type: "text",//
            name: "classify",//
            label: "变更归类",//
            immutableAdd: true
        },
        {
            type: "text",//
            name: "point",//
            label: "变更项点",//
            placeholder: "请输入变更项点",
            immutableAdd: true
        },
        {
            type: "textarea",//
            name: "reason",//
            label: "变更理由",//
            placeholder: "请输入变更理由",
			immutableAdd: true
        },
        {
            type: "text",//
            name: "successKey",//
            label: "变更成功关键点",//
            placeholder: "请输入变更成功关键点",
			immutableAdd: true
        },
        {
            type: "text",//
            name: "listItem",//
            label: "涉及费用清单项",//
            placeholder: "请输入涉及费用清单项",
			immutableAdd: true
        },
        {
            type: "number",//
            name: "reportAmount",//
            label: "上报金额(元)",//
            placeholder: "请输入上报金额(元)",
			immutableAdd: true
        },
        {
            type: "number",//
            name: "approvalAmount",//
            label: "批复金额(元)",//
            placeholder: "请输入批复金额(元)",
			immutableAdd: true
        },
        {
            type: "textarea",//
            name: "supportMaterial",//
            label: "所需支撑性材料",//
            placeholder: "请输入所需支撑性材料",
			immutableAdd: true
        },
        {
            type: "textarea",//
            name: "ext1",//
            label: "东北片区",//
            placeholder: "请输入东北片区",
			immutableAdd: true
        },
        {
            type: "textarea",//
            name: "ext2",//
            label: "华北地区",//
            placeholder: "请输入华北地区",
			immutableAdd: true
        },
        {
            type: "textarea",//
            name: "ext3",//
            label: "华中片区",//
            placeholder: "请输入华中片区",
			immutableAdd: true
        },
        {
            type: "textarea",//
            name: "ext4",//
            label: "华东地区",//
            placeholder: "请输入华东地区",
			immutableAdd: true
        },
        {
            type: "textarea",//
            name: "ext5",//
            label: "华南地区",//
            placeholder: "请输入华南地区",
			immutableAdd: true
        },
        {
            type: "textarea",//
            name: "ext6",//
            label: "西南地区",//
            placeholder: "请输入西南地区",
			immutableAdd: true
        },
        {
            type: "upload",//
            name: "fileList",//
            label: "附件上传",//
            btnName: "选择文件",
            projectName: "zj-hw-resource",
            uploadUrl: "uploadFilesByFileName",
			immutableAdd: true,
            //maxLen: 1,
            fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf", "zip", "rar", "dwg"]
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
            projectName: "zj-assets-haiwei",
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
        l.ajax('batchImportResourceChange', { "importFileList": _params.importFileList }, function (_params) {
            pager.page('remote')
            obj.close()
        })
    }
});

$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            detailLayer.open();
            break;
        case "leading"://导入
            leading.open();
            break;
        case "exportOpen"://导入
            // var width = (window.screen.availWidth * 0.9);
            // var height = (window.screen.availHeight * 0.85);
            // var left = (window.screen.availWidth * 0.05);
            // var top = (window.screen.availHeight * 0.05);
            // window.open('http://192.168.1.223:8081/ureport/excel?_u=file:hwzyChange.xml&url=' + l.domain + '&classType=' + classType + '&chapter=' + chapter, '', 'width=' + width + ',height=' + height + ',scrollbars=yes,top=' + top + ',left=' + left);
            //window.open('http://wechat.zjyjhw.com:88/ureport/excel?_u=file:hwzyChange.xml&url=' + l.domain + '&classType=' + classType + '&chapter=' + chapter, '', 'width=' + width + ',height=' + height + ',scrollbars=yes,top=' + top + ',left=' + left);
            window.location.href = 'http://192.168.1.223:8081/ureport/excel?_u=file:hwzyChange.xml&url=' + l.domain + '&classType=' + classType + '&chapter=' + chapter;
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
                layer.confirm("确定删除？", { icon: 0, }, function (index) {
                    l.ajax("batchDeleteUpdateZjHwZyResourceChange", checkedData, function () {
                        pager.page('remote')
                    })
                    layer.close(index);
                });
            }
            break;
    }
})

function myOpen1(index, type) {
    var rowData = allData[Number(index)];
    var params;
    switch (type) {
        case 'xq':
            params = {};
            params.changeId = rowData.changeId;
            l.ajax('getZjHwZyResourceChangeDetail', params, function (res) {
                seeLayer.open(res);
                $('#seeLayer .btn').hide();
            })
            break;
    }
}
