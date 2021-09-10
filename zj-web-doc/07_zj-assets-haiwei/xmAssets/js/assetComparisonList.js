var code = Lny.getUrlParam('code')
Lny.setCookie('code', code, 30)
var table = $('#table').DataTable({
    "info": false,//是否显示数据信息
    "paging": false,//是否开启自带分页
    "ordering": false, //是否开启DataTables的高度自适应
    "processing": false,//是否显示‘进度’提示
    "searching": false,//是否开启自带搜索
    "autoWidth": false,//是否监听宽度变化
    "initComplete": function (settings) {
        var innerTh = '<th rowspan="1"></th>';
        innerTh += '<th colspan="5" style="text-align:center;">本系统资产</th>';
        innerTh += '<th colspan="5" style="text-align:center;">浪潮系统资产</th>';
        $('#table').find("thead").prepend(innerTh);
    },
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
            'data': 'zcmc',
            "title": "资产名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {

            "targets": [2],//第几列
            "data": "zcbh",//接口对应字段
            "title": "资产编号",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "zcyz",//接口对应字段
            "title": "资产原值",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "zxjz",//接口对应字段
            "title": "资产净值",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "ljzj",//接口对应字段
            "title": "累计折旧",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "assetsName",//接口对应字段
            "title": "资产名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {

            "targets": [7],//第几列
            "data": "assetsNumber",//接口对应字段
            "title": "资产编号",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            // "targets": [8],//第几列
            "targets": [8],//第几列
            "data": function (row) {
                return row;
            },//接口对应字段
            "title": "资产原值",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                if (data.zcyz == data.assetsAccounted) {
                    if (data.assetsAccounted) {
                        return data.assetsAccounted;
                    } else {
                        return '-'
                    }
                } else {
                    if (data.assetsAccounted) {
                        return '<span style="color:red; font-weight:800;">' + data.assetsAccounted + '</span>';
                    } else {
                        return '-';
                    }
                }
            }
        },
        {
            "targets": [9],//第几列
            "data": function (row) {
                return row;
            },
            "title": "资产净值",//表头名
            "render": function (data) {
                if (data.zxjz == data.assetsNetValue) {
                    if (data.assetsNetValue) {
                        return data.assetsNetValue;
                    } else if(data.assetsNetValue == 0){
						return data.assetsNetValue;
					}else{
                        return '-'
                    }
                } else if(data.assetsNetValue == 0){
					 return '<span style="color:red; font-weight:800;">' + data.assetsNetValue + '</span>';
				}else{
                    if (data.assetsNetValue) {
                        return '<span style="color:red; font-weight:800;">' + data.assetsNetValue + '</span>';
                    } else {
                        return '-';
                    }
                }
            }
        },
        {
            "targets": [10],//第几列
            "data": function (row) {
                return row;
            },
            "title": "累计折旧",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                if (data.ljzj == data.accumulatedDepreciation) {
                    if (data.accumulatedDepreciation) {
                        return data.accumulatedDepreciation;
                    } else {
                        return '-'
                    }
                } else {
                    if (data.accumulatedDepreciation) {
                        return '<span style="color:red; font-weight:800;">' + data.accumulatedDepreciation + '</span>';
                    } else {
                        return '-';
                    }
                }
            }
        }
    ]
});

var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjXmAssetsComparisonList"),
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

$("body").on("click", "button", function () {
    checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "batchRepair":
            if (checkedData[0]) {
				var isNewAsset = true;
				$.each(checkedData, function(i, v){
					if(v.zxjz == null || v.ljzj == null ){
						isNewAsset = false;
					}
				});
				if(isNewAsset){
					l.ajax('batchRepair', { 'currentList': checkedData }, function (res) {
						layer.alert("修复成功", { icon: 0 }, function (index) {
							layer.close(index);
							pager.page('remote')
						})
					});
				}else{
					layer.alert("该资产没有折旧！")
				}
            } else {
                layer.alert('请选择数据！', { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
            return
    }
})