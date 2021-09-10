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
            "data": "materialQuotaClass",//接口对应字段
            "title": "类别",//表头名
            "defaultContent": "-",//默认显示
        }, 
		{
            "targets": [2],//第几列
            "data": "materialName",//接口对应字段
            "title": "材料名称/部位",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "constructionTechonlogy",//接口对应字段
            "title": "施工工艺",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [4],//第几列
            "data": "measure",//接口对应字段
            "title": "计量单位",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "lossRate",//接口对应字段
            "title": "损耗定额",//表头名
            "defaultContent": "-",//默认显示
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
            "title": "上传时间",//表头名
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
			    html += '<a style="color:blue;"  href="javascript:void(0);" onclick="myOpen1(\' ' + data.rowIndex + '\',\'' + 'xq' + '\')" href="javascript:void(0);">详情</a>';
                html += '</span>'	
                return html;
            }           
        }
    ]
});

var allData;
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjHwZyResourceMaterialQuotaList"),
        params: {        
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
				allData = data;
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
layerArea: ["100%", "100%"],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "materialQuotaId",//输入字段名
        },
        {
            type: "text",//
            name: "materialQuotaClass",//
            label: "类别",//
            placeholder: "类别",
            must: true
        },
		{
            type: "text",//
            name: "materialName",//
            label: "材料名称/部位",//
            placeholder: "请输入材料名称/部位",
            must: true
        },
		{
            type: "text",//
            name: "constructionTechonlogy",//
            label: "施工工艺",//
            placeholder: "请输入施工工艺"
        },
		{
            type: "text",//
            name: "measure",//
            label: "计量单位",//
            placeholder: "请输入计量单位",
            must: true
        },
		{
            type: "number",//
            name: "lossRate",//
            label: "损耗定额",//
            placeholder: "请输入损耗定额"
        },
		{
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入备注"
        },
		{
            type: "upload",//
            name: "materialQuotaFileList",//
            label: "附件上传",//
            btnName: "选择文件",
            projectName: "zj-hw-resource",
			uploadUrl:"uploadFilesByFileName",			
            fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf","zip","rar","dwg"]
        }
    ],
    onSave: function (obj, _params) {
         l.ajax('updateZjHwZyResourceMaterialQuota', _params, function (data) {
                pager.page('remote')
                obj.close()
            })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjHwZyResourceMaterialQuota', _params, function (data) {
                pager.page('remote')
                obj.close()
            })
    }
})
//详情
var personnelDetailLayer = $('#personnelDetailLayer').detailLayer({
layerArea: ["100%", "100%"],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "materialQuotaId",//输入字段名
        },
        {
            type: "text",//
            name: "materialQuotaClass",//
            label: "类别",//
            placeholder: "类别",
            immutableAdd: true
        },
        {
            type: "text",//
            name: "materialName",//
            label: "材料名称/部位",//
            placeholder: "请输入材料名称/部位",
            immutableAdd: true
        },
		{
            type: "text",//
            name: "constructionTechonlogy",//
            label: "施工工艺",//
            placeholder: "请输入施工工艺",
			 immutableAdd: true
        },
		{
            type: "text",//
            name: "measure",//
            label: "计量单位",//
            placeholder: "请输入计量单位",
             immutableAdd: true
        },
		{
            type: "number",//
            name: "lossRate",//
            label: "损耗定额",//
            placeholder: "请输入损耗定额",
			 immutableAdd: true
        },	
		{
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入备注",
			immutableAdd: true
        },
		{
            type: "upload",//
            name: "materialQuotaFileList",//
            label: "附件上传",//
            btnName: "选择文件",
            projectName: "zj-hw-resource",
			uploadUrl:"uploadFilesByFileName",
			immutableAdd: true,
            fileType: ["jpg", "jpeg", "png", "gif", "docx", "xls", "doc", "ppt", "xlsx", "pptx", "xlsm", "pdf","zip","rar","dwg"]
        }
		
    ],
})

var leading = $('#leading').detailLayer({
    layerArea:['410px', '310px'],
    layerTitle:['导入'],
        conditions: [
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
        l.ajax('batchImportZjHwZyResourceMaterialQuota', {"importFileList":_params.importFileList}, function (_params) {
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
				l.ajax("batchDeleteUpdateZjHwZyResourceMaterialQuota", checkedData, function () {
					pager.page('remote')
				})
				layer.close(index);
            });
            }
            break;
	    case "leading"://导入
            leading.open();
            break;
    }
})

function myOpen1(index, type) {
    var rowData = allData[Number(index)];
    var params;
    switch (type) {
        case 'xq':
            params = {};
            params.materialQuotaId = rowData.materialQuotaId;
            //console.log('params:',params)
            l.ajax('getZjHwZyResourceMaterialQuotaDetail', params, function (res) {
                personnelDetailLayer.open(res);
                $('#personnelDetailLayer .btn').hide();
            })
            break;
    }
}