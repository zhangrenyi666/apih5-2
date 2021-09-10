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
            "data": "checkDetail",//接口对应字段
            "title": "检查项明细",//表头名
            "defaultContent": "-",//默认显示
        },		
		{
            "targets": [3],//第几列
            "data": "requirInfo",//接口对应字段
            "title": "所需资料说明",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [4],//第几列
            "data": "assignUnit",//接口对应字段
            "title": "指定单位",//表头名
            "defaultContent": "-",//默认显示
        }
    ]
});
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjLhddJointSupCheckDetailList"),
        params: {
		 taskId: l.getUrlParam("id")
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
            name: "checkDetailId",//输入字段名
        },
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "taskId",//输入字段名
			defaultValue:l.getUrlParam("id"),
        },
		{
            type: "text",//
            name: "checkItem",//
            label: "检查项",//
            defaultValue:l.getUrlParam("checkItem") ,
            immutableAdd:true,
            must: true
        },  
		{
            type: "pickTree",//
            name: "oaCheckObject",//
            label: "指定单位(检查对象)",//           
            immutableAdd:true,
            pickType:{
		    	       department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
		    	       member: false,//成员列表对应接口字段名,false表示不开启该类
		    	    }
        },   
		{
            type: "text",//
            name: "checkDetail",//
            label: "检查项明细",//
            placeholder: "请输入检查项明细",
            must: true
        },
		{
            type: "textarea",//
            name: "requirInfo",//
            label: "所需资料说明",//
            placeholder: "请输入所需资料说明",
			must: true
        }		
    ],
    onSave: function (obj, _params) {
        l.ajax('addZjLhddJointSupCheckDetail', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    }
});
var editLayer = $('#editLayer').detailLayer({
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "checkDetailId",//输入字段名
        },
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "taskId",//输入字段名
			defaultValue:l.getUrlParam("id"),
        },
		{
            type: "text",//
            name: "checkItem",//
            label: "检查项",//
            defaultValue:l.getUrlParam("checkItem") ,
            immutableAdd:true,
            must: true
        },  
		{
            type: "pickTree",//
            name: "oaCheckObject",//
            label: "指定单位(检查对象)",//           
            immutableAdd:true,
            pickType:{
		    	       department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
		    	       member: false,//成员列表对应接口字段名,false表示不开启该类
		    	    }
        },   
		{
            type: "text",//
            name: "checkDetail",//
            label: "检查项明细",//
            placeholder: "请输入检查项明细",
            must: true
        },
		{
            type: "textarea",//
            name: "requirInfo",//
            label: "所需资料说明",//
            placeholder: "请输入所需资料说明",
			must: true
        }		
    ],
    onSave: function (obj, _params) {
        l.ajax('updateZjLhddJointSupCheckDetail', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    }
})
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
	var params = {};
    switch (name) {
        case "add":
		     l.ajax("getZjLhddJointSupTaskList", params, function (res) {							
				detailLayer.open(res[0]);
				})
            
            break;
        case "edit":
            if (checkedData.length == 1) {
                editLayer.open(checkedData[0]);
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
				l.ajax("batchDeleteUpdateZjLhddJointSupCheckDetail", checkedData, function () {
					pager.page('remote')
				})
				layer.close(index);
            });
            }
            break;
    }
})