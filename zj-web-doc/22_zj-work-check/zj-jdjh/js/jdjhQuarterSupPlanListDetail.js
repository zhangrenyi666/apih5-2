var recordid = l.getUrlParam('id') || "";
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
             "data": function (row) {
                return row
            },
            "title": "监督事项",//表头名
            "defaultContent": "-",//默认显示
			"render": function (data) {
                var a;				
                if (data) {                   
                   a = '<a style="color:blue;"  onclick="detailLook(' + data.rowIndex + ')" href="javascript:void(0);">' + data.supMatter + '</a>'   
                }
                return a;
            }			
        },	
        {
            "targets": [3],//第几列
            "data": "scheduleTime",//接口对应字段
            "title": "计划实施时间",//表头名
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
            "data": "createUserName",//接口对应字段
            "title": "操作者",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "createTime",//接口对应字段
            "title": "更新时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return data ? l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss") : "-";
            }
        }
    ]
});

//检查项
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjJdjhQuarterSupMatterList"),
        params: {
            quarterSupPlanId: recordid
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
    customBtnGroup: {
        btns: [
            {
                name: "close",
                label: "关闭"
            }
        ],
        callback: function (dataName, obj) { obj.close() }
    },
	layerArea:['70%', '90%'],
    conditions: [
         {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "quarterSupPlanId",//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "quarterSupMatterId",//输入字段名
        },       
        {
            type: "textarea",//
            name: "supMatter",//
            label: "监督事项",//
            placeholder: "请输入监督事项",
        },
		{
            type: "text",//
            name: "scheduleTime",//
            label: "计划实施时间",//
            placeholder: "请输入计划实施时间",
        },
		{
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入备注",
        }
    ]
})
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table);
    var name = $(this).attr("data-name");
    switch (name) {
        case "close":
            parent.layer.close(parent.myOpenLayer)
            break;
    }
})
$("#tab-system").Huitab({
    index: 0
});
function detailLook(rowIndex) {
    detailLayer.open(table.row(rowIndex).data())
}



l.ajax("getZjJdjhQuarterSupPlanDetails", { quarterSupPlanId: recordid }, function (data, message, success) {
    if (success) {
        var customBtnGroup = {
            btns: [
                {
                    name: "close",
                    label: "关闭"
                }
            ],
            callback: function () {
                parent.layer.close(parent.myOpenLayer)
            }
        }
        var detailForm = $('#detailForm').detailLayer({
            customBtnGroup: customBtnGroup,
            conditions: [
        {
            type: "text",//五种形式：text,select,date,hidden,textarea,
            name: "number",//输入字段名
            label: "编号",
            must: true          
        },			
        {
            type: "pickTree",//
            name: "oaDutyDep",//接口字段名
            label: "监督责任部门",//
            must: true,
            pickType: {
                department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
                member: false,//成员列表对应接口字段名,false表示不开启该类
            }
        },
        {
            type: "text",
            name: "supObj",
            label: "监督对象",
            must: true
        }  
            ]
        })
        detailForm.setOpenData(data)
    }
})







