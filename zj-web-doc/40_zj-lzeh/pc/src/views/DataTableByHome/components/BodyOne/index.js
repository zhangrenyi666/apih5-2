import React from "react";
import { Spin } from "antd";
import s from "./style.less";
import ReactEcharts from 'echarts-for-react';
import Apih5 from 'qnn-apih5'
class Readme extends Apih5 {
    state = {
        loading: true,
        //铺装  
        data: {
            items: [
                {
                    labels: ["已完成","未完成"],
                    data1: 0,
                    data2: 0,
                    count: 0,
                    desc: "总计：0",
                    title: "合同金额（万元）"
                },
                {
                    labels: ["已完成","未完成"],
                    data1: 0,
                    data2:0,
                    count:0,
                    desc: "总计：0",
                    title: "本年计划（万元）"
                },
                {
                    labels: ["已完成","未完成"],
                    data1: 0,
                    data2: 0,
                    count: 0,
                    desc: "总计：0",
                    title: "本月计划（万元）"
                }
            ]
        },
    };
    componentDidMount() {
        if (this.props.isNeedGetData) {
            this.refresh();
        } else {
            this.setState({
                loading: false,
            })
        }
    }
    refresh = async () => {
        const { myFetch,errMsg } = this.props;
        const { data,success,message,code } = await myFetch("getZjLzehProductionList",{});

        if (success) {
            const {
                totalContractAmount,accumulatedCompletionAmount,
                currentYearSchemeAmount,currentYearCompletionAmount,
                currentMonthSchemeAmount,currentMonthCompletionAmount

            } = data[0];
            //处理数据data
            this.setState({
                data: {
                    items: [
                        {
                            labels: ["已完成","未完成"],
                            data1: accumulatedCompletionAmount,
                            data2: (totalContractAmount - accumulatedCompletionAmount).toFixed(2),
                            count: totalContractAmount,
                            desc: "总计：" + totalContractAmount,
                            title: "合同金额（万元）"
                        },
                        {
                            labels: ["已完成","未完成"],
                            data1: currentYearCompletionAmount,
                            data2: (currentYearSchemeAmount - currentYearCompletionAmount).toFixed(2),
                            count: currentYearSchemeAmount,
                            desc: "总计：" + currentYearSchemeAmount,
                            title: "本年计划（万元）"
                        },
                        {
                            labels: ["已完成","未完成"],
                            data1: currentMonthCompletionAmount,
                            data2: (currentMonthSchemeAmount - currentMonthCompletionAmount).toFixed(2),
                            count: currentMonthSchemeAmount,
                            desc: "总计：" + currentMonthSchemeAmount,
                            title: "本月计划（万元）"
                        }
                    ]
                },
                loading: false
            });
        } else {
            errMsg(message,code);
        }
    };

    //获取圆环图 的配置
    getOption = (data) => {
        return {
            color: ['#0f6fc6','#f49100'],
            title: {
                text: data.desc,
                left: 'center',
                bottom: 5,
                textStyle: {
                    fontSize: 10,
                    color: "white"
                }
            },
            legend: {
                right: 1,
                top: 5,
                data: data.legendData,
                itemGap: 5,
                itemWidth: 10,
                itemHeight: 8,
                textStyle: {
                    fontSize: 10,
                    color: 'white'
                },
                orient: 'vertical',
                selected: data.labels?.map?.(item => {
                    return {
                        name: item,
                        // 强制设置图形为圆。
                        icon: 'circle',
                    }

                }),
            },
            grid: {
                left: '0%',
                right: '0%',
                top: '20%',
                bottom: '0%',
                containLabel: false
            },
            series: [
                {
                    type: 'pie',
                    radius: ['40%','60%'],
                    avoidLabelOverlap: false,
                    label: {
                        fontSize: '12',
                        position: "inside",
                        formatter: "{c}"
                    },
                    emphasis: {
                        label: {
                            show: true,
                            fontSize: '12',
                        }
                    },
                    labelLine: {
                        show: false
                    },
                    data: [
                        { value: data.data1,name: data.labels?.[0] },
                        { value: data.data2,name: data.labels?.[1] },
                    ]
                },
                {
                    type: 'pie',
                    radius: ['0%','0%'],
                    avoidLabelOverlap: false,
                    hoverAnimation: false,
                    label: {
                        fontSize: '18',
                        position: "center",
                        formatter: `${(data.data1 / data.count * 100).toFixed(1)}%`,
                        color: "white"
                    },
                    emphasis: {
                        label: {
                            show: false,
                        }
                    },
                    labelLine: {
                        show: false
                    },
                    data: [
                        { value: data.data1,name: data.labels?.[0] },
                    ]
                }
            ]
        }

    }

    render() {
        const {
            loading,
            data: { items = [] },
        } = this.state;
        return (
            <Spin spinning={loading} wrapperClassName={s.loading}>
                <div className={s.info}>
                    <div className={s.title}>
                        产值完成情况
                    </div>
                    <div className={s.con}>
                        {
                            items.map((item,index) => {
                                return <div className={s.dataItem} key={index}>
                                    <div className={s.itemTit}>{item.title}</div>
                                    <ReactEcharts
                                        style={{ width: '100%',height: "100%" }}
                                        // option={this.getOption(item)}
                                        option={{}}
                                        ref={(me) => {
                                            if (me) {
                                                setTimeout(() => {
                                                    let ech = me.getEchartsInstance();
                                                    ech && ech.setOption && ech.setOption(this.getOption(item));
                                                },300)
                                            }
                                        }}
                                        notMerge={true}
                                        lazyUpdate={true}
                                        theme={"theme_name"}
                                    />
                                </div>
                            })
                        }

                    </div>
                </div>
            </Spin>
        );
    }
}
export default Readme;
