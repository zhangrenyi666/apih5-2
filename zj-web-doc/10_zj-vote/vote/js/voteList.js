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
            "data": "voteType",//接口对应字段
            "title": "投票类型",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "0":
                        r = "普通模式"
                        break
                    case "1":
                        r = "评选模式"
                        break
                    case "2":
                        r = "多人评选模式"
                        break
                }
                return r
            }
        },
        {
            "targets": [3],//第几列
            "data": "voteTitle",//接口对应字段
            "title": "投票标题",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "startDate",//接口对应字段
            "title": "投票开始时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
          if(data == null){
			return null;
			}else{
			return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
			}
            }
        },
        {
            "targets": [5],//第几列
            "data": "endDate",//接口对应字段
            "title": "投票结束时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
			if(data == null){
			return null;
			}else{
			return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
			}
                
            }
        },
        {
            "targets": [6],//第几列
            "data": "rule",//接口对应字段
            "title": "投票规则",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [7],//第几列
            "data": "remarks",//接口对应字段
            "title": "备注",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [8],//第几列
            "data": "state",//接口对应字段
            "title": "投票状态",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "0":
                        r = "未发布"
                        break
                    case "1":
                        r = "进行中"
                        break
                    case "2":
                        r = "投票结束"
                        break
                }
                return r
            }
        },
        {
            "targets": [9],//第几列
            "data": "voteWaiverTotal",//接口对应字段
            "title": "投票数/未投票/总数",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [10],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "更新时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [11],//第几列
            "data": "modifyUser",//接口对应字段
            "title": "更新者",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [12],//第几列
            "width": 80,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名
            "render": function (data) {
                var html = '';
                html += '<span class="dropDown">'
                html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">操作</a>';
                html += '<ul class="dropDown-menu menu radius box-shadow">';
				html += '<li><a href="javascript:void(0);" onclick="edit(\'' + data.voteId + '\',\'' + data.selectionVoteCount + '\',\'' + data.rowIndex + '\')">编辑</a></li>';
                // 只有投票状态时为发布的时候允许修改
                if (data.state == 0) {
                    if (data.voteType == 0) {//普通模式
                        html += '<li><a href="javascript:void(0);" onclick="myOpen(\'问题列表\',\'' + data.voteId + '\',\'' + 'questionsList' + '\')">问题列表</a></li>';
                    }
                    else if (data.voteType == 1) {//评选模式
                        html += '<li><a href="javascript:void(0);" onclick="myOpen(\'被投票者列表\',\'' + data.voteId + '\',\'' + 'voteCandidateList' + '\')">被投票者</a></li>';
                    }
                    else if (data.voteType == 2) {//多人评选模式
                        html += '<li><a href="javascript:void(0);" onclick="myOpen(\'问题列表\',\'' + data.voteId + '\',\'' + 'questionsList' + '\')">问题列表</a></li>';
                        html += '<li><a href="javascript:void(0);" onclick="myOpen(\'被投票者列表\',\'' + data.voteId + '\',\'' + 'voteCandidateList' + '\')">被投票者</a></li>';
                    }
                } else {
                    html += '<li><a href="javascript:void(0);" onclick="myOpen(\'得票统计列表\',\'' + data.voteId + '\',\'' + 'totalCandidateList' + '\',\'' + data.totalFlag + '\')">统计</a></li>';
                }
                if (data.state == 0 || data.state == 1) {
                    html += '<li><a href="javascript:void(0);" onclick="send(\'' + data.voteId + '\')">发布</a></li>';
                }
                html += '<li><a href="javascript:void(0);" onclick="previewPc(\'' + data.voteId + '\',\'' + data.voteType + '\')">PC预览</a></li>';
                html += '<li><a href="javascript:void(0);" onclick="previewMobile(\'' + data.voteId + '\',\'' + data.voteType + '\')">手机预览</a></li>';
                html += '<li><a href="javascript:void(0);" onclick="copy(\'' + data.voteId + '\')">复制</a></li>';
                html += '</ul></span>'
                return html;
            }
        }
    ]
});

var form = $('#form').filterfrom({
    customBtnGroup: {
        btns: [
            {
                name: "close",
                label: "关闭"
            }
        ],
        callback: function (dataName, obj) { obj.close() }
    },
    conditions: [//表单项配置
        {
            type: "text",//三种形式：text,select,date,
            name: "voteTitle",//输入字段名
            label: "投票标题",//输入字段对杨的中文名称
            placeholder: "请输入投票标题",
        },
        {
            type: "select",
            name: "state",
            label: "状态",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 0,//输入字段的值
                    text: "未发布",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "进行中",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 2,//输入字段的值
                    text: "投票结束",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        }
    ],
    onSearch: function (arr) {//搜索按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].name == "startDate" || arr[i].name == "endDate") {
                if (arr[i].value) {
                    _params[arr[i].name] = l.dateFormat(new Date(new Date(arr[i].value).getTime()), "yyyy-MM-dd");
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
        url: l.getApiUrl("getVoteList"),
        params: {
            voteTitle: "",
            state: "",
			userId:l.getUrlParam("userId")
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                $.each(data, function (index, item) {
                    item.rowIndex = index
                })
                table.clear().rows.add(data).draw();
            } else {
//                layer.alert(result.message, { icon: 0, }, function (index) {
//                    layer.close(index);
//                });
            }
        },
    }
});
var detailLayer = $('#detailLayer').detailLayer({
    layerArea: ["100%", "100%"],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "voteId",//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "state",//输入字段名
        },		
        {
            type: "select",
            name: "voteType",
            label: "投票类型",
            selectList: [//当类型为select时，option配置
                {
                    value: 0,//输入字段的值
                    text: "普通模式",//显示中文名
                    selected: true//默认是否选中
                },
				/*
                {
                    value: 1,//输入字段的值
                    text: "评选模式",//显示中文名
                    selected: false//默认是否选中
                },
				*/
                {
                    value: 2,//输入字段的值
                    text: "多人评选模式",//显示中文名
                    selected: false//默认是否选中
                }
            ],
            immutable: true
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
            defaultValue: new Date(),
            maxDate: "\'#F{$dp.$D(\\\'logmax\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmin",
            must: true
        },
        {
            type: "date",//text,select,date,
            name: "endDate",
            label: "投票结束时间",
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date().setMonth(new Date().getMonth() + 1),
            maxDate: new Date().setFullYear(new Date().getFullYear() + 1),
            minDate: "\'#F{$dp.$D(\\\'logmin\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmax",
            must: true
        },
        {
            type: "textarea",//
            name: "rule",//
            label: "投票规则",//
            placeholder: "请输入投票规则",
        },
        {
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入备注",
        },
/*        {
            type: "select",
            name: "showFlag",
            label: "允许查看投票结果",
            selectList: [//当类型为select时，option配置
                {
                    value: 0,//输入字段的值
                    text: "否",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "是",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        },
*/
        {
            type: "select",
            name: "anonymousFlag",
            label: "匿名投票",
            selectList: [//当类型为select时，option配置
                {
                    value: 0,//输入字段的值
                    text: "否",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "是",//显示中文名
                    selected: true//默认是否选中
                }
            ],
        },
		{
            type: "pickTree",
            name: "oaMemberList",
            label: "投票发送者",
			must: true,
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            }
        }
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
})

var addDetailLayer = $('#addDetailLayer').detailLayer({
    layerArea: ["100%", "100%"],
    conditions: [
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "voteId",//输入字段名
        },
        {
            type: "hidden",//五种形式：text,select,date,hidden,textarea,
            name: "state",//输入字段名
        },		
        {
            type: "select",
            name: "voteType",
            label: "投票类型",
            selectList: [//当类型为select时，option配置
                {
                    value: 1,//输入字段的值
                    text: "评选模式",//显示中文名
                    selected: false//默认是否选中
                }
            ],
            immutable: true
        },
        {
            type: "number",//
            name: "selectionVoteCount",//
            label: "评选模式个人最多投票数",//
            //placeholder: "请输入投票标题",
			defaultValue:2,
            must: true
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
            defaultValue: new Date(),
            maxDate: "\'#F{$dp.$D(\\\'logmax\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmin",
            must: true
        },
        {
            type: "date",//text,select,date,
            name: "endDate",
            label: "投票结束时间",
            dateFmt: "yyyy-MM-dd HH:mm:ss",
            defaultValue: new Date().setMonth(new Date().getMonth() + 1),
            maxDate: new Date().setFullYear(new Date().getFullYear() + 1),
            minDate: "\'#F{$dp.$D(\\\'logmin\\\')||\\\'%y-%M-%d\\\'}\'",
            id: "logmax",
            must: true
        },
        {
            type: "textarea",//
            name: "rule",//
            label: "投票规则",//
            placeholder: "请输入投票规则",
        },
        {
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入备注",
        },
        {
            type: "select",
            name: "showFlag",
            label: "允许查看投票结果",
            selectList: [//当类型为select时，option配置
                {
                    value: 0,//输入字段的值
                    text: "否",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "是",//显示中文名
                    selected: false//默认是否选中
                }
            ],
        },
        {
            type: "select",
            name: "anonymousFlag",
            label: "匿名投票",
            selectList: [//当类型为select时，option配置
                {
                    value: 0,//输入字段的值
                    text: "否",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "是",//显示中文名
                    selected: true//默认是否选中
                }
            ],
        },
		{
            type: "pickTree",
            name: "oaMemberList",
            label: "投票发送者",
			must: true,
            pickType: {
                department: false,//部门列表对应接口字段名,false表示不开启该类
                member: "oaMemberList",//成员列表对应接口字段名,false表示不开启该类
            }
        }
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
})
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
        case "addcc":
            addDetailLayer.open();
            break;	
        case "add":
            detailLayer.open();
            break;
        case "del":
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
                l.delTableRow("voteId", 'voteList', 'batchDeleteVote', checkedData, function (data) {
			   layer.alert("删除成功", { icon: 1, }, function (index) {
                    layer.close(index);
                });
                    pager.page('remote')
                })//  删除url地址
            }
            break;
    }
})

function edit(id,selectionVoteCount,rowIndex) {
	var checkedData=table.row(rowIndex).data();
           if(checkedData.state == "1"){
			   layer.alert("项目进行中，无法编辑", { icon: 0, }, function (index) {
                    layer.close(index);
                });
			}else if(selectionVoteCount == "null"){
			  detailLayer.open(checkedData);
			}else{
			  addDetailLayer.open(checkedData);
			}
}
function myOpen(title, id, url,flag) {
	if(flag == 0){
	title = '得分统计列表'
	url = 'totalCandidateListForFraction';
	}else if(flag == 1){
    title = '得票统计列表'
	url = 'totalCandidateList';
	}
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id+"&code="+code
    };
    layer.full(layer.open(options))
}
function send(voteId) {//发布
    l.ajax('sendVoteInfo', { voteId: voteId }, function (data, message) {
        layer.alert(message, { icon: 1, }, function (index) {
            layer.close(index);
            pager.page('remote')
        });
    })
}
function copy(voteId) {//复制
    l.ajax('copyVoteDetails', { voteId: voteId }, function (data, message) {
        layer.alert(message, { icon: 0, }, function (index) {
            layer.close(index);
            pager.page('remote')
        });
    })
}
function previewMobile(voteId, type) {//手机预览
    
	 if (type == 1) {
	 window.open('multiplayerVote_mobile.html?id=' + voteId + '&previewFlag=1', 'newwindow', 'height=500,width=350,top=100,left=500,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no')
    } else {
	window.open('vote_mobile.html?id=' + voteId + '&previewFlag=1', 'newwindow', 'height=500,width=350,top=100,left=500,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no')
    }
}
function previewPc(voteId, type) {
    if (type == 1) {
        myOpen("Pc预览", voteId, "pingxuanp")
    } else {
        myOpen("Pc预览", voteId, "votep")
    }
}