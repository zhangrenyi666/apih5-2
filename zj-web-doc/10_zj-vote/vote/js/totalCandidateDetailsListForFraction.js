var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
var table;
var pager = $("#pager").page({
    remote: { //ajax请求配置
        url: l.getApiUrl("getTotalCandidateDetailsListForFraction"),
        params: {
            candidateId: l.getUrlParam("id"),
        },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                $.each(data, function (index, item) {
                    item.rowIndex = index
                    $.each(item.voterAnswerList, function (i, v) {
                        item["voterAnswer" + i] = v
                    })
                })
                var voterAnswerList = data[0] && data[0].voterAnswerList ? data[0].voterAnswerList : [];
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
                target = target + 1
                columnDefs.push({
                    "targets": [target], //第几列
                    "data": "oaUserName", //接口对应字段
                    "title": "投票者", //表头名
                    "defaultContent": "-", //默认显示
                })
                for (var i = 0; i < voterAnswerList.length; i++) {
                    target = target + 1
                    columnDefs.push({
                        "targets": [target], //第几列
                        "data": "voterAnswer" + i, //接口对应字段
                        "title": voterAnswerList[i].title, //表头名
                        "defaultContent": "-", //默认显示
                        "render": function (data) {
						if(data&&data.flag == 2){
						
						 return data ? '<span style = "color:red">'+data.answer+'</span>' : ""
						}else{
						return data ? data.answer : ""
						}  
                        }
                    })
                }
                if (table) {
                    table.clear().rows.add(data).draw();
                } else {
                    table = $('#table').DataTable({
                        "info": false, //是否显示数据信息
                        "paging": false, //是否开启自带分页
                        "ordering": false, //是否开启DataTables的高度自适应
                        "processing": false, //是否显示‘进度’提示
                        "searching": false, //是否开启自带搜索
                        "autoWidth": false, //是否监听宽度变化
                        "columnDefs": columnDefs,
                        "data": data
                    });
                }
            } else {
                layer.alert(result.message, {
                    icon: 0,
                }, function (index) {
                    layer.close(index);
                });
            }
        },
    }
});