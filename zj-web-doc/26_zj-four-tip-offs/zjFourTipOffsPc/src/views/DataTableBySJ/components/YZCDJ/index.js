import React, { Component } from 'react'
import { Spin, message as Msg } from 'antd';
import s from './style.less'
import ReactEcharts from 'echarts-for-react';
class YZCDJ extends Component {
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
        myFetch('getJszlReportDataStatistics', { type: '1', startDate, endDate }).then(({ data, success, message }) => {
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
                    name: '严重程度',
                    type: 'pie',
                    radius: '75%',
                    center: ['50%', '50%'],
                    data: data.map((item) => {
                        if(item.problemLevel === '0'){
                            item.problemLevel = '正常';
                        }else if(item.problemLevel === '1'){
                            item.problemLevel = '忽略';
                        }else if(item.problemLevel === '2'){
                            item.problemLevel = '轻微';
                        }else if(item.problemLevel === '3'){
                            item.problemLevel = '严重';
                        }else if(item.problemLevel === '4'){
                            item.problemLevel = '警告';
                        }
                        return {value:item.problemLevelNum,name:item.problemLevel}
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
                        严重程度
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
export { YZCDJ } 