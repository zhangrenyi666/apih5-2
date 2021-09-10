import React, { Component } from "react";
import ReactEcharts from 'echarts-for-react';
import * as echarts from 'echarts';
import QnnForm from "../../modules/qnn-form";
import ProjectList from "./ProjectList";
import 'echarts/map/js/china.js';
import 'echarts/map/js/province/anhui';
import 'echarts/map/js/province/aomen';
import 'echarts/map/js/province/beijing';
import 'echarts/map/js/province/chongqing';
import 'echarts/map/js/province/fujian';
import 'echarts/map/js/province/gansu';
import 'echarts/map/js/province/guangdong';
import 'echarts/map/js/province/guangxi';
import 'echarts/map/js/province/guizhou';
import 'echarts/map/js/province/hainan';
import 'echarts/map/js/province/hebei';
import 'echarts/map/js/province/heilongjiang';
import 'echarts/map/js/province/henan';
import 'echarts/map/js/province/hubei';
import 'echarts/map/js/province/hunan';
import 'echarts/map/js/province/jiangsu';
import 'echarts/map/js/province/jiangxi';
import 'echarts/map/js/province/jilin';
import 'echarts/map/js/province/liaoning';
import 'echarts/map/js/province/neimenggu';
import 'echarts/map/js/province/ningxia';
import 'echarts/map/js/province/qinghai';
import 'echarts/map/js/province/shandong';
import 'echarts/map/js/province/shanghai';
import 'echarts/map/js/province/shanxi';
import 'echarts/map/js/province/shanxi1';
import 'echarts/map/js/province/sichuan';
import 'echarts/map/js/province/taiwan';
import 'echarts/map/js/province/tianjin';
import 'echarts/map/js/province/xianggang';
import 'echarts/map/js/province/xinjiang';
import 'echarts/map/js/province/xizang';
import 'echarts/map/js/province/yunnan';
import 'echarts/map/js/province/zhejiang';
import s from './style.less';
import { message as Msg, Spin, Button } from 'antd';
import {
    RiseOutlined,
    FallOutlined
} from '@ant-design/icons';
const rightTopB = require('../../../../src/imgs/dyzlRightTopB.png');
const rightTop = {
    backgroundImage: `url(${rightTopB})`,
    backgroundRepeat: 'no-repeat',
    backgroundSize: '100% 100%'
};
class index extends Component {
    state = {
        totalAmount: [],
        alwaysTake: [],
        data: null,
        loadingOut: false,
        loadingOne: true,
        loadingTow: false,
        loadingL: false,
        type: '1',
        dataTow: [],
        dataTowLegend:[],
        dataL: [],
        areaName: '',
        proProcessName: '',
        proTypeName: '',
        areaNameY: '',
        areaNameUp: '',
        dataOut: []
    }
    getOption = () => {
        const { dataOut } = this.state;
        var option = {
            tooltip: {
                trigger: 'item',
                formatter: function (params) {
                    return params.name + '：<br/>项目数量：' + (params?.data?.number || 0);
                }
            },
            geo: {
                map: 'china',
                show: true,
                roam: false,
                zoom: 1.1,
                label: {
                    normal: {
                        show: false
                    },
                    emphasis: {
                        show: false
                    }
                },
                layoutSize: "100%",
                itemStyle: {
                    normal: {
                        shadowColor: '#00ffffcc',
                        shadowOffsetX: 0,
                        borderWidth: 0,
                        shadowOffsetY: -10,
                        areaColor: '#00ffffcc'
                    }
                },
                regions: [{
                    name: '南海诸岛',
                    itemStyle: {
                        areaColor: 'rgba(0, 10, 52, 1)',
                        borderColor: 'rgba(0, 10, 52, 1)',
                        normal: {
                            opacity: 0,
                            label: {
                                show: false,
                                color: "#009cc9",
                            }
                        }
                    },


                }],
            },
            series: [
                {
                    type: 'map',
                    map: 'china',
                    aspectScale: 0.75,
                    zoom: 1.1,
                    label: {
                        normal: {
                            color: "#fff",
                            show: false
                        },
                        emphasis: {
                            color: "#fff",
                            show: false
                        }
                    },
                    itemStyle: {
                        normal: {
                            borderWidth: 0,
                            shadowBlur: 5,
                            shadowColor: 'rgba(0,255,255,1)',
                            areaColor: {
                                type: 'radial',
                                x: 0.95,
                                y: 0.95,
                                r: 0.95,
                                colorStops: [{
                                    offset: 0,
                                    color: '#19314d' // 0% 处的颜色
                                }, {
                                    offset: 1,
                                    color: '#19314d'  // 100% 处的颜色
                                }],
                                globalCoord: true // 缺省为 false
                            },
                        },
                        emphasis: {
                            areaColor: 'rgba(230,189,69,1)',
                            borderWidth: 0.1
                        }
                    },
                    data: dataOut
                },
                {
                    type: "effectScatter",
                    coordinateSystem: "geo",
                    showEffectOn: "render",
                    zlevel: 1,
                    symbol: "path://M409,271.9c0,40.2-69.6,73.8-155.5,73.8S98,313.1,98,272.9s69.6-72.8,155.5-72.8S409,231.7,409,271.9z M362,274.3c0,28.2-48.8,51.7-109,51.7s-109-22.8-109-51s48.8-51,109-51S362,246.1,362,274.3z M252.1,309.5c-37.9,0-67.6-14.7-67.6-33.5s29.7-33.5,67.6-33.5c17.7,0,34.3,3.2,46.8,8.9c13.4,6.2,20.8,14.8,20.8,24.2C319.7,294.6,290,309.5,252.1,309.5z M252.1,249.5c-32.9,0-60.6,12.1-60.6,26.5s27.8,26.5,60.6,26.5c16.6,0,32.1-3.1,43.8-8.7c10.7-5.1,16.8-11.7,16.8-18.3C312.7,261.4,285,249.5,252.1,249.5z M250.5,223h-2v-8h2V223z M258.5,215h-2v-8h2V215z M250.5,207h-2v-8h2V207z M258.5,199h-2v-8h2V199z M250.5,191h-2v-8h2V191z M258.5,183h-2v-8h2V183z M250.5,175h-2v-8h2V175z M258.5,167h-2v-8h2V167z M250.5,159h-2v-8h2V159z M258.5,151h-2v-8h2V151z M250.5,143h-2v-8h2V143z M258.5,135h-2v-8h2V135z M250.5,127h-2v-8h2V127z M258.5,119h-2v-8h2V119z M250.5,111h-2v-8h2V111z M258.5,103h-2v-8h2V103z M250.5,95h-2v-8h2V95z M258.5,87h-2v-8h2V87z M250.5,79h-2v-8h2V79z M258.5,71h-2v-8h2V71z M250.5,63h-2v-8h2V63z M258.5,55h-2v-8h2V55z M250.5,47h-2v-5h5v2h-3V47z",
                    rippleEffect: {
                        period: 15,
                        scale: 4,
                        brushType: "fill"
                    },
                    hoverAnimation: true,
                    label: {
                        normal: {
                            formatter: function (params) {
                                return params?.data?.number || 0;
                            },
                            position: "top",
                            offset: [
                                0,
                                10
                            ],
                            fontSize: 18,
                            fontWeight: "bold",
                            color: "#00FFFF",
                            show: true
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: "#1DE9B6",
                            shadowBlur: 10,
                            shadowColor: "#333"
                        }
                    },
                    symbolSize: 10,
                    data: dataOut
                },
                {
                    symbolSize: 10,
                    showEffectOn: "render",
                    zlevel: 1,
                    symbol: "path://M409,271.9c0,40.2-69.6,73.8-155.5,73.8S98,313.1,98,272.9s69.6-72.8,155.5-72.8S409,231.7,409,271.9z M362,274.3c0,28.2-48.8,51.7-109,51.7s-109-22.8-109-51s48.8-51,109-51S362,246.1,362,274.3z M252.1,309.5c-37.9,0-67.6-14.7-67.6-33.5s29.7-33.5,67.6-33.5c17.7,0,34.3,3.2,46.8,8.9c13.4,6.2,20.8,14.8,20.8,24.2C319.7,294.6,290,309.5,252.1,309.5z M252.1,249.5c-32.9,0-60.6,12.1-60.6,26.5s27.8,26.5,60.6,26.5c16.6,0,32.1-3.1,43.8-8.7c10.7-5.1,16.8-11.7,16.8-18.3C312.7,261.4,285,249.5,252.1,249.5z M250.5,223h-2v-8h2V223z M258.5,215h-2v-8h2V215z M250.5,207h-2v-8h2V207z M258.5,199h-2v-8h2V199z M250.5,191h-2v-8h2V191z M258.5,183h-2v-8h2V183z M250.5,175h-2v-8h2V175z M258.5,167h-2v-8h2V167z M250.5,159h-2v-8h2V159z M258.5,151h-2v-8h2V151z M250.5,143h-2v-8h2V143z M258.5,135h-2v-8h2V135z M250.5,127h-2v-8h2V127z M258.5,119h-2v-8h2V119z M250.5,111h-2v-8h2V111z M258.5,103h-2v-8h2V103z M250.5,95h-2v-8h2V95z M258.5,87h-2v-8h2V87z M250.5,79h-2v-8h2V79z M258.5,71h-2v-8h2V71z M250.5,63h-2v-8h2V63z M258.5,55h-2v-8h2V55z M250.5,47h-2v-5h5v2h-3V47z",
                    rippleEffect: {
                        period: 15,
                        scale: 4,
                        brushType: "fill"
                    },
                    label: {
                        normal: {
                            formatter: '{b}',
                            offset: [
                                0,
                                10
                            ],
                            fontSize: 14,
                            position: 'bottom',
                            show: true
                        },
                        emphasis: {
                            color: "white",
                            show: true
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: "white",
                            show: false
                        }
                    },
                    name: 'light',
                    type: 'scatter',
                    coordinateSystem: 'geo',
                    data: dataOut

                }
            ]
        };
        return option;
    }
    getOptionOne = () => {
        let { areaName } = this.state;
        var option = {
            geo: {
                map: areaName,
                aspectScale: 0.75, //长宽比
                zoom: 0.8,
                roam: false,
                itemStyle: {
                    normal: {
                        areaColor: '#e6bd4580',
                        shadowColor: '#e6bd4580',
                        shadowOffsetX: 0,
                        shadowOffsetY: 15,
                        borderWidth: 0
                    },
                    emphasis: {
                        areaColor: '#e6bd4580',
                        borderWidth: 0,
                        label: {
                            show: false
                        }
                    }
                }
            },
            series: [
                {
                    type: 'map',
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: false
                        }
                    },
                    itemStyle: {
                        normal: {
                            borderColor: '#e6bd4580',
                            areaColor: '#e6bd4580',
                            borderWidth: 0,
                        },
                        emphasis: {
                            areaColor: '#e6bd4580',
                            borderWidth: 0,
                            color: '#e6bd4580'
                        }
                    },
                    zoom: 0.8,
                    roam: false,
                    map: areaName
                }
            ]
        };
        return option;
    }
    onChartClick = (e) => {
        const { dataOut } = this.state;
        var cityArr = [
            ['上海', '河北', '山西', '内蒙古', '辽宁', '吉林', '黑龙江', '江苏', '浙江', '安徽', '福建', '江西', '山东', '河南', '湖北', '湖南', '广东', '广西', '海南', '四川', '贵州', '云南', '西藏', '陕西', '甘肃', '青海', '宁夏', '新疆', '北京', '天津', '重庆', '香港', '澳门', '台湾'],
            ['shanghai', 'hebei', 'shanxi', 'neimenggu', 'liaoning', 'jilin', 'heilongjiang', 'jiangsu', 'zhejiang', 'anhui', 'fujian', 'jiangxi', 'shandong', 'henan', 'hubei', 'hunan', 'guangdong', 'guangxi', 'hainan', 'sichuan', 'guizhou', 'yunnan', 'xizang', 'shanxi1', 'gansu', 'qinghai', 'ningxia', 'xinjiang', 'beijing', 'tianjin', 'chongqing', 'xianggang', 'aomen', 'taiwan'],
            ['Shang Hai', 'He Bei', 'Shan Xi', 'Inner Mongolia', 'Liao Ning', 'Ji Lin', 'Hei Long Jiang', 'Jiang Su', 'Zhe Jiang', 'An Hui', 'Fu Jian', 'Jiang Xi', 'Shan Dong', 'He Nan', 'Hu Bei', 'Hu Nan', 'Guang Dong', 'Guang Xi', 'Hai Nan', 'Si Chuan', 'Gui Zhou', 'Yun Nan', 'Xi Zang', 'Shaan Xi', 'Gan Su', 'Qing Hai', 'Ning Xia', 'Xin Jiang', 'Bei Jing', 'Tian Jin', 'Chong Qing', 'Hong Kong', 'Ma Cao', 'Tai Wan']
        ];
        let areaName = '';
        let areaNameY = '';
        let areaNameUp = '';
        for (let i = 0; i < cityArr[0].length; i++) {
            if (cityArr[0][i] === e.name) {
                areaName = cityArr[0][i];
                areaNameY = cityArr[1][i];
                areaNameUp = cityArr[2][i];
            }
        }
        this.setState({
            areaName,
            areaNameY,
            areaNameUp,
            proTypeName: '',
            proProcessName: '',
            dataOut: dataOut.map((item) => {
                if (item.name === e.name) {
                    item.selected = true;
                } else {
                    item.selected = false;
                }
                return item;
            }),
            loadingL: true
        }, () => {
            this.props.myFetch('getHomeRegionalOverviewMap', { areaName: this.state.areaName }).then(
                ({ success, message, data }) => {
                    if (success) {
                        this.setState({
                            dataL: data,
                            loadingL: false
                        })
                    } else {
                        Msg.error(message);
                        this.setState({
                            loadingL: false
                        })
                    }
                }
            );
        })
    }
    onChartTowClick = (e) => {
        const { type } = this.state;
        if (type === '1') {
            this.setState({
                proProcessName: e.name,
                proTypeName: '',
                areaName: ''
            })
        } else {
            this.setState(({
                proTypeName: e.name,
                proProcessName: '',
                areaName: ''
            }))
        }
    }
    componentDidMount() {
        this.refreshOut();
        this.props.myFetch('getHomeRegionalOverviewZhtAndZja', {}).then(
            ({ success, message, data }) => {
                if (success) {
                    let totalAmount = data.zht.toString().split("");
                    let alwaysTake = data.zja.toString().split("");
                    this.setState({
                        totalAmount: totalAmount,
                        alwaysTake: alwaysTake,
                        data: data
                    }, () => {
                        this.setState({
                            loadingOne: false
                        })
                    })
                } else {
                    Msg.error(message)
                }
            }
        );
        this.refresh(); 
    }
    refreshOut = () => {
        this.setState({
            loadingOut: true
        })
        this.props.myFetch('getHomeRegionalOverviewMap', {}).then(
            ({ success, message, data }) => {
                if (success) {
                    this.setState({
                        dataOut: data.map((item) => {
                            item.name = item.name.replace(/省|市/g, "");
                            item.number = item.value;
                            item.value = item.map;
                            return item;
                        }),
                        loadingOut: false
                    })
                } else {
                    Msg.error(message);
                    this.setState({
                        loadingOut: false
                    })
                }
            }
        );
    }
    refresh = () => {
        const { type } = this.state;
        this.setState({
            loadingTow: true
        })
        this.props.myFetch('getHomeRegionalOverviewProcessAndType', { type: type }).then(
            ({ success, message, data }) => {
                if (success) {
                    this.setState({
                        dataTowLegend:data.map((item) => {
                            return {
                                name: type === '1' ? (item.proProcessName ? item.proProcessName : '空') : (item.proTypeName ? item.proTypeName : '空'),
                                value: type === '1' ? item.processCount : item.typeCount,
                                ...item
                            }
                        }),
                        dataTow: data.reverse().map((item) => {
                            return {
                                name: type === '1' ? (item.proProcessName ? item.proProcessName : '空') : (item.proTypeName ? item.proTypeName : '空'),
                                value: type === '1' ? item.processCount : item.typeCount,
                                ...item
                            }
                        }),
                        loadingTow: false
                    })
                } else {
                    Msg.error(message)
                }
            }
        );
    }
    getOptionTow = () => {
        const { dataTow, dataTowLegend } = this.state;
        // series
        let series = [];
        let color1 = ['rgba(204,242,255,0.8)', 'rgba(211,101,6,0.8)', 'rgba(6,126,211,0.8)', 'rgba(211,6,205,0.8)', 'rgba(57,211,6,0.8)', 'rgba(230,189,69,0.8)', 'rgba(0,234,255,0.8)', 'rgba(0,46,255,0.8)','rgba(255,51,85,0.6)','rgba(88,5,199,0.8)'];
        let color2 = ['rgba(204,242,255,0)', 'rgba(211,101,6,0.2)', 'rgba(6,126,211,0.2)', 'rgba(211,6,205,0.2)', 'rgba(57,211,6,0.2)', 'rgba(230,189,69,0.2)', 'rgba(0,234,255,0.2)', 'rgba(0,46,255,0.2)','rgba(255,51,85,0.2)','rgba(88,5,199,0.2)'];
        let color3 = ['#ccf2ffe6', '#d36506e6', '#067ed3e6', '#d306cde6', '#39d306e6', '#e6bd45e6', '#00eaffe6', '#002effe6', '#ff3355cc', '#5805c7e6'];
        dataTow.map((item, index) => {
            if (index === 0) {
                let c1 = color1[index];
                let c2 = color2[index];
                let c3 = color3[index];
                series.push(
                    {
                        name: item.name,
                        type: 'bar',
                        barWidth: 100,
                        barGap: '-100%',
                        itemStyle: {
                            normal: {
                                color: new echarts.graphic.LinearGradient(0, 0, 0, 1,
                                    [
                                        {
                                            offset: 0,
                                            color: c1
                                        },
                                        {
                                            offset: 1,
                                            color: c2
                                        }
                                    ],
                                    false
                                )
                            }
                        },
                        data: [item.value]
                    },
                    { // 替代柱状图 默认不显示颜色，是最下方柱图（邮件营销）的value值 - 20 
                        name: item.name,
                        type: 'bar',
                        barWidth: 100,
                        barGap: '-100%',
                        stack: '柱状图高度',
                        itemStyle: {
                            color: 'transparent'
                        },
                        data: [item.value],
                        label: {
                            show: true,
                            position: 'right',
                            color: c3,
                            fontSize: 12,
                            fontWeight: 'bold',
                            formatter: function (param) {
                                return `${(param.value / item.count * 100).toFixed(2)}%`
                            }
                        },
                    },
                    //中间菱形
                    {
                        name: "",
                        type: "pictorialBar",
                        symbol: 'diamond',
                        symbolSize: [100, 45],
                        symbolOffset: [0, -23],
                        symbolPosition: "end",
                        itemStyle: {
                            normal: {
                                color: new echarts.graphic.LinearGradient(0, 0, 0, 1,
                                    [
                                        {
                                            offset: 0,
                                            color: c3
                                        },
                                        {
                                            offset: 1,
                                            color: c3
                                        }
                                    ],
                                    false
                                ),
                            }
                        },
                        z: 12,
                        data: [item.value]
                    }
                )
            } else {
                let number = 0;
                for (var i = 0; i <= index; i++) {
                    number += dataTow[i].value;
                }
                let c1 = color1[index];
                let c2 = color2[index];
                let c3 = color3[index];
                series.push(
                    {
                        name: item.name,
                        type: 'bar',
                        barWidth: 100,
                        barGap: '-100%',
                        stack: '柱状图高度',
                        itemStyle: {
                            normal: {
                                color: new echarts.graphic.LinearGradient(0, 0, 0, 1,
                                    [
                                        {
                                            offset: 0,
                                            color: c1
                                        },
                                        {
                                            offset: 1,
                                            color: c2
                                        }
                                    ],
                                    false
                                ),
                            }
                        },
                        label: {
                            show: true,
                            position: 'right',
                            color: c3,
                            fontSize: 12,
                            fontWeight: 'bold',
                            formatter: function (param) {
                                return `${(param.value / item.count * 100).toFixed(2)}%`
                            }
                        },
                        data: [item.value]
                    },
                    //中间菱形
                    {
                        name: item.name,
                        type: "pictorialBar",
                        symbol: 'diamond',
                        symbolSize: [100, 45],
                        symbolOffset: [0, -23],
                        symbolPosition: "end",
                        itemStyle: {
                            normal: {
                                color: new echarts.graphic.LinearGradient(0, 0, 0, 1,
                                    [
                                        {
                                            offset: 0,
                                            color: c3
                                        },
                                        {
                                            offset: 1,
                                            color: c3
                                        }
                                    ],
                                    false
                                ),
                            }
                        },
                        z: 12,
                        data: [number]
                    }
                )
            }
            return item;
        })
        let title = {
            text: `投资项目总数`,
            textStyle: {
                color: 'white',
                fontSize: 16,
                fontWeight: '500'
            },
            subtext: `${dataTow.length ? dataTow[0].count : 0}`,
            subtextStyle: {
                color: '#1890ff',
                fontSize: 26,
                fontWeight: 'bold'
            }
        };
        // tooltip
        // const tooltip = {
        //     trigger: "item",
        //     confine: true
        // }
        // legend
        const legend = {
            data: dataTowLegend,
            type: 'scroll',
            // selectedMode: false,
            textStyle: { color: '#ffffff' },
            itemWidth: 18,
            itemHeight: 18,
            x: 5,
            y: 'bottom',
            icon: 'roundRect',
            orient: "vertical",
            formatter: function (name) {
                var value;
                for (var i = 0, l = dataTow.length; i < l; i++) {
                    if (dataTow[i].name === name || !name) {
                        value = dataTow[i].value;
                    }
                }
                return `${name}：${value}`
            }
        }
        // grid
        const grid = { top: 20, left: 100, right: 0, bottom: 20 }

        // xAxis
        const xAxis = {
            show: false,
            data: ['数量']
        }

        // yAxis
        const yAxis = [{
            show: false
        }]

        // 渲染
        let option = { title, xAxis, yAxis, series, grid, legend }
        return option;
    }
    render() {
        const { totalAmount, alwaysTake, data, loadingOut, loadingOne, loadingTow, loadingL, areaName, proProcessName, proTypeName, dataL, dataOut, areaNameUp, type } = this.state;
        return (
            <div className={s.root}>
                <div className={s.left}>
                    <Spin spinning={loadingOut}>
                        {dataOut.length ? <ReactEcharts
                            style={{ height: '100%' }}
                            option={this.getOption()}
                            notMerge={true}
                            lazyUpdate={true}
                            theme={"theme_name"}
                            onEvents={{
                                'click': this.onChartClick
                            }}
                        /> : null}
                    </Spin>
                </div>
                <div className={s.right}>
                    {!areaName ? <div className={s.rightTop} style={rightTop}>
                        <div className={s.rightTopOne}>
                            <div className={s.rightTopOneL}>
                                总览
                            </div>
                            <div className={s.rightTopOneR}>
                                <QnnForm
                                    fetch={this.props.myFetch}
                                    upload={this.props.myUpload}
                                    headers={{
                                        token: this.props.loginAndLogoutInfo.loginInfo.token
                                    }}
                                    wrappedComponentRef={(me) => {
                                        this.form = me;
                                    }}
                                    formConfig={[
                                        {
                                            field: 'type',
                                            type: 'select',
                                            optionConfig: {
                                                label: 'label',
                                                value: 'value'
                                            },
                                            optionData: [
                                                {
                                                    label: '项目进展',
                                                    value: '1'
                                                },
                                                {
                                                    label: '项目类型',
                                                    value: '2'
                                                }
                                            ],
                                            size: 'small',
                                            initialValue: type,
                                            allowClear: false,
                                            onChange: (val) => {
                                                this.setState({
                                                    type: val,
                                                    proProcessName: '',
                                                    proTypeName: '',
                                                    areaName: ''
                                                }, () => {
                                                    this.refresh();
                                                })
                                            },
                                            placeholder: '请选择'
                                        }
                                    ]}
                                />
                            </div>
                        </div>
                        <div className={s.rightTopTow}>
                            <div className={s.rightTopTowL}>
                                <Spin spinning={loadingOne}>
                                    <div className={s.rightTopTowLOne}>
                                        <div>
                                            <div style={{ color: 'white', fontSize: '1.2em', fontWeight: '500' }}>总合同额</div>
                                            <div>
                                                <span style={{ color: '#1890ff', fontSize: '1.8em', fontWeight: 'bold' }}>{totalAmount}</span>&nbsp;&nbsp;
                                                <span style={{ color: '#1890ff', fontSize: '1em', fontWeight: 'bold' }}>万元</span>
                                            </div>
                                            <div style={{ color: 'white', fontSize: '0.8em' }}>本年完成投资&nbsp;&nbsp;<span style={{ fontWeight: 'bold', fontSize: '1.2em' }}>{data?.ztzwcbn}</span>&nbsp;&nbsp;万元</div>
                                            <div style={{ color: 'white', fontSize: '0.8em' }}>相比去年同期{data?.tzwcbhl >= 0 ? '增长' : '减少'}&nbsp;&nbsp;<span style={data?.tzwcbhl >= 0 ? { fontWeight: 'bold', fontSize: '1.2em', color: '#04e4e4' } : { fontWeight: 'bold', fontSize: '1.2em', color: '#e4048a' }}>{data?.tzwcbhl}%</span>&nbsp;&nbsp;{data?.tzwcbhl === 0 ? null : data?.tzwcbhl < 0 ? <FallOutlined style={{ color: '#e4048a', fontSize: '1.5em' }} /> : <RiseOutlined style={{ color: '#04e4e4', fontSize: '1.5em' }} />}</div>
                                        </div>
                                    </div>
                                    <div className={s.rightTopTowLTow}>
                                        <div>
                                            <div style={{ color: 'white', fontSize: '1.2em', fontWeight: '500' }}>总建安费</div>
                                            <div>
                                                <span style={{ color: '#1890ff', fontSize: '1.8em', fontWeight: 'bold' }}>{alwaysTake}</span>&nbsp;&nbsp;
                                                <span style={{ color: '#1890ff', fontSize: '1em', fontWeight: 'bold' }}>万元</span>
                                            </div>
                                            <div style={{ color: 'white', fontSize: '0.8em' }}>本年完成建安&nbsp;&nbsp;<span style={{ fontWeight: 'bold', fontSize: '1.2em' }}>{data?.zwcjafbn}</span>&nbsp;&nbsp;万元</div>
                                            <div style={{ color: 'white', fontSize: '0.8em' }}>相比去年同期{data?.wcjafbhl >= 0 ? '增长' : '减少'}&nbsp;&nbsp;<span style={data?.wcjafbhl >= 0 ? { fontWeight: 'bold', fontSize: '1.2em', color: '#04e4e4' } : { fontWeight: 'bold', fontSize: '1.2em', color: '#e4048a' }}>{data?.wcjafbhl}%</span>&nbsp;&nbsp;{data?.wcjafbhl === 0 ? null : data?.wcjafbhl < 0 ? <FallOutlined style={{ color: '#e4048a', fontSize: '1.5em' }} /> : <RiseOutlined style={{ color: '#04e4e4', fontSize: '1.5em' }} />}</div>
                                        </div>
                                    </div>
                                </Spin>
                            </div>
                            <div className={s.rightTopTowR}>
                                <Spin spinning={loadingTow}>
                                    <ReactEcharts
                                        style={{ height: "100%" }}
                                        option={this.getOptionTow()}
                                        notMerge={true}
                                        lazyUpdate={true}
                                        theme={"theme_name"}
                                        onEvents={{
                                            'legendselectchanged': this.onChartTowClick
                                        }}
                                    />
                                </Spin>
                            </div>
                        </div>
                    </div> : <div className={s.rightTop} style={rightTop}>
                            <div style={{ position: 'absolute', top: 20, left: 20, zIndex: 999 }}>
                                <div style={{ fontSize: '2em', color: '#1890ff', borderBottom: '4px solid #1890ff', paddingBottom: '10px', fontWeight: '500' }}>{areaName}</div>
                                <div style={{ fontSize: '1.2em', color: '#1890ff', fontWeight: '500' }}>{areaNameUp}</div>
                            </div>
                            <div style={{ position: 'absolute', bottom: 20, left: 20, zIndex: 999 }}>
                                <Button type="primary" onClick={() => {
                                    this.setState({
                                        areaName: '',
                                        proTypeName: '',
                                        proProcessName: '',
                                        dataOut: dataOut.map((item) => {
                                            item.selected = false;
                                            return item;
                                        })
                                    })
                                }}>返回</Button>
                            </div>
                            <Spin spinning={loadingL}>
                                <div className={s.rightTopL}>
                                    <ReactEcharts
                                        style={{ height: '100%' }}
                                        option={this.getOptionOne()}
                                        notMerge={true}
                                        lazyUpdate={true}
                                        theme={"theme_name"}
                                    />
                                </div>
                                {dataL.length ? <div className={s.rightTopR} style={{ padding: '20px' }}>
                                    <div className={s.rightTopRC}>
                                        <div>
                                            <div style={{ color: 'white', fontSize: '1.2em', fontWeight: '500' }}>累计完成投资总额：</div>
                                            <div style={{ color: '#1890ff', fontWeight: '500' }}><span style={{ fontSize: '2.2em', fontWeight: 'bold' }}>{(dataL[0]?.value?.tzwckl || 0).toFixed(2).replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,')}</span>&nbsp;&nbsp;万元</div>
                                        </div>
                                    </div>
                                    <div className={s.rightTopRC} style={{ marginTop: '12px' }}>
                                        <div>
                                            <div style={{ color: 'white', fontSize: '1.2em', fontWeight: '500' }}>累计完成建安总额：</div>
                                            <div style={{ color: '#1890ff', fontWeight: '500' }}><span style={{ fontSize: '2.2em', fontWeight: 'bold' }}>{(dataL[0]?.value?.wcjafkl || 0).toFixed(2).replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,')}</span>&nbsp;&nbsp;万元</div>
                                        </div>
                                    </div>
                                    <div className={s.rightTopRC} style={{ marginTop: '12px' }}>
                                        <div>
                                            <div style={{ color: 'white', fontSize: '1.2em', fontWeight: '500' }}>投资项目数量：</div>
                                            <div style={{ color: '#1890ff', fontWeight: '500' }}><span style={{ fontSize: '2.2em', fontWeight: 'bold' }}>{dataL[0]?.value?.invCount}</span>&nbsp;&nbsp;个</div>
                                        </div>
                                    </div>
                                </div> : null}
                            </Spin>
                        </div>}
                    <div className={s.rightBottom} style={rightTop}>
                        <ProjectList {...this.props} areaName={areaName} proProcessName={proProcessName} proTypeName={proTypeName} />
                    </div>
                </div>
            </div>
        )
    }
}
export default index;
