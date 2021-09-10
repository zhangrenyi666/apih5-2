var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
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
            "data": "voteTitle",//接口对应字段
            "title": "投票标题",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [2],//第几列
            "data": "startDate",//接口对应字段
            "title": "投票开始时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [3],//第几列
            "data": "endDate",//接口对应字段
            "title": "投票结束时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [4],//第几列
            "data": "rule",//接口对应字段
            "title": "投票规则",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "remarks",//接口对应字段
            "title": "备注",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "data": "state",//接口对应字段
            "title": "投票状态",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "0":
                        r = "未发布";
                        break;
                    case "1":
                        r = "进行中";
                        break;
                    case "2":
                        r = "投票结束";
                        break
                }
                return r
            }
        },
        {
            "targets": [7],//第几列
            "width": 100,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名
            "render": function (data) {
                var html = '';
				if(data.state != '2'){
					html += '<span class="dropDown">';
					html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">操作</a>';
					html += '<ul class="dropDown-menu menu radius box-shadow">';
					html += '<li><a href="javascript:void(0);" onclick="startVote(\'' + data.voteId + '\',\'' + data.voteType + '\')">开始投票</a></li>';
					html += '</ul></span>';
				}
                return html;
            }
        }
    ]
});
var flag;
	if(l.getUrlParam("hwCode") == "null"){
		flag = "1";
	}else{
		
	}

var pager = $("#pager").page({

    remote: {//ajax请求配置
        url: l.getApiUrl("getExternalVoteListByVoter"),
        params: {
            hwCode: l.getUrlParam("hwCode"),
            hwOrg: l.getUrlParam("hwOrg"),
			flag: flag
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
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "voteId",//输入字段名
        },
        {
            type: "text",//
            name: "voteTitle",//
            label: "投票标题",//
            placeholder: "请输入投票标题",
            must: true
        },
        {
            type: "date",//text,select,date,
            name: "startDate",
            label: "投票开始时间",
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date().setDate(1),
            maxDate: "\'#F{$dp.$D(\\\'logmax\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmin",
            must: true
        },
        {
            type: "date",//text,select,date,
            name: "endDate",
            label: "投票结束时间",
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date(),
            minDate: "\'#F{$dp.$D(\\\'logmin\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmax",
            must: true
        },
        {
            type: "textarea",//
            name: "rule",//
            label: "投票规则",//
            placeholder: "请输入投票规则",
            must: true
        },
        {
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入备注",
        },
    ],
    onSave: function (obj, _params) {
        l.ajax('updateVote', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addVote', _params, function (data) {
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
                l.delTableRow("voteId", 'questionsList', 'batchDeleteVote', checkedData, function (data) {
                    pager.page('remote')
                })//  删除url地址
            }
            break;
    }
});
function myOpen(title, id, url) {
	var hwCode = l.getUrlParam("hwCode");
    var hwOrg = l.getUrlParam("hwOrg");
    var options = {
        type: 2,
        title: title,
        //content: url + ".html?id=" + id + "&hwCode="+ hwCode +"&hwOrg=" + hwOrg
        content: url + ".html?id=" + id + "&hwCode="+ hwCode +"&hwOrg=" + hwOrg+"&code=" + code		
    };
    layer.full(layer.open(options))
}
function startVote(voteId, type) { 
    if (type == 1) {
        myOpen("发起投票", voteId, "pingxuan")
    } else {
        myOpen("发起投票", voteId, "ExternalVote")
    }
}

function bb(){
	pager.page('remote')
}
