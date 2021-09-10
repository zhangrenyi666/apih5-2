var code = Lny.getUrlParam('code');
Lny.setCookie('code', code, 30);
var table;
function tables(data) {
    return table = $('#table').DataTable({
        "info": false,//是否显示数据信息
        "paging": false,//是否开启自带分页
        "ordering": false, //是否开启DataTables的高度自适应
        "processing": false,//是否显示‘进度’提示
        "searching": false,//是否开启自带搜索
        "autoWidth": false,//是否监听宽度变化
        "columnDefs": data || []
    })
}
$(function () {
    l.ajax("getZjMeetingRoomSituationTableTitle", {}, function (data, message, success) {
        if (success) {
            if(data[2].length){
                var datas = [{
                    "targets": [0],
                    "data": "tr0",
                    "title":data[2][0],
                    "width":'10%',
                    "defaultContent": "-",
                }];
                for(var i = 1; i < data[2].length; i++){
                    datas.push({
                        "targets": [i],
                        "data": "tr"+i,
                        "title":data[2][i],
                        "width":90/(data[2].length-1)+'%',
                        "defaultContent": "-",
                        "render":function(dataq){
                            if(dataq.indexOf('&') == -1){
                                return "<center>"+dataq+"</center>";               
                            }else{
                                var dataw = [];
                                for(var i = 0; i < dataq.split('&').length-1; i++){
                                    if(i === dataq.split('&').length-2){
                                        dataw.push("<div style='min-height:80px;'>"+dataq.split('&')[i]+"</div>")
                                    }else{
                                        dataw.push("<div style='min-height:80px;'>"+dataq.split('&')[i]+"</div></br>")
                                    }
                                }
                                return dataw.join('');
                            }                          
                        }
                    })
                }
                table = tables(datas);
                $('#table').prepend('<thead class="zjThead"></thead>');
                for(var i = 0; i < data[1].length; i++){
                    if(i == 0){
                        $('.zjThead').append('<th>'+data[1][i]+'</th>')
                    }else{
                        $('.zjThead').append('<th colspan="2">'+data[1][i]+'</th>')
                    }
                }
            }
            l.ajax("getZjMeetingRoomSituationTable", {}, function (data, message, success) {
                if (success) {
                    table.clear().rows.add(data).draw();
                }
            })
        }
    })
});