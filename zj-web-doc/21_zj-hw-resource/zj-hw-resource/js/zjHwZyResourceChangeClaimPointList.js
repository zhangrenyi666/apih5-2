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
            "data": "classify",//接口对应字段
            "title": "变更归类",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [2],//第几列
            "data": "point",//接口对应字段
            "title": "变更项点",//表头名
            "defaultContent": "-",//默认显示
        },     
        {
            "targets": [3],//第几列
            "data": "reason",//接口对应字段
            "title": "变更理由",//表头名
            "defaultContent": "-",//默认显示
        },       
        {
            "targets": [4],//第几列
            "data": "successKey",//接口对应字段
            "title": "变更成功关键点",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [5],//第几列
            "data": "listItem",//接口对应字段
            "title": "涉及费用清单项",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [6],//第几列
            "data": "reportAmount",//接口对应字段
            "title": "上报金额(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [7],//第几列
            "data": "approvalAmount",//接口对应字段
            "title": "批复金额(元)",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [8],//第几列
            "data": "supportMaterial",//接口对应字段
            "title": "所需支撑性材料",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [9],//第几列
            "data": "ext1",//接口对应字段
            "title": "东北片区",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [10],//第几列
            "data": "ext2",//接口对应字段
            "title": "华北地区",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [11],//第几列
            "data": "ext3",//接口对应字段
            "title": "华中片区",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [12],//第几列
            "data": "ext4",//接口对应字段
            "title": "华东地区",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [13],//第几列
            "data": "ext5",//接口对应字段
            "title": "华南地区",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [14],//第几列
            "data": "ext6",//接口对应字段
            "title": "西南地区",//表头名
            "defaultContent": "-",//默认显示
        }       
    ]
});
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjHwZyResourceChangeClaimPointList"),
        params: {
            classifyId: l.getUrlParam("id")
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
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
            name: "pointId",//输入字段名
        },
        {
            type: "text",//
            name: "classify",//
            label: "变更归类",//
            defaultValue:l.getUrlParam("classify") ,
            immutableAdd:true,
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
        }	
    ],
    onSave: function (obj, _params) {
        _params.classifyId=l.getUrlParam("id")
        l.ajax('updateZjHwZyResourceChangeClaimPoint', _params, function (data) {
                pager.page('remote')
                obj.close()
            })
    },
    onAdd: function (obj, _params) {
        _params.classifyId=l.getUrlParam("id");
        l.ajax('addZjHwZyResourceChangeClaimPoint', _params, function (data) {
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
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
				layer.confirm("确定删除？", { icon: 0,}, function (index) {
				l.ajax("batchDeleteUpdateZjHwZyResourceChangeClaimPoint", checkedData, function () {
					pager.page('remote')
				})
				layer.close(index);
            });
            }
            break;
    }
})
