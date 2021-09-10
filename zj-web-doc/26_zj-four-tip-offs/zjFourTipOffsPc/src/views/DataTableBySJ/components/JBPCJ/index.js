import React, { Component } from 'react'
import { Spin, message as Msg } from 'antd';
import ReactEcharts from 'echarts-for-react';
import s from './style.less'

class JBPCJ extends Component {
    state = {
        loading: true,
        aqH: (window.innerHeight - 172 - ((window.innerHeight - 172) / 2) - 27) * 0.65,
        startDate: this.props.startDate,
        endDate: this.props.endDate,
        data: []
    }
    componentDidMount() {
        window.addEventListener('resize', this.autoSize, false);
        this.refresh();
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
        myFetch('getReportCategoryFrequency', { type:'1', startDate, endDate }).then(({ data, success, message }) => {
            if (success) {
                this.setState({
                    data: data,
                    loading: false
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
                data: data && data.length ? data.map((item) => {
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
                    data: data && data.length ? data.map((item) => {
                        return item.reportNum
                    }) : [],
                    itemStyle: { normal: { label: { show: true } } }
                },
            ]
        };
        return option
    }
    render() {
        const { aqH, loading } = this.state;
        return (
            <div className={s.root}>
                <div className={s.con}>
                    <div className={s.l}>
                        <div className={s.title}>举报频次数据</div>
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
                </div>
            </div>
        )
    }
}
export { JBPCJ }