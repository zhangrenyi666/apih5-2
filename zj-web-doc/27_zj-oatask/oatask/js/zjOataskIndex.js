var code = Lny.getUrlParam('code');
Lny.setCookie('code', code, 30);
function dom_Echarts(data) {
    var dom = document.getElementById("zjEcharts");
    var myChart = echarts.init(dom);
    option = null;
    option = {
        tooltip: {
            trigger: 'axis',
        },
        grid: {
            left: 2,
            right: 5,
            top: 10,
            bottom: 10,
            containLabel: true
        },
        // xAxis: [
        //     {
        //         type: 'category',
        //         axisLabel: {
        //             interval: 0,
        //         },
        //         axisLine: {
        //             lineStyle: {
        //                 color: 'white'
        //             }
        //         },
        //         data: data.deptName
        //     }
        // ],
        // yAxis: [
        //     {
        //         type: 'value',
        //         axisLine: {
        //             lineStyle: {
        //                 color: 'white'
        //             }
        //         }
        //     }
        // ],
        xAxis: {
            type: 'category',
            data: data.deptName,
            axisLabel: {
                interval: 0,
                // fontSize: 9,
                // formatter: function (params) {
                //     var newParamsName = "";
                //     var paramsNameNumber = params.length;
                //     var provideNumber = 10;
                //     var rowNumber = Math.ceil(paramsNameNumber / provideNumber);
                //     if (paramsNameNumber > provideNumber) {
                //         for (var p = 0; p < rowNumber; p++) {
                //             var tempStr = "";
                //             var start = p * provideNumber;
                //             var end = start + provideNumber;
                //             if (p == rowNumber - 1) {
                //                 tempStr = params.substring(start, paramsNameNumber);
                //             } else {
                //                 tempStr = params.substring(start, end) + "\n";
                //             }
                //             newParamsName += tempStr;
                //         }

                //     } else {
                //         newParamsName = params;
                //     }
                //     return newParamsName
                // }
            },
            axisLine: {
                lineStyle: {
                    color: 'white'
                }
            },
        },
        yAxis: {
            type: 'value',
            boundaryGap: [0, 0.01],
            axisLine: {
                lineStyle: {
                    color: 'white'
                }
            }
        },
        series: [
            {
                name: '??????',
                type: 'bar',
                stack: 'x',
                data: data.totalNumber,
                itemStyle: {
                    normal: {
                        color: '#1890ff'
                    }
                },
                label: {
                    normal: {
                        show: true,
                        position: 'inside',
                    }
                }
            }
        ]
    };
    myChart.setOption(option, true);
}
$(function () {
    l.ajax("getZjOataskColumnList", {}, function (data, message, success) {
        if (success) {
            dom_Echarts(data);
        }
    })
    l.ajax("getZjOataskRollingList", { limit: 50, page: 1 }, function (data, message, success) {
        if (success) {
            if (data.length) {
                for (var i = 0; i < data.length; i++) {
                    $('#zjScroll ul').append('<li><div class="zjScroll_d rounded"><div><div class="zjScroll_dl">???????????????' + data[i].taskMatter + '</div><div class="zjScroll_dr">????????????' + data[i].leaderName + '</div></div><div class="zjContent">???????????????' + data[i].content + '</div></div></li>');
                }
                if (data.length === $('#zjScroll ul li').length) {
                    if (window.PIE.attach) {
                        $('.rounded').each(function () {
                            PIE.attach(this);
                        });
                    }
                    $('#zjScroll').myScroll({
                        speed: 40, //???????????????????????????
                        rowHeight: 80//li?????????
                    });
                    // setInterval(function(){
                    //     $('#zjScroll ul li:first').animate({'height':'0','opacity': '0'}, 'slow', function() {
                    //       $(this).removeAttr('style').insertAfter('#zjScroll ul li:last');
                    //     });
                    // },1000);
                    // $(this).attr('disabled',true);
                }
            }
        }
    })
    // if (window.PIE.attach) {
    //     $('.rounded').each(function () {
    //         PIE.attach(this);
    //     });
    // }
    $('#table').prepend('<thead><th  class="zjThead" colspan="6"">????????????????????????</th></thead>');
    $('#tables').prepend('<thead><th  class="zjThead" colspan="6"">????????????????????????</th></thead>');
});
var table = $('#table').DataTable({
    "info": false,//????????????????????????
    "paging": false,//????????????????????????
    "ordering": false, //????????????DataTables??????????????????
    "processing": false,//??????????????????????????????
    "searching": false,//????????????????????????
    "autoWidth": false,//????????????????????????
    "columnDefs": [//???????????????
        {
            "targets": [0],//?????????
            "data": "undertakeDeptName",//??????????????????
            "title": "??????/??????",//?????????
            "defaultContent": "-",//????????????
            "render": function (data) {
                if (data) {
                    return data
                } else {
                    return '0'
                }
            }
        },
        {
            "targets": [1],//?????????
            "data": "totalNum",//??????????????????
            "title": "????????????",//?????????
            "defaultContent": "-",//????????????
            "render": function (data) {
                if (data) {
                    return data
                } else {
                    return '0'
                }
            }
        },
        {
            "targets": [2],//?????????
            "data": "finishedNum",//??????????????????
            "title": "???????????????",//?????????
            "defaultContent": "-",//????????????
            "render": function (data) {
                if (data) {
                    return data
                } else {
                    return '0'
                }
            }
        },
        {
            "targets": [3],//?????????
            "data": "unfinishedNum",//??????????????????
            "title": "<span style='color:#e8ec2d;'>????????????</span>",//?????????
            "defaultContent": "-",//????????????
            "render": function (data) {
                if (data) {
                    return '<span style="color:#e8ec2d;">' + data + '</span>'
                } else {
                    return '<span style="color:#e8ec2d;">' + 0 + '</span>'
                }
            }
        },
        {
            "targets": [4],//?????????
            "data": "overdueNum",//??????????????????
            "title": "<span style='color:#f31c1c;'>??????????????????</span>",//?????????
            "defaultContent": "-",//????????????
            "render": function (data) {
                if (data) {
                    return '<span style="color:#f31c1c;">' + data + '</span>'
                } else {
                    return '<span style="color:#f31c1c;">' + 0 + '</span>'
                }
            }
        },
        {
            "targets": [5],//?????????
            "data": "unoverdueNum",//??????????????????
            "title": "<span style='color:#ec7409;'>???????????????</span>",//?????????
            "defaultContent": "-",//????????????
            "render": function (data) {
                if (data) {
                    return '<span style="color:#ec7409;">' + data + '</span>'
                } else {
                    return '<span style="color:#ec7409;">' + 0 + '</span>'
                }
            }
        }
    ]
});
var tables = $('#tables').DataTable({
    "info": false,//????????????????????????
    "paging": false,//????????????????????????
    "ordering": false, //????????????DataTables??????????????????
    "processing": false,//??????????????????????????????
    "searching": false,//????????????????????????
    "autoWidth": false,//????????????????????????
    "columnDefs": [//???????????????
        {
            "targets": [0],//?????????
            "data": "leaderName",//??????????????????
            "title": "?????????",//?????????
            "defaultContent": "-",//????????????
        },
        {
            "targets": [1],//?????????
            "data": "totalNum",//??????????????????
            "title": "????????????",//?????????
            "defaultContent": "-",//????????????
            "render": function (data) {
                if (data) {
                    return data
                } else {
                    return '0'
                }
            }
        },
        {
            "targets": [2],//?????????
            "data": "finishedNum",//??????????????????
            "title": "???????????????",//?????????
            "defaultContent": "-",//????????????
            "render": function (data) {
                if (data) {
                    return data
                } else {
                    return '0'
                }
            }
        },
        {
            "targets": [3],//?????????
            "data": "unfinishedNum",//??????????????????
            "title": "<span style='color:#e8ec2d;'>????????????</span>",//?????????
            "defaultContent": "-",//????????????
            "render": function (data) {
                if (data) {
                    return '<span style="color:#e8ec2d;">' + data + '</span>'
                } else {
                    return '<span style="color:#e8ec2d;">' + 0 + '</span>'
                }
            }
        },
        {
            "targets": [4],//?????????
            "data": "overdueNum",//??????????????????
            "title": "<span style='color:#f31c1c;'>??????????????????</span>",//?????????
            "defaultContent": "-",//????????????
            "render": function (data) {
                if (data) {
                    return '<span style="color:#f31c1c;">' + data + '</span>'
                } else {
                    return '<span style="color:#f31c1c;">' + 0 + '</span>'
                }
            }
        },
        {
            "targets": [5],//?????????
            "data": "unoverdueNum",//??????????????????
            "title": "<span style='color:#ec7409;'>???????????????</span>",//?????????
            "defaultContent": "-",//????????????
            "render": function (data) {
                if (data) {
                    return '<span style="color:#ec7409;">' + data + '</span>'
                } else {
                    return '<span style="color:#ec7409;">' + 0 + '</span>'
                }
            }
        }
    ]
});
var pager = $("#pager").page({
    remote: {//ajax????????????
        url: l.getApiUrl("getZjOataskStatisticAnalysisList"),
        params: {},
        success: function (result) {
            if (result.success) {
                var data = result.data;
                $.each(data, function (index, item) {
                    item.rowIndex = index
                })
                if (data.length && data[0].redFlag === '1') {
                    $('#tables').css('display', 'table');
                    tables.clear().rows.add(data).draw();
                } else {
                    $('#table').css('display', 'table');
                    table.clear().rows.add(data).draw();
                }
            } else {
                layer.alert(result.message, { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
        },
    }
});