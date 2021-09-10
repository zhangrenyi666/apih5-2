import React, { Component } from 'react'
import { Spin, message as Msg } from 'antd'; //, message as Msg 
import ReactEcharts from 'echarts-for-react';
import s from './style.less';
class PIEONE extends Component {
    constructor(props) {
        super(props);
        this.state = {
            aqH: (window.innerHeight-64)*0.25,
            changeDoneRate: 0,
            unchangeDoneRate:0,
            xData: '',
            year:props.year,
            loading: false
        }
    }
    
    componentDidMount() {
        this.refresh();
        window.addEventListener('resize', this.autoSize, false)
    }
    componentDidUpdate(prevProps, prevState, snapshot) {
        if (prevProps.year !== this.state.year) {
            this.setState({ year: prevProps.year }, () => {
                this.refresh();
            })
        }
    }
    refresh = () => {
        this.setState({ loading: true });
        const { myFetch,year } = this.props;
        myFetch('getZtEndMonthFormCountRate', {changeYear:year }).then(
            ({ data, success, message }) => {
                if (success) {
                    for (let i = 0; i < data.length; i++) {
                        let item = data[i];
                        if(item.enginnerId === '1'){
                            this.setState({
                                loading: false,
                                xData:item.enginnerName,
                                changeDoneRate:Number(item.changeDoneRate),
                                unchangeDoneRate:Number(item.unchangeDoneRate),
                            });
                        }
                    }
                } else {
                    Msg.error(message);
                }
            }
        );
    }
    autoSize = () => {
        this.setState({
            aqH: (window.innerHeight-64)*0.25,
        })
    }
    componentWillUnmount() {
        window.removeEventListener('resize', this.autoSize)
    }
    getOption = () => {
        const { xData, changeDoneRate, unchangeDoneRate } = this.state;
        const option = {
            color:['#91c7ae','#eb2100'],
            // title: {
            //     text: changeDoneRate+'%',
            //     subtext: xData,
            //     x: 'center',
            //     y: 'center',
            //     textStyle: {
            //         fontSize:13,
            //         fontWeight:'normal',
            //         color: ['#fff'],
            //         align: 'center'
            //     },
            //     subtextStyle: {
            //         color: '#fff',
            //         fontSize: 13
            //     },
            // },
            // tooltip:{},
            series: [
                {
                    name:'',
                    type:'pie',
                    hoverAnimation: false,
                    radius: ['50', '70'],
                    center: ['50%', '50%'],
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            show: true,
                            textStyle: {
                                fontSize: '30',
                                fontWeight: 'bold'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },
                    silent: true,
                    data:[
                        {
                            value:changeDoneRate, name:'已完成',
                            label: {
                                normal: {
                                    rich: {
                                        a: {
                                            color: '#82ffff',
                                            align: 'center',
                                            fontSize: 13,
                                            fontWeight: "bold"
                                        },
                                        b: {
                                            color: '#82ffff',
                                            align: 'center',
                                            fontSize: 13
                                        },
                                        c: {
                                            color: '#82ffff',
                                          fontSize: 15,
                                          fontWeight: "bold"
                                        }
                                    },
                                    formatter: function(params){
                                        return "{a|"+params.value+"}"+" {c|%}"+"\n\n{b|"+xData+"}";
                                    },
                                    position: 'center',
                                    show: true,
                                    textStyle: {
                                        fontSize: '14',
                                        fontWeight: 'normal',
                                        color: '#82ffff'
                                    }
                                }
                            },
                            itemStyle: {
                                normal: {
                                    color: {
                                        type: 'radial',
                                        r: 1,
                                        colorStops: [{
                                            offset: 0, color: '#5EA6FE' 
                                        }, {
                                            offset: 1, color: 'rgba(94,166,254, 0.9)' 
                                        }],
                                        global: false 
                                    },
                                    shadowColor: '#82ffff',
                                    shadowBlur: 10
                                }
                            }
                        },
                        {
                            value:unchangeDoneRate, name:'未完成',
                            label: {
                                show: false  
                            },
                            itemStyle: {
                                normal: {
                                    color: {
                                        type: 'radial',
                                        r: 1,
                                        colorStops: [{
                                            offset: 0, color: '#FF5624' 
                                        }, {
                                            offset: 1, color: 'rgba(255,86,36, 0.2)' 
                                        }],
                                        global: false 
                                    }
                                }
                            }
                        }
                        
                    ]
                }
            ]
        };
        return option
    }
    render() {
        const { loading, aqH } = this.state;
        return (
            <div className={s.PIEONE}>
                {/* <div className={s.title}>本年变更索赔完成率</div> */}
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
export { PIEONE }