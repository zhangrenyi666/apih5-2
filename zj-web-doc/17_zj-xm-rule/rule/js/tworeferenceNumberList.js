var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30);
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
            "data": "referenceNumberId",//接口对应字段
            "title": "文号编号",//表头名
            "defaultContent": "-",//默认显示
        }, 
		{
            "targets": [2],//第几列
            "data": "twoName",//接口对应字段
            "title": "局文号名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "referenceNumberName",//接口对应字段
            "title": "下发部门文号名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "modifyUserName",//接口对应字段
            "title": "操作员",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "操作时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
		{
            "targets": [6],//第几列
            "width": 100,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名
            "render": function (data) {
                var html = '';
                html += '<span class="dropDown">'
                html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">操作</a>';
                html += '<ul class="dropDown-menu menu radius box-shadow">';
                html += '<li><a href="javascript:void(0);" onclick="myOpen(\'年文号\',\'' + data.recordId + '\',\'' + data.referenceNumberName + '\',\'' + 'yearReferenceNumberList' + '\')">年文号</a></li>';
                html += '</ul></span>'
                return html;
            }
        }
    ]
});
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getTwoReferenceNumberList"),
        params: {
            highReferenceNumber: l.getUrlParam("id")
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
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "recordId",//输入字段名
        },
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "selectFlag",//输入字段名
			defaultValue:'1'
        },
        {
            type: "text",//
            name: "referenceNumberId",//
            label: "文号编号",//
            placeholder: "请输入文号编号",
            must: true,
            immutable:true
        },
        {
            type: "text",//
            name: "yearReferenceName",//
            label: "所属局文号名称",//
            defaultValue:l.getUrlParam("referenceNumberName") ,
            immutableAdd:true,
            must: true
        },
        {
            type: "text",//
            name: "referenceNumberName",//
            label: "下发部门文号名称",//
            placeholder: "请输入下发部门号名称",
            must: true
        }
        
    ],
     onSave: function (obj, _params) {
	 _params.highReferenceNumber=l.getUrlParam("id");
            l.ajax('updateZjXmReferenceNumber', _params, function (data) {
                pager.page('remote')
                obj.close()
            })    
    },
    onAdd: function (obj, _params) {
 _params.highReferenceNumber=l.getUrlParam("id");	
            l.ajax('addZjTwoReferenceNumber', _params, function (data) {
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
				l.ajax("batchDeleteUpdateZjXmReferenceNumber", checkedData, function () {
					pager.page('remote')
				})
				layer.close(index);
            });
            }
            break;
    }
})


function myOpen(title, id,referenceNumberName, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id+"&referenceNumberName="+encodeURI(referenceNumberName)+"&code="+code
    }
    layer.full(layer.open(options))
}