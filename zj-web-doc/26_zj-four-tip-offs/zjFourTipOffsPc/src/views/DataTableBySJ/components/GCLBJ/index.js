import React, { Component } from 'react'
import { Spin, message as Msg } from 'antd';
import s from './style.less'
import ReactEcharts from 'echarts-for-react';
class GCLBJ extends Component {
    state = {
        aqH: (window.innerHeight - 165 - ((window.innerHeight - 165) / 2) - 77) * 0.65,
        loading: false,
        data: [],
        startDate: this.props.startDate,
        endDate: this.props.endDate
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
        this.setState({ loading: true });
        const { myFetch } = this.props;
        const { startDate, endDate } = this.state;
        myFetch('getJszlReportDataStatistics', { type: '0', startDate, endDate }).then(({ data, success, message }) => {
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
    getOption = () => {
        const { data } = this.state;
        const option = {
            color: [
                "orangered",
                "#f49100",
                "#10cf9b",
                "#0bd0d9",
                "#7cca62",
                "#0f6fc6",
                "#f49100"
            ],
            tooltip: {
                trigger: "item",
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            series: [
                {
                    name: '工程类别',
                    type: 'pie',
                    radius: '75%',
                    center: ['50%', '50%'],
                    data: data.map((item) => {
                        return {value:item.enginnerCategoryNum,name:item.engineerCategoryName}
                    }).sort(function (a, b) { return a.value - b.value; }),
                    roseType: 'radius',
                    label: {
                        normal: {
                            formatter: "{b}\n{c}项\n占比{d}%"
                        }
                    },
                    labelLine: {
                        normal: {
                            lineStyle: {
                                color: "rgba(255, 255, 255, 0.3)"
                            },
                            smooth: 0.2,
                            length: 7,
                            length2: 8
                        }
                    },
                    itemStyle: {
                        normal: {
                            // color: '#c23531',
                            shadowBlur: 200,
                            shadowColor: "rgba(0, 0, 0, 0.5)"
                        }
                    },

                    animationType: "scale",
                    animationEasing: "elasticOut",
                    animationDelay: function (idx) {
                        return Math.random() * 200;
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
        const { loading, aqH } = this.state;
        return (
            <Spin spinning={loading}>
                <div className={s.info}>
                    <div className={s.title}>
                        工程类别
                    </div>
                    <div>
                        <ReactEcharts
                            style={{ height: aqH / 0.7 }}
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
export { GCLBJ } 