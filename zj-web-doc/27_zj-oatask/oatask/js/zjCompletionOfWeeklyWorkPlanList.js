var code = Lny.getUrlParam('code');
var reportDate = Lny.getUrlParam('reportDate');
Lny.setCookie('code', code, 30);
l.ajax('getZjOataskWeekPlanDetail', { weekPlanId: l.getUrlParam("id") }, function (data, message, success) {
    if (success) {
        if (data.weekTitle) {
            $('.weekTitle').text(data.weekTitle);
        } else {
            $('.weekTitle').text('中交一公局机关周工作计划');
        }
        if (data.lastPlan) {
            $('.lastPlan').text("上周工作计划：" + data.lastPlan);
        } else {
            $('.lastPlan').text("上周工作计划：无");
        }
        if (data.nowPlan) {
            $('.nowPlan').text("本周工作计划：" + data.nowPlan);
        } else {
            $('.nowPlan').text("本周工作计划：无");
        }
    } else {
        layer.alert(message, { icon: 0, }, function (index) {
            layer.close(index);
        });
    }
})
var new_Date = new Date(parseInt(reportDate));
var timesStamp = new_Date.getTime();
var currenDay = new_Date.getDay();
var titleDate = [];
for (var i = 0; i < 7; i++) {
    var titleDates = new Date(timesStamp + 24 * 60 * 60 * 1000 * (i - (currenDay + 6) % 7));
    titleDates = l.dateFormat(titleDates, "yyyy-MM-dd")
    titleDate.push(titleDates);
}
var table = $('#table').DataTable({
    "info": false,//是否显示数据信息
    "paging": false,//是否开启自带分页
    "ordering": false, //是否开启DataTables的高度自适应
    "processing": false,//是否显示‘进度’提示
    "searching": false,//是否开启自带搜索
    "autoWidth": false,//是否监听宽度变化
    "columnDefs": [//表格列配置
        {
            "targets": [0],//第几列
            "data": "ext1",//接口对应字段
            "width": "16%",
            "title": "周一(" + titleDate[0] + ")",//表头名
            "defaultContent": "-",//默认显示
            "render":function(data,display,rowData){
                return "<textarea class='textareas' onblur='myblur(\"" + 'ext1' + "\",\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + rowData.ext1 + "\",$(this))'>" + rowData.ext1 + "</textarea>";
            }
        },
        {
            "targets": [1],//第几列
            "data": "ext2",//接口对应字段
            "width": "16%",
            "title": "周二(" + titleDate[1] + ")",//表头名
            "defaultContent": "-",//默认显示
            "render":function(data,display,rowData){
                return "<textarea class='textareas' onblur='myblur(\"" + 'ext2' + "\",\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + rowData.ext2 + "\",$(this))'>" + rowData.ext2 + "</textarea>";
            }
        },
        {
            "targets": [2],//第几列
            "data": "ext3",//接口对应字段
            "width": "16%",
            "title": "周三(" + titleDate[2] + ")",//表头名
            "defaultContent": "-",//默认显示
            "render":function(data,display,rowData){
                return "<textarea class='textareas' onblur='myblur(\"" + 'ext3' + "\",\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + rowData.ext3 + "\",$(this))'>" + rowData.ext3 + "</textarea>";
            }
        },
        {
            "targets": [3],//第几列
            "data": "ext4",//接口对应字段
            "width": "16%",
            "title": "周四(" + titleDate[3] + ")",//表头名
            "defaultContent": "-",//默认显示
            "render":function(data,display,rowData){
                return "<textarea class='textareas' onblur='myblur(\"" + 'ext4' + "\",\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + rowData.ext4 + "\",$(this))'>" + rowData.ext4 + "</textarea>";
            }
        },
        {
            "targets": [4],//第几列
            "data": "ext5",//接口对应字段
            "width": "16%",
            "title": "周五(" + titleDate[4] + ")",//表头名
            "defaultContent": "-",//默认显示
            "render":function(data,display,rowData){
                return "<textarea class='textareas' onblur='myblur(\"" + 'ext5' + "\",\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + rowData.ext5 + "\",$(this))'>" + rowData.ext5 + "</textarea>";
            }
        },
        {
            "targets": [5],//第几列
            "data": "unfinishReason",//接口对应字段
            "width": "20%",
            "title": "未完成工作及原因",//表头名
            "defaultContent": "-",//默认显示
            "render":function(data,display,rowData){
                return "<textarea class='textareas' onblur='myblur(\"" + 'unfinishReason' + "\",\"" + encodeURI(JSON.stringify(rowData)) + "\",\"" + rowData.unfinishReason + "\",$(this))'>" + rowData.unfinishReason + "</textarea>";
            }
        }
    ]
});
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjOataskWeekPlanItemPerformanceList"),
        params: { weekPlanId: l.getUrlParam("id") },
        success: function (result) {
            if (result.success) {
                var data = result.data;
                allData = data;
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
function myblur(filed, rowData, text, textarea) {
    if (text.split('\n').join('').split('\r').join('') != textarea.val().split('\n').join('').split('\r').join('')) {
        var _params = JSON.parse(decodeURI(rowData));
        if(filed === 'ext1'){
            _params.ext1 = textarea.val();
        }else if(filed === 'ext2'){
            _params.ext2 = textarea.val();
        }else if(filed === 'ext3'){
            _params.ext3 = textarea.val();
        }else if(filed === 'ext4'){
            _params.ext4 = textarea.val();
        }else if(filed === 'ext5'){
            _params.ext5 = textarea.val();
        }else if(filed === 'unfinishReason'){
            _params.unfinishReason = textarea.val();
        }
        l.ajax('updateZjOataskWeekPlanItemPerformance', _params, function (data, message, success) {
            if (success) {
                pager.page('remote');
            } else {
                layer.alert(message, { icon: 0 });
            }
        })
    }
}