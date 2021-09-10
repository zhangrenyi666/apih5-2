﻿var code = Lny.getUrlParam('code')
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
            "data": "lotteryTitle",//接口对应字段
            "title": "抽奖活动名称",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "settingTimes",//接口对应字段
            "title": "抽奖次数",//表头名
            "defaultContent": "0",//默认显示
        },
        {
            "targets": [3],//第几列
            "data": "remarks",//接口对应字段
            "title": "备注",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [4],//第几列
            "data": "modifyTime",//接口对应字段
            "title": "更新时间",//表头名
            "defaultContent": "-",//默认显示
            "render": function (data) {
                return l.dateFormat(new Date(data), "yyyy-MM-dd HH:mm:ss");
            }
        },
        {
            "targets": [5],//第几列
            "data": "modifyUser",//接口对应字段
            "title": "更新者",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [6],//第几列
            "width": 80,
            "data": function (row) {
                return row
            },//接口对应字段
            "title": "操作",//表头名
            "render": function (data) {
                // Lny.log('数据是',data)
                var html = '';
                html += '<span class="dropDown">'
                html += '<a href="javascript:void(0);" class="dropDown_A" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">操作</a>';
                html += '<ul class="dropDown-menu menu radius box-shadow">';
                html += '<li><a href="javascript:void(0);" onclick="  myOpen(\'抽奖名单\',\'' + data.lotteryId + '\',\'' + data.lotteryTitle + '\',\'' + 'kscjCandidateList' + '\')  ">抽奖名单</a></li>';
                html += '<li><a href="javascript:void(0);" onclick="  myOpen(\'中奖名单\',\'' + data.lotteryId + '\',\'' + data.lotteryTitle + '\',\'' + 'kscjWinnerList' + '\')  ">中奖名单</a></li>';                
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
            name: "lotteryTitle",//输入字段名
            label: "抽奖活动名称",//输入字段对杨的中文名称
            placeholder: "请输入抽奖活动名称",
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
        pager.page('remote',0, _params)
    },
    onReset: function (arr) {//重置按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
        pager.page('remote',0, _params)
    }
})
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getLotteryList"),
        params: {
            lotteryTitle: "",
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
            name: "lotteryId",//输入字段名
        },
        {
            type: "text",//
            name: "lotteryTitle",//
            label: "抽奖活动名称",//
            placeholder: "请输入抽奖活动名称",
            must: true
        },
        {
            type: "number",//
            name: "settingTimes",//
            label: "抽奖次数",//
            placeholder: "请输入抽奖次数",
            must: true
        },
        {
            type: "textarea",//
            name: "remarks",//
            label: "备注",//
            placeholder: "请输入备注",
        }
        // {
        //     type: "checkbox",//
        //     name: "checkboxTest",//
        //     label: "多选测试",//   
        //     optionData:[
        //         {
        //             label:"选项一",
        //             value:"0",
        //             // checked:true
        //         },
        //         {
        //             label:"选项二",
        //             value:"2"
        //         },
        //         {
        //             label:"选项二",
        //             value:"3"
        //         } 
        //     ]
        // }
    ],
    onSave: function (obj, _params) { 
        l.ajax('updateLottery', _params, function (data) {
            pager.page('remote')
            obj.close()
        })
    },
    onAdd: function (obj, _params) { 
        l.ajax('addLottery', _params, function (data) {
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
            // detailLayer.open({
            //     lotteryTitle:"测试",
            //     // checkboxTest:["0", "2"]
            // });
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
            }else {
                l.ajax("batchDeleteLottery", checkedData, function () {
                    pager.page('remote')
				})
				// l.delTableRow("lotteryId", 'lotteryList', 'batchDeleteLottery', checkedData, function (data) {
                //     pager.page('remote')
                // })// 删除url地址
            }
            break;
    }
})
function myOpen(title, id, title, url) {
    // Lny.log(id)
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id+"&title="+title
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
