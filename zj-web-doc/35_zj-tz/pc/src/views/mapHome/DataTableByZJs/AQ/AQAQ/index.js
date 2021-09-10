import React, { Component } from 'react'
import { Spin, message as Msg } from 'antd'; //, message as Msg 
import ReactEcharts from 'echarts-for-react';
import s from './style.less';
class AQAQ extends Component {
    state = {
        aqH: (window.innerHeight - 165 - ((window.innerHeight - 165) / 2) - 27) * 0.65,
        finish: [],
        totalNum: [],
        xData: [],
        loading: false
    }
    componentDidMount() {
        this.refresh();
        window.addEventListener('resize', this.autoSize, false)
    }
   
    refresh = () => {
        this.setState({ loading: true });
        const { myFetch } = this.props;
        myFetch('getZtEndAccruedCountMoney', { }).then(
            ({ data, success, message }) => {
                if (success) {
                    let xData = [];
                    let finish = [];
                    let totalNum = [];
                    for (let i = 0; i < data.length; i++) {
                        let item = data[i];
                        xData.push(item.enginnerName);
                        finish.push(item.totalMoneyIn);
                        totalNum.push(item.totalMoneyOut);
                    }
                    // //处理数据data
                    this.setState({
                        loading: false,
                        xData,
                        finish,
                        totalNum,
                    });
                } else {
                    Msg.error(message);
                }
            }
        );
    }
    autoSize = () => {
        this.setState({
            aqH: (window.innerHeight-64)*0.3,
        })
    }
    componentWillUnmount() {
        window.removeEventListener('resize', this.autoSize)
    }
    getOption = () => {
        const { xData, finish, totalNum } = this.state;
        const option = {
            color:['#eb2100', '#0096f3'],
            tooltip: {
                trigger: 'axis',
                axisPointer: {          
                    type: 'shadow', 
                    itemStyle: {
                        color: '#eebdc1'
                    }
                }
            },
            legend: {
                data: ['合同内', '合同外'],
                y: 'bottom',
                textStyle: {
                    color: 'white',
                },
            },
            grid: {
                left: '26%',
                right: '14%',
                bottom: '23%',
                top: '3%',
            },
            yAxis: {
                type: 'category',
                data: xData,
            },
            xAxis: {
                type: 'value',
                show: false,
            },
            textStyle: {
                color: 'white'
            },
            label: {
                show: true
            },
            series: [
                {
                    name: '合同外',
                    type: 'bar',
                    stack: 'aq',
                    data: totalNum,
                    label: {
                        normal: {
                            show: false
                        }
                    },
                    // itemStyle: {
                    //     color: {
                    //         type: 'radial',
                    //         r: 1,
                    //         colorStops: [{
                    //             offset: 0, color: '#5EA6FE' // 0% 处的颜色
                    //         }, {
                    //             offset: 1, color: 'rgba(94,166,254, 0.2)' // 100% 处的颜色
                    //         }],
                    //         global: false // 缺省为 false
                    //     }
                    // }
                },
                {
                    name: '合同内',
                    type: 'bar',
                    stack: 'aq',
                    data: finish,
                    label: {
                        normal: {
                            show: false
                        }
                    },
                    // itemStyle: {
                    //     color: {
                    //         type: 'radial',
                    //         r: 1,
                    //         colorStops: [{
                    //             offset: 0, color: '#FF5624' // 0% 处的颜色
                    //         }, {
                    //             offset: 1, color: 'rgba(255,86,36, 0.2)' // 100% 处的颜色
                    //         }],
                    //         global: false // 缺省为 false
                    //     }
                    // }
                }
            ]
        };
        return option
    }
    render() {
        const { loading, aqH } = this.state;
        return (
            <div className={s.AQAQ}>
                <div className={s.title}>应计未计情况</div>
                <Spin spinning={loading}>
                    <ReactEcharts
                        style={{ height: aqH}}
                        option={this.getOption()}
                        notMerge={true}
                        lazyUpdate={true}
                        theme={"theme_name"}
                    />
                </Spin>
            </div>
        )
    }
}
export { AQAQ }