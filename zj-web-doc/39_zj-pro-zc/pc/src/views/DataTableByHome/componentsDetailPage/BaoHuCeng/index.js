import React from 'react';
import Apih5 from 'qnn-apih5';
import style from "./style.less"
import ReactEcharts from 'echarts-for-react';

class Page extends Apih5 {

    state = {

        //一共五条线 每条线二十天
        xName: Array.from(new Array(5)).map((item,index) => `z27-${index + 1}`),
        data: Array.from(new Array(5)).map((item,index) => {
            return {
                label: `第${index + 1}条测线`,
                //合格率数据集
                twentyDaysPassRate: Array.from(new Array(20)).map((item,_index) => {
                    return (Math.random() * 100 + 1).toFixed(0)
                }),
            }
        }),


        // data:{ 
        //     xName:['z27-1', 'z27-2', ...],
        //     lines:[
        //         {
        //             label: `第1条测线`,
        //             //合格率数据集
        //             twentyDaysPassRate:[1, 2, 3, 4, 5]
        //         },
        //         {
        //             label: `第2条测线`,
        //             //合格率数据集
        //             twentyDaysPassRate:[1, 2, 3, 4, 5]
        //         },
        //         ...
        //     ]
        // }

    }

    getOptionOne = () => {
        const { xName,data } = this.state;
        let color = ['#f0c725','#16f892','#0BE3FF','#7cca62','#37adcb','orangered'];
        const ops = {
            color: color,
            grid: {
                left: '6%',
                right: '6%',
                top: '10%',
                bottom: '3%',
                containLabel: true
            },
            tooltip: {
                trigger: 'axis',
                axisPointer: { // 坐标轴指示器，坐标轴触发有效
                    type: 'line' // 默认为直线，可选为：'line' | 'shadow'
                },
                formatter: `
                <b>{b0}</b><br />
                <div style="color:${color[0]}">{a0}: {c0}%</div>
                <div style="color:${color[1]}">{a1}: {c1}%</div>
                <div style="color:${color[2]}">{a2}: {c2}%</div>
                <div style="color:${color[3]}">{a3}: {c3}%</div>
                <div style="color:${color[4]}">{a4}: {c4}%</div>
                <div style="color:${color[5]}">{a5}: {c5}%</div>
                `
            },
            legend: {
                show: true,
                x: 'center',
                top: '0%',
                // data: ['第1条测线','第2条测线','第3条测线','第4条测线','第5条测线'],
                orient: 'horizontal',
                textStyle: {
                    color: '#c1cadf',
                    "fontSize": 14
                }
            },
            xAxis: [{
                name: '梁板编号',
                nameTextStyle: {
                    color: '#c1cadf',
                    // align: 'right',
                    // lineHeight: 30,
                    padding:[6, 0, 0, 0]
                },
                type: 'category',
                data: xName,
                textStyle: {
                    color: 'white'
                },
                axisLine: {
                    lineStyle: {
                        color: 'rgba(240,199,37,0.5)'
                    }
                },
                axisLabel: {
                    interval: 0,
                    color: '#c1cadf',
                    fontSize: '15',
                    // rotate: 30
                }
            }],
            yAxis: [{
                type: 'value',
                name: '合格率',
                nameTextStyle: {
                    color: '#c1cadf',
                    align: 'right',
                    lineHeight: 10
                },
                axisLine: {
                    lineStyle: {
                        color: 'rgba(240,199,37,0.5)'
                    }
                },
                axisLabel: {
                    interval: 0,
                    color: '#c1cadf',
                    fontSize: '15',
                    formatter: '{value}%'
                },
                splitLine: {
                    show: true,
                    lineStyle: {
                        color: 'rgba(151,151,151,0.25)'
                    }
                }
            }],
            series: data.map(item => {
                return {
                    data: item.twentyDaysPassRate,
                    type: 'line',
                    smooth: true,
                    name: item.label,
                }
            })
        }
        return ops;
    }

    componentDidMount() {
        this.refreshBaohuCeng();
    }

    //保护层 
    refreshBaohuCeng = async () => {
        return new Promise(async (resolve) => {
            const { myFetch,errMsg } = this.props;
            const { data = [],success,message,code } = await myFetch("getZjProZcProtectLayerLargeScreenList",{
                limit: 999
            });
            resolve();
            if (success) {
                // 处理数据data
                const newLines = data.lines.map((item,index) => {
                    return {
                        label: item.linesName,
                        //合格率数据集
                        twentyDaysPassRate: item.twentyDaysPassRate.map(item => (item * 100).toFixed(2))
                    }
                });
                console.log(newLines)
                this.setState({
                    xName: data.xName,
                    data: newLines,
                });
            } else {
                errMsg(message,code);
            }
        })
    };


    render() {
        return (
            <div className={style.page}>
                <div className={style.title}>
                    最近20片T梁保护层合格率曲线
                </div>
                <div className={style.con}>
                    <ReactEcharts
                        style={{ width: '100%',height: "100%" }}
                        option={{}}
                        ref={(me) => {
                            if (me) {
                                setTimeout(() => {
                                    let ects = me.getEchartsInstance?.();
                                    if (ects && ects.setOption) {
                                        ects.setOption(this.getOptionOne())
                                    }
                                },300)
                            }
                        }}
                        notMerge={true}
                        lazyUpdate={true}
                        theme={"theme_name"}
                    />
                </div>
            </div>)
    }
}
export default Page;