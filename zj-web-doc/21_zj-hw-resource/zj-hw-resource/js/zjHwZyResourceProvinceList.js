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
            "data": "areaCityNumber",//接口对应字段
            "title": "区域、城市编号",//表头名
            "defaultContent": "-",//默认显示
        }, 
		{
            "targets": [2],//第几列
            "data": "provinceName",//接口对应字段
            "title": "区域名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "areaCityName",//接口对应字段
            "title": "省名称",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [4],//第几列
            "data": "remarks",//接口对应字段
            "title": "备注",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "state",//接口对应字段
            "title": "状态",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "0":
                        r = "启用"
                        break
                    case "1":
                        r = "停用"
                        break
                }
                return r
            }
        },
        {
            "targets": [6],//第几列
            "data": "modifyUserName",//接口对应字段
            "title": "操作员",//表头名
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
        },
		{
            "targets": [8],//第几列
            "width": 100,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名
		    "render": function (data) {
                var html = '';
                html += '<span class="dropDown">'
			    html += '<a style="color:blue;" href="javascript:void(0);"onclick="myOpen(\'市列表\',\'' + data.areaCityId + '\',\'' + data.areaCityName + '\',\'' + 'zjHwZyResourceCityList' + '\')">市列表</a>';
                html += '</span>'	
                return html;
            } 
        }
    ]
});
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjHwZyResourceProvinceList"),
        params: {
            higherAreaId: l.getUrlParam("id")
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
            name: "areaCityId",//输入字段名
        },
        {
            type: "text",//
            name: "areaCityNumber",//
            label: "区域、城市编号",//
            placeholder: "请输入区域、城市编号",
            must: true,
            immutable:true
        },
        {
            type: "text",//
            name: "yearReferenceName",//
            label: "所属区域名称",//
            defaultValue:l.getUrlParam("areaCityName") ,
            immutableAdd:true,
            must: true
        },
        {
            type: "text",//
            name: "areaCityName",//
            label: "省名称",//
            placeholder: "请输入下发部门号名称",
            must: true
        },
		{
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入备注",

        },
        {
            type: "select",
            name: "state",
            label: "状态",
            selectList: [//当类型为select时，option配置
                {
                    value: 0,//输入字段的值
                    text: "启用",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "停用",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        }
        
    ],
     onSave: function (obj, _params) {
	 _params.higherAreaId=l.getUrlParam("id");
            l.ajax('updateZjHwZyResourceAreaCity', _params, function (data) {
                pager.page('remote')
                obj.close()
            })    
    },
    onAdd: function (obj, _params) {
 _params.higherAreaId=l.getUrlParam("id");	
            l.ajax('addZjHwZyResourceTwo', _params, function (data) {
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
				l.ajax("batchDeleteUpdateZjHwZyResourceAreaCity", checkedData, function () {
					pager.page('remote')
				})
				layer.close(index);
            });
            }
            break;
    }
})


function myOpen(title, id,areaCityName, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id+"&areaCityName="+encodeURI(areaCityName)+"&code="+code
    }
    layer.full(layer.open(options))
}