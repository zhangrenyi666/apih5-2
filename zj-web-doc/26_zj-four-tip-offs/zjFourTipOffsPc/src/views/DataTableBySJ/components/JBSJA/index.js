import React, { Component } from 'react'
import { Spin, message as Msg } from 'antd';
import s from './style.less'
import ReactEcharts from 'echarts-for-react';
class JBSJA extends Component {
    state = {
        aqH: (window.innerHeight - 165 - ((window.innerHeight - 165) / 2) - 77) * 0.65,
        loading: false,
        data: [],
        startDate:this.props.startDate,
        endDate:this.props.endDate
    }
    componentDidMount() {
        window.addEventListener('resize', this.autoSize, false);
        this.refresh();
    }
    componentWillUnmount() {
        window.removeEventListener('resize', this.autoSize);
    }
    autoSize = () => {
        this.setState({
            aqH: (window.innerHeight - 172 - ((window.innerHeight - 172) / 2) - 77) * 0.65,
        })
    }

    refresh = () => {
        this.setState({loading:true});
        const { myFetch } = this.props;
        const { startDate, endDate } = this.state;
        myFetch('getAqyhReportDataStatistics', {type:'2',startDate,endDate}).then(({ data, success, message }) => {
            if (success) {
                this.setState({
                    data,
                    loading:false
                })
            } else {
                Msg.error(message)
            }
        })
    }
    getOption = () => {
        const { data } = this.state;
        const option = {
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
                top:5,
                data:['无效举报','有效举报'],
                x: 'center',
                textStyle: {
                    color: 'white',
                },
            },
            grid: {
                left: 5,
                bottom: 0,
                top: 43,
                right: 0,
                containLabel: true,
            },
            xAxis: {
                type: 'category',
                data: ['举报数据'],
                axisLabel: {
                    interval: 0
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
                    name: '无效举报',
                    type: 'bar',
                    stack: 'aq',
                    data: data && data[0] ? [Number(data[0].reportDataTotalNum)] : [],
                    itemStyle: {
                        color: '#1890ff'
                    }
                },
                {
                    name: '有效举报',
                    type: 'bar',
                    stack: 'aqs',
                    data: data && data[1] ? [Number(data[1].reportDataTotalNum)] : [],
                    itemStyle: {
                        color: '#10cf9b'
                    }
                }
            ]
        };
        return option
    }
    componentWillReceiveProps(nextProps) {
        if (this.state.startDate !== nextProps.startDate || this.state.endDate !== nextProps.endDate) {
            this.setState({
                startDate: nextProps.startDate,
                endDate: nextProps.endDate
            }, () => {
                this.refresh();
            })
        }
    }
    render() {
        const { loading,aqH } = this.state;
        return (
            <Spin spinning={loading}>
                <div className={s.zl}>
                    <div className={s.title}>
                        举报数据
                    </div>
                    <div>
                        <ReactEcharts
                            style={{ height: aqH/0.7 }}
                            option={this.getOption()}
                            notMerge={true}
                            lazyUpdate={true}
                            theme={"theme_name"}
                        />
                    </div>
                </div>
            </Spin>
        )
    }
}
export { JBSJA } 