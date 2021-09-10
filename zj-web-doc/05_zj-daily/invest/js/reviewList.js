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
            "data": "dailyType",//接口对应字段
            "title": "项目类型",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "0":
                        r = "土地征迁"
                        break
                    case "1":
                        r = "设计图"
                        break
                    case "2":
                        r = "项目资金"
                        break
                    case "3":
                        r = "评估许可证"
                        break
                    case "4":
                        r = "工程形象"
                        break
                }
                return r
            }
        },
        {
            "targets": [3],//第几列
            "data": "progressDesc",//接口对应字段
            "title": "进展描述",//表头名
			"width": 200,
            "defaultContent": "-",//默认显示
			"render": function (data) {
                return data.length > 60 ? data.substring(0, 60)+"..." : data;
            }
        },
        {
            "targets": [4],//第几列
            "data": "changes",//接口对应字段
            "title": "有无变化",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "0":
                        r = "否"
                        break
                    case "1":
                        r = "是"
                        break
                }
                return r
            }
        },
        {
            "targets": [5],//第几列
            "data": "changeDesc",//接口对应字段
            "title": "变化描述",//表头名
            "width": 200,
            "defaultContent": "-",//默认显示
			"render": function (data) {
                return data.length > 60 ? data.substring(0, 60)+"..." : data;
            }
        },
        {
            "targets": [6],//第几列
            "data": "checkState",//接口对应字段
            "title": "审核状态",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {//自定义输出
                var r = "未知";
                switch (data) {
                    case "0":
                        r = "未审核"
                        break
                    case "1":
                        r = "审核"
                        break
                }
                return r
            }
        },
        {
            "targets": [7],//第几列
            "data": "fillDate",//接口对应字段
            "title": "填报日期",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd");
            }
        },
        {
            "targets": [8],
            "data": "accessoryList",
            "title": "附件",
            "defaultContent": "-",
            "render": function (data) {

                var arr = '';
                $.each(data, function (i, v) {
                    arr += '<a href="' + v.url + '">' + v.fileName + '</a><br>'
                })
                return arr;
            }
        },
        {
            "targets": [9],//第几列
            "width": 80,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名
            "render": function (data) {
				var urlkey
				if(data.dailyType == "0"){
					urlkey = "TzLand";
				}
				else if (data.dailyType == "1"){
					urlkey = "TzDesignChart";
				}
				else if (data.dailyType == "2"){
					urlkey = "TzProjectFunds";
				}
				else if (data.dailyType == "3"){
					urlkey = "TzAssessment";
				}
				else if (data.dailyType == "4"){
					urlkey = "TzGraphicProgress";
				}
				
                var html = '';
                html += '<span class="dropDown">'
                html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">操作</a>';
                html += '<ul class="dropDown-menu menu radius box-shadow">';
                html += '<li><a href="javascript:void(0);" onclick="myOpen(\'详情\',\'' + data.reviewId + '\',\'' + urlkey + '\',\'' + 'tzDetail2' + '\')">详情</a></li>';
                html += '</ul></span>'
                return html;
            }
        }
    ]
});
/**
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "select",
            name: "checkState",
            label: "审核状态",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 0,//输入字段的值
                    text: "未审核",//显示中文名
                    selected: false//默认是否选中
                },
                {
                    value: 1,//输入字段的值
                    text: "审核",//显示中文名
                    selected: false//默认是否选中
                }
            ]
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
})**/
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getReviewList"),
        params: {
			 companyId: l.getUrlParam("companyId")
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
            name: "reviewId",//输入字段名
        },
        {
            type: "textarea",//
            name: "progressDesc",//
            label: "进展描述",//
            placeholder: "请输入进展描述",
        },
        {
            type: "textarea",//
            name: "changeDesc",//
            label: "变化描述",//
            placeholder: "请输入变化描述",
        },
        {
            type: "date",//
            name: "fillDate",//
            label: "填报日期",//
            dateFmt: "yyyy-MM-dd",
            defaultValue: new Date(),
            id:"fillDate",
            must: true
        },
    ],
    onSave: function (obj, _params) {
        l.ajax('updateReview', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addReview', _params, function (data) {
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
        case "review":
            if (checkedData.length == 0) {
                layer.alert("未选择任何项", { icon: 0, }, function (index) {
                    layer.close(index);
                });
            } else {
				urlname = "updateReview";
				reviewId = "reviewId";
				dailyType = "dailyType";
				listKey = "investCompanyList";

				var params = {};
				var arr = []
				for (var i = 0; i < checkedData.length; i++) {
					var param = {};
					param[reviewId] = checkedData[i][reviewId]
					param[dailyType] = checkedData[i][dailyType]
					arr.push(param)
				}
				params[listKey] = arr;
				l.ajax(urlname, params, function (checkedData) {
					layer.alert("OK", { icon: 0, }, function (index) {
						layer.close(index);
						pager.page('remote')
					});
					
				})
            }
            break;
    }
})
function myOpen(title, id, urlkey , url) {
    var options = {
        type: 2,
        title: title,
        // content: url + ".html?id=" + id + "&urlkey=" + urlkey
		content: 'TzDetail2.html' + '?companyId=' + id + '&urlkey=' + urlkey + '&id=' + id + '&saveType=2'//saveType: 0是新增，1是修改，2是复制新增
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
