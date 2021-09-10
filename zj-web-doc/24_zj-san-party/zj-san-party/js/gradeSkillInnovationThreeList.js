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
            "data": "rankName",//接口对应字段
            "title": "级别",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [3],//第几列
            "data": "className",//接口对应字段
            "title": "类别",//表头名
            "defaultContent": "-",//默认显示
        },		
		{
            "targets": [4],//第几列
            "data": "scope",//接口对应字段
            "title": "奖励范围",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [5],//第几列
            "data": "score",//接口对应字段
            "title": "分值",//表头名
            "defaultContent": "-",//默认显示
        },   
        {
            "targets": [6],//第几列
            "data": "indate",//接口对应字段
            "title": "有效期限(年)",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "操作时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        }
		
    ]
});
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjSanDjGradeStandardListTwo"),
        params: {
		 pId: l.getUrlParam("id")
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
            name: "recordId",//输入字段名
        },
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "pId",//输入字段名
			defaultValue:l.getUrlParam("id"),
        },
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "gradeType",//输入字段名
			defaultValue:"JSCX4-2",
        }, 		
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "gradeTypeName",//输入字段名
			defaultValue:"技术创新成果奖励子集2",
        }, 
		{
            type: "text",//
            name: "pName",//
            label: "类别名称",//
            defaultValue:l.getUrlParam("className") ,
            immutableAdd:true,
            must: true
        },  
		{
            type: "hidden",//
            name: "scoreName",//
            label: "奖励名称",//
            defaultValue:l.getUrlParam("scoreName") ,
            immutableAdd:true,
            must: true
        },  
		{
            type: "hidden",//
            name: "rankName",//
            label: "级别名称",//
            defaultValue:l.getUrlParam("scoreName") ,
            immutableAdd:true,
            must: true
        },  
		{
            type: "hidden",//
            name: "className",//
            label: "类别名称",//
            defaultValue:l.getUrlParam("className") ,
            immutableAdd:true,
            must: true
        },        
		{
            type: "text",//
            name: "scope",//
            label: "奖励范围",//
            placeholder: "请输入奖励范围",
            must: true
        },
		{
            type: "number",//
            name: "score",//
            label: "分值",//
            placeholder: "请输入分值",
			must: true
        },
		{
            type: "text",//
            name: "indate",//
            label: "有效期限(年)",//
            placeholder: "请输入有效期",
            must: true
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateZjSanDjGradeStandard', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjSanDjGradeStandardTwo', _params, function (data) {
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
				l.ajax("batchDeleteUpdateZjSanDjGradeStandard", checkedData, function () {
					pager.page('remote')
				})
				layer.close(index);
            });
            }
            break;
    }
})