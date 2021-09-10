import React, { Component } from "react";
import { Spin } from "antd";
import s from "./style.less";
import ReactEcharts from 'echarts-for-react';

import Apih5 from "qnn-apih5"
class Readme extends Apih5 {
    state = {
        loading: true,
        //质量安全
        data: [
            // {
            //     label: "紧要",
            //     data1: 32,
            //     data2: 92,
            //     count: 124,
            // },
            // {
            //     label: "严重",
            //     data1: 22,
            //     data2: 22,
            //     count: 44,
            // },
            // {
            //     label: "一般",
            //     data1: 22,
            //     data2: 22,
            //     count: 44,
            // },
        ],
    };
    componentDidMount() {
        if (this.props.isNeedGetData) {
            this.refresh();
        } else {
            this.setState({
                loading: false,
                // data: `测试数据`,
            })
        }
    }
    refresh = async () => {
        const { myFetch, errMsg } = this.props;
        const { data, success, message, code } = await myFetch("getZjLzehSafetyInspectionManagementBySumTroubleLevel", {});
        if (success) {
            //处理数据data
            this.setState({
                data: data.map(item => {
                    return {
                        ...item,
                        label: item.troublelevel,
                        data1: item.solved,
                        data2: item.unsolved,
                        count: item.solved + item.unsolved,
                    }
                }),
                loading: false
            });
        } else {
            errMsg(message, code);
        }
    };


    //获取柱状图 的配置
    getOptionOne = (data) => {
        return {
            color: ['#0f6fc6', '#f49100'],
            // tooltip: {
            //     trigger: 'axis',
            //     axisPointer: {
            //         type: 'shadow'
            //     }
            // },
            legend: {
                right: -0,
                top: -5,
                itemGap: 5,
                itemWidth: 10,
                itemHeight: 8,
                textStyle: {
                    fontSize: 10,
                    color: 'white'
                },
                // selected: data.labels.map(item => {
                //     return {
                //         name: item,
                //         // 强制设置图形为圆。
                //         icon: 'circle',
                //     }

                // }),
            },
            grid: {
                left: '10%',
                right: '0%',
                top: '20%',
                bottom: '15%',
                containLabel: false
            },
            xAxis: [
                {
                    type: 'category',
                    data: data.map(item => item.label),
                    axisLabel: {
                        color: "white",
                        fontSize: 12,
                        interval: 0,
                        // formatter: function (value) {
                        //     return value.split("").join("\n");
                        // }
                    },s
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    splitLine: {
                        show: true,
                        lineStyle: {
                            color: 'rgba(57, 62, 99, 0.48)'
                        }
                    },
                    axisTick: {
                        show: true,
                    },
                    axisLabel: {
                        color: "white",
                        fontSize: 9,
                    },
                }
            ],
            series: [
                {
                    name: '总计数量',
                    type: 'bar',
                    barGap: 0,
                    // stack: 'T梁',
                    data: data.map(item => item.data1),
                    label: {
                        show: true,
                        position: 'inside'
                    },
                    barWidth: 25
                },
                {
                    name: '整改完成',
                    type: 'bar',
                    // stack: 'T梁',
                    data: data.map(item => item.data2),
                    label: {
                        show: true,
                        position: 'inside'
                    },
                    barWidth: 25
                },
                //这个这是上面的总和
                // {
                //     name: '总数',
                //     type: 'bar',
                //     stack: 'T梁',
                //     data: data.map(item => 0),
                //     itemStyle: {
                //         normal: {
                //             color: "transparent"
                //         }
                //     },
                //     label: {
                //         normal: {
                //             show: true,
                //             offset: [0,-10],
                //             formatter: ({ dataIndex }) => data[dataIndex].count
                //         }
                //     },
                //     barWidth: 30
                // },
            ]
        }
    }

    render() {
        const {
            loading,
            data,
        } = this.state;
        return (
            <Spin spinning={loading} wrapperClassName={s.loading}>
                <div className={s.info}>
                    <div className={s.title}>
                        质量检查数据
                    </div>
                    <div className={s.con}>
                        <ReactEcharts
                            style={{ width: '100%', height: "100%" }}
                            // option={this.getOptionOne(data)}
                            option={{}}
                            ref={(me) => {
                                if (me) {
                                    setTimeout(() => {
                                        let ech = me.getEchartsInstance();
                                        ech && ech.setOption && ech.setOption(this.getOptionOne(data));
                                    }, 300)
                                }
                            }}
                            notMerge={true}
                            lazyUpdate={true}
                            theme={"theme_name"}
                        />
                    </div>
                </div>
            </Spin>
        );
    }
}
export default Readme;
