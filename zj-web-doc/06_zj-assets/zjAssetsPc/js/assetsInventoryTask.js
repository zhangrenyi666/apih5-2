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
            "data": "inventoryTitle",//接口对应字段
            "title": "盘点任务标题",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "pdrq",//接口对应字段
            "title": "盘点任务开始时间",//表头名
            "defaultContent": "-",//默认显示
			"render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
        }
		},
		 {
            "targets": [4],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "盘点任务结束时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
				if(data != null){
					return l.dateFormat(new Date(data), "yyyy-MM-dd");
				}                
            }
        },	
		 {
            "targets": [5],//第几列
            "data": "inventoryTaskState",//接口对应字段
            "title": "盘点任务状态",//表头名
            "defaultContent": "-",//默认显示
			   "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "0":
                        r = "未开始"
                        break
                    case "1":
                        r = "已完成"
                        break
                }
                return r
            }
        },
        {
        	"targets": [6],//第几列
            "width": 80,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "盘点明细",//表头名
            "render": function (data) {
	            		   var html = '';
	            		   html += '<span class="dropDown">'
	            		   html += '<a style="color:blue;" href="javascript:void(0);" onclick="myOpen(\'资产盘点列表查询\',\'' + data.recordid + '\',\'' + 'InventoryTaskDetailsList'+ '\',\'' + data.inventoryTitle + '\')">盘点明细</a>';
	            		   html += '</span>'
	            			   return html;
	            	   }
        }
    ]
});


var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getinventoryTaskList"),
        params: {
            sszclx1: "",
            sszclx2: "",
            cfdd1: "",
            cfdd2: "",
            zcmc: "",
            sybm: "",
            syr: "",
            cwbh: "",
            zcztdm: "",
			sydw:"",
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



var addDetailLayer = $('#addDetailLayer').detailLayer({
    layerArea:['100%', '100%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "recordid",//输入字段名
        },       
        {
            type: "text",
            name: "inventoryTitle",
            label: "盘点标题",
            must: true
        },
        {
            type: "date",//
            name: "pdrq",//
            label: "盘点日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"pdrq",
		    must: true
			
        }
    ],
    onSave: function (obj, _params) {
            l.ajax('updateInventoryTask', _params, function (_params) {
                pager.page('remote');
                obj.close()
            })
    
    },
    onAdd: function (obj, _params) {
            l.ajax('addInventoryTask', _params, function (_params) {
                pager.page('remote')
                obj.close()
            })
            }


})

var editDetailLayer = $('#editDetailLayer').detailLayer({
    layerArea:['100%', '100%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "recordid",//输入字段名
        },
        {
            type: "text",
            name: "inventoryTitle",
            label: "盘点标题",
            must: true
        },
        {
            type: "date",//
            name: "pdrq",//
            label: "盘点开始日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"pdrq",
		    must: true,
		    immutableAdd:true
			
        },
		{
            type: "date",//
            name: "pdrq",//
            label: "盘点结束日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"pdrq",
		    must: true
			
        }
    ],
    onSave: function (obj, _params) {
            // console.log(_params)
            l.ajax('updateInventoryTask', _params, function (_params) {
                pager.page('remote');
                obj.close()
            })
    },
    onAdd: function (obj, _params) {
   
			// console.log(_params)
            l.ajax('addInventoryTask', _params, function (_params) {
				
                pager.page('remote')
                obj.close()
            })
            }

});





$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            // detailLayer.open();
            addDetailLayer.open();
            break;
        case "edit":
            if (checkedData.length == 1) {
                // console.log('数据是:',checkedData[0])
                editDetailLayer.open(checkedData[0]);                
            } else if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            } else {
                layer.alert("只能选择一个", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            }
            break;
        case "del":
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0 }, function (index) {
                    layer.close(index);
                });
            } else {
				 layer.confirm("确定删除？", { icon: 0,}, function (index) {
               /*  l.delTableRow("recordid", 'currentList', 'batchDeleteInventoryTask', checkedData, function (data) {
                    pager.page('remote')
                })//  删除url地址 */
				l.ajax("batchDeleteInventoryTask", checkedData, function () {
					pager.page('remote')
				})
				layer.close(index);
            });
            }
            break;
    }
})
function myOpen(title, id, url,titlen) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id+"&titlen="+titlen
    }
    layer.full(layer.open(options))
}