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
            "title": 'No.',
            "width": 25,
            "render": function (data) {
                return data + 1
            }
        },
        {
            "targets": [1],//第几列
            "data": "projectName",//接口对应字段
            "title": "项目名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [2],//第几列
            "data": "shouldDays",//接口对应字段
            "title": "累计应周报份数（份）",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "realDays",//接口对应字段
            "title": "累计实际周报份数（份）",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "notDays",//接口对应字段
            "title": "累计未报周报份数（份）",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "totalMark",//接口对应字段
            "title": "总分(分)",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "width": 100,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名
            "render": function (data) {
				// 获取检索添加的日期
				startDatea = l.dateToUnix($("#form [name=startDate]").val());
				endDatea = l.dateToUnix($("#form [name=endDate]").val());
                var html = '';
                html += '<span class="dropDown">'
                html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">操作</a>';
                html += '<ul class="dropDown-menu menu radius box-shadow">';
                html += '<li><a href="javascript:void(0);" onclick="myOpen(\'周报统计详情\',\'' + data.companyId + '\',\'' + startDatea + '\',\'' + endDatea + '\',\'' + 'investDailySheetDetail2' + '\')">详情</a></li>';
				html += '<li><a href="javascript:void(0);" onclick="myRestReview(\'反审核内容列表\',\'' + data.companyId + '\',\'' + 'restReviewList' + '\')">反审核</a></li>';
                html += '</ul></span>'
                return html;
            }
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "projectName",//输入字段名
            label: "名称",//输入字段对杨的中文名称
            placeholder: "请输入完整名称",
        },
        {
            type: "date",//text,select,date,
            name: "startDate",
            label: "起始时间",
            defaultValue: l.dateFormat(new Date(new Date().getTime()-1000*60*60*24*6),"yyyy-MM-dd"),
            maxDate: "\'#F{$dp.$D(\\\'logmax\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmin",
        },
        {
            type: "date",//text,select,date,
            name: "endDate",
            label: "终止时间",
            defaultValue: l.dateFormat(new Date(), "yyyy-MM-dd"),
            minDate: "\'#F{$dp.$D(\\\'logmin\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmax",
        }
    ],
    onSearch: function (arr) {//搜索按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].name == "startDate" || arr[i].name == "endDate") {
                if (arr[i].value) {
                    _params[arr[i].name] = l.dateToUnix(arr[i].value);
                } else {
                    _params[arr[i].name] = "";
                }
            } else {
                _params[arr[i].name] = arr[i].value;
            }
        }
        pager.page('remote', _params)
    },
    onReset: function (arr) {//重置按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
        pager.page('remote', _params)
    }
})
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getTotalDailyDaysNumList"),
        params: {
            projectName: "",
            startDate: new Date(new Date().getTime()-1000*60*60*24*6).getTime(),
            endDate: new Date().getTime(),
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
var sendLayer, tzInvestCompanyList = [], tzMessageMemberList = [];
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
        case "save":
            var $messageMember = $("[name=messageMember]:checked")
            if ($messageMember.length > 0) {
                tzMessageMemberList = [];
                for (var i = 0; i < $messageMember.length; i++) {
                    tzMessageMemberList.push({
                        oaUserId: $messageMember.eq(i).val()
                    })
                }
                var _params = {
                    tzInvestCompanyList: tzInvestCompanyList,
                    tzMessageMemberList: tzMessageMemberList,
                    startDate: l.dateToUnix($("#form [name=startDate]").val()),
                    endDate: l.dateToUnix($("#form [name=endDate]").val()),
                }
                l.ajax('sendDailyMsgByPc', _params, function (data, message) {
                    layer.alert(message, { icon: 0, }, function (index) {
                        layer.close(index);
                        layer.close(sendLayer);
                    });
                })
            } else {
                layer.alert("至少选择一个人员", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
            break;
        case "close":
            layer.close(sendLayer);
            break;
        case "send":
		    startDate = $("#form [name=startDate]").val();
			endDate = $("#form [name=endDate]").val();
			if(startDate == null || startDate == "" ){
				layer.alert("请选择开始查询日期，作为发报日期。", { icon: 0, }, function (index) {
					layer.close(index);
				});
			}
			else if(endDate == null || endDate == ""){
				layer.alert("请选择结束查询日期，作为发报日期。", { icon: 0, }, function (index) {
					layer.close(index);
				});
			}
            //else if (table.row(0).data().totalNumber == 0) {
			else if (table.rows().length == 0) {				
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
                tzInvestCompanyList = [];
                $.each(checkedData, function (i, v) {
                    tzInvestCompanyList.push({
                        companyId: v.companyId
                    })
                })
                l.ajax('getMessageMemberListByPc', {memberType: "1"}, function (data, message) {
                    $("#messageMemberList").html("")
                    $.each(data, function (i, v) {
                        $("#messageMemberList").append('<li><label><input class="mr-10" name="messageMember" value="' + v.oaUserId + '" type="checkBox"/>' + v.oaUserName + '</label></li>')
                    })
                    sendLayer = layer.open({
                        type: 1,
                        title: '选择人员',
                        area: ['400px', '40%'],
                        content: $("#sendBox")
                    });
                })

            }
            break;
    }
})
function myOpen(title, id, startDate, endDate, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?companyId=" + id + "&startDate="+startDate + "&endDate="+endDate
    }
    layer.full(layer.open(options))
}

function myRestReview(title, id, url) {
     var options = {
        type: 2,
        title: title,
        content: url + ".html?companyId=" + id
    }
    layer.full(layer.open(options))
}





