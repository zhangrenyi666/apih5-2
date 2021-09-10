import React, { Component } from 'react';
import ReactEcharts from 'echarts-for-react';
import { message as Msg } from 'antd';
class BMTJ extends Component {
    state = {
        aqH: (window.innerHeight - 96) / 2,
        xData:[]
    }
    componentDidMount() {
        window.addEventListener('resize', this.autoSize, false);
        // this.props.myFetch('getZjSjConsultDeptStatistics', {  }).then(({ success, message, data }) => {
        //     if (success) {
        //         console.log(data);
        //         this.setState({
        //             xData: data
        //         });
        //     } else {
        //         Msg.error(message);
        //     }
        // });
    }
    autoSize = () => {
        this.setState({
            aqH: (window.innerHeight - 96) / 2
        })
    }
    componentWillUnmount() {
        window.removeEventListener('resize', this.autoSize);
    }
    getOption = () => {
        const { xData } = this.state;
        const option = {
            title: {
                text: '部门统计',
                x: 'center',
                textStyle: {
                    color: '#f49100',
                    fontSize: 20
                },
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow',        // 默认为直线，可选为：'line' | 'shadow'
                    itemStyle: {
                        color: '#eebdc1'
                    }
                }
            },
            legend: {
                top: 25,
                data: ['办理总数', '发起次数', '未办总数', '收到次数'],
                x: 'center',
                textStyle: {
                    color: 'white',
                },
            },
            grid: {
                left: 5,
                bottom: 0,
                top: 55,
                right: 0,
                containLabel: true,
            },
            xAxis: {
                type: 'category',
                data: ['办公室', '财务部', '安全环保监督部', '安全监督部', '安全监督处'],
                axisLabel: {
                    interval: 0,
                    formatter: function (params) {
                        var newParamsName = "";
                        var paramsNameNumber = params.length;
                        var provideNumber = 4;
                        var rowNumber = Math.ceil(paramsNameNumber / provideNumber);
                        if (paramsNameNumber > provideNumber) {
                            for (var p = 0; p < rowNumber; p++) {
                                var tempStr = "";
                                var start = p * provideNumber;
                                var end = start + provideNumber;
                                if (p == rowNumber - 1) {
                                    tempStr = params.substring(start, paramsNameNumber);
                                } else {
                                    tempStr = params.substring(start, end) + "\n";
                                }
                                newParamsName += tempStr;
                            }

                        } else {
                            newParamsName = params;
                        }
                        return newParamsName
                    }
                },
                axisLine: {
                    lineStyle: {
                        color: '#ccc'
                    }
                },
            },
            yAxis: {
                type: 'value',
                axisLine: {
                    lineStyle: {
                        color: '#ccc'
                    }
                }
            },
            textStyle: {
                color: 'white'
            },
            label: {
                show: true
            },
            series: [
                {
                    name: '办理总数',
                    type: 'bar',
                    stack: 'x',
                    data: [320, 332, 301, 334, 390]
                },
                {
                    name: '发起次数',
                    type: 'bar',
                    stack: 'x',
                    data: [320, 332, 301, 334, 390]
                },
                {
                    name: '未办总数',
                    type: 'bar',
                    stack: 'x',
                    data: [320, 332, 301, 334, 390]
                },
                {
                    name: '收到次数',
                    type: 'bar',
                    stack: 'x',
                    data: [320, 332, 301, 334, 390]
                }
            ]
        };
        return option
    }
    render() {
        const { aqH, xData } = this.state;
        return (
            <div>
                <ReactEcharts
                    style={{ height: aqH }}
                    option={this.getOption()}
                    notMerge={true}
                    lazyUpdate={true}
                />
            </div>
        )
    }
}
export { BMTJ }