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
            "data": "code",//接口对应字段
            "title": "编码",//表头名
			"width": 80,
            "defaultContent": "-",//默认显示
			"render":function(data,index,row){
	            		   var a;
	            		   if(data){   
	            			   a = '<a style="color:blue;" onclick="myOpen1(\' '+row.rowIndex+ '\',\'' + 'xq' + '\')" href="javascript:void(0);">'+data+'</a>'
	            		   }
	            		   return a;
	        }
        },
		{
            "targets": [2],//第几列
            "data": "standardProcessName",//接口对应字段
            "title": "标准工序名称",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [3],//第几列
            "data": "unit",//接口对应字段
            "title": "单位",//表头名
			"width": 30,
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [4],//第几列
            "data": "constructionContent",//接口对应字段
            "title": "施工内容",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "valuationRules",//接口对应字段
            "title": "计价规则",//表头名
            "defaultContent": "-",//默认显示
        }     
    ]
});

var allData;
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjHwZyResourcePriceSubpackageDetailList"),
        params: {subpackageMainId:l.getUrlParam("id")   },
        success: function (result) {
            if (result.success) {
                var data = result.data;
				allData = data;
				if (result.data.length > 0) {
                    if (result.data[0].hideFlag == 0) {
                       
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
            name: "subpackageDetailId",//输入字段名
        }, 
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "subpackageMainId",//输入字段名
			defaultValue:l.getUrlParam("id") ,
        },
		{
            type: "text",//
            name: "chapter",//
            label: "章节名称",//
            defaultValue:l.getUrlParam("chapter") ,
            immutableAdd:true,
            must: true
        },
        {
            type: "text",//
            name: "code",//
            label: "编码",//
            placeholder: "请输入编码",
            must: true,
			immutable: true
        },
		{
            type: "text",//
            name: "standardProcessName",//
            label: "标准工序名称",//
            placeholder: "请输入标准工序名称",
            must: true
        },
		{
            type: "text",//
            name: "unit",//
            label: "单位",//
            placeholder: "请输入单位"
        },
		{
            type: "textarea",//
            name: "constructionContent",//
            label: "施工内容",//
            placeholder: "请输入施工内容"
        },
		{
            type: "textarea",//
            name: "valuationRules",//
            label: "计价规则",//
            placeholder: "请输入计价规则"
        },
		{
            type: "textarea",//
            name: "plain",//
            label: "平原",//
            placeholder: "请输入平原"
        },
		{
            type: "textarea",//
            name: "ridge",//
            label: "山岭",//
            placeholder: "请输入山岭"
        },
		{
            type: "textarea",//
            name: "levelRoad",//
            label: "等级路",//
            placeholder: "请输入等级路"
        },
		{
            type: "textarea",//
            name: "rebuildRoad",//
            label: "改建路",//
            placeholder: "请输入改建路"
        },
		{
            type: "textarea",//
            name: "ext1",//
            label: "华北",//
            placeholder: "请输入"
        },
		{
            type: "textarea",//
            name: "ext2",//
            label: "东北",//
            placeholder: "请输入"
        },
		{
            type: "textarea",//
            name: "ext3",//
            label: "华东",//
            placeholder: "请输入"
        },
		{
            type: "textarea",//
            name: "ext4",//
            label: "华中",//
            placeholder: "请输入"
        },
		{
            type: "textarea",//
            name: "ext5",//
            label: "华南",//
            placeholder: "请输入"
        },
		{
            type: "textarea",//
            name: "ext6",//
            label: "西南",//
            placeholder: "请输入"
        },
		{
            type: "textarea",//
            name: "ext7",//
            label: "西北",//
            placeholder: "请输入"
        },
		{
            type: "textarea",//
            name: "ext8",//
            label: "说明",//
            placeholder: "请输入"
        }
    ],
    onSave: function (obj, _params) {
         l.ajax('updateZjHwZyResourcePriceSubpackageDetail', _params, function (data) {
                pager.page('remote')
                obj.close()
            })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjHwZyResourcePriceSubpackageDetail', _params, function (data) {
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
            name: "subpackageDetailId",//输入字段名
        }, 
		{
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "subpackageMainId",//输入字段名
			defaultValue:l.getUrlParam("id") ,
        },
		{
            type: "text",//
            name: "chapter",//
            label: "章节名称",//
            defaultValue:l.getUrlParam("chapter") ,
            immutableAdd: true
        },
        {
            type: "text",//
            name: "code",//
            label: "编码",//
            placeholder: "请输入编码",
             immutableAdd: true
        },
		{
            type: "text",//
            name: "standardProcessName",//
            label: "标准工序名称",//
            placeholder: "请输入标准工序名称",
             immutableAdd: true
        },
		{
            type: "text",//
            name: "unit",//
            label: "单位",//
            placeholder: "请输入单位",
			 immutableAdd: true
        },
		{
            type: "textarea",//
            name: "constructionContent",//
            label: "施工内容",//
            placeholder: "请输入施工内容",
			 immutableAdd: true
        },
		{
            type: "textarea",//
            name: "valuationRules",//
            label: "计价规则",//
            placeholder: "请输入计价规则",
			 immutableAdd: true
        },
		{
            type: "textarea",//
            name: "plain",//
            label: "平原",//
            placeholder: "请输入平原",
			 immutableAdd: true
        },
		{
            type: "textarea",//
            name: "ridge",//
            label: "山岭",//
            placeholder: "请输入山岭",
			 immutableAdd: true
        },
		{
            type: "textarea",//
            name: "levelRoad",//
            label: "等级路",//
            placeholder: "请输入等级路",
			 immutableAdd: true
        },
		{
            type: "textarea",//
            name: "rebuildRoad",//
            label: "改建路",//
            placeholder: "请输入改建路",
			 immutableAdd: true
        },
		{
            type: "textarea",//
            name: "ext1",//
            label: "华北",//
            placeholder: "请输入",
			 immutableAdd: true
        },
		{
            type: "textarea",//
            name: "ext2",//
            label: "东北",//
            placeholder: "请输入",
			 immutableAdd: true
        },
		{
            type: "textarea",//
            name: "ext3",//
            label: "华东",//
            placeholder: "请输入",
			 immutableAdd: true
        },
		{
            type: "textarea",//
            name: "ext4",//
            label: "华中",//
            placeholder: "请输入",
			 immutableAdd: true
        },
		{
            type: "textarea",//
            name: "ext5",//
            label: "华南",//
            placeholder: "请输入",
			 immutableAdd: true
        },
		{
            type: "textarea",//
            name: "ext6",//
            label: "西南",//
            placeholder: "请输入",
			 immutableAdd: true
        },
		{
            type: "textarea",//
            name: "ext7",//
            label: "西北",//
            placeholder: "请输入",
			 immutableAdd: true
        },
		{
            type: "textarea",//
            name: "ext8",//
            label: "说明",//
            placeholder: "请输入",
			 immutableAdd: true
        }
    ],
})

var leading = $('#leading').detailLayer({
    layerArea:['410px', '310px'],
    layerTitle:['导入'],
        conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "subpackageMainId",//输入字段名
			defaultValue:l.getUrlParam("id") ,
        },
		{
            type: "upload",
            maxLen:1,
            name: "importFileList",
            label: "导入文件",
            btnName: "选择文件",
			projectName:"zj-assets-haiwei",
            fileType: ["xls", "xlsx"],
        }
    ],
    onAdd: function (obj, _params){
        if(_params.importFileList.length==0){
            layer.alert("请选择文件", { icon: 0, }, function (index) {
                layer.close(index);
            });
            return;
        }
        l.ajax('batchImportZjHwZyResourcePriceSubpackage', {"importFileList":_params.importFileList,"subpackageMainId":_params.subpackageMainId}, function (_params) {
            pager.page('remote')
            obj.close()
        })
    }
});

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
				l.ajax("batchDeleteUpdateZjHwZyResourcePriceSubpackageDetail", checkedData, function () {
					pager.page('remote')
				})
				layer.close(index);
            });
            }
            break;
		case "leading"://导入
            leading.open();
            break;
		case "derive"://导出
            var params = {};
            //params.deriveType =exportFlag;
            layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
                l.ajax('exportExcelZjHwZyResourcePriceSubpackageDetail', params, function (res) {
                    layer.alert("导出成功！", { icon: 0 }, function (index) {
                        layer.close(index);
                        window.location.href = res;
                    })
                })
            });
            break;	
    }
})


function myOpen1(index, type) {
	var rowData = allData[Number(index)];
	var params;
	switch (type){
	case 'xq':
		params = {};
		params.subpackageDetailId = rowData.subpackageDetailId;
		l.ajax('getZjHwZyResourcePriceSubpackageDetailDetail',params,function(res){
			personnelDetailLayer.open(res);
			$('#personnelDetailLayer .btn').hide();
		})
		break;	
	}
}