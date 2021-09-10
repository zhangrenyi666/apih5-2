var code = Apih5.getUrlParam('code');
Apih5.setCookie('code', code, 30);
l.ajax("getZjLdzbLeaderPlanStatistics", {weekFlag:0}, function (data) {
    table.clear().rows.add(data).draw();
})
var table = table = $('#table').DataTable({
    "info": false,//是否显示数据信息
    "paging": false,//是否开启自带分页
    "ordering": false, //是否开启DataTables的高度自适应
    "processing": false,//是否显示‘进度’提示
    "searching": false,//是否开启自带搜索
    "autoWidth": false,//是否监听宽度变化
    "columnDefs": [//表格列配置
        {
            "targets": [0],//第几列
            "data": "weekName",//接口对应字段
            "title": "时间",//表头名
            "width":200,
            "defaultContent": "-"//默认显示
        },{
            "targets": [1],//第几列
            "data": "content",//接口对应字段
            "title": "工作内容",//表头名
            "defaultContent": "-",//默认显示
        },
        {
            "targets": [2],//第几列
            "data": "",//接口对应字段
            "title": "备注",
            "width":200,
            "defaultContent": "-",//默认显示
        }
    ]
});
var weekFlag = 0;
$('.button-click').click(function(){
	weekFlag = 0;
    l.ajax("getZjLdzbLeaderPlanStatistics", {weekFlag:0}, function (data) {
        table.clear().rows.add(data).draw();
    })                   
});
$('.button-clickt').click(function(){
	weekFlag = 1;
    l.ajax("getZjLdzbLeaderPlanStatistics", {weekFlag:1}, function (data) {
        table.clear().rows.add(data).draw();
    })                   
});

$("body").on("click", "button", function () {
    var name = $(this).attr("data-name");
    switch (name) {
        case "derive"://导出
            var params = {weekFlag:weekFlag};
            layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
                l.ajax('ldzbLeaderPlanStatisticsExport', params, function (res) {
                    layer.alert("导出成功！", { icon: 0 }, function (index) {
                        layer.close(index);
                        window.location.href = res;
                    })
                })
            });
            break;
    }
})

$('.btn-h').click(function(){
    history.go(-1);
})