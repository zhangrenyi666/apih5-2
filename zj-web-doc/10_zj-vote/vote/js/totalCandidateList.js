var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
var table;
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getTotalCandidateList"),
        params: {
            voteId: l.getUrlParam("id"),
            //voteId: "1BLHARQBJ000FA1ED7720000FDE78291"
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                $.each(data, function (index, item) {
                    item.rowIndex = index
                    $.each(item.voteQuestionsList, function (i, v) {
                        item["voteQuestion" + i] = v
                    })
                })
                var columnTitleList = data[0] && data[0].columnTitleList ? data[0].columnTitleList : [];
                var target = 0;
                var columnDefs = [
                    {
                        "targets": [target],
                        "data": "rowIndex",
                        "title": 'No.',
                        "width": 25,
                        "render": function (data) {
                            return data + 1
                        }
                    }
                ];
                if (data[0].titleOne) {
                    target = target + 1
                    columnDefs.push({
                        "targets": [target],//第几列
                        "data": "contentOne",
                        "title": data[0].titleOne,//表头名
                        "defaultContent": "-",//默认显示
                    })
                }
                if (data[0].titleTwo) {
                    target = target + 1
                    columnDefs.push({
                        "targets": [target],//第几列
                        "data": "contentTwo",
                        "title": data[0].titleTwo,//表头名
                        "defaultContent": "-",//默认显示
                    })
                }
                if (data[0].titleThree) {
                    target = target + 1
                    columnDefs.push({
                        "targets": [target],//第几列
                        "data": "contentThree",
                        "title": data[0].titleThree,//表头名
                        "defaultContent": "-",//默认显示
                    })
                }
                if (data[0].titleFour) {
                    target = target + 1
                    columnDefs.push({
                        "targets": [target],//第几列
                        "data": "contentFour",
                        "title": data[0].titleFour,//表头名
                        "defaultContent": "-",//默认显示
                    })
                }
                if (data[0].titleFive) {
                    target = target + 1
                    columnDefs.push({
                        "targets": [target],//第几列
                        // "data": "name",//接口对应字段
                        "data": "contentFive",
                        "title": data[0].titleFive,//表头名
                        "defaultContent": "-",//默认显示
                    })
                }
                target = target + 1
                columnDefs.push({
                    "targets": [target],//第几列
                    "data": "voterNum",//接口对应字段
                    "title": "投票者数",//表头名
                    "defaultContent": "-",//默认显示
                })
				/*
                target = target + 1
                columnDefs.push({
                    "targets": [target],//第几列
                    "data": "total",//接口对应字段
                    "title": "平均分",//表头名
                    "defaultContent": "-",//默认显示
                })
				*/
                for (var i = 0; i < columnTitleList.length; i++) {
                    target = target + 1
                    columnDefs.push({
                        "targets": [target],//第几列
                        "data": "voteQuestion" + i,//接口对应字段
                        "title": columnTitleList[i],//表头名
                        "defaultContent": "-",//默认显示
                        "render": function (data) {
                            if (data.answerTotal == "") {
                                return "-"
                            } else {
                                return data.answerTotal
                            }
                        }
                    })
                }
                target = target + 1
                columnDefs.push({
                    "targets": [target],//第几列
                    "data": "candidateId",//接口对应字段
                    "title": "详情",//表头名
                    "defaultContent": "-",//默认显示
                    "render": function (data) {
                        return '<a href="javascript:void(0);" class="dropDown_A" onclick="myOpen(\'得票统计详情列表\',\'' + data + '\',\'' + 'totalCandidateDetailsList' + '\')">查看</a>'
                    }
                })
				if(table){
					table.destroy()
				}
                table = $('#table').DataTable({
                    "info": false,//是否显示数据信息
                    "paging": false,//是否开启自带分页
                    "ordering": false, //是否开启DataTables的高度自适应
                    "processing": false,//是否显示‘进度’提示
                    "searching": false,//是否开启自带搜索
                    "autoWidth": false,//是否监听宽度变化
                    "columnDefs": columnDefs,
                    "data": data
                });
            } else {
                layer.alert(result.message, { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
        },
    }
});
$("body").on("click", "button", function () {
    var checkedData = l.getTableCheckedData(table)
    var name = $(this).attr("data-name");
    switch (name) {
        case "del":
            outExcel();
            break;
    }
})
function myOpen(title, id, url) {
    var options = {
        type: 2,
        title: title,
        content: url + ".html?id=" + id +"&code="+code
    }
    layer.full(layer.open(options))
}

function outExcel() {
    var id = l.getUrlParam("id");
    l.ajax('exportCandidateList', { voteId: id }, function (data, message) {
        if (message == "ok") {
            layer.alert(message, { icon: 0, }, function (index) {
                window.open(data);// 导出（下载）Excel
                layer.close(index);
                pager.page('remote')

            });
        }
        else {
            layer.alert(message, { icon: 0, }, function (index) {
                layer.close(index);
                pager.page('remote')

            });
        }
    })



}

