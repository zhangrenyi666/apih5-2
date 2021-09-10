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
            "data": "unitName",//接口对应字段
            "title": "申请单位",//表头名
            "defaultContent": "-",//默认显示
        },
		 {
            "targets": [3],//第几列
            "data": "applyUserName",//接口对应字段
            "title": "申请人",//表头名
            "defaultContent": "-",//默认显示
        }, 
		{
            "targets": [4],//第几列
            "data": "applyMoneySmall",//接口对应字段
            "title": "申请金额",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "title",//接口对应字段
            "title": "党费明细",//表头名
            "defaultContent": "-",//默认显示
        },        
        {
            "targets": [6],//第几列
            "data": "createTime",//接口对应字段
            "title": "申请时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [7],//第几列
            "data": "phone",//接口对应字段
            "title": "联系方式",//表头名
            "defaultContent": "-",//默认显示
        }
    ]
});
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjFlowPartyFeeUseList"),
        params: {
		apih5FlowStatus:"2"
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

$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
       case "derive"://党费使用导出
		 var params = {}	  
             layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
                l.ajax('exportExcelZjFlowPartyFeeUse', params, function (res) {
                    layer.alert("导出成功！", { icon: 0 }, function (index) {
                        layer.close(index);
                        window.location.href = res;
                    })
                })
            }); 			
            break
    }
})