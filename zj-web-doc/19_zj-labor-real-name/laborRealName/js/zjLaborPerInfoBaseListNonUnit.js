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
            "data": "cropName",//接口对应字段
            "title": "公司名称",//表头名
            "defaultContent": "-",//默认显示
        },
		{
            "targets": [3],//第几列
            "data": "projectName",//接口对应字段
            "title": "项目名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "name",//接口对应字段
            "title": "姓名",//表头名
            "defaultContent": "-",//默认显示          
        },     
        {
            "targets": [5],//第几列
            "data": "age",//接口对应字段
            "title": "年龄",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "nativePlace",//接口对应字段
            "title": "籍贯",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "idCard",//接口对应字段
            "title": "身份证号",//表头名
            "defaultContent": "-",//默认显示
        },
		 {
            "targets": [8],//第几列
            "width": 80,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名
            "render": function (data) {
                var html = '';
                html += '<span class="dropDown">'
                html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">操作</a>';
                html += '<ul class="dropDown-menu menu radius box-shadow">'
                html += '<li><a href="javascript:void(0);" onclick="myOpen(\'添加证件\',\'' + data.recordid + '\',\'' + 'zjLaborSpecialOperationList' + '\')">添加证件</a></li>';
                html += '</ul></span>'
                return html;
            }
        }
    ]
});
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjLaborPerInfoBaseList"),
        params: {
		perType: "3"
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
var $table = $('<table class="table table-border table-bordered radius"/>')
 var $tr = $("<tr/>")
        var $th = $("<th/>")
        var $td = $("<td/>")		
        $table.append($tr.clone().append($th.clone().attr("colspan", "100%").addClass("text-c").html("非本单位人员信息")))
       
	   var line0 = $tr.clone()
        var cell0_0 = $th.clone().attr("data-cellName", "name")
		var cell0_1 = $td.clone().attr("data-cellName", "name")
		var cell0_2 = $th.clone().attr("data-cellName", "projectName")
		var cell0_3 = $td.clone().attr("data-cellName", "projectName")
		var cell0_4 = $th.clone().attr("data-cellName", "sex")
		var cell0_5 = $td.clone().attr("data-cellName", "sex")
        line0.append(cell0_0).append(cell0_1).append(cell0_2).append(cell0_3).append(cell0_4).append(cell0_5)		
        $table.append(line0)
		
        var line1 = $tr.clone()
        var cell1_0 = $th.clone().attr("data-cellName", "age")
        var cell1_1 = $td.clone().attr("data-cellName", "age")
        var cell1_2 = $th.clone().attr("data-cellName", "nativePlace")
        var cell1_3 = $td.clone().attr("data-cellName", "nativePlace")
        var cell1_4 = $th.clone().attr("data-cellName", "typeOfWork")
        var cell1_5 = $td.clone().attr("data-cellName", "typeOfWork")
        line1.append(cell1_0).append(cell1_1).append(cell1_2).append(cell1_3).append(cell1_4).append(cell1_5)		
        $table.append(line1)
		
        var line2 = $tr.clone()	
        var cell2_0 = $th.clone().attr("data-cellName", "idCard")
        var cell2_1 = $td.clone().attr("data-cellName", "idCard")
        var cell2_2 = $th.clone().attr("data-cellName", "idCardPhotoFileList")
        var cell2_3 = $td.clone().attr("colspan", "3").attr("data-cellName", "idCardPhotoFileList")
        line2.append(cell2_0).append(cell2_1).append(cell2_2).append(cell2_3)
        $table.append(line2)
		
        var line3 = $tr.clone()
        var cell3_0 = $th.clone().attr("data-cellName", "cropName")
		var cell3_1 = $td.clone().attr("data-cellName", "cropName")
        var cell3_2 = $th.clone().attr("data-cellName", "perPhotoFileList")
		var cell3_3 = $td.clone().attr("colspan", "3").attr("data-cellName", "perPhotoFileList")     
        line3.append(cell3_0).append(cell3_1).append(cell3_2).append(cell3_3)    
		$table.append(line3)
		
		var line7 = $tr.clone()
        var cell7_0 = $th.clone().attr("data-cellName", "specialOperationNumber")
		var cell7_1 = $td.clone().attr("data-cellName", "specialOperationNumber")
        var cell7_2 = $th.clone().attr("data-cellName", "specialOperationStartTime")
		var cell7_3 = $td.clone().attr("data-cellName", "specialOperationStartTime")
		var cell7_4 = $th.clone().attr("data-cellName", "specialOperationEndTime")
		var cell7_5 = $td.clone().attr("data-cellName", "specialOperationEndTime") 
        line7.append(cell7_0).append(cell7_1).append(cell7_2).append(cell7_3).append(cell7_4).append(cell7_5)
        $table.append(line7)
        var line8 = $tr.clone()
		var cell8_0 = $th.clone().attr("data-cellName", "rewardsAndPunish")
        var cell8_1 = $td.clone().attr("colspan", "3").attr("data-cellName", "rewardsAndPunish")
		var cell8_2 = $th.clone().attr("data-cellName", "rewardsAndPunishFileList")
        var cell8_3 = $td.clone().attr("data-cellName", "rewardsAndPunishFileList")
        line8.append(cell8_0).append(cell8_1).append(cell8_2).append(cell8_3)
        $table.append(line8)
var detailLayer = $('#detailLayer').detailLayer({
    layerArea: ['100%', '100%'],
	$formTable:$table,
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "perInfoBaseId",//输入字段名
        },
		{
            type: "hidden",//
            name: "perType",//
            label: "人员类型",//
			defaultValue:"3",
            placeholder: "请输入姓名"
        },      
        {
            type: "text",//
            name: "name",//
            label: "姓名",//
            placeholder: "请输入姓名",
            must: true
        },
        {
            type: "text",//
            name: "projectName",//
            label: "项目名称",//
            placeholder: "请输入项目名称",
            must: true
        },
        {
            type: "select",
            name: "sex",
            label: "性别",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "男",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 2,//输入字段的值
                    text: "女",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        },
        {
            type: "number",//
            name: "age",//
            label: "年龄",//
            placeholder: "请输入年龄",
            must: true
        },
        {
            type: "text",//
            name: "nativePlace",//
            label: "籍贯",//
            placeholder: "请输入籍贯",
            must: true
        },
        {
            type: "select",
            name: "typeOfWork",
            label: "工种",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "普工",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 2,//输入字段的值
                    text: "电工作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 3,//输入字段的值
                    text: "金属焊接切割",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 4,//输入字段的值
                    text: "起重机械",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 5,//输入字段的值
                    text: "企业内机动车辆驾驶",//显示中文名
                    selected: false//默认是否选中
                },
				{
                    value: 6,//输入字段的值
                    text: "登高架设及高空悬挂作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 7,//输入字段的值
                    text: "制冷作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 8,//输入字段的值
                    text: "锅炉作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 9,//输入字段的值
                    text: "压力容器作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 10,//输入字段的值
                    text: "爆破作业",//显示中文名
                    selected: false//默认是否选中
                },
				{
                    value: 11,//输入字段的值
                    text: "其他",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        },
        {
            type: "text",//
            name: "idCard",//
            label: "身份证号",//
            placeholder: "请输入身份证号",
            must: true
        },
		{
            type: "upload",//
            name: "idCardPhotoFileList",//
            label: "身份证扫描件上传",//
            btnName: "添加身份证扫描件",
            projectName: "zj-labor-real-name",
            fileType: ["jpg", "jpeg", "png", "gif"]
        },
        {
            type: "text",//
            name: "cropName",//
            label: "公司名称",//
            placeholder: "请输入公司名称",
            must: true
        },
		{
            type: "upload",//
            name: "perPhotoFileList",//
            label: "个人照片上传",//
            btnName: "添加个人照片",
            projectName: "zj-labor-real-name",
            fileType: ["jpg", "jpeg", "png", "gif"]
        },
        {
            type: "select",
            name: "specialOperationNumber",
            label: "特种作业证件",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "电作作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 2,//输入字段的值
                    text: "金属焊接、切割作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 3,//输入字段的值
                    text: "起重机械（含电梯）作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 4,//输入字段的值
                    text: "企业内机动车辆驾驶",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 5,//输入字段的值
                    text: "登高架设作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 6,//输入字段的值
                    text: "锅炉作业（含水质化验）",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 7,//输入字段的值
                    text: "压力容器作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 8,//输入字段的值
                    text: "制冷作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 9,//输入字段的值
                    text: "爆破作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 10,//输入字段的值
                    text: "危险物品作业",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 11,//输入字段的值
                    text: "其它作业",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        },
        {
            type: "date",//
            name: "specialOperationStartTime",//
            label: "发证日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"specialOperationStartTime",
        },
        {
            type: "date",//
            name: "specialOperationEndTime",//
            label: "到期日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"specialOperationEndTime",
        },
		{
            type: "upload",//
            name: "specialOperationFileList",//
            label: "特种作业证件上传",//
            btnName: "添加特种作业证件",
            projectName: "zj-labor-real-name",
            fileType: ["jpg", "jpeg", "png", "gif"]
        },
        {
            type: "textarea",//
            name: "rewardsAndPunish",//
            label: "安全奖惩情况",//
            placeholder: "请输入安全奖惩情况",
        },
		{
            type: "upload",//
            name: "rewardsAndPunishFileList",//
            label: "安全奖惩情况上传",//
            btnName: "添加安全奖惩情况",
            projectName: "zj-labor-real-name",
            fileType: ["jpg", "jpeg", "png", "gif"]
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "rewardsAndPunishFileId",//输入字段名
        },
        {
            type: "textarea",//
            name: "violateWarn",//
            label: "违章警示",//
            placeholder: "请输入违章警示",
        },
		{
            type: "upload",//
            name: "violateWarnFileList",//
            label: "违章警示上传",//
            btnName: "添加违章警示",
            projectName: "zj-labor-real-name",
            fileType: ["jpg", "jpeg", "png", "gif"]
        }
    ],	
    onSave: function (obj, _params) {
        l.ajax('updateZjLaborPerInfoBase', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjLaborPerInfoBase', _params, function (data) {
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
					   l.ajax("batchDeleteUpdateZjLaborPerInfoBase", checkedData, function () {
					   pager.page('remote')
				    })
				  layer.close(index);
               });
            }
            break;
    }
})
function myOpen(title, id, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id
    }
    layer.full(layer.open(options))
}
function public(voteId) {//发布
    l.ajax('sendVoteInfo', { voteId: voteId }, function (data, message) {
        layer.alert(message, { icon: 0, }, function (index) {
            layer.close(index);
            pager.page('remote')
        });
    })
}
function preview(voteId) {//预览
    window.open(l.domainName + 'initMVotePreview.do?voteId=' + voteId + '&previewFlag=1', 'newwindow', 'height=500,width=350,top=100,left=500,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no')
}
