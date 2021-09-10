var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
var flag = Apih5.getUrlParam('flag');
var fallInDeptId = Apih5.getUrlParam('id');
var param = {
    fallInDeptId: fallInDeptId
}
var table = $('#table').DataTable({
    "info": false,// 是否显示数据信息
    "paging": false,// 是否开启自带分页
    "ordering": false, // 是否开启DataTables的高度自适应
    "processing": false,// 是否显示‘进度’提示
    "searching": false,// 是否开启自带搜索
    "autoWidth": false,// 是否监听宽度变化
    "columnDefs": [// 表格列配置
        {
            "targets": [0],
            "data": "rowIndex",
            "title": 'No.',
            "width": 25,
            "render": function (data) {
                return data + 1
            }
        },
        {
            "targets": [1],// 第几列
            "data": "meetingTimeStr",// 接口对应字段
            "title": "会议时间",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [2],// 第几列
            "data": "meetingNameStr",// 接口对应字段
            "title": "会议名称",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [3],// 第几列
            "data": "meetingForm",// 接口对应字段
            "title": "会议形式",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [4],// 第几列
            "data": "joinObject",// 接口对应字段
            "title": "参加对象",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [5],// 第几列
            "data": "joinNumber",// 接口对应字段
            "title": "人数",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [6],// 第几列
            "data": "sponsoringDept",// 接口对应字段
            "title": "主办部门",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [7],// 第几列
            "data": "coOperationDept",// 接口对应字段
            "title": "协办部门",// 表头名
            "class": "text-overflow",            
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [8],// 第几列
            "data": "budgetaryCost",// 接口对应字段
            "title": "预算费用（万元）",// 表头名
            "defaultContent": "-",// 默认显示
        },
        {
            "targets": [9],// 第几列
            "data": "meetingRemakes",// 接口对应字段
            "title": "备注",// 表头名
            "defaultContent": "-",// 默认显示
        }
    ]
});
var pager = $("#pager").page({
    remote: {// ajax请求配置
        url: l.getApiUrl("getZjMeetingPlanFallInListDeptId"),
        params: {
            fallInDeptId: fallInDeptId
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                //console.log(data);
                // questionList = data[data.length - 1];
                $.each(data, function (index, item) {
                    item.rowIndex = index
                })
                table.clear().rows.add(data).draw();
                $.each($(".text-overflow"), function (index, item) {
                    $(".text-overflow").eq(index + 1).attr({"data-toggle":"tooltip","data-placemen":"top","title":data[index]["coOperationDept"]});
                })
                $(function () { $("[data-toggle='tooltip']").tooltip()});                
            } else {
                layer.alert(result.message, { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
        },
    }
});

// 详情
var detailLayer = $('#detailLayer').detailLayer({
    conditions: [
        {
            type: "hidden",//
            name: "questionId",//
        },
        {
            type: "text",//
            name: "oaUserName",//
            label: "主办部门",//
            immutableAdd: true,
            lineNum: 1
        },   
        {
            type: "text",//
            name: "fallInYear",//
            label: "年度",//
            immutableAdd: true,
            lineNum: 1
        },         
        {
            type: "text",//
            name: "fallInUser",//
            label: "填报人",//
            immutableAdd: true
        },
        {
            type: "text",//
            name: "fallInTime",//
            label: "填报时间",//
            immutableAdd: true
        },        
        {
            type: "textarea",//
            name: "fallInRemakes",//
            label: "备注",//
            immutableAdd: true
        }
    ],

})
// 领导审批
var askLayer = $('#askLayer').detailLayer({
    layerTitle: ['发起'],
    layerArea: ['45%', '30%'],
    conditions: [
        {
            type: "hidden",//
            name: "fallInDeptId"//
        },   
		{
            type: "pickTree",
            name: "deptLeaderList",
            label: "部门负责人",
            pickType: {
            department: false,//部门列表对应接口字段名,false表示不开启该类
            member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            },
			//must: true,
        },
    ],
    onSave: function (obj, _params) {
        l.ajax('zjMeetingPlanSubmitApply', _params, function (data) {
            layer.alert("发起成功！", { icon: 1, }, function (index) {
                layer.close(index);
                pager.page('remote')
                parent['pager'].page('remote')
                layer_close()                
                obj.close()		                    
            });  
        })
    }
})
// 领导审批
var applyLayer = $('#applyLayer').detailLayer({
    layerTitle: ['审批'],
    layerArea: ['40%', '40%'],
    conditions: [
        {
            type: "hidden",//
            name: "fallInDeptId"//
        },        
        {
            type: "hidden",//
            name: "approvalResult"//
        },        
        {
            type: "textarea",//
            name: "approvalOpinion",//
            label: "领导意见",//
            // must: true,
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('zjMeetingPlanSubmitApproval', _params, function (data) {
            layer.alert("审批成功！", { icon: 1, }, function (index) {
                layer.close(index);
                pager.page('remote')
                parent['pager'].page('remote')
                layer_close()                     
                obj.close()		                    
            });              
        })
    }
})
$("body").on("click", "button", function () {
    var name = $(this).attr("data-name");
    switch (name) {
        case "forward":
            askLayer.open(param);
            break;
        case "agree":
            applyLayer.open({approvalResult:"1",fallInDeptId:fallInDeptId,approvalOpinion:"同意"});
            break;
        case "reject":
            applyLayer.open({approvalResult:"0",fallInDeptId:fallInDeptId,approvalOpinion:"驳回"});
            break;            
        case "close":
            pager.page('remote')
            parent['pager'].page('remote')
            layer_close()
            break;
    }
})
//请求数据给第一个表单赋值
myRefresh();
function myRefresh() {
//flag:0；申请人身份；1:审批人身份    
    if (flag == "0") {
        $("#agree").hide();
        $("#reject").hide();
    }else if(flag == "1"){
        $("#forward").hide();
    }
    l.ajax('getZjMeetingPlanFallInInfoDetailByDeptId', param, function (data) {
        $('#detailLayer .btn').hide();
        if (data) {
            var $input = $('#detailLayer input, #detailLayer select,#detailLayer textarea');
            $input.each(function (index, ele) {
                var _name = $(ele).attr('name');
                $(ele).attr('disabled', true);
                //日期和图片列表特殊处理 因为   type值跟文本一样so
                if (_name === 'fallInTime' && data[_name]) {
                    //日期 
                    var d = new Date(data[_name]);
                    var toLocaleTimeString = d.getFullYear() + '-' + (d.getMonth() + 1) + "-" +
                    (d.getDate()) + " " + 
                    (d.getHours()) + ":" + 
                    (d.getMinutes()) + ":" + 
                    (d.getSeconds());
                    $(ele).val(toLocaleTimeString);
                } else {
                    $(ele).val(data[_name])
                }
            })
        }
    })
}
