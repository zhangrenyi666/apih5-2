var code = Apih5.getUrlParam('code');
Apih5.setCookie('code', code, 30);
l.ajax("selectWeekTableTitle", {}, function (datas) {
    table = tables(datas);
    l.ajax('getZjLdzbLeaderPlanWeekList', {}, function (data) {
        table.clear().rows.add(data).draw();
    })
})
l.ajax("selectNextWeekTableTitle", {}, function (datas) {
    tablet = tablest(datas);
    l.ajax('getZjLdzbLeaderPlanNextWeekList', {}, function (data) {
        tablet.clear().rows.add(data).draw();
    })
})
var table;
function tables(datas){
    return table = $('#table').DataTable({
        "info": false,//是否显示数据信息
        "paging": false,//是否开启自带分页
        "ordering": false, //是否开启DataTables的高度自适应
        "processing": false,//是否显示‘进度’提示
        "searching": false,//是否开启自带搜索
        "autoWidth": false,//是否监听宽度变化
        "columnDefs": [//表格列配置
            {
                "targets": [0],//第几列
                "data": "oaMemberName",//接口对应字段
                "title": datas[0] || "",//表头名
                "defaultContent": "-"//默认显示
            },{
                "targets": [1],//第几列
                "data": "mondayContent",//接口对应字段
                "title": datas[1] || "",//表头名
                "defaultContent": "-",//默认显示
                "render":function(mondayContent,display,data){
                    return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ 'mondayContent' + "\",\""+ data.mondayContent.split('\n').join('').split('\r').join('')  + "\",$(this))'>" + mondayContent + "</textarea>"
                }
            },
            {
                "targets": [2],//第几列
                "data": "tuesdayContent",//接口对应字段
                "title": datas[2] || "",
                "defaultContent": "-",//默认显示
                "render":function(tuesdayContent,display,data){
                    return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ 'tuesdayContent' + "\",\""+ data.tuesdayContent.split('\n').join('').split('\r').join('')  + "\",$(this))'>" + tuesdayContent + "</textarea>"
                }
            },
            {
                "targets": [3],//第几列
                "data": "wednesdayContent",//接口对应字段
                "title": datas[3] || "",//表头名
                "defaultContent": "-",//默认显示
                "render":function(wednesdayContent,display,data){
                    return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ 'wednesdayContent' + "\",\""+ data.wednesdayContent.split('\n').join('').split('\r').join('')  + "\",$(this))'>" + wednesdayContent + "</textarea>"
                }
            },
            {
                "targets": [4],//第几列
                "data": "thursdayContent",//接口对应字段
                "title": datas[4] || "",//表头名
                "defaultContent": "-",//默认显示
                "render":function(thursdayContent,display,data){
                    return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ 'thursdayContent' + "\",\""+ data.thursdayContent.split('\n').join('').split('\r').join('')  + "\",$(this))'>" + thursdayContent + "</textarea>"
                }
            },
            {
                "targets": [5],//第几列
                "data": "fridayContent",//接口对应字段
                "title": datas[5] || "",//表头名
                "defaultContent": "-",//默认显示
                "render":function(fridayContent,display,data){
                    return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ 'fridayContent' + "\",\""+ data.fridayContent.split('\n').join('').split('\r').join('') + "\",$(this))'>" + fridayContent + "</textarea>"
                }
            },
            {
                "targets": [6],//第几列
                "data": "saturdayContent",//接口对应字段
                "title": datas[6] || "",//表头名
                "defaultContent": "-",//默认显示
                "render":function(saturdayContent,display,data){
                    return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ 'saturdayContent' + "\",\""+ data.saturdayContent.split('\n').join('').split('\r').join('')  + "\",$(this))'>" + saturdayContent + "</textarea>"
                }
            },
            {
                "targets": [7],//第几列
                "data": "sundayContent",//接口对应字段
                "title": datas[7] || "",//表头名
                "defaultContent": "-",//默认显示
                "render":function(sundayContent,display,data){
                    return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ 'sundayContent' + "\",\""+ data.sundayContent.split('\n').join('').split('\r').join('')  + "\",$(this))'>" + sundayContent + "</textarea>"
                }
            }
        ]
    });  
} 
var tablet;
function tablest(datas){
    return tablet = $('#tablet').DataTable({
        "info": false,//是否显示数据信息
        "paging": false,//是否开启自带分页
        "ordering": false, //是否开启DataTables的高度自适应
        "processing": false,//是否显示‘进度’提示
        "searching": false,//是否开启自带搜索
        "autoWidth": false,//是否监听宽度变化
        "columnDefs": [//表格列配置
            {
                "targets": [0],//第几列
                "data": "oaMemberName",//接口对应字段
                "title": datas[0] || "",//表头名
                "defaultContent": "-"//默认显示
            },{
                "targets": [1],//第几列
                "data": "mondayContent",//接口对应字段
                "title": datas[1] || "",//表头名
                "defaultContent": "-",//默认显示
                "render":function(mondayContent,display,data){
                    return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ 'mondayContent' + "\",\""+ data.mondayContent.split('\n').join('').split('\r').join('')  + "\",$(this))'>" + mondayContent + "</textarea>"
                }
            },
            {
                "targets": [2],//第几列
                "data": "tuesdayContent",//接口对应字段
                "title": datas[2] || "",
                "defaultContent": "-",//默认显示
                "render":function(tuesdayContent,display,data){
                    return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ 'tuesdayContent' + "\",\""+ data.tuesdayContent.split('\n').join('').split('\r').join('')  + "\",$(this))'>" + tuesdayContent + "</textarea>"
                }
            },
            {
                "targets": [3],//第几列
                "data": "wednesdayContent",//接口对应字段
                "title": datas[3] || "",//表头名
                "defaultContent": "-",//默认显示
                "render":function(wednesdayContent,display,data){
                    return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ 'wednesdayContent' + "\",\""+ data.wednesdayContent.split('\n').join('').split('\r').join('')  + "\",$(this))'>" + wednesdayContent + "</textarea>"
                }
            },
            {
                "targets": [4],//第几列
                "data": "thursdayContent",//接口对应字段
                "title": datas[4] || "",//表头名
                "defaultContent": "-",//默认显示
                "render":function(thursdayContent,display,data){
                    return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ 'thursdayContent' + "\",\""+ data.thursdayContent.split('\n').join('').split('\r').join('')  + "\",$(this))'>" + thursdayContent + "</textarea>"
                }
            },
            {
                "targets": [5],//第几列
                "data": "fridayContent",//接口对应字段
                "title": datas[5] || "",//表头名
                "defaultContent": "-",//默认显示
                "render":function(fridayContent,display,data){
                    return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ 'fridayContent' + "\",\""+ data.fridayContent.split('\n').join('').split('\r').join('')  + "\",$(this))'>" + fridayContent + "</textarea>"
                }
            },
            {
                "targets": [6],//第几列
                "data": "saturdayContent",//接口对应字段
                "title": datas[6] || "",//表头名
                "defaultContent": "-",//默认显示
                "render":function(saturdayContent,display,data){
                    return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ 'saturdayContent' + "\",\""+ data.saturdayContent.split('\n').join('').split('\r').join('')  + "\",$(this))'>" + saturdayContent + "</textarea>"
                }
            },
            {
                "targets": [7],//第几列
                "data": "sundayContent",//接口对应字段
                "title": datas[7] || "",//表头名
                "defaultContent": "-",//默认显示
                "render":function(sundayContent,display,data){
                    return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ 'sundayContent' + "\",\""+ data.sundayContent.split('\n').join('').split('\r').join('')  + "\",$(this))'>" + sundayContent + "</textarea>"
                }
            }
        ]
    });   
} 
$('.button-click').click(function(){
    $('#table').show();
    $('#tablet').hide();
    $('.textareas').attr("readonly",true);              
});
$('.button-clickt').click(function(){
    $('#table').hide();
    $('#tablet').show();  
    $('.textareas').attr("readonly",true);                
});
function myblur(id,content,text,textarea){
    //console.log(textarea.val());
    if(text.split('\n').join('').split('\r').join('') != textarea.val().split('\n').join('').split('\r').join('')){
        var _params = {
            planId:id
        };
        if(content == 'mondayContent'){
            _params.mondayContent = textarea.val().split('\n').join('').split('\r').join('');
            _params.showFlag = 1;
        }else if(content == 'tuesdayContent'){
            _params.tuesdayContent = textarea.val().split('\n').join('').split('\r').join('');
            _params.showFlag = 2;
        }else if(content == 'wednesdayContent'){
            _params.wednesdayContent = textarea.val().split('\n').join('').split('\r').join('');
            _params.showFlag = 3;
        }else if(content == 'thursdayContent'){
            _params.thursdayContent = textarea.val().split('\n').join('').split('\r').join('');
            _params.showFlag = 4;
        }else if(content == 'fridayContent'){
            _params.fridayContent = textarea.val().split('\n').join('').split('\r').join('');
            _params.showFlag = 5;
        }else if(content == 'saturdayContent'){
            _params.saturdayContent = textarea.val().split('\n').join('').split('\r').join('');
            _params.showFlag = 6;
        }else if(content == 'sundayContent'){
            _params.sundayContent = textarea.val().split('\n').join('').split('\r').join('');
            _params.showFlag = 7;
        }
        l.ajax('updateZjLdzbLeaderPlan1', _params, function (data,message,success) {
            if(success){
                layer.alert('更新数据成功',{ icon: 1 },function(index){
                    layer.close(index);
                    if($('#tablet').css('display') == 'none'){
                        l.ajax('getZjLdzbLeaderPlanWeekList', {}, function (data) {
                            table.clear().rows.add(data).draw();
                            $('.textareas').attr("readonly",false);
                        })
                    }else{
                        l.ajax('getZjLdzbLeaderPlanNextWeekList', {}, function (data) {
                            tablet.clear().rows.add(data).draw();
                            $('.textareas').attr("readonly",false);
                        })
                    }
                });
            }else{
                layer.alert(message,{ icon: 0 });
            }          
        })
    }
}
var iTop = (window.screen.availHeight - 30 - 600) / 2;
var iLeft = (window.screen.availWidth - 10 - 800) / 2;
$('.btn-x').click(function(){
    window.open('windowOpen.html?code=' + code,'','width=800,height=600,scrollbars=yes,top='+ iTop + ',' + 'left='+ iLeft);
})
$('.btn-g').click(function(){
    l.ajax('getZjLdzbEditJurisdictionMember', {}, function (data) {
        if(data != null){
            $('.textareas').attr("readonly",false);
        }
    })
})
$('.btn-t').click(function(){
    window.location.href = 'statisticsList.html?code=' + code;
})
$('.btn-b').click(function(){
    window.location.href = 'statement.html?code=' + code;
})