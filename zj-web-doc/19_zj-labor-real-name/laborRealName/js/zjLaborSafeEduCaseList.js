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
            "data": "corpName",//接口对应字段
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
            "data": "eduType",//接口对应字段
            "title": "教育类型",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "trainName",//接口对应字段
            "title": "培训名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "teacher",//接口对应字段
            "title": "授课人",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "date",//接口对应字段
            "title": "日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [8],//第几列
            "data": "participant",//接口对应字段
            "title": "参加人员",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [9],//第几列
            "data": "trainContent",//接口对应字段
            "title": "培训内容",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [10],//第几列
            "data": "passExamFlag",//接口对应字段
            "title": "是否通过考核",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "1":
                        r = "是"
                        break
                    case "2":
                        r = "否"
                        break
                }
                return r
            }
        }
    ]
});
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjLaborSafeEduCaseList"),
        params: {
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
     layerArea:['100%', '100%'],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "safeEduCaseId",//输入字段名
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
            name: "eduType",
            label: "教育类型",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "三级教育",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 2,//输入字段的值
                    text: "复工教育",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 3,//输入字段的值
                    text: "换岗教育",//显示中文名
                    selected: false//默认是否选中
                },
				{
                    value: 4,//输入字段的值
                    text: "三类人员教育",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 5,//输入字段的值
                    text: "专项培训",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 6,//输入字段的值
                    text: "节假日教育",//显示中文名
                    selected: false//默认是否选中
                },
				{
                    value: 7,//输入字段的值
                    text: "季节性教育",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 8,//输入字段的值
                    text: "应急培训",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 9,//输入字段的值
                    text: "其他",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        },				
        {
            type: "text",//
            name: "trainName",//
            label: "培训名称",//
            placeholder: "请输入培训名称",
            must: true
        },
        {
            type: "text",//
            name: "teacher",//
            label: "授课人",//
            placeholder: "请输入授课人",
            must: true
        },
		{
            type: "date",//
            name: "date",//
            label: "日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id: "date",
            must: true
        },
        {
            type: "text",//
            name: "participant",//
            label: "参加人员",//
            placeholder: "请输入参加人员",
            must: true
        },
        {
            type: "textarea",//
            name: "trainContent",//
            label: "培训内容",//
            placeholder: "请输入培训内容",
            must: true
        },
        {
            type: "select",
            name: "passExamFlag",
            label: "是否通过考核",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "是",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 2,//输入字段的值
                    text: "否",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        },
		{
            type: "upload",//
            name: "safeEduCaseList",//
            label: "附件上传",//
            btnName: "添加附件",
            projectName: "zj-labor-real-name",
            fileType: ["jpg", "jpeg", "png", "gif"]
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('updateZjLaborSafeEduCase', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjLaborSafeEduCase', _params, function (data) {
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
					   l.ajax("batchDeleteZjLaborSafeEduCase", checkedData, function () {
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
