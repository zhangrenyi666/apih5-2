var code = Apih5.getUrlParam('code')
Apih5.setCookie('code', code, 30);
l.ajax("getZjXmRuleRecordListForChart", {}, function (data, message, success) {
    if (success) {
        dom_Echarts(data);
    }
})
function dom_Echarts(data) {
    var operationYearList = [];
    var num1List = [];
    var num2List = [];
    var num3List = [];
    for(var i = 0; i < data.length; i++){
        operationYearList.push(data[i].operationYear);
        num1List.push(data[i].num1);
        num2List.push(data[i].num2);
        num3List.push(data[i].num3);
    }
    var dom = document.getElementById("zjEcharts");
    var myChart = echarts.init(dom);
    option = null;
    option = {
        title: {
            text: '条形统计直观图',
            x: 'center',
            textStyle: {
                fontSize: 20
            }
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        legend: {
            top:30,
            data:['新增制度','修改制度','废弃制度'],
            x: 'center'
        },
        grid: {
            left: 2,
            right: 5,
            top: 60,
            bottom: 10,
            containLabel: true
        },
        xAxis: {
            type: 'category',
            data: operationYearList,
            axisLabel: {
                interval: 0
            }
        },
        yAxis: {
            type: 'value',
            boundaryGap: [0, 0.01]
        },
        series: [
            {
                name: '新增制度',
                type: 'bar',
                data: num1List,
                itemStyle: {
                    normal: {
                        color: '#10cf9b'
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
                name: '修改制度',
                type: 'bar',
                data: num2List,
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
            },
            {
                name: '废弃制度',
                type: 'bar',
                data: num3List,
                itemStyle: {
                    normal: {
                        color: '#f49100'
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