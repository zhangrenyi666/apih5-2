var code = Apih5.getUrlParam('code');
Apih5.setCookie('code', code, 30);
var goBack = 1;
l.ajax('getZjPxjhEditJurisdictionMember', {}, function (_d, _m, _s) {
    if (_s) {
        if(_d === '0'){
            $('button[data-name="edit"]').show();
        }else{
            $('button[data-name="edit"]').hide();
        }
    }
})
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
            "data": "oaUserName",//接口对应字段
            "title": "",//表头名
            "defaultContent": "-",//默认显示
            "width":60
        },
        {
            "targets": [1],//第几列
            "data": "mondayTrainContent",//接口对应字段
            "title": "培训",//表头名
            "defaultContent": "-",//默认显示
            "render":function (mondayTrainContent,display,data){
                return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ '1' + "\",\""+ data.mondayTrainContent.split('\n').join('').split('\r').join('')  + "\",$(this))'>" + mondayTrainContent + "</textarea>"
            }
        },
        {
            "targets": [2],//第几列
            "data": "mondayMeetContent",//接口对应字段
            "title": "会议",//表头名
            "defaultContent": "-",//默认显示
            "render":function (mondayMeetContent,display,data){
                return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ '2' + "\",\""+ data.mondayMeetContent.split('\n').join('').split('\r').join('')  + "\",$(this))'>" + mondayMeetContent + "</textarea>"
            }
        },
        {
            "targets": [3],//第几列
            "data": "tuesdayTrainContent",//接口对应字段
            "title": "培训",//表头名
            "defaultContent": "-",//默认显示
            "render":function (tuesdayTrainContent,display,data){
                return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ '3' + "\",\""+ data.tuesdayTrainContent.split('\n').join('').split('\r').join('')  + "\",$(this))'>" + tuesdayTrainContent + "</textarea>"
            }
        },
        {
            "targets": [4],//第几列
            "data": "tuesdayMeetContent",//接口对应字段
            "title": "会议",//表头名
            "defaultContent": "-",//默认显示
            "render":function (tuesdayMeetContent,display,data){
                return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ '4' + "\",\""+ data.tuesdayMeetContent.split('\n').join('').split('\r').join('')  + "\",$(this))'>" + tuesdayMeetContent + "</textarea>"
            }
        },
        {
            "targets": [5],//第几列
            "data": "wednesdayTrainContent",//接口对应字段
            "title": "培训",//表头名
            "defaultContent": "-",//默认显示
            "render":function (wednesdayTrainContent,display,data){
                return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ '5' + "\",\""+ data.wednesdayTrainContent.split('\n').join('').split('\r').join('')  + "\",$(this))'>" + wednesdayTrainContent + "</textarea>"
            }
        },
        {
            "targets": [6],//第几列
            "data": "wednesdayMeetContent",//接口对应字段
            "title": "会议",//表头名
            "defaultContent": "-",//默认显示
            "render":function (wednesdayMeetContent,display,data){
                return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ '6' + "\",\""+ data.wednesdayMeetContent.split('\n').join('').split('\r').join('')  + "\",$(this))'>" + wednesdayMeetContent + "</textarea>"
            }
        },
        {
            "targets": [7],//第几列
            "data": "thursdayTrainContent",//接口对应字段
            "title": "培训",//表头名
            "defaultContent": "-",//默认显示
            "render":function (thursdayTrainContent,display,data){
                return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ '7' + "\",\""+ data.thursdayTrainContent.split('\n').join('').split('\r').join('')  + "\",$(this))'>" + thursdayTrainContent + "</textarea>"
            }
        },
        {
            "targets": [8],//第几列
            "data": "thursdayMeetContent",//接口对应字段
            "title": "会议",//表头名
            "defaultContent": "-",//默认显示
            "render":function (thursdayMeetContent,display,data){
                return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ '8' + "\",\""+ data.thursdayMeetContent.split('\n').join('').split('\r').join('')  + "\",$(this))'>" + thursdayMeetContent + "</textarea>"
            }
        },
        {
            "targets": [9],//第几列
            "data": "fridayTrainContent",//接口对应字段
            "title": "培训",//表头名
            "defaultContent": "-",//默认显示
            "render":function (fridayTrainContent,display,data){
                return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ '9' + "\",\""+ data.fridayTrainContent.split('\n').join('').split('\r').join('')  + "\",$(this))'>" + fridayTrainContent + "</textarea>"
            }
        },
        {
            "targets": [10],//第几列
            "data": "fridayMeetContent",//接口对应字段
            "title": "会议",//表头名
            "defaultContent": "-",//默认显示
            "render":function (fridayMeetContent,display,data){
                return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ '10' + "\",\""+ data.fridayMeetContent.split('\n').join('').split('\r').join('')  + "\",$(this))'>" + fridayMeetContent + "</textarea>"
            }
        },
        {
            "targets": [11],//第几列
            "data": "saturdayTrainContent",//接口对应字段
            "title": "培训",//表头名
            "defaultContent": "-",//默认显示
            "render":function (saturdayTrainContent,display,data){
                return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ '11' + "\",\""+ data.saturdayTrainContent.split('\n').join('').split('\r').join('')  + "\",$(this))'>" + saturdayTrainContent + "</textarea>"
            }
        },
        {
            "targets": [12],//第几列
            "data": "saturdayMeetContent",//接口对应字段
            "title": "会议",//表头名
            "defaultContent": "-",//默认显示
            "render":function (saturdayMeetContent,display,data){
                return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ '12' + "\",\""+ data.saturdayMeetContent.split('\n').join('').split('\r').join('')  + "\",$(this))'>" + saturdayMeetContent + "</textarea>"
            }
        },
        {
            "targets": [13],//第几列
            "data": "sundayTrainContent",//接口对应字段
            "title": "培训",//表头名
            "defaultContent": "-",//默认显示
            "render":function (sundayTrainContent,display,data){
                return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ '13' + "\",\""+ data.sundayTrainContent.split('\n').join('').split('\r').join('')  + "\",$(this))'>" + sundayTrainContent + "</textarea>"
            }
        },
        {
            "targets": [14],//第几列
            "data": "sundayMeetContent",//接口对应字段
            "title": "会议",//表头名
            "defaultContent": "-",//默认显示
            "render":function (sundayMeetContent,display,data){
                return "<textarea class='textareas' readonly style='resize:none;width:95%;height:100%;' onblur='myblur(\""+ data.planId + "\",\""+ '14' + "\",\""+ data.sundayMeetContent.split('\n').join('').split('\r').join('')  + "\",$(this))'>" + sundayMeetContent + "</textarea>"
            }
        }
    ]
});
var detailLayer = $('#detailLayer').detailLayer({
    layerArea: ['100%', '100%'],
    conditions: [
        {
            type: "pickTree",
            name: "oaDepartmentList",
            label: "部门",
            pickType: {
                department: "oaDepartmentList",//部门列表对应接口字段名,false表示不开启该类
                member: false,//成员列表对应接口字段名,false表示不开启该类
            },
        },
        {
            label: '周一培训',
            type: "textarea",
            name: 'mondayTrainContent',
            lineNum: 2,
            placeholder: '请输入'
        },
        {
            label: '周一会议',
            type: "textarea",
            name: 'mondayMeetContent',
            lineNum: 2,
            placeholder: '请输入'
        },
        {
            label: '周二培训',
            type: "textarea",
            name: 'tuesdayTrainContent',
            lineNum: 3,
            placeholder: '请输入'
        },
        {
            label: '周二会议',
            type: "textarea",
            name: 'tuesdayMeetContent',
            lineNum: 3,
            placeholder: '请输入'
        },
        {
            label: '周三培训',
            type: "textarea",
            name: 'wednesdayTrainContent',
            lineNum: 4,
            placeholder: '请输入'
        },
        {
            label: '周三会议',
            type: "textarea",
            name: 'wednesdayMeetContent',
            lineNum: 4,
            placeholder: '请输入'
        },
        {
            label: '周四培训',
            type: "textarea",
            name: 'thursdayTrainContent',
            lineNum: 5,
            placeholder: '请输入'
        },
        {
            label: '周四会议',
            type: "textarea",
            name: 'thursdayMeetContent',
            lineNum: 5,
            placeholder: '请输入'
        },
        {
            label: '周五培训',
            type: "textarea",
            name: 'fridayTrainContent',
            lineNum: 6,
            placeholder: '请输入'
        },
        {
            label: '周五会议',
            type: "textarea",
            name: 'fridayMeetContent',
            lineNum: 6,
            placeholder: '请输入'
        },
        {
            label: '周六培训',
            type: "textarea",
            name: 'saturdayTrainContent',
            lineNum: 7,
            placeholder: '请输入'
        },
        {
            label: '周六会议',
            type: "textarea",
            name: 'saturdayMeetContent',
            lineNum: 7,
            placeholder: '请输入'
        },
        {
            label: '周日培训',
            type: "textarea",
            name: 'sundayTrainContent',
            lineNum: 8,
            placeholder: '请输入'
        },
        {
            label: '周日会议',
            type: "textarea",
            name: 'sundayMeetContent',
            lineNum: 8,
            placeholder: '请输入'
        }
    ],
    onSave: function (obj, _params) {
        l.ajax('addZjPxjhWeekPlanAddDept', _params, function (data, message, success) {
            if (success) {
                layer.alert(message, { icon: 0, }, function (index) {
                    if(goBack == 1){
                        l.ajax('getZjPxjhWeekPlanWeekList', {}, function (data) {
                            table.clear().rows.add(data).draw();
                        })
                    }else{
                        l.ajax('getZjPxjhWeekPlanNextWeekList', {}, function (data) {
                            table.clear().rows.add(data).draw();
                        })
                    }
                    layer.close(index);
                    obj.close();
                });
            }
        })
    },
    onAdd: function (obj, _params) {
        l.ajax('addZjPxjhWeekPlanAddDept', _params, function (data, message, success) {
            if (success) {
                layer.alert(message, { icon: 0, }, function (index) {
                    if(goBack == 1){
                        l.ajax('getZjPxjhWeekPlanWeekList', {}, function (data) {
                            table.clear().rows.add(data).draw();
                        })
                    }else{
                        l.ajax('getZjPxjhWeekPlanNextWeekList', {}, function (data) {
                            table.clear().rows.add(data).draw();
                        })
                    }
                    layer.close(index);
                    obj.close();
                });
            }
        })
    }
})
l.ajax('selectZjPxjhWeekTableTitle', {}, function (_d, _m, _s) {
    if (_s) {
        $('.theads').remove();
        $('#table').prepend('<thead class="theads"><th colspan="1" style="background-color:#b3ddfe;">' + _d[0] + '</th><th colspan="2" style="background-color:#b3ddfe;">' + _d[1] + '</th><th colspan="2" style="background-color:#b3ddfe;">' + _d[2] + '</th><th colspan="2" style="background-color:#b3ddfe;">' + _d[3] + '</th><th colspan="2" style="background-color:#b3ddfe;">' + _d[4] + '</th><th colspan="2" style="background-color:#b3ddfe;">' + _d[5] + '</th><th colspan="2" style="background-color:#b3ddfe;">' + _d[6] + '</th><th colspan="2" style="background-color:#b3ddfe;">' + _d[7] + '</th></thead>');
        l.ajax('getZjPxjhWeekPlanWeekList', {}, function (data, message, success) {
            if (success) {
                table.clear().rows.add(data).draw();
            }
        })
    }
})
$("body").on("click", "button", function () {
    var name = $(this).attr("data-name");
    switch (name) {
        case "add":
            $('.textareas').attr("readonly", true);
            detailLayer.open();
            break;
        case "edit":
            $('.textareas').attr("readonly", false);
            break;
        case "back":
            goBack = 1;
            l.ajax('selectZjPxjhWeekTableTitle', {}, function (_d, _m, _s) {
                if (_s) {
                    $('.theads').remove();
                    $('#table').prepend('<thead class="theads"><th colspan="1" style="background-color:#b3ddfe;">' + _d[0] + '</th><th colspan="2" style="background-color:#b3ddfe;">' + _d[1] + '</th><th colspan="2" style="background-color:#b3ddfe;">' + _d[2] + '</th><th colspan="2" style="background-color:#b3ddfe;">' + _d[3] + '</th><th colspan="2" style="background-color:#b3ddfe;">' + _d[4] + '</th><th colspan="2" style="background-color:#b3ddfe;">' + _d[5] + '</th><th colspan="2" style="background-color:#b3ddfe;">' + _d[6] + '</th><th colspan="2" style="background-color:#b3ddfe;">' + _d[7] + '</th></thead>');
                    l.ajax('getZjPxjhWeekPlanWeekList', {}, function (data, message, success) {
                        if (success) {
                            table.clear().rows.add(data).draw();
                            $('.textareas').attr("readonly", true);
                        }
                    })
                }
            })
            break;
        case "next":
            goBack = 2;
            l.ajax('selectZjPxjhNextWeekTableTitle', {}, function (_d, _m, _s) {
                if (_s) {
                    $('.theads').remove();
                    $('#table').prepend('<thead class="theads"><th colspan="1" style="background-color:#b3ddfe;">' + _d[0] + '</th><th colspan="2" style="background-color:#b3ddfe;">' + _d[1] + '</th><th colspan="2" style="background-color:#b3ddfe;">' + _d[2] + '</th><th colspan="2" style="background-color:#b3ddfe;">' + _d[3] + '</th><th colspan="2" style="background-color:#b3ddfe;">' + _d[4] + '</th><th colspan="2" style="background-color:#b3ddfe;">' + _d[5] + '</th><th colspan="2" style="background-color:#b3ddfe;">' + _d[6] + '</th><th colspan="2" style="background-color:#b3ddfe;">' + _d[7] + '</th></thead>');
                    l.ajax('getZjPxjhWeekPlanNextWeekList', {}, function (data, message, success) {
                        if (success) {
                            table.clear().rows.add(data).draw();
                            $('.textareas').attr("readonly", true);
                        }
                    })
                }
            })
            break;
    }
})
function myblur(id,number,text,textarea){
    if(text.split('\n').join('').split('\r').join('') != textarea.val().split('\n').join('').split('\r').join('')){
        var _params = {
            planId:id
        };
        if(number == '1'){
            _params.mondayTrainContent = textarea.val().split('\n').join('').split('\r').join('');
            _params.showFlag = 1;
        }else if(number == '2'){
            _params.mondayMeetContent = textarea.val().split('\n').join('').split('\r').join('');
            _params.showFlag = 2;
        }else if(number == '3'){
            _params.tuesdayTrainContent = textarea.val().split('\n').join('').split('\r').join('');
            _params.showFlag = 3;
        }else if(number == '4'){
            _params.tuesdayMeetContent = textarea.val().split('\n').join('').split('\r').join('');
            _params.showFlag = 4;
        }else if(number == '5'){
            _params.wednesdayTrainContent = textarea.val().split('\n').join('').split('\r').join('');
            _params.showFlag = 5;
        }else if(number == '6'){
            _params.wednesdayMeetContent = textarea.val().split('\n').join('').split('\r').join('');
            _params.showFlag = 6;
        }else if(number == '7'){
            _params.thursdayTrainContent = textarea.val().split('\n').join('').split('\r').join('');
            _params.showFlag = 7;
        }else if(number == '8'){
            _params.thursdayMeetContent = textarea.val().split('\n').join('').split('\r').join('');
            _params.showFlag = 8;
        }else if(number == '9'){
            _params.fridayTrainContent = textarea.val().split('\n').join('').split('\r').join('');
            _params.showFlag = 9;
        }else if(number == '10'){
            _params.fridayMeetContent = textarea.val().split('\n').join('').split('\r').join('');
            _params.showFlag = 10;
        }else if(number == '11'){
            _params.saturdayTrainContent = textarea.val().split('\n').join('').split('\r').join('');
            _params.showFlag = 11;
        }else if(number == '12'){
            _params.saturdayMeetContent = textarea.val().split('\n').join('').split('\r').join('');
            _params.showFlag = 12;
        }else if(number == '13'){
            _params.sundayTrainContent = textarea.val().split('\n').join('').split('\r').join('');
            _params.showFlag = 13;
        }else if(number == '14'){
            _params.sundayMeetContent = textarea.val().split('\n').join('').split('\r').join('');
            _params.showFlag = 14;
        }
        l.ajax('updateZjPxjhWeekPlanAddDept', _params, function (data,message,success) {
            if(success){
                layer.alert(message,{ icon: 1 },function(index){
                    layer.close(index);
                    if(goBack == 1){
                        l.ajax('getZjPxjhWeekPlanWeekList', {}, function (data) {
                            table.clear().rows.add(data).draw();
                            $('.textareas').attr("readonly", false);
                        })
                    }else{
                        l.ajax('getZjPxjhWeekPlanNextWeekList', {}, function (data) {
                            table.clear().rows.add(data).draw();
                            $('.textareas').attr("readonly", false);
                        })
                    }
                });
            }else{
                layer.alert(message,{ icon: 0 });
            }          
        })
    }
}