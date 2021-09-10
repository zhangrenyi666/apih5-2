import React from "react";
import { Spin } from "antd";
import s from "./style.less";
import ReactEcharts from 'echarts-for-react';
import Apih5 from 'qnn-apih5';
class Readme extends Apih5 {
    state = {
        loading: true,
        //质量安全
        data: [
            {
                label: "质量",
                data1: 22,
                data2: 22,
                count: 44,
            },
            {
                label: "安全",
                data1: 22,
                data2: 22,
                count: 44,
            },
        ],
    };
    componentDidMount() {
        this.isDidMount = true;
        if (this.props.isNeedGetData) {
            this.refresh();
        } else {
            this.setState({
                loading: false,
                // data: `测试数据`,
            })
        }
    }
    componentWillUnmount() {
        this.isDidMount = false;
    }
    refresh = async () => {
        const { myFetch, errMsg } = this.props;
        const { data, success, message, code } = await myFetch("getZjProZcQsListForBigScreen", {});
        if (success) {
            //处理数据data
            this.isDidMount && this.setState({
                data: data.map(item => {
                    return {
                        ...item,
                        label: item.problemNature,
                        data1: item.finish,
                        data2: item.unFinish,
                        count: item.finish + item.unFinish,
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
            color: ['rgb(2, 143, 194)', 'rgb(118,175,62)'],
            // tooltip: {
            //     trigger: 'axis',
            //     axisPointer: {
            //         type: 'shadow'
            //     }
            // },
            legend: {
                right: -40,
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
                left: '0%',
                right: '0%',
                top: '25%',
                bottom: '3%',
                containLabel: true,
            },
            xAxis: [
                {
                    type: 'category',
                    data: data.map(item => item.label),
                    axisLabel: {
                        color: "white",
                        fontSize: 12,
                    },

                    axisLine:{
                        show:true,
                        lineStyle: {
                            color: 'rgba(151, 151, 151, 0.48)' 
                        }
                    },
                }
            ],
            yAxis: [
                {
                    type: 'value',
                    splitLine: {
                        show: true,
                        lineStyle: {
                            color: 'rgba(151, 151, 151, 0.48)'
                        }
                    },
                    axisLine:{
                        show:true,
                        lineStyle: {
                            color: 'rgba(151, 151, 151, 0.48)' 
                        }
                    },
                    axisTick: {
                        color: "white",
                        show: true,

                    },
                    axisLabel: {
                        color: "white",
                        fontSize: 12,
                        // 使用函数模板，函数参数分别为刻度数值（类目），刻度的索引
                        formatter: function (value, index) {
                            return `${index * 25}%`;
                        }
                    },
                    max: 100, //需要比100多一点用于放置总数
                    interval: 25,
                    splitNumber: 4
                }
            ],
            series: [
                {
                    name: '已解决',
                    type: 'bar',
                    stack: 'T梁', 
                    data: data.map(item => item.data1 / item.count * 100),
                    label: {
                        show: true,
                        position: 'inside',
                        formatter: ({ dataIndex }) => {
                            return data[dataIndex].data1
                        }
                    },
                    barWidth: 45,
                    zlevel: ((() => {
                        let _data = data.filter(item => item.data1 < 35);
                        return _data.length ? 3 : 1
                    })())
                },
                {
                    name: '未解决',
                    type: 'bar',
                    stack: 'T梁',
                    data: data.map(item => item.data2 / item.count * 100), 
                    label: {
                        show: true,
                        position: 'inside',
                        formatter: ({ dataIndex }) => {
                            return data[dataIndex].data2
                        }
                    },
                    barWidth: 45,
                    zlevel: ((() => {
                        let _data = data.filter(item => item.data1 < 35);
                        return _data.length ? 3 : 1
                    })())
                },
                //这个这是上面的总和
                {
                    name: '总数',
                    type: 'bar',
                    stack: 'T梁',
                    data: data.map(item => 0),
                    itemStyle: {
                        normal: {
                            color: "transparent"
                        }
                    },
                    label: {
                        normal: {
                            show: true,
                            offset: [0, -10],
                            formatter: ({ dataIndex }) => data[dataIndex].count
                        }
                    },
                    barWidth: 49,
                    zlevel: 3
                },
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
                        质量安全问题统计
                    </div>
                    <div className={s.con} onClick={() => {
                        this.props.openWindow('DataTableDetailByQS')
                    }}>
                        <ReactEcharts
                            style={{ width: '100%', height: "100%" }}
                            option={this.getOptionOne(data)}
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
