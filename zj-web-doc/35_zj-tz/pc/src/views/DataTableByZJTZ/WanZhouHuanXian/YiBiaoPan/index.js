import React, { Component } from "react";
import s from './style.less';
import { Spin } from "antd";
import moment from 'moment';
import ReactEcharts from 'echarts-for-react';
import * as echarts from 'echarts';
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            firstYear: 0,
            currentYear: 0,
            lastYear: 0,
            clickRouter: props.clickRouter
        }
    }
    componentDidMount() {
        this.kedu();
    }
    kedu() {
        const { projectId } = this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany;
        this.setState({
            loading: true
        })
        this.props.myFetch(this.state.clickRouter === 'YunYing' ? 'getOperatePageCurrentPeriod' : 'getHgPageCurrentPeriod', {
            projectId: projectId
        }).then(
            ({ success, message, data }) => {
                if (success) {
                    this.setState({
                        loading: false,
                        firstYear: data ? data.firstYear : 0,
                        currentYear: data ? data.currentYear : 0,
                        lastYear: data ? data.lastYear : 0
                    })
                } else {
                    this.setState({
                        loading: false,
                    })
                }
            }
        );
    }
    getOptionOne = () => {
        const { firstYear, currentYear, lastYear } = this.state;
        let nqqColor = [];
        if ((firstYear / (lastYear ? lastYear : 1)) <= 0.33) {
            nqqColor = [
                {
                    offset: 0,
                    color: '#49ce05f2',
                }, {
                    offset: 1,
                    color: '#d6c606f2',
                }
            ]
        } else if (0.33 < (firstYear / (lastYear ? lastYear : 1)) && (firstYear / (lastYear ? lastYear : 1)) <= 0.66) {
            nqqColor = [
                {
                    offset: 0,
                    color: '#49ce05f2',
                }, {
                    offset: 0.7,
                    color: '#d6c606f2',
                }, {
                    offset: 1,
                    color: '#d64906f2',
                }
            ]
        } else if ((firstYear / (lastYear ? lastYear : 1)) > 0.66) {
            nqqColor = [
                {
                    offset: 0,
                    color: '#49ce05f2',
                }, {
                    offset: 0.5,
                    color: '#d6c606f2',
                }, {
                    offset: 0.7,
                    color: '#d64906f2',
                },
                {
                    offset: 1,
                    color: '#f90505f2',
                }
            ]
        }
        let nqColor = [
            [(firstYear / (lastYear ? lastYear : 1)), new echarts.graphic.LinearGradient(
                0, 0, 1, 0, nqqColor)], [1, "#2c363880"]
        ];
        const option = {
            tooltip: {
                show: false
            },
            series: [
                {
                    name: "刻度文字",
                    type: "gauge",
                    radius: '120%',
                    center: ["50%", "85%"],
                    startAngle: 180,
                    endAngle: 0,
                    z: 7,
                    splitNumber: 1,
                    min: firstYear || 0,
                    max: lastYear || 30,
                    axisTick: {
                        show: true,
                        lineStyle: {
                            color: "rgba(255, 255, 255, 0.5)",
                            width: 1
                        },
                        length: -5,
                        splitNumber: 30
                    },
                    splitLine: {
                        show: false
                    },
                    axisLine: {
                        show: false
                    },
                    axisLabel: {
                        distance: -100,
                        fontSize: 16,
                        color: 'rgba(255, 255, 255, 0.5)',
                        formatter: (params) => {
                            return `第 ${params} 年`;
                        }
                    },
                    detail: {
                        show: true,
                        offsetCenter: [0, "-1%"],
                        textStyle: {
                            fontSize: 16,
                            color: "rgba(255, 255, 255, 0.5)"
                        },
                        formatter: () => {
                            return '当前运营时期';
                        }
                    },
                    pointer: {
                        show: false,
                    },
                },
                {
                    name: "内层盘",
                    type: "gauge",
                    z: 6,
                    radius: '120%',
                    startAngle: 180,
                    endAngle: 0,
                    center: ["50%", "85%"],
                    axisLine: {
                        lineStyle: {
                            color: nqColor,
                            width: 20
                        }
                    },
                    splitNumber: 1,
                    min: firstYear || 0,
                    max: lastYear || 30,
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        show: false
                    },
                    pointer: {
                        show: false
                    },
                    detail: {
                        show: true,
                        offsetCenter: [0, "-30%"],
                        textStyle: {
                            fontSize: 25,
                            color: (currentYear || 0) > (lastYear || 0) ? "#ff1818" : "#1890ff"
                        },
                        formatter: (params) => {
                            return (currentYear || 0) > (lastYear || 0) ? '运营期结束' : `第 ${params} 年`;
                        }
                    },
                    data: [currentYear || 0]
                }
            ]
        };
        return option
    }
    render() {
        return (
            <div className={s.PIEtWO}>
                <div className={s.leftBottom}>
                    <Spin spinning={this.state.loading}>
                        <ReactEcharts
                            style={{ height: "100%" }}
                            option={this.getOptionOne()}
                            notMerge={true}
                            lazyUpdate={true}
                            theme={"theme_name"}
                        />
                    </Spin>

                </div>
            </div>
        );
    }
}

export default index;