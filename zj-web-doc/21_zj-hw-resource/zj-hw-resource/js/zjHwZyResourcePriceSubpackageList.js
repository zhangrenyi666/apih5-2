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
            "data": "chapter",//接口对应字段
            "title": "章节",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [2],//第几列
            "data": "remarks",//接口对应字段
            "title": "备注",//表头名
            "defaultContent": "-",//默认显示
        },        
        {
            "targets": [3],//第几列
            "data": "modifyUserName",//接口对应字段
            "title": "操作员",//表头名
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
				html += '<a style="color:blue;" href="javascript:void(0);"onclick="myOpen(\'章节详情列表\',\'' + data.subpackageMainId + '\',\'' + data.chapter + '\',\'' + 'zjHwZyResourcePriceSubpackageDetailList' +'\')">章节详情列表</a>';
                html += '</span>'	
                return html;
            }           
        }
    ]
});

var allData;
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjHwZyResourcePriceSubpackageMainList"),
        params: {        
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
				allData = data;
                if (result.data.length > 0) {
                    if (result.data[0].hideFlag == '0') {
                        
                    }else{
					  $("#edit").hide();
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

var detailLayer = $('#detailLayer').detailLayer({
  layerArea:['100%', '100%'],
    conditions: [	
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "subpackageMainId",//输入字段名
        },
        {
            type: "text",//
            name: "chapter",//
            label: "章节名称",//
            placeholder: "请输入章节名称",
			must:true,
            immutable: true
        },		
		{
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入备注"

        }     
    ],
    onSave: function (obj, _params) {
         l.ajax('updateZjHwZyResourcePriceSubpackageMain', _params, function (data) {
                pager.page('remote')
                obj.close()
            })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjHwZyResourcePriceSubpackageMain', _params, function (data) {
                pager.page('remote')
                obj.close()
            })
    }
})


//详情
var personnelDetailLayer = $('#personnelDetailLayer').detailLayer({
    layerArea:['100%', '100%'],
    conditions: [	
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "subpackageMainId",//输入字段名
        },
        {
            type: "text",//
            name: "chapter",//
            label: "章节名称",//
            placeholder: "请输入章节名称",
            immutableAdd: true
        },		
		{
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入备注",
			immutableAdd: true
        } 
    ],
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
				l.ajax("batchDeleteUpdateZjHwZyResourcePriceSubpackageMain", checkedData, function () {
					pager.page('remote')
				})
				layer.close(index);
            });
            }
            break;
    }
})

function myOpen(title, id,chapter, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id+"&chapter="+encodeURI(chapter)+"&code="+code
    }
    layer.full(layer.open(options))
}