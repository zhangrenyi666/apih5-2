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
            "targets": [0],//第几列
            "data": "className",//接口对应字段
            "title": "类别名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [1],//第几列
            "data": "departmentName",//接口对应字段
            "title": "部门名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [2],//第几列
            "data": "departmentFullName",//接口对应字段
            "title": "部门全称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "assetsName",//接口对应字段
            "title": "资产名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "unitName",//接口对应字段
            "title": "单位名称",//表头名
            "defaultContent": "-",//默认显示
        },
		
        {
            "targets": [5],//第几列
            "data": "assetsAccounted",//接口对应字段
            "title": "资产原值",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "assetsNetValue",//接口对应字段
            "title": "资产净值",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "accumulatedDepreciation",//接口对应字段
            "title": "累计折旧",//表头名
            "defaultContent": "-",//默认显示
        }
    ]
});
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getLcAsstesList"),
        params: {
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
var leading = $('#leading').detailLayer({
    layerArea:['500px', '200px'],
    layerTitle:['导入'],
        conditions: [
        {
            type: "upload",
            maxLen:1,
            name: "importFileList",
            label: "导入文件",
            btnName: "选择文件",
			projectName:"zj-assets-haiwei",
            fileType: ["xls", "xlsx"],
        }
    ],
    onAdd: function (obj, _params){
        if(_params.importFileList.length==0){
            layer.alert("请选择文件", { icon: 0, }, function (index) {
                layer.close(index);
            });
            return;
        }
        // console.log('参数',{"fileUrl":_params.importFileList})
        l.ajax('batchInputLcAssets', {"importFileList":_params.importFileList}, function (_params) {
            pager.page('remote')
            obj.close()
        })
    }
});
var detailLayer = $('#detailLayer').detailLayer({
    conditions: [
    ],
    onSave: function (obj, _params) {
        l.ajax('updateLcAsstes', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addLcAsstes', _params, function (data) {
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
        case "leading"://导入
            leading.open();
            break;
		case "downExcel"://导入
             window.location.href = "http://wechat.zjyjhw.com/apihaiwei/template/zj-assets-haiwei/浪潮导入模板.xls";
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
                l.delTableRow("recordid", 'currentList', 'batchDeleteAssetComparison', checkedData, function (data) {
                    pager.page('remote')
                })//  删除url地址
            }
            break;
    }
})