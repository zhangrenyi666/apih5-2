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
            "render": function (data,_,rowData) {
                if(rowData.warnFlag === 'red'){
                    return '<span style="color:red;">'+ data + 1 +'</span>';
                }
                return data + 1;
            }
        },
        {
            "targets": [2],//第几列
            "data": "sszclx1Name",//接口对应字段
            "title": "资产大类",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data,_,rowData) {
                if(rowData.warnFlag === 'red'){
                    return '<span style="color:red;">'+ data +'</span>';
                }
                return data;
            }
        },
        {
            "targets": [3],//第几列
            "data": "sszclx2Name",//接口对应字段
            "title": "资产小类",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data,_,rowData) {
                if(rowData.warnFlag === 'red'){
                    return '<span style="color:red;">'+ data +'</span>';
                }
                return data;
            }
        },
        {
            "targets": [4],//第几列
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "财务编号",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                var a;
                if (data) {
                    a = '<a style="color:blue;" onclick="myOpenForPurchase(\'资产详情查询\',\'' + data.recordid + '\',\'' + 'assetDetails' + '\')" href="javascript:void(0);">' + data.cwbh + '</a>'
                }
                return a;
            }
        },
        {
            "targets": [5],//第几列
            "data": "zcmc",//接口对应字段
            "title": "资产名称",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data,_,rowData) {
                if(rowData.warnFlag === 'red'){
                    return '<span style="color:red;">'+ data +'</span>';
                }
                return data;
            }
        },
        {
            "targets": [6],//第几列
            "data": "sydwName",//接口对应字段
            "title": "使用单位",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data,_,rowData) {
                if(rowData.warnFlag === 'red'){
                    return '<span style="color:red;">'+ data +'</span>';
                }
                return data;
            }
        },
        {
            "targets": [7],//第几列
            "data": "grrq",//接口对应字段
            "title": "购入日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data,_,rowData) {
                if(rowData.warnFlag === 'red'){
                    return '<span style="color:red;">'+ (data) ? l.dateFormat(new Date(data), "yyyy-MM-dd") : "-" +'</span>';
                }
                return (data) ? l.dateFormat(new Date(data), "yyyy-MM-dd") : "-";
            }
        },
        {
            "targets": [8],//第几列
            "data": "zcztName",//接口对应字段
            "title": "资产状态",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data,_,rowData) {
                if(rowData.warnFlag === 'red'){
                    return '<span style="color:red;">'+ data +'</span>';
                }
                return data;
            }
        },
		{
            "targets": [9],//第几列
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "资产编号",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                var a;
                if (data) {
                    a = '<a style="color:blue;" onclick="myOpenForPurchase(\'资产详情查询\',\'' + data.recordid + '\',\'' + 'assetDetails' + '\')" href="javascript:void(0);">' + data.zcbh + '</a>'
                }
                return a;
            }
        },
        /* {
            "targets": [9],//第几列
            "data": "wxztdmName",//接口对应字段
            "title": "维修状态",//表头名
            "defaultContent": "-",//默认显示
        }, */
        {
            "targets": [10],//第几列
            "data": "glryName",//接口对应字段
            "title": "管理员",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data,_,rowData) {
                if(rowData.warnFlag === 'red'){
                    return '<span style="color:red;">'+ data +'</span>';
                }
                return data;
            }
        },
        {
            "targets": [11],//第几列
            "data": "createTime",//接口对应字段
            "title": "操作时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data,_,rowData) {
                if(rowData.warnFlag === 'red'){
                    return '<span style="color:red;">'+ l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss") +'</span>';
                }
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [12],//第几列
            "width": 80,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名
            "render": function (data) {
                var html = '';
                html += '<span class="dropDown">'
                html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">操作</a>';
                html += '<ul class="dropDown-menu menu radius box-shadow">'
                if (data.zcztdm == "1") {//正常使用
                    if (data.wxztdm == "1") {
                        html = '-';
                    } else {
                        html += '<li><a href="javascript:void(0);" onclick="myOpenForPurchase(\'资产编辑\',\'' + data.recordid + '\',\'' + 'assetDetailsEdit' + '\')">编辑</a></li>';
                        if (data.loginCorpFlag == "sgs") {
                            html += '<li><a href="javascript:void(0);" onclick="myOpenFlow(\'三公司报废申请\',\'' + data.recordid + '\',\'' + 'sgsZcScrapApproval' + '\')">三公司报废申请</a></li>';
                            html += '<li><a href="javascript:void(0);" onclick="myOpenFlow(\'资产调拨申请\',\'' + data.recordid + '\',\'' + 'zjZcTransferApproval' + '\')">调拨申请</a></li>';
                        } else if (data.loginCorpFlag == "zcb") {
                            html += '<li><a href="javascript:void(0);" onclick="myOpenFlow(\'资产报废申请\',\'' + data.recordid + '\',\'' + 'assetsScrapApproval' + '\')">报废申请</a></li>';
                            html += '<li><a href="javascript:void(0);" onclick="myOpenFlow(\'总承包调拨申请\',\'' + data.recordid + '\',\'' + 'zcbZcTransferApproval' + '\')">总承包调拨申请</a></li>';
                        } else if (data.loginCorpFlag == "qs") {
						    html += '<li><a href="javascript:void(0);" onclick="myOpenFlow(\'桥隧资产报废申请\',\'' + data.recordid + '\',\'' + 'assetsScrapApprovalForQs' + '\')">桥隧资产报废申请</a></li>';
                            html += '<li><a href="javascript:void(0);" onclick="myOpenFlow(\'桥隧资产调拨申请\',\'' + data.recordid + '\',\'' + 'zjZcTransferApprovalForQs' + '\')">桥隧资产调拨申请</a></li>';
						 }else {
                            html += '<li><a href="javascript:void(0);" onclick="myOpenFlow(\'资产报废申请\',\'' + data.recordid + '\',\'' + 'assetsScrapApproval' + '\')">报废申请</a></li>';
                            html += '<li><a href="javascript:void(0);" onclick="myOpenFlow(\'资产调拨申请\',\'' + data.recordid + '\',\'' + 'zjZcTransferApproval' + '\')">调拨申请</a></li>';
                        }
                    }
                    if (data.wxztdm == "2" || data.wxztdm == null || data.wxztdm == "") {//维修状态 完成
                        html += '<li><a href="javascript:void(0);" onclick="myOpenForPurchase(\'资产维修新增\',\'' + data.recordid + '\',\'' + 'assetsRepairList' + '\')">资产维修</a></li>';
                    }
                } else if (data.zcztdm == "2") {//未验收
				 html += '<li><a href="javascript:void(0);" onclick="myOpenForPurchase(\'资产编辑\',\'' + data.recordid + '\',\'' + 'assetDetailsEdit' + '\')">编辑</a></li>';
				      if(data.loginCorpFlag == "qs"){
					   html += '<li><a href="javascript:void(0);" onclick="myOpenFlow(\'桥隧资产验收申请\',\'' + data.recordid + '\',\'' + 'assetsCheckApprovalForQs' + '\')">桥隧资产验收申请</a></li>';
				      }else{
					   html += '<li><a href="javascript:void(0);" onclick="myOpenFlow(\'资产验收申请\',\'' + data.recordid + '\',\'' + 'assetsCheckApproval' + '\')">资产验收申请</a></li>';					  
					  }                  
                } else if (data.zcztdm == "3" || data.zcztdm == "5" || data.zcztdm == "6" || data.zcztdm == "7" || data.zcztdm == "8" || data.zcztdm == "9") {//已报废、验收中、报废中、购置中、调拨中、调拨的
                    html = '-';
                } else if (data.zcztdm == "4") {//闲置
                    html = '<li><a href="javascript:void(0);" onclick="myOpenForPurchase(\'资产恢复=待定\',\'' + data.recordid + '\',\'' + 'assetsXZList' + '\')">资产恢复</a></li>';
                }
                html += '</ul></span>'
                return html;
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
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getAssetsList"),
        params: {},
        success: function (result) {
            if (result.success) {
                var data = result.data;
                allData = data;
                if (result.data.length > 0) {
                    if (result.data[0].isCompany == 0) {
                        $("#delete").hide();
                    }
					
					if (result.data[0].loginCorpFlag == 'stzg') {
                        $("#addMain").hide();
                        $("#qsAdd").hide();
                        $("#add").hide();
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
        case "addMain":
            myOpenForPurchase('局资产购置', '', 'zjZcPurchaseApprovalForJu')
            break;
		case "qsAdd":
            myOpenForPurchase('桥隧资产购置', '', 'zjZcPurchaseApprovalForQs')
            break;
        case "add":
            myOpenForPurchase('分公司资产购置流程发起', '', 'zjZcPurchaseApproval')
            break;
			  case "stzgAdd":
            myOpenForPurchase('世通重工资产申请', '', 'zjZcPurchaseApprovalForStzg')
            break;
        case "del":
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.confirm("确定删除？", { icon: 0, }, function (index) {
                    l.ajax("batchDeleteAssets", checkedData, function () {
                        pager.page('remote')
                    })
                    layer.close(index);
                });
            }
            break;
        case "leading"://导入
            leading.open();
            break;  
		case "downExcel"://导入
             window.location.href = "http://weixin.fheb.cn:99/apiwoa/template/zj-assets/导入模板里面有详细字段备注.xlsx";
            break;
        case "derive"://导出
            var params = {};
            params.sydwName = $('[name = "sydwid"]').val();
            params.startTime = $('[name = "startTime"]').val();
            params.endTime = $('[name = "endTime"]').val();
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
//资产验收、报废、调拨 流程申请打开的弹窗
function myOpenFlow(title, sszcid, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?sszcid=" + sszcid + "&code=" + code
    }
    myOpenLayer = layer.open(options)
    layer.full(myOpenLayer)
}
//资产详情、购置等打开的弹窗
function myOpenForPurchase(title, id, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id + "&code=" + code
    }
    myOpenLayer = layer.open(options)
    layer.full(myOpenLayer)
}