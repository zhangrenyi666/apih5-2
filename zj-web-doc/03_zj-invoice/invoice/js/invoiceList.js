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
            "data": "companyName",//接口对应字段
            "title": "公司名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "taxNumber",//接口对应字段
            "title": "税号",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "unitAddress",//接口对应字段
            "title": "单位地址",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "companyPhone",//接口对应字段
            "title": "公司电话",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "bank",//接口对应字段
            "title": "开户银行",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "bankAccount",//接口对应字段
            "title": "银行账号",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [8],//第几列
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
            "targets": [9],//第几列
            "data": "modifyUser",//接口对应字段
            "title": "更新者",//表头名
            "defaultContent": "-",//默认显示
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "companyName",//输入字段名
            label: "公司名称",//输入字段对杨的中文名称
            placeholder: "请输入公司名称",
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
        pager.page('remote', _params)
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
        url: l.getApiUrl("getZjInvoiceList"),
        params: {
            companyName: "",
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
            name: "invoiceId",//输入字段名
        },
        {
            type: "text",//
            name: "companyName",//
            label: "公司名称",//
            placeholder: "请输入公司名称(必填)",
            must: true
        },
        {
            type: "text",//
            name: "taxNumber",//
            label: "税号",//
            placeholder: "请输入税号(企业报销时必填)",
        },
        {
            type: "text",//
            name: "unitAddress",//
            label: "单位地址",//
            placeholder: "请输入公司地址",
        },
        {
            type: "text",//
            name: "companyPhone",//
            label: "公司电话",//
            placeholder: "请输入公司电话",
        },
        {
            type: "text",//
            name: "bank",//
            label: "开户银行",//
            placeholder: "请输入开户银行",
        },
        {
            type: "number",//
            name: "bankAccount",//
            label: "银行账号",//
            placeholder: "请输入银行账号",
        },
		{
            type: "upload",//
            name: "invoiceUrl",
            label: "普通发票上传",//
            btnName: "选择图片",
            projectName: "zjinvoice",
            maxLen: 1,
            must: true,
            fileType: ["jpg", "jpeg", "png", "gif", "bmp"],
        },
        {
            type: "upload",//
            name: "qrcodeUrl",
            label: "增值税发票上传",//
            btnName: "选择图片",
            projectName: "zjinvoice",
            maxLen: 1,
            must: true,
            fileType: ["jpg", "jpeg", "png", "gif", "bmp"],
        } 
    ],
    onSave: function (obj, _params) {
        var new_params = {}
        for (var key in _params) {
            if (_params.hasOwnProperty(key)) {
                if (key == "qrcodeUrl") {
                    if(_params[key].length == 0){
                        layer.alert('请上传增值税图片！');
                        return;
                    }
                    new_params[key] =  _params[key][0].fileUrl;
                } else if (key == "invoiceUrl"){
					if(_params[key].length == 0){
                        layer.alert('请上传普通发票图片！');
                        return;
                    }
					  new_params[key] =  _params[key][0].fileUrl;
				}else {
                    new_params[key] = _params[key]
                }
            }
        }
        l.ajax('updateZjInvoice', new_params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        var new_params = {}
        for (var key in _params) {
            if (_params.hasOwnProperty(key)) {
                if (key == "qrcodeUrl") {
                    if(_params[key].length == 0){
                        layer.alert('请上传增值税图片！');
                        return;
                    }
                    new_params[key] = _params[key][0].fileUrl
                } else if (key == "invoiceUrl"){
					if(_params[key].length == 0){
                        layer.alert('请上传普通发票图片！');
                        return;
					}
					new_params[key] = _params[key][0].fileUrl
                }else {
                    new_params[key] = _params[key]
                }
            }
        }
        //Lny.log(new_params)
        l.ajax('addZjInvoice', new_params, function (data) {
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
            for (var key in checkedData[0]) {
                if (checkedData[0].hasOwnProperty(key)) {
                        if (key == "qrcodeUrl" && checkedData[0][key]) {
                            newCheckedData[key] = [{ fileUrl: checkedData[0][key], fileName: "增值税图片"}]
                        } else if (key == "invoiceUrl" && checkedData[0][key]) {
                            newCheckedData[key] = [{ fileUrl: checkedData[0][key], fileName: "普通发票图片"}]
                        } 										
						else {
                            newCheckedData[key] = checkedData[0][key];
                        }
                    }
                }
                detailLayer.open(newCheckedData);
                // detailLayer.open(checkedData[0]);
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
            }else {
				/* l.delTableRowNew("invoiceId", 'batchDeleteUpdateZjInvoice', checkedData, function (data) {
                    pager.page('remote')
                })// 删除url地址 */
				l.ajax("batchDeleteUpdateZjInvoice", checkedData, function () {
					pager.page('remote')
				})
            }
            break;
    }
})
function myOpen(title, id, title, url) {
    // Lny.log(id)
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id+"&title="+title
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
