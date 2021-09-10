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
            "data": "modifyTime",//接口对应字段
            "title": "操作时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
		{
            "targets": [5],//第几列
            "width": 100,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名			
            "render": function (data) {
                var html = '';
                html += '<span class="dropDown">'
                html += '<a style="color:blue;" href="javascript:void(0);" onclick="myOpen(\'添加奖励范围\',\'' + data.recordId + '\',\'' + data.scoreName + '\',\'' + data.className + '\',\'' + 'gradePatentThreeList' + '\')">添加奖励范围</a>';
                html += '</ul></span>'
                return html;
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
			defaultValue:"ZL6-1",
        }, 		
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "gradeTypeName",//输入字段名
			defaultValue:"专利奖励子集",
        }, 
		{
            type: "text",//
            name: "pName",//
            label: "级别名称",//
            defaultValue:l.getUrlParam("scoreName") ,
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
            type: "text",//
            name: "className",//
            label: "类别",//
            placeholder: "请输入类别",
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

function myOpen(title, id,scoreName,className, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id+"&scoreName="+encodeURI(scoreName)+"&className="+encodeURI(className)+"&code="+code
    }
    layer.full(layer.open(options))
}