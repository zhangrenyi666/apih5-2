var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30)
var form = $('#form').filterfrom({
    conditions: [//表单项配置
        {
            type: "select",
            name: "typeId",
            label: "类型",
            selectList: [//当类型为select时，option配置
                {
                    value: "0",
                    text: "项目类型"
                },
                {
                    value: "1",
                    text: "部门类型"
                }
            ],
            immutableAdd: true
        },
        {
            type: "date",//text,select,date,
            name: "startDate",
            label: "问题出现开始时间",
            id: "logmin",
            immutableAdd: true
        },
        {
            type: "date",//text,select,date,
            name: "endDate",
            label: "问题出现结束时间",
            id: "logmax",
            immutableAdd: true
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
        if (_params.typeId == 0) {
            $('#container').show();
            l.ajax('getQuestionListReportForm', _params, function (data) {
                $('.tables').empty();
                $('.tables').append('<tr class="classify"></tr><tr class="accomplish"></tr><tr class="exist"></tr>')
                $('.classify').append('<th>分类</th>');
                $('.accomplish').append('<td><i style="background: #61daa5;width: 16px;height: 16px;display: inline-block;vertical-align:middle;margin-right: 5px;"></i><span style="vertical-align:middle;">整改完成</span></td>');
                $('.exist').append('<td><i style="background: #0099FF;width: 16px;height: 16px;display: inline-block;vertical-align:middle;margin-right: 5px;"></i><span style="vertical-align:middle;">存在问题</span></td>');
                if (data.nameDate) {
                    $.each(data.nameDate, function (i) {
                        $('.classify').append('<th>' + data.nameDate[i] + '</th>');
                        $('.accomplish').append('<td>' + data.completedNumDate[i] + '</td>');
                        $('.exist').append('<td>' + data.notCompletedNumDate[i] + '</td>');
                    })
                    dom_h(data);
                }
            })
        } else {
            $('#containers').show();
            l.ajax('getQuestionListReportForm', _params, function (data) {
                $('.tables').empty();
                $('.tables').append('<tr class="tr1"></tr><tr class="tr2"></tr><tr class="tr3"></tr><tr class="tr4"></tr><tr class="tr5"></tr><tr class="tr6"></tr><tr class="tr7"></tr><tr class="tr8"></tr><tr class="tr9"></tr>');
                if (data.nameDate != "") {
                    $('.tr1').append('<th>部门</th>');
                    $.each(data.nameDate, function (i) {
                        $('.tr1').append('<th>' + data.nameDate[i] + '</th>');
                    })
                    if (data.a1 != null) {
                        $('.tr2').append('<td>·问题类型一</td>');
                        $.each(data.a1, function (i) {
                            $('.tr2').append('<td>' + data.a1[i].name + '</td>');
                        })
                    }
                    if (data.a2 != null) {
                        $('.tr3').append('<td>·问题类型二</td>');
                        $.each(data.a2, function (i) {
                            $('.tr3').append('<td>' + data.a2[i].name + '</td>');
                        })
                    }
                    if (data.a3 != null) {
                        $('.tr4').append('<td>·问题类型三</td>');
                        $.each(data.a3, function (i) {
                            $('.tr4').append('<td>' + data.a3[i].name + '</td>');
                        })
                    }
                    if (data.a4 != null) {
                        $('.tr5').append('<td>·问题类型四</td>');
                        $.each(data.a4, function (i) {
                            $('.tr4').append('<td>' + data.a4[i].name + '</td>');
                        })
                    }
                    if (data.a5 != null) {
                        $('.tr6').append('<td>·问题类型五</td>');
                        $.each(data.a5, function (i) {
                            $('.tr4').append('<td>' + data.a5[i].name + '</td>');
                        })
                    }
                    if (data.a6 != null) {
                        $('.tr7').append('<td>·问题类型六</td>');
                        $.each(data.a6, function (i) {
                            $('.tr4').append('<td>' + data.a6[i].name + '</td>');
                        })
                    }
                    if (data.a7 != null) {
                        $('.tr8').append('<td>·问题类型七</td>');
                        $.each(data.a7, function (i) {
                            $('.tr4').append('<td>' + data.a7[i].name + '</td>');
                        })
                    }
                    if (data.a8 != null) {
                        $('.tr9').append('<td>·问题类型八</td>');
                        $.each(data.a8, function (i) {
                            $('.tr4').append('<td>' + data.a8[i].name + '</td>');
                        })
                    }
                    dom_h(data);
                }
            })
        }

    },
    onReset: function (arr) {//重置按钮回调
        var _params = {};
        for (var i = 0; i < arr.length; i++) {
            _params[arr[i].name] = arr[i].value;
        }
        $('#container').show();
        l.ajax('getQuestionListReportForm', _params, function (data) {
            $('.tables').empty();
            $('.tables').append('<tr class="classify"></tr><tr class="accomplish"></tr><tr class="exist"></tr>')
            $('.classify').append('<th>分类</th>');
            $('.accomplish').append('<td><i style="background: #61daa5;width: 16px;height: 16px;display: inline-block;vertical-align:middle;margin-right: 5px;"></i><span style="vertical-align:middle;">整改完成</span></td>');
            $('.exist').append('<td><i style="background: #0099FF;width: 16px;height: 16px;display: inline-block;vertical-align:middle;margin-right: 5px;"></i><span style="vertical-align:middle;">存在问题</span></td>');
            if (data.nameDate) {
                $.each(data.nameDate, function (i) {
                    $('.classify').append('<th>' + data.nameDate[i] + '</th>');
                    $('.accomplish').append('<td>' + data.completedNumDate[i] + '</td>');
                    $('.exist').append('<td>' + data.notCompletedNumDate[i] + '</td>');
                })
                dom_h(data);
            }
        })
    }
})
$('.btn-primary').parent().append('<button data-name="derive" class="btn ml-20  btn-primary" type="button"><i class="Hui-iconfont">&#xe645;</i> 导出</button>');
$("body").on("click", "button", function () {
    var name = $(this).attr("data-name");
    switch (name) {
        case "derive"://导出
            var params = {};
            params.typeId = $('[name = "typeId"]').val();
            params.startDate = $('[name = "startDate"]').val();
            params.endDate = $('[name = "endDate"]').val();
            layer.alert("确定导出此数据？", { icon: 0, }, function (index) {
                l.ajax('questionListReportFormExport', params, function (res) {
                    layer.alert("导出成功！", { icon: 0 }, function (index) {
                        layer.close(index);
                        window.location.href = res;
                    })
                })
            });
            break;
    }
})

function dom_h(datas) {
    var dom = document.getElementById("container");
    var doms = document.getElementById("containers");
    var myChart = echarts.init(dom);
    var myCharts = echarts.init(doms);
    if (datas.typeId == "0") {
        $('#containers').hide();
        option = null;
        option = {
            tooltip: {
                trigger: 'axis',
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    data: datas.nameDate
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    splitNumber: 1
                }
            ],
            series: [
                {
                    name: '存在问题',
                    type: 'bar',
                    stack: '项目',
                    data: datas.notCompletedNumDate,
                    itemStyle: {
                        normal: {
                            color: '#0099FF'
                        }
                    },
                    label: {
                        normal: {
                            show: true,
                            position: 'inside',
                        }
                    }
                },
                {
                    name: '整改完成',
                    type: 'bar',
                    stack: '项目',
                    data: datas.completedNumDate,
                    itemStyle: {
                        normal: {
                            color: '#61daa5'
                        }
                    },
                    label: {
                        normal: {
                            show: true,
                            position: 'inside'
                        }
                    }
                }
            ]
        };
        myChart.setOption(option, true);
        var iTop = (window.screen.availHeight - 30 - 600) / 2;
        var iLeft = (window.screen.availWidth - 10 - 800) / 2;
        myChart.on('click', function (param) {
            var modifyFlag = "";
            if (param.seriesName == "存在问题") {
                modifyFlag = 1;
            } else if (param.seriesName == "整改完成") {
                modifyFlag = 0;
            }
            window.open('questionStatisticsTables.html?code=' + code + '&name=' + param.name + '&modifyFlag=' + modifyFlag, '', 'width=800,height=600,scrollbars=yes,top=' + iTop + ',' + 'left=' + iLeft);
        });
    } else if (datas.typeId == "1") {
        $('#container').hide();
        option = null;
        option = {
            tooltip: {
                trigger: 'axis',
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    data: datas.nameDate || []
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    splitNumber: 1
                }
            ],
            series: [
                {
                    name: '',
                    type: 'bar',
                    stack: '总量',
                    data: datas.a1 || [],
                    itemStyle: {
                        normal: {
                            color: '#23b9cc'
                        }
                    },
                    label: {
                        normal: {
                            show: true,
                            formatter: '{b}'
                        }
                    }
                },
                {
                    name: '',
                    type: 'bar',
                    stack: '总量',
                    data: datas.a2 || [],
                    itemStyle: {
                        normal: {
                            color: '#53c047'
                        }
                    },
                    label: {
                        normal: {
                            show: true,
                            formatter: '{b}'
                        }
                    }
                },
                {
                    name: '',
                    type: 'bar',
                    stack: '总量',
                    data: datas.a3 || [],
                    itemStyle: {
                        normal: {
                            color: '#ffc252'
                        }
                    },
                    label: {
                        normal: {
                            show: true,
                            formatter: '{b}'
                        }
                    }
                },
                {
                    name: '',
                    type: 'bar',
                    stack: '总量',
                    data: datas.a4 || [],
                    itemStyle: {
                        normal: {
                            color: '#98319b'
                        }
                    },
                    label: {
                        normal: {
                            show: true,
                            formatter: '{b}'
                        }
                    }
                },
                {
                    name: '',
                    type: 'bar',
                    stack: '总量',
                    data: datas.a5 || [],
                    itemStyle: {
                        normal: {
                            color: '#ca6f6f'
                        }
                    },
                    label: {
                        normal: {
                            show: true,
                            formatter: '{b}'
                        }
                    }
                },
                {
                    name: '',
                    type: 'bar',
                    stack: '总量',
                    data: datas.a6 || [],
                    itemStyle: {
                        normal: {
                            color: '#261fba'
                        }
                    },
                    label: {
                        normal: {
                            show: true,
                            formatter: '{b}'
                        }
                    }
                },
                {
                    name: '',
                    type: 'bar',
                    stack: '总量',
                    data: datas.a7 || [],
                    itemStyle: {
                        normal: {
                            color: '#c6e522'
                        }
                    },
                    label: {
                        normal: {
                            show: true,
                            formatter: '{b}'
                        }
                    }
                },
                {
                    name: '',
                    type: 'bar',
                    stack: '总量',
                    data: datas.a8 || [],
                    itemStyle: {
                        normal: {
                            color: '#22a722'
                        }
                    },
                    label: {
                        normal: {
                            show: true,
                            formatter: '{b}'
                        }
                    }
                }
            ]
        };
        myCharts.setOption(option, true);
        var iTop = (window.screen.availHeight - 30 - 600) / 2;
        var iLeft = (window.screen.availWidth - 10 - 800) / 2;
        myCharts.on('click', function (param) {
            window.open('questionStatisticsTable.html?code=' + code + '&id=' + param.data.id, '', 'width=800,height=600,scrollbars=yes,top=' + iTop + ',' + 'left=' + iLeft);
        });
    }
}
l.ajax('getQuestionListReportForm', { typeId: 0 }, function (data) {
    $('.tables').append('<tr class="classify"></tr><tr class="accomplish"></tr><tr class="exist"></tr>')
    $('.classify').append('<th>分类</th>');
    $('.accomplish').append('<td><i style="background: #61daa5;width: 16px;height: 16px;display: inline-block;vertical-align:middle;margin-right: 5px;"></i><span style="vertical-align:middle;">整改完成</span></td>');
    $('.exist').append('<td><i style="background: #0099FF;width: 16px;height: 16px;display: inline-block;vertical-align:middle;margin-right: 5px;"></i><span style="vertical-align:middle;">存在问题</span></td>');
    if (data.nameDate) {
        $.each(data.nameDate, function (i) {
            $('.classify').append('<th>' + data.nameDate[i] + '</th>');
            $('.accomplish').append('<td>' + data.completedNumDate[i] + '</td>');
            $('.exist').append('<td>' + data.notCompletedNumDate[i] + '</td>');
        })
        dom_h(data);
    }
})