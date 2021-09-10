var code = Lny.getUrlParam('code')
Lny.setCookie('code', code, 30)
l.ajax("getZjMeetingRoomEditJurisdiction", {}, function (_d, _m, _s) {
    if (_s) {
        $('#btnt').css('display', 'block');
    }
})
function datas(data) {
    var myDate = l.dateFormat(new Date(), "yyyy-MM-dd");
    if (data.indexOf('&') != -1) {
        if (data.indexOf('@') != -1) {
            var datas = data.split('@')[0].split('^')[1];
            if (data.split('@').length > 2) {
                var datac = data.split('@')[1].split('&');
                datac.pop();
                var datat = (datac.join('') + data.split('@')[2]).split('&').join('');
            } else {
                var datat = data.split('@')[1].split('&').join('');
            }
            if (datas.indexOf('&') != -1) {
                if (datas.split('&').length > 2) {
                    var datac = datas.split('&');
                    datac.pop();
                    datat = datac.join('') + datat;
                    if (datas.split('&').length > 1) {
                        datas = datas.split('&').pop();
                    } else {
                        datas = datas.pop();
                    }
                } else {
                    datat = datas.split('&')[0] + datat;
                    datas = datas.split('&')[1];
                }

            }
            if (data.indexOf(myDate) != -1) {
                return '<a style="color:#0c5aee;" onclick="myOpen(\'会议室详情\',\'' + datas + '\',\'' + 'zjMeetingRoomApplyDetail' + '\')" href="javascript:void(0);">' + datat + '</a>'
            } else {
                return '<a onclick="myOpen(\'会议室详情\',\'' + datas + '\',\'' + 'zjMeetingRoomApplyDetail' + '\')" href="javascript:void(0);">' + datat + '</a>'
            }
        } else {
            var datas = data.split('^')[1];
            if (datas.indexOf('*') != -1) {
                if (datas.split('&').length > 2) {
                    var datac = datas.split('&');
                    datac.pop();
                    datas = datac.join('') + datas.split('*')[1];
                } else {
                    datas = datas.split('&')[0] + datas.split('*')[1];
                }
            } else {
                datas = datas.split('&').join('');
            }
            if (data.indexOf(myDate) != -1) {
                return '<div style="color:#0c5aee;">' + datas + '</div>'
            } else {
                return '<div>' + datas + '</div>'
            }
        }
    } else {
        if (data.indexOf('^') != -1) {
            if (data.indexOf('*') != -1) {
                var datas = data.split('*')[0].split('^')[1];
                var datat = data.split('*')[1].split('\n').join('').split('\r').join('');
                var dataq = data.split('*')[1];
                var datae = data.split('*')[0].split('^')[0];
            } else {
                var datas = "";
                var datat = data.split('^')[1];
                var dataq = data.split('^')[1];
                var datae = data.split('^')[0];
            }
        } else {
            var datas = "";
            var datat = "";
            var dataq = "";
            var datae = "";
        }
        return "<textarea class='textareas' readonly onblur='myblur(\"" + datas + "\",\"" + datae + "\",0,\"" + dataq + "\",$(this))'>" + datat + "</textarea>"
    }
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
            "targets": [0],
            "title": '上午',
            "data": 'contentOne',//接口对应字段
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [1],//第几列
            "data": "contentTwo",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [2],//第几列
            "data": "contentThree",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [3],//第几列
            "data": "contentFour",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [4],//第几列
            "data": "contentFive",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [5],//第几列
            "data": "contentSix",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [6],//第几列
            "data": "contentSeven",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [7],//第几列
            "data": "contentEight",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [8],//第几列
            "data": "contentNine",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [9],//第几列
            "data": "contentTen",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [10],//第几列
            "data": "contentEleven",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [11],//第几列
            "data": "contentTwelve",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [12],//第几列
            "data": "contentThirteen",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [13],//第几列
            "data": "contentFourteen",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
                
        }
    ]
});
var tableq = $('#tableq').DataTable({
    "info": false,//是否显示数据信息
    "paging": false,//是否开启自带分页
    "ordering": false, //是否开启DataTables的高度自适应
    "processing": false,//是否显示‘进度’提示
    "searching": false,//是否开启自带搜索
    "autoWidth": false,//是否监听宽度变化
    "columnDefs": [//表格列配置
        {
            "targets": [0],
            "title": '上午',
            "data": 'contentOne',//接口对应字段
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [1],//第几列
            "data": "contentTwo",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [2],//第几列
            "data": "contentThree",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [3],//第几列
            "data": "contentFour",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [4],//第几列
            "data": "contentFive",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [5],//第几列
            "data": "contentSix",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [6],//第几列
            "data": "contentSeven",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [7],//第几列
            "data": "contentEight",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [8],//第几列
            "data": "contentNine",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [9],//第几列
            "data": "contentTen",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [10],//第几列
            "data": "contentEleven",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [11],//第几列
            "data": "contentTwelve",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [12],//第几列
            "data": "contentThirteen",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [13],//第几列
            "data": "contentFourteen",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        }
    ]
});
var tablew = $('#tablew').DataTable({
    "info": false,//是否显示数据信息
    "paging": false,//是否开启自带分页
    "ordering": false, //是否开启DataTables的高度自适应
    "processing": false,//是否显示‘进度’提示
    "searching": false,//是否开启自带搜索
    "autoWidth": false,//是否监听宽度变化
    "columnDefs": [//表格列配置
        {
            "targets": [0],
            "title": '上午',
            "data": 'contentOne',//接口对应字段
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [1],//第几列
            "data": "contentTwo",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [2],//第几列
            "data": "contentThree",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [3],//第几列
            "data": "contentFour",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [4],//第几列
            "data": "contentFive",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [5],//第几列
            "data": "contentSix",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [6],//第几列
            "data": "contentSeven",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [7],//第几列
            "data": "contentEight",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [8],//第几列
            "data": "contentNine",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [9],//第几列
            "data": "contentTen",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [10],//第几列
            "data": "contentEleven",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [11],//第几列
            "data": "contentTwelve",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [12],//第几列
            "data": "contentThirteen",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [13],//第几列
            "data": "contentFourteen",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        }
    ]
});
var tablee = $('#tablee').DataTable({
    "info": false,//是否显示数据信息
    "paging": false,//是否开启自带分页
    "ordering": false, //是否开启DataTables的高度自适应
    "processing": false,//是否显示‘进度’提示
    "searching": false,//是否开启自带搜索
    "autoWidth": false,//是否监听宽度变化
    "columnDefs": [//表格列配置
        {
            "targets": [0],
            "title": '上午',
            "data": 'contentOne',//接口对应字段
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [1],//第几列
            "data": "contentTwo",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [2],//第几列
            "data": "contentThree",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [3],//第几列
            "data": "contentFour",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [4],//第几列
            "data": "contentFive",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [5],//第几列
            "data": "contentSix",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [6],//第几列
            "data": "contentSeven",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [7],//第几列
            "data": "contentEight",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [8],//第几列
            "data": "contentNine",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [9],//第几列
            "data": "contentTen",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [10],//第几列
            "data": "contentEleven",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [11],//第几列
            "data": "contentTwelve",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [12],//第几列
            "data": "contentThirteen",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [13],//第几列
            "data": "contentFourteen",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        }
    ]
});
var tabler = $('#tabler').DataTable({
    "info": false,//是否显示数据信息
    "paging": false,//是否开启自带分页
    "ordering": false, //是否开启DataTables的高度自适应
    "processing": false,//是否显示‘进度’提示
    "searching": false,//是否开启自带搜索
    "autoWidth": false,//是否监听宽度变化
    "columnDefs": [//表格列配置
        {
            "targets": [0],
            "title": '上午',
            "data": 'contentOne',//接口对应字段
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [1],//第几列
            "data": "contentTwo",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [2],//第几列
            "data": "contentThree",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [3],//第几列
            "data": "contentFour",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [4],//第几列
            "data": "contentFive",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [5],//第几列
            "data": "contentSix",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [6],//第几列
            "data": "contentSeven",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [7],//第几列
            "data": "contentEight",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [8],//第几列
            "data": "contentNine",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [9],//第几列
            "data": "contentTen",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [10],//第几列
            "data": "contentEleven",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [11],//第几列
            "data": "contentTwelve",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [12],//第几列
            "data": "contentThirteen",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [13],//第几列
            "data": "contentFourteen",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        }
    ]
});
var tablet = $('#tablet').DataTable({
    "info": false,//是否显示数据信息
    "paging": false,//是否开启自带分页
    "ordering": false, //是否开启DataTables的高度自适应
    "processing": false,//是否显示‘进度’提示
    "searching": false,//是否开启自带搜索
    "autoWidth": false,//是否监听宽度变化
    "columnDefs": [//表格列配置
        {
            "targets": [0],
            "title": '上午',
            "data": 'contentOne',//接口对应字段
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [1],//第几列
            "data": "contentTwo",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [2],//第几列
            "data": "contentThree",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [3],//第几列
            "data": "contentFour",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [4],//第几列
            "data": "contentFive",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [5],//第几列
            "data": "contentSix",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [6],//第几列
            "data": "contentSeven",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [7],//第几列
            "data": "contentEight",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [8],//第几列
            "data": "contentNine",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [9],//第几列
            "data": "contentTen",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [10],//第几列
            "data": "contentEleven",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [11],//第几列
            "data": "contentTwelve",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [12],//第几列
            "data": "contentThirteen",//接口对应字段
            "title": "上午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        },
        {
            "targets": [13],//第几列
            "data": "contentFourteen",//接口对应字段
            "title": "下午",//表头名
            "defaultContent": "-",//默认显示
            'width': '7.1%',
            'render': function (data) {
                return datas(data);
            }
        }
    ]
});
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "select",//三种形式：text,select,date,
            name: "recordid",//输入字段名
            label: "会议室",//输入字段对杨的中文名称
            ajax: {
                api: "getMeetingRoomAllList",//api名称
                valueName: "recordid",//设置value对应的接口字段名称
                textName: "meetingRoomName",//设置text对应的接口字段名称
            },
            lineNum: 6,
        },
        {
            type: "date",//text,select,date,
            name: "years",
            label: "月份",
            dateFmt: "yyyy-MM",
            defaultValue: l.dateFormat(new Date(), "yyyy-MM"),
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
        pager.page('remote', 0, _params)
    },
    onReset: function (arr) {//重置按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
        pager.page('remote', _params)
    }
})
var pager = $("#pager").page({
    remote: {//ajax请求配置
        url: l.getApiUrl("getZjMeetingRoomSituationList"),
        params: {
        },
        success: function (result) {
            if (result.success) {
                $('.theads').remove();
                var data = result.data;
                if (data.maxDay && data.maxDay != "") {
                    for (var i = 0; i < 42; i++) {
                        if (data.maxDay[i] == null || data.maxDay[i] == 'undefined') {
                            data.maxDay[i] = "";
                        }
                    }
                    if (data.maxDay[28] == null || data.maxDay[28] == 'undefined' || data.maxDay[28] == '') {
                        $('#tabler').hide();
                    } else {
                        $('#tabler').show();
                    }
                    if (data.maxDay[35] == null || data.maxDay[35] == 'undefined' || data.maxDay[35] == '') {
                        $('#tablet').hide();
                    } else {
                        $('#tablet').show();
                    }
                    $('#table').prepend('<thead class="theads"><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[0] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周一)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[1] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周二)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[2] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周三)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[3] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周四)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[4] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周五)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[5] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周六)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[6] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周日)</div></th></thead>');
                    $('#tableq').prepend('<thead class="theads"><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[7] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周一)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[8] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周二)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[9] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周三)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[10] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周四)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[11] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周五)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[12] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周六)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[13] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周日)</div></th></thead>');
                    $('#tablew').prepend('<thead class="theads"><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[14] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周一)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[15] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周二)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[16] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周三)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[17] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周四)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[18] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周五)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[19] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周六)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[20] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周日)</div></th></thead>');
                    $('#tablee').prepend('<thead class="theads"><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[21] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周一)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[22] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周二)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[23] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周三)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[24] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周四)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[25] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周五)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[26] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周六)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[27] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周日)</div></th></thead>');
                    $('#tabler').prepend('<thead class="theads"><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[28] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周一)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[29] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周二)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[30] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周三)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[31] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周四)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[32] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周五)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[33] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周六)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[34] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周日)</div></th></thead>');
                    $('#tablet').prepend('<thead class="theads"><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[35] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周一)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[36] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周二)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[37] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周三)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[38] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周四)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[39] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周五)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[40] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周六)</div></th><th colspan="2" style="text-align:center;background-color:#b3ddfe;font-size:16px;">' + data.maxDay[41] + '<div style="float:right;font-size:12px;color:#da0f0f;">(周日)</div></th></thead>');
                }
                table.clear().rows.add(data.date1).draw();
                tableq.clear().rows.add(data.date2).draw();
                tablew.clear().rows.add(data.date3).draw();
                tablee.clear().rows.add(data.date4).draw();
                tabler.clear().rows.add(data.date5).draw();
                tablet.clear().rows.add(data.date6).draw();
                $('#arrang').text('当前会议室:' + data.meetingRoomName);
            } else {
                layer.alert(result.message, { icon: 0, }, function (index) {
                    layer.close(index);
                });
            }
        },
    }
});
$(function () {
    //兼容不支持placeholder的浏览器[ie浏览器，并且10以下均采用替代方式处理]
    if ((navigator.appName == "Microsoft Internet Explorer") && (document.documentMode < 10 || document.documentMode == undefined)) {
        var $placeholder = $("input[placeholder]");
        $placeholder.each(function (i) {
            $placeholder.eq(i).parent().append("<span class='placeholder'>" + $placeholder.eq(i).attr('placeholder') + "</span>");
        })
        $('.placeholder').click(function () {
            $(this).parent().find('span.placeholder').hide();
            $(this).parent().find('input').focus();
        })
        $placeholder.focus(function () {
            if ($(this).val() == "") {
                $(this).parent().find('span.placeholder').hide();
            }
        }).blur(function () {
            if ($(this).val() == "") {
                $(this).parent().find('span.placeholder').show();
            }
        });
    }
});
function myOpen(title, id, url) {
    window.open(url + ".html?&code=" + code + "&id=" + id, '', 'width=' + (window.screen.availWidth - 200) + ',height=' + (window.screen.availHeight - 200) + ',scrollbars=yes,top=100,left=100')
}
$('#btns').click(function () {
    window.location.href = 'zjMeetingRoomApproveOpen.html?code=' + code;
});
$('#btnt').click(function () {
    $('.textareas').attr("readonly", false);
})
function myblur(outsideMeetingId, days, afternoon, text, textarea) {
    if (text.split('\n').join('').split('\r').join('') != textarea.val().split('\n').join('').split('\r').join('')) {
        var _params = {
            outsideMeetingId: outsideMeetingId,
            days: days,
            afternoon: afternoon,
            outsideMeetingContent: textarea.val(),
            meetingRoomName: $('#arrang').text().split(':')[1],
            years: $('input').val()

        }
        var _paramt = {
            meetingRoomName: $('#arrang').text().split(':')[1],
            years: $('input').val()

        }
        l.ajax('addZjMeetingOutsideMeetingSituation', _params, function (data, message, success) {
            if (success) {
                pager.page('remote', 0, _paramt);
            } else {
                layer.alert(message, { icon: 0 });
            }
        })
    }
}
