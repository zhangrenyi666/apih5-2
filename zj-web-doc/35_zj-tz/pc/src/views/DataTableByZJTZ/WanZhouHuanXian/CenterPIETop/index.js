import React, { Component } from "react";
import s from './style.less';
import ReactEcharts from 'echarts-for-react';
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            type: props.type || '',
            pieMiddleData: props.pieMiddleData || []
        }
    }
    getOptionOne = () => {
        const { pieMiddleData, type } = this.state;
        var optionJianShe = {
            color: ['rgba(0,149,255,1)', 'rgba(230,189,69,1)', 'rgba(0,255,234,1)', 'rgba(253,52,86,1)'],
            tooltip: {
                trigger: 'item',
                formatter: '{b} : {c} ({d}%)'
            },
            title: [
                {
                    text: '单位：万元',
                    x: 'right',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: '500'
                    },
                },
                {
                    text: pieMiddleData?.[0]?.[0]?.name || '',
                    top: "2%",
                    left: "15%",
                    textAlign: "center",
                    textStyle: {
                        color: "rgba(255,255,255,0.5)",
                        fontSize: 14,
                        fontWeight: 'normal',
                        textAlign: "center"
                    }
                },
                {
                    text: pieMiddleData?.[2]?.[0]?.name || '',
                    top: "2%",
                    left: "40%",
                    textAlign: "center",
                    textStyle: {
                        color: "rgba(255,255,255,0.5)",
                        fontSize: 14,
                        fontWeight: 'normal',
                        textAlign: "center"
                    }
                },
                {
                    text: pieMiddleData?.[4]?.[0]?.name || '',
                    top: "2%",
                    left: "65%",
                    textAlign: "center",
                    textStyle: {
                        color: "rgba(255,255,255,0.5)",
                        fontSize: 14,
                        fontWeight: 'normal',
                        textAlign: "center"
                    }
                },
                {
                    text: `总计：${pieMiddleData?.[0]?.[0]?.value || 0}`,
                    bottom: "2%",
                    left: "15%",
                    textAlign: "center",
                    textStyle: {
                        color: "white",
                        fontSize: 14,
                        fontWeight: 'normal',
                        textAlign: "center"
                    }
                },
                {
                    text: `总计：${pieMiddleData?.[2]?.[0]?.value || 0}`,
                    bottom: "2%",
                    left: "40%",
                    textAlign: "center",
                    textStyle: {
                        color: "white",
                        fontSize: 14,
                        fontWeight: 'normal',
                        textAlign: "center"
                    }
                },
                {
                    text: `总计：${pieMiddleData?.[4]?.[0]?.value || 0}`,
                    bottom: "2%",
                    left: "65%",
                    textAlign: "center",
                    textStyle: {
                        color: "white",
                        fontSize: 14,
                        fontWeight: 'normal',
                        textAlign: "center"
                    }
                },
            ],
            legend: {
                selectedMode: false,
                icon: 'circle',
                orient: "vertical",
                x: 'right',
                y: 'center',
                textStyle: { color: 'rgba(255,255,255,0.5)' },
                itemWidth: 10,
                itemHeight: 10,
                data: [
                    '未完成合同金额', '累计完成金额', '本年完成金额', '本季完成金额'
                ]
            },
            graphic: [{
                type: "text",
                top: "45%",
                left: "11%",
                style: {
                    text: `${((pieMiddleData?.[0]?.[1]?.value || 0) / (pieMiddleData?.[0]?.[0]?.value || 1) * 100).toFixed(2)}%`,
                    fontSize: 22,
                    fontWeight: 'bolder',
                    textAlign: "center",
                    fill: "rgba(230,189,69,1)"
                }
            }, {
                type: "text",
                top: "45%",
                left: "36%",
                style: {
                    text: `${((pieMiddleData?.[2]?.[1]?.value || 0) / (pieMiddleData?.[2]?.[0]?.value || 1) * 100).toFixed(2)}%`,
                    fontSize: 22,
                    fontWeight: 'bolder',
                    textAlign: "center",
                    fill: "rgba(0,255,234,1)"
                }
            }, {
                type: "text",
                top: "45%",
                left: "61%",
                style: {
                    text: `${((pieMiddleData?.[4]?.[1]?.value || 0) / (pieMiddleData?.[4]?.[0]?.value || 1) * 100).toFixed(2)}%`,
                    fontSize: 22,
                    fontWeight: 'bolder',
                    textAlign: "center",
                    fill: "rgba(253,52,86,1)",
                }
            }],
            series: [
                {
                    type: 'pie',
                    center: ['15%', '50%'],
                    radius: [50, 80],
                    startAngle: 90, //起始角度
                    label: {
                        show: true,
                        position: 'outer',
                        formatter: '{d}%',
                        fontSize: 14,
                        fontWeight: 'bold'
                    },
                    labelLine: {
                        show: false,
                        normal: {
                            length: 2,
                            length2: 2,
                            lineStyle: {
                                width: 0
                            }
                        }
                    },
                    data: [
                        {
                            name: '累计完成金额',
                            value: pieMiddleData?.[0]?.[1]?.value || 0,
                            itemStyle: {
                                normal: {
                                    color: 'rgba(230,189,69,1)',
                                }
                            }
                        },
                        {
                            name: '未完成合同金额',
                            value: pieMiddleData?.[0]?.[2]?.value || 0,
                            itemStyle: {
                                normal: {
                                    color: 'rgba(0,149,255,0.5)',
                                }
                            }
                        }
                    ]
                },
                {
                    type: 'pie',
                    center: ['40%', '50%'],
                    radius: [50, 80],
                    label: {
                        show: true,
                        position: 'outer',
                        formatter: '{d}%',
                        fontSize: 14,
                        fontWeight: 'bold'
                    },
                    labelLine: {
                        show: false,
                        normal: {
                            length: 2,
                            length2: 2,
                            lineStyle: {
                                width: 0
                            }
                        }
                    },
                    data: [
                        {
                            name: '本年完成金额',
                            value: pieMiddleData?.[2]?.[1]?.value || 0,
                            itemStyle: {
                                normal: {
                                    color: 'rgba(0,255,234,1)',
                                }
                            }
                        },
                        {
                            name: '未完成合同金额',
                            value: pieMiddleData?.[2]?.[2]?.value || 0,
                            itemStyle: {
                                normal: {
                                    color: 'rgba(0,149,255,0.5)',
                                }
                            }
                        }
                    ]
                },
                {
                    type: 'pie',
                    center: ['65%', '50%'],
                    radius: [50, 80],
                    label: {
                        show: true,
                        position: 'outer',
                        formatter: '{d}%',
                        fontSize: 14,
                        fontWeight: 'bold'
                    },
                    labelLine: {
                        show: false,
                        normal: {
                            length: 2,
                            length2: 2,
                            lineStyle: {
                                width: 0
                            }
                        }
                    },
                    data: [
                        {
                            name: '本季完成金额',
                            value: pieMiddleData?.[4]?.[1]?.value || 0,
                            itemStyle: {
                                normal: {
                                    color: 'rgba(253,52,86,1)',
                                }
                            }
                        },
                        {
                            name: '未完成合同金额',
                            value: pieMiddleData?.[4]?.[2]?.value || 0,
                            itemStyle: {
                                normal: {
                                    color: 'rgba(0,149,255,0.5)',
                                }
                            }
                        }
                    ]
                }
            ]
        };
        var optionYunYing = {
            color: ['rgba(0,149,255,1)', 'rgba(230,189,69,1)', 'rgba(0,255,234,1)', 'rgba(253,52,86,1)'],
            tooltip: {
                trigger: 'item',
                formatter: '{b} : {c} ({d}%)'
            },
            title: [
                {
                    text: '单位：万元',
                    x: 'right',
                    textStyle: {
                        color: 'rgba(255,255,255,0.5)',
                        fontSize: 16,
                        fontWeight: '500'
                    },
                },
                {
                    text: "累计投评收入\n与实际收入情况",
                    top: "2%",
                    left: "15%",
                    textAlign: "center",
                    textStyle: {
                        color: "rgba(255,255,255,0.5)",
                        fontSize: 14,
                        fontWeight: 'normal',
                        textAlign: "center"
                    }
                },
                {
                    text: "本年投评收入\n与实际收入情况",
                    top: "2%",
                    left: "40%",
                    textAlign: "center",
                    textStyle: {
                        color: "rgba(255,255,255,0.5)",
                        fontSize: 14,
                        fontWeight: 'normal',
                        textAlign: "center"
                    }
                },
                {
                    text: "本月投评收入\n与实际收入情况",
                    top: "2%",
                    left: "65%",
                    textAlign: "center",
                    textStyle: {
                        color: "rgba(255,255,255,0.5)",
                        fontSize: 14,
                        fontWeight: 'normal',
                        textAlign: "center"
                    }
                },
                {
                    text: `总计：${pieMiddleData?.[2]?.[0]?.value || 0}`,
                    bottom: "2%",
                    left: "15%",
                    textAlign: "center",
                    textStyle: {
                        color: "white",
                        fontSize: 14,
                        fontWeight: 'normal',
                        textAlign: "center"
                    }
                },
                {
                    text: `总计：${pieMiddleData?.[3]?.[0]?.value || 0}`,
                    bottom: "2%",
                    left: "40%",
                    textAlign: "center",
                    textStyle: {
                        color: "white",
                        fontSize: 14,
                        fontWeight: 'normal',
                        textAlign: "center"
                    }
                },
                {
                    text: `总计：${pieMiddleData?.[4]?.[0]?.value || 0}`,
                    bottom: "2%",
                    left: "65%",
                    textAlign: "center",
                    textStyle: {
                        color: "white",
                        fontSize: 14,
                        fontWeight: 'normal',
                        textAlign: "center"
                    }
                },
            ],
            legend: {
                selectedMode: false,
                icon: 'circle',
                orient: "vertical",
                x: 'right',
                y: 'center',
                textStyle: { color: 'rgba(255,255,255,0.5)' },
                itemWidth: 10,
                itemHeight: 10,
                data: [
                    '未完成投评金额', '累计营业收入', '本年营业收入', '本月营业收入'
                ]
            },
            graphic: [{
                type: "text",
                top: "45%",
                left: "11%",
                style: {
                    text: `${((pieMiddleData?.[2]?.[1]?.value || 0) / (pieMiddleData?.[2]?.[0]?.value || 1) * 100).toFixed(2)}%`,
                    fontSize: 22,
                    fontWeight: 'bolder',
                    textAlign: "center",
                    fill: "rgba(230,189,69,1)"
                }
            }, {
                type: "text",
                top: "45%",
                left: "36%",
                style: {
                    text: `${((pieMiddleData?.[3]?.[1]?.value || 0) / (pieMiddleData?.[3]?.[0]?.value || 1) * 100).toFixed(2)}%`,
                    fontSize: 22,
                    fontWeight: 'bolder',
                    textAlign: "center",
                    fill: "rgba(0,255,234,1)"
                }
            }, {
                type: "text",
                top: "45%",
                left: "61%",
                style: {
                    text: `${((pieMiddleData?.[4]?.[1]?.value || 0) / (pieMiddleData?.[4]?.[0]?.value || 1) * 100).toFixed(2)}%`,
                    fontSize: 22,
                    fontWeight: 'bolder',
                    textAlign: "center",
                    fill: "rgba(253,52,86,1)",
                }
            }],
            series: [
                {
                    type: 'pie',
                    center: ['15%', '50%'],
                    radius: [50, 80],
                    startAngle: 90, //起始角度
                    label: {
                        show: true,
                        position: 'outer',
                        formatter: '{d}%',
                        fontSize: 14,
                        fontWeight: 'bold'
                    },
                    labelLine: {
                        show: false,
                        normal: {
                            length: 2,
                            length2: 2,
                            lineStyle: {
                                width: 0
                            }
                        }
                    },
                    data: [
                        {
                            name: '累计营业收入',
                            value: pieMiddleData?.[2]?.[1]?.value || 0,
                            itemStyle: {
                                normal: {
                                    color: 'rgba(230,189,69,1)',
                                }
                            }
                        },
                        {
                            name: '未完成投评金额',
                            value: pieMiddleData?.[2]?.[2]?.value || 0,
                            itemStyle: {
                                normal: {
                                    color: 'rgba(0,149,255,0.5)',
                                }
                            }
                        }
                    ]
                },
                {
                    type: 'pie',
                    center: ['40%', '50%'],
                    radius: [50, 80],
                    label: {
                        show: true,
                        position: 'outer',
                        formatter: '{d}%',
                        fontSize: 14,
                        fontWeight: 'bold'
                    },
                    labelLine: {
                        show: false,
                        normal: {
                            length: 2,
                            length2: 2,
                            lineStyle: {
                                width: 0
                            }
                        }
                    },
                    data: [
                        {
                            name: '本年营业收入',
                            value: pieMiddleData?.[3]?.[1]?.value || 0,
                            itemStyle: {
                                normal: {
                                    color: 'rgba(0,255,234,1)',
                                }
                            }
                        },
                        {
                            name: '未完成投评金额',
                            value: pieMiddleData?.[3]?.[2]?.value || 0,
                            itemStyle: {
                                normal: {
                                    color: 'rgba(0,149,255,0.5)',
                                }
                            }
                        }
                    ]
                },
                {
                    type: 'pie',
                    center: ['65%', '50%'],
                    radius: [50, 80],
                    label: {
                        show: true,
                        position: 'outer',
                        formatter: '{d}%',
                        fontSize: 14,
                        fontWeight: 'bold'
                    },
                    labelLine: {
                        show: false,
                        normal: {
                            length: 2,
                            length2: 2,
                            lineStyle: {
                                width: 0
                            }
                        }
                    },
                    data: [
                        {
                            name: '本月营业收入',
                            value: pieMiddleData?.[4]?.[1]?.value || 0,
                            itemStyle: {
                                normal: {
                                    color: 'rgba(253,52,86,1)',
                                }
                            }
                        },
                        {
                            name: '未完成投评金额',
                            value: pieMiddleData?.[4]?.[2]?.value || 0,
                            itemStyle: {
                                normal: {
                                    color: 'rgba(0,149,255,0.5)',
                                }
                            }
                        }
                    ]
                }
            ]
        };
        var optionHuiGou = {
            color: ['rgba(0,149,255,1)', 'rgba(230,189,69,1)', 'rgba(0,255,234,1)', 'rgba(253,52,86,1)'],
            tooltip: {
                trigger: 'item',
                formatter: '{b} : {c} ({d}%)'
            },
            title: [
                {
                    text: "累计回购情况",
                    top: "2%",
                    left: "15%",
                    textAlign: "center",
                    textStyle: {
                        color: "rgba(255,255,255,0.5)",
                        fontSize: 14,
                        fontWeight: 'normal',
                        textAlign: "center"
                    }
                },
                {
                    text: "本年回购情况",
                    top: "2%",
                    left: "63%",
                    textAlign: "center",
                    textStyle: {
                        color: "rgba(255,255,255,0.5)",
                        fontSize: 14,
                        fontWeight: 'normal',
                        textAlign: "center"
                    }
                },
                {
                    text: `总计：${pieMiddleData?.[2]?.[0]?.value || 0}`,
                    bottom: "2%",
                    left: "15%",
                    textAlign: "center",
                    textStyle: {
                        color: "white",
                        fontSize: 14,
                        fontWeight: 'normal',
                        textAlign: "center"
                    }
                },
                {
                    text: `总计：${pieMiddleData?.[3]?.[0]?.value || 0}`,
                    bottom: "2%",
                    left: "63%",
                    textAlign: "center",
                    textStyle: {
                        color: "white",
                        fontSize: 14,
                        fontWeight: 'normal',
                        textAlign: "center"
                    }
                },
            ],
            legend: {
                selectedMode: false,
                icon: 'circle',
                orient: "vertical",
                x: 'right',
                y: 'center',
                textStyle: { color: 'rgba(255,255,255,0.5)' },
                itemWidth: 10,
                itemHeight: 10,
                data: [
                    '未完成回购金额', '累计回购金额', '本年回购金额'
                ]
            },
            graphic: [{
                type: "text",
                top: "45%",
                left: "11%",
                style: {
                    text: `${((pieMiddleData?.[2]?.[1]?.value || 0) / (pieMiddleData?.[2]?.[0]?.value || 1) * 100).toFixed(2)}%`,
                    fontSize: 22,
                    fontWeight: 'bolder',
                    textAlign: "center",
                    fill: "rgba(230,189,69,1)"
                }
            }, {
                type: "text",
                top: "45%",
                left: "59%",
                style: {
                    text: `${((pieMiddleData?.[3]?.[1]?.value || 0) / (pieMiddleData?.[3]?.[0]?.value || 1) * 100).toFixed(2)}%`,
                    fontSize: 22,
                    fontWeight: 'bolder',
                    textAlign: "center",
                    fill: "rgba(0,255,234,1)"
                }
            }],
            series: [
                {
                    type: 'pie',
                    center: ['15%', '50%'],
                    radius: [50, 80],
                    startAngle: 90, //起始角度
                    label: {
                        show: true,
                        position: 'outer',
                        formatter: '{d}%',
                        fontSize: 14,
                        fontWeight: 'bold'
                    },
                    labelLine: {
                        show: false,
                        normal: {
                            length: 2,
                            length2: 2,
                            lineStyle: {
                                width: 0
                            }
                        }
                    },
                    data: [
                        {
                            name: '累计回购金额',
                            value: pieMiddleData?.[2]?.[1]?.value || 0,
                            itemStyle: {
                                normal: {
                                    color: 'rgba(230,189,69,1)',
                                }
                            }
                        },
                        {
                            name: '未完成回购金额',
                            value: pieMiddleData?.[2]?.[2]?.value || 0,
                            itemStyle: {
                                normal: {
                                    color: 'rgba(0,149,255,0.5)',
                                }
                            }
                        }
                    ]
                },
                {
                    type: 'pie',
                    center: ['63%', '50%'],
                    radius: [50, 80],
                    label: {
                        show: true,
                        position: 'outer',
                        formatter: '{d}%',
                        fontSize: 14,
                        fontWeight: 'bold'
                    },
                    labelLine: {
                        show: false,
                        normal: {
                            length: 2,
                            length2: 2,
                            lineStyle: {
                                width: 0
                            }
                        }
                    },
                    data: [
                        {
                            name: '本年回购金额',
                            value: pieMiddleData?.[3]?.[1]?.value || 0,
                            itemStyle: {
                                normal: {
                                    color: 'rgba(0,255,234,1)',
                                }
                            }
                        },
                        {
                            name: '未完成回购金额',
                            value: pieMiddleData?.[3]?.[2]?.value || 0,
                            itemStyle: {
                                normal: {
                                    color: 'rgba(0,149,255,0.5)',
                                }
                            }
                        }
                    ]
                }
            ]
        };
        if(type === 'jianshe'){
            return optionJianShe; 
        }else if(type === 'yunying'){
            return optionYunYing;
        }else if(type === 'huigou'){
            return optionHuiGou;
        }else{
            return {};
        }
    }
    render() {
        return (
            <div className={s.CenterPIETop}>
                <ReactEcharts
                    style={{ height: "100%" }}
                    option={this.getOptionOne()}
                    notMerge={true}
                    lazyUpdate={true}
                    theme={"theme_name"}
                />
            </div>
        );
    }
}

export default index;