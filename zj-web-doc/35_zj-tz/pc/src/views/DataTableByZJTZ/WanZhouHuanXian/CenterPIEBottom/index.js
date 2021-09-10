import React, { Component } from "react";
import s from './style.less';
import ReactEcharts from 'echarts-for-react';
class index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            type:props.type || '',
            pieMiddleData: props.pieMiddleData || []
        }
    }
    getOptionOne = () => {
        const { pieMiddleData, type } = this.state;
        var optionJianShe = {
            color: ['rgba(0,149,255,1)', 'rgba(0,232,255,1)', 'rgba(204,242,255,1)', 'rgba(2,47,255,1)'],
            tooltip: {
                trigger: 'item',
                formatter: '{b} : {c} ({d}%)'
            },
            title: [
                {
                    text: pieMiddleData?.[1]?.[0]?.name || '',
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
                    text: pieMiddleData?.[3]?.[0]?.name || '',
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
                    text: pieMiddleData?.[5]?.[0]?.name || '',
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
                    text: `总计：${pieMiddleData?.[1]?.[0]?.value || 0}`,
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
                    text: `总计：${pieMiddleData?.[5]?.[0]?.value || 0}`,
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
                    '未完成建安金额', '累计完成建安', '本年完成建安', '本季完成建安'
                ]
            },
            graphic: [{
                type: "text",
                top: "45%",
                left: "11%",
                style: {
                    text: `${((pieMiddleData?.[1]?.[1]?.value || 0) / (pieMiddleData?.[1]?.[0]?.value || 1) * 100).toFixed(2)}%`,
                    fontSize: 22,
                    fontWeight: 'bolder',
                    textAlign: "center",
                    fill: "rgba(0,232,255,1)"
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
                    fill: "rgba(204,242,255,1)"
                }
            }, {
                type: "text",
                top: "45%",
                left: "61%",
                style: {
                    text: `${((pieMiddleData?.[5]?.[1]?.value || 0) / (pieMiddleData?.[5]?.[0]?.value || 1) * 100).toFixed(2)}%`,
                    fontSize: 22,
                    fontWeight: 'bolder',
                    textAlign: "center",
                    fill: "rgba(2,47,255,1)",
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
                            name: '累计完成建安',
                            value: pieMiddleData?.[1]?.[1]?.value || 0,
                            itemStyle: {
                                normal: {
                                    color: 'rgba(0,232,255,1)',
                                }
                            }
                        },
                        {
                            name: '未完成建安金额',
                            value: pieMiddleData?.[1]?.[2]?.value || 0,
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
                            name: '本年完成建安',
                            value: pieMiddleData?.[3]?.[1]?.value || 0,
                            itemStyle: {
                                normal: {
                                    color: 'rgba(204,242,255,1)',
                                }
                            }
                        },
                        {
                            name: '未完成建安金额',
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
                            name: '本季完成建安',
                            value: pieMiddleData?.[5]?.[1]?.value || 0,
                            itemStyle: {
                                normal: {
                                    color: 'rgba(2,47,255,1)',
                                }
                            }
                        },
                        {
                            name: '未完成建安金额',
                            value: pieMiddleData?.[5]?.[2]?.value || 0,
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
            color: ['rgba(0,149,255,1)', 'rgba(0,232,255,1)', 'rgba(204,242,255,1)', 'rgba(2,47,255,1)'],
            tooltip: {
                trigger: 'item',
                formatter: '{b} : {c} ({d}%)'
            },
            title: [
                {
                    text: "总投资完成情况",
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
                    text: "总建安完成情况",
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
                    text: `总计：${pieMiddleData?.[1]?.[0]?.value || 0}`,
                    bottom: "2%",
                    left: "40%",
                    textAlign: "center",
                    textStyle: {
                        color: "white",
                        fontSize: 14,
                        fontWeight: 'normal',
                        textAlign: "center"
                    }
                }
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
                    '未完成金额', '累计完成投资', '累计完成建安'
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
                    fill: "rgba(0,232,255,1)"
                }
            }, {
                type: "text",
                top: "45%",
                left: "36%",
                style: {
                    text: `${((pieMiddleData?.[1]?.[1]?.value || 0) / (pieMiddleData?.[1]?.[0]?.value || 1) * 100).toFixed(2)}%`,
                    fontSize: 22,
                    fontWeight: 'bolder',
                    textAlign: "center",
                    fill: "rgba(204,242,255,1)"
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
                            name: '累计完成投资',
                            value: pieMiddleData?.[0]?.[1]?.value || 0,
                            itemStyle: {
                                normal: {
                                    color: 'rgba(0,232,255,1)',
                                }
                            }
                        },
                        {
                            name: '未完成金额',
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
                            name: '累计完成建安',
                            value: pieMiddleData?.[1]?.[1]?.value || 0,
                            itemStyle: {
                                normal: {
                                    color: 'rgba(204,242,255,1)',
                                }
                            }
                        },
                        {
                            name: '未完成金额',
                            value: pieMiddleData?.[1]?.[2]?.value || 0,
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
            color: ['rgba(0,149,255,1)', 'rgba(0,232,255,1)', 'rgba(204,242,255,1)', 'rgba(2,47,255,1)'],
            tooltip: {
                trigger: 'item',
                formatter: '{b} : {c} ({d}%)'
            },
            title: [
                {
                    text: "总投资完成情况",
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
                    text: "总建安完成情况",
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
                    text: `总计：${pieMiddleData?.[1]?.[0]?.value || 0}`,
                    bottom: "2%",
                    left: "63%",
                    textAlign: "center",
                    textStyle: {
                        color: "white",
                        fontSize: 14,
                        fontWeight: 'normal',
                        textAlign: "center"
                    }
                }
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
                    '未完成金额', '累计完成投资', '累计完成建安'
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
                    fill: "rgba(0,232,255,1)"
                }
            }, {
                type: "text",
                top: "45%",
                left: "59%",
                style: {
                    text: `${((pieMiddleData?.[1]?.[1]?.value || 0) / (pieMiddleData?.[1]?.[0]?.value || 1) * 100).toFixed(2)}%`,
                    fontSize: 22,
                    fontWeight: 'bolder',
                    textAlign: "center",
                    fill: "rgba(204,242,255,1)"
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
                            name: '累计完成投资',
                            value: pieMiddleData?.[0]?.[1]?.value || 0,
                            itemStyle: {
                                normal: {
                                    color: 'rgba(0,232,255,1)',
                                }
                            }
                        },
                        {
                            name: '未完成金额',
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
                            name: '累计完成建安',
                            value: pieMiddleData?.[1]?.[1]?.value || 0,
                            itemStyle: {
                                normal: {
                                    color: 'rgba(204,242,255,1)',
                                }
                            }
                        },
                        {
                            name: '未完成金额',
                            value: pieMiddleData?.[1]?.[2]?.value || 0,
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
            <div className={s.CenterPIEBottom}>
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