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
            "data": "userName",//接口对应字段
            "title": "姓名",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "userUnit",//接口对应字段
            "title": "单位",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "userGrade",//接口对应字段
            "title": "成绩",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [5],//第几列
            "data": "userPhone",//接口对应字段
            "title": "用户电话",//表头名
            "defaultContent": "-",//默认显示
        }
    ]
});

var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getKscjWinnerList"),
        params: {
            userName: "",
            awardType: "",
            lotteryId:l.getUrlParam("id")
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
            name: "userId",//输入字段名
        },
        {
            type: "select",
            name: "awardType",
            label: "中奖类型",
            selectList: [//当类型为select时，option配置
                {
                    value: "",//输入字段的值
                    text: "全部",//显示中文名
                    selected: true//默认是否选中
                },
                {
                    value: 0,//输入字段的值
                    text: "80分以上",//显示中文名
                    selected: false//默认是否选中
                }
                // {
                //     value: 1,//输入字段的值
                //     text: "二等奖",//显示中文名
                //     selected: false//默认是否选中
                // },
                // {
                //     value: 2,//输入字段的值
                //     text: "三等奖",//显示中文名
                //     selected: false//默认是否选中
                // }
            ]
        },
        {
            type: "text",//
            name: "userName",//
            label: "姓名",//
            immutable:true,
            placeholder: "请输入姓名",
            must: true
        },
        {
            type: "text",//
            name: "userUnit",//
            label: "单位",//
            immutable:true,
            placeholder: "请输入单位",
            must: true
        },
        {
            type: "text",//
            name: "userGrade",//
            label: "成绩",//
            immutable:true,
            placeholder: "请输入成绩",
            must: true
        },
        {
            type: "text",//
            name: "userPhone",//
            label: "用户电话",//
            immutable:true,
            placeholder: "请输入用户电话",
            must: true
        },
    ],
    onSave: function (obj, _params) {
        l.ajax('updateWinner', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addKscjWinner', _params, function (data) {
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
            }else if(checkedData.length == 1){
                //Lny.log('选择的数据：',checkedData)
                l.delTableRowById("userId", "deleteWinner", checkedData[0], function (data) {
                    pager.page('remote')
                })//  删除url地址
            }else {
                l.ajax("batchDelWinner", checkedData, function () {
                    pager.page('remote')
				})
                // l.delTableRow("userId", 'winnerList', 'batchDelWinner', checkedData, function (data) {
                //     pager.page('remote')
                // })// 删除url地址
            }
            break;
        case "derive"://导出
            // Lny.log( $('.select').eq(0).val())
            if(true){
                 layer.alert("确定导出此数据？", { icon: 0 }, function (index) {
                    var params={};
                    params.lotteryId=l.getUrlParam("id");
                    // Lny.log(l.getUrlParam("id"))
                    layer.close(index);
                    l.ajax("exportWinnerList",params,function(res){
                        window.location.href=res;
                    });
                });
            }else{
                layer.alert("请选择导出抽奖类型！")
            }
        break;
        }
});



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
