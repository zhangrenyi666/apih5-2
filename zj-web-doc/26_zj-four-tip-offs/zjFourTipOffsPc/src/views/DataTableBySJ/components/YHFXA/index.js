import React, { Component } from 'react'
import { Spin, message as Msg } from 'antd';
import ReactEcharts from 'echarts-for-react';
import s from './style.less'

class YHFXA extends Component {
    state = {
        loadingt: true,
        loading: true,
        aqH: (window.innerHeight - 172 - ((window.innerHeight - 172) / 2) - 27) * 0.65,
        startDate: this.props.startDate,
        endDate: this.props.endDate,
        data: [],
        datas: []
    }
    componentDidMount() {
        window.addEventListener('resize', this.autoSize, false);
        this.refresh();
        this.refreshs();
    }
    componentWillUnmount() {
        window.removeEventListener('resize', this.autoSize);
    }
    componentWillReceiveProps(nextProps) {
        if (this.state.startDate !== nextProps.startDate || this.state.endDate !== nextProps.endDate) {
            this.setState({
                startDate: nextProps.startDate,
                endDate: nextProps.endDate
            }, () => {
                this.refresh();
                this.refreshs();
            })
        }
    }
    autoSize = () => {
        this.setState({
            aqH: (window.innerHeight - 172 - ((window.innerHeight - 172) / 2) - 27) * 0.65,
        })
    }

    //刷新图表
    refresh = () => {
        this.setState({ loading: true });
        const { myFetch } = this.props;
        const { startDate, endDate } = this.state;
        myFetch('getAqyhReportDataStatistics', { type: '3', startDate, endDate }).then(({ data, success, message }) => {
            if (success) {
                this.setState({
                    data,
                    loading: false
                })
            } else {
                Msg.error(message)
            }
        })
    }
    refreshs = () => {
        this.setState({ loadingt: true });
        const { myFetch } = this.props;
        const { startDate, endDate } = this.state;
        myFetch('getReportCategoryFrequency', { type:'3', startDate, endDate }).then(({ data, success, message }) => {
            if (success) {
                this.setState({
                    datas: data,
                    loadingt: false
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
                top: 5,
                data: ['人的不安全行为', '物的不安全状态', '管理上的缺陷', '其他'],
                x: 'center',
                textStyle: {
                    color: 'white',
                },
            },
            grid: {
                left: 5,
                bottom: 15,
                top: 35,
                right: 0,
                containLabel: true,
            },
            xAxis: {
                type: 'category',
                data: ['隐患分析类别'],
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
                    name: '人的不安全行为',
                    type: 'bar',
                    stack: 'aq',
                    data: data && data[0] ? [Number(data[0].hiddenDangerAnalysisTypeTotalNum)] : [],
                    itemStyle: {
                        color: '#1890ff'
                    }
                },
                {
                    name: '物的不安全状态',
                    type: 'bar',
                    stack: 'aqs',
                    data: data && data[1] ? [Number(data[1].hiddenDangerAnalysisTypeTotalNum)] : [],
                    itemStyle: {
                        color: '#10cf9b'
                    }
                },
                {
                    name: '管理上的缺陷',
                    type: 'bar',
                    stack: 'aqw',
                    data: data && data[2] ? [Number(data[2].hiddenDangerAnalysisTypeTotalNum)] : [],
                    itemStyle: {
                        color: '#0c2ede'
                    }
                },
                {
                    name: '其他',
                    type: 'bar',
                    stack: 'aqe',
                    data: data && data[3] ? [Number(data[3].hiddenDangerAnalysisTypeTotalNum)] : [],
                    itemStyle: {
                        color: '#06ef5a'
                    }
                }
            ]
        };
        return option
    }
    getOptions = () => {
        const { datas } = this.state;
        const option = {
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                top: 5,
                data: ['举报频次'],
                x: 'center',
                textStyle: {
                    color: 'white',
                },
            },
            color: ['#f49100'],
            grid: {
                left: 5,
                right: 5,
                bottom: 15,
                top: 35,
                containLabel: true,
            },
            xAxis: {
                type: 'category',
                data: datas && datas.length ? datas.map((item) => {
                    return item.reportDay
                }) : [],
                axisLabel: {
                    interval: 0
                },
                axisLine: {
                    lineStyle: {
                        color: 'white'
                    }
                },
            },
            yAxis: {
                type: 'value',
                axisLine: {
                    lineStyle: {
                        color: 'white'
                    }
                },
            },
            series: [
                {
                    name: '举报频次',
                    type: 'line',
                    stack: 'zl1',
                    data: datas && datas.length ? datas.map((item) => {
                        return item.reportNum
                    }) : [],
                    itemStyle: { normal: { label: { show: true } } }
                },
            ]
        };
        return option
    }
    render() {
        const { aqH, loading, loadingt } = this.state;
        return (
            <div className={s.root}>
                <div className={s.con}>
                    <div className={s.l}>
                        <div className={s.title}>隐患分析类别</div>
                        <div>
                            <Spin spinning={loading}>
                                <ReactEcharts
                                    style={{ height: aqH / 0.8 }}
                                    option={this.getOption()}
                                    notMerge={true}
                                    lazyUpdate={true}
                                    theme={"theme_name"}
                                />
                            </Spin>
                        </div>
                    </div>
                    <div className={s.r}>
                        <div className={s.title}>举报频次数据</div>
                        <Spin spinning={loadingt}>
                            <ReactEcharts
                                style={{ height: aqH / 0.8 }}
                                option={this.getOptions()}
                                notMerge={true}
                                lazyUpdate={true}
                                theme={"theme_name"}
                            />
                        </Spin>
                    </div>
                </div>
            </div>
        )
    }
}
export { YHFXA }